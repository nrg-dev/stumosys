package com.stumosys.managedBean;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.mail.BodyPart;
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
import com.stumosys.domain.TimeTableDataBean;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name = "timeTableViewMB")
@RequestScoped
public class TimeTableViewMB {
	TimeTableDataBean timeTableDataBean = new TimeTableDataBean();
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	Date now = new Date();
	private List<String> classList;
	private List<String> examList;
	private boolean flag = false;
	private List<TimeTableDataBean> filteredExam;
	private boolean boxflag = false;
	private boolean flag0 = false;
	private List<String> studentList;
	private boolean stuflag = false;
	private String stuflag1 = "none";
	private String role;
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	private boolean checkflag = false;
	private boolean checkflagz = false;
	private List<TimeTableDataBean> marksList=null;

	
	public List<TimeTableDataBean> getMarksList() {
		return marksList;
	}

	public void setMarksList(List<TimeTableDataBean> marksList) {
		this.marksList = marksList;
	}

	public boolean isCheckflag() {
		return checkflag;
	}

	public void setCheckflag(boolean checkflag) {
		this.checkflag = checkflag;
	}

	public boolean isCheckflagz() {
		return checkflagz;
	}

	public void setCheckflagz(boolean checkflagz) {
		this.checkflagz = checkflagz;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStuflag1() {
		return stuflag1;
	}

	public void setStuflag1(String stuflag1) {
		this.stuflag1 = stuflag1;
	}

	public boolean isStuflag() {
		return stuflag;
	}

	public void setStuflag(boolean stuflag) {
		this.stuflag = stuflag;
	}

	public List<String> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<String> studentList) {
		this.studentList = studentList;
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

	public boolean isFlag0() {
		return flag0;
	}

	public void setFlag0(boolean flag0) {
		this.flag0 = flag0;
	}

	public boolean isBoxflag() {
		return boxflag;
	}

	public void setBoxflag(boolean boxflag) {
		this.boxflag = boxflag;
	}

	public List<TimeTableDataBean> getFilteredExam() {
		return filteredExam;
	}

	public void setFilteredExam(List<TimeTableDataBean> filteredExam) {
		this.filteredExam = filteredExam;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public List<String> getExamList() {
		return examList;
	}

	public void setExamList(List<String> examList) {
		this.examList = examList;
	}

	public List<String> getClassList() {
		return classList;
	}

	public void setClassList(List<String> classList) {
		this.classList = classList;
	}

	/**
	 * @return the timeTableDataBean
	 */
	public TimeTableDataBean getTimeTableDataBean() {
		return timeTableDataBean;
	}

	/**
	 * @param timeTableDataBean
	 *            the timeTableDataBean to set
	 */
	public void setTimeTableDataBean(TimeTableDataBean timeTableDataBean) {
		this.timeTableDataBean = timeTableDataBean;
	}

	private static Logger logger = Logger.getLogger(TimeTableViewMB.class);
	SmsController controller = null;

	public String timeTableView() {
		List<String> splitList=null;
		try {
			flag = false;
			String personID;
			logger.info("------timeTableView method calling------");
			timeTableDataBean.setExamTitle("");
			timeTableDataBean.setExamClass("");
			timeTableDataBean.setTablelist(null);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			role = "" + FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role");
			if (personID != null) {
				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
						.equals("Student")) {
					examList = controller.getexamViewList(personID, timeTableDataBean);
					Collections.sort(examList);
					stuflag = false;
					stuflag1 = "none";
					return "timeTableViewStudent";
				}
				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
						.equals("Parent")) {
					studentList = controller.getstudentList(personID, timeTableDataBean);
					Collections.sort(studentList);
					stuflag = true;
					stuflag1 = "1";
					return "timeTableViewStudent";
				} else {
					if (role.equals("Admin")) {
						flag0 = true;
					} else {
						flag0 = false;
					}
					classList = controller.getClassList(personID);
					if(schoolID.equalsIgnoreCase(paths.getString("SMRV.SCHOOLID"))){
						splitList=new ArrayList<String>();
						for (int i = 0; i < classList.size(); i++) {
							splitList.add(classList.get(i).split("/")[0]);
						}
						classList.clear();
						Set<String> dublicate=new HashSet<String>(splitList);
						splitList.clear();splitList.addAll(dublicate);
						classList.addAll(splitList);
					}
					Collections.sort(classList);
					controller.examRecords(timeTableDataBean, personID);
					return "allexamInfo";
				}
			}
		} catch (Exception e) {
			logger.info("-----timeTableView method Exception calling------");
			logger.warn("Exception -->"+e.getMessage());
		} finally {
			timeTableDataBean.setExamClass("");
			timeTableDataBean.setExamTitle("");
			timeTableDataBean.setStudentID(null);
			boxflag = false;
		}
		return "";
	}

	public boolean validate(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (StringUtils.isEmpty(timeTableDataBean.getExamClass())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TimeViewClass").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Class."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(timeTableDataBean.getExamTitle())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TimeViewTitle").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Exam Title."));
			}
			valid = false;
		}
		return valid;
	}

	public String reset() {

		timeTableDataBean.setExamClass("");
		timeTableDataBean.setExamTitle("");
		flag = false;
		return "";
	}

	public void classExam(ValueChangeEvent v) {
		logger.debug("class -- > " + v.getNewValue());
		setExamList(null);
		flag = false;
		try {
			timeTableDataBean.setTablelist(null);
			String personID;
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				timeTableDataBean.setExamClass(v.getNewValue().toString());
				examList = controller.getexamList(personID, timeTableDataBean);
				logger.debug("exam list -- > " + examList);
				Collections.sort(examList);
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {
			timeTableDataBean.setExamTitle("");
		}
	}

	public String examView(TimeTableDataBean timeTable) {
		String personID;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				timeTableDataBean.setExamTitle(timeTable.getExamTitle());
				timeTableDataBean.setExamClass(timeTable.getExamClass());
				timeTableDataBean.setExamStartDate(timeTable.getExamStartDate());
				timeTableDataBean.setExamDay(timeTable.getExamDay());
				timeTableDataBean.setExamSubject(timeTable.getExamSubject());
				timeTableDataBean.setExamSubjectCode(timeTable.getExamSubjectCode());
				timeTableDataBean.setExamRoomNo(timeTable.getExamRoomNo());
				timeTableDataBean.setStime(timeTable.getStime());
				timeTableDataBean.setEtime(timeTable.getEtime());
				timeTableDataBean.setExamtTotalTime(timeTable.getExamtTotalTime());
				timeTableDataBean.setExamtableId(timeTable.getExamtableId());
				//controller.getexamRecordsList(personID, timeTableDataBean);
			}
			return "";
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			return "";
		} finally {

		}
	}

	public String examEdit(TimeTableDataBean timeTable) {
		logger.info("exam edit -- > ");
		String personID;
		boxflag = false;
		DateFormat df = null;//new SimpleDateFormat("HH:mm");
		try {
			df = new SimpleDateFormat("HH:mm");

			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				timeTableDataBean.setExamTitle(timeTable.getExamTitle());
				timeTableDataBean.setExamClass(timeTable.getExamClass());
				timeTableDataBean.setExamStartDate(timeTable.getExamStartDate());
				timeTableDataBean.setExamDay(timeTable.getExamDay());
				timeTableDataBean.setExamSubject(timeTable.getExamSubject());
				timeTableDataBean.setExamSubjectCode(timeTable.getExamSubjectCode());
				timeTableDataBean.setExamRoomNo(timeTable.getExamRoomNo());
				timeTableDataBean.setExamStartTime(df.parse(timeTable.getStime()));
				timeTableDataBean.setExamEndTime(df.parse(timeTable.getEtime()));
				timeTableDataBean.setExamtTotalTime(timeTable.getExamtTotalTime());
				timeTableDataBean.setExamtableId(timeTable.getExamtableId());
				//controller.getexamEditList(personID, timeTableDataBean);
				controller.subjectValues(timeTableDataBean, personID);
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}

	public void subjectcode(ValueChangeEvent vv) {
		logger.debug("subject -1- > " + vv.getNewValue());
		String personID = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			logger.debug("person id -- " + personID);
			timeTableDataBean.setExamSubject(vv.getNewValue().toString());
			if (personID != null) {
				controller.changeSubjectCode(personID, timeTableDataBean);
				timeTableDataBean.setExamSubjectCode(timeTableDataBean.getExamSubjectCodes());
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	public void shiftChange(ValueChangeEvent v) {
		logger.debug("shift -- > " + v.getNewValue());
		try {
			timeTableDataBean.setExamShift(v.getNewValue().toString());
			if (validate4(true, v)) {

			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	public boolean validate4(boolean flag, ValueChangeEvent v) {
		boolean valid = true;
		String fieldName;
		String starthours = "", starthour = "", endhour = "";
		;
		String startmin = "";
		String starttime = "";
		String endtime = "", time = "";
		;
		FacesContext fc = FacesContext.getCurrentInstance();
		starthour = String.valueOf(timeTableDataBean.getExamStartTime().getHours());
		endhour = String.valueOf(timeTableDataBean.getExamEndTime().getHours());
		String timestart = String.valueOf(timeTableDataBean.getExamStartTime().getHours() + ":"
				+ timeTableDataBean.getExamStartTime().getMinutes());
		String timeend = String.valueOf(
				timeTableDataBean.getExamEndTime().getHours() + ":" + timeTableDataBean.getExamEndTime().getMinutes());
		logger.debug("start time --  > " + timestart + " end time --  > " + timeend);
		starthours = String.valueOf(timeTableDataBean.getExamStartTime().getHours());
		startmin = String.valueOf(timeTableDataBean.getExamStartTime().getMinutes());
		if (starthours.length() == 1 && startmin.length() == 1) {
			starttime = "0" + starthours + ":" + "0" + startmin;
		} else if (starthours.length() == 1 && startmin.length() == 2) {
			starttime = "0" + starthours + ":" + startmin;
		} else if (starthours.length() == 2 && startmin.length() == 2) {
			starttime = starthours + ":" + startmin;
		} else if (starthours.length() == 2 && startmin.length() == 1) {
			starttime = starthours + ":" + "0" + startmin;
		}
		timestart = starttime;
		logger.debug("start hour and min -- " + timestart);
		starthours = String.valueOf(timeTableDataBean.getExamEndTime().getHours());
		startmin = String.valueOf(timeTableDataBean.getExamEndTime().getMinutes());
		if (starthours.length() == 1 && startmin.length() == 1) {
			endtime = "0" + starthours + ":" + "0" + startmin;
		} else if (starthours.length() == 1 && startmin.length() == 2) {
			endtime = "0" + starthours + ":" + startmin;
		} else if (starthours.length() == 2 && startmin.length() == 2) {
			endtime = starthours + ":" + startmin;
		} else if (starthours.length() == 2 && startmin.length() == 1) {
			endtime = starthours + ":" + "0" + startmin;
		}
		timeend = endtime;
		logger.debug("end hour and min -- " + timeend);
		starthours = String.valueOf((Integer.parseInt(timeend.substring(0, 2)) - 1));
		starthours = String.valueOf((Integer.parseInt(starthours) - Integer.parseInt(timestart.substring(0, 2))));
		if (Integer.parseInt(timestart.substring(3, 5)) < Integer.parseInt(timeend.substring(3, 5))) {
			startmin = String.valueOf((Integer.parseInt(timeend.substring(3, 5)) + 60));
			startmin = String.valueOf((Integer.parseInt(startmin) - Integer.parseInt(timestart.substring(3, 5))));
		} else if (Integer.parseInt(timestart.substring(3, 5)) > Integer.parseInt(timeend.substring(3, 5))) {
			startmin = String
					.valueOf((Integer.parseInt(timestart.substring(3, 5)) - Integer.parseInt(timeend.substring(3, 5))));
		} else if (Integer.parseInt(timestart.substring(3, 5)) == Integer.parseInt(timeend.substring(3, 5))) {
			startmin = String.valueOf((Integer.parseInt(timeend.substring(3, 5)) + 60));
			startmin = String.valueOf((Integer.parseInt(startmin) - Integer.parseInt(timestart.substring(3, 5))));
		}
		if (startmin.length() == 3) {
			startmin = String.valueOf((Integer.parseInt(startmin) - 60));
			starthours = String.valueOf((Integer.parseInt(starthours) + 1));
		} else if (startmin.length() != 3) {

		}
		if (starthours.length() == 1 && startmin.length() == 1) {
			time = "0" + starthours + ":" + "0" + startmin;
		} else if (starthours.length() == 1 && startmin.length() == 2) {
			time = "0" + starthours + ":" + startmin;
		} else if (starthours.length() == 2 && startmin.length() == 2) {
			time = starthours + ":" + startmin;
		} else if (starthours.length() == 2 && startmin.length() == 1) {
			time = starthours + ":" + "0" + startmin;
		}
		starthours = time.substring(0, 2);
		startmin = time.substring(3, 5);
		logger.debug("hour -- > " + starthours + " minutes - - > " + startmin);
		if (timeTableDataBean.getExamShift().equals("Morning Shift")) {
			if (!((0 < Integer.parseInt(starthour)) && Integer.parseInt(starthour) <= 13)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exStartTime").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Select Correct Morning Time"));
				}
				valid = false;
			}
			if (!((0 < Integer.parseInt(endhour)) && Integer.parseInt(endhour) <= 13)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exEndTime").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Select Correct Morning Time"));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamShift().equals("Evening Shift")) {
			if (!((13 <= Integer.parseInt(starthour)) && Integer.parseInt(starthour) < 18)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exStartTime").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Select Correct Evening Time"));
				}
				valid = false;
			}
			if (!((13 <= Integer.parseInt(endhour)) && Integer.parseInt(endhour) < 18)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exEndTime").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Select Correct Evening Time"));
				}
				valid = false;
			}
		}
		if (timeTableDataBean.getExamtTotalTime().equals("30 Minute")) {
			if ((Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 30)
					|| (Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 00)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("1 Hour")) {
			if ((Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 00)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 60)
					|| (Integer.parseInt(starthours) > 01 || Integer.parseInt(startmin) > 60)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("1 Hour 30 Minute")) {
			if ((Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 30)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 90)
					|| (Integer.parseInt(starthours) > 01 || Integer.parseInt(startmin) > 90)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("2 Hour")) {
			if ((Integer.parseInt(starthours) == 02 && Integer.parseInt(startmin) != 00)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 120)
					|| (Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 60)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("2 Hour 30 Minute")) {
			if ((Integer.parseInt(starthours) == 02 && Integer.parseInt(startmin) != 30)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 150)
					|| (Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 90)
					|| (Integer.parseInt(starthours) > 02 && Integer.parseInt(startmin) != 30)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("3 Hour")) {
			if ((Integer.parseInt(starthours) == 03 && Integer.parseInt(startmin) != 00)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 180)
					|| (Integer.parseInt(starthours) == 02 && Integer.parseInt(startmin) != 60)
					|| (Integer.parseInt(starthours) < 03 && Integer.parseInt(startmin) != 60)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		}
		return valid;
	}

	public String examUpdates() {
		logger.info("exam updates --");
		String personID;
		try {
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (validatez(true)) {
				if (personID != null) {
					RequestContext reqcontextt = RequestContext.getCurrentInstance();
					reqcontextt.execute("exupdateBlk1();");
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return "";
	}

	public String examUpdate() {
		logger.info("exam update -- > ");
		String personID;
		try {
			String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				if (validatez(true)) {
					timeTableDataBean.setSchoolID(schoolid);
					timeTableDataBean.setSchoolName(schoolName);
				String status = controller.getexamupdate(personID, timeTableDataBean);
				logger.debug("staus - " + status);
				if (status.equalsIgnoreCase("Success")) {
					MailSendJDBC.examTimeTableUpdate(timeTableDataBean,schoolName,schoolid);
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('classUpdate').show();");
						reqcontext.execute("PF('examEdit').hide();");
						boxflag = true;
						checkflag = false;
						checkflagz = false;
				} else if (status.equalsIgnoreCase("Exsist")) {
					checkflag = true;
					checkflagz = false;
					RequestContext reqcontext = RequestContext.getCurrentInstance();
					reqcontext.execute("PF('checkDialog').show();");
				}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return "";
	}

	public boolean validatez(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();

		if (timeTableDataBean.getExamStartDate() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exDate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Exam Date"));
			}
			valid = false;
		}

		if (StringUtils.isEmpty(timeTableDataBean.getExamDay())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exDay").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Exam Day."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(timeTableDataBean.getExamSubject())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exSubject").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Subject."));
			}
			valid = false;
		}

		if (StringUtils.isEmpty(timeTableDataBean.getExamRoomNo())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exRoomNo").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Room No."));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(timeTableDataBean.getExamRoomNo())) {
			if (!CommonValidate.validateNumber(timeTableDataBean.getExamRoomNo())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exRoomNo").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Room No."));
				}
				valid = false;
			}
		}
		if (timeTableDataBean.getExamStartTime() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exStartTime").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Start Time."));
			}
			valid = false;
		}
		if (timeTableDataBean.getExamEndTime() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exEndTime").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the End Time."));
			}
			valid = false;
		}
		/*if (StringUtils.isEmpty(timeTableDataBean.getExamShift())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exShift").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Exam Shift."));
			}
			valid = false;
		}*/
		return valid;
	}

	/*private String sendEmail(TimeTableDataBean timeTableDataBean) throws NoSuchProviderException {
		String status = "Fail";
		String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			InternetAddress[] toAddress = new InternetAddress[timeTableDataBean.getMails().size()];
			for (int i = 0; i < timeTableDataBean.getMails().size(); i++) {
				toAddress[i] = new InternetAddress(timeTableDataBean.getMails().get(i));
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
					+ "<p>Welcome on-board into"
					+ schoolName
					+ " family. Please find the your Exams are Postponed and the Details enclosed with is mail."
					+ "All the very best in your " + timeTableDataBean.getExamTitle() + " Exams</p>"
					+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
					+ "<tr>" + "<td> " + " Exam Date" + "</td>" + "<td>" + " Subject" + "</td>" + "<td> " + " Room No"
					+ "</td>" + "</tr>" + "<tr>" + "<td> " + dateFormat.format(timeTableDataBean.getExamStartDate())
					+ "</td>" + "<td>" + timeTableDataBean.getExamSubject() + "</td>" + "<td>"
					+ timeTableDataBean.getExamRoomNo() + "</td>" + "</tr>" + "</table>" + "<footer>" + "<center>"
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
				message.addRecipients(Message.RecipientType.TO, toAddress);
				 message.addRecipients(Message.RecipientType.CC, myCcList); 
				message.setSubject("Exam Time Table Update");
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
		} finally {

		}
		return status;
	}*/

	public String update() {
		logger.info("inside update completed ");
		setBoxflag(false);
		setBoxflag(false);
		timeTableDataBean.setExamClass("");
		timeTableDataBean.setExamTitle("");
		try {
			timeTableDataBean.setTablelist(new ArrayList<TimeTableDataBean>());
			String personID;
			flag = false;
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				controller.examRecords(timeTableDataBean, personID);
				String login = "" + FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role");
				if (login.equalsIgnoreCase("Admin")) {
					flag0 = true;
				} else {
					flag0 = false;
				}
				flag = true;
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {
		}
		return "";
	}

	public void submit() {
		setBoxflag(false);
		try {
			logger.info("------submit method calling--------");
			String personID;
			flag = false;
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				if(searchValidate(true)){
				timeTableDataBean.setTablelist(new ArrayList<TimeTableDataBean>());
				controller.examRecords(timeTableDataBean, personID);
				logger.debug("Size"+ timeTableDataBean.getTablelist().size());
				
				String login = "" + FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role");
				if (login.equals("Admin")) {
					flag0 = true;
				} else {
					flag0 = false;
				}
				flag = true;
				}
			}
		} catch (Exception e) {
			logger.info("-----submit method exception calling-------");
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	private boolean searchValidate(boolean valid) {
		valid=true;
		FacesContext fc=null;
		try{
			fc=FacesContext.getCurrentInstance();
			if(StringUtils.isEmpty(timeTableDataBean.getExamClass())){
				fc.addMessage(CommonValidate.findComponentInRoot("TimeViewClass").getClientId(), new FacesMessage("Please Choose the Class name"));
				valid=false;
			}
			if(StringUtils.isEmpty(timeTableDataBean.getExamTitle())){
				fc.addMessage(CommonValidate.findComponentInRoot("TimeViewTitle").getClientId(), new FacesMessage("Please Choose the ExamTitle"));
				valid=false;
			}
		}catch(Exception e){
		}
		return valid;
	}

	public void starttime(ValueChangeEvent v) {
		try {
			logger.debug("start time -- " + v.getNewValue().toString());
			timeTableDataBean.setExamStartTime((Date) v.getNewValue());
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	public void endtime(ValueChangeEvent v) {
		try {
			logger.debug("end time -- " + v.getNewValue().toString());
			timeTableDataBean.setExamEndTime((Date) v.getNewValue());
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

	public String updateBack() {
		try{
		checkflag = false;
		checkflagz = false;
		RequestContext reqcontext = RequestContext.getCurrentInstance();
		reqcontext.execute("PF('checkDialogz').hide();");
		reqcontext.execute("PF('checkDialog').hide();");
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (SmsController) ctx.getBean("controller");
		controller.examRecords(timeTableDataBean, personID);
		reqcontext.update("XX");
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}
	public String examMarkEdit(TimeTableDataBean timeTable){
		String personID;
		try {
			marksList=new ArrayList<TimeTableDataBean>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				timeTableDataBean.setExamClass(timeTable.getExamClass());
				timeTableDataBean.setExamTitle(timeTable.getExamTitle());
				marksList=controller.getexamMarksList(personID, timeTableDataBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {

		}
		return "";
	}
	public void onRowEdit(RowEditEvent event){
		logger.debug("inside row edit method calling");
		String personID;
		String frommark="",tomark="",grade="",gradedetailid="",examResult="";
		try {
		frommark = ((TimeTableDataBean) event.getObject()).getExamFromMark().toString();
		tomark = ((TimeTableDataBean) event.getObject()).getExamToMark().toString();
		grade = ((TimeTableDataBean) event.getObject()).getExamGrade().toString();
		gradedetailid = ((TimeTableDataBean) event.getObject()).getGradedetailId().toString();
		examResult=((TimeTableDataBean) event.getObject()).getExamResult().toString();
		timeTableDataBean.setExamFromMark(frommark);
		timeTableDataBean.setExamToMark(tomark);
		timeTableDataBean.setExamGrade(grade);
		timeTableDataBean.setGradedetailId(gradedetailid);
		timeTableDataBean.setExamResult(examResult);
		if(marksCheck(true)){
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
				if (personID != null) {
					String status = controller.getexammarksupdate(personID, timeTableDataBean);
					 if(status.equalsIgnoreCase("Success")){
						 	FacesMessage msg = new FacesMessage("Sucessfully Updated Exam Marks and Grade",
										timeTableDataBean.getExamTitle());
								FacesContext.getCurrentInstance().addMessage(null, msg);
					 }
				}
			} 
		}catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}
		
	public boolean marksCheck(boolean valid){
		valid=true;
		if (StringUtils.isEmpty(timeTableDataBean.getExamFromMark())) {
			FacesMessage msg = new FacesMessage(text.getString("sms.timetable.frommark"));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			valid = false;
		} else if (!StringUtils.isEmpty(timeTableDataBean.getExamFromMark())) {
			if (!CommonValidate.validateNumber(timeTableDataBean.getExamFromMark())) {
				FacesMessage msg = new FacesMessage(text.getString("sms.timetable.vfrommark"));
				FacesContext.getCurrentInstance().addMessage(null, msg);
				valid = false;
			}
			if(Integer.parseInt(timeTableDataBean.getExamFromMark())>100){
				FacesMessage msg = new FacesMessage(text.getString("sms.timetable.vfrommarks"));
				FacesContext.getCurrentInstance().addMessage(null, msg);
				valid = false;
			}
		}
		if (StringUtils.isEmpty(timeTableDataBean.getExamToMark())) {
			FacesMessage msg = new FacesMessage(text.getString("sms.timetable.tomark"));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			valid = false;
		} else if (!StringUtils.isEmpty(timeTableDataBean.getExamToMark())) {
			if (!CommonValidate.validateNumber(timeTableDataBean.getExamToMark())) {
				FacesMessage msg = new FacesMessage(text.getString("sms.timetable.vtomark"));
				FacesContext.getCurrentInstance().addMessage(null, msg);
				valid = false;
			}
			if(Integer.parseInt(timeTableDataBean.getExamToMark())>100){
				FacesMessage msg = new FacesMessage(text.getString("sms.timetable.vtomarks"));
				FacesContext.getCurrentInstance().addMessage(null, msg);
				valid = false;
			}
		}
		if (StringUtils.isEmpty(timeTableDataBean.getExamGrade())) {
			FacesMessage msg = new FacesMessage(text.getString("sms.timetable.grade"));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			valid = false;
		} 
		if (StringUtils.isEmpty(timeTableDataBean.getExamResult())) {
			FacesMessage msg = new FacesMessage(text.getString("sms.timetable.result"));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			valid = false;
		} 
		return valid;
	}
	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled", ((TimeTableDataBean) event.getObject()).getExamTitle());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void examDelete(TimeTableDataBean timeTable){
		ApplicationContext ctx =null;
		String status="";
		String personID="";
		try{
			ctx= FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID").toString();
			if(personID!=null){
				status=controller.examDelete(timeTable);
				if("Success".equalsIgnoreCase(status)){
					RequestContext.getCurrentInstance().execute("PF('examDelete').show();");
				}
			}
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		}
	}
	public void gradeDelete(TimeTableDataBean timeTable){
		System.out.println("timetable------"+timeTable.getGradedetailId());
		ApplicationContext ctx =null;
		String status="";
		String personID="";
		try{
			ctx= FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID").toString();
			if(personID!=null){
				status=controller.gradeDelete(timeTable);
				if("Success".equalsIgnoreCase(status)){
					marksList=new ArrayList<TimeTableDataBean>();
					marksList=controller.getexamMarksList(personID, timeTableDataBean);
				}
			}
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		}
	}
}
