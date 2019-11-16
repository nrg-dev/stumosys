package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the loginotp database table.
 * 
 */
@Entity
@Table(name="loginotp")
@NamedQuery(name="Loginotp.findAll", query="SELECT l FROM Loginotp l")
public class Loginotp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int otp_ID;

	private String country;

	private String emailID;

	private String otpNumber;

	private String phoneNumber;

	private String status;

	private String userName;



	public Loginotp() {
	}

	public int getOtp_ID() {
		return this.otp_ID;
	}

	public void setOtp_ID(int otp_ID) {
		this.otp_ID = otp_ID;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmailID() {
		return this.emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getOtpNumber() {
		return this.otpNumber;
	}

	public void setOtpNumber(String otpNumber) {
		this.otpNumber = otpNumber;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}