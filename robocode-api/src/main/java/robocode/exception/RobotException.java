package robocode.exception;

/**
 * @author Mathew A. Nelson (original)
 */
@SuppressWarnings("unused") // API
public class RobotException extends Error { // Must be an Error!

    public RobotException() {
        super();
    }

    public RobotException(String s) {
        super(s);
    }
}