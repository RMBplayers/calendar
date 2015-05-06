package hkust.cse.calendar.unit;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;

import java.io.Serializable;
import java.util.Vector;


/**
 * \class invitations
 * @author JX
 * a invitation that can be responded
 */
public class Invitation implements Serializable {
	private int joinApptID;   //each invitation can only have one id
	private String UserID;    //each invitation to on user so that can be deleted easily
	private ApptStorageControllerImpl controller;
	
	public Invitation(int apptid, String userid) {
		joinApptID = apptid;
		UserID = userid;
	}
	
	public void joinAppt() {
		
	}
	
	public void rejectAppt() {
		//
	}
	/*
	public Vector<TimeSpan> selectTime() {
		;
	}
	
	public String getInformation() {
		//get the information ;
	}
	*/
}
