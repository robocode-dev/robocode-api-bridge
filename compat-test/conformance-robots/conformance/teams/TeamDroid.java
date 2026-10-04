package conformance.teams;

import robocode.Droid;
import robocode.MessageEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

import java.io.IOException;

public class TeamDroid extends TeamRobot implements Droid {

    @Override
    public void run() {
        out.println("TeamDroidName:" + getName());
        out.println("TeamDroidReady");
        execute();
        execute();
        execute();
        try {
            for (int index = 1; index <= 5; index++) {
                broadcastMessage(new TeamPayload("ORDER:DROID:" + index));
            }
        } catch (IOException exception) {
            out.println("TeamDroidMessageError:" + exception.getClass().getSimpleName());
        }
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
