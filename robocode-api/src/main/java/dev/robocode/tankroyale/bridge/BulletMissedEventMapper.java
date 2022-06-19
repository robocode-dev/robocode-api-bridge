package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.BulletHitWallEvent;
import robocode.Bullet;
import robocode.BulletMissedEvent;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

final class BulletMissedEventMapper {
    public static List<BulletMissedEvent> map(List<BulletHitWallEvent> bulletHitWallEvents) {
        if (bulletHitWallEvents == null) return emptyList();

        List<BulletMissedEvent> events = new ArrayList<>();
        bulletHitWallEvents.forEach(event -> events.add(map(event)));
        return events;
    }

    public static BulletMissedEvent map(BulletHitWallEvent bulletHitWallEvent) {
        if (bulletHitWallEvent == null) return null;

        Bullet bullet = BulletMapper.map(bulletHitWallEvent.getBullet(), null);
        return new BulletMissedEvent(bullet);
    }
}