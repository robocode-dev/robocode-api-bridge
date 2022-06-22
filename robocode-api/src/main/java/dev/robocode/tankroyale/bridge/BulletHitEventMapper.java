package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.BulletHitBotEvent;
import robocode.Bullet;
import robocode.BulletHitEvent;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

final class BulletHitEventMapper {

    public static List<BulletHitEvent> map(List<BulletHitBotEvent> bulletHitBotEvents) {
        if (bulletHitBotEvents == null) return emptyList();

        List<BulletHitEvent> events = new ArrayList<>();
        bulletHitBotEvents.forEach(event -> events.add(map(event)));
        return events;
    }

    public static BulletHitEvent map(BulletHitBotEvent bulletHitBotEvent) {
        if (bulletHitBotEvent == null) return null;

        String victimName = "" + bulletHitBotEvent.getVictimId();
        Bullet bullet = BulletMapper.map(bulletHitBotEvent.getBullet(), victimName);
        return new BulletHitEvent(victimName, bulletHitBotEvent.getEnergy(), bullet);
    }
}
