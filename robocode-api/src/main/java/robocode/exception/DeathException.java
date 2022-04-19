package robocode.exception;

/**
 * @author Mathew A. Nelson (original)
 */
@SuppressWarnings("unused") // API
public class DeathException extends Error { // Must be an Error!

    public DeathException() {
        super();
    }

    public DeathException(String message) {
        super(message);
    }
}