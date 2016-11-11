package colors;


import java.util.Calendar;

import javax.swing.SwingWorker;

public class SingleDataProcess extends SwingWorker<Double,Void>{
	public RectDraw rectangle;
	double actual =0;
	String index;
	
	public SingleDataProcess(RectDraw rectangle, String index) {
		// Auto-generated constructor stub
		this.rectangle = rectangle;
		this.index = index;
		System.out.println("\n====================Create a new thread of rectangle!");
	}

	@Override
	protected Double doInBackground() throws Exception {
		
		System.out.println("Starting doing in background: "+index);
		while(true){
			actual =  DataProcess.getActual(index);
			System.out.print("\n\nGetting a test actual from $singleDataProcess for "+index+" actual="+actual);
			int minute = Calendar.getInstance().get(Calendar.MINUTE);
			int second = Calendar.getInstance().get(Calendar.SECOND);
			if(actual !=0){break;}
			//pause and keep looping
			if (minute%5==0 && second <2 || minute%5==4 && second >57) {
				Thread.sleep(10);
			}
			else {Thread.sleep(7000);}
		}
		return actual;
	}
	
	
	@Override
	protected void done(){
		System.out.println("\nThis thread is ending....");
		try{
			actual = get();
			System.out.print(">>>>>>>>>>>>>>Got THIS real-time data: "+actual);
		}
		catch(Exception e){
			System.out.print("\n==========CANNOT GET BMG ACTUALS FROM $SingleDataProcess.DoInBackground==========\n");
			e.printStackTrace();
		}
		rectangle.setActual(actual);
		rectangle.setFill(true);
		rectangle.repaint();
	}
	
}

