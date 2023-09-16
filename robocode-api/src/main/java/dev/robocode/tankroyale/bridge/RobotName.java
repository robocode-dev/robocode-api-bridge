package dev.robocode.tankroyale.bridge;

final class RobotName {

    private static String name;

    public static void setName(String name) {
        RobotName.name = name;
    }

    public static String getName() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalStateException("Robot name is missing");
        }
        return name;
    }
}
