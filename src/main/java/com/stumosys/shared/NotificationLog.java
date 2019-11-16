package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the notification_log database table.
 * 
 */
@Entity
@Table(name="notification_log")
@NamedQuery(name="NotificationLog.findAll", query="SELECT n FROM NotificationLog n")
public class NotificationLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int notification_log_ID;

	@Temporal(TemporalType.DATE)
	private Date date;

	private String message;

	private String status;

	private String subject;

	private Timestamp time;

	//bi-directional many-to-one association to StudentParent
	@ManyToOne
	@JoinColumn(name="hasstudent_parent_ID")
	private StudentParent studentParent;

	public NotificationLog() {
	}

	public int getNotification_log_ID() {
		return this.notification_log_ID;
	}

	public void setNotification_log_ID(int notification_log_ID) {
		this.notification_log_ID = notification_log_ID;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public StudentParent getStudentParent() {
		return this.studentParent;
	}

	public void setStudentParent(StudentParent studentParent) {
		this.studentParent = studentParent;
	}

}