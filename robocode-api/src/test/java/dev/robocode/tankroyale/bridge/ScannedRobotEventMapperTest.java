package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.ScannedBotEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import robocode.ScannedRobotEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/** Acceptance evidence for API-006 — scan events retain classic target names across callbacks. */
class ScannedRobotEventMapperTest {

    @Test
    @DisplayName("API-006 positive: repeated scans reuse the classic name-map string")
    void testAPI006_UnitPositive_PreservesStableClassicTargetNameIdentity() {
        String classicName = new String("apc.Colossus2 0.12 (2)");
        RecordingBot bot = RecordingBot.create().named(27, classicName);

        ScannedRobotEvent first = map(bot, 27);
        ScannedRobotEvent second = map(bot, 27);

        assertEquals(classicName, first.getName());
        assertSame(first.getName(), second.getName(),
                "legacy robots can identify a repeatedly scanned target by the same String reference");
    }

    @Test
    @DisplayName("API-006 positive: the numeric fallback is also stable when the name map is absent")
    void testAPI006_UnitPositive_PreservesFallbackTargetNameIdentity() {
        RecordingBot bot = RecordingBot.createWithoutNameMap();

        ScannedRobotEvent first = map(bot, 27);
        ScannedRobotEvent second = map(bot, 27);

        assertEquals("27", first.getName());
        assertSame(first.getName(), second.getName());
    }

    private static ScannedRobotEvent map(RecordingBot bot, int botId) {
        var event = new ScannedBotEvent(12, 0, botId, 50, 100, 200, 1, 3);
        return ScannedRobotEventMapper.map(event, bot.asBot());
    }
}
