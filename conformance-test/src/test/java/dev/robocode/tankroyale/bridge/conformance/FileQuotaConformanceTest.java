package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Acceptance evidence for FIO-003 — a robot's data directory is capped at the classic
 * 200000-byte size limit, refused at the same point classic refuses it.
 */
class FileQuotaConformanceTest extends ConformanceTestBase {

    private static final String ROBOT = "conformance.probes.FileQuotaProbe";
    private static final Path SOURCE = ConformanceHarness.repoRoot().resolve(Path.of(
            "compat-test", "conformance-robots", "conformance", "probes", "FileQuotaProbe.java"));

    @Test
    @DisplayName("FIO-003: a write past the quota is refused with classic's own message")
    void testFIO003_IntegrationPositive_WritePastQuotaIsRefused() {
        assertOnBothEngines(ROBOT, SOURCE, (outcome, engine) -> {
            assertTrue(outcome.anyConsoleContains("DataQuota:200000"),
                    () -> "the documented quota was not reported as 200000 on " + engine
                            + " (" + outcome.summary() + ")");
            assertTrue(outcome.anyConsoleContains("WroteChunk:0") && outcome.anyConsoleContains("WroteChunk:1"),
                    () -> "the first two chunks, which fit inside the quota, did not both write on " + engine
                            + " (" + outcome.summary() + ")");
            assertTrue(outcome.anyConsoleContains("QuotaExceeded:")
                            && outcome.anyConsoleContains("200000 bytes"),
                    () -> "the third chunk was not refused with the classic quota message on " + engine
                            + " (" + outcome.summary() + ")");
        });
    }

    @Test
    @DisplayName("FIO-003 negative: the write that exceeds the quota does not also succeed")
    void testFIO003_IntegrationNegative_TheRefusedChunkDoesNotAlsoReportSuccess() {
        assertOnBothEngines(ROBOT, SOURCE, (outcome, engine) ->
                assertFalse(outcome.anyConsoleContains("WroteChunk:2"),
                        () -> "the third, quota-exceeding chunk reported as written on " + engine
                                + " (" + outcome.summary() + ")"));
    }
}
