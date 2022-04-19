package robocode;

/**
 * This event is sent to {@link Robot#onStatus(StatusEvent) onStatus()} every
 * turn in a battle to provide the status of the robot.
 *
 * @author Flemming N. Larsen (original)
 *
 * @since 1.5
 */
public final class StatusEvent extends Event {
	private static final long serialVersionUID = 1L;
	private final static int DEFAULT_PRIORITY = 99;

	private final RobotStatus status;

	/**
	 * This constructor is called internally from the game in order to create
	 * a new {@link RobotStatus}.
	 *
	 * @param status the current states
	 */
	public StatusEvent(RobotStatus status) {
		super();

		this.status = status;
	}

	/**
	 * Returns the {@link RobotStatus} at the time defined by {@link Robot#getTime()}.
	 *
	 * @return the {@link RobotStatus} at the time defined by {@link Robot#getTime()}.
	 * @see #getTime()
	 */
	public RobotStatus getStatus() {
		return status;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	int getDefaultPriority() {
		return DEFAULT_PRIORITY;
	}
}