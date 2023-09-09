package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.events.HitBotEvent;
import robocode.HitRobotEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRcBearingRad;
import static java.util.Collections.emptyList;

final class HitRobotEventMapper {

    public static List<HitRobotEvent> map(List<HitBotEvent> hitBotEvents, IBot bot) {
        if (hitBotEvents == null) return emptyList();

        return hitBotEvents.stream()
                .map(event -> map(event, bot))
                .collect(Collectors.toList());
    }

    public static HitRobotEvent map(HitBotEvent hitBotEvent, IBot bot) {
        if (hitBotEvent == null) return null;

        var name = String.valueOf(hitBotEvent.getVictimId());
        var bearing = toRcBearingRad(bot.bearingTo(hitBotEvent.getX(), hitBotEvent.getY()));

        var event = new HitRobotEvent(name, bearing, hitBotEvent.getEnergy(), hitBotEvent.isRammed());
        event.setTime(hitBotEvent.getTurnNumber());
        return event;
    }
}
