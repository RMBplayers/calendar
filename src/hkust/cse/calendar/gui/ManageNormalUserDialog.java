package hkust.cse.calendar.gui;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;

//import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

public class ManageNormalUserDialog extends JFrame implements ActionListener{
	private CalGrid parent;
	private JList<String> NormalUserList;
	private JScrollPane leftSide;
	private JPanel rightSide;
	private JButton viewButton;
	private JButton deleteButtion;
	private JButton changeButtion;
	private JButton leaveButtion;
	private JSplitPane combination;
	
	
	ManageNormalUserDialog(CalGrid par, String title){
		parent = par;
		this.setAlwaysOnTop(true);
		setTitle(title);
		// the container
		Container contentPane;
		contentPane = getContentPane();
		contentPane.setPreferredSize(new Dimension(400,160));
		contentPane.setLayout(new FlowLayout());
		contentPane.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		// frame size
		
		
		// the left jlist of user
		// warning, the administor is still inside
		Vector<String> userlist = new Vector<String>(parent.controller.getAllUserID());
		NormalUserList = new JList<String>(userlist);
		NormalUserList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		NormalUserList.setLayoutOrientation(JList.VERTICAL);
		NormalUserList.setVisibleRowCount(-1);
		leftSide = new JScrollPane(NormalUserList);
		leftSide.setPreferredSize(new Dimension(250, 150));
		
		// the right part of user
		rightSide = new JPanel();
		rightSide.setLayout(new FlowLayout(FlowLayout.LEADING));
		rightSide.setPreferredSize(new Dimension(100,150));
		//rightSide.setLayout(new BoxLayout(rightSide, BoxLayout.Y_AXIS));
		// the buttion group
		viewButton = new JButton("View");
		viewButton.setPreferredSize(new Dimension(90,30));
		viewButton.addActionListener(this);
		rightSide.add(viewButton);
		
		deleteButtion = new JButton("Delete");
		deleteButtion.setPreferredSize(new Dimension(90,30));
		deleteButtion.addActionListener(this);
		rightSide.add(deleteButtion);
		
		changeButtion = new JButton("Change");
		changeButtion.setPreferredSize(new Dimension(90,30));
		changeButtion.addActionListener(this);
		rightSide.add(changeButtion);
		
		leaveButtion = new JButton("Leave");
		leaveButtion.setPreferredSize(new Dimension(90,30));
		leaveButtion.addActionListener(this);
		rightSide.add(leaveButtion);
		
		combination = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSide, rightSide);
		combination.setDividerLocation(250);
		contentPane.add(combination);
		
		pack();
		this.setVisible(true);		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == leaveButtion){
			dispose();
		}
		else if (e.getSource() == viewButton){
			AccountDetail a = new AccountDetail(ManageNormalUserDialog.this.parent.controller
					.getUser(NormalUserList.getSelectedValuesList().get(0)));
		}
		else if (e.getSource() == deleteButtion){
			if(parent.controller.getUser(NormalUserList.getSelectedValuesList().get(0)).equals(
					parent.controller.getDefaultUser())){
				JOptionPane.showMessageDialog(this, "can't delete self",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
			else{			
			ManageNormalUserDialog.this.parent.controller.deleteUser(ManageNormalUserDialog.this.parent.controller
					.getUser(NormalUserList.getSelectedValuesList().get(0)).ID());
			dispose();
			}
		}
		else if (e.getSource() == changeButtion){
			EditAccountInfoDialog a = new EditAccountInfoDialog(ManageNormalUserDialog.this.parent.controller
					.getUser(NormalUserList.getSelectedValuesList().get(0)));
		}
		
	}

}
