package colors;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class ColorChoice extends JFrame implements ActionListener, ItemListener{
	
	private JPanel contentPane;
	//Personalized fields and test git
	ArrayList<JCheckBox> choices ;
	ArrayList<String> selected = new ArrayList<String>();
	
	
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
		setTitle("Colors Version 1");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		/*Get files from /db*/
		ArrayList<String> securities = new ArrayList<String>();
		File folder = new File("/Users/liujingyun/Desktop/Docs"); //NOTE: Path to be changed
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
		    if (file.isFile()) {
		        String fullName = file.getName();
		        String name = fullName.split("\\.")[0];
		        securities.add(name);
		    }
		}
		
		//String[] securities = {"NFP", "ISM", "GDP","Oil","Gasoline" };
		//TODO: make a scroll panel and make the layout look better
		GridLayout test = new GridLayout((securities.size())+2,4);
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
	}
	
	
//#########################GO TO NEXT PANEL#################################	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton o = (JButton) e.getSource();
		String label = o.getText();
		if(label.equals( "GO")){
			Bars bar = new Bars(selected);
			bar.pack();
			//TODO: In Bars class, modify grids of empty color bars and while loop
			bar.setVisible(true);
			System.out.println("Selected:"+selected);	//Print Selected
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
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
