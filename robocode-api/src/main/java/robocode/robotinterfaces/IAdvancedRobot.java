package robocode.robotinterfaces;

/**
 * A robot interface for creating a more advanced type of robot like
 * {@link robocode.AdvancedRobot} that is able to handle advanced robot events.
 * An advanced robot allows non-blocking calls, custom events, get notifications
 * about skipped turns, and also allow writes to the file system.
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 * @see robocode.AdvancedRobot
 * @see IBasicRobot
 * @see IJuniorRobot
 * @see IInteractiveRobot
 * @see ITeamRobot
 * @since 1.6
 */
@SuppressWarnings("unused") // API
public interface IAdvancedRobot extends IBasicRobot {

    /**
     * This method is called by the game to notify this robot about advanced
     * robot event. Hence, this method must be implemented so it returns your
     * {@link IAdvancedEvents} listener.
     *
     * @return listener to advanced events or {@code null} if this robot should
     * not receive the notifications.
     * @since 1.6
     */
    IAdvancedEvents getAdvancedEventListener();
}