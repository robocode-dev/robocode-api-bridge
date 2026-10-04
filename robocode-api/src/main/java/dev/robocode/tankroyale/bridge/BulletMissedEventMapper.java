package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.BulletHitWallEvent;
import robocode.BulletMissedEvent;

final class BulletMissedEventMapper {

    public static BulletMissedEvent map(BulletHitWallEvent bulletHitWallEvent) {
        return map(bulletHitWallEvent, null);
    }

    public static BulletMissedEvent map(BulletHitWallEvent bulletHitWallEvent, Object bot) {
        if (bulletHitWallEvent == null) return null;

        var bullet = BulletMapper.map(bulletHitWallEvent.getBullet(), null, bot);

        var event = new BulletMissedEvent(bullet);
        event.setTime(bulletHitWallEvent.getTurnNumber());

        return event;
    }
}
