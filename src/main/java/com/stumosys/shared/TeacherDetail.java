package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the teacher_details database table.
 * 
 */
@Entity
@Table(name="teacher_details")
@NamedQuery(name="TeacherDetail.findAll", query="SELECT t FROM TeacherDetail t")
public class TeacherDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int hasteacherdet_ID;

	private String address1_Street;

	private String address2_Street;

	@Temporal(TemporalType.DATE)
	private Date dob;

	@Column(name="email_id")
	private String emailId;

	private String faculties;

	@Column(name="father_name")
	private String fatherName;

	@Column(name="first_name")
	private String firstName;

	private String gender;

	@Column(name="last_name")
	private String lastName;

	@Column(name="mother_name")
	private String motherName;

	private String percentage;

	@Column(name="phone_number")
	private String phoneNumber;

	private String post;

	private String qualification;

	@Column(name="working_hour")
	private String workingHour;

	@Column(name="year_of_passing")
	private String yearOfPassing;

	//bi-directional many-to-one association to Postcode
	@ManyToOne
	@JoinColumn(name="postcode2_ID")
	private Postcode postcode1;

	//bi-directional many-to-one association to Postcode
	@ManyToOne
	@JoinColumn(name="postcode1_ID")
	private Postcode postcode2;

	//bi-directional many-to-one association to Teacher
	@ManyToOne
	@JoinColumn(name="teacher_ID")
	private Teacher teacher;

	@Column(name="present_city")
	private String presentCity;

	@Column(name="present_country")
	private String presentCountry;

	@Column(name="present_postal")
	private String presentPostal;

	@Column(name="present_state")
	private String presentState;
	
	@Column(name="phone_number1")
	private String phoneNumber1;
	
	@Column(name="permanent_city")
	private String permanentCity;

	@Column(name="permanent_country")
	private String permanentCountry;

	@Column(name="permanent_postal")
	private String permanentPostal;

	@Column(name="permanent_state")
	private String permanentState;
	
	@Column(name="permanent_code")
	private String permanentCode;
	
	@Column(name="present_code")
	private String presentCode;
	
	private String management;
	
	public String getManagement() {
		return management;
	}

	public void setManagement(String management) {
		this.management = management;
	}

	public String getPresentCity() {
		return presentCity;
	}

	public void setPresentCity(String presentCity) {
		this.presentCity = presentCity;
	}

	public String getPresentCountry() {
		return presentCountry;
	}

	public void setPresentCountry(String presentCountry) {
		this.presentCountry = presentCountry;
	}

	public String getPresentPostal() {
		return presentPostal;
	}

	public void setPresentPostal(String presentPostal) {
		this.presentPostal = presentPostal;
	}

	public String getPresentState() {
		return presentState;
	}

	public void setPresentState(String presentState) {
		this.presentState = presentState;
	}

	public String getPhoneNumber1() {
		return phoneNumber1;
	}

	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}

	public String getPermanentCity() {
		return permanentCity;
	}

	public void setPermanentCity(String permanentCity) {
		this.permanentCity = permanentCity;
	}

	public String getPermanentCountry() {
		return permanentCountry;
	}

	public void setPermanentCountry(String permanentCountry) {
		this.permanentCountry = permanentCountry;
	}

	public String getPermanentPostal() {
		return permanentPostal;
	}

	public void setPermanentPostal(String permanentPostal) {
		this.permanentPostal = permanentPostal;
	}

	public String getPermanentState() {
		return permanentState;
	}

	public void setPermanentState(String permanentState) {
		this.permanentState = permanentState;
	}

	public String getPermanentCode() {
		return permanentCode;
	}

	public void setPermanentCode(String permanentCode) {
		this.permanentCode = permanentCode;
	}

	public String getPresentCode() {
		return presentCode;
	}

	public void setPresentCode(String presentCode) {
		this.presentCode = presentCode;
	}

	public TeacherDetail() {
	}

	public int getHasteacherdet_ID() {
		return this.hasteacherdet_ID;
	}

	public void setHasteacherdet_ID(int hasteacherdet_ID) {
		this.hasteacherdet_ID = hasteacherdet_ID;
	}

	public String getAddress1_Street() {
		return this.address1_Street;
	}

	public void setAddress1_Street(String address1_Street) {
		this.address1_Street = address1_Street;
	}

	public String getAddress2_Street() {
		return this.address2_Street;
	}

	public void setAddress2_Street(String address2_Street) {
		this.address2_Street = address2_Street;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFaculties() {
		return this.faculties;
	}

	public void setFaculties(String faculties) {
		this.faculties = faculties;
	}

	public String getFatherName() {
		return this.fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
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

	public String getMotherName() {
		return this.motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getPercentage() {
		return this.percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getQualification() {
		return this.qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getWorkingHour() {
		return this.workingHour;
	}

	public void setWorkingHour(String workingHour) {
		this.workingHour = workingHour;
	}

	public String getYearOfPassing() {
		return this.yearOfPassing;
	}

	public void setYearOfPassing(String yearOfPassing) {
		this.yearOfPassing = yearOfPassing;
	}

	public Postcode getPostcode1() {
		return this.postcode1;
	}

	public void setPostcode1(Postcode postcode1) {
		this.postcode1 = postcode1;
	}

	public Postcode getPostcode2() {
		return this.postcode2;
	}

	public void setPostcode2(Postcode postcode2) {
		this.postcode2 = postcode2;
	}

	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

}