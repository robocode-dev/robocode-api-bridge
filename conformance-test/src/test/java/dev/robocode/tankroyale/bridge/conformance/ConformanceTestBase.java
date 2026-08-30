package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.BeforeAll;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Base for a conformance test: state one expectation, have it checked against both engines.
 *
 * The shape matters. A test that asserted separately per engine would let the two drift
 * apart one edit at a time, which is the failure this tier exists to prevent -- so an
 * expectation is written once and {@link #assertOnBothEngines} runs it twice.
 *
 * Classic is the specification (G-001), so its result is checked first: an expectation that
 * classic itself does not meet is a broken test rather than a bridge defect, and saying so
 * separately keeps the two apart in the failure message.
 */
abstract class ConformanceTestBase {

    private static ConformanceHarness harness;
    private final Map<Engine, BattleOutcome> ran = new EnumMap<>(Engine.class);

    @BeforeAll
    static void resolveEnvironment() {
        harness = ConformanceHarness.resolveOrNull();
        // Skipping rather than failing: this tier needs two engine installations and the
        // classic source repository's compiled test robots, none of which exist on a CI
        // runner, and a clean checkout must still build (ARCH-003).
        assumeTrue(harness != null,
                () -> "conformance tier not runnable here -- " + ConformanceHarness.missingEnvironment());
    }

    /** What a battle with this robot must show, on whichever engine ran it. */
    interface Expectation {
        void check(BattleOutcome outcome, Engine engine);
    }

    /**
     * Runs the robot on both engines and applies the same expectation to each.
     */
    void assertOnBothEngines(String robotClass, Expectation expectation) {
        for (Engine engine : Engine.values()) {
            BattleOutcome outcome = outcomeFor(engine, robotClass);
            assertTrue(outcome.completed(),
                    () -> "the battle did not complete on " + engine + " (" + outcome.summary() + ")");
            expectation.check(outcome, engine);
        }
    }

    /**
     * Asserts that both engines produced the marker the same number of times.
     *
     * Use only where the count is genuinely determined by the robot's own logic. Battle
     * length is not comparable between the engines -- Tank Royale has no seed (AN-002) --
     * so a count that depends on how long the robot survived will differ for reasons that
     * say nothing about the bridge.
     */
    void assertSameCountOnBothEngines(String robotClass, String marker) {
        int classic = outcomeFor(Engine.CLASSIC, robotClass).countOf(marker);
        int bridge = outcomeFor(Engine.BRIDGE, robotClass).countOf(marker);
        assertEquals(classic, bridge,
                () -> "'" + marker + "' appeared " + classic + " time(s) on classic Robocode and "
                        + bridge + " time(s) through the bridge");
    }

    /** Runs a robot on one engine, reusing the result within a single test. */
    BattleOutcome outcomeFor(Engine engine, String robotClass) {
        return ran.computeIfAbsent(engine, e -> harness.run(e, robotClass));
    }
}
