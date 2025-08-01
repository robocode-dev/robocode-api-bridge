package robocode.robotinterfaces;

import robocode.robotinterfaces.peer.IBasicRobotPeer;

/**
 * A robot interface for creating a basic type of robot like {@link robocode.Robot}
 * that is able to receive common robot events, but not interactive events as
 * with the {@link robocode.Robot} class.
 * A basic robot allows blocking calls only and cannot handle custom events nor
 * writes to the file system like an advanced robot.
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 * @see robocode.Robot
 * @see IJuniorRobot
 * @see IInteractiveRobot
 * @see IAdvancedRobot
 * @see ITeamRobot
 * @since 1.6
 */
@SuppressWarnings("unused") // API
public interface IBasicRobot {

    /**
     * This method is called by the game to invoke the
     * {@link java.lang.Runnable#run() run()} method of your robot, where the program
     * of your robot is implemented.
     *
     * @return a runnable implementation
     * @see java.lang.Runnable#run()
     * @since 1.6
     */
    Runnable getRobotRunnable();

    /**
     * This method is called by the game to notify this robot about basic
     * robot event. Hence, this method must be implemented so it returns your
     * {@link IBasicEvents} listener.
     *
     * @return listener to basic events or {@code null} if this robot should
     * not receive the notifications.
     * @since 1.6
     */
    IBasicEvents getBasicEventListener();

    /**
     * Do not call this method! Your robot will simply stop interacting with
     * the game.
     * <p>
     * This method is called by the game. A robot peer is the object that deals
     * with game mechanics and rules, and makes sure your robot abides by them.
     *
     * @param peer the robot peer supplied by the game
     */
    void setPeer(IBasicRobotPeer peer);

    /**
     * Do not call this method!
     * <p>
     * This method is called by the game when setting the output stream for your
     * robot.
     *
     * @param out the new output print stream for this robot
     * @since 1.6
     */
    void setOut(java.io.PrintStream out);

    /**
     * This method is not a part of the original Robocode API, but a part of the
     * Robocode bridge for Robocode Tank Royale.
     * <p>
     * Stops the robot thread.
     */
    void stopThread();
}