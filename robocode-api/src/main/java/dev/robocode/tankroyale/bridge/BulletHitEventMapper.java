package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.BulletHitBotEvent;
import robocode.BulletHitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

final class BulletHitEventMapper {

    public static List<BulletHitEvent> map(List<BulletHitBotEvent> bulletHitBotEvents) {
        if (bulletHitBotEvents == null) return emptyList();

        return bulletHitBotEvents.stream()
                .map(BulletHitEventMapper::map)
                .collect(Collectors.toList());
    }

    public static BulletHitEvent map(BulletHitBotEvent bulletHitBotEvent) {
        if (bulletHitBotEvent == null) return null;

        var victimName = String.valueOf(bulletHitBotEvent.getVictimId());
        var bullet = BulletMapper.map(bulletHitBotEvent.getBullet(), victimName);

        var event = new BulletHitEvent(victimName, bulletHitBotEvent.getEnergy(), bullet);
        event.setTime(bulletHitBotEvent.getTurnNumber());
        return event;
    }
}
