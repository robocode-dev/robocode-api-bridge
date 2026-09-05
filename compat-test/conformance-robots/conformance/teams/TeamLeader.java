package conformance.teams;

import robocode.TeamRobot;

import java.io.IOException;

public class TeamLeader extends TeamRobot {

    @Override
    public void run() {
        String[] teammates = getTeammates();
        out.println("TeamLeaderReady:" + (teammates == null ? 0 : teammates.length));
        try {
            execute();
            broadcastMessage(new TeamPayload("BROADCAST"));
            if (teammates != null && teammates.length > 0) {
                sendMessage(teammates[0], "DIRECT");
            }
            execute();
        } catch (IOException e) {
            out.println("TeamMessageError:" + e.getClass().getSimpleName());
        }
        while (true) {
            execute();
        }
    }
}
