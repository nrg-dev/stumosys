package com.stumosys.shared;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the indexs database table.
 * 
 */
@Entity
@Table(name="student_attendance")
@NamedQuery(name="StudentAttendance.findAll", query="SELECT i FROM StudentAttendance i")
public class StudentAttendance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int student_attendance_id;

	private Date attendance_date;

	private String inTime;
	
	private String outTime;
	
	private String present_status;
	
	private String present_day;
	
	private String class_section;
	
	private String student_ID;
	
	private String status;
	
	
	public int getStudent_attendance_id() {
		return student_attendance_id;
	}


	public void setStudent_attendance_id(int student_attendance_id) {
		this.student_attendance_id = student_attendance_id;
	}


	public Date getAttendance_date() {
		return attendance_date;
	}


	public void setAttendance_date(Date attendance_date) {
		this.attendance_date = attendance_date;
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


	public String getPresent_status() {
		return present_status;
	}


	public void setPresent_status(String present_status) {
		this.present_status = present_status;
	}


	public String getPresent_day() {
		return present_day;
	}


	public void setPresent_day(String present_day) {
		this.present_day = present_day;
	}


	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school;
	
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	


	public String getClass_section() {
		return class_section;
	}


	public void setClass_section(String class_section) {
		this.class_section = class_section;
	}


	public String getStudent_ID() {
		return student_ID;
	}


	public void setStudent_ID(String student_ID) {
		this.student_ID = student_ID;
	}


	public School getSchool() {
		return school;
	}


	public void setSchool(School school) {
		this.school = school;
	}


	public StudentAttendance() {
	}

	

}