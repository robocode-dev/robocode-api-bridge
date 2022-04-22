package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.Bot;
import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.events.*;
import robocode.Bullet;
import robocode.robotinterfaces.peer.IBasicRobotPeer;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRcRadians;

public final class BotPeer implements IBasicRobotPeer {

    final Set<BulletPeer> bulletPeers = new HashSet<>();

    final IBot bot = new BotImpl();

    final Graphics2D graphics2D = new Graphics2DImpl();

    @Override
    public String getName() { // TODO
        return null;
    }

    @Override
    public long getTime() {
        return bot.getTurnNumber();
    }

    @Override
    public double getEnergy() {
        return bot.getEnergy();
    }

    @Override
    public double getX() { // TODO
        return bot.getX();
    }

    @Override
    public double getY() { // TODO
        return bot.getY();
    }

    @Override
    public double getVelocity() {
        return bot.getSpeed();
    }

    @Override
    public double getBodyHeading() {
        return toRcRadians(bot.getDirection());
    }

    @Override
    public double getGunHeading() {
        return toRcRadians(bot.getGunDirection());
    }

    @Override
    public double getRadarHeading() {
        return toRcRadians(bot.getRadarDirection());
    }

    @Override
    public double getGunHeat() {
        return bot.getGunHeat();
    }

    @Override
    public double getBattleFieldWidth() {
        return bot.getArenaWidth();
    }

    @Override
    public double getBattleFieldHeight() {
        return bot.getArenaHeight();
    }

    @Override
    public int getOthers() {
        return bot.getEnemyCount();
    }

    @Override
    public int getNumSentries() { // TODO
        return 0;
    }

    @Override
    public int getNumRounds() {
        return bot.getNumberOfRounds();
    }

    @Override
    public int getRoundNum() {
        return bot.getRoundNumber();
    }

    @Override
    public int getSentryBorderSize() { // TODO
        return 0;
    }

    @Override
    public double getGunCoolingRate() {
        return bot.getGunCoolingRate();
    }

    @Override
    public double getDistanceRemaining() {
        return bot.getDistanceRemaining();
    }

    @Override
    public double getBodyTurnRemaining() {
        return Math.toRadians(bot.getTurnRemaining());
    }

    @Override
    public double getGunTurnRemaining() {
        return Math.toRadians(bot.getGunTurnRemaining());
    }

    @Override
    public double getRadarTurnRemaining() {
        return Math.toRadians(bot.getRadarTurnRemaining());
    }

    @Override
    public void execute() {
        bot.go();
    }

    @Override
    public void move(double distance) {
        bot.forward(distance);
    }

    @Override
    public void turnBody(double radians) {
        bot.turnLeft(Math.toDegrees(radians));
    }

    @Override
    public void turnGun(double radians) {
        bot.turnGunLeft(Math.toDegrees(radians));
    }

    @Override
    public Bullet fire(double power) {
        bot.fire(power);
        BulletPeer bullet = new BulletPeer(bot, power);
        bulletPeers.add(bullet);
        return bullet;
    }

    @Override
    public Bullet setFire(double power) {
        if (bot.setFire(power)) {
            BulletPeer bullet = new BulletPeer(bot, power);
            bulletPeers.add(bullet);
            return bullet;
        }
        return null;
    }

    @Override
    public void setBodyColor(Color color) {
        bot.setBodyColor(ColorMapper.map(color));
    }

    @Override
    public void setGunColor(Color color) {
        bot.setTurretColor(ColorMapper.map(color)); // yes, turret!
    }

    @Override
    public void setRadarColor(Color color) {
        bot.setRadarColor(ColorMapper.map(color));
    }

    @Override
    public void setBulletColor(Color color) {
        bot.setBulletColor(ColorMapper.map(color));
    }

    @Override
    public void setScanColor(Color color) {
        bot.setScanColor(ColorMapper.map(color));
    }

    @Override
    public void getCall() { // ignore
    }

    @Override
    public void setCall() { // ignore
    }

    @Override
    public Graphics2D getGraphics() {
        return graphics2D;
    }

    @Override
    public void setDebugProperty(String key, String value) { // ignore for now
    }

    @Override
    public void rescan() {
        bot.scan();
    }

    private class BotImpl extends Bot {

        public void onGameStarted(GameStartedEvent gameStatedEvent) { // TODO
        }

        public void onGameEnded(GameEndedEvent gameEndedEvent) { // TODO
        }

        public void onRoundStarted(RoundStartedEvent roundStartedEvent) { // TODO
        }

        public void onRoundEnded(RoundEndedEvent roundEndedEvent) { // TODO
        }

        public void onTick(TickEvent tickEvent) { // TODO
        }

        public void onBotDeath(DeathEvent botDeathEvent) { // TODO
        }

        public void onDeath(DeathEvent botDeathEvent) { // TODO
        }

        public void onHitBot(HitBotEvent botHitBotEvent) { // TODO
        }

        public void onHitWall(HitWallEvent botHitWallEvent) { // TODO
        }

        public void onBulletFired(BulletFiredEvent bulletFiredEvent) { // TODO
        }

        public void onHitByBullet(BulletHitBotEvent bulletHitBotEvent) { // TODO
        }

        public void onBulletHit(BulletHitBotEvent bulletHitBotEvent) { // TODO
        }

        public void onBulletHitBullet(BulletHitBulletEvent bulletHitBulletEvent) { // TODO
        }

        public void onBulletHitWall(BulletHitWallEvent bulletHitWallEvent) { // TODO
        }

        public void onScannedBot(ScannedBotEvent scannedBotEvent) { // TODO
        }

        public void onSkippedTurn(SkippedTurnEvent skippedTurnEvent) { // TODO
        }

        public void onWonRound(WonRoundEvent wonRoundEvent) { // TODO
        }

        public void onCustomEvent(CustomEvent customEvent) { // TODO
        }
    };
}
