package conformance.probes;

import robocode.AdvancedRobot;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

/** Records only a scan handler entered while the higher-priority wall handler is blocked. */
public class EventPriorityProbe extends AdvancedRobot {

    private boolean wallHandlerActive;

    @Override
    public void run() {
        while (true) {
            ahead(10);
        }
    }

    @Override
    public void onHitWall(HitWallEvent event) {
        wallHandlerActive = true;
        try {
            turnRadarRight(360);
        } finally {
            wallHandlerActive = false;
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        if (wallHandlerActive) {
            out.println("ScannedDuringWallHandler!!!");
        }
    }
}
