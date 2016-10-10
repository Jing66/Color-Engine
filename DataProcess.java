package colors;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Event;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.MessageIterator;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;
import com.bloomberglp.blpapi.SessionOptions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;

/**
 * process data: get Var from excel, get expectation from Bloomberg, get real-time data from 
 * 	Bloomberg
 * @author liujingyun
 *
 */


public class DataProcess extends EventObject {
	private Data data;
	
	public DataProcess(Object source, Data data) {
	// TODO Auto-generated constructor stub
		super(source);
		this.data = data;
	}
	
	

	/************Read variable from the H4 cell********/
	public static double getVar(String fileName){
	double var=0;
	/*++++++++++++++++++Open an csv file instead++++++++++++++++++*/
	BufferedReader br = null; 
	String line= " ";
	try {
 		String fullPath = "C:\\Users\\windows7\\Desktop\\JingyLiu\\db\\"+fileName;
 		br = new BufferedReader(new FileReader(fullPath));
           //======The data is located at 4th line last cell=====
   		line = br.readLine();
           line = br.readLine();
           line = br.readLine();
           line = br.readLine();
           String[] values = line.split(",");
           var = Double.parseDouble(values[values.length-1]);

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
	
	/***********Get expectation[0] and Actual[1]  value of an indicator ****************************/
	/**
	 * Returns the expectation data or real-time data of given securities 
	 * @param  indicator the securities to get field
	 * @param  field  which field to return. 0: expected value. 1: real-time data.
	 * @return      data to return
	 */
	//return 0 if data not ready
	public static double getBMG(String indicator, int field) throws Exception {
		double output = 0;
		SessionOptions sessionOptions = new SessionOptions();
 		sessionOptions.setServerHost("localhost");
 		sessionOptions.setServerPort(8194);
 		Session session = new Session(sessionOptions);
 		if (!session.start()) {
		 System.out.println("Could not start session.");
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
 		//request.append("fields", "NAME");
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
 		 		output = handleResponseEvent(event, field);
 		 		break;
 		 	default:
 		 		//handleOtherEvent(event);
 		 		break;
 		 	}
 		 }
 		return output;
	}
	
	//return 0 if data is not ready
	 private static double handleResponseEvent(Event event, int field) throws Exception {
		 double output = 0;
		
		 MessageIterator iter = event.messageIterator();
		 while (iter.hasNext()) {
			 Message message = iter.next();
			 Element ReferenceDataResponse = message.asElement();
			 Element securityDataArray =  ReferenceDataResponse.getElement("securityData");
			 //parse
			 int numItems = securityDataArray.numValues();
			 for (int i = 0; i < numItems; ++i) {
				 Element securityData = securityDataArray.getValueAsElement(i);
				 Element fieldData =securityData.getElement("fieldData");
				 //String NAME = fieldData.getElementAsString(" NAME");
				 double exp = fieldData.getElementAsFloat64(" RT_BN_SURVEY_MEDIAN");
				 double actual =fieldData.getElementAsFloat64("LAST_PRICE");
				 String date = fieldData.getElementAsString("TIME");
				  
				 if(field==0){	//get Expectation
					 output = exp;
				 }
				 else{		//get Actual
					 if (date.contains(":")){	//if it's today's data
						 output = actual;
					 }
				 }
			 }
			 //message.print(System.out);
			 
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
		 ArrayList<String> names = new ArrayList<String>();
		 SessionOptions sessionOptions = new SessionOptions();
	 		sessionOptions.setServerHost("localhost");
	 		sessionOptions.setServerPort(8194);
	 		Session session = new Session(sessionOptions);
	 		if (!session.start()) {
			 System.out.println("Could not start session.");
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
				request.getElement("securities").appendValue(securities.get(i));
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
			 Element ReferenceDataResponse = message.asElement();
			 Element securityDataArray =  ReferenceDataResponse.getElement("securityData");
			 //parse
			 int numItems = securityDataArray.numValues();
			 for (int i = 0; i < numItems; ++i) {
				 Element securityData = securityDataArray.getValueAsElement(i);
				 Element fieldData =securityData.getElement("fieldData");
				 String name = fieldData.getElementAsString(" NAME");
				 if(name!=null){
					 names.add(name);
				 }
			 }
		 	}
		return names;
	}

	
	/*********** TESTING 
	 * @throws Exception ****************************/
	public static void main(String[] args) throws Exception{
	//System.out.print(DataProcess.getVar("Change in NFP.csv"));
		
		//TEST: getBMG
		System.out.print(DataProcess.getBMG("NHSPATOT Index",0));
		//TEST: getNames
		ArrayList<String> test = new ArrayList<String>();
		test.add("NHSPATOT Index");
		//add more securities here
		System.out.print(DataProcess.getNames(test));
	}

	public class Data{
		private String indicator;
		private double exp=0;
		private double actual=0;
		
		public Data(String indicator){
			this.indicator = indicator;
		}
	}
}