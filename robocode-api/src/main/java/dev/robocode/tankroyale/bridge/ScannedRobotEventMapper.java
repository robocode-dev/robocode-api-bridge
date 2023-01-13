package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.events.ScannedBotEvent;
import robocode.ScannedRobotEvent;

import java.util.ArrayList;
import java.util.List;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRcBearingRad;
import static dev.robocode.tankroyale.bridge.AngleConverter.toRcHeadingRad;
import static java.util.Collections.emptyList;

final class ScannedRobotEventMapper {

    public static List<ScannedRobotEvent> map(List<ScannedBotEvent> scannedBotEvents, IBot bot) {
        if (scannedBotEvents == null) return emptyList();

        var events = new ArrayList<ScannedRobotEvent>();
        scannedBotEvents.forEach(event -> events.add(map(event, bot)));
        return events;
    }

    public static ScannedRobotEvent map(ScannedBotEvent scannedBotEvent, IBot bot) {
        if (scannedBotEvent == null) return null;

        String name = "" + scannedBotEvent.getScannedBotId();
        double energy = scannedBotEvent.getEnergy();
        double x = scannedBotEvent.getX();
        double y = scannedBotEvent.getY();
        double bearing = toRcBearingRad(bot.bearingTo(x, y));
        double distance = bot.distanceTo(x, y);
        double velocity = scannedBotEvent.getSpeed();
        double heading = toRcHeadingRad(scannedBotEvent.getDirection());

        // `isSentryRobot` is unsupported
        return new ScannedRobotEvent(name, energy, bearing, distance, heading, velocity, false);
    }
}