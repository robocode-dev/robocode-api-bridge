package robocode.exception;

/**
 * @author Mathew A. Nelson (original)
 */
public class EventInterruptedException extends Error { // Must be an Error!
	private static final long serialVersionUID = 1L;

	private int priority = Integer.MIN_VALUE;

	public EventInterruptedException(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}
}
