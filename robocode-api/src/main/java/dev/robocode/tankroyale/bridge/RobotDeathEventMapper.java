package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.BotDeathEvent;
import robocode.RobotDeathEvent;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

final class RobotDeathEventMapper {

    public static List<RobotDeathEvent> map(List<BotDeathEvent> botDeathEvents) {
        if (botDeathEvents == null) return emptyList();

        List<RobotDeathEvent> events = new ArrayList<>();
        botDeathEvents.forEach(event -> events.add(map(event)));
        return events;
    }

    public static RobotDeathEvent map(BotDeathEvent botDeathEvent) {
        if (botDeathEvent == null) return null;

        String name = "" + botDeathEvent.getVictimId();
        return new RobotDeathEvent(name);
    }
}