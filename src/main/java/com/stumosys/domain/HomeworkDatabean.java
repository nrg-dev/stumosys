package com.stumosys.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class HomeworkDatabean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9082232785385811934L;
	private String classname;
	private String subject;
	private String homework;
	private List<String> mails = null;
	private Date date;
	public List<HomeworkDatabean> worklist = null;
	public int sno;
	private String returnStatus;
	private List<String> phones=null;
	private String schoolName;
	private int schoolID;
	private List<String> classnameList=null;
	public List<String> getClassnameList() {
		return classnameList;
	}

	public void setClassnameList(List<String> classnameList) {
		this.classnameList = classnameList;
	}

	public int getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(int schoolID) {
		this.schoolID = schoolID;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
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
	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public List<HomeworkDatabean> getWorklist() {
		return worklist;
	}

	public void setWorklist(List<HomeworkDatabean> worklist) {
		this.worklist = worklist;
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

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getHomework() {
		return homework;
	}

	public void setHomework(String homework) {
		this.homework = homework;
	}
}
