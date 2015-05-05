package hkust.cse.calendar.unit;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;

/**
 * this is a boolean indicator to indicate whether a user is available at a specified day
 * for universal judging usage, please set user to null
 * @author JX
 *
 * usage: call mergeAll() with proper input
 *  and call result for a answering vector
 */
public class BooleanIndicator {
	private User user;
	private int year;
	private int month;
	private int day;
	private boolean[] indArray;  //!<for each grid, true if the timeslot is notoccupied
	private ApptStorageControllerImpl controller;
	
	private long begin;
	
	/**
	 * \constructor
	 */
	public BooleanIndicator(User u, int y, int m, int d) {
		user = u;
		year = y;
		month = m;
		day = d;
		indArray = new boolean[40];
		Arrays.fill(indArray, false);
		begin = (new GregorianCalendar(year, month, day, 8, 0, 0)).getTimeInMillis();
	}
	
	/**
	 * calculate formula for each grid
	 * @return
	 */
	private void calculateForward() {
		Timestamp start = new Timestamp((new GregorianCalendar(year, month, day, 8, 0, 0)).getTimeInMillis());
		
		Timestamp t1 = new Timestamp((new GregorianCalendar(year, month, day, 8, 0, 0)).getTimeInMillis());
		Timestamp t2 = new Timestamp((new GregorianCalendar(year, month, day, 18, 0, 0)).getTimeInMillis());
		Timestamp temp1 = start;
		
		Appt[] apptArray = controller.RetrieveAppts(user, new TimeSpan(t1, t2));
		int length = apptArray.length;
		
		for (int i = 0; i<length; ++i) {
			Timestamp temp2 = apptArray[i].getStartTime();
			int i1 = (int) (temp1.getTime() - start.getTime())/(15*60*1000);
			int i2 = (int) (temp2.getTime() - start.getTime())/(15*60*1000);
			for(int j = i1; j < i2; ++j) {
				indArray[j] = false;
			}
			temp1 = apptArray[i].getEndTime();
		}
	}
	
	public boolean[] getIndArray() {
		return indArray;
	}
	
	
	/**
	 * this function make it possible to compare two person's plan
	 * and merge them together
	 * to compare all users, call for loop
	 * @param arrayB
	 */
	private void mergeWithOther(User user2) {
		boolean[] arrayB = (new BooleanIndicator(user2, year, month, day)).getIndArray();
		for (int i = 0; i<40; i++) {
				/**
				 * \this is a problematic part
				 */
				indArray[i] = indArray[i] && indArray[i];
		}
	}
	
	public void mergeAll(List<String> names) {
		Iterator<String> it = names.iterator();
		while (it.hasNext()) {
			mergeWithOther(controller.getUser(it.next()));
		}
	}
	
	public Vector<TimeSpan> result(int minutes) {
		Vector<TimeSpan> answer = new Vector<TimeSpan>();
		int blocks = minutes/15;
		for(int i = 0; i<40-blocks+1; ++i) {
			boolean temp = true;
			for (int j = i; j<i+blocks; ++j) {
				temp = temp && indArray[j];
			}
						
			if (temp) {
				Timestamp t1 = new Timestamp(begin + i*15*60*1000);
				Timestamp t2 = new Timestamp(begin + (i+blocks)*15*60*1000);
				answer.add(new TimeSpan(t1,t2));
			}		
		}
		return answer;		
	}
}
