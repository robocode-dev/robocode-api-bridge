package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.events.HitWallEvent;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRcBearingRad;
import static java.lang.Math.abs;

final class HitWallEventMapper {

    public static robocode.HitWallEvent map(HitWallEvent hitWallEvent, IBot bot) {
        if (hitWallEvent == null) return null;

        var bearing = toRcBearingRad(bot.calcBearing(calcDirectionNearestToWall(bot)));

        var event = new robocode.HitWallEvent(bearing);
        event.setTime(hitWallEvent.getTurnNumber());
        return event;
    }

    private static double calcDirectionNearestToWall(IBot bot) {
        double dx = bot.getX() - (bot.getArenaWidth() / 2.);
        double dy = bot.getY() - (bot.getArenaHeight() / 2.);

        return abs(dx) > abs(dy) ?
                dx >= 0 ? 0 : 180 :
                dy >= 0 ? 90 : 270;
    }
}
