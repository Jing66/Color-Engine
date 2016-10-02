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


public class ColorChoice extends JFrame implements ActionListener{
	
	private JPanel contentPane;
	//Personalized fields and test git
	ArrayList<JCheckBox> choices ;
	Hashtable<String,Integer> selected = new Hashtable<String,Integer>();
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ColorChoice frame = new ColorChoice();
					//pack
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
	public ColorChoice() {
		
		//++++++++++++Add ScrollPanel+++++++++++++++++++		
		setTitle("Colors Version 1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));

		//+++++++++++++++Set GridBagLayout+++++++++++++++++
		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints left = new GridBagConstraints();
		left.anchor = GridBagConstraints.WEST;
        left.weightx = 3.0;
        GridBagConstraints right = new GridBagConstraints();
        right.weightx = 3.0;
        right.fill = GridBagConstraints.HORIZONTAL;
        right.gridwidth = GridBagConstraints.REMAINDER;
        GridBagConstraints middle = new GridBagConstraints();
        middle.weightx = 3.0;
		/******************Get files from /db*******************/
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
		
		GridLayout test = new GridLayout((securities.size())+2,4);
		getContentPane().setLayout(test);
		choices = new ArrayList<JCheckBox>();
		for(String i: securities){
			//Add a Checkbox
			JCheckBox choice = new JCheckBox(i, false);
			choice.setName(i);
			choice.addActionListener(this);
			choices.add(choice);
			contentPane.add(choice,left);
			//Group radioButton. 
			ButtonGroup buttonGroup = new ButtonGroup();
			JRadioButton bond = new JRadioButton("Bond", false);
			bond.setActionCommand(i+"=BOND");
			JRadioButton inverse = new JRadioButton("Inverse", false);
			inverse.setActionCommand(i+"=INVERSE");
			buttonGroup.add(bond);
			buttonGroup.add(inverse);
			//buttonGroup.add(choice);
			//Add RadioButton
			contentPane.add(bond,middle);
			contentPane.add(inverse,right);
			bond.addActionListener(this);
			inverse.addActionListener(this);
		}
		
	
		//++++++++++++++++Submit Button++++++++++++
		JButton submit = new JButton("GO");
		submit.addActionListener(this);
		//submit.setBounds(10, 10, 10, 10);
		//submit.setPreferredSize(new Dimension(10,40));
		contentPane.add(submit);

		//++++++++++++++++Closing Button++++++++++++
		JButton close = new JButton("close");
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		contentPane.add(close);
		
		JScrollPane scrollPane = new JScrollPane(contentPane);
		setContentPane(scrollPane);
		
	}
	
	
//#########################GO TO NEXT PANEL#################################	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		//If a JButton is clicked
		if (source instanceof JButton){
			JButton o = (JButton) source;
			String label = o.getText();
			if(label.equals( "GO")){
				Bars bar = new Bars(selected);
				bar.pack();
				//TODO: In Bars class, modify grids of empty color bars and while loop
				bar.setVisible(true);
				System.out.println("Selected:"+selected);	//Print Selected
			}
		}
		
		//If RadioButton is selected
		if(source instanceof JRadioButton){
			JRadioButton o = (JRadioButton)source;
			String commandFull = o.getActionCommand();
			String item = commandFull.split("=")[0];
			
			String command = commandFull.split("=")[1];
			int commandInt = 0;
			
			if(command.equals("INVERSE")){ commandInt = 1;}
			
			//put command into hashtable
			selected.put(item, commandInt);
			
		}
		
		if (source instanceof JCheckBox){
			AbstractButton box = (AbstractButton) e.getSource();
			if (box.isSelected()){
				String label = box.getName();
				if(!selected.contains(label)) selected.put(label,0);
			}
			else{
				String label = box.getName();
				selected.remove(label);
			}
		}
		
	}

//	@Override
//	public void itemStateChanged(ItemEvent e) {
//		AbstractButton box = (AbstractButton) e.getSource();
//		if (box.isSelected()){
//			String label = box.getName();
//			if(!selected.contains(label)) selected.put(label,0);
//		}
//		else{
//			String label = box.getName();
//			selected.remove(label);
//		}
//	}

}
