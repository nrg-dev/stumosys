package com.stumosys.managedBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
//import java.util.Properties;
import java.util.ResourceBundle;

/*import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
*/import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
/*import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
*/
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

//import com.itextpdf.text.Document;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.Image;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
import com.stumosys.controler.SmsController;
import com.stumosys.domain.LibrarianDataBean;
//import com.stumosys.domain.StudentDataBean;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name = "librarianMB")
@RequestScoped

public class LibrarianMB {
	LibrarianDataBean librarianDataBean = new LibrarianDataBean();
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	private static Logger logger = Logger.getLogger(LibrarianMB.class);
	//private List<LibrarianDataBean> ImageListPath = null;
	UploadedFile file;
	Date now = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	private boolean lflag = false;
	private boolean boxflag;
	public String status;
	private boolean valid = true;
	private List<String> menulist=null;
	private boolean menuflag=false;

	public List<String> getMenulist() {
		return menulist;
	}

	public void setMenulist(List<String> menulist) {
		this.menulist = menulist;
	}

	public boolean isMenuflag() {
		return menuflag;
	}

	public void setMenuflag(boolean menuflag) {
		this.menuflag = menuflag;
	}

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

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isBoxflag() {
		return boxflag;
	}

	public void setBoxflag(boolean boxflag) {
		this.boxflag = boxflag;
	}

	public boolean isLflag() {
		return lflag;
	}

	public void setLflag(boolean lflag) {
		this.lflag = lflag;
	}

	public FacesContext getContext() {
		return context;
	}

	public void setContext(FacesContext context) {
		this.context = context;
	}

	public ResourceBundle getText() {
		return text;
	}

	public void setText(ResourceBundle text) {
		this.text = text;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		LibrarianMB.logger = logger;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public LibrarianDataBean getLibrarianDataBean() {
		return librarianDataBean;
	}

	public void setLibrarianDataBean(LibrarianDataBean librarianDataBean) {
		this.librarianDataBean = librarianDataBean;
	}

	public String loadLibrarianPage() {
		logger.info("-----------Inside loadLibrarianPage method()----------------");
		librarianDataBean.setLibEmail("");
		librarianDataBean.setLibFirstName("");
		librarianDataBean.setLibGender("");
		librarianDataBean.setLibID("");
		librarianDataBean.setLibLastName("");
		librarianDataBean.setLibLibrarianPhoto(null);
		librarianDataBean.setLibPhoneNo("");
		boxflag = false;
		setMenuflag(false);
		librarianDataBean.setMenus(null);
		try {
			SmsController controller = null;
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				menulist=new ArrayList<String>();
				menulist=controller.getmenus();
				String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
				int schoolid=Integer.parseInt(schoolID);
				if(schoolid==1 || schoolid==2||schoolid==3||schoolid==4||schoolid==5||schoolid==6){
					setMenuflag(false);
				}else{
					setMenuflag(true);
				}
				status = controller.getRoll(personID);
				logger.info("-----status----" + status);
				if (status.equalsIgnoreCase("Admin")) {
					logger.info("inside admin --- ");
					setLflag(true);
				}
				if (status.equalsIgnoreCase("Student") || status.equalsIgnoreCase("Parent")
						|| status.equalsIgnoreCase("Teacher") || status.equalsIgnoreCase("Librarian")) {
					setLflag(false);
				}
				return "LibrarianRegistration";
			}
		} catch (Exception e) {
			logger.warn("----------Inside addStudentPage Method Exception Calling-----" + e);

		}
		return "";
	}

	public String dummyAction(FileUploadEvent event) throws IOException {
		logger.info("-----------Inside dummyAction method()----------------");
		this.file = event.getFile();
		librarianDataBean.setLibLibrarianPhoto(event.getFile());
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);
		return "";
	}

	public String libReg() {
		logger.info("-----------Inside libReg method()----------------");
		try {
			if (validate(true)) {
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");

				if (personID != null) {
					RequestContext reqcontextt = RequestContext.getCurrentInstance();
					reqcontextt.execute("librarianBlk();");

				}
			}

		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return "";
	}

	private boolean validate(boolean flag) {
		logger.info("-----------Inside validate method()----------------");
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (!StringUtils.isEmpty(librarianDataBean.getLibEmail())) {
			if (!CommonValidate.validateEmail(librarianDataBean.getLibEmail())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("lEmail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.librarian.vemail")));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(librarianDataBean.getLibFirstName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("lFName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.librarian.fname")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(librarianDataBean.getLibFirstName())) {
			if (!CommonValidate.validateName(librarianDataBean.getLibFirstName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("lFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.librarian.vfname")));
				}
				valid = false;
			} else if (librarianDataBean.getLibFirstName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("lFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.librarian.vfname")));
				}
				valid = false;
			}
		}
		
			if(!StringUtils.isEmpty(librarianDataBean.getCountryCode())){
				if (StringUtils.isEmpty(librarianDataBean.getLibPhoneNo())) {
					fieldName = CommonValidate.findComponentInRoot("lPhoneNo").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the Phone Number"));
					valid=false;
				}
				else if (!StringUtils.isEmpty(librarianDataBean.getLibPhoneNo())) {
				if (!CommonValidate.validateNumberOnly(librarianDataBean.getLibPhoneNo())) {
					if (flag) {
	
						fieldName = CommonValidate.findComponentInRoot("lPhoneNo").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(text.getString("sms.librarian.vphno")));
					}
					valid = false;
					}else if(!CommonValidate.countryCodeValidation(librarianDataBean.getLibPhoneNo(),librarianDataBean.getCountryCode())){
						fieldName = CommonValidate.findComponentInRoot("lPhoneNo").getClientId(fc);
						if (librarianDataBean.getCountryCode().equalsIgnoreCase("+91")) {
							fc.addMessage(fieldName, new FacesMessage(text.getString("sms.india.phonevalidation")));
						}
						else if (librarianDataBean.getCountryCode().equalsIgnoreCase("+971")) {
							fc.addMessage(fieldName, new FacesMessage(text.getString("sms.uae.phonevalidation")));
						}
						else if (librarianDataBean.getCountryCode().equalsIgnoreCase("+62")) {
							fc.addMessage(fieldName, new FacesMessage(text.getString("sms.indonesia.phonevalidation")));
						}
						else if (librarianDataBean.getCountryCode().equalsIgnoreCase("+60")) {
							fc.addMessage(fieldName, new FacesMessage(text.getString("sms.malaysia.phonevalidation")));
						}
						
						valid = false;
					}
				}
			}
			if (StringUtils.isEmpty(librarianDataBean.getLibID())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("lID").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.librarian.libID")));
				}
				valid = false;
			} else if (librarianDataBean.getLibID().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("lID").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.librarian.vlibID")));
				}
				valid = false;
			}
			
			if(!StringUtils.isEmpty(librarianDataBean.getLibPhoneNo())){
				if(StringUtils.isEmpty(librarianDataBean.getCountryCode())){
					fieldName = CommonValidate.findComponentInRoot("countrycode").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose the Countrycode"));
					valid=false;
				}
			}
		return valid;
	}

		public String submit() {
		logger.info("-----------Inside submit method()----------------");
		String status = "";
		String fieldName;
		SmsController controller = null;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			logger.debug("chky "+librarianDataBean.getLibID());
			String status1 = controller.checkLibrarianID(librarianDataBean, librarianDataBean.getLibID());

			logger.debug("personID" + personID);
			if (personID != null) {
				 String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
				String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
				if(librarianDataBean.getLibLibrarianPhoto()==null){
					librarianDataBean.setLibfilePath("");
				}else{
					String s = librarianDataBean.getLibLibrarianPhoto().getContentType();
					String type = s.substring(s.lastIndexOf("/") + 1);
					copyFile(librarianDataBean.getLibID(), librarianDataBean.getLibLibrarianPhoto().getInputstream(), type);
					String path = ft.format(now) + "/" + librarianDataBean.getLibID() + "." + type;
					librarianDataBean.setLibfilePath(path);
				}
				logger.debug(librarianDataBean.getLibfilePath());
				
				status = controller.insertLibrarian(personID, librarianDataBean);
				if (status.equalsIgnoreCase("Success")) {
					logger.debug("MB PWD" + librarianDataBean.getLibSecurePassword());
					logger.debug("MB Name" + librarianDataBean.getLibUsername());
					//String pdfStatus = generatePdf(librarianDataBean);
					MailSendJDBC.librarianInsert(librarianDataBean,schoolName,schoolID);
					
							FacesMessage message = new FacesMessage("Succesful",
									librarianDataBean.getLibID() + " is Inserted .");
							FacesContext.getCurrentInstance().addMessage(null, message);
							librarianDataBean.setLibEmail("");
							librarianDataBean.setLibFirstName("");
							librarianDataBean.setLibGender("");
							librarianDataBean.setLibID("");
							librarianDataBean.setLibLastName("");
							librarianDataBean.setLibLibrarianPhoto(null);
							librarianDataBean.setLibPhoneNo("");
							librarianDataBean.setCountryCode("");
							RequestContext reqcontext = RequestContext.getCurrentInstance();
							reqcontext.execute("PF('librarianblockUI').hide();");
							reqcontext.execute("PF('librarianRegDialog').show();");
							setBoxflag(true);

						//}
					} else if (status1.equalsIgnoreCase("Exsist")) {
						logger.debug("inside els Exsist");
						fieldName = CommonValidate.findComponentInRoot("lID").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Already Exsist"));
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('librarianblockUI').hide();");
						reqcontext.execute("PF('librarianRegDialog').hide();");
						reqcontext.execute("PF('librarianExsistDialog').show();");
						setBoxflag(false);
					} else {
						FacesMessage message = new FacesMessage(text.getString("sms.librarian.network"));
						FacesContext.getCurrentInstance().addMessage(null, message);
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('librarianblockUI').hide();");
						reqcontext.execute("PF('librarianRegDialog').hide();");
						setBoxflag(false);
					}

					valid = false;
					return "";
				}
			/*}*/
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return "";

	}

	public String reset() {
		logger.info("-----------Inside reset method()----------------");
		//logger.debug("Inside Reset Method Calling...");
		librarianDataBean.setLibEmail("");
		librarianDataBean.setLibFirstName("");
		librarianDataBean.setLibGender("");
		librarianDataBean.setLibID("");
		librarianDataBean.setLibLastName("");
		librarianDataBean.setLibLibrarianPhoto(null);
		librarianDataBean.setLibPhoneNo("");
		librarianDataBean.setCountryCode("");
		librarianDataBean.setLibLibrarianPhoto(null);
		setBoxflag(false);
		return "";
	}

	private void copyFile(String fileName, InputStream inputstream, String n) {
		logger.info("-----------Inside copyFile method()----------------");
		try {
			// Create Directory
			File files = new File(paths.getString("sms.librarian.photo") + ft.format(now));
			if (!files.exists()) {
				files.mkdirs();
			} else {
				logger.debug("Alreday Found");
			}
			OutputStream out = new FileOutputStream(new File(files + paths.getString("path_context").toString() + fileName + "." + n));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputstream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			inputstream.close();
			out.flush();
			out.close();

			logger.debug("New file created!");
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}

	}

	public String returnToHome() {
		logger.info("-----------Inside returnToHome method()----------------");
		//logger.debug("Calling");
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				return "SuccessHome";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";

	}

	/*private String generatePdf(LibrarianDataBean librarianDataBean) {
		String status = "fail";
		SmsController controller = null;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			
			if (personID != null) {
				ImageListPath = new ArrayList<LibrarianDataBean>();
				ImageListPath = controller.getImagePath5(personID, librarianDataBean.getLibID());
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
							new File(files + paths.getString("path_context").toString() + librarianDataBean.getLibUsername() + ".pdf"));
					librarianDataBean.setLibfilePath(files +paths.getString("path_context").toString()+ librarianDataBean.getLibUsername() + ".pdf");
					Document document = new Document(PageSize.A4, 50, 50, 10, 50);
					PdfWriter.getInstance(document, file);
					document.open();
					String logo ="";
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						logo = paths.getString(schoolID+"_LOGO");
						document.add(Image.getInstance(getClass().getClassLoader().getResource(logo)));
					}else{
						logo = librarianDataBean.getSchoolLogo();
						document.add(Image.getInstance(logo));
					}
					document.add(new Paragraph(
							"------------------------------------------------------------------------------------------------------------------------"));

					float[] columnWidths = { 20f, 1f };
					//Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 27);
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
					String imageLocation = paths.getString("sms.librarian.photo") + tempdate + "/" + ImageListPath.get(0).getPath();
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
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}*/

/*	private String sendEmail(LibrarianDataBean librarianDataBean, List<LibrarianDataBean> imageListPath2)
			throws AddressException {
		String status = "Fail";
		String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
		String mail = librarianDataBean.getLibEmail();
		String to = "" + mail;
		Properties prop = new Properties();
		prop.put("mail.smtp.host", text.getString("sms.mail.host"));
		prop.put("mail.smtp.socketFactory.port", text.getString("sms.mail.port"));
		prop.put("mail.smtp.socketFactory.class", text.getString("sms.mail.class"));
		prop.put("mail.smtp.starttls.enable", text.getString("sms.mail.smtp.startles"));
		prop.put("mail.smtp.auth", text.getString("sms.mail.auth"));
		prop.put("mail.smtp.port", text.getString("sms.mail.smtp.port"));
		prop.put("mail.smtp.ssl.trust", text.getString("sms.mail.smtp.trust"));

		String body1 = "<htm><head></heade><body>"
				+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
				+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
				+ "<h3>Dear " + librarianDataBean.getLibUsername() + ",</h3>"
				+ "<p>Welcome on-board into"
				+ schoolName
				+ "family. Please find the your Username and Password enclosed with is mail."
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
			 message.addRecipients(Message.RecipientType.CC, myCcList); 
			message.setSubject("SMS Confirmation-" + librarianDataBean.getLibUsername());
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setContent(body1, "text/html");

			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			String filename =paths.getString("sms.librarian.pdf")+ ft.format(now) + "/" + librarianDataBean.getLibUsername()
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
*/
	//RAGULAN 
	private List<LibrarianDataBean> classlist=null;
	private List<LibrarianDataBean> sublectlist=null;
	private List<String> clasSectionList = null;
	public List<String> getClasSectionList() {
		return clasSectionList;
	}

	public void setClasSectionList(List<String> clasSectionList) {
		this.clasSectionList = clasSectionList;
	}

	public List<LibrarianDataBean> getSublectlist() {
		return sublectlist;
	}

	public void setSublectlist(List<LibrarianDataBean> sublectlist) {
		this.sublectlist = sublectlist;
	}

	public List<LibrarianDataBean> getClasslist() {
		return classlist;
	}

	public void setClasslist(List<LibrarianDataBean> classlist) {
		this.classlist = classlist;
	}

	@PostConstruct
	public void init(){
		logger.info("-----------Inside init method()----------------");
		librarianDataBean=new LibrarianDataBean();
		classlist=new ArrayList<LibrarianDataBean>();
		sublectlist=new ArrayList<LibrarianDataBean>();
	}
	
	public String classsubjectpage(){
		logger.info("-----------Inside classsubjectpage method()----------------");
		SmsController controller=null;
		clasSectionList=new ArrayList<String>();
	try{
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (SmsController) ctx.getBean("controller");
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
		librarianDataBean.setClassname("");
		librarianDataBean.setClasssection("");
		librarianDataBean.setSubjectcode("");
		librarianDataBean.setSubjectname("");
		librarianDataBean.setName("");
		classlist.clear();
		sublectlist.clear();
		clasSectionList = controller.getClassList(personID);
		Collections.sort(clasSectionList);
		}catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return "ClassSubjectRegistration";
	}
	
	public String addclass(){
		logger.info("-----------Inside addclass method()----------------");
		SmsController controller=null;
		String status="";
		String name;
		FacesContext fc=FacesContext.getCurrentInstance();
		try{
			if(addclassvalidation(true)){
				String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				status=controller.validatecalss(librarianDataBean,schoolID);
				logger.debug("check status"+status);
				if(status.equalsIgnoreCase("success"))
				{
					librarianDataBean=new LibrarianDataBean();
				}
				else
				{
					name=CommonValidate.findComponentInRoot("name").getClientId(fc);
					fc.addMessage(name, new FacesMessage("Already Subject and Class Exsist"));	
					classlist.clear();
				}				
			}else{
				classlist.remove(classlist.size()-1);
			}
		}catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return "";
	}

	public String addsubject(){
		logger.info("-----------Inside addsubject method()----------------");
		SmsController controller=null;
		String name;
		String status="";
		FacesContext fc=FacesContext.getCurrentInstance();
		try{
			if(addsubjectvalidation(true)){
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
				controller = (SmsController) ctx.getBean("controller");
				logger.debug("class"+librarianDataBean.getName());
				logger.debug("subject"+librarianDataBean.getSubjectname());
				status=controller.validatesubject(librarianDataBean,schoolID);
				logger.debug("check statuss"+status);
				if(status.equalsIgnoreCase("exist"))
				{
					name=CommonValidate.findComponentInRoot("classsection").getClientId(fc);
					fc.addMessage(name, new FacesMessage("This Class Already has this Subject"));
					sublectlist.clear();
				}
				else if(status.equalsIgnoreCase("success"))
				{
					logger.debug("success");
					int c=0;
					logger.debug("size---"+sublectlist.size());
					for (int i = 0; i < sublectlist.size(); i++) {
						System.out.println("inside for loop---");
						if(sublectlist.get(i).getName().equalsIgnoreCase(librarianDataBean.getName())
								 && sublectlist.get(i).getSubjectname().equalsIgnoreCase(librarianDataBean.getSubjectname()) &&
								sublectlist.get(i).getSubjectcode().equalsIgnoreCase(librarianDataBean.getSubjectcode())){
							c++;
						}
					}
					logger.debug("inside c---"+c);
					if(c>1){
						System.out.println("inside if---");
						name=CommonValidate.findComponentInRoot("sname").getClientId(fc);
						fc.addMessage(name, new FacesMessage("This Subject name and code Already has this Class"));
						sublectlist.remove(sublectlist.size()-1);
					}else{
						librarianDataBean=new LibrarianDataBean();
					}
					logger.debug("size---"+sublectlist.size());
				}
			}else{
				sublectlist.remove(sublectlist.size()-1);
			}
		}catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return "";
	}
	
	private boolean addsubjectvalidation(boolean b) {
		logger.info("-----------Inside addsubjectvalidation method()----------------");
		valid=true;
		String name;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(StringUtils.isEmpty(librarianDataBean.getName())){
			name=CommonValidate.findComponentInRoot("classsection").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Select the Class"));
			valid=false;
		}
		if(StringUtils.isEmpty(librarianDataBean.getSubjectname())){
			name=CommonValidate.findComponentInRoot("sname").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter The Subject Name"));
			valid=false;
		}if(StringUtils.isEmpty(librarianDataBean.getSubjectcode())){
			name=CommonValidate.findComponentInRoot("code").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter The Subject Code"));
			valid=false;
		}
		return valid;
	}
	
	private boolean addclassvalidation(boolean valid) {
		logger.info("-----------Inside addclassvalidation method()----------------");
		valid=true;
		String name;
		//String status="";
		SmsController controller=null;
		try {
		FacesContext fc=FacesContext.getCurrentInstance();
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (SmsController) ctx.getBean("controller");
		if(StringUtils.isEmpty(librarianDataBean.getClassname())){
			name=CommonValidate.findComponentInRoot("name").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter The Class Name"));
			valid=false;
		}if(StringUtils.isEmpty(librarianDataBean.getClasssection())){
			name=CommonValidate.findComponentInRoot("section").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter The Class Section"));
			valid=false;
		}
		return valid;
		}
		catch(Exception e)
		{
			logger.error("LibrarianMB Exception -->"+e.getMessage());
		}finally{
			if(controller!=null){
				controller=null;
			}
		}
		return valid;
	}
	
	public String classinsert(){
		logger.info("-----------Inside classinsert method()----------------");
		SmsController controller=null;
		try{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			if(personID!=null){
				for(int i=0;i<classlist.size();i++){
					librarianDataBean.setClassname(classlist.get(i).getClassname());
					librarianDataBean.setClasssection(classlist.get(i).getClasssection());
					String status=controller.insertclass(personID,schoolID,librarianDataBean);
					if(status.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("PF('confirm').show()");	
					}
				}
			}
		}catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return "";
	}
	
	public String subjectinsert(){
		logger.info("-----------Inside subjectinsert method()----------------");
		SmsController controller=null;
		try{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			if(personID!=null){
				for(int i=0;i<sublectlist.size();i++){
					librarianDataBean.setName(sublectlist.get(i).getName());
					librarianDataBean.setSubjectname(sublectlist.get(i).getSubjectname());
					librarianDataBean.setSubjectcode(sublectlist.get(i).getSubjectcode());
					String status=controller.insertsubject(personID,schoolID,librarianDataBean);
					if(status.equalsIgnoreCase("Success")){
						RequestContext.getCurrentInstance().execute("PF('confirm').show()");	
					}
				}
			}
			}catch(Exception e){
				logger.warn(" exception - "+e);
			}
			return "";
		}
	public String onTabChange()
	{
		logger.info("-----------Inside onTabChange method()----------------");
		SmsController controller=null;
		try
		{
		clasSectionList=new ArrayList<String>();
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (SmsController) ctx.getBean("controller");
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
	    clasSectionList = controller.getClassList(personID);
	    Collections.sort(clasSectionList);
		}
		catch(Exception e)
		{
			logger.warn(" exception - "+e);
		}
		return null;	
	}
	
	public String clear(){
		logger.info("-----------Inside clear method()----------------");
		librarianDataBean.setClassname("");
		librarianDataBean.setClasssection("");
		librarianDataBean.setSubjectcode("");
		librarianDataBean.setSubjectname("");
		librarianDataBean.setName("");
		classlist.clear();
		sublectlist.clear();
		return"";
	}
}
