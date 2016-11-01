package colors2;


import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import colors.Bars;
import colors.DataProcess;

public class ColorChoice extends JFrame implements ActionListener{

	private JPanel contentPane;

	//Personalized fields and test git
	ArrayList<JCheckBox> choices ;
	Hashtable<String,Integer> selected = new Hashtable<String,Integer>();
	//ArrayList<String> nickNames = new ArrayList<String>();	//A list of all securities in their personalized nicknames
	ArrayList<String> securitiesIndex = new ArrayList<String>();	//A list of all securities listed on panel (not NAME)
	ArrayList<String> securities = new ArrayList<String>();	//A list of all security NAMEs corresponding to securitiesIndex
	ArrayList<String> indexSelected = new ArrayList<String>();
	ArrayList<String> nameSelected =new ArrayList<String>();

	JList<String> selectedList;
	JList<String> list;
	DefaultListModel<String> model = new DefaultListModel<String>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ColorChoice frame = new ColorChoice();
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
		setBounds(100, 100, 950, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));

		//+++++++++++++++Set GridBagLayout+++++++++++++++++
		/*contentPane.setLayout(new GridBagLayout());
		GridBagConstraints left = new GridBagConstraints();
		left.anchor = GridBagConstraints.WEST;
		left.weightx = 3.0;
		GridBagConstraints right = new GridBagConstraints();
		right.weightx = 3.0;
	    right.fill = GridBagConstraints.HORIZONTAL;
		right.gridwidth = GridBagConstraints.REMAINDER;
		GridBagConstraints middle = new GridBagConstraints();
		middle.weightx = 3.0;*/
		
		//+++++++++++++read all the vars from vars.csv+++++++++++++
		getIndices();
		
		//++++++++++++++Generate JList++++++++++++++
		String[] toShow = securities.toArray(new String[securities.size()]);
		list = new JList<String>(toShow); //data has type Object[]
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(8);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(500, 1000));
		contentPane.add(listScroller);
		
		//selected arrayList
		selectedList = new JList<String>(indexSelected.toArray(new String[selected.size()]));
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		JScrollPane selectedListScroller = new JScrollPane(selectedList);
		listScroller.setPreferredSize(new Dimension(300, 300));
		contentPane.add(selectedListScroller);
		
		//++++++++++++++++Add Button++++++++++++
		JButton add = new JButton("ADD");
		add.addActionListener(this);
		contentPane.add(add);
		//++++++++++++++++Delete Button++++++++++++
		JButton del = new JButton("REMOVE");
		del.addActionListener(this);
		contentPane.add(del);
		//++++++++++++++++Submit Button++++++++++++
		JButton submit = new JButton("GO");
		submit.addActionListener(this);
		contentPane.add(submit);
		//++++++++++++++++Closing Button++++++++++++
		JButton close = new JButton("close");
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){System.exit(0);}
		});
		contentPane.add(close);
		
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
					 selected.put(securitiesIndex.get(index), 0);
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
				selected.remove(indexSelected.get(index));
				model.removeElement(nameSelected.get(index));
				 nameSelected.remove(index);
				 indexSelected.remove(index);
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
  	        	 // nickNames.add(tuples[1]);
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
