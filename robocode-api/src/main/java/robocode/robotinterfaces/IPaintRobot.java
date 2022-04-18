package robocode.robotinterfaces;

/**
 * A robot interface that makes it possible for a robot to receive paint events.
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 *
 * @since 1.6
 */
public interface IPaintRobot extends IBasicRobot {

	/**
	 * This method is called by the game to notify this robot about painting
	 * events. Hence, this method must be implemented so it returns your
	 * {@link IPaintEvents} listener.
	 *
	 * @return listener to paint events or {@code null} if this robot should
	 *         not receive the notifications.
	 * @since 1.6
	 */
	IPaintEvents getPaintEventListener();
}
