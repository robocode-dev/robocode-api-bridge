package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** Acceptance evidence for EVT-005 — status events arrive at the current turn boundary. */
class TurnBoundaryConformanceTest extends ConformanceTestBase {

    private static final String ROBOT = "conformance.probes.TurnBoundaryProbe";
    private static final String ENEMY = "sample.Target";
    private static final String MARKER = "TurnStatus:";
    private static final Path SOURCE = ConformanceHarness.repoRoot().resolve(Path.of(
            "compat-test", "conformance-robots", "conformance", "probes", "TurnBoundaryProbe.java"));
    private static final Pattern RECORD = Pattern.compile(
            "TurnStatus:(\\d+):(-?\\d+):(-?\\d+):(-?\\d+)");

    @Test
    @DisplayName("EVT-005: status event, status snapshot, and robot clock name the same turn")
    void testEVT005_IntegrationPositive_NewTurnStatusUsesCurrentTurn() {
        assertOnBothEngines(ROBOT, SOURCE, ENEMY, (outcome, engine) -> {
            int records = 0;
            for (String console : outcome.consoles()) {
                Matcher matcher = RECORD.matcher(console);
                while (matcher.find()) {
                    records++;
                    assertTrue(matcher.group(2).equals(matcher.group(3))
                                    && matcher.group(2).equals(matcher.group(4)),
                            () -> "status clocks disagree on " + engine + ": " + matcher.group());
                }
            }
            assertTrue(records >= 2,
                    () -> "fewer than two turn-boundary records appeared on " + engine
                            + " (" + outcome.summary() + ")");
        });
    }

    @Test
    @DisplayName("EVT-005 negative: turn-boundary dispatch emits no malformed status record")
    void testEVT005_IntegrationNegative_StatusDispatchDoesNotEmitMalformedTurn() {
        assertOnBothEngines(ROBOT, SOURCE, ENEMY, (outcome, engine) -> {
            for (String console : outcome.consoles()) {
                for (String line : console.split("\\R")) {
                    if (line.contains(MARKER)) {
                        assertTrue(RECORD.matcher(line.trim()).matches(),
                                () -> "malformed turn-boundary record on " + engine + ": " + line);
                    }
                }
            }
        });
    }
}
