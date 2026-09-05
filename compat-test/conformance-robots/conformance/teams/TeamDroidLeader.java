package conformance.probes.teams;

import robocode.TeamRobot;

import java.io.IOException;

/** Repeats a teammate message until the droid has had time to receive it. */
public class TeamDroidLeader extends TeamRobot {

    @Override
    public void run() {
        int attempts = 0;
        while (attempts++ < 25) {
            String[] teammates = getTeammates();
            if (teammates != null && teammates.length > 0) {
                try {
                    broadcastMessage("DROID_SIGNAL");
                    out.println("DroidSignalSent");
                } catch (IOException e) {
                    out.println("DroidSignalFailed:" + e.getClass().getSimpleName());
                }
            }
            turnLeft(1);
        }
        while (true) {
            turnLeft(1);
        }
    }
}
