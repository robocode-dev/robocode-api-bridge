package robocode;

/**
 * A MouseDraggedEvent is sent to {@link Robot#onMouseDragged(java.awt.event.MouseEvent)
 * onMouseDragged()} when the mouse is dragged inside the battle view.
 *
 * @see MouseClickedEvent
 * @see MousePressedEvent
 * @see MouseReleasedEvent
 * @see MouseEnteredEvent
 * @see MouseExitedEvent
 * @see MouseMovedEvent
 * @see MouseWheelMovedEvent
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 *
 * @since 1.6.1
 */
@SuppressWarnings("unused") // API
public final class MouseDraggedEvent extends MouseEvent {
	private static final long serialVersionUID = 1L;
	private final static int DEFAULT_PRIORITY = 98;

	/**
	 * Called by the game to create a new MouseDraggedEvent.
	 *
	 * @param source the source mouse event originating from the AWT.
	 */
	public MouseDraggedEvent(java.awt.event.MouseEvent source) {
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