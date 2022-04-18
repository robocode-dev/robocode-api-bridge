package robocode.exception;

/**
 * @author Mathew A. Nelson (original)
 */
public class RobotException extends Error { // Must be an Error!
	private static final long serialVersionUID = 1L;

	public RobotException() {
		super();
	}

	public RobotException(String s) {
		super(s);
	}
}
