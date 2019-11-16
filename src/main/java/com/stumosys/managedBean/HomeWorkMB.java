package com.stumosys.managedBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
/*import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
*/import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
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
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.stumosys.controler.SmsController;
//import com.stumosys.domain.AttendanceDataBean;
import com.stumosys.domain.HomeworkDatabean;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name = "homeWorkMB")
public class HomeWorkMB {
	private static Logger logger = Logger.getLogger(TimeTableMB.class);
	SmsController controller = null;
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	HomeworkDatabean homeworkDatabean = new HomeworkDatabean();
	public List<String> classList = null;
	public List<String> subjectList = null;
	public boolean boxflag = false;
	public boolean classflag = false;
	public boolean flag = false;
	public boolean actionflag = false;
	private List<HomeworkDatabean> filteredstudent;
	public List<HomeworkDatabean> getFilteredstudent() {
		return filteredstudent;
	}

	public void setFilteredstudent(List<HomeworkDatabean> filteredstudent) {
		this.filteredstudent = filteredstudent;
	}

	public boolean isActionflag() {
		return actionflag;
	}

	public void setActionflag(boolean actionflag) {
		this.actionflag = actionflag;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public boolean isClassflag() {
		return classflag;
	}

	public void setClassflag(boolean classflag) {
		this.classflag = classflag;
	}

	public boolean isBoxflag() {
		return boxflag;
	}

	public void setBoxflag(boolean boxflag) {
		this.boxflag = boxflag;
	}

	public List<String> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<String> subjectList) {
		this.subjectList = subjectList;
	}

	public List<String> getClassList() {
		return classList;
	}

	public void setClassList(List<String> classList) {
		this.classList = classList;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		HomeWorkMB.logger = logger;
	}

	public HomeworkDatabean getHomeworkDatabean() {
		return homeworkDatabean;
	}

	public void setHomeworkDatabean(HomeworkDatabean homeworkDatabean) {
		this.homeworkDatabean = homeworkDatabean;
	}

	public String homeworkcall() {
		logger.debug("home work call ");
		String personID = "";homeworkDatabean.setClassname("");
		homeworkDatabean.setSubject("");homeworkDatabean.setHomework("");
		String status="homework";
		List<String> classnameList=null;
		try {
			classnameList=new ArrayList<String>();
			classList=new ArrayList<String>();
			subjectList=new ArrayList<String>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				 classList = controller.getClassNameList(personID);
				 classnameList = controller.classNamesList(personID,status);
				if(classnameList.size()>0){
					homeworkDatabean.setClassname(classnameList.get(0));
					homeworkDatabean.setSubject(classnameList.get(1));
					subjectList = controller.classSubjectList(homeworkDatabean, personID);
				}				
				Collections.sort(classList);
				return "addHomeWork";
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
			e.printStackTrace();
		} finally {			
			setBoxflag(false);
		}
		return "";
	}

	public void classSelect(ValueChangeEvent v) {
		logger.info("-----------Inside classSelect method()----------------");
		//logger.debug("class -- " + v.getNewValue());
		String personID = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			homeworkDatabean.setClassname(v.getNewValue().toString());
			if (personID != null) {
				subjectList = controller.classSubjectList(homeworkDatabean, personID);
				Collections.sort(subjectList);
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
	}

	public String workInsert() {
		logger.info("-----------Inside workInsert method()----------------");
		String personID = "";
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			if (validate(true)) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
				if (personID != null) {
					String status = controller.checkHomeWork(homeworkDatabean, personID);
					logger.debug("status -- " + status);
					if (status.equalsIgnoreCase("success")) {
						logger.debug("inside success ");
						RequestContext reqcontextt1 = RequestContext.getCurrentInstance();
						reqcontextt1.execute("workBlk()");
					} else {
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('homeWorkBlockUI').hide();");
						reqcontext.execute("PF('homeWorkdialog').hide();");
						fieldName = CommonValidate.findComponentInRoot("valid").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Already given work for today"));
						setBoxflag(false);
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return "";
	}

	public String homeWorkSave() {
		logger.info("-----------Inside homeWorkSave method()----------------");
		String personID = "";
		try {
			String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			if (personID != null) {
				String status = controller.homeWorkInsert(homeworkDatabean, personID);
				if (status.equalsIgnoreCase("Success")) {
					MailSendJDBC.homeworkInsert(homeworkDatabean,schoolName,schoolid);
					/*String emailStatus = sendEmail(homeworkDatabean);
					if (emailStatus.equalsIgnoreCase("Success")) {*/
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('homeWorkBlockUI').hide();");
						reqcontext.execute("PF('homeWorkdialog').show();");
						setBoxflag(true);
					
				}else{
					RequestContext reqcontext = RequestContext.getCurrentInstance();
					reqcontext.execute("PF('homeWorkBlockUI').hide();");
					reqcontext.execute("PF('homeWorkdialog').hide();");
					setBoxflag(false);
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		} finally {

		}
		return "";
	}

	public boolean validate(boolean flag) {
		logger.info("-----------Inside validate method()----------------");
		String fieldName;
		boolean valid = true;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (StringUtils.isEmpty(homeworkDatabean.getClassname())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("classz").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Class"));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(homeworkDatabean.getSubject())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("subjectt").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject"));
			}
			valid = false;
		}
		
		return valid;
	}

	

	public String reset() {
		logger.info("-----------Inside reset method()----------------");
		homeworkDatabean.setClassname("");
		homeworkDatabean.setHomework("");
		homeworkDatabean.setSubject("");
		setBoxflag(false);
		return "";
	}

	public String homeworkViewCall() {
		logger.info("-----------Inside homeworkViewCall method()----------------");
		String personID = "";
		String status="homeworkview";
		try {
			homeworkDatabean.setClassnameList(new ArrayList<String>());
			homeworkDatabean.setDate(null);
			homeworkDatabean.setClassname("");
			classList=new ArrayList<String>();
			boxflag=false;
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
						.equals("Student")) {
					classflag = false;
				} else {
					classList = controller.getClassNameList(personID);
					Collections.sort(classList);
					classflag = true;
					if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role").equals("Teacher"))
						actionflag=true;
					else
						actionflag=false;
				}
				homeworkDatabean.setClassnameList(classList);
				controller.classChange(homeworkDatabean, personID);
				flag=true;
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		} finally {
			homeworkDatabean.setDate(null);
			homeworkDatabean.setClassname("");
		}
		return "homeWorkView";
	}

	public void dateChange(ValueChangeEvent v) {
		logger.info("-----------Inside dateChange method()----------------");
		String personID = "";
		flag = false;
		actionflag = false;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			homeworkDatabean.setDate((Date) v.getNewValue());
			if (personID != null) {
				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
						.equals("Student")) {
					controller.classChange(homeworkDatabean, personID);
					flag = true; 
				} else {
					flag = false;
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
	}

	public void classChange() {
		logger.info("----Inside classChange method()--------------);// -- " + homeworkDatabean.getClassname());
		String personID = "";
		flag = false;
		String datee = "";
		String hwdatee = "";
		boxflag = false;
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				if(searchValidation(true)){
				controller.classChange(homeworkDatabean, personID);
				datee = format.format(date);
				hwdatee = format.format(homeworkDatabean.getDate());
				logger.debug(" -- " + datee + " - " + hwdatee);
				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
						.equals("Teacher")) {
					if (datee.equals(hwdatee))
						actionflag = true;
					else if (!datee.equals(hwdatee))
						actionflag = false;
				} else {
					actionflag = false;
				}
				flag = true;
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
	}

	private boolean searchValidation(boolean valid) {
logger.info("-----------Inside searchValidation method()----------------");
			
	valid=true;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(homeworkDatabean.getDate()==null){
			fc.addMessage(CommonValidate.findComponentInRoot("date").getClientId(), new FacesMessage("Please Choose the Date"));
			valid=false;
		}
		if(StringUtils.isEmpty(homeworkDatabean.getClassname())){
			fc.addMessage(CommonValidate.findComponentInRoot("class").getClientId(), new FacesMessage("Please Choose the ClassName"));
			valid=false;
		}
		return valid;
	}

	public void workupdate(RowEditEvent event) {
		logger.info("-----------Inside workupdate method()----------------");
		String personID = "";
		try {
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				logger.debug("update ");
				homeworkDatabean.setSubject(((HomeworkDatabean) event.getObject()).getSubject().toString());
				homeworkDatabean.setHomework(((HomeworkDatabean) event.getObject()).getHomework().toString());
				homeworkDatabean.setSno(((HomeworkDatabean) event.getObject()).getSno());
				RequestContext reqcontextt = RequestContext.getCurrentInstance();
				reqcontextt.execute("WorkUpdateBlk();");
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
	}

	public String onRowEdit() {
		logger.info("-----------Inside onRowEdit method()----------------");
		String personID = "";
		try {
			String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			if (personID != null) {
				controller.updateHomeWork(homeworkDatabean, personID);
				MailSendJDBC.homeworkUpdate(homeworkDatabean,schoolName,schoolid);
				/*String mailStatus = sendEmail1(homeworkDatabean);
				if (mailStatus.equalsIgnoreCase("Success")) {*/
					HomeworkDatabean list = new HomeworkDatabean();
					list.setSno(homeworkDatabean.getSno());
					list.setSubject(homeworkDatabean.getSubject());
					list.setHomework(homeworkDatabean.getHomework());
					homeworkDatabean.getWorklist().set(homeworkDatabean.getSno() - 1, list);
					RequestContext reqcontext = RequestContext.getCurrentInstance();
					reqcontext.execute("PF('homeWorkUpdateBlock').hide();");
					reqcontext.execute("PF('updatework').show();");
				/*}*/
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
		return "";
	}

	public void onRowCancel(RowEditEvent event) {
		logger.info("-----------Inside onRowCancel method()----------------");
		FacesMessage msg = new FacesMessage("Edit Cancelled");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	

	public String workDelete() {
		logger.info("-----------Inside workDelete method()----------------");
		String personID = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				controller.deleteHomeWork(homeworkDatabean, personID);
				boxflag = true;
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
		return "";
	}

	public String returnView() {
		logger.info("-----------Inside returnView method()----------------");
		String personID = "";
		flag = false;
		boxflag = false;
		homeworkDatabean.setClassname("");
		homeworkDatabean.setDate(null);
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				controller.classChange(homeworkDatabean, personID);
				logger.debug("lsit size - " + homeworkDatabean.getWorklist().size());
				flag = true;
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return "";
	}
	
}
