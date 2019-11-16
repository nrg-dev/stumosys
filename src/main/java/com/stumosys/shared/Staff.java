package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;


/**
 * The persistent class for the staff database table.
 * 
 */
@Entity
@Table(name="staff")
@NamedQuery(name="Staff.findAll", query="SELECT s FROM Staff s")
public class Staff implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int staff_ID;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Column(name="email_id")
	private String emailId;

	@Column(name="first_name")
	private String firstName;

	private String gender;

	@Column(name="last_name")
	private String lastName;

	@Column(name="phone_number")
	private String phoneNumber;

	@Column(name="roll_number")
	private String rollNumber;

	private String status;
	
	private String countryCode;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;

	//bi-directional many-to-one association to ImagePath
		@OneToMany(mappedBy="staff")
		private List<ImagePath> imagePaths;
		
		//bi-directional many-to-one association to School
		@ManyToOne
		@JoinColumn(name="school_ID")
		private School school;
		
		
	public Staff() {
	}

	public int getStaff_ID() {
		return this.staff_ID;
	}

	public void setStaff_ID(int staff_ID) {
		this.staff_ID = staff_ID;
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

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	public List<ImagePath> getImagePaths() {
		return this.imagePaths;
	}

	public void setImagePaths(List<ImagePath> imagePaths) {
		this.imagePaths = imagePaths;
	}

	public ImagePath addImagePath(ImagePath imagePath) {
		getImagePaths().add(imagePath);
		imagePath.setStaff(this);

		return imagePath;
	}

	public ImagePath removeImagePath(ImagePath imagePath) {
		getImagePaths().remove(imagePath);
		imagePath.setStaff(null);

		return imagePath;
	}
	
	public School getSchool() {
		return this.school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

}