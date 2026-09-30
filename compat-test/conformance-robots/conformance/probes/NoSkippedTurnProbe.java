package conformance.probes;

import robocode.AdvancedRobot;

/** Responds to turns immediately so a completed capture can exercise the empty-event case. */
public class NoSkippedTurnProbe extends AdvancedRobot {

    @Override
    public void run() {
        while (true) {
            execute();
        }
    }
}
