package robocode;

/**
 * A RoundEndedEvent is sent to {@link Robot#onRoundEnded(RoundEndedEvent)
 * onRoundEnded()} when a round has ended.
 * You can use the information contained in this event to determine which round that has ended.
 *
 * @see Robot#onRoundEnded(RoundEndedEvent)
 *
 * @author Flemming N. Larsen (original)
 *
 * @since 1.7.2
 */
@SuppressWarnings("unused") // API
public final class RoundEndedEvent extends Event {
	private static final long serialVersionUID = 1L;
	private final static int DEFAULT_PRIORITY = 110; // System event -> cannot be changed!

	private final int round;
	private final int turns;
	private final int totalTurns;

	/**
	 * Called by the game to create a new RoundEndedEvent.
	 *
	 * @param round the round that has ended (zero-indexed).
	 * @param turns the number of turns that this round reached.
	 * @param totalTurns the total number of turns reached in the battle when this round ended.
	 */
	public RoundEndedEvent(int round, int turns, int totalTurns) {
		this.round = round;
		this.turns = turns;
		this.totalTurns = totalTurns;
	}

	/**
	 * Returns the round that ended (zero-indexed).
	 *
	 * @return the round that ended (zero-indexed).
	 */
	public int getRound() {
		return round;
	}

	/**
	 * Returns the number of turns that this round reached. 
	 *
	 * @return the number of turns that this round reached.
	 */
	public int getTurns() {
		return turns;
	}

	/**
	 * Returns the total number of turns reached in the battle when this round ended. 
	 *
	 * @return the total number of turns reached in the battle when this round ended. 
	 */
	public int getTotalTurns() {
		return totalTurns;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	int getDefaultPriority() {
		return DEFAULT_PRIORITY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPriority() {
		return DEFAULT_PRIORITY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	boolean isCriticalEvent() {
		return true;
	}
}