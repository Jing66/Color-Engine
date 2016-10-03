package colors;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TestPane extends JFrame  {

	private JPanel contentPane;
	private Rectangle box;
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
		setBounds(100, 100, 750, 600);
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(new Insets(150, 200, 150, 200)));
		//+++++++++++++++++++add BoxLayout+++++++++++++++++
		BoxLayout boxlayout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);  //Y_AXIS: vertical
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
		//contentPane.add(Box.createHorizontalGlue());  //Another way to create space
		contentPane.add(button1);
		
		contentPane.add(new JLabel("NFP"));
		RectDraw rect = new RectDraw();
		//QUESTION: how to add listener?????
		
		contentPane.add(rect);
		contentPane.add(new JLabel("ISM"));	//NOTE: it's far away.
		
		
		
		//++++++++++++++++++++++++++Scroll panel++++++++++++++++++++
		JScrollPane scrollPane = new JScrollPane(contentPane);
		setContentPane(scrollPane);
	}
	
	/***********************Events Listener***********************/
	class BooleanListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private static class RectDraw extends JPanel {
        public void paintComponent(Graphics g) {
        super.paintComponent(g);  
        
/*         g.drawRect(100,50,100,20);  	//args: (距离左边，距离上面,length,width)  
         //g.fillRect(130,50,300,20);  //fill it
         g.drawRect(200, 50, 100, 20);  //connect: x' = x + length
         g.drawRect(300, 50, 100, 20);  //connect: x' = x + length
         g.drawRect(230,120,100,20);  //draw a hollow rectangle
         g.setColor(Color.RED);	//set the border to be red
*/
        //generate 20 black rectangles connected together
        for (int start = 100;start<600;start+=50){
       	 g.drawRect(start,10,50,30);
        }
        g.drawString("150K", 350, 10);
        g.setColor(Color.BLACK);
        
        //Test Transparency
        Graphics2D g2d = (Graphics2D)g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.5));
        g.fillRect(350, 10, 65, 30);
 /*      
        //Event on certain rectangle
        Rectangle box = new Rectangle(5,10,20,30);
        Graphics2D g2 = (Graphics2D)g;
        g2.draw(box);
        
        //Simulate a change in data
        boolean change = false;
        try {
			Thread.sleep(2000);
			change = true;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        if(change) {g.fillRect(5,10,20,30); g.drawString("TestNumber", 350, 25);}
        */
        } 
     }

}
