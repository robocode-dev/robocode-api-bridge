package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;
import dev.robocode.tankroyale.botapi.events.BulletHitBulletEvent;
import dev.robocode.tankroyale.botapi.events.Condition;
import dev.robocode.tankroyale.botapi.events.CustomEvent;
import dev.robocode.tankroyale.botapi.events.HitByBulletEvent;
import dev.robocode.tankroyale.botapi.events.RoundEndedEvent;
import dev.robocode.tankroyale.botapi.events.SkippedTurnEvent;
import robocode.*;
import robocode.robotinterfaces.*;
import robocode.robotinterfaces.peer.IJuniorRobotPeer;
import robocode.robotinterfaces.peer.ITeamRobotPeer;

import java.awt.*;
import java.awt.Color;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRobocodeBearingRad;
import static dev.robocode.tankroyale.bridge.AngleConverter.toRobocodeHeadingRad;
import static dev.robocode.tankroyale.bridge.ResultsMapper.map;
import static dev.robocode.tankroyale.bridge.BulletMapper.map;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;
import static robocode.util.Utils.normalRelativeAngle;

public final class BotPeer implements ITeamRobotPeer, IJuniorRobotPeer {

    private final IBasicRobot robot;
    private final IBasicEvents basicEvents;
    private final IAdvancedEvents advancedEvents;

    private final IBot bot;
    private final Set<BulletPeer> firedBullets = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private final Graphics2D graphics2D = new Graphics2DImpl();

    private final Map<robocode.Condition, Condition> conditions = new ConcurrentHashMap<>();
    private final AtomicReference<RobotStatus> currentRobotStatus = new AtomicReference<>();

    private boolean stopThread;

    @SuppressWarnings("unused")
    public BotPeer(IBasicRobot robot, BotInfo botInfo) {
        log("BotPeer");

        this.robot = robot;
        bot = new BotImpl(botInfo);

        robot.setOut(System.out); // Redirect output to "our" System.out, which Tank Royale is overriding

        basicEvents = robot.getBasicEventListener();

        if (robot instanceof IAdvancedRobot) {
            advancedEvents = ((IAdvancedRobot) robot).getAdvancedEventListener();
        } else {
            advancedEvents = new AdvancedEventAdaptor();
        }

        init();
    }

    @SuppressWarnings("unused")
    public void start() {
        log("start()");
        bot.start();
    }

    private void init() {
        RobotName.setName(robot.getClass().getSimpleName());
        setupEventPriorities();
    }

    private void setupEventPriorities() {
        setEventPriority(robocode.WinEvent.class.getSimpleName(), 100);
        setEventPriority(robocode.SkippedTurnEvent.class.getSimpleName(), 100);
        setEventPriority(robocode.StatusEvent.class.getSimpleName(), 99);
        setEventPriority(robocode.CustomEvent.class.getSimpleName(), 80);
        setEventPriority(robocode.MessageEvent.class.getSimpleName(), 75);
        setEventPriority(robocode.RobotDeathEvent.class.getSimpleName(), 70);
        setEventPriority(robocode.BulletMissedEvent.class.getSimpleName(), 60);
        setEventPriority(robocode.BulletHitBulletEvent.class.getSimpleName(), 55);
        setEventPriority(robocode.BulletHitEvent.class.getSimpleName(), 50);
        setEventPriority(robocode.HitByBulletEvent.class.getSimpleName(), 40);
        setEventPriority(robocode.HitWallEvent.class.getSimpleName(), 30);
        setEventPriority(robocode.HitRobotEvent.class.getSimpleName(), 20);
        setEventPriority(robocode.ScannedRobotEvent.class.getSimpleName(), 10);
        setEventPriority(robocode.DeathEvent.class.getSimpleName(), -1);
    }

    //-------------------------------------------------------------------------
    // IBasicRobotPeer
    //-------------------------------------------------------------------------

    @Override
    public String getName() {
        log("getName()");
        return RobotName.getName();
    }

    @Override
    public long getTime() {
        log("getTime()");
        return bot.getTurnNumber();
    }

    @Override
    public double getEnergy() {
        log("getEnergy()");

        if (stopThread) {
            // Sets the energy to a negative value to break `while(getEnergy() >= 0)` in the run() method which
            // that is substituting `while(true)` by modifying the bytecode of the run() method.
            return -1;
        }
        return bot.getEnergy();
    }

    @Override
    public double getX() {
        log("getX()");
        return bot.getX();
    }

    @Override
    public double getY() {
        log("getY()");
        return bot.getY();
    }

    @Override
    public double getVelocity() {
        log("getVelocity()");
        return bot.getSpeed();
    }

    @Override
    public double getBodyHeading() {
        log("getBodyHeading()");
        return toRobocodeHeadingRad(bot.getDirection());
    }

    @Override
    public double getGunHeading() {
        log("getGunHeading()");
        return toRobocodeHeadingRad(bot.getGunDirection());
    }

    @Override
    public double getRadarHeading() {
        log("getRadarHeading()");
        return toRobocodeHeadingRad(bot.getRadarDirection());
    }

    @Override
    public double getGunHeat() {
        log("getGunHeat()");
        return bot.getGunHeat();
    }

    @Override
    public double getBattleFieldWidth() {
        log("getBattleFieldWidth()");
        return bot.getArenaWidth();
    }

    @Override
    public double getBattleFieldHeight() {
        log("getBattleFieldHeight()");
        return bot.getArenaHeight();
    }

    @Override
    public int getOthers() {
        log("getOthers()");
        return bot.getEnemyCount();
    }

    @Override
    public int getNumSentries() { // Not supported
        log("getNumSentries()");
        return 0;
    }

    @Override
    public int getSentryBorderSize() { // Not supported
        log("getSentryBorderSize()");
        return 0;
    }

    @Override
    public int getNumRounds() {
        log("getNumRounds()");
        return bot.getNumberOfRounds();
    }

    @Override
    public int getRoundNum() {
        log("getRoundNum()");
        return bot.getRoundNumber() - 1;
    }

    @Override
    public double getGunCoolingRate() {
        log("getGunCoolingRate()");
        return bot.getGunCoolingRate();
    }

    @Override
    public double getDistanceRemaining() {
        log("getDistanceRemaining()");
        return bot.getDistanceRemaining();
    }

    @Override
    public double getBodyTurnRemaining() {
        log("getBodyTurnRemaining()");
        return -toRadians(bot.getTurnRemaining());
    }

    @Override
    public double getGunTurnRemaining() {
        log("getGunTurnRemaining()");
        return -toRadians(bot.getGunTurnRemaining());
    }

    @Override
    public double getRadarTurnRemaining() {
        log("getRadarTurnRemaining()");
        return -toRadians(bot.getRadarTurnRemaining());
    }

    @Override
    public void execute() {
        log("execute()");
        bot.go();
    }

    private void dispatchBotEvents() {
        bot.getEvents().forEach(this::dispatch);
    }

    private void dispatch(BotEvent botEvent) {
        switch (botEvent.getClass().getSimpleName()) {
            case "TickEvent":
                dispatchStatusEvent(botEvent);
                break;
            case "BulletFiredEvent":
                // Ignore, as this is not a Robocode Robot event
                break;
            case "ScannedBotEvent":
                dispatchScannedRobotEvent(botEvent);
                break;
            case "BulletHitWallEvent":
                dispatchBulletMissedEvent(botEvent);
                break;
            case "BulletHitBotEvent":
                dispatchBulletHitEvent(botEvent);
                break;
            case "HitByBulletEvent":
                dispatchHitByBulletEvent(botEvent);
                break;
            case "HitWallEvent":
                dispatchHitWallEvent();
                break;
            case "HitBotEvent":
                dispatchHitRobotEvent(botEvent);
                break;
            case "BotDeathEvent":
                var botDeathEvent = (BotDeathEvent) botEvent;
                if (botDeathEvent.getVictimId() == bot.getMyId()) {
                    dispatchDeathEvent();
                } else {
                    dispatchRobotDeathEvent(botDeathEvent);
                }
                break;
            case "SkippedTurnEvent":
                dispatchSkippedTurnEvent(botEvent);
                break;
            case "WonRoundEvent":
                dispatchWinEvent();
                break;
            case "BulletHitBulletEvent":
                dispatchBulletHitBulletEvent(botEvent);
                break;
            case "CustomEvent":
                dispatchCustomEvent(botEvent);
                break;
            case "MessageEvent":
                dispatchMessageEvent(botEvent);
                break;
            default:
                throw new UnsupportedOperationException(botEvent.getClass().getSimpleName() +
                        " is unsupported. Contact Robocode Tank Royale author for support");
        }
    }

    private void dispatchStatusEvent(BotEvent botEvent) {
        log("-> onStatus");

        // Save robot status snapshot for event handlers needing robot status
        RobotStatus robotStatus = IBotToRobotStatusMapper.map(bot);
        currentRobotStatus.set(robotStatus);

        TickEvent tickEvent = (TickEvent) botEvent;

        // Update fired bullets
        firedBullets.forEach(bulletPeer -> {
            Optional<BulletState> bulletStateOpt = tickEvent.getBulletStates().stream().filter(
                    bulletState -> bulletPeer.getBulletId() == bulletState.getBulletId()).findFirst();

            bulletStateOpt.ifPresent(bulletState ->
                    bulletPeer.setPosition(bulletState.getX(), bulletState.getY()));
        });

        // Fire event
        basicEvents.onStatus(StatusEventMapper.map(robotStatus));
    }

    private void dispatchScannedRobotEvent(BotEvent botEvent) {
        log("-> onScannedRobot");
        var scannedBotEvent = (ScannedBotEvent) botEvent;
        var scannedRobotEvent = ScannedRobotEventMapper.map(scannedBotEvent, bot);
        basicEvents.onScannedRobot(scannedRobotEvent);
    }

    private void dispatchBulletMissedEvent(BotEvent botEvent) {
        log("-> onBulletMissed");
        var bulletHitWallEvent = (BulletHitWallEvent) botEvent;
        Bullet bullet = BulletMapper.map(bulletHitWallEvent.getBullet(), null);
        basicEvents.onBulletMissed(new robocode.BulletMissedEvent(bullet));
    }

    private void dispatchBulletHitEvent(BotEvent botEvent) {
        log("-> onBulletHit");
        var bulletHitBotEvent = (BulletHitBotEvent) botEvent;
        var bulletState = bulletHitBotEvent.getBullet();
        var victimName = String.valueOf(bulletHitBotEvent.getVictimId());
        var bullet = new Bullet(
                toRobocodeHeadingRad(bulletState.getDirection()),
                bulletState.getX(),
                bulletState.getY(),
                bulletState.getPower(),
                String.valueOf(bulletState.getOwnerId()),
                victimName,
                false, // isActive
                bulletState.getBulletId());

        basicEvents.onBulletHit(new robocode.BulletHitEvent(victimName, bulletHitBotEvent.getEnergy(), bullet));
    }

    private void dispatchHitByBulletEvent(BotEvent botEvent) {
        log("-> onHitByBullet");
        var hitByBulletEvent = (HitByBulletEvent) botEvent;
        BulletState bullet = hitByBulletEvent.getBullet();
        double bearing = toRobocodeBearingRad(bot.bearingTo(bullet.getX(), bullet.getY()));
        basicEvents.onHitByBullet(new robocode.HitByBulletEvent(
                bearing, map(bullet, String.valueOf(bot.getMyId()))));
    }

    private void dispatchHitWallEvent() {
        log("-> onHitWall");
        basicEvents.onHitWall(new robocode.HitWallEvent(calcBearingToWallRadians(bot.getDirection())));
    }

    private void dispatchHitRobotEvent(BotEvent botEvent) {
        log("-> onHitRobot");

        var hitBotEvent = (HitBotEvent) botEvent;

        double bearing = toRobocodeBearingRad(bot.bearingTo(hitBotEvent.getX(), hitBotEvent.getY()));
        basicEvents.onHitRobot(new robocode.HitRobotEvent(
                String.valueOf(hitBotEvent.getVictimId()), bearing, hitBotEvent.getEnergy(), hitBotEvent.isRammed()
        ));
    }

    private void dispatchRobotDeathEvent(BotDeathEvent botDeathEvent) {
        log("-> onRobotDeath");
        basicEvents.onRobotDeath(new robocode.RobotDeathEvent(String.valueOf(botDeathEvent.getVictimId())));
    }

    private void dispatchSkippedTurnEvent(BotEvent botEvent) {
        log("-> onSkippedTurn");
        var skippedTurnEvent = (SkippedTurnEvent) botEvent;
        advancedEvents.onSkippedTurn(new robocode.SkippedTurnEvent(skippedTurnEvent.getTurnNumber()));
    }

    private void dispatchDeathEvent() {
        log("-> onDeath");
        basicEvents.onDeath(new robocode.DeathEvent());
    }

    private void dispatchWinEvent() {
        log("-> onWin");
        basicEvents.onWin(new robocode.WinEvent());
    }

    private void dispatchBulletHitBulletEvent(BotEvent botEvent) {
        log("-> onBulletHitBullet");
        var bulletHitBulletEvent = (BulletHitBulletEvent) botEvent;
        BulletPeer bullet = findBulletById(bulletHitBulletEvent.getBullet());
        Bullet hitBullet = BulletMapper.map(bulletHitBulletEvent.getHitBullet(), null);
        bullet.setInactive();

        firedBullets.remove(bullet);

        basicEvents.onBulletHitBullet(new robocode.BulletHitBulletEvent(bullet, hitBullet));
    }

    private void dispatchCustomEvent(BotEvent botEvent) {
        log("-> onCustomEvent");

        var customEvent = (CustomEvent) botEvent;
        Condition trCondition = customEvent.getCondition();
        if (trCondition == null) return;

        Optional<Map.Entry<robocode.Condition, Condition>> optCondition = conditions.entrySet().stream()
                .filter(entry -> trCondition.equals(entry.getValue())).findFirst();
        if (optCondition.isPresent()) {
            robocode.Condition condition = optCondition.get().getKey();
            advancedEvents.onCustomEvent(new robocode.CustomEvent(condition));
        }
    }

    private void dispatchMessageEvent(BotEvent botEvent) {
        throw new UnsupportedOperationException(botEvent.getClass().getSimpleName() +
                " is unsupported. Contact Robocode Tank Royale author for support");
    }

    @Override
    public void move(double distance) {
        log("move()");
        bot.forward(distance);
    }

    @Override
    public void turnBody(double radians) {
        log("turnBody()");
        bot.turnRight(toDegrees(radians));
    }

    @Override
    public void turnGun(double radians) {
        log("turnGun()");
        bot.turnGunRight(toDegrees(radians));
    }

    @Override
    public void turnRadar(double radians) {
        log("turnRadar()");
        bot.turnRadarRight(toDegrees(radians));
    }

    @Override
    public Bullet fire(double power) {
        log("fire()");
        bot.fire(power);
        return createAndAddBullet(power);
    }

    @Override
    public Bullet setFire(double power) {
        log("setFire()");
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
        log("setBodyColor()");
        bot.setBodyColor(ColorMapper.map(color));
    }

    @Override
    public void setGunColor(Color color) {
        log("setGunColor()");
        bot.setTurretColor(ColorMapper.map(color)); // yes, turret!
    }

    @Override
    public void setRadarColor(Color color) {
        log("setRadarColor()");
        bot.setRadarColor(ColorMapper.map(color));
    }

    @Override
    public void setBulletColor(Color color) {
        log("setBulletColor()");
        bot.setBulletColor(ColorMapper.map(color));
    }

    @Override
    public void setScanColor(Color color) {
        log("setScanColor()");
        bot.setScanColor(ColorMapper.map(color));
    }

    @Override
    public void getCall() { // ignore
        log("getCall()");
    }

    @Override
    public void setCall() { // ignore
        log("setCall()");
    }

    @Override
    public Graphics2D getGraphics() {
        log("getGraphics()");
        return graphics2D;
    }

    @Override
    public void setDebugProperty(String key, String value) { // ignore for now
        log("setDebugProperty()");
    }

    @Override
    public void rescan() {
        log("rescan()");
        bot.rescan();
    }

    //-------------------------------------------------------------------------
    // IStandardRobotPeer
    //-------------------------------------------------------------------------

    @Override
    public void stop(boolean overwrite) {
        log("stop()");
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
        log("resume()");
        bot.resume();
    }

    @Override
    public void setAdjustGunForBodyTurn(boolean adjust) {
        log("setAdjustGunForBodyTurn()");
        bot.setAdjustGunForBodyTurn(adjust);
    }

    @Override
    public void setAdjustRadarForGunTurn(boolean adjust) {
        log("setAdjustRadarForGunTurn()");
        bot.setAdjustRadarForGunTurn(adjust);
    }

    @Override
    public void setAdjustRadarForBodyTurn(boolean adjust) {
        log("setAdjustRadarForBodyTurn()");
        bot.setAdjustRadarForBodyTurn(adjust);
    }

    //-------------------------------------------------------------------------
    // IAdvancedRobotPeer
    //-------------------------------------------------------------------------

    @Override
    public boolean isAdjustGunForBodyTurn() {
        log("isAdjustGunForBodyTurn()");
        return bot.isAdjustGunForBodyTurn();
    }

    @Override
    public boolean isAdjustRadarForGunTurn() {
        log("isAdjustRadarForGunTurn()");
        return bot.isAdjustRadarForGunTurn();
    }

    @Override
    public boolean isAdjustRadarForBodyTurn() {
        log("isAdjustRadarForBodyTurn()");
        return bot.isAdjustRadarForBodyTurn();
    }

    @Override
    public void setStop(boolean overwrite) {
        log("setStop()");
        bot.setStop(overwrite);
    }

    @Override
    public void setResume() {
        log("setResume()");
        bot.setResume();
    }

    @Override
    public void setMove(double distance) {
        log("setMove()");
        if (Double.isNaN(distance)) {
            distance = 0;
        }
        bot.setForward(distance);
    }

    @Override
    public void setTurnBody(double radians) {
        log("setTurnBody()");
        if (Double.isNaN(radians)) {
            radians = 0; // orig. Robocode treats NaN as 0
        }
        bot.setTurnRight(toDegrees(radians));
    }

    @Override
    public void setTurnGun(double radians) {
        log("setTurnGun()");
        if (Double.isNaN(radians)) {
            radians = 0; // orig. Robocode treats NaN as 0
        }
        bot.setTurnGunRight(toDegrees(radians));
    }

    @Override
    public void setTurnRadar(double radians) {
        log("setTurnRadar()");
        if (Double.isNaN(radians)) {
            radians = 0; // orig. Robocode treats NaN as 0
        }
        bot.setTurnRadarRight(toDegrees(radians));
    }

    @Override
    public void setMaxTurnRate(double newMaxTurnRate) {
        log("setMaxTurnRate()");
        bot.setMaxTurnRate(newMaxTurnRate);
    }

    @Override
    public void setMaxVelocity(double newMaxVelocity) {
        log("setMaxVelocity()");
        bot.setMaxSpeed(newMaxVelocity);
    }

    @Override
    public void waitFor(robocode.Condition condition) {
        log("waitFor()");
        bot.waitFor(new Condition(condition.getName()) {
            @Override
            public boolean test() {
                return condition.test();
            }
        });
    }

    @Override
    public void setInterruptible(boolean interruptible) {
        log("setInterruptible()");
        bot.setInterruptible(interruptible);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setEventPriority(String eventClass, int priority) {
        log("setEventPriority()");

        if ("PaintEvent".equals(eventClass)) {
            // PaintEvent is not supported (yet) -> just ignore it as it is not crucial for the bot to run
            return;
        }

        var botEvent = EventClassMapper.toBotEventClass(eventClass);
        bot.setEventPriority((Class<BotEvent>) botEvent, priority);
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getEventPriority(String eventClass) {
        log("getEventPriority()");
        var botEvent = EventClassMapper.toBotEventClass(eventClass);
        return bot.getEventPriority((Class<BotEvent>) botEvent);
    }

    @Override
    public void addCustomEvent(robocode.Condition condition) {
        log("addCustomEvent()");
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
        log("removeCustomEvent()");
        Condition trCondition = conditions.get(condition);
        if (trCondition != null) {
            bot.removeCustomEvent(trCondition);
        }
    }

    @Override
    public void clearAllEvents() {
        log("clearAllEvents()");
        bot.clearEvents();
    }

    @Override
    public List<robocode.Event> getAllEvents() {
        log("getAllEvents()");
        return AllEventsMapper.map(bot.getEvents(), bot, currentRobotStatus.get());
    }

    @Override
    public List<robocode.StatusEvent> getStatusEvents() {
        log("getStatusEvents()");
        return getAllEvents().stream()
                .filter(StatusEvent.class::isInstance)
                .map(StatusEvent.class::cast)
                .collect(Collectors.toList());
    }


    @Override
    public List<robocode.BulletMissedEvent> getBulletMissedEvents() {
        log("getBulletMissedEvents()");
        return getAllEvents().stream()
                .filter(BulletMissedEvent.class::isInstance)
                .map(BulletMissedEvent.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public List<robocode.BulletHitBulletEvent> getBulletHitBulletEvents() {
        log("getBulletHitBulletEvents()");
        return getAllEvents().stream()
                .filter(robocode.BulletHitBulletEvent.class::isInstance)
                .map(robocode.BulletHitBulletEvent.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public List<robocode.BulletHitEvent> getBulletHitEvents() {
        log("getBulletHitEvents()");
        return getAllEvents().stream()
                .filter(BulletHitEvent.class::isInstance)
                .map(BulletHitEvent.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public List<robocode.HitByBulletEvent> getHitByBulletEvents() {
        log("getHitByBulletEvents()");
        return getAllEvents().stream()
                .filter(robocode.HitByBulletEvent.class::isInstance)
                .map(robocode.HitByBulletEvent.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public List<robocode.HitRobotEvent> getHitRobotEvents() {
        log("getHitRobotEvents()");
        return getAllEvents().stream()
                .filter(HitRobotEvent.class::isInstance)
                .map(HitRobotEvent.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public List<robocode.HitWallEvent> getHitWallEvents() {
        log("getHitWallEvents()");
        return getAllEvents().stream()
                .filter(robocode.HitWallEvent.class::isInstance)
                .map(robocode.HitWallEvent.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public List<robocode.RobotDeathEvent> getRobotDeathEvents() {
        log("getRobotDeathEvents()");
        return getAllEvents().stream()
                .filter(RobotDeathEvent.class::isInstance)
                .map(RobotDeathEvent.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public List<robocode.ScannedRobotEvent> getScannedRobotEvents() {
        log("getScannedRobotEvents()");
        return getAllEvents().stream()
                .filter(ScannedRobotEvent.class::isInstance)
                .map(ScannedRobotEvent.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public File getDataDirectory() {
        log("getDataDirectory()");
        return Paths.get("").toFile(); // use current path (where the application was started from)
    }

    @Override
    public File getDataFile(String filename) {
        log("getDataFile()");
        return RobotData.getDataFile(filename);
    }

    @Override
    public long getDataQuotaAvailable() {
        log("getDataQuotaAvailable()");
        return 200_000;
    }


    //-------------------------------------------------------------------------
    // IJuniorRobotPeer
    //-------------------------------------------------------------------------

    @Override
    public void turnAndMove(double distance, double radians) {
        log("turnAndMove()");
        JuniorRobotImpl.turnAndMove(bot, distance, toDegrees(radians));
    }

    //-------------------------------------------------------------------------
    // ITeamRobotPeer
    //-------------------------------------------------------------------------

    @Override
    public String[] getTeammates() {
        log("getTeammates()");
        var teammates = bot.getTeammateIds();
        return (teammates != null) ? teammates.stream().map(String::valueOf).toArray(String[]::new) : null;
    }

    @Override
    public boolean isTeammate(String name) {
        log("isTeammate()");
        try {
            var id = Integer.parseInt(name);
            return bot.isTeammate(id);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void broadcastMessage(Serializable message) {
        log("broadcastMessage()");
        bot.broadcastTeamMessage(message);
    }

    @Override
    public void sendMessage(String name, Serializable message) {
        log("sendMessage()");
        try {
            var id = Integer.parseInt(name);
            bot.sendTeamMessage(id, message);
        } catch (NumberFormatException ignore) {
            throw new BotException("sendMessage: Cannot find receiver of team message: " + name);
        }
    }

    @Override
    public List<MessageEvent> getMessageEvents() {
        log("getMessageEvents()");
        return getAllEvents().stream()
                .filter(e -> e instanceof MessageEvent)
                .map(e -> (MessageEvent) e)
                .collect(Collectors.toList());
    }

    private double calcBearingToWallRadians(double directionDeg) {
        int minX = 40;
        int minY = 40;
        int maxX = bot.getArenaWidth() - 40;
        int maxY = bot.getArenaHeight() - 40;

        double angle = 0;
        if (getX() < minX) {
            angle = bot.normalizeRelativeAngle(180 - directionDeg);
        } else if (getX() > maxX) {
            angle = bot.normalizeRelativeAngle(360 - directionDeg);
        }
        if (getY() < minY) {
            angle = normalRelativeAngle(90 - directionDeg);
        } else if (getY() > maxY) {
            angle = normalRelativeAngle(270 - directionDeg);
        }
        return toRobocodeBearingRad(angle);
    }

    private BulletPeer findBulletById(BulletState bulletState) {
        var bulletPeer = firedBullets.stream().filter(
                        bullet -> bulletState.getBulletId() == bullet.getBulletId())
                .findFirst()
                .orElse(null);

        if (bulletPeer == null) {
            bulletPeer = findBulletByXAndY(bulletState);
        }
        if (bulletPeer == null) {
            throw new BotException("findBulletById: Could not find bullet: " + bulletState.getBulletId());
        }
        return bulletPeer;
    }

    private BulletPeer findBulletByXAndY(BulletState bulletState) {
        var foundBullet = new AtomicReference<BulletPeer>();
        var minDist = new AtomicReference<>(Double.MAX_VALUE);

        firedBullets.forEach(bullet -> {
            var dist = Math.pow(bullet.getX() - bulletState.getX(), 2) + Math.pow(bullet.getY() - bulletState.getY(), 2);
            if (dist < minDist.get()) {
                foundBullet.set(bullet);
                minDist.set(dist);
            }
        });
        return foundBullet.get();
    }

    //-------------------------------------------------------------------------
    // IBasicEvents3 and IAdvancedEvents event triggers
    //-------------------------------------------------------------------------

    private class BotImpl extends Bot {

        final AtomicInteger totalTurns = new AtomicInteger(0);

        BotImpl(BotInfo botInfo) {
            super(botInfo);
        }

        @Override
        public void run() {
            log("Bot.run()");

            stopThread = false;

            while (bot.isRunning()) {
                robot.getRobotRunnable().run();
            }

            log("Bot.run() -> exit");
        }

        @Override
        public void go() {
            super.go();

            dispatchBotEvents();
        }

        @Override
        public void onGameStarted(GameStartedEvent gameStatedEvent) {
            totalTurns.set(0);
        }

        @Override
        public void onGameEnded(GameEndedEvent gameEndedEvent) {
            log("-> onBattleEnded");
            if (basicEvents instanceof IBasicEvents2) {
                ((IBasicEvents2) basicEvents).onBattleEnded(new robocode.BattleEndedEvent(
                        false, map(gameEndedEvent.getResults(), String.valueOf(getMyId())))
                );
            }
        }

        @Override
        public void onRoundStarted(RoundStartedEvent roundStartedEvent) {
            // no event handler for `round started` in orig. Robocode
            firedBullets.clear();
        }

        @Override
        public void onRoundEnded(RoundEndedEvent roundEndedEvent) {
            log("-> onRoundEnded");
            try {
                int turnNumber = roundEndedEvent.getTurnNumber();
                int newTotalTurns = totalTurns.addAndGet(turnNumber);

                if (basicEvents instanceof IBasicEvents3) {
                    ((IBasicEvents3) basicEvents).onRoundEnded(
                            new robocode.RoundEndedEvent(roundEndedEvent.getRoundNumber() - 1, turnNumber, newTotalTurns));
                }
            } finally {
                robot.stopThread();
            }
        }

        @Override
        public void onBulletFired(BulletFiredEvent bulletFiredEvent) {
            BulletState bulletState = bulletFiredEvent.getBullet();
            BulletPeer bullet = findBulletByXAndY(bulletState);
            if (bullet == null) {
                throw new BotException("onBulletFired: Could not find bullet: " + bulletState.getX() + "," + bulletState.getY());
            }
            bullet.setBulletId(bulletState.getBulletId());
        }
    }

    private static void log(String message) {
//        System.out.println(message);
    }

    @Override
    public void stopThread() {
        stopThread = true;
    }
}
