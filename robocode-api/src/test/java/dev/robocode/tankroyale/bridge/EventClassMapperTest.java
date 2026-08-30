package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Acceptance evidence for API-006 — each Robocode event class resolves to the Tank Royale
 * event class that carries the same meaning.
 * <p>
 * The mapping is not name-for-name, and the places where it is not are where a mistake would
 * hide. A robot registering interest in one event and receiving another is a fidelity defect
 * that produces no error at all.
 */
class EventClassMapperTest {

    @Test
    @DisplayName("API-006 positive: events whose names correspond map to their counterparts")
    void testAPI006_UnitPositive_MapsCorrespondingEventNames() {
        assertEquals(ScannedBotEvent.class, map(robocode.ScannedRobotEvent.class));
        assertEquals(HitWallEvent.class, map(robocode.HitWallEvent.class));
        assertEquals(HitByBulletEvent.class, map(robocode.HitByBulletEvent.class));
        assertEquals(BulletHitBulletEvent.class, map(robocode.BulletHitBulletEvent.class));
        assertEquals(SkippedTurnEvent.class, map(robocode.SkippedTurnEvent.class));
        assertEquals(DeathEvent.class, map(robocode.DeathEvent.class));
        assertEquals(CustomEvent.class, map(robocode.CustomEvent.class));
    }

    @Test
    @DisplayName("API-006 positive: events whose names diverge map by meaning, not by name")
    void testAPI006_UnitPositive_MapsDivergentlyNamedEventsByMeaning() {
        // These are the ones a name-matching implementation would get wrong.
        assertEquals(TickEvent.class, map(robocode.StatusEvent.class),
                "a Robocode status update is a Tank Royale tick");
        assertEquals(BulletHitWallEvent.class, map(robocode.BulletMissedEvent.class),
                "a bullet missing means it reached a wall");
        assertEquals(BulletHitBotEvent.class, map(robocode.BulletHitEvent.class),
                "the robot's own bullet hitting someone");
        assertEquals(HitBotEvent.class, map(robocode.HitRobotEvent.class));
        assertEquals(BotDeathEvent.class, map(robocode.RobotDeathEvent.class),
                "another robot dying, distinct from the robot's own death");
        assertEquals(WonRoundEvent.class, map(robocode.WinEvent.class));
        assertEquals(TeamMessageEvent.class, map(robocode.MessageEvent.class));
    }

    @Test
    @DisplayName("API-006 positive: a robot's own death and another robot's death do not collide")
    void testAPI006_UnitPositive_KeepsOwnDeathDistinctFromAnotherRobotsDeath() {
        // These two are one character apart in Robocode's naming and mean opposite things.
        // Conflating them is how a robot stops learning that it died -- the defect that
        // existed before events were routed through the Bot API's queue.
        assertEquals(DeathEvent.class, map(robocode.DeathEvent.class));
        assertEquals(BotDeathEvent.class, map(robocode.RobotDeathEvent.class));
    }

    @Test
    @DisplayName("API-006 negative: an unknown event is refused rather than mapped to something unrelated")
    void testAPI006_UnitNegative_RefusesAnEventWithNoCounterpart() {
        UnsupportedOperationException thrown = assertThrows(UnsupportedOperationException.class,
                () -> EventClassMapper.toBotEventClass("NoSuchEvent"));

        // Refusing loudly is the point: silently returning a default would deliver the wrong
        // event to the robot, and nothing downstream could tell.
        assertEquals(true, thrown.getMessage().contains("NoSuchEvent"),
                "the refusal names the event it could not map");
    }

    private static Class<? extends BotEvent> map(Class<?> robocodeEventClass) {
        return EventClassMapper.toBotEventClass(robocodeEventClass.getSimpleName());
    }
}
