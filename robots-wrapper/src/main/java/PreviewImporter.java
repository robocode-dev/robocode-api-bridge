import java.io.IOException;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

final class PreviewImporter {
    static final String STATE = ".bridge-state.properties";
    private final Path home;
    private final Path output;
    private final PrintStream out;
    private final PrintStream err;
    private Properties state;

    PreviewImporter(Path home, Path output, PrintStream out, PrintStream err) throws IOException {
        this.home = PreviewFiles.canonical(home);
        PreviewFiles.rejectLinks(output);
        this.output = PreviewFiles.canonical(output);
        this.out = out;
        this.err = err;
    }

    static Properties loadState(Path output) throws IOException {
        Path path = output.resolve(STATE);
        PreviewFiles.rejectLinks(path);
        Properties state = PreviewFiles.load(path);
        if (Files.exists(path) && !"1".equals(state.getProperty("format"))) {
            throw new IOException("Unknown bridge state format in " + path + ". Choose a new output directory.");
        }
        state.setProperty("format", "1");
        return state;
    }

    int importSources(List<Path> sources) throws IOException {
        for (Path source : sources) {
            Path root = PreviewFiles.canonical(source);
            if (output.startsWith(root) || root.startsWith(output)) {
                throw new IOException("Source and output must be separate, non-overlapping directories: " + root + " and " + output);
            }
            if (home.startsWith(output) || output.startsWith(home)) {
                throw new IOException("Output must be outside the unpacked bridge package");
            }
        }
        Map<String, Path> jars = findJars(sources);
        if (jars.isEmpty()) throw new IOException("No robot jars found in " + sources);
        Files.createDirectories(output);
        Path lockPath = PreviewFiles.child(output, ".bridge.lock");
        try (FileChannel channel = FileChannel.open(lockPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
             var lock = channel.tryLock()) {
            if (lock == null) throw new IOException("Another wrap-robots process is updating " + output);
            state = loadState(output);
            // Validate all persisted managed paths before any mutation.
            for (String key : state.stringPropertyNames()) {
                if (key.startsWith("owner.")) PreviewFiles.child(output, key.substring(6));
            }
            Properties metadata = PreviewFiles.load(home.resolve("preview.properties"));
            String fingerprint = PreviewFiles.sha256(home.resolve("preview.properties"));
            installRuntime(metadata);
            int imported = 0;
            int reused = 0;
            int failures = 0;
            for (Map.Entry<String, Path> jar : jars.entrySet()) {
                try {
                    if (importJar(jar.getValue(), jar.getKey(), fingerprint)) imported++;
                    else reused++;
                } catch (IOException | RuntimeException failure) {
                    failures++;
                    err.println("FAILED " + jar.getValue() + ": " + failure.getMessage());
                }
            }
            state.setProperty("source.count", Integer.toString(sources.size()));
            for (int i = 0; i < sources.size(); i++) state.setProperty("source." + i, sources.get(i).toString());
            PreviewFiles.save(output.resolve(STATE), state);
            out.println("Imported " + imported + ", reused " + reused + ", failed " + failures + " unique jar(s).");
            return failures;
        }
    }

    static Map<String, Path> findJars(List<Path> sources) throws IOException {
        List<Path> files = new ArrayList<>();
        for (Path source : sources) {
            Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
                @Override public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    if (!dir.equals(source)) {
                        try { PreviewFiles.rejectLinks(dir); }
                        catch (IOException redirected) { return FileVisitResult.SKIP_SUBTREE; }
                    }
                    String name = dir.getFileName().toString();
                    if (!dir.equals(source) && (name.startsWith(".") || name.endsWith(".data") || name.equals("lib")
                            || Files.exists(dir.resolve(Main.JAVA_WRAPPER)))) return FileVisitResult.SKIP_SUBTREE;
                    return FileVisitResult.CONTINUE;
                }
                @Override public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (attrs.isRegularFile() && file.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".jar")) files.add(file);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        files.sort(Comparator.comparing(Path::toString));
        Map<String, Path> distinct = new LinkedHashMap<>();
        for (Path file : files) distinct.putIfAbsent(PreviewFiles.sha256(file), file);
        return distinct;
    }

    private void installRuntime(Properties metadata) throws IOException {
        int count;
        try { count = Integer.parseInt(metadata.getProperty("runtime.count", "0")); }
        catch (NumberFormatException invalid) { throw new IOException("Invalid runtime metadata; unpack the archive again", invalid); }
        if (count != 2) throw new IOException("Preview runtime metadata is incomplete; unpack the whole archive again");
        Map<String, Path> runtime = new TreeMap<>();
        for (int i = 0; i < count; i++) {
            String packaged = metadata.getProperty("runtime." + i);
            if (packaged == null || !packaged.startsWith("runtime/lib/")) throw new IOException("Invalid runtime path in package metadata");
            Path jar = PreviewFiles.child(home, packaged);
            String expected = metadata.getProperty("sha256." + packaged);
            if (!Files.isRegularFile(jar) || !PreviewFiles.sha256(jar).equals(expected)) {
                throw new IOException("Missing or changed packaged library: " + packaged + ". Unpack the complete archive again.");
            }
            String relative = "lib/" + jar.getFileName();
            checkReplacement(relative, expected);
            runtime.put(relative, jar);
        }
        Path lib = PreviewFiles.child(output, "lib");
        if (Files.isDirectory(lib)) {
            try (var paths = Files.list(lib)) {
                for (Path path : (Iterable<Path>) paths::iterator) {
                    String name = path.getFileName().toString();
                    String relative = "lib/" + name;
                    if ((name.startsWith("robocode-api-") || name.startsWith("robocode-tankroyale-bot-api-"))
                            && !runtime.containsKey(relative) && !state.containsKey("owner." + relative)) {
                        throw new IOException("Unmanaged bridge library conflicts with packaged runtime: " + path + ". Use a separate output directory.");
                    }
                }
            }
        }
        List<String> old = lines(state.getProperty("runtime.files", ""));
        for (Map.Entry<String, Path> item : runtime.entrySet()) state.setProperty("owner." + item.getKey(), PreviewFiles.sha256(item.getValue()));
        PreviewFiles.save(output.resolve(STATE), state);
        for (Map.Entry<String, Path> item : runtime.entrySet()) replace(item.getValue(), PreviewFiles.child(output, item.getKey()));
        for (String previous : old) if (!runtime.containsKey(previous)) removeOwned(previous);
        state.setProperty("runtime.files", String.join("\n", runtime.keySet()));
        PreviewFiles.save(output.resolve(STATE), state);
    }

    private boolean importJar(Path source, String hash, String fingerprint) throws IOException {
        String key = "jar." + hash;
        List<String> previous = lines(state.getProperty(key + ".files", ""));
        if (fingerprint.equals(state.getProperty(key + ".package")) && intact(previous)) {
            out.println("REUSED " + source);
            return false;
        }
        Path stage = Files.createTempDirectory(output.getParent(), ".bridge-stage-");
        try {
            Path stagedJar = stage.resolve(hash + ".jar");
            Files.copy(source, stagedJar);
            if (!hash.equals(PreviewFiles.sha256(stagedJar))) throw new IOException("Source changed while being copied; retry the import");
            Main.processJarStrict(stagedJar);
            List<Path> generated;
            try (var paths = Files.walk(stage)) {
                generated = paths.filter(Files::isRegularFile).sorted().collect(java.util.stream.Collectors.toList());
            }
            List<String> owned = new ArrayList<>();
            Set<String> identities = new TreeSet<>();
            try (var paths = Files.list(stage)) {
                for (Path dir : (Iterable<Path>) paths.filter(Files::isDirectory)::iterator) {
                    String name = dir.getFileName().toString();
                    PreviewFiles.safeSegment(name);
                    if (name.equalsIgnoreCase("lib")) throw new IOException("Robot identity conflicts with the runtime library directory");
                    String identity = name.toLowerCase(Locale.ROOT);
                    if (!identities.add(identity)) throw new IOException("Conflicting case-insensitive robot identity: " + name);
                    String existing = state.getProperty("bot." + identity);
                    if (existing != null && !existing.equals(hash)) throw new IOException("Conflicting robot identity: " + name + " belongs to another jar");
                    if (existing == null && Files.exists(PreviewFiles.child(output, name))) {
                        throw new IOException("Unmanaged bot directory already exists: " + name);
                    }
                }
            }
            for (Path file : generated) {
                String relative = stage.relativize(file).toString().replace('\\', '/');
                PreviewFiles.child(output, relative);
                if (!PreviewFiles.dataFile(relative)) {
                    checkReplacement(relative, PreviewFiles.sha256(file));
                    owned.add(relative);
                }
            }
            // Persist ownership before writes so an interrupted merge can be repaired on rerun.
            for (String identity : identities) state.setProperty("bot." + identity, hash);
            for (String relative : owned) state.setProperty("owner." + relative, PreviewFiles.sha256(stage.resolve(relative)));
            state.remove(key + ".package");
            Set<String> pendingFiles = new LinkedHashSet<>(previous);
            pendingFiles.addAll(owned);
            state.setProperty(key + ".files", String.join("\n", pendingFiles));
            PreviewFiles.save(output.resolve(STATE), state);
            for (Path file : generated) {
                String relative = stage.relativize(file).toString().replace('\\', '/');
                Path target = PreviewFiles.child(output, relative);
                if (!PreviewFiles.dataFile(relative) || !Files.exists(target)) replace(file, target);
            }
            for (String old : previous) if (!owned.contains(old)) removeOwned(old);
            state.setProperty(key + ".files", String.join("\n", owned));
            state.setProperty(key + ".package", fingerprint);
            state.setProperty(key + ".source", source.toString());
            PreviewFiles.save(output.resolve(STATE), state);
            out.println("IMPORTED " + source);
            return true;
        } finally { PreviewFiles.removeTree(stage); }
    }

    private boolean intact(List<String> files) throws IOException {
        if (files.isEmpty()) return false;
        for (String file : files) {
            Path path = PreviewFiles.child(output, file);
            if (!Files.isRegularFile(path) || !PreviewFiles.sha256(path).equals(state.getProperty("owner." + file))) return false;
        }
        return true;
    }

    private void checkReplacement(String relative, String expected) throws IOException {
        Path target = PreviewFiles.child(output, relative);
        if (Files.exists(target) && !state.containsKey("owner." + relative)
                && (!Files.isRegularFile(target) || !expected.equals(PreviewFiles.sha256(target)))) {
            throw new IOException("Refusing to replace an unmanaged file: " + target);
        }
    }

    private void removeOwned(String relative) throws IOException {
        if (PreviewFiles.dataFile(relative)) throw new IOException("State cannot own learned data: " + relative);
        Path target = PreviewFiles.child(output, relative);
        String expected = state.getProperty("owner." + relative);
        if (expected == null) throw new IOException("State does not own obsolete file: " + relative);
        if (Files.exists(target) && !expected.equals(PreviewFiles.sha256(target))) {
            throw new IOException("Obsolete managed file was changed; preserve it elsewhere before retrying: " + target);
        }
        Files.deleteIfExists(target);
        state.remove("owner." + relative);
    }

    private static List<String> lines(String value) {
        return value.isEmpty() ? List.of() : Arrays.asList(value.split("\n"));
    }

    private static void replace(Path source, Path target) throws IOException {
        PreviewFiles.rejectLinks(target);
        Files.createDirectories(target.getParent());
        Path temp = Files.createTempFile(target.getParent(), ".bridge-copy-", ".tmp");
        try {
            Files.copy(source, temp, StandardCopyOption.REPLACE_EXISTING);
            PreviewFiles.move(temp, target);
            if (target.toString().endsWith(".sh")) target.toFile().setExecutable(true);
        } finally { Files.deleteIfExists(temp); }
    }
}
