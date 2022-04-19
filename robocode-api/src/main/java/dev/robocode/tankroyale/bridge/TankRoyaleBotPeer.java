package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.Bot;
import dev.robocode.tankroyale.botapi.IBot;
import robocode.Bullet;
import robocode.robotinterfaces.peer.IBasicRobotPeer;

import java.awt.*;

public final class TankRoyaleBotPeer implements IBasicRobotPeer {

    final IBot bot = new Bot() {
    };

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
        return Math.toRadians(bot.normalizeAbsoluteAngle(90.0 - bot.getDirection()));
    }

    @Override
    public double getGunHeading() {
        return Math.toRadians(bot.normalizeAbsoluteAngle(90.0 - bot.getGunDirection()));
    }

    @Override
    public double getRadarHeading() {
        return Math.toRadians(bot.normalizeAbsoluteAngle(90.0 - bot.getRadarDirection()));
    }

    @Override
    public double getGunHeat() { // TODO
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
    public Bullet fire(double power) { // TODO
        return null;
    } // TODO

    @Override
    public Bullet setFire(double power) { // TODO
        return null;
    } // TODO

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
    public Graphics2D getGraphics() { // TODO
        return graphics2D;
    }

    @Override
    public void setDebugProperty(String key, String value) { // ignore for now
    }

    @Override
    public void rescan() {
        bot.scan();
    }
}
