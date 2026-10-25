import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipFile;
import javax.tools.ToolProvider;

final class PreviewTestSupport {
    static final Path ROOT = Path.of(System.getProperty("preview.root"));
    static final Path WRAPPER = Path.of(System.getProperty("preview.wrapper"));
    static final Path ADAPTER = Path.of(System.getProperty("preview.adapter"));
    static final List<Path> DEPENDENCIES = Arrays.stream(System.getProperty("preview.dependencies").split(java.util.regex.Pattern.quote(File.pathSeparator)))
            .map(Path::of).collect(java.util.stream.Collectors.toList());
    static final String BOT_API = DEPENDENCIES.stream().map(p -> p.getFileName().toString())
            .filter(n -> n.startsWith("robocode-tankroyale-bot-api-")).findFirst().orElseThrow()
            .replace("robocode-tankroyale-bot-api-", "").replace(".jar", "");

    static Path archive(Path directory) throws Exception {
        return PreviewArchive.build(ROOT, directory, "a".repeat(40), "test-preview", BOT_API, WRAPPER, ADAPTER, DEPENDENCIES);
    }

    static Path unpack(Path directory) throws Exception {
        Path archive = archive(directory.resolve("archives"));
        Path extract = directory.resolve("unpacked preview with spaces");
        try (ZipFile zip = new ZipFile(archive.toFile())) {
            var entries = zip.entries();
            while (entries.hasMoreElements()) {
                var entry = entries.nextElement();
                Path target = extract.resolve(entry.getName());
                Files.createDirectories(target.getParent());
                try (InputStream in = zip.getInputStream(entry)) { Files.copy(in, target); }
            }
        }
        return extract.resolve("robocode-api-bridge-test-preview");
    }

    static Path robotJar(Path directory, String name, String data, String teamMembers) throws Exception {
        Files.createDirectories(directory);
        Path compile = Files.createTempDirectory(directory.getParent(), "fixture-compile-");
        try {
            Path source = compile.resolve("sample/Bot.java");
            Files.createDirectories(source.getParent());
            Files.writeString(source, "package sample; public class Bot extends robocode.Robot {"
                    + " static { if (Boolean.getBoolean(\"preview.probe\")) { System.err.println(\"PREVIEW_PROBE_LOADED\");"
                    + " throw new IllegalStateException(\"fixture stops before network\"); } }"
                    + " public void run() { while(true) { ahead(50); turnRight(90); } } }");
            ByteArrayOutputStream errors = new ByteArrayOutputStream();
            int result = ToolProvider.getSystemJavaCompiler().run(null, errors, errors, "--release", "11", "-cp", WRAPPER.toString(), "-d", compile.toString(), source.toString());
            if (result != 0) throw new IOException("Fixture compilation failed: " + errors.toString(StandardCharsets.UTF_8));
            Path jar = directory.resolve(name);
            try (JarOutputStream output = new JarOutputStream(Files.newOutputStream(jar))) {
                entry(output, "sample/Bot.class", Files.readAllBytes(compile.resolve("sample/Bot.class")));
                entry(output, "sample/Bot.properties", "robot.classname=sample.Bot\nrobot.version=1.0\nrobot.author.name=Preview tests\n".getBytes(StandardCharsets.UTF_8));
                entry(output, "sample/Bot.data/learning.txt", data.getBytes(StandardCharsets.UTF_8));
                if (teamMembers != null) entry(output, "sample/FixtureTeam.team", ("team.members=" + teamMembers + "\nteam.description=Fixture team\nteam.version=1.0\n").getBytes(StandardCharsets.UTF_8));
            }
            return jar;
        } finally { PreviewFiles.removeTree(compile); }
    }

    static void entry(JarOutputStream output, String name, byte[] bytes) throws IOException {
        JarEntry entry = new JarEntry(name);
        entry.setTimeLocal(java.time.LocalDateTime.of(1980, 1, 1, 0, 0));
        output.putNextEntry(entry);
        output.write(bytes);
        output.closeEntry();
    }

    static PreviewImporter importer(Path home, Path output) throws IOException {
        PrintStream sink = new PrintStream(new ByteArrayOutputStream());
        return new PreviewImporter(home, output, sink, sink);
    }

    static Map<String, String> snapshot(Path root) throws IOException {
        Map<String, String> result = new TreeMap<>();
        try (var paths = Files.walk(root)) {
            for (Path path : (Iterable<Path>) paths.filter(Files::isRegularFile)::iterator) result.put(root.relativize(path).toString(), PreviewFiles.sha256(path));
        }
        return result;
    }

    static Result command(Path cwd, Map<String, String> environment, String... command) throws Exception {
        Path log = Files.createTempFile(cwd, "command-", ".log");
        ProcessBuilder builder = new ProcessBuilder(command).directory(cwd.toFile()).redirectErrorStream(true).redirectOutput(log.toFile());
        builder.environment().remove("CLASSPATH");
        builder.environment().remove("JAVA_TOOL_OPTIONS");
        builder.environment().remove("JDK_JAVA_OPTIONS");
        builder.environment().putAll(environment);
        Process process = builder.start();
        if (!process.waitFor(45, TimeUnit.SECONDS)) {
            process.descendants().forEach(ProcessHandle::destroyForcibly);
            process.destroyForcibly();
            throw new IOException("Command timed out: " + Arrays.toString(command));
        }
        String output = Files.readString(log);
        Files.delete(log);
        return new Result(process.exitValue(), output);
    }

    static final class Result {
        final int code;
        final String text;
        Result(int code, String text) { this.code = code; this.text = text; }
    }
}
