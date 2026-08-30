package dev.robocode.tankroyale.bridge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import robocode.RobotStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Acceptance evidence for ROUTE-012 — the two paths a robot can read its own state through
 * report the same thing.
 *
 * A robot can ask the peer directly ({@code getTurnRemainingRadians()}) or read the status it
 * was handed in {@code onStatus}. Those are different code paths over the same Bot API state,
 * and nothing compared them until they disagreed.
 *
 * That is how {@code AN-007} survived: both paths had unit tests, both passed, and neither
 * test looked at the other path. A test written from an implementation's behaviour confirms
 * that implementation — so the guard that matters here is not another test of either path,
 * but a test that they agree.
 *
 * This class exists to fail when they drift apart again, whatever the cause.
 */
class StatusAndPeerAgreementTest {

    private static final double EPSILON = 1e-9;

    private RecordingBot bot;
    private BotPeer peer;

    @BeforeEach
    void setUp() {
        // Distinct values per field, so an agreement that holds only because two fields
        // happen to share a number is not mistaken for agreement.
        bot = RecordingBot.create()
                .returning("getEnergy", 71.5)
                .returning("getX", 133.25)
                .returning("getY", 466.75)
                .returning("getSpeed", 5.5)
                .returning("getGunHeat", 2.25)
                .returning("getDistanceRemaining", 48.0)
                .returning("getDirection", 12.0)
                .returning("getGunDirection", 34.0)
                .returning("getRadarDirection", 56.0)
                .returning("getTurnRemaining", 21.0)
                .returning("getGunTurnRemaining", 43.0)
                .returning("getRadarTurnRemaining", 65.0)
                .returning("getEnemyCount", 5)
                .returning("getTurnNumber", 97)
                .returning("getRoundNumber", 8)
                .returning("getNumberOfRounds", 13);
        peer = new BotPeer(new StubRobot(), bot.asBot());
    }

    @Test
    @DisplayName("ROUTE-012 positive: position, energy and speed agree across both paths")
    void testROUTE012_UnitPositive_ScalarStateAgreesAcrossBothPaths() {
        RobotStatus status = IBotToRobotStatusMapper.map(bot.asBot());

        assertEquals(peer.getEnergy(), status.getEnergy(), EPSILON, "energy");
        assertEquals(peer.getX(), status.getX(), EPSILON, "x");
        assertEquals(peer.getY(), status.getY(), EPSILON, "y");
        assertEquals(peer.getVelocity(), status.getVelocity(), EPSILON, "velocity");
        assertEquals(peer.getGunHeat(), status.getGunHeat(), EPSILON, "gun heat");
        assertEquals(peer.getDistanceRemaining(), status.getDistanceRemaining(), EPSILON,
                "distance remaining");
        assertEquals(peer.getOthers(), status.getOthers(), "others");
    }

    @Test
    @DisplayName("ROUTE-012 positive: time, round and sentry state agree across both paths")
    void testROUTE012_UnitPositive_TimeRoundAndSentryStateAgreesAcrossBothPaths() {
        RobotStatus status = IBotToRobotStatusMapper.map(bot.asBot());

        assertEquals(peer.getTime(), status.getTime(), "time");
        assertEquals(peer.getRoundNum(), status.getRoundNum(), "round number");
        assertEquals(peer.getNumRounds(), status.getNumRounds(), "number of rounds");
        assertEquals(peer.getNumSentries(), status.getNumSentries(), "number of sentries");
    }

    @Test
    @DisplayName("ROUTE-012 positive: body, gun and radar headings agree across both paths")
    void testROUTE012_UnitPositive_HeadingsAgreeAcrossBothPaths() {
        RobotStatus status = IBotToRobotStatusMapper.map(bot.asBot());

        assertEquals(peer.getBodyHeading(), status.getHeadingRadians(), EPSILON, "body heading");
        assertEquals(peer.getGunHeading(), status.getGunHeadingRadians(), EPSILON, "gun heading");
        assertEquals(peer.getRadarHeading(), status.getRadarHeadingRadians(), EPSILON,
                "radar heading");
    }

    @Test
    @DisplayName("ROUTE-012 negative: remaining turns agree in sign as well as magnitude")
    void testROUTE012_UnitNegative_RemainingTurnsAgreeInSignNotJustMagnitude() {
        RobotStatus status = IBotToRobotStatusMapper.map(bot.asBot());

        // The exact defect AN-007 recorded. Comparing magnitudes would have passed while the
        // two paths reported opposite directions, so these compare signed values — and the
        // distinct per-field inputs mean a body/gun/radar swap fails here too.
        assertEquals(peer.getBodyTurnRemaining(), status.getTurnRemainingRadians(), EPSILON,
                "body turn remaining, signed");
        assertEquals(peer.getGunTurnRemaining(), status.getGunTurnRemainingRadians(), EPSILON,
                "gun turn remaining, signed");
        assertEquals(peer.getRadarTurnRemaining(), status.getRadarTurnRemainingRadians(), EPSILON,
                "radar turn remaining, signed");
    }

    @Test
    @DisplayName("ROUTE-012 negative: a right turn is positive on both paths, as classic reports it")
    void testROUTE012_UnitNegative_ARightTurnIsPositiveOnBothPaths() {
        // Tank Royale reports a negative remainder while turning right; Robocode reports a
        // positive one. Measured on both live engines: after setTurnRight(90), classic shows
        // +80 on both paths. Anything negative here means the handedness was dropped.
        RecordingBot turningRight = RecordingBot.create().returning("getTurnRemaining", -90.0);
        BotPeer rightPeer = new BotPeer(new StubRobot(), turningRight.asBot());

        RobotStatus status = IBotToRobotStatusMapper.map(turningRight.asBot());

        assertEquals(Math.toRadians(90), rightPeer.getBodyTurnRemaining(), EPSILON,
                "the peer must report a right turn as positive");
        assertEquals(Math.toRadians(90), status.getTurnRemainingRadians(), EPSILON,
                "and so must the status");
    }
}
