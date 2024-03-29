package robocode;

import java.io.Serializable;

/**
 * A MessageEvent is sent to {@link TeamRobot#onMessageReceived(MessageEvent)
 * onMessageReceived()} when a teammate sends a message to your robot.
 * You can use the information contained in this event to determine what to do.
 *
 * @author Mathew A. Nelson (original)
 */
@SuppressWarnings("unused") // API
public final class MessageEvent extends Event {

    private final static int DEFAULT_PRIORITY = 75;

    private final String sender;
    private final Serializable message;

    /**
     * Called by the game to create a new MessageEvent.
     *
     * @param sender  the name of the sending robot
     * @param message the message for your robot
     */
    public MessageEvent(String sender, Serializable message) {
        this.sender = sender;
        this.message = message;
    }

    /**
     * Returns the name of the sending robot.
     *
     * @return the name of the sending robot
     */
    public String getSender() {
        return sender;
    }

    /**
     * Returns the message itself.
     *
     * @return the message
     */
    public Serializable getMessage() {
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    int getDefaultPriority() {
        return DEFAULT_PRIORITY;
    }
}