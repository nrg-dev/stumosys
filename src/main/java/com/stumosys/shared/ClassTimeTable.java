package com.stumosys.shared;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the class_time_table database table.
 * 
 */
@Entity
@Table(name="class_time_table")
@NamedQuery(name="ClassTimeTable.findAll", query="SELECT c FROM ClassTimeTable c")
public class ClassTimeTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int class_time_table_ID;

	@Column(name="day")
	private String day;
	
	@Column(name="end_time")
	private String endTime;

	private String period;

	@Column(name="start_time")
	private String startTime;

	private String subject;

	@Column(name="subject_code")
	private String subjectCode;

	@Temporal(TemporalType.DATE)
	private Date date;
	
	//bi-directional many-to-one association to ClassTable
	@ManyToOne
	@JoinColumn(name="class_table_ID")
	private ClassTable classTable;
	
	//bi-directional many-to-one association to teacher
	@ManyToOne
	@JoinColumn(name="teacher_ID")
	private Teacher teacher;

	//bi-directional many-to-one association to School
	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school;
	
	public ClassTimeTable() {
	}

	public int getClass_time_table_ID() {
		return this.class_time_table_ID;
	}

	public void setClass_time_table_ID(int class_time_table_ID) {
		this.class_time_table_ID = class_time_table_ID;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPeriod() {
		return this.period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubjectCode() {
		return this.subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public ClassTable getClassTable() {
		return this.classTable;
	}

	public void setClassTable(ClassTable classTable) {
		this.classTable = classTable;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	
}