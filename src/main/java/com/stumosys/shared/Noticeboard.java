package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the noticeboard database table.
 * 
 */
@Entity
@Table(name="noticeboard")
@NamedQuery(name="Noticeboard.findAll", query="SELECT n FROM Noticeboard n")
public class Noticeboard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int noticeBoard_ID;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="created_time")
	private Timestamp createdTime;

	private String message;

	@Column(name="notice_follower")
	private String noticeFollower;
	
	@Column(name="notice_class")
	private String noticeClass;

	private String status;

	private String subject;
	
	@Temporal(TemporalType.DATE)
	@Column(name="from_date")
	private Date fromDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="to_date")
	private Date toDate;

	//bi-directional many-to-one association to School
	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school;

	public Noticeboard() {
	}

	public int getNoticeBoard_ID() {
		return this.noticeBoard_ID;
	}

	public void setNoticeBoard_ID(int noticeBoard_ID) {
		this.noticeBoard_ID = noticeBoard_ID;
	}
	
	

	public String getNoticeClass() {
		return noticeClass;
	}

	public void setNoticeClass(String noticeClass) {
		this.noticeClass = noticeClass;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNoticeFollower() {
		return this.noticeFollower;
	}

	public void setNoticeFollower(String noticeFollower) {
		this.noticeFollower = noticeFollower;
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

	public School getSchool() {
		return this.school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

}