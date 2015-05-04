package hkust.cse.calendar.unit;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author JX
 * \class UserTimer
 * \get and set offset
 */
public class UserTimer implements Serializable{
	private GregorianCalendar newTime;
	private GregorianCalendar normalTime;
	private long diff = 0;
	public UserTimer() {
		newTime = new GregorianCalendar();
		normalTime = new GregorianCalendar();
	}
	
	/**
	 * constructor, set the time to new date
	 * @param rollDate
	 */
	public UserTimer(Timestamp rollDate) {
		newTime = new GregorianCalendar(rollDate.getYear(), rollDate.getMonth(), rollDate.getDate(),
				rollDate.getHours(), rollDate.getMinutes(), rollDate.getSeconds());
		normalTime = new GregorianCalendar();
	}
	
	public void setTime(Timestamp rollDate) {
		newTime.set(rollDate.getYear(), rollDate.getMonth(), rollDate.getDate(),
				rollDate.getHours(), rollDate.getMinutes(), rollDate.getSeconds());
		setDiff(newTime.getTimeInMillis() - (new GregorianCalendar()).getTimeInMillis());
	}

	private void setDiff(long l) {
		diff = l;
	}
	
	private long getDiff() {
		return diff; //newTime.getTimeInMillis() - (new GregorianCalendar()).getTimeInMillis();
	}
	
	
	public Date getTime() {
		//return new Date(newTime.getTimeInMillis());
		return new Date((new GregorianCalendar()).getTimeInMillis() + getDiff());
	}
	

	/**
	 * \get a Date for time
	 * directly go to system time for timer task
	 * @return
	 */
	public Date getSystemTime(Date actualTime) {
		return new Date(actualTime.getTime() - getDiff());
	}
}
