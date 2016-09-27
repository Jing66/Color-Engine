package colors;
/**
 * Bars: Second panel to generate animation
 * @author liujingyun
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.Box;
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
					test.put("b",0);test.put("c",0);
					Bars frame = new Bars(test);
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
		setBounds(100, 100, 450, 300);
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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(8, 10, 8, 10));
		contentPane.setLayout(new BorderLayout(5, 10));
		setContentPane(contentPane);
		//grid layout
		GridLayout test = new GridLayout((choices.size())+2,1);
		getContentPane().setLayout(test);
		Enumeration<String> keys = choices.keys();
		while (keys.hasMoreElements()){
			
			String i = keys.nextElement();
			PanelBars barPane = new PanelBars(i);
			
			getContentPane().add(barPane);
			contentPane.add(Box.createRigidArea(new Dimension(10,10)));
			
		}
		
	}
	
	/**********************PanelBar Class***********************/
	class PanelBars extends JPanel{
		private JPanel pane;
		
		public PanelBars(String i){
			pane = new JPanel();
			SpringLayout layout = new SpringLayout();
			
			setLayout(layout);
			//Add label
			JLabel label = new JLabel(i);
			//Add table
			DefaultTableModel model = new DefaultTableModel(1,50);
			
			JTable table = new JTable(model);
			layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.EAST,pane);
			setLayout(layout);
			
			add(label);
			add(table);
			
		}
		
	}
	
	/**********************TableBar Class***********************/
	class BarTable extends JTable{
		private JTable table;
		
		public BarTable(){
			DefaultTableModel model = new DefaultTableModel(1,50);
			JTable table = new JTable(model);
			this.table=table;
		}
	}

	
	
}
