package com.stumosys.managedBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
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
import org.primefaces.context.RequestContext;
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
import com.stumosys.domain.ClassTimeTableDataBean;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name = "classTimeTableMB") 
@RequestScoped
public class ClassTimeTableMB {

	ClassTimeTableDataBean classTimeTableDataBean = new ClassTimeTableDataBean();
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	Date now = new Date();
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	private static Logger logger = Logger.getLogger(ClassTimeTableMB.class);

	private List<String> classes;
	List<ClassTimeTableDataBean> periodlist = new ArrayList<ClassTimeTableDataBean>();
	private boolean flag = false;
	private boolean flag1 = false;
	private boolean flag2 = false;
	private boolean flag3 = false;
	private boolean flag4 = false;
	private boolean flag5 = false;
	private boolean flag6 = false;
	private boolean flag7 = false;
	private boolean flag8 = false;
	private boolean flag9 = false;
	private boolean flag10 = false;
	private boolean flag11 = false;
	private boolean flag12 = false;
	private boolean flag13 = false;
	private boolean confirmflag = false;
	private List<String> teacherIDList=null;
	private boolean panelFlag=false;
	public List<ClassTimeTableDataBean> classesBean;
	private List<String> subjectList=null;
	private String classname;
	private String day;
	private Date startTime;
	private Date endTime;
	private boolean codeflag=false;
	private boolean codeflag1=false;
	private List<String> subjectLists=null;
	public List<String> getSubjectLists() {
		return subjectLists;
	}

	public void setSubjectLists(List<String> subjectLists) {
		this.subjectLists = subjectLists;
	}

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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public List<String> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<String> subjectList) {
		this.subjectList = subjectList;
	}

	public List<ClassTimeTableDataBean> getClassesBean() {
		return classesBean;
	}

	public void setClassesBean(List<ClassTimeTableDataBean> classesBean) {
		this.classesBean = classesBean;
	}

	public boolean isPanelFlag() {
		return panelFlag;
	}

	public void setPanelFlag(boolean panelFlag) {
		this.panelFlag = panelFlag;
	}

	public boolean isConfirmflag() {
		return confirmflag;
	}

	public void setConfirmflag(boolean confirmflag) {
		this.confirmflag = confirmflag;
	}

	public boolean isFlag8() {
		return flag8;
	}

	public void setFlag8(boolean flag8) {
		this.flag8 = flag8;
	}

	public boolean isFlag9() {
		return flag9;
	}

	public void setFlag9(boolean flag9) {
		this.flag9 = flag9;
	}

	public boolean isFlag10() {
		return flag10;
	}

	public void setFlag10(boolean flag10) {
		this.flag10 = flag10;
	}

	public boolean isFlag11() {
		return flag11;
	}

	public void setFlag11(boolean flag11) {
		this.flag11 = flag11;
	}

	public boolean isFlag12() {
		return flag12;
	}

	public void setFlag12(boolean flag12) {
		this.flag12 = flag12;
	}

	public boolean isFlag13() {
		return flag13;
	}

	public void setFlag13(boolean flag13) {
		this.flag13 = flag13;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
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

	public boolean isFlag3() {
		return flag3;
	}

	public void setFlag3(boolean flag3) {
		this.flag3 = flag3;
	}

	public boolean isFlag4() {
		return flag4;
	}

	public void setFlag4(boolean flag4) {
		this.flag4 = flag4;
	}

	public boolean isFlag5() {
		return flag5;
	}

	public void setFlag5(boolean flag5) {
		this.flag5 = flag5;
	}

	public boolean isFlag6() {
		return flag6;
	}

	public void setFlag6(boolean flag6) {
		this.flag6 = flag6;
	}

	public boolean isFlag7() {
		return flag7;
	}

	public void setFlag7(boolean flag7) {
		this.flag7 = flag7;
	}

	public List<ClassTimeTableDataBean> getPeriodlist() {
		return periodlist;
	}

	public void setPeriodlist(List<ClassTimeTableDataBean> periodlist) {
		this.periodlist = periodlist;
	}

	public List<String> getClasses() {
		return classes;
	}

	public void setClasses(List<String> classes) {
		this.classes = classes;
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

	public ClassTimeTableDataBean getClassTimeTableDataBean() {
		return classTimeTableDataBean;
	}

	public void setClassTimeTableDataBean(ClassTimeTableDataBean classTimeTableDataBean) {
		this.classTimeTableDataBean = classTimeTableDataBean;
	}

	public String classtimetable() {
		logger.info("class time table call");
		try {
			periodlist=new ArrayList<ClassTimeTableDataBean>();
			classTimeTableDataBean.setMonthlist(new ArrayList<String>());
			
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			if (personID != null) {
				classTimeTableDataBean.setMonthlist(Arrays.asList("January","February","March","April","May","June","July","August","September","October","November","December"));
				classes = controller.classeslist(personID);
				Collections.sort(classes);
				logger.debug("school id "+schoolid);
				if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
					flag1 = true;
					flag2 = false;
				}else{
					flag1 = false;
					flag2 = true;
				}
				return "classTimeTable";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {
			confirmflag = false;
			classTimeTableDataBean.setClassname("");
			classTimeTableDataBean.setDay("");
			classTimeTableDataBean.setMonth("");
		}
		return "";
	}

	public String refresh() {
		classTimeTableDataBean.setClassname("");
		classTimeTableDataBean.setDay("");
		return "";
	}

	public String createTimeTable() {
		logger.info("create class time table ");
		classTimeTableDataBean.setSubjectlist(null);
		String temp="";
		String fieldName;
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			fieldName = CommonValidate.findComponentInRoot("validates").getClientId(fc);
			periodlist=new ArrayList<ClassTimeTableDataBean>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (validate(true)) {
				if (personID != null) {
					controller.classTimeTable(personID, classTimeTableDataBean);
					String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
					int schoolid=Integer.parseInt(schoolID);
						if (validation(true)) {
							subjectList=new ArrayList<String>();
							setClassname(classTimeTableDataBean.getClassname());
							setDay(classTimeTableDataBean.getDay());
							setCodeflag(true);
							setCodeflag1(false);
							setPanelFlag(true);	
							try{
								if(classTimeTableDataBean.getSubjectlist().size()>0){
									HashSet<String> sublist=new HashSet<String>(subjectList);
									subjectList.clear();
									subjectList.addAll(sublist);
									subjectList.addAll(classTimeTableDataBean.getSubjectlist());
									Collections.sort(subjectList);
									for (int i = 0; i < 8; i++) {
										ClassTimeTableDataBean templist=new ClassTimeTableDataBean();
										templist.setPeriod(i+1+"");
										periodlist.add(templist);
									}
									setSubjectLists(subjectList);
									fc.addMessage(fieldName, new FacesMessage(" "));
									flag1 = true;
								}
							}catch(Exception e){
								fieldName = CommonValidate.findComponentInRoot("validates").getClientId(fc);
								fc.addMessage(fieldName, new FacesMessage("There is no subject for this Class"));
							}
						}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {}
		return "";
	}

	public boolean validate(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (StringUtils.isEmpty(classTimeTableDataBean.getClassname())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("classtime").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Class."));
			}
			valid = false;
		}
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		int schoolid=Integer.parseInt(schoolID);
		/*if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
			if (StringUtils.isEmpty(classTimeTableDataBean.getDay())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("day").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Select the Day."));
				}
				valid = false;
			}
		}else{*/
		if (StringUtils.isEmpty(classTimeTableDataBean.getDay())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("day").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Select the Day."));
				}
				valid = false;
			}
		/*if (StringUtils.isEmpty(classTimeTableDataBean.getMonth())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("month").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Month."));
			}
			valid = false;
		}*/
			
		return valid;
	}

	public boolean validation(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (classTimeTableDataBean.getStatus().equalsIgnoreCase("Exist")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("validates").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Already registered the time table for "
						+ classTimeTableDataBean.getDay() + " to " + classTimeTableDataBean.getClassname()));
			}
			valid = false;
		}
		return valid;
	}

	public void subjectChange(ValueChangeEvent v) {
		logger.debug("subject change -  " + v.getNewValue());
		classTimeTableDataBean.getTeacherIDList().clear();
		String subject="";
		String no="";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			subject = v.getNewValue().toString();
			if (!subject.equalsIgnoreCase("")) {
			no = v.getComponent().getAttributes().get("period").toString();
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				teacherIDList = new ArrayList<String>();
				classTimeTableDataBean.getTeacherIDList().clear();
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
					obj.setPeriod(no);
					obj.setSubject(subject);
					obj.setSubjectCode(classTimeTableDataBean.getSubjectcodelist().get(0));
					obj.setTeacherIDList(teacherIDList);
					
					getPeriodlist().set(Integer.parseInt(no) - 1, obj);
					
				/*	setCodeflag1(false);setCodeflag(true);
					
				setSubjectList(null);
				setSubjectList(subjectLists);
				classTimeTableDataBean.setSubject(v.getNewValue().toString());*/
				
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
	
	public void subjectChange1(ValueChangeEvent v) {
		logger.debug("subject change 1-  " + v.getNewValue());
		int i=0;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				classTimeTableDataBean.getTeacherIDList().clear();
				classTimeTableDataBean.setSubject(v.getNewValue().toString());
				teacherIDList=controller.subjectChange(personID, classTimeTableDataBean);
				classTimeTableDataBean.setSubjectCode1(classTimeTableDataBean.getSubjectcodelist().get(i));
				classTimeTableDataBean.setSubject1(v.getNewValue().toString());
				classTimeTableDataBean.setTeacherIDList(teacherIDList);
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	public void subjectChange2(ValueChangeEvent v) {
		logger.debug("subject change 2-  " + v.getNewValue());
		int i=0;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				classTimeTableDataBean.getTeacherIDList2().clear();
				classTimeTableDataBean.setSubject(v.getNewValue().toString());
				teacherIDList=controller.subjectChange(personID, classTimeTableDataBean);
				System.out.println("------subject code---------->"+classTimeTableDataBean.getSubjectcodelist().get(i));
				classTimeTableDataBean.setSubjectCode2(classTimeTableDataBean.getSubjectcodelist().get(i));
				classTimeTableDataBean.setSubject2(v.getNewValue().toString());
				classTimeTableDataBean.setTeacherIDList2(teacherIDList);
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	public void subjectChange3(ValueChangeEvent v) {
		logger.debug("subject change 3-  " + v.getNewValue());
		int i=0;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				classTimeTableDataBean.getTeacherIDList3().clear();
				classTimeTableDataBean.setSubject(v.getNewValue().toString());
				teacherIDList=controller.subjectChange(personID, classTimeTableDataBean);
				classTimeTableDataBean.setSubjectCode3(classTimeTableDataBean.getSubjectcodelist().get(i));
				classTimeTableDataBean.setSubject3(v.getNewValue().toString());
				classTimeTableDataBean.setTeacherIDList3(teacherIDList);
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	public void subjectChange4(ValueChangeEvent v) {
		logger.debug("subject change 4-  " + v.getNewValue());
		int i=0;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				classTimeTableDataBean.getTeacherIDList4().clear();
				classTimeTableDataBean.setSubject(v.getNewValue().toString());
				teacherIDList=controller.subjectChange(personID, classTimeTableDataBean);
				classTimeTableDataBean.setSubjectCode4(classTimeTableDataBean.getSubjectcodelist().get(i));
				classTimeTableDataBean.setSubject4(v.getNewValue().toString());
				classTimeTableDataBean.setTeacherIDList4(teacherIDList);
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	public void subjectChange5(ValueChangeEvent v) {
		logger.debug("subject change 5-  " + v.getNewValue());
		int i=0;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				classTimeTableDataBean.getTeacherIDList5().clear();
				classTimeTableDataBean.setSubject(v.getNewValue().toString());
				teacherIDList=controller.subjectChange(personID, classTimeTableDataBean);
				classTimeTableDataBean.setSubjectCode5(classTimeTableDataBean.getSubjectcodelist().get(i));
				classTimeTableDataBean.setSubject5(v.getNewValue().toString());
				classTimeTableDataBean.setTeacherIDList5(teacherIDList);
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	public void subjectChange6(ValueChangeEvent v) {
		logger.debug("subject change 6-  " + v.getNewValue());
		int i=0;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				classTimeTableDataBean.getTeacherIDList6().clear();
				classTimeTableDataBean.setSubject(v.getNewValue().toString());
				teacherIDList=controller.subjectChange(personID, classTimeTableDataBean);
				classTimeTableDataBean.setSubjectCode6(classTimeTableDataBean.getSubjectcodelist().get(i));
				classTimeTableDataBean.setSubject6(v.getNewValue().toString());
				classTimeTableDataBean.setTeacherIDList6(teacherIDList);
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	public void subjectChange7(ValueChangeEvent v) {
		logger.debug("subject change 7-  " + v.getNewValue());
		int i=0;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				classTimeTableDataBean.getTeacherIDList7().clear();
				classTimeTableDataBean.setSubject(v.getNewValue().toString());
				teacherIDList=controller.subjectChange(personID, classTimeTableDataBean);
				classTimeTableDataBean.setSubjectCode7(classTimeTableDataBean.getSubjectcodelist().get(i));
				classTimeTableDataBean.setSubject7(v.getNewValue().toString());
				classTimeTableDataBean.setTeacherIDList7(teacherIDList);
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	public String period1() {
		logger.info("peroid 1");
		try {
			if (period1Vali(true)) {
				ClassTimeTableDataBean periods1 = new ClassTimeTableDataBean();
				periods1.setSubject(classTimeTableDataBean.getSubject());
				periods1.setSubjectCode(classTimeTableDataBean.getSubjectCode());
				periods1.setStartTime(classTimeTableDataBean.getStartTime());
				periods1.setEndTime(classTimeTableDataBean.getEndTime());
				periodlist.add(periods1);
				classTimeTableDataBean.setClasstimeList(periodlist);
				flag1 = true;
				flag = false;
				flag2 = true;
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return "";
	}

	public boolean period1Vali(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (classTimeTableDataBean.getSubject().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("subjectt").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getStartTime() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("startTime").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Start Time."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getEndTime() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("endTime").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select End Time."));
			}
			valid = false;
		}
		return valid;
	}

	public String period2() {
		logger.info("peroid 2");
		try {
			if (period2Vali(true)) {
				ClassTimeTableDataBean periods1 = new ClassTimeTableDataBean();
				periods1.setSubject(classTimeTableDataBean.getSubject1());
				periods1.setSubjectCode(classTimeTableDataBean.getSubjectCode1());
				periods1.setStartTime(classTimeTableDataBean.getStartTime1());
				periods1.setEndTime(classTimeTableDataBean.getEndTime1());
				periodlist.add(periods1);
				classTimeTableDataBean.setClasstimeList(periodlist);
				flag2 = false;
				flag3 = true;
				flag4 = true;
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return "";
	}

	public boolean period2Vali(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (classTimeTableDataBean.getSubject1().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("subjectt1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getStartTime1() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("startTime1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Start Time."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getEndTime1() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("endTime1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select End Time."));
			}
			valid = false;
		}
		return valid;
	}

	public String period3() {
		logger.info("peroid 3");
		try {
			if (period3Vali(true)) {
				ClassTimeTableDataBean periods1 = new ClassTimeTableDataBean();
				periods1.setSubject(classTimeTableDataBean.getSubject2());
				periods1.setSubjectCode(classTimeTableDataBean.getSubjectCode2());
				periods1.setStartTime(classTimeTableDataBean.getStartTime2());
				periods1.setEndTime(classTimeTableDataBean.getEndTime2());
				periodlist.add(periods1);
				classTimeTableDataBean.setClasstimeList(periodlist);
				flag4 = false;
				flag5 = true;
				flag6 = true;
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return "";
	}

	public boolean period3Vali(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (classTimeTableDataBean.getSubject2().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("subjectt2").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getStartTime2() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("startTime2").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Start Time."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getEndTime2() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("endTime2").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select End Time."));
			}
			valid = false;
		}
		return valid;
	}

	public String period4() {
		logger.info("peroid 4");
		try {
			if (period4Vali(true)) {
				ClassTimeTableDataBean periods1 = new ClassTimeTableDataBean();
				periods1.setSubject(classTimeTableDataBean.getSubject3());
				periods1.setSubjectCode(classTimeTableDataBean.getSubjectCode3());
				periods1.setStartTime(classTimeTableDataBean.getStartTime3());
				periods1.setEndTime(classTimeTableDataBean.getEndTime3());
				periodlist.add(periods1);
				classTimeTableDataBean.setClasstimeList(periodlist);
				flag6 = false;
				flag7 = true;
				flag8 = true;
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return "";
	}

	public boolean period4Vali(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (classTimeTableDataBean.getSubject3().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("subjectt3").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getStartTime3() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("startTime3").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Start Time."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getEndTime3() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("endTime3").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select End Time."));
			}
			valid = false;
		}
		return valid;
	}

	public String period5() {
		logger.info("peroid 5");
		try {
			if (period5Vali(true)) {
				ClassTimeTableDataBean periods1 = new ClassTimeTableDataBean();
				periods1.setSubject(classTimeTableDataBean.getSubject4());
				periods1.setSubjectCode(classTimeTableDataBean.getSubjectCode4());
				periods1.setStartTime(classTimeTableDataBean.getStartTime4());
				periods1.setEndTime(classTimeTableDataBean.getEndTime4());
				periodlist.add(periods1);
				classTimeTableDataBean.setClasstimeList(periodlist);
				flag8 = false;
				flag9 = true;
				flag10 = true;
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return "";
	}

	public boolean period5Vali(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (classTimeTableDataBean.getSubject4().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("subjectt4").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getStartTime4() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("startTime4").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Start Time."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getEndTime4() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("endTime4").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select End Time."));
			}
			valid = false;
		}
		return valid;
	}

	public String period6() {
		logger.info("peroid 6");
		try {
			if (period6Vali(true)) {
				ClassTimeTableDataBean periods1 = new ClassTimeTableDataBean();
				periods1.setSubject(classTimeTableDataBean.getSubject5());
				periods1.setSubjectCode(classTimeTableDataBean.getSubjectCode5());
				periods1.setStartTime(classTimeTableDataBean.getStartTime5());
				periods1.setEndTime(classTimeTableDataBean.getEndTime5());
				periodlist.add(periods1);
				classTimeTableDataBean.setClasstimeList(periodlist);
				flag10 = false;
				flag11 = true;
				flag12 = true;
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return "";
	}

	public boolean period6Vali(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (classTimeTableDataBean.getSubject5().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("subjectt5").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getStartTime5() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("startTime5").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Start Time."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getEndTime5() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("endTime5").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select End Time."));
			}
			valid = false;
		}
		return valid;
	}

	public String period7() {
		logger.info("peroid 7");
		try {
			if (period7Vali(true)) {
				ClassTimeTableDataBean periods1 = new ClassTimeTableDataBean();
				periods1.setSubject(classTimeTableDataBean.getSubject6());
				periods1.setSubjectCode(classTimeTableDataBean.getSubjectCode6());
				periods1.setStartTime(classTimeTableDataBean.getStartTime6());
				periods1.setEndTime(classTimeTableDataBean.getEndTime6());
				periodlist.add(periods1);
				classTimeTableDataBean.setClasstimeList(periodlist);
				flag12 = false;
				flag13 = true;
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return "";
	}

	public boolean period7Vali(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (classTimeTableDataBean.getSubject6().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("subjectt6").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getStartTime6() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("startTime6").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Start Time."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getEndTime6() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("endTime6").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select End Time."));
			}
			valid = false;
		}
		return valid;
	}

	public String classTimeTable() {
		logger.info("inside classs table insert");
		confirmflag = false;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (saveValidate(true)) {
				if (personID != null) {
					ClassTimeTableDataBean periods1 = new ClassTimeTableDataBean();
					periods1.setSubject(classTimeTableDataBean.getSubject7());
					periods1.setSubjectCode(classTimeTableDataBean.getSubjectCode7());
					periods1.setStartTime(classTimeTableDataBean.getStartTime7());
					periods1.setEndTime(classTimeTableDataBean.getEndTime7());
					periodlist.add(periods1);
					classTimeTableDataBean.setClasstimeList(periodlist);
					logger.debug("table size in mb -- " + classTimeTableDataBean.getClasstimeList().size());
					controller.saveClassTimeTable(classTimeTableDataBean, personID);
					String pdfStatus = generatePdf(classTimeTableDataBean);
					if (pdfStatus.equalsIgnoreCase("Success")) {
						String emailStatus = sendEmail(classTimeTableDataBean);
						if (emailStatus.equalsIgnoreCase("Success")) {
							confirmflag = true;
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

	public String classTimTable() {
		confirmflag = false;
		String personID = "";
		periodlist=null;
		try {
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				if (personID != null) {
					periodlist = new ArrayList<ClassTimeTableDataBean>();
					periodlist.addAll(classesBean);
					logger.debug("period list size-"+periodlist.size()+"-----"+classesBean.size());
					if(periodlist.size()>0){
						RequestContext reqcontextt = RequestContext.getCurrentInstance();
						reqcontextt.execute("classBlk();");
					}else{
						String fieldName;
						FacesContext fc = FacesContext.getCurrentInstance();
						fieldName = CommonValidate.findComponentInRoot("ermsg").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please add atleast one period."));
					}					
				}
			}else{
				if (saveValidate(true)) {
					if (personID != null) {
						//Add To List
						logger.debug(classTimeTableDataBean.getEndTime()+"from" + classTimeTableDataBean.getStartTime());
						DateFormat df = new SimpleDateFormat("hh:mm");
						String hour = df.format(classTimeTableDataBean.getEndTime());
						logger.debug("Hour: " + hour);
						periodlist = new ArrayList<ClassTimeTableDataBean>();
						ClassTimeTableDataBean periods = new ClassTimeTableDataBean();
						periods.setSubject(classTimeTableDataBean.getSubject());
						periods.setSubjectCode(classTimeTableDataBean.getSubjectCode());
						periods.setStartTime(classTimeTableDataBean.getStartTime());
						periods.setEndTime(classTimeTableDataBean.getEndTime());
						periods.setTeaID(classTimeTableDataBean.getTeaID());
						periodlist.add(periods);
						ClassTimeTableDataBean periods1 = new ClassTimeTableDataBean();
						periods1.setSubject(classTimeTableDataBean.getSubject1());
						periods1.setSubjectCode(classTimeTableDataBean.getSubjectCode1());
						periods1.setStartTime(classTimeTableDataBean.getStartTime1());
						periods1.setEndTime(classTimeTableDataBean.getEndTime1());
						periods1.setTeaID(classTimeTableDataBean.getTeaID1());
						periodlist.add(periods1);
						ClassTimeTableDataBean periods2 = new ClassTimeTableDataBean();
						periods2.setSubject(classTimeTableDataBean.getSubject2());
						periods2.setSubjectCode(classTimeTableDataBean.getSubjectCode2());
						periods2.setStartTime(classTimeTableDataBean.getStartTime2());
						periods2.setEndTime(classTimeTableDataBean.getEndTime2());
						periods2.setTeaID(classTimeTableDataBean.getTeaID2());
						periodlist.add(periods2);
						ClassTimeTableDataBean periods3 = new ClassTimeTableDataBean();
						periods3.setSubject(classTimeTableDataBean.getSubject3());
						periods3.setSubjectCode(classTimeTableDataBean.getSubjectCode3());
						periods3.setStartTime(classTimeTableDataBean.getStartTime3());
						periods3.setEndTime(classTimeTableDataBean.getEndTime3());
						periods3.setTeaID(classTimeTableDataBean.getTeaID3());
						periodlist.add(periods3);
						ClassTimeTableDataBean periods4 = new ClassTimeTableDataBean();
						periods4.setSubject(classTimeTableDataBean.getSubject4());
						periods4.setSubjectCode(classTimeTableDataBean.getSubjectCode4());
						periods4.setStartTime(classTimeTableDataBean.getStartTime4());
						periods4.setEndTime(classTimeTableDataBean.getEndTime4());
						periods4.setTeaID(classTimeTableDataBean.getTeaID4());
						periodlist.add(periods4);
						ClassTimeTableDataBean periods5 = new ClassTimeTableDataBean();
						periods5.setSubject(classTimeTableDataBean.getSubject5());
						periods5.setSubjectCode(classTimeTableDataBean.getSubjectCode5());
						periods5.setStartTime(classTimeTableDataBean.getStartTime5());
						periods5.setEndTime(classTimeTableDataBean.getEndTime5());
						periods5.setTeaID(classTimeTableDataBean.getTeaID5());
						periodlist.add(periods5);
						ClassTimeTableDataBean periods6 = new ClassTimeTableDataBean();
						periods6.setSubject(classTimeTableDataBean.getSubject6());
						periods6.setSubjectCode(classTimeTableDataBean.getSubjectCode6());
						periods6.setStartTime(classTimeTableDataBean.getStartTime6());
						periods6.setEndTime(classTimeTableDataBean.getEndTime6());
						periods6.setTeaID(classTimeTableDataBean.getTeaID6());
						periodlist.add(periods6);
						ClassTimeTableDataBean periods7 = new ClassTimeTableDataBean();
						periods7.setSubject(classTimeTableDataBean.getSubject7());
						periods7.setSubjectCode(classTimeTableDataBean.getSubjectCode7());
						periods7.setStartTime(classTimeTableDataBean.getStartTime7());
						periods7.setEndTime(classTimeTableDataBean.getEndTime7());
						periods7.setTeaID(classTimeTableDataBean.getTeaID7());
						periodlist.add(periods7);
						logger.debug(periodlist.size());
						RequestContext reqcontextt = RequestContext.getCurrentInstance();
						reqcontextt.execute("classBlk();");
				}
			}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return "";
	}
	public boolean ValidateInsertTimetable(boolean flag) {
		  boolean valid = true;
		  String fieldName;
		  FacesContext fc = FacesContext.getCurrentInstance();
		  try {
		   if (StringUtils.isEmpty(periodlist.get(0).getSubject())) {
		    if (flag) {
		     fieldName = CommonValidate.findComponentInRoot("classtimetable").getClientId(fc);
		     fc.addMessage(fieldName, new FacesMessage("Please Choose Atleast one Period"));
		    }
		    valid = false;
		   }
		   else{
		    if (StringUtils.isEmpty(periodlist.get(0).getTeaID())) {
		     if (flag) {
		     fieldName = CommonValidate.findComponentInRoot("classtimetable").getClientId(fc);
		     fc.addMessage(fieldName, new FacesMessage("Choose Teacher Name For Period 1"));
		    }
		    valid = false;
		    }
		    else if (periodlist.get(0).getStartTime()==null) {
		     if (flag) {
		     fieldName = CommonValidate.findComponentInRoot("classtimetable").getClientId(fc);
		     fc.addMessage(fieldName, new FacesMessage("Choose Start Time For Period 1"));
		    }
		    valid = false;
		    }
		    else if (periodlist.get(0).getEndTime()==null) {
		     if (flag) {
		     fieldName = CommonValidate.findComponentInRoot("classtimetable").getClientId(fc);
		     fc.addMessage(fieldName, new FacesMessage("Choose End Time For Period 1"));
		    }
		    valid = false;
		    }
		   }
		   for (int i = 1; i < periodlist.size(); i++) {
		    if (StringUtils.isEmpty(periodlist.get(i).getSubject())) {
		     logger.info("inside if condition ValidateInsertTimetable ");
		    }
		    else{
		     if (StringUtils.isEmpty(periodlist.get(i).getTeaID())) {
		      if (flag) {
		      fieldName = CommonValidate.findComponentInRoot("classtimetable").getClientId(fc);
		      fc.addMessage(fieldName, new FacesMessage("Choose Teacher Name For Period "+(i+1)));
		     }
		     valid = false;
		     }
		     else if (periodlist.get(i).getStartTime()==null){
		      if (flag) {
		      fieldName = CommonValidate.findComponentInRoot("classtimetable").getClientId(fc);
		      fc.addMessage(fieldName, new FacesMessage("Choose Start Time For Period "+(i+1)));
		     }
		     valid = false;
		     }
		     else if (periodlist.get(i).getEndTime()==null) {
		      if (flag) {
		      fieldName = CommonValidate.findComponentInRoot("classtimetable").getClientId(fc);
		      fc.addMessage(fieldName, new FacesMessage("Choose End Time For Period "+(i+1)));
		     }
		     valid = false;
		     }
		    }
		   }
		   logger.info("inside ValidateInsertTimetable VALID value"+valid);
		  } catch (Exception e) {
		   logger.warn("Exception -->"+e.getMessage());
		   e.printStackTrace();
		  }
		  return valid;
		 }
	public String classTimeTableInsert() {
		logger.info("inside classs table insert");
		confirmflag = false;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
		if (ValidateInsertTimetable(true)) {
		
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
				String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
				classTimeTableDataBean.setClasstimeList(periodlist);
				logger.debug("table size in mb -- " + classTimeTableDataBean.getClasstimeList().size());
				controller.saveClassTimeTable(classTimeTableDataBean, personID);
				/*String pdfStatus = generatePdf(classTimeTableDataBean);*/
				MailSendJDBC.classTimeTableInsert(classTimeTableDataBean,schoolName,schoolID);
				/*System.out.println("pdf status------"+pdfStatus);*/
				if (classTimeTableDataBean.getStatus().equalsIgnoreCase("Success")) {
					/*String emailStatus = sendEmail(classTimeTableDataBean);*/
					/*if (emailStatus.equalsIgnoreCase("Success")) {*/
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('classblocksUI').hide();");
						reqcontext.execute("PF('classTime').show();");
						confirmflag = true;
					/*}*/
				}
			}
		}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			e.printStackTrace();
		} finally {

		}
		return "";
	}

	public boolean saveValidate(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (classTimeTableDataBean.getSubject().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("subjectt").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getStartTime() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("startTime").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Start Time."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getEndTime() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("endTime").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select End Time."));
			}
			valid = false;
		}
		if(StringUtils.isEmpty(classTimeTableDataBean.getTeaID())){
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("teaID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select TeacherID."));
			}
			valid = false;
		}
		
		if (classTimeTableDataBean.getSubject1().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("subjectt1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getStartTime1() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("startTime1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Start Time."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getEndTime1() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("endTime1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select End Time."));
			}
			valid = false;
		}
		if(StringUtils.isEmpty(classTimeTableDataBean.getTeaID1())){
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("teaID1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select TeacherID."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getSubject2().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("subjectt2").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getStartTime2() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("startTime2").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Start Time."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getEndTime2() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("endTime2").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select End Time."));
			}
			valid = false;
		}
		if(StringUtils.isEmpty(classTimeTableDataBean.getTeaID2())){
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("teaID2").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select TeacherID."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getSubject3().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("subjectt3").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getStartTime3() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("startTime3").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Start Time."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getEndTime3() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("endTime3").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select End Time."));
			}
			valid = false;
		}
		if(StringUtils.isEmpty(classTimeTableDataBean.getTeaID3())){
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("teaID3").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select TeacherID."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getSubject4().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("subjectt4").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getStartTime4() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("startTime4").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Start Time."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getEndTime4() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("endTime4").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select End Time."));
			}
			valid = false;
		}
		if(StringUtils.isEmpty(classTimeTableDataBean.getTeaID4())){
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("teaID4").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select TeacherID."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getSubject5().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("subjectt5").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getStartTime5() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("startTime5").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Start Time."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getEndTime5() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("endTime5").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select End Time."));
			}
			valid = false;
		}
		if(StringUtils.isEmpty(classTimeTableDataBean.getTeaID5())){
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("teaID5").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select TeacherID."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getSubject6().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("subjectt6").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getStartTime6() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("startTime6").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Start Time."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getEndTime6() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("endTime6").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select End Time."));
			}
			valid = false;
		}
		if(StringUtils.isEmpty(classTimeTableDataBean.getTeaID6())){
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("teaID6").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select TeacherID."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getSubject7().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("subjectt7").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getStartTime7() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("startTime7").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Start Time."));
			}
			valid = false;
		}
		if (classTimeTableDataBean.getEndTime7() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("endTime7").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select End Time."));
			}
			valid = false;
		}
		if(StringUtils.isEmpty(classTimeTableDataBean.getTeaID7())){
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("teaID7").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select TeacherID."));
			}
			valid = false;
		}
		return valid;
	}

	public String returnForm() {
		confirmflag = false;
		classTimeTableDataBean.setClassname("");
		classTimeTableDataBean.setDay("");
		flag1 = false;
		return "";
	}

	private String sendEmail(ClassTimeTableDataBean classTimeTableDataBean) throws NoSuchProviderException {
		String status = "Fail";
		String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("schoolName");
		try {
			if(classTimeTableDataBean.getMails().size() > 0){
				classTimeTableDataBean.getMails().removeAll(Collections.singleton(""));
				classTimeTableDataBean.getMails().removeAll(Collections.singleton(null));
				Set<String> dublicate=new HashSet<String>();
				dublicate.addAll(classTimeTableDataBean.getMails());
				classTimeTableDataBean.getMails().clear();
				classTimeTableDataBean.getMails().addAll(dublicate);
				
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
					+ "<p>Welcome on-board into"
					+ schoolName
					+ " family. Please find the your class time table for "
					+ classTimeTableDataBean.getDay() + " , the details enclosed with is mail." + "</p>"
					+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
					+ "<tr>" + "<td> " + " Period " + "</td>" + "<td>" + " Subject" + "</td>" + "</tr>" + "<tr>"
					+ "<td> " + 1 + "</td>" + "<td>" + classTimeTableDataBean.getClasstimeList().get(0).getSubject()
					+ "</td>" + "</tr>" + "<tr>" + "<td> " + 2 + "</td>" + "<td>"
					+ classTimeTableDataBean.getClasstimeList().get(1).getSubject() + "</td>" + "</tr>" + "<tr>"
					+ "<td> " + 3 + "</td>" + "<td>" + classTimeTableDataBean.getClasstimeList().get(2).getSubject()
					+ "</td>" + "</tr>" + "<tr>" + "<td> " + 4 + "</td>" + "<td>"
					+ classTimeTableDataBean.getClasstimeList().get(3).getSubject() + "</td>" + "</tr>" + "<tr>"
					+ "<td> " + 5 + "</td>" + "<td>" + classTimeTableDataBean.getClasstimeList().get(4).getSubject()
					+ "</td>" + "</tr>" + "<tr>" + "<td> " + 6 + "</td>" + "<td>"
					+ classTimeTableDataBean.getClasstimeList().get(5).getSubject() + "</td>" + "</tr>" + "<tr>"
					+ "<td> " + 7 + "</td>" + "<td>" + classTimeTableDataBean.getClasstimeList().get(6).getSubject()
					+ "</td>" + "</tr>" + "<tr>" + "<td> " + 8 + "</td>" + "<td>"
					+ classTimeTableDataBean.getClasstimeList().get(7).getSubject() + "</td>" + "</tr>" + "</table>"
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
				/* message.addRecipients(Message.RecipientType.CC, myCcList); */
				message.setSubject("Class Time Table");
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
	}

	private String generatePdf(ClassTimeTableDataBean classTimeTableDataBean) {
		String status = "fail";
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			if (personID != null) {

				File files = new File(paths.getString("sms.classtimetable.pdf") + ft.format(now));
				if (!files.exists()) {
					files.mkdirs();
				} else {
					logger.debug("Alreday Found");
				}
				// Write PDF File
				OutputStream file = new FileOutputStream(
						new File(files + paths.getString("path_context").toString() + classTimeTableDataBean.getClassSection() + "-"
								+ classTimeTableDataBean.getDay() + ".pdf"));
				classTimeTableDataBean.setFilepath(files +paths.getString("path_context").toString()+ classTimeTableDataBean.getClassSection() + "-"
								+ classTimeTableDataBean.getDay() + ".pdf");
				Document document = new Document(PageSize.A4, 50, 50, 10, 50);
				PdfWriter.getInstance(document, file);
				document.open();
				
				String logo ="";
				if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
					logo = paths.getString(schoolID+"_LOGO");
					document.add(Image.getInstance(getClass().getClassLoader().getResource(logo)));
				}else{
					logo = classTimeTableDataBean.getSchoolLogo();
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

				PdfPTable headTable = new PdfPTable(2);
				headTable.setWidthPercentage(100f);
				PdfPCell headCell = new PdfPCell(new Paragraph("Period ", font3));
				PdfPCell headCell1 = new PdfPCell(new Paragraph("Subject ", font3));
				headCell.setBorder(PdfPCell.NO_BORDER);
				headCell1.setBorder(PdfPCell.NO_BORDER);
				headTable.addCell(headCell);
				headTable.addCell(headCell1);
				headTable.setSpacingBefore(3f);
				headTable.setSpacingAfter(3f);
				cell1.addElement(headTable);

				try{
					PdfPTable nestedTable = new PdfPTable(2);
					nestedTable.setWidthPercentage(100f);
					PdfPCell nesCell1 = new PdfPCell(new Paragraph("1", font3));
					PdfPCell nesCell2 = new PdfPCell(
							new Paragraph("" + classTimeTableDataBean.getClasstimeList().get(0).getSubject(), font3));
					nesCell1.setBorder(PdfPCell.NO_BORDER);
					nesCell2.setBorder(PdfPCell.NO_BORDER);
					nestedTable.addCell(nesCell1);
					nestedTable.addCell(nesCell2);
					nestedTable.setSpacingBefore(3f);
					nestedTable.setSpacingAfter(3f);
					cell1.addElement(nestedTable);

					PdfPTable nestedTable1 = new PdfPTable(2);
					nestedTable1.setWidthPercentage(100f);
					PdfPCell nesCell11 = new PdfPCell(new Paragraph("2", font3));
					PdfPCell nesCell12 = new PdfPCell(
							new Paragraph("" + classTimeTableDataBean.getClasstimeList().get(1).getSubject(), font3));
					nesCell11.setBorder(PdfPCell.NO_BORDER);
					nesCell12.setBorder(PdfPCell.NO_BORDER);
					nestedTable1.addCell(nesCell11);
					nestedTable1.addCell(nesCell12);
					nestedTable1.setSpacingBefore(3f);
					nestedTable1.setSpacingAfter(3f);
					cell1.addElement(nestedTable1);

					PdfPTable nestedTable2 = new PdfPTable(2);
					nestedTable2.setWidthPercentage(100f);
					PdfPCell nesCell21 = new PdfPCell(new Paragraph("3", font3));
					PdfPCell nesCell22 = new PdfPCell(
							new Paragraph("" + classTimeTableDataBean.getClasstimeList().get(2).getSubject(), font3));
					nesCell21.setBorder(PdfPCell.NO_BORDER);
					nesCell22.setBorder(PdfPCell.NO_BORDER);
					nestedTable2.addCell(nesCell21);
					nestedTable2.addCell(nesCell22);
					nestedTable2.setSpacingBefore(3f);
					nestedTable2.setSpacingAfter(3f);
					cell1.addElement(nestedTable2);

					PdfPTable nestedTable3 = new PdfPTable(2);
					nestedTable3.setWidthPercentage(100f);
					PdfPCell nesCell31 = new PdfPCell(new Paragraph("4", font3));
					PdfPCell nesCell32 = new PdfPCell(
							new Paragraph("" + classTimeTableDataBean.getClasstimeList().get(3).getSubject(), font3));
					nesCell31.setBorder(PdfPCell.NO_BORDER);
					nesCell32.setBorder(PdfPCell.NO_BORDER);
					nestedTable3.addCell(nesCell31);
					nestedTable3.addCell(nesCell32);
					nestedTable3.setSpacingBefore(3f);
					nestedTable3.setSpacingAfter(3f);
					cell1.addElement(nestedTable3);

					PdfPTable nestedTable4 = new PdfPTable(2);
					nestedTable4.setWidthPercentage(100f);
					PdfPCell nesCell41 = new PdfPCell(new Paragraph("5", font3));
					PdfPCell nesCell42 = new PdfPCell(
							new Paragraph("" + classTimeTableDataBean.getClasstimeList().get(4).getSubject(), font3));
					nesCell41.setBorder(PdfPCell.NO_BORDER);
					nesCell42.setBorder(PdfPCell.NO_BORDER);
					nestedTable4.addCell(nesCell41);
					nestedTable4.addCell(nesCell42);
					nestedTable4.setSpacingBefore(3f);
					nestedTable4.setSpacingAfter(3f);
					cell1.addElement(nestedTable4);

					PdfPTable nestedTable5 = new PdfPTable(2);
					nestedTable5.setWidthPercentage(100f);
					PdfPCell nesCell51 = new PdfPCell(new Paragraph("6", font3));
					PdfPCell nesCell52 = new PdfPCell(
							new Paragraph("" + classTimeTableDataBean.getClasstimeList().get(5).getSubject(), font3));
					nesCell51.setBorder(PdfPCell.NO_BORDER);
					nesCell52.setBorder(PdfPCell.NO_BORDER);
					nestedTable5.addCell(nesCell51);
					nestedTable5.addCell(nesCell52);
					nestedTable5.setSpacingBefore(3f);
					nestedTable5.setSpacingAfter(3f);
					cell1.addElement(nestedTable5);

					PdfPTable nestedTable6 = new PdfPTable(2);
					nestedTable6.setWidthPercentage(100f);
					PdfPCell nesCell61 = new PdfPCell(new Paragraph("7", font3));
					PdfPCell nesCell62 = new PdfPCell(
							new Paragraph("" + classTimeTableDataBean.getClasstimeList().get(6).getSubject(), font3));
					nesCell61.setBorder(PdfPCell.NO_BORDER);
					nesCell62.setBorder(PdfPCell.NO_BORDER);
					nestedTable6.addCell(nesCell61);
					nestedTable6.addCell(nesCell62);
					nestedTable6.setSpacingBefore(3f);
					nestedTable6.setSpacingAfter(3f);
					cell1.addElement(nestedTable6);

					PdfPTable nestedTable7 = new PdfPTable(2);
					nestedTable7.setWidthPercentage(100f);
					PdfPCell nesCell71 = new PdfPCell(new Paragraph("8", font3));
					PdfPCell nesCell72 = new PdfPCell(
							new Paragraph("" + classTimeTableDataBean.getClasstimeList().get(7).getSubject(), font3));
					nesCell71.setBorder(PdfPCell.NO_BORDER);
					nesCell72.setBorder(PdfPCell.NO_BORDER);
					nestedTable7.addCell(nesCell71);
					nestedTable7.addCell(nesCell72);
					nestedTable1.setSpacingBefore(3f);
					nestedTable7.setSpacingAfter(3f);
					cell1.addElement(nestedTable7);
				}catch(Exception e){
					
				}

				table.addCell(cell);
				table.addCell(cell1);
				table.addCell(cell2);

				document.add(table);
				document.close();
				file.close();
				logger.debug("Done");
				status = "Success";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
			e.printStackTrace();
		} finally {

		}
		return status;
	}

	public List<String> getTeacherIDList() {
		return teacherIDList;
	}

	public void setTeacherIDList(List<String> teacherIDList) {
		this.teacherIDList = teacherIDList;
	}
	
	/* prema 08/11/2016 */
	
	@PostConstruct
    public void init() {
        classTimeTableDataBean = new ClassTimeTableDataBean();
        classesBean = new ArrayList<ClassTimeTableDataBean>();         
    }
	
	public String addclasstimetable(){
		try{
			logger.debug("inside add classtimetable");	
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			String personID = "";
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");			
			if(classcheck(true)){
				logger.debug("inside");				
				classTimeTableDataBean=new ClassTimeTableDataBean();
				classTimeTableDataBean.setClassname(classname);
				classTimeTableDataBean.setDay(day);
				classTimeTableDataBean.setStartTime(startTime);
				classTimeTableDataBean.setEndTime(endTime);
				controller.classTimeTable(personID, classTimeTableDataBean);				
			}else {
				logger.debug("else");	
				classesBean.remove(classesBean.size()-1);
			}
			classTimeTableDataBean.setStartTime(null);
			classTimeTableDataBean.setEndTime(null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	public boolean classcheck(boolean flag){
		String fieldName;
		boolean valid=true;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (classTimeTableDataBean.getSubject().equalsIgnoreCase(null) ||
				classTimeTableDataBean.getSubject().equalsIgnoreCase("")) {
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("subjectname").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.subject")));
			}
			valid=false;
		}
		if (classTimeTableDataBean.getSubjectCode().equalsIgnoreCase(null) ||
				classTimeTableDataBean.getSubjectCode().equalsIgnoreCase("")) {
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("subjectcode").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Subject Code."));
			}
			valid=false;
		}
		if (classTimeTableDataBean.getTeaID().equalsIgnoreCase(null) || 
				classTimeTableDataBean.getTeaID().equalsIgnoreCase("")) {
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("teacherid").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Teacher ID."));
			}
			valid=false;
		}		
		if (classTimeTableDataBean.getStartTime() == null) {
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("starttime").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Start Time."));
			}
			valid=false;
		}
		if (classTimeTableDataBean.getEndTime() == null) {
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("endtime").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select End Time."));
			}
			valid=false;
		}	
		logger.debug("validaTION "+valid);
		setStartTime(classTimeTableDataBean.getStartTime());
		setEndTime(classTimeTableDataBean.getEndTime());
		return valid;
	}

}