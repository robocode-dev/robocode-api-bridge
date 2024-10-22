package dev.robocode.tankroyale.bridge;

import robocode.RobotStatus;
import robocode.StatusEvent;

final class StatusEventMapper {

    public static StatusEvent map(RobotStatus robotStatus) {
        var event = new StatusEvent(robotStatus);
        event.setTime(robotStatus.getTime());
        return event;
    }
}
