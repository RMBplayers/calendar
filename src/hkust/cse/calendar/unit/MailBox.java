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
		while(!sendBox.isEmpty()){
			Mail tmp = sendBox.remove(0);
			tmp.getReceiver().getMailBox().addMail(tmp);
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
	
	public boolean isEmpty(){
		if (!receiveBox.isEmpty() || !sendBox.isEmpty()){
			return false;
		}
		return true;
		
	}

}