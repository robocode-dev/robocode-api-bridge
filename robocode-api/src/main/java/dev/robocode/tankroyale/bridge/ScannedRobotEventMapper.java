package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.events.ScannedBotEvent;
import robocode.ScannedRobotEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRcBearingRad;
import static dev.robocode.tankroyale.bridge.AngleConverter.toRcHeadingRad;
import static java.util.Collections.emptyList;

final class ScannedRobotEventMapper {

    public static List<ScannedRobotEvent> map(List<ScannedBotEvent> scannedBotEvents, IBot bot) {
        if (scannedBotEvents == null) return emptyList();

        return scannedBotEvents.stream()
                .map(event -> map(event, bot))
                .collect(Collectors.toList());
    }

    public static ScannedRobotEvent map(ScannedBotEvent scannedBotEvent, IBot bot) {
        if (scannedBotEvent == null) return null;

        var name = String.valueOf(scannedBotEvent.getScannedBotId());
        var energy = scannedBotEvent.getEnergy();
        var x = scannedBotEvent.getX();
        var y = scannedBotEvent.getY();
        var bearing = toRcBearingRad(bot.bearingTo(x, y));
        var distance = bot.distanceTo(x, y);
        var velocity = scannedBotEvent.getSpeed();
        var heading = toRcHeadingRad(scannedBotEvent.getDirection());

        // `isSentryRobot` is unsupported
        var event = new ScannedRobotEvent(name, energy, bearing, distance, heading, velocity, false);
        event.setTime(scannedBotEvent.getTurnNumber());
        return event;
    }
}