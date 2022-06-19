package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.TickEvent;
import robocode.RobotStatus;
import robocode.StatusEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

final class TickToRobotStatusEventMapper {

    public static List<robocode.StatusEvent> map(List<TickEvent> tickEvents, Map<Integer, RobotStatus> robotStatusSnapshots) {
        if (tickEvents == null) return emptyList();

        List<robocode.StatusEvent> events = new ArrayList<>();
        tickEvents.forEach(event -> events.add(map(event, robotStatusSnapshots)));
        return events;
    }

    public static robocode.StatusEvent map(TickEvent tickEvent, Map<Integer, RobotStatus> robotStatusSnapshots) {
        if (tickEvent == null) return null;

        RobotStatus robotStatus = robotStatusSnapshots.get(tickEvent.getTurnNumber());
        return new StatusEvent(robotStatus);
    }
}
