package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.BulletState;
import dev.robocode.tankroyale.botapi.graphics.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import robocode.Bullet;

import static java.lang.Math.toRadians;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Acceptance evidence for API-004 — bullets map to Robocode's {@link Bullet} with owner,
 * power, and heading preserved.
 * <p>
 * The mapper also converts the bullet's direction, so a heading error here would surface far
 * away: a robot that tracks its own bullets would mispredict where they went, and the only
 * symptom would be a slightly worse robot.
 */
class BulletMapperTest {

    private static final double EPSILON = 1e-9;

    private static BulletState bullet(int id, int ownerId, double power, double x, double y, double direction) {
        return new BulletState(id, ownerId, power, x, y, direction, Color.RED);
    }

    @Test
    @DisplayName("API-004 positive: power, position, and identity are preserved")
    void testAPI004_UnitPositive_PreservesPowerPositionAndIdentity() {
        Bullet mapped = BulletMapper.map(bullet(7, 42, 2.5, 120.0, 340.0, 0), "Target");

        assertEquals(2.5, mapped.getPower(), EPSILON);
        assertEquals(120.0, mapped.getX(), EPSILON);
        assertEquals(340.0, mapped.getY(), EPSILON);
        assertEquals("42", mapped.getName(), "the owner is carried across as its bot id");
        assertEquals("Target", mapped.getVictim());
    }

    @Test
    @DisplayName("API-004 positive: the bullet's direction is converted to a Robocode heading")
    void testAPI004_UnitPositive_ConvertsDirectionToARobocodeHeading() {
        // Travelling east in Tank Royale is a heading of 90 degrees in Robocode.
        assertEquals(toRadians(90), BulletMapper.map(bullet(1, 1, 1, 0, 0, 0), "V").getHeadingRadians(), EPSILON);
        // Travelling north.
        assertEquals(toRadians(0), BulletMapper.map(bullet(1, 1, 1, 0, 0, 90), "V").getHeadingRadians(), EPSILON);
        // The same conversion the robot's own heading uses, so the two cannot disagree.
        assertEquals(AngleConverter.toRobocodeHeadingRad(217),
                BulletMapper.map(bullet(1, 1, 1, 0, 0, 217), "V").getHeadingRadians(), EPSILON);
    }

    @Test
    @DisplayName("API-004 negative: a bullet with no victim still maps rather than failing")
    void testAPI004_UnitNegative_MapsABulletThatHasNotHitAnything() {
        Bullet mapped = BulletMapper.map(bullet(3, 9, 1.5, 10, 20, 45), null);

        assertNotNull(mapped, "a bullet in flight has no victim and must still map");
        assertNull(mapped.getVictim());
        assertEquals(1.5, mapped.getPower(), EPSILON);
    }

    @Test
    @DisplayName("API-004 negative: an out-of-range direction is normalised rather than carried through")
    void testAPI004_UnitNegative_NormalisesAnOutOfRangeDirection() {
        double wrapped = BulletMapper.map(bullet(1, 1, 1, 0, 0, 450), "V").getHeadingRadians();
        double plain = BulletMapper.map(bullet(1, 1, 1, 0, 0, 90), "V").getHeadingRadians();

        // 450 degrees is 90 degrees. A heading that escaped its range would break any robot
        // comparing bullet headings by subtraction.
        assertEquals(plain, wrapped, EPSILON);
    }
}
