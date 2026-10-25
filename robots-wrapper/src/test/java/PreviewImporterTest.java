import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Assumptions;
import java.nio.file.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class PreviewImporterTest {
    @TempDir Path temp;

    @Test
    void testPKG003_IntegrationPositive_StagesDeduplicatedInputsAndPreservesTeamDataOnUpgrade() throws Exception {
        Path home = PreviewTestSupport.unpack(temp.resolve("package"));
        Path sources = Files.createDirectories(temp.resolve("LiteRumble robots"));
        Path jar = PreviewTestSupport.robotJar(sources.resolve("roborumble"), "Fixture.jar", "initial", "sample.Bot,sample.Bot");
        Path melee = Files.createDirectories(sources.resolve("meleerumble"));
        Files.copy(jar, melee.resolve("duplicate.jar"));
        Map<String, String> before = PreviewTestSupport.snapshot(sources);
        Path output = temp.resolve("generated bots");
        assertEquals(1, PreviewImporter.findJars(List.of(sources)).size());
        assertEquals(0, PreviewTestSupport.importer(home, output).importSources(List.of(sources)));
        assertTrue(Files.isRegularFile(output.resolve("sample.Bot_1.0/Wrapper.java")));
        assertTrue(Files.isRegularFile(output.resolve("FixtureTeam/FixtureTeam.json")));
        Path learned = output.resolve("sample.Bot_1.0/Bot.data/learning.txt");
        Path repeated = output.resolve("sample.Bot_1.0-FixtureTeam-member-2/Bot.data/learning.txt");
        assertEquals("initial", Files.readString(learned));
        Files.writeString(learned, "learned solo state");
        Files.writeString(repeated, "learned repeated-member state");
        Files.writeString(output.resolve("notes.txt"), "unowned notes");
        Properties metadata = PreviewFiles.load(home.resolve("preview.properties"));
        metadata.setProperty("version", "newer preview");
        PreviewFiles.save(home.resolve("preview.properties"), metadata);
        assertEquals(0, PreviewTestSupport.importer(home, output).importSources(List.of(sources)));
        assertEquals("learned solo state", Files.readString(learned));
        assertEquals("learned repeated-member state", Files.readString(repeated));
        assertEquals("unowned notes", Files.readString(output.resolve("notes.txt")));
        assertEquals(before, PreviewTestSupport.snapshot(sources));
        Properties state = PreviewImporter.loadState(output);
        assertEquals(1, state.stringPropertyNames().stream().filter(k -> k.startsWith("jar.") && k.endsWith(".package")).count());
        try (var libraries = Files.list(output.resolve("lib"))) { assertEquals(2, libraries.count()); }
    }

    @Test
    void testPKG003_IntegrationNegative_RejectsOverlapAndUnmanagedReplacement() throws Exception {
        Path home = PreviewTestSupport.unpack(temp.resolve("package"));
        Path source = Files.createDirectories(temp.resolve("source"));
        PreviewTestSupport.robotJar(source, "bot.jar", "initial", null);
        assertThrows(java.io.IOException.class, () -> PreviewTestSupport.importer(home, source.resolve("bots")).importSources(List.of(source)));
        Path output = Files.createDirectories(temp.resolve("output/sample.Bot_1.0"));
        Files.writeString(output.resolve("private.txt"), "preserve");
        assertEquals(1, PreviewTestSupport.importer(home, output.getParent()).importSources(List.of(source)));
        assertEquals("preserve", Files.readString(output.resolve("private.txt")));
        assertFalse(Files.exists(output.resolve("Wrapper.java")));
    }

    @Test
    void testPKG003_IntegrationNegative_RejectsSymbolicLinkDestinations() throws Exception {
        Path real = Files.createDirectories(temp.resolve("real"));
        Path link = temp.resolve("redirected");
        try { Files.createSymbolicLink(link, real); }
        catch (UnsupportedOperationException | java.io.IOException denied) {
            if (System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("win")) {
                PreviewTestSupport.Result junction = PreviewTestSupport.command(temp, Map.of(), "cmd.exe", "/d", "/c", "mklink", "/J", link.toString(), real.toString());
                Assumptions.assumeTrue(junction.code == 0, "Creating symlinks and junctions is unavailable: " + junction.text);
            } else Assumptions.assumeTrue(false, "Creating symbolic links is unavailable: " + denied.getMessage());
        }
        try { assertThrows(java.io.IOException.class, () -> PreviewFiles.rejectLinks(link.resolve("bot/file"))); }
        finally { Files.deleteIfExists(link); }
    }

    @Test
    void testPKG004_IntegrationNegative_RejectsClassNamesThatCouldEscapeStaging() throws Exception {
        Path home = PreviewTestSupport.unpack(temp.resolve("package"));
        Path source = Files.createDirectories(temp.resolve("source"));
        try (var jar = new java.util.jar.JarOutputStream(Files.newOutputStream(source.resolve("unsafe.jar")))) {
            PreviewTestSupport.entry(jar, "unsafe.properties", "robot.classname=..outside\n".getBytes(java.nio.charset.StandardCharsets.UTF_8));
            PreviewTestSupport.entry(jar, "//outside.class", new byte[]{0});
        }
        Path output = temp.resolve("bots");
        assertEquals(1, PreviewTestSupport.importer(home, output).importSources(List.of(source)));
        assertFalse(Files.exists(temp.resolve("outside.class")));
    }

    @Test
    void testPKG004_IntegrationNegative_RejectsDelimiterOnlyTeamsWithoutReplacingAnInstalledBot() throws Exception {
        Path home = PreviewTestSupport.unpack(temp.resolve("package"));
        Path valid = Files.createDirectories(temp.resolve("valid"));
        PreviewTestSupport.robotJar(valid, "original.jar", "learned data", null);
        Path output = temp.resolve("bots");
        assertEquals(0, PreviewTestSupport.importer(home, output).importSources(List.of(valid)));
        Map<String, String> installed = PreviewTestSupport.snapshot(output.resolve("sample.Bot_1.0"));
        int index = 0;
        for (String members : List.of(",", " , , ")) {
            Path bad = Files.createDirectories(temp.resolve("bad-team-" + index++));
            PreviewTestSupport.robotJar(bad, "bad.jar", "replacement", members);
            assertEquals(1, PreviewTestSupport.importer(home, output).importSources(List.of(bad)));
            assertEquals(installed, PreviewTestSupport.snapshot(output.resolve("sample.Bot_1.0")));
            assertFalse(Files.exists(output.resolve("FixtureTeam")));
        }
    }

    @Test
    void testPKG004_IntegrationNegative_RejectsAmbiguousNestedClassesAcrossVersions() throws Exception {
        Path home = PreviewTestSupport.unpack(temp.resolve("package"));
        Path inputs = Files.createDirectories(temp.resolve("nested inputs"));
        Path one = PreviewTestSupport.robotJar(inputs, "one.jar", "first implementation", null);
        Path two = inputs.resolve("two.jar");
        try (var input = new java.util.zip.ZipFile(one.toFile());
             var result = new java.util.jar.JarOutputStream(Files.newOutputStream(two))) {
            var entries = input.entries();
            while (entries.hasMoreElements()) {
                var entry = entries.nextElement();
                byte[] data = input.getInputStream(entry).readAllBytes();
                if (entry.getName().endsWith(".properties")) data = new String(data, java.nio.charset.StandardCharsets.UTF_8)
                        .replace("robot.version=1.0", "robot.version=2.0").getBytes(java.nio.charset.StandardCharsets.UTF_8);
                PreviewTestSupport.entry(result, entry.getName(), data);
            }
        }
        Path source = Files.createDirectories(temp.resolve("source"));
        try (var jar = new java.util.jar.JarOutputStream(Files.newOutputStream(source.resolve("ambiguous-team.jar")))) {
            PreviewTestSupport.entry(jar, "one.jar", Files.readAllBytes(one));
            PreviewTestSupport.entry(jar, "two.jar", Files.readAllBytes(two));
            PreviewTestSupport.entry(jar, "Ambiguous.team", "team.members=sample.Bot 1.0,sample.Bot 2.0\n".getBytes(java.nio.charset.StandardCharsets.UTF_8));
        }
        Path output = temp.resolve("bots");
        assertEquals(1, PreviewTestSupport.importer(home, output).importSources(List.of(source)));
        assertFalse(Files.exists(output.resolve("sample.Bot_1.0")));
        assertFalse(Files.exists(output.resolve("sample.Bot_2.0")));
        assertFalse(Files.exists(output.resolve("Ambiguous")));
    }

    @Test
    void testPKG004_IntegrationPositive_SeparatesTeamIdentityFromItsMultilineDescription() throws Exception {
        Path team = Files.createDirectories(temp.resolve("ActualTeam"));
        String description = "   A sample team.\nA long description that is not a team identity.";
        Main.createTeamJsonFile(team, "ActualTeam", description, "9.7", "Team author", List.of("member"));
        String json = Files.readString(team.resolve("ActualTeam.json"));
        assertTrue(json.contains("\"name\": \"ActualTeam\""));
        assertTrue(json.contains("\"description\": \"" + Main.escape(description) + "\""));
    }

    @Test
    void testPKG004_IntegrationPositive_ImportsCompleteRepeatedMemberTeam() throws Exception {
        Path home = PreviewTestSupport.unpack(temp.resolve("package"));
        Path source = Files.createDirectories(temp.resolve("source"));
        PreviewTestSupport.robotJar(source, "team.jar", "data", "sample.Bot,sample.Bot");
        Path output = temp.resolve("bots");
        assertEquals(0, PreviewTestSupport.importer(home, output).importSources(List.of(source)));
        String json = Files.readString(output.resolve("FixtureTeam/FixtureTeam.json"));
        assertTrue(json.contains("sample.Bot_1.0\""));
        assertTrue(json.contains("sample.Bot_1.0-FixtureTeam-member-2"));
        assertTrue(Files.isRegularFile(output.resolve("sample.Bot_1.0-FixtureTeam-member-2/Wrapper.java")));
    }

    @Test
    void testPKG004_IntegrationNegative_RejectsIncompleteInvalidAndConflictingJars() throws Exception {
        Path home = PreviewTestSupport.unpack(temp.resolve("package"));
        Path incomplete = Files.createDirectories(temp.resolve("incomplete"));
        PreviewTestSupport.robotJar(incomplete, "team.jar", "data", "sample.Bot,sample.Missing");
        Path output = temp.resolve("bots");
        assertEquals(1, PreviewTestSupport.importer(home, output).importSources(List.of(incomplete)));
        assertFalse(Files.exists(output.resolve("FixtureTeam")));
        assertFalse(Files.exists(output.resolve("sample.Bot_1.0")));
        Path source = Files.createDirectories(temp.resolve("valid"));
        PreviewTestSupport.robotJar(source, "original.jar", "original", null);
        assertEquals(0, PreviewTestSupport.importer(home, output).importSources(List.of(source)));
        String installed = Files.readString(output.resolve("sample.Bot_1.0/Bot.data/learning.txt"));
        Path conflict = Files.createDirectories(temp.resolve("conflict"));
        PreviewTestSupport.robotJar(conflict, "different.jar", "different", null);
        Files.writeString(conflict.resolve("corrupt.jar"), "not a zip");
        assertEquals(2, PreviewTestSupport.importer(home, output).importSources(List.of(conflict)));
        assertEquals(installed, Files.readString(output.resolve("sample.Bot_1.0/Bot.data/learning.txt")));
    }
}
