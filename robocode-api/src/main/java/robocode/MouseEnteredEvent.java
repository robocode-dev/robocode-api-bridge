package robocode;

/**
 * A MouseEnteredEvent is sent to {@link Robot#onMouseEntered(java.awt.event.MouseEvent)
 * onMouseEntered()} when the mouse has entered the battle view.
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 * @see MouseClickedEvent
 * @see MousePressedEvent
 * @see MouseReleasedEvent
 * @see MouseExitedEvent
 * @see MouseMovedEvent
 * @see MouseDraggedEvent
 * @see MouseWheelMovedEvent
 * @since 1.6.1
 */
@SuppressWarnings("unused") // API
public final class MouseEnteredEvent extends MouseEvent {

    private final static int DEFAULT_PRIORITY = 98;

    /**
     * Called by the game to create a new MouseEnteredEvent.
     *
     * @param source the source mouse event originating from the AWT.
     */
    public MouseEnteredEvent(java.awt.event.MouseEvent source) {
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