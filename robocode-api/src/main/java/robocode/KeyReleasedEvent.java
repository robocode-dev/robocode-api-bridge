package robocode;

/**
 * A KeyReleasedEvent is sent to {@link Robot#onKeyReleased(java.awt.event.KeyEvent)
 * onKeyReleased()} when a key has been released on the keyboard.
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 * @see KeyPressedEvent
 * @see KeyTypedEvent
 * @since 1.6.1
 */
@SuppressWarnings("unused") // API
public final class KeyReleasedEvent extends KeyEvent {

    private final static int DEFAULT_PRIORITY = 98;

    /**
     * Called by the game to create a new KeyReleasedEvent.
     *
     * @param source the source key event originating from the AWT.
     */
    public KeyReleasedEvent(java.awt.event.KeyEvent source) {
        super(source);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    int getDefaultPriority() {
        return DEFAULT_PRIORITY;
    }
}