package conformance.teams;

import robocode.MessageEvent;
import robocode.TeamRobot;

import java.util.List;

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
        Object message = event.getMessage();
        if (message instanceof List<?> batch && batch.size() == 2) {
            out.println("TeamRecipientBatch:" + batch.get(0) + "," + batch.get(1));
        } else {
            out.println("TeamRecipientMessage:" + message + " from " + event.getSender());
        }
    }
}
