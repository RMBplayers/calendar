package hkust.cse.calendar.gui;


//future tasks: set method for initializing time
//set timer methods

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.unit.UserTimer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeSetter extends JFrame {
	private JTextField timeSetField;
	
	//set labels and text fields for seperate input
	private JLabel yearL;
	private JTextField yearField;
	private JLabel monthL;
	private JTextField monthField;
	private JLabel dayL;
	private JTextField dayField;
	private JLabel hourL;
	private JTextField hourField;
	private JLabel minuteL;
	private JTextField minuteField;
	private JLabel secondL;
	private JTextField secondField;
	
	private JButton confirmButton;

	private SimpleDateFormat sdf;
	/**
	 * \private controller
	 */
	private ApptStorageControllerImpl _controller;
	
	public TimeSetter (ApptStorageControllerImpl controller) {
		_controller = controller;
		
		JPanel contentPane = new JPanel();
		setTitle("set_time");
	
		sdf = new SimpleDateFormat();
		
		this.setLayout(new BorderLayout());
		this.setLocationByPlatform(true);
		this.setSize(300, 200);
		
		/**
		 * this is the cross on the top right
		 */
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//_controller.setLocationVector(_controller.getLocationVector());
				exit(0);
			}
		});
		
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				//get input from JTextField
				String timeInput = timeSetField.getText();
				String yearInput = yearField.getText();
				String monthInput = monthField.getText();
				String dayInput = dayField.getText();
				String hourInput = hourField.getText();
				String minuteInput = minuteField.getText();
				String secondInput = secondField.getText();
				
				if ((yearInput.equals(""))||(monthInput.equals(""))||(dayInput.equals(""))||(hourInput.equals(""))
						||(minuteInput.equals(""))||(secondInput.equals(""))) {
					System.out.println("Not enough input for setting time, by default we use system time.");
					System.out.println(sdf.format(_controller.getTime()));
					System.out.println(_controller.getTime().getTime());
					setVisible(false);
					dispose();
					return;
				}
				Timestamp userDate;
				userDate = new Timestamp(Integer.parseInt(yearInput), Integer.parseInt(monthInput) -1,
						Integer.parseInt(dayInput), Integer.parseInt(hourInput), Integer.parseInt(minuteInput),
						Integer.parseInt(secondInput), 0);
					
				//System.out.println(_controller.getTime().getTime());
				/*_controller.setTime(new Timestamp((new Date()).getTime()));
				System.out.println(sdf.format(_controller.getTime()));
				System.out.println(_controller.getTime().getTime());*/
				_controller.setTime(userDate);
				_controller.updateScheduleNotify();
				setVisible(false);	
				dispose();
				System.out.println(sdf.format(_controller.getTime()));
				System.out.println(_controller.getTime().getTime());
				exit(0);			
			}
		}
		
		timeSetField = new JTextField(40);
		yearField = new JTextField(4);
		monthField = new JTextField(2);
		dayField = new JTextField(2);
		hourField = new JTextField(2);
		minuteField = new JTextField(2);
		secondField = new JTextField(2);
		final ButtonListener bl = new ButtonListener();
		
		yearL = new JLabel("YEAR:");
		monthL = new JLabel("MONTH:");
		dayL = new JLabel("DAY:");
		hourL = new JLabel("HOUR:");
		minuteL = new JLabel("MINUTE");
		secondL = new JLabel("SECOND");
		
		
		confirmButton = new JButton("confirm");
		confirmButton.addActionListener(bl);
		contentPane.add(yearL);
		contentPane.add(yearField);
		contentPane.add(monthL);
		contentPane.add(monthField);
		contentPane.add(dayL);
		contentPane.add(dayField);
		contentPane.add(hourL);
		contentPane.add(hourField);
		contentPane.add(minuteL);
		contentPane.add(minuteField);
		contentPane.add(secondL);
		contentPane.add(secondField);
		contentPane.add(confirmButton);
		setContentPane(contentPane);
		
		pack();
		setVisible(true);
		//(~.~)
	}
	protected void exit(int i) {
		// TODO Auto-generated method stub
		
	}
}
