package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Acceptance evidence for EVT-015 — the classic event-priority filter expectation.
 *
 * The probe first raises scan priority and proves that a radar sweep enters the scan handler
 * while the wall handler is blocked. It then lowers scan priority and proves the same sweep does
 * not enter that handler, matching classic's authoritative EventPriorityFilter boundary.
 */
class EventPriorityConformanceTest extends ConformanceTestBase {

    private static final String ROBOT = "conformance.probes.EventPriorityProbe";
    private static final String ENEMY = "sample.Target";
    private static final String SCAN_OBSERVED = "ScanObserved!!!";
    private static final String SCAN_CONTROL = "ScanControlDuringWallHandler!!!";
    private static final String SUPPRESSION_WINDOW = "SuppressionWindowEntered!!!";
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
                assertTrue(outcome.anyConsoleContains(SCAN_CONTROL),
                        () -> "the priority probe found no high-priority scan during the wall handler on " + engine
                                + " (" + outcome.summary() + ")");
                assertTrue(outcome.anyConsoleContains(SUPPRESSION_WINDOW),
                        () -> "the priority probe did not reach the lower-priority window on " + engine
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
        assertTrue(classic.anyConsoleContains(SCAN_CONTROL),
                () -> "the classic priority probe found no high-priority scan during the wall handler ("
                        + classic.summary() + ")");
        assertTrue(bridge.anyConsoleContains(SCAN_CONTROL),
                () -> "the bridge priority probe found no high-priority scan during the wall handler ("
                        + bridge.summary() + ")");
        assertTrue(classic.anyConsoleContains(SUPPRESSION_WINDOW),
                () -> "the classic priority probe did not reach the lower-priority window ("
                        + classic.summary() + ")");
        assertTrue(bridge.anyConsoleContains(SUPPRESSION_WINDOW),
                () -> "the bridge priority probe did not reach the lower-priority window ("
                        + bridge.summary() + ")");
        assertFalse(classic.anyConsoleContains(SCANNED),
                () -> "the classic priority-probe baseline reported a scan ("
                        + classic.summary() + ")");
        assertFalse(bridge.anyConsoleContains(SCANNED),
                () -> "the bridge reported a scan that classic Robocode did not ("
                        + bridge.summary() + ")");
    }
}
