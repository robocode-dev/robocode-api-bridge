package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** Acceptance evidence for EVT-006 — custom events register, dispatch, and can be removed. */
class CustomEventsConformanceTest extends ConformanceTestBase {

    private static final String CLASSIC_ROBOT = "tested.robots.CustomEvents";
    private static final String REMOVAL_ROBOT = "conformance.probes.CustomEventRemovalProbe";
    private static final String ENEMY = "sample.Target";
    private static final Path SOURCE = ConformanceHarness.repoRoot().resolve(Path.of(
            "compat-test", "conformance-robots", "conformance", "probes",
            "CustomEventRemovalProbe.java"));

    @Test
    @DisplayName("EVT-006: classic custom-event conditions dispatch on both engines")
    void testEVT006_IntegrationPositive_ClassicCustomEventsDispatch() {
        assertOnBothEngines(CLASSIC_ROBOT, ENEMY, (outcome, engine) -> {
            assertTrue(outcome.anyConsoleContains("onTick99"),
                    () -> "priority-99 custom event was not delivered on " + engine);
            assertTrue(outcome.anyConsoleContains("onTick30"),
                    () -> "priority-30 custom event was not delivered on " + engine);
        });
    }

    @Test
    @DisplayName("EVT-006: removing a custom condition stops subsequent deliveries")
    void testEVT006_IntegrationPositive_RemovedCustomEventStopsFiring() {
        assertOnBothEngines(REMOVAL_ROBOT, SOURCE, ENEMY, (outcome, engine) -> {
            assertTrue(outcome.anyConsoleContains("CustomEventFired!"),
                    () -> "custom event never fired on " + engine);
            assertTrue(outcome.anyConsoleContains("CustomEventRemoved!"),
                    () -> "custom event was not removed on " + engine);
        });
    }

    @Test
    @DisplayName("EVT-006 negative: a removed custom condition fires at most once per round")
    void testEVT006_IntegrationNegative_RemovedCustomEventDoesNotRepeat() {
        assertOnBothEngines(REMOVAL_ROBOT, SOURCE, ENEMY, (outcome, engine) -> {
            for (int fires : outcome.countsOf("CustomEventFired!")) {
                assertTrue(fires <= configuredRounds(),
                        () -> "removed custom event fired " + fires + " times on " + engine
                                + ", more than once per configured round (" + outcome.summary() + ")");
            }
        });
    }
}
