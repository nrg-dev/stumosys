package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the homework database table.
 * 
 */
@Entity
@Table(name="homework")
@NamedQuery(name="Homework.findAll", query="SELECT h FROM Homework h")
public class Homework implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int home_work_ID;

	@Column(name="class_name")
	private String className;

	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name="home_work")
	private String homeWork;

	//bi-directional many-to-one association to School
	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school;

	private String status;

	private String subject;

	public Homework() {
	}

	public int getHome_work_ID() {
		return this.home_work_ID;
	}

	public void setHome_work_ID(int home_work_ID) {
		this.home_work_ID = home_work_ID;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getHomeWork() {
		return this.homeWork;
	}

	public void setHomeWork(String homeWork) {
		this.homeWork = homeWork;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}