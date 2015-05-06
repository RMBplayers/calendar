package hkust.cse.calendar.gui;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.unit.User;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * \class SelectTime
 * @author JX
 * provide a JComboBox to select time
 * and a button to make sure of this selection
 */
public class SelectTime extends JFrame {
	
	private JPanel panel;
	
	private LinkedList<String> inviteList;
	private JButton confirmButton;
	private HashMap<JCheckBox, TimeSpan> timeMap;
	private Vector<JCheckBox> checkBox;
	private Appt appt;
	
	private ApptStorageControllerImpl controller;
	
	public SelectTime(Vector<TimeSpan> times, Appt a, ApptStorageControllerImpl cont) {
		super();
		appt = a;
		Container contentPane = getContentPane();
		Vector<String> timeSymbol = new Vector<String>();
		Iterator<TimeSpan> it = times.iterator();
		timeMap = new HashMap<JCheckBox, TimeSpan> ();
		controller = cont;
		
		checkBox = new Vector<JCheckBox>();
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		while (it.hasNext()) {
			TimeSpan t = it.next();
			JCheckBox jc = new JCheckBox(t.toString());
			timeMap.put(jc, t);
			checkBox.add(jc);
			panel.add(jc);
		}
		
		JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(50, 30, 300, 50);
        //panel.setPreferredSize(new Dimension(200,200));
		
		//timeBox = new JComboBox(timeSymbol);
		
		//panel.add(timeBox);
		
		confirmButton = new JButton("Confirm");
		class confirmButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				//update appt information method
				//if u wish to send use the next constructor
			}
		}
			
		final confirmButtonListener cbl = new confirmButtonListener();
		confirmButton.addActionListener(cbl);
		
		panel.add(confirmButton);
		
		contentPane.add(scrollPane, BorderLayout.CENTER);
		this.setContentPane(contentPane);
		
		pack();
		setVisible(true);
	}
	
	public SelectTime(Vector<TimeSpan> times, final LinkedList<String> i, Appt a, ApptStorageControllerImpl cont) {
		super();
		inviteList = i;
		appt = a;
		Container contentPane = getContentPane();
		Vector<String> timeSymbol = new Vector<String>();
		Iterator<TimeSpan> it = times.iterator();
		timeMap = new HashMap<JCheckBox, TimeSpan> ();
		controller = cont;
		
		checkBox = new Vector<JCheckBox>();
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		while (it.hasNext()) {
			TimeSpan t = it.next();
			JCheckBox jc = new JCheckBox(t.toString());
			timeMap.put(jc, t);
			checkBox.add(jc);
			panel.add(jc);
		}
		
		JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(50, 30, 300, 50);
        //panel.setPreferredSize(new Dimension(200,200));
		
		//timeBox = new JComboBox(timeSymbol);
		
		//panel.add(timeBox);
		
		confirmButton = new JButton("Confirm");
		class confirmButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//String[] selectedTime = (String[]) timeBox.getSelectedObjects();
				//USERTOBEINVITED.addInvitation(Appt appt, getTimeSlots()>)
				for (int j = 0; j < inviteList.size(); j++) {
					User user = controller.getUser(inviteList.get(j));
					user.addInvitation(appt,getTimeSlots());
					controller.addUser(user);
				}
				dispose();
				
				
				//System.out.println(getTimeSlots().size());
			}
		}
			
		final confirmButtonListener cbl = new confirmButtonListener();
		confirmButton.addActionListener(cbl);
		
		panel.add(confirmButton);
		
		contentPane.add(scrollPane, BorderLayout.CENTER);
		this.setContentPane(contentPane);
		
		pack();
		setVisible(true);
	}	
	
	// check null 
	public Vector<TimeSpan> getTimeSlots() {
		Iterator<JCheckBox> itCheck = checkBox.iterator();
		Vector<TimeSpan> answer = new Vector<TimeSpan>();
		while(itCheck.hasNext()) {
			JCheckBox temp = itCheck.next();
			if (temp.isSelected()) {
				answer.add(timeMap.get(temp));
			}
		}
		return answer;
	}
}

/*
@yiquan if u want to make use of the selecttime tool for initiator you can call the second constructor

however if you want to send reply just use first one(unimplemented)
*/