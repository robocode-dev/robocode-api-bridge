package robocode;

/**
 * A prebuilt condition you can use that indicates your robot has finished
 * moving.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author Nathaniel Troutman (contributor)
 * @see Condition
 */
@SuppressWarnings("unused") // API
public class MoveCompleteCondition extends Condition {
    private AdvancedRobot robot;

    /**
     * Creates a new MoveCompleteCondition with default priority.
     * The default priority is 80.
     *
     * @param robot your robot, which must be a {@link AdvancedRobot}
     */
    public MoveCompleteCondition(AdvancedRobot robot) {
        super();
        this.robot = robot;
    }

    /**
     * Creates a new MoveCompleteCondition with the specified priority.
     * A condition priority is a value from 0 - 99. The higher value, the
     * higher priority. The default priority is 80.
     *
     * @param robot    your robot, which must be a {@link AdvancedRobot}
     * @param priority the priority of this condition
     * @see Condition#setPriority(int)
     */
    public MoveCompleteCondition(AdvancedRobot robot, int priority) {
        super();
        this.robot = robot;
        this.priority = priority;
    }

    /**
     * Tests if the robot has stopped moving.
     *
     * @return {@code true} if the robot has stopped moving; {@code false}
     * otherwise
     */
    @Override
    public boolean test() {
        return (robot.getDistanceRemaining() == 0);
    }

    /**
     * Called by the system in order to clean up references to internal objects.
     *
     * @since 1.4.3
     */
    @Override
    public void cleanup() {
        robot = null;
    }
}