package com.stumosys.domain;
import java.io.Serializable;

public class RegisteredLogin implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String otpid;
	public String username;
	public String country;
	public String phonenumber;
	public String emailid;
	public String otpnumber;
	public String status;
	public String schoolid;
	public String personrole;
	public String password;
	public String securePassword;

	public String getSecurePassword() {
		return securePassword;
	}
	public void setSecurePassword(String securePassword) {
		this.securePassword = securePassword;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPersonrole() {
		return personrole;
	}
	public void setPersonrole(String personrole) {
		this.personrole = personrole;
	}
	public String getSchoolid() {
		return schoolid;
	}
	public void setSchoolid(String schoolid) {
		this.schoolid = schoolid;
	}
	public String getOtpid() {
		return otpid;
	}
	public void setOtpid(String otpid) {
		this.otpid = otpid;
	}
	public String getCountry() {
		return country;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getOtpnumber() {
		return otpnumber;
	}
	public void setOtpnumber(String otpnumber) {
		this.otpnumber = otpnumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}