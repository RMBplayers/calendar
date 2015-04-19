package hkust.cse.calendar.gui;

/*
 * \LocationsDialog.java
 * \author JX 26-3-2015
 * \location setting dialog
 */


//to dos:
//scroll bar for the list
//change list name
//set a map between string name and location.

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.apptstorage.ApptStorageNullImpl;
import hkust.cse.calendar.unit.Location;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LocationsDialog extends JFrame{
	
	private static final long serialVerisonID = 1L;
	
	/**
	 * \private controller
	 */
	private ApptStorageControllerImpl _controller;
	
	private DefaultListModel<Location> listModel;
	private JList<Location> list; //what is this?
	private JTextField locationNameText;
    private DefaultListModel<String> nameListModel; // a name list to store name map
    private JList<String> nameList;
    private HashMap<String, Location> nameToLocation;
	
	private JButton addButton;
	private JButton removeButton;

	//at the same time initialize a location list
	protected Vector<Location> currentVector;
	
	
	/*
	 * \dialogue to set location
	 * \conversion constructor form controller
	 */
	public LocationsDialog(ApptStorageControllerImpl controller) {
		_controller = controller;
		
		//_controller.setLocationVector(new Vector<Location>());
	
		JPanel contentPane = new JPanel();
		setTitle("Locations");
		
		//set functions
		this.setLayout(new BorderLayout());
		this.setLocationByPlatform(true);
		this.setSize(300, 200);
		
		//if the vector is empty then we can initialize a list
		//else we use the ones existing
		
		nameListModel = new DefaultListModel<String>();
		listModel = new DefaultListModel<Location>();
		nameToLocation = new HashMap<String, Location>();
		Vector<Location> curVector = _controller.getLocationVector();
		if (curVector.size() != 0) {
			for (int i = 0; i < curVector.size(); ++i) {
				Location tempLocation = curVector.get(i);
				listModel.addElement(tempLocation);
				String tempName = tempLocation.getLocationName();
				nameListModel.addElement(tempName);
				nameToLocation.put(tempName, tempLocation);
			}
		}
		
		nameList = new JList<String>(nameListModel);	
		list = new JList<Location>(listModel);
		nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//nameList.setSelectedIndex(0);
		//(~,~) start.pdf is wrong
		nameList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String positionToDelete = nameList.getSelectedValue();
				locationNameText.setText(positionToDelete); //is this proper naming method?
				if(e.getValueIsAdjusting() == false) {
					if (list.getSelectedIndex() == -1) {
						removeButton.setEnabled(true);
					} else {
						removeButton.setEnabled(false);
					}
				}
			}
		});
		
		nameList.setPreferredSize(new Dimension(375,200));
		
/////////////////////////////////////the part below is for buttons/////////////////////////////////////////////			
		
		/**
		 * this is the cross on the top right
		 */
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//_controller.setLocationVector(_controller.getLocationVector());
				exit(0);
			}
		});
		
		/**
		 * \add a add button listener
		 * later created an instance for add
		 */
		class addButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String locationName = locationNameText.getText();
					if (!nameListModel.contains(locationName)) {
						Location userDefinedLocation = new Location(locationName);
						nameListModel.addElement(locationName);
						nameToLocation.put(locationName, userDefinedLocation);
						listModel.addElement(userDefinedLocation);
						_controller.addLocationToVector(userDefinedLocation);
				}
			}
		}
		final addButtonListener addbl = new addButtonListener();	
		
		/**
		 * similar to addButtonListener
		 */
		class removeButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e){
				String locationName = locationNameText.getText();
				if (nameListModel.contains(locationName)) {
					Location locationToDelete = nameToLocation.get(locationName);
					nameListModel.removeElement(locationName);
					nameToLocation.remove(locationName);
					listModel.removeElement(locationToDelete);
					nameList.clearSelection();
					_controller.removeLocationFromVector(locationToDelete);
				}
			}
		}	
		final removeButtonListener removebl = new removeButtonListener();
		
		//top and bottom on center JPanel,
		//center on the contentPane
		JPanel top, bottom, center;
		top = new JPanel();
		top.setLayout(new FlowLayout(FlowLayout.LEFT));
		top.add(nameList);
		
		locationNameText = new JTextField(20);
		addButton = new JButton("add");
		removeButton = new JButton("remove");

		bottom = new JPanel();
		
		addButton.addActionListener(addbl);
		removeButton.addActionListener(removebl);
		
		bottom.add(locationNameText);
		bottom.add(addButton);
		bottom.add(removeButton);
		
		
		center = new JPanel(new BorderLayout());
		
		center.add(top, BorderLayout.NORTH);
		center.add(bottom, BorderLayout.SOUTH);
		contentPane.add(center, BorderLayout.CENTER);
		
		setContentPane(contentPane);
		
		
		pack();
		setVisible(true);
	}


	protected void exit(int i) {
		// TODO Auto-generated method stub	
	}
}
