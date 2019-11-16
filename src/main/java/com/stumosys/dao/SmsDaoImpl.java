package com.stumosys.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.*;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.Months;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import com.stumosys.domain.AttendanceDataBean;
import com.stumosys.domain.BookSaleDataBean;
import com.stumosys.domain.ClassTimeTableDataBean;
import com.stumosys.domain.LibrarianDataBean;
import com.stumosys.domain.LibraryDataBean;
import com.stumosys.domain.LoginAccess;
import com.stumosys.domain.NoticeBoardDataBean;
import com.stumosys.domain.ParentsDataBean;
import com.stumosys.domain.PaymentDatabean;
import com.stumosys.domain.RegisteredLogin;
import com.stumosys.domain.ReportCardDataBean;
import com.stumosys.domain.ReportDataBean;
import com.stumosys.domain.StaffDataBean;
import com.stumosys.domain.StudentDataBean;
import com.stumosys.domain.StudentPerformanceDataBean;
import com.stumosys.domain.TeacherDataBean;
import com.stumosys.exception.StumosysException;
import com.stumosys.shared.Activities;
import com.stumosys.shared.Attendance;
import com.stumosys.shared.Attendanceclass;
//import com.stumosys.shared.BackofficeLogin;
import com.stumosys.shared.BookOrderRecord;
import com.stumosys.shared.BookSale;
import com.stumosys.shared.BookSalesRecord;
import com.stumosys.shared.Browerbook;
import com.stumosys.shared.Certificate;
import com.stumosys.shared.Class;
import com.stumosys.shared.ClassSubject;
import com.stumosys.shared.ClassTable;
import com.stumosys.shared.ClassTimeTable;
import com.stumosys.shared.Enquiryform;
import com.stumosys.shared.ExamTable;
import com.stumosys.shared.ExtraCurricularActivity;
import com.stumosys.shared.FeesDetail;
import com.stumosys.shared.ImagePath;
import com.stumosys.shared.Library;
import com.stumosys.shared.Loginotp;
//import com.stumosys.shared.Noticeboard;
import com.stumosys.shared.Parent;
import com.stumosys.shared.ParentDetail;
import com.stumosys.shared.Person;
import com.stumosys.shared.Postcode;
import com.stumosys.shared.Product;
import com.stumosys.shared.ReportFilterTable;
import com.stumosys.shared.ReportMenu;
import com.stumosys.shared.Reportcard;
import com.stumosys.shared.School;
import com.stumosys.shared.Staff;
import com.stumosys.shared.State;
import com.stumosys.shared.Student;
import com.stumosys.shared.StudentAttendance;
import com.stumosys.shared.StudentClass;
import com.stumosys.shared.StudentDetail;
import com.stumosys.shared.StudentParent;
import com.stumosys.shared.StudentPerformance;
import com.stumosys.shared.Subject;
import com.stumosys.shared.SupProduct;
import com.stumosys.shared.Teacher;
import com.stumosys.shared.TeacherAttendance;
import com.stumosys.shared.TeacherClass;
import com.stumosys.shared.TeacherDetail;
import com.stumosys.shared.TeacherNote;
import com.stumosys.shared.TeacherSubject;
import com.stumosys.shared.TimeTable;
import com.stumosys.shared.User;
import com.stumosys.shared.UserProduct;
import com.stumosys.util.GetTimeZone;
import com.stumosys.util.MailSendJDBC;
import com.stumosys.util.PasswordEncryption;
import com.stumosys.util.RandomPasswordGenerator;

@Repository
@Singleton
public class SmsDaoImpl implements SmsDao {
	@PersistenceContext(unitName = "sms-pu")
	private EntityManager entitymanager;

	@PersistenceContext(unitName = "sms-pu")
	private EntityManager entitymanager1;

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");

	Date date = new Date();
	Timestamp timestamp = new Timestamp(date.getTime());

	SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	final Logger logger = Logger.getLogger(SmsDaoImpl.class);

	/*
	 * FacesContext context = FacesContext.getCurrentInstance(); ResourceBundle
	 * text = context.getApplication().evaluateExpressionGet(context, "#{text}",
	 * ResourceBundle.class);
	 */
	
	Locale locale = new Locale("en", "US");
	ResourceBundle paths = ResourceBundle.getBundle("email",locale);
	
	/**
	 * This loginDao Method is Used for Authenticate the User
	 */
	@Override
	@SuppressWarnings("unchecked")
	 public String loginDao(LoginAccess loginaccess) throws StumosysException {
	  logger.info("-----------Inside loginDao method()----------------");
	  String Status = "Failure";
	  Query q = null;
	  String pwd = "";
	  List<User> result=null;
	  Long totalStudent = null, totalTeacher = null, totalParent = null;int  todayAbsent = 0 ;
	  logger.debug("User given user name -->"+loginaccess.getUsername());
	  try {
		  logger.info("loginDao - Inside try block1");
		  q = entitymanager.createQuery("from User where username=? and status='Active'");
		  q.setParameter(1, loginaccess.getUsername());
	  try
	   {
		  result = (List<User>)q.getResultList();
		  logger.info("loginDao - Inside try block2");
		  if (result.size() > 0) {
	    pwd = result.get(0).getPassword();
	    String persionID = String.valueOf(result.get(0).getPerson().getPerson_ID());
	    String schoolID = String.valueOf(result.get(0).getPerson().getSchool().getSchool_ID());
	    logger.debug("" + PasswordEncryption.MatchPaswword(loginaccess.getUserpassword(), pwd));
	    if (PasswordEncryption.MatchPaswword(loginaccess.getUserpassword(), pwd) == true) {
	     Status = "Authorized";
	     loginaccess.setPersonID(String.valueOf(result.get(0).getPerson().getPerson_ID()));
	     loginaccess.setSchoolID(String.valueOf(result.get(0).getPerson().getSchool().getSchool_ID()));
	     loginaccess.setSchoolName(result.get(0).getPerson().getSchool().getName());
	     loginaccess.setUser_rolles(result.get(0).getPerson().getPersonRole());
	     logger.debug("Login user role -->"+loginaccess.getUser_rolles());
		 if(loginaccess.getUser_rolles().equalsIgnoreCase("Admin"))
		 {	 logger.info("Admin role start");
		     q= entitymanager.createQuery("select count(*) from Student where status=? and school_ID=?");
		     q.setParameter(1, "Active");
		     q.setParameter(2, Integer.parseInt(schoolID));
		     totalStudent=(Long)q.getSingleResult();
		     logger.debug("Student record count -->"+totalStudent);
		     q=entitymanager.createQuery("select count(*) from Parent where status=? and school_ID=?");
		     q.setParameter(1, "Active");
		     q.setParameter(2, Integer.parseInt(schoolID));
		     totalParent = (Long)q.getSingleResult();
		     logger.debug("Parent record count -->"+totalParent);
		     q=entitymanager.createQuery("select count(*) from Teacher where status=? and school_ID=?");
		     q.setParameter(1, "Active");
		     q.setParameter(2, Integer.parseInt(schoolID));
		     totalTeacher = (Long)q.getSingleResult();
		     logger.debug("Teacher record count -->"+totalTeacher);
		     todayAbsent = getTodayAbsent(persionID);
		     loginaccess.setTodayAbsent(String.valueOf(todayAbsent));
		     loginaccess.setTotalParent(String.valueOf(totalParent));
		     loginaccess.setTotalStudent(String.valueOf(totalStudent));
		     loginaccess.setTotalTeacher(String.valueOf(totalTeacher));
			 logger.info("Admin role end");

		 }
	     if(loginaccess.getUser_rolles().equalsIgnoreCase("Teacher")){
	    	 logger.info("Teacher role start");
	    	 q=null;
	    	 q=entitymanager.createQuery("from Teacher where person_ID=? and status='Active'");
	    	 q.setParameter(1, loginaccess.getPersonID());
	    	 List<Teacher> teacherList=(List<Teacher>)q.getResultList();
	    	 if(teacherList.size()>0){
	    		 q=null;
	    		 q=entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
		    	 q.setParameter(1, teacherList.get(0).getTeacher_ID());
		    	 List<TeacherDetail> teacherdetail=(List<TeacherDetail>)q.getResultList();
		    	 if(teacherdetail.size()>0){
		    		 loginaccess.setName(teacherdetail.get(0).getFirstName()+""+teacherdetail.get(0).getLastName());
		    	 }
	    	 }
	    	 logger.info("Teacher role end");

	     }else if(loginaccess.getUser_rolles().equalsIgnoreCase("Parent")){
	    	 q=null;
	    	 q=entitymanager.createQuery("from Parent where person_ID=? and status='Active'");
	    	 q.setParameter(1, loginaccess.getPersonID());
	    	 List<Parent> parentList=(List<Parent>)q.getResultList();
	    	 if(parentList.size()>0){
	    		 q=null;
	    		 q=entitymanager.createQuery("from ParentDetail where parent_ID=?");
		    	 q.setParameter(1, parentList.get(0).getParent_ID());
		    	 List<ParentDetail> parentdetail=(List<ParentDetail>)q.getResultList();
		    	 if(parentdetail.size()>0){
		    		 loginaccess.setName(parentdetail.get(0).getFirstName()+""+parentdetail.get(0).getLastName());
		    	 }
	    	 }
	     }else if(loginaccess.getUser_rolles().equalsIgnoreCase("Student")){
	    	 q=null;
	    	 q=entitymanager.createQuery("from Student where person_ID=? and status='Active'");
	    	 q.setParameter(1, loginaccess.getPersonID());
	    	 List<Student> studentList=(List<Student>)q.getResultList();
	    	 if(studentList.size()>0){
	    		 q=null;
	    		 q=entitymanager.createQuery("from StudentDetail where student_ID=?");
		    	 q.setParameter(1, studentList.get(0).getStudent_ID());
		    	 List<StudentDetail> studentdetail=(List<StudentDetail>)q.getResultList();
		    	 if(studentdetail.size()>0){
		    		 loginaccess.setName(studentdetail.get(0).getFirstName()+""+studentdetail.get(0).getLastName());
		    	 }
	    	 }
	     }else if(loginaccess.getUser_rolles().equalsIgnoreCase("BookShop") ||
	    		 loginaccess.getUser_rolles().equalsIgnoreCase("Librarian") || (loginaccess.getUser_rolles().equalsIgnoreCase("Staff"))){
	    	 q=null;
	    	 q=entitymanager.createQuery("from Staff where person_ID=? and status='Active'");
	    	 q.setParameter(1, loginaccess.getPersonID());
	    	 List<Staff> staffList=(List<Staff>)q.getResultList();
	    	 if(staffList.size()>0){
	    		 loginaccess.setName(staffList.get(0).getFirstName()+""+staffList.get(0).getLastName());
	    	 }
	     }else{
	    	 loginaccess.setName(loginaccess.getUsername());
	     }
	    } else {
	     Status = "Fail";
	    }
	   }
	   loginaccess.setAuthentoicationStatus(Status);
	  } 
	   catch(Exception ee)
	   {
		logger.warn(" exception - "+ee.getMessage());   
	   }
	  }
	   catch (Exception e) {
	   logger.warn("Exception -->"+e.getMessage());
	  }
	  return Status;
	 }
	
	@SuppressWarnings("unchecked")
	private int getTodayAbsent(String persionID) {
		logger.info("-----------Inside getTodayAbsent method()----------------");
		int res = 0;
		Query q = null;
		Query q1 = null;
		int attendanceID = 0;
		List<Person> rollStatus = null;
		int school_ID = 0;
		try {
			if (persionID != null) {
				rollStatus = getRollType(persionID);
				school_ID=rollStatus.get(0).getSchool().getSchool_ID();
				if (rollStatus.size() > 0) {
			q = entitymanager.createQuery("from Attendance where date=? and school_ID=?");
			if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
				q.setParameter(1, GetTimeZone.getDate("Asia/Makassar"));
			}else{
				q.setParameter(1, date);
			}
			q.setParameter(2, school_ID);
			List<Attendance> result = (List<Attendance>) q.getResultList();
			if (result.size() > 0) {
				attendanceID = result.get(0).getAttendance_ID();
				q1 = entitymanager.createQuery("from Attendanceclass where attendance_ID=? and (status='Leave' or status='Absent')");
				q1.setParameter(1, attendanceID);
				List<Attendanceclass> absent = (List<Attendanceclass>) q1.getResultList();
				if (absent.size() > 0) {
					res = absent.size();
				}
			}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception"+e.getMessage());
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	private int getTotalStudent(String persionID) {
		logger.info("-----------Inside getTotalStudent method()----------------");
		int res = 0;
		Query q = null;
		List<Person> rollStatus = null;
		int school_ID = 0;
		try {
			if (persionID != null) {
				rollStatus = getRollType(persionID);
				school_ID=rollStatus.get(0).getSchool().getSchool_ID();
				if (rollStatus.size() > 0) {
			q = entitymanager.createQuery("from Student where status=? and school_ID=?");
			q.setParameter(1, "Active");
			q.setParameter(2, school_ID);
			List<Student> result = (List<Student>) q.getResultList();
			if (result.size() > 0) {
				res = result.size();
			}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception"+e.getMessage());
		}
		return res;
	}

	/**
	 * @author Robert Arjun This method is used to load menu from DB depends on
	 *         userID
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<UserProduct> LoadMenu(LoginAccess loginaccess) {
		logger.info("-----------Inside LoadMenu method()----------------");
		int userID = 0;
		Query q = null;
		List<UserProduct> result = null;
		try {
			logger.debug("Inside Load Menu method calling");
			userID = getUserID(loginaccess.getUsername());
			if (userID > 0) {
				q = entitymanager.createQuery("from UserProduct where has_user_ID=? and status='Active'");
				q.setParameter(1, userID);				
				result = (List<UserProduct>) q.getResultList();
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return result;
	}

	/**
	 * @author Robert Arjun This method is used to get person ID depends on
	 *         username
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int getPersonID(String username) {
		logger.info("-----------Inside getPersonID method()----------------");
		int res = 0;
		Query q = null;
		try {
			q = entitymanager.createQuery("from User where username=? and status='Active'");
			q.setParameter(1, username);
			List<User> result = (List<User>) q.getResultList();
			if (result.size() > 0) {
				res = result.get(0).getPerson().getPerson_ID();
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res;
	}

	
	@SuppressWarnings("unchecked")
	private int getUserID(String username) {
		logger.info("-----------Inside getUserID method()----------------");
		int res = 0;
		Query q = null;
		try {
			q = entitymanager.createQuery("from User where username=? and status='Active'");
			q.setParameter(1, username);
			List<User> result = (List<User>) q.getResultList();
			if (result.size() > 0) {
				res = result.get(0).getHas_user_ID();
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res;
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<SupProduct> loadSubMenu(int product_ID, String name) {
		logger.info("-----------Inside loadSubMenu method()----------------");
		Query q = null;
		List<SupProduct> result = null;

		try {
			logger.debug("name" + name + "---" + product_ID);
			q = entitymanager.createQuery("from SupProduct where product_ID=? and status='Active'");
			q.setParameter(1, product_ID);
			result = (List<SupProduct>) q.getResultList();
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return result;
	}

	/**
	 * @author Robert Arjun This method is used to get state name from db set to
	 *         JSF
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<String> getStateList(String personID) {
		logger.info("-----------Inside getStateList method()----------------");
		List<String> result = null;
		String personRole = "";
		Query q = null;
		if (personID != null) {
			try {
				personRole = getPersonRole(personID);
				if (personRole.equalsIgnoreCase("Admin")) {
					q = entitymanager.createQuery("select stateName from State");
					result = (List<String>) q.getResultList();
				}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
		}
		return result;
	}

	/**
	 * @author Robert Arjun * This method is used to get role name
	 * @param personID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getPersonRole(String personID) {
		logger.info("-----------Inside getPersonRole method()----------------");
		Query q = null;
		String res = "";
		try {
			int perID = Integer.parseInt(personID);
			logger.debug("---" + perID);
			q = entitymanager.createQuery("from Person where person_ID =? and status='Active' ");
			q.setParameter(1, perID);
			List<Person> res1 = (List<Person>) q.getResultList();
			if (res1.size() > 0) {
				res = res1.get(0).getPersonRole();
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res;
	}

	/**
	 * * @author Robert Arjun
	 */
	@Override
	public String checkValidateZip(String personID, TeacherDataBean teacherDataBean) {
		logger.info("-----------Inside checkValidateZip method()----------------");
		String status = "Fail";
		//Query q = null;
		try {
			if (personID != null) {

				String zip1 = ValidateZip(teacherDataBean.getTeaPermanentState(), teacherDataBean.getTeaPermanentZip());
				String zip2 = ValidateZip(teacherDataBean.getTeaPresentState(), teacherDataBean.getTeaPresentZip());
				if (zip1.equalsIgnoreCase("Valid") && zip2.equalsIgnoreCase("Valid")) {
					status = "BothSuccess";
				} else if (zip1.equalsIgnoreCase("Not") && zip2.equalsIgnoreCase("Valid")) {
					status = "Zip2Success";
				} else if (zip1.equalsIgnoreCase("Valid") && zip2.equalsIgnoreCase("Not")) {
					status = "Zip1Success";
				} else {
					logger.debug("Both Failes");
				}

			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	private String ValidateZip(String state, String zip) {
		logger.info("-----------Inside ValidateZip method()----------------");
		String status = "Not";
		Query q = null;
		Query q1 = null;
		try {
			q = entitymanager.createQuery("from State where stateName=?");
			q.setParameter(1, state);
			List<State> res = (List<State>) q.getResultList();
			if (res.size() > 0) {
				String stateCode = res.get(0).getStateCode();
				q1 = entitymanager.createQuery("from Postcode where state_code=? and postcode=?");
				q1.setParameter(1, stateCode);
				q1.setParameter(2, zip);
				List<Postcode> result = (List<Postcode>) q1.getResultList();
				if (result.size() > 0) {
					status = "Valid";
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}

	/**
	 * @author Robert Arjun This method is used to Load class and Section from
	 *         BB
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<String> getClassSection(String personID) {
		logger.info("-----------Inside getClassSection method()----------------");
		List<String> resultSize = null;
		List<Person> roleStatus = null;
		int school_ID = 0;
		Query q = null;
		try {
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						q = entitymanager.createQuery("from Class where school_ID=? and status='Active'");
						q.setParameter(1, school_ID);
						List<Class> result = (List<Class>) q.getResultList();
						resultSize = new ArrayList<String>();
						if (result.size() > 0) {

							for (int i = 0; i < result.size(); i++) {
								String s = result.get(i).getClassName() + "/" + result.get(i).getClassSection();
								resultSize.add(s);
							}
						}
					}
				}
			} else {
				logger.debug("Session Out..");
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return resultSize;
	}

	/**
	 * @author Robert Arjun This method is used to Load subject and subject code
	 *         from BB
	 */

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getSubjectList(String personID) {
		logger.info("-----------Inside getSubjectList method()----------------");
		List<String> subList = null;
		Query q = null;
		List<Person> roleStatus = null;
		int school_ID = 0;
		try {
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
				q = entitymanager.createQuery("from Subject where status='Active' and school_ID=?");
				q.setParameter(1, school_ID);
				List<Subject> res = (List<Subject>) q.getResultList();
				subList = new ArrayList<String>();
				if (res.size() > 0) {
					for (int i = 0; i < res.size(); i++) {
						String s = res.get(i).getSujectName() + "/" + res.get(i).getSubjectCode();

						subList.add(s);
					}
				}
			}
				}
				}else {
				logger.debug("Session Out..");
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return subList;
	}

	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String insertTeacher(String personID, TeacherDataBean teacherDataBean) { 
		logger.info("-----------Inside insertTeacher method()----------------");
		List<Person> roleStatus=null;
		String status = "Fail";
		int school_ID = 0;
		//int postcode_ID1 = 0;
		//int postcode_ID2 = 0;
		int newPerson_ID = 0;
		int teacher_ID = 0;
		int user_ID = 0;
		Query insertPerson = null;
		Query personQuery = null;
		Query dateailsQuery = null;
		String secPwd = "";
		String EncPwd = "";
		Query insetMenu = null;
			try {
				roleStatus = new ArrayList<Person>();
				if (personID != null) {
					roleStatus = getRollType(personID);
					if (roleStatus.size() > 0) {
						school_ID = roleStatus.get(0).getSchool().getSchool_ID();
						if (school_ID > 0) {
						teacherDataBean.setSchoolLogo(roleStatus.get(0).getSchool().getLogopath());
						insertPerson = entitymanager.createQuery("from Person where personRoleNumber=?");
						insertPerson.setParameter(1, teacherDataBean.getTeaID());
						List<Person> rollSize = (List<Person>) insertPerson.getResultList();
						if (rollSize.size() > 0) {
							status = "Exsist";
						} else {
							logger.debug(
									"----------------------------------Inserting Teacher 1 Begin-----------------------------------------------");
							// Insert Person Table
							Person person = new Person();
							if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
								person.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
								person.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
							}else{
								person.setCreatedDate(date);
								person.setCreatedTime(timestamp);
							}	
							person.setPersonRoleNumber(teacherDataBean.getTeaID());
							person.setPersonRole("Teacher");
							person.setStatus("Active");
							person.setSchool(entitymanager.find(School.class, school_ID));
							entitymanager.persist(person);
							logger.info(
									"----------------------------------Inserting Teacher 1 End-----------------------------------------------");
							logger.info(
									"======================================================================================================");
	
							logger.info(
									"----------------------------------Inserting Teacher 2 Begin-----------------------------------------------");
	
							personQuery = entitymanager.createQuery("from Person where personRoleNumber=?");
							personQuery.setParameter(1, teacherDataBean.getTeaID());
							List<Person> result = (List<Person>) personQuery.getResultList();
							if (result.size() > 0) {
								newPerson_ID = result.get(0).getPerson_ID();
								logger.debug("Persion ID" + newPerson_ID);
								teacherDataBean.setSchoolName(result.get(0).getSchool().getName());
								teacherDataBean.setSchoolID(school_ID);
								// Insert Teacher Table
								Teacher teacher = new Teacher();
								teacher.setSchool(entitymanager.find(School.class, school_ID));
								if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
									teacher.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
									teacher.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
								}else{
									teacher.setCreatedDate(date);
									teacher.setCreatedTime(timestamp);
								}
								teacher.setPerson(entitymanager.find(Person.class, newPerson_ID));
								teacher.setRollNumber(teacherDataBean.getTeaID());
								teacher.setStatus("Active");
								entitymanager.persist(teacher);
								logger.info(
										"----------------------------------Inserting Teacher 2 End-----------------------------------------------");
								logger.info(
										"======================================================================================================");
	
								logger.info(
										"----------------------------------Inserting Teacher 3 Begin-----------------------------------------------");
	
								dateailsQuery = entitymanager.createQuery("from Teacher where rollNumber=?");
								dateailsQuery.setParameter(1, teacherDataBean.getTeaID());
								List<Teacher> result1 = (List<Teacher>) dateailsQuery.getResultList();
								if (result1.size() > 0) {
									teacher_ID = result1.get(0).getTeacher_ID();
									logger.debug("teacher ID" + teacher_ID);
								/*	postcode_ID1 = getPostcodeID(teacherDataBean.getTeaPresentZip());
									postcode_ID2 = getPostcodeID(teacherDataBean.getTeaPermanentZip());
									logger.debug("1---" + postcode_ID1);
									logger.debug("2----" + postcode_ID2);
									if (postcode_ID1 > 0 && postcode_ID2 > 0) {*/
										// Insert TeacherDetails Table
										TeacherDetail teacherDetail = new TeacherDetail();
										teacherDetail.setTeacher(entitymanager.find(Teacher.class, teacher_ID));
										teacherDetail.setAddress1_Street(teacherDataBean.getTeaPresentAddress());
										//teacherDetail.setPostcode1(entitymanager.find(Postcode.class, postcode_ID1));
										teacherDetail.setAddress2_Street(teacherDataBean.getTeaPermanentAddress());
										//teacherDetail.setPostcode2(entitymanager.find(Postcode.class, postcode_ID2));
										logger.debug("dob "+teacherDataBean.getTeaDob());
										teacherDetail.setDob(teacherDataBean.getTeaDob());
										teacherDetail.setEmailId(teacherDataBean.getTeaEmail());
										teacherDetail.setFatherName(teacherDataBean.getTeaFatherName());
										teacherDetail.setFirstName(teacherDataBean.getTeaFirstName());
										teacherDetail.setGender(teacherDataBean.getTeaGender());
										teacherDetail.setLastName(teacherDataBean.getTeaLastName());
										teacherDetail.setPercentage(teacherDataBean.getTeaPercentage());
										teacherDetail.setPhoneNumber(teacherDataBean.getTeaPhoneNo());
										teacherDetail.setPost(teacherDataBean.getTeaPosition());
										teacherDetail.setQualification(teacherDataBean.getTeaEduQualification());
										teacherDetail.setWorkingHour(teacherDataBean.getTeaWorkingHour());
										logger.debug("hour "+teacherDataBean.getTeaWorkingHour());
										teacherDetail.setYearOfPassing(teacherDataBean.getTeaYearOfPassing());
										teacherDetail.setMotherName(teacherDataBean.getTeaMotherName());
										teacherDetail.setPresentCity(teacherDataBean.getTeaCity());
										teacherDetail.setPermanentCity(teacherDataBean.getTeaCity1());
										teacherDetail.setPresentCountry(teacherDataBean.getTeaCountry());
										teacherDetail.setPermanentCountry(teacherDataBean.getTeaCountry1());
										teacherDetail.setPresentState(teacherDataBean.getTeaState());
										teacherDetail.setPermanentState(teacherDataBean.getTeaState1());
										teacherDetail.setPresentCode(teacherDataBean.getCode());
										teacherDetail.setPermanentCode(teacherDataBean.getCode1());
										teacherDetail.setPresentPostal(teacherDataBean.getTeaPostal());
										teacherDetail.setPermanentPostal(teacherDataBean.getTeaPostal1());
										teacherDetail.setPhoneNumber1(teacherDataBean.getTeaPhoneNo1());
										teacherDetail.setManagement(teacherDataBean.getManagement());
										entitymanager.persist(teacherDetail);
										logger.info(
												"----------------------------------Inserting Teacher 3 End-----------------------------------------------");
										logger.info(
												"======================================================================================================");
										if(teacherDataBean.getTeafilePath() !=null)
										{
										StringTokenizer stoken = new StringTokenizer(teacherDataBean.getTeafilePath());
										Date d = dateFormat.parse(stoken.nextToken("/"));
										String tempPath = teacherDataBean.getTeafilePath();
										String path = tempPath.substring(tempPath.lastIndexOf("/") + 1);
										// Insert ImagePath table
										ImagePath imagepath = new ImagePath();
										imagepath.setDate(d);
										imagepath.setPath(path);
										imagepath.setName(teacherDataBean.getTeaID());
										imagepath.setRollStatus("Teacher");
										imagepath.setTeacher(entitymanager.find(Teacher.class, teacher_ID));
										imagepath.setStatus("Active");
										entitymanager.persist(imagepath);
										}
										String subjectStatus = insertTeacherSubject(teacherDataBean, school_ID, teacher_ID);// call
																															// insertTeacherSubject
																															// method
										logger.debug("subjectStatus == "+subjectStatus);																					// call
										
										String classSratus = insertinsertTeacherClass(teacherDataBean, school_ID,teacher_ID);// call
										logger.debug("classSratus == "+classSratus);		
															// insertinsertTeacherClass
															// method call
										if (subjectStatus.equalsIgnoreCase("Success")
												&& classSratus.equalsIgnoreCase("Success")) {
											logger.debug(
													"----------------------------------Generate Teacher 6 Begin-----------------------------------------------");
											secPwd = RandomPasswordGenerator.GenerateSecurePassword(teacherDataBean.getTeaID());// used
																										// to
																										// generate
																										// secure
																										// random
																										// password
											if (secPwd != null) {
												teacherDataBean.setTeaSecurePasword(secPwd);
												teacherDataBean.setTeaUsername(teacherDataBean.getTeaID());
												EncPwd = PasswordEncryption.GeneratePaswword(teacherDataBean.getTeaSecurePasword());// used
																													// to
																													// generate
																													// Encryption
																													// Password
												logger.debug("Encription " + EncPwd + "----" + secPwd);
												if (EncPwd != null) {
													logger.debug(
															"----------------------------------Insert Login Teacher 7 Begin-----------------------------------------------");
													// Insert User Table
													User user = new User();
													user.setStatus("Active");
													user.setUsername(teacherDataBean.getTeaUsername());
													user.setPerson(entitymanager.find(Person.class, newPerson_ID));
													user.setPassword(EncPwd);
													entitymanager.persist(user);
													logger.debug(
															"----------------------------------Insert Login Teacher 7 End-----------------------------------------------");
													logger.debug(
															"======================================================================================================");
													
													insetMenu = entitymanager.createQuery("from User where username=?");
													insetMenu.setParameter(1,teacherDataBean.getTeaUsername());
													List<User> userList = (List<User>) insetMenu.getResultList();
													if (userList.size() > 0) {
														user_ID = userList.get(0).getHas_user_ID();
														logger.debug("user ID" + user_ID);
	
														String menuStatus = insertTeacherMenus(user_ID,school_ID);// call
																										// insertTeacherMenus
																										// method
																										// call
														logger.debug("Menu Status" + menuStatus);
														status = "Success";
													}
												}
											}
											logger.info(
													"----------------------------------Generate Teacher 6 End-----------------------------------------------");
											logger.info(
													"======================================================================================================");
	
										}
								}
								
							}
						}

					}
				}
				}

			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
				//logger.error("Exception -->"+e.getMessage());
				//e.printStackTrace();
			}

		return status;
	}

	/**
	 * @author Robert Arjun This method is used insert Menu for user
	 * @param user_ID
	 * @param school_ID 
	 * @return
	 */
	@Transactional(value = "transactionManager")
	private String insertTeacherMenus(int user_ID, int school_ID) {
		logger.info("-----------Inside insertTeacherMenus method()----------------");
		String res = "fail";
		int mainMenuID1 = 0;
		String res1 = "F";
		String res2 = "F";
		String res3 = "F";
		String res4 = "F";
		String res5 = "F";
		String res6 = "F";
		String res7 = "F";
		//String res8 = "F";
		String res9 = "F";
		try {
			// Insert UserProduct table
			if (user_ID > 0) {
				mainMenuID1 = getMainMenu("TIM100");
				if (mainMenuID1 > 0) {

					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res1 = "S";
				}
				mainMenuID1 = getMainMenu("NOT100");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res2 = "S";
				}
					if (school_ID!=37) {
						mainMenuID1 = getMainMenu("ATT100");
						if (mainMenuID1 > 0) {
							UserProduct user = new UserProduct();
							user.setUser(entitymanager.find(User.class, user_ID));
							user.setStatus("Active");
							user.setProduct(entitymanager.find(Product.class, mainMenuID1));
							entitymanager.persist(user);
							entitymanager.flush();
							entitymanager.clear();
							res3 = "S";
						}
					}
				/*if(school_ID==1 || school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5 || school_ID==6 ){*/
					mainMenuID1 = getMainMenu("HW000");
					if (mainMenuID1 > 0) {
						UserProduct user = new UserProduct();
						user.setUser(entitymanager.find(User.class, user_ID));
						user.setStatus("Active");
						user.setProduct(entitymanager.find(Product.class, mainMenuID1));
						entitymanager.persist(user);
						entitymanager.flush();
						entitymanager.clear();
						res9 = "S";
					}
				/*}*/
					if (school_ID!=37) {
				mainMenuID1 = getMainMenu("STU100");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res4 = "S";
				}
					}
					if (school_ID!=37) {
						mainMenuID1 = getMainMenu("REP100");
						if (mainMenuID1 > 0) {
							UserProduct user = new UserProduct();
							user.setUser(entitymanager.find(User.class, user_ID));
							user.setStatus("Active");
							user.setProduct(entitymanager.find(Product.class, mainMenuID1));
							entitymanager.persist(user);
							entitymanager.flush();
							entitymanager.clear();
							res5 = "S";
						}
					}
				mainMenuID1 = getMainMenu("PAY100");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res6 = "S";
				}
				mainMenuID1 = getMainMenu("LIB100");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res7 = "S";
				}
				/*mainMenuID1 = getMainMenu("BOO100");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res8 = "S";
				}*/
				mainMenuID1 = getMainMenu("PRO100");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res9 = "S";
				}
				if (res1.equalsIgnoreCase("S") && res2.equalsIgnoreCase("S") && res3.equalsIgnoreCase("S")
						&& res4.equalsIgnoreCase("S") && res5.equalsIgnoreCase("S") && res6.equalsIgnoreCase("S")
						&& res7.equalsIgnoreCase("S")  && res9.equalsIgnoreCase("S")) {
					res = "Sucess";
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res;
	}

	/**
	 * @author Robert Arjun
	 * 
	 * @param code
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int getMainMenu(String code) {
		logger.info("-----------Inside getMainMenu method()----------------");
		int res = 0;
		Query q = null;
		try {
			q = entitymanager.createQuery("from Product where productCode=? and status='Active'");
			q.setParameter(1, code);
			List<Product> result = (List<Product>) q.getResultList();
			if (result.size() > 0) {
				res = result.get(0).getProduct_ID();
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res;
	}

	/**
	 * @author Robert Arjun Insert TeacherSubject table
	 * @param teacherDataBean
	 * @param school_ID
	 * @param teacher_ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	private String insertTeacherSubject(TeacherDataBean teacherDataBean, int school_ID, int teacher_ID) {
		logger.info("-----------Inside insertTeacherSubject method()----------------");
		String status = "Failure";
		Query q = null;
		int subject_ID = 0;
		if (school_ID > 0 && teacher_ID > 0) {
			try {
				logger.debug("Subject--" + teacherDataBean.getTeaSubjectTargetList().size());
				for (int i = 0; i < teacherDataBean.getTeaSubjectTargetList().size(); i++) {
					StringTokenizer st = new StringTokenizer(teacherDataBean.getTeaSubjectTargetList().get(i));
					String subjectName = st.nextToken("/");
					String subjectCode = teacherDataBean.getTeaSubjectTargetList().get(i);
					String code = subjectCode.substring(subjectCode.lastIndexOf("/") + 1);
					logger.debug(
							"----------------------------------Inserting Teacher 4 Begin-----------------------------------------------");

					q = entitymanager
							.createQuery("from Subject where sujectName=? and subjectCode=? and school_ID=? and status='Active'");
					q.setParameter(1, subjectName);
					q.setParameter(2, code);
					q.setParameter(3, school_ID);
					List<Subject> result = (List<Subject>) q.getResultList();
					if (result.size() > 0) {
						subject_ID = result.get(0).getSubject_ID();
						TeacherSubject teacherSubject = new TeacherSubject();
						teacherSubject.setSchool(entitymanager.find(School.class, school_ID));
						teacherSubject.setTeacher(entitymanager.find(Teacher.class, teacher_ID));
						teacherSubject.setSubject(entitymanager.find(Subject.class, subject_ID));
						teacherSubject.setStatus("Active");
						entitymanager.persist(teacherSubject);
						entitymanager.flush();
						entitymanager.clear();
						status = "Success";
						logger.debug(
								"----------------------------------Inserting Teacher 4 End-----------------------------------------------");
						logger.debug(
								"======================================================================================================");

					}

				}
			} catch (Exception e) {
				//logger.warn("Exception -->"+e.getMessage());
				logger.error("Exception -->"+e.getMessage());
			}
		}
		return status;
	}


	/**
	 * @author Robert Arjun Insert TeacherClass table
	 * @param teacherDataBean
	 * @param school_ID
	 * @param teacher_ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	private String insertinsertTeacherClass(TeacherDataBean teacherDataBean, int school_ID, int teacher_ID) {
		logger.info("-----------Inside insertinsertTeacherClass method()----------------");
		String status = "Failure";
		Query q = null;
		int class_ID = 0;
		if (school_ID > 0 && teacher_ID > 0) {
			try {
				logger.debug("Class--" + teacherDataBean.getTeaClassTargetList().size());
				for (int i = 0; i < teacherDataBean.getTeaClassTargetList().size(); i++) {
					StringTokenizer st = new StringTokenizer(teacherDataBean.getTeaClassTargetList().get(i));
					String className = st.nextToken("/");
					String sectionName = teacherDataBean.getTeaClassTargetList().get(i);
					String section = sectionName.substring(sectionName.lastIndexOf("/") + 1);
					logger.info(
							"----------------------------------Inserting Teacher 5 Begin-----------------------------------------------");

					q = entitymanager.createQuery("from Class where className=? and classSection=? and school_ID=? and status='Active'");
					q.setParameter(1, className);
					q.setParameter(2, section);
					q.setParameter(3, school_ID);
					List<Class> result = (List<Class>) q.getResultList();
					if (result.size() > 0) {
						class_ID = result.get(0).getClass_ID();
						TeacherClass teacherClass = new TeacherClass();
						teacherClass.setClazz(entitymanager.find(Class.class, class_ID));
						teacherClass.setTeacher(entitymanager.find(Teacher.class, teacher_ID));
						teacherClass.setStatus("Active");
						entitymanager.persist(teacherClass);
						entitymanager.flush();
						entitymanager.clear();
						status = "Success";
						logger.info(
								"----------------------------------Inserting Teacher 5 End-----------------------------------------------");
						logger.info(
								"======================================================================================================");

					}
				}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
		}
		return status;
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<TeacherDataBean> getImagePath(String personID, String teaID) {
		logger.info("-----------Inside getImagePath method()----------------");
		List<Person> rollStatus=null;
		Query q = null;
		Query q1 = null;
		int teacher_ID = 0;
		List<TeacherDataBean> finalList = null;
		try {
			finalList = new ArrayList<TeacherDataBean>();

			if ((personID != null) && (!teaID.equalsIgnoreCase(""))) {
				rollStatus = getRollType(personID);
				logger.debug("Roll Size" + rollStatus.size());
				if (rollStatus.size() > 0) {
					int school_ID = rollStatus.get(0).getSchool().getSchool_ID();
					teacher_ID = getTeacherID(teaID,school_ID);
					if (teacher_ID > 0) {
						q = entitymanager.createQuery("from ImagePath where teacher_ID=? and rollStatus='Teacher'");
						q.setParameter(1, teacher_ID);
						List<ImagePath> result = (List<ImagePath>) q.getResultList();
						if (result.size() > 0) {
							q1 = entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
							q1.setParameter(1, teacher_ID);
							List<TeacherDetail> res = (List<TeacherDetail>) q1.getResultList();
							if (res.size() > 0) {
								TeacherDataBean teacherDataBean = new TeacherDataBean();
								teacherDataBean.setPath(result.get(0).getPath());
								teacherDataBean.setPathDate(result.get(0).getDate());
								teacherDataBean.setTeaDob(res.get(0).getDob());
								teacherDataBean.setTeaFirstName(res.get(0).getFirstName());
								teacherDataBean.setTeaLastName(res.get(0).getLastName());
								teacherDataBean.setTeaGender(res.get(0).getGender());
								teacherDataBean.setTeaPresentAddress(res.get(0).getAddress1_Street());
								teacherDataBean.setTeaFatherName(res.get(0).getFatherName());
								teacherDataBean.setTeaEmail(res.get(0).getEmailId());
								teacherDataBean.setTeaID(teaID);
								teacherDataBean.setTeaPosition(res.get(0).getPost());
								teacherDataBean.setTeaPhoneNo(res.get(0).getPhoneNumber());
								//teacherDataBean.setTeaPermanentZip(res.get(0).getPostcode1().getPostcode());
								//teacherDataBean.setTeaPresentState(res.get(0).getPostcode1().getState().getStateName());
								//teacherDataBean.setTeaPresentZip(res.get(0).getPostcode1().getPostcode());
								//teacherDataBean.setTeaPermanentState(res.get(0).getPostcode2().getState().getStateName());
								teacherDataBean.setTeaPermanentAddress(res.get(0).getAddress2_Street());
								teacherDataBean.setTeaMotherName(res.get(0).getMotherName());
								teacherDataBean.setTeaFatherName(res.get(0).getFatherName());
								teacherDataBean.setTeaYearOfPassing(res.get(0).getYearOfPassing());
								teacherDataBean.setTeaEduQualification(res.get(0).getQualification());
	
								finalList.add(teacherDataBean);
							}
						}else{
							q1 = entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
							q1.setParameter(1, teacher_ID);
							List<TeacherDetail> res = (List<TeacherDetail>) q1.getResultList();
							if (res.size() > 0) {
								TeacherDataBean teacherDataBean = new TeacherDataBean();
								teacherDataBean.setPath(null);
								teacherDataBean.setPathDate(null);
								teacherDataBean.setTeaDob(res.get(0).getDob());
								teacherDataBean.setTeaFirstName(res.get(0).getFirstName());
								teacherDataBean.setTeaLastName(res.get(0).getLastName());
								teacherDataBean.setTeaGender(res.get(0).getGender());
								teacherDataBean.setTeaPresentAddress(res.get(0).getAddress1_Street());
								teacherDataBean.setTeaFatherName(res.get(0).getFatherName());
								teacherDataBean.setTeaEmail(res.get(0).getEmailId());
								teacherDataBean.setTeaID(teaID);
								teacherDataBean.setTeaPosition(res.get(0).getPost());
								teacherDataBean.setTeaPhoneNo(res.get(0).getPhoneNumber());
								//teacherDataBean.setTeaPermanentZip(res.get(0).getPostcode1().getPostcode());
								//teacherDataBean.setTeaPresentState(res.get(0).getPostcode1().getState().getStateName());
								//teacherDataBean.setTeaPresentZip(res.get(0).getPostcode1().getPostcode());
								//teacherDataBean.setTeaPermanentState(res.get(0).getPostcode2().getState().getStateName());
								teacherDataBean.setTeaPermanentAddress(res.get(0).getAddress2_Street());
								teacherDataBean.setTeaMotherName(res.get(0).getMotherName());
								teacherDataBean.setTeaFatherName(res.get(0).getFatherName());
								teacherDataBean.setTeaYearOfPassing(res.get(0).getYearOfPassing());
								teacherDataBean.setTeaEduQualification(res.get(0).getQualification());
	
								finalList.add(teacherDataBean);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return finalList;
	}

	
	@SuppressWarnings("unchecked")
	private int getTeacherID(String teaID, int school_ID) {
		logger.info("-----------Inside getTeacherID method()----------------");
		Query q = null;
		int res = 0;
		try {
			if (!teaID.equalsIgnoreCase("")) {
				q = entitymanager.createQuery("from Teacher where rollNumber=? and school_ID=? and status='Active'");
				q.setParameter(1, teaID);
				q.setParameter(2, school_ID);
				List<Teacher> result = (List<Teacher>) q.getResultList();
				if (result.size() > 0) {
					res = result.get(0).getTeacher_ID();
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res;
	}

	/**
	 * @author Robert Arjun
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String checkTeacherID(TeacherDataBean teacherDataBean, String personID) {
		logger.info("-----------Inside checkTeacherID method()----------------");
		Query q = null;
		String s = "Exsist";
		if (personID != null) {
			try {
				q = entitymanager.createQuery("from Person where personRoleNumber=? ");
				q.setParameter(1, teacherDataBean.getTeaID());
				List<Person> rollSize = (List<Person>) q.getResultList();
				if (rollSize.size() > 0) {
					s = "Exsist";
				} else {
					s = "Available";
				}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
				s = "Exsist";
			}
		}
		return s;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getStateLists(String personID) {
		logger.info("-----------Inside getStateLists method()----------------");
		List<String> result = null;
		String personRole = "";
		Query q = null;
		if (personID != null) {
			try {
				personRole = getPersonRole(personID);
				if (personRole.equalsIgnoreCase("Admin")) {
					q = entitymanager.createQuery("select stateName from State");
					result = (List<String>) q.getResultList();
				}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getZipList(StudentDataBean studentdatabean) {
		logger.debug("-------inside ziplist------");
		Query q = null;
		String stcode;
		try {
			q = entitymanager.createQuery("from Postcode where postcode=?");
			q.setParameter(1, studentdatabean.getStuPresentZip());
			logger.debug("-----input code----" + studentdatabean.getStuPresentZip());
			List<Postcode> ziplist = (List<Postcode>) q.getResultList();
			logger.debug("----ziplist" + ziplist);
			if (ziplist.size() > 0) {
				stcode = ziplist.get(0).getState().getStateCode();
				q = null;
				q = entitymanager.createQuery("from State where state_code=? ");
				q.setParameter(1, stcode);
				logger.debug("-----state code----" + stcode);
				List<State> ziplist2 = (List<State>) q.getResultList();
				if (ziplist.size() > 0) {
					studentdatabean.setStuZipList(ziplist2.get(0).getStateName());
				}
			} else {
				studentdatabean.setStuZipList(null);
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.debug("-----state name----" + studentdatabean.getStuZipList());
		return "";

	}

	@Override
	@SuppressWarnings("unchecked")
	public String getZipList1(StudentDataBean studentdatabean) {
		logger.debug("-------inside ziplist1------");
		Query q = null;
		String stcode;
		try {
			q = entitymanager.createQuery("from Postcode where postcode=?");
			q.setParameter(1, studentdatabean.getStuPermanentZip());
			logger.debug("-----input code----" + studentdatabean.getStuPermanentZip());
			List<Postcode> ziplist = (List<Postcode>) q.getResultList();
			logger.debug("----ziplist" + ziplist);
			if (ziplist.size() > 0) {
				stcode = ziplist.get(0).getState().getStateCode();
				q = null;
				q = entitymanager.createQuery("from State where state_code=? ");
				q.setParameter(1, stcode);
				logger.debug("-----state code----" + stcode);
				List<State> ziplist2 = (List<State>) q.getResultList();
				if (ziplist2.size() > 0) {
					studentdatabean.setStuZipList1(ziplist2.get(0).getStateName());
				}
			} else {
				studentdatabean.setStuZipList1(null);
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.debug("-----state name----" + studentdatabean.getStuZipList1());
		return "";

	}
	
	/*Neela Oct 25 getAllTeacherInfo*/
	@Override
	@SuppressWarnings("unchecked")
	public List<TeacherDataBean> getAllTeacherInfo(String personID,TeacherDataBean teacherDataBean1) {
		logger.info("-----------Inside getAllTeacherInfo method()----------------");
		List<Person> rollStatus = null;
		List<TeacherDataBean> teacherList = null;
		Query q = null;
		int school_ID = 0;
		Query q1 = null;
		int perid = 0;
		int studid = 0;
		int clsid = 0;
		int teaid = 0;
		try {
			if (personID != null) {
				rollStatus = new ArrayList<Person>();
				teacherList = new ArrayList<TeacherDataBean>();
				rollStatus = getRollType(personID);
				logger.debug("Roll Size" + rollStatus.size());
				if (rollStatus.size() > 0) {
					if (rollStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
						school_ID = rollStatus.get(0).getSchool().getSchool_ID();
						q = entitymanager.createQuery("from Teacher where school_ID=? and status='Active'");
						q.setParameter(1, school_ID);
						List<Teacher> result = (List<Teacher>) q.getResultList();
						if (result.size() > 0) {
							for (int i = 0; i < result.size(); i++) {
								q1 = entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
								q1.setParameter(1, result.get(i).getTeacher_ID());
								List<TeacherDetail> teaDetails = (List<TeacherDetail>) q1.getResultList();
								if (teaDetails.size() > 0) {
									TeacherDataBean teacherDataBean = new TeacherDataBean();
									teacherDataBean.setNames(
											teaDetails.get(0).getFirstName() + " " + teaDetails.get(0).getLastName());
									teacherDataBean.setTeaEmail(teaDetails.get(0).getEmailId());
									teacherDataBean.setTeaID(result.get(i).getRollNumber());
									teacherDataBean.setTeaPhoneNo(teaDetails.get(0).getPhoneNumber());
									teacherDataBean.setCode(teaDetails.get(0).getPresentCode());
									teacherDataBean.setTeaPosition(teaDetails.get(0).getPost());
									teacherDataBean.setTeaDob(teaDetails.get(0).getDob());
									
									teacherDataBean.setTeaEmail(teaDetails.get(0).getEmailId());
									teacherDataBean.setTeaGender(teaDetails.get(0).getGender());
									teacherDataBean.setTeaWorkingHour(teaDetails.get(0).getWorkingHour());
									teacherDataBean.setTeaEduQualification(teaDetails.get(0).getQualification());
									teacherDataBean.setTeaYearOfPassing(teaDetails.get(0).getYearOfPassing());
									teacherDataBean.setTeaPercentage(teaDetails.get(0).getPercentage());
									
									
									teacherDataBean.setTeaPresentAddress(teaDetails.get(0).getAddress1_Street());
									teacherDataBean.setTeaPresentState(teaDetails.get(0).getPresentState());
									teacherDataBean.setTeaCountry(teaDetails.get(0).getPresentCountry());
									
								//	teacherDataBean.setPath("C://Photos/TeacherDavid01.jpeg");
									teacherList.add(teacherDataBean);
								}
							}
						}
					} else if (rollStatus.get(0).getPersonRole().equalsIgnoreCase("Student")) {
						school_ID = rollStatus.get(0).getSchool().getSchool_ID();
						perid = rollStatus.get(0).getPerson_ID();
						q = null;
						q = entitymanager.createQuery("from Student where person_ID=? and status='Active'");
						q.setParameter(1, perid);
						List<Student> studlist = (List<Student>) q.getResultList();
						if (studlist.size() > 0) {
							studid = studlist.get(0).getStudent_ID();
							q = null;
							q = entitymanager.createQuery("from StudentClass where student_ID=? and status='active'");
							q.setParameter(1, studid);
							List<StudentClass> studclslist = (List<StudentClass>) q.getResultList();
							if (studclslist.size() > 0) {
								clsid = studclslist.get(0).getClazz().getClass_ID();
								q = null;
								q = entitymanager.createQuery("from TeacherClass where class_ID=? and status='Active'");
								q.setParameter(1, clsid);
								List<TeacherClass> teaclass = (List<TeacherClass>) q.getResultList();
								logger.debug("-------teaclass size------" + teaclass.size());
								if (teaclass.size() > 0) {
									for (int s = 0; s < teaclass.size(); s++) {
										teaid = teaclass.get(s).getTeacher().getTeacher_ID();
										logger.debug("---------tacher id----" + teaid);
										q = null;

										q = entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
										q.setParameter(1, teaid);
										List<TeacherDetail> teaDetails = (List<TeacherDetail>) q.getResultList();
										logger.debug("----------list size------" + teaDetails.size());
										if (teaDetails.size() > 0) {
											TeacherDataBean teacherDataBean = new TeacherDataBean();
											teacherDataBean.setNames(teaDetails.get(0).getFirstName() + " "
													+ teaDetails.get(0).getLastName());
											teacherDataBean.setTeaEmail(teaDetails.get(0).getEmailId());
											teacherDataBean.setTeaID(teaDetails.get(0).getTeacher().getRollNumber());
											teacherDataBean.setTeaPhoneNo(teaDetails.get(0).getPhoneNumber());
											teacherDataBean.setTeaPosition(teaDetails.get(0).getPost());
											teacherDataBean.setTeaDob(teaDetails.get(0).getDob());
											
											teacherDataBean.setTeaEmail(teaDetails.get(0).getEmailId());
											teacherDataBean.setTeaGender(teaDetails.get(0).getGender());
											teacherDataBean.setTeaWorkingHour(teaDetails.get(0).getWorkingHour());
											teacherDataBean.setTeaEduQualification(teaDetails.get(0).getQualification());
											teacherDataBean.setTeaYearOfPassing(teaDetails.get(0).getYearOfPassing());
											teacherDataBean.setTeaPercentage(teaDetails.get(0).getPercentage());
										//	teacherDataBean.setPath("C://Photos/TeacherDavid01.jpeg");
											teacherList.add(teacherDataBean);

										}
									}

								}
							}
						}
					} else if (rollStatus.get(0).getPersonRole().equalsIgnoreCase("Parent")) {
						school_ID = rollStatus.get(0).getSchool().getSchool_ID();
						perid = rollStatus.get(0).getPerson_ID();
						StringTokenizer stringtoken = new StringTokenizer(teacherDataBean1.getTeaclsSection());
						String classname = stringtoken.nextToken("/");
						String section = teacherDataBean1.getTeaclsSection();
						String sectionName = section.substring(section.lastIndexOf("/") + 1);
								q = null;
								q = entitymanager
										.createQuery("from Class where className=? and classSection=? and school_ID=? and status='Active'");
								q.setParameter(1, classname);
								q.setParameter(2, sectionName);
								q.setParameter(3, school_ID);
								List<Class> studclslist = (List<Class>) q.getResultList();
								if (studclslist.size() > 0) {
									clsid = studclslist.get(0).getClass_ID();
									q = null;
									q = entitymanager
											.createQuery("from TeacherClass where class_ID=? and status='Active'");
									q.setParameter(1, clsid);
									List<TeacherClass> teaclass = (List<TeacherClass>) q.getResultList();
									logger.debug("-------teaclass size------" + teaclass.size());
									if (teaclass.size() > 0) {
										for (int s = 0; s < teaclass.size(); s++) {
											teaid = teaclass.get(s).getTeacher().getTeacher_ID();
											logger.debug("---------tacher id----" + teaid);
											q = null;

											q = entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
											q.setParameter(1, teaid);
											List<TeacherDetail> teaDetails = (List<TeacherDetail>) q.getResultList();
											logger.debug("----------list size------" + teaDetails.size());
											if (teaDetails.size() > 0) {
												TeacherDataBean teacherDataBean = new TeacherDataBean();
												teacherDataBean.setNames(teaDetails.get(0).getFirstName() + " "
														+ teaDetails.get(0).getLastName());
												teacherDataBean.setTeaEmail(teaDetails.get(0).getEmailId());
												teacherDataBean
														.setTeaID(teaDetails.get(0).getTeacher().getRollNumber());
												teacherDataBean.setTeaPhoneNo(teaDetails.get(0).getPhoneNumber());
												teacherDataBean.setTeaPosition(teaDetails.get(0).getPost());
												teacherDataBean.setTeaDob(teaDetails.get(0).getDob());
												
												teacherDataBean.setTeaEmail(teaDetails.get(0).getEmailId());
												teacherDataBean.setTeaGender(teaDetails.get(0).getGender());
												teacherDataBean.setTeaWorkingHour(teaDetails.get(0).getWorkingHour());
												teacherDataBean.setTeaEduQualification(teaDetails.get(0).getQualification());
												teacherDataBean.setTeaYearOfPassing(teaDetails.get(0).getYearOfPassing());
												teacherDataBean.setTeaPercentage(teaDetails.get(0).getPercentage());
												//teacherDataBean.setPath("C://Photos/TeacherDavid01.jpeg");
												teacherList.add(teacherDataBean);
											}

										}
									}
									else{
								          teacherList=new ArrayList<TeacherDataBean>();
								         }
								}
							
					}
					else {
						logger.debug("Not valid Person");
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return teacherList;
	}

	/**
	 * This method is used to check person roll tyoe
	 * 
	 * @param personID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Person> getRollType(String personID) {
		logger.info("-----------Inside getRollType method()----------------");
		Query q = null;
		List<Person> rolls = null;
		try {

			if (personID != null) {
				int pid = Integer.parseInt(personID);
				q = entitymanager.createQuery("from Person where person_ID=? and status='Active'");
				q.setParameter(1, pid);
				rolls = (List<Person>) q.getResultList();
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return rolls;
	}

	@Override
	//@SuppressWarnings("unchecked")
	public List<TeacherDataBean> getTeacherInfo(String personID, String teaID) {
		logger.info("-----------Inside getTeacherInfo method()----------------");
		//logger.debug("--------------Inside Dao getTeacherInfo method calling-------------" + teaID);
		List<TeacherDataBean> teaList = null;
		List<Person> rollStatus = null;
		int school_ID = 0;
		int teacher_ID = 0;
		List<TeacherDetail> teaDetails = null;
		List<TeacherClass> teaClass = null;
		List<TeacherSubject> teaSub = null;
		List<ImagePath> teaPath = null;
		List<Postcode> teaPost1 = null;
		List<Postcode> teaPost2 = null;
		TeacherDataBean teacherData = new TeacherDataBean();
		//Query q = null;
		try {
			teaList = new ArrayList<TeacherDataBean>();
			teaDetails = new ArrayList<TeacherDetail>();
			teaClass = new ArrayList<TeacherClass>();
			teaSub = new ArrayList<TeacherSubject>();
			rollStatus = new ArrayList<Person>();
			teaPath = new ArrayList<ImagePath>();
			teaPost1 = new ArrayList<Postcode>();
			teaPost2 = new ArrayList<Postcode>();
			if (personID != null && teaID != null) {
				rollStatus = getRollType(personID);
				logger.debug("Roll Size" + rollStatus.size());
				if (rollStatus.size() > 0) {
					if (rollStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
						school_ID = rollStatus.get(0).getSchool().getSchool_ID();
						teacher_ID = getTeacherID(teaID,school_ID);
						if (teacher_ID > 0) {
							teaDetails = getTeacherDetails(teacher_ID, personID);
							teacherData.setTeacherDet(teaDetails);
							teaClass = getTeaClass(teacher_ID, personID);
							teacherData.setTeacherCls(teaClass);
							teaSub = getTeaSub(teacher_ID, personID);
							teacherData.setTeacherSub(teaSub);
							teaPath = getImagePathName(personID, teacher_ID);
							teacherData.setTeacherImgPath(teaPath);
							teaList.add(teacherData);
						}

					} else if (rollStatus.get(0).getPersonRole().equalsIgnoreCase("Student")) {
						school_ID = rollStatus.get(0).getSchool().getSchool_ID();
						teacher_ID = getTeacherID(teaID,school_ID);
						if (teacher_ID > 0) {
							teaDetails = getTeacherDetails(teacher_ID, personID);
							teacherData.setTeacherDet(teaDetails);
							teaClass = getTeaClass(teacher_ID, personID);
							teacherData.setTeacherCls(teaClass);
							teaSub = getTeaSub(teacher_ID, personID);
							teacherData.setTeacherSub(teaSub);
							teaPath = getImagePathName(personID, teacher_ID);
							teacherData.setTeacherImgPath(teaPath);
							teaList.add(teacherData);
						}

					} else if (rollStatus.get(0).getPersonRole().equalsIgnoreCase("Parent")) {
						school_ID = rollStatus.get(0).getSchool().getSchool_ID();
						teacher_ID = getTeacherID(teaID,school_ID);
						if (teacher_ID > 0) {
							teaDetails = getTeacherDetails(teacher_ID, personID);
							teacherData.setTeacherDet(teaDetails);
							teaClass = getTeaClass(teacher_ID, personID);
							teacherData.setTeacherCls(teaClass);
							teaSub = getTeaSub(teacher_ID, personID);
							teacherData.setTeacherSub(teaSub);
							teaPath = getImagePathName(personID, teacher_ID);
							teacherData.setTeacherImgPath(teaPath);
							teaList.add(teacherData);
						}

					} else if (rollStatus.get(0).getPersonRole().equalsIgnoreCase("Teacher")) {
						school_ID = rollStatus.get(0).getSchool().getSchool_ID();
						teacher_ID = getTeacherID(teaID,school_ID);
						if (teacher_ID > 0) {
							teaDetails = getTeacherDetails(teacher_ID, personID);
							teacherData.setTeacherDet(teaDetails);
							teaClass = getTeaClass(teacher_ID, personID);
							teacherData.setTeacherCls(teaClass);
							teaSub = getTeaSub(teacher_ID, personID);
							teacherData.setTeacherSub(teaSub);
							teaPath = getImagePathName(personID, teacher_ID);
							teacherData.setTeacherImgPath(teaPath);
							teaList.add(teacherData);
						}

					}
				}

			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return teaList;
	}

	@SuppressWarnings("unchecked")
	private List<ImagePath> getImagePathName(String personID, int teacher_ID) {
		logger.info("-----------Inside getImagePathName method()----------------");
		Query q = null;
		List<ImagePath> sub = null;
		try {
			sub = new ArrayList<ImagePath>();
			if (personID != null && teacher_ID > 0) {
				q = entitymanager.createQuery("from ImagePath where teacher_ID=? and rollStatus='Teacher'");
				q.setParameter(1, teacher_ID);
				sub = (List<ImagePath>) q.getResultList();
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return sub;
	}

	@SuppressWarnings("unchecked")
	private List<TeacherSubject> getTeaSub(int teacher_ID, String personID) {
		logger.info("-----------Inside getTeaSub method()----------------");
		Query q = null;
		List<TeacherSubject> sub = null;
		try {
			sub = new ArrayList<TeacherSubject>();
			if (personID != null && teacher_ID > 0) {
				q = entitymanager.createQuery("from TeacherSubject where teacher_ID=? and status='Active'");
				q.setParameter(1, teacher_ID);
				sub = (List<TeacherSubject>) q.getResultList();
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return sub;
	}

	@SuppressWarnings("unchecked")
	private List<TeacherClass> getTeaClass(int teacher_ID, String personID) {
		logger.info("-----------Inside getTeaClass method()----------------");
		Query q = null;
		List<TeacherClass> teaClass = null;
		try {
			teaClass = new ArrayList<TeacherClass>();
			if (personID != null && teacher_ID > 0) {
				q = entitymanager.createQuery("from TeacherClass where teacher_ID=? and status='Active'");
				q.setParameter(1, teacher_ID);
				teaClass = (List<TeacherClass>) q.getResultList();
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}

		return teaClass;
	}

	@SuppressWarnings("unchecked")
	private List<TeacherDetail> getTeacherDetails(int teacher_ID, String personID) {
		Query q = null;
		List<TeacherDetail> teaDetails = null;
		try {
			if (personID != null && teacher_ID > 0) {
				teaDetails = new ArrayList<TeacherDetail>();
				q = entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
				q.setParameter(1, teacher_ID);
				teaDetails = (List<TeacherDetail>) q.getResultList();
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return teaDetails;
	}

	@Override
	@Transactional(value = "transactionManager")
	public String updateTeacher(String personID, TeacherDataBean teacherDataBean) {
		logger.info("-----------Inside updateTeacher method()----------------");
		List<Person> rollStatus = null;
		int school_ID = 0;
		int teacher_ID = 0;
		int hasTea_ID = 0;
		String status = "Fail";
		//int postcode_ID1 = 0;
		//int postcode_ID2 = 0;
		int imgID = 0;
		List<TeacherDetail> teaDetails = null;
		List<ImagePath> teaPath = null;
	//	Query q = null;
		try {
			teaDetails = new ArrayList<TeacherDetail>();
			teaPath = new ArrayList<ImagePath>();
			if (personID != null && teacherDataBean.getTeaID() != null) {
				rollStatus = getRollType(personID);
				logger.debug("Roll Size" + rollStatus.size());
				if (rollStatus.size() > 0) {
					teacherDataBean.setSchoolLogo(rollStatus.get(0).getSchool().getLogopath());
					teacherDataBean.setSchoolName(rollStatus.get(0).getSchool().getName());
					teacherDataBean.setSchoolID(rollStatus.get(0).getSchool().getSchool_ID());
					if (rollStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
						school_ID = rollStatus.get(0).getSchool().getSchool_ID();
						teacher_ID = getTeacherID(teacherDataBean.getTeaID(),school_ID);
						if (teacher_ID > 0) {
							teaDetails = getTeacherDetails(teacher_ID, personID);
							if (teaDetails.size() > 0) {
								hasTea_ID = teaDetails.get(0).getHasteacherdet_ID();
								/*postcode_ID1 = getPostcodeID(teacherDataBean.getTeaPresentZip());
								postcode_ID2 = getPostcodeID(teacherDataBean.getTeaPermanentZip());
								if (postcode_ID1 > 0 && postcode_ID2 > 0) {*/
									TeacherDetail details = entitymanager.find(TeacherDetail.class, hasTea_ID);
									details.setAddress1_Street(teacherDataBean.getTeaPresentAddress());
									//details.setPostcode1(entitymanager.find(Postcode.class, postcode_ID1));
									details.setAddress2_Street(teacherDataBean.getTeaPermanentAddress());
									//details.setPostcode2(entitymanager.find(Postcode.class, postcode_ID2));
									details.setDob(teacherDataBean.getTeaDob());
									details.setEmailId(teacherDataBean.getTeaEmail());
									details.setFatherName(teacherDataBean.getTeaFatherName());
									details.setFirstName(teacherDataBean.getTeaFirstName());
									details.setGender(teacherDataBean.getTeaGender());
									details.setLastName(teacherDataBean.getTeaLastName());
									details.setPercentage(teacherDataBean.getTeaPercentage());
									details.setPhoneNumber(teacherDataBean.getTeaPhoneNo());
									details.setPost(teacherDataBean.getTeaPosition());
									details.setQualification(teacherDataBean.getTeaEduQualification());
									details.setWorkingHour(teacherDataBean.getTeaWorkingHour());
									details.setYearOfPassing(teacherDataBean.getTeaYearOfPassing());
									details.setMotherName(teacherDataBean.getTeaMotherName());
									details.setPresentCity(teacherDataBean.getTeaCity());
									details.setPermanentCity(teacherDataBean.getTeaCity1());
									details.setPresentCountry(teacherDataBean.getTeaCountry());
									details.setPermanentCountry(teacherDataBean.getTeaCountry1());
									details.setPresentState(teacherDataBean.getTeaState());
									details.setPermanentState(teacherDataBean.getTeaState1());
									details.setPresentCode(teacherDataBean.getCode());
									details.setPermanentCode(teacherDataBean.getCode1());
									details.setPresentPostal(teacherDataBean.getTeaPostal());
									details.setPermanentPostal(teacherDataBean.getTeaPostal1());
									details.setPhoneNumber1(teacherDataBean.getTeaPhoneNo1());
									details.setManagement(teacherDataBean.getManagement());
									entitymanager.merge(details);

									if (teacherDataBean.getTeafilePath() != null) {
										teaPath = getImagePathName(personID, teacher_ID);
										StringTokenizer stoken = new StringTokenizer(teacherDataBean.getTeafilePath());
										Date d = dateFormat.parse(stoken.nextToken("/"));
										String tempPath = teacherDataBean.getTeafilePath();
										String path = tempPath.substring(tempPath.lastIndexOf("/") + 1);
										if (teaPath.size() > 0) {
											imgID = teaPath.get(0).getImage_ID();
											ImagePath imagepath = entitymanager.find(ImagePath.class, imgID);
											imagepath.setDate(d);
											imagepath.setPath(path);
											imagepath.setName(teacherDataBean.getTeaID());
											entitymanager.merge(imagepath);

										}else{
											ImagePath imagepath = new ImagePath();
											imagepath.setDate(d);
											imagepath.setPath(path);
											imagepath.setName(teacherDataBean.getTeaID());
											imagepath.setRollStatus("Teacher");
											imagepath.setTeacher(entitymanager.find(Teacher.class, teacher_ID));
											imagepath.setStatus("Active");
											entitymanager.persist(imagepath);
										}
									}
									String subjectStatus = updateTeacherSubject(teacher_ID, personID, teacherDataBean,
											school_ID);
									String classSratus = updateTeacherClass(teacherDataBean, school_ID, teacher_ID,
											personID);
									if (subjectStatus.equalsIgnoreCase("Success")
											&& classSratus.equalsIgnoreCase("Success")) {
										status = "Success";
									}
								
							}

						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	private String updateTeacherClass(TeacherDataBean teacherDataBean, int school_ID, int teacher_ID, String personID) {
		logger.info("-----------Inside updateTeacherClass method()----------------");
		String status = "Failure";
		Query q = null;
		int class_ID = 0;
		int hasclass_ID = 0;
		List<TeacherClass> teaClass = null;
		try {
			teaClass = new ArrayList<TeacherClass>();
			if (personID != null && school_ID > 0) {
				teaClass = getTeaClass(teacher_ID, personID);
				if (teaClass.size() > 0) {
					for (int m = 0; m < teaClass.size(); m++) {
						hasclass_ID = teaClass.get(m).getHasteacher_ID();
						TeacherClass teacClass = entitymanager.find(TeacherClass.class, hasclass_ID);
						teacClass.setStatus("De-Active");
						entitymanager.merge(teacClass);
						entitymanager.flush();
						entitymanager.clear();
					}
				}
				for (int i = 0; i < teacherDataBean.getTeaClassTargetList().size(); i++) {
					StringTokenizer st = new StringTokenizer(teacherDataBean.getTeaClassTargetList().get(i));
					String className = st.nextToken("/");
					String sectionName = teacherDataBean.getTeaClassTargetList().get(i);
					String section = sectionName.substring(sectionName.lastIndexOf("/") + 1);
					logger.debug(
							"----------------------------------Inserting Teacher 5 Begin-----------------------------------------------");

					q = entitymanager.createQuery("from Class where className=? and classSection=? and school_ID=? and status='Active'");
					q.setParameter(1, className);
					q.setParameter(2, section);
					q.setParameter(3, school_ID);
					List<Class> result = (List<Class>) q.getResultList();
					if (result.size() > 0) {
						class_ID = result.get(0).getClass_ID();
						TeacherClass teacherClass = new TeacherClass();
						teacherClass.setClazz(entitymanager.find(Class.class, class_ID));
						teacherClass.setTeacher(entitymanager.find(Teacher.class, teacher_ID));
						teacherClass.setStatus("Active");
						entitymanager.persist(teacherClass);
						entitymanager.flush();
						entitymanager.clear();
						status = "Success";
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	private String updateTeacherSubject(int teacher_ID, String personID, TeacherDataBean teacherDataBean,
			int school_ID) {
		logger.info("-----------Inside updateTeacherSubject method()----------------");
		List<TeacherSubject> teaSub = null;
		String status = "Failure";
		Query q = null;
		int subject_ID = 0;
		int storeSubId = 0;
		try {
			teaSub = new ArrayList<TeacherSubject>();
			if (personID != null && school_ID > 0) {
				teaSub = getTeaSub(teacher_ID, personID);
				if (teaSub.size() > 0) {

					for (int j = 0; j < teaSub.size(); j++) {
						storeSubId = teaSub.get(j).getHasteacher_subject_ID();
						TeacherSubject teacherSubject = entitymanager.find(TeacherSubject.class, storeSubId);
						teacherSubject.setStatus("De-Active");
						entitymanager.merge(teacherSubject);
						entitymanager.flush();
						entitymanager.clear();
					}

					for (int i = 0; i < teacherDataBean.getTeaSubjectTargetList().size(); i++) {
						StringTokenizer st = new StringTokenizer(teacherDataBean.getTeaSubjectTargetList().get(i));
						String subjectName = st.nextToken("/");
						String subjectCode = teacherDataBean.getTeaSubjectTargetList().get(i);
						String code = subjectCode.substring(subjectCode.lastIndexOf("/") + 1);

						q = entitymanager.createQuery("from Subject where sujectName=? and subjectCode=? and status='Active' and school_ID=?");
						q.setParameter(1, subjectName);
						q.setParameter(2, code);
						q.setParameter(3, school_ID);						
						List<Subject> result = (List<Subject>) q.getResultList();
						if (result.size() > 0) {
							subject_ID = result.get(0).getSubject_ID();
							TeacherSubject teacherSubject = new TeacherSubject();
							teacherSubject.setSchool(entitymanager.find(School.class, school_ID));
							teacherSubject.setTeacher(entitymanager.find(Teacher.class, teacher_ID));
							teacherSubject.setSubject(entitymanager.find(Subject.class, subject_ID));
							teacherSubject.setStatus("Active");
							entitymanager.persist(teacherSubject);
							entitymanager.flush();
							entitymanager.clear();
							status = "Success";
						}

					}
				}else{

					for (int i = 0; i < teacherDataBean.getTeaSubjectTargetList().size(); i++) {
						StringTokenizer st = new StringTokenizer(teacherDataBean.getTeaSubjectTargetList().get(i));
						String subjectName = st.nextToken("/");
						String subjectCode = teacherDataBean.getTeaSubjectTargetList().get(i);
						String code = subjectCode.substring(subjectCode.lastIndexOf("/") + 1);

						q = entitymanager.createQuery("from Subject where sujectName=? and subjectCode=? and status='Active' and school_ID=?");
						q.setParameter(1, subjectName);
						q.setParameter(2, code);
						q.setParameter(3, school_ID);
						List<Subject> result = (List<Subject>) q.getResultList();
						if (result.size() > 0) {
							subject_ID = result.get(0).getSubject_ID();
							TeacherSubject teacherSubject = new TeacherSubject();
							teacherSubject.setSchool(entitymanager.find(School.class, school_ID));
							teacherSubject.setTeacher(entitymanager.find(Teacher.class, teacher_ID));
							teacherSubject.setSubject(entitymanager.find(Subject.class, subject_ID));
							teacherSubject.setStatus("Active");
							entitymanager.persist(teacherSubject);
							entitymanager.flush();
							entitymanager.clear();
							status = "Success";
						}

					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}

	@Override
	@Transactional(value = "transactionManager")
	public String deleteTeacher(String personID, TeacherDataBean teacherDataBean) {
		logger.info("-----------Inside deleteTeacher method()----------------");
		String status = "Fail";
		List<Person> rollStatus = null;
		int school_ID = 0;
		int teacher_ID = 0;
		try {
			rollStatus = new ArrayList<Person>();
			if (personID != null && teacherDataBean.getTeaID() != null) {
				rollStatus = getRollType(personID);
				logger.debug("Roll Size" + rollStatus.size());
				if (rollStatus.size() > 0) {
					if (rollStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
						school_ID = rollStatus.get(0).getSchool().getSchool_ID();
						teacher_ID = getTeacherID(teacherDataBean.getTeaID(),school_ID);
						if (teacher_ID > 0) {
							Teacher teacher = entitymanager.find(Teacher.class, teacher_ID);
							teacher.setStatus("De-Active");
							entitymanager.merge(teacher);
							status = "Success";
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}

	/**
	 * @author Jeevini This method is used to get Class and section list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getClassList(String personID, StudentDataBean studentdatabean) {
		logger.info("-----------Inside getClassList method()----------------");
		List<Person> roleStatus = null;
		int school_ID = 0;
		List<String> stt = new ArrayList<String>();
		ArrayList<StudentDataBean> picklist2 = new ArrayList<StudentDataBean>();
		Query q = null;
		try {
			studentdatabean.setStuPickList(new ArrayList<StudentDataBean>());
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						q = entitymanager.createQuery("from Class where school_ID=? and status='Active'");
						q.setParameter(1, school_ID);
						List<Class> picklist1 = (List<Class>) q.getResultList();
						if (picklist1.size() > 0) {
							for (int i = 0; i < picklist1.size(); i++) {
								StudentDataBean pickobj = new StudentDataBean();
								pickobj.setStuCls(
										picklist1.get(i).getClassName() + "/" + picklist1.get(i).getClassSection());
								picklist2.add(pickobj);
								studentdatabean.setStuPickList(picklist2);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}

		return stt;

	}

	/**
	 * @author Jeevini This method is used to insert student details
	 */
	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	@Override
	public String insertStudent(String personID, StudentDataBean studentDataBean) {
		logger.info("-----------Inside insertStudent method()----------------");
		//logger.debug("-------inside insert student------");
		String status = "Failure";
		Query q = null;
		Date date = new Date();
		List<Person> roleStatus = null;
		int school_ID = 0;
		Timestamp timestamp = new Timestamp(date.getTime());
		try {
			int schlid = 0;
			int pid = 0;
			int studid = 0;
			//int postid = 0;
			//int postid1 = 0;
			int classid = 0;
			int user_ID = 0;
			Query insetMenu1 = null;
			String secPwd = "";
			String EncPwd = "";
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					studentDataBean.setSchoolName(roleStatus.get(0).getSchool().getName());
					studentDataBean.setSchoolLogo(roleStatus.get(0).getSchool().getLogopath());
					if (roleStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
						schlid = roleStatus.get(0).getSchool().getSchool_ID();
						q = null;
						q = entitymanager.createQuery("from Person where person_role_number=?");
						q.setParameter(1, studentDataBean.getStuRollNo());
						List<Person> perlist3 = (List<Person>) q.getResultList();
						if (perlist3.size() > 0) {
							status = "Exsist";
						} else {
							Person person = new Person();
							person.setPersonRole("Student");
							person.setSchool(entitymanager.find(School.class, schlid));
							person.setPersonRoleNumber(studentDataBean.getStuRollNo());
							person.setStatus("Active");
							if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
								person.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
								person.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
							}else{
								person.setCreatedDate(date);
								person.setCreatedTime(timestamp);
							}	
							entitymanager.persist(person);
							logger.debug("-------person table inserted------");
							q = null;
							q = entitymanager.createQuery("from Person where person_role=? and person_role_number=?");
							q.setParameter(1, "Student");
							q.setParameter(2, studentDataBean.getStuRollNo());
							List<Person> perlist = (List<Person>) q.getResultList();
							pid = perlist.get(0).getPerson_ID();
							logger.debug("---pid---" + pid);
							logger.debug("----perlist size---" + perlist.size());
							if (perlist.size() > 0) {
								Student student = new Student();
								student.setPerson(entitymanager.find(Person.class, pid));
								student.setSchool(entitymanager.find(School.class, schlid));
								student.setRollNumber(studentDataBean.getStuRollNo());
								if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
									student.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
									student.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
								}else{
									student.setCreatedDate(date);
									student.setCreatedTime(timestamp);
								}	
								student.setStatus("Active");
								entitymanager.persist(student);
								logger.debug("-------student table inserted------");
							}
						
							q = null;
							q = entitymanager.createQuery("from Student where roll_number=?");
							q.setParameter(1, studentDataBean.getStuRollNo());
							List<Student> stdlist2 = (List<Student>) q.getResultList();
							if (stdlist2.size() > 0) {
								studid = stdlist2.get(0).getStudent_ID();
							}
							logger.debug("----stud id---" + studid);
							StudentDetail studdetail = new StudentDetail();
							studdetail.setStudentid(entitymanager.find(Student.class, studid));
							studdetail.setFirstName(studentDataBean.getStuFirstName());
							studdetail.setLastName(studentDataBean.getStuLastName());
							studdetail.setFatherName(studentDataBean.getStuFatherName());
							studdetail.setMotherName(studentDataBean.getStuMotherName());
							studdetail.setDob(studentDataBean.getStuDob());
							studdetail.setGender(studentDataBean.getStuGender());
							studdetail.setPhoneNumber(studentDataBean.getStuPhoneNo());
							studdetail.setEmailId(studentDataBean.getStuEmail());
							studdetail.setFatherOccupation(studentDataBean.getStuFatherOccu());
							studdetail.setMotherOccupation(studentDataBean.getStuMotherOccu());
							studdetail.setFatherIncome(studentDataBean.getStuFatherIncome());
							studdetail.setAddress1_Street(studentDataBean.getStuPresentAddress());
							studdetail.setAddress2_Street(studentDataBean.getStuPermanentAddress());
							studdetail.setPresentCity(studentDataBean.getStuPresentCity());
							studdetail.setPermanentCity(studentDataBean.getStuPermanentCity());
							studdetail.setPresentCountry(studentDataBean.getStuPresentCountry());
							studdetail.setPermanentCountry(studentDataBean.getStuPermanentCountry());
							studdetail.setPresentState(studentDataBean.getStuPresentState());
							studdetail.setPermanentState(studentDataBean.getStuPermanentState());
							studdetail.setPresentPostal(studentDataBean.getStuPresentpostal());
							studdetail.setPermanentPostal(studentDataBean.getStuPermanentpostal());
							studdetail.setPhoneNumber1(studentDataBean.getStuPhoneNo1());
							studdetail.setPresentcode(studentDataBean.getCode());
							studdetail.setPermanentcode(studentDataBean.getCode1());
							//studdetail.setPostcode1(entitymanager.find(Postcode.class, postid));
							//studdetail.setPostcode2(entitymanager.find(Postcode.class, postid1));
							studdetail.setReligion(studentDataBean.getReligion());
							studdetail.setCaste(studentDataBean.getCaste());
							studdetail.setClassification(studentDataBean.getClassification());
							studdetail.setCommunityCertificateNo(studentDataBean.getCommunityCertificateNo());
							studdetail.setIssuedAuthority(studentDataBean.getIssuedAuthority());
							studdetail.setIssueddate(studentDataBean.getIssuedDate());
							studdetail.setBankAccNo(studentDataBean.getBankAccNo());
							studdetail.setBankName(studentDataBean.getBankName());
							studdetail.setBranchName(studentDataBean.getBranchName());
							studdetail.setBranchCode(studentDataBean.getBranchCode());
							studdetail.setIfscCode(studentDataBean.getIfscCode());
							studdetail.setMicrNo(studentDataBean.getMicrNo());
							studdetail.setAadharCardNo(studentDataBean.getAadharCardNo());
							studdetail.setRationCardNo(studentDataBean.getRationCardNo());
							studdetail.setEmploymentCardNo(studentDataBean.getEmploymentCardNo());
							studdetail.setEmisNo(studentDataBean.getEmisNo());
							studdetail.setBusPassNo(studentDataBean.getBusPassNo());
							entitymanager.persist(studdetail);
							logger.debug("-------student details table inserted------");
							if(!studentDataBean.getStufilePath().equalsIgnoreCase(""))
							{
								StringTokenizer stoken = new StringTokenizer(studentDataBean.getStufilePath());
								Date d = dateFormat.parse(stoken.nextToken("/"));
								logger.debug("check hase photo");
								String tempPath = studentDataBean.getStufilePath();
								String path = tempPath.substring(tempPath.lastIndexOf("/") + 1);
								// Insert ImagePath table
					            ImagePath imagepath = new ImagePath();
								imagepath.setDate(d);
								imagepath.setPath(path);
								imagepath.setName(studentDataBean.getStuRollNo());
								imagepath.setRollStatus("Student");
								imagepath.setStudent(entitymanager.find(Student.class, studid));
								imagepath.setStatus("Active");
								entitymanager.persist(imagepath);
								logger.debug("-------Image path table inserted------");
								}
							
							StringTokenizer st = new StringTokenizer(studentDataBean.getStuCls());
							String className = st.nextToken("/");
							String sectionName = studentDataBean.getStuCls();
							String section = sectionName.substring(sectionName.lastIndexOf("/") + 1);
							logger.debug("----class name---" + className);
							logger.debug("Student Section"+section);
							q=null;
							q = entitymanager.createQuery("from Class where class_name=? and classSection=? and school_ID=? and status='Active'");
							q.setParameter(1, className);
							q.setParameter(2, section);
							q.setParameter(3, roleStatus.get(0).getSchool().getSchool_ID());
							List<Class> classlist = (List<Class>) q.getResultList();
							if (classlist.size() > 0) {
								classid = classlist.get(0).getClass_ID();
							}
							logger.debug("----class id---" + classid);
							StudentClass studclass = new StudentClass();
							studclass.setStudent(entitymanager.find(Student.class, studid));
							studclass.setClazz(entitymanager.find(Class.class, classid));
							studclass.setStatus("Active");
							entitymanager.persist(studclass);
							logger.debug("-------student class table inserted------");
							secPwd = RandomPasswordGenerator.GenerateSecurePassword(studentDataBean.getStuRollNo());// used
																													// to
																													// generate
																													// secure
																													// random
																													// password
							if (secPwd != null) {
								studentDataBean.setStuSecurePasword(secPwd);
								studentDataBean.setStuUsername(studentDataBean.getStuRollNo());
								EncPwd = PasswordEncryption.GeneratePaswword(studentDataBean.getStuSecurePasword());// used
																													// to
																													// generate
																													// Encryption
																													// Password
								logger.debug("Encription " + EncPwd + "----" + secPwd);
								if (EncPwd != null) {
									// Insert User Table
									User user = new User();
									user.setStatus("Active");
									user.setUsername(studentDataBean.getStuRollNo());
									user.setPerson(entitymanager.find(Person.class, pid));
									user.setPassword(EncPwd);
									user.setLastLoginTime(timestamp);
									entitymanager.persist(user);
									logger.debug("-------user table inserted------");
									insetMenu1 = entitymanager.createQuery("from User where username=?");
									insetMenu1.setParameter(1, studentDataBean.getStuRollNo());
									List<User> userList = (List<User>) insetMenu1.getResultList();
									if (userList.size() > 0) {
										user_ID = userList.get(0).getHas_user_ID();
									logger.debug("user ID" + user_ID);
										String menuStatus = insertStudentMenus(user_ID,school_ID);// call
																						// insertStudentMenus
																						// method
																						// call
										logger.debug("Menu Status" + menuStatus);
										status = "Success";
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}

		return status;
	}

	/**
	 * @author Jeevini This method is used to get the Image path
	 */
	@Override
	//@SuppressWarnings("unchecked")
	@SuppressWarnings("unchecked")
	public List<StudentDataBean> getImagePath1(String personID, String stuRollNo) {
		logger.info("-----------Inside getImagePath1 method()----------------");
		logger.debug("-------inside getImagePath1------");
		Query q = null;
		Query q1 = null;
		int student_ID = 0;
		List<StudentDataBean> finalList1 = null;
		try {
			if ((personID != null) && (!stuRollNo.equalsIgnoreCase(""))) {
				student_ID = getStudentID(stuRollNo);
				logger.debug("----student_id----" + student_ID);
				if (student_ID > 0) {
					q = entitymanager.createQuery("from StudentClass where student_ID=? and status='Active'");
					q.setParameter(1, student_ID);
					List<StudentClass> studclslist = (List<StudentClass>) q.getResultList();
					if(studclslist.size()>0)
						finalList1 = new ArrayList<StudentDataBean>();
						q1 = entitymanager.createQuery("from StudentDetail where student_ID=?");
						q1.setParameter(1, student_ID);
						List<StudentDetail> res = (List<StudentDetail>) q1.getResultList();
						if (res.size() > 0) {
							StudentDataBean studentDataBean = new StudentDataBean();
							q = null;
							q = entitymanager.createQuery("from ImagePath where student_ID=? and rollStatus='Student'");
							q.setParameter(1, student_ID);
							List<ImagePath> result = (List<ImagePath>) q.getResultList();
							if (result.size() > 0) {
								studentDataBean.setPath(result.get(0).getPath());
								studentDataBean.setPathDate(result.get(0).getDate());
							}							
							studentDataBean.setStuDob(res.get(0).getDob());
							studentDataBean.setStuFirstName(res.get(0).getFirstName());
							studentDataBean.setStuLastName(res.get(0).getLastName());
							studentDataBean.setStuMotherName(res.get(0).getMotherName());
							studentDataBean.setStuGender(res.get(0).getGender());
							studentDataBean.setStuPresentAddress(res.get(0).getAddress1_Street());
							studentDataBean.setStuPermanentAddress(res.get(0).getAddress2_Street());
							//studentDataBean.setStuPermanentState(res.get(0).getPostcode2().getState().getStateName());
							//studentDataBean.setStuPresentZip(res.get(0).getPostcode1().getPostcode());
							studentDataBean.setStuFatherName(res.get(0).getFatherName());
							studentDataBean.setStuEmail(res.get(0).getEmailId());
							studentDataBean.setStuRollNo(stuRollNo);
							studentDataBean.setStuPhoneNo(res.get(0).getPhoneNumber());
							//studentDataBean.setStuPermanentZip(res.get(0).getPostcode1().getPostcode());
							//studentDataBean.setStuPresentState(res.get(0).getPostcode1().getState().getStateName());
							studentDataBean.setStuFatherOccu(res.get(0).getFatherOccupation());
							studentDataBean.setStuMotherOccu(res.get(0).getMotherOccupation());
							studentDataBean.setStuFatherIncome(res.get(0).getFatherIncome());
							studentDataBean.setStuCls(studclslist.get(0).getClazz().getClassName() + "/"
									+ studclslist.get(0).getClazz().getClassSection());
							finalList1.add(studentDataBean);
						}
					}
				}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn(" exception - "+e);
		}
		return finalList1;
	}

	/**
	 * @author Jeevini This method is used to get student id
	 */
	@SuppressWarnings("unchecked")
	private int getStudentID(String stuRollNo) {
		logger.info("-----------Inside getStudentID method()----------------");
		Query q = null;
		int res1 = 0;
		try {
			if (!stuRollNo.equalsIgnoreCase("")) {
				q = entitymanager.createQuery("from Student where roll_number=? and status='Active'");
				q.setParameter(1, stuRollNo);
				List<Student> result = (List<Student>) q.getResultList();
				if (result.size() > 0) {
					res1 = result.get(0).getStudent_ID();
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res1;
	}

	/**
	 * @author Jeevini This method is used to insert Menus
	 * @param school_ID 
	 */
	@Transactional(value = "transactionManager")
	private String insertStudentMenus(int user_ID, int school_ID) {
		logger.info("-----------Inside insertStudentMenus method()----------------");
		//logger.debug("----inside insertstudentmenu------");
		String res = "fail";
		int mainMenuID1 = 0;
		String res1 = "F";
		String res2 = "F";
		String res3 = "F";
		String res4 = "F";
		String res5 = "F";
		String res6 = "F";
		String res7 = "F";
		String res8 = "F";
		String res9 = "F";
		try {
			// Insert UserProduct table
			logger.debug("-----user_ID------" + user_ID);
			if (user_ID > 0) {
				mainMenuID1 = getMainMenu("TIM200");
				if (mainMenuID1 > 0) {

					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res1 = "S";
				}
				mainMenuID1 = getMainMenu("NOT200");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res2 = "S";
				}
				mainMenuID1 = getMainMenu("ATT200");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res3 = "S";
				}
				if(school_ID==1 || school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5 || school_ID==6 ){
					mainMenuID1 = getMainMenu("HW100");
					if (mainMenuID1 > 0) {
						UserProduct user = new UserProduct();
						user.setUser(entitymanager.find(User.class, user_ID));
						user.setStatus("Active");
						user.setProduct(entitymanager.find(Product.class, mainMenuID1));
						entitymanager.persist(user);
						entitymanager.flush();
						entitymanager.clear();
						res9 = "S";
					}
				}
				
				mainMenuID1 = getMainMenu("STU200");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res4 = "S";
				}
				mainMenuID1 = getMainMenu("REP200");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res5 = "S";
				}
				mainMenuID1 = getMainMenu("PAY200");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res6 = "S";
				}
				mainMenuID1 = getMainMenu("LIB200");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res7 = "S";
				}
				if(school_ID==1 || school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5 || school_ID==6 ){
					mainMenuID1 = getMainMenu("BOO200");
					if (mainMenuID1 > 0) {
						UserProduct user = new UserProduct();
						user.setUser(entitymanager.find(User.class, user_ID));
						user.setStatus("Active");
						user.setProduct(entitymanager.find(Product.class, mainMenuID1));
						entitymanager.persist(user);
						entitymanager.flush();
						entitymanager.clear();
						res8 = "S";
					}
				}else{
					mainMenuID1 = getMainMenu("BOO600");
					if (mainMenuID1 > 0) {
						UserProduct user = new UserProduct();
						user.setUser(entitymanager.find(User.class, user_ID));
						user.setStatus("Active");
						user.setProduct(entitymanager.find(Product.class, mainMenuID1));
						entitymanager.persist(user);
						entitymanager.flush();
						entitymanager.clear();
						res8 = "S";
					}
				}
				
				mainMenuID1 = getMainMenu("PRO200");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res9 = "S";
				}
				if (res1.equalsIgnoreCase("S") && res2.equalsIgnoreCase("S") && res3.equalsIgnoreCase("S")
						&& res4.equalsIgnoreCase("S") && res5.equalsIgnoreCase("S") && res6.equalsIgnoreCase("S")
						&& res7.equalsIgnoreCase("S") && res8.equalsIgnoreCase("S") && res9.equalsIgnoreCase("S")) {
					res = "Sucess";
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res;
	}

	/**
	 * @author Jeevini This method is used to view the student profile
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<StudentDataBean> getStudentInfo(String personID, StudentDataBean studentDataBean) {
		logger.info("-----------Inside getStudentInfo method()----------------");
		//logger.debug("----inside getStudentInfo------");
		Query q = null;
		List<StudentParent> studrollno=new ArrayList<StudentParent>();
		List<StudentDataBean> studlist1 = new ArrayList<StudentDataBean>();
		int studid = 0;
		int classid = 0;
		String studno;
		List<Person> roleStatus = null;
		int school_ID = 0;
		String classname = null;
		try {
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (roleStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
						List<Class> clslist=null;
						if(studentDataBean.getTeaclssection().equalsIgnoreCase("all")){							
							q = null;
							q = entitymanager
									.createQuery("from Student where school_ID=? and status='Active'");
							q.setParameter(1, school_ID);
							logger.debug("schoolid---"+school_ID);
							List<Student> student = (List<Student>) q.getResultList();
							if (student.size() > 0) {
								for(Student students:student){
									q = null;
									q = entitymanager.createQuery("from StudentDetail where student_ID=?");
									q.setParameter(1, students.getStudent_ID());
									List<StudentDetail> studdetaillist = (List<StudentDetail>) q.getResultList();
									q = null;
									q = entitymanager.createQuery(
											"from ImagePath where student_ID=? and roll_status='Student'");
									q.setParameter(1, studid);
									List<ImagePath> imagelist = (List<ImagePath>) q.getResultList();
									logger.debug("---imagelist size----" + imagelist.size());
									studentDataBean.setImageList(imagelist);
									if (studdetaillist.size() > 0) {
										StudentDataBean studobj = new StudentDataBean();
										studobj.setStuFirstName(studdetaillist.get(0).getFirstName());
										studobj.setStuRollNo(students.getRollNumber());
										studobj.setNames(studdetaillist.get(0).getFirstName());
										studobj.setStuDob(studdetaillist.get(0).getDob());
										studobj.setStuGender(studdetaillist.get(0).getGender());
										studobj.setStuPhoneNo(studdetaillist.get(0).getPhoneNumber());
										studlist1.add(studobj);
										studentDataBean.setStudlist(studlist1);
										if(imagelist.size() > 0){
											studentDataBean.setImagePath1(imagelist.get(0).getPath());
											studentDataBean.setImageDate(imagelist.get(0).getDate());
										}else{
											studentDataBean.setImagePath1(null);
											studentDataBean.setImageDate(null);
										}									
									}
								}								
							}
						}else{
							StringTokenizer st = new StringTokenizer(studentDataBean.getTeaclssection());
							String className = st.nextToken("/");
							String sectionName = studentDataBean.getTeaclssection();
							String section = sectionName.substring(sectionName.lastIndexOf("/") + 1);							
							q = null;
							q = entitymanager.createQuery("from Class where class_name=? and class_section=? and school_ID=? and status='Active'");
							q.setParameter(1, className);
							q.setParameter(2, section);
							q.setParameter(3, school_ID);
							clslist = (List<Class>) q.getResultList();
							if (clslist.size() > 0) {
								for(int j=0;j<clslist.size();j++){								
								classid = clslist.get(j).getClass_ID();
								q = entitymanager.createQuery("from StudentClass where class_ID=? and status='Active'");
								q.setParameter(1, classid);
								List<StudentClass> studclasslist1 = (List<StudentClass>) q.getResultList();
								if (studclasslist1.size() > 0) {
									for (int i = 0; i < studclasslist1.size(); i++) {
										studid = studclasslist1.get(i).getStudent().getStudent_ID();
										studno = studclasslist1.get(i).getStudent().getRollNumber();
										q = null;
										q = entitymanager
												.createQuery("from Student where rollNumber=? and school_ID=? and status='Active'");
										q.setParameter(1, studclasslist1.get(i).getStudent().getRollNumber());
										q.setParameter(2, school_ID);
										List<Student> student = (List<Student>) q.getResultList();
										if (student.size() > 0) {
											q = null;
											q = entitymanager.createQuery("from StudentDetail where student_ID=?");
											q.setParameter(1, student.get(0).getStudent_ID());
											List<StudentDetail> studdetaillist = (List<StudentDetail>) q.getResultList();
											q = null;
											q = entitymanager.createQuery(
													"from ImagePath where student_ID=? and roll_status='Student'");
											q.setParameter(1, studid);
											List<ImagePath> imagelist = (List<ImagePath>) q.getResultList();
											logger.debug("---imagelist size----" + imagelist.size());
											studentDataBean.setImageList(imagelist);
											if (studdetaillist.size() > 0) {
												StudentDataBean studobj = new StudentDataBean();
												studobj.setStuFirstName(studdetaillist.get(0).getFirstName());
												studobj.setStuRollNo(studno);
												studobj.setNames(studdetaillist.get(0).getFirstName());
												studobj.setStuDob(studdetaillist.get(0).getDob());
												//studobj.setStuEmail(studdetaillist.get(0).getEmailId());
												studobj.setStuPhoneNo(studdetaillist.get(0).getPhoneNumber());
												studobj.setStuGender(studdetaillist.get(0).getGender());
												studlist1.add(studobj);
												studentDataBean.setStudlist(studlist1);
												if(imagelist.size() > 0){
													studentDataBean.setImagePath1(imagelist.get(0).getPath());
													studentDataBean.setImageDate(imagelist.get(0).getDate());
												}else{
													studentDataBean.setImagePath1(null);
													studentDataBean.setImageDate(null);
												}
												
												// studobj.setStuCls(studclasslist.get(0).getClazz().getClassName());
											}
										}
									}
								}
								}
							}
						}			
						
					} else if (roleStatus.get(0).getPersonRole().equalsIgnoreCase("Teacher")) {
						StringTokenizer st = new StringTokenizer(studentDataBean.getTeaclssection());
						String className = st.nextToken("/");
						String sectionName = studentDataBean.getTeaclssection();
						String section = sectionName.substring(sectionName.lastIndexOf("/") + 1);
						q = null;
						q = entitymanager.createQuery("from Class where class_name=? and class_section=? and school_ID=? and status='Active'");
						q.setParameter(1, className);
						q.setParameter(2, section);
						q.setParameter(3, school_ID);
						List<Class> clslist = (List<Class>) q.getResultList();
						if (clslist.size() > 0) {
							classid = clslist.get(0).getClass_ID();
							q = entitymanager.createQuery("from StudentClass where class_ID=? and status='Active'");
							q.setParameter(1, classid);
							List<StudentClass> studclasslist1 = (List<StudentClass>) q.getResultList();
							if (studclasslist1.size() > 0) {
								for (int i = 0; i < studclasslist1.size(); i++) {
									studid = studclasslist1.get(i).getStudent().getStudent_ID();
									studno = studclasslist1.get(i).getStudent().getRollNumber();
									q = null;
									q = entitymanager
											.createQuery("from Student where rollNumber=? and status='Active'");
									q.setParameter(1, studclasslist1.get(i).getStudent().getRollNumber());
									List<Student> student = (List<Student>) q.getResultList();
									if (student.size() > 0) {
										q = null;
										q = entitymanager.createQuery("from StudentDetail where student_ID=?");
										q.setParameter(1, student.get(0).getStudent_ID());
										List<StudentDetail> studdetaillist = (List<StudentDetail>) q.getResultList();
										q = null;
										q = entitymanager.createQuery(
												"from ImagePath where student_ID=? and roll_status='Student'");
										q.setParameter(1, studid);
										List<ImagePath> imagelist = (List<ImagePath>) q.getResultList();
										logger.debug("---imagelist size----" + imagelist.size());
										studentDataBean.setImageList(imagelist);
										if (studdetaillist.size() > 0) {
											StudentDataBean studobj = new StudentDataBean();
											studobj.setStuFirstName(studdetaillist.get(0).getFirstName());
											studobj.setStuRollNo(studno);
											studobj.setNames(studdetaillist.get(0).getFirstName());
											studobj.setStuDob(studdetaillist.get(0).getDob());
											studobj.setStuEmail(studdetaillist.get(0).getEmailId());
											studobj.setStuPhoneNo(studdetaillist.get(0).getPhoneNumber());
											studobj.setStuGender(studdetaillist.get(0).getGender());
											studlist1.add(studobj);
											studentDataBean.setStudlist(studlist1);
											
											if(imagelist.size() > 0){
												studentDataBean.setImagePath1(imagelist.get(0).getPath());
												studentDataBean.setImageDate(imagelist.get(0).getDate());
											}else{
												studentDataBean.setImagePath1(null);
												studentDataBean.setImageDate(null);
											}
											studobj.setStuCls(classname);
										}
									}
								}
							}
						}
					} else if (roleStatus.get(0).getPersonRole().equalsIgnoreCase("Student")) {
						school_ID = roleStatus.get(0).getSchool().getSchool_ID();
						q = entitymanager.createQuery("from Student where roll_number=? and status='Active'");
						q.setParameter(1, studentDataBean.getTestroll());
						List<Student> perlist = (List<Student>) q.getResultList();
						if (perlist.size() > 0) {
							for (int i = 0; i < perlist.size(); i++) {
								studid = perlist.get(i).getStudent_ID();
								studno = perlist.get(i).getRollNumber();
								q = null;
								q = entitymanager.createQuery("from StudentClass where student_ID=?");
								q.setParameter(1, studid);
								List<StudentClass> studclasslist = (List<StudentClass>) q.getResultList();
								q = null;
								q = entitymanager.createQuery("from StudentDetail where student_ID=?");
								q.setParameter(1, studid);
								List<StudentDetail> studdetaillist = (List<StudentDetail>) q.getResultList();
								q = null;
								q = entitymanager
										.createQuery("from ImagePath where student_ID=? and roll_status='Student'");
								q.setParameter(1, studid);
								List<ImagePath> imagelist = (List<ImagePath>) q.getResultList();
								logger.debug("---imagelist size----" + imagelist.size());
								studentDataBean.setImageList(imagelist);
								if (studdetaillist.size() > 0) {
									StudentDataBean studobj = new StudentDataBean();
									studobj.setStuFirstName(studdetaillist.get(0).getFirstName());
									studobj.setNames(studdetaillist.get(0).getFirstName());
									studobj.setStuDob(studdetaillist.get(0).getDob());
									studobj.setStuRollNo(studno);
									studobj.setStuGender(studdetaillist.get(0).getGender());
									studobj.setStuEmail(studdetaillist.get(0).getEmailId());
									studobj.setStuPhoneNo(studdetaillist.get(0).getPhoneNumber());
									studlist1.add(studobj);
									studentDataBean.setStudlist(studlist1);
									if(imagelist.size() > 0){
										studentDataBean.setImagePath1(imagelist.get(0).getPath());
										studentDataBean.setImageDate(imagelist.get(0).getDate());
									}else{
										studentDataBean.setImagePath1(null);
										studentDataBean.setImageDate(null);
									}
									studobj.setStuCls(studclasslist.get(0).getClazz().getClassName());
								}
							}
						}
					} else if (roleStatus.get(0).getPersonRole().equalsIgnoreCase("Parent")) {
						logger.debug("---------inside parent-----");						
						school_ID = roleStatus.get(0).getSchool().getSchool_ID();
						String rollno=roleStatus.get(0).getPersonRoleNumber();
						q = entitymanager.createQuery("from Parent where rollNumber=? and status='Active'");
						q.setParameter(1, rollno);
						List<Parent> paarlist = (List<Parent>) q.getResultList();
						if(paarlist.size() > 0){
							int parID=paarlist.get(0).getParent_ID();
							studrollno= getParentStudent(personID, school_ID, parID);
							if(studrollno.size()>0){
								for (int j = 0; j < studrollno.size(); j++) {
									q = entitymanager.createQuery("from Student where roll_number=? and status='Active'");
									q.setParameter(1, studrollno.get(j).getStudent().getRollNumber());
									List<Student> perlist = (List<Student>) q.getResultList();
									if (perlist.size() > 0) {
										for (int i = 0; i < perlist.size(); i++) {
											studid = perlist.get(i).getStudent_ID();
											studno = perlist.get(i).getRollNumber();
											q = null;
											q = entitymanager.createQuery("from StudentClass where student_ID=?");
											q.setParameter(1, studid);
											List<StudentClass> studclasslist = (List<StudentClass>) q.getResultList();
											q = null;
											q = entitymanager.createQuery("from StudentDetail where student_ID=?");
											q.setParameter(1, studid);
											List<StudentDetail> studdetaillist = (List<StudentDetail>) q.getResultList();
											q = null;
											q = entitymanager
													.createQuery("from ImagePath where student_ID=? and roll_status='Student'");
											q.setParameter(1, studid);
											List<ImagePath> imagelist = (List<ImagePath>) q.getResultList();
											logger.debug("---imagelist size----" + imagelist.size());
											studentDataBean.setImageList(imagelist);
											if (studdetaillist.size() > 0) {
												StudentDataBean studobj = new StudentDataBean();
												studobj.setStuFirstName(studdetaillist.get(0).getFirstName());
												studobj.setNames(studdetaillist.get(0).getFirstName());
												studobj.setStuDob(studdetaillist.get(0).getDob());
												studobj.setStuRollNo(studno);
												studobj.setStuGender(studdetaillist.get(0).getGender());
												studobj.setStuEmail(studdetaillist.get(0).getEmailId());
												studobj.setStuPhoneNo(studdetaillist.get(0).getPhoneNumber());
												studlist1.add(studobj);
												studentDataBean.setStudlist(studlist1);
												if(imagelist.size() > 0){
													studentDataBean.setImagePath1(imagelist.get(0).getPath());
													studentDataBean.setImageDate(imagelist.get(0).getDate());
												}else{
													studentDataBean.setImagePath1(null);
													studentDataBean.setImageDate(null);
												}
												studobj.setStuCls(studclasslist.get(0).getClazz().getClassName());
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
			logger.warn(" exception - "+e);
		}
		return studlist1;
	}

	/**
	 * @author Jeevini This method is used to view the student information when
	 *         the view button clicked
	 */

	@Override
	@SuppressWarnings("unchecked")
	public List<StudentDetail> getStudentView(StudentDataBean studentDataBean,String personID) {
		logger.info("-----------Inside getStudentView method()----------------");
		//logger.debug("------inside getstudentview--------");
		Query q = null;
		int studid = 0;
		int schoolid=0;
		List<Person> roleStatus = null;
		List<StudentDetail> studlist1 = null;
	//	List<ImagePath> stuPath = null;
		if (studentDataBean.getStuRollNo() != null) {
			roleStatus=new ArrayList<Person>();
			roleStatus=getRollType(personID);
			if(roleStatus.size()>0){
				schoolid=roleStatus.get(0).getSchool().getSchool_ID();
			try {
				q = entitymanager.createQuery("from Student where roll_number=? and school_ID=? and status='Active'");
				q.setParameter(1, studentDataBean.getStuRollNo());
				q.setParameter(2, schoolid);
				List<Student> studlist = (List<Student>) q.getResultList();
				logger.debug("---studlist size----" + studlist.size());
				if (studlist.size() > 0) {
					studid = studlist.get(0).getStudent_ID();
				}
				q = null;
				q = entitymanager.createQuery("from StudentDetail where student_ID=?");
				q.setParameter(1, studid);
				studlist1 = (List<StudentDetail>) q.getResultList();
				logger.debug("---studlist1 size----" + studlist1.size());
				q = null;
				q = entitymanager.createQuery("from ImagePath where student_ID=? and roll_status='Student'");
				q.setParameter(1, studid);
				List<ImagePath> imagelist = (List<ImagePath>) q.getResultList();
				logger.debug("---imagelist size----" + imagelist.size());
				studentDataBean.setImageList(imagelist);
				if (imagelist.size() > 0) {
					studentDataBean.setImagePath1(imagelist.get(0).getPath());
					studentDataBean.setImageDate(imagelist.get(0).getDate());
				}

			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
		}
		}
		logger.debug("------outside getstudentview--------");
		return studlist1;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StudentDetail> getStudentDetails(StudentDataBean studentDataBean,String personID) {
		logger.info("-----------Inside getStudentDetails method()----------------");
		Query q = null;
		int studid = 0;
		int schoolid=0;
		List<Person> roll=null;
		List<StudentDetail> studlist1 = null;
		if (studentDataBean.getStuRollNo() != null) {
			try {
				roll=new ArrayList<Person>();
				roll=getRollType(personID);
				if(roll.size()>0){
					schoolid=roll.get(0).getSchool().getSchool_ID();
				studentDataBean.setClassName(getclassSection(studentDataBean.getStuRollNo(),schoolid));
				logger.debug("-----class name--" + studentDataBean.getClassName());
				q = entitymanager.createQuery("from Student where roll_number=? and school_ID=? and status='Active'");
				q.setParameter(1, studentDataBean.getStuRollNo());
				q.setParameter(2, schoolid);
				List<Student> studlist = (List<Student>) q.getResultList();
				if (studlist.size() > 0) {
					studid = studlist.get(0).getStudent_ID();
				}
				q = null;
				q = entitymanager.createQuery("from StudentDetail where student_ID=?");
				q.setParameter(1, studid);
				studlist1 = (List<StudentDetail>) q.getResultList();
				logger.debug("---studlist1 size----" + studlist1.size());
				q = null;
				q = entitymanager.createQuery("from ImagePath where student_ID=? and roll_status='Student'");
				q.setParameter(1, studid);
				List<ImagePath> imagelist = (List<ImagePath>) q.getResultList();
				logger.debug("---imagelist size----" + imagelist.size());
				studentDataBean.setImageList(imagelist);
				if (imagelist.size() > 0) {
					studentDataBean.setImagePath1(imagelist.get(0).getPath());
					studentDataBean.setImageDate(imagelist.get(0).getDate());
				}
				}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
		}
		logger.debug("------outside getstudentview--------");
		return studlist1;
	}

	/**
	 * @author Jeevini This method is used to get student id
	 */
	@SuppressWarnings("unchecked")
	public int getStudentId(String stuRollNo) {
		Query q = null;
		int result = 0;
		try {
			q = entitymanager.createQuery("from Student where roll_number=?");
			q.setParameter(1, stuRollNo);
			List<Student> studlist = (List<Student>) q.getResultList();
			if (studlist.size() > 0) {
				result = studlist.get(0).getStudent_ID();
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return result;
	}

	/**
	 * @author Jeevini This method is used to get student class
	 */
	@SuppressWarnings("unchecked")
	public String getStudentClass(String stuRollNo,String personID) {
		logger.info("-----------Inside getStudentClass method()----------------");
		Query q = null;
		String result = null;
		int studid = 0;
		//int classid = 0;
		int schoolid=0;
		List<Person> rolllist=null;
		try {
			rolllist=new ArrayList<Person>();
			rolllist=getRollType(personID);
			if(rolllist.size()>0){
				schoolid=rolllist.get(0).getSchool().getSchool_ID();
			q = entitymanager.createQuery("from Student where roll_number=? and school_ID=?");
			q.setParameter(1, stuRollNo);
			q.setParameter(2, schoolid);
			List<Student> studlist = (List<Student>) q.getResultList();
			if (studlist.size() > 0) {
				studid = studlist.get(0).getStudent_ID();
			}
			q = null;
			q = entitymanager.createQuery("from StudentClass where student_ID=?");
			q.setParameter(1, studid);
			List<StudentClass> studlist1 = (List<StudentClass>) q.getResultList();
			if (studlist1.size() > 0) {
				result = studlist1.get(0).getClazz().getClassName()+"/"+studlist1.get(0).getClazz().getClassSection();
			}
			}
			logger.info("result"+result);
		} catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return result;
	}

	/**
	 * @author Jeevini This method is used to get student class and section
	 */
	@SuppressWarnings("unchecked")
	private String getclassSection(String stuRollNo,int schoolid) {
		logger.info("-----------Inside getclassSection method()----------------");
		Query q = null;
		String result = null;
		int studid = 0;
		//int classid = 0;
		try {
			q = entitymanager.createQuery("from Student where roll_number=? and school_ID=?");
			q.setParameter(1, stuRollNo);
			q.setParameter(2, schoolid);
			List<Student> studlist = (List<Student>) q.getResultList();
			if (studlist.size() > 0) {
				studid = studlist.get(0).getStudent_ID();
			}
			q = null;
			q = entitymanager.createQuery("from StudentClass where student_ID=?");
			q.setParameter(1, studid);
			List<StudentClass> studlist1 = (List<StudentClass>) q.getResultList();
			if (studlist1.size() > 0) {
				result = studlist1.get(0).getClazz().getClassName() + "/"
						+ studlist1.get(0).getClazz().getClassSection();

			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return result;
	}

	/**
	 * @author Jeevini This method is used to check student roll number
	 */

	@Override
	@SuppressWarnings("unchecked")
	public String checkStudentRollno(StudentDataBean studentDataBean, String stuRollNo) {
		logger.info("-----------Inside checkStudentRollno method()----------------");
		Query q = null;
		String s = "Exsist";
		try {
			q = entitymanager.createQuery("from Person where personRoleNumber=? and status='Active' ");
			q.setParameter(1, studentDataBean.getStuRollNo());
			List<Person> rollSize = (List<Person>) q.getResultList();
			if (rollSize.size() > 0) {
				s = "Exsist";
			} else {
				s = "Available";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			s = "Exsist";
		}
		return s;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StudentDataBean> getAllStudentInfo(String personID) {
		logger.info("-----------Inside getAllStudentInfo method()----------------");
		List<Person> roleStatus = null;
		List<StudentDataBean> studentList = null;
		Query q = null;
		int school_ID = 0;
		Query q1 = null;
		try {
			if (personID != null) {
				roleStatus = new ArrayList<Person>();
				studentList = new ArrayList<StudentDataBean>();
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					if (roleStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
						school_ID = roleStatus.get(0).getSchool().getSchool_ID();
						q = entitymanager.createQuery("from Student where school_ID=? and status='Active'");
						q.setParameter(1, school_ID);
						List<Student> result = (List<Student>) q.getResultList();
						if (result.size() > 0) {
							for (int i = 0; i < result.size(); i++) {
								q1 = entitymanager.createQuery("from StudentDetail where student_ID=?");
								q1.setParameter(1, result.get(i).getStudent_ID());
								List<StudentDetail> studdetaillist = (List<StudentDetail>) q1.getResultList();
								if (studdetaillist.size() > 0) {
									StudentDataBean studentDataBean = new StudentDataBean();
									studentDataBean.setNames(studdetaillist.get(0).getFirstName() + " "
											+ studdetaillist.get(0).getLastName());
									studentDataBean.setStuEmail(studdetaillist.get(0).getEmailId());
									studentDataBean.setStuRollNo(result.get(i).getRollNumber());
									studentDataBean.setStuPhoneNo(studdetaillist.get(0).getPhoneNumber());
									studentDataBean.setStuDob(studdetaillist.get(0).getDob());
									studentDataBean.setPath("C://Photos/85.jpeg");
									studentList.add(studentDataBean);
								}
							}
						}
					} else {
						logger.debug("Not valid Person");
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return studentList;
	}

	@Override
	public List<String> getStudentRollNumber(String personID, String name) {
		logger.info("-----------Inside getStudentRollNumber method()----------------");
		List<Person> roleStatus = null;
		List<String> IDList = null;
		//Query q = null;
		int school_ID = 0;
		try {
			IDList = new ArrayList<String>();
			roleStatus = new ArrayList<Person>();
			if (personID != null && name != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					logger.debug("school id "+school_ID);
					if (school_ID > 0) {
						IDList = getRollNumber(personID, school_ID, name);
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return IDList;
	}

	@SuppressWarnings("unchecked")
	private List<String> getRollNumber(String personID, int school_ID, String name) {
		logger.info("-----------Inside getRollNumber method()----------------");
		Query q = null;
		int classID = 0;
		Query q1 = null;int stuids = 0;
		List<String> IDLis = null;
		try {
			IDLis = new ArrayList<String>();
			if (personID != null && name != null) {
				StringTokenizer st = new StringTokenizer(name);
				String className = st.nextToken("/");
				String sectionName = name;
				String section = sectionName.substring(sectionName.lastIndexOf("/") + 1);
				q = entitymanager.createQuery(
						"from Class where school_ID=? and className=? and classSection=? and status='Active'");
				q.setParameter(1, school_ID);
				q.setParameter(2, className);
				q.setParameter(3, section);
				List<Class> result = (List<Class>) q.getResultList();
				if (result.size() > 0) {
					classID = result.get(0).getClass_ID();
					q1 = entitymanager.createQuery("from StudentClass where class_ID=? and status='Active'");
					q1.setParameter(1, classID);
					List<StudentClass> res = (List<StudentClass>) q1.getResultList();
					if (res.size() > 0) {
						for (StudentClass idNumber : res) {
							if(idNumber.getStudent().getSchool().getSchool_ID()==school_ID){
									stuids = idNumber.getStudent().getStudent_ID();
									q=null;
									q=entitymanager.createQuery("from StudentDetail where student_ID = ?");
									q.setParameter(1, stuids);
									List<StudentDetail> studentdetailList=(List<StudentDetail>)q.getResultList();
									if(studentdetailList.size()>0){
										IDLis.add(studentdetailList.get(0).getFirstName()+studentdetailList.get(0).getLastName()+"/"+idNumber.getStudent().getRollNumber());
									}
							}	
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return IDLis;
	}

	@Override
	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	public String insertParents(String personID, ParentsDataBean parentsDataBean) {

		logger.info("-----------Inside insertParents method()----------------");
		List<Person> roleStatus = null;
		String status = "Fail";
		Query q = null;
		int newPerson_ID = 0;
		Query q1 = null;
		int parentID = 0;
		int school_ID = 0;
		int studentID = 0;
		String secPwd = "";
		String EncPwd = "";
		Query q2 = null;
		List<Parent> parentLists = null;
		int user_ID = 0;
		try {
			roleStatus = new ArrayList<Person>();
			parentLists = new ArrayList<Parent>();

			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					parentsDataBean.setSchoolLogo(roleStatus.get(0).getSchool().getLogopath());
					if (roleStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
						school_ID = roleStatus.get(0).getSchool().getSchool_ID();
						parentsDataBean.setSchoolID(school_ID);
						logger.debug("scholls id---"+school_ID);
						if (school_ID > 0) {
							parentLists = getParentLists(personID, school_ID, parentsDataBean);
							logger.debug("size "+parentLists.size());
							if (parentLists.size() > 0) {
								status = "Exsist";
							} else {

								// Insert Person Table
								Person person = new Person();
								if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
									person.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
									person.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
								}else{
									person.setCreatedDate(date);
									person.setCreatedTime(timestamp);
								}	
								person.setPersonRoleNumber(parentsDataBean.getParParentID());
								person.setPersonRole("Parent");
								person.setStatus("Active");
								person.setSchool(entitymanager.find(School.class, school_ID));
								entitymanager.persist(person);

								q = entitymanager.createQuery("from Person where personRoleNumber=?");
								q.setParameter(1, parentsDataBean.getParParentID());
								List<Person> result = (List<Person>) q.getResultList();
								if (result.size() > 0) {
									newPerson_ID = result.get(0).getPerson_ID();
									logger.debug("Persion ID" + newPerson_ID);
									Parent parent = new Parent();
									if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
										parent.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
										parent.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
									}else{
										parent.setCreatedDate(date);
										parent.setCreatedTime(timestamp);
									}
									parent.setRollNumber(parentsDataBean.getParParentID());
									parent.setPerson(entitymanager.find(Person.class, newPerson_ID));
									parent.setStatus("Active");
									parent.setSchool(entitymanager.find(School.class, school_ID));
									entitymanager.persist(parent);

									q1 = entitymanager.createQuery("from Parent where rollNumber=?");
									q1.setParameter(1, parentsDataBean.getParParentID());
									List<Parent> res = (List<Parent>) q1.getResultList();
									logger.debug("Parent rollnumber result----->" + res.size());
									if (res.size() > 0) {
										parentID = res.get(0).getParent_ID();
										ParentDetail parentDetail = new ParentDetail();
										parentDetail.setEmaiId(parentsDataBean.getParEmail());
										parentDetail.setFirstName(parentsDataBean.getParFirstName());
										parentDetail.setLastName(parentsDataBean.getParLastName());
										parentDetail.setParent(entitymanager.find(Parent.class, parentID));
										parentDetail.setRelation(parentsDataBean.getParentRelation());
										try {
											if (!parentsDataBean.getParPhoneNo().equalsIgnoreCase("") && !parentsDataBean.getCountryCode().equalsIgnoreCase("")
										&& parentsDataBean.getParPhoneNo()!=null && parentsDataBean.getCountryCode() != null) {
												parentDetail.setPhoneNumber(parentsDataBean.getParPhoneNo());
												parentDetail.setCountrycode(parentsDataBean.getCountryCode().split("-")[0]);
											}
											else{
												parentDetail.setPhoneNumber("");
												parentDetail.setCountrycode("");
											}
										} catch (NullPointerException e) {
											parentDetail.setPhoneNumber("");
											parentDetail.setCountrycode("");
										}
										entitymanager.persist(parentDetail);
										try{
											if (parentsDataBean.getParfilePath() != null || !parentsDataBean.getParfilePath().equals("")) {
												StringTokenizer stoken = new StringTokenizer(parentsDataBean.getParfilePath());
												Date d = dateFormat.parse(stoken.nextToken("/"));
												String tempPath = parentsDataBean.getParfilePath();
												String path = tempPath.substring(tempPath.lastIndexOf("/") + 1);

												// Insert ImagePath table
												ImagePath imagepath = new ImagePath();
												imagepath.setDate(d);
												imagepath.setPath(path);
												imagepath.setName(parentsDataBean.getParParentID());
												imagepath.setRollStatus("Parent");
												imagepath.setParent(entitymanager.find(Parent.class, parentID));
												imagepath.setStatus("Active");
												entitymanager.persist(imagepath);
											}
										}catch (Exception e) {
											logger.warn("Exception -->"+e.getMessage());
										}
										
										for (int i = 0; i < parentsDataBean.getParentdetails().size(); i++) {
											String student=parentsDataBean.getParentdetails().get(i).getParStudID();
											String studentnid = student.substring(student.lastIndexOf("/") + 1);
											studentID = getStudentID(studentnid);
											logger.debug("studentID--->" + studentID);
											if (studentID > 0) {
												StudentParent studentParent = new StudentParent();
												studentParent.setStudent(entitymanager.find(Student.class, studentID));
												studentParent.setParent(entitymanager.find(Parent.class, parentID));
												studentParent.setStatus("Active");
												entitymanager.persist(studentParent);
												entitymanager.flush();
												entitymanager.clear();
											}
										}
										secPwd = RandomPasswordGenerator
												.GenerateSecurePassword(parentsDataBean.getParParentID());// used
																											// to
																											// generate
																											// secure
																											// random
																											// password
										logger.debug("secPwd--->" + secPwd);
										if (secPwd != null) {
											parentsDataBean.setParSecurePasword(secPwd);
											parentsDataBean.setParUsername(parentsDataBean.getParParentID());
											EncPwd = PasswordEncryption
													.GeneratePaswword(parentsDataBean.getParSecurePasword());// used
																												// to
																												// generate
																												// Encryption
																												// Password
											logger.debug("Encription " + EncPwd + "----" + secPwd);
											if (EncPwd != null) {
												logger.debug(
														"----------------------------------Insert Login Teacher 7 Begin-----------------------------------------------");
												// Insert User Table
												User user = new User();
												user.setStatus("Active");
												user.setUsername(parentsDataBean.getParUsername());
												user.setPerson(entitymanager.find(Person.class, newPerson_ID));
												user.setPassword(EncPwd);
												entitymanager.persist(user);
												q2 = entitymanager.createQuery("from User where username=? and person_ID=?");
												q2.setParameter(1, parentsDataBean.getParUsername());
												q2.setParameter(2, newPerson_ID);
												List<User> userList = (List<User>) q2.getResultList();
												if (userList.size() > 0) {
													user_ID = userList.get(0).getHas_user_ID();
													logger.debug("user ID" + user_ID);

													String menuStatus = insertParentMenus(user_ID,school_ID);// call
																									// insertTeacherMenus
																									// method
																									// call
													if (menuStatus.equalsIgnoreCase("Sucess")) {
														status = "Success";
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			e.printStackTrace();
		}
		return status;
	
	}

	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	private String insertParentMenus(int user_ID, int school_ID) {
		logger.info("-----------Inside insertParentMenus method()----------------");
		String res = "fail";
		int mainMenuID1 = 0;
		String res1 = "F";
		String res2 = "F";
		String res3 = "F";
		String res4 = "F";
		String res5 = "F";
		String res6 = "F";
		String res7 = "F";
		String res8 = "F";
		String res9 = "F";
		try {
			// Insert UserProduct table
			if (user_ID > 0) {
				mainMenuID1 = getMainMenu("TIM300");
				if (mainMenuID1 > 0) {

					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res1 = "S";
				}
				mainMenuID1 = getMainMenu("NOT300");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res2 = "S";
				}
				if (school_ID!=37) {
				mainMenuID1 = getMainMenu("ATT300");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res3 = "S";
				}
				}
			/*	if(school_ID==1 || school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5 || school_ID==6 ){*/
					mainMenuID1 = getMainMenu("HW100");
					if (mainMenuID1 > 0) {
						UserProduct user = new UserProduct();
						user.setUser(entitymanager.find(User.class, user_ID));
						user.setStatus("Active");
						user.setProduct(entitymanager.find(Product.class, mainMenuID1));
						entitymanager.persist(user);
						entitymanager.flush();
						entitymanager.clear();
						res9 = "S";
					}
				/*}*/				
				if (school_ID!=37) {
				mainMenuID1 = getMainMenu("STU300");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res4 = "S";
				}
				}
				if (school_ID!=37) {
				mainMenuID1 = getMainMenu("REP300");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res5 = "S";
				}
				}
				mainMenuID1 = getMainMenu("PAY300");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res6 = "S";
				}
				mainMenuID1 = getMainMenu("LIB300");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res7 = "S";
				}
				if(school_ID==1 || school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5 || school_ID==6 ){
					mainMenuID1 = getMainMenu("BOO300");
					if (mainMenuID1 > 0) {
						UserProduct user = new UserProduct();
						user.setUser(entitymanager.find(User.class, user_ID));
						user.setStatus("Active");
						user.setProduct(entitymanager.find(Product.class, mainMenuID1));
						entitymanager.persist(user);
						entitymanager.flush();
						entitymanager.clear();
						res8 = "S";
					}
				}else{
					mainMenuID1 = getMainMenu("BOO700");
					if (mainMenuID1 > 0) {
						UserProduct user = new UserProduct();
						user.setUser(entitymanager.find(User.class, user_ID));
						user.setStatus("Active");
						user.setProduct(entitymanager.find(Product.class, mainMenuID1));
						entitymanager.persist(user);
						entitymanager.flush();
						entitymanager.clear();
						res8 = "S";
					}
				}
				
				mainMenuID1 = getMainMenu("PRO300");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res9 = "S";
				}
				/*if (res1.equalsIgnoreCase("S") && res2.equalsIgnoreCase("S") && res3.equalsIgnoreCase("S")
						&& res4.equalsIgnoreCase("S") && res5.equalsIgnoreCase("S") && res6.equalsIgnoreCase("S")
						&& res7.equalsIgnoreCase("S") && res8.equalsIgnoreCase("S") && res9.equalsIgnoreCase("S")) {*/
					res = "Sucess";
				/*}*/
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res;
	}

	@Override
	public List<ParentsDataBean> getParentDetilsList(String personID, ParentsDataBean parentsDataBean) {
		logger.info("-----------Inside getParentDetilsList method()----------------");
		List<Parent> parentLists = null;
		List<ParentDetail> parentDetList = null;
		List<StudentParent> parStudentLists = null;
		List<ParentsDataBean> parStudentList = null;
		List<ImagePath> parImageList = null;
		List<String> studentClassList=null;
		List<Person> roleStatus = null;
		List<ParentsDataBean> parentFullDetails = null;
		int school_ID = 0;
		int parent_ID = 0;
		try {
			parentFullDetails = new ArrayList<ParentsDataBean>();
			parentLists = new ArrayList<Parent>();
			parentDetList = new ArrayList<ParentDetail>();
			parStudentList = new ArrayList<ParentsDataBean>();
			parImageList = new ArrayList<ImagePath>();
			roleStatus = new ArrayList<Person>();
			studentClassList=new ArrayList<String>();
			parStudentLists=new ArrayList<StudentParent>();
			if (personID != null && parentsDataBean.getParParentID() != null) {
				roleStatus = getRollType(personID);
				if (roleStatus.size() > 0) {
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						parentLists = getParentList(personID, school_ID, parentsDataBean);
						if (parentLists.size() > 0) {
							parent_ID = parentLists.get(0).getParent_ID();
							if (parent_ID > 0) {
								parentDetList = getParentDeatils(personID, school_ID, parent_ID);
								parStudentLists = getParentStudent(personID, school_ID, parent_ID);
								parStudentList = getParentStudents(personID, school_ID, parent_ID);
								parImageList = getParentImagePath(personID, school_ID, parent_ID);
								studentClassList=getstudentClass(personID,school_ID,parStudentLists);
								ParentsDataBean DataBean = new ParentsDataBean();
								DataBean.setParentDetailsList(parentDetList);
								DataBean.setParentList(parentLists);
								DataBean.setParentImageList(parImageList);
								DataBean.setStudentParentList(parStudentLists);
								DataBean.setParentdetails(parStudentList);
								DataBean.setStudentClass(studentClassList);
								parentFullDetails.add(DataBean);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return parentFullDetails;
	}

	private List<String> getstudentClass(String personID, int school_ID,List<StudentParent> parStudentList) {
		logger.info("-----------Inside getstudentClass method()----------------");
		List<String> classList=new ArrayList<String>();
		Query v=null;
		try{
			for (int i = 0; i < parStudentList.size(); i++) {
				v=entitymanager.createQuery("from StudentClass where student_ID=? and status='Active'");
				v.setParameter(1, parStudentList.get(i).getStudent().getStudent_ID());
				List<StudentClass> studentClass=(List<StudentClass>)v.getResultList();
				if(studentClass.size()>0){
					classList.add(studentClass.get(0).getClazz().getClassName()+"/"+
							studentClass.get(0).getClazz().getClassSection());
				}
			}			
		}
		catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		}
		return classList;
	}

	@SuppressWarnings("unchecked")
	private List<ImagePath> getParentImagePath(String personID, int school_ID, int parent_ID) {
		List<ImagePath> resultList = null;
		Query q = null;
		try {
			resultList = new ArrayList<ImagePath>();
			if (personID != null && school_ID > 0 && parent_ID > 0) {
				q = entitymanager.createQuery("from ImagePath where parent_ID=? and rollStatus=? and status='Active'");
				q.setParameter(1, parent_ID);
				q.setParameter(2, "Parent");
				List<ImagePath> result = (List<ImagePath>) q.getResultList();
				if (result.size() > 0) {
					resultList.addAll(result);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}

		return resultList;
	}

	@SuppressWarnings("unchecked")
	private List<StudentParent> getParentStudent(String personID, int school_ID, int parent_ID) {
		List<StudentParent> resultList = null;
		Query q = null;
		try {
			resultList = new ArrayList<StudentParent>();
			if (personID != null && school_ID > 0 && parent_ID > 0) {
				q = entitymanager.createQuery("from StudentParent where parent_ID=?  and status='Active'");
				q.setParameter(1, parent_ID);
				List<StudentParent> result = (List<StudentParent>) q.getResultList();
				logger.debug("size "+result.size());
				if (result.size() > 0) {
					resultList.addAll(result);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}

		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	private List<ParentsDataBean> getParentStudents(String personID, int school_ID, int parent_ID) {
		List<ParentsDataBean> resultList = null;int studentid=0;
		Query q = null;
		try {
			resultList = new ArrayList<ParentsDataBean>();
			if (personID != null && school_ID > 0 && parent_ID > 0) {
				q = entitymanager.createQuery("from StudentParent where parent_ID=?  and status='Active'");
				q.setParameter(1, parent_ID);
				List<StudentParent> result = (List<StudentParent>) q.getResultList();
				logger.debug("size "+result.size());
				if (result.size() > 0) {
					for (int i = 0; i < result.size(); i++) {
						studentid=result.get(i).getStudent().getStudent_ID();
						q=null;
						q=entitymanager.createQuery("from StudentDetail where student_ID=?");
						q.setParameter(1, studentid);
						List<StudentDetail> studentdetail=(List<StudentDetail>)q.getResultList();
						
						if(studentdetail.size()>0){
							ParentsDataBean obj=new ParentsDataBean();
							obj.setName(studentdetail.get(0).getFirstName()+studentdetail.get(0).getLastName()+"/"+result.get(i).getStudent().getRollNumber());
							resultList.add(obj);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	private List<ParentDetail> getParentDeatils(String personID, int school_ID, int parent_ID) {
		List<ParentDetail> resultList = null;
		Query q = null;
		try {
			resultList = new ArrayList<ParentDetail>();
			if (personID != null && school_ID > 0 && parent_ID > 0) {
				q = entitymanager.createQuery("from ParentDetail where parent_ID=?");
				q.setParameter(1, parent_ID);
				List<ParentDetail> result = (List<ParentDetail>) q.getResultList();
				if (result.size() > 0) {
					resultList.addAll(result);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}

		return resultList;
	}

	@SuppressWarnings("unchecked")
	private List<Parent> getParentLists(String personID, int school_ID, ParentsDataBean parentsDataBean) {
		List<Parent> resultList = null;
		Query q = null;
		try {
			resultList = new ArrayList<Parent>();
			if (personID != null && parentsDataBean.getParParentID() != null) {
				q = entitymanager.createQuery("from Parent where rollNumber=? and status='Active'");
				q.setParameter(1, parentsDataBean.getParParentID());
				List<Parent> result = (List<Parent>) q.getResultList();
				if (result.size() > 0) {
					resultList.addAll(result);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}

		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	private List<Parent> getParentList(String personID, int school_ID, ParentsDataBean parentsDataBean) {
		List<Parent> resultList = null;
		Query q = null;
		try {
			resultList = new ArrayList<Parent>();
			if (personID != null && parentsDataBean.getParParentID() != null) {
				q = entitymanager.createQuery("from Parent where rollNumber=? and school_ID=? and status='Active'");
				q.setParameter(1, parentsDataBean.getParParentID());
				q.setParameter(2, school_ID);
				List<Parent> result = (List<Parent>) q.getResultList();
				if (result.size() > 0) {
					resultList.addAll(result);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}

		return resultList;
	}
	
	public List<ParentsDataBean> addParent(List<Parent> parent,List<ParentsDataBean> parList){
		try 
		{
			
			for(int i=0;i<parent.size();i++){
				if(parent.size()>0){
				logger.info("getParentInfo() 16");
				ParentsDataBean Parents = new ParentsDataBean();
				Parents.setParParentID(parent.get(i).getRollNumber());
				Parents.setName(parent.get(i).getParentDetail().getFirstName() + " "
						+ parent.get(i).getParentDetail().getLastName());
				Parents.setParEmail(parent.get(i).getParentDetail().getEmaiId());
				Parents.setParPhoneNo(parent.get(i).getParentDetail().getPhoneNumber());
				if(!"".equalsIgnoreCase(parent.get(i).getParentDetail().getCountrycode()) || 
						parent.get(i).getParentDetail().getCountrycode()!=null){
					logger.info("getParentInfo() 17");
					Parents.setCountryCode(parent.get(i).getParentDetail().getCountrycode());
				}else{
					logger.info("getParentInfo() 18");
					Parents.setCountryCode("");
				}
				logger.info("getParentInfo() 19");
				parList.add(Parents);
				logger.info("getParentInfo() 20");
			}		
		
			}
			
			
		}catch(Exception e) {
			logger.error("Exception -->"+e.getMessage());
		//	e.printStackTrace();
		}
		
		return parList;
		
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ParentsDataBean> getParentInfo(String personID, ParentsDataBean parentsDataBean) {
		List<ParentsDataBean> parList = null;
		int student_ID = 0;
		int school_ID = 0;
		int parentID = 0;
		Query q = null;
		List<Person> roleStatus = null;
		List<String> parents=new ArrayList<String>();
		List<StudentClass> studentList = null;
		try {
			Map<Integer, String> studentParent = new HashMap<Integer, String>(); 

			studentList = new ArrayList<StudentClass>();
			roleStatus = new ArrayList<Person>();
			parList = new ArrayList<ParentsDataBean>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				if (roleStatus.size() > 0) {

					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						if (parentsDataBean.getParStuClass().equalsIgnoreCase("ALL")) {
							 
							logger.info("1");
							System.out.println("---- Inside Parent all Condition ----");						
							q = entitymanager.createQuery("from Parent where school_ID=? and status='Active' order by created_date desc");
							q.setParameter(1, school_ID);
							List<Parent> parent = (List<Parent>) q.getResultList();
							if(parent.size()>0){
								q=null;
								q= entitymanager.createQuery("from StudentParent where student_ID=? and status='Active'");
								q.setParameter(1, student_ID);
								List<StudentParent> result = (List<StudentParent>) q.getResultList();
								logger.info("-- result list ----->"+result.size());	
									for (int j = 0; j < result.size(); j++) {
										studentParent.put(student_ID, result.get(j).getParent().getRollNumber());
										parents.add(result.get(j).getParent().getRollNumber());
									}
									HashSet<String> names=new HashSet<String>(parents);
									parents.clear();parents.addAll(names);
									logger.info("parents "+parents);									
								System.out.println("Parent Size----"+parent.size()); 
								addParent(parent, parList);									
								logger.info("ParList Size -----"+parList.size()); 
							}	
							
							/* 
							logger.info("1");
							System.out.println("---- Inside Parent all Condition ----");
							q = entitymanager.createQuery("from Parent where school_ID=? and status='Active' order by created_date desc");
							q.setParameter(1, school_ID);
							List<Parent> parent = (List<Parent>) q.getResultList();
							if(parent.size()>0){
								System.out.println("Parent Size----"+parent.size()); 
								int i=0;
								ParentsDataBean Parents =null;
								for(Parent p : parent){
									
									System.out.println("parent ID"+p.getRollNumber()); 
									System.out.println("count"+i); 
									i++;
									Parents = new ParentsDataBean();
									Parents.setParParentID(parent.get(i).getRollNumber());
									Parents.setName(parent.get(i).getParentDetail().getFirstName());
									parList.add(Parents);
								}
								System.out.println("ParList Size -----"+parList.size()); 
							}
				
						*/}
						else{/*
						logger.info("----------" + parentsDataBean.getParStuClass());
						studentList = getStudentIDfromSection(personID, parentsDataBean.getParStuClass(), school_ID);
						logger.info("-- student list ----->"+studentList.size());
						if (studentList.size() > 0) {
							for (int i = 0; i < studentList.size(); i++) {
								student_ID = studentList.get(i).getStudent().getStudent_ID();
								logger.info("-- studentID ----->"+student_ID);
								q = entitymanager
										.createQuery("from StudentParent where student_ID=? and status='Active'");
								q.setParameter(1, student_ID);
								List<StudentParent> result = (List<StudentParent>) q.getResultList();
								logger.info("-- result list ----->"+result.size());
								if (result.size() > 0) {
									for (int j = 0; j < result.size(); j++) {										
										parents.add(result.get(j).getParent().getRollNumber());
									}
									HashSet<String> names=new HashSet<String>(parents);
									parents.clear();parents.addAll(names);
									logger.info("parents "+parents);									
								}
							}
							for (int v = 0; v < parents.size(); v++) {
								q = entitymanager
										.createQuery("from Parent where rollNumber=? and school_ID=? and status='Active'");
								q.setParameter(1, parents.get(v));
								q.setParameter(2, school_ID);
								List<Parent> parent = (List<Parent>) q.getResultList();
								logger.info("-- parent list ----->"+parent.size());
								if(parent.size()>0){
									ParentsDataBean Parents = new ParentsDataBean();
									Parents.setParParentID(parent.get(0).getRollNumber());
									Parents.setName(parent.get(0).getParentDetail().getFirstName() + " "
											+ parent.get(0).getParentDetail().getLastName());
									//Parents.setParStudID(result.get(j).getStudent().getRollNumber());
									Parents.setParEmail(parent.get(0).getParentDetail().getEmaiId());
									Parents.setParPhoneNo(
											parent.get(0).getParentDetail().getPhoneNumber());
									if(!"".equalsIgnoreCase(parent.get(0).getParentDetail().getCountrycode()) || 
											parent.get(0).getParentDetail().getCountrycode()!=null){
										
										Parents.setCountryCode(parent.get(0).getParentDetail().getCountrycode());
									}else{
										Parents.setCountryCode("");
									}
									parList.add(Parents);
								}else{
									logger.info("getParentInfo() | Student Size is Zero");	
								}										
							}
						}
					*/
					

							logger.info("----------" + parentsDataBean.getParStuClass());
							studentList = getStudentIDfromSection(personID, parentsDataBean.getParStuClass(), school_ID);
							logger.info("-- student list ----->"+studentList.size());
							if (studentList.size() > 0) {
								for (int i = 0; i < studentList.size(); i++) {
									student_ID = studentList.get(i).getStudent().getStudent_ID();
									logger.info("-- studentID ----->"+student_ID);
									q = entitymanager
											.createQuery("from StudentParent where student_ID=? and status='Active'");
									q.setParameter(1, student_ID);
									List<StudentParent> result = (List<StudentParent>) q.getResultList();
									logger.info("-- result list ----->"+result.size());								
									if (result.size() > 0) {
										for (int j = 0; j < result.size(); j++) {
											studentParent.put(student_ID, result.get(j).getParent().getRollNumber());
											parents.add(result.get(j).getParent().getRollNumber());
										}
										HashSet<String> names=new HashSet<String>(parents);
										parents.clear();parents.addAll(names);
										logger.info("parents "+parents);									
									}
								}
								//for (int v = 0; v < parents.size(); v++) {
								for (Map.Entry student : studentParent.entrySet()) {
									q = entitymanager
											.createQuery("from Parent where rollNumber=? and school_ID=? and status='Active'");
									//q.setParameter(1, parents.get(v));
									q.setParameter(1, student.getValue());
									q.setParameter(2, school_ID);
									List<Parent> parent = (List<Parent>) q.getResultList();
									logger.info("-- parent list ----->"+parent.size());
									if(parent.size()>0){
										ParentsDataBean Parents = new ParentsDataBean();
										Parents.setParParentID(parent.get(0).getRollNumber());
										Parents.setName(parent.get(0).getParentDetail().getFirstName() + " "
												+ parent.get(0).getParentDetail().getLastName());
										//Parents.setParStudID(result.get(j).getStudent().getRollNumber());
										Parents.setParEmail(parent.get(0).getParentDetail().getEmaiId());
										Parents.setParPhoneNo(
												parent.get(0).getParentDetail().getPhoneNumber());
										if(!"".equalsIgnoreCase(parent.get(0).getParentDetail().getCountrycode()) || 
												parent.get(0).getParentDetail().getCountrycode()!=null){
											
											Parents.setCountryCode(parent.get(0).getParentDetail().getCountrycode());
										}else{
											Parents.setCountryCode("");
										}
										String studentName =  getStudentIDAndNameforParentID(personID, student.getKey().toString());
										Parents.setParStudID(studentName);
										parList.add(Parents);
									}else{
										logger.info("getParentInfo() | Student Size is Zero");	
									}										
								}
							}
						
						}
				}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("Exception -->"+e.getMessage());
		}
		return parList;
	}	
/*
@Override
@SuppressWarnings("unchecked")
public List<ParentsDataBean> getParentInfo(String personID, ParentsDataBean parentsDataBean) {
	logger.info("getParentInfo() 1");
	List<ParentsDataBean> parList = null;
	int student_ID = 0;
	int school_ID = 0;
	int parentID = 0;
	Query q = null;
	List<Person> roleStatus = null;
	List<String> parents=new ArrayList<String>();
	List<StudentClass> studentList = null;
	logger.info("getParentInfo() 2");
	try {
		logger.info("getParentInfo() 3");
		studentList = new ArrayList<StudentClass>();
		roleStatus = new ArrayList<Person>();
		parList = new ArrayList<ParentsDataBean>();
		if (personID != null) {
			logger.info("getParentInfo() 4");
			roleStatus = getRollType(personID);
			if (roleStatus.size() > 0) {
			logger.info("getParentInfo() 5");
			//logger.info("getParentInfo() 2");
			school_ID = roleStatus.get(0).getSchool().getSchool_ID();
			if (school_ID > 0) {
				logger.info("getParentInfo() 6");
				logger.info("Class name -->" + parentsDataBean.getParStuClass());
				if(parentsDataBean.getParStuClass().equalsIgnoreCase("ALL")){
					logger.info("getParentInfo() 7");
					//studentList = getStudentIDFromAllClass(personID, school_ID);
					logger.info("getParentInfo() 14");
					q = entitymanager.createQuery("from Parent where school_ID=? and status='Active'");
					q.setParameter(1, school_ID);
					List<Parent> parent = (List<Parent>) q.getResultList();
					logger.info("getParentInfo() 15");
					addParent(parent, parList);				
				}
				else 
				{
				// This is for Class level choosen
				logger.info("Person ID -->"+personID);
				logger.info("UI Selected Class name -->"+parentsDataBean.getParStuClass());
				logger.info("School ID -->"+school_ID);
				logger.info("getParentInfo() 8");
				studentList = getStudentIDfromSection(personID, parentsDataBean.getParStuClass(), school_ID);
				logger.debug("Student List size-->"+studentList.size());
				if (studentList.size() > 0) {
					logger.info("getParentInfo() 9");
					for (int i = 0; i < studentList.size(); i++) {
						logger.info("getParentInfo() 10");
						student_ID = studentList.get(i).getStudent().getStudent_ID();
						logger.info("getParentInfo() Student ID-->"+student_ID);
						q = entitymanager.createQuery("from StudentParent where student_ID=? and status='Active'");
						q.setParameter(1, student_ID);
						List<StudentParent> result = (List<StudentParent>) q.getResultList();
						if (result.size() > 0) {
							logger.info("getParentInfo() 11");
							for (int j = 0; j < result.size(); j++) {										
								parents.add(result.get(j).getParent().getRollNumber());
							}
							logger.info("getParentInfo() 12");
							HashSet<String> names=new HashSet<String>(parents);
							parents.clear();
							parents.addAll(names);
							logger.info("getParentInfo() 13");
							logger.debug("parents size"+parents.size());									
						}
					}
								
	for (int v = 0; v < parents.size(); v++) {
	logger.info("Parent roll number -->"+parents.get(v));
	logger.info("getParentInfo() 14");
	q = entitymanager.createQuery("from Parent where rollNumber=? and school_ID=? and status='Active'");
	q.setParameter(1, parents.get(v));
	q.setParameter(2, school_ID);
	List<Parent> parent = (List<Parent>) q.getResultList();
	logger.info("getParentInfo() 15");
	if(parent.size()>0){
		logger.info("getParentInfo() 16");
		ParentsDataBean Parents = new ParentsDataBean();
		Parents.setParParentID(parent.get(v).getRollNumber());
		Parents.setName(parent.get(v).getParentDetail().getFirstName() + " "
				+ parent.get(v).getParentDetail().getLastName());
		//Parents.setParStudID(result.get(j).getStudent().getRollNumber());
		Parents.setParEmail(parent.get(v).getParentDetail().getEmaiId());
		Parents.setParPhoneNo(parent.get(v).getParentDetail().getPhoneNumber());
		if(!"".equalsIgnoreCase(parent.get(v).getParentDetail().getCountrycode()) || 
				parent.get(v).getParentDetail().getCountrycode()!=null){
			logger.info("getParentInfo() 17");
			Parents.setCountryCode(parent.get(v).getParentDetail().getCountrycode());
		}else{
			logger.info("getParentInfo() 18");
			Parents.setCountryCode("");
		}
		logger.info("getParentInfo() 19");
		parList.add(Parents);
		logger.info("getParentInfo() 20");
		}										
	}
}
									
								
				else{
					logger.info("getParentInfo() | Student Size is Zero");	
					}
	}
}
}
}
} 
catch (Exception e) {
			logger.info("getParentInfo() 13");
			logger.warn("Exception -->"+e.getMessage());
		}
	finally{
		
	}
		return parList;
		
}*/

	@SuppressWarnings("unchecked")
	private String  getStudentIDAndNameforParentID(String personID, String studentID){
		Query q = null;
		String studentName = "";
		try {
				q = entitymanager.createQuery("from StudentDetail where student_ID=?");
				q.setParameter(1, Integer.parseInt(studentID)); 
				List<StudentDetail> studentDetail=(ArrayList<StudentDetail>) q.getResultList();
				if(studentDetail.size()>0){
					studentName = studentDetail.get(0).getFirstName()+" "+ studentDetail.get(0).getLastName();
				}
		}catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return studentName;
	}
		
	@SuppressWarnings("unchecked")
	private List<StudentClass> getStudentIDfromSection(String personID, String parStuClass, int school_ID) {
		Query q = null;
		Query q1 = null;
		//int class_ID = 0;
		//int stuID = 0;
		List<StudentClass> studentList = null;
		
		logger.info("getStudentIDfromSection Class name -->" + parStuClass);
		try {
			if (personID != null && parStuClass != null && school_ID > 0) {
				StringTokenizer st = new StringTokenizer(parStuClass);
				String className = st.nextToken("/");
				String sectionName = parStuClass;
				String section = sectionName.substring(sectionName.lastIndexOf("/") + 1);
				logger.info("Class name -->"+className);
				logger.info("Section name -->"+section);
				logger.info("School ID -->"+school_ID);				
				q = entitymanager.createQuery("from Class where className=? and classSection=? and school_ID=? and status='Active'");
				q.setParameter(1, className);
				q.setParameter(2, section);
				q.setParameter(3, school_ID);
				List<Class> result = (List<Class>) q.getResultList();
				studentList = new ArrayList<StudentClass>();
				if(result.size()>0){
				for(Class l:result){
					q1 = entitymanager.createQuery("from StudentClass where class_ID=? and status='Active'");
					q1.setParameter(1, l.getClass_ID());
					//studentList = (List<StudentClass>) q1.getResultList();
					studentList.addAll((List<StudentClass>) q1.getResultList());
				}
				
				}else {
					logger.info("There is NO Student Class found");
				}
				
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		finally
		{
			
		}
		return studentList;
	}

	@Override
	@Transactional(value = "transactionManager")
	public String updateParent(String personID, ParentsDataBean parentsDataBean) {
		int student_ID = 0;
		int school_ID = 0;
		int parentID = 0;
		int hasParID = 0;
		int img_ID = 0;
		int studentID = 0;
		List<Person> roleStatus = null;
		int hasParStuID = 0;
		List<Parent> resultList = null;
		List<ParentDetail> paretnDetList = null;
		List<ImagePath> parentImageList = null;
		List<StudentParent> stuParList = null;
		String status = "Fail";
		try {
			roleStatus = new ArrayList<Person>();
			resultList = new ArrayList<Parent>();
			paretnDetList = new ArrayList<ParentDetail>();
			parentImageList = new ArrayList<ImagePath>();
			stuParList = new ArrayList<StudentParent>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				if (roleStatus.size() > 0) {
					parentsDataBean.setSchoolLogo(roleStatus.get(0).getSchool().getLogopath());
					parentsDataBean.setSchoolID(roleStatus.get(0).getSchool().getSchool_ID());
					parentsDataBean.setSchoolName(roleStatus.get(0).getSchool().getName());
						school_ID = roleStatus.get(0).getSchool().getSchool_ID();
						if (school_ID > 0) {
							resultList = getParentList(personID, school_ID, parentsDataBean);
							if (resultList.size() > 0) {
								parentID = resultList.get(0).getParent_ID();

								paretnDetList = getParentDeatils(personID, school_ID, parentID);
								if (paretnDetList.size() > 0) {
									hasParID = paretnDetList.get(0).getHas_parent_ID();
									ParentDetail parentDetail = entitymanager.find(ParentDetail.class, hasParID);
									parentDetail.setEmaiId(parentsDataBean.getParEmail());
									parentDetail.setFirstName(parentsDataBean.getParFirstName());
									parentDetail.setLastName(parentsDataBean.getParLastName());
									parentDetail.setRelation(parentsDataBean.getParentRelation());
									parentDetail.setPhoneNumber(parentsDataBean.getParPhoneNo());
									parentDetail.setCountrycode(parentsDataBean.getCountryCode().split("-")[0]);
									entitymanager.merge(parentDetail);

									if (parentsDataBean.getParFile() != null || parentsDataBean.getParfilePath() != null) {
										parentImageList = getParentImagePath(personID, school_ID, parentID);
										if (parentImageList.size() > 0) {
											StringTokenizer stoken = new StringTokenizer(
													parentsDataBean.getParfilePath());
											Date d = new Date();
											String tempPath = parentsDataBean.getParfilePath();
											String path = tempPath.substring(tempPath.lastIndexOf("/") + 1);
											img_ID = parentImageList.get(0).getImage_ID();
											// Insert ImagePath table
											ImagePath imagepath = entitymanager.find(ImagePath.class, img_ID);
											imagepath.setDate(d);
											imagepath.setPath(path);
											imagepath.setName(parentsDataBean.getParParentID());
											entitymanager.merge(imagepath);
										}
									} else {
										logger.debug("Old Imaage");
									}

									stuParList = getParentStudent(personID, school_ID, parentID);
									if (stuParList.size() > 0) {
										studentParent(personID, school_ID,parentID);
										for (int i = 0; i < parentsDataBean.getParentdetails().size(); i++) {
											String student=parentsDataBean.getParentdetails().get(i).getParStudID();
											String studentnid = student.substring(student.lastIndexOf("/") + 1);
											studentID = getStudentID(studentnid);
											if (studentID > 0) {
												StudentParent stuParent = new StudentParent();
												stuParent.setParent(entitymanager.find(Parent.class, parentID));
												stuParent.setStudent(entitymanager.find(Student.class, studentID));
												stuParent.setStatus("Active");
												entitymanager.persist(stuParent);
												entitymanager.flush();
												entitymanager.clear();
											}
										}
										status = "Success";
									}
								}

							}
						}
					}
				}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			//logger.error("Exception -->"+e.getMessage());
		}
		return status;
	}
	
	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	private String studentParent(String personID, int school_ID, int parentID) {
		Query v=null;
		try {
			if (personID != null && school_ID > 0 && parentID > 0) {				
				v = entitymanager.createQuery("from StudentParent where parent_ID=?  and status='Active'");
				v.setParameter(1, parentID);
				List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();
				if(studentParent.size()>0){
					for (int j = 0; j < studentParent.size(); j++) {
						StudentParent studentUpdate=entitymanager.find(StudentParent.class, studentParent.get(j).getHasstudent_parent_ID());
						studentUpdate.setStatus("De-Active");
						entitymanager.merge(studentUpdate);
						entitymanager.flush();
						entitymanager.clear();
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}

	@Override
	@Transactional(value = "transactionManager")
	public String deleteParent(String personID, ParentsDataBean parentsDataBean) {
		int school_ID = 0;
		int parentID = 0;
		int hasParId = 0;
		List<Person> roleStatus = null;
		List<Parent> resultList = null;
		List<StudentParent> resulting = null;
		String status = "Fail";
		try {
			roleStatus = new ArrayList<Person>();
			resultList = new ArrayList<Parent>();
			resulting = new ArrayList<StudentParent>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				if (roleStatus.size() > 0) {
					if (roleStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
						school_ID = roleStatus.get(0).getSchool().getSchool_ID();
						if (school_ID > 0) {
							resultList = getParentList(personID, school_ID, parentsDataBean);
							if (resultList.size() > 0) {
								parentID = resultList.get(0).getParent_ID();
								Parent parent = entitymanager.find(Parent.class, parentID);
								if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
									parent.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
									parent.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
								}else{
									parent.setCreatedDate(date);
									parent.setCreatedTime(timestamp);
								}	
								parent.setRollNumber(parentsDataBean.getParParentID());
								parent.setStatus("De-Active");
								entitymanager.merge(parent);
								status = "Success";
								resulting = getParentStudent(personID, school_ID, parentID);
								logger.debug("Size" + resulting.size());
								if (resulting.size() > 0) {
									for (int i = 0; i < resulting.size(); i++) {
										hasParId = resulting.get(i).getHasstudent_parent_ID();
										StudentParent studentParent = entitymanager.find(StudentParent.class, hasParId);
										studentParent.setStatus("De-Active");
										entitymanager.merge(studentParent);
										entitymanager.flush();
										entitymanager.clear();
									}									
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}

	/**
	 * @author Jeevini This method is used to get the book info for order
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<BookSaleDataBean> getBookInfo(String personID, BookSaleDataBean bookSaleDataBean) {
		logger.debug("----------inside getBookInfo---------");
		Query q = null;
		List<BookSaleDataBean> booklist1 = new ArrayList<BookSaleDataBean>();
		int clsid = 0;
		List<Person> roleStatus = null;
		String rollType = "NotValid";
		String no;
		try {
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					int schoolID=roleStatus.get(0).getSchool().getSchool_ID();
					if (rollType.equalsIgnoreCase("Parent")) {
						q = entitymanager.createQuery("from BookSalesRecord where school_ID=? and status='Active'");
						q.setParameter(1, schoolID);
						List<BookSalesRecord> booklist = (List<BookSalesRecord>) q.getResultList();
						logger.debug("--------list size-----" + booklist);
						if (booklist.size() > 0) {
							int sno = 1;
							for (int i = 0; i < booklist.size(); i++) {
								BookSaleDataBean obj = new BookSaleDataBean();
								obj.setSerial(String.valueOf(sno));
								obj.setBookName(booklist.get(i).getBookName());
								obj.setBookPrice(booklist.get(i).getPrice());
								obj.setBookQuantity(booklist.get(i).getQuantity());
								obj.setAmount(booklist.get(i).getNetAmount());
								obj.setFlag(false);
								booklist1.add(obj);
								bookSaleDataBean.setBooklist2(booklist1);
								sno++;

							}
						}
					}

				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}

		return booklist1;
	}

	/**
	 * @author Jeevini This method is used to order book using student id
	 */
	@Override
	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	public String addBookOrder(String personID, BookSaleDataBean bookSaleDataBean) {

		logger.debug("-----------inside addBookOrder----------");
		logger.debug("------studid inside------" + bookSaleDataBean.getStudID());
		List<Person> rolestatus = null;
		String roletype = "not valid";
		int schlid = 0;
		int studid = 0;
		BigDecimal amount = new BigDecimal(0);
		BigDecimal quant = new BigDecimal(0);
		BigDecimal amt = new BigDecimal(0);
		String status = "Fail";
		int salesid = 0;
		int salesid1 = 0;
		int c=0;
		Query q = null;
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		Calendar now= Calendar.getInstance();
		String month=new SimpleDateFormat("MMM").format(now.getTime());
		try {
			rolestatus = new ArrayList<Person>();
			if (personID != null) {
				rolestatus = getRollType(personID);
				if (rolestatus.size() > 0) {
					roletype = rolestatus.get(0).getPersonRole();
					schlid = rolestatus.get(0).getSchool().getSchool_ID();
					String student=bookSaleDataBean.getStudID();
					String studentid=student.substring(student.lastIndexOf("/")+1);
					q = entitymanager.createQuery("from Student where roll_number=? and status='Active'");
					q.setParameter(1, studentid);
					List<Student> studlist = (List<Student>) q.getResultList();
					if (studlist.size() > 0) {

						studid = studlist.get(0).getStudent_ID();
						if (roletype.equalsIgnoreCase("Parent")) {
							logger.debug("------------ inside first table-------");
							Query v=entitymanager.createQuery("from BookSale");
							List<BookSale> sales=(List<BookSale>)v.getResultList();
							String orderno="";
							if(sales.size()>0)
							{
								c=sales.size()+1;
								logger.debug("size -- >"+c);
								if(c<=9)
								{
									orderno="BSO000"+c+"/"+month+"/"+now.get(Calendar.YEAR);
									logger.debug("no 1- > "+orderno);
								}
								else if(c>9 && c<=99)
								{
									orderno="BSO00"+c+"/"+month+"/"+now.get(Calendar.YEAR);
									logger.debug("no 2- > "+orderno);
								}
								else if(c>=99)
								{
									orderno="BSO0"+c+"/"+month+"/"+now.get(Calendar.YEAR);
									logger.debug("no 3- > "+orderno);
								}
							}
							else
							{	
								c=1;
								orderno="BSO0001"+"/"+month+"/"+now.get(Calendar.YEAR);
								logger.debug("no - > "+orderno);
							}
							bookSaleDataBean.setOrderNumber(orderno);
							BookSale book = new BookSale();
							book.setSchool(entitymanager.find(School.class, schlid));
							book.setStudent(entitymanager.find(Student.class, studid));
							if(schlid==2 || schlid==3 || schlid==4 || schlid==5|| schlid==6){
								book.setOrderDate(GetTimeZone.getDate("Asia/Makassar"));
								book.setOrderTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
							}else if(schlid==35 || schlid==36 || schlid==37 || schlid==38|| schlid==39 || schlid==40 || schlid==41 || schlid==42 || schlid==43){
								book.setOrderDate(date);
								book.setOrderTime(timestamp);
							}
							else{
								book.setOrderDate(GetTimeZone.getDate("Asia/Makassar"));
								book.setOrderTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
							}	
							book.setStatus("Active");
							book.setApprovalstatus("Inserted");
							book.setTotalPrice(bookSaleDataBean.getTotal());
							book.setOrderNumber(orderno);
							entitymanager.persist(book);
							logger.debug("------------first table inserted---------");
							logger.debug("--------studid-------" + studid);
							q = null;
							q = entitymanager.createQuery("from BookSale where student_ID=? and orderNumber=? and status='Active'");
							q.setParameter(1, studid);
							q.setParameter(2, orderno);
							List<BookSale> booklist1 = (List<BookSale>) q.getResultList();
							logger.debug("------list size--------" + booklist1.size());
							if (booklist1.size() > 0) {
								salesid = booklist1.get(0).getSales_ID();
								logger.debug("-------sales id-----" + salesid);
								logger.debug("-----------inside second table--------");
								logger.debug("--------book list size----" + bookSaleDataBean.getBooklist2().size());
								for (int i = 0; i < bookSaleDataBean.getBooklist2().size(); i++) {
									if (bookSaleDataBean.getBooklist2().get(i).getNetAmount() != null) {
										logger.debug("---------inside if--------");
										BookOrderRecord book1 = new BookOrderRecord();
										book1.setBooksale(entitymanager.find(BookSale.class, salesid));
										book1.setBookName(bookSaleDataBean.getBooklist2().get(i).getBookName());
										book1.setPrice(bookSaleDataBean.getBooklist2().get(i).getBookPrice());
										book1.setQuantity(bookSaleDataBean.getBooklist2().get(i).getOrder());
										book1.setNetAmount(bookSaleDataBean.getBooklist2().get(i).getNetAmount());
										entitymanager.persist(book1);
										
										q = null;
										q = entitymanager.createQuery("from BookSalesRecord where book_name=?");
										q.setParameter(1, bookSaleDataBean.getBooklist2().get(i).getBookName());
										List<BookSalesRecord> rlist = (List<BookSalesRecord>) q.getResultList();
										if (rlist.size() > 0) {
											salesid1 = rlist.get(0).getSales_record_ID();
											quant = new BigDecimal(rlist.get(0).getQuantity()).subtract(
													new BigDecimal(bookSaleDataBean.getBooklist2().get(i).getOrder()));
											amt = quant.multiply(new BigDecimal(
													bookSaleDataBean.getBooklist2().get(i).getBookPrice()));
											BookSalesRecord obj = entitymanager.find(BookSalesRecord.class, salesid1);
											logger.debug("---------remaining amount-------" + quant.toString());
											obj.setQuantity(quant.toString());
											obj.setNetAmount(amt.toString());
											entitymanager.merge(obj);

										}
										entitymanager.flush();
										entitymanager.clear();
										logger.debug("---------second table inserted---------");
									}
								}
								status = "Success";
							}
						}

						// }
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}
	/* Prema OCT 25   getBookViewInfo*/
	@Override
	@SuppressWarnings("unchecked")
	public List<BookSaleDataBean> getBookViewInfo(String personID, BookSaleDataBean bookSaleDataBean) {
		logger.debug("----------inside getBookViewInfo---------");
		Query q = null;
		List<BookSaleDataBean> booklist1 = new ArrayList<BookSaleDataBean>();
		int clsid = 0;
		List<Person> roleStatus = null;
		String rollType = "NotValid";
		BigDecimal totalAmount=BigDecimal.valueOf(0);
		bookSaleDataBean.setTotalAmount("0");
		String no;
		int studid = 0;
		int salesid = 0;
		String total;
		try {
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					if (rollType.equalsIgnoreCase("Admin") || rollType.equalsIgnoreCase("BookShop")) {
						String student = bookSaleDataBean.getStudID();
						String studentid = student.substring(student.lastIndexOf("/") + 1);
						q = null;
						q = entitymanager.createQuery("from Student where roll_number=? and school_ID=? and status='Active'");
						q.setParameter(1, studentid);
						q.setParameter(2, roleStatus.get(0).getSchool().getSchool_ID());
						List<Student> studlist = (List<Student>) q.getResultList();
						if (studlist.size() > 0) {
							studid = studlist.get(0).getStudent_ID();
							logger.debug("-------studis------" + studid);
							q = null;
							q = entitymanager.createQuery("from BookSale where student_ID=? and status='Active'");
							q.setParameter(1, studid);
							List<BookSale> booklist = (List<BookSale>) q.getResultList();
							logger.debug("-------list size-----" + booklist);
							if (booklist.size() > 0)
							{
									int sno = 1;
									for (int i = 0; i < booklist.size(); i++) {
										BookSaleDataBean obj = new BookSaleDataBean();
										obj.setSerial(String.valueOf(sno));
										obj.setOrderNumber(booklist.get(i).getOrderNumber());
										obj.setTotalAmount(booklist.get(i).getTotalPrice());
										obj.setStatus(booklist.get(i).getApprovalstatus());
										booklist1.add(obj);
										bookSaleDataBean.setBookviewlist(booklist1);
										sno++;
									}

								}
							}
						}

					 else if (rollType.equalsIgnoreCase("Parent")) {
						logger.debug("---------inside parent-----");
						logger.debug("------student id-------" + bookSaleDataBean.getStudID());
						String student = bookSaleDataBean.getStudID();
						String studentid = student.substring(student.lastIndexOf("/") + 1);
						q = null;
						q = entitymanager.createQuery("from Student where roll_number=? and school_ID=? and status='Active'");
						q.setParameter(1, studentid);
						q.setParameter(2, roleStatus.get(0).getSchool().getSchool_ID());
						List<Student> studlist = (List<Student>) q.getResultList();
						if (studlist.size() > 0) {
							studid = studlist.get(0).getStudent_ID();
							logger.debug("-------studis------" + studid);
							q = null;
							q = entitymanager.createQuery("from BookSale where student_ID=? and status='Active'");
							q.setParameter(1, studid);
							List<BookSale> booklist = (List<BookSale>) q.getResultList();
							logger.debug("-------list size-----" + booklist);
							if (booklist.size() > 0) {
									int sno = 1;
									for (int i = 0; i < booklist.size(); i++) {
										BookSaleDataBean obj = new BookSaleDataBean();
										obj.setSerial(String.valueOf(sno));
										obj.setOrderNumber(booklist.get(i).getOrderNumber());
										obj.setTotalAmount(booklist.get(i).getTotalPrice());
										obj.setStatus(booklist.get(i).getApprovalstatus());
										if(obj.getStatus().equalsIgnoreCase("Inserted")){
											obj.setActionFlag(true);
										}else{
											obj.setActionFlag(false);
										}
										sno++;
										booklist1.add(obj);
										bookSaleDataBean.setBookviewlist(booklist1);
									}

								}
							}
						}

				 else if (rollType.equalsIgnoreCase("Student")) {
						logger.debug("---------inside parent-----");
						logger.debug("------student id-------" + bookSaleDataBean.getStudID());
						q = null;
						q = entitymanager.createQuery("from Student where roll_number=? and school_ID=? and status='Active'");
						q.setParameter(1, roleStatus.get(0).getPersonRoleNumber());
						q.setParameter(2, roleStatus.get(0).getSchool().getSchool_ID());
						List<Student> studlist = (List<Student>) q.getResultList();
						if (studlist.size() > 0) {
							studid = studlist.get(0).getStudent_ID();
							logger.debug("-------studis------" + studid);
							q = null;
							q = entitymanager.createQuery("from BookSale where student_ID=? and status='Active'");
							q.setParameter(1, studid);
							List<BookSale> booklist = (List<BookSale>) q.getResultList();
							logger.debug("-------list size-----" + booklist);
							if (booklist.size() > 0) {
									int sno = 1;
									for (int i = 0; i < booklist.size(); i++) {
										BookSaleDataBean obj = new BookSaleDataBean();
										obj.setSerial(String.valueOf(sno));
										obj.setOrderNumber(booklist.get(i).getOrderNumber());
										obj.setTotalAmount(booklist.get(i).getTotalPrice());
										obj.setStatus(booklist.get(i).getApprovalstatus());
										booklist1.add(obj);
										bookSaleDataBean.setBookviewlist(booklist1);
										sno++;
								}
							}
						}

					}

				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}

		return booklist1;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ExamTable> getTimeTableList(String personID) {
		List<ExamTable> timeList = null;
		List<ExamTable> timeList1 = null;
		Query q = null;
		List<Person> roleStatus = null;
		int school_ID = 0;
		String rollType = "Not Valid";
		int teacher_ID = 0;
		List<TeacherClass> teaClass = null;
		int studentID = 0;
		List<StudentClass> classList = null;
		List<Parent> parentList = null;
		int parentID = 0;
		List<StudentParent> parentStudent = null;
		List<StudentClass> studentClass = null;
		try {
			timeList = new ArrayList<ExamTable>();
			teaClass = new ArrayList<TeacherClass>();
			roleStatus = new ArrayList<Person>();
			classList = new ArrayList<StudentClass>();
			timeList1 = new ArrayList<ExamTable>();
			parentList = new ArrayList<Parent>();
			parentStudent = new ArrayList<StudentParent>();
			studentClass = new ArrayList<StudentClass>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				school_ID = roleStatus.get(0).getSchool().getSchool_ID();
				if (school_ID > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					if (rollType.equalsIgnoreCase("Admin")) {
						q = entitymanager.createQuery("from ExamTable where school_ID=? and status=?");
						q.setParameter(1, school_ID);
						q.setParameter(2, "Insert");
						List<ExamTable> res = (List<ExamTable>) q.getResultList();
						if (res.size() > 0) {
							timeList.addAll(res);
						}
					} else if (rollType.equalsIgnoreCase("Teacher")) {
						teacher_ID = getTeacherID(roleStatus.get(0).getPersonRoleNumber(),school_ID);
						teaClass = getTeaClass(teacher_ID, personID);
						if (teaClass.size() > 0) {
							for (int i = 0; i < teaClass.size(); i++) {
								logger.debug(teaClass.get(i).getClazz().getClassName());
								timeList1 = getExamList(school_ID, personID, teaClass.get(i).getClazz().getClassName()+"/"+teaClass.get(i).getClazz().getClassSection());
								if (timeList1.size() > 0) {
									timeList.addAll(timeList1);
								}
							}
							logger.debug("--------------" + timeList.size());
						}
					} else if (rollType.equalsIgnoreCase("Student")) {
						studentID = getStudentID1(roleStatus.get(0).getPersonRoleNumber());
						logger.debug("studentID------------" + studentID);
						classList = getStudentClassList(studentID, school_ID, personID);
						logger.debug("classList------------" + classList.size());

						if (classList.size() > 0) {
							String classname = classList.get(0).getClazz().getClassName()+"/"+classList.get(0).getClazz().getClassSection();
							logger.debug("--------" + classname);
							timeList = getExamList(school_ID, personID, classname);
						}

					} else if (rollType.equalsIgnoreCase("Parent")) {
						parentList = getParentList1(personID, roleStatus.get(0).getPersonRoleNumber());
						if (parentList.size() > 0) {
							parentID = parentList.get(0).getParent_ID();
							parentStudent = getParentStudent(personID, school_ID, parentID);
							if (parentStudent.size() > 0) {
								studentID = parentStudent.get(0).getStudent().getStudent_ID();
								studentClass = getStudentClassList(studentID, school_ID, personID);
								if (studentClass.size() > 0) {
									String classname = studentClass.get(0).getClazz().getClassName()+"/"+studentClass.get(0).getClazz().getClassSection();
									timeList = getExamList(school_ID, personID, classname);
								}
								/*
								 * logger.debug(parentStudent.get(0).getStudent(
								 * ).getStudentClasses().get(0).getClazz().
								 * getClassName());
								 */ }
						}
					} else
						logger.debug("Not valid");

				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return timeList;
	}

	@SuppressWarnings("unchecked")
	private List<ExamTable> getExamList(int school_ID, String personID, String classname) {
		List<ExamTable> timeList = null;
		Query q = null;
		try {
			timeList = new ArrayList<ExamTable>();
			if (personID != null && school_ID > 0 && classname != null) {
				q = entitymanager.createQuery("from ExamTable where class_name=? and school_ID=? and status=?");
				q.setParameter(1, classname);
				q.setParameter(2, school_ID);
				q.setParameter(3, "Insert");
				List<ExamTable> res = (List<ExamTable>) q.getResultList();
				if (res.size() > 0) {
					timeList.addAll(res);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return timeList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TimeTable> getExamTableList(String personID, int examID) {
		Query q = null;
		List<TimeTable> result = null;
		try {
			result = new ArrayList<TimeTable>();
			q = entitymanager.createQuery("from TimeTable where exam_table_ID=?");
			q.setParameter(1, examID);
			List<TimeTable> res = (List<TimeTable>) q.getResultList();
			if (res.size() > 0) {
				result.addAll(res);
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private List<StudentClass> getStudentClassList(int studentID, int school_ID, String personID) {
		List<StudentClass> classList = null;
		Query q = null;
		try {
			classList = new ArrayList<StudentClass>();
			if (personID != null && school_ID > 0 && studentID > 0) {
				q = entitymanager.createQuery("from StudentClass where student_ID=? and status='Active'");
				q.setParameter(1, studentID);
				List<StudentClass> res = (List<StudentClass>) q.getResultList();
				if (res.size() > 0) {
					classList.addAll(res);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return classList;
	}

	@SuppressWarnings("unchecked")
	private int getStudentID1(String stuRollNo) {
		Query q = null;
		int res1 = 0;
		try {
			q = entitymanager.createQuery("from Student where roll_number=? and status='Active'");
			q.setParameter(1, stuRollNo);
			List<Student> result = (List<Student>) q.getResultList();
			if (result.size() > 0) {
				res1 = result.get(0).getStudent_ID();

			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res1;
	}

	@SuppressWarnings("unchecked")
	private List<Parent> getParentList1(String personID, String personRoleNumber) {
		List<Parent> res = null;
		Query q = null;
		try {
			res = new ArrayList<Parent>();
			if (personID != null) {
				q = entitymanager.createQuery("from Parent where rollNumber=? and status='Active'");
				q.setParameter(1, personRoleNumber);
				res = (List<Parent>) q.getResultList();
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<School> getAdminDetails(String personID, LoginAccess loginAccess) {
		logger.debug("----------inside getAdminDetails------");
		Query q = null;
		List<Person> roll = null;
		String rollType = "Not Valid";
		List<School> schoollist1 = null;
		int schlid = 0;
		try {
			roll = new ArrayList<Person>();
			roll = getRollType(personID);
			if (personID != null) {
				rollType = roll.get(0).getPersonRole();
				if (rollType.equalsIgnoreCase("Admin")) {
					schlid = roll.get(0).getSchool().getSchool_ID();
					q = entitymanager.createQuery("from School where school_ID=? and status='Active'");
					q.setParameter(1, schlid);
					schoollist1 = (List<School>) q.getResultList();
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.debug("-----------list size-------" + schoollist1.size());
		return schoollist1;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getStudClsSection(String stuRollNo) {
		logger.debug("--------inside getclasssection---------");
		logger.debug("------roll number dao----" + stuRollNo);
		Query q = null;
		String result = null;
		int studid = 0;
		//int classid = 0;
		try {
			q = entitymanager.createQuery("from Student where roll_number=?");
			q.setParameter(1, stuRollNo);
			List<Student> studlist = (List<Student>) q.getResultList();
			if (studlist.size() > 0) {
				studid = studlist.get(0).getStudent_ID();
			}
			q = null;
			q = entitymanager.createQuery("from StudentClass where student_ID=?");
			q.setParameter(1, studid);
			List<StudentClass> studlist1 = (List<StudentClass>) q.getResultList();
			if (studlist1.size() > 0) {
				result = studlist1.get(0).getClazz().getClassName() + "/"
						+ studlist1.get(0).getClazz().getClassSection();

			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getTeaClass(String rollNumber, StudentDataBean studentDataBean) {
		logger.debug("------------inside getTeaClass------");
		logger.debug("---------roll number-------" + rollNumber);
		Query q = null;
		int teaid = 0;
		//int clsid = 0;
		List<Person> roll=null;
		List<String> classes = new ArrayList<String>();
		String classname = "";
		try {
			roll = new ArrayList<Person>();
			roll=getRollType(rollNumber);
			if (roll.size() > 0) {
				int schoolid=roll.get(0).getSchool().getSchool_ID();
				if (roll.get(0).getPersonRole().equals("Admin")) {
				q = entitymanager.createQuery("from Class where status='Active' and school_ID=?");
				q.setParameter(1, schoolid);
				List<Class> classs = (List<Class>) q.getResultList();
				if (classs.size() > 0) {
					for (int i = 0; i < classs.size(); i++) {
						classname = classs.get(i).getClassName() + "/" + classs.get(i).getClassSection();
						classes.add(classname);
					}
				}
			} else if (roll.get(0).getPersonRole().equals("Teacher")) {
				q = entitymanager.createQuery("from Teacher where roll_number=? and status='Active' and school_ID=?");
				q.setParameter(1, roll.get(0).getPersonRoleNumber());
				q.setParameter(2, schoolid);
				List<Teacher> tealist = (List<Teacher>) q.getResultList();
				ArrayList<StudentDataBean> teaclass1 = new ArrayList<StudentDataBean>();
				if (tealist.size() > 0) {
					teaid = tealist.get(0).getTeacher_ID();
					q = null;
					q = entitymanager.createQuery("from TeacherClass where teacher_ID=? and status='Active'");
					q.setParameter(1, teaid);
					List<TeacherClass> teaclslist = (List<TeacherClass>) q.getResultList();
					if (teaclslist.size() > 0) {
						for (int i = 0; i < teaclslist.size(); i++) {
							classname = teaclslist.get(i).getClazz().getClassName() + "/"
									+ teaclslist.get(i).getClazz().getClassSection();
							StudentDataBean obj = new StudentDataBean();
							obj.setTeaclssection(classname);
							teaclass1.add(obj);
							classes.add(classname);
						
						}
					}
					studentDataBean.setTeaClassList(teaclass1);
					logger.debug("---------test2--------" + studentDataBean.getTeaClassList().size());
				}
			}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return classes;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ParentsDataBean> getImagePath2(String personID, String parParentID) {
		logger.debug("-------inside getImagePath2------");
		Query q = null;
		int parent_ID = 0;
		int perid = 0;
		int studid = 0;
		List<ParentsDataBean> finalList1 = new ArrayList<ParentsDataBean>();
		try {
			if ((personID != null) && (!parParentID.equalsIgnoreCase(""))) {
				logger.debug("--------id------" + parParentID);
				q = entitymanager.createQuery("from Person where person_role_number=? and status='Active'");
				q.setParameter(1, parParentID);
				List<Person> perlist = (List<Person>) q.getResultList();
				logger.debug("perlist size-------" + perlist.size());
				if (perlist.size() > 0) {
					perid = perlist.get(0).getPerson_ID();
					q = null;
					q = entitymanager.createQuery("from Parent where person_ID=? and status='Active'");
					q.setParameter(1, perid);
					List<Parent> parlist = (List<Parent>) q.getResultList();
					logger.debug("---------parent list size------" + parlist.size());
					if (parlist.size() > 0) {
						parent_ID = parlist.get(0).getParent_ID();
						q = null;
						q = entitymanager.createQuery("from StudentParent where parent_ID=? and status='Active'");
						q.setParameter(1, parent_ID);
						List<StudentParent> studparlist = (List<StudentParent>) q.getResultList();
						logger.debug("-------student parent size------" + studparlist.size());
						if (studparlist.size() > 0) {
							for (int i = 0; i < studparlist.size() ; i++) {
								studid = studparlist.get(i).getStudent().getStudent_ID();
								q = null;
								q = entitymanager.createQuery("from StudentClass where student_ID=? and status='Active'");
								q.setParameter(1, studid);
								List<StudentClass> studclslist = (List<StudentClass>) q.getResultList();
								logger.debug("---------student class-------" + studclslist.size());
								q = null;
								q = entitymanager.createQuery("from ImagePath where parent_ID=? and roll_status='Parent'");
								q.setParameter(1, parent_ID);
								List<ImagePath> result = (List<ImagePath>) q.getResultList();
								logger.debug("----------imagepath size------" + result.size());
								
									// finalList1=new ArrayList<ParentsDataBean>();
									parent_ID = parlist.get(0).getParent_ID();
									q = null;
									q = entitymanager.createQuery("from ParentDetail where parent_ID=?");
									q.setParameter(1, parent_ID);
									List<ParentDetail> detaillist = (List<ParentDetail>) q.getResultList();
									if (detaillist.size() > 0) {
										ParentsDataBean parentsDataBean = new ParentsDataBean();
										parentsDataBean.setParFirstName(detaillist.get(0).getFirstName());
										parentsDataBean.setParLastName(detaillist.get(0).getLastName());
										parentsDataBean.setParEmail(detaillist.get(0).getEmaiId());
										parentsDataBean.setParentRelation(detaillist.get(0).getRelation());
										parentsDataBean.setParParentID(parParentID);
										parentsDataBean.setParPhoneNo(detaillist.get(0).getPhoneNumber());
										parentsDataBean.setParStuClass(studclslist.get(0).getClazz().getClassName() + "/"
												+ studclslist.get(0).getClazz().getClassSection());
										if (result.size() > 0) {
											parentsDataBean.setParfilePath(result.get(0).getPath());
											parentsDataBean.setParPathDate(result.get(0).getDate());
										}else{
											parentsDataBean.setParfilePath("");
											parentsDataBean.setParPathDate(null);
										}
										parentsDataBean.setParStudID(studclslist.get(0).getStudent().getRollNumber());
										finalList1.add(parentsDataBean);
										logger.debug("list size----------" + finalList1.size());
									}
								
							}
						}							
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}

		logger.debug("--------outside-----");
		return finalList1;

	}

	@Override
	public List<NoticeBoardDataBean> getAllUserList(String personID, NoticeBoardDataBean noticeBoardDataBean) {
		logger.debug("inside getalluserlist");
		int school_ID = 0;
		List<TeacherDetail> teacher_emailList = null;
		List<ParentDetail> parent_emailList = null;
		List<StudentDetail> student_emailList = null;
		List<Staff> staff_emailList = null;
		List<Person> roleStatus = null;
		StringBuilder rollType = new StringBuilder("NotValid");
		List<NoticeBoardDataBean> emailList = null;
		Query q=null;
		try {
			//NoticeBoardDataBean notice = new NoticeBoardDataBean();
			roleStatus = new ArrayList<Person>();
			emailList = new ArrayList<NoticeBoardDataBean>();
			parent_emailList = new ArrayList<ParentDetail>();
			teacher_emailList = new ArrayList<TeacherDetail>();
			student_emailList = new ArrayList<StudentDetail>();
			staff_emailList=new ArrayList<Staff>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = new StringBuilder(roleStatus.get(0).getPersonRole());
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						if (noticeBoardDataBean.getNoticeFollower() != null || !noticeBoardDataBean.getNoticeFollower().equalsIgnoreCase("") && 
								noticeBoardDataBean.getNoticeClass() != null || !noticeBoardDataBean.getNoticeClass().equalsIgnoreCase("")) {
							if (noticeBoardDataBean.getNoticeFollower().equalsIgnoreCase("All") && 
									noticeBoardDataBean.getNoticeClass().equalsIgnoreCase("All") || 
									noticeBoardDataBean.getNoticeFollower().equalsIgnoreCase("External") && 
									noticeBoardDataBean.getNoticeClass().equalsIgnoreCase("All") ) {
								logger.debug("inside 1");
								if(school_ID==40){
									if(noticeBoardDataBean.getStudentnameList().size()>0){
										noticeBoardDataBean.setPhonenos(new ArrayList<String>());
										for (int i = 0; i < noticeBoardDataBean.getStudentnameList().size(); i++) {
											q=entitymanager.createQuery("select student_ID from Student where school_ID=? and rollNumber=? and status=?");
											q.setParameter(1, school_ID);
											q.setParameter(2, noticeBoardDataBean.getStudentnameList().get(i).split("/")[1]);
											q.setParameter(3, "Active");
											List<Integer> studentList=(ArrayList<Integer>)q.getResultList();
											if(studentList.size()>0){
												int studentID=studentList.get(0);
												q=entitymanager.createQuery("from StudentDetail where student_ID=?");
												q.setParameter(1, studentID);
												List<StudentDetail> studentDetail=(ArrayList<StudentDetail>)q.getResultList();
												if(studentDetail.size()>0){
													noticeBoardDataBean.getPhonenos().add(studentDetail.get(0).getPhoneNumber());
												}
											}
										}
									}
								}
								emailList=getExternalFollowerAllClassDetail(school_ID);
								logger.debug("email list size--"+emailList.size());
							}else if(noticeBoardDataBean.getNoticeFollower().equalsIgnoreCase("All") && 
									!noticeBoardDataBean.getNoticeClass().equalsIgnoreCase("All") || 
									 noticeBoardDataBean.getNoticeFollower().equalsIgnoreCase("External") && 
									!noticeBoardDataBean.getNoticeClass().equalsIgnoreCase("All")){
								logger.debug("class---ssss"+noticeBoardDataBean.getNoticeClass());
								logger.debug("inside 2");
								if(school_ID==40){
									if(noticeBoardDataBean.getStudentnameList().size()>0){
										noticeBoardDataBean.setPhonenos(new ArrayList<String>());
										for (int i = 0; i < noticeBoardDataBean.getStudentnameList().size(); i++) {
											q=entitymanager.createQuery("select student_ID from Student where school_ID=? and rollNumber=? and status=?");
											q.setParameter(1, school_ID);
											q.setParameter(2, noticeBoardDataBean.getStudentnameList().get(i).split("/")[1]);
											q.setParameter(3, "Active");
											List<Integer> studentList=(ArrayList<Integer>)q.getResultList();
											if(studentList.size()>0){
												int studentID=studentList.get(0);
												q=entitymanager.createQuery("from StudentDetail where student_ID=?");
												q.setParameter(1, studentID);
												List<StudentDetail> studentDetail=(ArrayList<StudentDetail>)q.getResultList();
												if(studentDetail.size()>0){
													noticeBoardDataBean.getPhonenos().add(studentDetail.get(0).getPhoneNumber());
												}
											}
										}
									}
								}
								emailList=getExternalFollowerClassDetail(school_ID,noticeBoardDataBean);
							}else if(noticeBoardDataBean.getNoticeFollower().equalsIgnoreCase("Internal") && 
									noticeBoardDataBean.getNoticeClass().equalsIgnoreCase("All")){
								logger.debug("inside 3");
								emailList=getInternalFollowerAllClassDetail(school_ID);
							}else if(noticeBoardDataBean.getNoticeFollower().equalsIgnoreCase("Internal") && 
									!noticeBoardDataBean.getNoticeClass().equalsIgnoreCase("All")){
								logger.debug("inside 4");
								emailList=getInternalFollowerClassDetail(school_ID,noticeBoardDataBean);
							}
							if(school_ID==40){
								if(noticeBoardDataBean.getStudentnameList().size()>0){
									noticeBoardDataBean.setPhonenos(new ArrayList<String>());
									for (int i = 0; i < noticeBoardDataBean.getStudentnameList().size(); i++) {
										q=entitymanager.createQuery("select student_ID from Student where school_ID=? and rollNumber=? and status=?");
										q.setParameter(1, school_ID);
										q.setParameter(2, noticeBoardDataBean.getStudentnameList().get(i).split("/")[1]);
										q.setParameter(3, "Active");
										List<Integer> studentList=(ArrayList<Integer>)q.getResultList();
										if(studentList.size()>0){
											int studentID=studentList.get(0);
											q=entitymanager.createQuery("from StudentDetail where student_ID=?");
											q.setParameter(1, studentID);
											List<StudentDetail> studentDetail=(ArrayList<StudentDetail>)q.getResultList();
											if(studentDetail.size()>0){
												noticeBoardDataBean.getPhonenos().add(studentDetail.get(0).getPhoneNumber());
											}
										}
									}
								}
							}
						}
					}
				}
			}
			logger.debug("Size--" + emailList.size());
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return emailList;
	}
	private List<NoticeBoardDataBean> getExternalFollowerAllClassDetail(int school_ID){
		List<TeacherDetail> teacher_emailList = null;
		List<ParentDetail> parent_emailList = null;
		List<StudentDetail> student_emailList = null;
		List<Staff> staff_emailList = null;
		List<NoticeBoardDataBean> emailList = null;
		try{
			NoticeBoardDataBean notice = new NoticeBoardDataBean();
			emailList = new ArrayList<NoticeBoardDataBean>();
			parent_emailList = new ArrayList<ParentDetail>();
			teacher_emailList = new ArrayList<TeacherDetail>();
			student_emailList = new ArrayList<StudentDetail>();
			staff_emailList=new ArrayList<Staff>();
				if (school_ID > 0) {
					if(school_ID  == 1 || school_ID == 2 || school_ID == 3 || school_ID == 4 || school_ID == 5 || school_ID == 6){
					}else{
						student_emailList = getStudentEmailBySchool(school_ID);
						if (student_emailList.size() > 0) {
							notice.setStudentList(student_emailList);
							emailList.add(notice);
						}
					}
					teacher_emailList = getTeacherEmailBySchool(school_ID);
					parent_emailList = getParentEmailBySchool(school_ID);
					staff_emailList = getStaffEmailBySchool(school_ID);
					if (teacher_emailList.size() > 0) {
						notice.setTeacherList(teacher_emailList);
						emailList.add(notice);
					}
					if (parent_emailList.size() > 0) {
						notice.setParentList(parent_emailList);
						emailList.add(notice);
					}
					if(staff_emailList.size() > 0){
						notice.setStaffList(staff_emailList);
						emailList.add(notice);
					logger.debug("email list szie4"+emailList.size());
				}	
			}
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}
		return emailList;
	}
	private List<NoticeBoardDataBean> getInternalFollowerAllClassDetail(int school_ID){
		List<TeacherDetail> teacher_emailList = null;
		List<Staff> staff_emailList = null;
		List<NoticeBoardDataBean> emailList = null;
		try{
			NoticeBoardDataBean notice = new NoticeBoardDataBean();
			emailList = new ArrayList<NoticeBoardDataBean>();
			teacher_emailList = new ArrayList<TeacherDetail>();
			staff_emailList=new ArrayList<Staff>();
			if (school_ID > 0) {
				teacher_emailList = getTeacherEmailBySchool(school_ID);
				staff_emailList = getStaffEmailBySchool(school_ID);
				if (teacher_emailList.size() > 0) {
					notice.setTeacherList(teacher_emailList);
					emailList.add(notice);
				}
				if(staff_emailList.size() > 0){
					notice.setStaffList(staff_emailList);
					emailList.add(notice);
				}
			}	
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}
		return emailList;
	}
	private List<NoticeBoardDataBean> getExternalFollowerClassDetail(int school_ID, NoticeBoardDataBean notice){
		logger.debug("inside getexternalfollowerclassdetail");
		List<TeacherDetail> teacher_emailList = null;
		List<ParentDetail> parent_emailList = null;
		List<StudentDetail> student_emailList = null;
		List<Staff> staff_emailList = null;
		List<NoticeBoardDataBean> emailList = null;int classid=0;
		try{
			emailList = new ArrayList<NoticeBoardDataBean>();
			parent_emailList = new ArrayList<ParentDetail>();
			teacher_emailList = new ArrayList<TeacherDetail>();
			student_emailList = new ArrayList<StudentDetail>();
			staff_emailList=new ArrayList<Staff>();
			if (school_ID > 0) {
				classid=getClassID(school_ID, notice);
				logger.debug("classid"+classid);
				if(school_ID == 1 || school_ID == 2 || school_ID == 3 || school_ID == 4 || school_ID == 5 || school_ID == 6){
				}else{
					student_emailList = getStudentEmailBySchools(classid);
					if (student_emailList.size() > 0) {
						notice.setStudentList(student_emailList);
						emailList.add(notice);
					}
				}
				teacher_emailList = getTeacherEmailBySchools(classid);
				parent_emailList = getParentEmailBySchools(classid);
				staff_emailList = getStaffEmailBySchool(school_ID);
				if (teacher_emailList.size() > 0) {
					notice.setTeacherList(teacher_emailList);
					emailList.add(notice);
				}
				if (parent_emailList.size() > 0) {
					notice.setParentList(parent_emailList);
					emailList.add(notice);
				}
				if(staff_emailList.size() > 0){
					notice.setStaffList(staff_emailList);
					emailList.add(notice);
				}
			}	
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}
		return emailList;
	}
	private List<NoticeBoardDataBean> getInternalFollowerClassDetail(int school_ID, NoticeBoardDataBean notice){
		List<TeacherDetail> teacher_emailList = null;
		List<Staff> staff_emailList = null;int classid=0;
		List<NoticeBoardDataBean> emailList = null;
		try{
			emailList = new ArrayList<NoticeBoardDataBean>();
			teacher_emailList = new ArrayList<TeacherDetail>();
			staff_emailList=new ArrayList<Staff>();
			if (school_ID > 0) {
				classid=getClassID(school_ID, notice);
				teacher_emailList = getTeacherEmailBySchools(classid);
				staff_emailList = getStaffEmailBySchool(school_ID);
				if (teacher_emailList.size() > 0) {
					notice.setTeacherList(teacher_emailList);
					emailList.add(notice);
				}
				if(staff_emailList.size() > 0){
					notice.setStaffList(staff_emailList);
					emailList.add(notice);
				}
			}	
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}
		return emailList;
	}
	@SuppressWarnings("unchecked")
	private List<Staff> getStaffEmailBySchool(int school_ID) {
		List<Staff> staffList=null;
		Query q=null;
		try{
			staffList=new ArrayList<Staff>();
			if(school_ID > 0)
			{
			q=entitymanager.createQuery("from Staff where school_ID=? and status=?");
			q.setParameter(1, school_ID);
			q.setParameter(2, "Active");
			List<Staff> result=(List<Staff>)q.getResultList();
			if(result.size() > 0){
				staffList.addAll(result);
			}
			}
		}catch(Exception e){
			logger.warn("Inside Exception",e);
		}
		return staffList;
	}
	@SuppressWarnings("unchecked")
	private int getClassID(int school_ID,NoticeBoardDataBean noticeBoardDataBean) {
	logger.debug("inside classid");
	Query q = null;int classid=0;
	try {
		logger.debug("classs"+noticeBoardDataBean.getNoticeClass());
			if(school_ID > 0){
				StringTokenizer st=new StringTokenizer(noticeBoardDataBean.getNoticeClass());
				String classname=st.nextToken("/");
				String sections=noticeBoardDataBean.getNoticeClass();
				String section=sections.substring(sections.lastIndexOf("/")+1);
				q = entitymanager.createQuery("from Class where school_ID=? and className=? and classSection=? and status='Active'");
				q.setParameter(1, school_ID);
				q.setParameter(2, classname);
				q.setParameter(3, section);
				List<Class> classList  = (List<Class>) q.getResultList();
				if(classList.size()>0){
					classid=classList.get(0).getClass_ID();
					logger.debug("classid"+classid);
				}
			}

		} catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return classid;
	}
	@SuppressWarnings("unchecked")
	private List<TeacherDetail> getTeacherEmailBySchool(int school_ID) {
	List<TeacherDetail> result = null;Query q = null;
	try {
		result = new ArrayList<TeacherDetail>();
		q = entitymanager.createQuery("from Teacher where status='Active' and school_ID=?");
		q.setParameter(1, school_ID);
		List<Teacher> teacherresult = (List<Teacher>) q.getResultList();
		for (int i = 0; i < teacherresult.size(); i++) {
			q=null;
			q = entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
			q.setParameter(1, teacherresult.get(i).getTeacher_ID());
			List<TeacherDetail> res = (List<TeacherDetail>) q.getResultList();
			if (res.size() > 0) {
				result.addAll(res);
			}
		}
	} catch (Exception e) {
		logger.error("Exception -->"+e.getMessage());
	}
	return result;
}
	@SuppressWarnings("unchecked")
	private List<TeacherDetail> getTeacherEmailBySchools(int classid) {
	List<TeacherDetail> result = null;
	Query q = null;
	try {
		result = new ArrayList<TeacherDetail>();
		q = entitymanager.createQuery("from TeacherClass where class_ID=? and status='Active'");
		q.setParameter(1, classid);
		List<TeacherClass> teacherclasslist =(List<TeacherClass>)q.getResultList();
		if(teacherclasslist.size()>0){
			for (int i = 0; i < teacherclasslist.size(); i++) {
				q=null;
				q=entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
				q.setParameter(1, teacherclasslist.get(i).getTeacher().getTeacher_ID());
				List<TeacherDetail> teacherdetaillist=(List<TeacherDetail>)q.getResultList();
				if(teacherdetaillist.size()>0){
					result.addAll(teacherdetaillist);
				}
			}
		}
	} catch (Exception e) {
		logger.error("Exception -->"+e.getMessage());
	}
	return result;
}
	@SuppressWarnings("unchecked")
	private List<ParentDetail> getParentEmailBySchool(int school_ID) {
	List<ParentDetail> result = null;Query q = null;
	try {
		result = new ArrayList<ParentDetail>();
		q = entitymanager.createQuery("from Parent where status='Active' and school_ID=?");
		q.setParameter(1, school_ID);
		List<Parent> parentresult = (List<Parent>) q.getResultList();
		for (int i = 0; i < parentresult.size(); i++) {
			q=null;
			q = entitymanager.createQuery("from ParentDetail where parent_ID=?");
			q.setParameter(1, parentresult.get(i).getParent_ID());
			List<ParentDetail> res = (List<ParentDetail>) q.getResultList();
			if (res.size() > 0) {
				result.addAll(res);
			}
		}
	} catch (Exception e) {
		logger.error("Exception -->"+e.getMessage());
	}
	return result;
}
	@SuppressWarnings("unchecked")
	private List<ParentDetail> getParentEmailBySchools(int classid) {
	List<ParentDetail> result = null;Query q = null;
	try {
		result = new ArrayList<ParentDetail>();
		q = entitymanager.createQuery("from StudentClass where class_ID=? and status='Active'");
		q.setParameter(1, classid);
		List<StudentClass> studentclasslist=(List<StudentClass>)q.getResultList();
		if(studentclasslist.size()>0){
			for (int i = 0; i < studentclasslist.size(); i++) {
				q =null;
				q=entitymanager.createQuery("from StudentParent where student_ID=? and status='Active'");
				q.setParameter(1, studentclasslist.get(i).getStudent().getStudent_ID());
				List<StudentParent> studentparentlist=(List<StudentParent>)q.getResultList();
				if(studentparentlist.size()>0){
					q=null;
					q=entitymanager.createQuery("from ParentDetail where parent_ID=?");
					q.setParameter(1, studentparentlist.get(0).getParent().getParent_ID());
					List<ParentDetail> parentdetaillist=(List<ParentDetail>)q.getResultList();
					if(parentdetaillist.size()>0){
						result.addAll(parentdetaillist);
					}
				}
			}
		}
	} catch (Exception e) {
		logger.error("Exception -->"+e.getMessage());
	}
	return result;
}
	@SuppressWarnings("unchecked")
	private List<StudentDetail> getStudentEmailBySchool(int school_ID) {
	List<StudentDetail> result = null;Query q = null;
	try {
		result = new ArrayList<StudentDetail>();
		q = entitymanager.createQuery("from Student where status='Active' and school_ID=?");
		q.setParameter(1, school_ID);
		List<Student> studentresult = (List<Student>) q.getResultList();
		for (int i = 0; i < studentresult.size(); i++) {
			q=null;
			q = entitymanager.createQuery("from StudentDetail where student_ID=?");
			q.setParameter(1, studentresult.get(i).getStudent_ID());
			List<StudentDetail> res = (List<StudentDetail>) q.getResultList();
			if (res.size() > 0) {
				result.addAll(res);
			}
		}
	} catch (Exception e) {
		logger.error("Exception -->"+e.getMessage());
	}
	return result;
}
	@SuppressWarnings("unchecked")
	private List<StudentDetail> getStudentEmailBySchools(int classid) {
	List<StudentDetail> result = null;Query q = null;
	try {
		result = new ArrayList<StudentDetail>();
		q = entitymanager.createQuery("from StudentClass where class_ID=? and status='Active'");
		q.setParameter(1, classid);
		List<StudentClass> studentclasslist = (List<StudentClass>) q.getResultList();
		if(studentclasslist.size()>0){
			for (int i = 0; i < studentclasslist.size(); i++) {
				q=null;
				q = entitymanager.createQuery("from StudentDetail where student_ID=?");
				q.setParameter(1, studentclasslist.get(i).getStudent().getStudent_ID());
				List<StudentDetail> studentdetaillist=(List<StudentDetail>)q.getResultList();
				if(studentdetaillist.size()>0){
					result.addAll(studentdetaillist);
				}
			}
		}
	} catch (Exception e) {
		logger.error("Exception -->"+e.getMessage());
	}
	return result;
}

	@Override
	@SuppressWarnings("unchecked")
	public List<StaffDataBean> tagstaffview(String personID, StaffDataBean staffDataBean) {

		Query q = null;
		Query q1 = null;
		List<StaffDataBean> resultList = null;
		List<Person> roleStatus = null;
		int school_ID = 0;
		try {
			roleStatus = new ArrayList<Person>();
			resultList = new ArrayList<StaffDataBean>();
			StaffDataBean databean = new StaffDataBean();
			if (staffDataBean.getStaStaffID() != null) {
				if (personID != null) {
					roleStatus = getRollType(personID);
					logger.debug("Roll Size" + roleStatus.size());
					if (roleStatus.size() > 0) {
						school_ID = roleStatus.get(0).getSchool().getSchool_ID();
						if (school_ID > 0) {
							q = entitymanager
									.createQuery("from Staff where rollNumber=? and school_ID=? and status='Active'");
							q.setParameter(1, staffDataBean.getStaStaffID());
							q.setParameter(2, school_ID);
							List<Staff> result = (List<Staff>) q.getResultList();
							if (result.size() > 0) {
								databean.setStaffList(result);
								q1 = entitymanager.createQuery(
										"from ImagePath where staff_ID=? and (rollStatus='Staff' or rollStatus='BookShop') and status='Active'");
								q1.setParameter(1, result.get(0).getStaff_ID());
								List<ImagePath> resultImage = (List<ImagePath>) q1.getResultList();
								if (resultImage.size() > 0) {
									databean.setImagepath(resultImage);
								}
								resultList.add(databean);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return resultList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StaffDataBean> staffinfomation(String personID, StaffDataBean staffDataBean) {
		Query q2 =null;
		Query q = null;
		int staffid=0;
		List<StaffDataBean> staff = null;
		List<Person> roleStatus = null;
		int school_ID = 0;
		try {
			roleStatus = new ArrayList<Person>();
			staff = new ArrayList<StaffDataBean>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				logger.debug("rolestatus"+roleStatus);
				if (roleStatus.size() > 0) {
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						q2=entitymanager.createQuery("from Person where person_role=? and status='Active'");
						q2.setParameter(1, staffDataBean.getRollcheck());
						List<Person> reslt=(List<Person>) q2.getResultList();
						if(reslt.size()>0){
						for (int i = 0; i < reslt.size(); i++) {
							staffid=reslt.get(i).getPerson_ID();
						
						q = entitymanager.createQuery("from Staff where school_ID=? and person_ID=? and status='Active'");
						q.setParameter(1, school_ID);
						q.setParameter(2, staffid);
						List<Staff> staffdetail = (List<Staff>) q.getResultList();
						logger.debug("Inside------dao--list" + staffdetail.size());
						logger.debug("staffdetail"+staffdetail.size());
						if (staffdetail.size() > 0) {
							for (Staff rag : staffdetail) {
								StaffDataBean staf = new StaffDataBean();
								staf.setName(rag.getFirstName() + "" + rag.getLastName());
								staf.setStaStaffID(rag.getRollNumber());
								staf.setStaEmail(rag.getEmailId());
								try {
								if("".equalsIgnoreCase(rag.getCountryCode()) || rag.getCountryCode().equals(null)){
									staf.setStaPhoneNo(rag.getPhoneNumber());
								}else{
									staf.setStaPhoneNo(rag.getCountryCode()+rag.getPhoneNumber());
								}
								}catch (NullPointerException e) {
									staf.setStaPhoneNo(rag.getPhoneNumber());
									staf.setCountryCode("");
								}
								staf.setStaGender(rag.getGender());
								staff.add(staf);
								}
							}
						  }
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return staff;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String staffidcheck(StaffDataBean staffDataBean, String staStaffID) {
		Query q = null;
		String status = "Exsist";
		try {
			q = entitymanager.createQuery("from Person where personRoleNumber=? and status='Active' ");
			q.setParameter(1, staStaffID);
			List<Person> rag = (List<Person>) q.getResultList();
			if (rag.size() > 0) {
				status = "Exsist";
			} else {
				status = "Avilable";
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			status = "Exsist";
		}
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String insertStaff(String personID, StaffDataBean staffDataBean) {
		List<Person> roleStatus = null;
		String status = "Fail";
		Query q = null;
		//int newPerson_ID = 0;
		Query q1 = null;
		int staffID = 0;
		int school_ID = 0;
		//int studentID = 0;
		String secPwd = "";
		String EncPwd = "";
		Query q2 = null;
		List<Staff> staffLists = null;
		int user_ID = 0;
		int pid = 0;
		Timestamp timestamp = new Timestamp(date.getTime());
		Date date = new Date();
		try {
			roleStatus = new ArrayList<Person>();
			staffLists = new ArrayList<Staff>();

			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					staffDataBean.setSchoolLogo(roleStatus.get(0).getSchool().getLogopath());
					if (roleStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
						school_ID = roleStatus.get(0).getSchool().getSchool_ID();
						String school_name=roleStatus.get(0).getSchool().getName();
					    staffDataBean.setName(school_name);
					    staffDataBean.setSchoolID(school_ID);
						q = null;
						q = entitymanager.createQuery("from Person where person_role_number=?");
						q.setParameter(1, staffDataBean.getStaStaffID());
						List<Person> perlist3 = (List<Person>) q.getResultList();
						if (perlist3.size() > 0) {
							status = "Exsist";
						} else {

							// Insert Person Table
							Person person = new Person();
							if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
								person.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
								person.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
							}else{
								person.setCreatedDate(date);
								person.setCreatedTime(timestamp);
							}	
							person.setPersonRoleNumber(staffDataBean.getStaStaffID());
							person.setPersonRole("BookShop");
							person.setStatus("Active");
							person.setSchool(entitymanager.find(School.class, school_ID));
							entitymanager.persist(person);
							entitymanager.flush();
							entitymanager.clear();
							q = null;
							q = entitymanager.createQuery("from Person where person_role=? and person_role_number=?");
							q.setParameter(1, "BookShop");
							q.setParameter(2, staffDataBean.getStaStaffID());
							List<Person> perlist = (List<Person>) q.getResultList();
							
							if (perlist.size() > 0) {
								pid = perlist.get(0).getPerson_ID();
								Staff staff = new Staff();
								if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
									staff.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
									staff.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
								}else{
									staff.setCreatedDate(date);
									staff.setCreatedTime(timestamp);
								}	
								staff.setRollNumber(staffDataBean.getStaStaffID());
								staff.setPerson(entitymanager.find(Person.class, pid));
								staff.setStatus("Active");
								staff.setSchool(entitymanager.find(School.class, school_ID));
								staff.setFirstName(staffDataBean.getStaFirstName());
								staff.setLastName(staffDataBean.getStaLastName());
								staff.setEmailId(staffDataBean.getStaEmail());
								staff.setPhoneNumber(staffDataBean.getStaPhoneNo());
								staff.setGender(staffDataBean.getStaGender());
								staff.setCountryCode(staffDataBean.getCountryCode());
								entitymanager.persist(staff);
								entitymanager.flush();
								entitymanager.clear();

								q1 = entitymanager.createQuery("from Staff where rollNumber=? and person_ID=?");
								q1.setParameter(1,staffDataBean.getStaStaffID());
								q1.setParameter(2,pid);
								List<Staff> res = (List<Staff>) q1.getResultList();
								if (res.size() > 0) {
									staffID = res.get(0).getStaff_ID();
								 if(staffDataBean.getStafilePath().equals("") || staffDataBean.getStafilePath()==null){
							       }else{
									StringTokenizer stoken = new StringTokenizer(staffDataBean.getStafilePath());
									Date d = dateFormat.parse(stoken.nextToken("/"));
									String tempPath = staffDataBean.getStafilePath();
									String path = tempPath.substring(tempPath.lastIndexOf("/") + 1);

									// Insert ImagePath table
									ImagePath imagepath = new ImagePath();
									imagepath.setDate(d);
									imagepath.setPath(path);
									imagepath.setName(staffDataBean.getStaStaffID());
									imagepath.setRollStatus("BookShop");
									imagepath.setStaff(entitymanager.find(Staff.class, staffID));
									imagepath.setStatus("Active");
									entitymanager.persist(imagepath);
									entitymanager.flush();
									entitymanager.clear();
							        }
									secPwd = RandomPasswordGenerator
											.GenerateSecurePassword(staffDataBean.getStaStaffID());// used
																									// to
																									// generate
																									// secure
																									// random
																									// password
									if (secPwd != null) {
										staffDataBean.setStaSecurePasword(secPwd);
										staffDataBean.setStaUsername(staffDataBean.getStaStaffID());
										EncPwd = PasswordEncryption
												.GeneratePaswword(staffDataBean.getStaSecurePasword());// used
																										// to
																										// generate
																										// Encryption
																										// Password
										logger.debug("Encription " + EncPwd + "----" + secPwd);
										if (EncPwd != null) {
											logger.debug(
													"----------------------------------Insert Login Staff 7 Begin-----------------------------------------------");
											// Insert User Table
											User user = new User();
											user.setStatus("Active");
											user.setUsername(staffDataBean.getStaUsername());
											user.setPerson(entitymanager.find(Person.class, pid));
											user.setPassword(EncPwd);
											entitymanager.persist(user);
											q2 = entitymanager.createQuery("from User where username=? and person_ID=?");
											q2.setParameter(1,staffDataBean.getStaUsername());
											q2.setParameter(2,pid);
											List<User> userList = (List<User>) q2.getResultList();
											if (userList.size() > 0) {
												user_ID = userList.get(0).getHas_user_ID();
												logger.debug("user ID" + user_ID);

												String menuStatus = insertBookShopMenus(user_ID,school_ID);// call
																								// insertStaffMenus
																								// method
																								// call
												if (menuStatus.equalsIgnoreCase("Sucess")) {
													status = "Success";
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}

	@Transactional(value = "transactionManager")
	private String insertBookShopMenus(int user_ID,int school_ID) {
		String res = "fail";
		int mainMenuID1 = 0;
		String res1 = "F";
		String res2 = "F";
		try {
			// Insert UserProduct table
			if (user_ID > 0) {
				if(school_ID==1 || school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5 || school_ID==6){
					mainMenuID1 = getMainMenu("BOO400");					
				}else{
					mainMenuID1 = getMainMenu("BOO500");
				}
				if (mainMenuID1 > 0) {

					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res1 = "S";
				}
				mainMenuID1 = getMainMenu("NOT400");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res2 = "S";
				}

				if (res1.equalsIgnoreCase("S") && res2.equalsIgnoreCase("S")) {
					res = "Sucess";
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res;
	}
	
	@Transactional(value = "transactionManager")
	private String insertStaffMenus(int user_ID) {
		String res = "fail";
		int mainMenuID1 = 0;
		String res1 = "F";
		String res2 = "F";
		try {
			// Insert UserProduct table
			if (user_ID > 0) {
				mainMenuID1 = getMainMenu("BOO400");
				if (mainMenuID1 > 0) {

					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res1 = "S";
				}
				mainMenuID1 = getMainMenu("NOT400");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res2 = "S";
				}

				if (res1.equalsIgnoreCase("S") && res2.equalsIgnoreCase("S")) {
					res = "Sucess";
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StaffDataBean> getImagePath3(String personID, String staffID) {
		Query q = null;
		Query q1 = null;
		int staff_ID = 0;
		List<StaffDataBean> finalList = null;
		try {
			if ((personID != null) && (!staffID.equalsIgnoreCase(""))) {
				staff_ID = getStaffID(staffID);
				if (staff_ID > 0) {
					q = entitymanager.createQuery("from ImagePath where staff_ID=? and (rollStatus='BookShop' or rollStatus='Staff')");
					q.setParameter(1, staff_ID);
					List<ImagePath> result = (List<ImagePath>) q.getResultList();
					if (result.size() > 0) {
						finalList = new ArrayList<StaffDataBean>();
						q1 = entitymanager.createQuery("from Staff where staff_ID=?");
						q1.setParameter(1, staff_ID);
						List<Staff> res = (List<Staff>) q1.getResultList();
						if (res.size() > 0) {
							StaffDataBean staffDataBean = new StaffDataBean();
							staffDataBean.setStafilePath(result.get(0).getPath());
							staffDataBean.setStaPathDate(result.get(0).getDate());
							staffDataBean.setStaFirstName(res.get(0).getFirstName());
							staffDataBean.setStaLastName(res.get(0).getLastName());
							staffDataBean.setStaGender(res.get(0).getGender());
							staffDataBean.setStaEmail(res.get(0).getEmailId());
							staffDataBean.setStaStaffID(staffID);
							staffDataBean.setStaPhoneNo(res.get(0).getPhoneNumber());
							finalList.add(staffDataBean);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return finalList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StaffDataBean> getImagePathstaff3(String personID, String staffID) {
		Query q = null;
		Query q1 = null;
		int staff_ID = 0;
		List<StaffDataBean> finalList = null;
		try {
			if ((personID != null) && (!staffID.equalsIgnoreCase(""))) {
				staff_ID = getStaffID(staffID);
				if (staff_ID > 0) {
					q = entitymanager.createQuery("from ImagePath where staff_ID=? and (rollStatus='Staff' or rollStatus='BookShop')");
					q.setParameter(1, staff_ID);
					List<ImagePath> result = (List<ImagePath>) q.getResultList();
					if (result.size() > 0) {
						finalList = new ArrayList<StaffDataBean>();
						q1 = entitymanager.createQuery("from Staff where staff_ID=?");
						q1.setParameter(1, staff_ID);
						List<Staff> res = (List<Staff>) q1.getResultList();
						if (res.size() > 0) {
							StaffDataBean staffDataBean = new StaffDataBean();
							staffDataBean.setStafilePath(result.get(0).getPath());
							staffDataBean.setStaPathDate(result.get(0).getDate());
							staffDataBean.setStaFirstName(res.get(0).getFirstName());
							staffDataBean.setStaLastName(res.get(0).getLastName());
							staffDataBean.setStaGender(res.get(0).getGender());
							staffDataBean.setStaEmail(res.get(0).getEmailId());
							staffDataBean.setStaStaffID(staffID);
							staffDataBean.setStaPhoneNo(res.get(0).getPhoneNumber());
							finalList.add(staffDataBean);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return finalList;
	}
	
	@SuppressWarnings("unchecked")
	private int getStaffID(String staffID) {
		Query q = null;
		int res = 0;
		try {
			if (!staffID.equalsIgnoreCase("")) {
				q = entitymanager.createQuery("from Staff where rollNumber=? and status='Active'");
				q.setParameter(1, staffID);
				List<Staff> result = (List<Staff>) q.getResultList();
				if (result.size() > 0) {
					res = result.get(0).getStaff_ID();
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String updateStaff(String personID, StaffDataBean staffDataBean) {
		List<Person> roleStatus = null;
		int school_ID = 0;
		int staffId = 0;
		Query q = null;
		Timestamp timestamp = new Timestamp(date.getTime());
		Date date = new Date();
		String status = "Fail";
		try {
			roleStatus = new ArrayList<Person>();

			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					staffDataBean.setSchoolLogo(roleStatus.get(0).getSchool().getLogopath());
					staffDataBean.setSchoolID(roleStatus.get(0).getSchool().getSchool_ID());
					staffDataBean.setSchoolName(roleStatus.get(0).getSchool().getName());
					if (roleStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
						school_ID = roleStatus.get(0).getSchool().getSchool_ID();
						if (school_ID > 0) {
							staffId = getStaffID(staffDataBean.getStaStaffID());
							if (staffId > 0) {
								Staff staff = entitymanager.find(Staff.class, staffId);
								if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
									staff.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
									staff.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
								}else{
									staff.setCreatedDate(date);
									staff.setCreatedTime(timestamp);
								}	
								staff.setRollNumber(staffDataBean.getStaStaffID());
								staff.setStatus("Active");
								staff.setFirstName(staffDataBean.getStaFirstName());
								staff.setLastName(staffDataBean.getStaLastName());
								staff.setEmailId(staffDataBean.getStaEmail());
								staff.setPhoneNumber(staffDataBean.getStaPhoneNo());
								staff.setGender(staffDataBean.getStaGender());
								staff.setCountryCode(staffDataBean.getCountryCode());
								entitymanager.merge(staff);
								if (staffDataBean.getStafilePath() != null) {
									q = entitymanager
											.createQuery("from ImagePath where staff_ID=? and rollStatus= ? ");
									q.setParameter(1, staffId);
									q.setParameter(2, staffDataBean.getRollcheck());
									List<ImagePath> result = (List<ImagePath>) q.getResultList();
									if (result.size() > 0) {
										StringTokenizer stoken = new StringTokenizer(staffDataBean.getStafilePath());
										Date d = dateFormat.parse(stoken.nextToken("/"));
										String tempPath = staffDataBean.getStafilePath();
										String path = tempPath.substring(tempPath.lastIndexOf("/") + 1);

										ImagePath imagepath = entitymanager.find(ImagePath.class,
												result.get(0).getImage_ID());
										imagepath.setDate(d);
										imagepath.setPath(path);
										imagepath.setName(staffDataBean.getStaStaffID());
										imagepath.setRollStatus(staffDataBean.getRollcheck());
										imagepath.setStaff(entitymanager.find(Staff.class, staffId));
										imagepath.setStatus("Active");
										entitymanager.merge(imagepath);
									}else{
										Date d = dateFormat.parse(staffDataBean.getStafilePath().split("/")[0]);
										String path = staffDataBean.getStafilePath().split("/")[1];
										// Insert ImagePath table
										ImagePath imagepath = new ImagePath();
										imagepath.setDate(d);
										imagepath.setPath(path);
										imagepath.setName(staffDataBean.getStaStaffID());
										imagepath.setRollStatus(staffDataBean.getRollcheck());
										imagepath.setStaff(entitymanager.find(Staff.class, staffId));
										imagepath.setStatus("Active");
										entitymanager.persist(imagepath);
									}
								}
								status = "Success";

							}
						}
					}
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;

	}

	@Override
	@Transactional(value = "transactionManager")
	public String deleteStaff(String personID, StaffDataBean staffDataBean) {
		List<Person> roleStatus = null;
		int school_ID = 0;
		int staffId = 0;
		Query q = null;
		Timestamp timestamp = new Timestamp(date.getTime());
		Date date = new Date();
		String status = "Fail";
		try {
			roleStatus = new ArrayList<Person>();

			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					if (roleStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
						school_ID = roleStatus.get(0).getSchool().getSchool_ID();
						if (school_ID > 0) {
							staffId = getStaffID(staffDataBean.getStaStaffID());
							if (staffId > 0) {
								Staff staff = entitymanager.find(Staff.class, staffId);
								if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
									staff.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
									staff.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
								}else{
									staff.setCreatedDate(date);
									staff.setCreatedTime(timestamp);
								}	
								staff.setStatus("De-Active");
								entitymanager.merge(staff);
								status = "Success";
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}

	/*Clinton Oct 25 forgot*/
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String forgot(LoginAccess loginAccess) {

		Query q = null;
		Query q1 = null;
		String roleType = "Fail";
		String mailid = "null";
		String OTPass = "";
		int personID = 0;
		List<TeacherDetail> teacherdetails = null;
		//List<ParentDetail> parentdetails = null;
		List<StudentDetail> studentDetails = null;
		List<Parent> parentList = null;
		try {

			teacherdetails = new ArrayList<TeacherDetail>();
			parentList = new ArrayList<Parent>();
			studentDetails = new ArrayList<StudentDetail>();
			q = entitymanager.createQuery("from User where username=? and status='Active'");
			q.setParameter(1, loginAccess.getUsername());
			List<User> result = (List<User>) q.getResultList();

			//Person res = null;
			if (result.size() > 0) {
				OTPass = RandomPasswordGenerator.GenerateSecureOTP();
				if (OTPass != null) {
					User OTP = entitymanager.find(User.class, result.get(0).getHas_user_ID());
					OTP.setOTP(OTPass);
					entitymanager.merge(OTP);
					logger.debug("OTP inserted successfully" + OTPass);
					loginAccess.setTemOTP(OTPass);
					personID = result.get(0).getPerson().getPerson_ID();
					logger.debug("Person Role" + result.get(0).getPerson().getPersonRole());

					roleType = result.get(0).getPerson().getPersonRole();
					if (roleType.equalsIgnoreCase("Admin")) {

						mailid = result.get(0).getPerson().getSchool().getEmailId();
						logger.debug("admin mail ID" + mailid);

					} else if (roleType.equalsIgnoreCase("Teacher")) {
						int teacherID = getTeacherIDs(result.get(0).getPerson().getPersonRoleNumber());
						logger.debug("Teacher ID" + teacherID);
						if (teacherID > 0) {
							teacherdetails = getTeacherDetails(teacherID, String.valueOf(personID));
							if (teacherdetails.size() > 0) {
								mailid = teacherdetails.get(0).getEmailId();
							}
						}
					} else if (roleType.equalsIgnoreCase("Parent")) {
						parentList = getParentList1(String.valueOf(personID),
								result.get(0).getPerson().getPersonRoleNumber());
						logger.debug("Parent ID" + parentList);
						if (parentList.size() > 0) {
							mailid = parentList.get(0).getParentDetail().getEmaiId();
						}

					} else if (roleType.equalsIgnoreCase("Student")) {
						int studentID = getStudentID(result.get(0).getPerson().getPersonRoleNumber());
						logger.debug("Student ID" + studentID);
						if (studentID > 0) {
							q1 = entitymanager.createQuery("from StudentDetail where student_ID=?");
							q1.setParameter(1, studentID);
							List<StudentDetail> result1 = (List<StudentDetail>) q1.getResultList();
							//String stumail = null;
							if (result1.size() > 0) {
								mailid = result1.get(0).getEmailId();

							}
						}
					} else if (roleType.equalsIgnoreCase("Staff") || roleType.equalsIgnoreCase("Librarian") || roleType.equalsIgnoreCase("BookShop")) {
						q = entitymanager.createQuery("from Staff where rollNumber=? and status='Active'");
						q.setParameter(1, result.get(0).getPerson().getPersonRoleNumber());
						List<Staff> staff = (List<Staff>) q.getResultList();
						if (staff.size() > 0) {
							mailid = staff.get(0).getEmailId();
						}

					}

				}
			}
		} catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
		return mailid;
	}

	@SuppressWarnings("unchecked")
	private int getTeacherIDs(String teaID) {
		Query q = null;
		int res = 0;
		try {
			if (!teaID.equalsIgnoreCase("")) {
				q = entitymanager.createQuery("from Teacher where rollNumber=? and status='Active'");
				q.setParameter(1, teaID);
				List<Teacher> result = (List<Teacher>) q.getResultList();
				if (result.size() > 0) {
					res = result.get(0).getTeacher_ID();
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public String otpvarify(String otp, String username) {
		String status = "Fail";
		//String otpck = null;
		Query q = null;
		try {
			q = entitymanager.createQuery("from User where username=? and status='Active'");
			q.setParameter(1, username);
			List<User> result = (List<User>) q.getResultList();
			if (result.size() > 0) {
				status = result.get(0).getOTP();
			}
		} catch (Exception e) {

		}
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String setNewpassword(String newPassword, String username) {
		Query q = null;
		String status = "failure";
		String NewEncPwd = null;
		logger.debug("Username inside newpassword page" + username);
		try {
			if (newPassword != null) {
				q = entitymanager.createQuery("from User where username=? and status='Active'");
				q.setParameter(1, username);
				List<User> result = (List<User>) q.getResultList();
				if (result.size() > 0) {
					NewEncPwd = PasswordEncryption.GeneratePaswword(newPassword);
					if (NewEncPwd != null) {
						User Pass = entitymanager.find(User.class, result.get(0).getHas_user_ID());
						Pass.setPassword(NewEncPwd);
						entitymanager.merge(Pass);
						logger.debug("encrypted password inserted" + Pass);
						status = "Success";
					}
				}
			}
		} catch (Exception e) {

		}
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String getBookDelete(String personID, BookSaleDataBean bookSaleDataBean) {
		logger.debug("----------inside getBookDelete---------");
		Query q = null;
		//int clsid = 0;
		List<Person> roleStatus = null;
		String rollType = "NotValid";
		//String no;
		String status = "";
		int salesid = 0;
		try {
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					if (rollType.equalsIgnoreCase("Bookshop")) {
						q = entitymanager.createQuery("from BookSalesRecord where book_name=? and status='Active'");
						q.setParameter(1, bookSaleDataBean.getBookName());
						List<BookSalesRecord> booklist = (List<BookSalesRecord>) q.getResultList();
						logger.debug("-------list size-----" + booklist.size());
						if (booklist.size() > 0) {
							salesid = booklist.get(0).getSales_record_ID();
							logger.debug("------salesid-------" + salesid);
							BookSalesRecord obj = entitymanager.find(BookSalesRecord.class, salesid);
							obj.setStatus("De-Active");
							entitymanager.merge(obj);
							status = "Success";

						}

					}

				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.debug("-------outside getbookdelete-------");
		return status;

	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String getBookUpdate(String personID, BookSaleDataBean bookSaleDataBean) {
		logger.debug("----------inside getBookUpdate---------");
		Query q = null;
		//int clsid = 0;
		List<Person> roleStatus = null;
		String rollType = "NotValid";
		//String no;
		String status = "";
		int salesid = 0;
		BigDecimal amount = new BigDecimal(0);
		try {
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					int school_ID=roleStatus.get(0).getSchool().getSchool_ID();
					if (rollType.equalsIgnoreCase("Bookshop")) {
						q = entitymanager.createQuery("from BookSalesRecord where book_name=? and status='Active'");
						q.setParameter(1, bookSaleDataBean.getBookName());
						List<BookSalesRecord> booklist = (List<BookSalesRecord>) q.getResultList();
						logger.debug("-------list size-----" + booklist.size());
						if (booklist.size() > 0) {
							salesid = booklist.get(0).getSales_record_ID();
							logger.debug("------salesid-------" + salesid);
							BookSalesRecord obj = entitymanager.find(BookSalesRecord.class, salesid);
							obj.setBookName(bookSaleDataBean.getBookName());
							obj.setPrice(bookSaleDataBean.getBookPrice());
							obj.setImagePath(bookSaleDataBean.getBookfilePath());							
							if(school_ID == 35 || school_ID== 36 || school_ID==37 || school_ID==38 || school_ID==39 || school_ID==40 || school_ID==41 || school_ID==42 || school_ID==43){
								
							}else{
								obj.setQuantity(bookSaleDataBean.getBookQuantity());
								amount = new BigDecimal(bookSaleDataBean.getBookQuantity())
								.multiply(new BigDecimal(bookSaleDataBean.getBookPrice()));
								obj.setNetAmount(amount.toString());
							}
							obj.setStatus("Active");
							entitymanager.merge(obj);
							status = "Success";

						}

					}

				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.debug("-------outside getbookupdate-------");
		return status;

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BookSalesRecord> getBookDetailInfo(String personID, BookSaleDataBean bookSaleDataBean) {
		logger.debug("----------inside getBookDetailInfo---------");
		Query q = null;
		List<BookSalesRecord> booklist1 = null;
		//int clsid = 0;
		List<Person> roleStatus = null;
		String rollType = "NotValid";
		//String no;
		try {
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					if (rollType.equalsIgnoreCase("Bookshop")) {
						q = entitymanager.createQuery("from BookSalesRecord where book_name=? and status='Active' ");
						q.setParameter(1, bookSaleDataBean.getBookName());
						booklist1 = (List<BookSalesRecord>) q.getResultList();

					}

				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}

		return booklist1;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BookSaleDataBean> getBookInfo1(String personID, BookSaleDataBean bookSaleDataBean) {
		logger.debug("----------inside getBookInfo---------");
		Query q = null;
		List<BookSaleDataBean> booklist1 = new ArrayList<BookSaleDataBean>();
		//int clsid = 0;
		List<Person> roleStatus = null;
		String rollType = "NotValid";
		//String no;
		int schoolid=0;
		try {
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					schoolid=roleStatus.get(0).getSchool().getSchool_ID();
					if (rollType.equalsIgnoreCase("Admin") || rollType.equalsIgnoreCase("Bookshop")) {
						q = entitymanager.createQuery("from BookSalesRecord where school_ID=? and status='Active'");
						q.setParameter(1, schoolid);
						List<BookSalesRecord> booklist = (List<BookSalesRecord>) q.getResultList();
						logger.debug("--------list size-----" + booklist);
						if (booklist.size() > 0) {
							int sno = 1;
							for (int i = 0; i < booklist.size(); i++) {
								BookSaleDataBean obj = new BookSaleDataBean();
								obj.setSerial(String.valueOf(sno));
								obj.setBookName(booklist.get(i).getBookName());
								obj.setBookPrice(booklist.get(i).getPrice());
								obj.setBookQuantity(booklist.get(i).getQuantity());
								obj.setAmount(booklist.get(i).getNetAmount());
								booklist1.add(obj);
								bookSaleDataBean.setBooklist2(booklist1);
								sno++;

							}
						}
					}

				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}

		return booklist1;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String insertAddBook(String personID, BookSaleDataBean bookSaleDataBean) {
		logger.debug("-----------inside insertAddBook----------");
		List<Person> rolestatus = null;
		String roletype = "not valid";
		int schoolid=0;
		BigDecimal amount = new BigDecimal(0);
		String status = "";
		Query q = null;

		try {
			rolestatus = new ArrayList<Person>();
			if (personID != null) {
				rolestatus = getRollType(personID);
				if (rolestatus.size() > 0) {
					roletype = rolestatus.get(0).getPersonRole();
					schoolid=rolestatus.get(0).getSchool().getSchool_ID();
					if (roletype.equalsIgnoreCase("Bookshop")) {
						q = null;
						q = entitymanager.createQuery("from BookSalesRecord where book_name=? and school_ID=? and status='Active'");
						q.setParameter(1, bookSaleDataBean.getBookName());
						q.setParameter(2, schoolid);
						List<BookSalesRecord> booklist = (List<BookSalesRecord>) q.getResultList();
						logger.debug("book size "+booklist.size());
						if (booklist.size() > 0) {
							status = "Failure";
						} else {
							BookSalesRecord book = new BookSalesRecord();
							book.setBookName(bookSaleDataBean.getBookName());
							book.setPrice(bookSaleDataBean.getBookPrice());							
							book.setImagePath(bookSaleDataBean.getBookfilePath());
							book.setSchool(entitymanager.find(School.class, schoolid));
							book.setStatus("Active");
							if(schoolid==2|| schoolid==3 || schoolid==4 ||schoolid==5 || schoolid==6){
								amount = new BigDecimal(bookSaleDataBean.getBookQuantity())
								.multiply(new BigDecimal(bookSaleDataBean.getBookPrice()));
								logger.debug("--------amount--------" + amount);
								book.setQuantity(bookSaleDataBean.getBookQuantity());
								logger.info("Quantity ---------->"+book.getQuantity()); 
								book.setNetAmount(amount.toString());
							}else if(schoolid==35|| schoolid==36 || schoolid==37 ||schoolid==38 || schoolid==39 || schoolid==40 || schoolid==41 || schoolid==42 || schoolid==43){
								book.setQuantity("0");
								book.setNetAmount("0");
							}
							else{
								amount = new BigDecimal(bookSaleDataBean.getBookQuantity())
								.multiply(new BigDecimal(bookSaleDataBean.getBookPrice()));
								logger.debug("--------amount--------" + amount);
								book.setQuantity(bookSaleDataBean.getBookQuantity());
								logger.info("Quantity ---------->"+book.getQuantity()); 
								book.setNetAmount(amount.toString());
							}
							entitymanager.persist(book);
							status = "Success";
						}
						
					}

				}

			}
		} catch (Exception e) {
			logger.warn(" exception - "+e);
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getRollNumber(String personID) {
		logger.debug("---------inside getroll--------");
		//String status = "Failed";
		List<Person> roll = null;
		String rollType = "Not Valid";
		String rollnumber = "";
		Query q = null;
		int perid = 0;
		int parentid = 0;
		int studid = 0;
		try {
			roll = new ArrayList<Person>();
			roll = getRollType(personID);
			if (personID != null) {
				rollType = roll.get(0).getPersonRole();
				logger.debug("-------roll size----" + roll.size());
				if (roll.size() > 0) {
					rollType = roll.get(0).getPersonRole();
					logger.debug("roll -- " + rollType);
					if (rollType.equalsIgnoreCase("Parent")) {
						perid = roll.get(0).getPerson_ID();
						q = null;
						q = entitymanager.createQuery("from Parent where person_ID=? and status='Active'");
						q.setParameter(1, perid);
						List<Parent> parlist = (List<Parent>) q.getResultList();
						if (parlist.size() > 0) {
							parentid = parlist.get(0).getParent_ID();
							q = null;
							q = entitymanager.createQuery("from StudentParent where parent_ID=?");
							q.setParameter(1, parentid);
							List<StudentParent> splist = (List<StudentParent>) q.getResultList();
							if (splist.size() > 0) {
								studid = splist.get(0).getStudent().getStudent_ID();
								q = null;
								q = entitymanager.createQuery("from Student where student_ID=? and status='Active'");
								q.setParameter(1, studid);
								List<Student> studlist = (List<Student>) q.getResultList();
								if (studlist.size() > 0) {
									rollnumber = studlist.get(0).getRollNumber();
								}
							}
						}
					} else if (rollType.equalsIgnoreCase("Student")) {
						rollnumber = roll.get(0).getPersonRoleNumber();
					} else if (rollType.equalsIgnoreCase("Teacher")) {
						rollnumber = roll.get(0).getPersonRoleNumber();

					} else if (rollType.equalsIgnoreCase("Staff") || rollType.equalsIgnoreCase("Librarian")) {
						rollnumber = roll.get(0).getPersonRoleNumber();
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.debug("---------outside getroll-----");
		return rollnumber;

	}

	@Override
	@SuppressWarnings("unchecked")
	public String checkLibrarianID(LibrarianDataBean librarianDataBean, String libID) {
		Query q = null;
		String s = "Exsist";
		try {
			q = entitymanager.createQuery("from Person where personRoleNumber=? and status='Active' ");
			q.setParameter(1, librarianDataBean.getLibID());
			List<Person> rollSize = (List<Person>) q.getResultList();
			if (rollSize.size() > 0) {
				s = "Exsist";
			} else {
				s = "Available";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			s = "Exsist";
		}
		return s;
	}

	/**
	 * Neela 6-5-2016 This method is used to insert Librarian details
	 */
	@Transactional(value = "transactionManager")
	@Override
	@SuppressWarnings("unchecked")
	public String insertLibrarian(String personID, LibrarianDataBean librarianDataBean) {
		logger.debug("-------inside insert student------");
		String status = "Failure";
		Query q = null;
		Date date = new Date();
		List<Person> roleStatus = null;
		int school_ID = 0;
		Timestamp timestamp = new Timestamp(date.getTime());
		try {
			int schlid = 0;
			int pid = 0;
			int stafid = 0;
			//int postid = 0;
			//int postid1 = 0;
			//int classid = 0;
			int user_ID = 0;
			Query insetMenu1 = null;
			String secPwd = "";
			String EncPwd = "";
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					librarianDataBean.setSchoolLogo(roleStatus.get(0).getSchool().getLogopath());
					if (roleStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
						schlid = roleStatus.get(0).getSchool().getSchool_ID();
						librarianDataBean.setSchoolID(schlid);
						librarianDataBean.setSchoolName(roleStatus.get(0).getSchool().getName());
						q = null;
						q = entitymanager.createQuery("from Person where person_role_number=?");
						q.setParameter(1, librarianDataBean.getLibID());
						List<Person> perlist3 = (List<Person>) q.getResultList();
						if (perlist3.size() > 0) {
							status = "Exsist";
						} else {
							Person person = new Person();
							person.setPersonRole("Librarian");
							person.setSchool(entitymanager.find(School.class, schlid));
							person.setPersonRoleNumber(librarianDataBean.getLibID());
							person.setStatus("Active");
							if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
								person.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
								person.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
							}else{
								person.setCreatedDate(date);
								person.setCreatedTime(timestamp);
							}	
							entitymanager.persist(person);
							logger.debug("-------person table inserted------");
							q = null;
							q = entitymanager.createQuery("from Person where person_role=? and person_role_number=?");
							q.setParameter(1, "Librarian");
							q.setParameter(2, librarianDataBean.getLibID());
							List<Person> perlist = (List<Person>) q.getResultList();
							pid = perlist.get(0).getPerson_ID();
							logger.debug("---pid---" + pid);
							logger.debug("----perlist size---" + perlist.size());
							if (perlist.size() > 0) {
								Staff staff = new Staff();
								staff.setPerson(entitymanager.find(Person.class, pid));
								staff.setRollNumber(librarianDataBean.getLibID());
								staff.setFirstName(librarianDataBean.getLibFirstName());
								staff.setLastName(librarianDataBean.getLibLastName());
								staff.setEmailId(librarianDataBean.getLibEmail());
								staff.setGender(librarianDataBean.getLibGender());
								staff.setPhoneNumber(librarianDataBean.getLibPhoneNo());
								staff.setCountryCode(librarianDataBean.getCountryCode());
								if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
									staff.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
									staff.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
								}else{
									staff.setCreatedDate(date);
									staff.setCreatedTime(timestamp);
								}	
								staff.setSchool(entitymanager.find(School.class, schlid));
								staff.setStatus("Active");
								entitymanager.persist(staff);
								logger.debug("-------staff table inserted------");
								if(!librarianDataBean.getLibfilePath().equals("")){
								StringTokenizer stoken = new StringTokenizer(librarianDataBean.getLibfilePath());
								Date d = dateFormat.parse(stoken.nextToken("/"));
								String tempPath = librarianDataBean.getLibfilePath();
								String path = tempPath.substring(tempPath.lastIndexOf("/") + 1);
								stafid = getStaffID(librarianDataBean.getLibID());
								// Insert ImagePath table
								ImagePath imagepath = new ImagePath();
								imagepath.setDate(d);
								imagepath.setPath(path);
								imagepath.setName(librarianDataBean.getLibID());
								imagepath.setRollStatus("Librarian");
								imagepath.setLibrarianID(entitymanager.find(Staff.class, stafid));
								imagepath.setStatus("Active");
								entitymanager.persist(imagepath);
								}
								secPwd = RandomPasswordGenerator.GenerateSecurePassword(librarianDataBean.getLibID());// used
																														// to
																														// generate
																														// secure
																														// random
																														// password
								if (secPwd != null) {
									librarianDataBean.setLibSecurePassword(secPwd);
									librarianDataBean.setLibUsername(librarianDataBean.getLibID());
									EncPwd = PasswordEncryption
											.GeneratePaswword(librarianDataBean.getLibSecurePassword());// used
																										// to
																										// generate
																										// Encryption
																										// Password
									logger.debug("Encription " + EncPwd + "----" + secPwd);
									if (EncPwd != null) {
										// Insert User Table
										User user = new User();
										user.setStatus("Active");
										user.setUsername(librarianDataBean.getLibID());
										user.setPerson(entitymanager.find(Person.class, pid));
										user.setPassword(EncPwd);
										user.setLastLoginTime(timestamp);
										entitymanager.persist(user);
										logger.debug("-------user table inserted------");
										insetMenu1 = entitymanager.createQuery("from User where username=? and person_ID=?");
										insetMenu1.setParameter(1, librarianDataBean.getLibID());
										insetMenu1.setParameter(2, pid);
										List<User> userList = (List<User>) insetMenu1.getResultList();
										if (userList.size() > 0) {
											user_ID = userList.get(0).getHas_user_ID();
											logger.debug("user ID" + user_ID);
											String menuStatus = insertLibrarianMenus(user_ID);// call
																								// insertStudentMenus
																								// method
																								// call
											logger.debug("Menu Status" + menuStatus);
											status = "Success";
										}
									}
								}
							}
						}
					}
				}
			}
		}

		catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Exception -->"+e.getMessage());
		}

		return status;
	}

	/*
	 * private int getStaffID(String libID) { Query q=null; int res1=0; try{
	 * if(!libID.equalsIgnoreCase("")){ q=entitymanager.createQuery(
	 * "from Staff where rollNumber=? and status='Active'"); q.setParameter(1,
	 * libID); List<Staff> result=(List<Staff>)q.getResultList();
	 * if(result.size() >0){ res1=result.get(0).getStaff_ID(); } }
	 * }catch(Exception e){ logger.warn("Exception -->"+e.getMessage()); } return res1; }
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<LibrarianDataBean> getImagePath5(String personID, String libID) {
		logger.debug("-------inside getImagePath1------");
		Query q = null;
		Query q1 = null;
		int staff_ID = 0;
		List<LibrarianDataBean> finalList1 = new ArrayList<LibrarianDataBean>();
		try {
			if ((personID != null) && (!libID.equalsIgnoreCase(""))) {
				staff_ID = getStaffID(libID);
				logger.debug("----student_id----" + staff_ID);
				if (staff_ID > 0) {
					q = entitymanager.createQuery("from ImagePath where librarian_ID=? and rollStatus='Librarian'");
					q.setParameter(1, staff_ID);
					List<ImagePath> result = (List<ImagePath>) q.getResultList();
					logger.debug(" sizee -- " + result.size());
					
						q1 = entitymanager.createQuery("from Staff where staff_ID=? and status='Active'");
						q1.setParameter(1, staff_ID);
						List<Staff> stafflist = (List<Staff>) q1.getResultList();
						logger.debug(" stafflist -- " + stafflist.size());
						if (stafflist.size() > 0) {
							LibrarianDataBean librarianDataBean = new LibrarianDataBean();
							if (result.size() > 0) {
								librarianDataBean.setPath(result.get(0).getPath());
								librarianDataBean.setPathDate(result.get(0).getDate());
							}else{
								librarianDataBean.setPath("");
								librarianDataBean.setPathDate(null);
							}
							librarianDataBean.setLibFirstName(stafflist.get(0).getFirstName());
							librarianDataBean.setLibLastName(stafflist.get(0).getLastName());
							librarianDataBean.setLibGender(stafflist.get(0).getGender());
							librarianDataBean.setLibPhoneNo(stafflist.get(0).getPhoneNumber());
							librarianDataBean.setLibEmail(stafflist.get(0).getEmailId());
							librarianDataBean.setLibID(stafflist.get(0).getRollNumber());
							finalList1.add(librarianDataBean);
						}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Exception -->"+e.getMessage());
		}
		return finalList1;
	}

	@Transactional(value = "transactionManager")
	private String insertLibrarianMenus(int user_ID) {
		String res = "fail";
		int mainMenuID1 = 0;
		String res1 = "F";
		String res2 = "F";
		try {
			// Insert UserProduct table
			if (user_ID > 0) {
				mainMenuID1 = getMainMenu("LIB500");
				if (mainMenuID1 > 0) {

					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res1 = "S";
				}
				mainMenuID1 = getMainMenu("NOT500");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res2 = "S";
				}
				if (res1.equalsIgnoreCase("S") && res2.equalsIgnoreCase("S")) {
					res = "Sucess";
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LibrarianDataBean> getAllLibrarianInfo(String personID) {
		List<Person> rollStatus = null;
		List<Person> result = null;
		List<LibrarianDataBean> librarianList = null;
		Query q = null;
		int school_ID = 0;
		Query q1 = null;
		//int perid = 0;
		//int studid = 0;
		//int clsid = 0;
		//int teaid = 0;
		//String tearoll = "";
		//int parid = 0;
		try {
			if (personID != null) {
				rollStatus = new ArrayList<Person>();
				librarianList = new ArrayList<LibrarianDataBean>();
				rollStatus = getRollType(personID);
				logger.debug("Roll Size" + rollStatus.size());
				if (rollStatus.size() > 0) {
					if (rollStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
						school_ID = rollStatus.get(0).getSchool().getSchool_ID();
						//int pid = Integer.parseInt(personID);
						q = entitymanager.createQuery(
								"from Person where school_ID=? and status='Active' and personRole='Librarian' ");
						q.setParameter(1, school_ID);
						result = (List<Person>) q.getResultList();
						if (result.size() > 0) {
							for (int i = 0; i < result.size(); i++) {
								q1 = entitymanager.createQuery("from Staff where person_ID=? and status='Active'");
								q1.setParameter(1, result.get(i).getPerson_ID());
								List<Staff> stafdetaillist = (List<Staff>) q1.getResultList();
								if (stafdetaillist.size() > 0) {
									LibrarianDataBean librarianDataBean = new LibrarianDataBean();
									librarianDataBean.setName(stafdetaillist.get(0).getFirstName() + " "
											+ stafdetaillist.get(0).getLastName());
									librarianDataBean.setLibEmail(stafdetaillist.get(0).getEmailId());
									librarianDataBean.setLibID(stafdetaillist.get(0).getRollNumber());
									try {
									if("".equalsIgnoreCase(stafdetaillist.get(0).getCountryCode()) || stafdetaillist.get(0).getCountryCode().equalsIgnoreCase(null)){
										librarianDataBean.setLibPhoneNo(stafdetaillist.get(0).getPhoneNumber());
									}else{
										librarianDataBean.setLibPhoneNo(stafdetaillist.get(0).getCountryCode()+stafdetaillist.get(0).getPhoneNumber());
									}
									}catch (NullPointerException e) {
										librarianDataBean.setLibPhoneNo(stafdetaillist.get(0).getPhoneNumber());
									}
									librarianDataBean.setLibGender(stafdetaillist.get(0).getGender());
									librarianList.add(librarianDataBean);
								}
							}
						}
					}

					else {
						logger.debug("Not valid Person");
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Exception -->"+e.getMessage());
		}
		return librarianList;
	}
/*
	@SuppressWarnings("unchecked")
	private List<Person> getRollType1(String personID) {
		Query q = null;
		List<Person> rolls = null;
		try {

			if (personID != null) {
				int pid = Integer.parseInt(personID);
				q = entitymanager
						.createQuery("from Person where person_ID=? and status='Active' personRole='Librarian' ");
				q.setParameter(1, pid);
				rolls = (List<Person>) q.getResultList();
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return rolls;
	}*/

	@Override
	public String getLibrarianInfo(String personID, String libID, LibrarianDataBean librarianDataBean) {

		logger.debug("--------------Inside Dao getTeacherInfo method calling-------------");
		List<LibrarianDataBean> libList = null;
		List<Person> rollStatus = null;
		int school_ID = 0;
		//int librarian_ID = 0;
		List<Staff> libDetails = null;
		List<ImagePath> imgpath = null;
		List<ImagePath> libPath = null;
		List<Postcode> teaPost1 = null;
		List<Postcode> teaPost2 = null;
		Query q = null;
		try {
			libList = new ArrayList<LibrarianDataBean>();
			rollStatus = new ArrayList<Person>();
			libPath = new ArrayList<ImagePath>();
			teaPost1 = new ArrayList<Postcode>();
			teaPost2 = new ArrayList<Postcode>();
			if (personID != null && libID != null) {
				rollStatus = getRollType(personID);
				logger.debug("Roll Size" + rollStatus.size());
				if (rollStatus.size() > 0) {
					school_ID = rollStatus.get(0).getSchool().getSchool_ID();
					libDetails = getLibrarianDetails(libID, personID);
					logger.debug("lib detail -- " + libDetails.size());
					if (libDetails.size() > 0) {
						imgpath = getLibrarianID(libDetails.get(0).getStaff_ID());
						librarianDataBean
								.setName(libDetails.get(0).getFirstName() + " " + libDetails.get(0).getLastName());
						librarianDataBean.setLibFirstName(libDetails.get(0).getFirstName());
						librarianDataBean.setLibLastName(libDetails.get(0).getLastName());
						logger.debug("first name - " + librarianDataBean.getLibFirstName());
						if (imgpath.size()>0) {
							librarianDataBean.setPathDate(imgpath.get(0).getDate());
							librarianDataBean.setPath(imgpath.get(0).getPath());
						}
						librarianDataBean.setLibID(libDetails.get(0).getRollNumber());
						librarianDataBean.setLibGender(libDetails.get(0).getGender());
						librarianDataBean.setLibEmail(libDetails.get(0).getEmailId());
						try {
						if("".equalsIgnoreCase(libDetails.get(0).getCountryCode()) || libDetails.get(0).getCountryCode().equalsIgnoreCase(null)){
							librarianDataBean.setLibPhoneNo(libDetails.get(0).getPhoneNumber());
							librarianDataBean.setCountryCode("");
						}else{
							librarianDataBean.setLibPhoneNo(libDetails.get(0).getCountryCode()+libDetails.get(0).getPhoneNumber());
							librarianDataBean.setCountryCode(libDetails.get(0).getCountryCode());
						}}catch (NullPointerException e) {
							librarianDataBean.setLibPhoneNo(libDetails.get(0).getPhoneNumber());
							librarianDataBean.setCountryCode("");
						}
					}

				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	private List<ImagePath> getLibrarianID(int i) {
		Query q = null;
		List<ImagePath> result = null;
		try {

			q = entitymanager.createQuery("from ImagePath where librarian_ID=? and status='Active'");
			q.setParameter(1, i);
			result = (List<ImagePath>) q.getResultList();
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private List<Staff> getLibrarianDetails(String libID, String personID) {
		Query q = null;
		List<Staff> libDetails = null;
		try {
			if (personID != null) {
				libDetails = new ArrayList<Staff>();
				q = entitymanager.createQuery("from Staff where rollNumber=? and status='Active'");
				q.setParameter(1, libID);
				logger.debug("lib id -- " + libID);
				libDetails = (List<Staff>) q.getResultList();
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Exception -->"+e.getMessage());
		}
		return libDetails;
	}

	@SuppressWarnings("unchecked")
	private int getLibrarianID1(String libID) {
		Query q = null;
		int res = 0;
		try {
			if (!libID.equalsIgnoreCase("")) {
				q = entitymanager.createQuery("from Staff where rollNumber=? and status='Active'");
				q.setParameter(1, libID);
				List<Staff> result = (List<Staff>) q.getResultList();
				if (result.size() > 0) {
					res = result.get(0).getStaff_ID();
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res;
	}

	@Override
	@Transactional(value = "transactionManager")
	public String deleteLibrarian(String personID, LibrarianDataBean librarianDataBean) {
		String status = "Fail";
		List<Person> rollStatus = null;
		int school_ID = 0;
		int librarian_ID = 0;
		try {
			logger.debug("inside delete method --- " + librarianDataBean.getLibID());
			rollStatus = new ArrayList<Person>();
			if (personID != null && librarianDataBean.getLibID() != null) {
				rollStatus = getRollType(personID);
				if (rollStatus.size() > 0) {
					if (rollStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
						librarian_ID = getLibrarianID1(librarianDataBean.getLibID());
						if (librarian_ID > 0) {
							Staff staff = entitymanager.find(Staff.class, librarian_ID);
							staff.setStatus("De-Active");
							entitymanager.merge(staff);
							status = "Success";
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String libUpdate(LibrarianDataBean librarianDataBean, String personID) {
		Query q = null;
		String status = "fail";
		List<Person> rollStatus = null;
		int school_ID = 0;
		int librarian_ID = 0;
		int stafid = 0;
		try {
			logger.debug("inside edit method --- " + librarianDataBean.getLibID());
			rollStatus = new ArrayList<Person>();
			if (personID != null) {
				rollStatus = getRollType(personID);
				if (rollStatus.size() > 0) {
					librarianDataBean.setSchoolLogo(rollStatus.get(0).getSchool().getLogopath());
					librarianDataBean.setSchoolID(rollStatus.get(0).getSchool().getSchool_ID());
					librarianDataBean.setSchoolName(rollStatus.get(0).getSchool().getName());
					if (rollStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
						librarian_ID = getLibrarianID1(librarianDataBean.getLibID());
						if (librarian_ID > 0) {
							Staff staff = entitymanager.find(Staff.class, librarian_ID);
							staff.setRollNumber(librarianDataBean.getLibID());
							staff.setFirstName(librarianDataBean.getLibFirstName());
							staff.setLastName(librarianDataBean.getLibLastName());
							staff.setEmailId(librarianDataBean.getLibEmail());
							staff.setGender(librarianDataBean.getLibGender());
							staff.setPhoneNumber(librarianDataBean.getLibPhoneNo());
							if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
								staff.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
								staff.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
							}else{
								staff.setCreatedDate(date);
								staff.setCreatedTime(timestamp);
							}	
							staff.setCountryCode(librarianDataBean.getCountryCode());
							staff.setStatus("Active");
							entitymanager.merge(staff);
							// Insert ImagePath table
							logger.debug("path --" + librarianDataBean.getLibfilePath());
							if (librarianDataBean.getLibfilePath() != null) {
								logger.debug("image insert");
								q = entitymanager
										.createQuery("from ImagePath where librarian_ID=? and rollStatus='Librarian'");
								q.setParameter(1, librarian_ID);
								logger.debug("librarian id - " + librarian_ID);
								List<ImagePath> result = (List<ImagePath>) q.getResultList();
								logger.debug("path size - " + result.size());
								if (result.size() > 0) {
									StringTokenizer stoken = new StringTokenizer(librarianDataBean.getLibfilePath());
									Date d = dateFormat.parse(stoken.nextToken("/"));
									String tempPath = librarianDataBean.getLibfilePath();
									String path = tempPath.substring(tempPath.lastIndexOf("/") + 1);
									logger.debug("path -- " + path);
									ImagePath imagepath = entitymanager.find(ImagePath.class,
											result.get(0).getImage_ID());
									imagepath.setDate(d);
									imagepath.setPath(path);
									imagepath.setName(librarianDataBean.getLibID());
									imagepath.setRollStatus("Librarian");
									entitymanager.merge(imagepath);
								}else{
									Date d = dateFormat.parse(librarianDataBean.getLibfilePath().split("/")[0]);
									String path = librarianDataBean.getLibfilePath().split("/")[1];
									stafid = getStaffID(librarianDataBean.getLibID());
									// Insert ImagePath table
									ImagePath imagepath = new ImagePath();
									imagepath.setDate(d);
									imagepath.setPath(path);
									imagepath.setName(librarianDataBean.getLibID());
									imagepath.setRollStatus("Librarian");
									imagepath.setLibrarianID(entitymanager.find(Staff.class, stafid));
									imagepath.setStatus("Active");
									entitymanager.persist(imagepath);
								}
							}
							status = "success";
						}
					}
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;

	}

	@Override
	@SuppressWarnings("unchecked")
	public int getTotalTeacher(String personID) {
		int res = 0;
		Query q = null;
		List<Person> rollStatus = null;
		//int school_ID = 0;
		try {
			if (personID != null) {
				rollStatus = getRollType(personID);
				if (rollStatus.size() > 0) {
					q = entitymanager.createQuery("from Teacher where status=? and school_ID=?");
					q.setParameter(1, "Active");
					q.setParameter(2, rollStatus.get(0).getSchool().getSchool_ID());
					List<Teacher> result = (List<Teacher>) q.getResultList();
					if (result.size() > 0) {
						res = result.size();
					}
				}
			}
			logger.debug(res);
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	public int getTotalParent(String persionID) {
		int res = 0;
		Query q = null;
		List<Person> rollStatus = null;
		//int school_ID = 0;
		try {
			if (persionID != null) {
				rollStatus = getRollType(persionID);
				if (rollStatus.size() > 0) {
					q = entitymanager.createQuery("from Parent where status=? and school_ID=?");
					q.setParameter(1, "Active");
					q.setParameter(2, rollStatus.get(0).getSchool().getSchool_ID());
					List<Parent> result = (List<Parent>) q.getResultList();
					if (result.size() > 0) {
						res = result.size();
					}
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res;
	}

	@Transactional(value = "transactionManager")
	@Override
	@SuppressWarnings("unchecked")
	public String getStudentEdit(StudentDataBean studentDataBean,String personID) {
		logger.info("---------getStudentEdit---------------");
		String status = "Failed";
		Query q = null;
		int studid = 0;
		List<Person> rollStatus = null;
		int schoolid=0;
		//String stuPath;
		try {
			rollStatus=new ArrayList<Person>();
			StringTokenizer st = new StringTokenizer(studentDataBean.getStuCls());
			String className = st.nextToken("/");
			String classSection=studentDataBean.getStuCls();
			String section=classSection.substring(classSection.lastIndexOf("/")+1);
			logger.info("----class name---" + className);
			rollStatus=getRollType(personID);
			if(rollStatus.size()>0){
				studentDataBean.setSchoolLogo(rollStatus.get(0).getSchool().getLogopath());
				schoolid=rollStatus.get(0).getSchool().getSchool_ID();
			q = entitymanager.createQuery("from Class where class_name=? and classSection=? and school_ID=? and status='Active'");
			q.setParameter(1, className);
			q.setParameter(2, section);
			q.setParameter(3, schoolid);
			List<Class> classlist1 = (List<Class>) q.getResultList();
			if(classlist1.size()>0){
				logger.info("----class id------" + classlist1.get(0).getClass_ID());
			q = null;
			q = entitymanager.createQuery("from Student where roll_number=? and school_ID=? and status='Active'");
			q.setParameter(1, studentDataBean.getStuRollNo());
			q.setParameter(2, schoolid);
			List<Student> studlist = (List<Student>) q.getResultList();
			if (studlist.size() > 0) {
				studid = studlist.get(0).getStudent_ID();
			q = null;
			q = entitymanager.createQuery("from StudentDetail where student_ID=?");
			q.setParameter(1, studid);
			List<StudentDetail> studlist1 = (List<StudentDetail>) q.getResultList();
			if(studlist1.size()>0){
			q = null;
			q = entitymanager.createQuery("from StudentClass where student_ID=?");
			q.setParameter(1, studid);
			List<StudentClass> classlist = (List<StudentClass>) q.getResultList();
			logger.info("---studentclass size----" + classlist.size());
			/*q = null;
			q = entitymanager.createQuery("from Postcode where postcode=?");
			q.setParameter(1, studentDataBean.getStuPresentZip());
			List<Postcode> postlist = (List<Postcode>) q.getResultList();
			logger.debug("---postcode list size----" + postlist.size());
			q = null;
			q = entitymanager.createQuery("from Postcode where postcode=?");
			q.setParameter(1, studentDataBean.getStuPermanentZip());
			List<Postcode> postlist1 = (List<Postcode>) q.getResultList();*/
			q = null;
			q = entitymanager.createQuery("from ImagePath where student_ID=? and roll_status='Student'");
			q.setParameter(1, studid);
			List<ImagePath> imagelist = (List<ImagePath>) q.getResultList();
			logger.info("---imagelist size----" + imagelist.size());
			if (studlist1.size() > 0 && classlist.size() > 0) {
				logger.debug("if");
				StudentDetail studobj = entitymanager.find(StudentDetail.class, studlist1.get(0).getHas_student_ID());
				StudentClass studobj1 = entitymanager.find(StudentClass.class,
						classlist.get(0).getHasstudent_class_ID());
				/*studobj.setPostcode1(entitymanager.find(Postcode.class, postlist.get(0).getPostcode_ID()));
				studobj.setPostcode2(entitymanager.find(Postcode.class, postlist.get(0).getPostcode_ID()));*/
				studobj.setFirstName(studentDataBean.getStuFirstName());
				studobj.setLastName(studentDataBean.getStuLastName());
				studobj.setFatherName(studentDataBean.getStuFatherName());
				studobj.setMotherName(studentDataBean.getStuMotherName());
				studobj.setDob(studentDataBean.getStuDob());
				studobj.setAddress1_Street(studentDataBean.getStuPresentAddress());
				studobj.setAddress2_Street(studentDataBean.getStuPermanentAddress());				
				studobj.setFatherIncome(studentDataBean.getStuFatherIncome());
				studobj.setFatherOccupation(studentDataBean.getStuFatherOccu());
				studobj.setMotherOccupation(studentDataBean.getStuMotherOccu());
				studobj.setPresentCity(studentDataBean.getStuPresentCity());
				studobj.setPermanentCity(studentDataBean.getStuPermanentCity());
				studobj.setPresentCountry(studentDataBean.getStuPresentCountry());
				studobj.setPermanentCountry(studentDataBean.getStuPermanentCountry());
				studobj.setPresentState(studentDataBean.getStuPresentState());
				studobj.setPermanentState(studentDataBean.getStuPermanentState());
				studobj.setPresentPostal(studentDataBean.getStuPresentpostal());
				studobj.setPermanentPostal(studentDataBean.getStuPermanentpostal());
				studobj.setPhoneNumber1(studentDataBean.getStuPhoneNo1());
				studobj.setPhoneNumber(studentDataBean.getStuPhoneNo());
				studobj.setPresentcode(studentDataBean.getCode());
				studobj.setPermanentcode(studentDataBean.getCode1());
				if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6)
				{
				}else{
					studobj.setPhoneNumber(studentDataBean.getStuPhoneNo());
					studobj.setEmailId(studentDataBean.getStuEmail());					
				}
				studobj.setGender(studentDataBean.getStuGender());
				studobj.setReason(studentDataBean.getReason());
				logger.debug("reason -- " + studentDataBean.getReason());
				studobj1.setClazz(entitymanager.find(Class.class, classlist1.get(0).getClass_ID()));				
				/*studobj.setPostcode1(entitymanager.find(Postcode.class, postlist.get(0).getPostcode_ID()));
				studobj.setPostcode2(entitymanager.find(Postcode.class, postlist1.get(0).getPostcode_ID()));*/
				studobj.setStudentid(entitymanager.find(Student.class, studid));
				studobj.setReligion(studentDataBean.getReligion());
				studobj.setCaste(studentDataBean.getCaste());
				studobj.setClassification(studentDataBean.getClassification());
				studobj.setCommunityCertificateNo(studentDataBean.getCommunityCertificateNo());
				studobj.setIssuedAuthority(studentDataBean.getIssuedAuthority());
				studobj.setIssueddate(studentDataBean.getIssuedDate());
				studobj.setBankAccNo(studentDataBean.getBankAccNo());
				studobj.setBankName(studentDataBean.getBankName());
				studobj.setBranchName(studentDataBean.getBranchName());
				studobj.setBranchCode(studentDataBean.getBranchCode());
				studobj.setIfscCode(studentDataBean.getIfscCode());
				studobj.setMicrNo(studentDataBean.getMicrNo());
				studobj.setAadharCardNo(studentDataBean.getAadharCardNo());
				studobj.setRationCardNo(studentDataBean.getRationCardNo());
				studobj.setEmploymentCardNo(studentDataBean.getEmploymentCardNo());
				studobj.setEmisNo(studentDataBean.getEmisNo());
				studobj.setBusPassNo(studentDataBean.getBusPassNo());
				entitymanager.merge(studobj);
				entitymanager.merge(studobj1);
				if(imagelist.size() >0){
				ImagePath imageobj = entitymanager.find(ImagePath.class, imagelist.get(0).getImage_ID());
				imageobj.setPath(imagelist.get(0).getPath());
				imageobj.setDate(imagelist.get(0).getDate());
				imageobj.setName(imagelist.get(0).getName());
				imageobj.setStudent(entitymanager.find(Student.class, studid));
				entitymanager.merge(imageobj);
				}else{
					if(studentDataBean.getStufilePath() != null)
					{	StringTokenizer stoken = new StringTokenizer(studentDataBean.getStufilePath());
						Date d = dateFormat.parse(stoken.nextToken("/"));
						logger.debug("check hase photo");
						String tempPath = studentDataBean.getStufilePath();
						String path = tempPath.substring(tempPath.lastIndexOf("/") + 1);
						// Insert ImagePath table
			            ImagePath imagepath = new ImagePath();
						imagepath.setDate(d);
						imagepath.setPath(path);
						imagepath.setName(studentDataBean.getStuRollNo());
						imagepath.setRollStatus("Student");
						imagepath.setStudent(entitymanager.find(Student.class, studid));
						imagepath.setStatus("Active");
						entitymanager.persist(imagepath);
				}
				}
			}else {
				logger.debug("update else ");
				StudentDetail studobj = entitymanager.find(StudentDetail.class, studlist1.get(0).getHas_student_ID());				
				/*studobj.setPostcode1(entitymanager.find(Postcode.class, postlist.get(0).getPostcode_ID()));
				studobj.setPostcode2(entitymanager.find(Postcode.class, postlist.get(0).getPostcode_ID()));*/
				studobj.setFirstName(studentDataBean.getStuFirstName());
				studobj.setLastName(studentDataBean.getStuLastName());
				studobj.setFatherName(studentDataBean.getStuFatherName());
				studobj.setMotherName(studentDataBean.getStuMotherName());
				studobj.setDob(studentDataBean.getStuDob());
				studobj.setAddress1_Street(studentDataBean.getStuPresentAddress());
				studobj.setAddress2_Street(studentDataBean.getStuPermanentAddress());
				//studobj.setEmailId(studentDataBean.getStuEmail());
				studobj.setFatherIncome(studentDataBean.getStuFatherIncome());
				studobj.setFatherOccupation(studentDataBean.getStuFatherOccu());
				studobj.setMotherOccupation(studentDataBean.getStuMotherOccu());
				//studobj.setPhoneNumber(studentDataBean.getStuPhoneNo());
				studobj.setGender(studentDataBean.getStuGender());
				studobj.setReason(studentDataBean.getReason());
				studobj.setPresentCity(studentDataBean.getStuPresentCity());
				studobj.setPermanentCity(studentDataBean.getStuPermanentCity());
				studobj.setPresentCountry(studentDataBean.getStuPresentCountry());
				studobj.setPermanentCountry(studentDataBean.getStuPermanentCountry());
				studobj.setPresentState(studentDataBean.getStuPresentState());
				studobj.setPermanentState(studentDataBean.getStuPermanentState());
				studobj.setPresentPostal(studentDataBean.getStuPresentpostal());
				studobj.setPermanentPostal(studentDataBean.getStuPermanentpostal());
				studobj.setPhoneNumber1(studentDataBean.getStuPhoneNo1());
				studobj.setPhoneNumber(studentDataBean.getStuPhoneNo());
				studobj.setPresentcode(studentDataBean.getCode());
				studobj.setPermanentcode(studentDataBean.getCode1());
				logger.debug("reason -- " + studentDataBean.getReason());
				/*studobj.setPostcode1(entitymanager.find(Postcode.class, postlist.get(0).getPostcode_ID()));
				studobj.setPostcode2(entitymanager.find(Postcode.class, postlist1.get(0).getPostcode_ID()));*/
				studobj.setStudentid(entitymanager.find(Student.class, studid));
				studobj.setReligion(studentDataBean.getReligion());
				studobj.setCaste(studentDataBean.getCaste());
				studobj.setClassification(studentDataBean.getClassification());
				studobj.setCommunityCertificateNo(studentDataBean.getCommunityCertificateNo());
				studobj.setIssuedAuthority(studentDataBean.getIssuedAuthority());
				studobj.setIssueddate(studentDataBean.getIssuedDate());
				studobj.setBankAccNo(studentDataBean.getBankAccNo());
				studobj.setBankName(studentDataBean.getBankName());
				studobj.setBranchName(studentDataBean.getBranchName());
				studobj.setBranchCode(studentDataBean.getBranchCode());
				studobj.setIfscCode(studentDataBean.getIfscCode());
				studobj.setMicrNo(studentDataBean.getMicrNo());
				studobj.setAadharCardNo(studentDataBean.getAadharCardNo());
				studobj.setRationCardNo(studentDataBean.getRationCardNo());
				studobj.setEmploymentCardNo(studentDataBean.getEmploymentCardNo());
				studobj.setEmisNo(studentDataBean.getEmisNo());
				studobj.setBusPassNo(studentDataBean.getBusPassNo());
				entitymanager.merge(studobj);
				entitymanager.flush();
				entitymanager.clear();
				StudentClass studobj1 = new StudentClass();
				studobj1.setClazz(entitymanager.find(Class.class, classlist1.get(0).getClass_ID()));
				studobj1.setStudent(entitymanager.find(Student.class, studid));	
				studobj1.setStatus("Active");
				entitymanager.persist(studobj1);
				entitymanager.flush();
				entitymanager.clear();
				if(imagelist.size() >0){
				ImagePath imageobj = entitymanager.find(ImagePath.class, imagelist.get(0).getImage_ID());
				imageobj.setPath(imagelist.get(0).getPath());
				imageobj.setDate(imagelist.get(0).getDate());
				imageobj.setName(imagelist.get(0).getName());
				imageobj.setStudent(entitymanager.find(Student.class, studid));
				entitymanager.merge(imageobj);
				}else{
					if(studentDataBean.getStufilePath() != null)
					{	StringTokenizer stoken = new StringTokenizer(studentDataBean.getStufilePath());
						Date d = dateFormat.parse(stoken.nextToken("/"));
						logger.debug("check hase photo");
						String tempPath = studentDataBean.getStufilePath();
						String path = tempPath.substring(tempPath.lastIndexOf("/") + 1);
						// Insert ImagePath table
			            ImagePath imagepath = new ImagePath();
						imagepath.setDate(d);
						imagepath.setPath(path);
						imagepath.setName(studentDataBean.getStuRollNo());
						imagepath.setRollStatus("Student");
						imagepath.setStudent(entitymanager.find(Student.class, studid));
						imagepath.setStatus("Active");
						entitymanager.persist(imagepath);
				}
				}
			}
			status = "Success";
		}
		}
		}
		}logger.info("status ----------"+status);
		}
		catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn(" exception - "+e);
			logger.warn("Inside Exception",e);
			logger.error("Exception -->"+e.getMessage());
		}
		return status;
	}

	public String totalStudent(LoginAccess loginaccess) {
		int totalStudent = 0;
		totalStudent = getTotalStudent(loginaccess.getPersonID());
		loginaccess.setTotalStudent(String.valueOf(totalStudent));
		logger.debug("total student - " + totalStudent);
		return "";
	}

	@Transactional(value = "transactionManager")
	@Override
	@SuppressWarnings("unchecked")
	public String getStudentDelete(String personID, StudentDataBean studentDataBean) {
		String status = "Fail";
		Query q = null;
		Query v = null;
		List<Person> rollStatus = null;
		int school_ID = 0;
		int student_ID = 0;
		try {
			rollStatus = new ArrayList<Person>();
			if (personID != null && studentDataBean.getStuRollNo() != null) {
				rollStatus = getRollType(personID);
				logger.debug("Roll Size" + rollStatus.size());
				if (rollStatus.size() > 0) {
					if (rollStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
						school_ID = rollStatus.get(0).getSchool().getSchool_ID();
						//q = entitymanager.createQuery("from Student where roll_number=? and status='Active'");
						q = entitymanager.createQuery("from Student where school_ID=? and roll_number=? and status='Active'");
						q.setParameter(1, school_ID);
						q.setParameter(2, studentDataBean.getStuRollNo());
						//q.setParameter(1, studentDataBean.getStuRollNo());
						List<Student> studlist = (List<Student>) q.getResultList();
						if (studlist.size() > 0) {
							student_ID = studlist.get(0).getStudent_ID();
						}

						if (student_ID > 0) {
							Student student = entitymanager.find(Student.class, student_ID);
							student.setStatus("De-Active");
							entitymanager.merge(student);
							entitymanager.flush();
							entitymanager.clear();
							v = entitymanager.createQuery("from StudentClass where student_ID=? and status='Active'");
							v.setParameter(1, student_ID);
							List<StudentClass> studentClass = (List<StudentClass>) v.getResultList();
							if (studentClass.size() > 0) {
								StudentClass delete = entitymanager.find(StudentClass.class,
										studentClass.get(0).getHasstudent_class_ID());
								delete.setStatus("De-Active");
								entitymanager.merge(delete);
								entitymanager.flush();
								entitymanager.clear();
								status = "Success";
								v = null;
								v = entitymanager
										.createQuery("from StudentParent where student_ID=? and status='Active'");
								v.setParameter(1, student_ID);
								List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();
								if (studentParent.size() > 0) {
									StudentParent deletepare = entitymanager.find(StudentParent.class,
											studentParent.get(0).getHasstudent_parent_ID());
									deletepare.setStatus("De-Active");
									entitymanager.merge(deletepare);
									entitymanager.flush();
									entitymanager.clear();
									status = "Success";
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getParentRollNumber(String personID, LoginAccess loginAccess) {
		logger.debug("---------inside getroll--------");
		//String status = "Failed";
		List<Person> roll = null;
		String rollType = "Not Valid";
		//String rollnumber = "";
		String parRollNumber = "";
		Query q = null;
		//int perid = 0;
		int parentid = 0;
		int studid = 0;
		//int classid = 0;
		String clssection = "";
		try {
			roll = new ArrayList<Person>();
			roll = getRollType(personID);
			if (personID != null) {
				rollType = roll.get(0).getPersonRole();
				logger.debug("-------roll size----" + roll.size());
				if (roll.size() > 0) {
					rollType = roll.get(0).getPersonRole();
					if (rollType.equalsIgnoreCase("Parent")) {
						parRollNumber = roll.get(0).getPersonRoleNumber();
						logger.debug("--------roll number-----" + parRollNumber);
						q = entitymanager.createQuery("from Parent where roll_number=? and status='Active'");
						q.setParameter(1, parRollNumber);
						List<Parent> parentlist = (List<Parent>) q.getResultList();
						if (parentlist.size() > 0) {
							parentid = parentlist.get(0).getParent_ID();
							q = null;
							q = entitymanager.createQuery("from StudentParent where parent_ID=?");
							q.setParameter(1, parentid);
							List<StudentParent> studparentlist = (List<StudentParent>) q.getResultList();
							if (studparentlist.size() > 0) {
								studid = studparentlist.get(0).getStudent().getStudent_ID();
								q = null;
								q = entitymanager.createQuery("from StudentClass where student_ID=?");
								q.setParameter(1, studid);
								List<StudentClass> studclasslist = (List<StudentClass>) q.getResultList();
								if (studclasslist.size() > 0) {
									clssection = studclasslist.get(0).getClazz().getClassName() + "/"
											+ studclasslist.get(0).getClazz().getClassSection();
									loginAccess.setRollNumber(clssection);

								}
							}

						}
					}

				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.debug("---------outside getroll-----");
		return parRollNumber;

	}
	/* Prema OCT 25   getBookView*/
	@Override
	@SuppressWarnings("unchecked")
	public List<BookSaleDataBean> getBookView(String personID,
			BookSaleDataBean bookSaleDataBean) 
	{
		List<BookSaleDataBean> bookinfo = new ArrayList<BookSaleDataBean>();
		List<Person> roleStatus = null;Query q=null;
		String rollType = "NotValid";
		int salesid = 0;int studentid=0;String classname="";String section="";
		try
		{
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				if (roleStatus.size() > 0) 
				{
					rollType = roleStatus.get(0).getPersonRole();
					if(rollType.equalsIgnoreCase("Student")){
						studentid=getStudentId(roleStatus.get(0).getPersonRoleNumber());
						q = entitymanager.createQuery("from StudentClass where student_ID=? and status='Active'");
						q.setParameter(1,studentid);
						List<StudentClass> studentclasslist = (List<StudentClass>) q.getResultList();
						if(studentclasslist.size()>0){
							classname=studentclasslist.get(0).getClazz().getClassName();
							section=studentclasslist.get(0).getClazz().getClassSection();
						}
						bookSaleDataBean.setClassname(classname+"/"+section);
						bookSaleDataBean.setStudID(roleStatus.get(0).getPersonRoleNumber());
						q=null;
						q = entitymanager.createQuery("from BookSale where orderNumber=? and status='Active'");
						q.setParameter(1, bookSaleDataBean.getOrderNumber());
						List<BookSale> booklist1 = (List<BookSale>) q.getResultList();
						logger.debug("------list size--------" + booklist1.size());
						if (booklist1.size() > 0) 
						{
							salesid = booklist1.get(0).getSales_ID();
							bookSaleDataBean.setFilePath(booklist1.get(0).getPath_name());
							q=null;
							q = entitymanager.createQuery("from BookOrderRecord where sales_ID=?");
							q.setParameter(1, salesid);
							List<BookOrderRecord> rlist = (List<BookOrderRecord>) q.getResultList();
							logger.debug("rlist --"+rlist.size());
							if (rlist.size() > 0) 
							{
								int sno = 1;
								for (int i = 0; i < rlist.size(); i++) {
									BookSaleDataBean obj = new BookSaleDataBean();
									q=null;
									q = entitymanager.createQuery("from BookSalesRecord where bookName=? and status='Active'");
									q.setParameter(1, rlist.get(i).getBookName());
									List<BookSalesRecord> blist = (List<BookSalesRecord>) q.getResultList();
									if(blist.size() > 0)
									{
										obj.setBookQuantity(blist.get(0).getQuantity());
									}
									obj.setSerial(String.valueOf(sno));
									obj.setBookName(rlist.get(i).getBookName());
									obj.setBookPrice(rlist.get(i).getPrice());
									obj.setOrder(rlist.get(i).getQuantity());
									obj.setOldQuan(rlist.get(i).getQuantity());
									obj.setNetAmount(rlist.get(i).getNetAmount());
									bookinfo.add(obj);
									bookSaleDataBean.setBookinfolist(bookinfo);
									sno++;
								}
							}
							bookSaleDataBean.setTotal(booklist1.get(0).getTotalPrice());
							logger.debug("bookinfo -- "+bookinfo.size());
						}
					   }
						else if (rollType.equalsIgnoreCase("Admin")||rollType.equalsIgnoreCase("Parent")||
							rollType.equalsIgnoreCase("BookShop")) 
						{
						q=null;
						q = entitymanager.createQuery("from BookSale where orderNumber=? and status='Active'");
						q.setParameter(1, bookSaleDataBean.getOrderNumber());
						List<BookSale> booklist1 = (List<BookSale>) q.getResultList();
						logger.debug("------list size--------" + booklist1.size());
						if (booklist1.size() > 0) 
						{
							salesid = booklist1.get(0).getSales_ID();
							bookSaleDataBean.setFilePath(booklist1.get(0).getPath_name());
							q=null;
							q = entitymanager.createQuery("from BookOrderRecord where sales_ID=?");
							q.setParameter(1, salesid);
							List<BookOrderRecord> rlist = (List<BookOrderRecord>) q.getResultList();
							logger.debug("rlist --"+rlist.size());
							if (rlist.size() > 0) 
							{
								int sno = 1;
								for (int i = 0; i < rlist.size(); i++) {
									BookSaleDataBean obj = new BookSaleDataBean();
									q=null;
									q = entitymanager.createQuery("from BookSalesRecord where bookName=? and status='Active'");
									q.setParameter(1, rlist.get(i).getBookName());
									List<BookSalesRecord> blist = (List<BookSalesRecord>) q.getResultList();
									if(blist.size() > 0)
									{
										obj.setBookQuantity(blist.get(0).getQuantity());
									}
									obj.setSerial(String.valueOf(sno));
									obj.setBookName(rlist.get(i).getBookName());
									obj.setBookPrice(rlist.get(i).getPrice());
									obj.setOrder(rlist.get(i).getQuantity());
									obj.setOldQuan(rlist.get(i).getQuantity());
									obj.setNetAmount(rlist.get(i).getNetAmount());
									bookinfo.add(obj);
									bookSaleDataBean.setBookinfolist(bookinfo);
									sno++;
								}
							}
							bookSaleDataBean.setTotal(booklist1.get(0).getTotalPrice());
							logger.debug("bookinfo -- "+bookinfo.size());
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			logger.warn("Inside Exception",e);
		}
		return bookinfo;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String deletebooksale(String personID,
			BookSaleDataBean bookSaleDataBean) {
		String status = "Fail";
		List<Person> rollStatus = null;
		//int school_ID = 0;
		int sale_id=0;
		try {
			logger.debug("inside delete method --- " + bookSaleDataBean.getOrderNumber());
			rollStatus = new ArrayList<Person>();
			if (personID != null && bookSaleDataBean.getOrderNumber() != null) {
				rollStatus = getRollType(personID);
				if (rollStatus.size() > 0) {
					if (rollStatus.get(0).getPersonRole().equalsIgnoreCase("Parent")) 
					{
						Query q=null;
						q=entitymanager.createQuery("from BookSale where orderNumber=?");
						q.setParameter(1, bookSaleDataBean.getOrderNumber());
						List<BookSale> sale=(List<BookSale>)q.getResultList();
						sale_id=sale.get(0).getSales_ID();
						if (sale_id > 0) {
							BookSale booksale=entitymanager.find(BookSale.class, sale_id);
							booksale.setStatus("De-Active");
							entitymanager.merge(booksale);
							status = "Success";
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String bookOrderUpdate(String personID, BookSaleDataBean bookSaleDataBean) {

		logger.debug("-----------inside addBookOrder----------");
		logger.debug("------studid inside------" + bookSaleDataBean.getStudID());
		List<Person> rolestatus = null;
		String roletype = "not valid";
		int schlid = 0;
		int studid = 0;
		BigDecimal amount = new BigDecimal(0);
		BigDecimal quant = new BigDecimal(0);
		BigDecimal amt = new BigDecimal(0);
		String status = "Fail";
		int salesid = 0;
		int id = 0;
		int salesid1 = 0;
		int c=0;
		Query q = null;
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		Calendar now= Calendar.getInstance();
		String month=new SimpleDateFormat("MMM").format(now.getTime());
		try {
			rolestatus = new ArrayList<Person>();
			if (personID != null) {
				rolestatus = getRollType(personID);
				if (rolestatus.size() > 0) {
					roletype = rolestatus.get(0).getPersonRole();
					schlid = rolestatus.get(0).getSchool().getSchool_ID();
					String student=bookSaleDataBean.getStudID();
					String studentid=student.substring(student.lastIndexOf("/")+1);
					q = entitymanager.createQuery("from Student where roll_number=? and school_ID=? and status='Active'");
					q.setParameter(1, studentid);
					q.setParameter(2, schlid);
					List<Student> studlist = (List<Student>) q.getResultList();
					if (studlist.size() > 0) {

						studid = studlist.get(0).getStudent_ID();
						if (roletype.equalsIgnoreCase("Parent")) {
							logger.debug("------------inside first table-------"+bookSaleDataBean.getTotal());
							
							Query v=entitymanager.createQuery("from BookSale where student_ID=? and orderNumber=? and status='Active'");
							v.setParameter(1, studid);
							v.setParameter(2, bookSaleDataBean.getOrderNumber());
							List<BookSale> sales=(List<BookSale>)v.getResultList();
							if (sales.size() > 0) 
							{
							id=sales.get(0).getSales_ID();
							
							BookSale book = entitymanager.find(BookSale.class, id);
							book.setSchool(entitymanager.find(School.class, schlid));
							book.setStudent(entitymanager.find(Student.class, studid));
							if(schlid==2 || schlid==3 || schlid==4 || schlid==5|| schlid==6){
								book.setOrderDate(GetTimeZone.getDate("Asia/Makassar"));
								book.setOrderTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
							}else{
								book.setOrderDate(date);
								book.setOrderTime(timestamp);
							}
							book.setStatus("Active");
							book.setApprovalstatus("Inserted");
							book.setTotalPrice(bookSaleDataBean.getTotal());
							book.setOrderNumber(bookSaleDataBean.getOrderNumber());
							entitymanager.merge(book);
							logger.debug("------------first table inserted---------");
							logger.debug("--------studid-------" + studid);
							
							q=null;
							q=entitymanager.createQuery("from BookOrderRecord where sales_ID=?");
							q.setParameter(1, id);
							List<BookOrderRecord> orderRec=(List<BookOrderRecord>)q.getResultList();
							if(orderRec.size()>0){
								for (int i = 0; i < bookSaleDataBean.getBookinfolist().size(); i++) 
								{
									int orderRecID=orderRec.get(i).getOrder_record_ID();
										logger.debug("---------inside if--------");
										BookOrderRecord book1 = entitymanager.find(BookOrderRecord.class, orderRecID);
										book1.setBooksale(entitymanager.find(BookSale.class, id));
										book1.setBookName(bookSaleDataBean.getBookinfolist().get(i).getBookName());
										book1.setPrice(bookSaleDataBean.getBookinfolist().get(i).getBookPrice());
										book1.setQuantity(bookSaleDataBean.getBookinfolist().get(i).getOrder());
										book1.setNetAmount(bookSaleDataBean.getBookinfolist().get(i).getNetAmount());
										entitymanager.merge(book1);
										
										q = null;
										q = entitymanager.createQuery("from BookSalesRecord where book_name=?");
										q.setParameter(1, bookSaleDataBean.getBookinfolist().get(i).getBookName());
										List<BookSalesRecord> rlist = (List<BookSalesRecord>) q.getResultList();
										if (rlist.size() > 0) {
											logger.debug("old quan  ---- "+bookSaleDataBean.getBookinfolist().get(i).getOldQuan());
											logger.debug("available -- "+rlist.get(0).getQuantity());
											logger.debug("quant --- "+bookSaleDataBean.getBookinfolist().get(i).getOrder());
											salesid1 = rlist.get(0).getSales_record_ID();
											quant = new BigDecimal(rlist.get(0).getQuantity()).subtract(
													new BigDecimal(bookSaleDataBean.getBookinfolist().get(i).getOrder())).add(
													new BigDecimal(bookSaleDataBean.getBookinfolist().get(i).getOldQuan()));
											amt = quant.multiply(new BigDecimal(
													bookSaleDataBean.getBookinfolist().get(i).getBookPrice()));
											BookSalesRecord obj = entitymanager.find(BookSalesRecord.class, salesid1);
											logger.debug("---------remaining amount-------" + quant.toString());
											obj.setQuantity(quant.toString());
											obj.setNetAmount(amt.toString());
											entitymanager.merge(obj);

										}
										entitymanager.flush();
										entitymanager.clear();
										logger.debug("---------second table inserted---------");
									
								}
								status = "Success";
							}
						}
					}
				}
			}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String insertStaff2(String personID, StaffDataBean staffDataBean) {
		 List<Person> roleStatus = null;
		  String status = "Fail";
		  Query q = null;
		  //int newPerson_ID = 0;
		  Query q1 = null;
		  int staffID = 0;
		  int school_ID = 0;
		  //int studentID = 0;
		  String secPwd = "";
		  String EncPwd = "";
		  Query q2 = null;
		  int user_ID = 0;
		  int pid = 0;
		  Timestamp timestamp = new Timestamp(date.getTime());
		  Date date = new Date();
		  try {
		   roleStatus = new ArrayList<Person>();
		

		   if (personID != null) {
		    roleStatus = getRollType(personID);
		    logger.debug("Roll Size" + roleStatus.size());
		    if (roleStatus.size() > 0) {
		     if (roleStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
		      school_ID = roleStatus.get(0).getSchool().getSchool_ID();
		      staffDataBean.setSchoolID(school_ID);
		      q = null;
		      q = entitymanager.createQuery("from Person where person_role_number=?");
		      q.setParameter(1, staffDataBean.getStaStaffID());
		      List<Person> perlist3 = (List<Person>) q.getResultList();
		      if (perlist3.size() > 0) {
		       status = "Exsist";
		      } else {

		       // Insert Person Table
		       Person person = new Person();
		       if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
		    		person.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
		    		person.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
		    	}else{
		    		person.setCreatedDate(date);
		    		person.setCreatedTime(timestamp);
		    	}	
		       person.setPersonRoleNumber(staffDataBean.getStaStaffID());
		       person.setPersonRole("Staff");
		       person.setStatus("Active");
		       person.setSchool(entitymanager.find(School.class, school_ID));
		       entitymanager.persist(person);
		       entitymanager.flush();
		       entitymanager.clear();
		       q = null;
		       q = entitymanager.createQuery("from Person where person_role=? and person_role_number=?");
		       q.setParameter(1, "Staff");
		       q.setParameter(2, staffDataBean.getStaStaffID());
		       List<Person> perlist = (List<Person>) q.getResultList();
		      
		       if (perlist.size() > 0) {
		    	   pid = perlist.get(0).getPerson_ID();
			       logger.debug("---check PID----" + pid);
		        Staff staff = new Staff();
		        staff.setRollNumber(staffDataBean.getStaStaffID());
		        staff.setPerson(entitymanager.find(Person.class, pid));
		        staff.setStatus("Active");
		        staff.setSchool(entitymanager.find(School.class, school_ID));
		        staff.setFirstName(staffDataBean.getStaFirstName());
		        staff.setLastName(staffDataBean.getStaLastName());
		        staff.setEmailId(staffDataBean.getStaEmail());
		        staff.setPhoneNumber(staffDataBean.getStaPhoneNo());
		        staff.setGender(staffDataBean.getStaGender());
		        if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
		        	staff.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
		        	staff.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
		        }else{
		        	staff.setCreatedDate(date);
		        	staff.setCreatedTime(timestamp);
		        }
		        staff.setCountryCode(staffDataBean.getCountryCode());
		        entitymanager.persist(staff);
		        entitymanager.flush();
		        entitymanager.clear();

		        q1 = entitymanager.createQuery("from Staff where rollNumber=?");
		        q1.setParameter(1, staffDataBean.getStaStaffID());
		        List<Staff> res = (List<Staff>) q1.getResultList();
		        if (res.size() > 0) {
		         staffID = res.get(0).getStaff_ID();
		         if(staffDataBean.getStafilePath().equals("") || staffDataBean.getStafilePath()==null){
		         }else{
		         StringTokenizer stoken = new StringTokenizer(staffDataBean.getStafilePath());
		         Date d = dateFormat.parse(stoken.nextToken("/"));
		         String tempPath = staffDataBean.getStafilePath();
		         String path = tempPath.substring(tempPath.lastIndexOf("/") + 1);

		         // Insert ImagePath table
		         ImagePath imagepath = new ImagePath();
		         imagepath.setDate(d);
		         imagepath.setPath(path);
		         imagepath.setName(staffDataBean.getStaStaffID());
		         imagepath.setRollStatus("Staff");
		         imagepath.setStaff(entitymanager.find(Staff.class, staffID));
		         imagepath.setStatus("Active");
		         entitymanager.persist(imagepath);
		         entitymanager.flush();
		         entitymanager.clear();
		         }
		         secPwd = RandomPasswordGenerator
		           .GenerateSecurePassword(staffDataBean.getStaStaffID());// used
		                         // to
		                         // generate
		                         // secure
		                         // random
		                         // password
		         if (secPwd != null) {
		          staffDataBean.setStaSecurePasword(secPwd);
		          staffDataBean.setStaUsername(staffDataBean.getStaStaffID());
		          EncPwd = PasswordEncryption
		            .GeneratePaswword(staffDataBean.getStaSecurePasword());// used
		                          // to
		                          // generate
		                          // Encryption
		                          // Password
		          logger.debug("Encription " + EncPwd + "----" + secPwd);
		          if (EncPwd != null) {
		           logger.debug(
		             "----------------------------------Insert Login Staff 7 Begin-----------------------------------------------");
		           // Insert User Table
		           User user = new User();
		           user.setStatus("Active");
		           user.setUsername(staffDataBean.getStaUsername());
		           user.setPerson(entitymanager.find(Person.class, pid));
		           user.setPassword(EncPwd);
		           entitymanager.persist(user);
		           entitymanager.flush();
		           entitymanager.clear();
		           q2 = entitymanager.createQuery("from User where username=?");
		           q2.setParameter(1, staffDataBean.getStaUsername());
		           List<User> userList = (List<User>) q2.getResultList();
		           if (userList.size() > 0) {
		            user_ID = userList.get(0).getHas_user_ID();
		           logger.debug("user ID" + user_ID);

		            String menuStatus = insertStaffMenus1(user_ID);// call
		                        // insertStaffMenus
		                        // method
		                        // call
		            if (menuStatus.equalsIgnoreCase("Sucess")) {
		             status = "Success";
		            }
		           }
		         
		          }
		         }
		        }
		       }
		      }
		     }
		    }
		   }

		  } catch (Exception e) {
		   logger.warn("Exception -->"+e.getMessage());
		   logger.warn("Inside Exception",e);
		  }
		  return status;
	}

	private String insertStaffMenus1(int user_ID) {
		String res = "fail";
		int mainMenuID1 = 0;
	//	String res1 = "F";
		//String res2 = "F";
		try {
			// Insert UserProduct table
			if (user_ID > 0) {
				/*mainMenuID1 = getMainMenu("BOO400");
				if (mainMenuID1 > 0) {

					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res1 = "S";
				}*/
				mainMenuID1 = getMainMenu("NOT400");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res = "Sucess";
				}

				
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res;
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String bookpayemtapprove(String personID,
			BookSaleDataBean bookSaleDataBean) {
		Query q=null;
		String status="Fail";
		int salesid=0;
		int stuid=0;
		String paremailid="";
		try{
			if(personID!=null){
				q = entitymanager.createQuery("from BookSale where  orderNumber=? and status='Active'");
				q.setParameter(1, bookSaleDataBean.getOrderNumber());
				List<BookSale> booklist= (List<BookSale>) q.getResultList();	
				if(booklist.size()>0){
					salesid=booklist.get(0).getSales_ID();
					BookSale sale=entitymanager.find(BookSale.class, salesid);
					sale.setApprovalstatus("Approved");
					entitymanager.merge(sale);
					status="Success";
				stuid=booklist.get(0).getStudent().getStudent_ID();
				q=entitymanager.createQuery("from StudentParent where student_ID=? and status='Active'");
				q.setParameter(1, stuid);
				List<StudentParent> stuparlist=(List<StudentParent>)q.getResultList();
				if(stuparlist.size()>0){
					paremailid=stuparlist.get(0).getParent().getParentDetail().getEmaiId();
					bookSaleDataBean.setEmailId(paremailid);
					bookSaleDataBean.setPhoneNo(stuparlist.get(0).getParent().getParentDetail().getPhoneNumber());
				}
				}
			}
		}catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String bookpayemtreject(String personID,
			BookSaleDataBean bookSaleDataBean) {
		Query q=null;
		String status="Fail";
		int salesid=0;
		int stuid=0;
		String paremailid="";
		try{
			if(personID!=null){
				q = entitymanager.createQuery("from BookSale where  orderNumber=? and status='Active'");
				q.setParameter(1, bookSaleDataBean.getOrderNumber());
				List<BookSale> booklist= (List<BookSale>) q.getResultList();	
				if(booklist.size()>0){
					salesid=booklist.get(0).getSales_ID();
					BookSale sale=entitymanager.find(BookSale.class, salesid);
					sale.setApprovalstatus("Rejected");
					entitymanager.merge(sale);
					status="Success";
					stuid=booklist.get(0).getStudent().getStudent_ID();
					q=entitymanager.createQuery("from StudentParent where student_ID=? and status='Active'");
					q.setParameter(1, stuid);
					List<StudentParent> stuparlist=(List<StudentParent>)q.getResultList();
					if(stuparlist.size()>0){
						paremailid=stuparlist.get(0).getParent().getParentDetail().getEmaiId();
						bookSaleDataBean.setEmailId(paremailid);
						bookSaleDataBean.setPhoneNo(stuparlist.get(0).getParent().getParentDetail().getPhoneNumber());
					}
				}
			}
		}catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return status;
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String bookpaymentupload(String personID,BookSaleDataBean bookSaleDataBean) {
	
		//Date date = new Date();
		List<Person> rollStatus = null;
		String status="fail";
		int sale_id=0;
		try {
			if (personID != null) {
					logger.debug("inside delete method --- " + bookSaleDataBean.getOrderNumber());
					rollStatus = new ArrayList<Person>();
					if (personID != null && bookSaleDataBean.getOrderNumber() != null) {
						rollStatus = getRollType(personID);
						if (rollStatus.size() > 0) {
							if (rollStatus.get(0).getPersonRole().equalsIgnoreCase("Parent")) 
							{
								Query q=null;
								q=entitymanager.createQuery("from BookSale where orderNumber=?");
								q.setParameter(1, bookSaleDataBean.getOrderNumber());
								List<BookSale> sale=(List<BookSale>)q.getResultList();
								sale_id=sale.get(0).getSales_ID();
								String aprovalstatus=sale.get(0).getApprovalstatus();
								if (sale_id > 0 && (aprovalstatus.equalsIgnoreCase("Inserted") || aprovalstatus.equalsIgnoreCase("Rejected"))) {
									BookSale booksale=entitymanager.find(BookSale.class, sale_id);
									booksale.setApprovalstatus("Waiting");
									booksale.setPath_name(bookSaleDataBean.getBookfilePath());
									entitymanager.merge(booksale);
									bookSaleDataBean.setEmailId(rollStatus.get(0).getSchool().getEmailId());
									bookSaleDataBean.setPhoneNo(rollStatus.get(0).getSchool().getPhoneNumber());
									status="success";
									
							}
					}
				}
			}
			}
		}
	 catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		
		return status;
	}	
	
		@Override
		@SuppressWarnings("unchecked")
		@Transactional(value = "transactionManager")
		public String getRegisteredCustomer(RegisteredLogin registeredLogin) {
			String status="fail";
			String OTpass="";
			Query q=null;
		try{
				q=entitymanager.createQuery("from Loginotp where emailID= ?");
				q.setParameter(1, registeredLogin.getEmailid());				
				List<com.stumosys.shared.Loginotp> customerList=(List<com.stumosys.shared.Loginotp>)q.getResultList();
				logger.debug("size "+customerList.size());
				OTpass = RandomPasswordGenerator.GenerateSecureOTP();
				logger.debug("otp "+OTpass);
				if (OTpass != null) {
					com.stumosys.shared.Loginotp loginOtp = new com.stumosys.shared.Loginotp();
					loginOtp.setCountry(registeredLogin.getCountry());
					loginOtp.setEmailID(registeredLogin.getEmailid());
					loginOtp.setUserName(registeredLogin.getUsername());
					loginOtp.setPhoneNumber(registeredLogin.getPhonenumber());
					loginOtp.setOtpNumber(OTpass);
					loginOtp.setStatus("Active");
					entitymanager.persist(loginOtp);
					status="Success";
					registeredLogin.setOtpnumber(OTpass);
				}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
				logger.warn(" exception - "+e);
			}
			return status;
		}

		@Override
		@SuppressWarnings("unchecked")
		@Transactional(value = "transactionManager")
		public String getOtpCustomer(RegisteredLogin registeredLogin) {
			String status="fail";
			//String OTpass="";
			Query q=null;
			//String secPwd="";
			//String EncPwd="";
			try{
				
				q=entitymanager.createQuery("from Loginotp where userName=? and emailID=? and otpNumber=? ");
				q.setParameter(1, registeredLogin.getUsername());
				q.setParameter(2, registeredLogin.getEmailid());
				q.setParameter(3, registeredLogin.getOtpnumber());
				List<com.stumosys.shared.Loginotp> customerList=(List<com.stumosys.shared.Loginotp>)q.getResultList();
				logger.debug("size "+customerList.size());
				if(customerList.size()>0){
					status="Success";
				}
				else{
					status="Fail";
				}
			}
			catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
				logger.warn(" exception - "+e);
			}
			return status;			
		}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String getOtpResent(RegisteredLogin registeredLogin) {
		String status="fail";
		String OTpass="";
		Query q=null;
		try{
			
			q=entitymanager.createQuery("from Loginotp where emailID= ? and userName=?");
			q.setParameter(1, registeredLogin.getEmailid());
			q.setParameter(2, registeredLogin.getUsername());
			List<com.stumosys.shared.Loginotp> customerList=(List<com.stumosys.shared.Loginotp>)q.getResultList();
			if(customerList.size()>0){
				OTpass = RandomPasswordGenerator.GenerateSecureOTP();
				logger.debug("otp "+OTpass);
				int loginid=customerList.get(customerList.size()-1).getOtp_ID();
				Loginotp loginotp=entitymanager.find(Loginotp.class,loginid);
				loginotp.setOtpNumber(OTpass);
				entitymanager.merge(loginotp);
				registeredLogin.setOtpnumber(OTpass);
				status="Success";
				logger.debug("status"+status);
				}				
			}
	        catch (Exception e) {
	        	logger.warn("Exception -->"+e.getMessage());
	        	logger.warn(" exception - "+e);
	         }
			return status;
		}

	@Override
	@SuppressWarnings("unchecked")
	public List<ClassTimeTableDataBean> getstudentclassTimeTable(
			String personID, StudentDataBean studentDataBean) 
	{
		List<ClassTimeTableDataBean> classtimelist = new ArrayList<ClassTimeTableDataBean>();
		Query v = null;
		List<Person> roll = null;
		Calendar now = Calendar.getInstance();
		String dates = "";
		String exdate = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int year = now.get(Calendar.YEAR);
		List<String> periods = new ArrayList<String>();
		List<String> days = new ArrayList<String>();
		List<StudentClass> studentClass = null;
		List<String> subjects = null;
		List<String> classes = null;
		int size=0;
		int count = 0;
		int count1 = 0;List<String> times = null;
		roll = getRollType(personID);
		int schoolid = roll.get(0).getSchool().getSchool_ID();
		try{
			if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				days.add("Sunday");
			}
			days.add("Monday");
			days.add("Tuesday");
			days.add("Wednesday");
			days.add("Thursday");
			days.add("Friday");
			days.add("Saturday");
			v = entitymanager
					.createQuery("from Student where school_ID=? and rollNumber=? and status=?");
			v.setParameter(1, schoolid);
			v.setParameter(2, studentDataBean.getStuStudentId());
			v.setParameter(3, "Active");
			List<Student> student = (List<Student>) v.getResultList();
			if (student.size() > 0) {
				v = entitymanager.createQuery("from StudentClass where student_ID=? and status=?");
				v.setParameter(1, student.get(0).getStudent_ID());
				v.setParameter(2, "Active");
				studentClass = (List<StudentClass>) v.getResultList();
				logger.debug("studentClass "+studentClass.size());
				size++;
			}
			int cc = 1;
			for (int c = 0; c < 10; c++) {
				periods.add("" + cc);
				cc++;
			}
			if (size > 0) {

				if (periods.size() > 0) {
					int no = 1;
					classtimelist = new ArrayList<ClassTimeTableDataBean>();
					for (int j = 0; j < days.size(); j++) {
						subjects = new ArrayList<String>();times = new ArrayList<String>();
						for (int i = 0; i < periods.size(); i++) {
							String subject = "-";String time="";
							v = null;
							v = entitymanager.createQuery(
									"from ClassTimeTable where period=? and school_ID=? and day=?");
							v.setParameter(1, periods.get(i));
							v.setParameter(2, schoolid);
							v.setParameter(3, days.get(j));
							List<ClassTimeTable> classtime = (List<ClassTimeTable>) v.getResultList();
							logger.debug("classtime "+classtime.size());
							if (classtime.size() > 0) {
								if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
									if (classtime.get(0).getClassTable().getClassz()
											.getClass_ID() == studentClass.get(0).getClazz()
													.getClass_ID()) {
										subject = classtime.get(0).getSubject();
										time = classtime.get(0).getStartTime()+" - "+classtime.get(0).getEndTime();
									} else {
										subject = "-";
										time="";
									}
								}else{
									if (periods.get(i).equalsIgnoreCase("9")
											|| periods.get(i).equalsIgnoreCase("10")) {
										if (periods.get(i).equalsIgnoreCase("9")) {
											try {
												if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
													dates = format.format(GetTimeZone.getDate("Asia/Makassar"));
												}else{
													dates = format.format(date);
												}
												exdate = "" + classtime.get(0).getDate();
												if (exdate.equals(dates)) {
													if (classtime.get(0).getClassTable().getClassz()
															.getClass_ID() == studentClass.get(0).getClazz()
																	.getClass_ID()) {
														subject = classtime.get(0).getSubject();
														count++;
													} else {
														subject = "-";
														time="";
													}
												}
											} catch (Exception e) {
												subject = "-";
												time="";
											}
										}
										if (periods.get(i).equalsIgnoreCase("10")) {
											try {
												if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
													dates = format.format(GetTimeZone.getDate("Asia/Makassar"));
												}else{
													dates = format.format(date);
												}
												exdate = "" + classtime.get(0).getDate();
												if (exdate.equals(dates)) {
													if (classtime.get(0).getClassTable().getClassz()
															.getClass_ID() == studentClass.get(0).getClazz()
																	.getClass_ID()) {
														subject = classtime.get(0).getSubject();
														count1++;
													} else {
														subject = "-";
														time="";
													}
												}
											} catch (Exception e) {
												subject = "-";
												time="";
											}
										}
									} else {
										if (classtime.get(0).getClassTable().getClassz()
												.getClass_ID() == studentClass.get(0).getClazz()
														.getClass_ID()) {
											subject = classtime.get(0).getSubject();
										} else {
											subject = "-";
											time="";
										}
									}
								}
							}else {
								subject = "-";
								time="";
							}
							subjects.add(subject);
							times.add(time);
						}
						ClassTimeTableDataBean tablelist = new ClassTimeTableDataBean();
						if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
							if (days.equals("Sunday"))
								tablelist.setDay("Sun");
						}
						if (days.get(j).equals("Monday"))
							tablelist.setDay("Mon");
						else if (days.get(j).equals("Tuesday"))
							tablelist.setDay("Tue");
						else if (days.get(j).equals("Wednesday"))
							tablelist.setDay("Wed");
						else if (days.get(j).equals("Thursday"))
							tablelist.setDay("Thu");
						else if (days.get(j).equals("Friday"))
							tablelist.setDay("Fri");
						else if (days.get(j).equals("Saturday"))
							tablelist.setDay("Sat");
						tablelist.setSubject(subjects.get(0));
						tablelist.setSubject1(subjects.get(1));
						tablelist.setSubject2(subjects.get(2));
						tablelist.setSubject3(subjects.get(3));
						tablelist.setSubject4(subjects.get(4));
						tablelist.setSubject5(subjects.get(5));
						tablelist.setSubject6(subjects.get(6));
						tablelist.setSubject7(subjects.get(7));
						tablelist.setTeaID(times.get(0));tablelist.setTeaID1(times.get(1));tablelist.setTeaID2(times.get(2));tablelist.setTeaID3(times.get(3));
						tablelist.setTeaID4(times.get(4));tablelist.setTeaID5(times.get(5));tablelist.setTeaID6(times.get(6));tablelist.setTeaID7(times.get(7));
						if (count > 0) {
							tablelist.setExSubject(subjects.get(8));
							tablelist.setTeaID8(times.get(8));
						}
						if (count1 > 0) {
							tablelist.setExSubject1(subjects.get(9));
							tablelist.setTeaID9(times.get(9));
						}
						classtimelist.add(tablelist);
						logger.debug("list sizeee "+classtimelist.size());
					}
				}
			
			}
			
		}
		catch(Exception e)
		{
			logger.warn(" exception - "+e);
		}
		return classtimelist;
	} 
	
	//john clinton Attendance method
	
	@Override
	@SuppressWarnings("unchecked")
	public List<String> getTeaClasslist(String rollNumber) {
		logger.debug("------------inside getTeaClass------");
		logger.debug("---------roll number-------" + rollNumber);
		Query q = null;
		int teaid = 0;
		int clsid = 0;
		int temp=0;
		List<Person> roll=null;
		List<String> classes = new ArrayList<String>();
		String classname = "";
		try {
			roll = new ArrayList<Person>();
			roll=getRollType(rollNumber);
			if (roll.size() > temp) {
				int schoolid=roll.get(temp).getSchool().getSchool_ID();
				if (roll.get(temp).getPersonRole().equals("Admin") || roll.get(temp).getPersonRole().equals("Teacher")) {
				q = entitymanager.createQuery("from Class where status='Active' and school_ID=?");
				q.setParameter(1, schoolid);
				List<Class> classs = (List<Class>) q.getResultList();
				if (classs.size() > temp) {
					for (int i = temp; i < classs.size(); i++) {
						classname = classs.get(i).getClassName() + "/" + classs.get(i).getClassSection();
						classes.add(classname);
					}
				}
			} /*else if (roll.get(temp).getPersonRole().equals("Teacher")) {
				q = entitymanager.createQuery("from Teacher where roll_number=? and status='Active' and school_ID=?");
				q.setParameter(1, roll.get(temp).getPersonRoleNumber());
				q.setParameter(2, schoolid);
				List<Teacher> tealist = (List<Teacher>) q.getResultList();
				ArrayList<StudentDataBean> teaclass1 = new ArrayList<StudentDataBean>();
				if (tealist.size() > temp) {
					teaid = tealist.get(temp).getTeacher_ID();
					q = null;
					q = entitymanager.createQuery("from TeacherClass where teacher_ID=? and status='Active'");
					q.setParameter(1, teaid);
					List<TeacherClass> teaclslist = (List<TeacherClass>) q.getResultList();
					if (teaclslist.size() > temp) {
						for (int i = temp; i < teaclslist.size(); i++) {
							classname = teaclslist.get(i).getClazz().getClassName() + "/"
									+ teaclslist.get(i).getClazz().getClassSection();
							StudentDataBean obj = new StudentDataBean();
							obj.setTeaclssection(classname);
							teaclass1.add(obj);
							classes.add(classname);
						
						}
					}
				}
			}*/
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		return classes;
	}
// john clinton 09-26-2017
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public String takeattendanceNew(String personID,AttendanceDataBean attendanceDataBean) {
		logger.debug("inside take attendance student list dao ");
		SimpleDateFormat formatNowYear = new SimpleDateFormat("yyyy");
		Query v = null;
		List<Person> person = null;
		String days = "";
		String st = "";
		String et = "";
		String stime = "";
		String classname="";
		String section="";
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		List<AttendanceDataBean> attendancedata = new ArrayList<AttendanceDataBean>();
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					int schoolid = person.get(0).getSchool().getSchool_ID();
					String day = "";
						day = String.valueOf(attendanceDataBean.getDate().getDay());
					System.out.println("day "+day);
					if (day.equals("0"))
						days = "Sunday";
					else if (day.equals("1"))
						days = "Monday";
					else if (day.equals("2"))
						days = "Tuesday";
					else if (day.equals("3"))
						days = "Wednesday";
					else if (day.equals("4"))
						days = "Thursday";
					else if (day.equals("5"))
						days = "Friday";
					else if (day.equals("6"))
						days = "Saturday";
					attendanceDataBean.setMonthyear("" + (now.get(Calendar.MONTH) + 1));
					attendanceDataBean.setYear("" + now.get(Calendar.YEAR));
					
					v = entitymanager.createQuery("from Teacher where school_ID=? and person_ID=? and status=?");
					v.setParameter(1, schoolid);
					v.setParameter(2, personID);
					v.setParameter(3, "Active");
					List<Teacher> teacher = (List<Teacher>) v.getResultList();
					System.out.println("teacher size "+teacher.size()+"teacher ID--->"+teacher.get(0).getRollNumber());
					
					v = entitymanager.createQuery("from Class where class_name=? and class_section=? and status=? and school_ID=?");
					v.setParameter(1, attendanceDataBean.getClassname().split("/")[0]);
					v.setParameter(2, attendanceDataBean.getClassname().split("/")[1]);
					v.setParameter(3, "Active");
					v.setParameter(4, schoolid);
					List<Class> classSecId = (List<Class>) v.getResultList();
					System.out.println("classSec size "+classSecId.size()+"------->"+classSecId.get(0).getClass_ID());
					
					/*v = entitymanager.createQuery("from ClassTable where class_name=? and class_section=? and status=?");
					v.setParameter(1, attendanceDataBean.getClassname().split("/")[0]);
					v.setParameter(2, attendanceDataBean.getClassname().split("/")[1]);
					v.setParameter(3, "Active");
					List<ClassTable> classtable1 = (List<ClassTable>) v.getResultList();
					System.out.println("teacher size "+teacher.size());
					*/
					if (teacher.size() > 0) {
						v = entitymanager.createQuery("from StudentClass where class_ID=? and status=?");
						v.setParameter(1, classSecId.get(0).getClass_ID());
						v.setParameter(2, "Active");
						List<StudentClass> stuclass = (List<StudentClass>) v.getResultList();
						System.out.println("stuclass size----->"+stuclass.size());
						if (stuclass.size()>0) {
							for (int i = 0; i < stuclass.size(); i++) {
								
								v = null;
								v = entitymanager.createQuery("from StudentDetail where student_ID=?");
								v.setParameter(1, stuclass.get(i).getStudent().getStudent_ID());
								List<StudentDetail> studentdetail = (List<StudentDetail>) v.getResultList();
								System.out.println("student detail size"+studentdetail.size()+"--period value-->"+attendanceDataBean.getPeriod());
								
								AttendanceDataBean attendancelist = new AttendanceDataBean();
								attendancelist.setStudentName(studentdetail.get(0).getFirstName()+studentdetail.get(0).getLastName());
								attendancelist.setStudentID(stuclass.get(i).getStudent().getRollNumber());
								attendancelist.setDate(attendanceDataBean.getDate());
								v = null;
								v = entitymanager.createQuery("from Attendance where school_ID=? and date=? and class_=? and section=?");
								v.setParameter(1, schoolid);
								v.setParameter(2, attendanceDataBean.getDate());
								v.setParameter(3, classSecId.get(0).getClassName());
								v.setParameter(4, classSecId.get(0).getClassSection());
								List<Attendance> attendance = (List<Attendance>) v.getResultList();
								if (attendance.size()>0) {
									v = null;
									v = entitymanager.createQuery("from Attendanceclass where attendance_ID=? and studentName=? and student_ID=?  and period=?");
									v.setParameter(1, attendance.get(0).getAttendance_ID());
									v.setParameter(2, studentdetail.get(0).getFirstName()+studentdetail.get(0).getLastName());
									v.setParameter(3, stuclass.get(i).getStudent().getRollNumber());
									v.setParameter(4, attendanceDataBean.getPeriod());
									List<Attendanceclass> attendanceclass = (List<Attendanceclass>) v.getResultList();
									System.out.println("attendance class----->"+attendanceclass.size());
									if (attendanceclass.size()>0) {
										attendancelist.setStatus(attendanceclass.get(0).getStatus());
										attendancelist.setStatus1(attendanceclass.get(0).getStatus());
									}
									else{
										attendancelist.setStatus("Not Yet Taken for this period");
										attendancelist.setStatus1("Present");
									}
								}
								else{
									attendancelist.setStatus("Not Yet Taken");
									attendancelist.setStatus1("Present");
								}	
								attendancedata.add(attendancelist);
								attendanceDataBean.setStudentList(attendancedata);	
							}
							
						}
					}
					System.out.println("student size -- " + attendanceDataBean.getStudentList());
					}
				}
			
		} catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
			logger.warn("Exception", e);
			
		}
		return null;		
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public String insertAttandance(String personID, String schoolID,
			AttendanceDataBean attendanceDataBean) {
		logger.info("-------------- inside take attendance student list dao-----------------------");
		//SimpleDateFormat formatNowYear = new SimpleDateFormat("yyyy");
		String status="fail";
		Query v = null;
		List<Person> person = null;
		String days = "";
		String classname="";
		String section="";
		Calendar now = Calendar.getInstance();
		//int year = now.get(Calendar.YEAR);
		//List<AttendanceDataBean> attendancedata = new ArrayList<AttendanceDataBean>();
		try {
			if (personID != null) {
				person = getRollType(personID);
				logger.info("--------- PersonID ---------------"+personID);
				if (person.size() > 0) {
					int schoolid = person.get(0).getSchool().getSchool_ID();
					logger.info("--------- SchoolID ---------------"+schoolid);
					String day = "";
						day = String.valueOf(attendanceDataBean.getDate().getDay());
					logger.info("day "+day);
					if (day.equals("0"))
						days = "Sunday";
					else if (day.equals("1"))
						days = "Monday";
					else if (day.equals("2"))
						days = "Tuesday";
					else if (day.equals("3"))
						days = "Wednesday";
					else if (day.equals("4"))
						days = "Thursday";
					else if (day.equals("5"))
						days = "Friday";
					else if (day.equals("6"))
						days = "Saturday";
					attendanceDataBean.setMonthyear("" + (now.get(Calendar.MONTH) + 1));
					attendanceDataBean.setYear("" + now.get(Calendar.YEAR));
					logger.info("--------- Monthyear ---------------"+attendanceDataBean.getMonthyear());
					logger.info("--------- Year ---------------"+attendanceDataBean.getYear());
					v = entitymanager.createQuery("from Teacher where school_ID=? and person_ID=? and status=?");
					v.setParameter(1, schoolid);
					v.setParameter(2, personID);
					v.setParameter(3, "Active");
					List<Teacher> teacher = (List<Teacher>) v.getResultList();
					logger.info("teacher size --------- "+teacher.size()+"----------- teacher ID--->"+teacher.get(0).getRollNumber());
					logger.info("---getClassname----->"+attendanceDataBean.getClassname());
					v = entitymanager.createQuery("from Class where class_name=? and class_section=? and school_ID=? and status=?");
					v.setParameter(1, attendanceDataBean.getClassname().split("/")[0]);
					v.setParameter(2, attendanceDataBean.getClassname().split("/")[1]);
					v.setParameter(3, schoolid);
					v.setParameter(4, "Active");

					List<Class> classSecId = (List<Class>) v.getResultList();
					logger.info("--------- class Name -----"+attendanceDataBean.getClassname().split("/")[0]);
					logger.info("--------- class Section -----"+attendanceDataBean.getClassname().split("/")[1]);
					logger.info("classSec size -------"+classSecId.size()+"--- classID ---->"+classSecId.get(0).getClass_ID());
					
					v = entitymanager.createQuery("from Class where class_name=? and class_section=? and status=?");
					v.setParameter(1, attendanceDataBean.getClassname().split("/")[0]);
					v.setParameter(2, attendanceDataBean.getClassname().split("/")[1]);
					v.setParameter(3, "Active");
					List<Student> studentid = (List<Student>) v.getResultList();
					int studentID=getStudentId(attendanceDataBean.getStudentID());
					logger.info("--- Student ID----->"+studentID);
					if (teacher.size() > 0) {
						v = entitymanager.createQuery("from StudentClass where class_ID=? and student_ID=? and status=?");
						v.setParameter(1, classSecId.get(0).getClass_ID());
						v.setParameter(2, studentID);
						v.setParameter(3, "Active");
						List<StudentClass> stuclass = (List<StudentClass>) v.getResultList();
						System.out.println("stuclass size----->"+stuclass.size());
						if (stuclass.size()>0) {
							for (int i = 0; i < stuclass.size(); i++) {
								
								v = null;
								v = entitymanager.createQuery("from StudentDetail where student_ID=?");
								v.setParameter(1, stuclass.get(i).getStudent().getStudent_ID());
								List<StudentDetail> studentdetail = (List<StudentDetail>) v.getResultList();
								logger.info("student detail size"+studentdetail.size()+"--period value-->"+attendanceDataBean.getPeriod());
						
								v = null;
								v = entitymanager.createQuery("from Attendance where school_ID=? and date=? and class_=? and section=?");
								v.setParameter(1, schoolid);
								v.setParameter(2, attendanceDataBean.getDate());
								v.setParameter(3, classSecId.get(0).getClassName());
								v.setParameter(4, classSecId.get(0).getClassSection());
								List<Attendance> attendance = (List<Attendance>) v.getResultList();
								System.out.println("======attendance========="+attendance.size());
								if (attendance.size()>0) {
									v = null;
									v = entitymanager.createQuery("from Attendanceclass where attendance_ID=? and studentName=? and student_ID=?  and period=?");
									v.setParameter(1, attendance.get(0).getAttendance_ID());
									v.setParameter(2, studentdetail.get(0).getFirstName()+studentdetail.get(0).getLastName());
									v.setParameter(3, stuclass.get(i).getStudent().getRollNumber());
									v.setParameter(4, attendanceDataBean.getPeriod());
									List<Attendanceclass> attendanceclass = (List<Attendanceclass>) v.getResultList();
									System.out.println("attendance class----->"+attendanceclass.size());
									if (attendanceclass.size()>0) {
										Attendanceclass insert1 = entitymanager.find(Attendanceclass.class, attendanceclass.get(0).getAtt_class_ID());
										insert1.setStatus(attendanceDataBean.getStatus());
										entitymanager.merge(insert1);
										status="Success";
									
									}
									else{
										Attendanceclass insert1 = new Attendanceclass();
										insert1.setAttendance(entitymanager.find(Attendance.class,attendance.get(0).getAttendance_ID()));
										insert1.setStudentName(studentdetail.get(0).getFirstName()+studentdetail.get(0).getLastName());
										insert1.setStudent_ID(studentdetail.get(0).getStudentid().getRollNumber());
										insert1.setStatus(attendanceDataBean.getStatus());
										insert1.setTeacher(entitymanager.find(Teacher.class, teacher.get(0).getTeacher_ID()));
										insert1.setPeriod(attendanceDataBean.getPeriod());
										if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
											logger.info("Indonesian time--->"+GetTimeZone.getIndonesiaTimeHour("Asia/Makassar") );
											insert1.setTime(Timestamp.valueOf(GetTimeZone.getIndonesiaTimeHour("Asia/Makassar")));
										}else if(schoolid==35 || schoolid==36 || schoolid==37 || schoolid==38|| schoolid==39 || schoolid==40 || schoolid==41 || schoolid==42 || schoolid==43){	
											insert1.setTime(timestamp);
											logger.info("Nagercoil time--->"+timestamp);
										}else{	
											logger.info("Indonesian time--->"+GetTimeZone.getIndonesiaTimeHour("Asia/Makassar") );
											insert1.setTime(Timestamp.valueOf(GetTimeZone.getIndonesiaTimeHour("Asia/Makassar")));
										}
										insert1.setMonth(attendanceDataBean.getMonthyear());
										insert1.setYear(attendanceDataBean.getYear());
										entitymanager.persist(insert1);
										entitymanager.flush();
										entitymanager.clear();
										status="Success";
									}
								}
								else{
									logger.info("Attendance======inside else====>");
									logger.info("schoolid====>"+schoolid+"==classname======="+classname+"====section========"+section
											+"====attendanceDataBean.getDate()====="+attendanceDataBean.getDate());
									// Parent table
									Attendance insert = new Attendance();
									// Child table
								
									insert.setSchool(entitymanager.find(School.class, schoolid));
									insert.setClass_(classSecId.get(0).getClassName());
									insert.setSection(classSecId.get(0).getClassSection());
									insert.setDate(attendanceDataBean.getDate());
									logger.info("-----After insert attendance table ----->");
									Attendanceclass insert1 = new Attendanceclass();
									insert1.setStudentName(studentdetail.get(0).getFirstName()+studentdetail.get(0).getLastName());
									insert1.setStudent_ID(studentdetail.get(0).getStudentid().getRollNumber());
									insert1.setStatus(attendanceDataBean.getStatus());
									insert1.setTeacher(entitymanager.find(Teacher.class, teacher.get(0).getTeacher_ID()));
									insert1.setPeriod(attendanceDataBean.getPeriod());
									if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
										logger.info("Indonesian time--->"+GetTimeZone.getIndonesiaTimeHour("Asia/Makassar") );
										insert1.setTime(Timestamp.valueOf(GetTimeZone.getIndonesiaTimeHour("Asia/Makassar")));
									}else if(schoolid==35 || schoolid==36 || schoolid==37 || schoolid==38|| schoolid==39 || schoolid==40 || schoolid==41 || schoolid==42 || schoolid==43){	
										insert1.setTime(timestamp);
										logger.info("Nagercoil time--->"+timestamp);
									}else{	
										logger.info("Indonesian time--->"+GetTimeZone.getIndonesiaTimeHour("Asia/Makassar") );
										insert1.setTime(Timestamp.valueOf(GetTimeZone.getIndonesiaTimeHour("Asia/Makassar")));
									}
									//insert1.setTime(timestamp);
									
									insert1.setMonth(attendanceDataBean.getMonthyear());
									insert1.setYear(attendanceDataBean.getYear());
									insert1.setAttendance(insert);
									// adding child to parent
									//insert.addAttendanceclass(insert1);
									logger.info("-----After insert attendanceclass table into an attendance table----->");
									// Save parent and child
									entitymanager.persist(insert);
									entitymanager.flush();
									entitymanager.clear();
									status="Success";
									logger.info("----- Attendance 1 Status ----->"+status);
								}	
								/*attendancedata.add(attendancelist);
								attendanceDataBean.setStudentList(attendancedata);	*/
							}
							
						}
					}
					logger.info("student size -- " + attendanceDataBean.getStudentList().size());
					}
				}
			
		} catch (Exception e) {
			//logger.error("Exception -->"+e.getMessage());
			logger.warn("Exception"+ e.getMessage());
			//e.printStackTrace();
		}
		return status;		
	}
	
	/* Ragulan OCT 25   takeattendance*/
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public String takeattendance(String personID,AttendanceDataBean attendanceDataBean) {
		logger.debug("inside take attendance student list dao ");
		Query v = null;
		List<Person> person = null;
		String days = "";
		String st = "";
		String et = "";
		String stime = "";
		String classname="";
		String section="";
		Calendar now = Calendar.getInstance();
		//int year = now.get(Calendar.YEAR);
		List<AttendanceDataBean> attendancedata = new ArrayList<AttendanceDataBean>();
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					int schoolid = person.get(0).getSchool().getSchool_ID();
					String day = "";
					if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						day = String.valueOf(GetTimeZone.getDate("Asia/Makassar").getDay());
					}else{
						day = String.valueOf(date.getDay());
					}
					
					logger.debug("day "+day);
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						if (day.equals("0"))
							days = "Sunday";
					}if (day.equals("1"))
						days = "Monday";
					else if (day.equals("2"))
						days = "Tuesday";
					else if (day.equals("3"))
						days = "Wednesday";
					else if (day.equals("4"))
						days = "Thursday";
					else if (day.equals("5"))
						days = "Friday";
					else if (day.equals("6"))
						days = "Saturday";
					attendanceDataBean.setMonthyear("" + (now.get(Calendar.MONTH) + 1));
					attendanceDataBean.setYear("" + now.get(Calendar.YEAR));
					if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						st= GetTimeZone.getTimeHour("Asia/Makassar");
						et=GetTimeZone.getTimeMin("Asia/Makassar");	
					}else{
						st = String.valueOf(now.getTime().getHours());
						et = String.valueOf(now.getTime().getMinutes());			
					}
					logger.debug("[Before] start time -- "+st+" end time -- "+et+" date "+date);
					if (st.length() == 1 && et.length() == 1) {
						stime = "0" + st + ":" + "0" + et;
					} else if (st.length() == 1 && et.length() == 2) {
						stime = "0" + st + ":" + et;
					} else if (st.length() == 2 && et.length() == 1) {
						stime = st + ":" + "0" + et;
					} else if (st.length() == 2 && et.length() == 2) {
						stime = st + ":" + et;
					}
					logger.debug("[After] start time -- "+st+" end time -- "+et+" date "+date);
					/*org.joda.time.DateTime now1 = new org.joda.time.DateTime();
					 DateTime dates=now1.toDateTime( org.joda.time.DateTimeZone.UTC );
					 logger.debug("hour "+dates.getHourOfDay()+" min -- "+dates.getMinuteOfHour());*/
					logger.debug("current time -- "+stime);
					v = entitymanager.createQuery("from Teacher where school_ID=? and person_ID=? and status=?");
					v.setParameter(1, schoolid);
					v.setParameter(2, personID);
					v.setParameter(3, "Active");
					List<Teacher> teacher = (List<Teacher>) v.getResultList();
					logger.debug("teacher size "+teacher.size());
					if (teacher.size() > 0) {
							v = null;
							//if (schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6) {
								v = entitymanager.createQuery("from ClassTimeTable where teacher_ID=? and day=? and ? between startTime and endTime");
								v.setParameter(1, teacher.get(0).getTeacher_ID());
								v.setParameter(2, days);
								v.setParameter(3, stime);
							//}
							/*else{
								v = entitymanager.createQuery("from ClassTimeTable where teacher_ID=? and day=?");
								v.setParameter(1, teacher.get(0).getTeacher_ID());
								v.setParameter(2, days);
							}*/
						
							List<ClassTimeTable> classTimeTable = (List<ClassTimeTable>) v.getResultList();
							logger.debug("class time table size "+classTimeTable.size());
							if (classTimeTable.size() > 0){
								for (int k = 0; k < classTimeTable.size(); k++) {
									v = null;
									v = entitymanager.createQuery("from ClassTable where class_table_ID=? and status='Active'");
									v.setParameter(1, classTimeTable.get(k).getClassTable().getClass_table_ID());
									List<ClassTable> classtable = (List<ClassTable>) v.getResultList();
									if(classtable.size() > 0){
										classname=classTimeTable.get(k).getClassTable().getClassz().getClassName();
										section=classTimeTable.get(k).getClassTable().getClassz().getClassSection();
										int class_id=classTimeTable.get(k).getClassTable().getClassz().getClass_ID();	
										attendanceDataBean.setClassname(classname+"/"+section);
										v = null;
										v = entitymanager.createQuery("from StudentClass where class_ID=? and status=?");
										v.setParameter(1, class_id);
										v.setParameter(2, "Active");
										List<StudentClass> studentclass = (List<StudentClass>) v.getResultList();
										if (studentclass.size() > 0) {
											int i1 = 1;
											for (int i = 0; i < studentclass.size(); i++) {
												v = null;
												v = entitymanager.createQuery("from StudentDetail where student_ID=?");
												v.setParameter(1, studentclass.get(i).getStudent().getStudent_ID());
												List<StudentDetail> studentdetail = (List<StudentDetail>) v.getResultList();
												logger.debug("student detail size"+studentdetail.size());
												if (studentdetail.size() > 0) {
													if(studentclass.get(i).getStudent().getSchool().getSchool_ID()==schoolid &&
															studentclass.get(i).getClazz().getSchool().getSchool_ID()==schoolid){
														AttendanceDataBean attendancelist = new AttendanceDataBean();
														attendancelist.setStudentName(studentdetail.get(0).getFirstName() + studentdetail.get(0).getLastName());
														attendancelist.setStudentID(studentclass.get(i).getStudent().getRollNumber());
														if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
															attendancelist.setDate(GetTimeZone.getDate("Asia/Makassar"));
														}else{
															attendancelist.setDate(date);
														}
		
														v = null;
														v = entitymanager.createQuery("from Attendance where school_ID=? and date=? and class_=? and section=?");
														v.setParameter(1, schoolid);
														if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
															v.setParameter(2, GetTimeZone.getDate("Asia/Makassar"));
														}else{
															v.setParameter(2, date);
														}
														v.setParameter(3, classname);
														v.setParameter(4, section);
														List<Attendance> attendance = (List<Attendance>) v.getResultList();
														if (attendance.size() > 0) {													
															v = null;
															v = entitymanager.createQuery("from Attendanceclass where attendance_ID=? and studentName=? and student_ID=? and teacher_ID=? and class_time_table_ID=? and period=?");
															v.setParameter(1, attendance.get(0).getAttendance_ID());
															v.setParameter(2, studentdetail.get(0).getFirstName()+ studentdetail.get(0).getLastName());
															v.setParameter(3, studentclass.get(i).getStudent().getRollNumber());
															v.setParameter(4, teacher.get(0).getTeacher_ID());
															v.setParameter(5, classTimeTable.get(k).getClass_time_table_ID());
															v.setParameter(6, classTimeTable.get(k).getPeriod());
															List<Attendanceclass> attendanceclass = (List<Attendanceclass>) v.getResultList();
															logger.debug("size - " + attendanceclass.size());
															if (attendanceclass.size() > 0) {
																if(attendanceclass.get(0).getStatus().equalsIgnoreCase("Present")){
																	attendancelist.setFlag(true);
																	attendancelist.setFlag1(false);
																	attendancelist.setStatus("");	
																	attendancelist.setPercentage("");
																}else{
																	attendancelist.setFlag(false);
																	attendancelist.setFlag1(true);
																	attendancelist.setStatus(attendanceclass.get(0).getStatus());	
																	attendancelist.setPercentage(attendanceclass.get(0).getPercentage());
																}
															} else {
																Attendanceclass insert1 = new Attendanceclass();
																insert1.setAttendance(entitymanager.find(Attendance.class,
																		attendance.get(attendance.size() - 1).getAttendance_ID()));
																insert1.setStudentName(studentdetail.get(0).getFirstName()+studentdetail.get(0).getLastName());
																insert1.setStudent_ID(studentdetail.get(0).getStudentid().getRollNumber());
																insert1.setStatus("Present");
																insert1.setTeacher(
																		entitymanager.find(Teacher.class, teacher.get(0).getTeacher_ID()));
																insert1.setPeriod(classTimeTable.get(k).getPeriod());
																insert1.setClassTimeTable(entitymanager.find(ClassTimeTable.class,
																		classTimeTable.get(k).getClass_time_table_ID()));
																if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
																	insert1.setTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
																}else{
																	insert1.setTime(timestamp);
																}	
																insert1.setMonth(attendanceDataBean.getMonthyear());
																insert1.setYear(attendanceDataBean.getYear());
																insert1.setPercentage("100%");
																entitymanager.persist(insert1);
																entitymanager.flush();
																entitymanager.clear();
																v = entitymanager.createQuery("from Attendanceclass where attendance_ID=? and student_ID=? and period=?");
																v.setParameter(1, attendance.get(0).getAttendance_ID());
																v.setParameter(2,studentclass.get(i).getStudent().getRollNumber());
																v.setParameter(3, classTimeTable.get(k).getPeriod());
																List<Attendanceclass> attendanceclass1 = (List<Attendanceclass>) v.getResultList();
																if (attendanceclass1.size() > 0) {
																	if(attendanceclass1.get(0).getStatus().equalsIgnoreCase("Present")){
																		attendancelist.setFlag(true);
																		attendancelist.setFlag1(false);
																		attendancelist.setStatus("");	
																		attendancelist.setPercentage("");
																	}else{
																		attendancelist.setFlag(false);
																		attendancelist.setFlag1(true);
																		attendancelist.setStatus(attendanceclass1.get(0).getStatus());	
																		attendancelist.setPercentage(attendanceclass1.get(0).getPercentage());
																	}
																} else {
																	attendancelist.setStatus("");
																	attendancelist.setPercentage("");
																	attendancelist.setFlag1(false);
																	attendancelist.setFlag(true);
																}
															}
														}
														else{		
															Attendance insert = new Attendance();
															insert.setSchool(entitymanager.find(School.class, schoolid));
															insert.setClass_(classname);
															insert.setSection(section);
															if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
																insert.setDate(GetTimeZone.getDate("Asia/Makassar"));
															}else{
																insert.setDate(date);
															}													
															entitymanager.persist(insert);
															v = null;
															v = entitymanager.createQuery("from Attendance");
															List<Attendance> attendance1 = (List<Attendance>) v.getResultList();
															if (attendance1.size() > 0) {
																int attendanceid = attendance1.get(attendance1.size() - 1).getAttendance_ID();
																logger.debug("last attendance id -- "
																		+ attendance1.get(attendance1.size() - 1).getAttendance_ID());
																Attendanceclass insert1 = new Attendanceclass();
																insert1.setAttendance(entitymanager.find(Attendance.class,
																		attendance1.get(attendance1.size() - 1).getAttendance_ID()));
																insert1.setStudentName(studentdetail.get(0).getFirstName()+studentdetail.get(0).getLastName());
																insert1.setStudent_ID(studentdetail.get(0).getStudentid().getRollNumber());
																insert1.setStatus("Present");
																insert1.setTeacher(
																		entitymanager.find(Teacher.class, teacher.get(0).getTeacher_ID()));
																insert1.setPeriod(classTimeTable.get(k).getPeriod());
																insert1.setPercentage("100%");
																insert1.setClassTimeTable(entitymanager.find(ClassTimeTable.class,
																		classTimeTable.get(k).getClass_time_table_ID()));
																if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
																	insert1.setTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
																}else{
																	insert1.setTime(timestamp);
																}	
																insert1.setMonth(attendanceDataBean.getMonthyear());
																insert1.setYear(attendanceDataBean.getYear());
																entitymanager.persist(insert1);
																entitymanager.flush();
																entitymanager.clear();
															}
														
															attendancelist.setStatus("");
															attendancelist.setPercentage("");
															attendancelist.setFlag1(false);
															attendancelist.setFlag(true);
														}
														attendancelist.setSno(i1);
														attendancedata.add(attendancelist);
														attendanceDataBean.setStudentList(attendancedata);
														i1++;
													}											
												}
											}
										}
									}
								}
							}
						}
					logger.debug("student size -- " + attendanceDataBean.getStudentList());
				}
			}
		} catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
			logger.warn("Exception", e);
			e.printStackTrace();
		}
		return null;		
	}
	/* Ragulan OCT 25   takeattendance*/

	/*@Override
	@Transactional(value="transactionManager")
	public String takeattendance(String personID,AttendanceDataBean attendanceDataBean) {
		logger.debug("inside take attendance student list dao ");
		Query v = null;
		List<Person> person = null;
		String days = "";
		String st = "";
		String et = "";
		String stime = "";
		String classname="";
		String section="";
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		List<AttendanceDataBean> attendancedata = new ArrayList<AttendanceDataBean>();
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					int schoolid = person.get(0).getSchool().getSchool_ID();
					String day = "";
					if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						day = String.valueOf(GetTimeZone.getDate("Asia/Makassar").getDay());
					}else{
						day = String.valueOf(date.getDay());
					}
					
					logger.debug("day "+day);
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						if (day.equals("0"))
							days = "Sunday";
					}if (day.equals("1"))
						days = "Monday";
					else if (day.equals("2"))
						days = "Tuesday";
					else if (day.equals("3"))
						days = "Wednesday";
					else if (day.equals("4"))
						days = "Thursday";
					else if (day.equals("5"))
						days = "Friday";
					else if (day.equals("6"))
						days = "Saturday";
					attendanceDataBean.setMonthyear("" + (now.get(Calendar.MONTH) + 1));
					attendanceDataBean.setYear("" + now.get(Calendar.YEAR));
					if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						st= GetTimeZone.getTimeHour("Asia/Makassar");
						et=GetTimeZone.getTimeMin("Asia/Makassar");	
					}else{
						st = String.valueOf(now.getTime().getHours());
						et = String.valueOf(now.getTime().getMinutes());			
					}
					logger.debug("[Before] start time -- "+st+" end time -- "+et+" date "+date);
					if (st.length() == 1 && et.length() == 1) {
						stime = "0" + st + ":" + "0" + et;
					} else if (st.length() == 1 && et.length() == 2) {
						stime = "0" + st + ":" + et;
					} else if (st.length() == 2 && et.length() == 1) {
						stime = st + ":" + "0" + et;
					} else if (st.length() == 2 && et.length() == 2) {
						stime = st + ":" + et;
					}
					logger.debug("[After] start time -- "+st+" end time -- "+et+" date "+date);

					org.joda.time.DateTime now1 = new org.joda.time.DateTime();
					 DateTime dates=now1.toDateTime( org.joda.time.DateTimeZone.UTC );
					 logger.debug("hour "+dates.getHourOfDay()+" min -- "+dates.getMinuteOfHour());
					logger.debug("current time -- "+stime);
					v = entitymanager.createQuery("from Teacher where school_ID=? and person_ID=? and status=?");
					v.setParameter(1, schoolid);
					v.setParameter(2, personID);
					v.setParameter(3, "Active");
					List<Teacher> teacher = (List<Teacher>) v.getResultList();
					logger.warn("teacher size "+teacher.size());
					if (teacher.size() > 0) {
							v = null;
							v = entitymanager.createQuery(
									"from ClassTimeTable where teacher_ID=? and day=? and ? between startTime and endTime");
							v.setParameter(1, teacher.get(0).getTeacher_ID());
							v.setParameter(2, days);
							v.setParameter(3, stime);
							List<ClassTimeTable> classTimeTable = (List<ClassTimeTable>) v.getResultList();
							logger.warn("class time table size "+classTimeTable.size());
							if (classTimeTable.size() > 0){
								classname=classTimeTable.get(0).getClassTable().getClassz().getClassName();
								section=classTimeTable.get(0).getClassTable().getClassz().getClassSection();
								int class_id=classTimeTable.get(0).getClassTable().getClassz().getClass_ID();	
								attendanceDataBean.setClassname(classname+"/"+section);
								v = null;
								v = entitymanager.createQuery("from StudentClass where class_ID=? and status=?");
								v.setParameter(1, class_id);
								v.setParameter(2, "Active");
								List<StudentClass> studentclass = (List<StudentClass>) v.getResultList();
								if (studentclass.size() > 0) {
									int i1 = 1;
									for (int i = 0; i < studentclass.size(); i++) {
										v = null;
										v = entitymanager.createQuery("from StudentDetail where student_ID=?");
										v.setParameter(1, studentclass.get(i).getStudent().getStudent_ID());
										List<StudentDetail> studentdetail = (List<StudentDetail>) v.getResultList();
										logger.debug("student detail size"+studentdetail.size());
										if (studentdetail.size() > 0) {
											if(studentclass.get(i).getStudent().getSchool().getSchool_ID()==schoolid &&
													studentclass.get(i).getClazz().getSchool().getSchool_ID()==schoolid){
												AttendanceDataBean attendancelist = new AttendanceDataBean();
												attendancelist.setStudentName(studentdetail.get(0).getFirstName() + studentdetail.get(0).getLastName());
												attendancelist.setStudentID(studentclass.get(i).getStudent().getRollNumber());
												if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
													attendancelist.setDate(GetTimeZone.getDate("Asia/Makassar"));
												}else{
													attendancelist.setDate(date);
												}

												v = null;
												v = entitymanager.createQuery("from Attendance where school_ID=? and date=? and class_=? and section=?");
												v.setParameter(1, schoolid);
												if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
													v.setParameter(2, GetTimeZone.getDate("Asia/Makassar"));
												}else{
													v.setParameter(2, date);
												}
												v.setParameter(3, classname);
												v.setParameter(4, section);
												List<Attendance> attendance = (List<Attendance>) v.getResultList();
												if (attendance.size() > 0) {													
													v = null;
													v = entitymanager.createQuery("from Attendanceclass where attendance_ID=? and studentName=? and student_ID=? and teacher_ID=? and class_time_table_ID=? and period=?");
													v.setParameter(1, attendance.get(0).getAttendance_ID());
													v.setParameter(2, studentdetail.get(0).getFirstName()+ studentdetail.get(0).getLastName());
													v.setParameter(3, studentclass.get(i).getStudent().getRollNumber());
													v.setParameter(4, teacher.get(0).getTeacher_ID());
													v.setParameter(5, classTimeTable.get(0).getClass_time_table_ID());
													v.setParameter(6, classTimeTable.get(0).getPeriod());
													List<Attendanceclass> attendanceclass = (List<Attendanceclass>) v.getResultList();
													logger.debug("size - " + attendanceclass.size());
													if (attendanceclass.size() > 0) {
														if(attendanceclass.get(0).getStatus().equalsIgnoreCase("Present")){
															attendancelist.setFlag(true);
															attendancelist.setFlag1(false);
															attendancelist.setStatus("");	
															attendancelist.setPercentage("");
														}else{
															attendancelist.setFlag(false);
															attendancelist.setFlag1(true);
															attendancelist.setStatus(attendanceclass.get(0).getStatus());	
															attendancelist.setPercentage(attendanceclass.get(0).getPercentage());
														}
													} else {
														Attendanceclass insert1 = new Attendanceclass();
														insert1.setAttendance(entitymanager.find(Attendance.class,
																attendance.get(attendance.size() - 1).getAttendance_ID()));
														insert1.setStudentName(studentdetail.get(0).getFirstName()+studentdetail.get(0).getLastName());
														insert1.setStudent_ID(studentdetail.get(0).getStudentid().getRollNumber());
														insert1.setStatus("Present");
														insert1.setTeacher(
																entitymanager.find(Teacher.class, teacher.get(0).getTeacher_ID()));
														insert1.setPeriod(classTimeTable.get(0).getPeriod());
														insert1.setClassTimeTable(entitymanager.find(ClassTimeTable.class,
																classTimeTable.get(0).getClass_time_table_ID()));
														if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
															insert1.setTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
														}else{
															insert1.setTime(timestamp);
														}	
														insert1.setMonth(attendanceDataBean.getMonthyear());
														insert1.setYear(attendanceDataBean.getYear());
														insert1.setPercentage("100%");
														entitymanager.persist(insert1);
														entitymanager.flush();
														entitymanager.clear();
														v = entitymanager.createQuery("from Attendanceclass where attendance_ID=? and student_ID=? and period=?");
														v.setParameter(1, attendance.get(0).getAttendance_ID());
														v.setParameter(2,studentclass.get(i).getStudent().getRollNumber());
														v.setParameter(3, classTimeTable.get(0).getPeriod());
														List<Attendanceclass> attendanceclass1 = (List<Attendanceclass>) v.getResultList();
														if (attendanceclass1.size() > 0) {
															if(attendanceclass1.get(0).getStatus().equalsIgnoreCase("Present")){
																attendancelist.setFlag(true);
																attendancelist.setFlag1(false);
																attendancelist.setStatus("");	
																attendancelist.setPercentage("");
															}else{
																attendancelist.setFlag(false);
																attendancelist.setFlag1(true);
																attendancelist.setStatus(attendanceclass1.get(0).getStatus());	
																attendancelist.setPercentage(attendanceclass1.get(0).getPercentage());
															}
														} else {
															attendancelist.setStatus("");
															attendancelist.setPercentage("");
															attendancelist.setFlag1(false);
															attendancelist.setFlag(true);
														}
													}
												}
												else{		
													Attendance insert = new Attendance();
													insert.setSchool(entitymanager.find(School.class, schoolid));
													insert.setClass_(classname);
													insert.setSection(section);
													if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
														insert.setDate(GetTimeZone.getDate("Asia/Makassar"));
													}else{
														insert.setDate(date);
													}													
													entitymanager.persist(insert);
													v = null;
													v = entitymanager.createQuery("from Attendance");
													List<Attendance> attendance1 = (List<Attendance>) v.getResultList();
													if (attendance1.size() > 0) {
														int attendanceid = attendance1.get(attendance1.size() - 1).getAttendance_ID();
														logger.debug("last attendance id -- "
																+ attendance1.get(attendance1.size() - 1).getAttendance_ID());
														Attendanceclass insert1 = new Attendanceclass();
														insert1.setAttendance(entitymanager.find(Attendance.class,
																attendance1.get(attendance1.size() - 1).getAttendance_ID()));
														insert1.setStudentName(studentdetail.get(0).getFirstName()+studentdetail.get(0).getLastName());
														insert1.setStudent_ID(studentdetail.get(0).getStudentid().getRollNumber());
														insert1.setStatus("Present");
														insert1.setTeacher(
																entitymanager.find(Teacher.class, teacher.get(0).getTeacher_ID()));
														insert1.setPeriod(classTimeTable.get(0).getPeriod());
														insert1.setPercentage("100%");
														insert1.setClassTimeTable(entitymanager.find(ClassTimeTable.class,
																classTimeTable.get(0).getClass_time_table_ID()));
														if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
															insert1.setTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
														}else{
															insert1.setTime(timestamp);
														}	
														insert1.setMonth(attendanceDataBean.getMonthyear());
														insert1.setYear(attendanceDataBean.getYear());
														entitymanager.persist(insert1);
														entitymanager.flush();
														entitymanager.clear();
													}
												
													attendancelist.setStatus("");
													attendancelist.setPercentage("");
													attendancelist.setFlag1(false);
													attendancelist.setFlag(true);
												}
												attendancelist.setSno(i1);
												attendancedata.add(attendancelist);
												attendanceDataBean.setStudentList(attendancedata);
												i1++;
											}											
										}
									}
								}
							}
						}
					logger.debug("student size -- " + attendanceDataBean.getStudentList());
				}
			}
		} catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
			logger.warn("Exception", e);
		}
		return null;		
	}*/

	@Override
	@SuppressWarnings("unchecked")
	public String getperstudentList(StudentPerformanceDataBean studentPerformanceDataBean,String personID) {
		Query v = null;
		ArrayList<StudentPerformanceDataBean> rollnolist1 = null;
		List<Person> person = null;
		String days = "";
		String st = "";
		String et = "";
		String stime = "";
		String classname = "";
		String section = "";
		int studid = 0;
		Calendar now = Calendar.getInstance();
		//int year = now.get(Calendar.YEAR);
		try {
			rollnolist1=new ArrayList<StudentPerformanceDataBean>();		
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					int schoolid = person.get(0).getSchool().getSchool_ID();
					String day = "";
					if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						day=String.valueOf(GetTimeZone.getDate("Asia/Makassar").getDay());
					}else{
						day=String.valueOf(date.getDay());
					}
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						if (day.equals("0"))
							days = "Sunday";
					} if (day.equals("1"))
						days = "Monday";
					else if (day.equals("2"))
						days = "Tuesday";
					else if (day.equals("3"))
						days = "Wednesday";
					else if (day.equals("4"))
						days = "Thursday";
					else if (day.equals("5"))
						days = "Friday";
					else if (day.equals("6"))
						days = "Saturday";
					if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						st= GetTimeZone.getTimeHour("Asia/Makassar");
						et=GetTimeZone.getTimeMin("Asia/Makassar");
					}else{
						st = String.valueOf(now.getTime().getHours());
						et = String.valueOf(now.getTime().getMinutes());
					}
					if (st.length() == 1 && et.length() == 1) {
						stime = "0" + st + ":" + "0" + et;
					} else if (st.length() == 1 && et.length() == 2) {
						stime = "0" + st + ":" + et;
					} else if (st.length() == 2 && et.length() == 1) {
						stime = st + ":" + "0" + et;
					} else if (st.length() == 2 && et.length() == 2) {
						stime = st + ":" + et;
					}
					v = entitymanager.createQuery("from Teacher where school_ID=? and person_ID=? and status=?");
					v.setParameter(1, schoolid);
					v.setParameter(2, personID);
					v.setParameter(3, "Active");
					List<Teacher> teacher = (List<Teacher>) v.getResultList();
					if (teacher.size() > 0) {
						v = null;
						v = entitymanager.createQuery("from ClassTimeTable where teacher_ID=? and day=? and ? between startTime and endTime");
						v.setParameter(1, teacher.get(0).getTeacher_ID());
						v.setParameter(2, days);
						v.setParameter(3, stime);
						List<ClassTimeTable> classTimeTable = (List<ClassTimeTable>) v.getResultList();
						if (classTimeTable.size() > 0){
							classname=classTimeTable.get(0).getClassTable().getClassz().getClassName();
							section=classTimeTable.get(0).getClassTable().getClassz().getClassSection();
							int class_id=classTimeTable.get(0).getClassTable().getClassz().getClass_ID();	
							studentPerformanceDataBean.setClassname(classname+"/"+section);
							v = null;
							v = entitymanager.createQuery("from StudentClass where class_ID=? and status=?");
							v.setParameter(1, class_id);
							v.setParameter(2, "Active");
							List<StudentClass> clslist1 = (List<StudentClass>) v.getResultList();
							if (clslist1.size() > 0) {
								for (int i = 0; i < clslist1.size(); i++) {
									studid = clslist1.get(i).getStudent().getStudent_ID();
									v = null;
									v = entitymanager.createQuery("from StudentDetail where student_ID=?");
									v.setParameter(1, studid);
									List<StudentDetail> studentlist = (List<StudentDetail>) v.getResultList();
									logger.debug("--------list size-----" + studentlist.size());
									if (studentlist.size() > 0) {
										for (int s = 0; s < studentlist.size(); s++) {
											StudentPerformanceDataBean idobj = new StudentPerformanceDataBean();
											idobj.setPerformStudID(studentlist.get(s).getFirstName()+studentlist.get(s).getLastName()+"/"+clslist1.get(i).getStudent().getRollNumber());
											rollnolist1.add(idobj);
								
										}								
									}
								}
								studentPerformanceDataBean.setRollnolist(rollnolist1);
							}
						}
					}
				}
			}
			} catch (Exception e) {
					logger.warn("Exception", e);
			}
		return null;
	}	

	@Transactional(value = "transactionManager")
	@Override
	@SuppressWarnings("unchecked")
	public String bookstockin(BookSaleDataBean bookSaleDataBean,String personID) {
		Query q=null;
		int schoolid=0;
		List<Person> perlist=null;
		logger.debug("www");
		String status="";
	try{
		perlist=new ArrayList<Person>();
		perlist=getRollType(personID);
		schoolid=perlist.get(0).getSchool().getSchool_ID();
		q=entitymanager.createQuery("from BookSalesRecord where bookName=? and school_ID=? and status='Active'");
		q.setParameter(1, bookSaleDataBean.getBookName());
		q.setParameter(2, schoolid);
		List<BookSalesRecord> schoolList=(List<BookSalesRecord>)q.getResultList();
		if(schoolList.size()>0){
			int id=schoolList.get(0).getSales_record_ID();
			BookSalesRecord booksalesrecord=entitymanager.find(BookSalesRecord.class, id);
			booksalesrecord.setQuantity(""+new BigDecimal(bookSaleDataBean.getBookQuantity()).add(new BigDecimal(schoolList.get(0).getQuantity())));
			entitymanager.merge(booksalesrecord);
			status="Success";
		}
	}catch(Exception e)	{
	logger.warn(" exception - "+e);	
	}
		return status;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	  public List<Library> booknamelist(String personID) {
	   List<Library> librarylist=null;
	   
	   try
	   {
	    librarylist=new ArrayList<Library>();
	    logger.debug("check stock in ");
	    Query booklistquery=null;
	    booklistquery=entitymanager.createQuery("from Library where status='Active'");
	    List<Library> booklist=(ArrayList<Library>)booklistquery.getResultList();
	    if(booklist.size()>0)
	    {
	    
	    for(int i=0;booklist.size()>i;i++)
	    {
	        Library library=new Library();
	     logger.debug("check list"+i);
	     library.setAuthorName(booklist.get(i).getAuthorName());
	     logger.debug("check auther name"+library.getAuthorName());
	     library.setPrice(booklist.get(i).getPrice());
	     library.setBookName(booklist.get(i).getBookName());
	     library.setQuantity(booklist.get(i).getQuantity());
	     librarylist.add(library);
	     }
	    }
	    }
	   catch(Exception e)
	   {
	   logger.warn(" exception - "+e); 
	   }
	  
	   return librarylist;
	  }

	@Override
	@SuppressWarnings("unchecked")
	public List<Library> authernamelist(String authername, String personID) {
		List<Library> authernamelist=null;
			try
		{
				if(personID!=null)
				{
					logger.debug("check dao name"+authername);
			Query autherquery=null;
			autherquery=entitymanager.createQuery("select authorName from Library where bookName=? and status='Active'");
			autherquery.setParameter(1,authername);
			authernamelist=(ArrayList<Library>)autherquery.getResultList();
				}
		}
		catch(Exception e)
		{
		logger.warn(" exception - "+e);	
		}
	    return authernamelist;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String stockinbookQuentity(LibraryDataBean libraryDataBean,
			String personID) 
	{
		String status="fail";
		int availabelquantity=0;
		Query libQuery=null;
	    int quantity1=0;
		List<Library> library=null;
		int quantity=0;
		try
		{
			quantity1=Integer.parseInt(libraryDataBean.getAddQuentity());
		    libQuery=entitymanager.createQuery("from Library where bookName=? and authorName=? and status='Active'");
			libQuery.setParameter(1,libraryDataBean.getBookName());
			libQuery.setParameter(2,libraryDataBean.getAuthorName());
			library=(ArrayList<Library>)libQuery.getResultList();
			logger.debug("size"+library.size());
			int id=library.get(0).getLibrary_ID();
			logger.debug("check library id"+id);
			availabelquantity=Integer.parseInt(library.get(0).getQuantity());
			quantity=quantity1+availabelquantity;
			if(library.size()>0)
			 {
			     Library stock=entitymanager.find(Library.class,id);
				 stock.setQuantity(""+quantity);
				 entitymanager.merge(stock);
				 status="success";
			 }
			
		
		}
		catch(Exception e)
		{
			
		}
		return status;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<String> studentidlist(String personID) {
		List<Person> rollStatus = null;
		int school_ID=0;
		Query q=null;
		List<String> studrollnumber=null;
		try{
			rollStatus=new ArrayList<Person>();	
			studrollnumber=new ArrayList<String>();
			rollStatus = getRollType(personID);
			logger.debug("check roll status"+rollStatus.size());
			if(rollStatus.size()>0){
				school_ID = rollStatus.get(0).getSchool().getSchool_ID();
				logger.debug("check school id" + school_ID);
				q = entitymanager.createQuery("from Student where school_ID=? and status='Active'");
				q.setParameter(1, school_ID);
				List<Student> studentlist=(List<Student>)q.getResultList();
				if(studentlist.size()>0){
					for (int i = 0; i < studentlist.size(); i++) {
						q=null;
						q = entitymanager.createQuery("from StudentDetail where student_ID=?");
						q.setParameter(1, studentlist.get(i).getStudent_ID());
						List<StudentDetail> studentdetail=(List<StudentDetail>)q.getResultList();
						if(studentdetail.size()>0){
							studrollnumber.add(studentdetail.get(0).getFirstName()+studentdetail.get(0).getLastName()+"/"+studentlist.get(i).getRollNumber());
						}
					}
				}
				logger.debug("check studrollnumber size"+ studrollnumber.size());
			}	
		}
		catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}
		return studrollnumber;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LibraryDataBean> stuinfo(String personID, String sturollno) {
		int school_ID=0;
		List<Person> rollStatus = null;
		List<Student> slist = null;
		List<LibraryDataBean> lbeanlist = new ArrayList<LibraryDataBean>();
		int sid = 0;
		String firstname = "";
		String lastname = "";
		String fullname = "";
		try {
			logger.debug("check roll no" + sturollno);
			Query q = null;
			rollStatus = new ArrayList<Person>();
			rollStatus = getRollType(personID);
			logger.debug("check roll status" + rollStatus.size());
			if (rollStatus.size() > 0) {
				school_ID = rollStatus.get(0).getSchool().getSchool_ID();
				String student = sturollno;
				String studentid = student.substring(student.lastIndexOf("/") + 1);
				q = entitymanager.createQuery("from Student where school_ID=? and rollNumber=? and status='Active'");
				q.setParameter(1, school_ID);
				q.setParameter(2, studentid);
				slist = (ArrayList<Student>) q.getResultList();
				if (slist.size() > 0) {
					sid = slist.get(0).getStudent_ID();
					logger.debug("check id====" + sid);
					q = null;
					q = entitymanager.createQuery("from StudentDetail where student_ID=?");
					q.setParameter(1, sid);
					List<StudentDetail> stdetail = (ArrayList<StudentDetail>) q.getResultList();
					if (stdetail.size() > 0) {
						firstname = stdetail.get(0).getFirstName();
						lastname = stdetail.get(0).getLastName();
						fullname = firstname.concat(lastname);
						q = null;
						q = entitymanager.createQuery("from StudentClass where student_ID=? ");
						q.setParameter(1, sid);
						List<StudentClass> stclas = (ArrayList<StudentClass>) q.getResultList();
						logger.debug("check class size" + stclas.size());
						if (stclas.size() > 0) {
							int classid = stclas.get(0).getClazz().getClass_ID();
							q = null;
							q = entitymanager.createQuery("from Class where class_ID=?");
							q.setParameter(1, classid);
							List<Class> sss = (ArrayList<Class>) q.getResultList();
							if(sss.size()>0){
								LibraryDataBean libraryDataBean = new LibraryDataBean();
								libraryDataBean.setStudentname(fullname);
								libraryDataBean.setStudentid(studentid);
								libraryDataBean.setLibStuClass(sss.get(0).getClassName()+"/"+sss.get(0).getClassSection());
								lbeanlist.add(libraryDataBean);
							}
						}

					}
				}

			}

		} catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return lbeanlist;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public String borrowrinsertbook(String personID,
			LibraryDataBean libraryDataBean) {
		List<Person> rollStatus = null;
		int school_ID=0;
		String status="Fail";
		try
		{
			Query q=null;
			rollStatus=new ArrayList<Person>();	
			rollStatus = getRollType(personID);
			logger.debug("check roll status"+rollStatus.size());
			if(rollStatus.size()>0)
			{
				school_ID=rollStatus.get(0).getSchool().getSchool_ID();					
				q=entitymanager.createQuery("from Library where bookName=? and school_ID=? and status='Active'");
				q.setParameter(1, libraryDataBean.getBookName());
				q.setParameter(2, rollStatus.get(0).getSchool().getSchool_ID());
				List<Library> library=(List<Library>)q.getResultList();
				logger.debug("library size "+library.size());
				if(library.size()>0)
				{
					int libID=library.get(0).getLibrary_ID();	
					if(library.get(0).getQuantity().equalsIgnoreCase("0")){
						status="NoStock";
					}
					else
					{
						String students=libraryDataBean.getLibStudID();
						String studentids=students.substring(students.lastIndexOf("/")+1);
						q=entitymanager.createQuery("from Student where school_ID=? and rollNumber=?");
						q.setParameter(1,school_ID);
						q.setParameter(2,studentids);
						List<Student> student=(ArrayList<Student>)q.getResultList();
						logger.debug("student size "+student.size());
						if(student.size()>0)
						 {
							int studentid=student.get(0).getStudent_ID();								
							q=entitymanager.createQuery("from Browerbook where student_ID=? and library_ID=? and school_ID=? and status='Borrowed'");
							q.setParameter(1, studentids);
							q.setParameter(2, libID);
							q.setParameter(3, rollStatus.get(0).getSchool().getSchool_ID());
							List<Browerbook> browerbook=(List<Browerbook>)q.getResultList();
							logger.debug("borrower size "+browerbook.size());
							if(browerbook.size()>0){
								
								status="Exsist";
							}else{									
								Browerbook brrowerBook = new Browerbook();
								if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
									brrowerBook.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
									brrowerBook.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
								}else{
									brrowerBook.setCreatedDate(date);
									brrowerBook.setCreatedTime(timestamp);
								}	
								brrowerBook.setLibrary(entitymanager.find(Library.class, libID));
								brrowerBook.setSchool(entitymanager.find(School.class, school_ID));
								brrowerBook.setStudent(entitymanager.find(Student.class, studentid));
								brrowerBook.setStatus("Borrowed");
								brrowerBook.setDueDate(libraryDataBean.getBorroweDate());
								entitymanager.persist(brrowerBook);
								BigDecimal stockin=BigDecimal.valueOf(0);
								stockin=new BigDecimal(library.get(0).getQuantity()).subtract(BigDecimal.valueOf(1));
								Library libraryUpdate=entitymanager.find(Library.class, libID);
								libraryUpdate.setQuantity(stockin.toString());
								entitymanager.merge(libraryUpdate);
								status="Success";
							}								
						}	
					}												
				 }
			}				
		}
		catch(Exception e)
		{
			logger.error("Exception -->"+e.getMessage());
			logger.warn(" exception - "+e);
		}
		return status;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public String getNotReturnedBooks(String personID, LoginAccess loginaccess){
		Query v=null;
		String status="Fail";
		List<Person> role=new ArrayList<Person>();
		String studentid="";List<String> studentids=new ArrayList<String>();
		List<String> phnonenos=new ArrayList<String>();
		try{
			if (personID != null) {
				role = getRollType(personID);
				if (role.size() > 0) {
					int schoolid=role.get(0).getSchool().getSchool_ID();
					v=entitymanager.createQuery("from Browerbook where school_ID=? and status='Borrowed'");
					v.setParameter(1, role.get(0).getSchool().getSchool_ID());
					List<Browerbook> borrowbook=(List<Browerbook>)v.getResultList();
					if(borrowbook.size()>0){
						for (int i = 0; i <borrowbook.size(); i++) { 
							int c=0;
							if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
								if(borrowbook.get(i).getDueDate().compareTo(GetTimeZone.getDate("Asia/Makassar"))<0) c++;
							}else{
								if(borrowbook.get(i).getDueDate().compareTo(date)<0) c++;
							}
							if(c>0){
								v=null;
								v=entitymanager.createQuery("from Student where rollNumber=? and school_ID=? and status='Active' ");
								v.setParameter(1, borrowbook.get(i).getStudent().getRollNumber());
								v.setParameter(2, role.get(0).getSchool().getSchool_ID());
								List<Student> student=(List<Student>)v.getResultList();
								if(student.size()>0){
									logger.debug("id "+studentids);	
									String ids=borrowbook.get(i).getStudent().getRollNumber();
									 studentids.add(ids);
									v=null;
									v=entitymanager.createQuery("from StudentParent where student_ID=? and status='Active' ");
									v.setParameter(1, student.get(0).getStudent_ID());
									List<StudentParent> studentParent=(List<StudentParent>)v.getResultList();
									if(studentParent.size()>0){											
										String phhnumber=studentParent.get(0).getParent().getParentDetail().getPhoneNumber();
										studentid=borrowbook.get(i).getStudent().getRollNumber();
										//MailSendJDBC.smsSendParent(phhnumber, studentid,role.get(0).getSchool().getName());																			
									}
								}									
							}
						}		
						v=null;
						v=entitymanager.createQuery("from Teacher where school_ID=? and status='Active' ");
						v.setParameter(1, role.get(0).getSchool().getSchool_ID());
						List<Teacher> teacher=(List<Teacher>)v.getResultList();
						if(teacher.size()>0){
							for (int j = 0; j < teacher.size(); j++) {
								v = entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
								v.setParameter(1, teacher.get(0).getTeacher_ID());
								List<TeacherDetail> teacherDetail = (List<TeacherDetail>) v.getResultList();
								if (teacherDetail.size() > 0) {
									phnonenos.add(teacherDetail.get(0).getPhoneNumber());
									//MailSendJDBC.smsSendBorrowBook(phnonenos,studentids,role.get(0).getSchool().getName());
								}								
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return "";
	}
	
	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	@Override
	public String inserschool(LoginAccess loginaccess) {
		String status="fail";
		Query q=null;
		//int schoolid=0;
		//String password="";
		try{
				q=entitymanager.createQuery("from School where name=?");
				q.setParameter(1, loginaccess.getSchoolName());
				List<School> schoollist=(List<School>)q.getResultList();
				if(schoollist.size()>0){
					status="Exist";
				}else{
					School login=new School();
					login.setName(loginaccess.getSchoolName());
					login.setEmailId(loginaccess.getSchoolEmail());
					login.setAlternativeemail(loginaccess.getAlternativeemail());
					login.setPhoneNumber(loginaccess.getSchoolPhone());
					login.setLandlinenumber(loginaccess.getLandlinenumber());
					login.setContactpersonname(loginaccess.getUsername());
					login.setMobilenumber(loginaccess.getContactnumber());
					login.setAddress(loginaccess.getSchoolAddress());
					login.setCountry(loginaccess.getCountry());
					login.setState(loginaccess.getState());
					login.setLogopath(loginaccess.getFilepath());
					login.setFaxNumber(loginaccess.getLandlinenumber());
					login.setStatus("Waiting");
					login.setCreatedDate(date);
					login.setCreatedTime(timestamp);
					login.setUsername(loginaccess.getLoginusername());
					entitymanager.persist(login);
					status="Success";
				}
		}catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return status;
	}
	
	@Transactional(value = "transactionManager")
	private String insertAdminMenus(int user_ID) {
		String res = "Fail";
		int mainMenuID1 = 0;
		String res1 = "F";
		String res2 = "F";
		String res3 = "F";
		String res4 = "F";
		String res5 = "F";
		String res6 = "F";
		String res7 = "F";
		String res9 = "F";String res10 = "F";
		String res11 = "F";
		try {
			// Insert UserProduct table
			if (user_ID > 0) {
				mainMenuID1 = getMainMenu("REG000");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res1 = "S";
				}
				mainMenuID1 = getMainMenu("TIM000");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res2 = "S";
				}
				mainMenuID1 = getMainMenu("NOT000");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res3 = "S";
				}
				mainMenuID1 = getMainMenu("STU000");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res9 = "S";
				}
				mainMenuID1 = getMainMenu("REP000");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res4 = "S";
				}
				mainMenuID1 = getMainMenu("PAY000");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res5 = "S";
				}
				mainMenuID1 = getMainMenu("PRO000");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res6 = "S";
				}
				mainMenuID1 = getMainMenu("ATT000");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res7 = "S";
				}
				mainMenuID1 = getMainMenu("MYS000");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res10 = "S";
				}
				mainMenuID1 = getMainMenu("REP001");
				if (mainMenuID1 > 0) {
					UserProduct user = new UserProduct();
					user.setUser(entitymanager.find(User.class, user_ID));
					user.setStatus("Active");
					user.setProduct(entitymanager.find(Product.class, mainMenuID1));
					entitymanager.persist(user);
					entitymanager.flush();
					entitymanager.clear();
					res11 = "S";
				}
				if (res1.equalsIgnoreCase("S") && res2.equalsIgnoreCase("S") && res3.equalsIgnoreCase("S")
						&& res4.equalsIgnoreCase("S") && res5.equalsIgnoreCase("S") && res6.equalsIgnoreCase("S")
						&& res7.equalsIgnoreCase("S") && res9.equalsIgnoreCase("S") && res10.equalsIgnoreCase("S") && res11.equalsIgnoreCase("S")) {
					res = "Success";
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LoginAccess> getSchoolsList(LoginAccess loginaccess) {
		Query q=null;
		List<LoginAccess> schoollist=null;
		//String roleStatus = "";
		List<School> schoolslist=null;
		try{
				schoollist=new ArrayList<LoginAccess>();
				q=entitymanager.createQuery("from School ORDER BY createdDate desc");
				schoolslist=(List<School>)q.getResultList();
				if(schoolslist.size()>0){
					for(School school:schoolslist){
						loginaccess=new LoginAccess();
						loginaccess.setSchoolName(school.getName());
						loginaccess.setSchoolPhone(school.getPhoneNumber());
						loginaccess.setSchoolEmail(school.getEmailId());
						loginaccess.setSchoolAddress(school.getAddress());
						loginaccess.setUserStatus(school.getStatus());
						schoollist.add(loginaccess);
					}
				
				}
				else 
				{
					logger.debug("No School Name found in database !!!");
				}
		}catch(Exception e){
			logger.warn(" exception - "+e);
		}
		finally
		{
			 q=null;
			 schoolslist=null;
		}
		return schoollist;
	}

	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	@Override
	public String newschoolapproval(LoginAccess login) {
		String status="fail";
		//String roleStatus="";
		Query q=null;
		//Query personquery=null;
		int schoolid=0;
		int personid=0;
		int user_ID=0;
		String	password="";
		String encryptpassword="";
			try{
					 q=entitymanager.createQuery("from School where name=? ");
					 q.setParameter(1, login.getSchoolName());
					 List<School> loginlist=(List<School>)q.getResultList();
					 if(loginlist.size()>0){
						 if(loginlist.get(0).getStatus().equalsIgnoreCase("Waiting") || loginlist.get(0).getStatus().equalsIgnoreCase("Rejected") ){
						 schoolid=loginlist.get(0).getSchool_ID();
						 School log=entitymanager.find(School.class, schoolid);
						 log.setStatus("Active");
						 entitymanager.merge(log);
						Person person=new Person();
						person.setSchool(entitymanager.find(School.class, schoolid));
						person.setPersonRole("Admin");
						person.setPersonRoleNumber(loginlist.get(0).getName());
						person.setStatus("Active");
						if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
							person.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
							person.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
						}else{
							person.setCreatedDate(date);
							person.setCreatedTime(timestamp);
						}	
						entitymanager.persist(person);
						q=entitymanager.createQuery("from Person where school_ID=? and status='Active'");
						q.setParameter(1, schoolid);
						List<Person> personlist=(List<Person>)q.getResultList();
						if(personlist.size()>0){
							personid=personlist.get(0).getPerson_ID();
							password=RandomPasswordGenerator.GenerateSecurePassword(login.getSchoolName());
							login.setUserpassword(password);
							login.setUsername(loginlist.get(0).getName());
							login.setSchoolPhone(loginlist.get(0).getPhoneNumber());
							login.setSchoolEmail(loginlist.get(0).getEmailId());
							encryptpassword = PasswordEncryption.GeneratePaswword(login.getUserpassword());
							User user=new User();
							user.setUsername(loginlist.get(0).getName());
							user.setPerson(entitymanager.find(Person.class, personid));
							user.setStatus("Active");
							user.setPassword(encryptpassword);
							entitymanager.persist(user);
							Query insertMenu=null;
							insertMenu = entitymanager.createQuery("from User where username=?");
							insertMenu.setParameter(1, loginlist.get(0).getName());
							List<User> userList = (List<User>) insertMenu.getResultList();
							if (userList.size() > 0) {
								user_ID = userList.get(0).getHas_user_ID();
								String menuStatus = insertAdminMenus(user_ID);
								status="Approved";
							}
						}
					}else{
						schoolid=loginlist.get(0).getSchool_ID();
						 School log=entitymanager.find(School.class, schoolid);
						 log.setStatus("Active");
						 entitymanager.merge(log);
						q=entitymanager.createQuery("from Person where school_ID=?");
						q.setParameter(1, schoolid);
						List<Person> perlist=(List<Person>)q.getResultList();
						if(perlist.size()>0){
							for(int i=0;i<perlist.size();i++){
							personid=perlist.get(i).getPerson_ID();
							Person per=entitymanager.find(Person.class, personid);
							per.setStatus("Active");
							entitymanager.merge(per);
							q = entitymanager.createQuery("from User where person_ID=?");
							q.setParameter(1, personid);
							List<User> userlist=(List<User>)q.getResultList();
							if(userlist.size()>0){
								user_ID=userlist.get(0).getHas_user_ID();
								User user=entitymanager.find(User.class, user_ID);
								user.setStatus("Active");
								status="Success";
							}
						  }
						}
					}
				}
			}catch(Exception e){
				logger.warn(" exception - "+e);
			}
		return status;
	}

	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	@Override
	public String newschoolreject(LoginAccess login) {
		String status="Fail";
		Query q=null;
		int id=0;
		int personid=0;
		int user_ID=0;
		//String roleStatus="";
			try{
					q=entitymanager.createQuery("from School where name=?");
					q.setParameter(1, login.getSchoolName());
					List<School> list=(List<School>)q.getResultList();
					if(list.size()>0){
						id=list.get(0).getSchool_ID();
						if(list.get(0).getStatus().equalsIgnoreCase("Waiting")){
						School log=entitymanager.find(School.class, id);
						log.setStatus("Rejected");
						entitymanager.merge(log);
						status="Success";
						}else if(list.get(0).getStatus().equalsIgnoreCase("Active")){
							School log=entitymanager.find(School.class, id);
							log.setStatus("Deactive");
							entitymanager.merge(log);
							q=entitymanager.createQuery("from Person where school_ID=?");
							q.setParameter(1, id);
							List<Person> perlist=(List<Person>)q.getResultList();
							if(perlist.size()>0){
								for(int i=0;i<perlist.size();i++){
								 personid = perlist.get(i).getPerson_ID();
								Person per=entitymanager.find(Person.class, personid);
								per.setStatus("Deactive");
								entitymanager.merge(per);
								q = entitymanager.createQuery("from User where person_ID=?");
								q.setParameter(1, personid);
								List<User> userlist=(List<User>)q.getResultList();
								if(userlist.size()>0){
									user_ID=userlist.get(0).getHas_user_ID();
									User user=entitymanager.find(User.class, user_ID);
									user.setStatus("Deactive");
									status="Success";
								}
								}
							}	
						}
					}
			}catch(Exception e){
				logger.warn(" exception - "+e);
			}
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<School>  schooledit(LoginAccess login) {
		List<School> schoollist=null;
			Query q=null;
			try{
				q=entitymanager.createQuery("from School where name=?");
				q.setParameter(1, login.getSchoolName());
				 schoollist=(ArrayList<School>)q.getResultList();
		}catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return schoollist;
	}

	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	@Override
	public String schoolupdate(LoginAccess loginaccess) {
		Query q=null;
		int id=0;
		String status="Fail";
		try{
			q=entitymanager.createQuery("from School where name=?");
			q.setParameter(1, loginaccess.getSchoolName());
			List<School> schoollist=(List<School>)q.getResultList();
			if(schoollist.size()>0){
				id=schoollist.get(0).getSchool_ID();
				School login=entitymanager.find(School.class, id);
				login.setEmailId(loginaccess.getSchoolEmail());
				login.setAlternativeemail(loginaccess.getAlternativeemail());
				login.setPhoneNumber(loginaccess.getSchoolPhone());
				login.setLandlinenumber(loginaccess.getLandlinenumber());
				login.setContactpersonname(loginaccess.getUsername());
				login.setMobilenumber(loginaccess.getContactnumber());
				login.setAddress(loginaccess.getSchoolAddress());
				login.setCountry(loginaccess.getCountry());
				login.setState(loginaccess.getState());
				login.setLogopath(loginaccess.getFilepath());
				entitymanager.merge(login);
				status="Success";
			}
		}catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return status;
	}
	
	@Transactional(value = "transactionManager")
	@Override
	public String insertclass(String personID, String schoolID,
			LibrarianDataBean librarianDataBean) {
		String status="Fail";
		int schoolid;
		try{
			if(personID!=null){
				schoolid=Integer.parseInt(schoolID);
			Class clas=new Class();	
			clas.setClassName(librarianDataBean.getClassname());
			clas.setClassSection(librarianDataBean.getClasssection());
			clas.setStatus("Active");
			clas.setSchool(entitymanager.find(School.class, schoolid));
			entitymanager.persist(clas);
			entitymanager.flush();
			entitymanager.clear();
			status="Success";
			}
		}catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return status;
	}

	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	@Override
	public String insertsubject(String personID, String schoolID,
		LibrarianDataBean librarianDataBean) {
		String status="Fail";
		Query q=null;
		int schoolid;
		int classid=0;
		int subjectid=0;
		try{
			schoolid=Integer.parseInt(schoolID);
			q=entitymanager.createQuery("from Class where className=? and classSection=? and school_ID=? and status='Active'");
			q.setParameter(1, librarianDataBean.getName().split("/")[0]);
			q.setParameter(2, librarianDataBean.getName().split("/")[1]);
			q.setParameter(3, schoolid);
			List<Class> classlist=(List<Class>)q.getResultList();
			if(classlist.size()>0){
				classid=classlist.get(0).getClass_ID();
				q=entitymanager.createQuery("from Subject where subjectCode=? and school_ID=? and status='Active'");
			/*	q.setParameter(1, librarianDataBean.getSubjectname());*/
				q.setParameter(1, librarianDataBean.getSubjectcode());
				q.setParameter(2, schoolid);
				List<Subject> subjectlist=(List<Subject>)q.getResultList();
				if(subjectlist.size()>0){
					subjectid=subjectlist.get(0).getSubject_ID();
					ClassSubject clasub=new ClassSubject();
					clasub.setClazz(entitymanager.find(Class.class, classid));
					clasub.setSubject(entitymanager.find(Subject.class, subjectid));
					clasub.setStatus("Active");
					entitymanager.persist(clasub);
					entitymanager.flush();
					entitymanager.clear();
					status="Success";
				}else{
					Subject sub=new Subject();
					sub.setSubjectCode(librarianDataBean.getSubjectcode());
					sub.setSujectName(librarianDataBean.getSubjectname());
					sub.setStatus("Active");
					sub.setSchool(entitymanager.find(School.class, schoolid));
					entitymanager.persist(sub);
					entitymanager.flush();
					entitymanager.clear();					
					q=entitymanager.createQuery("from Subject where subjectCode=? and school_ID=? and status='Active'");
					/*q.setParameter(1, librarianDataBean.getSubjectname());*/
					q.setParameter(1, librarianDataBean.getSubjectcode());
					q.setParameter(2, schoolid);
					List<Subject> subjectlists=(List<Subject>)q.getResultList();
					if(subjectlists.size()>0){
						subjectid=subjectlists.get(0).getSubject_ID();
						ClassSubject clasub=new ClassSubject();
						clasub.setClazz(entitymanager.find(Class.class, classid));
						clasub.setSubject(entitymanager.find(Subject.class, subjectid));
						clasub.setStatus("Active");
						entitymanager.persist(clasub);
						entitymanager.flush();
						entitymanager.clear();
						status="Success";
					}
				}
			}
		}catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return status;
	}
	@Override
	public List<String> getmenus() {
		Query q=null;
		List<String> menulist=null;
		try{
			menulist=new ArrayList<String>();
			q=entitymanager.createQuery("from Product where status='Active'");
			List<Product> prolist=(List<Product>)q.getResultList();
			if(prolist.size()>0){
				for(int i=0;i<prolist.size();i++){
					String menuname=prolist.get(i).getProductName();
					menulist.add(menuname);
					HashSet<String> dublicate=new HashSet<String>();
					dublicate.addAll(menulist);
					menulist.clear();
					menulist.addAll(dublicate);
					menulist.removeAll(Collections.singleton("Certificate"));
				}
				
			}
		}catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return menulist;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String validatecalss(LibrarianDataBean librarianDataBean,String schoolID) {
		String status="fail";
		try
		{
		Query q=null;
		int schoolid=Integer.parseInt(schoolID);
		q=entitymanager.createQuery("from Class where  className=? and classSection=? and school_ID=? and status='Active'");
		q.setParameter(1, librarianDataBean.getClassname());
		q.setParameter(2, librarianDataBean.getClasssection());
		q.setParameter(3, schoolid);
		List<Class> classlist=(ArrayList<Class>)q.getResultList();
		if(classlist.size()>0)
		{
			status="exsist";
		}
		else
		{
			status="success";
		}
			
		}
		catch(Exception e)
		{
			
		}
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String validatesubject(LibrarianDataBean librarianDataBean,String schoolID) {
		int id=0;
		int subid=0;
		Query q=null;
	    String status="fail";
		try
		{ 
			logger.debug("check subname"+librarianDataBean.getName());
			int schoolid=Integer.parseInt(schoolID);
			q=entitymanager.createQuery("from Class where className=? and classSection=? and school_ID=? and status='Active'");
			q.setParameter(1, librarianDataBean.getName().split("/")[0]);
			q.setParameter(2, librarianDataBean.getName().split("/")[1]);
			q.setParameter(3, schoolid);
			List<Class> classlist=(ArrayList<Class>)q.getResultList();
			if(classlist.size()>0)
			{
			q=null;
			id=classlist.get(0).getClass_ID();
			logger.debug("check class id"+id);
			q=entitymanager.createQuery("from ClassSubject where class_ID=? and status=?");
		    q.setParameter(1,id);
		    q.setParameter(2,"Active");
			List<ClassSubject> classsubject=(ArrayList<ClassSubject>)q.getResultList();
			if(classsubject.size()>0)
			{
			q=null;
			int count=0;
			for(int i=0;classsubject.size()>i;i++)
			{
				logger.debug("check subject name"+classsubject.get(i).getSubject().getSujectName());
				if(classsubject.get(i).getSubject().getSubjectCode().equalsIgnoreCase(librarianDataBean.getSubjectcode()))
					{
					logger.debug("chek inside");
					count++;
					logger.debug("check countttt"+count);
					}
				 }
			if(count>0)
			{
				logger.debug("ibside exist");
				status="exist";
			}
			else
			{
				logger.debug("ibside success");
				status="success";	
			}
			}
			else
			{
				status="success";
			}
		}
		}
		
		catch(Exception e)
		{
			logger.warn(" exception - "+e);
		}
		return status;
	}

	/*prema begin 01/11/2016*/
	
	public List<LibrarianDataBean> classlistview(String personID,LibrarianDataBean librarianDataBean) {
		logger.debug("----------inside classlistview---------");
		Query q = null;
		List<LibrarianDataBean> classlist = new ArrayList<LibrarianDataBean>();
		List<Person> roleStatus = null;int schoolID=0;
		try {
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				if (roleStatus.size() > 0) {
						schoolID=roleStatus.get(0).getSchool().getSchool_ID();
						q = entitymanager.createQuery("from Class where school_ID=? and status='Active'");
						q.setParameter(1, schoolID);
						List<Class> classlists = (List<Class>) q.getResultList();
						logger.debug("--------list size-----" + classlists.size());
						if (classlists.size() > 0) {
							int sno = 1;
							for (int i = 0; i < classlists.size(); i++) {
								LibrarianDataBean obj = new LibrarianDataBean();
								obj.setSno((String.valueOf(sno)));
								obj.setClassname(classlists.get(i).getClassName());
								obj.setClasssection(classlists.get(i).getClassSection());
								obj.setClassid(String.valueOf(classlists.get(i).getClass_ID()));
								classlist.add(obj);
								sno++;
							}
						}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return classlist;
	}

	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	public String editclasssec(String personID, LibrarianDataBean librarianDataBean) {
		logger.debug("----------inside editclasssec---------");
		Query q = null; 
		List<Person> roleStatus = null;String no;String status = "Fail";
		try {
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				if (roleStatus.size() > 0) {
						int schoolID=roleStatus.get(0).getSchool().getSchool_ID();
						int classids=Integer.parseInt(librarianDataBean.getClassid());
						q = entitymanager.createQuery("from Class where className=? and classSection=? and school_ID=? and status='Active'");
						q.setParameter(1, librarianDataBean.getClassname());
						q.setParameter(2, librarianDataBean.getClasssection());
						q.setParameter(3, schoolID);
						List<Class> classlist = (List<Class>) q.getResultList();
						logger.debug("-------list size-----" + classlist.size());
						if (classlist.size() > 0) {
							status="Fail";
						}else{
							logger.debug("------classid-------" + classids);
							Class obj = entitymanager.find(Class.class, classids);
							obj.setClassName(librarianDataBean.getClassname());
							obj.setClassSection(librarianDataBean.getClasssection());
							obj.setStatus("Active");
							entitymanager.merge(obj);
							status = "Success";
						}
				}
			}
		} catch (Exception e) {
			logger.warn(" exception - "+e);
		}
		return status;
	}
	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	public String deleteclasssec(String personID,LibrarianDataBean librarianDataBean) {
		logger.debug("----------inside deleteclasssec---------");
		Query q = null; 
		List<Person> roleStatus = null;int classids=0;int classidss=0;String status = "Fail";
		try {
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				if (roleStatus.size() > 0) {
						//int schoolID=roleStatus.get(0).getSchool().getSchool_ID();
						int classid=Integer.parseInt(librarianDataBean.getClassid());
							Class obj = entitymanager.find(Class.class, classid);
							obj.setStatus("De-Active");
							entitymanager.merge(obj);
							status="Success";
							q=entitymanager.createQuery("from ClassSubject where class_ID=? and status='Active'");
							q.setParameter(1, classid);
							List<ClassSubject> classsubjectlist=(List<ClassSubject>)q.getResultList();
							if(classsubjectlist.size()>0){
								for (int i = 0; i < classsubjectlist.size(); i++) {
									classids=classsubjectlist.get(i).getClazz().getClass_ID();
									ClassSubject obj1=entitymanager.find(ClassSubject.class, classids);
									obj1.setStatus("De-Active");
									entitymanager.merge(obj1);
									entitymanager.flush();
									entitymanager.clear();
								}
								q=null;
								q=entitymanager.createQuery("from TeacherClass where class_ID=? and status='Active'");
								q.setParameter(1, classid);
								List<TeacherClass> teacherclass=(List<TeacherClass>)q.getResultList();
								if(teacherclass.size()>0){
									for (int i = 0; i < teacherclass.size(); i++) {
										classidss=teacherclass.get(i).getHasteacher_ID();
										TeacherClass obj2=entitymanager.find(TeacherClass.class, classidss);
										obj2.setStatus("De-Active");
										entitymanager.merge(obj2);
										entitymanager.flush();
										entitymanager.clear();
									}
								}
								status="Success";
						}
				}
			}
		} catch (Exception e) {
			logger.warn(" exception - "+e);
		}
		return status;
	}
	@SuppressWarnings("unchecked")
	public List<LibrarianDataBean> subjectlistview(String personID,LibrarianDataBean librarianDataBean) {
		Query q = null;
		List<LibrarianDataBean> subjectlist = new ArrayList<LibrarianDataBean>();
		List<Person> roleStatus = null;int schoolID=0; //int subjectids = 0;
		try {
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				if (roleStatus.size() > 0) {
						schoolID=roleStatus.get(0).getSchool().getSchool_ID();
						StringTokenizer st = new StringTokenizer(librarianDataBean.getClassname());
						String className = st.nextToken("/");
						String sectionName = librarianDataBean.getClassname();
						String section = sectionName.substring(sectionName.lastIndexOf("/") + 1);
						q = entitymanager.createQuery("from Class where className=? and classSection=? and school_ID=? and status='Active'");
						q.setParameter(1, className);
						q.setParameter(2, section);
						q.setParameter(3, schoolID);
						List<Class> classlist = (List<Class>) q.getResultList();
						logger.debug("--------list size-----" + classlist.size());
						if (classlist.size() > 0) {
							q=null;
							q = entitymanager.createQuery("from ClassSubject where class_ID=? and status='Active'");
							q.setParameter(1, classlist.get(0).getClass_ID());
							List<ClassSubject> classsubjectlist=(List<ClassSubject>)q.getResultList();
							if(classsubjectlist.size()>0){
								for (int i = 0; i < classsubjectlist.size(); i++) {
									q=null;
									q = entitymanager.createQuery("from Subject where subject_ID=? and school_ID=? and status='Active'");
									q.setParameter(1, classsubjectlist.get(i).getSubject().getSubject_ID());
									q.setParameter(2, schoolID);
									List<Subject> subjectlists=(List<Subject>)q.getResultList();
									if(subjectlists.size()>0){
										for (int j = 0; j < subjectlists.size(); j++) {
											LibrarianDataBean obj = new LibrarianDataBean();
											obj.setSubjectname(subjectlists.get(j).getSujectName());
											obj.setSubjectcode(subjectlists.get(j).getSubjectCode());
											obj.setSubid(String.valueOf(subjectlists.get(j).getSubject_ID()));
											subjectlist.add(obj);
										}
									}
								}
							}								
						}
					}
			}
		} catch (Exception e) {
			logger.warn(" exception - "+e);
		}
		return subjectlist;
	}
	@Transactional(value = "transactionManager")
	public String updatesujectname(String personID,LibrarianDataBean librarianDataBean) {
		logger.debug("----------inside updatesujectname---------");
		Query q = null; 
		List<Person> roleStatus = null;String no;String status = "Fail";
		try {
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				if (roleStatus.size() > 0) {
						int schoolID=roleStatus.get(0).getSchool().getSchool_ID();
						int subjectid=Integer.parseInt(librarianDataBean.getSubid());
						q = entitymanager.createQuery("from Subject where sujectName=? and subjectCode=? and school_ID=? and status='Active'");
						q.setParameter(1, librarianDataBean.getSubjectname());
						q.setParameter(2, librarianDataBean.getSubjectcode());
						q.setParameter(3, schoolID);
						List<Subject> subjectlist = (List<Subject>) q.getResultList();
						logger.debug("-------list size-----" + subjectlist.size());
						if (subjectlist.size() > 0) {
							status="Fail";
						}else{
							logger.debug("------subject id-------" + subjectid);
							Subject obj = entitymanager.find(Subject.class, subjectid);
							obj.setSujectName(librarianDataBean.getSubjectname());
							obj.setSubjectCode(librarianDataBean.getSubjectcode());
							obj.setStatus("Active");
							entitymanager.merge(obj);
							status = "Success";
						}
				}
			}
		} catch (Exception e) {
			logger.warn(" exception - "+e);
		}
		return status;
	}
	@Transactional(value = "transactionManager")
	public String deletesubname(String personID, LibrarianDataBean librarianDataBean) {
		logger.debug("----------inside deletesubname---------");
		Query q = null; 
		List<Person> roleStatus = null;String status = "Fail";int subjectids=0;int subjectidss=0;
		try {
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				if (roleStatus.size() > 0) {
						int schoolID=roleStatus.get(0).getSchool().getSchool_ID();
						int subjectid=Integer.parseInt(librarianDataBean.getSubid());
							Subject obj = entitymanager.find(Subject.class, subjectid);
							obj.setStatus("De-Active");
							entitymanager.merge(obj);
							status="Success";
							q=entitymanager.createQuery("from ClassSubject where subject_ID=? and status='Active'");
							q.setParameter(1, subjectid);
							List<ClassSubject> classsubjectlist=(List<ClassSubject>)q.getResultList();
							if(classsubjectlist.size()>0){
								for (int i = 0; i < classsubjectlist.size(); i++) {
									subjectids=classsubjectlist.get(i).getHasClass_ID();
									ClassSubject obj1=entitymanager.find(ClassSubject.class, subjectids);
									obj1.setStatus("De-Active");
									entitymanager.merge(obj1);
									entitymanager.flush();
									entitymanager.clear();
								}
								q=null;
								q=entitymanager.createQuery("from TeacherSubject where subject_ID=? and school_ID=? and status='Active'");
								q.setParameter(1, subjectid);
								q.setParameter(2, schoolID);
								List<TeacherSubject> teachersubject=(List<TeacherSubject>)q.getResultList();
								if(teachersubject.size()>0){
									for (int i = 0; i < teachersubject.size(); i++) {
										subjectidss=teachersubject.get(i).getHasteacher_subject_ID();
										TeacherSubject obj2=entitymanager.find(TeacherSubject.class, subjectidss);
										obj2.setStatus("De-Active");
										entitymanager.merge(obj2);
										entitymanager.flush();
										entitymanager.clear();
									}
								}
							status="Success";
						}
				}
			}
		} catch (Exception e) {
			logger.warn(" exception - "+e);
		}
		return status;
	}
	/*prema End 01/11/2016*/
	
	@Transactional(value="transactionManager")
	@Override
	public String feessave(String personID, PaymentDatabean paymentDatabean) {
		logger.debug("payment----"+paymentDatabean.getPayclass());
		String status="Fail";
		int schoolid=0;
		String schoolname="";
		List<Person> roleStatus=null;
		if(personID!=null){
			try{
				roleStatus=new ArrayList<Person>();
				roleStatus = getRollType(personID);
				if(roleStatus.size()>0){
					schoolid=roleStatus.get(0).getSchool().getSchool_ID();
					schoolname=roleStatus.get(0).getSchool().getName();
					FeesDetail fees=new FeesDetail();
					fees.setTitle(paymentDatabean.getTitle());
					fees.setAmount(paymentDatabean.getAmount());
					fees.setDuedate(paymentDatabean.getDuedate());
					fees.setDescription(paymentDatabean.getDescription());
					fees.setClassName(paymentDatabean.getPayclass());
					fees.setClassName(paymentDatabean.getPayClassSection());
					fees.setStudent_ID(paymentDatabean.getPaymentStudID());
					fees.setSchoolId(entitymanager.find(School.class, schoolid));
					if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
						fees.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
					}else{
						fees.setCreatedDate(date);
					}	
					fees.setStatus("Active");
					entitymanager.persist(fees);
					getmailphones(paymentDatabean,schoolid,schoolname);
					logger.debug("fdsfshjfdf"+paymentDatabean.getMailId());
					status="Success";
				}
			}catch(Exception e){
				logger.error("Exception -->"+e.getMessage());
			}
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	private void getmailphones(PaymentDatabean paymentDatabean, int schoolid,String schoolname) {
		Query q=null;
		int studentid=0;
		int classid=0;
		try{
		if(paymentDatabean.getOptions().equalsIgnoreCase("All")){
			q=null;
			q=entitymanager.createQuery("from Class where school_ID=? and status='Active'");
			q.setParameter(1, schoolid);
			List<Class> list=(List<Class>)q.getResultList();
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					classid=list.get(i).getClass_ID();
					q=entitymanager.createQuery("from StudentClass where class_ID=? and status='Active'");
					q.setParameter(1, classid);
					List<StudentClass> stuclalist=(List<StudentClass>)q.getResultList();
				if(stuclalist.size()>0){
				studentid=stuclalist.get(0).getStudent().getStudent_ID();
				q=entitymanager.createQuery("from StudentDetail where student_ID=? ");
				q.setParameter(1, studentid);
				List<StudentDetail> detaillist=(List<StudentDetail>)q.getResultList();
				if(detaillist.size()>0){
					paymentDatabean.setPhoneno(detaillist.get(0).getPhoneNumber());
					paymentDatabean.setMailId(detaillist.get(0).getEmailId());
				}
				q = entitymanager.createQuery("from StudentParent where student_ID=?");
				q.setParameter(1, studentid);
				List<StudentParent> studentParent = (List<StudentParent>) q.getResultList();
				if (studentParent.size() > 0) {
					paymentDatabean.setMailId(studentParent.get(0).getParent().getParentDetail().getEmaiId());
					paymentDatabean.setPhoneno(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
				}
				}
				//MailSendJDBC.feesSave(paymentDatabean,schoolname,schoolid);
			}
			}
		}else if(paymentDatabean.getOptions().equalsIgnoreCase("Class")){
			q=null;
			q=entitymanager.createQuery("from Class where className=? and school_ID=? and status='Active'");
			q.setParameter(1, paymentDatabean.getPayclass());
			q.setParameter(2, schoolid);
			List<Class> classlist=(List<Class>)q.getResultList();
			if(classlist.size()>0){
				for(int k=0;k<classlist.size();k++){
				classid=classlist.get(k).getClass_ID();
				q=null;
				q=entitymanager.createQuery("from StudentClass where class_ID=? and status='Active'");
				q.setParameter(1, classid);
				List<StudentClass> stuclalist=(List<StudentClass>)q.getResultList();
				if(stuclalist.size()>0){
				studentid=stuclalist.get(0).getStudent().getStudent_ID();
				q=entitymanager.createQuery("from StudentDetail where student_ID=? ");
				q.setParameter(1, studentid);
				List<StudentDetail> detaillist=(List<StudentDetail>)q.getResultList();
				if(detaillist.size()>0){
					paymentDatabean.setPhoneno(detaillist.get(0).getPhoneNumber());
					paymentDatabean.setMailId(detaillist.get(0).getEmailId());
				}
				q = entitymanager.createQuery("from StudentParent where student_ID=?");
				q.setParameter(1, studentid);
				List<StudentParent> studentParent = (List<StudentParent>) q.getResultList();
				if (studentParent.size() > 0) {
					paymentDatabean.setMailId(studentParent.get(0).getParent().getParentDetail().getEmaiId());
					paymentDatabean.setPhoneno(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
				}
			}
				//MailSendJDBC.feesSave(paymentDatabean,schoolname,schoolid);
		  }
		}
		}else if(paymentDatabean.getOptions().equalsIgnoreCase("Student")){
			q = null;
			q = entitymanager.createQuery("from Student where rollNumber=? and school_ID=?");
			q.setParameter(1, paymentDatabean.getPaymentStudID());
			q.setParameter(2, schoolid);
			List<Student> student = (List<Student>) q.getResultList();
			if (student.size() > 0) {
				q = entitymanager.createQuery("from StudentParent where student_ID=?");
				q.setParameter(1, student.get(0).getStudent_ID());
				List<StudentParent> studentParent = (List<StudentParent>) q.getResultList();
				if (studentParent.size() > 0) {
					paymentDatabean.setMailId(studentParent.get(0).getParent().getParentDetail().getEmaiId());
					paymentDatabean.setPhoneno(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
				}
				//MailSendJDBC.feesSave(paymentDatabean,schoolname,schoolid);
			}
		}
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}
	}
	
/*teacher global*/
	@Transactional(value="transactionManager")
	@Override
	public String saveAttendents(TeacherDataBean teacherDataBean) {
		String status="Fail";
		Query q = null;
		List<Person> rollStatus = null;
		int school_ID = 0;
		int teacher_ID = 0;
		//SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss a");
		try {
			rollStatus = new ArrayList<Person>();
			rollStatus = getRollType(teacherDataBean.getPerson_ID());
			if (rollStatus.size()>0) {
				school_ID = rollStatus.get(0).getSchool().getSchool_ID();
				teacher_ID=getTeacherID(teacherDataBean.getTeaID(), school_ID);
			
						q = entitymanager.createQuery("from TeacherAttendance where date=? and teacher_id=? and school_ID=?");
						q.setParameter(1,teacherDataBean.getAttendentsDate());
						q.setParameter(2,teacher_ID);
						q.setParameter(3,teacherDataBean.getSchoolID());
						List<TeacherAttendance> detaillist=(List<TeacherAttendance>)q.getResultList();
						if (detaillist.size()>0) {
							TeacherAttendance teacherAttendance = entitymanager.find(TeacherAttendance.class, detaillist.get(0).getTeacherAttendanceId());
							teacherAttendance.setStatus(teacherDataBean.getAttendentsStatus());
							if (teacherDataBean.getAttendentsStatus().equalsIgnoreCase("Present") || teacherDataBean.getLeaveType().equalsIgnoreCase("Half day") ) {
								teacherAttendance.setInTime(teacherDataBean.getInTime());
								teacherAttendance.setOut_Time(teacherDataBean.getOutTime());
							}
							else{
								teacherAttendance.setInTime("");
								teacherAttendance.setOut_Time("");
							}
							
							if (teacherDataBean.getAttendentsStatus().equalsIgnoreCase("Absent")) {
								teacherAttendance.setLeaveType(teacherDataBean.getLeaveType());
							}
							else{
								teacherAttendance.setLeaveType("");
							}
							teacherAttendance.setLeaveType(teacherDataBean.getLeaveType());
							teacherAttendance.setDate(teacherDataBean.getAttendentsDate());
							teacherAttendance.setUpdatedDate(new Date());
							teacherAttendance.setLunch_Break(1);
							teacherAttendance.setSchool(entitymanager.find(School.class, teacherDataBean.getSchoolID()));
							logger.debug("------------------teacher_ID-------------->"+teacher_ID);
							
							teacherAttendance.setTeacher(entitymanager.find(Teacher.class, teacher_ID));
							entitymanager.merge(teacherAttendance);
						}
						else{
							TeacherAttendance teacherAttendance = new TeacherAttendance();
							teacherAttendance.setStatus(teacherDataBean.getAttendentsStatus());
							if (teacherDataBean.getAttendentsStatus().equalsIgnoreCase("Present") || teacherDataBean.getLeaveType().equalsIgnoreCase("Half day") ) {
								teacherAttendance.setInTime(teacherDataBean.getInTime());
								teacherAttendance.setOut_Time(teacherDataBean.getOutTime());
							}
							else{
								teacherAttendance.setInTime("");
								teacherAttendance.setOut_Time("");
							}
							if (teacherDataBean.getAttendentsStatus().equalsIgnoreCase("Absent")) {
								teacherAttendance.setLeaveType(teacherDataBean.getLeaveType());
							}
							else{
								teacherAttendance.setLeaveType("");
							}
							teacherAttendance.setLeaveType(teacherDataBean.getLeaveType());
							teacherAttendance.setDate(teacherDataBean.getAttendentsDate());
							teacherAttendance.setCreatedDate(new Date());
							teacherAttendance.setUpdatedDate(new Date());
							teacherAttendance.setLunch_Break(2);
						logger.debug("--------------teacher_ID----------------->>>>>>>>>>>>"+teacher_ID);
						teacherAttendance.setSchool(entitymanager.find(School.class, teacherDataBean.getSchoolID()));
							teacherAttendance.setTeacher(entitymanager.find(Teacher.class, teacher_ID));
							entitymanager.persist(teacherAttendance);
						}
			}
			status="Success";
		} catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return status;
	}

	@Override
	public List<TeacherDataBean> getAttendentsDetail(TeacherDataBean teacherDataBean) {
		Query q=null;
		List<TeacherDataBean> teacherdatabeanlist=null;
		List<Person> rollStatus = null;
		int school_ID = 0;
		int teacher_ID = 0;
		
	  // SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm:ss a");
	     
		try {
			logger.debug("value chk------Currentmonths------>"+teacherDataBean.getCurrentmonths()+"---year-->+"+teacherDataBean.getCurrentyear());
			rollStatus = new ArrayList<Person>();
			teacherdatabeanlist= new ArrayList<TeacherDataBean>();
			rollStatus = getRollType(teacherDataBean.getPerson_ID());
			if (rollStatus.size()>0) {
				school_ID = rollStatus.get(0).getSchool().getSchool_ID();
				teacher_ID=getTeacherID(teacherDataBean.getTeaID(), school_ID);
					logger.debug("====teacher_ID=====>"+teacher_ID);
						q = entitymanager.createQuery("from TeacherAttendance where MONTH(date) = ? and YEAR(date) = ? and teacher_id=?");
						q.setParameter(1,teacherDataBean.getCurrentmonths());
						q.setParameter(2,teacherDataBean.getCurrentyear());
						q.setParameter(3,teacher_ID);
						List<TeacherAttendance> detaillist=(List<TeacherAttendance>)q.getResultList();
						logger.debug("detaillist size check---->"+detaillist.size());
						if (detaillist.size()>0) {
						for (int i = 0; i < detaillist.size(); i++) {
							TeacherDataBean teacherdata=new TeacherDataBean();
							teacherdata.setAttendentsDate(detaillist.get(i).getDate());
							teacherdata.setAttendentsStatus(detaillist.get(i).getStatus());
							if(detaillist.get(i).getStatus().equalsIgnoreCase("Present")||detaillist.get(i).getLeaveType().equalsIgnoreCase("Half day")){
							logger.debug(new SimpleDateFormat("HH:MM:SS a").parse(detaillist.get(i).getInTime()));
								teacherdata.setInTime(detaillist.get(i).getInTime());
								teacherdata.setOutTime(detaillist.get(i).getOut_Time());
								/*teacherdata.setInTime(displayFormat.format(teacherdata.getInTime()));*/
							}
							
							teacherdata.setLeaveType(detaillist.get(i).getLeaveType());
							teacherdata.setLanuchBreak(String.valueOf(detaillist.get(i).getLunch_Break()));
							teacherdatabeanlist.add(teacherdata);
						}
						}
			logger.debug("----------->"+teacherdatabeanlist.size());
			}
		} catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return teacherdatabeanlist;
	}

	@Transactional(value="transactionManager")
	@Override
	public String saveNotes(TeacherDataBean teacherDataBean) {
		String status="Fail";
		Query q = null;
		List<Person> rollStatus = null;
		int school_ID = 0;
		int teacher_ID = 0;
		try {
			rollStatus = new ArrayList<Person>();
			rollStatus = getRollType(teacherDataBean.getPerson_ID());
			if (rollStatus.size()>0) {
				school_ID  = rollStatus.get(0).getSchool().getSchool_ID();
				teacher_ID = getTeacherID(teacherDataBean.getTeaID(), school_ID);
						q = entitymanager.createQuery("from TeacherNote where notesTitle=? ");
						q.setParameter(1,teacherDataBean.getNotesHeading());
						List<TeacherAttendance> detaillist=(List<TeacherAttendance>)q.getResultList();
						if (detaillist.size()>0) {
							status="Exist";
						}
						else{
							TeacherNote teacherNote= new TeacherNote();
							teacherNote.setNotesTitle(teacherDataBean.getNotesHeading());
							teacherNote.setNotes(teacherDataBean.getNotes());
							teacherNote.setDate(teacherDataBean.getNotesDate());
							teacherNote.setCreatedDate(new Date());
							teacherNote.setUpdatedDate(new Date());
							teacherNote.setStatus("Active");
							logger.debug("--------------teacher_ID------------------>>>>>>>>>>>>"+teacher_ID);
							teacherNote.setTeacher(entitymanager.find(Teacher.class, teacher_ID));
							entitymanager.persist(teacherNote);
							status="Success";
						}
			}
			
		} catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return status;
	}

	@Override
	public List<TeacherDataBean> getNotesDetail(TeacherDataBean teacherDataBean) {
		Query q=null;
		List<TeacherDataBean> teacherdatabeanlist=null;
		List<Person> rollStatus = null;
		int school_ID = 0;
		int teacher_ID = 0;
		try {
			logger.debug("value check-----getPerson_ID-->"+teacherDataBean.getPerson_ID()+"---getNotesHeading-->"+teacherDataBean.getNotesHeading());
			rollStatus = new ArrayList<Person>();
			teacherdatabeanlist= new ArrayList<TeacherDataBean>();
			rollStatus = getRollType(teacherDataBean.getPerson_ID());
			if (rollStatus.size()>0) {
				school_ID = rollStatus.get(0).getSchool().getSchool_ID();
				teacher_ID=getTeacherID(teacherDataBean.getTeaID(), school_ID);
				logger.debug("=====teacher_ID====>"+teacher_ID);
						q = entitymanager.createQuery("from TeacherNote where status=? and teacher_id=?");
						q.setParameter(1,"Active");
						q.setParameter(2,teacher_ID);
						List<TeacherNote> detaillist=(List<TeacherNote>)q.getResultList();
						logger.debug("detaillist size check---->"+detaillist.size());
						if (detaillist.size()>0) {
						for (int i = 0; i < detaillist.size(); i++) {
							TeacherDataBean teacherdata=new TeacherDataBean();
							teacherdata.setNotesDate(detaillist.get(i).getDate());
							teacherdata.setNotesHeading(detaillist.get(i).getNotesTitle());
							teacherdata.setNotes(detaillist.get(i).getNotes());
							teacherdata.setCode(String.valueOf(detaillist.get(i).getTeacherNotesId()));
							teacherdatabeanlist.add(teacherdata);
						}
					}
			logger.debug("-------teacherdatabeanlist.size---->"+teacherdatabeanlist.size());
			}
		} catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return teacherdatabeanlist;
	}
	@Transactional(value="transactionManager")
	@Override
	public String updateNotes(TeacherDataBean teacherDataBean) {
		String status="Fail";
		Query q = null;
		try {
							TeacherNote teachernotes = entitymanager.find(TeacherNote.class, Integer.parseInt(teacherDataBean.getCode()));
							teachernotes.setDate(teacherDataBean.getNotesDate());
							teachernotes.setNotes(teacherDataBean.getNotes());
							teachernotes.setNotesTitle(teacherDataBean.getNotesHeading());
							teachernotes.setUpdatedDate(new Date());
							entitymanager.merge(teachernotes);
							status="Success";
		} catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return status;
	}
	@Transactional(value="transactionManager")
	@Override
	public String deleteNotes(TeacherDataBean teacherDataBean) {
		String status="Fail";
		//Query q = null;
		try {
							TeacherNote teachernotes = entitymanager.find(TeacherNote.class, Integer.parseInt(teacherDataBean.getCode()));
							teachernotes.setStatus("Deactive");
							entitymanager.merge(teachernotes);
							status="Success";
		} catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return status;
	}
	
	
/*Student*/
	@Override
	@Transactional(value = "transactionManager")
	public String saveActivity(StudentDataBean studentDataBean) {
		String status="Fail";
		try{
			ExtraCurricularActivity extra=new ExtraCurricularActivity();
			extra.setActivity(studentDataBean.getStuActivity());
			extra.setStime(studentDataBean.getStime());
			extra.setEtime(studentDataBean.getEtime());
			extra.setClass_section(studentDataBean.getStuCls());
			extra.setStudent_ID(studentDataBean.getStuRollNo());
			extra.setSchool(entitymanager.find(School.class, studentDataBean.getSchool_id()));
			extra.setStatus("Active");
			entitymanager.persist(extra);
			status="Success";
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return status;
	}

	@Override
	public List<StudentDataBean> getActivity(StudentDataBean studentDataBean) {
		Query q=null;
		List<StudentDataBean> actList=null;
		try{
			actList=new ArrayList<StudentDataBean>();
			q=entitymanager.createQuery("from ExtraCurricularActivity where student_ID=? and status=?");
			q.setParameter(1, studentDataBean.getStuRollNo());
			q.setParameter(2, "Active");
			List<ExtraCurricularActivity> act=(List<ExtraCurricularActivity>)q.getResultList();
			if(act.size()>0){
				for (int i = 0; i < act.size(); i++) {
					StudentDataBean stud=new StudentDataBean();
					stud.setStuActivity(act.get(i).getActivity());
					stud.setEtime(act.get(i).getEtime());
					stud.setStime(act.get(i).getStime());
					stud.setAct_pid(act.get(i).getActivity_id());
					actList.add(stud);
				}
			}
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return actList;
	}
	
	@Transactional(value = "transactionManager")
	public String updateActivity(StudentDataBean studentDataBean) {
		String status="Fail";
		try{
			ExtraCurricularActivity extra=entitymanager.find(ExtraCurricularActivity.class, studentDataBean.getAct_pid());
			extra.setActivity(studentDataBean.getStuActivity());
			extra.setStime(studentDataBean.getStime());
			extra.setEtime(studentDataBean.getEtime());
			extra.setClass_section(studentDataBean.getStuCls());
			extra.setStudent_ID(studentDataBean.getStuRollNo());
			extra.setSchool(entitymanager.find(School.class, studentDataBean.getSchool_id()));
			extra.setStatus("Active");
			entitymanager.merge(extra);
			status="Success";
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return status;
	}

	@Transactional(value = "transactionManager")
	public String saveAddActivity(StudentDataBean studentDataBean) {
		String status="Fail";
		try{
			Activities activities=new Activities();
			activities.setActivity(studentDataBean.getAddActivity());
			activities.setSchool(entitymanager.find(School.class, studentDataBean.getSchool_id()));
			activities.setStatus("Active");
			entitymanager.persist(activities);
			status="Success";
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return status;
	}
	
	@Override
	public List<String> getActivitieslist(StudentDataBean studentDataBean) {
		Query q=null;
		List<String> actList=null;
		try{
			actList=new ArrayList<String>();
			q=entitymanager.createQuery("select activity from Activities where school_ID=? and status=?");
			q.setParameter(1, studentDataBean.getSchool_id());
			q.setParameter(2, "Active");
			actList=(List<String>)q.getResultList();
			
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return actList;
	}
	
	@Transactional(value = "transactionManager")
	public String deleteActivity(StudentDataBean studentDataBean) {
		//Query q=null;
		String status="Fail";
		try{
			ExtraCurricularActivity extra=entitymanager.find(ExtraCurricularActivity.class, studentDataBean.getAct_pid());
			extra.setStatus("DeActive");
			entitymanager.merge(extra);
			status="Success";
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return status;
	}
	
	@Override
	public List<StudentDataBean> getstudentbehaviourList(StudentDataBean studentDataBean) {
		Query q=null;int studid1=0;
		List<StudentDataBean> studlist2=null;
		try{
			studlist2=new ArrayList<StudentDataBean>();
			q = null;
			q = entitymanager.createQuery("from Student where roll_number=? and school_ID=? and status='Active'");
			q.setParameter(1, studentDataBean.getStuRollNo());
			q.setParameter(2, studentDataBean.getSchool_id());
			List<Student> studentlist = (List<Student>) q.getResultList();
			if (studentlist.size() > 0) {
				studid1 = studentlist.get(0).getStudent_ID();
				q = null;
				q = entitymanager.createQuery("from StudentDetail where student_ID=?");
				q.setParameter(1, studid1);
				List<StudentDetail> studlist = (List<StudentDetail>) q.getResultList();
				q = null;
				q = entitymanager.createQuery("from StudentPerformance where student_ID=? and status='Active'");
				q.setParameter(1, studid1);
				List<StudentPerformance> perlist2 = (List<StudentPerformance>) q.getResultList();
				if (studentlist.size() > 0 && studlist.size() > 0 && perlist2.size() > 0) {
					for (int s = 0; s < perlist2.size(); s++) {
						StudentDataBean stud=new StudentDataBean();
						stud.setPerformance_pid(perlist2.get(s).getPerformance_ID());
						stud.setBdate(perlist2.get(s).getCreateDate());
						stud.setStuApp(perlist2.get(s).getAppearanceStatus());
						stud.setStuAtt(perlist2.get(s).getAttitudeStatus());
						studlist2.add(stud);
					}
				}

			}
		logger.debug("----------studlist2 size---------"+studlist2.size());
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}
		
		return studlist2;
	}

	@Override
	public List<StudentDataBean> getFeesList(StudentDataBean studentDataBean) {
		Query q=null;List<StudentDataBean> feesdetail=null;
		try{
			feesdetail=new ArrayList<StudentDataBean>();
			/*FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);*/
			 
			if(studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("MAHARISHI.SCHOOLID")) || studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("SCMS.SCHOOLID"))) {
				q = entitymanager.createQuery("from FeesDetail where school_id=? and student_ID=? and status=? and month=? and year=?");
				q.setParameter(1, studentDataBean.getSchool_id());
				q.setParameter(2, studentDataBean.getStuRollNo());
				q.setParameter(3, "Active");
				q.setParameter(4, studentDataBean.getMonth());
				q.setParameter(5, studentDataBean.getYear());
			}
			else {
				q = entitymanager.createQuery("from FeesDetail where school_id=? and student_ID=? and status=? and year=?");
				q.setParameter(1, studentDataBean.getSchool_id());
				q.setParameter(2, studentDataBean.getStuRollNo());
				q.setParameter(3, "Active");
				q.setParameter(4, studentDataBean.getYear());
			}
			List<FeesDetail> feesDetail = (List<FeesDetail>) q.getResultList();
			if(feesDetail.size()>0){
				for (int i = 0; i < feesDetail.size(); i++) {
					StudentDataBean stud=new StudentDataBean();
					stud.setExamfees(feesDetail.get(i).getExamFees());
					stud.setTransfees(feesDetail.get(i).getTransportFees());
					stud.setTuitionfees(feesDetail.get(i).getTuitionFees());
					stud.setTotalAmount(feesDetail.get(i).getTotalFees());
					stud.setApprovestatus(feesDetail.get(i).getApprovalStatus());
					stud.setPaidAmount(feesDetail.get(i).getPaidAmount());
					stud.setBalanceAmount(feesDetail.get(i).getBalanceAmount());
					stud.setExtraFee1(feesDetail.get(i).getExtraFees1());
					stud.setExtraFee2(feesDetail.get(i).getExtraFees2());
					stud.setInvoiceNumber(feesDetail.get(i).getInvoiceNumber());
					stud.setPaymentStatus(feesDetail.get(i).getPaymentStatus());
					stud.setTerm(feesDetail.get(i).getTermstatus());
					if(studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("SCMS.SCHOOLID"))){
						stud.setClassName(feesDetail.get(i).getClassName());
						DateFormatSymbols symbols = new DateFormatSymbols();
						stud.setMonth(symbols.getMonths()[Integer.parseInt(feesDetail.get(i).getMonth())-1]);
						stud.setMicrNo(feesDetail.get(i).getEcaFees());
						stud.setAadharCardNo(feesDetail.get(i).getSpecialFees());
						stud.setRationCardNo(feesDetail.get(i).getLabFees());
						stud.setBusPassNo(feesDetail.get(i).getAddmissionFees());
					}
					feesdetail.add(stud);
				}
			}
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
			//e.printStackTrace();
		}
		return feesdetail;
	}
	
	
	@Transactional(value = "transactionManager")
	public String updatefees(StudentDataBean studentDataBean) {
		String status="Fail";Query q=null;
		try{
			//FacesContext context = FacesContext.getCurrentInstance();
			//ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
			if(studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("MAHARISHI.SCHOOLID")) || studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("SCMS.SCHOOLID"))){
				q=entitymanager.createQuery("from FeesDetail where school_id=? and student_ID=? and year=? and month=? and status=?");
				q.setParameter(1, studentDataBean.getSchool_id());
				q.setParameter(2, studentDataBean.getStuRollNo());
				q.setParameter(3, studentDataBean.getYear());
				q.setParameter(4, studentDataBean.getMonth());
				q.setParameter(5, "Active");
			}
			else{
			q = entitymanager.createQuery("from FeesDetail where school_id=? and student_ID=? and year=? and status=?");
			q.setParameter(1, studentDataBean.getSchool_id());
			q.setParameter(2, studentDataBean.getStuRollNo());
			q.setParameter(3, studentDataBean.getYear());
			q.setParameter(4, "Active");
			}
			List<FeesDetail> feesDetail = (List<FeesDetail>) q.getResultList();
			logger.debug("feesDetail--------------->"+feesDetail.size());
			if(feesDetail.size()>0){
				int id=feesDetail.get(0).getPaymentId();
				FeesDetail fDetail=entitymanager.find(FeesDetail.class, id);
				fDetail.setTuitionFees(studentDataBean.getTuitionfees());
				fDetail.setTransportFees(studentDataBean.getTransfees());
				if(studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("MAHARISHI.SCHOOLID"))){
					fDetail.setExtraFees1(studentDataBean.getExtraFee1());
					fDetail.setExtraFees2(studentDataBean.getExtraFee2());
				}else if(studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("SCMS.SCHOOLID"))){
					fDetail.setEcaFees(studentDataBean.getMicrNo());
					fDetail.setSpecialFees(studentDataBean.getAadharCardNo());
					fDetail.setLabFees(studentDataBean.getRationCardNo());
					fDetail.setAddmissionFees(studentDataBean.getBusPassNo());
				}
				else{
				fDetail.setExamFees(studentDataBean.getExamfees());
				}
				fDetail.setTotalFees(studentDataBean.getTotalAmount());
				entitymanager.merge(fDetail);
				status="Success";
			}
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}
		return status;
	}

	@Transactional(value = "transactionManager")
	public String deletefeesDetails(StudentDataBean studentDataBean) {
		String status="Fail";Query q=null;
		try{
			//FacesContext context = FacesContext.getCurrentInstance();
			//ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
			if(studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("MAHARISHI.SCHOOLID")) || studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("SCMS.SCHOOLID"))){
				q=entitymanager.createQuery("from FeesDetail where school_id=? and student_ID=? and year=? and month=? and status='Active'");
				q.setParameter(1, studentDataBean.getSchool_id());
				q.setParameter(2, studentDataBean.getStuRollNo());
				q.setParameter(3, studentDataBean.getYear());
				q.setParameter(4, studentDataBean.getMonth());
			}
			else{
			q = entitymanager.createQuery("from FeesDetail where student_ID=? and className=?  and school_id=? and year=? and status='Active'");
			q.setParameter(1,studentDataBean.getStuRollNo() );
			q.setParameter(2, studentDataBean.getStuCls());
			q.setParameter(3, studentDataBean.getSchool_id());
			q.setParameter(4, studentDataBean.getYear());
			}
			logger.debug("------------"+studentDataBean.getStuRollNo());
			logger.debug("------------"+studentDataBean.getStuCls());
			logger.debug("------------"+studentDataBean.getSchool_id());
			List<FeesDetail> result = (List<FeesDetail>) q.getResultList();
			logger.debug("result size ----- "+result.size());
			if(result.size()>0){
				FeesDetail feesDetail = entitymanager.find(FeesDetail.class, result.get(0).getPaymentId());
				feesDetail.setStatus("DeActive");
				entitymanager.merge(feesDetail);
				status = "Success";
			}
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}
		return status;
	}
	
	
	@Transactional(value = "transactionManager")
	public String payfees(StudentDataBean studentDataBean) {
		String status="Fail";Query q=null;
		//BigDecimal paid=BigDecimal.valueOf(0);
		//BigDecimal balance=BigDecimal.valueOf(0);
		try{
			//FacesContext context = FacesContext.getCurrentInstance();
			//ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
			if(studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("MAHARISHI.SCHOOLID")) || studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("SCMS.SCHOOLID"))){
				q=entitymanager.createQuery("from FeesDetail where school_id=? and student_ID=? and year=? and month=? and status='Active'");
				q.setParameter(1, studentDataBean.getSchool_id());
				q.setParameter(2, studentDataBean.getStuRollNo());
				q.setParameter(3, studentDataBean.getYear());
				q.setParameter(4, studentDataBean.getMonth());
			}else if(studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("CNPS.SCHOOLID"))){
				q = entitymanager.createQuery("from FeesDetail where school_id=? and student_ID=? and year=? and status='Active' and termstatus=?");
				q.setParameter(1, studentDataBean.getSchool_id());
				q.setParameter(2, studentDataBean.getStuRollNo());
				q.setParameter(3, studentDataBean.getYear());
				q.setParameter(4, studentDataBean.getTerm());
			}
			else{
			q = entitymanager.createQuery("from FeesDetail where school_id=? and student_ID=? and year=? and status='Active'");
			q.setParameter(1, studentDataBean.getSchool_id());
			q.setParameter(2, studentDataBean.getStuRollNo());
			q.setParameter(3, studentDataBean.getYear());
			}
			List<FeesDetail> feesDetail = (List<FeesDetail>) q.getResultList();
			if(feesDetail.size()>0){
				int id=feesDetail.get(0).getPaymentId();
				FeesDetail fDetail=entitymanager.find(FeesDetail.class, id);
				fDetail.setPaidAmount(studentDataBean.getPaidAmount());
				fDetail.setBalanceAmount(studentDataBean.getBalanceAmount());
				if(studentDataBean.getBalanceAmount().equals("0")){
					fDetail.setPaymentStatus("Paid");
				}else{
					fDetail.setPaymentStatus("Partially Paid");
				}
				entitymanager.merge(fDetail);
				status="Success";
			}
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return status;
	}
	
	/*@Override
	public List<TimeTable> getglobalSearchTimeTableList(String schoolID,String personID, String classnames) {
		List<ExamTable> examList=null;List<TimeTable> timetableList=null;
		try{
			examList=new ArrayList<ExamTable>();timetableList=new ArrayList<TimeTable>();
			examList=getExamList(Integer.parseInt(schoolID),personID,classnames);
			if(examList.size()>0){
				for (int i = 0; i < examList.size(); i++) {
					timetableList=getExamTableList(personID,examList.get(i).getExam_table_ID());
				}
			}
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}
		return timetableList;
	}*/
	
	
	@Override
	public List<ExamTable> getglobalSearchTimeTableList(String schoolID,String personID, String className, String month) {
		List<ExamTable> examList=null;//
		List<TimeTable> timetableList=null;
		Query q = null;
		try {
			examList=new ArrayList<ExamTable>();timetableList=new ArrayList<TimeTable>();
				if (personID != null && Integer.parseInt(schoolID) > 0 && className != null && month != null) {
					q = entitymanager.createQuery("from ExamTable where class_name=? and school_ID=? and status=? and MONTH(startDate) = ?");
					q.setParameter(1, className);
					q.setParameter(2, Integer.parseInt(schoolID));
					q.setParameter(3, "Insert");
					q.setParameter(4, Integer.parseInt(month));
					List<ExamTable> res = (List<ExamTable>) q.getResultList();
					System.out.println(res.size());
					if (res.size() > 0) {
						examList.addAll(res);
					}
				}
		} catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());

		}
		finally {
			timetableList=null;
			
		}
		return examList;
	}
	
	@Transactional(value = "transactionManager")
	public String updateAttendance(AttendanceDataBean attendanceDataBean) {
		Query q=null;
		String status="";
		String schoolName="";
		List<Person> roleStatus=null;
		//FacesContext context = FacesContext.getCurrentInstance();
		//ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
		try{
			if(attendanceDataBean.getStudentID()!=""){
					roleStatus=new ArrayList<Person>();
					roleStatus = getRollType(String.valueOf(getPersonID(attendanceDataBean.getStudentID())));
					if(roleStatus.size()>0){
						schoolName=roleStatus.get(0).getSchool().getName();
					}
			}
			q=entitymanager.createQuery("from StudentAttendance where student_ID=? and attendance_date=? and school_ID=? and class_section=?");
			q.setParameter(1, attendanceDataBean.getStudentID());
			q.setParameter(2, attendanceDataBean.getMonthDates());
			q.setParameter(3, attendanceDataBean.getSchoolID());
			q.setParameter(4, attendanceDataBean.getClassname());
			List<StudentAttendance> atdncList=(List<StudentAttendance>)q.getResultList();
			if(atdncList.size()>0){
				
				int id=atdncList.get(0).getStudent_attendance_id();
				StudentAttendance studentAttendance=entitymanager.find(StudentAttendance.class, id);
				studentAttendance.setAttendance_date(attendanceDataBean.getMonthDates());
				studentAttendance.setClass_section(attendanceDataBean.getClassname());
				studentAttendance.setInTime(attendanceDataBean.getInTime());
				studentAttendance.setOutTime(attendanceDataBean.getOutTime());
				studentAttendance.setPresent_day(attendanceDataBean.getPresentDay());
				studentAttendance.setPresent_status(attendanceDataBean.getPresentStatus());
				studentAttendance.setSchool(entitymanager.find(School.class, attendanceDataBean.getSchoolID()));
				studentAttendance.setStudent_ID(attendanceDataBean.getStudentID());
				studentAttendance.setStatus("Active");
				entitymanager.merge(studentAttendance);
				status="update";
			}else{
				StudentAttendance studentAttendance=new StudentAttendance();
				studentAttendance.setAttendance_date(attendanceDataBean.getMonthDates());
				studentAttendance.setClass_section(attendanceDataBean.getClassname());
				studentAttendance.setInTime(attendanceDataBean.getInTime());
				studentAttendance.setOutTime(attendanceDataBean.getOutTime());
				studentAttendance.setPresent_day(attendanceDataBean.getPresentDay());
				studentAttendance.setPresent_status(attendanceDataBean.getPresentStatus());
				studentAttendance.setSchool(entitymanager.find(School.class, attendanceDataBean.getSchoolID()));
				studentAttendance.setStudent_ID(attendanceDataBean.getStudentID());
				studentAttendance.setStatus("Active");
				entitymanager.persist(studentAttendance);
				status="save";
			}
			
			if(attendanceDataBean.getSchoolID()==Integer.parseInt(paths.getString("MAHARISHI.SCHOOLID")) || attendanceDataBean.getSchoolID()==Integer.parseInt(paths.getString("SMRV.SCHOOLID"))) {
			int studentID=getStudentID(attendanceDataBean.getStudentID());
			if (studentID>0 && attendanceDataBean.getPresentStatus().equalsIgnoreCase("Absent")) {
				attendanceDataBean.setPhonesNo(getparentdetailBystudentId(studentID,"phnNo"));
				attendanceDataBean.setMailid(getparentdetailBystudentId(studentID,"email"));
				MailSendJDBC.attendanceTake(attendanceDataBean, schoolName, attendanceDataBean.getSchoolID());
			}
			}
			
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return status;
	}
	
	
	
	@Override
	@SuppressWarnings("unchecked")
	public String getAttendanceList(AttendanceDataBean attendanceDataBean) {
		Query q=null;//String dmonth="";
		try{
			q=entitymanager.createQuery("from StudentAttendance where month(attendance_date)=? and year(attendance_date)=? and student_ID=? and school_ID=? and class_section=?");
			q.setParameter(1, attendanceDataBean.getDmonth());
			q.setParameter(2, attendanceDataBean.getDyear());
			q.setParameter(3, attendanceDataBean.getStudentID());
			q.setParameter(4, attendanceDataBean.getSchoolID());
			q.setParameter(5, attendanceDataBean.getClassname());
			System.out.println("year --- "+attendanceDataBean.getDyear());
			/*q=entitymanager.createQuery("from StudentAttendance where year(attendance_date)=? and month(attendance_date)=? and ");
			q.setParameter(1, attendanceDataBean.getDyear());
			q.setParameter(2, attendanceDataBean.getDmonth());*/
			List<StudentAttendance> atdncList=(List<StudentAttendance>)q.getResultList();
			System.out.println("atdncList size ------------------"+atdncList.size());
			attendanceDataBean.setStudentAttendanceList(atdncList);
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return "";
	}
	
	/*@Override
	public List<LoginAccess> getUserDetails(LoginAccess loginaccess) {
		Query q=null;
		List<School> schoollist=null;int school_id=0;
		List<Person> personlist=null;int person_id=0;
		List<User> userlist=null;List<Student> studlist=null;
		List<StudentDetail> studentlist=null;
		List<Teacher> teachlist=null;List<TeacherDetail> teacherlist=null;
		List<Parent> parlist=null;List<ParentDetail> parentlist=null;
		List<Staff> stafflist=null;List<LoginAccess> userdetail=null;
		List<StudentParent> studentparent=null;List<StudentClass> studentclass=null;
		List<Class> classlist=null;
		try{
			userdetail=new ArrayList<LoginAccess>();
			q=entitymanager.createQuery("from School where name=?");
			q.setParameter(1, loginaccess.getSchoolName());
			schoollist=(List<School>)q.getResultList();
			if(schoollist.size()>0){
				school_id=schoollist.get(0).getSchool_ID();
				loginaccess.setSchoolEmail(schoollist.get(0).getEmailId());
				q=null;
				q=entitymanager.createQuery("from Person where school_ID=? and personRole=?");
				q.setParameter(1, school_id);
				q.setParameter(2, loginaccess.getUser());
				personlist=(List<Person>)q.getResultList();
				if(personlist.size()>0){
					for (int i = 0; i < personlist.size(); i++) {
						person_id=personlist.get(i).getPerson_ID();
						q=null;
						q=entitymanager.createQuery("from User where person_ID=?");
						q.setParameter(1, person_id);
						userlist=(List<User>)q.getResultList();
						if(userlist.size()>0){
							LoginAccess login=new LoginAccess();
							login.setUsername(userlist.get(0).getUsername());
							login.setUserpassword(userlist.get(0).getText_password());
							login.setUserID(userlist.get(0).getHas_user_ID());
							 if(loginaccess.getUser().equalsIgnoreCase("Teacher")){
									q=null;
									q=entitymanager.createQuery("from Teacher where person_ID=?");
									q.setParameter(1, person_id);
									teachlist=(List<Teacher>)q.getResultList();
									if(teachlist.size()>0){
										System.out.println("-------teacher id--------"+teachlist.get(0).getTeacher_ID());
										q=null;
										q=entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
										q.setParameter(1, teachlist.get(0).getTeacher_ID());
										teacherlist=(List<TeacherDetail>)q.getResultList();
										if(teacherlist.size()>0){
											login.setTeacherName(teacherlist.get(0).getFirstName()+" "+teacherlist.get(0).getLastName());
										}
									}
								}else if(loginaccess.getUser().equalsIgnoreCase("Parent")){
									System.out.println("-----------Parent----------------");
									q=null;
									q=entitymanager.createQuery("from Parent where person_ID=?");
									q.setParameter(1, person_id);
									parlist=(List<Parent>)q.getResultList();
									if(parlist.size()>0){
										q=null;
										q=entitymanager.createQuery("from ParentDetail where parent_ID=?");
										q.setParameter(1, parlist.get(0).getParent_ID());
										parentlist=(List<ParentDetail>)q.getResultList();
										if(parentlist.size()>0){
											if(!parentlist.get(0).getEmaiId().equals("")){
												login.setParentName(parentlist.get(0).getFirstName()+" "+parentlist.get(0).getLastName());
												q=null;
												q=entitymanager.createQuery("from StudentParent where parent_ID=?");
												q.setParameter(1, parlist.get(0).getParent_ID());
												studentparent=(List<StudentParent>)q.getResultList();
												if(studentparent.size() > 0){
													q=null;
													q=entitymanager.createQuery("from StudentClass where student_ID=?");
													q.setParameter(1, studentparent.get(0).getStudent().getStudent_ID());
													studentclass=(List<StudentClass>)q.getResultList();
													if(studentclass.size() > 0){
														q=null;
														q=entitymanager.createQuery("from Class where class_ID=?");
														q.setParameter(1, studentclass.get(0).getClazz().getClass_ID());
														classlist=(List<Class>)q.getResultList();
														login.setClassname(classlist.get(0).getClassName()+"/"+classlist.get(0).getClassSection());
													}
												}
											}
										}
									}
								}
							userdetail.add(login);
						}
					}
				}
			}System.out.println("-------------userdetail--------------"+userdetail.size());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception -->"+e.getMessage());
		}
		return userdetail;
	}*/
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<LoginAccess> getUserDetails(LoginAccess loginaccess) {
	Query q=null;
	List<School> schoollist=null;int school_id=0;
	List<Person> personlist=null;int person_id=0;
	List<User> userlist=null;List<Student> studlist=null;
	List<StudentDetail> studentlist=null;
	List<Teacher> teachlist=null;List<TeacherDetail> teacherlist=null;
	List<Parent> parlist=null;List<ParentDetail> parentlist=null;
	List<Staff> stafflist=null;List<LoginAccess> userdetail=null;
	List<StudentParent> studentparent=null;List<StudentClass> studentclass=null;
	List<Class> classlist=null;LoginAccess login=null;
	try{
	userdetail=new ArrayList<LoginAccess>();
	q=entitymanager.createQuery("from School where name=?");
	q.setParameter(1, loginaccess.getSchoolName());
	schoollist=(List<School>)q.getResultList();
	if(schoollist.size()>0){
	school_id=schoollist.get(0).getSchool_ID();
	loginaccess.setSchoolEmail(schoollist.get(0).getEmailId());
	q=null;
	q=entitymanager.createQuery("from Person where school_ID=? and personRole=? and status='Active'");
	q.setParameter(1, school_id);
	q.setParameter(2, loginaccess.getUser());
	personlist=(List<Person>)q.getResultList();
	logger.debug("personlist ------- "+personlist.size());
	if(personlist.size()>0){
	for (int i = 0; i < personlist.size(); i++) {
	person_id=personlist.get(i).getPerson_ID();
	q=null;
	q=entitymanager.createQuery("from User where person_ID=?");
	q.setParameter(1, person_id);
	userlist=(List<User>)q.getResultList();
	if(userlist.size()>0){
		if(loginaccess.getUser().equalsIgnoreCase("Teacher")){
		q=null;
		q=entitymanager.createQuery("from Teacher where person_ID=? and status='Active'");
		q.setParameter(1, person_id);
		teachlist=(List<Teacher>)q.getResultList();
		if(teachlist.size()>0){
		login=new LoginAccess();
		login.setUsername(userlist.get(0).getUsername());
		login.setUserpassword(userlist.get(0).getText_password());
		login.setUserID(userlist.get(0).getHas_user_ID());
		logger.debug("-------teacher id--------"+teachlist.get(0).getTeacher_ID());
		q=null;
		q=entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
		q.setParameter(1, teachlist.get(0).getTeacher_ID());
		teacherlist=(List<TeacherDetail>)q.getResultList();
		if(teacherlist.size()>0){
		login.setTeacherName(teacherlist.get(0).getFirstName()+" "+teacherlist.get(0).getLastName());
		}
		userdetail.add(login);
		}
				}
		
		else if(loginaccess.getUser().equalsIgnoreCase("Parent")){
			logger.debug("-----------Parent----------------");
			q=null;
			q=entitymanager.createQuery("from Parent where person_ID=? and status='Active'");
			q.setParameter(1, person_id);
			parlist=(List<Parent>)q.getResultList();
			if(parlist.size()>0){
			q=null;
			q=entitymanager.createQuery("from ParentDetail where parent_ID=?");
			q.setParameter(1, parlist.get(0).getParent_ID());
			parentlist=(List<ParentDetail>)q.getResultList();
			if(parentlist.size()>0){
			login=new LoginAccess();
			login.setUsername(userlist.get(0).getUsername());
			login.setUserpassword(userlist.get(0).getText_password());
			login.setUserID(userlist.get(0).getHas_user_ID());
			login.setParentName(parentlist.get(0).getFirstName()+" "+parentlist.get(0).getLastName());
			q=null;
			q=entitymanager.createQuery("from StudentParent where parent_ID=?");
			q.setParameter(1, parlist.get(0).getParent_ID());
			studentparent=(List<StudentParent>)q.getResultList();
			if(studentparent.size() > 0){
			q=null;
			q=entitymanager.createQuery("from StudentClass where student_ID=?");
			q.setParameter(1, studentparent.get(0).getStudent().getStudent_ID());
			studentclass=(List<StudentClass>)q.getResultList();
			if(studentclass.size() > 0){
			q=null;
			q=entitymanager.createQuery("from Class where class_ID=?");
			q.setParameter(1, studentclass.get(0).getClazz().getClass_ID());
			classlist=(List<Class>)q.getResultList();
			login.setClassname(classlist.get(0).getClassName()+"/"+classlist.get(0).getClassSection());
			}
			}
			userdetail.add(login);
			}
			 
			}
			}
	else{
	login=new LoginAccess();
	login.setUsername(userlist.get(0).getUsername());
	login.setUserpassword(userlist.get(0).getText_password());
	login.setUserID(userlist.get(0).getHas_user_ID());
	userdetail.add(login);
	}
	 
	}
	}
	}
	}logger.debug("-------------userdetail--------------"+userdetail.size());
	}catch (Exception e) {
	logger.error("Exception -->"+e.getMessage());
	}
	return userdetail;
	}
	
	
	//josni changes
	@Transactional(value = "transactionManager")
	@Override
	public String insertformdetails(LoginAccess loginaccess) {
		String status="Fail";
		Enquiryform form=null;
		try{
			System.out.println("inside form details");
			form=new Enquiryform(loginaccess);
			entitymanager.persist(form);
			status="Sucess";
			System.out.println("----------status in dao----->"+status); 
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception -->"+e.getMessage());
		}
		return status;
	}
	// CommunitryDetail list
	@Override
	public List<StudentDataBean> getStudentCommunitryDetail(String communitySearchType, StudentDataBean studentDataBean) {
		logger.debug("----inside getStudentInfo------");
		Query q = null;
		//List<StudentParent> studrollno=new ArrayList<StudentParent>();
		List<StudentDataBean> studlist = new ArrayList<StudentDataBean>();
		
		int studid = 0;
		int classid = 0;
		List<Person> roleStatus = null;
		try {
			roleStatus = new ArrayList<Person>();
			if (studentDataBean.getPersonID() != null) {
				roleStatus = getRollType(studentDataBean.getPersonID());
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					if (roleStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
						
							if (communitySearchType.equalsIgnoreCase("All")) {
								q = entitymanager.createQuery("from Student where school_ID=? and status=? ");
								q.setParameter(1, studentDataBean.getSchool_id());
								q.setParameter(2, "Active");
								List<Student> student = (List<Student>) q.getResultList();
								if (student.size() > 0) {
									for(Student students:student){
										q = null;
										q = entitymanager.createQuery("from StudentDetail where student_ID=?");
										q.setParameter(1, students.getStudent_ID());
										List<StudentDetail> studdetaillist = (List<StudentDetail>) q.getResultList();
										if (studdetaillist.size() > 0) {
											StudentDataBean studobj = new StudentDataBean();
											studobj.setReligion(studdetaillist.get(0).getReligion());
											studobj.setStuRollNo(studdetaillist.get(0).getStudentid().getRollNumber());
											studobj.setClassification(studdetaillist.get(0).getClassification());
											studobj.setStuGender(studdetaillist.get(0).getGender());
											studlist.add(studobj);
										}
									}
								}
							}
							else{
								if (communitySearchType.equalsIgnoreCase("Class Wise")) {
									q = entitymanager.createQuery("from Class where class_name=? and school_ID=? and status=?");
									q.setParameter(1, studentDataBean.getClassName());
									q.setParameter(2, studentDataBean.getSchool_id());
									q.setParameter(3, "Active");
								}
								else if(communitySearchType.equalsIgnoreCase("Section Wise")){
									String className = studentDataBean.getClassName().split("/")[0];
									String sectionName = studentDataBean.getClassName().split("/")[1];
									logger.debug("---inside section wise search valure----"+className+"----->"+sectionName+"-------->");
									q = entitymanager.createQuery("from Class where class_name=? and class_section=? and school_ID=? and status=?");
									q.setParameter(1, className);
									q.setParameter(2, sectionName);
									q.setParameter(3, studentDataBean.getSchool_id());
									q.setParameter(4, "Active");
								}else if(communitySearchType.equalsIgnoreCase("Year Wise")){
									q = entitymanager.createQuery("from Class where class_name=? and school_ID=? and status=?");
									q.setParameter(1, studentDataBean.getClassName());
									q.setParameter(2, studentDataBean.getSchool_id());
									q.setParameter(3, "Active");
								}
								List<Class> clslist = (List<Class>) q.getResultList();
								if (clslist.size() > 0) {
									for (int i = 0; i < clslist.size(); i++) {
											classid = clslist.get(i).getClass_ID();
											q = entitymanager.createQuery("from StudentClass where class_ID=? and status='Active'");
											q.setParameter(1, classid);
											List<StudentClass> studclasslist1 = (List<StudentClass>) q.getResultList();
											if (studclasslist1.size() > 0) {
												for (int j = 0; j < studclasslist1.size(); j++) {
													studid = studclasslist1.get(j).getStudent().getStudent_ID();
													q = null;
													q = entitymanager.createQuery("from Student where student_ID=? and school_ID=? and status='Active'");
													q.setParameter(1, studid);
													q.setParameter(2, studentDataBean.getSchool_id());
													List<Student> student = (List<Student>) q.getResultList();
													if (student.size() > 0) {
														q = null;
														q = entitymanager.createQuery("from StudentDetail where student_ID=?");
														q.setParameter(1, studid);
														List<StudentDetail> studdetaillist = (List<StudentDetail>) q.getResultList();
														if (studdetaillist.size() > 0) {
															StudentDataBean studobj = new StudentDataBean();
															studobj.setReligion(studdetaillist.get(0).getReligion());
															studobj.setStuRollNo(studdetaillist.get(0).getStudentid().getRollNumber());
															studobj.setClassification(studdetaillist.get(0).getClassification());
															studobj.setStuGender(studdetaillist.get(0).getGender());
															studlist.add(studobj);
														}
													}
												}
											}
										}
									}
							}
					} 
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			//logger.warn("Inside Exception",e);
			//logger.warn(" exception - "+e);
			//e.printStackTrace();
		}
		return studlist;
	
	}
	
	@Override
	public List<String> getTeachername(String schoolID) {
		List<String> nameList=null;
		Query q=null;
		try{
			nameList=new ArrayList<String>();
			q=entitymanager.createQuery("select teacher_ID from Teacher where school_ID=? and status='Active'");
			q.setParameter(1, schoolID);
			List<String> teacherList=(ArrayList<String>)q.getResultList();
			if(teacherList.size()>0){
				for (int i = 0; i <teacherList.size(); i++) {
					q=entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
					q.setParameter(1, teacherList.get(i));
					List<TeacherDetail> detailsList=(ArrayList<TeacherDetail>)q.getResultList();
					if(detailsList.size()>0){
						nameList.add(detailsList.get(0).getFirstName()+" "+detailsList.get(0).getLastName());
					}
				}
			}
		}catch(Exception e){
			
		}
		return nameList;
	}

	@Override
	public List<String> getStudentname(String schoolID) {
		List<String> nameList=null;
		Query q=null;
		try{
			nameList=new ArrayList<String>();
			q=entitymanager.createQuery("from Student where school_ID=? and status='Active'");
			q.setParameter(1, schoolID);
			List<Student> studentList=(ArrayList<Student>)q.getResultList();
			if(studentList.size()>0){
				for (int i = 0; i <studentList.size(); i++) {
					q=entitymanager.createQuery("from StudentDetail where student_ID=?");
					q.setParameter(1, studentList.get(i).getStudent_ID());
					List<StudentDetail> detailsList=(ArrayList<StudentDetail>)q.getResultList();
					if(detailsList.size()>0){
						nameList.add(detailsList.get(0).getFirstName()+" "+detailsList.get(0).getLastName()+"/"+studentList.get(i).getRollNumber());
					}
				}
			}
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception -->"+e.getMessage());
		}
		return nameList;
	}

	@Override
	public void getStudentdata(String personID, StaffDataBean staffDataBean) {
		Query q=null;
		try{
			q=entitymanager.createQuery("from Student where rollNumber=? and status='Active'");
			q.setParameter(1, staffDataBean.getStudentName().split("/")[1]);
			List<Student> studentList=(ArrayList<Student>)q.getResultList();
			if(studentList.size()>0){
					q=null;
					q=entitymanager.createQuery("from StudentDetail where student_ID=?");
					q.setParameter(1, studentList.get(0).getStudent_ID());
					List<StudentDetail> detailsList=(ArrayList<StudentDetail>)q.getResultList();
					if(detailsList.size()>0){
						staffDataBean.setDateofBirth(detailsList.get(0).getDob());
						staffDataBean.setParentName(detailsList.get(0).getFatherName());
					}
					q = null;
					q = entitymanager.createQuery("from StudentClass where student_ID=?");
					q.setParameter(1, studentList.get(0).getStudent_ID());
					List<StudentClass> studclasslist = (List<StudentClass>) q.getResultList();
					if(studclasslist.size()>0){
						staffDataBean.setClassName(studclasslist.get(0).getClazz().getClassName()+"-"+studclasslist.get(0).getClazz().getClassSection());
					}
			}
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception -->"+e.getMessage());
		}
	}

	@Override
	public String emisnoDetails(StaffDataBean staffDataBean) {
		String status="Fail";
		Query q=null;
		try{
			q=entitymanager.createQuery("from StudentDetail where emisNo=?");
			q.setParameter(1, staffDataBean.getEmisNo());
			List<StudentDetail> studentList=(ArrayList<StudentDetail>)q.getResultList();
			if(studentList.size()>0){
				status="Success";
				String serialNo=generateSerisialno(staffDataBean.getSchoolID());
				staffDataBean.setSno(serialNo);
				staffDataBean.setStudentName((studentList.get(0).getFirstName()+" "+studentList.get(0).getLastName()).toUpperCase());
				staffDataBean.setReligion(studentList.get(0).getReligion());
				staffDataBean.setStuGender(studentList.get(0).getGender());
				staffDataBean.setDateofBirth(studentList.get(0).getDob());
				staffDataBean.setParentName(studentList.get(0).getFatherName());
				q = null;
				q = entitymanager.createQuery("from StudentClass where student_ID=?");
				q.setParameter(1, studentList.get(0).getStudentid());
				List<StudentClass> studclasslist = (List<StudentClass>) q.getResultList();
				if(studclasslist.size()>0){
					staffDataBean.setClassName(studclasslist.get(0).getClazz().getClassName());
				}
			}
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception -->"+e.getMessage());
		}
		return status;
	}

	private String generateSerisialno(int schoolID) {
		Query q=null;
		String serialNo="";
		try{
			q=entitymanager.createQuery("from Certificate where schoolID=?");
			q.setParameter(1, String.valueOf(schoolID));
			List<Certificate> certificateList=(ArrayList<Certificate>)q.getResultList();
			if(certificateList.size()==0){
				serialNo="00001";
			}else{
				String value=String.valueOf(certificateList.size()+1);
				if(value.length()==1){
					serialNo="0000"+value;
				}else if(value.length()==2){
					serialNo="000"+value;
				}else if(value.length()==3){
					serialNo="00"+value;
				}else if(value.length()==4){
					serialNo="0"+value;
				}else if(value.length()==5){
					serialNo=value;
				}else{
					serialNo=String.valueOf(certificateList.size()+1);
				}
			}
		}catch(Exception e){
			//e.printStackTrace();
		}
		return serialNo;
	}

	@Transactional(value = "transactionManager")
	@Override
	public String saveTC(StaffDataBean staffDataBean) {
		String status="Fail";
		try{
			Certificate certificate=new Certificate();
			certificate.setCreaateDate(date);
			certificate.setSchoolID(String.valueOf(staffDataBean.getSchoolID()));
			certificate.setEmisno(staffDataBean.getEmisNo());
			certificate.setSerialNo(staffDataBean.getSno());
			entitymanager.persist(certificate);
			status="Success";
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}
	
	@Transactional(value = "transactionManager")
	public String changePassword(LoginAccess loginaccess) {
		String status="";String EncPwd="";
		try{
			for (int i = 0; i < loginaccess.getSelectUserlist().size(); i++) {
				User user=entitymanager.find(User.class, loginaccess.getSelectUserlist().get(i).getUserID());
				EncPwd=PasswordEncryption.GeneratePaswword(loginaccess.getSelectUserlist().get(i).getUserpassword());
				user.setPassword(EncPwd);
				user.setText_password(loginaccess.getSelectUserlist().get(i).getUserpassword());
				entitymanager.merge(user);
				entitymanager.flush();
				entitymanager.clear();
				status="Success";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	@SuppressWarnings("unchecked")
	public String getparentdetailBystudentId(int student_ID,String info) {
		Query q=null;
		//String status="";
		int parent_ID=0;
		String infoDetail="";
		try{
				if (student_ID!=0) {
					q = entitymanager.createQuery("from StudentParent where student_ID=? and status='Active'");
					q.setParameter(1, student_ID);
					List<StudentParent> result = (List<StudentParent>) q.getResultList();
					if (result.size() > 0) {
					parent_ID=result.get(0).getParent().getParent_ID();
					q = entitymanager.createQuery("from ParentDetail where parent_ID=?");
						q.setParameter(1, parent_ID);
						List<ParentDetail> parentdetail = (List<ParentDetail>) q.getResultList();
						if (info.equalsIgnoreCase("email")) {
							infoDetail=parentdetail.get(0).getEmaiId();
						}
						else if (info.equalsIgnoreCase("phnNo")) {
							infoDetail=parentdetail.get(0).getPhoneNumber();
						}
						
					}
				}
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return infoDetail;
	}

	@Transactional(value = "transactionManager")
	@Override
	@SuppressWarnings("unchecked")
	public String insertTermfees(StudentDataBean studentDataBean) {
		Query q=null;
		try{
				studentDataBean.setFeesList(new ArrayList<StudentDataBean>());
				q = entitymanager.createQuery("from FeesDetail where school_id=? and student_ID=? and status=? and year=? and termstatus=?");
				q.setParameter(1, studentDataBean.getSchool_id());
				q.setParameter(2, studentDataBean.getStuRollNo());
				q.setParameter(3, "Active");
				q.setParameter(4, studentDataBean.getYear());
				q.setParameter(5, studentDataBean.getTerm());
				List<FeesDetail> feesDetail = (List<FeesDetail>) q.getResultList();
				if(feesDetail.size()>0){
					for (int i = 0; i < feesDetail.size(); i++) {
						StudentDataBean stud=new StudentDataBean();
						stud.setExamfees(feesDetail.get(i).getExamFees());
						stud.setTransfees(feesDetail.get(i).getTransportFees());
						stud.setTuitionfees(feesDetail.get(i).getTuitionFees());
						stud.setTotalAmount(feesDetail.get(i).getTotalFees());
						stud.setApprovestatus(feesDetail.get(i).getApprovalStatus());
						stud.setPaidAmount(feesDetail.get(i).getPaidAmount());
						stud.setBalanceAmount(feesDetail.get(i).getBalanceAmount());
						stud.setExtraFee1(feesDetail.get(i).getExtraFees1());
						stud.setExtraFee2(feesDetail.get(i).getExtraFees2());
						stud.setInvoiceNumber(feesDetail.get(i).getInvoiceNumber());
						stud.setPaymentStatus(feesDetail.get(i).getPaymentStatus());
						stud.setTerm(feesDetail.get(i).getTermstatus());
						studentDataBean.getFeesList().add(stud);
					}
				}else{
					FeesDetail fees=new FeesDetail();
					fees.setSchoolId(entitymanager.find(School.class, studentDataBean.getSchool_id()));
					fees.setTotalFees(studentDataBean.getTotalFees());
					fees.setClassName(studentDataBean.getStuCls());
					fees.setTermstatus(studentDataBean.getTerm());
					fees.setApprovalStatus("insert");
					fees.setStatus("Active");
					fees.setPaymentStatus("Not Paid");
					fees.setPaidAmount("0");
					fees.setBalanceAmount("0");
					fees.setStudent_ID(studentDataBean.getStuRollNo());
					fees.setCreatedDate(date);
					fees.setUploadedDate(date);
					fees.setYear(studentDataBean.getYear());
					entitymanager.persist(fees);
			}
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}
		return null;
	}
	
	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	@Override
	public String saveReport(ReportDataBean reportDataBean) {
		String status="fail";
		Query q=null;
		logger.info("inside dao impl save report");
		try {
			q = entitymanager.createQuery("from ReportMenu where school_id=? and status=? and reportName=?");
			q.setParameter(1, Integer.parseInt(reportDataBean.getSchoolID()));
			q.setParameter(2, "Active");
			q.setParameter(3, reportDataBean.getReportName());
			List<ReportMenu> reportlist = (List<ReportMenu>) q.getResultList();
			logger.debug("reportlist--->"+reportlist.size());
			if(reportlist.size()>0){
				status="Exist";	
			}
			else{
				ReportMenu reportinsert=new ReportMenu();
				reportinsert.setSchool_ID(entitymanager.find(School.class, Integer.parseInt(reportDataBean.getSchoolID())));
				reportinsert.setReportName(reportDataBean.getReportName());
				reportinsert.setReportDescription(reportDataBean.getReportdescription());
				reportinsert.setReportSubtype(reportDataBean.getReportsubType());
				reportinsert.setReportType(reportDataBean.getReportType());
				reportinsert.setStatus("Active");
				entitymanager.persist(reportinsert);
						
						q = entitymanager.createQuery("from ReportMenu where school_id=? and status=? and reportName=?");
						q.setParameter(1, Integer.parseInt(reportDataBean.getSchoolID()));
						q.setParameter(2, "Active");
						q.setParameter(3, reportDataBean.getReportName());
						List<ReportMenu> getreportID = (List<ReportMenu>) q.getResultList();
						logger.debug("getreportID--->"+getreportID.size());
						if(getreportID.size()>0){
							for (int i = 0; i <reportDataBean.getFilterlist().size(); i++) {
								ReportFilterTable reportFilterTableinsert=new ReportFilterTable();
								reportFilterTableinsert.setFilter_activeStatus(String.valueOf(reportDataBean.getFilterlist().get(i).isActiveStatus()));
								reportFilterTableinsert.setFilterName(reportDataBean.getFilterlist().get(i).getFilterName());
								reportFilterTableinsert.setReportMenu(entitymanager.find(ReportMenu.class, getreportID.get(0).getReportMenuId()));
								entitymanager.persist(reportFilterTableinsert);
								entitymanager.flush();
								entitymanager.clear();	
							}
						}
				logger.debug("end--->");		
				status="Success";
				
			}
		} catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
			e.printStackTrace();
		}
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ReportDataBean> getReportmenulist(ReportDataBean reportDataBean) {
		Query q=null;
		List<ReportDataBean> reportMenu=new ArrayList<ReportDataBean>();
		try {
			q = entitymanager.createQuery("from ReportMenu where school_id=? and status=?");
			q.setParameter(1, Integer.parseInt(reportDataBean.getSchoolID()));
			q.setParameter(2, "Active");
			List<ReportMenu> reportlist = (List<ReportMenu>) q.getResultList();
		if (reportlist.size()>0) {
			for (int i = 0; i < reportlist.size(); i++) {
				ReportDataBean reportdatabean=new ReportDataBean();
				reportdatabean.setReportName(reportlist.get(i).getReportName());
				reportdatabean.setReportsubType(reportlist.get(i).getReportSubtype());
				reportdatabean.setReportType(reportlist.get(i).getReportType());
				reportdatabean.setReportdescription(reportlist.get(i).getReportDescription());
				reportdatabean.setReportID(reportlist.get(i).getReportMenuId());
				reportMenu.add(reportdatabean);
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reportMenu;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ReportDataBean> getFilterlist(ReportDataBean reportDataBean) {
		Query q=null;
		List<ReportDataBean> reportfilteroption=new ArrayList<ReportDataBean>();
		logger.debug("-------inside getFilter List---------------->"+reportDataBean.getReportID());
		try {
			q = entitymanager.createQuery("from ReportFilterTable where has_report_menu_ID=?");
			q.setParameter(1, reportDataBean.getReportID());
			List<ReportFilterTable> reportfilterlist = (List<ReportFilterTable>) q.getResultList();
		if (reportfilterlist.size()>0) {
			for (int i = 0; i < reportfilterlist.size(); i++) {
				ReportDataBean reportdatabean=new ReportDataBean();
				reportdatabean.setFilterName(reportfilterlist.get(i).getFilterName());
				reportdatabean.setActiveStatus(Boolean.valueOf(reportfilterlist.get(i).getFilter_activeStatus()));
				reportfilteroption.add(reportdatabean);
			}
		}
		} catch (Exception e) {
			logger.warn("Exception -->" + e.getMessage());
			e.printStackTrace();
		}
		return reportfilteroption;
	}

	
	public void classNamebasedData(ReportDataBean reportDataBean) {
		Query q=null;
		try{
			reportDataBean.setSectionlist(new ArrayList<String>());
			q=entitymanager.createQuery("from Class where className=? and school_ID=? and status=?");
			q.setParameter(1, reportDataBean.getClassName().split("/")[0]);
			q.setParameter(2, Integer.parseInt(reportDataBean.getSchoolID()));
			q.setParameter(3, "Active");
			List<Class> classSection=(ArrayList<Class>)q.getResultList();
			logger.debug("---size class section--->"+classSection.size());
			if(classSection.size()>0){
					for (int i = 0; i < classSection.size(); i++) {
						reportDataBean.getSectionlist().add(i,classSection.get(i).getClassSection());
					}
				}
		}catch(Exception e){
			logger.warn("Exception -->" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public void secNamebasedData(ReportDataBean reportDataBean) {
		logger.debug("inside sec Name based data method");
		Query v=null;
		List<String> studentID = null;
		try{
			logger.debug("class and Sec Name Value--->"+reportDataBean.getClassName()+"------->"+reportDataBean.getSecName());
			
			if (reportDataBean.getSecName().equalsIgnoreCase("All") && reportDataBean.getClassName().equalsIgnoreCase("All")) {
				v = entitymanager.createQuery("from Class where school_ID=? and status=?");
				v.setParameter(1, Integer.parseInt(reportDataBean.getSchoolID()));
				v.setParameter(2, "Active");
			}
			else {
				v = entitymanager.createQuery("from Class where class_name=? and class_section=? and school_ID=? and status=?");
				v.setParameter(1, reportDataBean.getClassName());
				v.setParameter(2, reportDataBean.getSecName());
				v.setParameter(3, Integer.parseInt(reportDataBean.getSchoolID()));
				v.setParameter(4, "Active");
			}
				List<Class> classs = (List<Class>) v.getResultList();
				logger.debug("class size--->"+classs.size());
						if (classs.size() > 0) {
							studentID = new ArrayList<String>();
							for (int i = 0; i < classs.size(); i++) {
								v = entitymanager.createQuery("from StudentClass where class_ID=? and status='Active'");
								v.setParameter(1, classs.get(i).getClass_ID());
								List<StudentClass> studentClass = (List<StudentClass>) v.getResultList();
								logger.debug("student class size------"+studentClass.size());
								if (studentClass.size() > 0) {
									for (int j = 0; j < studentClass.size(); j++) {
										v = null;
										v = entitymanager.createQuery("from StudentDetail where student_ID=?");
										v.setParameter(1, studentClass.get(j).getStudent().getStudent_ID());
										List<StudentDetail> student = (List<StudentDetail>) v.getResultList();
										logger.debug("student size--->"+student.size());
										if (student.size() > 0) {
											studentID.add(student.get(0).getFirstName()+student.get(0).getLastName()+""+"/"+""+studentClass.get(j).getStudent().getRollNumber());
											reportDataBean.setStudentList(studentID);
										}
									}
								}
								
							}
							logger.debug("student list size--->"+reportDataBean.getStudentList().size());
						}
		}catch(Exception e){
			e.printStackTrace();
			logger.warn("Exception -->" + e.getMessage());
		}
}

	@Override
	public List<AttendanceDataBean> getAttendanceReport(ReportDataBean reportDataBean) {
		List<AttendanceDataBean> attendancedetail = new ArrayList<AttendanceDataBean>();
		Query q=null;
		String qry="";
		String tempqry="";
		List<StudentDetail> stuDetailList=null;
		List<ParentDetail> parDetailList=null;
		DateFormatSymbols symbols=new DateFormatSymbols();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			qry="from Attendance where";
			qry=qry+" school_ID="+Integer.parseInt(reportDataBean.getSchoolID());
			if (reportDataBean.isDateFlag()==true) {
				qry=qry+" and date='"+dateFormat.format(reportDataBean.getDate1())+"'";
			}	
			logger.debug("value class--->"+reportDataBean.getClassName());
			if (reportDataBean.isClassFlag()==true) {
				if (reportDataBean.getClassName().equalsIgnoreCase("All")) {
					logger.debug("class name is ALL");
				}
				else{
					logger.debug("class name else");
				qry=qry+" and class_='"+reportDataBean.getClassName()+"'";
				}
			}
			if (reportDataBean.isSectionFlag()==true) {
				if (reportDataBean.getSecName().equalsIgnoreCase("All")) {
					logger.debug("sec name is ALL");
				}
				else{
				qry=qry+" and section='"+reportDataBean.getSecName()+"'";
				}
			}
			
		
			logger.debug("final qry before excute in attendance----->"+qry);
			q=entitymanager.createQuery(qry);
			List<Attendance> attendancelist = (List<Attendance>) q.getResultList();
			logger.debug("---------attendance list--------->"+attendancelist.size());
			if (attendancelist.size()>0) {
				
				logger.debug("chk value----->"+reportDataBean.getMonth()+"=========>"+reportDataBean.getYear());
				
					for (int j = 0; j < attendancelist.size(); j++) {
							q = null;
							qry="";
							qry="from Attendanceclass where";
							qry=qry+" attendance_ID="+attendancelist.get(j).getAttendance_ID();
							
							
							if (reportDataBean.isStudentNameFlag()==true){
								if (reportDataBean.getStudentName().equalsIgnoreCase("All")) {
									logger.debug("----student name is All----------");
								}
								else{
								qry=qry+" and student_ID='"+reportDataBean.getStudentName().split("/")[1]+"'";
								}
							}
							if (reportDataBean.isMonthFlag()==true) {
								qry=qry+" and month='"+reportDataBean.getMonth()+"'";
							}
							if (reportDataBean.isYearFlag()==true) {
								qry=qry+" and year='"+reportDataBean.getYear()+"'";
							}
							logger.debug("final qry before excute in Attendanceclass----->"+qry);
							q=entitymanager.createQuery(qry);
							List<Attendanceclass> attendanceclasslist = (List<Attendanceclass>) q.getResultList();
							if (attendanceclasslist.size() > 0) {
								for (int i = 0; i < attendanceclasslist.size(); i++) {
									stuDetailList=new ArrayList<StudentDetail>();
									parDetailList=new ArrayList<ParentDetail>();
									AttendanceDataBean attendanceData=new AttendanceDataBean();
									attendanceData.setStudentName(attendanceclasslist.get(i).getStudentName());
									attendanceData.setStudentID(attendanceclasslist.get(i).getStudent_ID());
									
									attendanceData.setMonth(symbols.getMonths()[Integer.parseInt(attendanceclasslist.get(i).getMonth())-1]);
									
									attendanceData.setYear(attendanceclasslist.get(i).getYear());
									attendanceData.setTime(attendanceclasslist.get(i).getTime());
									attendanceData.setStatus(attendanceclasslist.get(i).getStatus());
									attendanceData.setAbsentMarkedBy(attendanceclasslist.get(i).getTeacher().getRollNumber());
									attendanceData.setClassname(attendanceclasslist.get(i).getAttendance().getClass_()+"/"+attendanceclasslist.get(i).getAttendance().getSection());
									attendanceData.setDate(attendanceclasslist.get(i).getAttendance().getDate());
									attendanceData.setPeriod(attendanceclasslist.get(i).getPeriod());
									
									stuDetailList=getStudentinfo(getStuIDbyStuRoll(attendanceclasslist.get(i).getStudent_ID()));
									
									parDetailList=getparentinfobyStuID(getStuIDbyStuRoll(attendanceclasslist.get(i).getStudent_ID()));
									
									
									attendancedetail.add(attendanceData);
								}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->" + e.getMessage());
			e.printStackTrace();
		}
		return attendancedetail;
	}
	
	public List<String> getStudentListBasedOnClassSec(int schoolID,String Class,String Sec) {
		List<String> studentList=null;
		Query q=null;
		logger.debug("--class----sec---schoolID----value-->"+Class+"---"+schoolID+"---->"+Sec);
		try {
			if (Class.equalsIgnoreCase("All")) {
				q = entitymanager.createQuery("from Class where school_ID=? and status='Active'");
				q.setParameter(1, schoolID);
			}
			else if (Sec.equalsIgnoreCase("All")) {
				q = entitymanager.createQuery("from Class where school_ID=? and className=? and status='Active'");
				q.setParameter(1, schoolID);
				q.setParameter(2, Class);
				
			}
			else if (Sec.equalsIgnoreCase("none")) {
				q = entitymanager.createQuery("from Class where school_ID=? and className=? and status='Active'");
				q.setParameter(1, schoolID);
				q.setParameter(2, Class);
			}
			else{
			q = entitymanager.createQuery("from Class where school_ID=? and className=? and classSection=? and status='Active'");
			q.setParameter(1, schoolID);
			q.setParameter(2, Class);
			q.setParameter(3, Sec);
			}
			List<Class> result = (List<Class>) q.getResultList();
			studentList=new ArrayList<String>();
			for (int i = 0; i < result.size(); i++) {
				
				q = entitymanager.createQuery("from StudentClass where class_ID=? and status='active'");
				q.setParameter(1, result.get(i).getClass_ID());
				List<StudentClass> studclslist = (List<StudentClass>) q.getResultList();
				for (int j = 0; j < studclslist.size(); j++) {
					studentList.add(String.valueOf(studclslist.get(j).getStudent().getStudent_ID()));
				}
			}
			logger.debug("------student list size------------->"+studentList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentList;
	}
	
	public List<StudentDetail> getStudentinfo(int studentID) {
		List<StudentDetail> studetail=new ArrayList<StudentDetail>();
		Query q=null;
		try {
			q = entitymanager.createQuery("from StudentDetail where student_ID=?");
			q.setParameter(1, studentID);
			List<StudentDetail> result = (List<StudentDetail>) q.getResultList();
			studetail.addAll(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studetail;
	}
	
	public List<ParentDetail> getparentinfobyStuID(int studentID) {
		List<ParentDetail> pardetail=new ArrayList<ParentDetail>();
		Query q=null;
		try {
			q = entitymanager.createQuery("from StudentParent where student_ID=? and status=?");
			q.setParameter(1, studentID);
			q.setParameter(2, "Active");
			List<StudentParent> result = (List<StudentParent>) q.getResultList();
			if (result.size()>0) {
				
				q = entitymanager.createQuery("from ParentDetail where parent_ID=?");
				q.setParameter(1, result.get(0).getParent().getParent_ID());
				List<ParentDetail> pardetailResult = (List<ParentDetail>) q.getResultList();
				pardetail.addAll(pardetailResult);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pardetail;
	}
	public Integer getStuIDbyStuRoll(String rollnumber) {
		int StudentID=0;
		Query q=null;
		try {
			if (!rollnumber.equalsIgnoreCase("")) {
				q = entitymanager.createQuery("from Student where rollNumber=? and status=?");
				q.setParameter(1, rollnumber);
				q.setParameter(2, "Active");
				List<Student> result = (List<Student>) q.getResultList();
				if (result.size()>0) {
					StudentID=result.get(0).getStudent_ID();
					logger.debug("student id ---->"+StudentID);
				}
			}
		}catch (Exception e) {
				e.printStackTrace();
		}
		return StudentID;
	}
	public String getClassNamebyStuId(int studentID) {
		Query q=null;
		String classname="";
		try {
			q = entitymanager.createQuery("from StudentClass where student_ID=? and status=?");
			q.setParameter(1, studentID);
			q.setParameter(2, "Active");
			List<StudentClass> result = (List<StudentClass>) q.getResultList();
			if (result.size()>0) {
				classname=result.get(0).getClazz().getClassName()+"/"+result.get(0).getClazz().getClassSection();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classname;
	}
	
	@Override
	public List<StudentPerformanceDataBean> getBehaviourReport(ReportDataBean reportDataBean) {
		Query q=null;
		int studentID=0;
		String qry="";
		String tempqry="";
		List<StudentPerformanceDataBean> studentperformacelist=null;
		List<String> studentList=null;
		List<StudentDetail> stuDetailList=null;
		List<ParentDetail> parDetailList=null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			studentperformacelist=new ArrayList<StudentPerformanceDataBean>();		
			studentList=new ArrayList<String>();
			qry="from StudentPerformance where";
			qry=qry+" school_ID="+Integer.parseInt(reportDataBean.getSchoolID());
		
			if (reportDataBean.isMonthFlag()==true) {
				qry=qry+" and month(createDate)="+Integer.parseInt(reportDataBean.getMonth());
			}
			if (reportDataBean.isYearFlag()==true) {
				qry=qry+" and year(createDate)="+Integer.parseInt(reportDataBean.getYear());
			}
			if (reportDataBean.isDateFlag()==true) {
				qry=qry+" and createDate='"+dateFormat.format(reportDataBean.getDate1())+"'";
			}
			
			if (reportDataBean.isClassFlag()==true) {
				if (("All").equalsIgnoreCase(reportDataBean.getClassName())) {
					logger.debug("inside class All");
					studentList.add("none");
				}
				else{
					if (reportDataBean.isSectionFlag()==true) {
						studentList=getStudentListBasedOnClassSec(Integer.parseInt(reportDataBean.getSchoolID()),reportDataBean.getClassName(),reportDataBean.getSecName());			
					logger.debug("value--list-size--->"+studentList.size());	
					
					}
					else{
						studentList=getStudentListBasedOnClassSec(Integer.parseInt(reportDataBean.getSchoolID()),reportDataBean.getClassName(),"none");			
						logger.debug("value--list-size--->"+studentList.size());	
					}
				}
			}
			if (reportDataBean.isStudentNameFlag()==true) {
				logger.debug("inside student  name flag");
				if (!("All").equalsIgnoreCase(reportDataBean.getClassName()) && !("All").equalsIgnoreCase(reportDataBean.getSecName())
						&& !("All").equalsIgnoreCase(reportDataBean.getStudentName())) {
					logger.debug("inside student  name flag Not All");
					studentID=getStudentID(reportDataBean.getStudentName().split("/")[1]);
					studentList=new ArrayList<String>();
					studentList.add(String.valueOf(studentID));
				}
				else if (reportDataBean.getStudentName().equalsIgnoreCase("All")) {
					logger.debug("inside student  name flag All");
					studentList.add("none");
				}
			}
			if (reportDataBean.isStudentNameFlag()==false && reportDataBean.isClassFlag()==false) {
				studentList.add("none");
			}
			qry=qry+" and status='Active'";
			tempqry=qry;
			HashSet<String> haslist=new HashSet<String>(studentList);
			studentList.clear();
			studentList.addAll(haslist);
			logger.debug("qry-----------1---------->"+qry);
			logger.debug("value size of studentList---->"+studentList.size());
				for (int i = 0; i < studentList.size(); i++) {
					logger.debug(studentList.get(i));
					if (reportDataBean.isStudentNameFlag()==true && !"none".equalsIgnoreCase(studentList.get(i))) {
						qry=tempqry+" and student_ID="+studentList.get(i);
					}
					else if ("none"!=studentList.get(i)) {
						qry=tempqry+" and student_ID="+studentList.get(i);
					}
					logger.debug("qry-----------2 final---------->"+qry);
					q=entitymanager.createQuery(qry);
					List<StudentPerformance> performanceList=(ArrayList<StudentPerformance>)q.getResultList();
					logger.debug("------value list size---------->"+performanceList.size());
					if(performanceList.size()>0){
						for(int j=0;j<performanceList.size();j++){
							stuDetailList=new ArrayList<StudentDetail>();
							parDetailList=new ArrayList<ParentDetail>();
							
							StudentPerformanceDataBean perform=new StudentPerformanceDataBean();
							perform.setStuAtt(performanceList.get(j).getAttitudeStatus());
							perform.setStuApp(performanceList.get(j).getAppearanceStatus());
							perform.setFromdate(performanceList.get(j).getCreateDate());
							perform.setStuName1(performanceList.get(j).getStudent().getRollNumber());
							
							stuDetailList=getStudentinfo(performanceList.get(j).getStudent().getStudent_ID());
							perform.setStuAddress(stuDetailList.get(0).getAddress1_Street());
							perform.setStuName(stuDetailList.get(0).getFirstName()+" "+stuDetailList.get(0).getLastName());
							perform.setStuCountry(stuDetailList.get(0).getPresentCountry());
							perform.setStuState(stuDetailList.get(0).getPresentState());
							
							parDetailList=getparentinfobyStuID(performanceList.get(j).getStudent().getStudent_ID());
							perform.setParName(parDetailList.get(0).getFirstName()+" "+parDetailList.get(0).getLastName());
							perform.setParentsPhoneNo(parDetailList.get(0).getPhoneNumber());
							perform.setParentsMail(parDetailList.get(0).getEmaiId());
							perform.setStuParRelation(parDetailList.get(0).getRelation());
							
							perform.setClassname(getClassNamebyStuId(performanceList.get(j).getStudent().getStudent_ID()));
							
							studentperformacelist.add(perform);
						}
						logger.debug("-----list value from return list----->"+studentperformacelist.size());
					}
				}
		/*	if (reportDataBean.getClassName().equalsIgnoreCase("All")) {
				q=entitymanager.createQuery("from StudentPerformance where school_ID=? and month(createDate)=? and year(createDate)=? and status='Active'");
				q.setParameter(1, reportDataBean.getSchoolID());
				q.setParameter(2, Integer.parseInt(reportDataBean.getMonth()));
				q.setParameter(3, Integer.parseInt(reportDataBean.getYear()));
			}
			else{*/
				
				/*q.setParameter(1, reportDataBean.getSchoolID());
				q.setParameter(2, studentID);
				q.setParameter(3, Integer.parseInt(reportDataBean.getMonth()));
				q.setParameter(4, Integer.parseInt(reportDataBean.getYear()));*/
			/*}*/
				
		} catch (Exception e) {
			logger.warn("Exception -->" + e.getMessage());
			e.printStackTrace();
		}
		return studentperformacelist;
	}
	@Transactional(value = "transactionManager")
	@Override
	public String deleteMenu(ReportDataBean reportDataBean) {
		String status="";
		try {
			ReportMenu reportmenu=entitymanager.find(ReportMenu.class, reportDataBean.getReportID());
			reportmenu.setStatus("DeActive");
			entitymanager.merge(reportmenu);
			entitymanager.flush();
			entitymanager.clear();
			status="Success";
		} catch (Exception e) {
			logger.warn("Exception -->" + e.getMessage());
			e.printStackTrace();
		}
		return status;
	}
	@Transactional(value = "transactionManager")
	@Override
	public String updateMenuEdit(ReportDataBean reportDataBean) {
		String status="Fail";
		Query q=null;
	try {
		q = entitymanager.createQuery("delete ReportFilterTable where has_report_menu_ID=?");
		q.setParameter(1, reportDataBean.getReportID());
		q.executeUpdate();
		logger.debug("-school ID-.>"+Integer.parseInt(reportDataBean.getSchoolID())+"-reportName-.>"+reportDataBean.getReportName()+"-report_menu_id-->"+reportDataBean.getReportID()+"--------->");
		q = entitymanager.createQuery("from ReportMenu where school_id=? and status=? and reportName=? and report_menu_id!=?");
		q.setParameter(1, Integer.parseInt(reportDataBean.getSchoolID()));
		q.setParameter(2, "Active");
		q.setParameter(3, reportDataBean.getReportName());
		q.setParameter(4, reportDataBean.getReportID());
		
		List<ReportMenu> reportlist = (List<ReportMenu>) q.getResultList();
		logger.debug("reportlist--->"+reportlist.size());
		if(reportlist.size()>0){
			status="Exist";	
		}
		else{
			ReportMenu reportMenu= entitymanager.find(ReportMenu.class, reportDataBean.getReportID());
			reportMenu.setReportName(reportDataBean.getReportName());
			reportMenu.setReportDescription(reportDataBean.getReportdescription());
			entitymanager.merge(reportMenu);
			if(reportDataBean.getFilterlist().size()>0){
				for (int i = 0; i <reportDataBean.getFilterlist().size(); i++) {
					ReportFilterTable reportFilterTableinsert=new ReportFilterTable();
					reportFilterTableinsert.setFilter_activeStatus(String.valueOf(reportDataBean.getFilterlist().get(i).isActiveStatus()));
					reportFilterTableinsert.setFilterName(reportDataBean.getFilterlist().get(i).getFilterName());
					reportFilterTableinsert.setReportMenu(entitymanager.find(ReportMenu.class, reportDataBean.getReportID()));
					entitymanager.persist(reportFilterTableinsert);
					entitymanager.flush();
					entitymanager.clear();	
				}
			status="Success";
			}
		}
	} catch (Exception e) {
		logger.warn("Exception -->" + e.getMessage());
		e.printStackTrace();
	}
		return status;
	}

	@Override
	public List<ReportCardDataBean> getReportCardReport(ReportDataBean reportDataBean) {
		Query q=null;
		int studentID=0;
		String qry="";
		String tempqry="";
		List<ReportCardDataBean> studentMarklist=null;
		List<String> studentList=null;
		
		List<StudentDetail> stuDetailList=null;
		List<ParentDetail> parDetailList=null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			studentMarklist=new ArrayList<ReportCardDataBean>();		
			studentList=new ArrayList<String>();
			qry="from Reportcard where";
			qry=qry+" school_ID="+Integer.parseInt(reportDataBean.getSchoolID());
		
			if (reportDataBean.isExamTitleFlag()==true) {
				qry=qry+" and examTitle='"+reportDataBean.getExamTitle().split("->")[0]+"'";
			}
			if (reportDataBean.isClassFlag()==true) {
				if (("All").equalsIgnoreCase(reportDataBean.getClassName())) {
					logger.debug("inside class All");
				}
				else{
					if (reportDataBean.isSectionFlag()==true && !("All").equalsIgnoreCase(reportDataBean.getSecName())) {
					logger.debug("inside sec flag true");
					qry=qry+" and class_ID="+getClassIDbyClassName(Integer.parseInt(reportDataBean.getSchoolID()),
							reportDataBean.getClassName(),reportDataBean.getSecName());
					}
				}
			}
			if (reportDataBean.isStudentNameFlag()==true) {
				if (reportDataBean.getStudentName().equalsIgnoreCase("All")) {
				logger.debug("report student name All");	
				studentList=getStudentListBasedOnClassSec(Integer.parseInt(reportDataBean.getSchoolID()),
							reportDataBean.getClassName(),reportDataBean.getSecName());
				}
				else{
				qry=qry+" and student_ID="+getStuIDbyStuRoll(reportDataBean.getStudentName().split("/")[1]);
				studentList.add("none");
				}
			}
			else{
				studentList.add("none");
			}
					logger.debug("qry-----------final---------->"+qry);
					tempqry=qry;
					for (int i = 0; i < studentList.size(); i++) {
						qry=tempqry;
					
						if (StringUtils.isEmpty(reportDataBean.getStudentName())) {
						
						}
						else{
							if ("All".equalsIgnoreCase(reportDataBean.getStudentName())) {
								qry=qry+" and student_ID="+getStuIDbyStuRoll(studentList.get(i));
							
							}
						}
						
						q=entitymanager.createQuery(qry);
						List<Reportcard> reportCardList=(ArrayList<Reportcard>)q.getResultList();
						logger.debug("------value list size---------->"+reportCardList.size());
						if(reportCardList.size()>0){
							for(int j=0;j<reportCardList.size();j++){
								stuDetailList=new ArrayList<StudentDetail>();
								parDetailList=new ArrayList<ParentDetail>();
								
								ReportCardDataBean cardReport=new ReportCardDataBean();
								cardReport.setExamMarkTitle(reportCardList.get(j).getExamTitle());
								cardReport.setGrade(reportCardList.get(j).getGrade());
								cardReport.setResultStatus(reportCardList.get(i).getResultStatus());
								
								stuDetailList=getStudentinfo(reportCardList.get(j).getStudent().getStudent_ID());
								cardReport.setStudentName(stuDetailList.get(0).getFirstName()+" "+stuDetailList.get(0).getLastName());
								cardReport.setStudentRollNumber(reportCardList.get(0).getStudent().getRollNumber());
								cardReport.setStudentCountry(stuDetailList.get(0).getPresentCountry());
								
								parDetailList=getparentinfobyStuID(reportCardList.get(j).getStudent().getStudent_ID());
								cardReport.setStudentParentName(parDetailList.get(0).getFirstName()+" "+parDetailList.get(0).getLastName());
								cardReport.setStudentParentRelation(parDetailList.get(0).getRelation());
								cardReport.setMailid(parDetailList.get(0).getEmaiId());
								cardReport.setPhoneno(parDetailList.get(0).getPhoneNumber());
								
								cardReport.setSubjectName(reportCardList.get(j).getSubject().getSujectName());
								cardReport.setSubjectCode(reportCardList.get(j).getSubject().getSubjectCode());
								cardReport.setTeacherRollNumber(reportCardList.get(j).getTeacher().getRollNumber());
								cardReport.setClassName(getClassNamebyStuId(reportCardList.get(j).getStudent().getStudent_ID()));
						
								studentMarklist.add(cardReport);
							}
					}
					
					
						logger.debug("-----list value from return list----->"+studentMarklist.size());
					}
				
				
		} catch (Exception e) {
			logger.warn("Exception -->" + e.getMessage());
			e.printStackTrace();
		}
		return studentMarklist;
	}

	@Override
	public List<String> getExamListforMark(ReportDataBean reportDataBean) {
		Query q=null;
		List<String> examlist=null;
		String qry="";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
		
			examlist=new ArrayList<String>();
			qry="from ExamTable where";
			qry=qry+" school_ID="+Integer.parseInt(reportDataBean.getSchoolID());
			if (reportDataBean.isSectionFlag()==true) {
				if (reportDataBean.getSecName().equalsIgnoreCase("All")) {
					logger.debug("-----inside sec name all---------");
				}
				else{
				qry=qry+" and class_name='"+reportDataBean.getClassName()+"/"+reportDataBean.getSecName()+"'";
				}
			}
			if (reportDataBean.isYearFlag()==true) {
				qry=qry+" and year(startDate)="+Integer.parseInt(reportDataBean.getYear());
			}
			if (reportDataBean.isMonthFlag()==true) {
				qry=qry+" and month(startDate)="+Integer.parseInt(reportDataBean.getMonth());
			}
			if (reportDataBean.isDateFlag()==true) {
				qry=qry+" and startDate='"+dateFormat.format(reportDataBean.getDate1())+"'";
			}
			logger.debug("final qry----------->"+qry);
			q=entitymanager.createQuery(qry);
			List<ExamTable> examtableList=(ArrayList<ExamTable>)q.getResultList();
			if (examtableList.size()>0) {
				for (int i = 0; i < examtableList.size(); i++) {
					examlist.add(examtableList.get(i).getExamTitle()+"->"+examtableList.get(i).getClass_name());	
				}
			}
			logger.debug("----------exam tablel list----------->"+examtableList.size());
		} catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return examlist;
	}
	private int getClassIDbyClassName(int school_ID,String className,String sectionName) {
		logger.debug("inside classid");
		Query q = null;int classid=0;
		try {
			logger.debug("classs"+className);
				if(school_ID > 0){
					q = entitymanager.createQuery("from Class where school_ID=? and className=? and classSection=? and status='Active'");
					q.setParameter(1, school_ID);
					q.setParameter(2, className);
					q.setParameter(3, sectionName);
					List<Class> classList  = (List<Class>) q.getResultList();
					if(classList.size()>0){
						classid=classList.get(0).getClass_ID();
						logger.debug("classid"+classid);
					}
				}
			} catch (Exception e) {
				logger.error("Exception -->"+e.getMessage());
			}
			return classid;
		}

	
	
	
	
	@Override
	public List<String> teacherNameList(ReportDataBean reportDataBean) {
		
	//	public List<String> teacherNameList(ReportDataBean reportDataBean) {

		Query q=null;
		List<String> teacherNameList=null;
		String qry="";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			teacherNameList=new ArrayList<String>();
		logger.debug("sub type in teacher name list--->"+reportDataBean.getReportsubType());
			if (reportDataBean.getReportsubType().equalsIgnoreCase("Attendance")) {
				q=entitymanager.createQuery("from Teacher where school_ID=? and status='Active'");
				q.setParameter(1, Integer.parseInt(reportDataBean.getSchoolID()));
				List<Teacher> teacher=(ArrayList<Teacher>)q.getResultList();
				if (teacher.size()>0) {
					for (int i = 0; i < teacher.size(); i++) {
						q=entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
						q.setParameter(1, teacher.get(i).getTeacher_ID());
						List<TeacherDetail> teacherDetail=(ArrayList<TeacherDetail>)q.getResultList();
						teacherNameList.add(teacherDetail.get(0).getFirstName()+" "+teacherDetail.get(0).getLastName()+"/"+teacher.get(i).getRollNumber());	
						logger.debug("teacher name adding list data----->"+
								teacherDetail.get(0).getFirstName()+" "+teacherDetail.get(0).getLastName()+"/"+teacher.get(i).getRollNumber());
					}
				}
			}
			if (reportDataBean.getReportsubType().equalsIgnoreCase("TimeTable")) {
				logger.debug("-------------inside teacher name timetable--------------");
				if (reportDataBean.getClassName().equalsIgnoreCase("All")) {
					q=entitymanager.createQuery("from Teacher where school_ID=? and status='Active'");
					q.setParameter(1, Integer.parseInt(reportDataBean.getSchoolID()));
					List<Teacher> teacher=(ArrayList<Teacher>)q.getResultList();
					if (teacher.size()>0) {
						for (int i = 0; i < teacher.size(); i++) {
							q=entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
							q.setParameter(1, teacher.get(i).getTeacher_ID());
							List<TeacherDetail> teacherDetail=(ArrayList<TeacherDetail>)q.getResultList();
							teacherNameList.add(teacherDetail.get(0).getFirstName()+" "+teacherDetail.get(0).getLastName()+"/"+teacher.get(i).getRollNumber());	
							logger.debug("teacher name adding list data----->"+
									teacherDetail.get(0).getFirstName()+" "+teacherDetail.get(0).getLastName()+"/"+teacher.get(i).getRollNumber());
						}
					}
				}
				else{
					int classID=getClassIDbyClassName(Integer.parseInt(reportDataBean.getSchoolID()),reportDataBean.getClassName().split("/")[0], reportDataBean.getClassName().split("/")[1]);
				logger.debug("classID---->"+classID);
					q=entitymanager.createQuery("from TeacherClass where class_ID=? and status=?");
					q.setParameter(1, classID);
					q.setParameter(2, "Active");
					List<TeacherClass> teacherclass=(ArrayList<TeacherClass>)q.getResultList();
					if (teacherclass.size()>0) {
						for (int i = 0; i < teacherclass.size(); i++) {
							q=entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
							q.setParameter(1, teacherclass.get(i).getTeacher().getTeacher_ID());
							List<TeacherDetail> teacherDetail=(ArrayList<TeacherDetail>)q.getResultList();
							teacherNameList.add(teacherDetail.get(0).getFirstName()+" "+teacherDetail.get(0).getLastName()+"/"+teacherclass.get(i).getTeacher().getRollNumber());	
							logger.debug("teacher name adding list data----->"+
									teacherDetail.get(0).getFirstName()+" "+teacherDetail.get(0).getLastName()+"/"+teacherclass.get(i).getTeacher().getRollNumber());
						}
					}
				}
				
			}
			
		
		} catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return teacherNameList;
	}


	@Override
	public List<ReportDataBean> getTeacherAttendanceReport(ReportDataBean reportDataBean) { 
		List<ReportDataBean> attendancedetail = new ArrayList<ReportDataBean>();
		Query q=null;
		String qry="";
		String tempqry="";
		List<StudentDetail> stuDetailList=null;
		List<ParentDetail> parDetailList=null;
		DateFormatSymbols symbols=new DateFormatSymbols();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat Monthformat = new SimpleDateFormat("MMMM"); 
		SimpleDateFormat yearformat = new SimpleDateFormat("yyyy"); 
       
		try {
		logger.debug("inside teacher attendance report method daoimpl");
			qry="from TeacherAttendance where";
			qry=qry+" school_ID="+Integer.parseInt(reportDataBean.getSchoolID());
			
			if (reportDataBean.isDateFlag()==true) {
				qry=qry+" and date='"+dateFormat.format(reportDataBean.getDate1())+"'";
			}	
			if (reportDataBean.isYearFlag()==true) {
				qry=qry+" and year(date)="+Integer.parseInt(reportDataBean.getYear());
			}
			if (reportDataBean.isMonthFlag()==true) {
				qry=qry+" and month(date)="+Integer.parseInt(reportDataBean.getMonth());
			}
			if (reportDataBean.isLeaveTypeFlag()==true) {
				if (!reportDataBean.getLeaveType().equalsIgnoreCase("None") && !reportDataBean.getLeaveType().equalsIgnoreCase("All")) {
					qry=qry+" and leaveType='"+reportDataBean.getLeaveType()+"'";
				}
			}
			if (reportDataBean.isAttendanceStatusFlag()==true) {
				if (!reportDataBean.getAttendanceStatus().equalsIgnoreCase("Both")) {
				qry=qry+" and status='"+reportDataBean.getAttendanceStatus()+"'";
				}
			}
			
			if (reportDataBean.isTeacherNameFlag()==true) {
				if (!reportDataBean.getTeacherName().equalsIgnoreCase("All")) {
				
					qry=qry+" and teacher_ID="+getTeacherID(reportDataBean.getTeacherName().split("/")[1],Integer.parseInt(reportDataBean.getSchoolID()));
				}
				
			}	
			
			
			logger.debug("final qry before excute in Teacher Attendance----->"+qry);
			
			q=entitymanager.createQuery(qry);
			List<TeacherAttendance> attendancelist = (List<TeacherAttendance>) q.getResultList();
			
			logger.debug("---------attendance list--------->"+attendancelist.size());
			
			if (attendancelist.size()>0) {
					for (int i = 0; i < attendancelist.size(); i++) {
					
							ReportDataBean attendanceData=new ReportDataBean();
							attendanceData.setTeacherRoll(attendancelist.get(i).getTeacher().getRollNumber());
							attendanceData.setTeacherName(getTeacherNamebyTeacherID(0, attendancelist.get(i).getTeacher().getRollNumber()));
							attendanceData.setDate1(attendancelist.get(i).getDate());
							attendanceData.setMonth(Monthformat.format(attendancelist.get(i).getDate()));
							attendanceData.setYear(yearformat.format(attendancelist.get(i).getDate()));
							attendanceData.setAttendanceStatus((attendancelist.get(i).getStatus()));
							attendanceData.setLeaveType(attendancelist.get(i).getLeaveType());
							attendanceData.setInTime(attendancelist.get(i).getInTime());
							attendanceData.setOutTime(attendancelist.get(i).getOut_Time());
							
							attendancedetail.add(attendanceData);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->" + e.getMessage());
			e.printStackTrace();
		}
		return attendancedetail;
	}

	@Override
	public List<ReportDataBean> getTeacherTimetableReport(ReportDataBean reportDataBean) { 

		List<ReportDataBean> timetabledetail = new ArrayList<ReportDataBean>();
		Query q=null;
		String qryclasstable="";
		String qry="";
		String classNameSec="";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat Monthformat = new SimpleDateFormat("MMMM"); 
		SimpleDateFormat yearformat = new SimpleDateFormat("yyyy"); 
       List<Integer> classtableID= new ArrayList<Integer>();
       List<ClassTimeTable> timeTableDataList=new ArrayList<ClassTimeTable>();
		try {
		
				if (reportDataBean.isDayFlag()==true || reportDataBean.isMonthFlag()==true || reportDataBean.isYearFlag()==true || reportDataBean.isClassFlag()==true) {
					qryclasstable="from ClassTable where";
					qryclasstable=qryclasstable+" school_ID="+Integer.parseInt(reportDataBean.getSchoolID());
					
					if (reportDataBean.isDayFlag()==true) {
						qryclasstable=qryclasstable+" and day='"+reportDataBean.getDay()+"'";
					}	
					if (reportDataBean.isYearFlag()==true) {
						qryclasstable=qryclasstable+" and year='"+reportDataBean.getYear()+"'";
					}
					if (reportDataBean.isMonthFlag()==true) {
						qryclasstable=qryclasstable+" and month='"+reportDataBean.getMonth()+"'";
					}
					if (reportDataBean.isClassFlag()==true && !reportDataBean.getClassName().equalsIgnoreCase("All")) {
						System.out.println("class id by name ---------->"+getClassIDbyClassName(Integer.parseInt(reportDataBean.getSchoolID()),reportDataBean.getClassName().split("/")[0], reportDataBean.getClassName().split("/")[1]));
						System.out.println("class name --->"+reportDataBean.getClassName());
						qryclasstable=qryclasstable+" and class_ID="+getClassIDbyClassName(Integer.parseInt(reportDataBean.getSchoolID()),reportDataBean.getClassName().split("/")[0], reportDataBean.getClassName().split("/")[1]);
					}
					qryclasstable=qryclasstable+" and status='Active'";
				
					logger.debug("qryclasstable-------->"+qryclasstable);
					q=entitymanager.createQuery(qryclasstable);
					List<ClassTable> classtableList = (List<ClassTable>) q.getResultList();
					if (classtableList.size()>0) {
						for (int i = 0; i < classtableList.size(); i++) {
							classtableID.add(classtableList.get(i).getClass_table_ID());
						}
					}
				}
				else{
					classtableID.add(0);
				}
		
		logger.debug("classtable ID size--------->"+classtableID.size());
	
		
		
		/*-----------------2nd qry----------------*/
		for (int i = 0; i < classtableID.size(); i++) {
			
		
			qry="from ClassTimeTable where";
			qry=qry+" school_ID="+Integer.parseInt(reportDataBean.getSchoolID());
			
			
			if (classtableID.get(i)!=0) {
					qry=qry+" and class_table_ID="+classtableID.get(i);
			}
			if (reportDataBean.isTeacherNameFlag()==true) {
				if (!reportDataBean.getTeacherName().equalsIgnoreCase("All")) {
					System.out.println("teacher roll number"+reportDataBean.getTeacherName().split("/")[1]);
					System.out.println("teacher id----------->"+getTeacherID(reportDataBean.getTeacherName().split("/")[1],Integer.parseInt(reportDataBean.getSchoolID())));
					qry=qry+" and teacher_ID="+getTeacherID(reportDataBean.getTeacherName().split("/")[1],Integer.parseInt(reportDataBean.getSchoolID()));
				}
			}	
			if (reportDataBean.isSubjectNameFlag()==true) {
				if (!reportDataBean.getSubjectName().equalsIgnoreCase("All")) {
					qry=qry+" and subject='"+reportDataBean.getSubjectName().split("/")[0]+"'";
					qry=qry+" and subjectCode='"+reportDataBean.getSubjectName().split("/")[1]+"'";
				}
			}
			if (reportDataBean.isPeriodFlag()==true) {
				if (!reportDataBean.getPeriod().equalsIgnoreCase("All")) {
					qry=qry+" and period='"+reportDataBean.getPeriod()+"'";
				}
			}
			if (reportDataBean.isDayFlag()==true) {
					qry=qry+" and day='"+reportDataBean.getDay()+"'";
			}
		
			q=entitymanager.createQuery(qry);
			List<ClassTimeTable> timeTableList = (List<ClassTimeTable>) q.getResultList();
			
			
			timeTableDataList.addAll(timeTableList);
			
		}
	
			if (timeTableDataList.size()>0) {
				
					for (int i = 0; i < timeTableDataList.size(); i++) {
							ReportDataBean timeTableData=new ReportDataBean();
							if (!"".equalsIgnoreCase(timeTableDataList.get(i).getSubject()) && timeTableDataList.get(i).getSubject()!=null) {
								
								timeTableData.setTeacherName(getTeacherNamebyTeacherID(timeTableDataList.get(i).getTeacher().getTeacher_ID(),"none"));
								timeTableData.setTeacherRoll(timeTableDataList.get(i).getTeacher().getRollNumber());
								timeTableData.setSubjectName(timeTableDataList.get(i).getSubject());
								timeTableData.setInTime(timeTableDataList.get(i).getStartTime());
								timeTableData.setOutTime(timeTableDataList.get(i).getEndTime());
								timeTableData.setSubjectCode(timeTableDataList.get(i).getSubjectCode());
							}
							else{
							
								timeTableData.setTeacherName("-");
								timeTableData.setTeacherRoll("-");
								timeTableData.setSubjectName("-");
								timeTableData.setInTime("-");
								timeTableData.setOutTime("-");
								timeTableData.setSubjectCode("-");
							}
						
							timeTableData.setDay(timeTableDataList.get(i).getDay());
							timeTableData.setInTime(timeTableDataList.get(i).getStartTime());
							timeTableData.setOutTime(timeTableDataList.get(i).getEndTime());
							timeTableData.setSubjectCode(timeTableDataList.get(i).getSubjectCode());
							timeTableData.setPeriod(timeTableDataList.get(i).getPeriod());
							timeTableData.setYear(timeTableDataList.get(i).getClassTable().getYear());
							timeTableData.setMonth(timeTableDataList.get(i).getClassTable().getMonth());
/*							timeTableData.setClassName(getClassNamebyClassID(timeTableDataList.get(i).getClassTable().getClass(getc)));*/
							

							timeTableData.setClassName(timeTableDataList.get(i).getClassTable().getClassz().getClassName()+"/"+
									timeTableDataList.get(i).getClassTable().getClassz().getClassSection());
							
							timetabledetail.add(timeTableData);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->" + e.getMessage());
			e.printStackTrace();
		}
		return timetabledetail;
	
	}

	private String getTeacherNamebyTeacherID(int teacherID , String teacherRoll) {
		logger.debug("inside classid");
		Query q = null;
		String teacherName="";
		try {
			logger.debug("teacher ID---"+teacherID);
				if(teacherID > 0){
					q = entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
					q.setParameter(1, teacherID);
					
					List<TeacherDetail> teacherList  = (List<TeacherDetail>) q.getResultList();
					if(teacherList.size()>0){
						teacherName=teacherList.get(0).getFirstName()+" "+teacherList.get(0).getLastName();
						logger.debug("teacher Name------>"+teacherName);
					}
				}
				else if (teacherID==0 && !teacherRoll.equalsIgnoreCase("none") && !"".equalsIgnoreCase(teacherRoll)) {
					q = entitymanager.createQuery("from Teacher where roll_number=? and status=?");
					q.setParameter(1, teacherRoll);
					q.setParameter(2, "Active");
					List<Teacher> teacherList  = (List<Teacher>) q.getResultList();
					
					q = entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
					q.setParameter(1, teacherList.get(0).getTeacher_ID());
					
					List<TeacherDetail> teacherdetailList  = (List<TeacherDetail>) q.getResultList();
					if(teacherdetailList.size()>0){
						teacherName=teacherdetailList.get(0).getFirstName()+" "+teacherdetailList.get(0).getLastName();
						logger.debug("teacher Name------>"+teacherName);
					}
					
				}
			} catch (Exception e) {
				logger.error("Exception -->"+e.getMessage());
			}
			return teacherName;
		}
	
	@Override
	public List<String> subjectNameList(ReportDataBean reportDataBean) {
		Query q=null;
		List<String> subjectNameList=null;
		String qry="";
		int classID=0;
		int teacherID=0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			subjectNameList=new ArrayList<String>();
		
			if (reportDataBean.getReportsubType().equalsIgnoreCase("TimeTable")) {
				
				if (reportDataBean.getClassName().equalsIgnoreCase("All") && reportDataBean.getTeacherName().equalsIgnoreCase("All")) {
					q=entitymanager.createQuery("from Subject where school_ID=? and status=?");
					q.setParameter(1, Integer.parseInt(reportDataBean.getSchoolID()));
					q.setParameter(2, "Active");
					List<Subject> subjectlist=(ArrayList<Subject>)q.getResultList();
					
					if (subjectlist.size()>0) {
						for (int i = 0; i < subjectlist.size(); i++) {
							subjectNameList.add(subjectlist.get(i).getSujectName()+"/"+subjectlist.get(i).getSubjectCode());	
							
						}
					}
				}
				else if (!reportDataBean.getClassName().equalsIgnoreCase("All") && reportDataBean.getReference().equalsIgnoreCase("Class")) {
					classID=getClassIDbyClassName(Integer.parseInt(reportDataBean.getSchoolID()),reportDataBean.getClassName().split("/")[0], reportDataBean.getClassName().split("/")[1]);
					logger.debug("classID---->"+classID);
					q=entitymanager.createQuery("from ClassSubject where class_ID=? and status=?");
					q.setParameter(1, classID);
					q.setParameter(2, "Active");
					List<ClassSubject> subjectlist=(ArrayList<ClassSubject>)q.getResultList();
					
					if (subjectlist.size()>0) {
						for (int i = 0; i < subjectlist.size(); i++) {
							subjectNameList.add(subjectlist.get(i).getSubject().getSujectName()+"/"+subjectlist.get(i).getSubject().getSubjectCode());	
						
						}
					}
				
				}
				else if (reportDataBean.getReference().equalsIgnoreCase("Teacher") && !reportDataBean.getTeacherName().equalsIgnoreCase("All") ){
					
					teacherID=getTeacherID(reportDataBean.getTeacherName().split("/")[1], Integer.parseInt(reportDataBean.getSchoolID()));
				
					q=entitymanager.createQuery("from TeacherSubject where teacher_ID=? and status=?");
					q.setParameter(1, teacherID);
					q.setParameter(2, "Active");
					List<TeacherSubject> subjectlist=(ArrayList<TeacherSubject>)q.getResultList();
					
					if (subjectlist.size()>0) {
						for (int i = 0; i < subjectlist.size(); i++) {
							subjectNameList.add(subjectlist.get(i).getSubject().getSujectName()+"/"+subjectlist.get(i).getSubject().getSubjectCode());	
						
						}
					}
				
				 
				}
				
			}
			
		logger.debug("----------subjectName List----------->"+subjectNameList.size());
		} catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return subjectNameList;
	}
	
	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	public String getClassEdit(StudentDataBean studentDataBean,String personID) {
		logger.info("----------inside getClassEdit---------");
			Query q = null; int studid = 0;
			List<Person> roleStatus = null;String status = "Fail";
			try {
				roleStatus = new ArrayList<Person>();
				if (personID != null) {
					roleStatus = getRollType(personID);
					StringTokenizer st = new StringTokenizer(studentDataBean.getTeaclssection());
					String className = st.nextToken("/");
					logger.debug("--------  className --------"+className);
					String classSection=studentDataBean.getTeaclssection();
					String section=classSection.substring(classSection.lastIndexOf("/")+1);
					logger.debug("--------  class Section --------"+section);
					if (roleStatus.size() > 0) {
							int schoolid=roleStatus.get(0).getSchool().getSchool_ID();
							q = entitymanager.createQuery("from Class where className=? and classSection=? and school_ID=? and status='Active'");
							q.setParameter(1, className);
							q.setParameter(2, section);
							q.setParameter(3, schoolid);
							List<Class> classlist1 = (List<Class>) q.getResultList();
							logger.debug("-------list size-----" + classlist1.size());
							if (classlist1.size() > 0) {
								logger.debug("----class id------" + classlist1.get(0).getClass_ID());
								q = null;
								q = entitymanager.createQuery("from Student where roll_number=? and school_ID=? and status='Active'");
								q.setParameter(1, studentDataBean.getStuRollNo());
								q.setParameter(2, schoolid);
								List<Student> studlist = (List<Student>) q.getResultList();
								if (studlist.size() > 0) {
									studid = studlist.get(0).getStudent_ID();
								q = null;
								q = entitymanager.createQuery("from StudentDetail where student_ID=?");
								q.setParameter(1, studid);
								List<StudentDetail> studlist1 = (List<StudentDetail>) q.getResultList();
								if(studlist1.size()>0){
								q = null;
								q = entitymanager.createQuery("from StudentClass where student_ID=?");
								q.setParameter(1, studid);
								List<StudentClass> classlist = (List<StudentClass>) q.getResultList();
								logger.debug("---studentclass size----" + classlist.size());								
								if (studlist1.size() > 0 && classlist.size() > 0) {
									logger.info("----------------- if ----------------");
									StudentDetail studobj = entitymanager.find(StudentDetail.class, studlist1.get(0).getHas_student_ID());
									StudentClass studobj1 = entitymanager.find(StudentClass.class,classlist.get(0).getHasstudent_class_ID());
									studobj1.setClazz(entitymanager.find(Class.class, classlist1.get(0).getClass_ID()));				
									entitymanager.merge(studobj);
									entitymanager.merge(studobj1);
								}else {
									logger.info("---------------- update else -------------------");
									/*StudentDetail studobj = entitymanager.find(StudentDetail.class, studlist1.get(0).getHas_student_ID());				
									studobj.setStudentid(entitymanager.find(Student.class, studid));
									entitymanager.merge(studobj);
									entitymanager.flush();
									entitymanager.clear();*/
									StudentClass studobj1 = new StudentClass();
									studobj1.setClazz(entitymanager.find(Class.class, classlist1.get(0).getClass_ID()));
									studobj1.setStudent(entitymanager.find(Student.class, studid));	
									studobj1.setStatus("Active");
									entitymanager.persist(studobj1);
									entitymanager.flush();
									entitymanager.clear();
																	
								}
								status = "Success";
							}
						  }
						  logger.info("If condition status ----------"+status);
						}else{
							status="Fail";
						}
						logger.info("Status ----------"+status);
					}
				}
			} catch (Exception e) {
				logger.warn(" exception - "+e);
				e.printStackTrace();
			}
			return status;
		}
	
	// New 
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public String insertAttendance1(String personID, String schoolID,ArrayList<AttendanceDataBean> list) {
		logger.info("-------------- Inside InsertAttendance Mobile dao Method Calling --------------");	
		String status="fail";
		List<Person> person = null;
		Query query = null;
		Calendar now = Calendar.getInstance();
		try {
			logger.info("-------------- Inside Try --------------"); // 	
			  for( AttendanceDataBean value : list ) {
					logger.info("-------------- Test 1 --------------");	
					logger.info("-------------- School_ID --------------"+schoolID);
					logger.info("-------------- Person_ID --------------"+personID);
					logger.info("-------------- Test 2 --------------");	
					logger.info("-------------- Class Name --------------"+value.getClassname().split("/")[0]);
					logger.info("-------------- Class Section --------------"+value.getClassname().split("/")[1]);
					logger.info("-------------- Date --------------"+value.getDate());
					
					Person p = entitymanager.find(Person.class, Integer.valueOf(personID));
					Teacher t = entitymanager.find(Teacher.class, p.getTeachers().get(0).getTeacher_ID());
					
					// Parent Table data set
					Attendance attance = new Attendance();
					attance.setClass_(value.getClassname().split("/")[0]);
					attance.setSection(value.getClassname().split("/")[1]);
					attance.setDate(value.getDate());					
					attance.setSchool(entitymanager.find(School.class, Integer.valueOf(schoolID)));	
					entitymanager.persist(attance);
					//entitymanager.flush();
					//entitymanager.clear();
					
					logger.info("-------------- Parent table saved sucessfully --------------");
					// Child table data set 
					Attendanceclass ac = new Attendanceclass();
					ac.setStatus(value.getStatus1());
					ac.setStudentName(value.getStudentName());					
					ac.setYear("" + now.get(Calendar.YEAR));
					ac.setMonth("" +(now.get(Calendar.MONTH) + 1));
					ac.setTeacher(t);
					
					ac.setPeriod(value.getPeriod());
					//if(schoolid==35 || schoolid==36 || schoolid==37 || schoolid==38|| schoolid==39 || schoolid==40 || schoolid==41 || schoolid==42 || schoolid==43){	
						//ac.setTime(timestamp);
					//}else{	
						ac.setTime(Timestamp.valueOf(GetTimeZone.getIndonesiaTimeHour("Asia/Makassar")));
					//}
					logger.info("-------------- Test 4 --------------");	
					logger.info("-------------- Status --------------"+value.getStatus1());
					logger.info("-------------- Student Name --------------"+value.getStudentName());
					logger.info("-------------- Year --------------"+now.get(Calendar.YEAR));
					logger.info("-------------- Month --------------"+(now.get(Calendar.MONTH) + 1));
					logger.info("-------------- Period --------------"+value.getPeriod());
					logger.info("-------------- Time --------------"+ac.getTime());

					logger.info("-------------- Test 5 --------------");	
					logger.info("-------------- Teacher_ID --------------"+t.getTeacher_ID());
					
					query = entitymanager.createQuery("from Student where rollNumber=?");
					logger.info("-------------- Roll Number --------------"+value.getStudentID());	
					query.setParameter(1, value.getStudentID());
					List<Student> studnetID = (List<Student>) query.getResultList();
					logger.info("-------------- Student ID  --------------"+studnetID.get(0).getStudent_ID());
					ac.setStudent_ID(String.valueOf(studnetID.get(0).getStudent_ID()));
					logger.info("-------------- Test 7 --------------");	
					ac.setAttendance(attance);
					attance.getAttendanceclasses().add(ac);					
					entitymanager.persist(ac);
					entitymanager.flush();
					entitymanager.clear();
					logger.info("-------------- Success  --------------");						
					status="Success";
					return status;
			  }		
			return status;	
		}catch(Exception e) {
			status="fail";
			logger.info("Exception"+ e.getMessage());
			e.printStackTrace();
		}
		finally {
			
		}
		
	return status;
	}
	
}
	