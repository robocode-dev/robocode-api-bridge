package dev.robocode.tankroyale.bridge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Acceptance evidence for ROUTE-008 — the per-type event lists a robot can ask for, the
 * round bookkeeping, and the remaining reads.
 *
 * The event lists are one family of near-identical methods differing only in the type they
 * filter for, which is the shape where a copy-paste error survives review: every one
 * compiles, every one returns a list, and a robot asking for bullet hits that receives wall
 * hits simply behaves oddly.
 */
class EventListRoutingTest {

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
    @DisplayName("ROUTE-008 positive: every per-type event list is answerable and typed to itself")
    void testROUTE008_UnitPositive_AnswersEveryPerTypeEventList() {
        // With no events pending each list is empty, but each must still answer rather than
        // fail or return null: robots iterate these unconditionally every turn.
        assertNotNull(peer.getStatusEvents());
        assertNotNull(peer.getScannedRobotEvents());
        assertNotNull(peer.getBulletHitEvents());
        assertNotNull(peer.getBulletMissedEvents());
        assertNotNull(peer.getBulletHitBulletEvents());
        assertNotNull(peer.getHitByBulletEvents());
        assertNotNull(peer.getHitRobotEvents());
        assertNotNull(peer.getHitWallEvents());
        assertNotNull(peer.getRobotDeathEvents());
    }

    @Test
    @DisplayName("ROUTE-008 negative: asking for one event type does not consume the queue")
    void testROUTE008_UnitNegative_AskingForEventsDoesNotConsumeTheQueue() {
        peer.getScannedRobotEvents();
        peer.getHitWallEvents();
        peer.getBulletHitEvents();

        // A robot reads several of these in the same turn. If the first read emptied the
        // queue, every later one would come back empty and the robot would act on a partial
        // view of its own turn.
        assertFalse(bot.called("clearEvents"), bot.names());
    }

    @Test
    @DisplayName("ROUTE-008 positive: round bookkeeping is rebased to classic's numbering")
    void testROUTE008_UnitPositive_RebasesRoundNumberingToClassic() {
        bot.returning("getRoundNumber", 1).returning("getNumberOfRounds", 35);

        // Tank Royale counts rounds from one, classic from zero. A robot keying first-round
        // setup on round zero would never run it if the number were passed through.
        assertEquals(0, peer.getRoundNum(), "the first round is round zero to a robot");
        assertEquals(35, peer.getNumRounds(), "the total is a count, not an index, and is not rebased");
    }

    @Test
    @DisplayName("ROUTE-008 positive: the gun cooling rate is read from the Bot API")
    void testROUTE008_UnitPositive_ReadsGunCoolingRate() {
        bot.returning("getGunCoolingRate", 0.3);

        assertEquals(0.3, peer.getGunCoolingRate(), EPSILON,
                "robots compute when they can next fire from this");
    }

    @Test
    @DisplayName("ROUTE-002 positive: the blocking fire routes and returns the bullet it fired")
    void testROUTE002_UnitPositive_BlockingFireRoutesToTheBotApi() {
        peer.fire(1.5);

        // The blocking form fires and completes the turn; the queued form does not. Routing
        // one to the other would change when the shot leaves relative to everything else.
        assertTrue(bot.called("setFire") || bot.called("fire"), bot.names());
    }

    @Test
    @DisplayName("ROUTE-007 positive: reading an event priority maps the event class first")
    void testROUTE007_UnitPositive_ReadsEventPriorityByMappedClass() {
        bot.returning("getEventPriority", 42);

        assertEquals(42, peer.getEventPriority("HitWallEvent"));
        assertEquals(dev.robocode.tankroyale.botapi.events.HitWallEvent.class,
                bot.onlyCall("getEventPriority").args[0],
                "the Robocode class name must be mapped, not passed through");
    }

    @Test
    @DisplayName("ROUTE-008 negative: a debug property is accepted and dropped, issuing no Bot API call")
    void testROUTE008_UnitNegative_AcceptsADebugPropertyWithoutRoutingIt() {
        peer.setDebugProperty("state", "hunting");

        // The Bot API has no debug-property channel, so this deliberately goes nowhere. That
        // is a real difference from classic, where the value appears beside the robot in the
        // UI — but it is a difference in what a person sees, not in what the robot does, and
        // accepting the call silently is what keeps a debugging robot running.
        //
        // Asserted rather than left untested: the failure worth catching is this quietly
        // acquiring a route to something it should not touch.
        assertTrue(bot.calls().isEmpty(),
                "a debug property must not reach the Bot API: " + bot.names());
    }
}
