package com.stumosys.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the leaverequest database table.
 * 
 */
@Entity
@Table(name="leaverequest")
@NamedQuery(name="Leaverequest.findAll", query="SELECT l FROM Leaverequest l")
public class Leaverequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int leave_requset_ID;

	@Column(name="approval_status")
	private String approvalStatus;

	@Temporal(TemporalType.DATE)
	@Column(name="leave_date")
	private Date leaveDate;

	@Column(name="leave_reason")
	private String leaveReason;
	
	@Column(name="class_name")
	private String className;

	private String status;

	private String student_ID;
	
	//bi-directional many-to-one association to School
	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school;

	//bi-directional many-to-one association to School
	@ManyToOne
	@JoinColumn(name="parent_ID")
	private Parent parent;

	public Leaverequest() {
	}

	public int getLeave_requset_ID() {
		return this.leave_requset_ID;
	}

	public void setLeave_requset_ID(int leave_requset_ID) {
		this.leave_requset_ID = leave_requset_ID;
	}	

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getApprovalStatus() {
		return this.approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Date getLeaveDate() {
		return this.leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getLeaveReason() {
		return this.leaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStudent_ID() {
		return this.student_ID;
	}

	public void setStudent_ID(String student_ID) {
		this.student_ID = student_ID;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

}