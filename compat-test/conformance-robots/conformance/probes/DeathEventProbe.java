package conformance.probes;

import robocode.DeathEvent;
import robocode.Robot;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

/** A two-instance probe that reports its own and another robot's death handlers. */
public class DeathEventProbe extends Robot {

    @Override
    public void run() {
        while (true) {
            ahead(100);
            turnGunRight(360);
            back(100);
            turnGunRight(360);
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        fire(2);
    }

    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        out.println("OtherDeath!");
    }

    @Override
    public void onDeath(DeathEvent event) {
        out.println("OwnDeath!");
    }
}
