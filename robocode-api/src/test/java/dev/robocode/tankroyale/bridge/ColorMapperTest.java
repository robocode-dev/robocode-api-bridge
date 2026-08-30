package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.graphics.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Acceptance evidence for API-002 — colours convert between Robocode's {@code java.awt.Color}
 * and Tank Royale's colour representation.
 * <p>
 * The negative direction is the whole point here. A robot that never sets a colour must stay
 * without one, because Tank Royale treats an absent colour differently from a black one, and
 * a mapper that defaults instead of preserving absence changes how the robot looks without
 * changing anything a battle would report.
 */
class ColorMapperTest {

    @Test
    @DisplayName("API-002 positive: RGB components survive the conversion")
    void testAPI002_UnitPositive_PreservesRgbComponents() {
        Color mapped = ColorMapper.map(new java.awt.Color(18, 52, 86));

        assertEquals(18, mapped.getR());
        assertEquals(52, mapped.getG());
        assertEquals(86, mapped.getB());
    }

    @Test
    @DisplayName("API-002 positive: the channel extremes are not clipped or reordered")
    void testAPI002_UnitPositive_PreservesChannelExtremesInOrder() {
        Color black = ColorMapper.map(java.awt.Color.BLACK);
        assertEquals(0, black.getR());
        assertEquals(0, black.getG());
        assertEquals(0, black.getB());

        Color white = ColorMapper.map(java.awt.Color.WHITE);
        assertEquals(255, white.getR());
        assertEquals(255, white.getG());
        assertEquals(255, white.getB());

        // A channel-order mistake is invisible on greys, so check one asymmetric colour.
        Color red = ColorMapper.map(java.awt.Color.RED);
        assertEquals(255, red.getR());
        assertEquals(0, red.getG());
        assertEquals(0, red.getB());
    }

    @Test
    @DisplayName("API-002 negative: an absent colour stays absent rather than becoming a default")
    void testAPI002_UnitNegative_DoesNotSubstituteADefaultForAnAbsentColour() {
        assertNull(ColorMapper.map(null),
                "a robot that set no colour must not acquire one through the mapper");
    }

    @Test
    @DisplayName("API-002 negative: alpha is dropped rather than bleeding into a colour channel")
    void testAPI002_UnitNegative_DoesNotLetAlphaAffectTheColourChannels() {
        java.awt.Color opaque = new java.awt.Color(10, 20, 30, 255);
        java.awt.Color transparent = new java.awt.Color(10, 20, 30, 0);

        Color fromOpaque = ColorMapper.map(opaque);
        Color fromTransparent = ColorMapper.map(transparent);

        // Tank Royale's colour carries no alpha, so both must map identically. A mapper that
        // folded alpha into a channel would differ here and nowhere else.
        assertEquals(fromOpaque.getR(), fromTransparent.getR());
        assertEquals(fromOpaque.getG(), fromTransparent.getG());
        assertEquals(fromOpaque.getB(), fromTransparent.getB());
    }
}
