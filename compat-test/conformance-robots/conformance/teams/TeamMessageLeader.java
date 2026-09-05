package conformance.probes.teams;

import robocode.MessageEvent;
import robocode.TeamRobot;

import java.io.IOException;

/** Sends one direct and one broadcast message after the team roster is available. */
public class TeamMessageLeader extends TeamRobot {

    private boolean sent;

    @Override
    public void run() {
        while (!sent) {
            String[] teammates = getTeammates();
            if (teammates != null && teammates.length > 0) {
                try {
                    sendMessage(teammates[0], "DIRECT");
                    broadcastMessage("BROADCAST");
                    out.println("TeamMessagesSent");
                } catch (IOException e) {
                    out.println("TeamMessagesFailed:" + e.getClass().getSimpleName());
                }
                sent = true;
            }
            turnLeft(1);
        }
        while (true) {
            turnLeft(1);
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
