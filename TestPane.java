package colors;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

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
		//+++++++++++++++++++add BoxLayout
		//BoxLayout boxlayout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);
		//contentPane.setLayout(boxlayout);
		//add GridBagLayout
		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.EAST;
        GridBagConstraints right = new GridBagConstraints();
        right.weightx = 52.0;
        right.fill = GridBagConstraints.HORIZONTAL;
        right.gridwidth = GridBagConstraints.REMAINDER;
		
		//+++++++++++++++++++++add conponents
		JLabel label1 = new JLabel("1");
		JLabel label2 = new JLabel("2");
		JLabel label3 = new JLabel("3");
		JButton button1 = new JButton("1");
		contentPane.add(label1,left);
		contentPane.add(label2,right);
		contentPane.add(label3,left);
		//contentPane.add(BorderLayout.SOUTH, button1);
		//+++++++++++++++++add scroll panel
		JScrollPane scrollPane = new JScrollPane(contentPane);
		setContentPane(scrollPane);
	}

}
