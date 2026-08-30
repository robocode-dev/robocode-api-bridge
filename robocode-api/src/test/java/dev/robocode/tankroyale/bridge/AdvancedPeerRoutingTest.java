package dev.robocode.tankroyale.bridge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import robocode.Condition;

import java.io.File;

import static java.lang.Math.toRadians;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Acceptance evidence for ROUTE-006 through ROUTE-008 — the {@code IAdvancedRobotPeer}
 * surface: the queued {@code set*} commands, custom events and priorities, and data access.
 *
 * This is the widest of the peer interfaces and the one most robots actually use. The
 * queued commands are what let a robot move, turn its gun and turn its radar in the same
 * turn, so a robot that loses one of them keeps fighting with a stuck component.
 */
class AdvancedPeerRoutingTest {

    private static final double EPSILON = 1e-9;

    private RecordingBot bot;
    private BotPeer peer;

    @BeforeEach
    void setUp() {
        bot = RecordingBot.create();
        peer = new BotPeer(new StubRobot(), bot.asBot());
        bot.clear();
    }

    @Test
    @DisplayName("ROUTE-006 positive: each queued command reaches its own Bot API setter in degrees")
    void testROUTE006_UnitPositive_RoutesEachQueuedCommandToItsOwnSetter() {
        peer.setMove(120);
        assertEquals(120.0, bot.onlyCall("setForward").doubleArg(0), EPSILON);

        bot.clear();
        peer.setTurnBody(toRadians(60));
        assertEquals(60.0, bot.onlyCall("setTurnRight").doubleArg(0), EPSILON);

        bot.clear();
        peer.setTurnGun(toRadians(30));
        assertEquals(30.0, bot.onlyCall("setTurnGunRight").doubleArg(0), EPSILON);

        bot.clear();
        peer.setTurnRadar(toRadians(15));
        assertEquals(15.0, bot.onlyCall("setTurnRadarRight").doubleArg(0), EPSILON);
    }

    @Test
    @DisplayName("ROUTE-006 negative: a queued command does not also execute the turn")
    void testROUTE006_UnitNegative_QueuedCommandsDoNotCompleteTheTurn() {
        peer.setMove(100);
        peer.setTurnBody(toRadians(45));

        // The whole point of the queued form is that it takes effect at the next execute().
        // A routing that completed the turn itself would collapse a robot's turn structure.
        assertFalse(bot.called("go"), "queuing must not complete the turn: " + bot.names());
        assertFalse(bot.called("forward"), "the queued form must not use the blocking call");
        assertFalse(bot.called("turnRight"), "the queued form must not use the blocking call");
    }

    @Test
    @DisplayName("ROUTE-006 negative: NaN becomes zero, as classic treats it")
    void testROUTE006_UnitNegative_TreatsNaNAsZero() {
        peer.setMove(Double.NaN);
        assertEquals(0.0, bot.onlyCall("setForward").doubleArg(0), EPSILON);

        bot.clear();
        peer.setTurnBody(Double.NaN);
        assertEquals(0.0, bot.onlyCall("setTurnRight").doubleArg(0), EPSILON);

        bot.clear();
        peer.setTurnGun(Double.NaN);
        assertEquals(0.0, bot.onlyCall("setTurnGunRight").doubleArg(0), EPSILON);

        bot.clear();
        peer.setTurnRadar(Double.NaN);
        assertEquals(0.0, bot.onlyCall("setTurnRadarRight").doubleArg(0), EPSILON);
    }

    @Test
    @DisplayName("ROUTE-006 positive: stop, resume and the rate limits route to their own calls")
    void testROUTE006_UnitPositive_RoutesQueuedStopResumeAndRateLimits() {
        peer.setStop(false);
        assertEquals(false, bot.onlyCall("setStop").args[0]);

        bot.clear();
        peer.setResume();
        assertTrue(bot.called("setResume"), bot.names());

        bot.clear();
        peer.setMaxVelocity(6.5);
        assertEquals(6.5, bot.onlyCall("setMaxSpeed").doubleArg(0), EPSILON,
                "Tank Royale calls it speed; the peer must bridge the name");

        bot.clear();
        peer.setMaxTurnRate(7.5);
        assertEquals(7.5, bot.onlyCall("setMaxTurnRate").doubleArg(0), EPSILON);
    }

    @Test
    @DisplayName("ROUTE-007 positive: waiting for a condition passes a condition through")
    void testROUTE007_UnitPositive_RoutesWaitForWithACondition() {
        peer.waitFor(new Condition("done") {
            @Override
            public boolean test() {
                return true;
            }
        });

        assertNotNull(bot.onlyCall("waitFor").args[0], "a condition must reach the Bot API");
    }

    @Test
    @DisplayName("ROUTE-007 positive: a custom event is added and the same instance removed")
    void testROUTE007_UnitPositive_RemovesTheSameConditionItAdded() {
        Condition condition = new Condition("watch") {
            @Override
            public boolean test() {
                return false;
            }
        };

        peer.addCustomEvent(condition);
        Object added = bot.onlyCall("addCustomEvent").args[0];

        bot.clear();
        peer.removeCustomEvent(condition);
        Object removed = bot.onlyCall("removeCustomEvent").args[0];

        // The peer wraps the robot's condition in a Bot API one and must remember the pairing.
        // Wrapping afresh on removal would remove a condition the Bot API never had, leaving
        // the original firing forever with nothing to show why.
        assertEquals(added, removed, "removal must pass the instance that was added");
    }

    @Test
    @DisplayName("ROUTE-007 negative: removing an unknown condition does not reach the Bot API")
    void testROUTE007_UnitNegative_RemovingAnUnregisteredConditionIsNotForwarded() {
        peer.removeCustomEvent(new Condition("never-added") {
            @Override
            public boolean test() {
                return false;
            }
        });

        assertFalse(bot.called("removeCustomEvent"),
                "an unknown condition has no Bot API counterpart to remove: " + bot.names());
    }

    @Test
    @DisplayName("ROUTE-007 positive: clearing events reaches the Bot API's clear")
    void testROUTE007_UnitPositive_RoutesClearAllEvents() {
        peer.clearAllEvents();

        assertTrue(bot.called("clearEvents"), bot.names());
    }

    @Test
    @DisplayName("ROUTE-007 positive: event priorities route by mapped event class")
    void testROUTE007_UnitPositive_RoutesEventPriorityByMappedClass() {
        peer.setEventPriority("ScannedRobotEvent", 77);

        RecordingBot.Call call = bot.onlyCall("setEventPriority");
        assertEquals(dev.robocode.tankroyale.botapi.events.ScannedBotEvent.class, call.args[0],
                "the Robocode event class must be mapped to its Tank Royale counterpart");
        assertEquals(77, call.args[1]);
    }

    @Test
    @DisplayName("ROUTE-007 negative: an unsupported event priority is ignored rather than mapped wrongly")
    void testROUTE007_UnitNegative_IgnoresTheUnsupportedPaintEventPriority() {
        // Tank Royale has no paint event. Ignoring is deliberate: mapping it to a neighbour
        // would change the priority of an event the robot did not name.
        peer.setEventPriority("PaintEvent", 50);

        assertFalse(bot.called("setEventPriority"), bot.names());
    }

    @Test
    @DisplayName("ROUTE-007 positive: setInterruptible passes the flag through")
    void testROUTE007_UnitPositive_RoutesSetInterruptible() {
        peer.setInterruptible(true);

        assertEquals(true, bot.onlyCall("setInterruptible").args[0]);
    }

    @Test
    @DisplayName("ROUTE-008 positive: the data directory and a data file resolve against the same place")
    void testROUTE008_UnitPositive_ResolvesDataFileInsideTheDataDirectory() {
        File directory = peer.getDataDirectory();
        File file = peer.getDataFile("state.dat");

        assertNotNull(directory);
        assertNotNull(file);
        // IDR-002: one question about where a robot's data lives should have one answer.
        assertEquals(directory.getAbsolutePath(), file.getParentFile().getAbsolutePath(),
                "a data file must sit inside the directory the robot was given");
    }

    @Test
    @DisplayName("ROUTE-008 positive: the data quota reports the classic limit")
    void testROUTE008_UnitPositive_ReportsTheClassicDataQuota() {
        assertEquals(200_000L, peer.getDataQuotaAvailable(),
                "robots size their writes against this; a different number changes behaviour");
    }

    @Test
    @DisplayName("ROUTE-008 negative: reading events does not consume or clear them")
    void testROUTE008_UnitNegative_ReadingEventsDoesNotClearThem() {
        peer.getAllEvents();

        assertFalse(bot.called("clearEvents"),
                "reading the event list must not discard it: " + bot.names());
    }
}
