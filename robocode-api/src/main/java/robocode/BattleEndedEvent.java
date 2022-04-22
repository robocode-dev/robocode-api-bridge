package robocode;

/**
 * A BattleEndedEvent is sent to {@link Robot#onBattleEnded(BattleEndedEvent)
 * onBattleEnded()} when the battle is ended.
 * You can use the information contained in this event to determine if the
 * battle was aborted and also get the results of the battle.
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 * @see BattleResults
 * @see Robot#onBattleEnded(BattleEndedEvent)
 * @since 1.6.1
 */
@SuppressWarnings("unused") // API
public final class BattleEndedEvent extends Event {

    private final static int DEFAULT_PRIORITY = 100; // System event -> cannot be changed!

    private final boolean aborted;
    private final BattleResults results;

    /**
     * Called by the game to create a new BattleEndedEvent.
     *
     * @param aborted {@code true} if the battle was aborted; {@code false} otherwise.
     * @param results the battle results
     */
    public BattleEndedEvent(boolean aborted, BattleResults results) {
        this.aborted = aborted;
        this.results = results;
    }

    /**
     * Checks if this battle was aborted.
     *
     * @return {@code true} if the battle was aborted; {@code false} otherwise.
     */
    public boolean isAborted() {
        return aborted;
    }

    /**
     * Returns the battle results.
     *
     * @return the battle results.
     */
    public BattleResults getResults() {
        return results;
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