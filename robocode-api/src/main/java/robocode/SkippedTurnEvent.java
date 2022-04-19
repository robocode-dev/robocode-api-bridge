package robocode;

/**
 * A SkippedTurnEvent is sent to {@link AdvancedRobot#onSkippedTurn(SkippedTurnEvent)
 * onSkippedTurn()} when your robot is forced to skipping a turn.
 * You must take an action every turn in order to participate in the game.
 * For example,
 * <pre>
 *    try {
 *        Thread.sleep(1000);
 *    } catch (InterruptedException e) {
 *        // Immediately reasserts the robocode.exception by interrupting the caller thread
 *        // itself.
 *        Thread.currentThread().interrupt();
 *    }
 * </pre>
 * will cause many SkippedTurnEvents, because you are not responding to the game.
 * If you receive 30 SkippedTurnEvents, you will be removed from the round.
 * <p>
 * Instead, you should do something such as:
 * <pre>
 *     for (int i = 0; i &lt; 30; i++) {
 *         doNothing(); // or perhaps scan();
 *     }
 * </pre>
 * <p>
 * This event may also be generated if you are simply doing too much processing
 * between actions, that is using too much processing power for the calculations
 * etc. in your robot.
 *
 * @see AdvancedRobot#onSkippedTurn(SkippedTurnEvent)
 * @see SkippedTurnEvent
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
@SuppressWarnings("unused") // API
public final class SkippedTurnEvent extends Event {
	private static final long serialVersionUID = 1L;
	private final static int DEFAULT_PRIORITY = 100; // System event -> cannot be changed!;

	private final long skippedTurn;

	/**
	 * Called by the game to create a new SkippedTurnEvent.
	 *
	 * @param skippedTurn the skipped turn
	 */
	public SkippedTurnEvent(long skippedTurn) {
		super();
		this.skippedTurn = skippedTurn;
	}

	/**
	 * Returns the turn that was skipped.
	 *
	 * @return the turn that was skipped.
	 *
	 * @since 1.7.2.0
	 */
	public long getSkippedTurn() {
		return skippedTurn;
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
	int getDefaultPriority() {
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