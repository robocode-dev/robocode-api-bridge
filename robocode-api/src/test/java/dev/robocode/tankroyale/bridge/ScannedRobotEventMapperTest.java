package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.ScannedBotEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import robocode.ScannedRobotEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/** Acceptance evidence for API-007 — scan events retain visible names and alias unnamed targets. */
class ScannedRobotEventMapperTest {

    @Test
    @DisplayName("API-007 negative: a visible name is preserved instead of being replaced by an alias")
    void testAPI007_UnitNegative_PreservesVisibleClassicTargetName() {
        String classicName = new String("apc.Colossus2 0.12 (2)");
        RecordingBot bot = RecordingBot.create().named(27, classicName);

        ScannedRobotEvent first = map(bot, 27);
        ScannedRobotEvent second = map(bot, 27);

        assertEquals(classicName, first.getName());
        assertSame(first.getName(), second.getName(), "the visible name remains stable across scans");
    }

    @Test
    @DisplayName("API-007 positive: an unnamed target gets a stable Classic-shaped alias")
    void testAPI007_UnitPositive_AliasesAnUnmappedTargetWithClassicNameShape() {
        RecordingBot bot = RecordingBot.createWithoutNameMap();

        ScannedRobotEvent first = map(bot, 27);
        ScannedRobotEvent second = map(bot, 27);
        ScannedRobotEvent differentBot = map(bot, 28);

        assertEquals("tankroyale.anonymous.Bot27 1.0", first.getName());
        assertSame(first.getName(), second.getName());
        assertNotEquals(first.getName(), differentBot.getName());
    }

    private static ScannedRobotEvent map(RecordingBot bot, int botId) {
        var event = new ScannedBotEvent(12, 0, botId, 50, 100, 200, 1, 3);
        return ScannedRobotEventMapper.map(event, bot.asBot());
    }
}
