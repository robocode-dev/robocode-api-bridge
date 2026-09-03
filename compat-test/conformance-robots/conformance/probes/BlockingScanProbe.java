package conformance.probes;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

/** Keeps a radar sweep running while a scan handler makes a blocking move. */
public class BlockingScanProbe extends AdvancedRobot {

    @Override
    public void run() {
        setAdjustRadarForRobotTurn(true);
        while (true) {
            setTurnRadarRight(360);
            setAhead(20);
            execute();
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        out.println("BlockingScanDelivered!");
        ahead(100);
    }
}
