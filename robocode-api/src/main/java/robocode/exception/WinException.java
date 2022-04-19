package robocode.exception;

/**
 * @author Mathew A. Nelson (original)
 */
@SuppressWarnings("unused") // API
public class WinException extends Error { // Must be an Error!

	public WinException() {
		super();
	}

	public WinException(String s) {
		super(s);
	}
}