package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.BotDeathEvent;
import robocode.RobotDeathEvent;

final class RobotDeathEventMapper {

    public static RobotDeathEvent map(BotDeathEvent botDeathEvent) {
        if (botDeathEvent == null) return null;

        var name = String.valueOf(botDeathEvent.getVictimId());

        var event = new RobotDeathEvent(name);
        event.setTime(botDeathEvent.getTurnNumber());

        return event;
    }
}