package com.stumosys.managedBean;

//import java.io.ByteArrayInputStream;
/*import java.io.File;
import java.io.FileOutputStream;
*///import java.io.InputStream;
/*import java.io.OutputStream;
import java.io.StringReader;
*/import java.text.SimpleDateFormat;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.util.SystemOutLogger;
import org.primefaces.context.RequestContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/*import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
*/import com.stumosys.controler.SmsController;
import com.stumosys.domain.NoticeBoardDataBean;
import com.stumosys.domain.ReportCardDataBean;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name = "noticeBoardMB")
@RequestScoped
public class NoticeBoardMB {

	NoticeBoardDataBean noticeBoardDataBean = new NoticeBoardDataBean();
	private List<NoticeBoardDataBean> noticeBoardList = null;
	private List<String> classList;
	private boolean boxflag = false;
	FacesContext context = FacesContext.getCurrentInstance();
	Date now = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	private static Logger logger = Logger.getLogger(NoticeBoardMB.class);
	private List<String> studentList=null;

	public List<String> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<String> studentList) {
		this.studentList = studentList;
	}

	/**
	 * @return the boxflag1
	 */
	public boolean isBoxflag1() {
		return boxflag1;
	}

	/**
	 * @param boxflag1
	 *            the boxflag1 to set
	 */
	public void setBoxflag1(boolean boxflag1) {
		this.boxflag1 = boxflag1;
	}

	private boolean boxflag1 = false;

	/**
	 * @return the noticeBoardDataBean
	 */
	public NoticeBoardDataBean getNoticeBoardDataBean() {
		return noticeBoardDataBean;
	}

	/**
	 * @param noticeBoardDataBean
	 *            the noticeBoardDataBean to set
	 */
	public void setNoticeBoardDataBean(NoticeBoardDataBean noticeBoardDataBean) {
		this.noticeBoardDataBean = noticeBoardDataBean;
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
	
	public List<String> getClassList() {
		return classList;
	}

	public void setClassList(List<String> classList) {
		this.classList = classList;
	}

	public String noticePageLoad() {
		logger.debug("-------noticePageLoad method Calling------");
		try {
			SmsController controller = null;
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			noticeBoardDataBean.setFromdate(null);
			noticeBoardDataBean.setTodate(null);
			noticeBoardDataBean.setNoticeSubject("");
			noticeBoardDataBean.setNoticeFollower("");
			noticeBoardDataBean.setNoticeClass("");
			noticeBoardDataBean.setNoticeID("");
			boxflag = false;
			boxflag1 = false;
			classList = controller.getClassSection(personID);
			 Collections.sort(classList);
			logger.debug(boxflag);
			studentList=null;
			noticeBoardDataBean.setStudentnameList(new ArrayList<String>());
		} catch (Exception e) {
			logger.debug("--------noticePageLoad method Exception Calling------");
			logger.warn("Exception -->"+e.getMessage());
		}
		return "noticeboard";

	}

	public boolean validate(boolean flag) {
		String fieldName;
		boolean valid = true;
		FacesContext fc = FacesContext.getCurrentInstance();
		// Senthiga Apr6,2016 beg

		if (noticeBoardDataBean.getFromdate() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("frmDate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the From Date."));
			}
			valid = false;
		}

		if (noticeBoardDataBean.getTodate() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("toDate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the To Date."));
			}
			valid = false;
		}

		if (StringUtils.isEmpty(noticeBoardDataBean.getNoticeSubject())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("noticeBoardSubject").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Notice Subject."));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(noticeBoardDataBean.getNoticeSubject())) {
			if (!CommonValidate.validateName(noticeBoardDataBean.getNoticeSubject())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("noticeBoardSubject").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Notice Subject."));
				}
				valid = false;
			} else if (noticeBoardDataBean.getNoticeSubject().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("noticeBoardSubject").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Notice Subject."));
				}
				valid = false;
			}
		}

		if (StringUtils.isEmpty(noticeBoardDataBean.getNoticeFollower())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("noticeBoardFollow").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Notice Follower."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(noticeBoardDataBean.getNoticeClass())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("noticeBoardClass").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Notice Follower Class."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(noticeBoardDataBean.getNoticeID())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("noticeBoardId").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Notice."));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(noticeBoardDataBean.getNoticeID())) {
			if (noticeBoardDataBean.getNoticeID().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("noticeBoardId").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Notice."));
				}
				valid = false;
			}
		}

		return valid;
	}

	public String submitConfirm() {
		SmsController controller = null;
		FacesContext fc = FacesContext.getCurrentInstance();
		String fieldName;
		String status = "Fail";
		List<String> emailIdList = null;List<String> phonenos = new ArrayList<String>();
		System.out.println("Notice message----------"+noticeBoardDataBean.getNoticeboardID());
		//What is this output coming 
		try {
			noticeBoardDataBean.setEmailList(new ArrayList<String>());
			noticeBoardDataBean.setPhonenos(new ArrayList<String>());
			String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
			emailIdList = new ArrayList<String>();
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");

			status = controller.insertNoticeBoard(personID, noticeBoardDataBean);
			logger.debug("------->>>>>><<<<<<>>>>>" + status);
			if (status.equalsIgnoreCase("Success")) {
				logger.debug("------->>>>>><<<<<<>>>>>inside if");
				logger.debug("class---"+noticeBoardDataBean.getNoticeClass());
					noticeBoardList = controller.getAllUserList(personID, noticeBoardDataBean);
					logger.debug("MB Size" + noticeBoardList.size());
					if (noticeBoardList.size() > 0) {
						if (noticeBoardList.get(0).getParentList() != null) {
							if (noticeBoardList.get(0).getParentList().size() > 0) {

								int listsize = noticeBoardList.get(0).getParentList().size();
								for (int i = 0; i < listsize; i++) {
									logger.debug(noticeBoardList.get(0).getParentList().get(i).getEmaiId());
									emailIdList.add(noticeBoardList.get(0).getParentList().get(i).getEmaiId());
									phonenos.add(noticeBoardList.get(0).getParentList().get(i).getCountrycode()+noticeBoardList.get(0).getParentList().get(i).getPhoneNumber());
								}
							}
						}
						if (noticeBoardList.get(0).getTeacherList() != null) {
							if (noticeBoardList.get(0).getTeacherList().size() > 0) {

								int listsize = noticeBoardList.get(0).getTeacherList().size();
								for (int i = 0; i < listsize; i++) {
									logger.debug(noticeBoardList.get(0).getTeacherList().get(i).getEmailId());
									emailIdList.add(noticeBoardList.get(0).getTeacherList().get(i).getEmailId());
									phonenos.add(noticeBoardList.get(0).getTeacherList().get(i).getPresentCode()+noticeBoardList.get(0).getTeacherList().get(i).getPhoneNumber());
									logger.debug("phonenumber-----------------"+noticeBoardList.get(0).getTeacherList().get(i).getPresentCode()+noticeBoardList.get(0).getTeacherList().get(i).getPhoneNumber());
								}
							}
						}
						if (noticeBoardList.get(0).getStaffList() != null) {
							if (noticeBoardList.get(0).getStaffList().size() > 0) {

								int listsize = noticeBoardList.get(0).getStaffList().size();
								for (int i = 0; i < listsize; i++) {
									logger.debug(noticeBoardList.get(0).getStaffList().get(i).getEmailId());
									emailIdList.add(noticeBoardList.get(0).getStaffList().get(i).getEmailId());
									phonenos.add(noticeBoardList.get(0).getStaffList().get(i).getCountryCode()+noticeBoardList.get(0).getStaffList().get(i).getPhoneNumber());
								}
							}
						}
					}
					logger.debug("Checking Purpose  1----------" + emailIdList.size());
					logger.debug("Checking Purpose  2----------" + phonenos.size());
					noticeBoardDataBean.setNoticeSubjects(noticeBoardDataBean.getNoticeSubject());
					noticeBoardDataBean.setEmailList(emailIdList);
					noticeBoardDataBean.setPhonenos(phonenos);
					Set<String> duplicate = new HashSet<String>();
					duplicate.addAll(noticeBoardDataBean.getEmailList());
					noticeBoardDataBean.getEmailList().clear();
					noticeBoardDataBean.getEmailList().addAll(duplicate);
					logger.debug("dublicate email-----------"+noticeBoardDataBean.getEmailList().size());
					Set<String> duplicatePhone = new HashSet<String>();
					duplicatePhone.addAll(noticeBoardDataBean.getPhonenos());
					noticeBoardDataBean.getPhonenos().clear();
					noticeBoardDataBean.getPhonenos().addAll(duplicatePhone);
					logger.debug("dublicate phone-----------"+noticeBoardDataBean.getPhonenos().size());
					if (noticeBoardDataBean.getPhonenos().size() > 0 || noticeBoardDataBean.getEmailList().size()>0) {
						MailSendJDBC.noticeBoardInsert(noticeBoardDataBean, schoolName,schoolid);
						/*String emailStatus = sendEmail(noticeBoardDataBean);
						logger.debug("email status -- " + emailStatus);
						if (emailStatus.equalsIgnoreCase("Success")) {*/
							setBoxflag(true);
							setBoxflag1(false);
							RequestContext reqcontext = RequestContext.getCurrentInstance();
							reqcontext.execute("PF('noticeblockUI').hide();");
							reqcontext.execute("PF('NoticeDialog').show();");
							reqcontext.execute("PF('NoticeAlertDialog').hide();");
							emailIdList = null;
							noticeBoardDataBean.setFromdate(null);
							noticeBoardDataBean.setTodate(null);
							noticeBoardDataBean.setNoticeSubject("");
							noticeBoardDataBean.setNoticeFollower("");
							noticeBoardDataBean.setNoticeID("");
							noticeBoardDataBean.setNoticeClass("");
						/*}*/
					} else {
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('noticeblockUI').hide();");
						reqcontext.execute("PF('NoticeDialog').hide();");
						reqcontext.execute("PF('NoticeAlertDialog').hide();");
					}
				
			} else if (status.equalsIgnoreCase("Invalid")) {
				setBoxflag1(true);
				setBoxflag(false);
				RequestContext reqcontext = RequestContext.getCurrentInstance();
				reqcontext.execute("PF('noticeblockUI').hide();");
				reqcontext.execute("PF('NoticeDialog').hide();");
				reqcontext.execute("PF('NoticeAlertDialog').show();");
			} else if (status.equalsIgnoreCase("exsist")) {
				logger.debug("Exsist");
				setBoxflag1(false);
				setBoxflag(false);
				RequestContext reqcontext = RequestContext.getCurrentInstance();
				reqcontext.execute("PF('noticeblockUI').hide();");
				reqcontext.execute("PF('NoticeDialog').hide();");
				reqcontext.execute("PF('NoticeAlertDialog').hide();");
				fieldName = CommonValidate.findComponentInRoot("noticeBoardSubject").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("This Subject Already Exists."));
			} else {
				logger.debug("Try Again..");
				setBoxflag(false);
				setBoxflag1(false);
				RequestContext reqcontext = RequestContext.getCurrentInstance();
				reqcontext.execute("PF('noticeblockUI').hide();");
				reqcontext.execute("PF('NoticeDialog').hide();");
				reqcontext.execute("PF('NoticeAlertDialog').hide();");
			}
			logger.debug("subject -- " + noticeBoardDataBean.getNoticeSubject());

		} catch (Exception e) {
			logger.debug("------submit method exception calling------");
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
		return "";

	}

	public String submit() {
		logger.debug("------submit method calling------");
		System.out.println("UI Message"+noticeBoardDataBean.getNoticeboardID());
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
		if (validate(true)) {
			setBoxflag(false);
			setBoxflag1(false);
			if (personID != null) {
				logger.debug("Test");
				RequestContext rq = RequestContext.getCurrentInstance();
				rq.execute("noticeblk();");
			}
		}
		return "";

	}

	/*private String createPDF(NoticeBoardDataBean noticeBoardDataBean2) {
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("schoolID");
		int schoolid=Integer.parseInt(schoolID);
		StringBuilder status = new StringBuilder("failpdf");
		try {

			// Create Directory
			File files = new File(paths.getString("sms.notice.pdf") + ft.format(now));
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
			document.open();
			String logo ="";
			if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				logo = paths.getString(schoolID+"_LOGO");
				document.add(Image.getInstance(getClass().getClassLoader().getResource(logo)));
			}else{
				logo = noticeBoardDataBean.getSchoolLogo();
				document.add(Image.getInstance(logo));
			}
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
			
			 * String logo="C://Photos/Header/nrg.png";
			 * document.add(Image.getInstance(logo)); document.add(new
			 * Paragraph(
			 * "------------------------------------------------------------------------------------------------------------------------"
			 * ));
			 
			document.close();
			status = new StringBuilder("createdpdf");
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return status.toString();
	}*/

	public String reset() {
		noticeBoardDataBean.setFromdate(null);
		noticeBoardDataBean.setTodate(null);
		noticeBoardDataBean.setNoticeSubject("");
		noticeBoardDataBean.setNoticeFollower("");
		noticeBoardDataBean.setNoticeID("");
		noticeBoardDataBean.setNoticeClass("");
		return "";
	}

	public String returnToHome() {

		logger.debug("Calling");
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				noticeBoardDataBean.setFromdate(null);
				noticeBoardDataBean.setTodate(null);
				noticeBoardDataBean.setNoticeSubject("");
				noticeBoardDataBean.setNoticeFollower("");
				noticeBoardDataBean.setNoticeID("");
				noticeBoardDataBean.setNoticeClass("");
				setBoxflag(false);
				setBoxflag1(false);
				return "SuccessHome";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";

	}

/*	private String sendEmail(NoticeBoardDataBean noticeBoardDataBean) {
		StringBuilder status = new StringBuilder("Fail");

		try {
			InternetAddress[] toAddress = new InternetAddress[noticeBoardDataBean.getEmailList().size()];
			for (int i = 0; i < noticeBoardDataBean.getEmailList().size(); i++) {
				toAddress[i] = new InternetAddress(noticeBoardDataBean.getEmailList().get(i));
				logger.debug("mail id -- " + noticeBoardDataBean.getEmailList().get(i));
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
					+ "<h1> Student Monitoring System Notice Board</h1><br></br></center>" + "</center>" + "</header>"
					+ "<h3>Dear " + noticeBoardDataBean.getNoticeFollower() + ",</h3>"
					+"<br></br>"
					+noticeBoardDataBean.getNoticeID()
					+ "<footer>" + "<center>"
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
				
				 * message.addRecipients(Message.RecipientType.CC, myCcList);
				  message.setSubject(noticeBoardDataBean.getNoticeSubject());
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
*/
	/**
	 * @return the noticeBoardList
	 */
	public List<NoticeBoardDataBean> getNoticeBoardList() {
		return noticeBoardList;
	}

	/**
	 * @param noticeBoardList
	 *            the noticeBoardList to set
	 */
	public void setNoticeBoardList(List<NoticeBoardDataBean> noticeBoardList) {
		this.noticeBoardList = noticeBoardList;
	}

	public void classChange(){
		String schoolID="",personID="";
		SmsController controller=null;
		ApplicationContext context=null;
		ReportCardDataBean reportCardDataBean=null;
		try{
			schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			personID=	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID").toString();
			context=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(SmsController) context.getBean("controller");
			studentList=new ArrayList<String>();
			if(schoolID.equalsIgnoreCase(paths.getString("SCMS.SCHOOLID"))){
				if(!"".equalsIgnoreCase(noticeBoardDataBean.getNoticeClass()) && !"Internal".equalsIgnoreCase(noticeBoardDataBean.getNoticeFollower())){
					reportCardDataBean=new ReportCardDataBean();
					reportCardDataBean.setStudMarkClass(noticeBoardDataBean.getNoticeClass());
					studentList=controller.getStudentIDList(reportCardDataBean,personID);
				}
			}
		}catch(Exception e){
			logger.warn("Exception message--------------------"+ e.getMessage());
		}finally{
			context=null;
			reportCardDataBean=null;
			controller=null;
		}
	}
}
