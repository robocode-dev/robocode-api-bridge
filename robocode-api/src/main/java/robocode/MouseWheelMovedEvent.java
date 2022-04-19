package robocode;

/**
 * A MouseWheelMovedEvent is sent to {@link Robot#onMouseWheelMoved(java.awt.event.MouseWheelEvent e)
 * onMouseWheelMoved()} when the mouse wheel is rotated inside the battle view.
 *
 * @see MouseClickedEvent
 * @see MousePressedEvent
 * @see MouseReleasedEvent
 * @see MouseEnteredEvent
 * @see MouseExitedEvent
 * @see MouseMovedEvent
 * @see MouseDraggedEvent
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 *
 * @since 1.6.1
 */
public final class MouseWheelMovedEvent extends MouseEvent {
	private static final long serialVersionUID = 1L;
	private final static int DEFAULT_PRIORITY = 98;

	/**
	 * Called by the game to create a new MouseWheelMovedEvent.
	 *
	 * @param source the source mouse event originating from the AWT.
	 */
	public MouseWheelMovedEvent(java.awt.event.MouseEvent source) {
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