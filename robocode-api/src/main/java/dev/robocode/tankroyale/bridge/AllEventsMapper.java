package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.events.*;
import robocode.Event;
import robocode.RobotStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

final class AllEventsMapper {

    public static List<Event> map(List<BotEvent> botEvents, IBot bot, Map<Long, RobotStatus> robotStatuses) {
        if (botEvents == null) return emptyList();

        var events = new ArrayList<Event>();

        botEvents.forEach(botEvent -> {
            Event event = null;
            if (botEvent instanceof WonRoundEvent) {
                event = new robocode.WinEvent();
            } else if (botEvent instanceof SkippedTurnEvent) {
                event = new robocode.SkippedTurnEvent(botEvent.getTurnNumber());
            } else if (botEvent instanceof TickEvent) {
                event = StatusEventMapper.map(robotStatuses, botEvent.getTurnNumber());
            } else if (botEvent instanceof CustomEvent) {
                event = CustomEventMapper.map((CustomEvent) botEvent);
            } else if (botEvent instanceof BulletHitWallEvent) {
                event = BulletMissedEventMapper.map((BulletHitWallEvent) botEvent);
            } else if (botEvent instanceof BulletHitBulletEvent) {
                event = BulletHitBulletEventMapper.map((BulletHitBulletEvent) botEvent);
            } else if (botEvent instanceof BulletHitBotEvent) {
                event = BulletHitEventMapper.map((BulletHitBotEvent) botEvent);
            } else if (botEvent instanceof HitByBulletEvent) {
                event = HitByBulletEventMapper.map((HitByBulletEvent) botEvent, bot);
            } else if (botEvent instanceof HitWallEvent) {
                event = HitWallEventMapper.map((HitWallEvent) botEvent, bot);
            } else if (botEvent instanceof HitBotEvent) {
                event = HitRobotEventMapper.map((HitBotEvent) botEvent, bot);
            } else if (botEvent instanceof ScannedBotEvent) {
                event = ScannedRobotEventMapper.map((ScannedBotEvent) botEvent, bot);
            } else if (botEvent instanceof BotDeathEvent) {
                event = RobotDeathEventMapper.map((BotDeathEvent) botEvent);
            } else if (botEvent instanceof TeamMessageEvent) {
                event = MessageEventMapper.map((TeamMessageEvent)botEvent);
            }
            if (event != null) {
                events.add(event);
            }
        });
        return events;
    }
}
