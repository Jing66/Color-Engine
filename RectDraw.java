package colors;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JPanel;

public class RectDraw extends JPanel {

	/**
	 * Create the panel.
	 */
	//BOND = 0; INVERSE = 1;
	private final int FULL_RECT_LEN = 250;
	private final int MIDDLE_RECT_LEN = 150;
	
	//Constructor
	public RectDraw(String name){
		setPreferredSize(new Dimension(600,20));
		setLayout(new FlowLayout());
	}
	
	//Draw Rectangle: Each Rectangle length 125, startX=100, Width=30, startY = 5; middle rect length = 75;
	public void paintComponent(Graphics g){
		super.paintComponent(g);  
		int middleX = 100;
		int countBox = 0;
		int startX = 50;
		while (startX<=FULL_RECT_LEN*6+MIDDLE_RECT_LEN){	
			if(countBox == 3){middleX = startX+MIDDLE_RECT_LEN/2;g.drawRect(startX,5,MIDDLE_RECT_LEN,30);startX+=MIDDLE_RECT_LEN;countBox++;continue;}
			g.drawRect(startX,5,FULL_RECT_LEN,30);
	       	startX+=FULL_RECT_LEN;countBox++;
	    }
	}

}
