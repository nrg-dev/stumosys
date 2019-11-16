package com.stumosys.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.stumosys.shared.StudentAttendance;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class AttendanceDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4035485212314357699L;

	private String classname;
	private String Section;
	private Date date;
	private List<AttendanceDataBean> studentList = null;
	private String studentID;
	private String studentName;
	private String category;
	private String percentage;
	private int sno;
	private String status;
	private String status1;
	private Timestamp time;
	private String time1;
	private String monthyear;
	private List<AttendanceDataBean> chart = null;
	private Date stdate;
	private Date endate;
	private String absent;
	private String leave;
	private String mailid;
	private boolean flag = false;
	private boolean flag1 = false;
	private String period;
	private List<String> periods = null;
	private List<String> mails = null;
	private String late;
	private String year;
	private List<String> years = null;
	private String classStudent;
	private List<String> students = null;
	private String leavereason;
	private String returnStatus;
	private List<String> phones=null;
	private String schoolName;
	private int schoolID;
	private String classnames;
	private String schoolLogo;
	private int dmonth;
	private int dyear;
	private Date monthDates;
	private String inTime;
	private String outTime;
	private String presentStatus;
	private String presentDay;
	private String inTime1;// What is this inTime1 ?  
	private String outTime1; // ? 
	private String presentStatus1; // ? 
	private String presentDay1; // ? 	
	private List<AttendanceDataBean> attendanceList=null;
	List<StudentAttendance> studentAttendanceList=null; 
	private String selectedmonth;
	private boolean attendanceFlag=false;
	private boolean attendanceeditFlag=false;
	private boolean presentFlag=false;
	private boolean absentFlag=false;
	private boolean presentFlag1=false;
	private boolean absentFlag1=false;
	private String phonesNo;
	private String month;
	private String absentMarkedBy;
	
	
	public String getAbsentMarkedBy() {
		return absentMarkedBy;
	}

	public void setAbsentMarkedBy(String absentMarkedBy) {
		this.absentMarkedBy = absentMarkedBy;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getPhonesNo() {
		return phonesNo;
	}

	public void setPhonesNo(String phonesNo) {
		this.phonesNo = phonesNo;
	}

	public String getSchoolLogo() {
		return schoolLogo;
	}
	
	public void setSchoolLogo(String schoolLogo) {
		this.schoolLogo = schoolLogo;
	}
	public String getClassnames() {
		return classnames;
	}

	public void setClassnames(String classnames) {
		this.classnames = classnames;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public int getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(int schoolID) {
		this.schoolID = schoolID;
	}

	public List<String> getPhones() {
		return phones;
	}

	public void setPhones(List<String> phones) {
		this.phones = phones;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
	public String getLeavereason() {
		return leavereason;
	}

	public void setLeavereason(String leavereason) {
		this.leavereason = leavereason;
	}

	public List<String> getStudents() {
		return students;
	}

	public void setStudents(List<String> students) {
		this.students = students;
	}

	public String getClassStudent() {
		return classStudent;
	}

	public void setClassStudent(String classStudent) {
		this.classStudent = classStudent;
	}

	public List<String> getYears() {
		return years;
	}

	public void setYears(List<String> years) {
		this.years = years;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getLate() {
		return late;
	}

	public void setLate(String late) {
		this.late = late;
	}

	public List<String> getMails() {
		return mails;
	}

	public void setMails(List<String> mails) {
		this.mails = mails;
	}

	public List<String> getPeriods() {
		return periods;
	}

	public void setPeriods(List<String> periods) {
		this.periods = periods;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
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

	/**
	 * @return the mailid
	 */
	public String getMailid() {
		return mailid;
	}

	/**
	 * @param mailid
	 *            the mailid to set
	 */
	public void setMailid(String mailid) {
		this.mailid = mailid;
	}

	public String getAbsent() {
		return absent;
	}

	public void setAbsent(String absent) {
		this.absent = absent;
	}

	public String getLeave() {
		return leave;
	}

	public void setLeave(String leave) {
		this.leave = leave;
	}

	public Date getStdate() {
		return stdate;
	}

	public void setStdate(Date stdate) {
		this.stdate = stdate;
	}

	public Date getEndate() {
		return endate;
	}

	public void setEndate(Date endate) {
		this.endate = endate;
	}

	public List<AttendanceDataBean> getChart() {
		return chart;
	}

	public void setChart(List<AttendanceDataBean> chart) {
		this.chart = chart;
	}

	public String getMonthyear() {
		return monthyear;
	}

	public void setMonthyear(String monthyear) {
		this.monthyear = monthyear;
	}

	public String getTime1() {
		return time1;
	}

	public void setTime1(String time1) {
		this.time1 = time1;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public List<AttendanceDataBean> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<AttendanceDataBean> studentList) {
		this.studentList = studentList;
	}

	/**
	 * @return the classname
	 */
	

	/**
	 * @return the section
	 */
	public String getSection() {
		return Section;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	/**
	 * @param section
	 *            the section to set
	 */
	public void setSection(String section) {
		Section = section;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	public int getDmonth() {
		return dmonth;
	}

	public void setDmonth(int dmonth) {
		this.dmonth = dmonth;
	}

	public int getDyear() {
		return dyear;
	}

	public void setDyear(int dyear) {
		this.dyear = dyear;
	}

	public Date getMonthDates() {
		return monthDates;
	}

	public void setMonthDates(Date monthDates) {
		this.monthDates = monthDates;
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

	public String getPresentStatus() {
		return presentStatus;
	}

	public void setPresentStatus(String presentStatus) {
		this.presentStatus = presentStatus;
	}

	public String getPresentDay() {
		return presentDay;
	}

	public void setPresentDay(String presentDay) {
		this.presentDay = presentDay;
	}

	

	public String getInTime1() {
		return inTime1;
	}

	public void setInTime1(String inTime1) {
		this.inTime1 = inTime1;
	}

	public String getOutTime1() {
		return outTime1;
	}

	public void setOutTime1(String outTime1) {
		this.outTime1 = outTime1;
	}

	public String getPresentStatus1() {
		return presentStatus1;
	}

	public void setPresentStatus1(String presentStatus1) {
		this.presentStatus1 = presentStatus1;
	}

	public String getPresentDay1() {
		return presentDay1;
	}

	public void setPresentDay1(String presentDay1) {
		this.presentDay1 = presentDay1;
	}

	public List<AttendanceDataBean> getAttendanceList() {
		return attendanceList;
	}

	public void setAttendanceList(List<AttendanceDataBean> attendanceList) {
		this.attendanceList = attendanceList;
	}

	public List<StudentAttendance> getStudentAttendanceList() {
		return studentAttendanceList;
	}

	public void setStudentAttendanceList(List<StudentAttendance> studentAttendanceList) {
		this.studentAttendanceList = studentAttendanceList;
	}

	public String getSelectedmonth() {
		return selectedmonth;
	}

	public void setSelectedmonth(String selectedmonth) {
		this.selectedmonth = selectedmonth;
	}

	public boolean isAttendanceFlag() {
		return attendanceFlag;
	}

	public void setAttendanceFlag(boolean attendanceFlag) {
		this.attendanceFlag = attendanceFlag;
	}

	public boolean isAttendanceeditFlag() {
		return attendanceeditFlag;
	}

	public void setAttendanceeditFlag(boolean attendanceeditFlag) {
		this.attendanceeditFlag = attendanceeditFlag;
	}

	public boolean isPresentFlag() {
		return presentFlag;
	}

	public void setPresentFlag(boolean presentFlag) {
		this.presentFlag = presentFlag;
	}

	public boolean isAbsentFlag() {
		return absentFlag;
	}

	public void setAbsentFlag(boolean absentFlag) {
		this.absentFlag = absentFlag;
	}

	public boolean isPresentFlag1() {
		return presentFlag1;
	}

	public void setPresentFlag1(boolean presentFlag1) {
		this.presentFlag1 = presentFlag1;
	}

	public boolean isAbsentFlag1() {
		return absentFlag1;
	}

	public void setAbsentFlag1(boolean absentFlag1) {
		this.absentFlag1 = absentFlag1;
	}
	
	

}
