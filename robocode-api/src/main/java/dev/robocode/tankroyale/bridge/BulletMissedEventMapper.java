package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.BulletHitWallEvent;
import robocode.BulletMissedEvent;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

final class BulletMissedEventMapper {
    public static List<BulletMissedEvent> map(List<BulletHitWallEvent> bulletHitWallEvents) {
        if (bulletHitWallEvents == null) return emptyList();

        var events = new ArrayList<BulletMissedEvent>();
        bulletHitWallEvents.forEach(event -> events.add(map(event)));
        return events;
    }

    public static BulletMissedEvent map(BulletHitWallEvent bulletHitWallEvent) {
        if (bulletHitWallEvent == null) return null;

        var bullet = BulletMapper.map(bulletHitWallEvent.getBullet(), null);

        var event = new BulletMissedEvent(bullet);
        event.setTime(bulletHitWallEvent.getTurnNumber());
        return event;
    }
}