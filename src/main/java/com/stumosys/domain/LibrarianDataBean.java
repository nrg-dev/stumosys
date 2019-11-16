package com.stumosys.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.primefaces.model.UploadedFile;
@XmlRootElement
public class LibrarianDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7883887825425181177L;
	private String libGender;
	private String libFirstName;
	private String libLastName;
	private String libPhoneNo;
	private String libEmail;
	private String libID;
	private UploadedFile libLibrarianPhoto;
	private String libfilePath;
	private String libSecurePassword;
	private String libUsername;
	private String path;
	private Date imageDate;
	private Date pathDate;
	private String teaclsSection;
	private String teaID;
	private String name;
	private String returnStatus;
	private String schoolName;
	private int schoolID;
	private String classname;
	private String classsection;
	private String subjectname;
	private String subjectcode;
	private String schoolLogo;
	private String subid;
	private String classid;
	private String sno;
	private String[] menus;
	private String countryCode;
	private String status;
	private List<LibrarianDataBean> classtableList = null;

	
	public List<LibrarianDataBean> getClasstableList() {
		return classtableList;
	}
	public void setClasstableList(List<LibrarianDataBean> classtableList) {
		this.classtableList = classtableList;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
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

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public String getSubid() {
		return subid;
	}

	public void setSubid(String subid) {
		this.subid = subid;
	}

	public String getSchoolLogo() {
		return schoolLogo;
	}

	public void setSchoolLogo(String schoolLogo) {
		this.schoolLogo = schoolLogo;
	}
	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getClasssection() {
		return classsection;
	}

	public void setClasssection(String classsection) {
		this.classsection = classsection;
	}

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

	public String getSubjectcode() {
		return subjectcode;
	}

	public void setSubjectcode(String subjectcode) {
		this.subjectcode = subjectcode;
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
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeaID() {
		return teaID;
	}

	public void setTeaID(String teaID) {
		this.teaID = teaID;
	}

	public String getTeaclsSection() {
		return teaclsSection;
	}

	public void setTeaclsSection(String teaclsSection) {
		this.teaclsSection = teaclsSection;
	}

	public Date getPathDate() {
		return pathDate;
	}

	public void setPathDate(Date pathDate) {
		this.pathDate = pathDate;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getImageDate() {
		return imageDate;
	}

	public void setImageDate(Date imageDate) {
		this.imageDate = imageDate;
	}

	public String getLibSecurePassword() {
		return libSecurePassword;
	}

	public void setLibSecurePassword(String libSecurePassword) {
		this.libSecurePassword = libSecurePassword;
	}

	public String getLibUsername() {
		return libUsername;
	}

	public void setLibUsername(String libUsername) {
		this.libUsername = libUsername;
	}

	public String getLibfilePath() {
		return libfilePath;
	}

	public void setLibfilePath(String libfilePath) {
		this.libfilePath = libfilePath;
	}

	public String getLibGender() {
		return libGender;
	}

	public void setLibGender(String libGender) {
		this.libGender = libGender;
	}

	public String getLibFirstName() {
		return libFirstName;
	}

	public void setLibFirstName(String libFirstName) {
		this.libFirstName = libFirstName;
	}

	public String getLibLastName() {
		return libLastName;
	}

	public void setLibLastName(String libLastName) {
		this.libLastName = libLastName;
	}

	public String getLibPhoneNo() {
		return libPhoneNo;
	}

	public void setLibPhoneNo(String libPhoneNo) {
		this.libPhoneNo = libPhoneNo;
	}

	public String getLibEmail() {
		return libEmail;
	}

	public void setLibEmail(String libEmail) {
		this.libEmail = libEmail;
	}

	public String getLibID() {
		return libID;
	}

	public void setLibID(String libID) {
		this.libID = libID;
	}

	public UploadedFile getLibLibrarianPhoto() {
		return libLibrarianPhoto;
	}

	public void setLibLibrarianPhoto(UploadedFile uploadedFile) {
		this.libLibrarianPhoto = uploadedFile;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
