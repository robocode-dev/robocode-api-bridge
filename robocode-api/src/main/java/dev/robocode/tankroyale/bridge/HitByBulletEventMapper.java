package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.BulletState;
import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.events.HitByBulletEvent;
import robocode.Bullet;

import java.util.ArrayList;
import java.util.List;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRcBearingRad;
import static java.util.Collections.emptyList;

final class HitByBulletEventMapper {

    public static List<robocode.HitByBulletEvent> map(List<HitByBulletEvent> hitByBulletEvents, IBot bot) {
        if (hitByBulletEvents == null) return emptyList();

        List<robocode.HitByBulletEvent> events = new ArrayList<>();
        hitByBulletEvents.forEach(event -> events.add(map(event, bot)));
        return events;
    }

    public static robocode.HitByBulletEvent map(HitByBulletEvent hitByBulletEvent, IBot bot) {
        if (hitByBulletEvent == null) return null;

        String victimName = "" + bot.getMyId();
        BulletState bulletState = hitByBulletEvent.getBullet();

        double bearing = toRcBearingRad(bot.bearingTo(bulletState.getX(), bulletState.getY()));
        Bullet bullet = BulletMapper.map(bulletState, victimName);

        return new robocode.HitByBulletEvent(bearing, bullet);
    }
}
