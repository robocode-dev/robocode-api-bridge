package tracing;

import robocode.AdvancedRobot;
import robocode.StatusEvent;

/**
 * Settles one question empirically: after a known right turn is commanded, what sign does the
 * remaining turn have — and do the two paths a robot can read it through agree?
 *
 * The two paths are {@code getTurnRemainingRadians()}, which goes through the peer, and
 * {@code e.getStatus().getTurnRemainingRadians()} inside {@code onStatus}, which goes through
 * the status mapper. On classic Robocode both are the same engine value, so classic is the
 * reference for what a robot should see.
 *
 * Run on both engines and compare. Documentation says a right turn leaves a positive
 * remainder in Robocode and a negative one in Tank Royale; this reports what actually happens.
 */
public class TurnSignProbe extends AdvancedRobot {

    private int reported;

    @Override
    public void run() {
        // A right turn, large enough that a remainder still exists on the next turn.
        setTurnRight(90);
        setTurnGunRight(90);
        setTurnRadarRight(90);
        execute();

        while (true) {
            if (reported < 3) {
                System.out.println("PROBE peer"
                        + " body=" + fmt(getTurnRemainingRadians())
                        + " gun=" + fmt(getGunTurnRemainingRadians())
                        + " radar=" + fmt(getRadarTurnRemainingRadians()));
                reported++;
            }
            execute();
        }
    }

    @Override
    public void onStatus(StatusEvent e) {
        if (reported < 3) {
            System.out.println("PROBE status"
                    + " body=" + fmt(e.getStatus().getTurnRemainingRadians())
                    + " gun=" + fmt(e.getStatus().getGunTurnRemainingRadians())
                    + " radar=" + fmt(e.getStatus().getRadarTurnRemainingRadians()));
        }
    }

    private static String fmt(double radians) {
        return String.format(java.util.Locale.ROOT, "%+.1f", Math.toDegrees(radians));
    }
}
