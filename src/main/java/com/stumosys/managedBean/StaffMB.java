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
//import java.util.Properties;
import java.util.ResourceBundle;

/*import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
*/import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
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
import com.stumosys.domain.StaffDataBean;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name = "staffMB")
@RequestScoped
public class StaffMB {
	StaffDataBean staffDataBean = new StaffDataBean();
	Date now = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	private UploadedFile file;
	private List<String> staffIDList = null;
	private boolean boxflag = false;
	private boolean boxflag1 = false;
	private boolean delboxflag = false;

	public boolean isDelboxflag() {
		return delboxflag;
	}

	public void setDelboxflag(boolean delboxflag) {
		this.delboxflag = delboxflag;
	}

	public boolean isBoxflag1() {
		return boxflag1;
	}

	public void setBoxflag1(boolean boxflag1) {
		this.boxflag1 = boxflag1;
	}

	private List<StaffDataBean> staffList = null;
	private List<StaffDataBean> filterstaffList = null;
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	private static Logger logger = Logger.getLogger(StaffMB.class);
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

	private List<StaffDataBean> ImageListPath = null;
	List<StaffDataBean> stafftaglist = null;

	public List<StaffDataBean> getStafftaglist() {
		return stafftaglist;
	}

	public void setStafftaglist(List<StaffDataBean> stafftaglist) {
		this.stafftaglist = stafftaglist;
	}

	public List<StaffDataBean> getFilterstaffList() {
		return filterstaffList;
	}

	public void setFilterstaffList(List<StaffDataBean> filterstaffList) {
		this.filterstaffList = filterstaffList;
	}

	/**
	 * @return the imageListPath
	 */
	public List<StaffDataBean> getImageListPath() {
		return ImageListPath;
	}

	/**
	 * @param imageListPath
	 *            the imageListPath to set
	 */
	public void setImageListPath(List<StaffDataBean> imageListPath) {
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
	 * @return the StaffDataBean
	 */
	public StaffDataBean getStaffDataBean() {
		return staffDataBean;
	}

	/**
	 * @param staffDataBean
	 *            the staffDataBean to set
	 */
	public void setStaffDataBean(StaffDataBean staffDataBean) {
		this.staffDataBean = staffDataBean;
	}

	/**
	 * @return the staffIDList
	 */
	public List<String> getStaffIDList() {
		return staffIDList;
	}

	/**
	 * @param staffIDList
	 *            the staffIDList to set
	 */
	public void setStaffIDList(List<String> staffIDList) {
		this.staffIDList = staffIDList;
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
	 * @return the staffList
	 */
	public List<StaffDataBean> getStaffList() {
		return staffList;
	}

	/**
	 * @param staffList
	 *            the staffList to set
	 */
	public void setStaffList(List<StaffDataBean> staffList) {
		this.staffList = staffList;
	}

	public String loadStaffPage() {
		logger.debug("----------Inside loadBookshopPage Method Calling-----");
		try {
			staffIDList = null;
			staffDataBean.setStaStaffID("");
			staffDataBean.setStaFirstName("");
			staffDataBean.setStaLastName("");
			staffDataBean.setStaEmail("");
			staffDataBean.setStaPhoneNo("");
			staffDataBean.setStaStaffPhoto("");
			staffDataBean.setStaffImageList(null);
			staffDataBean.setStaffList(null);
			staffDataBean.setStaSecurePasword("");
			staffDataBean.setStaFile(null);
			staffDataBean.setStaUsername("");
			staffDataBean.setStaGender("");
			staffDataBean.setCountryCode("");
			setBoxflag(false);
			setMenuflag(false);
			staffDataBean.setMenus(null);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			menulist=new ArrayList<String>();
			menulist=controller.getmenus();
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			if(schoolid==1 || schoolid==2||schoolid==3||schoolid==4||schoolid==5||schoolid==6){
				setMenuflag(false);
			}else{
				setMenuflag(true);
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return "bookshopReg";

	}
	public String loadStaffPage2() {
		logger.debug("----------Inside loadStaffPage Method Calling-----");
		try {
			staffIDList = null;
			staffDataBean.setStaStaffID("");
			staffDataBean.setStaFirstName("");
			staffDataBean.setStaLastName("");
			staffDataBean.setStaEmail("");
			staffDataBean.setStaPhoneNo("");
			staffDataBean.setStaStaffPhoto("");
			staffDataBean.setStaffImageList(null);
			staffDataBean.setStaffList(null);
			staffDataBean.setStaSecurePasword("");
			staffDataBean.setStaFile(null);
			staffDataBean.setStaUsername("");
			staffDataBean.setStaGender("");
			staffDataBean.setCountryCode("");
			setBoxflag(false);
			setMenuflag(false);
			staffDataBean.setMenus(null);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			menulist=new ArrayList<String>();
			menulist=controller.getmenus();
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			if(schoolid==1 || schoolid==2||schoolid==3||schoolid==4||schoolid==5||schoolid==6){
				setMenuflag(false);
			}else{
				setMenuflag(true);
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return "staffReg";

	}

	public boolean validate(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();

		if (StringUtils.isBlank(staffDataBean.getStaStaffID())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("staStaffID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.staffID")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(staffDataBean.getStaStaffID())) {
			if (staffDataBean.getStaStaffID().length() < 3) {
				if (!CommonValidate.validateName(staffDataBean.getStaStaffID())) {
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("staStaffID").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.vstaffID")));
					}
					valid = false;
				}
			} else if (staffDataBean.getStaStaffID().length() >= 3) {
				if (!CommonValidate.validateName(staffDataBean.getStaStaffID())) {
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("staStaffID").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.vstaffID")));
					}
					valid = false;
				}
			}
		}
		if (StringUtils.isEmpty(staffDataBean.getCountryCode())) {
			/*if (flag) {
				fieldName = CommonValidate.findComponentInRoot("parPhone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.CountryCode")));
			}
			valid = false;*/
		} 
		else{
		if (StringUtils.isEmpty(staffDataBean.getStaPhoneNo())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("staPhone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.phno")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(staffDataBean.getStaPhoneNo())) {
			if(!CommonValidate.validateNumberOnly(staffDataBean.getStaPhoneNo())){
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("staPhone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumberinvalid")));
			}
			valid = false;
			}
			
			else if(!CommonValidate.countryCodeValidation(staffDataBean.getStaPhoneNo(),staffDataBean.getCountryCode())){
				fieldName = CommonValidate.findComponentInRoot("staPhone").getClientId(fc);
				if (staffDataBean.getCountryCode().equalsIgnoreCase("+91")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.india.phonevalidation")));
				}
				else if (staffDataBean.getCountryCode().equalsIgnoreCase("+971")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.uae.phonevalidation")));
				}
				else if (staffDataBean.getCountryCode().equalsIgnoreCase("+62")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.indonesia.phonevalidation")));
				}
				else if (staffDataBean.getCountryCode().equalsIgnoreCase("+60")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.malaysia.phonevalidation")));
				}
				
				valid = false;
			}
			
		}
	}
		if (StringUtils.isEmpty(staffDataBean.getStaFirstName())) {
			System.out.println("valie"+staffDataBean.getStaFirstName());
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("staFName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.fname")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(staffDataBean.getStaFirstName())) {
			if (staffDataBean.getStaFirstName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("staFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.vfname")));
				}
				valid = false;
			} else if (!CommonValidate.validateName(staffDataBean.getStaFirstName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("staFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.vfname")));
				}
				valid = false;
			}
		}
		/*if (StringUtils.isEmpty(staffDataBean.getStaLastName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("staLName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.lname")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(staffDataBean.getStaLastName())) {
			if (staffDataBean.getStaLastName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("staLName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.vlame")));
				}
				valid = false;=======================
			} else if (!CommonValidate.validateName(staffDataBean.getStaLastName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("staLName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.vlame")));
				}
				valid = false;
			}
		}*/
		/*if (StringUtils.isEmpty(staffDataBean.getStaGender())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("staGender").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.gender")));
			}
			valid = false;

		}*/
		/*if (StringUtils.isEmpty(staffDataBean.getStaEmail())) {
			{
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("staE-mail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.email")));
				}
				valid = false;
			}
		}*//* else if (!StringUtils.isEmpty(staffDataBean.getStaEmail())) {
			if (!CommonValidate.validateEmail(staffDataBean.getStaEmail())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("staE-mail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.vemail")));
				}
				valid = false;
			}
		}*/
		/*if (StringUtils.isEmpty(staffDataBean.getStaPhoneNo())) {
			if (flag) {

				fieldName = CommonValidate.findComponentInRoot("staPhone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.phno")));
			}
			valid = false;
		}else if(!StringUtils.isEmpty(staffDataBean.getStaPhoneNo())){
			if(!CommonValidate.validateNumberOnly(staffDataBean.getStaPhoneNo())){
				if (flag) {

					fieldName = CommonValidate.findComponentInRoot("staPhone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.vphno")));
				}
				valid = false;
			}else if(Integer.parseInt(staffDataBean.getStaPhoneNo()) <= 0){
				if (flag) {

					fieldName = CommonValidate.findComponentInRoot("staPhone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.vphno")));
				}
				valid = false;
			}
			else if(staffDataBean.getStaPhoneNo().length()<11){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("staPhone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.vphno")));
				}
				valid = false;
			}	
		}*/
		
		/*if (staffDataBean.getStaFile() == null) {
			System.out.println("file pic "+staffDataBean.getStaFile()==null);
			if (flag) {

				fieldName = CommonValidate.findComponentInRoot("staPhoto").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.photo")));
			}
			valid = false;
		}*/
		/*String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		int schoolid=Integer.parseInt(schoolID);
		if(schoolid==1 || schoolid==2||schoolid==3||schoolid==4||schoolid==5||schoolid==6){
		logger.debug("inside if---");	
		}else{
			if (staffDataBean.getMenus().length==0) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("menus").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose the menus"));
				}
				valid = false;
			}
		}*/
		if (!StringUtils.isEmpty(staffDataBean.getStaEmail())) {
			if (!CommonValidate.validateEmail(staffDataBean.getStaEmail())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("staE-mail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.vemail")));
				}
				valid = false;
			}
		}
		if(!StringUtils.isEmpty(staffDataBean.getStaPhoneNo())){
			if(StringUtils.isEmpty(staffDataBean.getCountryCode())){
				fieldName = CommonValidate.findComponentInRoot("countrycode").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Countrycode"));
				valid = false;
			}
		}
System.out.println("return"+valid);
		return valid;
	}

	public String reset() {
		logger.debug("Inside Reset Method Calling...");
		staffDataBean.setStaStaffID("");
		staffDataBean.setStaFirstName("");
		staffDataBean.setStaLastName("");
		staffDataBean.setStaGender("");
		staffDataBean.setStaEmail("");
		staffDataBean.setStaPhoneNo("");
		staffDataBean.setStaStaffPhoto("");
		setBoxflag(false);
		staffDataBean.setCountryCode("");
		staffDataBean.setStaGender("");
		staffDataBean.setStaFile(null);
		return "";

	}

	public String insertStaff() {
		SmsController controller = null;
		String fieldname;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			setBoxflag(false);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			/*
			 * String
			 * idcheck=controller.staffidcheck(staffDataBean,staffDataBean.
			 * getStaStaffID());
			 */ if (personID != null) {
				 String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
				 String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
				if(staffDataBean.getStaFile()==null){
					staffDataBean.setStafilePath("");
				}else{
					String s = staffDataBean.getStaFile().getContentType();
					String type = s.substring(s.lastIndexOf(paths.getString("path_context").toString()) + 1);
					copyFile(staffDataBean.getStaStaffID(), staffDataBean.getStaFile().getInputstream(), type);
					String path = ft.format(now) + paths.getString("path_context").toString() + staffDataBean.getStaStaffID() + "." + type;
					staffDataBean.setStafilePath(path);
				}
				/*if(staffDataBean.getStaPhoneNo().startsWith(text.getString("sms.phno"))){
					staffDataBean.setStaPhoneNo(staffDataBean.getStaPhoneNo());
				}else if(staffDataBean.getStaPhoneNo().startsWith("0")){
					String phoneno=staffDataBean.getStaPhoneNo().substring(1);
					staffDataBean.setStaPhoneNo(text.getString("sms.phno")+phoneno);
				}else{
					staffDataBean.setStaPhoneNo(text.getString("sms.phno")+staffDataBean.getStaPhoneNo());
				}*/
				String status = controller.insertStaff(personID, staffDataBean);
				logger.debug("check status--" + status);
				if (status.equalsIgnoreCase("Success")) {
					//String pdfStatus = generatePdf(staffDataBean);		
					MailSendJDBC.bookShopInsert(staffDataBean,schoolName,schoolID);
					/*String emailStatus = sendEmail(staffDataBean, ImageListPath);*/
					/*if (pdfStatus.equalsIgnoreCase("Success") && emailStatus.equalsIgnoreCase("Success")) {*/
					//if (pdfStatus.equalsIgnoreCase("Success")) {
						staffList = null;
						staffDataBean.setStaStaffID("");
						staffDataBean.setStaFirstName("");
						staffDataBean.setStaLastName("");
						staffDataBean.setStaGender("");
						staffDataBean.setStaEmail("");
						staffDataBean.setStaPhoneNo("");
						staffDataBean.setStaStaffPhoto("");
						staffDataBean.setStaffImageList(null);
						staffDataBean.setStaffList(null);
						staffDataBean.setStaSecurePasword("");
						staffDataBean.setStaUsername("");
						staffDataBean.setCountryCode("");
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('staffblockUI').hide();");
						reqcontext.execute("PF('staffRegDialog').show();");
						setBoxflag(true);

					//}

				} else if (status.equalsIgnoreCase("Exsist")) {
					fieldname = CommonValidate.findComponentInRoot("staStaffID").getClientId(fc);
					fc.addMessage(fieldname, new FacesMessage("This  BookShop ID is Already Exist."));
					RequestContext reqcontextt = RequestContext.getCurrentInstance();
					reqcontextt.execute("PF('staffblockUI').hide();");
					reqcontextt.execute("PF('staffRegDialog').hide();");
					setBoxflag(false);
				} else {

					RequestContext reqcontext = RequestContext.getCurrentInstance();
					reqcontext.execute("PF('staffblockUI').hide();");
					reqcontext.execute("PF('staffRegDialog').hide();");
					setBoxflag(false);
				}

			}

		} catch (Exception e) {
			logger.warn(" exception - "+e);
			logger.warn("Inside Exception",e);
		}
		return "";

	}
	public String insertStaff2() {
		SmsController controller = null;
		String fieldname;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			setBoxflag(false);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			/*
			 * String
			 * idcheck=controller.staffidcheck(staffDataBean,staffDataBean.
			 * getStaStaffID());
			 */ if (personID != null) {
				
				 String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
				 String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
				
				 if (staffDataBean.getStaFile()!=null) {
					
				
				 System.out.println(staffDataBean.getStaFile().getContentType());
				String s = staffDataBean.getStaFile().getContentType();
				logger.debug("file Type " + s);
				String type = s.substring(s.lastIndexOf(paths.getString("path_context").toString()) + 1);
				logger.debug(type);

				copyFile2(staffDataBean.getStaStaffID(), staffDataBean.getStaFile().getInputstream(), type);
				logger.debug(staffDataBean.getStaStaffID() + "." + type);
				String path = ft.format(now) + paths.getString("path_context").toString() + staffDataBean.getStaStaffID() + "." + type;
				staffDataBean.setStafilePath(path);
				 }else{
					 staffDataBean.setStafilePath("");
				 }
				/*if(staffDataBean.getStaPhoneNo().startsWith(text.getString("sms.phno"))){
					staffDataBean.setStaPhoneNo(staffDataBean.getStaPhoneNo());
				}else if(staffDataBean.getStaPhoneNo().startsWith("0")){
					String phoneno=staffDataBean.getStaPhoneNo().substring(1);
					staffDataBean.setStaPhoneNo(text.getString("sms.phno")+phoneno);
				}else{
					staffDataBean.setStaPhoneNo(text.getString("sms.phno")+staffDataBean.getStaPhoneNo());
				}*/
				String status = controller.insertStaff2(personID, staffDataBean);
				System.out.println("pass control"+personID);
				System.out.println("check status--" + status);
				if (status.equalsIgnoreCase("Success")) {
					//String pdfStatus = generatePdfstaff(staffDataBean);
					MailSendJDBC.staffInsert(staffDataBean,schoolName,schoolID);
					/*String emailStatus = sendEmailstaff(staffDataBean, ImageListPath);
					if (pdfStatus.equalsIgnoreCase("Success") && emailStatus.equalsIgnoreCase("Success")) {*/
				//if (pdfStatus.equalsIgnoreCase("Success")) {
						//staffList = null;
						staffDataBean.setStaStaffID("");
						staffDataBean.setStaFirstName("");
						staffDataBean.setStaLastName("");
						staffDataBean.setStaGender("");
						staffDataBean.setStaEmail("");
						staffDataBean.setStaPhoneNo("");
						staffDataBean.setStaStaffPhoto("");
						staffDataBean.setStaffImageList(null);
						staffDataBean.setStaffList(null);
						staffDataBean.setStaSecurePasword("");
						staffDataBean.setStaUsername("");
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('staffblockUI').hide();");
						reqcontext.execute("PF('staffRegDialog').show();");
						setBoxflag(true);
						staffDataBean.setCountryCode("");
					//}

				} else   if (status.equalsIgnoreCase("Exsist")) {
					System.out.println("status eist");
					fieldname = CommonValidate.findComponentInRoot("staStaffID").getClientId(fc);
					fc.addMessage(fieldname, new FacesMessage("This  Staff ID is Already Exist."));
					RequestContext reqcontextt = RequestContext.getCurrentInstance();
					reqcontextt.execute("PF('staffblockUI').hide();");
					reqcontextt.execute("PF('staffRegDialog').show();");
					setBoxflag(false);
				} else {

					RequestContext reqcontext = RequestContext.getCurrentInstance();
					reqcontext.execute("PF('staffblockUI').hide();");
					reqcontext.execute("PF('staffRegDialog').show();");
					setBoxflag(false);
				}

			}

		} catch (Exception e) {
			logger.warn(" exception - "+e.getMessage());
			e.printStackTrace();
		}
		return "";

	}
	/*private String sendEmailstaff(StaffDataBean staffDataBean2, List<StaffDataBean> imageListPath2) throws AddressException {
		String status = "Fail";

		try {

			String mail = staffDataBean2.getStaEmail();
			String to = "" + mail;
			logger.debug("----mail----" + mail);
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
					+ "<h3>Dear " + staffDataBean2.getStaUsername() + ",</h3>"
					+ "<p>Welcome to join  our Student Monitoring System. you can find the following features  ."
					+ "you can check your  report, attendence,online payment,notice board etc."
					+ "also you can get our user name and password, from that you can check our website itself</p>"
					+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
					+ "<tr>" + "<td>User Name" + "</td>" + "<td>" + staffDataBean2.getStaUsername() + "</td>" + "</tr>"
					+ "<tr>" + "<td>Password" + "</td>" + "<td>" + staffDataBean2.getStaSecurePasword() + "</td>"
					+ "</tr>" + "</table>" + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>"
					+ "</center>" + "</footer>" + "</body></html>";

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
				message.setSubject("SMS Confirmation-" + staffDataBean2.getStaUsername());
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");

				MimeBodyPart messageBodyPart2 = new MimeBodyPart();
				String filename = paths.getString("sms.staff.pdf").toString() + ft.format(now) + paths.getString("path_context").toString() + staffDataBean2.getStaUsername()
						+ ".pdf";// change accordingly
				DataSource source = new FileDataSource(filename);
				messageBodyPart2.setDataHandler(new DataHandler(source));
				messageBodyPart2.setFileName(staffDataBean2.getStaUsername() + ".pdf");

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
	}*/

/*	private String generatePdfstaff(StaffDataBean staffDataBean2) {

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
				ImageListPath = new ArrayList<StaffDataBean>();
				logger.debug("parid-------" + staffDataBean2.getStaStaffID());
				ImageListPath = controller.getImagePathstaff3(personID, staffDataBean2.getStaStaffID());
				logger.debug("image path size----------" + ImageListPath.size());
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
					OutputStream file = new FileOutputStream( new File(files + paths.getString("path_context").toString() + staffDataBean2.getStaStaffID() + ".pdf"));
					staffDataBean.setStafilePath((files + paths.getString("path_context").toString() + staffDataBean2.getStaStaffID() + ".pdf"));
					logger.info("PDF Path -->"+staffDataBean.getStafilePath());
					Document document = new Document(PageSize.A4, 50, 50, 10, 50);
					PdfWriter.getInstance(document, file);
					document.open();
					String logo ="";
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						logo = paths.getString(schoolID+"_LOGO");
						document.add(Image.getInstance(getClass().getClassLoader().getResource(logo)));
					}else{
						logo = staffDataBean.getSchoolLogo();
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
					String imageLocation = paths.getString("sms.staff.photo").toString()+ tempdate + paths.getString("path_context").toString() + ImageListPath.get(0).getStafilePath();
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
	}*/
	
	
	public String parRegPage() {
		SmsController controller = null;
		String fieldname;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			setBoxflag(false);
			logger.debug("------parRegPage method calling--------");
			if (validate(true)) {
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");

				if (personID != null) {
					ApplicationContext ctx = FacesContextUtils
							.getWebApplicationContext(FacesContext.getCurrentInstance());
					controller = (SmsController) ctx.getBean("controller");
					String idcheck = controller.staffidcheck(staffDataBean, staffDataBean.getStaStaffID());
					if (idcheck.equalsIgnoreCase("Avilable")) {
						RequestContext reqcontextt = RequestContext.getCurrentInstance();
						reqcontextt.execute("staffdBlk();");// calling
															// JavaScript
					} else if (idcheck.equalsIgnoreCase("Exsist")) {
						fieldname = CommonValidate.findComponentInRoot("staStaffID").getClientId(fc);
						fc.addMessage(fieldname, new FacesMessage("This  BookShop ID is Already Exist."));
					}

				}
			}

		} catch (Exception e) {
			logger.debug("-----parRegPage method exception calling-------");
			logger.warn("Exception -->"+e.getMessage());
			return "";

		}
		return "";
	}
	
	
	public String staffRegPage() {
		SmsController controller = null;
		String fieldname;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			setBoxflag(false);
			logger.debug("------parRegPage method calling--------");
			if (validate(true)) {
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");

				if (personID != null) {
					ApplicationContext ctx = FacesContextUtils
							.getWebApplicationContext(FacesContext.getCurrentInstance());
					controller = (SmsController) ctx.getBean("controller");
					String idcheck = controller.staffidcheck(staffDataBean, staffDataBean.getStaStaffID());
					if (idcheck.equalsIgnoreCase("Avilable")) {
						RequestContext reqcontextt = RequestContext.getCurrentInstance();
						reqcontextt.execute("staffdBlk();");// calling
															// JavaScript
					} else if (idcheck.equalsIgnoreCase("Exsist")) {
						fieldname = CommonValidate.findComponentInRoot("staStaffID").getClientId(fc);
						fc.addMessage(fieldname, new FacesMessage("This  Staff ID is Already Exist."));
					}

				}
			}

		} catch (Exception e) {
			logger.debug("-----parRegPage method exception calling-------");
			logger.warn("Exception -->"+e.getMessage());
			return "";

		}
		return "";
	}

	/*private String generatePdf(StaffDataBean staffDataBean2) {
		String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		int schoolid=Integer.parseInt(schoolID);
		String status = "fail";
		SmsController controller = null;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				ImageListPath = new ArrayList<StaffDataBean>();
				logger.debug("parid-------" + staffDataBean2.getStaStaffID());
				ImageListPath = controller.getImagePath3(personID, staffDataBean2.getStaStaffID());
				logger.debug("image path size----------" + ImageListPath.size());
				logger.debug("======jackkkk======" + ImageListPath.size());
				if (ImageListPath.size() > 0) {
					// Create Directory
					File files = new File(paths.getString("sms.bookshop.pdf").toString() + ft.format(now));
					if (!files.exists()) {
						files.mkdirs();
					} else {

						logger.debug("Alreday Found");
					}
					// Write PDF File
					OutputStream file = new FileOutputStream(
							new File(files + paths.getString("path_context").toString() + staffDataBean2.getStaStaffID() + ".pdf"));
					staffDataBean.setStafilePath(files + paths.getString("path_context").toString() + staffDataBean2.getStaStaffID() + ".pdf");
					Document document = new Document(PageSize.A4, 50, 50, 10, 50);
					PdfWriter.getInstance(document, file);
					document.open();
					String logo ="";
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						logo = paths.getString(schoolID+"_LOGO");
						document.add(Image.getInstance(getClass().getClassLoader().getResource(logo)));
					}else{
						logo = staffDataBean.getSchoolLogo();
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
					PdfPCell nesCell5 = new PdfPCell(new Paragraph("Bookshop Roll Number:"));
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
					String imageLocation = paths.getString("sms.bookshop.photo").toString() + tempdate + paths.getString("path_context").toString() + ImageListPath.get(0).getStafilePath();
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
			}
		} catch (Exception e) {
			logger.warn(" exception - "+e);
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
		return status;
	}
*/
	
	private void copyFile(String fileName, InputStream inputstream, String n) {
		try {

			// Create Directory
			File files = new File(paths.getString("sms.bookshop.photo").toString() + ft.format(now));
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
	private void copyFile2(String fileName, InputStream inputstream, String n) {
		try {

			// Create Directory
			File files = new File(paths.getString("sms.staff.photo").toString() + ft.format(now));
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
		staffDataBean.setStaFile(event.getFile());
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);
		logger.debug(staffDataBean.getStaFile().getContentType());

		return "";
	}

	public String returnToHome() {

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

	/*private String sendEmail(StaffDataBean staffDataBean2, List<StaffDataBean> imageListPath2) throws AddressException {
		String status = "Fail";

		try {

			String mail = staffDataBean2.getStaEmail();
			String to = "" + mail;
			logger.debug("----ragulan----" + mail);
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
					+ "<h3>Dear " + staffDataBean2.getStaUsername() + ",</h3>"
					+ "<p>Welcome to join  our Student Monitoring System. you can find the following features  ."
					+ "you can check your  report, attendence,online payment,notice board etc."
					+ "also you can get our user name and password, from that you can check our website itself</p>"
					+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
					+ "<tr>" + "<td>User Name" + "</td>" + "<td>" + staffDataBean2.getStaUsername() + "</td>" + "</tr>"
					+ "<tr>" + "<td>Password" + "</td>" + "<td>" + staffDataBean2.getStaSecurePasword() + "</td>"
					+ "</tr>" + "</table>" + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>"
					+ "</center>" + "</footer>" + "</body></html>";

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
				message.setSubject("SMS Confirmation-" + staffDataBean2.getStaUsername());
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");

				MimeBodyPart messageBodyPart2 = new MimeBodyPart();
				String filename = paths.getString("sms.bookshop.pdf")+ ft.format(now) + paths.getString("path_context").toString() + staffDataBean2.getStaUsername()
						+ ".pdf";// change accordingly
				DataSource source = new FileDataSource(filename);
				messageBodyPart2.setDataHandler(new DataHandler(source));
				messageBodyPart2.setFileName(staffDataBean2.getStaUsername() + ".pdf");

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
			logger.warn(" exception - "+e);
			logger.warn("Inside Exception",e);
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}*/
/*
	private String sendEmail1(StaffDataBean staffDataBean2, List<StaffDataBean> imageListPath2)
			throws AddressException {
		String status = "Fail";

		try {

			String mail = staffDataBean2.getStaEmail();
			String to = mail;
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
				
				 * session = Session.getInstance(prop, this);// this is the
				 * local variable not the instance one
				  MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("SMS Confirmation-" + staffDataBean2.getStaStaffID());
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");

				MimeBodyPart messageBodyPart2 = new MimeBodyPart();
				String filename = paths.getString("sms.bookshop.pdf").toString() + ft.format(now) + paths.getString("path_context").toString() + staffDataBean2.getStaStaffID()
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
	}*/
	public String staffview() {
		SmsController controller = null;
		staffList = null;
		boxflag1 = false;
		staffDataBean.setRollcheck("Staff");
		try {
			staffList = new ArrayList<StaffDataBean>();

			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				staffList = controller.staffinfomation(personID, staffDataBean);
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return "editPageSuccess";
	}
	public String bookshopview() {
		SmsController controller = null;
		staffList = null;
		boxflag1 = false;
		
		staffDataBean.setRollcheck("BookShop");
		try {
			staffList = new ArrayList<StaffDataBean>();

			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				staffList = controller.staffinfomation(personID, staffDataBean);
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return "editPageBookshopSuccess";
	}

	public void imageview(OutputStream out, Object data) throws IOException {
		logger.debug("-----image view--------");
		String s = paths.getString("sms.bookshop.photo").toString();
		BufferedImage img = ImageIO
				.read(new File(s + ft.format(staffDataBean.getStaPathDate()) + paths.getString("path_context").toString() + staffDataBean.getStafilePath()));
		ImageIO.write(img, "png", out);
	}
	public void imageviewstaff(OutputStream out, Object data) throws IOException {
		logger.debug("-----image view--------");
		String s = paths.getString("sms.staff.photo").toString();
		BufferedImage img = ImageIO
				.read(new File(s + ft.format(staffDataBean.getStaPathDate()) + paths.getString("path_context").toString() + staffDataBean.getStafilePath()));
		ImageIO.write(img, "png", out);
	}
	public void staffTagview(StaffDataBean databean) {
		SmsController controller = null;
		logger.debug("Get Values" + databean.getStaStaffID());
		stafftaglist = null;
		delboxflag = false;
		staffDataBean.setStafilePath("");
		staffDataBean.setStaPathDate(null);
		try {
			stafftaglist = new ArrayList<StaffDataBean>();
			if (databean.getStaStaffID() != null) {
				staffDataBean.setStaStaffID(databean.getStaStaffID());
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				if (personID != null) {
					stafftaglist = controller.tagstaffview(personID, staffDataBean);
					logger.debug("------" + stafftaglist.size());
					try {
						if (stafftaglist.size() > 0) {
							if (stafftaglist.get(0).getStaffList().size() > 0) {
								staffDataBean.setStaStaffID(stafftaglist.get(0).getStaffList().get(0).getRollNumber());
								staffDataBean.setName(stafftaglist.get(0).getStaffList().get(0).getFirstName() + " "
										+ stafftaglist.get(0).getStaffList().get(0).getLastName());
								staffDataBean.setStaGender(stafftaglist.get(0).getStaffList().get(0).getGender());
								if(!"".equalsIgnoreCase(stafftaglist.get(0).getStaffList().get(0).getCountryCode()) || stafftaglist.get(0).getStaffList().get(0).getCountryCode()!=null){
									String code=stafftaglist.get(0).getStaffList().get(0).getCountryCode();
									staffDataBean.setStaPhoneNo(code+stafftaglist.get(0).getStaffList().get(0).getPhoneNumber());
								}else{
									staffDataBean.setStaPhoneNo(stafftaglist.get(0).getStaffList().get(0).getPhoneNumber());
								}
								staffDataBean.setStaEmail(stafftaglist.get(0).getStaffList().get(0).getEmailId());
							} else {
								staffDataBean.setStaStaffID("");
								staffDataBean.setName("");
								staffDataBean.setStaGender("");
								staffDataBean.setStaPhoneNo("");
								staffDataBean.setStaEmail("");
								staffDataBean.setCountryCode("");
							}

							if (stafftaglist.get(0).getImagepath().size() > 0) {
								staffDataBean.setStafilePath(stafftaglist.get(0).getImagepath().get(0).getPath());
								staffDataBean.setStaPathDate(stafftaglist.get(0).getImagepath().get(0).getDate());
							} else {
								staffDataBean.setStafilePath("");
								staffDataBean.setStaPathDate(null);
							}
						}
					} catch (NullPointerException n) {
						staffDataBean.setStaPhoneNo(stafftaglist.get(0).getStaffList().get(0).getPhoneNumber());
						n.printStackTrace();
					}
				}

			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
	}

	public void staffView(StaffDataBean databean) {
		SmsController controller = null;
		logger.debug("Get Values" + databean.getStaStaffID());
		stafftaglist = null;
		delboxflag = false;
		staffDataBean.setStafilePath("");
		staffDataBean.setStaPathDate(null);
		try {
			stafftaglist = new ArrayList<StaffDataBean>();
			if (databean.getStaStaffID() != null) {
				staffDataBean.setStaStaffID(databean.getStaStaffID());
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				if (personID != null) {
					stafftaglist = controller.tagstaffview(personID, staffDataBean);
					logger.debug("------" + stafftaglist.size());
					try {
						if (stafftaglist.size() > 0) {
							if (stafftaglist.get(0).getStaffList().size() > 0) {
								staffDataBean.setStaStaffID(stafftaglist.get(0).getStaffList().get(0).getRollNumber());
								staffDataBean.setName(stafftaglist.get(0).getStaffList().get(0).getFirstName() + " "
										+ stafftaglist.get(0).getStaffList().get(0).getLastName());
								staffDataBean.setStaGender(stafftaglist.get(0).getStaffList().get(0).getGender());
								if(!"".equalsIgnoreCase(stafftaglist.get(0).getStaffList().get(0).getCountryCode()) || stafftaglist.get(0).getStaffList().get(0).getCountryCode()!=null){
									String code=stafftaglist.get(0).getStaffList().get(0).getCountryCode();
									staffDataBean.setStaPhoneNo(code+stafftaglist.get(0).getStaffList().get(0).getPhoneNumber());
								}else{
									staffDataBean.setStaPhoneNo(stafftaglist.get(0).getStaffList().get(0).getPhoneNumber());
								}
								staffDataBean.setStaEmail(stafftaglist.get(0).getStaffList().get(0).getEmailId());
								staffDataBean.setStaFirstName(stafftaglist.get(0).getStaffList().get(0).getFirstName());
								staffDataBean.setStaLastName(stafftaglist.get(0).getStaffList().get(0).getLastName());
							} else {
								staffDataBean.setStaStaffID("");
								staffDataBean.setName("");
								staffDataBean.setStaGender("");
								staffDataBean.setStaPhoneNo("");
								staffDataBean.setStaEmail("");
								staffDataBean.setStaFirstName("");
								staffDataBean.setStaLastName("");
								staffDataBean.setCountryCode("");
							}

							if (stafftaglist.get(0).getImagepath().size() > 0) {
								staffDataBean.setStafilePath(stafftaglist.get(0).getImagepath().get(0).getPath());
								staffDataBean.setStaPathDate(stafftaglist.get(0).getImagepath().get(0).getDate());
							} else {
								staffDataBean.setStafilePath("");
								staffDataBean.setStaPathDate(null);
							}
						}
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}

			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
	}
public String stafviews(){
	System.out.println("okkk inside view");
	SmsController controller = null;
	stafftaglist = null;
	delboxflag = false;
	try {
		stafftaglist = new ArrayList<StaffDataBean>();
		if (staffDataBean.getStaStaffID() != null) {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				stafftaglist = controller.tagstaffview(personID, staffDataBean);
				logger.debug("------" + stafftaglist.size());
				try {
					if (stafftaglist.size() > 0) {
						if (stafftaglist.get(0).getStaffList().size() > 0) {
							staffDataBean.setStaStaffID(stafftaglist.get(0).getStaffList().get(0).getRollNumber());
							staffDataBean.setName(stafftaglist.get(0).getStaffList().get(0).getFirstName() + " "
									+ stafftaglist.get(0).getStaffList().get(0).getLastName());
							staffDataBean.setStaGender(stafftaglist.get(0).getStaffList().get(0).getGender());
							if(!"".equalsIgnoreCase(stafftaglist.get(0).getStaffList().get(0).getCountryCode()) || stafftaglist.get(0).getStaffList().get(0).getCountryCode()!=null){
								String code=stafftaglist.get(0).getStaffList().get(0).getCountryCode();
								staffDataBean.setStaPhoneNo(code+stafftaglist.get(0).getStaffList().get(0).getPhoneNumber());
							}else{
								staffDataBean.setStaPhoneNo(stafftaglist.get(0).getStaffList().get(0).getPhoneNumber());
							}
							staffDataBean.setStaEmail(stafftaglist.get(0).getStaffList().get(0).getEmailId());
							staffDataBean.setStaFirstName(stafftaglist.get(0).getStaffList().get(0).getFirstName());
							staffDataBean.setStaLastName(stafftaglist.get(0).getStaffList().get(0).getLastName());
						} else {
							staffDataBean.setStaStaffID("");
							staffDataBean.setName("");
							staffDataBean.setStaGender("");
							staffDataBean.setStaPhoneNo("");
							staffDataBean.setStaEmail("");
							staffDataBean.setStaFirstName("");
							staffDataBean.setStaLastName("");
						}

						if (stafftaglist.get(0).getImagepath().size() > 0) {
							staffDataBean.setStafilePath(stafftaglist.get(0).getImagepath().get(0).getPath());
							staffDataBean.setStaPathDate(stafftaglist.get(0).getImagepath().get(0).getDate());
						} else {
							staffDataBean.setStafilePath("");
							staffDataBean.setStaPathDate(null);
						}
					}
				} catch (NullPointerException n) {
					staffDataBean.setStaPhoneNo(stafftaglist.get(0).getStaffList().get(0).getPhoneNumber());
					n.printStackTrace();
				}
			}

		}
	} catch (Exception e) {
		logger.warn("Inside Exception",e);
	}
	return"view";
}
	
	
	public String staffedit() {
		System.out.println("inside edit v iew");
		SmsController controller = null;
		stafftaglist = null;
		delboxflag = false;
		try {
			stafftaglist = new ArrayList<StaffDataBean>();
			if (staffDataBean.getStaStaffID() != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				if (personID != null) {
					stafftaglist = controller.tagstaffview(personID, staffDataBean);
					logger.debug("------" + stafftaglist.size());
					try {
						if (stafftaglist.size() > 0) {
							if (stafftaglist.get(0).getStaffList().size() > 0) {
								staffDataBean.setStaStaffID(stafftaglist.get(0).getStaffList().get(0).getRollNumber());
								staffDataBean.setName(stafftaglist.get(0).getStaffList().get(0).getFirstName() + " "
										+ stafftaglist.get(0).getStaffList().get(0).getLastName());
								staffDataBean.setStaGender(stafftaglist.get(0).getStaffList().get(0).getGender());
								staffDataBean.setStaPhoneNo(stafftaglist.get(0).getStaffList().get(0).getPhoneNumber());
								staffDataBean.setCountryCode(stafftaglist.get(0).getStaffList().get(0).getCountryCode());
								staffDataBean.setStaEmail(stafftaglist.get(0).getStaffList().get(0).getEmailId());
								staffDataBean.setStaFirstName(stafftaglist.get(0).getStaffList().get(0).getFirstName());
								staffDataBean.setStaLastName(stafftaglist.get(0).getStaffList().get(0).getLastName());
							} else {
								staffDataBean.setStaStaffID("");
								staffDataBean.setName("");
								staffDataBean.setStaGender("");
								staffDataBean.setStaPhoneNo("");
								staffDataBean.setStaEmail("");
								staffDataBean.setStaFirstName("");
								staffDataBean.setStaLastName("");
								staffDataBean.setCountryCode("");
							}

							if (stafftaglist.get(0).getImagepath().size() > 0) {
								staffDataBean.setStafilePath(stafftaglist.get(0).getImagepath().get(0).getPath());
								staffDataBean.setStaPathDate(stafftaglist.get(0).getImagepath().get(0).getDate());
							} else {
								staffDataBean.setStafilePath("");
								staffDataBean.setStaPathDate(null);
							}
						}
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}

			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return "editPagestaff";
	}

	public String bookshopedit() {
		SmsController controller = null;
		stafftaglist = null;
		delboxflag = false;
		try {
			stafftaglist = new ArrayList<StaffDataBean>();
			if (staffDataBean.getStaStaffID() != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				if (personID != null) {
					stafftaglist = controller.tagstaffview(personID, staffDataBean);
					logger.debug("------" + stafftaglist.size());
					try {
						if (stafftaglist.size() > 0) {
							if (stafftaglist.get(0).getStaffList().size() > 0) {
								staffDataBean.setStaStaffID(stafftaglist.get(0).getStaffList().get(0).getRollNumber());
								staffDataBean.setName(stafftaglist.get(0).getStaffList().get(0).getFirstName() + " "
										+ stafftaglist.get(0).getStaffList().get(0).getLastName());
								staffDataBean.setStaGender(stafftaglist.get(0).getStaffList().get(0).getGender());
								staffDataBean.setStaPhoneNo(stafftaglist.get(0).getStaffList().get(0).getPhoneNumber());
								staffDataBean.setStaEmail(stafftaglist.get(0).getStaffList().get(0).getEmailId());
								staffDataBean.setStaFirstName(stafftaglist.get(0).getStaffList().get(0).getFirstName());
								staffDataBean.setStaLastName(stafftaglist.get(0).getStaffList().get(0).getLastName());
								staffDataBean.setCountryCode(stafftaglist.get(0).getStaffList().get(0).getCountryCode());
							} else {
								staffDataBean.setStaStaffID("");
								staffDataBean.setName("");
								staffDataBean.setStaGender("");
								staffDataBean.setStaPhoneNo("");
								staffDataBean.setStaEmail("");
								staffDataBean.setStaFirstName("");
								staffDataBean.setStaLastName("");
								staffDataBean.setCountryCode("");
							}

							if (stafftaglist.get(0).getImagepath().size() > 0) {
								staffDataBean.setStafilePath(stafftaglist.get(0).getImagepath().get(0).getPath());
								logger.debug("chk"+stafftaglist.get(0).getImagepath().get(0).getPath());
								staffDataBean.setStaPathDate(stafftaglist.get(0).getImagepath().get(0).getDate());
							} else {
								staffDataBean.setStafilePath("");
								staffDataBean.setStaPathDate(null);
							}
						}
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}

			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return "editPagebookshop";
	}
	
	public void updateStaff() {
		boxflag1 = false;
		delboxflag = false;
		staffDataBean.setRollcheck("Staff");
		SmsController controller = null;
		try {
			if (staffDataBean.getStaStaffID() != null) {
				 String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
				 String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
				 int schoolid=Integer.parseInt(schoolID);
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				if (personID != null) {
					if (staffDataBean.getStaFile() != null) {
						String s = staffDataBean.getStaFile().getContentType();
						String type = s.substring(s.lastIndexOf(paths.getString("path_context").toString()) + 1);
						copyFile2(staffDataBean.getStaStaffID(), staffDataBean.getStaFile().getInputstream(), type);
						logger.debug(staffDataBean.getStaStaffID() + "." + type);
						String path = ft.format(now) + paths.getString("path_context").toString() + staffDataBean.getStaStaffID() + "." + type;
						staffDataBean.setStafilePath(path);
					} else {
						staffDataBean.setStafilePath(null);
					}
					/*if(staffDataBean.getStaPhoneNo().startsWith("0")){
						String phoneno=staffDataBean.getStaPhoneNo().substring(1);
						staffDataBean.setStaPhoneNo(text.getString("sms.phno")+phoneno);
					}else if(staffDataBean.getStaPhoneNo().startsWith(text.getString("sms.phno"))){
						staffDataBean.setStaPhoneNo(staffDataBean.getStaPhoneNo());
					}else{
						staffDataBean.setStaPhoneNo(text.getString("sms.phno")+staffDataBean.getStaPhoneNo());
					}*/
					String status = controller.updateStaff(personID, staffDataBean);
					if (status.equalsIgnoreCase("Success")) {
						//String pdfStatus = generatePdfstaff(staffDataBean);
						MailSendJDBC.staffUpdate(staffDataBean,schoolName,schoolid);
						//if (pdfStatus.equalsIgnoreCase("Success")){
						/*String emailStatus = sendEmail1staff(staffDataBean, ImageListPath);
						if (pdfStatus.equalsIgnoreCase("Success") && emailStatus.equalsIgnoreCase("Success")) {*/

							staffList = null;
							staffDataBean.setStaStaffID("");
							staffDataBean.setStaFirstName("");
							staffDataBean.setStaLastName("");
							staffDataBean.setStaGender("");
							staffDataBean.setStaEmail("");
							staffDataBean.setStaPhoneNo("");
							staffDataBean.setStaStaffPhoto("");
							staffDataBean.setStaffImageList(null);
							staffDataBean.setStaffList(null);
							staffDataBean.setStaSecurePasword("");
							staffDataBean.setStaUsername("");
							staffDataBean.setRollcheck("");
							RequestContext reqcontext = RequestContext.getCurrentInstance();
							reqcontext.execute("PF('staffeditblockUI').hide();");
							reqcontext.execute("PF('staffEditDialog').show();");
							setBoxflag1(true);

						/*} else {
							RequestContext reqcontext = RequestContext.getCurrentInstance();
							reqcontext.execute("PF('staffeditblockUI').hide();");
							reqcontext.execute("PF('staffEditDialog').hide();");
							setBoxflag1(true);
						}*/
					} else {
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('staffeditblockUI').hide();");
						reqcontext.execute("PF('staffEditDialog').hide();");
						setBoxflag1(true);
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
	}

	/*private String sendEmail1staff(StaffDataBean staffDataBean2, List<StaffDataBean> imageListPath2)
			throws AddressException {
		String status = "Fail";

		try {

			String mail = staffDataBean2.getStaEmail();
			String to = mail;
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
				
				 * session = Session.getInstance(prop, this);// this is the
				 * local variable not the instance one
				  MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("SMS Confirmation-" + staffDataBean2.getStaStaffID());
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");

				MimeBodyPart messageBodyPart2 = new MimeBodyPart();
				String filename = paths.getString("sms.staff.pdf").toString() + ft.format(now) + paths.getString("path_context").toString() + staffDataBean2.getStaStaffID()
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
	}*/

	public void updateBookshop() {
		boxflag1 = false;
		delboxflag = false;
		staffDataBean.setRollcheck("BookShop");
		SmsController controller = null;
		try {
			if (staffDataBean.getStaStaffID() != null) {
				String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
				String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
				int schoolid=Integer.parseInt(schoolID);
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				if (personID != null) {
					if (staffDataBean.getStaFile() != null) {
						String s = staffDataBean.getStaFile().getContentType();
						String type = s.substring(s.lastIndexOf(paths.getString("path_context").toString()) + 1);
						copyFile(staffDataBean.getStaStaffID(), staffDataBean.getStaFile().getInputstream(), type);
						logger.debug(staffDataBean.getStaStaffID() + "." + type);
						String path = ft.format(now) + paths.getString("path_context").toString() + staffDataBean.getStaStaffID() + "." + type;
						staffDataBean.setStafilePath(path);
					} else {
						staffDataBean.setStafilePath(null);
					}
					/*if(staffDataBean.getStaPhoneNo().startsWith("0")){
						String phoneno=staffDataBean.getStaPhoneNo().substring(1);
						staffDataBean.setStaPhoneNo(text.getString("sms.phno")+phoneno);
					}else if(staffDataBean.getStaPhoneNo().startsWith(text.getString("sms.phno"))){
						staffDataBean.setStaPhoneNo(staffDataBean.getStaPhoneNo());
					}else{
						staffDataBean.setStaPhoneNo(text.getString("sms.phno")+staffDataBean.getStaPhoneNo());
					}*/
					String status = controller.updateStaff(personID, staffDataBean);
					if (status.equalsIgnoreCase("Success")) {
						//String pdfStatus = generatePdf(staffDataBean);
						MailSendJDBC.bookShopUpdate(staffDataBean,schoolName,schoolid);
						/*String emailStatus = sendEmail1(staffDataBean, ImageListPath);
						if (pdfStatus.equalsIgnoreCase("Success") && emailStatus.equalsIgnoreCase("Success")) {*/
						//if (pdfStatus.equalsIgnoreCase("Success")){
							staffList = null;
							staffDataBean.setStaStaffID("");
							staffDataBean.setStaFirstName("");
							staffDataBean.setStaLastName("");
							staffDataBean.setStaGender("");
							staffDataBean.setStaEmail("");
							staffDataBean.setStaPhoneNo("");
							staffDataBean.setStaStaffPhoto("");
							staffDataBean.setStaffImageList(null);
							staffDataBean.setStaffList(null);
							staffDataBean.setStaSecurePasword("");
							staffDataBean.setStaUsername("");
							staffDataBean.setRollcheck("");
							RequestContext reqcontext = RequestContext.getCurrentInstance();
							reqcontext.execute("PF('staffeditblockUI').hide();");
							reqcontext.execute("PF('staffEditDialog').show();");
							setBoxflag1(true);

						/*} else {
							RequestContext reqcontext = RequestContext.getCurrentInstance();
							reqcontext.execute("PF('staffeditblockUI').hide();");
							reqcontext.execute("PF('staffEditDialog').hide();");
							setBoxflag1(true);
						}*/
					} else {
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('staffeditblockUI').hide();");
						reqcontext.execute("PF('staffEditDialog').hide();");
						setBoxflag1(true);
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
	}

	public void editPage() {
		boxflag1 = false;
		delboxflag = false;
		try {

			if (validate1(true)) {
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");

				if (personID != null) {
					RequestContext reqcontextt = RequestContext.getCurrentInstance();
					reqcontextt.execute("staffedBlk()");// calling JavaScript
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
	}
// This code is not correct way
	public void staffdelete() {
		SmsController controller = null;
		logger.debug("Get Values" + staffDataBean.getStaStaffID());
		delboxflag = false;
		try {
			stafftaglist = new ArrayList<StaffDataBean>();
			if (staffDataBean.getStaStaffID() != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				if (personID != null) {
					//String status = 
							controller.deleteStaff(personID, staffDataBean);
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception"+e.getMessage());
		}
	}

	public boolean validate1(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();

		if (StringUtils.isBlank(staffDataBean.getStaStaffID())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("staStaffID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.staffID")));
			}
			valid = false;
		}

		if (StringUtils.isEmpty(staffDataBean.getStaFirstName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("staFName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.fname")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(staffDataBean.getStaFirstName())) {
			if (staffDataBean.getStaFirstName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("staFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.vfname")));
				}
				valid = false;
			} else if (!CommonValidate.validateName(staffDataBean.getStaFirstName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("staFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.vfname")));
				}
				valid = false;
			}
		}
		/*if (StringUtils.isEmpty(staffDataBean.getStaLastName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("staLName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.lname")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(staffDataBean.getStaLastName())) {
			if (staffDataBean.getStaLastName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("staLName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.vlame")));
				}
				valid = false;
			} else if (!CommonValidate.validateName(staffDataBean.getStaLastName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("staLName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.vlame")));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(staffDataBean.getStaGender())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("staGender").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.gender")));
			}
			valid = false;

		}
		if (StringUtils.isEmpty(staffDataBean.getStaEmail())) {
			{
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("staE-mail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.email")));
				}
				valid = false;
			}
		} else if (!StringUtils.isEmpty(staffDataBean.getStaEmail())) {
			if (!CommonValidate.validateEmail(staffDataBean.getStaEmail())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("staE-mail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.vemail")));
				}
				valid = false;
			}
		}

		if (StringUtils.isEmpty(staffDataBean.getStaGender())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("staGender").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.gender")));
			}
			valid = false;

		}
		if (StringUtils.isEmpty(staffDataBean.getStaEmail())) {
			{
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("staE-mail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.email")));
				}
				valid = false;
			}
		}*/ if (!StringUtils.isEmpty(staffDataBean.getStaEmail())) {
			if (!CommonValidate.validateEmail(staffDataBean.getStaEmail())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("staE-mail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.vemail")));
				}
				valid = false;
			}
		}
		if (!StringUtils.isEmpty(staffDataBean.getCountryCode())) {
		if (StringUtils.isEmpty(staffDataBean.getStaPhoneNo())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("staPhone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.phno")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(staffDataBean.getStaPhoneNo())) {
			if(!CommonValidate.validateNumberOnly(staffDataBean.getStaPhoneNo())){
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("staPhone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumberinvalid")));
			}
			valid = false;
			}
			
			else if(!CommonValidate.countryCodeValidation(staffDataBean.getStaPhoneNo(),staffDataBean.getCountryCode())){
				fieldName = CommonValidate.findComponentInRoot("staPhone").getClientId(fc);
				if (staffDataBean.getCountryCode().equalsIgnoreCase("+91")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.india.phonevalidation")));
				}
				else if (staffDataBean.getCountryCode().equalsIgnoreCase("+971")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.uae.phonevalidation")));
				}
				else if (staffDataBean.getCountryCode().equalsIgnoreCase("+62")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.indonesia.phonevalidation")));
				}
				else if (staffDataBean.getCountryCode().equalsIgnoreCase("+60")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.malaysia.phonevalidation")));
				}
				
				valid = false;
			}
			
		}
	}
		/*if (StringUtils.isEmpty(staffDataBean.getStaPhoneNo())) {
			if (flag) {

				fieldName = CommonValidate.findComponentInRoot("staPhone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.phno")));
			}
			valid = false;
		}else if(!StringUtils.isEmpty(staffDataBean.getStaPhoneNo())){
			if(!CommonValidate.validateNumber(staffDataBean.getStaPhoneNo())){
				if (flag) {

					fieldName = CommonValidate.findComponentInRoot("staPhone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.vphno")));
				}
				valid = false;
			}else if(Integer.parseInt(staffDataBean.getStaPhoneNo()) <= 0){
				if (flag) {

					fieldName = CommonValidate.findComponentInRoot("staPhone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.vphno")));
				}
				valid = false;
			}
			else if(staffDataBean.getStaPhoneNo().length()<11){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("staPhone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.staff.vphno")));
				}
				valid = false;
			}	
		}*/
		/*
		 * if(staffDataBean.getStaFile()==null){ if(flag){
		 * 
		 * fieldName =
		 * CommonValidate.findComponentInRoot("staPhoto").getClientId(fc);
		 * fc.addMessage(fieldName, new
		 * FacesMessage(text.getString("sms.staff.photo"))); } valid = false; }
		 */
		if(!StringUtils.isEmpty(staffDataBean.getStaPhoneNo())){
			if(StringUtils.isEmpty(staffDataBean.getCountryCode())){
				fieldName = CommonValidate.findComponentInRoot("countrycode").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Countrycode"));
				valid = false;
			}
		}
		return valid;
	}
	
	public String delete(){
		if(staffDataBean.getRollcheck().equals("Staff")){
			staffview();
		}else{
			bookshopview();
		}
		return "";
	}
	
	public String returnToHomes(){
		  
		  return "staffview";
		 }

}