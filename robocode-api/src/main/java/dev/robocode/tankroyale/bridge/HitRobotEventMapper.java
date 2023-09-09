package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.events.HitBotEvent;
import robocode.HitRobotEvent;

import java.util.ArrayList;
import java.util.List;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRcBearingRad;
import static java.util.Collections.emptyList;

final class HitRobotEventMapper {

    public static List<HitRobotEvent> map(List<HitBotEvent> hitBotEvents, IBot bot) {
        if (hitBotEvents == null) return emptyList();

        var events = new ArrayList<robocode.HitRobotEvent>();
        hitBotEvents.forEach(event -> events.add(map(event, bot)));
        return events;
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
