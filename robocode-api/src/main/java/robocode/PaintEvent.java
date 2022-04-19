package robocode;

import java.awt.Graphics2D;

/**
 * This event occurs when your robot should paint, where the {@link
 * Robot#onPaint(Graphics2D) onPaint()} is called on your robot.
 * <p>
 * You can use this event for setting the event priority by calling
 * {@link AdvancedRobot#setEventPriority(String, int)
 * setEventPriority("PaintEvent", priority)}
 *
 * @author Flemming N. Larsen (original)
 */
@SuppressWarnings("unused") // API
public final class PaintEvent extends Event {

    private final static int DEFAULT_PRIORITY = 5;

    /**
     * Called by the game to create a new PaintEvent.
     */
    public PaintEvent() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    int getDefaultPriority() {
        return DEFAULT_PRIORITY;
    }
}