package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.events.HitBotEvent;
import robocode.HitRobotEvent;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRobocodeBearingRad;

final class HitRobotEventMapper {

    public static HitRobotEvent map(HitBotEvent hitBotEvent, IBot bot) {
        if (hitBotEvent == null) return null;

        var name = String.valueOf(hitBotEvent.getVictimId());
        var bearing = toRobocodeBearingRad(bot.bearingTo(hitBotEvent.getX(), hitBotEvent.getY()));

        var event = new HitRobotEvent(name, bearing, hitBotEvent.getEnergy(), hitBotEvent.isRammed());
        event.setTime(hitBotEvent.getTurnNumber());

        return event;
    }
}
