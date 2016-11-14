package colors;


import java.util.Calendar;

import javax.swing.SwingWorker;

public class SingleDataProcess extends SwingWorker<Double,Void>{
	public RectDraw rectangle;
	double actual =0;
	String index;
	
	public SingleDataProcess(RectDraw rectangle) {
		// Auto-generated constructor stub
		this.rectangle = rectangle;
		this.index = rectangle.getIndex();
	}

	@Override
	protected Double doInBackground() throws Exception {
		
		System.out.println("\n>>>>>>>>>>>>>>>>>Starting doing in background for: "+rectangle.toString());
		while(true){
			actual =  DataProcess.getActual(index);
			System.out.print("\n\nTrying to get actual from $singleDataProcess for "+index+", actual="+actual);
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
		try{
			actual = get();
			System.out.print("\n>>>>>>>>>>>>>>Got THIS real-time data: "+actual+" for index "+index);
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

