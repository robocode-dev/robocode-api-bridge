package conformance.probes;

import robocode.AdvancedRobot;
import robocode.SkippedTurnEvent;
import robocode.StatusEvent;

/** Deliberately overruns a bounded number of status callbacks to produce skipped turns. */
public class SkippedTurnProbe extends AdvancedRobot {

    private int statusCallbacks;
    private int round;

    @Override
    public void run() {
        while (true) {
            turnLeft(1);
        }
    }

    @Override
    public void onStatus(StatusEvent event) {
        round = event.getStatus().getRoundNum();
        if (++statusCallbacks <= 10) {
            try {
                Thread.sleep(130);
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void onSkippedTurn(SkippedTurnEvent event) {
        out.println("SkippedTurnReported:" + round + ":" + event.getSkippedTurn());
    }

}
