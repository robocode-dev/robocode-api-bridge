package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import robocode.Bullet;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRobocodeHeadingRad;

final class BulletPeer extends Bullet {

    public BulletPeer(IBot bot, double power) {
        this.headingRadians = toRobocodeHeadingRad(bot.getGunDirection());
        this.power = power;
        this.x = bot.getX();
        this.y = bot.getY();
        this.ownerName = String.valueOf(bot.getMyId());
        this.isActive = true;
        this.bulletId = -1;
    }

    public void setBulletId(int bulletId) {
        this.bulletId = bulletId;
    }

    public Integer getBulletId() {
        return bulletId;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setVictimName(String victimName) {
        this.victimName = victimName;
    }

    public void setInactive() {
        this.isActive = false;
    }
}
