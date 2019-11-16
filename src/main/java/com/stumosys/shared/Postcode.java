package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the postcode database table.
 * 
 */
@Entity
@Table(name="postcode")
@NamedQuery(name="Postcode.findAll", query="SELECT p FROM Postcode p")
public class Postcode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int postcode_ID;

	private String area;

	@Column(name="post_office")
	private String postOffice;

	private String postcode;

	//bi-directional many-to-one association to State
	@ManyToOne
	@JoinColumn(name="state_code")
	private State state;

	//bi-directional many-to-one association to School
	@OneToMany(mappedBy="postcode")
	private List<School> schools;

	//bi-directional many-to-one association to StudentDetail
	@OneToMany(mappedBy="postcode1")
	private List<StudentDetail> studentDetails1;

	//bi-directional many-to-one association to StudentDetail
	@OneToMany(mappedBy="postcode2")
	private List<StudentDetail> studentDetails2;

	//bi-directional many-to-one association to TeacherDetail
	@OneToMany(mappedBy="postcode1")
	private List<TeacherDetail> teacherDetails1;

	//bi-directional many-to-one association to TeacherDetail
	@OneToMany(mappedBy="postcode2")
	private List<TeacherDetail> teacherDetails2;

	public Postcode() {
	}

	public int getPostcode_ID() {
		return this.postcode_ID;
	}

	public void setPostcode_ID(int postcode_ID) {
		this.postcode_ID = postcode_ID;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPostOffice() {
		return this.postOffice;
	}

	public void setPostOffice(String postOffice) {
		this.postOffice = postOffice;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<School> getSchools() {
		return this.schools;
	}

	public void setSchools(List<School> schools) {
		this.schools = schools;
	}

	public School addSchool(School school) {
		getSchools().add(school);
		school.setPostcode(this);

		return school;
	}

	public School removeSchool(School school) {
		getSchools().remove(school);
		school.setPostcode(null);

		return school;
	}

	public List<StudentDetail> getStudentDetails1() {
		return this.studentDetails1;
	}

	public void setStudentDetails1(List<StudentDetail> studentDetails1) {
		this.studentDetails1 = studentDetails1;
	}

	public StudentDetail addStudentDetails1(StudentDetail studentDetails1) {
		getStudentDetails1().add(studentDetails1);
		studentDetails1.setPostcode1(this);

		return studentDetails1;
	}

	public StudentDetail removeStudentDetails1(StudentDetail studentDetails1) {
		getStudentDetails1().remove(studentDetails1);
		studentDetails1.setPostcode1(null);

		return studentDetails1;
	}

	public List<StudentDetail> getStudentDetails2() {
		return this.studentDetails2;
	}

	public void setStudentDetails2(List<StudentDetail> studentDetails2) {
		this.studentDetails2 = studentDetails2;
	}

	public StudentDetail addStudentDetails2(StudentDetail studentDetails2) {
		getStudentDetails2().add(studentDetails2);
		studentDetails2.setPostcode2(this);

		return studentDetails2;
	}

	public StudentDetail removeStudentDetails2(StudentDetail studentDetails2) {
		getStudentDetails2().remove(studentDetails2);
		studentDetails2.setPostcode2(null);

		return studentDetails2;
	}

	public List<TeacherDetail> getTeacherDetails1() {
		return this.teacherDetails1;
	}

	public void setTeacherDetails1(List<TeacherDetail> teacherDetails1) {
		this.teacherDetails1 = teacherDetails1;
	}

	public TeacherDetail addTeacherDetails1(TeacherDetail teacherDetails1) {
		getTeacherDetails1().add(teacherDetails1);
		teacherDetails1.setPostcode1(this);

		return teacherDetails1;
	}

	public TeacherDetail removeTeacherDetails1(TeacherDetail teacherDetails1) {
		getTeacherDetails1().remove(teacherDetails1);
		teacherDetails1.setPostcode1(null);

		return teacherDetails1;
	}

	public List<TeacherDetail> getTeacherDetails2() {
		return this.teacherDetails2;
	}

	public void setTeacherDetails2(List<TeacherDetail> teacherDetails2) {
		this.teacherDetails2 = teacherDetails2;
	}

	public TeacherDetail addTeacherDetails2(TeacherDetail teacherDetails2) {
		getTeacherDetails2().add(teacherDetails2);
		teacherDetails2.setPostcode2(this);

		return teacherDetails2;
	}

	public TeacherDetail removeTeacherDetails2(TeacherDetail teacherDetails2) {
		getTeacherDetails2().remove(teacherDetails2);
		teacherDetails2.setPostcode2(null);

		return teacherDetails2;
	}

}