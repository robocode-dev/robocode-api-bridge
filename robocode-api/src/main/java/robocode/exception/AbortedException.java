package robocode.exception;

/**
 * @author Pavel Savara (original)
 * @since 1.6.1
 */
@SuppressWarnings("unused") // API
public class AbortedException extends Error { // Must be an Error!

    public AbortedException() {
        super();
    }

    public AbortedException(String message) {
        super(message);
    }
}