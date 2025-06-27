package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.graphics.Color;

final class ColorMapper {

    public static Color map(java.awt.Color awtColor) {
        return awtColor == null ? null : Color.fromRgb(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue());
    }
}
