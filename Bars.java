package colors;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class Bars extends JFrame {

	public static JPanel contentPane;
	public static ArrayList<String> securities = new ArrayList<String>();	//A list of what's shown on this panel, in order. NAME
	public static ArrayList<String> securitiesIndex = new ArrayList<String>(); //A list of security index. SECURITIES
	
	public ArrayList<RectDraw> rectangles = new ArrayList<RectDraw>();	//list of rectangle components
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hashtable<String,Integer> test = new Hashtable<String,Integer>();
					test.put("CANLNETJ Index",0);
					test.put("DOEASCRD Index",0);
					
					Bars frame = new Bars(test);
					frame.setVisible(true);
					
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
	public Bars(Hashtable<String,Integer> choices){
		//++++++++++++++++++Whole Panel Layout++++++++++++++++++++++++++++++++
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
		System.out.print("$Bars: Securities Index: "+securitiesIndex);
		try {
			securities = DataProcess.getNickNames(securitiesIndex);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		for(int j=0;j<securitiesIndex.size();j++){
			//++++++++++++++ Group Labels and set layout+++++++++++++
			JPanel labelPane = new JPanel();
			labelPane.setLayout(new BoxLayout(labelPane, BoxLayout.X_AXIS));
			
			//++++++++++++ Construct Rectangle+++++++++++++++
			double var = DataProcess.getVar(securitiesIndex.get(j));
			var = Double.valueOf(String.format("%1.2f",var));
			//Construct a RectDraw ==> Actual==0 for now
			RectDraw rect = null;
			rect = new RectDraw(securities.get(j), 0,choices.get(securitiesIndex.get(j)),0,var);
			
			double testExp = 0;
			try {
				 testExp = DataProcess.getExp(securitiesIndex.get(j)); 
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("\n==========ERROR getting EXP for "+securities.get(j)+"============");
				JOptionPane.showMessageDialog(contentPane, "BMG has no survey for "+securities.get(j)+"! Please input manually! ");
				
			}	
			finally{
				rect.setExp(testExp);  
				rect.setIndex(securitiesIndex.get(j));
				//*************Add Expectation value and VAR JLabel**************	
				String bondInverse;
				
				//++++++++++++++++  Add Labels
				String text = securities.get(j);
				JLabel indicator = new JLabel(text);
				indicator.setAlignmentX(CENTER_ALIGNMENT);
				indicator.setFont(new Font("Serif", Font.BOLD, 25));
				labelPane.add(indicator);
				
				if(choices.get(securitiesIndex.get(j))==0) {
					bondInverse = "           (high = sell)";
				}
				else bondInverse = "             (high = buy)";
				JLabel bondOrInverse = new JLabel(bondInverse);
				bondOrInverse.setFont(new Font("Serif", Font.BOLD, 18));
				labelPane.add(bondOrInverse);
			    contentPane.add(labelPane);

				System.out.println("\n>>>Initialized this rectangle:" + rect.toString());
				//Draw Rectangle
				contentPane.add(rect);
				rectangles.add(rect);
				
			}
			
		}
		
	
	}
	
	
	
}
