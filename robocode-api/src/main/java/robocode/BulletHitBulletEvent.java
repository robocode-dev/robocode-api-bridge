package robocode;

/**
 * This event is sent to {@link Robot#onBulletHitBullet(BulletHitBulletEvent)
 * onBulletHitBullet} when one of your bullets has hit another bullet.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
@SuppressWarnings("unused") // API
public final class BulletHitBulletEvent extends Event {
    private static final long serialVersionUID = 1L;
    private final static int DEFAULT_PRIORITY = 55;

    private final Bullet bullet;
    private final Bullet hitBullet;

    /**
     * Called by the game to create a new {@code BulletHitEvent}.
     *
     * @param bullet    your bullet that hit another bullet
     * @param hitBullet the bullet that was hit by your bullet
     */
    public BulletHitBulletEvent(Bullet bullet, Bullet hitBullet) {
        super();
        this.bullet = bullet;
        this.hitBullet = hitBullet;
    }

    /**
     * Returns your bullet that hit another bullet.
     *
     * @return your bullet
     */
    public Bullet getBullet() {
        return bullet;
    }

    /**
     * Returns the bullet that was hit by your bullet.
     *
     * @return the bullet that was hit
     */
    public Bullet getHitBullet() {
        return hitBullet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    int getDefaultPriority() {
        return DEFAULT_PRIORITY;
    }
}
