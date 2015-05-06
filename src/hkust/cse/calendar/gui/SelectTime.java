package hkust.cse.calendar.gui;

import hkust.cse.calendar.unit.TimeSpan;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * \class SelectTime
 * @author JX
 * provide a JComboBox to select time
 * and a button to make sure of this selection
 */
public class SelectTime extends JFrame {
	
	private JPanel panel;
	
	private JButton confirmButton;
	private HashMap<JCheckBox, TimeSpan> timeMap;
	private Vector<JCheckBox> checkBox;
	
	
	public SelectTime(Vector<TimeSpan> times) {
		super();
		Container contentPane = getContentPane();
		Vector<String> timeSymbol = new Vector<String>();
		Iterator<TimeSpan> it = times.iterator();
		timeMap = new HashMap<JCheckBox, TimeSpan> ();
		checkBox = new Vector<JCheckBox>();
		panel = new JPanel();
		while (it.hasNext()) {
			TimeSpan t = it.next();
			JCheckBox jc = new JCheckBox(t.toString());
			timeMap.put(jc, t);
			checkBox.add(jc);
			panel.add(jc);
		}
		
		
		
		//timeBox = new JComboBox(timeSymbol);
		
		//panel.add(timeBox);
		
		confirmButton = new JButton("Confirm");
		class confirmButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//String[] selectedTime = (String[]) timeBox.getSelectedObjects();
			}
			
		}
		final confirmButtonListener cbl = new confirmButtonListener();
		confirmButton.addActionListener(cbl);
		
		panel.add(confirmButton);
		
		contentPane.add(panel, BorderLayout.CENTER);
		this.setContentPane(contentPane);
		
		pack();
		setVisible(true);
	}	
}
