package com.stumosys.managedBean;

import java.awt.image.BufferedImage;
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

//import javax.activation.DataHandler;
//import javax.activation.DataSource;
//import javax.activation.FileDataSource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.imageio.ImageIO;
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
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
//import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
//import com.lowagie.text.pdf.PdfPCell;
//import com.lowagie.text.pdf.PdfPTable;
//import com.lowagie.text.pdf.PdfWriter;
import com.stumosys.controler.SmsController;
import com.stumosys.domain.LibrarianDataBean;
//import com.stumosys.domain.LibraryDataBean;
//import com.stumosys.domain.TeacherDataBean;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name = "librarianViewMB")
@RequestScoped
public class LibrarianViewMB {
	LibrarianDataBean librarianDataBean = new LibrarianDataBean();
	private static Logger logger = Logger.getLogger(LibrarianViewMB.class);
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	private List<LibrarianDataBean> ImageListPath = null;
	private boolean flag1 = false;
	private boolean flag2 = false;
	private boolean lflag = false;
	private boolean boxflag = false;
	private boolean delBoxflag = false;
	private String status;
	private String studClass;
	private String rollnumber1;
	private String teaID;
	UploadedFile file;
	private List<LibrarianDataBean> librarianList = null;
	private List<LibrarianDataBean> filterlibrarianList;
	private List<LibrarianDataBean> subjectLists = null;
	private boolean subjectlistFlag=false;
	private List<String> clasSectionList=null;
	public List<String> getClasSectionList() {
		return clasSectionList;
	}

	public void setClasSectionList(List<String> clasSectionList) {
		this.clasSectionList = clasSectionList;
	}

	public boolean isSubjectlistFlag() {
		return subjectlistFlag;
	}

	public void setSubjectlistFlag(boolean subjectlistFlag) {
		this.subjectlistFlag = subjectlistFlag;
	}

	public List<LibrarianDataBean> getSubjectLists() {
		return subjectLists;
	}

	public void setSubjectLists(List<LibrarianDataBean> subjectLists) {
		this.subjectLists = subjectLists;
	}
	public List<LibrarianDataBean> getFilterlibrarianList() {
		return filterlibrarianList;
	}

	public void setFilterlibrarianList(List<LibrarianDataBean> filterlibrarianList) {
		this.filterlibrarianList = filterlibrarianList;
	}

	private List<LibrarianDataBean> librarianViewList = null;
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	Date now = new Date();

	public List<LibrarianDataBean> getImageListPath() {
		return ImageListPath;
	}

	public void setImageListPath(List<LibrarianDataBean> imageListPath) {
		ImageListPath = imageListPath;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public List<LibrarianDataBean> getLibrarianViewList() {
		return librarianViewList;
	}

	public void setLibrarianViewList(List<LibrarianDataBean> librarianViewList) {
		this.librarianViewList = librarianViewList;
	}

	public SimpleDateFormat getFt() {
		return ft;
	}

	public void setFt(SimpleDateFormat ft) {
		this.ft = ft;
	}

	public String getTeaID() {
		return teaID;
	}

	public void setTeaID(String teaID) {
		this.teaID = teaID;
	}

	public String getRollnumber1() {
		return rollnumber1;
	}

	public void setRollnumber1(String rollnumber1) {
		this.rollnumber1 = rollnumber1;
	}

	public String getStudClass() {
		return studClass;
	}

	public void setStudClass(String studClass) {
		this.studClass = studClass;
	}

	public List<LibrarianDataBean> getLibrarianList() {
		return librarianList;
	}

	public void setLibrarianList(List<LibrarianDataBean> librarianList) {
		this.librarianList = librarianList;
	}

	public boolean isLflag() {
		return lflag;
	}

	public void setLflag(boolean lflag) {
		this.lflag = lflag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		LibrarianViewMB.logger = logger;
	}

	public boolean isFlag1() {
		return flag1;
	}

	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}

	public boolean isFlag2() {
		return flag2;
	}

	public void setFlag2(boolean flag2) {
		this.flag2 = flag2;
	}

	public boolean isBoxflag() {
		return boxflag;
	}

	public void setBoxflag(boolean boxflag) {
		this.boxflag = boxflag;
	}

	public boolean isDelBoxflag() {
		return delBoxflag;
	}

	public void setDelBoxflag(boolean delBoxflag) {
		this.delBoxflag = delBoxflag;
	}

	public LibrarianDataBean getLibrarianDataBean() {
		return librarianDataBean;
	}

	public void setLibrarianDataBean(LibrarianDataBean librarianDataBean) {
		this.librarianDataBean = librarianDataBean;
	}

	public String librarianInfo() {
		logger.info("-----------Inside librarianInfo method()----------------");
		setBoxflag(false);
		setDelBoxflag(false);
		setFlag1(false);
		SmsController controller = null;
		String rollnumber = "";
		String status = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			logger.debug("-------person ID-------" + personID);
			status = controller.getRoll(personID);
			if (personID != null) {
				if (status.equalsIgnoreCase("Admin")) {
					librarianList = new ArrayList<LibrarianDataBean>();
					librarianList = controller.getAllLibrarianInfo(personID);
				} else if (status.equalsIgnoreCase("Student")) {
					rollnumber = controller.getRollNumber(personID);
					logger.debug("------student roll number-----" + rollnumber);
					studClass = controller.getStudClsSection(rollnumber);
					librarianDataBean.setTeaclsSection(studClass);
					librarianList = new ArrayList<LibrarianDataBean>();
					librarianList = controller.getAllLibrarianInfo(personID);
					setFlag1(true);
				} else if (status.equalsIgnoreCase("Parent")) {
					rollnumber = controller.getRollNumber(personID);
					logger.debug("------student roll number-----" + rollnumber);
					studClass = controller.getStudClsSection(rollnumber);
					librarianDataBean.setTeaclsSection(studClass);
					librarianList = new ArrayList<LibrarianDataBean>();
					librarianList = controller.getAllLibrarianInfo(personID);
					setFlag1(true);
				} else if (status.equalsIgnoreCase("Teacher")) {

					logger.debug("-----------Teacher---------");
					rollnumber1 = controller.getRollNumber(personID);
					logger.debug("------test roll-------" + rollnumber1);
					librarianDataBean.setTeaID(rollnumber1);
					librarianDataBean.setTeaID(rollnumber1);
					librarianList = controller.getAllLibrarianInfo(personID);
					setFlag1(true);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			//logger.warn("Inside Exception",e);
		}
		return "LibrarianviewPageLoad";

	}

	public String returnToHome() {
		logger.info("-----------Inside returnToHome method()----------------");
		SmsController controller = null;
		logger.debug("Calling");
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				librarianList = new ArrayList<LibrarianDataBean>();
				librarianList = controller.getAllLibrarianInfo(personID);
				setBoxflag(false);
				setDelBoxflag(false);
				return "LibrarianviewPageLoad";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";

	}

	public String view() {
		logger.info("-----------Inside view method()----------------");
		try {
			//logger.debug("-----Inside call Teacher view method ------");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");

			if (personID != null) {
				if (librarianDataBean.getLibID() != null) {
					librarianDataBean.setPath("");
					librarianDataBean.setPathDate(null);
					String status = getLibrarianView(personID, librarianDataBean.getLibID());
					logger.debug("status -- " + status);
					logger.debug(status);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			//logger.warn("Exception -->"+e.getMessage());
		}

		return null;
	}

	private String getLibrarianView(String personID, String libID) {
		logger.info("-----------Inside getLibrarianView method()----------------");
		try {
			SmsController controller = null;
			librarianViewList = null;
			//String status = "Fail";
			logger.debug("lib view ");
			if (personID != null && libID != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				controller.getLibrarianInfo(personID, librarianDataBean.getLibID(), librarianDataBean);
				logger.debug("name -- " + librarianDataBean.getLibFirstName());
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return null;
	}

	public void delete() {
		logger.info("-----------Inside delete method()----------------");
		SmsController controller = null;
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			logger.debug("----------------" + librarianDataBean.getLibID());
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			if (personID != null && librarianDataBean.getLibID() != null) {
				String status = controller.deleteLibrarian(personID, librarianDataBean);
				if (status.equalsIgnoreCase("Success")) {
					setDelBoxflag(true);
				}
			}
		} catch (Exception e) {
			//logger.warn("Inside Exception",e);
			logger.warn("Exception -->"+e.getMessage());
		}
	}

	public void imageview(OutputStream out, Object data) throws IOException {
		logger.info("-----------Inside imageview method()----------------");
		String s = paths.getString("sms.librarian.photo");
		BufferedImage img = ImageIO
				.read(new File(s + ft.format(librarianDataBean.getPathDate()) + "/" + librarianDataBean.getPath()));
		ImageIO.write(img, "png", out);
	}

	public String edit() {
		logger.info("-----------Inside edit method()----------------");
		//SmsController controller = null;
		try {
		//	ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			//controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				if (librarianDataBean.getLibID() != null) {
					librarianDataBean.setPath("");
					librarianDataBean.setPathDate(null);
					String status = getLibrarianView1(personID, librarianDataBean.getLibID());
					logger.debug("status - " + status);
					if (status.equalsIgnoreCase("sucess")) {
						return "editPageLoad1";
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return "";

	}

	private String getLibrarianView1(String personID, String libID) {
		logger.info("-----------Inside getLibrarianView1 method()----------------");
		try {
			SmsController controller = null;
			librarianViewList = null;
			//String status = "Fail";
			logger.debug("lib view ");
			if (personID != null && libID != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				controller.getLibrarianInfo(personID, librarianDataBean.getLibID(), librarianDataBean);
				if("".equalsIgnoreCase(librarianDataBean.getCountryCode()) && "".equalsIgnoreCase(librarianDataBean.getLibPhoneNo()) ||
						librarianDataBean.getCountryCode().equalsIgnoreCase(null) && librarianDataBean.getLibPhoneNo().equalsIgnoreCase(null)){
				}else{
					String code="";
				if(librarianDataBean.getCountryCode().equalsIgnoreCase("+971")){
					code=librarianDataBean.getLibPhoneNo().substring(4);
				}else{
					code=librarianDataBean.getLibPhoneNo().substring(3);
				}
					librarianDataBean.setLibPhoneNo(code);
				}
				logger.debug("name -- " + librarianDataBean.getLibFirstName());
				setStatus("sucess");
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return status;
	}

	public String libEdit() {
		logger.info("-----------Inside libEdit method()----------------");
		try {
			if (validate(true)) {
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");

				if (personID != null) {

					logger.debug("person -- " + personID);
					RequestContext reqcontextt = RequestContext.getCurrentInstance();
					reqcontextt.execute("librarianEditBlk()");

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
		/*if (StringUtils.isEmpty(librarianDataBean.getLibEmail())) {
			{
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("lEmail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.librarian.email")));
				}
				valid = false;
			}
		} else*/ if (!StringUtils.isEmpty(librarianDataBean.getLibEmail())) {
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
		if(!StringUtils.isEmpty(librarianDataBean.getLibPhoneNo())){
			if(StringUtils.isEmpty(librarianDataBean.getCountryCode())){
				fieldName = CommonValidate.findComponentInRoot("countrycode").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Countrycode"));
				valid=false;
			}
		}
		return valid;
	}

	public String update() {
		logger.info("-----------Inside update method()----------------");
		SmsController controller = null;
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			 String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
			 String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			 int schoolid=Integer.parseInt(schoolID);
			if (personID != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				if (librarianDataBean.getLibLibrarianPhoto() == null) {
					logger.debug("photo -- " + librarianDataBean.getLibLibrarianPhoto());
					librarianDataBean.setLibfilePath(null);
				} else {
					logger.debug("photo -1- " + librarianDataBean.getLibLibrarianPhoto());
					String s = librarianDataBean.getLibLibrarianPhoto().getContentType();
					String type = s.substring(s.lastIndexOf("/") + 1);
					copyFile(librarianDataBean.getLibID(), librarianDataBean.getLibLibrarianPhoto().getInputstream(),
							type);
					String path = ft.format(now) + "/" + librarianDataBean.getLibID() + "." + type;
					librarianDataBean.setLibfilePath(path);
				}
				
				String status = controller.libUpdate(librarianDataBean, personID);
				if (status.equals("success")) {
					//String pdfStatus = generatePdf(librarianDataBean);
					MailSendJDBC.librarianUpdate(librarianDataBean,schoolName,schoolid);
					//if (pdfStatus.equalsIgnoreCase("Success")) {
						/*String emailStatus = sendEmail(librarianDataBean, ImageListPath);
						if (emailStatus.equalsIgnoreCase("Success")) {
*/
							FacesMessage message = new FacesMessage("Succesful",
									librarianDataBean.getLibID() + " is Updated .");
							FacesContext.getCurrentInstance().addMessage(null, message);
							RequestContext reqcontext = RequestContext.getCurrentInstance();
							reqcontext.execute("PF('librarianEditblockUI').hide();");
							reqcontext.execute("PF('librarianeditDialog').show();");
							boxflag = true;
						/*}*/
					//}
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return null;

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
							new File(files + paths.getString("path_context").toString() + librarianDataBean.getLibID() + ".pdf"));
					librarianDataBean.setLibfilePath(files +paths.getString("path_context").toString() + librarianDataBean.getLibID() + ".pdf");
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
					Font font3 = new Font(Font.TIMES_ROMAN, 27);
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

	/*private String sendEmail(LibrarianDataBean librarianDataBean, List<LibrarianDataBean> imageListPath2)
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
				+ "<h3>Dear " + librarianDataBean.getLibID() + ",</h3>"
				+ "<p>Welcome on-board into"
				+ schoolName
				+ "family. Your profile is updated and the details enclosed with is mail.</p>"

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
			 message.addRecipients(Message.RecipientType.CC, myCcList); 
			message.setSubject("SMS Confirmation-" + librarianDataBean.getLibID());
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setContent(body1, "text/html");

			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			String filename = paths.getString("sms.librarian.pdf") + ft.format(now) + "/" + librarianDataBean.getLibID() + ".pdf";// change
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
	}*/

	public String dummyAction(FileUploadEvent event) throws IOException {
		logger.info("-----------Inside dummyAction method()----------------");
		this.file = event.getFile();
		librarianDataBean.setLibLibrarianPhoto(event.getFile());
		logger.debug("photo -- " + librarianDataBean.getLibLibrarianPhoto());
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);
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

			// write the inputStream to a FileOutputStream
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

	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		logger.info("-----------Inside preProcessPDF method()----------------");
		String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		int schoolid=Integer.parseInt(schoolID);
		String logo ="";		
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);		
		if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
			logo = paths.getString(schoolID+"_LOGO");
			pdf.add(Image.getInstance(getClass().getClassLoader().getResource(logo)));
		}else{
			logo=MailSendJDBC.pdfProcess(schoolid);
			logger.debug("logo "+logo);
			pdf.add(Image.getInstance(logo));
		}		
		pdf.add(new Paragraph(
				"------------------------------------------------------------------------------------------------------------------------"));
	}

	/*prema 01/11/2016 class & subject information*/
	public String onTabChange()
	{
		logger.info("-----------Inside onTabChange method()----------------");
		SmsController controller=null;
		librarianDataBean.setName("");
		setSubjectlistFlag(false);
		try{
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
	public String classsubjectviewpage(){
		logger.info("-----------Inside classsubjectviewpage method()----------------");
		SmsController controller = null;
		librarianDataBean.setName("");
		setSubjectlistFlag(false);
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				librarianList = new ArrayList<LibrarianDataBean>();
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				librarianList=controller.classlistview(personID,librarianDataBean);
			}
		} catch (Exception e) {
			logger.warn("Exception - "+e.getMessage());
		}
		return "ClassSubjectView";
	}
	public void onRowCancel(RowEditEvent event) {
		logger.info("-----------Inside onRowCancel method()----------------");
		FacesMessage msg = new FacesMessage("Edit Cancelled", ((LibrarianDataBean) event.getObject()).getClassname()+"/"+((LibrarianDataBean) event.getObject()).getClasssection());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	private boolean validates(boolean valid) {
		logger.info("-----------Inside validates method()----------------");
		valid=true;
		if (StringUtils.isEmpty(librarianDataBean.getClassname())) {
			FacesMessage msg = new FacesMessage("Please Enter the Class Name");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			valid = false;
		} 
		if (StringUtils.isEmpty(librarianDataBean.getClasssection())) {
			FacesMessage msg = new FacesMessage("Please Enter the Class Section");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			valid = false;
		} 
		return valid;
	}
	private boolean validates1(boolean valid) {
		logger.info("-----------Inside validates1 method()----------------");
		valid=true;
		if (StringUtils.isEmpty(librarianDataBean.getSubjectname())) {
			FacesMessage msg = new FacesMessage("Please Enter the Subject Name");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			valid = false;
		} 
		if (StringUtils.isEmpty(librarianDataBean.getSubjectcode())) {
			FacesMessage msg = new FacesMessage("Please Enter the Subject Code");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			valid = false;
		} 
		return valid;
	}
	public void updateclass(RowEditEvent event)
	{
		logger.info("-----------Inside updateclass method()----------------");
		SmsController controller = null;
		String status = "Fail";
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				String classname = ((LibrarianDataBean) event.getObject()).getClassname().toString();
				String section = ((LibrarianDataBean) event.getObject()).getClasssection().toString();
				String classid = ((LibrarianDataBean) event.getObject()).getClassid().toString();
				librarianDataBean.setClassname(classname);
				librarianDataBean.setClasssection(section);
				librarianDataBean.setClassid(classid);
				if (validates(true)) {
					ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
					controller = (SmsController) ctx.getBean("controller");
					status = controller.editclasssec(personID,librarianDataBean);
					if (status.equalsIgnoreCase("Success")) {
						librarianList=controller.classlistview(personID,librarianDataBean);
						RequestContext.getCurrentInstance().execute("PF('updateblock').show();");
					}else if(status.equalsIgnoreCase("Fail")){
						RequestContext.getCurrentInstance().execute("PF('updateexistblock').show();");
					}
				}
			}
		}
		catch(Exception e)
		{
			logger.warn(" exception - "+e);
		}
	}
	public String deleteclass()
	{
		logger.info("-----------Inside deleteclass method()----------------");
		SmsController controller = null;
		//String status = "Fail";
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				status = controller.deleteclasssec(personID,librarianDataBean);
				librarianList=controller.classlistview(personID,librarianDataBean);
			}
		}
		catch(Exception e)
		{
			logger.warn(" exception - "+e);
		}
		return "";
	}
	public String subjectlist(ValueChangeEvent vc){
		logger.info("-----------Inside subjectlist method()----------------");
		SmsController controller = null;
		setSubjectlistFlag(true);
		try{
			String classs=vc.getNewValue().toString();
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				subjectLists=new ArrayList<LibrarianDataBean>();
				librarianDataBean.setClassname(classs);
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				subjectLists=controller.subjectlistview(personID,librarianDataBean);
			}
			
		}catch(Exception e){
			logger.warn(" exception - "+e.getMessage());
		}
		return "";
	}
	public void updatesubject(RowEditEvent event)
	{
		logger.info("-----------Inside updatesubject method()----------------");
		SmsController controller = null;
		String status = "Fail";
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				String subjectname = ((LibrarianDataBean) event.getObject()).getSubjectname().toString();
				String subjectcode = ((LibrarianDataBean) event.getObject()).getSubjectcode().toString();
				String subjectid = ((LibrarianDataBean) event.getObject()).getSubid().toString();
				librarianDataBean.setSubjectname(subjectname);
				librarianDataBean.setSubjectcode(subjectcode);
				librarianDataBean.setSubid(subjectid);
				if (validates1(true)) {
					ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
					controller = (SmsController) ctx.getBean("controller");
					status = controller.updatesujectname(personID,librarianDataBean);
					if (status.equalsIgnoreCase("Success")) {
						subjectLists=controller.subjectlistview(personID,librarianDataBean);
						RequestContext.getCurrentInstance().execute("PF('updateblock').show();");
					}else if(status.equalsIgnoreCase("Fail")){
						RequestContext.getCurrentInstance().execute("PF('subupdateexistblock').show();");
					}
				}
			}
		}
		catch(Exception e)
		{
			logger.warn("Exception -->"+e.getMessage());
		}
	}
	public String deletesubject()
	{
		logger.info("-----------Inside deletesubject method()----------------");
		SmsController controller = null;
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				status = controller.deletesubname(personID,librarianDataBean);
			}
		}
		catch(Exception e)
		{
			logger.warn("Exception - "+e.getMessage());
		}
		return "";
	}
	public String returnToHome1() {
		logger.info("-----------Inside returnToHome1 method()----------------");
		SmsController controller = null;
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				subjectLists=controller.subjectlistview(personID,librarianDataBean);
				return "";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}
}
