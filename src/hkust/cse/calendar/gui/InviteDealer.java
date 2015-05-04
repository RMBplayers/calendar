package hkust.cse.calendar.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Vector;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.unit.Invitation;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * deal with it invitations one by one
 * @author JX
 * buttons: Attend, Reject, Later
 */

public class InviteDealer extends JFrame {
	
	
	private JLabel information; //<!who where and which day
	private JLabel length; //<! the time length of the appointment
	
	private JCheckBox selectTime;
	
	private JButton attendButton;
	private JButton rejectButton;
	private JButton laterButton;
	
	public InviteDealer(final Vector<Invitation> invitations) {
		super();
		final Iterator<Invitation> it = invitations.iterator();
		
		Container contentPane = getContentPane(); 
		JPanel pInvite = new JPanel();
		Border inviteBorder = new TitledBorder(null, "Invite");
		pInvite.setBorder(inviteBorder);
		
		information = new JLabel(it.next().getInformation());// + it.next().getInitiator());
		pInvite.add(information);
		length = new JLabel("1");
		
		pInvite.add(length);
		selectTime = new JCheckBox();
		pInvite.add(selectTime);
		
		contentPane.add(pInvite, BorderLayout.NORTH);
		
		attendButton = new JButton("Attend");
		class aButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (invitations == null) dispose();
				//switch to next
				if(it.hasNext()) {
					it.remove();
					dispose();
					new InviteDealer(invitations);
				} else {
					it.remove();
					dispose();
				}
			}
		}
		final aButtonListener f = new aButtonListener();
		attendButton.addActionListener(f);
		
		rejectButton = new JButton("reject");
		class rButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (invitations == null) dispose();
				if(it.hasNext()) {
					it.remove();
					dispose();
					new InviteDealer(invitations);
				} else {
					it.remove();
					dispose();
				}
			}		
		}
		final rButtonListener f2 = new rButtonListener();
		rejectButton.addActionListener(f2);
		
		laterButton = new JButton("later");
		class lButtonListener implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (invitations == null) dispose();
				if(it.hasNext()) {
					it.remove();
					dispose();
					new InviteDealer(invitations);
				} else {
					it.remove();
					dispose();
				}
				// TODO Auto-generated method stub		
			}		
		}
		final lButtonListener f3 = new lButtonListener();
		laterButton.addActionListener(f3);
		
		JPanel button = new JPanel();
		
		button.add(attendButton);
		button.add(rejectButton);
		button.add(laterButton);
		
		contentPane.add(button, BorderLayout.SOUTH);
		
		setContentPane(contentPane);
		
		pack();
		setVisible(true);
	}
}
