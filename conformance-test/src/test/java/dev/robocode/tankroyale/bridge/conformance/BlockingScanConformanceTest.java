package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** Acceptance evidence for EVT-002 — pending same-priority scans survive a blocking handler. */
class BlockingScanConformanceTest extends ConformanceTestBase {

    private static final String ROBOT = "conformance.probes.BlockingScanProbe";
    private static final String ENEMY = "sample.Target";
    private static final String SCAN = "BlockingScanDelivered!";
    private static final Path SOURCE = ConformanceHarness.repoRoot().resolve(Path.of(
            "compat-test", "conformance-robots", "conformance", "probes", "BlockingScanProbe.java"));

    @Test
    @DisplayName("EVT-002: pending same-priority scans are delivered after a blocking handler")
    void testEVT002_IntegrationPositive_PendingScansSurviveBlockingHandler() {
        assertOnBothEngines(ROBOT, SOURCE, ENEMY, (outcome, engine) ->
                assertTrue(outcome.countOf(SCAN) >= 2,
                        () -> "fewer than two scan callbacks reached the blocking probe on " + engine
                                + " (" + outcome.summary() + ")"));
    }

    @Test
    @DisplayName("EVT-002 negative: a completed bridge run does not lose all pending scans")
    void testEVT002_IntegrationNegative_BridgeDoesNotDiscardPendingScans() {
        assertOnBothEngines(ROBOT, SOURCE, ENEMY, (outcome, engine) ->
                assertTrue(outcome.anyConsoleContains(SCAN),
                        () -> "the scan handler was never entered on " + engine
                                + " (" + outcome.summary() + ")"));
    }
}
