package com.stumosys.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.primefaces.model.UploadedFile;

import com.stumosys.shared.FeesDetail;

public class PaymentDatabean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5697000325544093046L;
	private String payClassSection;
	private String paymentStudID;
	private String paymentFees;
	private String examFees;
	private String tuitionFees;
	private String transportFees;
	private String totalFees;
	private String sNo;
	private String approvalStatus;
	private ArrayList<PaymentDatabean> stuClassList;
	private String stuCls1;
	private ArrayList<PaymentDatabean> rollnolist;
	private List<FeesDetail> paymentviewList;
	private boolean flag=false;
	private UploadedFile upFile;
	private boolean upflag=false;
	private boolean payflag=false;
	private String filePath;
	private boolean flag1=false;
	private String status;
	private String mailId;
	private String parentID;
	private String phoneno;
	private String schoolLogo;
	private String paymentStuName;
	private String title;
	private String amount;
	private Date duedate;
	private String description;
	private String options;
	private String payclass;
	private String school_Name;
	private String extraFee1;
	private String extraFee2;
	private String year;
	private String month;
	private String className;
	private String specialFees;
	private String ecaFees;
	private String labFees;
	private String admissionFees;

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
	public String getAdmissionFees() {
		return admissionFees;
	}
	public void setAdmissionFees(String admissionFees) {
		this.admissionFees = admissionFees;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
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
	public String getExtraFee1() {
		return extraFee1;
	}
	public void setExtraFee1(String extraFee1) {
		this.extraFee1 = extraFee1;
	}
	public String getExtraFee2() {
		return extraFee2;
	}
	public void setExtraFee2(String extraFee2) {
		this.extraFee2 = extraFee2;
	}
	public String getSchool_Name() {
		return school_Name;
	}
	public void setSchool_Name(String school_Name) {
		this.school_Name = school_Name;
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
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public String getPayclass() {
		return payclass;
	}
	public void setPayclass(String payclass) {
		this.payclass = payclass;
	}
	public String getPaymentStuName() {
		return paymentStuName;
	}
	public void setPaymentStuName(String paymentStuName) {
		this.paymentStuName = paymentStuName;
	}
	public String getSchoolLogo() {
		return schoolLogo;
	}

	public void setSchoolLogo(String schoolLogo) {
		this.schoolLogo = schoolLogo;
	}
	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public UploadedFile getUpFile() {
		return upFile;
	}

	public void setUpFile(UploadedFile upFile) {
		this.upFile = upFile;
	}

	public boolean isUpflag() {
		return upflag;
	}

	public void setUpflag(boolean upflag) {
		this.upflag = upflag;
	}

	public boolean isPayflag() {
		return payflag;
	}

	public void setPayflag(boolean payflag) {
		this.payflag = payflag;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public boolean isFlag1() {
		return flag1;
	}

	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}

	public List<FeesDetail> getPaymentviewList() {
		return paymentviewList;
	}

	public void setPaymentviewList(List<FeesDetail> paymentviewList) {
		this.paymentviewList = paymentviewList;
	}

	public String getsNo() {
		return sNo;
	}

	public void setsNo(String sNo) {
		this.sNo = sNo;
	}

	public String getExamFees() {
		return examFees;
	}

	public void setExamFees(String examFees) {
		this.examFees = examFees;
	}

	public String getTuitionFees() {
		return tuitionFees;
	}

	public void setTuitionFees(String tuitionFees) {
		this.tuitionFees = tuitionFees;
	}

	public String getTransportFees() {
		return transportFees;
	}

	public void setTransportFees(String transportFees) {
		this.transportFees = transportFees;
	}

	public String getTotalFees() {
		return totalFees;
	}

	public void setTotalFees(String totalFees) {
		this.totalFees = totalFees;
	}

	public ArrayList<PaymentDatabean> getRollnolist() {
		return rollnolist;
	}

	public void setRollnolist(ArrayList<PaymentDatabean> rollnolist) {
		this.rollnolist = rollnolist;
	}

	public String getStuCls1() {
		return stuCls1;
	}

	public void setStuCls1(String stuCls1) {
		this.stuCls1 = stuCls1;
	}

	public ArrayList<PaymentDatabean> getStuClassList() {
		return stuClassList;
	}

	public void setStuClassList(ArrayList<PaymentDatabean> stuClassList) {
		this.stuClassList = stuClassList;
	}

	public String getPaymentFees() {
		return paymentFees;
	}

	public void setPaymentFees(String paymentFees) {
		this.paymentFees = paymentFees;
	}

	public String getPaymentStudID() {
		return paymentStudID;
	}

	public void setPaymentStudID(String paymentStudID) {
		this.paymentStudID = paymentStudID;
	}

	public String getPayClassSection() {
		return payClassSection;
	}

	public void setPayClassSection(String payClassSection) {
		this.payClassSection = payClassSection;
	}
	
	
}
