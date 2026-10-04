package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.BulletHitBotEvent;
import robocode.BulletHitEvent;

final class BulletHitEventMapper {

    public static BulletHitEvent map(BulletHitBotEvent bulletHitBotEvent) {
        return map(bulletHitBotEvent, null);
    }

    public static BulletHitEvent map(BulletHitBotEvent bulletHitBotEvent, Object bot) {
        if (bulletHitBotEvent == null) return null;

        var victimName = TankRoyaleBotNameResolver.getNameOrId(bot, bulletHitBotEvent.getVictimId());
        var bullet = BulletMapper.map(bulletHitBotEvent.getBullet(), victimName, bot);

        var event = new BulletHitEvent(victimName, bulletHitBotEvent.getEnergy(), bullet);
        event.setTime(bulletHitBotEvent.getTurnNumber());

        return event;
    }
}
