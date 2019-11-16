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


//import java.util.StringJoiner;

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
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.itextpdf.text.Document;
//import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.stumosys.controler.SmsController;
import com.stumosys.domain.LoginAccess;
import com.stumosys.domain.StudentDataBean;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name = "studentMB")
@RequestScoped
public class StudentMB {
	@ManagedProperty(value = "#{loginMB}")
	LoginMB loginMB;
	LoginAccess loginaccess = new LoginAccess();
	StudentDataBean studentDataBean = new StudentDataBean();
	UploadedFile file;
	Date now = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	private StreamedContent chart;
	private StreamedContent listImage = null;
	private List<StudentDataBean> ImageListPath = null;
	private boolean boxflag = false;
	private List<String> stateLists = null;
	private DualListModel<String> sclass;
	private String status;
	private boolean valid = true;
	private List<String> sclass1 = null;
	List<String> classlist = null;
	private String sclass2 = null;
	private boolean sflag = false;
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	FacesContext context1 = FacesContext.getCurrentInstance();
	ResourceBundle paths = context1.getApplication().evaluateExpressionGet(context1, "#{paths}", ResourceBundle.class);
	private static Logger logger = Logger.getLogger(StudentMB.class);
	private List<String> menulist=null;
	private boolean menuflag=false;
	private String code;
	private String code1;
	public List<String> statelist=null;
	public List<String> statelist1=null;
	private boolean chkBox;
	private boolean permenantAddFlag=true;
	private boolean permenantAddFlag1=false; 
	String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
	private List<String> numberList=null;
	private List<String> ifscnoList=null;
	private List<String> branchcodeList=null;
	private List<String> aadharnoList=null;
	private List<String> emisnoList=null;

	public List<String> getIfscnoList() {
		return ifscnoList;
	}

	public void setIfscnoList(List<String> ifscnoList) {
		this.ifscnoList = ifscnoList;
	}

	public List<String> getBranchcodeList() {
		return branchcodeList;
	}

	public void setBranchcodeList(List<String> branchcodeList) {
		this.branchcodeList = branchcodeList;
	}

	public List<String> getAadharnoList() {
		return aadharnoList;
	}

	public void setAadharnoList(List<String> aadharnoList) {
		this.aadharnoList = aadharnoList;
	}

	public List<String> getEmisnoList() {
		return emisnoList;
	}

	public void setEmisnoList(List<String> emisnoList) {
		this.emisnoList = emisnoList;
	}

	public List<String> getNumberList() {
		return numberList;
	}

	public void setNumberList(List<String> numberList) {
		this.numberList = numberList;
	}

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

	public List<String> getStatelist() {
		return statelist;
	}

	public void setStatelist(List<String> statelist) {
		this.statelist = statelist;
	}

	public List<String> getStatelist1() {
		return statelist1;
	}

	public void setStatelist1(List<String> statelist1) {
		this.statelist1 = statelist1;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode1() {
		return code1;
	}

	public void setCode1(String code1) {
		this.code1 = code1;
	}

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

	public LoginMB getLoginMB() {
		return loginMB;
	}

	public void setLoginMB(LoginMB loginMB) {
		this.loginMB = loginMB;
	}

	public LoginAccess getLoginaccess() {
		return loginaccess;
	}

	public void setLoginaccess(LoginAccess loginaccess) {
		this.loginaccess = loginaccess;
	}

	public boolean isSflag() {
		return sflag;
	}

	public void setSflag(boolean sflag) {
		this.sflag = sflag;
	}

	public boolean isBoxflag() {
		return boxflag;
	}

	public void setBoxflag(boolean boxflag) {
		this.boxflag = boxflag;
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

	public StreamedContent getChart() {
		return chart;
	}

	public void setChart(StreamedContent chart) {
		this.chart = chart;
	}

	public StreamedContent getListImage() {
		return listImage;
	}

	public void setListImage(StreamedContent listImage) {
		this.listImage = listImage;
	}

	public List<StudentDataBean> getImageListPath() {
		return ImageListPath;
	}

	public void setImageListPath(List<StudentDataBean> imageListPath) {
		ImageListPath = imageListPath;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public List<String> getSclass1() {
		return sclass1;
	}

	public void setSclass1(List<String> sclass1) {
		this.sclass1 = sclass1;
	}

	public List<String> getClasslist() {
		return classlist;
	}

	public void setClasslist(List<String> classlist) {
		this.classlist = classlist;
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

	public DualListModel<String> getSclass() {
		return sclass;
	}

	public void setSclass(DualListModel<String> sclass) {
		this.sclass = sclass;
	}

	public List<String> getStateLists() {
		return stateLists;
	}

	public void setStateLists(List<String> stateLists) {
		this.stateLists = stateLists;
	}

	public String getSclass2() {
		return sclass2;
	}

	public void setSclass2(String sclass2) {
		this.sclass2 = sclass2;
	}

	public StudentDataBean getStudentDataBean() {
		return studentDataBean;
	}

	public void setStudentDataBean(StudentDataBean studentDataBean) {
		this.studentDataBean = studentDataBean;
	}
	public String fileUploadPage() {
		return "fileupload";
	}

	public String addStudentPage() {
		logger.debug("----------Inside addStudentPage Method Calling-----");
		SmsController controller = null;
		studentDataBean.setStuFirstName("");
		studentDataBean.setStuLastName("");
		studentDataBean.setStuFatherName("");
		studentDataBean.setStuMotherName("");
		studentDataBean.setStuDob(null);
		studentDataBean.setStuGender("");
		studentDataBean.setStuPresentAddress("");
		studentDataBean.setStuPresentAddress("");
		studentDataBean.setStuPresentState("");
		studentDataBean.setStuPresentZip("");
		studentDataBean.setStuPermanentState("");
		studentDataBean.setStuPermanentAddress("");
		studentDataBean.setStuPermanentZip("");
		studentDataBean.setStuPhoneNo("");
		studentDataBean.setStuEmail("");
		studentDataBean.setStuFatherOccu("");
		studentDataBean.setStuFatherIncome(null);
		studentDataBean.setStuMotherOccu("");
		studentDataBean.setStuCls(null);
		studentDataBean.setStuSession("");
		studentDataBean.setStuStudentId(null);
		studentDataBean.setStuRollNo(null);
		studentDataBean.setStuStudentPhoto("");
		studentDataBean.setStuPermanentCountry("");
		studentDataBean.setStuPresentCountry("");
		studentDataBean.setStuPermanentState("");
		studentDataBean.setStuPresentState("");
		studentDataBean.setStuPresentCity("");
		studentDataBean.setStuPermanentCity("");
		studentDataBean.setStuPresentpostal("");
		studentDataBean.setStuPermanentpostal("");
		studentDataBean.setStuPhoneNo1("");
		studentDataBean.setCode("");
		studentDataBean.setCode1("");
		setMenuflag(false);
		studentDataBean.setMenus(null);
		String returnPage="studentRegistration";
		studentDataBean.setReligion("");studentDataBean.setCaste("");studentDataBean.setClassification("");
		studentDataBean.setCommunityCertificateNo("");studentDataBean.setIssuedAuthority("");studentDataBean.setIssuedDate(null);
		studentDataBean.setBankAccNo("");studentDataBean.setBankName("");studentDataBean.setBranchName("");studentDataBean.setIfscCode("");
		studentDataBean.setMicrNo("");studentDataBean.setAadharCardNo("");studentDataBean.setRationCardNo("");studentDataBean.setEmploymentCardNo("");
		studentDataBean.setEmisNo("");studentDataBean.setBusPassNo("");
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				menulist=new ArrayList<String>();
				classlist=new ArrayList<String>();
				sclass1=new ArrayList<String>();
				menulist=controller.getmenus();
				classlist = controller.getClassList(personID, studentDataBean);
				
				if(studentDataBean.getStuPickList()!=null){
					
					for (int i = 0; i < studentDataBean.getStuPickList().size(); i++) {
						sclass1.add(studentDataBean.getStuPickList().get(i).getStuCls());
					}
				}
				else 
				{
					logger.info("No Class found !!!");
					//returnPage="Failurepage";
					return returnPage;
				}
				
				System.out.println("sclass1 --- "+sclass1.size());
				Collections.sort(menulist);
				status = controller.getRoll(personID);
				logger.debug("-----status----" + status);
				if (status.equalsIgnoreCase("Admin")) {
					setSflag(true);
				}
				if (status.equalsIgnoreCase("Student") || status.equalsIgnoreCase("Parent")
						|| status.equalsIgnoreCase("Teacher")) {
					setSflag(false);
				}
				if(schoolID.equals(paths.getString("SMRV.SCHOOLID"))){
					numberList=new ArrayList<String>();
					ifscnoList=new ArrayList<String>();
					branchcodeList=new ArrayList<String>();
					aadharnoList=new ArrayList<String>();
					emisnoList=new ArrayList<String>();
					for (int i = 0; i < 15; i++) {
						numberList.add(new String());
					}
					for (int i = 0; i < 11; i++) {
						ifscnoList.add(new String());
					}
					for (int i = 0; i < 9; i++) {
						branchcodeList.add(new String());
					}
					for (int i = 0; i < 14; i++) {
						aadharnoList.add(new String());
					}
					for (int i = 0; i < 16; i++) {
						emisnoList.add(new String());
					}
				}
				return returnPage;
			}
		} catch (Exception e) {
			logger.debug("----------Inside addStudentPage Method Exception Calling-----");
			//e.printStackTrace();
			//return "studentRegistration";
			

		}
		return returnPage;
	}

	public boolean validate(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (StringUtils.isEmpty(studentDataBean.getStuFirstName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sFName").getClientId(fc);
			//	fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.ftname")));
				fc.addMessage(fieldName, new FacesMessage(text.getString("stumosys.student.ftname")));
				
			}
			valid = false;
		} else if (!StringUtils.isEmpty(studentDataBean.getStuFirstName())) {
			if (studentDataBean.getStuFirstName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vftname")));
				}
				valid = false;
			} else if (!CommonValidate.validateName(studentDataBean.getStuFirstName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vftname")));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(studentDataBean.getStuLastName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sLName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.lname")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(studentDataBean.getStuLastName())) {
			if (studentDataBean.getStuLastName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sLName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vlname")));
				}
				valid = false;
			} else if (!CommonValidate.validateName(studentDataBean.getStuLastName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sLName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vlname")));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(studentDataBean.getStuFatherName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sFaName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.fname")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(studentDataBean.getStuFatherName())) {
			if (studentDataBean.getStuFatherName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sFaName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vfname")));
				}
				valid = false;
			} else if (!CommonValidate.validateName(studentDataBean.getStuFatherName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sFaName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vfname")));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(studentDataBean.getStuMotherName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sMoName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.mname")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(studentDataBean.getStuMotherName())) {
			if (studentDataBean.getStuMotherName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sMoName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vmname")));
				}
				valid = false;
			} else if (!CommonValidate.validateName(studentDataBean.getStuMotherName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sMoName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vmname")));
				}
				valid = false;
			}
		}
		if (studentDataBean.getStuDob() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sDob").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.dob")));
			}
			valid = false;
		}

		if (StringUtils.isEmpty(studentDataBean.getStuGender())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sGender").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.gender")));
			}
			valid = false;
		}

		return valid;
	}

	private boolean flags=false;
	public boolean isFlags() {
		return flags;
	}

	public void setFlags(boolean flags) {
		this.flags = flags;
	}

	public String stuRegPage1() {
		logger.debug("------stuRegPage1 method calling--------");
		SmsController controller = null;
		String personID = "";
		flags=false;
		if (validate(true)) {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			logger.debug(personID);
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			if (personID != null) {
				stateLists = controller.getStateLists(personID);
				logger.debug("school id "+schoolid);
				if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
					flags=false;
				}else{
					flags=true;
				}
			}
			return "studentRegistration1";
		} else {
			return "";
		}

	}

	private boolean validate1(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (StringUtils.isEmpty(studentDataBean.getStuPresentAddress())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("SPAddress").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.address")));
			}
			valid = false;

		} else if (studentDataBean.getStuPresentAddress().length() < 3) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("SPAddress").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vaddress")));
			}
			valid = false;
		}
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		int schoolid=Integer.parseInt(schoolID);
		if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
		}else{
			if (StringUtils.isEmpty(studentDataBean.getStuPhoneNo())) {
			if (flag) {

				fieldName = CommonValidate.findComponentInRoot("sPhoneNo").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.phno")));
			}
			valid = false;
		} else if(studentDataBean.getStuPhoneNo().length()<11){
			if (flag) {

				fieldName = CommonValidate.findComponentInRoot("sPhoneNo").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vphno")));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(studentDataBean.getStuEmail())) {
			{
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sEmail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.email")));
				}
				valid = false;
			}
		}
		else if (!StringUtils.isEmpty(studentDataBean.getStuEmail())) 
		{
			if (!CommonValidate.validateEmail(studentDataBean.getStuEmail())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sEmail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.email")));
				}
				valid = false;
			}
		}
		}
		/*if (StringUtils.isEmpty(studentDataBean.getStuPresentState())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("myTextBox1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.state")));
			}
			valid = false;
		}*/
		/*if (StringUtils.isEmpty(studentDataBean.getStuPresentZip())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("myTextBox").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.zip")));
			}
			valid = false;
		}*/
		if (StringUtils.isEmpty(studentDataBean.getStuPermanentAddress())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("SpermantAddress").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.peraddress")));
			}
			valid = false;
		} else if (studentDataBean.getStuPermanentAddress().length() < 3) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("SpermantAddress").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vperaddress")));
			}
			valid = false;
		}
		/*if (StringUtils.isEmpty(studentDataBean.getStuPermanentState())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("myTextBox4").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.perstate")));
			}
			valid = false;
		}*/
		/*if (StringUtils.isEmpty(studentDataBean.getStuPermanentZip())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("myTextBox3").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.perzip")));
			}
			valid = false;
		}*/
		logger.debug("ph no - " + studentDataBean.getStuPhoneNo());
		/*if (StringUtils.isEmpty(studentDataBean.getStuPhoneNo())) {
			if (flag) {

				fieldName = CommonValidate.findComponentInRoot("sPhoneNo").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.phno")));
			}
			valid = false;
		}*/
		/*if (studentDataBean.getStuPhoneNo().equalsIgnoreCase("(+00)000-000-0000")) {
			if (flag) {

				fieldName = CommonValidate.findComponentInRoot("sPhoneNo").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vphno")));
			}
			valid = false;
		}*/

		/*if (StringUtils.isEmpty(studentDataBean.getStuEmail())) {
			{
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sEmail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.email")));
				}
				valid = false;
			}
		}
		else if (!StringUtils.isEmpty(studentDataBean.getStuEmail())) 
		{
			if (!CommonValidate.validateEmail(studentDataBean.getStuEmail())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sEmail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.email")));
				}
				valid = false;
			}
		}*/

		return valid;
	}

	public String stuRegPage2() {

		try {
			logger.debug("------stuRegPage2 method calling--------");
			sclass1 = new ArrayList<String>();
			SmsController controller = null;
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				classlist = controller.getClassList(personID, studentDataBean);
				for (int i = 0; i < studentDataBean.getStuPickList().size(); i++) {
					sclass1.add(studentDataBean.getStuPickList().get(i).getStuCls());
				}
				logger.debug("-----sclass---" + sclass1);

				if (validate1(true)) {
					return "studentRegistration2";
				}
			}
		} catch (Exception e) {
			logger.debug("-----stuRegPage2 method exception calling-------");
			logger.warn("Exception -->"+e.getMessage());
		}

		return "";
	}

	public boolean validate2(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (StringUtils.isEmpty(studentDataBean.getStuFatherOccu())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sFatherOccu").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.foccupation")));
			}
			valid = false;
		}
		if (studentDataBean.getStuFatherIncome() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sFatherInRange").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.income")));
			}
			valid = false;
		} else if (studentDataBean.getStuFatherIncome() != null) {
			if (!CommonValidate.validatePrice(studentDataBean.getStuFatherIncome())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sFatherInRange").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vincome")));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(studentDataBean.getStuMotherOccu())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sMotherOccu").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.moccupation")));
			}
			valid = false;
		}

		if (StringUtils.isEmpty(studentDataBean.getStuCls())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sClass").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.class")));
			}
			valid = false;
		}

		return valid;
	}

	public String stuRegPage3() {
		try {
			setBoxflag(false);
			setMenuflag(false);
			logger.debug("------stuRegPage3 method calling--------");
			String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			if (validate2(true)) {
				int schoolid=Integer.parseInt(schoolID);
				if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
					setMenuflag(false);
				}else{
					setMenuflag(true);
				}
				return "studentRegistration3";
			}

		} catch (Exception e) {
			logger.debug("-----stuRegPage3 method exception calling-------");
			logger.warn("Exception -->"+e.getMessage());
		}

		return "";
	}

	public boolean validate3(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		logger.debug("roll no - " + studentDataBean.getStuRollNo());
		if (StringUtils.isEmpty(studentDataBean.getStuRollNo())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sRollNo").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.rollno")));
			}
			valid = false;
		} 
		if (StringUtils.isEmpty(studentDataBean.getStuCls())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sClass").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.class")));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(studentDataBean.getStuFirstName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sFName").getClientId(fc);
				//fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.ftname")));
				fc.addMessage(fieldName, new FacesMessage(text.getString("stumosys.student.ftname")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(studentDataBean.getStuFirstName())) {
			if (studentDataBean.getStuFirstName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vftname")));
				}
				valid = false;
			} else if (!CommonValidate.validateName(studentDataBean.getStuFirstName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vftname")));
				}
				valid = false;
			}
		}
		if(StringUtils.isEmpty(studentDataBean.getStuPresentCountry())){
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("SPcountry").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.teacherCountry")));
			}
			valid = false;
		}
		if(!StringUtils.isEmpty(studentDataBean.getStuPresentCountry())){
			if(StringUtils.isEmpty(studentDataBean.getStuPhoneNo())) {
				/*if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPhone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumber")));
				}
				valid = false;*/
			}else if(!CommonValidate.validateNumberOnly(studentDataBean.getStuPhoneNo())){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPhone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumberinvalid")));
				}
				valid = false;
			}
			else if(!CommonValidate.countryValidateNumber(studentDataBean.getStuPhoneNo(),studentDataBean.getStuPresentCountry())){
				fieldName = CommonValidate.findComponentInRoot("TPhone").getClientId(fc);
				if (studentDataBean.getStuPresentCountry().equalsIgnoreCase("INDIA")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.india.phonevalidation")));
				}
				else if (studentDataBean.getStuPresentCountry().equalsIgnoreCase("UAE")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.uae.phonevalidation")));
				}
				else if (studentDataBean.getStuPresentCountry().equalsIgnoreCase("INDONESIA")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.indonesia.phonevalidation")));
				}
				else if (studentDataBean.getStuPresentCountry().equalsIgnoreCase("MALAYSIA")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.malaysia.phonevalidation")));
				}
				
				valid = false;
			}
		}
		
		/*if (studentDataBean.getStuFile() == null) {
			logger.debug("---------inside if-----");
			logger.debug("---------inside if1");
			fieldName = CommonValidate.findComponentInRoot("stuPhoto").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.photo")));
			valid = false;
		}
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		int schoolid=Integer.parseInt(schoolID);
		if(schoolid==1 || schoolid==2||schoolid==3||schoolid==4||schoolid==5||schoolid==6){
		logger.debug("inside if----");
		}else{
			if (studentDataBean.getMenus().length==0) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("menus").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose the Menus"));
				}
				valid = false;
			}*/ 
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		if(schoolID.equals(paths.getString("SMRV.SCHOOLID"))){
			if(StringUtils.isEmpty(studentDataBean.getReligion())){
				fieldName = CommonValidate.findComponentInRoot("religion").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.religion")));
				valid=false;
			}if(StringUtils.isEmpty(studentDataBean.getClassification())){
				fieldName = CommonValidate.findComponentInRoot("classification").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.classification")));
				valid=false;
			}
			/*for (int i = 0; i <emisnoList.size(); i++) {
				if(StringUtils.isEmpty(emisnoList.get(i))){
					fieldName = CommonValidate.findComponentInRoot("panelid").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.emisNo")));
					valid=false;
				}
			}*/
		}
		return valid;
	}

	public String studentPageLoad() {
		logger.debug("Inside Reset Method Calling...");
		studentDataBean.setStuFirstName("");
		studentDataBean.setStuLastName("");
		studentDataBean.setStuFatherName("");
		studentDataBean.setStuMotherName("");
		studentDataBean.setStuDob(null);
		studentDataBean.setStuGender("");
		studentDataBean.setStuPhoneNo("");
		studentDataBean.setStuEmail("");
		studentDataBean.setStuFatherOccu("");
		studentDataBean.setStuFatherIncome(null);
		studentDataBean.setStuMotherOccu("");
		studentDataBean.setStuCls(null);
		studentDataBean.setStuSession("");
		studentDataBean.setStuStudentId(null);
		studentDataBean.setStuRollNo(null);
		studentDataBean.setStuStudentPhoto("");
		studentDataBean.setStuPresentAddress("");
		studentDataBean.setStuPermanentAddress("");
		studentDataBean.setStuPermanentCountry("");
		studentDataBean.setStuPresentCountry("");
		studentDataBean.setStuPermanentState("");
		studentDataBean.setStuPresentState("");
		studentDataBean.setStuPresentCity("");
		studentDataBean.setStuPermanentCity("");
		studentDataBean.setStuPresentpostal("");
		studentDataBean.setStuPermanentpostal("");
		studentDataBean.setStuPhoneNo1("");
		return "studentRegistration";
	}

	public String submit() {
		String status = "";
		SmsController controller = null;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			logger.debug("personID" + personID);
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			
			if (personID != null) {
				
				if(studentDataBean.getStuFile()==null)
				{
					studentDataBean.setStufilePath("");
				}
				else
				{
					logger.debug(studentDataBean.getStuFile().getContentType());
					String s = studentDataBean.getStuFile().getContentType();
					logger.debug("file Type " + s);
					String type = s.substring(s.lastIndexOf("/") + 1);
					logger.debug(type);
					copyFile(studentDataBean.getStuRollNo(), studentDataBean.getStuFile().getInputstream(), type);
					logger.debug(studentDataBean.getStuRollNo() + "." + type);
					String path = ft.format(now) + "/" + studentDataBean.getStuRollNo() + "." + type;
					studentDataBean.setStufilePath(path);
					logger.debug(studentDataBean.getStufilePath());	
				}
			/*	if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6)
				{
				}else{
					if(studentDataBean.getStuPhoneNo().startsWith(text.getString("sms.phno"))){
						studentDataBean.setStuPhoneNo(studentDataBean.getStuPhoneNo());
					}else if(studentDataBean.getStuPhoneNo().startsWith("0")){
						String phoneno=studentDataBean.getStuPhoneNo().substring(1);
						studentDataBean.setStuPhoneNo(text.getString("sms.phno")+phoneno);
					}else{
						studentDataBean.setStuPhoneNo(text.getString("sms.phno")+studentDataBean.getStuPhoneNo());
					}
				}*/
				status = controller.insertStudent(personID, studentDataBean);
				logger.debug("check staus"+status);
				if (status.equalsIgnoreCase("Success")) 
				{
					logger.debug("check staus"+status);
					logger.debug("MB PWD" + studentDataBean.getStuSecurePasword());
					logger.debug("MB Name" + studentDataBean.getStuUsername());
					logger.debug("school id "+schoolid);
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6)
					{
					}else{
						//String pdfStatus = generatePdf(studentDataBean);
						//logger.debug("status "+pdfStatus);
						//if (pdfStatus.equalsIgnoreCase("Success")) 
						//{
							MailSendJDBC.studentinsert(studentDataBean);
						//}
					}
					/*String pdfStatus = generatePdf(studentDataBean);
					if (pdfStatus.equalsIgnoreCase("Success")) 
					{
						String emailStatus = sendEmail(studentDataBean, ImageListPath);
						if (emailStatus.equalsIgnoreCase("Success")) {*/
							FacesMessage message = new FacesMessage("Succesful",
									studentDataBean.getStuRollNo() + " is Inserted .");
							FacesContext.getCurrentInstance().addMessage(null, message);
							studentDataBean.setStuFirstName("");
							studentDataBean.setStuLastName("");
							studentDataBean.setStuFatherName("");
							studentDataBean.setStuMotherName("");
							studentDataBean.setStuDob(null);
							studentDataBean.setStuGender("");
						    studentDataBean.setStuEmail("");
							studentDataBean.setStuStudentId("");
							studentDataBean.setStuPhoneNo("");
							studentDataBean.setStuPermanentAddress("");
							studentDataBean.setStuPresentAddress("");
							studentDataBean.setStuStudentPhoto("");
							studentDataBean.setStuPermanentState("");
							studentDataBean.setStuPresentState("");
							studentDataBean.setStuPermanentZip("");
							studentDataBean.setStuPresentZip("");
							studentDataBean.setStuFile(null);
							studentDataBean.setStuSecurePasword("");
							studentDataBean.setPath("");
							studentDataBean.setPathDate(null);
							studentDataBean.setStuCls("");
							studentDataBean.setStuPermanentCountry("");
							studentDataBean.setStuPresentCountry("");
							studentDataBean.setStuPermanentState("");
							studentDataBean.setStuPresentState("");
							studentDataBean.setStuPresentCity("");
							studentDataBean.setStuPermanentCity("");
							studentDataBean.setStuPresentpostal("");
							studentDataBean.setStuPermanentpostal("");
							studentDataBean.setStuPhoneNo1("");
							RequestContext reqcontext = RequestContext.getCurrentInstance();
							reqcontext.execute("PF('studentblockUI').hide();");
							reqcontext.execute("PF('studentRegDialog').show();");
							setBoxflag(true);

						}
			else {
						FacesMessage message = new FacesMessage(text.getString("sms.student.network"));
						FacesContext.getCurrentInstance().addMessage(null, message);
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('studentblockUI').hide();");
						reqcontext.execute("PF('studentRegDialog').hide();");
						setBoxflag(false);
					}

					valid = false;
					return "";
			}
		} catch (Exception e) {
			logger.warn(" exception - "+e);
			//logger.warn("Inside Exception",e);
		}
		return "";

	}

	public String convertToString(List<String> list) {
		String joinValue="";
		try{
			if(list!=null){
				for(int i=0; i<list.size(); i++){
					joinValue += list.get(i)+" ";
				}
				joinValue=joinValue.replace(" ", "");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return joinValue;
	}
	public String stuRegPage4() {
		String fieldName;
		SmsController controller = null;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			logger.debug("------stuRegPage4 method calling--------");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (validate3(true)) {
				if (personID != null) {
					if(schoolID.equals(paths.getString("SMRV.SCHOOLID"))){
						studentDataBean.setBankAccNo(convertToString(numberList));
						studentDataBean.setIfscCode(convertToString(ifscnoList));
						studentDataBean.setAadharCardNo(convertToString(aadharnoList));
						studentDataBean.setBranchCode(convertToString(branchcodeList));
						studentDataBean.setEmisNo(convertToString(emisnoList));
					}
					String status1 = controller.checkStudentRollno(studentDataBean, studentDataBean.getStuRollNo());
					if (status1.equalsIgnoreCase("Available")) {
						RequestContext reqcontextt = RequestContext.getCurrentInstance();
						reqcontextt.execute("studendBlk();");
					} else if (status1.equalsIgnoreCase("Exsist")) {
						fieldName = CommonValidate.findComponentInRoot("sRollNo").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Already Exsist"));
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('studentblockUI').hide();");
						reqcontext.execute("PF('studentRegDialog').hide();");
						setBoxflag(false);
					}
				}
			}
		} catch (Exception e) {
			logger.debug("-----stuRegPage4 method exception calling-------");
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";

	}

	public String reset() {
		logger.debug("Inside Reset Method Calling...");
		studentDataBean.setStuFirstName("");
		studentDataBean.setStuLastName("");
		studentDataBean.setStuFatherName("");
		studentDataBean.setStuMotherName("");
		studentDataBean.setStuDob(null);
		studentDataBean.setStuGender("");
		studentDataBean.setStuPresentAddress("");
		studentDataBean.setStuPermanentAddress("");
		studentDataBean.setStuPhoneNo("");
		studentDataBean.setStuEmail("");
		studentDataBean.setStuFatherOccu("");
		studentDataBean.setStuFatherIncome(null);
		studentDataBean.setStuMotherOccu("");
		studentDataBean.setStuCls(null);
		studentDataBean.setStuSession("");
		studentDataBean.setStuStudentId(null);
		studentDataBean.setStuRollNo(null);
		studentDataBean.setStuStudentPhoto("");
		studentDataBean.setStuPermanentCountry("");
		studentDataBean.setStuPresentCountry("");
		studentDataBean.setStuPermanentState("");
		studentDataBean.setStuPresentState("");
		studentDataBean.setStuPresentCity("");
		studentDataBean.setStuPermanentCity("");
		studentDataBean.setStuPresentpostal("");
		studentDataBean.setStuPermanentpostal("");
		studentDataBean.setStuPhoneNo1("");
		studentDataBean.setCode("");
		studentDataBean.setCode1("");
		return "";
	}

	public String dummyAction(FileUploadEvent event) throws IOException {
		this.file = event.getFile();
		studentDataBean.setStuFile(event.getFile());
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);
		return "";
	}

	private void copyFile(String fileName, InputStream inputstream, String n) {
		try {
			// Create Directory
			File files = new File(paths.getString("sms.student.photo") + ft.format(now));
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

	private String generatePdf(StudentDataBean studentDataBean2) {
		String status = "fail";
		SmsController controller = null;
		String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		int schoolid=Integer.parseInt(schoolID);
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				ImageListPath = new ArrayList<StudentDataBean>();
				ImageListPath = controller.getImagePath1(personID, studentDataBean2.getStuRollNo());
				logger.debug("sise "+ImageListPath.size());
				if (ImageListPath.size() > 0) {
					// Create Directory
					File files = new File(paths.getString("sms.student.pdf") + ft.format(now));
					if (!files.exists()) {
						files.mkdirs();
					} else {
						logger.debug("Alreday Found");
					}
					// Write PDF File
					OutputStream file = new FileOutputStream(
							new File(files + paths.getString("path_context").toString() + studentDataBean2.getStuUsername() + ".pdf"));
					studentDataBean.setStufilePath(files + paths.getString("path_context").toString() + studentDataBean2.getStuUsername() + ".pdf");
					Document document = new Document(PageSize.A4, 50, 50, 10, 50);
					PdfWriter.getInstance(document, file);
					document.open();
					String logo ="";
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						logo = paths.getString(schoolID+"_LOGO");
						document.add(Image.getInstance(getClass().getClassLoader().getResource(logo)));
					}else{
						logo = studentDataBean.getSchoolLogo();
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
					PdfPCell nesCell2 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuFirstName()));
					PdfPCell nesCell3 = new PdfPCell(new Paragraph("Last Name:"));
					PdfPCell nesCell4 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuLastName()));
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
					PdfPCell nesCell6 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuFatherName()));
					PdfPCell nesCell7 = new PdfPCell(new Paragraph("Mother Name:"));
					PdfPCell nesCell8 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuMotherName()));
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
					PdfPCell nesCell10 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuDob().toString()));
					PdfPCell nesCell11 = new PdfPCell(new Paragraph("Gender:"));
					PdfPCell nesCell12 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuGender()));
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
					PdfPCell nesCell14 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuPermanentAddress()));
					PdfPCell nesCell15 = new PdfPCell(new Paragraph("Permanent Address:"));
					PdfPCell nesCell16 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuPermanentAddress()));
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
					PdfPCell nesCell18 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuPermanentAddress()));
					PdfPCell nesCell19 = new PdfPCell(new Paragraph("Permanent State:"));
					PdfPCell nesCell20 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuPermanentState()));
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
					PdfPCell nesCell38 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuPresentZip()));
					PdfPCell nesCell39 = new PdfPCell(new Paragraph("Permanent Zip:"));
					PdfPCell nesCell40 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuPermanentZip()));
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
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6)
					{
					}else{
						PdfPTable nestedTable6 = new PdfPTable(4);
						nestedTable6.setWidthPercentage(100f);
						PdfPCell nesCell21 = new PdfPCell(new Paragraph("Phone Number:"));
						PdfPCell nesCell22 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuPhoneNo()));
						PdfPCell nesCell23 = new PdfPCell(new Paragraph("Email ID:"));
						PdfPCell nesCell24 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuEmail()));
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
					}
					

					PdfPTable nestedTable7 = new PdfPTable(4);
					nestedTable7.setWidthPercentage(100f);
					PdfPCell nesCell25 = new PdfPCell(new Paragraph("Father Occupation:"));
					PdfPCell nesCell26 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuFatherOccu()));
					PdfPCell nesCell27 = new PdfPCell(new Paragraph("Father Income:"));
					PdfPCell nesCell28 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuFatherIncome()));
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
					PdfPCell nesCell30 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuMotherOccu()));
					PdfPCell nesCell31 = new PdfPCell(new Paragraph("Class&Section:"));
					PdfPCell nesCell32 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuCls()));
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

					PdfPTable nestedTable9 = new PdfPTable(2);
					nestedTable9.setWidthPercentage(100f);
					PdfPCell nesCell33 = new PdfPCell(new Paragraph("Roll Number:"));
					PdfPCell nesCell34 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuRollNo()));
					nesCell33.setBorder(PdfPCell.NO_BORDER);
					nesCell34.setBorder(PdfPCell.NO_BORDER);
					nestedTable9.addCell(nesCell33);
					nestedTable9.addCell(nesCell34);
					nestedTable9.setSpacingBefore(3f);
					nestedTable9.setSpacingAfter(3f);
					cell1.addElement(nestedTable9);

					if(ImageListPath.get(0).getPathDate()!=null){
						PdfPTable nestedTable10 = new PdfPTable(2);
						nestedTable10.setWidthPercentage(100f);						
						PdfPCell nesCell45 = new PdfPCell(new Paragraph("Photo"));
						String tempdate = ft.format(ImageListPath.get(0).getPathDate());
						String imageLocation = paths.getString("sms.student.photo") + tempdate + "/" + ImageListPath.get(0).getPath();
						Image image = Image.getInstance(imageLocation);
						image.scaleAbsolute(100, 100);
						PdfPCell nesCell46 = new PdfPCell(image, false);
						nesCell45.setBorder(PdfPCell.NO_BORDER);
						nesCell46.setBorder(PdfPCell.NO_BORDER);
						nestedTable10.addCell(nesCell45);
						nestedTable10.addCell(nesCell46);
						nestedTable10.setSpacingBefore(3f);
						nestedTable10.setSpacingAfter(3f);
						cell1.addElement(nestedTable10);
					}
					
					table.addCell(cell1);
					table.addCell(cell2);
					document.add(table);
					document.close();
					file.close();

					/*
					 * //PDF open Windows and MAC File pdfFile = new
					 * File(files+paths.getString("path_context").toString()+studentDataBean2.getStuUsername()+".pdf")
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
			logger.warn(" exception - "+e);
			logger.warn("Inside Exception",e);
		}
		return status;
	}

	/*private String sendEmail(StudentDataBean studentDataBean2, List<StudentDataBean> imageListPath2)
			throws AddressException {
		String status = "Fail";
		String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
		String mail = studentDataBean2.getStuEmail();
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
				+ "<h3>Dear " + studentDataBean2.getStuUsername() + ",</h3>"
				+ "<p>Welcome on-board into "
				+ schoolName
				+ "family. Please find the your Username and Password enclosed with is mail."
				+ "All the very best in your new assignment</p>"
				+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
				+ "<tr>" + "<td>User Name" + "</td>" + "<td>" + studentDataBean2.getStuUsername() + "</td>" + "</tr>"
				+ "<tr>" + "<td>Password" + "</td>" + "<td>" + studentDataBean2.getStuSecurePasword() + "</td>"
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
			message.setSubject("SMS Confirmation-" + studentDataBean2.getStuUsername());
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setContent(body1, "text/html");

			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			String filename = paths.getString("sms.student.pdf") + ft.format(now) + "/" + studentDataBean2.getStuUsername()
					+ ".pdf";// change accordingly
			DataSource source = new FileDataSource(filename);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(studentDataBean2.getStuUsername() + ".pdf");

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
	}*/

	public String returnToHome() {

		logger.debug("Calling");
		SmsController controller = null;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				loginaccess.setPersonID(personID);
				controller.totalStudent(loginaccess);
				logger.debug("students -- " + loginaccess.getTotalStudent());
				loginMB.loginaccess.setTotalStudent(loginaccess.getTotalStudent());
				setBoxflag(false);
				studentPageLoad();
				return "";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";

	}

	public void valueChangeMethod(ValueChangeEvent e) {

	}
	
	public void state(ValueChangeEvent v){
		SmsController controller=null;
		String valuechange="";
		setCode("");
		statelist=new ArrayList<String>();
		try{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			System.out.println("=========================================1");
			controller = (SmsController) ctx.getBean("controller");	
			valuechange=v.getNewValue().toString();				
			statelist=controller.getcountryList(valuechange); 
			System.out.println("================>"+statelist.size());  
			if(valuechange.equalsIgnoreCase("INDIA")) studentDataBean.setCode("+91"); 
		     else if(valuechange.equalsIgnoreCase("MALAYSIA")) studentDataBean.setCode("+60"); 
		     else if(valuechange.equalsIgnoreCase("UAE")) studentDataBean.setCode("+971"); 
		     else if(valuechange.equalsIgnoreCase("INDONESIA")) studentDataBean.setCode("+62");
		     else 
		     {
		    	 studentDataBean.setCode("00");
		     }
		}catch(Exception e){
			e.printStackTrace();
		}
		}

	// Null pointer exception coming if no country selected
	public void state1(ValueChangeEvent v){
		SmsController controller=null;
		String valuechange="";
		setCode("");statelist1=new ArrayList<String>();
		try{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			System.out.println("=========================================1");
			controller = (SmsController) ctx.getBean("controller");	
			valuechange=v.getNewValue().toString();				
			statelist1=controller.getcountryList(valuechange); 
			System.out.println("================>"+statelist1.size());  
			if(valuechange.equalsIgnoreCase("INDIA")) studentDataBean.setCode1("+91"); 
		     else if(valuechange.equalsIgnoreCase("MALAYSIA")) studentDataBean.setCode1("+60"); 
		     else if(valuechange.equalsIgnoreCase("UAE")) studentDataBean.setCode1("+971"); 
		     else if(valuechange.equalsIgnoreCase("INDONESIA")) studentDataBean.setCode1("+62");
		     else 
		     {
		    	 studentDataBean.setCode1("00");
		     }
		}catch(Exception e){
			//e.printStackTrace();
			System.out.println("[StudentMB-state1]Exception -->"+e.getMessage());
		}
		}
	
	public void checkbox(ValueChangeEvent v){	
		 String chkBox=null;
		try{
			System.out.println("========================>insidecheckbox================================>");
			chkBox=v.getNewValue().toString();
			if(chkBox.equals("true")){
				setPermenantAddFlag(false);
				setPermenantAddFlag1(true); 
				studentDataBean.setStuPermanentAddress(studentDataBean.getStuPresentAddress());
				studentDataBean.setStuPermanentCountry(studentDataBean.getStuPresentCountry());
				studentDataBean.setStuPermanentState(studentDataBean.getStuPresentState());
				studentDataBean.setStuPermanentCity(studentDataBean.getStuPresentCity());
				studentDataBean.setStuPermanentpostal(studentDataBean.getStuPresentpostal());
				studentDataBean.setStuPhoneNo1(studentDataBean.getStuPhoneNo());
				studentDataBean.setCode1(studentDataBean.getCode());
			}else{
				setPermenantAddFlag(true);
				setPermenantAddFlag1(false);  
				studentDataBean.setStuPermanentAddress("");
				studentDataBean.setStuPermanentCountry("");
				studentDataBean.setStuPermanentState("");
				studentDataBean.setStuPermanentCity("");
				studentDataBean.setStuPermanentpostal("");
				studentDataBean.setStuPhoneNo1("");
				studentDataBean.setCode1("");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
