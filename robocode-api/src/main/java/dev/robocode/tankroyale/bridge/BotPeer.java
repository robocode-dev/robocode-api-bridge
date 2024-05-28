package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.*;
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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRcBearingRad;
import static dev.robocode.tankroyale.bridge.AngleConverter.toRcHeadingRad;
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
    private final Set<BulletPeer> firedBullets = Collections.newSetFromMap(new HashMap<>());
    private final Graphics2D graphics2D = new Graphics2DImpl();

    private final Map<robocode.Condition, Condition> conditions = new HashMap<>();
    private final Map<Long, RobotStatus> robotStatuses = new HashMap<>();

    private boolean isRoundRunning;

    @SuppressWarnings("unused")
    public BotPeer(IBasicRobot robot, BotInfo botInfo) {
        checkForStopExecution();
        log("BotPeer");

        this.robot = robot;
        bot = new BotImpl(botInfo);

        robot.setOut(System.out); // Redirect output to "our" System.out, which Tank Royale is overriding

        basicEvents = robot.getBasicEventListener();

        if (robot instanceof IAdvancedRobot) {
            advancedEvents = ((IAdvancedRobot)robot).getAdvancedEventListener();
        } else {
            advancedEvents = new AdvancedEventAdaptor();
        }

        init();
    }

    @SuppressWarnings("unused")
    public void start() {
        checkForStopExecution();
        log("start()");
        bot.start();
    }

    private void init() {
        RobotName.setName(robot.getClass().getSimpleName());
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
        checkForStopExecution();
        log("getName()");
        return RobotName.getName();
    }

    @Override
    public long getTime() {
        checkForStopExecution();
        log("getTime()");
        return bot.getTurnNumber();
    }

    @Override
    public double getEnergy() {
        checkForStopExecution();
        log("getEnergy()");
        return bot.getEnergy();
    }

    @Override
    public double getX() {
        checkForStopExecution();
        log("getX()");
        return bot.getX();
    }

    @Override
    public double getY() {
        checkForStopExecution();
        log("getY()");
        return bot.getY();
    }

    @Override
    public double getVelocity() {
        checkForStopExecution();
        log("getVelocity()");
        return bot.getSpeed();
    }

    @Override
    public double getBodyHeading() {
        checkForStopExecution();
        log("getBodyHeading()");
        return toRcHeadingRad(bot.getDirection());
    }

    @Override
    public double getGunHeading() {
        checkForStopExecution();
        log("getGunHeading()");
        return toRcHeadingRad(bot.getGunDirection());
    }

    @Override
    public double getRadarHeading() {
        checkForStopExecution();
        log("getRadarHeading()");
        return toRcHeadingRad(bot.getRadarDirection());
    }

    @Override
    public double getGunHeat() {
        checkForStopExecution();
        log("getGunHeat()");
        return bot.getGunHeat();
    }

    @Override
    public double getBattleFieldWidth() {
        checkForStopExecution();
        log("getBattleFieldWidth()");
        return bot.getArenaWidth();
    }

    @Override
    public double getBattleFieldHeight() {
        checkForStopExecution();
        log("getBattleFieldHeight()");
        return bot.getArenaHeight();
    }

    @Override
    public int getOthers() {
        checkForStopExecution();
        log("getOthers()");
        return bot.getEnemyCount();
    }

    @Override
    public int getNumSentries() { // Not supported
        checkForStopExecution();
        log("getNumSentries()");
        return 0;
    }

    @Override
    public int getSentryBorderSize() { // Not supported
        checkForStopExecution();
        log("getSentryBorderSize()");
        return 0;
    }

    @Override
    public int getNumRounds() {
        checkForStopExecution();
        log("getNumRounds()");
        return bot.getNumberOfRounds();
    }

    @Override
    public int getRoundNum() {
        checkForStopExecution();
        log("getRoundNum()");
        return bot.getRoundNumber() - 1;
    }

    @Override
    public double getGunCoolingRate() {
        checkForStopExecution();
        log("getGunCoolingRate()");
        return bot.getGunCoolingRate();
    }

    @Override
    public double getDistanceRemaining() {
        checkForStopExecution();
        log("getDistanceRemaining()");
        return bot.getDistanceRemaining();
    }

    @Override
    public double getBodyTurnRemaining() {
        checkForStopExecution();
        log("getBodyTurnRemaining()");
        return -toRadians(bot.getTurnRemaining());
    }

    @Override
    public double getGunTurnRemaining() {
        checkForStopExecution();
        log("getGunTurnRemaining()");
        return -toRadians(bot.getGunTurnRemaining());
    }

    @Override
    public double getRadarTurnRemaining() {
        checkForStopExecution();
        log("getRadarTurnRemaining()");
        return -toRadians(bot.getRadarTurnRemaining());
    }

    @Override
    public void execute() {
        checkForStopExecution();
        log("execute()");
        bot.go();
    }

    @Override
    public void move(double distance) {
        checkForStopExecution();
        log("move()");
        bot.forward(distance);
    }

    @Override
    public void turnBody(double radians) {
        checkForStopExecution();
        log("turnBody()");
        bot.turnRight(toDegrees(radians));
    }

    @Override
    public void turnGun(double radians) {
        checkForStopExecution();
        log("turnGun()");
        bot.turnGunRight(toDegrees(radians));
    }

    @Override
    public void turnRadar(double radians) {
        checkForStopExecution();
        log("turnRadar()");
        bot.turnRadarRight(toDegrees(radians));
    }

    @Override
    public Bullet fire(double power) {
        checkForStopExecution();
        log("fire()");
        bot.fire(power);
        return createAndAddBullet(power);
    }

    @Override
    public Bullet setFire(double power) {
        checkForStopExecution();
        log("setFire()");
        if (bot.setFire(power)) {
            return createAndAddBullet(power);
        }
        return null;
    }

    private BulletPeer createAndAddBullet(double power) {
        BulletPeer bullet = new BulletPeer(bot, power);
        synchronized (firedBullets) {
            firedBullets.add(bullet);
        }
        return bullet;
    }

    @Override
    public void setBodyColor(Color color) {
        checkForStopExecution();
        log("setBodyColor()");
        bot.setBodyColor(ColorMapper.map(color));
    }

    @Override
    public void setGunColor(Color color) {
        checkForStopExecution();
        log("setGunColor()");
        bot.setTurretColor(ColorMapper.map(color)); // yes, turret!
    }

    @Override
    public void setRadarColor(Color color) {
        checkForStopExecution();
        log("setRadarColor()");
        bot.setRadarColor(ColorMapper.map(color));
    }

    @Override
    public void setBulletColor(Color color) {
        checkForStopExecution();
        log("setBulletColor()");
        bot.setBulletColor(ColorMapper.map(color));
    }

    @Override
    public void setScanColor(Color color) {
        checkForStopExecution();
        log("setScanColor()");
        bot.setScanColor(ColorMapper.map(color));
    }

    @Override
    public void getCall() { // ignore
        checkForStopExecution();
        log("getCall()");
    }

    @Override
    public void setCall() { // ignore
        checkForStopExecution();
        log("setCall()");
    }

    @Override
    public Graphics2D getGraphics() {
        checkForStopExecution();
        log("getGraphics()");
        return graphics2D;
    }

    @Override
    public void setDebugProperty(String key, String value) { // ignore for now
        checkForStopExecution();
        log("setDebugProperty()");
    }

    @Override
    public void rescan() {
        checkForStopExecution();
        log("rescan()");
        bot.rescan();
    }

    //-------------------------------------------------------------------------
    // IStandardRobotPeer
    //-------------------------------------------------------------------------

    @Override
    public void stop(boolean overwrite) {
        checkForStopExecution();
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
        checkForStopExecution();
        log("resume()");
        bot.resume();
    }

    @Override
    public void setAdjustGunForBodyTurn(boolean adjust) {
        checkForStopExecution();
        log("setAdjustGunForBodyTurn()");
        bot.setAdjustGunForBodyTurn(adjust);
    }

    @Override
    public void setAdjustRadarForGunTurn(boolean adjust) {
        checkForStopExecution();
        log("setAdjustRadarForGunTurn()");
        bot.setAdjustRadarForGunTurn(adjust);
    }

    @Override
    public void setAdjustRadarForBodyTurn(boolean adjust) {
        checkForStopExecution();
        log("setAdjustRadarForBodyTurn()");
        bot.setAdjustRadarForBodyTurn(adjust);
    }

    //-------------------------------------------------------------------------
    // IAdvancedRobotPeer
    //-------------------------------------------------------------------------

    @Override
    public boolean isAdjustGunForBodyTurn() {
        checkForStopExecution();
        log("isAdjustGunForBodyTurn()");
        return bot.isAdjustGunForBodyTurn();
    }

    @Override
    public boolean isAdjustRadarForGunTurn() {
        checkForStopExecution();
        log("isAdjustRadarForGunTurn()");
        return bot.isAdjustRadarForGunTurn();
    }

    @Override
    public boolean isAdjustRadarForBodyTurn() {
        checkForStopExecution();
        log("isAdjustRadarForBodyTurn()");
        return bot.isAdjustRadarForBodyTurn();
    }

    @Override
    public void setStop(boolean overwrite) {
        checkForStopExecution();
        log("setStop()");
        bot.setStop(overwrite);
    }

    @Override
    public void setResume() {
        checkForStopExecution();
        log("setResume()");
        bot.setResume();
    }

    @Override
    public void setMove(double distance) {
        checkForStopExecution();
        log("setMove()");
        if (Double.isNaN(distance)) {
            distance = 0;
        }
        bot.setForward(distance);
    }

    @Override
    public void setTurnBody(double radians) {
        checkForStopExecution();
        log("setTurnBody()");
        if (Double.isNaN(radians)) {
            radians = 0; // orig. Robocode treats NaN as 0
        }
        bot.setTurnRight(toDegrees(radians));
    }

    @Override
    public void setTurnGun(double radians) {
        checkForStopExecution();
        log("setTurnGun()");
        if (Double.isNaN(radians)) {
            radians = 0; // orig. Robocode treats NaN as 0
        }
        bot.setTurnGunRight(toDegrees(radians));
    }

    @Override
    public void setTurnRadar(double radians) {
        checkForStopExecution();
        log("setTurnRadar()");
        if (Double.isNaN(radians)) {
            radians = 0; // orig. Robocode treats NaN as 0
        }
        bot.setTurnRadarRight(toDegrees(radians));
    }

    @Override
    public void setMaxTurnRate(double newMaxTurnRate) {
        checkForStopExecution();
        log("setMaxTurnRate()");
        bot.setMaxTurnRate(newMaxTurnRate);
    }

    @Override
    public void setMaxVelocity(double newMaxVelocity) {
        checkForStopExecution();
        log("setMaxVelocity()");
        bot.setMaxSpeed(newMaxVelocity);
    }

    @Override
    public void waitFor(robocode.Condition condition) {
        checkForStopExecution();
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
        checkForStopExecution();
        log("setInterruptible()");
        bot.setInterruptible(interruptible);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setEventPriority(String eventClass, int priority) {
        checkForStopExecution();
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
        checkForStopExecution();
        log("getEventPriority()");
        var botEvent = EventClassMapper.toBotEventClass(eventClass);
        return bot.getEventPriority((Class<BotEvent>) botEvent);
    }

    @Override
    public void addCustomEvent(robocode.Condition condition) {
        checkForStopExecution();
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
        checkForStopExecution();
        log("removeCustomEvent()");
        Condition trCondition = conditions.get(condition);
        if (trCondition != null) {
            bot.removeCustomEvent(trCondition);
        }
    }

    @Override
    public void clearAllEvents() {
        checkForStopExecution();
        log("clearAllEvents()");
        bot.clearEvents();
    }

    @Override
    public List<robocode.Event> getAllEvents() {
        checkForStopExecution();
        log("getAllEvents()");
        return AllEventsMapper.map(bot.getEvents(), bot, robotStatuses);
    }

    @Override
    public List<robocode.StatusEvent> getStatusEvents() {
        checkForStopExecution();
        log("getStatusEvents()");
        return getAllEvents().stream()
                .filter(e -> e instanceof StatusEvent)
                .map(e -> (StatusEvent) e)
                .collect(Collectors.toList());
    }

    @Override
    public List<robocode.BulletMissedEvent> getBulletMissedEvents() {
        checkForStopExecution();
        log("getBulletMissedEvents()");
        return getAllEvents().stream()
                .filter(e -> e instanceof BulletMissedEvent)
                .map(e -> (BulletMissedEvent) e)
                .collect(Collectors.toList());
    }

    @Override
    public List<robocode.BulletHitBulletEvent> getBulletHitBulletEvents() {
        checkForStopExecution();
        log("getBulletHitBulletEvents()");
        return getAllEvents().stream()
                .filter(e -> e instanceof robocode.BulletHitBulletEvent)
                .map(e -> (robocode.BulletHitBulletEvent) e)
                .collect(Collectors.toList());
    }

    @Override
    public List<robocode.BulletHitEvent> getBulletHitEvents() {
        checkForStopExecution();
        log("getBulletHitEvents()");
        return getAllEvents().stream()
                .filter(e -> e instanceof BulletHitEvent)
                .map(e -> (BulletHitEvent) e)
                .collect(Collectors.toList());
    }

    @Override
    public List<robocode.HitByBulletEvent> getHitByBulletEvents() {
        checkForStopExecution();
        log("getHitByBulletEvents()");
        return getAllEvents().stream()
                .filter(e -> e instanceof robocode.HitByBulletEvent)
                .map(e -> (robocode.HitByBulletEvent) e)
                .collect(Collectors.toList());
    }

    @Override
    public List<robocode.HitRobotEvent> getHitRobotEvents() {
        checkForStopExecution();
        log("getHitRobotEvents()");
        return getAllEvents().stream()
                .filter(e -> e instanceof HitRobotEvent)
                .map(e -> (HitRobotEvent) e)
                .collect(Collectors.toList());
    }

    @Override
    public List<robocode.HitWallEvent> getHitWallEvents() {
        checkForStopExecution();
        log("getHitWallEvents()");
        return getAllEvents().stream()
                .filter(e -> e instanceof robocode.HitWallEvent)
                .map(e -> (robocode.HitWallEvent) e)
                .collect(Collectors.toList());
    }

    @Override
    public List<robocode.RobotDeathEvent> getRobotDeathEvents() {
        checkForStopExecution();
        log("getRobotDeathEvents()");
        return getAllEvents().stream()
                .filter(e -> e instanceof RobotDeathEvent)
                .map(e -> (RobotDeathEvent) e)
                .collect(Collectors.toList());
    }

    @Override
    public List<robocode.ScannedRobotEvent> getScannedRobotEvents() {
        checkForStopExecution();
        log("getScannedRobotEvents()");
        return getAllEvents().stream()
                .filter(e -> e instanceof ScannedRobotEvent)
                .map(e -> (ScannedRobotEvent) e)
                .collect(Collectors.toList());
    }

    @Override
    public File getDataDirectory() {
        checkForStopExecution();
        log("getDataDirectory()");
        return Paths.get("").toFile(); // use current path (where the application was started from)
    }

    @Override
    public File getDataFile(String filename) {
        checkForStopExecution();
        log("getDataFile()");
        return RobotData.getDataFile(filename);
    }

    @Override
    public long getDataQuotaAvailable() {
        checkForStopExecution();
        log("getDataQuotaAvailable()");
        return 200_000;
    }


    //-------------------------------------------------------------------------
    // IJuniorRobotPeer
    //-------------------------------------------------------------------------

    @Override
    public void turnAndMove(double distance, double radians) {
        checkForStopExecution();
        log("turnAndMove()");
        JuniorRobotImpl.turnAndMove(bot, distance, toDegrees(radians));
    }

    //-------------------------------------------------------------------------
    // ITeamRobotPeer
    //-------------------------------------------------------------------------

    @Override
    public String[] getTeammates() {
        checkForStopExecution();
        log("getTeammates()");
        var teammates = bot.getTeammateIds();
        return (teammates != null) ? teammates.stream().map(String::valueOf).toArray(String[]::new) : null;
    }

    @Override
    public boolean isTeammate(String name) {
        checkForStopExecution();
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
        checkForStopExecution();
        log("broadcastMessage()");
        bot.broadcastTeamMessage(message);
    }

    @Override
    public void sendMessage(String name, Serializable message) {
        checkForStopExecution();
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
        checkForStopExecution();
        log("getMessageEvents()");
        return getAllEvents().stream()
                .filter(e -> e instanceof MessageEvent)
                .map(e -> (MessageEvent) e)
                .collect(Collectors.toList());
    }

    private void checkForStopExecution() {
        if (isRoundRunning && bot != null && !bot.isRunning()) {
            throw new StopRunningError();
        }
    }

    //-------------------------------------------------------------------------
    // IBasicEvents3 and IAdvancedEvents event triggers
    //-------------------------------------------------------------------------½

    private class BotImpl extends Bot {

        int totalTurns;

        BotImpl(BotInfo botInfo) {
            super(botInfo);
        }

        @Override
        public void run() {
            log("Bot.run()");

            try {
                var robotStatus = IBotToRobotStatusMapper.map(bot);
                basicEvents.onStatus(StatusEventMapper.map(robotStatus, getTime()));

                if (robot instanceof IJuniorRobot) {
                    while (bot.isRunning()) {
                        runRobot();
                    }
                } else {
                    runRobot();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } catch (StopRunningError ignored) {
                // this error is called to stop executing, as we cannot kill the thread with Thread.kill()
            }
        }

        private void runRobot() {
            if (robot instanceof Runnable) {
                ((Runnable)robot).run();
            }
        }

        @Override
        public void onGameStarted(GameStartedEvent gameStatedEvent) {
            totalTurns = 0;
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
            log("-> onRoundStarted");

            isRoundRunning = true;

            // no event handler for `round started` in orig. Robocode

            synchronized (firedBullets) {
                firedBullets.clear();
            }
            robotStatuses.clear();
        }

        @Override
        public void onRoundEnded(RoundEndedEvent roundEndedEvent) {
            log("-> onRoundEnded");

            try {
                int turnNumber = roundEndedEvent.getTurnNumber();
                totalTurns += turnNumber;

                if (basicEvents instanceof IBasicEvents3) {
                    ((IBasicEvents3) basicEvents).onRoundEnded(
                            new robocode.RoundEndedEvent(roundEndedEvent.getRoundNumber() - 1, turnNumber, totalTurns));
                }
            } finally {
                stop();
            }
            isRoundRunning = false;
        }

        @Override
        public void onTick(TickEvent tickEvent) {
            log("-> onStatus");

            // Save robot status snapshot for event handlers needing robot status
            RobotStatus robotStatus = IBotToRobotStatusMapper.map(bot);
            long time = tickEvent.getTurnNumber();
            robotStatuses.put(time, robotStatus);

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
            basicEvents.onStatus(StatusEventMapper.map(robotStatus, time));
        }

        @Override
        public void onBotDeath(BotDeathEvent botDeathEvent) {
            log("-> onRobotDeath");
            basicEvents.onRobotDeath(new robocode.RobotDeathEvent(String.valueOf(botDeathEvent.getVictimId())));
        }

        @Override
        public void onDeath(DeathEvent botDeathEvent) {
            log("-> onDeath");
            basicEvents.onDeath(new robocode.DeathEvent());
        }

        @Override
        public void onHitBot(HitBotEvent botHitBotEvent) {
            log("-> onHitRobot");
            double bearing = toRcBearingRad(bearingTo(botHitBotEvent.getX(), botHitBotEvent.getY()));
            basicEvents.onHitRobot(new robocode.HitRobotEvent(
                    String.valueOf(botHitBotEvent.getVictimId()), bearing, botHitBotEvent.getEnergy(), botHitBotEvent.isRammed()
            ));
        }

        @Override
        public void onHitWall(HitWallEvent botHitWallEvent) {
            log("-> onHitWall");
            basicEvents.onHitWall(new robocode.HitWallEvent(calcBearingToWallRadians(getDirection())));
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

        @Override
        public void onHitByBullet(HitByBulletEvent hitByBulletEvent) {
            log("-> onHitByBullet");
            BulletState bullet = hitByBulletEvent.getBullet();
            double bearing = toRcBearingRad(bearingTo(bullet.getX(), bullet.getY()));
            basicEvents.onHitByBullet(new robocode.HitByBulletEvent(
                    bearing, map(bullet, String.valueOf(this.getMyId()))));
        }

        @Override
        public void onBulletHit(BulletHitBotEvent bulletHitBotEvent) {
            log("-> onBulletHit");
            var bulletState = bulletHitBotEvent.getBullet();
            var victimName = String.valueOf(bulletHitBotEvent.getVictimId());
            var bullet = new Bullet(
                    toRcHeadingRad(bulletState.getDirection()),
                    bulletState.getX(),
                    bulletState.getY(),
                    bulletState.getPower(),
                    String.valueOf(bulletState.getOwnerId()),
                    victimName,
                    false, // isActive
                    bulletState.getBulletId());

            basicEvents.onBulletHit(new robocode.BulletHitEvent(victimName, bulletHitBotEvent.getEnergy(), bullet));
        }

        @Override
        public void onBulletHitBullet(BulletHitBulletEvent bulletHitBulletEvent) {
            log("-> onBulletHitBullet");
            BulletPeer bullet = findBulletById(bulletHitBulletEvent.getBullet());
            Bullet hitBullet = BulletMapper.map(bulletHitBulletEvent.getHitBullet(), null);
            bullet.setInactive();

            synchronized (firedBullets) {
                firedBullets.remove(bullet);
            }

            basicEvents.onBulletHitBullet(new robocode.BulletHitBulletEvent(bullet, hitBullet));
        }

        @Override
        public void onBulletHitWall(BulletHitWallEvent bulletHitWallEvent) {
            log("-> onBulletMissed");
            Bullet bullet = BulletMapper.map(bulletHitWallEvent.getBullet(), null);
            basicEvents.onBulletMissed(new robocode.BulletMissedEvent(bullet));
        }

        @Override
        public void onScannedBot(ScannedBotEvent scannedBotEvent) {
            log("-> onScannedRobot");
            var scannedRobotEvent = ScannedRobotEventMapper.map(scannedBotEvent, bot);
            basicEvents.onScannedRobot(scannedRobotEvent);
        }

        @Override
        public void onSkippedTurn(SkippedTurnEvent skippedTurnEvent) {
            log("-> onSkippedTurn");
            advancedEvents.onSkippedTurn(new robocode.SkippedTurnEvent(skippedTurnEvent.getTurnNumber()));
        }

        @Override
        public void onWonRound(WonRoundEvent wonRoundEvent) {
            log("-> onWin");
            basicEvents.onWin(new robocode.WinEvent());
        }

        @Override
        public void onCustomEvent(CustomEvent customEvent) {
            log("-> onCustomEvent");
            Condition trCondition = customEvent.getCondition();
            if (trCondition == null) return;

            Optional<Map.Entry<robocode.Condition, Condition>> optCondition = conditions.entrySet().stream()
                    .filter(entry -> trCondition.equals(entry.getValue())).findFirst();
            if (optCondition.isPresent()) {
                robocode.Condition condition = optCondition.get().getKey();
                advancedEvents.onCustomEvent(new robocode.CustomEvent(condition));
            }
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

        private double calcBearingToWallRadians(double directionDeg) {
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
            return toRcHeadingRad(angle);
        }
    }

    private static void log(String message) {
        System.out.println(message);
    }
}
