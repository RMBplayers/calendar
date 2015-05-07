package hkust.cse.calendar.gui;

import java.awt.Color;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;


class CalCellRenderer extends DefaultTableCellRenderer

{

	private int r;

	private int c;
	
	//public static int row=-1,col=-1,state;
	//public static int index = 0;	//assume no appt at first
	static boolean [][] light = new boolean [6][7];


	public CalCellRenderer(Object value,int r,int c) {
		if (value != null) {
			setForeground(Color.red);
		} else
			setForeground(Color.black);
		
		// check wheher the location exits
		if (light[r][c])
			setBackground(Color.green);
		else
			setBackground(Color.white);
		
		// once for clicking color change now comment it
		/*
		if(r==row&&c==col)
		{
			if(state==1)setBackground(Color.green);
			else if(state==2)setBackground(Color.yellow);
			else if(state==3)setBackground(Color.blue);
			else if(state==4)setBackground(Color.red);
			else setBackground(Color.white);
		}
		else 
		*/
		
		setHorizontalAlignment(SwingConstants.RIGHT);
		setVerticalAlignment(SwingConstants.TOP);
	}


	
	public int row() {
		return r;
	}

	public int col() {
		return c;
	}

}
