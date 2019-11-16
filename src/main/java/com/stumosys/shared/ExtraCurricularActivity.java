package com.stumosys.shared;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the indexs database table.
 * 
 */
@Entity
@Table(name="extra_curricular_activity")
@NamedQuery(name="ExtraCurricularActivity.findAll", query="SELECT i FROM ExtraCurricularActivity i")
public class ExtraCurricularActivity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int activity_id;

	private String activity;

	private String stime;
	
	private String etime;
	
	private String class_section;
	
	private String student_ID;
	
	private String status;
	
	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school;
	
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public int getActivity_id() {
		return activity_id;
	}


	public void setActivity_id(int activity_id) {
		this.activity_id = activity_id;
	}


	public String getActivity() {
		return activity;
	}


	public void setActivity(String activity) {
		this.activity = activity;
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


	public ExtraCurricularActivity() {
	}

	

}