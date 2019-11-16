package com.stumosys.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.stumosys.domain.AttendanceDataBean;
import com.stumosys.domain.ClassTimeTableDataBean;
import com.stumosys.domain.HomeworkDatabean;
import com.stumosys.domain.LibrarianDataBean;
import com.stumosys.domain.NoticeBoardDataBean;
import com.stumosys.domain.ParentsDataBean;
import com.stumosys.domain.ReportCardDataBean;
import com.stumosys.domain.StaffDataBean;
import com.stumosys.domain.StudentDataBean;
import com.stumosys.domain.StudentPerformanceDataBean;
import com.stumosys.domain.TeacherDataBean;
import com.stumosys.domain.TimeTableDataBean;

public class GeneratePdfMail {
	public static Logger logger = Logger.getLogger(GeneratePdfMail.class);
	public static Date now = new Date();
	public static SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	private static final String MAIL_HOST="smtp.gmail.com";
	private static final String MAIL_PORT="465";
	private static final String MAIL_CLASS="javax.net.ssl.SSLSocketFactory";
	private static final String MAIL_AUTH="true";
	private static final String MAIL_SMTP_PORT="587";
	private static final String MAIL_STARTLES="true";
	private static final String MAIL_TRUST="smtp.gmail.com";
	static ResourceBundle paths=ResourceBundle.getBundle("com.sms.paths");

	
	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public SimpleDateFormat getFt() {
		return ft;
	}

	public void setFt(SimpleDateFormat ft) {
		this.ft = ft;
	}	
	
	public static String imageSet(String file, String id) throws IOException{
		String type;String types;String path = "";
		logger.debug("inside image write");
		logger.debug("inside image write");
		try{

			type = file.substring(file.lastIndexOf("\\") + 1);
			types = type.substring(type.lastIndexOf(".") + 1);
			logger.debug("type "+type+" - "+types);
			path = ft.format(now) + "/" + id + "." + types;
			logger.debug("path - "+path+" file "+file);
			FileInputStream in=new FileInputStream(file);
			logger.debug("in "+in);
			File files = new File("C:\\Photos\\Teacher\\" + ft.format(now));
			if (!files.exists()) {
				files.mkdirs();
				logger.debug("exist");
			} else {
				logger.debug("not exist");
			}
			logger.debug("files - "+files);
			OutputStream out = new FileOutputStream(new File(files + paths.getString("path_context").toString() + id + "." + types));
			logger.debug("out  "+files + paths.getString("path_context").toString() + id + "." + types);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();
		}
		catch(Exception e){
			logger.warn("Inside Exception",e);
		}
		return path;		
	}
	
	public static String generatePdf(TeacherDataBean teacherDataBean2, List<TeacherDataBean> imageListPath, String personID) {		
		String status = "Fail";
		logger.debug("insode pdf");
		try {
			
			if (personID != null) {
				if (imageListPath.size() > 0) {
					// Create Directory
					File files = new File(paths.getString("sms.teacher.teacherpdf") + ft.format(now));
					if (!files.exists()) {
						files.mkdirs();
					} else {
						logger.debug("Alreday Found");
					}
					// Write PDF File
					OutputStream file = new FileOutputStream(
							new File(files + paths.getString("path_context").toString() + teacherDataBean2.getTeaUsername() + ".pdf"));
					teacherDataBean2.setTeafilePath(files + paths.getString("path_context").toString() + teacherDataBean2.getTeaUsername() + ".pdf");
					Document document = new Document(PageSize.A4, 50, 50, 10, 50);
					PdfWriter.getInstance(document, file);
					document.open();
					String logo =  paths.getString(teacherDataBean2.getSchoolID()+"_LOGO");
					document.add(Image.getInstance(new Object(){}.getClass().getClassLoader().getResource(logo)));
					document.add(new Paragraph(
							"------------------------------------------------------------------------------------------------------------------------"));

					float[] columnWidths = { 20f, 1f };
					Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 27);
					PdfPTable table = new PdfPTable(2); // .
					table.setWidthPercentage(100f);
					table.setWidths(columnWidths);

					PdfPCell cell1 = new PdfPCell(new Paragraph());
					cell1.setBorder(PdfPCell.NO_BORDER);
					PdfPCell cell2 = new PdfPCell(new Paragraph());
					cell2.setBorder(PdfPCell.NO_BORDER);
					table.setSpacingBefore(10f);
					table.setSpacingAfter(25f);

					PdfPTable nestedTable = new PdfPTable(4);
					nestedTable.setWidthPercentage(100f);
					PdfPCell nesCell1 = new PdfPCell(new Paragraph("First Name:"));
					PdfPCell nesCell2 = new PdfPCell(new Paragraph(imageListPath.get(0).getTeaFirstName()));
					PdfPCell nesCell3 = new PdfPCell(new Paragraph("Last Name:"));
					PdfPCell nesCell4 = new PdfPCell(new Paragraph(imageListPath.get(0).getTeaLastName()));
					nesCell1.setBorder(PdfPCell.NO_BORDER);
					nesCell2.setBorder(PdfPCell.NO_BORDER);
					nesCell3.setBorder(PdfPCell.NO_BORDER);
					nesCell4.setBorder(PdfPCell.NO_BORDER);
					nestedTable.addCell(nesCell1);
					nestedTable.addCell(nesCell2);
					nestedTable.addCell(nesCell3);
					nestedTable.addCell(nesCell4);
					nestedTable.setSpacingBefore(3f);
					nestedTable.setSpacingAfter(3f);
					cell1.addElement(nestedTable);

					PdfPTable nestedTable1 = new PdfPTable(4);
					nestedTable1.setWidthPercentage(100f);
					PdfPCell nesCell5 = new PdfPCell(new Paragraph("Father Name:"));
					PdfPCell nesCell6 = new PdfPCell(new Paragraph(imageListPath.get(0).getTeaFatherName()));
					PdfPCell nesCell7 = new PdfPCell(new Paragraph("Mother Name:"));
					PdfPCell nesCell8 = new PdfPCell(new Paragraph(imageListPath.get(0).getTeaMotherName()));
					nesCell5.setBorder(PdfPCell.NO_BORDER);
					nesCell6.setBorder(PdfPCell.NO_BORDER);
					nesCell7.setBorder(PdfPCell.NO_BORDER);
					nesCell8.setBorder(PdfPCell.NO_BORDER);
					nestedTable1.addCell(nesCell5);
					nestedTable1.addCell(nesCell6);
					nestedTable1.addCell(nesCell7);
					nestedTable1.addCell(nesCell8);
					nestedTable1.setSpacingBefore(3f);
					nestedTable1.setSpacingAfter(3f);
					cell1.addElement(nestedTable1);

					PdfPTable nestedTable2 = new PdfPTable(4);
					nestedTable2.setWidthPercentage(100f);
					PdfPCell nesCell9 = new PdfPCell(new Paragraph(" Date of Birth:"));
					PdfPCell nesCell10 = new PdfPCell(new Paragraph(imageListPath.get(0).getTeaDob().toString()));
					PdfPCell nesCell11 = new PdfPCell(new Paragraph("Gender:"));
					PdfPCell nesCell12 = new PdfPCell(new Paragraph(imageListPath.get(0).getTeaGender()));
					nesCell10.setBorder(PdfPCell.NO_BORDER);
					nesCell11.setBorder(PdfPCell.NO_BORDER);
					nesCell12.setBorder(PdfPCell.NO_BORDER);
					nesCell9.setBorder(PdfPCell.NO_BORDER);
					nestedTable2.addCell(nesCell9);
					nestedTable2.addCell(nesCell10);
					nestedTable2.addCell(nesCell11);
					nestedTable2.addCell(nesCell12);
					nestedTable2.setSpacingBefore(3f);
					nestedTable2.setSpacingAfter(3f);
					cell1.addElement(nestedTable2);

					PdfPTable nestedTable3 = new PdfPTable(4);
					nestedTable3.setWidthPercentage(100f);
					PdfPCell nesCell13 = new PdfPCell(new Paragraph("Present Address:"));
					PdfPCell nesCell14 = new PdfPCell(new Paragraph(imageListPath.get(0).getTeaPresentAddress()));
					PdfPCell nesCell15 = new PdfPCell(new Paragraph("Permanent Address:"));
					PdfPCell nesCell16 = new PdfPCell(new Paragraph(imageListPath.get(0).getTeaPermanentAddress()));
					/*PdfPCell nesCell15 = new PdfPCell(new Paragraph("Present State:"));
					PdfPCell nesCell16 = new PdfPCell(new Paragraph(imageListPath.get(0).getTeaPresentState()));
					*/nesCell13.setBorder(PdfPCell.NO_BORDER);
					nesCell14.setBorder(PdfPCell.NO_BORDER);
					nesCell15.setBorder(PdfPCell.NO_BORDER);
					nesCell16.setBorder(PdfPCell.NO_BORDER);
					nestedTable3.addCell(nesCell13);
					nestedTable3.addCell(nesCell14);
					nestedTable3.addCell(nesCell15);
					nestedTable3.addCell(nesCell16);
					nestedTable3.setSpacingBefore(3f);
					nestedTable3.setSpacingAfter(3f);
					cell1.addElement(nestedTable3);

					/*PdfPTable nestedTable4 = new PdfPTable(4);
					nestedTable4.setWidthPercentage(100f);
					PdfPCell nesCell17 = new PdfPCell(new Paragraph("Permanent Address:"));
					PdfPCell nesCell18 = new PdfPCell(new Paragraph(imageListPath.get(0).getTeaPermanentAddress()));
					PdfPCell nesCell19 = new PdfPCell(new Paragraph("Permanent State:"));
					PdfPCell nesCell20 = new PdfPCell(new Paragraph(imageListPath.get(0).getTeaPermanentState()));
					nesCell17.setBorder(PdfPCell.NO_BORDER);
					nesCell18.setBorder(PdfPCell.NO_BORDER);
					nesCell19.setBorder(PdfPCell.NO_BORDER);
					nesCell20.setBorder(PdfPCell.NO_BORDER);
					nestedTable4.addCell(nesCell17);
					nestedTable4.addCell(nesCell18);
					nestedTable4.addCell(nesCell19);
					nestedTable4.addCell(nesCell20);
					nestedTable4.setSpacingBefore(3f);
					nestedTable4.setSpacingAfter(3f);
					cell1.addElement(nestedTable4);

					PdfPTable nestedTable5 = new PdfPTable(4);
					nestedTable5.setWidthPercentage(100f);
					PdfPCell nesCell37 = new PdfPCell(new Paragraph("Present Zip:"));
					PdfPCell nesCell38 = new PdfPCell(new Paragraph(imageListPath.get(0).getTeaPresentZip()));
					PdfPCell nesCell39 = new PdfPCell(new Paragraph("Permanent Zip:"));
					PdfPCell nesCell40 = new PdfPCell(new Paragraph(imageListPath.get(0).getTeaPermanentZip()));
					nesCell37.setBorder(PdfPCell.NO_BORDER);
					nesCell38.setBorder(PdfPCell.NO_BORDER);
					nesCell39.setBorder(PdfPCell.NO_BORDER);
					nesCell40.setBorder(PdfPCell.NO_BORDER);
					nestedTable5.addCell(nesCell37);
					nestedTable5.addCell(nesCell38);
					nestedTable5.addCell(nesCell39);
					nestedTable5.addCell(nesCell40);
					nestedTable5.setSpacingBefore(3f);
					nestedTable5.setSpacingAfter(3f);
					cell1.addElement(nestedTable5);*/

					PdfPTable nestedTable6 = new PdfPTable(4);
					nestedTable6.setWidthPercentage(100f);
					PdfPCell nesCell21 = new PdfPCell(new Paragraph("Phone Number:"));
					PdfPCell nesCell22 = new PdfPCell(new Paragraph(imageListPath.get(0).getTeaPhoneNo()));
					PdfPCell nesCell23 = new PdfPCell(new Paragraph("Email ID:"));
					PdfPCell nesCell24 = new PdfPCell(new Paragraph(imageListPath.get(0).getTeaEmail()));
					nesCell21.setBorder(PdfPCell.NO_BORDER);
					nesCell22.setBorder(PdfPCell.NO_BORDER);
					nesCell23.setBorder(PdfPCell.NO_BORDER);
					nesCell24.setBorder(PdfPCell.NO_BORDER);
					nestedTable6.addCell(nesCell21);
					nestedTable6.addCell(nesCell22);
					nestedTable6.addCell(nesCell23);
					nestedTable6.addCell(nesCell24);
					nestedTable6.setSpacingBefore(3f);
					nestedTable6.setSpacingAfter(3f);
					cell1.addElement(nestedTable6);

					PdfPTable nestedTable7 = new PdfPTable(4);
					nestedTable7.setWidthPercentage(100f);
					PdfPCell nesCell25 = new PdfPCell(new Paragraph("Education Qualification:"));
					PdfPCell nesCell26 = new PdfPCell(new Paragraph(imageListPath.get(0).getTeaEduQualification()));
					PdfPCell nesCell27 = new PdfPCell(new Paragraph("Year of Passing:"));
					PdfPCell nesCell28 = new PdfPCell(new Paragraph(imageListPath.get(0).getTeaYearOfPassing()));
					nesCell25.setBorder(PdfPCell.NO_BORDER);
					nesCell26.setBorder(PdfPCell.NO_BORDER);
					nesCell27.setBorder(PdfPCell.NO_BORDER);
					nesCell28.setBorder(PdfPCell.NO_BORDER);
					nestedTable7.addCell(nesCell25);
					nestedTable7.addCell(nesCell26);
					nestedTable7.addCell(nesCell27);
					nestedTable7.addCell(nesCell28);
					nestedTable7.setSpacingBefore(3f);
					nestedTable7.setSpacingAfter(3f);
					cell1.addElement(nestedTable7);

					PdfPTable nestedTable9 = new PdfPTable(4);
					nestedTable9.setWidthPercentage(100f);
					PdfPCell nesCell33 = new PdfPCell(new Paragraph("Teacher ID:"));
					PdfPCell nesCell34 = new PdfPCell(new Paragraph(imageListPath.get(0).getTeaID()));
					PdfPCell nesCell35 = null;
					PdfPCell nesCell36 = null;
					if(imageListPath.get(0).getPath() !=null)
					{
						String tempdate = ft.format(imageListPath.get(0).getPathDate());
						String imageLocation = paths.getString("sms.teacher.teacherphoto") + tempdate + "/" + imageListPath.get(0).getPath();
						Image image = Image.getInstance(imageLocation);
						image.scaleAbsolute(100, 100);
						nesCell35=new PdfPCell(new Paragraph("Photo:"));
						nesCell36 = new PdfPCell(image, false);
					}else{
						nesCell35=new PdfPCell(new Paragraph(""));
						 nesCell36 = new PdfPCell(new Paragraph(""));
					}
					nesCell33.setBorder(PdfPCell.NO_BORDER);
					nesCell34.setBorder(PdfPCell.NO_BORDER);
					nesCell35.setBorder(PdfPCell.NO_BORDER);
					nesCell36.setBorder(PdfPCell.NO_BORDER);
					nestedTable9.addCell(nesCell33);
					nestedTable9.addCell(nesCell34);
					nestedTable9.addCell(nesCell35);
					nestedTable9.addCell(nesCell36);
					nestedTable9.setSpacingBefore(3f);
					nestedTable9.setSpacingAfter(3f);
					cell1.addElement(nestedTable9);

					table.addCell(cell1);
					table.addCell(cell2);
					document.add(table);
					document.close();
					file.close();

					logger.debug("Done");
					status = "Success";
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return status;
	}
	
	public static String sendEmail(TeacherDataBean teacherDataBean) {
		String status = "Fail";
		logger.debug("inside mail ");
		try {
			String to = teacherDataBean.getTeaEmail();
			logger.debug("to-------" + to);
			Properties prop = new Properties();
			prop.put("mail.smtp.host", MAIL_HOST);
			prop.put("mail.smtp.socketFactory.port", MAIL_PORT);
			prop.put("mail.smtp.socketFactory.class", MAIL_CLASS);
			prop.put("mail.smtp.starttls.enable", MAIL_STARTLES);
			prop.put("mail.smtp.auth", MAIL_AUTH);
			prop.put("mail.smtp.port", MAIL_SMTP_PORT);
			prop.put("mail.smtp.ssl.trust", MAIL_TRUST);

			String body1 = "<htm><head></heade><body>"
					+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
					+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
					+ "<h3>Dear " + teacherDataBean.getTeaUsername() + ",</h3>"
					+ "<p>Welcome on-board into NRG family. Please find the your Username and Password enclosed with is mail."
					+ "All the very best in your new assignment</p>"
					+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
					+ "<tr>" + "<td>User Name" + "</td>" + "<td>" + teacherDataBean.getTeaUsername() + "</td>"
					+ "</tr>" + "<tr>" + "<td>Password" + "</td>" + "<td>" + teacherDataBean.getTeaSecurePasword()
					+ "</td>" + "</tr>" + "</table>" + "<footer>" + "<center>"
					+ "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>" + "</body></html>";

			Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
				}
			});
			// compose message
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("SMS Confirmation-" + teacherDataBean.getTeaUsername());
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");

				MimeBodyPart messageBodyPart2 = new MimeBodyPart();
				String filename = "C://Files/PDF/Teacher/" + ft.format(now) + "/" + teacherDataBean.getTeaUsername()
						+ ".pdf";// change accordingly
				DataSource source = new FileDataSource(filename);
				messageBodyPart2.setDataHandler(new DataHandler(source));
				messageBodyPart2.setFileName(teacherDataBean.getTeaUsername() + ".pdf");

				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				multipart.addBodyPart(messageBodyPart2);
				message.setContent(multipart);

				Transport.send(message);

				logger.debug("message sent successfully");
				logger.debug("message sent successfully");
				status = "Success";

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
		return status;
	}
	
	/*STUDENT*/
	
	public static String imageStudent(String file, String id) throws IOException{
		String type;String types;String path = "";
		try{

			type = file.substring(file.lastIndexOf("\\") + 1);
			types = type.substring(type.lastIndexOf(".") + 1);
			logger.debug("type "+type+" - "+types);
			path = ft.format(now) + "/" + id + "." + types;
			InputStream in=new FileInputStream(file);
			logger.debug("in "+in);
			File files = new File(paths.getString("sms.student.photo") + ft.format(now));
			if (!files.exists()) {
				files.mkdirs();
			} else {
			}
			OutputStream out = new FileOutputStream(new File(files + paths.getString("path_context").toString() + id + "." + types));
			logger.debug("out  "+files + paths.getString("path_context").toString() + id + "." + types);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();
		}
		catch(Exception e){
			logger.warn("Inside Exception",e);
		}
		return path;		
	}
	
	public static String generatePdfStudent(StudentDataBean studentDataBean, List<StudentDataBean> imageListPath, String personID) {
		String status = "Fail";
		try {
			if (personID != null) {				
				if (imageListPath.size() > 0) {
					// Create Directory
					File files = new File(paths.getString("sms.student.pdf") + ft.format(now));
					if (!files.exists()) {
						files.mkdirs();
					} else {
						logger.debug("Alreday Found");
					}
					// Write PDF File
					OutputStream file = new FileOutputStream(
							new File(files + paths.getString("path_context").toString() + studentDataBean.getStuUsername() + ".pdf"));

					Document document = new Document(PageSize.A4, 50, 50, 10, 50);
					PdfWriter.getInstance(document, file);
					document.open();
					String logo = "C://Photos/Header/nrg.png";
					document.add(Image.getInstance(logo));
					document.add(new Paragraph(
							"------------------------------------------------------------------------------------------------------------------------"));

					float[] columnWidths = { 20f, 1f };
					Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 27);
					PdfPTable table = new PdfPTable(2); // .
					table.setWidthPercentage(100f);
					table.setWidths(columnWidths);

					PdfPCell cell1 = new PdfPCell(new Paragraph());
					cell1.setBorder(PdfPCell.NO_BORDER);
					PdfPCell cell2 = new PdfPCell(new Paragraph());
					cell2.setBorder(PdfPCell.NO_BORDER);
					table.setSpacingBefore(10f);
					table.setSpacingAfter(25f);

					PdfPTable nestedTable = new PdfPTable(4);
					nestedTable.setWidthPercentage(100f);
					PdfPCell nesCell1 = new PdfPCell(new Paragraph("First Name:"));
					PdfPCell nesCell2 = new PdfPCell(new Paragraph(imageListPath.get(0).getStuFirstName()));
					PdfPCell nesCell3 = new PdfPCell(new Paragraph("Last Name:"));
					PdfPCell nesCell4 = new PdfPCell(new Paragraph(imageListPath.get(0).getStuLastName()));
					nesCell1.setBorder(PdfPCell.NO_BORDER);
					nesCell2.setBorder(PdfPCell.NO_BORDER);
					nesCell3.setBorder(PdfPCell.NO_BORDER);
					nesCell4.setBorder(PdfPCell.NO_BORDER);
					nestedTable.addCell(nesCell1);
					nestedTable.addCell(nesCell2);
					nestedTable.addCell(nesCell3);
					nestedTable.addCell(nesCell4);
					nestedTable.setSpacingBefore(3f);
					nestedTable.setSpacingAfter(3f);
					cell1.addElement(nestedTable);

					PdfPTable nestedTable1 = new PdfPTable(4);
					nestedTable1.setWidthPercentage(100f);
					PdfPCell nesCell5 = new PdfPCell(new Paragraph("Father Name:"));
					PdfPCell nesCell6 = new PdfPCell(new Paragraph(imageListPath.get(0).getStuFatherName()));
					PdfPCell nesCell7 = new PdfPCell(new Paragraph("Mother Name:"));
					PdfPCell nesCell8 = new PdfPCell(new Paragraph(imageListPath.get(0).getStuMotherName()));
					nesCell5.setBorder(PdfPCell.NO_BORDER);
					nesCell6.setBorder(PdfPCell.NO_BORDER);
					nesCell7.setBorder(PdfPCell.NO_BORDER);
					nesCell8.setBorder(PdfPCell.NO_BORDER);
					nestedTable1.addCell(nesCell5);
					nestedTable1.addCell(nesCell6);
					nestedTable1.addCell(nesCell7);
					nestedTable1.addCell(nesCell8);
					nestedTable1.setSpacingBefore(3f);
					nestedTable1.setSpacingAfter(3f);
					cell1.addElement(nestedTable1);

					PdfPTable nestedTable2 = new PdfPTable(4);
					nestedTable2.setWidthPercentage(100f);
					PdfPCell nesCell9 = new PdfPCell(new Paragraph(" Date of Birth:"));
					PdfPCell nesCell10 = new PdfPCell(new Paragraph(imageListPath.get(0).getStuDob().toString()));
					PdfPCell nesCell11 = new PdfPCell(new Paragraph("Gender:"));
					PdfPCell nesCell12 = new PdfPCell(new Paragraph(imageListPath.get(0).getStuGender()));
					nesCell9.setBorder(PdfPCell.NO_BORDER);
					nesCell10.setBorder(PdfPCell.NO_BORDER);
					nesCell11.setBorder(PdfPCell.NO_BORDER);
					nesCell12.setBorder(PdfPCell.NO_BORDER);
					nestedTable2.addCell(nesCell9);
					nestedTable2.addCell(nesCell10);
					nestedTable2.addCell(nesCell11);
					nestedTable2.addCell(nesCell12);
					nestedTable2.setSpacingBefore(3f);
					nestedTable2.setSpacingAfter(3f);
					cell1.addElement(nestedTable2);

					PdfPTable nestedTable3 = new PdfPTable(4);
					nestedTable3.setWidthPercentage(100f);
					PdfPCell nesCell13 = new PdfPCell(new Paragraph("Present Address:"));
					PdfPCell nesCell14 = new PdfPCell(new Paragraph(imageListPath.get(0).getStuPermanentAddress()));
					PdfPCell nesCell15 = new PdfPCell(new Paragraph("Permanent Address:"));
					PdfPCell nesCell16 = new PdfPCell(new Paragraph(imageListPath.get(0).getStuPermanentAddress()));
					/*PdfPCell nesCell15 = new PdfPCell(new Paragraph("Present State:"));
					PdfPCell nesCell16 = new PdfPCell(new Paragraph(imageListPath.get(0).getStuPresentState()));*/
					nesCell13.setBorder(PdfPCell.NO_BORDER);
					nesCell14.setBorder(PdfPCell.NO_BORDER);
					nesCell15.setBorder(PdfPCell.NO_BORDER);
					nesCell16.setBorder(PdfPCell.NO_BORDER);
					nestedTable3.addCell(nesCell13);
					nestedTable3.addCell(nesCell14);
					nestedTable3.addCell(nesCell15);
					nestedTable3.addCell(nesCell16);
					nestedTable3.setSpacingBefore(3f);
					nestedTable3.setSpacingAfter(3f);
					cell1.addElement(nestedTable3);

					/*PdfPTable nestedTable4 = new PdfPTable(4);
					nestedTable4.setWidthPercentage(100f);
					PdfPCell nesCell17 = new PdfPCell(new Paragraph("Permanent Address:"));
					PdfPCell nesCell18 = new PdfPCell(new Paragraph(imageListPath.get(0).getStuPermanentAddress()));
					PdfPCell nesCell19 = new PdfPCell(new Paragraph("Permanent State:"));
					PdfPCell nesCell20 = new PdfPCell(new Paragraph(imageListPath.get(0).getStuPermanentState()));
					nesCell17.setBorder(PdfPCell.NO_BORDER);
					nesCell18.setBorder(PdfPCell.NO_BORDER);
					nesCell19.setBorder(PdfPCell.NO_BORDER);
					nesCell20.setBorder(PdfPCell.NO_BORDER);
					nestedTable4.addCell(nesCell17);
					nestedTable4.addCell(nesCell18);
					nestedTable4.addCell(nesCell19);
					nestedTable4.addCell(nesCell20);
					nestedTable4.setSpacingBefore(3f);
					nestedTable4.setSpacingAfter(3f);
					cell1.addElement(nestedTable4);*/

					/*PdfPTable nestedTable5 = new PdfPTable(4);
					nestedTable5.setWidthPercentage(100f);
					PdfPCell nesCell37 = new PdfPCell(new Paragraph("Present Zip:"));
					PdfPCell nesCell38 = new PdfPCell(new Paragraph(imageListPath.get(0).getStuPresentZip()));
					PdfPCell nesCell39 = new PdfPCell(new Paragraph("Permanent Zip:"));
					PdfPCell nesCell40 = new PdfPCell(new Paragraph(imageListPath.get(0).getStuPermanentZip()));
					nesCell37.setBorder(PdfPCell.NO_BORDER);
					nesCell38.setBorder(PdfPCell.NO_BORDER);
					nesCell39.setBorder(PdfPCell.NO_BORDER);
					nesCell40.setBorder(PdfPCell.NO_BORDER);
					nestedTable5.addCell(nesCell37);
					nestedTable5.addCell(nesCell38);
					nestedTable5.addCell(nesCell39);
					nestedTable5.addCell(nesCell40);
					nestedTable5.setSpacingBefore(3f);
					nestedTable5.setSpacingAfter(3f);
					cell1.addElement(nestedTable5);*/

					/*PdfPTable nestedTable6 = new PdfPTable(4);
					nestedTable6.setWidthPercentage(100f);
					PdfPCell nesCell21 = new PdfPCell(new Paragraph("Phone Number:"));
					PdfPCell nesCell22 = new PdfPCell(new Paragraph(imageListPath.get(0).getStuPhoneNo()));
					PdfPCell nesCell23 = new PdfPCell(new Paragraph("Email ID:"));
					PdfPCell nesCell24 = new PdfPCell(new Paragraph(imageListPath.get(0).getStuEmail()));
					nesCell21.setBorder(PdfPCell.NO_BORDER);
					nesCell22.setBorder(PdfPCell.NO_BORDER);
					nesCell23.setBorder(PdfPCell.NO_BORDER);
					nesCell24.setBorder(PdfPCell.NO_BORDER);
					nestedTable6.addCell(nesCell21);
					nestedTable6.addCell(nesCell22);
					nestedTable6.addCell(nesCell23);
					nestedTable6.addCell(nesCell24);
					nestedTable6.setSpacingBefore(3f);
					nestedTable6.setSpacingAfter(3f);
					cell1.addElement(nestedTable6);*/

					PdfPTable nestedTable7 = new PdfPTable(4);
					nestedTable7.setWidthPercentage(100f);
					PdfPCell nesCell25 = new PdfPCell(new Paragraph("Father Occupation:"));
					PdfPCell nesCell26 = new PdfPCell(new Paragraph(imageListPath.get(0).getStuFatherOccu()));
					PdfPCell nesCell27 = new PdfPCell(new Paragraph("Father Income:"));
					PdfPCell nesCell28 = new PdfPCell(new Paragraph(imageListPath.get(0).getStuFatherIncome()));
					nesCell25.setBorder(PdfPCell.NO_BORDER);
					nesCell26.setBorder(PdfPCell.NO_BORDER);
					nesCell27.setBorder(PdfPCell.NO_BORDER);
					nesCell28.setBorder(PdfPCell.NO_BORDER);
					nestedTable7.addCell(nesCell25);
					nestedTable7.addCell(nesCell26);
					nestedTable7.addCell(nesCell27);
					nestedTable7.addCell(nesCell28);
					nestedTable7.setSpacingBefore(3f);
					nestedTable7.setSpacingAfter(3f);
					cell1.addElement(nestedTable7);

					PdfPTable nestedTable8 = new PdfPTable(4);
					nestedTable8.setWidthPercentage(100f);
					PdfPCell nesCell29 = new PdfPCell(new Paragraph("Mother Occupation:"));
					PdfPCell nesCell30 = new PdfPCell(new Paragraph(imageListPath.get(0).getStuMotherOccu()));
					PdfPCell nesCell31 = new PdfPCell(new Paragraph("Class&Section:"));
					PdfPCell nesCell32 = new PdfPCell(new Paragraph(imageListPath.get(0).getStuCls()));
					nesCell29.setBorder(PdfPCell.NO_BORDER);
					nesCell30.setBorder(PdfPCell.NO_BORDER);
					nesCell31.setBorder(PdfPCell.NO_BORDER);
					nesCell32.setBorder(PdfPCell.NO_BORDER);
					nestedTable8.addCell(nesCell29);
					nestedTable8.addCell(nesCell30);
					nestedTable8.addCell(nesCell31);
					nestedTable8.addCell(nesCell32);
					nestedTable8.setSpacingBefore(3f);
					nestedTable8.setSpacingAfter(3f);
					cell1.addElement(nestedTable8);

					PdfPTable nestedTable9 = new PdfPTable(4);
					nestedTable9.setWidthPercentage(100f);
					PdfPCell nesCell33 = new PdfPCell(new Paragraph("Roll Number:"));
					PdfPCell nesCell34 = new PdfPCell(new Paragraph(imageListPath.get(0).getStuRollNo()));
					PdfPCell nesCell35 = new PdfPCell(new Paragraph(""));
					PdfPCell nesCell36 = new PdfPCell(new Paragraph(""));
					/*PdfPCell nesCell35 = new PdfPCell(new Paragraph("Photo"));
					String tempdate = ft.format(imageListPath.get(0).getPathDate());
					String imageLocation = "C://Photos/Student/" + tempdate + "/" + imageListPath.get(0).getPath();
					Image image = Image.getInstance(imageLocation);
					image.scaleAbsolute(100, 100);
					PdfPCell nesCell36 = new PdfPCell(image, false);*/
					nesCell33.setBorder(PdfPCell.NO_BORDER);
					nesCell34.setBorder(PdfPCell.NO_BORDER);
					nesCell35.setBorder(PdfPCell.NO_BORDER);
					nesCell36.setBorder(PdfPCell.NO_BORDER);
					nestedTable9.addCell(nesCell33);
					nestedTable9.addCell(nesCell34);
					nestedTable9.addCell(nesCell35);
					nestedTable9.addCell(nesCell36);
					nestedTable9.setSpacingBefore(3f);
					nestedTable9.setSpacingAfter(3f);
					cell1.addElement(nestedTable9);

					table.addCell(cell1);
					table.addCell(cell2);
					document.add(table);
					document.close();
					file.close();
					logger.debug("Done");
					status = "Success";
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return status;
	}

	public static String sendEmailStudent(StudentDataBean studentDataBean)throws AddressException {
		String status = "Fail";
		String mail = studentDataBean.getStuEmail();
		String to = "" + mail;
		Properties prop = new Properties();
		prop.put("mail.smtp.host", MAIL_HOST);
		prop.put("mail.smtp.socketFactory.port", MAIL_PORT);
		prop.put("mail.smtp.socketFactory.class", MAIL_CLASS);
		prop.put("mail.smtp.starttls.enable", MAIL_STARTLES);
		prop.put("mail.smtp.auth", MAIL_AUTH);
		prop.put("mail.smtp.port", MAIL_SMTP_PORT);
		prop.put("mail.smtp.ssl.trust", MAIL_TRUST);

		String body1 = "<htm><head></heade><body>"
				+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
				+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
				+ "<h3>Dear " + studentDataBean.getStuUsername() + ",</h3>"
				+ "<p>Welcome on-board into NRG family. Please find the your Username and Password enclosed with is mail."
				+ "All the very best in your new assignment</p>"
				+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
				+ "<tr>" + "<td>User Name" + "</td>" + "<td>" + studentDataBean.getStuUsername() + "</td>" + "</tr>"
				+ "<tr>" + "<td>Password" + "</td>" + "<td>" + studentDataBean.getStuSecurePasword() + "</td>"
				+ "</tr>" + "</table>" + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>"
				+ "</center>" + "</footer>" + "</body></html>";

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
			}
		});
		// compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			/* message.addRecipients(Message.RecipientType.CC, myCcList); */
			message.setSubject("SMS Confirmation-" + studentDataBean.getStuUsername());
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setContent(body1, "text/html");

			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			String filename = "C://Files/PDF/Student/" + ft.format(now) + "/" + studentDataBean.getStuUsername()
					+ ".pdf";// change accordingly
			DataSource source = new FileDataSource(filename);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(studentDataBean.getStuUsername() + ".pdf");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);
			message.setContent(multipart);

			Transport.send(message);

			logger.debug("message sent successfully");
			status = "Success";

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return status;
	}
	
	/*PARENT*/
	
	public static String imageParent(String file, String id) throws IOException{
		String type;String types;String path = "";
		try{

			type = file.substring(file.lastIndexOf("\\") + 1);
			types = type.substring(type.lastIndexOf(".") + 1);
			logger.debug("type "+type+" - "+types);
			path = ft.format(now) + "/" + id + "." + types;
			InputStream in=new FileInputStream(file);
			logger.debug("in "+in);
			File files = new File("C:\\Photos\\Parents\\" + ft.format(now));
			if (!files.exists()) {
				files.mkdirs();
			} else {
			}
			OutputStream out = new FileOutputStream(new File(files + "\\" + id + "." + types));
			logger.debug("out  "+files + "\\" + id + "." + types);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();
		}
		catch(Exception e){
			logger.warn("Inside Exception",e);
		}
		return path;		
	}

	public static String generatePdfParent(ParentsDataBean parentsDataBean, List<ParentsDataBean> imageListPath, String personID) {
		String status = "Fail";
		try {
			if (personID != null) {
				System.out.println("size--"+imageListPath.size());
				int schoolid=parentsDataBean.getSchoolID();
				if (imageListPath.size() > 0) {
					// Create Directory
					File files = new File(paths.getString("sms.parents.pdf") + ft.format(now));
					if (!files.exists()) {
						files.mkdirs();
					} else {
						logger.debug("Alreday Found");
					}
					// Write PDF File
					OutputStream file = new FileOutputStream(
							new File(files + paths.getString("path_context").toString() + parentsDataBean.getParUsername() + ".pdf"));
					parentsDataBean.setParfilePath(files + paths.getString("path_context").toString() + parentsDataBean.getParUsername() + ".pdf");
					Document document = new Document(PageSize.A4, 50, 50, 10, 50);
					PdfWriter.getInstance(document, file);
					document.open();
					String logo ="";
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						logo = paths.getString(parentsDataBean.getSchoolID()+"_LOGO");
						document.add(Image.getInstance(new Object(){}.getClass().getClassLoader().getResource(logo)));
					}else{
						logo = parentsDataBean.getSchoolLogo();
						document.add(Image.getInstance(logo));
					}
					document.add(new Paragraph(
							"------------------------------------------------------------------------------------------------------------------------"));

					float[] columnWidths = { 20f, 1f };
					Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 27);
					PdfPTable table = new PdfPTable(2); // .
					table.setWidthPercentage(100f);
					table.setWidths(columnWidths);
					;
					PdfPCell cell1 = new PdfPCell(new Paragraph());
					cell1.setBorder(PdfPCell.NO_BORDER);
					PdfPCell cell2 = new PdfPCell(new Paragraph());
					cell2.setBorder(PdfPCell.NO_BORDER);
					table.setSpacingBefore(10f);
					table.setSpacingAfter(25f);

					PdfPTable nestedTable = new PdfPTable(4);
					nestedTable.setWidthPercentage(100f);
					PdfPCell nesCell1 = new PdfPCell(new Paragraph("First Name:"));
					PdfPCell nesCell2 = new PdfPCell(new Paragraph(imageListPath.get(0).getParFirstName()));
					PdfPCell nesCell3 = new PdfPCell(new Paragraph("Last Name:"));
					PdfPCell nesCell4 = new PdfPCell(new Paragraph(imageListPath.get(0).getParLastName()));
					nesCell1.setBorder(PdfPCell.NO_BORDER);
					nesCell2.setBorder(PdfPCell.NO_BORDER);
					nesCell3.setBorder(PdfPCell.NO_BORDER);
					nesCell4.setBorder(PdfPCell.NO_BORDER);
					nestedTable.addCell(nesCell1);
					nestedTable.addCell(nesCell2);
					nestedTable.addCell(nesCell3);
					nestedTable.addCell(nesCell4);
					nestedTable.setSpacingBefore(3f);
					nestedTable.setSpacingAfter(3f);
					cell1.addElement(nestedTable);
					
					if(imageListPath.size()==1)
					{
						PdfPTable nestedTable1 = new PdfPTable(4);
						nestedTable1.setWidthPercentage(100f);
						PdfPCell nesCell5 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell6 = new PdfPCell(new Paragraph(imageListPath.get(0).getParStudID()));
						PdfPCell nesCell7 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell8 = new PdfPCell(new Paragraph(imageListPath.get(0).getParStuClass()));
						nesCell5.setBorder(PdfPCell.NO_BORDER);
						nesCell6.setBorder(PdfPCell.NO_BORDER);
						nesCell7.setBorder(PdfPCell.NO_BORDER);
						nesCell8.setBorder(PdfPCell.NO_BORDER);
						nestedTable1.addCell(nesCell5);
						nestedTable1.addCell(nesCell6);
						nestedTable1.addCell(nesCell7);
						nestedTable1.addCell(nesCell8);
						nestedTable1.setSpacingBefore(3f);
						nestedTable1.setSpacingAfter(3f);
						cell1.addElement(nestedTable1);
					}
					else if(imageListPath.size()==2)
					{
						PdfPTable nestedTable1 = new PdfPTable(4);
						nestedTable1.setWidthPercentage(100f);
						PdfPCell nesCell5 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell6 = new PdfPCell(new Paragraph(imageListPath.get(0).getParStudID()));
						PdfPCell nesCell7 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell8 = new PdfPCell(new Paragraph(imageListPath.get(0).getParStuClass()));
						nesCell5.setBorder(PdfPCell.NO_BORDER);
						nesCell6.setBorder(PdfPCell.NO_BORDER);
						nesCell7.setBorder(PdfPCell.NO_BORDER);
						nesCell8.setBorder(PdfPCell.NO_BORDER);
						nestedTable1.addCell(nesCell5);
						nestedTable1.addCell(nesCell6);
						nestedTable1.addCell(nesCell7);
						nestedTable1.addCell(nesCell8);
						nestedTable1.setSpacingBefore(3f);
						nestedTable1.setSpacingAfter(3f);
						cell1.addElement(nestedTable1);
						PdfPTable nestedTable11 = new PdfPTable(4);
						nestedTable11.setWidthPercentage(100f);
						PdfPCell nesCell51 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell61 = new PdfPCell(new Paragraph(imageListPath.get(1).getParStudID()));
						PdfPCell nesCell71 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell81 = new PdfPCell(new Paragraph(imageListPath.get(1).getParStuClass()));
						nesCell51.setBorder(PdfPCell.NO_BORDER);
						nesCell61.setBorder(PdfPCell.NO_BORDER);
						nesCell71.setBorder(PdfPCell.NO_BORDER);
						nesCell81.setBorder(PdfPCell.NO_BORDER);
						nestedTable11.addCell(nesCell51);
						nestedTable11.addCell(nesCell61);
						nestedTable11.addCell(nesCell71);
						nestedTable11.addCell(nesCell81);
						nestedTable11.setSpacingBefore(3f);
						nestedTable11.setSpacingAfter(3f);
						cell1.addElement(nestedTable11);
					}
					else if(imageListPath.size()==3)
					{
						PdfPTable nestedTable1 = new PdfPTable(4);
						nestedTable1.setWidthPercentage(100f);
						PdfPCell nesCell5 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell6 = new PdfPCell(new Paragraph(imageListPath.get(0).getParStudID()));
						PdfPCell nesCell7 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell8 = new PdfPCell(new Paragraph(imageListPath.get(0).getParStuClass()));
						nesCell5.setBorder(PdfPCell.NO_BORDER);
						nesCell6.setBorder(PdfPCell.NO_BORDER);
						nesCell7.setBorder(PdfPCell.NO_BORDER);
						nesCell8.setBorder(PdfPCell.NO_BORDER);
						nestedTable1.addCell(nesCell5);
						nestedTable1.addCell(nesCell6);
						nestedTable1.addCell(nesCell7);
						nestedTable1.addCell(nesCell8);
						nestedTable1.setSpacingBefore(3f);
						nestedTable1.setSpacingAfter(3f);
						cell1.addElement(nestedTable1);
						PdfPTable nestedTable11 = new PdfPTable(4);
						nestedTable11.setWidthPercentage(100f);
						PdfPCell nesCell51 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell61 = new PdfPCell(new Paragraph(imageListPath.get(1).getParStudID()));
						PdfPCell nesCell71 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell81 = new PdfPCell(new Paragraph(imageListPath.get(1).getParStuClass()));
						nesCell51.setBorder(PdfPCell.NO_BORDER);
						nesCell61.setBorder(PdfPCell.NO_BORDER);
						nesCell71.setBorder(PdfPCell.NO_BORDER);
						nesCell81.setBorder(PdfPCell.NO_BORDER);
						nestedTable11.addCell(nesCell51);
						nestedTable11.addCell(nesCell61);
						nestedTable11.addCell(nesCell71);
						nestedTable11.addCell(nesCell81);
						nestedTable11.setSpacingBefore(3f);
						nestedTable11.setSpacingAfter(3f);
						cell1.addElement(nestedTable11);
						PdfPTable nestedTable12 = new PdfPTable(4);
						nestedTable12.setWidthPercentage(100f);
						PdfPCell nesCell52 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell62 = new PdfPCell(new Paragraph(imageListPath.get(2).getParStudID()));
						PdfPCell nesCell72 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell82 = new PdfPCell(new Paragraph(imageListPath.get(2).getParStuClass()));
						nesCell52.setBorder(PdfPCell.NO_BORDER);
						nesCell62.setBorder(PdfPCell.NO_BORDER);
						nesCell72.setBorder(PdfPCell.NO_BORDER);
						nesCell82.setBorder(PdfPCell.NO_BORDER);
						nestedTable12.addCell(nesCell52);
						nestedTable12.addCell(nesCell62);
						nestedTable12.addCell(nesCell72);
						nestedTable12.addCell(nesCell82);
						nestedTable12.setSpacingBefore(3f);
						nestedTable12.setSpacingAfter(3f);
						cell1.addElement(nestedTable12);
					}
					else if(imageListPath.size()==4)
					{
						PdfPTable nestedTable1 = new PdfPTable(4);
						nestedTable1.setWidthPercentage(100f);
						PdfPCell nesCell5 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell6 = new PdfPCell(new Paragraph(imageListPath.get(0).getParStudID()));
						PdfPCell nesCell7 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell8 = new PdfPCell(new Paragraph(imageListPath.get(0).getParStuClass()));
						nesCell5.setBorder(PdfPCell.NO_BORDER);
						nesCell6.setBorder(PdfPCell.NO_BORDER);
						nesCell7.setBorder(PdfPCell.NO_BORDER);
						nesCell8.setBorder(PdfPCell.NO_BORDER);
						nestedTable1.addCell(nesCell5);
						nestedTable1.addCell(nesCell6);
						nestedTable1.addCell(nesCell7);
						nestedTable1.addCell(nesCell8);
						nestedTable1.setSpacingBefore(3f);
						nestedTable1.setSpacingAfter(3f);
						cell1.addElement(nestedTable1);
						PdfPTable nestedTable11 = new PdfPTable(4);
						nestedTable11.setWidthPercentage(100f);
						PdfPCell nesCell51 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell61 = new PdfPCell(new Paragraph(imageListPath.get(1).getParStudID()));
						PdfPCell nesCell71 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell81 = new PdfPCell(new Paragraph(imageListPath.get(1).getParStuClass()));
						nesCell51.setBorder(PdfPCell.NO_BORDER);
						nesCell61.setBorder(PdfPCell.NO_BORDER);
						nesCell71.setBorder(PdfPCell.NO_BORDER);
						nesCell81.setBorder(PdfPCell.NO_BORDER);
						nestedTable11.addCell(nesCell51);
						nestedTable11.addCell(nesCell61);
						nestedTable11.addCell(nesCell71);
						nestedTable11.addCell(nesCell81);
						nestedTable11.setSpacingBefore(3f);
						nestedTable11.setSpacingAfter(3f);
						cell1.addElement(nestedTable11);
						PdfPTable nestedTable12 = new PdfPTable(4);
						nestedTable12.setWidthPercentage(100f);
						PdfPCell nesCell52 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell62 = new PdfPCell(new Paragraph(imageListPath.get(2).getParStudID()));
						PdfPCell nesCell72 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell82 = new PdfPCell(new Paragraph(imageListPath.get(2).getParStuClass()));
						nesCell52.setBorder(PdfPCell.NO_BORDER);
						nesCell62.setBorder(PdfPCell.NO_BORDER);
						nesCell72.setBorder(PdfPCell.NO_BORDER);
						nesCell82.setBorder(PdfPCell.NO_BORDER);
						nestedTable12.addCell(nesCell52);
						nestedTable12.addCell(nesCell62);
						nestedTable12.addCell(nesCell72);
						nestedTable12.addCell(nesCell82);
						nestedTable12.setSpacingBefore(3f);
						nestedTable12.setSpacingAfter(3f);
						cell1.addElement(nestedTable12);
						PdfPTable nestedTable13 = new PdfPTable(4);
						nestedTable13.setWidthPercentage(100f);
						PdfPCell nesCell53 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell63 = new PdfPCell(new Paragraph(imageListPath.get(3).getParStudID()));
						PdfPCell nesCell73 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell83 = new PdfPCell(new Paragraph(imageListPath.get(3).getParStuClass()));
						nesCell53.setBorder(PdfPCell.NO_BORDER);
						nesCell63.setBorder(PdfPCell.NO_BORDER);
						nesCell73.setBorder(PdfPCell.NO_BORDER);
						nesCell83.setBorder(PdfPCell.NO_BORDER);
						nestedTable13.addCell(nesCell53);
						nestedTable13.addCell(nesCell63);
						nestedTable13.addCell(nesCell73);
						nestedTable13.addCell(nesCell83);
						nestedTable13.setSpacingBefore(3f);
						nestedTable13.setSpacingAfter(3f);
						cell1.addElement(nestedTable13);
					}
					else if(imageListPath.size()==5)
					{
						PdfPTable nestedTable1 = new PdfPTable(4);
						nestedTable1.setWidthPercentage(100f);
						PdfPCell nesCell5 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell6 = new PdfPCell(new Paragraph(imageListPath.get(0).getParStudID()));
						PdfPCell nesCell7 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell8 = new PdfPCell(new Paragraph(imageListPath.get(0).getParStuClass()));
						nesCell5.setBorder(PdfPCell.NO_BORDER);
						nesCell6.setBorder(PdfPCell.NO_BORDER);
						nesCell7.setBorder(PdfPCell.NO_BORDER);
						nesCell8.setBorder(PdfPCell.NO_BORDER);
						nestedTable1.addCell(nesCell5);
						nestedTable1.addCell(nesCell6);
						nestedTable1.addCell(nesCell7);
						nestedTable1.addCell(nesCell8);
						nestedTable1.setSpacingBefore(3f);
						nestedTable1.setSpacingAfter(3f);
						cell1.addElement(nestedTable1);
						PdfPTable nestedTable11 = new PdfPTable(4);
						nestedTable11.setWidthPercentage(100f);
						PdfPCell nesCell51 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell61 = new PdfPCell(new Paragraph(imageListPath.get(1).getParStudID()));
						PdfPCell nesCell71 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell81 = new PdfPCell(new Paragraph(imageListPath.get(1).getParStuClass()));
						nesCell51.setBorder(PdfPCell.NO_BORDER);
						nesCell61.setBorder(PdfPCell.NO_BORDER);
						nesCell71.setBorder(PdfPCell.NO_BORDER);
						nesCell81.setBorder(PdfPCell.NO_BORDER);
						nestedTable11.addCell(nesCell51);
						nestedTable11.addCell(nesCell61);
						nestedTable11.addCell(nesCell71);
						nestedTable11.addCell(nesCell81);
						nestedTable11.setSpacingBefore(3f);
						nestedTable11.setSpacingAfter(3f);
						cell1.addElement(nestedTable11);
						PdfPTable nestedTable12 = new PdfPTable(4);
						nestedTable12.setWidthPercentage(100f);
						PdfPCell nesCell52 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell62 = new PdfPCell(new Paragraph(imageListPath.get(2).getParStudID()));
						PdfPCell nesCell72 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell82 = new PdfPCell(new Paragraph(imageListPath.get(2).getParStuClass()));
						nesCell52.setBorder(PdfPCell.NO_BORDER);
						nesCell62.setBorder(PdfPCell.NO_BORDER);
						nesCell72.setBorder(PdfPCell.NO_BORDER);
						nesCell82.setBorder(PdfPCell.NO_BORDER);
						nestedTable12.addCell(nesCell52);
						nestedTable12.addCell(nesCell62);
						nestedTable12.addCell(nesCell72);
						nestedTable12.addCell(nesCell82);
						nestedTable12.setSpacingBefore(3f);
						nestedTable12.setSpacingAfter(3f);
						cell1.addElement(nestedTable12);
						PdfPTable nestedTable13 = new PdfPTable(4);
						nestedTable13.setWidthPercentage(100f);
						PdfPCell nesCell53 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell63 = new PdfPCell(new Paragraph(imageListPath.get(3).getParStudID()));
						PdfPCell nesCell73 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell83 = new PdfPCell(new Paragraph(imageListPath.get(3).getParStuClass()));
						nesCell53.setBorder(PdfPCell.NO_BORDER);
						nesCell63.setBorder(PdfPCell.NO_BORDER);
						nesCell73.setBorder(PdfPCell.NO_BORDER);
						nesCell83.setBorder(PdfPCell.NO_BORDER);
						nestedTable13.addCell(nesCell53);
						nestedTable13.addCell(nesCell63);
						nestedTable13.addCell(nesCell73);
						nestedTable13.addCell(nesCell83);
						nestedTable13.setSpacingBefore(3f);
						nestedTable13.setSpacingAfter(3f);
						cell1.addElement(nestedTable13);
						PdfPTable nestedTable14 = new PdfPTable(4);
						nestedTable14.setWidthPercentage(100f);
						PdfPCell nesCell54 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell64 = new PdfPCell(new Paragraph(imageListPath.get(4).getParStudID()));
						PdfPCell nesCell74 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell84 = new PdfPCell(new Paragraph(imageListPath.get(4).getParStuClass()));
						nesCell54.setBorder(PdfPCell.NO_BORDER);
						nesCell64.setBorder(PdfPCell.NO_BORDER);
						nesCell74.setBorder(PdfPCell.NO_BORDER);
						nesCell84.setBorder(PdfPCell.NO_BORDER);
						nestedTable14.addCell(nesCell54);
						nestedTable14.addCell(nesCell64);
						nestedTable14.addCell(nesCell74);
						nestedTable14.addCell(nesCell84);
						nestedTable14.setSpacingBefore(3f);
						nestedTable14.setSpacingAfter(3f);
						cell1.addElement(nestedTable14);
					}
					else if(imageListPath.size()==6)
					{
						PdfPTable nestedTable1 = new PdfPTable(4);
						nestedTable1.setWidthPercentage(100f);
						PdfPCell nesCell5 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell6 = new PdfPCell(new Paragraph(imageListPath.get(0).getParStudID()));
						PdfPCell nesCell7 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell8 = new PdfPCell(new Paragraph(imageListPath.get(0).getParStuClass()));
						nesCell5.setBorder(PdfPCell.NO_BORDER);
						nesCell6.setBorder(PdfPCell.NO_BORDER);
						nesCell7.setBorder(PdfPCell.NO_BORDER);
						nesCell8.setBorder(PdfPCell.NO_BORDER);
						nestedTable1.addCell(nesCell5);
						nestedTable1.addCell(nesCell6);
						nestedTable1.addCell(nesCell7);
						nestedTable1.addCell(nesCell8);
						nestedTable1.setSpacingBefore(3f);
						nestedTable1.setSpacingAfter(3f);
						cell1.addElement(nestedTable1);
						PdfPTable nestedTable11 = new PdfPTable(4);
						nestedTable11.setWidthPercentage(100f);
						PdfPCell nesCell51 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell61 = new PdfPCell(new Paragraph(imageListPath.get(1).getParStudID()));
						PdfPCell nesCell71 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell81 = new PdfPCell(new Paragraph(imageListPath.get(1).getParStuClass()));
						nesCell51.setBorder(PdfPCell.NO_BORDER);
						nesCell61.setBorder(PdfPCell.NO_BORDER);
						nesCell71.setBorder(PdfPCell.NO_BORDER);
						nesCell81.setBorder(PdfPCell.NO_BORDER);
						nestedTable11.addCell(nesCell51);
						nestedTable11.addCell(nesCell61);
						nestedTable11.addCell(nesCell71);
						nestedTable11.addCell(nesCell81);
						nestedTable11.setSpacingBefore(3f);
						nestedTable11.setSpacingAfter(3f);
						cell1.addElement(nestedTable11);
						PdfPTable nestedTable12 = new PdfPTable(4);
						nestedTable12.setWidthPercentage(100f);
						PdfPCell nesCell52 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell62 = new PdfPCell(new Paragraph(imageListPath.get(2).getParStudID()));
						PdfPCell nesCell72 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell82 = new PdfPCell(new Paragraph(imageListPath.get(2).getParStuClass()));
						nesCell52.setBorder(PdfPCell.NO_BORDER);
						nesCell62.setBorder(PdfPCell.NO_BORDER);
						nesCell72.setBorder(PdfPCell.NO_BORDER);
						nesCell82.setBorder(PdfPCell.NO_BORDER);
						nestedTable12.addCell(nesCell52);
						nestedTable12.addCell(nesCell62);
						nestedTable12.addCell(nesCell72);
						nestedTable12.addCell(nesCell82);
						nestedTable12.setSpacingBefore(3f);
						nestedTable12.setSpacingAfter(3f);
						cell1.addElement(nestedTable12);
						PdfPTable nestedTable13 = new PdfPTable(4);
						nestedTable13.setWidthPercentage(100f);
						PdfPCell nesCell53 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell63 = new PdfPCell(new Paragraph(imageListPath.get(3).getParStudID()));
						PdfPCell nesCell73 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell83 = new PdfPCell(new Paragraph(imageListPath.get(3).getParStuClass()));
						nesCell53.setBorder(PdfPCell.NO_BORDER);
						nesCell63.setBorder(PdfPCell.NO_BORDER);
						nesCell73.setBorder(PdfPCell.NO_BORDER);
						nesCell83.setBorder(PdfPCell.NO_BORDER);
						nestedTable13.addCell(nesCell53);
						nestedTable13.addCell(nesCell63);
						nestedTable13.addCell(nesCell73);
						nestedTable13.addCell(nesCell83);
						nestedTable13.setSpacingBefore(3f);
						nestedTable13.setSpacingAfter(3f);
						cell1.addElement(nestedTable13);
						PdfPTable nestedTable14 = new PdfPTable(4);
						nestedTable14.setWidthPercentage(100f);
						PdfPCell nesCell54 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell64 = new PdfPCell(new Paragraph(imageListPath.get(4).getParStudID()));
						PdfPCell nesCell74 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell84 = new PdfPCell(new Paragraph(imageListPath.get(4).getParStuClass()));
						nesCell54.setBorder(PdfPCell.NO_BORDER);
						nesCell64.setBorder(PdfPCell.NO_BORDER);
						nesCell74.setBorder(PdfPCell.NO_BORDER);
						nesCell84.setBorder(PdfPCell.NO_BORDER);
						nestedTable14.addCell(nesCell54);
						nestedTable14.addCell(nesCell64);
						nestedTable14.addCell(nesCell74);
						nestedTable14.addCell(nesCell84);
						nestedTable14.setSpacingBefore(3f);
						nestedTable14.setSpacingAfter(3f);
						cell1.addElement(nestedTable14);
						PdfPTable nestedTable15 = new PdfPTable(4);
						nestedTable15.setWidthPercentage(100f);
						PdfPCell nesCell55 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell65 = new PdfPCell(new Paragraph(imageListPath.get(5).getParStudID()));
						PdfPCell nesCell75 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell85 = new PdfPCell(new Paragraph(imageListPath.get(5).getParStuClass()));
						nesCell55.setBorder(PdfPCell.NO_BORDER);
						nesCell65.setBorder(PdfPCell.NO_BORDER);
						nesCell75.setBorder(PdfPCell.NO_BORDER);
						nesCell85.setBorder(PdfPCell.NO_BORDER);
						nestedTable15.addCell(nesCell55);
						nestedTable15.addCell(nesCell65);
						nestedTable15.addCell(nesCell75);
						nestedTable15.addCell(nesCell85);
						nestedTable15.setSpacingBefore(3f);
						nestedTable15.setSpacingAfter(3f);
						cell1.addElement(nestedTable15);
					}
					else if(imageListPath.size()==7)
					{
						PdfPTable nestedTable1 = new PdfPTable(4);
						nestedTable1.setWidthPercentage(100f);
						PdfPCell nesCell5 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell6 = new PdfPCell(new Paragraph(imageListPath.get(0).getParStudID()));
						PdfPCell nesCell7 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell8 = new PdfPCell(new Paragraph(imageListPath.get(0).getParStuClass()));
						nesCell5.setBorder(PdfPCell.NO_BORDER);
						nesCell6.setBorder(PdfPCell.NO_BORDER);
						nesCell7.setBorder(PdfPCell.NO_BORDER);
						nesCell8.setBorder(PdfPCell.NO_BORDER);
						nestedTable1.addCell(nesCell5);
						nestedTable1.addCell(nesCell6);
						nestedTable1.addCell(nesCell7);
						nestedTable1.addCell(nesCell8);
						nestedTable1.setSpacingBefore(3f);
						nestedTable1.setSpacingAfter(3f);
						cell1.addElement(nestedTable1);
						PdfPTable nestedTable11 = new PdfPTable(4);
						nestedTable11.setWidthPercentage(100f);
						PdfPCell nesCell51 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell61 = new PdfPCell(new Paragraph(imageListPath.get(1).getParStudID()));
						PdfPCell nesCell71 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell81 = new PdfPCell(new Paragraph(imageListPath.get(1).getParStuClass()));
						nesCell51.setBorder(PdfPCell.NO_BORDER);
						nesCell61.setBorder(PdfPCell.NO_BORDER);
						nesCell71.setBorder(PdfPCell.NO_BORDER);
						nesCell81.setBorder(PdfPCell.NO_BORDER);
						nestedTable11.addCell(nesCell51);
						nestedTable11.addCell(nesCell61);
						nestedTable11.addCell(nesCell71);
						nestedTable11.addCell(nesCell81);
						nestedTable11.setSpacingBefore(3f);
						nestedTable11.setSpacingAfter(3f);
						cell1.addElement(nestedTable11);
						PdfPTable nestedTable12 = new PdfPTable(4);
						nestedTable12.setWidthPercentage(100f);
						PdfPCell nesCell52 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell62 = new PdfPCell(new Paragraph(imageListPath.get(2).getParStudID()));
						PdfPCell nesCell72 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell82 = new PdfPCell(new Paragraph(imageListPath.get(2).getParStuClass()));
						nesCell52.setBorder(PdfPCell.NO_BORDER);
						nesCell62.setBorder(PdfPCell.NO_BORDER);
						nesCell72.setBorder(PdfPCell.NO_BORDER);
						nesCell82.setBorder(PdfPCell.NO_BORDER);
						nestedTable12.addCell(nesCell52);
						nestedTable12.addCell(nesCell62);
						nestedTable12.addCell(nesCell72);
						nestedTable12.addCell(nesCell82);
						nestedTable12.setSpacingBefore(3f);
						nestedTable12.setSpacingAfter(3f);
						cell1.addElement(nestedTable12);
						PdfPTable nestedTable13 = new PdfPTable(4);
						nestedTable13.setWidthPercentage(100f);
						PdfPCell nesCell53 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell63 = new PdfPCell(new Paragraph(imageListPath.get(3).getParStudID()));
						PdfPCell nesCell73 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell83 = new PdfPCell(new Paragraph(imageListPath.get(3).getParStuClass()));
						nesCell53.setBorder(PdfPCell.NO_BORDER);
						nesCell63.setBorder(PdfPCell.NO_BORDER);
						nesCell73.setBorder(PdfPCell.NO_BORDER);
						nesCell83.setBorder(PdfPCell.NO_BORDER);
						nestedTable13.addCell(nesCell53);
						nestedTable13.addCell(nesCell63);
						nestedTable13.addCell(nesCell73);
						nestedTable13.addCell(nesCell83);
						nestedTable13.setSpacingBefore(3f);
						nestedTable13.setSpacingAfter(3f);
						cell1.addElement(nestedTable13);
						PdfPTable nestedTable14 = new PdfPTable(4);
						nestedTable14.setWidthPercentage(100f);
						PdfPCell nesCell54 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell64 = new PdfPCell(new Paragraph(imageListPath.get(4).getParStudID()));
						PdfPCell nesCell74 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell84 = new PdfPCell(new Paragraph(imageListPath.get(4).getParStuClass()));
						nesCell54.setBorder(PdfPCell.NO_BORDER);
						nesCell64.setBorder(PdfPCell.NO_BORDER);
						nesCell74.setBorder(PdfPCell.NO_BORDER);
						nesCell84.setBorder(PdfPCell.NO_BORDER);
						nestedTable14.addCell(nesCell54);
						nestedTable14.addCell(nesCell64);
						nestedTable14.addCell(nesCell74);
						nestedTable14.addCell(nesCell84);
						nestedTable14.setSpacingBefore(3f);
						nestedTable14.setSpacingAfter(3f);
						cell1.addElement(nestedTable14);
						PdfPTable nestedTable15 = new PdfPTable(4);
						nestedTable15.setWidthPercentage(100f);
						PdfPCell nesCell55 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell65 = new PdfPCell(new Paragraph(imageListPath.get(5).getParStudID()));
						PdfPCell nesCell75 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell85 = new PdfPCell(new Paragraph(imageListPath.get(5).getParStuClass()));
						nesCell55.setBorder(PdfPCell.NO_BORDER);
						nesCell65.setBorder(PdfPCell.NO_BORDER);
						nesCell75.setBorder(PdfPCell.NO_BORDER);
						nesCell85.setBorder(PdfPCell.NO_BORDER);
						nestedTable15.addCell(nesCell55);
						nestedTable15.addCell(nesCell65);
						nestedTable15.addCell(nesCell75);
						nestedTable15.addCell(nesCell85);
						nestedTable15.setSpacingBefore(3f);
						nestedTable15.setSpacingAfter(3f);
						cell1.addElement(nestedTable15);
						PdfPTable nestedTable16 = new PdfPTable(4);
						nestedTable16.setWidthPercentage(100f);
						PdfPCell nesCell56 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell66 = new PdfPCell(new Paragraph(imageListPath.get(6).getParStudID()));
						PdfPCell nesCell76 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell86 = new PdfPCell(new Paragraph(imageListPath.get(6).getParStuClass()));
						nesCell56.setBorder(PdfPCell.NO_BORDER);
						nesCell66.setBorder(PdfPCell.NO_BORDER);
						nesCell76.setBorder(PdfPCell.NO_BORDER);
						nesCell86.setBorder(PdfPCell.NO_BORDER);
						nestedTable16.addCell(nesCell56);
						nestedTable16.addCell(nesCell66);
						nestedTable16.addCell(nesCell76);
						nestedTable16.addCell(nesCell86);
						nestedTable16.setSpacingBefore(3f);
						nestedTable16.setSpacingAfter(3f);
						cell1.addElement(nestedTable16);
					}
					else if(imageListPath.size()==8)
					{
						PdfPTable nestedTable1 = new PdfPTable(4);
						nestedTable1.setWidthPercentage(100f);
						PdfPCell nesCell5 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell6 = new PdfPCell(new Paragraph(imageListPath.get(0).getParStudID()));
						PdfPCell nesCell7 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell8 = new PdfPCell(new Paragraph(imageListPath.get(0).getParStuClass()));
						nesCell5.setBorder(PdfPCell.NO_BORDER);
						nesCell6.setBorder(PdfPCell.NO_BORDER);
						nesCell7.setBorder(PdfPCell.NO_BORDER);
						nesCell8.setBorder(PdfPCell.NO_BORDER);
						nestedTable1.addCell(nesCell5);
						nestedTable1.addCell(nesCell6);
						nestedTable1.addCell(nesCell7);
						nestedTable1.addCell(nesCell8);
						nestedTable1.setSpacingBefore(3f);
						nestedTable1.setSpacingAfter(3f);
						cell1.addElement(nestedTable1);
						PdfPTable nestedTable11 = new PdfPTable(4);
						nestedTable11.setWidthPercentage(100f);
						PdfPCell nesCell51 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell61 = new PdfPCell(new Paragraph(imageListPath.get(1).getParStudID()));
						PdfPCell nesCell71 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell81 = new PdfPCell(new Paragraph(imageListPath.get(1).getParStuClass()));
						nesCell51.setBorder(PdfPCell.NO_BORDER);
						nesCell61.setBorder(PdfPCell.NO_BORDER);
						nesCell71.setBorder(PdfPCell.NO_BORDER);
						nesCell81.setBorder(PdfPCell.NO_BORDER);
						nestedTable11.addCell(nesCell51);
						nestedTable11.addCell(nesCell61);
						nestedTable11.addCell(nesCell71);
						nestedTable11.addCell(nesCell81);
						nestedTable11.setSpacingBefore(3f);
						nestedTable11.setSpacingAfter(3f);
						cell1.addElement(nestedTable11);
						PdfPTable nestedTable12 = new PdfPTable(4);
						nestedTable12.setWidthPercentage(100f);
						PdfPCell nesCell52 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell62 = new PdfPCell(new Paragraph(imageListPath.get(2).getParStudID()));
						PdfPCell nesCell72 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell82 = new PdfPCell(new Paragraph(imageListPath.get(2).getParStuClass()));
						nesCell52.setBorder(PdfPCell.NO_BORDER);
						nesCell62.setBorder(PdfPCell.NO_BORDER);
						nesCell72.setBorder(PdfPCell.NO_BORDER);
						nesCell82.setBorder(PdfPCell.NO_BORDER);
						nestedTable12.addCell(nesCell52);
						nestedTable12.addCell(nesCell62);
						nestedTable12.addCell(nesCell72);
						nestedTable12.addCell(nesCell82);
						nestedTable12.setSpacingBefore(3f);
						nestedTable12.setSpacingAfter(3f);
						cell1.addElement(nestedTable12);
						PdfPTable nestedTable13 = new PdfPTable(4);
						nestedTable13.setWidthPercentage(100f);
						PdfPCell nesCell53 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell63 = new PdfPCell(new Paragraph(imageListPath.get(3).getParStudID()));
						PdfPCell nesCell73 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell83 = new PdfPCell(new Paragraph(imageListPath.get(3).getParStuClass()));
						nesCell53.setBorder(PdfPCell.NO_BORDER);
						nesCell63.setBorder(PdfPCell.NO_BORDER);
						nesCell73.setBorder(PdfPCell.NO_BORDER);
						nesCell83.setBorder(PdfPCell.NO_BORDER);
						nestedTable13.addCell(nesCell53);
						nestedTable13.addCell(nesCell63);
						nestedTable13.addCell(nesCell73);
						nestedTable13.addCell(nesCell83);
						nestedTable13.setSpacingBefore(3f);
						nestedTable13.setSpacingAfter(3f);
						cell1.addElement(nestedTable13);
						PdfPTable nestedTable14 = new PdfPTable(4);
						nestedTable14.setWidthPercentage(100f);
						PdfPCell nesCell54 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell64 = new PdfPCell(new Paragraph(imageListPath.get(4).getParStudID()));
						PdfPCell nesCell74 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell84 = new PdfPCell(new Paragraph(imageListPath.get(4).getParStuClass()));
						nesCell54.setBorder(PdfPCell.NO_BORDER);
						nesCell64.setBorder(PdfPCell.NO_BORDER);
						nesCell74.setBorder(PdfPCell.NO_BORDER);
						nesCell84.setBorder(PdfPCell.NO_BORDER);
						nestedTable14.addCell(nesCell54);
						nestedTable14.addCell(nesCell64);
						nestedTable14.addCell(nesCell74);
						nestedTable14.addCell(nesCell84);
						nestedTable14.setSpacingBefore(3f);
						nestedTable14.setSpacingAfter(3f);
						cell1.addElement(nestedTable14);
						PdfPTable nestedTable15 = new PdfPTable(4);
						nestedTable15.setWidthPercentage(100f);
						PdfPCell nesCell55 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell65 = new PdfPCell(new Paragraph(imageListPath.get(5).getParStudID()));
						PdfPCell nesCell75 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell85 = new PdfPCell(new Paragraph(imageListPath.get(5).getParStuClass()));
						nesCell55.setBorder(PdfPCell.NO_BORDER);
						nesCell65.setBorder(PdfPCell.NO_BORDER);
						nesCell75.setBorder(PdfPCell.NO_BORDER);
						nesCell85.setBorder(PdfPCell.NO_BORDER);
						nestedTable15.addCell(nesCell55);
						nestedTable15.addCell(nesCell65);
						nestedTable15.addCell(nesCell75);
						nestedTable15.addCell(nesCell85);
						nestedTable15.setSpacingBefore(3f);
						nestedTable15.setSpacingAfter(3f);
						cell1.addElement(nestedTable15);
						PdfPTable nestedTable16 = new PdfPTable(4);
						nestedTable16.setWidthPercentage(100f);
						PdfPCell nesCell56 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell66 = new PdfPCell(new Paragraph(imageListPath.get(6).getParStudID()));
						PdfPCell nesCell76 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell86 = new PdfPCell(new Paragraph(imageListPath.get(6).getParStuClass()));
						nesCell56.setBorder(PdfPCell.NO_BORDER);
						nesCell66.setBorder(PdfPCell.NO_BORDER);
						nesCell76.setBorder(PdfPCell.NO_BORDER);
						nesCell86.setBorder(PdfPCell.NO_BORDER);
						nestedTable16.addCell(nesCell56);
						nestedTable16.addCell(nesCell66);
						nestedTable16.addCell(nesCell76);
						nestedTable16.addCell(nesCell86);
						nestedTable16.setSpacingBefore(3f);
						nestedTable16.setSpacingAfter(3f);
						cell1.addElement(nestedTable16);
						PdfPTable nestedTable17 = new PdfPTable(4);
						nestedTable17.setWidthPercentage(100f);
						PdfPCell nesCell57 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell67 = new PdfPCell(new Paragraph(imageListPath.get(7).getParStudID()));
						PdfPCell nesCell77 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell87 = new PdfPCell(new Paragraph(imageListPath.get(7).getParStuClass()));
						nesCell57.setBorder(PdfPCell.NO_BORDER);
						nesCell67.setBorder(PdfPCell.NO_BORDER);
						nesCell77.setBorder(PdfPCell.NO_BORDER);
						nesCell87.setBorder(PdfPCell.NO_BORDER);
						nestedTable17.addCell(nesCell57);
						nestedTable17.addCell(nesCell67);
						nestedTable17.addCell(nesCell77);
						nestedTable17.addCell(nesCell87);
						nestedTable17.setSpacingBefore(3f);
						nestedTable17.setSpacingAfter(3f);
						cell1.addElement(nestedTable17);
					}
					else if(imageListPath.size()==9)
					{
						PdfPTable nestedTable1 = new PdfPTable(4);
						nestedTable1.setWidthPercentage(100f);
						PdfPCell nesCell5 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell6 = new PdfPCell(new Paragraph(imageListPath.get(0).getParStudID()));
						PdfPCell nesCell7 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell8 = new PdfPCell(new Paragraph(imageListPath.get(0).getParStuClass()));
						nesCell5.setBorder(PdfPCell.NO_BORDER);
						nesCell6.setBorder(PdfPCell.NO_BORDER);
						nesCell7.setBorder(PdfPCell.NO_BORDER);
						nesCell8.setBorder(PdfPCell.NO_BORDER);
						nestedTable1.addCell(nesCell5);
						nestedTable1.addCell(nesCell6);
						nestedTable1.addCell(nesCell7);
						nestedTable1.addCell(nesCell8);
						nestedTable1.setSpacingBefore(3f);
						nestedTable1.setSpacingAfter(3f);
						cell1.addElement(nestedTable1);
						PdfPTable nestedTable11 = new PdfPTable(4);
						nestedTable11.setWidthPercentage(100f);
						PdfPCell nesCell51 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell61 = new PdfPCell(new Paragraph(imageListPath.get(1).getParStudID()));
						PdfPCell nesCell71 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell81 = new PdfPCell(new Paragraph(imageListPath.get(1).getParStuClass()));
						nesCell51.setBorder(PdfPCell.NO_BORDER);
						nesCell61.setBorder(PdfPCell.NO_BORDER);
						nesCell71.setBorder(PdfPCell.NO_BORDER);
						nesCell81.setBorder(PdfPCell.NO_BORDER);
						nestedTable11.addCell(nesCell51);
						nestedTable11.addCell(nesCell61);
						nestedTable11.addCell(nesCell71);
						nestedTable11.addCell(nesCell81);
						nestedTable11.setSpacingBefore(3f);
						nestedTable11.setSpacingAfter(3f);
						cell1.addElement(nestedTable11);
						PdfPTable nestedTable12 = new PdfPTable(4);
						nestedTable12.setWidthPercentage(100f);
						PdfPCell nesCell52 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell62 = new PdfPCell(new Paragraph(imageListPath.get(2).getParStudID()));
						PdfPCell nesCell72 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell82 = new PdfPCell(new Paragraph(imageListPath.get(2).getParStuClass()));
						nesCell52.setBorder(PdfPCell.NO_BORDER);
						nesCell62.setBorder(PdfPCell.NO_BORDER);
						nesCell72.setBorder(PdfPCell.NO_BORDER);
						nesCell82.setBorder(PdfPCell.NO_BORDER);
						nestedTable12.addCell(nesCell52);
						nestedTable12.addCell(nesCell62);
						nestedTable12.addCell(nesCell72);
						nestedTable12.addCell(nesCell82);
						nestedTable12.setSpacingBefore(3f);
						nestedTable12.setSpacingAfter(3f);
						cell1.addElement(nestedTable12);
						PdfPTable nestedTable13 = new PdfPTable(4);
						nestedTable13.setWidthPercentage(100f);
						PdfPCell nesCell53 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell63 = new PdfPCell(new Paragraph(imageListPath.get(3).getParStudID()));
						PdfPCell nesCell73 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell83 = new PdfPCell(new Paragraph(imageListPath.get(3).getParStuClass()));
						nesCell53.setBorder(PdfPCell.NO_BORDER);
						nesCell63.setBorder(PdfPCell.NO_BORDER);
						nesCell73.setBorder(PdfPCell.NO_BORDER);
						nesCell83.setBorder(PdfPCell.NO_BORDER);
						nestedTable13.addCell(nesCell53);
						nestedTable13.addCell(nesCell63);
						nestedTable13.addCell(nesCell73);
						nestedTable13.addCell(nesCell83);
						nestedTable13.setSpacingBefore(3f);
						nestedTable13.setSpacingAfter(3f);
						cell1.addElement(nestedTable13);
						PdfPTable nestedTable14 = new PdfPTable(4);
						nestedTable14.setWidthPercentage(100f);
						PdfPCell nesCell54 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell64 = new PdfPCell(new Paragraph(imageListPath.get(4).getParStudID()));
						PdfPCell nesCell74 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell84 = new PdfPCell(new Paragraph(imageListPath.get(4).getParStuClass()));
						nesCell54.setBorder(PdfPCell.NO_BORDER);
						nesCell64.setBorder(PdfPCell.NO_BORDER);
						nesCell74.setBorder(PdfPCell.NO_BORDER);
						nesCell84.setBorder(PdfPCell.NO_BORDER);
						nestedTable14.addCell(nesCell54);
						nestedTable14.addCell(nesCell64);
						nestedTable14.addCell(nesCell74);
						nestedTable14.addCell(nesCell84);
						nestedTable14.setSpacingBefore(3f);
						nestedTable14.setSpacingAfter(3f);
						cell1.addElement(nestedTable14);
						PdfPTable nestedTable15 = new PdfPTable(4);
						nestedTable15.setWidthPercentage(100f);
						PdfPCell nesCell55 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell65 = new PdfPCell(new Paragraph(imageListPath.get(5).getParStudID()));
						PdfPCell nesCell75 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell85 = new PdfPCell(new Paragraph(imageListPath.get(5).getParStuClass()));
						nesCell55.setBorder(PdfPCell.NO_BORDER);
						nesCell65.setBorder(PdfPCell.NO_BORDER);
						nesCell75.setBorder(PdfPCell.NO_BORDER);
						nesCell85.setBorder(PdfPCell.NO_BORDER);
						nestedTable15.addCell(nesCell55);
						nestedTable15.addCell(nesCell65);
						nestedTable15.addCell(nesCell75);
						nestedTable15.addCell(nesCell85);
						nestedTable15.setSpacingBefore(3f);
						nestedTable15.setSpacingAfter(3f);
						cell1.addElement(nestedTable15);
						PdfPTable nestedTable16 = new PdfPTable(4);
						nestedTable16.setWidthPercentage(100f);
						PdfPCell nesCell56 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell66 = new PdfPCell(new Paragraph(imageListPath.get(6).getParStudID()));
						PdfPCell nesCell76 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell86 = new PdfPCell(new Paragraph(imageListPath.get(6).getParStuClass()));
						nesCell56.setBorder(PdfPCell.NO_BORDER);
						nesCell66.setBorder(PdfPCell.NO_BORDER);
						nesCell76.setBorder(PdfPCell.NO_BORDER);
						nesCell86.setBorder(PdfPCell.NO_BORDER);
						nestedTable16.addCell(nesCell56);
						nestedTable16.addCell(nesCell66);
						nestedTable16.addCell(nesCell76);
						nestedTable16.addCell(nesCell86);
						nestedTable16.setSpacingBefore(3f);
						nestedTable16.setSpacingAfter(3f);
						cell1.addElement(nestedTable16);
						PdfPTable nestedTable18 = new PdfPTable(4);
						nestedTable18.setWidthPercentage(100f);
						PdfPCell nesCell58 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell68 = new PdfPCell(new Paragraph(imageListPath.get(8).getParStudID()));
						PdfPCell nesCell78 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell88 = new PdfPCell(new Paragraph(imageListPath.get(8).getParStuClass()));
						nesCell58.setBorder(PdfPCell.NO_BORDER);
						nesCell68.setBorder(PdfPCell.NO_BORDER);
						nesCell78.setBorder(PdfPCell.NO_BORDER);
						nesCell88.setBorder(PdfPCell.NO_BORDER);
						nestedTable18.addCell(nesCell58);
						nestedTable18.addCell(nesCell68);
						nestedTable18.addCell(nesCell78);
						nestedTable18.addCell(nesCell88);
						nestedTable18.setSpacingBefore(3f);
						nestedTable18.setSpacingAfter(3f);
						cell1.addElement(nestedTable18);
					}
					PdfPTable nestedTable2 = new PdfPTable(4);
					nestedTable2.setWidthPercentage(100f);
					PdfPCell nesCell9 = new PdfPCell(new Paragraph("Phone number:"));
					PdfPCell nesCell10 = new PdfPCell(new Paragraph(imageListPath.get(0).getParPhoneNo()));
					PdfPCell nesCell11 = new PdfPCell(new Paragraph("Email ID:"));
					PdfPCell nesCell12 = new PdfPCell(new Paragraph(imageListPath.get(0).getParEmail()));
					nesCell9.setBorder(PdfPCell.NO_BORDER);
					nesCell10.setBorder(PdfPCell.NO_BORDER);
					nesCell11.setBorder(PdfPCell.NO_BORDER);
					nesCell12.setBorder(PdfPCell.NO_BORDER);
					nestedTable2.addCell(nesCell9);
					nestedTable2.addCell(nesCell10);
					nestedTable2.addCell(nesCell11);
					nestedTable2.addCell(nesCell12);
					nestedTable2.setSpacingBefore(3f);
					nestedTable2.setSpacingAfter(3f);
					cell1.addElement(nestedTable2);

					PdfPTable nestedTable3 = new PdfPTable(4);
					nestedTable3.setWidthPercentage(100f);
					PdfPCell nesCell13 = new PdfPCell(new Paragraph("Relation:"));
					PdfPCell nesCell14 = new PdfPCell(new Paragraph(imageListPath.get(0).getParentRelation()));
					PdfPCell nesCell15 = new PdfPCell(new Paragraph("Parent ID:"));
					PdfPCell nesCell16 = new PdfPCell(new Paragraph(imageListPath.get(0).getParParentID()));
					nesCell13.setBorder(PdfPCell.NO_BORDER);
					nesCell14.setBorder(PdfPCell.NO_BORDER);
					nesCell15.setBorder(PdfPCell.NO_BORDER);
					nesCell16.setBorder(PdfPCell.NO_BORDER);
					nestedTable3.addCell(nesCell13);
					nestedTable3.addCell(nesCell14);
					nestedTable3.addCell(nesCell15);
					nestedTable3.addCell(nesCell16);
					nestedTable3.setSpacingBefore(3f);
					nestedTable3.setSpacingAfter(3f);
					cell1.addElement(nestedTable3);

				if(imageListPath.get(0).getParfilePath()=="" || imageListPath.get(0).getParPathDate()==null){
				}else{
					PdfPTable nestedTable9 = new PdfPTable(4);
					nestedTable9.setWidthPercentage(100f);
					PdfPCell nesCell33 = new PdfPCell(new Paragraph());
					PdfPCell nesCell34 = new PdfPCell(new Paragraph());
					PdfPCell nesCell35 = new PdfPCell(new Paragraph("Photo"));
					String tempdate = ft.format(imageListPath.get(0).getParPathDate());
					String imageLocation = paths.getString("sms.parents.photo") + tempdate + "/" + imageListPath.get(0).getParfilePath();
					Image image = Image.getInstance(imageLocation);
					image.scaleAbsolute(100, 100);
					PdfPCell nesCell36 = new PdfPCell(image, false);
					nesCell33.setBorder(PdfPCell.NO_BORDER);
					nesCell34.setBorder(PdfPCell.NO_BORDER);
					nesCell35.setBorder(PdfPCell.NO_BORDER);
					nesCell36.setBorder(PdfPCell.NO_BORDER);
					nestedTable9.addCell(nesCell35);
					nestedTable9.addCell(nesCell36);
					nestedTable9.addCell(nesCell33);
					nestedTable9.addCell(nesCell34);

					nestedTable9.setSpacingBefore(3f);
					nestedTable9.setSpacingAfter(3f);
					cell1.addElement(nestedTable9);
					}
					table.addCell(cell1);
					table.addCell(cell2);
					document.add(table);
					document.close();
					file.close();
					logger.debug("Done");
					status = "Success";
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			e.printStackTrace();
		}
		return status;
	}
	public static String sendEmailParent(ParentsDataBean parentsDataBean) {
		String status = "Fail";

		try {
			// String to="robertarjun46@gmail.com";
			String to = parentsDataBean.getParEmail();
			Properties prop = new Properties();
			prop.put("mail.smtp.host", MAIL_HOST);
			prop.put("mail.smtp.socketFactory.port", MAIL_PORT);
			prop.put("mail.smtp.socketFactory.class", MAIL_CLASS);
			prop.put("mail.smtp.starttls.enable", MAIL_STARTLES);
			prop.put("mail.smtp.auth", MAIL_AUTH);
			prop.put("mail.smtp.port", MAIL_SMTP_PORT);
			prop.put("mail.smtp.ssl.trust", MAIL_TRUST);
			String body1 = "<htm><head></heade><body>"
					+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
					+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
					+ "<h3>Dear " + parentsDataBean.getParUsername() + ",</h3>"
					+ "<p>Welcome to join  our Student Monitoring System. you can find the following features  ."
					+ "you can check your child report card, attendence,online payment,notice board and time table."
					+ "also you can get our user name and password, from that you can check our website itself</p>"
					+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
					+ "<tr>" + "<td>User Name" + "</td>" + "<td>" + parentsDataBean.getParUsername() + "</td>"
					+ "</tr>" + "<tr>" + "<td>Password" + "</td>" + "<td>" + parentsDataBean.getParSecurePasword()
					+ "</td>" + "</tr>" + "</table>" + "<footer>" + "<center>"
					+ "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>" + "</body></html>";

			Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
				}
			});

			// compose message
			try {
				/*
				 * session = Session.getInstance(prop, this);// this is the
				 * local variable not the instance one
				 */ MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("SMS Confirmation-" + parentsDataBean.getParUsername());
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");

				MimeBodyPart messageBodyPart2 = new MimeBodyPart();
				String filename = "C://Files/PDF/Parent/" + ft.format(now) + "/" + parentsDataBean.getParUsername()
						+ ".pdf";// change accordingly
				DataSource source = new FileDataSource(filename);
				messageBodyPart2.setDataHandler(new DataHandler(source));
				messageBodyPart2.setFileName(parentsDataBean.getParUsername() + ".pdf");

				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				multipart.addBodyPart(messageBodyPart2);
				message.setContent(multipart);

				Transport.send(message);

				logger.debug("message sent successfully");
				status = "Success";

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}

		} catch (Exception e) {
			logger.warn("Inside Exception",e);
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}
	
	/*TEACHER UPDATE*/
	
	public static String imageTeacherUpdate(String file, InputStream inputStream, String id) throws IOException{
		String status = "Fail";
		try{

			File files = new File("C:\\Photos\\Teacher\\" + ft.format(now));
			if (!files.exists()) {
				files.mkdirs();
			} else {
				logger.debug("Alreday Found");
			}

			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(files + "\\" + file + "." + id));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			inputStream.close();
			out.flush();
			out.close();
			status="Success";
		}
		catch(Exception e){
			logger.warn("Inside Exception",e);
		}
		return status;		
	}

	public static String generatePdfexamTimeTable(TimeTableDataBean timeTableDataBean,String personID) {
		String status = "fail";
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		try {
			if (personID != null) {
				File files = new File(paths.getString("sms.timetable.pdf") + ft.format(now));
				if (!files.exists()) {
					files.mkdirs();
				} else {
					logger.debug("Alreday Found");
				}
				// Write PDF File
				OutputStream file = new FileOutputStream(
						new File(files + paths.getString("path_context").toString() + timeTableDataBean.getExamTitle() + ".pdf"));
				timeTableDataBean.setFilepath(files + paths.getString("path_context").toString() + timeTableDataBean.getExamTitle() + ".pdf");
				Document document = new Document(PageSize.A4, 50, 50, 10, 50);
				PdfWriter.getInstance(document, file);
				document.open();
				String logo = paths.getString(timeTableDataBean.getSchoolID()+"_LOGO");
				document.add(Image.getInstance(new Object(){}.getClass().getClassLoader().getResource(logo)));
				float[] columnWidths = { 20f, 1f };
				Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 16);
				PdfPTable table = new PdfPTable(2); //     .
				table.setWidthPercentage(100f);
				table.setWidths(columnWidths);
				PdfPCell cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(2);
				cell.setBorder(PdfPCell.NO_BORDER);
				PdfPCell cell1 = new PdfPCell(new Paragraph());
				cell1.setBorder(PdfPCell.NO_BORDER);
				PdfPCell cell2 = new PdfPCell(new Paragraph());
				cell2.setBorder(PdfPCell.NO_BORDER);
				PdfPCell cell3 = new PdfPCell(new Paragraph());
				cell3.setBorder(PdfPCell.NO_BORDER);
				PdfPCell cell4 = new PdfPCell(new Paragraph());
				cell4.setBorder(PdfPCell.NO_BORDER);
				PdfPCell cell5 = new PdfPCell(new Paragraph());
				cell5.setBorder(PdfPCell.NO_BORDER);
				table.setSpacingBefore(10f);
				table.setSpacingAfter(25f);

				PdfPTable headTable = new PdfPTable(5); //     .
				headTable.setWidthPercentage(100f);
				PdfPCell headCell = new PdfPCell(new Paragraph("Exam : ", font3));
				PdfPCell headCell1 = new PdfPCell(
						new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamTitle(), font3));
				PdfPCell headCell2 = new PdfPCell(new Paragraph("Class : ", font3));
				PdfPCell headCell3 = new PdfPCell(
						new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamClass(), font3));
				PdfPCell headCell4 = new PdfPCell(new Paragraph(""));
				headCell.setBorder(PdfPCell.NO_BORDER);
				headCell1.setBorder(PdfPCell.NO_BORDER);
				headCell2.setBorder(PdfPCell.NO_BORDER);
				headCell3.setBorder(PdfPCell.NO_BORDER);
				headCell4.setBorder(PdfPCell.NO_BORDER);
				headTable.addCell(headCell);
				headTable.addCell(headCell1);
				headTable.addCell(headCell2);
				headTable.addCell(headCell3);
				headTable.addCell(headCell4);
				headTable.setSpacingBefore(3f);
				headTable.setSpacingAfter(3f);
				cell1.addElement(headTable);

				PdfPTable nestedTable = new PdfPTable(5);
				nestedTable.setWidthPercentage(100f);
				PdfPCell nesCell1 = new PdfPCell(new Paragraph("Exam Date ", font3));
				PdfPCell nesCell2 = new PdfPCell(new Paragraph("Subject ", font3));
				PdfPCell nesCell3 = new PdfPCell(new Paragraph("Room No ", font3));
				PdfPCell nesCell4 = new PdfPCell(new Paragraph("Shift ", font3));
				PdfPCell nesCell5 = new PdfPCell(new Paragraph("Day ", font3));
				
				nesCell1.setBorder(PdfPCell.NO_BORDER);
				nesCell2.setBorder(PdfPCell.NO_BORDER);
				nesCell3.setBorder(PdfPCell.NO_BORDER);
				nesCell4.setBorder(PdfPCell.NO_BORDER);
				nesCell5.setBorder(PdfPCell.NO_BORDER);
				nestedTable.addCell(nesCell1);
				nestedTable.addCell(nesCell2);
				nestedTable.addCell(nesCell3);
				nestedTable.addCell(nesCell4);
				nestedTable.addCell(nesCell5);
				nestedTable.setSpacingBefore(3f);
				nestedTable.setSpacingAfter(3f);
				cell1.addElement(nestedTable);

				if (timeTableDataBean.getClassList().size() == 1) {
					PdfPTable nestedTable1 = new PdfPTable(5);
					nestedTable1.setWidthPercentage(100f);
					PdfPCell nesCell11 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(0).getExamStartDate())));
					PdfPCell nesCell12 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamSubject()));
					PdfPCell nesCell13 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamRoomNo()));
					PdfPCell nesCell14 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamShift()));
					PdfPCell nesCell15 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamDay()));
					nesCell11.setBorder(PdfPCell.NO_BORDER);
					nesCell12.setBorder(PdfPCell.NO_BORDER);
					nesCell13.setBorder(PdfPCell.NO_BORDER);
					nesCell14.setBorder(PdfPCell.NO_BORDER);
					nesCell15.setBorder(PdfPCell.NO_BORDER);
					nestedTable1.addCell(nesCell11);
					nestedTable1.addCell(nesCell12);
					nestedTable1.addCell(nesCell13);
					nestedTable1.addCell(nesCell14);
					nestedTable1.addCell(nesCell15);
					nestedTable1.setSpacingBefore(3f);
					nestedTable1.setSpacingAfter(3f);
					cell1.addElement(nestedTable1);
				} else if (timeTableDataBean.getClassList().size() == 2) {
					PdfPTable nestedTable1 = new PdfPTable(5);
					nestedTable1.setWidthPercentage(100f);
					PdfPCell nesCell11 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(0).getExamStartDate())));
					PdfPCell nesCell12 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamSubject()));
					PdfPCell nesCell13 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamRoomNo()));
					PdfPCell nesCell14 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamShift()));
					PdfPCell nesCell15 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamDay()));
					nesCell11.setBorder(PdfPCell.NO_BORDER);
					nesCell12.setBorder(PdfPCell.NO_BORDER);
					nesCell13.setBorder(PdfPCell.NO_BORDER);
					nesCell14.setBorder(PdfPCell.NO_BORDER);
					nesCell15.setBorder(PdfPCell.NO_BORDER);
					nestedTable1.addCell(nesCell11);
					nestedTable1.addCell(nesCell12);
					nestedTable1.addCell(nesCell13);
					nestedTable1.addCell(nesCell14);
					nestedTable1.addCell(nesCell15);
					nestedTable1.setSpacingBefore(3f);
					nestedTable1.setSpacingAfter(3f);
					cell1.addElement(nestedTable1);

					PdfPTable nestedTable2 = new PdfPTable(5);
					nestedTable2.setWidthPercentage(100f);
					PdfPCell nesCell21 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(1).getExamStartDate())));
					PdfPCell nesCell22 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamSubject()));
					PdfPCell nesCell23 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamRoomNo()));
					PdfPCell nesCell24 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamShift()));
					PdfPCell nesCell25 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamDay()));
					nesCell21.setBorder(PdfPCell.NO_BORDER);
					nesCell22.setBorder(PdfPCell.NO_BORDER);
					nesCell23.setBorder(PdfPCell.NO_BORDER);
					nesCell24.setBorder(PdfPCell.NO_BORDER);
					nesCell25.setBorder(PdfPCell.NO_BORDER);
					nestedTable2.addCell(nesCell21);
					nestedTable2.addCell(nesCell22);
					nestedTable2.addCell(nesCell23);
					nestedTable2.addCell(nesCell24);
					nestedTable2.addCell(nesCell25);
					nestedTable2.setSpacingBefore(3f);
					nestedTable2.setSpacingAfter(3f);
					cell1.addElement(nestedTable2);
				} else if (timeTableDataBean.getClassList().size() == 3) {
					PdfPTable nestedTable1 = new PdfPTable(5);
					nestedTable1.setWidthPercentage(100f);
					PdfPCell nesCell11 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(0).getExamStartDate())));
					PdfPCell nesCell12 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamSubject()));
					PdfPCell nesCell13 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamRoomNo()));
					PdfPCell nesCell14 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamShift()));
					PdfPCell nesCell15 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamDay()));
					nesCell11.setBorder(PdfPCell.NO_BORDER);
					nesCell12.setBorder(PdfPCell.NO_BORDER);
					nesCell13.setBorder(PdfPCell.NO_BORDER);
					nesCell14.setBorder(PdfPCell.NO_BORDER);
					nesCell15.setBorder(PdfPCell.NO_BORDER);
					nestedTable1.addCell(nesCell11);
					nestedTable1.addCell(nesCell12);
					nestedTable1.addCell(nesCell13);
					nestedTable1.addCell(nesCell14);
					nestedTable1.addCell(nesCell15);
					nestedTable1.setSpacingBefore(3f);
					nestedTable1.setSpacingAfter(3f);
					cell1.addElement(nestedTable1);

					PdfPTable nestedTable2 = new PdfPTable(5);
					nestedTable2.setWidthPercentage(100f);
					PdfPCell nesCell21 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(1).getExamStartDate())));
					PdfPCell nesCell22 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamSubject()));
					PdfPCell nesCell23 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamRoomNo()));
					PdfPCell nesCell24 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamShift()));
					PdfPCell nesCell25 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamDay()));
					nesCell21.setBorder(PdfPCell.NO_BORDER);
					nesCell22.setBorder(PdfPCell.NO_BORDER);
					nesCell23.setBorder(PdfPCell.NO_BORDER);
					nesCell24.setBorder(PdfPCell.NO_BORDER);
					nesCell25.setBorder(PdfPCell.NO_BORDER);
					nestedTable2.addCell(nesCell21);
					nestedTable2.addCell(nesCell22);
					nestedTable2.addCell(nesCell23);
					nestedTable2.addCell(nesCell24);
					nestedTable2.addCell(nesCell25);
					nestedTable2.setSpacingBefore(3f);
					nestedTable2.setSpacingAfter(3f);
					cell1.addElement(nestedTable2);

					PdfPTable nestedTable3 = new PdfPTable(5);
					nestedTable3.setWidthPercentage(100f);
					PdfPCell nesCell31 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(2).getExamStartDate())));
					PdfPCell nesCell32 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamSubject()));
					PdfPCell nesCell33 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamRoomNo()));
					PdfPCell nesCell34 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamShift()));
					PdfPCell nesCell35 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamDay()));
					nesCell31.setBorder(PdfPCell.NO_BORDER);
					nesCell32.setBorder(PdfPCell.NO_BORDER);
					nesCell33.setBorder(PdfPCell.NO_BORDER);
					nesCell34.setBorder(PdfPCell.NO_BORDER);
					nesCell35.setBorder(PdfPCell.NO_BORDER);
					nestedTable3.addCell(nesCell31);
					nestedTable3.addCell(nesCell32);
					nestedTable3.addCell(nesCell33);
					nestedTable3.addCell(nesCell34);
					nestedTable3.addCell(nesCell35);
					nestedTable3.setSpacingBefore(3f);
					nestedTable3.setSpacingAfter(3f);
					cell1.addElement(nestedTable3);
				} else if (timeTableDataBean.getClassList().size() == 4) {
					PdfPTable nestedTable1 = new PdfPTable(5);
					nestedTable1.setWidthPercentage(100f);
					PdfPCell nesCell11 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(0).getExamStartDate())));
					PdfPCell nesCell12 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamSubject()));
					PdfPCell nesCell13 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamRoomNo()));
					PdfPCell nesCell14 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamShift()));
					PdfPCell nesCell15 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamDay()));
					nesCell11.setBorder(PdfPCell.NO_BORDER);
					nesCell12.setBorder(PdfPCell.NO_BORDER);
					nesCell13.setBorder(PdfPCell.NO_BORDER);
					nesCell14.setBorder(PdfPCell.NO_BORDER);
					nesCell15.setBorder(PdfPCell.NO_BORDER);
					nestedTable1.addCell(nesCell11);
					nestedTable1.addCell(nesCell12);
					nestedTable1.addCell(nesCell13);
					nestedTable1.addCell(nesCell14);
					nestedTable1.addCell(nesCell15);
					nestedTable1.setSpacingBefore(3f);
					nestedTable1.setSpacingAfter(3f);
					cell1.addElement(nestedTable1);

					PdfPTable nestedTable2 = new PdfPTable(5);
					nestedTable2.setWidthPercentage(100f);
					PdfPCell nesCell21 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(1).getExamStartDate())));
					PdfPCell nesCell22 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamSubject()));
					PdfPCell nesCell23 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamRoomNo()));
					PdfPCell nesCell24 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamShift()));
					PdfPCell nesCell25 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamDay()));
					nesCell21.setBorder(PdfPCell.NO_BORDER);
					nesCell22.setBorder(PdfPCell.NO_BORDER);
					nesCell23.setBorder(PdfPCell.NO_BORDER);
					nesCell24.setBorder(PdfPCell.NO_BORDER);
					nesCell25.setBorder(PdfPCell.NO_BORDER);
					nestedTable2.addCell(nesCell21);
					nestedTable2.addCell(nesCell22);
					nestedTable2.addCell(nesCell23);
					nestedTable2.addCell(nesCell24);
					nestedTable2.addCell(nesCell25);
					nestedTable2.setSpacingBefore(3f);
					nestedTable2.setSpacingAfter(3f);
					cell1.addElement(nestedTable2);

					PdfPTable nestedTable3 = new PdfPTable(5);
					nestedTable3.setWidthPercentage(100f);
					PdfPCell nesCell31 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(2).getExamStartDate())));
					PdfPCell nesCell32 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamSubject()));
					PdfPCell nesCell33 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamRoomNo()));
					PdfPCell nesCell34 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamShift()));
					PdfPCell nesCell35 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamDay()));
					nesCell31.setBorder(PdfPCell.NO_BORDER);
					nesCell32.setBorder(PdfPCell.NO_BORDER);
					nesCell33.setBorder(PdfPCell.NO_BORDER);
					nesCell34.setBorder(PdfPCell.NO_BORDER);
					nesCell35.setBorder(PdfPCell.NO_BORDER);
					nestedTable3.addCell(nesCell31);
					nestedTable3.addCell(nesCell32);
					nestedTable3.addCell(nesCell33);
					nestedTable3.addCell(nesCell34);
					nestedTable3.addCell(nesCell35);
					nestedTable3.setSpacingBefore(3f);
					nestedTable3.setSpacingAfter(3f);
					cell1.addElement(nestedTable3);

					PdfPTable nestedTable4 = new PdfPTable(5);
					nestedTable4.setWidthPercentage(100f);
					PdfPCell nesCell41 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(3).getExamStartDate())));
					PdfPCell nesCell42 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(3).getExamSubject()));
					PdfPCell nesCell43 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(3).getExamRoomNo()));
					PdfPCell nesCell44 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(3).getExamShift()));
					PdfPCell nesCell45 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(3).getExamDay()));
					nesCell41.setBorder(PdfPCell.NO_BORDER);
					nesCell42.setBorder(PdfPCell.NO_BORDER);
					nesCell43.setBorder(PdfPCell.NO_BORDER);
					nesCell44.setBorder(PdfPCell.NO_BORDER);
					nesCell45.setBorder(PdfPCell.NO_BORDER);
					nestedTable4.addCell(nesCell41);
					nestedTable4.addCell(nesCell42);
					nestedTable4.addCell(nesCell43);
					nestedTable4.addCell(nesCell44);
					nestedTable4.addCell(nesCell45);
					nestedTable4.setSpacingBefore(3f);
					nestedTable4.setSpacingAfter(3f);
					cell1.addElement(nestedTable4);
				} else if (timeTableDataBean.getClassList().size() == 5) {
					PdfPTable nestedTable1 = new PdfPTable(5);
					nestedTable1.setWidthPercentage(100f);
					PdfPCell nesCell11 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(0).getExamStartDate())));
					PdfPCell nesCell12 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamSubject()));
					PdfPCell nesCell13 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamRoomNo()));
					PdfPCell nesCell14 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamShift()));
					PdfPCell nesCell15 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamDay()));
					nesCell11.setBorder(PdfPCell.NO_BORDER);
					nesCell12.setBorder(PdfPCell.NO_BORDER);
					nesCell13.setBorder(PdfPCell.NO_BORDER);
					nesCell14.setBorder(PdfPCell.NO_BORDER);
					nesCell15.setBorder(PdfPCell.NO_BORDER);
					nestedTable1.addCell(nesCell11);
					nestedTable1.addCell(nesCell12);
					nestedTable1.addCell(nesCell13);
					nestedTable1.addCell(nesCell14);
					nestedTable1.addCell(nesCell15);
					nestedTable1.setSpacingBefore(3f);
					nestedTable1.setSpacingAfter(3f);
					cell1.addElement(nestedTable1);

					PdfPTable nestedTable2 = new PdfPTable(5);
					nestedTable2.setWidthPercentage(100f);
					PdfPCell nesCell21 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(1).getExamStartDate())));
					PdfPCell nesCell22 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamSubject()));
					PdfPCell nesCell23 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamRoomNo()));
					PdfPCell nesCell24 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamShift()));
					PdfPCell nesCell25 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamDay()));
					nesCell21.setBorder(PdfPCell.NO_BORDER);
					nesCell22.setBorder(PdfPCell.NO_BORDER);
					nesCell23.setBorder(PdfPCell.NO_BORDER);
					nesCell24.setBorder(PdfPCell.NO_BORDER);
					nesCell25.setBorder(PdfPCell.NO_BORDER);
					nestedTable2.addCell(nesCell21);
					nestedTable2.addCell(nesCell22);
					nestedTable2.addCell(nesCell23);
					nestedTable2.addCell(nesCell24);
					nestedTable2.addCell(nesCell25);
					nestedTable2.setSpacingBefore(3f);
					nestedTable2.setSpacingAfter(3f);
					cell1.addElement(nestedTable2);

					PdfPTable nestedTable3 = new PdfPTable(5);
					nestedTable3.setWidthPercentage(100f);
					PdfPCell nesCell31 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(2).getExamStartDate())));
					PdfPCell nesCell32 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamSubject()));
					PdfPCell nesCell33 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamRoomNo()));
					PdfPCell nesCell34 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamShift()));
					PdfPCell nesCell35 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamDay()));
					nesCell31.setBorder(PdfPCell.NO_BORDER);
					nesCell32.setBorder(PdfPCell.NO_BORDER);
					nesCell33.setBorder(PdfPCell.NO_BORDER);
					nesCell34.setBorder(PdfPCell.NO_BORDER);
					nesCell35.setBorder(PdfPCell.NO_BORDER);
					nestedTable3.addCell(nesCell31);
					nestedTable3.addCell(nesCell32);
					nestedTable3.addCell(nesCell33);
					nestedTable3.addCell(nesCell34);
					nestedTable3.addCell(nesCell35);
					nestedTable3.setSpacingBefore(3f);
					nestedTable3.setSpacingAfter(3f);
					cell1.addElement(nestedTable3);

					PdfPTable nestedTable4 = new PdfPTable(5);
					nestedTable4.setWidthPercentage(100f);
					PdfPCell nesCell41 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(3).getExamStartDate())));
					PdfPCell nesCell42 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(3).getExamSubject()));
					PdfPCell nesCell43 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(3).getExamRoomNo()));
					PdfPCell nesCell44 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(3).getExamShift()));
					PdfPCell nesCell45 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(3).getExamDay()));
					nesCell41.setBorder(PdfPCell.NO_BORDER);
					nesCell42.setBorder(PdfPCell.NO_BORDER);
					nesCell43.setBorder(PdfPCell.NO_BORDER);
					nesCell44.setBorder(PdfPCell.NO_BORDER);
					nesCell45.setBorder(PdfPCell.NO_BORDER);
					nestedTable4.addCell(nesCell41);
					nestedTable4.addCell(nesCell42);
					nestedTable4.addCell(nesCell43);
					nestedTable4.addCell(nesCell44);
					nestedTable4.addCell(nesCell45);
					nestedTable4.setSpacingBefore(3f);
					nestedTable4.setSpacingAfter(3f);
					cell1.addElement(nestedTable4);

					PdfPTable nestedTable5 = new PdfPTable(5);
					nestedTable5.setWidthPercentage(100f);
					PdfPCell nesCell51 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(4).getExamStartDate())));
					PdfPCell nesCell52 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(4).getExamSubject()));
					PdfPCell nesCell53 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(4).getExamRoomNo()));
					PdfPCell nesCell54 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(4).getExamShift()));
					PdfPCell nesCell55 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(4).getExamDay()));
					nesCell51.setBorder(PdfPCell.NO_BORDER);
					nesCell52.setBorder(PdfPCell.NO_BORDER);
					nesCell53.setBorder(PdfPCell.NO_BORDER);
					nesCell54.setBorder(PdfPCell.NO_BORDER);
					nesCell55.setBorder(PdfPCell.NO_BORDER);
					nestedTable5.addCell(nesCell51);
					nestedTable5.addCell(nesCell52);
					nestedTable5.addCell(nesCell53);
					nestedTable5.addCell(nesCell54);
					nestedTable5.addCell(nesCell55);
					nestedTable5.setSpacingBefore(3f);
					nestedTable5.setSpacingAfter(3f);
					cell1.addElement(nestedTable5);
				}

				table.addCell(cell);
				table.addCell(cell1);
				table.addCell(cell2);

				document.add(table);
				document.close();
				file.close();
				logger.debug("Done");
				status = "Success";
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		} finally {

		}
		return status;
	}
	
/*Exam TimeTable SendEmail*/
	public static String sendEmail(TimeTableDataBean timeTableDataBean) throws NoSuchProviderException {
		String status = "Fail";
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			InternetAddress[] toAddress = new InternetAddress[timeTableDataBean.getMails().size()];
			for (int i = 0; i < timeTableDataBean.getMails().size(); i++) {
				toAddress[i] = new InternetAddress(timeTableDataBean.getMails().get(i));
			}

			Properties prop = new Properties();
			prop.put("mail.smtp.host", MAIL_HOST);
			prop.put("mail.smtp.socketFactory.port", MAIL_PORT);
			prop.put("mail.smtp.socketFactory.class", MAIL_CLASS);
			prop.put("mail.smtp.starttls.enable", MAIL_STARTLES);
			prop.put("mail.smtp.auth", MAIL_AUTH);
			prop.put("mail.smtp.port", MAIL_SMTP_PORT);
			prop.put("mail.smtp.ssl.trust", MAIL_TRUST);
			String body1 = "";
			if (timeTableDataBean.getClassList().size() == 1) {
				body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>"
						+ "<center>" + "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>"
						+ "</header>" + "<h3>Dear </h3>"
						+ "<p>Welcome on-board into NRG family. Please find the your Exam Details enclosed with is mail."
						+ "All the very best in your " + timeTableDataBean.getExamTitle() + " Exams</p>"
						+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
						+ "<tr>" + "<td> " + " Exam Date" + "</td>" + "<td>" + " Subject" + "</td>" + "</tr>" + "<tr>"
						+ "<td> " + dateFormat.format(timeTableDataBean.getClassList().get(0).getExamStartDate())
						+ "</td>" + "<td>" + timeTableDataBean.getClassList().get(0).getExamSubject() + "</td>"
						+ "</tr>" + "</table>" + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>"
						+ "</center>" + "</footer>" + "</body></html>";
			} else if (timeTableDataBean.getClassList().size() == 2) {
				body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>"
						+ "<center>" + "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>"
						+ "</header>" + "<h3>Dear </h3>"
						+ "<p>Welcome on-board into NRG family. Please find the your Exam Details enclosed with is mail."
						+ "All the very best in your " + timeTableDataBean.getExamTitle() + " Exams</p>"
						+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
						+ "<tr>" + "<td> " + " Exam Date" + "</td>" + "<td>" + " Subject" + "</td>" + "</tr>" + "<tr>"
						+ "<td> " + dateFormat.format(timeTableDataBean.getClassList().get(0).getExamStartDate())
						+ "</td>" + "<td>" + timeTableDataBean.getClassList().get(0).getExamSubject() + "</td>"
						+ "</tr>" + "<tr>" + "<td> "
						+ dateFormat.format(timeTableDataBean.getClassList().get(1).getExamStartDate()) + "</td>"
						+ "<td>" + timeTableDataBean.getClassList().get(1).getExamSubject() + "</td>" + "</tr>"
						+ "</table>" + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>"
						+ "</center>" + "</footer>" + "</body></html>";
			} else if (timeTableDataBean.getClassList().size() == 3) {
				body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>"
						+ "<center>" + "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>"
						+ "</header>" + "<h3>Dear </h3>"
						+ "<p>Welcome on-board into NRG family. Please find the your Exam Details enclosed with is mail."
						+ "All the very best in your " + timeTableDataBean.getExamTitle() + " Exams</p>"
						+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
						+ "<tr>" + "<td> " + " Exam Date" + "</td>" + "<td>" + " Subject" + "</td>" + "</tr>" + "<tr>"
						+ "<td> " + dateFormat.format(timeTableDataBean.getClassList().get(0).getExamStartDate())
						+ "</td>" + "<td>" + timeTableDataBean.getClassList().get(0).getExamSubject() + "</td>"
						+ "</tr>" + "<tr>" + "<td> "
						+ dateFormat.format(timeTableDataBean.getClassList().get(1).getExamStartDate()) + "</td>"
						+ "<td>" + timeTableDataBean.getClassList().get(1).getExamSubject() + "</td>" + "</tr>" + "<tr>"
						+ "<td> "
						+ dateFormat.format(timeTableDataBean.getClassList().get(2).getExamStartDate())
						+ "</td>" + "<td>" + timeTableDataBean.getClassList().get(2).getExamSubject() + "</td>"
						+ "</tr>" + "</table>" + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>"
						+ "</center>" + "</footer>" + "</body></html>";
			} else if (timeTableDataBean.getClassList().size() == 4) {
				body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>"
						+ "<center>" + "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>"
						+ "</header>" + "<h3>Dear </h3>"
						+ "<p>Welcome on-board into NRG family. Please find the your Exam Details enclosed with is mail."
						+ "All the very best in your " + timeTableDataBean.getExamTitle() + " Exams</p>"
						+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
						+ "<tr>" + "<td> " + " Exam Date" + "</td>" + "<td>" + " Subject" + "</td>" + "</tr>" + "<tr>"
						+ "<td> " + dateFormat.format(timeTableDataBean.getClassList().get(0).getExamStartDate())
						+ "</td>" + "<td>" + timeTableDataBean.getClassList().get(0).getExamSubject() + "</td>"
						+ "</tr>" + "<tr>" + "<td> "
						+ dateFormat.format(timeTableDataBean.getClassList().get(1).getExamStartDate()) + "</td>"
						+ "<td>" + timeTableDataBean.getClassList().get(1).getExamSubject() + "</td>" + "</tr>" + "<tr>"
						+ "<td> " + dateFormat.format(timeTableDataBean.getClassList().get(2).getExamStartDate())
						+ "</td>" + "<td>" + timeTableDataBean.getClassList().get(2).getExamSubject() + "</td>"
						+ "</tr>" + "<tr>" + "<td> "
						+ dateFormat.format(timeTableDataBean.getClassList().get(3).getExamStartDate()) + "</td>"
						+ "<td>" + timeTableDataBean.getClassList().get(3).getExamSubject() + "</td>" + "</tr>"
						+ "</table>" + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>"
						+ "</center>" + "</footer>" + "</body></html>";
			} else if (timeTableDataBean.getClassList().size() == 5) {
				body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>"
						+ "<center>" + "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>"
						+ "</header>" + "<h3>Dear </h3>"
						+ "<p>Welcome on-board into NRG family. Please find the your Exam Details enclosed with is mail."
						+ "All the very best in your " + timeTableDataBean.getExamTitle() + " Exams</p>"
						+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
						+ "<tr>" + "<td> " + " Exam Date" + "</td>" + "<td>" + " Subject" + "</td>" + "</tr>" + "<tr>"
						+ "<td> " + dateFormat.format(timeTableDataBean.getClassList().get(0).getExamStartDate())
						+ "</td>" + "<td>" + timeTableDataBean.getClassList().get(0).getExamSubject() + "</td>"
						+ "</tr>" + "<tr>" + "<td> "
						+ dateFormat.format(timeTableDataBean.getClassList().get(1).getExamStartDate()) + "</td>"
						+ "<td>" + timeTableDataBean.getClassList().get(1).getExamSubject() + "</td>" + "</tr>" + "<tr>"
						+ "<td> " + dateFormat.format(timeTableDataBean.getClassList().get(2).getExamStartDate())
						+ "</td>" + "<td>" + timeTableDataBean.getClassList().get(2).getExamSubject() + "</td>"
						+ "</tr>" + "<tr>" + "<td> "
						+ dateFormat.format(timeTableDataBean.getClassList().get(3).getExamStartDate()) + "</td>"
						+ "<td>" + timeTableDataBean.getClassList().get(3).getExamSubject() + "</td>" + "</tr>" + "<tr>"
						+ "<td> " + dateFormat.format(timeTableDataBean.getClassList().get(4).getExamStartDate())
						+ "</td>" + "<td>" + timeTableDataBean.getClassList().get(4).getExamSubject() + "</td>"
						+ "</tr>" + "</table>" + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>"
						+ "</center>" + "</footer>" + "</body></html>";
			}

			Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
				}
			});

			// compose message
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
				message.addRecipients(Message.RecipientType.TO, toAddress);
				/* message.addRecipients(Message.RecipientType.CC, myCcList); */
				message.setSubject("Exam Time Table");
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);
				Transport.send(message);

				logger.info("message sent successfully");
			}

			catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
			status = "Success";

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return status;
	}
	
	/*STAFF*/
	
	public static String imageStaff(String file, String id) throws IOException{
		String type;String types;String path = "";
		logger.debug("inside image write");
		logger.debug("inside image write");
		try{

			type = file.substring(file.lastIndexOf("\\") + 1);
			types = type.substring(type.lastIndexOf(".") + 1);
			logger.debug("type "+type+" - "+types);
			path = ft.format(now) + "/" + id + "." + types;
			logger.debug("path - "+path+" file "+file);
			FileInputStream in=new FileInputStream(file);
			logger.debug("in "+in);
			File files = new File("C:\\Photos\\Staff\\" + ft.format(now));
			if (!files.exists()) {
				files.mkdirs();
				logger.debug("exist");
			} else {
				logger.debug("not exist");
			}
			logger.debug("files - "+files);
			OutputStream out = new FileOutputStream(new File(files + "\\" + id + "." + types));
			logger.debug("out  "+files + "\\" + id + "." + types);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();
		}
		catch(Exception e){
			logger.warn("Inside Exception",e);
		}
		return path;		
	}
	
	public static String generatePdfStaff(StaffDataBean staffDataBean, List<StaffDataBean> imageListPath, String personID) {
		String status = "Fail";
		try {
			if (personID != null) {
				int schoolid=staffDataBean.getSchoolID();
				System.out.println("size--"+imageListPath.size());
				if (imageListPath.size() > 0) {
					// Create Directory
					File files = new File(paths.getString("sms.staff.pdf").toString() + ft.format(now));
					if (!files.exists()) {
						files.mkdirs();
					} else {

						logger.debug("Alreday Found");
					}
					// Write PDF File
					OutputStream file = new FileOutputStream(
							new File(files + paths.getString("path_context").toString() + staffDataBean.getStaStaffID() + ".pdf"));
					staffDataBean.setStafilePath(files + paths.getString("path_context").toString() + staffDataBean.getStaStaffID() + ".pdf");
					Document document = new Document(PageSize.A4, 50, 50, 10, 50);
					PdfWriter.getInstance(document, file);
					document.open();
					String logo ="";
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						logo = paths.getString(staffDataBean.getSchoolID()+"_LOGO");
						document.add(Image.getInstance(new Object(){}.getClass().getClassLoader().getResource(logo)));
					}else{
						logo = staffDataBean.getSchoolLogo();
						document.add(Image.getInstance(logo));
					}
					document.add(Image.getInstance(new Object(){}.getClass().getClassLoader().getResource(logo)));
					document.add(new Paragraph(
							"------------------------------------------------------------------------------------------------------------------------"));

					float[] columnWidths = { 20f, 1f };
					Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 27);
					PdfPTable table = new PdfPTable(2); // .
					table.setWidthPercentage(100f);
					table.setWidths(columnWidths);

					PdfPCell cell1 = new PdfPCell(new Paragraph());
					cell1.setBorder(PdfPCell.NO_BORDER);
					PdfPCell cell2 = new PdfPCell(new Paragraph());
					cell2.setBorder(PdfPCell.NO_BORDER);
					table.setSpacingBefore(10f);
					table.setSpacingAfter(25f);

					PdfPTable nestedTable = new PdfPTable(4);
					nestedTable.setWidthPercentage(100f);
					PdfPCell nesCell1 = new PdfPCell(new Paragraph("First Name:"));
					PdfPCell nesCell2 = new PdfPCell(new Paragraph(imageListPath.get(0).getStaFirstName()));
					PdfPCell nesCell3 = new PdfPCell(new Paragraph("Last Name:"));
					PdfPCell nesCell4 = new PdfPCell(new Paragraph(imageListPath.get(0).getStaLastName()));
					nesCell1.setBorder(PdfPCell.NO_BORDER);
					nesCell2.setBorder(PdfPCell.NO_BORDER);
					nesCell3.setBorder(PdfPCell.NO_BORDER);
					nesCell4.setBorder(PdfPCell.NO_BORDER);
					nestedTable.addCell(nesCell1);
					nestedTable.addCell(nesCell2);
					nestedTable.addCell(nesCell3);
					nestedTable.addCell(nesCell4);
					nestedTable.setSpacingBefore(3f);
					nestedTable.setSpacingAfter(3f);
					cell1.addElement(nestedTable);

					PdfPTable nestedTable1 = new PdfPTable(4);
					nestedTable1.setWidthPercentage(100f);
					PdfPCell nesCell5 = new PdfPCell(new Paragraph("Staff Roll Number:"));
					PdfPCell nesCell6 = new PdfPCell(new Paragraph(imageListPath.get(0).getStaStaffID()));
					PdfPCell nesCell7 = new PdfPCell(new Paragraph("Phone number:"));
					PdfPCell nesCell8 = new PdfPCell(new Paragraph(imageListPath.get(0).getStaPhoneNo()));
					nesCell5.setBorder(PdfPCell.NO_BORDER);
					nesCell6.setBorder(PdfPCell.NO_BORDER);
					nesCell7.setBorder(PdfPCell.NO_BORDER);
					nesCell8.setBorder(PdfPCell.NO_BORDER);
					nestedTable1.addCell(nesCell5);
					nestedTable1.addCell(nesCell6);
					nestedTable1.addCell(nesCell7);
					nestedTable1.addCell(nesCell8);
					nestedTable1.setSpacingBefore(3f);
					nestedTable1.setSpacingAfter(3f);
					cell1.addElement(nestedTable1);

					PdfPTable nestedTable2 = new PdfPTable(4);
					nestedTable2.setWidthPercentage(100f);
					PdfPCell nesCell9 = new PdfPCell(new Paragraph("Email ID:"));
					PdfPCell nesCell10 = new PdfPCell(new Paragraph(imageListPath.get(0).getStaEmail()));
					PdfPCell nesCell11 = new PdfPCell(new Paragraph("Gender:"));
					PdfPCell nesCell12 = new PdfPCell(new Paragraph(imageListPath.get(0).getStaGender()));
					nesCell9.setBorder(PdfPCell.NO_BORDER);
					nesCell10.setBorder(PdfPCell.NO_BORDER);
					nesCell11.setBorder(PdfPCell.NO_BORDER);
					nesCell12.setBorder(PdfPCell.NO_BORDER);
					nestedTable2.addCell(nesCell9);
					nestedTable2.addCell(nesCell10);
					nestedTable2.addCell(nesCell11);
					nestedTable2.addCell(nesCell12);
					nestedTable2.setSpacingBefore(3f);
					nestedTable2.setSpacingAfter(3f);
					cell1.addElement(nestedTable2);
					if(imageListPath.get(0).getStaPathDate()==null && imageListPath.get(0).getStafilePath()==""){
						PdfPTable nestedTable3 = new PdfPTable(4);
						cell1.addElement(nestedTable3);
					}else{
					PdfPTable nestedTable3 = new PdfPTable(4);
					nestedTable3.setWidthPercentage(100f);
					PdfPCell nesCell13 = new PdfPCell(new Paragraph("Photo:"));
					String tempdate = ft.format(imageListPath.get(0).getStaPathDate());
					String imageLocation = paths.getString("sms.staff.photo") + tempdate + "/" + imageListPath.get(0).getStafilePath();
					logger.debug("path " + imageLocation);
					Image image = Image.getInstance(imageLocation);
					image.scaleAbsolute(100, 100);
					PdfPCell nesCell14 = new PdfPCell(image, false);
					PdfPCell nesCell15 = new PdfPCell(new Paragraph(""));
					PdfPCell nesCell16 = new PdfPCell(new Paragraph(""));
					nesCell13.setBorder(PdfPCell.NO_BORDER);
					nesCell14.setBorder(PdfPCell.NO_BORDER);
					nesCell15.setBorder(PdfPCell.NO_BORDER);
					nesCell16.setBorder(PdfPCell.NO_BORDER);
					nestedTable3.addCell(nesCell13);
					nestedTable3.addCell(nesCell14);
					nestedTable3.addCell(nesCell15);
					nestedTable3.addCell(nesCell16);
					nestedTable3.setSpacingBefore(3f);
					nestedTable3.setSpacingAfter(3f);
					cell1.addElement(nestedTable3);
					}
					table.addCell(cell1);
					table.addCell(cell2);
					document.add(table);

					document.close();
					file.close();
					logger.debug("Done");
					status = "Success";
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
		return status;
	}
	
	public static String sendEmailStaff(StaffDataBean staffDataBean) throws AddressException {
		String status = "Fail";

		try {

			String mail = staffDataBean.getStaEmail();
			String to = "" + mail;
			logger.debug("----ragulan----" + mail);
			Properties prop = new Properties();
			prop.put("mail.smtp.host", MAIL_HOST);
			prop.put("mail.smtp.socketFactory.port", MAIL_PORT);
			prop.put("mail.smtp.socketFactory.class", MAIL_CLASS);
			prop.put("mail.smtp.starttls.enable", MAIL_STARTLES);
			prop.put("mail.smtp.auth", MAIL_AUTH);
			prop.put("mail.smtp.port", MAIL_SMTP_PORT);
			prop.put("mail.smtp.ssl.trust", MAIL_TRUST);
			String body1 = "<htm><head></heade><body>"
					+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
					+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
					+ "<h3>Dear " + staffDataBean.getStaUsername() + ",</h3>"
					+ "<p>Welcome to join  our Student Monitoring System. you can find the following features  ."
					+ "you can check your  report, attendence,online payment,notice board etc."
					+ "also you can get our user name and password, from that you can check our website itself</p>"
					+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
					+ "<tr>" + "<td>User Name" + "</td>" + "<td>" + staffDataBean.getStaUsername() + "</td>" + "</tr>"
					+ "<tr>" + "<td>Password" + "</td>" + "<td>" + staffDataBean.getStaSecurePasword() + "</td>"
					+ "</tr>" + "</table>" + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>"
					+ "</center>" + "</footer>" + "</body></html>";

			Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
				}
			});

			// compose message
			try {
				/*
				 * session = Session.getInstance(prop, this);// this is the
				 * local variable not the instance one
				 */ MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("SMS Confirmation-" + staffDataBean.getStaUsername());
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");

				MimeBodyPart messageBodyPart2 = new MimeBodyPart();
				String filename = "C://Files/PDF/Staff/" + ft.format(now) + "/" + staffDataBean.getStaUsername()
						+ ".pdf";// change accordingly
				DataSource source = new FileDataSource(filename);
				messageBodyPart2.setDataHandler(new DataHandler(source));
				messageBodyPart2.setFileName(staffDataBean.getStaUsername() + ".pdf");

				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				multipart.addBodyPart(messageBodyPart2);
				message.setContent(multipart);

				Transport.send(message);
				logger.debug("--message sent successfully--");
				logger.debug("message sent successfully");
				status = "Success";

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}

		} catch (Exception e) {
			logger.warn("Inside Exception",e);
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}

	/*LIBRARIAN*/
	
	public static String imageLibrarian(String file, String id) throws IOException{
		String type;String types;String path = "";
		logger.debug("inside image write");
		logger.debug("inside image write");
		try{

			type = file.substring(file.lastIndexOf("\\") + 1);
			types = type.substring(type.lastIndexOf(".") + 1);
			logger.debug("type "+type+" - "+types);
			path = ft.format(now) + "/" + id + "." + types;
			logger.debug("path - "+path+" file "+file);
			FileInputStream in=new FileInputStream(file);
			logger.debug("in "+in);
			File files = new File("C:\\Photos\\Librarian\\" + ft.format(now));
			if (!files.exists()) {
				files.mkdirs();
				logger.debug("exist");
			} else {
				logger.debug("not exist");
			}
			logger.debug("files - "+files);
			OutputStream out = new FileOutputStream(new File(files + "\\" + id + "." + types));
			logger.debug("out  "+files + "\\" + id + "." + types);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();
		}
		catch(Exception e){
			logger.warn("Inside Exception",e);
		}
		return path;		
	}
	
	public static String generatePdfLibrarian(LibrarianDataBean librarianDataBean, List<LibrarianDataBean> imageListPath, String personID) {
		String status = "Fail";
		try {
			if (personID != null) {
				if (imageListPath.size() > 0) {
					// Create Directory
					File files = new File(paths.getString("sms.librarian.pdf") + ft.format(now));
					if (!files.exists()) {
						files.mkdirs();
					} else {
						logger.debug("Alreday Found");
					}
					// Write PDF File
					OutputStream file = new FileOutputStream(
							new File(files + paths.getString("path_context").toString() + librarianDataBean.getLibUsername() + ".pdf"));
					librarianDataBean.setLibfilePath(files + paths.getString("path_context").toString() + librarianDataBean.getLibUsername() + ".pdf");
					Document document = new Document(PageSize.A4, 50, 50, 10, 50);
					PdfWriter.getInstance(document, file);
					document.open();
					String logo = paths.getString(librarianDataBean.getSchoolID()+"_LOGO");
					document.add(Image.getInstance(new Object(){}.getClass().getClassLoader().getResource(logo)));
					document.add(new Paragraph(
							"------------------------------------------------------------------------------------------------------------------------"));

					float[] columnWidths = { 20f, 1f };
					Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 27);
					PdfPTable table = new PdfPTable(2); // .
					table.setWidthPercentage(100f);
					table.setWidths(columnWidths);

					PdfPCell cell1 = new PdfPCell(new Paragraph());
					cell1.setBorder(PdfPCell.NO_BORDER);
					PdfPCell cell2 = new PdfPCell(new Paragraph());
					cell2.setBorder(PdfPCell.NO_BORDER);
					table.setSpacingBefore(10f);
					table.setSpacingAfter(25f);

					PdfPTable nestedTable = new PdfPTable(4);
					nestedTable.setWidthPercentage(100f);
					PdfPCell nesCell1 = new PdfPCell(new Paragraph("First Name:"));
					PdfPCell nesCell2 = new PdfPCell(new Paragraph(imageListPath.get(0).getLibFirstName()));
					PdfPCell nesCell3 = new PdfPCell(new Paragraph("Last Name:"));
					PdfPCell nesCell4 = new PdfPCell(new Paragraph(imageListPath.get(0).getLibLastName()));
					nesCell1.setBorder(PdfPCell.NO_BORDER);
					nesCell2.setBorder(PdfPCell.NO_BORDER);
					nesCell3.setBorder(PdfPCell.NO_BORDER);
					nesCell4.setBorder(PdfPCell.NO_BORDER);
					nestedTable.addCell(nesCell1);
					nestedTable.addCell(nesCell2);
					nestedTable.addCell(nesCell3);
					nestedTable.addCell(nesCell4);
					nestedTable.setSpacingBefore(3f);
					nestedTable.setSpacingAfter(3f);
					cell1.addElement(nestedTable);

					PdfPTable nestedTable1 = new PdfPTable(4);
					nestedTable1.setWidthPercentage(100f);
					PdfPCell nesCell5 = new PdfPCell(new Paragraph("Librarian ID:"));
					PdfPCell nesCell6 = new PdfPCell(new Paragraph(imageListPath.get(0).getLibID()));
					PdfPCell nesCell7 = new PdfPCell(new Paragraph("Email:"));
					PdfPCell nesCell8 = new PdfPCell(new Paragraph(imageListPath.get(0).getLibEmail()));
					nesCell5.setBorder(PdfPCell.NO_BORDER);
					nesCell6.setBorder(PdfPCell.NO_BORDER);
					nesCell7.setBorder(PdfPCell.NO_BORDER);
					nesCell8.setBorder(PdfPCell.NO_BORDER);
					nestedTable1.addCell(nesCell5);
					nestedTable1.addCell(nesCell6);
					nestedTable1.addCell(nesCell7);
					nestedTable1.addCell(nesCell8);
					nestedTable1.setSpacingBefore(3f);
					nestedTable1.setSpacingAfter(3f);
					cell1.addElement(nestedTable1);

					PdfPTable nestedTable2 = new PdfPTable(4);
					nestedTable2.setWidthPercentage(100f);
					PdfPCell nesCell9 = new PdfPCell(new Paragraph("Phone Number:"));
					PdfPCell nesCell10 = new PdfPCell(new Paragraph(imageListPath.get(0).getLibPhoneNo()));
					PdfPCell nesCell11 = new PdfPCell(new Paragraph("Gender:"));
					PdfPCell nesCell12 = new PdfPCell(new Paragraph(imageListPath.get(0).getLibGender()));
					nesCell9.setBorder(PdfPCell.NO_BORDER);
					nesCell10.setBorder(PdfPCell.NO_BORDER);
					nesCell11.setBorder(PdfPCell.NO_BORDER);
					nesCell12.setBorder(PdfPCell.NO_BORDER);
					nestedTable2.addCell(nesCell9);
					nestedTable2.addCell(nesCell10);
					nestedTable2.addCell(nesCell11);
					nestedTable2.addCell(nesCell12);
					nestedTable2.setSpacingBefore(3f);
					nestedTable2.setSpacingAfter(3f);
					cell1.addElement(nestedTable2);

					/*PdfPTable nestedTable3 = new PdfPTable(2);
					nestedTable3.setWidthPercentage(100f);
					PdfPCell nesCell13 = new PdfPCell(new Paragraph("Photo:"));
					String tempdate = ft.format(imageListPath.get(0).getPathDate());
					String imageLocation = "C://Photos/Librarian/" + tempdate + "/" + imageListPath.get(0).getPath();
					Image image = Image.getInstance(imageLocation);
					logger.debug(image);
					image.scaleAbsolute(100, 100);
					PdfPCell nesCell14 = new PdfPCell(image, false);
					nesCell13.setBorder(PdfPCell.NO_BORDER);
					nesCell14.setBorder(PdfPCell.NO_BORDER);
					nestedTable3.addCell(nesCell13);
					nestedTable3.addCell(nesCell14);
					nestedTable3.setSpacingBefore(3f);
					nestedTable3.setSpacingAfter(3f);
					cell1.addElement(nestedTable3);*/

					table.addCell(cell1);
					table.addCell(cell2);
					document.add(table);
					document.close();
					file.close();
					logger.debug("Done");
					status = "Success";
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}
	
	public static String sendEmailLibrarian(LibrarianDataBean librarianDataBean)
			throws AddressException {
		String status = "Fail";

		String mail = librarianDataBean.getLibEmail();
		String to = "" + mail;
		Properties prop = new Properties();
		prop.put("mail.smtp.host", MAIL_HOST);
		prop.put("mail.smtp.socketFactory.port", MAIL_PORT);
		prop.put("mail.smtp.socketFactory.class", MAIL_CLASS);
		prop.put("mail.smtp.starttls.enable", MAIL_STARTLES);
		prop.put("mail.smtp.auth", MAIL_AUTH);
		prop.put("mail.smtp.port", MAIL_SMTP_PORT);
		prop.put("mail.smtp.ssl.trust", MAIL_TRUST);

		String body1 = "<htm><head></heade><body>"
				+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
				+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
				+ "<h3>Dear " + librarianDataBean.getLibUsername() + ",</h3>"
				+ "<p>Welcome on-board into NRG family. Please find the your Username and Password enclosed with is mail."
				+ "All the very best in your new assignment</p>"
				+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
				+ "<tr>" + "<td>User Name" + "</td>" + "<td>" + librarianDataBean.getLibUsername() + "</td>" + "</tr>"
				+ "<tr>" + "<td>Password" + "</td>" + "<td>" + librarianDataBean.getLibSecurePassword() + "</td>"
				+ "</tr>" + "</table>" + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>"
				+ "</center>" + "</footer>" + "</body></html>";

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
			}
		});
		// compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			/* message.addRecipients(Message.RecipientType.CC, myCcList); */
			message.setSubject("SMS Confirmation-" + librarianDataBean.getLibUsername());
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setContent(body1, "text/html");

			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			String filename = "C://Files/PDF/Librarian/" + ft.format(now) + "/" + librarianDataBean.getLibUsername()
					+ ".pdf";// change accordingly
			DataSource source = new FileDataSource(filename);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(librarianDataBean.getLibUsername() + ".pdf");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);
			message.setContent(multipart);

			Transport.send(message);

			logger.debug("message sent successfully");
			status = "Success";

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return status;
	}
	
	public static String generatePdfClass(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
	    String status = "fail";
	    try {
	     if (personID != null) {

	      File files = new File(paths.getString("sms.classtimetable.pdf") + ft.format(now));
	      if (!files.exists()) {
	       files.mkdirs();
	      } else {
	       logger.debug("Alreday Found");
	      }
	      // Write PDF File
	      OutputStream file = new FileOutputStream(
	        new File(files + paths.getString("path_context").toString() + classTimeTableDataBean.getClassname().substring(0, 8) + "-"
	          + classTimeTableDataBean.getClassname().substring(9, 10) + "-"
	          + classTimeTableDataBean.getDay() + ".pdf"));
	      classTimeTableDataBean.setFilepath(files + paths.getString("path_context").toString() + classTimeTableDataBean.getClassname().substring(0, 8) + "-"
	          + classTimeTableDataBean.getClassname().substring(9, 10) + "-"
	          + classTimeTableDataBean.getDay() + ".pdf");
	      Document document = new Document(PageSize.A4, 50, 50, 10, 50);
	      PdfWriter.getInstance(document, file);
	      document.open();
	      String logo = paths.getString(classTimeTableDataBean.getSchoolID()+"_LOGO");
	   document.add(Image.getInstance(new Object(){}.getClass().getClassLoader().getResource(logo)));
	      float[] columnWidths = { 20f, 1f };
	      Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 16);
	      PdfPTable table = new PdfPTable(2); //     .
	      table.setWidthPercentage(100f);
	      table.setWidths(columnWidths);
	      PdfPCell cell = new PdfPCell();
	      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	      cell.setColspan(2);
	      cell.setBorder(PdfPCell.NO_BORDER);
	      PdfPCell cell1 = new PdfPCell(new Paragraph());
	      cell1.setBorder(PdfPCell.NO_BORDER);
	      PdfPCell cell2 = new PdfPCell(new Paragraph());
	      cell2.setBorder(PdfPCell.NO_BORDER);
	      PdfPCell cell3 = new PdfPCell(new Paragraph());
	      cell3.setBorder(PdfPCell.NO_BORDER);
	      PdfPCell cell4 = new PdfPCell(new Paragraph());
	      cell4.setBorder(PdfPCell.NO_BORDER);
	      PdfPCell cell5 = new PdfPCell(new Paragraph());
	      cell5.setBorder(PdfPCell.NO_BORDER);
	      table.setSpacingBefore(10f);
	      table.setSpacingAfter(25f);

	      PdfPTable headTable = new PdfPTable(2);
	      headTable.setWidthPercentage(100f);
	      PdfPCell headCell = new PdfPCell(new Paragraph("Period ", font3));
	      PdfPCell headCell1 = new PdfPCell(new Paragraph("Subject ", font3));
	      headCell.setBorder(PdfPCell.NO_BORDER);
	      headCell1.setBorder(PdfPCell.NO_BORDER);
	      headTable.addCell(headCell);
	      headTable.addCell(headCell1);
	      headTable.setSpacingBefore(3f);
	      headTable.setSpacingAfter(3f);
	      cell1.addElement(headTable);
	  
	      for(int i=0;i<classTimeTableDataBean.getClasstimeList().size();i++){
	      PdfPTable nestedTable = new PdfPTable(2);
	      nestedTable.setWidthPercentage(100f);
	      PdfPCell nesCell1 = new PdfPCell(new Paragraph(""+(i+1),font3));
	      PdfPCell nesCell2 = new PdfPCell(
	        new Paragraph("" + classTimeTableDataBean.getClasstimeList().get(i).getSubject(), font3));
	      nesCell1.setBorder(PdfPCell.NO_BORDER);
	      nesCell2.setBorder(PdfPCell.NO_BORDER);
	      nestedTable.addCell(nesCell1);
	      nestedTable.addCell(nesCell2);
	      nestedTable.setSpacingBefore(3f);
	      nestedTable.setSpacingAfter(3f);
	      cell1.addElement(nestedTable);
	      }
	     /* PdfPTable nestedTable1 = new PdfPTable(2);
	      nestedTable1.setWidthPercentage(100f);
	      PdfPCell nesCell11 = new PdfPCell(new Paragraph("2", font3));
	      PdfPCell nesCell12 = new PdfPCell(
	        new Paragraph("" + classTimeTableDataBean.getClasstimeList().get(1).getSubject(), font3));
	      nesCell11.setBorder(PdfPCell.NO_BORDER);
	      nesCell12.setBorder(PdfPCell.NO_BORDER);
	      nestedTable1.addCell(nesCell11);
	      nestedTable1.addCell(nesCell12);
	      nestedTable1.setSpacingBefore(3f);
	      nestedTable1.setSpacingAfter(3f);
	      cell1.addElement(nestedTable1);

	      PdfPTable nestedTable2 = new PdfPTable(2);
	      nestedTable2.setWidthPercentage(100f);
	      PdfPCell nesCell21 = new PdfPCell(new Paragraph("3", font3));
	      PdfPCell nesCell22 = new PdfPCell(
	        new Paragraph("" + classTimeTableDataBean.getClasstimeList().get(2).getSubject(), font3));
	      nesCell21.setBorder(PdfPCell.NO_BORDER);
	      nesCell22.setBorder(PdfPCell.NO_BORDER);
	      nestedTable2.addCell(nesCell21);
	      nestedTable2.addCell(nesCell22);
	      nestedTable2.setSpacingBefore(3f);
	      nestedTable2.setSpacingAfter(3f);
	      cell1.addElement(nestedTable2);

	      PdfPTable nestedTable3 = new PdfPTable(2);
	      nestedTable3.setWidthPercentage(100f);
	      PdfPCell nesCell31 = new PdfPCell(new Paragraph("4", font3));
	      PdfPCell nesCell32 = new PdfPCell(
	        new Paragraph("" + classTimeTableDataBean.getClasstimeList().get(3).getSubject(), font3));
	      nesCell31.setBorder(PdfPCell.NO_BORDER);
	      nesCell32.setBorder(PdfPCell.NO_BORDER);
	      nestedTable3.addCell(nesCell31);
	      nestedTable3.addCell(nesCell32);
	      nestedTable3.setSpacingBefore(3f);
	      nestedTable3.setSpacingAfter(3f);
	      cell1.addElement(nestedTable3);

	      PdfPTable nestedTable4 = new PdfPTable(2);
	      nestedTable4.setWidthPercentage(100f);
	      PdfPCell nesCell41 = new PdfPCell(new Paragraph("5", font3));
	      PdfPCell nesCell42 = new PdfPCell(
	        new Paragraph("" + classTimeTableDataBean.getClasstimeList().get(4).getSubject(), font3));
	      nesCell41.setBorder(PdfPCell.NO_BORDER);
	      nesCell42.setBorder(PdfPCell.NO_BORDER);
	      nestedTable4.addCell(nesCell41);
	      nestedTable4.addCell(nesCell42);
	      nestedTable4.setSpacingBefore(3f);
	      nestedTable4.setSpacingAfter(3f);
	      cell1.addElement(nestedTable4);

	      PdfPTable nestedTable5 = new PdfPTable(2);
	      nestedTable5.setWidthPercentage(100f);
	      PdfPCell nesCell51 = new PdfPCell(new Paragraph("6", font3));
	      PdfPCell nesCell52 = new PdfPCell(
	        new Paragraph("" + classTimeTableDataBean.getClasstimeList().get(5).getSubject(), font3));
	      nesCell51.setBorder(PdfPCell.NO_BORDER);
	      nesCell52.setBorder(PdfPCell.NO_BORDER);
	      nestedTable5.addCell(nesCell51);
	      nestedTable5.addCell(nesCell52);
	      nestedTable5.setSpacingBefore(3f);
	      nestedTable5.setSpacingAfter(3f);
	      cell1.addElement(nestedTable5);

	      PdfPTable nestedTable6 = new PdfPTable(2);
	      nestedTable6.setWidthPercentage(100f);
	      PdfPCell nesCell61 = new PdfPCell(new Paragraph("7", font3));
	      PdfPCell nesCell62 = new PdfPCell(
	        new Paragraph("" + classTimeTableDataBean.getClasstimeList().get(6).getSubject(), font3));
	      nesCell61.setBorder(PdfPCell.NO_BORDER);
	      nesCell62.setBorder(PdfPCell.NO_BORDER);
	      nestedTable6.addCell(nesCell61);
	      nestedTable6.addCell(nesCell62);
	      nestedTable6.setSpacingBefore(3f);
	      nestedTable6.setSpacingAfter(3f);
	      cell1.addElement(nestedTable6);

	      PdfPTable nestedTable7 = new PdfPTable(2);
	      nestedTable7.setWidthPercentage(100f);
	      PdfPCell nesCell71 = new PdfPCell(new Paragraph("8", font3));
	      PdfPCell nesCell72 = new PdfPCell(
	        new Paragraph("" + classTimeTableDataBean.getClasstimeList().get(7).getSubject(), font3));
	      nesCell71.setBorder(PdfPCell.NO_BORDER);
	      nesCell72.setBorder(PdfPCell.NO_BORDER);
	      nestedTable7.addCell(nesCell71);
	      nestedTable7.addCell(nesCell72);
	      nestedTable1.setSpacingBefore(3f);
	      nestedTable7.setSpacingAfter(3f);
	      cell1.addElement(nestedTable7);*/

	      table.addCell(cell);
	      table.addCell(cell1);
	      table.addCell(cell2);

	      document.add(table);
	      document.close();
	      file.close();
	      logger.debug("Done");
	      status = "Success";
	     }
	    } catch (Exception e) {
	     logger.warn("Exception -->"+e.getMessage());
	     logger.warn("Inside Exception",e);
	     e.printStackTrace();
	    } finally {

	    }
	    return status;
	   }
		 
		/* Class TimeTable Email*/
		 public static String sendEmailClass(ClassTimeTableDataBean classTimeTableDataBean) throws NoSuchProviderException {
		  String status = "Fail";
		  try {
		   InternetAddress[] toAddress = new InternetAddress[classTimeTableDataBean.getMails().size()];
		   for (int i = 0; i < classTimeTableDataBean.getMails().size(); i++) {
		    toAddress[i] = new InternetAddress(classTimeTableDataBean.getMails().get(i));
		   }

		   Properties prop = new Properties();
			prop.put("mail.smtp.host", MAIL_HOST);
			prop.put("mail.smtp.socketFactory.port", MAIL_PORT);
			prop.put("mail.smtp.socketFactory.class", MAIL_CLASS);
			prop.put("mail.smtp.starttls.enable", MAIL_STARTLES);
			prop.put("mail.smtp.auth", MAIL_AUTH);
			prop.put("mail.smtp.port", MAIL_SMTP_PORT);
			prop.put("mail.smtp.ssl.trust", MAIL_TRUST);
		   String body1 = "<htm><head></heade><body>"
					+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
					+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
					+ "<h3>Dear </h3>"
		     + "<p>Welcome on-board into NRG family. Please find the your class time table for "
		     + classTimeTableDataBean.getDay() + " , the details enclosed with is mail." + "</p>"
		     + "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
		     + "<tr>" + "<td> " + " Period " + "</td>" + "<td>" + " Subject" + "</td>" + "</tr>" + "<tr>"
		     + "<td> " + 1 + "</td>" + "<td>" + classTimeTableDataBean.getClasstimeList().get(0).getSubject()
		     + "</td>" + "</tr>" + "<tr>" + "<td> " + 2 + "</td>" + "<td>"
		     + classTimeTableDataBean.getClasstimeList().get(1).getSubject() + "</td>" + "</tr>" + "<tr>"
		     + "<td> " + 3 + "</td>" + "<td>" + classTimeTableDataBean.getClasstimeList().get(2).getSubject()
		     + "</td>" + "</tr>" + "<tr>" + "<td> " + 4 + "</td>" + "<td>"
		     + classTimeTableDataBean.getClasstimeList().get(3).getSubject() + "</td>" + "</tr>" + "<tr>"
		     + "<td> " + 5 + "</td>" + "<td>" + classTimeTableDataBean.getClasstimeList().get(4).getSubject()
		     + "</td>" + "</tr>" + "<tr>" + "<td> " + 6 + "</td>" + "<td>"
		     + classTimeTableDataBean.getClasstimeList().get(5).getSubject() + "</td>" + "</tr>" + "<tr>"
		     + "<td> " + 7 + "</td>" + "<td>" + classTimeTableDataBean.getClasstimeList().get(6).getSubject()
		     + "</td>" + "</tr>" + "<tr>" + "<td> " + 8 + "</td>" + "<td>"
		     + classTimeTableDataBean.getClasstimeList().get(7).getSubject() + "</td>" + "</tr>" + "</table>"
		     + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>"
		     + "</footer>" + "</body></html>";

		   Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
				}
			});

			// compose message
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
				message.addRecipients(Message.RecipientType.TO, toAddress);
				/* message.addRecipients(Message.RecipientType.CC, myCcList); */
				message.setSubject("Class Time Table");
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);
				Transport.send(message);

				logger.info("message sent successfully");
			}

			catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
			status = "Success";

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		} finally {

		}
		return status;
	}

		public static String sendEmailExtraClass(
				ClassTimeTableDataBean classTimeTableDataBean) {
			String status = "Fail";
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			try {
				InternetAddress[] toAddress = new InternetAddress[classTimeTableDataBean.getMails().size()];
				for (int i = 0; i < classTimeTableDataBean.getMails().size(); i++) {
					toAddress[i] = new InternetAddress(classTimeTableDataBean.getMails().get(i));
				}

				Properties prop = new Properties();
				prop.put("mail.smtp.host", MAIL_HOST);
				prop.put("mail.smtp.socketFactory.port", MAIL_PORT);
				prop.put("mail.smtp.socketFactory.class", MAIL_CLASS);
				prop.put("mail.smtp.starttls.enable", MAIL_STARTLES);
				prop.put("mail.smtp.auth", MAIL_AUTH);
				prop.put("mail.smtp.port", MAIL_SMTP_PORT);
				prop.put("mail.smtp.ssl.trust", MAIL_TRUST);
				String body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
						+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
						+ "<h3>Dear </h3>"
						+ "<p>Welcome on-board into NRG family. Please find the you have extra class for "
						+ classTimeTableDataBean.getSubject() + " , at " + classTimeTableDataBean.getTimeStart() + " to "
						+ classTimeTableDataBean.getTimeEnd() + " on " + format.format(classTimeTableDataBean.getDate())
						+ " ." + "</p>" + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>"
						+ "</center>" + "</footer>" + "</body></html>";
				Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
					}
				});

				// compose message
				try {
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
					message.addRecipients(Message.RecipientType.TO, toAddress);
					/* message.addRecipients(Message.RecipientType.CC, myCcList); */
					message.setSubject("Extra Class Time ");
					BodyPart messageBodyPart1 = new MimeBodyPart();
					messageBodyPart1.setContent(body1, "text/html");
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart1);
					message.setContent(multipart);
					Transport.send(message);

					logger.info("message sent successfully");
				}

				catch (Exception e) {
					logger.warn("Exception -->"+e.getMessage());
				}
				status = "Success";

			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
				logger.warn("Inside Exception",e);
			} finally {

			}
			return status;
		}
		public static String sendEmailExamClassUpdate(TimeTableDataBean timeTableDataBean) throws NoSuchProviderException {
			String status = "Fail";
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				InternetAddress[] toAddress = new InternetAddress[timeTableDataBean.getMails().size()];
				for (int i = 0; i < timeTableDataBean.getMails().size(); i++) {
					toAddress[i] = new InternetAddress(timeTableDataBean.getMails().get(i));
				}

				Properties prop = new Properties();
				prop.put("mail.smtp.host", MAIL_HOST);
				prop.put("mail.smtp.socketFactory.port", MAIL_PORT);
				prop.put("mail.smtp.socketFactory.class", MAIL_CLASS);
				prop.put("mail.smtp.starttls.enable", MAIL_STARTLES);
				prop.put("mail.smtp.auth", MAIL_AUTH);
				prop.put("mail.smtp.port", MAIL_SMTP_PORT);
				prop.put("mail.smtp.ssl.trust", MAIL_TRUST);
				String body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
						+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
						+ "<h3>Dear </h3>"
						+ "<p>Welcome on-board into NRG family. Please find the your Exams are Postponed and the Details enclosed with is mail."
						+ "All the very best in your " + timeTableDataBean.getExamTitle() + " Exams</p>"
						+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
						+ "<tr>" + "<td> " + " Exam Date" + "</td>" + "<td>" + " Subject" + "</td>" + "<td> " + " Room No"
						+ "</td>" + "</tr>" + "<tr>" + "<td> " + dateFormat.format(timeTableDataBean.getExamStartDate())
						+ "</td>" + "<td>" + timeTableDataBean.getExamSubject() + "</td>" + "<td>"
						+ timeTableDataBean.getExamRoomNo() + "</td>" + "</tr>" + "</table>" + "<footer>" + "<center>"
						+ "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>" + "</body></html>";
				Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
					}
				});

				// compose message
				try {
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
					message.addRecipients(Message.RecipientType.TO, toAddress);
					/* message.addRecipients(Message.RecipientType.CC, myCcList); */
					message.setSubject("Exam Time Table Update");
					BodyPart messageBodyPart1 = new MimeBodyPart();
					messageBodyPart1.setContent(body1, "text/html");
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart1);
					message.setContent(multipart);
					Transport.send(message);

					logger.info("message sent successfully");
				}

				catch (Exception e) {
					logger.warn("Exception -->"+e.getMessage());
				}
				status = "Success";

			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			} finally {

			}
			return status;
		}
		
		public static String sendEmailHomeWork(HomeworkDatabean homeworkDatabean) {
			String status = "Fail";
			try {
				if (homeworkDatabean.getMails() == null) {
					logger.debug("Inside Null");
				} else {
					InternetAddress[] toAddress = new InternetAddress[homeworkDatabean.getMails().size()];
					for (int i = 0; i < homeworkDatabean.getMails().size(); i++) {
						toAddress[i] = new InternetAddress(homeworkDatabean.getMails().get(i));
					}

					Properties prop = new Properties();
					prop.put("mail.smtp.host", MAIL_HOST);
					prop.put("mail.smtp.socketFactory.port", MAIL_PORT);
					prop.put("mail.smtp.socketFactory.class", MAIL_CLASS);
					prop.put("mail.smtp.starttls.enable", MAIL_STARTLES);
					prop.put("mail.smtp.auth", MAIL_AUTH);
					prop.put("mail.smtp.port", MAIL_SMTP_PORT);
					prop.put("mail.smtp.ssl.trust", MAIL_TRUST);
					String body1 = "<htm><head></heade><body>"
							+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>"
							+ "<center>" + "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>"
							+ "</header>" + "<h3>Dear </h3>"
							+ "<p>Welcome on-board into NRG family. Please find the your home Work for "
							+ homeworkDatabean.getSubject() + " is " + homeworkDatabean.getHomework() + " ." + "</p>"
							+ "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>"
							+ "</footer>" + "</body></html>";
					Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
						}
					});

					// compose message
					try {
						MimeMessage message = new MimeMessage(session);
						message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
						message.addRecipients(Message.RecipientType.TO, toAddress);
						message.setSubject("Home Work");
						BodyPart messageBodyPart1 = new MimeBodyPart();
						messageBodyPart1.setContent(body1, "text/html");
						Multipart multipart = new MimeMultipart();
						multipart.addBodyPart(messageBodyPart1);
						message.setContent(multipart);
						Transport.send(message);
						logger.info("message sent successfully");
					} catch (Exception e) {
						logger.warn("Exception -->"+e.getMessage());
					}
					status = "Success";
				}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
				logger.warn("Inside Exception",e);
			} finally {

			}
			return status;
		}
		
		public static String sendNoticeEmail(NoticeBoardDataBean noticeBoardDataBean) {
			StringBuilder status = new StringBuilder("Fail");

			try {
				InternetAddress[] toAddress = new InternetAddress[noticeBoardDataBean.getEmailList().size()];
				for (int i = 0; i < noticeBoardDataBean.getEmailList().size(); i++) {
					toAddress[i] = new InternetAddress(noticeBoardDataBean.getEmailList().get(i));
					logger.debug("mail id -- " + noticeBoardDataBean.getEmailList().get(i));
				}
				Properties prop = new Properties();
				prop.put("mail.smtp.host", MAIL_HOST);
				prop.put("mail.smtp.socketFactory.port", MAIL_PORT);
				prop.put("mail.smtp.socketFactory.class", MAIL_CLASS);
				prop.put("mail.smtp.starttls.enable", MAIL_STARTLES);
				prop.put("mail.smtp.auth", MAIL_AUTH);
				prop.put("mail.smtp.port", MAIL_SMTP_PORT);
				prop.put("mail.smtp.ssl.trust", MAIL_TRUST);

				String body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
						+ "<h1> Student Monitoring System Notice Board</h1><br></br></center>" + "</center>" + "</header>"
						+ "<h3>Dear " + noticeBoardDataBean.getNoticeFollower() + ",</h3>" 
						+noticeBoardDataBean.getNoticeID()
						+"<footer>" + "<center>"
						+ "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>" + "</body></html>";

				Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
					}
				});
				// compose message
				try {
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
					message.setRecipients(Message.RecipientType.TO, toAddress);
					/*
					 * message.addRecipients(Message.RecipientType.CC, myCcList);
					 */ message.setSubject(noticeBoardDataBean.getNoticeSubject());
					BodyPart messageBodyPart1 = new MimeBodyPart();
					messageBodyPart1.setContent(body1, "text/html");
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart1);
					message.setContent(multipart);

					Transport.send(message);

					logger.debug("message sent successfully");
					status = new StringBuilder("Success");

				} catch (MessagingException e) {
					logger.warn("Inside Exception",e);
					throw new RuntimeException(e);
				}

			} catch (AddressException e) {
				logger.warn("Exception -->"+e.getMessage());
				logger.warn("Inside Exception",e);
			}
			return status.toString();
		}
		
		public static String sendEmailAttendance(AttendanceDataBean attendanceDataBean) {
			String status = "Fail";
			String period = "";
			try {
				InternetAddress[] toAddress = new InternetAddress[attendanceDataBean.getMails().size()];
				for (int i = 0; i < attendanceDataBean.getMails().size(); i++) {
					toAddress[i] = new InternetAddress(attendanceDataBean.getMails().get(i));
				}
				Properties prop = new Properties();
				prop.put("mail.smtp.host", MAIL_HOST);
				prop.put("mail.smtp.socketFactory.port", MAIL_PORT);
				prop.put("mail.smtp.socketFactory.class", MAIL_CLASS);
				prop.put("mail.smtp.starttls.enable", MAIL_STARTLES);
				prop.put("mail.smtp.auth", MAIL_AUTH);
				prop.put("mail.smtp.port", MAIL_SMTP_PORT);
				prop.put("mail.smtp.ssl.trust", MAIL_TRUST);
				if (attendanceDataBean.getPeriod().equals("1"))
					period = "st Period";
				else if (attendanceDataBean.getPeriod().equals("2"))
					period = "nd Period";
				else if (attendanceDataBean.getPeriod().equals("3"))
					period = "rd Period";
				else
					period = "th Period";
				String body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
						+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
						+ "<h3>Dear " + attendanceDataBean.getStudentName() + ",</h3>"
						+ "<p>Welcome on-board into NRG family.The Student " + attendanceDataBean.getStudentName()
						+ " is Absent Today " + attendanceDataBean.getPeriod() + period + ".</p>"

						+ "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>"
						+ "</footer>" + "</body></html>";

				Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
					}
				});

				// compose message
				try {
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
					message.addRecipients(Message.RecipientType.TO, toAddress);
					message.setSubject("Take Attendance");
					BodyPart messageBodyPart1 = new MimeBodyPart();
					messageBodyPart1.setContent(body1, "text/html");
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart1);
					message.setContent(multipart);
					Transport.send(message);

					logger.info("message sent successfully");
					status = "Success";

				} catch (Exception e) {
					logger.warn("Exception -->"+e.getMessage());
				}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
			return status;
		}
		
		public static String sendEmailAttendanceUpdate(AttendanceDataBean attendanceDataBean) {
			String status = "Fail";
			String period = "";
			try {
				InternetAddress[] toAddress = new InternetAddress[attendanceDataBean.getMails().size()];
				for (int i = 0; i < attendanceDataBean.getMails().size(); i++) {
					toAddress[i] = new InternetAddress(attendanceDataBean.getMails().get(i));
				}

				Properties prop = new Properties();
				prop.put("mail.smtp.host", MAIL_HOST);
				prop.put("mail.smtp.socketFactory.port", MAIL_PORT);
				prop.put("mail.smtp.socketFactory.class", MAIL_CLASS);
				prop.put("mail.smtp.starttls.enable", MAIL_STARTLES);
				prop.put("mail.smtp.auth", MAIL_AUTH);
				prop.put("mail.smtp.port", MAIL_SMTP_PORT);
				prop.put("mail.smtp.ssl.trust", MAIL_TRUST);
				if (attendanceDataBean.getPeriod().substring(7, 8).equals("1"))
					period = "st Period";
				else if (attendanceDataBean.getPeriod().substring(7, 8).equals("2"))
					period = "nd Period";
				else if (attendanceDataBean.getPeriod().substring(7, 8).equals("3"))
					period = "rd Period";
				else
					period = "th Period";
				String body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
						+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
						+ "<h3>Dear " + attendanceDataBean.getStudentName() + ",</h3>"
						+ "<p>Welcome on-board into NRG family.The Student " + attendanceDataBean.getStudentName()
						+ " is Absent Today " + attendanceDataBean.getPeriod().substring(7, 8) + period + ".</p>"
						+ "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>"
						+ "</footer>" + "</body></html>";

				Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
					}
				});

				// compose message
				try {
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
					message.addRecipients(Message.RecipientType.TO, toAddress);
					message.setSubject("Change the Attendance ");
					BodyPart messageBodyPart1 = new MimeBodyPart();
					messageBodyPart1.setContent(body1, "text/html");
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart1);
					message.setContent(multipart);
					Transport.send(message);

					logger.info("message sent successfully");
					status = "Success";

				} catch (Exception e) {
					logger.warn("Exception -->"+e.getMessage());
				}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
			return status;
		}

		public static String sendEmailAttendanceUpdate1(AttendanceDataBean attendanceDataBean) {
			String status = "Fail";
			String period = "";
			try {
				InternetAddress[] toAddress = new InternetAddress[attendanceDataBean.getMails().size()];
				for (int i = 0; i < attendanceDataBean.getMails().size(); i++) {
					toAddress[i] = new InternetAddress(attendanceDataBean.getMails().get(i));
				}

				Properties prop = new Properties();
				prop.put("mail.smtp.host", MAIL_HOST);
				prop.put("mail.smtp.socketFactory.port", MAIL_PORT);
				prop.put("mail.smtp.socketFactory.class", MAIL_CLASS);
				prop.put("mail.smtp.starttls.enable", MAIL_STARTLES);
				prop.put("mail.smtp.auth", MAIL_AUTH);
				prop.put("mail.smtp.port", MAIL_SMTP_PORT);
				prop.put("mail.smtp.ssl.trust", MAIL_TRUST);
				if (attendanceDataBean.getPeriod().substring(7, 8).equals("1"))
					period = "st Period";
				else if (attendanceDataBean.getPeriod().substring(7, 8).equals("2"))
					period = "nd Period";
				else if (attendanceDataBean.getPeriod().substring(7, 8).equals("3"))
					period = "rd Period";
				else
					period = "th Period";
				String body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
						+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
						+ "<h3>Dear " + attendanceDataBean.getStudentName() + ",</h3>"
						+ "<p>Welcome on-board into NRG family.The Student " + attendanceDataBean.getStudentName()
						+ " is Coming Late Today " + "at " + attendanceDataBean.getTime1() + " for "
						+ attendanceDataBean.getPeriod().substring(7, 8) + period + ".</p>" + "<footer>" + "<center>"
						+ "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>" + "</body></html>";

				Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
					}
				});

				// compose message
				try {
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
					message.addRecipients(Message.RecipientType.TO, toAddress);
					message.setSubject("Change the Attendance ");
					BodyPart messageBodyPart1 = new MimeBodyPart();
					messageBodyPart1.setContent(body1, "text/html");
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart1);
					message.setContent(multipart);
					Transport.send(message);

					logger.info("message sent successfully");
					status = "Success";

				} catch (Exception e) {
					logger.warn("Exception -->"+e.getMessage());
				}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
			return status;
		}
		
		public static String sendEmailWorkUpdate(HomeworkDatabean homeworkDatabean) {
			String status = "Fail";
			try {
				InternetAddress[] toAddress = new InternetAddress[homeworkDatabean.getMails().size()];
				for (int i = 0; i < homeworkDatabean.getMails().size(); i++) {
					toAddress[i] = new InternetAddress(homeworkDatabean.getMails().get(i));
				}

				Properties prop = new Properties();
				prop.put("mail.smtp.host", MAIL_HOST);
				prop.put("mail.smtp.socketFactory.port", MAIL_PORT);
				prop.put("mail.smtp.socketFactory.class", MAIL_CLASS);
				prop.put("mail.smtp.starttls.enable", MAIL_STARTLES);
				prop.put("mail.smtp.auth", MAIL_AUTH);
				prop.put("mail.smtp.port", MAIL_SMTP_PORT);
				prop.put("mail.smtp.ssl.trust", MAIL_TRUST);
				String body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
						+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
						+ "<h3>Dear </h3>" + "<p>Welcome on-board into NRG family. Please find the your home Work for "
						+ homeworkDatabean.getSubject() + " is updated and the work is " + homeworkDatabean.getHomework()
						+ " ." + "</p>" + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>"
						+ "</center>" + "</footer>" + "</body></html>";
				Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
					}
				});

				// compose message
				try {
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
					message.addRecipients(Message.RecipientType.TO, toAddress);
					message.setSubject("Home Work");
					BodyPart messageBodyPart1 = new MimeBodyPart();
					messageBodyPart1.setContent(body1, "text/html");
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart1);
					message.setContent(multipart);
					Transport.send(message);
					logger.info("message sent successfully");
				} catch (Exception e) {
					logger.warn("Exception -->"+e.getMessage());
				}
				status = "Success";
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
				logger.warn("Inside Exception",e);
			} finally {

			}
			return status;
		}
		
	public static String spgeneratePdf(String personID,StudentPerformanceDataBean studentPerformanceDataBean2) {
	  String status = "fail";
	  try {
	   if (personID != null) {
	    // Create Directory
	    File files = new File("C:\\Files\\PDF\\StudentPerformance\\" + ft.format(now));
	    if (!files.exists()) {
	     files.mkdirs();
	    } else {
	     logger.debug("Alreday Found");
	    }
	    // Write PDF File
	    OutputStream file = new FileOutputStream(
	      new File(files + paths.getString("path_context").toString() + studentPerformanceDataBean2.getPerformStudID() + ".pdf"));

	    Document document = new Document(PageSize.A4, 50, 50, 10, 50);
	    PdfWriter.getInstance(document, file);
	    document.open();
	    String imageLocation = "C://Photos/Header/nrg.png";
	    Image image = Image.getInstance(imageLocation);
	    document.add(Image.getInstance(image));

	    float[] columnWidths = { 20f, 1f };
	    Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 27);
	    PdfPTable table = new PdfPTable(2); // .
	    table.setWidthPercentage(100f);
	    table.setWidths(columnWidths);

	    PdfPCell cell = new PdfPCell();

	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setColspan(2);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    PdfPCell cell1 = new PdfPCell(new Paragraph());
	    cell1.setBorder(PdfPCell.NO_BORDER);
	    PdfPCell cell2 = new PdfPCell(new Paragraph());
	    cell2.setBorder(PdfPCell.NO_BORDER);
	    table.setSpacingBefore(25f);
	    table.setSpacingAfter(25f);

	    PdfPTable nestedTable = new PdfPTable(4);
	    nestedTable.setWidthPercentage(100f);
	    PdfPCell nesCell1 = new PdfPCell(new Paragraph("Class : "));
	    PdfPCell nesCell2 = new PdfPCell(new Paragraph(studentPerformanceDataBean2.getPerClassSection()));
	    PdfPCell nesCell3 = new PdfPCell(new Paragraph("Student ID : "));
	    PdfPCell nesCell4 = new PdfPCell(new Paragraph(studentPerformanceDataBean2.getPerformStudID()));
	    nesCell1.setBorder(PdfPCell.NO_BORDER);
	    nesCell2.setBorder(PdfPCell.NO_BORDER);
	    nesCell3.setBorder(PdfPCell.NO_BORDER);
	    nesCell4.setBorder(PdfPCell.NO_BORDER);
	    nestedTable.addCell(nesCell1);
	    nestedTable.addCell(nesCell2);
	    nestedTable.addCell(nesCell3);
	    nestedTable.addCell(nesCell4);
	    nestedTable.setSpacingBefore(3f);
	    nestedTable.setSpacingAfter(3f);
	    cell1.addElement(nestedTable);

	    PdfPTable nestedTable1 = new PdfPTable(4);
	    nestedTable1.setWidthPercentage(100f);
	    PdfPCell nesCell5 = new PdfPCell(new Paragraph("Appearance : "));
	    PdfPCell nesCell6 = new PdfPCell(
	      new Paragraph(Arrays.deepToString(studentPerformanceDataBean2.getStudentAppearance())));
	    PdfPCell nesCell7 = new PdfPCell(new Paragraph("Attitude : "));
	    PdfPCell nesCell8 = new PdfPCell(
	      new Paragraph(Arrays.deepToString(studentPerformanceDataBean2.getStudentAttitude())));
	    nesCell5.setBorder(PdfPCell.NO_BORDER);
	    nesCell6.setBorder(PdfPCell.NO_BORDER);
	    nesCell7.setBorder(PdfPCell.NO_BORDER);
	    nesCell8.setBorder(PdfPCell.NO_BORDER);
	    nestedTable1.addCell(nesCell5);
	    nestedTable1.addCell(nesCell6);
	    nestedTable1.addCell(nesCell7);
	    nestedTable1.addCell(nesCell8);
	    nestedTable1.setSpacingBefore(3f);
	    nestedTable1.setSpacingAfter(3f);
	    cell1.addElement(nestedTable1);

	    table.addCell(cell);
	    table.addCell(cell1);
	    table.addCell(cell2);
	    document.add(table);
	    document.close();
	    file.close();
	    logger.debug("Done");
	    status = "Success";
	   }
	  } catch (Exception e) {
	   logger.warn("Inside Exception",e);
	  }
	  return status;
	 }

			 
	 public static String spsendEmail(String personID,StudentPerformanceDataBean studentPerformanceDataBean2) {
	  String status = "Fail";
	  logger.debug("---mail id --- " + studentPerformanceDataBean2.getMailid());
	  try {

	   InternetAddress[] toAddress = new InternetAddress[studentPerformanceDataBean2.getMailid().size()];
	   for (int i = 0; i < studentPerformanceDataBean2.getMailid().size(); i++) {
	    toAddress[i] = new InternetAddress(studentPerformanceDataBean2.getMailid().get(i));
	   }
	   Properties prop = new Properties();
	   prop.put("mail.smtp.host", "smtp.gmail.com");
	   prop.put("mail.smtp.socketFactory.port", "465");
	   prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	   prop.put("mail.smtp.starttls.enable", "true");
	   prop.put("mail.smtp.auth", "true");
	   prop.put("mail.smtp.port", "587");
	   prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

	   String body1 = "<htm><head></heade><body>"
				+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
				+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
	     + "<h3>Dear " + studentPerformanceDataBean2.getPerformStudID() + ",</h3>"
	     + "<p>Welcome on-board into NRG family. Please find the Appearance and Attitude Status enclosed with is mail."
	     + "All the very best in your new assignment</p>"
	     + "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
	     + "<tr>" + "<td>Appearance" + "</td>" + "<td>"
	     + Arrays.deepToString(studentPerformanceDataBean2.getStudentAppearance()) + ","
	     + studentPerformanceDataBean2.getAppOthers() + "</td>" + "</tr>" + "<tr>" + "<td>Attitude" + "</td>"
	     + "<td>" + Arrays.deepToString(studentPerformanceDataBean2.getStudentAttitude()) + ","
	     + studentPerformanceDataBean2.getAttOthers() + "</td>" + "</tr>" + "</table>" + "<footer>"
	     + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>"
	     + "</body></html>";

	   Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
	    protected PasswordAuthentication getPasswordAuthentication() {
	     return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
	    }
	   });
	   // compose message
	   try {
	    MimeMessage message = new MimeMessage(session);
	    message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
	    message.addRecipients(Message.RecipientType.TO, toAddress);
	    message.setSubject("SMS Confirmation");
	    BodyPart messageBodyPart1 = new MimeBodyPart();
	    messageBodyPart1.setContent(body1, "text/html");

	    MimeBodyPart messageBodyPart2 = new MimeBodyPart();
	    String filename = "C://Files/PDF/StudentPerformance/" + ft.format(now) + "/"
	      + studentPerformanceDataBean2.getPerformStudID() + ".pdf";// change
	                     // accordingly
	    DataSource source = new FileDataSource(filename);
	    messageBodyPart2.setDataHandler(new DataHandler(source));
	    messageBodyPart2.setFileName(studentPerformanceDataBean2.getPerformStudID() + ".pdf");

	    Multipart multipart = new MimeMultipart();
	    multipart.addBodyPart(messageBodyPart1);
	    multipart.addBodyPart(messageBodyPart2);
	    message.setContent(multipart);

	    Transport.send(message);

	    logger.debug("message sent successfully");
	    status = "Success";

	   } catch (MessagingException e) {
	    logger.warn("Inside Exception",e);
	    throw new RuntimeException(e);
	   }

	  } catch (Exception e) {
	   logger.warn("Inside Exception",e);
	   logger.warn("Exception -->"+e.getMessage());
	  }
	  return status;
	 }

	 public static String createPDFNotice(NoticeBoardDataBean noticeBoardDataBean2) {
			StringBuilder status = new StringBuilder("failpdf");
			try {

				// Create Directory
				File files = new File("C:\\Files\\PDF\\Notice\\" + ft.format(now));
				if (!files.exists()) {
					files.mkdirs();
				} else {
					logger.debug("Alreday Found");
				}
				// Write PDF File
				OutputStream file = new FileOutputStream(
						new File(files + paths.getString("path_context").toString() + noticeBoardDataBean2.getNoticeSubject() + ".pdf"));

				Document document = new Document(PageSize.A4, 50, 50, 10, 50);
				PdfWriter pdfWriter = PdfWriter.getInstance(document, file);
				pdfWriter.setViewerPreferences(PdfWriter.HideToolbar);
				document.open();
				String logo = "C://Photos/Header/nrg.png";
				document.add(Image.getInstance(logo));
				document.add(new Paragraph(
						"------------------------------------------------------------------------------------------------------------------------"));
				XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
				try {
					logger.debug("try");
					String str = noticeBoardDataBean2.getNoticeID();
					worker.parseXHtml(pdfWriter, document, new StringReader(str));
				} catch (Exception e) {
					logger.debug("catch");
					String str = "<p>" + noticeBoardDataBean2.getNoticeID() + "</p>";
					worker.parseXHtml(pdfWriter, document, new StringReader(str));
				}
				/*
				 * String logo="C://Photos/Header/nrg.png";
				 * document.add(Image.getInstance(logo)); document.add(new
				 * Paragraph(
				 * "------------------------------------------------------------------------------------------------------------------------"
				 * ));
				 */
				document.close();
				status = new StringBuilder("createdpdf");
			} catch (Exception e) {
				logger.warn("Inside Exception",e);
			}
			return status.toString();
		}

		public static String sendEmailNotice(NoticeBoardDataBean noticeBoardDataBean, List<String> emailIdList) {
			StringBuilder status = new StringBuilder("Fail");

			try {
				InternetAddress[] toAddress = new InternetAddress[emailIdList.size()];
				 for (int i = 0; i < emailIdList.size(); i++) {
					toAddress[i] = new InternetAddress(emailIdList.get(i));
				}
				 Properties prop = new Properties();
				   prop.put("mail.smtp.host", "smtp.gmail.com");
				   prop.put("mail.smtp.socketFactory.port", "465");
				   prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				   prop.put("mail.smtp.starttls.enable", "true");
				   prop.put("mail.smtp.auth", "true");
				   prop.put("mail.smtp.port", "587");
				   prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

				String body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
						+ "<h1> Student Monitoring System Notice Board</h1><br></br></center>" + "</center>" + "</header>"
						+ "<h3>Dear " + noticeBoardDataBean.getNoticeFollower() + ",</h3>" + "<footer>" + "<center>"
						+ "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>" + "</body></html>";

				Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
					}
				});
				// compose message
				try {
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
					message.setRecipients(Message.RecipientType.TO, toAddress);
					message.setSubject(noticeBoardDataBean.getNoticeSubject());
					BodyPart messageBodyPart1 = new MimeBodyPart();
					messageBodyPart1.setContent(body1, "text/html");

					MimeBodyPart messageBodyPart2 = new MimeBodyPart();
					String filename = "C://Files/PDF/Notice/" + ft.format(now) + "/"
							+ noticeBoardDataBean.getNoticeSubject() + ".pdf";
					DataSource source = new FileDataSource(filename);
					messageBodyPart2.setDataHandler(new DataHandler(source));
					messageBodyPart2.setFileName(noticeBoardDataBean.getNoticeSubject() + ".pdf");

					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart1);
					multipart.addBodyPart(messageBodyPart2);
					message.setContent(multipart);

					Transport.send(message);

					logger.debug("message sent successfully");
					status = new StringBuilder("Success");

				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}

			} catch (AddressException e) {
				logger.warn("Exception -->"+e.getMessage());
			}
			return status.toString();
		}
		
		public static String sendEmailClassTimeTableUpdate(ClassTimeTableDataBean classTimeTableDataBean) throws NoSuchProviderException {
			String status = "Fail";
			try {
				InternetAddress[] toAddress = new InternetAddress[classTimeTableDataBean.getMails().size()];
				for (int i = 0; i < classTimeTableDataBean.getMails().size(); i++) {
					toAddress[i] = new InternetAddress(classTimeTableDataBean.getMails().get(i));
				}

				Properties prop = new Properties();
				prop.put("mail.smtp.host", "smtp.gmail.com");
				prop.put("mail.smtp.socketFactory.port", "465");
				prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				prop.put("mail.smtp.starttls.enable", "true");
				prop.put("mail.smtp.auth", "true");
				prop.put("mail.smtp.port", "587");
				prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
				String body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
						+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
						+ "<h3>Dear </h3>"
						+ "<p>Welcome on-board into NRG family. Please find the your class time table is changed for "
						+ classTimeTableDataBean.getDay() + " , the details enclosed with is mail." + "</p>"
						+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
						+ "<tr>" + "<td> " + " Period " + "</td>" + "<td>" + " Subject" + "</td>" + "</tr>" + "<tr>"
						+ "<td> " + 1 + "</td>" + "<td>" + classTimeTableDataBean.getClasstimeList().get(0).getSubject()
						+ "</td>" + "</tr>" + "<tr>" + "<td> " + 2 + "</td>" + "<td>"
						+ classTimeTableDataBean.getClasstimeList().get(1).getSubject() + "</td>" + "</tr>" + "<tr>"
						+ "<td> " + 3 + "</td>" + "<td>" + classTimeTableDataBean.getClasstimeList().get(2).getSubject()
						+ "</td>" + "</tr>" + "<tr>" + "<td> " + 4 + "</td>" + "<td>"
						+ classTimeTableDataBean.getClasstimeList().get(3).getSubject() + "</td>" + "</tr>" + "<tr>"
						+ "<td> " + 5 + "</td>" + "<td>" + classTimeTableDataBean.getClasstimeList().get(4).getSubject()
						+ "</td>" + "</tr>" + "<tr>" + "<td> " + 6 + "</td>" + "<td>"
						+ classTimeTableDataBean.getClasstimeList().get(5).getSubject() + "</td>" + "</tr>" + "<tr>"
						+ "<td> " + 7 + "</td>" + "<td>" + classTimeTableDataBean.getClasstimeList().get(6).getSubject()
						+ "</td>" + "</tr>" + "<tr>" + "<td> " + 8 + "</td>" + "<td>"
						+ classTimeTableDataBean.getClasstimeList().get(7).getSubject() + "</td>" + "</tr>" + "</table>"
						+ "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>"
						+ "</footer>" + "</body></html>";
				Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
					}
				});

				// compose message
				try {
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
					message.addRecipients(Message.RecipientType.TO, toAddress);
					/* message.addRecipients(Message.RecipientType.CC, myCcList); */
					message.setSubject("Class Time Table Update");
					BodyPart messageBodyPart1 = new MimeBodyPart();
					messageBodyPart1.setContent(body1, "text/html");
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart1);
					message.setContent(multipart);
					Transport.send(message);
					logger.info("message sent successfully");
				}

				catch (Exception e) {
					logger.warn("Exception -->"+e.getMessage());
				}
				status = "Success";

			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
				logger.warn("Inside Exception",e);
			} finally {

			}
			return status;
		}

		public static String teacherupdategeneratePdf(TeacherDataBean teacherDataBean2,List<TeacherDataBean> ImageListPath) {
			String status = "fail";
			try {
					if (ImageListPath.size() > 0) {
						// Create Directory
						File files = new File(paths.getString("sms.teacher.teacherpdf") + ft.format(now));
						if (!files.exists()) {
							files.mkdirs();
						} else {
							logger.debug("Alreday Found");
						}
						// Write PDF File
						OutputStream file = new FileOutputStream(
								new File(files + paths.getString("path_context").toString() + teacherDataBean2.getTeaID() + ".pdf"));
						teacherDataBean2.setTeafilePath(files + paths.getString("path_context").toString() + teacherDataBean2.getTeaID() + ".pdf");
						Document document = new Document(PageSize.A4, 50, 50, 10, 50);
						PdfWriter.getInstance(document, file);
						document.open();
						String logo = paths.getString(teacherDataBean2.getSchoolID()+"_LOGO");
						document.add(Image.getInstance(new Object(){}.getClass().getClassLoader().getResource(logo)));
						document.add(new Paragraph(
								"------------------------------------------------------------------------------------------------------------------------"));

						float[] columnWidths = { 20f, 1f };
						Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 27);
						PdfPTable table = new PdfPTable(2); // .
						table.setWidthPercentage(100f);
						table.setWidths(columnWidths);

						PdfPCell cell1 = new PdfPCell(new Paragraph());
						cell1.setBorder(PdfPCell.NO_BORDER);
						PdfPCell cell2 = new PdfPCell(new Paragraph());
						cell2.setBorder(PdfPCell.NO_BORDER);
						table.setSpacingBefore(10f);
						table.setSpacingAfter(25f);

						PdfPTable nestedTable = new PdfPTable(4);
						nestedTable.setWidthPercentage(100f);
						PdfPCell nesCell1 = new PdfPCell(new Paragraph("First Name:"));
						PdfPCell nesCell2 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaFirstName()));
						PdfPCell nesCell3 = new PdfPCell(new Paragraph("Last Name:"));
						PdfPCell nesCell4 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaLastName()));
						nesCell1.setBorder(PdfPCell.NO_BORDER);
						nesCell2.setBorder(PdfPCell.NO_BORDER);
						nesCell3.setBorder(PdfPCell.NO_BORDER);
						nesCell4.setBorder(PdfPCell.NO_BORDER);
						nestedTable.addCell(nesCell1);
						nestedTable.addCell(nesCell2);
						nestedTable.addCell(nesCell3);
						nestedTable.addCell(nesCell4);
						nestedTable.setSpacingBefore(3f);
						nestedTable.setSpacingAfter(3f);
						cell1.addElement(nestedTable);

						PdfPTable nestedTable1 = new PdfPTable(4);
						nestedTable1.setWidthPercentage(100f);
						PdfPCell nesCell5 = new PdfPCell(new Paragraph("Father Name:"));
						PdfPCell nesCell6 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaFatherName()));
						PdfPCell nesCell7 = new PdfPCell(new Paragraph("Mother Name:"));
						PdfPCell nesCell8 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaMotherName()));
						nesCell5.setBorder(PdfPCell.NO_BORDER);
						nesCell6.setBorder(PdfPCell.NO_BORDER);
						nesCell7.setBorder(PdfPCell.NO_BORDER);
						nesCell8.setBorder(PdfPCell.NO_BORDER);
						nestedTable1.addCell(nesCell5);
						nestedTable1.addCell(nesCell6);
						nestedTable1.addCell(nesCell7);
						nestedTable1.addCell(nesCell8);
						nestedTable1.setSpacingBefore(3f);
						nestedTable1.setSpacingAfter(3f);
						cell1.addElement(nestedTable1);

						PdfPTable nestedTable2 = new PdfPTable(4);
						nestedTable2.setWidthPercentage(100f);
						PdfPCell nesCell9 = new PdfPCell(new Paragraph(" Date of Birth:"));
						PdfPCell nesCell10 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaDob().toString()));
						PdfPCell nesCell11 = new PdfPCell(new Paragraph("Gender:"));
						PdfPCell nesCell12 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaGender()));
						nesCell10.setBorder(PdfPCell.NO_BORDER);
						nesCell11.setBorder(PdfPCell.NO_BORDER);
						nesCell12.setBorder(PdfPCell.NO_BORDER);
						nesCell9.setBorder(PdfPCell.NO_BORDER);
						nestedTable2.addCell(nesCell9);
						nestedTable2.addCell(nesCell10);
						nestedTable2.addCell(nesCell11);
						nestedTable2.addCell(nesCell12);
						nestedTable2.setSpacingBefore(3f);
						nestedTable2.setSpacingAfter(3f);
						cell1.addElement(nestedTable2);

						PdfPTable nestedTable3 = new PdfPTable(4);
						nestedTable3.setWidthPercentage(100f);
						PdfPCell nesCell13 = new PdfPCell(new Paragraph("Present Address:"));
						PdfPCell nesCell14 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaPresentAddress()));
						PdfPCell nesCell15 = new PdfPCell(new Paragraph("Permanent Address:"));
						PdfPCell nesCell16 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaPermanentAddress()));
						nesCell13.setBorder(PdfPCell.NO_BORDER);
						nesCell14.setBorder(PdfPCell.NO_BORDER);
						nesCell15.setBorder(PdfPCell.NO_BORDER);
						nesCell16.setBorder(PdfPCell.NO_BORDER);
						nestedTable3.addCell(nesCell13);
						nestedTable3.addCell(nesCell14);
						nestedTable3.addCell(nesCell15);
						nestedTable3.addCell(nesCell16);
						nestedTable3.setSpacingBefore(3f);
						nestedTable3.setSpacingAfter(3f);
						cell1.addElement(nestedTable3);

						

						
						PdfPTable nestedTable6 = new PdfPTable(4);
						nestedTable6.setWidthPercentage(100f);
						PdfPCell nesCell21 = new PdfPCell(new Paragraph("Phone Number:"));
						PdfPCell nesCell22 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaPhoneNo()));
						PdfPCell nesCell23 = new PdfPCell(new Paragraph("Email ID:"));
						PdfPCell nesCell24 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaEmail()));
						nesCell21.setBorder(PdfPCell.NO_BORDER);
						nesCell22.setBorder(PdfPCell.NO_BORDER);
						nesCell23.setBorder(PdfPCell.NO_BORDER);
						nesCell24.setBorder(PdfPCell.NO_BORDER);
						nestedTable6.addCell(nesCell21);
						nestedTable6.addCell(nesCell22);
						nestedTable6.addCell(nesCell23);
						nestedTable6.addCell(nesCell24);
						nestedTable6.setSpacingBefore(3f);
						nestedTable6.setSpacingAfter(3f);
						cell1.addElement(nestedTable6);

						PdfPTable nestedTable7 = new PdfPTable(4);
						nestedTable7.setWidthPercentage(100f);
						PdfPCell nesCell25 = new PdfPCell(new Paragraph("Education Qualification:"));
						PdfPCell nesCell26 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaEduQualification()));
						PdfPCell nesCell27 = new PdfPCell(new Paragraph("Year of Passing:"));
						PdfPCell nesCell28 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaYearOfPassing()));
						nesCell25.setBorder(PdfPCell.NO_BORDER);
						nesCell26.setBorder(PdfPCell.NO_BORDER);
						nesCell27.setBorder(PdfPCell.NO_BORDER);
						nesCell28.setBorder(PdfPCell.NO_BORDER);
						nestedTable7.addCell(nesCell25);
						nestedTable7.addCell(nesCell26);
						nestedTable7.addCell(nesCell27);
						nestedTable7.addCell(nesCell28);
						nestedTable7.setSpacingBefore(3f);
						nestedTable7.setSpacingAfter(3f);
						cell1.addElement(nestedTable7);

						PdfPTable nestedTable9 = new PdfPTable(4);
						nestedTable9.setWidthPercentage(100f);
						PdfPCell nesCell33 = new PdfPCell(new Paragraph("Teacher ID:"));
						PdfPCell nesCell34 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaID()));
						PdfPCell nesCell35 = null;
						PdfPCell nesCell36 = null;
						if(ImageListPath.get(0).getPath() !=null)
						{
							String tempdate = ft.format(ImageListPath.get(0).getPathDate());
							String imageLocation = paths.getString("sms.teacher.teacherphoto") + tempdate + "/" + ImageListPath.get(0).getPath();
							Image image = Image.getInstance(imageLocation);
							image.scaleAbsolute(100, 100);
							nesCell35=new PdfPCell(new Paragraph("Photo:"));
							nesCell36 = new PdfPCell(image, false);
						}else{
							nesCell35=new PdfPCell(new Paragraph(""));
							 nesCell36 = new PdfPCell(new Paragraph(""));
						}
						nesCell33.setBorder(PdfPCell.NO_BORDER);
						nesCell34.setBorder(PdfPCell.NO_BORDER);
						nesCell35.setBorder(PdfPCell.NO_BORDER);
						nesCell36.setBorder(PdfPCell.NO_BORDER);
						nestedTable9.addCell(nesCell33);
						nestedTable9.addCell(nesCell34);
						nestedTable9.addCell(nesCell35);
						nestedTable9.addCell(nesCell36);
						nestedTable9.setSpacingBefore(3f);
						nestedTable9.setSpacingAfter(3f);
						cell1.addElement(nestedTable9);

						table.addCell(cell1);
						table.addCell(cell2);
						document.add(table);
						document.close();
						file.close();

						/*
						 * //PDF open Windows and MAC File pdfFile = new
						 * File(files+"\\"+teacherDataBean2.getTeaUsername()+".pdf")
						 * ; if(pdfFile.exists()){ if (Desktop.isDesktopSupported())
						 * { Desktop.getDesktop().open(pdfFile); } else {
						 * logger.debug("Awt Desktop is not supported!"); }
						 * 
						 * } else { logger.debug("File is not exists!"); }
						 */
						logger.debug("Done");
						status = "Success";
					}
				
			} catch (Exception e) {
				logger.warn("Inside Exception",e);
				e.printStackTrace();
			}
			return status;
		}

		public static String teacherupdatesendEmail(TeacherDataBean teacherDataBean2, List<TeacherDataBean> imageListPath2) {
			String status = "Fail";
			try {
				String to = teacherDataBean2.getTeaEmail();
				logger.debug("to-------" + to);
				Properties prop = new Properties();
				prop.put("mail.smtp.host", "smtp.gmail.com");
				prop.put("mail.smtp.socketFactory.port", "465");
				prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				prop.put("mail.smtp.starttls.enable", "true");
				prop.put("mail.smtp.auth", "true");
				prop.put("mail.smtp.port", "587");
				prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");


				String body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
						+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
						+ "<h3>Dear " + teacherDataBean2.getTeaID() + ",</h3>" + "<p>Welcome on-board into NRG family. "
						+ "All the very best in your new assignment</p>" + "You Profile Updated" + "<footer>" + "<center>"
						+ "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>" + "</body></html>";

				Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
					}
				});
				// compose message
				try {
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
					message.setSubject("SMS Confirmation-" + teacherDataBean2.getTeaID());
					BodyPart messageBodyPart1 = new MimeBodyPart();
					messageBodyPart1.setContent(body1, "text/html");

					MimeBodyPart messageBodyPart2 = new MimeBodyPart();
					//String filename = "C://Files/PDF/Teacher/" + ft.format(now) + "/" + teacherDataBean2.getTeaID()	+ ".pdf";// change accordingly
					String filename = "/home/ec2-user/File_Sms/Files/PDF/Teacher" + ft.format(now) + "/" + teacherDataBean2.getTeaID()	+ ".pdf";// change accordingly
					DataSource source = new FileDataSource(filename);
					messageBodyPart2.setDataHandler(new DataHandler(source));
					messageBodyPart2.setFileName(teacherDataBean2.getTeaID() + ".pdf");

					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart1);
					multipart.addBodyPart(messageBodyPart2);
					message.setContent(multipart);

					Transport.send(message);

					logger.debug("message sent successfully");
					logger.debug("message sent successfully");
					status = "Success";

				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}

			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
				logger.warn("Inside Exception",e);
			}
			return status;
		}


	//parent
		
		public static String parentupdatesendEmail(List<ParentsDataBean> parentList2, String personID, ParentsDataBean parentsDataBean2) {
			String status = "Fail";

			try {
				// String to="robertarjun46@gmail.com";
				String to = parentsDataBean2.getParEmail();
				Properties prop = new Properties();
				prop.put("mail.smtp.host", "smtp.gmail.com");
				prop.put("mail.smtp.socketFactory.port", "465");
				prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				prop.put("mail.smtp.starttls.enable", "true");
				prop.put("mail.smtp.auth", "true");
				prop.put("mail.smtp.port", "587");
				prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
				String body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
						+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
						+ "<h3>Dear " + parentsDataBean2.getParParentID() + ",</h3>"
						+ "<p>Welcome to join  our Student Monitoring System. you can find the following features  ."
						+ "you can check your child report card, attendence,online payment,notice board and time table."
						+ "also you can get our user name and password, from that you can check our website itself</p>"
						+ "<br></br>" + "Your Profile is Updated" + "<footer>" + "<center>"
						+ "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>" + "</body></html>";

				Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
					}
				});

				// compose message
				try {
					/*
					 * session = Session.getInstance(prop, this);// this is the
					 * local variable not the instance one
					 */ MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
					message.setSubject("SMS Confirmation-" + parentsDataBean2.getParParentID());
					BodyPart messageBodyPart1 = new MimeBodyPart();
					messageBodyPart1.setContent(body1, "text/html");

					MimeBodyPart messageBodyPart2 = new MimeBodyPart();
				//	String filename = "C://Files/PDF/Parent/" + ft.format(now) + "/" + parentsDataBean2.getParParentID() + ".pdf";// change accordingly
					String filename = "/home/ec2-user/File_Sms/PDF/Parent" + ft.format(now) + "/" + parentsDataBean2.getParParentID() + ".pdf";// change accordingly

					DataSource source = new FileDataSource(filename);
					messageBodyPart2.setDataHandler(new DataHandler(source));
					messageBodyPart2.setFileName(parentsDataBean2.getParParentID() + ".pdf");

					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart1);
					multipart.addBodyPart(messageBodyPart2);
					message.setContent(multipart);

					Transport.send(message);

					logger.debug("message sent successfully");
					status = "Success";

				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}

			} catch (Exception e) {
				logger.warn("Inside Exception",e);
				logger.warn("Exception -->"+e.getMessage());
			}
			return status;
		}

		public static String parentupdategeneratePdf(List<ParentsDataBean> ImageListPath, ParentsDataBean parentsDataBean2) {

			String status = "fail";
			try {
					logger.debug("image path size----------" + ImageListPath.size());
					if (ImageListPath.size() > 0) {
						// Create Directory
						File files = new File(paths.getString("sms.parents.pdf") + ft.format(now));
						if (!files.exists()) {
							files.mkdirs();
						} else {
							logger.debug("Alreday Found");
						}
						// Write PDF File
						OutputStream file = new FileOutputStream(
								new File(files + paths.getString("path_context").toString() + parentsDataBean2.getParParentID() + ".pdf"));
						parentsDataBean2.setParfilePath(files + paths.getString("path_context").toString() + parentsDataBean2.getParParentID() + ".pdf");
						Document document = new Document(PageSize.A4, 50, 50, 10, 50);
						PdfWriter.getInstance(document, file);
						document.open();
						String logo = paths.getString(parentsDataBean2.getSchoolID()+"_LOGO");
						document.add(Image.getInstance(new Object(){}.getClass().getClassLoader().getResource(logo)));
						document.add(new Paragraph(
								"------------------------------------------------------------------------------------------------------------------------"));

						float[] columnWidths = { 20f, 1f };
						Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 27);
						PdfPTable table = new PdfPTable(2); // .
						table.setWidthPercentage(100f);
						table.setWidths(columnWidths);

						PdfPCell cell1 = new PdfPCell(new Paragraph());
						cell1.setBorder(PdfPCell.NO_BORDER);
						PdfPCell cell2 = new PdfPCell(new Paragraph());
						cell2.setBorder(PdfPCell.NO_BORDER);
						table.setSpacingBefore(10f);
						table.setSpacingAfter(25f);

						PdfPTable nestedTable = new PdfPTable(4);
						nestedTable.setWidthPercentage(100f);
						PdfPCell nesCell1 = new PdfPCell(new Paragraph("First Name:"));
						PdfPCell nesCell2 = new PdfPCell(new Paragraph(ImageListPath.get(0).getParFirstName()));
						PdfPCell nesCell3 = new PdfPCell(new Paragraph("Last Name:"));
						PdfPCell nesCell4 = new PdfPCell(new Paragraph(ImageListPath.get(0).getParLastName()));
						nesCell1.setBorder(PdfPCell.NO_BORDER);
						nesCell2.setBorder(PdfPCell.NO_BORDER);
						nesCell3.setBorder(PdfPCell.NO_BORDER);
						nesCell4.setBorder(PdfPCell.NO_BORDER);
						nestedTable.addCell(nesCell1);
						nestedTable.addCell(nesCell2);
						nestedTable.addCell(nesCell3);
						nestedTable.addCell(nesCell4);
						nestedTable.setSpacingBefore(3f);
						nestedTable.setSpacingAfter(3f);
						cell1.addElement(nestedTable);

						if(ImageListPath.size()==1)
						{
							PdfPTable nestedTable1 = new PdfPTable(4);
							nestedTable1.setWidthPercentage(100f);
							PdfPCell nesCell5 = new PdfPCell(new Paragraph("Student ID:"));
							PdfPCell nesCell6 = new PdfPCell(new Paragraph(ImageListPath.get(0).getParStudID()));
							PdfPCell nesCell7 = new PdfPCell(new Paragraph("Student Class:"));
							PdfPCell nesCell8 = new PdfPCell(new Paragraph(ImageListPath.get(0).getParStuClass()));
							nesCell5.setBorder(PdfPCell.NO_BORDER);
							nesCell6.setBorder(PdfPCell.NO_BORDER);
							nesCell7.setBorder(PdfPCell.NO_BORDER);
							nesCell8.setBorder(PdfPCell.NO_BORDER);
							nestedTable1.addCell(nesCell5);
							nestedTable1.addCell(nesCell6);
							nestedTable1.addCell(nesCell7);
							nestedTable1.addCell(nesCell8);
							nestedTable1.setSpacingBefore(3f);
							nestedTable1.setSpacingAfter(3f);
							cell1.addElement(nestedTable1);
						}
						else if(ImageListPath.size()==2)
						{
							PdfPTable nestedTable1 = new PdfPTable(4);
							nestedTable1.setWidthPercentage(100f);
							PdfPCell nesCell5 = new PdfPCell(new Paragraph("Student ID:"));
							PdfPCell nesCell6 = new PdfPCell(new Paragraph(ImageListPath.get(0).getParStudID()));
							PdfPCell nesCell7 = new PdfPCell(new Paragraph("Student Class:"));
							PdfPCell nesCell8 = new PdfPCell(new Paragraph(ImageListPath.get(0).getParStuClass()));
							nesCell5.setBorder(PdfPCell.NO_BORDER);
							nesCell6.setBorder(PdfPCell.NO_BORDER);
							nesCell7.setBorder(PdfPCell.NO_BORDER);
							nesCell8.setBorder(PdfPCell.NO_BORDER);
							nestedTable1.addCell(nesCell5);
							nestedTable1.addCell(nesCell6);
							nestedTable1.addCell(nesCell7);
							nestedTable1.addCell(nesCell8);
							nestedTable1.setSpacingBefore(3f);
							nestedTable1.setSpacingAfter(3f);
							cell1.addElement(nestedTable1);
							PdfPTable nestedTable11 = new PdfPTable(4);
							nestedTable11.setWidthPercentage(100f);
							PdfPCell nesCell51 = new PdfPCell(new Paragraph("Student ID:"));
							PdfPCell nesCell61 = new PdfPCell(new Paragraph(ImageListPath.get(1).getParStudID()));
							PdfPCell nesCell71 = new PdfPCell(new Paragraph("Student Class:"));
							PdfPCell nesCell81 = new PdfPCell(new Paragraph(ImageListPath.get(1).getParStuClass()));
							nesCell51.setBorder(PdfPCell.NO_BORDER);
							nesCell61.setBorder(PdfPCell.NO_BORDER);
							nesCell71.setBorder(PdfPCell.NO_BORDER);
							nesCell81.setBorder(PdfPCell.NO_BORDER);
							nestedTable11.addCell(nesCell51);
							nestedTable11.addCell(nesCell61);
							nestedTable11.addCell(nesCell71);
							nestedTable11.addCell(nesCell81);
							nestedTable11.setSpacingBefore(3f);
							nestedTable11.setSpacingAfter(3f);
							cell1.addElement(nestedTable11);
						}
						else if(ImageListPath.size()==3)
						{
							PdfPTable nestedTable1 = new PdfPTable(4);
							nestedTable1.setWidthPercentage(100f);
							PdfPCell nesCell5 = new PdfPCell(new Paragraph("Student ID:"));
							PdfPCell nesCell6 = new PdfPCell(new Paragraph(ImageListPath.get(0).getParStudID()));
							PdfPCell nesCell7 = new PdfPCell(new Paragraph("Student Class:"));
							PdfPCell nesCell8 = new PdfPCell(new Paragraph(ImageListPath.get(0).getParStuClass()));
							nesCell5.setBorder(PdfPCell.NO_BORDER);
							nesCell6.setBorder(PdfPCell.NO_BORDER);
							nesCell7.setBorder(PdfPCell.NO_BORDER);
							nesCell8.setBorder(PdfPCell.NO_BORDER);
							nestedTable1.addCell(nesCell5);
							nestedTable1.addCell(nesCell6);
							nestedTable1.addCell(nesCell7);
							nestedTable1.addCell(nesCell8);
							nestedTable1.setSpacingBefore(3f);
							nestedTable1.setSpacingAfter(3f);
							cell1.addElement(nestedTable1);
							PdfPTable nestedTable11 = new PdfPTable(4);
							nestedTable11.setWidthPercentage(100f);
							PdfPCell nesCell51 = new PdfPCell(new Paragraph("Student ID:"));
							PdfPCell nesCell61 = new PdfPCell(new Paragraph(ImageListPath.get(1).getParStudID()));
							PdfPCell nesCell71 = new PdfPCell(new Paragraph("Student Class:"));
							PdfPCell nesCell81 = new PdfPCell(new Paragraph(ImageListPath.get(1).getParStuClass()));
							nesCell51.setBorder(PdfPCell.NO_BORDER);
							nesCell61.setBorder(PdfPCell.NO_BORDER);
							nesCell71.setBorder(PdfPCell.NO_BORDER);
							nesCell81.setBorder(PdfPCell.NO_BORDER);
							nestedTable11.addCell(nesCell51);
							nestedTable11.addCell(nesCell61);
							nestedTable11.addCell(nesCell71);
							nestedTable11.addCell(nesCell81);
							nestedTable11.setSpacingBefore(3f);
							nestedTable11.setSpacingAfter(3f);
							cell1.addElement(nestedTable11);
							PdfPTable nestedTable12 = new PdfPTable(4);
							nestedTable12.setWidthPercentage(100f);
							PdfPCell nesCell52 = new PdfPCell(new Paragraph("Student ID:"));
							PdfPCell nesCell62 = new PdfPCell(new Paragraph(ImageListPath.get(2).getParStudID()));
							PdfPCell nesCell72 = new PdfPCell(new Paragraph("Student Class:"));
							PdfPCell nesCell82 = new PdfPCell(new Paragraph(ImageListPath.get(2).getParStuClass()));
							nesCell52.setBorder(PdfPCell.NO_BORDER);
							nesCell62.setBorder(PdfPCell.NO_BORDER);
							nesCell72.setBorder(PdfPCell.NO_BORDER);
							nesCell82.setBorder(PdfPCell.NO_BORDER);
							nestedTable12.addCell(nesCell52);
							nestedTable12.addCell(nesCell62);
							nestedTable12.addCell(nesCell72);
							nestedTable12.addCell(nesCell82);
							nestedTable12.setSpacingBefore(3f);
							nestedTable12.setSpacingAfter(3f);
							cell1.addElement(nestedTable12);
						}
						else if(ImageListPath.size()==4)
						{
							PdfPTable nestedTable1 = new PdfPTable(4);
							nestedTable1.setWidthPercentage(100f);
							PdfPCell nesCell5 = new PdfPCell(new Paragraph("Student ID:"));
							PdfPCell nesCell6 = new PdfPCell(new Paragraph(ImageListPath.get(0).getParStudID()));
							PdfPCell nesCell7 = new PdfPCell(new Paragraph("Student Class:"));
							PdfPCell nesCell8 = new PdfPCell(new Paragraph(ImageListPath.get(0).getParStuClass()));
							nesCell5.setBorder(PdfPCell.NO_BORDER);
							nesCell6.setBorder(PdfPCell.NO_BORDER);
							nesCell7.setBorder(PdfPCell.NO_BORDER);
							nesCell8.setBorder(PdfPCell.NO_BORDER);
							nestedTable1.addCell(nesCell5);
							nestedTable1.addCell(nesCell6);
							nestedTable1.addCell(nesCell7);
							nestedTable1.addCell(nesCell8);
							nestedTable1.setSpacingBefore(3f);
							nestedTable1.setSpacingAfter(3f);
							cell1.addElement(nestedTable1);
							PdfPTable nestedTable11 = new PdfPTable(4);
							nestedTable11.setWidthPercentage(100f);
							PdfPCell nesCell51 = new PdfPCell(new Paragraph("Student ID:"));
							PdfPCell nesCell61 = new PdfPCell(new Paragraph(ImageListPath.get(1).getParStudID()));
							PdfPCell nesCell71 = new PdfPCell(new Paragraph("Student Class:"));
							PdfPCell nesCell81 = new PdfPCell(new Paragraph(ImageListPath.get(1).getParStuClass()));
							nesCell51.setBorder(PdfPCell.NO_BORDER);
							nesCell61.setBorder(PdfPCell.NO_BORDER);
							nesCell71.setBorder(PdfPCell.NO_BORDER);
							nesCell81.setBorder(PdfPCell.NO_BORDER);
							nestedTable11.addCell(nesCell51);
							nestedTable11.addCell(nesCell61);
							nestedTable11.addCell(nesCell71);
							nestedTable11.addCell(nesCell81);
							nestedTable11.setSpacingBefore(3f);
							nestedTable11.setSpacingAfter(3f);
							cell1.addElement(nestedTable11);
							PdfPTable nestedTable12 = new PdfPTable(4);
							nestedTable12.setWidthPercentage(100f);
							PdfPCell nesCell52 = new PdfPCell(new Paragraph("Student ID:"));
							PdfPCell nesCell62 = new PdfPCell(new Paragraph(ImageListPath.get(2).getParStudID()));
							PdfPCell nesCell72 = new PdfPCell(new Paragraph("Student Class:"));
							PdfPCell nesCell82 = new PdfPCell(new Paragraph(ImageListPath.get(2).getParStuClass()));
							nesCell52.setBorder(PdfPCell.NO_BORDER);
							nesCell62.setBorder(PdfPCell.NO_BORDER);
							nesCell72.setBorder(PdfPCell.NO_BORDER);
							nesCell82.setBorder(PdfPCell.NO_BORDER);
							nestedTable12.addCell(nesCell52);
							nestedTable12.addCell(nesCell62);
							nestedTable12.addCell(nesCell72);
							nestedTable12.addCell(nesCell82);
							nestedTable12.setSpacingBefore(3f);
							nestedTable12.setSpacingAfter(3f);
							cell1.addElement(nestedTable12);
							PdfPTable nestedTable13 = new PdfPTable(4);
							nestedTable13.setWidthPercentage(100f);
							PdfPCell nesCell53 = new PdfPCell(new Paragraph("Student ID:"));
							PdfPCell nesCell63 = new PdfPCell(new Paragraph(ImageListPath.get(3).getParStudID()));
							PdfPCell nesCell73 = new PdfPCell(new Paragraph("Student Class:"));
							PdfPCell nesCell83 = new PdfPCell(new Paragraph(ImageListPath.get(3).getParStuClass()));
							nesCell53.setBorder(PdfPCell.NO_BORDER);
							nesCell63.setBorder(PdfPCell.NO_BORDER);
							nesCell73.setBorder(PdfPCell.NO_BORDER);
							nesCell83.setBorder(PdfPCell.NO_BORDER);
							nestedTable13.addCell(nesCell53);
							nestedTable13.addCell(nesCell63);
							nestedTable13.addCell(nesCell73);
							nestedTable13.addCell(nesCell83);
							nestedTable13.setSpacingBefore(3f);
							nestedTable13.setSpacingAfter(3f);
							cell1.addElement(nestedTable13);
						}
						else if(ImageListPath.size()==5)
						{
							PdfPTable nestedTable1 = new PdfPTable(4);
							nestedTable1.setWidthPercentage(100f);
							PdfPCell nesCell5 = new PdfPCell(new Paragraph("Student ID:"));
							PdfPCell nesCell6 = new PdfPCell(new Paragraph(ImageListPath.get(0).getParStudID()));
							PdfPCell nesCell7 = new PdfPCell(new Paragraph("Student Class:"));
							PdfPCell nesCell8 = new PdfPCell(new Paragraph(ImageListPath.get(0).getParStuClass()));
							nesCell5.setBorder(PdfPCell.NO_BORDER);
							nesCell6.setBorder(PdfPCell.NO_BORDER);
							nesCell7.setBorder(PdfPCell.NO_BORDER);
							nesCell8.setBorder(PdfPCell.NO_BORDER);
							nestedTable1.addCell(nesCell5);
							nestedTable1.addCell(nesCell6);
							nestedTable1.addCell(nesCell7);
							nestedTable1.addCell(nesCell8);
							nestedTable1.setSpacingBefore(3f);
							nestedTable1.setSpacingAfter(3f);
							cell1.addElement(nestedTable1);
							PdfPTable nestedTable11 = new PdfPTable(4);
							nestedTable11.setWidthPercentage(100f);
							PdfPCell nesCell51 = new PdfPCell(new Paragraph("Student ID:"));
							PdfPCell nesCell61 = new PdfPCell(new Paragraph(ImageListPath.get(1).getParStudID()));
							PdfPCell nesCell71 = new PdfPCell(new Paragraph("Student Class:"));
							PdfPCell nesCell81 = new PdfPCell(new Paragraph(ImageListPath.get(1).getParStuClass()));
							nesCell51.setBorder(PdfPCell.NO_BORDER);
							nesCell61.setBorder(PdfPCell.NO_BORDER);
							nesCell71.setBorder(PdfPCell.NO_BORDER);
							nesCell81.setBorder(PdfPCell.NO_BORDER);
							nestedTable11.addCell(nesCell51);
							nestedTable11.addCell(nesCell61);
							nestedTable11.addCell(nesCell71);
							nestedTable11.addCell(nesCell81);
							nestedTable11.setSpacingBefore(3f);
							nestedTable11.setSpacingAfter(3f);
							cell1.addElement(nestedTable11);
							PdfPTable nestedTable12 = new PdfPTable(4);
							nestedTable12.setWidthPercentage(100f);
							PdfPCell nesCell52 = new PdfPCell(new Paragraph("Student ID:"));
							PdfPCell nesCell62 = new PdfPCell(new Paragraph(ImageListPath.get(2).getParStudID()));
							PdfPCell nesCell72 = new PdfPCell(new Paragraph("Student Class:"));
							PdfPCell nesCell82 = new PdfPCell(new Paragraph(ImageListPath.get(2).getParStuClass()));
							nesCell52.setBorder(PdfPCell.NO_BORDER);
							nesCell62.setBorder(PdfPCell.NO_BORDER);
							nesCell72.setBorder(PdfPCell.NO_BORDER);
							nesCell82.setBorder(PdfPCell.NO_BORDER);
							nestedTable12.addCell(nesCell52);
							nestedTable12.addCell(nesCell62);
							nestedTable12.addCell(nesCell72);
							nestedTable12.addCell(nesCell82);
							nestedTable12.setSpacingBefore(3f);
							nestedTable12.setSpacingAfter(3f);
							cell1.addElement(nestedTable12);
							PdfPTable nestedTable13 = new PdfPTable(4);
							nestedTable13.setWidthPercentage(100f);
							PdfPCell nesCell53 = new PdfPCell(new Paragraph("Student ID:"));
							PdfPCell nesCell63 = new PdfPCell(new Paragraph(ImageListPath.get(3).getParStudID()));
							PdfPCell nesCell73 = new PdfPCell(new Paragraph("Student Class:"));
							PdfPCell nesCell83 = new PdfPCell(new Paragraph(ImageListPath.get(3).getParStuClass()));
							nesCell53.setBorder(PdfPCell.NO_BORDER);
							nesCell63.setBorder(PdfPCell.NO_BORDER);
							nesCell73.setBorder(PdfPCell.NO_BORDER);
							nesCell83.setBorder(PdfPCell.NO_BORDER);
							nestedTable13.addCell(nesCell53);
							nestedTable13.addCell(nesCell63);
							nestedTable13.addCell(nesCell73);
							nestedTable13.addCell(nesCell83);
							nestedTable13.setSpacingBefore(3f);
							nestedTable13.setSpacingAfter(3f);
							cell1.addElement(nestedTable13);
							PdfPTable nestedTable14 = new PdfPTable(4);
							nestedTable14.setWidthPercentage(100f);
							PdfPCell nesCell54 = new PdfPCell(new Paragraph("Student ID:"));
							PdfPCell nesCell64 = new PdfPCell(new Paragraph(ImageListPath.get(4).getParStudID()));
							PdfPCell nesCell74 = new PdfPCell(new Paragraph("Student Class:"));
							PdfPCell nesCell84 = new PdfPCell(new Paragraph(ImageListPath.get(4).getParStuClass()));
							nesCell54.setBorder(PdfPCell.NO_BORDER);
							nesCell64.setBorder(PdfPCell.NO_BORDER);
							nesCell74.setBorder(PdfPCell.NO_BORDER);
							nesCell84.setBorder(PdfPCell.NO_BORDER);
							nestedTable14.addCell(nesCell54);
							nestedTable14.addCell(nesCell64);
							nestedTable14.addCell(nesCell74);
							nestedTable14.addCell(nesCell84);
							nestedTable14.setSpacingBefore(3f);
							nestedTable14.setSpacingAfter(3f);
							cell1.addElement(nestedTable14);
						}

						PdfPTable nestedTable2 = new PdfPTable(4);
						nestedTable2.setWidthPercentage(100f);
						PdfPCell nesCell9 = new PdfPCell(new Paragraph("Phone number:"));
						PdfPCell nesCell10 = new PdfPCell(new Paragraph(ImageListPath.get(0).getParPhoneNo()));
						PdfPCell nesCell11 = new PdfPCell(new Paragraph("Email ID:"));
						PdfPCell nesCell12 = new PdfPCell(new Paragraph(ImageListPath.get(0).getParEmail()));
						nesCell9.setBorder(PdfPCell.NO_BORDER);
						nesCell10.setBorder(PdfPCell.NO_BORDER);
						nesCell11.setBorder(PdfPCell.NO_BORDER);
						nesCell12.setBorder(PdfPCell.NO_BORDER);
						nestedTable2.addCell(nesCell9);
						nestedTable2.addCell(nesCell10);
						nestedTable2.addCell(nesCell11);
						nestedTable2.addCell(nesCell12);
						nestedTable2.setSpacingBefore(3f);
						nestedTable2.setSpacingAfter(3f);
						cell1.addElement(nestedTable2);

						PdfPTable nestedTable3 = new PdfPTable(4);
						nestedTable3.setWidthPercentage(100f);
						PdfPCell nesCell13 = new PdfPCell(new Paragraph("Relation:"));
						PdfPCell nesCell14 = new PdfPCell(new Paragraph(ImageListPath.get(0).getParentRelation()));
						PdfPCell nesCell15 = new PdfPCell(new Paragraph("Parent ID:"));
						PdfPCell nesCell16 = new PdfPCell(new Paragraph(ImageListPath.get(0).getParParentID()));
						nesCell13.setBorder(PdfPCell.NO_BORDER);
						nesCell14.setBorder(PdfPCell.NO_BORDER);
						nesCell15.setBorder(PdfPCell.NO_BORDER);
						nesCell16.setBorder(PdfPCell.NO_BORDER);
						nestedTable3.addCell(nesCell13);
						nestedTable3.addCell(nesCell14);
						nestedTable3.addCell(nesCell15);
						nestedTable3.addCell(nesCell16);
						nestedTable3.setSpacingBefore(3f);
						nestedTable3.setSpacingAfter(3f);
						cell1.addElement(nestedTable3);

						PdfPTable nestedTable9 = new PdfPTable(4);
						nestedTable9.setWidthPercentage(100f);
						PdfPCell nesCell33 = new PdfPCell(new Paragraph());
						PdfPCell nesCell34 = new PdfPCell(new Paragraph());
						PdfPCell nesCell35 = new PdfPCell(new Paragraph("Photo:"));
						String tempdate = ft.format(ImageListPath.get(0).getParPathDate());
						String imageLocation = paths.getString("sms.parents.photo") + tempdate + paths.getString("path_context")
								+ ImageListPath.get(0).getParfilePath();
						Image image = Image.getInstance(imageLocation);
						image.scaleAbsolute(100, 100);
						PdfPCell nesCell36 = new PdfPCell(image, false);
						nesCell33.setBorder(PdfPCell.NO_BORDER);
						nesCell34.setBorder(PdfPCell.NO_BORDER);
						nesCell35.setBorder(PdfPCell.NO_BORDER);
						nesCell36.setBorder(PdfPCell.NO_BORDER);
						nestedTable9.addCell(nesCell35);
						nestedTable9.addCell(nesCell36);
						nestedTable9.addCell(nesCell33);
						nestedTable9.addCell(nesCell34);

						nestedTable9.setSpacingBefore(3f);
						nestedTable9.setSpacingAfter(3f);
						cell1.addElement(nestedTable9);

						table.addCell(cell1);
						table.addCell(cell2);
						document.add(table);
						document.close();
						file.close();
						/*
						 * //PDF open Windows and MAC File pdfFile = new
						 * File(files+"\\"+parentsDataBean2.getParUsername()+".pdf")
						 * ; if(pdfFile.exists()){ if (Desktop.isDesktopSupported())
						 * { Desktop.getDesktop().open(pdfFile); } else {
						 * logger.debug("Awt Desktop is not supported!"); }
						 * 
						 * } else { logger.debug("File is not exists!"); }
						 */

						logger.debug("Done");
						status = "Success";
					}
				
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
			return status;
		}
		
		
		public static String staffupdategeneratePdf(StaffDataBean staffDataBean2,List<StaffDataBean> ImageListPath) {

			String status = "fail";
			try {
					logger.debug("======jackkkk======" + ImageListPath.size());
					if (ImageListPath.size() > 0) {
						// Create Directory
						File files = new File(paths.getString("sms.staff.pdf").toString() + ft.format(now));
						if (!files.exists()) {
							files.mkdirs();
						} else {

							logger.debug("Alreday Found");
						}
						// Write PDF File
						OutputStream file = new FileOutputStream(
								new File(files + paths.getString("path_context").toString() + staffDataBean2.getStaStaffID() + ".pdf"));
						staffDataBean2.setStafilePath((files + paths.getString("path_context").toString() + staffDataBean2.getStaStaffID() + ".pdf"));
						Document document = new Document(PageSize.A4, 50, 50, 10, 50);
						PdfWriter.getInstance(document, file);
						document.open();
						String logo = paths.getString(staffDataBean2.getSchoolID()+"_LOGO");
						document.add(Image.getInstance(new Object(){}.getClass().getClassLoader().getResource(logo)));
						document.add(new Paragraph(
								"------------------------------------------------------------------------------------------------------------------------"));

						float[] columnWidths = { 20f, 1f };
						Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 27);
						PdfPTable table = new PdfPTable(2); // .
						table.setWidthPercentage(100f);
						table.setWidths(columnWidths);

						PdfPCell cell1 = new PdfPCell(new Paragraph());
						cell1.setBorder(PdfPCell.NO_BORDER);
						PdfPCell cell2 = new PdfPCell(new Paragraph());
						cell2.setBorder(PdfPCell.NO_BORDER);
						table.setSpacingBefore(10f);
						table.setSpacingAfter(25f);

						PdfPTable nestedTable = new PdfPTable(4);
						nestedTable.setWidthPercentage(100f);
						PdfPCell nesCell1 = new PdfPCell(new Paragraph("First Name:"));
						PdfPCell nesCell2 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStaFirstName()));
						PdfPCell nesCell3 = new PdfPCell(new Paragraph("Last Name:"));
						PdfPCell nesCell4 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStaLastName()));
						nesCell1.setBorder(PdfPCell.NO_BORDER);
						nesCell2.setBorder(PdfPCell.NO_BORDER);
						nesCell3.setBorder(PdfPCell.NO_BORDER);
						nesCell4.setBorder(PdfPCell.NO_BORDER);
						nestedTable.addCell(nesCell1);
						nestedTable.addCell(nesCell2);
						nestedTable.addCell(nesCell3);
						nestedTable.addCell(nesCell4);
						nestedTable.setSpacingBefore(3f);
						nestedTable.setSpacingAfter(3f);
						cell1.addElement(nestedTable);

						PdfPTable nestedTable1 = new PdfPTable(4);
						nestedTable1.setWidthPercentage(100f);
						PdfPCell nesCell5 = new PdfPCell(new Paragraph("Staff Roll Number:"));
						PdfPCell nesCell6 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStaStaffID()));
						PdfPCell nesCell7 = new PdfPCell(new Paragraph("Phone number:"));
						PdfPCell nesCell8 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStaPhoneNo()));
						nesCell5.setBorder(PdfPCell.NO_BORDER);
						nesCell6.setBorder(PdfPCell.NO_BORDER);
						nesCell7.setBorder(PdfPCell.NO_BORDER);
						nesCell8.setBorder(PdfPCell.NO_BORDER);
						nestedTable1.addCell(nesCell5);
						nestedTable1.addCell(nesCell6);
						nestedTable1.addCell(nesCell7);
						nestedTable1.addCell(nesCell8);
						nestedTable1.setSpacingBefore(3f);
						nestedTable1.setSpacingAfter(3f);
						cell1.addElement(nestedTable1);

						PdfPTable nestedTable2 = new PdfPTable(4);
						nestedTable2.setWidthPercentage(100f);
						PdfPCell nesCell9 = new PdfPCell(new Paragraph("Email ID:"));
						PdfPCell nesCell10 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStaEmail()));
						PdfPCell nesCell11 = new PdfPCell(new Paragraph("Gender:"));
						PdfPCell nesCell12 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStaGender()));
						nesCell9.setBorder(PdfPCell.NO_BORDER);
						nesCell10.setBorder(PdfPCell.NO_BORDER);
						nesCell11.setBorder(PdfPCell.NO_BORDER);
						nesCell12.setBorder(PdfPCell.NO_BORDER);
						nestedTable2.addCell(nesCell9);
						nestedTable2.addCell(nesCell10);
						nestedTable2.addCell(nesCell11);
						nestedTable2.addCell(nesCell12);
						nestedTable2.setSpacingBefore(3f);
						nestedTable2.setSpacingAfter(3f);
						cell1.addElement(nestedTable2);

						PdfPTable nestedTable3 = new PdfPTable(4);
						nestedTable3.setWidthPercentage(100f);
						PdfPCell nesCell13 = new PdfPCell(new Paragraph("Photo:"));
						String tempdate = ft.format(ImageListPath.get(0).getStaPathDate());
						String imageLocation = paths.getString("sms.staff.photo") + tempdate + paths.getString("path_context") + ImageListPath.get(0).getStafilePath();
						logger.debug("path " + imageLocation);
						Image image = Image.getInstance(imageLocation);
						image.scaleAbsolute(100, 100);
						PdfPCell nesCell14 = new PdfPCell(image, false);
						PdfPCell nesCell15 = new PdfPCell(new Paragraph(""));
						PdfPCell nesCell16 = new PdfPCell(new Paragraph(""));
						nesCell13.setBorder(PdfPCell.NO_BORDER);
						nesCell14.setBorder(PdfPCell.NO_BORDER);
						nesCell15.setBorder(PdfPCell.NO_BORDER);
						nesCell16.setBorder(PdfPCell.NO_BORDER);
						nestedTable3.addCell(nesCell13);
						nestedTable3.addCell(nesCell14);
						nestedTable3.addCell(nesCell15);
						nestedTable3.addCell(nesCell16);
						nestedTable3.setSpacingBefore(3f);
						nestedTable3.setSpacingAfter(3f);
						cell1.addElement(nestedTable3);

						table.addCell(cell1);
						table.addCell(cell2);
						document.add(table);

						document.close();
						file.close();

						logger.debug("-----rraagguullan-----Done");
						logger.debug("Done");
						status = "Success";
					}
				
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
				logger.warn("Inside Exception",e);
			}
			return status;
		}
		
		public static String staffupdatesendEmail(StaffDataBean staffDataBean2, List<StaffDataBean> imageListPath2)
				throws AddressException {
			String status = "Fail";

			try {
				String mail = staffDataBean2.getStaEmail();
				String to = mail;
				Properties prop = new Properties();
				prop.put("mail.smtp.host", "smtp.gmail.com");
				prop.put("mail.smtp.socketFactory.port", "465");
				prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				prop.put("mail.smtp.starttls.enable", "true");
				prop.put("mail.smtp.auth", "true");
				prop.put("mail.smtp.port", "587");
				prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
				String body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
						+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
						+ "<h3>Dear " + staffDataBean2.getStaStaffID() + ",</h3>"
						+ "<p>Welcome to join  our Student Monitoring System." + "Your updated Profile" + "</p>"
						+ "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>"
						+ "</footer>" + "</body></html>";

				Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
					}
				});

				// compose message
				try {
					/*
					 * session = Session.getInstance(prop, this);// this is the
					 * local variable not the instance one
					 */ MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
					message.setSubject("SMS Confirmation-" + staffDataBean2.getStaStaffID());
					BodyPart messageBodyPart1 = new MimeBodyPart();
					messageBodyPart1.setContent(body1, "text/html");

					MimeBodyPart messageBodyPart2 = new MimeBodyPart();
					String filename = "/home/ec2-user/File_Sms/Files/PDF/Staff" + ft.format(now) + "/" + staffDataBean2.getStaStaffID()
							+ ".pdf";// change accordingly
					DataSource source = new FileDataSource(filename);
					messageBodyPart2.setDataHandler(new DataHandler(source));
					messageBodyPart2.setFileName(staffDataBean2.getStaStaffID() + ".pdf");

					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart1);
					multipart.addBodyPart(messageBodyPart2);
					message.setContent(multipart);

					Transport.send(message);
					logger.debug("--message sent successfully--");
					logger.debug("message sent successfully");
					status = "Success";

				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}

			} catch (Exception e) {
				logger.warn("Inside Exception",e);
				logger.warn("Exception -->"+e.getMessage());
			}
			return status;
		}
		
		
		//librarian
		
		public static String libupdategeneratePdf(LibrarianDataBean librarianDataBean,List<LibrarianDataBean> ImageListPath) {
			String status = "fail";
			try {
					logger.debug("imagelist size --- " + ImageListPath.size());
					if (ImageListPath.size() > 0) {
						// Create Directory
						File files = new File(paths.getString("sms.librarian.pdf") + ft.format(now));
						if (!files.exists()) {
							files.mkdirs();
						} else {
							logger.debug("Alreday Found");
						}
						// Write PDF File
						OutputStream file = new FileOutputStream(
								new File(files + paths.getString("path_context").toString() + librarianDataBean.getLibID() + ".pdf"));
						librarianDataBean.setLibfilePath(files + paths.getString("path_context").toString() + librarianDataBean.getLibID() + ".pdf");
						Document document = new Document(PageSize.A4, 50, 50, 10, 50);
						PdfWriter.getInstance(document, file);
						document.open();
						String logo = paths.getString(librarianDataBean.getSchoolID()+"_LOGO");
						document.add(Image.getInstance(new Object(){}.getClass().getClassLoader().getResource(logo)));
						document.add(new Paragraph(
								"------------------------------------------------------------------------------------------------------------------------"));

						float[] columnWidths = { 20f, 1f };
						Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 27);
						PdfPTable table = new PdfPTable(2); // .
						table.setWidthPercentage(100f);
						table.setWidths(columnWidths);

						PdfPCell cell1 = new PdfPCell(new Paragraph());
						cell1.setBorder(PdfPCell.NO_BORDER);
						PdfPCell cell2 = new PdfPCell(new Paragraph());
						cell2.setBorder(PdfPCell.NO_BORDER);
						table.setSpacingBefore(10f);
						table.setSpacingAfter(25f);

						PdfPTable nestedTable = new PdfPTable(4);
						nestedTable.setWidthPercentage(100f);
						PdfPCell nesCell1 = new PdfPCell(new Paragraph("First Name:"));
						PdfPCell nesCell2 = new PdfPCell(new Paragraph(ImageListPath.get(0).getLibFirstName()));
						PdfPCell nesCell3 = new PdfPCell(new Paragraph("Last Name:"));
						PdfPCell nesCell4 = new PdfPCell(new Paragraph(ImageListPath.get(0).getLibLastName()));
						nesCell1.setBorder(PdfPCell.NO_BORDER);
						nesCell2.setBorder(PdfPCell.NO_BORDER);
						nesCell3.setBorder(PdfPCell.NO_BORDER);
						nesCell4.setBorder(PdfPCell.NO_BORDER);
						nestedTable.addCell(nesCell1);
						nestedTable.addCell(nesCell2);
						nestedTable.addCell(nesCell3);
						nestedTable.addCell(nesCell4);
						nestedTable.setSpacingBefore(3f);
						nestedTable.setSpacingAfter(3f);
						cell1.addElement(nestedTable);

						PdfPTable nestedTable1 = new PdfPTable(4);
						nestedTable1.setWidthPercentage(100f);
						PdfPCell nesCell5 = new PdfPCell(new Paragraph("Librarian ID:"));
						PdfPCell nesCell6 = new PdfPCell(new Paragraph(ImageListPath.get(0).getLibID()));
						PdfPCell nesCell7 = new PdfPCell(new Paragraph("Email:"));
						PdfPCell nesCell8 = new PdfPCell(new Paragraph(ImageListPath.get(0).getLibEmail()));
						nesCell5.setBorder(PdfPCell.NO_BORDER);
						nesCell6.setBorder(PdfPCell.NO_BORDER);
						nesCell7.setBorder(PdfPCell.NO_BORDER);
						nesCell8.setBorder(PdfPCell.NO_BORDER);
						nestedTable1.addCell(nesCell5);
						nestedTable1.addCell(nesCell6);
						nestedTable1.addCell(nesCell7);
						nestedTable1.addCell(nesCell8);
						nestedTable1.setSpacingBefore(3f);
						nestedTable1.setSpacingAfter(3f);
						cell1.addElement(nestedTable1);

						PdfPTable nestedTable2 = new PdfPTable(4);
						nestedTable2.setWidthPercentage(100f);
						PdfPCell nesCell9 = new PdfPCell(new Paragraph("Phone Number:"));
						PdfPCell nesCell10 = new PdfPCell(new Paragraph(ImageListPath.get(0).getLibPhoneNo()));
						PdfPCell nesCell11 = new PdfPCell(new Paragraph("Gender:"));
						PdfPCell nesCell12 = new PdfPCell(new Paragraph(ImageListPath.get(0).getLibGender()));
						nesCell9.setBorder(PdfPCell.NO_BORDER);
						nesCell10.setBorder(PdfPCell.NO_BORDER);
						nesCell11.setBorder(PdfPCell.NO_BORDER);
						nesCell12.setBorder(PdfPCell.NO_BORDER);
						nestedTable2.addCell(nesCell9);
						nestedTable2.addCell(nesCell10);
						nestedTable2.addCell(nesCell11);
						nestedTable2.addCell(nesCell12);
						nestedTable2.setSpacingBefore(3f);
						nestedTable2.setSpacingAfter(3f);
						cell1.addElement(nestedTable2);

						PdfPTable nestedTable3 = new PdfPTable(2);
						nestedTable3.setWidthPercentage(100f);
						PdfPCell nesCell13 = new PdfPCell(new Paragraph("Photo:"));
						String tempdate = ft.format(ImageListPath.get(0).getPathDate());
						String imageLocation = paths.getString("sms.librarian.photo") + tempdate + paths.getString("path_context")+ ImageListPath.get(0).getPath();
						Image image = Image.getInstance(imageLocation);
						logger.debug(image);
						image.scaleAbsolute(100, 100);
						PdfPCell nesCell14 = new PdfPCell(image, false);
						nesCell13.setBorder(PdfPCell.NO_BORDER);
						nesCell14.setBorder(PdfPCell.NO_BORDER);
						nestedTable3.addCell(nesCell13);
						nestedTable3.addCell(nesCell14);
						nestedTable3.setSpacingBefore(3f);
						nestedTable3.setSpacingAfter(3f);
						cell1.addElement(nestedTable3);

						table.addCell(cell1);
						table.addCell(cell2);
						document.add(table);
						document.close();
						file.close();
						logger.debug("Done");
						status = "Success";
					}
				
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
			return status;
		}

		public static String libupdatesendEmail(LibrarianDataBean librarianDataBean, List<LibrarianDataBean> imageListPath2)
				throws AddressException {
			String status = "Fail";
			String mail = librarianDataBean.getLibEmail();
			String to = "" + mail;
			Properties prop = new Properties();
			prop.put("mail.smtp.host", "smtp.gmail.com");
			prop.put("mail.smtp.socketFactory.port", "465");
			prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			prop.put("mail.smtp.starttls.enable", "true");
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.port", "587");
			prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
			String body1 = "<htm><head></heade><body>"
					+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
					+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
					+ "<h3>Dear " + librarianDataBean.getLibID() + ",</h3>"
					+ "<p>Welcome on-board into NRG family. Your profile is updated and the details enclosed with is mail.</p>"

					+ "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>"
					+ "</body></html>";

			Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
				}
			});
			// compose message
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				/* message.addRecipients(Message.RecipientType.CC, myCcList); */
				message.setSubject("SMS Confirmation-" + librarianDataBean.getLibID());
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");

				MimeBodyPart messageBodyPart2 = new MimeBodyPart();
				String filename = "/home/ec2-user/File_Sms/Files/PDF/Librarian" + ft.format(now) + "/" + librarianDataBean.getLibID() + ".pdf";// change
																															// accordingly
				DataSource source = new FileDataSource(filename);
				messageBodyPart2.setDataHandler(new DataHandler(source));
				messageBodyPart2.setFileName(librarianDataBean.getLibID() + ".pdf");

				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				multipart.addBodyPart(messageBodyPart2);
				message.setContent(multipart);

				Transport.send(message);

				logger.info("message sent successfully");
				status = "Success";

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
			return status;
		}
		
		public static String reportcardsendEmail(ReportCardDataBean reportCardDataBean) {
			  String status = "Fail";

			  try {
			   String to = reportCardDataBean.getMailid();
			   Properties prop = new Properties();
			   prop.put("mail.smtp.host", "smtp.gmail.com");
			   prop.put("mail.smtp.socketFactory.port", "465");
			   prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			   prop.put("mail.smtp.starttls.enable", "true");
			   prop.put("mail.smtp.auth", "true");
			   prop.put("mail.smtp.port", "587");
			   prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

			   String body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
						+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
			     + "<h3>Dear Parents/Students,</h3>" + "<p>We have listed the"
			     + reportCardDataBean.getExamMarkTitle() + " Marks for " + reportCardDataBean.getMarkSubTitle()
			     + " Subject."
			     + "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
			     + "<tr>" + "<td> Student ID" + "</td>" + "<td> Name" + "</td>" + "<td> Mark" + "</td>"
			     + "<td> Grade " + "</td>" + "<td> Result " + "</td>" + "</tr>" + "<tr>" + "<td>"
			     + reportCardDataBean.getRollNo() + "</td>" + "<td>" + reportCardDataBean.getName() + "</td>"
			     + "<td>" + reportCardDataBean.getMark() + "</td>" + "<td>" + reportCardDataBean.getGrade() + "</td>"
			     + "<td>" + reportCardDataBean.getResultStatus() + "</td>" + "</tr>" + "</table>" + "<footer>"
			     + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>"
			     + "</body></html>";

			   Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			    protected PasswordAuthentication getPasswordAuthentication() {
			     return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
			    }
			   });
			   // compose message
			   try {
			    MimeMessage message = new MimeMessage(session);
			    message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
			    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			    message.setSubject(
			      reportCardDataBean.getExamMarkTitle() + " Marks for " + reportCardDataBean.getMarkSubTitle());
			    BodyPart messageBodyPart1 = new MimeBodyPart();
			    messageBodyPart1.setContent(body1, "text/html");
			    Multipart multipart = new MimeMultipart();
			    multipart.addBodyPart(messageBodyPart1);
			    message.setContent(multipart);

			    Transport.send(message);

			    logger.debug("message sent successfully");
			    status = "Success";

			   } catch (MessagingException e) {
			    throw new RuntimeException(e);
			   }

			  } catch (Exception e) {
			   logger.warn("Exception -->"+e.getMessage());
			  }
			  return status;
			 }
		// udhaya
		
		public static String generatePdfBookshop(StaffDataBean staffDataBean, List<StaffDataBean> imageListPath, String personID) {
			 String status = "Fail";
			 try {
			  if (personID != null) {
			   if (imageListPath.size() > 0) {
			    // Create Directory
			    File files = new File(paths.getString("sms.bookshop.pdf").toString() + ft.format(now));
			    if (!files.exists()) {
			     files.mkdirs();
			    } else {

			     logger.debug("Alreday Found");
			    }
			    // Write PDF File
			    OutputStream file = new FileOutputStream(
			      new File(files + paths.getString("path_context").toString() + staffDataBean.getStaStaffID() + ".pdf"));
			    staffDataBean.setStafilePath((files + paths.getString("path_context").toString() + staffDataBean.getStaStaffID() + ".pdf"));
			    Document document = new Document(PageSize.A4, 50, 50, 10, 50);
			    PdfWriter.getInstance(document, file);
			    document.open();
			    String logo = paths.getString(staffDataBean.getSchoolID()+"_LOGO");
				document.add(Image.getInstance(new Object(){}.getClass().getClassLoader().getResource(logo)));
			    document.add(new Paragraph(
			      "------------------------------------------------------------------------------------------------------------------------"));

			    float[] columnWidths = { 20f, 1f };
			    Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 27);
			    PdfPTable table = new PdfPTable(2); // .
			    table.setWidthPercentage(100f);
			    table.setWidths(columnWidths);

			    PdfPCell cell1 = new PdfPCell(new Paragraph());
			    cell1.setBorder(PdfPCell.NO_BORDER);
			    PdfPCell cell2 = new PdfPCell(new Paragraph());
			    cell2.setBorder(PdfPCell.NO_BORDER);
			    table.setSpacingBefore(10f);
			    table.setSpacingAfter(25f);

			    PdfPTable nestedTable = new PdfPTable(4);
			    nestedTable.setWidthPercentage(100f);
			    PdfPCell nesCell1 = new PdfPCell(new Paragraph("First Name:"));
			    PdfPCell nesCell2 = new PdfPCell(new Paragraph(imageListPath.get(0).getStaFirstName()));
			    PdfPCell nesCell3 = new PdfPCell(new Paragraph("Last Name:"));
			    PdfPCell nesCell4 = new PdfPCell(new Paragraph(imageListPath.get(0).getStaLastName()));
			    nesCell1.setBorder(PdfPCell.NO_BORDER);
			    nesCell2.setBorder(PdfPCell.NO_BORDER);
			    nesCell3.setBorder(PdfPCell.NO_BORDER);
			    nesCell4.setBorder(PdfPCell.NO_BORDER);
			    nestedTable.addCell(nesCell1);
			    nestedTable.addCell(nesCell2);
			    nestedTable.addCell(nesCell3);
			    nestedTable.addCell(nesCell4);
			    nestedTable.setSpacingBefore(3f);
			    nestedTable.setSpacingAfter(3f);
			    cell1.addElement(nestedTable);

			    PdfPTable nestedTable1 = new PdfPTable(4);
			    nestedTable1.setWidthPercentage(100f);
			    PdfPCell nesCell5 = new PdfPCell(new Paragraph("Bookshop Roll Number:"));
			    PdfPCell nesCell6 = new PdfPCell(new Paragraph(imageListPath.get(0).getStaStaffID()));
			    PdfPCell nesCell7 = new PdfPCell(new Paragraph("Phone number:"));
			    PdfPCell nesCell8 = new PdfPCell(new Paragraph(imageListPath.get(0).getStaPhoneNo()));
			    nesCell5.setBorder(PdfPCell.NO_BORDER);
			    nesCell6.setBorder(PdfPCell.NO_BORDER);
			    nesCell7.setBorder(PdfPCell.NO_BORDER);
			    nesCell8.setBorder(PdfPCell.NO_BORDER);
			    nestedTable1.addCell(nesCell5);
			    nestedTable1.addCell(nesCell6);
			    nestedTable1.addCell(nesCell7);
			    nestedTable1.addCell(nesCell8);
			    nestedTable1.setSpacingBefore(3f);
			    nestedTable1.setSpacingAfter(3f);
			    cell1.addElement(nestedTable1);

			    PdfPTable nestedTable2 = new PdfPTable(4);
			    nestedTable2.setWidthPercentage(100f);
			    PdfPCell nesCell9 = new PdfPCell(new Paragraph("Email ID:"));
			    PdfPCell nesCell10 = new PdfPCell(new Paragraph(imageListPath.get(0).getStaEmail()));
			    PdfPCell nesCell11 = new PdfPCell(new Paragraph("Gender:"));
			    PdfPCell nesCell12 = new PdfPCell(new Paragraph(imageListPath.get(0).getStaGender()));
			    nesCell9.setBorder(PdfPCell.NO_BORDER);
			    nesCell10.setBorder(PdfPCell.NO_BORDER);
			    nesCell11.setBorder(PdfPCell.NO_BORDER);
			    nesCell12.setBorder(PdfPCell.NO_BORDER);
			    nestedTable2.addCell(nesCell9);
			    nestedTable2.addCell(nesCell10);
			    nestedTable2.addCell(nesCell11);
			    nestedTable2.addCell(nesCell12);
			    nestedTable2.setSpacingBefore(3f);
			    nestedTable2.setSpacingAfter(3f);
			    cell1.addElement(nestedTable2);
			    
			    PdfPTable nestedTable3 = new PdfPTable(4);
				nestedTable3.setWidthPercentage(100f);
				PdfPCell nesCell13 = new PdfPCell(new Paragraph("Photo:"));
				String tempdate = ft.format(imageListPath.get(0).getStaPathDate());
				String imageLocation = paths.getString("sms.bookshop.photo").toString()+ tempdate + "/" + imageListPath.get(0).getStafilePath();
				logger.debug("path " + imageLocation);
				Image image = Image.getInstance(imageLocation);
				image.scaleAbsolute(100, 100);			
				PdfPCell nesCell14 = new PdfPCell(image, false);
				PdfPCell nesCell15 = new PdfPCell(new Paragraph(""));
				PdfPCell nesCell16 = new PdfPCell(new Paragraph(""));
				nesCell13.setBorder(PdfPCell.NO_BORDER);
				nesCell14.setBorder(PdfPCell.NO_BORDER);
				nesCell15.setBorder(PdfPCell.NO_BORDER);
				nesCell16.setBorder(PdfPCell.NO_BORDER);
				nestedTable3.addCell(nesCell13);
				nestedTable3.addCell(nesCell14);
				nestedTable3.addCell(nesCell15);
				nestedTable3.addCell(nesCell16);
				nestedTable3.setSpacingBefore(3f);
				nestedTable3.setSpacingAfter(3f);
				cell1.addElement(nestedTable3);

			    
			    table.addCell(cell1);
			    table.addCell(cell2);
			    document.add(table);

			    document.close();
			    file.close();
			    logger.debug("Done");
			    status = "Success";
			   }
			  }
			 } catch (Exception e) {
			  logger.warn("Exception -->"+e.getMessage());
			  logger.warn("Inside Exception",e);
			 }
			 return status;
			}
		
		public static String generatePdfBookShop(String personID,List<StaffDataBean> imageListPath, StaffDataBean staffDataBean) {

			String status = "fail";
			try {
				
				if (personID != null) {
					if (imageListPath.size() > 0) {
						// Create Directory
						File files = new File(paths.getString("sms.bookshop.pdf").toString() + ft.format(now));
						if (!files.exists()) {
							files.mkdirs();
						} else {

							logger.debug("Alreday Found");
						}
						// Write PDF File
						OutputStream file = new FileOutputStream(
								new File(files + paths.getString("path_context").toString() + staffDataBean.getStaStaffID() + ".pdf"));
						staffDataBean.setStafilePath((files + paths.getString("path_context").toString() + staffDataBean.getStaStaffID() + ".pdf"));
						Document document = new Document(PageSize.A4, 50, 50, 10, 50);
						PdfWriter.getInstance(document, file);
						document.open();
						String logo = paths.getString(staffDataBean.getSchoolID()+"_LOGO");
						document.add(Image.getInstance(new Object(){}.getClass().getClassLoader().getResource(logo)));
						document.add(new Paragraph(
								"------------------------------------------------------------------------------------------------------------------------"));

						float[] columnWidths = { 20f, 1f };
						Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 27);
						PdfPTable table = new PdfPTable(2); // .
						table.setWidthPercentage(100f);
						table.setWidths(columnWidths);

						PdfPCell cell1 = new PdfPCell(new Paragraph());
						cell1.setBorder(PdfPCell.NO_BORDER);
						PdfPCell cell2 = new PdfPCell(new Paragraph());
						cell2.setBorder(PdfPCell.NO_BORDER);
						table.setSpacingBefore(10f);
						table.setSpacingAfter(25f);

						PdfPTable nestedTable = new PdfPTable(4);
						nestedTable.setWidthPercentage(100f);
						PdfPCell nesCell1 = new PdfPCell(new Paragraph("First Name:"));
						PdfPCell nesCell2 = new PdfPCell(new Paragraph(imageListPath.get(0).getStaFirstName()));
						PdfPCell nesCell3 = new PdfPCell(new Paragraph("Last Name:"));
						PdfPCell nesCell4 = new PdfPCell(new Paragraph(imageListPath.get(0).getStaLastName()));
						nesCell1.setBorder(PdfPCell.NO_BORDER);
						nesCell2.setBorder(PdfPCell.NO_BORDER);
						nesCell3.setBorder(PdfPCell.NO_BORDER);
						nesCell4.setBorder(PdfPCell.NO_BORDER);
						nestedTable.addCell(nesCell1);
						nestedTable.addCell(nesCell2);
						nestedTable.addCell(nesCell3);
						nestedTable.addCell(nesCell4);
						nestedTable.setSpacingBefore(3f);
						nestedTable.setSpacingAfter(3f);
						cell1.addElement(nestedTable);

						PdfPTable nestedTable1 = new PdfPTable(4);
						nestedTable1.setWidthPercentage(100f);
						PdfPCell nesCell5 = new PdfPCell(new Paragraph("Staff Roll Number:"));
						PdfPCell nesCell6 = new PdfPCell(new Paragraph(imageListPath.get(0).getStaStaffID()));
						PdfPCell nesCell7 = new PdfPCell(new Paragraph("Phone number:"));
						PdfPCell nesCell8 = new PdfPCell(new Paragraph(imageListPath.get(0).getStaPhoneNo()));
						nesCell5.setBorder(PdfPCell.NO_BORDER);
						nesCell6.setBorder(PdfPCell.NO_BORDER);
						nesCell7.setBorder(PdfPCell.NO_BORDER);
						nesCell8.setBorder(PdfPCell.NO_BORDER);
						nestedTable1.addCell(nesCell5);
						nestedTable1.addCell(nesCell6);
						nestedTable1.addCell(nesCell7);
						nestedTable1.addCell(nesCell8);
						nestedTable1.setSpacingBefore(3f);
						nestedTable1.setSpacingAfter(3f);
						cell1.addElement(nestedTable1);

						PdfPTable nestedTable2 = new PdfPTable(4);
						nestedTable2.setWidthPercentage(100f);
						PdfPCell nesCell9 = new PdfPCell(new Paragraph("Email ID:"));
						PdfPCell nesCell10 = new PdfPCell(new Paragraph(imageListPath.get(0).getStaEmail()));
						PdfPCell nesCell11 = new PdfPCell(new Paragraph("Gender:"));
						PdfPCell nesCell12 = new PdfPCell(new Paragraph(imageListPath.get(0).getStaGender()));
						nesCell9.setBorder(PdfPCell.NO_BORDER);
						nesCell10.setBorder(PdfPCell.NO_BORDER);
						nesCell11.setBorder(PdfPCell.NO_BORDER);
						nesCell12.setBorder(PdfPCell.NO_BORDER);
						nestedTable2.addCell(nesCell9);
						nestedTable2.addCell(nesCell10);
						nestedTable2.addCell(nesCell11);
						nestedTable2.addCell(nesCell12);
						nestedTable2.setSpacingBefore(3f);
						nestedTable2.setSpacingAfter(3f);
						cell1.addElement(nestedTable2);

						PdfPTable nestedTable3 = new PdfPTable(4);
						nestedTable3.setWidthPercentage(100f);
						PdfPCell nesCell13 = new PdfPCell(new Paragraph("Photo:"));
						String tempdate = ft.format(imageListPath.get(0).getStaPathDate());
						String imageLocation = paths.getString("sms.bookshop.photo").toString()+ tempdate + paths.getString("path_context") + imageListPath.get(0).getStafilePath();
						logger.debug("path " + imageLocation);
						Image image = Image.getInstance(imageLocation);
						image.scaleAbsolute(100, 100);
						PdfPCell nesCell14 = new PdfPCell(image, false);
						PdfPCell nesCell15 = new PdfPCell(new Paragraph(""));
						PdfPCell nesCell16 = new PdfPCell(new Paragraph(""));
						nesCell13.setBorder(PdfPCell.NO_BORDER);
						nesCell14.setBorder(PdfPCell.NO_BORDER);
						nesCell15.setBorder(PdfPCell.NO_BORDER);
						nesCell16.setBorder(PdfPCell.NO_BORDER);
						nestedTable3.addCell(nesCell13);
						nestedTable3.addCell(nesCell14);
						nestedTable3.addCell(nesCell15);
						nestedTable3.addCell(nesCell16);
						nestedTable3.setSpacingBefore(3f);
						nestedTable3.setSpacingAfter(3f);
						cell1.addElement(nestedTable3);

						table.addCell(cell1);
						table.addCell(cell2);
						document.add(table);

						document.close();
						file.close();
						logger.debug("Done");
						status = "Success";
					}
				}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
				logger.warn("Inside Exception",e);
			}
			return status;
		}
}

