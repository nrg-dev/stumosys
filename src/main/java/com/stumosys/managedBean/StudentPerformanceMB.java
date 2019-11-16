package com.stumosys.managedBean;

//import java.awt.Desktop;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Date;
import java.util.List;
//import java.util.Properties;
import java.util.ResourceBundle;

/*import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
*/import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
/*import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
*///import javax.mail.internet.AddressException;
/*import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
*/
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/*import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
*/import com.stumosys.controler.SmsController;
import com.stumosys.domain.StudentDataBean;
import com.stumosys.domain.StudentPerformanceDataBean;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name = "studentPerformanceMB")
@RequestScoped
public class StudentPerformanceMB {

	StudentPerformanceDataBean studentPerformanceDataBean = new StudentPerformanceDataBean();
	StudentDataBean studentDataBean = new StudentDataBean();
	private String[] checkAppearance;
	private boolean check = false;
	private boolean flag = true;
	private boolean flag1 = true;
	private boolean sflag = false;
	private boolean classdropflag=false;
	private boolean classlabelflag=false;
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	private static Logger logger = Logger.getLogger(StudentPerformanceMB.class);


	public boolean isClassdropflag() {
		return classdropflag;
	}

	public void setClassdropflag(boolean classdropflag) {
		this.classdropflag = classdropflag;
	}

	public boolean isClasslabelflag() {
		return classlabelflag;
	}

	public void setClasslabelflag(boolean classlabelflag) {
		this.classlabelflag = classlabelflag;
	}

	public boolean isSflag() {
		return sflag;
	}

	public void setSflag(boolean sflag) {
		this.sflag = sflag;
	}

	List<String> idlist = null;
	private boolean check1 = false;
	private boolean check2 = false;
	Date now = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");

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

	
	private List<String> sRoll = null;
	private List<String> sRoll1 = null;

	public List<String> getsRoll1() {
		return sRoll1;
	}

	public void setsRoll1(List<String> sRoll1) {
		this.sRoll1 = sRoll1;
	}

	private String clssection;
	private String[] test;
	List<String> app = null;
	private boolean boxflag = false;
	private boolean boxflag1 = false;
	private boolean boxflag2 = false;
	private boolean boxflag3 = false;
	private List<StudentPerformanceDataBean> ImageListPath = null;

	public List<StudentPerformanceDataBean> getImageListPath() {
		return ImageListPath;
	}

	public void setImageListPath(List<StudentPerformanceDataBean> imageListPath) {
		ImageListPath = imageListPath;
	}

	public boolean isBoxflag2() {
		return boxflag2;
	}

	public void setBoxflag2(boolean boxflag2) {
		this.boxflag2 = boxflag2;
	}

	public boolean isBoxflag1() {
		return boxflag1;
	}

	public void setBoxflag1(boolean boxflag1) {
		this.boxflag1 = boxflag1;
	}

	public boolean isBoxflag() {
		return boxflag;
	}

	public void setBoxflag(boolean boxflag) {
		this.boxflag = boxflag;
	}

	public List<String> getApp() {
		return app;
	}

	public void setApp(List<String> app) {
		this.app = app;
	}

	public String[] getTest() {
		return test;
	}

	public void setTest(String[] test) {
		this.test = test;
	}

	public String getClssection() {
		return clssection;
	}

	public void setClssection(String clssection) {
		this.clssection = clssection;
	}

	public List<String> getsRoll() {
		return sRoll;
	}

	public void setsRoll(List<String> sRoll) {
		this.sRoll = sRoll;
	}

	public boolean isBoxflag3() {
		return boxflag3;
	}

	public void setBoxflag3(boolean boxflag3) {
		this.boxflag3 = boxflag3;
	}

	public List<String> getIdlist() {
		return idlist;
	}

	public void setIdlist(List<String> idlist) {
		this.idlist = idlist;
	}

	public boolean isFlag1() {
		return flag1;
	}

	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	
	public boolean getCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public boolean getCheck1() {
		return check1;
	}

	public void setCheck1(boolean check1) {
		this.check1 = check1;
	}

	public boolean getCheck2() {
		return check2;
	}

	public void setCheck2(boolean check2) {
		this.check2 = check2;
	}

	public String[] getCheckAppearance() {
		return checkAppearance;
	}

	public void setCheckAppearance(String[] checkAppearance) {
		this.checkAppearance = checkAppearance;
	}

	public StudentPerformanceDataBean getStudentPerformanceDataBean() {
		return studentPerformanceDataBean;
	}

	public void setStudentPerformanceDataBean(StudentPerformanceDataBean studentPerformanceDataBean) {
		this.studentPerformanceDataBean = studentPerformanceDataBean;
	}
private Date ttdate;
	public String attitudePage() {
		studentPerformanceDataBean.setPerformStudID("");
		studentPerformanceDataBean.setPerClassSection("");
		studentPerformanceDataBean.setStudentAppearance(null);
		studentPerformanceDataBean.setStudentAttitude(null);
		studentPerformanceDataBean.setAttOthers("");
		studentPerformanceDataBean.setAppOthers("");
		setCheck1(false);
		setCheck(false);
		setCheck2(false);
		setFlag(true);
		setFlag1(true);
		SmsController controller = null;
		String status = "";
		String schoolID = ""; 
		int school_id = 0;
		sRoll1=null;
		try {
			setBoxflag(false);
			setBoxflag1(false);
			setBoxflag2(false);
			setBoxflag3(false);
			logger.debug("-----attitudePage method calling-----");
			sRoll = new ArrayList<String>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			school_id=Integer.parseInt(schoolID);
			controller = (SmsController) ctx.getBean("controller");
			if (personID != null) {
				idlist = new ArrayList<String>();
				status = controller.getRoll(personID);
				logger.debug("-----status----" + status);
				if (status.equalsIgnoreCase("Admin") || status.equalsIgnoreCase("Teacher")) {
					setSflag(true);
				}
				if (status.equalsIgnoreCase("Student") || status.equalsIgnoreCase("Parent")) {
					setSflag(false);
				}
				if((school_id==1) || (school_id==2) || (school_id==3) || (school_id==4) || (school_id==5) || (school_id==6)){
					setClassdropflag(true);
					idlist = controller.getPerClassList1(personID,studentPerformanceDataBean);
					// idlist=controller.getClassList(personID,studentDataBean);
					logger.debug("---------dom size-----" + studentPerformanceDataBean.getStuClassList().size());
					logger.debug("------value------" + studentPerformanceDataBean.getStuClassList().get(0).getStuCls1());
					for (int i = 0; i < studentPerformanceDataBean.getStuClassList().size(); i++) {
						sRoll.add(studentPerformanceDataBean.getStuClassList().get(i).getStuCls1());
					}
				}else{
					if(status.equalsIgnoreCase("Admin") || "Teacher".equalsIgnoreCase(status)){
						setClassdropflag(true);
						setClasslabelflag(false);
						idlist = controller.getPerClassList1(personID,studentPerformanceDataBean);
						// idlist=controller.getClassList(personID,studentDataBean);
						logger.debug("---------dom size-----" + studentPerformanceDataBean.getStuClassList().size());
						logger.debug("------value------" + studentPerformanceDataBean.getStuClassList().get(0).getStuCls1());
						for (int i = 0; i < studentPerformanceDataBean.getStuClassList().size(); i++) {
							sRoll.add(studentPerformanceDataBean.getStuClassList().get(i).getStuCls1());
						}
					}
					else{
					setClasslabelflag(true);
					sRoll1 = new ArrayList<String>();
					controller.getperstudentList(studentPerformanceDataBean,personID);
					if(studentPerformanceDataBean.getRollnolist().size() > 0){
						for (int i = 0; i < studentPerformanceDataBean.getRollnolist().size(); i++) {
							sRoll1.add(studentPerformanceDataBean.getRollnolist().get(i).getPerformStudID());
						}
					}else{
						sRoll1 = new ArrayList<String>();
						}
					}
				}
			}
		}

		catch (Exception e) {
			logger.debug("-------attitudePage method Exception calling------");
			logger.warn("Exception -->"+e.getMessage());
		}

		return "studentPerform";

	}

	public Date getTtdate() {
		return ttdate;
	}

	public void setTtdate(Date ttdate) {
		this.ttdate = ttdate;
	}

	public String reset() {
		try {
			studentPerformanceDataBean.setPerformStudID("");
			studentPerformanceDataBean.setPerClassSection("");
			studentPerformanceDataBean.setStudentAppearance(null);
			studentPerformanceDataBean.setStudentAttitude(null);
			studentPerformanceDataBean.setAttOthers("");
			studentPerformanceDataBean.setAppOthers("");
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}

	public void classSection(ValueChangeEvent e) {
		logger.debug("-------inside test method------");

		SmsController controller = null;
		sRoll1=null;
		try {
			sRoll1 = new ArrayList<String>();
			String classname= e.getNewValue().toString();
			logger.debug("Insde"+classname);
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			if(classname.equalsIgnoreCase("select")){
				sRoll1 = new ArrayList<String>();
			}else{
			studentPerformanceDataBean.setPerClassSection(classname);
			clssection = controller.getPerClass(studentPerformanceDataBean,personID);
			if(studentPerformanceDataBean.getRollnolist().size() > 0){
			for (int i = 0; i < studentPerformanceDataBean.getRollnolist().size(); i++) {
				sRoll1.add(studentPerformanceDataBean.getRollnolist().get(i).getPerformStudID());
			}
			}else{
				
				sRoll1 = new ArrayList<String>();
			}
			}
			logger.debug("-----class section-----" + clssection);
		} catch (Exception v) {
			logger.warn(v.getMessage());
			logger.debug("3");
		}
	}

	public String loading() {
		String personID = "";
		logger.debug("check"+check);
		logger.debug("check1"+check1);
		try {
			if (validate(true)) {
				personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
				if (personID != null) {
					RequestContext reqcontextt = RequestContext.getCurrentInstance();
					reqcontextt.execute("studentPerformBlk();");
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";

	}

	public String submit() {
		logger.debug("------inside submit-----");
		String status = "";
		SmsController controller = null;
		try {
			setBoxflag(false);
			setBoxflag1(false);
			setBoxflag2(false);
			setBoxflag3(false);
			if (validate(true)) {
				String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
				int schoolid=Integer.parseInt(schoolID);
				if (personID != null) {
					studentPerformanceDataBean.setAppearancecheck(check);
					studentPerformanceDataBean.setAttitudecheck(check1);
					if(studentPerformanceDataBean.isAppearancecheck() == false){
						studentPerformanceDataBean.setAppOthers("");
					}
					if(studentPerformanceDataBean.isAttitudecheck() == false){
						studentPerformanceDataBean.setAttOthers("");
					}
					
					status = controller.insertStudentPerform(personID, studentPerformanceDataBean);
					logger.debug("status -- " + status);
					studentPerformanceDataBean.setPerformStudIDs(studentPerformanceDataBean.getPerformStudID());
					if (status.equalsIgnoreCase("Success")) {
						MailSendJDBC.studentPerformInsert(studentPerformanceDataBean, schoolName,schoolid);
						/*String pdfStatus = generatePdf(studentPerformanceDataBean);
						logger.debug("pdf status -- " + pdfStatus);*/
						/*String emailStatus = sendEmail(studentPerformanceDataBean);
						logger.debug("email status -- " + emailStatus);
						if (emailStatus.equalsIgnoreCase("Success")) {*/
							FacesMessage message = new FacesMessage("Succesful",
									studentPerformanceDataBean.getPerformStudID() + " Performanse is inserted .");
							FacesContext.getCurrentInstance().addMessage(null, message);
							studentPerformanceDataBean.setPerformStudID("");
							studentPerformanceDataBean.setPerClassSection("");
							studentPerformanceDataBean.setStudentAppearance(null);
							studentPerformanceDataBean.setStudentAttitude(null);
							studentPerformanceDataBean.setAppOthers("");
							studentPerformanceDataBean.setAttitudeOther("");
							studentPerformanceDataBean.setAttOthers("");
							setCheck1(false);
							setCheck(false);
							RequestContext reqcontext = RequestContext.getCurrentInstance();
							reqcontext.execute("PF('studentperformUI').hide();");
							reqcontext.execute("PF('studentRegDialog1').show();");
							setBoxflag(true);
						/*}*/
					} else if (status.equalsIgnoreCase("failure")) {
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('studentperformUI').hide();");
						reqcontext.execute("PF('studentRegDialog2').show();");
						setBoxflag1(true);
					}/*else if(status.equalsIgnoreCase("Fail")){
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('studentperformUI').hide();");
						reqcontext.execute("PF('studentRegDialog4').show();");
						setBoxflag3(true);
					}*/
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";

	}

	public void valueChangeHandler1(ValueChangeEvent event2) {
		logger.debug("-----inside valueChangeHandler1------");
		String s = event2.getNewValue().toString();
		logger.debug("-------value------" + s);
		if (event2.getNewValue().toString().trim().equalsIgnoreCase("true")) {
			setFlag(false);
		} else {
			setFlag(true);
		}
	}

	public void valueChangeHandler2(ValueChangeEvent event3) {
		logger.debug("-----inside valueChangeHandler2------");
		String s = event3.getNewValue().toString();
		logger.debug("-------value------" + s);
		if (event3.getNewValue().toString().trim().equalsIgnoreCase("true")) {
			setFlag1(false);
		} else {
			setFlag1(true);
		}
	}

	public String returnToHome2() {
		studentPerformanceDataBean.setPerformStudID("");
		studentPerformanceDataBean.setPerClassSection("");
		studentPerformanceDataBean.setStudentAppearance(null);
		studentPerformanceDataBean.setStudentAttitude(null);
		studentPerformanceDataBean.setAttOthers("");
		studentPerformanceDataBean.setAppOthers("");

		logger.debug("Calling");
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				return "SuccessHome";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "SuccessHome";

	}

	public void valueChangeHandler3(ValueChangeEvent event4) throws SQLException {
		logger.debug("-----inside valueChangeHandler3------");
		String s = event4.getNewValue().toString();
		logger.debug("-------value------" + s);
		SmsController controller = null;
		String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		int schoolid=Integer.parseInt(schoolID);
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (SmsController) ctx.getBean("controller");
		if (event4.getNewValue().toString().trim().equalsIgnoreCase("true")) {
			if (validate1(true)) {
				controller.getmailsID(studentPerformanceDataBean);
				MailSendJDBC.studentPerformUrgent(studentPerformanceDataBean, schoolName,schoolid);
				/*String emailStatus = sendEmail1(studentPerformanceDataBean);
				if (emailStatus.equalsIgnoreCase("Success")) {*/
					FacesMessage message = new FacesMessage("Succesful",
							studentPerformanceDataBean.getPerformStudID() + " Performanse is inserted .");
					FacesContext.getCurrentInstance().addMessage(null, message);
					studentPerformanceDataBean.setPerformStudID("");
					studentPerformanceDataBean.setPerClassSection("");
					studentPerformanceDataBean.setStudentAppearance(null);
					studentPerformanceDataBean.setStudentAttitude(null);
					setBoxflag2(true);
				/*}*/
			}
		} else {
			setBoxflag2(false);
		}
	}

	/*private String sendEmail(StudentPerformanceDataBean studentPerformanceDataBean2) {
		String status = "Fail";
		String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
		try {
			InternetAddress[] toAddress = new InternetAddress[studentPerformanceDataBean2.getMailid().size()];
			for (int i = 0; i < studentPerformanceDataBean2.getMailid().size(); i++) {
				toAddress[i] = new InternetAddress(studentPerformanceDataBean2.getMailid().get(i));
			}
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
					+ "<h3>Dear " + "Parents"
					+"<br></br>"
					+"Your son/daughter "
					+studentPerformanceDataBean2.getPerformStudID() + ",</h3>"
					+ "<p>Welcome on-board into "
					+ schoolName
					+ " family. Please find the Appearance and Attitude Status enclosed with is mail."
					+ "</p>"
					+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
					+ "<tr>" + "<td>Appearance" + "</td>" + "<td>"
					+ Arrays.deepToString(studentPerformanceDataBean2.getStudentAppearance()) + "</td>" + "</tr>"
					+ "<tr>" + "<td>Attitude" + "</td>" + "<td>"
					+ Arrays.deepToString(studentPerformanceDataBean2.getStudentAttitude()) + "</td>" + "</tr>"
					+ "</table>" + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>"
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
				message.setSubject("Student Performance and Behaviour");
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
				throw new RuntimeException(e);
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}
*/
	/*private String generatePdf(StudentPerformanceDataBean studentPerformanceDataBean2) {
		String status = "fail";
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			if (personID != null) {
				// Create Directory
				File files = new File(paths.getString("sms.studentperformance.pdf") + ft.format(now));
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
				String logo ="";
				if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
					logo = paths.getString(schoolID+"_LOGO");
					document.add(Image.getInstance(getClass().getClassLoader().getResource(logo)));
				}else{
					logo = studentPerformanceDataBean.getSchoolLogo();
					document.add(Image.getInstance(logo));
				}
				float[] columnWidths = { 20f, 1f };
				Font font3 = new Font(Font.TIMES_ROMAN, 27);
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

				
				 * //PDF open Windows and MAC File pdfFile = new
				 * File(files+paths.getString("path_context").toString()+teacherDataBean2.getTeaUsername()+".pdf");
				 * if(pdfFile.exists()){ if (Desktop.isDesktopSupported()) {
				 * Desktop.getDesktop().open(pdfFile); } else { logger.debug(
				 * "Awt Desktop is not supported!"); }
				 * 
				 * } else { logger.debug("File is not exists!"); }
				 

				logger.debug("Done");
				status = "Success";
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return status;
	}*/

	/*private String sendEmail1(StudentPerformanceDataBean studentPerformanceDataBean2) {
		String status = "Fail";
		String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
		try {
			for (int i = 0; i < studentPerformanceDataBean2.getMailid().size(); i++) {
				String to = studentPerformanceDataBean2.getMailid().get(i);
				Properties prop = new Properties();
				prop.put("mail.smtp.host", text.getString("sms.mail.host"));
				prop.put("mail.smtp.socketFactory.port", text.getString("sms.mail.port"));
				prop.put("mail.smtp.socketFactory.class", text.getString("sms.mail.class"));
				prop.put("mail.smtp.auth", text.getString("sms.mail.auth"));
				prop.put("mail.smtp.port", text.getString("sms.mail.smtp.port"));

				String body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>"
						+ "<center>" + "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>"
						+ "</header>" + "<h3>Dear " + studentPerformanceDataBean2.getPerformStudID() + ",</h3>"
						+ "<p>Welcome on-board into"
						+ schoolName
						+ "family. Required immediate attention. Please visit the school."
						+ "All the very best in your new assignment</p>" + "<footer>" + "<center>"
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
					message.setSubject("SMS Confirmation");
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

			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}*/
	
	public boolean validate(boolean flag) {
		boolean valid = true;
		String fieldName;
		String sapp = null;
		String satt = null;
		FacesContext fc = FacesContext.getCurrentInstance();
		Object[] obj1 = studentPerformanceDataBean.getStudentAppearance();
		for (int x = 0; x < obj1.length; x++) {
			Object obj = obj1[x];
			sapp = obj.toString();
			logger.debug(sapp);
			logger.debug("-------"+sapp);
			
		}
		Object[] objj1 = studentPerformanceDataBean.getStudentAttitude();
		for (int x = 0; x < objj1.length; x++) {
			Object objj = objj1[x];
			satt = objj.toString();
			logger.debug(satt);
		}
		if (studentPerformanceDataBean.getTeaDob() == null) {
			

				fieldName = CommonValidate.findComponentInRoot("TDob").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.studebt.date")));
			
			valid = false;
		}
		
		if (StringUtils.isEmpty(studentPerformanceDataBean.getPerformStudID())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("perStudentID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Student Name."));
			}
			valid = false;
		}
		if (studentPerformanceDataBean.getPerClassSection().equalsIgnoreCase("select")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("perClass").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Student Class."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(sapp) && StringUtils.isEmpty(satt) && check == false && check1==false) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("perAppearance").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Student Appearance or Attitude"));
			}
			valid = false;
		}
		
		if(check == true){
			if(StringUtils.isEmpty(studentPerformanceDataBean.getAppOthers())){
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("checkOther").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Reason"));
			}
			valid = false;
			}else if(!StringUtils.isEmpty(studentPerformanceDataBean.getAppOthers())){
				if(!CommonValidate.validateName(studentPerformanceDataBean.getAppOthers()))
				{
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("checkOther").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Reason"));
				}
				valid = false;
			}
			}
		}
		
		if(check1 == true){
			if(StringUtils.isEmpty(studentPerformanceDataBean.getAttOthers()))
			{
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("checkAttOther").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Reason"));
			}
			valid = false;
			}else if(!StringUtils.isEmpty(studentPerformanceDataBean.getAttOthers())){
				if(!CommonValidate.validateName(studentPerformanceDataBean.getAttOthers())){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("checkAttOther").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Reason"));
				}
				valid = false;
				}
			}
		}
		/*
		 * else if (StringUtils.isEmpty(satt) &&
		 * StringUtils.isEmpty(studentPerformanceDataBean.getAttOthers())) {
		 * if(flag){ fieldName =
		 * CommonValidate.findComponentInRoot("perAttitude").getClientId(fc);
		 * fc.addMessage(fieldName, new FacesMessage(
		 * "Please Select the Student Attitude")); } valid = false; }
		 */
		return valid;
	}

	public boolean validate1(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (StringUtils.isEmpty(studentPerformanceDataBean.getPerformStudID())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("perStudentID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Student Name."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(studentPerformanceDataBean.getPerClassSection())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("perClass").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Student Class."));
			}
			valid = false;
		}
		return valid;
	}
	
	public boolean globalperformancevalidate(boolean flag) {
		System.out.println("------------inside validate --------------------"+studentPerformanceDataBean.getStudentAppearance());
		boolean valid = true;SmsController controller = null;
		String fieldName;
		String sapp = null;
		String satt = null;String status = "";
		FacesContext fc = FacesContext.getCurrentInstance();
		Object[] obj1 = studentPerformanceDataBean.getStudentAppearance();
		for (int x = 0; x < obj1.length; x++) {
			Object obj = obj1[x];
			sapp = obj.toString();
			logger.debug(sapp);
			logger.debug("-------"+sapp);
			
		}
		Object[] objj1 = studentPerformanceDataBean.getStudentAttitude();
		for (int x = 0; x < objj1.length; x++) {
			Object objj = objj1[x];
			satt = objj.toString();
			logger.debug(satt);
		}
		if (studentPerformanceDataBean.getTeaDob() == null) {
			

				fieldName = CommonValidate.findComponentInRoot("TDob").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.studebt.date")));
			
			valid = false;
		}else{
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			studentPerformanceDataBean.setSchoolID(schoolid);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			status = controller.performanceCheck(studentPerformanceDataBean);
			System.out.println("status--------------"+status);
			if(status.equalsIgnoreCase("Exist")){
				System.out.println("--------------Exist----------------");
				fieldName = CommonValidate.findComponentInRoot("TDob").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.studebt.validdate")));
				valid = false;
			}
		}
		
		if (StringUtils.isEmpty(sapp) && StringUtils.isEmpty(satt) && check == false && check1==false) {
			System.out.println("----333333333-----------");
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("perAppearance1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Student Appearance or Attitude"));
			}
			valid = false;
		}
		System.out.println("-------4444444--------valid----------------"+valid);
		if(check == true){
			if(StringUtils.isEmpty(studentPerformanceDataBean.getAppOthers())){
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("checkOther1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Reason"));
			}
			valid = false;
			}else if(!StringUtils.isEmpty(studentPerformanceDataBean.getAppOthers())){
				if(!CommonValidate.validateName(studentPerformanceDataBean.getAppOthers()))
				{
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("checkOther1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Reason"));
				}
				valid = false;
			}
			}
		}
		System.out.println("-------55555555--------valid----------------"+valid);
		if(check1 == true){
			if(StringUtils.isEmpty(studentPerformanceDataBean.getAttOthers()))
			{
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("checkAttOther1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Reason"));
			}
			valid = false;
			}else if(!StringUtils.isEmpty(studentPerformanceDataBean.getAttOthers())){
				if(!CommonValidate.validateName(studentPerformanceDataBean.getAttOthers())){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("checkAttOther1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Reason"));
				}
				valid = false;
				}
			}
		}
		
		System.out.println("---------------valid----------------"+valid);
		return valid;
	}
	
	public String globalstudPerformanceSave() {
		System.out.println("------inside globalstudPerformanceSave-----"+studentPerformanceDataBean.getTeaDob()+"-----"+studentPerformanceDataBean.getPerformStudID());
		String status = "";
		SmsController controller = null;
		try {
			if (globalperformancevalidate(true)) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				
				//Never used any where
				//String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
				//int schoolid=Integer.parseInt(schoolID);
				System.out.println(" ---------------------"+personID);
				studentPerformanceDataBean.setPerformStudID(studentPerformanceDataBean.getStuFirstName()+studentPerformanceDataBean.getStuLastName()+"/"+studentPerformanceDataBean.getPerformStudID());
				if (personID != null) {
					studentPerformanceDataBean.setAppearancecheck(check);
					studentPerformanceDataBean.setAttitudecheck(check1);
					if(studentPerformanceDataBean.isAppearancecheck() == false){
						studentPerformanceDataBean.setAppOthers("");
					}
					if(studentPerformanceDataBean.isAttitudecheck() == false){
						studentPerformanceDataBean.setAttOthers("");
					}
					
					status = controller.insertStudentPerform(personID, studentPerformanceDataBean);
					logger.debug("status -- " + status);
					studentPerformanceDataBean.setPerformStudIDs(studentPerformanceDataBean.getPerformStudID());
					System.out.println("status ---------------------"+status);
					if (status.equalsIgnoreCase("Success")) {
							FacesMessage message = new FacesMessage("Succesful",
									studentPerformanceDataBean.getPerformStudID() + " Performanse is inserted .");
							FacesContext.getCurrentInstance().addMessage(null, message);
							studentPerformanceDataBean.setTeaDob(null);
							studentPerformanceDataBean.setStudentAppearance(null);
							studentPerformanceDataBean.setStudentAttitude(null);
							studentPerformanceDataBean.setAppOthers("");
							studentPerformanceDataBean.setAttitudeOther("");
							studentPerformanceDataBean.setAttOthers("");
							setCheck1(false);
							setCheck(false);
							RequestContext reqcontext = RequestContext.getCurrentInstance();
							reqcontext.execute("PF('performancedlg').hide();");
							reqcontext.execute("PF('performancedlgsave').show();");
							setBoxflag(true);
					}else{
						/*RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('performancesavefail').show();");*/
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			e.printStackTrace();
		}
		return "";

	}
	
	public String viewperformancedlg(){
		try{
			studentPerformanceDataBean.setPerformStudID("");
			studentPerformanceDataBean.setPerClassSection("");
			studentPerformanceDataBean.setStudentAppearance(new String[0]);
			studentPerformanceDataBean.setStudentAttitude(new String[0]);
			studentPerformanceDataBean.setAttOthers("");
			studentPerformanceDataBean.setAppOthers("");
			studentPerformanceDataBean.setTeaDob(null);
			RequestContext reqcontext = RequestContext.getCurrentInstance();
			reqcontext.execute("PF('performancedlg').show();");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
