package robocode;

/**
 * A KeyReleasedEvent is sent to {@link Robot#onKeyReleased(java.awt.event.KeyEvent)
 * onKeyReleased()} when a key has been released on the keyboard.
 *
 * @see KeyPressedEvent
 * @see KeyTypedEvent
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 *
 * @since 1.6.1
 */
public final class KeyReleasedEvent extends KeyEvent {
	private static final long serialVersionUID = 1L;
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