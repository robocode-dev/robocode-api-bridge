package conformance.probes.teams;

import robocode.MessageEvent;
import robocode.TeamRobot;

/** A second teammate that must receive broadcasts but not the leader's direct message. */
public class TeamMessageObserver extends TeamRobot {

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
