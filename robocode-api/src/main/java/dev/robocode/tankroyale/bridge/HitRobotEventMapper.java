package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.events.HitBotEvent;
import robocode.HitRobotEvent;

import java.util.ArrayList;
import java.util.List;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRcBearingToRadians;
import static java.util.Collections.emptyList;

final class HitRobotEventMapper {

    public static List<HitRobotEvent> map(List<HitBotEvent> hitBotEvents, IBot bot) {
        if (hitBotEvents == null) return emptyList();

        List<robocode.HitRobotEvent> events = new ArrayList<>();
        hitBotEvents.forEach(event -> events.add(map(event, bot)));
        return events;
    }

    public static HitRobotEvent map(HitBotEvent hitBotEvent, IBot bot) {
        if (hitBotEvent == null) return null;

        String name = "" + hitBotEvent.getVictimId();
        double bearing = toRcBearingToRadians(bot.bearingTo(hitBotEvent.getX(), hitBotEvent.getY()));

        return new HitRobotEvent(name, bearing, hitBotEvent.getEnergy(), hitBotEvent.isRammed());
    }
}
