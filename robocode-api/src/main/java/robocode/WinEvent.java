package robocode;

/**
 * This event is sent to {@link Robot#onWin(WinEvent) onWin()} when your robot
 * wins the round in a battle.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
@SuppressWarnings("unused") // API
public final class WinEvent extends Event {

    private final static int DEFAULT_PRIORITY = 100; // System event -> cannot be changed!

    /**
     * Called by the game to create a new WinEvent.
     */
    public WinEvent() {
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