package conformance.probes;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robocode.StatusEvent;

/** Reports the number of scan callbacks carried by each observed turn. */
public class MeleeScanProbe extends AdvancedRobot {

    private long currentTurn = -1;
    private int scansThisTurn;

    @Override
    public void run() {
        setAdjustRadarForRobotTurn(true);
        while (true) {
            setTurnRadarRight(360);
            execute();
        }
    }

    @Override
    public void onStatus(StatusEvent event) {
        if (currentTurn >= 0) {
            out.println("MeleeScanCount:" + currentTurn + ":" + scansThisTurn);
        }
        currentTurn = event.getTime();
        scansThisTurn = 0;
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        scansThisTurn++;
    }
}
