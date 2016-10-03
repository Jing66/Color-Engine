package colors;
import java.awt.AlphaComposite;
/**
 * Bars: Second panel to generate animation
 * @author liujingyun
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Bars extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hashtable<String,Integer> test = new Hashtable<String,Integer>();
					test.put("a",1);test.put("aaaaaaaaa",1);
					//test.put("b",0);test.put("ccccccc",0);test.put("dddd",0);test.put("e",0);test.put("f",0);
					Bars frame = new Bars(test);
					//frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Bars() {
		setTitle("Bars Test");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(8, 10, 8, 10));
		contentPane.setLayout(new BorderLayout(5, 10));
		setContentPane(contentPane);
		
//		JButton test = new JButton("test");
//		add(test);
//		
	}
	
	public Bars(Hashtable<String,Integer> choices){
		//TODO: make grids and empty bars
		setTitle("Bars Ready");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 600);
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(8, 10, 8, 10));
		//contentPane.setLayout(new BorderLayout(5, 10));
		setContentPane(contentPane);
		//++++++++++++++++++Layout++++++++++++++++++++++++++++++++
		//GridLayout test = new GridLayout((choices.size())+2,1);
		//getContentPane().setLayout(test);
		BoxLayout boxlayout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);  //Y_AXIS: vertical
		contentPane.setLayout(boxlayout);
		
		//++++++++++++++++++Loop to Draw Rectangles++++++++++++++
		Enumeration<String> keys = choices.keys();
		int count = 0;
		while (keys.hasMoreElements()){
			count++;
			String i = keys.nextElement();
			//Draw Rectangle
			RectDraw rect = new RectDraw(i,count,choices.get(i));
			//*************Add fake Expectation value and VAR**************
			//USE GRIDBAGLAYOUT IF NEEDED
			double exp = 135.9;
			double var = 12.4;
			String text = i+"     E: "+exp + ",  Var:"+var;
			JLabel indicator = new JLabel(text);
			indicator.setAlignmentX(CENTER_ALIGNMENT);
			indicator.setFont(new Font("Serif", Font.BOLD, 20));
			contentPane.add(indicator);
		
			//Draw Rectangle
			contentPane.add(rect);
			
		}
		
	}
	
	/**********************Draw Rectangle***********************/
	private static class RectDraw extends JPanel{
		//BOND = 0; INVERSE = 1;
		private String name;
		private int bond; 
		private int order;
		private final int SINGLE_CELL_LEN = 15;
		
		//Constructor
		public RectDraw(String name, int order, int bond){
			setPreferredSize(new Dimension(600,20));
			setLayout(new FlowLayout());
			this.name = name;
			this.order = order;
			this.bond = bond;
		}
		
		//Draw Rectangle
		public void paintComponent(Graphics g){
			super.paintComponent(g);  
			int middleX = 100;
			int countBox = 0;
			for (int start = 100;start<925;start+=75){	
		       	 g.drawRect(start,5,75,30);
		       	 countBox++;
		       	 if(countBox == 6){middleX = start;}
		    }
			g.setFont(new Font("default", Font.BOLD, 16));
			
		    //g.drawString(name, 500, order*10);
			
			//**************TODO: Get Real Data and show Colors***************
			/*double realData = DataProcess.getRealData(name);
			double var = DataProcess.getVar(name);
			double numVar =  var/realData;*/
			double numVar = 3.4;
			//if BOND: paint numVar length to right RED. else paint left Blue
			double fillLength = numVar * SINGLE_CELL_LEN;
			//paint from middle
			double lengthLeft = 0;
			Graphics2D g2d = (Graphics2D)g;
			for(int i=1;i<numVar+1;i+=1){
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, i*0.2f));
		        //if BOND
				g.fillRect(middleX+i*SINGLE_CELL_LEN, 5, SINGLE_CELL_LEN, 30);
		        if(i==numVar-1){lengthLeft =fillLength - i*SINGLE_CELL_LEN; }
		        System.out.print("paint one box!\n");
			}
			g.fillRect(middleX+(int)numVar*SINGLE_CELL_LEN,5,(int)lengthLeft,30);
			
		}
	}
	
	
}
