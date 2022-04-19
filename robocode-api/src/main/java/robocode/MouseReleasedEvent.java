package robocode;

/**
 * A MouseReleasedEvent is sent to {@link Robot#onMouseReleased(java.awt.event.MouseEvent)
 * onMouseReleased()} when the mouse is released inside the battle view.
 *
 * @see MouseClickedEvent
 * @see MousePressedEvent
 * @see MouseEnteredEvent
 * @see MouseExitedEvent
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
public final class MouseReleasedEvent extends MouseEvent {
	private static final long serialVersionUID = 1L;
	private final static int DEFAULT_PRIORITY = 98;

	/**
	 * Called by the game to create a new MouseReleasedEvent.
	 *
	 * @param source the source mouse event originating from the AWT.
	 */
	public MouseReleasedEvent(java.awt.event.MouseEvent source) {
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