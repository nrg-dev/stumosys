package com.stumosys.managedBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
//import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
//import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;




import javax.annotation.PostConstruct;
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
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.stumosys.controler.SmsController;
import com.stumosys.domain.TimeTableDataBean;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name = "timeTableMB")
@RequestScoped
public class TimeTableMB {

	TimeTableDataBean timeTableDataBean = new TimeTableDataBean();
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	Date now = new Date();
	private List<String> classList;
	private List<TimeTableDataBean> filteredExam;
	private boolean next = false;
	private boolean next1 = false;
	private boolean next2 = false;
	private boolean next3 = false;
	private boolean next4 = false;
	private boolean next5 = false;
	private boolean next6 = false;
	private boolean next7 = false;
	private boolean checkflag = false;
	private boolean checkflagz = false;
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	DateTime jodadate;
	public List<TimeTableDataBean> timesBean;
	public String examtitle;
	public Date examdate;
	public String classs;
	public String totaltime;
	public String getExamtitle() {
		return examtitle;
	}

	public void setExamtitle(String examtitle) {
		this.examtitle = examtitle;
	}

	public Date getExamdate() {
		return examdate;
	}

	public void setExamdate(Date examdate) {
		this.examdate = examdate;
	}

	public String getClasss() {
		return classs;
	}

	public void setClasss(String classs) {
		this.classs = classs;
	}

	public String getTotaltime() {
		return totaltime;
	}

	public void setTotaltime(String totaltime) {
		this.totaltime = totaltime;
	}

	public List<TimeTableDataBean> getTimesBean() {
		return timesBean;
	}

	public void setTimesBean(List<TimeTableDataBean> timesBean) {
		this.timesBean = timesBean;
	}

	public boolean isCheckflagz() {
		return checkflagz;
	}

	public void setCheckflagz(boolean checkflagz) {
		this.checkflagz = checkflagz;
	}

	public boolean isCheckflag() {
		return checkflag;
	}

	public void setCheckflag(boolean checkflag) {
		this.checkflag = checkflag;
	}

	public List<TimeTableDataBean> getFilteredExam() {
		return filteredExam;
	}

	public void setFilteredExam(List<TimeTableDataBean> filteredExam) {
		this.filteredExam = filteredExam;
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

	public boolean isNext7() {
		return next7;
	}

	public void setNext7(boolean next7) {
		this.next7 = next7;
	}

	public boolean isNext4() {
		return next4;
	}

	public void setNext4(boolean next4) {
		this.next4 = next4;
	}

	public boolean isNext5() {
		return next5;
	}

	public void setNext5(boolean next5) {
		this.next5 = next5;
	}

	public boolean isNext6() {
		return next6;
	}

	public void setNext6(boolean next6) {
		this.next6 = next6;
	}

	public boolean isNext2() {
		return next2;
	}

	public void setNext2(boolean next2) {
		this.next2 = next2;
	}

	public boolean isNext3() {
		return next3;
	}

	public void setNext3(boolean next3) {
		this.next3 = next3;
	}

	List<TimeTableDataBean> list = new ArrayList<TimeTableDataBean>();
	List<TimeTableDataBean> listcheck = new ArrayList<TimeTableDataBean>();

	public List<TimeTableDataBean> getListcheck() {
		return listcheck;
	}

	public void setListcheck(List<TimeTableDataBean> listcheck) {
		this.listcheck = listcheck;
	}

	public List<TimeTableDataBean> getList() {
		return list;
	}

	public void setList(List<TimeTableDataBean> list) {
		this.list = list;
	}

	public boolean isNext1() {
		return next1;
	}

	public void setNext1(boolean next1) {
		this.next1 = next1;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
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

	private static Logger logger = Logger.getLogger(TimeTableMB.class);
	SmsController controller = null;

	public String addTimeTable() {
		list.clear();
		List<String> splitList=null;
		try {
			logger.info("----------Inside addTimeTable Method Calling-----");
			timeTableDataBean.setExamTitle("");
			timeTableDataBean.setExamStartDate(null);
			timeTableDataBean.setExamClass("");
			timeTableDataBean.setExamtTotalTime("");
			timeTableDataBean.setExamDate(null);
			timeTableDataBean.setExamDay("");
			timeTableDataBean.setExamSubject("");
			timeTableDataBean.setExamSubjectCode("");
			timeTableDataBean.setExamRoomNo("");
			timeTableDataBean.setExamStartTime(null);
			timeTableDataBean.setExamEndTime(null);
			timeTableDataBean.setExamShift("");
			timeTableDataBean.setExamStartDate1(null);
			timeTableDataBean.setExamDate1(null);
			timeTableDataBean.setExamDay1("");
			timeTableDataBean.setExamSubject1("");
			timeTableDataBean.setExamSubjectCode1("");
			timeTableDataBean.setExamRoomNo1("");
			timeTableDataBean.setExamStartTime1(null);
			timeTableDataBean.setExamEndTime1(null);
			timeTableDataBean.setExamShift1("");
			timeTableDataBean.setExamStartDate2(null);
			timeTableDataBean.setExamDate2(null);
			timeTableDataBean.setExamDay2("");
			timeTableDataBean.setExamSubject2("");
			timeTableDataBean.setExamSubjectCode2("");
			timeTableDataBean.setExamRoomNo2("");
			timeTableDataBean.setExamStartTime2(null);
			timeTableDataBean.setExamEndTime2(null);
			timeTableDataBean.setExamShift2("");
			timeTableDataBean.setExamStartDate3(null);
			timeTableDataBean.setExamDate3(null);
			timeTableDataBean.setExamDay3("");
			timeTableDataBean.setExamSubject3("");
			timeTableDataBean.setExamSubjectCode3("");
			timeTableDataBean.setExamRoomNo3("");
			timeTableDataBean.setExamStartTime3(null);
			timeTableDataBean.setExamEndTime3(null);
			timeTableDataBean.setExamShift3("");
			timeTableDataBean.setExamStartDate4(null);
			timeTableDataBean.setExamDate4(null);
			timeTableDataBean.setExamDay4("");
			timeTableDataBean.setExamSubject4("");
			timeTableDataBean.setExamSubjectCode4("");
			timeTableDataBean.setExamRoomNo4("");
			timeTableDataBean.setExamStartTime4(null);
			timeTableDataBean.setExamEndTime4(null);
			timeTableDataBean.setExamShift4("");
			timeTableDataBean.setClassList(null);
			timeTableDataBean.setExamFromMark("");
			timeTableDataBean.setExamToMark("");
			timeTableDataBean.setExamGrade("");
			timeTableDataBean.setExamResult("");
			timesBean.clear();
			next = false;
			next4 = false;
			next1 = true;
			next5 = false;
			checkflag = false;
			next2 = false;
			next6 = false;
			next7 = false;
			checkflagz = false;
			next3 = false;
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			if (personID != null) {
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
				return "createTimeTables";
			}
		} catch (Exception e) {
			logger.info("----------Inside addTimeTable Method Exception Calling-----");
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return "";
	}

	public boolean validate(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		if(!schoolID.equalsIgnoreCase(paths.getString("MALAYSIA.SCHOOLID")) && !schoolID.equalsIgnoreCase(paths.getString("SMRV.SCHOOLID"))){
		if(timesBean.size()==0){
			if (StringUtils.isEmpty(timeTableDataBean.getExamFromMark())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("fromMark").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.timetable.frommark")));
				}
				valid = false;
			} else if (!StringUtils.isEmpty(timeTableDataBean.getExamFromMark())) {
				if (!CommonValidate.validateNumber(timeTableDataBean.getExamFromMark())) {
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("fromMark").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(text.getString("sms.timetable.vfrommark")));
					}
					valid = false;
				}
				if(Integer.parseInt(timeTableDataBean.getExamFromMark())>100){
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("fromMark").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(text.getString("sms.timetable.vfrommarks")));
					}
					valid = false;
				}
			}
			if (StringUtils.isEmpty(timeTableDataBean.getExamToMark())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("toMark").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.timetable.tomark")));
				}
				valid = false;
			} else if (!StringUtils.isEmpty(timeTableDataBean.getExamToMark())) {
				if (!CommonValidate.validateNumber(timeTableDataBean.getExamToMark())) {
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("toMark").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(text.getString("sms.timetable.vtomark")));
					}
					valid = false;
				}
				if(Integer.parseInt(timeTableDataBean.getExamToMark())>100){
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("toMark").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(text.getString("sms.timetable.vtomarks")));
					}
					valid = false;
				}
			}
			if (StringUtils.isEmpty(timeTableDataBean.getExamGrade())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("grade").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.timetable.grade")));
				}
				valid = false;
			} 
			if (StringUtils.isEmpty(timeTableDataBean.getExamResult())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("result").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.timetable.result")));
				}
				valid = false;
			} 
		}
		}
		if (StringUtils.isEmpty(timeTableDataBean.getExamTitle())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("examTitle").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Exam Title."));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(timeTableDataBean.getExamTitle())) {
			if (!CommonValidate.validateName(timeTableDataBean.getExamTitle())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("examTitle").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Exam Title."));
				}
				valid = false;
			}
		}
		if (timeTableDataBean.getExamStartDate() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("startDate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Exam Start Date."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(timeTableDataBean.getExamClass())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("examClass").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Class."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(timeTableDataBean.getExamtTotalTime())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("totalTime").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Total Time."));
			}
			valid = false;
		}
		return valid;
	}
	
	public String submit() {
		String personID = "";
		next = false;
		next1 = true;
		next2 = false;
		next3 = false;
		try {
			System.out.println("------submit method calling--------" + timeTableDataBean.getExamStartDate());
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			System.out.println(personID);
			if (validate(true)) {
				if (personID != null) {
					if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
							.equals("Admin")) {
						timeTableDataBean.setClassList(new ArrayList<TimeTableDataBean>());
						System.out.println("Before calling subject value menthod");
						controller.subjectValues(timeTableDataBean, personID);
						System.out.println("Successfully called subject value method");
						DateTime date=new DateTime(timeTableDataBean.getExamStartDate());
						timeTableDataBean.setExamDay(date.dayOfWeek().getAsText(Locale.ENGLISH));
						return "makeexam";
					} else {

					}
				}
			}
		} catch (Exception e) {
			logger.info("-----submit method exception calling-------");
//			logger.warn("Exception -->"+e.getMessage());
			System.out.println("Exception -->"+e.getMessage());
		
			//	e.printStackTrace();
		} finally {

		}
		return "";
	}

	public boolean validate1(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
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
		oh	}
			valid = false;
		}*/
		return valid;
	}

	public String examTable() {
		listcheck = null;
		String personID = "";
		ApplicationContext ctx = null;
		logger.info("exam time table ");
		listcheck = new ArrayList<TimeTableDataBean>();
		try {
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				if (next1 == true) {
					if (validate1(true)) {
						ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
						controller = (SmsController) ctx.getBean("controller");
						TimeTableDataBean nexts = new TimeTableDataBean();
						nexts.setExamClass(timeTableDataBean.getExamClass());
						nexts.setExamStartDate(timeTableDataBean.getExamStartDate());
						nexts.setExamDate(timeTableDataBean.getExamDate());
						nexts.setExamDay(timeTableDataBean.getExamDay());
						nexts.setExamTitle(timeTableDataBean.getExamTitle());
						nexts.setExamtTotalTime(timeTableDataBean.getExamtTotalTime());
						nexts.setExamEndTime(timeTableDataBean.getExamEndTime());
						nexts.setExamStartTime(timeTableDataBean.getExamStartTime());
						nexts.setExamRoomNo(timeTableDataBean.getExamRoomNo());
						nexts.setExamSubject(timeTableDataBean.getExamSubject());
						nexts.setExamSubjectCode(timeTableDataBean.getExamSubjectCode());
						nexts.setExamShift(timeTableDataBean.getExamShift());
						listcheck.add(nexts);
						timeTableDataBean.setTablelist(listcheck);
						if (personID != null) {
							String status = controller.timeTableICheck(personID, timeTableDataBean);
							System.out.println("status -- " + status);
							if (status.equalsIgnoreCase("no")) {
								RequestContext reqcontextt = RequestContext.getCurrentInstance();
								reqcontextt.execute("examBlk();");
								checkflag = false;
								checkflagz = false;
							} else if (status.equalsIgnoreCase("subject")) {
								checkflagz = true;
							} else {
								checkflag = true;
							}
						}
					}
				} else if (next2 == true) {
					next2 = true;
					if (validatez(true)) {
						TimeTableDataBean nexts = new TimeTableDataBean();
						nexts.setExamClass(timeTableDataBean.getExamClass());
						nexts.setExamDate(timeTableDataBean.getExamDate());
						nexts.setExamTitle(timeTableDataBean.getExamTitle());
						nexts.setExamtTotalTime(timeTableDataBean.getExamtTotalTime());
						nexts.setExamDay(timeTableDataBean.getExamDay1());
						nexts.setExamStartDate(timeTableDataBean.getExamStartDate1());
						nexts.setExamEndTime(timeTableDataBean.getExamEndTime1());
						nexts.setExamStartTime(timeTableDataBean.getExamStartTime1());
						nexts.setExamRoomNo(timeTableDataBean.getExamRoomNo1());
						nexts.setExamSubject(timeTableDataBean.getExamSubject1());
						nexts.setExamSubjectCode(timeTableDataBean.getExamSubjectCode1());
						nexts.setExamShift(timeTableDataBean.getExamShift1());
						listcheck.add(nexts);
						timeTableDataBean.setTablelist(listcheck);
						if (personID != null) {
							String status = controller.timeTableICheck(personID, timeTableDataBean);
							System.out.println("status -- " + status);
							if (status.equalsIgnoreCase("no")) {
								RequestContext reqcontextt = RequestContext.getCurrentInstance();
								reqcontextt.execute("examBlk();");
								checkflag = false;
								checkflagz = false;
							} else if (status.equalsIgnoreCase("subject")) {
								checkflagz = true;
							} else {
								checkflag = true;
							}
						}
					}
				} else if (next4 == true) {
					next4 = true;
					if (validatez1(true)) {
						TimeTableDataBean nexts = new TimeTableDataBean();
						nexts.setExamClass(timeTableDataBean.getExamClass());
						nexts.setExamDate(timeTableDataBean.getExamDate());
						nexts.setExamTitle(timeTableDataBean.getExamTitle());
						nexts.setExamtTotalTime(timeTableDataBean.getExamtTotalTime());
						nexts.setExamDay(timeTableDataBean.getExamDay2());
						nexts.setExamStartDate(timeTableDataBean.getExamStartDate2());
						nexts.setExamEndTime(timeTableDataBean.getExamEndTime2());
						nexts.setExamStartTime(timeTableDataBean.getExamStartTime2());
						nexts.setExamRoomNo(timeTableDataBean.getExamRoomNo2());
						nexts.setExamSubject(timeTableDataBean.getExamSubject2());
						nexts.setExamSubjectCode(timeTableDataBean.getExamSubjectCode());
						nexts.setExamShift(timeTableDataBean.getExamShift2());
						listcheck.add(nexts);
						timeTableDataBean.setTablelist(listcheck);
						if (personID != null) {
							String status = controller.timeTableICheck(personID, timeTableDataBean);
							System.out.println("status -- " + status);
							if (status.equalsIgnoreCase("no")) {
								RequestContext reqcontextt = RequestContext.getCurrentInstance();
								reqcontextt.execute("examBlk();");
								checkflag = false;
								checkflagz = false;
							} else if (status.equalsIgnoreCase("subject")) {
								checkflagz = true;
							} else {
								checkflag = true;
							}
						}
					}
				} else if (next6 == true) {
					next6 = true;
					if (validatez2(true)) {
						TimeTableDataBean nexts = new TimeTableDataBean();
						nexts.setExamClass(timeTableDataBean.getExamClass());
						nexts.setExamDate(timeTableDataBean.getExamDate());
						nexts.setExamTitle(timeTableDataBean.getExamTitle());
						nexts.setExamtTotalTime(timeTableDataBean.getExamtTotalTime());
						nexts.setExamDay(timeTableDataBean.getExamDay3());
						nexts.setExamStartDate(timeTableDataBean.getExamStartDate3());
						nexts.setExamEndTime(timeTableDataBean.getExamEndTime3());
						nexts.setExamStartTime(timeTableDataBean.getExamStartTime3());
						nexts.setExamRoomNo(timeTableDataBean.getExamRoomNo3());
						nexts.setExamSubject(timeTableDataBean.getExamSubject3());
						nexts.setExamSubjectCode(timeTableDataBean.getExamSubjectCode3());
						nexts.setExamShift(timeTableDataBean.getExamShift1());
						listcheck.add(nexts);
						timeTableDataBean.setTablelist(listcheck);
						if (personID != null) {
							String status = controller.timeTableICheck(personID, timeTableDataBean);
							System.out.println("status -- " + status);
							if (status.equalsIgnoreCase("no")) {
								RequestContext reqcontextt = RequestContext.getCurrentInstance();
								reqcontextt.execute("examBlk();");
								checkflag = false;
								checkflagz = false;
							} else if (status.equalsIgnoreCase("subject")) {
								checkflagz = true;
							} else {
								checkflag = true;
							}
						}
					}
				} else if (next7 == true) {
					if (validatez4(true)) {
						TimeTableDataBean nexts = new TimeTableDataBean();
						nexts.setExamClass(timeTableDataBean.getExamClass());
						nexts.setExamDate(timeTableDataBean.getExamDate());
						nexts.setExamTitle(timeTableDataBean.getExamTitle());
						nexts.setExamtTotalTime(timeTableDataBean.getExamtTotalTime());
						nexts.setExamDay(timeTableDataBean.getExamDay4());
						nexts.setExamStartDate(timeTableDataBean.getExamStartDate4());
						nexts.setExamEndTime(timeTableDataBean.getExamEndTime4());
						nexts.setExamStartTime(timeTableDataBean.getExamStartTime4());
						nexts.setExamRoomNo(timeTableDataBean.getExamRoomNo4());
						nexts.setExamSubject(timeTableDataBean.getExamSubject4());
						nexts.setExamSubjectCode(timeTableDataBean.getExamSubjectCode4());
						nexts.setExamShift(timeTableDataBean.getExamShift4());
						listcheck.add(nexts);
						timeTableDataBean.setTablelist(listcheck);
						if (personID != null) {
							String status = controller.timeTableICheck(personID, timeTableDataBean);
							System.out.println("status -- " + status);
							if (status.equalsIgnoreCase("no")) {
								RequestContext reqcontextt = RequestContext.getCurrentInstance();
								reqcontextt.execute("examBlk();");
								checkflag = false;
								checkflagz = false;
							} else if (status.equalsIgnoreCase("subject")) {
								checkflagz = true;
							} else {
								checkflag = true;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return "";
	}

	public String viewTimeTable() {
		ApplicationContext ctx = null;
		FacesMessage message = null;
		try {
			String personID = "";
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			logger.info("----------Inside viewTimeTable Method Calling-----");
			ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			if (next1 == true) {
				TimeTableDataBean nexts = new TimeTableDataBean();
				nexts.setExamClass(timeTableDataBean.getExamClass());
				nexts.setExamStartDate(timeTableDataBean.getExamStartDate());
				nexts.setExamDate(timeTableDataBean.getExamDate());
				nexts.setExamDay(timeTableDataBean.getExamDay());
				nexts.setExamTitle(timeTableDataBean.getExamTitle());
				nexts.setExamtTotalTime(timeTableDataBean.getExamtTotalTime());
				nexts.setExamEndTime(timeTableDataBean.getExamEndTime());
				nexts.setExamStartTime(timeTableDataBean.getExamStartTime());
				nexts.setExamRoomNo(timeTableDataBean.getExamRoomNo());
				nexts.setExamSubject(timeTableDataBean.getExamSubject());
				nexts.setExamSubjectCode(timeTableDataBean.getExamSubjectCode());
				nexts.setExamShift(timeTableDataBean.getExamShift());
				list.add(nexts);
				timeTableDataBean.setClassList(list);
				next2 = true;
				if (personID != null) {
					String status = controller.timeTableInsert(personID, timeTableDataBean,timesBean);
					System.out.println("status -- > " + status);
					if (status.equalsIgnoreCase("Success")) {
						controller.timeTableView(timeTableDataBean, personID);
						
						//String pdfStatus = generatePdf(timeTableDataBean);
						
						logger.info("Before calling Exam Time table start ------------------");
						String mailStatus =MailSendJDBC.examTimeTableInsert(timeTableDataBean,schoolName,schoolid);
						logger.info("Before calling Exam Time table end ------------------");

						if (mailStatus.equalsIgnoreCase("Success")) {
							/*String emailStatus = sendEmail(timeTableDataBean);
							System.out.println("mail status -- > " + emailStatus);
							if (emailStatus.equalsIgnoreCase("Success")) {*/
								message = new FacesMessage("Succesful",
										timeTableDataBean.getExamTitle() + " is Inserted .");
								FacesContext.getCurrentInstance().addMessage(null, message);
								RequestContext reqcontext = RequestContext.getCurrentInstance();
								reqcontext.execute("PF('examblocksUI').hide();");
								reqcontext.execute("PF('examDialog').show();");
							/*}*/
						}
					} else {
						message = new FacesMessage("Network Problem. Please try some times");
						FacesContext.getCurrentInstance().addMessage(null, message);
					}
				}
			} else if (next2 == true) {
				next2 = false;
				TimeTableDataBean nexts = new TimeTableDataBean();
				nexts.setExamClass(timeTableDataBean.getExamClass());
				nexts.setExamDate(timeTableDataBean.getExamDate());
				nexts.setExamTitle(timeTableDataBean.getExamTitle());
				nexts.setExamtTotalTime(timeTableDataBean.getExamtTotalTime());
				nexts.setExamDay(timeTableDataBean.getExamDay1());
				nexts.setExamStartDate(timeTableDataBean.getExamStartDate1());
				nexts.setExamEndTime(timeTableDataBean.getExamEndTime1());
				nexts.setExamStartTime(timeTableDataBean.getExamStartTime1());
				nexts.setExamRoomNo(timeTableDataBean.getExamRoomNo1());
				nexts.setExamSubject(timeTableDataBean.getExamSubject1());
				nexts.setExamSubjectCode(timeTableDataBean.getExamSubjectCode1());
				nexts.setExamShift(timeTableDataBean.getExamShift1());
				list.add(nexts);
				timeTableDataBean.setClassList(list);
				if (personID != null) {
					String status = controller.timeTableInsert(personID, timeTableDataBean,timesBean);
					System.out.println("status -- > " + status);
					if (status.equalsIgnoreCase("Success")) {
						controller.timeTableView(timeTableDataBean, personID);
						//String pdfStatus = generatePdf(timeTableDataBean);
						MailSendJDBC.examTimeTableInsert(timeTableDataBean,schoolName,schoolid);
						//if (pdfStatus.equalsIgnoreCase("Success")) {
							/*String emailStatus = sendEmail(timeTableDataBean);
							System.out.println("mail status -- > " + emailStatus);
							if (emailStatus.equalsIgnoreCase("Success")) {*/
								message = new FacesMessage("Succesful",
										timeTableDataBean.getExamTitle() + " is Inserted .");
								FacesContext.getCurrentInstance().addMessage(null, message);
								RequestContext reqcontext = RequestContext.getCurrentInstance();
								reqcontext.execute("PF('examblocksUI').hide();");
								reqcontext.execute("PF('examDialog').show();");
							/*}*/
						//}
					} else {
						message = new FacesMessage("Network Problem. Please try some times");
						FacesContext.getCurrentInstance().addMessage(null, message);
					}
				}
			} else if (next4 == true) {
				next4 = false;
				TimeTableDataBean nexts = new TimeTableDataBean();
				nexts.setExamClass(timeTableDataBean.getExamClass());
				nexts.setExamDate(timeTableDataBean.getExamDate());
				nexts.setExamTitle(timeTableDataBean.getExamTitle());
				nexts.setExamtTotalTime(timeTableDataBean.getExamtTotalTime());
				nexts.setExamDay(timeTableDataBean.getExamDay2());
				nexts.setExamStartDate(timeTableDataBean.getExamStartDate2());
				nexts.setExamEndTime(timeTableDataBean.getExamEndTime2());
				nexts.setExamStartTime(timeTableDataBean.getExamStartTime2());
				nexts.setExamRoomNo(timeTableDataBean.getExamRoomNo2());
				nexts.setExamSubject(timeTableDataBean.getExamSubject2());
				nexts.setExamSubjectCode(timeTableDataBean.getExamSubjectCode2());
				nexts.setExamShift(timeTableDataBean.getExamShift2());
				list.add(nexts);
				timeTableDataBean.setClassList(list);
				if (personID != null) {
					String status = controller.timeTableInsert(personID, timeTableDataBean,timesBean);
					System.out.println("status -- > " + status);
					if (status.equalsIgnoreCase("Success")) {
						controller.timeTableView(timeTableDataBean, personID);
						//String pdfStatus = generatePdf(timeTableDataBean);
						MailSendJDBC.examTimeTableInsert(timeTableDataBean,schoolName,schoolid);
						//if (pdfStatus.equalsIgnoreCase("Success")) {
							/*String emailStatus = sendEmail(timeTableDataBean);
							System.out.println("mail status -- > " + emailStatus);
							if (emailStatus.equalsIgnoreCase("Success")) {*/
								message = new FacesMessage("Succesful",
										timeTableDataBean.getExamTitle() + " is Inserted .");
								FacesContext.getCurrentInstance().addMessage(null, message);
								RequestContext reqcontext = RequestContext.getCurrentInstance();
								reqcontext.execute("PF('examblocksUI').hide();");
								reqcontext.execute("PF('examDialog').show();");
							/*}*/
						//}
					} else {
						message = new FacesMessage("Network Problem. Please try some times");
						FacesContext.getCurrentInstance().addMessage(null, message);
					}
				}
			} else if (next6 == true) {
				next6 = false;
				TimeTableDataBean nexts = new TimeTableDataBean();
				nexts.setExamClass(timeTableDataBean.getExamClass());
				nexts.setExamDate(timeTableDataBean.getExamDate());
				nexts.setExamTitle(timeTableDataBean.getExamTitle());
				nexts.setExamtTotalTime(timeTableDataBean.getExamtTotalTime());
				nexts.setExamDay(timeTableDataBean.getExamDay3());
				nexts.setExamStartDate(timeTableDataBean.getExamStartDate3());
				nexts.setExamEndTime(timeTableDataBean.getExamEndTime3());
				nexts.setExamStartTime(timeTableDataBean.getExamStartTime3());
				nexts.setExamRoomNo(timeTableDataBean.getExamRoomNo3());
				nexts.setExamSubject(timeTableDataBean.getExamSubject3());
				nexts.setExamSubjectCode(timeTableDataBean.getExamSubjectCode3());
				nexts.setExamShift(timeTableDataBean.getExamShift3());
				list.add(nexts);
				timeTableDataBean.setClassList(list);
				if (personID != null) {
					String status = controller.timeTableInsert(personID, timeTableDataBean,timesBean);
					System.out.println("status -- > " + status);
					if (status.equalsIgnoreCase("Success")) {
						controller.timeTableView(timeTableDataBean, personID);
						//String pdfStatus = generatePdf(timeTableDataBean);
						MailSendJDBC.examTimeTableInsert(timeTableDataBean,schoolName,schoolid);
						//if (pdfStatus.equalsIgnoreCase("Success")) {
							/*String emailStatus = sendEmail(timeTableDataBean);
							System.out.println("mail status -- > " + emailStatus);
							if (emailStatus.equalsIgnoreCase("Success")) {*/
								message = new FacesMessage("Succesful",
										timeTableDataBean.getExamTitle() + " is Inserted .");
								FacesContext.getCurrentInstance().addMessage(null, message);
								RequestContext reqcontext = RequestContext.getCurrentInstance();
								reqcontext.execute("PF('examblocksUI').hide();");
								reqcontext.execute("PF('examDialog').show();");
							/*}*/
						//}
					} else {
						message = new FacesMessage("Network Problem. Please try some times");
						FacesContext.getCurrentInstance().addMessage(null, message);
					}
				}
			} else if (next7 == true) {
				TimeTableDataBean nexts = new TimeTableDataBean();
				nexts.setExamClass(timeTableDataBean.getExamClass());
				nexts.setExamDate(timeTableDataBean.getExamDate());
				nexts.setExamTitle(timeTableDataBean.getExamTitle());
				nexts.setExamtTotalTime(timeTableDataBean.getExamtTotalTime());
				nexts.setExamDay(timeTableDataBean.getExamDay4());
				nexts.setExamStartDate(timeTableDataBean.getExamStartDate4());
				nexts.setExamEndTime(timeTableDataBean.getExamEndTime4());
				nexts.setExamStartTime(timeTableDataBean.getExamStartTime4());
				nexts.setExamRoomNo(timeTableDataBean.getExamRoomNo4());
				nexts.setExamSubject(timeTableDataBean.getExamSubject4());
				nexts.setExamSubjectCode(timeTableDataBean.getExamSubjectCode4());
				nexts.setExamShift(timeTableDataBean.getExamShift4());
				list.add(nexts);
				timeTableDataBean.setClassList(list);
				if (personID != null) {
					String status = controller.timeTableInsert(personID, timeTableDataBean,timesBean);
					System.out.println("status -- > " + status);
					if (status.equalsIgnoreCase("Success")) {
						controller.timeTableView(timeTableDataBean, personID);
						//String pdfStatus = generatePdf(timeTableDataBean);
						MailSendJDBC.examTimeTableInsert(timeTableDataBean,schoolName,schoolid);
						//if (pdfStatus.equalsIgnoreCase("Success")) {
							/*String emailStatus = sendEmail(timeTableDataBean);
							System.out.println("mail status -- > " + emailStatus);
							if (emailStatus.equalsIgnoreCase("Success")) {*/
								message = new FacesMessage("Succesful",
										timeTableDataBean.getExamTitle() + " is Inserted .");
								FacesContext.getCurrentInstance().addMessage(null, message);
								RequestContext reqcontext = RequestContext.getCurrentInstance();
								reqcontext.execute("PF('examblocksUI').hide();");
								reqcontext.execute("PF('examDialog').show();");
							/*}else{
								RequestContext reqcontext = RequestContext.getCurrentInstance();
								reqcontext.execute("PF('examblocksUI').hide();");
								reqcontext.execute("PF('examDialog').hide();");
							}
						}*/
					} else {
						message = new FacesMessage("Network Problem. Please try some times");
						FacesContext.getCurrentInstance().addMessage(null, message);
					}
				}
			}
			System.out.println("exam time table size - > " + timeTableDataBean.getClassList().size());
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {
			message = null;
		}
		return "";
	}

	public String examBack() {
		return "dashboard1back";
	}

	public boolean validatez(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (timeTableDataBean.getExamStartDate1() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exDate1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Exam Date."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(timeTableDataBean.getExamDay1())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exDay1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Exam day."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(timeTableDataBean.getExamSubject1())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exSubject1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Subject."));
			}
			valid = false;
		}

		if (StringUtils.isEmpty(timeTableDataBean.getExamRoomNo1())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exRoomNo1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Room No."));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(timeTableDataBean.getExamRoomNo1())) {
			if (!CommonValidate.validateNumber(timeTableDataBean.getExamRoomNo1())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exRoomNo1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Room No."));
				}
				valid = false;
			}
		}
		if (timeTableDataBean.getExamStartTime1() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exStartTime1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Start Time."));
			}
			valid = false;
		}
		if (timeTableDataBean.getExamEndTime1() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exEndTime1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the End Time."));
			}
			valid = false;
		}
		/*if (StringUtils.isEmpty(timeTableDataBean.getExamShift1())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exShift1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Exam Shift."));
			}
			valid = false;
		}*/
		return valid;
	}

	public boolean validatez1(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (timeTableDataBean.getExamStartDate2() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exDate3").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Exam Date."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(timeTableDataBean.getExamDay2())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exDay3").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Exam day."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(timeTableDataBean.getExamSubject2())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exSubject3").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Subject."));
			}
			valid = false;
		}

		if (StringUtils.isEmpty(timeTableDataBean.getExamRoomNo2())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exRoomNo3").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Room No."));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(timeTableDataBean.getExamRoomNo2())) {
			if (!CommonValidate.validateNumber(timeTableDataBean.getExamRoomNo2())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exRoomNo3").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Room No."));
				}
				valid = false;
			}
		}
		if (timeTableDataBean.getExamStartTime2() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exStartTime3").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Start Time."));
			}
			valid = false;
		}
		if (timeTableDataBean.getExamEndTime2() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exEndTime3").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the End Time."));
			}
			valid = false;
		}
		/*if (StringUtils.isEmpty(timeTableDataBean.getExamShift2())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exShift3").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Exam Shift."));
			}
			valid = false;
		}*/
		return valid;
	}

	public boolean validatez2(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (timeTableDataBean.getExamStartDate3() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exDate4").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Exam Date."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(timeTableDataBean.getExamDay3())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exDay4").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Exam day."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(timeTableDataBean.getExamSubject3())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exSubject4").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Subject."));
			}
			valid = false;
		}

		if (StringUtils.isEmpty(timeTableDataBean.getExamRoomNo3())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exRoomNo4").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Room No."));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(timeTableDataBean.getExamRoomNo3())) {
			if (!CommonValidate.validateNumber(timeTableDataBean.getExamRoomNo3())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exRoomNo4").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Room No."));
				}
				valid = false;
			}
		}
		if (timeTableDataBean.getExamStartTime3() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exStartTime4").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Start Time."));
			}
			valid = false;
		}
		if (timeTableDataBean.getExamEndTime3() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exEndTime4").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the End Time."));
			}
			valid = false;
		}
		/*if (StringUtils.isEmpty(timeTableDataBean.getExamShift3())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exShift4").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Exam Shift."));
			}
			valid = false;
		}*/
		return valid;
	}

	public boolean validatez4(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (timeTableDataBean.getExamStartDate4() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exDate5").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Exam Date."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(timeTableDataBean.getExamDay4())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exDay5").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Exam day."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(timeTableDataBean.getExamSubject4())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exSubject5").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Subject."));
			}
			valid = false;
		}

		if (StringUtils.isEmpty(timeTableDataBean.getExamRoomNo4())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exRoomNo5").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Room No."));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(timeTableDataBean.getExamRoomNo4())) {
			if (!CommonValidate.validateNumber(timeTableDataBean.getExamRoomNo4())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exRoomNo5").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Room No."));
				}
				valid = false;
			}
		}
		if (timeTableDataBean.getExamStartTime4() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exStartTime5").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Start Time."));
			}
			valid = false;
		}
		if (timeTableDataBean.getExamEndTime4() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exEndTime5").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the End Time."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(timeTableDataBean.getExamShift4())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("exShift5").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Exam Shift."));
			}
			valid = false;
		}
		return valid;
	}

	public String reset() {
		logger.info("------reset method calling");
		timeTableDataBean.setExamTitle("");
		timeTableDataBean.setExamStartDate(null);
		timeTableDataBean.setExamClass("");
		timeTableDataBean.setExamtTotalTime("");
		next = false;
		next1 = true;
		next2 = false;
		next3 = false;
		return "";
	}

	public void subjectcode(ValueChangeEvent vv) {
		System.out.println("subject -1- > " + vv.getNewValue());
		next = false;
		next1 = true;
		next2 = false;
		next3 = false;
		String personID = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			System.out.println(personID);
			timeTableDataBean.setExamSubject(vv.getNewValue().toString());
			if (personID != null) {
				controller.changeSubjectCode(personID, timeTableDataBean);
				System.out.println("subject -- > " + timeTableDataBean.getExamSubjectCodes());
				timeTableDataBean.setExamSubjectCode(timeTableDataBean.getExamSubjectCodes());
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	public void subjectcode1(ValueChangeEvent vv) {
		System.out.println("subject -2- > " + vv.getNewValue());
		next = true;
		next1 = false;
		next2 = true;
		next3 = false;
		String personID = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			System.out.println(personID);
			timeTableDataBean.setExamSubject(vv.getNewValue().toString());
			if (personID != null) {
					controller.changeSubjectCode(personID, timeTableDataBean);
					System.out.println("subject -1- > " + timeTableDataBean.getExamSubjectCodes());
					timeTableDataBean.setExamSubjectCode1(timeTableDataBean.getExamSubjectCodes());
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	public void subjectcode2(ValueChangeEvent vv) {
		System.out.println("subject -3- > " + vv.getNewValue());
		next3 = true;
		next4 = true;
		String personID = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			System.out.println(personID);
			timeTableDataBean.setExamSubject(vv.getNewValue().toString());
			if (personID != null) {
					controller.changeSubjectCode(personID, timeTableDataBean);
					System.out.println("subject -2- > " + timeTableDataBean.getExamSubjectCodes());
					timeTableDataBean.setExamSubjectCode2(timeTableDataBean.getExamSubjectCodes());
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	public void subjectcode3(ValueChangeEvent vv) {
		System.out.println("subject -4- > " + vv.getNewValue());
		next5 = true;
		next6 = true;
		String personID = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			System.out.println(personID);
			timeTableDataBean.setExamSubject(vv.getNewValue().toString());
			if (personID != null) {
					controller.changeSubjectCode(personID, timeTableDataBean);
					System.out.println("subject -4- > " + timeTableDataBean.getExamSubjectCodes());
					timeTableDataBean.setExamSubjectCode3(timeTableDataBean.getExamSubjectCodes());
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	public void subjectcode4(ValueChangeEvent vv) {
		System.out.println("subject -5- > " + vv.getNewValue());
		next7 = true;
		String personID = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			System.out.println(personID);
			timeTableDataBean.setExamSubject(vv.getNewValue().toString());
			if (personID != null) {
					controller.changeSubjectCode(personID, timeTableDataBean);
					System.out.println("subject -5- > " + timeTableDataBean.getExamSubjectCodes());
					timeTableDataBean.setExamSubjectCode4(timeTableDataBean.getExamSubjectCodes());
				}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	public boolean validate2(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		for (int i = 0; i < timeTableDataBean.getClassList().size(); i++) {
			if (timeTableDataBean.getClassList().get(i).getExamSubject().equals(timeTableDataBean.getExamSubject())) {
				if (flag) {
					if (next2 == true) {
						fieldName = CommonValidate.findComponentInRoot("exSubject1").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Select Another one Subject"));
					} else if (next4 == true) {
						fieldName = CommonValidate.findComponentInRoot("exSubject3").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Select Another one Subject"));
					} else if (next6 == true) {
						fieldName = CommonValidate.findComponentInRoot("exSubject4").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Select Another one Subject"));
					} else if (next7 == true) {
						fieldName = CommonValidate.findComponentInRoot("exSubject5").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Select Another one Subject"));
					}
				}
				valid = false;
			}
		}
		return valid;
	}

	public String nextExam() {
		try {
			if (validate1(true)) {
				TimeTableDataBean nexts = new TimeTableDataBean();
				nexts.setExamClass(timeTableDataBean.getExamClass());
				nexts.setExamStartDate(timeTableDataBean.getExamStartDate());
				nexts.setExamDate(timeTableDataBean.getExamDate());
				nexts.setExamDay(timeTableDataBean.getExamDay());
				nexts.setExamTitle(timeTableDataBean.getExamTitle());
				nexts.setExamtTotalTime(timeTableDataBean.getExamtTotalTime());
				nexts.setExamEndTime(timeTableDataBean.getExamEndTime());
				nexts.setExamStartTime(timeTableDataBean.getExamStartTime());
				nexts.setExamRoomNo(timeTableDataBean.getExamRoomNo());
				nexts.setExamSubject(timeTableDataBean.getExamSubject());
				nexts.setExamSubjectCode(timeTableDataBean.getExamSubjectCode());
				nexts.setExamShift(timeTableDataBean.getExamShift());
				list.add(nexts);
				listcheck.add(nexts);
				timeTableDataBean.setClassList(list);
				System.out.println("exam size -- > " + timeTableDataBean.getClassList().size());
				next = true;
				next1 = false;
				next3 = false;
				next2 = true;
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}

	public String nextExam1() {
		next3 = false;
		next2 = true;
		try {
			if (validatez(true)) {
				TimeTableDataBean nexts = new TimeTableDataBean();
				nexts.setExamStartDate(timeTableDataBean.getExamStartDate1());
				nexts.setExamDay(timeTableDataBean.getExamDay1());
				nexts.setExamEndTime(timeTableDataBean.getExamEndTime1());
				nexts.setExamStartTime(timeTableDataBean.getExamStartTime1());
				nexts.setExamRoomNo(timeTableDataBean.getExamRoomNo1());
				nexts.setExamSubject(timeTableDataBean.getExamSubject1());
				nexts.setExamSubjectCode(timeTableDataBean.getExamSubjectCode1());
				nexts.setExamShift(timeTableDataBean.getExamShift1());
				list.add(nexts);
				listcheck.add(nexts);
				timeTableDataBean.setClassList(list);
				System.out.println("exam time table size -- > " + timeTableDataBean.getClassList().size());
				next3 = true;
				next2 = false;
				next4 = true;
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return "";
	}

	public String nextExam2() {
		next4 = true;
		try {
			if (validatez1(true)) {
				TimeTableDataBean nexts = new TimeTableDataBean();
				nexts.setExamStartDate(timeTableDataBean.getExamStartDate2());
				nexts.setExamDay(timeTableDataBean.getExamDay2());
				nexts.setExamEndTime(timeTableDataBean.getExamEndTime2());
				nexts.setExamStartTime(timeTableDataBean.getExamStartTime2());
				nexts.setExamRoomNo(timeTableDataBean.getExamRoomNo2());
				nexts.setExamSubject(timeTableDataBean.getExamSubject2());
				nexts.setExamSubjectCode(timeTableDataBean.getExamSubjectCode2());
				nexts.setExamShift(timeTableDataBean.getExamShift2());
				list.add(nexts);
				listcheck.add(nexts);
				timeTableDataBean.setClassList(list);
				System.out.println("exam time table size -- > " + timeTableDataBean.getClassList().size());
				next5 = true;
				next6 = true;
				next4 = false;
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return "";
	}

	public String nextExam3() {
		next6 = true;
		try {
			if (validatez2(true)) {
				TimeTableDataBean nexts = new TimeTableDataBean();
				nexts.setExamStartDate(timeTableDataBean.getExamStartDate3());
				nexts.setExamDay(timeTableDataBean.getExamDay3());
				nexts.setExamEndTime(timeTableDataBean.getExamEndTime3());
				nexts.setExamStartTime(timeTableDataBean.getExamStartTime3());
				nexts.setExamRoomNo(timeTableDataBean.getExamRoomNo3());
				nexts.setExamSubject(timeTableDataBean.getExamSubject3());
				nexts.setExamSubjectCode(timeTableDataBean.getExamSubjectCode3());
				nexts.setExamShift(timeTableDataBean.getExamShift3());
				list.add(nexts);
				listcheck.add(nexts);
				timeTableDataBean.setClassList(list);
				System.out.println("exam time table size -- > " + timeTableDataBean.getClassList().size());
				next7 = true;
				next6 = false;
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return "";
	}

	public String reset1() {

		logger.info("------reset method calling");
		timeTableDataBean.setExamDate(null);
		timeTableDataBean.setExamDay("");
		timeTableDataBean.setExamSubject("");
		timeTableDataBean.setExamSubjectCode("");
		timeTableDataBean.setExamRoomNo("");
		timeTableDataBean.setExamStartTime(null);
		timeTableDataBean.setExamEndTime(null);
		timeTableDataBean.setExamShift("");
		timeTableDataBean.setExamStartDate1(null);
		timeTableDataBean.setExamDate1(null);
		timeTableDataBean.setExamDay1("");
		timeTableDataBean.setExamSubject1("");
		timeTableDataBean.setExamSubjectCode1("");
		timeTableDataBean.setExamRoomNo1("");
		timeTableDataBean.setExamStartTime1(null);
		timeTableDataBean.setExamEndTime1(null);
		timeTableDataBean.setExamShift1("");
		timeTableDataBean.setExamStartDate2(null);
		timeTableDataBean.setExamDate2(null);
		timeTableDataBean.setExamDay2("");
		timeTableDataBean.setExamSubject2("");
		timeTableDataBean.setExamSubjectCode2("");
		timeTableDataBean.setExamRoomNo2("");
		timeTableDataBean.setExamStartTime2(null);
		timeTableDataBean.setExamEndTime2(null);
		timeTableDataBean.setExamShift2("");
		timeTableDataBean.setExamStartDate3(null);
		timeTableDataBean.setExamDate3(null);
		timeTableDataBean.setExamDay3("");
		timeTableDataBean.setExamSubject3("");
		timeTableDataBean.setExamSubjectCode3("");
		timeTableDataBean.setExamRoomNo3("");
		timeTableDataBean.setExamStartTime3(null);
		timeTableDataBean.setExamEndTime3(null);
		timeTableDataBean.setExamShift3("");
		timeTableDataBean.setExamStartDate4(null);
		timeTableDataBean.setExamDate4(null);
		timeTableDataBean.setExamDay4("");
		timeTableDataBean.setExamSubject4("");
		timeTableDataBean.setExamSubjectCode4("");
		timeTableDataBean.setExamRoomNo4("");
		timeTableDataBean.setExamStartTime4(null);
		timeTableDataBean.setExamEndTime4(null);
		timeTableDataBean.setExamShift4("");
		next = false;
		next1 = true;
		next2 = false;
		next3 = false;
		return "";
	}

	public void shiftChange(ValueChangeEvent v) {
		System.out.println("shift -- > " + v.getNewValue() + " - " + timeTableDataBean.getExamStartDate4());
		try {
			if (next1 == true) {
				timeTableDataBean.setExamShift(v.getNewValue().toString());
				if (validate4(true, v)) {

				}
			} else if (next2 == true) {
				timeTableDataBean.setExamShift1(v.getNewValue().toString());
				if (validate3(true)) {
					if (validate44(true, v)) {

					}
				}
			} else if (next4 == true) {
				timeTableDataBean.setExamShift2(v.getNewValue().toString());
				if (validate3(true)) {
					if (validate4z(true, v)) {

					}
				}
			} else if (next6 == true) {
				timeTableDataBean.setExamShift3(v.getNewValue().toString());
				if (validate3(true)) {
					if (validate41(true, v)) {

					}
				}
			} else if (next7 == true) {
				timeTableDataBean.setExamShift4(v.getNewValue().toString());
				if (validate3(true)) {
					if (validate42(true, v)) {

					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	public boolean validate3(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		for (int i = 0; i < timeTableDataBean.getClassList().size(); i++) {
			if (next2 == true) {
				if (timeTableDataBean.getClassList().get(i).getExamShift().equals(timeTableDataBean.getExamShift1())
						&& timeTableDataBean.getClassList().get(i).getExamStartDate()
								.equals(timeTableDataBean.getExamStartDate1())) {
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("exShift1").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Already Select this Shift"));
					}
					valid = false;
				}
			} else if (next4 == true) {
				if (timeTableDataBean.getClassList().get(i).getExamShift().equals(timeTableDataBean.getExamShift2())
						&& timeTableDataBean.getClassList().get(i).getExamStartDate()
								.equals(timeTableDataBean.getExamStartDate2())) {
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("exShift3").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Already Select this Shift"));
					}
					valid = false;
				}
			} else if (next6 == true) {
				if (timeTableDataBean.getClassList().get(i).getExamShift().equals(timeTableDataBean.getExamShift3())
						&& timeTableDataBean.getClassList().get(i).getExamStartDate()
								.equals(timeTableDataBean.getExamStartDate3())) {
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("exShift4").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Already Select this Shift"));
					}
					valid = false;
				}
			} else if (next7 == true) {
				if (timeTableDataBean.getClassList().get(i).getExamShift().equals(timeTableDataBean.getExamShift4())
						&& timeTableDataBean.getClassList().get(i).getExamStartDate()
								.equals(timeTableDataBean.getExamStartDate4())) {
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("exShift5").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Already Select this Shift"));
					}
					valid = false;
				}
			}
		}
		return valid;
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
		System.out.println("start time --  > " + timestart + " end time --  > " + timeend);
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
		System.out.println("start hour and min -- " + timestart);
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
		System.out.println("end hour and min -- " + timeend);
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
		System.out.println("hour -- > " + starthours + " minutes - - > " + startmin);
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
		if (timeTableDataBean.getExamtTotalTime().equals("30 Minutes")) {
			if ((Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 30)
					|| (Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 00)
					|| (Integer.parseInt(starthours) == -1 && Integer.parseInt(startmin) > 90)) {
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
		} else if (timeTableDataBean.getExamtTotalTime().equals("1 Hour 30 Minutes")) {
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
		} else if (timeTableDataBean.getExamtTotalTime().equals("2 Hours")) {
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
		} else if (timeTableDataBean.getExamtTotalTime().equals("2 Hours 30 Minutes")) {
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
		} else if (timeTableDataBean.getExamtTotalTime().equals("3 Hours")) {
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

	public boolean validate44(boolean flag, ValueChangeEvent v) {
		boolean valid = true;
		String fieldName;
		String starthours = "", starthour = "", endhour = "";
		;
		String startmin = "";
		String starttime = "";
		String endtime = "", time = "";
		;
		FacesContext fc = FacesContext.getCurrentInstance();
		starthour = String.valueOf(timeTableDataBean.getExamStartTime1().getHours());
		endhour = String.valueOf(timeTableDataBean.getExamEndTime1().getHours());
		String timestart = String.valueOf(timeTableDataBean.getExamStartTime1().getHours() + ":"
				+ timeTableDataBean.getExamStartTime1().getMinutes());
		String timeend = String.valueOf(timeTableDataBean.getExamEndTime1().getHours() + ":"
				+ timeTableDataBean.getExamEndTime1().getMinutes());
		System.out.println("start time --  > " + timestart + " end time --  > " + timeend);
		starthours = String.valueOf(timeTableDataBean.getExamStartTime1().getHours());
		startmin = String.valueOf(timeTableDataBean.getExamStartTime1().getMinutes());
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
		System.out.println("start hour and min -- " + timestart);
		starthours = String.valueOf(timeTableDataBean.getExamEndTime1().getHours());
		startmin = String.valueOf(timeTableDataBean.getExamEndTime1().getMinutes());
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
		System.out.println("end hour and min -- " + timeend);
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
		System.out.println("hour -- > " + starthours + " minutes - - > " + startmin);
		if (timeTableDataBean.getExamShift1().equals("Morning Shift")) {
			if (!((0 < Integer.parseInt(starthour)) && Integer.parseInt(starthour) <= 13)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exStartTime1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Select Correct Morning Time"));
				}
				valid = false;
			}
			if (!((0 < Integer.parseInt(endhour)) && Integer.parseInt(endhour) <= 13)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exEndTime1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Select Correct Morning Time"));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamShift1().equals("Evening Shift")) {
			if (!((13 <= Integer.parseInt(starthour)) && Integer.parseInt(starthour) < 18)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exStartTime1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Select Correct Evening Time"));
				}
				valid = false;
			}
			if (!((13 <= Integer.parseInt(endhour)) && Integer.parseInt(endhour) < 18)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exEndTime1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Select Correct Evening Time"));
				}
				valid = false;
			}
		}
		if (timeTableDataBean.getExamtTotalTime().equals("30 Minutes")) {
			if ((Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 30)
					|| (Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 00)
					|| (Integer.parseInt(starthours) == -1 && Integer.parseInt(startmin) > 90)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift1").getClientId(fc);
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
					fieldName = CommonValidate.findComponentInRoot("exShift1").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("1 Hour 30 Minutes")) {
			if ((Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 30)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 90)
					|| (Integer.parseInt(starthours) > 01 || Integer.parseInt(startmin) > 90)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift1").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("2 Hours")) {
			if ((Integer.parseInt(starthours) == 02 && Integer.parseInt(startmin) != 00)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 120)
					|| (Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 60)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift1").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("2 Hours 30 Minutes")) {
			if ((Integer.parseInt(starthours) == 02 && Integer.parseInt(startmin) != 30)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 150)
					|| (Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 90)
					|| (Integer.parseInt(starthours) > 02 && Integer.parseInt(startmin) != 30)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift1").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("3 Hours")) {
			if ((Integer.parseInt(starthours) == 03 && Integer.parseInt(startmin) != 00)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 180)
					|| (Integer.parseInt(starthours) == 02 && Integer.parseInt(startmin) != 60)
					|| (Integer.parseInt(starthours) < 03 && Integer.parseInt(startmin) != 60)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift1").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		}
		return valid;
	}

	public boolean validate4z(boolean flag, ValueChangeEvent v) {
		boolean valid = true;
		String fieldName;
		String starthours = "", starthour = "", endhour = "";
		;
		String startmin = "";
		String starttime = "";
		String endtime = "", time = "";
		;
		FacesContext fc = FacesContext.getCurrentInstance();
		starthour = String.valueOf(timeTableDataBean.getExamStartTime2().getHours());
		endhour = String.valueOf(timeTableDataBean.getExamEndTime2().getHours());
		String timestart = String.valueOf(timeTableDataBean.getExamStartTime2().getHours() + ":"
				+ timeTableDataBean.getExamStartTime2().getMinutes());
		String timeend = String.valueOf(timeTableDataBean.getExamEndTime2().getHours() + ":"
				+ timeTableDataBean.getExamEndTime2().getMinutes());
		System.out.println("start time --  > " + timestart + " end time --  > " + timeend);
		starthours = String.valueOf(timeTableDataBean.getExamStartTime2().getHours());
		startmin = String.valueOf(timeTableDataBean.getExamStartTime2().getMinutes());
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
		System.out.println("start hour and min -- " + timestart);
		starthours = String.valueOf(timeTableDataBean.getExamEndTime2().getHours());
		startmin = String.valueOf(timeTableDataBean.getExamEndTime2().getMinutes());
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
		System.out.println("end hour and min -- " + timeend);
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
		System.out.println("hour -- > " + starthours + " minutes - - > " + startmin);
		if (timeTableDataBean.getExamShift2().equals("Morning Shift")) {
			if (!((0 < Integer.parseInt(starthour)) && Integer.parseInt(starthour) <= 13)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exStartTime3").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Select Correct Morning Time"));
				}
				valid = false;
			}
			if (!((0 < Integer.parseInt(endhour)) && Integer.parseInt(endhour) <= 13)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exEndTime3").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Select Correct Morning Time"));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamShift2().equals("Evening Shift")) {
			if (!((13 <= Integer.parseInt(starthour)) && Integer.parseInt(starthour) < 18)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exStartTime3").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Select Correct Evening Time"));
				}
				valid = false;
			}
			if (!((13 <= Integer.parseInt(endhour)) && Integer.parseInt(endhour) < 18)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exEndTime3").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Select Correct Evening Time"));
				}
				valid = false;
			}
		}
		if (timeTableDataBean.getExamtTotalTime().equals("30 Minutes")) {
			if ((Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 30)
					|| (Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 00)
					|| (Integer.parseInt(starthours) == -1 && Integer.parseInt(startmin) > 90)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift3").getClientId(fc);
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
					fieldName = CommonValidate.findComponentInRoot("exShift3").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("1 Hour 30 Minutes")) {
			if ((Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 30)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 90)
					|| (Integer.parseInt(starthours) > 01 || Integer.parseInt(startmin) > 90)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift3").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("2 Hours")) {
			if ((Integer.parseInt(starthours) == 02 && Integer.parseInt(startmin) != 00)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 120)
					|| (Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 60)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift3").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("2 Hours 30 Minutes")) {
			if ((Integer.parseInt(starthours) == 02 && Integer.parseInt(startmin) != 30)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 150)
					|| (Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 90)
					|| (Integer.parseInt(starthours) > 02 && Integer.parseInt(startmin) != 30)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift3").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("3 Hours")) {
			if ((Integer.parseInt(starthours) == 03 && Integer.parseInt(startmin) != 00)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 180)
					|| (Integer.parseInt(starthours) == 02 && Integer.parseInt(startmin) != 60)
					|| (Integer.parseInt(starthours) < 03 && Integer.parseInt(startmin) != 60)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift3").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		}
		return valid;
	}

	public boolean validate41(boolean flag, ValueChangeEvent v) {
		boolean valid = true;
		String fieldName;
		String starthours = "", starthour = "", endhour = "";
		;
		String startmin = "";
		String starttime = "";
		String endtime = "", time = "";
		;
		FacesContext fc = FacesContext.getCurrentInstance();
		starthour = String.valueOf(timeTableDataBean.getExamStartTime3().getHours());
		endhour = String.valueOf(timeTableDataBean.getExamEndTime3().getHours());
		String timestart = String.valueOf(timeTableDataBean.getExamStartTime3().getHours() + ":"
				+ timeTableDataBean.getExamStartTime3().getMinutes());
		String timeend = String.valueOf(timeTableDataBean.getExamEndTime3().getHours() + ":"
				+ timeTableDataBean.getExamEndTime3().getMinutes());
		System.out.println("start time --  > " + timestart + " end time --  > " + timeend);
		starthours = String.valueOf(timeTableDataBean.getExamStartTime3().getHours());
		startmin = String.valueOf(timeTableDataBean.getExamStartTime3().getMinutes());
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
		System.out.println("start hour and min -- " + timestart);
		starthours = String.valueOf(timeTableDataBean.getExamEndTime3().getHours());
		startmin = String.valueOf(timeTableDataBean.getExamEndTime3().getMinutes());
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
		System.out.println("end hour and min -- " + timeend);
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
		System.out.println("hour -- > " + starthours + " minutes - - > " + startmin);
		if (timeTableDataBean.getExamShift3().equals("Morning Shift")) {
			if (!((0 < Integer.parseInt(starthour)) && Integer.parseInt(starthour) <= 13)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exStartTime4").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Select Correct Morning Time"));
				}
				valid = false;
			}
			if (!((0 < Integer.parseInt(endhour)) && Integer.parseInt(endhour) <= 13)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exEndTime4").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Select Correct Morning Time"));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamShift3().equals("Evening Shift")) {
			if (!((13 <= Integer.parseInt(starthour)) && Integer.parseInt(starthour) < 18)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exStartTime4").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Select Correct Evening Time"));
				}
				valid = false;
			}
			if (!((13 <= Integer.parseInt(endhour)) && Integer.parseInt(endhour) < 18)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exEndTime4").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Select Correct Evening Time"));
				}
				valid = false;
			}
		}
		if (timeTableDataBean.getExamtTotalTime().equals("30 Minutes")) {
			if ((Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 30)
					|| (Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 00)
					|| (Integer.parseInt(starthours) == -1 && Integer.parseInt(startmin) > 90)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift4").getClientId(fc);
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
					fieldName = CommonValidate.findComponentInRoot("exShift4").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("1 Hour 30 Minutes")) {
			if ((Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 30)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 90)
					|| (Integer.parseInt(starthours) > 01 || Integer.parseInt(startmin) > 90)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift4").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("2 Hours")) {
			if ((Integer.parseInt(starthours) == 02 && Integer.parseInt(startmin) != 00)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 120)
					|| (Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 60)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift4").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("2 Hours 30 Minutes")) {
			if ((Integer.parseInt(starthours) == 02 && Integer.parseInt(startmin) != 30)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 150)
					|| (Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 90)
					|| (Integer.parseInt(starthours) > 02 && Integer.parseInt(startmin) != 30)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift4").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("3 Hours")) {
			if ((Integer.parseInt(starthours) == 03 && Integer.parseInt(startmin) != 00)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 180)
					|| (Integer.parseInt(starthours) == 02 && Integer.parseInt(startmin) != 60)
					|| (Integer.parseInt(starthours) < 03 && Integer.parseInt(startmin) != 60)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift4").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		}
		return valid;
	}

	public boolean validate42(boolean flag, ValueChangeEvent v) {
		boolean valid = true;
		String fieldName;
		String starthours = "", starthour = "", endhour = "";
		;
		String startmin = "";
		String starttime = "";
		String endtime = "", time = "";
		;
		FacesContext fc = FacesContext.getCurrentInstance();
		starthour = String.valueOf(timeTableDataBean.getExamStartTime4().getHours());
		endhour = String.valueOf(timeTableDataBean.getExamEndTime4().getHours());
		String timestart = String.valueOf(timeTableDataBean.getExamStartTime4().getHours() + ":"
				+ timeTableDataBean.getExamStartTime4().getMinutes());
		String timeend = String.valueOf(timeTableDataBean.getExamEndTime4().getHours() + ":"
				+ timeTableDataBean.getExamEndTime4().getMinutes());
		System.out.println("start time --  > " + timestart + " end time --  > " + timeend);
		starthours = String.valueOf(timeTableDataBean.getExamStartTime4().getHours());
		startmin = String.valueOf(timeTableDataBean.getExamStartTime4().getMinutes());
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
		System.out.println("start hour and min -- " + timestart);
		starthours = String.valueOf(timeTableDataBean.getExamEndTime4().getHours());
		startmin = String.valueOf(timeTableDataBean.getExamEndTime4().getMinutes());
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
		System.out.println("end hour and min -- " + timeend);
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
		System.out.println("hour -- > " + starthours + " minutes - - > " + startmin);
		if (timeTableDataBean.getExamShift4().equals("Morning Shift")) {
			if (!((0 < Integer.parseInt(starthour)) && Integer.parseInt(starthour) <= 13)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exStartTime5").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Select Correct Morning Time"));
				}
				valid = false;
			}
			if (!((0 < Integer.parseInt(endhour)) && Integer.parseInt(endhour) <= 13)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exEndTime5").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Select Correct Morning Time"));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamShift4().equals("Evening Shift")) {
			if (!((13 <= Integer.parseInt(starthour)) && Integer.parseInt(starthour) < 18)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exStartTime5").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Select Correct Evening Time"));
				}
				valid = false;
			}
			if (!((13 <= Integer.parseInt(endhour)) && Integer.parseInt(endhour) < 18)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exEndTime5").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Select Correct Evening Time"));
				}
				valid = false;
			}
		}
		if (timeTableDataBean.getExamtTotalTime().equals("30 Minutes")) {
			if ((Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 30)
					|| (Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 00)
					|| (Integer.parseInt(starthours) == -1 && Integer.parseInt(startmin) > 90)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift5").getClientId(fc);
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
					fieldName = CommonValidate.findComponentInRoot("exShift5").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("1 Hour 30 Minutes")) {
			if ((Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 30)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 90)
					|| (Integer.parseInt(starthours) > 01 || Integer.parseInt(startmin) > 90)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift5").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("2 Hours")) {
			if ((Integer.parseInt(starthours) == 02 && Integer.parseInt(startmin) != 00)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 120)
					|| (Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 60)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift5").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("2 Hours 30 Minutes")) {
			if ((Integer.parseInt(starthours) == 02 && Integer.parseInt(startmin) != 30)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 150)
					|| (Integer.parseInt(starthours) == 01 && Integer.parseInt(startmin) != 90)
					|| (Integer.parseInt(starthours) > 02 && Integer.parseInt(startmin) != 30)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift5").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		} else if (timeTableDataBean.getExamtTotalTime().equals("3 Hours")) {
			if ((Integer.parseInt(starthours) == 03 && Integer.parseInt(startmin) != 00)
					|| (Integer.parseInt(starthours) == 00 && Integer.parseInt(startmin) != 180)
					|| (Integer.parseInt(starthours) == 02 && Integer.parseInt(startmin) != 60)
					|| (Integer.parseInt(starthours) < 03 && Integer.parseInt(startmin) != 60)) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("exShift5").getClientId(fc);
					fc.addMessage(fieldName,
							new FacesMessage("Total Exam Time is " + timeTableDataBean.getExamtTotalTime()));
				}
				valid = false;
			}
		}
		return valid;
	}

	private String generatePdf(TimeTableDataBean timeTableDataBean) {
		String status = "fail";
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("schoolID");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		int schoolid=Integer.parseInt(schoolID);
		
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {

				File files = new File(paths.getString("sms.timetable.pdf") + ft.format(now));
				if (!files.exists()) {
					files.mkdirs();
				} else {
					System.out.println("Alreday Found");
				}
				// Write PDF File
				OutputStream file = new FileOutputStream(
						new File(files + paths.getString("path_context").toString() + timeTableDataBean.getExamTitle() + ".pdf"));
				timeTableDataBean.setFilepath(files + paths.getString("path_context").toString() + timeTableDataBean.getExamTitle() + ".pdf");
				Document document = new Document(PageSize.A4, 50, 50, 10, 50);
				PdfWriter.getInstance(document, file);
				document.open();
				String logo ="";
				if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
					logo = paths.getString(schoolID+"_LOGO");
					document.add(Image.getInstance(getClass().getClassLoader().getResource(logo)));
				}else{
					logo = timeTableDataBean.getSchoolLogo();
					document.add(Image.getInstance(logo));
				}
				float[] columnWidths = { 20f, 1f };
				Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 16);
				PdfPTable table = new PdfPTable(2); //     .
				table.setWidthPercentage(100f);
				table.setWidths(columnWidths);
				PdfPCell cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(2);
				cell.setBorder(PdfPCell.NO_BORDER);
				PdfPCell cell1 = new PdfPCell(new Paragraph());
				cell1.setBorder(PdfPCell.NO_BORDER);
				PdfPCell cell2 = new PdfPCell(new Paragraph());
				cell2.setBorder(PdfPCell.NO_BORDER);
				PdfPCell cell3 = new PdfPCell(new Paragraph());
				cell3.setBorder(PdfPCell.NO_BORDER);
				PdfPCell cell4 = new PdfPCell(new Paragraph());
				cell4.setBorder(PdfPCell.NO_BORDER);
				PdfPCell cell5 = new PdfPCell(new Paragraph());
				cell5.setBorder(PdfPCell.NO_BORDER);
				table.setSpacingBefore(10f);
				table.setSpacingAfter(25f);

				PdfPTable headTable = new PdfPTable(5); //     .
				headTable.setWidthPercentage(100f);
				PdfPCell headCell = new PdfPCell(new Paragraph("Exam : ", font3));
				PdfPCell headCell1 = new PdfPCell(
						new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamTitle(), font3));
				PdfPCell headCell2 = new PdfPCell(new Paragraph("Class : ", font3));
				PdfPCell headCell3 = new PdfPCell(
						new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamClass(), font3));
				PdfPCell headCell4 = new PdfPCell(new Paragraph(""));
				headCell.setBorder(PdfPCell.NO_BORDER);
				headCell1.setBorder(PdfPCell.NO_BORDER);
				headCell2.setBorder(PdfPCell.NO_BORDER);
				headCell3.setBorder(PdfPCell.NO_BORDER);
				headCell4.setBorder(PdfPCell.NO_BORDER);
				headTable.addCell(headCell);
				headTable.addCell(headCell1);
				headTable.addCell(headCell2);
				headTable.addCell(headCell3);
				headTable.addCell(headCell4);
				headTable.setSpacingBefore(3f);
				headTable.setSpacingAfter(3f);
				cell1.addElement(headTable);

				PdfPTable nestedTable = new PdfPTable(5);
				nestedTable.setWidthPercentage(100f);
				PdfPCell nesCell1 = new PdfPCell(new Paragraph("Exam Date ", font3));
				PdfPCell nesCell2 = new PdfPCell(new Paragraph("Subject ", font3));
				PdfPCell nesCell3 = new PdfPCell(new Paragraph("Room No ", font3));
				//PdfPCell nesCell4 = new PdfPCell(new Paragraph("Shift ", font3));
				PdfPCell nesCell5 = new PdfPCell(new Paragraph("Day ", font3));
				/*
				 * PdfPCell nesCell5 = new PdfPCell(new
				 * Paragraph(""+dateFormat.format(timeTableDataBean.getClassList
				 * ().get(0).getExamStartDate())));
				 * 
				 * PdfPCell nesCell4 = new PdfPCell(new
				 * Paragraph(""+timeTableDataBean.getClassList().get(0).
				 * getExamSubject()));
				 */nesCell1.setBorder(PdfPCell.NO_BORDER);
				nesCell2.setBorder(PdfPCell.NO_BORDER);
				nesCell3.setBorder(PdfPCell.NO_BORDER);
				//nesCell4.setBorder(PdfPCell.NO_BORDER);
				nesCell5.setBorder(PdfPCell.NO_BORDER);
				nestedTable.addCell(nesCell1);
				nestedTable.addCell(nesCell2);
				nestedTable.addCell(nesCell3);
				//nestedTable.addCell(nesCell4);
				nestedTable.addCell(nesCell5);
				nestedTable.setSpacingBefore(3f);
				nestedTable.setSpacingAfter(3f);
				cell1.addElement(nestedTable);

				if (timeTableDataBean.getClassList().size() == 1) {
					PdfPTable nestedTable1 = new PdfPTable(5);
					nestedTable1.setWidthPercentage(100f);
					PdfPCell nesCell11 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(0).getExamStartDate())));
					PdfPCell nesCell12 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamSubject()));
					PdfPCell nesCell13 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamRoomNo()));
					//PdfPCell nesCell14 = new PdfPCell(new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamShift()));
					PdfPCell nesCell15 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamDay()));
					nesCell11.setBorder(PdfPCell.NO_BORDER);
					nesCell12.setBorder(PdfPCell.NO_BORDER);
					nesCell13.setBorder(PdfPCell.NO_BORDER);
					//nesCell14.setBorder(PdfPCell.NO_BORDER);
					nesCell15.setBorder(PdfPCell.NO_BORDER);
					nestedTable1.addCell(nesCell11);
					nestedTable1.addCell(nesCell12);
					nestedTable1.addCell(nesCell13);
					//nestedTable1.addCell(nesCell14);
					nestedTable1.addCell(nesCell15);
					nestedTable1.setSpacingBefore(3f);
					nestedTable1.setSpacingAfter(3f);
					cell1.addElement(nestedTable1);
				} else if (timeTableDataBean.getClassList().size() == 2) {
					PdfPTable nestedTable1 = new PdfPTable(5);
					nestedTable1.setWidthPercentage(100f);
					PdfPCell nesCell11 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(0).getExamStartDate())));
					PdfPCell nesCell12 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamSubject()));
					PdfPCell nesCell13 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamRoomNo()));
					/*PdfPCell nesCell14 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamShift()));*/
					PdfPCell nesCell15 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamDay()));
					nesCell11.setBorder(PdfPCell.NO_BORDER);
					nesCell12.setBorder(PdfPCell.NO_BORDER);
					nesCell13.setBorder(PdfPCell.NO_BORDER);
					//nesCell14.setBorder(PdfPCell.NO_BORDER);
					nesCell15.setBorder(PdfPCell.NO_BORDER);
					nestedTable1.addCell(nesCell11);
					nestedTable1.addCell(nesCell12);
					nestedTable1.addCell(nesCell13);
					//nestedTable1.addCell(nesCell14);
					nestedTable1.addCell(nesCell15);
					nestedTable1.setSpacingBefore(3f);
					nestedTable1.setSpacingAfter(3f);
					cell1.addElement(nestedTable1);

					PdfPTable nestedTable2 = new PdfPTable(5);
					nestedTable2.setWidthPercentage(100f);
					PdfPCell nesCell21 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(1).getExamStartDate())));
					PdfPCell nesCell22 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamSubject()));
					PdfPCell nesCell23 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamRoomNo()));
					/*PdfPCell nesCell24 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamShift()));*/
					PdfPCell nesCell25 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamDay()));
					nesCell21.setBorder(PdfPCell.NO_BORDER);
					nesCell22.setBorder(PdfPCell.NO_BORDER);
					nesCell23.setBorder(PdfPCell.NO_BORDER);
					//nesCell24.setBorder(PdfPCell.NO_BORDER);
					nesCell25.setBorder(PdfPCell.NO_BORDER);
					nestedTable2.addCell(nesCell21);
					nestedTable2.addCell(nesCell22);
					nestedTable2.addCell(nesCell23);
					//nestedTable2.addCell(nesCell24);
					nestedTable2.addCell(nesCell25);
					nestedTable2.setSpacingBefore(3f);
					nestedTable2.setSpacingAfter(3f);
					cell1.addElement(nestedTable2);
				} else if (timeTableDataBean.getClassList().size() == 3) {
					PdfPTable nestedTable1 = new PdfPTable(5);
					nestedTable1.setWidthPercentage(100f);
					PdfPCell nesCell11 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(0).getExamStartDate())));
					PdfPCell nesCell12 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamSubject()));
					PdfPCell nesCell13 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamRoomNo()));
					/*PdfPCell nesCell14 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamShift()));*/
					PdfPCell nesCell15 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamDay()));
					nesCell11.setBorder(PdfPCell.NO_BORDER);
					nesCell12.setBorder(PdfPCell.NO_BORDER);
					nesCell13.setBorder(PdfPCell.NO_BORDER);
					//nesCell14.setBorder(PdfPCell.NO_BORDER);
					nesCell15.setBorder(PdfPCell.NO_BORDER);
					nestedTable1.addCell(nesCell11);
					nestedTable1.addCell(nesCell12);
					nestedTable1.addCell(nesCell13);
					//nestedTable1.addCell(nesCell14);
					nestedTable1.addCell(nesCell15);
					nestedTable1.setSpacingBefore(3f);
					nestedTable1.setSpacingAfter(3f);
					cell1.addElement(nestedTable1);

					PdfPTable nestedTable2 = new PdfPTable(5);
					nestedTable2.setWidthPercentage(100f);
					PdfPCell nesCell21 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(1).getExamStartDate())));
					PdfPCell nesCell22 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamSubject()));
					PdfPCell nesCell23 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamRoomNo()));
					/*PdfPCell nesCell24 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamShift()));*/
					PdfPCell nesCell25 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamDay()));
					nesCell21.setBorder(PdfPCell.NO_BORDER);
					nesCell22.setBorder(PdfPCell.NO_BORDER);
					nesCell23.setBorder(PdfPCell.NO_BORDER);
					//nesCell24.setBorder(PdfPCell.NO_BORDER);
					nesCell25.setBorder(PdfPCell.NO_BORDER);
					nestedTable2.addCell(nesCell21);
					nestedTable2.addCell(nesCell22);
					nestedTable2.addCell(nesCell23);
					//nestedTable2.addCell(nesCell24);
					nestedTable2.addCell(nesCell25);
					nestedTable2.setSpacingBefore(3f);
					nestedTable2.setSpacingAfter(3f);
					cell1.addElement(nestedTable2);

					PdfPTable nestedTable3 = new PdfPTable(5);
					nestedTable3.setWidthPercentage(100f);
					PdfPCell nesCell31 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(2).getExamStartDate())));
					PdfPCell nesCell32 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamSubject()));
					PdfPCell nesCell33 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamRoomNo()));
					/*PdfPCell nesCell34 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamShift()));*/
					PdfPCell nesCell35 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamDay()));
					nesCell31.setBorder(PdfPCell.NO_BORDER);
					nesCell32.setBorder(PdfPCell.NO_BORDER);
					nesCell33.setBorder(PdfPCell.NO_BORDER);
					//nesCell34.setBorder(PdfPCell.NO_BORDER);
					nesCell35.setBorder(PdfPCell.NO_BORDER);
					nestedTable3.addCell(nesCell31);
					nestedTable3.addCell(nesCell32);
					nestedTable3.addCell(nesCell33);
					//nestedTable3.addCell(nesCell34);
					nestedTable3.addCell(nesCell35);
					nestedTable3.setSpacingBefore(3f);
					nestedTable3.setSpacingAfter(3f);
					cell1.addElement(nestedTable3);
				} else if (timeTableDataBean.getClassList().size() == 4) {
					PdfPTable nestedTable1 = new PdfPTable(5);
					nestedTable1.setWidthPercentage(100f);
					PdfPCell nesCell11 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(0).getExamStartDate())));
					PdfPCell nesCell12 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamSubject()));
					PdfPCell nesCell13 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamRoomNo()));
					/*PdfPCell nesCell14 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamShift()));*/
					PdfPCell nesCell15 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamDay()));
					nesCell11.setBorder(PdfPCell.NO_BORDER);
					nesCell12.setBorder(PdfPCell.NO_BORDER);
					nesCell13.setBorder(PdfPCell.NO_BORDER);
					//nesCell14.setBorder(PdfPCell.NO_BORDER);
					nesCell15.setBorder(PdfPCell.NO_BORDER);
					nestedTable1.addCell(nesCell11);
					nestedTable1.addCell(nesCell12);
					nestedTable1.addCell(nesCell13);
					//nestedTable1.addCell(nesCell14);
					nestedTable1.addCell(nesCell15);
					nestedTable1.setSpacingBefore(3f);
					nestedTable1.setSpacingAfter(3f);
					cell1.addElement(nestedTable1);

					PdfPTable nestedTable2 = new PdfPTable(5);
					nestedTable2.setWidthPercentage(100f);
					PdfPCell nesCell21 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(1).getExamStartDate())));
					PdfPCell nesCell22 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamSubject()));
					PdfPCell nesCell23 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamRoomNo()));
					/*PdfPCell nesCell24 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamShift()));*/
					PdfPCell nesCell25 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamDay()));
					nesCell21.setBorder(PdfPCell.NO_BORDER);
					nesCell22.setBorder(PdfPCell.NO_BORDER);
					nesCell23.setBorder(PdfPCell.NO_BORDER);
					//nesCell24.setBorder(PdfPCell.NO_BORDER);
					nesCell25.setBorder(PdfPCell.NO_BORDER);
					nestedTable2.addCell(nesCell21);
					nestedTable2.addCell(nesCell22);
					nestedTable2.addCell(nesCell23);
					//nestedTable2.addCell(nesCell24);
					nestedTable2.addCell(nesCell25);
					nestedTable2.setSpacingBefore(3f);
					nestedTable2.setSpacingAfter(3f);
					cell1.addElement(nestedTable2);

					PdfPTable nestedTable3 = new PdfPTable(5);
					nestedTable3.setWidthPercentage(100f);
					PdfPCell nesCell31 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(2).getExamStartDate())));
					PdfPCell nesCell32 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamSubject()));
					PdfPCell nesCell33 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamRoomNo()));
					/*PdfPCell nesCell34 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamShift()));*/
					PdfPCell nesCell35 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamDay()));
					nesCell31.setBorder(PdfPCell.NO_BORDER);
					nesCell32.setBorder(PdfPCell.NO_BORDER);
					nesCell33.setBorder(PdfPCell.NO_BORDER);
					//nesCell34.setBorder(PdfPCell.NO_BORDER);
					nesCell35.setBorder(PdfPCell.NO_BORDER);
					nestedTable3.addCell(nesCell31);
					nestedTable3.addCell(nesCell32);
					nestedTable3.addCell(nesCell33);
					//nestedTable3.addCell(nesCell34);
					nestedTable3.addCell(nesCell35);
					nestedTable3.setSpacingBefore(3f);
					nestedTable3.setSpacingAfter(3f);
					cell1.addElement(nestedTable3);

					PdfPTable nestedTable4 = new PdfPTable(5);
					nestedTable4.setWidthPercentage(100f);
					PdfPCell nesCell41 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(3).getExamStartDate())));
					PdfPCell nesCell42 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(3).getExamSubject()));
					PdfPCell nesCell43 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(3).getExamRoomNo()));
					/*PdfPCell nesCell44 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(3).getExamShift()));*/
					PdfPCell nesCell45 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(3).getExamDay()));
					nesCell41.setBorder(PdfPCell.NO_BORDER);
					nesCell42.setBorder(PdfPCell.NO_BORDER);
					nesCell43.setBorder(PdfPCell.NO_BORDER);
					//nesCell44.setBorder(PdfPCell.NO_BORDER);
					nesCell45.setBorder(PdfPCell.NO_BORDER);
					nestedTable4.addCell(nesCell41);
					nestedTable4.addCell(nesCell42);
					nestedTable4.addCell(nesCell43);
					//nestedTable4.addCell(nesCell44);
					nestedTable4.addCell(nesCell45);
					nestedTable4.setSpacingBefore(3f);
					nestedTable4.setSpacingAfter(3f);
					cell1.addElement(nestedTable4);
				} else if (timeTableDataBean.getClassList().size() == 5) {
					PdfPTable nestedTable1 = new PdfPTable(5);
					nestedTable1.setWidthPercentage(100f);
					PdfPCell nesCell11 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(0).getExamStartDate())));
					PdfPCell nesCell12 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamSubject()));
					PdfPCell nesCell13 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamRoomNo()));
					/*PdfPCell nesCell14 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamShift()));*/
					PdfPCell nesCell15 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(0).getExamDay()));
					nesCell11.setBorder(PdfPCell.NO_BORDER);
					nesCell12.setBorder(PdfPCell.NO_BORDER);
					nesCell13.setBorder(PdfPCell.NO_BORDER);
					//nesCell14.setBorder(PdfPCell.NO_BORDER);
					nesCell15.setBorder(PdfPCell.NO_BORDER);
					nestedTable1.addCell(nesCell11);
					nestedTable1.addCell(nesCell12);
					nestedTable1.addCell(nesCell13);
					//nestedTable1.addCell(nesCell14);
					nestedTable1.addCell(nesCell15);
					nestedTable1.setSpacingBefore(3f);
					nestedTable1.setSpacingAfter(3f);
					cell1.addElement(nestedTable1);

					PdfPTable nestedTable2 = new PdfPTable(5);
					nestedTable2.setWidthPercentage(100f);
					PdfPCell nesCell21 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(1).getExamStartDate())));
					PdfPCell nesCell22 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamSubject()));
					PdfPCell nesCell23 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamRoomNo()));
					/*PdfPCell nesCell24 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamShift()));*/
					PdfPCell nesCell25 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(1).getExamDay()));
					nesCell21.setBorder(PdfPCell.NO_BORDER);
					nesCell22.setBorder(PdfPCell.NO_BORDER);
					nesCell23.setBorder(PdfPCell.NO_BORDER);
					//nesCell24.setBorder(PdfPCell.NO_BORDER);
					nesCell25.setBorder(PdfPCell.NO_BORDER);
					nestedTable2.addCell(nesCell21);
					nestedTable2.addCell(nesCell22);
					nestedTable2.addCell(nesCell23);
					//nestedTable2.addCell(nesCell24);
					nestedTable2.addCell(nesCell25);
					nestedTable2.setSpacingBefore(3f);
					nestedTable2.setSpacingAfter(3f);
					cell1.addElement(nestedTable2);

					PdfPTable nestedTable3 = new PdfPTable(5);
					nestedTable3.setWidthPercentage(100f);
					PdfPCell nesCell31 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(2).getExamStartDate())));
					PdfPCell nesCell32 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamSubject()));
					PdfPCell nesCell33 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamRoomNo()));
					/*PdfPCell nesCell34 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamShift()));*/
					PdfPCell nesCell35 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(2).getExamDay()));
					nesCell31.setBorder(PdfPCell.NO_BORDER);
					nesCell32.setBorder(PdfPCell.NO_BORDER);
					nesCell33.setBorder(PdfPCell.NO_BORDER);
					//nesCell34.setBorder(PdfPCell.NO_BORDER);
					nesCell35.setBorder(PdfPCell.NO_BORDER);
					nestedTable3.addCell(nesCell31);
					nestedTable3.addCell(nesCell32);
					nestedTable3.addCell(nesCell33);
					//nestedTable3.addCell(nesCell34);
					nestedTable3.addCell(nesCell35);
					nestedTable3.setSpacingBefore(3f);
					nestedTable3.setSpacingAfter(3f);
					cell1.addElement(nestedTable3);

					PdfPTable nestedTable4 = new PdfPTable(5);
					nestedTable4.setWidthPercentage(100f);
					PdfPCell nesCell41 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(3).getExamStartDate())));
					PdfPCell nesCell42 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(3).getExamSubject()));
					PdfPCell nesCell43 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(3).getExamRoomNo()));
					/*PdfPCell nesCell44 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(3).getExamShift()));*/
					PdfPCell nesCell45 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(3).getExamDay()));
					nesCell41.setBorder(PdfPCell.NO_BORDER);
					nesCell42.setBorder(PdfPCell.NO_BORDER);
					nesCell43.setBorder(PdfPCell.NO_BORDER);
					//nesCell44.setBorder(PdfPCell.NO_BORDER);
					nesCell45.setBorder(PdfPCell.NO_BORDER);
					nestedTable4.addCell(nesCell41);
					nestedTable4.addCell(nesCell42);
					nestedTable4.addCell(nesCell43);
					//nestedTable4.addCell(nesCell44);
					nestedTable4.addCell(nesCell45);
					nestedTable4.setSpacingBefore(3f);
					nestedTable4.setSpacingAfter(3f);
					cell1.addElement(nestedTable4);

					PdfPTable nestedTable5 = new PdfPTable(5);
					nestedTable5.setWidthPercentage(100f);
					PdfPCell nesCell51 = new PdfPCell(new Paragraph(
							"" + dateFormat.format(timeTableDataBean.getClassList().get(4).getExamStartDate())));
					PdfPCell nesCell52 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(4).getExamSubject()));
					PdfPCell nesCell53 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(4).getExamRoomNo()));
					/*PdfPCell nesCell54 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(4).getExamShift()));*/
					PdfPCell nesCell55 = new PdfPCell(
							new Paragraph("" + timeTableDataBean.getClassList().get(4).getExamDay()));
					nesCell51.setBorder(PdfPCell.NO_BORDER);
					nesCell52.setBorder(PdfPCell.NO_BORDER);
					nesCell53.setBorder(PdfPCell.NO_BORDER);
					//nesCell54.setBorder(PdfPCell.NO_BORDER);
					nesCell55.setBorder(PdfPCell.NO_BORDER);
					nestedTable5.addCell(nesCell51);
					nestedTable5.addCell(nesCell52);
					nestedTable5.addCell(nesCell53);
					//nestedTable5.addCell(nesCell54);
					nestedTable5.addCell(nesCell55);
					nestedTable5.setSpacingBefore(3f);
					nestedTable5.setSpacingAfter(3f);
					cell1.addElement(nestedTable5);
				}

				table.addCell(cell);
				table.addCell(cell1);
				table.addCell(cell2);

				document.add(table);
				document.close();
				file.close();
				System.out.println("Done");
				status = "Success";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return status;
	}

	public String printPdf() {
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
		if (personID != null) {
			String pdfStatus = generatePdf(timeTableDataBean);
			System.out.println("pdf status -- > " + pdfStatus);
		}
		return "";
	}

	public void starttime(ValueChangeEvent v) throws ParseException {
		try {
			Date ss = (Date) v.getNewValue();
			if (next1 == true) {
				timeTableDataBean.setExamStartTime(ss);
			}
			if (next2 == true) {
				timeTableDataBean.setExamStartTime1(ss);
			} else if (next4 == true) {
				timeTableDataBean.setExamStartTime2(ss);
			} else if (next6 == true) {
				timeTableDataBean.setExamStartTime3(ss);
			} else if (next7 == true) {
				timeTableDataBean.setExamStartTime4(ss);
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	public void endtime(ValueChangeEvent v) throws ParseException {
		try {
			Date ss = (Date) v.getNewValue();
			System.out.println("time 1-- " + v.getNewValue().toString());
			if (next1 == true) {
				timeTableDataBean.setExamEndTime(ss);
			}
			if (next2 == true) {
				timeTableDataBean.setExamEndTime1(ss);
			} else if (next4 == true) {
				timeTableDataBean.setExamEndTime2(ss);
			} else if (next6 == true) {
				timeTableDataBean.setExamEndTime3(ss);
			} else if (next7 == true) {
				timeTableDataBean.setExamEndTime4(ss);
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	/*private String sendEmail(TimeTableDataBean timeTableDataBean) throws NoSuchProviderException {
		String status = "Fail";
		try {
			
			if(timeTableDataBean.getMails().size() > 0){
				timeTableDataBean.getMails().removeAll(Collections.singleton(null));  //remove null values from the list
				timeTableDataBean.getMails().removeAll(Collections.singleton(""));  //remove "" values from the list
			Set<String> dublicate=new HashSet<String>();
			dublicate.addAll(timeTableDataBean.getMails());
			timeTableDataBean.getMails().clear();
			timeTableDataBean.getMails().addAll(dublicate);
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
			String body1 = "";
			if (timeTableDataBean.getClassList().size() == 1) {
				body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>"
						+ "<center>" + "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>"
						+ "</header>" + "<h3>Dear </h3>"
						+ "<p>Welcome on-board into NRG family. Please find the your Exam Details enclosed with is mail."
						+ "All the very best in your " + timeTableDataBean.getExamTitle() + " Exams</p>"
						+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
						+ "<tr>" + "<td> " + " Exam Date" + "</td>" + "<td>" + " Subject" + "</td>" + "</tr>" + "<tr>"
						+ "<td> " + dateFormat.format(timeTableDataBean.getClassList().get(0).getExamStartDate())
						+ "</td>" + "<td>" + timeTableDataBean.getClassList().get(0).getExamSubject() + "</td>"
						+ "</tr>" + "</table>" + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>"
						+ "</center>" + "</footer>" + "</body></html>";
			} else if (timeTableDataBean.getClassList().size() == 2) {
				body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>"
						+ "<center>" + "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>"
						+ "</header>" + "<h3>Dear </h3>"
						+ "<p>Welcome on-board into NRG family. Please find the your Exam Details enclosed with is mail."
						+ "All the very best in your " + timeTableDataBean.getExamTitle() + " Exams</p>"
						+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
						+ "<tr>" + "<td> " + " Exam Date" + "</td>" + "<td>" + " Subject" + "</td>" + "</tr>" + "<tr>"
						+ "<td> " + dateFormat.format(timeTableDataBean.getClassList().get(0).getExamStartDate())
						+ "</td>" + "<td>" + timeTableDataBean.getClassList().get(0).getExamSubject() + "</td>"
						+ "</tr>" + "<tr>" + "<td> "
						+ dateFormat.format(timeTableDataBean.getClassList().get(1).getExamStartDate()) + "</td>"
						+ "<td>" + timeTableDataBean.getClassList().get(1).getExamSubject() + "</td>" + "</tr>"
						+ "</table>" + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>"
						+ "</center>" + "</footer>" + "</body></html>";
			} else if (timeTableDataBean.getClassList().size() == 3) {
				body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>"
						+ "<center>" + "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>"
						+ "</header>" + "<h3>Dear </h3>"
						+ "<p>Welcome on-board into NRG family. Please find the your Exam Details enclosed with is mail."
						+ "All the very best in your " + timeTableDataBean.getExamTitle() + " Exams</p>"
						+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
						+ "<tr>" + "<td> " + " Exam Date" + "</td>" + "<td>" + " Subject" + "</td>" + "</tr>" + "<tr>"
						+ "<td> " + dateFormat.format(timeTableDataBean.getClassList().get(0).getExamStartDate())
						+ "</td>" + "<td>" + timeTableDataBean.getClassList().get(0).getExamSubject() + "</td>"
						+ "</tr>" + "<tr>" + "<td> "
						+ dateFormat.format(timeTableDataBean.getClassList().get(1).getExamStartDate()) + "</td>"
						+ "<td>" + timeTableDataBean.getClassList().get(1).getExamSubject() + "</td>" + "</tr>" + "<tr>"
						+ "<td> "
						+ dateFormat.format(timeTableDataBean.getClassList().get(2).getExamStartDate())
						+ "</td>" + "<td>" + timeTableDataBean.getClassList().get(2).getExamSubject() + "</td>"
						+ "</tr>" + "</table>" + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>"
						+ "</center>" + "</footer>" + "</body></html>";
			} else if (timeTableDataBean.getClassList().size() == 4) {
				body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>"
						+ "<center>" + "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>"
						+ "</header>" + "<h3>Dear </h3>"
						+ "<p>Welcome on-board into NRG family. Please find the your Exam Details enclosed with is mail."
						+ "All the very best in your " + timeTableDataBean.getExamTitle() + " Exams</p>"
						+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
						+ "<tr>" + "<td> " + " Exam Date" + "</td>" + "<td>" + " Subject" + "</td>" + "</tr>" + "<tr>"
						+ "<td> " + dateFormat.format(timeTableDataBean.getClassList().get(0).getExamStartDate())
						+ "</td>" + "<td>" + timeTableDataBean.getClassList().get(0).getExamSubject() + "</td>"
						+ "</tr>" + "<tr>" + "<td> "
						+ dateFormat.format(timeTableDataBean.getClassList().get(1).getExamStartDate()) + "</td>"
						+ "<td>" + timeTableDataBean.getClassList().get(1).getExamSubject() + "</td>" + "</tr>" + "<tr>"
						+ "<td> " + dateFormat.format(timeTableDataBean.getClassList().get(2).getExamStartDate())
						+ "</td>" + "<td>" + timeTableDataBean.getClassList().get(2).getExamSubject() + "</td>"
						+ "</tr>" + "<tr>" + "<td> "
						+ dateFormat.format(timeTableDataBean.getClassList().get(3).getExamStartDate()) + "</td>"
						+ "<td>" + timeTableDataBean.getClassList().get(3).getExamSubject() + "</td>" + "</tr>"
						+ "</table>" + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>"
						+ "</center>" + "</footer>" + "</body></html>";
			} else if (timeTableDataBean.getClassList().size() == 5) {
				body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>"
						+ "<center>" + "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>"
						+ "</header>" + "<h3>Dear </h3>"
						+ "<p>Welcome on-board into NRG family. Please find the your Exam Details enclosed with is mail."
						+ "All the very best in your " + timeTableDataBean.getExamTitle() + " Exams</p>"
						+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
						+ "<tr>" + "<td> " + " Exam Date" + "</td>" + "<td>" + " Subject" + "</td>" + "</tr>" + "<tr>"
						+ "<td> " + dateFormat.format(timeTableDataBean.getClassList().get(0).getExamStartDate())
						+ "</td>" + "<td>" + timeTableDataBean.getClassList().get(0).getExamSubject() + "</td>"
						+ "</tr>" + "<tr>" + "<td> "
						+ dateFormat.format(timeTableDataBean.getClassList().get(1).getExamStartDate()) + "</td>"
						+ "<td>" + timeTableDataBean.getClassList().get(1).getExamSubject() + "</td>" + "</tr>" + "<tr>"
						+ "<td> " + dateFormat.format(timeTableDataBean.getClassList().get(2).getExamStartDate())
						+ "</td>" + "<td>" + timeTableDataBean.getClassList().get(2).getExamSubject() + "</td>"
						+ "</tr>" + "<tr>" + "<td> "
						+ dateFormat.format(timeTableDataBean.getClassList().get(3).getExamStartDate()) + "</td>"
						+ "<td>" + timeTableDataBean.getClassList().get(3).getExamSubject() + "</td>" + "</tr>" + "<tr>"
						+ "<td> " + dateFormat.format(timeTableDataBean.getClassList().get(4).getExamStartDate())
						+ "</td>" + "<td>" + timeTableDataBean.getClassList().get(4).getExamSubject() + "</td>"
						+ "</tr>" + "</table>" + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>"
						+ "</center>" + "</footer>" + "</body></html>";
			}

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
				message.setSubject("Exam Time Table");
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
		} 
		return status;
	}*/
	public void dateChange1(SelectEvent event) {
       
        Date date = (Date)event.getObject();
        jodadate=new DateTime(date);
        timeTableDataBean.setExamDay1(jodadate.dayOfWeek().getAsText(Locale.ENGLISH));
	}
	public void dateChange2(SelectEvent event) {
	       
        Date date = (Date)event.getObject();
        jodadate=new DateTime(date);
        timeTableDataBean.setExamDay2(jodadate.dayOfWeek().getAsText(Locale.ENGLISH));
	}
	public void dateChange3(SelectEvent event) {
	       
        Date date = (Date)event.getObject();
        jodadate=new DateTime(date);
        timeTableDataBean.setExamDay3(jodadate.dayOfWeek().getAsText(Locale.ENGLISH));
	}
	public void dateChange4(SelectEvent event) {
	       
        Date date = (Date)event.getObject();
        jodadate=new DateTime(date);
        timeTableDataBean.setExamDay4(jodadate.dayOfWeek().getAsText(Locale.ENGLISH));
	}
	/*prema 27/10/2016*/
	@PostConstruct
    public void init() {
        timeTableDataBean = new TimeTableDataBean();
        timesBean = new ArrayList<TimeTableDataBean>();         
    }
	public String addmarksGrade(){
		try{
			System.out.println("inside add marks and grade");	
			if(marksCheck(true))
			{
				timeTableDataBean=new TimeTableDataBean();
				timeTableDataBean.setExamTitle(examtitle);
				timeTableDataBean.setExamStartDate(examdate);
				timeTableDataBean.setExamClass(classs);
				timeTableDataBean.setExamtTotalTime(totaltime);
			}else {
				timesBean.remove(timesBean.size()-1);
			}
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}
	public boolean marksCheck(boolean flag){
		String fieldName;
		boolean valid=true;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (StringUtils.isEmpty(timeTableDataBean.getExamFromMark())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("fromMark").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.timetable.frommark")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(timeTableDataBean.getExamFromMark())) {
			if (!CommonValidate.validateNumber(timeTableDataBean.getExamFromMark())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("fromMark").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.timetable.vfrommark")));
				}
				valid = false;
			}
			if(Integer.parseInt(timeTableDataBean.getExamFromMark())>100){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("fromMark").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.timetable.vfrommarks")));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(timeTableDataBean.getExamToMark())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("toMark").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.timetable.tomark")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(timeTableDataBean.getExamToMark())) {
			if (!CommonValidate.validateNumber(timeTableDataBean.getExamToMark())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("toMark").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.timetable.vtomark")));
				}
				valid = false;
			}
			if(Integer.parseInt(timeTableDataBean.getExamToMark())>100){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("toMark").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.timetable.vtomarks")));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(timeTableDataBean.getExamGrade())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("grade").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.timetable.grade")));
			}
			valid = false;
		} 
		if (StringUtils.isEmpty(timeTableDataBean.getExamResult())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("result").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.timetable.result")));
			}
			valid = false;
		} 
		examtitle=timeTableDataBean.getExamTitle();
		examdate=timeTableDataBean.getExamStartDate();
		classs=timeTableDataBean.getExamClass();
		totaltime=timeTableDataBean.getExamtTotalTime();
		return valid;
	}
	
}
