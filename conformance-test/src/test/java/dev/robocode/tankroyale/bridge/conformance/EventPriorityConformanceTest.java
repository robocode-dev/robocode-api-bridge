package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Acceptance evidence for EVT-015 — the classic event-priority filter expectation.
 *
 * The probe moves until it hits a wall, then blocks while turning its radar. Its lower-priority
 * scan handler prints a marker only if it is entered during that handler. Classic's authoritative
 * EventPriorityFilter test asserts the same boundary through its observable marker; this probe
 * makes the handler window explicit for an unseeded Tank Royale battle.
 */
class EventPriorityConformanceTest extends ConformanceTestBase {

    private static final String ROBOT = "conformance.probes.EventPriorityProbe";
    private static final String ENEMY = "sample.Target";
    private static final String SCAN_OBSERVED = "ScanObserved!!!";
    private static final String SCANNED = "ScannedDuringWallHandler!!!";
    private static final java.nio.file.Path SOURCE = ConformanceHarness.repoRoot()
            .resolve("compat-test/conformance-robots/conformance/probes/EventPriorityProbe.java");

    @Test
    @DisplayName("EVT-015: a lower-priority scan is suppressed on both engines")
    void testEVT015_IntegrationPositive_LowerPriorityScanIsSuppressedOnBothEngines() {
        assertOnBothEngines(ROBOT, SOURCE, ENEMY, (outcome, engine) -> {
                assertTrue(outcome.anyConsoleContains(SCAN_OBSERVED),
                        () -> "the priority probe observed no scan on " + engine
                                + " (" + outcome.summary() + ")");
                assertFalse(outcome.anyConsoleContains(SCANNED),
                        () -> "the lower-priority scan handler ran on " + engine
                                + " (" + outcome.summary() + ")");
        });
    }

    @Test
    @DisplayName("EVT-015 negative: the bridge does not report a scan classic did not see")
    void testEVT015_IntegrationNegative_BridgeDoesNotReportAScanClassicDidNotSee() {
        BattleOutcome classic = outcomeFor(Engine.CLASSIC, ROBOT, SOURCE, ENEMY);
        BattleOutcome bridge = outcomeFor(Engine.BRIDGE, ROBOT, SOURCE, ENEMY);

        assertTrue(classic.completed(), () -> "the classic priority-probe battle did not complete ("
                + classic.summary() + ")");
        assertTrue(bridge.completed(), () -> "the bridge priority-probe battle did not complete ("
                + bridge.summary() + ")");
        assertTrue(classic.anyConsoleContains(SCAN_OBSERVED),
                () -> "the classic priority probe observed no scan (" + classic.summary() + ")");
        assertTrue(bridge.anyConsoleContains(SCAN_OBSERVED),
                () -> "the bridge priority probe observed no scan (" + bridge.summary() + ")");
        assertFalse(classic.anyConsoleContains(SCANNED),
                () -> "the classic priority-probe baseline reported a scan ("
                        + classic.summary() + ")");
        assertFalse(bridge.anyConsoleContains(SCANNED),
                () -> "the bridge reported a scan that classic Robocode did not ("
                        + bridge.summary() + ")");
    }
}
