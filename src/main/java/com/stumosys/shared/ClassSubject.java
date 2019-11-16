package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the class_subject database table.
 * 
 */
@Entity
@Table(name="class_subject")
@NamedQuery(name="ClassSubject.findAll", query="SELECT c FROM ClassSubject c")
public class ClassSubject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int hasClass_ID;

	private String status;

	//bi-directional many-to-one association to Class
	@ManyToOne
	@JoinColumn(name="class_ID")
	private Class clazz;

	//bi-directional many-to-one association to Subject
	@ManyToOne
	@JoinColumn(name="subject_ID")
	private Subject subject;

	public ClassSubject() {
	}

	public int getHasClass_ID() {
		return this.hasClass_ID;
	}

	public void setHasClass_ID(int hasClass_ID) {
		this.hasClass_ID = hasClass_ID;
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

	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}