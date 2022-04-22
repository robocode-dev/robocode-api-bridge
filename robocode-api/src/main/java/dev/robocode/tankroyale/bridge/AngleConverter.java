package dev.robocode.tankroyale.bridge;

public final class AngleConverter {

    public static double toRcRadians(double realDeg) {
        return Math.toRadians(normalizeAbsoluteAngle(90.0 - realDeg));
    }

    private static double normalizeAbsoluteAngle(double angle) {
        return (angle %= 360) >= 0 ? angle : (angle + 360);
    }
}
