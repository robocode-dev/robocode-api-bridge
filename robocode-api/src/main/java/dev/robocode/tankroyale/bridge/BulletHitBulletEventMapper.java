package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.BulletHitBulletEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

final class BulletHitBulletEventMapper {

    public static List<robocode.BulletHitBulletEvent> map(List<BulletHitBulletEvent> bulletHitBulletEvents) {
        if (bulletHitBulletEvents == null) return emptyList();

        return bulletHitBulletEvents.stream()
                .map(BulletHitBulletEventMapper::map)
                .collect(Collectors.toList());
    }

    public static robocode.BulletHitBulletEvent map(BulletHitBulletEvent bulletHitBulletEvent) {
        if (bulletHitBulletEvent == null) return null;

        var bullet = BulletMapper.map(bulletHitBulletEvent.getBullet(), null);
        var hitBullet = BulletMapper.map(bulletHitBulletEvent.getHitBullet(), null);

        var event = new robocode.BulletHitBulletEvent(bullet, hitBullet);
        event.setTime(bulletHitBulletEvent.getTurnNumber());

        return event;
    }
}
