package com.stumosys.managedBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
//import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

/*import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
*/import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
*/import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
//import org.apache.poi.util.SystemOutLogger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;
import com.itextpdf.text.Document;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.stumosys.controler.SmsController;
import com.stumosys.domain.TeacherDataBean;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

/**
 * 
 * @author Robert Arjun
 *
 */
@ManagedBean(name = "teacherMB")
@RequestScoped

public class TeacherMB {

	TeacherDataBean teacherDataBean = new TeacherDataBean();

	@ManagedProperty(value = "#{loginMB}")
	private LoginMB loginMB;

	UploadedFile file;
	Date now = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	private List<String> stateList = null;
	private DualListModel<String> classList;
	private List<String> classSectionList = null;
	private DualListModel<String> subjectList;
	private StreamedContent chart;
	private StreamedContent listImage = null;
	private List<TeacherDataBean> ImageListPath = null;
	private boolean boxflag = false;
	private List<String> menulist=null;
	private boolean meuflag=false;
	String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");

	public boolean isMeuflag() {
		return meuflag;
	}

	public void setMeuflag(boolean meuflag) {
		this.meuflag = meuflag;
	}

	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	private static Logger logger = Logger.getLogger(TeacherMB.class);

	public List<String> getMenulist() {
		return menulist;
	}

	public void setMenulist(List<String> menulist) {
		this.menulist = menulist;
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
	 * @return the chart
	 */
	public StreamedContent getChart() {
		return chart;
	}

	/**
	 * @param chart
	 *            the chart to set
	 */
	public void setChart(StreamedContent chart) {
		this.chart = chart;
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
	 * @return the teacherDataBean
	 */
	public TeacherDataBean getTeacherDataBean() {
		return teacherDataBean;
	}

	/**
	 * @param teacherDataBean
	 *            the teacherDataBean to set
	 */
	public void setTeacherDataBean(TeacherDataBean teacherDataBean) {
		this.teacherDataBean = teacherDataBean;
	}

	/**
	 * @return the stateList
	 */
	public List<String> getStateList() {
		return stateList;
	}

	/**
	 * @param stateList
	 *            the stateList to set
	 */
	public void setStateList(List<String> stateList) {
		this.stateList = stateList;
	}

	/**
	 * @return the classList
	 */
	public DualListModel<String> getClassList() {
		return classList;
	}

	/**
	 * @param classList
	 *            the classList to set
	 */
	public void setClassList(DualListModel<String> classList) {
		this.classList = classList;
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

	/**
	 * @return the subjectList
	 */
	public DualListModel<String> getSubjectList() {
		return subjectList;
	}

	/**
	 * @param subjectList
	 *            the subjectList to set
	 */
	public void setSubjectList(DualListModel<String> subjectList) {
		this.subjectList = subjectList;
	}

	/**
	 * @return the listImage
	 */
	public StreamedContent getListImage() {
		if (listImage == null) {
			try {
				listImage = new DefaultStreamedContent(new FileInputStream("E:/t.jpg"), "image/png"); // load
																										// a
																										// dummy
																										// image
			} catch (FileNotFoundException e) {
				logger.warn("Exception -->"+e.getMessage());
			}
		}

		return listImage;
	}

	/**
	 * @param listImage
	 *            the listImage to set
	 */
	public void setListImage(StreamedContent listImage) {
		this.listImage = listImage;
	}

	/**
	 * @return
	 */
	public String teacherPage() {
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
		teacherDataBean.setTeaFirstName("");
		teacherDataBean.setTeaLastName("");
		teacherDataBean.setTeaFatherName("");
		teacherDataBean.setTeaMotherName("");
		teacherDataBean.setTeaDob(null);
		teacherDataBean.setTeaGender("");
		teacherDataBean.setTeaEduQualification("");
		teacherDataBean.setTeaEmail("");
		teacherDataBean.setTeaID("");
		teacherDataBean.setTeaPercentage("");
		teacherDataBean.setTeaPhoneNo("");
		teacherDataBean.setTeaPosition("");
		teacherDataBean.setTeaPermanentAddress("");
		teacherDataBean.setTeaPresentAddress("");
		teacherDataBean.setTeaSubject("");
		teacherDataBean.setTeaTeacherPhoto("");
		teacherDataBean.setTeaWorkingHour("");
		teacherDataBean.setTeaYearOfPassing("");
		teacherDataBean.setTeaPermanentState("");
		teacherDataBean.setTeaPresentState("");
		teacherDataBean.setTeaPermanentZip("");
		teacherDataBean.setTeaPresentZip("");
		teacherDataBean.setTeaFile(null);
		teacherDataBean.setTeaSecurePasword("");
		teacherDataBean.setPath("");
		teacherDataBean.setPathDate(null);
		teacherDataBean.setTeaSubjectTargetList(null);
		teacherDataBean.setTeaClassTargetList(null);
		setSubjectTarget1(new ArrayList<String>());
		setClassTarget1(new ArrayList<String>());
		setBoxflag(false);
		setMeuflag(false);
		teacherDataBean.setMenus(null);
		teacherDataBean.setManagement("");
		teacherDataBean.setTeaCountry("");
		teacherDataBean.setTeaState("");
		teacherDataBean.setTeaCity("");
		teacherDataBean.setTeaPostal("");
		teacherDataBean.setCode("");
		teacherDataBean.setTeaPhoneNo("");
		setChkBox(false);
		teacherDataBean.setTeaCountry1("");
		teacherDataBean.setTeaState1("");
		teacherDataBean.setTeaCity1("");
		teacherDataBean.setTeaPostal1("");
		teacherDataBean.setCode1("");
		teacherDataBean.setTeaPhoneNo1("");
		menulist=new ArrayList<String>();
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		SmsController controller = (SmsController) ctx.getBean("controller");
		//menulist=controller.getmenus();
		//Collections.sort(menulist);
		//subjectSource1 = controller.getSubjectList(personID);
		subjectSource1=new ArrayList<String>();
		classSource1 = controller.getClassSection(personID);			
		classList = new DualListModel<String>(classSource1, classTarget1);
		teacherDataBean.setNames("");
		if(schoolID.equalsIgnoreCase(paths.getString("MALAYSIA.SCHOOLID"))){
			teacherDataBean.setNames("Teacher Photo");
		}else{
			teacherDataBean.setNames("Education Details");
		}
		return "loadTeacherPage";
	}

	public void classChange(){
		String personID ="";
		ApplicationContext ctx=null;
		SmsController controller=null;
		List<String> classList=null;
		try{
			classList=new ArrayList<String>();
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			if(classTarget1.size()==0){
				subjectSource1=new ArrayList<String>();
			}else{
				for(String s : classTarget1) {
					String[] value = s.split(",");
					for(String classname : value) {
						classList=controller.getSubjectListTeacher(personID,classname);
					}
					subjectSource1.addAll(classList);
				}
				Set<String> dublicate=new HashSet<String>(subjectSource1);
				subjectSource1.clear();subjectSource1.addAll(dublicate);
				subjectList = new DualListModel<String>(subjectSource1, subjectTarget1);
			}
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		}
	}
	
	private boolean validate(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();

		if (StringUtils.isEmpty(teacherDataBean.getTeaFirstName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TFName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.fname")));
			}
			valid = false;

		} else if (!StringUtils.isEmpty(teacherDataBean.getTeaFirstName())) {
			if (!CommonValidate.validateName(teacherDataBean.getTeaFirstName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vfname")));
				}
				valid = false;
			} else if (teacherDataBean.getTeaFirstName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vfname")));
				}
				valid = false;
			}
		}
		if(StringUtils.isEmpty(teacherDataBean.getTeaCountry())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TCountry").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.teacherCountry")));
			}
			valid = false;
		}
		else if(!StringUtils.isEmpty(teacherDataBean.getTeaCountry())){
			if(StringUtils.isEmpty(teacherDataBean.getTeaPhoneNo())) {
				/*if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPhoneNo").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumber")));
				}
				valid = false;*/
			}else if(!CommonValidate.validateNumberOnly(teacherDataBean.getTeaPhoneNo())){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPhoneNo").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumberinvalid")));
				}
				valid = false;
			}
			else if(!CommonValidate.countryValidateNumber(teacherDataBean.getTeaPhoneNo(),teacherDataBean.getTeaCountry())){
				fieldName = CommonValidate.findComponentInRoot("TPhoneNo").getClientId(fc);
				if (teacherDataBean.getTeaCountry().equalsIgnoreCase("INDIA")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.india.phonevalidation")));
				}
				else if (teacherDataBean.getTeaCountry().equalsIgnoreCase("UAE")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.uae.phonevalidation")));
				}
				else if (teacherDataBean.getTeaCountry().equalsIgnoreCase("INDONESIA")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.indonesia.phonevalidation")));
				}
				else if (teacherDataBean.getTeaCountry().equalsIgnoreCase("MALAYSIA")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.malaysia.phonevalidation")));
				}
				
				valid = false;
			}
		}
		if (StringUtils.isEmpty(teacherDataBean.getTeaID())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TTeacherID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.teacherId")));
			}
			valid = false;

		}
		if (subjectTarget1.size() <= 0) {
			if (flag) {

				fieldName = CommonValidate.findComponentInRoot("TSubject").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.subject")));
			}
			valid = false;

		}
		if (classTarget1.size() <= 0) {
			if (flag) {

				fieldName = CommonValidate.findComponentInRoot("TClass").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.class")));
			}
			valid = false;

		}
		if(schoolID.equals(paths.getString("SMRV.SCHOOLID"))){
			if(StringUtils.isEmpty(teacherDataBean.getManagement())){
				fieldName = CommonValidate.findComponentInRoot("management").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.management")));
				valid=false;
			}
		}

		return valid;
	}

	public String submit() {
		logger.debug("---------- submit Method Calling-----" + text.getString("sms.teacher.fname"));
		SmsController controller = null;
		setBoxflag(false);
		schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		if (validate(true)) {

			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			logger.debug(personID);
			if (personID != null) {
				stateList = controller.getStateList(personID);
			}
			return "loadTAddressPage";
		} else {
			return "";
		}
	}

	private boolean validate1(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();

		if (StringUtils.isEmpty(teacherDataBean.getTeaPresentAddress())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TPAddress").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.presentaddress")));
			}
			valid = false;

		} else if (teacherDataBean.getTeaPresentAddress().length() < 3) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TPAddress").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vpresentaddress")));
			}
			valid = false;
		}
		/*if (StringUtils.isEmpty(teacherDataBean.getTeaPresentState())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TPresentState").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.presentstate")));
			}
			valid = false;
		} *//*
			 * else
			 * if(!StringUtils.isEmpty(teacherDataBean.getTeaPresentState())){
			 * if(!StringUtils.isEmpty(teacherDataBean.getTeaPresentZip())){
			 * if(flag){ fieldName =
			 * CommonValidate.findComponentInRoot("TPresentZip").getClientId(fc)
			 * ; fc.addMessage(fieldName, new FacesMessage(
			 * "Please Enter the Present Zip.")); } valid = false; }
			 * 
			 * }
			 */
		/*if (StringUtils.isEmpty(teacherDataBean.getTeaPresentZip())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TPresentZip").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.presentzip")));
			}
			valid = false;
		}*/
		if (StringUtils.isEmpty(teacherDataBean.getTeaPermanentAddress())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TpermantAddress").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.permanentaddress")));
			}
			valid = false;
		} else if (teacherDataBean.getTeaPermanentAddress().length() < 3) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TpermantAddress").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vpermanentaddress")));
			}
			valid = false;
		}
		/*if (StringUtils.isEmpty(teacherDataBean.getTeaPermanentState())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TpermantState").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.permanentatate")));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(teacherDataBean.getTeaPermanentZip())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TpermantZip").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.permanentzip")));
			}
			valid = false;
		}*/
		if (StringUtils.isEmpty(teacherDataBean.getTeaPhoneNo())) {
			if (flag) {

				fieldName = CommonValidate.findComponentInRoot("TPhone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumber")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(teacherDataBean.getTeaPhoneNo())) {
			if(!CommonValidate.validateNumberOnly(teacherDataBean.getTeaPhoneNo())){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPhone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.invalidphno")));
				}
				valid = false;
			}else if(teacherDataBean.getTeaPhoneNo().length()<11){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPhone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.invalidphno")));
				}
				valid = false;
			}
			/*if (teacherDataBean.getTeaPhoneNo().equalsIgnoreCase("(+00)000-000-0000")) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPhone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumberinvalid")));
				}
				valid = false;
			}*/
		}

		if (StringUtils.isEmpty(teacherDataBean.getTeaEmail())) {
			{
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TEmail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.email")));
				}
				valid = false;
			}
		} else if (!StringUtils.isEmpty(teacherDataBean.getTeaEmail())) {
			if (!CommonValidate.validateEmail(teacherDataBean.getTeaEmail())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TEmail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.invaildemail")));
				}
				valid = false;
			}
		}

		return valid;

	}

	public String teacherPage1() {
		SmsController controller = null;
		setBoxflag(false);
		setMeuflag(false);
		try {
			if (validate1(true)) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				if(personID !=null){
					 classSectionList=new ArrayList<String>(); 
						List<String> classSource = new ArrayList<String>();
						List<String> classTarget = new ArrayList<String>();
						List<String> subjectSource = new ArrayList<String>();
						List<String> subjectTarget = new ArrayList<String>();

						subjectSource = controller.getSubjectList(personID);
						subjectList = new DualListModel<String>(subjectSource, subjectTarget);

						classSource = controller.getClassSection(personID);
						classList = new DualListModel<String>(classSource, classTarget);
						String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
						int schoolid=Integer.parseInt(schoolID);
						if(schoolid==1||schoolid==2||schoolid==3||schoolid==4|| schoolid==5||schoolid==6){
							setMeuflag(false);
						}else{
							setMeuflag(true);
						}
						return "loadTeacherOther";
				}
			}
			return "";
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			return "";
		}
	}

	private boolean validate2(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();

		if (StringUtils.isEmpty(teacherDataBean.getTeaID())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TTeacherID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.teacherId")));
			}
			valid = false;

		} else if (!StringUtils.isEmpty(teacherDataBean.getTeaID())) {
			if (!CommonValidate.validateName(teacherDataBean.getTeaID())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TTeacherID").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vteacherId")));
				}
				valid = false;
			} else if (teacherDataBean.getTeaID().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TTeacherID").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vteacherId")));
				}
				valid = false;
			}
		}

		if (StringUtils.isEmpty(teacherDataBean.getTeaPosition())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TPost").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.post")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(teacherDataBean.getTeaPosition())) {
			if (!CommonValidate.validateName(teacherDataBean.getTeaPosition())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPost").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vpost")));
				}
				valid = false;
			} else if (teacherDataBean.getTeaPosition().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPost").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vpost")));
				}
				valid = false;
			}
		}

		if (subjectList.getTarget().size() <= 0) {
			if (flag) {

				fieldName = CommonValidate.findComponentInRoot("TSubject").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.subject")));
			}
			valid = false;

		}
		if (classList.getTarget().size() <= 0) {
			if (flag) {

				fieldName = CommonValidate.findComponentInRoot("TClass").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.class")));
			}
			valid = false;

		}

		if (StringUtils.isEmpty(teacherDataBean.getTeaWorkingHour())) {
			{
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TWorking").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.workingHour")));
				}
				valid = false;
			}

		}
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		int schoolid=Integer.parseInt(schoolID);
		if(schoolid==1 || schoolid==2||schoolid==3||schoolid==4||schoolid==5||schoolid==6){
			logger.debug("inside if------");
		}else{
			if (teacherDataBean.getMenus().length==0) {
				if (flag) {
							fieldName = CommonValidate.findComponentInRoot("menus").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage("Please Choose the Menus"));
						}
						valid = false;
				}
		}
		return valid;

	}

	public String teacherPage2() {
		SmsController controller = null;
		String fieldName;
		setBoxflag(false);
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			logger.debug("---------- ccc-----" + classList.getTarget().size());
			/*
			 * teacherDataBean.getTeaClassTargetList().clear();
			 * teacherDataBean.getTeaSubjectTargetList().clear();
			 */

			if (validate2(true)) {
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				if (personID != null) {
					teacherDataBean.setTeaClassTargetList(classList.getTarget());
					teacherDataBean.setTeaSubjectTargetList(subjectList.getTarget());
					logger.debug(teacherDataBean.getTeaClassTargetList().size());
					logger.debug(teacherDataBean.getTeaClassTargetList());
					ApplicationContext ctx = FacesContextUtils
							.getWebApplicationContext(FacesContext.getCurrentInstance());
					controller = (SmsController) ctx.getBean("controller");
					String status = controller.checkTeacherID(teacherDataBean, personID);
					if (status.equalsIgnoreCase("Exsist")) {
						fieldName = CommonValidate.findComponentInRoot("TTeacherID").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.teacherexsistId")));
						return "";
					} else {
						return "TeacherEduPage";
					}
				}
			}
		} catch (Exception e) {
			logger.debug("----------ccc1-----");

			logger.warn("Exception -->"+e.getMessage());
			return "";
		}
		return "";

	}

	/*private boolean validate3(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (StringUtils.isEmpty(teacherDataBean.getTeaEmail())) {
			
			{
				if (flag) {
					System.out.println("==============================>inside mail===============================");
					fieldName = CommonValidate.findComponentInRoot("TEmail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.email")));
				}
				valid = false;
			}
		} else if (!StringUtils.isEmpty(teacherDataBean.getTeaEmail())) {
			if (!CommonValidate.validateEmail(teacherDataBean.getTeaEmail())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TEmail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.invaildemail")));
				}
				valid = false;
			}
		}
		
		if (!StringUtils.isEmpty(teacherDataBean.getTeaPhoneNo())) {
			if (teacherDataBean.getTeaPhoneNo().equalsIgnoreCase("(+00)000-000-0000")) {
				if (flag) {
						System.out.println("==============inside phone number=================");
					fieldName = CommonValidate.findComponentInRoot("TPhone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumberinvalid")));
				}
				valid = false;
			}
		}

		if (StringUtils.isEmpty(teacherDataBean.getTeaEduQualification())) {
			if (flag) {
				System.out.println("==============================>inside Qualification===============================");
				fieldName = CommonValidate.findComponentInRoot("TQualification").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.educationQual")));
			}
			valid = false;

		} else if (!StringUtils.isEmpty(teacherDataBean.getTeaEduQualification())) {
			if (!CommonValidate.validateName(teacherDataBean.getTeaEduQualification())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TQualification").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.veducationQual")));
				}
				valid = false;
			} else if (teacherDataBean.getTeaEduQualification().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TQualification").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.veducationQual")));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(teacherDataBean.getTeaPercentage())) {
			if (flag) {
				System.out.println("==============================>inside teacher percentage===============================");
				fieldName = CommonValidate.findComponentInRoot("TPercentage").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.percentage")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(teacherDataBean.getTeaPercentage())) {
			if (!CommonValidate.validateNumber(teacherDataBean.getTeaPercentage())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPercentage").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.percentageinvalid")));
				}
				valid = false;
			} else if (Double.parseDouble(teacherDataBean.getTeaPercentage()) > 100) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPercentage").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.percentageinvalid")));
				}
				valid = false;
			} else if (Double.parseDouble(teacherDataBean.getTeaPercentage()) <= 0) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPercentage").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.percentageinvalid")));
				}
				valid = false;
			}
		}

		
		if (StringUtils.isEmpty(teacherDataBean.getTeaYearOfPassing())) {
			if (flag) {
				System.out.println("==============================>inside year of passing===============================");
				fieldName = CommonValidate.findComponentInRoot("TPassing").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.yearofPassing")));
			}
			valid = false;

		} else if (!StringUtils.isEmpty(teacherDataBean.getTeaYearOfPassing())) {
			if (!CommonValidate.validateNumber(teacherDataBean.getTeaYearOfPassing())) {
				if (flag) {

					fieldName = CommonValidate.findComponentInRoot("TPassing").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.yearofPassinginvalid")));
				}
				valid = false;
			} else if (Integer.parseInt(teacherDataBean.getTeaYearOfPassing()) <= 0) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPassing").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.yearofPassinginvalid")));
				}
				valid = false;
			}
		}
		if (teacherDataBean.getTeaFile() == null) {
			if (flag) {

				fieldName = CommonValidate.findComponentInRoot("TPhoto").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.photo")));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(teacherDataBean.getTeaFirstName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TFName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.fname")));
			}
			valid = false;

		} else if (!StringUtils.isEmpty(teacherDataBean.getTeaFirstName())) {
			if (!CommonValidate.validateName(teacherDataBean.getTeaFirstName())) {
				if (flag) {
					System.out.println("==============================>inside firstname===============================");
					fieldName = CommonValidate.findComponentInRoot("TFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vfname")));
				}
				valid = false;
			} else if (teacherDataBean.getTeaFirstName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vfname")));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(teacherDataBean.getTeaLastName())) {
			if (flag) {
				System.out.println("==============================>inside lastname===============================");
				fieldName = CommonValidate.findComponentInRoot("TLName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.lname")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(teacherDataBean.getTeaLastName())) {
			if (!CommonValidate.validateName(teacherDataBean.getTeaLastName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TLName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vlname")));
				}
				valid = false;
			} else if (teacherDataBean.getTeaLastName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TLName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vlname")));
				}
				valid = false;
			}
		}

		if (StringUtils.isEmpty(teacherDataBean.getTeaFatherName())) {
			if (flag) {
				System.out.println("==============================>inside fathername===============================");
				fieldName = CommonValidate.findComponentInRoot("TFatherName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vfathername")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(teacherDataBean.getTeaFatherName())) {
			if (!CommonValidate.validateName(teacherDataBean.getTeaFatherName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TFatherName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vfathername")));
				}
				valid = false;
			} else if (teacherDataBean.getTeaFatherName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TFatherName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vfathername")));
				}
				valid = false;
			}
		}

		if (StringUtils.isEmpty(teacherDataBean.getTeaMotherName())) {
			{
				if (flag) {
					System.out.println("==============================>inside mothername===============================");
					fieldName = CommonValidate.findComponentInRoot("TMotherName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.mothername")));
				}
				valid = false;
			}
		} else if (!StringUtils.isEmpty(teacherDataBean.getTeaMotherName())) {
			if (!CommonValidate.validateName(teacherDataBean.getTeaMotherName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TMotherName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vmothername")));
				}
				valid = false;
			} else if (teacherDataBean.getTeaMotherName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TMotherName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vmothername")));
				}
				valid = false;
			}
		}

		if (teacherDataBean.getTeaDob() == null) {
			if (flag) {
				System.out.println("==============================>inside date of birth===============================");
				fieldName = CommonValidate.findComponentInRoot("TDob").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.dob")));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(teacherDataBean.getTeaGender())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TGender").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.gender")));
			}
			valid = false;

		}
		if (StringUtils.isEmpty(teacherDataBean.getTeaPresentAddress())) {
			if (flag) {
				System.out.println("==============================>inside present address===============================");
				fieldName = CommonValidate.findComponentInRoot("TPAddress").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.presentaddress")));
			}
			valid = false;

		} else if (teacherDataBean.getTeaPresentAddress().length() < 3) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TPAddress").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vpresentaddress")));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(teacherDataBean.getTeaPresentState())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TPresentState").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.presentstate")));
			}
			valid = false;
		} 
			 * else
			 * if(!StringUtils.isEmpty(teacherDataBean.getTeaPresentState())){
			 * if(!StringUtils.isEmpty(teacherDataBean.getTeaPresentZip())){
			 * if(flag){ fieldName =
			 * CommonValidate.findComponentInRoot("TPresentZip").getClientId(fc)
			 * ; fc.addMessage(fieldName, new FacesMessage(
			 * "Please Enter the Present Zip.")); } valid = false; }
			 * 
			 * }
			 
		if (StringUtils.isEmpty(teacherDataBean.getTeaPresentZip())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TPresentZip").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.presentzip")));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(teacherDataBean.getTeaPermanentAddress())) {
			if (flag) {
				System.out.println("==============================>inside permenant address===============================");
				fieldName = CommonValidate.findComponentInRoot("TpermantAddress").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.permanentaddress")));
			}
			valid = false;
		} else if (teacherDataBean.getTeaPermanentAddress().length() < 3) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TpermantAddress").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vpermanentaddress")));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(teacherDataBean.getTeaPermanentState())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TpermantState").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.permanentatate")));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(teacherDataBean.getTeaPermanentZip())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TpermantZip").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.permanentzip")));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(teacherDataBean.getTeaPhoneNo())) {
			if (flag) {
				System.out.println("==============================>inside phone number===============================");
				fieldName = CommonValidate.findComponentInRoot("TPhone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumber")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(teacherDataBean.getTeaPhoneNo())) {
			if(!CommonValidate.validateNumberOnly(teacherDataBean.getTeaPhoneNo())){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPhone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.invalidphno")));
				}
				valid = false;
			}else if(teacherDataBean.getTeaPhoneNo().length()<11){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPhone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.invalidphno")));
				}
				valid = false;
			}
			if (teacherDataBean.getTeaPhoneNo().equalsIgnoreCase("(+00)000-000-0000")) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPhone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumberinvalid")));
				}
				valid = false;
			}
		}

		
		if (StringUtils.isEmpty(teacherDataBean.getTeaID())) {
			if (flag) {
				System.out.println("==============================>inside teacher id===============================");
				fieldName = CommonValidate.findComponentInRoot("TTeacherID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.teacherId")));
			}
			valid = false;

		} else if (!StringUtils.isEmpty(teacherDataBean.getTeaID())) {
			if (!CommonValidate.validateName(teacherDataBean.getTeaID())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TTeacherID").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vteacherId")));
				}
				valid = false;
			} else if (teacherDataBean.getTeaID().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TTeacherID").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vteacherId")));
				}
				valid = false;
			}
		}

		if (StringUtils.isEmpty(teacherDataBean.getTeaPosition())) {
			if (flag) {
				System.out.println("==============================>inside position===============================");
				fieldName = CommonValidate.findComponentInRoot("TPost").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.post")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(teacherDataBean.getTeaPosition())) {
			if (!CommonValidate.validateName(teacherDataBean.getTeaPosition())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPost").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vpost")));
				}
				valid = false;
			} else if (teacherDataBean.getTeaPosition().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPost").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vpost")));
				}
				valid = false;
			}
		}

		if (subjectTarget1.size() == 0) {
			if (flag) {
				System.out.println("==============================>inside subject===============================");
				fieldName = CommonValidate.findComponentInRoot("TSubject").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.subject")));
			}
			valid = false;

		}
		if (classTarget1.size() == 0) {
			if (flag) {
				System.out.println("==============================>inside class===============================");
				fieldName = CommonValidate.findComponentInRoot("TClass").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.class")));
			}
			valid = false;

		}

		if (StringUtils.isEmpty(teacherDataBean.getTeaWorkingHour())) {
			{
				if (flag) {
					System.out.println("==============================>inside working hour===============================");
					fieldName = CommonValidate.findComponentInRoot("TWorking").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.workingHour")));
				}
				valid = false;
			}

		}
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		int schoolid=Integer.parseInt(schoolID);
		if(schoolid==1 || schoolid==2||schoolid==3||schoolid==4||schoolid==5||schoolid==6){
			logger.debug("inside if------");
		}else{
			if (teacherDataBean.getMenus().length==0) {
				if (flag) {
							fieldName = CommonValidate.findComponentInRoot("menus").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage("Please Choose the Menus"));
						}
						valid = false;
				}
		}
		return valid;

	}*/

	public String submitConfirm() {
		setBoxflag(false);

		try {
			System.out.println("submitConfirm --- ");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");

			if (validate(true)) {
				if (personID != null) {
					RequestContext reqcontextt = RequestContext.getCurrentInstance();
					reqcontextt.execute("teachercheck();"); // calling
															// JavaScript

				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			e.printStackTrace();
			return "";
		}
		return "";

	}
	
	public List<String> listTeacher=new ArrayList<String>();
	public List<String> listTeacher1=new ArrayList<String>();
	private boolean chkBox;
	private boolean permenantAddFlag=true;
	private boolean permenantAddFlag1=false; 
	
	
	public boolean isChkBox() {
		return chkBox;
	}

	public void setChkBox(boolean chkBox) {
		this.chkBox = chkBox;
	}

	public boolean isPermenantAddFlag() {
		return permenantAddFlag;
	}

	public void setPermenantAddFlag(boolean permenantAddFlag) {
		this.permenantAddFlag = permenantAddFlag;
	}

	public boolean isPermenantAddFlag1() {
		return permenantAddFlag1;
	}

	public void setPermenantAddFlag1(boolean permenantAddFlag1) {
		this.permenantAddFlag1 = permenantAddFlag1;
	}

	public List<String> getListTeacher1() {
		return listTeacher1;
	}

	public void setListTeacher1(List<String> listTeacher1) {
		this.listTeacher1 = listTeacher1;
	}

	public List<String>checkboxList=new ArrayList<String>();
	public List<String> getCheckboxList() {
		return checkboxList;
	}

	public void setCheckboxList(List<String> checkboxList) {
		this.checkboxList = checkboxList;
	}

	private String code;
	private String code1;
	
public String getCode1() {
		return code1;
	}

	public void setCode1(String code1) {
		this.code1 = code1;
	}

public String getCode() {
		return code;
}

public void setCode(String code) {
		this.code = code;
	}

public List<String> getListTeacher() {
		return listTeacher;
	}

	public void setListTeacher(List<String> listTeacher) {
		this.listTeacher = listTeacher;
	}

public void state(ValueChangeEvent v){
	SmsController controller=null;
	String valuechange="";
	setCode("");
	try{
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		System.out.println("=========================================1");
		controller = (SmsController) ctx.getBean("controller");	
		valuechange=v.getNewValue().toString();				
		listTeacher=controller.getcountryList(valuechange); 
		System.out.println("================>"+listTeacher.size());  
		if(valuechange.equalsIgnoreCase("INDIA")) teacherDataBean.setCode("+91"); 
	     else if(valuechange.equalsIgnoreCase("MALAYSIA")) teacherDataBean.setCode("+60"); 
	     else if(valuechange.equalsIgnoreCase("UAE")) teacherDataBean.setCode("+971"); 
	     else if(valuechange.equalsIgnoreCase("INDONESIA")) teacherDataBean.setCode("+62");
	     else
	     {
	    	 teacherDataBean.setCode("00");
	     }
	}catch(Exception e){
		e.printStackTrace();
	}
	}

public void state1(ValueChangeEvent v){
	SmsController controller=null;
	String valuechange="";
	setCode("");
	try{
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		System.out.println("=========================================1");
		controller = (SmsController) ctx.getBean("controller");	
		valuechange=v.getNewValue().toString();				
		listTeacher1=controller.getcountryList(valuechange); 
		System.out.println("================>"+listTeacher.size());  
		if(valuechange.equalsIgnoreCase("INDIA")) teacherDataBean.setCode1("+91"); 
	     else if(valuechange.equalsIgnoreCase("MALAYSIA")) teacherDataBean.setCode1("+60"); 
	     else if(valuechange.equalsIgnoreCase("UAE")) teacherDataBean.setCode1("+971"); 
	     else if(valuechange.equalsIgnoreCase("INDONESIA")) teacherDataBean.setCode1("+62");
	     else 
	     {
	    	 teacherDataBean.setCode1("00");
	     }
	}catch(Exception e){
		e.printStackTrace();
	}
	}

List<String> subjectSource1 = new ArrayList<String>();
List<String> subjectTarget1 = new ArrayList<String>();
List<String> classSource1 = new ArrayList<String>();
List<String> classTarget1 = new ArrayList<String>();



public List<String> getClassSource1() {
	return classSource1;
}

public void setClassSource1(List<String> classSource1) {
	this.classSource1 = classSource1;
}

public List<String> getClassTarget1() {
	return classTarget1;
}

public void setClassTarget1(List<String> classTarget1) {
	this.classTarget1 = classTarget1;
}

public List<String> getSubjectSource1() {
	return subjectSource1;
}

public void setSubjectSource1(List<String> subjectSource1) {
	this.subjectSource1 = subjectSource1;
}

public List<String> getSubjectTarget1() {
	return subjectTarget1;
}

public void setSubjectTarget1(List<String> subjectTarget1) {
	this.subjectTarget1 = subjectTarget1;
}

public void checkbox(ValueChangeEvent v){	
	 String chkBox=null;
	try{
		System.out.println("========================>insidecheckbox================================>");
		chkBox=v.getNewValue().toString();
		if(chkBox.equals("true")){
			setPermenantAddFlag(false);
			setPermenantAddFlag1(true); 
			teacherDataBean.setTeaPermanentAddress(teacherDataBean.getTeaPresentAddress());  
			teacherDataBean.setTeaCountry1(teacherDataBean.getTeaCountry()); 
			teacherDataBean.setTeaState1(teacherDataBean.getTeaState()); 
			teacherDataBean.setTeaCity1(teacherDataBean.getTeaCity()); 
			teacherDataBean.setTeaPostal1(teacherDataBean.getTeaPostal()); 
			teacherDataBean.setTeaPhoneNo1(teacherDataBean.getTeaPhoneNo()); 
			teacherDataBean.setCode1(teacherDataBean.getCode());  
		}else{
			setPermenantAddFlag(true);
			setPermenantAddFlag1(false);  
			teacherDataBean.setTeaPermanentAddress("");  
			teacherDataBean.setTeaCountry1(""); 
			teacherDataBean.setTeaState1(""); 
			teacherDataBean.setTeaCity1(""); 
			teacherDataBean.setTeaPostal1(""); 
			teacherDataBean.setTeaPhoneNo1("");  
			teacherDataBean.setCode1(""); 
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}

	public String submitConfirm1() throws SQLException {
		System.out.println("---- inside conform ----");
		String status = "";
		SmsController controller = null;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			logger.debug("personID" + personID);
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			
			if (personID != null) {
				if(teacherDataBean.getTeaFile() !=null){
				logger.debug(teacherDataBean.getTeaFile().getContentType());
				String s = teacherDataBean.getTeaFile().getContentType();
				logger.debug("file Type " + s);
				String type = s.substring(s.lastIndexOf("/") + 1);
				logger.debug(type);
				
				copyFile(teacherDataBean.getTeaID(), teacherDataBean.getTeaFile().getInputstream(), type);

				logger.debug(teacherDataBean.getTeaID() + "." + type);
				String path = ft.format(now) + "/" + teacherDataBean.getTeaID() + "." + type;
				teacherDataBean.setTeafilePath(path);
				logger.debug(teacherDataBean.getTeafilePath());
				}else{
					teacherDataBean.setTeafilePath(null);
				}
				/*if(teacherDataBean.getTeaPhoneNo().startsWith(text.getString("sms.phno"))){
					teacherDataBean.setTeaPhoneNo(teacherDataBean.getTeaPhoneNo());
				}else if(teacherDataBean.getTeaPhoneNo().startsWith("0")){
					String phoneno=teacherDataBean.getTeaPhoneNo().substring(1);
					teacherDataBean.setTeaPhoneNo(text.getString("sms.phno")+phoneno);
				}else{
						teacherDataBean.setTeaPhoneNo(text.getString("sms.phno")+teacherDataBean.getTeaPhoneNo());
				}*/
				
				teacherDataBean.setTeaClassTargetList(classTarget1);
				
				teacherDataBean.setTeaSubjectTargetList(subjectTarget1);
				status = controller.insertTeacher(personID, teacherDataBean);
				System.out.println("status --------"+status);
				if (status.equalsIgnoreCase("Success")) {
					setBoxflag(true);
					String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
					//String pdfStatus = generatePdf(teacherDataBean);
					String emailStatus=MailSendJDBC.teacherInsert(teacherDataBean,schoolName,schoolID);
					System.out.println("emailStatus======"+emailStatus);
					if (emailStatus.equalsIgnoreCase("Success")) {
						/*String emailStatus = sendEmail(teacherDataBean, ImageListPath);
						if (emailStatus.equalsIgnoreCase("Success")) {*/
							FacesMessage message = new FacesMessage("Succesful",
									teacherDataBean.getTeaID() + " is Updated .");
							FacesContext.getCurrentInstance().addMessage(null, message);
							teacherDataBean.setTeaFirstName("");
							teacherDataBean.setTeaLastName("");
							teacherDataBean.setTeaFatherName("");
							teacherDataBean.setTeaMotherName("");
							teacherDataBean.setTeaDob(null);
							teacherDataBean.setTeaGender("");
							teacherDataBean.setTeaEduQualification("");
							teacherDataBean.setTeaEmail("");
							teacherDataBean.setTeaPercentage("");
							teacherDataBean.setTeaPhoneNo("");
							teacherDataBean.setTeaPosition("");
							teacherDataBean.setTeaPermanentAddress("");
							teacherDataBean.setTeaPresentAddress("");
							teacherDataBean.setTeaSubject("");
							teacherDataBean.setTeaTeacherPhoto("");
							teacherDataBean.setTeaWorkingHour("");
							teacherDataBean.setTeaYearOfPassing("");
							teacherDataBean.setTeaPermanentState("");
							teacherDataBean.setTeaPresentState("");
							teacherDataBean.setTeaPermanentZip("");
							teacherDataBean.setTeaPresentZip("");
							teacherDataBean.setTeaFile(null);
							teacherDataBean.setTeaSecurePasword("");
							teacherDataBean.setPath("");
							teacherDataBean.setPathDate(null);
							teacherDataBean.setTeaSubjectTargetList(null);
							teacherDataBean.setTeaClassTargetList(null);
							teacherDataBean.setTeaCountry("");
							teacherDataBean.setTeaState("");
							teacherDataBean.setTeaCity("");
							teacherDataBean.setTeaPostal("");
							teacherDataBean.setCode("");
							teacherDataBean.setTeaPhoneNo("");
							setChkBox(false);
							teacherDataBean.setTeaCountry1("");
							teacherDataBean.setTeaState1("");
							teacherDataBean.setTeaCity1("");
							teacherDataBean.setTeaPostal1("");
							teacherDataBean.setCode1("");
							teacherDataBean.setTeaPhoneNo1("");
							System.out.println("teacherblockUI-----");
							RequestContext reqcontext = RequestContext.getCurrentInstance();
							reqcontext.execute("PF('teacherblockUI').hide();");
							reqcontext.execute("PF('existDialog').hide();");
							reqcontext.execute("PF('teacherRegDialog').show();");
							setBoxflag(true);

						} 
					else {
						RequestContext reqcontextt = RequestContext.getCurrentInstance();
						reqcontextt.execute("PF('teacherblockUI').hide();");
						reqcontextt.execute("PF('teacherRegDialog').show();");
						//setBoxflag(false);
					}
				} else if (status.equalsIgnoreCase("Exsist")) {
					FacesMessage message = new FacesMessage(text.getString("sms.teacher.teacherexsistId"));
					FacesContext.getCurrentInstance().addMessage(null, message);
					RequestContext reqcontextt = RequestContext.getCurrentInstance();
					reqcontextt.execute("PF('teacherblockUI').hide();");
					reqcontextt.execute("PF('teacherRegDialog').hide();");
					reqcontextt.execute("PF('existDialog').show();");
					setBoxflag(false);
				} else {
					FacesMessage message = new FacesMessage(text.getString("sms.network.error"));
					FacesContext.getCurrentInstance().addMessage(null, message);
					RequestContext reqcontextt = RequestContext.getCurrentInstance();
					reqcontextt.execute("PF('teacherblockUI').hide();");
					reqcontextt.execute("PF('teacherRegDialog').hide();");
					reqcontextt.execute("PF('existDialog').hide();");
					setBoxflag(false);
				}
			}
			System.out.println("Box flag "+boxflag);

		} catch (IOException e) {
			logger.warn("Exception -->"+e.getMessage());
			e.printStackTrace();
		}

		return "";

	}

	private String generatePdf(TeacherDataBean teacherDataBean2) {
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

				ImageListPath = new ArrayList<TeacherDataBean>();
				ImageListPath = controller.getImagePath(personID, teacherDataBean2.getTeaID());
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
							new File(files + paths.getString("path_context").toString()+ teacherDataBean2.getTeaUsername() + ".pdf"));
					teacherDataBean.setTeafilePath(files + paths.getString("path_context").toString() + teacherDataBean2.getTeaUsername() + ".pdf");
					Document document = new Document(PageSize.A4, 50, 50, 10, 50);
					PdfWriter.getInstance(document, file);
					document.open();
					String logo ="";
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						logo = paths.getString(schoolID+"_LOGO");
						document.add(Image.getInstance(getClass().getClassLoader().getResource(logo)));
					}else{
						logo = teacherDataBean.getSchoolLogo();
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
					PdfPCell nesCell9 = new PdfPCell(new Paragraph("Date of Birth:"));
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

					PdfPTable nestedTable4 = new PdfPTable(4);
					nestedTable4.setWidthPercentage(100f);
					PdfPCell nesCell17 = new PdfPCell(new Paragraph("Teacher ID:"));
					PdfPCell nesCell18 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaID()));
					PdfPCell nesCell19 = new PdfPCell(new Paragraph("Post / Position"));
					PdfPCell nesCell20 = new PdfPCell(new Paragraph(teacherDataBean2.getTeaPosition()));
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
					PdfPCell nesCell37 = new PdfPCell(new Paragraph("Class:"));
					PdfPCell nesCell38 = new PdfPCell(new Paragraph(teacherDataBean2.getTeaClassTargetList().toString()));
					PdfPCell nesCell39 = new PdfPCell(new Paragraph("Faculties / Subject:"));
					PdfPCell nesCell40 = new PdfPCell(new Paragraph(teacherDataBean2.getTeaSubjectTargetList().toString()));
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
					cell1.addElement(nestedTable5);

					

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

					PdfPTable nestedTable8 = new PdfPTable(4);
					nestedTable8.setWidthPercentage(100f);
					PdfPCell nesCell50 = new PdfPCell(new Paragraph("Working Hour:"));
					PdfPCell nesCell51 = new PdfPCell(new Paragraph(teacherDataBean2.getTeaWorkingHour()));
					PdfPCell nesCell52 = new PdfPCell(new Paragraph("Percentage:"));
					PdfPCell nesCell53 = new PdfPCell(new Paragraph(teacherDataBean2.getTeaPercentage()));
					nesCell50.setBorder(PdfPCell.NO_BORDER);
					nesCell51.setBorder(PdfPCell.NO_BORDER);
					nesCell52.setBorder(PdfPCell.NO_BORDER);
					nesCell53.setBorder(PdfPCell.NO_BORDER);
					nestedTable8.addCell(nesCell50);
					nestedTable8.addCell(nesCell51);
					nestedTable8.addCell(nesCell52);
					nestedTable8.addCell(nesCell53);
					nestedTable8.setSpacingBefore(3f);
					nestedTable8.setSpacingAfter(3f);
					cell1.addElement(nestedTable8);
					
					PdfPTable nestedTable9 = new PdfPTable(4);
					nestedTable9.setWidthPercentage(100f);
					
					PdfPCell nesCell35 = new PdfPCell(new Paragraph("Photo"));
					PdfPCell nesCell36 = null;
					try{
						if(ImageListPath.get(0).getPath() !=null)
						{
						String tempdate = ft.format(ImageListPath.get(0).getPathDate());
						String imageLocation = paths.getString("sms.teacher.teacherphoto") + tempdate + "/" + ImageListPath.get(0).getPath();
						Image image = Image.getInstance(imageLocation);
						image.scaleAbsolute(100, 100);
						nesCell36 = new PdfPCell(image, false);
						}else{
							 nesCell36 = new PdfPCell(new Paragraph(""));
						}
					}catch(FileNotFoundException f){
						nesCell36 = new PdfPCell(new Paragraph(""));
					}
					
					PdfPCell nesCell33 = new PdfPCell(new Paragraph(""));
					PdfPCell nesCell34 = new PdfPCell(new Paragraph(""));
					nesCell35.setBorder(PdfPCell.NO_BORDER);
					nesCell36.setBorder(PdfPCell.NO_BORDER);
					nesCell33.setBorder(PdfPCell.NO_BORDER);
					nesCell34.setBorder(PdfPCell.NO_BORDER);
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

					logger.debug("Done");
					status = "Success";
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return status;
	}

	public String dummyAction(FileUploadEvent event) throws IOException {

		this.file = event.getFile();
		teacherDataBean.setTeaFile(event.getFile());
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);
		/*
		 * File chartFile = new File(event.getFile().getContentType());
		 * listImage=null; logger.debug(listImage); final UploadedFile
		 * uploadedFile = event.getFile(); chart= new DefaultStreamedContent(new
		 * FileInputStream(event.getFile().getContentType()),"image/png");
		 * 
		 * logger.debug("Uploaded File Name Is :"+uploadedFile.getFileName());
		 * listImage = new DefaultStreamedContent(new
		 * ByteArrayInputStream(uploadedFile.getContents()), "image/png");
		 */

		return "";
	}

	private void copyFile(String fileName, InputStream inputstream, String n) {
		try {

			// Create Directory
			File files = new File(paths.getString("sms.teacher.teacherphoto") + ft.format(now));
			if (!files.exists()) {
				files.mkdirs();
			} else {
				logger.debug("Alreday Found");
			}

			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(files + paths.getString("path_context").toString()+ fileName + "." + n));

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

	/**
	 * @return the imageListPath
	 */
	public List<TeacherDataBean> getImageListPath() {
		return ImageListPath;
	}

	/**
	 * @param imageListPath
	 *            the imageListPath to set
	 */
	public void setImageListPath(List<TeacherDataBean> imageListPath) {
		ImageListPath = imageListPath;
	}

	public String returnToHome() {

		logger.debug("Calling");
		SmsController controller = null;
		int teacherCount = 0;
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				teacherCount = controller.getTotalTeacher(personID);
				if (teacherCount > 0) {
					String count = String.valueOf(teacherCount);
					loginMB.loginaccess.setTotalTeacher(count);
				}
				teacherPage();
				return "";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
		return "";

	}

	public LoginMB getLoginMB() {
		return loginMB;
	}

	public void setLoginMB(LoginMB loginMB) {
		this.loginMB = loginMB;
	}

	/*private String sendEmail(TeacherDataBean teacherDataBean2, List<TeacherDataBean> imageListPath2) {
		String status = "Fail";
		String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
		try {
			String to = teacherDataBean2.getTeaEmail();
			logger.debug("to-------" + to);
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
					+ "<h3>Dear " + teacherDataBean2.getTeaUsername() + ",</h3>"
					+ "<p>Welcome on-board into"
					+ schoolName
					+ " family. Please find the your Username and Password enclosed with is mail."
					+ "All the very best in your new assignment</p>"
					+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
					+ "<tr>" + "<td>User Name" + "</td>" + "<td>" + teacherDataBean2.getTeaUsername() + "</td>"
					+ "</tr>" + "<tr>" + "<td>Password" + "</td>" + "<td>" + teacherDataBean2.getTeaSecurePasword()
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
				message.setSubject("SMS Confirmation-" + teacherDataBean2.getTeaUsername());
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");

				MimeBodyPart messageBodyPart2 = new MimeBodyPart();
				String filename = paths.getString("sms.teacher.teacherpdf") + ft.format(now) + "/" + teacherDataBean2.getTeaUsername()
						+ ".pdf";// change accordingly
				DataSource source = new FileDataSource(filename);
				messageBodyPart2.setDataHandler(new DataHandler(source));
				messageBodyPart2.setFileName(teacherDataBean2.getTeaUsername() + ".pdf");

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
	}*/
}
