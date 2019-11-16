package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="user")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int has_user_ID;

	@Column(name="last_login_time")
	private Timestamp lastLoginTime;

	private String password;

	private String status;

	private String username;
	
	private String text_password;
	
	
	public String getText_password() {
		return text_password;
	}

	public void setText_password(String text_password) {
		this.text_password = text_password;
	}

	public String getOTP() {
		return OTP;
	}

	public void setOTP(String oTP) {
		OTP = oTP;
	}

	private String OTP;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;

	public User() {
	}

	public int getHas_user_ID() {
		return this.has_user_ID;
	}

	public void setHas_user_ID(int has_user_ID) {
		this.has_user_ID = has_user_ID;
	}

	public Timestamp getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}