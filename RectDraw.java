package colors;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicArrowButton;

public class RectDraw extends JPanel implements ActionListener{

	//BOND = 0; INVERSE = 1;
	private final int UNIT_WIDTH = 7; //everytime user click on INCR/DECR button, unit of change
	
	private final int FULL_RECT_LEN = 250;
	private final int HEIGHT = 50;
	private final int MIDDLE_RECT_LEN = 150;
	private final int SINGLE_CELL_LEN = FULL_RECT_LEN/10;
	private double exp =0;
	private JTextField expText;
	private boolean fill = false;
	private double realData=0;
	private int bond; 
	private String name; //SecuritiesName
	private double var;
	private int recHeight = 80;
	
	private String index;
	/**
	 * Create the panel.
	 */
	public RectDraw(String name, double exp,int bond, double actual, double var){
		setPreferredSize(new Dimension(600,80));
		setLayout(new FlowLayout());
		/*GridBagLayout layOut = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.gridx = 3;
		*/
		//setLayout(new BorderLayout());
		this.name = name;
		this.exp = exp;
		expText = new JTextField(Double.toString(exp));
		expText.addActionListener(this);
		this.bond = bond;
		this.realData = actual;
		this.var = var;
		
		//Fill the blank
		this.add(new JLabel("                                                                                                                                                                                                                                                                  "));
		//Editable expectation value
		expText = new JTextField(Double.toString(exp));
		expText.setAlignmentX(Component.CENTER_ALIGNMENT); 
		Font bigFont = expText.getFont().deriveFont(Font.PLAIN, 30f);
		expText.setFont(bigFont);
		expText.addActionListener(this);
		this.add(expText);
	/*
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = 5;
		c.gridy = 0;
	*/
		//Fill the blank: make arrows at the end
		this.add(new JLabel("                                                                                                                                                                                                                                                           "));
		//adjustable width of rectangles
		/*JButton incr = new JButton("+");
		JButton decr = new JButton("-");
		*/
		BasicArrowButton incr = new BasicArrowButton(BasicArrowButton.NORTH);   //getDirection() returns 1(int)
		BasicArrowButton decr = new BasicArrowButton(BasicArrowButton.SOUTH);	//getDirection() returns 5(int)
		/*incr.setFont(new Font("Arial", Font.PLAIN, 20));
		decr.setFont(new Font("Arial", Font.PLAIN, 20));
		decr.setAlignmentX(Component.LEFT_ALIGNMENT);
		incr.setAlignmentX(Component.RIGHT_ALIGNMENT);*/
		incr.addActionListener(this);
		decr.addActionListener(this);
		this.add(incr);
		this.add(decr);
	
		//System.out.println("FIRST Initialize");
		
	/*	layOut.addLayoutComponent(incr,c);
		layOut.addLayoutComponent(decr,c);
		setLayout(layOut);
	*/
	}
	
	//Draw Rectangle: Each Rectangle length 125, startX=100, Width=30, startY = 5; middle rect length = 75;
	public void paintComponent(Graphics g){
		//System.out.println("PAINTING");
		super.paintComponent(g);  
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(3));
		int countBox = 0;
		int startX = 50;
		int middleX = 100;
		while (startX<=FULL_RECT_LEN*6+MIDDLE_RECT_LEN){	
			if(countBox == 3){
				g.drawRect(startX,HEIGHT,MIDDLE_RECT_LEN,recHeight);
				startX+=MIDDLE_RECT_LEN;
				countBox++;
				middleX=startX-MIDDLE_RECT_LEN/2;
				continue;
			}
			g.drawRect(startX,HEIGHT,FULL_RECT_LEN,recHeight);
	       	startX+=FULL_RECT_LEN;countBox++;
	    }
		
		if(fill){
			System.out.println("\nRepainting this rectnagle..."+toString());
			double mVar =  (realData - exp)/var;
			mVar = Double.valueOf(String.format("%1.2f",mVar));
			//While get Actual data
			if (mVar <=-0.1 ||mVar > 0.1 ){
				int numSmallBox = (int) Math.abs((10*mVar));
				float countSmallBox = 0;
				
				
				//*********************Fill Colors*************************
				//If (BOND && mVar >0)||(Inverse && mVar<0) : red on right
				if(bond == 0&& mVar > 0 || bond ==1 && mVar <0){
					//System.out.print("\nCASE: A > E: blue box to the right \n");
					mVar = Math.abs(mVar);
					int startFill=middleX+MIDDLE_RECT_LEN/2;
					while(countSmallBox < numSmallBox){
						g.setColor(Color.RED);
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)((countSmallBox+1)/(numSmallBox+1))));
						g.fillRect(startFill, HEIGHT, SINGLE_CELL_LEN, recHeight);
						startFill +=SINGLE_CELL_LEN;
						countSmallBox++;
					}
				}
				else{
					//System.out.print("\nCASE: A < E: red box to the right \n");
					mVar = Math.abs(mVar);
					int startFill=middleX-MIDDLE_RECT_LEN/2-SINGLE_CELL_LEN;
					while(countSmallBox < numSmallBox){
						g.setColor(Color.BLUE);
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)((countSmallBox+1)/(numSmallBox+1))));
						g.fillRect(startFill, HEIGHT, SINGLE_CELL_LEN, recHeight);
						startFill -=SINGLE_CELL_LEN;
						countSmallBox++;
					}
				}
			}
				//If Actual == expectation, grey box in middle
			else{
				System.out.print("\nCASE: A = E: grey box in middle\n");
				g.setColor(Color.GRAY);
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
				g.fillRect(middleX-MIDDLE_RECT_LEN/2, HEIGHT, MIDDLE_RECT_LEN, recHeight);
			}
			//Show Actual in middle
			String actual = Double.toString(realData);
			String mVarString = Double.toString(mVar);
			g.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.BOLD, 40));
			g.drawString(actual, middleX-MIDDLE_RECT_LEN/4,HEIGHT+40);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g.drawString("MVar= "+mVarString,middleX+MIDDLE_RECT_LEN*2 , HEIGHT -10);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
		if(source instanceof JButton){
			
			BasicArrowButton o = (BasicArrowButton) source;
			//System.out.println("!!!! get direction:"+o.getDirection());
			if(o.getDirection()==1) {
				//If increase the width
				setHeight(recHeight+UNIT_WIDTH);
			}
			else{
				//If decrease the width
				setHeight(recHeight-UNIT_WIDTH);
			}
			//repaint
			this.repaint();
		}
		else{
		//if the action is from textfield
			try{
				exp = Double.parseDouble(expText.getText());
				System.out.print("\n\n+++++++++++++++++++Exp has changed to: "+exp+"\n");
			}catch(Exception e){
				System.out.print("==========Expectation input should be double========");
				e.printStackTrace();
			}
		}
		
	}
	
	public double getExp(){
		return exp;
	}
	
	public void setFill(boolean set){
		fill = set;
	}
	public void setActual(double data){
		this.realData = data;
	}
	
	private void setHeight(int newHeight){
		recHeight = newHeight;
	}
	public String getName(){
		return name;
	}
	
	public void setExp(double input){
		this.exp = input;
		expText.setText(Double.toString(input));
	}
	
	public void setIndex(String input){
		this.index = input;
	}
	
	public String getIndex(){
		return this.index;
	}
	@Override
	public String toString(){
		return "\nRectangle name: " + name + ", Exp=" + exp + ", Var= " + var + ", bond= "+bond+" index= "+index;
	}
}