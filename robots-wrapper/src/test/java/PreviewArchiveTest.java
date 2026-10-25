import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.zip.ZipFile;
import static org.junit.jupiter.api.Assertions.*;

class PreviewArchiveTest {
    @TempDir Path temp;

    @Test
    void testPKG001_IntegrationPositive_ContainsCompletePayloadAndVerifiableHashes() throws Exception {
        Path archive = PreviewTestSupport.archive(temp);
        String prefix = "robocode-api-bridge-test-preview/";
        try (ZipFile zip = new ZipFile(archive.toFile())) {
            for (String path : List.of("bin/wrap-robots.cmd", "bin/wrap-robots", "guide/README.md", "THIRD-PARTY.md", "sources/bridge-sources.zip", "licenses/classic-EPL-1.0.txt")) {
                assertNotNull(zip.getEntry(prefix + path), path);
            }
            Properties metadata = new Properties();
            metadata.load(zip.getInputStream(zip.getEntry(prefix + "preview.properties")));
            assertEquals("a".repeat(40), metadata.getProperty("revision"));
            assertEquals("2", metadata.getProperty("runtime.count"));
            assertTrue(metadata.stringPropertyNames().stream().anyMatch(k -> k.endsWith(".coordinates") && metadata.getProperty(k).startsWith("org.apache.bcel:bcel:")));
            String sums = new String(zip.getInputStream(zip.getEntry(prefix + "SHA256SUMS")).readAllBytes(), StandardCharsets.UTF_8);
            for (String line : sums.split("\n")) {
                String name = line.substring(66);
                assertEquals(line.substring(0, 64), PreviewFiles.sha256(zip.getInputStream(zip.getEntry(prefix + name)).readAllBytes()), name);
            }
            assertTrue(sums.contains("runtime/lib/robocode-api-"));
            assertTrue(sums.contains("runtime/lib/robocode-tankroyale-bot-api-"));
        }
        String before = PreviewFiles.sha256(archive);
        assertEquals(before, PreviewFiles.sha256(PreviewTestSupport.archive(temp)));
        assertTrue(Files.readString(temp.resolve(archive.getFileName() + ".sha256")).startsWith(before));
    }

    @Test
    void testPKG001_IntegrationNegative_RejectsDirtyInputsLocalOverridesAndStaleNotices() throws Exception {
        assertThrows(java.io.IOException.class, () -> PreviewArchive.requireClean(temp, true));
        PreviewTestSupport.Result init = PreviewTestSupport.command(temp, Map.of(), "git", "init");
        assertEquals(0, init.code, init.text);
        Files.writeString(temp.resolve("uncommitted.txt"), "input");
        assertThrows(java.io.IOException.class, () -> PreviewArchive.requireClean(temp, false));
        assertThrows(java.io.IOException.class, () -> PreviewArchive.build(PreviewTestSupport.ROOT, temp, "a".repeat(40), "test", "other-version", PreviewTestSupport.WRAPPER, PreviewTestSupport.ADAPTER, PreviewTestSupport.DEPENDENCIES));
    }
}
