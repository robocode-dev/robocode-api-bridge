import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class PreviewCommandTest {
    @TempDir Path temp;

    @Test
    void testPKG005_IntegrationPositive_UsesUnpackedCommandAndSourceLauncherFromPathsWithSpaces() throws Exception {
        Path home = PreviewTestSupport.unpack(temp.resolve("bridge package"));
        Path source = Files.createDirectories(temp.resolve("original collection"));
        Path output = temp.resolve("generated bots with spaces");
        PreviewTestSupport.robotJar(source, "original.jar", "initial", null);
        PreviewTestSupport.Result version = invoke(home, "--version");
        assertEquals(0, version.code, version.text);
        assertTrue(version.text.contains("test-preview"));
        assertTrue(version.text.contains("a".repeat(40)));
        PreviewTestSupport.Result help = invoke(home, "--help");
        assertEquals(0, help.code, help.text);
        assertTrue(help.text.contains("JDK 11"));
        PreviewTestSupport.Result imported = invoke(home, source.toString(), "--output", output.toString());
        assertEquals(0, imported.code, imported.text);
        assertTrue(imported.text.contains("Imported 1"));
        PreviewTestSupport.Result repeated = invoke(home, "--output", output.toString());
        assertEquals(0, repeated.code, repeated.text);
        assertTrue(repeated.text.contains("reused 1"));
        Path script = output.resolve("sample.Bot_1.0/sample.Bot_1.0" + (windows() ? ".cmd" : ".sh"));
        Map<String, String> environment = new HashMap<>();
        environment.put("JAVA_HOME", System.getProperty("java.home"));
        environment.put("JAVA_TOOL_OPTIONS", "-Dpreview.probe=true");
        String[] command = windows() ? new String[]{"cmd.exe", "/d", "/c", "call", script.toString()} : new String[]{"sh", script.toString()};
        PreviewTestSupport.Result launched = PreviewTestSupport.command(temp, environment, command);
        assertNotEquals(0, launched.code);
        assertTrue(launched.text.contains("PREVIEW_PROBE_LOADED"), launched.text);
        assertFalse(launched.text.contains("ClassNotFoundException"), launched.text);
        String guide = Files.readString(home.resolve("guide/README.md"));
        assertTrue(guide.contains("Classic Robocode does not need to be installed or running"));
        assertTrue(guide.contains("JDK"));
        assertTrue(guide.contains("Bot Root Directories"));
    }

    @Test
    void testPKG005_IntegrationNegative_ReportsBadArgumentsMissingJavaAndChangedLibraries() throws Exception {
        Path home = PreviewTestSupport.unpack(temp.resolve("package"));
        PreviewTestSupport.Result invalid = invoke(home, "--unexpected");
        assertEquals(2, invalid.code, invalid.text);
        assertTrue(invalid.text.contains("--help"));
        Path source = Files.createDirectories(temp.resolve("source"));
        PreviewTestSupport.robotJar(source, "bot.jar", "data", null);
        Properties metadata = PreviewFiles.load(home.resolve("preview.properties"));
        Files.delete(home.resolve(metadata.getProperty("runtime.0")));
        PreviewTestSupport.Result missing = invoke(home, source.toString(), "--output", temp.resolve("bots").toString());
        assertEquals(1, missing.code, missing.text);
        assertTrue(missing.text.contains("Unpack the complete archive"), missing.text);
        Map<String, String> environment = Map.of("JAVA_HOME", temp.resolve("no JDK").toString());
        PreviewTestSupport.Result noJava = PreviewTestSupport.command(temp, environment, launcher(home, "--help"));
        assertNotEquals(0, noJava.code);
        assertTrue(noJava.text.contains("JDK 11"), noJava.text);
    }

    private PreviewTestSupport.Result invoke(Path home, String... args) throws Exception {
        return PreviewTestSupport.command(temp, Map.of("JAVA_HOME", System.getProperty("java.home")), launcher(home, args));
    }

    private String[] launcher(Path home, String... args) {
        List<String> command = new ArrayList<>();
        if (windows()) command.addAll(List.of("cmd.exe", "/d", "/c", "call", home.resolve("bin/wrap-robots.cmd").toString()));
        else command.addAll(List.of("sh", home.resolve("bin/wrap-robots").toString()));
        command.addAll(Arrays.asList(args));
        return command.toArray(new String[0]);
    }

    private static boolean windows() { return System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("win"); }
}
