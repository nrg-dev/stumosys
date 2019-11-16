package com.stumosys.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class StudentPerformanceDataBean implements Serializable {

	private static final long serialVersionUID = -3745221068211282754L;
	private String performStudID;
	private String performStudIDs;
	private String perClassSection;
	private String performViewClass;
	private String performViewSection;
	private String[] studentAppearance;
	private String[] studentAttitude;
	private List<String> applist = null;
	private List<String> testapp = null;
	private List<String> test = null;
	private String studID;
	private List<String> mailid = null;
	private boolean appearancecheck=false;
	private boolean attitudecheck=false;
	private String apperanceOther;
	private String attitudeOther;
	private String returnStatus;
	private String classname;
	private List<String> phonenos=null;
	private String schoolName;
	private String schoolLogo;
	private Date fromdate;
	private Date todate;
	private int schoolID;
	private Date ttdate;private Date bdate;
	private String stuFirstName;
	private String stuLastName;

	private String parentsPhoneNo;
	private String parentsMail;
	
	private String stuAddress;
	private String stuParRelation;
	
	private String stuCountry;
	private String stuState;
	
	  
	public String getStuCountry() {
		return stuCountry;
	}

	public void setStuCountry(String stuCountry) {
		this.stuCountry = stuCountry;
	}

	public String getStuState() {
		return stuState;
	}

	public void setStuState(String stuState) {
		this.stuState = stuState;
	}

	public String getStuParRelation() {
		return stuParRelation;
	}

	public void setStuParRelation(String stuParRelation) {
		this.stuParRelation = stuParRelation;
	}

	public String getStuAddress() {
		return stuAddress;
	}

	public void setStuAddress(String stuAddress) {
		this.stuAddress = stuAddress;
	}

	public String getParentsPhoneNo() {
		return parentsPhoneNo;
	}

	public void setParentsPhoneNo(String parentsPhoneNo) {
		this.parentsPhoneNo = parentsPhoneNo;
	}

	public String getParentsMail() {
		return parentsMail;
	}

	public void setParentsMail(String parentsMail) {
		this.parentsMail = parentsMail;
	}

	public String getStuFirstName() {
		return stuFirstName;
	}

	public void setStuFirstName(String stuFirstName) {
		this.stuFirstName = stuFirstName;
	}

	public String getStuLastName() {
		return stuLastName;
	}

	public void setStuLastName(String stuLastName) {
		this.stuLastName = stuLastName;
	}

	public Date getBdate() {
		return bdate;
	}

	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}

	public Date getTtdate() {
		return ttdate;
	}

	public void setTtdate(Date ttdate) {
		this.ttdate = ttdate;
	}

	private Date TeaDob;
	public Date getTeaDob() {
		return TeaDob;
	}

	public void setTeaDob(Date teaDob) {
		TeaDob = teaDob;
	}

	public int getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(int schoolID) {
		this.schoolID = schoolID;
	}

	public Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
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

	public List<String> getPhonenos() {
		return phonenos;
	}

	public void setPhonenos(List<String> phonenos) {
		this.phonenos = phonenos;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
	public String getPerformStudIDs() {
		return performStudIDs;
	}

	public void setPerformStudIDs(String performStudIDs) {
		this.performStudIDs = performStudIDs;
	}

	/**
	 * @return the mailid
	 */
	public List<String> getMailid() {
		return mailid;
	}

	/**
	 * @param mailid
	 *            the mailid to set
	 */
	public void setMailid(List<String> mailid) {
		this.mailid = mailid;
	}

	public String getStudID() {
		return studID;
	}

	public void setStudID(String studID) {
		this.studID = studID;
	}

	public List<String> getTest() {
		return test;
	}

	public void setTest(List<String> test) {
		this.test = test;
	}

	private String appOthers;
	private String attOthers;
	private ArrayList<StudentPerformanceDataBean> stuClassList;
	private String stuCls1;

	public String getStuCls1() {
		return stuCls1;
	}

	public void setStuCls1(String stuCls1) {
		this.stuCls1 = stuCls1;
	}

	public ArrayList<StudentPerformanceDataBean> getStuClassList() {
		return stuClassList;
	}

	public void setStuClassList(ArrayList<StudentPerformanceDataBean> stuClassList) {
		this.stuClassList = stuClassList;
	}

	public String getAppOthers() {
		return appOthers;
	}

	public void setAppOthers(String appOthers) {
		this.appOthers = appOthers;
	}

	public String getAttOthers() {
		return attOthers;
	}

	public void setAttOthers(String attOthers) {
		this.attOthers = attOthers;
	}

	public List<String> getTestapp() {
		return testapp;
	}

	public void setTestapp(List<String> testapp) {
		this.testapp = testapp;
	}

	private String clssection;
	private List<String> app = null;

	public List<String> getApp() {
		return app;
	}

	public void setApp(List<String> app) {
		this.app = app;
	}

	public String getClssection() {
		return clssection;
	}

	public void setClssection(String clssection) {
		this.clssection = clssection;
	}

	private String stuName;
	private String stuName1;
	private String stuPar1;

	public String getStuPar1() {
		return stuPar1;
	}

	public void setStuPar1(String stuPar1) {
		this.stuPar1 = stuPar1;
	}

	public String getStuName1() {
		return stuName1;
	}

	public void setStuName1(String stuName1) {
		this.stuName1 = stuName1;
	}

	private String parName;
	private String stuApp;
	private String stuAtt;

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

	List<StudentPerformanceDataBean> studlist = null;

	public List<StudentPerformanceDataBean> getStudlist() {
		return studlist;
	}

	public void setStudlist(List<StudentPerformanceDataBean> studlist) {
		this.studlist = studlist;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getParName() {
		return parName;
	}

	public void setParName(String parName) {
		this.parName = parName;
	}

	private String stuCls;

	public String getStuCls() {
		return stuCls;
	}

	public void setStuCls(String stuCls) {
		this.stuCls = stuCls;
	}

	List<String> applist1 = new ArrayList<String>();
	List<String> attlist1 = new ArrayList<String>();
	private ArrayList<StudentPerformanceDataBean> studentclassList;

	public ArrayList<StudentPerformanceDataBean> getStudentclassList() {
		return studentclassList;
	}

	public void setStudentclassList(ArrayList<StudentPerformanceDataBean> studentclassList) {
		this.studentclassList = studentclassList;
	}

	public List<String> getAttlist1() {
		return attlist1;
	}

	public void setAttlist1(List<String> attlist1) {
		this.attlist1 = attlist1;
	}

	public List<String> getApplist1() {
		return applist1;
	}

	public void setApplist1(List<String> applist1) {
		this.applist1 = applist1;
	}

	public List<String> getApplist() {
		return applist;
	}

	public void setApplist(List<String> applist) {
		this.applist = applist;
	}

	private ArrayList<StudentPerformanceDataBean> rollnolist;

	public String[] getStudentAttitude() {
		return studentAttitude;
	}

	public void setStudentAttitude(String[] studentAttitude) {
		this.studentAttitude = studentAttitude;
	}

	public ArrayList<StudentPerformanceDataBean> getRollnolist() {
		return rollnolist;
	}

	public void setRollnolist(ArrayList<StudentPerformanceDataBean> rollnolist) {
		this.rollnolist = rollnolist;
	}

	/**
	 * @return the studentAppearance
	 */
	public String[] getStudentAppearance() {
		return studentAppearance;
	}

	/**
	 * @param studentAppearance
	 *            the studentAppearance to set
	 */
	public void setStudentAppearance(String[] studentAppearance) {
		this.studentAppearance = studentAppearance;
	}

	/**
	 * @return the performStudID
	 */
	public String getPerformStudID() {
		return performStudID;
	}

	/**
	 * @param performStudID
	 *            the performStudID to set
	 */
	public void setPerformStudID(String performStudID) {
		this.performStudID = performStudID;
	}

	/**
	 * @return the perClassSection
	 */
	public String getPerClassSection() {
		return perClassSection;
	}

	/**
	 * @param perClassSection
	 *            the perClassSection to set
	 */
	public void setPerClassSection(String perClassSection) {
		this.perClassSection = perClassSection;
	}

	/**
	 * @return the performViewClass
	 */
	public String getPerformViewClass() {
		return performViewClass;
	}

	/**
	 * @param performViewClass
	 *            the performViewClass to set
	 */
	public void setPerformViewClass(String performViewClass) {
		this.performViewClass = performViewClass;
	}

	/**
	 * @return the performViewSection
	 */
	public String getPerformViewSection() {
		return performViewSection;
	}

	/**
	 * @param performViewSection
	 *            the performViewSection to set
	 */
	public void setPerformViewSection(String performViewSection) {
		this.performViewSection = performViewSection;
	}

	public boolean isAppearancecheck() {
		return appearancecheck;
	}

	public void setAppearancecheck(boolean appearancecheck) {
		this.appearancecheck = appearancecheck;
	}

	public boolean isAttitudecheck() {
		return attitudecheck;
	}

	public void setAttitudecheck(boolean attitudecheck) {
		this.attitudecheck = attitudecheck;
	}

	public String getApperanceOther() {
		return apperanceOther;
	}

	public void setApperanceOther(String apperanceOther) {
		this.apperanceOther = apperanceOther;
	}

	public String getAttitudeOther() {
		return attitudeOther;
	}

	public void setAttitudeOther(String attitudeOther) {
		this.attitudeOther = attitudeOther;
	}

}
