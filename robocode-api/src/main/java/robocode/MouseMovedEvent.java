package robocode;

/**
 * A MouseMovedEvent is sent to {@link Robot#onMouseMoved(java.awt.event.MouseEvent)
 * onMouseMoved()} when the mouse has moved inside the battle view.
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 * @see MouseClickedEvent
 * @see MousePressedEvent
 * @see MouseReleasedEvent
 * @see MouseEnteredEvent
 * @see MouseExitedEvent
 * @see MouseDraggedEvent
 * @see MouseWheelMovedEvent
 * @since 1.6.1
 */
@SuppressWarnings("unused") // API
public final class MouseMovedEvent extends MouseEvent {

    private final static int DEFAULT_PRIORITY = 98;

    /**
     * Called by the game to create a new MouseMovedEvent.
     *
     * @param source the source mouse event originating from the AWT.
     */
    public MouseMovedEvent(java.awt.event.MouseEvent source) {
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