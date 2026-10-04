package conformance.teams;

import robocode.TeamRobot;

import java.io.IOException;
import java.util.ArrayList;

public class TeamLeader extends TeamRobot {

    @Override
    public void run() {
        out.println("TeamLeaderName:" + getName());
        String[] teammates = getTeammates();
        out.println("TeamLeaderReady:" + (teammates == null ? 0 : teammates.length));
        if (teammates != null) {
            for (String teammate : teammates) {
                out.println("TeamTeammateName:" + teammate);
                if (!isTeammate(teammate)) {
                    out.println("TeamIsTeammateMismatch:" + teammate);
                }
            }
        }
        try {
            // Let every member finish connecting before the first team message. The
            // embedded Tank Royale server starts bot processes independently, so sending
            // immediately at round start can race a teammate's event subscription.
            execute();
            execute();
            execute();
            broadcastMessage(new TeamPayload("BROADCAST"));
            execute();
            for (int index = 1; index <= 5; index++) {
                broadcastMessage(new TeamPayload("ORDER:LEADER:" + index));
            }
            execute();
            var batch = new ArrayList<String>();
            batch.add("BATCH:first");
            batch.add("BATCH:second");
            broadcastMessage(batch);
            // Keep the list payload and directed-message checks separate from the same-turn
            // ordering probe above so each path has an independent result.
            execute();
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
