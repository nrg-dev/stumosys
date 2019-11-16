package com.stumosys.shared;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the student_details database table.
 * 
 */
@Entity
@Table(name="student_details")
@NamedQuery(name="StudentDetail.findAll", query="SELECT s FROM StudentDetail s")
public class StudentDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int has_student_ID;

	
	

	public Student getStudentid() {
		return studentid;
	}

	public void setStudentid(Student studentid) {
		this.studentid = studentid;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	private String address1_Street;

	private String address2_Street;
	
	@Temporal(TemporalType.DATE)
	private Date dob;

	@Column(name="email_id")
	private String emailId;

	@Column(name="father_income")
	private String fatherIncome;

	@Column(name="father_name")
	private String fatherName;

	@Column(name="father_occupation")
	private String fatherOccupation;

	@Column(name="first_name")
	private String firstName;

	private String gender;

	@Column(name="last_name")
	private String lastName;

	@Column(name="mother_name")
	private String motherName;

	@Column(name="mother_occupation")
	private String motherOccupation;

	@Column(name="phone_number")
	private String phoneNumber;
	
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

	private String permanentcode;
	
	private String presentcode;
	/*//bi-directional one-to-one association to Student
	@OneToOne
	@JoinColumn(name="student_ID")
	private Student student1;*/

	//bi-directional many-to-one association to Postcode
	@ManyToOne
	@JoinColumn(name="postcode1_ID")
	private Postcode postcode1;
	
	private String reason;
	
	
	public String getPermanentcode() {
		return permanentcode;
	}

	public void setPermanentcode(String permanentcode) {
		this.permanentcode = permanentcode;
	}

	public String getPresentcode() {
		return presentcode;
	}

	public void setPresentcode(String presentcode) {
		this.presentcode = presentcode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	//bi-directional many-to-one association to Postcode
	@ManyToOne
	@JoinColumn(name="postcode2_ID")
	private Postcode postcode2;

	/*//bi-directional one-to-one association to Student
	@OneToOne
	@JoinColumn(name="student_ID")
	private Student student2;*/
	
	public int getHas_student_ID() {
		return has_student_ID;
	}

	public void setHas_student_ID(int has_student_ID) {
		this.has_student_ID = has_student_ID;
	}

		//bi-directional many-to-one association to Student
		@ManyToOne
		@JoinColumn(name="student_ID")
		private Student studentid;
		
	private String religion,caste,classification,communityCertificateNo,issuedAuthority;
	
	@Temporal(TemporalType.DATE)
	private Date issueddate;
	
	private String bankAccNo,bankName,branchName,ifscCode,micrNo,branchCode,aadharCardNo,rationCardNo,employmentCardNo,emisNo,busPassNo;

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getCaste() {
		return caste;
	}

	public void setCaste(String caste) {
		this.caste = caste;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getCommunityCertificateNo() {
		return communityCertificateNo;
	}

	public void setCommunityCertificateNo(String communityCertificateNo) {
		this.communityCertificateNo = communityCertificateNo;
	}

	public String getIssuedAuthority() {
		return issuedAuthority;
	}

	public void setIssuedAuthority(String issuedAuthority) {
		this.issuedAuthority = issuedAuthority;
	}

	public Date getIssueddate() {
		return issueddate;
	}

	public void setIssueddate(Date issueddate) {
		this.issueddate = issueddate;
	}

	public String getBankAccNo() {
		return bankAccNo;
	}

	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getMicrNo() {
		return micrNo;
	}

	public void setMicrNo(String micrNo) {
		this.micrNo = micrNo;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getAadharCardNo() {
		return aadharCardNo;
	}

	public void setAadharCardNo(String aadharCardNo) {
		this.aadharCardNo = aadharCardNo;
	}

	public String getRationCardNo() {
		return rationCardNo;
	}

	public void setRationCardNo(String rationCardNo) {
		this.rationCardNo = rationCardNo;
	}

	public String getEmploymentCardNo() {
		return employmentCardNo;
	}

	public void setEmploymentCardNo(String employmentCardNo) {
		this.employmentCardNo = employmentCardNo;
	}

	public String getEmisNo() {
		return emisNo;
	}

	public void setEmisNo(String emisNo) {
		this.emisNo = emisNo;
	}

	public String getBusPassNo() {
		return busPassNo;
	}

	public void setBusPassNo(String busPassNo) {
		this.busPassNo = busPassNo;
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

	public StudentDetail() {
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

	

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFatherIncome() {
		return this.fatherIncome;
	}

	public void setFatherIncome(String fatherIncome) {
		this.fatherIncome = fatherIncome;
	}

	public String getFatherName() {
		return this.fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getFatherOccupation() {
		return this.fatherOccupation;
	}

	public void setFatherOccupation(String fatherOccupation) {
		this.fatherOccupation = fatherOccupation;
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

	public String getMotherOccupation() {
		return this.motherOccupation;
	}

	public void setMotherOccupation(String motherOccupation) {
		this.motherOccupation = motherOccupation;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/*public Student getStudent1() {
		return this.student1;
	}

	public void setStudent1(Student student1) {
		this.student1 = student1;
	}*/

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

	/*public Student getStudent2() {
		return this.student2;
	}

	public void setStudent2(Student student2) {
		this.student2 = student2;
	}*/
	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
}