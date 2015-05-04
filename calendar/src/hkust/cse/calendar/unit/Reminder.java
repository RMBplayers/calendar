package hkust.cse.calendar.unit;

import java.io.Serializable;
import java.util.TimerTask;

public class Reminder implements Serializable{
	
	// three key data in reminder
	private int days = 0;
	private int hours = 0;
	private int minutes = 0;
	private boolean exists = false;
	
	
	public Reminder(){
	}
	
	public Reminder(int d, int h, int m){
		setInfo(d, h, m);
	}
	
	public boolean setInfo(int d, int h, int m){
		// assume days is between 0-99, hours is between 0-23, minutes is between 0-59
		// it will be handled before input
		days = d;
		hours = h;
		minutes = m;
		exists = true;
		return true;
	}
	
	public long getInMinutes(){
		long TimeInMinutes = minutes + hours*60 + days*60;
		return TimeInMinutes;
	}
	
	public boolean isExist(){
		return exists;
	}
}