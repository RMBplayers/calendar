package hkust.cse.calendar.unit;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;

import java.io.Serializable;
import java.util.Vector;


/**
 * \class invitations
 * @author JX YQ
 * a invitation that can be responded
 */
public class Invitation implements Serializable {
//	private int joinApptID;   //each invitation can only have one id
//	private String Username;    //each invitation to on user so that can be deleted easily
	private Appt appt;        //detail information of appt
	private Vector<TimeSpan> Timeslots;  // user can choose from
	
	public Invitation(Appt app, Vector<TimeSpan> timeslots) {
		appt = app;
		Timeslots = timeslots;
	}
	
	public void joinAppt() {
		
	}
	
	public void rejectAppt() {
		
	}
	
	public Vector<TimeSpan> TimeSpan() {
		return Timeslots;
	}
	
	public Appt getAppt() {
		return appt;
	}
}
