package robocode;

/**
 * A MousePressedEvent is sent to {@link Robot#onMousePressed(java.awt.event.MouseEvent)
 * onMousePressed()} when the mouse is pressed inside the battle view.
 *
 * @see MouseClickedEvent
 * @see MouseReleasedEvent
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
public final class MousePressedEvent extends MouseEvent {
	private static final long serialVersionUID = 1L;
	private final static int DEFAULT_PRIORITY = 98;

	/**
	 * Called by the game to create a new MousePressedEvent.
	 *
	 * @param source the source mouse event originating from the AWT.
	 */
	public MousePressedEvent(java.awt.event.MouseEvent source) {
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