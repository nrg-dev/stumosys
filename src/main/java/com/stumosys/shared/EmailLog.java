package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the email_log database table.
 * 
 */
@Entity
@Table(name="email_log")
@NamedQuery(name="EmailLog.findAll", query="SELECT e FROM EmailLog e")
public class EmailLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int email_log_ID;

	private String date;

	@Column(name="from_id")
	private String fromId;

	private String message;

	private String status;

	private String subject;

	private String time;

	@Column(name="to_id")
	private String toId;

	//bi-directional many-to-one association to StudentParent
	@ManyToOne
	@JoinColumn(name="hasstudent_parent_ID")
	private StudentParent studentParent;

	public EmailLog() {
	}

	public int getEmail_log_ID() {
		return this.email_log_ID;
	}

	public void setEmail_log_ID(int email_log_ID) {
		this.email_log_ID = email_log_ID;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFromId() {
		return this.fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getToId() {
		return this.toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public StudentParent getStudentParent() {
		return this.studentParent;
	}

	public void setStudentParent(StudentParent studentParent) {
		this.studentParent = studentParent;
	}

}