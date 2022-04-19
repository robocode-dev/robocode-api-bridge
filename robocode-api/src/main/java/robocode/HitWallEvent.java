package robocode;

/**
 * A HitWallEvent is sent to {@link Robot#onHitWall(HitWallEvent) onHitWall()}
 * when you collide a wall.
 * You can use the information contained in this event to determine what to do.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
@SuppressWarnings("unused") // API
public final class HitWallEvent extends Event {
	private static final long serialVersionUID = 1L;
	private final static int DEFAULT_PRIORITY = 30;

	private final double bearing;

	/**
	 * Called by the game to create a new HitWallEvent.
	 *
	 * @param bearing the bearing to the wall that your robot hit, in radians
	 */
	public HitWallEvent(double bearing) {
		this.bearing = bearing;
	}

	/**
	 * Returns the bearing to the wall you hit, relative to your robot's
	 * heading, in degrees (-180 &lt;= getBearing() &lt; 180)
	 *
	 * @return the bearing to the wall you hit, in degrees
	 */
	public double getBearing() {
		return bearing * 180.0 / Math.PI;
	}

	/**
	 * @return the bearing to the wall you hit, in degrees
	 * @deprecated Use {@link #getBearing()} instead.
	 */
	@Deprecated
	public double getBearingDegrees() {
		return getBearing();
	}

	/**
	 * Returns the bearing to the wall you hit, relative to your robot's
	 * heading, in radians (-PI &lt;= getBearingRadians() &lt; PI)
	 *
	 * @return the bearing to the wall you hit, in radians
	 */
	public double getBearingRadians() {
		return bearing;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	int getDefaultPriority() {
		return DEFAULT_PRIORITY;
	}
}