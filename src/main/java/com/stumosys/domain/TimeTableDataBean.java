package com.stumosys.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.primefaces.extensions.component.timepicker.TimePicker;

import com.stumosys.shared.ExamTable;
//import com.stumosys.shared.TimeTable;
@XmlRootElement
public class TimeTableDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1324458252163054068L;

	private String examTitle;
	private Date examStartDate;
	private String examClass;
	private String examtTotalTime;
	private Date examDate;
	private String examDay;
	private String examSubject;
	private String examSubjectCode;
	private String examRoomNo;
	private Date examStartTime;
	private Date examEndTime;
	private String examShift;
	private Date examStartDate1;
	private Date examDate1;
	private String examDay1;
	private String examSubject1;
	private String examSubjectCode1;
	private String examRoomNo1;
	private Date examStartTime1;
	private Date examEndTime1;
	private String examShift1;
	private Date examStartDate2;
	private Date examDate2;
	private String examDay2;
	private String examSubject2;
	private String examSubjectCode2;
	private String examRoomNo2;
	private Date examStartTime2;
	private Date examEndTime2;
	private String examShift2;
	private Date examStartDate3;
	private Date examDate3;
	private String examDay3;
	private String examSubject3;
	private String examSubjectCode3;
	private String examRoomNo3;
	private Date examStartTime3;
	private Date examEndTime3;
	private String examShift3;
	private Date examStartDate4;
	private Date examDate4;
	private String examDay4;
	private String examSubject4;
	private String examSubjectCode4;
	private String examRoomNo4;
	private Date examStartTime4;
	private Date examEndTime4;
	private String examShift4;
	private List<TimeTableDataBean> classList;
	private List<String> sublist;
	private int class_id;
	private String examSubjectCodes;
	private String status;
	private int examtableId;
	private List<TimeTableDataBean> tablelist;
	private String stime;
	private String etime;
	private TimePicker pp;
	private String studentID;
	private List<String> mails;
	private List<ExamTable> timetablesList = null;
	private String examtimeTableStatus;
	private String filepath;
	private List<String> phonenos=null;
	private String schoolName;
	private int schoolID;
	private String schoolLogo;
	private String examFromMark;
	private String examToMark;
	private String examGrade;
	public String gradedetailId;
	private String examResult;
	
	public String getExamResult() {
		return examResult;
	}
	public void setExamResult(String examResult) {
		this.examResult = examResult;
	}	
			
	public String getGradedetailId() {
		return gradedetailId;
	}
	public void setGradedetailId(String gradedetailId) {
		this.gradedetailId = gradedetailId;
	}
	public String getExamFromMark() {
		return examFromMark;
	}
	public void setExamFromMark(String examFromMark) {
		this.examFromMark = examFromMark;
	}
	public String getExamToMark() {
		return examToMark;
	}
	public void setExamToMark(String examToMark) {
		this.examToMark = examToMark;
	}
	public String getExamGrade() {
		return examGrade;
	}
	public void setExamGrade(String examGrade) {
		this.examGrade = examGrade;
	}
	public String getSchoolLogo() {
		return schoolLogo;
	}

	public void setSchoolLogo(String schoolLogo) {
		this.schoolLogo = schoolLogo;
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

	public String getExamtimeTableStatus() {
		return examtimeTableStatus;
	}

	public void setExamtimeTableStatus(String examtimeTableStatus) {
		this.examtimeTableStatus = examtimeTableStatus;
	}

	public List<String> getMails() {
		return mails;
	}

	public void setMails(List<String> mails) {
		this.mails = mails;
	}

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public TimePicker getPp() {
		return pp;
	}

	public void setPp(TimePicker pp) {
		this.pp = pp;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public List<TimeTableDataBean> getTablelist() {
		return tablelist;
	}

	public void setTablelist(List<TimeTableDataBean> tablelist) {
		this.tablelist = tablelist;
	}

	public int getExamtableId() {
		return examtableId;
	}

	public void setExamtableId(int examtableId) {
		this.examtableId = examtableId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getExamStartDate2() {
		return examStartDate2;
	}

	public void setExamStartDate2(Date examStartDate2) {
		this.examStartDate2 = examStartDate2;
	}

	public Date getExamDate2() {
		return examDate2;
	}

	public void setExamDate2(Date examDate2) {
		this.examDate2 = examDate2;
	}

	public String getExamDay2() {
		return examDay2;
	}

	public void setExamDay2(String examDay2) {
		this.examDay2 = examDay2;
	}

	public String getExamSubject2() {
		return examSubject2;
	}

	public void setExamSubject2(String examSubject2) {
		this.examSubject2 = examSubject2;
	}

	public String getExamSubjectCode2() {
		return examSubjectCode2;
	}

	public void setExamSubjectCode2(String examSubjectCode2) {
		this.examSubjectCode2 = examSubjectCode2;
	}

	public String getExamRoomNo2() {
		return examRoomNo2;
	}

	public void setExamRoomNo2(String examRoomNo2) {
		this.examRoomNo2 = examRoomNo2;
	}

	public Date getExamStartTime2() {
		return examStartTime2;
	}

	public void setExamStartTime2(Date examStartTime2) {
		this.examStartTime2 = examStartTime2;
	}

	public Date getExamEndTime2() {
		return examEndTime2;
	}

	public void setExamEndTime2(Date examEndTime2) {
		this.examEndTime2 = examEndTime2;
	}

	public String getExamShift2() {
		return examShift2;
	}

	public void setExamShift2(String examShift2) {
		this.examShift2 = examShift2;
	}

	public Date getExamStartDate3() {
		return examStartDate3;
	}

	public void setExamStartDate3(Date examStartDate3) {
		this.examStartDate3 = examStartDate3;
	}

	public Date getExamDate3() {
		return examDate3;
	}

	public void setExamDate3(Date examDate3) {
		this.examDate3 = examDate3;
	}

	public String getExamDay3() {
		return examDay3;
	}

	public void setExamDay3(String examDay3) {
		this.examDay3 = examDay3;
	}

	public String getExamSubject3() {
		return examSubject3;
	}

	public void setExamSubject3(String examSubject3) {
		this.examSubject3 = examSubject3;
	}

	public String getExamSubjectCode3() {
		return examSubjectCode3;
	}

	public void setExamSubjectCode3(String examSubjectCode3) {
		this.examSubjectCode3 = examSubjectCode3;
	}

	public String getExamRoomNo3() {
		return examRoomNo3;
	}

	public void setExamRoomNo3(String examRoomNo3) {
		this.examRoomNo3 = examRoomNo3;
	}

	public Date getExamStartTime3() {
		return examStartTime3;
	}

	public void setExamStartTime3(Date examStartTime3) {
		this.examStartTime3 = examStartTime3;
	}

	public Date getExamEndTime3() {
		return examEndTime3;
	}

	public void setExamEndTime3(Date examEndTime3) {
		this.examEndTime3 = examEndTime3;
	}

	public String getExamShift3() {
		return examShift3;
	}

	public void setExamShift3(String examShift3) {
		this.examShift3 = examShift3;
	}

	public Date getExamStartDate4() {
		return examStartDate4;
	}

	public void setExamStartDate4(Date examStartDate4) {
		this.examStartDate4 = examStartDate4;
	}

	public Date getExamDate4() {
		return examDate4;
	}

	public void setExamDate4(Date examDate4) {
		this.examDate4 = examDate4;
	}

	public String getExamDay4() {
		return examDay4;
	}

	public void setExamDay4(String examDay4) {
		this.examDay4 = examDay4;
	}

	public String getExamSubject4() {
		return examSubject4;
	}

	public void setExamSubject4(String examSubject4) {
		this.examSubject4 = examSubject4;
	}

	public String getExamSubjectCode4() {
		return examSubjectCode4;
	}

	public void setExamSubjectCode4(String examSubjectCode4) {
		this.examSubjectCode4 = examSubjectCode4;
	}

	public String getExamRoomNo4() {
		return examRoomNo4;
	}

	public void setExamRoomNo4(String examRoomNo4) {
		this.examRoomNo4 = examRoomNo4;
	}

	public Date getExamStartTime4() {
		return examStartTime4;
	}

	public void setExamStartTime4(Date examStartTime4) {
		this.examStartTime4 = examStartTime4;
	}

	public Date getExamEndTime4() {
		return examEndTime4;
	}

	public void setExamEndTime4(Date examEndTime4) {
		this.examEndTime4 = examEndTime4;
	}

	public String getExamShift4() {
		return examShift4;
	}

	public void setExamShift4(String examShift4) {
		this.examShift4 = examShift4;
	}

	public String getExamSubjectCodes() {
		return examSubjectCodes;
	}

	public void setExamSubjectCodes(String examSubjectCodes) {
		this.examSubjectCodes = examSubjectCodes;
	}

	public Date getExamStartDate1() {
		return examStartDate1;
	}

	public void setExamStartDate1(Date examStartDate1) {
		this.examStartDate1 = examStartDate1;
	}

	public Date getExamDate1() {
		return examDate1;
	}

	public void setExamDate1(Date examDate1) {
		this.examDate1 = examDate1;
	}

	public String getExamDay1() {
		return examDay1;
	}

	public void setExamDay1(String examDay1) {
		this.examDay1 = examDay1;
	}

	public String getExamSubject1() {
		return examSubject1;
	}

	public void setExamSubject1(String examSubject1) {
		this.examSubject1 = examSubject1;
	}

	public String getExamSubjectCode1() {
		return examSubjectCode1;
	}

	public void setExamSubjectCode1(String examSubjectCode1) {
		this.examSubjectCode1 = examSubjectCode1;
	}

	public String getExamRoomNo1() {
		return examRoomNo1;
	}

	public void setExamRoomNo1(String examRoomNo1) {
		this.examRoomNo1 = examRoomNo1;
	}

	public Date getExamStartTime1() {
		return examStartTime1;
	}

	public void setExamStartTime1(Date examStartTime1) {
		this.examStartTime1 = examStartTime1;
	}

	public Date getExamEndTime1() {
		return examEndTime1;
	}

	public void setExamEndTime1(Date examEndTime1) {
		this.examEndTime1 = examEndTime1;
	}

	public String getExamShift1() {
		return examShift1;
	}

	public void setExamShift1(String examShift1) {
		this.examShift1 = examShift1;
	}

	public int getClass_id() {
		return class_id;
	}

	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}

	public List<String> getSublist() {
		return sublist;
	}

	public void setSublist(List<String> sublist) {
		this.sublist = sublist;
	}

	public List<TimeTableDataBean> getClassList() {
		return classList;
	}

	public void setClassList(List<TimeTableDataBean> classList) {
		this.classList = classList;
	}

	/**
	 * @return the examTitle
	 */
	public String getExamTitle() {
		return examTitle;
	}

	/**
	 * @param examTitle
	 *            the examTitle to set
	 */
	public void setExamTitle(String examTitle) {
		this.examTitle = examTitle;
	}

	/**
	 * @return the examStartDate
	 */
	public Date getExamStartDate() {
		return examStartDate;
	}

	/**
	 * @param examStartDate
	 *            the examStartDate to set
	 */
	public void setExamStartDate(Date examStartDate) {
		this.examStartDate = examStartDate;
	}

	/**
	 * @return the examClass
	 */
	public String getExamClass() {
		return examClass;
	}

	/**
	 * @param examClass
	 *            the examClass to set
	 */
	public void setExamClass(String examClass) {
		this.examClass = examClass;
	}

	/**
	 * @return the examtTotalTime
	 */
	public String getExamtTotalTime() {
		return examtTotalTime;
	}

	/**
	 * @param examtTotalTime
	 *            the examtTotalTime to set
	 */
	public void setExamtTotalTime(String examtTotalTime) {
		this.examtTotalTime = examtTotalTime;
	}

	/**
	 * @return the examDate
	 */
	public Date getExamDate() {
		return examDate;
	}

	/**
	 * @param examDate
	 *            the examDate to set
	 */
	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	/**
	 * @return the examDay
	 */
	public String getExamDay() {
		return examDay;
	}

	/**
	 * @param examDay
	 *            the examDay to set
	 */
	public void setExamDay(String examDay) {
		this.examDay = examDay;
	}

	/**
	 * @return the examSubject
	 */
	public String getExamSubject() {
		return examSubject;
	}

	/**
	 * @param examSubject
	 *            the examSubject to set
	 */
	public void setExamSubject(String examSubject) {
		this.examSubject = examSubject;
	}

	/**
	 * @return the examSubjectCode
	 */
	public String getExamSubjectCode() {
		return examSubjectCode;
	}

	/**
	 * @param examSubjectCode
	 *            the examSubjectCode to set
	 */
	public void setExamSubjectCode(String examSubjectCode) {
		this.examSubjectCode = examSubjectCode;
	}

	/**
	 * @return the examRoomNo
	 */
	public String getExamRoomNo() {
		return examRoomNo;
	}

	/**
	 * @param examRoomNo
	 *            the examRoomNo to set
	 */
	public void setExamRoomNo(String examRoomNo) {
		this.examRoomNo = examRoomNo;
	}

	public Date getExamStartTime() {
		return examStartTime;
	}

	public void setExamStartTime(Date examStartTime) {
		this.examStartTime = examStartTime;
	}

	/**
	 * @param examEndTime
	 *            the examEndTime to set
	 */

	public String getExamShift() {
		return examShift;
	}

	public Date getExamEndTime() {
		return examEndTime;
	}

	public void setExamEndTime(Date examEndTime) {
		this.examEndTime = examEndTime;
	}

	/**
	 * @param examShift
	 *            the examShift to set
	 */
	public void setExamShift(String examShift) {
		this.examShift = examShift;
	}

	/**
	 * @return the timetablesList
	 */
	public List<ExamTable> getTimetablesList() {
		return timetablesList;
	}

	/**
	 * @param timetablesList
	 *            the timetablesList to set
	 */
	public void setTimetablesList(List<ExamTable> timetablesList) {
		this.timetablesList = timetablesList;
	}

}
