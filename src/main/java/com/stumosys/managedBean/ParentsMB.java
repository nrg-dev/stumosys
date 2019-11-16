package com.stumosys.managedBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
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
import javax.mail.internet.MimeMultipart;*/

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

//import com.itextpdf.text.Document;
//import com.itextpdf.text.Font;
/*import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
*/import com.stumosys.controler.SmsController;
import com.stumosys.domain.ParentsDataBean;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;



@ManagedBean(name = "parentsMB")
@SessionScoped
public class ParentsMB {
	ParentsDataBean parentsDataBean = new ParentsDataBean();
	public List<ParentsDataBean> parentsBean;
	Date now = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	private UploadedFile file;
	private List<String> classSectionList = null;
	private List<String> studentIDList = null;
	private boolean boxflag = false;
	private List<ParentsDataBean> parentList = null;
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	private static Logger logger = Logger.getLogger(ParentsMB.class);
	private List<ParentsDataBean> ImageListPath = null;
	@ManagedProperty(value = "#{loginMB}")
	private LoginMB loginMB;
	private List<String> menulist=null;
	private boolean menuflag=false;
	public String firstname;
	public String lastname;
	public String relation;
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
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

	public List<ParentsDataBean> getParentsBean() {
		return parentsBean;
	}

	public void setParentsBean(List<ParentsDataBean> parentsBean) {
		this.parentsBean = parentsBean;
	}

	public LoginMB getLoginMB() {
		return loginMB;
	}

	public void setLoginMB(LoginMB loginMB) {
		this.loginMB = loginMB;
	}

	/**
	 * @return the imageListPath
	 */
	public List<ParentsDataBean> getImageListPath() {
		return ImageListPath;
	}

	/**
	 * @param imageListPath
	 *            the imageListPath to set
	 */
	public void setImageListPath(List<ParentsDataBean> imageListPath) {
		ImageListPath = imageListPath;
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

	public String loadParentsPage() {
		SmsController controller = null;

		try {
			logger.debug("----------Inside loadParentPage Method Calling-----");
			classSectionList = null;
			studentIDList = null;
			parentsDataBean.setParStudID("");
			parentsDataBean.setParFirstName("");
			parentsDataBean.setParLastName("");
			parentsDataBean.setParentRelation("");
			parentsDataBean.setParEmail("");
			parentsDataBean.setParPhoneNo("");
			parentsDataBean.setParParentPhoto("");
			parentsDataBean.setParStuClass("");
			parentsDataBean.setParentDetailsList(null);
			parentsDataBean.setParentImageList(null);
			parentsDataBean.setParentList(null);
			parentsDataBean.setParParentID("");
			parentsDataBean.setStudentParentList(null);
			parentsDataBean.setParSecurePasword("");
			parentsDataBean.setParUsername("");
			parentsBean.clear();
			classSectionList = new ArrayList<String>();
			setBoxflag(false);
			setMenuflag(false);
			parentsDataBean.setMenus(null);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				menulist=new ArrayList<String>();
				menulist=controller.getmenus();
				classSectionList = controller.getClassSection(personID);
				return "parentsRegistration";
			} else {
				return "";
			}			
		} catch (Exception e) {
			logger.debug("----------Inside loadParentPage Method Exception Calling-----");
			e.getStackTrace();
			return "failure";
		}

	}

	public boolean validate(boolean flag) {
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
		}*/
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

		return valid;
	}

	public String parRegPage() {
		try {
			setBoxflag(false);
			setMenuflag(menuflag);
			logger.debug("------parRegPage method calling--------");
			if (validate(true)) {		
				logger.debug("sizes - "+parentsBean.size());	
				if(parentsBean.size()==0){
					ParentsDataBean parents=new ParentsDataBean();
					parents.setParStuClass(parentsDataBean.getParStuClass());
					parents.setParStudID(parentsDataBean.getParStudID());
					parentsBean.add(parents);
					parentsDataBean.setParentdetails(parentsBean);
				}else{
					parentsDataBean.setParentdetails(parentsBean);
				}
				String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
				int schoolid=Integer.parseInt(schoolID);
				if(schoolid==1 || schoolid==2||schoolid==3||schoolid==4||schoolid==5||schoolid==6){
					setMenuflag(false);
				}else{
					setMenuflag(true);
				}
				logger.debug("size - "+parentsBean.size());				
				return "parentsRegistration1";
			}

		} catch (Exception e) {
			logger.debug("-----parRegPage method exception calling-------");
			logger.warn("Exception -->"+e.getMessage());
			return "failure";

		}

		return "";
	}

	public boolean validate1(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (StringUtils.isEmpty(parentsDataBean.getParParentID())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("parParentID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.parentId")));
			}
			valid = false;
		}
		
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
		/* else if (!StringUtils.isEmpty(parentsDataBean.getParParentID())) {
			if (parentsDataBean.getParParentID().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("parParentID").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.vparentID")));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(parentsDataBean.getParEmail())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("parE-mail").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.email")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(parentsDataBean.getParEmail())) {
			if (!CommonValidate.validateEmail(parentsDataBean.getParEmail())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("parE-mail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.vemail")));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(parentsDataBean.getParPhoneNo())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("parPhone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.phno")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(parentsDataBean.getParPhoneNo())) {
			if(!CommonValidate.validateNumberOnly(parentsDataBean.getParPhoneNo())){
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("parPhone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumberinvalid")));
			}
			valid = false;
			}
		else if(new BigDecimal(parentsDataBean.getParPhoneNo()).compareTo(BigDecimal.ZERO)==0){
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("parPhone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumberinvalid")));
			}
			valid = false;
		}else if(parentsDataBean.getParPhoneNo().length()<11){
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("parPhone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumberinvalid")));
			}
			valid = false;
		}
		}
		if (parentsDataBean.getParFile() == null) {
			if (flag) {

				fieldName = CommonValidate.findComponentInRoot("parPhoto").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.photo")));
			}
			valid = false;
		}
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		int schoolid=Integer.parseInt(schoolID);
		if(schoolid==1 || schoolid==2||schoolid==3||schoolid==4||schoolid==5||schoolid==6){
			logger.debug("inside if------");
		}else{
			if (parentsDataBean.getMenus().length==0) {
				if (flag) {
							fieldName = CommonValidate.findComponentInRoot("menus").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage("Please Choose the Menus"));
						}
						valid = false;
				}
		}*/
		return valid;
	}

	public String reset() {
		logger.debug("Inside Reset Method Calling...");
		parentsDataBean.setParStudID("");
		parentsDataBean.setParFirstName("");
		parentsDataBean.setParLastName("");
		parentsDataBean.setParentRelation("");
		parentsDataBean.setParEmail("");
		parentsDataBean.setParPhoneNo("");
		parentsDataBean.setParParentPhoto("");
		setBoxflag(false);

		return "";

	}
	/* Nivesh OCT 25   insertParent*/
	public String insertParent() {
		SmsController controller = null;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			setBoxflag(false);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
				String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
				if(parentsDataBean.getParFile() !=null){
					logger.debug(parentsDataBean.getParFile().getContentType());
					String s = parentsDataBean.getParFile().getContentType();
					logger.debug("file Type " + s);
					String type = s.substring(s.lastIndexOf("/") + 1);
					logger.debug(type);

					copyFile(parentsDataBean.getParParentID(), parentsDataBean.getParFile().getInputstream(), type);

					logger.debug(parentsDataBean.getParParentID() + "." + type);
					String path = ft.format(now) + "/" + parentsDataBean.getParParentID() + "." + type;
					parentsDataBean.setParfilePath(path);
					logger.debug(parentsDataBean.getParfilePath());
				}
				/*if(parentsDataBean.getParPhoneNo().startsWith(text.getString("sms.phno"))){
					parentsDataBean.setParPhoneNo(parentsDataBean.getParPhoneNo());
				}else if(parentsDataBean.getParPhoneNo().startsWith("0")){
					String phoneno=parentsDataBean.getParPhoneNo().substring(1);
					parentsDataBean.setParPhoneNo(text.getString("sms.phno")+phoneno);
				}else{
					parentsDataBean.setParPhoneNo(text.getString("sms.phno")+parentsDataBean.getParPhoneNo());
				}*/
				String status = controller.insertParents(personID, parentsDataBean);
				if (status.equalsIgnoreCase("Success")) {
					parentList = controller.getParentDetilsList(personID, parentsDataBean);
					logger.debug("MB List Size" + parentList.size());
					if (parentList.size() > 0) {
						/*String pdfStatus = generatePdf(parentList, parentsDataBean);*/
						MailSendJDBC.parentInsert(parentsDataBean,schoolName,schoolID);
						/*String emailStatus = sendEmail(parentList, personID, parentsDataBean);
						if (pdfStatus.equalsIgnoreCase("Success") && emailStatus.equalsIgnoreCase("Success")) {
							*/
							/*if (pdfStatus.equalsIgnoreCase("Success")) {*/

							classSectionList = null;
							studentIDList = null;
							// parentsDataBean.setParStudID("");
							parentsDataBean.setParFirstName("");
							parentsDataBean.setParLastName("");
							parentsDataBean.setParentRelation("");
							parentsDataBean.setParEmail("");
							parentsDataBean.setParPhoneNo("");
							parentsDataBean.setParParentPhoto("");
							parentsDataBean.setParStuClass("");
							parentsDataBean.setParentDetailsList(null);
							parentsDataBean.setParentImageList(null);
							parentsDataBean.setParentList(null);
							parentsDataBean.setParParentID("");
							parentsDataBean.setStudentParentList(null);
							parentsDataBean.setParSecurePasword("");
							parentsDataBean.setParUsername("");
							RequestContext reqcontext = RequestContext.getCurrentInstance();
							reqcontext.execute("PF('parentblockUI').hide();");
							reqcontext.execute("PF('parentsRegDialog').show();");
							reqcontext.execute("PF('parantExsistDialog').hide();");
							//setBoxflag(true);
						
					}
					// String pdfStatus=generatePdf(parentsDataBean);
				} else if (status.equalsIgnoreCase("Exsist")) {
					fieldName = CommonValidate.findComponentInRoot("parParentID").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("This  Parent ID is Already Exist."));
					RequestContext reqcontext = RequestContext.getCurrentInstance();
					reqcontext.execute("PF('parentblockUI').hide();");
					reqcontext.execute("PF('parentsRegDialog').hide();");
					reqcontext.execute("PF('parantExsistDialog').show();");
					//setBoxflag(false);
				} else {
					logger.debug("Session Out");
					RequestContext reqcontext = RequestContext.getCurrentInstance();
					reqcontext.execute("PF('parentblockUI').hide();");
					reqcontext.execute("PF('parentsRegDialog').hide();");
					reqcontext.execute("PF('parantExsistDialog').hide();");
					//setBoxflag(false);
				}
			}

		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return "";

	}

	public String parRegPage1() {
		try {
			setBoxflag(false);
			logger.info("------parRegPage1 method calling--------");
			if (validate1(true)) {
			/*if (parentsDataBean.getCountryCode().equalsIgnoreCase("")) {
					logger.info("insdie if phncode null");
			}
			else{
				parentsDataBean.setParPhoneNo(parentsDataBean.getCountryCode().split("-")[0]+parentsDataBean.getParPhoneNo());
			}*/
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
				if (personID != null) {
					RequestContext reqcontextt = RequestContext.getCurrentInstance();
					reqcontextt.execute("parentdBlk();");// calling JavaScript

				}
			}

		} catch (Exception e) {
			logger.debug("-----parRegPage1 method exception calling-------");
			logger.warn("Exception -->"+e.getMessage());
		}

		return "";
	}

	/*private String generatePdf(List<ParentsDataBean> parentList2, ParentsDataBean parentsDataBean2) {

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
							new File(files + paths.getString("path_context").toString() + parentsDataBean2.getParUsername() + ".pdf"));
					parentsDataBean.setParfilePath(files + paths.getString("path_context").toString() + parentsDataBean2.getParUsername() + ".pdf");
					Document document = new Document(PageSize.A4, 50, 50, 10, 50);
					PdfWriter.getInstance(document, file);
					document.open();
					String logo ="";
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						logo = paths.getString(schoolID+"_LOGO");
						document.add(Image.getInstance(getClass().getClassLoader().getResource(logo)));
					}else{
						logo = parentsDataBean.getSchoolLogo();
						Image img=Image.getInstance(logo);
						img.scaleAbsolute(100,100);
						document.add(Image.getInstance(logo));
					}
					document.add(new Paragraph(
							"------------------------------------------------------------------------------------------------------------------------"));

					float[] columnWidths = { 20f, 1f };
					//Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 27);
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
					else if(ImageListPath.size()==6)
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
						PdfPTable nestedTable15 = new PdfPTable(4);
						nestedTable15.setWidthPercentage(100f);
						PdfPCell nesCell55 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell65 = new PdfPCell(new Paragraph(ImageListPath.get(5).getParStudID()));
						PdfPCell nesCell75 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell85 = new PdfPCell(new Paragraph(ImageListPath.get(5).getParStuClass()));
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
					else if(ImageListPath.size()==7)
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
						PdfPTable nestedTable15 = new PdfPTable(4);
						nestedTable15.setWidthPercentage(100f);
						PdfPCell nesCell55 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell65 = new PdfPCell(new Paragraph(ImageListPath.get(5).getParStudID()));
						PdfPCell nesCell75 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell85 = new PdfPCell(new Paragraph(ImageListPath.get(5).getParStuClass()));
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
						PdfPCell nesCell66 = new PdfPCell(new Paragraph(ImageListPath.get(6).getParStudID()));
						PdfPCell nesCell76 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell86 = new PdfPCell(new Paragraph(ImageListPath.get(6).getParStuClass()));
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
					else if(ImageListPath.size()==8)
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
						PdfPTable nestedTable15 = new PdfPTable(4);
						nestedTable15.setWidthPercentage(100f);
						PdfPCell nesCell55 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell65 = new PdfPCell(new Paragraph(ImageListPath.get(5).getParStudID()));
						PdfPCell nesCell75 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell85 = new PdfPCell(new Paragraph(ImageListPath.get(5).getParStuClass()));
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
						PdfPCell nesCell66 = new PdfPCell(new Paragraph(ImageListPath.get(6).getParStudID()));
						PdfPCell nesCell76 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell86 = new PdfPCell(new Paragraph(ImageListPath.get(6).getParStuClass()));
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
						PdfPCell nesCell67 = new PdfPCell(new Paragraph(ImageListPath.get(7).getParStudID()));
						PdfPCell nesCell77 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell87 = new PdfPCell(new Paragraph(ImageListPath.get(7).getParStuClass()));
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
					else if(ImageListPath.size()==9)
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
						PdfPTable nestedTable15 = new PdfPTable(4);
						nestedTable15.setWidthPercentage(100f);
						PdfPCell nesCell55 = new PdfPCell(new Paragraph("Student ID:"));
						PdfPCell nesCell65 = new PdfPCell(new Paragraph(ImageListPath.get(5).getParStudID()));
						PdfPCell nesCell75 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell85 = new PdfPCell(new Paragraph(ImageListPath.get(5).getParStuClass()));
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
						PdfPCell nesCell66 = new PdfPCell(new Paragraph(ImageListPath.get(6).getParStudID()));
						PdfPCell nesCell76 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell86 = new PdfPCell(new Paragraph(ImageListPath.get(6).getParStuClass()));
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
						PdfPCell nesCell68 = new PdfPCell(new Paragraph(ImageListPath.get(8).getParStudID()));
						PdfPCell nesCell78 = new PdfPCell(new Paragraph("Student Class:"));
						PdfPCell nesCell88 = new PdfPCell(new Paragraph(ImageListPath.get(8).getParStuClass()));
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
					if (ImageListPath.get(0).getParfilePath()!= null) {
						String tempdate = ft.format(ImageListPath.get(0).getParPathDate());
						String imageLocation = paths.getString("sms.parents.photo") + tempdate + "/"
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
				}
					table.addCell(cell1);
					table.addCell(cell2);
					document.add(table);
					document.close();
					file.close();
					
					 * //PDF open Windows and MAC File pdfFile = new
					 * File(files+paths.getString("path_context").toString()+parentsDataBean2.getParUsername()+".pdf")
					 * ; if(pdfFile.exists()){ if (Desktop.isDesktopSupported())
					 * { Desktop.getDesktop().open(pdfFile); } else {
					 * logger.debug("Awt Desktop is not supported!"); }
					 * 
					 * } else { logger.debug("File is not exists!"); }
					 

					logger.debug("Done");
					status = "Success";
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}
*/
	/*
	 * public void handleFileUpload(FileUploadEvent event) { FacesMessage
	 * message = new FacesMessage("Succesful", event.getFile().getFileName() +
	 * " is uploaded."); FacesContext.getCurrentInstance().addMessage(null,
	 * message); }
	 */
	public void classChange(ValueChangeEvent event) {
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

	public String dummyAction(FileUploadEvent event) throws IOException {
		this.file = event.getFile();
		parentsDataBean.setParFile(event.getFile());
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);
		logger.debug(parentsDataBean.getParFile().getContentType());

		return "";
	}

	public String returnToHome() {
		SmsController controller = null;
		int parentCount = 0;

		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				parentCount = controller.getTotalParent(personID);
				if (parentCount > 0) {
					String count = String.valueOf(parentCount);
					loginMB.loginaccess.setTotalParent(count);
				}
				loadParentsPage();
				return "SuccessHome";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
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
					+ "<h3>Dear " + parentsDataBean2.getParUsername() + ",</h3>"
					+ "<p>Welcome to join  our Student Monitoring System. you can find the following features  ."
					+ "you can check your child report card, attendence,online payment,notice board and time table."
					+ "also you can get our user name and password, from that you can check our website itself</p>"
					+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
					+ "<tr>" + "<td>User Name" + "</td>" + "<td>" + parentsDataBean2.getParUsername() + "</td>"
					+ "</tr>" + "<tr>" + "<td>Password" + "</td>" + "<td>" + parentsDataBean2.getParSecurePasword()
					+ "</td>" + "</tr>" + "</table>" + "<footer>" + "<center>"
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
				message.setSubject("SMS Confirmation-" + parentsDataBean2.getParUsername());
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");

				MimeBodyPart messageBodyPart2 = new MimeBodyPart();
				String filename = paths.getString("sms.parents.pdf") + ft.format(now) + "/" + parentsDataBean2.getParUsername()
						+ ".pdf";// change accordingly
				DataSource source = new FileDataSource(filename);
				messageBodyPart2.setDataHandler(new DataHandler(source));
				messageBodyPart2.setFileName(parentsDataBean2.getParUsername() + ".pdf");

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
	
	@PostConstruct
    public void init() {
        parentsDataBean = new ParentsDataBean();
        parentsBean = new ArrayList<ParentsDataBean>();         
    }
	
	public String addStudent()
	{
		logger.debug("inside add student");	
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if(studentCheck(true))
		{
			logger.debug("inside ");
			parentsDataBean = new ParentsDataBean();
			parentsDataBean.setParFirstName(firstname);
			parentsDataBean.setParLastName(lastname);
			parentsDataBean.setParentRelation(relation);
			int count=0;
			logger.debug("size "+parentsBean.size());
			if(parentsBean.size()>1){
				for (int i = 0; i < parentsBean.size()-1; i++) {
					if (studentID.equalsIgnoreCase(parentsBean.get(i).getParStudID())) count++;
				}
			}		
			if(count>0){
				fieldName = CommonValidate.findComponentInRoot("parStudentID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.astudentID")));
				parentsBean.remove(parentsBean.size()-1);
				parentsDataBean.setParStuClass(classname);
				parentsDataBean.setParStudID(studentID);
			}
		}
		else {
			parentsBean.remove(parentsBean.size()-1);
		}
		return "";
	}
	public String studentID;
	public String classname;
	
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
		firstname=parentsDataBean.getParFirstName();
		lastname=parentsDataBean.getParLastName();
		relation=parentsDataBean.getParentRelation();				
		return valid;
	}
}
