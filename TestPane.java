package colors;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class TestPane extends JFrame  {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestPane frame = new TestPane();
					frame.setVisible(true);
					//frame.pack();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestPane() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(new Insets(150, 200, 150, 200)));
		//+++++++++++++++++++add BoxLayout+++++++++++++++++
		BoxLayout boxlayout = new BoxLayout(contentPane, BoxLayout.PAGE_AXIS);  //Y_AXIS: vertical
		contentPane.setLayout(boxlayout);
		
		//+++++++++++++++++++add GridBagLayout+++++++++++++++
/*		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints left = new GridBagConstraints();
		left.anchor = GridBagConstraints.WEST;
        left.weightx = 3.0;
        GridBagConstraints right = new GridBagConstraints();
        right.weightx = 3.0;
        right.fill = GridBagConstraints.HORIZONTAL;
        right.gridwidth = GridBagConstraints.REMAINDER;
        
        GridBagConstraints middle = new GridBagConstraints();
        middle.weightx = 3.0;
*/
		//+++++++++++++++++++++Label and buttons++++++++++++++++++
		JLabel label1 = new JLabel("111111111111111111111111111111");
		label1.setAlignmentX(Component.CENTER_ALIGNMENT); //set label1 into center
		JLabel label2 = new JLabel("22");
		JLabel label3 = new JLabel("3333333");
		JButton button1 = new JButton("1");
		button1.setAlignmentX(Component.CENTER_ALIGNMENT); 
		contentPane.add(label1);
		contentPane.add(Box.createRigidArea(new Dimension(0, 10))); //Create space between 2 components
		contentPane.add(label2);
		contentPane.add(label3);
		contentPane.add(Box.createHorizontalGlue());  //Another way to create space
		contentPane.add(button1);
		//++++++++++++++++++++++++++Scroll panel++++++++++++++++++++
		JScrollPane scrollPane = new JScrollPane(contentPane);
		setContentPane(scrollPane);
	}

}
