package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.events.HitByBulletEvent;

import java.util.ArrayList;
import java.util.List;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRcBearingRad;
import static java.util.Collections.emptyList;

final class HitByBulletEventMapper {

    public static List<robocode.HitByBulletEvent> map(List<HitByBulletEvent> hitByBulletEvents, IBot bot) {
        if (hitByBulletEvents == null) return emptyList();

        var events = new ArrayList<robocode.HitByBulletEvent>();
        hitByBulletEvents.forEach(event -> events.add(map(event, bot)));
        return events;
    }

    public static robocode.HitByBulletEvent map(HitByBulletEvent hitByBulletEvent, IBot bot) {
        if (hitByBulletEvent == null) return null;

        var victimName = String.valueOf(bot.getMyId());
        var bulletState = hitByBulletEvent.getBullet();

        var bearing = toRcBearingRad(bot.bearingTo(bulletState.getX(), bulletState.getY()));
        var bullet = BulletMapper.map(bulletState, victimName);

        var event = new robocode.HitByBulletEvent(bearing, bullet);
        event.setTime(hitByBulletEvent.getTurnNumber());
        return event;
    }
}
