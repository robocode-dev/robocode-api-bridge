package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** Acceptance evidence for EVT-008 — skipped turns reach the robot. */
class SkippedTurnsConformanceTest extends ConformanceTestBase {

    private static final String ROBOT = "conformance.probes.SkippedTurnProbe";
    private static final String SKIPPED = "SkippedTurnReported:";
    private static final Pattern SKIPPED_EVENT = Pattern.compile("SkippedTurnReported:\\d+:\\d+");
    private static final Path SOURCE = ConformanceHarness.repoRoot().resolve(Path.of(
            "compat-test", "conformance-robots", "conformance", "probes", "SkippedTurnProbe.java"));

    @Test
    @DisplayName("EVT-008: skipped turns are reported on both engines")
    void testEVT008_IntegrationPositive_SkippedTurnsReachTheRobot() {
        assertOnBothEngines(ROBOT, SOURCE, (outcome, engine) ->
                assertTrue(outcome.anyConsoleContains(SKIPPED),
                        () -> "no skipped-turn marker appeared on " + engine
                                + " (" + outcome.summary() + ")"));
    }

    @Test
    @DisplayName("EVT-008 negative: skipped-turn delivery does not duplicate without bound")
    void testEVT008_IntegrationNegative_SkippedTurnsDoNotDuplicateWithoutBound() {
        assertOnBothEngines(ROBOT, SOURCE, (outcome, engine) -> {
            for (String console : outcome.consoles()) {
                Matcher matcher = SKIPPED_EVENT.matcher(console);
                Set<String> events = new HashSet<>();
                while (matcher.find()) {
                    events.add(matcher.group());
                }
                int reported = (int) SKIPPED_EVENT.matcher(console).results().count();
                assertTrue(reported == events.size(),
                        () -> "a skipped-turn event was delivered more than once on " + engine
                                + " (" + outcome.summary() + ")");
            }
        });
    }
}
