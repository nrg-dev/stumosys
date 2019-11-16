package com.stumosys.shared;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the student_performance database table.
 * 
 */
@Entity
@Table(name="student_performance")
@NamedQuery(name="StudentPerformance.findAll", query="SELECT s FROM StudentPerformance s")
public class StudentPerformance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int performance_ID;

	@Column(name="appearance_status")
	private String appearanceStatus;

	@Column(name="attitude_status")
	private String attitudeStatus;

	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school;
	
	@ManyToOne
	@JoinColumn(name="student_ID")
	private Student student;
	
	@Column(name="status")
	private String status;
	
	@Column(name="appearance_other")
	private String appearanceOther;

	@Column(name="attitude_other")
	private String attitudeOther;
	
	private boolean attitudeOtherStatus;
	
	private boolean appearanceOtherStatus;
	
	@Temporal(TemporalType.DATE)
	private Date createDate;
	
	@Column(name="subject_code")
	private String subjectCode;

	@Column(name="subject_name")
	private String subjectName;
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public StudentPerformance() {
	}

	public int getPerformance_ID() {
		return this.performance_ID;
	}

	public void setPerformance_ID(int performance_ID) {
		this.performance_ID = performance_ID;
	}

	public String getAppearanceStatus() {
		return this.appearanceStatus;
	}

	public void setAppearanceStatus(String appearanceStatus) {
		this.appearanceStatus = appearanceStatus;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getAttitudeStatus() {
		return this.attitudeStatus;
	}

	public void setAttitudeStatus(String attitudeStatus) {
		this.attitudeStatus = attitudeStatus;
	}

	public String getAppearanceOther() {
		return appearanceOther;
	}

	public void setAppearanceOther(String appearanceOther) {
		this.appearanceOther = appearanceOther;
	}

	public String getAttitudeOther() {
		return attitudeOther;
	}

	public void setAttitudeOther(String attitudeOther) {
		this.attitudeOther = attitudeOther;
	}

	public boolean isAttitudeOtherStatus() {
		return attitudeOtherStatus;
	}

	public void setAttitudeOtherStatus(boolean attitudeOtherStatus) {
		this.attitudeOtherStatus = attitudeOtherStatus;
	}

	public boolean isAppearanceOtherStatus() {
		return appearanceOtherStatus;
	}

	public void setAppearanceOtherStatus(boolean appearanceOtherStatus) {
		this.appearanceOtherStatus = appearanceOtherStatus;
	}

	

	
}