package hkust.cse.calendar.apptstorage;//

import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.Location;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.unit.User;
import hkust.cse.calendar.unit.UserTimer;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.Timer;


public abstract class ApptStorage {

	public HashMap<String,Vector<Appt>> mAppts;		// a hashmap to save every thing to it, write to memory by the memory based storage implementation	
	public HashMap<String, User> users;           // save user info
	public User defaultUser;	//a user object, now is single user mode without login
	public int mAssignedApptID;	//a global appointment ID for each appointment record
	/////////////////////////////////////////////////////////////////////////////////////////	
	public Vector<Location> apptLocation; //can I initialize here?
	
	//add following three commands to java code
	public UserTimer newTime = new UserTimer();
	
	public Timer taskScheduler = new Timer();
		
	public abstract void setTime(Timestamp t);
		
	public abstract Date getTime();
		
	public abstract Date getSystemTime(Date d);

	/*
	* \get the list of location
	*/
	public abstract Vector<Location> getLocationVector();
	
	/*
	* \set the list of location
	*/
	public abstract void setLocationVector(Vector<Location> locationVector);
	
	public abstract void addLocationToVector(Location location);
	
	public abstract void removeLocationFromVector(Location location);
	
	/////////////////////////////////////////////////////////////////////////////////////	
	public ApptStorage() {	//default constructor
	mAppts = new HashMap<String,Vector<Appt>>();
	apptLocation = new Vector<Location>(0);
	users = new HashMap<String,User>();
	}

	public abstract void SaveAppt(Appt appt);	//abstract method to save an appointment record

	public abstract Appt[] RetrieveAppts(TimeSpan d);	//abstract method to retrieve an appointment record by a given timespan

	public abstract Appt[] RetrieveAppts(User entity, TimeSpan time);	//overloading abstract method to retrieve an appointment record by a given user object and timespan
	
	public abstract Appt RetrieveAppts(int joinApptID);					// overload method to retrieve appointment with the given joint appointment id

	public abstract void UpdateAppt(Appt appt);	//abstract method to update an appointment record

	public abstract void RemoveAppt(Appt appt);	//abstract method to remove an appointment record
	
	public abstract User getDefaultUser();		//abstract method to return the current user object
	
	public abstract void setDefaultUser(User user);      //abstract method to set the current user object
	
	public abstract void LoadApptFromXml();		//abstract method to load appointment from xml reocrd into hash map
	
	/*
	 * Add other methods if necessary
	 */
	public abstract void scheduleApptsAll();
	
	public abstract boolean checkOverlap(User entity, Appt appt); // check event overlap
	
	public abstract boolean verifyUser(String username, String password); // verify user info
	
	public abstract User getUser(String username);
	
	public abstract void addUser(User user);
}
