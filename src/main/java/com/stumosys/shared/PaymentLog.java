package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the payment_log database table.
 * 
 */
@Entity
@Table(name="payment_log")
@NamedQuery(name="PaymentLog.findAll", query="SELECT p FROM PaymentLog p")
public class PaymentLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int payment_log_ID;

	@Column(name="payment_amount")
	private String paymentAmount;

	@Column(name="payment_date")
	private String paymentDate;

	@Column(name="payment_gateway")
	private String paymentGateway;

	@Column(name="payment_time")
	private String paymentTime;

	@Column(name="payment_type")
	private String paymentType;

	@Column(name="pending_amount")
	private String pendingAmount;

	private String status;

	//bi-directional many-to-one association to StudentParent
	@ManyToOne
	@JoinColumn(name="hasstudent_parent_ID")
	private StudentParent studentParent;

	public PaymentLog() {
	}

	public int getPayment_log_ID() {
		return this.payment_log_ID;
	}

	public void setPayment_log_ID(int payment_log_ID) {
		this.payment_log_ID = payment_log_ID;
	}

	public String getPaymentAmount() {
		return this.paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentGateway() {
		return this.paymentGateway;
	}

	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	public String getPaymentTime() {
		return this.paymentTime;
	}

	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPendingAmount() {
		return this.pendingAmount;
	}

	public void setPendingAmount(String pendingAmount) {
		this.pendingAmount = pendingAmount;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public StudentParent getStudentParent() {
		return this.studentParent;
	}

	public void setStudentParent(StudentParent studentParent) {
		this.studentParent = studentParent;
	}

}