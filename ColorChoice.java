package colors;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class ColorChoice extends JFrame implements ActionListener{
	
	private JPanel contentPane;
	//Personalized fields and test git
	ArrayList<JCheckBox> choices ;
	Hashtable<String,Integer> selected = new Hashtable<String,Integer>();
	ArrayList<String> securitiesIndex = new ArrayList<String>();	//A list of all securities listed on panel (not NAME)
	ArrayList<String> securities = new ArrayList<String>();	//A list of all security NAMEs corresponding to securitiesIndex
	
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
	 * <p>
	 * Go through database to get all the available indicators and list
	 * them on the frame. Generate corresponding choices of fields (BOND
	 * : high = buy; INVERSE: high = sell) and remember user's choices
	 * generate new frames according to user's choice
	 */
	public ColorChoice() {
		//++++++++++++Add ScrollPanel+++++++++++++++++++		
		setTitle("Colors Version 1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));

		//+++++++++++++++Set Layout+++++++++++++++++
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
        
      //++++++++++++Read all Indices for which we have Var into securitiesIndex+++++++++++++++++++	
		BufferedReader br = null; 
		String line= " ";
		try {
	 		String fullPath = "C:\\Users\\windows7\\Desktop\\JingyLiu\\db\\vars.csv";
	 		br = new BufferedReader(new FileReader(fullPath));
	           //======The data is located at 4th line last cell=====
	   		line = br.readLine();
	          while(line!=null){
	        	  String[] tuples = line.split(",");
	        	  //Add indices into securitiesIndex
	        	  securitiesIndex.add(tuples[0]);
	        	  line = br.readLine();
	          }
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
		
		//Get the names of the indicators and show
		try {
			securities = DataProcess.getNames(securitiesIndex);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.print("\n===============ERROR GETTING SECURITY NAMES============");
		}
		//Show the names to the users to choose
		//NOTE: When pass to bars, pass security name not NAME field
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
	/**
	 * Respond to the choices of an indicator and its corresponding field
	 * <p>
	 * When user chooses an indicator, click on the checkbox or radiobox, 
	 * record user's choice so that it can be passed as argument into next
	 * step
	 * @param  e Click of some button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		//If a JButton is clicked
		if (source instanceof JButton){
			JButton o = (JButton) source;
			String label = o.getText();
			if(label.equals( "GO")){
				Bars bar = null;
				
				try {
					bar = new Bars(selected);
				} catch (Exception e1) {
					e1.printStackTrace();
					System.out.print("\n==========ERROR generating BARS============");
				}
				
				bar.setVisible(true);
				//----------------Start BACKGROUND Process here--------------------
				DataProcess data = new DataProcess(selected,bar);
				data.execute();
			}
		}
		
		//If RadioButton is selected
		if(source instanceof JRadioButton){
			JRadioButton o = (JRadioButton)source;
			String commandFull = o.getActionCommand();
			//--------------NOTE: change NAME to INDEX-----------------
			String item = getSecurity(commandFull.split("=")[0]);	
			
			String command = commandFull.split("=")[1];
			int commandInt = 0;		
			if(command.equals("INVERSE")){ commandInt = 1;}		
			//put command into hashtable
			selected.put(item, commandInt);	
		}
		if (source instanceof JCheckBox){
			AbstractButton box = (AbstractButton) e.getSource();
			if (box.isSelected()){
				//--------------NOTE: change NAME to INDEX-----------------
				String label = getSecurity(box.getName());
				if(!selected.contains(label)) selected.put(label,0);
			}
			else{
				String label = getSecurity(box.getName());
				selected.remove(label);
			}
		}
		
	}

	
	
	private String getSecurity(String name) {
		int i = securities.indexOf(name);
		return securitiesIndex.get(i);
	}

}
