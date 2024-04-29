package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.BulletHitBotEvent;
import robocode.BulletHitEvent;

final class BulletHitEventMapper {

    public static BulletHitEvent map(BulletHitBotEvent bulletHitBotEvent) {
        if (bulletHitBotEvent == null) return null;

        var victimName = String.valueOf(bulletHitBotEvent.getVictimId());
        var bullet = BulletMapper.map(bulletHitBotEvent.getBullet(), victimName);

        var event = new BulletHitEvent(victimName, bulletHitBotEvent.getEnergy(), bullet);
        event.setTime(bulletHitBotEvent.getTurnNumber());

        return event;
    }
}
