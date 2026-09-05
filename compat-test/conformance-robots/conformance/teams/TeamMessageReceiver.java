package conformance.probes.teams;

import robocode.MessageEvent;
import robocode.TeamRobot;

/** Receives the direct and broadcast messages from the team leader. */
public class TeamMessageReceiver extends TeamRobot {

    @Override
    public void run() {
        while (true) {
            turnRight(1);
        }
    }

    @Override
    public void onMessageReceived(MessageEvent event) {
        if ("DIRECT".equals(event.getMessage())) {
            out.println("TeamDirectReceived");
        }
        if ("BROADCAST".equals(event.getMessage())) {
            out.println("TeamBroadcastReceived");
        }
    }
}
