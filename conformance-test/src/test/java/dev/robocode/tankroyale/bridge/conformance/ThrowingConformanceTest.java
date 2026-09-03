package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Acceptance evidence for EVT-009 — handler exceptions follow classic reporting. */
class ThrowingConformanceTest extends ConformanceTestBase {

    private static final String ROBOT = "tested.robots.Throwing";
    private static final String EXCEPTION = "NullPointerException";

    @Test
    @DisplayName("EVT-009: a handler exception is reported on both engines")
    void testEVT009_IntegrationPositive_HandlerExceptionIsReported() {
        assertOnBothEngines(ROBOT, (outcome, engine) ->
                assertTrue(outcome.errors().stream().anyMatch(error -> error.contains(EXCEPTION)),
                        () -> "no " + EXCEPTION + " was reported on " + engine
                                + " (" + outcome.summary() + ")"));
    }

    @Test
    @DisplayName("EVT-009 negative: handler failure is not replaced by a bridge-only exception")
    void testEVT009_IntegrationNegative_HandlerFailureKeepsClassicExceptionType() {
        assertOnBothEngines(ROBOT, (outcome, engine) -> {
            assertFalse(outcome.errors().isEmpty(),
                    () -> "no error was reported on " + engine + " (" + outcome.summary() + ")");
            assertTrue(outcome.errors().stream().allMatch(error -> error.contains(EXCEPTION)),
                    () -> "unexpected error type on " + engine + ": " + outcome.errors());
        });
    }
}
