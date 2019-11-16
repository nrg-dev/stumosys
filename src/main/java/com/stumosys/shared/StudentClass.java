package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the student_class database table.
 * 
 */
@Entity
@Table(name="student_class")
@NamedQuery(name="StudentClass.findAll", query="SELECT s FROM StudentClass s")
public class StudentClass implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int hasstudent_class_ID;

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

	//bi-directional many-to-one association to Student
	@ManyToOne
	@JoinColumn(name="student_ID")
	private Student student;

	public StudentClass() {
	}

	public int getHasstudent_class_ID() {
		return this.hasstudent_class_ID;
	}

	public void setHasstudent_class_ID(int hasstudent_class_ID) {
		this.hasstudent_class_ID = hasstudent_class_ID;
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

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}