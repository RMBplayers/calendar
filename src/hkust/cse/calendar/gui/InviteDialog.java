package hkust.cse.calendar.gui;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.BooleanIndicator;
import hkust.cse.calendar.unit.Invitation;
import hkust.cse.calendar.unit.Location;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.unit.User;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

//to dos: don't indicate user itself

public class InviteDialog extends JFrame {
	/**
	 * \private controller
	 */
	private ApptStorageControllerImpl _controller;
	//private Appt _appt;
	
    private DefaultListModel<String> userNameListModel; // a name list to store name map
    private JList<String> userNameList;
    
    private DefaultListModel<String> inviteModel;
    private JList<String> inviteList;
	
	private JButton inviteButton;
	private JButton anotherDayButton;
	
	private JLabel yearL;
	private JTextField yearF;
	private JLabel monthL;
	private JTextField monthF;
	private JLabel dayL;
	private JTextField dayF;
	
	private Appt tempAppt;
	
	private JLabel lengthL;
	JTextField lengthF;
	
	private Vector<Integer> yearArray;
	private Vector<Integer> monthArray;
	private Vector<Integer> dayArray;
	private Vector<TimeSpan> timeSpans;
	
	/*
	 * \dialogue to set location
	 * \conversion constructor form controller
	 */
	public InviteDialog(ApptStorageControllerImpl controller, Appt appt) {
		
		super();
		_controller = controller;
		//_appt = appt;
		tempAppt = appt;
		//_controller.setLocationVector(new Vector<Location>());
	
		Container contentPane = getContentPane();
		JPanel pInvite = new JPanel();
		Border inviteBorder = new TitledBorder(null, "INVITE OTHERS");
		pInvite.setBorder(inviteBorder);
		
		yearL = new JLabel("YEAR: ");
		pInvite.add(yearL);
		yearF = new JTextField(6);
		pInvite.add(yearF);
		monthL = new JLabel("MONTH: ");
		pInvite.add(monthL);
		monthF = new JTextField(4);
		pInvite.add(monthF);
		dayL = new JLabel("DAY: ");
		pInvite.add(dayL);
		dayF = new JTextField(4);
		pInvite.add(dayF);
		
		yearArray = new Vector<Integer>();
		monthArray = new Vector<Integer>();
		dayArray = new Vector<Integer>();
		
		//pInvite.add(d);
		//dateVector.add(d);
		lengthL = new JLabel("LENGTH: ");
		lengthF = new JTextField(4);
		pInvite.add(lengthL);
		pInvite.add(lengthF);
		
		timeSpans = new Vector<TimeSpan>();
		
		/**
		 * this is the cross on the top right
		 */
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//_controller.setLocationVector(_controller.getLocationVector());
				exit(0);
			}
		});
		
		
		userNameListModel = new DefaultListModel<String>();
		//for each in set add
		inviteModel = new DefaultListModel<String>();
		
		Set<String> userSet = _controller.getAllUsers();
		Iterator<String> it = userSet.iterator();
	    while (it.hasNext()) {
	    	//not sure if the loop correct
	    	userNameListModel.addElement(it.next());
	    	//it.remove();
		}
	    userNameListModel.removeElement(_controller.getDefaultUser().toString());
	    
	    JScrollPane scrollName = new JScrollPane();
	    JScrollPane scrollInvite = new JScrollPane();
	    
	    userNameList = new JList<String>(userNameListModel);
		inviteList = new JList<String>(inviteModel);
		
		userNameList.setVisibleRowCount(6);
		scrollName.setViewportView(userNameList);
		
		inviteList.setVisibleRowCount(6);
		scrollInvite.setViewportView(inviteList);
		
		userNameList.setPreferredSize(new Dimension(100, 200));
		inviteList.setPreferredSize(new Dimension(100, 200));
		
		//userNameList.setFixedCellWidth(20);
		//inviteList.setFixedCellWidth(20);
		//userNameList.setVisibleRowCount(8);
		//inviteList.setVisibleRowCount(8);
	    
		userNameList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (userNameList.getModel().getSize() == 0) {
					userNameList.setEnabled(false);
				}// else {
					//userNameList.setEnabled(true);
				//}
				String userToInvite = userNameList.getSelectedValue();
				if (e.getValueIsAdjusting() == false) {
					if (!inviteModel.contains(userToInvite)){
						inviteModel.addElement(userToInvite);
						//userNameListModel.removeElement(userToInvite);
					}
				}
			}		
		});
		
		inviteList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				inviteList.setEnabled(false);
				String userNotInvite = inviteList.getSelectedValue();
				if (e.getValueIsAdjusting() == false) {
					userNameListModel.addElement(userNotInvite);
					inviteModel.removeElement(userNotInvite);
				}
			}
		});
		
		
		final JPanel top, leftList, botButton, rightList;
		top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		//top.setBorder(new BevelBorder(BevelBorder.RAISED));
		top.add(pInvite);
		leftList = new JPanel(); 
		botButton = new JPanel();
		rightList = new JPanel();
		
		inviteButton = new JButton("invite");
		
		class inviteButtonListener implements ActionListener {
		    @Override
			public void actionPerformed(ActionEvent e) {
		    	if ((getValidDate()!=null) && (getValidLength() != 0) && (extractInviteList() != null)) {
		    		int tempYear =  getValidDate()[0];
		    		int tempMonth = getValidDate()[1];
		    		int tempDay = getValidDate()[2];
		    	
		    		yearArray.add(tempYear);
		    		monthArray.add(tempMonth);
		    		dayArray.add(tempDay);
//		    		currentTimeSpans(Integer.parseInt(lengthF.getText()));
				
		    		//Appt newAppt = new Appt();
		    		SelectTime s = new SelectTime(extractAllTimeSpans(extractInviteList(),getValidLength()), extractInviteList(), tempAppt, _controller);
		    		s.setVisible(true);
		    	//selectTimeStamps();
		    	//dispose();
		    	}
			} 		
		}
		final inviteButtonListener ivbl = new inviteButtonListener();
		inviteButton.addActionListener(ivbl);
		
		anotherDayButton = new JButton("add date");
		
		class aButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				/*
				JPanel pInvite2 = new JPanel();
				Border inviteBorder2 = new TitledBorder(null, "Another day");
				pInvite2.setBorder(inviteBorder2);
				
				JLabel yearL2 = new JLabel("YEAR: ");
				pInvite2.add(yearL2);
				JTextField yearF2 = new JTextField(6);
				pInvite2.add(yearF2);
				JLabel monthL2 = new JLabel("MONTH: ");
				pInvite2.add(monthL2);
				JTextField monthF2 = new JTextField(4);
				pInvite2.add(monthF2);
				JLabel dayL2 = new JLabel("DAY: ");
				pInvite2.add(dayL2);
				
				JTextField dayF2 = new JTextField(4);
				pInvite2.add(dayF2);
				*/
				
				/**
				 * \this part will be judging useful information
				 * \need to show valid
				 */
				
				int date[] = getValidDate();
				
				yearArray.add(date[0]);
				monthArray.add(date[1]);
				dayArray.add(date[2]);
				
				userNameList.setEnabled(false);
				
				lengthF.setEnabled(false);
				
				yearF.setText(null);
				monthF.setText(null);
				dayF.setText(null);
				lengthF.enableInputMethods(false);
				validate();
				repaint();
			}
		}
		
		final aButtonListener abl = new aButtonListener();
		anotherDayButton.addActionListener(abl);
		
		leftList.add(new JLabel("user to invite"));
		leftList.add(scrollName);
		
		rightList.add(new JLabel("invite list"));
		rightList.add(scrollInvite);
		
		botButton.add(inviteButton);
		botButton.add(anotherDayButton);
		contentPane.add(top, BorderLayout.NORTH);
		contentPane.add(leftList, BorderLayout.WEST);
		contentPane.add(rightList, BorderLayout.EAST);
		contentPane.add(botButton, BorderLayout.SOUTH);
		
		setContentPane(contentPane);
		
		pack();
		setVisible(true);
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
	
	private int getValidLength() {
		int length = Utility.getNumber(lengthF.getText());
		if ((length <= 0) || (length % 15 != 0)) {
			JOptionPane.showMessageDialog(this,
					"Please input valid time length.", "Input Error",
					JOptionPane.ERROR_MESSAGE);
			return 0;
		} else return length;
	}
	
	public LinkedList<String> extractInviteList() {
		LinkedList<String> receivers = new LinkedList<String>();
		for (int i = 0; i < inviteModel.size(); i++) {
			receivers.add(inviteModel.get(i));
		}
		if (receivers.size() == 0) {
			return null;
		} else return receivers;
	}
	
	public Vector<TimeSpan> currentTimeSpans(int lengthInMinutes) {
		Vector<TimeSpan> answer = new Vector<TimeSpan>();
		for(int i = 0; i<yearArray.size(); ++i) {
			//do one day
			int tempYear = yearArray.elementAt(i);
			int tempMonth = monthArray.elementAt(i);
			int tempDay = dayArray.elementAt(i);
			
			long beginInMillisecond = (new GregorianCalendar(tempYear, tempMonth, tempDay, 8, 0, 0)).getTimeInMillis();
			Timestamp begin = new Timestamp(beginInMillisecond);
			Timestamp end = new Timestamp(beginInMillisecond+lengthInMinutes*60*1000);
			while (end.getTime() <= (new GregorianCalendar(tempYear, tempMonth, tempDay, 18, 0, 0)).getTimeInMillis()) {
				
				TimeSpan t = new TimeSpan(begin, end);
				tempAppt.setTimeSpan(t);
				//tempAppt.set frequency
				
				if(!_controller.checkOverlap(_controller.getDefaultUser(),tempAppt)) {
					answer.add(t);
				}
				beginInMillisecond = beginInMillisecond+15*60*1000;
				begin = new Timestamp(beginInMillisecond);
				end = new Timestamp(beginInMillisecond+lengthInMinutes*60*1000);
			}
		}
		System.out.println(answer.size());
		return answer;
	}
	
	/**
	 * extracts all possible timespans
	 * for the initiator to select.
	 * @return
	 */
	public Vector<TimeSpan> extractAllTimeSpans(LinkedList<String> users, int lengthInMinutes) {
		Vector<TimeSpan> answer = this.currentTimeSpans(lengthInMinutes);
		for (int i = 0; i < users.size(); i++) {
			Appt tempAppt = new Appt();
			for (int j = 0; j < answer.size(); j++) {
				TimeSpan t = answer.get(j);
				tempAppt.setTimeSpan(t);
				if(_controller.checkOverlap(_controller.getUser(users.get(i)),tempAppt)) {
					answer.remove(t);
					System.out.println(answer.size());
				}
			}
		}
		System.out.println(answer.size());
		return answer;
	}
	
	/**
	 * \several parts to be done in this function
	 *\1. get I/O and days, see util/datePanel.java
	 *\\2. generate joinapptid, by composing the userid and requestNo (see User.java)
	 * 3. fetch valid date, see util/BooleanIndicator.java
	 * 4. set final timespan for the appt
	 * 
	 * 5. modify the location so that location capacity can be set
	 * 
	 * \to generate group appt, the method is generate an appt without timespan, and remove it if cannot be
	 *  dealt with properly
	 */
	
	public void sendInvitation() {
		LinkedList<String> receivers = extractInviteList();
		Iterator<String> it = receivers.iterator();
		Appt newAppt = new Appt();
		
		newAppt.setWaitingList(receivers);
		//newAppt.setJoinID(_controller.getDefaultUser().toString()+Integer.toString(_controller.getDefaultUser().getRequestNo()));
		//while(it.hasNext()) {
			//Invitation i = new Invitation(yearF.getText() + monthF.getText() + dayF.getText(), _controller.getDefaultUser().toString());
			//_controller.getUser(it.next()).addInvitation(i);
			/*
			Iterator<datePanel> itp = dateVector.iterator();
			while (itp.hasNext()) {
				i = new Invitation(itp.next().yearF.getText() + itp.next().monthF.getText() + itp.next().dayF.getText(), _controller.getDefaultUser().toString());
				_controller.getUser(it.next()).addInvitation(i);
			}
			*/
	}

	protected void exit(int i) {
		// TODO Auto-generated method stub	
	}
}
