package conformance.probes.teams;

import robocode.TeamRobot;

import java.io.IOException;

/** Supplies the droid with a teammate message instead of a scan. */
public class TeamDroidLeader extends TeamRobot {

    private boolean sent;

    @Override
    public void run() {
        while (!sent) {
            try {
                broadcastMessage("DROID_SIGNAL");
                out.println("DroidSignalSent");
            } catch (IOException e) {
                out.println("DroidSignalFailed:" + e.getClass().getSimpleName());
            }
            sent = true;
            turnLeft(1);
        }
        while (true) {
            turnLeft(1);
        }
    }
}
