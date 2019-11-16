package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the class database table.
 * 
 */
@Entity
@Table(name="class")
@NamedQuery(name="Class.findAll", query="SELECT c FROM Class c")
public class Class implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@OneToMany(mappedBy="class_ID",fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	private int class_ID;
	//private List<ClassTable> class_ID = new ArrayList<ClassTable>();
	
	@Column(name="class_code")
	private String classCode;

	@Column(name="class_name")
	private String className;

	@Column(name="class_section")
	private String classSection;

	private String status;

	//bi-directional many-to-one association to School
	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school;

	//bi-directional many-to-one association to ClassSubject
	@OneToMany(mappedBy="clazz")
	private List<ClassSubject> classSubjects;

	//bi-directional many-to-one association to StudentClass
	@OneToMany(mappedBy="clazz")
	private List<StudentClass> studentClasses;

	//bi-directional many-to-one association to TeacherClass
	@OneToMany(mappedBy="clazz")
	private List<TeacherClass> teacherClasses;
	
	//bi-directional many-to-one association to Reportcard
	@OneToMany(mappedBy="clazz")
	private List<Reportcard> reportcards;

	public Class() {
	}

	public int getClass_ID() {
		return this.class_ID;
	}

	public void setClass_ID(int class_ID) {
		this.class_ID = class_ID;
	}

	public String getClassCode() {
		return this.classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassSection() {
		return this.classSection;
	}

	public void setClassSection(String classSection) {
		this.classSection = classSection;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public School getSchool() {
		return this.school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public List<ClassSubject> getClassSubjects() {
		return this.classSubjects;
	}

	public void setClassSubjects(List<ClassSubject> classSubjects) {
		this.classSubjects = classSubjects;
	}

	public ClassSubject addClassSubject(ClassSubject classSubject) {
		getClassSubjects().add(classSubject);
		classSubject.setClazz(this);

		return classSubject;
	}

	public ClassSubject removeClassSubject(ClassSubject classSubject) {
		getClassSubjects().remove(classSubject);
		classSubject.setClazz(null);

		return classSubject;
	}

	public List<StudentClass> getStudentClasses() {
		return this.studentClasses;
	}

	public void setStudentClasses(List<StudentClass> studentClasses) {
		this.studentClasses = studentClasses;
	}

	public StudentClass addStudentClass(StudentClass studentClass) {
		getStudentClasses().add(studentClass);
		studentClass.setClazz(this);

		return studentClass;
	}

	public StudentClass removeStudentClass(StudentClass studentClass) {
		getStudentClasses().remove(studentClass);
		studentClass.setClazz(null);

		return studentClass;
	}

	public List<TeacherClass> getTeacherClasses() {
		return this.teacherClasses;
	}

	public void setTeacherClasses(List<TeacherClass> teacherClasses) {
		this.teacherClasses = teacherClasses;
	}

	public TeacherClass addTeacherClass(TeacherClass teacherClass) {
		getTeacherClasses().add(teacherClass);
		teacherClass.setClazz(this);

		return teacherClass;
	}

	public TeacherClass removeTeacherClass(TeacherClass teacherClass) {
		getTeacherClasses().remove(teacherClass);
		teacherClass.setClazz(null);

		return teacherClass;
	}
	public List<Reportcard> getReportcards() {
		return this.reportcards;
	}

	public void setReportcards(List<Reportcard> reportcards) {
		this.reportcards = reportcards;
	}

	public Reportcard addReportcard(Reportcard reportcard) {
		getReportcards().add(reportcard);
		reportcard.setClazz(this);

		return reportcard;
	}

	public Reportcard removeReportcard(Reportcard reportcard) {
		getReportcards().remove(reportcard);
		reportcard.setClazz(null);

		return reportcard;
	}
}