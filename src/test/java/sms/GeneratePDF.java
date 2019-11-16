package sms;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneratePDF {
	final static Logger logger = Logger.getLogger(GeneratePDF.class);
	static Date now = new Date();
	
	
	
	 public static void main(String[] args) {
		 SimpleDateFormat  ft=new SimpleDateFormat ("dd-MM-yyyy");
		 logger.debug(ft.format(now));
	        try {
	        	//Create Directory
	        	File files = new File("C:\\Testing\\Test1\\"+ft.format(now));
	        	if(!files.exists()){
	        	files.mkdirs();
	        	}else{
	        		logger.debug("Alreday Found");
	        	}
	        	//Write PDF File
	        	 OutputStream file = new FileOutputStream(new File(files+"\\PDF_Java4s.pdf"));
	        	 
	          	Document pdf = new Document(PageSize.A4);
	             PdfWriter.getInstance(pdf, file);
	             Paragraph cell = new Paragraph();
	             PdfPCell cell1 = new PdfPCell();
	             PdfPCell cell2 = new PdfPCell();
	             pdf.open();
	             Paragraph p1 = new Paragraph("Student Monitoring System", FontFactory.getFont(FontFactory.HELVETICA, 24, Font.BOLD));
	 			p1.setAlignment(Paragraph.ALIGN_CENTER);
	 			 pdf.add(p1);
	 			PdfPTable table = new PdfPTable(2);
				table.setWidthPercentage(100f);
				cell = new Paragraph("First Name", FontFactory.getFont(FontFactory.HELVETICA, 16, Font.NORMAL));
				cell.add("Robert");
				cell1 = new PdfPCell(cell);
				cell1.setBorder(PdfPCell.NO_BORDER);
				table.addCell(cell1);
				cell = new Paragraph("Last Name", FontFactory.getFont(FontFactory.HELVETICA, 16, Font.NORMAL));
				cell.add("Jenifer");
				cell1 = new PdfPCell(cell);
				cell1.setBorder(PdfPCell.NO_BORDER);
				table.addCell(cell1);
				pdf.add(table);
	             pdf.close();
	             file.close();
	             
	             //PDF open Windows and MAC
	             File pdfFile = new File(files+"\\PDF_Java4s.pdf");
	            if(pdfFile.exists()){
	            	if (Desktop.isDesktopSupported()) {
        				Desktop.getDesktop().open(pdfFile);
        			} else {
        				logger.debug("Awt Desktop is not supported!");
        			}

        		} else {
        			logger.debug("File is not exists!");
        		}

        		logger.debug("Done");
	            
	        	
	        }catch(Exception e){
	        }
	        	
}
}