package com.stumosys.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the attendanceclass database table.
 * 
 */
@Entity
@Table(name="attendanceclass")
@NamedQuery(name="Attendanceclass.findAll", query="SELECT a FROM Attendanceclass a")
public class Attendanceclass implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int att_class_ID;

	private String percentage;

	private String status;

	private String student_ID;

	@Column(name="student_name")
	private String studentName;

	@Column(name="month")
	private String month;		
	
	@Column(name="year")
	private String year;	
	
	@Column(name="period")
	private String period;
	
	private Timestamp time;

	//bi-directional many-to-one association to Attendance
	//@ManyToOne
	@ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name="attendance_ID")
	private Attendance attendance;

	//bi-directional many-to-one association to teacher
	@ManyToOne
	@JoinColumn(name="teacher_ID")
	private Teacher teacher;
	
	//bi-directional many-to-one association to ClassTable
	@ManyToOne
	@JoinColumn(name="class_time_table_ID")
	private ClassTimeTable classTimeTable;
	
	

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

	public Attendanceclass() {
	}

	public int getAtt_class_ID() {
		return this.att_class_ID;
	}

	public void setAtt_class_ID(int att_class_ID) {
		this.att_class_ID = att_class_ID;
	}

	public String getPercentage() {
		return this.percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStudent_ID() {
		return this.student_ID;
	}

	public void setStudent_ID(String student_ID) {
		this.student_ID = student_ID;
	}

	public String getStudentName() {
		return this.studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Attendance getAttendance() {
		return this.attendance;
	}

	public void setAttendance(Attendance attendance) {
		this.attendance = attendance;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public ClassTimeTable getClassTimeTable() {
		return classTimeTable;
	}

	public void setClassTimeTable(ClassTimeTable classTimeTable) {
		this.classTimeTable = classTimeTable;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

}