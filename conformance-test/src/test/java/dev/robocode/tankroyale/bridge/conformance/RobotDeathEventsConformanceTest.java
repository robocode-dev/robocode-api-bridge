package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** Acceptance evidence for EVT-014 — a survivor receives another robot's death. */
class RobotDeathEventsConformanceTest extends ConformanceTestBase {

    private static final String ROBOT = "conformance.probes.DeathEventProbe";
    private static final Path SOURCE = ConformanceHarness.repoRoot().resolve(Path.of("compat-test",
            "conformance-robots", "conformance", "probes", "DeathEventProbe.java"));
    private static final String OTHER_DEATH = "OtherDeath!";

    @Test
    @DisplayName("EVT-014: a surviving robot reports another robot's death on both engines")
    void testEVT014_IntegrationPositive_SurvivorReceivesAnotherRobotsDeath() {
        assertOnBothEngines(ROBOT, SOURCE, (outcome, engine) ->
                assertTrue(outcome.anyConsoleContains(OTHER_DEATH),
                        () -> "no survivor reported another robot's death on " + engine + " ("
                                + outcome.summary() + ")"));
    }

    @Test
    @DisplayName("EVT-014 negative: a survivor does not receive duplicate death notifications per round")
    void testEVT014_IntegrationNegative_SurvivorDoesNotReceiveDuplicateDeathNotifications() {
        assertOnBothEngines(ROBOT, SOURCE, (outcome, engine) -> {
            for (int deaths : outcome.countsOf(OTHER_DEATH)) {
                assertTrue(deaths <= configuredRounds(),
                        () -> "a participant received " + deaths + " other-robot death notifications on "
                                + engine + ", more than once per configured round ("
                                + outcome.summary() + ")");
            }
        });
    }
}
