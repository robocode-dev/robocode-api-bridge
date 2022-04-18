package robocode.robotinterfaces;

import robocode.BattleEndedEvent;
import robocode.RoundEndedEvent;

/**
 * First extended version of the {@link IBasicEvents} interface.
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 *
 * @since 1.6.1
 */
public interface IBasicEvents2 extends IBasicEvents {

	/**
	 * This method is called after the end of the battle, even when the battle is aborted.
	 * You should override it in your robot if you want to be informed of this event.
	 * <p>
	 * Example:
	 * <pre>
	 *   public void onBattleEnded(BattleEndedEvent event) {
	 *       out.println("The battle has ended");
	 *   }
	 * </pre>
	 *
	 * @param event the BattleEndedEvent set by the game
	 * @see BattleEndedEvent
	 * @see IBasicEvents3#onRoundEnded(RoundEndedEvent)
	 * @see robocode.WinEvent
	 * @see robocode.DeathEvent
	 * @see robocode.Event
	 * 
	 * @since 1.6.1
	 */
	void onBattleEnded(BattleEndedEvent event);
}
