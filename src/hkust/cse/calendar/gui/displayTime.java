package hkust.cse.calendar.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.GregorianCalendar;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * \class displayTime
 * will make an area displaying the current user set time and
 * an exit "cross" as well as a set time button
 * on clicking the setting button, the window shall close and open a set-time dialog
 */

public class displayTime extends JFrame {
	
	
	private static final long serialVersionUID = 1L;

	private watchPanel watch = new watchPanel();
	private JButton SetTimeButton;

	public displayTime() {
		//the little cross
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//_controller.setLocationVector(_controller.getLocationVector());
				exit(0);
			}
		});
		//time display area
		
		final class DigitalClock extends JFrame {
		    public DigitalClock() {
		        super("Digital Clock");
		        setSize(345, 60);
		        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        JPanel pane = new JPanel();
		        pane.setLayout(new GridLayout(1, 1, 15, 15));
		        pane.add(watch);
		        setContentPane(pane);
		        show();
		    }
		}
		
		DigitalClock clock = new DigitalClock();
		
		//set time button
		class setTimeButton implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				//(~.~) open another window to set time
				//(~.~) i don't want to write it
				exit(0);
			}	
		}
		
		SetTimeButton = new JButton("set time");
		final setTimeButton setbl = new setTimeButton();
		SetTimeButton.addActionListener(setbl);
		
		// p(~.~)q
		pack();
		setVisible(true);
	}
	

	protected void exit(int i) {
		// TODO Auto-generated method stub
		
	}
	
}

/**
 * \class watchPanel
 *  show current time on the display screen 
 */
class watchPanel extends JPanel implements Runnable {
    Thread runner;

    watchPanel() {
        if (runner == null) {
            runner = new Thread(this);
            runner.start();
        }
    }

    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { }
        }
    }

    public void paintComponent(Graphics comp) {
        Graphics2D comp2D = (Graphics2D)comp;
        Font type = new Font("Serif", Font.BOLD, 24);
        comp2D.setFont(type);
        comp2D.setColor(getBackground());
        comp2D.fillRect(0, 0, getSize().width, getSize().height);
        GregorianCalendar day = new GregorianCalendar();
        String time = (day.getTime()).toString();
        comp2D.setColor(Color.black);
        comp2D.drawString(time, 5, 25);
    }
}
