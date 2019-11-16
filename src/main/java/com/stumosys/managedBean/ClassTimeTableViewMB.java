package com.stumosys.managedBean;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
//import java.util.HashSet;
import java.util.List;
//import java.util.Properties;
import java.util.ResourceBundle;
//import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
/*import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.stumosys.controler.SmsController;
import com.stumosys.domain.AttendanceDataBean;
import com.stumosys.domain.ClassTimeTableDataBean;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;



@ManagedBean(name = "classTimeTableViewMB")
@RequestScoped
public class ClassTimeTableViewMB {

	ClassTimeTableDataBean classTimeTableDataBean = new ClassTimeTableDataBean();
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	Date now = new Date();
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	private static Logger logger = Logger.getLogger(ClassTimeTableViewMB.class);
	private List<AttendanceDataBean> filteredclass;
	private List<String> classes;
	private boolean flag = false;
	private boolean editflag = false;
	private boolean studentflag = false;
	private boolean addflag = false;
	private boolean addPeriodflag = false;
	private boolean addflagz = false;
	private boolean boxflag = false;
	private boolean boxflag1 = false;
	private boolean updateflag = false;
	private boolean updateflag1 = false;
	private boolean updateflag2 = false;
	private List<String> teacherIDList=null;
	private ClassTimeTableDataBean selectedClassTable;
	private boolean flag1 = false;
	private boolean flag2 = false;
	private boolean codeflag=false;
	private boolean codeflag1=false;
	
	public boolean isCodeflag() {
		return codeflag;
	}

	public void setCodeflag(boolean codeflag) {
		this.codeflag = codeflag;
	}

	public boolean isCodeflag1() {
		return codeflag1;
	}

	public void setCodeflag1(boolean codeflag1) {
		this.codeflag1 = codeflag1;
	}

	public boolean isBoxflag1() {
		return boxflag1;
	}

	public void setBoxflag1(boolean boxflag1) {
		this.boxflag1 = boxflag1;
	}

	public boolean isAddPeriodflag() {
		return addPeriodflag;
	}

	public void setAddPeriodflag(boolean addPeriodflag) {
		this.addPeriodflag = addPeriodflag;
	}

	public boolean isUpdateflag1() {
		return updateflag1;
	}

	public void setUpdateflag1(boolean updateflag1) {
		this.updateflag1 = updateflag1;
	}

	public boolean isUpdateflag2() {
		return updateflag2;
	}

	public void setUpdateflag2(boolean updateflag2) {
		this.updateflag2 = updateflag2;
	}

	public boolean isFlag1() {
		return flag1;
	}

	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}

	public boolean isFlag2() {
		return flag2;
	}

	public void setFlag2(boolean flag2) {
		this.flag2 = flag2;
	}

	public boolean isUpdateflag() {
		return updateflag;
	}

	public void setUpdateflag(boolean updateflag) {
		this.updateflag = updateflag;
	}

	public boolean isBoxflag() {
		return boxflag;
	}

	public void setBoxflag(boolean boxflag) {
		this.boxflag = boxflag;
	}

	public boolean isAddflagz() {
		return addflagz;
	}

	public void setAddflagz(boolean addflagz) {
		this.addflagz = addflagz;
	}

	public boolean isAddflag() {
		return addflag;
	}

	public void setAddflag(boolean addflag) {
		this.addflag = addflag;
	}

	public boolean isStudentflag() {
		return studentflag;
	}

	public void setStudentflag(boolean studentflag) {
		this.studentflag = studentflag;
	}

	public boolean isEditflag() {
		return editflag;
	}

	public void setEditflag(boolean editflag) {
		this.editflag = editflag;
	}

	public List<AttendanceDataBean> getFilteredclass() {
		return filteredclass;
	}

	public void setFilteredclass(List<AttendanceDataBean> filteredclass) {
		this.filteredclass = filteredclass;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public List<String> getClasses() {
		return classes;
	}

	public void setClasses(List<String> classes) {
		this.classes = classes;
	}

	public ClassTimeTableDataBean getClassTimeTableDataBean() {
		return classTimeTableDataBean;
	}

	public void setClassTimeTableDataBean(ClassTimeTableDataBean classTimeTableDataBean) {
		this.classTimeTableDataBean = classTimeTableDataBean;
	}

	public SimpleDateFormat getFt() {
		return ft;
	}

	public void setFt(SimpleDateFormat ft) {
		this.ft = ft;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public String classTimeViewCall() {
		logger.info("class time table view call");
		addflag = false;addPeriodflag=false;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			classTimeTableDataBean.setMonthlist(Arrays.asList("January","February","March","April","May","June","July","August","September","October","November","December"));
			if (personID != null) {
				if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
					flag1 = true;
					flag2 = false;
				}else{
					flag1 = false;
					flag2 = true;
				}
				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
						.equals("Teacher")) {
					return "classTeacherTimeTable";
				} else {
					if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
							.equals("Student")) {
						studentflag = false;
						addflagz = false;
					} else if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
							.equals("Admin")) {
						classes = controller.classeslist(personID);
						Collections.sort(classes);
						studentflag = true;
						addflagz = true;
					} else {
						classes = controller.classeslist(personID);
						Collections.sort(classes);
						studentflag = true;
						addflagz = false;
					}
					return "classTimeTableView";
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {
			classTimeTableDataBean.setClassname("");
			classTimeTableDataBean.setDay("");
			flag = false;
			boxflag = false;
			boxflag1 = false;
		}
		return "";
	}

	public void timetableview() {
		logger.debug("day - " + classTimeTableDataBean.getDay());
		addPeriodflag=false;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role").equals("Parent")
					|| FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
							.equals("Admin")) {
				/*if(!classTimeTableDataBean.getMonth().equalsIgnoreCase(null)){*/
					if (validate(true)) {
						if (personID != null) {
							/*classTimeTableDataBean.setMonth(v.getNewValue().toString());*/
							controller.classTimeTableView(classTimeTableDataBean, personID);
							if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
									.equals("Parent"))
								editflag = false;
							else if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
									.equals("Admin"))
								editflag = true;
							flag = true;
						}
					}
			/*	} */
			}
			 else {
				 if (validate(true)) {
				if (personID != null) {
					/*classTimeTableDataBean.setMonth(v.getNewValue().toString());*/
					controller.classTimeTableView(classTimeTableDataBean, personID);
					flag = true;
					editflag = false;
				}
				 }
			}
			
		/*	if (classTimeTableDataBean.getClasstimeList().size()>0) {
				for (int i = 0; i < 8; i++) {
					System.out.println("inside for loop");
					for (int j = 0; j < classTimeTableDataBean.getClasstimeList().size(); j++) {
						if (classTimeTableDataBean.getClasstimeList().get(j).getPeriod().equalsIgnoreCase(""+(i+1))) {
							System.out.println("inside class time table list If condition");
						}
						else{
							System.out.println("inside class time table list Else condition");
						}
					}
				}
			}*/
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {
			addflag = false;
			boxflag = false;
			boxflag1 = false;
		}
	}

	public boolean validate(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (classTimeTableDataBean.getClassname().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("classtime").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Class."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getDay().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("day1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Day."));
			}
			valid = false;
		}
		logger.debug("========================"+classTimeTableDataBean.getMonth());
		if (classTimeTableDataBean.getMonth().equalsIgnoreCase("")) {
		
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("month").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Month."));
			}
			valid = false;
		}
		return valid;
	}

	public void loadimage(RowEditEvent event) {
		String personID = "";
		try {
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				classTimeTableDataBean.setSubject(((ClassTimeTableDataBean) event.getObject()).getSubject().toString());
				classTimeTableDataBean
						.setSubjects(((ClassTimeTableDataBean) event.getObject()).getSubjects().toString());
				classTimeTableDataBean
						.setSubjectCode(((ClassTimeTableDataBean) event.getObject()).getSubjectCode().toString());
				classTimeTableDataBean
						.setTimeStart(((ClassTimeTableDataBean) event.getObject()).getTimeStart().toString());
				classTimeTableDataBean.setTimeEnd(((ClassTimeTableDataBean) event.getObject()).getTimeEnd().toString());
				RequestContext reqcontextt = RequestContext.getCurrentInstance();
				reqcontextt.execute("classupdateBlk();");
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
	}
// method to delete class time table period by jakap john
	public String deleteClassTimeTable() {
		logger.info("-----inside Class time table period Delete--------");
		System.out.println("--inside Class time table period Delete-- ");
		SmsController controller = null;
		String status;
		try {
			if (classTimeTableDataBean != null) {
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				if (personID != null) {
					status = controller.deleteClassTimeTable(classTimeTableDataBean, personID);
					
					if(status.equalsIgnoreCase("Success")){
						RequestContext.getCurrentInstance().execute("PF('deleteConfirmPeriodDlg').hide();");
						RequestContext.getCurrentInstance().execute("PF('deleteclassPeriodDialog').show();");
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}
	
	
	
	public String onRowEdit() {
		logger.info("update class period ");
		String personID = "";
		try {
			String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			RequestContext reqcontext = RequestContext.getCurrentInstance();
			if (personID != null) {
			String statuss=controller.checkupdateClassTimeTable(classTimeTableDataBean,personID);
			logger.debug("statuss"+statuss);
			if(statuss.equalsIgnoreCase("Exist")){
				updateflag1=true;
				reqcontext.execute("PF('classupdblocksUI').hide();");
				reqcontext.execute("PF('alreadyclassUpdate').show();");
			}else if(statuss.equalsIgnoreCase("Fail")){
				updateflag2=true;
				reqcontext.execute("PF('classupdblocksUI').hide();");
				reqcontext.execute("PF('alreadyteacherUpdate').show();");
			}else if(statuss.equalsIgnoreCase("Success")){
				String status=controller.updateClassTimeTable(classTimeTableDataBean, personID);
				logger.debug("status"+status);
				if(status.equalsIgnoreCase("Success")){
					MailSendJDBC.classTimeTableUpdate(classTimeTableDataBean,schoolName,schoolid);
					/*String mailStatus = sendEmail(classTimeTableDataBean);
					if (mailStatus.equalsIgnoreCase("Success")) {*/
						updateflag=true;
						reqcontext.execute("PF('classupdblocksUI').hide();");
						reqcontext.execute("PF('classUpdate').show();");
						/*}else{
						updateflag=false;
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('classupdblocksUI').hide();");
						reqcontext.execute("PF('classUpdate').hide();");
					}*/
				}
			}
		}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
		return "";
	}

	public void onRowCancel(RowEditEvent event) {
		/*
		 * String no=""+(((ClassTimeTableDataBean)
		 * event.getObject()).getSerialno());
		 */
		FacesMessage msg = new FacesMessage("Edit Cancelled");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/*private String sendEmail(ClassTimeTableDataBean classTimeTableDataBean) throws NoSuchProviderException {
		String status = "Fail";
		String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("schoolName");
		try {
			if(classTimeTableDataBean.getMails().size() > 0)
			{
				classTimeTableDataBean.getMails().removeAll(Collections.singleton(""));
				classTimeTableDataBean.getMails().removeAll(Collections.singleton(null));
				Set<String> duplicate=new HashSet<String>();
				duplicate.addAll(classTimeTableDataBean.getMails());
				classTimeTableDataBean.getMails().clear();
				classTimeTableDataBean.getMails().addAll(duplicate);
			InternetAddress[] toAddress = new InternetAddress[classTimeTableDataBean.getMails().size()];
			for (int i = 0; i < classTimeTableDataBean.getMails().size(); i++) {
				toAddress[i] = new InternetAddress(classTimeTableDataBean.getMails().get(i));
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
					+ "<h3>Dear </h3>"
					+ "<p>Please check your class time table on "+classTimeTableDataBean.getDay()+"."
					+ "The updated "+classTimeTableDataBean.getPeriod() + " is "+classTimeTableDataBean.getSubject()+ " .</p>"					
					+ "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>"
					+ "</footer>" + "</body></html>";
			Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
				}
			});

			// compose message
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
				message.addRecipients(Message.RecipientType.TO, toAddress);
				 message.addRecipients(Message.RecipientType.CC, myCcList); 
				message.setSubject("Class Time Table Update");
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);
				Transport.send(message);

				logger.info("message sent successfully");
			}

			catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
			status = "Success";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		} finally {

		}
		return status;
	}*/

	public void teacherTableview(ValueChangeEvent v) {
		logger.debug("day - " + v.getNewValue());
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				classTimeTableDataBean.setDay(v.getNewValue().toString());
				controller.teacherTimeTable(personID, classTimeTableDataBean);
				flag = true;
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
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

	public void addClass() {
		logger.debug("add extra class ");
		addflag = true;flag = false;
		String personID = "";setCodeflag(true);setCodeflag1(false);
		boxflag = false;boxflag1 = false;String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();		
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				classes = controller.classeslist(personID);
				Collections.sort(classes);								
				fieldName = CommonValidate.findComponentInRoot("classtime").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(" "));
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		} finally {
			classTimeTableDataBean.setClassname("");classTimeTableDataBean.setEndTime(null);
			classTimeTableDataBean.setDate(null);classTimeTableDataBean.setStartTime(null);
			classTimeTableDataBean.setSubject("");classTimeTableDataBean.setSubjectCode("");
			classTimeTableDataBean.setTeaID("");classTimeTableDataBean.setSubjectcodelist(null);
			classTimeTableDataBean.setSubjectlist(null);
			setTeacherIDList(null);
		}
	}
	
	public String deleteClassTime() {
		String fieldName;int count=0;
		FacesContext fc = FacesContext.getCurrentInstance();
		String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		int schoolid=Integer.parseInt(schoolID);
		try{
			if (StringUtils.isEmpty(classTimeTableDataBean.getClassname())) {
				logger.debug("class - " + classTimeTableDataBean.getClassname());
				fieldName = CommonValidate.findComponentInRoot("classtime").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Class"));
				count++;
			}
			if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				if (StringUtils.isEmpty(classTimeTableDataBean.getDay())) {
					fieldName = CommonValidate.findComponentInRoot("day1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Select Day"));
					count++;
				}
			}
			else{
				if (StringUtils.isEmpty(classTimeTableDataBean.getDay())) {
					fieldName = CommonValidate.findComponentInRoot("day").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Select Day"));
					count++;
				}
			}
			if(count==0){
				RequestContext reqcontext = RequestContext.getCurrentInstance();
				reqcontext.execute("PF('deleteConfirmDlg').show();");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}

	public void addPeriod() {
		logger.debug("add period");
		addPeriodflag = true;flag = false;setCodeflag(true);setCodeflag1(false);
		String personID = "";boxflag = false;addflag=false;
		boxflag1 = false;	
		try {
			String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				classes = controller.classeslist(personID);
				Collections.sort(classes);	
				if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
					flag1 = true;
					flag2 = false;
				}else{
					flag1 = false;
					flag2 = true;
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		} finally {
			classTimeTableDataBean.setEndTime(null);classTimeTableDataBean.setStartTime(null);
			classTimeTableDataBean.setSubject("");classTimeTableDataBean.setSubjectCode("");
			classTimeTableDataBean.setTeaID("");classTimeTableDataBean.setSubjectcodelist(null);
			setTeacherIDList(null);
		}
	}
	
	public String checkPeriod() {
		logger.debug("check period");
		String personID = "";
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		addflag = false;addPeriodflag=true;
		try {
			if (checkPeriod(true)) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				SmsController controller = (SmsController) ctx.getBean("controller");
				personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
				if (personID != null) {
					String status = controller.checkExtraPeriod(personID, classTimeTableDataBean);
					logger.debug("status - " + status);
					if (status.equalsIgnoreCase("fail")) {
						fieldName = CommonValidate.findComponentInRoot("validates1").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("In this time the class already has a period"));
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('classupdblocksUI').hide();");
					}else if (status.equalsIgnoreCase("no")) {
						fieldName = CommonValidate.findComponentInRoot("validates1").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("This Class has no time table for " + classTimeTableDataBean.getDay()));
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('classupdblocksUI').hide();");
					}
					else {
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("extraPeriodBlk();");
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return null;
	}

	public boolean checkPeriod(boolean flag) {
		String fieldName;
		boolean valid = true;
		FacesContext fc = FacesContext.getCurrentInstance();		
		String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		int schoolid=Integer.parseInt(schoolID);
		if (StringUtils.isEmpty(classTimeTableDataBean.getClassname())) {
			logger.debug("class - " + classTimeTableDataBean.getClassname());
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("classz1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Class"));
			}
			valid = false;
		}
		if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
			if (StringUtils.isEmpty(classTimeTableDataBean.getDay())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("days").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Select Day"));
				}
				valid = false;
			}
		}else{
			if (StringUtils.isEmpty(classTimeTableDataBean.getDay())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("days1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Select Day"));
				}
				valid = false;
			}
		}
		
		if (StringUtils.isEmpty(classTimeTableDataBean.getMonth())) {
			logger.debug("month - " + classTimeTableDataBean.getMonth());
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("monthAP").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Month"));
			}
			valid = false;
		}
		
		if (StringUtils.isEmpty(classTimeTableDataBean.getSubject())) {
			logger.debug("subject - " + classTimeTableDataBean.getSubject());
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("subjectts1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject"));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(classTimeTableDataBean.getSubjectCode())) {
			logger.debug("subject - " + classTimeTableDataBean.getSubjectCode());
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("subjectcode1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject Code"));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(classTimeTableDataBean.getTeaID())) {
			logger.debug("tea id - " + classTimeTableDataBean.getTeaID());
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("teaid1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Teacher ID"));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getStartTime() == null) {
			logger.debug("start time - " + classTimeTableDataBean.getStartTime());
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("startTimes1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Start Time"));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getEndTime() == null) {
			logger.debug("end time - " + classTimeTableDataBean.getEndTime());
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("endTimes1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select End Time"));
			}
			valid = false;
		}
		return valid;
	}

	public String extraPeriodInsert() {
		logger.debug("insert extra class period ");
		String personID = "";
		addflag = true;
		try {
			String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				String status = controller.insertExtraPeriod(personID, classTimeTableDataBean);
				logger.debug("status - " + status);
				if (status.equalsIgnoreCase("success")) {
					MailSendJDBC.classPeriodAdd(classTimeTableDataBean, schoolName);
					/*String emailStatus = sendEmail1(classTimeTableDataBean);
					if (emailStatus.equalsIgnoreCase("Success")) {*/
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('classupdblocksUI').hide();");
						reqcontext.execute("PF('extraperiod').show();");
						boxflag1= true;
					/*} else {
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('classupdblocksUI').hide();");
						reqcontext.execute("PF('extracls').hide();");
						boxflag = false;
					}*/
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return null;
	}

	public void classChange(ValueChangeEvent v) {
		logger.debug("class change -  " + v.getNewValue());
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				classTimeTableDataBean.setClassname(v.getNewValue().toString());
				controller.classChange(personID, classTimeTableDataBean);
				Collections.sort(classTimeTableDataBean.getSubjectlist());
				logger.debug("subjects - " + classTimeTableDataBean.getSubjectlist());
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}
		public void subjectChange(ValueChangeEvent v) {
		    logger.debug("subject change -  " + v.getNewValue());
		    logger.debug("----------------subjectChange  method ValueChangeEvent-----------------");
		    try {
		    	if (!v.getNewValue().toString().equalsIgnoreCase("") || v.getNewValue().toString()!=null){
		     ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		      SmsController controller = (SmsController) ctx.getBean("controller");
		     String personID = "";
		     teacherIDList=null;
		     personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
		     teacherIDList=new ArrayList<String>();
		     if (personID != null) {
		      classTimeTableDataBean.setSubject(v.getNewValue().toString());
		      teacherIDList=controller.subjectChange(personID, classTimeTableDataBean);		      
		      logger.debug("subject code ------>"+classTimeTableDataBean.getSubjectCodes());
		      if(classTimeTableDataBean.getSubjectcodelist().size()>1){
					logger.debug("> 1");
					teacherIDList=new ArrayList<String>();
					setCodeflag1(true);setCodeflag(false);
				}else if(classTimeTableDataBean.getSubjectcodelist().size()==1){
					logger.debug("1");
					classTimeTableDataBean.setSubjectCodes(classTimeTableDataBean.getSubjectcodelist().get(0));
					classTimeTableDataBean.setSubjectCode(classTimeTableDataBean.getSubjectcodelist().get(0));
					setCodeflag1(false);setCodeflag(true);
				}	
		      logger.debug("teacherid size------>"+teacherIDList.size());
		     }
		    }
		    } catch (Exception e) {
		     logger.warn("Exception -->"+e.getMessage());
		    } finally {

		    }
		 }


		public void subjectCodeChange(ValueChangeEvent v) {
			logger.debug("subject code change -  " + v.getNewValue());
			classTimeTableDataBean.getTeacherIDList().clear();
			try {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				SmsController controller = (SmsController) ctx.getBean("controller");
				String personID = "";
				personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
				if (personID != null) {
					teacherIDList = new ArrayList<String>();
					classTimeTableDataBean.setSubjectCode(v.getNewValue().toString());
					teacherIDList=controller.subjectCodeChange(personID, classTimeTableDataBean);
					logger.debug("teacher size "+teacherIDList);
					if(classTimeTableDataBean.getSubjectcodelist().size()>1){
						logger.debug("> 1");
						setCodeflag1(true);setCodeflag(false);
					}else if(classTimeTableDataBean.getSubjectcodelist().size()==1){
						logger.debug("1");
						setCodeflag1(false);setCodeflag(true);
					}				
					classTimeTableDataBean.setSubjectCode(v.getNewValue().toString());
					classTimeTableDataBean.setTeacherIDList(teacherIDList);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.warn("Exception -->"+e.getMessage());
			} finally {

			}
		}
		
	public String checkClass() {
		logger.debug("check");
		String personID = "";
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		addflag = true;
		try {
			if (check(true)) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				SmsController controller = (SmsController) ctx.getBean("controller");
				personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
				if (personID != null) {
					String status = controller.checkExtraClass(personID, classTimeTableDataBean);
					logger.debug("status - " + status);
					if (status.equalsIgnoreCase("fail")) {
						fieldName = CommonValidate.findComponentInRoot("validates").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("In this time the class already has a period"));
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('classupdblocksUI').hide();");
					} else if (status.equalsIgnoreCase("no")) {
						fieldName = CommonValidate.findComponentInRoot("validates").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(
								"This Class has no time table for " + classTimeTableDataBean.getDay()));
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('classupdblocksUI').hide();");
					} else {
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("extraclsBlk()");
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return null;
	}

	public boolean check(boolean flag) {
		String fieldName;
		boolean valid = true;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (classTimeTableDataBean.getDate() == null) {
			logger.debug("date - " + classTimeTableDataBean.getDate());
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("date").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Date"));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(classTimeTableDataBean.getClassname())) {
			logger.debug("class - " + classTimeTableDataBean.getClassname());
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("classz").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Class"));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(classTimeTableDataBean.getSubject())) {
			logger.debug("subject - " + classTimeTableDataBean.getSubject());
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("subjectts").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject"));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(classTimeTableDataBean.getSubjectCode())) {
			logger.debug("subject code- " + classTimeTableDataBean.getSubjectCode());
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("subjectcode").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject Code"));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(classTimeTableDataBean.getTeaID())) {
			logger.debug("tea id - " + classTimeTableDataBean.getTeaID());
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("teaid").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Teacher ID"));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getStartTime() == null) {
			logger.debug("start time - " + classTimeTableDataBean.getStartTime());
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("startTimes").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Start Time"));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getEndTime() == null) {
			logger.debug("end time - " + classTimeTableDataBean.getEndTime());
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("endTimes").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select End Time"));
			}
			valid = false;
		}
		return valid;
	}

	public String extraClassInsert() {
		logger.debug("insert extra class ");
		String personID = "";
		addflag = true;
		try {
			String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				String status = controller.insertExtraClass(personID, classTimeTableDataBean);
				logger.debug("status - " + status);
				if (status.equalsIgnoreCase("success")) {
					MailSendJDBC.classTimeTableAdd(classTimeTableDataBean, schoolName,schoolid);
					/*String emailStatus = sendEmail1(classTimeTableDataBean);
					if (emailStatus.equalsIgnoreCase("Success")) {*/
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('classupdblocksUI').hide();");
						reqcontext.execute("PF('extracls').show();");
						boxflag = true;
					/*} else {
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('classupdblocksUI').hide();");
						reqcontext.execute("PF('extracls').hide();");
						boxflag = false;
					}*/
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return null;
	}

	public void checkStart(ValueChangeEvent v) {
		logger.debug("start time -- " + v.getNewValue());
		classTimeTableDataBean.setStartTime((Date) v.getNewValue());
	}

	public void checkEnd(ValueChangeEvent v) {
		logger.debug("end time -- " + v.getNewValue());
		classTimeTableDataBean.setEndTime((Date) v.getNewValue());
	}

	/*private String sendEmail1(ClassTimeTableDataBean classTimeTableDataBean) {
		String status = "Fail";
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("schoolName");
		try {
			InternetAddress[] toAddress = new InternetAddress[classTimeTableDataBean.getMails().size()];
			for (int i = 0; i < classTimeTableDataBean.getMails().size(); i++) {
				toAddress[i] = new InternetAddress(classTimeTableDataBean.getMails().get(i));
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
					+ "<h3>Dear </h3>"
					+ "<p>Welcome on-board into "
					+ schoolName
					+ "family. Please find the you have extra class for "
					+ classTimeTableDataBean.getSubject() + " , at " + classTimeTableDataBean.getTimeStart() + " to "
					+ classTimeTableDataBean.getTimeEnd() + " on " + format.format(classTimeTableDataBean.getDate())
					+ " ." + "</p>" + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>"
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
				message.addRecipients(Message.RecipientType.TO, toAddress);
				 message.addRecipients(Message.RecipientType.CC, myCcList); 
				message.setSubject("Extra Class Time ");
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);
				Transport.send(message);

				logger.info("message sent successfully");
			}

			catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
			status = "Success";

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		} finally {

		}
		return status;
	}*/

	public String returnPage() {
		addflag = false;
		boxflag = false;
		boxflag1 = false;
		flag = false;
		return "";
	}
	
	public void subChangeListener1(ValueChangeEvent v) {
		try{
			logger.debug("subChangeListener Calling ");
			logger.debug(v.getNewValue().toString());
			
		}catch(Exception e){
			logger.warn("Inside Exception",e);
		}
	}
	public void teacherChange(ValueChangeEvent v)  {
		classTimeTableDataBean.setTeaID(v.getNewValue().toString());
	}
	public void subChange(ValueChangeEvent v)  {
		String personID = "";
		teacherIDList=null;
		try{
			if (!v.getNewValue().toString().equalsIgnoreCase("") || v.getNewValue().toString()!=null) {
			logger.debug("subChangeListener Calling ");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			teacherIDList=new ArrayList<String>();
			if (personID != null) {
			classTimeTableDataBean.setSubject(v.getNewValue().toString());
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			teacherIDList=controller.subjectChange(personID, classTimeTableDataBean);			
			logger.debug(teacherIDList.size());
			classTimeTableDataBean.setSubject("");
			if(classTimeTableDataBean.getSubjectcodelist().size()>1){
				logger.debug("> 1");
				teacherIDList=new ArrayList<String>();
				setCodeflag1(true);setCodeflag(false);
			}else if(classTimeTableDataBean.getSubjectcodelist().size()==1){
				logger.debug("1");
				setCodeflag1(false);setCodeflag(true);
				classTimeTableDataBean.setSubjectCode(classTimeTableDataBean.getSubjectcodelist().get(0));
			}
		
			}
			}
		}catch(Exception e){
			logger.warn("Inside Exception",e);
		}
	}
	public String update() {
		try{
		if (classTimeTableDataBean.getSubject().equalsIgnoreCase("Select") || classTimeTableDataBean.getSubject().equalsIgnoreCase("Select")) {
			logger.debug("1");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Required", "Please Choose the Subject.");
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		}else if (classTimeTableDataBean.getSubjectCode().equalsIgnoreCase("Select") || classTimeTableDataBean.getSubjectCode().equalsIgnoreCase("")) {
			logger.debug("su1");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Required", "Please Choose the Subject Code.");
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		}else if(classTimeTableDataBean.getTeaID().equalsIgnoreCase("Select1") || classTimeTableDataBean.getTeaID().equalsIgnoreCase("")){
			logger.debug("2");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Required", "Please Choose the Teacher ID.");
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		}else if(classTimeTableDataBean.getStartTime() == null || classTimeTableDataBean.getStartTime().equals("")){
			logger.debug("inside --");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Required", "Please Select the Start Time.");
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		}else if(classTimeTableDataBean.getEndTime() == null || classTimeTableDataBean.getEndTime().equals("")){
			logger.debug("inside --");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Required", "Please Select the End Time.");
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		}else{
			RequestContext reqcontextt = RequestContext.getCurrentInstance();
			reqcontextt.execute("PF('classDialog').hide()");
			reqcontextt.execute("classupdateBlk();");
			
			logger.debug("Update"+classTimeTableDataBean.getSubjects()+classTimeTableDataBean.getSubject());
		}
		}catch(Exception e){
			logger.warn("Inside Exception",e);
		}
		return "";
		
	}
	public List<String> getTeacherIDList() {
		return teacherIDList;
	}

	public void setTeacherIDList(List<String> teacherIDList) {
		this.teacherIDList = teacherIDList;
	}

	public ClassTimeTableDataBean getSelectedClassTable() {
		return selectedClassTable;
	}

	public void setSelectedClassTable(ClassTimeTableDataBean selectedClassTable) {
		this.selectedClassTable = selectedClassTable;
	}
	
	public void editTimeTable(ClassTimeTableDataBean databean) {
		logger.debug("----------------edit Time Table  method-----------------");
		teacherIDList=null;DateFormat df=new SimpleDateFormat("hh:mm");
		setCodeflag(true);setCodeflag1(false);
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			teacherIDList=new ArrayList<String>();
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				logger.debug("classTimeTableDataBean"+classTimeTableDataBean.getClassname());
				controller.classChange(personID, classTimeTableDataBean);
				Collections.sort(classTimeTableDataBean.getSubjectlist());
				logger.debug("subjects - " + classTimeTableDataBean.getSubjectlist());
				classTimeTableDataBean.setSubject(databean.getSubject());
				teacherIDList=controller.subjectChange(personID, classTimeTableDataBean);
				classTimeTableDataBean.setTeaID(databean.getTeaID());
				classTimeTableDataBean.setPeriod(databean.getPeriod());
				classTimeTableDataBean.setSubject(databean.getSubject());
				classTimeTableDataBean.setSubjectCode(databean.getSubjectCode());
				classTimeTableDataBean.setStartTime(df.parse(databean.getTimeStart()));
				classTimeTableDataBean.setEndTime(df.parse(databean.getTimeEnd()));
				classTimeTableDataBean.setSubjects(databean.getSubject());
				classTimeTableDataBean.setClasstableid(databean.getClasstableid());
				
			}else{
				classTimeTableDataBean.setTeaID("");
				classTimeTableDataBean.setPeriod("");
				classTimeTableDataBean.setSubject("");
				classTimeTableDataBean.setSubjectCode("");
				classTimeTableDataBean.setStartTime(null);
				classTimeTableDataBean.setEndTime(null);
				classTimeTableDataBean.setSubjects("");
				classTimeTableDataBean.setClasstableid(0);
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}
	public void refreshTable() {
		
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role").equals("Parent")
					|| FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
							.equals("Admin")) {
				if (validate(true)) {
					if (personID != null) {
						
						controller.classTimeTableView(classTimeTableDataBean, personID);
						if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
								.equals("Parent"))
							editflag = false;
						else if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
								.equals("Admin"))
							editflag = true;
						flag = true;
					}
				}
			} else {
				if (personID != null) {
					
					controller.classTimeTableView(classTimeTableDataBean, personID);
					flag = true;
					editflag = false;
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {
			addflag = false;
			boxflag = false;
			boxflag1 = false;
			updateflag=false;
			updateflag1=false;
			updateflag2=false;
		}
	}
	public String deleteclass(){
		String status="Fail";
		try{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				status=controller.classTimeTableDelete(classTimeTableDataBean, personID);
				if(status.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("PF('deleteConfirmDlg').hide();");
					RequestContext.getCurrentInstance().execute("PF('deleteclassDialog').show();");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	
	public void tableupdate(RowEditEvent event) {
		logger.debug("inside tableupdate row edit Event event");
		
		try {
			classTimeTableDataBean.setClasstableid(((ClassTimeTableDataBean) event.getObject()).getClasstableid());
			classTimeTableDataBean.setPeriod(((ClassTimeTableDataBean) event.getObject()).getPeriod());
			classTimeTableDataBean.setSubject(((ClassTimeTableDataBean) event.getObject()).getSubject());
			classTimeTableDataBean.setSubjectCode(((ClassTimeTableDataBean) event.getObject()).getSubjectCode());
			classTimeTableDataBean.setTeaID(((ClassTimeTableDataBean) event.getObject()).getTeaID());
			classTimeTableDataBean.setStartTime(((ClassTimeTableDataBean) event.getObject()).getStartTime());
			classTimeTableDataBean.setEndTime(((ClassTimeTableDataBean) event.getObject()).getEndTime());


			try{
			if (classTimeTableDataBean.getSubject().equalsIgnoreCase("Select") || classTimeTableDataBean.getSubject().equalsIgnoreCase("")) {
				logger.debug("1");
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Required", "Please Choose the Subject.");
		        RequestContext.getCurrentInstance().showMessageInDialog(message);
			}else if (classTimeTableDataBean.getSubjectCode().equalsIgnoreCase("Select") || classTimeTableDataBean.getSubjectCode().equalsIgnoreCase("")) {
				logger.debug("su1");
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Required", "Please Choose the Subject Code.");
		        RequestContext.getCurrentInstance().showMessageInDialog(message);
			}else if(classTimeTableDataBean.getTeaID().equalsIgnoreCase("Select1") || classTimeTableDataBean.getTeaID().equalsIgnoreCase("")){
				logger.debug("2");
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Required", "Please Choose the Teacher ID.");
		        RequestContext.getCurrentInstance().showMessageInDialog(message);
			}else if(classTimeTableDataBean.getStartTime() == null || classTimeTableDataBean.getStartTime().equals("")){
				logger.debug("inside --");
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Required", "Please Select the Start Time.");
		        RequestContext.getCurrentInstance().showMessageInDialog(message);
			}else if(classTimeTableDataBean.getEndTime() == null || classTimeTableDataBean.getEndTime().equals("")){
				logger.debug("inside --");
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Required", "Please Select the End Time.");
		        RequestContext.getCurrentInstance().showMessageInDialog(message);
			}else{
				RequestContext reqcontextt = RequestContext.getCurrentInstance();
				reqcontextt.execute("PF('classDialog').hide()");
				reqcontextt.execute("classupdateBlk();");
				
				logger.debug("Update"+classTimeTableDataBean.getSubjects()+classTimeTableDataBean.getSubject());
			}
			}catch(Exception e){
				logger.warn("Inside Exception",e);
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
	}

	public void subjectChangeNew(ValueChangeEvent v) {
		logger.debug("subject change -  " + v.getNewValue());
		System.out.println("----------------subjectChangeNew  method ValueChangeEvent-----------------");
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String subject="";
		String period="";
		String serialno="";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			subject = v.getNewValue().toString();
			if (!subject.equalsIgnoreCase("") || subject!=null) {
			period = v.getComponent().getAttributes().get("period").toString();
			serialno=v.getComponent().getAttributes().get("serialno").toString();
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				teacherIDList = new ArrayList<String>();
				classTimeTableDataBean.setTeacherIDList(new ArrayList<String>());
				classTimeTableDataBean.setSubject(subject);
				teacherIDList=controller.subjectChange(personID, classTimeTableDataBean);
				logger.debug("code "+classTimeTableDataBean.getSubjectcodelist().size()+" - "+classTimeTableDataBean.getSubjectcodelist());
				/*if(classTimeTableDataBean.getSubjectcodelist().size()>1){
					logger.debug("> 1");
					setCodeflag1(true);setCodeflag(false);
				}else*/ 
					logger.debug("1");
					/*classTimeTableDataBean.setSubjectCodes(classTimeTableDataBean.getSubjectcodelist().get(0));
					classTimeTableDataBean.setSubjectCode(classTimeTableDataBean.getSubjectcodelist().get(0));
					classTimeTableDataBean.setTeacherIDList(teacherIDList);*/
					ClassTimeTableDataBean obj=new ClassTimeTableDataBean();
					obj.setPeriod(period);
					obj.setSubject(subject);
					obj.setSubjectCode(classTimeTableDataBean.getSubjectcodelist().get(0));
					obj.setTeacherIDList(teacherIDList);
					obj.setSerialno(Integer.parseInt(v.getComponent().getAttributes().get("serialno").toString()));
					obj.setClasstableid(Integer.parseInt(v.getComponent().getAttributes().get("classtableid").toString()));
					obj.setTeaID(v.getComponent().getAttributes().get("teaID").toString());
					System.out.println("----------->"+(Date)v.getComponent().getAttributes().get("startTime"));
					obj.setStartTime(((Date)v.getComponent().getAttributes().get("startTime")));
					obj.setEndTime(((Date)v.getComponent().getAttributes().get("startTime")));

					classTimeTableDataBean.getClasstimeList().set(Integer.parseInt(serialno) - 1, obj);
					
				/*	setCodeflag1(false);setCodeflag(true);
					
				setSubjectList(null);
				setSubjectList(subjectLists);
				classTimeTableDataBean.setSubject(v.getNewValue().toString());*/
				
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}
	
	
}
