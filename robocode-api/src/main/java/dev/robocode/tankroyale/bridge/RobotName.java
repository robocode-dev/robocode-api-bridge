package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.BotException;

final class RobotName {

    private static String name;

    public static void setName(String name) {
        RobotName.name = name;
    }

    public static String getName() {
        if (name == null || name.trim().isEmpty()) {
            throw new BotException("getName: Robot name is missing");
        }
        return name;
    }
}
