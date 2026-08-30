package tracing;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robocode.HitWallEvent;
import robocode.HitByBulletEvent;

/**
 * A robot that reports its own state every turn, so the same run can be compared between
 * classic Robocode and Tank Royale through the bridge.
 *
 * This exists because neither engine will hand the harness a per-turn view from outside.
 * Classic's Control API offers turn snapshots, the Tank Royale runner offers only the final
 * result, and instrumenting either engine would mean measuring a build no robot will ever
 * run against.
 *
 * Reporting from inside the robot is better than either. It measures what the robot
 * perceives, which is the parity question exactly, and it is engine-independent: one class
 * compiled against the classic API runs on classic directly and on Tank Royale through the
 * bridge, because reproducing that API is what the bridge is for.
 *
 * The movement is a fixed sequence rather than a strategy. Two engines cannot be given the
 * same random battle -- Tank Royale has no seed -- so the robot removes every decision it
 * can and issues identical commands in identical order. What remains different between the
 * two traces is the engine, not the robot.
 */
public class TraceRobot extends AdvancedRobot {

    private static final String PREFIX = "TRACE";

    @Override
    public void run() {
        // No colours, no radar strategy, no targeting: fewer decisions, fewer explanations
        // for a divergence that turns out to be the robot's own doing.
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        while (true) {
            report("ahead");
            setAhead(100);
            execute();

            report("turnRight");
            setTurnRight(90);
            execute();

            report("turnGunRight");
            setTurnGunRight(45);
            execute();

            report("back");
            setBack(100);
            execute();

            report("turnRadarRight");
            setTurnRadarRight(90);
            execute();

            report("fire");
            setFire(1);
            execute();
        }
    }

    /**
     * One line per turn, in a fixed field order so two traces can be compared column by
     * column. Values are rounded: the engines agree on the game, not on the last bit of a
     * double, and an unrounded trace would differ on every line for no useful reason.
     */
    private void report(String phase) {
        System.out.println(String.join(" ",
                PREFIX,
                "turn=" + getTime(),
                "round=" + getRoundNum(),
                "phase=" + phase,
                "x=" + round(getX()),
                "y=" + round(getY()),
                "heading=" + round(getHeading()),
                "gun=" + round(getGunHeading()),
                "radar=" + round(getRadarHeading()),
                "energy=" + round(getEnergy()),
                "velocity=" + round(getVelocity()),
                "gunHeat=" + round(getGunHeat()),
                "distanceRemaining=" + round(getDistanceRemaining()),
                "turnRemaining=" + round(getTurnRemaining()),
                "gunTurnRemaining=" + round(getGunTurnRemaining()),
                "radarTurnRemaining=" + round(getRadarTurnRemaining())));
    }

    private static String round(double value) {
        return String.format(java.util.Locale.ROOT, "%.3f", value);
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        System.out.println(PREFIX + " turn=" + getTime() + " event=scanned"
                + " bearing=" + round(e.getBearing())
                + " distance=" + round(e.getDistance())
                + " energy=" + round(e.getEnergy())
                + " heading=" + round(e.getHeading())
                + " velocity=" + round(e.getVelocity()));
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        System.out.println(PREFIX + " turn=" + getTime() + " event=hitWall"
                + " bearing=" + round(e.getBearing()));
    }

    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        System.out.println(PREFIX + " turn=" + getTime() + " event=hitByBullet"
                + " bearing=" + round(e.getBearing())
                + " power=" + round(e.getPower()));
    }
}
