package robocode.exception;

/**
 * @author Pavel Savara (original)
 *
 * @since 1.6.1
 */
@SuppressWarnings("unused") // API
public class AbortedException extends Error { // Must be an Error!
	// From viewpoint of the Robot, an Error is a JVM error:
	// Robot died, their CPU exploded, the JVM for the robot's brain has an error.
	private static final long serialVersionUID = 1L;

	public AbortedException() {
		super();
	}

	public AbortedException(String message) {
		super(message);
	}
}
