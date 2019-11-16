package com.stumosys.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.stumosys.shared.ParentDetail;
import com.stumosys.shared.Staff;
import com.stumosys.shared.StudentDetail;
import com.stumosys.shared.TeacherDetail;
@XmlRootElement
public class NoticeBoardDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -510557497937710698L;

	private String noticeSubject;
	private String noticeSubjects;
	private String noticeFollower;
	private String noticeFollowers;
	private String noticeID;
	private Date noticeDate;
	private Date fromdate;
	private Date todate;
	private List<ParentDetail> parentList = null;
	private List<Staff> staffList = null;
	private List<TeacherDetail> teacherList = null;
	private List<StudentDetail> studentList = null;
	private List<String> emailList = null;
	private String returnStatus;
	private List<String> phonenos;
	private String schoolName;
	private int noticeboardID;
	private String schoolLogo;
	private String noticeClass;
	private int schoolID;
	private List<String> studentnameList=null;
	
	public List<String> getStudentnameList() {
		return studentnameList;
	}

	public void setStudentnameList(List<String> studentnameList) {
		this.studentnameList = studentnameList;
	}
	
	public int getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(int schoolID) {
		this.schoolID = schoolID;
	}

	public String getNoticeClass() {
		return noticeClass;
	}

	public void setNoticeClass(String noticeClass) {
		this.noticeClass = noticeClass;
	}

	public String getSchoolLogo() {
		return schoolLogo;
	}

	public void setSchoolLogo(String schoolLogo) {
		this.schoolLogo = schoolLogo;
	}
	public int getNoticeboardID() {
		return noticeboardID;
	}

	public void setNoticeboardID(int noticeboardID) {
		this.noticeboardID = noticeboardID;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public List<String> getPhonenos() {
		return phonenos;
	}

	public void setPhonenos(List<String> phonenos) {
		this.phonenos = phonenos;
	}

	public String getNoticeFollowers() {
		return noticeFollowers;
	}

	public void setNoticeFollowers(String noticeFollowers) {
		this.noticeFollowers = noticeFollowers;
	}

	public String getNoticeSubjects() {
		return noticeSubjects;
	}

	public void setNoticeSubjects(String noticeSubjects) {
		this.noticeSubjects = noticeSubjects;
	}

	/**
	 * @return the parentList
	 */
	public List<ParentDetail> getParentList() {
		return parentList;
	}

	/**
	 * @param parentList
	 *            the parentList to set
	 */
	public void setParentList(List<ParentDetail> parentList) {
		this.parentList = parentList;
	}

	/**
	 * @return the teacherList
	 */
	public List<TeacherDetail> getTeacherList() {
		return teacherList;
	}

	/**
	 * @param teacherList
	 *            the teacherList to set
	 */
	public void setTeacherList(List<TeacherDetail> teacherList) {
		this.teacherList = teacherList;
	}

	/**
	 * @return the studentList
	 */
	public List<StudentDetail> getStudentList() {
		return studentList;
	}

	/**
	 * @param studentList
	 *            the studentList to set
	 */
	public void setStudentList(List<StudentDetail> studentList) {
		this.studentList = studentList;
	}

	/**
	 * @return the fromdate
	 */
	public Date getFromdate() {
		return fromdate;
	}

	/**
	 * @param fromdate
	 *            the fromdate to set
	 */
	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	/**
	 * @return the todate
	 */
	public Date getTodate() {
		return todate;
	}

	/**
	 * @param todate
	 *            the todate to set
	 */
	public void setTodate(Date todate) {
		this.todate = todate;
	}

	/**
	 * 
	 * @return the noticeSubject
	 */
	public String getNoticeSubject() {
		return noticeSubject;
	}

	/**
	 * @param noticeSubject
	 *            the noticeSubject to set
	 */
	public void setNoticeSubject(String noticeSubject) {
		this.noticeSubject = noticeSubject;
	}

	/**
	 * @return the noticeFollower
	 */
	public String getNoticeFollower() {
		return noticeFollower;
	}

	/**
	 * @param noticeFollower
	 *            the noticeFollower to set
	 */
	public void setNoticeFollower(String noticeFollower) {
		this.noticeFollower = noticeFollower;
	}

	/**
	 * @return the noticeID
	 */
	public String getNoticeID() {
		return noticeID;
	}

	/**
	 * @param noticeID
	 *            the noticeID to set
	 */
	public void setNoticeID(String noticeID) {
		this.noticeID = noticeID;
	}

	/**
	 * @return the noticeDate
	 */
	public Date getNoticeDate() {
		return noticeDate;
	}

	/**
	 * @param noticeDate
	 *            the noticeDate to set
	 */
	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}

	/**
	 * @return the emailList
	 */
	public List<String> getEmailList() {
		return emailList;
	}

	/**
	 * @param emailList
	 *            the emailList to set
	 */
	public void setEmailList(List<String> emailList) {
		this.emailList = emailList;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	public List<Staff> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<Staff> staffList) {
		this.staffList = staffList;
	}

}
