package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.Bot;
import dev.robocode.tankroyale.botapi.BulletState;
import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.events.*;
import dev.robocode.tankroyale.botapi.events.BulletHitBulletEvent;
import dev.robocode.tankroyale.botapi.events.Condition;
import dev.robocode.tankroyale.botapi.events.CustomEvent;
import dev.robocode.tankroyale.botapi.events.DeathEvent;
import dev.robocode.tankroyale.botapi.events.HitByBulletEvent;
import dev.robocode.tankroyale.botapi.events.HitWallEvent;
import dev.robocode.tankroyale.botapi.events.RoundEndedEvent;
import dev.robocode.tankroyale.botapi.events.SkippedTurnEvent;
import robocode.*;
import robocode.Robot;
import robocode.robotinterfaces.IAdvancedEvents;
import robocode.robotinterfaces.IBasicEvents3;
import robocode.robotinterfaces.peer.IAdvancedRobotPeer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRcRadians;
import static dev.robocode.tankroyale.bridge.ResultsMapper.map;
import static dev.robocode.tankroyale.bridge.BulletMapper.map;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;
import static robocode.util.Utils.normalRelativeAngle;

public final class BotPeer implements IAdvancedRobotPeer {

    private final IBasicEvents3 basicEvents;
    private final IAdvancedEvents advancedEvents;
    private final Robot robot;

    private final IBot bot = new BotImpl();
    private final Set<BulletPeer> firedBullets = new HashSet<>();
    private final Graphics2D graphics2D = new Graphics2DImpl();

    private final Map<robocode.Condition, Condition> conditions = new HashMap<>();
    private final Map<Integer, RobotStatus> robotStatuses = new HashMap<>();


    public BotPeer(IBasicEvents3 basicEvents, IAdvancedEvents advancedEvents, Robot robot) {
        if (advancedEvents == null) {
            advancedEvents = new AdvancedEventAdaptor();
        }

        this.basicEvents = basicEvents;
        this.advancedEvents = advancedEvents;
        this.robot = robot;

        init();
    }

    public void start() {
        bot.start();
    }

    private void init() {
        setupEventPriorities();
    }

    private void setupEventPriorities() {
        setEventPriority("WinEvent", 100);
        setEventPriority("SkippedTurnEvent", 100);
        setEventPriority("StatusEvent", 99);
        setEventPriority("CustomEvent", 80);
        setEventPriority("MessageEvent", 75);
        setEventPriority("BulletMissedEvent", 60);
        setEventPriority("BulletHitBulletEvent", 55);
        setEventPriority("BulletHitEvent", 50);
        setEventPriority("HitByBulletEvent", 40);
        setEventPriority("HitWallEvent", 30);
        setEventPriority("HitRobotEvent", 20);
        setEventPriority("ScannedRobotEvent", 10);
        setEventPriority("DeathEvent", -1);
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
    public double getX() {
        return bot.getX();
    }

    @Override
    public double getY() {
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
    public int getNumSentries() { // Not supported
        return 0;
    }

    @Override
    public int getSentryBorderSize() { // Not supported
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
        return createAndAddBullet(power);
    }

    @Override
    public Bullet setFire(double power) {
        if (bot.setFire(power)) {
            return createAndAddBullet(power);
        }
        return null;
    }

    private BulletPeer createAndAddBullet(double power) {
        BulletPeer bullet = new BulletPeer(bot, power);
        firedBullets.add(bullet);
        return bullet;
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
    @SuppressWarnings("unchecked")
    public void setEventPriority(String eventClass, int priority) {
        // not supported (yet) -> just ignore
        if ("MessageEvent".equals(eventClass) || "PaintEvent".equals(eventClass)) return;

        Class<? extends BotEvent> botEvent = EventClassMapper.toBotEvent(eventClass);
        bot.setEventPriority((Class<BotEvent>) botEvent, priority);
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getEventPriority(String eventClass) {
        Class<? extends BotEvent> botEvent = EventClassMapper.toBotEvent(eventClass);
        return bot.getEventPriority((Class<BotEvent>) botEvent);
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
        bot.clearEvents();
    }

    @Override
    public List<robocode.Event> getAllEvents() {
        return AllEventsMapper.map(bot.getEvents(), bot, robotStatuses);
    }

    @Override
    public List<robocode.StatusEvent> getStatusEvents() {
        List<TickEvent> tickEvents = bot.getEvents().stream()
                .filter(event -> event instanceof TickEvent)
                .map(TickEvent.class::cast).collect(Collectors.toList());
        return StatusEventMapper.map(tickEvents, robotStatuses);
    }

    @Override
    public List<robocode.BulletMissedEvent> getBulletMissedEvents() {
        List<BulletHitWallEvent> bulletHitWallEvents = bot.getEvents().stream()
                .filter(event -> event instanceof BulletHitWallEvent)
                .map(BulletHitWallEvent.class::cast).collect(Collectors.toList());
        return BulletMissedEventMapper.map(bulletHitWallEvents);
    }

    @Override
    public List<robocode.BulletHitBulletEvent> getBulletHitBulletEvents() {
        List<BulletHitBulletEvent> bulletHitBulletEvents = bot.getEvents().stream()
                .filter(event -> event instanceof BulletHitBulletEvent)
                .map(BulletHitBulletEvent.class::cast).collect(Collectors.toList());
        return BulletHitBulletEventMapper.map(bulletHitBulletEvents);
    }

    @Override
    public List<robocode.BulletHitEvent> getBulletHitEvents() {
        List<BulletHitBotEvent> bulletHitBotEvents = bot.getEvents().stream()
                .filter(event -> event instanceof BulletHitBotEvent)
                .map(BulletHitBotEvent.class::cast).collect(Collectors.toList());
        return BulletHitEventMapper.map(bulletHitBotEvents);
    }

    @Override
    public List<robocode.HitByBulletEvent> getHitByBulletEvents() {
        List<HitByBulletEvent> hitByBulletEvents = bot.getEvents().stream()
                .filter(event -> event instanceof HitByBulletEvent)
                .map(HitByBulletEvent.class::cast).collect(Collectors.toList());
        return HitByBulletEventMapper.map(hitByBulletEvents, bot);
    }

    @Override
    public List<robocode.HitRobotEvent> getHitRobotEvents() {
        List<HitBotEvent> hitBotEvents = bot.getEvents().stream()
                .filter(event -> event instanceof HitBotEvent)
                .map(HitBotEvent.class::cast).collect(Collectors.toList());
        return HitRobotEventMapper.map(hitBotEvents, bot);
    }

    @Override
    public List<robocode.HitWallEvent> getHitWallEvents() {
        List<HitWallEvent> hitWallEvents = bot.getEvents().stream()
                .filter(event -> event instanceof HitWallEvent)
                .map(HitWallEvent.class::cast).collect(Collectors.toList());
        return HitWallEventMapper.map(hitWallEvents, bot);
    }

    @Override
    public List<robocode.RobotDeathEvent> getRobotDeathEvents() {
        List<BotDeathEvent> botDeathEvents = bot.getEvents().stream()
                .filter(event -> event instanceof BotDeathEvent)
                .map(BotDeathEvent.class::cast).collect(Collectors.toList());
        return RobotDeathEventMapper.map(botDeathEvents);
    }

    @Override
    public List<robocode.ScannedRobotEvent> getScannedRobotEvents() {
        List<ScannedBotEvent> scannedBotEvents = bot.getEvents().stream()
                .filter(event -> event instanceof ScannedBotEvent)
                .map(ScannedBotEvent.class::cast).collect(Collectors.toList());
        return ScannedRobotEventMapper.map(scannedBotEvents, bot);
    }

    @Override
    public File getDataDirectory() {
        return Paths.get("").toFile(); // use current path (where the application was started from)
    }

    @Override
    public File getDataFile(String filename) {
        Path dirPath = Paths.get("").resolve(getName());
        try {
            Files.createDirectories(dirPath);
        } catch (IOException e) {
            System.err.println("Could not create directories: " + dirPath);
        }
        return dirPath.resolve(filename).toFile();
    }

    @Override
    public long getDataQuotaAvailable() {
        return 300_000;
    }


    //-------------------------------------------------------------------------
    // IBasicEvents3 and IAdvancedEvents event triggers
    //-------------------------------------------------------------------------

    private class BotImpl extends Bot {

        int totalTurns;

        @Override
        public void run() {
            robot.run();
        }

        @Override
        public void onGameStarted(GameStartedEvent gameStatedEvent) { // TODO
            totalTurns = 0;
        }

        @Override
        public void onGameEnded(GameEndedEvent gameEndedEvent) {
            basicEvents.onBattleEnded(new robocode.BattleEndedEvent(
                    false, map(gameEndedEvent.getResults(), "" + this.getMyId()))
            );
        }

        @Override
        public void onRoundStarted(RoundStartedEvent roundStartedEvent) {
            // no event handler for `round started` in orig. Robocode

            firedBullets.clear();
            robotStatuses.clear();
        }

        @Override
        public void onRoundEnded(RoundEndedEvent roundEndedEvent) {
            int turnNumber = roundEndedEvent.getTurnNumber();
            totalTurns += turnNumber;

            basicEvents.onRoundEnded(
                    new robocode.RoundEndedEvent(roundEndedEvent.getRoundNumber(), turnNumber, totalTurns));
        }

        @Override
        public void onTick(TickEvent tickEvent) {
            // Save robot status snapshot for event handlers needing robot status
            RobotStatus robotStatus = IBotToRobotStatusMapper.map(bot);
            robotStatuses.put(tickEvent.getTurnNumber(), robotStatus);

            // Update fired bullets
            firedBullets.forEach(bulletPeer -> {
                Optional<BulletState> bulletStateOpt = tickEvent.getBulletStates().stream().filter(
                        bulletState -> bulletPeer.getBulletId() == bulletState.getBulletId()).findFirst();

                if (bulletStateOpt.isPresent()) {
                    BulletState bulletState = bulletStateOpt.get();
                    bulletPeer.setPosition(bulletState.getX(), bulletState.getY());
                }
            });

            // Fire event
            basicEvents.onStatus(new robocode.StatusEvent(robotStatus));
        }

        @Override
        public void onBotDeath(BotDeathEvent botDeathEvent) {
            basicEvents.onRobotDeath(new robocode.RobotDeathEvent("" + botDeathEvent.getVictimId()));
        }

        @Override
        public void onDeath(DeathEvent botDeathEvent) {
            basicEvents.onDeath(new robocode.DeathEvent());
        }

        @Override
        public void onHitBot(HitBotEvent botHitBotEvent) {
            double bearing = toRcBearingToRadians(botHitBotEvent.getX(), botHitBotEvent.getY());
            basicEvents.onHitRobot(new robocode.HitRobotEvent(
                    "" + botHitBotEvent.getVictimId(), bearing, botHitBotEvent.getEnergy(), botHitBotEvent.isRammed()
            ));
        }

        @Override
        public void onHitWall(HitWallEvent botHitWallEvent) {
            basicEvents.onHitWall(new robocode.HitWallEvent(calcBearingToWallRadians(getDirection())));
        }

        @Override
        public void onBulletFired(BulletFiredEvent bulletFiredEvent) {
            BulletState bulletState = bulletFiredEvent.getBullet();
            BulletPeer bullet = findBulletByXAndY(bulletState);
            if (bullet == null) {
                throw new IllegalStateException("onBulletFired: Could not find bullet: " + bulletState.getX() + "," + bulletState.getY());
            }
            bullet.setBulletId(bulletState.getBulletId());
        }

        @Override
        public void onHitByBullet(HitByBulletEvent hitByBulletEvent) {
            BulletState bullet = hitByBulletEvent.getBullet();
            double bearing = toRcBearingToRadians(bullet.getX(), bullet.getY());
            basicEvents.onHitByBullet(new robocode.HitByBulletEvent(
                    bearing, map(bullet, "" + this.getMyId())));
        }

        @Override
        public void onBulletHit(BulletHitBotEvent bulletHitBotEvent) {
            BulletPeer bullet = findBulletById(bulletHitBotEvent.getBullet());
            String victimName = "" + bulletHitBotEvent.getVictimId();
            bullet.setVictimName(victimName);
            bullet.setInactive();

            firedBullets.remove(bullet);

            basicEvents.onBulletHit(new robocode.BulletHitEvent(victimName, bulletHitBotEvent.getEnergy(), bullet));
        }

        @Override
        public void onBulletHitBullet(BulletHitBulletEvent bulletHitBulletEvent) {
            BulletPeer bullet = findBulletById(bulletHitBulletEvent.getBullet());
            Bullet hitBullet = BulletMapper.map(bulletHitBulletEvent.getHitBullet(), null);
            bullet.setInactive();

            firedBullets.remove(bullet);

            basicEvents.onBulletHitBullet(new robocode.BulletHitBulletEvent(bullet, hitBullet));
        }

        @Override
        public void onBulletHitWall(BulletHitWallEvent bulletHitWallEvent) {
            double bulletDirection = bulletHitWallEvent.getBullet().getDirection();
            basicEvents.onHitWall(new robocode.HitWallEvent(calcBearingToWallRadians(bulletDirection)));
        }

        @Override
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

        @Override
        public void onSkippedTurn(SkippedTurnEvent skippedTurnEvent) {
            advancedEvents.onSkippedTurn(new robocode.SkippedTurnEvent(skippedTurnEvent.getTurnNumber()));
        }

        @Override
        public void onWonRound(WonRoundEvent wonRoundEvent) {
            basicEvents.onWin(new robocode.WinEvent());
        }

        @Override
        public void onCustomEvent(CustomEvent customEvent) {
            Condition trCondition = customEvent.getCondition();
            if (trCondition == null) return;

            Optional<Map.Entry<robocode.Condition, Condition>> optCondition = conditions.entrySet().stream()
                    .filter(entry -> trCondition.equals(entry.getValue())).findFirst();
            if (optCondition.isPresent()) {
                robocode.Condition condition = optCondition.get().getKey();
                advancedEvents.onCustomEvent(new robocode.CustomEvent(condition));
            }
        }

        BulletPeer findBulletByXAndY(BulletState bulletState) {
            return firedBullets.stream().filter(
                            bullet -> bulletState.getX() == bullet.getX() && bullet.getY() == bulletState.getY())
                    .findFirst()
                    .orElse(null);
        }

        BulletPeer findBulletById(BulletState bulletState) {
            BulletPeer bulletPeer = firedBullets.stream().filter(
                            bullet -> bulletState.getBulletId() == bullet.getBulletId())
                    .findFirst()
                    .orElse(null);

            if (bulletPeer == null) {
                throw new IllegalStateException("findBulletById: Could not find bullet: " + bulletState.getBulletId());
            }
            return bulletPeer;
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
