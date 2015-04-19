package hkust.cse.calendar.apptstorage;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.unit.User;
import hkust.cse.calendar.unit.Location;

/* This class is for managing the Appt Storage according to different actions */
public class ApptStorageControllerImpl {

	/* Remove the Appt from the storage */
	public final static int REMOVE = 1;

	/* Modify the Appt the storage */
	public final static int MODIFY = 2;

	/* Add a new Appt into the storage */
	public final static int NEW = 3;
	
	/*
	 * Add additional flags which you feel necessary
	 */
	
	/* The Appt storage */
	private ApptStorage mApptStorage;

	/* Create a new object of ApptStorageControllerImpl from an existing storage of Appt */
	public ApptStorageControllerImpl(ApptStorage storage) {
		mApptStorage = storage;
	}

	/* Retrieve the Appt's in the storage for a specific user within the specific time span */
	public Appt[] RetrieveAppts(User entity, TimeSpan time) {
		return mApptStorage.RetrieveAppts(entity, time);
	}

	// overload method to retrieve appointment with the given joint appointment id
	public Appt RetrieveAppts(int joinApptID) {
		return mApptStorage.RetrieveAppts(joinApptID);
	}
	
	/* Manage the Appt in the storage
	 * parameters: the Appt involved, the action to take on the Appt */
	public void ManageAppt(Appt appt, int action) {

		if (action == NEW) {				// Save the Appt into the storage if it is new and non-null
			if (appt == null)
				return;
			mApptStorage.SaveAppt(appt);
		} else if (action == MODIFY) {		// Update the Appt in the storage if it is modified and non-null
			if (appt == null)
				return;
			mApptStorage.UpdateAppt(appt);
		} else if (action == REMOVE) {		// Remove the Appt from the storage if it should be removed
			mApptStorage.RemoveAppt(appt);
		} 
	}

	/* Get the defaultUser of mApptStorage */
	public User getDefaultUser() {
		return mApptStorage.getDefaultUser();
	}
	
	public void setDefaultUser(User user) {
		mApptStorage.setDefaultUser(user);
	}

	// method used to load appointment from xml record into hash map
	public void LoadApptFromXml(){
		mApptStorage.LoadApptFromXml();
	}
	
	/*
	 * \
	 */
	public Vector<Location> getLocationVector() {
		return mApptStorage.getLocationVector();
	}
	
	public void setLocationVector(Vector<Location> locationVector) {
		mApptStorage.setLocationVector(locationVector);
	}
	
	public void addLocationToVector(Location location) {
		if (this.getLocationVector() == null) {
			this.setLocationVector(new Vector<Location>());
		}
		mApptStorage.addLocationToVector(location);
	}
	
	public void removeLocationFromVector(Location location) {
		mApptStorage.removeLocationFromVector(location);
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public void setTime(Timestamp t) {
		mApptStorage.setTime(t);
	}
	
	public Date getTime() {
		return mApptStorage.getTime();
	}
	
	
	
	/**
	* this input the programmer-desired time and give user time for them to use
	* @param xueShen
	* @return systemTime
	*/
	public final Date getSystemTime(Date xueShen) {
		return mApptStorage.getSystemTime(xueShen);
	}
	
	// update the schedule here 
	public void updateScheduleNotify(){
		mApptStorage.scheduleApptsAll();
	}
	
	public boolean checkOverlap(User user, Appt appt) {
		return mApptStorage.checkOverlap(user, appt);
	}
	
	// verify user info
	public boolean verifyUser(String username, String password) {
		return mApptStorage.verifyUser(username,password);
	}
}
