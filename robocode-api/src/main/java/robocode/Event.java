package robocode;

import java.io.Serializable;

/**
 * The superclass of all Robocode events.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
public abstract class Event implements Comparable<Event>, Serializable {
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_PRIORITY = 80;

	private long time;
	private int priority;

	// time is valid only after adding to event manager on proxy side, we do not update it on battle side
	private transient boolean addedToQueue;

	/**
	 * Default constructor used by the game to create a new event.
	 */
	public Event() {
		super();
	}

	/**
	 * Compares this event to another event regarding precedence.
	 * The event precedence is first and foremost determined by the event time,
	 * secondly the event priority, and lastly specific event information.
	 * <p>
	 * This method will first compare the time of each event. If the event time
	 * is the same for both events, then this method compared the priority of
	 * each event. If the event priorities are equals, then this method will
	 * compare the two event based on specific event information.
	 * <p>
	 * This method is called by the game in order to sort the event queue of a
	 * robot to make sure the events are listed in chronological order.
	 *
	 * @param event the event to compare to this event.
	 * @return a negative value if this event has higher precedence, i.e. must
	 *         be listed before the specified event. A positive value if this event
	 *         has a lower precedence, i.e. must be listed after the specified event.
	 *         0 means that the precedence of the two events are equal.
	 */
	public int compareTo(Event event) {
		// Compare the time difference which has precedence over priority.
		int timeDiff = (int) (time - event.time);

		if (timeDiff != 0) {
			return timeDiff; // Time differ
		}

		// Same time -> Compare the difference in priority
		int priorityDiff = event.getPriority() - getPriority();

		return priorityDiff; // Priority differ

		// Same time and priority -> Compare specific event types
		// look at overrides in ScannedRobotEvent and HitRobotEvent
	}

	/**
	 * Returns the time when this event occurred.
	 * <p>
	 * Note that the time is equal to the turn of a battle round.
	 *
	 * @return the time when this event occurred.
	 */
	// Note: We rolled back the use of 'final' here due to Bug [2928688], where some old robots do override getTime()
	// with their own events that inherits from robocode.Event.
	public /* final*/long getTime() {
		return time;
	}

	/**
	 * Changes the time when this event occurred.
	 * <p>
	 * Note that the time is equal to the turn of a battle round.
	 * <p>
	 * This method is intended for letting robot developers create their own event types.
	 * It is not possible to change the time of an event after it has been added to the event
	 * queue of the robot.
	 *
	 * @param newTime the time this event occurred
	 */
	public void setTime(long newTime) {
		if (addedToQueue) {
			Logger.printlnToRobotsConsole(
					"SYSTEM: The time of an event cannot be changed after it has been added the event queue.");
		} else {
			time = newTime;
		}
	}

	/**
	 * Returns the priority of this event.
	 * <p>
	 * An event priority is a value from 0 - 99. The higher value, the higher priority.
	 * <p>
	 * The default priority is 80, but varies depending on the type of event.
	 *
	 * @return the priority of this event.
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Changes the priority of this event.
	 * An event priority is a value from 0 - 99. The higher value, the higher priority.
	 * <p>
	 * The default priority is 80, but varies depending on the type of event.
	 * <p>
	 * This method is intended for letting robot developers create their own event types.
	 * It is not possible to change the priority of an event after it has been added to the event
	 * queue of the robot.
	 * 
	 * @param newPriority the new priority of this event
	 * @see AdvancedRobot#setEventPriority(String, int)
	 */
	public final void setPriority(int newPriority) {
		if (addedToQueue) {
			Logger.printlnToRobotsConsole(
					"SYSTEM: The priority of an event cannot be changed after it has been added the event queue.");
		} else {
			setPriorityHidden(newPriority);
		}
	}

	/**
	 * Hidden method for setting the exact time when this event occurred.
	 * <p>
	 * <strong>Notice:</strong> This method is called by the game engine only.
	 *
	 * @param time the time when this event occurred. 
	 */
	// This method must be invisible on Robot API
	private void setTimeHidden(long time) {
		// we do not replace time which is set by robot to the future 
		if (this.time < time) {
			this.time = time;
		}
		// when this flag is set, robots are not allowed to change the time or priority of the event anymore
		addedToQueue = true;
	}

	/**
	 * Hidden method for setting the priority from the game engine without checking for the 'addedToQueue' flag.
	 * <p>
	 * <strong>Notice:</strong> This method is called by the game engine only.
	 *
	 * @param newPriority the new priority of this event.
	 */
	// This method must be invisible on Robot API
	private void setPriorityHidden(int newPriority) {
		if (newPriority < 0) {
			Logger.printlnToRobotsConsole("SYSTEM: Priority must be between 0 and 99");
			Logger.printlnToRobotsConsole("SYSTEM: Priority for " + this.getClass().getName() + " will be 0");
			newPriority = 0;
		} else if (newPriority > 99) {
			Logger.printlnToRobotsConsole("SYSTEM: Priority must be between 0 and 99");
			Logger.printlnToRobotsConsole("SYSTEM: Priority for " + this.getClass().getName() + " will be 99");
			newPriority = 99;
		}
		priority = newPriority;
	}

	/**
	 * Returns the default priority of this event class.
	 */
	// This method must be invisible on Robot API
	int getDefaultPriority() {
		return DEFAULT_PRIORITY;
	}

	/**
	 * Checks if this event must be delivered even after timeout.
	 *
	 * @return {@code true} when this event must be delivered even after timeout;
	 *         {@code false} otherwise.
	 */
	// This method must be invisible on Robot API
	boolean isCriticalEvent() {
		return false;
	}
}
