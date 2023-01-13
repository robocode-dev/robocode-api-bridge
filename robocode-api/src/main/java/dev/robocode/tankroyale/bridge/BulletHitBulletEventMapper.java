package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.BulletHitBulletEvent;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

final class BulletHitBulletEventMapper {

    public static List<robocode.BulletHitBulletEvent> map(List<BulletHitBulletEvent> bulletHitBulletEvents) {
        if (bulletHitBulletEvents == null) return emptyList();

        var events = new ArrayList<robocode.BulletHitBulletEvent>();
        bulletHitBulletEvents.forEach(event -> events.add(map(event)));
        return events;
    }

    public static robocode.BulletHitBulletEvent map(BulletHitBulletEvent bulletHitBulletEvent) {
        if (bulletHitBulletEvent == null) return null;

        var bullet = BulletMapper.map(bulletHitBulletEvent.getBullet(), null);
        var hitBullet = BulletMapper.map(bulletHitBulletEvent.getHitBullet(), null);
        return new robocode.BulletHitBulletEvent(bullet, hitBullet);
    }
}
