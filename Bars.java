package colors;
/**
 * Bars: Second panel to generate animation
 * @author liujingyun
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
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
					test.put("a",1);
					test.put("b",0);test.put("c",0);test.put("d",0);test.put("e",0);test.put("f",0);
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
		setBounds(100, 100, 950, 600);
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(8, 10, 8, 10));
		//contentPane.setLayout(new BorderLayout(5, 10));
		setContentPane(contentPane);
		//++++++++++++++++++Layout++++++++++++++++++++++++++++++++
		//GridLayout test = new GridLayout((choices.size())+2,1);
		//getContentPane().setLayout(test);
		BoxLayout boxlayout = new BoxLayout(contentPane, BoxLayout.PAGE_AXIS);  //Y_AXIS: vertical
		contentPane.setLayout(boxlayout);
		
		Enumeration<String> keys = choices.keys();
		int count = 0;
		while (keys.hasMoreElements()){
			count++;
			String i = keys.nextElement();
			//Draw Rectangle
			RectDraw rect = new RectDraw(i,count,choices.get(i));
			String text = "<html><B>"+i+"</B> </html>";
			JLabel indicator = new JLabel(text);
			indicator.setFont(new Font("Serif", Font.PLAIN, 20));
			contentPane.add(indicator);
			contentPane.add(rect);
		}
		
	}
	
	/**********************Draw Rectangle***********************/
	private static class RectDraw extends JPanel{
		//BOND = 0; INVERSE = 1;
		private String name;
		private int bond; 
		private int order;
	//	private Rectangle box;
		
		public RectDraw(String name, int order, int bond){
			setPreferredSize(new Dimension(600,20));
			this.name = name;
			this.order = order;
			this.bond = bond;
		}
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);  
			for (int start = 100;start<900;start+=25){
		       	 g.drawRect(start,order*4,25,30);
		    }
			g.setFont(new Font("default", Font.BOLD, 16));
		    //g.drawString(name, 500, order*10);	
		}
	}
	
	
}
