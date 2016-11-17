package colors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

public class ColorUI extends JFrame implements ActionListener {

	private JPanel contentPane;


	Hashtable<String,Integer> selected = new Hashtable<String,Integer>();
	ArrayList<String> securitiesIndex = new ArrayList<String>();	//A list of ALL securities listed on panel (not NAME)
	ArrayList<String> securities = new ArrayList<String>();	//A list of all security NAMEs corresponding to securitiesIndex
	public ArrayList<String> indexSelected = new ArrayList<String>(); //A list of SELECTED securities indices
	public ArrayList<String> nameSelected =new ArrayList<String>(); //A list of SELECTED securities nickNames

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
					ColorUI frame = new ColorUI();
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
	public ColorUI() {
		
		//++++++++++++Set Panel+++++++++++++++++++		
		setTitle("Colors Version 2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 970, 950);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 45));	
		
		//+++++++++++++read all the vars from vars.csv+++++++++++++
		getIndices();
		
		
		//++++++++++++++Generate List for ALL available indicies: nicknames ++++++++++++++
		String[] toShow = securities.toArray(new String[securities.size()]);
		list = new JList<String>(toShow); //data has type Object[]
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setVisibleRowCount(30);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(500, 800));
		contentPane.add(listScroller);
		//++++++++++++++++Show previously saved++++++++++++++++
		try {
			getPrevious();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//++++++++++++++Generate List for SELECTED available indicies++++++++++++++
		selectedList = new JList<String>(nameSelected.toArray(new String[selected.size()]));
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		//list.setFixedCellHeight(20);
		JScrollPane selectedListScroller = new JScrollPane(selectedList);
		selectedListScroller.setPreferredSize(new Dimension(300, 500));
		contentPane.add(selectedListScroller);		
		
		//++++++++++++++++Save Button++++++++++++++++
		JButton save = new JButton("SAVE");
		save.addActionListener(this);
		contentPane.add(save);
		
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
		//++++++++++++++++Go Button++++++++++++
		JButton submit = new JButton("GO");
		submit.addActionListener(this);
		contentPane.add(submit);		
		
		JLabel note = new JLabel("NOTE: Every indicator except for DOE, Unemployment, Initial Claims is: A > E => buy (color red)\n");
		contentPane.add(note);
		
		
		setContentPane(contentPane);
		
	}

	/*
	 *  Return all the indicators available
	 */
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

	/*
	 * read .csv in db and update selected, nameSelected, indexSelected, model 
	 */
	private void getPrevious() throws Exception{
		BufferedReader br = null; String line= " ";
		String fullPath = "C:\\Users\\windows7\\Desktop\\JingyLiu\\db\\saved.csv";
		br = new BufferedReader(new FileReader(fullPath));
	    line = br.readLine();
	    while(line!=null){
        	  String[] tuples = line.split(",");
        	  //Add indices into securitiesIndex
        	  this.indexSelected.add(tuples[0]);
        	  this.nameSelected.add(tuples[1]);
        	  if(tuples[0].contains("DOE")|| tuples[0].contains("Retail")|| tuples[0].contains("Initial")) selected.put(tuples[0], 1);

        	  else selected.put(tuples[0], 0);
        	  line = br.readLine();
	    }
	    br.close();
	    //update jlist model
	    for (int i=0; i<nameSelected.size();i++){
	    	model.addElement(nameSelected.get(i));
	    }
	}
	
	/*
	 * save all the <index, names> into saved.csv
	 */
	public void saveNames(ArrayList<String> indicies,ArrayList<String> names) throws Exception{
		//System.out.println("saveNames argument: "+indicies+ " and "+names);
		String fullPath = "C:\\Users\\windows7\\Desktop\\JingyLiu\\db\\saved.csv";
		PrintWriter pw = new PrintWriter(new File(fullPath));
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<indicies.size();i++){
			sb.append(indicies.get(i));	sb.append(",");
			sb.append(names.get(i));	sb.append("\r\n");
		}
		 pw.write(sb.toString());
	     pw.close();
		System.out.println("=========================\nIn saveNames write: \n"+sb.toString()+"======================\n");
	}
	

	private void writeToDB(String index, String nickName){
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
			// Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(contentPane, "Write to indices.csv failed! Please make sure the file is closed and run again!");
			System.exit(1);
		}
		
	}
	
	private static void removeLastLine(){
		
		//TOCHECK:  Remove the line in indices.csv
		String fileName = "C:\\Users\\windows7\\Desktop\\JingyLiu\\db\\Indices.csv";
		try{
			RandomAccessFile f = new RandomAccessFile(fileName, "rw");
			long length = f.length() - 1;
			byte b;
			do {                     
				length -= 1;
				f.seek(length);
				 b = f.readByte();
			} while(b != 10);
			f.setLength(length+1);
			f.close();
			System.out.println("LastLine removed!");
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		
		//If a JButton is clicked
		if (source instanceof JButton){
			JButton o = (JButton) source;
			String label = o.getText();
			if(label.equals("ADD")){
				//Add selected into [selected];
				 int index = list.getSelectedIndex();
				 if(index<0) JOptionPane.showMessageDialog(contentPane, "Please first select an indicator and then add it!");
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
				//Delete elements from scroll pane;
				 int index = selectedList.getSelectedIndex();
				 if(index<0) JOptionPane.showMessageDialog(contentPane, "Please first select an indicator and then remove it!");
				 String toRemove = indexSelected.get(index);
				 model.removeElement(nameSelected.get(index));
				 nameSelected.remove(index);
				 indexSelected.remove(index);
				 //System.out.println("runtime now contain"+nameSelected+indexSelected);
				 if(!indexSelected.contains(toRemove))  selected.remove(toRemove);
				 selectedList.setModel(model);
			}
			
			//save to saved.csv
			else if (label.equals("SAVE")){
				try {
					saveNames(indexSelected,nameSelected);
					JOptionPane.showMessageDialog(contentPane, "Modification saved!");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
			//+++++++++++++++++++++Main+++++++++++++++++++++++
			else if(label.equals( "GO")){
				System.out.println("\n====================$ColorUI: Initializing bars===============");
				Bars bar = null;
				bar = new Bars(selected);
				System.out.println("====================$ColorUI: Bars initialization finished!===============\n");
				bar.setVisible(true);
				//----------------Start BACKGROUND Process here--------------------
				
				for(int i=0; i<indexSelected.size();i++){
					RectDraw rect = bar.rectangles.get(i);
					SingleDataProcess data = new SingleDataProcess(rect);
					data.execute();
				}
			}
		
		//++++++++++++++++++++++++++update database++++++++++++++++++++++++++++++++
		else{
			String index = newIndicator.getText();
			String nickName = newNickName.getText(); 
			System.out.print("Add a new index:"+index+", NickName is "+nickName);
			
			ArrayList<String> test = new ArrayList<String>();
			test.add(index);
			try{
				ArrayList<String> realName = DataProcess.getNames(test);
			}catch(Exception exce){
				 JOptionPane.showMessageDialog(contentPane, "The input is not a legit Bloomberg Index! Please copy paste from Bloomberg directly! ");
		    	  System.exit(1);
			}
			//valid bloomberg index name checked
			writeToDB(index, nickName);
			String command = "\"C:\\Program Files\\R\\R-3.3.1\\bin\\x64\\Rscript.exe\" \"C:/Users/windows7/Desktop/JingyLiu/db/getVar.r\"";
			try {
				Process process = Runtime.getRuntime().exec(command);
				 JOptionPane.showMessageDialog(contentPane, "Updating database......Please wait and DON\'T terminate program!");
				InputStream is = process.getInputStream();
			      InputStreamReader isr = new InputStreamReader(is);
			      BufferedReader br = new BufferedReader(isr);
			     
			      String line= br.readLine();
			      System.out.println("   >>Got from cmd:"+line);
			      if(line.charAt(line.length()-2)=='S'){
			    	  JOptionPane.showMessageDialog(contentPane, "Database Updated! Please restart the program! Any changes will show at the bottom!");
			    	  System.exit(0);
			      }
			      else{
			    	  
			    	  JOptionPane.showMessageDialog(contentPane, "Cannot get the Var value of the new indicator! Please modify indices.csv manually!");
			    	  removeLastLine();
			    	  System.exit(1);
			      }
				
			} catch (IOException exc) {	
				exc.printStackTrace();
				 JOptionPane.showMessageDialog(contentPane, "Oops something unknown source went wrong! Please do a sanity check on every file! ");
				System.exit(1);
			}
			
			
			}
		}
	}
}
