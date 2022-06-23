package dev.robocode.tankroyale.bridge;

import robocode.CustomEvent;
import robocode.SkippedTurnEvent;
import robocode.robotinterfaces.IAdvancedEvents;

public class AdvancedEventAdaptor implements IAdvancedEvents {

    @Override
    public void onSkippedTurn(SkippedTurnEvent event) {
    }

    @Override
    public void onCustomEvent(CustomEvent event) {
    }
}
