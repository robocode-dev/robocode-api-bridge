package dev.robocode.tankroyale.bridge;

import static java.lang.Math.toRadians;
import static robocode.util.Utils.normalAbsoluteAngleDegrees;

final class AngleConverter {

    public static double toRobocodeHeadingRad(double deg) {
        return toRadians(normalAbsoluteAngleDegrees(90.0 - deg));
    }

    public static double toRobocodeBearingRad(double deg) {
        return toRadians(-deg);
    }
}