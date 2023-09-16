package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.*;

final class EventClassMapper {

    public static Class<? extends BotEvent> toBotEventClass(String eventClass) {
        if (robocode.StatusEvent.class.getSimpleName().equals(eventClass)) {
            return TickEvent.class;
        }
        if (robocode.ScannedRobotEvent.class.getSimpleName().equals(eventClass)) {
            return ScannedBotEvent.class;
        }
        if (robocode.SkippedTurnEvent.class.getSimpleName().equals(eventClass)) {
            return SkippedTurnEvent.class;
        }
        if (robocode.HitWallEvent.class.getSimpleName().equals(eventClass)) {
            return HitWallEvent.class;
        }
        if (robocode.BulletMissedEvent.class.getSimpleName().equals(eventClass)) {
            return BulletHitWallEvent.class;
        }
        if (robocode.BulletHitEvent.class.getSimpleName().equals(eventClass)) {
            return BulletHitBotEvent.class;
        }
        if (robocode.HitByBulletEvent.class.getSimpleName().equals(eventClass)) {
            return HitByBulletEvent.class;
        }
        if (robocode.BulletHitBulletEvent.class.getSimpleName().equals(eventClass)) {
            return BulletHitBulletEvent.class;
        }
        if (robocode.HitRobotEvent.class.getSimpleName().equals(eventClass)) {
            return HitBotEvent.class;
        }
        if (robocode.RobotDeathEvent.class.getSimpleName().equals(eventClass)) {
            return BotDeathEvent.class;
        }
        if (robocode.DeathEvent.class.getSimpleName().equals(eventClass)) {
            return DeathEvent.class;
        }
        if (robocode.CustomEvent.class.getSimpleName().equals(eventClass)) {
            return CustomEvent.class;
        }
        if (robocode.WinEvent.class.getSimpleName().equals(eventClass)) {
            return WonRoundEvent.class;
        }
        if (robocode.MessageEvent.class.getSimpleName().equals(eventClass)) {
            return TeamMessageEvent.class;
        }
        throw new UnsupportedOperationException("Unsupported event class: " + eventClass);
    }
}
