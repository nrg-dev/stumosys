package com.stumosys.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.stumosys.shared.Reportcard;
@XmlRootElement
public class ReportCardDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1244307270397939832L;

	private String studMarkClass;
	private String examMarkTitle;
	private String markSubTitle;
	private String viewMarkStuName;
	private String rollNo;
	private String grade;
	private String mark;
	private String name;
	private String resultStatus;
	private String sNo;
	private String totalMark;
	private String mailid;
	private boolean upflag = false;
	private boolean downflag = false;
	private String searchname;
	private String barstudentid;
	private String barclassid;
	private List<Reportcard> reportList = null;
	private List<String> subjectList = null;
	private String reason;
	private String returnStatus;
	private String phoneno;
	private String schoolName;
	private String schoolLogo;
	private String months;
	private int schoolID;
	private int examID;
	private Date examDate;
	
	private String studentName;
	private String studentRollNumber;
	private String studentCountry;
	private String studentParentName;
	private String studentParentRelation;
	private String subjectName;
	private String subjectCode;
	private String teacherRollNumber;
	private String className;
	
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentRollNumber() {
		return studentRollNumber;
	}

	public void setStudentRollNumber(String studentRollNumber) {
		this.studentRollNumber = studentRollNumber;
	}

	public String getStudentCountry() {
		return studentCountry;
	}

	public void setStudentCountry(String studentCountry) {
		this.studentCountry = studentCountry;
	}

	public String getStudentParentName() {
		return studentParentName;
	}

	public void setStudentParentName(String studentParentName) {
		this.studentParentName = studentParentName;
	}

	public String getStudentParentRelation() {
		return studentParentRelation;
	}

	public void setStudentParentRelation(String studentParentRelation) {
		this.studentParentRelation = studentParentRelation;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getTeacherRollNumber() {
		return teacherRollNumber;
	}

	public void setTeacherRollNumber(String teacherRollNumber) {
		this.teacherRollNumber = teacherRollNumber;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public int getExamID() {
		return examID;
	}

	public void setExamID(int examID) {
		this.examID = examID;
	}

	public String getMonths() {
		return months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	public int getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(int schoolID) {
		this.schoolID = schoolID;
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

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	/**
	 * @return the totalMark
	 */
	public String getTotalMark() {
		return totalMark;
	}

	/**
	 * @param totalMark
	 *            the totalMark to set
	 */
	public void setTotalMark(String totalMark) {
		this.totalMark = totalMark;
	}

	/**
	 * @return the rollNo
	 */
	public String getRollNo() {
		return rollNo;
	}

	/**
	 * @param rollNo
	 *            the rollNo to set
	 */
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}

	/**
	 * @param grade
	 *            the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	/**
	 * @return the mark
	 */
	public String getMark() {
		return mark;
	}

	/**
	 * @param mark
	 *            the mark to set
	 */
	public void setMark(String mark) {
		this.mark = mark;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the resultStatus
	 */
	public String getResultStatus() {
		return resultStatus;
	}

	/**
	 * @param resultStatus
	 *            the resultStatus to set
	 */
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	/**
	 * @return the studMarkClass
	 */
	public String getStudMarkClass() {
		return studMarkClass;
	}

	/**
	 * @param studMarkClass
	 *            the studMarkClass to set
	 */
	public void setStudMarkClass(String studMarkClass) {
		this.studMarkClass = studMarkClass;
	}

	/**
	 * @return the examMarkTitle
	 */
	public String getExamMarkTitle() {
		return examMarkTitle;
	}

	/**
	 * @param examMarkTitle
	 *            the examMarkTitle to set
	 */
	public void setExamMarkTitle(String examMarkTitle) {
		this.examMarkTitle = examMarkTitle;
	}

	/**
	 * @return the markSubTitle
	 */
	public String getMarkSubTitle() {
		return markSubTitle;
	}

	/**
	 * @param markSubTitle
	 *            the markSubTitle to set
	 */
	public void setMarkSubTitle(String markSubTitle) {
		this.markSubTitle = markSubTitle;
	}

	/**
	 * @return the viewMarkStuName
	 */
	public String getViewMarkStuName() {
		return viewMarkStuName;
	}

	/**
	 * @param viewMarkStuName
	 *            the viewMarkStuName to set
	 */
	public void setViewMarkStuName(String viewMarkStuName) {
		this.viewMarkStuName = viewMarkStuName;
	}

	/**
	 * @return the sNo
	 */
	public String getsNo() {
		return sNo;
	}

	/**
	 * @param sNo
	 *            the sNo to set
	 */
	public void setsNo(String sNo) {
		this.sNo = sNo;
	}

	/**
	 * @return the downflag
	 */
	public boolean isDownflag() {
		return downflag;
	}

	/**
	 * @param downflag
	 *            the downflag to set
	 */
	public void setDownflag(boolean downflag) {
		this.downflag = downflag;
	}

	/**
	 * @return the upflag
	 */
	public boolean isUpflag() {
		return upflag;
	}

	/**
	 * @param upflag
	 *            the upflag to set
	 */
	public void setUpflag(boolean upflag) {
		this.upflag = upflag;
	}

	/**
	 * @return the searchname
	 */
	public String getSearchname() {
		return searchname;
	}

	/**
	 * @param searchname
	 *            the searchname to set
	 */
	public void setSearchname(String searchname) {
		this.searchname = searchname;
	}

	/**
	 * @return the barstudentid
	 */
	public String getBarstudentid() {
		return barstudentid;
	}

	/**
	 * @param barstudentid
	 *            the barstudentid to set
	 */
	public void setBarstudentid(String barstudentid) {
		this.barstudentid = barstudentid;
	}

	/**
	 * @return the barclassid
	 */
	public String getBarclassid() {
		return barclassid;
	}

	/**
	 * @param barclassid
	 *            the barclassid to set
	 */
	public void setBarclassid(String barclassid) {
		this.barclassid = barclassid;
	}

	/**
	 * @return the reportList
	 */
	public List<Reportcard> getReportList() {
		return reportList;
	}

	/**
	 * @param reportList
	 *            the reportList to set
	 */
	public void setReportList(List<Reportcard> reportList) {
		this.reportList = reportList;
	}

	/**
	 * @return the subjectList
	 */
	public List<String> getSubjectList() {
		return subjectList;
	}

	/**
	 * @param subjectList
	 *            the subjectList to set
	 */
	public void setSubjectList(List<String> subjectList) {
		this.subjectList = subjectList;
	}

}
