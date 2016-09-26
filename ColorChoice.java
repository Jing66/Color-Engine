package colors;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class ColorChoice extends JFrame implements ActionListener, ItemListener{

	private JPanel contentPane;
	ArrayList<JCheckBox> choices ;
	ArrayList<String> selected = new ArrayList<String>();
	
	JLabel statusbar = new JLabel("initial");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ColorChoice frame = new ColorChoice();
					//pack
					frame.pack();
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
	public ColorChoice() {
//		JTextField jtf = new JTextField(20);
//		add(jtf);
		
		setTitle("Colors Version 1");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//add(new JLabel("NAME"));
		
		//TODO: securities to be replaced by searching the db directory
		String[] securities = {"NFP", "ISM", "GDP","Oil","Gasoline" };
		GridLayout test = new GridLayout((securities.length)+2,7);
		getContentPane().setLayout(test);
		choices = new ArrayList<JCheckBox>();
		for(String i: securities){
			JCheckBox choice = new JCheckBox(i, false);
			choice.setName(i);
			choice.addItemListener(this);
			choices.add(choice);
			getContentPane().add(choice);
			ButtonGroup buttonGroup = new ButtonGroup();
			JRadioButton bond = new JRadioButton("Bond", true);
			JRadioButton inverse = new JRadioButton("Inverse", false);
			buttonGroup.add(bond);
			buttonGroup.add(inverse);
			getContentPane().add(bond);
			getContentPane().add(inverse);
			
		}
		
		add(statusbar);
		
		getContentPane().add(Box.createRigidArea(new Dimension(0,5)));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
		
//		JPanel buttonPane = new JPanel();
//		buttonPane.setLayout(new BoxLayout(buttonPane,BoxLayout.Y_AXIS));
		
//###################### BUTTONS#############################
		
		//++++++++++++++++Submit Button++++++++++++
		JButton submit = new JButton("GO");
		submit.addActionListener(this);
		//submit.setBounds(10, 10, 10, 10);
		//submit.setPreferredSize(new Dimension(10,40));
		getContentPane().add(submit);
//		JPanel panel = new JPanel();
//		getContentPane().add(panel);
		
		//++++++++++++++++Closing Button++++++++++++
		JButton close = new JButton("close");
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		add(close);
		
		//++++++++++++++++Reset Button++++++++++++NEED OTHER THAN ANYNOUMOUS FUNCTION
		JButton reset = new JButton("reset");
		reset.addActionListener(this);
		add(reset);
//		buttonPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
//		setContentPane(contentPane);
		setSize(1000,1000);
//		buttonPane.setVisible(true);
	}
	
	
//#########################BUTTON LISTENER CLASS#################################	
	
		
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton o = (JButton) e.getSource();
		String label = o.getText();
		if(label.equals( "GO")){
			Bars bar = new Bars();
			bar.setVisible(true);
			System.out.println("Selected:"+selected);	//Print Selected
		}
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
	
		JCheckBox box = (JCheckBox) e.getSource();
		if (box.isSelected()){
			String label = box.getName();
			if(!selected.contains(label)) selected.add(label);
			
		}
		else{
			String label = box.getName();
			selected.remove(label);
		}
	}

}
