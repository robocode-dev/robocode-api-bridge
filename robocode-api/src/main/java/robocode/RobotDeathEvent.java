package robocode;

/**
 * This event is sent to {@link Robot#onRobotDeath(RobotDeathEvent) onRobotDeath()}
 * when another robot (not your robot) dies.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
public final class RobotDeathEvent extends Event {
	private static final long serialVersionUID = 1L;
	private final static int DEFAULT_PRIORITY = 70;

	private final String robotName;

	/**
	 * Called by the game to create a new RobotDeathEvent.
	 *
	 * @param robotName the name of the robot that died
	 */
	public RobotDeathEvent(String robotName) {
		super();
		this.robotName = robotName;
	}

	/**
	 * Returns the name of the robot that died.
	 *
	 * @return the name of the robot that died
	 */
	public String getName() {
		return robotName;
	}

	/**
	 * @return the name of the robot that died
	 * @deprecated Use {@link #getName()} instead.
	 */
	@Deprecated
	public String getRobotName() {
		return robotName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	int getDefaultPriority() {
		return DEFAULT_PRIORITY;
	}
}