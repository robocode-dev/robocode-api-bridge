package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Acceptance evidence for EVT-003 — an interruptible handler is re-entered when a
 * higher-priority event arrives.
 *
 * Classic's {@code InteruptibleEvent} robot turns its radar from inside {@code onHitWall}
 * and prints a marker if {@code onScannedRobot} is then entered. Classic's own suite
 * asserts exactly this, and the expectation ports unchanged because it is about a marker
 * the robot printed rather than about where the robot ended up.
 *
 * This is the criterion the earlier event-queue defect broke: with deferred same-priority
 * events discarded, the scan never arrived and the marker never appeared.
 */
class InterruptibleEventConformanceTest extends ConformanceTestBase {

    private static final String ROBOT = "tested.robots.InteruptibleEvent";
    private static final String SCANNED = "Scanned!!!";

    @Test
    @DisplayName("EVT-003: turning the radar inside onHitWall leads to onScannedRobot on both engines")
    void testEVT003_IntegrationPositive_ScanHandlerIsEnteredFromWithinTheWallHandler() {
        assertOnBothEngines(ROBOT, (outcome, engine) ->
                assertTrue(outcome.anyConsoleContains(SCANNED),
                        () -> "the robot never reported being scanned on " + engine
                                + ", so the interruptible handler was not re-entered ("
                                + outcome.summary() + ")"));
    }

    @Test
    @DisplayName("EVT-003 negative: the bridge does not report a scan the classic engine never saw")
    void testEVT003_IntegrationNegative_DoesNotReportAScanClassicDidNotSee() {
        BattleOutcome classic = outcomeFor(Engine.CLASSIC, ROBOT);
        BattleOutcome bridge = outcomeFor(Engine.BRIDGE, ROBOT);

        // Parity cuts both ways (G-001). A bridge that delivered scan events classic does
        // not deliver would make robots stronger under Tank Royale, and a score-based
        // instrument is least likely to question a robot that improved.
        if (!classic.anyConsoleContains(SCANNED)) {
            assertTrue(!bridge.anyConsoleContains(SCANNED),
                    () -> "the bridge reported a scan that classic Robocode did not ("
                            + bridge.summary() + ")");
        }
    }

    @Test
    @DisplayName("EVT-003: neither engine throws while dispatching the interrupted handler")
    void testEVT003_IntegrationNegative_DispatchingAnInterruptedHandlerThrowsOnNeitherEngine() {
        assertOnBothEngines(ROBOT, (outcome, engine) ->
                assertTrue(outcome.errors().isEmpty(),
                        () -> "errors on " + engine + ": " + outcome.errors()));
    }
}
