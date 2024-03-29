package robocode;

import robocode.exception.RobotException;
import robocode.robotinterfaces.IBasicRobot;
import robocode.robotinterfaces.peer.IBasicRobotPeer;
import robocode.util.Utils;

import java.io.PrintStream;

/**
 * This is the base class of all robots used by the system.
 * You should not inherit your robot on this class.
 * <p>
 * You should create a robot that is derived from the {@link Robot}, {@link AdvancedRobot},
 * {@link JuniorRobot}, {@link TeamRobot}, or {@link RateControlRobot} class instead.
 *
 * @author Flemming N. Larsen (original)
 * @author Pavel Savara (contributor)
 * @see Robot
 * @see JuniorRobot
 * @see AdvancedRobot
 * @see TeamRobot
 * @see RateControlRobot
 * @since 1.4
 */
@SuppressWarnings("unused") // API
public abstract class _RobotBase implements IBasicRobot, Runnable {

    // Internal for this package
    _RobotBase() {
    }

    IBasicRobotPeer peer;

    /**
     * The output stream your robot should use to print.
     * <p>
     * You can view the print-outs by clicking the button for your robot in the
     * right side of the battle window.
     * <p>
     * Example:
     * <pre>
     *   // Print out a line each time my robot hits another robot
     *   public void onHitRobot(HitRobotEvent e) {
     *       out.println("I hit a robot!  My energy: " + getEnergy() + " his energy: " + e.getEnergy());
     *   }
     * </pre>
     */
    public PrintStream out = System.out;

    /**
     * {@inheritDoc}
     */
    public final void setOut(PrintStream out) {
        this.out = out;
    }

    /**
     * {@inheritDoc}
     */
    public final void setPeer(IBasicRobotPeer peer) {
        this.peer = peer;
    }

    /**
     * Throws a RobotException. This method should be called when the robot's peer
     * is uninitialized.
     */
    static void uninitializedException() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String methodName = trace[2].getMethodName();

        throw new RobotException(
                "You cannot call the " + methodName
                        + "() method before your run() method is called, or you are using a Robot object that the game doesn't know about.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (peer == null) return null;
        return peer.getName() + " (" + (int) peer.getEnergy() + ") X" + (int) peer.getX() + " Y" + (int) peer.getY()
                + " ~" + Utils.angleToApproximateDirection(peer.getBodyHeading());
    }
}