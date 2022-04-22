package robocode;

/**
 * This event is sent to {@link Robot#onBulletHit(BulletHitEvent) onBulletHit}
 * when one of your bullets has hit another robot.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
@SuppressWarnings("unused") // API
public final class BulletHitEvent extends Event {

    private final static int DEFAULT_PRIORITY = 50;

    private final String name;
    private final double energy;
    private final Bullet bullet;

    /**
     * Called by the game to create a new {@code BulletHitEvent}.
     *
     * @param name   the name of the robot your bullet hit
     * @param energy the remaining energy of the robot that your bullet has hit
     * @param bullet the bullet that hit the robot
     */
    public BulletHitEvent(String name, double energy, Bullet bullet) {
        super();
        this.name = name;
        this.energy = energy;
        this.bullet = bullet;
    }

    /**
     * Returns the bullet of yours that hit the robot.
     *
     * @return the bullet that hit the robot
     */
    public Bullet getBullet() {
        return bullet;
    }

    /**
     * Returns the remaining energy of the robot your bullet has hit (after the
     * damage done by your bullet).
     *
     * @return energy the remaining energy of the robot that your bullet has hit
     */
    public double getEnergy() {
        return energy;
    }

    /**
     * @return energy the remaining energy of the robot that your bullet has hit
     * @deprecated Use {@link #getEnergy()} instead.
     */
    @Deprecated
    public double getLife() {
        return energy;
    }

    /**
     * Returns the name of the robot your bullet hit.
     *
     * @return the name of the robot your bullet hit.
     */
    public String getName() {
        return name;
    }

    /**
     * @return energy the remaining energy of the robot that your bullet has hit
     * @deprecated Use {@link #getEnergy()} instead.
     */
    @Deprecated
    public double getRobotLife() {
        return energy;
    }

    /**
     * @return the name of the robot your bullet hit.
     * @deprecated Use {@link #getName()} instead.
     */
    @Deprecated
    public String getRobotName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    int getDefaultPriority() {
        return DEFAULT_PRIORITY;
    }
}