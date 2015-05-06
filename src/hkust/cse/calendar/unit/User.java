package hkust.cse.calendar.unit;

import java.io.Serializable;
import java.util.Vector;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mPassword;			   // User password
	private String mID;					   // User id
	private String mFirstname;              // firstname
	private String mLastname; 			   // lastname
	private String mEmail;				   // email
	private boolean admin;                 // User type
	
	private int requestNo;                 // increment every time user adds appt
										   // compose of userid to constitute the joint
	
	private Vector<Invitation> invitations;
	
	// Getter of the user id
	public String ID() {		
		return mID;
	}

	// Constructor of class 'User' which set up the user id and password
	public User(String id, String pass, String firstname, String lastname, String email, boolean isAdmin) {
		mID = id;
		mPassword = pass;
		mFirstname = firstname;
		mLastname = lastname;
		mEmail = email;
		admin = isAdmin;
		
		invitations = new Vector<Invitation>();
	}

	// Another getter of the user id
	public String toString() {
		return ID();
	}

	// Getter of the user password
	public String Password() {
		return mPassword;
	}

	// Setter of the user password
	public void Password(String pass) {
		mPassword = pass;
	}
	
	// Setter of the firstname
	public void firstname(String firstname) {
		mFirstname = firstname;
	}
	
	public String firstname(){
		return mFirstname;
	}
	
    // Setter of the lastname
	public void lastname(String lastname) {
		mLastname = lastname;
	}
	
	public String lastname(){
		return mLastname;
	}
	
	// Setter of the email
	public void mEmail(String email) {
		mEmail = email;
	}
	
	public String mEmail() {
		return mEmail;
	}
	
	// Setter of admin
	public void admin(boolean isadmin) {
		admin = isadmin;
	}
	
	// Getter of admin
	public boolean isAdmin() {
		return admin;
	}
	
	public void addInvitation(Appt appt, Vector<TimeSpan> timeslots) {
		Invitation newInvitation = new Invitation(appt,timeslots);
		System.out.println(mID);
		System.out.println("begin");
		System.out.println(invitations.size());
		invitations.add(newInvitation);
		System.out.println(invitations.size());
		System.out.println("end");
	}
	
	public Vector<Invitation> getInvitions() {
		return invitations;
	}
}
