package conformance.probes;

import robocode.AdvancedRobot;
import robocode.RobotStatus;
import robocode.StatusEvent;

/** Records the three clocks visible when a new-turn status event reaches the robot. */
public class TurnBoundaryProbe extends AdvancedRobot {

    @Override
    public void run() {
        while (true) {
            setTurnRadarRight(360);
            execute();
        }
    }

    @Override
    public void onStatus(StatusEvent event) {
        RobotStatus status = event.getStatus();
        out.println("TurnStatus:" + status.getRoundNum() + ":" + event.getTime() + ":"
                + status.getTime() + ":" + getTime());
    }
}
