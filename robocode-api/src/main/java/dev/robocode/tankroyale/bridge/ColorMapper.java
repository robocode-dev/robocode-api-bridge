package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.Color;

final class ColorMapper {

    public static Color map(java.awt.Color awtColor) {
        return awtColor == null ? null : new Color(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue());
    }
}
