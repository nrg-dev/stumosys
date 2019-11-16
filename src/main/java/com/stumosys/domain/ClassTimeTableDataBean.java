package com.stumosys.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClassTimeTableDataBean implements Serializable {

	private static final long serialVersionUID = 1324458252163054068L;
	private String classname;
	private String day;
	private List<ClassTimeTableDataBean> classtimeList = null;
	private List<String> subjectlist = null;
	private String subject;
	private String teaID;
	private String subjectCode;
	private Date startTime;
	private Date endTime;
	private int no;
	private String subject1;
	private String subjectCode1;
	private Date startTime1;
	private String teaID1;
	private Date endTime1;
	private String subject2;
	private String subjectCode2;
	private Date startTime2;
	private String teaID2;
	private Date endTime2;
	private String subject3;
	private String subjectCode3;
	private Date startTime3;
	private String teaID3;
	private Date endTime3;
	private String subject4;
	private String subjectCode4;
	private String teaID4;
	private Date startTime4;
	private Date endTime4;
	private String subject5;
	private String subjectCode5;
	private String teaID5;
	private Date startTime5;
	private Date endTime5;
	private String subject6;
	private String teaID6;
	private String subjectCode6;
	private Date startTime6;
	private Date endTime6;
	private String subject7;
	private String subjectCode7;
	private String teaID7;
	private Date startTime7;
	private Date endTime7;
	private String subjectCodes;
	private String subjects;
	private String status;
	private String timeStart;
	private String timeEnd;
	private int serialno;
	private String year;
	private String period;
	private List<String> mails = null;
	private Date date;
	public String exSubject;
	public String exSubject1;
	public String exSubjectcode;
	public String exSubjectcode1;
	private List<String> teacherIDList = new ArrayList<String>();
	private List<String> teacherIDList1 = new ArrayList<String>();
	private List<String> teacherIDList2 = new ArrayList<String>();
	private List<String> teacherIDList3 = new ArrayList<String>();
	private List<String> teacherIDList4 = new ArrayList<String>();
	private List<String> teacherIDList5 = new ArrayList<String>();
	private List<String> teacherIDList6 = new ArrayList<String>();
	private List<String> teacherIDList7 = new ArrayList<String>();
	private String filepath;
	private List<String> phonenos=null;
	private String schoolName;
	private int schoolID;
	private String classSection;
	private String schoolLogo;
	private String teaID8;
	private String teaID9;
	private int classtableid;
	
	private String startTimeHr;
	private String startTimeMin;
	private String endTimeHr;
	private String endTimeMin;
	private String month;
	private List<String> monthlist=null;
	private String StartTimeMobile;
	private String EndTimeMobile;
	
	
	private List<String> subjectcodelist=null;

	public String getStartTimeMobile() {
		return StartTimeMobile;
	}

	public void setStartTimeMobile(String startTimeMobile) {
		StartTimeMobile = startTimeMobile;
	}

	public String getEndTimeMobile() {
		return EndTimeMobile;
	}

	public void setEndTimeMobile(String endTimeMobile) {
		EndTimeMobile = endTimeMobile;
	}

	public List<String> getMonthlist() {
		return monthlist;
	}

	public void setMonthlist(List<String> monthlist) {
		this.monthlist = monthlist;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getStartTimeHr() {
		return startTimeHr;
	}

	public void setStartTimeHr(String startTimeHr) {
		this.startTimeHr = startTimeHr;
	}

	public String getStartTimeMin() {
		return startTimeMin;
	}

	public void setStartTimeMin(String startTimeMin) {
		this.startTimeMin = startTimeMin;
	}

	public String getEndTimeHr() {
		return endTimeHr;
	}

	public void setEndTimeHr(String endTimeHr) {
		this.endTimeHr = endTimeHr;
	}

	public String getEndTimeMin() {
		return endTimeMin;
	}

	public void setEndTimeMin(String endTimeMin) {
		this.endTimeMin = endTimeMin;
	}

	public List<String> getSubjectcodelist() {
		return subjectcodelist;
	}

	public void setSubjectcodelist(List<String> subjectcodelist) {
		this.subjectcodelist = subjectcodelist;
	}

	public int getClasstableid() {
		return classtableid;
	}

	public void setClasstableid(int classtableid) {
		this.classtableid = classtableid;
	}

	public String getTeaID8() {
		return teaID8;
	}

	public void setTeaID8(String teaID8) {
		this.teaID8 = teaID8;
	}

	public String getTeaID9() {
		return teaID9;
	}

	public void setTeaID9(String teaID9) {
		this.teaID9 = teaID9;
	}

	public String getSchoolLogo() {
		return schoolLogo;
	}

	public void setSchoolLogo(String schoolLogo) {
		this.schoolLogo = schoolLogo;
	}
	public String getClassSection() {
		return classSection;
	}

	public void setClassSection(String classSection) {
		this.classSection = classSection;
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

	public List<String> getPhonenos() {
		return phonenos;
	}

	public void setPhonenos(List<String> phonenos) {
		this.phonenos = phonenos;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getExSubject() {
		return exSubject;
	}

	public void setExSubject(String exSubject) {
		this.exSubject = exSubject;
	}

	public String getExSubject1() {
		return exSubject1;
	}

	public void setExSubject1(String exSubject1) {
		this.exSubject1 = exSubject1;
	}

	public String getExSubjectcode() {
		return exSubjectcode;
	}

	public void setExSubjectcode(String exSubjectcode) {
		this.exSubjectcode = exSubjectcode;
	}

	public String getExSubjectcode1() {
		return exSubjectcode1;
	}

	public void setExSubjectcode1(String exSubjectcode1) {
		this.exSubjectcode1 = exSubjectcode1;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<String> getMails() {
		return mails;
	}

	public void setMails(List<String> mails) {
		this.mails = mails;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getSerialno() {
		return serialno;
	}

	public void setSerialno(int serialno) {
		this.serialno = serialno;
	}

	
	
	public String getTimeStart() {
		return timeStart;
	}

	

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubjects() {
		return subjects;
	}

	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}

	public String getSubjectCodes() {
		return subjectCodes;
	}

	public void setSubjectCodes(String subjectCodes) {
		this.subjectCodes = subjectCodes;
	}

	public String getSubject1() {
		return subject1;
	}

	public void setSubject1(String subject1) {
		this.subject1 = subject1;
	}

	public String getSubjectCode1() {
		return subjectCode1;
	}

	public void setSubjectCode1(String subjectCode1) {
		this.subjectCode1 = subjectCode1;
	}

	public Date getStartTime1() {
		return startTime1;
	}

	public void setStartTime1(Date startTime1) {
		this.startTime1 = startTime1;
	}

	public Date getEndTime1() {
		return endTime1;
	}

	public void setEndTime1(Date endTime1) {
		this.endTime1 = endTime1;
	}

	public String getSubject2() {
		return subject2;
	}

	public void setSubject2(String subject2) {
		this.subject2 = subject2;
	}

	public String getSubjectCode2() {
		return subjectCode2;
	}

	public void setSubjectCode2(String subjectCode2) {
		this.subjectCode2 = subjectCode2;
	}

	public Date getStartTime2() {
		return startTime2;
	}

	public void setStartTime2(Date startTime2) {
		this.startTime2 = startTime2;
	}

	public Date getEndTime2() {
		return endTime2;
	}

	public void setEndTime2(Date endTime2) {
		this.endTime2 = endTime2;
	}

	public String getSubject3() {
		return subject3;
	}

	public void setSubject3(String subject3) {
		this.subject3 = subject3;
	}

	public String getSubjectCode3() {
		return subjectCode3;
	}

	public void setSubjectCode3(String subjectCode3) {
		this.subjectCode3 = subjectCode3;
	}

	public Date getStartTime3() {
		return startTime3;
	}

	public void setStartTime3(Date startTime3) {
		this.startTime3 = startTime3;
	}

	public Date getEndTime3() {
		return endTime3;
	}

	public void setEndTime3(Date endTime3) {
		this.endTime3 = endTime3;
	}

	public String getSubject4() {
		return subject4;
	}

	public void setSubject4(String subject4) {
		this.subject4 = subject4;
	}

	public String getSubjectCode4() {
		return subjectCode4;
	}

	public void setSubjectCode4(String subjectCode4) {
		this.subjectCode4 = subjectCode4;
	}

	public Date getStartTime4() {
		return startTime4;
	}

	public void setStartTime4(Date startTime4) {
		this.startTime4 = startTime4;
	}

	public Date getEndTime4() {
		return endTime4;
	}

	public void setEndTime4(Date endTime4) {
		this.endTime4 = endTime4;
	}

	public String getSubject5() {
		return subject5;
	}

	public void setSubject5(String subject5) {
		this.subject5 = subject5;
	}

	public String getSubjectCode5() {
		return subjectCode5;
	}

	public void setSubjectCode5(String subjectCode5) {
		this.subjectCode5 = subjectCode5;
	}

	public Date getStartTime5() {
		return startTime5;
	}

	public void setStartTime5(Date startTime5) {
		this.startTime5 = startTime5;
	}

	public Date getEndTime5() {
		return endTime5;
	}

	public void setEndTime5(Date endTime5) {
		this.endTime5 = endTime5;
	}

	public String getSubject6() {
		return subject6;
	}

	public void setSubject6(String subject6) {
		this.subject6 = subject6;
	}

	public String getSubjectCode6() {
		return subjectCode6;
	}

	public void setSubjectCode6(String subjectCode6) {
		this.subjectCode6 = subjectCode6;
	}

	public Date getStartTime6() {
		return startTime6;
	}

	public void setStartTime6(Date startTime6) {
		this.startTime6 = startTime6;
	}

	public Date getEndTime6() {
		return endTime6;
	}

	public void setEndTime6(Date endTime6) {
		this.endTime6 = endTime6;
	}

	public String getSubject7() {
		return subject7;
	}

	public void setSubject7(String subject7) {
		this.subject7 = subject7;
	}

	public String getSubjectCode7() {
		return subjectCode7;
	}

	public void setSubjectCode7(String subjectCode7) {
		this.subjectCode7 = subjectCode7;
	}

	public Date getStartTime7() {
		return startTime7;
	}

	public void setStartTime7(Date startTime7) {
		this.startTime7 = startTime7;
	}

	public Date getEndTime7() {
		return endTime7;
	}

	public void setEndTime7(Date endTime7) {
		this.endTime7 = endTime7;
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

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public List<String> getSubjectlist() {
		return subjectlist;
	}

	public void setSubjectlist(List<String> subjectlist) {
		this.subjectlist = subjectlist;
	}

	public List<ClassTimeTableDataBean> getClasstimeList() {
		return classtimeList;
	}

	public void setClasstimeList(List<ClassTimeTableDataBean> classtimeList) {
		this.classtimeList = classtimeList;
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

	public List<String> getTeacherIDList() {
		return teacherIDList;
	}

	public void setTeacherIDList(List<String> teacherIDList) {
		this.teacherIDList = teacherIDList;
	}

	public String getTeaID() {
		return teaID;
	}

	public void setTeaID(String teaID) {
		this.teaID = teaID;
	}

	public String getTeaID1() {
		return teaID1;
	}

	public void setTeaID1(String teaID1) {
		this.teaID1 = teaID1;
	}

	public String getTeaID2() {
		return teaID2;
	}

	public void setTeaID2(String teaID2) {
		this.teaID2 = teaID2;
	}

	public String getTeaID3() {
		return teaID3;
	}

	public void setTeaID3(String teaID3) {
		this.teaID3 = teaID3;
	}

	public String getTeaID4() {
		return teaID4;
	}

	public void setTeaID4(String teaID4) {
		this.teaID4 = teaID4;
	}

	public String getTeaID5() {
		return teaID5;
	}

	public void setTeaID5(String teaID5) {
		this.teaID5 = teaID5;
	}

	public String getTeaID6() {
		return teaID6;
	}

	public void setTeaID6(String teaID6) {
		this.teaID6 = teaID6;
	}

	public String getTeaID7() {
		return teaID7;
	}

	public void setTeaID7(String teaID7) {
		this.teaID7 = teaID7;
	}

	public List<String> getTeacherIDList1() {
		return teacherIDList1;
	}

	public void setTeacherIDList1(List<String> teacherIDList1) {
		this.teacherIDList1 = teacherIDList1;
	}

	public List<String> getTeacherIDList2() {
		return teacherIDList2;
	}

	public void setTeacherIDList2(List<String> teacherIDList2) {
		this.teacherIDList2 = teacherIDList2;
	}

	public List<String> getTeacherIDList3() {
		return teacherIDList3;
	}

	public void setTeacherIDList3(List<String> teacherIDList3) {
		this.teacherIDList3 = teacherIDList3;
	}

	public List<String> getTeacherIDList4() {
		return teacherIDList4;
	}

	public void setTeacherIDList4(List<String> teacherIDList4) {
		this.teacherIDList4 = teacherIDList4;
	}

	public List<String> getTeacherIDList5() {
		return teacherIDList5;
	}

	public void setTeacherIDList5(List<String> teacherIDList5) {
		this.teacherIDList5 = teacherIDList5;
	}

	public List<String> getTeacherIDList6() {
		return teacherIDList6;
	}

	public void setTeacherIDList6(List<String> teacherIDList6) {
		this.teacherIDList6 = teacherIDList6;
	}

	public List<String> getTeacherIDList7() {
		return teacherIDList7;
	}

	public void setTeacherIDList7(List<String> teacherIDList7) {
		this.teacherIDList7 = teacherIDList7;
	}

}
