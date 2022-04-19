package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.Color;

final class ColorMapper {

    public static dev.robocode.tankroyale.botapi.Color map(java.awt.Color awtColor) {
        return new Color(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue());
    }
}
