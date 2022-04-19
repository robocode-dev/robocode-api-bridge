package robocode;

/**
 * A MouseClickedEvent is sent to {@link Robot#onMouseClicked(java.awt.event.MouseEvent)
 * onMouseClicked()} when the mouse is clicked inside the battle view.
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 * @see MousePressedEvent
 * @see MouseReleasedEvent
 * @see MouseEnteredEvent
 * @see MouseExitedEvent
 * @see MouseMovedEvent
 * @see MouseDraggedEvent
 * @see MouseWheelMovedEvent
 * @since 1.6.1
 */
@SuppressWarnings("unused") // API
public final class MouseClickedEvent extends MouseEvent {

    private final static int DEFAULT_PRIORITY = 98;

    /**
     * Called by the game to create a new MouseClickedEvent.
     *
     * @param source the source mouse event originating from the AWT.
     */
    public MouseClickedEvent(java.awt.event.MouseEvent source) {
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