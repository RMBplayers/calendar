package hkust.cse.calendar.unit;

import java.io.Serializable;

public class User implements Serializable {

	private String mPassword;			   // User password
	private String mID;					   // User id
	private String mFirstname;              // firstname
	private String mLastname; 			   // lastname
	private String mEmail;				   // email
	private boolean admin;                 // User type
	private MailBox mailBox;			   // mailbox which used to receive invite
	
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
		mailBox = new MailBox();		// an empty mailbox
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
	
    // Setter of the lastname
	public void lastname(String lastname) {
		mLastname = lastname;
	}
	
	// Setter of the email
	public void mEmail(String email) {
		mEmail = email;
	}
	
	// Setter of admin
	public void admin(boolean isadmin) {
		admin = isadmin;
	}
	
	// Getter of admin
	public boolean isAdmin() {
		return admin;
	}
	
	public MailBox getMailBox(){
		return this.mailBox;
	}
}
