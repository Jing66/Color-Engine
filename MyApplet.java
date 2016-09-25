package colors;

import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class MyApplet extends JApplet implements Runnable{

	public static final Dimension SCREEN_SIZE = new Dimension(800,800);
	public static final String TITLE = "Test1";
	public static Boolean isRunning = true;
	public static JFrame frame;
	/**
	 * Create the applet.
	 */
	public MyApplet() {
		setPreferredSize(SCREEN_SIZE);
		frame = new ColorChoice();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		init();
		while(isRunning){
			
		}
	}
	public void start(){
		init();
	}
	public void init(){
		
	}
	
	public void stop(){
		isRunning = false;
	}
	
	public static void main(String[] args){
		MyApplet test1 = new MyApplet();
		frame.setTitle(TITLE);
		frame.setVisible(true);  //IMPORTANT TO SEE
		test1.start();
	}

}
