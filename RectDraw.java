package colors;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class RectDraw extends JPanel implements ActionListener{

	//BOND = 0; INVERSE = 1;
	private final int FULL_RECT_LEN = 250;
	private final int MIDDLE_RECT_LEN = 150;
	private final int SINGLE_CELL_LEN = FULL_RECT_LEN/10;
	private double exp =0;
	private JTextField expText;
	private boolean fill = false;
	private double realData=0;
	private int bond; 
	private String name; //SecuritiesIndex
	private double var;
	
	/**
	 * Create the panel.
	 */
	public RectDraw(String name, double exp,int bond, double actual, double var){
		setPreferredSize(new Dimension(600,20));
		setLayout(new FlowLayout());
		
		this.name = name;
		this.exp = exp;
		expText = new JTextField(Double.toString(exp));
		expText.addActionListener(this);
		this.bond = bond;
		this.realData = actual;
		this.var = var;
		
		expText = new JTextField(Double.toString(exp));
		expText.setAlignmentX(Component.CENTER_ALIGNMENT); 
		expText.addActionListener(this);
		this.add(expText);
	}
	
	//Draw Rectangle: Each Rectangle length 125, startX=100, Width=30, startY = 5; middle rect length = 75;
	public void paintComponent(Graphics g){
		super.paintComponent(g);  
		int countBox = 0;
		int startX = 50;
		int middleX = 100;
		while (startX<=FULL_RECT_LEN*6+MIDDLE_RECT_LEN){	
			if(countBox == 3){
				g.drawRect(startX,20,MIDDLE_RECT_LEN,30);
				startX+=MIDDLE_RECT_LEN;
				countBox++;
				middleX=startX-MIDDLE_RECT_LEN/2;
				continue;
			}
			g.drawRect(startX,20,FULL_RECT_LEN,30);
	       	startX+=FULL_RECT_LEN;countBox++;
	    }
		
		if(fill){
			System.out.print("\nRe-painting now!!----\n");
			double mVar =  (realData - exp)/var;
			System.out.print("mVar = "+mVar);
			Graphics2D g2d = (Graphics2D)g;
			//While get Actual data
			if (mVar != 0 ){
				int numSmallBox = (int) Math.abs((10*mVar));
				float countSmallBox = 0;
				
				
				//*********************Fill Colors*************************
				//If (BOND && mVar >0)||(Inverse && mVar<0) : red on right
				if(bond == 0&& mVar > 0 || bond ==1 && mVar <0){
					System.out.print("\nCASE: A > E: blue box to the right \n");
					mVar = Math.abs(mVar);
					int startFill=middleX+MIDDLE_RECT_LEN/2;
					while(countSmallBox < numSmallBox){
						g.setColor(Color.BLUE);
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)((countSmallBox+1)/(numSmallBox+1))));
						g.fillRect(startFill, 20, SINGLE_CELL_LEN, 30);
						startFill +=SINGLE_CELL_LEN;
						countSmallBox++;
					}
				}
				else{
					System.out.print("\nCASE: A < E: red box to the right \n");
					mVar = Math.abs(mVar);
					int startFill=middleX-MIDDLE_RECT_LEN/2-SINGLE_CELL_LEN;
					while(countSmallBox < numSmallBox){
						g.setColor(Color.RED);
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)((countSmallBox+1)/(numSmallBox+1))));
						g.fillRect(startFill, 20, SINGLE_CELL_LEN, 30);
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
				g.fillRect(middleX-MIDDLE_RECT_LEN/2, 20, MIDDLE_RECT_LEN, 30);
			}
			//Show Actual in middle
			String actual = Double.toString(realData);
			g.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.BOLD, 30));
			g.drawString(actual, middleX,45);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		try{
			exp = Double.parseDouble(expText.getText());
			System.out.print("\n\n+++++++++++++++++++Exp has changed to: "+exp+"\n");
		}catch(Exception e){
			System.out.print("==========Expectation input should be double========");
			e.printStackTrace();
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
	@Override
	public String toString(){
		return "Rectangle name: " + name + ", Exp=" + exp + ", Var= " + var + ", bond= "+bond;
	}
}