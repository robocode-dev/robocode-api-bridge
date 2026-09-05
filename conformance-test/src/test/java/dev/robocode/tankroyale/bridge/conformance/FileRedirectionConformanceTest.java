package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Acceptance evidence for FIO-001 and FIO-002 — a root-relative path a robot names is
 * redirected into its data directory, and {@code getDataFile} and {@code getDataDirectory}
 * agree about where that directory is.
 */
class FileRedirectionConformanceTest extends ConformanceTestBase {

    private static final String ROBOT = "conformance.probes.FileRedirectionProbe";
    private static final Path SOURCE = ConformanceHarness.repoRoot().resolve(Path.of(
            "compat-test", "conformance-robots", "conformance", "probes", "FileRedirectionProbe.java"));

    @Test
    @DisplayName("FIO-001: a root-relative path is redirected into the data directory")
    void testFIO001_IntegrationPositive_RootRelativePathIsRedirectedIntoDataDirectory() {
        assertOnBothEngines(ROBOT, SOURCE, (outcome, engine) -> {
            assertTrue(outcome.anyConsoleContains("WriteSucceeded:/root-relative-name.txt:true"),
                    () -> "the root-relative write did not succeed on " + engine
                            + " (" + outcome.summary() + ")");
            assertTrue(outcome.anyConsoleContains("DirectoryListing:root-relative-name.txt"),
                    () -> "the redirected file did not land in the data directory on " + engine
                            + " (" + outcome.summary() + ")");
        });
    }

    @Test
    @DisplayName("FIO-001 negative: the redirected name is not written where it was named")
    void testFIO001_IntegrationNegative_NothingIsWrittenAtTheNamedPath() {
        assertOnBothEngines(ROBOT, SOURCE, (outcome, engine) -> {
            for (String console : outcome.consoles()) {
                for (String line : console.split("\\R")) {
                    if (line.startsWith("Resolved:/root-relative-name.txt:")) {
                        String resolved = line.substring("Resolved:/root-relative-name.txt:".length());
                        assertFalse(resolved.equals("/root-relative-name.txt")
                                        || resolved.equals("\\root-relative-name.txt"),
                                () -> "the name resolved to the unredirected path on " + engine + ": " + line);
                    }
                }
            }
        });
    }

    @Test
    @DisplayName("FIO-002: getDataFile and getDataDirectory resolve against the same place")
    void testFIO002_IntegrationPositive_DataFileAndDataDirectoryAgree() {
        assertOnBothEngines(ROBOT, SOURCE, (outcome, engine) -> {
            assertTrue(outcome.anyConsoleContains("WriteSucceeded:plain-name.txt:true"),
                    () -> "the plain-name write did not succeed on " + engine
                            + " (" + outcome.summary() + ")");
            assertTrue(outcome.anyConsoleContains("DirectoryListing:plain-name.txt"),
                    () -> "getDataDirectory's listing did not see what getDataFile wrote on " + engine
                            + " (" + outcome.summary() + ")");
        });
    }

    @Test
    @DisplayName("FIO-002 negative: the directory listing reports no file the probe did not write")
    void testFIO002_IntegrationNegative_DirectoryListingReportsNoUnwrittenFile() {
        assertOnBothEngines(ROBOT, SOURCE, (outcome, engine) ->
                assertFalse(outcome.anyConsoleContains("DirectoryListing:none"),
                        () -> "the data directory listing came back empty on " + engine
                                + " (" + outcome.summary() + ")"));
    }
}
