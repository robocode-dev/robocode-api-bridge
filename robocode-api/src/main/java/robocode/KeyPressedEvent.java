package robocode;

/**
 * A KeyPressedEvent is sent to {@link Robot#onKeyPressed(java.awt.event.KeyEvent)
 * onKeyPressed()} when a key has been pressed on the keyboard.
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 * @see KeyReleasedEvent
 * @see KeyTypedEvent
 * @since 1.6.1
 */
@SuppressWarnings("unused") // API
public final class KeyPressedEvent extends KeyEvent {

    private final static int DEFAULT_PRIORITY = 98;

    /**
     * Called by the game to create a new KeyPressedEvent.
     *
     * @param source the source key event originating from the AWT.
     */
    public KeyPressedEvent(java.awt.event.KeyEvent source) {
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