package dev.robocode.tankroyale.bridge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dev.robocode.tankroyale.botapi.BotException;

import java.util.List;
import java.util.Set;

import static java.lang.Math.toRadians;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Acceptance evidence for ROUTE-009 and ROUTE-010 — the {@code ITeamRobotPeer} and
 * {@code IJuniorRobotPeer} surfaces.
 *
 * The team surface routes today even though the wrapper cannot yet produce team bot
 * directories ({@code CAP-006}). That is worth testing now rather than later: when team
 * support does land, these calls are what it will run on, and a routing fault discovered
 * then would look like a fault in the new work.
 *
 * The identity translation is the interesting part. Robocode addresses teammates by name and
 * Tank Royale by numeric id, so the peer parses one into the other — and what it does with a
 * name that is not a number differs between the two calls.
 */
class TeamAndJuniorPeerRoutingTest {

    private RecordingBot bot;
    private BotPeer peer;

    @BeforeEach
    void setUp() {
        bot = RecordingBot.create();
        peer = new BotPeer(new StubRobot(), bot.asBot());
        bot.clear();
    }

    @Test
    @DisplayName("ROUTE-009 positive: broadcasting reaches the team broadcast with the message intact")
    void testROUTE009_UnitPositive_RoutesBroadcastWithTheMessageIntact() {
        peer.broadcastMessage("attack");

        assertEquals("attack", bot.onlyCall("broadcastTeamMessage").args[0],
                "the message must arrive as sent, not serialized or wrapped on the way");
    }

    @Test
    @DisplayName("ROUTE-009 positive: a message to one teammate is addressed by its numeric id")
    void testROUTE009_UnitPositive_AddressesASingleTeammateByNumericId() {
        peer.sendMessage("7", "regroup");

        RecordingBot.Call call = bot.onlyCall("sendTeamMessage");
        assertEquals(7, call.args[0], "Robocode names teammates; Tank Royale numbers them");
        assertEquals("regroup", call.args[1]);
    }

    @Test
    @DisplayName("ROUTE-009 negative: a directed message does not become a broadcast")
    void testROUTE009_UnitNegative_ADirectedMessageIsNotBroadcast() {
        peer.sendMessage("3", "flank left");

        // Turning a private instruction into a broadcast would tell the whole team something
        // meant for one member, which changes the team's behaviour without any error.
        assertFalse(bot.called("broadcastTeamMessage"), bot.names());
    }

    @Test
    @DisplayName("ROUTE-009 negative: an unaddressable teammate name is refused, not silently dropped")
    void testROUTE009_UnitNegative_RefusesAnUnaddressableTeammateName() {
        // A name that is not a number has no Tank Royale id. Failing loudly is right: a
        // silently dropped message leaves the sender believing its team was told.
        assertThrows(BotException.class, () -> peer.sendMessage("Leader", "regroup"));
        assertFalse(bot.called("sendTeamMessage"), bot.names());
    }

    @Test
    @DisplayName("ROUTE-009 positive: teammates are reported as names built from their ids")
    void testROUTE009_UnitPositive_ReportsTeammatesAsNames() {
        bot.returning("getTeammateIds", Set.of(4));

        assertArrayEquals(new String[] { "4" }, peer.getTeammates());
    }

    @Test
    @DisplayName("ROUTE-009 negative: no teammates reports absence rather than an empty team")
    void testROUTE009_UnitNegative_ReportsAbsentTeammatesAsNull() {
        bot.returningNull("getTeammateIds");

        // Classic returns null for a robot with no team, and robots branch on it. An empty
        // array would tell a lone robot it is in a team of nobody.
        assertNull(peer.getTeammates());
    }

    @Test
    @DisplayName("ROUTE-009 positive: a teammate check routes by id")
    void testROUTE009_UnitPositive_ChecksTeammateByNumericId() {
        bot.returning("isTeammate", true);

        assertTrue(peer.isTeammate("9"));
        assertEquals(9, bot.onlyCall("isTeammate").args[0]);
    }

    @Test
    @DisplayName("ROUTE-009 negative: an unparseable name is not a teammate and asks nothing")
    void testROUTE009_UnitNegative_TreatsAnUnparseableNameAsNotATeammate() {
        assertFalse(peer.isTeammate("Leader"));
        assertFalse(bot.called("isTeammate"),
                "there is no id to ask about, so the Bot API is not asked: " + bot.names());
    }

    @Test
    @DisplayName("ROUTE-009 positive: message events are read from the Bot API's events")
    void testROUTE009_UnitPositive_ReadsMessageEventsFromTheBotApi() {
        List<robocode.MessageEvent> events = peer.getMessageEvents();

        assertEquals(List.of(), events, "no events yet, but the call must not fail");
    }

    @Test
    @DisplayName("ROUTE-010 positive: the junior turn-and-move reaches the Bot API")
    void testROUTE010_UnitPositive_RoutesTheJuniorTurnAndMove() {
        peer.turnAndMove(80, toRadians(45));

        assertTrue(bot.calls().stream()
                        .anyMatch(call -> call.name.equals("setForward")
                                && call.args.length == 1
                                && call.doubleArg(0) == 80),
                "the junior move must pass its distance through unchanged: " + bot.calls());
        assertTrue(bot.calls().stream()
                        .anyMatch(call -> call.name.equals("setTurnRight")
                                && call.args.length == 1
                                && call.doubleArg(0) == 45),
                "the junior turn must reach the Bot API as 45 degrees: " + bot.calls());
    }

    @Test
    @DisplayName("ROUTE-010 negative: the angle is converted rather than passed through as radians")
    void testROUTE010_UnitNegative_DoesNotPassTheAngleThroughAsRadians() {
        peer.turnAndMove(80, toRadians(45));

        // A distance is a distance and an angle is an angle: only one of them converts. Passing
        // 0.785 into a degrees parameter turns the robot by about three quarters of a degree
        // where it asked for forty-five, every time, with nothing to show why.
        assertTrue(bot.calls().stream()
                        .anyMatch(call -> call.name.equals("setTurnRight")
                                && call.args.length == 1
                                && call.doubleArg(0) == 45),
                "the turn must receive 45 degrees, not the raw radian value: " + bot.calls());
    }
}
