package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.events.ScannedBotEvent;
import dev.robocode.tankroyale.botapi.events.TeamMessageEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import robocode.MessageEvent;
import robocode.ScannedRobotEvent;
import robocode.RobotStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Acceptance evidence for API-008 — scans and teammate messages share Classic's zero-based time. */
class EventTimeMappingTest {

    @Test
    @DisplayName("API-008 positive: scan and teammate message times agree with robot status")
    void testAPI008_UnitPositive_MapsScansAndMessagesToClassicTime() {
        assertTimesMatchStatus(4, 3L);
    }

    @Test
    @DisplayName("API-008 negative: the first events do not expose Tank Royale's one-based turn")
    void testAPI008_UnitNegative_DoesNotExposeOneBasedTimeOnFirstTurn() {
        assertTimesMatchStatus(1, 0L);
    }

    private static void assertTimesMatchStatus(int tankRoyaleTurn, long expectedClassicTime) {
        RecordingBot recordingBot = RecordingBot.create()
                .named(27, "legacy.TeamRobot 1.2 (1)")
                .returning("getTurnNumber", tankRoyaleTurn);
        IBot bot = recordingBot.asBot();
        RobotStatus status = IBotToRobotStatusMapper.map(bot);
        ScannedRobotEvent scan = ScannedRobotEventMapper.map(
                new ScannedBotEvent(tankRoyaleTurn, 2, 27, 51, 103, 207, 5, 39), bot);
        List<MessageEvent> messages = MessageEventMapper.map(
                new TeamMessageEvent(tankRoyaleTurn, "status", 27), bot);

        assertEquals(expectedClassicTime, status.getTime());
        assertEquals(status.getTime(), scan.getTime());
        assertEquals(1, messages.size());
        assertEquals(status.getTime(), messages.get(0).getTime());
    }
}
