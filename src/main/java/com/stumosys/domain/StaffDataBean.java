package com.stumosys.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.primefaces.model.UploadedFile;

import com.stumosys.shared.ImagePath;
import com.stumosys.shared.Staff;
@XmlRootElement
public class StaffDataBean extends StudentDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3106247360920489602L;

	private String staStaffID;
	private String staFirstName;
	private String staLastName;
	private String staGender;
	private String staEmail;
	private String staPhoneNo;
	private String staSatffPhoto;
	private UploadedFile staFile;
	private String stafilePath;
	private String staSecurePasword;
	private String staUsername;
	private List<Staff> staffList;
	private List<ImagePath> staffImageList;
	private Date staPathDate;
	private String name;
	private List<Staff> staffRes = null;
	private List<ImagePath> imagepath = null;
	private String rollcheck;
	private int schoolID;
	private Date createDate,relievingDate,dateofBirth;
	private String teacherName,designation,nameofDuty,placeofDuty,referenceNo,studentName,parentName,
	academicFromyear,academicToyear,conduct;
	private String countryCode;
	
	
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getConduct() {
		return conduct;
	}

	public void setConduct(String conduct) {
		this.conduct = conduct;
	}

	public Date getDateofBirth() {
		return dateofBirth;
	}

	public void setDateofBirth(Date dateofBirth) {
		this.dateofBirth = dateofBirth;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getAcademicFromyear() {
		return academicFromyear;
	}

	public void setAcademicFromyear(String academicFromyear) {
		this.academicFromyear = academicFromyear;
	}

	public String getAcademicToyear() {
		return academicToyear;
	}

	public void setAcademicToyear(String academicToyear) {
		this.academicToyear = academicToyear;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getRelievingDate() {
		return relievingDate;
	}

	public void setRelievingDate(Date relievingDate) {
		this.relievingDate = relievingDate;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getNameofDuty() {
		return nameofDuty;
	}

	public void setNameofDuty(String nameofDuty) {
		this.nameofDuty = nameofDuty;
	}

	public String getPlaceofDuty() {
		return placeofDuty;
	}

	public void setPlaceofDuty(String placeofDuty) {
		this.placeofDuty = placeofDuty;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public int getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(int schoolID) {
		this.schoolID = schoolID;
	}

	public String getStaSatffPhoto() {
		return staSatffPhoto;
	}

	public void setStaSatffPhoto(String staSatffPhoto) {
		this.staSatffPhoto = staSatffPhoto;
	}

	public String getRollcheck() {
		return rollcheck;
	}

	public void setRollcheck(String rollcheck) {
		this.rollcheck = rollcheck;
	}

	public List<Staff> getStaffRes() {
		return staffRes;
	}

	public void setStaffRes(List<Staff> staffRes) {
		this.staffRes = staffRes;
	}

	public List<ImagePath> getImagepath() {
		return imagepath;
	}

	public void setImagepath(List<ImagePath> imagepath) {
		this.imagepath = imagepath;
	}

	/**
	 * @return the staffImageList
	 */
	public List<ImagePath> getStaffImageList() {
		return staffImageList;
	}

	/**
	 * @param staffImageList
	 *            the staffImageList to set
	 */
	public void setStaffImageList(List<ImagePath> staffImageList) {
		this.staffImageList = staffImageList;
	}

	/**
	 * @return the staffList
	 */
	public List<Staff> getStaffList() {
		return staffList;
	}

	/**
	 * @param staffList
	 *            the staffList to set
	 */
	public void setStaffList(List<Staff> staffList) {
		this.staffList = staffList;
	}

	/**
	 * @return the staGender
	 */
	public String getStaGender() {
		return staGender;
	}

	/**
	 * @param staGender
	 *            the staGender to set
	 */
	public void setStaGender(String staGender) {
		this.staGender = staGender;
	}

	/**
	 * @return the staSecurePasword
	 */

	public String getStaSecurePasword() {
		return staSecurePasword;
	}

	/**
	 * @param staSecurePasword
	 *            the staSecurePasword to set
	 */
	public void setStaSecurePasword(String staSecurePasword) {
		this.staSecurePasword = staSecurePasword;
	}

	/**
	 * @return the staUsername
	 */
	public String getStaUsername() {
		return staUsername;
	}

	/**
	 * @param staUsername
	 *            the staUsername to set
	 */
	public void setStaUsername(String staUsername) {
		this.staUsername = staUsername;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the staFirstName
	 */
	public String getStaFirstName() {
		return staFirstName;
	}

	/**
	 * @param staFirstName
	 *            the staFirstName to set
	 */
	public void setStaFirstName(String staFirstName) {
		this.staFirstName = staFirstName;
	}

	/**
	 * @return the staLastName
	 */
	public String getStaLastName() {
		return staLastName;
	}

	/**
	 * @param parLastName
	 *            the parLastName to set
	 */
	public void setStaLastName(String staLastName) {
		this.staLastName = staLastName;
	}

	/**
	 * @return the staEmail
	 */
	public String getStaEmail() {
		return staEmail;
	}

	/**
	 * @param staEmail
	 *            the staEmail to set
	 */
	public void setStaEmail(String staEmail) {
		this.staEmail = staEmail;
	}

	/**
	 * @return the staPhoneNo
	 */
	public String getStaPhoneNo() {
		return staPhoneNo;
	}

	/**
	 * @param staPhoneNo
	 *            the staPhoneNo to set
	 */
	public void setStaPhoneNo(String staPhoneNo) {
		this.staPhoneNo = staPhoneNo;
	}

	/**
	 * @return the staSatffPhoto
	 */
	public String getStaStaffPhoto() {
		return staSatffPhoto;
	}

	/**
	 * @param staSatffPhoto
	 *            the staSatffPhoto to set
	 */
	public void setStaStaffPhoto(String staSatffPhoto) {
		this.staSatffPhoto = staSatffPhoto;
	}

	/**
	 * @return the staFile
	 */
	public UploadedFile getStaFile() {
		return staFile;
	}

	/**
	 * @param staFile
	 *            the staFile to set
	 */
	public void setStaFile(UploadedFile staFile) {
		this.staFile = staFile;
	}

	/**
	 * @return the staStaffID
	 */
	public String getStaStaffID() {
		return staStaffID;
	}

	/**
	 * @param staStaffID
	 *            the staStaffID to set
	 */
	public void setStaStaffID(String staStaffID) {
		this.staStaffID = staStaffID;
	}

	/**
	 * @return the stafilePath
	 */
	public String getStafilePath() {
		return stafilePath;
	}

	/**
	 * @param stafilePath
	 *            the stafilePath to set
	 */
	public void setStafilePath(String stafilePath) {
		this.stafilePath = stafilePath;
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
	 * @return the staPathDate
	 */
	public Date getStaPathDate() {
		return staPathDate;
	}

	/**
	 * @param staPathDate
	 *            the staPathDate to set
	 */
	public void setStaPathDate(Date staPathDate) {
		this.staPathDate = staPathDate;
	}

}
