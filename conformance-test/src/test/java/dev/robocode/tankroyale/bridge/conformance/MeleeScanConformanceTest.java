package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Acceptance evidence for EVT-010 — melee scan events are counted per turn. */
class MeleeScanConformanceTest extends ConformanceTestBase {

    private static final String ROBOT = "conformance.probes.MeleeScanProbe";
    private static final int MELEE_PARTICIPANTS = 10;
    private static final String COUNT_MARKER = "MeleeScanCount:";
    private static final Path SOURCE = ConformanceHarness.repoRoot().resolve(Path.of(
            "compat-test", "conformance-robots", "conformance", "probes", "MeleeScanProbe.java"));
    private static final Pattern COUNT = Pattern.compile("MeleeScanCount:(\\d+):(\\d+)");

    @Test
    @DisplayName("EVT-010: the official melee setup delivers per-turn scan counts")
    void testEVT010_IntegrationPositive_OfficialMeleeDeliversScanCounts() {
        assertOnBothEngines(ROBOT, SOURCE, MELEE_PARTICIPANTS, (outcome, engine) -> {
            assertEquals(MELEE_PARTICIPANTS, outcome.consoles().size(),
                    () -> "the melee run staged the wrong number of participants on " + engine
                            + " (" + outcome.summary() + ")");
            int records = 0;
            for (String console : outcome.consoles()) {
                Matcher matcher = COUNT.matcher(console);
                while (matcher.find()) {
                    records++;
                    int scans = Integer.parseInt(matcher.group(2));
                    assertTrue(scans >= 0 && scans <= MELEE_PARTICIPANTS - 1,
                            () -> "impossible per-turn scan count on " + engine + ": " + matcher.group());
                }
            }
            assertTrue(records > 0,
                    () -> "the melee probe reported no per-turn scan counts on " + engine
                            + " (" + outcome.summary() + ")");
        });
    }

    @Test
    @DisplayName("EVT-010 negative: melee scan counts stay within the participant bound")
    void testEVT010_IntegrationNegative_MeleeScanCountsStayWithinParticipantBound() {
        assertOnBothEngines(ROBOT, SOURCE, MELEE_PARTICIPANTS, (outcome, engine) -> {
            assertTrue(outcome.anyConsoleContains(COUNT_MARKER),
                    () -> "the melee probe reported no scan-count record on " + engine
                            + " (" + outcome.summary() + ")");
            for (String console : outcome.consoles()) {
                Matcher matcher = COUNT.matcher(console);
                while (matcher.find()) {
                    int scans = Integer.parseInt(matcher.group(2));
                    assertTrue(scans <= MELEE_PARTICIPANTS - 1,
                            () -> "a turn carried more scans than opponents on " + engine
                                    + ": " + matcher.group());
                }
            }
        });
    }
}
