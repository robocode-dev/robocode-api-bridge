package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.Bot;
import dev.robocode.tankroyale.botapi.BulletState;
import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.events.*;
import dev.robocode.tankroyale.botapi.events.BulletHitBulletEvent;
import dev.robocode.tankroyale.botapi.events.Condition;
import dev.robocode.tankroyale.botapi.events.CustomEvent;
import dev.robocode.tankroyale.botapi.events.DeathEvent;
import dev.robocode.tankroyale.botapi.events.HitWallEvent;
import dev.robocode.tankroyale.botapi.events.RoundEndedEvent;
import dev.robocode.tankroyale.botapi.events.SkippedTurnEvent;
import robocode.*;
import robocode.robotinterfaces.IAdvancedEvents;
import robocode.robotinterfaces.IBasicEvents3;
import robocode.robotinterfaces.peer.IAdvancedRobotPeer;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRcRadians;
import static dev.robocode.tankroyale.bridge.ResultsMapper.map;
import static dev.robocode.tankroyale.bridge.BulletMapper.map;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;
import static robocode.util.Utils.normalRelativeAngle;

public final class BotPeer implements IAdvancedRobotPeer {

    private final IBasicEvents3 basicEvents;
    private final IAdvancedEvents advancedEvents;

    private final IBot bot = new BotImpl();
    private final Set<BulletPeer> firedBullets = new HashSet<>();
    private final Graphics2D graphics2D = new Graphics2DImpl();

    private final Map<robocode.Condition, Condition> conditions = new HashMap<>();


    public BotPeer(IBasicEvents3 basicEvents, IAdvancedEvents advancedEvents) {
        this.basicEvents = basicEvents;
        this.advancedEvents = advancedEvents;
    }

    //-------------------------------------------------------------------------
    // IBasicRobotPeer
    //-------------------------------------------------------------------------

    @Override
    public String getName() {
        return "" + bot.getMyId();
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
        return toRadians(bot.getTurnRemaining());
    }

    @Override
    public double getGunTurnRemaining() {
        return toRadians(bot.getGunTurnRemaining());
    }

    @Override
    public double getRadarTurnRemaining() {
        return toRadians(bot.getRadarTurnRemaining());
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
        bot.turnLeft(toDegrees(radians));
    }

    @Override
    public void turnGun(double radians) {
        bot.turnGunLeft(toDegrees(radians));
    }

    @Override
    public Bullet fire(double power) {
        bot.fire(power);
        BulletPeer bullet = new BulletPeer(bot, power);
        firedBullets.add(bullet);
        return bullet;
    }

    @Override
    public Bullet setFire(double power) {
        if (bot.setFire(power)) {
            BulletPeer bullet = new BulletPeer(bot, power);
            firedBullets.add(bullet);
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
        bot.rescan();
    }

    //-------------------------------------------------------------------------
    // IStandardRobotPeer
    //-------------------------------------------------------------------------

    @Override
    public void stop(boolean overwrite) {
        if (overwrite) {
            // flemming-n-larsen: I don't expect any bots to use this functionality, and hence it is not supported (yet)
            // in Robocode Tank Royale.
            throw new UnsupportedOperationException(
                    "stop(overwrite=true) is unsupported. Contact Robocode Tank Royale author for support");
        }
        bot.stop();
    }

    @Override
    public void resume() {
        bot.resume();
    }

    @Override
    public void turnRadar(double radians) {
        bot.turnRadarLeft(toDegrees(radians));
    }

    @Override
    public void setAdjustGunForBodyTurn(boolean adjust) {
        bot.setAdjustGunForBodyTurn(adjust);
    }

    @Override
    public void setAdjustRadarForGunTurn(boolean adjust) {
        bot.setAdjustRadarForGunTurn(adjust);
    }

    @Override
    public void setAdjustRadarForBodyTurn(boolean adjust) {
        bot.setAdjustRadarForBodyTurn(adjust);
    }

    //-------------------------------------------------------------------------
    // IAdvancedRobotPeer
    //-------------------------------------------------------------------------

    @Override
    public boolean isAdjustGunForBodyTurn() {
        return bot.isAdjustGunForBodyTurn();
    }

    @Override
    public boolean isAdjustRadarForGunTurn() {
        return bot.isAdjustRadarForGunTurn();
    }

    @Override
    public boolean isAdjustRadarForBodyTurn() {
        return bot.isAdjustRadarForBodyTurn();
    }

    @Override
    public void setStop(boolean overwrite) {
        if (overwrite) {
            // flemming-n-larsen: I don't expect any bots to use this functionality, and hence it is not supported (yet)
            // in Robocode Tank Royale.
            throw new UnsupportedOperationException(
                    "setStop(overwrite=true) is unsupported. Contact Robocode Tank Royale author for support");
        }
        bot.setStop();
    }

    @Override
    public void setResume() {
        bot.setResume();
    }

    @Override
    public void setMove(double distance) {
        bot.setForward(distance);
    }

    @Override
    public void setTurnBody(double radians) {
        bot.setTurnLeft(toDegrees(radians));
    }

    @Override
    public void setTurnGun(double radians) {
        bot.setTurnGunLeft(toDegrees(radians));
    }

    @Override
    public void setTurnRadar(double radians) {
        bot.setTurnRadarLeft(toDegrees(radians));
    }

    @Override
    public void setMaxTurnRate(double newMaxTurnRate) {
        bot.setMaxTurnRate(newMaxTurnRate);
    }

    @Override
    public void setMaxVelocity(double newMaxVelocity) {
        bot.setMaxSpeed(newMaxVelocity);
    }

    @Override
    public void waitFor(robocode.Condition condition) {
        bot.waitFor(new Condition(condition.getName()) {
            @Override
            public boolean test() {
                return condition.test();
            }
        });
    }

    @Override
    public void setInterruptible(boolean interruptible) {
        bot.setInterruptible(interruptible);
    }

    @Override
    public void setEventPriority(String eventClass, int priority) {
        // TODO
    }

    @Override
    public int getEventPriority(String eventClass) {
        return 0; // TODO
    }

    @Override
    public void addCustomEvent(robocode.Condition condition) {
        Condition trCondition = new Condition() {
            @Override
            public boolean test() {
                return condition.test();
            }
        };
        conditions.put(condition, trCondition);

        bot.addCustomEvent(trCondition);
    }

    @Override
    public void removeCustomEvent(robocode.Condition condition) {
        Condition trCondition = conditions.get(condition);
        if (trCondition != null) {
            bot.removeCustomEvent(trCondition);
        }
    }

    @Override
    public void clearAllEvents() {
        // TODO
    }

    @Override
    public List<robocode.Event> getAllEvents() {
        return null; // TODO
    }

    @Override
    public List<StatusEvent> getStatusEvents() {
        return null; // TODO
    }

    @Override
    public List<BulletMissedEvent> getBulletMissedEvents() {
        return null; // TODO
    }

    @Override
    public List<robocode.BulletHitBulletEvent> getBulletHitBulletEvents() {
        return null; // TODO
    }

    @Override
    public List<BulletHitEvent> getBulletHitEvents() {
        return null; // TODO
    }

    @Override
    public List<HitByBulletEvent> getHitByBulletEvents() {
        return null; // TODO
    }

    @Override
    public List<HitRobotEvent> getHitRobotEvents() {
        return null; // TODO
    }

    @Override
    public List<robocode.HitWallEvent> getHitWallEvents() {
        return null; // TODO
    }

    @Override
    public List<RobotDeathEvent> getRobotDeathEvents() {
        return null; // TODO
    }

    @Override
    public List<ScannedRobotEvent> getScannedRobotEvents() {
        return null; // TODO
    }

    @Override
    public File getDataDirectory() {
        return null; // TODO
    }

    @Override
    public File getDataFile(String filename) {
        return null; // TODO
    }

    @Override
    public long getDataQuotaAvailable() {
        return 0; // TODO
    }


    //-------------------------------------------------------------------------
    // IBasicEvents3 and IAdvancedEvents event triggers
    //-------------------------------------------------------------------------

    private class BotImpl extends Bot {

        int totalTurns;

        public void onGameStarted(GameStartedEvent gameStatedEvent) { // TODO
            totalTurns = 0;
        }

        public void onGameEnded(GameEndedEvent gameEndedEvent) {
            basicEvents.onBattleEnded(new robocode.BattleEndedEvent(
                    false, map(gameEndedEvent.getResults(), "" + this.getMyId()))
            );
        }

        public void onRoundStarted(RoundStartedEvent roundStartedEvent) { // TODO
        }

        public void onRoundEnded(RoundEndedEvent roundEndedEvent) {
            int turnNumber = roundEndedEvent.getTurnNumber();
            totalTurns += turnNumber;

            basicEvents.onRoundEnded(
                    new robocode.RoundEndedEvent(roundEndedEvent.getRoundNumber(), turnNumber, totalTurns));
        }

        public void onTick(TickEvent tickEvent) { // TODO

            // Update fired bullets
            firedBullets.forEach(bulletPeer -> {
                Optional<BulletState> bulletStateOpt = tickEvent.getBulletStates().stream().filter(
                        bulletState -> bulletPeer.getBulletId() == bulletState.getBulletId()).findFirst();

                if (bulletStateOpt.isPresent()) {
                    BulletState bulletState = bulletStateOpt.get();
                    bulletPeer.setPosition(bulletState.getX(), bulletState.getY());
                }
            });
        }

        public void onBotDeath(DeathEvent botDeathEvent) {
            basicEvents.onRobotDeath(
                    new robocode.RobotDeathEvent("" + botDeathEvent.getVictimId()));
        }

        public void onDeath(DeathEvent botDeathEvent) {
            basicEvents.onDeath(new robocode.DeathEvent());
        }

        public void onHitBot(HitBotEvent botHitBotEvent) {
            double bearing = toRcBearingToRadians(botHitBotEvent.getX(), botHitBotEvent.getY());
            basicEvents.onHitRobot(new robocode.HitRobotEvent(
                    "" + botHitBotEvent.getVictimId(), bearing, botHitBotEvent.getEnergy(), botHitBotEvent.isRammed()
            ));
        }

        public void onHitWall(HitWallEvent botHitWallEvent) {
            basicEvents.onHitWall(new robocode.HitWallEvent(calcBearingToWallRadians(getDirection())));
        }

        public void onBulletFired(BulletFiredEvent bulletFiredEvent) {
            BulletState bulletState = bulletFiredEvent.getBullet();
            BulletPeer bullet = findBulletByXAndY(bulletState);
            if (bullet == null) {
                throw new IllegalStateException("onBulletFired: Could not find bullet");
            }
            bullet.setBulletId(bulletState.getBulletId());
        }

        public void onHitByBullet(BulletHitBotEvent bulletHitBotEvent) {
            BulletState bullet = bulletHitBotEvent.getBullet();
            double bearing = toRcBearingToRadians(bullet.getX(), bullet.getY());
            basicEvents.onHitByBullet(new robocode.HitByBulletEvent(
                    bearing, map(bullet, "" + bulletHitBotEvent.getVictimId())));
        }

        public void onBulletHit(BulletHitBotEvent bulletHitBotEvent) {
            BulletPeer bullet = findBulletById(bulletHitBotEvent.getBullet());
            if (bullet == null) {
                throw new IllegalStateException("onBulletHit: Could not find bullet");
            }

            String victimName = "" + bulletHitBotEvent.getVictimId();
            bullet.setVictimName(victimName);
            bullet.setInactive();

            firedBullets.remove(bullet);

            basicEvents.onBulletHit(new robocode.BulletHitEvent(victimName, bulletHitBotEvent.getEnergy(), bullet));
        }

        public void onBulletHitBullet(BulletHitBulletEvent bulletHitBulletEvent) {
            BulletPeer bullet = findBulletById(bulletHitBulletEvent.getBullet());
            if (bullet == null) {
                throw new IllegalStateException("onBulletHitBullet: Could not find bullet");
            }
            Bullet hitBullet = BulletMapper.map(bulletHitBulletEvent.getHitBullet(), null);
            bullet.setInactive();

            firedBullets.remove(bullet);

            basicEvents.onBulletHitBullet(new robocode.BulletHitBulletEvent(bullet, hitBullet));
        }

        public void onBulletHitWall(BulletHitWallEvent bulletHitWallEvent) {
            double bulletDirection = bulletHitWallEvent.getBullet().getDirection();
            basicEvents.onHitWall(new robocode.HitWallEvent(calcBearingToWallRadians(bulletDirection)));
        }

        public void onScannedBot(ScannedBotEvent scannedBotEvent) {
            double bearing = toRcBearingToRadians(scannedBotEvent.getX(), scannedBotEvent.getY());
            double distanceTo = distanceTo(scannedBotEvent.getX(), scannedBotEvent.getY());

            basicEvents.onScannedRobot(new robocode.ScannedRobotEvent(
                    "" + scannedBotEvent.getScannedBotId(),
                    scannedBotEvent.getEnergy(),
                    bearing,
                    distanceTo,
                    toRcRadians(scannedBotEvent.getDirection()),
                    scannedBotEvent.getSpeed(),
                    false
            ));
        }

        public void onSkippedTurn(SkippedTurnEvent skippedTurnEvent) {
            advancedEvents.onSkippedTurn(new robocode.SkippedTurnEvent(skippedTurnEvent.getTurnNumber()));
        }

        public void onWonRound(WonRoundEvent wonRoundEvent) {
            basicEvents.onWin(new robocode.WinEvent());
        }

        public void onCustomEvent(CustomEvent customEvent) { // TODO
        }

        BulletPeer findBulletByXAndY(BulletState bulletState) {
            return firedBullets.stream().filter(
                            bullet -> bulletState.getX() == bullet.getX() && bullet.getY() == bulletState.getY())
                    .findFirst()
                    .orElse(null);
        }

        BulletPeer findBulletById(BulletState bulletState) {
            return firedBullets.stream().filter(
                            bullet -> bulletState.getBulletId() == bullet.getBulletId())
                    .findFirst()
                    .orElse(null);
        }

        double calcBearingToWallRadians(double directionDeg) {
            int minX = 18; // half bot size (36x36)
            int minY = 18;
            int maxX = getArenaWidth() - 18;
            int maxY = getArenaHeight() - 18;

            double angle = 0;
            if (getX() < minX) {
                angle = normalizeRelativeAngle(180 - directionDeg);
            } else if (getX() > maxX) {
                angle = normalizeRelativeAngle(360 - directionDeg);
            }
            if (getY() < minY) {
                angle = normalRelativeAngle(90 - directionDeg);
            } else if (getY() > maxY) {
                angle = normalRelativeAngle(270 - directionDeg);
            }
            return toRcRadians(angle);
        }

        double toRcBearingToRadians(double x, double y) {
            return toRcRadians(bearingTo(x, y));
        }
    }
}
