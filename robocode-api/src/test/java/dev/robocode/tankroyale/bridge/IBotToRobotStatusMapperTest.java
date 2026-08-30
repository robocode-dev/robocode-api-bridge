package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import robocode.RobotStatus;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.toRadians;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Acceptance evidence for API-005 — a Tank Royale bot state maps to the status fields a
 * classic robot reads each turn.
 * <p>
 * {@code IBot} is a wide interface and only a handful of its methods matter here, so the test
 * drives the mapper through a proxy answering a small table of canned values. That keeps the
 * test to the conversion under examination rather than to a hand-written stub that would need
 * updating every time the Bot API grows a method.
 * <p>
 * Every field gets a distinct value for the same reason the results mapper's test does: this
 * is a long positional constructor call, and three consecutive headings are exactly the shape
 * where a swap goes unnoticed.
 */
class IBotToRobotStatusMapperTest {

    private static final double EPSILON = 1e-9;

    @Test
    @DisplayName("API-005 positive: scalar state carries across unchanged")
    void testAPI005_UnitPositive_CarriesScalarStateAcrossUnchanged() {
        RobotStatus status = IBotToRobotStatusMapper.map(bot(b -> {
            b.put("getEnergy", 87.5);
            b.put("getX", 123.25);
            b.put("getY", 456.75);
            b.put("getSpeed", 6.5);
            b.put("getDistanceRemaining", 42.0);
            b.put("getGunHeat", 1.75);
            b.put("getEnemyCount", 4);
            b.put("getTurnNumber", 317);
        }));

        assertEquals(87.5, status.getEnergy(), EPSILON);
        assertEquals(123.25, status.getX(), EPSILON);
        assertEquals(456.75, status.getY(), EPSILON);
        assertEquals(6.5, status.getVelocity(), EPSILON);
        assertEquals(42.0, status.getDistanceRemaining(), EPSILON);
        assertEquals(1.75, status.getGunHeat(), EPSILON);
        assertEquals(4, status.getOthers());
    }

    @Test
    @DisplayName("API-005 positive: body, gun, and radar headings convert without being interchanged")
    void testAPI005_UnitPositive_ConvertsEachHeadingWithoutInterchangingThem() {
        RobotStatus status = IBotToRobotStatusMapper.map(bot(b -> {
            b.put("getDirection", 0.0);    // east
            b.put("getGunDirection", 90.0);  // north
            b.put("getRadarDirection", 180.0); // west
        }));

        assertEquals(toRadians(90), status.getHeadingRadians(), EPSILON, "body faces east");
        assertEquals(toRadians(0), status.getGunHeadingRadians(), EPSILON, "gun faces north");
        assertEquals(toRadians(270), status.getRadarHeadingRadians(), EPSILON, "radar faces west");
    }

    @Test
    @DisplayName("API-005 positive: remaining turn amounts convert from degrees to radians")
    void testAPI005_UnitPositive_ConvertsRemainingTurnAmountsToRadians() {
        RobotStatus status = IBotToRobotStatusMapper.map(bot(b -> {
            b.put("getTurnRemaining", 30.0);
            b.put("getGunTurnRemaining", 60.0);
            b.put("getRadarTurnRemaining", 120.0);
        }));

        // These are signed amounts still to turn, not absolute directions, so they convert by
        // unit alone. Passing one through the heading conversion would flip its sign.
        assertEquals(toRadians(30), status.getTurnRemainingRadians(), EPSILON);
        assertEquals(toRadians(60), status.getGunTurnRemainingRadians(), EPSILON);
        assertEquals(toRadians(120), status.getRadarTurnRemainingRadians(), EPSILON);
    }

    @Test
    @DisplayName("API-005 negative: round numbering is rebased, and sentries stay absent")
    void testAPI005_UnitNegative_RebasesRoundNumberAndReportsNoSentries() {
        RobotStatus status = IBotToRobotStatusMapper.map(bot(b -> b.put("getRoundNumber", 1)));

        // Tank Royale counts rounds from one and classic from zero. Carrying the number
        // through unchanged would put every robot's round bookkeeping off by one.
        assertEquals(0, status.getRoundNum(), "the first round is round zero to a classic robot");

        // Border sentries are a classic concept Tank Royale has no equivalent for. Reporting
        // none is honest; inventing a count would be worse than the field being unavailable.
        assertEquals(0, status.getNumSentries());
    }

    @Test
    @DisplayName("API-005 negative: a negative remaining turn keeps its sign")
    void testAPI005_UnitNegative_KeepsTheSignOfANegativeRemainingTurn() {
        RobotStatus status = IBotToRobotStatusMapper.map(bot(b -> b.put("getTurnRemaining", -45.0)));

        // The sign is the direction of the turn. Losing it would make a robot turn the wrong
        // way, and only when it happened to be turning the other way.
        assertEquals(toRadians(-45), status.getTurnRemainingRadians(), EPSILON);
    }

    /** An IBot answering a small table of canned values; anything unasked-for returns a zero. */
    private static IBot bot(java.util.function.Consumer<Map<String, Object>> setup) {
        Map<String, Object> values = new HashMap<>();
        setup.accept(values);
        return (IBot) Proxy.newProxyInstance(
                IBot.class.getClassLoader(),
                new Class<?>[] { IBot.class },
                (proxy, method, args) -> {
                    Object value = values.get(method.getName());
                    if (value != null) {
                        return value;
                    }
                    Class<?> type = method.getReturnType();
                    if (type == double.class) return 0.0;
                    if (type == int.class) return 0;
                    if (type == boolean.class) return false;
                    return null;
                });
    }
}
