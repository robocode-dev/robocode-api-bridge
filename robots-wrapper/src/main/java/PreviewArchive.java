import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/** Assembles an offline-use archive; publication is deliberately a separate operation. */
public final class PreviewArchive {
    public static void main(String[] args) throws Exception {
        Path root = Path.of(args[0]);
        requireClean(root, false);
        String revision = git(root, "rev-parse", "HEAD").trim();
        String version = args[2].isBlank() ? args[3] + "-preview.g" + revision.substring(0, 12) : args[2];
        List<Path> dependencies = new ArrayList<>();
        for (int i = 7; i < args.length; i++) dependencies.add(Path.of(args[i]));
        Path zip = build(root, Path.of(args[1]), revision, version, args[4], Path.of(args[5]), Path.of(args[6]), dependencies);
        System.out.println("Prepared " + zip);
        System.out.println("SHA-256: " + PreviewFiles.sha256(zip));
    }

    static void requireClean(Path root, boolean localOverride) throws IOException, InterruptedException {
        if (localOverride) throw new IOException("Preview candidates cannot use tankRoyaleBotApiVersion overrides");
        if (!git(root, "status", "--porcelain", "--untracked-files=normal").isBlank()) {
            throw new IOException("Commit all intended source changes before building a revision-pinned preview candidate");
        }
    }

    private static String git(Path root, String... args) throws IOException, InterruptedException {
        List<String> command = new ArrayList<>(List.of("git", "-C", root.toString()));
        command.addAll(Arrays.asList(args));
        Process process = new ProcessBuilder(command).redirectErrorStream(true).start();
        String result = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        if (process.waitFor() != 0) throw new IOException("Cannot identify a clean Git source revision: " + result);
        return result;
    }

    static Path build(Path root, Path destination, String revision, String version, String botApiVersion,
                      Path wrapper, Path adapter, List<Path> dependencies) throws IOException {
        if (!revision.matches("[0-9a-f]{40}")) throw new IOException("A full Git revision is required");
        if (!version.matches("[A-Za-z0-9][A-Za-z0-9._+-]*")) throw new IOException("Invalid preview version: " + version);
        Path dist = root.resolve("robots-wrapper/src/dist");
        Properties metadata = PreviewFiles.load(dist.resolve("compatibility.properties"));
        if (!botApiVersion.equals(metadata.getProperty("notices.botApiVersion"))) {
            throw new IOException("Refresh the embedded-dependency notice inventory for Bot API " + botApiVersion);
        }
        metadata.setProperty("version", version);
        metadata.setProperty("revision", revision);
        metadata.setProperty("botApi.version", botApiVersion);
        Map<String, byte[]> files = new TreeMap<>();
        addTree(files, dist.resolve("bin"), "bin/");
        addTree(files, dist.resolve("licenses"), "licenses/");
        files.put("LICENSE", Files.readAllBytes(root.resolve("LICENSE")));
        files.put("THIRD-PARTY.md", Files.readAllBytes(dist.resolve("THIRD-PARTY.md")));
        files.put("guide/README.md", Files.readAllBytes(root.resolve("guide/README.md")));
        files.put("README.md", ("# Robocode API bridge " + version + "\n\n"
                + "Read [the installation and first-battle guide](guide/README.md).\n\n"
                + "Requires a full JDK " + metadata.getProperty("java.minimum") + " or newer. Classic Robocode is not required.\n"
                + "Tank Royale minimum: " + metadata.getProperty("tankRoyale.minimum") + "; newer stable versions in major "
                + metadata.getProperty("tankRoyale.major") + " are expected compatible. See the guide for tested combinations.\n\n"
                + "Windows: `bin\\wrap-robots.cmd \"C:\\your robot collection\"`\n\n"
                + "The manifest identifies the exact source revision and dependency bytes. SHA256SUMS checks the unpacked contents.\n")
                .getBytes(StandardCharsets.UTF_8));
        files.put("tools/" + wrapper.getFileName(), Files.readAllBytes(wrapper));
        files.put("runtime/lib/" + adapter.getFileName(), Files.readAllBytes(adapter));
        Path botApi = null;
        int dep = 0;
        for (Path dependency : dependencies) {
            if (dependency.equals(adapter)) continue;
            if (dependency.getFileName().toString().equals("robocode-tankroyale-bot-api-" + botApiVersion + ".jar")) botApi = dependency;
            metadata.setProperty("dependency." + dep + ".file", dependency.getFileName().toString());
            metadata.setProperty("dependency." + dep + ".coordinates", coordinates(dependency, botApiVersion));
            metadata.setProperty("dependency." + dep++ + ".sha256", PreviewFiles.sha256(dependency));
            copyNotices(files, dependency);
        }
        if (botApi == null) throw new IOException("Published Bot API jar is absent from the resolved runtime");
        files.put("runtime/lib/" + botApi.getFileName(), Files.readAllBytes(botApi));
        metadata.setProperty("runtime.count", "2");
        metadata.setProperty("runtime.0", "runtime/lib/" + adapter.getFileName());
        metadata.setProperty("runtime.1", "runtime/lib/" + botApi.getFileName());
        Map<String, byte[]> sources = new TreeMap<>();
        addTree(sources, root.resolve("robocode-api/src/main/java"), "robocode-api/");
        addTree(sources, root.resolve("robots-wrapper/src/main/java"), "robots-wrapper/");
        sources.put("LICENSE", files.get("LICENSE"));
        sources.put("classic-EPL-1.0.txt", files.get("licenses/classic-EPL-1.0.txt"));
        files.put("sources/bridge-sources.zip", zipBytes(sources, ""));
        for (Map.Entry<String, byte[]> file : files.entrySet()) metadata.setProperty("sha256." + file.getKey(), PreviewFiles.sha256(file.getValue()));
        metadata.setProperty("dependency.count", Integer.toString(dep));
        files.put("preview.properties", propertiesBytes(metadata));
        StringBuilder sums = new StringBuilder();
        for (Map.Entry<String, byte[]> file : files.entrySet()) sums.append(PreviewFiles.sha256(file.getValue())).append("  ").append(file.getKey()).append('\n');
        files.put("SHA256SUMS", sums.toString().getBytes(StandardCharsets.UTF_8));
        Files.createDirectories(destination);
        String name = "robocode-api-bridge-" + version;
        Path archive = destination.resolve(name + ".zip");
        Files.write(archive, zipBytes(files, name + "/"));
        Files.writeString(destination.resolve(name + ".zip.sha256"), PreviewFiles.sha256(archive) + "  " + archive.getFileName() + "\n");
        return archive;
    }

    private static void addTree(Map<String, byte[]> files, Path root, String prefix) throws IOException {
        if (!Files.isDirectory(root)) throw new IOException("Missing package input: " + root);
        try (var paths = Files.walk(root)) {
            for (Path path : (Iterable<Path>) paths.filter(Files::isRegularFile).sorted()::iterator) {
                byte[] content = Files.readAllBytes(path);
                if (path.toString().endsWith(".cmd")) {
                    content = new String(content, StandardCharsets.UTF_8).replace("\r\n", "\n").replace("\n", "\r\n").getBytes(StandardCharsets.UTF_8);
                }
                files.put(prefix + root.relativize(path).toString().replace('\\', '/'), content);
            }
        }
    }

    private static void copyNotices(Map<String, byte[]> files, Path jar) throws IOException {
        try (ZipFile zip = new ZipFile(jar.toFile())) {
            var entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String upper = entry.getName().toUpperCase(Locale.ROOT);
                if (!entry.isDirectory() && upper.startsWith("META-INF/")
                        && (upper.contains("LICENSE") || upper.contains("NOTICE") || upper.contains("COPYRIGHT"))) {
                    try (InputStream input = zip.getInputStream(entry)) {
                        files.put("licenses/" + jar.getFileName() + "/" + entry.getName(), input.readAllBytes());
                    }
                }
            }
        }
    }

    private static String coordinates(Path jar, String botApiVersion) throws IOException {
        if (jar.getFileName().toString().equals("robocode-tankroyale-bot-api-" + botApiVersion + ".jar")) {
            return "dev.robocode.tankroyale:robocode-tankroyale-bot-api:" + botApiVersion;
        }
        try (ZipFile zip = new ZipFile(jar.toFile())) {
            var entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.getName().startsWith("META-INF/maven/") && entry.getName().endsWith("/pom.properties")) {
                    Properties pom = new Properties();
                    try (InputStream input = zip.getInputStream(entry)) { pom.load(input); }
                    return pom.getProperty("groupId") + ":" + pom.getProperty("artifactId") + ":" + pom.getProperty("version");
                }
            }
        }
        throw new IOException("Dependency coordinates are missing from " + jar);
    }

    private static byte[] propertiesBytes(Properties values) throws IOException {
        StringWriter text = new StringWriter();
        values.store(text, null);
        List<String> lines = new ArrayList<>();
        for (String line : text.toString().split("\\R")) if (!line.startsWith("#")) lines.add(line);
        Collections.sort(lines);
        return (String.join("\n", lines) + "\n").getBytes(StandardCharsets.ISO_8859_1);
    }

    private static byte[] zipBytes(Map<String, byte[]> files, String prefix) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        try (ZipOutputStream zip = new ZipOutputStream(result)) {
            for (Map.Entry<String, byte[]> file : files.entrySet()) {
                ZipEntry entry = new ZipEntry(prefix + file.getKey());
                entry.setTimeLocal(java.time.LocalDateTime.of(1980, 1, 1, 0, 0));
                zip.putNextEntry(entry);
                zip.write(file.getValue());
                zip.closeEntry();
            }
        }
        return result.toByteArray();
    }
}
