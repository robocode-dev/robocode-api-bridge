package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.CustomEvent;
import robocode.Condition;

final class CustomEventMapper {

    public static robocode.CustomEvent map(CustomEvent customEvent) {
        var condition = new Condition() {
            @Override
            public boolean test() {
                return customEvent.getCondition().test();
            }
        };
        return new robocode.CustomEvent(condition);
    }
}
