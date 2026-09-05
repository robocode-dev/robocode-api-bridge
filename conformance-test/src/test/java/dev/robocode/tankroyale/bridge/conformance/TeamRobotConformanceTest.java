package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Integration evidence for CAP-006's team startup, messaging, and droid criteria. */
class TeamRobotConformanceTest extends ConformanceTestBase {

    private static final Path SOURCE = ConformanceHarness.repoRoot()
            .resolve("compat-test/conformance-robots/conformance/teams");

    private static final String MESSAGE_TEAM = "conformance.probes.teams.MessageTeam";
    private static final List<String> MESSAGE_MEMBERS = List.of(
            "conformance.probes.teams.TeamMessageLeader",
            "conformance.probes.teams.TeamMessageReceiver",
            "conformance.probes.teams.TeamMessageObserver");
    private static final List<String> MESSAGE_MEMBERS_WITH_EXTERNAL_CLASS = List.of(
            "conformance.probes.teams.TeamMessageLeader",
            "conformance.probes.other.MissingTeamMember");

    private static final String DROID_TEAM = "conformance.probes.teams.DroidTeam";
    private static final List<String> DROID_MEMBERS = List.of(
            "conformance.probes.teams.TeamDroidLeader",
            "conformance.probes.teams.TeamDroidProbe");

    @Test
    @DisplayName("TEAM-001: a generated team entry boots and completes a battle")
    void testTEAM001_IntegrationPositive_GeneratedTeamTakesPartInABattle() {
        assertOnBothEnginesTeam(MESSAGE_TEAM, SOURCE, MESSAGE_MEMBERS, (outcome, engine) -> {
            assertTrue(outcome.anyConsoleContains("TeamMessagesSent"),
                    () -> "the team leader sent no message on " + engine
                            + " (" + outcome.summary() + ")");
        });
    }

    @Test
    @DisplayName("TEAM-001 negative: a member outside the team package is rejected")
    void testTEAM001_IntegrationNegative_MalformedTeamPackageIsRejected() {
        for (Engine engine : Engine.values()) {
            BattleOutcome outcome = outcomeForTeam(engine, MESSAGE_TEAM, SOURCE,
                    MESSAGE_MEMBERS_WITH_EXTERNAL_CLASS);
            assertTrue(!outcome.completed(),
                    () -> "a malformed team package completed on " + engine
                            + " (" + outcome.summary() + ")");
        }
    }

    @Test
    @DisplayName("TEAM-002: direct and broadcast team messages have classic delivery")
    void testTEAM002_IntegrationPositive_TeamMessagesHaveClassicDelivery() {
        assertOnBothEnginesTeam(MESSAGE_TEAM, SOURCE, MESSAGE_MEMBERS, (outcome, engine) -> {
            assertTrue(outcome.countOf("TeamDirectReceived") > 0,
                    () -> "no direct team delivery was observed on " + engine
                            + " (" + outcome.summary() + ")");
            assertTrue(outcome.countOf("TeamBroadcastReceived")
                            > outcome.countOf("TeamDirectReceived"),
                    () -> "broadcast delivery was not broader than direct delivery on " + engine
                            + " (" + outcome.summary() + ")");
        });
    }

    @Test
    @DisplayName("TEAM-002 negative: a direct message does not become a broadcast")
    void testTEAM002_IntegrationNegative_DirectMessageDoesNotReachEveryTeammate() {
        assertOnBothEnginesTeam(MESSAGE_TEAM, SOURCE, MESSAGE_MEMBERS, (outcome, engine) -> {
            assertTrue(outcome.countOf("TeamBroadcastReceived") > outcome.countOf("TeamDirectReceived"),
                    () -> "direct delivery was not narrower than broadcast on " + engine
                            + " (" + outcome.summary() + ")");
        });
    }

    @Test
    @DisplayName("TEAM-003: a droid receives teammate information without scans")
    void testTEAM003_IntegrationPositive_DroidUsesTeamMessageWithoutOwnScans() {
        assertOnBothEnginesTeam(DROID_TEAM, SOURCE, DROID_MEMBERS, (outcome, engine) -> {
            assertTrue(outcome.countOf("DroidMessageReceived") > 0,
                    () -> "the droid received no teammate message on " + engine
                            + " (" + outcome.summary() + ")");
            assertEquals(0, outcome.countOf("DroidScanReceived"),
                    () -> "the droid received an own scan on " + engine
                            + " (" + outcome.summary() + ")");
        });
    }

    @Test
    @DisplayName("TEAM-003 negative: the bridge reports no droid scan classic did not report")
    void testTEAM003_IntegrationNegative_BridgeDoesNotReportDroidScans() {
        BattleOutcome classic = outcomeForTeam(Engine.CLASSIC, DROID_TEAM, SOURCE, DROID_MEMBERS);
        BattleOutcome bridge = outcomeForTeam(Engine.BRIDGE, DROID_TEAM, SOURCE, DROID_MEMBERS);

        assertTrue(classic.completed(), () -> "classic droid battle did not complete ("
                + classic.summary() + ")");
        assertTrue(bridge.completed(), () -> "bridge droid battle did not complete ("
                + bridge.summary() + ")");
        assertEquals(0, classic.countOf("DroidScanReceived"),
                () -> "classic delivered a droid scan (" + classic.summary() + ")");
        assertEquals(0, bridge.countOf("DroidScanReceived"),
                () -> "the bridge delivered a droid scan classic did not ("
                        + bridge.summary() + ")");
    }
}
