package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BlockingTeamMessageConformanceTest extends ConformanceTestBase {
    @Test
    void testTEAM002_IntegrationPositive_DeliversAtTheNextTurnDuringBlockingCalls() {
        Path source = ConformanceHarness.repoRoot().resolve(Path.of(
                "compat-test", "conformance-robots", "conformance", "blockingteams"));
        assertOnBothEnginesTeam("conformance.blockingteams.BlockingTeam", source, (outcome, engine) -> {
            assertFalse(outcome.anyConsoleContains("BlockingMessageError:"),
                    () -> "message order, sender or next-turn delivery differed on " + engine
                            + " (" + outcome.summary() + ")");
            for (String phase : new String[] {
                    "move", "handler-a", "handler-b", "body", "gun", "radar", "wait", "execute"}) {
                assertEquals(2 * configuredRounds(), outcome.countOf("BlockingMessageOk:" + phase + ":"),
                        () -> "missing next-turn delivery for " + phase + " on " + engine
                                + " (" + outcome.summary() + ")");
            }
        });
    }
}
