package robocode.robotinterfaces;

/**
 * A robot interface for creating an interactive type of robot like
 * {@link robocode.Robot} and {@link robocode.AdvancedRobot} that is able to
 * receive interactive events from the keyboard or mouse.
 * If a robot is directly inherited from this class it will behave as similar to
 * a {@link IBasicRobot}. If you need it to behave similar to a
 * {@link IAdvancedRobot} or {@link ITeamRobot}, you should inherit from these
 * interfaces instead, as these are inherited from this interface.
 *
 * @see robocode.Robot
 * @see robocode.AdvancedRobot
 * @see IBasicRobot
 * @see IJuniorRobot
 * @see IAdvancedRobot
 * @see ITeamRobot
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (javadoc)
 *
 * @since 1.6
 */
@SuppressWarnings("unused") // API
public interface IInteractiveRobot extends IBasicRobot {

	/**
	 * This method is called by the game to notify this robot about interactive
	 * events, i.e. keyboard and mouse events. Hence, this method must be
	 * implemented so it returns your {@link IInteractiveEvents} listener.
	 *
	 * @return listener to interactive events or {@code null} if this robot
	 *         should not receive the notifications.
	 * @since 1.6
	 */
	IInteractiveEvents getInteractiveEventListener();
}