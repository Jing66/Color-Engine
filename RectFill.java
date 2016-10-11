package colors;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class RectFill extends RectDraw{
	//BOND = 0; INVERSE = 1;
			private String name;	//Index of security
			private int bond; 
			private double actual;
			private final int FULL_RECT_LEN = 250;
			private final int SINGLE_CELL_LEN = FULL_RECT_LEN/10;
			private final int MIDDLE_RECT_LEN = 150;
			
	//Constructor
			public RectFill(String name, int bond, double actual){
				super(name);
				setPreferredSize(new Dimension(600,20));
				setLayout(new FlowLayout());
				this.name = name;
				this.actual = actual;
				this.bond = bond;
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
				//**************TODO:MODIFY: Get Real Data***************
				double realData = actual;
				
				double var = DataProcess.getVar(name);
				double exp = 0;
				try {
					exp = DataProcess.getBMG(name,0);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.print("\n============ERROR GETTING EXP=============");
				}
				double mVar =  var/(realData - exp);
				//While get Actual data
				while (mVar != 0 ){
					int numSmallBox = (int) Math.abs((10*mVar));
					float countSmallBox = 0;
					Graphics2D g2d = (Graphics2D)g;
					
					//*********************Fill Colors*************************
					//If (BOND && mVar >0)||(Inverse && mVar<0) : red on right
					if(bond == 0&& mVar > 0 || bond ==1 && mVar <0){
						mVar = Math.abs(mVar);
						int startFill=middleX+MIDDLE_RECT_LEN/2;
						while(countSmallBox < numSmallBox){
							g.setColor(Color.BLUE);
							g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)((countSmallBox+1)/(numSmallBox+1))));
							g.fillRect(startFill, 5, SINGLE_CELL_LEN, 30);
							startFill +=SINGLE_CELL_LEN;
							countSmallBox++;
						}
					}
					else{
						mVar = Math.abs(mVar);
						int startFill=middleX-MIDDLE_RECT_LEN/2-SINGLE_CELL_LEN;
						while(countSmallBox < numSmallBox){
							g.setColor(Color.RED);
							g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)((countSmallBox+1)/(numSmallBox+1))));
							g.fillRect(startFill, 5, SINGLE_CELL_LEN, 30);
							startFill -=SINGLE_CELL_LEN;
							countSmallBox++;
						}
					}
					//If Actual == expectation, grey box in middle
					if(mVar==0){
						g.setColor(Color.GRAY);
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
						g.fillRect(middleX-MIDDLE_RECT_LEN/2, 5, MIDDLE_RECT_LEN, 30);
					}
					//Show Actual in middle
					String actual = Double.toString(realData);
					g.setColor(Color.BLACK);
					g.setFont(new Font("TimesRoman", Font.BOLD, 20));
					g.drawString(actual, middleX-MIDDLE_RECT_LEN/2,30);
				}
			
			}
	}