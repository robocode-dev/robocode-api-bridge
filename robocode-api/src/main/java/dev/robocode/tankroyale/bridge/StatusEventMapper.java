package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.TickEvent;
import robocode.RobotStatus;
import robocode.StatusEvent;

import java.util.Map;

final class StatusEventMapper {

    public static StatusEvent map(TickEvent tickEvent, Map<Integer, RobotStatus> robotStatusSnapshots) {
        if (tickEvent == null) return null;

        var robotStatus = robotStatusSnapshots.get(tickEvent.getTurnNumber());

        var event = new StatusEvent(robotStatus);
        event.setTime(tickEvent.getTurnNumber());
        return event;
    }
}
