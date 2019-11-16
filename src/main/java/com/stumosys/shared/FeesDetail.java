package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the fees_details database table.
 * 
 */
@Entity
@Table(name="fees_details")
@NamedQuery(name="FeesDetail.findAll", query="SELECT f FROM FeesDetail f")
public class FeesDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="payment_id")
	private int paymentId;

	private String approvalStatus;

	@Column(name="class_name")
	private String className;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="exam_fees")
	private String examFees;

	@Column(name="file_upload")
	private String fileUpload;

	@Column(name="term_status")
	private String termstatus;
	
	@ManyToOne
	@JoinColumn(name="school_id")
	private School schoolId;

	private String status;
	


	private String student_ID;

	@Column(name="total_fees")
	private String totalFees;

	@Column(name="transport_fees")
	private String transportFees;

	@Column(name="tuition_fees")
	private String tuitionFees;

	@Temporal(TemporalType.DATE)
	@Column(name="uploaded_date")
	private Date uploadedDate;
	
	private String title;
	
	private String amount;
	
	@Temporal(TemporalType.DATE)
	private Date duedate;
	
	private String description;
	
	private String paidAmount;
	
	private String balanceAmount;
	
	private String paymentStatus;
	
	private String extraFees1;
	
	private String extraFees2;
	
	private String year;
	
	private String month;
	
	private String invoiceNumber;
	
private String specialFees;
	
	private String ecaFees;
	
	private String labFees;
	
	private String addmissionFees;
	
	public String getSpecialFees() {
		return specialFees;
	}

	public void setSpecialFees(String specialFees) {
		this.specialFees = specialFees;
	}

	public String getEcaFees() {
		return ecaFees;
	}

	public void setEcaFees(String ecaFees) {
		this.ecaFees = ecaFees;
	}

	public String getLabFees() {
		return labFees;
	}

	public void setLabFees(String labFees) {
		this.labFees = labFees;
	}

	public String getAddmissionFees() {
		return addmissionFees;
	}

	public void setAddmissionFees(String addmissionFees) {
		this.addmissionFees = addmissionFees;
	}

	
	public String getTermstatus() {
		return termstatus;
	}

	public void setTermstatus(String termstatus) {
		this.termstatus = termstatus;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getExtraFees1() {
		return extraFees1;
	}

	public void setExtraFees1(String extraFees1) {
		this.extraFees1 = extraFees1;
	}

	public String getExtraFees2() {
		return extraFees2;
	}

	public void setExtraFees2(String extraFees2) {
		this.extraFees2 = extraFees2;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Date getDuedate() {
		return duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FeesDetail() {
	}

	public int getPaymentId() {
		return this.paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public String getApprovalStatus() {
		return this.approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getExamFees() {
		return this.examFees;
	}

	public void setExamFees(String examFees) {
		this.examFees = examFees;
	}

	public String getFileUpload() {
		return this.fileUpload;
	}

	public void setFileUpload(String fileUpload) {
		this.fileUpload = fileUpload;
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

	public String getTotalFees() {
		return this.totalFees;
	}

	public void setTotalFees(String totalFees) {
		this.totalFees = totalFees;
	}

	public String getTransportFees() {
		return this.transportFees;
	}

	public void setTransportFees(String transportFees) {
		this.transportFees = transportFees;
	}

	public String getTuitionFees() {
		return this.tuitionFees;
	}

	public void setTuitionFees(String tuitionFees) {
		this.tuitionFees = tuitionFees;
	}

	public Date getUploadedDate() {
		return this.uploadedDate;
	}

	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	public School getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(School schoolId) {
		this.schoolId = schoolId;
	}

}