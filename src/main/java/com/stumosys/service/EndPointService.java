package com.stumosys.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.simple.parser.ParseException;
//import org.neo4j.cypher.internal.compiler.v2_1.perty.docbuilders.catchErrors;
//org.codehaus.jettison.json.JSONException: JSONObject["username"] not found.
import org.primefaces.model.DualListModel;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import com.google.gson.Gson;
//import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stumosys.domain.AttendanceDataBean;
import com.stumosys.domain.BookSaleDataBean;
import com.stumosys.domain.ClassTimeTableDataBean;
import com.stumosys.domain.HomeworkDatabean;
import com.stumosys.domain.LibrarianDataBean;
import com.stumosys.domain.LibraryDataBean;
import com.stumosys.domain.LoginAccess;
import com.stumosys.domain.NoticeBoardDataBean;
import com.stumosys.domain.ParentsDataBean;
import com.stumosys.domain.PaymentDatabean;
import com.stumosys.domain.RegisteredLogin;
import com.stumosys.domain.ReportCardDataBean;
import com.stumosys.domain.StaffDataBean;
import com.stumosys.domain.StudentDataBean;
import com.stumosys.domain.StudentPerformanceDataBean;
import com.stumosys.domain.TeacherDataBean;
import com.stumosys.domain.TimeTableDataBean;
import com.stumosys.managedBean.SpringApplicationContext;
import com.stumosys.shared.BookSalesRecord;
import com.stumosys.shared.Noticeboard;
import com.stumosys.shared.Person;
import com.stumosys.shared.School;
import com.stumosys.shared.StudentDetail;
import com.stumosys.shared.StudentPerformance;
import com.stumosys.shared.SupProduct;
import com.stumosys.shared.UserProduct;
import com.stumosys.util.MailSendJDBC;

@XmlRootElement
@Path("/endPoint/service")
public class EndPointService {
	@Context 
	private ServletContext context;
	SmsService smsservice;
	public static Logger logger = Logger.getLogger(EndPointService.class);
	List<TimeTableDataBean> examtableList=new ArrayList<TimeTableDataBean>();	
	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
	Date now = new Date();
	private DualListModel<String> subjectList;
	private DualListModel<String> classList;
	String studClass;
	List<ClassTimeTableDataBean> classtableList=new ArrayList<ClassTimeTableDataBean>();
	private List<String> classSectionList = null;
	private List<String> studentIDList = null;
	private List<String> sclasslist = null;
	private List<String> classSubjectList = null;
	TimeTableDataBean timeTableDataBean=new TimeTableDataBean();
	private int examTableid;
	List<ParentsDataBean> parentList=null;	
	ClassTimeTableDataBean classTimeTableDataBean=new ClassTimeTableDataBean();
	LibrarianDataBean librarianDataBean=new LibrarianDataBean();
	private DualListModel<String> availableBookList;
	LibraryDataBean libraryDataBean=new LibraryDataBean();
	private String createStatus;
	private List<NoticeBoardDataBean> noticeList = null;
	private List<Noticeboard> noticeListView = null;
	NoticeBoardDataBean noticeBoardDataBean = new NoticeBoardDataBean();
	private List<NoticeBoardDataBean> noticeBoardList = null;
	private List<UserProduct> menuList;
	private List<SupProduct> submenuList;
	ReportCardDataBean reportCardDataBean=new ReportCardDataBean();
	TeacherDataBean teacherDataBean=new TeacherDataBean();
	LoginAccess loginAccess=new LoginAccess();
	//Prema
	PaymentDatabean paymentDatabean=new PaymentDatabean();
	BookSaleDataBean bookSaleDataBean=new BookSaleDataBean();
	// Sujani
	RegisteredLogin registeredLogin=new RegisteredLogin();
	List<BookSaleDataBean> bookinfolist = null;
	//udhya
	StaffDataBean staffDataBean=new StaffDataBean();
	private List<StaffDataBean> bookShoplist = null;
	private List<String> teacherIDList=null;	
	private List<StaffDataBean> ImageListPath = null;
	SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");		
	ResourceBundle paths=ResourceBundle.getBundle("com.sms.paths");
	public List<TimeTableDataBean> timesBean=null;
	StudentDataBean studentDataBean=new StudentDataBean();
	ParentsDataBean parentDataBean=new ParentsDataBean();
	StudentPerformanceDataBean studentPerformanceDataBean=new StudentPerformanceDataBean();
	AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
	List<LibrarianDataBean> classsectablist=new ArrayList<LibrarianDataBean>();
	ArrayList<AttendanceDataBean> stuattendancelist=new ArrayList<AttendanceDataBean>();
	
	List<String>monthlist=new ArrayList<String>();
    public List<String> getMonthlist() {
		return monthlist;
	}

	public void setMonthlist(List<String> monthlist) {
		this.monthlist = monthlist;
	}

	public LibrarianDataBean getLibrarianDataBean() {
		return librarianDataBean;
	}

	public void setLibrarianDataBean(LibrarianDataBean librarianDataBean) {
		this.librarianDataBean = librarianDataBean;
	}

	public AttendanceDataBean getAttendanceDataBean() {
		return attendanceDataBean;
	}

	public void setAttendanceDataBean(AttendanceDataBean attendanceDataBean) {
		this.attendanceDataBean = attendanceDataBean;
	}

	public StudentPerformanceDataBean getStudentPerformanceDataBean() {
	 	return studentPerformanceDataBean;
	 }

	 public void setStudentPerformanceDataBean(
	 		StudentPerformanceDataBean studentPerformanceDataBean) {
	 	this.studentPerformanceDataBean = studentPerformanceDataBean;
	 }
	public StudentDataBean getStudentDataBean() {
		return studentDataBean;
	}

	public void setStudentDataBean(StudentDataBean studentDataBean) {
		this.studentDataBean = studentDataBean;
	}

	public ParentsDataBean getParentDataBean() {
		return parentDataBean;
	}

	public void setParentDataBean(ParentsDataBean parentDataBean) {
		this.parentDataBean = parentDataBean;
	}

	public List<TimeTableDataBean> getTimesBean() {
		return timesBean;
	}

	public void setTimesBean(List<TimeTableDataBean> timesBean) {
		this.timesBean = timesBean;
	}
	public PaymentDatabean getPaymentDatabean() {
		return paymentDatabean;
	}

	public void setPaymentDatabean(PaymentDatabean paymentDatabean) {
		this.paymentDatabean = paymentDatabean;
	}

	public BookSaleDataBean getBookSaleDataBean() {
		return bookSaleDataBean;
	}

	public void setBookSaleDataBean(BookSaleDataBean bookSaleDataBean) {
		this.bookSaleDataBean = bookSaleDataBean;
	}

	public RegisteredLogin getRegisteredLogin() {
		return registeredLogin;
	}

	public void setRegisteredLogin(RegisteredLogin registeredLogin) {
		this.registeredLogin = registeredLogin;
	}

	public List<BookSaleDataBean> getBookinfolist() {
		return bookinfolist;
	}

	public void setBookinfolist(List<BookSaleDataBean> bookinfolist) {
		this.bookinfolist = bookinfolist;
	}

	public StaffDataBean getStaffDataBean() {
		return staffDataBean;
	}

	public void setStaffDataBean(StaffDataBean staffDataBean) {
		this.staffDataBean = staffDataBean;
	}

	public List<StaffDataBean> getBookShoplist() {
		return bookShoplist;
	}

	public void setBookShoplist(List<StaffDataBean> bookShoplist) {
		this.bookShoplist = bookShoplist;
	}

	public List<String> getTeacherIDList() {
		return teacherIDList;
	}

	public void setTeacherIDList(List<String> teacherIDList) {
		this.teacherIDList = teacherIDList;
	}

	public List<StaffDataBean> getImageListPath() {
		return ImageListPath;
	}

	public void setImageListPath(List<StaffDataBean> imageListPath) {
		ImageListPath = imageListPath;
	}

	public TeacherDataBean getTeacherDataBean() {
		return teacherDataBean;
	}

	public void setTeacherDataBean(TeacherDataBean teacherDataBean) {
		this.teacherDataBean = teacherDataBean;
	}

	public ReportCardDataBean getReportCardDataBean() {
		return reportCardDataBean;
	}

	public void setReportCardDataBean(ReportCardDataBean reportCardDataBean) {
		this.reportCardDataBean = reportCardDataBean;
	}

	public List<UserProduct> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<UserProduct> menuList) {
		this.menuList = menuList;
	}

	public List<NoticeBoardDataBean> getNoticeBoardList() {
		return noticeBoardList;
	}

	public void setNoticeBoardList(List<NoticeBoardDataBean> noticeBoardList) {
		this.noticeBoardList = noticeBoardList;
	}

	public NoticeBoardDataBean getNoticeBoardDataBean() {
		return noticeBoardDataBean;
	}

	public void setNoticeBoardDataBean(NoticeBoardDataBean noticeBoardDataBean) {
		this.noticeBoardDataBean = noticeBoardDataBean;
	}

	public List<Noticeboard> getNoticeListView() {
		return noticeListView;
	}

	public void setNoticeListView(List<Noticeboard> noticeListView) {
		this.noticeListView = noticeListView;
	}

	public List<NoticeBoardDataBean> getNoticeList() {
		return noticeList;
	}

	public void setNoticeList(List<NoticeBoardDataBean> noticeList) {
		this.noticeList = noticeList;
	}

	public String getCreateStatus() {
		return createStatus;
	}

	public void setCreateStatus(String createStatus) {
		this.createStatus = createStatus;
	}

	public LibraryDataBean getLibraryDataBean() {
		return libraryDataBean;
	}

	public LoginAccess getLoginAccess() {
		return loginAccess;
	}

	public void setLoginAccess(LoginAccess loginAccess) {
		this.loginAccess = loginAccess;
	}

	public void setLibraryDataBean(LibraryDataBean libraryDataBean) {
		this.libraryDataBean = libraryDataBean;
	}

	public DualListModel<String> getAvailableBookList() {
		return availableBookList;
	}

	public void setAvailableBookList(DualListModel<String> availableBookList) {
		this.availableBookList = availableBookList;
	}

	public ClassTimeTableDataBean getClassTimeTableDataBean() {
		return classTimeTableDataBean;
	}

	public void setClassTimeTableDataBean(
			ClassTimeTableDataBean classTimeTableDataBean) {
		this.classTimeTableDataBean = classTimeTableDataBean;
	}

	HomeworkDatabean homeworkDatabean = new HomeworkDatabean();
	public HomeworkDatabean getHomeworkDatabean() {
		return homeworkDatabean;
	}

	public void setHomeworkDatabean(HomeworkDatabean homeworkDatabean) {
		this.homeworkDatabean = homeworkDatabean;
	}


	public List<ParentsDataBean> getParentList() {
		return parentList;
	}

	public void setParentList(List<ParentsDataBean> parentList) {
		this.parentList = parentList;
	}

	public int getExamTableid() {
		return examTableid;
	}

	public void setExamTableid(int examTableid) {
		this.examTableid = examTableid;
	}

	public TimeTableDataBean getTimeTableDataBean() {
		return timeTableDataBean;
	}

	public void setTimeTableDataBean(TimeTableDataBean timeTableDataBean) {
		this.timeTableDataBean = timeTableDataBean;
	}

	public List<String> getClassSubjectList() {
		return classSubjectList;
	}

	public void setClassSubjectList(List<String> classSubjectList) {
		this.classSubjectList = classSubjectList;
	}

	public List<SupProduct> getSubmenuList() {
		return submenuList;
	}

	public void setSubmenuList(List<SupProduct> submenuList) {
		this.submenuList = submenuList;
	}

	public List<String> getSclasslist() {
		return sclasslist;
	}

	public void setSclasslist(List<String> sclasslist) {
		this.sclasslist = sclasslist;
	}

	public List<String> getStudentIDList() {
		return studentIDList;
	}

	public void setStudentIDList(List<String> studentIDList) {
		this.studentIDList = studentIDList;
	}

	public List<String> getClassSectionList() {
		return classSectionList;
	}

	public void setClassSectionList(List<String> classSectionList) {
		this.classSectionList = classSectionList;
	}

	public List<ClassTimeTableDataBean> getClasstableList() {
		return classtableList;
	}

	public void setClasstableList(List<ClassTimeTableDataBean> classtableList) {
		this.classtableList = classtableList;
	}

	public SimpleDateFormat getFt() {
		return ft;
	}

	public void setFt(SimpleDateFormat ft) {
		this.ft = ft;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public DualListModel<String> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(DualListModel<String> subjectList) {
		this.subjectList = subjectList;
	}

	public DualListModel<String> getClassList() {
		return classList;
	}

	public void setClassList(DualListModel<String> classList) {
		this.classList = classList;
	}

	public String getStudClass() {
		return studClass;
	}

	public void setStudClass(String studClass) {
		this.studClass = studClass;
	}

	public List<TimeTableDataBean> getExamtableList() {
		return examtableList;
	}

	public void setExamtableList(List<TimeTableDataBean> examtableList) {
		this.examtableList = examtableList;
	}
	
	
	@GET
	public String getMethod()
	{
		logger.debug("inside get");
		String name="sms";
		return name;
	}
	

	@POST
	@Path("/rollid")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String name(String s) {
		logger.debug("Inside Get Method");
		smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
		int a	=smsservice.getStudentId(s); // run now no erro coming ..
		String a1=String.valueOf(a);
		logger.debug("Endpoint service " + a+a1);
		return a1;
	}
	
	@POST
	@Path("/robert")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String author(String s) {
		logger.debug("I am Robert .. This is My Testing Purpose");
		String ro = s;
		try {
			JSONObject ob=new JSONObject();
			ob.put("hello", "Robert");
			return ro+"---"+ob.getString("hello").toString();
		} catch (Exception e) {
			  logger.error("Exception -->"+e.getMessage());
			return "Exception";
		}

	}
	// Add New code for Login
	
	@POST
	@Path("/loginAuthorization")
	@Produces(MediaType.APPLICATION_JSON)
	public LoginAccess loginAuthorization(String json) throws JSONException, ParseException{
		System.out.println("Calling loginAuthorization Method"+json);
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(json);
		String user = element.getAsJsonObject().get("usename").getAsString();
		String pwd = element.getAsJsonObject().get("pwd").getAsString();
		
	    loginAccess.setUsername(user);
	    loginAccess.setUserpassword(pwd);
	    try{
	    	smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	    	String ss=smsservice.loginService(loginAccess);
	    	logger.debug(ss);
	    	if("Authorized".equalsIgnoreCase(loginAccess.getAuthentoicationStatus())){
	    		userMenus(loginAccess.getUsername());
	    	}
	    	
	    }catch(Exception e){
	    	e.printStackTrace();
	    	logger.error("inside exception",e);
	    	logger.warn(" exception - "+e);
	    	logger.error("Exception -->"+e.getMessage());
	    }
	    return loginAccess;
	
	}
	public SmsService getSmsservice() {
		return smsservice;
	}

	public void setSmsservice(SmsService smsservice) {
		this.smsservice = smsservice;
	}
	
	/*UDHAYA CODE BEGIN*/
	
/*UDHAYA CODE BEGIN*/
	 
	@POST
	@Path("/teacherReg")
	@Produces(MediaType.APPLICATION_JSON)
	public String teacherReg(String json)throws JSONException, IOException{
		logger.debug("inside teacher"); 
		String status="Fail";//String emailStatus="Fail";
		List<String> classList=new ArrayList<String>();
		List<String> subjectList=new ArrayList<String>();
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(json);
		String name = element.getAsJsonObject().get("name").getAsString();
        String lastname=element.getAsJsonObject().get("lastname").getAsString();
        String fathername=element.getAsJsonObject().get("fathername").getAsString();
        String mothername=element.getAsJsonObject().get("mothername").getAsString();
        String dob=element.getAsJsonObject().get("dob").getAsString(); 
        String gender=element.getAsJsonObject().get("gender").getAsString();
        String address=element.getAsJsonObject().get("address").getAsString();
        String address1=element.getAsJsonObject().get("address1").getAsString();
        String phonenumber=element.getAsJsonObject().get("phonenumber").getAsString();
        String mailid=element.getAsJsonObject().get("mailid").getAsString();
        String id=element.getAsJsonObject().get("id").getAsString();
        String subject=element.getAsJsonObject().get("subject").getAsString();
        String workinghour=element.getAsJsonObject().get("workingHour").getAsString();
        String personID=element.getAsJsonObject().get("personID").getAsString();
        String position=element.getAsJsonObject().get("position").getAsString();
        String qualification=element.getAsJsonObject().get("qualification").getAsString();
        String percentage=element.getAsJsonObject().get("percentage").getAsString();
        String passout=element.getAsJsonObject().get("passout").getAsString();
        String classsn=element.getAsJsonObject().get("classs").getAsString();
        String path=element.getAsJsonObject().get("path").getAsString();
        JSONObject js=new JSONObject(json);
        JSONArray jsonArray=js.getJSONArray("tClassSub"); 	
        logger.debug("registeration---------"+jsonArray.length());
		try{
			List<String> list = new ArrayList<String>();
			for (int i=0; i<jsonArray.length(); i++) {
			   list.add(jsonArray.getString(i));
			}
			logger.debug("class list size "+list.size());
			String[] classs = null;
			for(int j=0;j<list.size();j++) {
				classs=list.get(j).split(",");int c=0;
				logger.debug("class "+classs);
				for(String names:classs){
					if(c==0){ classList.add(names); c++;}
					else subjectList.add(names);
				}
			}
			if(jsonArray.length()==0){
				classList.add(classsn);
				subjectList.add(subject);
			}
			logger.debug(" class "+classList+" subject "+subjectList);
			teacherDataBean.setTeaFirstName(name);			
			teacherDataBean.setTeaAddress1(address);
			teacherDataBean.setTeaAddress2(address1);
			teacherDataBean.setTeaEduQualification(qualification);
			teacherDataBean.setTeaEmail(mailid);
			teacherDataBean.setTeaFatherName(fathername);
			teacherDataBean.setTeaMotherName(mothername);
			teacherDataBean.setTeaGender(gender);
			teacherDataBean.setTeaID(id);
			teacherDataBean.setTeaPercentage(percentage);
			teacherDataBean.setTeaYearOfPassing(passout);
			if(dob.equals("") || dob==null){
				teacherDataBean.setTeaDob(null);
			}else{
				teacherDataBean.setTeaDob(format.parse(dob));
			}
			teacherDataBean.setTeaPresentAddress(address);
			teacherDataBean.setTeaPermanentAddress(address1);
			teacherDataBean.setTeaLastName(lastname);
			teacherDataBean.setTeaSubjectTargetList(subjectList);
			teacherDataBean.setTeaClassTargetList(classList);	
			teacherDataBean.setTeaPhoneNo(phonenumber);
			teacherDataBean.setTeaPosition(position);
			teacherDataBean.setTeaWorkingHour(workinghour);
			if(path==null || path.equalsIgnoreCase(""))
	        	teacherDataBean.setTeafilePath(null);
	        else
	        	teacherDataBean.setTeafilePath(path);
			if (personID != null) 
			{
				logger.debug("teacher image path----"+teacherDataBean.getTeafilePath()+teacherDataBean.getSchoolID());
				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
				status=smsservice.insertTeacher(personID, teacherDataBean);
				teacherDataBean.setReturnStatus(status);
				System.out.println("status---------"+status);
				if(status.equalsIgnoreCase("Success")){
					List<TeacherDataBean> ImageListPath = new ArrayList<TeacherDataBean>();
					ImageListPath = smsservice.getImagePath(personID, teacherDataBean.getTeaID());
					logger.debug("size "+ImageListPath.size());
				/*	String pdfStatus=GeneratePdfMail.generatePdf(teacherDataBean,ImageListPath,personID);
					if(pdfStatus.equalsIgnoreCase("Success")){*/
						MailSendJDBC.teacherInsert(teacherDataBean,teacherDataBean.getSchoolName(),String.valueOf(teacherDataBean.getSchoolID()));
							teacherDataBean.setTeaFirstName(null);teacherDataBean.setTeaAddress1(null);
							teacherDataBean.setTeaAddress2(null);teacherDataBean.setTeaEduQualification(null);
							teacherDataBean.setTeaEmail(null);teacherDataBean.setTeaFatherName(null);
							teacherDataBean.setTeaMotherName(null);teacherDataBean.setTeaGender(null);
							teacherDataBean.setTeaID(null);teacherDataBean.setTeaPercentage(null);
							teacherDataBean.setTeaYearOfPassing(null);teacherDataBean.setTeaDob(null);
							teacherDataBean.setTeaPresentAddress(null);teacherDataBean.setTeaPermanentAddress(null);
							teacherDataBean.setTeaLastName(null);teacherDataBean.setTeaSubjectTargetList(null);
							teacherDataBean.setTeaClassTargetList(null);teacherDataBean.setTeaPhoneNo(null);
							teacherDataBean.setTeaPosition(null);teacherDataBean.setTeaWorkingHour(null);
					/*}*/
					teacherDataBean.setReturnStatus(status);
				}				
			}			
		}
		catch(Exception e){
			logger.error("inside exception ", e);
			logger.warn(" exception - "+e);
			  logger.error("Exception -->"+e.getMessage());
		}		
		return teacherDataBean.getReturnStatus();
	}	
	

	@POST
	@Path("/studentReg")
	@Produces(MediaType.TEXT_PLAIN)
	public String studentReg(String json)throws JSONException{
		logger.debug("inside student");
		String status="Failure";
		StudentDataBean studentDataBean=new StudentDataBean();
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(json);
		String name=element.getAsJsonObject().get("sfirstname").getAsString();
		String classs=element.getAsJsonObject().get("sclass").getAsString();
		String lastname=element.getAsJsonObject().get("slastname").getAsString();
		String fathername=element.getAsJsonObject().get("sfathername").getAsString();
		String mothername=element.getAsJsonObject().get("smothername").getAsString();
		String dob=element.getAsJsonObject().get("sdob").getAsString(); 
		String gender=element.getAsJsonObject().get("sgender").getAsString();
		String address=element.getAsJsonObject().get("saddress").getAsString();
		String address1=element.getAsJsonObject().get("saddress1").getAsString();
		String id=element.getAsJsonObject().get("sid").getAsString();
		String foccupation=element.getAsJsonObject().get("sfoccupation").getAsString(); 
		String moccupation=element.getAsJsonObject().get("smoccupation").getAsString();
		String personID=element.getAsJsonObject().get("spersonID").getAsString();
		String income=element.getAsJsonObject().get("sincome").getAsString();	
		String path=element.getAsJsonObject().get("path").getAsString();
		try{		
			studentDataBean.setStuFirstName(name);
			studentDataBean.setStuLastName(lastname);
			studentDataBean.setStuCls(classs);
			studentDataBean.setStuFatherName(fathername);
			studentDataBean.setStuMotherName(mothername);
			studentDataBean.setStuGender(gender);
			if(dob.equals("") || dob==null ){
				studentDataBean.setStuDob(null);
			}else{
				studentDataBean.setStuDob(format.parse(dob));
			}
			studentDataBean.setStuFatherIncome(income);
			studentDataBean.setStuFatherOccu(foccupation);
			studentDataBean.setStuMotherOccu(moccupation);
			studentDataBean.setStuPresentAddress(address);
			studentDataBean.setStuPermanentAddress(address1);
			studentDataBean.setStuRollNo(id);
			if(path==null || path.equalsIgnoreCase(""))
				studentDataBean.setStufilePath("");
			else
				studentDataBean.setStufilePath(path);
			if (personID != null) 
			{
				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
				status=smsservice.insertStudent(personID, studentDataBean);
				studentDataBean.setReturnStatus(status);
				if(studentDataBean.getReturnStatus().equalsIgnoreCase("Success")){
					studentDataBean.setStuFirstName(null);studentDataBean.setStuLastName(null);
					studentDataBean.setStuCls(null);studentDataBean.setStuFatherName(null);
					studentDataBean.setStuMotherName(null);studentDataBean.setStuGender(null);
					studentDataBean.setStuDob(null);studentDataBean.setStuFatherIncome(null);
					studentDataBean.setStuFatherOccu(null);studentDataBean.setStuMotherOccu(null);
					studentDataBean.setStuPresentAddress(null);studentDataBean.setStuPermanentAddress(null);
					studentDataBean.setStufilePath(null);studentDataBean.setStuRollNo(null);
				}		
			}			
		}
		catch(Exception e){
			logger.error("inside exception ", e);
			  logger.error("Exception -->"+e.getMessage());
		}		
		return studentDataBean.getReturnStatus();
	}	
	
	@POST
	@Path("/parentReg")	
	@Produces(MediaType.APPLICATION_JSON)
	public String parentReg(String json)throws JSONException{
		logger.debug("inside parent");
		List<ParentsDataBean> studentlist=new ArrayList<ParentsDataBean>();
		ParentsDataBean parentsDataBean=new ParentsDataBean();
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(json);
		String status="Fail";
		//String emailStatus="Fail";
		String name=element.getAsJsonObject().get("pfirstname").getAsString();
		String classs=element.getAsJsonObject().get("pclassname").getAsString();
		String lastname=element.getAsJsonObject().get("plastname").getAsString();
		String countrycode=element.getAsJsonObject().get("pcountrycode").getAsString();
		String phonenumber=element.getAsJsonObject().get("pphonenumber").getAsString();
		String mailid=element.getAsJsonObject().get("pmailid").getAsString();
		String id=element.getAsJsonObject().get("pid").getAsString();
		String personID=element.getAsJsonObject().get("ppersonID").getAsString();
		String relation=element.getAsJsonObject().get("prelation").getAsString();
		String studentID=element.getAsJsonObject().get("pstudentID").getAsString();
		JSONObject js=new JSONObject(json);
	    JSONArray jsonArray=js.getJSONArray("students");
	    String path=element.getAsJsonObject().get("path").getAsString();
		try{		
			for(int i=0;i<jsonArray.length();i++) {
				ParentsDataBean parentsbean=new ParentsDataBean();
				String studentIDs=(String) jsonArray.get(i);
				parentsbean.setParStudID(studentIDs); 
				studentlist.add(parentsbean);
				logger.debug("stduent "+studentIDs);
			}
			if(jsonArray.length()==0){
				logger.debug("zero size");
				ParentsDataBean parentsbean=new ParentsDataBean();
				parentsbean.setParStudID(studentID); 
				parentsbean.setParStuClass(classs);
				studentlist.add(parentsbean);
			}
			parentsDataBean.setParentdetails(studentlist);
			logger.debug("size "+parentsDataBean.getParentdetails().get(0).getParStudID());
			parentsDataBean.setParFirstName(name);
			parentsDataBean.setParLastName(lastname);
			parentsDataBean.setParPhoneNo(phonenumber);
			parentsDataBean.setParEmail(mailid);
			parentsDataBean.setParParentID(id);
			parentsDataBean.setParentRelation(relation);
			parentsDataBean.setCountryCode(countrycode);
			if(path==null || path.equals(""))
				parentsDataBean.setParfilePath("");
			else
			parentsDataBean.setParfilePath(path);
			if (personID != null) 
			{
				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
				status=smsservice.insertParents(personID, parentsDataBean);
				parentsDataBean.setReturnStatus(status);
				if (status.equalsIgnoreCase("Success")) {
					parentList = smsservice.getParentDetilsList(personID, parentsDataBean);
					logger.debug("parent size "+parentList.size());
					if(parentList.size()>0){
						List<ParentsDataBean> ImageListPath = new ArrayList<ParentsDataBean>();
						ImageListPath = smsservice.getImagePath2(personID, parentsDataBean.getParParentID());
						logger.debug("image size "+ImageListPath.size());
						/*String pdfStatus=GeneratePdfMail.generatePdfParent(parentsDataBean,ImageListPath,personID);
						if(pdfStatus.equalsIgnoreCase("Success")){*/
							MailSendJDBC.parentInsert(parentsDataBean,parentsDataBean.getSchoolName(),String.valueOf(parentDataBean.getSchoolID()));
							studentlist.add(null);parentsDataBean.setParentdetails(null);
							parentsDataBean.setParFirstName(null);parentsDataBean.setParLastName(null);
							parentsDataBean.setParPhoneNo(null);parentsDataBean.setParEmail(null);
							parentsDataBean.setParParentID(null);parentsDataBean.setParfilePath(null);
							parentsDataBean.setParentRelation(null);parentsDataBean.setCountryCode(null); 
						//	}
					}						
				}					
			}			
		}
		catch(Exception e){
			logger.error("inside exception "+ e.getMessage());
			//  logger.error("Exception -->"+e.getMessage());
		}		
		return parentsDataBean.getReturnStatus();
	}
	
	@POST
	@Path("/staffReg")
	@Produces(MediaType.APPLICATION_JSON)
	public String staffReg(String json)throws JSONException{
		logger.debug("inside staff");
		String status="Fail";
		//String emailStatus="Fail";
		StaffDataBean staffDataBean=new StaffDataBean();
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(json);
		String id=element.getAsJsonObject().get("sfid").getAsString();
		String name=element.getAsJsonObject().get("sffirstname").getAsString();
		String lastname=element.getAsJsonObject().get("sflastname").getAsString(); 
		String gender=element.getAsJsonObject().get("sfgender").getAsString();
		String mailid=element.getAsJsonObject().get("sfmailid").getAsString();
		String countrycode=element.getAsJsonObject().get("sfcountrycode").getAsString();
		String phoneno=element.getAsJsonObject().get("sfphoneno").getAsString();
		String personID=element.getAsJsonObject().get("personID").getAsString();
		String path=element.getAsJsonObject().get("path").getAsString();
		try{
			staffDataBean.setStaEmail(mailid);
			staffDataBean.setStaFirstName(name);
			staffDataBean.setStaGender(gender);
			staffDataBean.setStaLastName(lastname);
			staffDataBean.setStaPhoneNo(phoneno);
			staffDataBean.setCountryCode(countrycode); 
			staffDataBean.setStaStaffID(id);
			if(path==null || path.equals("")){
				staffDataBean.setStafilePath("");
			}else{
				staffDataBean.setStafilePath(path);
			}
			if (personID != null) 
			{
				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
				status=smsservice.insertStaff2(personID, staffDataBean);
				staffDataBean.setReturnStatus(status);
				if (status.equalsIgnoreCase("Success")) {
					//List<StaffDataBean> ImageListPath = new ArrayList<StaffDataBean>();
					ImageListPath = smsservice.getImagePath3(personID, staffDataBean.getStaStaffID());
					/*String pdfStatus=GeneratePdfMail.generatePdfStaff(staffDataBean,ImageListPath,personID);					
					if(pdfStatus.equalsIgnoreCase("Success")){	*/	
						MailSendJDBC.staffInsert(staffDataBean,staffDataBean.getName(),String.valueOf(staffDataBean.getSchoolID()));
						staffDataBean.setStaEmail(null);staffDataBean.setStaFirstName(null);
						staffDataBean.setStaGender(null);staffDataBean.setStaLastName(null);
						staffDataBean.setStaPhoneNo(null);staffDataBean.setStaStaffID(null);
						staffDataBean.setStafilePath(null);staffDataBean.setCountryCode(null); 
						//staffDataBean.setReturnStatus(pdfStatus);
					}else{
						staffDataBean.setReturnStatus(status);
					}
				//	}					
			}	
		}
		catch(Exception e){
			  logger.error("Exception -->"+e.getMessage());
		}
		return staffDataBean.getReturnStatus();		
	}
	
	@POST
	@Path("/librarianReg")
	@Produces(MediaType.APPLICATION_JSON)
	public String librarianReg(String json)throws JSONException{
		logger.debug("inside librarian");
		String status="Fail";//String emailStatus="Fail";
		LibrarianDataBean librarianDataBean=new LibrarianDataBean();
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(json);
		String id=element.getAsJsonObject().get("lid").getAsString();
		String name=element.getAsJsonObject().get("lfirstname").getAsString();
		String lastname=element.getAsJsonObject().get("llastname").getAsString();
		String gender=element.getAsJsonObject().get("lgender").getAsString();
		String mailid=element.getAsJsonObject().get("lmailid").getAsString();
		String countrycode=element.getAsJsonObject().get("lcountrycode").getAsString();
		String phoneno=element.getAsJsonObject().get("lphoneno").getAsString();
		String personID=element.getAsJsonObject().get("personID").getAsString();
		String path=element.getAsJsonObject().get("path").getAsString();
		try{
			librarianDataBean.setLibEmail(mailid);
			librarianDataBean.setLibFirstName(name);
			librarianDataBean.setLibGender(gender);
			librarianDataBean.setLibLastName(lastname);
			librarianDataBean.setCountryCode(countrycode); 	
			librarianDataBean.setLibPhoneNo(phoneno);
			librarianDataBean.setLibID(id);
			if(path=="" || path==null){
				librarianDataBean.setLibfilePath("");
			}else{
				librarianDataBean.setLibfilePath(path);
			}
			if (personID != null) 
			{
				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
				status=smsservice.insertLibrarian(personID, librarianDataBean);
				logger.debug("status -- "+status);
				librarianDataBean.setReturnStatus(status);
				if (status.equalsIgnoreCase("Success")) {
					//List<LibrarianDataBean> ImageListPath = new ArrayList<LibrarianDataBean>();
					//ImageListPath = smsservice.getImagePath5(personID, librarianDataBean.getLibID());
				/*String pdfStatus=GeneratePdfMail.generatePdfLibrarian(librarianDataBean,ImageListPath,personID);
					librarianDataBean.setReturnStatus(pdfStatus);
				if(pdfStatus.equalsIgnoreCase("Success")){*/
						MailSendJDBC.librarianInsert(librarianDataBean,librarianDataBean.getSchoolName(),String.valueOf(librarianDataBean.getSchoolID()));
						librarianDataBean.setLibEmail(null);librarianDataBean.setLibFirstName(null);
						librarianDataBean.setLibGender(null);librarianDataBean.setLibLastName(null);
						librarianDataBean.setLibPhoneNo(null);librarianDataBean.setLibID(null);
						librarianDataBean.setLibfilePath(null);librarianDataBean.setCountryCode(null); 
					//}					
				}
			}	
		}
		catch(Exception e){
			logger.warn("inside exception"+e.getMessage());
		}
		return librarianDataBean.getReturnStatus();	
	}	
	  
	  @GET
	  @Path("/getClassListParent/{id}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getClassListParent(@PathParam("id") String id) throws JSONException
	  {
		  logger.debug("inside slass list for parent");
		   Gson gson = new Gson();
		   String personID=id;
		   try{
			    if(personID != null){
				     smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
				     classSectionList=smsservice.getClassSection(personID);	     
				     logger.debug("class list size - "+classSectionList.size());
				     classSubjectList=smsservice.getSubjectList(personID);
				     Collections.sort(classSectionList);
				     Collections.sort(classSubjectList);
			    }
		   }
		   catch(Exception e){
		    logger.warn("inside exception"+e.getMessage());
		   }
		   return gson.toJson(classSectionList);
	  }
	  
	  @GET
	  @Path("/getSubjectListTeacher/{id}/{className}/{classSection}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getSubjectListTeacher(@PathParam("id") String id,@PathParam("className") String className
			  ,@PathParam("classSection") String classSection) throws JSONException
	  {
		   logger.debug("inside subject list ");
		   Gson gson = new Gson();
		   String personID=id;
		   try{
			    if(personID != null){
			    	String classname=className+"/"+classSection;
			    	logger.debug("class "+className+"/"+classSection);
				     smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
				     classSubjectList=smsservice.getSubjectListTeacher(personID,classname);
				     logger.debug("subject list size - "+classSubjectList.size());	     
			    }
		   }
		   catch(Exception e){
		    logger.warn("inside exception"+e.getMessage());
		   }
		   return gson.toJson(classSubjectList);
	  }
	  
	  @GET
	  @Path("/getStudentParent/{id}/{classname}/{classSection}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getStudentParent(@PathParam("id") String id,@PathParam("classname") String classname,
			  @PathParam("classSection") String classSection) throws JSONException
	  {
		   logger.debug("inside student list for parent");
		   Gson gson = new Gson();
		   String personID=id;
		   String stuclass=classname+"/"+classSection;
		   try{
			    if(personID != null){
				     smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
				     studentIDList=smsservice.getStudentRollNumber(personID, stuclass);    
				     logger.debug("student id size - "+studentIDList.size());
			    }
		   }
		   catch(Exception e){
		      logger.error("Exception -->"+e.getMessage());
		   }
		   return gson.toJson(studentIDList);
	  } 
	    

	  @GET
	  @Path("/attendanceClass/{id}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String attendanceClass(@PathParam("id") String id){
		  String personID=id;
		  Gson gson=new Gson();
		  try{
			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
		    if (personID != null) {
		    	classSectionList = smsservice.getClassListAtt(personID);
		    	Collections.sort(classSectionList);
		    	logger.debug("class size "+classSectionList);
		    }			 
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		  return gson.toJson(classSectionList);
	  }
	  @GET
	  @Path("/teacherclassview/{id}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String teacherclassview(@PathParam("id") String id){
		  String personID=id;
		  Gson gson=new Gson();
		  List<String>teacherclasslist=new ArrayList<String>();
		  try{
			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
		    if (personID != null) {
		    	teacherclasslist = smsservice.getTeaClasslist(personID);
		    	Collections.sort(teacherclasslist);
		    }			 
		  }
		  catch(Exception e){
			   e.printStackTrace();
		  }
		  return gson.toJson(teacherclasslist);
	  }
	  @GET
	  @Path("/classAttendance/{id}/{className}/{classSection}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String classAttendance(@PathParam("id") String id,@PathParam("className") String classname 
			  ,@PathParam("classSection") String classSection){
		  AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
		  Gson gson=new Gson();
		  String personID=id;
		  try{
			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
			if (personID != null) {
				attendanceDataBean.setClassname(classname+"/"+classSection);
				smsservice.studentList(personID, attendanceDataBean);
				logger.debug("student list size "+attendanceDataBean.getStudentList().size());
		    }			 
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		  return gson.toJson(attendanceDataBean.getStudentList());
	  }
	  
	  @GET 
	  @Path("/takeAttendance/{id}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String takeAttendance(@PathParam("id") String id)throws JSONException{
		  System.out.println("inside attendance"+id);
		  AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
		 // SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
		  Calendar now = Calendar.getInstance();
		  Gson gson=new Gson();
		  attendanceDataBean.setStudentList(null);
		 // JsonParser jsonParser = new JsonParser();
		  //JsonElement element = jsonParser.parse(json);
		  String personID=id;
		  /*String status=element.getAsJsonObject().get("status").getAsString();
		  String studentID=element.getAsJsonObject().get("studentID").getAsString();
		  String studentName=element.getAsJsonObject().get("studentName").getAsString();
		  String date=element.getAsJsonObject().get("date").getAsString();
		  String classname=element.getAsJsonObject().get("className").getAsString();
		  logger.debug("student id "+studentID);*/
		  try{
			  if (personID != null) {
				attendanceDataBean.setClassname("");
				attendanceDataBean.setSection("");
				attendanceDataBean.setStudentList(new ArrayList<AttendanceDataBean>());
			  	/*attendanceDataBean.setStatus(status);
				attendanceDataBean.setStudentName(studentName);
				attendanceDataBean.setStudentID(studentID);
				attendanceDataBean.setDate(ft.parse(date));
				attendanceDataBean.setClassname(classname);*/
				attendanceDataBean.setMonthyear("" + (now.get(Calendar.MONTH) + 1));
				attendanceDataBean.setYear("" + now.get(Calendar.YEAR));
				logger.debug("date "+attendanceDataBean.getDate());
				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");				
				smsservice.takeattendance(personID, attendanceDataBean);
				System.out.println("student size -- " + attendanceDataBean.getStudentList());
				/*if (attendanceDataBean.getStatus().equals("Absent")) {
					MailSendJDBC.attendanceTake(attendanceDataBean,attendanceDataBean.getSchoolName(),attendanceDataBean.getSchoolID());
					attendanceDataBean.setReturnStatus("Success");
				}else attendanceDataBean.setReturnStatus("Success");*/
			}
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
			 //   logger.error("Exception -->"+e.getMessage());
		  }
		  return gson.toJson(attendanceDataBean);
	  }
	  
	  @POST
	  @Path("/attendanceNewSchool")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String attendanceNewSchool(String json)throws JSONException{
		  JsonParser jsonParser = new JsonParser();
		  JsonElement element = jsonParser.parse(json);	  
		  Gson gson=new Gson();
		 // SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
		  try{
			  String personID=element.getAsJsonObject().get("personID").getAsString();
			  String classSection=element.getAsJsonObject().get("classSection").getAsString();
			  String period=element.getAsJsonObject().get("period").getAsString();
			  String date=element.getAsJsonObject().get("date").getAsString();
			  if(personID!=null){
				  attendanceDataBean.setClassname(classSection);
				  attendanceDataBean.setPeriod(period);
				  attendanceDataBean.setDate(ft.parse(date));
				  smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
				  smsservice.takeattendanceNew(personID,attendanceDataBean);
			  }
		  }catch(Exception e){
			  //  logger.error("Exception -->"+e.getMessage());
			  logger.error("Exception -->"+e.getMessage());
		  }
		  return gson.toJson(attendanceDataBean);
	  }
	  
	  
	  // Josini
	  
	/*  @POST
	  @Path("/attendancesave")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String attendancesave(String json) throws JSONException{
		  logger.info("Inside attendancesave Method");
		  String status="";
		  JsonParser jsonParser =new JsonParser();
		  JsonElement element = jsonParser.parse(json);
		  String personID=element.getAsJsonObject().get("personID").getAsString();
		  String schoolID=element.getAsJsonObject().get("schoolID").getAsString();
		  String classSection=element.getAsJsonObject().get("classSection").getAsString(); 
		  String period=element.getAsJsonObject().get("period").getAsString();
		  String date=element.getAsJsonObject().get("date").getAsString(); 
		  JSONObject js=new JSONObject(json);
		  JSONArray jsonArray=null;
		  JSONArray jsonArray1=null;
		  try{
			   attendanceDataBean.setClassname(classSection);
			   attendanceDataBean.setPeriod(period);
			   attendanceDataBean.setDate(ft.parse(date));
			   // first JSONArray
			   logger.info("Class Name -->"+classSection);
			   jsonArray=js.getJSONArray("myTableArray");
			   List<String> attendancelist = new ArrayList<String>();
			   logger.info("JsonArray length====="+jsonArray.length());
			   for(int i=0;i<jsonArray.length();i++){ 
				   attendancelist.add(jsonArray.getString(i));
				   logger.info("array list----"+jsonArray.getString(i)); 
			   }
			    String[] names=null; 
			    jsonArray1=js.getJSONArray("statusArray");
			    
			    for(int m=0;m<attendancelist.size();m++){
			    	  attendanceDataBean = new AttendanceDataBean();
			    	  names=attendancelist.get(m).split(",");
			    	  System.out.println("Date-------------->"+names[1]); 
			    	  System.out.println("Student ID--------->"+names[2]); 
			    	  System.out.println("Student Name--------->"+names[3]); // single  ID				    	  
			    	  attendanceDataBean.setSelectedmonth(names[1]); 
		        	  attendanceDataBean.setStudentID(names[2]);
		        	  attendanceDataBean.setStudentName(names[3]);
		              String temp1 = jsonArray1.getString(m).replace("[", "");
		              String temp2 = temp1.replace("]", "");
					  attendanceDataBean.setStatus1(temp2);			    	  
					  attendanceDataBean.setClassname(classSection);
					  attendanceDataBean.setPeriod(period);
					  attendanceDataBean.setDate(ft.parse(date));
					   
		              //System.out.println("Student Status --------->"+temp2); 
		        	 // System.out.println("After set Date in attendancedatabean------------>"+attendanceDataBean.getSelectedmonth()); 
		        	//  System.out.println("After set Student ID in attendancedatabean------------>"+attendanceDataBean.getStudentID()); 
		        	 // System.out.println("After set Student Name in attendancedatabean------------>"+attendanceDataBean.getStudentName());			    	 

			    	  stuattendancelist.add(attendanceDataBean);	
			    	
			    }
			    
			    for (int i = 0; i < stuattendancelist.size(); i++) {
					attendanceDataBean.setStudentID(stuattendancelist.get(i).getStudentID());
					attendanceDataBean.setDate(stuattendancelist.get(i).getDate()); 
					attendanceDataBean.setStatus(stuattendancelist.get(i).getStatus1());
					System.out.println("------- Student ID ---------"+stuattendancelist.get(i).getStudentID());
					System.out.println("------- Student Name ---------"+stuattendancelist.get(i).getStudentName());
					System.out.println("------- Date ---------"+stuattendancelist.get(i).getDate());
					smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
					//status = smsservice.insertAttandance1(personID,schoolID,stuattendancelist);
					status=smsservice.insertAttandance(personID,schoolID,attendanceDataBean);
					System.out.println("----- After insertAttandance Status ------"+status); 
			    }
			    System.out.println("------- Student Id list ---------"+stuattendancelist.get(0).getStudentID());
				  System.out.println("------- Student Id list ---------"+stuattendancelist.get(0).getStudentName());
				  smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
				  status = smsservice.insertAttandance1(personID,schoolID,stuattendancelist); 
				  System.out.println("----- Student ID ------"+stuattendancelist.get(0).getStudentID());
				  System.out.println("----- Student Name ------"+stuattendancelist.get(0).getStudentName());
				  System.out.println("----- After insertAttandance Status ------"+status);
			    System.out.println("----- EndpointService Status ------"+status); 
				attendanceDataBean.setReturnStatus(status);
			  
			    //second JSONArray
			       jsonArray1=js.getJSONArray("statusArray");
				   List<String> attendancelist1 = new ArrayList<String>();
				   logger.info("JsonArray1 length====="+jsonArray1.length());
				   for(int i=0;i<jsonArray1.length();i++){ 
					   attendancelist1.add(jsonArray1.getString(i));
					   logger.info("array list1----"+jsonArray1.getString(i)); 
				   }
				    String[] statusarray=null; 
				    for(int n=0;n<attendancelist1.size();n++){
				    	//statusarray=attendancelist1.get(n);
				    	  logger.info("Student Status"+statusarray[0]); // Student ID		       
				    	  attendanceDataBean.setStatus1(names[0]); 
			        	  logger.info("After set Status in attendancedatabean------------>"+attendanceDataBean.getStatus1()); 
				    	  stuattendancelist.add(attendanceDataBean);
			    jsonArray1=js.getJSONArray("statusArray");
			    List<String> namelist = new ArrayList<String>();
				   logger.info("jsonArray1 length====="+jsonArray1.length());
				   for(int i=0;i<jsonArray1.length();i++){ 
					   namelist.add(jsonArray1.getString(i));
				          AttendanceDataBean attendanceDataBean=new AttendanceDataBean();
					   logger.info("array list1----"+jsonArray1.getString(i)); 
			            String temp = jsonArray1.getString(i).replace("[", "");
					   logger.info("Student Status --------->"+temp); 
					   attendanceDataBean.setStatus1(temp);
				          stuattendancelist.add(attendanceDataBean);

					   // [present]
					   // Absent 
					   
				   }

				    String[] names1=null; 
				    for(int m=0;m<namelist.size();m++){
				    	
				    }
				    

				for (int i = 0; i < stuattendancelist.size(); i++) {
					logger.info("StudentID ----------->"+attendanceDataBean.getStudentID());
					logger.info("Date ----------->"+attendanceDataBean.getDate());
					logger.info("Student Status ----------->"+attendanceDataBean.getStatus1());

					attendanceDataBean.setStudentID(stuattendancelist.get(i).getStudentID());
					attendanceDataBean.setDate(stuattendancelist.get(i).getDate()); 
					attendanceDataBean.setStatus(stuattendancelist.get(i).getStatus1());
					//status = smsservice.insertAttandance(personID,schoolID,attendanceDataBean);
				
		  
				    }
				
		     }catch(Exception e){
		        logger.error("Exception -->"+e.getMessage());
		     }
		     return attendanceDataBean.getReturnStatus();
	 	}*/
	  
	  /*@POST
	  @Path("/attendancesave")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String attendancesave(String json)throws JSONException{
		  JsonParser jsonParser = new JsonParser();
		  JsonElement element = jsonParser.parse(json);	
		  Calendar now = Calendar.getInstance();
		  try {  
			  String personID=element.getAsJsonObject().get("personID").getAsString(); 
			  String classSection=element.getAsJsonObject().get("classSection").getAsString(); 
			  String period=element.getAsJsonObject().get("period").getAsString();
			  String date=element.getAsJsonObject().get("date").getAsString(); 
			  String schoolID=element.getAsJsonObject().get("schoolID").getAsString(); 
			  if(personID!=null){
				  attendanceDataBean.setClassname(classSection);
				  attendanceDataBean.setPeriod(period);
				  attendanceDataBean.setDate(ft.parse(date));
				  smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
				  smsservice.takeattendanceNew(personID,attendanceDataBean);
				  attendanceDataBean.setMonthyear("" + (now.get(Calendar.MONTH) + 1));
				  attendanceDataBean.setYear("" + now.get(Calendar.YEAR));
				  for (int i = 0; i < attendanceDataBean.getStudentList().size(); i++) {
					attendanceDataBean.setStudentID(attendanceDataBean.getStudentList().get(i).getStudentID());
					attendanceDataBean.setDate(attendanceDataBean.getStudentList().get(i).getDate());
					attendanceDataBean.setStatus(attendanceDataBean.getStudentList().get(i).getStatus1());
					String status = smsservice.insertAttandance(personID,schoolID,attendanceDataBean);
					attendanceDataBean.setReturnStatus(status);
				}
			  }
		  }catch(Exception e){
			   logger.error("Exception -->"+e.getMessage());
		  }
		  return attendanceDataBean.getReturnStatus();
	  }
	  */
	  
	  @POST
	  @Path("/attendancesave")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String attendancesave(String json) throws JSONException{
		  logger.info("-------------------------- Inside attendancesave Method -------------------------");
		  String status="";
		  JsonParser jsonParser =new JsonParser();
		  JsonElement element = jsonParser.parse(json);
		  String personID=element.getAsJsonObject().get("personID").getAsString();
		  String schoolID=element.getAsJsonObject().get("schoolID").getAsString();
		  String classSection=element.getAsJsonObject().get("classSection").getAsString(); 
		  String period=element.getAsJsonObject().get("period").getAsString();
		  String date=element.getAsJsonObject().get("date").getAsString(); 
		  JSONObject js=new JSONObject(json);
		  JSONArray jsonArray=null;
		  JSONArray jsonArray1=null;
		  try{
			   attendanceDataBean.setClassname(classSection);
			   attendanceDataBean.setPeriod(period);
			   attendanceDataBean.setDate(ft.parse(date));
			   // first JSONArray
			   logger.info("Class Name -->"+classSection);
			   jsonArray=js.getJSONArray("myTableArray");
			   List<String> attendancelist = new ArrayList<String>();
			   logger.info("JsonArray length====="+jsonArray.length());
			   for(int i=0;i<jsonArray.length();i++){ 
				   attendancelist.add(jsonArray.getString(i));
				   logger.info("array list----"+jsonArray.getString(i)); 
			   }
			    String[] names=null; 
			    jsonArray1=js.getJSONArray("statusArray");
			    
			    for(int m=0;m<attendancelist.size();m++){
			    	  attendanceDataBean = new AttendanceDataBean();
			    	  names=attendancelist.get(m).split(",");
			    	  logger.info("Date-------------->"+names[1]); 
			    	  logger.info("Student ID--------->"+names[2]); 
			    	  logger.info("Student Name--------->"+names[3]); // single  ID				    	  
			    	  attendanceDataBean.setSelectedmonth(names[1]); 
		        	  attendanceDataBean.setStudentID(names[2]);
		        	  attendanceDataBean.setStudentName(names[3]);
		              String temp1 = jsonArray1.getString(m).replace("[", "");
		              String temp2 = temp1.replace("]", "");
					  attendanceDataBean.setStatus1(temp2);			    	  
					  attendanceDataBean.setClassname(classSection);
					  attendanceDataBean.setPeriod(period);
					  attendanceDataBean.setDate(ft.parse(date));
			    	  stuattendancelist.add(attendanceDataBean);	
			    	
			    }
			    
			    /*for (int i = 0; i < stuattendancelist.size(); i++) {
					attendanceDataBean.setStudentID(stuattendancelist.get(i).getStudentID());
					attendanceDataBean.setDate(stuattendancelist.get(i).getDate()); 
					attendanceDataBean.setStatus(stuattendancelist.get(i).getStatus1());
					System.out.println("------- Student ID ---------"+stuattendancelist.get(i).getStudentID());
					System.out.println("------- Student Name ---------"+stuattendancelist.get(i).getStudentName());
					System.out.println("------- Date ---------"+stuattendancelist.get(i).getDate());
					smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
					//status = smsservice.insertAttandance1(personID,schoolID,stuattendancelist);
					status=smsservice.insertAttandance(personID,schoolID,attendanceDataBean);
					System.out.println("----- After insertAttandance Status ------"+status); 
			    }*/
			    logger.info("------- Student Id list ---------"+stuattendancelist.get(0).getStudentID());
			    logger.info("------- Student Id list ---------"+stuattendancelist.get(0).getStudentName());
				  smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
				  
				  for( AttendanceDataBean value : stuattendancelist ) {
					  logger.info("ENd ID -->"+value.getStudentID() + " Name -->" +value.getStudentID()+ 
								"Status -->"+value.getStatus1() + " Class name -->"+value.getClassname()+
								" Class Section"+value.getSection()+" Date -->"+value.getDate()) ;
				  }
				  /*stuattendancelist.forEach((AttendanceDataBean value) -> 
					
				 
					logger.info("ENd ID -->"+value.getStudentID() + " Name -->" +value.getStudentID()+ 
							"Status -->"+value.getStatus() + "Class name -->"+value.getClassname()+
							"Class Section"+value.getSection()+"Date -->"+value.getDate())

					
							);
				  */
				  status = smsservice.insertAttandance1(personID,schoolID,stuattendancelist); 
				  logger.info("----- Student ID ------"+stuattendancelist.get(0).getStudentID());
				  logger.info("----- Student Name ------"+stuattendancelist.get(0).getStudentName());
				  logger.info("----- After insertAttandance Status ------"+status);
				  logger.info("----- EndpointService Status ------"+status); 
				attendanceDataBean.setReturnStatus(status);	
		     }catch(Exception e){
		        logger.error("Exception -->"+e.getMessage());
		     }
		     return attendanceDataBean.getReturnStatus();
	 	}
	  @POST
	  @Path("/takeAttendancesave")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String takeAttendancesave(String json)throws JSONException{
		  logger.debug("inside attendance");
		  AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
		 // SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
		  Calendar now = Calendar.getInstance();
		  JsonParser jsonParser = new JsonParser();
		  JsonElement element = jsonParser.parse(json);
		  String status=element.getAsJsonObject().get("status").getAsString();
		  String studentID=element.getAsJsonObject().get("studentID").getAsString();
		  String studentName=element.getAsJsonObject().get("studentName").getAsString();
		  String date=element.getAsJsonObject().get("date").getAsString();
		  String personID=element.getAsJsonObject().get("personID").getAsString();
		  String classname=element.getAsJsonObject().get("className").getAsString();
		  String period=element.getAsJsonObject().get("period").getAsString();
		  logger.debug("student id "+studentID);
		  try{
			  if (personID != null) {
				  if(period!=""){
					  attendanceDataBean.setPeriod(period);
				  }
			  	attendanceDataBean.setStatus(status);
				attendanceDataBean.setStudentName(studentName);
				attendanceDataBean.setStudentID(studentID);
				attendanceDataBean.setDate(ft.parse(date));
				attendanceDataBean.setClassname(classname);
				attendanceDataBean.setMonthyear("" + (now.get(Calendar.MONTH) + 1));
				attendanceDataBean.setYear("" + now.get(Calendar.YEAR));
				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");	
				smsservice.attendanceStatusNew(personID, attendanceDataBean);
				attendanceDataBean.setReturnStatus("Success");
				/*if (attendanceDataBean.getStatus().equals("Absent")) {
					MailSendJDBC.attendanceTake(attendanceDataBean,attendanceDataBean.getSchoolName(),attendanceDataBean.getSchoolID());
					attendanceDataBean.setReturnStatus("Success");
				}else attendanceDataBean.setReturnStatus("Success");*/
			}
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		  return attendanceDataBean.getReturnStatus();
	  }
	  
	  @GET
	  @Path("/attendanceClassView/{id}/{role}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String attendanceClassView(@PathParam("id") String id,@PathParam("role") String role){
		  logger.debug("inside attendance class  ");
		  String personID=id;Gson gson=new Gson();
		  AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
		  try{
			  if (personID != null) {
				  smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");		
				  if(role.equalsIgnoreCase("Parent")) {
						studentIDList = smsservice.parentAttlist(personID, attendanceDataBean);
						Collections.sort(studentIDList);
						logger.debug("students "+studentIDList);
						return gson.toJson(studentIDList);
					  
				  }else if(role.equalsIgnoreCase("Teacher") || role.equalsIgnoreCase("Admin")){
					  classSectionList = smsservice.ClassListAttView(personID, attendanceDataBean);
					  Collections.sort(classSectionList);
					  return gson.toJson(classSectionList);
				  }
			  }			 
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		return "";		  
	  }
	  
	  @POST
	  @Path("/classPeriod")
	  @Produces(MediaType.APPLICATION_JSON)	  
	  public String classPeriod(String json) throws JSONException{
		  logger.debug("inside attendance class period ");
		  
		  SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		  Gson gson=new Gson();
		  JsonParser jsonParser = new JsonParser();
		  JsonElement element = jsonParser.parse(json);
		  String className=element.getAsJsonObject().get("className").getAsString();
		  String dates=element.getAsJsonObject().get("dates").getAsString();
		  String personID=element.getAsJsonObject().get("personID").getAsString();
		  AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
		  try{
			  if (personID != null) {
				  smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");	
				  attendanceDataBean.setClassname(className);
				  attendanceDataBean.setDate(ft.parse(dates));
				  smsservice.classPeriod(attendanceDataBean,personID);
			  }			 
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		  return gson.toJson(attendanceDataBean.getPeriods());
	  }	  
	  
	  @POST
	  @Path("/attendanceView")
	  @Produces(MediaType.APPLICATION_JSON)	
	  public String attendanceView(String json)throws JSONException{
		  logger.debug("inside attendance class views ");		  
		  SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		  Gson gson=new Gson();
		  JsonParser jsonParser = new JsonParser();
		  JsonElement element = jsonParser.parse(json);
		  String className=element.getAsJsonObject().get("className").getAsString();
		  String dates=element.getAsJsonObject().get("dates").getAsString();
		  String period=element.getAsJsonObject().get("period").getAsString();
		  String personID=element.getAsJsonObject().get("personID").getAsString();
		  AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
		  try{
			  if (personID != null) {
				  smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");	
				  attendanceDataBean.setClassname(className);
				  attendanceDataBean.setPeriod(period);
				  attendanceDataBean.setDate(ft.parse(dates));
				  smsservice.attendanceView(personID, attendanceDataBean);
			  }			 
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		  return gson.toJson(attendanceDataBean.getStudentList());
	  }
	  
	  @POST
	  @Path("/attendanceUpdate")	  
	  @Produces(MediaType.APPLICATION_JSON)
	  public String attendanceUpdate(String json)throws JSONException{
		  logger.info("---- Inside attendanceUpdate method ----");
		  List<AttendanceDataBean> attendancedata=new ArrayList<AttendanceDataBean>();
		  AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
		 // SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		  Calendar now = Calendar.getInstance();
		  JsonParser jsonParser = new JsonParser();
		  JsonElement element = jsonParser.parse(json);
		  String status=element.getAsJsonObject().get("status").getAsString();
		  String studentID=element.getAsJsonObject().get("studentID").getAsString();
		  String studentName=element.getAsJsonObject().get("studentName").getAsString(); 
		  String date=element.getAsJsonObject().get("date").getAsString();
		  String personID=element.getAsJsonObject().get("personID").getAsString();
		  String period=element.getAsJsonObject().get("period").getAsString();
		  String className=element.getAsJsonObject().get("className").getAsString();
		  String percentage=element.getAsJsonObject().get("percentage").getAsString();
		  String time=element.getAsJsonObject().get("time").getAsString();
		  try{
			  logger.info("---- Inside attendanceUpdate try block ----");
			  if (personID != null) {
				 attendanceDataBean.setSno(1);
			  	attendanceDataBean.setStatus(status);
				attendanceDataBean.setStudentName(studentName);
				attendanceDataBean.setStudentID(studentID);
				attendanceDataBean.setDate(ft.parse(date));
				attendanceDataBean.setMonthyear("" + (now.get(Calendar.MONTH) + 1));
				attendanceDataBean.setYear("" + now.get(Calendar.YEAR));
				attendanceDataBean.setPeriod(period);
				attendanceDataBean.setClassname(className);
				AttendanceDataBean list = new AttendanceDataBean();
				list.setStudentName(studentName);
				list.setStudentID(studentID);
				list.setStatus(status);
				list.setPercentage(percentage);
				list.setDate(ft.parse(date));
				list.setTime1(time);
				list.setSno(1);
				attendancedata.add(list);
				attendanceDataBean.setStudentList(attendancedata);
				logger.info("Before calling updateAttendance method");
				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");				
				String Status=smsservice.updateAttendance(attendanceDataBean, personID);
				logger.info("Successfully called updateAttendance method");
				logger.debug("Status -->  "+Status);
				if (Status.equalsIgnoreCase("success")) {					
					String mailStatus="";
					if (attendanceDataBean.getStatus().equals("Absent")) {
						MailSendJDBC.attendanceUpdate(attendanceDataBean,attendanceDataBean.getSchoolName(),attendanceDataBean.getSchoolID());
						mailStatus="Success";
					}else if (attendanceDataBean.getStatus().equals("Late")) {
						MailSendJDBC.attendanceUpdate(attendanceDataBean,attendanceDataBean.getSchoolName(),attendanceDataBean.getSchoolID());
						mailStatus="Success";
					}else mailStatus="Success";
					attendanceDataBean.setReturnStatus(mailStatus);
				} 
				logger.debug("status "+attendanceDataBean.getReturnStatus());
			}
		  }
		  catch(Exception e){
			  logger.error("Exception -->"+e.getMessage());
		  }
		  return attendanceDataBean.getReturnStatus();
	  }
	  	  
	  @POST
	  @Path("/attendanceParent")
	  @Produces(MediaType.APPLICATION_JSON)	  
	  public String attendanceParent(String json)throws JSONException{
		  logger.debug("inside attendance parent views ");
		  
		  SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		  Gson gson=new Gson();
		  JsonParser jsonParser = new JsonParser();
		  JsonElement element = jsonParser.parse(json);
		  String dates=element.getAsJsonObject().get("dates").getAsString();
		  String personID=element.getAsJsonObject().get("personID").getAsString();
		  String studentID=element.getAsJsonObject().get("studentID").getAsString();
		  AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
		  try{
			  if (personID != null) {
				  smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");	
				  attendanceDataBean.setDate(ft.parse(dates));
				  attendanceDataBean.setStudentID(studentID);
				  smsservice.parentstuAttView(personID, attendanceDataBean);
				  logger.debug("size "+attendanceDataBean.getStudentList().size());
			  }			 
		  }
		  catch(Exception e){
			  logger.error("Exception -->"+e.getMessage());
		  }
		  return gson.toJson(attendanceDataBean.getStudentList());
	  }
	  
	  @POST
	  @Path("/attendanceStudent")
	  @Produces(MediaType.APPLICATION_JSON)	  
	  public String attendanceStudent(String json)throws JSONException{
		  logger.debug("inside attendance student views ");		 
		  SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		  Gson gson=new Gson();
		  JsonParser jsonParser = new JsonParser();
		  JsonElement element = jsonParser.parse(json);
		  String dates=element.getAsJsonObject().get("dates").getAsString();
		  String personID=element.getAsJsonObject().get("personID").getAsString();
		  AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
		  try{
			  if (personID != null) {
				  smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");	
				  attendanceDataBean.setDate(ft.parse(dates));
				  smsservice.studentAttView(personID, attendanceDataBean);
				  logger.debug("size "+attendanceDataBean.getStudentList().size());
			  }			 
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		  return gson.toJson(attendanceDataBean.getStudentList());
	  }
	  
	  @GET
	  @Path("/leaveRequest/{id}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String leaveRequest(@PathParam("id") String id){
		  Gson gson=new Gson();
		  AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
		  String personID=id;
		  try{
			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
			if (personID != null) {
				studentIDList=smsservice.parentAttlist(personID, attendanceDataBean);
				Collections.sort(studentIDList);
				logger.debug("student size "+studentIDList);
		    }			 
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		  return gson.toJson(studentIDList);
	  }
	  
	  @POST
	  @Path("/leaveRequestSave")
	  
	  @Produces(MediaType.APPLICATION_JSON)
	  public String leaveRequestSave(String json) throws JSONException{
		  logger.debug("leave request save");
		  AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
		  JsonParser jsonParser = new JsonParser();
		  JsonElement element = jsonParser.parse(json);
		  String personID=element.getAsJsonObject().get("personID").getAsString();
		  String studentID=element.getAsJsonObject().get("studentID").getAsString();
		  String date=element.getAsJsonObject().get("date").getAsString();
		  String reason=element.getAsJsonObject().get("reason").getAsString(); 
		  try{
			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
			if (personID != null) {
				attendanceDataBean.setStudentID(studentID);
				attendanceDataBean.setLeavereason(reason);
				attendanceDataBean.setDate(ft.parse(date));
				logger.debug("date "+date);
				String status = smsservice.leaveRequest(attendanceDataBean, personID);
				attendanceDataBean.setReturnStatus(status);
				logger.debug("status "+status);
		    }			 
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		  return attendanceDataBean.getReturnStatus();
	  }
	  
	  @GET
	  @Path("/leaveRequestClass/{id}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String leaveRequestClass(@PathParam("id") String id){
		  Gson gson=new Gson();
		  AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
		  String personID=id;
		  try{
			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
			if (personID != null) {
				classSectionList=smsservice.ClassListAttView(personID, attendanceDataBean);
				Collections.sort(classSectionList);
				logger.debug("class size "+classSectionList);
		    }			 
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		  return gson.toJson(classSectionList);
	  }
	  
	  @GET
	  @Path("/leaveRequestView/{id}/{className}/{classSection}/{date}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String leaveRequestView(@PathParam("id") String id,@PathParam("className") String classname 
			  ,@PathParam("classSection") String classSection,@PathParam("date") String date){
		  logger.debug("inside leave request view"+date);
		  AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
		  SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		  Gson gson=new Gson();
		  String personID=id;
		  try{
			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
			if (personID != null) {
				attendanceDataBean.setClassname(classname+"/"+classSection);
				attendanceDataBean.setDate(ft.parse(date));
				logger.debug("date mb "+ft.parse(date));
				smsservice.leaveRequsetView(attendanceDataBean, personID);
				logger.debug("leave requset size "+attendanceDataBean.getStudentList().size());
		    }			 
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		  return gson.toJson(attendanceDataBean.getStudentList());
	  }
	  
	  @POST
	  @Path("/leaveRequestApprove")	 
	  @Produces(MediaType.APPLICATION_JSON)
	  public String leaveRequestApprove(String json) throws JSONException{
		 
		  AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
		  SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		  JsonParser jsonParser = new JsonParser();
		  JsonElement element = jsonParser.parse(json);
		  String className=element.getAsJsonObject().get("className").getAsString();
		  String date=element.getAsJsonObject().get("date").getAsString();
		  String personID=element.getAsJsonObject().get("personID").getAsString();
		  String studentID=element.getAsJsonObject().get("studentID").getAsString();
		  try{
			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
			if (personID != null) {
				attendanceDataBean.setClassname(className);
				attendanceDataBean.setDate(ft.parse(date));
				attendanceDataBean.setStudentID(studentID);
				smsservice.leaveRequsetApproval(attendanceDataBean, personID);
				attendanceDataBean.setStatus("Approve");
		    }			 
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		  return attendanceDataBean.getStatus();
	  }
	  
	  @POST
	  @Path("/leaveRequestReject")	  
	  @Produces(MediaType.APPLICATION_JSON)
	  public String leaveRequestReject(String json) throws JSONException{
		  AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
		  JsonParser jsonParser = new JsonParser();
		  JsonElement element = jsonParser.parse(json);
		  String personID=element.getAsJsonObject().get("personID").getAsString();
		  String className=element.getAsJsonObject().get("className").getAsString();
		  String date=element.getAsJsonObject().get("date").getAsString();
		  String studentID=element.getAsJsonObject().get("studentID").getAsString();
		 try{
			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
			if (personID != null) {
				attendanceDataBean.setClassname(className);
				attendanceDataBean.setDate(ft.parse(date));
				attendanceDataBean.setStudentID(studentID);
				smsservice.leaveRequsetReject(attendanceDataBean, personID);
				attendanceDataBean.setStatus("Reject");
		    }			 
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		  return attendanceDataBean.getStatus();
	  }
	  
	  @GET
	  @Path("/homeWorkClass/{id}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String homeWorkClass(@PathParam("id") String id){
		  String personID=id;
		  Gson gson=new Gson();
		  String status="homework";
		  try{
			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
		    if (personID != null) {
		    	classSectionList = smsservice.classNamesList(personID,status);
		    	Collections.sort(classSectionList);
		    	logger.debug("class "+classSectionList);
		    }			 
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		  return gson.toJson(classSectionList);
	  }
	  
	  @GET
	  @Path("/homeWorkClassView/{id}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String homeWorkClassView(@PathParam("id") String id){
		  String personID=id;
		  Gson gson=new Gson();
		  String status="homeworkview";
		  try{
			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
		    if (personID != null) {
		    	classSectionList = smsservice.classNamesList(personID,status);
		    	Collections.sort(classSectionList);
		    	logger.debug("class "+classSectionList);
		    }			 
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		  return gson.toJson(classSectionList);
	  }
	  
	  @GET
	  @Path("/homeWorkClassChange/{id}/{className}/{classSection}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String homeWorkClassChange(@PathParam("id") String id,@PathParam("className") String classname 
			  ,@PathParam("classSection") String classSection){
		  String personID=id;
		  Gson gson=new Gson();
		  try{
			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
		    if (personID != null) {	
		    	homeworkDatabean.setClassname(classname+"/"+classSection);
		    	classSubjectList = smsservice.classSubjectList(homeworkDatabean, personID);
		    	Collections.sort(classSubjectList);
		    }			 
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		  return gson.toJson(classSubjectList);
	  }
	  
	  @POST
	  @Path("/homeWorkInsert")	  
	  @Produces(MediaType.APPLICATION_JSON)
	  public String homeWorkInsert(String json) throws JSONException{
		  JsonParser jsonParser = new JsonParser();
		  JsonElement element = jsonParser.parse(json);
		  String personID=element.getAsJsonObject().get("personID").getAsString();
		  String className=element.getAsJsonObject().get("className").getAsString();
		  String subject=element.getAsJsonObject().get("subject").getAsString();
		  String homework=element.getAsJsonObject().get("homework").getAsString();
		 try{
			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
			if (personID != null) {
				homeworkDatabean.setClassname(className);
				homeworkDatabean.setSubject(subject);
				homeworkDatabean.setHomework(homework);
				String status=smsservice.checkHomeWork(homeworkDatabean, personID);
				if(status.equalsIgnoreCase("success")){
					String saveStatus=smsservice.homeWorkInsert(homeworkDatabean, personID);
					homeworkDatabean.setReturnStatus(saveStatus);
					if (saveStatus.equalsIgnoreCase("Success")) {
						MailSendJDBC.homeworkInsert(homeworkDatabean,homeworkDatabean.getSchoolName(),homeworkDatabean.getSchoolID());
						homeworkDatabean.setReturnStatus("Success");
					}
				}else if(status.equalsIgnoreCase("fail")){
					homeworkDatabean.setReturnStatus("fail");
				}				
		    }			 
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		  return homeworkDatabean.getReturnStatus();
	  }
	  
	  @GET
	  @Path("/homeWorkViewDate/{id}/{date}")
	  @Produces(MediaType.APPLICATION_JSON) 	  
	  public String homeWorkViewDate(@PathParam("id") String id,@PathParam("date") String date){
		  String personID=id;
		  Gson gson=new Gson();
		  SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		  try{
			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
		    if (personID != null) {
		    	homeworkDatabean.setDate(ft.parse(date));
		    	smsservice.classChange(homeworkDatabean, personID);
		    	logger.debug("size "+homeworkDatabean.getWorklist().size());
		    }			 
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		  return gson.toJson(homeworkDatabean.getWorklist());
	  }
	  
	  @POST
	  @Path("/homeWorkViewClass")	  
	  @Produces(MediaType.APPLICATION_JSON)
	  public String homeWorkViewClass(String json)throws JSONException{
		  HomeworkDatabean homeworkDatabean = new HomeworkDatabean();
		  SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		  JsonParser jsonParser = new JsonParser();
		  JsonElement element = jsonParser.parse(json);
		  String personID=element.getAsJsonObject().get("personID").getAsString();
		  String date=element.getAsJsonObject().get("date").getAsString();
		  String className=element.getAsJsonObject().get("className").getAsString();
		 Gson gson=new Gson();
		  logger.debug("class "+className);
		  try{
			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
		    if (personID != null) {
		    	homeworkDatabean.setClassname(className);
				homeworkDatabean.setDate(ft.parse(date));
		    	smsservice.classChange(homeworkDatabean, personID);
		    	System.out.println("size "+homeworkDatabean.getWorklist().size());
		    }			 
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		  return gson.toJson(homeworkDatabean.getWorklist());
	  }	  
	  
	  @POST
	  @Path("/homeWorkUpdate")
	  @Consumes(MediaType.APPLICATION_JSON)
	  @Produces(MediaType.APPLICATION_JSON)
	  public String homeWorkUpdate(String json)throws JSONException{
		  logger.debug("inside homeWork update");
		  HomeworkDatabean homeworkDatabean = new HomeworkDatabean();
		  SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		  JsonParser jsonParser = new JsonParser();
		  JsonElement element = jsonParser.parse(json);
		  String subject=element.getAsJsonObject().get("subject").getAsString();
		  String homework=element.getAsJsonObject().get("homework").getAsString();
		  String personID=element.getAsJsonObject().get("personID").getAsString();
		  String date=element.getAsJsonObject().get("date").getAsString();
		  String className=element.getAsJsonObject().get("className").getAsString();
		  try{
			  if (personID != null) {
					homeworkDatabean.setSubject(subject);
					homeworkDatabean.setHomework(homework);	
					homeworkDatabean.setClassname(className);
					homeworkDatabean.setDate(ft.parse(date));
					smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");				
					smsservice.updateHomeWork(homeworkDatabean, personID);
					MailSendJDBC.homeworkUpdate(homeworkDatabean,homeworkDatabean.getSchoolName(),homeworkDatabean.getSchoolID());
					homeworkDatabean.setReturnStatus("Success");
				} 
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		  return homeworkDatabean.getReturnStatus();
	  }
	  
	  @POST
	  @Path("/homeWorkDelete")	  
	  @Produces(MediaType.APPLICATION_JSON)
	  public String homeWorkDelete(String json)throws JSONException{
		  logger.debug("inside homeWork delete");
		  SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		  HomeworkDatabean homeworkDatabean = new HomeworkDatabean();
		  JsonParser jsonParser = new JsonParser();
		  JsonElement element = jsonParser.parse(json);
		  String personID=element.getAsJsonObject().get("personID").getAsString();
		  String date=element.getAsJsonObject().get("date").getAsString();
		  String className=element.getAsJsonObject().get("className").getAsString();
		  String subject=element.getAsJsonObject().get("subject").getAsString();
		  String homework=element.getAsJsonObject().get("homework").getAsString();
		 try{
			  if (personID != null) {
				    homeworkDatabean.setSubject(subject);
					homeworkDatabean.setHomework(homework);	
					homeworkDatabean.setClassname(className);
					homeworkDatabean.setDate(ft.parse(date));
					smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");				
					smsservice.deleteHomeWork(homeworkDatabean, personID);
					homeworkDatabean.setReturnStatus("Success");
				} 
		  }
		  catch(Exception e){
			    logger.error("Exception -->"+e.getMessage());
		  }
		  return homeworkDatabean.getReturnStatus();
	  }
	  
	  @GET
		@Path("/noticeBoardView/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public String noticeBoardView(@PathParam("id") String id){
			Gson gson=new Gson();
			try{
				if (id != null) {
					 smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
					 noticeList = smsservice.getNoticeBoardView(id);
					 logger.debug("size "+noticeList.size());
				}
			}
			catch(Exception e){
				  logger.error("Exception -->"+e.getMessage());
			}
			return gson.toJson(noticeList);
		}
		
		@POST
		@Path("/noticeBoardEdit")
		@Produces(MediaType.APPLICATION_JSON)		
		public String noticeBoardEdit(String json) throws JSONException{
			Gson gson=new Gson();
			JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			String personID=element.getAsJsonObject().get("personID").getAsString();
			String noticesubject=element.getAsJsonObject().get("noticesubject").getAsString();
			String noticefollower=element.getAsJsonObject().get("noticefollower").getAsString();
			String role=element.getAsJsonObject().get("role").getAsString();
			try{
				if (personID != null) {
					noticeBoardDataBean.setNoticeSubject(noticesubject);
					 noticeBoardDataBean.setNoticeFollower(noticefollower);
					 smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
					 noticeListView = smsservice.getNoticeBoardViewBYSubject(personID,noticeBoardDataBean,role);
					 logger.debug("notice size "+noticeListView.get(0).getFromDate());
					 if (noticeListView.size() > 0) {
						noticeBoardDataBean.setNoticeFollower(noticeListView.get(0).getNoticeFollower());
						noticeBoardDataBean.setNoticeFollowers(noticeListView.get(0).getNoticeFollower());
						noticeBoardDataBean.setNoticeSubject(noticeListView.get(0).getSubject());
						noticeBoardDataBean.setNoticeSubjects(noticeListView.get(0).getSubject());
						noticeBoardDataBean.setNoticeID(noticeListView.get(0).getMessage());
						noticeBoardDataBean.setFromdate(noticeListView.get(0).getFromDate());
						noticeBoardDataBean.setTodate(noticeListView.get(0).getToDate());
						noticeBoardDataBean.setNoticeboardID(noticeListView.get(0).getNoticeBoard_ID());
						noticeBoardDataBean.setNoticeClass(noticeListView.get(0).getNoticeClass());
					} else {
						noticeBoardDataBean.setNoticeFollower("");
						noticeBoardDataBean.setNoticeFollowers("");
						noticeBoardDataBean.setNoticeSubject("");
						noticeBoardDataBean.setNoticeID("");
						noticeBoardDataBean.setFromdate(null);
						noticeBoardDataBean.setTodate(null);
						noticeBoardDataBean.setNoticeboardID(0);
					}
				}
			}
			catch(Exception e){
				  logger.error("Exception -->"+e.getMessage());
			}
			return gson.toJson(noticeBoardDataBean);
		}
		
		@POST
		@Path("/noticeBoardUpdate")
		@Produces(MediaType.APPLICATION_JSON)		
		public String noticeBoardUpdate(String json) throws JSONException{
			List<String> emailIdList = null;
			List<String> phonenos=null;
			Gson gson=new Gson();
			JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			String personID=element.getAsJsonObject().get("personID").getAsString();
			String noticesubject=element.getAsJsonObject().get("noticesubject").getAsString();
			String noticefollower=element.getAsJsonObject().get("noticefollower").getAsString();
			String message=element.getAsJsonObject().get("message").getAsString();
			String fromdate=element.getAsJsonObject().get("fromdate").getAsString();
			String todate=element.getAsJsonObject().get("todate").getAsString();
			String noticeID=element.getAsJsonObject().get("noticeID").getAsString();
			String classname=element.getAsJsonObject().get("classname").getAsString();
			try{
				if (personID != null) {
					emailIdList=new ArrayList<String>();
					phonenos=new ArrayList<String>();							
					 noticeBoardDataBean.setNoticeID(message);
					 noticeBoardDataBean.setNoticeSubject(noticesubject);
					 noticeBoardDataBean.setNoticeFollower(noticefollower);
					 noticeBoardDataBean.setFromdate(format.parse(fromdate));
					 noticeBoardDataBean.setTodate(format.parse(todate));
					 noticeBoardDataBean.setNoticeboardID(Integer.parseInt(noticeID));
					 noticeBoardDataBean.setNoticeClass(classname);
					 smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
					 String status = smsservice.updateNotice(personID,noticeBoardDataBean);
					 if(status.equalsIgnoreCase("Success")){
						 noticeBoardList = smsservice.getAllUserList(personID,noticeBoardDataBean);
						 if (noticeBoardList.size() > 0) {
								if (noticeBoardList.get(0).getParentList() != null) {
									if (noticeBoardList.get(0).getParentList().size() > 0) {

										int listsize = noticeBoardList.get(0).getParentList().size();
										for (int i = 0; i < listsize; i++) {
											logger.debug(noticeBoardList.get(0).getParentList().get(i).getEmaiId());
											emailIdList.add(noticeBoardList.get(0).getParentList().get(i).getEmaiId());
											phonenos.add(noticeBoardList.get(0).getParentList().get(i).getPhoneNumber());
										}
									}
								}
								if (noticeBoardList.get(0).getTeacherList() != null) {
									if (noticeBoardList.get(0).getTeacherList().size() > 0) {

										int listsize = noticeBoardList.get(0).getTeacherList().size();
										for (int i = 0; i < listsize; i++) {
											logger.debug(noticeBoardList.get(0).getTeacherList().get(i).getEmailId());
											emailIdList
													.add(noticeBoardList.get(0).getTeacherList().get(i).getEmailId());
											phonenos.add(noticeBoardList.get(0).getTeacherList().get(i).getPhoneNumber());
										}
									}
								}
								if (noticeBoardList.get(0).getStaffList() != null) {
									if (noticeBoardList.get(0).getStaffList().size() > 0) {

										int listsize = noticeBoardList.get(0).getStaffList().size();
										for (int i = 0; i < listsize; i++) {
											logger.debug(noticeBoardList.get(0).getStaffList().get(i).getEmailId());
											emailIdList.add(noticeBoardList.get(0).getStaffList().get(i).getEmailId());
											phonenos.add(noticeBoardList.get(0).getStaffList().get(i).getPhoneNumber());
										}
									}
								}
							
							}
							logger.debug("Checking Purpose  " + emailIdList.size());
							noticeBoardDataBean.setPhonenos(phonenos);
							noticeBoardDataBean.setEmailList(emailIdList);
							Set<String> duplicate = new HashSet<String>();
							duplicate.addAll(emailIdList);
							emailIdList.clear();
							emailIdList.addAll(duplicate);
							logger.debug("Checking Purpose  " + emailIdList.size());
							if (emailIdList.size() > 0 || phonenos.size()>0) {
								MailSendJDBC.noticeBoardUpdate(noticeBoardDataBean, noticeBoardDataBean.getSchoolName(),noticeBoardDataBean.getSchoolID());
								noticeBoardDataBean.setFromdate(null);noticeBoardDataBean.setTodate(null);
								noticeBoardDataBean.setNoticeSubject("");noticeBoardDataBean.setNoticeFollower("");
								noticeBoardDataBean.setNoticeID("");emailIdList = null;
								noticeBoardDataBean.setReturnStatus("Success");
						}
					 }
				}
			}
			catch(Exception e){
				  logger.error("Exception -->"+e.getMessage());
			}
			return gson.toJson(noticeBoardDataBean.getReturnStatus());
		}
		
		@POST
		@Path("/noticeBoarddelete")
		@Produces(MediaType.APPLICATION_JSON)		
		public String noticeBoarddelete(String json) throws JSONException{
			JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			String personID=element.getAsJsonObject().get("personID").getAsString();
			String noticesubject=element.getAsJsonObject().get("noticesubject").getAsString();
			String noticefollower=element.getAsJsonObject().get("noticefollower").getAsString();
			try{
				if (personID != null) {
					 noticeBoardDataBean.setNoticeSubject(noticesubject);
					 noticeBoardDataBean.setNoticeFollower(noticefollower);
					 smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
					 String status = smsservice.deleteNotice(personID,noticeBoardDataBean);
					 noticeBoardDataBean.setReturnStatus(status);
				}
			}
			catch(Exception e){
				  logger.error("Exception -->"+e.getMessage());
			}
			return noticeBoardDataBean.getReturnStatus();
		}
		
		@POST
		@Path("/menuLoad")
		@Produces(MediaType.APPLICATION_JSON)		
		public String menuLoad(String json) throws JSONException{
			logger.debug("inside menu load");
			MenuModel model = null;
			Gson gson=new Gson();
			JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			String userName=element.getAsJsonObject().get("userName").getAsString();
			LoginAccess loginaccess1=new LoginAccess();
			List<LoginAccess> loginlist=new ArrayList<LoginAccess>();
			try{
				 loginaccess1.setUsername(userName);
				 smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
				 menuList=smsservice.LoadMenu(loginaccess1);
				 logger.debug("menu size "+menuList.size());
				 if(menuList.size()>0){
					 model=new DefaultMenuModel();
					 for (int i = 0; i < menuList.size(); i++) {
						 DefaultSubMenu firstSubmenu = new DefaultSubMenu(menuList.get(i).getProduct().getProductName());
						submenuList = new ArrayList<SupProduct>();
						submenuList = smsservice.loadSubMenu(menuList.get(i).getProduct().getProduct_ID(),
									menuList.get(i).getProduct().getProductCode());
						 logger.debug("sub menu size "+submenuList.size());
						 
						if (submenuList.size() > 0) {
							LoginAccess loginaccessn=new LoginAccess();
							loginaccessn.setSubmenu(menuList.get(i).getProduct().getProductName());
							loginlist.add(loginaccessn);
							for (int j = 0; j < submenuList.size(); j++) {
								if(submenuList.get(j).getProductCode().equalsIgnoreCase("ATT003") 
										|| submenuList.get(j).getProductCode().equalsIgnoreCase("REP003")){
									
								}else{
									LoginAccess loginaccess=new LoginAccess();							
									if (submenuList.get(j).getProductCode().equalsIgnoreCase("REG001")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									}else if (submenuList.get(j).getProductCode().equalsIgnoreCase("REG002")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("REG003")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("REG004")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("TIM001")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("TIM002")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("TIM003")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("TIM004")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("NOT001")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("NOT002")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("STU001")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("STU002")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("REP001")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("REP002")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("PAY001")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("PRO001")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("PRO002")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("PRO003")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("ATT001")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("ATT002")) {
										//DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName());
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} /*else if (submenuList.get(j).getProductCode().equalsIgnoreCase("ATT003")) {
										DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName());
										firstSubmenu.addElement(item);
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} */else if (submenuList.get(j).getProductCode().equalsIgnoreCase("ATT004")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("ATT005")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("LIB001")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("LIB002")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("LIB003")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("LIB004")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("BOO003")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("BOO001")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("BOO002")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("BOO004")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} /*else if (submenuList.get(j).getProductCode().equalsIgnoreCase("REP003")) {
										DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName());
										firstSubmenu.addElement(item);
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} */else if (submenuList.get(j).getProductCode().equalsIgnoreCase("PRO004")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("PRO005")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("REG005")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("HW001")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("HW002")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("HW003")) {
										loginaccess.setSubmenu(submenuList.get(j).getProductName());
									}
									loginlist.add(loginaccess);
								}							
							}						
						}
						model.addElement(firstSubmenu);
					 }
				 }
				 logger.debug("login size "+loginlist.size());
			}
			catch(Exception e){
				  logger.error("Exception -->"+e.getMessage());
			}
			return gson.toJson(loginlist);
		}
	  
		/*UDHAYA CODE END*/
		
		/*ROBERT CODE BEGIN*/
	  @POST
		@Path("/insertNoticeBoard")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		public String insertNoticeBoard(String json) throws JSONException {
			logger.debug("Calling insertNoticeBoard Method");
			NoticeBoardDataBean noticeBoardDataBean = new NoticeBoardDataBean();
			//SimpleDateFormat ft=new SimpleDateFormat("dd-MM-yyyy");
			JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			List<String> emailIdList = null;
			List<String> phonenos = null;
			 List<NoticeBoardDataBean> noticeBoardList = null;
			try{
				noticeBoardList=new ArrayList<NoticeBoardDataBean>();
				emailIdList=new ArrayList<String>();
				phonenos=new ArrayList<String>();
				String fromdate=element.getAsJsonObject().get("nfromdate").getAsString();
				String todate=element.getAsJsonObject().get("ntodate").getAsString();
				String subject=element.getAsJsonObject().get("subject").getAsString();
				String follower=element.getAsJsonObject().get("noticefollower").getAsString();
				String notice=element.getAsJsonObject().get("nnotice").getAsString();
				String personID=element.getAsJsonObject().get("personID").getAsString();
				String classname=element.getAsJsonObject().get("classname").getAsString();
				noticeBoardDataBean.setFromdate(ft.parse(fromdate));
				noticeBoardDataBean.setTodate(ft.parse(todate));
				noticeBoardDataBean.setNoticeSubject(subject);
				noticeBoardDataBean.setNoticeFollower(follower);
				noticeBoardDataBean.setNoticeID(notice);
				noticeBoardDataBean.setNoticeClass(classname);
				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
				String status = smsservice.insertNoticeBoard(personID, noticeBoardDataBean);
				noticeBoardDataBean.setReturnStatus(status);
				if (status.equalsIgnoreCase("Success")) {
					noticeBoardList = smsservice.getAllUserList(personID, noticeBoardDataBean);
					if (noticeBoardList.size() > 0) {
						if (noticeBoardList.get(0).getParentList() != null) {
							if (noticeBoardList.get(0).getParentList().size() > 0) {
		
								int listsize = noticeBoardList.get(0).getParentList().size();
								for (int i = 0; i < listsize; i++) {
									emailIdList.add(noticeBoardList.get(0).getParentList().get(i).getEmaiId());
									phonenos.add(noticeBoardList.get(0).getParentList().get(i).getPhoneNumber());
								}
							}
						}
						if (noticeBoardList.get(0).getTeacherList() != null) {
							if (noticeBoardList.get(0).getTeacherList().size() > 0) {
		
								int listsize = noticeBoardList.get(0).getTeacherList().size();
								for (int i = 0; i < listsize; i++) {
									emailIdList.add(noticeBoardList.get(0).getTeacherList().get(i).getEmailId());
									phonenos.add(noticeBoardList.get(0).getTeacherList().get(i).getPhoneNumber());
								}
							}
						}
						if (noticeBoardList.get(0).getStaffList() != null) {
							if (noticeBoardList.get(0).getStaffList().size() > 0) {
		
								int listsize = noticeBoardList.get(0).getStaffList().size();
								for (int i = 0; i < listsize; i++) {
									logger.debug(noticeBoardList.get(0).getStaffList().get(i).getEmailId());
									emailIdList.add(noticeBoardList.get(0).getStaffList().get(i).getEmailId());
									phonenos.add(noticeBoardList.get(0).getStaffList().get(i).getPhoneNumber());
								}
							}
						}
					}
					logger.debug("Checking Purpose  " + emailIdList.size());
					noticeBoardDataBean.setNoticeSubjects(noticeBoardDataBean.getNoticeSubject());
					noticeBoardDataBean.setPhonenos(phonenos);
					noticeBoardDataBean.setEmailList(emailIdList);
					Set<String> duplicate = new HashSet<String>();
					duplicate.addAll(emailIdList);
					emailIdList.clear();
					emailIdList.addAll(duplicate);
					noticeBoardDataBean.setEmailList(emailIdList);
					logger.debug("Checking Purpose  " + emailIdList.size());
					if (emailIdList.size() > 0 || phonenos.size()>0) {
						MailSendJDBC.noticeBoardInsert(noticeBoardDataBean, noticeBoardDataBean.getSchoolName(),noticeBoardDataBean.getSchoolID());
							emailIdList = null;
							noticeBoardDataBean.setFromdate(null);
							noticeBoardDataBean.setTodate(null);
							noticeBoardDataBean.setNoticeSubject("");
							noticeBoardDataBean.setNoticeFollower("");
							noticeBoardDataBean.setNoticeID("");
							noticeBoardDataBean.setReturnStatus("Success");
						} 					
				} 
		}catch(Exception e){
			  logger.error("Exception -->"+e.getMessage());
		}
			return noticeBoardDataBean.getReturnStatus();			
		}
	  /*ROBERT CODE END*/
		
		@GET
		@Path("/getAllStudentView/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public String getAllStudentView(@PathParam("id") String id) throws JSONException
		{
			Gson gson = new Gson();
			List<StudentDataBean> studentList = null;
			StudentDataBean studentDataBean=new StudentDataBean();
			String personID=id;
			studentDataBean.setTeaclssection("Class-01/A");
			try
			{
				if (personID != null) 
				{
					studentList=new ArrayList<StudentDataBean>();
					smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
					studentList = smsservice.getStudentInfo(personID, studentDataBean);
				}
			}
			catch(Exception e)
			{
				  logger.error("Exception -->"+e.getMessage());
			}
			return gson.toJson(studentList);
		}
		  
	  @GET
   @Path("/getPerClassList1/{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public String getPerClassList1(@PathParam("id") String personID) throws JSONException
   {
    List<String> classList=null;
    List<String> sRoll = null;
    Gson gson=new Gson();
    try
    {
    	
     classList=new ArrayList<String>();
     sRoll = new ArrayList<String>();
     smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
     if(classList!=null) {
     classList = smsservice.getClassList1(personID,studentPerformanceDataBean);
     }
     for (int i = 0; i < studentPerformanceDataBean.getStudentclassList().size(); i++) {
      sRoll.add(studentPerformanceDataBean.getStudentclassList().get(i).getStuCls());
     }
     
     logger.debug("class "+sRoll);
    }
    catch(Exception e)
    {
       logger.error("Exception -->"+e.getMessage());
    }
    return gson.toJson(sRoll);    
   }

   @GET
   @Path("/getPerClassstudID/{id}/{classname}/{classSection}")
   @Produces(MediaType.APPLICATION_JSON)
   public String getPerClassstudID(@PathParam("id") String id,@PathParam("classname") String classname,
        @PathParam("classSection") String classSection) throws JSONException
   {
    String personID = id;
    Gson gson=new Gson();
    String classs = classname + "/" + classSection;
    List<String> sRoll1=null;
   // List<Person> personlist=null;
    try {
    	//personlist=new ArrayList<Person>();
    	
     studentPerformanceDataBean.setClassname(classs);
     sRoll1 = new ArrayList<String>();
     
     smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
     if(classname.equalsIgnoreCase("select")){
      sRoll1 = new ArrayList<String>();
     }else{
     studentPerformanceDataBean.setPerClassSection(classs);
     smsservice.getPerClass(studentPerformanceDataBean,personID);
     if(studentPerformanceDataBean.getRollnolist().size() > 0){
     for (int i = 0; i < studentPerformanceDataBean.getRollnolist().size(); i++) {
      sRoll1.add(studentPerformanceDataBean.getRollnolist().get(i).getPerformStudID());
     }
     logger.debug("student id"+sRoll1);
     }else{
      
      sRoll1 = new ArrayList<String>();
     }
     }
    } catch (Exception v) {
     logger.warn(v.getMessage());
     logger.debug("3");
    }
    return gson.toJson(sRoll1);
   }


//student performance
 @POST
 @Path("/insertStudentPerform")
 
 @Produces(MediaType.APPLICATION_JSON)
 public String insertStudentPerform(String json) throws JSONException
 {
  logger.debug(" student performance method calling");
  Gson gson=new Gson();
  String status="";
  //List<String> attlist=null;
  smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
  JsonParser jsonParser = new JsonParser();
  JsonElement element = jsonParser.parse(json);
  String spstudID=element.getAsJsonObject().get("spstudID").getAsString();
  String spclass=element.getAsJsonObject().get("spclass").getAsString();
  String attitude=element.getAsJsonObject().get("attitude").getAsString();
  String appearence=element.getAsJsonObject().get("appearence").getAsString();
  String createDate=element.getAsJsonObject().get("date").getAsString();
  logger.debug("attitude "+attitude);
  
  logger.debug("appearence "+appearence);
  try
  {
   if(studentPerformanceDataBean.getAppOthers().equalsIgnoreCase(null))
   {
    studentPerformanceDataBean.setAppOthers("");
   }
  }catch(Exception e)
  {
   studentPerformanceDataBean.setAppOthers("");
     logger.error("Exception -->"+e.getMessage());
  }
  try
  {
   if(studentPerformanceDataBean.getAttOthers().equalsIgnoreCase(null))
   {
    studentPerformanceDataBean.setAttOthers("");
   }
   
  }catch(Exception e)
  {
   studentPerformanceDataBean.setAttOthers("");
     logger.error("Exception -->"+e.getMessage());
  }
  String appother=element.getAsJsonObject().get("appother").getAsString();
  String attitudeother=element.getAsJsonObject().get("attitudeother").getAsString();
  String personID=element.getAsJsonObject().get("sppersonID").getAsString();
  try
  {
   
   String[] app=new String[] {appearence};
   String[] att=new String[] {attitude};
   studentPerformanceDataBean.setPerformStudID(spstudID);
   studentPerformanceDataBean.setPerClassSection(spclass);
   studentPerformanceDataBean.setStudentAppearance(app);
   studentPerformanceDataBean.setStudentAttitude(att);
   studentPerformanceDataBean.setAppOthers(appother);
   studentPerformanceDataBean.setAttOthers(attitudeother);
   studentPerformanceDataBean.setTeaDob(ft.parse(createDate));
   logger.debug("attitudeother == "+studentPerformanceDataBean.getAttitudeOther());
   status = smsservice.insertStudentPerform(personID, studentPerformanceDataBean);
   studentPerformanceDataBean.setReturnStatus(status);
   if (status.equalsIgnoreCase("Success")) {
	   MailSendJDBC.studentPerformInsert(studentPerformanceDataBean, studentPerformanceDataBean.getSchoolName(),studentPerformanceDataBean.getSchoolID());
    logger.debug("insert student");
   } 
  }
  catch(Exception e)
  {
     logger.error("Exception -->"+e.getMessage());
     logger.error("Exception -->"+e.getMessage());
  }
  return gson.toJson(studentPerformanceDataBean.getReturnStatus());
 }

@GET
  @Path("/getStudentPerInfo/{id}/{classname}/{classSection}/{date}")
  @Produces(MediaType.APPLICATION_JSON)
  public String getStudentPerInfo(@PathParam("id") String id,@PathParam("classname") String classname,
         @PathParam("classSection") String classSection,@PathParam("date") String date) throws JSONException
  {
   Gson gson=new Gson();
  // String status="";
   String personID=id;
   List<StudentPerformanceDataBean> studentList = null;
   try
   {
    String spclass = classname+"/"+classSection;
    studentPerformanceDataBean.setPerClassSection(spclass);
    studentPerformanceDataBean.setTtdate(ft.parse(date));
    logger.debug("class --- "+studentPerformanceDataBean.getPerClassSection());
    if (personID != null) {
     smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
     studentList = new ArrayList<StudentPerformanceDataBean>();
     studentList = smsservice.getStudentPerInfo(personID, studentPerformanceDataBean);
     logger.debug("studentList --- "+studentList.size());
    }
   }
   catch(Exception e)
   {
      logger.error("Exception -->"+e.getMessage());
   }
   return gson.toJson(studentList);
  }

	@GET
	@Path("/getRollNumber/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRollNumber(@PathParam("id") String id) throws JSONException
	{
		String personID=id;
		 String rollnumber="";
		 Gson gson=new Gson();
		 try
		 {
		  if(personID != null){
			  smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
			  rollnumber=smsservice.getRollNumber(personID);
			  logger.debug("roll no"+rollnumber);
			  studentPerformanceDataBean.setStudID(rollnumber);
		  	}
		 }
		 catch(Exception e)
		 {
		  logger.warn("inside exception",e);
		 }
		 return gson.toJson(rollnumber);
	}

	@GET
	@Path("/getStudentPerInfo1/{id}/{stuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getStudentPerInfo1(@PathParam("id") String id,@PathParam("stuid") String stuid) throws JSONException
	{
		System.out.println("inside getStudentPerInfo1");
		Gson gson=new Gson();
		String status="";
		String personID=id;
		String stuID=stuid;
		List<StudentPerformanceDataBean> studentList = null;
		try
		{
			 studentPerformanceDataBean.setStudID(stuID);
			 System.out.println("stud id -- "+stuID+"personid--"+personID);
			 if (personID != null) {
			   smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
			   studentList = new ArrayList<StudentPerformanceDataBean>();
			   studentList = smsservice.getStudentPerInfo(personID, studentPerformanceDataBean);
			   logger.debug("studentList --- "+studentList.size());
			  }
		}
		catch(Exception e)
		{
			  /*logger.warn("inside exception",e);*/
			e.printStackTrace();
		}
		return gson.toJson(studentList);
	}

/*@POST
@Path("/getPerformView/{id}")
@Produces(MediaType.APPLICATION_JSON)
public String getPerformView(@PathParam("id") String id) throws JSONException
{
 String personID=id;
 String rollnumber="";
 Gson gson=new Gson();
 try
 {
  if(personID != null){
   smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
   rollnumber=smsservice.getRollNumber(personID);
   logger.debug("roll no"+rollnumber);
   studentPerformanceDataBean.setStudID(rollnumber);
  }
 }
 catch(Exception e)
 {
    logger.error("Exception -->"+e.getMessage());
 }
 return gson.toJson(rollnumber);
}

	@GET
	@Path("/getStudentPerInfo1/{id}/{stuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getStudentPerInfo1(@PathParam("id") String id,@PathParam("stuid") String stuid) throws JSONException
	{
	logger.debug("inside getStudentPerInfo1");
	 Gson gson=new Gson();
	 //String status="";
	 String personID=id;
	 String stuID=stuid;
	 List<StudentPerformanceDataBean> studentList = null;
	 try
	 {
	  studentPerformanceDataBean.setStudID(stuID);
	  logger.debug("stud id -- "+stuID+"personid--"+personID);
	  if (personID != null) {
	   smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	   studentList = new ArrayList<StudentPerformanceDataBean>();
	   studentList = smsservice.getStudentPerInfo(personID, studentPerformanceDataBean);
	   logger.debug("studentList --- "+studentList.size());
	  }
	 }
	 catch(Exception e)
	 {
	    logger.error("Exception -->"+e.getMessage());
	 }
	 return gson.toJson(studentList);
	}*/

	@POST
	@Path("/getPerformView/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public String getPerformView(@PathParam("id") String id,String json) throws JSONException
	{
	 Gson gson=new Gson();
	 JsonParser jsonParser = new JsonParser();
	 JsonElement element = jsonParser.parse(json);
	
	 List<StudentPerformance> perviewlist = null;
	 try
	 {
	  String studID=element.getAsJsonObject().get("spstudID").getAsString();
	  studentPerformanceDataBean.setPerformStudID(studID);
	  if (!studentPerformanceDataBean.getPerformStudID().equalsIgnoreCase("")) 
	  {
	   perviewlist =new ArrayList<StudentPerformance>();
	   smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	   perviewlist = smsservice.getPerformView(studentPerformanceDataBean);
	   if (perviewlist.size() > 0) {
	    studentPerformanceDataBean.setPerformStudID(studentPerformanceDataBean.getPerformStudID());
	    studentPerformanceDataBean.setStuApp(perviewlist.get(0).getAppearanceStatus());
	    studentPerformanceDataBean.setStuAtt(perviewlist.get(0).getAttitudeStatus());
	    studentPerformanceDataBean.setStuName(studentPerformanceDataBean.getStuName1());
	    studentPerformanceDataBean.setParName(studentPerformanceDataBean.getStuPar1());
	    logger.debug("parents -- "+studentPerformanceDataBean.getStuPar1());
	   }
	   logger.debug("inside perform view method");
	  }
	 }
	 catch(Exception e)
	 {
	    logger.error("Exception -->"+e.getMessage());
	 }
	 return gson.toJson(studentPerformanceDataBean);
	}


	//STUDENT UPDATE
	 @POST
	 @Path("/getStudentDetails") 
	 @Produces(MediaType.APPLICATION_JSON)
	 public String getStudentDetails(String json) throws JSONException
	 {
	  Gson gson=new Gson();
	  JsonParser jsonParser = new JsonParser();
	  JsonElement element = jsonParser.parse(json);
	  logger.debug("-----inside student Edit--------");
	  String personID=element.getAsJsonObject().get("personID").getAsString();
	  String status=null;
	  List<String> stateLists = null;
	  List<StudentDetail> editList = null;
	  List<String> sclass1 = null;
	  List<String> classlist = null;
	  StudentDataBean studentDataBean=new  StudentDataBean();
	 
	  try {
	   smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	   if ((personID != null)) {
	    stateLists = smsservice.getStateLists(personID);
	   }
	
	   sclass1 = new ArrayList<String>();
	   classlist = smsservice.getClassList(personID, studentDataBean);
	   for (int i = 0; i < studentDataBean.getStuPickList().size(); i++) {
	    sclass1.add(studentDataBean.getStuPickList().get(i).getStuCls());
	   }
	   editList = smsservice.getStudentDetails(studentDataBean, personID);
	   if (editList.size() > 0) {
	    for (StudentDetail st : editList) {
	     studentDataBean.setStuFirstName(st.getFirstName());
	     studentDataBean.setStuLastName(st.getLastName());
	     studentDataBean.setStuFatherName(st.getFatherName());
	     studentDataBean.setStuMotherName(st.getMotherName());
	     studentDataBean.setStuDob(st.getDob());
	     studentDataBean.setStuGender(st.getGender());
	     studentDataBean.setStuPresentAddress(st.getAddress1_Street());
	     studentDataBean.setStuPresentState(st.getPostcode1().getState().getStateName());
	     studentDataBean.setStuPresentZip(st.getPostcode1().getPostcode());
	     studentDataBean.setStuPermanentAddress(st.getAddress2_Street());
	     studentDataBean.setStuPermanentState(st.getPostcode2().getState().getStateName());
	     studentDataBean.setStuPermanentZip(st.getPostcode2().getPostcode());
	     studentDataBean.setStuFatherOccu(st.getFatherOccupation());
	     studentDataBean.setStuFatherIncome(st.getFatherIncome());
	     studentDataBean.setStuMotherOccu(st.getMotherOccupation());
	     studentDataBean.setStuCls(studentDataBean.getClassName());
	     studentDataBean.setStuCls1(studentDataBean.getClassName());
	     studentDataBean.setStuEmail(st.getEmailId());
	     studentDataBean.setStuPhoneNo(st.getPhoneNumber());
	     studentDataBean.setStufilePath(studentDataBean.getImagePath1());
	     status = "editPageLoad";
	    }
	   }
	
	  } catch (Exception e)
	
	  {
	   logger.warn("Exception -->"+e.getMessage());
	  }
	  return gson.toJson(studentDataBean);
	
	 }


 
 /* BOOK SALES ORDER */
	// add book(staff)
	@POST
	@Path("/insertAddBook")
	@Produces(MediaType.APPLICATION_JSON)
	public String insertAddBook(String json) throws JSONException {
		logger.debug("innnnnnnnnn");
		String status = "";
		BookSaleDataBean bookSaleDataBean = new BookSaleDataBean();
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(json);
		try {
			Gson gson = new Gson();
			smsservice = (SmsService) SpringApplicationContext
					.getBean("smsservice");

			String personID = element.getAsJsonObject().get("bpersonID")
					.getAsString();
			String bname = element.getAsJsonObject().get("bname").getAsString();
			String bprice = element.getAsJsonObject().get("bprice")
					.getAsString();
			String bquantity = element.getAsJsonObject().get("bquantity")
					.getAsString();
			String addbookphoto = element.getAsJsonObject().get("addbookphoto")
					.getAsString();
			bookSaleDataBean.setBookName(bname);
			bookSaleDataBean.setBookPrice(bprice);
			bookSaleDataBean.setBookQuantity(bquantity);
			bookSaleDataBean.setBookfilePath(addbookphoto);
			logger.debug("-------person id-------" + personID);
			if (personID != null) {
				status = smsservice.insertAddBook(personID, bookSaleDataBean);
				logger.debug("--------status-----" + status);
				bookSaleDataBean.setReturnStatus(status);
				return gson.toJson(bookSaleDataBean.getReturnStatus());
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("inside exception", e);
		}
		return status;
	}

//bookdetailsview(staff)
//datatable list

	 @GET
	 @Path("/bookDetailViewPage/{id}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public String bookDetailViewPage(@PathParam("id") String id) throws JSONException
	 {
	 	Gson gson=new Gson();
	 	List<BookSaleDataBean> bookdetaillist = null;
	 	BookSaleDataBean bookSaleDataBean=new BookSaleDataBean();
	 	logger.debug("---------inside bookDetailView------");
	 	try {
	 		String personID = id;
	 		smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 		if (personID != null) {
	 			bookdetaillist = smsservice.getBookInfo1(personID, bookSaleDataBean);
	 			logger.debug("bookdetaillist --- "+bookdetaillist);
	 		}
	
	 	} catch (Exception e) {
	 		logger.warn("Exception -->"+e.getMessage());
	 	}
	 	return gson.toJson(bookdetaillist);
	 }

//bookdetailsview(staff)
//view
 @POST
 @Path("/getBookDetailInfo/{id}")
 @Produces(MediaType.APPLICATION_JSON)
 public String getBookDetailInfo(@PathParam("id") String id,String json) throws JSONException
 {
 	Gson gson=new Gson();
 	String personID = id;
 	JsonParser jsonParser = new JsonParser();
 	JsonElement element = jsonParser.parse(json);
 	logger.debug("check my method");
 	smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
 	BookSaleDataBean bookSaleDataBean=new BookSaleDataBean();
 	List<BookSalesRecord> viewlist = null;
 	String bookname=element.getAsJsonObject().get("bname").getAsString();
 	try {
 	
 		bookSaleDataBean.setBookName(bookname);
 		if (personID != null) {
 			viewlist = smsservice.getBookDetailInfo(personID, bookSaleDataBean);
 			logger.debug("viewlist -- "+viewlist.size());
 			if (viewlist.size() > 0) {
 				bookSaleDataBean.setBookName(viewlist.get(0).getBookName());
 				bookSaleDataBean.setBookPrice(viewlist.get(0).getPrice());
 				bookSaleDataBean.setBookQuantity(viewlist.get(0).getQuantity());
 			}
 			if (viewlist.size() > 0) {
					bookSaleDataBean.setBookfilePath(viewlist.get(0).getImagePath());
					String file=paths.getString("sms.addbook.photo");
					String pathname=(file+paths.getString("path_context").toString()+bookSaleDataBean.getBookfilePath());
					String encodestring=convertimage(pathname);
					bookSaleDataBean.setBookfilePath(encodestring);
				} else {
					bookSaleDataBean.setBookfilePath("");
				}
 		}

 	} catch (Exception e) {
 		logger.warn("Exception -->"+e.getMessage());
 	}
 	return  gson.toJson(bookSaleDataBean);
 }
 
//bookdetailsview(staff)
	//update
 @POST
 @Path("/bookUpdate") 
 @Produces(MediaType.APPLICATION_JSON)
 public String bookUpdate(String json) throws JSONException
 {
 	logger.debug("check update");
 	logger.debug("-------inside bookUpdate--------");
 	String status = "";
 	BookSaleDataBean bookSaleDataBean=new BookSaleDataBean();
 	//List<BookSaleDataBean> ImageListPath = null;
 	JsonParser jsonParser = new JsonParser();
 	JsonElement element = jsonParser.parse(json);
 	Gson gson=new Gson();
 	try {
 		String personID=element.getAsJsonObject().get("bpersonID").getAsString();
 		String bname=element.getAsJsonObject().get("bname").getAsString();
 		String bprice=element.getAsJsonObject().get("bprice").getAsString();
 		String bquantity=element.getAsJsonObject().get("bquantity").getAsString();
 		System.out.println("quantity----"+bquantity);
 		String filename=element.getAsJsonObject().get("path").getAsString();
 		logger.debug("check update1"+personID);
 		logger.debug("check update1"+bname);
 		logger.debug("check update1"+bprice);
 	    bookSaleDataBean.setBookName(bname);
 		bookSaleDataBean.setBookPrice(bprice);
 		bookSaleDataBean.setBookQuantity(bquantity);
 		
 		if(filename==null || filename.equalsIgnoreCase(""))
				bookSaleDataBean.setBookfilePath(null);
			else
				bookSaleDataBean.setBookfilePath(filename);
 		//List<BookSaleDataBean> viewlist = new ArrayList<BookSaleDataBean>();
 		smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
 		if (personID != null) 
 		{
 			status = smsservice.getBookUpdate(personID, bookSaleDataBean);
 			bookSaleDataBean.setReturnStatus(status);
 			
 		}
 	} catch (Exception e) {
 		logger.warn("Exception -->"+e.getMessage());
 	}
 	return gson.toJson(bookSaleDataBean.getReturnStatus());
 }

//bookdetailsview(staff)
		//delete
 @POST
 @Path("/getBookDelete")
 
 @Produces(MediaType.APPLICATION_JSON)
 public String getBookDelete(String json) throws JSONException
 {
 	logger.debug("-----inside Book Delete--------");
 	logger.debug("check my method");
 	Gson gson=new Gson();
 	JsonParser jsonParser = new JsonParser();
 	JsonElement element = jsonParser.parse(json);
 	String status="";
 	BookSaleDataBean bookSaleDataBean=new BookSaleDataBean();
 	try {
 		String personID=element.getAsJsonObject().get("bpersonID").getAsString();
 		String bname=element.getAsJsonObject().get("bname").getAsString();
 		bookSaleDataBean.setBookName(bname);
 		if (bookSaleDataBean.getBookName() != null)
 		{
 			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
 			if (personID != null) 
 			{
 				status = smsservice.getBookDelete(personID, bookSaleDataBean);
 				bookSaleDataBean.setReturnStatus(status);
 			}
 		}
 	} catch (Exception e) {
 		logger.warn("Exception -->"+e.getMessage());
 	}
 	return gson.toJson(bookSaleDataBean.getReturnStatus());
 }

	
	//book sale order --get student id
	@GET
	@Path("/getstudentRollNumber/{id}/{cname}/{section}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getstudentRollNumber(@PathParam("id") String id,@PathParam("cname") String cname,@PathParam("section") String section) throws JSONException
	{
		logger.debug("inside getstudentRollNumber");
		List<String> rollnumber=null;
		Gson gson=new Gson();
		try
		{
			rollnumber=new ArrayList<String>();
			String personID = id;
			String classname = cname+"/"+section;
			logger.debug("classname"+classname);
			logger.debug("personID--"+personID);
			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
			rollnumber = smsservice.getstudentRollNumber(personID,classname);
			logger.debug("rollnumber -- "+rollnumber);
		}
		catch(Exception e)
		{
			  logger.error("Exception -->"+e.getMessage());
		}
		return gson.toJson(rollnumber);
	}	
	//book sale order --insert
	
/*@POST
@Path("/addBookOrder")

@Produces(MediaType.APPLICATION_JSON)
public String addBookOrder(String json) throws JSONException {
	logger.debug("Calling addBookOrder Method");
	List<BookSaleDataBean> booksaleList = null;
	BookSaleDataBean bookSaleDataBean = new BookSaleDataBean();
	JsonParser jsonParser = new JsonParser();
 	JsonElement element = jsonParser.parse(json);
	String status = "Fail";	
	String studID=element.getAsJsonObject().get("studID").getAsString();
	String clas=element.getAsJsonObject().get("class").getAsString();
	String personID=element.getAsJsonObject().get("id").getAsString();
	String total=element.getAsJsonObject().get("total").getAsString();
	JsonElement root=new JsonParser().parse(element.getAsJsonObject().get("booklist").getAsString()); 
     JsonArray booklist = root.getAsJsonArray();
	try {
		
		List<String> namelist = new ArrayList<String>();
		for (int i = 0; i < booklist.size(); i++) {
			namelist.add(booklist.get(i).getAsString());
		}
		logger.debug("books "+namelist);
		String[] bookNames = null;
		booksaleList = new ArrayList<BookSaleDataBean>();
		for (int j = 0; j < namelist.size(); j++) {
			bookNames = namelist.get(j).split(",");
			int c=0;								
			for (String names : bookNames) {	
				logger.debug("books values "+names+" "+c);
				if(c!=5){ c++;	}
				else if(c==5){
				if(!names.equalsIgnoreCase("]"))
				{ 
					int v=0;
					BookSaleDataBean bookSaleDataBeans = new BookSaleDataBean();
					for (String name : bookNames) {	
						if(v==0) v++;
						else if(v==1){
							bookSaleDataBeans.setBookName(name);v++;
						}else if(v==2){
							bookSaleDataBeans.setBookPrice(name);v++;
						}
						else if(v==3){
							bookSaleDataBeans.setBookQuantity(name);
							v++; 
						}else if(v==4){							
							bookSaleDataBeans.setOrder(name);
							v++;
						}else if(v==5){
							bookSaleDataBeans.setNetAmount(name.replace("]", ""));v++;
						}						
					}	
					booksaleList.add(bookSaleDataBeans);
				}
			}				
			}				
		}
		bookSaleDataBean.setStudID(studID);
		bookSaleDataBean.setClassname(clas);
		bookSaleDataBean.setTotal(total);
		bookSaleDataBean.setBooklist2(booksaleList);
		
		smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
		if (personID != null) {
			status = smsservice.addBookOrder(personID, bookSaleDataBean);
			bookSaleDataBean.setReturnStatus(status);
		}
	} catch (Exception e) {
		logger.warn("inside exception",e);
	}
	return status;
}*/
	
	@POST
	@Path("/addBookOrder")
	@Produces(MediaType.APPLICATION_JSON)
	public String addBookOrder(String json) throws JSONException {
		logger.debug("Calling addBookOrder Method");
		List<BookSaleDataBean> booksaleList = null;
		BookSaleDataBean bookSaleDataBean = new BookSaleDataBean();
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(json);
		String status = "Fail";
		String studID = element.getAsJsonObject().get("studID").getAsString();
		String clas = element.getAsJsonObject().get("class").getAsString();
		String personID = element.getAsJsonObject().get("id").getAsString();
		String total = element.getAsJsonObject().get("total").getAsString();
		JSONObject js = new JSONObject(json);
		JSONArray booklist = js.getJSONArray("booklist");
		try {

			List<String> namelist = new ArrayList<String>();
			for (int i = 0; i < booklist.length(); i++) {
				String studentIDs = (String) booklist.get(i);
				namelist.add(studentIDs);
			}
			logger.debug("books " + namelist);
			String[] bookNames = null;
			booksaleList = new ArrayList<BookSaleDataBean>();
			for (int j = 0; j < namelist.size(); j++) {
				bookNames = namelist.get(j).split(",");
				int c = 0;
				for (String names : bookNames) {
					logger.debug("books values " + names + " " + c);
					if (c != 5) {
						c++;
					} else if (c == 5) {
						if (!names.equalsIgnoreCase("]")) {
							int v = 0;
							BookSaleDataBean bookSaleDataBeans = new BookSaleDataBean();
							for (String name : bookNames) {
								if (v == 0)
									v++;
								else if (v == 1) {
									bookSaleDataBeans.setBookName(name);
									v++;
								} else if (v == 2) {
									bookSaleDataBeans.setBookPrice(name);
									v++;
								} else if (v == 3) {
									bookSaleDataBeans.setBookQuantity(name);
									v++;
								} else if (v == 4) {
									bookSaleDataBeans.setOrder(name);
									v++;
								} else if (v == 5) {
									bookSaleDataBeans.setNetAmount(name
											.replace("]", ""));
									v++;
								}
							}
							booksaleList.add(bookSaleDataBeans);
						}
					}
				}
			}
			bookSaleDataBean.setStudID(studID);
			bookSaleDataBean.setClassname(clas);
			bookSaleDataBean.setTotal(total);
			bookSaleDataBean.setBooklist2(booksaleList);

			smsservice = (SmsService) SpringApplicationContext
					.getBean("smsservice");
			if (personID != null) {
				status = smsservice.addBookOrder(personID, bookSaleDataBean);
				bookSaleDataBean.setReturnStatus(status);
			}
		} catch (Exception e) {
			logger.warn("inside exception", e);
		}
		return status;
	}
	
/*//booksalesview (parent) datatable list
	@GET
	@Path("/getBookViewInfo/{id}/{studID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBookViewInfo(@PathParam("id") String id,@PathParam("studID") String studID) throws JSONException
	{
		Gson gson=new Gson();
		List<BookSaleDataBean> booklist = null;
		BookSaleDataBean bookSaleDataBean=new BookSaleDataBean();
		smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
		try
		{
			String studid=studID;
			bookSaleDataBean.setStudID(studid);
			String personID=id;
			booklist=new ArrayList<BookSaleDataBean>();
			booklist = smsservice.getBookViewInfo(personID, bookSaleDataBean);
			booklist.get(0).setTotalAmount(bookSaleDataBean.getTotalAmount());
			logger.debug("booklist"+booklist.size());
		}
		catch(Exception e)
		{
			logger.warn("inside exception",e);
		}
		return gson.toJson(booklist);
	}
*/

	//booksalesview (parent) datatable list
	@GET
	@Path("/getBookViewInfo/{id}/{studID}/{studentname}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBookViewInfo(@PathParam("id") String id,
			@PathParam("studID") String studID,@PathParam("studentname") String studentname) throws JSONException {
		Gson gson = new Gson();
		List<BookSaleDataBean> booklist = null;
		smsservice = (SmsService) SpringApplicationContext
				.getBean("smsservice");
		try {
			String studid = studID+"/"+studentname;
			bookSaleDataBean.setStudID(studid);
			String personID = id;
			booklist = new ArrayList<BookSaleDataBean>();
			booklist = smsservice.getBookViewInfo(personID, bookSaleDataBean);
			System.out.println("TotalAmount"+bookSaleDataBean.getTotalAmount());
			logger.debug("booklist" + booklist.size());
		} catch (Exception e) {
			logger.warn("inside exception", e);
		}
		return gson.toJson(booklist);
	}
	@GET
    @Path("/getparentstudent/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getparentstudent(@PathParam("id") String id) throws JSONException
    {
     List<String> studidList=null;
     Gson gson=new Gson();
     String personID = id;
     try
     {
      smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
      studidList =  smsservice.getRollNumber1(personID);
      logger.debug("studidList---"+studidList.size());
     }
     catch(Exception e)
     {
        logger.error("Exception -->"+e.getMessage());
     }
     Collections.sort(studidList);
     return gson.toJson(studidList);
    }	
	/*NEELA CODE END*/
	
	/*PREMA CODE BEGIN*/
	
	@GET
	@Path("/getsubject/{id}/{eclass}/{esection}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSubjectView(@PathParam("id") String id,@PathParam("eclass") String eclass,@PathParam("esection") String esection) throws JSONException
	{
		System.out.println("inside getsubject method calling");
		Gson gson = new Gson();
		String personID=id;
		String classs=eclass+"/"+esection;
		List<String> subList=null;
		TimeTableDataBean timeTableDataBean=new TimeTableDataBean();
		timeTableDataBean.setExamClass(classs);
		try{
			if(personID != null){
				subList=new ArrayList<String>();
				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
				smsservice.subjectValues(timeTableDataBean, personID);
				subList.addAll(timeTableDataBean.getSublist());
				logger.debug("subject list size"+subList.size());
			}
		}
		catch(Exception e){
			  logger.error("Exception -->"+e.getMessage());
		}
		return gson.toJson(subList);
	}
	
		@GET
		   @Path("/getclasss/{id}")
		   @Produces(MediaType.APPLICATION_JSON)
		   public String getClassVieww(@PathParam("id") String id) throws JSONException
		   {
		     logger.debug("inside getclass method calling");
		     Gson gson = new Gson();
		     String personID=id;
		     List<String> classList=null;
		     try{
		       if(personID != null){
		         classList=new ArrayList<String>();
		         smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
		         classList=smsservice.classeslist(personID);
		         Collections.sort(classList);
		         logger.debug("subject list size"+classList.size());
		       }
		     }
		     catch(Exception e){
		      logger.warn(" exception - "+e);
		     }
		     return gson.toJson(classList);
		   }

	
		@GET
		 @Path("/getclass/{id}")
		 @Produces(MediaType.APPLICATION_JSON)
		 public String getClassView(@PathParam("id") String id) throws JSONException {
		  logger.debug("inside getclass method calling");
		  Gson gson = new Gson();
		  String personID = id;
		  List<String> classList = null;

		  try {
		   if (personID != null) {
		    classList = new ArrayList<String>();
		    smsservice = (SmsService) SpringApplicationContext
		      .getBean("smsservice");
		    classList = smsservice.getTeaClass(personID, studentDataBean);
		    Collections.sort(classList);
		    logger.debug("subject list size" + classList.size());
		   }
		  } catch (Exception e) {
		     logger.error("Exception -->"+e.getMessage());
		  }
		  return gson.toJson(classList);
		 }
	
	@GET
	@Path("/getexamclass/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getExamClassView(@PathParam("id") String id) throws JSONException
	{
		logger.debug("inside getexamclass method calling");
		Gson gson = new Gson();
		String personID=id;
		List<String> classList=null;
		try{
			if(personID != null){
				classList=new ArrayList<String>();
				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
				classList= smsservice.getClassList(personID);
				Collections.sort(classList);
				logger.debug("class list size"+classList.size());
			}
		}
		catch(Exception e){
			  logger.error("Exception -->"+e.getMessage());
			  logger.error("Exception -->"+e.getMessage());
		}
		return gson.toJson(classList);
	}
	 @POST
	 @Path("/insertexam")
	 @Produces(MediaType.APPLICATION_JSON)
	 public String getexaminsert(String json) throws JSONException{
	  logger.debug("Calling exam timetable Method");
	  timesBean=new ArrayList<TimeTableDataBean>();
	  String status="Fail"; String emailStatus="Fail";List<String> subjects=new ArrayList<String>();
	 // Gson gson=new Gson();
	  List<String> days=new ArrayList<String>(); List<String> subcodess=new ArrayList<String>();
	  List<String> roomnos=new ArrayList<String>(); List<String> starttimes=new ArrayList<String>();
	  List<String> endtimes=new ArrayList<String>(); List<String> shifts=new ArrayList<String>();
	  List<String> dates=new ArrayList<String>();
	  SimpleDateFormat sd=new SimpleDateFormat("dd-MM-yyyy");
	  SimpleDateFormat sd1=new SimpleDateFormat("hh:mm");
	  JsonParser jsonParser = new JsonParser();
	  JsonElement element = jsonParser.parse(json);
	  String title=element.getAsJsonObject().get("title").getAsString();
	  String clas=element.getAsJsonObject().get("clas").getAsString();
	  String tottime=element.getAsJsonObject().get("tottime").getAsString();
	  String personID=element.getAsJsonObject().get("personID").getAsString();
	  String startDate=element.getAsJsonObject().get("startDate").getAsString();
	  JSONObject js=new JSONObject(json);
      JSONArray subject=js.getJSONArray("subject");
	  JSONArray day = js.getJSONArray("day");
	  JSONArray subcode = js.getJSONArray("subcode");
	  JSONArray roomno = js.getJSONArray("roomno");
	  JSONArray starttime = js.getJSONArray("starttime");
	  JSONArray endtime = js.getJSONArray("endttime");
	  JSONArray shift = js.getJSONArray("shift");
	  JSONArray date = js.getJSONArray("date");
	  JSONArray jsonArray=js.getJSONArray("myTableArray");
	  logger.debug("table array length-----"+jsonArray.length());
	   try{
		   //From To Grade table 
		   List<String> namelist = new ArrayList<String>();
		      for (int k = 0; k < jsonArray.length(); k++) {
		       namelist.add(jsonArray.getString(k));
		      }
		      String[] marks=null;
		      for(int m=0;m<namelist.size();m++){
		    	  marks=namelist.get(m).split(","); 
		       int c=0;
		       for (String name : marks) { 
		        if(c!=2){
		         c++;
		        }else if(c==2){
		         if(!name.equalsIgnoreCase("]"))
		         { 
		          int v=0;
		          TimeTableDataBean timeTableDataBeans=new TimeTableDataBean();
		          for(String value:marks){
		           if(v==0){
		            v++;
		            timeTableDataBeans.setExamFromMark(value.replace("[", ""));
		           }else if(v==1){
		        	   timeTableDataBeans.setExamToMark(value);
		            v++;
		           }else if(v==2){
		        	   timeTableDataBeans.setExamGrade(value.replace("]", ""));
		          }
		         }
		          timesBean.add(timeTableDataBeans);
		        }
		       }
		      }
		      }
		       
		   List<String> slist = new ArrayList<String>();
		   for (int i=0; i<subject.length(); i++) {
		       slist.add(subject.getString(i));
		   }
		   String[] subjectss = null;
		   for(int j=0;j<slist.size();j++) {
			   subjectss=slist.get(j).split(",");
		    for(String names:subjectss){
		    	subjects.add(names);
		    }
		   }
		   List<String> dlist = new ArrayList<String>();
		   for (int i=0; i<day.length(); i++) {
			   dlist.add(day.getString(i));
		   }
		   String[] dayss = null;
		   for(int j=0;j<dlist.size();j++) {
			   dayss=dlist.get(j).split(",");
		    for(String names:dayss){
		    	days.add(names);
		    }
		   }
		   List<String> sclist = new ArrayList<String>();
		   for (int i=0; i<subcode.length(); i++) {
			   sclist.add(subcode.getString(i));
		   }
		   String[] subcodes = null;
		   for(int j=0;j<sclist.size();j++) {
			   subcodes=sclist.get(j).split(",");
		    for(String names:subcodes){
		    	subcodess.add(names);
		    }
		   }
		   List<String> rnlist = new ArrayList<String>();
		   for (int i=0; i<roomno.length(); i++) {
			   rnlist.add(roomno.getString(i));
		   }
		   String[] roomnoss = null;
		   for(int j=0;j<rnlist.size();j++) {
			   roomnoss=rnlist.get(j).split(",");
		    for(String names:roomnoss){
		    	roomnos.add(names);
		    }
		   }
		   List<String> stlist = new ArrayList<String>();
		   for (int i=0; i<starttime.length(); i++) {
			   stlist.add(starttime.getString(i));
		   }
		   String[] starttimess = null;
		   for(int j=0;j<stlist.size();j++) {
			   starttimess=stlist.get(j).split(",");
		    for(String names:starttimess){
		    	starttimes.add(names);
		    }
		   }
		   List<String> etlist = new ArrayList<String>();
		   for (int i=0; i<endtime.length(); i++) {
			   etlist.add(endtime.getString(i));
		   }
		   String[] endtimess = null;
		   for(int j=0;j<etlist.size();j++) {
			   endtimess=etlist.get(j).split(",");
		    for(String names:endtimess){
		    	endtimes.add(names);
		    }
		   }
		   List<String> sflist = new ArrayList<String>();
		   for (int i=0; i<shift.length(); i++) {
			   sflist.add(shift.getString(i));
		   }
		   String[] shiftss = null;
		   for(int j=0;j<sflist.size();j++) {
			   shiftss=sflist.get(j).split(",");
		    for(String names:shiftss){
		    	shifts.add(names);
		    }
		   }
		   List<String> datelist = new ArrayList<String>();
		   for (int i=0; i<date.length(); i++) {
			   datelist.add(date.getString(i));
		   }
		   String[] datess = null;
		   for(int j=0;j<datelist.size();j++) {
			   datess=datelist.get(j).split(",");
		    for(String names:datess){
		    	dates.add(names);
		    }
		   }
		   for (int i = 0; i < subject.length(); i++) {
			   logger.debug("inside for loop");
			   TimeTableDataBean timeTableDataBeans=new TimeTableDataBean();
			   timeTableDataBeans.setExamSubject(subjects.get(i));
			   timeTableDataBeans.setExamDay(days.get(i));
			   timeTableDataBeans.setExamSubjectCode(subcodess.get(i));
			   timeTableDataBeans.setExamRoomNo(roomnos.get(i));
			   timeTableDataBeans.setExamStartTime(sd1.parse(starttimes.get(i)));
			   timeTableDataBeans.setExamEndTime(sd1.parse(endtimes.get(i)));
			   timeTableDataBeans.setExamShift(shifts.get(i));
			   timeTableDataBeans.setExamStartDate(sd.parse(dates.get(i)));
			   examtableList.add(timeTableDataBeans);
				  logger.debug("subjects "+examtableList.get(i).getExamSubject()+"days "+examtableList.get(i).getExamDay()+"subjectcodes "+examtableList.get(i).getExamSubjectCode()
			    		+"room no "+examtableList.get(i).getExamRoomNo()+"start times "+examtableList.get(i).getExamStartTime()+"endtimes "+examtableList.get(i).getExamEndTime()+
			    		"shifts "+examtableList.get(i).getExamShift()+"start dates "+examtableList.get(i).getExamStartDate());
		  }
			timeTableDataBean.setExamTitle(title);
			timeTableDataBean.setExamClass(clas);
			timeTableDataBean.setExamtTotalTime(tottime);
			timeTableDataBean.setClassList(examtableList);
			timeTableDataBean.setExamStartDate(sd.parse(startDate));
			logger.debug("class list size"
					+ timeTableDataBean.getClassList().size());
			smsservice = (SmsService) SpringApplicationContext
					.getBean("smsservice");
			if(personID != null){
				smsservice.subjectValues(timeTableDataBean, personID);
				String statuss = smsservice.timeTableICheck(personID, timeTableDataBean);
				logger.debug("statuss -- " + statuss);
				if (statuss.equalsIgnoreCase("no")) {
				status = smsservice.timeTableInsert(personID, timeTableDataBean,timesBean);
				logger.debug("examtimetable status"+timeTableDataBean.getExamtimeTableStatus());
				if(status.equalsIgnoreCase("Success")){
					/*String pdfStatus = GeneratePdfMail.generatePdfexamTimeTable(timeTableDataBean,personID);
					logger.debug("pdfstatus"+pdfStatus);
					if(pdfStatus.equalsIgnoreCase("Success")){*/
						MailSendJDBC.examTimeTableInsert(timeTableDataBean,timeTableDataBean.getSchoolName(),timeTableDataBean.getSchoolID());
						emailStatus="Success";
					//}
				}
				}
				else if(statuss.equalsIgnoreCase("subject")){
					emailStatus="Exist";
				}
				else{
					emailStatus="Failure";
				}
				timeTableDataBean.setStatus(emailStatus);
			}
	     }catch(Exception e){
	        logger.error("Exception -->"+e.getMessage());
	        //logger.error("Exception -->"+e.getMessage());
	     }
	     return timeTableDataBean.getStatus();
	 }

/* Exam TimeTable Information(Exam Class value change)*/
	 @GET
	    @Path("/examtimeTableClassView/{id}/{role}/{classname}/{studentID}/{studentname}")
	    @Produces(MediaType.APPLICATION_JSON)
	    public String getexamTimeTableClassView(@PathParam("id") String id,@PathParam("role") String role
	      ,@PathParam("classname") String classname,@PathParam("studentID") String studentID,@PathParam("studentname") String studentname)throws JSONException {
	     System.out.println("inside getexamtimetableclassview method calling");
	     Gson gson = new Gson();
	     String personID=id;
	     String classs="";
	     logger.debug("Class name -->"+classs);
	     List<String> examList=new ArrayList<String>();
	     try {
	      if(role.equalsIgnoreCase("Admin") || role.equalsIgnoreCase("Teacher")){
	    	  classs=classname+"/"+studentID;
	    	  timeTableDataBean.setExamClass(classs);
	      }else{
	       timeTableDataBean.setExamClass(studentID+"/"+studentname);
	      }      
	      smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	      if (personID != null) {
	        examList = smsservice.getexamList(personID, timeTableDataBean);
	        logger.info("Exam list size---- > " + examList);
	        Collections.sort(examList);
	      }
	     } catch (Exception e) {
	        logger.error("Exception -->"+e.getMessage());
	     }
	     return gson.toJson(examList);
	    }
	/* Exam TimeTable Information(Exam Title value change)*/
		@GET
		@Path("/examtimeTableTitleView/{id}/{role}/{classname}/{etitle}/{studentID}/{studentname}")
		@Produces(MediaType.APPLICATION_JSON)
		public String getexamTimeTableTitleView(@PathParam("id") String id,@PathParam("role") String role,
				@PathParam("classname") String classname,
				@PathParam("etitle") String etitle,@PathParam("studentID") String studentID,@PathParam("studentname") String studentname) throws JSONException {
			logger.debug("inside getexamtimetabletitleview method calling");
			Gson gson = new Gson();
			String personID = id;
			String title = "";
			String classs = "";
			try {
				if(role.equalsIgnoreCase("Admin") || role.equalsIgnoreCase("Teacher")){
					title = studentID;
					classs = classname+"/"+etitle;
					timeTableDataBean.setExamClass(classs);
				}else{
					title = etitle;
					timeTableDataBean.setStudentID(studentID+"/"+studentname);
				}
				System.out.println("stu - "+studentID+" clas - "+classname+" tit - "+etitle+" name - "+studentname);
				timeTableDataBean.setExamTitle(title);
				smsservice = (SmsService) SpringApplicationContext
						.getBean("smsservice");
				List<ClassTimeTableDataBean> timetabledata = new ArrayList<ClassTimeTableDataBean>();
				if (personID != null) {
					smsservice.examRecords(timeTableDataBean, personID);
					for (int i = 0; i < timeTableDataBean.getTablelist().size(); i++) {
						ClassTimeTableDataBean list = new ClassTimeTableDataBean();
						list.setExSubject(timeTableDataBean.getTablelist().get(i)
								.getExamSubject());
						list.setDate(timeTableDataBean.getTablelist().get(i)
								.getExamStartDate());
						list.setDay(timeTableDataBean.getTablelist().get(i)
								.getExamDay());
						list.setSubjectCode(timeTableDataBean.getTablelist().get(i)
								.getExamShift());
						timetabledata.add(list);
						classTimeTableDataBean.setClasstimeList(timetabledata);
					}
					logger.debug("size "
							+ classTimeTableDataBean.getClasstimeList().size());
				}

			} catch (Exception e) {
				  logger.error("Exception -->"+e.getMessage());
				logger.warn("exception ", e);
				  logger.error("Exception -->"+e.getMessage());
			}
			return gson.toJson(classTimeTableDataBean.getClasstimeList());

		}
	/* Exam TimeTable View*/
	@POST
    @Path("/examtimeTableView")
    @Produces(MediaType.APPLICATION_JSON)
    public String getexamTimeTableView(String json)throws JSONException{
     logger.debug("inside getexamtimetableview method calling");
     Gson gson = new Gson();
     ClassTimeTableDataBean list = new ClassTimeTableDataBean();
     JsonParser jsonParser = new JsonParser();
	 JsonElement element = jsonParser.parse(json);
	 String personID=element.getAsJsonObject().get("personid").getAsString();
	 String date=element.getAsJsonObject().get("date").getAsString();
	 String day=element.getAsJsonObject().get("day").getAsString();
	 String subject=element.getAsJsonObject().get("subject").getAsString();
	 String classs=element.getAsJsonObject().get("class").getAsString();
	 String title=element.getAsJsonObject().get("title").getAsString();
	 String studentid=element.getAsJsonObject().get("studentid").getAsString();
    SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
     try {
      timeTableDataBean.setExamStartDate(ft.parse(date));
      timeTableDataBean.setExamSubject(subject);
      timeTableDataBean.setExamDay(day);
      timeTableDataBean.setExamClass(classs);
      timeTableDataBean.setExamTitle(title);
      timeTableDataBean.setStudentID(studentid);
      smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
      if (personID != null) {
        smsservice.getexamRecordsList(personID, timeTableDataBean);
        list.setExSubject(timeTableDataBean.getExamSubject());
        list.setDay(timeTableDataBean.getExamDay());
        list.setClassname(timeTableDataBean.getExamClass());
        list.setDate(timeTableDataBean.getExamStartDate());
        list.setSubject(timeTableDataBean.getExamTitle());
        list.setTimeStart(timeTableDataBean.getExamtTotalTime());
        list.setSubject1(timeTableDataBean.getStime());
        list.setSubject2(timeTableDataBean.getEtime());
        list.setSubjectCode1(timeTableDataBean.getExamRoomNo());
        list.setPeriod(timeTableDataBean.getExamShift());
        list.setSubjectCode(timeTableDataBean.getExamSubjectCode());
      }
     } catch (Exception e) {
        logger.error("Exception -->"+e.getMessage());
     }
     return gson.toJson(list);
    }
	
	@POST
    @Path("/insertclass")
    
    @Produces(MediaType.APPLICATION_JSON)
    public String getclassinsertion(String json) throws JSONException{
     logger.debug("Calling class timetable Method");
     String status="Fail";String emailStatus="Fail";
     Gson gson=new Gson();
     SimpleDateFormat sd=new SimpleDateFormat("hh:mm");
     JsonParser jsonParser = new JsonParser();
	 JsonElement element = jsonParser.parse(json);
	 String clas=element.getAsJsonObject().get("class").getAsString();
	 String day=element.getAsJsonObject().get("day").getAsString();
	 String period=element.getAsJsonObject().get("period").getAsString();
	 String subject=element.getAsJsonObject().get("subject1").getAsString();
	 String subcode=element.getAsJsonObject().get("subcode1").getAsString();
	 String starttime=element.getAsJsonObject().get("starttime1").getAsString();
	 String endttime=element.getAsJsonObject().get("endtime1").getAsString();
	 String subject1=element.getAsJsonObject().get("subject2").getAsString();
	 String subcode1=element.getAsJsonObject().get("subcode2").getAsString();
	 String starttime1=element.getAsJsonObject().get("starttime2").getAsString();
	 String endttime1=element.getAsJsonObject().get("endtime2").getAsString();
	 String subject2=element.getAsJsonObject().get("subject3").getAsString();
	 String subcode2=element.getAsJsonObject().get("subcode3").getAsString();
	 String starttime2=element.getAsJsonObject().get("starttime3").getAsString();
	 String endttime2=element.getAsJsonObject().get("endtime3").getAsString();
	 String subject3=element.getAsJsonObject().get("subject4").getAsString();
	 String subcode3=element.getAsJsonObject().get("subcode4").getAsString();
	 String starttime3=element.getAsJsonObject().get("starttime4").getAsString();
	 String endttime3=element.getAsJsonObject().get("endtime4").getAsString();
	 String subject4=element.getAsJsonObject().get("subject5").getAsString();
	 String subcode4=element.getAsJsonObject().get("subcode5").getAsString();
	 String starttime4=element.getAsJsonObject().get("starttime5").getAsString();
	 String endttime4=element.getAsJsonObject().get("endtime5").getAsString();
	 String subject5=element.getAsJsonObject().get("subject6").getAsString();
	 String subcode5=element.getAsJsonObject().get("subcode6").getAsString();
	 String starttime5=element.getAsJsonObject().get("starttime6").getAsString();
	 String endttime5=element.getAsJsonObject().get("endtime6").getAsString();
	 String subject6=element.getAsJsonObject().get("subject7").getAsString();
	 String subcode6=element.getAsJsonObject().get("subcode7").getAsString();
	 String starttime6=element.getAsJsonObject().get("starttime7").getAsString();
	 String endttime6=element.getAsJsonObject().get("endtime7").getAsString();
	 String subject7=element.getAsJsonObject().get("subject8").getAsString();
	 String subcode7=element.getAsJsonObject().get("subcode8").getAsString();
	 String starttime7=element.getAsJsonObject().get("starttime8").getAsString();
	 String endttime7=element.getAsJsonObject().get("endtime8").getAsString();
	 String personID=element.getAsJsonObject().get("personID").getAsString();
	 String teacherID=element.getAsJsonObject().get("teacherID").getAsString();
	 String teacherID1=element.getAsJsonObject().get("teacherID1").getAsString();
	 String teacherID2=element.getAsJsonObject().get("teacherID2").getAsString();
	 String teacherID3=element.getAsJsonObject().get("teacherID3").getAsString();
	 String teacherID4=element.getAsJsonObject().get("teacherID4").getAsString();
	 String teacherID5=element.getAsJsonObject().get("teacherID5").getAsString();
	 String teacherID6=element.getAsJsonObject().get("teacherID6").getAsString();
	 String teacherID7=element.getAsJsonObject().get("teacherID7").getAsString();
   try{
    	  for (int i = 0; i < 8; i++) {
    	       classTimeTableDataBean=new ClassTimeTableDataBean();
    	   if(i==0){
    		   	 classTimeTableDataBean.setPeriod(period);
    		     classTimeTableDataBean.setSubject(subject);
    		     classTimeTableDataBean.setSubjectCode(subcode);
    		     classTimeTableDataBean.setStartTime(sd.parse(starttime));
    		     classTimeTableDataBean.setEndTime(sd.parse(endttime));
    		     classTimeTableDataBean.setTeaID(teacherID);
    		     classtableList.add(classTimeTableDataBean);
    	   }else if(i==1){
    			 classTimeTableDataBean.setPeriod(period);
    		     classTimeTableDataBean.setSubject(subject1);
    		     classTimeTableDataBean.setSubjectCode(subcode1);
    		     classTimeTableDataBean.setStartTime(sd.parse(starttime1));
    		     classTimeTableDataBean.setEndTime(sd.parse(endttime1));
    		     classTimeTableDataBean.setTeaID(teacherID1);
    		     classtableList.add(classTimeTableDataBean);
    	   }
    	   else if(i==2){
    			 classTimeTableDataBean.setPeriod(period);
    		     classTimeTableDataBean.setSubject(subject2);
    		     classTimeTableDataBean.setSubjectCode(subcode2);
    		     classTimeTableDataBean.setStartTime(sd.parse(starttime2));
    		     classTimeTableDataBean.setEndTime(sd.parse(endttime2));
    		     classTimeTableDataBean.setTeaID(teacherID2);
    		     classtableList.add(classTimeTableDataBean);
    	   }
    	   else if(i==3){
    			 classTimeTableDataBean.setPeriod(period);
    		     classTimeTableDataBean.setSubject(subject3);
    		     classTimeTableDataBean.setSubjectCode(subcode3);
    		     classTimeTableDataBean.setStartTime(sd.parse(starttime3));
    		     classTimeTableDataBean.setEndTime(sd.parse(endttime3));
    		     classTimeTableDataBean.setTeaID(teacherID3);
    		     classtableList.add(classTimeTableDataBean);
    	   }
    	   else if(i==4){
    			 classTimeTableDataBean.setPeriod(period);
    		     classTimeTableDataBean.setSubject(subject4);
    		     classTimeTableDataBean.setSubjectCode(subcode4);
    		     classTimeTableDataBean.setStartTime(sd.parse(starttime4));
    		     classTimeTableDataBean.setEndTime(sd.parse(endttime4));
    		     classTimeTableDataBean.setTeaID(teacherID4);
    		     classtableList.add(classTimeTableDataBean);
    	   }
    	   else if(i==5){
    			 classTimeTableDataBean.setPeriod(period);
    		     classTimeTableDataBean.setSubject(subject5);
    		     classTimeTableDataBean.setSubjectCode(subcode5);
    		     classTimeTableDataBean.setStartTime(sd.parse(starttime5));
    		     classTimeTableDataBean.setEndTime(sd.parse(endttime5));
    		     classTimeTableDataBean.setTeaID(teacherID5);
    		     classtableList.add(classTimeTableDataBean); 
    	   }
    	   else if(i==6){
    			 classTimeTableDataBean.setPeriod(period);
    		     classTimeTableDataBean.setSubject(subject6);
    		     classTimeTableDataBean.setSubjectCode(subcode6);
    		     classTimeTableDataBean.setStartTime(sd.parse(starttime6));
    		     classTimeTableDataBean.setEndTime(sd.parse(endttime6));
    		     classTimeTableDataBean.setTeaID(teacherID6);
    		     classtableList.add(classTimeTableDataBean);
    	   }
    	   else if(i==7){
    			 classTimeTableDataBean.setPeriod(period);
    			 classTimeTableDataBean.setSubject(subject7);
    		     classTimeTableDataBean.setSubjectCode(subcode7);
    		     classTimeTableDataBean.setStartTime(sd.parse(starttime7));
    		     classTimeTableDataBean.setEndTime(sd.parse(endttime7));
    		     classTimeTableDataBean.setTeaID(teacherID7);
    		     classtableList.add(classTimeTableDataBean);
    	   }
    	  }
     
     classTimeTableDataBean.setClasstimeList(classtableList);
     classTimeTableDataBean.setClassname(clas);
     classTimeTableDataBean.setDay(day);
     logger.debug("classtable list size"
       + classTimeTableDataBean.getClasstimeList().size());
     logger.debug("subject1=========>"+classTimeTableDataBean.getClasstimeList().get(0).getSubject()+"subject1=========>"+classTimeTableDataBean.getClasstimeList().get(1).getSubject()+"subject2=========>"+classTimeTableDataBean.getClasstimeList().get(2).getSubject());
     smsservice = (SmsService) SpringApplicationContext
       .getBean("smsservice");
     if(personID != null){
    	 smsservice.classTimeTable(personID, classTimeTableDataBean);
    	 setCreateStatus(classTimeTableDataBean.getStatus());
    	 logger.debug("status======>"+classTimeTableDataBean.getStatus());
    	 if(createStatus.equalsIgnoreCase("Not")){
    		 smsservice.saveClassTimeTable(classTimeTableDataBean, personID);
    		 status="Success";
             logger.debug("status"+status); 
             if(status.equalsIgnoreCase("Success")){
               /* String pdfStatus = GeneratePdfMail.generatePdfClass(classTimeTableDataBean,personID);
                 logger.debug("pdfstatus------->"+pdfStatus);
                 if(pdfStatus.equalsIgnoreCase("Success")){*/
                	 MailSendJDBC.classTimeTableInsert(classTimeTableDataBean,classTimeTableDataBean.getSchoolName(),String.valueOf(classTimeTableDataBean.getSchoolID()));
                	 emailStatus="Success";
                 System.out.println("email status ------------->"+emailStatus);
           //  }
            }
    	 }
    	 else{
    		 emailStatus="Exist";
    	 }
         classTimeTableDataBean.setStatus(emailStatus);
      }
        }catch(Exception e){
           logger.error("Exception -->"+e.getMessage());
        }
        return gson.toJson(classTimeTableDataBean.getStatus());
   }


/* Class TimeTable View*/
	@GET
	@Path("/classtimeTableView/{id}/{classname}/{classSection}/{dayy}/{months}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getclassTimeTableView(@PathParam("id") String id,@PathParam("classname") String classname,
			@PathParam("classSection") String classSection,
			@PathParam("dayy") String dayy,@PathParam("months") String months)throws JSONException {
		logger.debug("inside getclasstimetableview method calling");
		Gson gson = new Gson();
		String personID = id;
		String classs = classname + "/" + classSection;
		String day = dayy;
		String month = months;
		List<ClassTimeTableDataBean> classList = new ArrayList<ClassTimeTableDataBean>();
		 DateFormat st=new SimpleDateFormat("hh:mm");
		try {
			classTimeTableDataBean.setClassname(classs);
			classTimeTableDataBean.setDay(day);
			classTimeTableDataBean.setMonth(month);
			smsservice = (SmsService) SpringApplicationContext
					.getBean("smsservice");
			if (personID != null) {
					smsservice.classTimeTableView(classTimeTableDataBean, personID);
					classList.addAll(classTimeTableDataBean.getClasstimeList());
					logger.debug("classtimetable list size" + classList.size());
			}
		} catch (Exception e) {
			  logger.error("Exception -->"+e.getMessage());
		}
		return gson.toJson(classList);
	}

	/* Create Class TimeTable */
	@GET
	@Path("/createclasstimetable/{id}/{classname}/{classSection}/{dayy}")
	@Produces(MediaType.APPLICATION_JSON)
	public String createclassTimeTable(@PathParam("id") String id,
			@PathParam("classname") String classname,
			@PathParam("classSection") String classSection,
			@PathParam("dayy") String dayy) throws JSONException {
		logger.debug("Calling create class timetable Method");
		String personID = id;
		Gson gson = new Gson();
		String classs = classname + "/" + classSection;
		String day = dayy;
		try {
			classTimeTableDataBean.setClassname(classs);
			classTimeTableDataBean.setDay(day);
			smsservice = (SmsService) SpringApplicationContext
					.getBean("smsservice");
			if (personID != null) {
				smsservice.classTimeTable(personID, classTimeTableDataBean);
				classTimeTableDataBean.setStatus(classTimeTableDataBean
						.getStatus());
				logger.debug("status--------->"
						+ classTimeTableDataBean.getStatus());
			}
		} catch (Exception e) {
			  logger.error("Exception -->"+e.getMessage());
		}
		return gson.toJson(classTimeTableDataBean.getSubjectlist());
	}

	
/* Class TimeTable Update*/
	   @POST
	   @Path("/classtimeTableUpdate/{dayy}/{classname}/{classSection}")
	 
	   @Produces(MediaType.APPLICATION_JSON)
	   public String updateclassTimeTable(String json,@PathParam("classname") String classname,
				@PathParam("classSection") String classSection,@PathParam("dayy") String dayy
				)throws JSONException {
	    logger.debug("Calling exam timetable update Method");
	    DateFormat st=new SimpleDateFormat("hh:mm");
	    Gson gson=new Gson();
	    int year=0;
	   /* Calendar now = Calendar.getInstance();
	    year = now.get(Calendar.YEAR);*/
	    JsonParser jsonParser = new JsonParser();
		 JsonElement element = jsonParser.parse(json);
		 String personID=element.getAsJsonObject().get("personID").getAsString();
		 int primary=element.getAsJsonObject().get("primarykey").getAsInt();
		 String updatesubject=element.getAsJsonObject().get("updatesubject").getAsString();
		 String subcode=element.getAsJsonObject().get("subcode").getAsString();
		 String starttime=element.getAsJsonObject().get("starttime").getAsString();
		 String endtime=element.getAsJsonObject().get("endtime").getAsString();
		 String period=element.getAsJsonObject().get("period").getAsString();
		 String teacherID=element.getAsJsonObject().get("teacherID").getAsString();
	   String day = dayy;
	    String classs = classname + "/" + classSection;
	     try {
	     classTimeTableDataBean.setClassname(classs);
	     classTimeTableDataBean.setDay(day);
	     classTimeTableDataBean.setPeriod(period);
	     classTimeTableDataBean.setClasstableid(primary);;
	     classTimeTableDataBean.setSubjectCode(subcode);
	     classTimeTableDataBean.setSubject(updatesubject);
	     classTimeTableDataBean.setYear(String.valueOf(year));
	     classTimeTableDataBean.setStartTime(st.parse(starttime));
	     classTimeTableDataBean.setEndTime(st.parse(endtime));
	     classTimeTableDataBean.setTeaID(teacherID);
	     smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	     if (personID != null) {
	    	 smsservice.classTimeTableView(classTimeTableDataBean, personID);
	    	String statuss=smsservice.checkupdateClassTimeTable(classTimeTableDataBean,personID);
	    	if(statuss.equalsIgnoreCase("Exist")){
	    		 classTimeTableDataBean.setStatus(statuss);
	    	}else if(statuss.equalsIgnoreCase("Fail")){
	    		classTimeTableDataBean.setStatus(statuss);
	    	}else if(statuss.equalsIgnoreCase("Success")){
	    		String status =smsservice.updateClassTimeTable(classTimeTableDataBean, personID);
	    		if(status.equalsIgnoreCase("Success")){
	    			 MailSendJDBC.classTimeTableUpdate(classTimeTableDataBean,classTimeTableDataBean.getSchoolName(),classTimeTableDataBean.getSchoolID());
	    			 classTimeTableDataBean.setStatus(status);
	    		}
	    	}
	     }
	    } catch (Exception e) {
	       logger.error("Exception -->"+e.getMessage());
	    }
	    return gson.toJson(classTimeTableDataBean.getStatus());
	   }

	
/* Add Extra Class in Class TimeTable*/
	@POST
	@Path("/addextraclass")
	
	@Produces(MediaType.APPLICATION_JSON)
	public String addextraclassTimeTable(String json)throws JSONException {
		logger.debug("Calling add extra class timetable Method");
		SimpleDateFormat sd=new SimpleDateFormat("hh:mm");
		//SimpleDateFormat sd1=new SimpleDateFormat("yyyy-MM-dd");
		String statuss="Fail";String emailStatus="Fail";
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(json);
		String personID=element.getAsJsonObject().get("personID").getAsString();
		String day=element.getAsJsonObject().get("day").getAsString();
		String month=element.getAsJsonObject().get("month").getAsString();
		String subject=element.getAsJsonObject().get("subject").getAsString();
		String classs=element.getAsJsonObject().get("class").getAsString();
		String subcode=element.getAsJsonObject().get("subcode").getAsString();
		String starttime=element.getAsJsonObject().get("starttime").getAsString();
		String endtime=element.getAsJsonObject().get("endtime").getAsString();
		String teacherID=element.getAsJsonObject().get("teacherID").getAsString();
	   try {
			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
			classTimeTableDataBean.setClassname(classs);
			classTimeTableDataBean.setSubject(subject);
			classTimeTableDataBean.setDay(day);
			classTimeTableDataBean.setMonth(month); 
			classTimeTableDataBean.setSubjectCode(subcode);
			classTimeTableDataBean.setStartTime(sd.parse(starttime));
			classTimeTableDataBean.setEndTime(sd.parse(endtime));
			classTimeTableDataBean.setTeaID(teacherID);
			if (personID != null) {
				String status = smsservice.checkExtraClass(personID, classTimeTableDataBean);
				logger.debug("status------------>"+status);
				if(status.equalsIgnoreCase("success")){
					statuss = smsservice.insertExtraClass(personID, classTimeTableDataBean);
					logger.debug("statussss - " + statuss);
					if(statuss.equalsIgnoreCase("success")){
						MailSendJDBC.classTimeTableAdd(classTimeTableDataBean, classTimeTableDataBean.getSchoolName(),classTimeTableDataBean.getSchoolID());
						emailStatus="Success";
						logger.debug("email status"+emailStatus);
					}
				}
				else if(status.equalsIgnoreCase("fail")){
					emailStatus="Exist";
				}else{
					emailStatus="Failure";
				}
				classTimeTableDataBean.setStatus(emailStatus);
			}
		} catch (Exception e) {
			  logger.error("Exception -->"+e.getMessage());
		}
		return classTimeTableDataBean.getStatus();
	}
	
	@GET
	@Path("/getclasschange/{id}/{classname}/{classSection}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getClassValueChange(@PathParam("id") String id,@PathParam("classname") String classname,@PathParam("classSection") String classSection) throws JSONException
	{
		logger.debug("inside getclass value change method calling");
		Gson gson = new Gson();
		String personID=id;
		List<String> subjectList=null;
		String classs= classname + "/" + classSection;
		try{
			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
			classTimeTableDataBean.setClassname(classs);
			if(personID != null){
				subjectList=new ArrayList<String>();
				smsservice.classChange(personID,classTimeTableDataBean);
				Collections.sort(classTimeTableDataBean.getSubjectlist());
				logger.debug("subject list size"+classTimeTableDataBean.getSubjectlist());
				subjectList.addAll(classTimeTableDataBean.getSubjectlist());
			}
		}
		catch(Exception e){
			  logger.error("Exception -->"+e.getMessage());
		}
		return gson.toJson(subjectList);
	}
	
	 @GET
	 @Path("/getsubjectchange/{id}/{classname}/{classSection}/{subject}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public String getSubjectValueChange(@PathParam("id") String id,@PathParam("classname") String classname ,
	   @PathParam("classSection") String classSection,@PathParam("subject") String subject) throws JSONException
	 {
	  logger.debug("inside getsubject value change method calling");
	  String personID=id;
	  String classs =classname+"/"+classSection;
	  String subjects=subject;
	  Gson gson=new Gson();
	  try{
	   smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	   classTimeTableDataBean.setSubject(subjects);
	   classTimeTableDataBean.setClassname(classs);
	   if(personID != null){
		   smsservice.subjectChange(personID, classTimeTableDataBean);
	    classTimeTableDataBean.setSubjectCode(classTimeTableDataBean.getSubjectcodelist().get(0));
	    logger.debug("subject code========>"+classTimeTableDataBean.getSubjectCode());
	   }
	  }
	  catch(Exception e){
	     logger.error("Exception -->"+e.getMessage());
	  }
	  return gson.toJson(classTimeTableDataBean.getSubjectCode());
	 }
	 
	 @GET
	 @Path("/getSubjectValueTeacher/{id}/{classname}/{classSection}/{subject}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public String getSubjectValueTeacher(@PathParam("id") String id,@PathParam("classname") String classname ,
	   @PathParam("classSection") String classSection,@PathParam("subject") String subject) throws JSONException
	 {
	  logger.debug("get teacher ids");
	  String personID=id;
	  String classs =classname+"/"+classSection;
	  String subjects=subject;
	  Gson gson=new Gson();
	  try{
	   smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	   classTimeTableDataBean.setSubject(subjects);
	   classTimeTableDataBean.setClassname(classs);
	   if(personID != null){
		 teacherIDList=smsservice.subjectChange(personID, classTimeTableDataBean);
		 logger.debug("size 1"+teacherIDList.size());
	   }
	  }
	  catch(Exception e){
	     logger.error("Exception -->"+e.getMessage());
	   logger.warn(" exception - "+e);
	  }
	  return gson.toJson(teacherIDList);
	 }

	
	@GET
	@Path("/getsubjectcodechange/{id}/{classname}/{subject}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSubjectCodeValueChange(@PathParam("id") String id,@PathParam("classname") String classname,@PathParam("subject") String subject) throws JSONException
	{
		logger.debug("inside getsubjectcode value change method calling");
		String personID=id;
		Gson gson=new Gson();
		String subjects=subject;
		String classs=classname;
		try{
			timeTableDataBean.setExamSubject(subjects);
			timeTableDataBean.setExamClass(classs);
			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
			if(personID != null){
				smsservice.subjectValues(timeTableDataBean, personID);
				smsservice.changeSubjectCode(personID, timeTableDataBean);
				logger.debug("subject code-- > " + timeTableDataBean.getExamSubjectCodes());
				timeTableDataBean.setExamSubjectCode(timeTableDataBean.getExamSubjectCodes());
			}
		}
		catch(Exception e){
			  logger.error("Exception -->"+e.getMessage());
		}
		return gson.toJson(timeTableDataBean.getExamSubjectCode());
	}
	
	@POST
    @Path("/examtimeTableEdit")
    @Produces(MediaType.APPLICATION_JSON)
    public String getExamTimeTableEdit(String json) throws JSONException
    {
     logger.debug("inside getexam time table edit value method calling");
     Gson gson=new Gson();
     ClassTimeTableDataBean list = new ClassTimeTableDataBean();
     SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
     JsonParser jsonParser = new JsonParser();
	 JsonElement element = jsonParser.parse(json);
	 String personID=element.getAsJsonObject().get("personid").getAsString();
	 String subject=element.getAsJsonObject().get("subject").getAsString();
	 String date=element.getAsJsonObject().get("date").getAsString();
	 String day=element.getAsJsonObject().get("day").getAsString();
	 String classs=element.getAsJsonObject().get("class").getAsString();
	 String title=element.getAsJsonObject().get("title").getAsString();
    try{
      timeTableDataBean.setExamSubject(subject);
      timeTableDataBean.setExamDay(day);
      timeTableDataBean.setExamStartDate(ft.parse(date));
      timeTableDataBean.setExamClass(classs);
      timeTableDataBean.setExamTitle(title);
      logger.debug("date"+timeTableDataBean.getExamStartDate());
      smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
      if(personID != null){
       smsservice.subjectValues(timeTableDataBean, personID);
       smsservice.getexamEditList(personID, timeTableDataBean);
       DateFormat df = new SimpleDateFormat("HH:mm");
       list.setExSubject(timeTableDataBean.getExamSubject());
        list.setDay(timeTableDataBean.getExamDay());
        list.setClassname(timeTableDataBean.getExamClass());
        list.setSubject5(format.format(timeTableDataBean.getExamStartDate()));
        list.setSubject(timeTableDataBean.getExamTitle());
        list.setTimeStart(timeTableDataBean.getExamtTotalTime());
        list.setSubject6(df.format(timeTableDataBean.getExamStartTime()));
        list.setSubject7(df.format(timeTableDataBean.getExamEndTime()));
        list.setSubjectCode1(timeTableDataBean.getExamRoomNo());
        list.setPeriod(timeTableDataBean.getExamShift());
        list.setSubjectCode(timeTableDataBean.getExamSubjectCode());
        list.setSerialno(timeTableDataBean.getExamtableId());
       setExamTableid(timeTableDataBean.getExamtableId());
       logger.debug("id--->"+examTableid+list.getExSubject()+">>"+list.getSubject5()+">>>"+ list.getSubject6());
      }
     }
     catch(Exception e){
        logger.error("Exception -->"+e.getMessage());
     }
     return gson.toJson(list);
    }

	
	 @POST
     @Path("/examtimetableupdate")     
     @Produces(MediaType.APPLICATION_JSON)
     public String getexamtimetableupdate(String json) throws JSONException{
      logger.debug("Calling exam timetable update Method");
      String status="Fail";
      Gson gson=new Gson(); 
      DateFormat sd1=new SimpleDateFormat("HH:mm");
      JsonParser jsonParser = new JsonParser();
 	  JsonElement element = jsonParser.parse(json);
 	  String dateedit=element.getAsJsonObject().get("date").getAsString();
 	  String day=element.getAsJsonObject().get("day").getAsString();
 	  String subject=element.getAsJsonObject().get("subject").getAsString();
 	  String subcode=element.getAsJsonObject().get("subcode").getAsString();
 	  String roomno=element.getAsJsonObject().get("roomno").getAsString();
 	  String starttime=element.getAsJsonObject().get("starttime").getAsString();
 	  String endttime=element.getAsJsonObject().get("endtime").getAsString();
 	  String shift=element.getAsJsonObject().get("shift").getAsString();
 	  String personID=element.getAsJsonObject().get("personID").getAsString();
 	  String classs=element.getAsJsonObject().get("class").getAsString();
 	  String title=element.getAsJsonObject().get("title").getAsString();
 	  String examtableid=element.getAsJsonObject().get("tableid").getAsString();
     try{
      
         timeTableDataBean.setExamClass(classs);
      timeTableDataBean.setExamTitle(title);
      logger.debug("chk 1");
      timeTableDataBean.setExamStartDate(format.parse(dateedit));
      timeTableDataBean.setExamDay(day);
      timeTableDataBean.setExamSubject(subject);
      timeTableDataBean.setExamSubjectCode(subcode);
      logger.debug("chk 2"+timeTableDataBean.getExamStartDate());
      timeTableDataBean.setExamRoomNo(roomno);
      timeTableDataBean.setExamStartTime(sd1.parse(starttime));
      logger.debug("chk 3"+timeTableDataBean.getExamStartTime());
      timeTableDataBean.setExamEndTime(sd1.parse(endttime));
      logger.debug("chk 4"+timeTableDataBean.getExamEndTime());
      timeTableDataBean.setExamShift(shift);
      timeTableDataBean.setExamtableId(Integer.parseInt(examtableid));
      smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
      if(personID != null){
      
       status = smsservice.getexamupdate(personID, timeTableDataBean);
      logger.debug("staus - " + status);
       if(status.equalsIgnoreCase("no")){
    	   MailSendJDBC.examTimeTableUpdate(timeTableDataBean,timeTableDataBean.getSchoolName(),timeTableDataBean.getSchoolID());
        String emailStatus ="Success";/* GeneratePdfMail.sendEmailExamClassUpdate(timeTableDataBean);*/
        logger.debug("mail status -- > " + emailStatus);
        timeTableDataBean.setStatus(emailStatus);
       }
      }
         }catch(Exception e){
            logger.error("Exception -->"+e.getMessage());
         }
         return gson.toJson(timeTableDataBean.getStatus());
     }
	/* @POST
	  @Path("/addbooklibrary")
	  @Consumes(MediaType.APPLICATION_JSON)
	  @Produces(MediaType.APPLICATION_JSON)
	  public String addbook(String json) throws JSONException {
	   logger.debug("Calling exam timetable update Method");
	   Gson gson = new Gson();
	   JsonParser jsonParser = new JsonParser();
	   JsonElement element = jsonParser.parse(json);
	   String bookname=element.getAsJsonObject().get("bookname").getAsString();
	   String authorname=element.getAsJsonObject().get("authname").getAsString();
	   String bookeditions=element.getAsJsonObject().get("editions").getAsString();
	   String category=element.getAsJsonObject().get("category").getAsString();
	   String price=element.getAsJsonObject().get("price").getAsString();
	   String pages=element.getAsJsonObject().get("pages").getAsString();
	   String duetype=element.getAsJsonObject().get("duetype").getAsString();
	   String penalty=element.getAsJsonObject().get("penalty").getAsString();
	   String description=element.getAsJsonObject().get("description").getAsString();
	   String personID=element.getAsJsonObject().get("personID").getAsString();
	 try {
	    libraryDataBean.setBookName(bookname);
	    libraryDataBean.setAuthorName(authorname);
	    libraryDataBean.setBookeditions(bookeditions);
	    libraryDataBean.setBookCategory(category);
	    libraryDataBean.setBookFee(penalty);
	    libraryDataBean.setBookDueType(duetype);
	    libraryDataBean.setBookPages(pages);
	    libraryDataBean.setBookPrice(price);
	    libraryDataBean.setBookDescription(description);
	    smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	    if (personID != null) {
	     String status = smsservice.insertLibrary(personID,libraryDataBean);
	     libraryDataBean.setStatus(status);
	     logger.debug("status-------->" + status);
	    }
	   } catch (Exception e) {
	    logger.warn(" exception - "+e);
	   }
	   return gson.toJson(libraryDataBean.getStatus());
	  }*/
	 
	 @POST
		@Path("/addbooklibrary")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public String addbook(String json) throws JSONException {
			logger.debug("Calling exam timetable update Method");
			Gson gson = new Gson();
			JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			String bookname = element.getAsJsonObject().get("bookname")
					.getAsString();
			String authorname = element.getAsJsonObject().get("authname")
					.getAsString();
			String bookeditions = element.getAsJsonObject().get("editions")
					.getAsString();
			String category = element.getAsJsonObject().get("category")
					.getAsString();
			String price = element.getAsJsonObject().get("price").getAsString();
			String pages = element.getAsJsonObject().get("pages").getAsString();
			String duetype = element.getAsJsonObject().get("duetype").getAsString();
			String penalty = element.getAsJsonObject().get("penalty").getAsString();
			String description = element.getAsJsonObject().get("description")
					.getAsString();
			String librarybookphoto = element.getAsJsonObject()
					.get("librarybookphoto").getAsString();
			String personID = element.getAsJsonObject().get("personID")
					.getAsString();
			try {
				libraryDataBean.setBookName(bookname);
				libraryDataBean.setAuthorName(authorname);
				libraryDataBean.setBookeditions(bookeditions);
				libraryDataBean.setBookCategory(category);
				libraryDataBean.setBookFee(penalty);
				libraryDataBean.setBookDueType(duetype);
				libraryDataBean.setBookPages(pages);
				libraryDataBean.setBookPrice(price);
				libraryDataBean.setBookDescription(description);
				libraryDataBean.setParfilePath(librarybookphoto);
				smsservice = (SmsService) SpringApplicationContext
						.getBean("smsservice");
				if (personID != null) {
					String status = smsservice.insertLibrary(personID,
							libraryDataBean);
					libraryDataBean.setStatus(status);
					logger.debug("status-------->" + status);
				}
			} catch (Exception e) {
				  logger.error("Exception -->"+e.getMessage());
			}
			return gson.toJson(libraryDataBean.getStatus());
		}

	 /*library addbook view*/
	  @GET
	  @Path("/addbookview/{id}/{category}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getaddbookview(@PathParam("id") String id,@PathParam("category") String category) throws JSONException {
	   logger.debug("inside add book view method calling");
	   String personID = id;
	   Gson gson = new Gson();
	   String name = category;
	   List<LibraryDataBean> bookList=new ArrayList<LibraryDataBean>(); 
	   try {
	     smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	     if (personID != null) {
	     bookList = smsservice.getBookListView(personID,name);
	     logger.debug("booklist size--->"+bookList.size());
	    }
	   } catch (Exception e) {
	    logger.warn(" exception - "+e);
	   }
	   return gson.toJson(bookList);
	  }
	  
	  /*library addbook detail view & library addbook detail edit*/
	  @POST
	  @Path("/addbookdetailview/{id}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getaddbookdetailview(@PathParam("id") String id,String json)
	    throws JSONException {
	   logger.debug("inside add book detail view method calling");
	   String personID = id;
	   Gson gson = new Gson();
	   JsonParser jsonParser = new JsonParser();
	   JsonElement element = jsonParser.parse(json);
	   String bookname=element.getAsJsonObject().get("bokna").getAsString();
	   String authorname=element.getAsJsonObject().get("bokau").getAsString();
	   String bookeditions=element.getAsJsonObject().get("boked").getAsString();
	  List<LibraryDataBean> bookDetailsList = new ArrayList<LibraryDataBean>();
	   try {
	    smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	    libraryDataBean.setBookName(bookname);
	    libraryDataBean.setAuthorName(authorname);
	    libraryDataBean.setBookeditions(bookeditions);
	    if (personID != null) {
	     bookDetailsList = smsservice.getBookDetailsView(personID,libraryDataBean);
	     if (bookDetailsList.size() > 0) {
	      if (bookDetailsList.get(0).getLibrayList() != null) {
	       if (bookDetailsList.get(0).getLibrayList().size() > 0) {
	    	   logger.debug("booklist size--->"+ bookDetailsList.size());
	        libraryDataBean.setAuthorName(bookDetailsList.get(0).getLibrayList().get(0).getAuthorName());
	        logger.debug("AuthorName--->"+ bookDetailsList.get(0).getLibrayList().get(0).getAuthorName());
	        libraryDataBean.setBookCategory(bookDetailsList.get(0).getLibrayList().get(0).getCategory());
	        logger.debug("BookCategory--->"+ bookDetailsList.get(0).getLibrayList().get(0).getCategory());
	        libraryDataBean.setBookDescription(bookDetailsList.get(0).getLibrayList().get(0).getDescription());
	        logger.debug("BookDescription--->"+ bookDetailsList.get(0).getLibrayList().get(0).getDescription());
	        libraryDataBean.setBookeditions(bookDetailsList.get(0).getLibrayList().get(0).getBookEdition());
	        logger.debug("Bookeditions--->"+ bookDetailsList.get(0).getLibrayList().get(0).getBookEdition());
	        libraryDataBean.setBookName(bookDetailsList.get(0).getLibrayList().get(0).getBookName());
	        logger.debug("BookName--->"+ bookDetailsList.get(0).getLibrayList().get(0).getBookName());
	        libraryDataBean.setBookPages(bookDetailsList.get(0).getLibrayList().get(0).getPages());
	        logger.debug("BookPages--->"+ bookDetailsList.get(0).getLibrayList().get(0).getPages());
	        libraryDataBean.setBookPrice(bookDetailsList.get(0).getLibrayList().get(0).getPrice());
	        logger.debug("BookPrice--->"+ bookDetailsList.get(0).getLibrayList().get(0).getPrice());
	        libraryDataBean.setBookDueType(bookDetailsList.get(0).getLibrayList().get(0).getDueType());
	        logger.debug("BookDueType--->"+ bookDetailsList.get(0).getLibrayList().get(0).getDueType());
	        libraryDataBean.setBookFee(bookDetailsList.get(0).getLibrayList().get(0).getPenaltyFee());
	        logger.debug("BookFee--->"+ bookDetailsList.get(0).getLibrayList().get(0).getPenaltyFee());
	        
	       } else {
	        libraryDataBean.setAuthorName("");
	        libraryDataBean.setBookCategory("");
	        libraryDataBean.setBookDescription("");
	        libraryDataBean.setBookeditions("");
	        libraryDataBean.setBookName("");
	        libraryDataBean.setBookPages("");
	        libraryDataBean.setBookPrice("");
	        libraryDataBean.setBookDueType("");
	        libraryDataBean.setBookFee("");
	       }
	      } else {
	       libraryDataBean.setAuthorName("");
	       libraryDataBean.setBookCategory("");
	       libraryDataBean.setBookDescription("");
	       libraryDataBean.setBookeditions("");
	       libraryDataBean.setBookName("");
	       libraryDataBean.setBookPages("");
	       libraryDataBean.setBookPrice("");
	       libraryDataBean.setBookDueType("");
	       libraryDataBean.setBookFee("");
	      }
	      if (bookDetailsList.get(0).getLibrayImageList() != null) {
	    	    if (bookDetailsList.get(0).getLibrayImageList().size() > 0) {
	    	     logger.debug("Inside ImagePath");
	    	     libraryDataBean.setParfilePathDate(bookDetailsList.get(0)
	    	       .getLibrayImageList().get(0).getDate());
	    	     libraryDataBean.setParfilePath(bookDetailsList.get(0)
	    	       .getLibrayImageList().get(0).getPath());
	    	     String file = paths.getString("sms.library.photo");
	    	     String pathname = (file
	    	       + format.format(libraryDataBean
	    	         .getParfilePathDate())
	    	       + paths.getString("path_context").toString() + libraryDataBean
	    	       .getParfilePath());
	    	     String encodestring = convertimage(pathname);
	    	     libraryDataBean.setParfilePath(encodestring);
	    	    } else {
	    	     libraryDataBean.setParfilePathDate(null);
	    	     libraryDataBean.setParfilePath("");
	    	    }
	    	   } else {
	    	    libraryDataBean.setParfilePathDate(null);
	    	    libraryDataBean.setParfilePath("");
	    	   }
	     }
	    }
	   } catch (Exception e) {
	    logger.warn(" exception - "+e);
	   }
	   return gson.toJson(libraryDataBean);
	  }
	  
	  /*Add book Update
	     @POST
	     @Path("/addbookUpdate")
	     @Consumes(MediaType.APPLICATION_JSON)
	     @Produces(MediaType.APPLICATION_JSON)
	     public String getaddbookupdate(String json)throws JSONException {
	      logger.debug("Callingadd book update Method");
	      Gson gson=new Gson();
	      JsonParser jsonParser = new JsonParser();
		   JsonElement element = jsonParser.parse(json);
		   String personID=element.getAsJsonObject().get("personID").getAsString();
		   String category=element.getAsJsonObject().get("category").getAsString();
		   String price=element.getAsJsonObject().get("price").getAsString();
		   String pages=element.getAsJsonObject().get("pages").getAsString();
		   String duetype=element.getAsJsonObject().get("duetype").getAsString();
		   String penaltyfee=element.getAsJsonObject().get("penaltyfee").getAsString();
		   String description=element.getAsJsonObject().get("description").getAsString();
		   String bookname=element.getAsJsonObject().get("bkname").getAsString();
		   String authorname=element.getAsJsonObject().get("authorname").getAsString();
		   String bookeditions=element.getAsJsonObject().get("bedition").getAsString();
		   try {
	         libraryDataBean.setBookCategory(category);
	         libraryDataBean.setBookPrice(price);
	         libraryDataBean.setBookPages(pages);
	         libraryDataBean.setBookDueType(duetype);
	         libraryDataBean.setBookFee(penaltyfee);
	         libraryDataBean.setBookDescription(description);
	         libraryDataBean.setBookName(bookname);
		     libraryDataBean.setAuthorName(authorname);
		     libraryDataBean.setBookeditions(bookeditions);
		     smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
		         if (personID != null) {
		        	 String status = smsservice.updateLibrary(personID, libraryDataBean);
		        	 libraryDataBean.setStatus(status);
		         }
	      } catch (Exception e) {
	       logger.warn(" exception - "+e);
	      }
	      return gson.toJson(libraryDataBean.getStatus());
	     }*/
	     	
	  
		/* Add book Update */
		@POST
		@Path("/addbookUpdate")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public String getaddbookupdate(String json) throws JSONException {
			logger.debug("Callingadd book update Method");
			Gson gson = new Gson();
			JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			String personID = element.getAsJsonObject().get("personID")
					.getAsString();
			String category = element.getAsJsonObject().get("category")
					.getAsString();
			String price = element.getAsJsonObject().get("price").getAsString();
			String pages = element.getAsJsonObject().get("pages").getAsString();
			String duetype = element.getAsJsonObject().get("duetype").getAsString();
			String penaltyfee = element.getAsJsonObject().get("penaltyfee")
					.getAsString();
			String description = element.getAsJsonObject().get("description")
					.getAsString();
			String bookname = element.getAsJsonObject().get("bkname").getAsString();
			String authorname = element.getAsJsonObject().get("authorname")
					.getAsString();
			String bookeditions = element.getAsJsonObject().get("bedition")
					.getAsString();
			String librarybookphoto = element.getAsJsonObject()
					.get("librarybookphoto").getAsString();
			try {
				libraryDataBean.setBookCategory(category);
				libraryDataBean.setBookPrice(price);
				libraryDataBean.setBookPages(pages);
				libraryDataBean.setBookDueType(duetype);
				libraryDataBean.setBookFee(penaltyfee);
				libraryDataBean.setBookDescription(description);
				libraryDataBean.setBookName(bookname);
				libraryDataBean.setAuthorName(authorname);
				libraryDataBean.setBookeditions(bookeditions);
				libraryDataBean.setParfilePath(librarybookphoto);
				smsservice = (SmsService) SpringApplicationContext
						.getBean("smsservice");
				if (personID != null) {
					String status = smsservice.updateLibrary(personID,
							libraryDataBean);
					libraryDataBean.setStatus(status);
				}
			} catch (Exception e) {
				  logger.error("Exception -->"+e.getMessage());
			}
			return gson.toJson(libraryDataBean.getStatus());
		}

	     /*Add book Delete*/
	     @POST
	     @Path("/addbookdetaildelete/{id}")
	     @Produces(MediaType.APPLICATION_JSON)
	     public String getaddbookdelete(@PathParam("id") String id,JSONObject object)throws JSONException {
	      logger.debug("Calling add book delete Method");
	      Gson gson=new Gson();
	      String personID=id;
	      String bookname = (String)object.get("bokna");
	   String authorname =  (String)object.get("bokau");
	   String bookeditions =  (String)object.get("boked");
	   try {
	         libraryDataBean.setBookName(bookname);
	     libraryDataBean.setAuthorName(authorname);
	     libraryDataBean.setBookeditions(bookeditions);
	     smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	        if (personID != null) {
	          String status = smsservice.deleteBook(personID, libraryDataBean);
	          libraryDataBean.setStatus(status);
	        }
	      } catch (Exception e) {
	       logger.warn(" exception - "+e);
	      }
	      return gson.toJson(libraryDataBean.getStatus());
	     }

	     
	     /*Borrower book view classlist*/
	     @GET
	     @Path("/borrowerbookclass/{id}")
	     @Produces(MediaType.APPLICATION_JSON)
	     public String getborrowerbookclass(@PathParam("id") String id) throws JSONException {
	      logger.debug("inside getborrower book class method calling");
	      Gson gson = new Gson();
	      String personID = id;
	      List<String> classList = null;
	      try {
	       if (personID != null) {
	        classList = new ArrayList<String>();
	        smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	        classList = smsservice.getClassNameList(personID);
	        Collections.sort(classList);
	        logger.debug("class list size" + classList.size());
	       }
	      } catch (Exception e) {
	       logger.warn(" exception - "+e);
	      }
	      return gson.toJson(classList);
	     }
	     
	     /*Borrower book insert studentlist*/
	     @GET
	     @Path("/borrowerbookinsertstudent/{id}/{classname}/{classSection}")
	     @Produces(MediaType.APPLICATION_JSON)
	     public String getinsertstudentid(@PathParam("id") String id,@PathParam("classname") String classname,@PathParam("classSection") String classSection) throws JSONException
	     {
	      logger.debug("inside getstudent id method calling");
	      Gson gson = new Gson();
	      String personID=id;
	      List<String> studentIDList=null;
	      String name= classname + "/" + classSection;
	      try{
	       smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	       if(personID != null && name != null){
	        studentIDList=new ArrayList<String>();
	        studentIDList = smsservice.getStudentRollNumber(personID, name);
	        logger.debug(studentIDList);
	       }
	      }
	      catch(Exception e){
	       logger.warn(" exception - "+e);
	      }
	      return gson.toJson(studentIDList);
	     }
	     
	     /*Borrower book insert category list*/
	     @GET
	     @Path("/borrbookinsertcategorylist")
	     @Produces(MediaType.APPLICATION_JSON)
	     public String getinsertcategorylist() throws JSONException
	     {
	      logger.debug("inside getstudent id method calling");
	      List<String> categoryList=null;
	      Gson gson=new Gson();
	      try{
	       categoryList=new ArrayList<String>();
	       smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	        categoryList.add("English");
	        categoryList.add("Spanish");
	        categoryList.add("Chinese");
	        categoryList.add("Malay");
	       logger.debug("category list----"+categoryList.size());
	      }
	      catch(Exception e){
	       logger.warn(" exception - "+e);
	      }
	      return gson.toJson(categoryList);
	     }
	     
	     @GET
	     @Path("/borrowerbookview/{id}/{studentname}/{studentid}")
	     @Produces(MediaType.APPLICATION_JSON)
	     public String getborrowerbookview(@PathParam("id") String id,@PathParam("studentname") String studentname,@PathParam("studentid") String studentid) throws JSONException
	     {
	      logger.debug("inside getstudent id method calling");
	      Gson gson = new Gson();
	      String personID=id;
	      List<LibraryDataBean> BorrowerBookList=new ArrayList<LibraryDataBean>();
	      String name= studentname + "/" + studentid;
	      try{
	       smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	       if(personID != null){
	        BorrowerBookList = smsservice.getBoorowerBookView(personID, name);
	        logger.debug("BorrowerBookList" + BorrowerBookList.size());
	       }
	      }
	      catch(Exception e){
	       logger.warn(" exception - "+e);
	      }
	      return gson.toJson(BorrowerBookList);
	     }
	     
	    /* @GET
	     @Path("/borrowview/{id}/{studentname}/{studentid}")
	     @Produces(MediaType.APPLICATION_JSON)
	     public String borrowview(@PathParam("id") String id,@PathParam("studentname") String studentname,@PathParam("studentid") String studentid) throws JSONException
	     {
	      logger.debug("inside getstudent id method calling");
	      Gson gson = new Gson();
	      String personID=id;
	      List<LibraryDataBean> BorrowerBookList=new ArrayList<LibraryDataBean>();
	      String name= studentname + "/" + studentid;
	      try{
	       smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	       if(personID != null){
	        BorrowerBookList = smsservice.getBoorowerBookView(personID, name);
	        logger.debug("BorrowerBookList" + BorrowerBookList.size());
	       }
	      }
	      catch(Exception e){
	       logger.warn(" exception - "+e);
	      }
	      return gson.toJson(BorrowerBookList);
	     }*/


		/*Borrower book insert category value change*/
	     @GET
	     @Path("/borrbookinsertcategory/{id}/{category}/{studid}/{studentname}")
	     @Produces(MediaType.APPLICATION_JSON)
	     public String getinsertcategorychange(@PathParam("id") String id,@PathParam("category") String category,@PathParam("studid") String studid,@PathParam("studentname") String studentname) throws JSONException
	     {
	      logger.debug("inside Borrowerbook insertcategorychange method calling");
	      String personID=id;
	      String stuid=studentname + "/" + studid;
	      Gson gson= new Gson();
	      List<LibraryDataBean> resultList=null;
	      List<String> availableBook = null;
	      String categoryname= category;
	      try{
	       smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	       libraryDataBean.setLibStudID(stuid);
	       logger.debug("student id--->"+libraryDataBean.getLibStudID());
	       if(personID != null){
	        resultList=new ArrayList<LibraryDataBean>();
	        availableBook=new ArrayList<String>();
	        resultList = smsservice.getBookListView(personID, categoryname);
	        logger.debug(resultList.size());
	        if (resultList.size() > 0) {
	         for (int i = 0; i < resultList.size(); i++) {
	          logger.debug("----" + resultList.get(i).getAuthorName());
	          String book = resultList.get(i).getBookName() + "~" + resultList.get(i).getAuthorName() + "/"
	            + resultList.get(i).getBookeditions();
	          availableBook.add(book);
	          logger.debug("available book--->"+availableBook.size());
	         }
	        } 
	       
	       }
	      }
	      catch(Exception e){
	       logger.warn(" exception - "+e);
	      }
	      return gson.toJson(availableBook);
	     }
	     
	     @GET
	     @Path("/getbarrowebook/{id}/{category}/{studid}")
	     @Produces(MediaType.APPLICATION_JSON)
	     public String getbarrowebook(@PathParam("id") String id,@PathParam("category") String category,@PathParam("studid") String studid) throws JSONException
	     {
	    	 String personID=id;
		      String stuid=studid;
		      Gson gson= new Gson();
		      String categoryname= category;
		      List<String> borrwoedBook = null;
		      try{
		    	  smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
			       libraryDataBean.setLibStudID(stuid);
			       borrwoedBook=new ArrayList<String>();
			       if(personID != null){
			    	   if(libraryDataBean.getLibStudID() != null || !libraryDataBean.getLibStudID().equalsIgnoreCase("")) {
				    	     borrwoedBook = smsservice.getBorrowedBook(personID, categoryname, libraryDataBean.getLibStudID());  
				    	     logger.debug("barroeBook details-----"+borrwoedBook.size());
				       } 
			       }
		      }catch(Exception e){
		    	  logger.warn(" exception - "+e);
		      }
		      return gson.toJson(borrwoedBook);
	     }
	     
	     
	    /* @POST
	     @Path("/insertBorrowed")
	     @Produces(MediaType.APPLICATION_JSON)
	  
	     public String insertBorrowed(String json) throws JSONException {
	    	  Gson gson= new Gson();
	    	  JsonParser jsonParser = new JsonParser();
			   JsonElement element = jsonParser.parse(json);
			   String personID=element.getAsJsonObject().get("personID").getAsString();
			   String stuid=element.getAsJsonObject().get("stuid").getAsString();
			   String categoryname=element.getAsJsonObject().get("bkcategory").getAsString();
	    	JsonElement bookroot=new JsonParser().parse(element.getAsJsonObject().get("date").getAsString()); 
			     JsonArray book = bookroot.getAsJsonArray();
		      logger.debug("json array-----"+book.size());
		      libraryDataBean.setLibStudID(stuid);
		      libraryDataBean.setBookCategory(categoryname);
		      List<LibraryDataBean> resultList=null;
		      List<String> availableBook=null;
		     
		      try{
		    	  resultList=new ArrayList<LibraryDataBean>();
		    	  availableBook=new ArrayList<String>();
		    	  smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
		      if (personID != null) {
		    	  resultList = smsservice.getBookListView(personID, categoryname);
			        logger.debug(resultList.size());
			        if (resultList.size() > 0) {
			         for (int i = 0; i < resultList.size(); i++) {
			          logger.debug("----" + resultList.get(i).getAuthorName());
			          String book2 = resultList.get(i).getBookName() + "~" + resultList.get(i).getAuthorName() + "/"
			            + resultList.get(i).getBookeditions();
					availableBook.add(book2);
			          logger.debug("available book--->"+availableBook.size());
			         }
			        } 
		    	  libraryDataBean.setTargetList(availableBook);
		    		String status = smsservice.insertBookBorrowed(personID, libraryDataBean);
					if(status.equalsIgnoreCase("Success")){
						libraryDataBean.setStatus(status);
					}
		      }
		      }catch(Exception e){
		    	  logger.warn(" exception - "+e);
		      }
	    	return gson.toJson(libraryDataBean.getStatus()); 
	     }*/
	     
	     @POST
	 	@Path("/insertBorrowed")
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String insertBorrowed(String json) throws JSONException {
	 		Gson gson = new Gson();
	 		JsonParser jsonParser = new JsonParser();
	 		List<String> targetList = new ArrayList<String>();
	 		LibraryDataBean libraryDataBean = new LibraryDataBean();
	 		JsonElement element = jsonParser.parse(json);
	 		String personID = element.getAsJsonObject().get("personID")
	 				.getAsString();
	 		String stuid = element.getAsJsonObject().get("stuid").getAsString();
	 		String categoryname = element.getAsJsonObject().get("bkcategory")
	 				.getAsString();
	 		JSONObject js = new JSONObject(json);
	 		JSONArray jsonArray = js.getJSONArray("barrowbook");
	 		// logger.debug("json array-----"+book.size());
	 		libraryDataBean.setLibStudID(stuid);
	 		libraryDataBean.setBookCategory(categoryname);
	 		List<LibraryDataBean> resultList = null;
	 		List<String> availableBook = null;
	 		try {
	 			for (int i = 0; i < jsonArray.length(); i++) {
	 				//LibraryDataBean librarybean = new LibraryDataBean();
	 				String studentIDs = (String) jsonArray.get(i);
	 				targetList.add(studentIDs);
	 			}

	 			resultList = new ArrayList<LibraryDataBean>();
	 			availableBook = new ArrayList<String>();
	 			smsservice = (SmsService) SpringApplicationContext
	 					.getBean("smsservice");
	 			if (personID != null) {
	 				resultList = smsservice.getBookListView(personID, categoryname);
	 				logger.debug(resultList.size());
	 				if (resultList.size() > 0) {
	 					for (int i = 0; i < resultList.size(); i++) {
	 						logger.debug("----" + resultList.get(i).getAuthorName());
	 						String book2 = resultList.get(i).getBookName() + "~"
	 								+ resultList.get(i).getAuthorName() + "/"
	 								+ resultList.get(i).getBookeditions();
	 						availableBook.add(book2);
	 						logger.debug("available book--->"
	 								+ availableBook.size());
	 					}
	 				}
	 				libraryDataBean.setTargetList(targetList);
	 				String status = smsservice.insertBookBorrowed(personID,
	 						libraryDataBean);
	 				if (status.equalsIgnoreCase("Success")) {
	 					libraryDataBean.setStatus(status);
	 				}
	 			}
	 		} catch (Exception e) {
	 			  logger.error("Exception -->"+e.getMessage());
	 		}
	 		return gson.toJson(libraryDataBean.getStatus());
	 	}

	     /*PREMA CODE END*/
	     
	     @GET
	 	@Path("/getAllTeacherView/{id}")
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String getAllTeacherView(@PathParam("id") String id) throws JSONException
	 	{
	 		logger.debug("id  "+id);
	 		List<TeacherDataBean> teacherList=null;
	 		String rollnumber = "";
	 		String personID=id;
	 		String role="";
	 		Gson gson = new Gson();
	 		try
	 		{
	 			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 			role=smsservice.getRoll(personID);
	 			if (personID != null) 
	 			{
	 				if (role.equalsIgnoreCase("Admin")) 
	 				{
	 					logger.debug("Inside getAllTeacherView admin");
	 					teacherList = new ArrayList<TeacherDataBean>();
	 					teacherList = smsservice.getAllTeacherInfo(personID,teacherDataBean);
	 				}	
	 				else if (role.equalsIgnoreCase("Student")) {
	 					rollnumber = smsservice.getRollNumber(personID);
	 					studClass = smsservice.getStudClsSection(rollnumber);
	 					teacherDataBean.setTeaclsSection(studClass);
	 					teacherList = new ArrayList<TeacherDataBean>();
	 					teacherList = smsservice.getAllTeacherInfo(personID,teacherDataBean);
	 				} 
	 				else if (role.equalsIgnoreCase("Parent")) {
	 					rollnumber = smsservice.getRollNumber(personID);
	 					studClass = smsservice.getStudClsSection(rollnumber);
	 					teacherDataBean.setTeaclsSection(studClass);
	 					teacherList = new ArrayList<TeacherDataBean>();
	 					teacherList = smsservice.getAllTeacherInfo(personID,teacherDataBean);
	 				}
	 			}
	 		logger.debug("sizee -- "+teacherList.size());
	 		}
	 		catch(Exception e)
	 		{
	 			logger.warn(" exception - "+e);
	 		}
	 		return gson.toJson(teacherList);
	 	}


	 	@POST
	 	@Path("/getTeacherInfo/{id}")
	 	
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String getTeacherInfo(@PathParam("id") String id,String json) throws JSONException
	 	{
	 		Gson gson = new Gson();
	 		List<TeacherDataBean> teacherViewList=null;
	 		String personID=id;
	 		 JsonParser jsonParser = new JsonParser();
			   JsonElement element = jsonParser.parse(json);
			   String teaID=element.getAsJsonObject().get("teaid").getAsString();
	 	//	SimpleDateFormat date=new SimpleDateFormat("dd-MM-yyyy");
	 		
	 		
	 		try
	 		{
	 			if (personID != null && teaID != null) 
	 			{
	 				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 				teacherViewList = new ArrayList<TeacherDataBean>();
	 				teacherViewList = smsservice.getTeacherInfo(personID, teaID);
	 				logger.debug("size -- "+teacherViewList.size());
	 				if (teacherViewList.size() > 0) {
	 					try {
	 						if (teacherViewList.get(0).getTeacherDet().size() > 0) {
	 							teacherDataBean
	 									.setTeaFirstName(teacherViewList.get(0).getTeacherDet().get(0).getFirstName());
	 							teacherDataBean.setTeaLastName(teacherViewList.get(0).getTeacherDet().get(0).getLastName());
	 							teacherDataBean.setNames(
	 									teacherDataBean.getTeaFirstName() + " " + teacherDataBean.getTeaLastName());
	 							teacherDataBean
	 									.setTeaFatherName(teacherViewList.get(0).getTeacherDet().get(0).getFatherName());
	 							teacherDataBean
	 									.setTeaMotherName(teacherViewList.get(0).getTeacherDet().get(0).getMotherName());
	 							teacherDataBean.setTeaDob(teacherViewList.get(0).getTeacherDet().get(0).getDob());
	 							
	 							teacherDataBean.setTeaGender(teacherViewList.get(0).getTeacherDet().get(0).getGender());
	 							teacherDataBean.setTeaEduQualification(
	 									teacherViewList.get(0).getTeacherDet().get(0).getQualification());
	 							teacherDataBean.setTeaEmail(teacherViewList.get(0).getTeacherDet().get(0).getEmailId());
	 							teacherDataBean.setTeaID(
	 									teacherViewList.get(0).getTeacherDet().get(0).getTeacher().getRollNumber());
	 							teacherDataBean
	 									.setTeaPercentage(teacherViewList.get(0).getTeacherDet().get(0).getPercentage());
	 							teacherDataBean
	 									.setTeaPhoneNo(teacherViewList.get(0).getTeacherDet().get(0).getPhoneNumber());
	 							teacherDataBean.setTeaPosition(teacherViewList.get(0).getTeacherDet().get(0).getPost());
	 							teacherDataBean.setTeaPermanentAddress(
	 									teacherViewList.get(0).getTeacherDet().get(0).getAddress2_Street());
	 							teacherDataBean.setTeaPresentAddress(
	 									teacherViewList.get(0).getTeacherDet().get(0).getAddress1_Street());
	 							teacherDataBean
	 									.setTeaWorkingHour(teacherViewList.get(0).getTeacherDet().get(0).getWorkingHour());
	 							teacherDataBean.setTeaYearOfPassing(
	 									teacherViewList.get(0).getTeacherDet().get(0).getYearOfPassing());
	 							
	 						} else {
	 							teacherDataBean.setTeaFirstName("");
	 							teacherDataBean.setTeaLastName("");
	 							teacherDataBean.setTeaFatherName("");
	 							teacherDataBean.setTeaMotherName("");
	 							teacherDataBean.setTeaDob(null);
	 							teacherDataBean.setTeaGender("");
	 							teacherDataBean.setTeaEduQualification("");
	 							teacherDataBean.setTeaEmail("");
	 							teacherDataBean.setTeaID("");
	 							teacherDataBean.setTeaPercentage("");
	 							teacherDataBean.setTeaPhoneNo("");
	 							teacherDataBean.setTeaPosition("");
	 							teacherDataBean.setTeaPermanentAddress("");
	 							teacherDataBean.setTeaPresentAddress("");
	 							teacherDataBean.setTeaWorkingHour("");
	 							teacherDataBean.setTeaYearOfPassing("");
	 							teacherDataBean.setTeaPermanentZip("");
	 							teacherDataBean.setTeaPresentZip("");
	 							teacherDataBean.setTeaAddress1("");
	 							teacherDataBean.setTeaAddress2("");
	 							teacherDataBean.setTeaPermanentState("");
	 							teacherDataBean.setTeaPresentState("");
	 							teacherDataBean.setTeaFile(null);
	 						}
	 						if (teacherViewList.get(0).getTeacherImgPath().size() > 0) {
	 							teacherDataBean.setPath(teacherViewList.get(0).getTeacherImgPath().get(0).getPath());
	 							teacherDataBean.setPathDate(teacherViewList.get(0).getTeacherImgPath().get(0).getDate());
	 							String file=paths.getString("sms.teacher.teacherphoto");
	 							String pathname=(file+format.format(teacherDataBean.getPathDate())+paths.getString("path_context").toString()+teacherDataBean.getPath());
	 							String encodestring=convertimage(pathname);
								teacherDataBean.setPath(encodestring);
	 						} else {
	 							teacherDataBean.setPath("");
	 							teacherDataBean.setPathDate(null);
	 						}
	 						logger.debug(teacherViewList.get(0).getTeacherSub().size());
	 						if (teacherViewList.get(0).getTeacherSub().size() > 0) {
	 							for (int j = 0; j < teacherViewList.get(0).getTeacherSub().size(); j++) {

	 								teacherDataBean.getTeaSubjectTargetList()
	 										.add(teacherViewList.get(0).getTeacherSub().get(j).getSubject().getSujectName()
	 												+ "/" + teacherViewList.get(0).getTeacherSub().get(j).getSubject()
	 														.getSubjectCode());
	 							}
	 						} else {
	 							teacherDataBean.getTeaSubjectTargetList().clear();
	 						}
	 						if (teacherViewList.get(0).getTeacherCls().size() > 0) {
	 							for (int m = 0; m < teacherViewList.get(0).getTeacherCls().size(); m++) {
	 								teacherDataBean.getTeaClassTargetList()
	 										.add(teacherViewList.get(0).getTeacherCls().get(m).getClazz().getClassName()
	 												+ "/" + teacherViewList.get(0).getTeacherCls().get(m).getClazz()
	 														.getClassSection());

	 							}

	 						} else {
	 							teacherDataBean.getTeaClassTargetList().clear();
	 						}

	 						logger.debug("state 1" + teacherDataBean.getTeaPresentState());
	 						logger.debug("state 2" + teacherDataBean.getTeaPermanentState());
	 					} catch (NullPointerException e) {
	 						logger.warn(" exception - "+e);
	 					}
	 				}
	 			}
	 		}
	 		catch(Exception e)
	 		{
	 			logger.warn(" exception - "+e);
	 		}
	 		return gson.toJson(teacherDataBean);
	 	}
	 	
	 	@POST
	 	@Path("/updateTeacher")
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String updateTeacher(String json) throws JSONException
	 	{
	 		Gson gson=new Gson();
	 		String status="Fail";String emailStatus="Fail";
	 		JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			 JSONObject js=new JSONObject(json);
			String firstname=element.getAsJsonObject().get("name").getAsString();
			String lastname=element.getAsJsonObject().get("lastname").getAsString();
			String fathername=element.getAsJsonObject().get("fathername").getAsString();
			String mothername=element.getAsJsonObject().get("mothername").getAsString();
			String dob=element.getAsJsonObject().get("dob").getAsString();
			String gender=element.getAsJsonObject().get("gender").getAsString();
			String address=element.getAsJsonObject().get("address").getAsString();
			String address1=element.getAsJsonObject().get("address1").getAsString();
			String phonenumber=element.getAsJsonObject().get("phonenumber").getAsString();
			String mailid=element.getAsJsonObject().get("mailid").getAsString();
			String id=element.getAsJsonObject().get("id").getAsString();
			JSONArray subjectArray=js.getJSONArray("subject");
			JSONArray classArray=js.getJSONArray("class");
			String workinghour=element.getAsJsonObject().get("workingHour").getAsString();
			String personID=element.getAsJsonObject().get("personID").getAsString();
			String position=element.getAsJsonObject().get("position").getAsString();
			String qualification=element.getAsJsonObject().get("qualification").getAsString();
			String percentage=element.getAsJsonObject().get("percentage").getAsString();
			String passout=element.getAsJsonObject().get("passout").getAsString();	 		
	 		List<String> classList=new ArrayList<String>();
	 		List<String> subjectList=new ArrayList<String>();
	 		String filepath=element.getAsJsonObject().get("path").getAsString();
	 		try
	 		{
	 			for (int i = 0; i < classArray.length(); i++) {
	 				classList.add(classArray.getString(i));
				}
	 			for (int i = 0; i < subjectArray.length(); i++) {
	 				subjectList.add(subjectArray.getString(i));
				}
	 			teacherDataBean.setTeaFirstName(firstname);
	 			//teacherDataBean.setTeaClass(classs);
	 			teacherDataBean.setTeaAddress1(address);
	 			teacherDataBean.setTeaAddress2(address1);
	 			teacherDataBean.setTeaEduQualification(qualification);
	 			teacherDataBean.setTeaEmail(mailid);
	 			teacherDataBean.setTeaFatherName(fathername);
	 			teacherDataBean.setTeaMotherName(mothername);
	 			teacherDataBean.setTeaGender(gender);
	 			teacherDataBean.setTeaID(id);
	 			teacherDataBean.setTeaPercentage(percentage);
	 			teacherDataBean.setTeaYearOfPassing(passout);
	 			if(dob.equals("") || dob==null){
	 				teacherDataBean.setTeaDob(null);
	 			}else{
	 				teacherDataBean.setTeaDob(format.parse(dob));
	 			}
	 			teacherDataBean.setTeaPresentAddress(address);
	 			teacherDataBean.setTeaPermanentAddress(address1);
	 			teacherDataBean.setTeaLastName(lastname);
	 			teacherDataBean.setTeaSubjectTargetList(subjectList);
	 			teacherDataBean.setTeaClassTargetList(classList);	
	 			teacherDataBean.setTeaPhoneNo(phonenumber);
	 			teacherDataBean.setTeaPosition(position);
	 			teacherDataBean.setTeaWorkingHour(workinghour);
	 			if(filepath==null || filepath.equalsIgnoreCase(""))
	 				teacherDataBean.setTeafilePath(null);
	 			else
	 				teacherDataBean.setTeafilePath(filepath);
	 			if (personID != null) 
	 			{
	 				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 				status = smsservice.updateTeacher(personID, teacherDataBean);
	 				System.out.println("status check----"+status);
	 				teacherDataBean.setReturnStatus(status);
	 				if(status.equalsIgnoreCase("Success")){
	 						List<TeacherDataBean> ImageListPath = new ArrayList<TeacherDataBean>();
	 						ImageListPath = smsservice.getImagePath(personID, teacherDataBean.getTeaID());
	 						System.out.println("list=======size"+ImageListPath);
	 						/*String pdfStatus = GeneratePdfMail.teacherupdategeneratePdf(teacherDataBean,ImageListPath);
	 						logger.debug("pdf----status---"+pdfStatus);
	 						if (pdfStatus.equalsIgnoreCase("Success")) {*/
	 							MailSendJDBC.teacherUpdate(teacherDataBean,teacherDataBean.getSchoolName(),teacherDataBean.getSchoolID());
	 							emailStatus="Success";
	 							 logger.debug("email----status---"+emailStatus);
	 						}
	 					//}
	 					teacherDataBean.setReturnStatus(status);
	 				}			
	 		}
	 		catch(Exception e)
	 		{
	 			logger.warn(" exception - "+e);
	 			  logger.error("Exception -->"+e.getMessage());
	 		}
	 		return gson.toJson(teacherDataBean.getReturnStatus());
	 	}	
	 	
	 	
	 	@POST
	 	@Path("/deleteTeacher/{id}")	 	
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String deleteTeacher(@PathParam("id") String id,String json) throws JSONException
	 	{
	 		Gson gson=new Gson();
	 		String status="";
	 		String personID=id;
	 		JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			String teaID=element.getAsJsonObject().get("teaid").getAsString();
	 		try
	 		{
	 			teacherDataBean.setTeaID(teaID);
	 			if (personID != null && teacherDataBean.getTeaID() != null)
	 			{
	 				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 				status = smsservice.deleteTeacher(personID, teacherDataBean);
	 				logger.debug(status);
	 				teacherDataBean.setReturnStatus(status);	 				
	 			}
	 		}
	 		catch(Exception e)
	 		{
	 			logger.warn(" exception - "+e);
	 		}
	 		return gson.toJson(teacherDataBean.getReturnStatus());
	 	}
	 	@POST
	 	@Path("/getStudentInfo/{id}/{class}/{sec}")
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String getStudentInfo(@PathParam("id") String id,@PathParam("class") String pclass,@PathParam("sec") String sec) throws JSONException
	 	{
	 		Gson gson = new Gson();
	 		List<StudentDataBean> studentList = null;
	 		StudentDataBean studentDataBean=new StudentDataBean();
	 		String personID=id;
	 		studentDataBean.setTeaclssection(pclass+"/"+sec);
	 		try
	 		{
	 			if (personID != null) 
	 			{
	 				studentList=new ArrayList<StudentDataBean>();
	 				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 				studentList = smsservice.getStudentInfo(personID, studentDataBean);
	 			}
	 		}
	 		catch(Exception e)
	 		{
	 			logger.warn(" exception - "+e);
	 		}
	 		return gson.toJson(studentList);
	 	}
	 	
	 	@POST
	 	@Path("/getStudentView/{id}")
	 	@Consumes(MediaType.APPLICATION_JSON)
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String getStudentView(@PathParam("id") String id,String json) throws JSONException
	 	{
	 		Gson gson = new Gson();
	 		String personID=id;
	 		StudentDataBean studentDataBean=new StudentDataBean();
	 		JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			String stuRollNo=element.getAsJsonObject().get("studendrollno").getAsString();
	 		studentDataBean.setStuRollNo(stuRollNo);
	 		List<StudentDetail> studentList=null;
	 		String classname;
	 		List<StudentDataBean> student=null;
	 		try
	 		{
	 			studentList = new ArrayList<StudentDetail>();
	 			student=new ArrayList<StudentDataBean>();
	 			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 			studentList = smsservice.getStudentView(studentDataBean,personID);
	 			logger.debug("-----studentList size-----" + studentList.size());
	 			classname = smsservice.getStudentClass(studentDataBean.getStuRollNo(),personID);
	 			logger.debug("-----classname-----" + classname);
	 			
	 			
	 			if (studentList.size() > 0) {
	 				try
	 				{
	 					studentDataBean.setNames(studentList.get(0).getFirstName() + " " + studentList.get(0).getLastName());
	 					studentDataBean.setStuCls(classname);
	 					studentDataBean.setStuRollNo(stuRollNo);
	 					studentDataBean.setStuLastName(studentList.get(0).getLastName());
	 					studentDataBean.setStuFatherName(studentList.get(0).getFatherName());
	 					studentDataBean.setStuMotherName(studentList.get(0).getMotherName());
	 					studentDataBean.setStuFatherIncome(studentList.get(0).getFatherIncome());
	 					studentDataBean.setStuFatherOccu(studentList.get(0).getFatherOccupation());
	 					studentDataBean.setStuMotherOccu(studentList.get(0).getMotherOccupation());
	 					studentDataBean.setStuDob(studentList.get(0).getDob());
	 					studentDataBean.setStuGender(studentList.get(0).getGender());
	 					studentDataBean.setStuPresentAddress(studentList.get(0).getAddress1_Street());
	 					studentDataBean.setStuPermanentAddress(studentList.get(0).getAddress2_Street());
	 					studentDataBean.setPresentAdd(studentList.get(0).getAddress1_Street() + ","
	 							+ studentList.get(0).getPostcode1().getState().getStateName() + ","
	 							+ studentList.get(0).getPostcode1().getPostcode());
	 					logger.debug(""+studentDataBean.getNames());
	 					logger.debug(""+studentDataBean.getStuCls());
	 					logger.debug(""+studentDataBean.getStuLastName());
	 					logger.debug(""+studentDataBean.getStuRollNo());
	 					logger.debug(""+studentDataBean.getStuMotherName());
	 					logger.debug(""+studentDataBean.getStuFatherName());
	 					logger.debug(""+studentDataBean.getStuFatherIncome());
	 					logger.debug(""+studentDataBean.getStuFatherOccu());
	 					logger.debug(""+studentDataBean.getStuMotherOccu());
	 					logger.debug(""+studentDataBean.getStuGender());
	 					logger.debug(""+studentDataBean.getStuDob());
	 					logger.debug(""+studentDataBean.getStuPresentAddress());
	 					logger.debug(""+studentDataBean.getStuPermanentAddress());
	 				student.add(studentDataBean);
	 				}
	 				catch(Exception e)
	 				{
	 					logger.warn(" exception - "+e);
	 				}
	 			}
	 		}
	 		catch(Exception e)
	 		{
	 			logger.warn(" exception - "+e);
	 		}
	 		return gson.toJson(studentList);
	 	}
	 	
	 	//STUDENT UPDATE
 		@POST
 		@Path("/getStudentDetails/{id}")
 		
 		@Produces(MediaType.APPLICATION_JSON)
 		public String getStudentDetails(@PathParam("id") String id,String json) throws JSONException
 		{
 			Gson gson=new Gson();
 			logger.debug("-----inside student Edit--------");
 			String personID=id;
 			JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			String stuRollNo=element.getAsJsonObject().get("studendrollno").getAsString();
 			List<StudentDataBean> editList = null;
 			StudentDataBean studentDataBean=new  StudentDataBean();
 			String classname;
 			try {
 				if(personID!=null){
 				editList=new ArrayList<StudentDataBean>();
 				
 				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
 				editList = smsservice.getStudentDetails(stuRollNo,studentDataBean);
 				if(editList.size()>0){
 					if(editList.get(0).getStudentlist().size()>0){
 						studentDataBean.setStuRollNo(stuRollNo);
 						logger.debug("list size----"+editList.get(0).getStudentlist().size());
 						studentDataBean.setStuFirstName(editList.get(0).getStudentlist().get(0).getFirstName());
 						studentDataBean.setStuLastName(editList.get(0).getStudentlist().get(0).getLastName());
 						studentDataBean.setStuFatherName(editList.get(0).getStudentlist().get(0).getFatherName());
 						studentDataBean.setStuMotherName(editList.get(0).getStudentlist().get(0).getMotherName());
 						studentDataBean.setStuGender(editList.get(0).getStudentlist().get(0).getGender());
 						studentDataBean.setStuDob(editList.get(0).getStudentlist().get(0).getDob());
 						studentDataBean.setStuPresentAddress(editList.get(0).getStudentlist().get(0).getAddress1_Street());
 						studentDataBean.setStuPermanentAddress(editList.get(0).getStudentlist().get(0).getAddress2_Street());
 						studentDataBean.setStuFatherIncome(editList.get(0).getStudentlist().get(0).getFatherIncome());
 						studentDataBean.setStuFatherOccu(editList.get(0).getStudentlist().get(0).getFatherOccupation());
 						studentDataBean.setStuMotherOccu(editList.get(0).getStudentlist().get(0).getMotherOccupation());
 						classname = smsservice.getStudentClass(stuRollNo,personID);
 						logger.debug("-----classname-----" + classname);
 						studentDataBean.setClassName(classname);
 						if(editList.get(0).getImageList().size()>0){
 							studentDataBean.setPath(editList.get(0).getImageList().get(0).getPath());
 							studentDataBean.setPathDate(editList.get(0).getImageList().get(0).getDate());
 							String file=paths.getString("sms.student.photo");
 							String pathname=(file+format.format(studentDataBean.getPathDate())+paths.getString("path_context").toString()+studentDataBean.getPath());
 							String encodestring=convertimage(pathname);
 							studentDataBean.setPath(encodestring);
 						}else{
 							studentDataBean.setPath(null);
 							studentDataBean.setPathDate(null);
 						}
 					}
 					}
 				}	
 				
 			} catch (Exception e)
 			{
 				logger.warn("Exception -->"+e.getMessage());
 				logger.warn(" exception - "+e);
 			}
 			return gson.toJson(studentDataBean);

 		}
	 	
	 	@POST
	 	@Path("/getStudentDelete/{id}")	 	
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String getStudentDelete(@PathParam("id") String id,String json) throws JSONException
	 	{
	 		Gson gson=new Gson();
	 		String status="";
	 		StudentDataBean studentDataBean=new StudentDataBean();
	 		String personID=id;
	 		JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			String stuRollNo=element.getAsJsonObject().get("stuRollNo").getAsString();
	 		try
	 		{
	 			studentDataBean.setStuRollNo(stuRollNo);
	 			if (personID != null && studentDataBean.getStuRollNo() != null) 
	 			{
	 				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 				status = smsservice.getStudentDelete(personID,studentDataBean);
	 				logger.debug(status);
	 				studentDataBean.setReturnStatus(status);
	 			}
	 		}
	 		catch(Exception e)
	 		{
	 			logger.warn(" exception - "+e);
	 		}
	 		return gson.toJson(studentDataBean.getReturnStatus());
	 	}
	 	
	 	//27/7
	 	
	 	@POST
	 	@Path("/getParentInfo/{id}/{class}/{sec}")
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String getParentInfo(@PathParam("id") String id,@PathParam("class") String pclass,@PathParam("sec") String sec) throws JSONException
	 	{
	 		Gson gson = new Gson();
	 		String status;
	 		List<ParentsDataBean> patentTableList = null;
	 		ParentsDataBean parentsDataBean=new ParentsDataBean();
	 		String personID=id;
	 		parentsDataBean.setParStuClass(pclass+"/"+sec);
	 		try
	 		{
	 			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 			status = smsservice.getRoll(personID);
	 			logger.debug("status -- "+status+""+parentsDataBean.getParStuClass());
	 			if (personID != null) {
	 				if (status.equalsIgnoreCase("Admin"))
	 				{
	 					patentTableList = smsservice.getParentInfo(personID, parentsDataBean);
	 					logger.debug("patentTableList -- "+patentTableList.size());
	 				} 
	 				else if (status.equalsIgnoreCase("Teacher")) 
	 				{
	 					patentTableList = smsservice.getParentInfo(personID, parentsDataBean);
	 					logger.debug("patentTableList -- "+patentTableList.size());
	 					
	 				}
	 			} 
	 		}
	 		catch(Exception e)
	 		{
	 			logger.warn(" exception - "+e);
	 		}
	 		return gson.toJson(patentTableList);
	 	}
	 	
	 	
	 	@POST
	 	@Path("/getParentDetilsList/{id}")
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String getParentDetilsList(@PathParam("id") String id,String json) throws JSONException
	 	{
	 		Gson gson = new Gson();
	 		List<ParentsDataBean> parentList = null;
	 		ParentsDataBean parentsDataBean=new ParentsDataBean();
	 		String personID=id;
	 		JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			String pid=element.getAsJsonObject().get("parentid").getAsString();
	 		logger.debug("id----check==="+pid);
	 		parentsDataBean.setParParentID(pid);
	 		try
	 		{
	 			parentList = new ArrayList<ParentsDataBean>();
	 			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 			logger.debug("-----Inside call Parents view method ------" + parentsDataBean.getParParentID());
	 			if (personID != null) {
	 				parentList = smsservice.getParentDetilsList(personID, parentsDataBean);
	 				if (parentList.size() > 0) {
	 					if (parentList.get(0).getParentList().size() > 0) {
	 						parentsDataBean.setParParentID(parentList.get(0).getParentList().get(0).getRollNumber());
	 					} else {
	 						parentsDataBean.setParParentID("");
	 					}
	 					logger.debug("parentList.get(0).getParentDetailsList().size() "+parentList.get(0).getParentDetailsList().size());
	 					logger.debug("parentList.get(0).getParentImageList().size() "+parentList.get(0).getParentImageList().size() );
	 					logger.debug("parentList.get(0).getStudentParentList().size()"+parentList.get(0).getStudentParentList().size());
	 					if (parentList.get(0).getParentDetailsList().size() > 0) {
	 						parentsDataBean.setName(parentList.get(0).getParentDetailsList().get(0).getFirstName() + " "
	 								+ parentList.get(0).getParentDetailsList().get(0).getLastName());
	 						parentsDataBean.setParEmail(parentList.get(0).getParentDetailsList().get(0).getEmaiId());
	 						parentsDataBean
	 								.setParentRelation(parentList.get(0).getParentDetailsList().get(0).getRelation());
	 						parentsDataBean.setParPhoneNo(parentList.get(0).getParentDetailsList().get(0).getPhoneNumber());
	 						parentsDataBean.setParFirstName(parentList.get(0).getParentDetailsList().get(0).getFirstName());
	 						parentsDataBean.setParLastName(parentList.get(0).getParentDetailsList().get(0).getLastName());
	 						parentsDataBean.setParStuClass(parentList.get(0).getStudentClass().get(0));
	 						
	 					} else {
	 						parentsDataBean.setName("");
	 						parentsDataBean.setParEmail("");
	 						parentsDataBean.setParentRelation("");
	 						parentsDataBean.setParPhoneNo("");
	 					}
	 					if (parentList.get(0).getParentImageList().size() > 0) {
	 						parentsDataBean.setParfilePath(parentList.get(0).getParentImageList().get(0).getPath());
	 						parentsDataBean.setParPathDate(parentList.get(0).getParentImageList().get(0).getDate());
	 						String file=paths.getString("sms.parents.photo");
 							String pathname=(file+format.format(parentsDataBean.getParPathDate())+paths.getString("path_context").toString()+parentsDataBean.getParfilePath());
 							String encodestring=convertimage(pathname);
 							parentsDataBean.setParfilePath(encodestring);
	 					} else {
	 						parentsDataBean.setParfilePath("");
	 						parentsDataBean.setParPathDate(null);
	 					}
	 					logger.debug("student size "+parentList.get(0).getStudentParentList().size());
	 					if (parentList.get(0).getStudentParentList().size() > 0) {
	 						for (int i = 0; i < parentList.get(0).getStudentParentList().size(); i++) {
	 							logger.debug("id "+
	 									parentList.get(0).getStudentParentList().get(i).getStudent().getRollNumber());
	 							if(parentList.get(0).getStudentParentList().size()==1) parentsDataBean.setParStudID(
	 									parentList.get(0).getStudentParentList().get(0).getStudent().getRollNumber());
	 							else if(parentList.get(0).getStudentParentList().size()==2) parentsDataBean.setParStudID(
	 									parentList.get(0).getStudentParentList().get(0).getStudent().getRollNumber()+" , "+
	 							parentList.get(0).getStudentParentList().get(1).getStudent().getRollNumber());
	 							else if(parentList.get(0).getStudentParentList().size()==3) parentsDataBean.setParStudID(
	 									parentList.get(0).getStudentParentList().get(0).getStudent().getRollNumber()+" , "+
	 							parentList.get(0).getStudentParentList().get(1).getStudent().getRollNumber()+" , "+
	 									parentList.get(0).getStudentParentList().get(2).getStudent().getRollNumber());
	 							else if(parentList.get(0).getStudentParentList().size()==4) parentsDataBean.setParStudID(
	 									parentList.get(0).getStudentParentList().get(0).getStudent().getRollNumber()+" , "+
	 							parentList.get(0).getStudentParentList().get(1).getStudent().getRollNumber()+" , "+
	 									parentList.get(0).getStudentParentList().get(2).getStudent().getRollNumber()+" , "+
	 											parentList.get(0).getStudentParentList().get(3).getStudent().getRollNumber());
	 							else if(parentList.get(0).getStudentParentList().size()==5) parentsDataBean.setParStudID(
	 									parentList.get(0).getStudentParentList().get(0).getStudent().getRollNumber()+" , "+
	 							parentList.get(0).getStudentParentList().get(1).getStudent().getRollNumber()+" , "+
	 									parentList.get(0).getStudentParentList().get(2).getStudent().getRollNumber()+" , "+
	 											parentList.get(0).getStudentParentList().get(3).getStudent().getRollNumber()+" , "+
	 													parentList.get(0).getStudentParentList().get(4).getStudent().getRollNumber());
	 						}
	 						logger.debug("student ID "+parentsDataBean.getParStudID());
	 					} else {
	 						parentsDataBean.setParStudID("");
	 					}
	 				} else {
	 					parentsDataBean.setParParentID("");
	 					parentsDataBean.setParStudID("");
	 					parentsDataBean.setParfilePath("");
	 					parentsDataBean.setParPathDate(null);
	 					parentsDataBean.setName("");
	 					parentsDataBean.setParEmail("");
	 					parentsDataBean.setParentRelation("");
	 					parentsDataBean.setParPhoneNo("");
	 				}
	 			}

	 		}
	 		catch(Exception e)
	 		{
	 			logger.warn(" exception - "+e);
	 		}
	 		return gson.toJson(parentsDataBean);
	 	}
	 	
	 	
	 	@POST
	 	@Path("/deleteParent/{id}")
	 	
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String deleteParent(@PathParam("id") String id,String json) throws JSONException
	 	{
	 		Gson gson=new Gson();
	 		String status="";
	 		ParentsDataBean parentsDataBean=new ParentsDataBean();
	 		String personID=id;
	 		JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			String pid=element.getAsJsonObject().get("parentid").getAsString();
	 		parentsDataBean.setParParentID(pid);
	 		try
	 		{
	 			if (personID != null && parentsDataBean.getParParentID() != null) 
	 			{
	 				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 				status = smsservice.deleteParent(personID, parentsDataBean);
	 				logger.debug(status);
	 				parentsDataBean.setReturnStatus(status);
	 			}
	 		}
	 		catch(Exception e)
	 		{
	 			logger.warn(" exception - "+e);
	 		}
	 		return gson.toJson(parentsDataBean.getReturnStatus());
	 	}
	 	
	 	
	 	@GET
	 	@Path("/staffinfomation/{id}")
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String staffinfomation(@PathParam("id") String id) throws JSONException
	 	{
	 		Gson gson = new Gson();
	 		List<StaffDataBean> staffList = null;
	 		String personID=id;
	 		try
	 		{
	 			staffList = new ArrayList<StaffDataBean>();
	 			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 			if (personID != null) 
	 			{
	 				staffDataBean.setRollcheck("Staff");
	 				staffList = smsservice.staffinfomation(personID, staffDataBean);
	 			} 
	 		}
	 		catch(Exception e)
	 		{
	 			logger.warn(" exception - "+e);
	 		}
	 		return gson.toJson(staffList);
	 	}
	 	
	 	@POST
	 	@Path("/tagstaffview/{id}")
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String tagstaffview(@PathParam("id") String id,String json) throws JSONException
	 	{
	 		StaffDataBean staffDataBean=new StaffDataBean();
	 		List<StaffDataBean> stafftaglist=null;
	 		Gson gson = new Gson();
	 		String personID=id;
	 		JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			String staffid=element.getAsJsonObject().get("staffid").getAsString();
	 		staffDataBean.setStaStaffID(staffid);
	 		try
	 		{
	 			stafftaglist = new ArrayList<StaffDataBean>();
	 			if (staffDataBean.getStaStaffID() != null) 
	 			{
	 				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 				if (personID != null) {
	 					stafftaglist = smsservice.tagstaffview(personID, staffDataBean);
	 					logger.debug("------" + stafftaglist.size());
	 					try {
	 						if (stafftaglist.size() > 0) {
	 							if (stafftaglist.get(0).getStaffList().size() > 0) {
	 								staffDataBean.setStaStaffID(stafftaglist.get(0).getStaffList().get(0).getRollNumber());
	 								staffDataBean.setName(stafftaglist.get(0).getStaffList().get(0).getFirstName() + " "
	 										+ stafftaglist.get(0).getStaffList().get(0).getLastName());
	 								staffDataBean.setStaGender(stafftaglist.get(0).getStaffList().get(0).getGender());
	 								staffDataBean.setStaPhoneNo(stafftaglist.get(0).getStaffList().get(0).getPhoneNumber());
	 								staffDataBean.setStaEmail(stafftaglist.get(0).getStaffList().get(0).getEmailId());
	 								staffDataBean.setStaFirstName(stafftaglist.get(0).getStaffList().get(0).getFirstName());
	 								staffDataBean.setStaLastName(stafftaglist.get(0).getStaffList().get(0).getLastName());
	 							} else {
	 								staffDataBean.setStaStaffID("");
	 								staffDataBean.setName("");
	 								staffDataBean.setStaGender("");
	 								staffDataBean.setStaPhoneNo("");
	 								staffDataBean.setStaEmail("");
	 								staffDataBean.setStaFirstName("");
	 								staffDataBean.setStaLastName("");
	 							}
	 							staffDataBean.setStafilePath("");
	 							if (stafftaglist.get(0).getImagepath().size() == 0 || stafftaglist.get(0).getImagepath()==null) {
	 								staffDataBean.setStafilePath("");
	 								staffDataBean.setStaPathDate(null);
	 							} else {
	 								staffDataBean.setStafilePath(stafftaglist.get(0).getImagepath().get(0).getPath());
	 								staffDataBean.setStaPathDate(stafftaglist.get(0).getImagepath().get(0).getDate());
	 								String file=paths.getString("sms.staff.photo");
		 							String pathname=(file+format.format(staffDataBean.getStaPathDate())+paths.getString("path_context").toString()+staffDataBean.getStafilePath());
		 							String encodestring=convertimage(pathname);
									staffDataBean.setStafilePath(encodestring);
	 							}
	 						}
	 					} catch (NullPointerException n) {
	 					}
	 				}

	 			}
	 		}
	 		catch(Exception e)
	 		{
	 			logger.warn(" exception - "+e);
	 		}
	 		return gson.toJson(staffDataBean);
	 	}
	 	

	 	@POST
	 	@Path("/deleteStaff/{id}")
	 	
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String deleteStaff(@PathParam("id") String id,String json) throws JSONException
	 	{
	 		Gson gson=new Gson();
	 		String status="";
	 		StaffDataBean staffDataBean=new StaffDataBean();
	 		String personID=id;
	 		JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			String staffid=element.getAsJsonObject().get("staffid").getAsString();
	 		staffDataBean.setStaStaffID(staffid);
	 		try
	 		{
	 			if (personID != null && staffDataBean.getStaStaffID() != null) 
	 			{
	 				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 				status = smsservice.deleteStaff(personID, staffDataBean);
	 				logger.debug(status);
	 				staffDataBean.setReturnStatus(status);
	 			}
	 		}
	 		catch(Exception e)
	 		{
	 			logger.warn(" exception - "+e);
	 		}
	 		return gson.toJson(staffDataBean.getReturnStatus());
	 	}
	 	
	 	
	 	@GET
	 	@Path("/getAllLibrarianInfo/{id}")
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String getAllLibrarianInfo(@PathParam("id") String id) throws JSONException
	 	{
	 		Gson gson = new Gson();
	 		String rollnumber = "";
	 		String rollnumber1="";
	 		String status = "";
	 		List<LibrarianDataBean> librarianList = null;
	 		LibrarianDataBean librarianDataBean=new LibrarianDataBean();
	 		String personID=id;
	 		try
	 		{
	 			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 			status = smsservice.getRoll(personID);
	 			logger.debug("status -- "+status);
	 			status = smsservice.getRoll(personID);
	 			if (personID != null) {
	 				if (status.equalsIgnoreCase("Admin")) {
	 					librarianList = new ArrayList<LibrarianDataBean>();
	 					librarianList = smsservice.getAllLibrarianInfo(personID);
	 				} else if (status.equalsIgnoreCase("Student")) {
	 					rollnumber = smsservice.getRollNumber(personID);
	 					logger.debug("------student roll number-----" + rollnumber);
	 					studClass = smsservice.getStudClsSection(rollnumber);
	 					librarianDataBean.setTeaclsSection(studClass);
	 					librarianList = new ArrayList<LibrarianDataBean>();
	 					librarianList = smsservice.getAllLibrarianInfo(personID);
	 				} else if (status.equalsIgnoreCase("Parent")) {
	 					rollnumber = smsservice.getRollNumber(personID);
	 					logger.debug("------student roll number-----" + rollnumber);
	 					studClass = smsservice.getStudClsSection(rollnumber);
	 					librarianDataBean.setTeaclsSection(studClass);
	 					librarianList = new ArrayList<LibrarianDataBean>();
	 					librarianList = smsservice.getAllLibrarianInfo(personID);
	 				} else if (status.equalsIgnoreCase("Teacher")) {

	 					logger.debug("-----------Teacher---------");
	 					rollnumber1 = smsservice.getRollNumber(personID);
	 					logger.debug("------test roll-------" + rollnumber1);
	 					librarianDataBean.setTeaID(rollnumber1);
	 					librarianDataBean.setTeaID(rollnumber1);
	 					librarianList = smsservice.getAllLibrarianInfo(personID);
	 				}
	 			}
	 		}
	 		catch(Exception e)
	 		{
	 			logger.warn(" exception - "+e);
	 		}
	 		return gson.toJson(librarianList);
	 	}


	 	



	 	@POST
	 	@Path("/getLibrarianInfo/{id}")
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String getLibrarianInfo(@PathParam("id") String id,String json) throws JSONException
	 	{
	 		Gson gson = new Gson();
	 		List<LibrarianDataBean> librarianViewList = new ArrayList<LibrarianDataBean>();
	 		LibrarianDataBean librarianDataBean=new LibrarianDataBean();
	 		String personID=id;
	 		JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			String libID=element.getAsJsonObject().get("libID").getAsString();
	 		librarianDataBean.setLibID(libID);
	 		try
	 		{
	 			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 			librarianViewList = null;
	 			logger.debug("lib view ");
	 			if (personID != null && libID != null) 
	 			{
	 				smsservice.getLibrarianInfo(personID, librarianDataBean.getLibID(), librarianDataBean);
	 				logger.debug("name -- " + librarianDataBean.getLibFirstName());
	 				if(librarianDataBean.getPath()=="" || librarianDataBean.getPath()==null || librarianDataBean.getPathDate()==null){
	 					librarianDataBean.setLibfilePath("");
	 				}else{
	 					String file=paths.getString("sms.librarian.photo");
						String pathname=(file+format.format(librarianDataBean.getPathDate())+paths.getString("path_context").toString()+librarianDataBean.getPath());
						String encodestring=convertimage(pathname);
						librarianDataBean.setLibfilePath(encodestring);
	 				}
	 			}
	 		
	 		}
	 		catch(Exception e)
	 		{
	 			logger.warn(" exception - "+e);
	 			  logger.error("Exception -->"+e.getMessage());
	 		}
	 		return gson.toJson(librarianDataBean);
	 	}
	 	

	 	@POST
	 	@Path("/deleteLibrarian/{id}")
	 
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String deleteLibrarian(@PathParam("id") String id,String json) throws JSONException
	 	{
	 		Gson gson=new Gson();
	 		String status="";
	 		LibrarianDataBean librarianDataBean=new LibrarianDataBean();
	 		String personID=id;
	 		JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			String libID=element.getAsJsonObject().get("libID").getAsString();
	 		librarianDataBean.setLibID(libID);
	 		try
	 		{
	 			if (personID != null && librarianDataBean.getLibID() != null) 
	 			{
	 				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 				status = smsservice.deleteLibrarian(personID, librarianDataBean);
	 				logger.debug(status);
	 				librarianDataBean.setReturnStatus(status);
	 			}
	 		}
	 		catch(Exception e)
	 		{
	 			logger.warn(" exception - "+e);
	 		}
	 		return gson.toJson(librarianDataBean.getReturnStatus());
	 	}
	 	
	 	
	 	@POST
	 	@Path("/getStudentEdit")
	 	@Consumes(MediaType.APPLICATION_JSON)
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String getStudentEdit(String json) throws JSONException
	 	{
	 		Gson gson=new Gson();
	 		logger.debug("-----inside student Update-------");
	 		//List<StudentDataBean> ImageListPath = null;
	 		String status="";
	 		StudentDataBean studentDataBean=new StudentDataBean();
	 		JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			String name=element.getAsJsonObject().get("sfirstname").getAsString();
			String classs=element.getAsJsonObject().get("sclass").getAsString();
			String lastname=element.getAsJsonObject().get("slastname").getAsString();
			String fathername=element.getAsJsonObject().get("sfathername").getAsString();
			String mothername=element.getAsJsonObject().get("smothername").getAsString();
			String dob=element.getAsJsonObject().get("sdob").getAsString();
			String gender=element.getAsJsonObject().get("sgender").getAsString();
			String address=element.getAsJsonObject().get("saddress").getAsString();
			String address1=element.getAsJsonObject().get("saddress1").getAsString();
			String id=element.getAsJsonObject().get("sid").getAsString();
			String foccupation=element.getAsJsonObject().get("sfoccupation").getAsString();
			String moccupation=element.getAsJsonObject().get("smoccupation").getAsString();
			String personID=element.getAsJsonObject().get("spersonID").getAsString();
			String fatherincome=element.getAsJsonObject().get("fathincome").getAsString();	
			String filename=element.getAsJsonObject().get("path").getAsString();
	 		try{		
	 			studentDataBean.setStuFirstName(name);
	 			studentDataBean.setStuLastName(lastname);
	 			studentDataBean.setStuCls(classs);
	 			studentDataBean.setStuFatherName(fathername);
	 			studentDataBean.setStuMotherName(mothername);
	 			studentDataBean.setStuGender(gender);
	 			if(dob.equals("") || dob==null){
	 				studentDataBean.setStuDob(null);
	 			}else{
	 				studentDataBean.setStuDob(format.parse(dob));
	 			}
	 			studentDataBean.setStuFatherIncome(fatherincome);
	 			studentDataBean.setStuFatherOccu(foccupation);
	 			studentDataBean.setStuMotherOccu(moccupation);
	 			studentDataBean.setStuPresentAddress(address);
	 			studentDataBean.setStuPermanentAddress(address1);
	 			studentDataBean.setStuRollNo(id);
	 			if(filename==null || filename.equalsIgnoreCase(""))
	 				studentDataBean.setStufilePath(null);
	 			else
	 				studentDataBean.setStufilePath(filename);
	 			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 			status = smsservice.getStudentEdit(studentDataBean,personID);
	 			logger.debug("Status1" + status);
	 			studentDataBean.setReturnStatus(status);
	 			if (status.equalsIgnoreCase("Success")) {
	 				studentDataBean.setStuFirstName("");
	 				studentDataBean.setStuLastName("");
	 				studentDataBean.setStuFatherName("");
	 				studentDataBean.setStuMotherName("");
	 				studentDataBean.setStuDob(null);
	 				studentDataBean.setStuGender("");
	 				studentDataBean.setStuEmail("");
	 				studentDataBean.setStuStudentId("");
	 				studentDataBean.setStuPhoneNo("");
	 				studentDataBean.setStuPermanentAddress("");
	 				studentDataBean.setStuPresentAddress("");
	 				studentDataBean.setStuStudentPhoto("");
	 				studentDataBean.setStuPermanentState("");
	 				studentDataBean.setStuPresentState("");
	 				studentDataBean.setStuPermanentZip("");
	 				studentDataBean.setStuPresentZip(""); 
	 				studentDataBean.setStuFile(null);
	 				studentDataBean.setStuSecurePasword("");
	 				studentDataBean.setPath("");
	 				studentDataBean.setPathDate(null);
	 				studentDataBean.setStuCls("");
	 			}
	 		} catch (Exception e) {
	 			logger.warn("Exception -->"+e.getMessage());
	 		}
	 		return gson.toJson(studentDataBean.getReturnStatus());
	 	}
	 	
	 	@POST
	 	@Path("/updateParent")	 	
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String updateParent(String json) throws JSONException
	 	{
	 		Gson gson=null;// Gson();
	 		logger.info("--- Inside updateParent method ---");
	 		List<ParentsDataBean> parentsBean=null;// ArrayList<ParentsDataBean>();
	 		List<ParentsDataBean> ImageListPath = null;
	 		List<ParentsDataBean> studentlist=null;// ArrayList<ParentsDataBean>();
	 		ParentsDataBean parentsDataBean=null;// ParentsDataBean();
	 		JsonParser jsonParser = null;//new JsonParser();
			JsonElement element = null;//jsonParser.parse(json);
			
			
			
	 		try {
	 			gson=new Gson();
		 		parentsBean=new ArrayList<ParentsDataBean>();
		 		studentlist=new ArrayList<ParentsDataBean>();
		 		parentsDataBean=new ParentsDataBean();
		 		jsonParser = new JsonParser();
				element = jsonParser.parse(json);
				String name=element.getAsJsonObject().get("pfirstname").getAsString();
				String classs=element.getAsJsonObject().get("pclass").getAsString();
				String lastname=element.getAsJsonObject().get("plastname").getAsString();
				String phonenumber=element.getAsJsonObject().get("pphonenumber").getAsString();
				String countryCode=element.getAsJsonObject().get("countryCode").getAsString();
				String mailid=element.getAsJsonObject().get("pmailid").getAsString();
				String id=element.getAsJsonObject().get("pid").getAsString();
				String personID=element.getAsJsonObject().get("ppersonID").getAsString();
				String relation=element.getAsJsonObject().get("prelation").getAsString();
				String studentID=element.getAsJsonObject().get("pstudentID").getAsString();
				String filename=element.getAsJsonObject().get("path").getAsString();				
				logger.info("File name -->"+filename);
				logger.info("Student ID -->"+studentID);
	 			ParentsDataBean parentsbean = new ParentsDataBean();
	 			parentsbean.setParStuClass(classs);
	 			parentsbean.setParStudID(studentID);
	 			logger.debug("parentsbean -- "+parentsbean.getParStudID());
	 			studentlist.add(parentsbean);
	 			parentsDataBean.setParentdetails(studentlist);
	 			parentsDataBean.setParFirstName(name);
	 			parentsDataBean.setParLastName(lastname);
	 			parentsDataBean.setParPhoneNo(phonenumber);
	 			parentsDataBean.setCountryCode(countryCode);
	 			parentsDataBean.setParEmail(mailid);
	 			parentsDataBean.setParParentID(id);
	 			parentsDataBean.setParentRelation(relation);
	 			if(filename==null || filename.equalsIgnoreCase(""))
	 				parentsDataBean.setParfilePath(null);
	 			else
	 				parentsDataBean.setParfilePath(filename);
	 			//List<ParentsDataBean> parentList = new ArrayList<ParentsDataBean>();
	 			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 			if (personID != null) {
	 				/*if (parentsDataBean.getParFile() != null) {
	 					String s = parentsDataBean.getParFile().getContentType();
	 					logger.debug("file Type " + s);
	 					String type = s.substring(s.lastIndexOf("/") + 1);
	 					logger.debug(type);
	 					logger.debug(parentsDataBean.getParParentID() + "." + type);
	 					String path = ft.format(now) + "/" + parentsDataBean.getParParentID() + "." + type;
	 					parentsDataBean.setParfilePath(path);
	 					logger.debug(parentsDataBean.getParfilePath());
	 				} else {
	 					parentsDataBean.setParFile(null);
	 				}*/
	 				logger.debug("parentsBean -- "+parentsBean.size());
	 				if(parentsBean.size()==0){
	 					ParentsDataBean parents=new ParentsDataBean();
	 					logger.debug("parentsDataBean.getParStuClass()"+parentsDataBean.getParentdetails().get(0).getParStudID());
	 					logger.debug("parentsDataBean.getParStudID()"+parentsDataBean.getParentdetails().get(0).getParStuClass());
	 					parents.setParStuClass(parentsDataBean.getParentdetails().get(0).getParStuClass());
	 					parents.setParStudID(parentsDataBean.getParentdetails().get(0).getParStudID());
	 					parentsBean.add(parents);
	 					parentsDataBean.setParentdetails(parentsBean);
	 				}else{
	 					parentsDataBean.setParentdetails(parentsBean);
	 				}
	 				String status = smsservice.updateParent(personID, parentsDataBean);
	 				logger.debug("status --"+status);
	 				parentsDataBean.setReturnStatus(status);
	 				if (status.equalsIgnoreCase("Success")) {
	 					ImageListPath = new ArrayList<ParentsDataBean>();
	 					ImageListPath = smsservice.getImagePath2(personID, parentsDataBean.getParParentID());
	 					logger.debug("ImageListPath == "+ImageListPath.size());
	 						/*String pdfStatus = GeneratePdfMail.parentupdategeneratePdf(ImageListPath, parentsDataBean);
	 						logger.debug("pdfStatus--" + pdfStatus );
	 						if (pdfStatus.equalsIgnoreCase("Success")) {*/
	 							MailSendJDBC.parentUpdate(parentsDataBean,parentsDataBean.getSchoolName(),parentsDataBean.getSchoolID());
	 							ImageListPath = null;
	 							parentsDataBean.setReturnStatus(status);
	 						/*}*/
	 					}
	 				logger.debug("status--"+parentsDataBean.getReturnStatus());
	 			}
	 		} catch (Exception e) {
	 			//logger.warn(" exception - "+e);
	 			  logger.error("Exception -->"+e.getMessage());
	 		}
	 		return gson.toJson(parentsDataBean.getReturnStatus());

	 	}

	 	//STAFF UPDATE	 		 	
	 	@POST
	 	@Path("/tagstaffview")	 	
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String tagstaffview(String json) throws JSONException
	 	{
	 		smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 		JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			String personID=element.getAsJsonObject().get("personID").getAsString();
	 		smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 		StaffDataBean staffDataBean=new StaffDataBean();
	 		List<StaffDataBean> stafftaglist = null;
	 		try {
	 			stafftaglist = new ArrayList<StaffDataBean>();
	 			if (staffDataBean.getStaStaffID() != null) {
	 				if (personID != null) {
	 					stafftaglist = smsservice.tagstaffview(personID, staffDataBean);
	 					logger.debug("------" + stafftaglist.size());
	 					try {
	 						if (stafftaglist.size() > 0) {
	 							if (stafftaglist.get(0).getStaffList().size() > 0) {
	 								staffDataBean.setStaStaffID(stafftaglist.get(0).getStaffList().get(0).getRollNumber());
	 								staffDataBean.setName(stafftaglist.get(0).getStaffList().get(0).getFirstName() + " "
	 										+ stafftaglist.get(0).getStaffList().get(0).getLastName());
	 								staffDataBean.setStaGender(stafftaglist.get(0).getStaffList().get(0).getGender());
	 								staffDataBean.setStaPhoneNo(stafftaglist.get(0).getStaffList().get(0).getPhoneNumber());
	 								staffDataBean.setStaEmail(stafftaglist.get(0).getStaffList().get(0).getEmailId());
	 								staffDataBean.setStaFirstName(stafftaglist.get(0).getStaffList().get(0).getFirstName());
	 								staffDataBean.setStaLastName(stafftaglist.get(0).getStaffList().get(0).getLastName());
	 							} else {
	 								staffDataBean.setStaStaffID("");
	 								staffDataBean.setName("");
	 								staffDataBean.setStaGender("");
	 								staffDataBean.setStaPhoneNo("");
	 								staffDataBean.setStaEmail("");
	 								staffDataBean.setStaFirstName("");
	 								staffDataBean.setStaLastName("");
	 							}

	 							if (stafftaglist.get(0).getImagepath().size() > 0) {
	 								staffDataBean.setStafilePath(stafftaglist.get(0).getImagepath().get(0).getPath());
	 								staffDataBean.setStaPathDate(stafftaglist.get(0).getImagepath().get(0).getDate());
	 							} else {
	 								staffDataBean.setStafilePath("");
	 								staffDataBean.setStaPathDate(null);
	 							}
	 						}
	 					} catch (NullPointerException n) {
	 						logger.error("Null Pointer Exception -->"+n.getMessage());
	 						//n.printStackTrace();
	 					}
	 				}

	 			}
	 		} catch (Exception e) {
	 			logger.error("Exception -->"+e.getMessage());
	 		}
	 		return "editPagestaff";
	 	}

	 	@POST
	 	@Path("/updateStaff")	 	
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String updateStaff(String json) throws JSONException
	 	{
	 		Gson gson=new Gson();
	 		StaffDataBean staffDataBean = new StaffDataBean();
	 		//List<StaffDataBean> staffList = null;
	 		smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 		JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			String id=element.getAsJsonObject().get("sfid").getAsString();
			String name=element.getAsJsonObject().get("sffirstname").getAsString();
			String lastname=element.getAsJsonObject().get("sflastname").getAsString(); 
			String gender=element.getAsJsonObject().get("sfgender").getAsString();
			String mailid=element.getAsJsonObject().get("sfmailid").getAsString();
			String phoneno=element.getAsJsonObject().get("sfphoneno").getAsString();
			String personID=element.getAsJsonObject().get("personID").getAsString();
			String filename=element.getAsJsonObject().get("path").getAsString();
	 		try {
	 			staffDataBean.setStaEmail(mailid);
	 			staffDataBean.setStaFirstName(name);
	 			staffDataBean.setStaGender(gender);
	 			staffDataBean.setStaLastName(lastname);
	 			staffDataBean.setStaPhoneNo(phoneno);
	 			staffDataBean.setStaStaffID(id);
	 			staffDataBean.setRollcheck("Staff");
	 			if(filename==null || filename.equalsIgnoreCase(""))
	 				staffDataBean.setStafilePath(null);
	 			else
	 				staffDataBean.setStafilePath(filename);
	 			if (staffDataBean.getStaStaffID() != null) 
	 			{
	 				if (personID != null) {
	 					/*if (staffDataBean.getStaFile() != null) {
	 						String s = staffDataBean.getStaFile().getContentType();
	 						String type = s.substring(s.lastIndexOf("/") + 1);
	 						logger.debug(staffDataBean.getStaStaffID() + "." + type);
	 						String path = ft.format(now) + "/" + staffDataBean.getStaStaffID() + "." + type;
	 						staffDataBean.setStafilePath(path);
	 					} else {
	 						staffDataBean.setStafilePath(null);
	 					}*/
	 					String status = smsservice.updateStaff(personID, staffDataBean);
	 					staffDataBean.setReturnStatus(status);
	 					if (status.equalsIgnoreCase("Success")) {
	 						//List<StaffDataBean> ImageListPath = new ArrayList<StaffDataBean>();
	 						//ImageListPath = smsservice.getImagePath3(personID, staffDataBean.getStaStaffID());
	 						//String pdfStatus = GeneratePdfMail.staffupdategeneratePdf(staffDataBean,ImageListPath);
	 						MailSendJDBC.staffUpdate(staffDataBean,staffDataBean.getSchoolName(),staffDataBean.getSchoolID());
	 						//if (pdfStatus.equalsIgnoreCase("Success")) {

	 							//staffList = null;
	 							staffDataBean.setStaStaffID("");
	 							staffDataBean.setStaFirstName("");
	 							staffDataBean.setStaLastName("");
	 							staffDataBean.setStaGender("");
	 							staffDataBean.setStaEmail("");
	 							staffDataBean.setStaPhoneNo("");
	 							staffDataBean.setStaStaffPhoto("");
	 							staffDataBean.setStaffImageList(null);
	 							staffDataBean.setStaffList(null);
	 							staffDataBean.setStaSecurePasword("");
	 							staffDataBean.setStaUsername("");
	 					//} 
	 				}
	 			}
	 			}
	 		} catch (Exception e) {
	 			logger.warn(" exception - "+e);
	 		}
	 		return gson.toJson(staffDataBean.getReturnStatus());
	 	}
	 	
	 	
	 	//LIBRARIAN UPDATE
	 	@POST
	 	@Path("/librarianUpdate")	 	
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String librarianUpdate(String json) throws JSONException
	 	{
	 		Gson gson=new Gson();
	 		//String emailStatus="";
	 		LibrarianDataBean librarianDataBean = new LibrarianDataBean();
	 		smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 		JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(json);
			String id=element.getAsJsonObject().get("lid").getAsString();
			String name=element.getAsJsonObject().get("lfirstname").getAsString();
			String lastname=element.getAsJsonObject().get("llastname").getAsString();
			String gender=element.getAsJsonObject().get("lgender").getAsString();
			String mailid=element.getAsJsonObject().get("lmailid").getAsString();
			String phoneno=element.getAsJsonObject().get("lphoneno").getAsString(); 
			String personID=element.getAsJsonObject().get("personID").getAsString(); 
			String filename=element.getAsJsonObject().get("path").getAsString();
	 		try {
	 			librarianDataBean.setLibEmail(mailid);
	 			librarianDataBean.setLibFirstName(name);
	 			librarianDataBean.setLibGender(gender);
	 			librarianDataBean.setLibLastName(lastname);
	 			librarianDataBean.setLibPhoneNo(phoneno);
	 			librarianDataBean.setLibID(id);
	 			if(filename==null || filename.equalsIgnoreCase(""))
	 			librarianDataBean.setLibfilePath(null);
	 			else
	 				librarianDataBean.setLibfilePath(filename);
	 			if (personID != null) {
	 				/*if (librarianDataBean.getLibLibrarianPhoto() == null) {
	 					logger.debug("photo -- " + librarianDataBean.getLibLibrarianPhoto());
	 				} else {
	 					logger.debug("photo -1- " + librarianDataBean.getLibLibrarianPhoto());
	 					String s = librarianDataBean.getLibLibrarianPhoto().getContentType();
	 					String type = s.substring(s.lastIndexOf("/") + 1);
	 					String path = ft.format(now) + "/" + librarianDataBean.getLibID() + "." + type;
	 					librarianDataBean.setLibfilePath(path);
	 				}*/
	 				String status = smsservice.libUpdate(librarianDataBean, personID);
	 				librarianDataBean.setReturnStatus(status);
	 				if (status.equals("success")) {
	 				//	List<LibrarianDataBean> ImageListPath = new ArrayList<LibrarianDataBean>();
	 					//ImageListPath = smsservice.getImagePath5(personID, librarianDataBean.getLibID());
	 					/*String pdfStatus = GeneratePdfMail.libupdategeneratePdf(librarianDataBean,ImageListPath);
	 					if (pdfStatus.equalsIgnoreCase("Success")) {*/
	 						MailSendJDBC.librarianUpdate(librarianDataBean,librarianDataBean.getSchoolName(),librarianDataBean.getSchoolID());
	 					/*}*/
	 					
	 				}
	 			}
	 			logger.debug("status -- "+librarianDataBean.getReturnStatus());
	 		} catch (Exception e) {
	 			//logger.warn(" exception - "+e);
	 			  logger.error("Exception -->"+e.getMessage());
	 		}
	 		return gson.toJson(librarianDataBean.getReturnStatus());
	 	}
	 	
	 	
	 	
	 	@GET
	 	 @Path("/getsubject/{id}  ")
	 	 @Produces(MediaType.APPLICATION_JSON)
	 	 public String getSubjectView(@PathParam("id") String id) throws JSONException
	 	 {
	 	  System.out.println("inside getsubject method calling");
	 	  Gson gson = new Gson();
	 	  String personID=id;
	 	  List<String> subList=null;
	 	  TimeTableDataBean timeTableDataBean=new TimeTableDataBean();
	 	  timeTableDataBean.setExamClass("Class-01");
	 	  try{
	 	   if(personID != null){
	 	    subList=new ArrayList<String>();
	 	    smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 	    smsservice.subjectValues(timeTableDataBean, personID);
	 	    subList.addAll(timeTableDataBean.getSublist());
	 	    logger.debug("subject list size"+subList.size());
	 	   }
	 	  }
	 	  catch(Exception e){
	 	   logger.warn(" exception - "+e);
	 	  }
	 	  return gson.toJson(subList);
	 	 }
	 	//book sale order
	 	  @GET
	 	  @Path("/getBookInfo/{id}")
	 	  @Produces(MediaType.APPLICATION_JSON)
	 	  public String getBookInfo(@PathParam("id") String id) throws JSONException
	 	  {
	 	   List<BookSaleDataBean> booklist = null;
	 	   BookSaleDataBean bookSaleDataBean=new BookSaleDataBean();
	 	   Gson gson=new Gson();
	 	   String personID = id;
	 	   try
	 	   {
	 		   booklist=new ArrayList<BookSaleDataBean>();
	 	    smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 	    booklist = smsservice.getBookInfo(personID, bookSaleDataBean);
	 	    logger.debug("booklist---"+booklist.size());
	 	   }
	 	   catch(Exception e)
	 	   {
	 	    logger.warn(" exception - "+e);
	 	   }
	 	   return gson.toJson(booklist);
	 	  }

	 	  @GET
	 	  @Path("/ClassListbooksale/{id}")
	 	  @Produces(MediaType.APPLICATION_JSON)
	 	  public String ClassListbooksale(@PathParam("id") String id) throws JSONException
	 	  {
	 		  logger.debug("inside");
	 	   List<String> classList=null;
	 	   BookSaleDataBean bookSaleDataBean=new BookSaleDataBean();
	 	   Gson gson=new Gson();
	 	   String personID = id;
	 	   try
	 	   {
	 	    smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 	    classList =  smsservice.ClassListbooksale(personID,bookSaleDataBean);
	 	    logger.debug("booklist---"+classList.size());
	 	   }
	 	   catch(Exception e)
	 	   {
	 	    logger.warn(" exception - "+e);
	 	   }
	 	   return gson.toJson(classList);
	 	  }
	 	 
	 	  
 		@GET
 	     @Path("/getClassNameList/{id}")
 	     @Produces(MediaType.APPLICATION_JSON)
 	     public String getClassNameList(@PathParam("id") String id) throws JSONException
 	     {
 	      List<String> classList=null;
 	      Gson gson=new Gson();
 	      String personID = id;
 	      try
 	      {
 	       if(personID != null)
 	       {
 	        classList = new ArrayList<String>();
 	        smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
 	        classList = smsservice.getClassNameList(personID);
 	        logger.debug("classList---"+classList.size());
 	       }
 	      }
 	      catch(Exception e)
 	      {
 	       logger.warn(" exception - "+e);
 	      }
 	      Collections.sort(classList);
 	      return gson.toJson(classList);
 	     }
	 	     
	 	     //get exam title
	 	     @GET
	 	     @Path("/getExamTitleName/{id}/{cname}/{section}")
	 	     @Produces(MediaType.APPLICATION_JSON)
	 	     public String getExamTitleName(@PathParam("id") String id,@PathParam("cname") String cname,@PathParam("section") String section) throws JSONException
	 	     {
	 	      
	 	      List<String> examtitle=null;
	 	      Gson gson=new Gson();
	 	      String personID = id;
	 	      String classname = cname+"/"+section;
	 	      try
	 	      {
	 	       if(personID != null)
	 	       {
	 	        examtitle = new ArrayList<String>();
	 	        smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 	        examtitle = smsservice.getExamTitleName(personID,classname);
	 	       }
	 	      }
	 	      catch(Exception e)
	 	      {
	 	       logger.warn(" exception - "+e);
	 	      }
	 	      return gson.toJson(examtitle);
	 	     }
	 	     
	 	     //get subject list
	 	     @GET
	 	     @Path("/getSubjectNameList/{id}/{cname}/{section}")
	 	     @Produces(MediaType.APPLICATION_JSON)
	 	     public String getSubjectNameList(@PathParam("id") String id,@PathParam("cname") String cname,@PathParam("section") String section) throws JSONException
	 	     {
	 	      List<String> subjectList=new ArrayList<String>();
	 	      Gson gson=new Gson();
	 	      String personID = id;
	 	      String classname = cname+"/"+section;
	 	      try
	 	      {
	 	       if(personID != null)
	 	       {
	 	        reportCardDataBean.setStudMarkClass(classname);
	 	        smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 	        subjectList = smsservice.getSubjectNameList(reportCardDataBean, personID);
	 	        logger.debug("subjectList -- "+subjectList.size());
	 	       }
	 	      }
	 	      catch(Exception e)
	 	      {
	 	       logger.warn("Exception -->"+e.getMessage());
	 	      }
	 	      return gson.toJson(subjectList);
	 	     }
	 	     
	 	     private List<ReportCardDataBean> studentClassList = null; 
	 			
	 		  public List<ReportCardDataBean> getStudentClassList() {
	 				return studentClassList;
	 			}

	 			public void setStudentClassList(List<ReportCardDataBean> studentClassList) {
	 				this.studentClassList = studentClassList;
	 			}
	 	     //submit method
	 	     
	 	     @GET
	 	     @Path("/studentClassList/{id}/{cname}/{section}/{exam}/{title}/{code}")
	 	     @Produces(MediaType.APPLICATION_JSON)
	 	     public String studentClassList(@PathParam("id") String id,@PathParam("cname") String cname,@PathParam("section") String section,@PathParam("exam") String exam,
	 	    		 @PathParam("title") String title,@PathParam("code") String code) throws JSONException
	 	     {
	 	      Gson gson=new Gson();
	 	      String personID = id;
	 	      String classname = cname+"/"+section;
	 	      reportCardDataBean.setStudMarkClass(classname);
	 	      reportCardDataBean.setExamMarkTitle(exam);
	 	      String subject=title+"/"+code;
	 	      reportCardDataBean.setMarkSubTitle(subject);
	 	      logger.debug("exam check---"+exam);
	 	      try
	 	      {
	 	       if(personID != null)
	 	       {
	 	        studentClassList = new ArrayList<ReportCardDataBean>();
	 	        smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 	        studentClassList = smsservice.getStudent(personID, reportCardDataBean);
	 	        logger.debug("studentClassList -- "+studentClassList.size());
	 	       }
	 	      }
	 	      catch(Exception e)
	 	      {
	 	       logger.warn(" exception - "+e);
	 	      }
	 	      return gson.toJson(studentClassList);
	 	     }
	 	     
	 	     @GET
	 	     @Path("/getRepoartCard/{id}/{cname}/{section}/{exam}/{title}/{code}")
	 	     @Produces(MediaType.APPLICATION_JSON)
	 	     public String getRepoartCard(@PathParam("id") String id,@PathParam("cname") String cname,@PathParam("section") String section,@PathParam("exam") String exam,
	 	    		 @PathParam("title") String title,@PathParam("code") String code) throws JSONException
	 	     {
	 	      List<ReportCardDataBean> studentMarkList = new ArrayList<ReportCardDataBean>();
	 	      Gson gson=new Gson();
	 	      String personID =id;
	 	      String rclass = cname+"/"+section;
	 	      try
	 	      {
	 	       reportCardDataBean.setStudMarkClass(rclass);
	 	       reportCardDataBean.setExamMarkTitle(exam);
	 	       reportCardDataBean.setViewMarkStuName(code);
	 	       if(personID != null)
	 	       {
	 	        smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 	        studentMarkList = smsservice.getRepoartCard(personID, reportCardDataBean);
	 	        logger.debug("studentMarkList -- "+studentMarkList.size());
	 	       }
	 	      }
	 	      catch(Exception e)
	 	      {
	 	       logger.warn(" exception - "+e);
	 	      }
	 	      return gson.toJson(studentMarkList);
	 	     }
	 	     
	 	     @GET
	 	     @Path("/getStudentIDList/{id}/{cname}/{section}")
	 	     @Produces(MediaType.APPLICATION_JSON)
	 	     public String getStudentIDList(@PathParam("id") String id,@PathParam("cname") String cname,@PathParam("section") String section) throws JSONException
	 	     {
	 	      Gson gson=new Gson();
	 	      String personID = id;
	 	      String classname=cname+"/"+section;
	 	      reportCardDataBean.setStudMarkClass(classname);
	 	      List<String> studentIDList=new ArrayList<String>();
	 	      try {
	 	       if (personID != null) {
	 	        smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 	        studentIDList = smsservice.getStudentIDList(reportCardDataBean, personID);
	 	        logger.debug("studentIDList -- "+studentIDList.size());
	 	       }

	 	      } catch (Exception e) {
	 	       logger.warn("Exception -->"+e.getMessage());
	 	      }
	 	      return gson.toJson(studentIDList);
	 	     }
	 	     
	 	    
	 	   @GET
	       @Path("/getRepoartCard/{id}/{cname}/{section}/{exam}/{code}")
	       @Produces(MediaType.APPLICATION_JSON)
	       public String getRepoartCard(@PathParam("id") String id,@PathParam("cname") String cname,@PathParam("section") String section,@PathParam("exam") String exam,
	         @PathParam("code") String code) throws JSONException
	       {
	        List<ReportCardDataBean> studentMarkList = new ArrayList<ReportCardDataBean>();
	        Gson gson=new Gson();
	        String personID =id;
	        String rclass = cname+"/"+section;
	        try
	        {
	         reportCardDataBean.setStudMarkClass(rclass);
	         reportCardDataBean.setExamMarkTitle(exam);
	         reportCardDataBean.setViewMarkStuName(code);
	         if(personID != null)
	         {
	          smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	          studentMarkList = smsservice.getRepoartCard(personID, reportCardDataBean);
	          logger.debug("studentMarkList -- "+studentMarkList.size());
	         }
	        }
	        catch(Exception e)
	        {
	         logger.warn(" exception - "+e);
	        }
	        return gson.toJson(studentMarkList);
	       }
	 	   
	 	  @POST
	 	  @Path("/getPerformEdit")
	 	  @Produces(MediaType.APPLICATION_JSON)
	 	  public String getPerformEdit(String json) throws JSONException
	 	  {
	 	   logger.debug("inside update ");
	 	    Gson gson=new Gson();
	 	    String status="";
	 	    String emailStatus="";
	 	   JsonParser jsonParser = new JsonParser();
		   JsonElement element = jsonParser.parse(json);
	 	    try {
	 	     smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 	    
			String spstudID=element.getAsJsonObject().get("spstudID").getAsString();
			String spclass=element.getAsJsonObject().get("spclass").getAsString();
			String attitude=element.getAsJsonObject().get("attitude").getAsString();
			String appearence=element.getAsJsonObject().get("appearence").getAsString();
			String appother=element.getAsJsonObject().get("appother").getAsString();
			String attitudeother=element.getAsJsonObject().get("attitudeother").getAsString();
			String personID=element.getAsJsonObject().get("sppersonID").getAsString();
			String createDate=element.getAsJsonObject().get("date").getAsString();
	 	     if (personID != null)
	 	     { 
	 	      String[] app=new String[] {appearence};
	 	      String[] att=new String[] {attitude};
	 	      studentPerformanceDataBean.setStuName(spstudID);;
	 	      studentPerformanceDataBean.setPerClassSection(spclass);
	 	      studentPerformanceDataBean.setStudentAppearance(app);
	 	      studentPerformanceDataBean.setStudentAttitude(att);
	 	      studentPerformanceDataBean.setAppOthers(appother);
	 	      logger.debug("appother"+appother);
	 	      studentPerformanceDataBean.setAttOthers(attitudeother);
	 	      studentPerformanceDataBean.setBdate(ft.parse(createDate));
	 	      logger.debug("attitudeother"+attitudeother);
	 	     status = smsservice.getPerformEdit(personID, studentPerformanceDataBean);
	 	      System.out.println("Status1----------------" + status);
	 	     studentPerformanceDataBean.setReturnStatus(status);
	 	      if (status.equalsIgnoreCase("Success")) {
	 	    	 MailSendJDBC.studentPerformUpdate(studentPerformanceDataBean, studentPerformanceDataBean.getSchoolName(),studentPerformanceDataBean.getSchoolID());
	 	       logger.debug("inside update methodhyrft"+emailStatus);
	 	      }
	 	     }
	 	    } catch (Exception e) {
	 	     logger.warn("Exception -->"+e.getMessage());
	 	     logger.warn(" exception - "+e);
	 	       logger.error("Exception -->"+e.getMessage());
	 	    }
	 	    return gson.toJson(studentPerformanceDataBean.getReturnStatus());
	 	  }
	 	  
	 	 @POST
	 	 @Path("/getperformDelete")
	 	
	 	 @Produces(MediaType.APPLICATION_JSON)
	 	 public String getperformDelete(String json) throws JSONException
	 	 {
	 	  Gson gson=new Gson();
	 	  String status="";
	 	  StudentPerformanceDataBean studentPerformanceDataBean=new StudentPerformanceDataBean();
	 	 JsonParser jsonParser = new JsonParser();
		 JsonElement element = jsonParser.parse(json);
		 String personID=element.getAsJsonObject().get("personID").getAsString();
		 String studid=element.getAsJsonObject().get("spstudID").getAsString(); 
	 	 studentPerformanceDataBean.setPerformStudID(studid);
	 	  try
	 	  {
	 	   if (personID != null && studentPerformanceDataBean.getPerformStudID() != null) 
	 	   {
	 	    smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 	    status = smsservice.getperformDelete(personID, studentPerformanceDataBean);
	 	    logger.debug(status);
	 	    studentPerformanceDataBean.setReturnStatus(status);
	 	   }
	 	   logger.debug("delete method calling");
	 	  }
	 	  catch(Exception e)
	 	  {
	 	   logger.warn(" exception - "+e);
	 	  }
	 	  return gson.toJson(studentPerformanceDataBean.getReturnStatus());
	 	 }
	 	 @POST
	 	  @Path("/getPerformDetails")
	 	  @Produces(MediaType.APPLICATION_JSON)
	 	 
	 	  public String getPerformDetails(String json) throws JSONException
	 	  {
	 	   logger.debug("iiiiinnnnnnnnn");
	 	   Gson gson=new Gson();
	 	   JsonParser jsonParser = new JsonParser();
			 JsonElement element = jsonParser.parse(json);
			 String studid=element.getAsJsonObject().get("spstudID").getAsString();
			 String date=element.getAsJsonObject().get("date").getAsString();
	 	   List<StudentPerformance> editList = null;
	 	   ArrayList<String> app = new ArrayList<String>();
	 	   ArrayList<String> att = new ArrayList<String>();
	 	 // boolean check;
	 	  //boolean check1;
	 	   String[] arr;
	 	   String[] arr2;
	 	   //String clssection;
	 	  // List<String> sRoll = null;
	 	  // List<String> idlist = null;
	 	   try
	 	   {
	 		 studentPerformanceDataBean.setPerformStudID(studid);
	 		studentPerformanceDataBean.setBdate(ft.parse(date));
	 	    smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 	    editList=new ArrayList<StudentPerformance>();
	 	    editList = smsservice.getPerformDetails(studentPerformanceDataBean);
	 	    System.out.println("editList -- "+editList.size());
	 	    if (editList.size() > 0) {
	 	     app.add(editList.get(0).getAppearanceStatus().trim());
	 	     arr = app.toArray(new String[] {});
	 	     ArrayList<String> applist = new ArrayList<String>();
	 	     for (String s : arr) {
	 	      String[] s2 = s.split(",");
	 	      for (String results : s2) {
	 	       applist.add(results);
	 	      }
	 	     }
	 	     String[] array = new String[applist.size()];
	 	     for (int i = 0; i < applist.size(); i++) {
	 	      array[i] = applist.get(i).trim();
	 	     }
	 	     att.add(editList.get(0).getAttitudeStatus().trim());
	 	     arr2 = att.toArray(new String[] {});
	 	     ArrayList<String> attlist = new ArrayList<String>();
	 	     for (String s : arr2) {
	 	      String[] s2 = s.split(",");
	 	      for (String results : s2) {
	 	       attlist.add(results);
	 	      }
	 	     }
	 	     String[] array1 = new String[attlist.size()];
	 	     for (int i = 0; i < attlist.size(); i++) {
	 	      array1[i] = attlist.get(i).trim();
	 	     }
	 	     studentPerformanceDataBean.setStudentAppearance(array);
	 	     studentPerformanceDataBean.setStudentAttitude(array1);
	 	     studentPerformanceDataBean.setPerClassSection(studentPerformanceDataBean.getClssection());
	 	     studentPerformanceDataBean.setPerformStudID(studentPerformanceDataBean.getPerformStudID());
	 	     //check=editList.get(0).isAppearanceOtherStatus();
	 	     //There is no usage on this check !!!
	 	     studentPerformanceDataBean.setAppOthers(editList.get(0).getAppearanceOther());
	 	     studentPerformanceDataBean.setAttitudeOther(editList.get(0).getAttitudeOther());

	 	    }

	 	   logger.debug("edit method calling");
	 	   }
	 	   catch(Exception e)
	 	   {
	 	   // logger.warn(" exception - "+e);
	 	      logger.error("Exception -->"+e.getMessage());
	 	   }
	 	   return gson.toJson(studentPerformanceDataBean);
	 	  }

	 	/*//add mark to the report card
	 	  @POST
	 	  @Path("/addReportCard")	 	  
	 	  @Produces(MediaType.APPLICATION_JSON)
	 	  public String addReportCard(String json) throws JSONException
	 	  {
	 	   Gson gson=new Gson();
	 	   String reportStatus="Failure";
	 	   ReportCardDataBean reportCardDataBean=new ReportCardDataBean();
	 	  JsonParser jsonParser = new JsonParser();
			 JsonElement element = jsonParser.parse(json);
			 String personID=element.getAsJsonObject().get("personID").getAsString();
			 String mark=element.getAsJsonObject().get("stumark").getAsString();
			 String studendrollno=element.getAsJsonObject().get("studendrollno").getAsString();
			 String markclass=element.getAsJsonObject().get("markclass").getAsString();
			 String marksubject=element.getAsJsonObject().get("marksubject").getAsString();
			 String exam=element.getAsJsonObject().get("exam").getAsString();
	 	 int submark=0;
	 	   String status="";
	 	   try
	 	   {
	 		   reportCardDataBean.setExamMarkTitle(exam);
	 		   reportCardDataBean.setStudMarkClass(markclass);
	 		   reportCardDataBean.setRollNo(studendrollno);
	 		   reportCardDataBean.setMarkSubTitle(marksubject);
	 	    if (mark != null) {
	 	     if (mark.equalsIgnoreCase("A")) {
	 	      reportCardDataBean.setMark(mark);
	 	      reportCardDataBean.setResultStatus("Absent");
	 	      reportCardDataBean.setGrade("F");	 	     
	 	     } else {
	 	      try {
	 	    	  smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 	    	 smsservice.getexammarkgrade(reportCardDataBean);
					if(reportCardDataBean.getGrade().equalsIgnoreCase("U")||reportCardDataBean.getGrade().equalsIgnoreCase("F")){
						reportCardDataBean.setResultStatus("Fail");
					}else{
						reportCardDataBean.setResultStatus("Pass");
						reportCardDataBean.setGrade("B");
					}
	 	       submark = Integer.parseInt(mark);
	 	       if (submark <= 100 && submark >= 80 || submark >= 80 && submark <= 100) {
	 	        reportCardDataBean.setMark(mark);
	 	        reportCardDataBean.setResultStatus("Pass");
	 	        reportCardDataBean.setGrade("A");
	 	       } else if (submark <= 79 && submark >= 70 || submark >= 70 && submark <= 79) {
	 	        reportCardDataBean.setMark(mark);
	 	        reportCardDataBean.setResultStatus("Pass");
	 	        reportCardDataBean.setGrade("A");
	 	       } else if (submark <= 69 && submark >= 60 || submark >= 60 && submark <= 69) {
	 	        reportCardDataBean.setMark(mark);
	 	        reportCardDataBean.setResultStatus("Pass");
	 	        reportCardDataBean.setGrade("B");
	 	       } else if (submark <= 59 && submark >= 55 || submark >= 55 && submark <= 59) {
	 	        reportCardDataBean.setMark(mark);
	 	        reportCardDataBean.setResultStatus("Pass");
	 	        reportCardDataBean.setGrade("B");
	 	       } else if (submark <= 54 && submark >= 50 || submark >= 50 && submark <= 54) {
	 	        reportCardDataBean.setMark(mark);
	 	        reportCardDataBean.setResultStatus("Pass");
	 	        reportCardDataBean.setGrade("B-");
	 	       } else if (submark <= 49 && submark >= 45 || submark >= 45 && submark <= 49) {
	 	        reportCardDataBean.setMark(mark);
	 	        reportCardDataBean.setResultStatus("Pass");
	 	        reportCardDataBean.setGrade("C");
	 	       } else if (submark <= 44 && submark >= 40 || submark >= 40 && submark <= 44) {
	 	        reportCardDataBean.setMark(mark);
	 	        reportCardDataBean.setResultStatus("Pass");
	 	        reportCardDataBean.setGrade("C");
	 	       } else if (submark <= 39 && submark >= 35 || submark >= 35 && submark <= 39) {
	 	        reportCardDataBean.setMark(mark);
	 	        reportCardDataBean.setResultStatus("Partially Pass");
	 	        reportCardDataBean.setGrade("C-");
	 	       } else if (submark <= 34 && submark >= 30 || submark >= 30 && submark <= 34) {
	 	        reportCardDataBean.setMark(mark);
	 	        reportCardDataBean.setResultStatus("Partially Pass");
	 	        reportCardDataBean.setGrade("D");
	 	       } else if (submark <= 29 && submark >= 25 || submark >= 25 && submark <= 29) {
	 	        reportCardDataBean.setMark(mark);
	 	        reportCardDataBean.setResultStatus("Partially Pass");
	 	        reportCardDataBean.setGrade("D");
	 	       } else if (submark <= 24 && submark >= 0 || submark >= 0 && submark <= 24) {
	 	        reportCardDataBean.setMark(mark);
	 	        reportCardDataBean.setResultStatus("Fail");
	 	        reportCardDataBean.setGrade("F");
	 	       } else {
	 	        logger.debug("Not valid Number");
	 	        reportCardDataBean.setMark(mark);
	 	        reportCardDataBean.setResultStatus("NA");
	 	        reportCardDataBean.setGrade("NA");
	 	       }
					status = setTableValues(reportCardDataBean);
	 	      } catch (NumberFormatException nex) {
	 	        
	 	       ReportCardDataBean rp = new ReportCardDataBean();
	 	       rp.setGrade("");
	 	       rp.setsNo(reportCardDataBean.getsNo());
	 	       rp.setResultStatus("");
	 	       rp.setName(reportCardDataBean.getName());
	 	       rp.setRollNo(reportCardDataBean.getRollNo());
	 	       rp.setMark("");

	 	      }
	 	     }
	 	    }
	 	    if (personID != null) 
	 	    {
	 	     smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 	     reportStatus = smsservice.addReportCard(personID, reportCardDataBean);
	 	    MailSendJDBC.addReportCard(reportCardDataBean,reportCardDataBean.getSchoolName());
	 	    }
	 	   }
	 	   catch(Exception e)
	 	   {
	 	    logger.warn(" exception - "+e);
	 	   }
	 	   return gson.toJson(reportCardDataBean);
	 	  }*/
	 	 
	 	// add mark to the report card
	 	@POST
	 	@Path("/addReportCard")
	 	@Produces(MediaType.APPLICATION_JSON)
	 	public String addReportCard(String json) throws JSONException {
	 		//Gson gson = new Gson();
	 		String reportStatus = "Failure";
	 		ReportCardDataBean reportCardDataBean = new ReportCardDataBean();
	 		JsonParser jsonParser = new JsonParser();
	 		JsonElement element = jsonParser.parse(json);
	 		String personID = element.getAsJsonObject().get("personID")
	 				.getAsString();
	 		String mark = element.getAsJsonObject().get("stumark").getAsString();
	 		String studendrollno = element.getAsJsonObject().get("studendrollno")
	 				.getAsString();
	 		String markclass = element.getAsJsonObject().get("markclass")
	 				.getAsString();
	 		String marksubject = element.getAsJsonObject().get("marksubject")
	 				.getAsString();
	 		String exam = element.getAsJsonObject().get("exam").getAsString();
	 		//int submark = 0;
	 		//String status = "";
	 		try {
	 			reportCardDataBean.setExamMarkTitle(exam);
	 			reportCardDataBean.setStudMarkClass(markclass);
	 			reportCardDataBean.setRollNo(studendrollno);
	 			reportCardDataBean.setMarkSubTitle(marksubject);
	 			reportCardDataBean.setMark(mark);
	 			if (mark != null) {
	 				if (mark.equalsIgnoreCase("A")) {
	 					reportCardDataBean.setMark(mark);
	 					reportCardDataBean.setResultStatus("Absent");
	 					reportCardDataBean.setGrade("F");
	 				} else {
	 					try {
	 						smsservice = (SmsService) SpringApplicationContext
	 								.getBean("smsservice");
	 						
	 						 smsservice.getexammarkgrade(reportCardDataBean);
	 						 System.out.println("grade ====== "+reportCardDataBean.getGrade());
	 						 if(reportCardDataBean.getGrade().equalsIgnoreCase("U")||reportCardDataBean.getGrade().equalsIgnoreCase("F")){
	 						  reportCardDataBean.setResultStatus("Fail"); 
	 						  }else{
	 						  reportCardDataBean.setResultStatus("Pass");
	 						 // reportCardDataBean.setGrade("B"); 
	 						 }
	 						 
	 						/*submark = Integer.parseInt(mark);
	 						if (submark <= 100 && submark >= 80 || submark >= 80
	 								&& submark <= 100) {
	 							reportCardDataBean.setMark(mark);
	 							reportCardDataBean.setResultStatus("Pass");
	 							reportCardDataBean.setGrade("A");
	 						} else if (submark <= 79 && submark >= 70
	 								|| submark >= 70 && submark <= 79) {
	 							reportCardDataBean.setMark(mark);
	 							reportCardDataBean.setResultStatus("Pass");
	 							reportCardDataBean.setGrade("A");
	 						} else if (submark <= 69 && submark >= 60
	 								|| submark >= 60 && submark <= 69) {
	 							reportCardDataBean.setMark(mark);
	 							reportCardDataBean.setResultStatus("Pass");
	 							reportCardDataBean.setGrade("B");
	 						} else if (submark <= 59 && submark >= 55
	 								|| submark >= 55 && submark <= 59) {
	 							reportCardDataBean.setMark(mark);
	 							reportCardDataBean.setResultStatus("Pass");
	 							reportCardDataBean.setGrade("B");
	 						} else if (submark <= 54 && submark >= 50
	 								|| submark >= 50 && submark <= 54) {
	 							reportCardDataBean.setMark(mark);
	 							reportCardDataBean.setResultStatus("Pass");
	 							reportCardDataBean.setGrade("B-");
	 						} else if (submark <= 49 && submark >= 45
	 								|| submark >= 45 && submark <= 49) {
	 							reportCardDataBean.setMark(mark);
	 							reportCardDataBean.setResultStatus("Pass");
	 							reportCardDataBean.setGrade("C");
	 						} else if (submark <= 44 && submark >= 40
	 								|| submark >= 40 && submark <= 44) {
	 							reportCardDataBean.setMark(mark);
	 							reportCardDataBean.setResultStatus("Pass");
	 							reportCardDataBean.setGrade("C");
	 						} else if (submark <= 39 && submark >= 35
	 								|| submark >= 35 && submark <= 39) {
	 							reportCardDataBean.setMark(mark);
	 							reportCardDataBean
	 									.setResultStatus("Partially Pass");
	 							reportCardDataBean.setGrade("C-");
	 						} else if (submark <= 34 && submark >= 30
	 								|| submark >= 30 && submark <= 34) {
	 							reportCardDataBean.setMark(mark);
	 							reportCardDataBean
	 									.setResultStatus("Partially Pass");
	 							reportCardDataBean.setGrade("D");
	 						} else if (submark <= 29 && submark >= 25
	 								|| submark >= 25 && submark <= 29) {
	 							reportCardDataBean.setMark(mark);
	 							reportCardDataBean
	 									.setResultStatus("Partially Pass");
	 							reportCardDataBean.setGrade("D");
	 						} else if (submark <= 24 && submark >= 0
	 								|| submark >= 0 && submark <= 24) {
	 							reportCardDataBean.setMark(mark);
	 							reportCardDataBean.setResultStatus("Fail");
	 							reportCardDataBean.setGrade("F");
	 						} else {
	 							logger.debug("Not valid Number");
	 							reportCardDataBean.setMark(mark);
	 							reportCardDataBean.setResultStatus("NA");
	 							reportCardDataBean.setGrade("NA");
	 						}*/
	 						//status = setTableValues(reportCardDataBean);
	 						setTableValues(reportCardDataBean);

	 					} catch (NumberFormatException nex) {
	 						ReportCardDataBean rp = new ReportCardDataBean();
	 						rp.setGrade("");
	 						rp.setsNo(reportCardDataBean.getsNo());
	 						rp.setResultStatus("");
	 						rp.setName(reportCardDataBean.getName());
	 						rp.setRollNo(reportCardDataBean.getRollNo());
	 						rp.setMark("");

	 					}catch (NullPointerException nex) {
	 						System.out.println("inside exception --- ");
	 						ReportCardDataBean rp = new ReportCardDataBean();
	 						rp.setGrade("");
	 						rp.setsNo(reportCardDataBean.getsNo());
	 						rp.setResultStatus("");
	 						rp.setName(reportCardDataBean.getName());
	 						rp.setRollNo(reportCardDataBean.getRollNo());
	 						rp.setMark("");

	 					}
	 				}
	 			}
	 			if (personID != null) {
	 				smsservice = (SmsService) SpringApplicationContext
	 						.getBean("smsservice");
	 				reportStatus = smsservice.addReportCard(personID,
	 						reportCardDataBean);
	 				MailSendJDBC.addReportCard(reportCardDataBean,
	 						reportCardDataBean.getSchoolName(),reportCardDataBean.getSchoolID());
	 			}
	 		} catch (Exception e) {
	 			  logger.error("Exception -->"+e.getMessage());
	 			//logger.warn(" exception - " + e);
	 		}
	 		return reportStatus;
	 	}
	 	  
	 	 private String setTableValues(ReportCardDataBean reportCardDataBean) {
	 		String status = "Fail";
	 		if (reportCardDataBean.getResultStatus().equalsIgnoreCase("NA")) {
	 			ReportCardDataBean rp = new ReportCardDataBean();
	 			rp.setGrade("");
	 			rp.setsNo(reportCardDataBean.getsNo());
	 			rp.setResultStatus("");
	 			rp.setName(reportCardDataBean.getName());
	 			rp.setRollNo(reportCardDataBean.getRollNo());
	 			rp.setMark("");
	 			rp.setUpflag(false);
	 			rp.setDownflag(false);
	 			studentClassList.set((Integer.parseInt(reportCardDataBean.getsNo()) - 1), rp);
	 		} else {
	 			ReportCardDataBean rp = new ReportCardDataBean();
	 			rp.setGrade(reportCardDataBean.getGrade());
	 			rp.setsNo(reportCardDataBean.getsNo());
	 			rp.setResultStatus(reportCardDataBean.getResultStatus());
	 			rp.setName(reportCardDataBean.getName());
	 			rp.setRollNo(reportCardDataBean.getRollNo());
	 			rp.setMark(reportCardDataBean.getMark());
	 			studentClassList.set((Integer.parseInt(reportCardDataBean.getsNo()) - 1), rp);
	 			/*
	 			 * FacesMessage msg = new FacesMessage("Sucessfully Saved mark"
	 			 * ,reportCardDataBean.getsNo() );
	 			 * FacesContext.getCurrentInstance().addMessage(null, msg);
	 			 */
	 			status = "Success";
	 		}
	 		return status;
	 	}
	 	  
	 	 /*Registered Login*/
	 		@POST
	 		@Path("/getRegisteredCustomer")
	 		@Produces(MediaType.APPLICATION_JSON)
	 		public String getRegisteredCustomer(String json) throws JSONException {
	 			logger.debug("inside get registered customer");
	 			JsonParser jsonParser = new JsonParser();
	 			JsonElement element = jsonParser.parse(json);
	 			String username = element.getAsJsonObject().get("rusername").getAsString();
	 			String country = element.getAsJsonObject().get("rcountry").getAsString();
	 			String phonenumber = element.getAsJsonObject().get("rphonenumber").getAsString();
	 			String emailid = element.getAsJsonObject().get("remailid").getAsString();
	 			
	 			try {
	 				registeredLogin.setUsername(username);
	 				registeredLogin.setCountry(country);
	 				registeredLogin.setEmailid(emailid);
	 				registeredLogin.setPhonenumber(phonenumber);		
	 				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 					String status = smsservice.getRegisteredCustomer(registeredLogin);
	 					registeredLogin.setStatus(status);
	 					if(status.equalsIgnoreCase("Success")){
	 						MailSendJDBC.OTPSend(registeredLogin);				
	 				}
	 			} catch (Exception e) {
	 				logger.warn("Exception -->"+e.getMessage());
	 			}
	 					
	 			return registeredLogin.getStatus();
	 		}
	 		
	 		/*OTP Login*/
	 		@POST
	 		@Path("/getOtpCustomer")
	 		@Produces(MediaType.APPLICATION_JSON)
	 		public String getOtpCustomer(String json) throws JSONException {
	 			logger.debug("-------inside getOtpCustomer-------");
	 			JsonParser jsonParser = new JsonParser();
	 			JsonElement element = jsonParser.parse(json);
	 			String username = element.getAsJsonObject().get("rusername").getAsString();
	 			String otpno = element.getAsJsonObject().get("otpno").getAsString();
	 			String mailid = element.getAsJsonObject().get("mailId").getAsString();
	 			
	 			try {
	 				registeredLogin.setUsername(username);
	 				registeredLogin.setOtpnumber(otpno);
	 				registeredLogin.setEmailid(mailid);
	 				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 					String status = smsservice.getOtpCustomer(registeredLogin);
	 					registeredLogin.setStatus(status);
	 			} catch (Exception e) {
	 				logger.warn("Exception -->"+e.getMessage());
	 			}
	 				
	 			return registeredLogin.getStatus();
	 			
	 		}
	 		
	 	/*OTP Resent*/
	 		    @POST
	 		    @Path("/getOtpResent")
	 		    @Produces(MediaType.APPLICATION_JSON)
	 		    public String getOtpResent(String json)throws JSONException{
	 			logger.debug("--------inside getOtpResent--------");
	 			JsonParser jsonParser = new JsonParser();
	 			JsonElement element = jsonParser.parse(json);
	 			String username = element.getAsJsonObject().get("rusername").getAsString();
	 			String mailid = element.getAsJsonObject().get("mailId").getAsString();
	 			try{
	 				registeredLogin.setUsername(username);
	 				registeredLogin.setEmailid(mailid);
	 				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 				String status=smsservice.getOtpResent(registeredLogin);
	 				registeredLogin.setStatus(status);			
	 			}
	 			catch (Exception e) {
	 				logger.warn("Exception -->"+e.getMessage());
	 			}
	 			return registeredLogin.getStatus();
	 			
	 		}
	 		    
	 		    // Prema 
	 		   /*Prema*/
	 		 	 
	 		   /*payment information*/
	 		   	@GET
	 		   	@Path("/getpaymentinfolist/{id}/{classname}/{classSection}")
	 		   	@Produces(MediaType.APPLICATION_JSON)
	 		   	public String getpaymentinfo(@PathParam("id") String id,@PathParam("classname") String classname,@PathParam("classSection") String classSection)
	 		   			throws JSONException {
	 		   		logger.debug("inside getsubject value change method calling");
	 		   		Gson gson = new Gson();
	 		   		List<PaymentDatabean> paymentinfolist = null;
	 		   		smsservice = (SmsService) SpringApplicationContext
	 		   				.getBean("smsservice");
	 		   		try {
	 		   			String personID = id;
	 		   			paymentDatabean.setPayClassSection(classname + "/" + classSection);
	 		   			logger.debug("class " + paymentDatabean.getPayClassSection());
	 		   			paymentinfolist = new ArrayList<PaymentDatabean>();
	 		   			paymentinfolist = smsservice.feesinfomation(personID,
	 		   					paymentDatabean);
	 		   			logger.debug("paymentinfolist" + paymentinfolist.size());
	 		   		} catch (Exception e) {
	 		   			logger.warn("inside exception", e);
	 		   		}
	 		   		finally {
	 		   			
	 		   		}
	 		   		return gson.toJson(paymentinfolist);
	 		   	}
	 		   	 	 
	 		   	/*Student List*/
	 		   	@GET
	 		   	@Path("/getpaymentStudentlist/{id}/{classname}/{classSection}")
	 		   	@Produces(MediaType.APPLICATION_JSON)
	 		   	public String getstudentlist(@PathParam("id") String id,@PathParam("classname") String classname,@PathParam("classSection") String classSection)
	 		   			throws JSONException {
	 		   		logger.debug("inside student list for parent");
	 		   		Gson gson = new Gson();
	 		   		String personID = id;
	 		   		String stuclass = classname + "/" + classSection;
	 		   		try {
	 		   			if (personID != null) {
	 		   				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 		   				studentIDList = smsservice.classStudent(personID, stuclass);
	 		   				logger.debug("student id size - " + studentIDList.size());
	 		   			}
	 		   		} catch (Exception e) {
	 		   			logger.warn("inside exception", e);
	 		   		}
	 		   		finally {
	 		   			
	 		   		}
	 		   		return gson.toJson(studentIDList);
	 		   	}
	 		   	 	 
	 		   	/*parentpayment information*/
	 		   @GET
	 		   	@Path("/getparpaymentinfolist/{id}/{classname}/{classSection}/{studentid}/{studentname}")
	 		   	@Produces(MediaType.APPLICATION_JSON)
	 		   	public String getparpaymentinfo(@PathParam("id") String id,@PathParam("classname") String classname,@PathParam("classSection") String classSection,
	 		   			@PathParam("studentid") String studentid,@PathParam("studentname") String studentname) throws JSONException {
	 		   		logger.debug("inside getparpaymentinfolist value change method calling");
	 		   		Gson gson = new Gson();
	 		   		smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 		   		try {
	 		   			String personID = id;
	 		   			paymentDatabean.setPayClassSection(classname + "/" + classSection);
	 		   			paymentDatabean.setPaymentStudID(studentid + "/" + studentname);
	 		   			smsservice.classStudentFeesView(personID, paymentDatabean);
	 		   		} catch (Exception e) {
	 		   			logger.warn("inside exception", e);
	 		   		}
	 		   		return gson.toJson(paymentDatabean);
	 		   	}
	 		   		
	 		   	/*Fees Registration classname list*/
	 		   	@GET
	 		   	@Path("/getFeesRegClassList/{id}")
	 		   	@Produces(MediaType.APPLICATION_JSON)
	 		   	public String getFeesRegClassNameList(@PathParam("id") String id)throws JSONException {
	 		   		Gson gson = new Gson();
	 		   		List<String> sRoll = null;
	 		   		String personID = id;
	 		   		try {
	 		   			if (personID != null) {
	 		   				sRoll = new ArrayList<String>();
	 		   				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 		   				smsservice.getPerClassList1(personID, paymentDatabean);
	 		   				for (int i = 0; i < paymentDatabean.getStuClassList().size(); i++) {
	 		   					sRoll.add(paymentDatabean.getStuClassList().get(i).getStuCls1());
	 		   				}
	 		   			}
	 		   		} catch (Exception e) {
	 		   			logger.warn("inside exception", e);
	 		   		}
	 		   		return gson.toJson(sRoll);
	 		   	}
	 		   	
	 		   	/*Fees Registration student list*/	 	 
	 		   	@GET
	 		   	@Path("/getFeesClassstudID/{id}/{classname}/{classSection}/{schoolID}")
	 		   	@Produces(MediaType.APPLICATION_JSON)
	 		   	public String getFeesClassstudID(@PathParam("id") String id,
	 		   			@PathParam("classname") String classname,
	 		   			@PathParam("classSection") String classSection,@PathParam("schoolID") String schoolID)
	 		   			throws JSONException {
	 		   		//String personID = id;
	 		   		Gson gson = new Gson();
	 		   		String classs = classname + "/" + classSection;
	 		   		List<String> sRoll1 = null;
	 		   		try {
	 		   			paymentDatabean.setParentID(schoolID);
	 		   			paymentDatabean.setPayClassSection(classs);
	 		   			sRoll1 = new ArrayList<String>();
	 		   			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 		   			smsservice.getPerClass(paymentDatabean);
	 		   			if (paymentDatabean.getRollnolist().size() > 0) {
	 		   				for (int i = 0; i < paymentDatabean.getRollnolist().size(); i++) {
	 		   					sRoll1.add(paymentDatabean.getRollnolist().get(i).getPaymentStudID());
	 		   				}
	 		   			} else {
	 		   				sRoll1 = new ArrayList<String>();
	 		   			}
	 		   		} catch (Exception v) {
	 		   			logger.warn(v.getMessage());
	 		   		}
	 		   		return gson.toJson(sRoll1);
	 		   	}
	 		   		 	   
	 		   	/*Fees Registration*/
	 		   	@POST
	 		   	@Path("/feesRegistration")
	 		   	@Consumes(MediaType.APPLICATION_JSON)
	 		   	@Produces(MediaType.APPLICATION_JSON)
	 		   	public String feesReg(String json) throws JSONException {
	 		   		logger.debug("Calling feesReg Method");
	 		   		Gson gson = new Gson();
	 		   		JsonParser jsonParser = new JsonParser();
	 		   		JsonElement element = jsonParser.parse(json);
	 		   		String classname = element.getAsJsonObject().get("classname").getAsString();
	 		   		String studid = element.getAsJsonObject().get("studid").getAsString();
	 		   		String examfee = element.getAsJsonObject().get("examfee").getAsString();
	 		   		String tuitionfee = element.getAsJsonObject().get("tuitionfee").getAsString();
	 		   		String transfee = element.getAsJsonObject().get("transfee").getAsString();
	 		   		String personID = element.getAsJsonObject().get("personID").getAsString();
	 		   		try {
	 		   			paymentDatabean.setPayClassSection(classname);
	 		   			paymentDatabean.setPaymentStudID(studid);
	 		   			paymentDatabean.setExamFees(examfee);
	 		   			paymentDatabean.setTuitionFees(tuitionfee);
	 		   			paymentDatabean.setTransportFees(transfee);
	 		   			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 		   			if (personID != null) {
	 		   				String status = smsservice.insertfees(personID, paymentDatabean);
	 		   				paymentDatabean.setStatus(status);
	 		   				logger.debug("status-------->" + status);
	 		   				/*
	 		   				 * if(status.equalsIgnoreCase("Success")){
	 		   				 * MailSendJDBC.feesRegister(paymentDatabean,schoolName);
	 		   				 * 
	 		   				 * }else{
	 		   				 * 
	 		   				 * }
	 		   				 */
	 		   			}
	 		   		} catch (Exception e) {
	 		   			logger.warn("Exception -->"+e.getMessage());
	 		   		}
	 		   		return gson.toJson(paymentDatabean.getStatus());
	 		   	}
	 		   		 
	 		   /* payment particular view */	  
	 		   @POST
	 		   	@Path("/getpaymentview/{id}")
	 		   	@Produces(MediaType.APPLICATION_JSON)
	 		   	public String getpaymentrview(@PathParam("id") String id, String json)throws JSONException {
	 		   		logger.debug("inside payment view method calling");
	 		   		String personID = id;
	 		   		Gson gson = new Gson();
	 		   		JsonParser jsonParser = new JsonParser();
	 		   		JsonElement element = jsonParser.parse(json);
	 		   		String classname = element.getAsJsonObject().get("classname").getAsString();
	 		   		String studentid = element.getAsJsonObject().get("studid").getAsString();
	 		   		String studentname= element.getAsJsonObject().get("studentname").getAsString();
	 		   		try {
	 		   			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 		   			paymentDatabean.setPayClassSection(classname);
	 		   			paymentDatabean.setPaymentStudID(studentid);
	 		   			paymentDatabean.setPaymentStuName(studentname);
	 		   			if (personID != null) {
	 		   				smsservice.paymentview(personID, paymentDatabean);
	 		   			}
	 		   		} catch (Exception e) {
	 		   			logger.warn("Exception -->"+e.getMessage());
	 		   		}
	 		   		return gson.toJson(paymentDatabean);
	 		   	}
	 		   		 	  
	 		     /*Payment Update*/
	 		   	@POST
	 		   	@Path("/paymentUpdate")
	 		   	@Consumes(MediaType.APPLICATION_JSON)
	 		   	@Produces(MediaType.APPLICATION_JSON)
	 		   	public String getpaymentUpdate(String json) throws JSONException {
	 		   		logger.debug("Calling payment " + "Update Method");
	 		   		Gson gson = new Gson();
	 		   		JsonParser jsonParser = new JsonParser();
	 		   		JsonElement element = jsonParser.parse(json);
	 		   		String classname = element.getAsJsonObject().get("classname").getAsString();
	 		   		String studentid = element.getAsJsonObject().get("studentid").getAsString();
	 		   		String personID = element.getAsJsonObject().get("personID").getAsString();
	 		   		String examfees = element.getAsJsonObject().get("exfee").getAsString();
	 		   		String tuitionfees = element.getAsJsonObject().get("tuifee").getAsString();
	 		   		String transportfees = element.getAsJsonObject().get("tranfee").getAsString();
	 		   		try {
	 		   			paymentDatabean.setExamFees(examfees);
	 		   			paymentDatabean.setTuitionFees(tuitionfees);
	 		   			paymentDatabean.setTransportFees(transportfees);
	 		   			paymentDatabean.setPayClassSection(classname);
	 		   			paymentDatabean.setPaymentStudID(studentid);
	 		   			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 		   			if (personID != null) {
	 		   				String status = smsservice.updatePayment(personID,paymentDatabean);
	 		   				paymentDatabean.setStatus(status);
	 		   				/*
	 		   				 * if(status.equalsIgnoreCase("Success")){
	 		   				 * MailSendJDBC.paymentUpdate(paymentDatabean,schoolName);
	 		   				 * }else{
	 		   				 * 
	 		   				 * }
	 		   				 */
	 		   			}
	 		   		} catch (Exception e) {
	 		   			logger.warn("Exception -->"+e.getMessage());
	 		   		}
	 		   		return gson.toJson(paymentDatabean.getStatus());
	 		   	}
	 		   	
	 		   	/*Payment Delete*/
	 		   	@POST
	 		   	@Path("/getPaymentDelete/{id}")
	 		   	@Consumes(MediaType.APPLICATION_JSON)
	 		   	@Produces(MediaType.APPLICATION_JSON)
	 		   	public String getPaymentDelete(@PathParam("id") String id, String json)throws JSONException {
	 		   		logger.debug("-----inside getPaymentDelete--------");
	 		   		Gson gson = new Gson();
	 		   		JsonParser jsonParser = new JsonParser();
	 		   		JsonElement element = jsonParser.parse(json);
	 		   		String status = "";
	 		   		try {
	 		   			String personID = id;
	 		   			String classname = element.getAsJsonObject().get("classname").getAsString();
	 		   			String studentid = element.getAsJsonObject().get("studentid").getAsString();
	 		   			paymentDatabean.setPayClassSection(classname);
	 		   			paymentDatabean.setPaymentStudID(studentid);
	 		   			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 		   			if (personID != null) {
	 		   				status = smsservice.deletePayment(personID, paymentDatabean);
	 		   				paymentDatabean.setStatus(status);
	 		   			}
	 		   		} catch (Exception e) {
	 		   			logger.warn("Exception -->"+e.getMessage());
	 		   		}
	 		   		return gson.toJson(paymentDatabean.getStatus());
	 		   	}
	 		   	
	 		   	/*Book payment Information*/
	 			@GET
	 		   	@Path("/getbookpaymentinfolist/{id}/{studentname}/{studentid}")
	 		   	@Produces(MediaType.APPLICATION_JSON)
	 		   	public String getbookpaymentinfo(@PathParam("id") String id,
	 		   			@PathParam("studentname") String studentname,@PathParam("studentid") String studentid) throws JSONException {
	 		   		logger.debug("inside getbookpaymentinfo method calling");
	 		   		Gson gson = new Gson();
	 		   		smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 		   		List<BookSaleDataBean> bookpaymentlist=null;
	 		   		try {
	 		   			bookpaymentlist=new ArrayList<BookSaleDataBean>();
	 		   			String personID = id;
	 		   			String stuname = studentname+"/"+studentid;
	 		   			bookSaleDataBean.setStudID(stuname);
	 		   			bookpaymentlist=smsservice.getBookViewInfo(personID, bookSaleDataBean);
	 		   		} catch (Exception e) {
	 		   			logger.warn("inside exception", e);
	 		   		}
	 		   		return gson.toJson(bookpaymentlist);
	 		   	}
	 		   	/*Book payment particular view*/
	 		   	@POST
	 		   	@Path("/getbookpaymentview/{id}")
	 		   	@Produces(MediaType.APPLICATION_JSON)
	 		   	public String getbookpaymentview(@PathParam("id") String id, String json)throws JSONException {
	 		   	logger.debug("inside getbookpayment view method calling");
	 		   		String personID = id;
	 		   		Gson gson = new Gson();
	 		   		List<BookSaleDataBean> bookinfolist=null;
	 		   		JsonParser jsonParser = new JsonParser();
	 		   		JsonElement element = jsonParser.parse(json);
	 		   		String ordernumber = element.getAsJsonObject().get("ordernumber").getAsString();
	 		   		try {
	 		   			bookinfolist=new ArrayList<BookSaleDataBean>();
	 		   			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 		   			bookSaleDataBean.setOrderNumber(ordernumber);
	 		   			if (personID != null) {
	 		   				bookinfolist=smsservice.getBookView(personID, bookSaleDataBean);
	 		   			}
	 		   		} catch (Exception e) {
	 		   			logger.warn("Exception -->"+e.getMessage());
	 		   		}
	 		   		return gson.toJson(bookinfolist);
	 		   	}
	 		   	
	 		   	/*Book Payment Approve*/
	 		   	@POST
	 		   	@Path("/getBookPaymentApprove/{id}")
	 		   	@Produces(MediaType.APPLICATION_JSON)
	 		   	public String getBookPaymentApprove(@PathParam("id") String id,String json)throws JSONException {
	 		   		logger.debug("----inside getBookPaymentApprove--------");
	 		   		Gson gson = new Gson();
	 		   		String status = "";
	 		   		try {
	 		   			String personID = id;
	 		   			JsonParser jsonParser = new JsonParser();
	 		   			JsonElement element = jsonParser.parse(json);
	 		   			String ordernumber = element.getAsJsonObject().get("ordernumber").getAsString();	
	 		   			bookSaleDataBean.setOrderNumber(ordernumber);
	 		   			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 		   			if (personID != null) {
	 		   				status=smsservice.bookpayemtapprove(personID,bookSaleDataBean);
	 		   				bookSaleDataBean.setStatus(status);
	 		   			}
	 		   		} catch (Exception e) {
	 		   			logger.warn("Exception -->"+e.getMessage());
	 		   		}
	 		   		return gson.toJson(bookSaleDataBean.getStatus());	
	 		   	}
	 		   	
	 		   /*Book Payment Download*/
	 		   	@POST
	 		   	@Path("/getBookPaymentDownload/{id}")
	 		   	@Produces(MediaType.APPLICATION_JSON)
	 		   	public String getBookPaymentDownload(@PathParam("id")String id,String json)throws JSONException {
	 		   		logger.debug("-----inside getBookPaymentDownload--------");
	 		   		Gson gson = new Gson();
	 		   		bookinfolist=new ArrayList<BookSaleDataBean>();
	 		   		String status = "";
	 		   		try {
	 		   			String personID = id;
	 		   			JsonParser jsonParser = new JsonParser();
	 		   			JsonElement element = jsonParser.parse(json);
	 		   			String ordernumber = element.getAsJsonObject().get("ordernumber").getAsString();		
	 		   			bookSaleDataBean.setOrderNumber(ordernumber);
	 		   			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 		   			if (personID != null) {
	 		   				bookinfolist = smsservice.getBookView(personID, bookSaleDataBean);
	 		   				bookSaleDataBean.setStatus(status);
	 		   			}
	 		   		} catch (Exception e) {
	 		   			logger.warn("Exception -->"+e.getMessage());
	 		   		}
	 		   		return gson.toJson(bookSaleDataBean.getStatus());
	 		   		
	 		   	}
	 		   	
	 		   	/*Book Payment Delete*/
	 		   	@POST
	 		   	@Path("/getBookPaymentDelete/{id}")
	 		   	@Produces(MediaType.APPLICATION_JSON)
	 		   	public String getBookPaymentDelete(@PathParam("id") String id,String json)throws JSONException {
	 		   		logger.debug("-----inside getBookPaymentDelete--------");
	 		   		Gson gson = new Gson();
	 		   		String status = "";
	 		   		try {
	 		   			String personID = id;
	 		   			JsonParser jsonParser = new JsonParser();
	 		   			JsonElement element = jsonParser.parse(json);
	 		   			String ordernumber = element.getAsJsonObject().get("ordernumber").getAsString();		
	 		   			bookSaleDataBean.setOrderNumber(ordernumber);
	 		   			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 		   			if (personID != null) {
	 		   				status = smsservice.bookpayemtreject(personID,bookSaleDataBean);
	 		   				bookSaleDataBean.setStatus(status);
	 		   			}
	 		   		} catch (Exception e) {
	 		   			logger.warn("Exception -->"+e.getMessage());
	 		   		}
	 		   		return gson.toJson(bookSaleDataBean.getStatus());
	 		   	}
	 		   	
	 			@POST
	 		   	@Path("/bookpaymentsave")
	 		   	@Produces(MediaType.APPLICATION_JSON)
	 		   	public String bookpaymentsave(String json)throws JSONException{
	 				
	 				Gson gson = new Gson();
	 				JsonParser jsonParser = new JsonParser();
 		   			JsonElement element = jsonParser.parse(json);
 		   			String ordernumber = element.getAsJsonObject().get("ordernumber").getAsString();
 		   			String personID = element.getAsJsonObject().get("personID").getAsString();
 		   			String filepath = element.getAsJsonObject().get("filepath").getAsString();
 		   			if(personID!=null){
 		   				try{
 		   				bookSaleDataBean.setOrderNumber(ordernumber);
 		   				bookSaleDataBean.setBookfilePath(filepath);
 		   				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
 		   				String status=smsservice.bookpaymentupload(personID,bookSaleDataBean);
 		   				bookSaleDataBean.setReturnStatus(status);
 		   				}catch(Exception e){
 		   					  logger.error("Exception -->"+e.getMessage());
 		   				}
 		   			}
	 				return gson.toJson(bookSaleDataBean.getReturnStatus());
	 			}
	 		   	
	 		   	// Udhaya 07-oct-2016
	 		   @POST
	 		    @Path("/bookshopReg")
	 		    @Produces(MediaType.APPLICATION_JSON)
	 		    public String bookshopReg(String json)throws JSONException{
	 		     logger.debug("inside bookshopreg");
	 		     String status="Fail";//String emailStatus="Fail";
	 		     logger.debug("inside bookshopreg");
	 		     StaffDataBean staffDataBean=new StaffDataBean();
	 		     JsonParser jsonParser = new JsonParser();
	 		     JsonElement element = jsonParser.parse(json);
	 		     String id=element.getAsJsonObject().get("bsid").getAsString();
	 		     String name=element.getAsJsonObject().get("bsfirstname").getAsString();
	 		     String lastname=element.getAsJsonObject().get("bslastname").getAsString(); 
	 		     String gender=element.getAsJsonObject().get("bsgender").getAsString();
	 		     String mailid=element.getAsJsonObject().get("bsmailid").getAsString();
	 		     String countrycode=element.getAsJsonObject().get("bscountrycode").getAsString();
	 		     String phoneno=element.getAsJsonObject().get("bsphoneno").getAsString();
	 		     String personID=element.getAsJsonObject().get("personID").getAsString();
	 		     String path=element.getAsJsonObject().get("path").getAsString();
	 		     String schoolName="";
	 		     logger.debug("befor try"+personID+">>>>"+name);
	 		     
	 		     try{
	 		      staffDataBean.setStaEmail(mailid);
	 		      staffDataBean.setStaFirstName(name);
	 		      staffDataBean.setStaGender(gender);
	 		      staffDataBean.setStaLastName(lastname);
	 		      staffDataBean.setStaPhoneNo(phoneno);
	 		      staffDataBean.setStaStaffID(id);
	 		      if(path.equals("") || path.equals(null)){
	 		    	 staffDataBean.setStafilePath(""); 
	 		      }else{
	 		    	  staffDataBean.setStafilePath(path); 
	 		      }
	 		      if (personID != null) 
	 		      {
	 		       smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 		       status= smsservice.insertStaff(personID, staffDataBean);
	 		       schoolName=staffDataBean.getName();
	 		       staffDataBean.setReturnStatus(status);
	 		       if (status.equalsIgnoreCase("Success")) {
	 		      //  List<StaffDataBean> ImageListPath = new ArrayList<StaffDataBean>();
	 		       // ImageListPath = smsservice.getImagePath3(personID, staffDataBean.getStaStaffID());
	 		       /* String pdfStatus=GeneratePdfMail.generatePdfBookshop(staffDataBean,ImageListPath,personID);
	 		        if(pdfStatus.equalsIgnoreCase("Success")){*/
	 		        MailSendJDBC.bookShopInsert(staffDataBean,schoolName,String.valueOf(staffDataBean.getSchoolID()));	         
	 		          staffDataBean.setStaEmail(null);staffDataBean.setStaFirstName(null);
	 		          staffDataBean.setStaGender(null);staffDataBean.setStaLastName(null);
	 		          staffDataBean.setStaPhoneNo(null);staffDataBean.setStaStaffID(null);
	 		          staffDataBean.setStafilePath(null);staffDataBean.setCountryCode(null); 	        
	 		        }
	 		        
	 		       }     
	 		      //} 
	 		     }
	 		     catch(Exception e){
	 		        logger.error("Exception -->"+e.getMessage());
	 		     }
	 		     return staffDataBean.getReturnStatus();  
	 		    }
	 		 	
	 		 	@GET
	 		 	@Produces(MediaType.APPLICATION_JSON)
	 		 	@Path("/bookShopView/{id}")
	 		 	public String bookShopView(@PathParam("id") String personID)throws JSONException{
	 		 		logger.debug("inside book shop view ");
	 		 		Gson gson=new Gson();
	 		 		try{
	 		 			if(personID!=null){
	 		 				staffDataBean.setRollcheck("BookShop");
	 		 				smsservice=(SmsService) SpringApplicationContext.getBean("smsservice");
	 		 				bookShoplist=smsservice.staffinfomation(personID, staffDataBean);
	 		 				logger.debug("bookshop size "+bookShoplist.size());
	 		 			}
	 		 		}catch(Exception e){
	 		 			logger.warn(" exception - "+e);
	 		 		}
	 		 		return gson.toJson(bookShoplist);
	 		 	}
	 		 	
	 		 	@GET
	 		 	@Produces(MediaType.APPLICATION_JSON)
	 		 	@Path("/bookShopsView/{id}/{bookShopID}")
	 		 	public String bookShopsView(@PathParam("id") String personID,
	 		 			@PathParam("bookShopID") String bookShopID)throws JSONException{
	 		 		logger.debug("inside book shop view ");
	 		 		Gson gson=new Gson();
	 		 		try{
	 		 			if(personID!=null){
	 		 				staffDataBean.setStaStaffID(bookShopID);
	 		 				smsservice=(SmsService) SpringApplicationContext.getBean("smsservice");
	 		 				bookShoplist=smsservice.tagstaffview(personID, staffDataBean);
	 		 				logger.debug("bookshop size "+bookShoplist.size());
	 		 				if (bookShoplist.size() > 0) {
	 							if (bookShoplist.get(0).getStaffList().size() > 0) {
	 								staffDataBean.setStaStaffID(bookShoplist.get(0).getStaffList().get(0).getRollNumber());
	 								staffDataBean.setName(bookShoplist.get(0).getStaffList().get(0).getFirstName() + " "
	 										+ bookShoplist.get(0).getStaffList().get(0).getLastName());
	 								staffDataBean.setStaGender(bookShoplist.get(0).getStaffList().get(0).getGender());
	 								staffDataBean.setStaPhoneNo(bookShoplist.get(0).getStaffList().get(0).getPhoneNumber());
	 								staffDataBean.setStaEmail(bookShoplist.get(0).getStaffList().get(0).getEmailId());
	 								staffDataBean.setStaFirstName(bookShoplist.get(0).getStaffList().get(0).getFirstName());
	 								staffDataBean.setStaLastName(bookShoplist.get(0).getStaffList().get(0).getLastName());
	 								staffDataBean.setCountryCode(bookShoplist.get(0).getStaffList().get(0).getCountryCode()); 
	 							} else {
	 								staffDataBean.setStaStaffID("");
	 								staffDataBean.setName("");
	 								staffDataBean.setStaGender("");
	 								staffDataBean.setStaPhoneNo("");
	 								staffDataBean.setStaEmail("");
	 								staffDataBean.setStaFirstName("");
	 								staffDataBean.setStaLastName("");
	 								staffDataBean.setCountryCode(""); 
	 								
	 							}
	 							staffDataBean.setStafilePath("");
	 							if (bookShoplist.get(0).getImagepath().size() == 0 || bookShoplist.get(0).getImagepath()==null) {
	 								staffDataBean.setStafilePath(" ");
	 								staffDataBean.setStaPathDate(null);
	 							} else {
	 								staffDataBean.setStafilePath(bookShoplist.get(0).getImagepath().get(0).getPath());
	 								staffDataBean.setStaPathDate(bookShoplist.get(0).getImagepath().get(0).getDate());
	 								String file=paths.getString("sms.bookshop.photo");
		 							String pathname=(file+format.format(staffDataBean.getStaPathDate())+paths.getString("path_context").toString()+staffDataBean.getStafilePath());
		 							String encodestring=convertimage(pathname);
									staffDataBean.setStafilePath(encodestring);
	 							}
	 		 				}
	 		 			}
	 		 		}catch(Exception e){
	 		 			logger.warn(" exception - "+e);
	 		 		}
	 		 		return gson.toJson(staffDataBean);	 		
	 		 	}
	 		 	
	 		 	@POST
	 		 	@Produces(MediaType.APPLICATION_JSON)
	 		 	@Path("/booShopUpdate")
	 		 	public String booShopUpdate(String json){
	 		 		staffDataBean.setRollcheck("BookShop");
	 		 		JsonParser jsonParser = new JsonParser();
	 			    JsonElement element = jsonParser.parse(json);
	 			    String personID=element.getAsJsonObject().get("bpersonID").getAsString();
	 			    String bookShopID=element.getAsJsonObject().get("bookShopID").getAsString();
	 			    String bookFname=element.getAsJsonObject().get("bookFname").getAsString();
	 			    String bookLname=element.getAsJsonObject().get("bookLname").getAsString();
	 			    String bookGender=element.getAsJsonObject().get("bookGender").getAsString();
	 			    String bookemail=element.getAsJsonObject().get("bookemail").getAsString();
	 			    String bookphno=element.getAsJsonObject().get("bookPhno").getAsString();
	 			    String bookCode=element.getAsJsonObject().get("bookCode").getAsString();
	 			    String filename=element.getAsJsonObject().get("path").getAsString();
	 		 		try{
	 		 			if(personID!=null){
	 		 				staffDataBean.setStaStaffID(bookShopID);
	 		 				staffDataBean.setStaFirstName(bookFname);
	 		 				staffDataBean.setStaLastName(bookLname);
	 		 				staffDataBean.setStaGender(bookGender);
	 		 				staffDataBean.setStaPhoneNo(bookphno);
	 		 				staffDataBean.setStaEmail(bookemail);
	 		 				staffDataBean.setCountryCode(bookCode); 
	 		 				if(filename==null || filename.equalsIgnoreCase(""))
	 		 					staffDataBean.setStafilePath(null);
	 		 				else
	 		 					staffDataBean.setStafilePath(filename);
	 		 				smsservice=(SmsService) SpringApplicationContext.getBean("smsservice");
	 		 				String status = smsservice.updateStaff(personID, staffDataBean);
	 		 				staffDataBean.setReturnStatus(status);
	 		 				if (status.equalsIgnoreCase("Success")) {
	 		 					ImageListPath = smsservice.getImagePathstaff3(personID, staffDataBean.getStaStaffID());
	 							/*String pdfStatus = GeneratePdfMail.generatePdfBookShop(personID,ImageListPath,staffDataBean);*/	
 							 MailSendJDBC.bookShopUpdate(staffDataBean,staffDataBean.getSchoolName(),staffDataBean.getSchoolID());
	 							staffDataBean.setReturnStatus(status);
	 		 				}
	 		 			}
	 		 		}catch(Exception e){
	 		 			logger.warn(" exception - "+e);
	 		 		}
	 		 		return staffDataBean.getReturnStatus();	 		
	 		 	}
	 		 	
	 		 	@GET
	 		 	@Produces(MediaType.APPLICATION_JSON)
	 		 	@Path("/booShopDelete/{id}/{shopID}")
	 		 	public String booShopDelete(@PathParam("id") String personID,
	 		 			@PathParam("shopID") String bookShopID){
	 		 		staffDataBean.setRollcheck("BookShop");
	 		 		Gson gson=new Gson();
	 		 		try{
	 		 			if(personID!=null){
	 		 				staffDataBean.setStaStaffID(bookShopID);
	 		 				smsservice=(SmsService) SpringApplicationContext.getBean("smsservice");
	 		 				smsservice.deleteStaff(personID, staffDataBean);
	 		 				staffDataBean.setReturnStatus("Success");	 	
	 		 			}
	 		 		}catch(Exception e){
	 		 			logger.warn(" exception - "+e);
	 		 		}
	 		 		return gson.toJson(staffDataBean.getReturnStatus());	 		
	 		 	}
	 		 	
	 		 	//Teacher image upload
	 		 	@POST
			 	@Path("/uploadimage")
		 		@Produces(MediaType.APPLICATION_JSON)
			 	 public String uploadimage(String json)throws JSONException, IOException{
		 	 		logger.debug("inside image upload-----");
		 	 		Gson gson=new Gson();
		 	 		JsonParser jsonParser = new JsonParser();
		 			JsonElement element = jsonParser.parse(json);
		 			String id=element.getAsJsonObject().get("srcData").getAsString();
		 			String filename=element.getAsJsonObject().get("filename").getAsString();
		 			try{
		 			String base64Image = id.split(",")[1];
		 			byte[] decoded = Base64.decodeBase64(base64Image);
		 			File files = new File(paths.getString("sms.teacher.teacherphoto") +format.format(now));
					if (!files.exists()) {
						files.mkdirs();
					} else {
						logger.debug("Alreday Found");
					}
		 			FileOutputStream fos = null;
		 			fos = new FileOutputStream(files+paths.getString("path_context").toString()+filename+".jpg");
		 			fos.write(decoded);
		 			fos.close();
		 			String path = format.format(now) + paths.getString("path_context").toString() + filename + ".jpg";
					teacherDataBean.setTeafilePath(path);
		 			}catch(Exception e){
		 				  logger.error("Exception -->"+e.getMessage());
		 			}
			        return gson.toJson(teacherDataBean.getTeafilePath());
		 	 }

	 		 	//Student image upload
	 		 	@POST
			 	@Path("/studentuploadimage")
		 		@Produces(MediaType.APPLICATION_JSON)
			 	 public String studentuploadimage(String json)throws JSONException, IOException{
		 	 		logger.debug("inside image upload-----");
		 	 		Gson gson=new Gson();
		 	 		JsonParser jsonParser = new JsonParser();
		 			JsonElement element = jsonParser.parse(json);
		 			String id=element.getAsJsonObject().get("srcData").getAsString();
		 			String filename=element.getAsJsonObject().get("filename").getAsString();
		 			try{
		 			String base64Image = id.split(",")[1];
		 			byte[] decoded = Base64.decodeBase64(base64Image);
		 			File files = new File(paths.getString("sms.student.photo") +format.format(now));
					if (!files.exists()) {
						files.mkdirs();
					} else {
						logger.debug("Alreday Found");
					}
		 			FileOutputStream fos = null;
		 			fos = new FileOutputStream(files+paths.getString("path_context").toString()+filename+".jpg");
		 			fos.write(decoded);
		 			fos.close();
		 			String path = format.format(now) + paths.getString("path_context").toString() + filename + ".jpg";
		 			studentDataBean.setStufilePath(path);
		 			}catch(Exception e){
		 				  logger.error("Exception -->"+e.getMessage());
		 			}
			        return gson.toJson(studentDataBean.getStufilePath());
		 	 }
	 		
	 		 	//Parent image upload
	 		 	@POST
			 	@Path("/parentuploadimage")
		 		@Produces(MediaType.APPLICATION_JSON)
			 	 public String parentuploadimage(String json)throws JSONException, IOException{
		 	 		logger.debug("inside image upload-----");
		 	 		Gson gson=new Gson();
		 	 		JsonParser jsonParser = new JsonParser();
		 			JsonElement element = jsonParser.parse(json);
		 			String id=element.getAsJsonObject().get("srcData").getAsString();
		 			String filename=element.getAsJsonObject().get("filename").getAsString();
		 			try{
		 			String base64Image = id.split(",")[1];
		 			byte[] decoded = Base64.decodeBase64(base64Image);
		 			File files = new File(paths.getString("sms.parents.photo") +format.format(now));
					if (!files.exists()) {
						files.mkdirs();
					} else {
						logger.debug("Alreday Found");
					}
		 			FileOutputStream fos = null;
		 			fos = new FileOutputStream(files+paths.getString("path_context").toString()+filename+".jpg");
		 			fos.write(decoded);
		 			fos.close();
		 			String path = format.format(now) + paths.getString("path_context").toString() + filename + ".jpg";
		 			parentDataBean.setParfilePath(path);
		 			}catch(Exception e){
		 				  logger.error("Exception -->"+e.getMessage());
		 			}
			        return gson.toJson(parentDataBean.getParfilePath());
		 	 }
	 		 	
	 		 	//Staff image upload
	 		 	@POST
			 	@Path("/staffuploadimage")
		 		@Produces(MediaType.APPLICATION_JSON)
			 	 public String staffuploadimage(String json)throws JSONException, IOException{
		 	 		logger.debug("inside image upload-----");
		 	 		Gson gson=new Gson();
		 	 		JsonParser jsonParser = new JsonParser();
		 			JsonElement element = jsonParser.parse(json);
		 			String id=element.getAsJsonObject().get("srcData").getAsString();
		 			String filename=element.getAsJsonObject().get("filename").getAsString();
		 			try{
		 			String base64Image = id.split(",")[1];
		 			byte[] decoded = Base64.decodeBase64(base64Image);
		 			File files = new File(paths.getString("sms.staff.photo") +format.format(now));
					if (!files.exists()) {
						files.mkdirs();
					} else {
						logger.debug("Alreday Found");
					}
		 			FileOutputStream fos = null;
		 			fos = new FileOutputStream(files+paths.getString("path_context").toString()+filename+".jpg");
		 			fos.write(decoded);
		 			fos.close();
		 			String path = format.format(now) + paths.getString("path_context").toString() + filename + ".jpg";
		 			staffDataBean.setStafilePath(path);
		 			}catch(Exception e){
		 				  logger.error("Exception -->"+e.getMessage());
		 			}
			        return gson.toJson(staffDataBean.getStafilePath());
		 	 }
	 		 	
	 		 //Bookshop image upload
	 		 	@POST
			 	@Path("/bookshopuploadimage")
		 		@Produces(MediaType.APPLICATION_JSON)
			 	 public String bookshopuploadimage(String json)throws JSONException, IOException{
		 	 		logger.debug("inside image upload-----");
		 	 		Gson gson=new Gson();
		 	 		JsonParser jsonParser = new JsonParser();
		 			JsonElement element = jsonParser.parse(json);
		 			String id=element.getAsJsonObject().get("srcData").getAsString();
		 			String filename=element.getAsJsonObject().get("filename").getAsString();
		 			try{
		 			String base64Image = id.split(",")[1];
		 			byte[] decoded = Base64.decodeBase64(base64Image);
		 			File files = new File(paths.getString("sms.bookshop.photo") +format.format(now));
					if (!files.exists()) {
						files.mkdirs();
					} else {
						logger.debug("Alreday Found");
					}
		 			FileOutputStream fos = null;
		 			fos = new FileOutputStream(files+paths.getString("path_context").toString()+filename+".jpg");
		 			fos.write(decoded);
		 			fos.close();
		 			String path = format.format(now) + paths.getString("path_context").toString() + filename + ".jpg";
		 			staffDataBean.setStafilePath(path);
		 			}catch(Exception e){
		 				  logger.error("Exception -->"+e.getMessage());
		 			}
			        return gson.toJson(staffDataBean.getStafilePath());
		 	 }
	 		 	
	 		 //Bookshop image upload
	 		 	@POST
			 	@Path("/librarianuploadimage")
		 		@Produces(MediaType.APPLICATION_JSON)
			 	 public String librarianuploadimage(String json)throws JSONException, IOException{
		 	 		logger.debug("inside image upload-----");
		 	 		Gson gson=new Gson();
		 	 		JsonParser jsonParser = new JsonParser();
		 			JsonElement element = jsonParser.parse(json);
		 			String id=element.getAsJsonObject().get("srcData").getAsString();
		 			String filename=element.getAsJsonObject().get("filename").getAsString();
		 			try{
		 			String base64Image = id.split(",")[1];
		 			byte[] decoded = Base64.decodeBase64(base64Image);
		 			File files = new File(paths.getString("sms.librarian.photo") +format.format(now));
					if (!files.exists()) {
						files.mkdirs();
					} else {
						logger.debug("Alreday Found");
					}
		 			FileOutputStream fos = null;
		 			fos = new FileOutputStream(files+paths.getString("path_context").toString()+filename+".jpg");
		 			fos.write(decoded);
		 			fos.close();
		 			String path = format.format(now) + paths.getString("path_context").toString() + filename + ".jpg";
		 			staffDataBean.setStafilePath(path);
		 			}catch(Exception e){
		 				  logger.error("Exception -->"+e.getMessage());
		 			}
			        return gson.toJson(staffDataBean.getStafilePath());
		 	 }
	 		 	
	 		 	
	 			private String convertimage(String filename)throws IOException{
	 		 		 String imageDataString = null;
	 		 		try{
	 		 			if(filename!=null || !filename.equals("")){
	 		 			 File file = new File(filename);
	 					 FileInputStream imageInFile = new FileInputStream(file);
	 			            byte imageData[] = new byte[(int) file.length()];
	 			            imageInFile.read(imageData);
	 			 
	 			            // Converting Image byte array into Base64 String
	 			             imageDataString = Base64.encodeBase64String(imageData);
	 		 			}
	 		 		}catch(FileNotFoundException e){
	 		 			  logger.error("Exception -->"+e.getMessage());
	 		 		}
	 				return imageDataString;
	 		 		
	 		 	}		
	 			
	 			//Payment upload
	 		 	@POST
			 	@Path("/paymentupload")
		 		@Produces(MediaType.APPLICATION_JSON)
			 	 public String paymentupload(String json)throws JSONException, IOException{
		 	 		logger.debug("inside image upload-----");
		 	 		String path="";
		 	 		Gson gson=new Gson();
		 	 		JsonParser jsonParser = new JsonParser();
		 			JsonElement element = jsonParser.parse(json);
		 			String id=element.getAsJsonObject().get("srcData").getAsString();
		 			String filename=element.getAsJsonObject().get("filename").getAsString();
		 			String filetype=element.getAsJsonObject().get("filetype").getAsString();
		 			try{
		 			String base64Image = id.split(",")[1];
		 			byte[] decoded = Base64.decodeBase64(base64Image);
		 			File files = new File(paths.getString("sms.feespay.photo") +format.format(now));
					if (!files.exists()) {
						files.mkdirs();
					} else {
						logger.debug("Alreday Found");
					}
		 			FileOutputStream fos = null;
		 			if(filetype.equals("application/pdf")){
		 			fos = new FileOutputStream(files+paths.getString("path_context").toString()+filename+".pdf");
		 			fos.write(decoded);
		 			fos.close();
		 			path=(format.format(now)+paths.getString("path_context").toString()+filename+".pdf");
		 			}else{
		 				fos = new FileOutputStream(files+paths.getString("path_context").toString()+filename+".jpg");
			 			fos.write(decoded);
			 			fos.close();
			 		path=(format.format(now)+paths.getString("path_context").toString()+filename+".jpg");
		 			}
		 			}catch(Exception e){
		 				  logger.error("Exception -->"+e.getMessage());
		 			}
			        return gson.toJson(path);
		 	 }	
	 		 	
	 		 //Book Payment upload 
	 		 	@POST
			 	@Path("/bookpaymentupload")
		 		@Produces(MediaType.APPLICATION_JSON)
			 	 public String bookpaymentupload(String json)throws JSONException, IOException{
		 	 		logger.debug("inside image upload-----");
		 	 		String path="";
		 	 		Gson gson=new Gson();
		 	 		JsonParser jsonParser = new JsonParser();
		 			JsonElement element = jsonParser.parse(json);
		 			String id=element.getAsJsonObject().get("srcData").getAsString();
		 			String filename=element.getAsJsonObject().get("filename").getAsString();
		 			String filetype=element.getAsJsonObject().get("filetype").getAsString();
		 			try{
		 			String base64Image = id.split(",")[1];
		 			byte[] decoded = Base64.decodeBase64(base64Image);
		 			File files = new File(paths.getString("sms.bookpayment.photo") +format.format(now));
					if (!files.exists()) {
						files.mkdirs();
					} else {
						logger.debug("Alreday Found");
					}
		 			FileOutputStream fos = null;
		 			if(filetype.equals("application/pdf")){
		 			fos = new FileOutputStream(files+paths.getString("path_context").toString()+filename+".pdf");
		 			fos.write(decoded);
		 			fos.close();
		 			path = format.format(now) + paths.getString("path_context").toString() + filename + ".pdf";
		 			}else{
		 				fos = new FileOutputStream(files+paths.getString("path_context").toString()+filename+".jpg");
			 			fos.write(decoded);
			 			fos.close();
			 			path = format.format(now) + paths.getString("path_context").toString() + filename + ".jpg";	
		 			}
		 			}catch(Exception e){
		 				  logger.error("Exception -->"+e.getMessage());
		 			}
			        return gson.toJson(path);
		 	 }	
	 		 	
	 		 	
	 		 	@POST
			 	@Path("/download")
		 		@Produces(MediaType.APPLICATION_JSON)
			 	 public String download(String json)throws JSONException, IOException{
		 	 		logger.debug("inside image download-----");
		 	 		String imageDataString=null;
		 	 		Gson gson=new Gson();
		 	 		JsonParser jsonParser = new JsonParser();
		 			JsonElement element = jsonParser.parse(json);
		 			String studentid=element.getAsJsonObject().get("studentid").getAsString();
		 			String classname=element.getAsJsonObject().get("classname").getAsString();
		 			String personID=element.getAsJsonObject().get("personID").getAsString();
		 			FileInputStream imageInFile=null;
		 			try{
		 				paymentDatabean.setPaymentStudID(studentid);
		 				paymentDatabean.setPayClassSection(classname);
		 				if(personID!=null){
		 				smsservice=(SmsService) SpringApplicationContext.getBean("smsservice");
 		 				smsservice.paymentview(personID, paymentDatabean);
		 				 String path=paymentDatabean.getFilePath();
		 				 String filename=path.split("/")[1];
		 				 File file = new File(paths.getString("sms.feespay.photo").toString()+paymentDatabean.getFilePath());
	 					 imageInFile = new FileInputStream(file);
	 			            byte imageData[] = new byte[(int) file.length()];
	 			           if(imageInFile!=null) {
	 			            imageInFile.read(imageData);
	 			           }
	 			            // Converting Image/file byte array into Base64 String
	 			             imageDataString = Base64.encodeBase64String(imageData);
	 			           paymentDatabean.setExamFees(filename);
	 			           paymentDatabean.setPaymentFees(imageDataString);
		 				}
		 			}catch(Exception e){
		 				  logger.error("Exception -->"+e.getMessage());
		 			}
			        return gson.toJson(paymentDatabean);
		 	 }
	 		 	
	 		 	@POST
	 		 	@Path("/paymentapprove")
		 		@Produces(MediaType.APPLICATION_JSON)
	 		 	public String paymentApprove(String json) throws JSONException{
	 		 		String status ="";
	 		 		Gson gson=new Gson();
	 		 		JsonParser jsonParser = new JsonParser();
		 			JsonElement element = jsonParser.parse(json);
		 			String studentid=element.getAsJsonObject().get("studentid").getAsString();
		 			String classname=element.getAsJsonObject().get("classname").getAsString();
		 			String personID=element.getAsJsonObject().get("personID").getAsString();
		 			if(personID!=null){
	 		 		try{
	 		 			paymentDatabean.setPaymentStudID(studentid);
		 				paymentDatabean.setPayClassSection(classname);
		 				paymentDatabean.setStatus("Approved");
	 		 			smsservice=(SmsService) SpringApplicationContext.getBean("smsservice");
	 		 			smsservice.approveRejectFees(personID, paymentDatabean);
	 		 			MailSendJDBC.paymentApprove(paymentDatabean,paymentDatabean.getSchool_Name());
	 		 			status="Success";
	 		 		}catch(Exception e){
	 		 			  logger.error("Exception -->"+e.getMessage());
	 		 			status="Fail";
	 		 		}
		 			}
					return gson.toJson(status);
	 		 	}
	 	
	 		 	@POST
	 		 	@Path("/paymentreject")
		 		@Produces(MediaType.APPLICATION_JSON)
	 		 	public String paymentReject(String json) throws JSONException{
	 		 		String status ="";
	 		 		Gson gson=new Gson();
	 		 		JsonParser jsonParser = new JsonParser();
		 			JsonElement element = jsonParser.parse(json);
		 			String studentid=element.getAsJsonObject().get("studentid").getAsString();
		 			String classname=element.getAsJsonObject().get("classname").getAsString();
		 			String personID=element.getAsJsonObject().get("personID").getAsString();
		 			if(personID!=null){
	 		 		try{
	 		 			paymentDatabean.setPaymentStudID(studentid);
		 				paymentDatabean.setPayClassSection(classname);
		 				paymentDatabean.setStatus("Rejected");
	 		 			smsservice=(SmsService) SpringApplicationContext.getBean("smsservice");
	 		 			smsservice.approveRejectFees(personID, paymentDatabean);
	 		 			MailSendJDBC.paymentApprove(paymentDatabean,paymentDatabean.getSchool_Name());
	 		 			status="Success";
	 		 		}catch(Exception e){
	 		 			  logger.error("Exception -->"+e.getMessage());
	 		 			status="Fail";
	 		 		}
		 			}
					return gson.toJson(status);
	 		 	}
	 		 	
	 		@POST
	 		@Path("/paymentsave")
	 		@Produces(MediaType.APPLICATION_JSON)
	 		public String paymentSave(String json)throws JSONException{
	 			String status ="";
	 			Gson gson=new Gson();
	 		 	JsonParser jsonParser = new JsonParser();
		 		JsonElement element = jsonParser.parse(json);
		 		String studentid=element.getAsJsonObject().get("studentid").getAsString();
		 		String classname=element.getAsJsonObject().get("classname").getAsString();
		 		String personID=element.getAsJsonObject().get("personID").getAsString();
		 		String filepath=element.getAsJsonObject().get("filepath").getAsString();
		 		if(personID!=null){
		 		try{
		 			paymentDatabean.setPayClassSection(classname);
		 			paymentDatabean.setPaymentStudID(studentid);
		 			paymentDatabean.setFilePath(filepath);
		 			smsservice=(SmsService) SpringApplicationContext.getBean("smsservice");
		 			smsservice.feespay(personID,paymentDatabean);
		 			MailSendJDBC.feespay(paymentDatabean, paymentDatabean.getSchool_Name());
		 			status="Success";
	 			}catch(Exception e){
	 				  logger.error("Exception -->"+e.getMessage());
	 				 status ="Fail";
	 			}
	 		}
				return gson.toJson(status);
	 		}
	 		
	 		@POST
		 	@Path("/bookpaymentdownload")
	 		@Produces(MediaType.APPLICATION_JSON)
		 	 public String bookpaymentdownload(String json)throws JSONException, IOException{
	 	 		logger.debug("inside image download-----");
	 	 		String imageDataString=null;
	 	 		//List<BookSaleDataBean> bookinfolist=null;
	 	 		Gson gson=new Gson();
	 	 		JsonParser jsonParser = new JsonParser();
	 			JsonElement element = jsonParser.parse(json);
	 			String ordernumber=element.getAsJsonObject().get("ordernumber").getAsString();
	 			String personID=element.getAsJsonObject().get("personID").getAsString();
	 			FileInputStream imageInFile=null;
	 			try{
	 				bookSaleDataBean.setOrderNumber(ordernumber);
	 				if(personID!=null){
	 				 bookinfolist=new ArrayList<BookSaleDataBean>();
	 				 smsservice=(SmsService) SpringApplicationContext.getBean("smsservice");
	 				 bookinfolist=smsservice.getBookView(personID, bookSaleDataBean);
	 				 String path=bookSaleDataBean.getFilePath();
	 				 String filename=path.split("/")[1];
	 				 File file = new File(paths.getString("sms.bookpayment.photo").toString()+path);
 					 imageInFile = new FileInputStream(file);
 			            byte imageData[] = new byte[(int) file.length()];
 			            imageInFile.read(imageData);
 			 
 			            // Converting Image/file byte array into Base64 String
 			             imageDataString = Base64.encodeBase64String(imageData);
 			          bookSaleDataBean.setBookName(filename);
 			          bookSaleDataBean.setBookfilePath(imageDataString);
	 				}
	 			}catch(Exception e){
	 				  logger.error("Exception -->"+e.getMessage());
	 			}
		        return gson.toJson(bookSaleDataBean);
	 	 }	
	 
	 		@POST
	 	    @Path("/classTimeTableinsert")
	 	    @Produces(MediaType.APPLICATION_JSON)
	 	    public String classTimeTableinsert(String json)throws JSONException{
	 	      SimpleDateFormat sd=new SimpleDateFormat("hh:mm");
	 	      String status="";
	 	      String emailStatus="";
	 	      Gson gson=new Gson();
	 	      JsonParser jsonParser = new JsonParser();
	 	      JsonElement element = jsonParser.parse(json);
	 	      String personID=element.getAsJsonObject().get("personID").getAsString();
	 	      String schoolName=element.getAsJsonObject().get("schoolName").getAsString();
	 	      String schoolID=element.getAsJsonObject().get("schoolID").getAsString();
	 	      String clas=element.getAsJsonObject().get("tclass").getAsString();
	 	      String day=element.getAsJsonObject().get("tdays").getAsString();
	 	      JSONObject js=new JSONObject(json);
	 	    // DateFormat st=new SimpleDateFormat("hh:mm");
	 	      JSONArray jsonArray=js.getJSONArray("myTableArray");
	 	     // List<String>monthlist=null;
	 	     try {
	 	      
	 	      List<String> namelist = new ArrayList<String>();
	 	      for (int i = 0; i < jsonArray.length(); i++) {
	 	       namelist.add(jsonArray.getString(i));
	 	      }
	 	      String[] names=null;
	 	      for(int j=0;j<namelist.size();j++){
	 	       names=namelist.get(j).split(","); 
	 	       int c=0;
	 	       for (String name : names) { 
	 	        if(c!=4){
	 	         c++;
	 	        }else if(c==4){
	 	         if(!name.equalsIgnoreCase("]"))
	 	         { 
	 	          int v=0;
	 	          ClassTimeTableDataBean classTimeTableDataBean=new ClassTimeTableDataBean();
	 	          for(String value:names){
	 	           if(v==0){
	 	            v++;
	 	            classTimeTableDataBean.setSubject(value.replace("[", ""));
	 	           }else if(v==1){
	 	            classTimeTableDataBean.setSubjectCode(value);
	 	            v++;
	 	           }else if(v==2){
	 	            classTimeTableDataBean.setTeaID(value);
	 	            v++;
	 	           }else if(v==3){
	 	            classTimeTableDataBean.setStartTime(sd.parse(value));
	 	            v++;
	 	           }else if(v==4){
	 	            classTimeTableDataBean.setEndTime(sd.parse(value.replace("]", "")));
	 	           }
	 	          }
	 	          classtableList.add(classTimeTableDataBean);
	 	         }
	 	        }
	 	       }
	 	      } 
	 	      monthlist=new ArrayList<String>();
	 	       classTimeTableDataBean.setClasstimeList(classtableList);
	 	          classTimeTableDataBean.setClassname(clas);
	 	          classTimeTableDataBean.setDay(day);
	 	          classTimeTableDataBean.setMonthlist(Arrays.asList("January","February","March","April","May","June","July","August","September","October","November","December"));
	 	          if(personID!=null){
	 	           smsservice=(SmsService) SpringApplicationContext.getBean("smsservice");
	 	           smsservice.classTimeTable(personID, classTimeTableDataBean);
	 	           setCreateStatus(classTimeTableDataBean.getStatus());
	 	           if(createStatus.equalsIgnoreCase("Not")){
	 	            smsservice.saveClassTimeTable(classTimeTableDataBean, personID);
	 	            status="Success";
	 	                  if(status.equalsIgnoreCase("Success")){
	 	                     /* String pdfStatus = GeneratePdfMail.generatePdfClass(classTimeTableDataBean,personID);
	 	                      System.out.println("pdfstatus ----"+pdfStatus); 
	 	                      if(pdfStatus.equalsIgnoreCase("Success")){*/
	 	                       MailSendJDBC.classTimeTableInsert(classTimeTableDataBean,schoolName,schoolID);
	 	                       emailStatus="Success";
	 	                       System.out.println("succes----"+emailStatus); 
	 	               /*   }*/
	 	                 }
	 	           }
	 	           else{
	 	            emailStatus="Exist";
	 	           }
	 	              classTimeTableDataBean.setStatus(emailStatus);
	 	           }
	 	     }catch(Exception e){
	 	        logger.error("Exception -->"+e.getMessage());
	 	     }
	 	     return gson.toJson(classTimeTableDataBean.getStatus());
	 	    }
	 		
	 		@POST
	 		@Path("/getExamGrade")
	 		@Produces(MediaType.APPLICATION_JSON)
	 		public String getExamgrade(String json){
	 		List<TimeTableDataBean>	gradelist=null;
	 		List<ClassTimeTableDataBean> timetablelist=null;
	 		Gson gson=new Gson();
	 		JsonParser jsonParser=new JsonParser();
	 		 JsonElement element = jsonParser.parse(json);
	 		 String personID=element.getAsJsonObject().get("personID").getAsString();
	 		 String examclass=element.getAsJsonObject().get("classname").getAsString();
	 		 String examtitle=element.getAsJsonObject().get("examname").getAsString();
	 		 try{
	 			 timeTableDataBean.setExamClass(examclass);
	 			 timeTableDataBean.setExamTitle(examtitle);
	 			 if(personID!=null){
	 				gradelist=new ArrayList<TimeTableDataBean>();
	 				smsservice=(SmsService) SpringApplicationContext.getBean("smsservice");
	 				gradelist=smsservice.getexamMarksList(personID, timeTableDataBean);
	 				if(gradelist.size()>0){
	 				 timetablelist= new ArrayList<ClassTimeTableDataBean>();
	 					for (int i = 0; i < gradelist.size(); i++) {
							classTimeTableDataBean=new ClassTimeTableDataBean();
							classTimeTableDataBean.setClassname(gradelist.get(i).getExamFromMark());
							classTimeTableDataBean.setClassSection(gradelist.get(i).getExamToMark());
							classTimeTableDataBean.setSubject(gradelist.get(i).getExamGrade());
							classTimeTableDataBean.setClasstableid(Integer.parseInt(gradelist.get(i).getGradedetailId()));
							timetablelist.add(classTimeTableDataBean);	
						}
	 				}
	 			 }
	 		 }catch(Exception e){
	 			   logger.error("Exception -->"+e.getMessage());
	 		 }
	 		 		return gson.toJson(timetablelist);	
	 		}
	 		
	 		@POST
	 		@Path("/gradeUpdate")
	 		@Produces(MediaType.APPLICATION_JSON)
	 		public String gradeUpdate(String json){
	 			 String status="";
	 			 Gson gson=new Gson();
	 			 JsonParser jsonParser=new JsonParser();
		 		 JsonElement element = jsonParser.parse(json);
		 		 String personID=element.getAsJsonObject().get("personID").getAsString();
		 		 String primaryid=element.getAsJsonObject().get("primaryid").getAsString();
		 		 String frommark=element.getAsJsonObject().get("frommark").getAsString();
		 		 String tomark=element.getAsJsonObject().get("tomark").getAsString();
		 		 String grade=element.getAsJsonObject().get("grade").getAsString();
		 		 try{
		 			timeTableDataBean.setExamFromMark(frommark);
		 			timeTableDataBean.setExamToMark(tomark);
		 			timeTableDataBean.setExamGrade(grade);
		 			timeTableDataBean.setGradedetailId(primaryid);
		 			if(personID!=null){
		 				smsservice=(SmsService) SpringApplicationContext.getBean("smsservice");
		 				 status = smsservice.getexammarksupdate(personID, timeTableDataBean);
		 			}
		 		 }catch(Exception e){
		 			   logger.error("Exception -->"+e.getMessage());
		 		 }
	 			return gson.toJson(status);
	 		}
	 		
	 	// AddBook image upload
	 		@POST
	 		@Path("/addbookuploadimage")
	 		@Produces(MediaType.APPLICATION_JSON)
	 		public String addbookimage(String json) throws JSONException, IOException {
	 			logger.debug("inside image upload-----");
	 			String path = "";
	 			Gson gson = new Gson();
	 			JsonParser jsonParser = new JsonParser();
	 			JsonElement element = jsonParser.parse(json);
	 			String id = element.getAsJsonObject().get("srcData").getAsString();
	 			String filename = element.getAsJsonObject().get("filename")
	 					.getAsString();
	 			try {
	 				String base64Image = id.split(",")[1];
	 				byte[] decoded = Base64.decodeBase64(base64Image);
	 				File files = new File(paths.getString("sms.addbook.photo")
	 						+ format.format(now));
	 				if (!files.exists()) {
	 					files.mkdirs();
	 				} else {
	 					logger.debug("Alreday Found");
	 				}
	 				FileOutputStream fos = null;
	 				fos = new FileOutputStream(files
	 						+ paths.getString("path_context").toString() + filename
	 						+ ".jpg");
	 				fos.write(decoded);
	 				fos.close();
	 				path = format.format(now)
	 						+ paths.getString("path_context").toString() + filename
	 						+ ".jpg";
	 			} catch (Exception e) {
	 				  logger.error("Exception -->"+e.getMessage());
	 			}
	 			return gson.toJson(path);
	 		}
 		
	 	// AddBook image upload
	 		@POST
	 		@Path("/libraryaddbookuploadimage")
	 		@Produces(MediaType.APPLICATION_JSON)
	 		public String bookuploadimage(String json) throws JSONException,
	 				IOException {
	 			logger.debug("inside image upload-----");
	 			String path = "";
	 			Gson gson = new Gson();
	 			JsonParser jsonParser = new JsonParser();
	 			JsonElement element = jsonParser.parse(json);
	 			String id = element.getAsJsonObject().get("srcData").getAsString();
	 			String filename = element.getAsJsonObject().get("filename")
	 					.getAsString();
	 			try {
	 				String base64Image = id.split(",")[1];
	 				byte[] decoded = Base64.decodeBase64(base64Image);
	 				File files = new File(paths.getString("sms.library.photo")
	 						+ format.format(now));
	 				if (!files.exists()) {
	 					files.mkdirs();
	 				} else {
	 					logger.debug("Alreday Found");
	 				}
	 				FileOutputStream fos = null;
	 				fos = new FileOutputStream(files
	 						+ paths.getString("path_context").toString() + filename
	 						+ ".jpg");
	 				fos.write(decoded);
	 				fos.close();
	 				path = format.format(now)
	 						+ paths.getString("path_context").toString() + filename
	 						+ ".jpg";
	 			} catch (Exception e) {
	 				  logger.error("Exception -->"+e.getMessage());
	 			}
	 			return gson.toJson(path);
	 		}

	 		
	 		//booksalesviewdelete
			@POST
			@Path("/booksalesviewdelete")
			@Produces(MediaType.APPLICATION_JSON)
			public String booksalesviewdelete(String json) throws JSONException {
				logger.debug("inside---");
				Gson gson = new Gson();
				JsonParser jsonParser = new JsonParser();
				JsonElement element = jsonParser.parse(json);
				logger.debug("jsonjson"+json);
				String personID = element.getAsJsonObject().get("personID").getAsString();
				logger.debug("person id"+personID);
				String ordernumber = element.getAsJsonObject().get("order").getAsString();
				logger.debug("order---"+ordernumber);
				bookSaleDataBean.setOrderNumber(ordernumber);
				String status="fail";
				try {
					if (personID != null && bookSaleDataBean.getOrderNumber() != null){
						smsservice = (SmsService) SpringApplicationContext
								.getBean("smsservice");
						status = smsservice.deletebooksale(personID, bookSaleDataBean);
						logger.debug(status);
					}
					logger.debug("delete method calling");
				} catch (Exception e) {
					  logger.error("Exception -->"+e.getMessage());
				}
				return gson.toJson(status);
			}
			
			//book image upload
 		 	@POST
		 	@Path("/bookuploadimage")
	 		@Produces(MediaType.APPLICATION_JSON)
		 	 public String booklistuploadimage(String json)throws JSONException, IOException{
	 	 		System.out.println("inside image upload-----");
	 	 		Gson gson=new Gson();
	 	 		JsonParser jsonParser = new JsonParser();
	 			JsonElement element = jsonParser.parse(json);
	 			String id=element.getAsJsonObject().get("srcData").getAsString();
	 			String filename=element.getAsJsonObject().get("filename").getAsString();
	 			try{
	 			String base64Image = id.split(",")[1];
	 			byte[] decoded = Base64.decodeBase64(base64Image);
	 			File files = new File(paths.getString("sms.addbook.photo") +format.format(now));
				if (!files.exists()) {
					files.mkdirs();
				} else {
					logger.debug("Alreday Found");
				}
	 			FileOutputStream fos = null;
	 			fos = new FileOutputStream(files+paths.getString("path_context").toString()+filename+".jpg");
	 			fos.write(decoded);
	 			fos.close();
	 			String path = format.format(now) + paths.getString("path_context").toString() + filename + ".jpg";
	 			bookSaleDataBean.setBookfilePath(path);
	 			}catch(Exception e){
	 				  logger.error("Exception -->"+e.getMessage());
	 			}
		        return gson.toJson(bookSaleDataBean.getBookfilePath());
	 	 }
			
			//booksalesviewupdate
			@POST
			@Path("/booksalesviewupdate")
			@Produces(MediaType.APPLICATION_JSON)
			public String booksalesviewupdate(String json) throws JSONException {
				logger.debug("inside---");
				Gson gson = new Gson();
				List<BookSaleDataBean> booksaleList = null;
				JsonParser jsonParser = new JsonParser();
				JsonElement element = jsonParser.parse(json);
				logger.debug("jsonjson"+json);
				String personID = element.getAsJsonObject().get("personID").getAsString();
				logger.debug("person id"+personID);
				String ordernumber = element.getAsJsonObject().get("order").getAsString();
				logger.debug("order---"+ordernumber);
				String studtid = element.getAsJsonObject().get("stuuu").getAsString();
				String classec = element.getAsJsonObject().get("classec").getAsString();
				String bookprice = element.getAsJsonObject().get("bookprice").getAsString();
				logger.debug("bookprice---"+bookprice);
				bookSaleDataBean.setOrderNumber(ordernumber);
				JSONObject js = new JSONObject(json);
				JSONArray orderlist = js.getJSONArray("orderlist");
				String status="fail";
				try {
					List<String> namelist = new ArrayList<String>();
					for (int i = 0; i < orderlist.length(); i++) {
						String studentIDs = (String)orderlist.get(i);
						namelist.add(studentIDs);
					}
					String[] bookNames = null;
					booksaleList = new ArrayList<BookSaleDataBean>();
					for (int j = 0; j < namelist.size(); j++) {
						bookNames = namelist.get(j).split(",");
						int c = 0;
						for (String names : bookNames) {
							logger.debug("books values " + names + " " + c);
							if (c != 5) {
								c++;
							} else if (c == 5) {
								if (!names.equalsIgnoreCase("]")) {
									int v = 0;
									BookSaleDataBean bookSaleDataBeans = new BookSaleDataBean();
									for (String name : bookNames) {
										if (v == 0)
											v++;
										else if (v == 1) {
											bookSaleDataBeans.setBookName(name);
											v++;
										} else if (v == 2) {
											bookSaleDataBeans.setBookPrice(name);
											v++;
										} else if (v == 3) {
											bookSaleDataBeans.setBookQuantity(name);
											v++;
										} else if (v == 4) {
											bookSaleDataBeans.setOrder(name);
											v++;
										} else if (v == 5) {
											bookSaleDataBeans.setNetAmount(name
													.replace("]", ""));
											v++;
										}
									}
									booksaleList.add(bookSaleDataBeans);
								}
							}
						}
					}
					bookSaleDataBean.setStudID(studtid);
					bookSaleDataBean.setClassname(classec);
					bookSaleDataBean.setTotal(bookprice);
					bookSaleDataBean.setBookinfolist(booksaleList);
					if (personID != null) {
						smsservice = (SmsService) SpringApplicationContext
								.getBean("smsservice");
						status = smsservice.bookOrderUpdate(personID, bookSaleDataBean);
						
						logger.debug(status);
					}
					logger.debug("delete method calling");
				} catch (Exception e) {
					  logger.error("Exception -->"+e.getMessage());
				}
				return gson.toJson(status);
			}
			
			//booksalesviewedit
			@POST
			@Path("/booksalesviewedit")
			@Produces(MediaType.APPLICATION_JSON)
			public String booksalesviewedit(String json) throws JSONException {
				Gson gson = new Gson();
				List<BookSaleDataBean> status = null;
				BookSaleDataBean bookSaleDataBean = new BookSaleDataBean();
				JsonParser jsonParser = new JsonParser();
				JsonElement element = jsonParser.parse(json);
				String personID = element.getAsJsonObject().get("personId").getAsString();
				String ordernumber = element.getAsJsonObject().get("ordernumberrr").getAsString();
				bookSaleDataBean.setOrderNumber(ordernumber);
				try {
					if (personID != null
							&& bookSaleDataBean.getOrderNumber() != null) {
						smsservice = (SmsService) SpringApplicationContext
								.getBean("smsservice");
						status = smsservice.getBookView(personID, bookSaleDataBean);
						logger.debug(status);
					}
					logger.debug("delete method calling");
				} catch (Exception e) {
					  logger.error("Exception -->"+e.getMessage());
				}
				return gson.toJson(status);
			}
			
			//booksalesview
			@POST
			@Path("/booksalesview")
			@Produces(MediaType.APPLICATION_JSON)
			public String booksalesview(String json) throws JSONException {
				Gson gson = new Gson();
				List<BookSaleDataBean> status = null;
				JsonParser jsonParser = new JsonParser();
				JsonElement element = jsonParser.parse(json);
				String personID = element.getAsJsonObject().get("personid").getAsString();
				String ordernumber = element.getAsJsonObject().get("ordernumber").getAsString();
				String studentid = element.getAsJsonObject().get("studID").getAsString();
				String classna = element.getAsJsonObject().get("classna").getAsString();
				bookSaleDataBean.setOrderNumber(ordernumber);
				try {
					if (personID != null
							&& bookSaleDataBean.getOrderNumber() != null) {
						smsservice = (SmsService) SpringApplicationContext
								.getBean("smsservice");
						status = smsservice.getBookView(personID, bookSaleDataBean);
						logger.debug(status);
					}
					logger.debug("delete method calling");
				} catch (Exception e) {
					  logger.error("Exception -->"+e.getMessage());
				}
				return gson.toJson(status);
			}
			@GET
	        @Path("/getstudentlist/{id}")
	        @Produces(MediaType.APPLICATION_JSON)
	        public String getParStudentIDList(@PathParam("id") String id) throws JSONException
	        {
	         Gson gson=new Gson();
	         String personID = id;
	         List<String> studentIDList=new ArrayList<String>();
	         try {
	          if (personID != null) {
	           smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	           studentIDList = smsservice.getstudentList(personID, timeTableDataBean);
	           Collections.sort(studentIDList);
	           logger.debug("studentIDList -- "+studentIDList.size());
	          }

	         } catch (Exception e) {
	          logger.warn("Exception -->"+e.getMessage());
	         }
	         return gson.toJson(studentIDList);
	        }
			/* Class TimeTable View Teacher*/
			@GET
			@Path("/classtimeTableViewTeacher/{id}/{dayy}")
			@Produces(MediaType.APPLICATION_JSON)
			public String getclasstimeTableViewTeacher(@PathParam("id") String id,@PathParam("dayy") String dayy)throws JSONException {
				System.out.println("inside getclasstimetableview method calling");
				Gson gson = new Gson();
				String personID = id;
				String day = dayy;
				System.out.println("day---"+day);
				List<ClassTimeTableDataBean> classList = new ArrayList<ClassTimeTableDataBean>();
				try {
					classTimeTableDataBean.setDay(day);
					smsservice = (SmsService) SpringApplicationContext
							.getBean("smsservice");
					if (personID != null) {
							smsservice.teacherTimeTable(personID,classTimeTableDataBean );
							classList.addAll(classTimeTableDataBean.getClasstimeList());
							System.out.println("classtimetable list size" + classList.size());
					}
				} catch (Exception e) {
					  logger.error("Exception -->"+e.getMessage());
				}
				return gson.toJson(classList);
			}
			/*Borrower book insert studentlist*/
		     @GET
		     @Path("/borrowerbookinsertstudentparent/{id}/{classname}/{classSection}")
		     @Produces(MediaType.APPLICATION_JSON)
		     public String getinsertstudentidparent(@PathParam("id") String id,@PathParam("classname") String classname,@PathParam("classSection") String classSection) throws JSONException
		     {
		      logger.debug("inside getstudent id method calling");
		      Gson gson = new Gson();
		      String personID=id;
		      List<String> studentIDList=null;
		      String name= classname + "/" + classSection;
		      try{
		       smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
		       if(personID != null && name != null){
		        studentIDList=new ArrayList<String>();
		        studentIDList = smsservice.getStudentIDList1(name,personID );
		        System.out.println(studentIDList);
		       }
		      }
		      catch(Exception e){
		       logger.warn(" exception - "+e);
		      }
		      return gson.toJson(studentIDList);
		     }
		     
		     @POST
		     @Path("/getSubjects")
		     @Produces(MediaType.APPLICATION_JSON)
		     public String getSubjects(String json)throws JSONException{
		    	 System.out.println("json array-------"+json);
		    	 List<String> subjectList=null;
		    	 Gson gson=new Gson();
		    	 JsonParser jsonParser = new JsonParser();
		    	 JsonElement element = jsonParser.parse(json);
		    	 try{
		    		 subjectList=new ArrayList<String>();
		    		// JSONObject js=new JSONObject(json);
		 	    	 String personID=element.getAsJsonObject().get("personID").getAsString();
		 	    	 smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
		 	    	subjectList=smsservice.getSubjectList(personID);		 	    	
		    	 }catch(Exception e){
		    		   logger.error("Exception -->"+e.getMessage());
		    	 }
		    	 return gson.toJson(subjectList); 
		     }
		   
		     @GET
		     @Path("/getStudentPerInfo2/{id}")
		     @Produces(MediaType.APPLICATION_JSON)
		     public String getStudentPerInfo2(@PathParam("id") String id) throws JSONException
		     {
		     logger.debug("inside getStudentPerInfo2");
		      Gson gson=null;// Gson();
		      //String status="";
		      String personID=id;
		      List<StudentPerformanceDataBean> studentList = null;
		      try
		      {
		    	  gson=new Gson();
		       
		       if (personID != null) {
		        smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
		        studentList = new ArrayList<StudentPerformanceDataBean>();
		        studentList = smsservice.getStudentPerInfo(personID, studentPerformanceDataBean);
		        logger.debug("studentList --- "+studentList.size());
		       }
		      }
		      catch(Exception e)
		      {
		         logger.error("Exception -->"+e.getMessage());
		      }
		      return gson.toJson(studentList);
		     }
		     @POST
			 @Path("/classTableinsert")
			 @Produces(MediaType.APPLICATION_JSON)
			 public String classTableinsert(String json) throws JSONException{
		    	 System.out.println("Inside class table list");
			  String status=" ";
			 // Gson gson=null;//new Gson();
			  JsonParser jsonParser = null;//new JsonParser();
			  JsonElement element = null;// jsonParser.parse(json);
			  String personID=null;//element.getAsJsonObject().get("personID").getAsString();
			  String schoolID=null;//element.getAsJsonObject().get("schoolID").getAsString();
			  JSONObject js=null;//new JSONObject(json);
			  JSONArray jsonArray=null;//js.getJSONArray("myTableArray");
			  System.out.println("length====="+jsonArray);
			   try{
					  // gson=new Gson();
				   jsonParser = new JsonParser();
				   element = jsonParser.parse(json);
				   personID=element.getAsJsonObject().get("personID").getAsString();
				   schoolID=element.getAsJsonObject().get("schoolID").getAsString();
				   js=new JSONObject(json);
				   jsonArray=js.getJSONArray("myTableArray");
				   List<String> namelist = new ArrayList<String>();
				   System.out.println("length====="+jsonArray.length());
				   for(int i=0;i<jsonArray.length();i++){ 
					   namelist.add(jsonArray.getString(i));
					   System.out.println("array list----"+jsonArray.getString(i)); 
				   }
				    String[] names=null; 
				    for(int m=0;m<namelist.size();m++){
				    	  names=namelist.get(m).split(","); 
				    	   System.out.println("names"+names[0]);
				       int c=0;
				       for (String name : names) { 
				        if(c!=1){
				         c++;
				        }else if(c==1){
				         if(!name.equalsIgnoreCase("]"))
				         { 
				          int v=0;
				              LibrarianDataBean librarianDataBean=new LibrarianDataBean();
				          for(String value:names){
				           if(v==0){
				            v++;
				            librarianDataBean.setClassname(value.replace("[", ""));
				           }else if(v==1){
				        	   librarianDataBean.setClasssection(value.replace("]", ""));
				        	   System.out.println("class section----"+value.replace("]", "")); 
				          }
				         }
				          classsectablist.add(librarianDataBean);
				        }
				       }
				      }
				      }
					smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
					for (int i = 0; i < classsectablist.size(); i++) {
						librarianDataBean.setClassname(classsectablist.get(i).getClassname());
						librarianDataBean.setClasssection(classsectablist.get(i).getClasssection());
						status=smsservice.insertclass(personID,schoolID,librarianDataBean);
						if(status.equalsIgnoreCase("Success")){
							librarianDataBean.setStatus(status);
							System.out.println("status---"+status); 
						}
					}
					
			     }catch(Exception e){
			        logger.error("Exception -->"+e.getMessage());
			     }
			     return librarianDataBean.getStatus();
			 }
		     @GET
				@Path("/classsectionView/{id}")
				@Produces(MediaType.APPLICATION_JSON)
				public String classsectionView(@PathParam("id") String id){
			      String personID=id;
			 		List<LibrarianDataBean> librarianList = null;
					Gson gson=new Gson();
					try{
						librarianList = new ArrayList<LibrarianDataBean>();
						if (id != null) {
							 smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
								librarianList=smsservice.classlistview(personID,librarianDataBean);
						}
					}
					catch(Exception e){
						  logger.error("Exception -->"+e.getMessage());
					}
					return gson.toJson(librarianList);
				}
		     	@POST
				@Path("/classsectionEdit")
				@Produces(MediaType.APPLICATION_JSON)		
				public String classsectionEdit(String json) throws JSONException{
					Gson gson=new Gson();
					JsonParser jsonParser = new JsonParser();
					JsonElement element = jsonParser.parse(json);
					String personID=element.getAsJsonObject().get("personID").getAsString();
					String classname=element.getAsJsonObject().get("classname").getAsString();
					String classsection=element.getAsJsonObject().get("classsection").getAsString();
					String classid =element.getAsJsonObject().get("classid").getAsString();
					String status="";
					try{
						if (personID != null) {
							 smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
							librarianDataBean.setClassname(classname);
							librarianDataBean.setClasssection(classsection);
							librarianDataBean.setClassid(classid);
							status = smsservice.editclasssec(personID,librarianDataBean);
						}
					}
					catch(Exception e){
						  logger.error("Exception -->"+e.getMessage());
					}
					return gson.toJson(status);
				}
				
				@POST
				@Path("/classsectionUpdate")
				@Produces(MediaType.APPLICATION_JSON)		
				public String classsectionUpdate(String json) throws JSONException{
					logger.info("--- Inside classsectionUpdate method ---");
					Gson gson=null;
					JsonParser jsonParser = null;
					JsonElement element = null;
					String personID=null;
					String classname=null;
					String classsection=null;
					//List<LibrarianDataBean> librarianList = null;
					try{
						logger.info("--- Inside classsectionUpdate try block ---");
						gson=new Gson();
						jsonParser = new JsonParser();
						element = jsonParser.parse(json);
						personID=element.getAsJsonObject().get("personID").getAsString();
						classname=element.getAsJsonObject().get("classname").getAsString();
						classsection=element.getAsJsonObject().get("classsection").getAsString();					
						
						if (personID != null) {
							 librarianDataBean.setClassname(classname);
							 librarianDataBean.setClasssection(classsection);
							 logger.info("--- Before calling  classlistview method ---");
							 smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
								 //librarianList=smsservice.classlistview(personID,librarianDataBean);
								 smsservice.classlistview(personID,librarianDataBean);
								 logger.info("--- Successfully called classlistview method ---");
								 librarianDataBean.setClassname("");librarianDataBean.setClasssection("");
								 librarianDataBean.setReturnStatus("Success");
						}
						return gson.toJson(librarianDataBean.getReturnStatus());
					}
					catch(Exception e){
						  logger.error("Exception -->"+e.getMessage());
					}
					finally
					{
						 personID=null;
						 classname=null;
						 classsection=null;
						 jsonParser = null;
						 element = null;
					}
					return gson.toJson(librarianDataBean.getReturnStatus());
				}
				
				@POST
				@Path("/classsectiondelete")
				@Produces(MediaType.APPLICATION_JSON)		
				public String classsectiondelete(String json) throws JSONException{
					System.out.println("Inside class section delete");
					Gson gson=new Gson();
					JsonParser jsonParser = new JsonParser();
					JsonElement element = jsonParser.parse(json);
					String personID=element.getAsJsonObject().get("personID").getAsString();
					String classname=element.getAsJsonObject().get("classname").getAsString();
					String classsection=element.getAsJsonObject().get("classsection").getAsString();
					String classid =element.getAsJsonObject().get("classid").getAsString();
					String status="";
					try{
						if (personID != null) {
							librarianDataBean.setClassname(classname);
							librarianDataBean.setClasssection(classsection);
							librarianDataBean.setClassid(classid); 
							smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
							status = smsservice.deleteclasssec(personID,librarianDataBean);
							//System.out.println("status---"+status); 
						}
					}
					catch(Exception e){
						  logger.error("Exception -->"+e.getMessage());
					}
					return gson.toJson(status);
				}
				 @POST
				 @Path("/subjectTableinsert")
				 @Produces(MediaType.APPLICATION_JSON)
				 public String subjectTableinsert(String json) throws JSONException{
				  String status=" ";
				  //Gson gson=new Gson();
				  JsonParser jsonParser = new JsonParser();
				  JsonElement element = jsonParser.parse(json);
				  String personID=element.getAsJsonObject().get("personID").getAsString();
				  String schoolID=element.getAsJsonObject().get("schoolID").getAsString();
				  JSONObject js=new JSONObject(json);
				  JSONArray jsonArray=js.getJSONArray("myTableArray");
				   try{
					   List<String> subjectlist = new ArrayList<String>();
					   for(int i=0;i<jsonArray.length();i++){
						   subjectlist.add(jsonArray.getString(i));
					   }
					    String[] subject=null; 
					    for(int m=0;m<subjectlist.size();m++){
					    	subject=subjectlist.get(m).split(","); 
					       int c=0;
					       for (String name : subject) { 
					        if(c!=2){
					         c++;
					        }else if(c==2){
					         if(!name.equalsIgnoreCase("]"))
					         { 
					          int v=0;
					              LibrarianDataBean librarianDataBean=new LibrarianDataBean();
					          for(String value:subject){
					           if(v==0){
					            v++;
					            librarianDataBean.setName(value.replace("[", ""));
					           }else if(v==1){
					        	   v++;
					        	   librarianDataBean.setSubjectname(value);
					           }else if(v==2){
					        	   librarianDataBean.setSubjectcode(value.replace("]", ""));
					          }
					         }
					          classsectablist.add(librarianDataBean);
					        }
					       }
					      }
					      }
						smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
						for (int i = 0; i < classsectablist.size(); i++) {
							librarianDataBean.setName(classsectablist.get(i).getName());
							librarianDataBean.setSubjectname(classsectablist.get(i).getSubjectname());
							librarianDataBean.setSubjectcode(classsectablist.get(i).getSubjectcode()); 
							 status=smsservice.insertsubject(personID,schoolID,librarianDataBean);
							if(status.equalsIgnoreCase("Success")){
								librarianDataBean.setStatus(status);
								System.out.println("status---"+status); 
							}
						}
						
						
				     }catch(Exception e){
				        logger.error("Exception -->"+e.getMessage());
				        logger.error("Exception -->"+e.getMessage());
				     }
				     return librarianDataBean.getStatus();
				 }
				 @POST
				 	@Path("/subjectView/{id}/{class}/{sec}")
				 	@Produces(MediaType.APPLICATION_JSON)
				 	public String subjectView(@PathParam("id") String id,@PathParam("class") String pclass,@PathParam("sec") String sec) throws JSONException
				 	{
				 		Gson gson = new Gson();
				 		List<LibrarianDataBean> classsectablist = null;
				 		LibrarianDataBean librarianDataBean=new LibrarianDataBean();
				 		String personID=id;
				 		librarianDataBean.setClassname(pclass+"/"+sec);
				 		try
				 		{
				 			smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
				 			classsectablist=smsservice.subjectlistview(personID,librarianDataBean);
				 			System.out.println("subjectTableList -- "+classsectablist.size());
				 		}
				 		catch(Exception e)
				 		{
				 			logger.warn(" exception - "+e);
				 		}
				 		return gson.toJson(classsectablist);
				 	}
				 @POST
					@Path("/classsubjectdelete")
					@Produces(MediaType.APPLICATION_JSON)		
					public String classsubjectdelete(String json) throws JSONException{
						System.out.println("Inside class subject delete");
						Gson gson=new Gson();
						JsonParser jsonParser = new JsonParser();
						JsonElement element = jsonParser.parse(json);
						String personID=element.getAsJsonObject().get("personID").getAsString();
						String subjectname=element.getAsJsonObject().get("subjectname").getAsString();
						String subjectcode=element.getAsJsonObject().get("subjectcode").getAsString();
						String subid =element.getAsJsonObject().get("subid").getAsString();
						String status="";
						try{
							if (personID != null) {
								librarianDataBean.setSubjectname(subjectname);
								librarianDataBean.setSubjectcode(subjectcode); 
								librarianDataBean.setSubid(subid); 
								smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
								status = smsservice.deletesubname(personID,librarianDataBean);
								System.out.println("status---"+status); 
							}
						}
						catch(Exception e){
							  logger.error("Exception -->"+e.getMessage());
						}
						return gson.toJson(status);
					}
				/*@GET
	 		   	@Path("/subjectView/{id}")
	 		   	@Produces(MediaType.APPLICATION_JSON)
	 		   	public String subjectView(@PathParam("id") String id) throws JSONException {
	 		   		logger.debug("inside subject list");
	 		   		Gson gson = new Gson();
	 		   		String personID = id;
	 		   		List<LibrarianDataBean>subjectLists=new ArrayList<LibrarianDataBean>();
	 		   		try {
	 		   			if (personID != null) {
	 		   				smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
	 		   				subjectLists=smsservice.subjectlistview(personID,librarianDataBean);
	 		   			}
	 		   		} catch (Exception e) {
	 		   			logger.warn("inside exception", e);
	 		   		}
	 		   		return gson.toJson(subjectLists);
	 		   	}*/
		     public String userMenus(String userName){
		    	 List<UserProduct> mainMenuList=null;
		    	 List<SupProduct> subMenuList=null;
		    	 String mainmenu="";
		    	 String submenu="";
		    	 try{
		    		 smsservice = (SmsService) SpringApplicationContext.getBean("smsservice");
		    		 loginAccess.setUsername(userName);
		    		 mainMenuList=new ArrayList<UserProduct>();
		    		 subMenuList=new ArrayList<SupProduct>();
		    		 loginAccess.setMainMeuList(new ArrayList<String>());
		    		 loginAccess.setSupMenuList(new ArrayList<String>());
		    		 mainMenuList=smsservice.LoadMenu(loginAccess);
		    		 if(mainMenuList.size()>0){
		    			 for (int i = 0; i < mainMenuList.size(); i++) {
		    				 submenu=submenu+mainMenuList.get(i).getProduct().getProductName()+":";
		    				 loginAccess.getMainMeuList().add(mainMenuList.get(i).getProduct().getProductName());
		    				 subMenuList=smsservice.loadSubMenu(mainMenuList.get(i).getProduct().getProduct_ID(),mainMenuList.get(i).getProduct().getProductCode());
		    				 if(subMenuList.size()>0){
		    					 for (int j = 0; j < subMenuList.size(); j++) {
		    						 submenu=submenu+subMenuList.get(j).getProductName()+",";
		    						 loginAccess.getSupMenuList().add(subMenuList.get(j).getProductName());
								 }
		    					 submenu=submenu+";";
		    				 
		    				 } 
						}
		    		
		    		 }
		    		 for (int i = 0; i < loginAccess.getMainMeuList().size(); i++) {
		    			 mainmenu=loginAccess.getMainMeuList().get(i);
		    			 mainmenu=mainmenu+";";
					}
		    		
		    	 }catch(Exception e){
		    		   logger.error("Exception -->"+e.getMessage());
		    	 }
				return "";
		    	 
		     }
}
