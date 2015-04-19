package hkust.cse.calendar.unit;

import java.util.GregorianCalendar;

/**
 * \class UserTime
 * define own clock for users that can be set with users own definition
 * interface planned at SetClock.java
 * will add entry at Calgrid.java
 * 
 * \data:
 * systemTime, timeDiff, userTime
 * meaning obvious from definition 
 * 
 * \Author: JX
 * \Date:28-3-2015
 */

public class UserTime {
	public GregorianCalendar sysCalendar;
	
	/**
	 * \ import system time and set it as an origin point
	 */
	public final long systemTime = sysCalendar.getTime().getTime();
	
	/**
	 * \ user-defined time interval
	 */
	private long timeDiff;
	
	/**
	 * \equals systemTime + timeDiff
	 */
	private long userTime;
	
	/**
	 * \constructor for no time intervals
	 */
	public UserTime() {
		timeDiff = 0;
		userTime = systemTime;
	}
	
	/**
	 * \constructor 
	 * set timeDiff and calculate usertime
	 */
	public UserTime(long time) {
		timeDiff = time;
		userTime = systemTime + timeDiff;
	}
	
	/**
	 * \ update User Time when necessary
	 */
	public void updateUserTime() {
		userTime = systemTime + timeDiff;
	}
	
	//set and get functions
	//in set function, user should (intuitively) pass user time
	public void setUserTime(long time) {
		userTime = time;
		timeDiff = time - systemTime;
	}
	
	//should we first update the clock and then return the time?
	public long getUserTime(long time) {
		updateUserTime();
		return userTime;
	}
}

//EOF
