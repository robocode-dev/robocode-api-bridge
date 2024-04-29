package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.BotException;
import robocode.RobotStatus;
import robocode.StatusEvent;

import java.util.Map;

final class StatusEventMapper {

    public static StatusEvent map(Map<Long, RobotStatus> robotStatusSnapshots, long turnNumber) {
        RobotStatus robotStatus = robotStatusSnapshots.get(turnNumber);
        if (robotStatus == null) {
            throw new BotException("StatusEventMapper.map: Could not get robot status from map");
        }
        return map(robotStatus, turnNumber);
    }

    public static StatusEvent map(RobotStatus robotStatus, long turnNumber) {
        var event = new StatusEvent(robotStatus);
        event.setTime(turnNumber);
        return event;
    }
}
