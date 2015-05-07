package hkust.cse.calendar.unit;

import javax.accessibility.Accessible;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class datePanel extends JPanel {
	
	public JLabel yearL;
	public JTextField yearF;
	public JLabel monthL;
	public JTextField monthF;
	public JLabel dayL;
	public JTextField dayF;

	public datePanel() {
		super();
		Border inviteBorder2 = new TitledBorder(null, "Another day");
		setBorder(inviteBorder2);
		yearL = new JLabel("YEAR: ");
		add(yearL);
		yearF = new JTextField(6);
		add(yearF);
		monthL = new JLabel("MONTH: ");
		add(monthL);
		monthF = new JTextField(4);
		add(monthF);
		dayL = new JLabel("DAY: ");
		add(dayL);
		dayF = new JTextField(4);
		add(dayF);
		repaint();
		setVisible(true);
	}
}
