package colors;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import com.bloomberglp.blpapi.CorrelationID;
import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Event;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.MessageIterator;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;
import com.bloomberglp.blpapi.SessionOptions;
import com.bloomberglp.blpapi.Subscription;
import com.bloomberglp.blpapi.SubscriptionList;

/**
 * process data: get Var from excel, get expectation from Bloomberg, get real-time data from 
 * 	Bloomberg
 * @author liujingyun
 *
 */
public class DataProcess extends SwingWorker<ArrayList<Double>,Void>{
	private JFrame bar;
	private Hashtable<String,Integer> indicators = new Hashtable<String,Integer>();
	ArrayList<Double> actuals = new ArrayList<Double>();
	public ArrayList<String> securities = new ArrayList<String>();
	public ArrayList<String> securitiesIndex = new ArrayList<String>();
	
	public DataProcess(Hashtable<String,Integer> indicators, JFrame bar) {
	// TODO Auto-generated constructor stub
		this.indicators = indicators;
		this.bar = bar;
		try {
			securitiesIndex = new ArrayList<String>(indicators.keySet());
			securities = getNames(securitiesIndex);
			System.out.println("CHOSEN SECURITIES NAMES:"+securities);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("================CANNOT CONSTRUCT DATAPROCESS==================");
		}
	}

	/************Read variable from the H4 cell********/
	public static double getVar(String index){
	double var=0;
	/*++++++++++++++++++Open an csv file instead++++++++++++++++++*/
	BufferedReader br = null; 
	String line= " ";
	try {
 		String fullPath = "C:\\Users\\windows7\\Desktop\\JingyLiu\\db\\Vars.csv";
 		br = new BufferedReader(new FileReader(fullPath));
           //======The data is located at 4th line last cell=====
   		line = br.readLine();
          while(line!=null){
        	  //System.out.print(line+" next line: \n");
        	  String[] tuples = line.split(",");
        	  if(tuples[0].equals(index)) {var = Double.parseDouble(tuples[2]);break;}
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

	return var;
	}
	
	
	/***********Get Expectation  value of an indicator ****************************/
	/**
	 * Returns the Bloomberg survey data of given securities 
	 * @param  indicator the securities to get field
	 * @return      data to return
	 */
	public static double getExp(String indicator) throws Exception {
		indicator = indicator.substring(1,indicator.length()-1);
		double output = 0;
		SessionOptions sessionOptions = new SessionOptions();
 		sessionOptions.setServerHost("localhost");
 		sessionOptions.setServerPort(8194);
 		Session session = new Session(sessionOptions);
 		if (!session.start()) {
		 System.out.println("Could not start session FROM getExp.start.");
 		System.exit(1);
 		}
 		if (!session.openService("//blp/refdata")) {
 		System.out.println("Could not open service " +
 		"//blp/refdata");
 		System.exit(1);
 		}
 		Service refDataSvc = session.getService("//blp/refdata");
		 Request request = refDataSvc.createRequest("ReferenceDataRequest");
 		request.append("securities", indicator);
 		request.append("fields", "NAME");
 		request.append("fields", "RT_BN_SURVEY_MEDIAN");
 		request.append("fields", "LAST_PRICE");
 		request.append("fields", "TIME");
 		session.sendRequest(request, null);
 		boolean continueToLoop = true;
 		while (continueToLoop) {
 		 Event event = session.nextEvent();
 		 switch (event.eventType().intValue()) {
 		 	case Event.EventType.Constants.RESPONSE: // final event
 		 		continueToLoop = false; // fall through
 		 	case Event.EventType.Constants.PARTIAL_RESPONSE:
 		 		output = handleResponseEvent(event);
 		 		break;
 		 	default:
 		 		//handleOtherEvent(event);
 		 		break;
 		 	}
 		 }
 		return output;
	}
	
	 private static double handleResponseEvent(Event event) throws Exception {
		 double output = 0;
		
		 MessageIterator iter = event.messageIterator();
		 while (iter.hasNext()) {
			 Message message = iter.next();
			// System.out.print(message);
			 Element ReferenceDataResponse = message.asElement();
			 Element securityDataArray =  ReferenceDataResponse.getElement("securityData");
			 //parse
			 int numItems = securityDataArray.numValues();
			 for (int i = 0; i < numItems; ++i) {
				 Element securityData = securityDataArray.getValueAsElement(i);
				 Element fieldData =securityData.getElement("fieldData");
				 //String NAME = fieldData.getElementAsString(" NAME");
				 double exp = fieldData.getElementAsFloat64("RT_BN_SURVEY_MEDIAN");
				 //double actual =fieldData.getElementAsFloat64("LAST_PRICE");
				 output = exp; 
			 }
			 //message.print(System.out);
		 	}
			 return output;
		 }
	
	/***********Get Actual value of an indicator ****************************/
	/**
	 * Returns the real-time data of given securities 
	 * @param  indicator the securities to get field
	 * @return      data to return
	 */
	//return 0 if data not ready
	public static double getActual(String indicator) throws Exception {
		indicator = indicator.substring(1,indicator.length()-1);
		double output = 0;
		SessionOptions sessionOptions = new SessionOptions();
		 sessionOptions.setServerHost("localhost");
		 sessionOptions.setServerPort(8194);
		 Session session = new Session(sessionOptions);
		 if (!session.start()) {
			 System.out.println("Could not start session FROM getActual session.start");
			 System.exit(1);
		 }
		 if (!session.openService("//blp/mktdata")) {
			 System.err.println("Could not start session FROM getActual sessions.openService.");
			 System.exit(1);
		 }
		 CorrelationID subscriptionID = new CorrelationID(2);
		 SubscriptionList subscriptions = new SubscriptionList();
		 subscriptions.add(new Subscription(indicator,"LAST_PRICE", "RT_BN_SURVEY_MEDIAN",subscriptionID));
		 session.subscribe(subscriptions);
		 while (true) {
			 Event event = session.nextEvent();
			 switch (event.eventType().intValue()) {
			 	case Event.EventType.Constants.SUBSCRIPTION_DATA:
			 			output = handleDataEvent(event);
			 			return output;
			 	default:
			 		break;
			 	}
		 }
	}
	
	//return 0 if data is not ready
	 private static double handleDataEvent(Event event) throws Exception {
		 double output = 0;
		 MessageIterator iter = event.messageIterator();
		 while (iter.hasNext()) {
			 Message message = iter.next();
//	 System.out.print(message);
			 Element ReferenceDataResponse = message.asElement();
			 
			 //parse
			 if(ReferenceDataResponse.hasElement("LAST_PRICE") && ReferenceDataResponse.hasElement("TIME")){
				 double actual =ReferenceDataResponse.getElementAsFloat64("LAST_PRICE");
				 String date = ReferenceDataResponse.getElementAsString("TIME");
				 if (date.contains(":")){	//if it's today's data
						output = actual;
					}
			 }
			 else{
				 System.out.print("\n!!!Does Not Contain LAST_PRICE or TIME\n");
				 System.out.print(message);
			 }
//				System.out.print("====PARSED:=========\n"+"TIME= "+date+"\nactual=" + actual);
			 }
			 return output;
		 }
	 
	 
	 /***********Get Names of an ArrayList of indicators ****************************/
	 /**
		 * Returns the names of a list of given securities 
		 * @param  securities the list of securities to get names
		 * @return      the list of names parsed from the message
		 */
	 public static ArrayList<String> getNames(ArrayList<String> securities) throws Exception{
		//System.out.print("\n arguments = "+securities);
		 ArrayList<String> names = new ArrayList<String>();
		 SessionOptions sessionOptions = new SessionOptions();
	 		sessionOptions.setServerHost("localhost");
	 		sessionOptions.setServerPort(8194);
	 		Session session = new Session(sessionOptions);
	 		if (!session.start()) {
			 System.out.println("Could not start session from GETNAMES.");
	 		System.exit(1);
	 		}
	 		if (!session.openService("//blp/refdata")) {
	 		System.out.println("Could not open service " +
	 		"//blp/refdata");
	 		System.exit(1);
	 		}
	 		//Setup a query
	 		Service refDataSvc = session.getService("//blp/refdata");
			Request request = refDataSvc.createRequest("ReferenceDataRequest");
	 		//loop to append all securities
			for (int i =0; i < securities.size();i++){
				String securityIndex = securities.get(i).substring(1, securities.get(i).length()-1);
				request.getElement("securities").appendValue(securityIndex);
			}
			request.getElement("fields").appendValue("NAME");
	 		session.sendRequest(request, null);
	 		//Handle response
	 		boolean continueToLoop = true;
	 		while (continueToLoop) {
	 		 Event event = session.nextEvent();
	 		 switch (event.eventType().intValue()) {
	 		 	case Event.EventType.Constants.RESPONSE: // final event
	 		 		continueToLoop = false; // fall through
	 		 	case Event.EventType.Constants.PARTIAL_RESPONSE:
	 		 		names = handleNameResponseEvent(event);
	 		 		break;
	 		 	default:
	 		 		//handleOtherEvent(event);
	 		 		break;
	 		 	}
	 		 }
		 
		 return names;
	 }
	 
	 
	private static ArrayList<String> handleNameResponseEvent(Event event) {
		ArrayList<String> names = new ArrayList<String>();
		
		MessageIterator iter = event.messageIterator();
		 while (iter.hasNext()) {
			 Message message = iter.next();
			 System.out.print(message);
			 Element ReferenceDataResponse = message.asElement();
			 Element securityDataArray =  ReferenceDataResponse.getElement("securityData");
			 //parse
			 int numItems = securityDataArray.numValues();
			 for (int i = 0; i < numItems; ++i) {
				 Element securityData = securityDataArray.getValueAsElement(i);
				 Element fieldData =securityData.getElement("fieldData");
				 String name = fieldData.getElementAsString("NAME");
				 if(name!=null){
					 names.add(name);
				 }
			 }
		 	}
		return names;
	}

	
	/** TEST
	 * @throws Exception ****************************/
	public static void main(String[] args) throws Exception{
		System.out.print(DataProcess.getVar("NAPMPMI  Index"));
		/*
		 * CARSCHNG Index (Retail sales)
		 * DOENUSCH Index (DOE)
		 */
		
		//TEST: getActual ----PASS
		/*System.out.print("\n==============GET Actual TESTING====================\n");
		System.out.print("output= "+ DataProcess.getActual("CARSCHNG Index"));*/
		
		//TEST: getExp ----PASS
		/*System.out.print("\n==============GET Expectation TESTING====================\n");
		System.out.print("output= "+ DataProcess.getExp("CARSCHNG Index"));*/
		
		//TEST: getNames ----PASS
/*		System.out.print("\n==============GET NAMES TESTING===========\n");
		ArrayList<String> test = new ArrayList<String>();
		test.add("CAHSTOTL Index");
		test.add("NAPMPMI  Index");
		//add more securities here
		System.out.print(DataProcess.getNames(test));
*/
	}

	@Override
	protected ArrayList<Double> doInBackground() throws Exception {
		System.out.println("\nPrinting from DataProcess:\n INDICATORS Hashtable:"+indicators);
		System.out.println("SecuritiesIndex:" + securitiesIndex);
		System.out.println("SECURITIES field:"+securities+"\n====================================\n");
		
		ArrayList<Double> output = new ArrayList<Double>();
		
		//get the actual of each indicator
		int count=0;	//set a loop timeout for testing
		double actual = 0;
		while(count<100){
			actual =  getActual(securitiesIndex.get(0));
			System.out.print("GOT The first actual data from background! data= "+actual);
			if(actual !=0) break;
			count++;
			Thread.sleep(150);
		}
		for (int i =0; i < indicators.size();i++){
			actual = getActual(securitiesIndex.get(i));
			output.add(i, actual);
		}
		if(actual==0) System.out.print("\n!!!!!!!!!!!!BACKGROUND PROCESS TIMEOUT!!!!!!!!!!!!");
		System.out.print("\nRETURN from background now");
		return output;
	}
	
	@Override
	protected void done(){JPanel contentPane = (JPanel) bar.getContentPane();
	//get actuals order same with securities
try{
		actuals = get();
		System.out.print("\n>>>>>>>>>>>>>>Get all the real-time data: "+actuals);
	}
	catch(Exception e){
		System.out.print("\n==========CANNOT GET BMG ACTUALS FROM $DataProcess.DoInBackground==========\n");
		e.printStackTrace();
	}
	// fill colors
	for(int i=0;i<Bars.rectangles.size();i++){
		System.out.print("\n++++++Repainting "+i+"th rectangle!++++");
		System.out.println(Bars.rectangles.get(i).toString());
		Bars.rectangles.get(i).setActual(actuals.get(i));
		Bars.rectangles.get(i).setFill(true);
		contentPane.revalidate();
		Bars.rectangles.get(i).repaint();
		
	}
	
}


}