package robocode.exception;

/**
 * @author Mathew A. Nelson (original)
 */
@SuppressWarnings("unused") // API
public class DeathException extends Error { // Must be an Error!
	// From viewpoint of the Robot, an Error is a JVM error:
	// Robot died, their CPU exploded, the JVM for the robot's brain has an error.
	private static final long serialVersionUID = 1L;

	public DeathException() {
		super();
	}

	public DeathException(String message) {
		super(message);
	}
}
