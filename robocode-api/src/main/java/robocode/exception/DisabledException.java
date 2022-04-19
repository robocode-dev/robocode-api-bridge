package robocode.exception;

@SuppressWarnings("unused") // API
public class DisabledException extends Error { // Must be an Error!

    public DisabledException() {
        super();
    }

    public DisabledException(String s) {
        super(s);
    }
}