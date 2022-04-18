package robocode.exception;

public class DisabledException extends Error { // Must be an Error!
	private static final long serialVersionUID = 1L;

	public DisabledException() {
		super();
	}

	public DisabledException(String s) {
		super(s);
	}
}
