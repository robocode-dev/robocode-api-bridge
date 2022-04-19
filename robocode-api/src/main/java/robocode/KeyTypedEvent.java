package robocode;

/**
 * A KeyTypedEvent is sent to {@link Robot#onKeyTyped(java.awt.event.KeyEvent)
 * onKeyTyped()} when a key has been typed (pressed and released) on the keyboard.
 *
 * @see KeyPressedEvent
 * @see KeyReleasedEvent
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 *
 * @since 1.6.1
 */
@SuppressWarnings("unused") // API
public final class KeyTypedEvent extends KeyEvent {
	private static final long serialVersionUID = 1L;
	private final static int DEFAULT_PRIORITY = 98;

	/**
	 * Called by the game to create a new KeyTypedEvent.
	 *
	 * @param source the source key event originating from the AWT.
	 */
	public KeyTypedEvent(java.awt.event.KeyEvent source) {
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