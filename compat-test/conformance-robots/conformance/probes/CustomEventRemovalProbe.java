package conformance.probes;

import robocode.AdvancedRobot;
import robocode.Condition;
import robocode.CustomEvent;

/** Registers an always-true condition and removes it after its first delivery. */
public class CustomEventRemovalProbe extends AdvancedRobot {

    @Override
    public void run() {
        addCustomEvent(new Condition("one-shot", 80) {
            @Override
            public boolean test() {
                return true;
            }
        });
        while (true) {
            ahead(50);
        }
    }

    @Override
    public void onCustomEvent(CustomEvent event) {
        out.println("CustomEventFired!");
        removeCustomEvent(event.getCondition());
        out.println("CustomEventRemoved!");
    }
}
