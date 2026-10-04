package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.BulletState;
import robocode.Bullet;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRobocodeHeadingRad;

final class BulletMapper {

    interface Resolver {
        Bullet resolveBullet(BulletState state, String victimName);
    }

    public static Bullet map(BulletState bullet, String victimName) {
        return map(bullet, victimName, null);
    }

    public static Bullet map(BulletState bullet, String victimName, Object bot) {
        if (bot instanceof Resolver) {
            var knownBullet = ((Resolver) bot).resolveBullet(bullet, victimName);
            if (knownBullet != null) return knownBullet;
        }
        return new Bullet(
                toRobocodeHeadingRad(bullet.getDirection()),
                bullet.getX(),
                bullet.getY(),
                bullet.getPower(),
                TankRoyaleBotNameResolver.getNameOrId(bot, bullet.getOwnerId()),
                victimName,
                false,
                bullet.getBulletId()
        );
    }
}
