package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the sms_log database table.
 * 
 */
@Entity
@Table(name="sms_log")
@NamedQuery(name="SmsLog.findAll", query="SELECT s FROM SmsLog s")
public class SmsLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int sms_ID;

	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name="from_number")
	private String fromNumber;

	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

	@Column(name="to_number")
	private String toNumber;

	//bi-directional many-to-one association to StudentParent
	@ManyToOne
	@JoinColumn(name="hasstudent_parent_ID")
	private StudentParent studentParent;

	public SmsLog() {
	}

	public int getSms_ID() {
		return this.sms_ID;
	}

	public void setSms_ID(int sms_ID) {
		this.sms_ID = sms_ID;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFromNumber() {
		return this.fromNumber;
	}

	public void setFromNumber(String fromNumber) {
		this.fromNumber = fromNumber;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getToNumber() {
		return this.toNumber;
	}

	public void setToNumber(String toNumber) {
		this.toNumber = toNumber;
	}

	public StudentParent getStudentParent() {
		return this.studentParent;
	}

	public void setStudentParent(StudentParent studentParent) {
		this.studentParent = studentParent;
	}

}