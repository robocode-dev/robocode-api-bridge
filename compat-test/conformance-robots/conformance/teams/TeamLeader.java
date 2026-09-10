package conformance.teams;

import robocode.TeamRobot;

import java.io.IOException;

public class TeamLeader extends TeamRobot {

    @Override
    public void run() {
        String[] teammates = getTeammates();
        out.println("TeamLeaderReady:" + (teammates == null ? 0 : teammates.length));
        try {
            // Let every member finish connecting before the first team message. The
            // embedded Tank Royale server starts bot processes independently, so sending
            // immediately at round start can race a teammate's event subscription.
            execute();
            execute();
            execute();
            broadcastMessage(new TeamPayload("BROADCAST"));
            // Keep the two message deliveries on separate turns. Classic queues both
            // messages reliably, but Tank Royale's callback scheduling is not required to
            // preserve multiple same-turn messages while the bridge is draining events.
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
