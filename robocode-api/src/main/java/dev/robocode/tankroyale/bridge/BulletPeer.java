package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.BulletState;
import robocode.Bullet;
import java.util.concurrent.atomic.AtomicInteger;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRobocodeHeadingRad;

final class BulletPeer extends Bullet {

    private static final AtomicInteger NEXT_LEGACY_ID = new AtomicInteger();
    private int transportBulletId = -1;

    public BulletPeer(IBot bot, double power) {
        this.headingRadians = toRobocodeHeadingRad(bot.getGunDirection());
        this.power = power;
        this.x = bot.getX();
        this.y = bot.getY();
        this.ownerName = TankRoyaleBotNameResolver.getNameOrId(bot, bot.getMyId());
        this.isActive = true;
        this.bulletId = NEXT_LEGACY_ID.incrementAndGet();
    }

    public void setBulletId(int bulletId) {
        this.transportBulletId = bulletId;
    }

    public Integer getBulletId() {
        return transportBulletId;
    }

    public void updateState(BulletState state, String victimName, boolean active) {
        headingRadians = toRobocodeHeadingRad(state.getDirection());
        power = state.getPower();
        x = state.getX();
        y = state.getY();
        this.victimName = victimName;
        isActive = active;
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
