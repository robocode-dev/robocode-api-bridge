package conformance.teams;

import robocode.Droid;
import robocode.MessageEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

public class TeamDroid extends TeamRobot implements Droid {

    @Override
    public void run() {
        out.println("TeamDroidReady");
        while (true) {
            execute();
        }
    }

    @Override
    public void onMessageReceived(MessageEvent event) {
        out.println("TeamDroidMessage:" + event.getMessage() + " from " + event.getSender());
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        out.println("TeamDroidScanned");
    }
}
