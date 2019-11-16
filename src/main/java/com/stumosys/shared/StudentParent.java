package com.stumosys.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the student_parent database table.
 * 
 */
@Entity
@Table(name="student_parent")
@NamedQuery(name="StudentParent.findAll", query="SELECT s FROM StudentParent s")
public class StudentParent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int hasstudent_parent_ID;

	//bi-directional many-to-one association to EmailLog
	@OneToMany(mappedBy="studentParent")
	private List<EmailLog> emailLogs;

	//bi-directional many-to-one association to NotificationLog
	@OneToMany(mappedBy="studentParent")
	private List<NotificationLog> notificationLogs;

	//bi-directional many-to-one association to PaymentLog
	@OneToMany(mappedBy="studentParent")
	private List<PaymentLog> paymentLogs;

	//bi-directional many-to-one association to SmsLog
	@OneToMany(mappedBy="studentParent")
	private List<SmsLog> smsLogs;

	//bi-directional many-to-one association to Parent
	@ManyToOne
	@JoinColumn(name="parent_ID")
	private Parent parent;

	//bi-directional many-to-one association to Student
	@ManyToOne
	@JoinColumn(name="student_ID")
	private Student student;
	
	private String status;


	public StudentParent() {
	}

	public int getHasstudent_parent_ID() {
		return this.hasstudent_parent_ID;
	}

	public void setHasstudent_parent_ID(int hasstudent_parent_ID) {
		this.hasstudent_parent_ID = hasstudent_parent_ID;
	}

	public List<EmailLog> getEmailLogs() {
		return this.emailLogs;
	}

	public void setEmailLogs(List<EmailLog> emailLogs) {
		this.emailLogs = emailLogs;
	}

	public EmailLog addEmailLog(EmailLog emailLog) {
		getEmailLogs().add(emailLog);
		emailLog.setStudentParent(this);

		return emailLog;
	}

	public EmailLog removeEmailLog(EmailLog emailLog) {
		getEmailLogs().remove(emailLog);
		emailLog.setStudentParent(null);

		return emailLog;
	}

	public List<NotificationLog> getNotificationLogs() {
		return this.notificationLogs;
	}

	public void setNotificationLogs(List<NotificationLog> notificationLogs) {
		this.notificationLogs = notificationLogs;
	}

	public NotificationLog addNotificationLog(NotificationLog notificationLog) {
		getNotificationLogs().add(notificationLog);
		notificationLog.setStudentParent(this);

		return notificationLog;
	}

	public NotificationLog removeNotificationLog(NotificationLog notificationLog) {
		getNotificationLogs().remove(notificationLog);
		notificationLog.setStudentParent(null);

		return notificationLog;
	}

	public List<PaymentLog> getPaymentLogs() {
		return this.paymentLogs;
	}

	public void setPaymentLogs(List<PaymentLog> paymentLogs) {
		this.paymentLogs = paymentLogs;
	}

	public PaymentLog addPaymentLog(PaymentLog paymentLog) {
		getPaymentLogs().add(paymentLog);
		paymentLog.setStudentParent(this);

		return paymentLog;
	}

	public PaymentLog removePaymentLog(PaymentLog paymentLog) {
		getPaymentLogs().remove(paymentLog);
		paymentLog.setStudentParent(null);

		return paymentLog;
	}

	public List<SmsLog> getSmsLogs() {
		return this.smsLogs;
	}

	public void setSmsLogs(List<SmsLog> smsLogs) {
		this.smsLogs = smsLogs;
	}

	public SmsLog addSmsLog(SmsLog smsLog) {
		getSmsLogs().add(smsLog);
		smsLog.setStudentParent(this);

		return smsLog;
	}

	public SmsLog removeSmsLog(SmsLog smsLog) {
		getSmsLogs().remove(smsLog);
		smsLog.setStudentParent(null);

		return smsLog;
	}

	public Parent getParent() {
		return this.parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}