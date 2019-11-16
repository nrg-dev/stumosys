package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the backoffice_login database table.
 * 
 */
@Entity
@Table(name="backoffice_login")
@NamedQuery(name="BackofficeLogin.findAll", query="SELECT b FROM BackofficeLogin b")
public class BackofficeLogin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="backoffice_id")
	private int backofficeId;

	private String alternativeemail;

	private String contactpersonname;

	private String country;

	private String landlinenumber;

	private String mobilenumber;

	@Lob
	@Column(name="school_logo")
	private byte[] schoolLogo;

	private String schooladdress;

	private String schoolcontactnumber;

	private String schoolemail;

	private String schoolname;

	private String state;
	
	private String status;
	
	private String logopath;

	public BackofficeLogin() {
	}

	public int getBackofficeId() {
		return this.backofficeId;
	}

	public void setBackofficeId(int backofficeId) {
		this.backofficeId = backofficeId;
	}

	public String getAlternativeemail() {
		return this.alternativeemail;
	}

	public void setAlternativeemail(String alternativeemail) {
		this.alternativeemail = alternativeemail;
	}

	public String getContactpersonname() {
		return this.contactpersonname;
	}

	public void setContactpersonname(String contactpersonname) {
		this.contactpersonname = contactpersonname;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLandlinenumber() {
		return this.landlinenumber;
	}

	public void setLandlinenumber(String landlinenumber) {
		this.landlinenumber = landlinenumber;
	}

	public String getMobilenumber() {
		return this.mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public byte[] getSchoolLogo() {
		return this.schoolLogo;
	}

	public void setSchoolLogo(byte[] schoolLogo) {
		this.schoolLogo = schoolLogo;
	}

	public String getSchooladdress() {
		return this.schooladdress;
	}

	public void setSchooladdress(String schooladdress) {
		this.schooladdress = schooladdress;
	}

	public String getSchoolcontactnumber() {
		return this.schoolcontactnumber;
	}

	public void setSchoolcontactnumber(String schoolcontactnumber) {
		this.schoolcontactnumber = schoolcontactnumber;
	}

	public String getSchoolemail() {
		return this.schoolemail;
	}

	public void setSchoolemail(String schoolemail) {
		this.schoolemail = schoolemail;
	}

	public String getSchoolname() {
		return this.schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLogopath() {
		return logopath;
	}

	public void setLogopath(String logopath) {
		this.logopath = logopath;
	}

	
}