package robocode;

/**
 * A MouseExitedEvent is sent to {@link Robot#onMouseExited(java.awt.event.MouseEvent)
 * onMouseExited()} when the mouse has exited the battle view.
 *
 * @see MouseClickedEvent
 * @see MousePressedEvent
 * @see MouseReleasedEvent
 * @see MouseEnteredEvent
 * @see MouseMovedEvent
 * @see MouseDraggedEvent
 * @see MouseWheelMovedEvent
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 *
 * @since 1.6.1
 */
@SuppressWarnings("unused") // API
public final class MouseExitedEvent extends MouseEvent {
	private static final long serialVersionUID = 1L;
	private final static int DEFAULT_PRIORITY = 98;

	/**
	 * Called by the game to create a new MouseExitedEvent.
	 *
	 * @param source the source mouse event originating from the AWT.
	 */
	public MouseExitedEvent(java.awt.event.MouseEvent source) {
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