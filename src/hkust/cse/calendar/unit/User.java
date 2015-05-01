package hkust.cse.calendar.unit;

import java.io.Serializable;

public class User implements Serializable {

	private String mPassword;				// User password
	private String mID;						// User id
	private boolean admin;                // User type
	
	// Getter of the user id
	public String ID() {		
		return mID;
	}

	// Constructor of class 'User' which set up the user id and password
	public User(String id, String pass, boolean isAdmin) {
		mID = id;
		mPassword = pass;
		admin = isAdmin;
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
	
	public boolean isAdmin() {
		return admin;
	}
}
