package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Acceptance evidence for EVT-013 — an interruptible handler is re-entered for a
 * same-priority event once marked interruptible.
 *
 * Classic's {@code InteruptibleEvent} robot turns its radar from inside {@code onHitWall}
 * and prints a marker if {@code onScannedRobot} is then entered. The robot sets
 * {@code HitWallEvent} to the same priority as {@code ScannedRobotEvent} ("make same as
 * scan"), so this is same-priority re-entry, not pre-emption by a higher-priority event.
 * Classic's own suite asserts exactly this, and the expectation ports unchanged because
 * it is about a marker the robot printed rather than about where the robot ended up.
 *
 * EVT-013 is the successor to EVT-003, retired because no robot in the source tree
 * exercises genuine higher-priority re-entry (IDR-003). This is the criterion the earlier
 * event-queue defect broke: with deferred same-priority events discarded, the scan never
 * arrived and the marker never appeared.
 */
class InterruptibleEventConformanceTest extends ConformanceTestBase {

    private static final String ROBOT = "tested.robots.InteruptibleEvent";
    private static final String SCANNED = "Scanned!!!";

    @Test
    @DisplayName("EVT-013: turning the radar inside onHitWall leads to onScannedRobot on both engines")
    void testEVT013_IntegrationPositive_ScanHandlerIsEnteredFromWithinTheWallHandler() {
        assertOnBothEngines(ROBOT, (outcome, engine) ->
                assertTrue(outcome.anyConsoleContains(SCANNED),
                        () -> "the robot never reported being scanned on " + engine
                                + ", so the interruptible handler was not re-entered ("
                                + outcome.summary() + ")"));
    }

    @Test
    @DisplayName("EVT-013 negative: the bridge does not report a scan the classic engine never saw")
    void testEVT013_IntegrationNegative_DoesNotReportAScanClassicDidNotSee() {
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
    @DisplayName("EVT-013: neither engine throws while dispatching the interrupted handler")
    void testEVT013_IntegrationNegative_DispatchingAnInterruptedHandlerThrowsOnNeitherEngine() {
        assertOnBothEngines(ROBOT, (outcome, engine) ->
                assertTrue(outcome.errors().isEmpty(),
                        () -> "errors on " + engine + ": " + outcome.errors()));
    }
}
