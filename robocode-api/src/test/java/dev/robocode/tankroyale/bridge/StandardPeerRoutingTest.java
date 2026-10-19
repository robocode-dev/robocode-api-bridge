package dev.robocode.tankroyale.bridge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.lang.Math.toRadians;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Acceptance evidence for ROUTE-013 — the {@code IStandardRobotPeer} surface: stopping,
 * resuming, turning the radar, and the three independence flags.
 *
 * The flags are the interesting part. Each decouples one component from another's rotation,
 * they are near-identically named, and a robot that gets the wrong one still fights — its
 * aim simply drifts. That is the profile of every unexplained score gap in the collection.
 */
class StandardPeerRoutingTest {

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
    @DisplayName("ROUTE-013 positive: stopping and resuming route to their Bot API calls")
    void testROUTE013_UnitPositive_RoutesStopAndResume() {
        peer.stop(false);
        assertTrue(bot.called("stop"), bot.names());
        assertEquals(Boolean.FALSE, bot.onlyCall("stop").args[0]);

        bot.clear();
        peer.resume();
        assertTrue(bot.called("resume"), bot.names());
    }

    @Test
    @DisplayName("ROUTE-013 positive: stop overwrite reaches the Bot API with its argument")
    void testROUTE013_UnitPositive_RoutesStopOverwriteArgument() {
        peer.stop(true);

        assertEquals(Boolean.TRUE, bot.onlyCall("stop").args[0]);
    }

    @Test
    @DisplayName("ROUTE-013 negative: stop overwrite does not call a neighboring Bot API method")
    void testROUTE013_UnitNegative_DoesNotCallAnotherStopAction() {
        peer.stop(true);

        assertEquals(1, bot.calls().size(), bot.names());
        assertEquals("stop", bot.calls().get(0).name);
        assertEquals(Boolean.TRUE, bot.calls().get(0).args[0]);
    }

    @Test
    @DisplayName("ROUTE-013 positive: turning the radar reaches the radar call in degrees")
    void testROUTE013_UnitPositive_RoutesRadarTurnInDegrees() {
        peer.turnRadar(toRadians(120));

        assertEquals(120.0, bot.onlyCall("turnRadarRight").doubleArg(0), EPSILON);
    }

    @Test
    @DisplayName("ROUTE-013 positive: each independence flag reaches its own Bot API setter")
    void testROUTE013_UnitPositive_RoutesEachIndependenceFlagToItsOwnSetter() {
        peer.setAdjustGunForBodyTurn(true);
        assertEquals(true, bot.onlyCall("setAdjustGunForBodyTurn").args[0]);

        bot.clear();
        peer.setAdjustRadarForGunTurn(true);
        assertEquals(true, bot.onlyCall("setAdjustRadarForGunTurn").args[0]);

        bot.clear();
        peer.setAdjustRadarForBodyTurn(true);
        assertEquals(true, bot.onlyCall("setAdjustRadarForBodyTurn").args[0]);
    }

    @Test
    @DisplayName("ROUTE-013 negative: setting one independence flag sets no other")
    void testROUTE013_UnitNegative_SettingOneIndependenceFlagLeavesTheOthersAlone() {
        peer.setAdjustRadarForGunTurn(true);

        assertFalse(bot.called("setAdjustGunForBodyTurn"), bot.names());
        assertFalse(bot.called("setAdjustRadarForBodyTurn"), bot.names());
    }

    @Test
    @DisplayName("ROUTE-013 positive: the flag value is passed through rather than assumed true")
    void testROUTE013_UnitPositive_PassesTheFlagValueThroughRatherThanAssumingTrue() {
        // A routing that ignored its argument would pass every test that only ever sets true,
        // and would leave a robot unable to turn the independence off again.
        peer.setAdjustGunForBodyTurn(false);

        assertEquals(false, bot.onlyCall("setAdjustGunForBodyTurn").args[0]);
    }

    @Test
    @DisplayName("ROUTE-013 positive: reading an independence flag reads the matching Bot API flag")
    void testROUTE013_UnitPositive_ReadsEachIndependenceFlagFromItsOwnSource() {
        bot.returning("isAdjustGunForBodyTurn", true)
           .returning("isAdjustRadarForGunTurn", false)
           .returning("isAdjustRadarForBodyTurn", true);

        assertTrue(peer.isAdjustGunForBodyTurn());
        assertFalse(peer.isAdjustRadarForGunTurn());
        assertTrue(peer.isAdjustRadarForBodyTurn());
    }
}
