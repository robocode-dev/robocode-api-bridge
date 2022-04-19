package robocode.exception;

/**
 * @author Mathew A. Nelson (original)
 */
@SuppressWarnings("unused") // API
public class EventInterruptedException extends Error { // Must be an Error!
	private final int priority;

	public EventInterruptedException(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}
}