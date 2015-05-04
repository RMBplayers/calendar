package hkust.cse.calendar.unit;

import java.util.Collection;
import java.util.Vector;

public class MailBox {
	private Vector<Mail> receiveBox;
	private Vector<Mail> sendBox;
	
	// an empty MailBox
	public MailBox(){
		receiveBox = new Vector<Mail>();
		sendBox = new Vector<Mail>();
	}
	
	public Mail receiveMail(Mail s){
		receiveBox.add(s);
		return s;
	}
	
	// add mail to send box
	public Mail addMail(Mail s){
		sendBox.add(s);
		return s;
	}
	
	public Mail addMail(User s, User r, MailType t, String l, Collection<TimeSpan> c){
		Mail a = new Mail(s,r,t,l,c);
		sendBox.add(a);
		return a;
	}
	
	public void sendMail(){
		int i=0;
		while(i<sendBox.size()){
			Mail tmp = sendBox.get(i);
			//tmp.getReceiver().getMailBox().receiveMail(tmp);
		}
	}
	
	public Mail getFirstMail(){
		return receiveBox.remove(0);
	}
	
	public Vector<Mail> getMailList(){
		return receiveBox;
	}
	
	public void clearReceiveBox(){
		sendBox.clear();
	}
	
	public void clearSendBox(){
		receiveBox.clear();
	}
	
	// if one receive the all the reply of the mail, determine if schedule or not
	public void determineAppt(){
		Vector<Mail> reply = new Vector<Mail>();
		int i=0;
		Mail tmp;
		while(i<receiveBox.size()){
			tmp = receiveBox.get(i);
			if(tmp.getMailType()==MailType.invite){
				reply.add(tmp);
			}
		}
	}
	
	public Vector<Mail> findMailFromSendBox(Vector<TimeSpan> a){
		Vector<Mail> m = new Vector<Mail>();
		int i=0;
		Mail tmp;
		while(i<sendBox.size()){
			tmp = sendBox.get(i);
			if(tmp.getMailType()==MailType.invite && tmp.getTimeSlotList().equals(a)){
				m.add(tmp);
			}
		}
		return m;
	}
	
	public boolean isEmpty(){
		if (!receiveBox.isEmpty() || !sendBox.isEmpty()){
			return false;
		}
		return true;
		
	}

}