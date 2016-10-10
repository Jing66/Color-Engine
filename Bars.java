package colors;
import java.awt.AlphaComposite;
/**
 * Bars: Second panel to generate animation
 * @author liujingyun
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.JLabel;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;


public class Bars extends JFrame {

	private JPanel contentPane;
	public ArrayList<String> securities = new ArrayList<String>();	//A list of what's shown on this panel, in order. It's SECURITY not NAME
	private List _listeners = new ArrayList();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hashtable<String,Integer> test = new Hashtable<String,Integer>();
					test.put("a",0);
					test.put("aaaaaaaaa",1);
					
					Bars frame = new Bars(test);
					frame.setVisible(true);
					//TODO: generate a new Frame after the data come out
					Bars newFrame = new Bars(test);
					newFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
/**
	 * Create the frame.
	 * @throws Exception 
	 * @param  choices  Hashtable that contains all the information by users' choice
	 */
	public Bars(Hashtable<String,Integer> choices) throws Exception{
		//++++++++++++++++++Layout++++++++++++++++++++++++++++++++
		setTitle("Bars Ready");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(30, 100, 1800, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(8, 10, 8, 10));
		setContentPane(contentPane);
		//GridLayout test = new GridLayout((choices.size())+2,1);
		//getContentPane().setLayout(test);
		BoxLayout boxlayout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);  //Y_AXIS: vertical
		contentPane.setLayout(boxlayout);
		
		//++++++++++++++++++Loop to Draw Rectangles, Given Indicator security name++++++++++++++
		Enumeration<String> keys = choices.keys();
		int count = 0;
		while (keys.hasMoreElements()){
			count++;
			String i = keys.nextElement();	//NOTE: i is indicator security name
			securities.add(i);
			RectDraw rect = new RectDraw(i);
			//*************Add Expectation value and VAR**************
			double exp = DataProcess.getBMG(i, 0);
			double var = DataProcess.getVar(i);
			
			String text = i+"     E: "+exp + ",  Var:"+var;
			JLabel indicator = new JLabel(text);
			indicator.setAlignmentX(CENTER_ALIGNMENT);
			indicator.setFont(new Font("Serif", Font.BOLD, 20));
			contentPane.add(indicator);
			//Draw Rectangle
			contentPane.add(rect);
			securities.add(i);		//add security String for next loop calling getActual
		}
		
		//++++++++++++++++++Loop to Fill Colors++++++++++++++++++++++
		
		//----------NOTE: May need to draw the Rectangles again--------
		//----------NOTE: THIS IS WRONG! Do it in <main> (ColorChoice)--------
		getContentPane().remove(contentPane);
		JPanel newPane = new JPanel();
		newPane.setBorder(new EmptyBorder(8, 10, 8, 10));
		setContentPane(newPane);	
		newPane.setLayout(boxlayout);
		
		for(int i=0;i<securities.size();i++){
			String security = securities.get(i);
			RectFill bar = new RectFill(security, choices.get(security));
			newPane.add(bar);
		}
		
	}
	
	/**********************Draw Rectangles***********************/
	private class RectDraw extends JPanel{
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
	
	/**********************Fill Color***********************/
	private class RectFill extends RectDraw{
		//BOND = 0; INVERSE = 1;
				private String name;	//Index of security
				private int bond; 
				private final int FULL_RECT_LEN = 250;
				private final int SINGLE_CELL_LEN = FULL_RECT_LEN/10;
				private final int MIDDLE_RECT_LEN = 150;
				
		//Constructor
				public RectFill(String name, int bond){
					super(name);
					setPreferredSize(new Dimension(600,20));
					setLayout(new FlowLayout());
					this.name = name;
					
					this.bond = bond;
				}
				
				//Draw Rectangle: Each Rectangle length 125, startX=100, Width=30, startY = 5; middle rect length = 75;
				public void paintComponent(Graphics g){
					super.paintComponent(g);  
					int middleX = 100;
					
					//**************TODO: Get Real Data***************
					double realData = 0;
					try {
						realData = DataProcess.getBMG(name,1);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					double var = DataProcess.getVar(name);
					double exp = 0;
					try {
						exp = DataProcess.getBMG(name,0);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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

	synchronized void addEventListener(myListener listener){
		_listeners.add(listener);
	}
	
	synchronized void removeEventListener(myListener listener){
		_listeners.remove(listener);
	}
	
	synchronized void fireEvent(){
		DataProcess data = new DataProcess(this);
		Iterator i = _listeners.iterator();
		while (i.hasNext()){
			((myListener) i.next()).handleEvent(data);
		}
	}
	
	
	
}

