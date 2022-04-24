package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.BulletState;
import robocode.Bullet;

public class BulletMapper {

    public static Bullet map(BulletState bullet, String victimName) {
        return new Bullet(
                Math.toRadians(bullet.getDirection()),
                bullet.getX(),
                bullet.getY(),
                bullet.getPower(),
                "" + bullet.getOwnerId(),
                victimName,
                true,
                bullet.getBulletId()
        );
    }
}
