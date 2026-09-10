package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Acceptance evidence for FIO-005 — classic permits five open robot file streams, refuses the
 * sixth, and permits a new stream after one is closed.
 */
class FileStreamLimitConformanceTest extends ConformanceTestBase {

    private static final String ROBOT = "conformance.probes.FileStreamLimitProbe";
    private static final Path SOURCE = ConformanceHarness.repoRoot().resolve(Path.of(
            "compat-test", "conformance-robots", "conformance", "probes", "FileStreamLimitProbe.java"));

    @Test
    @DisplayName("FIO-005: the sixth open robot file stream is refused")
    void testFIO005_IntegrationPositive_SixthStreamIsRefused() {
        assertOnBothEngines(ROBOT, SOURCE, (outcome, engine) -> {
            assertTrue(outcome.anyConsoleContains("Opened:5"),
                    () -> "five streams did not open on " + engine + " (" + outcome.summary() + ")");
            assertTrue(outcome.anyConsoleContains("StreamLimit:You may only have 5 streams open at a time."),
                    () -> "the sixth stream was not refused with classic's message on " + engine
                            + " (" + outcome.summary() + ")");
        });
    }

    @Test
    @DisplayName("FIO-005 negative: closing streams releases their open-stream slots")
    void testFIO005_IntegrationNegative_ClosedStreamCanBeReopened() {
        assertOnBothEngines(ROBOT, SOURCE, (outcome, engine) -> {
            assertFalse(outcome.anyConsoleContains("Opened:6"),
                    () -> "six streams opened simultaneously on " + engine + " ("
                            + outcome.summary() + ")");
            assertTrue(outcome.anyConsoleContains("Reopened:true"),
                    () -> "a closed stream did not release its slot on " + engine + " ("
                            + outcome.summary() + ")");
        });
    }
}
