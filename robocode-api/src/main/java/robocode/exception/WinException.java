package robocode.exception;

/**
 * @author Mathew A. Nelson (original)
 */
public class WinException extends Error { // Must be an Error!
	private static final long serialVersionUID = 1L;

	public WinException() {
		super();
	}

	public WinException(String s) {
		super(s);
	}
}
