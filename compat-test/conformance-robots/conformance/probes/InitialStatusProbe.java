package conformance.probes;

import robocode.AdvancedRobot;
import robocode.StatusEvent;

/** Checks that the start status is delivered before run and only once for its turn. */
public class InitialStatusProbe extends AdvancedRobot {

    private int statusCount;

    @Override
    public void run() {
        out.println("InitialStatusBeforeRun:" + statusCount);
        out.println("PendingStatusAtRunStart:" + getStatusEvents().size());
        setTurnRadarRight(360);
        execute();
        out.println("StatusAfterFirstExecute:" + statusCount);

        while (true) {
            execute();
        }
    }

    @Override
    public void onStatus(StatusEvent event) {
        if (statusCount == 0) {
            out.println("InitialStatusClock:" + event.getTime() + ":"
                    + event.getStatus().getTime() + ":" + getTime());
        }
        statusCount++;
    }
}
