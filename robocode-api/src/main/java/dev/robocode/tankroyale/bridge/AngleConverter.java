package dev.robocode.tankroyale.bridge;

import static java.lang.Math.toRadians;

final class AngleConverter {

    public static double toRcHeadingRad(double realDeg) {
        return toRadians(normalizeAbsoluteAngle(90.0 - realDeg));
    }

    public static double toRcBearingRad(double realDeg) {
        return toRadians(-realDeg);
    }

    private static double normalizeAbsoluteAngle(double angle) {
        return (angle %= 360) >= 0 ? angle : (angle + 360);
    }
}