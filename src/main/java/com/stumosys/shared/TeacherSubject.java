package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the teacher_subject database table.
 * 
 */
@Entity
@Table(name="teacher_subject")
@NamedQuery(name="TeacherSubject.findAll", query="SELECT t FROM TeacherSubject t")
public class TeacherSubject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int hasteacher_subject_ID;

	@Temporal(TemporalType.DATE)
	@Column(name="date_from")
	private Date dateFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="date_to")
	private Date dateTo;

	private String status;

	//bi-directional many-to-one association to Subject
	@ManyToOne
	@JoinColumn(name="subject_ID")
	private Subject subject;

	//bi-directional many-to-one association to School
	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school;

	//bi-directional many-to-one association to Teacher
	@ManyToOne
	@JoinColumn(name="teacher_ID")
	private Teacher teacher;

	public TeacherSubject() {
	}

	public int getHasteacher_subject_ID() {
		return this.hasteacher_subject_ID;
	}

	public void setHasteacher_subject_ID(int hasteacher_subject_ID) {
		this.hasteacher_subject_ID = hasteacher_subject_ID;
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

	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public School getSchool() {
		return this.school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

}