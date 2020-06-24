
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sathindu
 */
public class printer{
    String bill = "";
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String Date = dateFormat.format(date);
        String Time = timeFormat.format(date);
        DecimalFormat decim = new DecimalFormat("0.00");
        
    public List<String> getPrinters(){
		
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		
		PrintService printServices[] = PrintServiceLookup.lookupPrintServices(
				flavor, pras);
		
		List<String> printerList = new ArrayList<String>();
		for(PrintService printerService: printServices){
			printerList.add( printerService.getName());
		}
		
		return printerList;
	}
    private PrintService findPrintService(String printerName,
			PrintService[] services) {
		for (PrintService service : services) {
			if (service.getName().equalsIgnoreCase(printerName)) {
				return service;
			}
		}
 
		return null;
	}
    public void setstring(String body,double tot){
        String Header = 
         "          **Sekkuwa**\n"
        +"          091-2255898\n"
        +"Date: "+Date+"   Time: "+Time+"\n"
        +"_________________________________\n"
        +"Name\tQty\tRate\tAmt\n"
        +"---------------------------------\n";
             bill = Header;
             bill += body;
        String fotter=    "\nTotal Amount =\t\t"+decim.format(tot)+"\n"
         +"********************************\n"
         + "          Thank you.\n";
        bill += fotter;
        System.out.println(bill);
        
    }
    
    public void print(){
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();        
 
		PrintService printService[] = PrintServiceLookup.lookupPrintServices(
				flavor, pras);
		PrintService service = findPrintService("TM-T82-S-A", printService);                
 
		DocPrintJob job = service.createPrintJob();
 
        try {
            byte[] bytes;		
                bytes = bill.getBytes(); 
		Doc doc = new SimpleDoc(bytes, flavor, null);
                           
		job.print(doc,null );
                    
          
           System.out.println();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        bill = "";
    }
    
    
    
}
