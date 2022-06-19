package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.TickEvent;
import robocode.RobotStatus;
import robocode.StatusEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

final class StatusEventMapper {

    public static List<StatusEvent> map(List<TickEvent> tickEvents, Map<Integer, RobotStatus> robotStatusSnapshots) {
        if (tickEvents == null) return emptyList();

        List<StatusEvent> events = new ArrayList<>();
        tickEvents.forEach(event -> events.add(map(event, robotStatusSnapshots)));
        return events;
    }

    public static StatusEvent map(TickEvent tickEvent, Map<Integer, RobotStatus> robotStatusSnapshots) {
        if (tickEvent == null) return null;

        RobotStatus robotStatus = robotStatusSnapshots.get(tickEvent.getTurnNumber());
        return new StatusEvent(robotStatus);
    }
}
