package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the reportcard database table.
 * 
 */
@Entity
@Table(name="reportcard")
@NamedQuery(name="Reportcard.findAll", query="SELECT r FROM Reportcard r")
public class Reportcard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int reportcard_ID;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Column(name="exam_title")
	private String examTitle;

	private String grade;

	private String mark;

	@Column(name="result_status")
	private String resultStatus;

	private String status;

	//bi-directional many-to-one association to Student
	@ManyToOne
	@JoinColumn(name="student_ID")
	private Student student;

	//bi-directional many-to-one association to School
	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school;

	//bi-directional many-to-one association to Class
	@ManyToOne
	@JoinColumn(name="class_ID")
	private Class clazz;

	//bi-directional many-to-one association to Teacher
	@ManyToOne
	@JoinColumn(name="teacher_ID")
	private Teacher teacher;

	//bi-directional many-to-one association to Subject
	@ManyToOne
	@JoinColumn(name="subject_ID")
	private Subject subject;
	
	//bi-directional many-to-one association to Class
	@ManyToOne
	@JoinColumn(name="exam_table_ID")
	private ExamTable examTable;

	public Reportcard() {
	}

	public ExamTable getExamTable() {
		return examTable;
	}

	public void setExamTable(ExamTable examTable) {
		this.examTable = examTable;
	}

	public int getReportcard_ID() {
		return this.reportcard_ID;
	}

	public void setReportcard_ID(int reportcard_ID) {
		this.reportcard_ID = reportcard_ID;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public String getExamTitle() {
		return this.examTitle;
	}

	public void setExamTitle(String examTitle) {
		this.examTitle = examTitle;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getResultStatus() {
		return this.resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public School getSchool() {
		return this.school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Class getClazz() {
		return this.clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}