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

/**
 * process data: get Var from excel, get expectation from Bloomberg, get real-time data from 
 * 	Bloomberg
 * @author liujingyun
 *
 */


public class DataProcess {

	public DataProcess() {
	// TODO Auto-generated constructor stub
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
	
	/***********Get expectation value of an indicator ****************************/
	//send request
	public static void sendRequest(String indicator) throws Exception {
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
 		 		handleResponseEvent(event);
 		 		break;
 		 	default:
 		 		//handleOtherEvent(event);
 		 		break;
 		 	}
 		 }
	}
	 private static void handleResponseEvent(Event event) throws Exception {
		 MessageIterator iter = event.messageIterator();
		 while (iter.hasNext()) {
			 Message message = iter.next();
			 Element ReferenceDataResponse = message.asElement();
			 Element securityDataArray =  ReferenceDataResponse.getElement("securityData");
			 //parse
			 int numItems = securityDataArray.numValues();
			 for (int i = 0; i < numItems; ++i) {
				 Element securityData = securityDataArray.getValueAsElement(i);
				 String security = securityData.getElementAsString(
				 "security");
				 Element fieldData =securityData.getElement("fieldData");
				 String NAME = fieldData.getElementAsString(" NAME");
				 double exp = fieldData.getElementAsFloat64(" RT_BN_SURVEY_MEDIAN");
				 double actual =fieldData.getElementAsFloat64("LAST_PRICE");
				 String date = fieldData.getElementAsString("TIME");
			 }
			 message.print(System.out);
			 
		 	}
		 }
	 
	/*********** TESTING ****************************/
	/*********** TESTING 
	 * @throws Exception ****************************/
	public static void main(String[] args) throws Exception{
	//System.out.print(DataProcess.getVar("Change in NFP.csv"));
		DataProcess.sendRequest("NHSPATOT Index");
	}
}