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
import java.io.IOException;
import java.io.Serializable;
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

    private static final int MESSAGE_EVENT_PRIORITY = 75;
    private static final String SKIPPED_TURN_TELEMETRY_PROPERTY =
            "robocode.bridge.skippedTurnTelemetry";
    private static final boolean SKIPPED_TURN_TELEMETRY_ENABLED =
            Boolean.getBoolean(SKIPPED_TURN_TELEMETRY_PROPERTY);

    private volatile IBasicRobot robot;
    private volatile IBasicEvents basicEvents;
    private volatile IAdvancedEvents advancedEvents;
    private boolean firstRobotInstanceUsed;

    private final IBot bot;
    private final Set<BulletPeer> firedBullets = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private final Map<Integer, BulletPeer> mappedBullets = new ConcurrentHashMap<>();
    private volatile int initialOtherCount = -1;
    private final Set<Integer> deadOtherBots = ConcurrentHashMap.newKeySet();
    private final Graphics2D graphics2D = new Graphics2DImpl();

    private final Map<robocode.Condition, Condition> conditions = new ConcurrentHashMap<>();
    private final AtomicReference<RobotStatus> currentRobotStatus = new AtomicReference<>();

    private volatile boolean initialStatusDispatched;
    private volatile boolean initialStatusCallbackActive;
    // The Bot API receives ticks on its WebSocket thread. Classic robot time advances only
    // when the robot thread receives the next status during execute(), not on network receipt.
    private volatile long deliveredTurn = -1;
    private boolean stopThread;
    private volatile int suppressScansThroughTurn = -1;
    private boolean hitWallHandlerActive;
    private boolean hitWallHandlerBlocked;
    private boolean suppressScansForBlockedWallHandler;
    private final List<String> skippedTurnTelemetryRecords = SKIPPED_TURN_TELEMETRY_ENABLED
            ? new ArrayList<>() : Collections.emptyList();
    private final Set<String> skippedTurnTelemetryEventRecords = SKIPPED_TURN_TELEMETRY_ENABLED
            ? new HashSet<>() : Collections.emptySet();
    private final List<PendingTeamMessage> pendingTeamMessages = new ArrayList<>();
    private final List<PendingSelfTeamMessage> pendingSelfTeamMessages = new ArrayList<>();
    private final List<MessageEvent> activeSelfTeamMessageEvents = new ArrayList<>();

    @SuppressWarnings("unused")
    public BotPeer(IBasicRobot robot, BotInfo botInfo) {
        this(robot, null, botInfo);
    }

    /**
     * Test seam: drives the peer against a supplied {@link IBot} instead of one that connects
     * to a server.
     * <p>
     * Almost everything this class does is route a robot's call to a Bot API call, and that
     * routing is only observable at the boundary between the two. Without a seam it can be
     * checked solely by running a battle, which reports a score minutes later and cannot say
     * which of eighty-odd call sites was wrong.
     * <p>
     * The field was already an interface, so nothing about the production path changes: the
     * public constructor still builds its own {@link BotImpl}.
     */
    BotPeer(IBasicRobot robot, IBot bot) {
        this(robot, bot, null);
    }

    private BotPeer(IBasicRobot robot, IBot suppliedBot, BotInfo botInfo) {
        log("BotPeer");

        this.robot = robot;
        bot = suppliedBot != null ? suppliedBot : createBotImpl(robot, botInfo);

        robot.setOut(System.out); // Redirect output to "our" System.out, which Tank Royale is overriding

        resolveEventListeners();

        init();
    }

    private void resolveEventListeners() {
        basicEvents = robot.getBasicEventListener();

        if (robot instanceof IAdvancedRobot) {
            advancedEvents = ((IAdvancedRobot) robot).getAdvancedEventListener();
        } else {
            advancedEvents = new AdvancedEventAdaptor();
        }
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
        setEventPriority(robocode.MessageEvent.class.getSimpleName(), MESSAGE_EVENT_PRIORITY);
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
        try {
            var name = TankRoyaleBotNameResolver.getName(bot, bot.getMyId());
            if (name != null) return name;
        } catch (BotException ignored) {
            // Before game setup the Bot API may not yet know this bot's id; classic's local
            // robot name is still available and is the correct fallback at that point.
        }
        return RobotName.getName();
    }

    @Override
    public long getTime() {
        log("getTime()");
        if (initialStatusCallbackActive) return 0;
        return deliveredTurn >= 0 ? deliveredTurn : bot.getTurnNumber();
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
        var teammates = bot.getTeammateIds();
        if (teammates == null || teammates.isEmpty()) return bot.getEnemyCount();
        if (initialOtherCount < 0) {
            initialOtherCount = bot.getEnemyCount() + teammates.size();
        }
        return Math.max(0, initialOtherCount - deadOtherBots.size());
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
        flushTeamMessages();
        bot.go();
        dispatchPendingSelfTeamMessages(deliveredTurn);
    }

    private void flushTeamMessages() {
        List<PendingTeamMessage> pending;
        synchronized (pendingTeamMessages) {
            if (pendingTeamMessages.isEmpty()) return;
            int messageCount = Math.min(
                    pendingTeamMessages.size(), Constants.MAX_LOGICAL_TEAM_MESSAGES_PER_TURN);
            pending = new ArrayList<>(pendingTeamMessages.subList(0, messageCount));
            pendingTeamMessages.subList(0, messageCount).clear();
        }
        if (pending.size() == 1) {
            PendingTeamMessage message = pending.get(0);
            if (message.recipientId == null) {
                bot.broadcastTeamMessage(message.transportMessage);
            } else {
                bot.sendTeamMessage(message.recipientId, message.transportMessage);
            }
            return;
        }
        bot.broadcastTeamMessageBatch(pending.stream()
                .map(message -> message.batchMessage)
                .collect(Collectors.toList()));
    }

    void dispatchStatusEvent(TickEvent tickEvent) {
        log("-> onStatus");

        var teammates = bot.getTeammateIds();
        if (teammates != null && !teammates.isEmpty()) {
            if (initialOtherCount < 0) {
                initialOtherCount = tickEvent.getBotState().getEnemyCount() + teammates.size();
            }
            for (var event : tickEvent.getEvents()) {
                if (event instanceof BotDeathEvent) {
                    var victimId = ((BotDeathEvent) event).getVictimId();
                    if (victimId != bot.getMyId()) deadOtherBots.add(victimId);
                }
            }
        }

        // Save robot status snapshot for event handlers needing robot status
        RobotStatus robotStatus = IBotToRobotStatusMapper.map(bot, tickEvent.getTurnNumber());
        currentRobotStatus.set(robotStatus);

        // Update fired bullets
        firedBullets.forEach(bulletPeer -> {
            Optional<BulletState> bulletStateOpt = tickEvent.getBulletStates().stream().filter(
                    bulletState -> bulletPeer.getBulletId() == bulletState.getBulletId()).findFirst();

            bulletStateOpt.ifPresent(bulletState ->
                    bulletPeer.setPosition(bulletState.getX(), bulletState.getY()));
        });

        // Classic has already delivered the start status before run(). Tank Royale retains the
        // corresponding first TickEvent until execute(), so consume its duplicate callback here.
        if (initialStatusDispatched && tickEvent.getTurnNumber() == 1) {
            return;
        }

        deliveredTurn = tickEvent.getTurnNumber();
        dispatchStatusCallback(robotStatus, false);
    }

    private void dispatchInitialStatusEvent() {
        if (initialStatusDispatched || bot.getTurnNumber() != 1) {
            return;
        }

        // The first Tank Royale tick supplies the complete state needed for Classic's start
        // StatusEvent. Deliver it before the robot's run() and mark it consumed before invoking
        // user code, which may inspect the pending event list or block on execute().
        initialStatusDispatched = true;
        deliveredTurn = bot.getTurnNumber();
        RobotStatus robotStatus = IBotToRobotStatusMapper.map(bot, 0);
        currentRobotStatus.set(robotStatus);
        dispatchStatusCallback(robotStatus, true);
    }

    private void dispatchStatusCallback(RobotStatus robotStatus, boolean initial) {
        var robocodeEvent = StatusEventMapper.map(robotStatus);
        boolean previousInitialStatusCallback = initialStatusCallbackActive;
        initialStatusCallbackActive = initial;
        try {
            dispatchRobotCallback(() -> basicEvents.onStatus(robocodeEvent));
        } finally {
            initialStatusCallbackActive = previousInitialStatusCallback;
        }
    }

    private void dispatchScannedRobotEvent(BotEvent botEvent) {
        dispatchPendingSelfTeamMessagesBefore(botEvent);
        log("-> onScannedRobot");
        var scannedBotEvent = (ScannedBotEvent) botEvent;
        if (shouldSuppressScannedEvent(scannedBotEvent)) {
            return;
        }
        var scannedRobotEvent = ScannedRobotEventMapper.map(scannedBotEvent, bot);
        dispatchRobotCallback(() -> basicEvents.onScannedRobot(scannedRobotEvent));
    }

    /**
     * Classic expires lower-priority scan events while a higher-priority wall handler is
     * blocked on a radar turn. The Bot API queue can retain those events until the handler
     * returns, so the bridge must discard only events from that blocked interval rather than
     * allowing them to cross the classic API boundary afterwards.
     */
    private boolean shouldSuppressScannedEvent(ScannedBotEvent scannedBotEvent) {
        if (hitWallHandlerActive && hitWallHandlerBlocked && suppressScansForBlockedWallHandler) {
            return true;
        }
        int throughTurn = suppressScansThroughTurn;
        if (throughTurn < 0) {
            return false;
        }
        if (scannedBotEvent.getTurnNumber() <= throughTurn) {
            return true;
        }
        suppressScansThroughTurn = -1;
        return false;
    }

    private void dispatchBulletMissedEvent(BotEvent botEvent) {
        dispatchPendingSelfTeamMessagesBefore(botEvent);
        log("-> onBulletMissed");
        var bulletHitWallEvent = (BulletHitWallEvent) botEvent;
        Bullet bullet = BulletMapper.map(bulletHitWallEvent.getBullet(), null, bot);
        var robocodeEvent = new robocode.BulletMissedEvent(bullet);
        robocodeEvent.setTime(bulletHitWallEvent.getTurnNumber());
        dispatchRobotCallback(() -> basicEvents.onBulletMissed(robocodeEvent));
    }

    private void dispatchBulletHitEvent(BotEvent botEvent) {
        dispatchPendingSelfTeamMessagesBefore(botEvent);
        log("-> onBulletHit");
        var bulletHitBotEvent = (BulletHitBotEvent) botEvent;
        var bulletState = bulletHitBotEvent.getBullet();
        var victimName = TankRoyaleBotNameResolver.getNameOrId(bot, bulletHitBotEvent.getVictimId());
        var bullet = BulletMapper.map(bulletState, victimName, bot);

        var robocodeEvent = new robocode.BulletHitEvent(victimName, bulletHitBotEvent.getEnergy(), bullet);
        robocodeEvent.setTime(bulletHitBotEvent.getTurnNumber());
        dispatchRobotCallback(() -> basicEvents.onBulletHit(robocodeEvent));
    }

    private void dispatchHitByBulletEvent(BotEvent botEvent) {
        dispatchPendingSelfTeamMessagesBefore(botEvent);
        log("-> onHitByBullet");
        var hitByBulletEvent = (HitByBulletEvent) botEvent;
        BulletState bullet = hitByBulletEvent.getBullet();
        double bearing = toRobocodeBearingRad(bot.bearingTo(bullet.getX(), bullet.getY()));
        var robocodeEvent = new robocode.HitByBulletEvent(
                bearing, map(bullet, TankRoyaleBotNameResolver.getNameOrId(bot, bot.getMyId()), bot));
        robocodeEvent.setTime(hitByBulletEvent.getTurnNumber());
        dispatchRobotCallback(() -> basicEvents.onHitByBullet(robocodeEvent));
    }

    private void dispatchHitWallEvent(BotEvent botEvent) {
        dispatchPendingSelfTeamMessagesBefore(botEvent);
        log("-> onHitWall");
        hitWallHandlerActive = true;
        hitWallHandlerBlocked = false;
        suppressScansForBlockedWallHandler = scansHaveLowerPriorityThanWall();
        try {
            var robocodeEvent = new robocode.HitWallEvent(calcBearingToWallRadians(bot.getDirection()));
            dispatchRobotCallback(() -> basicEvents.onHitWall(robocodeEvent));
        } finally {
            if (hitWallHandlerBlocked && suppressScansForBlockedWallHandler) {
                suppressScansThroughTurn = bot.getTurnNumber();
            }
            hitWallHandlerActive = false;
            hitWallHandlerBlocked = false;
            suppressScansForBlockedWallHandler = false;
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private boolean scansHaveLowerPriorityThanWall() {
        return bot.getEventPriority((Class) ScannedBotEvent.class)
                < bot.getEventPriority((Class) dev.robocode.tankroyale.botapi.events.HitWallEvent.class);
    }

    private void dispatchHitRobotEvent(BotEvent botEvent) {
        dispatchPendingSelfTeamMessagesBefore(botEvent);
        log("-> onHitRobot");

        var hitBotEvent = (HitBotEvent) botEvent;

        double bearing = toRobocodeBearingRad(bot.bearingTo(hitBotEvent.getX(), hitBotEvent.getY()));
        var robocodeEvent = new robocode.HitRobotEvent(
                TankRoyaleBotNameResolver.getNameOrId(bot, hitBotEvent.getVictimId()), bearing, hitBotEvent.getEnergy(), hitBotEvent.isRammed()
        );
        dispatchRobotCallback(() -> basicEvents.onHitRobot(robocodeEvent));
    }

    private void dispatchRobotDeathEvent(BotDeathEvent botDeathEvent) {
        dispatchPendingSelfTeamMessagesBefore(botDeathEvent);
        log("-> onRobotDeath");
        if (botDeathEvent.getVictimId() != bot.getMyId()) {
            deadOtherBots.add(botDeathEvent.getVictimId());
        }
        var robocodeEvent = new robocode.RobotDeathEvent(TankRoyaleBotNameResolver.getNameOrId(bot, botDeathEvent.getVictimId()));
        dispatchRobotCallback(() -> basicEvents.onRobotDeath(robocodeEvent));
    }

    private void dispatchSkippedTurnEvent(BotEvent botEvent) {
        log("-> onSkippedTurn");
        var skippedTurnEvent = (SkippedTurnEvent) botEvent;
        if (SKIPPED_TURN_TELEMETRY_ENABLED) {
            String record = "BRIDGE_SKIPPED_TURN botId=" + bot.getMyId()
                    + " round=" + bot.getRoundNumber()
                    + " turn=" + skippedTurnEvent.getTurnNumber();
            if (skippedTurnTelemetryEventRecords.add(record)) {
                skippedTurnTelemetryRecords.add(record);
            }
        }
        var robocodeEvent = new robocode.SkippedTurnEvent(skippedTurnEvent.getTurnNumber());
        dispatchRobotCallback(() -> advancedEvents.onSkippedTurn(robocodeEvent));
    }

    private void flushSkippedTurnTelemetry() {
        if (SKIPPED_TURN_TELEMETRY_ENABLED) {
            for (String record : skippedTurnTelemetryRecords) {
                System.out.println(record);
            }
            System.out.println("BRIDGE_SKIPPED_TURN_TELEMETRY_COMPLETE botId=" + bot.getMyId()
                    + " eventCount=" + skippedTurnTelemetryEventRecords.size());
            skippedTurnTelemetryRecords.clear();
            skippedTurnTelemetryEventRecords.clear();
        }
    }

    private void dispatchDeathEvent(BotEvent botEvent) {
        dispatchPendingSelfTeamMessagesBefore(botEvent);
        log("-> onDeath");
        var robocodeEvent = new robocode.DeathEvent();
        dispatchRobotCallback(() -> basicEvents.onDeath(robocodeEvent));
    }

    private void dispatchWinEvent() {
        log("-> onWin");
        var robocodeEvent = new robocode.WinEvent();
        dispatchRobotCallback(() -> basicEvents.onWin(robocodeEvent));
    }

    private void dispatchBulletHitBulletEvent(BotEvent botEvent) {
        dispatchPendingSelfTeamMessagesBefore(botEvent);
        log("-> onBulletHitBullet");
        var bulletHitBulletEvent = (BulletHitBulletEvent) botEvent;
        Bullet bullet = BulletMapper.map(bulletHitBulletEvent.getBullet(), null, bot);
        Bullet hitBullet = BulletMapper.map(bulletHitBulletEvent.getHitBullet(), null, bot);

        var robocodeEvent = new robocode.BulletHitBulletEvent(bullet, hitBullet);
        robocodeEvent.setTime(bulletHitBulletEvent.getTurnNumber());
        dispatchRobotCallback(() -> basicEvents.onBulletHitBullet(robocodeEvent));
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
            var robocodeEvent = new robocode.CustomEvent(condition);
            dispatchRobotCallback(() -> advancedEvents.onCustomEvent(robocodeEvent));
        }
    }

    /**
     * Classic Robocode reports exceptions thrown by robot event handlers to the battle while
     * allowing event processing to continue. Tank Royale's event dispatcher swallows those
     * exceptions, so this boundary restores the observable classic behavior for the bridge.
     */
    private void dispatchRobotCallback(Runnable callback) {
        try {
            callback.run();
        } catch (Exception exception) {
            if (exception instanceof RuntimeException
                    && isLateCallbackWithoutCurrentTick((RuntimeException) exception)) {
                return;
            }
            exception.printStackTrace();
        }
    }

    /**
     * A callback that is already on the old bot thread can overlap the next round's cleanup.
     * The Bot API deliberately clears its current tick when that round starts, so a state read
     * in this stale callback reports "tick has not occurred yet". Classic stops the old robot
     * thread instead of exposing that internal cleanup exception to the battle. The callback's
     * effects can no longer affect the finished round; suppress only this exact stale-thread
     * signature and keep active-bot and bot-owned exceptions visible.
     */
    boolean isLateCallbackWithoutCurrentTick(RuntimeException exception) {
        return !bot.isRunning()
                && exception instanceof BotException
                && exception.getMessage() != null
                && exception.getMessage().contains("tick has not occurred yet");
    }

    private void dispatchMessageEvent(BotEvent botEvent) {
        dispatchPendingSelfTeamMessagesBefore(botEvent);
        log("-> onMessageReceived");
        if (!(robot instanceof ITeamEvents)) {
            return;
        }
        var robocodeEvents = MessageEventMapper.map((TeamMessageEvent) botEvent, bot);
        for (var robocodeEvent : robocodeEvents) {
            dispatchRobotCallback(() -> ((ITeamEvents) robot).onMessageReceived(robocodeEvent));
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void dispatchPendingSelfTeamMessagesBefore(BotEvent nextEvent) {
        if (bot.getEventPriority((Class) nextEvent.getClass()) <= MESSAGE_EVENT_PRIORITY) {
            dispatchPendingSelfTeamMessages(nextEvent.getTurnNumber());
        }
    }

    private void dispatchPendingSelfTeamMessages(long nextTurn) {
        if (!(robot instanceof ITeamEvents)) return;

        List<PendingSelfTeamMessage> ready = new ArrayList<>();
        synchronized (pendingSelfTeamMessages) {
            var iterator = pendingSelfTeamMessages.iterator();
            while (iterator.hasNext()) {
                var message = iterator.next();
                if (message.sentTurn < nextTurn) {
                    ready.add(message);
                    iterator.remove();
                }
            }
        }

        for (var message : ready) {
            var event = new MessageEvent(message.senderName, message.message);
            activeSelfTeamMessageEvents.add(event);
            try {
                dispatchRobotCallback(() -> ((ITeamEvents) robot).onMessageReceived(event));
            } finally {
                activeSelfTeamMessageEvents.remove(event);
            }
        }
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
        if (hitWallHandlerActive) {
            hitWallHandlerBlocked = true;
        }
        bot.turnRadarRight(toDegrees(radians));
    }

    @Override
    public Bullet fire(double power) {
        log("fire()");
        var bullet = setFire(power);
        execute();
        return bullet;
    }

    @Override
    public Bullet setFire(double power) {
        log("setFire()");
        if (rejectNaNFirepower(power)) {
            return null;
        }
        if (bot.setFire(power)) {
            return createAndAddBullet(power);
        }
        return null;
    }

    private boolean rejectNaNFirepower(double power) {
        if (Double.isNaN(power)) {
            System.out.println("SYSTEM: You cannot call fire(NaN)");
            return true;
        }
        return false;
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
        try {
            bot.setInterruptible(interruptible);
        } catch (NullPointerException ignore) {
            // Bot API 0.33.1 throws an NPE when there is no current event, i.e. when called outside
            // an event handler; classic Robocode just ignores the call in that situation
        }
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
        var botEvents = bot.getEvents();
        if (initialStatusDispatched) {
            // The start status is already delivered before run(), so it is no longer pending to
            // the legacy robot even though Tank Royale keeps its first TickEvent queued.
            botEvents = botEvents.stream()
                    .filter(event -> !(event instanceof TickEvent && event.getTurnNumber() == 1))
                    .collect(Collectors.toList());
        }
        var events = AllEventsMapper.map(botEvents, bot, currentRobotStatus.get());
        events.addAll(activeSelfTeamMessageEvents);
        return events;
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
        // Must be the same directory that getDataFile() resolves against, like in classic Robocode
        return RobotData.getDataDirectory();
    }

    @Override
    public File getDataFile(String filename) {
        log("getDataFile()");
        return RobotData.getDataFile(filename);
    }

    @Override
    public long getDataQuotaAvailable() {
        log("getDataQuotaAvailable()");
        return RobotData.getMaxQuota() - RobotData.getQuotaUsed();
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
        return (teammates != null)
                ? teammates.stream().map(id -> TankRoyaleBotNameResolver.getNameOrId(bot, id)).toArray(String[]::new)
                : null;
    }

    @Override
    public boolean isTeammate(String name) {
        log("isTeammate()");
        var id = parseBotId(name);
        if (id == null) id = findTeammateId(name);
        return id != null && bot.isTeammate(id);
    }

    @Override
    public void broadcastMessage(Serializable message) throws IOException {
        log("broadcastMessage()");
        synchronized (pendingTeamMessages) {
            Serializable transportMessage = BridgeTeamMessage.forTransport(message);
            pendingTeamMessages.add(new PendingTeamMessage(null, transportMessage, transportMessage));
        }
    }

    @Override
    public void sendMessage(String name, Serializable message) throws IOException {
        log("sendMessage()");
        String ownName = getName();
        if (robot instanceof ITeamEvents && name != null && name.equals(ownName)) {
            synchronized (pendingSelfTeamMessages) {
                pendingSelfTeamMessages.add(new PendingSelfTeamMessage(
                        bot.getTurnNumber(), ownName, message));
            }
            return;
        }
        var id = parseBotId(name);
        if (id == null) id = findTeammateId(name);
        if (id == null) {
            throw new BotException("sendMessage: Cannot find receiver of team message: " + name);
        }
        if (!bot.isTeammate(id)) {
            throw new BotException("sendMessage: Cannot find receiver of team message: " + name);
        }
        synchronized (pendingTeamMessages) {
            Serializable transportMessage = BridgeTeamMessage.forTransport(message);
            Serializable batchMessage = BridgeTeamMessage.forRecipient(id, message);
            pendingTeamMessages.add(new PendingTeamMessage(id, transportMessage, batchMessage));
        }
    }

    private static final class PendingTeamMessage {
        private final Integer recipientId;
        private final Serializable transportMessage;
        private final Serializable batchMessage;

        private PendingTeamMessage(Integer recipientId, Serializable transportMessage, Serializable batchMessage) {
            this.recipientId = recipientId;
            this.transportMessage = transportMessage;
            this.batchMessage = batchMessage;
        }
    }

    private static final class PendingSelfTeamMessage {
        private final int sentTurn;
        private final String senderName;
        private final Serializable message;

        private PendingSelfTeamMessage(int sentTurn, String senderName, Serializable message) {
            this.sentTurn = sentTurn;
            this.senderName = senderName;
            this.message = message;
        }
    }

    private Integer findTeammateId(String name) {
        if (name == null) return null;
        var teammateIds = bot.getTeammateIds();
        if (teammateIds == null) return null;
        return teammateIds.stream()
                .filter(id -> name.equals(TankRoyaleBotNameResolver.getNameOrId(bot, id)))
                .findFirst()
                .orElse(null);
    }

    private static Integer parseBotId(String name) {
        if (name == null) return null;
        try {
            return Integer.valueOf(name);
        } catch (NumberFormatException ignored) {
            return null;
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

        firedBullets.stream().filter(bullet -> bullet.getBulletId() == -1).forEach(bullet -> {
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

    /**
     * Tank Royale reads droid status from the connecting {@code Bot} instance's own type
     * ({@code WebSocketHandler}: {@code isDroid = baseBot instanceof Droid}), not from bot-info
     * or a handshake flag the wrapper can set independently. A classic robot declares itself a
     * droid by implementing {@link robocode.Droid} on the robot class itself, so the peer mirrors
     * that onto which {@code BotImpl} it constructs.
     */
    private IBot createBotImpl(IBasicRobot robot, BotInfo botInfo) {
        return isDroidRobot(robot) ? new DroidBotImpl(botInfo) : new BotImpl(botInfo);
    }

    /** Package-private test seam: the connecting-class decision without constructing a {@code Bot}. */
    static boolean isDroidRobot(IBasicRobot robot) {
        return robot instanceof robocode.Droid;
    }

    private class BotImpl extends Bot implements BulletMapper.Resolver, IBotToRobotStatusMapper.OthersResolver {

        @Override
        public int getClassicOthers() {
            return BotPeer.this.getOthers();
        }

        @Override
        public Bullet resolveBullet(BulletState state, String victimName) {
            if (state.getOwnerId() != bot.getMyId()) return null;
            var bullet = mappedBullets.get(state.getBulletId());
            if (bullet == null) return null;
            bullet.updateState(state, victimName, false);
            firedBullets.remove(bullet);
            return bullet;
        }

        final AtomicInteger totalTurns = new AtomicInteger(0);

        BotImpl(BotInfo botInfo) {
            super(botInfo);
        }

        @Override
        public void run() { // Called by the Bot API on a new thread at the start of each round
            log("Bot.run()");

            stopThread = false;

            prepareRobotForRound();
            dispatchInitialStatusEvent();

            Runnable runnable = robot.getRobotRunnable();
            if (runnable != null) {
                runnable.run();
            }

            // Like in classic Robocode, a robot whose run() method returns must keep executing
            // turns so that its event handlers keep firing. Restarting run() instead would rerun
            // setup code endlessly, and without go() no intent is ever sent to the server, making
            // every turn last the full turn timeout with the robot unable to act.
            while (bot.isRunning() && !stopThread) {
                BotPeer.this.execute();
            }

            log("Bot.run() -> exit");
        }

        /**
         * Classic Robocode creates a fresh robot instance every round (only static fields
         * survive). Reusing the same instance makes robots with per-round instance state
         * misbehave from round 2 onwards, e.g. a `won` flag checked in the run() loop.
         * The instance created by the wrapper has not run yet, so it is used for the first
         * round; every following round gets a new instance.
         */
        private void prepareRobotForRound() {
            if (!firstRobotInstanceUsed) {
                firstRobotInstanceUsed = true;
                // Conditions registered before the battle started (e.g. in setPeer) were cleared
                // by the Bot API at round start; re-register them for the first round.
                conditions.values().forEach(bot::addCustomEvent);
                return;
            }
            try {
                IBasicRobot newRobot = robot.getClass().getDeclaredConstructor().newInstance();

                // Classic Robocode also drops all custom events between rounds; the new robot
                // instance re-registers its own conditions (via setPeer or run()). The Bot API
                // has already cleared its own conditions at round start.
                conditions.clear();

                robot = newRobot;
                robot.setOut(System.out);
                robot.setPeer(BotPeer.this);
                resolveEventListeners();
            } catch (Exception e) {
                System.err.println("Could not create a new robot instance for this round; reusing the previous instance: " + e);
            }
        }

        //---------------------------------------------------------------------
        // Bot API event handlers dispatched via the Bot API event queue, which
        // handles event priorities and interruptible events like classic Robocode
        //---------------------------------------------------------------------

        @Override
        public void onTick(TickEvent tickEvent) {
            dispatchStatusEvent(tickEvent);
        }

        @Override
        public void onScannedBot(ScannedBotEvent scannedBotEvent) {
            dispatchScannedRobotEvent(scannedBotEvent);
        }

        @Override
        public void onBulletHitWall(BulletHitWallEvent bulletHitWallEvent) {
            dispatchBulletMissedEvent(bulletHitWallEvent);
        }

        @Override
        public void onBulletHit(BulletHitBotEvent bulletHitBotEvent) {
            dispatchBulletHitEvent(bulletHitBotEvent);
        }

        @Override
        public void onHitByBullet(HitByBulletEvent hitByBulletEvent) {
            dispatchHitByBulletEvent(hitByBulletEvent);
        }

        @Override
        public void onHitWall(dev.robocode.tankroyale.botapi.events.HitWallEvent hitWallEvent) {
            dispatchHitWallEvent(hitWallEvent);
        }

        @Override
        public void onHitBot(HitBotEvent hitBotEvent) {
            dispatchHitRobotEvent(hitBotEvent);
        }

        @Override
        public void onDeath(dev.robocode.tankroyale.botapi.events.DeathEvent deathEvent) {
            dispatchDeathEvent(deathEvent);
        }

        @Override
        public void onBotDeath(BotDeathEvent botDeathEvent) {
            dispatchRobotDeathEvent(botDeathEvent);
        }

        @Override
        public void onSkippedTurn(SkippedTurnEvent skippedTurnEvent) {
            dispatchSkippedTurnEvent(skippedTurnEvent);
        }

        @Override
        public void onWonRound(WonRoundEvent wonRoundEvent) {
            dispatchWinEvent();
        }

        @Override
        public void onBulletHitBullet(BulletHitBulletEvent bulletHitBulletEvent) {
            dispatchBulletHitBulletEvent(bulletHitBulletEvent);
        }

        @Override
        public void onCustomEvent(CustomEvent customEvent) {
            dispatchCustomEvent(customEvent);
        }

        @Override
        public void onTeamMessage(TeamMessageEvent teamMessageEvent) {
            dispatchMessageEvent(teamMessageEvent);
        }

        @Override
        public void onGameStarted(GameStartedEvent gameStatedEvent) {
            totalTurns.set(0);
            if (SKIPPED_TURN_TELEMETRY_ENABLED) {
                skippedTurnTelemetryRecords.add(
                        "BRIDGE_SKIPPED_TURN_TELEMETRY_READY botId=" + bot.getMyId());
            }
            // Legacy robots may read battlefield dimensions from setPeer(). The Bot API fills
            // GameSetup immediately before publishing this callback, so attach the peer here
            // instead of before the connection has received game setup.
            robot.setPeer(BotPeer.this);
        }

        @Override
        public void onGameEnded(GameEndedEvent gameEndedEvent) {
            flushSkippedTurnTelemetry();
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
            // Tank Royale publishes RoundEnded to bot handlers before its internal handler has
            // stopped and joined the previous bot thread. Close any abandoned streams here,
            // after that stop has completed and before the next round's bot thread starts.
            RobotData.closeOpenStreams();
            initialStatusDispatched = false;
            suppressScansThroughTurn = -1;
            synchronized (pendingSelfTeamMessages) {
                pendingSelfTeamMessages.clear();
            }
            activeSelfTeamMessageEvents.clear();
            firedBullets.clear();
            mappedBullets.clear();
            initialOtherCount = -1;
            deadOtherBots.clear();
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
            bullet.updateState(bulletState, null, true);
            mappedBullets.put(bulletState.getBulletId(), bullet);
        }
    }

    /**
     * Identical to {@link BotImpl}; the {@code dev.robocode.tankroyale.botapi.Droid} marker is
     * all {@code WebSocketHandler} checks.
     */
    private class DroidBotImpl extends BotImpl implements dev.robocode.tankroyale.botapi.Droid {
        DroidBotImpl(BotInfo botInfo) {
            super(botInfo);
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
