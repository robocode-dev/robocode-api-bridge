package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/** Acceptance evidence for EVT-005 — the initial status callback precedes robot run(). */
class InitialStatusConformanceTest extends ConformanceTestBase {

    private static final String ROBOT = "conformance.probes.InitialStatusProbe";
    private static final String ENEMY = "sample.Target";
    private static final Path SOURCE = ConformanceHarness.repoRoot().resolve(Path.of(
            "compat-test", "conformance-robots", "conformance", "probes", "InitialStatusProbe.java"));
    private static final Pattern BEFORE_RUN = Pattern.compile("InitialStatusBeforeRun:(\\d+)");
    private static final Pattern INITIAL_CLOCK = Pattern.compile("InitialStatusClock:(\\d+:\\d+:\\d+)");
    private static final Pattern PENDING_STATUS = Pattern.compile("PendingStatusAtRunStart:(\\d+)");
    private static final Pattern AFTER_EXECUTE = Pattern.compile("StatusAfterFirstExecute:(\\d+)");

    @Test
    @DisplayName("EVT-005: initial status runs before robot run and is not delivered twice")
    void testEVT005_IntegrationPositive_InitialStatusPrecedesRun() {
        assertOnBothEngines(ROBOT, SOURCE, ENEMY, (outcome, engine) -> {
            List<String> beforeRun = matches(outcome, BEFORE_RUN);
            List<String> initialClock = matches(outcome, INITIAL_CLOCK);
            List<String> pendingStatus = matches(outcome, PENDING_STATUS);
            List<String> afterExecute = matches(outcome, AFTER_EXECUTE);

            assertFalse(beforeRun.isEmpty(),
                    () -> "robot run marker missing on " + engine + " (" + outcome.summary() + ")");
            assertFalse(afterExecute.isEmpty(),
                    () -> "first execute marker missing on " + engine + " (" + outcome.summary() + ")");
            assertFalse(pendingStatus.isEmpty(),
                    () -> "run-start event-queue marker missing on " + engine + " (" + outcome.summary() + ")");
            assertFalse(initialClock.isEmpty(),
                    () -> "initial status clock marker missing on " + engine + " (" + outcome.summary() + ")");
            beforeRun.forEach(count -> assertEquals("1", count,
                    () -> "initial status was not delivered exactly once before run() on " + engine));
            pendingStatus.forEach(count -> assertEquals("0", count,
                    () -> "the consumed initial status remained pending at run() on " + engine));
            initialClock.forEach(clock -> assertEquals("0:0:0", clock,
                    () -> "initial status and peer clocks did not match Classic time zero on " + engine));
            afterExecute.forEach(count -> assertEquals("2", count,
                    () -> "the initial status was delivered again from its queued tick on " + engine));
        });
    }

    private static List<String> matches(BattleOutcome outcome, Pattern pattern) {
        List<String> values = new ArrayList<>();
        for (String console : outcome.consoles()) {
            Matcher matcher = pattern.matcher(console);
            while (matcher.find()) {
                values.add(matcher.group(1));
            }
        }
        return values;
    }
}
