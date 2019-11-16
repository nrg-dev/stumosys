package com.stumosys.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.primefaces.model.UploadedFile;

import com.stumosys.shared.ImagePath;
import com.stumosys.shared.Parent;
import com.stumosys.shared.ParentDetail;
import com.stumosys.shared.StudentParent;
@XmlRootElement
public class ParentsDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3106247360920489602L;

	private String parStudID;
	private String parFirstName;
	private String parLastName;
	private String parentRelation;
	private String parEmail;
	private String parPhoneNo;
	private String parParentPhoto;
	private UploadedFile parFile;
	private String parStuClass;
	private String parStuSection;
	private String parParentID;
	private String parfilePath;
	private String parSecurePasword;
	private String parUsername;
	private List<Parent> parentList;
	private List<ParentDetail> parentDetailsList;
	private List<StudentParent> studentParentList;
	private List<ImagePath> parentImageList;
	private Date parPathDate;
	private String name;
	private List<ParentsDataBean> parentdetails=null;
	private List<String> studentClass=null;
	private String returnStatus;
	private String schoolName;
	private int schoolID;
	private String schoolLogo;
	private String[] menus;
	private String countryCode;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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
	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public int getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(int schoolID) {
		this.schoolID = schoolID;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	public List<String> getStudentClass() {
		return studentClass;
	}

	public void setStudentClass(List<String> studentClass) {
		this.studentClass = studentClass;
	}

	public List<ParentsDataBean> getParentdetails() {
		return parentdetails;
	}

	public void setParentdetails(List<ParentsDataBean> parentdetails) {
		this.parentdetails = parentdetails;
	}

	/**
	 * @return the parentDetailsList
	 */
	public List<ParentDetail> getParentDetailsList() {
		return parentDetailsList;
	}

	/**
	 * @param parentDetailsList
	 *            the parentDetailsList to set
	 */
	public void setParentDetailsList(List<ParentDetail> parentDetailsList) {
		this.parentDetailsList = parentDetailsList;
	}

	/**
	 * @return the parentImageList
	 */
	public List<ImagePath> getParentImageList() {
		return parentImageList;
	}

	/**
	 * @param parentImageList
	 *            the parentImageList to set
	 */
	public void setParentImageList(List<ImagePath> parentImageList) {
		this.parentImageList = parentImageList;
	}

	/**
	 * @return the parentList
	 */
	public List<Parent> getParentList() {
		return parentList;
	}

	/**
	 * @param parentList
	 *            the parentList to set
	 */
	public void setParentList(List<Parent> parentList) {
		this.parentList = parentList;
	}

	/**
	 * @return the studentParentList
	 */
	public List<StudentParent> getStudentParentList() {
		return studentParentList;
	}

	/**
	 * @param studentParentList
	 *            the studentParentList to set
	 */
	public void setStudentParentList(List<StudentParent> studentParentList) {
		this.studentParentList = studentParentList;
	}

	/**
	 * @return the parSecurePasword
	 */
	public String getParSecurePasword() {
		return parSecurePasword;
	}

	/**
	 * @param parSecurePasword
	 *            the parSecurePasword to set
	 */
	public void setParSecurePasword(String parSecurePasword) {
		this.parSecurePasword = parSecurePasword;
	}

	/**
	 * @return the parUsername
	 */
	public String getParUsername() {
		return parUsername;
	}

	/**
	 * @param parUsername
	 *            the parUsername to set
	 */
	public void setParUsername(String parUsername) {
		this.parUsername = parUsername;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the parStuClass
	 */
	public String getParStuClass() {
		return parStuClass;
	}

	/**
	 * @param parStuClass
	 *            the parStuClass to set
	 */
	public void setParStuClass(String parStuClass) {
		this.parStuClass = parStuClass;
	}

	/**
	 * @return the parStuSection
	 */
	public String getParStuSection() {
		return parStuSection;
	}

	/**
	 * @param parStuSection
	 *            the parStuSection to set
	 */
	public void setParStuSection(String parStuSection) {
		this.parStuSection = parStuSection;
	}

	/**
	 * @return the parStudID
	 */
	public String getParStudID() {
		return parStudID;
	}

	/**
	 * @param parStudID
	 *            the parStudID to set
	 */
	public void setParStudID(String parStudID) {
		this.parStudID = parStudID;
	}

	/**
	 * @return the parFirstName
	 */
	public String getParFirstName() {
		return parFirstName;
	}

	/**
	 * @param parFirstName
	 *            the parFirstName to set
	 */
	public void setParFirstName(String parFirstName) {
		this.parFirstName = parFirstName;
	}

	/**
	 * @return the parLastName
	 */
	public String getParLastName() {
		return parLastName;
	}

	/**
	 * @param parLastName
	 *            the parLastName to set
	 */
	public void setParLastName(String parLastName) {
		this.parLastName = parLastName;
	}

	/**
	 * @return the parentRelation
	 */
	public String getParentRelation() {
		return parentRelation;
	}

	/**
	 * @param parentRelation
	 *            the parentRelation to set
	 */
	public void setParentRelation(String parentRelation) {
		this.parentRelation = parentRelation;
	}

	/**
	 * @return the parEmail
	 */
	public String getParEmail() {
		return parEmail;
	}

	/**
	 * @param parEmail
	 *            the parEmail to set
	 */
	public void setParEmail(String parEmail) {
		this.parEmail = parEmail;
	}

	/**
	 * @return the parPhoneNo
	 */
	public String getParPhoneNo() {
		return parPhoneNo;
	}

	/**
	 * @param parPhoneNo
	 *            the parPhoneNo to set
	 */
	public void setParPhoneNo(String parPhoneNo) {
		this.parPhoneNo = parPhoneNo;
	}

	/**
	 * @return the parParentPhoto
	 */
	public String getParParentPhoto() {
		return parParentPhoto;
	}

	/**
	 * @param parParentPhoto
	 *            the parParentPhoto to set
	 */
	public void setParParentPhoto(String parParentPhoto) {
		this.parParentPhoto = parParentPhoto;
	}

	/**
	 * @return the parFile
	 */
	public UploadedFile getParFile() {
		return parFile;
	}

	/**
	 * @param parFile
	 *            the parFile to set
	 */
	public void setParFile(UploadedFile parFile) {
		this.parFile = parFile;
	}

	/**
	 * @return the parParentID
	 */
	public String getParParentID() {
		return parParentID;
	}

	/**
	 * @param parParentID
	 *            the parParentID to set
	 */
	public void setParParentID(String parParentID) {
		this.parParentID = parParentID;
	}

	/**
	 * @return the parfilePath
	 */
	public String getParfilePath() {
		return parfilePath;
	}

	/**
	 * @param parfilePath
	 *            the parfilePath to set
	 */
	public void setParfilePath(String parfilePath) {
		this.parfilePath = parfilePath;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the parPathDate
	 */
	public Date getParPathDate() {
		return parPathDate;
	}

	/**
	 * @param parPathDate
	 *            the parPathDate to set
	 */
	public void setParPathDate(Date parPathDate) {
		this.parPathDate = parPathDate;
	}

}
