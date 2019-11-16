package com.stumosys.managedBean;

import java.util.ArrayList;
import java.util.List;
//import java.util.Properties;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
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
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
*/
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.stumosys.controler.SmsController;
import com.stumosys.domain.ReportCardDataBean;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name = "reportCardMB")
@RequestScoped
public class ReportCardMB {

	ReportCardDataBean reportCardDataBean = new ReportCardDataBean();
	private List<String> classList = null;
	private List<String> subjectList = null;
	private List<ReportCardDataBean> studentClassList = null;
	private List<ReportCardDataBean> filterstudentClass;
	private boolean editFlag = false;
	private List<String> examtitle = null;
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	private static Logger logger = Logger.getLogger(ReportCardMB.class);
	private boolean gradeFlag=false;
	private boolean resultFlag=false;

	public boolean isGradeFlag() {
		return gradeFlag;
	}

	public void setGradeFlag(boolean gradeFlag) {
		this.gradeFlag = gradeFlag;
	}

	public boolean isResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(boolean resultFlag) {
		this.resultFlag = resultFlag;
	}

	/**
	 * @return the studentClassList
	 */
	public List<ReportCardDataBean> getStudentClassList() {
		return studentClassList;
	}

	/**
	 * @param studentClassList
	 *            the studentClassList to set
	 */
	public void setStudentClassList(List<ReportCardDataBean> studentClassList) {
		this.studentClassList = studentClassList;
	}

	/**
	 * @return the reportCardDataBean
	 */
	public ReportCardDataBean getReportCardDataBean() {
		return reportCardDataBean;
	}

	/**
	 * @param reportCardDataBean
	 *            the reportCardDataBean to set
	 */
	public void setReportCardDataBean(ReportCardDataBean reportCardDataBean) {
		this.reportCardDataBean = reportCardDataBean;
	}

	public String addMarkPage() {
		SmsController controller = null;
		classList = null;
		subjectList = null;
		studentClassList = null;
		editFlag = false;
		examtitle = null;
		try {

			classList = new ArrayList<String>();
			logger.debug("------addmarkpage method calling-----");
			reportCardDataBean.setStudMarkClass("");
			reportCardDataBean.setExamMarkTitle("");
			reportCardDataBean.setMarkSubTitle("");
			setEditFlag(false);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String roll = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role");
			if (personID != null) {
				classList = controller.getClassNameList(personID);
				/* examtitle=controller.getExamTitleName(personID); */
				if (roll.equalsIgnoreCase("Teacher")) {
					setEditFlag(true);
				} else {
					setEditFlag(false);
				}

				return "studentmark";
			} else {
				return "";
			}
		} catch (Exception e) {
			logger.debug("------addmarkpage method Exception calling-----");
			logger.warn("Exception -->"+e.getMessage());
		}

		return "";
	}

	private boolean validate(boolean flag) {
		String fieldName;
		boolean valid = true;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (StringUtils.isEmpty(reportCardDataBean.getStudMarkClass())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("studentMarkClass").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Class."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(reportCardDataBean.getExamMarkTitle())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("markExamTitle").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Exam Title."));
			}
			valid = false;
		}

		if (StringUtils.isEmpty(reportCardDataBean.getMarkSubTitle())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("markSubjectTitle").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Subject."));
			}
			valid = false;
		}

		return valid;
	}

	public String submit() {
		SmsController controller = null;
		String schoolID="";
		try {
			logger.debug("------submit method calling-----");

			if (validate(true)) {

				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				String roll = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("Role");
				schoolID=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID").toString();
				logger.debug("personID" + personID);
				studentClassList = new ArrayList<ReportCardDataBean>();
				if (personID != null) {
					studentClassList = controller.getStudent(personID, reportCardDataBean);
					logger.debug("Size in MB" + studentClassList.size());

					if (roll.equalsIgnoreCase("Teacher")) {
						setEditFlag(true);
					} else {
						setEditFlag(false);
					}
					if(schoolID.equalsIgnoreCase(paths.getString("SMRV.SCHOOLID"))){
						String className=reportCardDataBean.getStudMarkClass().split("/")[0];
						if(className.equalsIgnoreCase("Class 10") ||className.equalsIgnoreCase("Class 11") ||className.equalsIgnoreCase("Class 12") ){
							setGradeFlag(false);
							setResultFlag(true);
						}else{
							setGradeFlag(true);
							setResultFlag(false);
						}
					}else{
						setGradeFlag(true);
					}
					return "givemark";

				}

			}
		} catch (Exception e) {
			logger.debug("-------submit methof exception calling------");
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}

	public String reset() {
		logger.debug("------Reset method calling------");
		reportCardDataBean.setStudMarkClass("");
		reportCardDataBean.setExamMarkTitle("");
		reportCardDataBean.setMarkSubTitle("");

		return "";
	}

	/**
	 * @return the classList
	 */
	public List<String> getClassList() {
		return classList;
	}

	/**
	 * @param classList
	 *            the classList to set
	 */
	public void setClassList(List<String> classList) {
		this.classList = classList;
	}

	/**
	 * @return the subjectList
	 */
	public List<String> getSubjectList() {
		return subjectList;
	}

	/**
	 * @param subjectList
	 *            the subjectList to set
	 */
	public void setSubjectList(List<String> subjectList) {
		this.subjectList = subjectList;
	}

	public void classChange(ValueChangeEvent event) {
		SmsController controller = null;
		subjectList = null;
		examtitle = null;
		try {
			subjectList = new ArrayList<String>();
			examtitle = new ArrayList<String>();

			if (event.getNewValue().toString() != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				if (personID != null) {
					reportCardDataBean.setStudMarkClass(event.getNewValue().toString());
					examtitle = controller.getExamTitleName(personID, event.getNewValue().toString());
					subjectList = controller.getSubjectNameList(reportCardDataBean, personID);
					logger.debug("Size---" + subjectList);

				}
			}
		} catch (NullPointerException e1) {

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}

	/**
	 * @return the filterstudentClass
	 */
	public List<ReportCardDataBean> getFilterstudentClass() {
		return filterstudentClass;
	}

	/**
	 * @param filterstudentClass
	 *            the filterstudentClass to set
	 */
	public void setFilterstudentClass(List<ReportCardDataBean> filterstudentClass) {
		this.filterstudentClass = filterstudentClass;
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed",
					"Old: " + oldValue + ", New:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void valueChange(ValueChangeEvent event) {
		String rollNo = event.getComponent().getAttributes().get("roll").toString();
		logger.debug(rollNo);
		logger.debug("----" + event.getNewValue().toString());

	}

	public void onRowEdit(RowEditEvent event) {
		SmsController controller = null;
		String mark = "";
		//String result = "";
		//int submark = 0;
		String status = "Fail";
		String serial = "";
		String rollNo = "";
		String name = "",result="";
		logger.debug("Calling");
		try {
			ApplicationContext ctxs = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctxs.getBean("controller");
			String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			reportCardDataBean.setSchoolID(schoolid);
			reportCardDataBean.setsNo("");
			reportCardDataBean.setRollNo("");
			reportCardDataBean.setName("");
			reportCardDataBean.setMark("");
			reportCardDataBean.setResultStatus("");
			reportCardDataBean.setGrade("");
			mark = ((ReportCardDataBean) event.getObject()).getMark().toString();
			serial = ((ReportCardDataBean) event.getObject()).getsNo().toString();
			rollNo = ((ReportCardDataBean) event.getObject()).getRollNo().toString();
			name = ((ReportCardDataBean) event.getObject()).getName().toString();
			reportCardDataBean.setsNo(serial);
			reportCardDataBean.setRollNo(rollNo);
			reportCardDataBean.setName(name);
			reportCardDataBean.setMark(mark);
			logger.debug("mark "+mark);
			if (mark == null || mark.equals(null) || mark.equals("")) {
				FacesMessage msg = new FacesMessage("Please Add Mark");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}else{
				if (mark.equalsIgnoreCase("A")) {
					reportCardDataBean.setMark(mark);
					reportCardDataBean.setResultStatus("Absent");
					reportCardDataBean.setGrade("F");
					status = setTableValues(reportCardDataBean);
				} else {
					try {
						if(schoolID.equalsIgnoreCase(paths.getString("SMRV.SCHOOLID"))){
							String className=reportCardDataBean.getStudMarkClass().split("/")[0];
							if(className.equalsIgnoreCase("Class 10") ||className.equalsIgnoreCase("Class 11") ||className.equalsIgnoreCase("Class 12")){
								result=((ReportCardDataBean) event.getObject()).getResultStatus().toString();
								reportCardDataBean.setResultStatus(result);
							}else{
								controller.getexammarkgrade(reportCardDataBean);
							}
						}else{
							controller.getexammarkgrade(reportCardDataBean);
						}
						/*if(reportCardDataBean.getGrade().equalsIgnoreCase("U")||reportCardDataBean.getGrade().equalsIgnoreCase("F")){
							reportCardDataBean.setResultStatus("Fail");
						}else{
							reportCardDataBean.setResultStatus("Pass");
							/*reportCardDataBean.setGrade("B");
							status = setTableValues(reportCardDataBean);
						} else if (submark <= 54 && submark >= 50 || submark >= 50 && submark <= 54) {
							reportCardDataBean.setMark(mark);
							reportCardDataBean.setResultStatus("Pass");
							reportCardDataBean.setGrade("B-");
							status = setTableValues(reportCardDataBean);
						} else if (submark <= 49 && submark >= 45 || submark >= 45 && submark <= 49) {
							reportCardDataBean.setMark(mark);
							reportCardDataBean.setResultStatus("Pass");
							reportCardDataBean.setGrade("C");
							setTableValues(reportCardDataBean);
						} else if (submark <= 44 && submark >= 40 || submark >= 40 && submark <= 44) {
							reportCardDataBean.setMark(mark);
							reportCardDataBean.setResultStatus("Pass");
							reportCardDataBean.setGrade("C");
							status = setTableValues(reportCardDataBean);
						} else if (submark <= 39 && submark >= 35 || submark >= 35 && submark <= 39) {
							reportCardDataBean.setMark(mark);
							reportCardDataBean.setResultStatus("Partially Pass");
							reportCardDataBean.setGrade("C-");
							status = setTableValues(reportCardDataBean);
						} else if (submark <= 34 && submark >= 30 || submark >= 30 && submark <= 34) {
							reportCardDataBean.setMark(mark);
							reportCardDataBean.setResultStatus("Partially Pass");
							reportCardDataBean.setGrade("D");
							status = setTableValues(reportCardDataBean);
						} else if (submark <= 29 && submark >= 25 || submark >= 25 && submark <= 29) {
							reportCardDataBean.setMark(mark);
							reportCardDataBean.setResultStatus("Partially Pass");
							reportCardDataBean.setGrade("D");
							status = setTableValues(reportCardDataBean);
						} else if (submark <= 24 && submark >= 0 || submark >= 0 && submark <= 24) {
							reportCardDataBean.setMark(mark);
							reportCardDataBean.setResultStatus("Fail");
							reportCardDataBean.setGrade("F");
							status = setTableValues(reportCardDataBean);
						} else {
							logger.debug("Not valid Number");
							reportCardDataBean.setMark(mark);
							reportCardDataBean.setResultStatus("NA");
							reportCardDataBean.setGrade("NA");
							status = setTableValues(reportCardDataBean);
						}*/
						status = setTableValues(reportCardDataBean);
					} catch (NumberFormatException nex) {
						/*
						 * reportCardDataBean.setMarkSubTitle(mark);
						 * reportCardDataBean.setResultStatus("NA");
						 * reportCardDataBean.setGrade("NA");
						 * setTableValues(reportCardDataBean);
						 */
						ReportCardDataBean rp = new ReportCardDataBean();
						rp.setGrade("");
						rp.setsNo(reportCardDataBean.getsNo());
						rp.setResultStatus("");
						rp.setName(reportCardDataBean.getName());
						rp.setRollNo(reportCardDataBean.getRollNo());
						rp.setMark("");
						studentClassList.set((Integer.parseInt(reportCardDataBean.getsNo()) - 1), rp);

						FacesMessage msg = new FacesMessage("Please Enter Valid Mark", reportCardDataBean.getRollNo());
						FacesContext.getCurrentInstance().addMessage(null, msg);

					}
				}
			}
			if (status.equalsIgnoreCase("Success")) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				if (personID != null) {
					String reportStatus = controller.addReportCard(personID, reportCardDataBean);

					if (reportStatus.equalsIgnoreCase("Success")) {
						FacesMessage msg1 = new FacesMessage("Please wait..", reportCardDataBean.getRollNo());
						FacesContext.getCurrentInstance().addMessage(null, msg1);
						MailSendJDBC.addReportCard(reportCardDataBean,schoolName,schoolid);
						/*String email = sendEmail(reportCardDataBean);
						if (email.equalsIgnoreCase("Success")) {*/
							FacesMessage msg = new FacesMessage("Sucessfully Saved mark",
									reportCardDataBean.getRollNo());
							FacesContext.getCurrentInstance().addMessage(null, msg);
						/*}*/
					} else if (reportStatus.equalsIgnoreCase("Edit")) {
						FacesMessage msg1 = new FacesMessage("Please wait..", reportCardDataBean.getRollNo());
						FacesContext.getCurrentInstance().addMessage(null, msg1);
						MailSendJDBC.addReportCard(reportCardDataBean,schoolName,schoolid);
						/*String email = sendEmail(reportCardDataBean);
						if (email.equalsIgnoreCase("Success")) {*/
							FacesMessage msg = new FacesMessage("Sucessfully updated mark",
									reportCardDataBean.getRollNo());
							FacesContext.getCurrentInstance().addMessage(null, msg);
						/*}*/
					} else {
						FacesMessage msg = new FacesMessage("Network Problem.Please try again..",
								reportCardDataBean.getRollNo());
						FacesContext.getCurrentInstance().addMessage(null, msg);
					}
				}
			}
		} catch (NullPointerException e1) {
			e1.printStackTrace();
			logger.warn("Exception -->"+e1.getMessage());
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}

	private String setTableValues(ReportCardDataBean reportCardDataBean) {
		String status = "Fail";
		if (reportCardDataBean.getResultStatus().equalsIgnoreCase("NA")) {
			ReportCardDataBean rp = new ReportCardDataBean();
			rp.setGrade("");
			rp.setsNo(reportCardDataBean.getsNo());
			rp.setResultStatus("");
			rp.setName(reportCardDataBean.getName());
			rp.setRollNo(reportCardDataBean.getRollNo());
			rp.setMark("");
			rp.setUpflag(false);
			rp.setDownflag(false);
			studentClassList.set((Integer.parseInt(reportCardDataBean.getsNo()) - 1), rp);

			FacesMessage msg = new FacesMessage("Please Enter Valid Mark", reportCardDataBean.getsNo());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			ReportCardDataBean rp = new ReportCardDataBean();
			rp.setGrade(reportCardDataBean.getGrade());
			rp.setsNo(reportCardDataBean.getsNo());
			rp.setResultStatus(reportCardDataBean.getResultStatus());
			rp.setName(reportCardDataBean.getName());
			rp.setRollNo(reportCardDataBean.getRollNo());
			rp.setMark(reportCardDataBean.getMark());
			studentClassList.set((Integer.parseInt(reportCardDataBean.getsNo()) - 1), rp);
			/*
			 * FacesMessage msg = new FacesMessage("Sucessfully Saved mark"
			 * ,reportCardDataBean.getsNo() );
			 * FacesContext.getCurrentInstance().addMessage(null, msg);
			 */
			status = "Success";
		}
		return status;
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled", ((ReportCardDataBean) event.getObject()).getRollNo());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * @return the editFlag
	 */
	public boolean isEditFlag() {
		return editFlag;
	}

	/**
	 * @param editFlag
	 *            the editFlag to set
	 */
	public void setEditFlag(boolean editFlag) {
		this.editFlag = editFlag;
	}

	/**
	 * @return the examtitle
	 */
	public List<String> getExamtitle() {
		return examtitle;
	}

	/**
	 * @param examtitle
	 *            the examtitle to set
	 */
	public void setExamtitle(List<String> examtitle) {
		this.examtitle = examtitle;
	}

	/*private String sendEmail(ReportCardDataBean reportCardDataBean) {
		String status = "Fail";

		try {
			String to = reportCardDataBean.getMailid();
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
					+ "<h3>Dear Parents/Students,</h3>" + "<p>We have listed the"
					+ reportCardDataBean.getExamMarkTitle() + " Marks for " + reportCardDataBean.getMarkSubTitle()
					+ " Subject."
					+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
					+ "<tr>" + "<td> Student ID" + "</td>" + "<td> Name" + "</td>" + "<td> Mark" + "</td>"
					+ "<td> Grade " + "</td>" + "<td> Result " + "</td>" + "</tr>" + "<tr>" + "<td>"
					+ reportCardDataBean.getRollNo() + "</td>" + "<td>" + reportCardDataBean.getName() + "</td>"
					+ "<td>" + reportCardDataBean.getMark() + "</td>" + "<td>" + reportCardDataBean.getGrade() + "</td>"
					+ "<td>" + reportCardDataBean.getResultStatus() + "</td>" + "</tr>" + "</table>" + "<footer>"
					+ "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>"
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
				message.setSubject(
						reportCardDataBean.getExamMarkTitle() + " Marks for " + reportCardDataBean.getMarkSubTitle());
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

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}*/

}
