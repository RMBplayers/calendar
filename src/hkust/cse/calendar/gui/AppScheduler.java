package hkust.cse.calendar.gui;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.Location;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.unit.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


public class AppScheduler extends JDialog implements ActionListener,
		ComponentListener {

	private JLabel yearL;
	private JTextField yearF;
	private JLabel monthL;
	private JTextField monthF;
	private JLabel dayL;
	private JTextField dayF;
	private JLabel sTimeHL;
	private JTextField sTimeH;
	private JLabel sTimeML;
	private JTextField sTimeM;
	private JLabel eTimeHL;
	private JTextField eTimeH;
	private JLabel eTimeML;
	private JTextField eTimeM;

	private JPanel reminderTime;
	// add jtextfield
	private JTextField TimeUnitd;
	private JTextField TimeUnith;
	private JTextField TimeUnitm;

	private JRadioButton reminderRB1;
	private JRadioButton reminderRB2;
	
	private DefaultListModel model;
	private JTextField titleField;

	private JButton saveBut;
	private JButton CancelBut;
	private JButton inviteBut;
	private JButton rejectBut;
	
	private JComboBox locationField;
	private JComboBox frequencyField;
	private JComboBox publicitySetter;
	
	private Appt NewAppt;
	private Appt ModifiedAppt;
	private CalGrid parent;
	private boolean isNew = true;
	private boolean isChanged = true;
	private boolean isJoint = false;
	boolean existReminder = false;
	private JTextArea detailArea;

	private JSplitPane pDes;
	JPanel detailPanel;

//	private JTextField attendField;
//	private JTextField rejectField;
//	private JTextField waitingField;
	private int selectedApptId = -1;
	

	private void commonConstructor(String title, CalGrid cal) {
		parent = cal;
		this.setAlwaysOnTop(true);
		setTitle(title);
		setModal(false);

		Container contentPane;
		contentPane = getContentPane();
		
		JPanel pDate = new JPanel();
		Border dateBorder = new TitledBorder(null, "DATE");
		pDate.setBorder(dateBorder);

		yearL = new JLabel("YEAR: ");
		pDate.add(yearL);
		yearF = new JTextField(6);
		pDate.add(yearF);
		monthL = new JLabel("MONTH: ");
		pDate.add(monthL);
		monthF = new JTextField(4);
		pDate.add(monthF);
		dayL = new JLabel("DAY: ");
		pDate.add(dayL);
		dayF = new JTextField(4);
		pDate.add(dayF);

		JPanel psTime = new JPanel();
		Border stimeBorder = new TitledBorder(null, "START TIME");
		psTime.setBorder(stimeBorder);
		sTimeHL = new JLabel("Hour");
		psTime.add(sTimeHL);
		sTimeH = new JTextField(4);
		psTime.add(sTimeH);
		sTimeML = new JLabel("Minute");
		psTime.add(sTimeML);
		sTimeM = new JTextField(4);
		psTime.add(sTimeM);

		JPanel peTime = new JPanel();
		Border etimeBorder = new TitledBorder(null, "END TIME");
		peTime.setBorder(etimeBorder);
		eTimeHL = new JLabel("Hour");
		peTime.add(eTimeHL);
		eTimeH = new JTextField(4);
		peTime.add(eTimeH);
		eTimeML = new JLabel("Minute");
		peTime.add(eTimeML);
		eTimeM = new JTextField(4);
		peTime.add(eTimeM);

		JPanel pTime = new JPanel();
		pTime.setLayout(new BorderLayout());
		pTime.add("West", psTime);
		pTime.add("East", peTime);

		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		top.setBorder(new BevelBorder(BevelBorder.RAISED));
		top.add(pDate, BorderLayout.NORTH);
		top.add(pTime, BorderLayout.CENTER);

		contentPane.add("North", top);

		JPanel titleAndTextPanel = new JPanel();
		JLabel titleL = new JLabel("TITLE");
		titleField = new JTextField(15);
		titleAndTextPanel.add(titleL);
		titleAndTextPanel.add(titleField);
		
		//added for creating combobox
		Vector<Location> locationVector = cal.controller.getLocationVector();
		if (locationVector == null){
			locationVector = new Vector<Location>();
		}
		Vector<String> nameVector = new Vector<String>();
		nameVector.add(" ");
		int i = 0;
		while (i < locationVector.size()) {
			nameVector.add(locationVector.get(i).getLocationName());
			++i;
		}
		JLabel locationLabel = new JLabel("LOCATION");
		locationField = new JComboBox<String>(nameVector);
		titleAndTextPanel.add(locationLabel);
		titleAndTextPanel.add(locationField);
		//add end
		
		
		//add frequency
		Vector<String> frequencyVector = new Vector<String>();
		frequencyVector.add("Once");
		frequencyVector.add("Daily");
		frequencyVector.add("Weekly");
		frequencyVector.add("Monthly");
		JLabel frequencyLabel = new JLabel("Frequency");
		frequencyField = new JComboBox<String>(frequencyVector);
		titleAndTextPanel.add(frequencyLabel);
		titleAndTextPanel.add(frequencyField);
		frequencyField.addActionListener(this);
		//finish of adding
		
		// add another line for set publicity
		JPanel publicityPanel = new JPanel();
		publicitySetter = new JComboBox<String>();
		publicitySetter.addItem(new String("PRIVATE"));
		publicitySetter.addItem(new String("PUBLIC"));
		publicitySetter.addActionListener(this);
		JLabel publicityLabel = new JLabel("Publicity");
		publicityPanel.add(publicityLabel);
		publicityPanel.add(publicitySetter);
		// add finish
		
		
		detailPanel = new JPanel();
		detailPanel.setLayout(new BorderLayout());
		Border detailBorder = new TitledBorder(null, "Appointment Description");
		detailPanel.setBorder(detailBorder);
		detailArea = new JTextArea(20, 30);

		detailArea.setEditable(true);
		JScrollPane detailScroll = new JScrollPane(detailArea);
		detailPanel.add(detailScroll);

		JSplitPane pre_pDes = new JSplitPane(JSplitPane.VERTICAL_SPLIT, publicityPanel,
				titleAndTextPanel);
		pDes = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pre_pDes,
				detailPanel);

		top.add(pDes, BorderLayout.SOUTH);

		if (NewAppt != null) {
			detailArea.setText(NewAppt.getInfo());

		}
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout(FlowLayout.RIGHT));

//		inviteBut = new JButton("Invite");
//		inviteBut.addActionListener(this);
//		panel2.add(inviteBut);
		
		// reminder
		JPanel reminder = new JPanel();
		JLabel reminderL = new JLabel("Reminder");
		reminder.add(reminderL);
		reminderRB1 = new JRadioButton("yes",false);
		reminderRB1.addActionListener(this);
		reminder.add(reminderRB1);
		reminderRB2 = new JRadioButton("no",true);
		reminderRB2.addActionListener(this);
		reminder.add(reminderRB2);
		ButtonGroup bgroup = new ButtonGroup();
		bgroup.add(reminderRB1);
		bgroup.add(reminderRB2);
		contentPane.add(reminder);
		
		// prepare reminderTime
		reminderTime = new JPanel();
		TimeUnitd = new JTextField(2);
		reminderTime.add(TimeUnitd);
		JLabel TimeUnitD = new JLabel("days");
		reminderTime.add(TimeUnitD);
		TimeUnith = new JTextField(2);
		reminderTime.add(TimeUnith);
		JLabel TimeUnitH = new JLabel("hours");
		reminderTime.add(TimeUnitH);
		TimeUnitm = new JTextField(2);
		reminderTime.add(TimeUnitm);
		JLabel TimeUnitM = new JLabel("minutes");
		reminderTime.add(TimeUnitM);
		
		
		
		saveBut = new JButton("Save");
		saveBut.addActionListener(this);
		panel2.add(saveBut);

		rejectBut = new JButton("Reject");
		rejectBut.addActionListener(this);
		panel2.add(rejectBut);
		rejectBut.show(false);

		CancelBut = new JButton("Cancel");
		CancelBut.addActionListener(this);
		panel2.add(CancelBut);

		contentPane.add("South", panel2);
		NewAppt = new Appt();

		if (this.getTitle().equals("Join Appointment Content Change") || this.getTitle().equals("Join Appointment Invitation")){
			inviteBut.show(false);
			rejectBut.show(true);
			CancelBut.setText("Consider Later");
			saveBut.setText("Accept");
		}
		if (this.getTitle().equals("Someone has responded to your Joint Appointment invitation") ){
			inviteBut.show(false);
			rejectBut.show(false);
			CancelBut.show(false);
			saveBut.setText("confirmed");
		}
		if (this.getTitle().equals("Join Appointment Invitation") || this.getTitle().equals("Someone has responded to your Joint Appointment invitation") || this.getTitle().equals("Join Appointment Content Change")){
			allDisableEdit();
		}
		pack();

	}
	
	AppScheduler(String title, CalGrid cal, int selectedApptId) {
		this.selectedApptId = selectedApptId;
		commonConstructor(title, cal);
	}

	AppScheduler(String title, CalGrid cal) {
		commonConstructor(title, cal);
	}
	
	public void actionPerformed(ActionEvent e) {

		// distinguish which button is clicked and continue with require function
		if (e.getSource() == CancelBut) {

			setVisible(false);
			dispose();
		} 
		else if (e.getSource() == saveBut) {
			saveButtonResponse();

		} 
		else if (e.getSource() == rejectBut) {
			if (JOptionPane.showConfirmDialog(this, "Reject this joint appointment?", "Confirmation", JOptionPane.YES_NO_OPTION) == 0){
				NewAppt.addReject(getCurrentUser());
				NewAppt.getAttendList().remove(getCurrentUser());
				NewAppt.getWaitingList().remove(getCurrentUser());
				this.setVisible(false);
				dispose();
			}
		} 
		// if click on "yes", then show the time schedule
		else if ( e.getSource() == reminderRB1 ) {
			existReminder = true;
			Container reminder = reminderRB1.getParent();
			Component[] components = reminder.getComponents();
		    for (Component component : components) {
		    	if (component == reminderTime) {
		    		return;
		    	}	
		    }
			reminder.add(reminderTime);
			reminder.validate();
			repaint();
		}
		// if click on "no", don't show 
		else if ( e.getSource() == reminderRB2 ) {
			existReminder = false;
			Container reminder = reminderRB2.getParent();
			reminder.remove(reminderTime);
			reminder.validate();
			repaint();
		}
		
		else if(e.getSource() == frequencyField){
			if(frequencyField.getSelectedIndex() == 0)
				NewAppt.setFrequency(0);
			else if(frequencyField.getSelectedIndex() == 1)
				NewAppt.setFrequency(1);
			else if(frequencyField.getSelectedIndex() == 2)
				NewAppt.setFrequency(2);
			else if(frequencyField.getSelectedIndex() == 3)
				NewAppt.setFrequency(3);
		}
		
		parent.getAppList().clear();
		parent.getAppList().setTodayAppt(parent.GetTodayAppt());
		parent.repaint();
		
		
			
	}

	private JPanel createPartOperaPane() {
		JPanel POperaPane = new JPanel();
		JPanel browsePane = new JPanel();
		JPanel controPane = new JPanel();

		POperaPane.setLayout(new BorderLayout());
		TitledBorder titledBorder1 = new TitledBorder(BorderFactory
				.createEtchedBorder(Color.white, new Color(178, 178, 178)),
				"Add Participant:");
		browsePane.setBorder(titledBorder1);

		POperaPane.add(controPane, BorderLayout.SOUTH);
		POperaPane.add(browsePane, BorderLayout.CENTER);
		POperaPane.setBorder(new BevelBorder(BevelBorder.LOWERED));
		return POperaPane;

	}

	private int[] getValidDate() {

		int[] date = new int[3];
		date[0] = Utility.getNumber(yearF.getText());
		date[1] = Utility.getNumber(monthF.getText());
		if (date[0] < 1980 || date[0] > 2100) {
			JOptionPane.showMessageDialog(this, "Please input proper year",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (date[1] <= 0 || date[1] > 12) {
			JOptionPane.showMessageDialog(this, "Please input proper month",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		date[2] = Utility.getNumber(dayF.getText());
		int monthDay = CalGrid.monthDays[date[1] - 1];
		if (date[1] == 2) {
			GregorianCalendar c = new GregorianCalendar();
			if (c.isLeapYear(date[0]))
				monthDay = 29;
		}
		if (date[2] <= 0 || date[2] > monthDay) {
			JOptionPane.showMessageDialog(this,
			"Please input proper month day", "Input Error",
			JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return date;
	}

	private int getTime(JTextField h, JTextField min) {

		int hour = Utility.getNumber(h.getText());
		if (hour == -1)
			return -1;
		int minute = Utility.getNumber(min.getText());
		if (minute == -1)
			return -1;

		return (hour * 60 + minute);

	}

	private int[] getValidTimeInterval() {

		int[] result = new int[2];
		result[0] = getTime(sTimeH, sTimeM);
		result[1] = getTime(eTimeH, eTimeM);
		if ((result[0] % 15) != 0 || (result[1] % 15) != 0) {
			JOptionPane.showMessageDialog(this,
					"Minute Must be 0, 15, 30, or 45 !", "Input Error",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		if (!sTimeM.getText().equals("0") && !sTimeM.getText().equals("15") && !sTimeM.getText().equals("30") && !sTimeM.getText().equals("45") 
			|| !eTimeM.getText().equals("0") && !eTimeM.getText().equals("15") && !eTimeM.getText().equals("30") && !eTimeM.getText().equals("45")){
			JOptionPane.showMessageDialog(this,
					"Minute Must be 0, 15, 30, or 45 !", "Input Error",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		if (result[1] == -1 || result[0] == -1) {
			JOptionPane.showMessageDialog(this, "Please check time",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (result[1] <= result[0]) {
			JOptionPane.showMessageDialog(this,
					"End time should be bigger than \nstart time",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if ((result[0] < AppList.OFFSET * 60)
				|| (result[1] > (AppList.OFFSET * 60 + AppList.ROWNUM * 2 * 15))) {
			JOptionPane.showMessageDialog(this, "Out of Appointment Range !",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		return result;
	}

	
	
	
	private void saveButtonResponse() {
		// create a new appointment
		int[] date = getValidDate();
		int[] time = getValidTimeInterval();
		if (date != null && time != null) {
			Timestamp start = CreateTimeStamp(date, time[0]);
			Timestamp end = CreateTimeStamp(date, time[1]);

			NewAppt.setTimeSpan(new TimeSpan(start,end));
			NewAppt.setTitle(titleField.getText());
			NewAppt.setInfo(detailArea.getText());
			NewAppt.setLocation(locationField.getSelectedItem().toString());
			
			// save the publicity
			if (publicitySetter.getSelectedItem().toString().equals("PRIVATE")){
				NewAppt.setPublicity(false);
			}
			else{
				NewAppt.setPublicity(true);
				//System.out.print("it is true");
				//
			}
			
			
			
			// end save
			
			
			
			
			// save the reminder to the appt
			// warning: haven't handle invalid input
			if (existReminder){
				try{
					//-------------------------------------------
					int d = Integer.parseInt(TimeUnitd.getText());
					int h = Integer.parseInt(TimeUnith.getText());
					int m = Integer.parseInt(TimeUnitm.getText());
					NewAppt.setReminder(d,h,m);
					}
					catch(NumberFormatException e1){
						JOptionPane.showMessageDialog(this, "please input proper reminder info",
								"Input Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			//-------------------------------------------
						
			// check if it is valid
						
			if (isvalid(NewAppt,getTitle())) {
					
				if (getTitle().equals("New")) {
				// add the new appt into AppStorage
					parent.controller.ManageAppt(NewAppt, 3);
				}
				
				// modify the appt into AppStorage
				else {
					parent.controller.ManageAppt(NewAppt, 2);
				}
					
				setVisible(false);
				dispose();
				parent.UpdateCal();
				// schedule the notification
				parent.controller.updateScheduleNotify();
			}
		}
		// Fix Me!
		// Save the appointment to the hard disk
			
	}
		
		

	private Timestamp CreateTimeStamp(int[] date, int time) {
		Timestamp stamp = new Timestamp(0);
		stamp.setYear(date[0]);
		stamp.setMonth(date[1] - 1);
		stamp.setDate(date[2]);
		stamp.setHours(time / 60);
		stamp.setMinutes(time % 60);
		return stamp;
	}

	private boolean isvalid(Appt appt,String method) {
		
	//		// replace system time with user defined time
		// check if the event is scheduled before current time
		GregorianCalendar today = new GregorianCalendar();
		if (appt.TimeSpan().StartTime().getTime() < today.getTimeInMillis()) {
			JOptionPane.showMessageDialog(this, "Events must happen after now",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		Appt[] Apptlist = parent.controller.RetrieveAppts(parent.mCurrUser, appt.TimeSpan());
		
		boolean isOverlap = parent.controller.checkOverlap(parent.mCurrUser, appt);
		
		// create an event
		if (method.equals("New")) {
			if (isOverlap == false) {
				return true;
			} else {
				JOptionPane.showMessageDialog(this, "New events overlaps in time",
						"Input Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		// modify an event
		else if (method.equals("Modify")) {
			if (Apptlist == null) {
				JOptionPane.showMessageDialog(this, "New event doesn't fit the original time slots",
						"Input Error", JOptionPane.ERROR_MESSAGE);
				return false;
			} else if (Apptlist.length == 1 && Apptlist[0] == ModifiedAppt) {
				return true;
			} else {
				JOptionPane.showMessageDialog(this, "New events overlaps in time",
						"Input Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	// display new added appointment
	public void updateSetApp(Appt appt) {
		Timestamp start = appt.TimeSpan().StartTime();
		Timestamp end = appt.TimeSpan().EndTime();
		yearF.setText("" + (start.getYear()+1900));
		monthF.setText("" + (start.getMonth()+1));
		dayF.setText("" + start.getDate());
		sTimeH.setText("" + start.getHours());
		sTimeM.setText("" + start.getMinutes());
		eTimeH.setText("" + end.getHours());
		eTimeM.setText("" + end.getMinutes());
		titleField.setText("" + appt.getTitle());
		detailArea.setText("" + appt.getInfo());
		ModifiedAppt = appt;
	}

	public void componentHidden(ComponentEvent e) {

	}

	public void componentMoved(ComponentEvent e) {

	}

	public void componentResized(ComponentEvent e) {

		Dimension dm = pDes.getSize();
		double width = dm.width * 0.93;
		double height = dm.getHeight() * 0.6;
		detailPanel.setSize((int) width, (int) height);

	}

	public void componentShown(ComponentEvent e) {

	}
	
	public String getCurrentUser()		// get the id of the current user
	{
		return this.parent.mCurrUser.ID();
	}
	
	private void allDisableEdit(){
		yearF.setEditable(false);
		monthF.setEditable(false);
		dayF.setEditable(false);
		sTimeH.setEditable(false);
		sTimeM.setEditable(false);
		eTimeH.setEditable(false);
		eTimeM.setEditable(false);
		titleField.setEditable(false);
		detailArea.setEditable(false);
	}
}
