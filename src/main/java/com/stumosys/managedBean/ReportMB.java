package com.stumosys.managedBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import scala.concurrent.DelayedLazyVal;

import com.stumosys.controler.SmsController;
import com.stumosys.domain.AttendanceDataBean;
import com.stumosys.domain.ReportCardDataBean;
import com.stumosys.domain.ReportDataBean;
import com.stumosys.domain.StudentPerformanceDataBean;
import com.stumosys.managedBean.AttendancePreviewMB;
import com.stumosys.util.CommonValidate;

@ManagedBean(name = "reportMB")
@RequestScoped
public class ReportMB {
	
	ReportDataBean reportDataBean=new ReportDataBean();
	@ManagedProperty(value ="#{attendancePreviewMB}")
	AttendancePreviewMB attendancePreviewMB;
	AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle report = context.getApplication().evaluateExpressionGet(context, "#{report}", ResourceBundle.class);
	private static Logger logger = Logger.getLogger(ReportMB.class);
	SmsController controller=null;
	Calendar now = Calendar.getInstance();
	
	public AttendanceDataBean getAttendanceDataBean() {
		return attendanceDataBean;
	}
	public void setAttendanceDataBean(AttendanceDataBean attendanceDataBean) {
		this.attendanceDataBean = attendanceDataBean;
	}
	public AttendancePreviewMB getAttendancePreviewMB() {
		return attendancePreviewMB;
	}
	public void setAttendancePreviewMB(AttendancePreviewMB attendancePreviewMB) {
		this.attendancePreviewMB = attendancePreviewMB;
	}
	public ReportDataBean getReportDataBean() {
		return reportDataBean;
	}
	public void setReportDataBean(ReportDataBean reportDataBean) {
		this.reportDataBean = reportDataBean;
	}
	public String reportpage(){
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		SmsController controller = (SmsController) ctx.getBean("controller");
		/*String personID="";*/
		try{
			/*personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");*/
			reportDataBean.setSchoolID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID"));
			reportDataBean.setReportList(new ArrayList<ReportDataBean>());
			reportDataBean.setReportType("");
			reportDataBean.setReportsubType("");
			reportDataBean.setReportName("");
			reportDataBean.setReportdescription("");
			
			setFilterTableflag(false);
			setReportmenuTableflag(true);
			setUpdateButtonflag(false);
			setSubmitButtonflag(false);
			
			reportDataBean.setReportList(controller.getReportmenulist(reportDataBean));
		}catch(Exception e){
			e.printStackTrace();
		}
		return "addReport";
	}
	
	public String reportmenuPage(){
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		SmsController controller = (SmsController) ctx.getBean("controller");
		try{
			reportDataBean.setSchoolID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID"));
			reportDataBean.setReportList(new ArrayList<ReportDataBean>());
			reportDataBean.setReportList(controller.getReportmenulist(reportDataBean));
		}catch(Exception e){
			e.printStackTrace();
		}
		return "reportMenuPage";
	}
	List<ReportDataBean> filterData=null;
	
	public List<ReportDataBean> getFilterData() {
		return filterData;
	}
	public void setFilterData(List<ReportDataBean> filterData) {
		this.filterData = filterData;
	}
	public String reportBasedPage(ReportDataBean reportData){
		filterData=new ArrayList<ReportDataBean>();
		reportDataBean.setStudentList(new ArrayList<String>());
		try{
			reset();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			setYearList(Arrays.asList(String.valueOf(now.get(Calendar.YEAR)-1),String.valueOf(now.get(Calendar.YEAR))));
			reportDataBean.setReportsubType(reportData.getReportsubType());
			reportDataBean.setReportType(reportData.getReportType());
			reportDataBean.setReportID(reportData.getReportID());
			filterData=controller.getFilterlist(reportData);
			for (int i = 0; i < filterData.size(); i++) {
				if (filterData.get(i).getFilterName().equalsIgnoreCase("Class")) {
					reportDataBean.setClassFlag(filterData.get(i).isActiveStatus());
				} 
				else if (filterData.get(i).getFilterName().equalsIgnoreCase("Section")) {
					reportDataBean.setSectionFlag(filterData.get(i).isActiveStatus());
				} 
				else if (filterData.get(i).getFilterName().equalsIgnoreCase("Student Name")) {
					reportDataBean.setStudentNameFlag(filterData.get(i).isActiveStatus());
				} 
				else if (filterData.get(i).getFilterName().equalsIgnoreCase("Month")) {
					reportDataBean.setMonthFlag(filterData.get(i).isActiveStatus());
				} 
				else if (filterData.get(i).getFilterName().equalsIgnoreCase("Year")) {
					reportDataBean.setYearFlag(filterData.get(i).isActiveStatus());
				} 
				else if (filterData.get(i).getFilterName().equalsIgnoreCase("Date")) {
					reportDataBean.setDateFlag(filterData.get(i).isActiveStatus());
				} 
				else if (filterData.get(i).getFilterName().equalsIgnoreCase("Exam Title")) {
					reportDataBean.setExamTitleFlag(filterData.get(i).isActiveStatus());
				}
				else if (filterData.get(i).getFilterName().equalsIgnoreCase("Teacher Name")) {
					reportDataBean.setTeacherNameFlag(filterData.get(i).isActiveStatus());
				}
				else if (filterData.get(i).getFilterName().equalsIgnoreCase("Status")) {
					reportDataBean.setAttendanceStatusFlag(filterData.get(i).isActiveStatus());
				}
				else if (filterData.get(i).getFilterName().equalsIgnoreCase("Leave Type")) {
					reportDataBean.setLeaveTypeFlag(filterData.get(i).isActiveStatus());
				}
				
				
				else if (filterData.get(i).getFilterName().equalsIgnoreCase("Day")) {
					reportDataBean.setDayFlag(filterData.get(i).isActiveStatus());
				}
				else if (filterData.get(i).getFilterName().equalsIgnoreCase("Subject Name")) {
					reportDataBean.setSubjectNameFlag(filterData.get(i).isActiveStatus());
				}
				else if (filterData.get(i).getFilterName().equalsIgnoreCase("Period")) {
					reportDataBean.setPeriodFlag(filterData.get(i).isActiveStatus());
				}
				
			}
			
			
			if (reportDataBean.isClassFlag()==false && reportDataBean.isSectionFlag()==false && reportDataBean.isStudentNameFlag()==true) {
				reportDataBean.setSecName("All");
				reportDataBean.setClassName("All");
				controller.secNamebasedData(reportDataBean);
			}
			
			
					if (reportData.getReportType().equalsIgnoreCase("Teacher")) {
						if (reportData.getReportsubType().equalsIgnoreCase("Attendance")) {
							FacesContext.getCurrentInstance().getExternalContext().redirect("/stumosys/pages/xhtml/reportTeacher.xhtml");
							teacherReport("Attendance");
						}
						else if (reportData.getReportsubType().equalsIgnoreCase("TimeTable")) {
							FacesContext.getCurrentInstance().getExternalContext().redirect("/stumosys/pages/xhtml/reportTeacher.xhtml");
							teacherReport("TimeTable");
						}
					}
					else if (reportData.getReportType().equalsIgnoreCase("Student")) {
							if (reportData.getReportsubType().equalsIgnoreCase("Mark")) {
								FacesContext.getCurrentInstance().getExternalContext().redirect("/stumosys/pages/xhtml/reportStudent.xhtml");
								studentReport("Mark");
							}
							else if (reportData.getReportsubType().equalsIgnoreCase("Behaviour")) {
								FacesContext.getCurrentInstance().getExternalContext().redirect("/stumosys/pages/xhtml/reportStudent.xhtml");
								studentReport("Behaviour");
							}
							else if (reportData.getReportsubType().equalsIgnoreCase("Attendance")) {
								FacesContext.getCurrentInstance().getExternalContext().redirect("/stumosys/pages/xhtml/reportStudent.xhtml");
								studentReport("Attendance");
							}
					}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	public List<String> reportFor=new ArrayList<String>();
	public boolean filterTableflag;
	public boolean reportmenuTableflag;
	public List<ReportDataBean> selectedReportMenu=new ArrayList<ReportDataBean>();
	private List<String> classList;
	
	private List<String> monthList;
	private List<String> yearList;
	private List<String> examList;
	private List<String> subjectList;

	
	public List<String> getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(List<String> subjectList) {
		this.subjectList = subjectList;
	}
	public List<String> getExamList() {
		return examList;
	}
	public void setExamList(List<String> examList) {
		this.examList = examList;
	}
	public List<String> getYearList() {
		return yearList;
	}
	public void setYearList(List<String> yearList) {
		this.yearList = yearList;
	}
	public List<String> getMonthList() {
		return monthList;
	}
	public void setMonthList(List<String> monthList) {
		this.monthList = monthList;
	}
	
	
	public List<String> getClassList() {
		return classList;
	}
	public void setClassList(List<String> classList) {
		this.classList = classList;
	}
	public List<ReportDataBean> getSelectedReportMenu() {
		return selectedReportMenu;
	}
	public void setSelectedReportMenu(List<ReportDataBean> selectedReportMenu) {
		this.selectedReportMenu = selectedReportMenu;
	}
	public boolean isFilterTableflag() {
		return filterTableflag;
	}
	public void setFilterTableflag(boolean filterTableflag) {
		this.filterTableflag = filterTableflag;
	}
	public boolean isReportmenuTableflag() {
		return reportmenuTableflag;
	}
	public void setReportmenuTableflag(boolean reportmenuTableflag) {
		this.reportmenuTableflag = reportmenuTableflag;
	}
	public List<String> getReportFor() {
		return reportFor;
	}
	public void setReportFor(List<String> reportFor) {
		this.reportFor = reportFor;
	}
	
	
	
	
/*report Menu insert / delete / filter add / menu validation*/
	public void reportTypeChange(ValueChangeEvent v){
		reportFor=new ArrayList<String>();
		try{
			String valuechange=v.getNewValue().toString();				
			reportDataBean.setFilterlist(new ArrayList<ReportDataBean>());
			reportDataBean.setReportsubType("");
			setFilterTableflag(false);
			setReportmenuTableflag(true);
			if(valuechange.equalsIgnoreCase("Teacher")){
				reportFor=Arrays.asList("Attendance","TimeTable");
				}
			else if (valuechange.equalsIgnoreCase("Student")) {
				reportFor=Arrays.asList("Mark","Behaviour","Attendance");
			}
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void addfilter(ValueChangeEvent v) {
		logger.info("inside addfilter method");
		List<String> filterStringList = null;
		try {
				filterStringList = new ArrayList<String>();
				reportDataBean.setFilterlist(new ArrayList<ReportDataBean>());
				reportDataBean.setReportsubType(v.getNewValue().toString());	
				logger.info("report sub type ---->"+reportDataBean.getReportsubType());
				String tempstring = report.getString("sms."+ reportDataBean.getReportType() + "."+ reportDataBean.getReportsubType());
				filterStringList = Arrays.asList(tempstring.split(","));
					for (int i = 0; i < filterStringList.size(); i++) {
						ReportDataBean temp = new ReportDataBean();
						temp.setFilterName(filterStringList.get(i));
						temp.setActiveStatus(false);
						reportDataBean.getFilterlist().add(temp);
					}
					setFilterTableflag(true);
					setReportmenuTableflag(false);
					setUpdateButtonflag(false);
					setSubmitButtonflag(true);
			} catch (Exception e) {
				logger.info("-----submit method exception calling-------");
				logger.warn("Exception -->" + e.getMessage());
				e.printStackTrace();
			} finally {

			}
		}
	
	public void saveReport() {
		logger.info("inside saveReport method");
		SmsController controller=null;
		String status="";
		String name="";
		FacesContext fc=FacesContext.getCurrentInstance();
		RequestContext reqcontext = RequestContext.getCurrentInstance();
				try {
					if(reportMenuValidation(true)){
						logger.info("ReportList--------->"+reportDataBean.getReportList().size());
						reportDataBean.setSchoolID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID"));
						ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
						controller = (SmsController) ctx.getBean("controller");	
						status=controller.saveReport(reportDataBean);
						logger.info("saveReport---status-->"+status);
						if (status.equalsIgnoreCase("Success")) {
							setFilterTableflag(false);
							reqcontext.execute("PF('menuRegDialog').show();");
						}
						else if (status.equalsIgnoreCase("Exist")) {
							
							name=CommonValidate.findComponentInRoot("reportname").getClientId(fc);
							fc.addMessage(name, new FacesMessage("Report Name Already Exist"));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
public void deleteMenu(){
		String status="";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			reportDataBean.setSchoolID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID"));
			logger.info("inside delete menu------>"+reportDataBean.getReportID());
			status=controller.deleteMenu(reportDataBean);
			if (status.equalsIgnoreCase("Success")) {
				reportDataBean.getReportList().remove(reportDataBean.getRowindex());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean updateButtonflag;
	public boolean submitButtonflag;
	
	
	public boolean isSubmitButtonflag() {
		return submitButtonflag;
	}
	public void setSubmitButtonflag(boolean submitButtonflag) {
		this.submitButtonflag = submitButtonflag;
	}
	public boolean isUpdateButtonflag() {
			return updateButtonflag;
		}
		public void setUpdateButtonflag(boolean updateButtonflag) {
			this.updateButtonflag = updateButtonflag;
		}
public void editMenu(){
	String status="";
	logger.info("inside edit menu---->"+reportDataBean.getRowindex()+"----ID---->"+reportDataBean.getReportID());
	reportDataBean.setReportName("");
	reportDataBean.setReportType("");
	reportDataBean.setReportsubType("");
	reportDataBean.setReportdescription("");
	List<String> filterStringList = null;
	try {
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (SmsController) ctx.getBean("controller");
		reportDataBean.setSchoolID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID"));
		
		reportDataBean.setReportName(reportDataBean.getReportList().get(reportDataBean.getRowindex()).getReportName());
		reportDataBean.setReportType(reportDataBean.getReportList().get(reportDataBean.getRowindex()).getReportType());
		reportDataBean.setReportsubType(reportDataBean.getReportList().get(reportDataBean.getRowindex()).getReportsubType());
		reportDataBean.setReportdescription(reportDataBean.getReportList().get(reportDataBean.getRowindex()).getReportdescription());
		
		filterStringList = new ArrayList<String>();
		reportDataBean.setFilterlist(new ArrayList<ReportDataBean>());
		String tempstring = report.getString("sms."+ reportDataBean.getReportType() + "."+ reportDataBean.getReportsubType());
		filterStringList = Arrays.asList(tempstring.split(","));
			for (int i = 0; i < filterStringList.size(); i++) {
				ReportDataBean temp = new ReportDataBean();
				temp.setFilterName(filterStringList.get(i));
				temp.setActiveStatus(false);
				reportDataBean.getFilterlist().add(temp);
			}
			setFilterTableflag(true);
			setUpdateButtonflag(true);
			setSubmitButtonflag(false);
			setReportmenuTableflag(false);
		
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.warn("Exception -->" + e.getMessage());	
	}
}

public void updateEditMenu(){
	String status="Fail";
	String name="";
	FacesContext fc=FacesContext.getCurrentInstance();
	RequestContext reqcontext = RequestContext.getCurrentInstance();
	try {
		if(reportMenuValidation(true)){
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (SmsController) ctx.getBean("controller");
		reportDataBean.setSchoolID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID"));
		status=controller.updateMenuEdit(reportDataBean);
		logger.info("Status------>"+status);
		if (status=="Success") {
			reqcontext.execute("PF('menuUpdateDialog').show();");
			reportDataBean.setReportList(new ArrayList<ReportDataBean>());
			reportDataBean.setReportList(controller.getReportmenulist(reportDataBean));
		}
		else if (status=="Exist") {
			name=CommonValidate.findComponentInRoot("reportname").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Report Name Already Exist"));
		}
		}
	} catch (Exception e) {
		e.printStackTrace();
		logger.warn("Exception -->" + e.getMessage());	
	}
}
public void cancelEditMenu(){
	logger.info("-----------cancel Edit Menu--------------");
	try {
		setFilterTableflag(false);
		setUpdateButtonflag(false);
		setSubmitButtonflag(false);
		setReportmenuTableflag(true);
		
		reportDataBean.setReportName("");
		reportDataBean.setReportType("");
		reportDataBean.setReportsubType("");
		reportDataBean.setReportdescription("");
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.warn("Exception -->" + e.getMessage());
	}
}

	private boolean reportMenuValidation(boolean valid) {
			valid=true;
			String name;
			FacesContext fc=FacesContext.getCurrentInstance();
				if(StringUtils.isEmpty(reportDataBean.getReportsubType())){
					name=CommonValidate.findComponentInRoot("reportType").getClientId(fc);
					fc.addMessage(name, new FacesMessage("Please Select the Report For"));
					valid=false;
				}
				if(StringUtils.isEmpty(reportDataBean.getReportName())){
					name=CommonValidate.findComponentInRoot("reportname").getClientId(fc);
					fc.addMessage(name, new FacesMessage("Please Enter Report Name"));
					valid=false;
				}
				int count=0;
				for (int i = 0; i < reportDataBean.getFilterlist().size(); i++) {
					if (reportDataBean.getFilterlist().get(i).isActiveStatus()==true) {
						count++;
					}
				}
				if(count==0){
					name=CommonValidate.findComponentInRoot("addReportTable").getClientId(fc);
					fc.addMessage(name, new FacesMessage("Choose Atleast One Filter"));
					valid=false;
				}
			return valid;
		}
	
	public Boolean attendanceDataTable;
	public Boolean behaviourDataTable;
	public Boolean markDataTable;
	public Boolean teacherAttendanceDataTable;
	public Boolean teacherTimetableDataTable;

	
	public Boolean getTeacherTimetableDataTable() {
		return teacherTimetableDataTable;
	}
	public void setTeacherTimetableDataTable(Boolean teacherTimetableDataTable) {
		this.teacherTimetableDataTable = teacherTimetableDataTable;
	}
	public Boolean getTeacherAttendanceDataTable() {
		return teacherAttendanceDataTable;
	}
	public void setTeacherAttendanceDataTable(Boolean teacherAttendanceDataTable) {
		this.teacherAttendanceDataTable = teacherAttendanceDataTable;
	}
	public Boolean getAttendanceDataTable() {
		return attendanceDataTable;
	}
	public void setAttendanceDataTable(Boolean attendanceDataTable) {
		this.attendanceDataTable = attendanceDataTable;
	}
	public Boolean getBehaviourDataTable() {
		return behaviourDataTable;
	}
	public void setBehaviourDataTable(Boolean behaviourDataTable) {
		this.behaviourDataTable = behaviourDataTable;
	}
	
	public Boolean getMarkDataTable() {
		return markDataTable;
	}
	public void setMarkDataTable(Boolean markDataTable) {
		this.markDataTable = markDataTable;
	}
	
	/*Teacher report method and validation*/
	public String teacherReport(String type){
		logger.info("inside the method---studentReport---");
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (SmsController) ctx.getBean("controller");
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
		reportDataBean.setSchoolID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID"));
		
		if(personID!=null){
		try{
			
			
			if (type.equalsIgnoreCase("Attendance")) {
			
			setTeacherAttendanceDataTable(false);
			reportDataBean.setTeacherAttendanData(new ArrayList<ReportDataBean>());
				if (reportDataBean.isTeacherNameFlag()==true) {
					reportDataBean.setTeacherList(new ArrayList<String>());
					reportDataBean.setTeacherList(controller.teacherNameList(reportDataBean));
				}
				System.out.println("teacher list size----->"+reportDataBean.getTeacherList().size());
			}
			else if (type.equalsIgnoreCase("TimeTable")) {
				System.out.println("-------------inside TimeTable if condition-----------------");
				if (reportDataBean.isClassFlag()==true) {
					classList=new ArrayList<String>();
					classList=controller.ClassListAttView(personID, attendanceDataBean);
					System.out.println("class list for time table size--->"+classList.size());
				}
				if (reportDataBean.isTeacherNameFlag()==true && reportDataBean.isClassFlag()==false) {
					reportDataBean.setTeacherList(new ArrayList<String>());
					reportDataBean.setClassName("All");
					reportDataBean.setTeacherList(controller.teacherNameList(reportDataBean));
					System.out.println("teacher list size----->"+reportDataBean.getTeacherList().size());
				}
				if (reportDataBean.isSubjectNameFlag()==true && reportDataBean.isTeacherNameFlag()==false && reportDataBean.isClassFlag()==false) {
					reportDataBean.setSubjectList(new ArrayList<String>());
					reportDataBean.setClassName("All");
					reportDataBean.setTeacherName("All");
					reportDataBean.setSubjectList(controller.subjectNameList(reportDataBean));
					System.out.println("subject list size----->"+reportDataBean.getSubjectList().size());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		return"";
	}
	
	/*Student report methods and validation*/
	public String studentReport(String type){
		logger.info("inside the method---studentReport---");
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (SmsController) ctx.getBean("controller");
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
		List<String> templist=new ArrayList<String>();
		examList=new ArrayList<String>();
		if(personID!=null){
		try{
			attendanceDataBean.setSchoolName("");
			attendanceDataBean.setClassname("");
			attendanceDataBean.setStudentName("");
			attendanceDataBean.setCategory("");
			
			setBehaviourDataTable(false);
			setAttendanceDataTable(false);
			setMarkDataTable(false);
			reportDataBean.setAttendanData(new ArrayList<AttendanceDataBean>());
			reportDataBean.setPerformanceData(new ArrayList<StudentPerformanceDataBean>());
			reportDataBean.setReportCardData(new ArrayList<ReportCardDataBean>());
				if (reportDataBean.isClassFlag()==false && reportDataBean.isSectionFlag()==false && reportDataBean.isStudentNameFlag()==true) {
					reportDataBean.setSecName("All");
					reportDataBean.setClassName("All");
					controller.secNamebasedData(reportDataBean);
					reportDataBean.setSecName("");
					reportDataBean.setClassName("");
				}
				if (reportDataBean.isClassFlag()==true) {
					classList=new ArrayList<String>();
					classList=controller.ClassListAttView(personID, attendanceDataBean);
					for (int i = 0; i < classList.size(); i++) {
						templist.add(classList.get(i).split("/")[0]);
					}
					HashSet<String> hashclasslist = new HashSet<String>(templist);
					classList=new ArrayList<String>();
					classList.addAll(hashclasslist);
					Collections.sort(classList);
				}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		return"";
	}
	
	/*report validation methods*/
	
	private boolean attendanceValidation(boolean valid) {
		valid=true;
		String name;
		FacesContext fc=FacesContext.getCurrentInstance();
		if (reportDataBean.isClassFlag()==true) {
				if(StringUtils.isEmpty(reportDataBean.getClassName())){
					name=CommonValidate.findComponentInRoot("classname").getClientId(fc);
					fc.addMessage(name, new FacesMessage("Please Select the Class"));
					valid=false;
				}
		}
		if (reportDataBean.isSectionFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getSecName())){
				name=CommonValidate.findComponentInRoot("sec").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Section"));
				valid=false;
			}
	}
		if (reportDataBean.isStudentNameFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getStudentName())){
				name=CommonValidate.findComponentInRoot("studentname").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Student Name"));
				valid=false;
			}
	}
		if (reportDataBean.isMonthFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getMonth())){
				name=CommonValidate.findComponentInRoot("month").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Month"));
				valid=false;
			}
	}
		if (reportDataBean.isYearFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getYear())){
				name=CommonValidate.findComponentInRoot("year").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Year"));
				valid=false;
			}
	}
		if (reportDataBean.isDateFlag()==true) {
			if(reportDataBean.getDate1()==null){
				name=CommonValidate.findComponentInRoot("date21").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Date"));
				valid=false;
			}
	}
		return valid;
	}
	private boolean behaviourValidation(boolean valid) {
		valid=true;
		String name;
		FacesContext fc=FacesContext.getCurrentInstance();
		if (reportDataBean.isClassFlag()==true) {
				if(StringUtils.isEmpty(reportDataBean.getClassName())){
					name=CommonValidate.findComponentInRoot("behavclassname").getClientId(fc);
					fc.addMessage(name, new FacesMessage("Please Select the Class"));
					valid=false;
				}
		}
		if (reportDataBean.isSectionFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getSecName())){
				name=CommonValidate.findComponentInRoot("behavsec").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Section"));
				valid=false;
			}
	}
		if (reportDataBean.isStudentNameFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getStudentName())){
				name=CommonValidate.findComponentInRoot("behavstudentname").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Student Name"));
				valid=false;
			}
	}
		if (reportDataBean.isMonthFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getMonth())){
				name=CommonValidate.findComponentInRoot("behavmonth").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Month"));
				valid=false;
			}
	}
		if (reportDataBean.isYearFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getYear())){
				name=CommonValidate.findComponentInRoot("behavyear").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Year"));
				valid=false;
			}
	}
		if (reportDataBean.isDateFlag()==true) {
			if(reportDataBean.getDate1()==null){
				name=CommonValidate.findComponentInRoot("behavdate21").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Date"));
				valid=false;
			}
	}
	
		return valid;
	}
	
	private boolean markValidation(boolean valid) {
		valid=true;
		String name;
		FacesContext fc=FacesContext.getCurrentInstance();
		if (reportDataBean.isClassFlag()==true) {
				if(StringUtils.isEmpty(reportDataBean.getClassName())){
					name=CommonValidate.findComponentInRoot("markclassname").getClientId(fc);
					fc.addMessage(name, new FacesMessage("Please Select the Class"));
					valid=false;
				}
		}
		if (reportDataBean.isSectionFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getSecName())){
				name=CommonValidate.findComponentInRoot("marksec").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Section"));
				valid=false;
			}
	}
		if (reportDataBean.isStudentNameFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getStudentName())){
				name=CommonValidate.findComponentInRoot("markstudentname").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Student Name"));
				valid=false;
			}
	}
		if (reportDataBean.isMonthFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getMonth())){
				name=CommonValidate.findComponentInRoot("markmonth").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Month"));
				valid=false;
			}
	}
		if (reportDataBean.isYearFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getYear())){
				name=CommonValidate.findComponentInRoot("markyear").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Year"));
				valid=false;
			}
	}
		if (reportDataBean.isDateFlag()==true) {
			if(reportDataBean.getDate1()==null){
				name=CommonValidate.findComponentInRoot("markdate21").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Date"));
				valid=false;
			}
	}
		
	
		return valid;
	}
	/*Students Report search methods */
	public void getExamTitle() {
		logger.info("inside exam title");
		try {
			 if(markValidation(true)) {
				 	ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
					controller = (SmsController) ctx.getBean("controller");
					reportDataBean.setSchoolID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID"));

				
				setExamList(controller.getExamListforMark(reportDataBean));
					if (examList.size()==0) {
						String name;
						FacesContext fc=FacesContext.getCurrentInstance();
						name=CommonValidate.findComponentInRoot("markExamTitle").getClientId(fc);
						fc.addMessage(name, new FacesMessage("No Exam Title found for given criteria"));
					}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void attendanceReportSearch() {
		logger.info("------Inside--------attendanceReportSearch--------Method----------");
		setAttendanceDataTable(false);
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			reportDataBean.setSchoolID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID"));
			if(attendanceValidation(true)){
				logger.info("------------inside attendance report aftr validation------------>");
				reportDataBean.setAttendanData(controller.getAttendanceReport(reportDataBean));
				logger.info("size of attendance data report----->"+reportDataBean.getAttendanData().size());
				if (reportDataBean.getAttendanData().size()==0) {
					setAttendanceDataTable(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void behaviourReportSearch() {
		logger.info("------Inside--------behaviourReportSearch--------Method----------");
		setBehaviourDataTable(false);
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			reportDataBean.setSchoolID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID"));
			
			if(behaviourValidation(true)){
				logger.info("------------inside behaviour report aftr validation------------>");
				reportDataBean.setPerformanceData(controller.getBehaviourReport(reportDataBean));
				if (reportDataBean.getPerformanceData().size()==0) {
					setBehaviourDataTable(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void markReportSearch() {
		logger.info("------Inside--------markReportSearch--------Method----------");
		setMarkDataTable(false);
		String name;
		int valid=0;
		FacesContext fc=FacesContext.getCurrentInstance();
		try { if(markValidation(true)) {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			reportDataBean.setSchoolID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID"));
		
				if(StringUtils.isEmpty(reportDataBean.getExamTitle()) && reportDataBean.isExamTitleFlag()==true){
					name=CommonValidate.findComponentInRoot("markExamTitle").getClientId(fc);
					fc.addMessage(name, new FacesMessage("Choose ExamTitle/Get Exam list "));
				}
				else valid=1;
				
				logger.info("---before if condition-----"+valid);
			if(valid==1){
				logger.info("------------inside mark report aftr validation------------>");
				reportDataBean.setReportCardData(controller.getReportCardReport(reportDataBean));
				if (reportDataBean.getReportCardData().size()==0) {
					setMarkDataTable(true);
				}
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*Teacher Validation Methods*/
	private boolean teacherAttendanceValidation(boolean valid) {
		valid=true;
		String name;
		FacesContext fc=FacesContext.getCurrentInstance();
		if (reportDataBean.isTeacherNameFlag()==true) {
				if(StringUtils.isEmpty(reportDataBean.getTeacherName())){
					name=CommonValidate.findComponentInRoot("teachername").getClientId(fc);
					fc.addMessage(name, new FacesMessage("Please Select the Teacher Name"));
					valid=false;
				}
		}
		if (reportDataBean.isAttendanceStatusFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getAttendanceStatus())){
				name=CommonValidate.findComponentInRoot("attendanceStatus").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Attendance Status"));
				valid=false;
			}
	}
		if (reportDataBean.isLeaveTypeFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getLeaveType())){
				name=CommonValidate.findComponentInRoot("leavetype").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Leave Type"));
				valid=false;
			}
	}
		if (reportDataBean.isMonthFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getMonth())){
				name=CommonValidate.findComponentInRoot("month").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Month"));
				valid=false;
			}
	}
		if (reportDataBean.isYearFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getYear())){
				name=CommonValidate.findComponentInRoot("year").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Year"));
				valid=false;
			}
	}
		if (reportDataBean.isDateFlag()==true) {
			if(reportDataBean.getDate1()==null){
				name=CommonValidate.findComponentInRoot("date21").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Date"));
				valid=false;
			}
	}
		return valid;
	}
	private boolean teacherTimetableValidation(boolean valid) {
		valid=true;
		String name;
		FacesContext fc=FacesContext.getCurrentInstance();
		if (reportDataBean.isClassFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getClassName())){
				name=CommonValidate.findComponentInRoot("TTclassname").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Class Name"));
				valid=false;
			}
	}
		if (reportDataBean.isTeacherNameFlag()==true) {
				if(StringUtils.isEmpty(reportDataBean.getTeacherName())){
					name=CommonValidate.findComponentInRoot("TTteachername").getClientId(fc);
					fc.addMessage(name, new FacesMessage("Please Select the Teacher Name"));
					valid=false;
				}
		}
		if (reportDataBean.isSubjectNameFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getSubjectName())){
				name=CommonValidate.findComponentInRoot("TTsubjectName").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Subject Name"));
				valid=false;
			}
	}
		if (reportDataBean.isPeriodFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getPeriod())){
				name=CommonValidate.findComponentInRoot("TTperiod").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Period"));
				valid=false;
			}
	}
		if (reportDataBean.isDayFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getDay())){
				name=CommonValidate.findComponentInRoot("TTDay").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Day"));
				valid=false;
			}
	}
		if (reportDataBean.isMonthFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getMonth())){
				name=CommonValidate.findComponentInRoot("TTmonth").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Month"));
				valid=false;
			}
	}
		if (reportDataBean.isYearFlag()==true) {
			if(StringUtils.isEmpty(reportDataBean.getYear())){
				name=CommonValidate.findComponentInRoot("TTyear").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Select the Year"));
				valid=false;
			}
	}
		return valid;
	}
	/*Teacher Report Search Methods*/
	public void teacherAttendanceSearch() {
		logger.info("------Inside--------teacher Attendance Search--------Method----------");
		setTeacherAttendanceDataTable(false);
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			reportDataBean.setSchoolID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID"));
			if(teacherAttendanceValidation(true)){
				logger.info("------------inside attendance report aftr validation------------>");
				reportDataBean.setTeacherAttendanData(controller.getTeacherAttendanceReport(reportDataBean));
				logger.info("size of attendance data report----->"+reportDataBean.getTeacherAttendanData().size());
				if (reportDataBean.getTeacherAttendanData().size()==0) {
					setTeacherAttendanceDataTable(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void teacherTimeTableSearch() {
	logger.info("------Inside--------teacher timetable Search--------Method----------");
		setTeacherTimetableDataTable(false);
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			reportDataBean.setSchoolID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID"));
			if(teacherTimetableValidation(true)){
				logger.info("------------inside timetable report aftr validation------------>");
				reportDataBean.setTeacherTimeTableData(new ArrayList<ReportDataBean>());
				reportDataBean.setTeacherTimeTableData(controller.getTeacherTimetableReport(reportDataBean));
			
				if (reportDataBean.getTeacherTimeTableData().size()==0) {
					setTeacherTimetableDataTable(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*common methods for all reports*/
	public void teacherChange(ValueChangeEvent v) {
		logger.info("inside classChangeTeacher method");
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			if (!v.getNewValue().toString().equalsIgnoreCase("") && v.getNewValue().toString()!=null) {
						reportDataBean.setTeacherName(v.getNewValue().toString());	
						reportDataBean.setSchoolID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID"));
						
						
						if (reportDataBean.isSubjectNameFlag()==true && !reportDataBean.getTeacherName().equalsIgnoreCase("All")) {
							reportDataBean.setReference("Teacher");
							reportDataBean.setSubjectList(new ArrayList<String>());
							reportDataBean.setSubjectList(controller.subjectNameList(reportDataBean));
							System.out.println("subject list size----->"+reportDataBean.getSubjectList().size());
						}
						else{
							reportDataBean.setSubjectName("All");
						}
			}
				logger.info("-----Size class sec list------------>"+reportDataBean.getSectionlist().size());
			} catch (Exception e) {
				logger.info("-----submit method exception calling-------");
				logger.warn("Exception -->" + e.getMessage());
				e.printStackTrace();
			} finally {

			}
		}
	
	public void classChangeTeacher(ValueChangeEvent v) {
		logger.info("inside classChangeTeacher method");
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			if (!v.getNewValue().toString().equalsIgnoreCase("") && v.getNewValue().toString()!=null) {
						reportDataBean.setClassName(v.getNewValue().toString());	
						reportDataBean.setSchoolID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID"));
						
						if (reportDataBean.isTeacherNameFlag()==true) {
							reportDataBean.setTeacherList(new ArrayList<String>());
							reportDataBean.setTeacherList(controller.teacherNameList(reportDataBean));
							System.out.println("teacher list size----->"+reportDataBean.getTeacherList().size());
						}
						if (reportDataBean.isSubjectNameFlag()==true) {
							reportDataBean.setReference("Class");
							reportDataBean.setSubjectList(new ArrayList<String>());
							reportDataBean.setSubjectList(controller.subjectNameList(reportDataBean));
							System.out.println("subject list size----->"+reportDataBean.getSubjectList().size());
						}
			}
				logger.info("-----Size class sec list------------>"+reportDataBean.getSectionlist().size());
			} catch (Exception e) {
				logger.info("-----submit method exception calling-------");
				logger.warn("Exception -->" + e.getMessage());
				e.printStackTrace();
			} finally {

			}
		}
	public void classChange(ValueChangeEvent v) {
		logger.info("inside classChange method");
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			if (!v.getNewValue().toString().equalsIgnoreCase("") && v.getNewValue().toString()!=null) {
						reportDataBean.setClassName(v.getNewValue().toString());	
						reportDataBean.setSchoolID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID"));
						if (reportDataBean.getClassName().equalsIgnoreCase("All")) {
							reportDataBean.setSecName("All");
							reportDataBean.setStudentName("All");
							/*reportDataBean.setSectionFlag(false);
							reportDataBean.setStudentNameFlag(false);*/
						}
						else{
							/*reportDataBean.setSectionFlag(true);
							reportDataBean.setStudentNameFlag(true);*/
							reportDataBean.setStudentName("");
							reportDataBean.setSecName("");
							controller.classNamebasedData(reportDataBean);
						}
			}
				logger.info("-----Size class sec list------------>"+reportDataBean.getSectionlist().size());
			} catch (Exception e) {
				logger.info("-----submit method exception calling-------");
				logger.warn("Exception -->" + e.getMessage());
				e.printStackTrace();
			} finally {

			}
		}
	public void sectionChange(ValueChangeEvent v) {
		logger.info("inside classChange method");
		try {
			if (!v.getNewValue().toString().equalsIgnoreCase("")) {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			
			reportDataBean.setSecName(v.getNewValue().toString());	
			reportDataBean.setSchoolID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID"));
			if (reportDataBean.getSecName().equalsIgnoreCase("All")) {
				logger.info("----inside if condition-------");
				reportDataBean.setStudentName("All");
			}
			else{
				logger.info("----inside else condition-------");
				reportDataBean.setStudentName("");
				controller.secNamebasedData(reportDataBean);
				}
			}
				logger.info("-----Size class sec list------------>"+reportDataBean.getSectionlist().size());
			} catch (Exception e) {
				logger.info("-----submit method exception calling-------");
				logger.warn("Exception -->" + e.getMessage());
				e.printStackTrace();
			} finally {

			}
		}
	public void attendanceStatusChange(ValueChangeEvent v) {
		logger.info("inside Attendance Status Change method");
		try {
			if (!v.getNewValue().toString().equalsIgnoreCase("")) {
					if (v.getNewValue().toString().equalsIgnoreCase("Present")) {
						reportDataBean.setLeaveType("None");
					}
					else if (v.getNewValue().toString().equalsIgnoreCase("Absent")) {
						reportDataBean.setLeaveType("");
					}
					else if (v.getNewValue().toString().equalsIgnoreCase("Both")) {
						reportDataBean.setLeaveType("None");
					}
			}
			} catch (Exception e) {
				logger.info("-----submit method exception calling-------");
				logger.warn("Exception -->" + e.getMessage());
				e.printStackTrace();
			} finally {

			}
		}
/*	public void dateRestrict(ValueChangeEvent v) {
		reportDataBean.setDate1(null);
	}
	public void yearmonthRestrict(ValueChangeEvent v) {
			reportDataBean.setMonth("");
			reportDataBean.setYear("");
	}*/
	
	public void statusValidation(int a) throws InterruptedException {
		logger.info("value------>"+a);
		logger.info("inside status validation filter name   ----->"+reportDataBean.getFilterlist().get(a).getFilterName());
		logger.info("inside status validation filter status ----->"+reportDataBean.getFilterlist().get(a).isActiveStatus());
		
		if (reportDataBean.getReportsubType().equalsIgnoreCase("Mark")) {
			if (reportDataBean.getFilterlist().get(a).getFilterName().equalsIgnoreCase("Class") && 
					reportDataBean.getFilterlist().get(a).isActiveStatus()==true) {
				for (int i = 0; i < reportDataBean.getFilterlist().size(); i++) {
					if (reportDataBean.getFilterlist().get(i).getFilterName().equalsIgnoreCase("Section")){
						reportDataBean.getFilterlist().get(i).setActiveStatus(true);
						}
					}
				}
			else if (reportDataBean.getFilterlist().get(a).getFilterName().equalsIgnoreCase("Section") && 
					reportDataBean.getFilterlist().get(a).isActiveStatus()==false) {
				for (int i = 0; i < reportDataBean.getFilterlist().size(); i++) {
					if (reportDataBean.getFilterlist().get(i).getFilterName().equalsIgnoreCase("Class")){
						reportDataBean.getFilterlist().get(i).setActiveStatus(false);
						}
					}
				}
			else if (reportDataBean.getFilterlist().get(a).getFilterName().equalsIgnoreCase("Exam Title") && 
					reportDataBean.getFilterlist().get(a).isActiveStatus()==true) {
				System.out.println("before for condition filter list count---->"+reportDataBean.getFilterlist().size());
				for (int i = 0; i < 3;i++) {
					ReportDataBean temp=new ReportDataBean();
					temp.setFilterName("Month");
					temp.setActiveStatus(false);
					reportDataBean.getFilterlist().add(temp);
				}
				System.out.println("filter list count---->"+reportDataBean.getFilterlist().size());
						
				}
			
		}
			if (reportDataBean.getFilterlist().get(a).getFilterName().equalsIgnoreCase("Date") && 
					reportDataBean.getFilterlist().get(a).isActiveStatus()==true) {
				for (int i = 0; i < reportDataBean.getFilterlist().size(); i++) {
						if (reportDataBean.getFilterlist().get(i).getFilterName().equalsIgnoreCase("Month")){
							reportDataBean.getFilterlist().get(i).setActiveStatus(false);
						}
						else if (reportDataBean.getFilterlist().get(i).getFilterName().equalsIgnoreCase("Year")){
							reportDataBean.getFilterlist().get(i).setActiveStatus(false);
						}
						else if (reportDataBean.getFilterlist().get(i).getFilterName().equalsIgnoreCase("From Date/To Date")){
							reportDataBean.getFilterlist().get(i).setActiveStatus(false);
						}
					}
				}
			else if (reportDataBean.getFilterlist().get(a).getFilterName().equalsIgnoreCase("Year") ||
					reportDataBean.getFilterlist().get(a).getFilterName().equalsIgnoreCase("Month") && 
					reportDataBean.getFilterlist().get(a).isActiveStatus()==true) {
				for (int i = 0; i < reportDataBean.getFilterlist().size(); i++) {
						if (reportDataBean.getFilterlist().get(i).getFilterName().equalsIgnoreCase("Date")){
							reportDataBean.getFilterlist().get(i).setActiveStatus(false);
						}
						else if (reportDataBean.getFilterlist().get(i).getFilterName().equalsIgnoreCase("From Date/To Date")){
							reportDataBean.getFilterlist().get(i).setActiveStatus(false);
						}
					}
				}
			else if (reportDataBean.getFilterlist().get(a).getFilterName().equalsIgnoreCase("From Date/To Date") && 
					reportDataBean.getFilterlist().get(a).isActiveStatus()==true) {
				for (int i = 0; i < reportDataBean.getFilterlist().size(); i++) {
					if (reportDataBean.getFilterlist().get(i).getFilterName().equalsIgnoreCase("Month")){
						reportDataBean.getFilterlist().get(i).setActiveStatus(false);
						}
					else if (reportDataBean.getFilterlist().get(i).getFilterName().equalsIgnoreCase("Year")){
						reportDataBean.getFilterlist().get(i).setActiveStatus(false);
						}
					else if (reportDataBean.getFilterlist().get(i).getFilterName().equalsIgnoreCase("Date")){
						reportDataBean.getFilterlist().get(i).setActiveStatus(false);
						}
					}
				}
			else if (reportDataBean.getFilterlist().get(a).getFilterName().equalsIgnoreCase("Section") && 
					reportDataBean.getFilterlist().get(a).isActiveStatus()==true) {
				for (int i = 0; i < reportDataBean.getFilterlist().size(); i++) {
					if (reportDataBean.getFilterlist().get(i).getFilterName().equalsIgnoreCase("Class")){
						reportDataBean.getFilterlist().get(i).setActiveStatus(true);
						}
					}
				}
			else if (reportDataBean.getFilterlist().get(a).getFilterName().equalsIgnoreCase("Class") && 
					reportDataBean.getFilterlist().get(a).isActiveStatus()==false) {
				for (int i = 0; i < reportDataBean.getFilterlist().size(); i++) {
					if (reportDataBean.getFilterlist().get(i).getFilterName().equalsIgnoreCase("Section")){
						reportDataBean.getFilterlist().get(i).setActiveStatus(false);
						}
					}
				}
	}
	public void reset() {
		reportDataBean.setAttendanData(new ArrayList<AttendanceDataBean>());
		reportDataBean.setTeacherAttendanData(new ArrayList<ReportDataBean>());
		reportDataBean.setTeacherTimeTableData(new ArrayList<ReportDataBean>());
		for (int i = 0; i < filterData.size(); i++) {
			if (filterData.get(i).getFilterName().equalsIgnoreCase("Class")) {
				reportDataBean.setClassFlag(filterData.get(i).isActiveStatus());
			} 
			else if (filterData.get(i).getFilterName().equalsIgnoreCase("Section")) {
				reportDataBean.setSectionFlag(filterData.get(i).isActiveStatus());
			} 
			else if (filterData.get(i).getFilterName().equalsIgnoreCase("Student Name")) {
				reportDataBean.setStudentNameFlag(filterData.get(i).isActiveStatus());
			} 
			else if (filterData.get(i).getFilterName().equalsIgnoreCase("Month")) {
				reportDataBean.setMonthFlag(filterData.get(i).isActiveStatus());
			} 
			else if (filterData.get(i).getFilterName().equalsIgnoreCase("Year")) {
				reportDataBean.setYearFlag(filterData.get(i).isActiveStatus());
			} 
			else if (filterData.get(i).getFilterName().equalsIgnoreCase("Date")) {
				reportDataBean.setDateFlag(filterData.get(i).isActiveStatus());
			} 
			else if (filterData.get(i).getFilterName().equalsIgnoreCase("Exam Title")) {
				reportDataBean.setExamTitleFlag(filterData.get(i).isActiveStatus());
			}
			else if (filterData.get(i).getFilterName().equalsIgnoreCase("Teacher Name")) {
				reportDataBean.setTeacherNameFlag(filterData.get(i).isActiveStatus());
			}
			else if (filterData.get(i).getFilterName().equalsIgnoreCase("Status")) {
				reportDataBean.setAttendanceStatusFlag(filterData.get(i).isActiveStatus());
			}
			else if (filterData.get(i).getFilterName().equalsIgnoreCase("Leave Type")) {
				reportDataBean.setLeaveTypeFlag(filterData.get(i).isActiveStatus());
			}
			
			
			else if (filterData.get(i).getFilterName().equalsIgnoreCase("Day")) {
				reportDataBean.setDayFlag(filterData.get(i).isActiveStatus());
			}
			else if (filterData.get(i).getFilterName().equalsIgnoreCase("Subject Name")) {
				reportDataBean.setSubjectNameFlag(filterData.get(i).isActiveStatus());
			}
			else if (filterData.get(i).getFilterName().equalsIgnoreCase("Period")) {
				reportDataBean.setPeriodFlag(filterData.get(i).isActiveStatus());
			}
			
		}
		reportDataBean.setStudentList(new ArrayList<String>());
		if (reportDataBean.isClassFlag()==false && reportDataBean.isSectionFlag()==false && reportDataBean.isStudentNameFlag()==true) {
			reportDataBean.setSecName("All");
			reportDataBean.setClassName("All");
			controller.secNamebasedData(reportDataBean);
		}
		reportDataBean.setClassName("");
		reportDataBean.setSecName("");
		reportDataBean.setDate1(null);
		reportDataBean.setMonth("");
		reportDataBean.setYear("");
		reportDataBean.setReportName("");
		reportDataBean.setSectionlist(new ArrayList<String>());
		reportDataBean.setDay("");
		reportDataBean.setSubjectName("");
		reportDataBean.setPeriod("");
		reportDataBean.setTeacherName("");
		reportDataBean.setAttendanceStatus("");
		reportDataBean.setLeaveType("");
		setMarkDataTable(false);
		setTeacherAttendanceDataTable(false);
		setTeacherTimetableDataTable(false);
	}
	
}
