package robocode;

/**
 * Super class of all events that originates from the keyboard.
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 * @since 1.6.1
 */
@SuppressWarnings("unused") // API
public abstract class KeyEvent extends Event {

    private final java.awt.event.KeyEvent source;

    /**
     * Called by the game to create a new KeyEvent.
     *
     * @param source the source key event originating from the AWT.
     */
    public KeyEvent(java.awt.event.KeyEvent source) {
        this.source = source;
    }

    /**
     * Do not call this method!
     * <p>
     * This method is used by the game to determine the type of the source key
     * event that occurred in the AWT.
     *
     * @return the source key event that originates from the AWT.
     */
    public java.awt.event.KeyEvent getSourceEvent() {
        return source;
    }
}