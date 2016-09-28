package colors;
//import org.apache.poi.EncryptedDocumentException;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
////Apache Poi library
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.util.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
	public static double getVar(String fileName){
		double var=0;
//++++++++++++Tried to open .xlsx file but can only open .xls +++++++++++++++++/
/*  			try{
			//Workbook wb = WorkbookFactory.create(new File(fileName));
			Workbook wb = WorkbookFactory.create(new File(fileName));
			Sheet sheet = wb.getSheetAt(0);
			Cell cell = null;
			cell = sheet.getRow(4).getCell(8);
			System.out.print("Get Cell!\n");
			var = cell.getNumericCellValue();
			wb.close();
		}
		catch(FileNotFoundException e){
			System.out.print("File not found");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			System.out.print("EncryptedDocumentException!");
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			System.out.print("InvalidFormatException!");
			e.printStackTrace();
		}  
******/
/*++++++++++++++++++Open an csv file instead++++++++++++++++++*/
		BufferedReader br = null; 
		String line= " ";
		try {
			 br = new BufferedReader(new FileReader(fileName));
	           //======The data is located at third line last cell=====
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

	//*********** TESTING ****************************/
	public static void main(String[] args){
		System.out.print(DataProcess.getVar("/Users/liujingyun/Desktop/Change in NFP.csv"));
	}
}
