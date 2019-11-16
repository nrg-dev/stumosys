package com.stumosys.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.primefaces.model.UploadedFile;

/**
 * This Java Class will communicate with Domain Object Robert Arjun
 * 
 * @date 01-11-2015
 * @copyright NRG
 * 
 */
@XmlRootElement
public class LoginAccess implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3664775843684486600L;
	private String authentoicationStatus;
	private String username;
	private String userpassword;
	private String userStatus;
	private String user_rolles;
	private String login_user;
	private String personID;
	private String rollNumber;
	private String totalStudent;
	private String totalTeacher;
	private String totalParent;
	private String todayAbsent;
	private String schoolName;
	private String schoolPhone;
	private String schoolFax;
	private String schoolEmail;
	private String schoolPost;
	private String schoolAddress;
	private String schoolID;
	private String OTP;
	private String temOTP;
	private String newPassword;
	private String submenu;
	private String confirmPassword;
	private String notification;
	private String alternativeemail;
	private String landlinenumber;
	private String contactnumber;
	private String country;
	private String state;
	//private UploadedFile schoollogo;
	private String filepath;
	private String value1;
	private String value2;
	private String moduleName;
	private String p_id;
	private String globalValue;
	private String value;
	private String lableValue;
	private String supportNumber;
	private String user;
	private String user_email;
	private boolean userdet_flag=false;
	private String loginusername;
	private int userID;
	private List<LoginAccess> selectUserlist=null;
	private String parentName;
	private String teacherName;
	private String classname;
	private int s_id;
	private int subject_id;
	private String examTitle;
	private String mark;
	private String result_status;
	private String status;
	private String subject_name;
	private Date created_date;
	private List<LoginAccess> overallmarkList=null;
	List<String> subjectList=null;
	private List<LoginAccess> subjCountList=null;
	private float count_percentage;
	private int year;
	private List<String> mainMeuList=null;
	private List<String> supMenuList=null;
	private List<String> studentList=null;
		
	public List<String> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<String> studentList) {
		this.studentList = studentList;
	}

	public List<String> getMainMeuList() {
		return mainMeuList;
	}

	public void setMainMeuList(List<String> mainMeuList) {
		this.mainMeuList = mainMeuList;
	}

	public List<String> getSupMenuList() {
		return supMenuList;
	}

	public void setSupMenuList(List<String> supMenuList) {
		this.supMenuList = supMenuList;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public float getCount_percentage() {
		return count_percentage;
	}

	public void setCount_percentage(float count_percentage) {
		this.count_percentage = count_percentage;
	}

	public List<LoginAccess> getSubjCountList() {
		return subjCountList;
	}

	public void setSubjCountList(List<LoginAccess> subjCountList) {
		this.subjCountList = subjCountList;
	}

	public List<String> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<String> subjectList) {
		this.subjectList = subjectList;
	}

	public List<LoginAccess> getOverallmarkList() {
		return overallmarkList;
	}

	public void setOverallmarkList(List<LoginAccess> overallmarkList) {
		this.overallmarkList = overallmarkList;
	}

	public int getS_id() {
		return s_id;
	}

	public void setS_id(int s_id) {
		this.s_id = s_id;
	}

	public int getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(int subject_id) {
		this.subject_id = subject_id;
	}

	public String getExamTitle() {
		return examTitle;
	}

	public void setExamTitle(String examTitle) {
		this.examTitle = examTitle;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getResult_status() {
		return result_status;
	}

	public void setResult_status(String result_status) {
		this.result_status = result_status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date date) {
		this.created_date = date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public List<LoginAccess> getSelectUserlist() {
		return selectUserlist;
	}

	public void setSelectUserlist(List<LoginAccess> selectUserlist) {
		this.selectUserlist = selectUserlist;
	}
	
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getLoginusername() {
		return loginusername;
	}

	public void setLoginusername(String loginusername) {
		this.loginusername = loginusername;
	}

	public boolean isUserdet_flag() {
		return userdet_flag;
	}

	public void setUserdet_flag(boolean userdet_flag) {
		this.userdet_flag = userdet_flag;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public String getSupportNumber() {
		return supportNumber;
	}

	public void setSupportNumber(String supportNumber) {
		this.supportNumber = supportNumber;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
}
public String getLableValue() {
		return lableValue;
	}

	public void setLableValue(String lableValue) {
		this.lableValue = lableValue;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getGlobalValue() {
		return globalValue;
	}

	public void setGlobalValue(String globalValue) {
		this.globalValue = globalValue;
	}

	public String getP_id() {
		return p_id;
	}

	public void setP_id(String p_id) {
		this.p_id = p_id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getAlternativeemail() {
		return alternativeemail;
	}

	public void setAlternativeemail(String alternativeemail) {
		this.alternativeemail = alternativeemail;
	}

	public String getLandlinenumber() {
		return landlinenumber;
	}

	public void setLandlinenumber(String landlinenumber) {
		this.landlinenumber = landlinenumber;
	}

	public String getContactnumber() {
		return contactnumber;
	}

	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/*public UploadedFile getSchoollogo() {
		return schoollogo;
	}

	public void setSchoollogo(UploadedFile schoollogo) {
		this.schoollogo = schoollogo;
	}*/

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public String getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(String schoolID) {
		this.schoolID = schoolID;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getOTP() {
		return OTP;
	}

	public void setOTP(String oTP) {
		OTP = oTP;
	}

	public String getTemOTP() {
		return temOTP;
	}

	public void setTemOTP(String temOTP) {
		this.temOTP = temOTP;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the schoolName
	 */
	public String getSchoolName() {
		return schoolName;
	}

	/**
	 * @param schoolName
	 *            the schoolName to set
	 */
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	/**
	 * @return the schoolPhone
	 */
	public String getSchoolPhone() {
		return schoolPhone;
	}

	/**
	 * @param schoolPhone
	 *            the schoolPhone to set
	 */
	public void setSchoolPhone(String schoolPhone) {
		this.schoolPhone = schoolPhone;
	}

	/**
	 * @return the schoolFax
	 */
	public String getSchoolFax() {
		return schoolFax;
	}

	/**
	 * @param schoolFax
	 *            the schoolFax to set
	 */
	public void setSchoolFax(String schoolFax) {
		this.schoolFax = schoolFax;
	}

	/**
	 * @return the schoolEmail
	 */
	public String getSchoolEmail() {
		return schoolEmail;
	}

	/**
	 * @param schoolEmail
	 *            the schoolEmail to set
	 */
	public void setSchoolEmail(String schoolEmail) {
		this.schoolEmail = schoolEmail;
	}

	/**
	 * @return the schoolPost
	 */
	public String getSchoolPost() {
		return schoolPost;
	}

	/**
	 * @param schoolPost
	 *            the schoolPost to set
	 */
	public void setSchoolPost(String schoolPost) {
		this.schoolPost = schoolPost;
	}

	/**
	 * @return the totalStudent
	 */
	public String getTotalStudent() {
		return totalStudent;
	}

	/**
	 * @param totalStudent
	 *            the totalStudent to set
	 */
	public void setTotalStudent(String totalStudent) {
		this.totalStudent = totalStudent;
	}

	/**
	 * @return the totalTeacher
	 */
	public String getTotalTeacher() {
		return totalTeacher;
	}

	/**
	 * @param totalTeacher
	 *            the totalTeacher to set
	 */
	public void setTotalTeacher(String totalTeacher) {
		this.totalTeacher = totalTeacher;
	}

	/**
	 * @return the totalParent
	 */
	public String getTotalParent() {
		return totalParent;
	}

	/**
	 * @param totalParent
	 *            the totalParent to set
	 */
	public void setTotalParent(String totalParent) {
		this.totalParent = totalParent;
	}

	/**
	 * @return the todayAbsent
	 */
	public String getTodayAbsent() {
		return todayAbsent;
	}

	/**
	 * @param todayAbsent
	 *            the todayAbsent to set
	 */
	public void setTodayAbsent(String todayAbsent) {
		this.todayAbsent = todayAbsent;
	}

	/**
	 * @return the rollNumber
	 */
	public String getRollNumber() {
		return rollNumber;
	}

	/**
	 * @param rollNumber
	 *            the rollNumber to set
	 */
	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}

	/**
	 * @return the login_user
	 */
	public String getLogin_user() {
		return login_user;
	}

	/**
	 * @param login_user
	 *            the login_user to set
	 */
	public void setLogin_user(String login_user) {
		this.login_user = login_user;
	}

	/**
	 * @return the user_rolles
	 */
	public String getUser_rolles() {
		return user_rolles;
	}

	/**
	 * @param user_rolles
	 *            the user_rolles to set
	 */
	public void setUser_rolles(String user_rolles) {
		this.user_rolles = user_rolles;
	}

	/**
	 * @return the userStatus
	 */
	public String getUserStatus() {
		return userStatus;
	}

	/**
	 * @param userStatus
	 *            the userStatus to set
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	/**
	 * @return the personID
	 */
	public String getPersonID() {
		return personID;
	}

	/**
	 * @param personID
	 *            the personID to set
	 */
	public void setPersonID(String personID) {
		this.personID = personID;
	}

	/**
	 * @return the schoolAddress
	 */
	public String getSchoolAddress() {
		return schoolAddress;
	}

	/**
	 * @param schoolAddress
	 *            the schoolAddress to set
	 */
	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}

	public String getAuthentoicationStatus() {
		return authentoicationStatus;
	}

	public void setAuthentoicationStatus(String authentoicationStatus) {
		this.authentoicationStatus = authentoicationStatus;
	}

	public String getSubmenu() {
		return submenu;
	}

	public void setSubmenu(String submenu) {
		this.submenu = submenu;
	}

	    private String displayName;
	     
	    private String name;
	     
	    public LoginAccess() {}
	 
	    public LoginAccess(String displayName, String name) {
	        this.displayName = displayName;
	        this.name = name;
	    }
	 
	    public String getDisplayName() {
	        return displayName;
	    }
	 
	    public void setDisplayName(String displayName) {
	        this.displayName = displayName;
	    }
	 
	    public String getName() {
	        return name;
	    }
	 
	    public void setName(String name) {
	        this.name = name;
	    }
	     
	    @Override
	    public String toString() {
	        return name;
	    }
	    private String organizationName;
		private String orgEmail;
		private String countryName;
		private String orgDetail;
		private String orgPhone;
        private String contact;
        private String options;
    	
    	public String getOptions() {
    		return options;
    	}

    	public void setOptions(String options) {
    		this.options = options;
    	}

		public String getContact() {
			return contact;
		}

		public void setContact(String contact) {
			this.contact = contact;
		}

		public String getOrganizationName() {
			return organizationName;
		}

		public void setOrganizationName(String organizationName) {
			this.organizationName = organizationName;
		}

		public String getOrgEmail() {
			return orgEmail;
		}

		public void setOrgEmail(String orgEmail) {
			this.orgEmail = orgEmail;
		}

		public String getCountryName() {
			return countryName;
		}

		public void setCountryName(String countryName) {
			this.countryName = countryName;
		}

		public String getOrgDetail() {
			return orgDetail;
		}

		public void setOrgDetail(String orgDetail) {
			this.orgDetail = orgDetail;
		}

		public String getOrgPhone() {
			return orgPhone;
		}

		public void setOrgPhone(String orgPhone) {
			this.orgPhone = orgPhone;
		}
	
}
