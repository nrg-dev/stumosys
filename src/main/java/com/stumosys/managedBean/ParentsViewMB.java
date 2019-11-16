package com.stumosys.managedBean;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.stumosys.controler.SmsController;
import com.stumosys.domain.ParentsDataBean;
import com.stumosys.domain.StudentDataBean;
/*import com.stumosys.domain.TeacherDataBean;
import com.stumosys.shared.ParentDetail;
*/import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name = "parentsViewMB")
@RequestScoped
public class ParentsViewMB {

	ParentsDataBean parentsDataBean = new ParentsDataBean();
	StudentDataBean studentDataBean = new StudentDataBean();
	public List<ParentsDataBean> parentsBean;
	Date now = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	private List<String> classSectionList = null;
	private List<String> classSectionList1 = null;
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	private static Logger logger = Logger.getLogger(ParentsViewMB.class);
	private List<ParentsDataBean> ImageListPath = null;
	private boolean flag=false;
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public List<ParentsDataBean> getParentsBean() {
		return parentsBean;
	}

	public void setParentsBean(List<ParentsDataBean> parentsBean) {
		this.parentsBean = parentsBean;
	}

	public List<ParentsDataBean> getImageListPath() {
		return ImageListPath;
	}

	public void setImageListPath(List<ParentsDataBean> imageListPath) {
		ImageListPath = imageListPath;
	}

	public List<String> getClassSectionList1() {
		return classSectionList1;
	}

	public void setClassSectionList1(List<String> classSectionList1) {
		this.classSectionList1 = classSectionList1;
	}

	private boolean tableflag = false;
	private List<ParentsDataBean> patentTableList = null;
	private List<ParentsDataBean> patentFilterList;
	private List<ParentsDataBean> parentList = null;
	private List<String> studentIDList = null;
	private UploadedFile file;
	private boolean boxflag = false;
	private boolean delBoxflag = false;
	private boolean flag1 = false;
	private boolean flag2 = false;
	private String studClass;
	private List<String> teaClass = null;

	public List<String> getTeaClass() {
		return teaClass;
	}

	public void setTeaClass(List<String> teaClass) {
		this.teaClass = teaClass;
	}

	public String getStudClass() {
		return studClass;
	}

	public void setStudClass(String studClass) {
		this.studClass = studClass;
	}

	public boolean isFlag2() {
		return flag2;
	}

	public void setFlag2(boolean flag2) {
		this.flag2 = flag2;
	}

	public boolean isFlag1() {
		return flag1;
	}

	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}

	/**
	 * @return the parentsDataBean
	 */
	public ParentsDataBean getParentsDataBean() {
		return parentsDataBean;
	}

	/**
	 * @param parentsDataBean
	 *            the parentsDataBean to set
	 */
	public void setParentsDataBean(ParentsDataBean parentsDataBean) {
		this.parentsDataBean = parentsDataBean;
	}

	/**
	 * @return the patentTableList
	 */
	public List<ParentsDataBean> getPatentTableList() {
		return patentTableList;
	}

	/**
	 * @param patentTableList
	 *            the patentTableList to set
	 */
	public void setPatentTableList(List<ParentsDataBean> patentTableList) {
		this.patentTableList = patentTableList;
	}

	/**
	 * @return the patentFilterList
	 */
	public List<ParentsDataBean> getPatentFilterList() {
		return patentFilterList;
	}

	/**
	 * @param patentFilterList
	 *            the patentFilterList to set
	 */
	public void setPatentFilterList(List<ParentsDataBean> patentFilterList) {
		this.patentFilterList = patentFilterList;
	}

	/**
	 * @return the studentIDList
	 */
	public List<String> getStudentIDList() {
		return studentIDList;
	}

	/**
	 * @param studentIDList
	 *            the studentIDList to set
	 */
	public void setStudentIDList(List<String> studentIDList) {
		this.studentIDList = studentIDList;
	}

	/**
	 * @return the file
	 */
	public UploadedFile getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(UploadedFile file) {
		this.file = file;
	}

	/**
	 * @return the tableflag
	 */
	public boolean isTableflag() {
		return tableflag;
	}

	/**
	 * @param tableflag
	 *            the tableflag to set
	 */
	public void setTableflag(boolean tableflag) {
		this.tableflag = tableflag;
	}

	public String parentsView() {
		logger.debug("Inside.........");
		parentsDataBean.setParStuClass("");
		parentsDataBean.setParStuSection("");
		classSectionList = null;
		setTableflag(false);
		SmsController controller = null;
		String status = "";
		String rollnumber = "";flag=false;
		try {
			classSectionList = new ArrayList<String>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			status = controller.getRoll(personID);
			if (personID != null) {
				if (status.equalsIgnoreCase("Admin")) {
					setFlag2(false);
					setFlag1(true);
					classSectionList = controller.getClassSection(personID);
				} else if (status.equalsIgnoreCase("Student")) {
					setFlag1(false);
					setFlag2(true);
					rollnumber = controller.getRollNumber(personID);
					logger.debug("------student roll number-----" + rollnumber);
					studClass = controller.getStudClsSection(rollnumber);
					parentsDataBean.setParStuClass(studClass);
					patentTableList = controller.getParentInfo(personID, parentsDataBean);					
					setTableflag(true);

				} else if (status.equalsIgnoreCase("Teacher")) {

					setFlag1(true);
					setFlag2(false);
					classSectionList = new ArrayList<String>();
					//rollnumber = controller.getRollNumber(personID);
					rollnumber=personID;
					classSectionList1 = controller.getTeaClass(rollnumber, studentDataBean);
					logger.debug("----------outside controller------");
					for (int i = 0; i < studentDataBean.getTeaClassList().size(); i++) {
						classSectionList.add(studentDataBean.getTeaClassList().get(i).getTeaclssection());
					}
					logger.debug("-----teaClass---" + teaClass);

				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "parentsInfo";

	}

	public String search() {

		try {
			logger.debug("---------- submit Method -----");
			if (validate(true)) {
				return "parentsInformations";
			}
		} catch (Exception e) {
			logger.debug("---------- submit Method  Calling-----");

			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}

	private boolean validate(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();

		if (parentsDataBean.getParStuClass().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("parViewClass").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.class")));
			}
			valid = false;

		}
		if (parentsDataBean.getParStuSection().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("parViewSec").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.section")));
			}
			valid = false;

		}
		return valid;
	}

	public String reset() {
		parentsDataBean.setParStuClass("");
		parentsDataBean.setParStuSection("");

		return "";

	}

	/**
	 * @return the classSectionList
	 */
	public List<String> getClassSectionList() {
		return classSectionList;
	}

	/**
	 * @param classSectionList
	 *            the classSectionList to set
	 */
	public void setClassSectionList(List<String> classSectionList) {
		this.classSectionList = classSectionList;
	}
	/* Ragulan OCT 25   classChange*/
	public void classChange(ValueChangeEvent event) {
		SmsController controller = null;
		String status = "";flag=false;
		try {
			patentTableList=new ArrayList<ParentsDataBean>();
			if (event.getNewValue().toString() != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				status = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role").toString();
				String schoolID=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID").toString();
				if (personID != null) {
					if (status.equalsIgnoreCase("Admin")) {
						parentsDataBean.setParStuClass(event.getNewValue().toString());
						patentTableList = controller.getParentInfo(personID, parentsDataBean);
						logger.debug("size "+patentTableList.size());
						setTableflag(true);
						setFlag2(false);
						flag=true;
					} else if (status.equalsIgnoreCase("Teacher")) {
						parentsDataBean.setParStuClass(event.getNewValue().toString());
						patentTableList = controller.getParentInfo(personID, parentsDataBean);
						setTableflag(true);
						setFlag2(false);
						if(schoolID.equalsIgnoreCase(paths.getString("SMRV.SCHOOLID"))){
							flag=true;
						}else{
							flag=false;
						}
					}
				} else {
					setTableflag(false);
				}
			} else {
				setTableflag(false);
			}
		} catch (NullPointerException e1) {
			setTableflag(false);
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}

	
	public String addStudent()
	{
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if(studentCheck(true))
		{
			int count=0;
			parentsDataBean=new ParentsDataBean();
			parentsDataBean.setParFirstName(fname);parentsDataBean.setParLastName(lname);
			parentsDataBean.setParEmail(mail);parentsDataBean.setParPhoneNo(phno);
			parentsDataBean.setParentRelation(relation);parentsDataBean.setParParentID(parentID);
			parentsDataBean.setParFile(filename);parentsDataBean.setParfilePath(filepath);
			parentsDataBean.setParPathDate(date);
			if(parentsBean.size()>1){
				for (int i = 0; i < parentsBean.size()-1; i++) {
					if (studentID.equalsIgnoreCase(parentsBean.get(i).getParStudID())) count++;
				}
			}		
			logger.debug("count "+count);
			if(count>0){
				fieldName = CommonValidate.findComponentInRoot("parStudentID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.astudentID")));
				parentsBean.remove(parentsBean.size()-1);
				parentsDataBean.setParStuClass(classname);parentsDataBean.setParStudID(studentID);				
			}
			else{
				parentsDataBean.setParFirstName(fname);parentsDataBean.setParLastName(lname);
				parentsDataBean.setParEmail(mail);parentsDataBean.setParPhoneNo(phno);
				parentsDataBean.setParentRelation(relation);parentsDataBean.setParParentID(parentID);
				parentsDataBean.setParFile(filename);parentsDataBean.setParPathDate(date);
				parentsDataBean.setParfilePath(filepath);
			}
		}
		else {
			parentsBean.remove(parentsBean.size()-1);
		}
		return "";
	}
	public String studentID;
	public String classname;
	public String fname;
	public String lname;
	public String relation;
	public String parentID;
	public String mail;
	public String phno;
	public UploadedFile filename;
	public String filepath;
	public Date date;
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhno() {
		return phno;
	}

	public void setPhno(String phno) {
		this.phno = phno;
	}

	public UploadedFile getFilename() {
		return filename;
	}

	public void setFilename(UploadedFile filename) {
		this.filename = filename;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public boolean studentCheck(boolean flag){
		String fieldName;
		boolean valid=true;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (parentsDataBean.getParStuClass().equalsIgnoreCase("Choose")) {
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("parClassID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.select")));
			}
			valid=false;
		}
		if (StringUtils.isBlank(parentsDataBean.getParStudID())) {
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("parStudentID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.studentID")));
			}
			valid=false;
		}	
		studentID=parentsDataBean.getParStudID();
		classname=parentsDataBean.getParStuClass();	
		fname=parentsDataBean.getParFirstName();lname=parentsDataBean.getParLastName();
		mail=parentsDataBean.getParEmail();phno=parentsDataBean.getParPhoneNo();
		relation=parentsDataBean.getParentRelation();parentID=parentsDataBean.getParParentID();
		filename=parentsDataBean.getParFile();filepath=parentsDataBean.getParfilePath();
		date=parentsDataBean.getParPathDate();
		return valid;
	}
	
	public void delete() {
		SmsController controller = null;
		try {
			logger.debug("-----Inside call Parents delete method ------" + parentsDataBean.getParParentID());
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");

			if (personID != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String status = controller.deleteParent(personID, parentsDataBean);
				if (status.equalsIgnoreCase("Success")) {
					setDelBoxflag(true);
				} else {
					setDelBoxflag(false);
					setTableflag(false);
					setBoxflag(false);
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}

	/**
	 * @return the parentList
	 */
	public List<ParentsDataBean> getParentList() {
		return parentList;
	}

	/**
	 * @param parentList
	 *            the parentList to set
	 */
	public void setParentList(List<ParentsDataBean> parentList) {
		this.parentList = parentList;
	}

	public void imageview(OutputStream out, Object data) throws IOException {
		String s = paths.getString("sms.parents.photo");
		BufferedImage img = ImageIO.read(
				new File(s + ft.format(parentsDataBean.getParPathDate()) + "/" + parentsDataBean.getParfilePath()));
		ImageIO.write(img, "png", out);
	}

	public void classChange1(ValueChangeEvent event) {
		SmsController controller = null;
		try {
			studentIDList = new ArrayList<String>();
			logger.debug("classChange Calling");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String name = event.getNewValue().toString();
			logger.debug(name);
			if (personID != null && name != null) {
				studentIDList = controller.getStudentRollNumber(personID, name);
				logger.debug(studentIDList);
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}

	public String dummyAction(FileUploadEvent event) throws IOException {
		this.setFile(event.getFile());
		parentsDataBean.setParFile(event.getFile());
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);
		logger.debug(parentsDataBean.getParFile().getContentType());

		return "";
	}

	/**
	 * @return the boxflag
	 */
	public boolean isBoxflag() {
		return boxflag;
	}

	/**
	 * @param boxflag
	 *            the boxflag to set
	 */
	public void setBoxflag(boolean boxflag) {
		this.boxflag = boxflag;
	}

	/**
	 * @return the delBoxflag
	 */
	public boolean isDelBoxflag() {
		return delBoxflag;
	}

	/**
	 * @param delBoxflag
	 *            the delBoxflag to set
	 */
	public void setDelBoxflag(boolean delBoxflag) {
		this.delBoxflag = delBoxflag;
	}

	public String returnToHome() {
		SmsController controller = null;
		logger.debug("Calling");
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				patentTableList = controller.getParentInfo(personID, parentsDataBean);
				setBoxflag(false);
				setDelBoxflag(false);
				setTableflag(true);
				return "parentviewPageLoad";
			} else {
				setTableflag(false);
				setBoxflag(false);
				setDelBoxflag(false);
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";

	}

	public String update() {

		setBoxflag(false);
		parentList = null;
		try {
			parentList = new ArrayList<ParentsDataBean>();

			logger.debug("Inside Update method calling....");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (validate1(true)) {
				if (personID != null) {
					RequestContext reqcontextt = RequestContext.getCurrentInstance();
					reqcontextt.execute("check();");

				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return null;

	}

	public String updateParent() {
		SmsController controller = null;
		setBoxflag(false);
		parentList = null;

		try {
			String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			parentList = new ArrayList<ParentsDataBean>();
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");

			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			if (personID != null) {
				if (parentsDataBean.getParFile() != null) {
					String s = parentsDataBean.getParFile().getContentType();
					logger.debug("file Type " + s);
					String type = s.substring(s.lastIndexOf("/") + 1);
					logger.debug(type);

					copyFile(parentsDataBean.getParParentID(), parentsDataBean.getParFile().getInputstream(), type);

					logger.debug(parentsDataBean.getParParentID() + "." + type);
					String path = ft.format(now) + "/" + parentsDataBean.getParParentID() + "." + type;
					parentsDataBean.setParfilePath(path);
					logger.debug(parentsDataBean.getParfilePath());
				} else {
					parentsDataBean.setParFile(null);
				}
				if(parentsBean.size()==0){
					ParentsDataBean parents=new ParentsDataBean();
					parents.setParStuClass(parentsDataBean.getParStuClass());
					parents.setParStudID(parentsDataBean.getParStudID());
					parentsBean.add(parents);
					parentsDataBean.setParentdetails(parentsBean);
				}else{
					parentsDataBean.setParentdetails(parentsBean);
				}
				/*if(parentsDataBean.getParPhoneNo().startsWith("0")){
					String phoneno=parentsDataBean.getParPhoneNo().substring(1);
					parentsDataBean.setParPhoneNo(text.getString("sms.phno")+phoneno);
				}else if(parentsDataBean.getParPhoneNo().startsWith(text.getString("sms.phno"))){
					parentsDataBean.setParPhoneNo(parentsDataBean.getParPhoneNo());
				}else{
					parentsDataBean.setParPhoneNo(text.getString("sms.phno")+parentsDataBean.getParPhoneNo());
				}*/
				String status = controller.updateParent(personID, parentsDataBean);
				if (status.equalsIgnoreCase("Success")) {
					parentList = controller.getParentDetilsList(personID, parentsDataBean);
					logger.debug("MB List Size" + parentList.size());
					if (parentList.size() > 0) {
						/*String pdfStatus = generatePdf(parentList, parentsDataBean);*/
						MailSendJDBC.parentUpdate(parentsDataBean,schoolName,schoolid);
						/*if (pdfStatus.equalsIgnoreCase("Success")){*/
						/*String emailStatus = sendEmail(parentList, personID, parentsDataBean);
						logger.debug("pdfStatus--" + pdfStatus + "emailStatus" + emailStatus);
						if (pdfStatus.equalsIgnoreCase("Success") && emailStatus.equalsIgnoreCase("Success")) {*/
							parentList = null;
							ImageListPath = null;
							RequestContext reqcontextt = RequestContext.getCurrentInstance();
							reqcontextt.execute("PF('blockUIW').hide();");
							reqcontextt.execute("PF('parentUpdateDialog').show();");
							setBoxflag(true);
						/*}*/
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return "";

	}

	/*private String sendEmail(List<ParentsDataBean> parentList2, String personID, ParentsDataBean parentsDataBean2) {
		String status = "Fail";

		try {
			// String to="robertarjun46@gmail.com";
			String to = parentsDataBean2.getParEmail();
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
				
				 * session = Session.getInstance(prop, this);// this is the
				 * local variable not the instance one
				  MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("SMS Confirmation-" + parentsDataBean2.getParParentID());
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");

				MimeBodyPart messageBodyPart2 = new MimeBodyPart();
				String filename = paths.getString("sms.parents.pdf") + ft.format(now) + "/" + parentsDataBean2.getParParentID()
						+ ".pdf";// change accordingly
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
	}*/

	private String generatePdf(List<ParentsDataBean> parentList2, ParentsDataBean parentsDataBean2) {

		String status = "fail";
		SmsController controller = null;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			
			if (personID != null) {
				ImageListPath = new ArrayList<ParentsDataBean>();
				logger.debug("parid-------" + parentsDataBean2.getParParentID());
				ImageListPath = controller.getImagePath2(personID, parentsDataBean2.getParParentID());
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
					parentsDataBean.setParfilePath(files +paths.getString("path_context").toString() + parentsDataBean2.getParParentID() + ".pdf");
					Document document = new Document(PageSize.A4, 50, 50, 10, 50);
					PdfWriter.getInstance(document, file);
					document.open();
					String logo ="";
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						logo = paths.getString(schoolID+"_LOGO");
						document.add(Image.getInstance(getClass().getClassLoader().getResource(logo)));
					}else{
						logo = parentsDataBean.getSchoolLogo();
						document.add(Image.getInstance(logo));
					}
					document.add(new Paragraph(
							"------------------------------------------------------------------------------------------------------------------------"));

					float[] columnWidths = { 20f, 1f };
					//Font font3 = new Font(Font.TIMES_ROMAN, 27);
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
					PdfPCell nesCell35 = new PdfPCell(new Paragraph("Photo"));
					String tempdate = ft.format(ImageListPath.get(0).getParPathDate());
					String imageLocation = paths.getString("sms.parents.photo") + tempdate + "/"
							+ ImageListPath.get(0).getParfilePath();
					//Image image = Image.getInstance(imageLocation);
					//image.scaleAbsolute(100, 100);
					//PdfPCell nesCell36 = new PdfPCell(image, false);
					nesCell33.setBorder(PdfPCell.NO_BORDER);
					nesCell34.setBorder(PdfPCell.NO_BORDER);
					nesCell35.setBorder(PdfPCell.NO_BORDER);
					//nesCell36.setBorder(PdfPCell.NO_BORDER);
					nestedTable9.addCell(nesCell35);
					//nestedTable9.addCell(nesCell36);
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
					 * File(files+paths.getString("path_context").toString()+parentsDataBean2.getParUsername()+".pdf")
					 * ; if(pdfFile.exists()){ if (Desktop.isDesktopSupported())
					 * { Desktop.getDesktop().open(pdfFile); } else {
					 * logger.debug("Awt Desktop is not supported!"); }
					 * 
					 * } else { logger.debug("File is not exists!"); }
					 */

					logger.debug("Done");
					status = "Success";
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}

	private void copyFile(String fileName, InputStream inputstream, String n) {
		try {

			// Create Directory
			File files = new File(paths.getString("sms.parents.photo") + ft.format(now));
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

	public boolean validate1(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if(parentsBean.size()==0)
		{
			if (parentsDataBean.getParStuClass().equalsIgnoreCase("Choose")) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("parClassID").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.select")));
				}
				valid = false;
			}
			if (StringUtils.isBlank(parentsDataBean.getParStudID())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("parStudentID").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.studentID")));
				}
				valid = false;
			} else if (!StringUtils.isBlank(parentsDataBean.getParStudID())) {
				if (parentsDataBean.getParStudID().length() < 3) {
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("parStudentID").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.vstudentID")));
					}
					valid = false;
				}
			}
		}
		if (StringUtils.isEmpty(parentsDataBean.getParFirstName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("parFName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.fname")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(parentsDataBean.getParFirstName())) {
			if (!CommonValidate.validateName(parentsDataBean.getParFirstName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("parFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.vfname")));
				}
				valid = false;
			} else if (parentsDataBean.getParFirstName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("parFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.vfname")));
				}
				valid = false;
			}
		}
		
		/*if (StringUtils.isEmpty(parentsDataBean.getParLastName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("parLName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.lname")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(parentsDataBean.getParLastName())) {
			if (!CommonValidate.validateName(parentsDataBean.getParLastName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("parLName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.vlame")));
				}
				valid = false;
			} else if (parentsDataBean.getParLastName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("parLName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.vlame")));
				}
				valid = false;
			}
		}
		*/
		if (StringUtils.isEmpty(parentsDataBean.getCountryCode())) {
			/*if (flag) {
				fieldName = CommonValidate.findComponentInRoot("parPhone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.CountryCode")));
			}
			valid = false;*/
		} 
		else{
		
		if (StringUtils.isEmpty(parentsDataBean.getParPhoneNo())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("parPhone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.phno")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(parentsDataBean.getParPhoneNo())) {
			
			if(!CommonValidate.validateNumberOnly(parentsDataBean.getParPhoneNo())){
			if (flag) {
				System.out.println("----->inside number format val----->");
				fieldName = CommonValidate.findComponentInRoot("parPhone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumberinvalid")));
			}
			valid = false;
			}
			
			else if(!CommonValidate.countryValidateNumber(parentsDataBean.getParPhoneNo(),parentsDataBean.getCountryCode().split("-")[1])){
				fieldName = CommonValidate.findComponentInRoot("parPhone").getClientId(fc);
				if (parentsDataBean.getCountryCode().split("-")[1].equalsIgnoreCase("INDIA")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.india.phonevalidation")));
				}
				else if (parentsDataBean.getCountryCode().split("-")[1].equalsIgnoreCase("UAE")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.uae.phonevalidation")));
				}
				else if (parentsDataBean.getCountryCode().split("-")[1].equalsIgnoreCase("INDONESIA")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.indonesia.phonevalidation")));
				}
				else if (parentsDataBean.getCountryCode().split("-")[1].equalsIgnoreCase("MALAYSIA")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.malaysia.phonevalidation")));
				}
				
				valid = false;
			}
			
		}
	}
		
		if (StringUtils.isEmpty(parentsDataBean.getParentRelation())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("parRelation").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.relation")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(parentsDataBean.getParentRelation())) {
			if (!CommonValidate.validateName(parentsDataBean.getParentRelation())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("parRelation").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.vrelation")));
				}
				valid = false;
			} else if (parentsDataBean.getParentRelation().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("parRelation").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.vrelation")));
				}
				valid = false;
			}
		}

		if (StringUtils.isEmpty(parentsDataBean.getParParentID())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("parParentID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.parentId")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(parentsDataBean.getParParentID())) {
			if (parentsDataBean.getParParentID().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("parParentID").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.vparentID")));
				}
				valid = false;
			}
		}
		/*if (StringUtils.isEmpty(parentsDataBean.getParEmail())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("parE-mail").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.email")));
			}
			valid = false;
		} else*/ if (!StringUtils.isEmpty(parentsDataBean.getParEmail())) {
			if (!CommonValidate.validateEmail(parentsDataBean.getParEmail())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("parE-mail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.vemail")));
				}
				valid = false;
			}
		}
		/*if (StringUtils.isEmpty(parentsDataBean.getParPhoneNo())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("parPhone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.phno")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(parentsDataBean.getParPhoneNo())) {
			if(!CommonValidate.validateNumber(parentsDataBean.getParPhoneNo())){
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("parPhone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumberinvalid")));
			}
			valid = false;
			}else if(Integer.parseInt(parentsDataBean.getParPhoneNo() <= 0){
			if (flag) {

			fieldName = CommonValidate.findComponentInRoot("parPhone").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumberinvalid")));
		}
		valid = false;
	}
		else if(parentsDataBean.getParPhoneNo().length() < 11){
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("parPhone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumberinvalid")));
			}
			valid = false;
		}
		}*/
		return valid;
	}

	public void preProcessPDF(Object document) throws  BadElementException, DocumentException, IOException {
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
	
	public void view() {
		SmsController controller = null;
		try {
			parentList = new ArrayList<ParentsDataBean>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			logger.debug("-----Inside call Parents view method ------" + parentsDataBean.getParParentID());
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				parentList = controller.getParentDetilsList(personID, parentsDataBean);
				if (parentList.size() > 0) {
					if (parentList.get(0).getParentList().size() > 0) {
						parentsDataBean.setParParentID(parentList.get(0).getParentList().get(0).getRollNumber());
					} else {
						parentsDataBean.setParParentID("");
					}

					if (parentList.get(0).getParentDetailsList().size() > 0) {
						parentsDataBean.setName(parentList.get(0).getParentDetailsList().get(0).getFirstName() + " "
								+ parentList.get(0).getParentDetailsList().get(0).getLastName());
						parentsDataBean.setParEmail(parentList.get(0).getParentDetailsList().get(0).getEmaiId());
						parentsDataBean
								.setParentRelation(parentList.get(0).getParentDetailsList().get(0).getRelation());
						parentsDataBean.setParPhoneNo(parentList.get(0).getParentDetailsList().get(0).getPhoneNumber());
						//parentsDataBean.setCountryCode(parentList.get(0).getParentDetailsList().get(0).getCountryCode());
						parentsDataBean.setCountryCode(parentList.get(0).getParentDetailsList().get(0).getCountrycode());
					} else {
						parentsDataBean.setName("");
						parentsDataBean.setParEmail("");
						parentsDataBean.setParentRelation("");
						parentsDataBean.setParPhoneNo("");
					}
					if (parentList.get(0).getParentImageList().size() > 0) {
						parentsDataBean.setParfilePath(parentList.get(0).getParentImageList().get(0).getPath());
						parentsDataBean.setParPathDate(parentList.get(0).getParentImageList().get(0).getDate());
					} else {
						parentsDataBean.setParfilePath("");
						parentsDataBean.setParPathDate(null);
					}
					if (parentList.get(0).getParentdetails().size() > 0) {
						for (int i = 0; i < parentList.get(0).getParentdetails().size(); i++) {
							logger.debug("id "+
									parentList.get(0).getParentdetails().get(i).getName());
							if(parentList.get(0).getParentdetails().size()==1) parentsDataBean.setParStudID(
									parentList.get(0).getParentdetails().get(0).getName());
							else if(parentList.get(0).getParentdetails().size()==2) parentsDataBean.setParStudID(
									parentList.get(0).getParentdetails().get(0).getName()+" , "+
							parentList.get(0).getParentdetails().get(1).getName());
							else if(parentList.get(0).getParentdetails().size()==3) parentsDataBean.setParStudID(
									parentList.get(0).getParentdetails().get(0).getName()+" , "+
											parentList.get(0).getParentdetails().get(1).getName()+" , "+
											parentList.get(0).getParentdetails().get(2).getName());
							else if(parentList.get(0).getParentdetails().size()==4) parentsDataBean.setParStudID(
									parentList.get(0).getParentdetails().get(0).getName()+" , "+
											parentList.get(0).getParentdetails().get(1).getName()+" , "+
											parentList.get(0).getParentdetails().get(2).getName()+" , "+
											parentList.get(0).getParentdetails().get(3).getName());
							else if(parentList.get(0).getParentdetails().size()==5) parentsDataBean.setParStudID(
									parentList.get(0).getParentdetails().get(0).getName()+" , "+
											parentList.get(0).getParentdetails().get(1).getName()+" , "+
											parentList.get(0).getParentdetails().get(2).getName()+" , "+
											parentList.get(0).getParentdetails().get(3).getName()+" , "+
											parentList.get(0).getParentdetails().get(4).getName());
						}
						logger.debug("student ID "+parentsDataBean.getParStudID());
					} else {
						parentsDataBean.setParStudID("");
					}
				} else {
					parentsDataBean.setParParentID("");
					parentsDataBean.setParStudID("");
					parentsDataBean.setParfilePath("");
					parentsDataBean.setParPathDate(null);
					parentsDataBean.setName("");
					parentsDataBean.setParEmail("");
					parentsDataBean.setParentRelation("");
					parentsDataBean.setParPhoneNo("");
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}
	
	
	public String edit() {
		SmsController controller = null;
		studentIDList = null;
		classSectionList = null;
		try {
			parentList = new ArrayList<ParentsDataBean>();
			studentIDList = new ArrayList<String>();
			classSectionList = new ArrayList<String>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			logger.debug("-----Inside call Parents view method ------" + parentsDataBean.getParParentID());
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				parentList = controller.getParentDetilsList(personID, parentsDataBean);
				if (parentList.size() > 0) {
					if (parentList.get(0).getParentList().size() > 0) {
						parentsDataBean.setParParentID(parentList.get(0).getParentList().get(0).getRollNumber());
					} else {
						parentsDataBean.setParParentID("");
					}

					if (parentList.get(0).getParentDetailsList().size() > 0) {
						parentsDataBean.setName(parentList.get(0).getParentDetailsList().get(0).getFirstName() + " "
								+ parentList.get(0).getParentDetailsList().get(0).getLastName());
						parentsDataBean.setParEmail(parentList.get(0).getParentDetailsList().get(0).getEmaiId());
						parentsDataBean
								.setParentRelation(parentList.get(0).getParentDetailsList().get(0).getRelation());
						parentsDataBean.setParPhoneNo(parentList.get(0).getParentDetailsList().get(0).getPhoneNumber());
						parentsDataBean.setParFirstName(parentList.get(0).getParentDetailsList().get(0).getFirstName());
						parentsDataBean.setParLastName(parentList.get(0).getParentDetailsList().get(0).getLastName());
						parentsDataBean.setCountryCode(CommonValidate.phonenumbercode(parentList.get(0).getParentDetailsList().get(0).getCountrycode()));
					} else {
						parentsDataBean.setName("");
						parentsDataBean.setParEmail("");
						parentsDataBean.setParentRelation("");
						parentsDataBean.setParPhoneNo("");
						parentsDataBean.setCountryCode("");
					}
					if (parentList.get(0).getParentImageList().size() > 0) {
						parentsDataBean.setParfilePath(parentList.get(0).getParentImageList().get(0).getPath());
						parentsDataBean.setParPathDate(parentList.get(0).getParentImageList().get(0).getDate());
					} else {
						parentsDataBean.setParfilePath("");
						parentsDataBean.setParPathDate(null);
					}
					if (parentList.get(0).getParentdetails().size() > 0) {
						parentsBean=new ArrayList<ParentsDataBean>();
						for (int i = 0; i < parentList.get(0).getParentdetails().size(); i++) {
							ParentsDataBean students=new ParentsDataBean();
							students.setParStudID(parentList.get(0).getParentdetails().get(i).getName());
							students.setParStuClass(parentList.get(0).getStudentClass().get(i));
							parentsBean.add(students);
						}
						parentsDataBean.setParStuClass("");
						logger.debug(parentsBean.size());
					} else {
						parentsDataBean.setParStudID("");
						parentsDataBean.setParStuClass("");
					}
				} else {
					parentsDataBean.setParParentID("");
					parentsDataBean.setParStudID("");
					parentsDataBean.setParfilePath("");
					parentsDataBean.setParPathDate(null);
					parentsDataBean.setName("");
					parentsDataBean.setParEmail("");
					parentsDataBean.setParentRelation("");
					parentsDataBean.setParPhoneNo("");
				}
				logger.debug("----->" + parentsDataBean.getParStuClass());
				classSectionList = controller.getClassSection(personID);
				studentIDList = controller.getStudentRollNumber(personID, parentsDataBean.getParStuClass());
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "parentEditPage";

	}
	public void phonenumberclear(ValueChangeEvent v){
		parentsDataBean.setParPhoneNo("");
		}
}