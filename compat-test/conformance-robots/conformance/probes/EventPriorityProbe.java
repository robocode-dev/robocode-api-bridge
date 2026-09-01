package conformance.probes;

import robocode.AdvancedRobot;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

/** Controls scan dispatch inside and outside a higher-priority wall handler. */
public class EventPriorityProbe extends AdvancedRobot {

    private boolean wallHandlerActive;
    private boolean controlWindow = true;

    @Override
    public void run() {
        // First make scans higher priority than HitWallEvent. A radar sweep in that handler
        // must enter onScannedRobot, proving the same window can generate a scan on both engines.
        setEventPriority("ScannedRobotEvent", 40);
        while (true) {
            ahead(10);
        }
    }

    @Override
    public void onHitWall(HitWallEvent event) {
        wallHandlerActive = true;
        try {
            if (!controlWindow) {
                out.println("SuppressionWindowEntered!!!");
            }
            turnRadarRight(360);
            if (controlWindow) {
                // Subsequent wall handlers exercise the classic lower-priority expiry rule.
                setEventPriority("ScannedRobotEvent", 10);
                controlWindow = false;
            }
        } finally {
            wallHandlerActive = false;
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        out.println("ScanObserved!!!");
        if (wallHandlerActive) {
            if (controlWindow) {
                out.println("ScanControlDuringWallHandler!!!");
            } else {
                out.println("ScannedDuringWallHandler!!!");
            }
        }
    }
}
