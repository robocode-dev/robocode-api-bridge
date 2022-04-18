package robocode.robotinterfaces;

import robocode.MessageEvent;

/**
 * An event interface for receiving robot team events with an
 * {@link ITeamRobot}.
 *
 * @see ITeamRobot
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 *
 * @since 1.6
 */
public interface ITeamEvents {

	/**
	 * This method is called when your robot receives a message from a teammate.
	 * You should override it in your robot if you want to be informed of this
	 * event.
	 * <p>
	 * Example:
	 * <pre>
	 *   public void onMessageReceived(MessageEvent event) {
	 *       out.println(event.getSender() + " sent me: " + event.getMessage());
	 *   }
	 * </pre>
	 *
	 * @param event the message event sent by the game
	 * @see MessageEvent
	 * @see robocode.Event
	 */
	void onMessageReceived(MessageEvent event);
}
