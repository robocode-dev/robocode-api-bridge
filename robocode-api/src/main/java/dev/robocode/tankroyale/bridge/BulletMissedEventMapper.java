package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.BulletHitWallEvent;
import robocode.BulletMissedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

final class BulletMissedEventMapper {
    public static List<BulletMissedEvent> map(List<BulletHitWallEvent> bulletHitWallEvents) {
        if (bulletHitWallEvents == null) return emptyList();

        return bulletHitWallEvents.stream()
                .map(BulletMissedEventMapper::map)
                .collect(Collectors.toList());
    }

    public static BulletMissedEvent map(BulletHitWallEvent bulletHitWallEvent) {
        if (bulletHitWallEvent == null) return null;

        var bullet = BulletMapper.map(bulletHitWallEvent.getBullet(), null);

        var event = new BulletMissedEvent(bullet);
        event.setTime(bulletHitWallEvent.getTurnNumber());
        return event;
    }
}