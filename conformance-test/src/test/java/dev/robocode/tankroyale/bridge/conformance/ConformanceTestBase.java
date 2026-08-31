package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.BeforeAll;

import java.util.HashMap;
import java.util.Map;
import java.nio.file.Path;

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
    private final Map<String, BattleOutcome> ran = new HashMap<>();

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
        assertOnBothEngines(robotClass, null, expectation);
    }

    /** Runs a locally held probe source on both engines after compiling it against classic. */
    void assertOnBothEngines(String robotClass, Path source, Expectation expectation) {
        for (Engine engine : Engine.values()) {
            BattleOutcome outcome = outcomeFor(engine, robotClass, source);
            assertTrue(outcome.completed(),
                    () -> "the battle did not complete on " + engine + " (" + outcome.summary() + ")");
            expectation.check(outcome, engine);
        }
    }

    /**
     * Runs a robot on one engine, reusing the result within a single test.
     *
     * Keyed by engine and robot together: a test that touches two robots would otherwise
     * get the first robot's battle back for the second assertion, and pass or fail for a
     * reason that has nothing to do with what it claims to check.
     */
    BattleOutcome outcomeFor(Engine engine, String robotClass) {
        return outcomeFor(engine, robotClass, null);
    }

    private BattleOutcome outcomeFor(Engine engine, String robotClass, Path source) {
        return ran.computeIfAbsent(engine.name() + " " + robotClass,
                key -> harness.run(engine, robotClass, source));
    }

    /** The number of rounds every battle in this run is configured for. */
    static int configuredRounds() {
        return harness.rounds();
    }
}
