package robocode;

import java.io.Serializable;

/**
 * Represents a bullet. This is returned from {@link Robot#fireBullet(double)}
 * and {@link AdvancedRobot#setFireBullet(double)}, and all the bullet-related
 * events.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @see Robot#fireBullet(double)
 * @see AdvancedRobot#setFireBullet(double)
 * @see BulletHitEvent
 * @see BulletMissedEvent
 * @see BulletHitBulletEvent
 */
@SuppressWarnings("unused") // API
public class Bullet implements Serializable {

    protected double headingRadians;
    protected double x;
    protected double y;
    protected double power;
    protected String ownerName;
    protected String victimName;
    protected boolean isActive;
    protected int bulletId;

    protected Bullet() {} // for inheritance

    /**
     * Called by the game to create a new {@code Bullet} object
     *
     * @param heading    the heading of the bullet, in radians.
     * @param x          the starting X position of the bullet.
     * @param y          the starting Y position of the bullet.
     * @param power      the power of the bullet.
     * @param ownerName  the name of the owner robot that owns the bullet.
     * @param victimName the name of the robot hit by the bullet.
     * @param isActive   {@code true} if the bullet still moves; {@code false} otherwise.
     * @param bulletId   unique id of bullet for owner robot.
     */
    public Bullet(double heading, double x, double y, double power, String ownerName, String victimName, boolean isActive, int bulletId) {
        this.headingRadians = heading;
        this.x = x;
        this.y = y;
        this.power = power;
        this.ownerName = ownerName;
        this.victimName = victimName;
        this.isActive = isActive;
        this.bulletId = bulletId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // bulletId is unique to single round and robot owner
        return bulletId == ((Bullet) obj).bulletId;
    }

    @Override
    public int hashCode() {
        return bulletId;
    }

    /**
     * Returns the direction the bullet is/was heading, in degrees
     * (0 &lt;= getHeading() &lt; 360). This is not relative to the direction you are
     * facing.
     *
     * @return the direction the bullet is/was heading, in degrees
     */
    public double getHeading() {
        return Math.toDegrees(headingRadians);
    }

    /**
     * Returns the direction the bullet is/was heading, in radians
     * (0 &lt;= getHeadingRadians() &lt; 2 * Math.PI). This is not relative to the
     * direction you are facing.
     *
     * @return the direction the bullet is/was heading, in radians
     */
    public double getHeadingRadians() {
        return headingRadians;
    }

    /**
     * Returns the name of the robot that fired this bullet.
     *
     * @return the name of the robot that fired this bullet
     */
    public String getName() {
        return ownerName;
    }

    /**
     * Returns the power of this bullet.
     * <p>
     * The bullet will do (4 * power) damage if it hits another robot.
     * If power is greater than 1, it will do an additional 2 * (power - 1)
     * damage. You will get (3 * power) back if you hit the other robot.
     *
     * @return the power of the bullet
     */
    public double getPower() {
        return power;
    }

    /**
     * Returns the velocity of this bullet. The velocity of the bullet is
     * constant once it has been fired.
     *
     * @return the velocity of the bullet
     */
    public double getVelocity() {
        return Rules.getBulletSpeed(power);
    }

    /**
     * Returns the name of the robot that this bullet hit, or {@code null} if
     * the bullet has not hit a robot.
     *
     * @return the name of the robot that this bullet hit, or {@code null} if
     * the bullet has not hit a robot.
     */
    public String getVictim() {
        return victimName;
    }

    /**
     * Returns the X position of the bullet.
     *
     * @return the X position of the bullet
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the Y position of the bullet.
     *
     * @return the Y position of the bullet
     */
    public double getY() {
        return y;
    }

    /**
     * Checks if this bullet is still active on the battlefield.
     *
     * @return {@code true} if the bullet is still active on the battlefield;
     * {@code false} otherwise
     */
    public boolean isActive() {
        return isActive;
    }
}