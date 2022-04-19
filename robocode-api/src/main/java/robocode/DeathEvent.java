package robocode;

/**
 * This event is sent to {@link Robot#onDeath(DeathEvent) onDeath()} when your
 * robot dies.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
@SuppressWarnings("unused") // API
public final class DeathEvent extends Event {
    private final static int DEFAULT_PRIORITY = -1; // System event -> cannot be changed!

    /**
     * Called by the game to create a new DeathEvent.
     */
    public DeathEvent() {
        super();
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