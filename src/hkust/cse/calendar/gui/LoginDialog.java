package hkust.cse.calendar.gui;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.apptstorage.ApptStorageNullImpl;
import hkust.cse.calendar.unit.User;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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


public class LoginDialog extends JFrame implements ActionListener
{
	private JTextField userName;
	private JPasswordField password;
	private JPasswordField password2;
	private JTextField email;
	private JTextField firstname;
	private JTextField lastname;
	private JButton button;
	private JButton closeButton;
	private JButton signupButton;
	private JButton signup;
	private JRadioButton normal;
	private JRadioButton administrator;
	private Container contentPane;
	
	public LoginDialog()		// Create a dialog to log in
	{
		
		setTitle("Log in");
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		
		contentPane = getContentPane();
		
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		
		JPanel messPanel = new JPanel();
		messPanel.add(new JLabel("Please input your user name and password to log in."));
		top.add(messPanel);
		
		JPanel namePanel = new JPanel();
		namePanel.add(new JLabel("User Name:"));
		userName = new JTextField(15);
		namePanel.add(userName);
		top.add(namePanel);
		
		JPanel pwPanel = new JPanel();
		pwPanel.add(new JLabel("Password:  "));
		password = new JPasswordField(15);
		pwPanel.add(password);
		top.add(pwPanel);
		
		JPanel signupPanel = new JPanel();
		signupPanel.add(new JLabel("If you don't have an account, please sign up:"));
		signupButton = new JButton("Sign up now");
		signupButton.addActionListener(this);
		signupPanel.add(signupButton);
		top.add(signupPanel);
		
		contentPane.add("North", top);
		
		JPanel butPanel = new JPanel();
		butPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		button = new JButton("Log in (No user name and password required)");
		button.addActionListener(this);
		butPanel.add(button);
		
		closeButton = new JButton("Close program");
		closeButton.addActionListener(this);
		butPanel.add(closeButton);
		
		contentPane.add("South", butPanel);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);	
		
	}
	

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == button)
		{
			// When the button is clicked, check the user name and password, and try to log the user in
			
			//login();
			String username = userName.getText();
			String passcode = password.getText();
			
			////////////////////
			// controller should have all the info instead of new , pass it to lizhiahng
			// remember to modify the controller when sign up (line 214)
			///////////////////
			
			////////////////////
			// lizhiahng： tou tou tou
			///////////////////
			
			
			ApptStorageControllerImpl controller = new ApptStorageControllerImpl(new ApptStorageNullImpl());
			controller.loadFromDisk("records.txt");

			if (!controller.verifyUser(username,passcode)) {
				JOptionPane.showMessageDialog(this, "please check your username and password",
						"Input Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			else {
				User user = controller.getUser(username);
				controller.setDefaultUser(user);
				//controller.loadFromDisk("records.txt");
				CalGrid grid = new CalGrid(controller);
				setVisible( false );
			}
		}
		else if(e.getSource() == signupButton)
		{
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
			
			JPanel username = new JPanel();
			username.add(new JLabel("Choose your username "));
			userName = new JTextField(15);
			username.add(userName);
			top.add(username);
			
			JPanel pwPanel = new JPanel();
			pwPanel.add(new JLabel("Create a password        "));
			password = new JPasswordField(15);
			pwPanel.add(password);
			top.add(pwPanel);
			
			JPanel pwPanel2 = new JPanel();
			pwPanel2.add(new JLabel("Confirm your password"));
			password2 = new JPasswordField(15);
			pwPanel2.add(password2);
			top.add(pwPanel2);
			
			JPanel emailPanel = new JPanel();
			emailPanel.add(new JLabel(" Email                            "));
			email = new JTextField(15);
			emailPanel.add(email);
			top.add(emailPanel);
			
			JPanel userType = new JPanel();
			userType.add(new JLabel("user type:                    "));
			normal = new JRadioButton("normal",true);
			userType.add(normal);
			administrator = new JRadioButton("administrator",false);
			userType.add(administrator);
			ButtonGroup bgroup = new ButtonGroup();
			bgroup.add(administrator);
			bgroup.add(normal);
			top.add(userType);
			
			contentPane.add("North", top);
			
			JPanel butPanel = new JPanel();
			butPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

			signup = new JButton("Sign up");
			signup.addActionListener(this);
			butPanel.add(signup);
			
			closeButton = new JButton("Close program");
			closeButton.addActionListener(this);
			butPanel.add(closeButton);
			
			contentPane.add("South", butPanel);
			
			pack();
			setLocationRelativeTo(null);
			setVisible(true);	
			
		}
		else if (e.getSource() == signup) {
			String firstName = firstname.getText();
			String lastName = lastname.getText();
			String username = userName.getText();
			String passWord1 = password.getText();
			String passWord2 = password2.getText();
			String Email = email.getText();
			ApptStorageControllerImpl controller = new ApptStorageControllerImpl(new ApptStorageNullImpl());
			controller.loadFromDisk("records.txt");
			if (firstName.equals("")||lastName.equals("")||username.equals("")||passWord1.equals("")||passWord2.equals("")||Email.equals("")) {
				JOptionPane.showMessageDialog(this, "info cannot be empty",
						"Input Error", JOptionPane.ERROR_MESSAGE);
			} 
			else if (controller.getUser(username) != null) {
				JOptionPane.showMessageDialog(this, "username already exists",
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
			else if (!ValidString(firstName)||!ValidString(lastName)||!ValidString(username)||!ValidString(passWord1)||!ValidString(passWord2)) {
				JOptionPane.showMessageDialog(this, "not validString",
						"Input Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				User newUser = new User(username,passWord1,firstName,lastName,Email,administrator.isSelected());
				controller.addUser(newUser);
				controller.setDefaultUser(newUser);
				CalGrid grid = new CalGrid(controller);
				setVisible( false );
				
				
			}
		}
		else if(e.getSource() == closeButton)
		{
			int n = JOptionPane.showConfirmDialog(null, "Exit Program ?",
					"Confirm", JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION)
				System.exit(0);			
		}
	}
	
	// This method checks whether a string is a valid user name or password, as they can contains only letters and numbers
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
