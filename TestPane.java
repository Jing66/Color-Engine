package colors;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Hashtable;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TestPane extends JFrame implements ActionListener {

	private JPanel contentPane;
	RectDraw rectDraw;
	//protected EventListener myListener;
	
	RectDraw rect;
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
	 * @throws InterruptedException 
	 */
	public TestPane() throws InterruptedException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 600);
		contentPane = new JPanel();		
		//+++++++++++++++++++add BoxLayout+++++++++++++++++
		BoxLayout boxlayout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);  //Y_AXIS: vertical
		contentPane.setLayout(boxlayout);
		
			JLabel label1 = new JLabel("111111111111111111111111111111");
			label1.setAlignmentX(Component.CENTER_ALIGNMENT); //set label1 into center
			JLabel label2 = new JLabel("22");
			JLabel label3 = new JLabel("3333333");
			JButton button1 = new JButton("1");
			button1.addActionListener(this);
			button1.setAlignmentX(Component.CENTER_ALIGNMENT); 
			contentPane.add(label1);
			contentPane.add(Box.createRigidArea(new Dimension(0, 10))); //Create space between 2 components
			contentPane.add(label2);
			contentPane.add(label3);
			//contentPane.add(Box.createHorizontalGlue());  //Another way to create space
			contentPane.add(button1);
			contentPane.add(new JLabel("NFP"));	
			//draw rectangle
			rectDraw = new RectDraw("NAME",13.3,0,30,4.5);
			
/*			RectFill rectFill = new RectFill();
			contentPane.add(rectFill);*/
			setContentPane(rectDraw);
			
	//Re-generate graph test
/*			System.out.print("Start to generate stuff:\n");
			Component[] comp = contentPane.getComponents();
			for (int i = 0; i<comp.length;i++){
				System.out.print(comp[i].getClass().getName()+"\n");
				if(comp[i].getClass().getName().toString().equals("colors.TestPane$RectDraw") ){
					contentPane.remove(comp[i]);
					System.out.print("REMOVED!");
							
				}
			}
			contentPane.revalidate();
*/
	//Editable textField and listener test
/*			double exp = 14.5;
			expText = new JTextField(Double.toString(exp));
			expText.addActionListener((ActionListener) this);
			contentPane.add(expText);
*/			
			
		//+++++++++++++++++++++Label and buttons++++++++++++++++++
		
			
	}

	
	//++++++++++++++++++++++++++Draw Rectangle++++++++++++++++++++
/*	private static class RectDraw extends JPanel {
        boolean fill = false;
		public void paintComponent(Graphics g) {
        //super.paintComponent(g);  
        
        g.drawRect(100,50,100,20);  	//args: (距离左边，距离上面,length,width)  
         //g.fillRect(130,50,300,20);  //fill it
         g.drawRect(200, 50, 100, 20);  //connect: x' = x + length
         g.drawRect(300, 50, 100, 20);  //connect: x' = x + length
         g.drawRect(230,120,100,20);  //draw a hollow rectangle
         g.setColor(Color.RED);	//set the border to be red

        //generate 20 black rectangles connected together
        for (int start = 100;start<600;start+=50){
       	 g.drawRect(start,10,50,30);
        }
        g.drawString("150K", 350, 10);
        g.setColor(Color.BLACK);
        
        if(fill){
        	 g.drawRect(100,50,100,20);
			 g.fillRect(130,50,300,20);
        }
        
        //Test Transparency
//        Graphics2D g2d = (Graphics2D)g;
//        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.5));
//        g.fillRect(350, 10, 65, 30);
 
        } 
		public void setFill(boolean set){
			fill = set;
		}
     
     }
	*/
	@Override
	 public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source instanceof JTextField){
			System.out.print("Button clicked\n");
			rectDraw.setFill(true);
			this.revalidate();
			rect.repaint();
		}
	   }
}
