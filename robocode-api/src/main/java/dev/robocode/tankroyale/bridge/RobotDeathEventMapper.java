package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.BotDeathEvent;
import robocode.RobotDeathEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

final class RobotDeathEventMapper {

    public static List<RobotDeathEvent> map(List<BotDeathEvent> botDeathEvents) {
        if (botDeathEvents == null) return emptyList();

        return botDeathEvents.stream()
                .map(RobotDeathEventMapper::map)
                .collect(Collectors.toList());
    }

    public static RobotDeathEvent map(BotDeathEvent botDeathEvent) {
        if (botDeathEvent == null) return null;

        var name = String.valueOf(botDeathEvent.getVictimId());

        var event = new RobotDeathEvent(name);
        event.setTime(botDeathEvent.getTurnNumber());
        return event;
    }
}