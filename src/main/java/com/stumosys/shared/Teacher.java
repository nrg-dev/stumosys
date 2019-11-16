package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the teacher database table.
 * 
 */
@Entity
@Table(name="teacher")
@NamedQuery(name="Teacher.findAll", query="SELECT t FROM Teacher t")
public class Teacher implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int teacher_ID;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Column(name="roll_number")
	private String rollNumber;

	private String status;

	//bi-directional many-to-one association to ImagePath
	@OneToMany(mappedBy="teacher")
	private List<ImagePath> imagePaths;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;

	//bi-directional many-to-one association to School
	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school;

	//bi-directional many-to-one association to TeacherClass
	@OneToMany(mappedBy="teacher")
	private List<TeacherClass> teacherClasses;

	//bi-directional many-to-one association to TeacherDetail
	@OneToMany(mappedBy="teacher")
	private List<TeacherDetail> teacherDetails;

	//bi-directional many-to-one association to TeacherSubject
	@OneToMany(mappedBy="teacher")
	private List<TeacherSubject> teacherSubjects;
	
	//bi-directional many-to-one association to Reportcard
		@OneToMany(mappedBy="teacher")
		private List<Reportcard> reportcards;

	public Teacher() {
	}

	public int getTeacher_ID() {
		return this.teacher_ID;
	}

	public void setTeacher_ID(int teacher_ID) {
		this.teacher_ID = teacher_ID;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public String getRollNumber() {
		return this.rollNumber;
	}

	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ImagePath> getImagePaths() {
		return this.imagePaths;
	}

	public void setImagePaths(List<ImagePath> imagePaths) {
		this.imagePaths = imagePaths;
	}

	public ImagePath addImagePath(ImagePath imagePath) {
		getImagePaths().add(imagePath);
		imagePath.setTeacher(this);

		return imagePath;
	}

	public ImagePath removeImagePath(ImagePath imagePath) {
		getImagePaths().remove(imagePath);
		imagePath.setTeacher(null);

		return imagePath;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public School getSchool() {
		return this.school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public List<TeacherClass> getTeacherClasses() {
		return this.teacherClasses;
	}

	public void setTeacherClasses(List<TeacherClass> teacherClasses) {
		this.teacherClasses = teacherClasses;
	}

	public TeacherClass addTeacherClass(TeacherClass teacherClass) {
		getTeacherClasses().add(teacherClass);
		teacherClass.setTeacher(this);

		return teacherClass;
	}

	public TeacherClass removeTeacherClass(TeacherClass teacherClass) {
		getTeacherClasses().remove(teacherClass);
		teacherClass.setTeacher(null);

		return teacherClass;
	}

	public List<TeacherDetail> getTeacherDetails() {
		return this.teacherDetails;
	}

	public void setTeacherDetails(List<TeacherDetail> teacherDetails) {
		this.teacherDetails = teacherDetails;
	}

	public TeacherDetail addTeacherDetail(TeacherDetail teacherDetail) {
		getTeacherDetails().add(teacherDetail);
		teacherDetail.setTeacher(this);

		return teacherDetail;
	}

	public TeacherDetail removeTeacherDetail(TeacherDetail teacherDetail) {
		getTeacherDetails().remove(teacherDetail);
		teacherDetail.setTeacher(null);

		return teacherDetail;
	}

	public List<TeacherSubject> getTeacherSubjects() {
		return this.teacherSubjects;
	}

	public void setTeacherSubjects(List<TeacherSubject> teacherSubjects) {
		this.teacherSubjects = teacherSubjects;
	}

	public TeacherSubject addTeacherSubject(TeacherSubject teacherSubject) {
		getTeacherSubjects().add(teacherSubject);
		teacherSubject.setTeacher(this);

		return teacherSubject;
	}

	public TeacherSubject removeTeacherSubject(TeacherSubject teacherSubject) {
		getTeacherSubjects().remove(teacherSubject);
		teacherSubject.setTeacher(null);

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
		reportcard.setTeacher(this);

		return reportcard;
	}

	public Reportcard removeReportcard(Reportcard reportcard) {
		getReportcards().remove(reportcard);
		reportcard.setTeacher(null);

		return reportcard;
	}
}