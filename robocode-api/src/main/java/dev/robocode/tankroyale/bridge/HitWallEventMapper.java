package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.events.HitWallEvent;

import java.util.ArrayList;
import java.util.List;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRcRadians;
import static java.lang.Math.abs;
import static java.util.Collections.emptyList;

public class HitWallEventMapper {

    public static List<robocode.HitWallEvent> map(List<HitWallEvent> hitWallEvents, IBot bot) {
        if (hitWallEvents == null) return emptyList();

        List<robocode.HitWallEvent> events = new ArrayList<>();
        hitWallEvents.forEach(event -> events.add(map(event, bot)));
        return events;
    }

    public static robocode.HitWallEvent map(HitWallEvent hitWallEvent, IBot bot) {
        if (hitWallEvent == null) return null;

        double bearing = toRcRadians(bot.calcBearing(calcDirectionNearestToWall(bot)));
        return new robocode.HitWallEvent(bearing);
    }

    private static double calcDirectionNearestToWall(IBot bot) {
        double dx = bot.getX() - (bot.getArenaWidth() / 2.);
        double dy = bot.getY() - (bot.getArenaHeight() / 2.);

        return abs(dx) > abs(dy) ?
                dx >= 0 ? 0 : 180 :
                dy >= 0 ? 90 : 270;
    }
}
