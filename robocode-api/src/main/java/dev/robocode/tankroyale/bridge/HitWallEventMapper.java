package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.events.HitWallEvent;

import java.util.ArrayList;
import java.util.List;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRcBearingRad;
import static java.lang.Math.abs;
import static java.util.Collections.emptyList;

final class HitWallEventMapper {

    public static List<robocode.HitWallEvent> map(List<HitWallEvent> hitWallEvents, IBot bot) {
        if (hitWallEvents == null) return emptyList();

        var events = new ArrayList<robocode.HitWallEvent>();
        hitWallEvents.forEach(event -> events.add(map(event, bot)));
        return events;
    }

    public static robocode.HitWallEvent map(HitWallEvent hitWallEvent, IBot bot) {
        if (hitWallEvent == null) return null;

        double bearing = toRcBearingRad(bot.calcBearing(calcDirectionNearestToWall(bot)));
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
