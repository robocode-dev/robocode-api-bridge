package dev.robocode.tankroyale.bridge;

import static java.lang.Math.toRadians;

final class AngleConverter {

    public static double toRobocodeHeadingRad(double deg) {
        return toRadians(normalizeAbsoluteAngle(90.0 - deg));
    }

    public static double toRobocodeBearingRad(double deg) {
        return toRadians(-deg);
    }

    private static double normalizeAbsoluteAngle(double angle) {
        return (angle %= 360) >= 0 ? angle : (angle + 360);
    }
}