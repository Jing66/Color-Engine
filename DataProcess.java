package colors;
//Apache Poi library
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
//JDK Library
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * process data: get Var from excel, get expectation from Bloomberg, get real-time data from 
 * 				Bloomberg
 * @author liujingyun
 *
 */


public class DataProcess {

	public DataProcess() {
		// TODO Auto-generated constructor stub
	}
	
	//************Read variable from the H4 cell********/
	public static int getVar(String fileName){
		int var=0;
		try{
			FileInputStream file = new FileInputStream(new File("filename"));
		}
		catch(FileNotFoundException e){
			System.out.print("File not found");
		}
		
		return var;
	}

	//*********** TESTING ****************************/
	public static void main(String[] args){
		
	}
}
