package hkust.cse.calendar.unit;

import java.io.Serializable;


/**
 * \class invitations
 * @author JX
 * a invitation that can be responded
 */
public class Invitation implements Serializable {
	private int status;
	private String information;
	private String initiator;
	
	//1-waiting, 2-approve, 0-reject
	
	public Invitation(String s, String i) {
		information = s;
		initiator = i;
		status = 1;
	}
	
	public void Approve() {
		status = 2;
	}
	
	public void Reject() {
		status = 0;
	}
	
	public String getInformation() {
		return information;
	}
	
	public String getInitiator() {
		return initiator;
	}
}
