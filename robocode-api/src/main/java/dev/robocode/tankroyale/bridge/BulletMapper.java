package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.BulletState;
import robocode.Bullet;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRobocodeHeadingRad;

final class BulletMapper {

    public static Bullet map(BulletState bullet, String victimName) {
        return new Bullet(
                toRobocodeHeadingRad(bullet.getDirection()),
                bullet.getX(),
                bullet.getY(),
                bullet.getPower(),
                String.valueOf(bullet.getOwnerId()),
                victimName,
                false,
                bullet.getBulletId()
        );
    }
}
