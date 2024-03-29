package robocode.robotinterfaces;

/**
 * A robot interface for creating the most primitive robot type, which is a
 * {@link robocode.JuniorRobot}. A junior robot is simpler than the
 * {@link robocode.Robot} class.
 * <p>
 * A junior robot has a simplified model, in purpose of teaching programming
 * skills to inexperienced in programming students.
 * The simplified robot model will keep player from overwhelming of Robocode's
 * rules, programming syntax and programming concept.
 * <p>
 * Instead of using getters and setters, public fields are provided for
 * receiving information like the last scanned robot, the coordinate of the
 * robot etc.
 * <p>
 * All methods on a junior robot are blocking calls, i.e. they do not return
 * before their action has been completed and will at least take one turn to
 * execute.
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 * @see robocode.JuniorRobot
 * @see IBasicRobot
 * @see IAdvancedRobot
 * @see IInteractiveRobot
 * @see ITeamRobot
 * @since 1.6
 */
public interface IJuniorRobot extends IBasicRobot {
}