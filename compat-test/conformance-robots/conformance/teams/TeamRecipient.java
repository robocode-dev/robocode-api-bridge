package conformance.teams;

import robocode.MessageEvent;
import robocode.TeamRobot;

import java.util.List;

public class TeamRecipient extends TeamRobot {

    private int leaderOrderPhase;
    private int droidOrderPhase;

    @Override
    public void run() {
        leaderOrderPhase = 0;
        droidOrderPhase = 0;
        out.println("TeamRecipientName:" + getName());
        out.println("TeamRecipientReady");
        while (true) {
            execute();
        }
    }

    @Override
    public void onMessageReceived(MessageEvent event) {
        Object message = event.getMessage();
        String text = String.valueOf(message);
        if (text.startsWith("ORDER:LEADER:")) {
            String expected = "ORDER:LEADER:" + (leaderOrderPhase + 1);
            if (!expected.equals(text)) {
                out.println("TeamRecipientOrderError:LEADER:" + expected + ":" + text);
            } else {
                leaderOrderPhase++;
                if (leaderOrderPhase == 5) {
                    out.println("TeamRecipientOrderOk:LEADER");
                }
            }
        } else if (text.startsWith("ORDER:DROID:")) {
            String expected = "ORDER:DROID:" + (droidOrderPhase + 1);
            if (!expected.equals(text)) {
                out.println("TeamRecipientOrderError:DROID:" + expected + ":" + text);
            } else {
                droidOrderPhase++;
                if (droidOrderPhase == 5) {
                    out.println("TeamRecipientOrderOk:DROID");
                }
            }
        } else if (message instanceof List<?> batch && batch.size() == 2) {
            out.println("TeamRecipientBatch:" + batch.get(0) + "," + batch.get(1));
        } else {
            out.println("TeamRecipientMessage:" + message + " from " + event.getSender());
        }
    }
}
