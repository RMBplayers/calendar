package hkust.cse.calendar.unit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.Serializable;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class Appt implements Serializable {

	public final static int onetime = 0;

	public final static int daily = 1;

	public final static int weekly = 2;
	
	public final static int monthly = 3;
	
	private TimeSpan mTimeSpan;					// Include day, start time and end time of the appointments

	private String mTitle;						// The Title of the appointments

	private String mInfo;						// Store the content of the appointments description

	private int mApptID;						// The appointment id
	
	private int joinApptID;						// The join appointment id

	private boolean isjoint;					// The appointment is a joint appointment
	
	private LinkedList<String> attend;			// The Attendant list
	
	private LinkedList<String> reject;			// The reject list
	
	private LinkedList<String> waiting;			// The waiting list
	
	private Location location;         			// The location of the event
	
	private Reminder reminder;					// the reminder of the event ---------------------- newly added
	
	private int frequency;   		            // frequency
	
	private boolean publicity;					// check if could be seen by others
	
	public Appt() {								// A default constructor used to set all the attribute to default values
		mApptID = 0;
		mTimeSpan = null;
		mTitle = "Untitled";
		mInfo = "";
		isjoint = false;
		attend = new LinkedList<String>();
		reject = new LinkedList<String>();
		waiting = new LinkedList<String>();
		joinApptID = -1;
		reminder = new Reminder();
		frequency = onetime;
		publicity = false;
	}

	// Getter of the mTimeSpan
	public TimeSpan TimeSpan() {
		return mTimeSpan;
	}
	
	// Getter of the appointment title
	public String getTitle() {
		return mTitle;
	}

	// Getter of appointment description
	public String getInfo() {
		return mInfo;
	}

	// Getter of the appointment id
	public int getID() {
		return mApptID;
	}
	
	// Getter of the join appointment id
	public int getJoinID(){
		return joinApptID;
	}

	public void setJoinID(int joinID){
		this.joinApptID = joinID;
	}
	// Getter of the attend LinkedList<String>
	public LinkedList<String> getAttendList(){
		return attend;
	}
	
	// Getter of the reject LinkedList<String>
	public LinkedList<String> getRejectList(){
		return reject;
	}
	
	// Getter of the waiting LinkedList<String>
	public LinkedList<String> getWaitingList(){
		return waiting;
	}
	
	public LinkedList<String> getAllPeople(){
		LinkedList<String> allList = new LinkedList<String>();
		allList.addAll(attend);
		allList.addAll(reject);
		allList.addAll(waiting);
		return allList;
	}
	
	public void addAttendant(String addID){
		if (attend == null)
			attend = new LinkedList<String>();
		attend.add(addID);
	}
	
	public void addReject(String addID){
		if (reject == null)
			reject = new LinkedList<String>();
		reject.add(addID);
	}
	
	public void addWaiting(String addID){
		if (waiting == null)
			waiting = new LinkedList<String>();
		waiting.add(addID);
	}
	
	public void setWaitingList(LinkedList<String> waitingList){
		waiting = waitingList;
	}
	
	public void setWaitingList(String[] waitingList){
		LinkedList<String> tempLinkedList = new LinkedList<String>();
		if (waitingList !=null){
			for (int a=0; a<waitingList.length; a++){
				tempLinkedList.add(waitingList[a].trim());
			}
		}
		waiting = tempLinkedList;
	}
	
	public void setRejectList(LinkedList<String> rejectLinkedList) {
		reject = rejectLinkedList;
	}
	
	public void setRejectList(String[] rejectList){
		LinkedList<String> tempLinkedList = new LinkedList<String>();
		if (rejectList !=null){
			for (int a=0; a<rejectList.length; a++){
				tempLinkedList.add(rejectList[a].trim());
			}
		}
		reject = tempLinkedList;
	}
	
	public void setAttendList(LinkedList<String> attendLinkedList) {
		attend = attendLinkedList;
	}
	
	public void setAttendList(String[] attendList){
		LinkedList<String> tempLinkedList = new LinkedList<String>();
		if (attendList !=null){
			for (int a=0; a<attendList.length; a++){
				tempLinkedList.add(attendList[a].trim());
			}
		}
		attend = tempLinkedList;
	}
	// Getter of the appointment title
	public String toString() {
		return mTitle;
	}

	// Setter of the appointment title
	public void setTitle(String t) {
		mTitle = t;
	}

	// Setter of the appointment description
	public void setInfo(String in) {
		mInfo = in;
	}

	// Setter of the mTimeSpan
	public void setTimeSpan(TimeSpan d) {
		mTimeSpan = d;
	}

	// Setter if the appointment id
	public void setID(int id) {
		mApptID = id;
	}
	
	// check whether this is a joint appointment
	public boolean isJoint(){
		return isjoint;
	}

	// setter of the isJoint
	public void setJoint(boolean isjoint){
		this.isjoint = isjoint;
	}

	public void setLocation(String name) {
		location = new Location(name);
	}
	
	public Location getLocation() {
		return location;
	}

	public String getLocationName() {
		return location.getLocationName();
	}
	
	// this part is for reminder
	// the first function is used to set reminder
	public void setReminder(int d, int h, int m){
		reminder.setInfo(d,h,m);
	}
	
	// check if there is reminder
		public boolean existReminder(){
			return reminder.isExist();
		}
		
		// add get reminder time in millisecond
		public long getReminderTime(){
			long timeInMiliSeconds = 0;
			timeInMiliSeconds = (this.reminder.getInMinutes())*60*1000;
			return timeInMiliSeconds;
		}
		
		// add get reminder
		public Reminder getReminder(){
			return this.reminder;
		}
		
		// create a notify frame of the current appt
		public void createNotifyFrame(){
			JFrame f = new JFrame("Notify");
			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			String prompt = new String();
			prompt = "title: "+ mTitle + "\n";
			JLabel textLabel = new JLabel(prompt,SwingConstants.CENTER);
			textLabel.setPreferredSize(new Dimension(300,100));
			f.getContentPane().add(textLabel,BorderLayout.CENTER);
			
			// Display the window
			f.setLocationRelativeTo(null);
			f.pack();
			f.setVisible(true);
		}

	
	public void setFrequency(int i) {
		frequency = i;
	}
	
	public int getFrequency() {
		return frequency;
	}
	
	public void setPublicity(boolean a){
		publicity = a;
	}
	
	public boolean getPublicity(){
		return this.publicity;
	}
}