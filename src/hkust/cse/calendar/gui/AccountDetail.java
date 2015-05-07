package hkust.cse.calendar.gui;

import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.unit.User;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class AccountDetail extends JFrame implements ActionListener {
	private JButton exitBut;
	private JTextArea area;
	
	public AccountDetail(User user) {
		// print content
		Container content = getContentPane();
		setTitle("Account");
		setAlwaysOnTop(true);
		
		JScrollPane panel = new JScrollPane();
		Border border = new TitledBorder(null, "Information");
		panel.setBorder(border);

		area = new JTextArea(25, 40);
//		area.setPreferredSize(new Dimension(400, 300));

		panel.getViewport().add(area);

		exitBut = new JButton("Exit");
		exitBut.addActionListener(this);

		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.CENTER));

		p2.add(exitBut);

		content.add("Center", panel);
		content.add("South", p2);
		
		this.setSize(500, 350);
		Display(user);
		pack();
		this.setVisible(true);
	}
	
	public void Display(User user) {

		area.setText("Account Information \n");
		area.append("Username: " + user.ID() + "\n");
		area.append("Firstname: " + user.firstname() + "\n");
		area.append("Lastname: " + user.lastname() +"\n");
		area.append("Email: " + user.mEmail()+ "\n");
		String userType;
		if (user.isAdmin()){
			userType = "Administrator";
		}else{
			userType = "Normal User";
		}
		area.append("Administrator: " + userType + "\n");
		area.setEditable(false);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == exitBut) {
			dispose();
		}
	}

}
