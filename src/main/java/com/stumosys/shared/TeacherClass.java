package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the teacher_class database table.
 * 
 */
@Entity
@Table(name="teacher_class")
@NamedQuery(name="TeacherClass.findAll", query="SELECT t FROM TeacherClass t")
public class TeacherClass implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int hasteacher_ID;

	@Temporal(TemporalType.DATE)
	@Column(name="date_from")
	private Date dateFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="date_to")
	private Date dateTo;

	private String status;

	//bi-directional many-to-one association to Class
	@ManyToOne
	@JoinColumn(name="class_ID")
	private Class clazz;

	//bi-directional many-to-one association to Teacher
	@ManyToOne
	@JoinColumn(name="teacher_ID")
	private Teacher teacher;

	public TeacherClass() {
	}

	public int getHasteacher_ID() {
		return this.hasteacher_ID;
	}

	public void setHasteacher_ID(int hasteacher_ID) {
		this.hasteacher_ID = hasteacher_ID;
	}

	public Date getDateFrom() {
		return this.dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return this.dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

}