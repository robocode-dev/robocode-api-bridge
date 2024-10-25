package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.events.HitByBulletEvent;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRobocodeBearingRad;

final class HitByBulletEventMapper {

    public static robocode.HitByBulletEvent map(HitByBulletEvent hitByBulletEvent, IBot bot) {
        if (hitByBulletEvent == null) return null;

        var victimName = String.valueOf(bot.getMyId());
        var bulletState = hitByBulletEvent.getBullet();

        var bearing = toRobocodeBearingRad(bot.bearingTo(bulletState.getX(), bulletState.getY()));
        var bullet = BulletMapper.map(bulletState, victimName);

        var event = new robocode.HitByBulletEvent(bearing, bullet);
        event.setTime(hitByBulletEvent.getTurnNumber());

        return event;
    }
}
