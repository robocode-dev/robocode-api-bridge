package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.events.ScannedBotEvent;
import robocode.ScannedRobotEvent;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRobocodeBearingRad;
import static dev.robocode.tankroyale.bridge.AngleConverter.toRobocodeHeadingRad;

final class ScannedRobotEventMapper {

    public static ScannedRobotEvent map(ScannedBotEvent scannedBotEvent, IBot bot) {
        if (scannedBotEvent == null) return null;

        var name = String.valueOf(scannedBotEvent.getScannedBotId());
        var energy = scannedBotEvent.getEnergy();
        var x = scannedBotEvent.getX();
        var y = scannedBotEvent.getY();
        var bearing = toRobocodeBearingRad(bot.bearingTo(x, y));
        var distance = bot.distanceTo(x, y);
        var velocity = scannedBotEvent.getSpeed();
        var heading = toRobocodeHeadingRad(scannedBotEvent.getDirection());

        // `isSentryRobot` is unsupported
        var event = new ScannedRobotEvent(name, energy, bearing, distance, heading, velocity, false);
        event.setTime(scannedBotEvent.getTurnNumber());

        return event;
    }
}