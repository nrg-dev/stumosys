package com.stumosys.managedBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
*/
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.stumosys.controler.SmsController;
import com.stumosys.domain.AttendanceDataBean;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name = "attendanceMB")
@RequestScoped
public class AttendanceMB {

	AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
	private List<String> classList;
	private List<AttendanceDataBean> filteredstudent;
	private boolean flag = false;
	private boolean flag1 = false;
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	private List<String> studentList;
	private boolean apprflag = false;
	private boolean statusflag = false;
	private boolean classdropflag = false;
	private boolean classlabelflag = false;
	
	
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

	public boolean isStatusflag() {
		return statusflag;
	}

	public void setStatusflag(boolean statusflag) {
		this.statusflag = statusflag;
	}

	public boolean isApprflag() {
		return apprflag;
	}

	public void setApprflag(boolean apprflag) {
		this.apprflag = apprflag;
	}

	public List<String> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<String> studentList) {
		this.studentList = studentList;
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

	public List<AttendanceDataBean> getFilteredstudent() {
		return filteredstudent;
	}

	public void setFilteredstudent(List<AttendanceDataBean> filteredstudent) {
		this.filteredstudent = filteredstudent;
	}

	public List<String> getClassList() {
		return classList;
	}

	public void setClassList(List<String> classList) {
		this.classList = classList;
	}

	/**
	 * @return the attendanceDataBean
	 */
	public AttendanceDataBean getAttendanceDataBean() {
		return attendanceDataBean;
	}

	/**
	 * @param attendanceDataBean
	 *            the attendanceDataBean to set
	 */
	public void setAttendanceDataBean(AttendanceDataBean attendanceDataBean) {
		this.attendanceDataBean = attendanceDataBean;
	}

	private static Logger logger = Logger.getLogger(AttendanceMB.class);
	SmsController controller = null;
	/* Ragulan OCT 25   markAttendancePage*/
	
	private List<String> classSectionList;
	private boolean flagdropdown = false;
	
	
	public boolean isFlagdropdown() {
		return flagdropdown;
	}

	public void setFlagdropdown(boolean flagdropdown) {
		this.flagdropdown = flagdropdown;
	}

	public List<String> getClassSectionList() {
		return classSectionList;
	}

	public void setClassSectionList(List<String> classSectionList) {
		this.classSectionList = classSectionList;
	}

	public String markAttendancePage() {
		logger.info("-----------Inside markAttendancePage method()----------------");
		//logger.info("take attendance - ");
		attendanceDataBean.setStudentList(null);
		int school_id = 0;
		try {
			attendanceDataBean.setClassname("");
			attendanceDataBean.setSection("");
			classSectionList= new ArrayList<String>();
			String personID = "";
			String schoolID = ""; 
			flag = false;
			flagothers = false;
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			school_id=Integer.parseInt(schoolID);
			if (personID != null) {
				
				if(school_id==2 || school_id==3 || school_id==4 || school_id==5 || school_id==6 || school_id==37 ){
					flagdropdown=false;
					setClasslabelflag(true);
					controller.takeattendance(personID,attendanceDataBean);	
					if(attendanceDataBean.getClassname()==null || attendanceDataBean.getClassname().equals("")){
					     attendanceDataBean.setClassname("You don't have any class right now");
					}
					flag = true;
					if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
							.equals("Teacher")) {
						flag1 = true;
					} else {
						flag1 = false;
					}
				}
				else{
					flagdropdown=true;
					classSectionList = controller.getTeaClasslist(personID);
					attendanceDataBean.setPeriod("");
					attendanceDataBean.setClassname("");
				}
				/*}*/
			}
			return "Addattendance";
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			return "";
		} finally {

		}
	}

	private boolean flagothers = false;
	
	public boolean isFlagothers() {
		return flagothers;
	}

	public void setFlagothers(boolean flagothers) {
		this.flagothers = flagothers;
	}

	public void search(ValueChangeEvent v) {
		logger.info("---------- submit Method Calling-----");
		String fieldName="";
		attendanceDataBean.setStudentList(null);
		flag = false;
		flag1 = false;boolean valid=true;
		flagothers =false;
		String personID = "";
		int school_id = 0;
		FacesContext fc=FacesContext.getCurrentInstance();
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			school_id=Integer.parseInt((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID"));
			if (personID != null) {
				
				if((school_id==2) || (school_id==3) || (school_id==4) || (school_id==5) || (school_id==6)){
				attendanceDataBean.setClassname(v.getNewValue().toString());
				controller.studentList(personID, attendanceDataBean);
				flag = true;
				flagothers=false;
				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
						.equals("Teacher")) {
					flag1 = true;
				} else {
					flag1 = false;
				}
				}
				else {
					if(attendanceDataBean.getDate()==null){
						fieldName = CommonValidate.findComponentInRoot("attPreDate").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Select the Date"));
						valid=false;
					}else if(attendanceDataBean.getClassname().equalsIgnoreCase("")){
						fieldName = CommonValidate.findComponentInRoot("attClass").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Choose the Class Name"));
						valid=false;
					}else{
						attendanceDataBean.setPeriod(v.getNewValue().toString());
						setClasslabelflag(true);
						controller.takeattendanceNew(personID,attendanceDataBean);	
						if(attendanceDataBean.getClassname()==null || attendanceDataBean.getClassname().equals("")){
						     attendanceDataBean.setClassname("You don't have any class right now");
						}
						flag = false;
						flagothers =true;
						if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
								.equals("Teacher")) {
							flag1 = true;
						} else {
							flag1 = false;
						}
					}
					
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}
	public String Attendanceinsert(){
		SmsController controller=null;
	
		try{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			Calendar now = Calendar.getInstance();
			if(personID!=null){
				logger.info("--------attendance Insert----------------->"+attendanceDataBean.getStudentList().size());
				attendanceDataBean.setMonthyear("" + (now.get(Calendar.MONTH) + 1));
				attendanceDataBean.setYear("" + now.get(Calendar.YEAR));
				for(int i=0;i<attendanceDataBean.getStudentList().size();i++){
					logger.info("========getStatus=============>"+attendanceDataBean.getStudentList().get(i).getStatus1());
					logger.info("========StudentID=============>"+attendanceDataBean.getStudentList().get(i).getStudentID());
					attendanceDataBean.setStudentID(attendanceDataBean.getStudentList().get(i).getStudentID());
					attendanceDataBean.setStatus(attendanceDataBean.getStudentList().get(i).getStatus1());
					attendanceDataBean.setDate(attendanceDataBean.getStudentList().get(i).getDate());
					String status=controller.insertAttandance(personID,schoolID,attendanceDataBean);
					logger.info("status--->"+status);
					if(status.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("PF('confirm').show()");	
					}
				}
				logger.info("==getPeriod===>"+attendanceDataBean.getPeriod());
				logger.info("==getClassname===>"+attendanceDataBean.getClassname());
				logger.info("==getDate===>"+attendanceDataBean.getDate());	
				controller.takeattendanceNew(personID,attendanceDataBean);
			}
		}catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return "";
	}
	
	public void loadimage(ValueChangeEvent v) {
		logger.debug("status -- " + v.getNewValue());
		int school_id = 0;
		int no = 0;
		String personID = "";
		try {
			Calendar now = Calendar.getInstance();
			school_id=Integer.parseInt((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID"));
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				no = (Integer) v.getComponent().getAttributes().get("sno");
				attendanceDataBean.setStatus(v.getNewValue().toString());
				attendanceDataBean.setStudentName("" + v.getComponent().getAttributes().get("name"));
				attendanceDataBean.setStudentID("" + v.getComponent().getAttributes().get("ID"));
				attendanceDataBean.setDate((Date) v.getComponent().getAttributes().get("date"));
				attendanceDataBean.setMonthyear("" + (now.get(Calendar.MONTH) + 1));
				attendanceDataBean.setYear("" + now.get(Calendar.YEAR));
				logger.debug("preiod ------>"+attendanceDataBean.getPeriod());
				if((school_id==1) || (school_id==2) || (school_id==3) || (school_id==4) || (school_id==5) || (school_id==6)) {
					controller.attendanceStatus(personID, attendanceDataBean);
				}
				else{
					logger.debug("=====class sec name====>"+attendanceDataBean.getClassname()+"===================>"
				+attendanceDataBean.getClassname().split("/")[0]+"------>"+attendanceDataBean.getClassname().split("/")[1]);
					controller.attendanceStatusNew(personID, attendanceDataBean);
				}
			
				AttendanceDataBean li = new AttendanceDataBean();
				li.setSno((Integer) v.getComponent().getAttributes().get("sno"));
				li.setDate((Date) v.getComponent().getAttributes().get("date"));
				li.setStudentName("" + v.getComponent().getAttributes().get("name"));
				li.setStudentID("" + v.getComponent().getAttributes().get("ID"));
				li.setPercentage(attendanceDataBean.getPercentage());
				li.setStatus(v.getNewValue().toString());
				li.setFlag(false);
				li.setFlag1(true);
				attendanceDataBean.getStudentList().set(no - 1, li);
				RequestContext reqcontextt = RequestContext.getCurrentInstance();
				reqcontextt.execute("attendBlk();");
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	public String prsentStatus() {
		flag = true;
		String personID = "";
		try {
			String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			if (personID != null) {
				if (attendanceDataBean.getStatus().equals("Absent")) {
					/*String mailStatus = sendEmail(attendanceDataBean);
					if (mailStatus.equalsIgnoreCase("Success")) {*/
						MailSendJDBC.attendanceTake(attendanceDataBean,schoolName,schoolid);
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('attendblocksUI').hide();");
					/*}*/
				} else {
					RequestContext reqcontext = RequestContext.getCurrentInstance();
					reqcontext.execute("PF('attendblocksUI').hide();");
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return "";
	}
	
	//hidden by Alex

	/*private String sendEmail(AttendanceDataBean attendanceDataBean) {
		String status = "Fail";
		String period = "";
		String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
		try {
			InternetAddress[] toAddress = new InternetAddress[attendanceDataBean.getMails().size()];
			for (int i = 0; i < attendanceDataBean.getMails().size(); i++) {
				toAddress[i] = new InternetAddress(attendanceDataBean.getMails().get(i));
			}
			Properties prop = new Properties();
			prop.put("mail.smtp.host", text.getString("sms.mail.host"));
			prop.put("mail.smtp.socketFactory.port", text.getString("sms.mail.port"));
			prop.put("mail.smtp.socketFactory.class", text.getString("sms.mail.class"));
			prop.put("mail.smtp.starttls.enable", text.getString("sms.mail.smtp.startles"));
			prop.put("mail.smtp.auth", text.getString("sms.mail.auth"));
			prop.put("mail.smtp.port", text.getString("sms.mail.smtp.port"));
			prop.put("mail.smtp.ssl.trust", text.getString("sms.mail.smtp.trust"));
			if (attendanceDataBean.getPeriod().equals("1"))
				period = "st Period";
			else if (attendanceDataBean.getPeriod().equals("2"))
				period = "nd Period";
			else if (attendanceDataBean.getPeriod().equals("3"))
				period = "rd Period";
			else
				period = "th Period";
			String body1 = "<htm><head></heade><body>"
					+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
					+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
					+ "<h3>Dear " + attendanceDataBean.getStudentName() + ",</h3>"
					+ "<p>Welcome on-board into "
					+ schoolName
					+ "family.The Student " + attendanceDataBean.getStudentName()
					+ " is Absent Today " + attendanceDataBean.getPeriod() + period + ".</p>"

					+ "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>"
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
				message.setSubject("Take Attendance");
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);
				Transport.send(message);

				logger.info("message sent successfully");
				status = "Success";

			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}*/

	public String leaveRequestCall() {
		logger.debug("leave request --");
		String personID = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				studentList = controller.parentAttlist(personID, attendanceDataBean);
				Collections.sort(studentList);
				return "leaveRequest";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {
			attendanceDataBean.setStudentID("");
			attendanceDataBean.setDate(null);
			attendanceDataBean.setLeavereason("");
			flag1 = false;
		}
		return "";
	}

	public String leaveRequsetSave() {
		String personID = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				if (dateValidate(true)) {
					String status = controller.leaveRequest(attendanceDataBean, personID);
					if (check(true, status)) {
						flag1 = true;
					} else {
						flag1 = false;
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return "";
	}

	private boolean check(boolean flag, String status) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (status.equals("exist")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("studentID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Leave Request Already Raised for this Student"));
			}
			valid = false;
		}
		return valid;
	}

	public String leaveback() {
		logger.info("back to leave request view");
		String personID="";flag1 = false;
		try{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				controller.leaveRequsetView(attendanceDataBean, personID);					
				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role").equals("Admin")){
					apprflag = true;statusflag = false;
				}
				else{
					apprflag = false;statusflag = true;
				}
				flag = true;
			}
		}catch(Exception e){
			logger.warn("Inside Exception",e);
		}		
		return "";
	}

	public String leaveRequestViewCall() {
		logger.info("leave request View--");
		String personID = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				classList = controller.ClassListAttView(personID, attendanceDataBean);
				Collections.sort(classList);
				return "leaveRequestView";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {
			attendanceDataBean.setStudentID("");
			attendanceDataBean.setDate(null);
			attendanceDataBean.setClassname("");
			flag = false;statusflag = false;
			flag1 = false;apprflag = false;
		}
		return "";
	}
	
	public void dateSelect(ValueChangeEvent v) {
		attendanceDataBean.setClassname("");
		flag=false;
	}

	public void classSearch(ValueChangeEvent v) {
		String personID = "";
		flag = false;
		flag1 = false;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				if (viewValidate(true)) {
					attendanceDataBean.setClassname(v.getNewValue().toString());
					controller.leaveRequsetView(attendanceDataBean, personID);
					if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role").equals("Admin")){
						apprflag = true;statusflag = false;
					}
					else{
						apprflag = false;statusflag = true;
					}
					flag = true;
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}

	private boolean viewValidate(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (attendanceDataBean.getDate() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("date").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Date ."));
			}
			valid = false;
		}
		return valid;
	}

	public String leaveApproval() {
		logger.info("leave request approval -- ");
		String personID = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				controller.leaveRequsetApproval(attendanceDataBean, personID);
				attendanceDataBean.setStatus("Approve");
				flag1 = true;
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return "";
	}
	
	public String leaveReject() {
		logger.info("leave request reject -- ");
		String personID = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				controller.leaveRequsetReject(attendanceDataBean, personID);
				attendanceDataBean.setStatus("Reject");
				flag1 = true;
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return "";
	}
	
	private boolean dateValidate(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (attendanceDataBean.getStudentID().equals("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("studentID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select one Student Name."));
			}
			valid = false;
		}
		if (attendanceDataBean.getDate() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("date").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Date."));
			}
			valid = false;
		}
		if (attendanceDataBean.getLeavereason().equals("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("leave").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Leave Reason."));
			}
			valid = false;
		}
		return valid;
	}
}
