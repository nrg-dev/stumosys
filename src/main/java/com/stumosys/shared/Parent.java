package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the parent database table.
 * 
 */
@Entity
@Table(name="parent")
@NamedQuery(name="Parent.findAll", query="SELECT p FROM Parent p")
public class Parent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int parent_ID;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Column(name="roll_number")
	private String rollNumber;

	private String status;

	//bi-directional many-to-one association to ImagePath
	@OneToMany(mappedBy="parent")
	private List<ImagePath> imagePaths;

	//bi-directional many-to-one association to School
	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;

	//bi-directional one-to-one association to ParentDetail
	@OneToOne(mappedBy="parent")
	private ParentDetail parentDetail;

	

	//bi-directional many-to-one association to StudentParent
	@OneToMany(mappedBy="parent")
	private List<StudentParent> studentParents;

	public Parent() {
	}

	public int getParent_ID() {
		return this.parent_ID;
	}

	public void setParent_ID(int parent_ID) {
		this.parent_ID = parent_ID;
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
		imagePath.setParent(this);

		return imagePath;
	}

	public ImagePath removeImagePath(ImagePath imagePath) {
		getImagePaths().remove(imagePath);
		imagePath.setParent(null);

		return imagePath;
	}

	public School getSchool() {
		return this.school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	

	/**
	 * @return the parentDetail
	 */
	public ParentDetail getParentDetail() {
		return parentDetail;
	}

	/**
	 * @param parentDetail the parentDetail to set
	 */
	public void setParentDetail(ParentDetail parentDetail) {
		this.parentDetail = parentDetail;
	}

	public List<StudentParent> getStudentParents() {
		return this.studentParents;
	}

	public void setStudentParents(List<StudentParent> studentParents) {
		this.studentParents = studentParents;
	}

	public StudentParent addStudentParent(StudentParent studentParent) {
		getStudentParents().add(studentParent);
		studentParent.setParent(this);

		return studentParent;
	}

	public StudentParent removeStudentParent(StudentParent studentParent) {
		getStudentParents().remove(studentParent);
		studentParent.setParent(null);

		return studentParent;
	}

}