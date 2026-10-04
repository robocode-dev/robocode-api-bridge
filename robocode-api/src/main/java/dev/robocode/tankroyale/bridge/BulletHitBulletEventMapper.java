package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.BulletHitBulletEvent;

final class BulletHitBulletEventMapper {

    public static robocode.BulletHitBulletEvent map(BulletHitBulletEvent bulletHitBulletEvent) {
        return map(bulletHitBulletEvent, null);
    }

    public static robocode.BulletHitBulletEvent map(BulletHitBulletEvent bulletHitBulletEvent, Object bot) {
        if (bulletHitBulletEvent == null) return null;

        var bullet = BulletMapper.map(bulletHitBulletEvent.getBullet(), null, bot);
        var hitBullet = BulletMapper.map(bulletHitBulletEvent.getHitBullet(), null, bot);

        var event = new robocode.BulletHitBulletEvent(bullet, hitBullet);
        event.setTime(bulletHitBulletEvent.getTurnNumber());

        return event;
    }
}
