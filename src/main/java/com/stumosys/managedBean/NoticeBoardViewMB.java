package com.stumosys.managedBean;

//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileOutputStream;
/*import java.io.IOException;
import java.io.InputStream;
*/
//import java.io.OutputStream;
//import java.io.StringReader;
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
import javax.faces.bean.RequestScoped;
//import javax.faces.context.ExternalContext;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
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
//import com.stumosys.domain.TeacherDataBean;
import com.stumosys.shared.Noticeboard;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name = "noticeBoardViewMB")
@RequestScoped
public class NoticeBoardViewMB {

	NoticeBoardDataBean noticeBoardDataBean = new NoticeBoardDataBean();

	private List<NoticeBoardDataBean> noticeList = null;
	private List<NoticeBoardDataBean> filteredNotice;
	private boolean delBoxflag = false;
	private List<Noticeboard> noticeListView = null;
	private boolean hideFlag = false;
	private boolean boxflag = false;
	private static Logger logger = Logger.getLogger(NoticeBoardViewMB.class);
	private List<NoticeBoardDataBean> noticeBoardList = null;
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	Date now = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	private List<String> classList;
	private List<String> studentList=null;

	public List<String> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<String> studentList) {
		this.studentList = studentList;
	}
	
	public List<String> getClassList() {
		return classList;
	}

	public void setClassList(List<String> classList) {
		this.classList = classList;
	}

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

	/**
	 * @return the noticeList
	 */
	public List<NoticeBoardDataBean> getNoticeList() {
		return noticeList;
	}

	/**
	 * @param noticeList
	 *            the noticeList to set
	 */
	public void setNoticeList(List<NoticeBoardDataBean> noticeList) {
		this.noticeList = noticeList;
	}

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
	 * @return the filteredNotice
	 */
	public List<NoticeBoardDataBean> getFilteredNotice() {
		return filteredNotice;
	}

	/**
	 * @param filteredNotice
	 *            the filteredNotice to set
	 */
	public void setFilteredNotice(List<NoticeBoardDataBean> filteredNotice) {
		this.filteredNotice = filteredNotice;
	}

	public String noticeBoardPageLoad() {
		SmsController controller = null;
		setDelBoxflag(false);
		setBoxflag(false);
		try {

			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String role_user = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("Role");
			noticeList = new ArrayList<NoticeBoardDataBean>();
			if (personID != null) {
				noticeList = controller.getNoticeBoardView(personID);
				if (role_user.equalsIgnoreCase("Admin")) {
					setHideFlag(true);
				} else {
					setHideFlag(false);
				}
				return "noticeBoardView";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";

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

	/**
	 * @return the noticeListView
	 */
	public List<Noticeboard> getNoticeListView() {
		return noticeListView;
	}

	/**
	 * @param noticeListView
	 *            the noticeListView to set
	 */
	public void setNoticeListView(List<Noticeboard> noticeListView) {
		this.noticeListView = noticeListView;
	}

	/**
	 * @return the hideFlag
	 */
	public boolean isHideFlag() {
		return hideFlag;
	}

	/**
	 * @param hideFlag
	 *            the hideFlag to set
	 */
	public void setHideFlag(boolean hideFlag) {
		this.hideFlag = hideFlag;
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

	public String noticeView() {
		SmsController controller = null;
		setDelBoxflag(false);
		try {
			logger.debug("Inside Notice-----");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String role_user = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("Role");
			logger.debug("Role" + role_user);
			noticeList = new ArrayList<NoticeBoardDataBean>();
			if (personID != null) {
				noticeList = controller.getNoticeBoardView(personID);
				if (role_user.equalsIgnoreCase("Admin")) {
					setHideFlag(true);
					setBoxflag(false);
				} else {
					setHideFlag(false);
					setBoxflag(false);
				}
				return "noticeBoardView";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";

	}

	public String edit() {
		SmsController controller = null;
		String schoolID="";
		try {
			classList=new ArrayList<String>();
			studentList=new ArrayList<String>();
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String role_user = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("Role");
			schoolID=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID").toString();
			noticeListView = new ArrayList<Noticeboard>();
			if (personID != null) {
				if (noticeBoardDataBean.getNoticeSubject() != null && noticeBoardDataBean.getNoticeFollower() != null) {
					ApplicationContext ctx = FacesContextUtils
							.getWebApplicationContext(FacesContext.getCurrentInstance());
					controller = (SmsController) ctx.getBean("controller");
					noticeListView = controller.getNoticeBoardViewBYSubject(personID, noticeBoardDataBean, role_user);
					if (noticeListView.size() > 0) {
						noticeBoardDataBean.setNoticeFollower(noticeListView.get(0).getNoticeFollower());
						noticeBoardDataBean.setNoticeFollowers(noticeListView.get(0).getNoticeFollower());
						noticeBoardDataBean.setNoticeSubject(noticeListView.get(0).getSubject());
						noticeBoardDataBean.setNoticeSubjects(noticeListView.get(0).getSubject());
						noticeBoardDataBean.setNoticeID(noticeListView.get(0).getMessage());
						noticeBoardDataBean.setFromdate(noticeListView.get(0).getFromDate());
						noticeBoardDataBean.setTodate(noticeListView.get(0).getToDate());
						noticeBoardDataBean.setNoticeboardID(noticeListView.get(0).getNoticeBoard_ID());
						noticeBoardDataBean.setNoticeClass(noticeListView.get(0).getNoticeClass());
						classList = controller.getClassSection(personID);
						Collections.sort(classList);
						if(schoolID.equalsIgnoreCase(paths.getString("SCMS.SCHOOLID"))){
							ReportCardDataBean reportCardDataBean=new ReportCardDataBean();
							reportCardDataBean.setStudMarkClass(noticeBoardDataBean.getNoticeClass());
							studentList=controller.getStudentIDList(reportCardDataBean,personID);
							System.out.println("student----------"+studentList.size());
						}
					} else {
						noticeBoardDataBean.setNoticeFollower("");
						noticeBoardDataBean.setNoticeFollowers("");
						noticeBoardDataBean.setNoticeSubject("");
						noticeBoardDataBean.setNoticeID("");
						noticeBoardDataBean.setFromdate(null);
						noticeBoardDataBean.setTodate(null);
						noticeBoardDataBean.setNoticeboardID(0);
						noticeBoardDataBean.setNoticeClass("");
					}
					setBoxflag(false);
					return "NoticeBoardPageEdit";
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";

	}

	public String updateConfirm() {
		SmsController controller = null;
		List<String> emailIdList = null;List<String> phonenos =null;
		try {
			noticeBoardDataBean.setEmailList(new ArrayList<String>());
			noticeBoardDataBean.setPhonenos(new ArrayList<String>());
			String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			emailIdList = new ArrayList<String>();
			phonenos = new ArrayList<String>();
			if (validate(true)) {
				if (personID != null) {
					ApplicationContext ctx = FacesContextUtils
							.getWebApplicationContext(FacesContext.getCurrentInstance());
					controller = (SmsController) ctx.getBean("controller");
					String status = controller.updateNotice(personID, noticeBoardDataBean);
					logger.debug(status);
					if (status.equalsIgnoreCase("Success")){
							noticeBoardList = controller.getAllUserList(personID,noticeBoardDataBean);
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
											emailIdList
													.add(noticeBoardList.get(0).getTeacherList().get(i).getEmailId());
											phonenos.add(noticeBoardList.get(0).getTeacherList().get(i).getPresentCode()+noticeBoardList.get(0).getTeacherList().get(i).getPhoneNumber());
										}
									}
								}
								if(schoolid == 1|| schoolid ==2 || schoolid == 3 || schoolid == 4 || schoolid == 5 || schoolid == 6){
								}else{
									if (noticeBoardList.get(0).getStudentList() != null) {
										if (noticeBoardList.get(0).getStudentList().size() > 0) {
										int listsize = noticeBoardList.get(0).getStudentList().size();
											for (int i = 0; i < listsize; i++) {
												logger.debug(noticeBoardList.get(0).getStudentList().get(i).getEmailId());
												emailIdList.add(noticeBoardList.get(0).getStudentList().get(i).getEmailId());
											}
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
							logger.debug("Checking Purpose  " + emailIdList.size());
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
								MailSendJDBC.noticeBoardUpdate(noticeBoardDataBean, schoolName,schoolid);
								/*String emailStatus = sendEmail(noticeBoardDataBean, emailIdList);
								if (emailStatus.equalsIgnoreCase("Success")) {*/
									setBoxflag(true);

									noticeBoardDataBean.setFromdate(null);
									noticeBoardDataBean.setTodate(null);
									noticeBoardDataBean.setNoticeSubject("");
									noticeBoardDataBean.setNoticeFollower("");
									noticeBoardDataBean.setNoticeID("");
									emailIdList = null;
									RequestContext reqcontext = RequestContext.getCurrentInstance();
									reqcontext.execute("PF('noticeeditblockUI').hide();");
									reqcontext.execute("PF('NoticeDialog').show();");
								/*} else {
									RequestContext reqcontext = RequestContext.getCurrentInstance();
									reqcontext.execute("PF('noticeeditblockUI').hide();");
									reqcontext.execute("PF('NoticeDialog').hide();");
								}*/
							}
						
					} else {
						setBoxflag(false);
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('noticeeditblockUI').hide();");
						reqcontext.execute("PF('NoticeDialog').hide();");
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
		return "";
	}

	public String update() {

		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");

			if (validate(true)) {
				if (personID != null) {
					RequestContext rq = RequestContext.getCurrentInstance();
					rq.execute("noticedidtblk();");
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}

	/*private String createPDF(NoticeBoardDataBean noticeBoardDataBean2) {
		StringBuilder status = new StringBuilder("failpdf");
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("schoolID");
		int schoolid=Integer.parseInt(schoolID);
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
			pdfWriter.setViewerPreferences(PdfWriter.HideToolbar);
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

	/*private String sendEmail(NoticeBoardDataBean noticeBoardDataBean, List<String> emailIdList) {
		StringBuilder status = new StringBuilder("Fail");

		try {
			InternetAddress[] toAddress = new InternetAddress[emailIdList.size()];
			
			 * InternetAddress[] myCcList =InternetAddress.parse(
			 * "	abritto.john@gmail.com,sing1alex@gmail.com,alexdubai9@gmail.com"
			 * );
			  for (int i = 0; i < emailIdList.size(); i++) {
				toAddress[i] = new InternetAddress(emailIdList.get(i));
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
				  message.setSubject("Updated "+ noticeBoardDataBean.getNoticeSubject());
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");

			
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
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
	}*/

	public String delete() {
		SmsController controller = null;
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				logger.debug(noticeBoardDataBean.getNoticeFollower());
				String status = controller.deleteNotice(personID, noticeBoardDataBean);
				
				logger.debug(status);
				if (status.equalsIgnoreCase("Success")) {
					setDelBoxflag(true);
				} else {
					setDelBoxflag(false);
				}

			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
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

	public boolean validate(boolean flag) {
		String fieldName;
		boolean valid = true;
		FacesContext fc = FacesContext.getCurrentInstance();
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
				fc.addMessage(fieldName, new FacesMessage("Please Select the Notice Class."));
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

	public String noticeView1() {
		SmsController controller = null;
		setDelBoxflag(false);
		try {
			logger.debug("Inside Notice-----");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String role_user = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("Role");
			logger.debug("Role" + role_user);
			noticeList = new ArrayList<NoticeBoardDataBean>();
			if (personID != null) {
				noticeList = controller.getNoticeBoardView(personID);
				if (role_user.equalsIgnoreCase("Admin")) {
					setHideFlag(true);
					setBoxflag(false);
				} else {
					setHideFlag(false);
					setBoxflag(false);
				}
				return "noticeBoardView";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";

	}

	/*
	 * public void preProcessPDF(Object document) throws IOException,
	 * BadElementException, DocumentException { Document pdf = (Document)
	 * document; pdf.open(); pdf.setPageSize(PageSize.A4);
	 * 
	 * ExternalContext externalContext =
	 * FacesContext.getCurrentInstance().getExternalContext(); String logo =
	 * externalContext.getRealPath("") + File.separator + "sms"+ File.separator
	 * + "src" + File.separator + "main"+ File.separator + "webapp" +
	 * File.separator + "images" + File.separator + "absent.png"; String
	 * logo="/images/absent.png"; pdf.add(Image.getInstance(logo)); }
	 */
	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
			HSSFCell cell = header.getCell(i);

			cell.setCellStyle(cellStyle);
		}
	}

}
