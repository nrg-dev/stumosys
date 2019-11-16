package com.stumosys.domain;

import java.io.Serializable;
//import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.primefaces.model.UploadedFile;

import com.stumosys.shared.ImagePath;
import com.stumosys.shared.Postcode;
import com.stumosys.shared.Teacher;
import com.stumosys.shared.TeacherClass;
import com.stumosys.shared.TeacherDetail;
import com.stumosys.shared.TeacherSubject;
@XmlRootElement
public class TeacherDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -33779104495844343L;

	private String teaFirstName;
	private String teaLastName;
	private String teaFatherName;
	private String teaMotherName;
	private Date teaDob;
	private String teaGender;
	private String teaPresentAddress;
	private String teaPermanentAddress;
	private String teaPhoneNo;
	private String teaEmail;
	private String teaID;
	private String teaPosition;
	private String teaSubject;
	private String teaWorkingHour;
	private String teaEduQualification;
	private String teaPercentage;
	private String teaYearOfPassing;
	private String teaTeacherPhoto;
	private UploadedFile teaFile;
	private String teaPermanentState;
	private String teaPermanentZip;
	private String teaPresentState;
	private String teaPresentZip;
	private String teafilePath;
	private List<String> teaSubjectTargetList = new ArrayList<String>();
	private List<String> teaClassTargetList = new ArrayList<String>();
	private String teaSecurePasword;
	private String teaUsername;
	private String path;
	private Date pathDate;
	private String names;
	private List<Teacher> teacher1 = null;
	private List<TeacherDetail> teacherDet = null;
	private List<TeacherSubject> teacherSub = null;
	private List<TeacherClass> teacherCls = null;
	private List<String> teaSubjectSourceList = new ArrayList<String>();
	private List<String> teaClassSourceList = new ArrayList<String>();
	private List<ImagePath> teacherImgPath = null;
	private List<Postcode> teaPostcode1 = null;
	private List<Postcode> teaPostcode2 = null;
	private String teaClass;
	private String teaAddress1;
	private String teaAddress2;
	private String teaclsSection;
	private String returnStatus;
	private String schoolName;
	private int schoolID;
	private String[] menus;
	private String schoolLogo;
	private String code;
	private String code1;
	private String teaCountry;
	private String teaCountry1;
	private String teaState;
	private String teaCity;
	private String teaPostal;
	private String teaState1;
	private String teaCity1;
	private String teaPostal1;
	private String teaPhoneNo1; 
	
	private String attendentsStatus;
	private Date attendentsDate;
	private String inTime;
	private String outTime;
	private String leaveType;
	private String person_ID;
	private String lanuchBreak;
	private String attTypeofPeriod;
	private int currentmonths; 
	private int currentyear;
	
	private String notesNumber; 
	private Date notesDate;
	private String notesHeading;
	private String notes;
	
	private String workingdays;
	private String halfdays;
	private String cldays;
	private String oddays;
	private String otdays;
	private List<String> timeList=null;
	private String management;
	
	public String getManagement() {
		return management;
	}

	public void setManagement(String management) {
		this.management = management;
	}

	
	
	public String getNotesNumber() {
		return notesNumber;
	}

	public void setNotesNumber(String notesNumber) {
		this.notesNumber = notesNumber;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotesHeading() {
		return notesHeading;
	}

	public void setNotesHeading(String notesHeading) {
		this.notesHeading = notesHeading;
	}

	public Date getNotesDate() {
		return notesDate;
	}

	public void setNotesDate(Date notesDate) {
		this.notesDate = notesDate;
	}

	public String getWorkingdays() {
		return workingdays;
	}

	public void setWorkingdays(String workingdays) {
		this.workingdays = workingdays;
	}

	public String getHalfdays() {
		return halfdays;
	}

	public void setHalfdays(String halfdays) {
		this.halfdays = halfdays;
	}

	public String getCldays() {
		return cldays;
	}

	public void setCldays(String cldays) {
		this.cldays = cldays;
	}

	public String getOddays() {
		return oddays;
	}

	public void setOddays(String oddays) {
		this.oddays = oddays;
	}

	public String getOtdays() {
		return otdays;
	}

	public void setOtdays(String otdays) {
		this.otdays = otdays;
	}

	public int getCurrentmonths() {
		return currentmonths;
	}

	public void setCurrentmonths(int currentmonths) {
		this.currentmonths = currentmonths;
	}

	public int getCurrentyear() {
		return currentyear;
	}

	public void setCurrentyear(int currentyear) {
		this.currentyear = currentyear;
	}

	public String getAttTypeofPeriod() {
		return attTypeofPeriod;
	}

	public void setAttTypeofPeriod(String attTypeofPeriod) {
		this.attTypeofPeriod = attTypeofPeriod;
	}

	public String getPerson_ID() {
		return person_ID;
	}

	public void setPerson_ID(String person_ID) {
		this.person_ID = person_ID;
	}

	public String getLanuchBreak() {
		return lanuchBreak;
	}

	public void setLanuchBreak(String lanuchBreak) {
		this.lanuchBreak = lanuchBreak;
	}

	public String getAttendentsStatus() {
		return attendentsStatus;
	}

	public void setAttendentsStatus(String attendentsStatus) {
		this.attendentsStatus = attendentsStatus;
	}

	public Date getAttendentsDate() {
		return attendentsDate;
	}

	public void setAttendentsDate(Date attendentsDate) {
		this.attendentsDate = attendentsDate;
	}

	

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
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

	public String getTeaPhoneNo1() {
		return teaPhoneNo1;
	}

	public void setTeaPhoneNo1(String teaPhoneNo1) {
		this.teaPhoneNo1 = teaPhoneNo1;
	}

	public String getTeaState() {
		return teaState;
	}

	public void setTeaState(String teaState) {
		this.teaState = teaState;
	}

	public String getTeaCity() {
		return teaCity;
	}

	public void setTeaCity(String teaCity) {
		this.teaCity = teaCity;
	}

	public String getTeaPostal() {
		return teaPostal;
	}

	public void setTeaPostal(String teaPostal) {
		this.teaPostal = teaPostal;
	}

	public String getTeaState1() {
		return teaState1;
	}

	public void setTeaState1(String teaState1) {
		this.teaState1 = teaState1;
	}

	public String getTeaCity1() {
		return teaCity1;
	}

	public void setTeaCity1(String teaCity1) {
		this.teaCity1 = teaCity1;
	}

	public String getTeaPostal1() {
		return teaPostal1;
	}

	public void setTeaPostal1(String teaPostal1) {
		this.teaPostal1 = teaPostal1;
	}

	public String getTeaCountry1() {
		return teaCountry1;
	}

	public void setTeaCountry1(String teaCountry1) {
		this.teaCountry1 = teaCountry1;
	}

	public String getTeaCountry() {
		return teaCountry;
	}

	public void setTeaCountry(String teaCountry) {
		this.teaCountry = teaCountry;
	}
	
	public String getSchoolLogo() {
		return schoolLogo;
	}

	public void setSchoolLogo(String schoolLogo) {
		this.schoolLogo = schoolLogo;
	}
 	public String[] getMenus() {
		return menus;
	}

	public void setMenus(String[] menus) {
		this.menus = menus;
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
	public String getTeaclsSection() {
		return teaclsSection;
	}

	public void setTeaclsSection(String teaclsSection) {
		this.teaclsSection = teaclsSection;
	}

	/**
	 * @return the teaAddress1
	 */
	public String getTeaAddress1() {
		return teaAddress1;
	}

	/**
	 * @param teaAddress1
	 *            the teaAddress1 to set
	 */
	public void setTeaAddress1(String teaAddress1) {
		this.teaAddress1 = teaAddress1;
	}

	/**
	 * @return the teaAddress2
	 */
	public String getTeaAddress2() {
		return teaAddress2;
	}

	/**
	 * @param teaAddress2
	 *            the teaAddress2 to set
	 */
	public void setTeaAddress2(String teaAddress2) {
		this.teaAddress2 = teaAddress2;
	}

	/**
	 * @return the teaClass
	 */
	public String getTeaClass() {
		return teaClass;
	}

	/**
	 * @param teaClass
	 *            the teaClass to set
	 */
	public void setTeaClass(String teaClass) {
		this.teaClass = teaClass;
	}

	/**
	 * @return the teaPostcode1
	 */
	public List<Postcode> getTeaPostcode1() {
		return teaPostcode1;
	}

	/**
	 * @param teaPostcode1
	 *            the teaPostcode1 to set
	 */
	public void setTeaPostcode1(List<Postcode> teaPostcode1) {
		this.teaPostcode1 = teaPostcode1;
	}

	/**
	 * @return the teaPostcode2
	 */
	public List<Postcode> getTeaPostcode2() {
		return teaPostcode2;
	}

	/**
	 * @param teaPostcode2
	 *            the teaPostcode2 to set
	 */
	public void setTeaPostcode2(List<Postcode> teaPostcode2) {
		this.teaPostcode2 = teaPostcode2;
	}

	/**
	 * @return the teacherImgPath
	 */
	public List<ImagePath> getTeacherImgPath() {
		return teacherImgPath;
	}

	/**
	 * @param teacherImgPath
	 *            the teacherImgPath to set
	 */
	public void setTeacherImgPath(List<ImagePath> teacherImgPath) {
		this.teacherImgPath = teacherImgPath;
	}

	/**
	 * @return the teacherSub
	 */
	public List<TeacherSubject> getTeacherSub() {
		return teacherSub;
	}

	/**
	 * @param teacherSub
	 *            the teacherSub to set
	 */
	public void setTeacherSub(List<TeacherSubject> teacherSub) {
		this.teacherSub = teacherSub;
	}

	/**
	 * @return the teacherCls
	 */
	public List<TeacherClass> getTeacherCls() {
		return teacherCls;
	}

	/**
	 * @param teacherCls
	 *            the teacherCls to set
	 */
	public void setTeacherCls(List<TeacherClass> teacherCls) {
		this.teacherCls = teacherCls;
	}

	/**
	 * @return the teacher1
	 */
	public List<Teacher> getTeacher1() {
		return teacher1;
	}

	/**
	 * @param teacher1
	 *            the teacher1 to set
	 */
	public void setTeacher1(List<Teacher> teacher1) {
		this.teacher1 = teacher1;
	}

	/**
	 * @return the teacherDet
	 */
	public List<TeacherDetail> getTeacherDet() {
		return teacherDet;
	}

	/**
	 * @param teacherDet
	 *            the teacherDet to set
	 */
	public void setTeacherDet(List<TeacherDetail> teacherDet) {
		this.teacherDet = teacherDet;
	}

	/**
	 * @return the names
	 */
	public String getNames() {
		return names;
	}

	/**
	 * @param names
	 *            the names to set
	 */
	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the pathDate
	 */
	public Date getPathDate() {
		return pathDate;
	}

	/**
	 * @param pathDate
	 *            the pathDate to set
	 */
	public void setPathDate(Date pathDate) {
		this.pathDate = pathDate;
	}

	/**
	 * @return the teafilePath
	 */
	public String getTeafilePath() {
		return teafilePath;
	}

	/**
	 * @param teafilePath
	 *            the teafilePath to set
	 */
	public void setTeafilePath(String teafilePath) {
		this.teafilePath = teafilePath;
	}

	/**
	 * @return the teaPermanentState
	 */
	public String getTeaPermanentState() {
		return teaPermanentState;
	}

	/**
	 * @param teaPermanentState
	 *            the teaPermanentState to set
	 */
	public void setTeaPermanentState(String teaPermanentState) {
		this.teaPermanentState = teaPermanentState;
	}

	/**
	 * @return the teaPermanentZip
	 */
	public String getTeaPermanentZip() {
		return teaPermanentZip;
	}

	/**
	 * @param teaPermanentZip
	 *            the teaPermanentZip to set
	 */
	public void setTeaPermanentZip(String teaPermanentZip) {
		this.teaPermanentZip = teaPermanentZip;
	}

	/**
	 * @return the teaPresentState
	 */
	public String getTeaPresentState() {
		return teaPresentState;
	}

	/**
	 * @param teaPresentState
	 *            the teaPresentState to set
	 */
	public void setTeaPresentState(String teaPresentState) {
		this.teaPresentState = teaPresentState;
	}

	/**
	 * @return the teaPresentZip
	 */
	public String getTeaPresentZip() {
		return teaPresentZip;
	}

	/**
	 * @param teaPresentZip
	 *            the teaPresentZip to set
	 */
	public void setTeaPresentZip(String teaPresentZip) {
		this.teaPresentZip = teaPresentZip;
	}

	/**
	 * @return the teaFirstName
	 */
	public String getTeaFirstName() {
		return teaFirstName;
	}

	/**
	 * @param teaFirstName
	 *            the teaFirstName to set
	 */
	public void setTeaFirstName(String teaFirstName) {
		this.teaFirstName = teaFirstName;
	}

	/**
	 * @return the teaLastName
	 */
	public String getTeaLastName() {
		return teaLastName;
	}

	/**
	 * @param teaLastName
	 *            the teaLastName to set
	 */
	public void setTeaLastName(String teaLastName) {
		this.teaLastName = teaLastName;
	}

	/**
	 * @return the teaFatherName
	 */
	public String getTeaFatherName() {
		return teaFatherName;
	}

	/**
	 * @param teaFatherName
	 *            the teaFatherName to set
	 */
	public void setTeaFatherName(String teaFatherName) {
		this.teaFatherName = teaFatherName;
	}

	/**
	 * @return the teaMotherName
	 */
	public String getTeaMotherName() {
		return teaMotherName;
	}

	/**
	 * @param teaMotherName
	 *            the teaMotherName to set
	 */
	public void setTeaMotherName(String teaMotherName) {
		this.teaMotherName = teaMotherName;
	}

	/**
	 * @return the teaDob
	 */
	public Date getTeaDob() {
		return teaDob;
	}

	/**
	 * @param teaDob
	 *            the teaDob to set
	 */
	public void setTeaDob(Date teaDob) {
		this.teaDob = teaDob;
	}

	/**
	 * @return the teaGender
	 */
	public String getTeaGender() {
		return teaGender;
	}

	/**
	 * @param teaGender
	 *            the teaGender to set
	 */
	public void setTeaGender(String teaGender) {
		this.teaGender = teaGender;
	}

	/**
	 * @return the teaPresentAddress
	 */
	public String getTeaPresentAddress() {
		return teaPresentAddress;
	}

	/**
	 * @param teaPresentAddress
	 *            the teaPresentAddress to set
	 */
	public void setTeaPresentAddress(String teaPresentAddress) {
		this.teaPresentAddress = teaPresentAddress;
	}

	/**
	 * @return the teaPermanentAddress
	 */
	public String getTeaPermanentAddress() {
		return teaPermanentAddress;
	}

	/**
	 * @param teaPermanentAddress
	 *            the teaPermanentAddress to set
	 */
	public void setTeaPermanentAddress(String teaPermanentAddress) {
		this.teaPermanentAddress = teaPermanentAddress;
	}

	/**
	 * @return the teaPhoneNo
	 */
	public String getTeaPhoneNo() {
		return teaPhoneNo;
	}

	/**
	 * @param teaPhoneNo
	 *            the teaPhoneNo to set
	 */
	public void setTeaPhoneNo(String teaPhoneNo) {
		this.teaPhoneNo = teaPhoneNo;
	}

	/**
	 * @return the teaEmail
	 */
	public String getTeaEmail() {
		return teaEmail;
	}

	/**
	 * @param teaEmail
	 *            the teaEmail to set
	 */
	public void setTeaEmail(String teaEmail) {
		this.teaEmail = teaEmail;
	}

	/**
	 * @return the teaID
	 */
	public String getTeaID() {
		return teaID;
	}

	/**
	 * @param teaID
	 *            the teaID to set
	 */
	public void setTeaID(String teaID) {
		this.teaID = teaID;
	}

	/**
	 * @return the teaPosition
	 */
	public String getTeaPosition() {
		return teaPosition;
	}

	/**
	 * @param teaPosition
	 *            the teaPosition to set
	 */
	public void setTeaPosition(String teaPosition) {
		this.teaPosition = teaPosition;
	}

	/**
	 * @return the teaSubject
	 */
	public String getTeaSubject() {
		return teaSubject;
	}

	/**
	 * @param teaSubject
	 *            the teaSubject to set
	 */
	public void setTeaSubject(String teaSubject) {
		this.teaSubject = teaSubject;
	}

	/**
	 * @return the teaWorkingHour
	 */
	public String getTeaWorkingHour() {
		return teaWorkingHour;
	}

	/**
	 * @param teaWorkingHour
	 *            the teaWorkingHour to set
	 */
	public void setTeaWorkingHour(String teaWorkingHour) {
		this.teaWorkingHour = teaWorkingHour;
	}

	/**
	 * @return the teaEduQualification
	 */
	public String getTeaEduQualification() {
		return teaEduQualification;
	}

	/**
	 * @param teaEduQualification
	 *            the teaEduQualification to set
	 */
	public void setTeaEduQualification(String teaEduQualification) {
		this.teaEduQualification = teaEduQualification;
	}

	/**
	 * @return the teaPercentage
	 */
	public String getTeaPercentage() {
		return teaPercentage;
	}

	/**
	 * @param teaPercentage
	 *            the teaPercentage to set
	 */
	public void setTeaPercentage(String teaPercentage) {
		this.teaPercentage = teaPercentage;
	}

	/**
	 * @return the teaYearOfPassing
	 */
	public String getTeaYearOfPassing() {
		return teaYearOfPassing;
	}

	/**
	 * @param teaYearOfPassing
	 *            the teaYearOfPassing to set
	 */
	public void setTeaYearOfPassing(String teaYearOfPassing) {
		this.teaYearOfPassing = teaYearOfPassing;
	}

	/**
	 * @return the teaTeacherPhoto
	 */
	public String getTeaTeacherPhoto() {
		return teaTeacherPhoto;
	}

	/**
	 * @param teaTeacherPhoto
	 *            the teaTeacherPhoto to set
	 */
	public void setTeaTeacherPhoto(String teaTeacherPhoto) {
		this.teaTeacherPhoto = teaTeacherPhoto;
	}

	/**
	 * @return the teaFile
	 */
	public UploadedFile getTeaFile() {
		return teaFile;
	}

	/**
	 * @param teaFile
	 *            the teaFile to set
	 */
	public void setTeaFile(UploadedFile teaFile) {
		this.teaFile = teaFile;
	}

	/**
	 * @return the teaSubjectTargetList
	 */
	public List<String> getTeaSubjectTargetList() {
		return teaSubjectTargetList;
	}

	/**
	 * @param teaSubjectTargetList
	 *            the teaSubjectTargetList to set
	 */
	public void setTeaSubjectTargetList(List<String> teaSubjectTargetList) {
		this.teaSubjectTargetList = teaSubjectTargetList;
	}

	/**
	 * @return the teaClassTargetList
	 */
	public List<String> getTeaClassTargetList() {
		return teaClassTargetList;
	}

	/**
	 * @param teaClassTargetList
	 *            the teaClassTargetList to set
	 */
	public void setTeaClassTargetList(List<String> teaClassTargetList) {
		this.teaClassTargetList = teaClassTargetList;
	}

	/**
	 * @return the teaSecurePasword
	 */
	public String getTeaSecurePasword() {
		return teaSecurePasword;
	}

	/**
	 * @param teaSecurePasword
	 *            the teaSecurePasword to set
	 */
	public void setTeaSecurePasword(String teaSecurePasword) {
		this.teaSecurePasword = teaSecurePasword;
	}

	/**
	 * @return the teaUsername
	 */
	public String getTeaUsername() {
		return teaUsername;
	}

	/**
	 * @param teaUsername
	 *            the teaUsername to set
	 */
	public void setTeaUsername(String teaUsername) {
		this.teaUsername = teaUsername;
	}

	/**
	 * @return the teaSubjectSourceList
	 */
	public List<String> getTeaSubjectSourceList() {
		return teaSubjectSourceList;
	}

	/**
	 * @param teaSubjectSourceList
	 *            the teaSubjectSourceList to set
	 */
	public void setTeaSubjectSourceList(List<String> teaSubjectSourceList) {
		this.teaSubjectSourceList = teaSubjectSourceList;
	}

	/**
	 * @return the teaClassSourceList
	 */
	public List<String> getTeaClassSourceList() {
		return teaClassSourceList;
	}

	/**
	 * @param teaClassSourceList
	 *            the teaClassSourceList to set
	 */
	public void setTeaClassSourceList(List<String> teaClassSourceList) {
		this.teaClassSourceList = teaClassSourceList;
	}

	public List<String> getTimeList() {
		return timeList;
	}

	public void setTimeList(List<String> timeList) {
		this.timeList = timeList;
	}
	
	

}
