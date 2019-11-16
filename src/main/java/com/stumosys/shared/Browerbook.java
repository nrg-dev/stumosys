package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the browerbook database table.
 * 
 */
@Entity
@Table(name="browerbook")
@NamedQuery(name="Browerbook.findAll", query="SELECT b FROM Browerbook b")
public class Browerbook implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int borrowerID;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Temporal(TemporalType.DATE)
	@Column(name="due_date")
	private Date dueDate;

	@Temporal(TemporalType.DATE)
	@Column(name="return_date")
	private Date returnDate;

	@Column(name="return_time")
	private Timestamp returnTime;

	private String status;
	
	private String category;
	
	private String fineAmount;
	
	
	

	public String getFineAmount() {
		return fineAmount;
	}

	public void setFineAmount(String fineAmount) {
		this.fineAmount = fineAmount;
	}

	//bi-directional many-to-one association to Student
	@ManyToOne
	@JoinColumn(name="student_ID")
	private Student student;

	//bi-directional many-to-one association to School
	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school;

	//bi-directional many-to-one association to Library
	@ManyToOne
	@JoinColumn(name="library_ID")
	private Library library;

	public Browerbook() {
	}

	public int getBorrowerID() {
		return this.borrowerID;
	}

	public void setBorrowerID(int borrowerID) {
		this.borrowerID = borrowerID;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
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

	

	public Date getReturnDate() {
		return this.returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Timestamp getReturnTime() {
		return this.returnTime;
	}

	public void setReturnTime(Timestamp returnTime) {
		this.returnTime = returnTime;
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

	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
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

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	

}