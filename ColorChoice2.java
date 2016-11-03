package colors2;


import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import colors.Bars;
import colors.DataProcess;

public class ColorChoice extends JFrame implements ActionListener{

	private JPanel contentPane;

	
	Hashtable<String,Integer> selected = new Hashtable<String,Integer>();
	//ArrayList<String> nickNames = new ArrayList<String>();	//A list of all securities in their personalized nicknames
	ArrayList<String> securitiesIndex = new ArrayList<String>();	//A list of all securities listed on panel (not NAME)
	ArrayList<String> securities = new ArrayList<String>();	//A list of all security NAMEs corresponding to securitiesIndex
	ArrayList<String> indexSelected = new ArrayList<String>();
	ArrayList<String> nameSelected =new ArrayList<String>();

	JList<String> selectedList;
	JList<String> list;
	DefaultListModel<String> model = new DefaultListModel<String>();
	
	JTextField newIndicator = new JTextField("New Index Here");
	JTextField newNickName = new JTextField("New nickname Here");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ColorChoice frame = new ColorChoice();
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
		//++++++++++++Set Panel+++++++++++++++++++		
		setTitle("Colors Version 2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 950, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 45));
		
		//+++++++++++++read all the vars from vars.csv+++++++++++++
		getIndices();
		
		
		//++++++++++++++Generate JList++++++++++++++
		String[] toShow = securities.toArray(new String[securities.size()]);
		list = new JList<String>(toShow); //data has type Object[]
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setVisibleRowCount(30);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(500, 800));
		contentPane.add(listScroller);
		
		//selected arrayList
		selectedList = new JList<String>(indexSelected.toArray(new String[selected.size()]));
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		//list.setFixedCellHeight(20);
		JScrollPane selectedListScroller = new JScrollPane(selectedList);
		selectedListScroller.setPreferredSize(new Dimension(300, 500));
		contentPane.add(selectedListScroller);
		
		//++++++++++++++++Submit Button++++++++++++
		JButton submit = new JButton("GO");
		submit.addActionListener(this);
		contentPane.add(submit);		
		
		//++++++++++++++++Add Button++++++++++++
		JButton add = new JButton("ADD");
		add.addActionListener(this);
		contentPane.add(add);
		//++++++++++++++++Delete Button++++++++++++
		JButton del = new JButton("REMOVE");
		del.addActionListener(this);
		contentPane.add(del);
		
		//++++++++++++++++Add New Indicator++++++++++++
		newIndicator.setPreferredSize( new Dimension( 150, 30 ) );
		contentPane.add(newIndicator);
		newNickName.setPreferredSize( new Dimension( 150, 30 ) );
		contentPane.add(newNickName);
		JButton addNew = new JButton("Add/Update new Index");
		addNew.addActionListener(this);
		contentPane.add(addNew);
		
		
		//++++++++++++++++Closing Button++++++++++++
		JButton close = new JButton("close");
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){System.exit(0);}
		});
		contentPane.add(close);
		
		JLabel note = new JLabel("NOTE: Every indicator except for DOE is: A > E => buy (color red)\n");
		contentPane.add(note);
		
		//++++++++++++++++Set ContentPane++++++++++++
		//JScrollPane scrollPane = new JScrollPane(contentPane);
		setContentPane(contentPane);
				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		
		//If a JButton is clicked
		if (source instanceof JButton){
			JButton o = (JButton) source;
			String label = o.getText();
			if(label.equals("ADD")){
				//TODO: Add selected into [selected];
				 int index = list.getSelectedIndex();
				 if(!selected.containsKey(securitiesIndex.get(index))){
					 if(securitiesIndex.get(index).contains("DOE")) selected.put(securitiesIndex.get(index), 1); 
					 else selected.put(securitiesIndex.get(index), 0);
					 indexSelected.add(securitiesIndex.get(index));
					 nameSelected.add(securities.get(index));
					//show on selectedList
					 model.addElement(securities.get(index));
					 selectedList.setModel(model);
				 }
				
			}
			else if(label.equals("REMOVE")){
				//TODO: Delete elements from scroll pane;
				 int index = selectedList.getSelectedIndex();
				 String toRemove = indexSelected.get(index);
				
				model.removeElement(nameSelected.get(index));
				 nameSelected.remove(index);
				 indexSelected.remove(index);
				 if(!indexSelected.contains(toRemove))  selected.remove(toRemove);
				 selectedList.setModel(model);
			}
			
			else if(label.equals( "GO")){
				Bars bar = null;
			//System.out.print("Passing argument to Bars: selected = "+selected);
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
		
		
		else{
			String index = newIndicator.getText();
			String nickName = newNickName.getText(); 
			System.out.print("Add a new index:"+index+", NickName is "+nickName);
			writeToDB(index, nickName);
			String command = "\"C:\\Program Files\\R\\R-3.3.1\\bin\\x64\\Rscript.exe\" \"C:/Users/windows7/Desktop/JingyLiu/db/getVar.r\"";
			try {
				Process process = Runtime.getRuntime().exec(command);
				 JOptionPane.showMessageDialog(contentPane, "Updating database......Please DON\'T terminate program!");
				InputStream is = process.getInputStream();
			      InputStreamReader isr = new InputStreamReader(is);
			      BufferedReader br = new BufferedReader(isr);
			      String line= br.readLine();
			      if(line.charAt(line.length()-2)=='S'){
			    	  JOptionPane.showMessageDialog(contentPane, "Database Updated! Please re-run the program! New addition will show at the bottom"
			    	  		+ "1");
			      }
			      else{
			    	  JOptionPane.showMessageDialog(contentPane, "Cannot get the Var value of the new indicator! Please exam if the BMG index is legit or modify the file manually!");
			      }
				
			} catch (IOException exc) {
				// TODO Auto-generated catch block
				exc.printStackTrace();
				JOptionPane.showMessageDialog(contentPane, "Add new indicator failed! Please don't re-try!");
				//TODO: Remove the line in indices.csv
			}
			
			
			}
		}
	}

	public void writeToDB(String index, String nickName){
		//TODO: append last line to indices.csv
		if(index.equals("")||nickName.equals("")) return;
		String fullPath = "C:\\Users\\windows7\\Desktop\\JingyLiu\\db\\indices.csv";
		Writer output;
		while(index.charAt(index.length()-1)==' ') index = index.substring(0,index.length()-1);
		while(index.charAt(0)==' ') index = index.substring(1,index.length());
		try {
			output = new BufferedWriter(new FileWriter(fullPath, true));
			output.append(index+","+nickName+"\r\n");
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(contentPane, "Write to indices.csv failed! Please close the file and run again!");
		}
		
	}
	
	public void getIndices(){
  		BufferedReader br = null; 
  		String line= " ";
  		try {
  	 		String fullPath = "C:\\Users\\windows7\\Desktop\\JingyLiu\\db\\vars.csv";
  	 		br = new BufferedReader(new FileReader(fullPath));
  	   		line = br.readLine();
  	   		line = br.readLine();	//skip the first line
  	          while(line!=null){
  	        	  String[] tuples = line.split(",");
  	        	  //Add indices into securitiesIndex
  	        	  this.securitiesIndex.add(tuples[0]);
  	        	  this.securities.add(tuples[1]);
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
	}
}
