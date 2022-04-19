package robocode.robotinterfaces;

/**
 * A robot interface for creating a team robot like {@link robocode.TeamRobot}
 * that is able to receive team events.
 * A team robot is an advanced type of robot that supports sending messages
 * between teammates that participates in a team.
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 * @see robocode.TeamRobot
 * @see IBasicRobot
 * @see IJuniorRobot
 * @see IInteractiveRobot
 * @see IAdvancedRobot
 * @see ITeamRobot
 * @since 1.6
 */
@SuppressWarnings("unused") // API
public interface ITeamRobot extends IAdvancedRobot {

    /**
     * This method is called by the game to notify this robot about team events.
     * Hence, this method must be implemented so it returns your
     * {@link ITeamEvents} listener.
     *
     * @return listener to team events or {@code null} if this robot should
     * not receive the notifications.
     */
    ITeamEvents getTeamEventListener();
}