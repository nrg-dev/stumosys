package com.stumosys.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import javax.faces.model.SelectItem;
import javax.xml.bind.annotation.XmlRootElement;

import org.primefaces.model.UploadedFile;

import com.stumosys.shared.ImagePath;
import com.stumosys.shared.Person;
import com.stumosys.shared.StudentDetail;
@XmlRootElement
public class StudentDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4215988704198004637L;

	private String stuUser;
	private String stuFirstName;
	private String stuLastName;
	private String stuFatherName;
	private String stuMotherName;
	private Date stuDob;
	private String stuGender;
	private String stuPresentAddress;
	private String stuPermanentAddress;
	private String stuPhoneNo;
	private String stuPhoneNo1;
	private String stuEmail;
	private String stuPassword;
	private String stuConfirmPwd;
	private String stuFatherOccu;
	private String stuFatherIncome;
	private String stuMotherOccu;
	private String stuCls;
	private String stuCls1;
	private String stuSession;
	private String stuStudentId;
	private String stuRollNo;
	private String stuStudentPhoto;
	private UploadedFile stuFile;
	private String stuPresentCountry;
	private String stuPermanentCountry;
	private String stuPresentpostal;
	private String stuPermanentpostal;
	private String stuPresentCity;
	private String stuPermanentCity;
	private String stuPermanentState;
	private String stuPermanentZip;
	private String stuPresentState;
	private String reason;
	private String returnStatus;
	private List<StudentDetail> studentlist=null;
	private String schoolLogo;
	private String[] menus;
	private String code;
	private String code1;
	private String personID;
	private String stuActivity;
	private String stuActivity1;
	private String stime;
	private String etime;
	private int school_id;
	private List<StudentDataBean> activityList=null;
	private List<String> actList=null;
	private int act_pid;
	private String addActivity;
	private boolean actFlag=false;
	private Date bdate;
	private String stuApp;
	private String stuAtt;
	private List<StudentDataBean> studentbehaveList=null;
	private int performance_pid;
	private String currentmonths;
	private List<StudentDataBean> feesList=null;
	private String examfees;
	private String tuitionfees;
	private String transfees;
	private String approvestatus;
	private String totalAmount;
	private String totalFees;
	private String paidAmount;
	private String paidAmount1;
	private String balanceAmount;
	private String paynow;
	private String paymentStatus;
	private boolean payFlag=false;
	private String fees;
	private String amount;
	private String sno;
	private Date todayDate;
	private List<StudentDataBean> feesregList=null;
	private boolean payeditFlag=false; 
	private boolean viewregFlag=false;
	private String religion,caste,classification,communityCertificateNo,issuedAuthority;
	private Date issuedDate;
	private String bankAccNo,bankName,branchName,ifscCode,micrNo,branchCode,aadharCardNo,rationCardNo,employmentCardNo,emisNo,busPassNo;
	private String year;
	private String month;
	private String extraFee1;
	private String extraFee2;
	private String invoiceNumber;
	private List<String> timeList=null;
	private String schoolName;
	private String term;
	
	
	
	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public List<String> getTimeList() {
		return timeList;
	}

	public void setTimeList(List<String> timeList) {
		this.timeList = timeList;
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

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getCaste() {
		return caste;
	}

	public void setCaste(String caste) {
		this.caste = caste;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getCommunityCertificateNo() {
		return communityCertificateNo;
	}

	public void setCommunityCertificateNo(String communityCertificateNo) {
		this.communityCertificateNo = communityCertificateNo;
	}

	public String getIssuedAuthority() {
		return issuedAuthority;
	}

	public void setIssuedAuthority(String issuedAuthority) {
		this.issuedAuthority = issuedAuthority;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getBankAccNo() {
		return bankAccNo;
	}

	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getMicrNo() {
		return micrNo;
	}

	public void setMicrNo(String micrNo) {
		this.micrNo = micrNo;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getAadharCardNo() {
		return aadharCardNo;
	}

	public void setAadharCardNo(String aadharCardNo) {
		this.aadharCardNo = aadharCardNo;
	}

	public String getRationCardNo() {
		return rationCardNo;
	}

	public void setRationCardNo(String rationCardNo) {
		this.rationCardNo = rationCardNo;
	}

	public String getEmploymentCardNo() {
		return employmentCardNo;
	}

	public void setEmploymentCardNo(String employmentCardNo) {
		this.employmentCardNo = employmentCardNo;
	}

	public String getEmisNo() {
		return emisNo;
	}

	public void setEmisNo(String emisNo) {
		this.emisNo = emisNo;
	}

	public String getBusPassNo() {
		return busPassNo;
	}

	public void setBusPassNo(String busPassNo) {
		this.busPassNo = busPassNo;
	}
	
	public boolean isViewregFlag() {
		return viewregFlag;
	}

	public void setViewregFlag(boolean viewregFlag) {
		this.viewregFlag = viewregFlag;
	} 
	
	 public boolean isPayeditFlag() {
		return payeditFlag;
	}
	public void setPayeditFlag(boolean payeditFlag) {
		this.payeditFlag = payeditFlag;
	}
	 
	 public boolean isPayFlag() {
	  return payFlag;
	 }
	 public void setPayFlag(boolean payFlag) {
	  this.payFlag = payFlag;
	 }
	
	public String getPaidAmount1() {
		return paidAmount1;
	}
	public void setPaidAmount1(String paidAmount1) {
		this.paidAmount1 = paidAmount1;
	}
	public String getTotalFees() {
		return totalFees;
	}
	public void setTotalFees(String totalFees) {
		this.totalFees = totalFees;
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
	public String getPaynow() {
		return paynow;
	}
	public void setPaynow(String paynow) {
		this.paynow = paynow;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getExamfees() {
		return examfees;
	}
	public void setExamfees(String examfees) {
		this.examfees = examfees;
	}
	public String getTuitionfees() {
		return tuitionfees;
	}
	public void setTuitionfees(String tuitionfees) {
		this.tuitionfees = tuitionfees;
	}
	public String getTransfees() {
		return transfees;
	}
	public void setTransfees(String transfees) {
		this.transfees = transfees;
	}
	public String getApprovestatus() {
		return approvestatus;
	}
	public void setApprovestatus(String approvestatus) {
		this.approvestatus = approvestatus;
	}
	public List<StudentDataBean> getFeesList() {
		return feesList;
	}
	public void setFeesList(List<StudentDataBean> feesList) {
		this.feesList = feesList;
	}
	public String getCurrentmonths() {
		return currentmonths;
	}
	public void setCurrentmonths(String currentmonths) {
		this.currentmonths = currentmonths;
	}
	public int getPerformance_pid() {
		return performance_pid;
	}
	public void setPerformance_pid(int performance_pid) {
		this.performance_pid = performance_pid;
	}
	public List<StudentDataBean> getStudentbehaveList() {
		return studentbehaveList;
	}
	public void setStudentbehaveList(List<StudentDataBean> studentbehaveList) {
		this.studentbehaveList = studentbehaveList;
	}
	public String getStuApp() {
		return stuApp;
	}
	public void setStuApp(String stuApp) {
		this.stuApp = stuApp;
	}
	public String getStuAtt() {
		return stuAtt;
	}
	public void setStuAtt(String stuAtt) {
		this.stuAtt = stuAtt;
	}
	public Date getBdate() {
		return bdate;
	}
	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}
	public String getStuActivity1() {
		return stuActivity1;
	}
	public void setStuActivity1(String stuActivity1) {
		this.stuActivity1 = stuActivity1;
	}
	public boolean isActFlag() {
		return actFlag;
	}
	public void setActFlag(boolean actFlag) {
		this.actFlag = actFlag;
	}
	public List<String> getActList() {
		return actList;
	}
	public void setActList(List<String> actList) {
		this.actList = actList;
	}
	public String getAddActivity() {
		return addActivity;
	}
	public void setAddActivity(String addActivity) {
		this.addActivity = addActivity;
	}
	public int getAct_pid() {
		return act_pid;
	}
	public void setAct_pid(int act_pid) {
		this.act_pid = act_pid;
	}
	public List<StudentDataBean> getActivityList() {
		return activityList;
	}
	public void setActivityList(List<StudentDataBean> activityList) {
		this.activityList = activityList;
	}
	public int getSchool_id() {
		return school_id;
	}
	public void setSchool_id(int school_id) {
		this.school_id = school_id;
	}
	public String getStuActivity() {
		return stuActivity;
	}
	public void setStuActivity(String stuActivity) {
		this.stuActivity = stuActivity;
	}
	
	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getPersonID() {
		return personID;
	}
	public void setPersonID(String personID) {
		this.personID = personID;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode1() {
		return code1;
	}
	public void setCode1(String code1) {
		this.code1 = code1;
	}
	public String getStuPhoneNo1() {
		return stuPhoneNo1;
	}
	public void setStuPhoneNo1(String stuPhoneNo1) {
		this.stuPhoneNo1 = stuPhoneNo1;
	}
	public String getStuPresentpostal() {
		return stuPresentpostal;
	}
	public void setStuPresentpostal(String stuPresentpostal) {
		this.stuPresentpostal = stuPresentpostal;
	}
	public String getStuPermanentpostal() {
		return stuPermanentpostal;
	}
	public void setStuPermanentpostal(String stuPermanentpostal) {
		this.stuPermanentpostal = stuPermanentpostal;
	}
	public String getStuPresentCity() {
		return stuPresentCity;
	}
	public void setStuPresentCity(String stuPresentCity) {
		this.stuPresentCity = stuPresentCity;
	}
	public String getStuPermanentCity() {
		return stuPermanentCity;
	}
	public void setStuPermanentCity(String stuPermanentCity) {
		this.stuPermanentCity = stuPermanentCity;
	}
	public String getStuPresentCountry() {
		return stuPresentCountry;
	}
	public void setStuPresentCountry(String stuPresentCountry) {
		this.stuPresentCountry = stuPresentCountry;
	}
	public String getStuPermanentCountry() {
		return stuPermanentCountry;
	}
	public void setStuPermanentCountry(String stuPermanentCountry) {
		this.stuPermanentCountry = stuPermanentCountry;
	}
	public String[] getMenus() {
		return menus;
	}
	public void setMenus(String[] menus) {
		this.menus = menus;
	}

	public String getSchoolLogo() {
		return schoolLogo;
	}

	public void setSchoolLogo(String schoolLogo) {
		this.schoolLogo = schoolLogo;
	}
	public List<StudentDetail> getStudentlist() {
		return studentlist;
	}

	public void setStudentlist(List<StudentDetail> studentlist) {
		this.studentlist = studentlist;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStuCls1() {
		return stuCls1;
	}

	public void setStuCls1(String stuCls1) {
		this.stuCls1 = stuCls1;
	}

	private String stuPresentZip;
	private String stuZipList;
	private String stuZipList1;
	private ArrayList<StudentDataBean> stuPickList;
	private List<Person> stuRolNo = null;
	private String stufilePath;
	private String stuSecurePasword;
	private String stuUsername;
	private String path;
	private String names;
	private String className;
	private String imagePath1;
	private Date imageDate;
	private String testroll;
	private String teaclssection;
	private ArrayList<StudentDataBean> teaClassList;

	public ArrayList<StudentDataBean> getTeaClassList() {
		return teaClassList;
	}

	public void setTeaClassList(ArrayList<StudentDataBean> teaClassList) {
		this.teaClassList = teaClassList;
	}

	public String getTeaclssection() {
		return teaclssection;
	}

	public void setTeaclssection(String teaclssection) {
		this.teaclssection = teaclssection;
	}

	public String getTestroll() {
		return testroll;
	}

	public void setTestroll(String testroll) {
		this.testroll = testroll;
	}

	public Date getImageDate() {
		return imageDate;
	}

	public void setImageDate(Date imageDate) {
		this.imageDate = imageDate;
	}

	private List<ImagePath> imageList;

	public List<ImagePath> getImageList() {
		return imageList;
	}

	public void setImageList(List<ImagePath> imagelist2) {
		this.imageList = imagelist2;
	}

	public String getImagePath1() {
		return imagePath1;
	}

	public void setImagePath1(String imagePath1) {
		this.imagePath1 = imagePath1;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	private String presentAdd;

	public String getPresentAdd() {
		return presentAdd;
	}

	public void setPresentAdd(String presentAdd) {
		this.presentAdd = presentAdd;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getStufilePath() {
		return stufilePath;
	}

	public void setStufilePath(String stufilePath) {
		this.stufilePath = stufilePath;
	}

	public String getStuSecurePasword() {
		return stuSecurePasword;
	}

	public void setStuSecurePasword(String stuSecurePasword) {
		this.stuSecurePasword = stuSecurePasword;
	}

	public String getStuUsername() {
		return stuUsername;
	}

	public void setStuUsername(String stuUsername) {
		this.stuUsername = stuUsername;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getPathDate() {
		return pathDate;
	}

	public void setPathDate(Date pathDate) {
		this.pathDate = pathDate;
	}

	private Date pathDate;

	public List<Person> getStuRolNo() {
		return stuRolNo;
	}

	public void setStuRolNo(List<Person> stuRolNo) {
		this.stuRolNo = stuRolNo;
	}

	public ArrayList<StudentDataBean> getStuPickList() {
		return stuPickList;
	}

	public void setStuPickList(ArrayList<StudentDataBean> picklist2) {
		this.stuPickList = picklist2;
	}

	public String getStuZipList1() {
		return stuZipList1;
	}

	public void setStuZipList1(String stuZipList1) {
		this.stuZipList1 = stuZipList1;
	}

	public String getStuZipList() {
		return stuZipList;
	}

	public void setStuZipList(String stuZipList) {
		this.stuZipList = stuZipList;
	}

	List<StudentDataBean> studlist = null;

	public List<StudentDataBean> getStudlist() {
		return studlist;
	}

	public void setStudlist(List<StudentDataBean> studlist) {
		this.studlist = studlist;
	}

	public String getStuPermanentState() {
		return stuPermanentState;
	}

	public void setStuPermanentState(String stuPermanentState) {
		this.stuPermanentState = stuPermanentState;
	}

	public String getStuPermanentZip() {
		return stuPermanentZip;
	}

	public void setStuPermanentZip(String stuPermanentZip) {
		this.stuPermanentZip = stuPermanentZip;
	}

	public String getStuPresentState() {
		return stuPresentState;
	}

	public void setStuPresentState(String stuPresentState) {
		this.stuPresentState = stuPresentState;
	}

	public String getStuPresentZip() {
		return stuPresentZip;
	}

	public void setStuPresentZip(String stuPresentZip) {
		this.stuPresentZip = stuPresentZip;
	}

	/**
	 * @return the stuUser
	 */
	public String getStuUser() {
		return stuUser;
	}

	/**
	 * @param stuUser
	 *            the stuUser to set
	 */
	public void setStuUser(String stuUser) {
		this.stuUser = stuUser;
	}

	/**
	 * @return the stuFirstName
	 */
	public String getStuFirstName() {
		return stuFirstName;
	}

	/**
	 * @param stuFirstName
	 *            the stuFirstName to set
	 */
	public void setStuFirstName(String stuFirstName) {
		this.stuFirstName = stuFirstName;
	}

	/**
	 * @return the stuLastName
	 */
	public String getStuLastName() {
		return stuLastName;
	}

	/**
	 * @param stuLastName
	 *            the stuLastName to set
	 */
	public void setStuLastName(String stuLastName) {
		this.stuLastName = stuLastName;
	}

	/**
	 * @return the stuFatherName
	 */
	public String getStuFatherName() {
		return stuFatherName;
	}

	/**
	 * @param stuFatherName
	 *            the stuFatherName to set
	 */
	public void setStuFatherName(String stuFatherName) {
		this.stuFatherName = stuFatherName;
	}

	/**
	 * @return the stuMotherName
	 */
	public String getStuMotherName() {
		return stuMotherName;
	}

	/**
	 * @param stuMotherName
	 *            the stuMotherName to set
	 */
	public void setStuMotherName(String stuMotherName) {
		this.stuMotherName = stuMotherName;
	}

	/**
	 * @return the stuDob
	 */
	public Date getStuDob() {
		return stuDob;
	}

	/**
	 * @param stuDob
	 *            the stuDob to set
	 */
	public void setStuDob(Date stuDob) {
		this.stuDob = stuDob;
	}

	/**
	 * @return the stuGender
	 */
	public String getStuGender() {
		return stuGender;
	}

	/**
	 * @param stuGender
	 *            the stuGender to set
	 */
	public void setStuGender(String stuGender) {
		this.stuGender = stuGender;
	}

	/**
	 * @return the stuPresentAddress
	 */
	public String getStuPresentAddress() {
		return stuPresentAddress;
	}

	/**
	 * @param stuPresentAddress
	 *            the stuPresentAddress to set
	 */
	public void setStuPresentAddress(String stuPresentAddress) {
		this.stuPresentAddress = stuPresentAddress;
	}

	/**
	 * @return the stuPermanentAddress
	 */
	public String getStuPermanentAddress() {
		return stuPermanentAddress;
	}

	/**
	 * @param stuPermanentAddress
	 *            the stuPermanentAddress to set
	 */
	public void setStuPermanentAddress(String stuPermanentAddress) {
		this.stuPermanentAddress = stuPermanentAddress;
	}

	/**
	 * @return the stuPhoneNo
	 */
	public String getStuPhoneNo() {
		return stuPhoneNo;
	}

	/**
	 * @param stuPhoneNo
	 *            the stuPhoneNo to set
	 */
	public void setStuPhoneNo(String stuPhoneNo) {
		this.stuPhoneNo = stuPhoneNo;
	}

	/**
	 * @return the stuEmail
	 */
	public String getStuEmail() {
		return stuEmail;
	}

	/**
	 * @param stuEmail
	 *            the stuEmail to set
	 */
	public void setStuEmail(String stuEmail) {
		this.stuEmail = stuEmail;
	}

	/**
	 * @return the stuPassword
	 */
	public String getStuPassword() {
		return stuPassword;
	}

	/**
	 * @param stuPassword
	 *            the stuPassword to set
	 */
	public void setStuPassword(String stuPassword) {
		this.stuPassword = stuPassword;
	}

	/**
	 * @return the stuConfirmPwd
	 */
	public String getStuConfirmPwd() {
		return stuConfirmPwd;
	}

	/**
	 * @param stuConfirmPwd
	 *            the stuConfirmPwd to set
	 */
	public void setStuConfirmPwd(String stuConfirmPwd) {
		this.stuConfirmPwd = stuConfirmPwd;
	}

	/**
	 * @return the stuFatherOccu
	 */
	public String getStuFatherOccu() {
		return stuFatherOccu;
	}

	/**
	 * @param stuFatherOccu
	 *            the stuFatherOccu to set
	 */
	public void setStuFatherOccu(String stuFatherOccu) {
		this.stuFatherOccu = stuFatherOccu;
	}

	/**
	 * @return the stuFatherIncome
	 */
	public String getStuFatherIncome() {
		return stuFatherIncome;
	}

	/**
	 * @param stuFatherIncome
	 *            the stuFatherIncome to set
	 */
	public void setStuFatherIncome(String stuFatherIncome) {
		this.stuFatherIncome = stuFatherIncome;
	}

	/**
	 * @return the stuMotherOccu
	 */
	public String getStuMotherOccu() {
		return stuMotherOccu;
	}

	/**
	 * @param stuMotherOccu
	 *            the stuMotherOccu to set
	 */
	public void setStuMotherOccu(String stuMotherOccu) {
		this.stuMotherOccu = stuMotherOccu;
	}

	/**
	 * @return the stuCls
	 */

	/**
	 * @param string
	 *            the stuCls to set
	 */

	/**
	 * @return the stuSession
	 */
	public String getStuSession() {
		return stuSession;
	}

	public String getStuCls() {
		return stuCls;
	}

	public void setStuCls(String stuCls) {
		this.stuCls = stuCls;
	}

	/**
	 * @param stuSession
	 *            the stuSession to set
	 */
	public void setStuSession(String stuSession) {
		this.stuSession = stuSession;
	}

	/**
	 * @return the stuStudentId
	 */
	public String getStuStudentId() {
		return stuStudentId;
	}

	/**
	 * @param stuStudentId
	 *            the stuStudentId to set
	 */
	public void setStuStudentId(String stuStudentId) {
		this.stuStudentId = stuStudentId;
	}

	/**
	 * @return the stuRollNo
	 */
	public String getStuRollNo() {
		return stuRollNo;
	}

	/**
	 * @param stuRollNo
	 *            the stuRollNo to set
	 */
	public void setStuRollNo(String stuRollNo) {
		this.stuRollNo = stuRollNo;
	}

	/**
	 * @return the stuStudentPhoto
	 */
	public String getStuStudentPhoto() {
		return stuStudentPhoto;
	}

	/**
	 * @param stuStudentPhoto
	 *            the stuStudentPhoto to set
	 */
	public void setStuStudentPhoto(String stuStudentPhoto) {
		this.stuStudentPhoto = stuStudentPhoto;
	}

	/**
	 * @return the stuFile
	 */
	public UploadedFile getStuFile() {
		return stuFile;
	}

	public List<StudentDataBean> getFeesregList() {
		return feesregList;
	}

	public void setFeesregList(List<StudentDataBean> feesregList) {
		this.feesregList = feesregList;
	}
	
	/**
	 * @param stuFile
	 *            the stuFile to set
	 */
	public void setStuFile(UploadedFile stuFile) {
		this.stuFile = stuFile;
	}
	public String getFees() {
		return fees;
	}
	public void setFees(String fees) {
		this.fees = fees;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public Date getTodayDate() {
		return todayDate;
	}
	public void setTodayDate(Date todayDate) {
		this.todayDate = todayDate;
	}

	
	
}
