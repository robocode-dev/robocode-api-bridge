package conformance.probes.teams;

import robocode.Droid;
import robocode.MessageEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

/** A droid must receive teammate information without receiving its own scans. */
public class TeamDroidProbe extends TeamRobot implements Droid {

    @Override
    public void run() {
        while (true) {
            turnRight(1);
        }
    }

    @Override
    public void onMessageReceived(MessageEvent event) {
        if ("DROID_SIGNAL".equals(event.getMessage())) {
            out.println("DroidMessageReceived");
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        out.println("DroidScanReceived");
    }
}
