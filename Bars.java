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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JLabel;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;


public class Bars extends JFrame {

	private JPanel contentPane;
	public ArrayList<String> securities = new ArrayList<String>();	//A list of what's shown on this panel, in order. It's SECURITY not NAME
	
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
	
	
	
}

