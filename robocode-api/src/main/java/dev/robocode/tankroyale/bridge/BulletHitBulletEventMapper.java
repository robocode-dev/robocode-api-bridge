package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.BulletHitBulletEvent;

final class BulletHitBulletEventMapper {

    public static robocode.BulletHitBulletEvent map(BulletHitBulletEvent bulletHitBulletEvent) {
        if (bulletHitBulletEvent == null) return null;

        var bullet = BulletMapper.map(bulletHitBulletEvent.getBullet(), null);
        var hitBullet = BulletMapper.map(bulletHitBulletEvent.getHitBullet(), null);

        var event = new robocode.BulletHitBulletEvent(bullet, hitBullet);
        event.setTime(bulletHitBulletEvent.getTurnNumber());

        return event;
    }
}
