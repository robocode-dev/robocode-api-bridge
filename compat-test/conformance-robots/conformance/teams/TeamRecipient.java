package conformance.teams;

import robocode.MessageEvent;
import robocode.TeamRobot;

public class TeamRecipient extends TeamRobot {

    @Override
    public void run() {
        out.println("TeamRecipientReady");
        while (true) {
            execute();
        }
    }

    @Override
    public void onMessageReceived(MessageEvent event) {
        out.println("TeamRecipientMessage:" + event.getMessage() + " from " + event.getSender());
    }
}
