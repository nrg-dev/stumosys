package com.stumosys.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.swing.text.StyledEditorKit.BoldAction;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReportDataBean implements Serializable {
	private static final long serialVersionUID = -1244307270397939832L;

	
	private String reportType;
	private String reportName;
	private String reportdescription;
	private List<ReportDataBean> reportList = null;
	private List<ReportDataBean> filterlist = null;
	private String filterName;
	private boolean activeStatus;
	private String reportsubType;
	private String schoolID;
	private int reportID;
	private int rowindex;
	private String className;
	private String secName;
	private String studentName;
	private String subjectName;
	private String subjectCode;
	private String resultStatus;
	private String month;
	private String year;
	private String examTitle;
	private Date date1;
	private List<String> sectionlist=null;
	
	private String teacherName;
	private String teacherRoll;
	private String attendanceStatus;
	private String leaveType;
	private String inTime;
	private String outTime;
	
	private String day;
	private String period;
	
	private boolean classFlag;
	private boolean sectionFlag;
	private boolean studentNameFlag;
	private boolean monthFlag;
	private boolean yearFlag;
	private boolean dateFlag;
	private boolean examTitleFlag;
	private boolean subjectNameFlag;
	private boolean resultStatusFlag;
	
	private boolean teacherNameFlag;
	private boolean attendanceStatusFlag;
	private boolean leaveTypeFlag;
	
	private boolean dayFlag;
	private boolean periodFlag;
	
	
	private List<String> studentList=null;
	private List<String> teacherList=null;
	private List<String> subjectList=null;
	
	private List<ReportDataBean> teacherAttendanData=null;
	private List<AttendanceDataBean> attendanData=null;
	private List<StudentPerformanceDataBean> performanceData=null;
	private List<ReportCardDataBean> reportCardData=null;
	
	private List<ReportDataBean> teacherTimeTableData=null;
	

	private String reference;
	
	

	public String getTeacherRoll() {
		return teacherRoll;
	}


	public void setTeacherRoll(String teacherRoll) {
		this.teacherRoll = teacherRoll;
	}


	public String getSubjectCode() {
		return subjectCode;
	}


	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}


	public String getReference() {
		return reference;
	}


	public void setReference(String reference) {
		this.reference = reference;
	}


	public List<ReportDataBean> getTeacherTimeTableData() {
		return teacherTimeTableData;
	}


	public void setTeacherTimeTableData(List<ReportDataBean> teacherTimeTableData) {
		this.teacherTimeTableData = teacherTimeTableData;
	}


	public String getPeriod() {
		return period;
	}


	public void setPeriod(String period) {
		this.period = period;
	}



	public boolean isPeriodFlag() {
		return periodFlag;
	}


	public void setPeriodFlag(boolean periodFlag) {
		this.periodFlag = periodFlag;
	}


	public List<String> getSubjectList() {
		return subjectList;
	}


	public void setSubjectList(List<String> subjectList) {
		this.subjectList = subjectList;
	}


	public String getDay() {
		return day;
	}


	public void setDay(String day) {
		this.day = day;
	}


	public boolean isDayFlag() {
		return dayFlag;
	}


	public void setDayFlag(boolean dayFlag) {
		this.dayFlag = dayFlag;
	}


	public String getInTime() {
		return inTime;
	}


	public void setInTime(String inTime) {
		this.inTime = inTime;
	}


	public String getOutTime() {
		return outTime;
	}


	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}


	public String getLeaveType() {
		return leaveType;
	}


	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}


	public boolean isLeaveTypeFlag() {
		return leaveTypeFlag;
	}


	public void setLeaveTypeFlag(boolean leaveTypeFlag) {
		this.leaveTypeFlag = leaveTypeFlag;
	}


	public String getAttendanceStatus() {
		return attendanceStatus;
	}


	public void setAttendanceStatus(String attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}


	public boolean isAttendanceStatusFlag() {
		return attendanceStatusFlag;
	}


	public void setAttendanceStatusFlag(boolean attendanceStatusFlag) {
		this.attendanceStatusFlag = attendanceStatusFlag;
	}


	public String getTeacherName() {
		return teacherName;
	}


	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}


	public boolean isTeacherNameFlag() {
		return teacherNameFlag;
	}


	public void setTeacherNameFlag(boolean teacherNameFlag) {
		this.teacherNameFlag = teacherNameFlag;
	}


	public List<String> getTeacherList() {
		return teacherList;
	}


	public void setTeacherList(List<String> teacherList) {
		this.teacherList = teacherList;
	}

	public List<ReportDataBean> getTeacherAttendanData() {
		return teacherAttendanData;
	}


	public void setTeacherAttendanData(List<ReportDataBean> teacherAttendanData) {
		this.teacherAttendanData = teacherAttendanData;
	}


	public String getSubjectName() {
		return subjectName;
	}


	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}


	public String getResultStatus() {
		return resultStatus;
	}


	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}


	public boolean isSubjectNameFlag() {
		return subjectNameFlag;
	}


	public void setSubjectNameFlag(boolean subjectNameFlag) {
		this.subjectNameFlag = subjectNameFlag;
	}


	public boolean isResultStatusFlag() {
		return resultStatusFlag;
	}


	public void setResultStatusFlag(boolean resultStatusFlag) {
		this.resultStatusFlag = resultStatusFlag;
	}


	public String getExamTitle() {
		return examTitle;
	}


	public void setExamTitle(String examTitle) {
		this.examTitle = examTitle;
	}


	public boolean isExamTitleFlag() {
		return examTitleFlag;
	}


	public void setExamTitleFlag(boolean examTitleFlag) {
		this.examTitleFlag = examTitleFlag;
	}


	public List<ReportCardDataBean> getReportCardData() {
		return reportCardData;
	}


	public void setReportCardData(List<ReportCardDataBean> reportCardData) {
		this.reportCardData = reportCardData;
	}


	public List<StudentPerformanceDataBean> getPerformanceData() {
		return performanceData;
	}


	public void setPerformanceData(List<StudentPerformanceDataBean> performanceData) {
		this.performanceData = performanceData;
	}


	public int getRowindex() {
		return rowindex;
	}


	public void setRowindex(int rowindex) {
		this.rowindex = rowindex;
	}


	public List<String> getStudentList() {
		return studentList;
	}


	public void setStudentList(List<String> studentList) {
		this.studentList = studentList;
	}


	public List<AttendanceDataBean> getAttendanData() {
		return attendanData;
	}


	public void setAttendanData(List<AttendanceDataBean> attendanData) {
		this.attendanData = attendanData;
	}


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public Date getDate1() {
		return date1;
	}


	public void setDate1(Date date1) {
		this.date1 = date1;
	}


	public String getStudentName() {
		return studentName;
	}


	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}


	public List<String> getSectionlist() {
		return sectionlist;
	}


	public void setSectionlist(List<String> sectionlist) {
		this.sectionlist = sectionlist;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	public String getSecName() {
		return secName;
	}


	public void setSecName(String secName) {
		this.secName = secName;
	}


	public int getReportID() {
		return reportID;
	}


	public void setReportID(int reportID) {
		this.reportID = reportID;
	}


	public boolean isClassFlag() {
		return classFlag;
	}


	public void setClassFlag(boolean classFlag) {
		this.classFlag = classFlag;
	}


	public boolean isSectionFlag() {
		return sectionFlag;
	}


	public void setSectionFlag(boolean sectionFlag) {
		this.sectionFlag = sectionFlag;
	}


	public boolean isStudentNameFlag() {
		return studentNameFlag;
	}


	public void setStudentNameFlag(boolean studentNameFlag) {
		this.studentNameFlag = studentNameFlag;
	}


	public boolean isMonthFlag() {
		return monthFlag;
	}


	public void setMonthFlag(boolean monthFlag) {
		this.monthFlag = monthFlag;
	}


	public boolean isYearFlag() {
		return yearFlag;
	}


	public void setYearFlag(boolean yearFlag) {
		this.yearFlag = yearFlag;
	}


	public boolean isDateFlag() {
		return dateFlag;
	}


	public void setDateFlag(boolean dateFlag) {
		this.dateFlag = dateFlag;
	}


	public List<ReportDataBean> getFilterlist() {
		return filterlist;
	}


	public void setFilterlist(List<ReportDataBean> filterlist) {
		this.filterlist = filterlist;
	}


	public String getSchoolID() {
		return schoolID;
	}


	public void setSchoolID(String schoolID) {
		this.schoolID = schoolID;
	}


	public String getReportsubType() {
		return reportsubType;
	}


	public void setReportsubType(String reportsubType) {
		this.reportsubType = reportsubType;
	}


	public String getFilterName() {
		return filterName;
	}


	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}


	

	public boolean isActiveStatus() {
		return activeStatus;
	}


	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}


	public List<ReportDataBean> getReportList() {
		return reportList;
	}


	public void setReportList(List<ReportDataBean> reportList) {
		this.reportList = reportList;
	}


	public String getReportName() {
		return reportName;
	}


	public void setReportName(String reportName) {
		this.reportName = reportName;
	}


	public String getReportdescription() {
		return reportdescription;
	}


	public void setReportdescription(String reportdescription) {
		this.reportdescription = reportdescription;
	}


	public String getReportType() {
		return reportType;
	}


	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	
}
