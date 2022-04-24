package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import robocode.Bullet;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRcRadians;

public final class BulletPeer extends Bullet {

    public BulletPeer(IBot bot, double power) {
        this.headingRadians = toRcRadians(bot.getGunDirection());
        this.power = power;
        this.x = bot.getX();
        this.y = bot.getY();
        this.ownerName = "" + bot.getMyId();
        this.isActive = true;
        this.bulletId = -1;
    }

    public void setBulletId(int bulletId) {
        this.bulletId = bulletId;
    }

    public Integer getBulletId() {
        return bulletId;
    }
}
