package com.stumosys.managedBean;

import java.awt.Color;
import java.awt.image.BufferedImage;
//import java.io.DataInputStream;
import java.io.File;
//import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
//import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
//import java.util.Formatter;
import java.util.HashMap;
//import java.util.LinkedList;
import java.util.List;
import java.util.Map;
//import java.util.Properties;
import java.util.ResourceBundle;

/*import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
*/import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.imageio.ImageIO;
/*import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
*/import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
//import org.neo4j.cypher.internal.compiler.v2_1.perty.docbuilders.simpleDocBuilder;
import org.primefaces.component.themeswitcher.ThemeSwitcher;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.stumosys.controler.SmsController;
import com.stumosys.domain.AttendanceDataBean;
import com.stumosys.domain.ClassTimeTableDataBean;
import com.stumosys.domain.HomeworkDatabean;
import com.stumosys.domain.LibrarianDataBean;
import com.stumosys.domain.LoginAccess;
import com.stumosys.domain.NoticeBoardDataBean;
import com.stumosys.domain.ParentsDataBean;
import com.stumosys.domain.StaffDataBean;
import com.stumosys.domain.StudentDataBean;
import com.stumosys.domain.TeacherDataBean;
//import com.stumosys.shared.BackofficeLogin;
import com.stumosys.shared.ExamTable;
import com.stumosys.shared.School;
import com.stumosys.shared.SupProduct;
import com.stumosys.shared.TimeTable;
import com.stumosys.shared.UserProduct;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;
import com.stumosys.util.Util;

/**
 * Date Author Changes 28-01-2016
 * Alex
 * DB
 * 
 */

@ManagedBean(name = "loginMB")
@RequestScoped
public class LoginMB implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2465660022771873410L;

	final static Logger logger = Logger.getLogger(LoginMB.class);

	@ManagedProperty(value = "#{studentViewMB}")
	StudentViewMB studentViewMB;
	@ManagedProperty(value = "#{parentsViewMB}")
	ParentsViewMB parentsViewMB;
	@ManagedProperty(value = "#{teacherViewMB}")
	TeacherViewMB teacherViewMB;
	@ManagedProperty(value = "#{staffMB}")
	StaffMB staffMB;
	@ManagedProperty(value = "#{librarianViewMB}")
	LibrarianViewMB librarianViewMB;
	@ManagedProperty(value ="#{feesPaymentViewMB}")
	FeesPaymentViewMB feesPaymentViewMB;
	private String logos;
	private PieChartModel pieModel;
	
   
    
	public PieChartModel getPieModel() {
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}
	public String getLogos() {
		return logos;
	}

	public void setLogos(String logos) {
		this.logos = logos;
	}

	public LoginAccess loginaccess = new LoginAccess();
	StudentDataBean studentDataBean = new StudentDataBean();
	ParentsDataBean parentsDataBean = new ParentsDataBean();
	TeacherDataBean teacherDataBean = new TeacherDataBean();
	StaffDataBean staffDataBean = new StaffDataBean();
	LibrarianDataBean librarianDataBean = new LibrarianDataBean();
	AttendanceDataBean attendanceDataBean=new AttendanceDataBean();
	private String invusername;
	private String invpassword;
	private String rollname;
	private String login_username;
	private boolean flag = true;
	private boolean studentListflag = false;
	private String orientation = "horizontal";
	private MenuModel model;
	private List<UserProduct> menuList;
	private List<SupProduct> submenuList;
	private ScheduleModel eventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();
	private ScheduleModel lazyEventModel;
	private boolean scheduleFlag = false;
	private boolean scheduleFlag1 = false;
	private List<TimeTable> timetableList = null;
	private List<ExamTable> examList = null;
	private List<NoticeBoardDataBean> noticeList = null;
	private List<HomeworkDatabean> homeworkList=null;
	private List<ClassTimeTableDataBean> classtimetablelist = null;
	private List<School> schoollist = null;
	private boolean flag1 = false;
	public boolean exflag = false;
	public boolean exflag1 = false;
	private List<LoginAccess> addschoollist=null;
	private boolean notificationflag=false;
	private List<LoginAccess> themes;
	private String themename= "bootstrap";
	private String background="#1578c9";
	UploadedFile file;
	private List<School> schooleditlist=null;
	private List<String> studentList=new ArrayList<String>(); 
	private boolean homebtnFlag = false;
	private boolean timeflag=false;
	private List<LoginAccess> searchlist;
	private List<LoginAccess> filtersearchlist;
	private String searchitem;
	private List<LoginAccess> userDetailList=null;
	Date todaydate = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	private String numberofUser;
	

	public String getNumberofUser() {
		return numberofUser;
	}

	public void setNumberofUser(String numberofUser) {
		this.numberofUser = numberofUser;
	}

	public List<LoginAccess> getUserDetailList() {
		return userDetailList;
	}

	public void setUserDetailList(List<LoginAccess> userDetailList) {
		this.userDetailList = userDetailList;
	}

	public List<LoginAccess> getFiltersearchlist() {
		return filtersearchlist;
	}

	public void setFiltersearchlist(List<LoginAccess> filtersearchlist) {
		this.filtersearchlist = filtersearchlist;
	}

	public String getSearchitem() {
		return searchitem;
	}

	public void setSearchitem(String searchitem) {
		this.searchitem = searchitem;
	}

	public List<LoginAccess> getSearchlist() {
		return searchlist;
	}

	public void setSearchlist(List<LoginAccess> searchlist) {
		this.searchlist = searchlist;
	}

	public boolean isTimeflag() {
		return timeflag;
	}

	public void setTimeflag(boolean timeflag) {
		this.timeflag = timeflag;
	}

	public boolean isHomebtnFlag() {
		return homebtnFlag;
	}

	public void setHomebtnFlag(boolean homebtnFlag) {
		this.homebtnFlag = homebtnFlag;
	}
	public boolean isStudentListflag() {
		return studentListflag;
	}

	public void setStudentListflag(boolean studentListflag) {
		this.studentListflag = studentListflag;
	}

	public List<String> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<String> studentList) {
		this.studentList = studentList;
	}

	public List<School> getSchooleditlist() {
		return schooleditlist;
	}

	public void setSchooleditlist(List<School> schooleditlist) {
		this.schooleditlist = schooleditlist;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public List<LoginAccess> getThemes() {
		return themes;
	}

	public void setThemes(List<LoginAccess> themes) {
		this.themes = themes;
	}

	public String getThemename() {
	return themename;
	}

	public void setThemename(String themename) {
		
	this.themename = themename;
	}

	
	public boolean isNotificationflag() {
		return notificationflag;
	}

	public List<LoginAccess> getAddschoollist() {
		return addschoollist;
	}

	public void setAddschoollist(List<LoginAccess> addschoollist) {
		this.addschoollist = addschoollist;
	}

	public void setNotificationflag(boolean notificationflag) {
		this.notificationflag = notificationflag;
	}

	public FeesPaymentViewMB getFeesPaymentViewMB() {
		return feesPaymentViewMB;
	}

	public void setFeesPaymentViewMB(FeesPaymentViewMB feesPaymentViewMB) {
		this.feesPaymentViewMB = feesPaymentViewMB;
	}

	public List<HomeworkDatabean> getHomeworkList() {
		return homeworkList;
	}

	public void setHomeworkList(List<HomeworkDatabean> homeworkList) {
		this.homeworkList = homeworkList;
	}

	public boolean isExflag() {
		return exflag;
	}

	public void setExflag(boolean exflag) {
		this.exflag = exflag;
	}

	public boolean isExflag1() {
		return exflag1;
	}

	public void setExflag1(boolean exflag1) {
		this.exflag1 = exflag1;
	}

	public StaffMB getStaffMB() {
		return staffMB;
	}

	public void setStaffMB(StaffMB staffMB) {
		this.staffMB = staffMB;
	}

	public LibrarianViewMB getLibrarianViewMB() {
		return librarianViewMB;
	}

	public void setLibrarianViewMB(LibrarianViewMB librarianViewMB) {
		this.librarianViewMB = librarianViewMB;
	}

	public StaffDataBean getStaffDataBean() {
		return staffDataBean;
	}

	public void setStaffDataBean(StaffDataBean staffDataBean) {
		this.staffDataBean = staffDataBean;
	}

	public LibrarianDataBean getLibrarianDataBean() {
		return librarianDataBean;
	}

	public void setLibrarianDataBean(LibrarianDataBean librarianDataBean) {
		this.librarianDataBean = librarianDataBean;
	}

	public boolean isFlag1() {
		return flag1;
	}

	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}

	public List<ClassTimeTableDataBean> getClasstimetablelist() {
		return classtimetablelist;
	}

	public void setClasstimetablelist(List<ClassTimeTableDataBean> classtimetablelist) {
		this.classtimetablelist = classtimetablelist;
	}

	/**
	 * @return the schoollist
	 */
	public List<School> getSchoollist() {
		return schoollist;
	}

	/**
	 * @param schoollist
	 *            the schoollist to set
	 */
	public void setSchoollist(List<School> schoollist) {
		this.schoollist = schoollist;
	}

	/**
	 * @return the timetableList
	 */
	public List<TimeTable> getTimetableList() {
		return timetableList;
	}

	/**
	 * @param timetableList
	 *            the timetableList to set
	 */
	public void setTimetableList(List<TimeTable> timetableList) {
		this.timetableList = timetableList;
	}

	/**
	 * @return the scheduleFlag
	 */
	public boolean isScheduleFlag() {
		return scheduleFlag;
	}

	/**
	 * @param scheduleFlag
	 *            the scheduleFlag to set
	 */
	public void setScheduleFlag(boolean scheduleFlag) {
		this.scheduleFlag = scheduleFlag;
	}

	/**
	 * @return the scheduleFlag1
	 */
	public boolean isScheduleFlag1() {
		return scheduleFlag1;
	}

	/**
	 * @param scheduleFlag1
	 *            the scheduleFlag1 to set
	 */
	public void setScheduleFlag1(boolean scheduleFlag1) {
		this.scheduleFlag1 = scheduleFlag1;
	}

	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	ResourceBundle database = context.getApplication().evaluateExpressionGet(context, "#{database}", ResourceBundle.class);
	/**
	 * @return the studentViewMB
	 */
	public StudentViewMB getStudentViewMB() {
		return studentViewMB;
	}

	/**
	 * @param studentViewMB
	 *            the studentViewMB to set
	 */
	public void setStudentViewMB(StudentViewMB studentViewMB) {
		this.studentViewMB = studentViewMB;
	}

	/**
	 * @return the parentsViewMB
	 */
	public ParentsViewMB getParentsViewMB() {
		return parentsViewMB;
	}

	/**
	 * @param parentsViewMB
	 *            the parentsViewMB to set
	 */
	public void setParentsViewMB(ParentsViewMB parentsViewMB) {
		this.parentsViewMB = parentsViewMB;
	}

	/**
	 * @return the teacherViewMB
	 */
	public TeacherViewMB getTeacherViewMB() {
		return teacherViewMB;
	}

	/**
	 * @param teacherViewMB
	 *            the teacherViewMB to set
	 */
	public void setTeacherViewMB(TeacherViewMB teacherViewMB) {
		this.teacherViewMB = teacherViewMB;
	}

	/**
	 * @return the studentDataBean
	 */
	public StudentDataBean getStudentDataBean() {
		return studentDataBean;
	}

	/**
	 * @param studentDataBean
	 *            the studentDataBean to set
	 */
	public void setStudentDataBean(StudentDataBean studentDataBean) {
		this.studentDataBean = studentDataBean;
	}

	/**
	 * @return the parentsDataBean
	 */
	public ParentsDataBean getParentsDataBean() {
		return parentsDataBean;
	}

	/**
	 * @param parentsDataBean
	 *            the parentsDataBean to set
	 */
	public void setParentsDataBean(ParentsDataBean parentsDataBean) {
		this.parentsDataBean = parentsDataBean;
	}

	/**
	 * @return the teacherDataBean
	 */
	public TeacherDataBean getTeacherDataBean() {
		return teacherDataBean;
	}

	/**
	 * @param teacherDataBean
	 *            the teacherDataBean to set
	 */
	public void setTeacherDataBean(TeacherDataBean teacherDataBean) {
		this.teacherDataBean = teacherDataBean;
	}

	/**
	 * @return the submenuList
	 */
	public List<SupProduct> getSubmenuList() {
		return submenuList;
	}

	/**
	 * @param submenuList
	 *            the submenuList to set
	 */
	public void setSubmenuList(List<SupProduct> submenuList) {
		this.submenuList = submenuList;
	}

	/**
	 * @return the model
	 */
	public MenuModel getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(MenuModel model) {
		this.model = model;
	}

	/**
	 * @return the orientation
	 */
	public String getOrientation() {
		return orientation;
	}

	/**
	 * @param orientation
	 *            the orientation to set
	 */
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	/**
	 * @return the flag
	 */
	public boolean isFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 * @return the loginaccess
	 */
	public LoginAccess getLoginaccess() {
		return loginaccess;
	}

	/**
	 * @param loginaccess
	 *            the loginaccess to set
	 */
	public void setLoginaccess(LoginAccess loginaccess) {
		this.loginaccess = loginaccess;
	}

	/**
	 * @return the login_username
	 */
	public String getLogin_username() {
		return login_username;
	}

	/**
	 * @param login_username
	 *            the login_username to set
	 */
	public void setLogin_username(String login_username) {
		this.login_username = login_username;
	}

	/**
	 * @return the rollname
	 */
	public String getRollname() {
		return rollname;
	}

	/**
	 * @param rollname
	 *            the rollname to set
	 */
	public void setRollname(String rollname) {
		this.rollname = rollname;
	}

	public String getInvusername() {
		return invusername;
	}

	public void setInvusername(String invusername) {
		this.invusername = invusername;
	}

	public String getInvpassword() {
		return invpassword;
	}

	public void setInvpassword(String invpassword) {
		this.invpassword = invpassword;
	}
	private boolean newlogoflag=false;
	private boolean oldlogoflag=false;
	public boolean isNewlogoflag() {
		return newlogoflag;
	}

	public void setNewlogoflag(boolean newlogoflag) {
		this.newlogoflag = newlogoflag;
	}

	public boolean isOldlogoflag() {
		return oldlogoflag;
	}

	public void setOldlogoflag(boolean oldlogoflag) {
		this.oldlogoflag = oldlogoflag;
	}
	

	
	public String userLogin() {
		logger.info("-----------Inside userLogin method()----------------");
		loginaccess.setOrganizationName(null);
		loginaccess.setOrgDetail(null);
		loginaccess.setOrgEmail(null);
		loginaccess.setOrgPhone(null);
		loginaccess.setCountryName(null); 
		loginaccess.setContact(null); 
		setScheduleFlag(false);
		setScheduleFlag1(false);
		studentListflag=false;
		flag1 = false;
		studentDataBean.setStuStudentId("");
		String successstatus = "Fail";
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		SmsController controller = null;
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (SmsController) ctx.getBean("controller");
		if (validate(true)) {

			logger.debug("Login name" + loginaccess.getUsername());
			try {
				String loginstatus=superadminlogin(loginaccess.getUsername(),loginaccess.getUserpassword());
				if(loginstatus.equalsIgnoreCase("Success"))
				{
					newschoolview();
					return "backofficehomepage";
				}else{
				String status = controller.userLogin(loginaccess);
				logger.debug("Status" + status);
				if (status.equalsIgnoreCase("Authorized")) {
					setRollname(loginaccess.getUser_rolles());
					logger.debug("get user name" + loginaccess.getUsername());
					logger.debug("get user name" + loginaccess.getPersonID());
					ExternalContext externalcontext = FacesContext.getCurrentInstance().getExternalContext();
					Map<String, Object> sessionMap = externalcontext.getSessionMap();

					if (loginaccess.getName().length() > 0) {
						if (loginaccess.getName().length() > 2) {
							StringBuilder sb = new StringBuilder(loginaccess.getName());
							loginaccess.setName(sb.substring(0, 1).toUpperCase() + sb.substring(1));
							logger.debug("---->>>>>" + sb.substring(0, 1).toUpperCase() + sb.substring(1));
						}

					}

					sessionMap.put("user", loginaccess.getUsername());
					sessionMap.put("LogID", loginaccess.getPersonID());
					sessionMap.put("schoolID", loginaccess.getSchoolID());
					sessionMap.put("Role", loginaccess.getUser_rolles());
					sessionMap.put("schoolName" ,loginaccess.getSchoolName());
					LoadMenu();
					scheduleCalender();
					classTimetable();
					String personID=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID").toString();
					if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role").equals("Parent")){
						studentList = controller.parentAttlist(personID, attendanceDataBean);
						logger.debug("studentList size "+studentList.size());
						if(studentList.size() > 0){
							if(studentList.size() > 1)
							{
								studentListflag=true;
								classtimetablelist=new ArrayList<ClassTimeTableDataBean>();
							}
							else{
								studentListflag=false;
								studentDataBean.setStuStudentId(studentList.get(0));
								logger.debug("studid "+studentList.get(0));
								classtimetablelist = controller.getstudentclassTimeTable(personID,studentDataBean);
								logger.debug("classtimetablelist  "+classtimetablelist);
								if (classtimetablelist == null) {

								} else {
									if (classtimetablelist.size() > 0) {
										flag1 = true;
										int c = 0;
										int c1 = 0;
										for (int i = 0; i < classtimetablelist.size(); i++) {
											try {
												if (!classtimetablelist.get(i).getExSubject().equalsIgnoreCase("")) {
													c++;
												}
											} catch (Exception e) {
												logger.warn("Exception -->"+e.getMessage());

											}
											try {
												if (!classtimetablelist.get(i).getExSubject1().equalsIgnoreCase("")) {
													c++;
												}
											} catch (Exception e) {
												logger.warn("Exception -->"+e.getMessage());

											}
										}
										if (c > 0) {
											for (int i = 0; i < 6; i++) {
												try {
													if (classtimetablelist.get(i).getExSubject().equals("")) {
													}
												} catch (Exception e) {
													classtimetablelist.get(i).setExSubject("-");
													logger.warn("Exception -->"+e.getMessage());

												}
											}
											exflag = true;
										}
										if (c1 > 0) {
											for (int i = 0; i < 6; i++) {
												try {
													if (classtimetablelist.get(i).getExSubject1().equals("")) {
													}
												} catch (Exception e) {
													classtimetablelist.get(i).setExSubject1("-");
													logger.warn("Exception -->"+e.getMessage());

												}
											}
											exflag1 = true;
										}
									}
								}
							}
						}	
					}					
					String roll = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
							.get("Role");
					String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
					String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
					int school_ID=Integer.parseInt(schoolID);
					loginaccess.setSchoolID(schoolID);
					loginaccess.setSupportNumber("+62 87864081839");
					/*if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5 || school_ID==6|| school_ID==37 ){
						loginaccess.setSupportNumber("+62 87864081839");
					}
					else {
						loginaccess.setSupportNumber("+91 9962343717");
					}*/
					
					schooleditlist=new ArrayList<School>();	
					if(school_ID==1 || school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5 || school_ID==6 ){
						setNewlogoflag(false);
						setOldlogoflag(true);
					if(schoolID.equalsIgnoreCase("1")){
						setLogos(text.getString("NRG_LOGO").toString());
					}
					if(schoolID.equalsIgnoreCase("2")){
						setLogos(text.getString("MTsMuallimat_LOGO").toString());
					}
					else if(schoolID.equalsIgnoreCase("3")){
						setLogos(text.getString("MTsMUAALLIMIN_LOGO").toString());
					}
					else if(schoolID.equalsIgnoreCase("4")){
						setLogos(text.getString("MAMuallimin_LOGO").toString());
					}
					else if(schoolID.equalsIgnoreCase("5")){
						setLogos(text.getString("MAMUALLIMAT_LOGO").toString());
					}
					else if(schoolID.equalsIgnoreCase("6")){
						setLogos(text.getString("MANWPancor_LOGO").toString());
					}
					}else{
						setNewlogoflag(true);
						setOldlogoflag(false);
							schooleditlist=new ArrayList<School>();
							loginaccess.setSchoolName(schoolName);
							schooleditlist=controller.schooledit(loginaccess);
							if(schooleditlist.size()>0){
								loginaccess.setFilepath(schooleditlist.get(0).getLogopath());
							}
					}
					if (roll.equalsIgnoreCase("Admin")) {
				
						getoverallpassDetails();
						setNotificationflag(false);
						setScheduleFlag(false);
						setScheduleFlag1(true);
						successstatus = "sucess";
					} else if (roll.equalsIgnoreCase("Parent") || roll.equalsIgnoreCase("Student")) {
						setScheduleFlag(false);
						setScheduleFlag1(true);
						setNotificationflag(false);
						successstatus = "dashboard2";
					} else if (roll.equalsIgnoreCase("Teacher")) {
						setScheduleFlag(false);
						setScheduleFlag1(true);
						setNotificationflag(false);
						successstatus = "dashboard";
					} else if (roll.equalsIgnoreCase("Bookshop")) {
						setScheduleFlag(false);
						setScheduleFlag1(true);
						setNotificationflag(false);
						if(school_ID==1 || school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5 || school_ID==6){
							bookflag="1";bookflag1="none";
						}
						else{
							bookflag="none";bookflag1="1";
						}
						successstatus = "dashboard3";
					} else if (roll.equalsIgnoreCase("Librarian")) {
						setScheduleFlag(false);
						setScheduleFlag1(true);
						setNotificationflag(false);
						if(school_ID==1 || school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5 || school_ID==6){
							bookflag="1";bookflag1="none";
						}
						else{
							bookflag="none";bookflag1="1";
						}
						controller.getNotReturnedBooks(personID,loginaccess);
						successstatus = "dashboard4";
					}
					else if (roll.equalsIgnoreCase("Staff")) {
						setScheduleFlag(false);
						setScheduleFlag1(true);
						setNotificationflag(false);
						successstatus = "dashboard5";
					}
					if(schoolID.equalsIgnoreCase(paths.getString("MALAYSIA.SCHOOLID"))){
						 if (roll.equalsIgnoreCase("Teacher")) {
							 setHomebtnFlag(true);
						 }else{
							 setHomebtnFlag(false);
						 }
					}
					else{
						  setHomebtnFlag(false);
					}
					return successstatus;
				} else if(status.equalsIgnoreCase("Fail")){
					fieldName = CommonValidate.findComponentInRoot("pwd").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.userpwd.notpwdvalid")));
					loginaccess.setUsername("");
					loginaccess.setUserpassword("");
					return "myNewLogin1";
				}
				else {
					fieldName = CommonValidate.findComponentInRoot("te").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.userpwd.notvalid")));
					loginaccess.setUsername("");
					loginaccess.setUserpassword("");

					return "myNewLogin1";
				}
				}
			} catch (Exception e) {
				loginaccess.setUsername("");
				loginaccess.setUserpassword("");
				logger.warn("Exception -->"+e.getMessage());
				//logger.warn("Inside Exception",e);
				//e.printStackTrace();
				return "myNewLogin1";
			}

		} else {
			loginaccess.setUsername("");
			loginaccess.setUserpassword("");

			return "myNewLogin1";
		}
		
	}

	private String superadminlogin(String username, String userpassword)throws SQLException {
		logger.info("-----------Inside superadminlogin method()----------------");
		 String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		 String DB_URL =database.getString("DB_URL");
		 String USER = database.getString("USER");
		 String PASS = database.getString("PASSWORD");
		 Connection con = null;  
		 Statement stmt=null;
		 String status="Fail";
		 try{
			 Class.forName(JDBC_DRIVER);
			 con=DriverManager.getConnection(DB_URL, USER, PASS);
			 stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT username, password FROM superadmin_login;");
			 while(rs.next()){
	                String Username = rs.getString("username");
	                String Password = rs.getString("password");
	                if(Username.equalsIgnoreCase(username) && Password.equalsIgnoreCase(userpassword)){
	                   status="Success";
	                }
			 }
		 }catch(Exception e){
				logger.warn("Exception -->"+e.getMessage());
		 }
		return status;
	}
	
Date date = new Date();
 public String Month;
 

	public String getMonth() {
	return Month;
}

public void setMonth(String month) {
	Month = month;
}

	private void classTimetable() {
		logger.info("-----------Inside classTimetable method()----------------");
		flag1 = false;
		classtimetablelist = null;
		SmsController controller = null;
		Month="";
		try {
			Month=new SimpleDateFormat("MMMM").format(date);
			classtimetablelist = new ArrayList<ClassTimeTableDataBean>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			classtimetablelist = controller.getclassTimeTable(personID);
			if (classtimetablelist == null) {

			} else {
				if (classtimetablelist.size() > 0) {
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6) timeflag=true;
					else timeflag=false;
					flag1 = true;
					int c = 0;
					int c1 = 0;
					for (int i = 0; i < classtimetablelist.size(); i++) {
						logger.debug("day "+classtimetablelist.get(i).getDay());
						try {
							if (!classtimetablelist.get(i).getExSubject().equalsIgnoreCase("")) {
								c++;
							}
						} catch (Exception e) {
							logger.warn("Exception -->"+e.getMessage());

						}
						try {
							if (!classtimetablelist.get(i).getExSubject1().equalsIgnoreCase("")) {
								c++;
							}
						} catch (Exception e) {
							logger.warn("Exception -->"+e.getMessage());

						}
					}
					if (c > 0) {
						for (int i = 0; i < 6; i++) {
							try {
								if (classtimetablelist.get(i).getExSubject().equals("")) {
								}
							} catch (Exception e) {
								classtimetablelist.get(i).setExSubject("-");
								logger.warn("Exception -->"+e.getMessage());

							}
						}
						exflag = true;
					}
					if (c1 > 0) {
						for (int i = 0; i < 6; i++) {
							try {
								if (classtimetablelist.get(i).getExSubject1().equals("")) {
								}
							} catch (Exception e) {
								classtimetablelist.get(i).setExSubject1("-");
								logger.warn("Exception -->"+e.getMessage());

							}
						}
						exflag1 = true;
					}
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("Exception -->"+e.getMessage());
		}
	}

	private void LoadMenu() {
		logger.info("-----------Inside LoadMenu method()----------------");
		SmsController controller = null;
		try {
			model = new DefaultMenuModel();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			menuList = new ArrayList<UserProduct>();
			menuList = controller.LoadMenu(loginaccess);
			logger.debug("MB Size" + menuList.size());
			String role=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role").toString();
			if (menuList.size() > 0) {
				int schoolID=menuList.get(0).getUser().getPerson().getSchool().getSchool_ID();
				logger.debug("school id "+schoolID);
				for (int i = 0; i < menuList.size(); i++) {
					logger.debug("menu "+menuList.get(i).getProduct().getProductName());
					DefaultSubMenu firstSubmenu = new DefaultSubMenu(menuList.get(i).getProduct().getProductName().toUpperCase());
					submenuList = new ArrayList<SupProduct>();
					submenuList = controller.loadSubMenu(menuList.get(i).getProduct().getProduct_ID(),
							menuList.get(i).getProduct().getProductCode());
					if (submenuList.size() > 0) {						
						for (int j = 0; j < submenuList.size(); j++) {
							logger.debug("submenu "+submenuList.get(j).getProductCode());
							if (submenuList.get(j).getProductCode().equalsIgnoreCase("REG001")) {
								logger.info("SubMenu --------------> REG001");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{teacherMB.teacherPage}");
								item.setAjax(false);
								item.setIcon("fa fa-user-plus");
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("REG002")) {
								logger.info("SubMenu --------------> REG002");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{studentMB.addStudentPage}");
								item.setAjax(false);
								item.setIcon("fa fa-user-plus");
								firstSubmenu.addElement(item);
							} 
							// File upload sub menu
							else if (submenuList.get(j).getProductCode().equalsIgnoreCase("REG008")) {
								logger.info("SubMenu --------------> REG007");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{studentMB.fileUploadPage}");
								item.setAjax(false);
								item.setIcon("fa fa-user-plus");
								firstSubmenu.addElement(item);
							}
							
							else if (submenuList.get(j).getProductCode().equalsIgnoreCase("REG003")) {
								logger.info("SubMenu --------------> REG003");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{parentsMB.loadParentsPage}");
								item.setIcon("fa fa-user-plus");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("REG004")) {
								logger.info("SubMenu --------------> REG004");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{staffMB.loadStaffPage}");
								item.setAjax(false);
								item.setIcon("fa fa-user-plus");
								firstSubmenu.addElement(item);
							}else if (submenuList.get(j).getProductCode().equalsIgnoreCase("REG006")) {
								logger.info("SubMenu --------------> REG006");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{staffMB.loadStaffPage2}");
								item.setAjax(false);
								item.setIcon("fa fa-user-plus");
								firstSubmenu.addElement(item);
							}else if (submenuList.get(j).getProductCode().equalsIgnoreCase("TIM001")) {
								logger.info("SubMenu --------------> TIM001");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{timeTableMB.addTimeTable}");
								item.setIcon("fa fa-calendar");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("TIM002")) {
								logger.info("SubMenu --------------> TIM002");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{timeTableViewMB.timeTableView}");
								item.setIcon("fa fa-eye");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("TIM003")) {
								logger.info("SubMenu --------------> TIM003");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{classTimeTableMB.classtimetable}");
								item.setIcon("fa fa-calendar");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("TIM004")) {
								logger.info("SubMenu --------------> TIM004");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{classTimeTableViewMB.classTimeViewCall}");
								item.setIcon("fa fa-calendar-o");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("NOT001")) {
								logger.info("SubMenu --------------> NOT001");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{noticeBoardMB.noticePageLoad}");
								item.setIcon("fa fa-newspaper-o");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("NOT002")) {
								logger.info("SubMenu --------------> NOT002");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{noticeBoardViewMB.noticeBoardPageLoad}");
								item.setIcon("fa fa-comments");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("STU001")) {
								logger.info("SubMenu --------------> STU001");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{studentPerformanceMB.attitudePage}");
								item.setAjax(false);
								item.setIcon("fa fa-street-view");
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("STU002")) {
								logger.info("SubMenu --------------> STU002");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{studentPerformanceViewMB.AttitudePageLoad}");
								item.setAjax(false);
								item.setIcon("fa fa-magic");
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("REP001")) {
								logger.info("SubMenu --------------> REP001");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{reportCardMB.addMarkPage}");
								item.setIcon("fa fa-filter");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("REP002")) {
								logger.info("SubMenu --------------> REP002");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{reportCardViewMB.marksheetviewpage}");
								item.setAjax(false);
								item.setIcon("fa fa-trophy");
								firstSubmenu.addElement(item);
							}  else if (submenuList.get(j).getProductCode().equalsIgnoreCase("FEE001")) {
								logger.info("SubMenu --------------> FEE001");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{paymentMB.feesReg}");
								item.setIcon("fa fa-money");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("PAY001")) {
								logger.info("SubMenu --------------> PAY001");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{feesPaymentViewMB.feesViewCall}");
								item.setIcon("fa fa-info");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							}else if (submenuList.get(j).getProductCode().equalsIgnoreCase("BOP001")) {
								logger.info("SubMenu --------------> BOP001");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{paymentMB.bookpayment}");
								item.setIcon("fa fa-money");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							}
								else if (submenuList.get(j).getProductCode().equalsIgnoreCase("PRO001")) {
								logger.info("SubMenu --------------> PRO001");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{teacherViewMB.teacherInfo}");
								item.setIcon("fa fa-info");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("PRO002")) {
								logger.info("SubMenu --------------> PRO002");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{studentViewMB.studentInfo}");
								item.setAjax(false);
								item.setIcon("fa fa-info");
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("PRO003")) {
								logger.info("SubMenu --------------> PRO003");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{parentsViewMB.parentsView}");
								item.setIcon("fa fa-info");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							}else if(submenuList.get(j).getProductCode().equalsIgnoreCase("PRO007")){
								logger.info("SubMenu --------------> PRO007");
								if(schoolID==Integer.parseInt(paths.getString("SMRV.SCHOOLID"))){
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{studentViewMB.studentCommunitypage}");
								item.setIcon("fa fa-info");
								item.setAjax(false);
								firstSubmenu.addElement(item);
								}
							}else if (submenuList.get(j).getProductCode().equalsIgnoreCase("ATT001")) {
								logger.info("SubMenu --------------> ATT001");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{attendanceMB.markAttendancePage}");
								item.setIcon("fa fa-check");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("ATT002")) {
								logger.info("SubMenu --------------> ATT002");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{attendancePreviewMB.previewAttendace}");
								item.setAjax(false);
								item.setIcon("fa fa-bullseye");
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("ATT003")) {
								logger.info("SubMenu --------------> ATT003");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{attendanceChartMB.attendancechart}");
								item.setIcon("fa fa-line-chart");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("ATT004")) {
								logger.info("SubMenu --------------> ATT004");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{attendanceMB.leaveRequestCall}");
								item.setIcon("fa fa-check-circle-o");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("ATT005")) {
								logger.info("SubMenu --------------> ATT005");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{attendanceMB.leaveRequestViewCall}");
								item.setIcon("fa fa-check-circle-o");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} 
							if(schoolID==1 || schoolID==2 || schoolID==3 || schoolID==4 || schoolID==5 || schoolID==6){
								if (submenuList.get(j).getProductCode().equalsIgnoreCase("LIB001")) {
									logger.info("SubMenu --------------> LIB001");
									DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
									item.setCommand("#{libraryMB.loadAddBook}");
									item.setIcon("fa fa-plus-square");
									item.setAjax(false);
									firstSubmenu.addElement(item);
								} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("LIB002")) {
									logger.info("SubMenu --------------> LIB002");
									DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
									item.setCommand("#{libraryViewMB.bookListPage}");
									item.setIcon("fa fa-ils");
									item.setAjax(false);
									firstSubmenu.addElement(item);
								} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("LIB003")) {
									logger.info("SubMenu --------------> LIB003");
									DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
									item.setCommand("#{libraryMB.bookBorrowerListPage}");
									item.setAjax(false);
									item.setIcon("fa fa-exchange");
									firstSubmenu.addElement(item);
								} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("LIB004")) {
									logger.info("SubMenu --------------> LIB004");
									DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
									item.setCommand("#{libraryViewMB.BorrowerViewListPage}");
									item.setAjax(false);
									item.setIcon("fa fa-stack-exchange");
									firstSubmenu.addElement(item);
								}								
							}else{
								if(role.equalsIgnoreCase("Parent")){
									 if (submenuList.get(j).getProductCode().equalsIgnoreCase("LIB002")) {
									logger.info("SubMenu --------------> LIB002");
									DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
									item.setCommand("#{libraryViewMB.bookListPage}");
									item.setIcon("fa fa-ils");
									item.setAjax(false);
									firstSubmenu.addElement(item);
								}
								}
								if (submenuList.get(j).getProductCode().equalsIgnoreCase("LIB001")) {
									logger.info("SubMenu --------------> LIB001");
									DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
									item.setCommand("#{libraryMB.loadAddBook}");
									item.setIcon("fa fa-plus-square");
									item.setAjax(false);
									firstSubmenu.addElement(item);
								}else if (submenuList.get(j).getProductCode().equalsIgnoreCase("LIB005")) {
									logger.info("SubMenu --------------> LIB005");
									DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
									item.setCommand("#{libraryMB.loadstockin}");
									item.setIcon("fa fa-ils");
									item.setAjax(false);
									firstSubmenu.addElement(item);
								} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("LIB003")) {
									logger.info("SubMenu --------------> LIB003");
									DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
									item.setCommand("#{libraryMB.bookBorrowerListPage}");
									item.setAjax(false);
									item.setIcon("fa fa-exchange");
									firstSubmenu.addElement(item);
								} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("LIB004")) {
									logger.info("SubMenu --------------> LIB004");
									DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
									item.setCommand("#{libraryViewMB.BorrowerViewListPage}");
									item.setAjax(false);
									item.setIcon("fa fa-stack-exchange");
									firstSubmenu.addElement(item);
								}else if (submenuList.get(j).getProductCode().equalsIgnoreCase("LIB006")) {
									logger.info("SubMenu --------------> LIB006");
									DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
									item.setCommand("#{libraryMB.returnBookCall}");
									item.setAjax(false);
									item.setIcon("fa fa-exchange");
									firstSubmenu.addElement(item);
								} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("LIB007")) {
									logger.info("SubMenu --------------> LIB007");
									DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
									item.setCommand("#{libraryViewMB.returnViewListPage}");
									item.setAjax(false);
									item.setIcon("fa fa-stack-exchange");
									firstSubmenu.addElement(item);
								}
							}
							
							 
							if(schoolID==1 || schoolID==2 || schoolID==3 || schoolID==4 || schoolID==5 || schoolID==6){
								if(role.equalsIgnoreCase("Parent")){
									if (submenuList.get(j).getProductCode().equalsIgnoreCase("BOO002")) {
										logger.info("SubMenu --------------> BOO002");
										DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
										item.setCommand("#{bookSaleViewMB.bookSalesviewLoad}");
										item.setIcon("fa fa-money");
										item.setAjax(false);
										firstSubmenu.addElement(item);
									}
									else if (submenuList.get(j).getProductCode().equalsIgnoreCase("BOO001")) {
										logger.info("SubMenu --------------> BOO001");
										DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
										item.setCommand("#{bookSaleMB.bookSalePage}");
										item.setIcon("fa fa-plus-circle");
										item.setAjax(false);
										firstSubmenu.addElement(item);
									}
								}else if(role.equalsIgnoreCase("Student")){
									if (submenuList.get(j).getProductCode().equalsIgnoreCase("BOO002")) {
										logger.info("SubMenu --------------> BOO002");
										DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
										item.setCommand("#{bookSaleViewMB.bookSalesviewLoad}");
										item.setIcon("fa fa-plus-circle");
										item.setAjax(false);
										firstSubmenu.addElement(item);
									}
								}else{
									if (submenuList.get(j).getProductCode().equalsIgnoreCase("BOO003")) {
										logger.info("SubMenu --------------> BOO003");
										DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
										item.setCommand("#{addBookSaleMB.addBookSalePage}");
										item.setIcon("fa fa-plus-circle");
										item.setAjax(false);
										firstSubmenu.addElement(item);
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("BOO004")) {
										logger.info("SubMenu --------------> BOO004");
										DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
										item.setCommand("#{bookDetailViewMB.bookDetailViewPage}");
										item.setIcon("fa fa-search-plus");
										item.setAjax(false);
										firstSubmenu.addElement(item);
									}
								}								
							}else{
								if(role.equalsIgnoreCase("Parent")){
									if (submenuList.get(j).getProductCode().equalsIgnoreCase("BOO503")) {
										logger.info("SubMenu --------------> BOO503");
										DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
										item.setCommand("#{bookSaleMB.bookSalePage}");
										item.setIcon("fa fa-money");
										item.setAjax(false);
										firstSubmenu.addElement(item);
									}
									else if (submenuList.get(j).getProductCode().equalsIgnoreCase("BOO504")) {
										logger.info("SubMenu --------------> BOO504");
										DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
										item.setCommand("#{bookSaleViewMB.bookSalesviewLoad}");
										item.setIcon("fa fa-plus-circle");
										item.setAjax(false);
										firstSubmenu.addElement(item);
									}
								}else if(role.equalsIgnoreCase("Student")){
									if (submenuList.get(j).getProductCode().equalsIgnoreCase("BOO504")) {
										logger.info("SubMenu --------------> BOO504");
										DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
										item.setCommand("#{bookSaleViewMB.bookSalesviewLoad}");
										item.setIcon("fa fa-plus-circle");
										item.setAjax(false);
										firstSubmenu.addElement(item);
									}
								}else{
									if (submenuList.get(j).getProductCode().equalsIgnoreCase("BOO501")) {
										logger.info("SubMenu --------------> BOO501");
										DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
										item.setCommand("#{addBookSaleMB.addBookSalePage}");
										item.setIcon("fa fa-plus-circle");
										item.setAjax(false);
										firstSubmenu.addElement(item);
									} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("BOO502")) {
										logger.info("SubMenu --------------> BOO502");
										DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
										item.setCommand("#{bookDetailViewMB.stockinpage}");
										item.setIcon("fa fa-search-plus");
										item.setAjax(false);
										firstSubmenu.addElement(item);
									}else if (submenuList.get(j).getProductCode().equalsIgnoreCase("BOO503")) {
										logger.info("SubMenu --------------> BOO503");
										DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
										item.setCommand("#{bookSaleViewMB.bookSalesviewLoad}");
										item.setIcon("fa fa-plus-circle");
										item.setAjax(false);
										firstSubmenu.addElement(item);
									}
									else if (submenuList.get(j).getProductCode().equalsIgnoreCase("BOO004")) {
										logger.info("SubMenu --------------> BOO004");
										DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
										item.setCommand("#{bookDetailViewMB.bookDetailViewPage}");
										item.setIcon("fa fa-search-plus");
										item.setAjax(false);
										firstSubmenu.addElement(item);
									}
								}								
							}
							 if (submenuList.get(j).getProductCode().equalsIgnoreCase("REP003")) {
								logger.info("SubMenu --------------> REP003");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{reportCardViewMB.barchartpageLoad}");
								item.setIcon("fa fa-line-chart");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("PRO004")) {
								logger.info("SubMenu --------------> PRO004");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{staffMB.staffview}");
								item.setIcon("fa fa-info");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("PRO005")) {
								logger.info("SubMenu --------------> PRO005");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{librarianViewMB.librarianInfo}");
								item.setIcon("fa fa-info");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("PRO006")) {
								logger.info("SubMenu --------------> PRO006");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{staffMB.bookshopview}");
								item.setIcon("fa fa-info");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("REG005")) {
								logger.info("SubMenu --------------> REG005");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{librarianMB.loadLibrarianPage}");
								item.setIcon("fa fa-user-plus");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("HW001")) {
								logger.info("SubMenu --------------> HW001");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{homeWorkMB.homeworkcall}");
								item.setIcon("fa fa-pencil");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("HW002")) {
								logger.info("SubMenu --------------> HW002");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{homeWorkMB.homeworkViewCall}");
								item.setIcon("fa fa-pencil");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							} else if (submenuList.get(j).getProductCode().equalsIgnoreCase("HW003")) {
								logger.info("SubMenu --------------> HW003");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{homeWorkMB.homeworkViewCall}");
								item.setIcon("fa fa-pencil");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							}else if (submenuList.get(j).getProductCode().equalsIgnoreCase("REG007")) {
								logger.info("SubMenu --------------> REG007");
								DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
								item.setCommand("#{librarianMB.classsubjectpage}");
								item.setIcon("fa fa-user-plus");
								item.setAjax(false);
								firstSubmenu.addElement(item);
							}else if (submenuList.get(j).getProductCode().equalsIgnoreCase("MYS001")) {
								logger.info("SubMenu --------------> MYS001");
						        DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
						        item.setCommand("#{librarianViewMB.classsubjectviewpage}");
						        item.setIcon("fa fa-info");
						        item.setAjax(false);
						        firstSubmenu.addElement(item);
						       }else if (submenuList.get(j).getProductCode().equalsIgnoreCase("CLS001")) {
								logger.info("SubMenu --------------> CLS001");
						        DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
						        item.setCommand("#{studentViewMB.classchangepage}");
						        item.setIcon("fa fa-info");
						        item.setAjax(false);
						        firstSubmenu.addElement(item);
						       }else if (submenuList.get(j).getProductCode().equalsIgnoreCase("MYS002")) {
						        DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
						        item.setUrl("../../pages/xhtml/studentReport.xhtml");
						        item.setIcon("fa fa-info");
						        item.setAjax(false);
						        firstSubmenu.addElement(item);
						       }
								else if (submenuList.get(j).getProductCode().equalsIgnoreCase("CER001")) {
									logger.info("SubMenu --------------> CER001");
							        DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
							        item.setCommand("#{staffViewMB.relievingOrderPage}");
							        item.setIcon("fa fa-info");
							        item.setAjax(false);
							        firstSubmenu.addElement(item);
							  }
						     else if (submenuList.get(j).getProductCode().equalsIgnoreCase("CER002")) {
						    	 	logger.info("SubMenu --------------> CER002");
							        DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
							        item.setCommand("#{staffViewMB.bonafidePage}");
							        item.setIcon("fa fa-info");
							        item.setAjax(false);
							        firstSubmenu.addElement(item);
							     }
						     else if (submenuList.get(j).getProductCode().equalsIgnoreCase("CER003")) {
						    	 	logger.info("SubMenu --------------> CER003");
							        DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
							        item.setCommand("#{staffViewMB.conductPage}");
							        item.setIcon("fa fa-info");
							        item.setAjax(false);
							        firstSubmenu.addElement(item);
							     }
						     else if (submenuList.get(j).getProductCode().equalsIgnoreCase("CER004")) {
						    	 	logger.info("SubMenu --------------> CER004");
							        DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
							        item.setCommand("#{staffViewMB.transferCertificatePage}");
							        item.setIcon("fa fa-info");
							        item.setAjax(false);
							        firstSubmenu.addElement(item);
							     }
						     else if (submenuList.get(j).getProductCode().equalsIgnoreCase("REM001")) {
						    	 	logger.info("SubMenu --------------> REM001");
							        DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
							        item.setCommand("#{reportCardViewMB.markreportpage}");
							        item.setIcon("fa fa-info");
							        item.setAjax(false);
							        firstSubmenu.addElement(item);
							     }
						     else if (submenuList.get(j).getProductCode().equalsIgnoreCase("REB001")) {
						    	 	logger.info("SubMenu --------------> REB001");
							        DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
							        item.setCommand("#{studentPerformanceViewMB.performancereportpage}");
							        item.setIcon("fa fa-info");
							        item.setAjax(false);
							        firstSubmenu.addElement(item);
							     }
						     else if (submenuList.get(j).getProductCode().equalsIgnoreCase("AR001")) {
						    	 	logger.info("SubMenu --------------> AR001");
							        DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
							        item.setCommand("#{reportMB.reportpage}");
							        item.setIcon("fa fa-plus");
							        item.setAjax(false);
							        firstSubmenu.addElement(item);
							     }
						     else if (submenuList.get(j).getProductCode().equalsIgnoreCase("REA001")) {
						    	 	logger.info("SubMenu --------------> REA001");
							        DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
							        item.setCommand("#{attendancePreviewMB.attendanceReport}");
							        item.setIcon("fa fa-info");
							        item.setAjax(false);
							        firstSubmenu.addElement(item);
							     }
						     else if (submenuList.get(j).getProductCode().equalsIgnoreCase("REMENU001")) {
						    	 	logger.info("SubMenu --------------> REMENU001");
							        DefaultMenuItem item = new DefaultMenuItem(submenuList.get(j).getProductName().toUpperCase());
							        item.setCommand("#{reportMB.reportmenuPage}");
							        item.setIcon("fa fa-bars");
							        item.setAjax(false);
							        firstSubmenu.addElement(item);
							     }
						}
					}
					model.addElement(firstSubmenu);

				}
			}
		} catch (NullPointerException e1) {
			logger.debug("Inside Null Pointer");
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}

	private boolean validate(boolean flag) {
		logger.info("-----------Inside validate method()----------------");
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (loginaccess.getUsername().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("uName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.username.blank").toString()));
			}
			valid = false;
		}
		if (loginaccess.getUserpassword().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("pwd").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.password.blank")));
			}
			valid = false;
		}
		return valid;
	}

	public String logout() {
		logger.info("-----------Inside logout method()----------------");
		try {

			HttpSession session = Util.getSession();
			session.removeAttribute("user");
			session.removeAttribute("LogID");
			session.removeAttribute("Role");
			session.invalidate();
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			Cookie[] cookies = request.getCookies();
			Cookie opentoken = null;
			for (Cookie c : cookies) {
				if (c.getName().equals("opentoken")) {
					logger.debug(
							"found the cookie: " + c.getName() + " domain:" + c.getDomain() + " exp:" + c.getMaxAge()); // log4j
																														// debug
																														// statement
					opentoken = c;
					opentoken.setMaxAge(0);
					opentoken.setValue(""); // it is more elegant to clear the
											// value but not necessary
					response.addCookie(opentoken);
					logger.debug("redirecting to " + request.getContextPath());
					response.sendRedirect(request.getContextPath());
					break;
				}
			}
			return "homePage";
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			return "failure";
		}
	}

	private int salesapprovalGM;
	private int salesapprovalMM;

	public int getSalesapprovalGM() {
		return salesapprovalGM;
	}

	public void setSalesapprovalGM(int salesapprovalGM) {
		this.salesapprovalGM = salesapprovalGM;
	}

	public int getSalesapprovalMM() {
		return salesapprovalMM;
	}

	public void setSalesapprovalMM(int salesapprovalMM) {
		this.salesapprovalMM = salesapprovalMM;
	}

	public String loadAdminPage() {
		logger.debug("Inside" + invusername);

		return "loadAdmin";
	}

	public String clickButton() {
		logger.info("-----------Inside clickButton method()----------------");
		setInvpassword(invusername);
		return "loadAdmin1";
	}

	public String loginPage() {
		logger.debug("Login");

		return "loadLogin";
	}

	public void onTabChange(TabChangeEvent event) {
		logger.debug("Input Text" + event.getTab().getTitle());
	}

	public String userLogin1() {
		logger.info("-----------Inside userLogin1 method()----------------");
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (StringUtils.isBlank(loginaccess.getUsername())) {
			logger.debug("Please");
			fieldName = CommonValidate.findComponentInRoot("uName1").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the UserName."));
		}
		return "";

	}

	public void changeEvent(ValueChangeEvent event) {
		logger.info("-----------Inside changeEvent method()----------------");
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		logger.debug("Input Text" + event.getNewValue());
		String aa = event.getNewValue().toString();
		loginaccess.setUsername(aa);
		if (loginaccess.getUsername().equalsIgnoreCase("") || loginaccess.getUsername() == null) {
			logger.debug("Inside Method");
			fieldName = CommonValidate.findComponentInRoot("uName").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage(text.getString("sms.username.blank").toString()));
		}

	}

	public void changeEvent1(ValueChangeEvent event) {
		logger.info("-----------Inside changeEvent1 method()----------------");
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		logger.debug("Input Text" + event.getNewValue());
		String aa = event.getNewValue().toString();
		loginaccess.setUserpassword(aa);
		if (loginaccess.getUserpassword().equalsIgnoreCase("") || loginaccess.getUserpassword() == null) {
			logger.debug("Inside Method");
			fieldName = CommonValidate.findComponentInRoot("pwd").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage(text.getString("sms.password.blank").toString()));
		}

	}

	/**
	 * @return the menuList
	 */
	public List<UserProduct> getMenuList() {
		return menuList;
	}

	/**
	 * @param menuList
	 *            the menuList to set
	 */
	public void setMenuList(List<UserProduct> menuList) {
		this.menuList = menuList;
	}

	public String aboutMe() {
		logger.info("-----------Inside aboutMe method()----------------");
		SmsController controller = null;
		String rollnumber = "";
		String rollnumber1 = "";
		schoollist = null;
		try {
			schoollist = new ArrayList<School>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				String status = controller.getRoll(personID);
				logger.debug("-----status----" + status);
				if (status.equalsIgnoreCase("Admin")) {
					logger.debug("---------Admin-------");
					schoollist = controller.getAdminDetails(personID, loginaccess);
					if (schoollist.size() > 0) {
						loginaccess.setSchoolName(schoollist.get(0).getName());
						loginaccess.setSchoolPhone(schoollist.get(0).getPhoneNumber());
						loginaccess.setSchoolFax(schoollist.get(0).getFaxNumber());
						loginaccess.setSchoolEmail(schoollist.get(0).getEmailId());
						loginaccess.setSchoolAddress(schoollist.get(0).getAddress());
						//loginaccess.setSchoolPost(schoollist.get(0).getPostcode().getPostcode());
					}
					return "adminPage";
				}
				if (status.equalsIgnoreCase("Teacher")) {

					logger.debug("-----------Teacher---------");
					rollnumber1 = controller.getRollNumber(personID);
					logger.debug("------test roll-------" + rollnumber1);
					teacherDataBean.setTeaID(rollnumber1);
					teacherViewMB.teacherDataBean.setTeaID(rollnumber1);
					logger.debug("--------rollnumber=====" + teacherDataBean.getTeaID());
					teacherViewMB.edit();
					return "editPageLoad1";
				}
				if (status.equalsIgnoreCase("Student")) {
					logger.debug("--------Student---------");
					rollnumber = controller.getRollNumber(personID);
					logger.debug("--------roll number-----" + rollnumber);
					studentDataBean.setStuRollNo(rollnumber);
					studentViewMB.studentDataBean.setStuRollNo(rollnumber);
					// studentViewMB.studentDataBean.setProfileflag(false);
					studentViewMB.studentEdit();
					return "editPageLoad";

				}
				if (status.equalsIgnoreCase("Parent")) {
					logger.debug("-----------Parent---------");
					rollnumber = controller.getRollNumber(personID);
					rollnumber1 = controller.getParentRollNumber(personID, loginaccess);
					parentsDataBean.setParParentID(rollnumber1);
					parentsDataBean.setParStuClass(loginaccess.getRollNumber());
					parentsViewMB.parentsDataBean.setParStuClass(loginaccess.getRollNumber());
					parentsDataBean.setParStudID(rollnumber);
					parentsViewMB.parentsDataBean.setParParentID(rollnumber1);
					parentsViewMB.edit();
					if (parentsViewMB.getParentList().get(0).getStudentParentList().size() > 0) {
						for (int i = 0; i < parentsViewMB.getParentList().get(0).getStudentParentList().size(); i++) {
							logger.debug("id "+
									parentsViewMB.getParentList().get(0).getStudentParentList().get(i).getStudent().getRollNumber());
							if(parentsViewMB.getParentList().get(0).getStudentParentList().size()==1){ 
									parentsViewMB.parentsDataBean.setParStudID(
											parentsViewMB.getParentList().get(0).getStudentParentList().get(0).getStudent().getRollNumber());
								parentsViewMB.parentsDataBean.setParStuClass(parentsViewMB.getParentList().get(0).getStudentClass().get(0));
							}
							else if(parentsViewMB.getParentList().get(0).getStudentParentList().size()==2){
								parentsViewMB.parentsDataBean.setParStudID(
									parentsViewMB.getParentList().get(0).getStudentParentList().get(0).getStudent().getRollNumber()+" , "+
											parentsViewMB.getParentList().get(0).getStudentParentList().get(1).getStudent().getRollNumber());
								parentsViewMB.parentsDataBean.setParStuClass(parentsViewMB.getParentList().get(0).getStudentClass().get(0)+" , "
											+parentsViewMB.getParentList().get(0).getStudentClass().get(1));
							}
							else if(parentsViewMB.getParentList().get(0).getStudentParentList().size()==3){
								parentsViewMB.parentsDataBean.setParStudID(
									parentsViewMB.getParentList().get(0).getStudentParentList().get(0).getStudent().getRollNumber()+" , "+
											parentsViewMB.getParentList().get(0).getStudentParentList().get(1).getStudent().getRollNumber()+" , "+
											parentsViewMB.getParentList().get(0).getStudentParentList().get(2).getStudent().getRollNumber());
								parentsViewMB.parentsDataBean.setParStuClass(parentsViewMB.getParentList().get(0).getStudentClass().get(0)+" , "
										+parentsViewMB.getParentList().get(0).getStudentClass().get(1)+" , "+parentsViewMB.getParentList().get(0).getStudentClass().get(2));
							}
							else if(parentsViewMB.getParentList().get(0).getStudentParentList().size()==4){
								parentsViewMB.parentsDataBean.setParStudID(
									parentsViewMB.getParentList().get(0).getStudentParentList().get(0).getStudent().getRollNumber()+" , "+
											parentsViewMB.getParentList().get(0).getStudentParentList().get(1).getStudent().getRollNumber()+" , "+
											parentsViewMB.getParentList().get(0).getStudentParentList().get(2).getStudent().getRollNumber()+" , "+
											parentsViewMB.getParentList().get(0).getStudentParentList().get(3).getStudent().getRollNumber());
								parentsViewMB.parentsDataBean.setParStuClass(parentsViewMB.getParentList().get(0).getStudentClass().get(0)+" , "
										+parentsViewMB.getParentList().get(0).getStudentClass().get(1)+" , "+parentsViewMB.getParentList().get(0).getStudentClass().get(2)
										+" , "+parentsViewMB.getParentList().get(0).getStudentClass().get(3));
							}
							else if(parentsViewMB.getParentList().get(0).getStudentParentList().size()==5){
								parentsViewMB.parentsDataBean.setParStudID(
									parentsViewMB.getParentList().get(0).getStudentParentList().get(0).getStudent().getRollNumber()+" , "+
											parentsViewMB.getParentList().get(0).getStudentParentList().get(1).getStudent().getRollNumber()+" , "+
											parentsViewMB.getParentList().get(0).getStudentParentList().get(2).getStudent().getRollNumber()+" , "+
									parentsViewMB.getParentList().get(0).getStudentParentList().get(3).getStudent().getRollNumber()+" , "+
									parentsViewMB.getParentList().get(0).getStudentParentList().get(4).getStudent().getRollNumber());
								parentsViewMB.parentsDataBean.setParStuClass(parentsViewMB.getParentList().get(0).getStudentClass().get(0)+" , "
										+parentsViewMB.getParentList().get(0).getStudentClass().get(1)+" , "+parentsViewMB.getParentList().get(0).getStudentClass().get(2)
										+" , "+parentsViewMB.getParentList().get(0).getStudentClass().get(3)+" , "+parentsViewMB.getParentList().get(0).getStudentClass().get(4));
							}
						}
					}
					return "parentEditPage";
				}
				if (status.equalsIgnoreCase("Staff")) {
					logger.info("-----------staff---------");
					rollnumber1 = controller.getStaffRollNumber(personID, loginaccess);
					staffMB.staffDataBean.setStaStaffID(rollnumber1);
					staffMB.staffedit();
					return "staffAboutpage";
				}
				if (status.equalsIgnoreCase("Librarian")) {
					logger.info("-----------Librarian---------");
					rollnumber1 = controller.getStaffRollNumber(personID, loginaccess);
					librarianViewMB.librarianDataBean.setLibID(rollnumber1);
					librarianViewMB.edit();
					return "librarianAboutpage";
				}
				if (status.equalsIgnoreCase("BookShop")) {
					logger.info("inside book shop About Me");
					logger.info("-----------BookShop---------");
					rollnumber1 = controller.getStaffRollNumber(personID, loginaccess);
					staffMB.staffDataBean.setStaStaffID(rollnumber1);
					staffMB.staffedit();
					logger.info("rollnumber1"+rollnumber1);
					return "bookshopAboutpage";
				}


			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}

		return "";
	}

	/**
	 * @return the eventModel
	 */
	public ScheduleModel getEventModel() {
		return eventModel;
	}

	/**
	 * @param eventModel
	 *            the eventModel to set
	 */
	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public void scheduleCalender() {
		logger.info("-----------Inside scheduleCalender method()----------------");
		//logger.debug("Init------");
		examList = null;
		timetableList = null;
		
		setNoticeList(null);
		try {
			eventModel = new DefaultScheduleModel();
			examList = new ArrayList<ExamTable>();
			timetableList = new ArrayList<TimeTable>();
			setNoticeList(new ArrayList<NoticeBoardDataBean>());
			

			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {

				lazyEventModel = new LazyScheduleModel() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void loadEvents(Date start, Date end) {
						
						String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");						
						String roll = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
								.get("Role");
						int school_id=Integer.parseInt(schoolID);
						SmsController controller = null;
						ApplicationContext ctx = FacesContextUtils
								.getWebApplicationContext(FacesContext.getCurrentInstance());
						controller = (SmsController) ctx.getBean("controller");
						String personID = (String) FacesContext.getCurrentInstance().getExternalContext()
								.getSessionMap().get("LogID");
						examList = controller.getTimeTableList(personID);
						logger.debug("exam list size-------"+examList.size());
						/*if (examList.size() > 0) {
							int result = examList.size();
							int examID = 0;
							for (int i = 0; i < result; i++) {
								examID = examList.get(i).getExam_table_ID();
								if(examList.get(i).getStartDate() == null){
									logger.debug("Inside Null");
								}else{
									logger.debug("---------->>>>>>>>>>" + examID);
									timetableList = controller.getExamTableList(personID, examID);
									logger.debug(timetableList.size());
									if (timetableList.size() > 0) {
										int result1 = timetableList.size();
										for (int j = 0; j < result1; j++) {
											logger.debug("Google check------->" + timetableList.get(j).getExamDay());
												addEvent(new DefaultScheduleEvent(
														examList.get(i).getExamTitle() + " " + timetableList.get(j).getSubject()
																+ "/" + timetableList.get(j).getSubjectCode(),
														startDate(timetableList.get(j).getExamDate()),
														startDate(timetableList.get(j).getExamDate())));
											}

										}
									}
								
								}
							}*/
						noticeList = controller.getNoticeBoardView(personID);
						logger.debug("Size----------->>>>>>" + noticeList.size());
						if (noticeList.size() > 0) {
							for (int j = 0; j < noticeList.size(); j++) {

								logger.debug("<<<<<<<<<<<<>>>>>>>>>>>> " + noticeList.get(j).getFromdate());
								if (noticeList.get(j).getFromdate() == null && noticeList.get(j).getTodate() == null) {
									logger.debug("Inside Null");
								} else {
									addEvent(new DefaultScheduleEvent(noticeList.get(j).getNoticeSubject(),
										startDate(noticeList.get(j).getFromdate()),
										startDate(noticeList.get(j).getTodate())));
								}
							}
						}
						homeworkList=controller.getHomework(personID);
						logger.debug("homeworkList size----------->>>>>>" + homeworkList.size());
						if(homeworkList.size() > 0){
							for (int i = 0; i < homeworkList.size(); i++) {
								if(homeworkList.get(i).getDate() == null)
								{
									logger.debug("Inside Null");
								}
								else
								{
									logger.debug("inside else homewrk");
									if((school_id==1) || (school_id == 2) || (school_id == 3) || (school_id == 4) || (school_id == 5) || (school_id == 6)){
										String hmWork=homeworkList.get(i).getSubject()+":\n"+homeworkList.get(i).getHomework();
										addEvent(new DefaultScheduleEvent(hmWork,
												startDate(homeworkList.get(i).getDate()),startDate(homeworkList.get(i).getDate())));
									}
									else{
										if(roll.equalsIgnoreCase("Teacher")){
											String hmWork="Home Work  :  "+homeworkList.get(i).getClassname();
											addEvent(new DefaultScheduleEvent(hmWork,
													startDate(homeworkList.get(i).getDate()),startDate(homeworkList.get(i).getDate())));
											
										}else if(roll.equalsIgnoreCase("Parent") || roll.equalsIgnoreCase("Student")){
											String hmWork="Home Work  :  "+homeworkList.get(i).getSubject();
											addEvent(new DefaultScheduleEvent(hmWork,
													startDate(homeworkList.get(i).getDate()),startDate(homeworkList.get(i).getDate())));
										}
									}
								}
							}
							 
						}
					}
				};

			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			//e.printStackTrace();
		}
	}

	private Date startDate(Date examDate) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(examDate);

		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}

	public Date getRandomDate(Date base) {
		Calendar date = Calendar.getInstance();
		date.setTime(base);
		date.add(Calendar.DATE, ((int) (Math.random() * 30)) + 1); // set random
																	// day of
																	// month

		return date.getTime();
	}

	public Date getInitialDate() throws ParseException {
		logger.info("-----------Inside getInitialDate method()----------------");
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}

	public Date InitialDate() throws ParseException {
		logger.info("-----------Inside InitialDate method()----------------");
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}
	

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public void addEvent(ActionEvent actionEvent) {
		if (event.getId() == null)
			eventModel.addEvent(event);
		else
			eventModel.updateEvent(event);

		event = new DefaultScheduleEvent();
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
	}

	public void onDateSelect(SelectEvent selectEvent) {
		event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
	}

	public void onEventMove(ScheduleEntryMoveEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved",
				"Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

		addMessage(message);
	}

	public void onEventResize(ScheduleEntryResizeEvent event) {
		logger.info("-----------Inside onEventResize method()----------------");
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized",
				"Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

		addMessage(message);
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * @return the lazyEventModel
	 */
	public ScheduleModel getLazyEventModel() {
		return lazyEventModel;
	}

	/**
	 * @param lazyEventModel
	 *            the lazyEventModel to set
	 */
	public void setLazyEventModel(ScheduleModel lazyEventModel) {
		this.lazyEventModel = lazyEventModel;
	}

	/**
	 * @return the examList
	 */
	public List<ExamTable> getExamList() {
		return examList;
	}

	/**
	 * @param examList
	 *            the examList to set
	 */
	public void setExamList(List<ExamTable> examList) {
		this.examList = examList;
	}

	/**
	 * @return the noticeList
	 */
	public List<NoticeBoardDataBean> getNoticeList() {
		return noticeList;
	}

	/**
	 * @param noticeList
	 *            the noticeList to set
	 */
	public void setNoticeList(List<NoticeBoardDataBean> noticeList) {
		this.noticeList = noticeList;
	}
	
	private String bookflag="none";
	private String bookflag1="none";

	

	public String getBookflag() {
		return bookflag;
	}

	public void setBookflag(String bookflag) {
		this.bookflag = bookflag;
	}

	public String getBookflag1() {
		return bookflag1;
	}

	public void setBookflag1(String bookflag1) {
		this.bookflag1 = bookflag1;
	}

	public String dashboard() {
		logger.info("-----------Inside dashboard method()----------------");
		String successstatus = "";
		scheduleCalender();
		classTimetable();
		String roll = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role");
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		int school_ID=Integer.parseInt(schoolID);
		if (roll.equalsIgnoreCase("Admin")) {
			/*
			 * setScheduleFlag(true); setScheduleFlag1(false);
			 */
			setScheduleFlag(false);
			setScheduleFlag1(true);
			successstatus = "sucess";
		} else if (roll.equalsIgnoreCase("Parent") || roll.equalsIgnoreCase("Student")) {
			setScheduleFlag(false);
			setScheduleFlag1(true);
			successstatus = "classDashboard2";
		} else if (roll.equalsIgnoreCase("Teacher")) {
			setScheduleFlag(false);
			setScheduleFlag1(true);
			successstatus = "classDashboard";
		} else if (roll.equalsIgnoreCase("BookShop")) {
			setScheduleFlag(false);
			setScheduleFlag1(true);
			if(school_ID==1 || school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5 || school_ID==6){
				bookflag="1";bookflag1="none";
			}
			else{
				bookflag="none";bookflag1="1";
			}
			successstatus = "bookshopdashboard3";
		} else if (roll.equalsIgnoreCase("Staff")) {
			setScheduleFlag(false);
			setScheduleFlag1(true);
			successstatus = "staffdashboard3";
		} else if (roll.equalsIgnoreCase("Librarian")) {
			setScheduleFlag(false);
			setScheduleFlag1(true);
			if(school_ID==1 || school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5 || school_ID==6){
				bookflag="1";bookflag1="none";
			}
			else{
				bookflag="none";bookflag1="1";
			}
			successstatus = "libdashboard4";
		}

		return successstatus;
	}

	public String redirect() {
		logger.info("-----------Inside redirect method()----------------");
		String successstatus = "myNewLogin1";
		try {
			scheduleCalender();
			classTimetable();
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int school_ID=Integer.parseInt(schoolID);
			String roll = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role");
			if (roll.equalsIgnoreCase("Admin")) {
				/*
				 * setScheduleFlag(true); setScheduleFlag1(false);
				 */
				setScheduleFlag(false);
				setScheduleFlag1(true);
				successstatus = "sucess";
			} else if (roll.equalsIgnoreCase("Parent") || roll.equalsIgnoreCase("Student")) {
				setScheduleFlag(false);
				setScheduleFlag1(true);
				successstatus = "classDashboard2";
			} else if (roll.equalsIgnoreCase("Teacher")) {
				setScheduleFlag(false);
				setScheduleFlag1(true);
				successstatus = "classDashboard";
			} else if (roll.equalsIgnoreCase("BookShop")) {
				setScheduleFlag(false);
				setScheduleFlag1(true);
				if(school_ID==1 || school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5 || school_ID==6){
					bookflag="1";bookflag1="none";
				}
				else{
					bookflag="none";bookflag1="1";
				}
				successstatus = "bookshopdashboard3";
			}else if (roll.equalsIgnoreCase("Staff")) {
				setScheduleFlag(false);
				setScheduleFlag1(true);
					successstatus = "staffdashboard3";
			} else if (roll.equalsIgnoreCase("Librarian")) {
				setScheduleFlag(false);
				setScheduleFlag1(true);
				if(school_ID==1 || school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5 || school_ID==6){
					bookflag="1";bookflag1="none";
				}
				else{
					bookflag="none";bookflag1="1";
				}
				successstatus = "libdashboard4";
			}
			return successstatus;
		} catch (NullPointerException e) {
			return "myNewLogin1";
			//logger.warn("Exception --> "+e.getMessage());
			

		}
		

	}

	public void forgot() {
		logger.info("-----------Inside forgot method()----------------");
		try {
		logger.debug("Inside");
		loginaccess.setUsername("");
		/* return "goForgetpassword"; */
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("draggable", false);
		options.put("resizable", false);
		options.put("contentHeight", 320);
		RequestContext.getCurrentInstance().openDialog("Forgetpassword", options, null);
		}
		catch(Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		}
	// forgot password method to recover password
	//RAGULAN MODIFY 04-10-2016
	public String forgotpass() {
		logger.info("-----------Inside forgotpass method()----------------");
		loginaccess.setOTP("");
		SmsController controller = null;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			logger.debug(">>>>>>>>>>" + loginaccess.getUsername());
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			if (loginaccess.getUsername() == null || loginaccess.getUsername().equalsIgnoreCase("")) {
				fieldName = CommonValidate.findComponentInRoot("uName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.username.blank").toString()));

			} else {
				String mailid = controller.forgot(loginaccess);
				logger.debug(mailid);
				if (mailid.equalsIgnoreCase("null")) {
					fieldName = CommonValidate.findComponentInRoot("uName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Invalid UserName"));
				} else {
				String emailStatus=MailSendJDBC.insertotp(loginaccess,mailid);
				logger.debug("email status-----"+emailStatus+mailid);
					//String emailStatus = sendEmail(mailid, loginaccess.getTemOTP());
					logger.debug("successfully get mailid and set OTP in database " + mailid + loginaccess.getTemOTP());
					logger.debug("emailstatus" + emailStatus);
					if (emailStatus.equalsIgnoreCase("success")) {

						return "goOTP";
					}
				}

			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		finally {
			
		}

		return "";
	}

	
	public String otpVerify() {
		logger.info("-----------Inside otpVerify method()----------------");
		SmsController controller = null;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {

			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			if (loginaccess.getOTP() == null || loginaccess.getOTP().equalsIgnoreCase("")) {
				fieldName = CommonValidate.findComponentInRoot("otp").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the OTP"));

			} else {
				String status = controller.otpvarify(loginaccess.getOTP(), loginaccess.getUsername());

				if (status.equals(loginaccess.getOTP())) {
					logger.debug("OTP verified successfully");
					return "goRecoverpassword";
				} else {
					fieldName = CommonValidate.findComponentInRoot("otp").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Invalid OTP"));
				}
			}
		} catch (Exception e) {
logger.error("Exception -->"+e.getMessage());
		}

		return "";
	}

	public String resetPassword() {
		logger.info("-----------Inside resetPassword method()----------------");
		SmsController controller = null;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
	
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");

			if (StringUtils.isEmpty(loginaccess.getNewPassword())) {
				fieldName = CommonValidate.findComponentInRoot("pwd").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the New Password"));
			}
			if (StringUtils.isEmpty(loginaccess.getConfirmPassword())) {
				fieldName = CommonValidate.findComponentInRoot("pwd1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter Confirm Password"));
			}
			if (!StringUtils.isEmpty(loginaccess.getNewPassword())
					&& !StringUtils.isEmpty(loginaccess.getConfirmPassword())) {

				if (loginaccess.getNewPassword().equals(loginaccess.getConfirmPassword())) {
					String status = controller.setNewpassword(loginaccess.getNewPassword(), loginaccess.getUsername());
					if (status.equalsIgnoreCase("Success")) {
						logger.debug("Password chenged successfully");
						RequestContext.getCurrentInstance().closeDialog("Forgetpassword");
					} else {
						return "";
					}
				} else {
					fieldName = CommonValidate.findComponentInRoot("incorrrect").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Password doesn't Match"));
				}
			}

		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return "";
	}
	
	//RAGULAN
	public String schoolregister(){
		logger.info("-----------Inside schoolregister method()----------------");
		loginaccess.setSchoolName("");
		loginaccess.setSchoolPhone("");
		loginaccess.setSchoolEmail("");
		loginaccess.setSchoolAddress("");
		loginaccess.setUsername("");
		loginaccess.setAlternativeemail("");
		//loginaccess.setSchoollogo(null);
		loginaccess.setLandlinenumber("");
		loginaccess.setContactnumber("");
		loginaccess.setCountry("");
		loginaccess.setState("");
		loginaccess.setLoginusername("");
		return"";
	}
	public void cancel(){
		logger.info("-----------Inside cancel method()----------------");
		loginaccess.setSchoolName("");
		loginaccess.setSchoolPhone("");
		loginaccess.setSchoolEmail("");
		loginaccess.setSchoolAddress("");
		loginaccess.setUsername("");
		loginaccess.setAlternativeemail("");
		//loginaccess.setSchoollogo(null);
		loginaccess.setLandlinenumber("");
		loginaccess.setContactnumber("");
		loginaccess.setCountry("");
		loginaccess.setState("");
		loginaccess.setLoginusername("");
	}
	public String addschool(){
		logger.info("-----------Inside addschool method()----------------");
		SmsController controller=null;
		String name;
		FacesContext fc=FacesContext.getCurrentInstance();
		try{
			if(addschoolvalidatioin(true)){
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");	
				/*String filename=loginaccess.getSchoollogo().getContentType();
				String type = filename.substring(filename.lastIndexOf("/") + 1);
				copyfile(loginaccess.getSchoolName(),loginaccess.getSchoollogo().getInputstream(),type);
				loginaccess.setFilepath(paths.getString("sms.school.logo")+loginaccess.getSchoolName()+"."+type);*/
				String status=controller.insertschool(loginaccess);
				if(status.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("PF('reg').hide()");
					RequestContext.getCurrentInstance().execute("PF('confirm').show()");
				}else if(status.equalsIgnoreCase("Exist")){
				name=CommonValidate.findComponentInRoot("name").getClientId(fc);	
				fc.addMessage(name, new FacesMessage("The School Name Already Exists"));
				}
			}
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		}
		return"";
	}

	/*private void copyfile(String schoolName, InputStream inputstream,String type) {
		logger.info("-----------Inside copyfile method()----------------");
		try {
			File files = new File(paths.getString("sms.school.logo"));
			if (!files.exists()) {
				files.mkdirs();
			} 
			OutputStream out = new FileOutputStream(new File(files + paths.getString("path_context").toString() + schoolName + "." + type));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputstream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			inputstream.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.warn("Exception -->"+e.getMessage());
		}

	}
*/
	private boolean addschoolvalidatioin(boolean valid) {
		logger.info("-----------Inside addschoolvalidation method()----------------");
		valid=true;
		String name;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(StringUtils.isEmpty(loginaccess.getSchoolName())){
			name=CommonValidate.findComponentInRoot("schoolname").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter The School Name"));
			valid=false;
		}if(StringUtils.isEmpty(loginaccess.getLoginusername())){
			name=CommonValidate.findComponentInRoot("loginusername").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter The User Name"));
			valid=false;
		}if(StringUtils.isEmpty(loginaccess.getUsername())){
			name=CommonValidate.findComponentInRoot("username").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter The Contact Person Name"));
			valid=false;
		}
		if(StringUtils.isEmpty(loginaccess.getSchoolPhone())){
			name=CommonValidate.findComponentInRoot("schoolphone").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter The Phone Number"));
			valid=false;
		}else if(!StringUtils.isEmpty(loginaccess.getSchoolPhone())){
			if(!CommonValidate.validateNumber(loginaccess.getSchoolPhone())){
				name=CommonValidate.findComponentInRoot("schoolphone").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Enter The Valid Phone Number"));
				valid=false;
			}
		}if(StringUtils.isEmpty(loginaccess.getSchoolEmail())){
			name=CommonValidate.findComponentInRoot("schoolemail").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter The Email"));
			valid=false;
		}else if(!StringUtils.isEmpty(loginaccess.getSchoolEmail())){
			if(!CommonValidate.validateEmail(loginaccess.getSchoolEmail())){
				name=CommonValidate.findComponentInRoot("schoolemail").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Enter The Valid Email"));
				valid=false;
			}
		}
		if(StringUtils.isEmpty(loginaccess.getSchoolAddress())){
			name=CommonValidate.findComponentInRoot("address").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter The Address"));
			valid=false;
		}
		if(!StringUtils.isEmpty(loginaccess.getAlternativeemail())){
			if(!CommonValidate.validateEmail(loginaccess.getAlternativeemail())){
				name=CommonValidate.findComponentInRoot("alteremail").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Enter The Valid Email"));
				valid=false;	
			}
		}
		if(StringUtils.isEmpty(loginaccess.getLandlinenumber())){
			name=CommonValidate.findComponentInRoot("landline").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter Landline Number"));
			valid=false;
		}else if(!StringUtils.isEmpty(loginaccess.getLandlinenumber())){
			if(!CommonValidate.validateNumber(loginaccess.getLandlinenumber())){
				name=CommonValidate.findComponentInRoot("landline").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Enter The Valid Landline number"));
				valid=false;
			}
		}
		if(StringUtils.isEmpty(loginaccess.getContactnumber())){
			name=CommonValidate.findComponentInRoot("contact").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter Landline Number"));
			valid=false;
		}else if(!StringUtils.isEmpty(loginaccess.getContactnumber())){
			if(!CommonValidate.validateNumber(loginaccess.getContactnumber())){
				name=CommonValidate.findComponentInRoot("contact").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Enter The Valid Contact number"));
				valid=false;
			}
		}
		if(StringUtils.isEmpty(loginaccess.getCountry())){
			name=CommonValidate.findComponentInRoot("schoolcountry").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter the country"));
			valid=false;
		}
		if(StringUtils.isEmpty(loginaccess.getState())){
			name=CommonValidate.findComponentInRoot("state").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter the State"));
			valid=false;
		}
		/*if(loginaccess.getSchoollogo()==null){
			name=CommonValidate.findComponentInRoot("logo").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please upload the Logo"));
			valid=false;
		}*/
		return valid;
	}
	
	private void newschoolview(){
		logger.info("-----------Inside newschoolview method()----------------");
		SmsController controller=null;
		try{
			addschoollist=new ArrayList<LoginAccess>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");	
			addschoollist=controller.getSchoolsList(loginaccess);
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		}
		finally
		{
			controller=null;
		}
	}
	
	public String newschoolapproval(){
		logger.info("-----------Inside newschoolapproval method()----------------");
		SmsController controller=null;
		String approvalstatus="";
		try{
			//String personID = loginaccess.getPersonID();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");	
			approvalstatus=controller.newschoolapproval(loginaccess);
			if(approvalstatus.equalsIgnoreCase("Success")){
				RequestContext.getCurrentInstance().execute("PF('confirm').show()");
				newschoolview();
			}else if(approvalstatus.equalsIgnoreCase("Approved")){
				MailSendJDBC.insertschool(loginaccess);	
				RequestContext.getCurrentInstance().execute("PF('confirm').show()");
				newschoolview();
			}
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}
	
	public String newschoolreject(){
		logger.info("-----------Inside newschoolreject method()----------------");
		logger.debug("inside reject method calling----");
		SmsController controller=null;
		String rejectstatus="";
		try{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");	
			rejectstatus=controller.newschoolreject(loginaccess);
			if(rejectstatus.equalsIgnoreCase("Success")){
				RequestContext.getCurrentInstance().execute("PF('reject').show()");
				newschoolview();
			}
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		}
		return"";
	}

		
     
		@PostConstruct
	    private void init() {
			logger.info("-----------Inside init method()----------------");
			try{
				themes=new ArrayList<LoginAccess>();
		        themes.add(new LoginAccess( "Afterdark", "afterdark"));
		        themes.add(new LoginAccess( "Afternoon", "afternoon"));
		        themes.add(new LoginAccess( "Afterwork", "afterwork"));
		        themes.add(new LoginAccess("Aristo", "aristo"));
		        themes.add(new LoginAccess("Black-Tie", "black-tie"));
		        themes.add(new LoginAccess("Blitzer", "blitzer"));
		        themes.add(new LoginAccess("Bluesky", "bluesky"));
		        themes.add(new LoginAccess("Bootstrap", "bootstrap"));
		        themes.add(new LoginAccess("Casablanca", "casablanca"));
		        themes.add(new LoginAccess("Cupertino", "cupertino"));
		        themes.add(new LoginAccess("Cruze", "cruze"));
		        themes.add(new LoginAccess("Dark-Hive", "dark-hive"));
		        themes.add(new LoginAccess("Delta", "delta"));
		        themes.add(new LoginAccess("Dot-Luv", "dot-luv"));
		        themes.add(new LoginAccess("Eggplant", "eggplant"));
		        themes.add(new LoginAccess("Excite-Bike", "excite-bike"));
		        themes.add(new LoginAccess("Flick", "flick"));
		        themes.add(new LoginAccess("Glass-X", "glass-x"));
		        themes.add(new LoginAccess("Home", "home"));
		        themes.add(new LoginAccess("Hot-Sneaks", "hot-sneaks"));
		        themes.add(new LoginAccess("Humanity", "humanity"));
		        themes.add(new LoginAccess("Le-Frog", "le-frog"));
		        themes.add(new LoginAccess("Midnight", "midnight"));
		        themes.add(new LoginAccess("Mint-Choc", "mint-choc"));
		        themes.add(new LoginAccess("Overcast", "overcast"));
		        themes.add(new LoginAccess("Pepper-Grinder", "pepper-grinder"));
		        themes.add(new LoginAccess("Redmond", "redmond"));
		        themes.add(new LoginAccess("Rocket", "rocket"));
		        themes.add(new LoginAccess("Sam", "sam"));
		        themes.add(new LoginAccess("Smoothness", "smoothness"));
		        themes.add(new LoginAccess("South-Street", "south-street"));
		        themes.add(new LoginAccess("Start", "start"));
		        themes.add(new LoginAccess("Sunny", "sunny"));
		        themes.add(new LoginAccess("Swanky-Purse", "swanky-purse"));
		        themes.add(new LoginAccess("Trontastic", "trontastic"));
		        themes.add(new LoginAccess("UI-Darkness", "ui-darkness"));
		        themes.add(new LoginAccess("UI-Lightness", "ui-lightness"));
		        themes.add(new LoginAccess("Vader", "vader"));
			}catch(Exception q){
				//q.printStackTrace();
				logger.error("Exception -->"+q.getMessage());
			}
		}
		
	    public void saveTheme(AjaxBehaviorEvent ajax)
	    {
	    setThemename((String) ((ThemeSwitcher)ajax.getSource()).getValue());
	    setBackground((String)((ThemeSwitcher)ajax.getSource()).getValue());
	    }  
	    
	public void fileupload(FileUploadEvent event) throws IOException{
		logger.info("-----------Inside fileupload method()----------------");
		this.file=event.getFile();
		//loginaccess.setSchoollogo(event.getFile());
		FacesMessage message = new FacesMessage("Succesfully uploaded");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public void imageview(OutputStream out, Object data) throws IOException {
		logger.info("-----------Inside imageview method()----------------");
		BufferedImage img = ImageIO.read(new File(loginaccess.getFilepath()));
		ImageIO.write(img, "png", out);
	}
	
	public void imageview1(OutputStream out, Object data) throws IOException {
		logger.info("-----------Inside imageview1 method()----------------");
		BufferedImage img = ImageIO.read(new File(loginaccess.getFilepath()));
		ImageIO.write(img, "png", out);
	}
	public String schooledit(){
		logger.info("-----------Inside schooledit method()----------------");
		SmsController controller=null;
		try{
			schooleditlist=new ArrayList<School>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");	
			schooleditlist=controller.schooledit(loginaccess);
			if(schooleditlist.size()>0){
				loginaccess.setSchoolName(schooleditlist.get(0).getName());
				loginaccess.setSchoolAddress(schooleditlist.get(0).getAddress());
				loginaccess.setSchoolEmail(schooleditlist.get(0).getEmailId());
				loginaccess.setAlternativeemail(schooleditlist.get(0).getAlternativeemail());
				loginaccess.setSchoolPhone(schooleditlist.get(0).getPhoneNumber());
				loginaccess.setLandlinenumber(schooleditlist.get(0).getLandlinenumber());
				loginaccess.setContactnumber(schooleditlist.get(0).getMobilenumber());
				loginaccess.setUsername(schooleditlist.get(0).getContactpersonname());
				loginaccess.setState(schooleditlist.get(0).getState());
				loginaccess.setCountry(schooleditlist.get(0).getCountry());
				loginaccess.setFilepath(schooleditlist.get(0).getLogopath());
			}
		}catch(Exception e){
			logger.warn("Exception - "+e.getMessage());
		}
		return "";
	}
	
	public String schoolupdate(){
		logger.info("-----------Inside schoolupdate method()----------------");
		SmsController controller=null;
		String editstatus="";
		try{
			if(schooleditvalidatioin(true)){
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				/*if(loginaccess.getSchoollogo()!=null){
				String filename=loginaccess.getSchoollogo().getContentType();
				String type = filename.substring(filename.lastIndexOf("/") + 1);
				copyfile(loginaccess.getSchoolName(),loginaccess.getSchoollogo().getInputstream(),type);
				loginaccess.setFilepath(paths.getString("sms.school.logo")+loginaccess.getSchoolName()+"."+type);
				}*/
				if(loginaccess.getSchoolPhone().startsWith(text.getString("sms.phno"))){
					loginaccess.setSchoolPhone(loginaccess.getSchoolPhone());
				}else if(loginaccess.getSchoolPhone().startsWith("0")){
					String phoneno=loginaccess.getSchoolPhone();
					String phno=phoneno.substring(1);
					loginaccess.setSchoolPhone(text.getString("sms.phno")+phno);
				}else{
					loginaccess.setSchoolPhone(text.getString("sms.phno")+loginaccess.getSchoolPhone());
				}
				editstatus=controller.schoolupdate(loginaccess);
				if(editstatus.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("PF('editconfirm').show()");
				}
			}
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		}
		return"";
	}

	private boolean schooleditvalidatioin(boolean valid) {
		logger.info("-----------Inside schooleditvalidation method()----------------");
		valid=true;
		String name;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(StringUtils.isEmpty(loginaccess.getUsername())){
			name=CommonValidate.findComponentInRoot("username").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter The User Name"));
			valid=false;
		}
		if(StringUtils.isEmpty(loginaccess.getSchoolPhone())){
			name=CommonValidate.findComponentInRoot("phone").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter The Phone Number"));
			valid=false;
		}else if(!StringUtils.isEmpty(loginaccess.getSchoolPhone())){
			if(!CommonValidate.validateNumber(loginaccess.getSchoolPhone())){
				name=CommonValidate.findComponentInRoot("phone").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Enter The Valid Phone Number"));
				valid=false;
			}
		}if(StringUtils.isEmpty(loginaccess.getSchoolEmail())){
			name=CommonValidate.findComponentInRoot("email").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter The Email"));
			valid=false;
		}else if(!StringUtils.isEmpty(loginaccess.getSchoolEmail())){
			if(!CommonValidate.validateEmail(loginaccess.getSchoolEmail())){
				name=CommonValidate.findComponentInRoot("email").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Enter The Valid Email"));
				valid=false;
			}
		}
		if(StringUtils.isEmpty(loginaccess.getSchoolAddress())){
			name=CommonValidate.findComponentInRoot("address").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter The Address"));
			valid=false;
		}
		if(!StringUtils.isEmpty(loginaccess.getAlternativeemail())){
			if(!CommonValidate.validateEmail(loginaccess.getAlternativeemail())){
				name=CommonValidate.findComponentInRoot("alteremail").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Enter The Valid Email"));
				valid=false;	
			}
		}
		if(StringUtils.isEmpty(loginaccess.getLandlinenumber())){
			name=CommonValidate.findComponentInRoot("landline").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter Landline Number"));
			valid=false;
		}else if(!StringUtils.isEmpty(loginaccess.getLandlinenumber())){
			if(!CommonValidate.validateNumber(loginaccess.getLandlinenumber())){
				name=CommonValidate.findComponentInRoot("landline").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Enter The Valid Landline number"));
				valid=false;
			}
		}
		if(StringUtils.isEmpty(loginaccess.getContactnumber())){
			name=CommonValidate.findComponentInRoot("contact").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter Contact Number"));
			valid=false;
		}else if(!StringUtils.isEmpty(loginaccess.getContactnumber())){
			if(!CommonValidate.validateNumber(loginaccess.getContactnumber())){
				name=CommonValidate.findComponentInRoot("contact").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Enter The Valid Contact number"));
				valid=false;
			}
		}
		if(StringUtils.isEmpty(loginaccess.getCountry())){
			name=CommonValidate.findComponentInRoot("country").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter the country"));
			valid=false;
		}
		if(StringUtils.isEmpty(loginaccess.getState())){
			name=CommonValidate.findComponentInRoot("state").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter the State"));
			valid=false;
		}
		return valid;
	}
	
	public void timetableChange(ValueChangeEvent v) 
	{
		logger.info("-----------Inside timetableChange method()----------------");
		logger.debug("inside valuechange");
		SmsController controller = null;
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (SmsController) ctx.getBean("controller");
		try{
			String studid=v.getNewValue().toString();
			String student = studid;
			String studentid = student.substring(student.lastIndexOf("/") + 1);
			studentDataBean.setStuStudentId(studentid);
			String personID=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID").toString();
			classtimetablelist = controller.getstudentclassTimeTable(personID,studentDataBean);
			logger.debug("classtimetablelist  "+classtimetablelist);
			if (classtimetablelist == null) {

			} else {
				if (classtimetablelist.size() > 0) {
					flag1 = true;
					int c = 0;
					int c1 = 0;
					for (int i = 0; i < classtimetablelist.size(); i++) {
						try {
							if (!classtimetablelist.get(i).getExSubject().equalsIgnoreCase("")) {
								c++;
							}
						} catch (Exception e) {
							logger.warn("Exception -->"+e.getMessage());

						}
						try {
							if (!classtimetablelist.get(i).getExSubject1().equalsIgnoreCase("")) {
								c++;
							}
						} catch (Exception e) {
							logger.warn("Exception -->"+e.getMessage());

						}
					}
					if (c > 0) {
						for (int i = 0; i < 6; i++) {
							try {
								if (classtimetablelist.get(i).getExSubject().equals("")) {
								}
							} catch (Exception e) {
								classtimetablelist.get(i).setExSubject("-");
								logger.warn("Exception -->"+e.getMessage());

							}
						}
						exflag = true;
					}
					if (c1 > 0) {
						for (int i = 0; i < 6; i++) {
							try {
								if (classtimetablelist.get(i).getExSubject1().equals("")) {
								}
							} catch (Exception e) {
								classtimetablelist.get(i).setExSubject1("-");
								logger.warn("Exception -->"+e.getMessage());

							}
						}
						exflag1 = true;
					}
				}
			}
		}
		catch(Exception e)
		{
			logger.warn("Exception -->"+e.getMessage());
		}
	}
	
	
	public List<LoginAccess> searchvalue(String val){
		logger.info("-----------Inside searchvalue method()----------------");
		SmsController controller = null;
		
		try{
			searchlist=new ArrayList<LoginAccess>();
			filtersearchlist=new ArrayList<LoginAccess>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			ExternalContext externalcontext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalcontext.getSessionMap();
			sessionMap.put("schoolID", loginaccess.getSchoolID());
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			searchlist=controller.searchvalues(schoolID);
			//("searchlist === "+searchlist.size());
			//("UI Value ---> "+val);
			for (int i = 0; i < searchlist.size(); i++) {
				if(searchlist.get(i).getValue1() != null)
				{
					 LoginAccess login=searchlist.get(i);
				     if(login.getValue1().toLowerCase().startsWith(val)|| login.getValue1().startsWith(val)) {
				    	 filtersearchlist.add(login);
				     }
				}
				
			}
			
			if(filtersearchlist.size()==0)
			{
				LoginAccess login=new LoginAccess();
			     login.setLableValue("---No Data found---");
		    	 filtersearchlist.add(login);
			     

			}
			//("List value size -->"+filtersearchlist.size());
			
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		}
		return filtersearchlist;
	}
	
	public void selectListener(SelectEvent event){
		logger.info("-----------Inside selectListener method()----------------");
		 
		  loginaccess.setGlobalValue("");
		  try{
		   Object value=event.getObject();
		   //("------value-----"+value.toString());
		   loginaccess.setGlobalValue(value.toString());
		   goGlobalSearch();
		  }catch(Exception e){
				logger.warn("Exception -->"+e.getMessage());
		  }
		}
	
	public String goGlobalSearch(){
		logger.info("-----------Inside goGlobalSearch method()----------------");
		  try{
			  //String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			 // String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			  //("global value "+loginaccess.getGlobalValue());
			  loginaccess.setValue1(loginaccess.getGlobalValue().split("/")[0]);
			  loginaccess.setModuleName(loginaccess.getGlobalValue().split("/")[1]);
			  loginaccess.setP_id(loginaccess.getGlobalValue().split("/")[2]);
			  loginaccess.setPersonID(loginaccess.getGlobalValue().split("/")[3]);
			  
			  //("module name ====== "+loginaccess.getModuleName());
			  if(loginaccess.getModuleName().equalsIgnoreCase("Student")){
				  studentDataBean.setPersonID(loginaccess.getGlobalValue().split("/")[3]);
				  studentDataBean.setStuRollNo(loginaccess.getGlobalValue().split("/")[4]);
				  studentViewMB.globalstudentpage(studentDataBean);
			  }
			else if(loginaccess.getModuleName().equalsIgnoreCase("Teacher")){
				  teacherViewMB.globalEdit(loginaccess.getGlobalValue());
			  }
		  }catch(Exception e){
				logger.warn("Exception -->"+e.getMessage());
		  }
		  return "";
		 }
	
	public String viewUserpage() {
		logger.info("-----------Inside viewUserpage method()----------------");
		loginaccess.setUser("");
		userDetailList=null;
		loginaccess.setUserdet_flag(false);
		return "gotoviewUserdetails";
	}


	
	public String getUserDetails(){
		logger.info("-----------Inside getUserDetails method()----------------");
		SmsController controller = null;
		loginaccess.setUserdet_flag(false);
		try{
			loginaccess.setSelectUserlist(new ArrayList<LoginAccess>());
			userDetailList=new ArrayList<LoginAccess>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			userDetailList=controller.getUserDetails(loginaccess);
			logger.debug("------------userDetailList------------"+userDetailList.size());
			if(userDetailList.size()>0){
				setNumberofUser(String.valueOf(userDetailList.size()));
				loginaccess.setUserdet_flag(true);
			}
			else 
			{
				logger.debug("No user details found on seclected Group");
			}
		}catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}
	
	public String sendUserDetail(){
		logger.info("-----------Inside sendUserDetail method()----------------");
		String pdfstatus="";String mailstatus="";
		try{
			pdfstatus=generatePDF();
			if(pdfstatus.equalsIgnoreCase("success")){
				mailstatus=MailSendJDBC.userDetailmail(loginaccess);
				//("------mailstatus---------"+mailstatus);
				if(mailstatus.equalsIgnoreCase("success")){
					RequestContext reqcontext = RequestContext.getCurrentInstance();
					reqcontext.execute("PF('emaildlg').show();");
				}
			}
		}catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}
	
	public String generatePDF(){
		logger.info("-----------Inside generatePDF method()----------------");
		String status="";
		try{
			String header[]={"Username","Password","Email ID"};
			 Document documento = new Document();
			// Create Directory
				File files = new File(paths.getString("sms.user") + ft.format(todaydate));
				if (!files.exists()) {
					files.mkdirs();
				} else {
					logger.debug("Alreday Found");
				}
			    //Create new File
				OutputStream file = new FileOutputStream(
						new File(files + paths.getString("path_context").toString()+loginaccess.getSchoolName() + ".pdf"));
				loginaccess.setFilepath(files + paths.getString("path_context").toString() + loginaccess.getSchoolName() + ".pdf");
			   /* File file = new File("D:/file.pdf");
			    file.createNewFile();
			    FileOutputStream fop = new FileOutputStream(file);*/
			    PdfWriter.getInstance(documento, file);
			    documento.open(); 
			    //Fonts
			    Font fontHeader = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
			    Font fontBody = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL);
			    //Table for header
			    PdfPTable cabetabla = new PdfPTable(header.length);
			    for (int j = 0; j < header.length; j++) {
			        Phrase frase = new Phrase(header[j], fontHeader);
			        PdfPCell cell = new PdfPCell(frase);
			        cell.setBackgroundColor(new BaseColor(Color.lightGray.getRGB()));
			        cabetabla.addCell(cell);
			    }
			    documento.add(cabetabla);
			    //Tabla for body
			    PdfPTable tabla = new PdfPTable(header.length);
			    for (int i = 0; i < userDetailList.size(); i++) {
			       
			            tabla.addCell(new Phrase(userDetailList.get(i).getUsername(), fontBody));
			            tabla.addCell(new Phrase(userDetailList.get(i).getUserpassword(), fontBody));
			            tabla.addCell(new Phrase(userDetailList.get(i).getUser_email(), fontBody));
			        
			    }
			    documento.add(tabla);
			    documento.close();
			    file.flush();
			    file.close();
			    status="success";
		}catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}
	
	//josni changes
	
	public String formregister(){
		logger.info("-----------Inside formregister method()----------------");
		loginaccess.setOrganizationName("");
		loginaccess.setOrgEmail("");
		loginaccess.setCountryName("");
		loginaccess.setOrgDetail("");
		loginaccess.setOrgPhone("");
		loginaccess.setContact(""); 
		return"";
	}
	
    public String enquiryforminsertion(){
    	logger.info("-----------Inside enquiryforminsertion method()----------------");
	  SmsController controller=null;String status="Fail";ApplicationContext ctx=null;
	   try{		
		    //("Inside Enquiry Form Insertion");
		    if(addformvalidation(true)){
		       ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		       controller = (SmsController) ctx.getBean("controller");
		       //("Inside Enquiry "+loginaccess.getOrganizationName());
		       status=controller.insertformdetails(loginaccess);
		       //("status in mb----"+status);
		      if(status.equalsIgnoreCase("Sucess")){
			   RequestContext.getCurrentInstance().execute("PF('confirm1').show()");
		      }
		    }
		  }catch(Exception e){
				logger.warn("Exception -->"+e.getMessage());
		 }
	  return"";
	}

  
private boolean addformvalidation(boolean valid) {
	logger.info("-----------Inside addformvalidation method()----------------");
	valid=true;
	String name;
	FacesContext fc=FacesContext.getCurrentInstance();
	if(StringUtils.isEmpty(loginaccess.getOrganizationName())){
		name=CommonValidate.findComponentInRoot("name").getClientId(fc);
		fc.addMessage(name, new FacesMessage("Please  Enter  The  Organization  Name"));
		valid=false;
	}if(StringUtils.isEmpty(loginaccess.getOrgEmail())){
		name=CommonValidate.findComponentInRoot("email").getClientId(fc);
		fc.addMessage(name, new FacesMessage("Please  Enter  The  Email"));
		valid=false;
	}else if(!StringUtils.isEmpty(loginaccess.getOrgEmail())){
		if(!CommonValidate.validateEmail(loginaccess.getOrgEmail())){
			name=CommonValidate.findComponentInRoot("email").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter The Valid Email"));
			valid=false;
		}
	}
	if(StringUtils.isEmpty(loginaccess.getOrgDetail())){
		name=CommonValidate.findComponentInRoot("details").getClientId(fc);
		fc.addMessage(name, new FacesMessage("Please  Enter  The  Details  Here"));
		valid=false;
	}
	if(StringUtils.isEmpty(loginaccess.getOrgPhone())){
		name=CommonValidate.findComponentInRoot("phone").getClientId(fc);
		fc.addMessage(name, new FacesMessage("Please Enter The Phone Number"));
		valid=false;
	}else if(!StringUtils.isEmpty(loginaccess.getOrgPhone())){
		if(!CommonValidate.validateNumber(loginaccess.getOrgPhone())){
			name=CommonValidate.findComponentInRoot("phone").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please  Enter  The  Valid  Phone Number"));
			valid=false;
		}
	}if(StringUtils.isEmpty(loginaccess.getCountryName())){
		   name=CommonValidate.findComponentInRoot("country").getClientId(fc);
		   fc.addMessage(name, new FacesMessage("Please  Enter  The  Country"));
		   valid=false;
	}
	if(StringUtils.isEmpty(loginaccess.getOptions())){
			name=CommonValidate.findComponentInRoot("option").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please  Select  The  Enquiry  Type"));
			valid=false;
	}if(StringUtils.isEmpty(loginaccess.getContact())){
		   name=CommonValidate.findComponentInRoot("lcontact").getClientId(fc);
		   fc.addMessage(name, new FacesMessage("Please choose the contact"));
		   valid=false;
	}
	return valid;
  }

	public String passwordChange() {
		logger.info("-----------Inside passwordChange method()----------------");
		SmsController controller = null;
		String status="";
		try{
			if(loginaccess.getSelectUserlist().size() > 0){
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				status=controller.changePassword(loginaccess);
				//("Inside if ---------"+status);
				if(status.equalsIgnoreCase("Success")){
					//("Inside if ---------");
					RequestContext reqcontext = RequestContext.getCurrentInstance();
					reqcontext.execute("PF('pswdChangedlg').show();");
					//("Inside if after---------");
				}
			}
			
		}catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		finally {
			loginaccess.setSelectUserlist(null);
		}
		return "";
	}
	
	

	private void createPieModel1() {
		logger.info("-----------Inside createPieModel1 method()----------------");
		pieModel = new PieChartModel();
		for (int i = 0; i < loginaccess.getSubjCountList().size(); i++) {
			//("sub --- "+loginaccess.getSubjCountList().get(i).getSubject_name()+"per---- "+loginaccess.getSubjCountList().get(i).getCount_percentage());
			 pieModel.set(loginaccess.getSubjCountList().get(i).getSubject_name(), loginaccess.getSubjCountList().get(i).getCount_percentage());
		}
		pieModel.setTitle("Overall Pass");
		pieModel.setLegendPosition("e");
		pieModel.setShowDataLabels(true);
    }
	
	public String getoverallpassDetails(){
		logger.info("-----------Inside getoverallpassDetails method()----------------");
		int count=0;int passcount=0;
		float pass_percentage=0;
		try{
			loginaccess.setSubjectList(new ArrayList<String>());
			loginaccess.setSubjCountList(new ArrayList<LoginAccess>());
			loginaccess.setOverallmarkList(new ArrayList<LoginAccess>());
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String year=sdf.format(todaydate);
			loginaccess.setYear(Integer.parseInt(year));
			MailSendJDBC.getoverallpass(loginaccess,schoolID);
			for (int i = 0; i < loginaccess.getSubjectList().size(); i++) {
				LoginAccess login=new LoginAccess();
				for (int j = 0; j < loginaccess.getOverallmarkList().size(); j++) {
					if(loginaccess.getSubjectList().get(i).equalsIgnoreCase(loginaccess.getOverallmarkList().get(j).getSubject_name())){
						count++;
						try{
						if(loginaccess.getOverallmarkList().get(j).getResult_status().equalsIgnoreCase("Pass")){
							passcount++;
						}}catch (Exception e) {}
					}
				}
			login.setSubject_name(loginaccess.getSubjectList().get(i));	
			pass_percentage=Float.parseFloat(String.valueOf(passcount))/Float.parseFloat(String.valueOf(count))*100;
			login.setCount_percentage(pass_percentage);
			loginaccess.getSubjCountList().add(login);
			//("subj---"+loginaccess.getSubjCountList().get(i).getSubject_name()+"--tot count---"+count+"---pass count --"+passcount+"--pass_percentage---"+pass_percentage);
			count=0;passcount=0;
			}
			createPieModel1();
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}
	
	
	
}
