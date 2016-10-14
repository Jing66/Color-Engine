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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JLabel;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class Bars extends JFrame {

	public static JPanel contentPane;
	public static ArrayList<String> securities = new ArrayList<String>();	//A list of what's shown on this panel, in order. NAME
	public static ArrayList<String> securitiesIndex = new ArrayList<String>(); //A list of security index. SECURITIES
	
	public static ArrayList<RectDraw> rectangles = new ArrayList<RectDraw>();	//list of rectangle components
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
		BoxLayout boxlayout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);  //Y_AXIS: vertical
		contentPane.setLayout(boxlayout);
		
		//++++++++++++++++++Loop to Draw Rectangles, Given Indicator security name++++++++++++++
		securitiesIndex = new ArrayList<String>(choices.keySet());
		securities = DataProcess.getNames(securitiesIndex);
		for(int j=0;j<securitiesIndex.size();j++){
			double var = DataProcess.getVar(securitiesIndex.get(j));
			//Construct a RectDraw ==> Actual==0 for now
			RectDraw rect = new RectDraw(securities.get(j), DataProcess.getExp(securitiesIndex.get(j)),choices.get(securitiesIndex.get(j)),0,var);	
			//*************Add Expectation value and VAR JLabel**************
			
			String text = securities.get(j)+"       Var:"+var;
			JLabel indicator = new JLabel(text);
			indicator.setAlignmentX(CENTER_ALIGNMENT);
			indicator.setFont(new Font("Serif", Font.BOLD, 20));
			contentPane.add(indicator);
			//Construct a editable textfield for Expectation
			
			//Draw Rectangle
			contentPane.add(rect);
			rectangles.add(rect);
		}
		
	
	}


}

