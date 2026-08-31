package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Acceptance evidence for EVT-004 (own death reaches the death handler), EVT-012 (winning a
 * round reaches the win handler), and EVT-011 (round and battle completion each reach their
 * handler exactly once).
 *
 * Classic's {@code BattleWin} robot prints a marker from each of {@code onWin},
 * {@code onDeath}, {@code onRoundEnded}, and {@code onBattleEnded}. In a robot-against-itself
 * battle one instance wins each round and the other dies, so both outcomes occur in the same
 * run and the pair can be checked together.
 *
 * EVT-004 is the criterion with the sharpest history in this repository. Before events were
 * routed through the Bot API's event queue, the bridge dispatched them from a hand-written
 * switch that had no case for the robot's own death, so {@code onDeath} was never called for
 * any robot. A manual switch fails silently on the branch nobody wrote, and nothing in a
 * score report shows it. Routing through the queue did not fix it either, and AN-009 now has
 * the reason: the death never reaches the bot, so there is nothing for any dispatcher to route.
 *
 * The win-handler and round-completion tests were originally tagged EVT-004 and EVT-005; G-002
 * found neither tag matched what the test asserted, since no criterion in CAP-001 covered
 * round or battle completion at all. EVT-012 and EVT-011 were minted for what these tests
 * actually prove, and the tests below are retagged and renamed accordingly.
 */
class RoundOutcomeEventsConformanceTest extends ConformanceTestBase {

    private static final String ROBOT = "tested.robots.BattleWin";

    @Test
    @DisplayName("EVT-004: a robot's own death reaches onDeath on both engines")
    void testEVT004_IntegrationPositive_OwnDeathReachesTheDeathHandler() {
        assertOnBothEngines(ROBOT, (outcome, engine) ->
                assertTrue(outcome.anyConsoleContains("Death!"),
                        () -> "no robot reported its own death on " + engine
                                + ", so onDeath was never called (" + outcome.summary() + ")"));
    }

    @Test
    @DisplayName("EVT-004 negative: a participant's onDeath is not reported more than once per round")
    void testEVT004_IntegrationNegative_DeathHandlerDoesNotRepeatWithinRounds() {
        assertOnBothEngines(ROBOT, (outcome, engine) -> {
            for (String console : outcome.consoles()) {
                int deaths = countIn(console, "Death!");
                assertTrue(deaths <= configuredRounds(),
                        () -> "a participant reported onDeath " + deaths + " times on " + engine
                                + ", more than once per configured round (" + outcome.summary() + ")");
            }
        });
    }

    @Test
    @DisplayName("EVT-012: winning a round reaches onWin on both engines")
    void testEVT012_IntegrationPositive_WinningARoundReachesTheWinHandler() {
        assertOnBothEngines(ROBOT, (outcome, engine) ->
                assertTrue(outcome.anyConsoleContains("Win!"),
                        () -> "no robot reported winning on " + engine + " (" + outcome.summary() + ")"));
    }

    @Test
    @DisplayName("EVT-011: round and battle completion reach their handlers on both engines")
    void testEVT011_IntegrationPositive_RoundAndBattleCompletionReachTheirHandlers() {
        assertOnBothEngines(ROBOT, (outcome, engine) -> {
            assertTrue(outcome.anyConsoleContains("RoundEnded!"),
                    () -> "onRoundEnded was not reported on " + engine + " (" + outcome.summary() + ")");
            assertTrue(outcome.anyConsoleContains("BattleEnded!"),
                    () -> "onBattleEnded was not reported on " + engine + " (" + outcome.summary() + ")");
        });
    }

    @Test
    @DisplayName("EVT-011 negative: round completion is reported once per round, not once per participant")
    void testEVT011_IntegrationNegative_RoundCompletionIsNotReportedMoreThanOncePerRound() {
        int expectedRounds = configuredRounds();
        assertOnBothEngines(ROBOT, (outcome, engine) -> {
            // Each participant sees each round end exactly once. A dispatcher that delivered
            // the event once per participant to every participant would satisfy the positive
            // test above and fail here, because it would double (or worse) the count below
            // rather than merely clear a lower bound.
            for (String console : outcome.consoles()) {
                int rounds = countIn(console, "RoundEnded!");
                int battles = countIn(console, "BattleEnded!");
                assertTrue(battles == 1,
                        () -> "a participant reported the battle ending " + battles
                                + " times on " + engine);
                assertTrue(rounds == expectedRounds,
                        () -> "a participant reported " + rounds + " round endings on " + engine
                                + ", expected exactly " + expectedRounds);
            }
        });
    }

    private static int countIn(String text, String marker) {
        int total = 0;
        for (int from = 0; (from = text.indexOf(marker, from)) >= 0; from += marker.length()) {
            total++;
        }
        return total;
    }
}
