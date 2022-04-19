package robocode;

/**
 * This event is sent to {@link Robot#onBulletMissed(BulletMissedEvent)
 * onBulletMissed} when one of your bullets has missed, i.e. when the bullet has
 * reached the border of the battlefield.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
public final class BulletMissedEvent extends Event {
	private static final long serialVersionUID = 1L;
	private final static int DEFAULT_PRIORITY = 60;

	private final Bullet bullet;

	/**
	 * Called by the game to create a new {@code BulletMissedEvent}.
	 *
	 * @param bullet the bullet that missed
	 */
	public BulletMissedEvent(Bullet bullet) {
		this.bullet = bullet;
	}

	/**
	 * Returns the bullet that missed.
	 *
	 * @return the bullet that missed
	 */
	public Bullet getBullet() {
		return bullet;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	int getDefaultPriority() {
		return DEFAULT_PRIORITY;
	}
}
