package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Acceptance evidence for TEAM-001, TEAM-002, and TEAM-003. */
class TeamSupportConformanceTest extends ConformanceTestBase {

    private static final String TEAM = "conformance.teams.TeamFixture";
    private static final Path SOURCE = ConformanceHarness.repoRoot().resolve(Path.of(
            "compat-test", "conformance-robots", "conformance", "teams"));
    private static final int TEAM_INSTANCES = 2;
    private static final int TEAM_MEMBERS = 3;

    @Test
    @DisplayName("TEAM-001: a team entry boots every member on both engines")
    void testTEAM001_IntegrationPositive_TeamEntryBootsEveryMember() {
        assertOnBothEnginesTeam(TEAM, SOURCE, (outcome, engine) -> {
            assertEquals(TEAM_INSTANCES * TEAM_MEMBERS, outcome.consoles().size(),
                    () -> "the team run staged the wrong number of member processes on " + engine
                            + " (" + outcome.summary() + ")");
            assertEquals(TEAM_INSTANCES * configuredRounds(), outcome.countOf("TeamLeaderReady:"),
                    () -> "not every team leader started every round on " + engine
                            + " (" + outcome.summary() + ")");
        });
    }

    @Test
    @DisplayName("TEAM-001 negative: a team entry does not collapse members into one bot")
    void testTEAM001_IntegrationNegative_TeamEntryDoesNotCollapseMembers() {
        assertOnBothEnginesTeam(TEAM, SOURCE, (outcome, engine) ->
                assertEquals(TEAM_INSTANCES * TEAM_MEMBERS, outcome.consoles().size(),
                        () -> "team members were collapsed on " + engine + " ("
                                + outcome.summary() + ")"));
    }

    @Test
    @DisplayName("TEAM-002: teammate messages reach the intended members on both engines")
    void testTEAM002_IntegrationPositive_TeammateMessagesReachIntendedMembers() {
        assertOnBothEnginesTeam(TEAM, SOURCE, (outcome, engine) -> {
            int rounds = configuredRounds();
            assertEquals(TEAM_INSTANCES * (TEAM_MEMBERS - 1) * rounds,
                    outcome.countOf("Message:BROADCAST"),
                    () -> "broadcast messages were not delivered to every teammate on " + engine
                            + " (" + outcome.summary() + ")");
            assertEquals(TEAM_INSTANCES * rounds, outcome.countOf("Message:DIRECT"),
                    () -> "direct messages were not delivered to one teammate per team on " + engine
                            + " (" + outcome.summary() + ")");
            assertTrue(outcome.anyConsoleContains(" from "),
                    () -> "team messages did not carry a sender on " + engine
                            + " (" + outcome.summary() + ")");
        });
    }

    @Test
    @DisplayName("TEAM-002 negative: a directed teammate message is not broadcast")
    void testTEAM002_IntegrationNegative_DirectedMessageIsNotBroadcast() {
        assertOnBothEnginesTeam(TEAM, SOURCE, (outcome, engine) ->
                assertEquals(TEAM_INSTANCES * configuredRounds(), outcome.countOf("Message:DIRECT"),
                        () -> "a directed message reached the wrong teammates on " + engine
                                + " (" + outcome.summary() + ")"));
    }

    @Test
    @DisplayName("TEAM-003: a droid receives teammate information without scans")
    void testTEAM003_IntegrationPositive_DroidReceivesTeammateInformationWithoutScans() {
        assertOnBothEnginesTeam(TEAM, SOURCE, (outcome, engine) -> {
            assertEquals(TEAM_INSTANCES * configuredRounds(),
                    outcome.countOf("TeamDroidMessage:BROADCAST"),
                    () -> "the droid did not receive teammate information on " + engine
                            + " (" + outcome.summary() + ")");
            assertFalse(outcome.anyConsoleContains("TeamDroidScanned"),
                    () -> "the droid received a scan on " + engine + " (" + outcome.summary() + ")");
        });
    }

    @Test
    @DisplayName("TEAM-003 negative: a droid never receives its own scan event")
    void testTEAM003_IntegrationNegative_DroidNeverReceivesOwnScan() {
        assertOnBothEnginesTeam(TEAM, SOURCE, (outcome, engine) ->
                assertFalse(outcome.anyConsoleContains("TeamDroidScanned"),
                        () -> "the droid received an own scan event on " + engine
                                + " (" + outcome.summary() + ")"));
    }
}
