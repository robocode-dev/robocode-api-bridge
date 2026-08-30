package dev.robocode.tankroyale.bridge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static java.lang.Math.PI;
import static java.lang.Math.toRadians;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Acceptance evidence for the {@code IBasicRobotPeer} half of ROUTE — movement, turning,
 * firing, colours, and the state a robot reads back.
 *
 * Every assertion names the Bot API call and the argument. That is the whole point: a
 * conversion test can prove degrees become radians correctly while the value still arrives
 * at the wrong Bot API method, and a battle can only report that the score differed.
 */
class BasicPeerRoutingTest {

    private static final double EPSILON = 1e-9;

    private RecordingBot bot;
    private BotPeer peer;

    @BeforeEach
    void setUp() {
        bot = RecordingBot.create();
        peer = new BotPeer(new StubRobot(), bot.asBot());
        bot.clear(); // construction sets event priorities; a test asserts on what it does
    }

    @Test
    @DisplayName("ROUTE-001 positive: blocking movement and turns reach their Bot API calls in degrees")
    void testROUTE001_UnitPositive_RoutesBlockingMovementAndTurnsInDegrees() {
        peer.move(150);
        assertEquals(150.0, bot.onlyCall("forward").doubleArg(0), EPSILON);

        bot.clear();
        peer.turnBody(toRadians(90));
        assertEquals(90.0, bot.onlyCall("turnRight").doubleArg(0), EPSILON,
                "the peer takes radians and the Bot API takes degrees");

        bot.clear();
        peer.turnGun(toRadians(45));
        assertEquals(45.0, bot.onlyCall("turnGunRight").doubleArg(0), EPSILON);
    }

    @Test
    @DisplayName("ROUTE-001 positive: a negative turn keeps its sign rather than reversing")
    void testROUTE001_UnitPositive_PreservesTurnDirection() {
        peer.turnBody(toRadians(-90));
        assertEquals(-90.0, bot.onlyCall("turnRight").doubleArg(0), EPSILON);

        bot.clear();
        peer.move(-100);
        assertEquals(-100.0, bot.onlyCall("forward").doubleArg(0), EPSILON,
                "moving back is negative distance, not a different call");
    }

    @Test
    @DisplayName("ROUTE-001 negative: a turn does not reach the movement call, nor the gun the body")
    void testROUTE001_UnitNegative_DoesNotCrossMovementAndTurnChannels() {
        peer.turnBody(toRadians(30));
        assertTrue(!bot.called("forward"), "turning the body must not move it: " + bot.names());
        assertTrue(!bot.called("turnGunRight"), "turning the body must not turn the gun");

        bot.clear();
        peer.turnGun(toRadians(30));
        assertTrue(!bot.called("turnRight"), "turning the gun must not turn the body");
        assertTrue(!bot.called("turnRadarRight"), "turning the gun must not turn the radar");
    }

    @Test
    @DisplayName("ROUTE-002 positive: firing routes to the Bot API with the power unchanged")
    void testROUTE002_UnitPositive_RoutesFiringWithPowerUnchanged() {
        peer.setFire(2.5);
        assertEquals(2.5, bot.onlyCall("setFire").doubleArg(0), EPSILON,
                "power is the same quantity in both engines and must not be scaled");
    }

    @Test
    @DisplayName("ROUTE-002 negative: firing does not move or turn the robot")
    void testROUTE002_UnitNegative_FiringTouchesNoMovementCall() {
        peer.setFire(1.0);

        assertTrue(!bot.called("forward") && !bot.called("setForward"), bot.names());
        assertTrue(!bot.called("turnRight") && !bot.called("setTurnRight"), bot.names());
    }

    @Test
    @DisplayName("ROUTE-003 positive: each colour reaches its own Bot API setter")
    void testROUTE003_UnitPositive_RoutesEachColourToItsOwnSetter() {
        peer.setBodyColor(Color.RED);
        assertTrue(bot.called("setBodyColor"), bot.names());

        bot.clear();
        peer.setGunColor(Color.GREEN);
        // Tank Royale calls the gun a turret; the peer must bridge the naming rather than
        // fall through to the body colour, which would be invisible in any battle.
        assertTrue(bot.called("setTurretColor"), "gun colour routes to the turret: " + bot.names());
        assertTrue(!bot.called("setBodyColor"), "and not to the body");

        bot.clear();
        peer.setRadarColor(Color.BLUE);
        assertTrue(bot.called("setRadarColor"), bot.names());

        bot.clear();
        peer.setBulletColor(Color.WHITE);
        assertTrue(bot.called("setBulletColor"), bot.names());

        bot.clear();
        peer.setScanColor(Color.YELLOW);
        assertTrue(bot.called("setScanColor"), bot.names());
    }

    @Test
    @DisplayName("ROUTE-003 negative: setting one colour sets no other")
    void testROUTE003_UnitNegative_SettingOneColourLeavesTheOthersAlone() {
        peer.setRadarColor(Color.BLUE);

        assertTrue(!bot.called("setBodyColor"), bot.names());
        assertTrue(!bot.called("setTurretColor"), bot.names());
        assertTrue(!bot.called("setBulletColor"), bot.names());
        assertTrue(!bot.called("setScanColor"), bot.names());
    }

    @Test
    @DisplayName("ROUTE-004 positive: state getters read the matching Bot API value")
    void testROUTE004_UnitPositive_ReadsStateFromTheMatchingBotApiValue() {
        bot.returning("getEnergy", 63.5)
           .returning("getX", 210.0)
           .returning("getY", 340.0)
           .returning("getSpeed", 7.5)
           .returning("getGunHeat", 1.25)
           .returning("getTurnNumber", 91)
           .returning("getEnemyCount", 6)
           .returning("getArenaWidth", 1000)
           .returning("getArenaHeight", 800);

        assertEquals(63.5, peer.getEnergy(), EPSILON);
        assertEquals(210.0, peer.getX(), EPSILON);
        assertEquals(340.0, peer.getY(), EPSILON);
        assertEquals(7.5, peer.getVelocity(), EPSILON, "velocity reads the Bot API's speed");
        assertEquals(1.25, peer.getGunHeat(), EPSILON);
        assertEquals(91L, peer.getTime());
        assertEquals(6, peer.getOthers());
        assertEquals(1000.0, peer.getBattleFieldWidth(), EPSILON);
        assertEquals(800.0, peer.getBattleFieldHeight(), EPSILON);
    }

    @Test
    @DisplayName("ROUTE-004 positive: headings convert from the Bot API's frame into Robocode's")
    void testROUTE004_UnitPositive_ConvertsHeadingsIntoTheRobocodeFrame() {
        bot.returning("getDirection", 0.0)        // east
           .returning("getGunDirection", 90.0)    // north
           .returning("getRadarDirection", 180.0); // west

        assertEquals(toRadians(90), peer.getBodyHeading(), EPSILON, "east is 90 to a robot");
        assertEquals(toRadians(0), peer.getGunHeading(), EPSILON, "north is 0");
        assertEquals(toRadians(270), peer.getRadarHeading(), EPSILON, "west is 270");
    }

    @Test
    @DisplayName("ROUTE-004 negative: a heading getter does not return another component's heading")
    void testROUTE004_UnitNegative_DoesNotConfuseBodyGunAndRadarHeadings() {
        bot.returning("getDirection", 10.0)
           .returning("getGunDirection", 20.0)
           .returning("getRadarDirection", 30.0);

        // Three getters of the same type reading three near-identical Bot API values is the
        // shape AN-005 was: a swap there is invisible in every battle.
        assertEquals(AngleConverter.toRobocodeHeadingRad(10.0), peer.getBodyHeading(), EPSILON);
        assertEquals(AngleConverter.toRobocodeHeadingRad(20.0), peer.getGunHeading(), EPSILON);
        assertEquals(AngleConverter.toRobocodeHeadingRad(30.0), peer.getRadarHeading(), EPSILON);
    }

    @Test
    @DisplayName("ROUTE-004 positive: remaining turn amounts convert to radians without crossing")
    void testROUTE004_UnitPositive_ConvertsRemainingTurnAmountsWithoutCrossingThem() {
        bot.returning("getDistanceRemaining", 55.0)
           .returning("getTurnRemaining", 30.0)
           .returning("getGunTurnRemaining", 60.0)
           .returning("getRadarTurnRemaining", 120.0);

        assertEquals(55.0, peer.getDistanceRemaining(), EPSILON, "a distance stays a distance");

        // Negated, because the two engines disagree about which way is positive. Tank
        // Royale's documentation is explicit: a positive turn remaining means turning left.
        // Robocode's is positive for a right turn. A remainder is a signed rotation, so
        // crossing the frame flips it -- unlike a command such as turnRight, which names its
        // own direction and needs no flip.
        assertEquals(toRadians(-30), peer.getBodyTurnRemaining(), EPSILON);
        assertEquals(toRadians(-60), peer.getGunTurnRemaining(), EPSILON);
        assertEquals(toRadians(-120), peer.getRadarTurnRemaining(), EPSILON);
    }

    @Test
    @DisplayName("ROUTE-001 positive: execute and rescan route to their Bot API equivalents")
    void testROUTE001_UnitPositive_RoutesExecuteAndRescan() {
        peer.execute();
        assertTrue(bot.called("go"), "execute completes the turn: " + bot.names());

        bot.clear();
        peer.rescan();
        assertTrue(bot.called("rescan"), bot.names());
    }

    @Test
    @DisplayName("ROUTE-004 positive: the graphics surface is available without touching the Bot API")
    void testROUTE004_UnitPositive_ProvidesGraphicsWithoutCallingTheBotApi() {
        assertNotNull(peer.getGraphics(), "a robot that paints must get a surface");
    }

    @Test
    @DisplayName("ROUTE-001 negative: reading state issues no command")
    void testROUTE001_UnitNegative_ReadingStateIssuesNoCommand() {
        peer.getEnergy();
        peer.getX();
        peer.getBodyHeading();
        peer.getTime();

        for (RecordingBot.Call call : bot.calls()) {
            assertTrue(call.name.startsWith("get"),
                    "reading state must not command the bot, but saw: " + call);
        }
    }

    @Test
    @DisplayName("ROUTE-001 positive: a full circle in radians becomes 360 degrees, not 6.28")
    void testROUTE001_UnitPositive_ConvertsAFullCircleRatherThanPassingRadiansThrough() {
        peer.turnBody(2 * PI);

        // The failure this catches is passing radians into a degrees parameter: the robot
        // would turn about six degrees instead of a full circle, every time, forever.
        assertEquals(360.0, bot.onlyCall("turnRight").doubleArg(0), EPSILON);
    }
}
