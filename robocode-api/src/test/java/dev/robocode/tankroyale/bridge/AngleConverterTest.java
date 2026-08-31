package dev.robocode.tankroyale.bridge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.lang.Math.PI;
import static java.lang.Math.toRadians;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Acceptance evidence for API-001 — angles convert from the Tank Royale convention to the
 * Robocode convention (single-direction).
 * <p>
 * The two engines disagree about where zero is and which way is positive. Tank Royale
 * measures direction counter-clockwise from east; Robocode measures clockwise from north.
 * A conversion that is right in the middle of the range and wrong at the wrap is the
 * failure this class is written to catch, which is why the negative direction concentrates
 * on the discontinuity rather than on rejected input.
 * <p>
 * There is no reverse conversion to test: outbound turn commands are relative rotations in
 * the same winding sense on both engines, so nothing in the bridge ever converts a
 * Robocode-convention angle back to Tank Royale's.
 */
class AngleConverterTest {

    private static final double EPSILON = 1e-9;

    @Test
    @DisplayName("API-001 positive: a Tank Royale direction becomes the same direction as a Robocode heading")
    void testAPI001_UnitPositive_ConvertsCardinalDirectionsToRobocodeHeadings() {
        // East in Tank Royale is 90 degrees clockwise from north in Robocode.
        assertEquals(toRadians(90), AngleConverter.toRobocodeHeadingRad(0), EPSILON, "east");
        // North is zero in Robocode.
        assertEquals(toRadians(0), AngleConverter.toRobocodeHeadingRad(90), EPSILON, "north");
        // West.
        assertEquals(toRadians(270), AngleConverter.toRobocodeHeadingRad(180), EPSILON, "west");
        // South.
        assertEquals(toRadians(180), AngleConverter.toRobocodeHeadingRad(270), EPSILON, "south");
    }

    @Test
    @DisplayName("API-001 positive: a bearing negates, because the two engines wind opposite ways")
    void testAPI001_UnitPositive_ConvertsBearingsByReversingTheWindingDirection() {
        assertEquals(toRadians(-45), AngleConverter.toRobocodeBearingRad(45), EPSILON);
        assertEquals(toRadians(45), AngleConverter.toRobocodeBearingRad(-45), EPSILON);
        assertEquals(0.0, AngleConverter.toRobocodeBearingRad(0), EPSILON);
    }

    @Test
    @DisplayName("API-001 negative: the wrap is normalised rather than producing a reflected angle")
    void testAPI001_UnitNegative_DoesNotReflectAnglesAcrossTheDiscontinuity() {
        // 91 degrees in Tank Royale is just past north, so the Robocode heading must wrap
        // to just short of a full turn -- not to +1 degree, which is what a conversion that
        // forgets to normalise produces.
        double justPastNorth = AngleConverter.toRobocodeHeadingRad(91);
        assertEquals(toRadians(359), justPastNorth, EPSILON);

        // Every heading stays inside one positive turn, whatever it is handed.
        for (double deg : new double[] { -360, -180, -1, 0, 359, 360, 450, 720, 1080 }) {
            double heading = AngleConverter.toRobocodeHeadingRad(deg);
            assertTrue(heading >= 0 && heading < 2 * PI,
                    "heading for " + deg + " degrees escaped [0, 2pi): " + heading);
        }
    }

    @Test
    @DisplayName("API-001 negative: an out-of-range input converts to the same heading as its normalised form")
    void testAPI001_UnitNegative_TreatsCoterminalInputsAsEqual() {
        assertEquals(AngleConverter.toRobocodeHeadingRad(45),
                AngleConverter.toRobocodeHeadingRad(45 + 360), EPSILON);
        assertEquals(AngleConverter.toRobocodeHeadingRad(45),
                AngleConverter.toRobocodeHeadingRad(45 - 360), EPSILON);
    }
}
