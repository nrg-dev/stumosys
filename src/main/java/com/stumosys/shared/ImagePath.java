package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the image_path database table.
 * 
 */
@Entity
@Table(name="image_path")
@NamedQuery(name="ImagePath.findAll", query="SELECT i FROM ImagePath i")
public class ImagePath implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int image_ID;

	@Temporal(TemporalType.DATE)
	private Date date;

	private String name;

	private String path;

	@Column(name="roll_status")
	private String rollStatus;

	private String status;
	
	//bi-directional many-to-one association to Student
		@ManyToOne
		@JoinColumn(name="library_ID")
		private Library library;

	//bi-directional many-to-one association to Student
	@ManyToOne
	@JoinColumn(name="student_ID")
	private Student student;

	//bi-directional many-to-one association to Parent
	@ManyToOne
	@JoinColumn(name="parent_ID")
	private Parent parent;

	//bi-directional many-to-one association to Teacher
	@ManyToOne
	@JoinColumn(name="teacher_ID")
	private Teacher teacher;

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

		//bi-directional many-to-one association to Staff
		@ManyToOne
		@JoinColumn(name="staff_ID")
		private Staff staff;
		
		//bi-directional many-to-one association to staff
				@ManyToOne
				@JoinColumn(name="librarian_ID")
				private Staff librarianID;
				
		
	public Staff getLibrarianID() {
					return librarianID;
				}

				public void setLibrarianID(Staff librarianID) {
					this.librarianID = librarianID;
				}

	public ImagePath() {
	}

	public int getImage_ID() {
		return this.image_ID;
	}

	public void setImage_ID(int image_ID) {
		this.image_ID = image_ID;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRollStatus() {
		return this.rollStatus;
	}

	public void setRollStatus(String rollStatus) {
		this.rollStatus = rollStatus;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Student getStudent() {
		return this.student;
	}

	/**
	 * @return the library
	 */
	public Library getLibrary() {
		return library;
	}

	/**
	 * @param library the library to set
	 */
	public void setLibrary(Library library) {
		this.library = library;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Parent getParent() {
		return this.parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

}