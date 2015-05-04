package hkust.cse.calendar.unit;

import java.util.Collection;
import java.util.Vector;

public class Mail{
	private User sender;
	private User receiver;
	private MailType type;
	private String location;
	private Vector<TimeSpan> timeSlotList; 
	
	public Mail(User s, User r, MailType t, String l, Collection<TimeSpan> c){
		sender = s;
		receiver = r;
		type = t;
		location = l;
		timeSlotList = new Vector<TimeSpan>(c);
	}
	
	public User getSender(){
		return this.sender;
	}
	
	public User getReceiver(){
		return this.receiver;
	}
	
	public MailType getMailType(){
		return this.type;
	}
	
	public void setMailType(MailType a){
		type = a;
		return; 
	}
	
	public String getLocation(){
		return this.location;
	}
	
	public Vector<TimeSpan> getTimeSlotList(){
		return this.timeSlotList;
	}
	
	public boolean belongTo(Mail a){
		if (this.location == a.getLocation() && this.receiver == a.getSender() && this.sender == a.getReceiver()){
			
		}
		return true;
	}
	
}
