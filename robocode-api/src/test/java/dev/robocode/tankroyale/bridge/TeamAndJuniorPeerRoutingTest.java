package dev.robocode.tankroyale.bridge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dev.robocode.tankroyale.botapi.BotException;
import dev.robocode.tankroyale.botapi.events.TeamMessageEvent;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.Serializable;
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
 * Robocode addresses teammates by name while Tank Royale routes by numeric id. The bridge uses
 * Tank Royale's name map to preserve classic names and translates names back to ids for directed
 * messages and teammate checks.
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
    @DisplayName("ROUTE-009 positive: one broadcast uses the classic single-message call")
    void testROUTE009_UnitPositive_PreservesTheSingleBroadcastCall() throws IOException {
        peer.broadcastMessage("attack");

        assertFalse(bot.called("broadcastTeamMessage"),
                "messages stay queued until the classic turn is submitted");

        peer.execute();

        assertEquals("attack", bot.onlyCall("broadcastTeamMessage").args[0],
                "a single broadcast keeps its original payload and API call");
        assertFalse(bot.called("broadcastTeamMessageBatch"));
    }

    @Test
    @DisplayName("ROUTE-009 positive: multiple turn messages are sent in one ordered batch")
    void testROUTE009_UnitPositive_BatchesMultipleMessagesInOrder() throws IOException {
        bot.returning("isTeammate", true);
        peer.broadcastMessage("attack");
        peer.sendMessage("7", "regroup");

        peer.execute();

        List<?> messages = (List<?>) bot.onlyCall("broadcastTeamMessageBatch").args[0];
        assertEquals("attack", messages.get(0));
        var routed = (BridgeTeamMessage.RoutedMessage) ((BridgeTeamMessage) messages.get(1)).decode();
        assertEquals(7, routed.getRecipientId());
        assertEquals("regroup", routed.getMessage());
    }

    @Test
    @DisplayName("ROUTE-009 positive: getName returns the classic name from Tank Royale")
    void testROUTE009_UnitPositive_ReportsClassicBotName() {
        bot.named(0, "legacy.TeamRobot 1.2 (1)");

        assertEquals("legacy.TeamRobot 1.2 (1)", peer.getName());
    }

    @Test
    @DisplayName("ROUTE-009 positive: older Bot APIs fall back to the local robot name")
    void testROUTE009_UnitPositive_FallsBackWhenNameMapApiIsUnavailable() {
        bot = RecordingBot.createWithoutNameMap();
        peer = new BotPeer(new StubRobot(), bot.asBot());
        bot.clear();

        assertEquals("StubRobot", peer.getName());
    }

    @Test
    @DisplayName("ROUTE-009 positive: a message to one teammate is addressed by its numeric id")
    void testROUTE009_UnitPositive_AddressesASingleTeammateByNumericId() throws IOException {
        bot.returning("isTeammate", true);
        peer.sendMessage("7", "regroup");

        peer.execute();

        Object[] args = bot.onlyCall("sendTeamMessage").args;
        assertEquals(7, args[0], "Robocode names teammates; Tank Royale numbers them");
        assertEquals("regroup", args[1]);
    }

    @Test
    @DisplayName("ROUTE-009 positive: a directed message by classic name reaches that teammate")
    void testROUTE009_UnitPositive_AddressesTeammateByClassicName() throws IOException {
        String teammateName = "legacy.TeamRobot 1.2 (1)";
        bot.named(7, teammateName).returning("getTeammateIds", Set.of(7)).returning("isTeammate", true);

        peer.sendMessage(teammateName, "regroup");

        peer.execute();

        Object[] args = bot.onlyCall("sendTeamMessage").args;
        assertEquals(7, args[0]);
        assertEquals("regroup", args[1]);
    }

    @Test
    @DisplayName("ROUTE-009 negative: another teammate does not receive a directed message")
    void testROUTE009_UnitNegative_HidesDirectedMessageFromOtherTeammates() throws IOException {
        bot.returning("getMyId", 4);
        var routed = BridgeTeamMessage.forRecipient(3, "flank left");
        var event = new TeamMessageEvent(3,
                new dev.robocode.tankroyale.botapi.TeamMessageBatch(List.of(routed)), 9);
        bot.named(9, "sender").returning("getEvents", List.of(event));

        assertEquals(List.of(), peer.getMessageEvents());
    }

    @Test
    @DisplayName("ROUTE-009 negative: an unaddressable teammate name is refused, not silently dropped")
    void testROUTE009_UnitNegative_RefusesAnUnaddressableTeammateName() {
        // A name outside this team's name map has no Tank Royale id. Failing loudly is right:
        // a silently dropped message leaves the sender believing its team was told.
        assertThrows(BotException.class, () -> peer.sendMessage("Leader", "regroup"));
        assertFalse(bot.called("sendTeamMessage"), bot.names());
    }

    @Test
    @DisplayName("ROUTE-009 negative: a non-serializable team payload preserves classic's failure")
    void testROUTE009_UnitNegative_PreservesLegacySerializationFailure() {
        assertThrows(NotSerializableException.class,
                () -> peer.broadcastMessage(new NonSerializableMessage()));
        assertFalse(bot.called("broadcastTeamMessage"), bot.names());
    }

    @Test
    @DisplayName("ROUTE-009 positive: teammates are reported with their classic names")
    void testROUTE009_UnitPositive_ReportsTeammatesAsNames() {
        bot.named(4, "legacy.TeamRobot 1.2 (1)")
                .returning("getTeammateIds", Set.of(4));

        assertArrayEquals(new String[] { "legacy.TeamRobot 1.2 (1)" }, peer.getTeammates());
    }

    @Test
    @DisplayName("ROUTE-009 positive: teammate ids remain a fallback when the name map is unavailable")
    void testROUTE009_UnitPositive_FallsBackToNumericTeammateIds() {
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
    @DisplayName("ROUTE-009 positive: a teammate check accepts the classic name")
    void testROUTE009_UnitPositive_ChecksTeammateByClassicName() {
        String teammateName = "legacy.TeamRobot 1.2 (1)";
        bot.named(9, teammateName)
                .returning("getTeammateIds", Set.of(9))
                .returning("isTeammate", true);

        assertTrue(peer.isTeammate(teammateName));
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
    @DisplayName("ROUTE-009 positive: a message event exposes its sender's classic name")
    void testROUTE009_UnitPositive_MapsMessageSenderToClassicName() {
        String senderName = "legacy.TeamRobot 1.2 (1)";
        bot.named(12, senderName)
                .returning("getEvents", List.of(new TeamMessageEvent(3, "attack", 12)));

        List<robocode.MessageEvent> events = peer.getMessageEvents();

        assertEquals(1, events.size());
        assertEquals(senderName, events.get(0).getSender());
        assertEquals("attack", events.get(0).getMessage());
    }

    @Test
    @DisplayName("ROUTE-009 positive: a batch becomes ordered classic message events for its recipient")
    void testROUTE009_UnitPositive_ExpandsAnOrderedBatchForItsRecipient() throws IOException {
        String senderName = "legacy.TeamRobot 1.2 (1)";
        var event = new TeamMessageEvent(3,
                new dev.robocode.tankroyale.botapi.TeamMessageBatch(List.of(
                        "attack",
                        BridgeTeamMessage.forRecipient(5, "regroup"),
                        BridgeTeamMessage.forRecipient(6, "private"))),
                12);
        bot.named(12, senderName)
                .returning("getMyId", 5)
                .returning("getEvents", List.of(event));

        List<robocode.MessageEvent> events = peer.getMessageEvents();

        assertEquals(2, events.size());
        assertEquals("attack", events.get(0).getMessage());
        assertEquals("regroup", events.get(1).getMessage());
        assertEquals(senderName, events.get(0).getSender());
        assertEquals(senderName, events.get(1).getSender());
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

    private static final class NonSerializableMessage implements Serializable {
        private final Object payload = new Object();
    }
}
