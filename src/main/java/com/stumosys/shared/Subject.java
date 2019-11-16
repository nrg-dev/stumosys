package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the subject database table.
 * 
 */
@Entity
@Table(name="subject")
@NamedQuery(name="Subject.findAll", query="SELECT s FROM Subject s")
public class Subject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int subject_ID;

	private String status;

	@Column(name="subject_code")
	private String subjectCode;

	@Column(name="suject_name")
	private String sujectName;

	//bi-directional many-to-one association to ClassSubject
	@OneToMany(mappedBy="subject")
	private List<ClassSubject> classSubjects;

	//bi-directional many-to-one association to School
	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school;

	//bi-directional many-to-one association to TeacherSubject
	@OneToMany(mappedBy="subject")
	private List<TeacherSubject> teacherSubjects;
	

	//bi-directional many-to-one association to Reportcard
	@OneToMany(mappedBy="subject")
	private List<Reportcard> reportcards;
	
	public Subject() {
	}

	public int getSubject_ID() {
		return this.subject_ID;
	}

	public void setSubject_ID(int subject_ID) {
		this.subject_ID = subject_ID;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubjectCode() {
		return this.subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSujectName() {
		return this.sujectName;
	}

	public void setSujectName(String sujectName) {
		this.sujectName = sujectName;
	}

	public List<ClassSubject> getClassSubjects() {
		return this.classSubjects;
	}

	public void setClassSubjects(List<ClassSubject> classSubjects) {
		this.classSubjects = classSubjects;
	}

	public ClassSubject addClassSubject(ClassSubject classSubject) {
		getClassSubjects().add(classSubject);
		classSubject.setSubject(this);

		return classSubject;
	}

	public ClassSubject removeClassSubject(ClassSubject classSubject) {
		getClassSubjects().remove(classSubject);
		classSubject.setSubject(null);

		return classSubject;
	}

	public School getSchool() {
		return this.school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public List<TeacherSubject> getTeacherSubjects() {
		return this.teacherSubjects;
	}

	public void setTeacherSubjects(List<TeacherSubject> teacherSubjects) {
		this.teacherSubjects = teacherSubjects;
	}

	public TeacherSubject addTeacherSubject(TeacherSubject teacherSubject) {
		getTeacherSubjects().add(teacherSubject);
		teacherSubject.setSubject(this);

		return teacherSubject;
	}

	public TeacherSubject removeTeacherSubject(TeacherSubject teacherSubject) {
		getTeacherSubjects().remove(teacherSubject);
		teacherSubject.setSubject(null);

		return teacherSubject;
	}
	public List<Reportcard> getReportcards() {
		return this.reportcards;
	}

	public void setReportcards(List<Reportcard> reportcards) {
		this.reportcards = reportcards;
	}

	public Reportcard addReportcard(Reportcard reportcard) {
		getReportcards().add(reportcard);
		reportcard.setSubject(this);

		return reportcard;
	}

	public Reportcard removeReportcard(Reportcard reportcard) {
		getReportcards().remove(reportcard);
		reportcard.setSubject(null);

		return reportcard;
	}
}