package hkust.cse.calendar.gui;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.apptstorage.ApptStorageNullImpl;
import hkust.cse.calendar.unit.User;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class EditAccountInfoDialog extends JFrame implements ActionListener {
	
	
	
	private Container contentPane;
	private JTextField firstname;
	private JTextField lastname;
	private JPasswordField oldPassword;
	private JPasswordField password;
	private JPasswordField password2;
	private JTextField email;
	private JButton confirmButton;
	private JButton cancelButton;
	private CalGrid parent;
	
	EditAccountInfoDialog(CalGrid pa){
		parent = pa;
		setTitle("sign up");
		contentPane = getContentPane();
		contentPane.removeAll();
		
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

		JPanel namePanel = new JPanel();
		namePanel.add(new JLabel(" Name                            "));
		firstname = new JTextField(7);
		lastname = new JTextField(7);
		namePanel.add(firstname);
		namePanel.add(lastname);
		top.add(namePanel);
		
		JPanel oldPwPanel = new JPanel();
		oldPwPanel.add(new JLabel("input old password       "));
		oldPassword = new JPasswordField(15);
		oldPwPanel.add(oldPassword);
		top.add(oldPwPanel);
		
		JPanel pwPanel1 = new JPanel();
		pwPanel1.add(new JLabel("Create a password        "));
		password = new JPasswordField(15);
		pwPanel1.add(password);
		top.add(pwPanel1);
		
		JPanel pwPanel2 = new JPanel();
		pwPanel2.add(new JLabel("Confirm your password"));
		password2 = new JPasswordField(15);
		pwPanel2.add(password2);
		top.add(pwPanel2);
		
		JPanel emailPanel = new JPanel();
		emailPanel.add(new JLabel(" Email                              "));
		email = new JTextField(15);
		emailPanel.add(email);
		top.add(emailPanel);
		
		contentPane.add("North", top);
		
		JPanel butPanel = new JPanel();
		butPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(this);
		butPanel.add(confirmButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		butPanel.add(cancelButton);
		
		contentPane.add("South", butPanel);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == cancelButton){
			int n = JOptionPane.showConfirmDialog(null, "Cancel Edition ?",
					"Cancel", JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION)
				this.dispose();
		}
		
		else if (e.getSource() == confirmButton){
			String firstName = firstname.getText();
			String lastName = lastname.getText();
			String oldPass = oldPassword.getText();
			String passWord1 = password.getText();
			String passWord2 = password2.getText();
			String Email = email.getText();

			parent.controller.loadFromDisk("records.txt");
			if (firstName.equals("")||lastName.equals("")||passWord1.equals("")||passWord2.equals("")||Email.equals("") || oldPass.equals("") ) {
				JOptionPane.showMessageDialog(this, "info cannot be empty",
						"Input Error", JOptionPane.ERROR_MESSAGE);
			}
			
			else if (passWord1.length() < 8) {
				JOptionPane.showMessageDialog(this, "password has to be at least 8 characters ",
						"Input Error", JOptionPane.ERROR_MESSAGE);
			}
			else if (!passWord1.equals(passWord2)) {
				JOptionPane.showMessageDialog(this, "passwords don't mathch",
						"Input Error", JOptionPane.ERROR_MESSAGE);
			}
			else if (!ValidString(firstName)||!ValidString(lastName)||!ValidString(passWord1)||!ValidString(passWord2)||!ValidString(oldPass)) {
				JOptionPane.showMessageDialog(this, "not validString",
						"Input Error", JOptionPane.ERROR_MESSAGE);
			}
			else if (!parent.controller.getDefaultUser().Password().equals(oldPass)){
				JOptionPane.showMessageDialog(this, "Error Old PassWord",
						"Input Error", JOptionPane.ERROR_MESSAGE);				
			}
			else {
				parent.controller.getDefaultUser().firstname(firstName);
				parent.controller.getDefaultUser().lastname(lastName);
				parent.controller.getDefaultUser().Password(passWord1);
				parent.controller.getDefaultUser().mEmail(Email);
				dispose();
			}
		}
		
	}
	
	public static boolean ValidString(String s)
	{
		char[] sChar = s.toCharArray();
		for(int i = 0; i < sChar.length; i++)
		{
			int sInt = (int)sChar[i];
			if(sInt < 48 || sInt > 122)
				return false;
			if(sInt > 57 && sInt < 65)
				return false;
			if(sInt > 90 && sInt < 97)
				return false;
		}
		return true;
	}

}
