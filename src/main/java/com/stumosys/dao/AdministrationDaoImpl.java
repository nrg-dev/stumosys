package com.stumosys.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import javax.faces.context.FacesContext;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.stumosys.domain.AttendanceDataBean;
import com.stumosys.domain.BookSaleDataBean;
import com.stumosys.domain.ClassTimeTableDataBean;
import com.stumosys.domain.HomeworkDatabean;
import com.stumosys.domain.LibraryDataBean;
import com.stumosys.domain.LoginAccess;
import com.stumosys.domain.NoticeBoardDataBean;
//import com.stumosys.domain.ParentsDataBean;
import com.stumosys.domain.PaymentDatabean;
import com.stumosys.domain.ReportCardDataBean;
import com.stumosys.domain.StudentDataBean;
import com.stumosys.domain.StudentPerformanceDataBean;
import com.stumosys.domain.TimeTableDataBean;
//import com.stumosys.managedBean.StudentPerformanceViewMB;
import com.stumosys.shared.Attendance;
import com.stumosys.shared.Attendanceclass;
import com.stumosys.shared.Browerbook;
import com.stumosys.shared.Class;
import com.stumosys.shared.ClassSubject;
import com.stumosys.shared.ClassTable;
import com.stumosys.shared.ClassTimeTable;
import com.stumosys.shared.ExamTable;
//import com.stumosys.shared.ExtraCurricularActivity;
import com.stumosys.shared.FeesDetail;
import com.stumosys.shared.GradeDetail;
import com.stumosys.shared.Homework;
import com.stumosys.shared.ImagePath;
import com.stumosys.shared.Leaverequest;
import com.stumosys.shared.Library;
import com.stumosys.shared.Noticeboard;
import com.stumosys.shared.Parent;
import com.stumosys.shared.ParentDetail;
import com.stumosys.shared.Person;
import com.stumosys.shared.Reportcard;
import com.stumosys.shared.School;
import com.stumosys.shared.Student;
import com.stumosys.shared.StudentClass;
import com.stumosys.shared.StudentDetail;
import com.stumosys.shared.StudentParent;
import com.stumosys.shared.StudentPerformance;
import com.stumosys.shared.Subject;
import com.stumosys.shared.Teacher;
import com.stumosys.shared.TeacherClass;
import com.stumosys.shared.TeacherDetail;
import com.stumosys.shared.TeacherSubject;
import com.stumosys.shared.TimeTable;
import com.stumosys.util.GetTimeZone;
import com.stumosys.util.MailSendJDBC;

@Repository
@Singleton
public class AdministrationDaoImpl implements AdministrationDao {
 
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");

	Date date = new Date();
	Timestamp timestamp = new Timestamp(date.getTime());

	final Logger logger = Logger.getLogger(AdministrationDaoImpl.class);

	@PersistenceContext(unitName = "sms-pu")
	private EntityManager entitymanager;

		Locale locale = new Locale("en", "US");
	ResourceBundle paths = ResourceBundle.getBundle("email",locale);


	@Override
	@Transactional(value = "transactionManager")
	public String insertNoticeBoard(String personID, NoticeBoardDataBean noticeBoardDataBean) {
		logger.info("-----------Inside insertNoticeBoard method() ----------------");
		List<Person> roleStatus = null;
		StringBuilder status = new StringBuilder("Failure");
		int school_ID = 0;
		int noticeID = 0;
		try {
			logger.info("insertNoticeBoard method 1");
			roleStatus = new ArrayList<Person>();
				if (personID != null && noticeBoardDataBean.getNoticeSubject() != null) {
					logger.info("insertNoticeBoard method 2");
					roleStatus = getRollType(personID);
					logger.debug("Roll Size" + roleStatus.size());
					logger.info("insertNoticeBoard method 3");
					if (roleStatus.size() > 0) {
						logger.info("insertNoticeBoard method 4");
						if (roleStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
							logger.info("insertNoticeBoard method 5");
							school_ID = roleStatus.get(0).getSchool().getSchool_ID();
							if (school_ID > 0) {
								logger.info("insertNoticeBoard method 6");
								noticeBoardDataBean.setSchoolName(roleStatus.get(0).getSchool().getName());
								noticeBoardDataBean.setSchoolID(roleStatus.get(0).getSchool().getSchool_ID());
								noticeID = getNoticeBoardID(personID, noticeBoardDataBean, school_ID);
								logger.info("insertNoticeBoard method 7");
								if (noticeID > 0) {
									logger.info("insertNoticeBoard method 8");
									status = new StringBuilder("exsist");
								} else {
									logger.info("insertNoticeBoard method 9");
									Noticeboard notice = new Noticeboard();
									notice.setSchool(entitymanager.find(School.class, school_ID));	
									logger.info("insertNoticeBoard method 10");
									if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
										logger.info("insertNoticeBoard method 11");
										notice.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
										notice.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
										logger.info("insertNoticeBoard method 12");
									}else{
										logger.info("insertNoticeBoard method 13");
										notice.setCreatedDate(date);
										notice.setCreatedTime(timestamp);
										logger.info("insertNoticeBoard method 14");
									}									
									notice.setSubject(noticeBoardDataBean.getNoticeSubject());
									notice.setStatus("Active");
									notice.setNoticeFollower(noticeBoardDataBean.getNoticeFollower());
									notice.setMessage(noticeBoardDataBean.getNoticeID());
									notice.setFromDate(noticeBoardDataBean.getFromdate());
									notice.setToDate(noticeBoardDataBean.getTodate());
									notice.setNoticeClass(noticeBoardDataBean.getNoticeClass());
									entitymanager.persist(notice);
									logger.info("insertNoticeBoard method 15");
									status = new StringBuilder("Success");
								}
							}
						}
					} else {
						status = new StringBuilder("Invalid");
						logger.info("insertNoticeBoard method 16");

					}
				}
		//	}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		noticeBoardDataBean.setReturnStatus(status.toString());
		logger.info("insertNoticeBoard method 17");
		return status.toString();
	}

	@SuppressWarnings("unchecked")
	private int getNoticeBoardID(String personID, NoticeBoardDataBean noticeBoardDataBean, int school_ID) {
		logger.info("-----------Inside getNoticeBoardID method() ----------------");
		Query q = null;
		int notice_ID = 0;
		try {
			logger.info("getNoticeBoardID method 1");
			logger.debug(school_ID+"noticeBoardDataBean.getNoticeFollowers()"+noticeBoardDataBean.getNoticeFollower()+"noticeBoardDataBean.getNoticeSubject()"+noticeBoardDataBean.getNoticeSubject());
			if (personID != null) {
				logger.info("getNoticeBoardID method 2");
				q = entitymanager.createQuery("from Noticeboard where school_ID=? and subject=? and noticeFollower=? and status='Active'");
				q.setParameter(1, school_ID);
				q.setParameter(2, noticeBoardDataBean.getNoticeSubject());
				q.setParameter(3, noticeBoardDataBean.getNoticeFollower());
				List<Noticeboard> result = (List<Noticeboard>) q.getResultList();
				logger.info("getNoticeBoardID method 3");
				if (result.size() > 0) {
					logger.info("getNoticeBoardID method 4");
					notice_ID = result.get(0).getNoticeBoard_ID();
				}
			}
			logger.debug("getNoticeBoardID notice_ID --------"+notice_ID);
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.info("getNoticeBoardID method 5");
		return notice_ID;
	}

	/**
	 * This method is used to check person roll tyoe
	 * 
	 * @param personID
	 * @return
	 */
	/*@SuppressWarnings("unchecked")
	private List<Person> getRollType(String personID) {
		logger.info("-----------Inside getRollType method() ----------------");
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
	}*/
	//Alex Changed
	@SuppressWarnings("unchecked")
	private List<Person> getRollType(String personID) {
		logger.info("-----------Inside getRollType method() ----------------");
		Query q = null;
		List<Person> rolls = null;
		try {
			//if (personID != null) {
			logger.info("getRollType method 1");
				int pid = Integer.parseInt(personID);
				q = entitymanager.createQuery("from Person where person_ID=? and status='Active'");
				q.setParameter(1, pid);
				rolls = (List<Person>) q.getResultList();
				logger.info("getRollType method 2");
		} catch (Exception e) {
			if(personID==null) {
				logger.info("getRollType method 3");
				//logger.info("[getRollType] personID ID is null");
			}
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.info("getRollType method 4");
		return rolls;
	}

	@Override
	public List<NoticeBoardDataBean> getNoticeBoardView(String personID) {
		logger.info("-----------Inside getNoticeBoardView method() ----------------");
		int school_ID = 0;
		List<Person> roleStatus = null;
		List<Noticeboard> noticeBoardList = null;
		List<NoticeBoardDataBean> noticeViewList = null;
		NoticeBoardDataBean notice=null;
		String rollType = "NotValid";
		String follow = "user";
		try {
			logger.info("getNoticeBoardView method 1");
			noticeBoardList = new ArrayList<Noticeboard>();
			noticeViewList = new ArrayList<NoticeBoardDataBean>();
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				logger.info("getNoticeBoardView method 2");
				roleStatus = getRollType(personID);
				logger.info("getNoticeBoardView method 3");
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					logger.info("getNoticeBoardView method 4");
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					logger.info("getNoticeBoardView method 5");
					if (school_ID > 0) {
						logger.info("getNoticeBoardView method 6");
						follow = "External";
						noticeBoardList = getNoticeByPerson(rollType, follow, school_ID);
						logger.info("getNoticeBoardView method 7");
						if (noticeBoardList.size() > 0) {
							logger.info("getNoticeBoardView method 8");
							for (int i = 0; i < noticeBoardList.size(); i++) {
								notice = new NoticeBoardDataBean();
								notice.setNoticeFollower(noticeBoardList.get(i).getNoticeFollower());
								notice.setNoticeID(noticeBoardList.get(i).getMessage());
								notice.setNoticeSubject(noticeBoardList.get(i).getSubject());
								notice.setNoticeDate(noticeBoardList.get(i).getCreatedDate());
								notice.setFromdate(noticeBoardList.get(i).getFromDate());
								notice.setTodate(noticeBoardList.get(i).getToDate());
								notice.setNoticeClass(noticeBoardList.get(i).getNoticeClass());
								noticeViewList.add(notice);
							}
							logger.info("getNoticeBoardView method 9");

						}
						else {
							logger.info("getNoticeBoardView method 10");
							logger.info("User is not Valid !!!");
						}

					}
				}

			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			//e.printStackTrace();
		}
		logger.info("getNoticeBoardView method 11");
		return noticeViewList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Noticeboard> getNoticeBoardViewBYSubject(String personID, NoticeBoardDataBean noticeBoardDataBean,
			String role_user) {
		logger.info("-----------Inside getNoticeBoardViewBYSubject method() ----------------");
		List<Noticeboard> noticeBoardList = null;
		int school_ID = 0;
		List<Person> roleStatus = null;
		Query q = null;
		try {
			logger.info("getNoticeBoardViewBYSubject method 1");
			noticeBoardList = new ArrayList<Noticeboard>();
			roleStatus = new ArrayList<Person>();
			if (personID != null && role_user.equalsIgnoreCase("Admin")) {
				logger.info("getNoticeBoardViewBYSubject method 2");
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					logger.info("getNoticeBoardViewBYSubject method 3");
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						logger.info("getNoticeBoardViewBYSubject method 4");
						q = entitymanager.createQuery(
								"from Noticeboard where school_ID=? and subject=? and noticeFollower=? and status='Active'");
						q.setParameter(1, school_ID);
						q.setParameter(2, noticeBoardDataBean.getNoticeSubject());
						q.setParameter(3, noticeBoardDataBean.getNoticeFollower());
						noticeBoardList = (List<Noticeboard>) q.getResultList();
						logger.info("getNoticeBoardViewBYSubject method 5");
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.info("getNoticeBoardViewBYSubject method 6");
		return noticeBoardList;
	}

	@Override
	@Transactional(value = "transactionManager")
	public String updateNotice(String personID, NoticeBoardDataBean noticeBoardDataBean) {
		logger.info("-----------Inside updateNotice method() ----------------");
		StringBuilder status = new StringBuilder("Fail");
		List<Person> roleStatus = null;
		String rollType = "NotValid";
		int school_ID = 0;
		int noticeID = 0;
		try {
			logger.info("updateNotice method 1");
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				logger.info("updateNotice method 2");
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					logger.info("updateNotice method 3");
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						logger.info("updateNotice method 4");
						noticeBoardDataBean.setSchoolName(roleStatus.get(0).getSchool().getName());
						if (rollType.equalsIgnoreCase("Admin")) {
							logger.info("updateNotice method 5");
							noticeID=noticeBoardDataBean.getNoticeboardID();
							/*noticeID = getNoticeBoardID(personID, noticeBoardDataBean, school_ID);*/
							if (noticeID > 0) {
								logger.info("updateNotice method 6");
								Noticeboard note = entitymanager.find(Noticeboard.class, noticeID);
								if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
									logger.info("updateNotice method 7");
									note.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
									note.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
								}else{
									logger.info("updateNotice method 8");
									note.setCreatedDate(date);
									note.setCreatedTime(timestamp);
								}	
								logger.info("updateNotice method 9");
								note.setNoticeFollower(noticeBoardDataBean.getNoticeFollower());
								note.setMessage(noticeBoardDataBean.getNoticeID());
								note.setFromDate(noticeBoardDataBean.getFromdate());
								note.setNoticeClass(noticeBoardDataBean.getNoticeClass());
								note.setToDate(noticeBoardDataBean.getTodate());
								entitymanager.merge(note);
								logger.info("updateNotice method 10");
								status = new StringBuilder("Success");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.info("updateNotice method 11");
		return status.toString();
	}

	@Override
	@Transactional(value = "transactionManager")
	public String deleteNotice(String personID, NoticeBoardDataBean noticeBoardDataBean) {
		logger.info("-----------Inside deleteNotice method() ----------------");
		String status = "Fail";
		List<Person> roleStatus = null;
		String rollType = "NotValid";
		int school_ID = 0;
		int noticeID = 0;
		try {
			logger.info("deleteNotice method 1");
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				logger.info("deleteNotice method 2");
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					logger.info("deleteNotice method 3");
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						logger.info("deleteNotice method 4");
						if (rollType.equalsIgnoreCase("Admin")) {
							logger.info("deleteNotice method 5");
							noticeID = getNoticeBoardID(personID, noticeBoardDataBean, school_ID);
							logger.debug("Notice Board ID "+noticeID);
							if (noticeID > 0) {
								logger.info("deleteNotice method 6");
								Noticeboard note = entitymanager.find(Noticeboard.class, noticeID);
								logger.info("deleteNotice method 7");
								if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
									logger.info("deleteNotice method 8");
									note.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
									note.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
								}else{
									logger.info("deleteNotice method 9");
									note.setCreatedDate(date);
									note.setCreatedTime(timestamp);
								}	
								note.setStatus("De-Active");
								entitymanager.merge(note);
								logger.info("deleteNotice method 10");
								status = "Success";
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.info("deleteNotice method 11");
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getClassNameList(String personID) {
		logger.info("-----------Inside getClassNameList method() ----------------");
		Query q = null;
		//Query q1 = null;
		int teacher_ID = 0;
		List<Person> roleStatus = null;
		List<String> resultSize = null;
		int school_ID = 0;
		String rollType = "NotValid";
		List<TeacherClass> teacherClass = null;
		int studentID = 0;
		int parentID = 0;
		List<Parent> parentList = null;
		List<StudentClass> studentClass = null;
		List<StudentParent> studentParent = null;
		try {
			logger.info("getClassNameList method 1");
			roleStatus = new ArrayList<Person>();
			teacherClass = new ArrayList<TeacherClass>();
			studentClass = new ArrayList<StudentClass>();
			parentList = new ArrayList<Parent>();
			studentParent = new ArrayList<StudentParent>();
			if (personID != null) {
				logger.info("getClassNameList method 2");
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					logger.info("getClassNameList method 3");
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					logger.debug("---Type---" + rollType);
					if (school_ID > 0) {
						logger.info("getClassNameList method 4");
						if (rollType.equalsIgnoreCase("Admin") || rollType.equalsIgnoreCase("Librarian")) {
							logger.info("getClassNameList method 5");
							q = entitymanager.createQuery("from Class where school_ID=? and status='Active'");
							q.setParameter(1, school_ID);
							List<Class> result = (List<Class>) q.getResultList();
							resultSize = new ArrayList<String>();
							logger.info("getClassNameList method 6");
							if (result.size() > 0) {
								logger.info("getClassNameList method 7");
								for (int i = 0; i < result.size(); i++) {
									String s = result.get(i).getClassName() + "/" + result.get(i).getClassSection();
									resultSize.add(s);
								}
							}
							logger.info("getClassNameList method 8");
						} else if (rollType.equalsIgnoreCase("Teacher")) {
							logger.info("getClassNameList method 9");
							resultSize = new ArrayList<String>();
							teacher_ID = getTeacherID(roleStatus.get(0).getPersonRoleNumber());
							teacherClass = getTeaClass(teacher_ID, personID);
							logger.info("getClassNameList method 10");
							if (teacherClass.size() > 0) {
								logger.info("getClassNameList method 11");
								for (int i = 0; i < teacherClass.size(); i++) {
									String name = teacherClass.get(i).getClazz().getClassName() + "/"
											+ teacherClass.get(i).getClazz().getClassSection();
									logger.debug(name);
									resultSize.add(name);
									 }
								logger.info("getClassNameList method 12");
							}
						} else if (rollType.equalsIgnoreCase("Student")) {
							logger.info("getClassNameList method 13");
							resultSize = new ArrayList<String>();
							studentID = getStudentID(roleStatus.get(0).getPersonRoleNumber());
							logger.info("getClassNameList method 14");
							if (studentID > 0) {
								logger.info("getClassNameList method 15");
								studentClass = getStudentClassList(studentID, school_ID, personID);
								if (studentClass.size() > 0) {
									logger.info("getClassNameList method 16");
									String name = studentClass.get(0).getClazz().getClassName() + "/"
											+ studentClass.get(0).getClazz().getClassSection();
									logger.debug(name);
									resultSize.add(name);
									logger.info("getClassNameList method 17");
								}
							}
						} else if (rollType.equalsIgnoreCase("Parent")) {
							logger.info("getClassNameList method 18");
							resultSize = new ArrayList<String>();
							parentList = getParentList(personID, roleStatus.get(0).getPersonRoleNumber());
							if (parentList.size() > 0) {
								logger.info("getClassNameList method 19");
								parentID = parentList.get(0).getParent_ID();
								studentParent = getParStudentID(parentID);
								if (studentParent.size() > 0) {
									logger.info("getClassNameList method 20");
									for (int i = 0; i < studentParent.size(); i++) {
										logger.info("getClassNameList method 21");
										studentID = studentParent.get(i).getStudent().getStudent_ID();
										if (studentID > 0) {
											logger.info("getClassNameList method 22");
											studentClass = getStudentClassList(studentID, school_ID, personID);
											if (studentClass.size() > 0) {
												logger.info("getClassNameList method 23");
												String name = studentClass.get(0).getClazz().getClassName() + "/"
														+ studentClass.get(0).getClazz().getClassSection();
												logger.debug(name);
												resultSize.add(name);
												logger.info("getClassNameList method 24");
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
		logger.info("getClassNameList method 25");
		return resultSize;
	}
	@SuppressWarnings("unchecked")
	private List<StudentParent> getParStudentID(int parentID) {
		logger.info("-----------Inside getParStudentID method() ----------------");
		List<StudentParent> resList = null;
		Query q = null;
		try {
			logger.info("getParStudentID method 1");
			resList = new ArrayList<StudentParent>();
			q = entitymanager.createQuery("from StudentParent where parent_ID=? and status='Active'");
			q.setParameter(1, parentID);
			resList = (List<StudentParent>) q.getResultList();
			logger.info("getParStudentID method 2");
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.info("getParStudentID method 3");
		return resList;
	}

	@SuppressWarnings("unchecked")
	private List<Parent> getParentList(String personID, String personRoleNumber) {
		logger.info("-----------Inside getParentList method() ----------------");
		List<Parent> res = null;
		Query q = null;
		try {
			logger.info("getParentList method 1");
			res = new ArrayList<Parent>();
			if (personID != null) {
				logger.info("getParentList method 2");
				q = entitymanager.createQuery("from Parent where rollNumber=? and status='Active'");
				q.setParameter(1, personRoleNumber);
				res = (List<Parent>) q.getResultList();
				logger.info("getParentList method 3");
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.info("getParentList method 4");
		return res;
	}

	@SuppressWarnings("unchecked")
	private List<StudentClass> getStudentClassList(int studentID, int school_ID, String personID) {
		logger.info("-----------Inside getStudentClassList method() ----------------");
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
	private List<TeacherClass> getTeaClass(int teacher_ID, String personID) {
		logger.info("-----------Inside getTeaClass method() ----------------");
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

	/**
	 * @author Robert Arjun
	 * 
	 */
	@SuppressWarnings("unchecked")
	private int getTeacherID(String teaID) {
		logger.info("-----------Inside getTeacherID method() ----------------");
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
	public List<String> getSubjectNameList(ReportCardDataBean reportCardDataBean, String personID) {
		logger.info("-----------Inside getSubjectNameList method() ----------------");
		Query q = null;
		Query q1 = null;
		int class_ID = 0;
		List<Person> roleStatus = null;
		List<String> resultSize = null;
		int school_ID = 0;
		String rollType = "NotValid";
		List<String> subList = null;
		LinkedHashSet<String> tempList = null;
		String personRollNo = "Not";
		int teaId = 0;
		List<String> matchSubjectList = null;
		try {
			roleStatus = new ArrayList<Person>();
			subList = new ArrayList<String>();
			tempList = new LinkedHashSet<String>();
			resultSize = new ArrayList<String>();
			matchSubjectList = new ArrayList<String>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					personRollNo = roleStatus.get(0).getPersonRoleNumber();
					logger.debug("---Type---" + rollType);
					if (school_ID > 0) {
						if (rollType.equalsIgnoreCase("Admin")) {
							StringTokenizer st = new StringTokenizer(reportCardDataBean.getStudMarkClass());
							String className = st.nextToken("/");
							String sectionName = reportCardDataBean.getStudMarkClass();
							String section = sectionName.substring(sectionName.lastIndexOf("/") + 1);
							q = entitymanager.createQuery("from Class where className=? and classSection=? and school_ID=? and status='Active'");
							q.setParameter(1, className);
							q.setParameter(2, section);
							q.setParameter(3, school_ID);
							List<Class> result = (List<Class>) q.getResultList();
							logger.debug("---" + result.size());
							if (result.size() > 0) {
								class_ID = result.get(0).getClass_ID();
								q1 = entitymanager
										.createQuery("from ClassSubject where class_ID=? and status='Active'");
								q1.setParameter(1, class_ID);
								List<ClassSubject> res = (List<ClassSubject>) q1.getResultList();
								if (res.size() > 0) {
									for (int i = 0; i < res.size(); i++) {
										subList.add(res.get(i).getSubject().getSujectName() + "/"
												+ res.get(i).getSubject().getSubjectCode());
									}
									logger.debug("---" + subList.size());
									tempList.addAll(subList);
									logger.debug("---" + tempList.size());

									subList.clear();
									subList.addAll(tempList);
									logger.debug("---" + subList.size());
								}

							}

						} else if (rollType.equalsIgnoreCase("Teacher")) {
							StringTokenizer st = new StringTokenizer(reportCardDataBean.getStudMarkClass());
							String className = st.nextToken("/");
							String sectionName = reportCardDataBean.getStudMarkClass();
							String section = sectionName.substring(sectionName.lastIndexOf("/") + 1);
							q = entitymanager.createQuery("from Class where className=? and classSection=? and school_ID=? and status='Active'");
							q.setParameter(1, className);
							q.setParameter(2, section);
							q.setParameter(3, school_ID);
							List<Class> result = (List<Class>) q.getResultList();
							logger.debug("---" + result.size());
							if (result.size() > 0) {
								class_ID = result.get(0).getClass_ID();
								teaId = getTeacherID(personRollNo);
								if (teaId > 0) {
									
									q1 = entitymanager.createQuery(
											"from TeacherSubject where school_ID=? and teacher_ID=? and status='Active'");
									q1.setParameter(1, school_ID);
									q1.setParameter(2, teaId);
									List<TeacherSubject> res = (List<TeacherSubject>) q1.getResultList();
									if (res.size() > 0) {
										for (int i = 0; i < res.size(); i++) {
											subList.add(res.get(i).getSubject().getSujectName() + "/"
													+ res.get(i).getSubject().getSubjectCode());
										}
										logger.debug("---" + subList.size());
										tempList.addAll(subList);
										logger.debug("---" + tempList.size());
										subList.clear();
										subList.addAll(tempList);
										matchSubjectList = getSubjectList(class_ID, school_ID, personID);
										if (matchSubjectList.size() > 0) {
											subList.retainAll(matchSubjectList);
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
		return subList;
	}

	@SuppressWarnings("unchecked")
	private List<String> getSubjectList(int class_ID, int school_ID, String personID) {
		logger.info("-----------Inside getSubjectList method() ----------------");

		Query q1 = null;
		List<String> subList = null;
		try {
			subList = new ArrayList<String>();
			q1 = entitymanager.createQuery("from ClassSubject where class_ID=? and status='Active'");
			q1.setParameter(1, class_ID);
			List<ClassSubject> res = (List<ClassSubject>) q1.getResultList();
			if (res.size() > 0) {
				for (int i = 0; i < res.size(); i++) {
					subList.add(
							res.get(i).getSubject().getSujectName() + "/" + res.get(i).getSubject().getSubjectCode());
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return subList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ReportCardDataBean> getStudent(String personID, ReportCardDataBean reportCardDataBean) {
		logger.info("-----------Inside getStudent method()----------------");

	//	String status = "Fail";
		List<Person> roleStatus = null;
		List<Reportcard> markCard = null;
		int school_ID = 0;
		String rollType = "NotValid";
		int class_ID = 0;
		List<Class> classList = null;
		List<StudentClass> studentClasList = null;
		List<ReportCardDataBean> resultList = null;
		int studentID = 0;
		Query q1 = null;
		int tea_ID = 0;
		int sub_ID = 0;
		try {
			roleStatus = new ArrayList<Person>();
			classList = new ArrayList<Class>();
			studentClasList = new ArrayList<StudentClass>();
			resultList = new ArrayList<ReportCardDataBean>();
			markCard = new ArrayList<Reportcard>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					logger.debug("---Type---" + rollType);
					if (school_ID > 0) {
						classList = getClassID(personID, school_ID, reportCardDataBean.getStudMarkClass());
						if (classList.size() > 0) {
							class_ID = classList.get(0).getClass_ID();
							studentClasList = getStudentClass(class_ID, personID);
							if (studentClasList.size() > 0) {
								for (int i = 0; i < studentClasList.size(); i++) {
									studentID = studentClasList.get(i).getStudent().getStudent_ID();
									q1 = entitymanager.createQuery("from StudentDetail where student_ID=? ");
									q1.setParameter(1, studentID);
									List<StudentDetail> res = (List<StudentDetail>) q1.getResultList();
									logger.debug("Size -----" + res.size());
									if (res.size() > 0) {
										tea_ID = getTeacherID(roleStatus.get(0).getPersonRoleNumber());
										sub_ID = getSubjectID(reportCardDataBean.getMarkSubTitle(), personID,
												school_ID);
										markCard = getmarkList(sub_ID, tea_ID, studentID, class_ID, school_ID,
												reportCardDataBean.getExamMarkTitle(), reportCardDataBean);
										ReportCardDataBean CardDataBean = new ReportCardDataBean();
										logger.debug("Report card Size" + markCard.size());
										if (markCard.size() > 0) {
											CardDataBean.setRollNo(res.get(0).getStudentid().getRollNumber());
											CardDataBean.setName(
													res.get(0).getFirstName() + " " + res.get(0).getLastName());
											CardDataBean.setsNo(String.valueOf((i + 1)));
											CardDataBean.setGrade(markCard.get(0).getGrade());
											CardDataBean.setMark(markCard.get(0).getMark());
											CardDataBean.setResultStatus(markCard.get(0).getResultStatus());
										} else {
											CardDataBean.setRollNo(res.get(0).getStudentid().getRollNumber());
											CardDataBean.setName(
													res.get(0).getFirstName() + " " + res.get(0).getLastName());
											CardDataBean.setsNo(String.valueOf((i + 1)));
											CardDataBean.setGrade("");
								           CardDataBean.setMark("");
								           CardDataBean.setResultStatus("");
										}
										resultList.add(CardDataBean);
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
		return resultList;
	}

	@SuppressWarnings("unchecked")
	private List<StudentClass> getStudentClass(int class_ID, String personID) {
		logger.info("-----------Inside getStudentClass method()----------------");
		List<StudentClass> studentClasList = null;
		Query q = null;
		try {
			studentClasList = new ArrayList<StudentClass>();
			if (personID != null && class_ID > 0) {
				q = entitymanager.createQuery("from StudentClass where class_ID=? and status='Active'");
				q.setParameter(1, class_ID);
				List<StudentClass> res = (List<StudentClass>) q.getResultList();
				if (res.size() > 0) {
					studentClasList.addAll(res);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return studentClasList;
	}

	@SuppressWarnings("unchecked")
	private List<Class> getClassID(String personID, int school_ID, String studMarkClass) {
		logger.info("-----------Inside getClassID method()----------------");
		List<Class> classList = null;
		Query q = null;
		try {
			classList = new ArrayList<Class>();
			if (personID != null && school_ID > 0) {
				StringTokenizer st = new StringTokenizer(studMarkClass);
				String className = st.nextToken("/");
				String sectionName = studMarkClass;
				String section = sectionName.substring(sectionName.lastIndexOf("/") + 1);
				q = entitymanager.createQuery("from Class where className=? and classSection=? and school_ID=? and status='Active'");
				q.setParameter(1, className);
				q.setParameter(2, section);
				q.setParameter(3, school_ID);
				List<Class> result = (List<Class>) q.getResultList();
				if (result.size() > 0) {
					classList.addAll(result);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return classList;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String addReportCard(String personID, ReportCardDataBean reportCardDataBean) {
		logger.info("-----------Inside addReportCard method()----------------");
		String status = "Fail";
		List<Person> roleStatus = null;
		int school_ID = 0;
		String rollType = "NotValid";
		int sub_ID = 0;
		int tea_ID = 0;
		int class_ID = 0;
		int student_ID = 0;
		List<Class> classList = null;
		List<Reportcard> markCard = null;
		int reportcardID = 0;
		try {
			logger.debug("---------Inside addReportCard Dao method calling---------");
			roleStatus = new ArrayList<Person>();
			classList = new ArrayList<Class>();
			markCard = new ArrayList<Reportcard>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					reportCardDataBean.setSchoolName(roleStatus.get(0).getSchool().getName());
					reportCardDataBean.setSchoolID(school_ID);
					logger.debug("---Type---" + rollType);
					if (school_ID > 0) {
						if (rollType.equalsIgnoreCase("Teacher")) {
							classList = getClassID(personID, school_ID, reportCardDataBean.getStudMarkClass());
							tea_ID = getTeacherID(roleStatus.get(0).getPersonRoleNumber());
							student_ID = getStudentID(reportCardDataBean.getRollNo());
							sub_ID = getSubjectID(reportCardDataBean.getMarkSubTitle(), personID, school_ID);
							if (classList.size() > 0 && tea_ID > 0 && student_ID > 0 && sub_ID > 0) {
								class_ID = classList.get(0).getClass_ID();
								markCard = getmarkList(sub_ID, tea_ID, student_ID, class_ID, school_ID,
										reportCardDataBean.getExamMarkTitle(), reportCardDataBean);
								if (markCard.size() > 0) {
									reportcardID = markCard.get(0).getReportcard_ID();
									Reportcard report = entitymanager.find(Reportcard.class, reportcardID);
									if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
										report.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
										report.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
									}else{
										report.setCreatedDate(date);
										report.setCreatedTime(timestamp);
									}	
									report.setGrade(reportCardDataBean.getGrade());
									report.setMark(reportCardDataBean.getMark());
									report.setResultStatus(reportCardDataBean.getResultStatus());
									entitymanager.merge(report);
									status = "Edit";
								} else {
									Reportcard reportcard = new Reportcard();
									reportcard.setClazz(entitymanager.find(Class.class, class_ID));
									if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
										reportcard.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
										reportcard.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
									}else{
										reportcard.setCreatedDate(date);
										reportcard.setCreatedTime(timestamp);
									}	
									reportcard.setExamTitle(reportCardDataBean.getExamMarkTitle());
									reportcard.setGrade(reportCardDataBean.getGrade());
									reportcard.setMark(reportCardDataBean.getMark());
									reportcard.setStudent(entitymanager.find(Student.class, student_ID));
									reportcard.setResultStatus(reportCardDataBean.getResultStatus());
									reportcard.setSchool(entitymanager.find(School.class, school_ID));
									reportcard.setStatus("Active");
									reportcard.setSubject(entitymanager.find(Subject.class, sub_ID));
									reportcard.setTeacher(entitymanager.find(Teacher.class, tea_ID));
									entitymanager.persist(reportcard);
									status = "Success";
								}
								Query q=null;
						        q=entitymanager.createQuery("from StudentParent where student_ID=? and status='Active'");
						        q.setParameter(1, student_ID);
						        List<StudentParent> studpar=(List<StudentParent>)q.getResultList();
						        if(studpar.size()>0){						         
						          reportCardDataBean.setMailid(studpar.get(0).getParent().getParentDetail().getEmaiId());
						          logger.debug("mail -- "+reportCardDataBean.getMailid());
						          reportCardDataBean.setPhoneno(studpar.get(0).getParent().getParentDetail().getPhoneNumber());
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

	@SuppressWarnings("unchecked")
	private int getStudentID(String stuRollNo) {
		logger.info("-----------Inside getStudentID method()----------------");
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
	private int getSubjectID(String subject, String personID, int school_ID) {
		logger.info("-----------Inside getSubjectID method()----------------");
		Query q = null;
		int subID = 0;
		try {
			if (personID != null && school_ID > 0 && subject != null) {
				StringTokenizer st = new StringTokenizer(subject);
				String subjectName = st.nextToken("/");
				String subjectCode = subject;
				String code = subjectCode.substring(subjectCode.lastIndexOf("/") + 1);
				q = entitymanager.createQuery(
						"from Subject where sujectName=? and subjectCode=? and school_ID=? and status='Active'");
				q.setParameter(1, subjectName);
				q.setParameter(2, code);
				q.setParameter(3, school_ID);
				List<Subject> result = (List<Subject>) q.getResultList();
				if (result.size() > 0) {
					subID = result.get(0).getSubject_ID();
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return subID;

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getStudentIDList(ReportCardDataBean reportCardDataBean, String personID) {
		logger.info("-----------Inside getStudentIDList method()----------------");
		List<Person> roleStatus = null;
		List<String> IDList = null;
		Query q = null;
		int school_ID = 0;
		String roleType = "NotValid";
		int studentID = 0;
		int parentID = 0;
		List<Parent> parentList = null;
		List<StudentClass> studentClass = null;
		List<StudentParent> studentParent = null;
		try {
			IDList = new ArrayList<String>();
			roleStatus = new ArrayList<Person>();
			parentList = new ArrayList<Parent>();
			studentClass = new ArrayList<StudentClass>();
			studentParent = new ArrayList<StudentParent>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					roleType = roleStatus.get(0).getPersonRole();
					if (!roleType.equalsIgnoreCase("NotValid")) {
						if (roleType.equalsIgnoreCase("Student")) {
							q=null;
							q=entitymanager.createQuery("from Student where rollNumber=? and status='Active'");
							q.setParameter(1, roleStatus.get(0).getPersonRoleNumber());
							List<Student> studentlists =(List<Student>)q.getResultList();
							if(studentlists.size()>0){
								q=null;
								q=entitymanager.createQuery("from StudentDetail where student_ID=?");
								q.setParameter(1, studentlists.get(0).getStudent_ID());
								List<StudentDetail> studentdetail=(List<StudentDetail>)q.getResultList();
								if(studentdetail.size()>0){
									IDList.add(studentdetail.get(0).getFirstName()+studentdetail.get(0).getLastName()+"/"+roleStatus.get(0).getPersonRoleNumber());
								}
							}
						} else if (roleType.equalsIgnoreCase("Parent")) {
							int classid=0;
							StringTokenizer stringtoken = new StringTokenizer(reportCardDataBean.getStudMarkClass());
							String classname = stringtoken.nextToken("/");
							String section = reportCardDataBean.getStudMarkClass();
							String sectionName = section.substring(section.lastIndexOf("/") + 1);
							Query q1=null;
							q1=entitymanager.createQuery("from Class where className=? and classSection=? and school_ID=? and status='Active'");
							q1.setParameter(1, classname);
							q1.setParameter(2, sectionName);
							q1.setParameter(3, school_ID);
							List<Class> classlist=(List<Class>)q1.getResultList();
							if(classlist.size() > 0){
								classid=classlist.get(0).getClass_ID();
							}
							parentList = getParentList(personID, roleStatus.get(0).getPersonRoleNumber());
							if (parentList.size() > 0) {
								parentID = parentList.get(0).getParent_ID();
								studentParent = getParStudentID(parentID);
								if (studentParent.size() > 0) {
									for (int i = 0; i < studentParent.size(); i++) {
										q1=null;
										q1=entitymanager.createQuery("from StudentClass where class_ID=? and student_ID=?");
										q1.setParameter(1, classid);
										q1.setParameter(2, studentParent.get(i).getStudent().getStudent_ID());
										List<StudentClass> studclasslist=(List<StudentClass>)q1.getResultList();
										if(studclasslist.size() > 0)
										{
											for (int j = 0; j < studclasslist.size(); j++) {
												studentID = studclasslist.get(j).getStudent().getStudent_ID();
												q=null;
												q=entitymanager.createQuery("from StudentDetail where student_ID = ?");
												q.setParameter(1, studentID);
												List<StudentDetail> studentdetailList=(List<StudentDetail>)q.getResultList();
												if(studentdetailList.size()>0){
													IDList.add(studentdetailList.get(0).getFirstName()+studentdetailList.get(0).getLastName()+"/"+studclasslist.get(j).getStudent().getRollNumber());
												}
											}
										}
									}
								}
							}
						} else {
							if (school_ID > 0) {
								IDList = getRollNumber(personID, school_ID, reportCardDataBean.getStudMarkClass());
							}
						}
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
		Query q1 = null;int studentID=0;
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
							studentID = idNumber.getStudent().getStudent_ID();
							q=null;
							q=entitymanager.createQuery("from StudentDetail where student_ID = ?");
							q.setParameter(1, studentID);
							List<StudentDetail> studentdetailList=(List<StudentDetail>)q.getResultList();
							if(studentdetailList.size()>0){
								IDLis.add(studentdetailList.get(0).getFirstName()+studentdetailList.get(0).getLastName()+"/"+idNumber.getStudent().getRollNumber());
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
	public List<String> getStudentIDList1(String name, String personID) {
		logger.info("-----------Inside getStudentIDList1 method()----------------");
		List<Person> roleStatus = null;
		List<String> IDList = null;
		Query q = null;
		int school_ID = 0;
		String roleType = "NotValid";
		int parentID = 0;
		List<Parent> parentList = null;
		List<StudentClass> studentClass = null;
		List<StudentParent> studentParent = null;
		try {
			IDList = new ArrayList<String>();
			roleStatus = new ArrayList<Person>();
			parentList = new ArrayList<Parent>();
			studentClass = new ArrayList<StudentClass>();
			studentParent = new ArrayList<StudentParent>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					roleType = roleStatus.get(0).getPersonRole();
					if (!roleType.equalsIgnoreCase("NotValid")) {
						if (roleType.equalsIgnoreCase("Student")) {
							IDList.add(roleStatus.get(0).getPersonRoleNumber());
						} else if (roleType.equalsIgnoreCase("Parent")) {

							parentList = getParentList(personID, roleStatus.get(0).getPersonRoleNumber());
							if (parentList.size() > 0) {
								parentID = parentList.get(0).getParent_ID();
								studentParent = getParStudentID(parentID);
								if (studentParent.size() > 0) {
									for (int i = 0; i < studentParent.size(); i++) {
								          IDList.add(studentParent.get(i).getStudent().getRollNumber());
									}
								}
							}
						} else {
							school_ID = roleStatus.get(0).getSchool().getSchool_ID();
							if (school_ID > 0) {
								IDList = getRollNumber(personID, school_ID, name);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return IDList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getIdList(StudentPerformanceDataBean studentPerformanceDataBean) {
		logger.info("-----------Inside getIdList method()----------------");
		List<String> stt = null;
		int perid = 0;
		logger.debug("-------inside getIdList------");
		ArrayList<StudentPerformanceDataBean> rollnolist1 = new ArrayList<StudentPerformanceDataBean>();
		Query q = null;
		try {
			q = entitymanager.createQuery("from Person where person_role='Student' and status='Active'");
			List<Person> idlist1 = (List<Person>) q.getResultList();
			if (idlist1.size() > 0) {
				for (int i = 0; i < idlist1.size(); i++) {
					perid = idlist1.get(i).getPerson_ID();
					q = null;
					q = entitymanager.createQuery("from Student where person_ID=? and school_ID=? and status='Active'");
					q.setParameter(1, perid);
					q.setParameter(2, idlist1.get(0).getSchool().getSchool_ID());
					List<Student> studlist = (List<Student>) q.getResultList();
					if (studlist.size() > 0) {
						for (int s = 0; s < studlist.size(); s++) {
							StudentPerformanceDataBean idobj = new StudentPerformanceDataBean();
							idobj.setPerformStudID(studlist.get(s).getRollNumber());
							rollnolist1.add(idobj);
							studentPerformanceDataBean.setRollnolist(rollnolist1);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.debug("-------outside getIdList-----");
		return stt;
	}

	/**
	 * @author Jeevini This method is used to get the student roll number based
	 *         on class and section
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String getPerClass(StudentPerformanceDataBean studentPerformanceDataBean,String personID) {
		logger.info("Inside getPerClass method() double");
		Query q = null;
		int studid = 0;
		int clsid = 0;
		ArrayList<StudentPerformanceDataBean> rollnolist1 = null;
		List<Person> personlist=null;
		try {
			personlist=new ArrayList<Person>();
			personlist=getRollType(personID);
			if(personlist.size()>0){
				int schoolid=personlist.get(0).getSchool().getSchool_ID();
			rollnolist1=new ArrayList<StudentPerformanceDataBean>();
			StringTokenizer st = new StringTokenizer(studentPerformanceDataBean.getPerClassSection());
			String className = st.nextToken("/");
			String section = studentPerformanceDataBean.getPerClassSection();
			String sectionName = section.substring(section.lastIndexOf("/") + 1);
			
			q = entitymanager.createQuery("from Class where class_name=? and class_section=? and school_ID=? and status='Active'");
			q.setParameter(1, className);
			q.setParameter(2, sectionName);
			q.setParameter(3, schoolid);
			List<Class> clslist = (List<Class>) q.getResultList();
			if (clslist.size() > 0) {
				clsid = clslist.get(0).getClass_ID();

				q = null;
				q = entitymanager.createQuery("from StudentClass where class_ID=? and status='Active'");
				q.setParameter(1, clsid);
				List<StudentClass> clslist1 = (List<StudentClass>) q.getResultList();
				if (clslist1.size() > 0) {
					for (int i = 0; i < clslist1.size(); i++) {
						studid = clslist1.get(i).getStudent().getStudent_ID();
						q = null;
						q = entitymanager.createQuery("from StudentDetail where student_ID=?");
						q.setParameter(1, studid);
						List<StudentDetail> studentlist = (List<StudentDetail>) q.getResultList();
						logger.debug("--------list size-----" + studentlist.size());
						if (studentlist.size() > 0) {
								StudentPerformanceDataBean idobj = new StudentPerformanceDataBean();
								idobj.setPerformStudID(studentlist.get(0).getFirstName()+studentlist.get(0).getLastName()+"/"+clslist1.get(i).getStudent().getRollNumber());
								rollnolist1.add(idobj);
						}
					}
				}
			}
			studentPerformanceDataBean.setRollnolist(rollnolist1);
			}
		} catch (Exception e) {
			//logger.debug("-------exception  getPerClass-----");
			logger.warn("Exception -->"+e.getMessage());
		}
		//logger.debug("-------outside getPerClass-----");
		return "";
	}

	/*Ragulan Oct 25 getClassList1*/

	@Override
	@SuppressWarnings("unchecked")
	 public List<String> getClassList1(String personID, StudentPerformanceDataBean studentPerformanceDataBean) {
		logger.info("-----------Inside getClassList1 method()----------------");
	  List<Person> roleStatus = null;
	  int school_ID = 0;
	  List<String> stt = null;
	  int teaid=0;
	  ArrayList<StudentPerformanceDataBean> clslist2 = new ArrayList<StudentPerformanceDataBean>();
	  Query q = null;
	  try {
	   roleStatus = new ArrayList<Person>();
	   if (personID != null) {
	    roleStatus = getRollType(personID);
	    logger.debug("Roll Size" + roleStatus.size());
	    if (roleStatus.size() > 0) {
	     school_ID = roleStatus.get(0).getSchool().getSchool_ID();
	     if (school_ID > 0) {
	      if(roleStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")){
	      q = entitymanager.createQuery("from Class where school_ID=? and status='Active'");
	      q.setParameter(1, school_ID);
	      List<Class> clslist1 = (List<Class>) q.getResultList();
	      if (clslist1.size() > 0) {
	       for (int i = 0; i < clslist1.size(); i++) {
	        StudentPerformanceDataBean pickobj = new StudentPerformanceDataBean();
	        pickobj.setStuCls(
	          clslist1.get(i).getClassName() + "/" + clslist1.get(i).getClassSection());
	        clslist2.add(pickobj);
	        studentPerformanceDataBean.setStudentclassList(clslist2);
	       }
	      }
	      }else if(roleStatus.get(0).getPersonRole().equalsIgnoreCase("Teacher")){
	      q = entitymanager.createQuery("from Teacher where roll_number=?  and school_ID=? and status='Active'");
	      q.setParameter(1, roleStatus.get(0).getPersonRoleNumber());
	      q.setParameter(2, school_ID);
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
	         StudentPerformanceDataBean pickobj = new StudentPerformanceDataBean();
	         pickobj.setStuCls(
	           teaclslist.get(i).getClazz().getClassName() + "/" + teaclslist.get(i).getClazz().getClassSection());
	         clslist2.add(pickobj);
	         studentPerformanceDataBean.setStudentclassList(clslist2);
	        
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

	  return stt;

	 }

	/**
	 * @author Jeevini This method is used to view student performance
	 */
	@SuppressWarnings("unchecked")
	public List<StudentPerformanceDataBean> getStudentPerInfo(String personID,
			StudentPerformanceDataBean studentPerformanceDataBean) {
		logger.info("-----------Inside getStudentPerInfo method()----------------");
		Query q = null;
		List<StudentPerformanceDataBean> studlist2 = new ArrayList<StudentPerformanceDataBean>();
		int clsid = 0;
		int studid;
		int studid1 = 0;
		int school_ID = 0;
		List<Person> roleStatus = null;
		List<String> studentrollno=new ArrayList<String>();
		String rollType = "NotValid";
		String follow = "user";
		try {
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("-----------Role Size----" + roleStatus.size()+"-----"+roleStatus.get(0).getSchool().getSchool_ID());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					logger.debug(rollType);
					logger.debug(rollType);
					if (school_ID > 0) {
						logger.debug(rollType);
						if (rollType.equalsIgnoreCase("Admin")) {
							if (studentPerformanceDataBean.getPerClassSection().equalsIgnoreCase("All")) {
								follow = "All User";
								q = entitymanager.createQuery("from Class where school_ID=? and status=?");
								q.setParameter(1, school_ID);
								q.setParameter(2, "Active");
							}
							else{
								StringTokenizer st = new StringTokenizer(studentPerformanceDataBean.getPerClassSection());
								String className = st.nextToken("/");
								String section = studentPerformanceDataBean.getPerClassSection();
								String sectionName = section.substring(section.lastIndexOf("/") + 1);
								follow = "All User";
								q = entitymanager.createQuery("from Class where class_name=? and class_section=? and school_ID=? and status='Active'");
								q.setParameter(1, className);
								q.setParameter(2, sectionName);
								q.setParameter(3, school_ID);
							}
							List<Class> clslist = (List<Class>) q.getResultList();
							if (clslist.size() > 0) {
								for (int i = 0; i < clslist.size(); i++) {
									clsid = clslist.get(i).getClass_ID();
									q = null;
									q = entitymanager.createQuery("from StudentClass where class_ID=? and status='Active'");
									q.setParameter(1, clsid);
									List<StudentClass> clslist1 = (List<StudentClass>) q.getResultList();
									if (clslist1.size() > 0) {
										for (int j = 0; j < clslist1.size(); j++) {
											studid = clslist1.get(j).getStudent().getStudent_ID();
											q = null;
											q = entitymanager.createQuery("from Student where student_ID=?  and status='Active'");
											q.setParameter(1, studid);
											List<Student> studentlist = (List<Student>) q.getResultList();
											q = null;
											q = entitymanager.createQuery("from StudentDetail where student_ID=?");
											q.setParameter(1, studid);
											List<StudentDetail> studlist = (List<StudentDetail>) q.getResultList();
											logger.info("---studentID for student detail table---"+studid); 
											logger.info("------studlist----"+studlist.size());
											/*q = null;
											q = entitymanager.createQuery("from StudentParent where student_ID=?");
											q.setParameter(1, studid);
											List<StudentParent> studentparent = (List<StudentParent>) q.getResultList();
											logger.info("---studentID for student parent table---"+studid); 
											logger.info("parent id ------>"+studentparent.get(0).getParent().getParent_ID()+"-----size----"+studentparent.size());
											if (studentparent.size()>0) {
												q = null;
												q = entitymanager.createQuery("from ParentDetail where parent_ID=?");
												q.setParameter(1, studentparent.get(0).getParent());
											}
											List<ParentDetail> parentDetail = (List<ParentDetail>) q.getResultList();
											logger.info("----parentdetails------"+parentDetail.size());*/
											q = null;
											q = entitymanager.createQuery("from StudentPerformance  where student_ID=? and createDate=? and status='Active'");
											q.setParameter(1, studid);
											q.setParameter(2,studentPerformanceDataBean.getTtdate() );
											List<StudentPerformance> perlist2 = (List<StudentPerformance>) q.getResultList();
											System.out.println("studentlist.studlist.perlist2---->"+studentlist.size()+"---->"+studlist.size()+"--->"+perlist2.size());
											if (studentlist.size() > 0 && studlist.size() > 0 && perlist2.size() > 0 ) {
												for (int s = 0; s < studlist.size(); s++) {
													StudentPerformanceDataBean obj = new StudentPerformanceDataBean();
													obj.setTtdate(perlist2.get(s).getCreateDate()); 
													obj.setPerformStudID(studentlist.get(s).getRollNumber());
													obj.setStuName(studlist.get(s).getFirstName());
													obj.setParName(studlist.get(s).getFatherName() +","+ studlist.get(s).getMotherName());
													obj.setStuApp(perlist2.get(s).getAppearanceStatus());
													obj.setStuAtt(perlist2.get(s).getAttitudeStatus());
													
													//obj.setParentsMail(parentDetail.get(s).getEmaiId());
													//obj.setParentsPhoneNo(parentDetail.get(s).getPhoneNumber());
													obj.setPerClassSection(clslist1.get(j).getClazz().getClassName()+"/"+clslist1.get(j).getClazz().getClassSection());
													
													studlist2.add(obj);
													studentPerformanceDataBean.setStudlist(studlist2);
													System.out.println("studentPerformanceDataBean.setStudlist---->"+studentPerformanceDataBean.getStudlist().size());
												}
											}
										}
									}
								
								}
							}
						} else if (rollType.equalsIgnoreCase("Teacher")) {
							if (studentPerformanceDataBean.getPerClassSection().equalsIgnoreCase("All")) {
								q = entitymanager.createQuery("from Class where school_ID=? and status=?");
								q.setParameter(1, school_ID);
								q.setParameter(2, "Active");
							}
							else{
								StringTokenizer st = new StringTokenizer(studentPerformanceDataBean.getPerClassSection());
								String className = st.nextToken("/");
								String section = studentPerformanceDataBean.getPerClassSection();
								String sectionName = section.substring(section.lastIndexOf("/") + 1);
								
								follow = "All Teacher";
								q = entitymanager.createQuery("from Class where class_name=? and class_section=? and school_ID=? and status='Active'");
								q.setParameter(1, className);
								q.setParameter(2, sectionName);
								q.setParameter(3, school_ID);
							}
							
							List<Class> clslist = (List<Class>) q.getResultList();
							if (clslist.size() > 0) {
								for (int i = 0; i < clslist.size(); i++) {
									clsid = clslist.get(i).getClass_ID();
									q = null;
									q = entitymanager.createQuery("from StudentClass where class_ID=? and status='Active'");
									q.setParameter(1, clsid);
									List<StudentClass> clslist1 = (List<StudentClass>) q.getResultList();
									if (clslist1.size() > 0) {
										for (int j = 0; j < clslist1.size(); j++) {
											studid = clslist1.get(j).getStudent().getStudent_ID();
											logger.info("------studentID------"+studid);
											q = null;
											q = entitymanager.createQuery("from Student where student_ID=? and status='Active'");
											q.setParameter(1, studid);
											List<Student> studentlist = (List<Student>) q.getResultList();
											q = null;
											q = entitymanager.createQuery("from StudentDetail where student_ID=?");
											q.setParameter(1, studid);
											List<StudentDetail> studlist = (List<StudentDetail>) q.getResultList();
											
											/*q = null;
											q = entitymanager.createQuery("from StudentParent where student_ID=?");
											q.setParameter(1, studid);
											List<StudentParent> studentparent = (List<StudentParent>) q.getResultList();
											logger.info("---studentID---"+studid); 
											logger.info("parent id ------>"+studentparent.get(0).getParent().getParent_ID()+"-----size----"+studentparent.size());
											if (studentparent.size()>0) {
												q = null;
												q = entitymanager.createQuery("from ParentDetail where parent_ID=?");
												q.setParameter(1, studentparent.get(0).getParent());
											}
											List<ParentDetail> parentDetail = (List<ParentDetail>) q.getResultList();
											
											logger.info("------parentdetails-------"+parentDetail.size());*/
											q = null;
											q = entitymanager.createQuery("from StudentPerformance  where student_ID=? and createDate=? and status='Active'");
											q.setParameter(1, studid);
											q.setParameter(2, studentPerformanceDataBean.getTtdate());
											List<StudentPerformance> perlist2 = (List<StudentPerformance>) q
													.getResultList();
											if (studentlist.size() > 0 && studlist.size() > 0 && perlist2.size() > 0) {
												for (int s = 0; s < studlist.size(); s++) {
													StudentPerformanceDataBean obj = new StudentPerformanceDataBean();
													obj.setTtdate(perlist2.get(s).getCreateDate()); 
													obj.setPerformStudID(studentlist.get(s).getRollNumber());
													obj.setStuName(studlist.get(s).getFirstName());
													obj.setParName(studlist.get(s).getFatherName() + ","
															+ studlist.get(s).getMotherName());
													obj.setStuApp(perlist2.get(s).getAppearanceStatus());
													obj.setStuAtt(perlist2.get(s).getAttitudeStatus());
													
													//obj.setParentsMail(parentDetail.get(s).getEmaiId());
													//obj.setParentsPhoneNo(parentDetail.get(s).getPhoneNumber());
													obj.setPerClassSection(clslist1.get(j).getClazz().getClassName()+"/"+clslist1.get(j).getClazz().getClassSection());
													
													
													studlist2.add(obj);
													studentPerformanceDataBean.setStudlist(studlist2);
												}
											}
										}
									}
								
								}
								}
						} else if (rollType.equalsIgnoreCase("Student")) {
							follow = "All Student";

							q = null;
							q = entitymanager.createQuery("from Student where roll_number=? and school_ID=? and status='Active'");
							q.setParameter(1, studentPerformanceDataBean.getStudID());
							q.setParameter(2, school_ID);
							List<Student> studentlist = (List<Student>) q.getResultList();
							if (studentlist.size() > 0) {
								studid1 = studentlist.get(0).getStudent_ID();
								q = null;
								q = entitymanager.createQuery("from StudentDetail where student_ID=?");
								q.setParameter(1, studid1);
								List<StudentDetail> studlist = (List<StudentDetail>) q.getResultList();
								
								q = null;
								q = entitymanager.createQuery("from StudentParent where student_ID=?");
								q.setParameter(1, studid1);
								List<StudentParent> studentparent = (List<StudentParent>) q.getResultList();
								System.out.println("parent id ------>"+studentparent.get(0).getParent().getParent_ID()+"-----size----"+studentparent.size());
								if (studentparent.size()>0) {
									q = null;
									q = entitymanager.createQuery("from ParentDetail where parent_ID=?");
									q.setParameter(1, studentparent.get(0).getParent());
								}
								List<ParentDetail> parentDetail = (List<ParentDetail>) q.getResultList();
								
								
								
								
								q = null;
								q = entitymanager
										.createQuery("from StudentPerformance where student_ID=? and status='Active'");
								q.setParameter(1, studid1);
								List<StudentPerformance> perlist2 = (List<StudentPerformance>) q.getResultList();
								if (studentlist.size() > 0 && studlist.size() > 0 && perlist2.size() > 0) {
									for (int s = 0; s < studlist.size(); s++) {
										StudentPerformanceDataBean obj = new StudentPerformanceDataBean();
										obj.setTtdate(perlist2.get(s).getCreateDate()); 
										obj.setPerformStudID(studentlist.get(s).getRollNumber());
										obj.setStuName(studlist.get(s).getFirstName());
										obj.setParName(studlist.get(s).getFatherName() + ","
												+ studlist.get(s).getMotherName());
										obj.setStuApp(perlist2.get(s).getAppearanceStatus());
										obj.setStuAtt(perlist2.get(s).getAttitudeStatus());
										
										obj.setParentsMail(parentDetail.get(s).getEmaiId());
										obj.setParentsPhoneNo(parentDetail.get(s).getPhoneNumber());
										obj.setPerClassSection("");
											
									
										studlist2.add(obj);
										studentPerformanceDataBean.setStudlist(studlist2);
									}
								}

							}
						} else if (rollType.equalsIgnoreCase("Parent")) {
							follow = "All Parents";
								q = entitymanager.createQuery("from Parent where school_ID=? and rollNumber=? and status=?");
								q.setParameter(1, school_ID);
								q.setParameter(2, roleStatus.get(0).getPersonRoleNumber());
								q.setParameter(3, "Active");
								List<Parent> parent = (List<Parent>) q.getResultList();
								if (parent.size() > 0) {
									q = entitymanager.createQuery("from StudentParent where parent_ID=? and status=?");
									q.setParameter(1, parent.get(0).getParent_ID());
									q.setParameter(2, "Active");
									List<StudentParent> studentparent = (List<StudentParent>) q.getResultList();
									if (studentparent.size() > 0) {
										for (int i = 0; i < studentparent.size(); i++) {
											String rollno = studentparent.get(i).getStudent().getRollNumber();
											studentrollno.add(rollno);
										}
									}
									HashSet<String> studentId = new HashSet<String>(studentrollno);
									studentrollno.clear();
									studentrollno.addAll(studentId);
									if(studentrollno.size()>0)
									{
										for (int i = 0; i < studentrollno.size(); i++)
										{
											q = null;
											q = entitymanager.createQuery("from Student where roll_number=? and status='Active'");
											q.setParameter(1, studentrollno.get(i));
											List<Student> studentlist = (List<Student>) q.getResultList();
											if (studentlist.size() > 0) {
												studid1 = studentlist.get(0).getStudent_ID();
												q = null;
												q = entitymanager.createQuery("from StudentDetail where student_ID=?");
												q.setParameter(1, studid1);
												List<StudentDetail> studlist = (List<StudentDetail>) q.getResultList();
												
												if (studentPerformanceDataBean.getTtdate() == null || studentPerformanceDataBean.getTtdate().equals("")) {

													q = null;
													q = entitymanager
															.createQuery("from StudentPerformance where student_ID=? and status='Active'");
													q.setParameter(1, studid1);
													List<StudentPerformance> perlist2 = (List<StudentPerformance>) q.getResultList();
													if (perlist2.size() > 0) {
														for (int s = 0; s < perlist2.size(); s++) {
															StudentPerformanceDataBean obj = new StudentPerformanceDataBean();
															obj.setTtdate(perlist2.get(s).getCreateDate()); 
															System.out.println("Performance Date ----->"+obj.getTtdate());
															obj.setPerformStudID(studentlist.get(0).getRollNumber());
															obj.setStuName(studlist.get(0).getFirstName());
															obj.setParName(studlist.get(0).getFatherName() + ","
																	+ studlist.get(0).getMotherName());
															obj.setStuApp(perlist2.get(s).getAppearanceStatus());
															obj.setStuAtt(perlist2.get(s).getAttitudeStatus());
															studlist2.add(obj);
														}
														studentPerformanceDataBean.setStudlist(studlist2);
													}
													
												}else{
													q = null;
													q = entitymanager
															.createQuery("from StudentPerformance where student_ID=? and status='Active' and createDate=?");
													q.setParameter(1, studid1);
													q.setParameter(2, studentPerformanceDataBean.getTtdate());
													List<StudentPerformance> perlist2 = (List<StudentPerformance>) q.getResultList();
													if (perlist2.size() > 0) {
														for (int s = 0; s < perlist2.size(); s++) {
															StudentPerformanceDataBean obj = new StudentPerformanceDataBean();
															obj.setTtdate(perlist2.get(s).getCreateDate()); 
															System.out.println("Performance Date ----->"+obj.getTtdate());
															obj.setPerformStudID(studentlist.get(0).getRollNumber());
															obj.setStuName(studlist.get(0).getFirstName());
															obj.setParName(studlist.get(0).getFatherName() + ","
																	+ studlist.get(0).getMotherName());
															obj.setStuApp(perlist2.get(s).getAppearanceStatus());
															obj.setStuAtt(perlist2.get(s).getAttitudeStatus());
															studlist2.add(obj);
														}
														studentPerformanceDataBean.setStudlist(studlist2);
													}	
												}
												
											}
										}
									}
							
							}
						} else {
							logger.debug("------Not valid User------");
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn(" exception - "+e);
			e.printStackTrace();
		}
		return studlist2;

	}
	/**
	 * @author Jeevini This method is used to view the student Performance when
	 *         the view button clicked
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<StudentPerformance> getPerformView(StudentPerformanceDataBean studentPerformanceDataBean) {
		logger.info("-----------Inside getPerformView method()----------------");
		List<StudentPerformance> performlist = null;
		Query q = null;
		int studid = 0;
		try {
			q = entitymanager.createQuery("from Student where roll_number=? and status='Active'");
			q.setParameter(1, studentPerformanceDataBean.getPerformStudID());
			logger.debug("------roll number-----" + studentPerformanceDataBean.getPerformStudID());
			List<Student> studlist = (List<Student>) q.getResultList();
			logger.debug("-----list size-----" + studlist.size());
			if (studlist.size() > 0) {
				studid = studlist.get(0).getStudent_ID();

				q = entitymanager.createQuery("from StudentPerformance where student_ID=? and status='Active'");
				q.setParameter(1, studid);
				performlist = (List<StudentPerformance>) q.getResultList();
				q = null;
				q = entitymanager.createQuery("from StudentDetail where student_ID=?");
				q.setParameter(1, studid);
				List<StudentDetail> detaillist = (List<StudentDetail>) q.getResultList();
				if (detaillist.size() > 0) {
					studentPerformanceDataBean.setStuName1(detaillist.get(0).getFirstName());
				}
				q = null;
				q = entitymanager.createQuery("from StudentClass where student_ID=?");
				q.setParameter(1, studid);
				List<StudentClass> studentParent = (List<StudentClass>) q.getResultList();
				if (studentParent.size() > 0) {
					int classid=studentParent.get(0).getClazz().getClass_ID();
					q = null;
					q = entitymanager.createQuery("from TeacherClass where class_ID=?");
					q.setParameter(1, classid);
					List<TeacherClass> classlist=(List<TeacherClass>)q.getResultList();
					if(classlist.size()>0){
						int teaid=classlist.get(0).getTeacher().getTeacher_ID();
						q = null;
						q = entitymanager.createQuery("from Teacher where teacher_ID=?");
						q.setParameter(1, teaid);
						List<Teacher> list=(List<Teacher>)q.getResultList();
						if(list.size()>0){
						studentPerformanceDataBean.setStuPar1(list.get(0).getRollNumber());
					}
				}
			}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return performlist;
	}

	/**
	 * @author Jeevini This method is used to delete student performance record
	 */
	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	@Override
	public String getperformDelete(String personID, StudentPerformanceDataBean studentPerformanceDataBean) {
		logger.info("-----------Inside getperformDelete method()----------------");
		String status = "Fail";
		Query q = null;
		List<Person> rollStatus = null;
		int school_ID = 0;
		int student_ID = 0;
		int perid = 0;
		try {
			logger.debug("Inised");
			rollStatus = new ArrayList<Person>();
			if (personID != null && studentPerformanceDataBean.getPerformStudID() != null) {
				rollStatus = getRollType(personID);
				logger.debug("Roll Size" + rollStatus.size());
				if (rollStatus.size() > 0) {
					if ((rollStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) || (rollStatus.get(0).getPersonRole().equalsIgnoreCase("Teacher"))) {
						school_ID = rollStatus.get(0).getSchool().getSchool_ID();
						q = entitymanager.createQuery("from Student where roll_number=? and school_ID=? and status='Active'");
						q.setParameter(1, studentPerformanceDataBean.getPerformStudID());
						q.setParameter(2, rollStatus.get(0).getSchool().getSchool_ID());
						List<Student> studlist = (List<Student>) q.getResultList();
						if (studlist.size() > 0) {
							student_ID = studlist.get(0).getStudent_ID();
						}
						logger.debug("--del studid-----" + student_ID);
						q = null;
						q = entitymanager.createQuery("from StudentPerformance where student_ID=? and createDate=? and status=?");
						q.setParameter(1, student_ID);
						q.setParameter(2, studentPerformanceDataBean.getBdate());
						q.setParameter(3, "Active");
						List<StudentPerformance> perlist = (List<StudentPerformance>) q.getResultList();
						logger.debug("----------del list size--------" + perlist.size());
						if (perlist.size() > 0) {
							perid = perlist.get(0).getPerformance_ID();
						}
						if (perid > 0) {
							StudentPerformance student = entitymanager.find(StudentPerformance.class, perid);
							student.setStatus("De-Active");
							entitymanager.merge(student);
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
	 * @author Jeevini This method is used to get student performance info for
	 *         edit
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<StudentPerformance> getPerformDetails(StudentPerformanceDataBean studentPerformanceDataBean) {
		logger.info("-----------Inside getPerformDetails method()----------------");
		Query q = null;
		int studid = 0;
		int classid = 0;
		List<StudentPerformance> studlist1 = null;
		logger.debug("-------roll num-----" + studentPerformanceDataBean.getPerformStudID());
		if (studentPerformanceDataBean.getPerformStudID() != null) {
			try {
				q = entitymanager.createQuery("from Student where roll_number=? and status='Active'");
				q.setParameter(1, studentPerformanceDataBean.getPerformStudID());
				List<Student> studlist = (List<Student>) q.getResultList();
				if (studlist.size() > 0) {
					studid = studlist.get(0).getStudent_ID();
				}
				q = null;
				q = entitymanager.createQuery("from StudentDetail where student_ID=?");
				q.setParameter(1, studid);
				List<StudentDetail> studentdetail=(List<StudentDetail>)q.getResultList();
				if(studentdetail.size()>0){
					studentPerformanceDataBean.setStuName(studentdetail.get(0).getFirstName()+""+studentdetail.get(0).getLastName()+"/"+studlist.get(0).getRollNumber());
				}
				q = null;
				q = entitymanager.createQuery("from StudentPerformance where student_ID=? and status='Active' and createDate=?");
				q.setParameter(1, studid);
				q.setParameter(2, studentPerformanceDataBean.getBdate());
				studlist1 = (List<StudentPerformance>) q.getResultList();
				logger.debug("-------edit list size------" + studlist1.size());
				if (studlist1.size() > 0) {
					logger.debug("------app status-----" + studlist1.get(0).getAppearanceStatus());
				}
				q = null;
				q = entitymanager.createQuery("from StudentClass where student_ID=? and status='Active'");
				q.setParameter(1, studid);
				List<StudentClass> classlist = (List<StudentClass>) q.getResultList();
				if (classlist.size() > 0) {
					classid = classlist.get(0).getClazz().getClass_ID();
				}
				q = null;
				q = entitymanager.createQuery("from Class where class_Id=?");
				q.setParameter(1, classid);
				List<Class> clslist = (List<Class>) q.getResultList();
				if (clslist.size() > 0) {
					studentPerformanceDataBean
							.setClssection(clslist.get(0).getClassName() + "/" + clslist.get(0).getClassSection());
				}
				logger.debug("---studlist1 size----" + studlist1.size());

			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
		}
		logger.debug("------outside getperformDetail--------");
		return studlist1;
	}

	/**
	 * @author Jeevini This method is used to edit the student performance
	 */

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getPerClassList1(String personID,StudentPerformanceDataBean studentPerformanceDataBean) {
		logger.info("-----------Inside getPerClassList1 method()----------------");
		List<String> stt =new ArrayList<String>();
		List<Person> roll=null;
		int schoolid=0;
		ArrayList<StudentPerformanceDataBean> picklist2 = new ArrayList<StudentPerformanceDataBean>();
		Query q = null;
		try {
			roll=new ArrayList<Person>();
			if (personID != null) {
				roll = getRollType(personID);
				if(roll.size()>0){
					schoolid=roll.get(0).getSchool().getSchool_ID();
			q = entitymanager.createQuery("from Class where school_ID=? and status='Active'");
			q.setParameter(1, schoolid);
			List<com.stumosys.shared.Class> picklist1 = (List<com.stumosys.shared.Class>) q.getResultList();
			logger.debug("--------list size-----" + picklist1.size());
			if (picklist1.size() > 0) {
				for (int i = 0; i < picklist1.size(); i++) {
					StudentPerformanceDataBean pickobj = new StudentPerformanceDataBean();
					pickobj.setStuCls1(picklist1.get(i).getClassName() + "/" + picklist1.get(i).getClassSection());
					picklist2.add(pickobj);
					studentPerformanceDataBean.setStuClassList(picklist2);
				}
				logger.debug("------domain list size------" + studentPerformanceDataBean.getStuClassList().size());
			}
			}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.debug("-------outside  picklist------");
		return stt;

	}

	@Override
	public List<ReportCardDataBean> getRepoartCard(String personID, ReportCardDataBean reportCardDataBean) {
		logger.info("-----------Inside getRepoartCard method()----------------");
		int class_ID = 0;
		List<Person> roleStatus = null;
		List<String> idList = null;
		int school_ID = 0;
		String rollType = "NotValid";
		List<Class> classList = null;
		int tea_ID = 0;
		int student_ID = 0;
		List<Reportcard> report = null;
		int total = 0;
		List<ReportCardDataBean> result = null;
		try {
			idList=new ArrayList<String>();
			roleStatus = new ArrayList<Person>();
			classList = new ArrayList<Class>();
			report = new ArrayList<Reportcard>();
			result = new ArrayList<ReportCardDataBean>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						classList = getClassID(personID, school_ID, reportCardDataBean.getStudMarkClass());
						tea_ID = getTeacherID(roleStatus.get(0).getPersonRoleNumber());
						if(!reportCardDataBean.getViewMarkStuName().equalsIgnoreCase("All"))
						{
						String stuid = reportCardDataBean.getViewMarkStuName();
						String studid = stuid.substring(stuid.lastIndexOf("/") + 1);
						student_ID = getStudentID(studid);
						logger.debug("Class List" + classList.size() + "Student ID" + student_ID);
						if (classList.size() > 0) {
							class_ID = classList.get(0).getClass_ID();
							if (class_ID > 0 && student_ID > 0) {
								logger.debug("Class List 1" + classList.size());

								report = getStudentReport(class_ID, student_ID, school_ID, personID,
										reportCardDataBean.getExamMarkTitle());
								logger.debug("----" + report.size());
								if (report.size() > 0) {
									for (int i = 0; i < report.size(); i++) {
										ReportCardDataBean dataBean = new ReportCardDataBean();
										dataBean.setRollNo(report.get(i).getStudent().getRollNumber());
										dataBean.setExamMarkTitle(report.get(i).getExamTitle());
										dataBean.setGrade(report.get(i).getGrade());
										dataBean.setMarkSubTitle(report.get(i).getSubject().getSujectName() + "/"
												+ report.get(i).getSubject().getSubjectCode());
										dataBean.setMark(report.get(i).getMark()); 
										dataBean.setsNo(String.valueOf(i + 1));
										System.out.println("SNo ------->"+dataBean.getsNo());
										dataBean.setResultStatus(report.get(i).getResultStatus());
										total += Integer.parseInt(report.get(i).getMark());
										dataBean.setTotalMark(String.valueOf(total));
										result.add(dataBean);
									}
								}
							}
						}
					}else if(reportCardDataBean.getViewMarkStuName().equalsIgnoreCase("All")){
						if (classList.size() > 0) {
							class_ID = classList.get(0).getClass_ID();
							if(class_ID > 0){
								idList=getStudentIDList(reportCardDataBean, personID);
								if(idList.size() > 0){
									int j=1;
									for(int m=0;m < idList.size(); m++)
									{
									student_ID = getStudentID(idList.get(m).split("/")[1]);
									if (class_ID > 0 && student_ID > 0) {
										logger.debug("Class List 1" + classList.size());

										report = getStudentReport(class_ID, student_ID, school_ID, personID,
												reportCardDataBean.getExamMarkTitle());
										logger.debug("----" + report.size());
										total=0;
										if (report.size() > 0) {
											for (int i = 0; i < report.size(); i++) {
												ReportCardDataBean dataBean = new ReportCardDataBean();
												dataBean.setRollNo(idList.get(m));
												logger.info("ROLL NUMBER =========>"+dataBean.getRollNo()); 
												dataBean.setExamMarkTitle(report.get(i).getExamTitle());
												dataBean.setGrade(report.get(i).getGrade());
												dataBean.setMarkSubTitle(report.get(i).getSubject().getSujectName() + "/"
														+ report.get(i).getSubject().getSubjectCode());
												dataBean.setMark(report.get(i).getMark());
												dataBean.setsNo(String.valueOf(j));
												logger.info("SNO =========>"+dataBean.getsNo()); 
												dataBean.setResultStatus(report.get(i).getResultStatus());
												total += Integer.parseInt(report.get(i).getMark());
												dataBean.setTotalMark(String.valueOf(total));
												result.add(dataBean);
												j++;
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
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private List<Reportcard> getStudentReport(int class_ID, int student_ID, int school_ID, String personID,
			String examMarkTitle) {
		logger.info("-----------Inside getStudentReport method()----------------");
		Query q = null;
		List<Reportcard> finalResult = null;
		try {
			finalResult = new ArrayList<Reportcard>();
			logger.debug("Inside getStudentReport");
			if (personID != null) {
				if(!examMarkTitle.equalsIgnoreCase("All")){
				q = entitymanager.createQuery(
						"from Reportcard where examTitle=? and student_ID=? and school_ID=? and class_ID=? and status='Active'");
				q.setParameter(1, examMarkTitle);
				q.setParameter(2, student_ID);
				q.setParameter(3, school_ID);
				q.setParameter(4, class_ID);
				List<Reportcard> result = (List<Reportcard>) q.getResultList();
				if (result.size() > 0) {
					finalResult.addAll(result);
				}
				logger.debug("---------------" + finalResult.size());
				}else if(examMarkTitle.equalsIgnoreCase("All")){
					q = entitymanager.createQuery(
							"from Reportcard where  student_ID=? and school_ID=? and class_ID=? and status='Active'");
					q.setParameter(1, student_ID);
					q.setParameter(2, school_ID);
					q.setParameter(3, class_ID);
					List<Reportcard> result = (List<Reportcard>) q.getResultList();
					if (result.size() > 0) {
						finalResult.addAll(result);
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return finalResult;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String insertLibrary(String personID, LibraryDataBean libraryDataBean) {
		logger.info("-----------Inside insertLibrary method()----------------");
		String status = "Fail";
		List<Person> roleStatus = null;
		String rollType = "NotValid";
		int school_ID = 0;
		int noticeID = 0;
		List<Library> libraryList = null;
		Query q1 = null;
		int libID = 0;
		try {
			roleStatus = new ArrayList<Person>();
			libraryList = new ArrayList<Library>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						libraryList = getLibraryDeatails(libraryDataBean.getAuthorName(), libraryDataBean.getBookName(),
								libraryDataBean.getBookeditions(), school_ID);
						logger.debug("size "+libraryList.size());
						if (libraryList.size() > 0) {
							status = "Exsist";
						} else {
							Library lib = new Library();
							lib.setAuthorName(libraryDataBean.getAuthorName());
							lib.setBookEdition(libraryDataBean.getBookeditions());
							lib.setBookName(libraryDataBean.getBookName());
							if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
								lib.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
								lib.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
							}else{
								lib.setCreatedDate(date);
								lib.setCreatedTime(timestamp);
							}	
							lib.setDescription(libraryDataBean.getBookDescription());
							lib.setPages(libraryDataBean.getBookPages());
							lib.setPrice(libraryDataBean.getBookPrice());
							lib.setCategory(libraryDataBean.getBookCategory());
							lib.setSchool(entitymanager.find(School.class, school_ID));
							lib.setStatus("Active");
							lib.setPenaltyFee(libraryDataBean.getBookFee());
							lib.setDueType(libraryDataBean.getBookDueType());
							try{
								if(libraryDataBean.getBookQty().equalsIgnoreCase("")){
									lib.setQuantity("0");
								}else{
									lib.setQuantity(libraryDataBean.getBookQty());
								}								
							}catch(Exception e){
								lib.setQuantity("0");
							}
							
							entitymanager.persist(lib);

							StringTokenizer stoken = new StringTokenizer(libraryDataBean.getParfilePath());
							Date d = dateFormat.parse(stoken.nextToken("/"));
							String tempPath = libraryDataBean.getParfilePath();
							String path = tempPath.substring(tempPath.lastIndexOf("/") + 1);

							q1 = entitymanager.createQuery("from Library");
							List<Library> res = (List<Library>) q1.getResultList();
							if (res.size() > 0) {
								libID = res.get(res.size() - 1).getLibrary_ID();
								// Insert ImagePath table
								ImagePath imagepath = new ImagePath();
								imagepath.setDate(d);
								imagepath.setPath(path);
								imagepath.setName(libraryDataBean.getBookName());
								imagepath.setRollStatus("Library");
								imagepath.setLibrary(entitymanager.find(Library.class, libID));
								imagepath.setStatus("Active");
								entitymanager.persist(imagepath);

								status = "Success";
							}
						}
					}
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn(" exception - "+e);
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	private List<Library> getLibraryDeatails(String authorName, String bookName, String bookeditions, int school_ID) {
		logger.info("-----------Inside getLibraryDeatails method()----------------");
		Query q = null;
		List<Library> res = null;
		try {
			if(bookeditions.equalsIgnoreCase(""))
			{
			res = new ArrayList<Library>();
			q = entitymanager.createQuery("from Library where school_ID=? and authorName=? and bookName=?  and status='Active'");
			q.setParameter(1, school_ID);
			q.setParameter(2, authorName);
			q.setParameter(3, bookName);
			/*q.setParameter(4, bookeditions);*/
			List<Library> result = (List<Library>) q.getResultList();
			if (result.size() > 0) {
				res.addAll(result);
			}
			}
			else
			{
				res = new ArrayList<Library>();
				logger.debug("school id "+school_ID);
				q = entitymanager.createQuery(
						"from Library where school_ID=? and authorName=? and bookName=?  and bookEdition=? and status='Active'");
				q.setParameter(1, school_ID);
				q.setParameter(2, authorName);
				q.setParameter(3, bookName);
				q.setParameter(4, bookeditions);
				List<Library> result = (List<Library>) q.getResultList();
				logger.debug("result size"+result.size());
				if (result.size() > 0) {
					res.addAll(result);
				}
				
			}
				
		} catch (Exception e) {
			//e.printStackTrace();
			//logger.warn(" exception - "+e);
			logger.warn("Exception -->"+e.getMessage());
		}
		return res;
	}
	/*Neela Oct 25 getBookListView*/
	@Override
	@SuppressWarnings("unchecked")
	public List<LibraryDataBean> getBookListView(String personID, String name) {
		logger.info("-----------Inside getBookListView method()----------------");
		List<LibraryDataBean> resultList = null;
		  List<String> studentrollno = new ArrayList<String>();
		Query q = null;//Query q1 = null;
		List<Person> roleStatus = null;
		String rollType = "NotValid";
		int school_ID = 0;
		try {
			resultList = new ArrayList<LibraryDataBean>();
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						if(rollType.equalsIgnoreCase("Parent"))
					       {

					        studentrollno=getparentstudent(personID);
					        if(studentrollno.size()>0)
					        {
					       /*  for (int j = 0; j < studentrollno.size(); j++) 
					         {
					        int  studentID = getStudentID(studentrollno.get(j));
					          if (studentID > 0) {*/
					        	  if(school_ID==1  || school_ID==2  || school_ID ==3 ||  school_ID==4 || school_ID==5 || school_ID==6){
					        		  q = entitymanager .createQuery("from Library where school_ID=? and category=? and status='Active'");
										q.setParameter(1, school_ID);
										q.setParameter(2, name);					        		 
					        	  }else{
					        		  q = entitymanager .createQuery("from Library where school_ID=? and status='Active'");
										q.setParameter(1, school_ID);
					        	  }					        	 
									List<Library> res = (List<Library>) q.getResultList();
									if (res.size() > 0) {
										logger.debug("ssssssssiiiiizzzzzzzew "+res.size());
										for (int i = 0; i < res.size(); i++) {
											BigInteger borrowQty=BigInteger.ZERO;
											BigInteger libQty=BigInteger.ZERO;
											//BigInteger tempQty=BigInteger.ZERO;
											String qty="0";
											if(res.get(i).getQuantity() == null){
												qty="0";
											}else{
												qty=res.get(i).getQuantity();
											}
											
											libQty=new BigInteger(qty);
										/*	q1=entitymanager.createQuery("from Browerbook where library_ID=? and student_ID=? and status=?");
											q1.setParameter(1, res.get(i).getLibrary_ID());
											q1.setParameter(2, studentID);
											q1.setParameter(3, "Borrowed");
											List<Browerbook> libinfo=(List<Browerbook>)q1.getResultList();
											if(libinfo.size() > 0){
												borrowQty=BigInteger.valueOf(libinfo.size());
												tempQty=libQty.subtract(borrowQty);*/
											LibraryDataBean lib = new LibraryDataBean();
											lib.setsNo((String.valueOf(i + 1)));
											lib.setAuthorName(res.get(i).getAuthorName());
											lib.setBookCategory(res.get(i).getCategory());
											lib.setBookDescription(res.get(i).getDescription());
											lib.setBookeditions(res.get(i).getBookEdition());
											lib.setBookPages(res.get(i).getPages());
											lib.setBookPrice(res.get(i).getPrice());
											lib.setBookName(res.get(i).getBookName());
											lib.setBookDueType(res.get(i).getDueType());
											lib.setBookFee(res.get(i).getPenaltyFee());
											lib.setBookQty(libQty.toString());
											lib.setBorrowedBookQty(borrowQty.toString());
											resultList.add(lib);
											}
										}
									/*}
					          }
					         }*/
					        }
					       }
						else{
							
							 if(school_ID==1  || school_ID==2  || school_ID ==3 ||  school_ID==4 || school_ID==5 || school_ID==6){
								 q = entitymanager
											.createQuery("from Library where school_ID=? and category=? and status='Active'");
									q.setParameter(1, school_ID);
									q.setParameter(2, name);
							 }else{
								 q = entitymanager
											.createQuery("from Library where school_ID=? and status='Active'");
									q.setParameter(1, school_ID);
							 }
				        	 
								List<Library> res = (List<Library>) q.getResultList();
								if (res.size() > 0) {
									for (int i = 0; i < res.size(); i++) {
										BigInteger borrowQty=BigInteger.ZERO;
										BigInteger libQty=BigInteger.ZERO;
										BigInteger tempQty=BigInteger.ZERO;
										String qty="0";
										if(res.get(i).getQuantity() == null){
											qty="0";
										}else{
											qty=res.get(i).getQuantity();
										}
										
										libQty=new BigInteger(qty);
										
										LibraryDataBean lib = new LibraryDataBean();
										lib.setsNo((String.valueOf(i + 1)));
										lib.setAuthorName(res.get(i).getAuthorName());
										lib.setBookCategory(res.get(i).getCategory());
										lib.setBookDescription(res.get(i).getDescription());
										lib.setBookeditions(res.get(i).getBookEdition());
										lib.setBookPages(res.get(i).getPages());
										lib.setBookPrice(res.get(i).getPrice());
										lib.setBookName(res.get(i).getBookName());
										lib.setBookDueType(res.get(i).getDueType());
										lib.setBookFee(res.get(i).getPenaltyFee());
										lib.setBookQty(libQty.toString());
										lib.setBorrowedBookQty(borrowQty.toString());
										resultList.add(lib);
										
									}
								}
				          }
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			//logger.warn(" exception - "+e);
		}
		return resultList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LibraryDataBean> getBookDetailsView(String personID, LibraryDataBean libraryDataBean) {
		logger.info("-----------Inside getBookDetailsView method()----------------");
		List<LibraryDataBean> bookList = null;
		Query q = null;
		List<Person> roleStatus = null;
		String rollType = "NotValid";
		int school_ID = 0;
		try {
			bookList = new ArrayList<LibraryDataBean>();
			roleStatus = new ArrayList<Person>();
			List<Library> libraryList = null;
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						LibraryDataBean lib = new LibraryDataBean();
						libraryList = getLibraryDeatails(libraryDataBean.getAuthorName(), libraryDataBean.getBookName(),
								libraryDataBean.getBookeditions(), school_ID);
						if (libraryList.size() > 0) {
							lib.setLibrayList(libraryList);
						}
						q = entitymanager.createQuery(
								"from ImagePath where library_ID=? and rollStatus='Library' and status='Active'");
						q.setParameter(1, libraryList.get(0).getLibrary_ID());
						List<ImagePath> res = (List<ImagePath>) q.getResultList();
						if (res.size() > 0) {
							lib.setLibrayImageList(res);
						}
						bookList.add(lib);
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return bookList;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String updateLibrary(String personID, LibraryDataBean libraryDataBean) {
		logger.info("-----------Inside updateLibrary method()----------------");
		String status = "Fail";
		List<Person> roleStatus = null;
		String rollType = "NotValid";
		int school_ID = 0;
		int noticeID = 0;
		List<Library> libraryList = null;
		Query q = null;Query q1 = null;
		BigInteger borrowQty=BigInteger.ZERO;
		
		BigInteger tempQty=BigInteger.ZERO;
		int libID = 0;
		
		try {
			roleStatus = new ArrayList<Person>();
			libraryList = new ArrayList<Library>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						libraryList = getLibraryDeatails(libraryDataBean.getAuthorName(), libraryDataBean.getBookName(),
								libraryDataBean.getBookeditions(), school_ID);
						if (libraryList.size() > 0) {
							libID = libraryList.get(0).getLibrary_ID();

							q1=entitymanager.createQuery("from Browerbook where library_ID=? and status=?");
							q1.setParameter(1, libID);
							q1.setParameter(2, "Borrowed");
							List<Browerbook> borrowInfo=(List<Browerbook>)q1.getResultList();
							if(borrowInfo.size() > 0){
								String qty=String.valueOf(borrowInfo.size());
								borrowQty=new BigInteger(qty);
								tempQty=new BigInteger(libraryDataBean.getBookQty());
								int compareRes=borrowQty.compareTo(tempQty);
								if(compareRes == 1){
									libraryDataBean.setBorrowedBookQty(borrowQty.toString());
									status="Borrowed";
								}else if(compareRes == 0){
									Library lib = entitymanager.find(Library.class, libID);
									if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
										lib.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
										lib.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
									}else{
										lib.setCreatedDate(date);
										lib.setCreatedTime(timestamp);
									}	
									lib.setDescription(libraryDataBean.getBookDescription());
									lib.setPages(libraryDataBean.getBookPages());
									lib.setPrice(libraryDataBean.getBookPrice());
									lib.setDueType(libraryDataBean.getBookDueType());
									lib.setPenaltyFee(libraryDataBean.getBookFee());
									lib.setCategory(libraryDataBean.getBookCategory());
									lib.setSchool(entitymanager.find(School.class, school_ID));
									lib.setQuantity("0");
									entitymanager.merge(lib);
									status = "Success";
								}
								else{
									Library lib = entitymanager.find(Library.class, libID);
									if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
										lib.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
										lib.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
									}else{
										lib.setCreatedDate(date);
										lib.setCreatedTime(timestamp);
									}	
									lib.setDescription(libraryDataBean.getBookDescription());
									lib.setPages(libraryDataBean.getBookPages());
									lib.setPrice(libraryDataBean.getBookPrice());
									lib.setDueType(libraryDataBean.getBookDueType());
									lib.setPenaltyFee(libraryDataBean.getBookFee());
									lib.setCategory(libraryDataBean.getBookCategory());
									lib.setSchool(entitymanager.find(School.class, school_ID));
									lib.setQuantity(libraryDataBean.getBookQty());
									entitymanager.merge(lib);
									status = "Success";
								}
								
							}else{
							Library lib = entitymanager.find(Library.class, libID);
							if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
								lib.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
								lib.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
							}else{
								lib.setCreatedDate(date);
								lib.setCreatedTime(timestamp);
							}	
							lib.setDescription(libraryDataBean.getBookDescription());
							lib.setPages(libraryDataBean.getBookPages());
							lib.setPrice(libraryDataBean.getBookPrice());
							lib.setDueType(libraryDataBean.getBookDueType());
							lib.setPenaltyFee(libraryDataBean.getBookFee());
							lib.setCategory(libraryDataBean.getBookCategory());
							lib.setQuantity(libraryDataBean.getBookQty());
							lib.setSchool(entitymanager.find(School.class, school_ID));
							entitymanager.merge(lib);
							status = "Success";
							}
							if (libraryDataBean.getBookFile() != null) {
								StringTokenizer stoken = new StringTokenizer(libraryDataBean.getParfilePath());
								Date d = dateFormat.parse(stoken.nextToken("/"));
								String tempPath = libraryDataBean.getParfilePath();
								String path = tempPath.substring(tempPath.lastIndexOf("/") + 1);

								q = entitymanager.createQuery(
										"from ImagePath where library_ID=? and rollStatus='Library' and status='Active'");
								q.setParameter(1, libID);
								List<ImagePath> res = (List<ImagePath>) q.getResultList();
								if (res.size() > 0) {
									// Insert ImagePath table
									ImagePath imagepath = entitymanager.find(ImagePath.class, res.get(0).getImage_ID());
									imagepath.setDate(d);
									imagepath.setPath(path);
									imagepath.setName(libraryDataBean.getBookName());
									imagepath.setRollStatus("Library");
									imagepath.setLibrary(entitymanager.find(Library.class, libID));
									entitymanager.merge(imagepath);
								} else {
									ImagePath imagepath = new ImagePath();
									imagepath.setDate(d);
									imagepath.setPath(path);
									imagepath.setName(libraryDataBean.getBookName());
									imagepath.setRollStatus("Library");
									imagepath.setLibrary(entitymanager.find(Library.class, libID));
									imagepath.setStatus("Active");
									entitymanager.persist(imagepath);
								}
							}
							
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			//logger.warn("Inside Exception",e);
		}
		return status;
	}

	@Override
	@Transactional(value = "transactionManager")
	public String deleteBook(String personID, LibraryDataBean libraryDataBean) {
		logger.info("-----------Inside deleteBook method()----------------");

		String status = "Fail";
		List<Person> roleStatus = null;
		String rollType = "NotValid";
		int school_ID = 0;
		int noticeID = 0;
		List<Library> libraryList = null;
		Query q = null;
		int libID = 0;
		try {
			roleStatus = new ArrayList<Person>();
			libraryList = new ArrayList<Library>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						libraryList = getLibraryDeatails(libraryDataBean.getAuthorName(), libraryDataBean.getBookName(),
								libraryDataBean.getBookeditions(), school_ID);
						if (libraryList.size() > 0) {
							libID = libraryList.get(0).getLibrary_ID();
							Library lib = entitymanager.find(Library.class, libID);
							lib.setStatus("De-Active");
							if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
								lib.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
								lib.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
							}else{
								lib.setCreatedDate(date);
								lib.setCreatedTime(timestamp);
							}
							entitymanager.merge(lib);
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
//	@SuppressWarnings("unchecked")
	@Transactional(value = "transactionManager")
	public String insertBookBorrowed(String personID, LibraryDataBean libraryDataBean) {
		logger.info("-----------Inside insertBookBorrowed method()----------------");
		String status = "Fail";
		List<Person> roleStatus = null;
		String rollType = "NotValid";
		int school_ID = 0;
		int studentID = 0;

		List<Browerbook> borrowerList = null;
		Query q = null;
		int libID = 0;
		int libID1 = 0;
		int borrowedID = 0;
		int bookAvailableQty=0;
		
		try {
			roleStatus = new ArrayList<Person>();

			borrowerList = new ArrayList<Browerbook>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {

						logger.debug("Book" + libraryDataBean.getTargetList());
						logger.debug("Book" + libraryDataBean.getTargetList().size());
						String students=libraryDataBean.getLibStudID();
					      String studentid=students.substring(students.lastIndexOf("/")+1);
					      studentID = getStudentID(studentid);
						if (studentID > 0) {
							logger.debug("studentID" + studentID);
							borrowerList = getBorrowedBookID(personID, libraryDataBean.getBookCategory(), studentID);
							logger.debug("size "+borrowerList.size()+" list "+libraryDataBean.getTargetList().size());
							if (libraryDataBean.getTargetList().size() > 0) {
								for (int i = 0; i < libraryDataBean.getTargetList().size(); i++) {
									int bookTempQty=0;
									List<Library> libraryList = null;
									libraryList = new ArrayList<Library>();
									StringTokenizer st = new StringTokenizer(libraryDataBean.getTargetList().get(i));
									String bookname = st.nextToken("~");
									bookname = bookname.trim();
									logger.debug("bookname" + bookname);
									String author = libraryDataBean.getTargetList().get(i);
									String bookAuthor = author.substring(author.lastIndexOf("~") + 1);
									bookAuthor = bookAuthor.trim();
									StringTokenizer st1 = new StringTokenizer(bookAuthor);
									bookAuthor = st1.nextToken("/");
									bookAuthor = bookAuthor.trim();
									logger.debug("bookAuthor" + bookAuthor);
									String edition = libraryDataBean.getTargetList().get(i);
									String bookedition = edition.substring(edition.lastIndexOf("/") + 1);
									bookedition = bookedition.trim();
									logger.debug("bookedition" + bookedition);

									libraryList = getLibraryDeatails(bookAuthor, bookname, bookedition, school_ID);
									logger.debug("side "+libraryList.size());
									if (libraryList.size() > 0) {
										libID = libraryList.get(0).getLibrary_ID();
										if(libraryList.get(0).getQuantity() == null){
											bookTempQty=0;
										}else{
											bookTempQty=Integer.parseInt(libraryList.get(0).getQuantity());
										}
										bookAvailableQty=bookTempQty;
										if (borrowerList.size() > 0) {
											for (int j = 0; j < borrowerList.size(); j++) {
												libID1 = borrowerList.get(j).getLibrary().getLibrary_ID();
												if (libID1 == libID)// check
																	// already
																	// book is
																	// borrowed
												{
													logger.debug("No Changes");
													logger.debug("No Changes");
												} else {
													BigDecimal fineAmount = BigDecimal.ZERO;
													fineAmount = getFineAmountByDate(
															borrowerList.get(j).getCreatedDate(),
															borrowerList.get(j).getLibrary().getDueType(),
															borrowerList.get(j).getLibrary().getPenaltyFee());

													borrowedID = borrowerList.get(j).getBorrowerID();
													Browerbook book = entitymanager.find(Browerbook.class, borrowedID);
													book.setStatus("Return");
													if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
														book.setReturnDate(GetTimeZone.getDate("Asia/Makassar"));
														book.setReturnTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
													}else{
														book.setReturnDate(date);
														book.setReturnTime(timestamp);
													}	
													book.setFineAmount(fineAmount.toString());
													entitymanager.merge(book);
													entitymanager.flush();
													entitymanager.clear();
													
													bookAvailableQty=bookAvailableQty+1;
													Library lib=entitymanager.find(Library.class, libID);
													lib.setQuantity(String.valueOf(bookAvailableQty));
													entitymanager.merge(lib);
													entitymanager.flush();
													entitymanager.clear();
													
													status = "Success";
													logger.debug("Inside Merge");
												}
											}
										}
										logger.debug("lib id "+libID+" -- "+libID1);
										if (libID > 0) {
											if (libID != libID1) {
												
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
												brrowerBook.setStudent(entitymanager.find(Student.class, studentID));
												brrowerBook.setStatus("Borrowed");
												brrowerBook.setCategory(libraryDataBean.getBookCategory());
												entitymanager.persist(brrowerBook);
												entitymanager.flush();
												entitymanager.clear();
												logger.debug("indside insert");
												if(bookAvailableQty > 0){
													bookAvailableQty=bookAvailableQty-1;
												Library lib=entitymanager.find(Library.class, libID);
												lib.setQuantity(String.valueOf(bookAvailableQty));
												entitymanager.merge(lib);
												entitymanager.flush();
												entitymanager.clear();
												status = "Success";
												}
											}
										}

									}
								}
							} else {
								if (libraryDataBean.getTargetList().size() == 0) {
									for (int m = 0; m < borrowerList.size(); m++) {
										
										List<Library> libraryList = null;
										libraryList = new ArrayList<Library>();
										
										int bookTempQty=0;
										BigDecimal fineAmount = BigDecimal.ZERO;
										fineAmount = getFineAmountByDate(borrowerList.get(m).getCreatedDate(),
												borrowerList.get(m).getLibrary().getDueType(),
												borrowerList.get(m).getLibrary().getPenaltyFee());

										borrowedID = borrowerList.get(m).getBorrowerID();
										Browerbook book = entitymanager.find(Browerbook.class, borrowedID);
										book.setStatus("Return");
										book.setFineAmount(fineAmount.toString());
										if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
											book.setReturnDate(GetTimeZone.getDate("Asia/Makassar"));
											book.setReturnTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
										}else{
											book.setReturnDate(date);
											book.setReturnTime(timestamp);
										}	
										entitymanager.merge(book);
										entitymanager.flush();
										entitymanager.clear();
										
										String bookname = borrowerList.get(m).getLibrary().getBookName();
										String bookAuthor = borrowerList.get(m).getLibrary().getAuthorName();
										String bookedition = borrowerList.get(m).getLibrary().getBookEdition();
										libraryList = getLibraryDeatails(bookAuthor, bookname, bookedition, school_ID);
										if (libraryList.size() > 0) {
											libID = libraryList.get(0).getLibrary_ID();
											if(libraryList.get(0).getQuantity() == null){
												bookTempQty=0;
											}else{
												bookTempQty=Integer.parseInt(libraryList.get(0).getQuantity());
											}
											bookAvailableQty=bookTempQty;
											
										if(bookAvailableQty < 0)bookAvailableQty=0;
										bookAvailableQty=bookAvailableQty+1;
										Library lib=entitymanager.find(Library.class, libID);
										lib.setQuantity(String.valueOf(bookAvailableQty));
										entitymanager.merge(lib);
										entitymanager.flush();
										entitymanager.clear();
										status = "Success";
										logger.debug("Inside Merge");
									}
								}
								}
							}
						}
					}
				}

			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("Exception -->"+e.getMessage());
			//logger.warn(" exception - "+e);
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	private List<Browerbook> getBorrowedBookID(String personID, String bookCategory, int studentID) {
		logger.info("-----------Inside getBorrowedBookID method()----------------");
		List<Browerbook> resultList = null;
		List<Person> roleStatus = null;
		String rollType = "NotValid";
		int school_ID = 0;
		List<Class> classList = null;
		Query q = null;
		int libID = 0;
		try {
			roleStatus = new ArrayList<Person>();
			classList = new ArrayList<Class>();
			resultList = new ArrayList<Browerbook>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {

						q = entitymanager.createQuery(
								"from Browerbook where school_ID=? and  category=? and student_ID=? and status='Borrowed'");
						q.setParameter(1, school_ID);
						q.setParameter(2, bookCategory);
						q.setParameter(3, studentID);
						List<Browerbook> res = (List<Browerbook>) q.getResultList();
						if (res.size() > 0) {
							resultList.addAll(res);
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
	public List<String> getBorrowedBook(String personID, String categoryname, String libStudID) {
		logger.info("-----------Inside getBorrowedBook method()----------------");
		List<String> resultList = null;
		List<Person> roleStatus = null;
		String rollType = "NotValid";
		int school_ID = 0;
		int studentID = 0;
		List<Class> classList = null;
		Query q = null;
		int libID = 0;
		try {
			roleStatus = new ArrayList<Person>();
			classList = new ArrayList<Class>();
			resultList = new ArrayList<String>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						studentID = getStudentID(libStudID);
						if (studentID > 0) {
							q = entitymanager.createQuery(
									"from Browerbook where school_ID=? and  category=? and student_ID=? and status='Borrowed'");
							q.setParameter(1, school_ID);
							q.setParameter(2, categoryname);
							q.setParameter(3, studentID);
							List<Browerbook> res = (List<Browerbook>) q.getResultList();
							if (res.size() > 0) {
								for (int i = 0; i < res.size(); i++) {
									String s = res.get(i).getLibrary().getBookName() + "~"
											+ res.get(i).getLibrary().getAuthorName() + "/"
											+ res.get(i).getLibrary().getBookEdition();
									resultList.add(s);
								}
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
	public List<LibraryDataBean> getBoorowerBookView(String personID, String name) {
		logger.info("-----------Inside getBoorowerBookView method()----------------");

		List<Person> roleStatus = null;
		String rollType = "NotValid";
		int school_ID = 0;
		int studentID = 0;
		List<LibraryDataBean> borrowList = null;
		Query q = null;
		int libID = 0;
		try {
			roleStatus = new ArrayList<Person>();
			borrowList = new ArrayList<LibraryDataBean>();

			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						String students=name;
					      String studentid=students.substring(students.lastIndexOf("/")+1);
						studentID = getStudentID(studentid);
						if (studentID > 0) {
							q = entitymanager.createQuery(
									"from Browerbook where school_ID=? and student_ID=? and status='Borrowed'");
							q.setParameter(1, school_ID);
							q.setParameter(2, studentID);
							List<Browerbook> res = (List<Browerbook>) q.getResultList();
							if (res.size() > 0) {
								for (int i = 0; i < res.size(); i++) {

									BigDecimal fineAmount = BigDecimal.ZERO;
									logger.debug("Fine Amount" + fineAmount);
									fineAmount = getFineAmountByDate(res.get(i).getCreatedDate(),
											res.get(i).getLibrary().getDueType(),
											res.get(i).getLibrary().getPenaltyFee());
									LibraryDataBean lib = new LibraryDataBean();
									lib.setBorroweDate(res.get(i).getCreatedDate());
									lib.setsNo(String.valueOf(i + 1));
									lib.setAuthorName(res.get(i).getLibrary().getAuthorName());
									lib.setBookeditions(res.get(i).getLibrary().getBookEdition());
									lib.setBookName(res.get(i).getLibrary().getBookName());
									lib.setBookCategory(res.get(i).getLibrary().getCategory());
									lib.setBookDueType(res.get(i).getLibrary().getDueType());
									lib.setBookFee(res.get(i).getLibrary().getPenaltyFee());
									lib.setBookfine(fineAmount.toString());
									borrowList.add(lib);
									logger.debug(res.get(i).getCreatedDate());
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return borrowList;
	}

	private BigDecimal getFineAmountByDate(Date createdDate, String dueType, String penaltyFee) {
		logger.info("-----------Inside getFineAmountByDate method()----------------");
		BigDecimal amount = BigDecimal.ZERO;
		Date d = new Date();
		int diff = 0;
		int due = 0;
		try {
			if (createdDate != null) {
				if (dueType.equalsIgnoreCase("7Days")) {
					due = 7;
				} else if (dueType.equalsIgnoreCase("14Days")) {
					due = 14;
				} else if (dueType.equalsIgnoreCase("30Days")) {
					due = 30;
				}

				DateTime date1 = new DateTime(createdDate);
				DateTime date2 = new DateTime(d);
				diff = Days.daysBetween(date1.toLocalDate(), date2.toLocalDate()).getDays();
				logger.debug(diff + "---------" + due);
				if (diff > due) {

					int tempDayDifference = diff - due;
					amount = new BigDecimal(penaltyFee).multiply(new BigDecimal(tempDayDifference));
				} else {

					amount = BigDecimal.ZERO;
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return amount;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getExamTitleName(String personID, String classname) {
		logger.info("-----------Inside getExamTitleName method()----------------");

		List<Person> roleStatus = null;
		String rollType = "NotValid";
		int school_ID = 0;
		int studentID = 0;
		List<String> res = null;
		Query q = null;
		int libID = 0;
		try {
			roleStatus = new ArrayList<Person>();
			res = new ArrayList<String>();

			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						StringTokenizer st = new StringTokenizer(classname);
						String subclass = st.nextToken("/");
						if(classname.equalsIgnoreCase("All")){
							q = entitymanager .createQuery("from ExamTable where school_ID=?  and status='Insert'");
							q.setParameter(1, school_ID);
							List<ExamTable> result = (List<ExamTable>) q.getResultList();
							if (result.size() > 0) {
								for (int i = 0; i < result.size(); i++) {
									String title = result.get(i).getExamTitle();
									res.add(title);
								}
							}
						}else{
						q = entitymanager
					        .createQuery("from ExamTable where school_ID=? and (class_name=? or class_name=?) and status='Insert'");
					      q.setParameter(1, school_ID);
					      q.setParameter(2, subclass);
					      q.setParameter(3, classname);
						List<ExamTable> result = (List<ExamTable>) q.getResultList();
						if (result.size() > 0) {
							for (int i = 0; i < result.size(); i++) {
								String title = result.get(i).getExamTitle();
								res.add(title);
							}
						}
						}
						Set<String> duplicate = new HashSet<String>();
						duplicate.addAll(res);
						res.clear();
						res.addAll(duplicate);
					}

				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	public List<String> getClassList(String personID) {
		logger.info("-----------Inside getClassList method()----------------");
		List<String> classes = new ArrayList<String>();
		List<Person> roll = null;
		Query q = null;
		try {
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					logger.debug("school id -- > " + schoolid);
					if (roll.get(0).getPersonRole().equals("Teacher")) {
						logger.debug("inside teacher -- ");
						q = entitymanager.createQuery("from Teacher where school_ID=? and person_ID=? and status=?");
						q.setParameter(1, schoolid);
						q.setParameter(2, personID);
						q.setParameter(3, "Active");
						List<Teacher> teacher = (List<Teacher>) q.getResultList();
						if (teacher.size() > 0) {
							q = entitymanager.createQuery("from TeacherClass where teacher_ID=? and status=?");
							q.setParameter(1, teacher.get(0).getTeacher_ID());
							q.setParameter(2, "Active");
							List<TeacherClass> teacherclass = (List<TeacherClass>) q.getResultList();
							logger.debug("teacher class size -- > " + teacherclass.size());
							if (teacherclass.size() > 0) {
								for (int i = 0; i < teacherclass.size(); i++) {
									String classs = teacherclass.get(i).getClazz().getClassName()+"/"+teacherclass.get(i).getClazz().getClassSection();
									classes.add(classs);
								}
							}

							HashSet<String> hashclass = new HashSet<String>(classes);
							classes.clear();
							classes.addAll(hashclass);
						}
					} else {
						q = entitymanager.createQuery("from Class where school_ID=? and status='Active'");
						q.setParameter(1, schoolid);
						List<com.stumosys.shared.Class> classs = (List<com.stumosys.shared.Class>) q.getResultList();
						if (classs.size() > 0) {
							for (int i = 0; i < classs.size(); i++) {
								String classname = classs.get(i).getClassName()+"/"+classs.get(i).getClassSection();
								classes.add(classname);
							}
							HashSet<String> hashclass = new HashSet<String>(classes);
							classes.clear();
							classes.addAll(hashclass);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception" +e.getMessage());
		}
		return classes;
	}

	@SuppressWarnings("unchecked")
	public String subjectValues(TimeTableDataBean timeTableDataBean, String personID) {
		logger.info("-----------Inside subjectValues method()----------------");
		Query v = null;
		List<Person> roll = null;
		int classid = 0;
		List<String> classsubjet = new ArrayList<String>();
		try {
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					v = entitymanager.createQuery("from Class where className=? and school_ID=? and status=?");
					v.setParameter(1, timeTableDataBean.getExamClass().split("/")[0]);
					v.setParameter(2, roll.get(0).getSchool().getSchool_ID());
					v.setParameter(3, "Active");
					List<Class> clasz = (List<Class>) v.getResultList();
					if (clasz.size() > 0) {
						classid = clasz.get(0).getClass_ID();
						timeTableDataBean.setClass_id(classid);
						v = null;
						v = entitymanager.createQuery("from ClassSubject where class_ID=? and status=?");
						v.setParameter(1, classid);
						v.setParameter(2, "Active");
						List<ClassSubject> classsubject = (List<ClassSubject>) v.getResultList();
						if (classsubject.size() > 0) {
							for (int i = 0; i < classsubject.size(); i++) {
								String subject = classsubject.get(i).getSubject().getSujectName();
								classsubjet.add(subject);
							}
							timeTableDataBean.setSublist(classsubjet);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public String changeSubjectCode(String personID, TimeTableDataBean timeTableDataBean) {
		logger.info("-----------Inside changeSubjectCode method()----------------");
		List<Person> roll = null;
		Query v = null;
		int subjectid = 0;
		try {
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					v = entitymanager.createQuery("from Subject where sujectName=? and school_ID=? and status=?");
					v.setParameter(1, timeTableDataBean.getExamSubject());
					v.setParameter(2, roll.get(0).getSchool().getSchool_ID());
					v.setParameter(3, "Active");
					List<Subject> subject = (List<Subject>) v.getResultList();
					if (subject.size() > 0) {
						for (int i = 0; i < subject.size(); i++) {
							subjectid = subject.get(i).getSubject_ID();
							v = entitymanager
									.createQuery("from ClassSubject where class_ID=? and subject_ID=? and status=?");
							v.setParameter(1, timeTableDataBean.getClass_id());
							v.setParameter(2, subjectid);
							v.setParameter(3, "Active");
							List<ClassSubject> classsubject = (List<ClassSubject>) v.getResultList();
							if (classsubject.size() > 0) {
								timeTableDataBean
										.setExamSubjectCodes(classsubject.get(0).getSubject().getSubjectCode());
							}
						}
						logger.debug("subject code -- > " + timeTableDataBean.getExamSubjectCodes());
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
			e.printStackTrace();
		}
		return "";
	}

	public List<Person> getperson(String personID) {
		logger.info("-----------Inside getperson method()----------------");
		//logger.debug("peron id -- > " + personID);
		List<Person> per = null;
		if (personID != null) {
			per = getRollType(personID);
		}
		return per;
	}

	@SuppressWarnings("unchecked")
	public String timeTableICheck(String personID, TimeTableDataBean timeTableDataBean) {
		logger.info("-----------Inside timeTableICheck method()----------------");
		Query v = null;
		String status = "no";
		List<Person> per = null;
		try {
			if (personID != null) {
				per = getRollType(personID);
				if (per.size() > 0) {
					v = entitymanager.createQuery(
							"from ExamTable where examTitle=? and class_name=? and school_ID=? and status='Insert'");
					v.setParameter(1, timeTableDataBean.getTablelist().get(0).getExamTitle());
					v.setParameter(2, timeTableDataBean.getTablelist().get(0).getExamClass());
					v.setParameter(3, per.get(0).getSchool().getSchool_ID());
					logger.debug("class - " + timeTableDataBean.getTablelist().get(0).getExamClass() + " title - "
							+ timeTableDataBean.getTablelist().get(0).getExamTitle() + " school id - "
							+ per.get(0).getSchool().getSchool_ID());
					List<ExamTable> examTable = (List<ExamTable>) v.getResultList();
					logger.debug("table size - " + examTable.size());
					if (examTable.size() > 0) {
						int count = 0;
						int count1 = 0;
						for (ExamTable exTable : examTable) {
							for (int i = 0; i < timeTableDataBean.getTablelist().size(); i++) {
								String starthour = "";
								String startmin = "";
								String starttime = "";
								starthour = "" + timeTableDataBean.getTablelist().get(i).getExamStartTime().getHours();
								startmin = "" + timeTableDataBean.getTablelist().get(i).getExamStartTime().getMinutes();
								if (starthour.length() == 1 && startmin.length() == 1) {
									starttime = "0" + starthour + ":" + "0" + startmin;
								} else if (starthour.length() == 1 && startmin.length() == 2) {
									starttime = "0" + starthour + ":" + startmin;
								} else if (starthour.length() == 2 && startmin.length() == 2) {
									starttime = starthour + ":" + startmin;
								} else if (starthour.length() == 2 && startmin.length() == 1) {
									starttime = starthour + ":" + "0" + startmin;
								}
								v = null;
								v = entitymanager.createQuery(
										"from TimeTable where exam_table_ID=? and examDate=? and ? between startTime and endTime and status=?");
								v.setParameter(1, exTable.getExam_table_ID());
								v.setParameter(2, timeTableDataBean.getTablelist().get(i).getExamStartDate());
								v.setParameter(3, starttime);
								v.setParameter(4, "Active");
								logger.debug("time - " + starttime + " id - "
										+ examTable.get(0).getExam_table_ID() + " date - "
										+ timeTableDataBean.getTablelist().get(i).getExamStartDate());
								List<TimeTable> timeTable = (List<TimeTable>) v.getResultList();
								if (timeTable.size() > 0) {
									count++;
								} else {
									v = null;
									v = entitymanager.createQuery(
											"from TimeTable where exam_table_ID=? and examDate=? and subjectCode=? and status=?");
									v.setParameter(1, exTable.getExam_table_ID());
									v.setParameter(2, timeTableDataBean.getTablelist().get(i).getExamStartDate());
									v.setParameter(3, timeTableDataBean.getTablelist().get(i).getExamSubjectCode());
									v.setParameter(4, "Active");
									List<TimeTable> timeTables = (List<TimeTable>) v.getResultList();
									if (timeTables.size() > 0) {
										count1++;
									}
								}
							}
						}
						logger.debug("count -- " + count + " 1 - " + count1);
						if (count > 0) {
							status = "exsist";
						} else {
							status = "no";
						}
						if (count1 > 0) {
							status = "subject";
						}
					} else {
						status = "no";
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return status;
	}

	

	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	public List<ExamTable> examTableInsert(int schoolId, TimeTableDataBean timeTableDataBean, List<TimeTableDataBean> timesBean) {
		logger.info("-----------Inside examTableInsert method()----------------");
		List<ExamTable> examtable = null;
		List<ExamTable> examlist =null;
		int examtableid=0;Query v = null;
		Calendar now = Calendar.getInstance(); int year = now.get(Calendar.YEAR);		
		try {
			v = entitymanager.createQuery("from ExamTable where examTitle=? and startDate=? and class_name=? and status=?");
			v.setParameter(1, timeTableDataBean.getExamTitle());
			v.setParameter(2, timeTableDataBean.getExamStartDate());
			v.setParameter(3, timeTableDataBean.getExamClass());
			v.setParameter(4, "Insert");
			examlist = (List<ExamTable>) v.getResultList();
			if(examlist.size()>0){
				logger.info("----time table Exist-------");
			}
			else{
			ExamTable exam = new ExamTable();
			exam.setClass_name(timeTableDataBean.getExamClass());
			exam.setStatus("Insert"); // Draft --> Approved -- > Active --> DeActive --> Deleted --> Removed 
			// Insert --> ??? 
			exam.setSchool(entitymanager.find(School.class, schoolId));
			exam.setExamTitle(timeTableDataBean.getExamTitle());
			exam.setTotalTime(timeTableDataBean.getExamtTotalTime());
			exam.setStartDate(timeTableDataBean.getExamStartDate());
			entitymanager.persist(exam);
			
			v = entitymanager.createQuery("from ExamTable where examTitle=? and startDate=? and class_name=? and status=?");
			v.setParameter(1, timeTableDataBean.getExamTitle());
			v.setParameter(2, timeTableDataBean.getExamStartDate());
			v.setParameter(3, timeTableDataBean.getExamClass());
			v.setParameter(4, "Insert");
			examtable = (List<ExamTable>) v.getResultList();
			logger.debug("--examtable size---->"+examtable.size());
			if(examtable.size()>0){
				examtableid=examtable.get(0).getExam_table_ID();
				logger.debug("collector size"+timesBean.size());
				logger.debug("exam table id"+examtableid);
				logger.debug("collector size"+timesBean.size());
				for (int i = 0; i < timesBean.size(); i++) {
					GradeDetail gradedetail=new GradeDetail();
					gradedetail.setExamTableId(entitymanager.find(ExamTable.class, examtableid));
					gradedetail.setFromMark(timesBean.get(i).getExamFromMark());
					if(timesBean.get(i).getExamToMark().equalsIgnoreCase("100")){
						gradedetail.setToMark("99");
					}else{
						gradedetail.setToMark(timesBean.get(i).getExamToMark());
					}
					gradedetail.setGrade(timesBean.get(i).getExamGrade());
					gradedetail.setYear(String.valueOf(year));
					gradedetail.setExamResult(timesBean.get(i).getExamResult());
					entitymanager.persist(gradedetail);
					entitymanager.flush();
					entitymanager.clear();
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return examtable;
	}
	
	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	public String timeTableInsert(int examId, TimeTableDataBean timeTableDataBean, int i, int schoolId) {
		logger.info("-----------Inside timeTableInsert method()----------------");
		String status = "Fail";
		List<String> maillist = new ArrayList<String>();
		List<String> phonenos=new ArrayList<String>();
		try {
			String starthour = "";
			String startmin = "";
			String starttime = "";
			String endhour = "";
			String endmin = "";
			String endtime = "";
			starthour = "" + timeTableDataBean.getClassList().get(i).getExamStartTime().getHours();
			startmin = "" + timeTableDataBean.getClassList().get(i).getExamStartTime().getMinutes();
			if (starthour.length() == 1 && startmin.length() == 1) {
				starttime = "0" + starthour + ":" + "0" + startmin;
			} else if (starthour.length() == 1 && startmin.length() == 2) {
				starttime = "0" + starthour + ":" + startmin;
			} else if (starthour.length() == 2 && startmin.length() == 2) {
				starttime = starthour + ":" + startmin;
			} else if (starthour.length() == 2 && startmin.length() == 1) {
				starttime = starthour + ":" + "0" + startmin;
			}
			endhour = "" + timeTableDataBean.getClassList().get(i).getExamEndTime().getHours();
			endmin = "" + timeTableDataBean.getClassList().get(i).getExamEndTime().getMinutes();
			if (endhour.length() == 1 && endmin.length() == 1) {
				endtime = "0" + endhour + ":" + "0" + endmin;
			} else if (endhour.length() == 1 && endmin.length() == 2) {
				endtime = "0" + endhour + ":" + endmin;
			} else if (endhour.length() == 2 && endmin.length() == 2) {
				endtime = endhour + ":" + endmin;
			} else if (endhour.length() == 2 && endmin.length() == 1) {
				endtime = endhour + ":" + "0" + endmin;
			}
			TimeTable table = new TimeTable();
			table.setExamTable(entitymanager.find(ExamTable.class, examId));
			table.setExamDate(timeTableDataBean.getClassList().get(i).getExamStartDate());
			table.setExamDay(timeTableDataBean.getClassList().get(i).getExamDay());
			table.setSubject(timeTableDataBean.getClassList().get(i).getExamSubject());
			table.setSubjectCode(timeTableDataBean.getClassList().get(i).getExamSubjectCode());
			table.setRoomNo(timeTableDataBean.getClassList().get(i).getExamRoomNo());
			table.setStartTime(starttime);
			table.setEndTime(endtime);
			table.setExamShift(timeTableDataBean.getClassList().get(i).getExamShift());
			table.setStatus("Active");
			entitymanager.persist(table);
			Query v = null;
			if(schoolId==35){
				v = entitymanager.createQuery("from Class where className=? and status=? and school_ID=?");
				v.setParameter(1, timeTableDataBean.getExamClass());
				v.setParameter(2, "Active");
				v.setParameter(3, schoolId);
			}else{
				v = entitymanager.createQuery("from Class where className=? and classSection=? and status=? and school_ID=?");
				v.setParameter(1, timeTableDataBean.getExamClass().split("/")[0]);
				v.setParameter(2, timeTableDataBean.getExamClass().split("/")[1]);
				v.setParameter(3, "Active");
				v.setParameter(4, schoolId);
			}
			List<Class> classes = (List<Class>) v.getResultList();
			if (classes.size() > 0) {
				for (int j = 0; j < classes.size(); j++) {
					v = null;
					v = entitymanager.createQuery("from StudentClass where class_ID=? and status=?");
					v.setParameter(1, classes.get(j).getClass_ID());
					v.setParameter(2, "Active");
					List<StudentClass> studentclass = (List<StudentClass>) v.getResultList();
					if (studentclass.size() > 0) {
						for (int j1 = 0; j1 < studentclass.size(); j1++) {							
							v = entitymanager.createQuery("from StudentParent where student_ID=?");
							v.setParameter(1, studentclass.get(j1).getStudent().getStudent_ID());
							List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();
							if (studentParent.size() > 0) {
								maillist.add(studentParent.get(0).getParent().getParentDetail().getEmaiId());
								phonenos.add(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
							}
						}

					}
					v = entitymanager.createQuery("from TeacherClass where class_ID=? and status=?");
					v.setParameter(1, classes.get(j).getClass_ID());
					v.setParameter(2, "Active");
					List<TeacherClass> teacherClass = (List<TeacherClass>) v.getResultList();
					if (teacherClass.size() > 0) {
						for (TeacherClass teacher : teacherClass) {
							v = entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
							v.setParameter(1, teacher.getTeacher().getTeacher_ID());
							List<TeacherDetail> teacherDetail = (List<TeacherDetail>) v.getResultList();
							if (teacherDetail.size() > 0) {
								maillist.add(teacherDetail.get(0).getEmailId());
								phonenos.add(teacherDetail.get(0).getPhoneNumber());
							}
						}
					}
					timeTableDataBean.setMails(maillist);
					timeTableDataBean.setPhonenos(phonenos);
				}
			}
			status = "Success";
			logger.debug("mail size --- " + timeTableDataBean.getMails().size());
		} catch (Exception e) {
			logger.warn("Exception", e);
			e.printStackTrace();
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	public String examCheck(TimeTableDataBean timeTableDataBean, String personID) {
		logger.info("-----------Inside examCheck method()----------------");
		List<Person> roll = null;
		String status = "";
		Query v = null;
		try {
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					v = entitymanager.createQuery(
							"from ExamTable where school_ID=? and class_name=? and examTitle=? and status=?");
					v.setParameter(1, roll.get(0).getSchool().getSchool_ID());
					v.setParameter(2, timeTableDataBean.getExamClass());
					v.setParameter(3, timeTableDataBean.getExamTitle());
					v.setParameter(4, "Insert");
					List<ExamTable> examtable = (List<ExamTable>) v.getResultList();
					if (examtable.size() > 0) {
						status = "Exist";
					} else {
						status = "New";
					}
					logger.debug("status - > " + status);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	public String timeTableView(TimeTableDataBean timeTableDataBean, String personID) {
		logger.info("-----------Inside timeTableView method()----------------");
		List<TimeTableDataBean> timetabledata = new ArrayList<TimeTableDataBean>();
		Query v = null;
		try {
			if (personID != null) {
				v = entitymanager.createQuery("from TimeTable where exam_table_ID=?");
				v.setParameter(1, timeTableDataBean.getExamtableId());
				List<TimeTable> timetable = (List<TimeTable>) v.getResultList();
				if (timetable.size() > 0) {
					for (int i = 0; i < timetable.size(); i++) {
						TimeTableDataBean tablelist = new TimeTableDataBean();
						tablelist.setExamStartDate(timetable.get(i).getExamDate());
						tablelist.setExamDay(timetable.get(i).getExamDay());
						tablelist.setExamSubject(timetable.get(i).getSubject());
						tablelist.setExamSubjectCode(timetable.get(i).getSubjectCode());
						tablelist.setExamRoomNo(timetable.get(i).getRoomNo());
						tablelist.setExamShift(timetable.get(i).getExamShift());
						tablelist.setStime(timetable.get(i).getStartTime());
						tablelist.setEtime(timetable.get(i).getEndTime());
						timetabledata.add(tablelist);
						timeTableDataBean.setTablelist(timetabledata);
					}
					logger.debug("table list size -- > " + timeTableDataBean.getTablelist().size());
					
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public List<String> getexamList(String personID, TimeTableDataBean timeTableDataBean) {
		logger.info("-----------Inside getexamList method()----------------");
		List<String> examlist = new ArrayList<String>();
		List<Person> person = null;
		Query v = null;
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					logger.debug("school id -- > " + person.get(0).getSchool().getSchool_ID());
					if (person.get(0).getPersonRole().equals("Parent")) {
						String students=timeTableDataBean.getExamClass();
						String studentid=students.substring(students.lastIndexOf("/")+1);
						v = entitymanager.createQuery("from Student where rollNumber=? and status=?");
						v.setParameter(1, studentid);
						v.setParameter(2, "Active");
						List<Student> student = (List<Student>) v.getResultList();
						if (student.size() > 0) {
							v = null;
							v = entitymanager.createQuery("from StudentClass where student_ID=? and status=?");
							v.setParameter(1, student.get(0).getStudent_ID());
							v.setParameter(2, "Active");
							List<StudentClass> studentclass = (List<StudentClass>) v.getResultList();
							if (studentclass.size() > 0) {
								v = null;
								v = entitymanager.createQuery("from ExamTable where class_name=? and status=?");
								v.setParameter(1, studentclass.get(0).getClazz().getClassName()+"/"+studentclass.get(0).getClazz().getClassSection());
								v.setParameter(2, "Insert");
								List<ExamTable> examtable = (List<ExamTable>) v.getResultList();
								if (examtable.size() > 0) {
									for (int i = 0; i < examtable.size(); i++) {
										examlist.add(examtable.get(i).getExamTitle());
									}
									HashSet<String> list = new HashSet<String>(examlist);
									examlist.clear();
									examlist.addAll(list);
								}
							}
						}
					} else {
						v = entitymanager.createQuery("from ExamTable where class_name=?  and school_ID=? and status=?");
						v.setParameter(1, timeTableDataBean.getExamClass());
						v.setParameter(2, person.get(0).getSchool().getSchool_ID());
						v.setParameter(3, "Insert");
						List<ExamTable> examtable = (List<ExamTable>) v.getResultList();
						if (examtable.size() > 0) {
							for (int i = 0; i < examtable.size(); i++) {
								examlist.add(examtable.get(i).getExamTitle());
							}
							HashSet<String> list = new HashSet<String>(examlist);
							examlist.clear();
							examlist.addAll(list);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return examlist;
	}

	@SuppressWarnings("unchecked")
	public String examRecords(TimeTableDataBean timeTableDataBean, String personID) {
		logger.info("-----------Inside examRecords method()----------------");
		List<Person> person = null;
		List<TimeTableDataBean> timetabledata = new ArrayList<TimeTableDataBean>();
		List<ExamTable> examtable=null;
		Query v = null;
		try {
			examtable=new ArrayList<ExamTable>();
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					if (person.get(0).getPersonRole().equals("Student")) {
						v = entitymanager.createQuery("from Student where rollNumber=? and status='Active'");
						v.setParameter(1, person.get(0).getPersonRoleNumber());
						List<Student> student = (List<Student>) v.getResultList();
						if (student.size() > 0) {
							v = null;
							v = entitymanager.createQuery("from StudentClass where student_ID=? and status=?");
							v.setParameter(1, student.get(0).getStudent_ID());
							v.setParameter(2, "Active");
							List<StudentClass> studentclass = (List<StudentClass>) v.getResultList();
							if (studentclass.size() > 0) {
								v = null;
								v = entitymanager.createQuery(
										"from ExamTable where class_name=? and school_ID=? and examTitle=? and status=?");
								v.setParameter(1, studentclass.get(0).getClazz().getClassName()+"/"+studentclass.get(0).getClazz().getClassSection());
								v.setParameter(2, person.get(0).getSchool().getSchool_ID());
								v.setParameter(3, timeTableDataBean.getExamTitle());
								v.setParameter(4, "Insert");
								examtable = (List<ExamTable>) v.getResultList();
								if (examtable.size() > 0) {
									for (ExamTable exTable : examtable) {
										v = null;
										v = entitymanager.createQuery("from TimeTable where exam_table_ID=? and status=?");
										v.setParameter(1, exTable.getExam_table_ID());
										v.setParameter(2, "Active");
										List<TimeTable> timetable = (List<TimeTable>) v.getResultList();
										if (timetable.size() > 0) {
											for (int i = 0; i < timetable.size(); i++) {
												TimeTableDataBean list = new TimeTableDataBean();
												list.setExamtTotalTime(timetable.get(0).getExamTable().getTotalTime());
												list.setExamStartDate(timetable.get(i).getExamDate());
												list.setStime(timetable.get(i).getStartTime());
												list.setEtime(timetable.get(i).getEndTime());
												list.setExamSubject(timetable.get(i).getSubject());
												list.setExamSubjectCode(timetable.get(i).getSubjectCode());
												list.setExamRoomNo(timetable.get(i).getRoomNo());
												list.setExamShift(timetable.get(i).getExamShift());
												list.setExamDay(timetable.get(i).getExamDay());
												list.setExamTitle(timetable.get(i).getExamTable().getExamTitle());
												timetabledata.add(list);
												timeTableDataBean.setTablelist(timetabledata);
											}
										}
									}
								}
							}
						}
					} else if (person.get(0).getPersonRole().equals("Parent")) {
						String students=timeTableDataBean.getStudentID();
						String studentid=students.substring(students.lastIndexOf("/")+1);
						v = entitymanager.createQuery("from Student where rollNumber=? and status=?");
						v.setParameter(1, studentid);
						v.setParameter(2, "Active");
						List<Student> student = (List<Student>) v.getResultList();
						if (student.size() > 0) {
							v = null;
							v = entitymanager.createQuery("from StudentClass where student_ID=? and status=?");
							v.setParameter(1, student.get(0).getStudent_ID());
							v.setParameter(2, "Active");
							List<StudentClass> studentclass = (List<StudentClass>) v.getResultList();
							if (studentclass.size() > 0) {
								v = null;
								v = entitymanager.createQuery(
										"from ExamTable where class_name=? and school_ID=? and examTitle=? and status=?");
								v.setParameter(1, studentclass.get(0).getClazz().getClassName()+"/"+studentclass.get(0).getClazz().getClassSection());
								v.setParameter(2, person.get(0).getSchool().getSchool_ID());
								v.setParameter(3, timeTableDataBean.getExamTitle());
								v.setParameter(4, "Insert");
								examtable = (List<ExamTable>) v.getResultList();
								if (examtable.size() > 0) {
									for (ExamTable exTable : examtable) {
										v = entitymanager.createQuery("from TimeTable where exam_table_ID=? and status=?");
										v.setParameter(1, exTable.getExam_table_ID());
										v.setParameter(2, "Active");
										List<TimeTable> timetable = (List<TimeTable>) v.getResultList();
										if (timetable.size() > 0) {
											for (int i = 0; i < timetable.size(); i++) {
												TimeTableDataBean record = new TimeTableDataBean();
												record.setExamtTotalTime(timetable.get(0).getExamTable().getTotalTime());
												record.setExamStartDate(timetable.get(i).getExamDate());
												record.setStime(timetable.get(i).getStartTime());
												record.setEtime(timetable.get(i).getEndTime());
												record.setExamSubject(timetable.get(i).getSubject());
												record.setExamSubjectCode(timetable.get(i).getSubjectCode());
												record.setExamRoomNo(timetable.get(i).getRoomNo());
												record.setExamShift(timetable.get(i).getExamShift());
												record.setExamDay(timetable.get(i).getExamDay());
												record.setExamTitle(timetable.get(i).getExamTable().getExamTitle());
												record.setExamClass(timetable.get(i).getExamTable().getClass_name());
												record.setExamtableId(timetable.get(i).getTime_table_ID());
												timetabledata.add(record);
												timeTableDataBean.setTablelist(timetabledata);
											}
										}
									}
								}
							}
						}
					} else {
						if(!"".equalsIgnoreCase(timeTableDataBean.getExamClass()) && !"".equalsIgnoreCase(timeTableDataBean.getExamTitle())){
							v = entitymanager.createQuery(
									"from ExamTable where class_name=? and school_ID=? and examTitle=? and status=? order by startDate desc");
							v.setParameter(1, timeTableDataBean.getExamClass());
							v.setParameter(2, person.get(0).getSchool().getSchool_ID());
							v.setParameter(3, timeTableDataBean.getExamTitle());
							v.setParameter(4, "Insert");
							examtable = (List<ExamTable>) v.getResultList();
						}else{
							v = entitymanager.createQuery("from ExamTable where school_ID=? and status=? order by startDate desc");
							v.setParameter(1, person.get(0).getSchool().getSchool_ID());
							v.setParameter(2, "Insert");
							examtable = (List<ExamTable>) v.getResultList();
						}
						if (examtable.size() > 0) {
							for (ExamTable exTable : examtable) {
								v = entitymanager.createQuery("from TimeTable where exam_table_ID=? and status=?");
								v.setParameter(1, exTable.getExam_table_ID());
								v.setParameter(2, "Active");
								List<TimeTable> timetable = (List<TimeTable>) v.getResultList();
								if (timetable.size() > 0) {
									for (int i = 0; i < timetable.size(); i++) {
										TimeTableDataBean list = new TimeTableDataBean();
										list.setExamtTotalTime(timetable.get(0).getExamTable().getTotalTime());
										list.setExamStartDate(timetable.get(i).getExamDate());
										list.setStime(timetable.get(i).getStartTime());
										list.setEtime(timetable.get(i).getEndTime());
										list.setExamSubject(timetable.get(i).getSubject());
										list.setExamSubjectCode(timetable.get(i).getSubjectCode());
										list.setExamRoomNo(timetable.get(i).getRoomNo());
										list.setExamShift(timetable.get(i).getExamShift());
										list.setExamDay(timetable.get(i).getExamDay());
										list.setExamTitle(timetable.get(i).getExamTable().getExamTitle());
										list.setExamClass(timetable.get(i).getExamTable().getClass_name());
										list.setExamtableId(timetable.get(i).getTime_table_ID());
										timetabledata.add(list);
										timeTableDataBean.setTablelist(timetabledata);
									}
								}
							}
						}
					}
					logger.debug("exam list size -- > " + timeTableDataBean.getTablelist().size());
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public String getexamRecordsList(String personID, TimeTableDataBean timeTableDataBean) {
		logger.info("-----------Inside getexamRecordsList method()----------------");
		List<Person> person = null;List<String> marklist = new ArrayList<String>();
		Query v = null;
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					if (person.get(0).getPersonRole().equals("Student")) {
						v = entitymanager.createQuery("from Student where rollNumber=? and status=?");
						v.setParameter(1, person.get(0).getPersonRoleNumber());
						v.setParameter(2, "Active");
						List<Student> student = (List<Student>) v.getResultList();
						if (student.size() > 0) {
							v = null;
							v = entitymanager.createQuery("from StudentClass where student_ID=? and status=?");
							v.setParameter(1, student.get(0).getStudent_ID());
							v.setParameter(2, "Active");
							List<StudentClass> studentclass = (List<StudentClass>) v.getResultList();
							if (studentclass.size() > 0) {
								v = null;
								v = entitymanager.createQuery(
										"from ExamTable where class_name=? and school_ID=? and examTitle=? and status=?");
								v.setParameter(1, studentclass.get(0).getClazz().getClassName()+"/"+studentclass.get(0).getClazz().getClassSection());
								v.setParameter(2, person.get(0).getSchool().getSchool_ID());
								v.setParameter(3, timeTableDataBean.getExamTitle());
								v.setParameter(4, "Insert");
								List<ExamTable> examtable = (List<ExamTable>) v.getResultList();
								if (examtable.size() > 0) {
									for (ExamTable exTable : examtable) {
										v=null;
										v=entitymanager.createQuery("from GradeDetail where exam_table_ID=? and status=?");
										v.setParameter(1, exTable.getExam_table_ID());
										v.setParameter(2, "Active");
										List<GradeDetail> gradedetailList=(List<GradeDetail>)v.getResultList();
										if(gradedetailList.size()>0){
											for (int j = 0; j < gradedetailList.size(); j++) {
												marklist.add(gradedetailList.get(j).getFromMark()
														+ "-"  +gradedetailList.get(j).getToMark()+"-"+gradedetailList.get(j).getGrade());
												timeTableDataBean.setSublist(marklist);
											}
										}
										v = entitymanager.createQuery(
												"from TimeTable where exam_table_ID=? and subject=? and examDate=? and examDay=? and status=?");
										v.setParameter(1, exTable.getExam_table_ID());
										v.setParameter(2, timeTableDataBean.getExamSubject());
										v.setParameter(3, timeTableDataBean.getExamStartDate());
										v.setParameter(4, timeTableDataBean.getExamDay());
										v.setParameter(5, "Active");
										List<TimeTable> timetable = (List<TimeTable>) v.getResultList();
										if (timetable.size() > 0) {
											timeTableDataBean
													.setExamtTotalTime(timetable.get(0).getExamTable().getTotalTime());
											timeTableDataBean.setExamStartDate(timetable.get(0).getExamDate());
											timeTableDataBean.setStime(timetable.get(0).getStartTime());
											timeTableDataBean.setEtime(timetable.get(0).getEndTime());
											timeTableDataBean.setExamSubject(timetable.get(0).getSubject());
											timeTableDataBean.setExamSubjectCode(timetable.get(0).getSubjectCode());
											timeTableDataBean.setExamRoomNo(timetable.get(0).getRoomNo());
											timeTableDataBean.setExamShift(timetable.get(0).getExamShift());
											timeTableDataBean.setExamDay(timetable.get(0).getExamDay());
											timeTableDataBean
													.setExamTitle(timetable.get(0).getExamTable().getExamTitle());
											timeTableDataBean
													.setExamClass(timetable.get(0).getExamTable().getClass_name());
										}
									}
								}
							}
						}
					} else if (person.get(0).getPersonRole().equals("Parent")) {
						String students=timeTableDataBean.getStudentID();
						String studentid=students.substring(students.lastIndexOf("/")+1);
						v = entitymanager.createQuery("from Student where rollNumber=? and status=?");
						v.setParameter(1, studentid);
						v.setParameter(2, "Active");
						List<Student> student = (List<Student>) v.getResultList();
						if (student.size() > 0) {
							v = null;
							v = entitymanager.createQuery("from StudentClass where student_ID=? and status=?");
							v.setParameter(1, student.get(0).getStudent_ID());
							v.setParameter(2, "Active");
							List<StudentClass> studentclass = (List<StudentClass>) v.getResultList();
							if (studentclass.size() > 0) {
								v = null;
								v = entitymanager.createQuery(
										"from ExamTable where class_name=? and school_ID=? and examTitle=? and status=?");
								v.setParameter(1, studentclass.get(0).getClazz().getClassName()+"/"+studentclass.get(0).getClazz().getClassSection());
								v.setParameter(2, person.get(0).getSchool().getSchool_ID());
								v.setParameter(3, timeTableDataBean.getExamTitle());
								v.setParameter(4, "Insert");
								List<ExamTable> examtable = (List<ExamTable>) v.getResultList();
								if (examtable.size() > 0) {
									for (ExamTable exTable : examtable) {
										v=null;
										v=entitymanager.createQuery("from GradeDetail where exam_table_ID=? and status=?");
										v.setParameter(1, exTable.getExam_table_ID());
										v.setParameter(2, "Active");
										List<GradeDetail> gradedetailList=(List<GradeDetail>)v.getResultList();
										if(gradedetailList.size()>0){
											for (int j = 0; j < gradedetailList.size(); j++) {
												marklist.add(gradedetailList.get(j).getFromMark()
														+ "-"  +gradedetailList.get(j).getToMark()+"-"+gradedetailList.get(j).getGrade());
												timeTableDataBean.setSublist(marklist);
											}
										}
										v = entitymanager.createQuery(
												"from TimeTable where exam_table_ID=? and subject=? and examDate=? and examDay=? and status=?");
										v.setParameter(1, exTable.getExam_table_ID());
										v.setParameter(2, timeTableDataBean.getExamSubject());
										v.setParameter(3, timeTableDataBean.getExamStartDate());
										v.setParameter(4, timeTableDataBean.getExamDay());
										v.setParameter(5, "Active");
										List<TimeTable> timetable = (List<TimeTable>) v.getResultList();
										if (timetable.size() > 0) {
											timeTableDataBean
													.setExamtTotalTime(timetable.get(0).getExamTable().getTotalTime());
											timeTableDataBean.setExamStartDate(timetable.get(0).getExamDate());
											timeTableDataBean.setStime(timetable.get(0).getStartTime());
											timeTableDataBean.setEtime(timetable.get(0).getEndTime());
											timeTableDataBean.setExamSubject(timetable.get(0).getSubject());
											timeTableDataBean.setExamSubjectCode(timetable.get(0).getSubjectCode());
											timeTableDataBean.setExamRoomNo(timetable.get(0).getRoomNo());
											timeTableDataBean.setExamShift(timetable.get(0).getExamShift());
											timeTableDataBean.setExamDay(timetable.get(0).getExamDay());
											timeTableDataBean
													.setExamTitle(timetable.get(0).getExamTable().getExamTitle());
											timeTableDataBean
													.setExamClass(timetable.get(0).getExamTable().getClass_name());
										}
									}
								}
							}
						}
					} else {
						v = entitymanager.createQuery(
								"from ExamTable where class_name=? and school_ID=? and examTitle=? and status=?");
						v.setParameter(1, timeTableDataBean.getExamClass());
						v.setParameter(2, person.get(0).getSchool().getSchool_ID());
						v.setParameter(3, timeTableDataBean.getExamTitle());
						v.setParameter(4, "Insert");
						List<ExamTable> examtable = (List<ExamTable>) v.getResultList();
						if (examtable.size() > 0) {
							for (ExamTable exTable : examtable) {
								v=null;
								v=entitymanager.createQuery("from GradeDetail where exam_table_ID=? and status=?");
								v.setParameter(1, exTable.getExam_table_ID());
								v.setParameter(2, "Active");
								List<GradeDetail> gradedetailList=(List<GradeDetail>)v.getResultList();
								if(gradedetailList.size()>0){
									for (int j = 0; j < gradedetailList.size(); j++) {
										marklist.add(gradedetailList.get(j).getFromMark()
												+ "-"  +gradedetailList.get(j).getToMark()+"-"+gradedetailList.get(j).getGrade());
										timeTableDataBean.setSublist(marklist);
									}
								}
								v = entitymanager.createQuery(
										"from TimeTable where exam_table_ID=? and subject=? and examDate=? and examDay=? and status=?");
								v.setParameter(1, exTable.getExam_table_ID());
								v.setParameter(2, timeTableDataBean.getExamSubject());
								v.setParameter(3, timeTableDataBean.getExamStartDate());
								v.setParameter(4, timeTableDataBean.getExamDay());
								v.setParameter(5, "Active");
								List<TimeTable> timetable = (List<TimeTable>) v.getResultList();
								if (timetable.size() > 0) {
									timeTableDataBean.setExamtTotalTime(timetable.get(0).getExamTable().getTotalTime());
									timeTableDataBean.setExamStartDate(timetable.get(0).getExamDate());
									timeTableDataBean.setStime(timetable.get(0).getStartTime());
									timeTableDataBean.setEtime(timetable.get(0).getEndTime());
									timeTableDataBean.setExamSubject(timetable.get(0).getSubject());
									timeTableDataBean.setExamSubjectCode(timetable.get(0).getSubjectCode());
									timeTableDataBean.setExamRoomNo(timetable.get(0).getRoomNo());
									timeTableDataBean.setExamShift(timetable.get(0).getExamShift());
									timeTableDataBean.setExamDay(timetable.get(0).getExamDay());
									timeTableDataBean.setExamTitle(timetable.get(0).getExamTable().getExamTitle());
									timeTableDataBean.setExamClass(timetable.get(0).getExamTable().getClass_name());
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public String getexamEditList(String personID, TimeTableDataBean timeTableDataBean) {
		logger.info("-----------Inside getexamEditList method()----------------");
		List<Person> person = null;
		Query v = null;
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					v = entitymanager.createQuery(
							"from ExamTable where class_name=? and school_ID=? and examTitle=? and status=?");
					v.setParameter(1, timeTableDataBean.getExamClass());
					v.setParameter(2, person.get(0).getSchool().getSchool_ID());
					v.setParameter(3, timeTableDataBean.getExamTitle());
					v.setParameter(4, "Insert");
					List<ExamTable> examtable = (List<ExamTable>) v.getResultList();
					logger.debug("exam table size - " + examtable.size());
					if (examtable.size() > 0) {
						for (ExamTable exTable : examtable) {
							v = entitymanager.createQuery(
									"from TimeTable where exam_table_ID=? and subject=? and examDate=? and examDay=? and status=?");
							v.setParameter(1, exTable.getExam_table_ID());
							v.setParameter(2, timeTableDataBean.getExamSubject());
							v.setParameter(3, timeTableDataBean.getExamStartDate());
							v.setParameter(4, timeTableDataBean.getExamDay());
							v.setParameter(5, "Active");
							List<TimeTable> timetable = (List<TimeTable>) v.getResultList();
							logger.debug("time table size - " + timetable.size());
							if (timetable.size() > 0) {
								timeTableDataBean.setExamtTotalTime(timetable.get(0).getExamTable().getTotalTime());
								timeTableDataBean.setExamStartDate(timetable.get(0).getExamDate());
								DateFormat df = new SimpleDateFormat("HH:mm");
								Date startDate = df.parse(timetable.get(0).getStartTime());
								Date startDate1 = df.parse(timetable.get(0).getEndTime());
								timeTableDataBean.setExamStartTime(startDate);
								timeTableDataBean.setExamEndTime(startDate1);
								timeTableDataBean.setExamSubject(timetable.get(0).getSubject());
								timeTableDataBean.setExamSubjectCode(timetable.get(0).getSubjectCode());
								timeTableDataBean.setExamRoomNo(timetable.get(0).getRoomNo());
								timeTableDataBean.setExamShift(timetable.get(0).getExamShift());
								timeTableDataBean.setExamDay(timetable.get(0).getExamDay());
								timeTableDataBean.setExamTitle(timetable.get(0).getExamTable().getExamTitle());
								timeTableDataBean.setExamClass(timetable.get(0).getExamTable().getClass_name());
								timeTableDataBean.setExamtableId(timetable.get(0).getTime_table_ID());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return "";
	}

	/*@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	public String getexamupdate(String personID, TimeTableDataBean timeTableDataBean) {
		logger.info("-----------Inside getexamupdate method()----------------");
		List<Person> person = null;
		String status = "no";
		List<String> maillist = new ArrayList<String>();
		List<String> phonenos = new ArrayList<String>();
		Query v = null;
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					timeTableDataBean.setSchoolName(person.get(0).getSchool().getName());
					String starthour = "";
					String startmin = "";
					String starttime = "";
					String endhour = "";
					String endmin = "";
					String endtime = "";
					starthour = "" + timeTableDataBean.getExamStartTime().getHours();
					startmin = "" + timeTableDataBean.getExamStartTime().getMinutes();
					if (starthour.length() == 1 && startmin.length() == 1) {
						starttime = "0" + starthour + ":" + "0" + startmin;
					} else if (starthour.length() == 1 && startmin.length() == 2) {
						starttime = "0" + starthour + ":" + startmin;
					} else if (starthour.length() == 2 && startmin.length() == 2) {
						starttime = starthour + ":" + startmin;
					} else if (starthour.length() == 2 && startmin.length() == 1) {
						starttime = starthour + ":" + "0" + startmin;
					}
					endhour = "" + timeTableDataBean.getExamEndTime().getHours();
					endmin = "" + timeTableDataBean.getExamEndTime().getMinutes();
					if (endhour.length() == 1 && endmin.length() == 1) {
						endtime = "0" + endhour + ":" + "0" + endmin;
					} else if (endhour.length() == 1 && endmin.length() == 2) {
						endtime = "0" + endhour + ":" + endmin;
					} else if (endhour.length() == 2 && endmin.length() == 2) {
						endtime = endhour + ":" + endmin;
					} else if (endhour.length() == 2 && endmin.length() == 1) {
						endtime = endhour + ":" + "0" + endmin;
					}
					v = entitymanager.createQuery("from TimeTable where time_table_ID=? and subjectCode=?");
					v.setParameter(1, timeTableDataBean.getExamtableId());
					v.setParameter(2, timeTableDataBean.getExamSubjectCode());
					List<TimeTable> timetables = (List<TimeTable>) v.getResultList();
					if (timetables.size() > 0) {
						v = entitymanager.createQuery(
								"from TimeTable where time_table_ID=? and ? between startTime and endTime");
						v.setParameter(1, timeTableDataBean.getExamtableId());
						v.setParameter(2, starttime);
						logger.debug("start time - " + starttime);
						List<TimeTable> timetablezz = (List<TimeTable>) v.getResultList();
						if (timetablezz.size() > 0) {
							status = "no";
							TimeTable update = entitymanager.find(TimeTable.class, timeTableDataBean.getExamtableId());
							update.setExamDate(timeTableDataBean.getExamStartDate());
							update.setExamDay(timeTableDataBean.getExamDay());
							update.setSubject(timeTableDataBean.getExamSubject());
							update.setSubjectCode(timeTableDataBean.getExamSubjectCode());
							update.setRoomNo(timeTableDataBean.getExamRoomNo());
							update.setStartTime(starttime);
							update.setEndTime(endtime);
							update.setExamShift(timeTableDataBean.getExamShift());
							entitymanager.merge(update);
							v = null;
							v = entitymanager.createQuery("from Class where className=? and status=?");
							v.setParameter(1, timeTableDataBean.getExamClass());
							v.setParameter(2, "Active");
							List<Class> classes = (List<Class>) v.getResultList();
							if (classes.size() > 0) {
								for (int j = 0; j < classes.size(); j++) {
									v = null;
									v = entitymanager.createQuery("from StudentClass where class_ID=? and status=?");
									v.setParameter(1, classes.get(j).getClass_ID());
									v.setParameter(2, "Active");
									List<StudentClass> studentclass = (List<StudentClass>) v.getResultList();
									if (studentclass.size() > 0) {
										for (int j1 = 0; j1 < studentclass.size(); j1++) {
											v = null;											
											v = entitymanager.createQuery("from StudentParent where student_ID=?");
											v.setParameter(1, studentclass.get(j1).getStudent().getStudent_ID());
											List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();
											if (studentParent.size() > 0) {
												maillist.add(
														studentParent.get(0).getParent().getParentDetail().getEmaiId());
												phonenos.add(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
											}
										}
									}
									v = entitymanager.createQuery("from TeacherClass where class_ID=? and status=?");
									v.setParameter(1, classes.get(j).getClass_ID());
									v.setParameter(2, "Active");
									List<TeacherClass> teacherClass = (List<TeacherClass>) v.getResultList();
									if (teacherClass.size() > 0) {
										for (TeacherClass teacher : teacherClass) {
											v = entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
											v.setParameter(1, teacher.getTeacher().getTeacher_ID());
											List<TeacherDetail> teacherDetail = (List<TeacherDetail>) v.getResultList();
											if (teacherClass.size() > 0) {
												maillist.add(teacherDetail.get(0).getEmailId());
												//phonenos.add(teacherDetail.get(0).getPhoneNumber());
												phonenos.add(teacherDetail.get(0).getPresentCode()+teacherDetail.get(0).getPhoneNumber());

											}
										}
									}
									timeTableDataBean.setMails(maillist);
									timeTableDataBean.setPhonenos(phonenos);
								}
							}
						} else {
							v = null;
							v = entitymanager.createQuery("from ExamTable where class_name=? and examTitle=?");
							v.setParameter(1, timetables.get(0).getExamTable().getClass_name());
							v.setParameter(2, timetables.get(0).getExamTable().getExamTitle());
							List<ExamTable> examtable = (List<ExamTable>) v.getResultList();
							if (examtable.size() > 0) {
								int count1 = 0;
								for (ExamTable exTable : examtable) {
									v = entitymanager.createQuery(
											"from TimeTable where exam_table_ID=? and ? between startTime and endTime");
									v.setParameter(1, exTable.getExam_table_ID());
									v.setParameter(2, starttime);
									logger.debug("start time - " + starttime);
									List<TimeTable> timetablea = (List<TimeTable>) v.getResultList();
									if (timetablea.size() > 0) {
										count1++;
									}
								}
								if (count1 > 0) {
									status = "exsist";
								}
							}
						}
					} else {
						v = entitymanager.createQuery("from TimeTable where time_table_ID=?");
						v.setParameter(1, timeTableDataBean.getExamtableId());
						List<TimeTable> timetable = (List<TimeTable>) v.getResultList();
						if (timetable.size() > 0) {
							v = null;
							v = entitymanager.createQuery("from ExamTable where class_name=? and examTitle=?");
							v.setParameter(1, timetable.get(0).getExamTable().getClass_name());
							v.setParameter(2, timetable.get(0).getExamTable().getExamTitle());
							List<ExamTable> examtable = (List<ExamTable>) v.getResultList();
							if (examtable.size() > 0) {
								int count = 0;
								int count1 = 0;
								for (ExamTable exTable : examtable) {
									v = entitymanager
											.createQuery("from TimeTable where exam_table_ID=? and subjectCode=?");
									v.setParameter(1, exTable.getExam_table_ID());
									v.setParameter(2, timeTableDataBean.getExamSubjectCode());
									List<TimeTable> timetable1 = (List<TimeTable>) v.getResultList();
									if (timetable1.size() > 0) {
										count++;
									} else {
										v = entitymanager.createQuery(
												"from TimeTable where exam_table_ID=? and ? between startTime and endTime");
										v.setParameter(1, exTable.getExam_table_ID());
										v.setParameter(2, starttime);
										List<TimeTable> timetable2 = (List<TimeTable>) v.getResultList();
										if (timetable2.size() > 0) {
											count1++;
										}
									}
								}
								if (count1 > 0) {
									status = "exsist";
								}
								if (count > 0) {
									status = "subject";
								} else {
									status = "no";
									v = entitymanager.createQuery("from TimeTable where time_table_ID=?");
									v.setParameter(1, timeTableDataBean.getExamtableId());
									List<TimeTable> timetablez = (List<TimeTable>) v.getResultList();
									if (timetable.size() > 0) {
										status = "no";
										TimeTable update = entitymanager.find(TimeTable.class,
												timeTableDataBean.getExamtableId());
										update.setExamDate(timeTableDataBean.getExamStartDate());
										update.setExamDay(timeTableDataBean.getExamDay());
										update.setSubject(timeTableDataBean.getExamSubject());
										update.setSubjectCode(timeTableDataBean.getExamSubjectCode());
										update.setRoomNo(timeTableDataBean.getExamRoomNo());
										update.setStartTime(starttime);
										update.setEndTime(endtime);
										update.setExamShift(timeTableDataBean.getExamShift());
										entitymanager.merge(update);
										v = null;
										v = entitymanager.createQuery("from Class where className=? an classSection=? and status=?");
										v.setParameter(1, timeTableDataBean.getExamClass().split("/")[0]);
										v.setParameter(2, timeTableDataBean.getExamClass().split("/")[1]);
										v.setParameter(3, "Active");
										List<Class> classes = (List<Class>) v.getResultList();
										if (classes.size() > 0) {
											for (int j = 0; j < classes.size(); j++) {
												v = null;
												v = entitymanager
														.createQuery("from StudentClass where class_ID=? and status=?");
												v.setParameter(1, classes.get(j).getClass_ID());
												v.setParameter(2, "Active");
												List<StudentClass> studentclass = (List<StudentClass>) v
														.getResultList();
												if (studentclass.size() > 0) {
													for (int j1 = 0; j1 < studentclass.size(); j1++) {
														v = null;														
														v = entitymanager
																.createQuery("from StudentParent where student_ID=?");
														v.setParameter(1,
																studentclass.get(j1).getStudent().getStudent_ID());
														List<StudentParent> studentParent = (List<StudentParent>) v
																.getResultList();
														if (studentParent.size() > 0) {
															maillist.add(studentParent.get(0).getParent()
																	.getParentDetail().getEmaiId());
															phonenos.add(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
														}
													}
												}
												v = entitymanager
														.createQuery("from TeacherClass where class_ID=? and status=?");
												v.setParameter(1, classes.get(j).getClass_ID());
												v.setParameter(2, "Active");
												List<TeacherClass> teacherClass = (List<TeacherClass>) v
														.getResultList();
												if (teacherClass.size() > 0) {
													for (TeacherClass teacher : teacherClass) {
														v = entitymanager
																.createQuery("from TeacherDetail where teacher_ID=?");
														v.setParameter(1, teacher.getTeacher().getTeacher_ID());
														List<TeacherDetail> teacherDetail = (List<TeacherDetail>) v
																.getResultList();
														if (teacherClass.size() > 0) {
															maillist.add(teacherDetail.get(0).getEmailId());
															//phonenos.add(teacherDetail.get(0).getPhoneNumber());
															phonenos.add(teacherDetail.get(0).getPresentCode()+teacherDetail.get(0).getPhoneNumber());

														}
													}
												}
												timeTableDataBean.setMails(maillist);
												timeTableDataBean.setPhonenos(phonenos);
											}
										}
									}
								}
							}
						}
					}
				}
			}
			logger.debug("status -- " + status);
		} catch (Exception e) {
			logger.warn("Exception"+ e.getMessage());
			e.printStackTrace();
		}
		return status;
	}*/
	
	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	public String getexamupdate(String personID, TimeTableDataBean timeTableDataBean) {
		String status="Fail";
		List<String> maillist = null;
		List<String> phonenos =null;
		int count = 0;
		Query q= null;
		String starthour = "";
		String startmin = "";
		String starttime = "";
		String endhour = "";
		String endmin = "";
		String endtime = "";
		int schoolID=0;
		try{
			maillist=new ArrayList<String>();
			phonenos=new ArrayList<String>();
			if(personID!=null){
					starthour = "" + timeTableDataBean.getExamStartTime().getHours();
					startmin = "" + timeTableDataBean.getExamStartTime().getMinutes();
					if (starthour.length() == 1 && startmin.length() == 1) {
						starttime = "0" + starthour + ":" + "0" + startmin;
					} else if (starthour.length() == 1 && startmin.length() == 2) {
						starttime = "0" + starthour + ":" + startmin;
					} else if (starthour.length() == 2 && startmin.length() == 2) {
						starttime = starthour + ":" + startmin;
					} else if (starthour.length() == 2 && startmin.length() == 1) {
						starttime = starthour + ":" + "0" + startmin;
					}
					endhour = "" + timeTableDataBean.getExamEndTime().getHours();
					endmin = "" + timeTableDataBean.getExamEndTime().getMinutes();
					if (endhour.length() == 1 && endmin.length() == 1) {
						endtime = "0" + endhour + ":" + "0" + endmin;
					} else if (endhour.length() == 1 && endmin.length() == 2) {
						endtime = "0" + endhour + ":" + endmin;
					} else if (endhour.length() == 2 && endmin.length() == 2) {
						endtime = endhour + ":" + endmin;
					} else if (endhour.length() == 2 && endmin.length() == 1) {
						endtime = endhour + ":" + "0" + endmin;
					}
					q = entitymanager.createQuery("from ExamTable where class_name=? and examTitle=? and school_ID=? and status=?");
					q.setParameter(1, timeTableDataBean.getExamClass());
					q.setParameter(2, timeTableDataBean.getExamTitle());
					q.setParameter(3, timeTableDataBean.getSchoolID());
					q.setParameter(4, "Insert");
					List<ExamTable> examtable = (List<ExamTable>) q.getResultList();
					if(examtable.size()>0){
						q=entitymanager.createQuery("from TimeTable where exam_table_ID=?");
						q.setParameter(1, examtable.get(0).getExam_table_ID());
						List<TimeTable> timetableList=(ArrayList<TimeTable>)q.getResultList();
						System.out.println("timetable----------"+timetableList.size());
						if(timetableList.size()>0){
							for (int i = 0; i < timetableList.size(); i++) {
									q=entitymanager.createQuery("from TimeTable where examDate=? and subject=? and roomNo=? and examDay=? and ? between startTime and endTime"); 
									q.setParameter(1, timeTableDataBean.getExamStartDate());
									q.setParameter(2, timeTableDataBean.getExamSubject());
									q.setParameter(3, timeTableDataBean.getExamRoomNo());
									q.setParameter(4, timeTableDataBean.getExamDay());
									q.setParameter(5, starttime);
									List<TimeTable> timetable=(ArrayList<TimeTable>)q.getResultList();
									System.out.println("timetable---------1------"+timetable.size());
									if(timetable.size()>0){
										count++;
									}
							}
							if(count>0){
								status="Exsist";
							}else{
								TimeTable update = entitymanager.find(TimeTable.class, timeTableDataBean.getExamtableId());
								update.setExamDate(timeTableDataBean.getExamStartDate());
								update.setExamDay(timeTableDataBean.getExamDay());
								update.setSubject(timeTableDataBean.getExamSubject());
								update.setSubjectCode(timeTableDataBean.getExamSubjectCode());
								update.setRoomNo(timeTableDataBean.getExamRoomNo());
								update.setStartTime(starttime);
								update.setEndTime(endtime);
								entitymanager.merge(update);
								status="Success";
								q = null;
								if(schoolID==35){
									q = entitymanager.createQuery("from Class where className=? and school_ID=? and status=?");
									q.setParameter(1, timeTableDataBean.getExamClass());
									q.setParameter(2, timeTableDataBean.getSchoolID());
									q.setParameter(3, "Active");
								}else{
									q = entitymanager.createQuery("from Class where className=? an classSection=? and status=? and school_ID=?");
									q.setParameter(1, timeTableDataBean.getExamClass().split("/")[0]);
									q.setParameter(2, timeTableDataBean.getExamClass().split("/")[1]);
									q.setParameter(3, "Active");
									q.setParameter(4, timeTableDataBean.getSchoolID());
								}
								List<Class> classes = (List<Class>) q.getResultList();
								if (classes.size() > 0) {
									for (int j = 0; j < classes.size(); j++) {
										q = null;
										q = entitymanager.createQuery("from StudentClass where class_ID=? and status=?");
										q.setParameter(1, classes.get(j).getClass_ID());
										q.setParameter(2, "Active");
										List<StudentClass> studentclass = (List<StudentClass>) q.getResultList();
										if (studentclass.size() > 0) {
											for (int j1 = 0; j1 < studentclass.size(); j1++) {
												q = null;														
												q = entitymanager.createQuery("from StudentParent where student_ID=?");
												q.setParameter(1,studentclass.get(j1).getStudent().getStudent_ID());
												List<StudentParent> studentParent = (List<StudentParent>) q.getResultList();
												if (studentParent.size() > 0) {
													maillist.add(studentParent.get(0).getParent().getParentDetail().getEmaiId());
													phonenos.add(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
												}
											}
										}
										q = entitymanager.createQuery("from TeacherClass where class_ID=? and status=?");
										q.setParameter(1, classes.get(j).getClass_ID());
										q.setParameter(2, "Active");
										List<TeacherClass> teacherClass = (List<TeacherClass>) q
												.getResultList();
										if (teacherClass.size() > 0) {
											for (TeacherClass teacher : teacherClass) {
												q = entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
												q.setParameter(1, teacher.getTeacher().getTeacher_ID());
												List<TeacherDetail> teacherDetail = (List<TeacherDetail>) q.getResultList();
												if (teacherClass.size() > 0) {
													maillist.add(teacherDetail.get(0).getEmailId());
													phonenos.add(teacherDetail.get(0).getPresentCode()+teacherDetail.get(0).getPhoneNumber());

												}
											}
										}
										timeTableDataBean.setMails(maillist);
										timeTableDataBean.setPhonenos(phonenos);
									}
								}
							}
							}
						}
				}
		}catch(Exception e){
			e.printStackTrace();
			logger.warn("Exception"+ e.getMessage());
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	public List<String> getClassListAtt(String personID) {
		logger.info("-----------Inside getClassListAtt method()----------------");
		List<String> classname = new ArrayList<String>();
		List<Person> person = null;
		Query q = null;
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					int schoolid = person.get(0).getSchool().getSchool_ID();
					q = entitymanager.createQuery("from Teacher where school_ID=? and person_ID=? and status=?");
					q.setParameter(1, schoolid);
					q.setParameter(2, personID);
					q.setParameter(3, "Active");
					List<Teacher> teacher = (List<Teacher>) q.getResultList();
					if (teacher.size() > 0) {
						q = entitymanager.createQuery("from TeacherClass where teacher_ID=? and status=?");
						q.setParameter(1, teacher.get(0).getTeacher_ID());
						q.setParameter(2, "Active");
						List<TeacherClass> teacherclass = (List<TeacherClass>) q.getResultList();
						logger.debug("teacher class size -- > " + teacherclass.size());
						if (teacherclass.size() > 0) {
							for (int i = 0; i < teacherclass.size(); i++) {
								String classs = teacherclass.get(i).getClazz().getClassName() + "/"
										+ teacherclass.get(i).getClazz().getClassSection();
								classname.add(classs);
							}
						}
						HashSet<String> classnme = new HashSet<String>(classname);
						classname.clear();
						classname.addAll(classnme);
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return classname;
	}

	@SuppressWarnings("unchecked")
	public String attendanceView(String personID, AttendanceDataBean attendanceDataBean) {
		logger.info("-----------Inside attendanceView method()----------------");
		List<AttendanceDataBean> attendancedata = new ArrayList<AttendanceDataBean>();
		List<Person> person = null;
		Query v = null;
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					int schoolid = person.get(0).getSchool().getSchool_ID();
					StringTokenizer st = new StringTokenizer(attendanceDataBean.getClassname());
					String className = st.nextToken("/");
					String section = attendanceDataBean.getClassname();
					String sectionName = section.substring(section.lastIndexOf("/") + 1);
					if (person.get(0).getPersonRole().equals("Teacher")) {
						v = entitymanager.createQuery("from Teacher where school_ID=? and person_ID=? and status=?");
						v.setParameter(1, schoolid);
						v.setParameter(2, personID);
						v.setParameter(3, "Active");
						List<Teacher> teacher = (List<Teacher>) v.getResultList();
						if (teacher.size() > 0) {
							v = entitymanager.createQuery(
									"from Attendance where school_ID=? and date=? and class_=? and section=?");
							v.setParameter(1, schoolid);
							v.setParameter(2, attendanceDataBean.getDate());
							v.setParameter(3, className);
							v.setParameter(4, sectionName);
							List<Attendance> attendance = (List<Attendance>) v.getResultList();
							if (attendance.size() > 0) {
								v = null;
								v = entitymanager.createQuery(
										"from Attendanceclass where attendance_ID=? and Teacher_ID=? and period=?");
								v.setParameter(1, attendance.get(0).getAttendance_ID());
								v.setParameter(2, teacher.get(0).getTeacher_ID());
								v.setParameter(3, attendanceDataBean.getPeriod().substring(7, 8));
								List<Attendanceclass> attendanceclass = (List<Attendanceclass>) v.getResultList();
								if (attendanceclass.size() > 0) {
									int i1 = 1;
									for (int i = 0; i < attendanceclass.size(); i++) {
										AttendanceDataBean list = new AttendanceDataBean();
										list.setStudentName(attendanceclass.get(i).getStudentName());
										list.setStudentID(attendanceclass.get(i).getStudent_ID());
										list.setStatus(attendanceclass.get(i).getStatus());
										list.setPercentage(attendanceclass.get(i).getPercentage());
										list.setDate(attendance.get(0).getDate());
										list.setTime1(attendanceclass.get(i).getTime().getHours() + ":"
												+ attendanceclass.get(i).getTime().getMinutes());
										list.setSno(i1);
										attendancedata.add(list);
										attendanceDataBean.setStudentList(attendancedata);
										i1++;
									}
									attendanceDataBean.setFlag(true);
								}
							} else {
								attendanceDataBean.setStudentList(attendancedata);
							}
						}
						logger.debug("attendance list size -- " + attendanceDataBean.getStudentList().size());
					} else {
						v = entitymanager
								.createQuery("from Attendance where school_ID=? and date=? and class_=? and section=?");
						v.setParameter(1, schoolid);
						v.setParameter(2, attendanceDataBean.getDate());
						v.setParameter(3, className);
						v.setParameter(4, sectionName);
						List<Attendance> attendance = (List<Attendance>) v.getResultList();
						if (attendance.size() > 0) {
							v = null;
							v = entitymanager.createQuery("from Attendanceclass where attendance_ID=? and period=?");
							v.setParameter(1, attendance.get(0).getAttendance_ID());
							v.setParameter(2, attendanceDataBean.getPeriod().substring(7, 8));
							List<Attendanceclass> attendanceclass = (List<Attendanceclass>) v.getResultList();
							if (attendanceclass.size() > 0) {
								int i1 = 1;
								for (int i = 0; i < attendanceclass.size(); i++) {
									AttendanceDataBean list = new AttendanceDataBean();
									list.setStudentName(attendanceclass.get(i).getStudentName());
									list.setStudentID(attendanceclass.get(i).getStudent_ID());
									list.setStatus(attendanceclass.get(i).getStatus());
									list.setPercentage(attendanceclass.get(i).getPercentage());
									list.setDate(attendance.get(0).getDate());
									list.setTime1(attendanceclass.get(i).getTime().getHours() + ":"
											+ attendanceclass.get(i).getTime().getMinutes());
									list.setSno(i1);
									attendancedata.add(list);
									attendanceDataBean.setStudentList(attendancedata);
									i1++;
								}
								attendanceDataBean.setFlag(false);
							}
						} else {
							attendanceDataBean.setStudentList(attendancedata);
						}
						logger.debug("attendance class size -- " + attendanceDataBean.getStudentList().size());
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public String studentAttView(String personID, AttendanceDataBean attendanceDataBean) {
		logger.info("-----------Inside studentAttView method()----------------");
		List<AttendanceDataBean> attendancedata = new ArrayList<AttendanceDataBean>();
		List<Person> person = null;
		Query v = null;
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					int schoolid = person.get(0).getSchool().getSchool_ID();
					v = entitymanager.createQuery("from Attendance where school_ID=? and date=?");
					v.setParameter(1, schoolid);
					v.setParameter(2, attendanceDataBean.getDate());
					List<Attendance> attendance = (List<Attendance>) v.getResultList();
					if (attendance.size() > 0) {
						for (int j = 0; j < attendance.size(); j++) {
							v = null;
							v = entitymanager
									.createQuery("from Attendanceclass where attendance_ID=? and student_ID=?");
							v.setParameter(1, attendance.get(j).getAttendance_ID());
							v.setParameter(2, person.get(0).getPersonRoleNumber());
							List<Attendanceclass> attendanceclass = (List<Attendanceclass>) v.getResultList();
							if (attendanceclass.size() > 0) {
								List<String> period = new ArrayList<String>();
								for (int i = 0; i < attendanceclass.size(); i++) {
									period.add(attendanceclass.get(i).getPeriod());
								}
								Collections.sort(period);
								int i1 = 1;
								for (int i = 0; i < period.size(); i++) {
									v = null;
									v = entitymanager.createQuery(
											"from Attendanceclass where attendance_ID=? and student_ID=? and period=?");
									v.setParameter(1, attendance.get(0).getAttendance_ID());
									v.setParameter(2,person.get(0).getPersonRoleNumber());
									v.setParameter(3, period.get(i));
									List<Attendanceclass> attendanceclass1 = (List<Attendanceclass>) v.getResultList();
									if (attendanceclass1.size() > 0) {
										attendanceDataBean.setStudentName(attendanceclass1.get(0).getStudentName());
										attendanceDataBean.setStudentID(attendanceclass1.get(0).getStudent_ID());
										AttendanceDataBean list = new AttendanceDataBean();
										list.setStatus(attendanceclass1.get(0).getStatus());
										list.setPercentage(attendanceclass1.get(0).getPercentage());
										list.setDate(attendanceclass1.get(0).getAttendance().getDate());
										list.setPeriod("Period " + attendanceclass1.get(0).getPeriod());
										list.setTime1(attendanceclass1.get(0).getTime().getHours() + ":"
												+ attendanceclass1.get(0).getTime().getMinutes());
										list.setSno(i1);
										attendancedata.add(list);
										attendanceDataBean.setStudentList(attendancedata);
										i1++;
									}
								}
							} else {
								v=null;
								v = entitymanager.createQuery("from Student where rollNumber=? and school_ID=? and status=?");
								v.setParameter(1, person.get(0).getPersonRoleNumber());
								v.setParameter(2, schoolid);
								v.setParameter(3, "Active");
								List<Student> student = (List<Student>) v.getResultList();
								if (student.size() > 0) {
									v = entitymanager.createQuery("from StudentDetail where student_ID=?");
									v.setParameter(1, student.get(0).getStudent_ID());
									List<StudentDetail> studentDetail = (List<StudentDetail>) v.getResultList();
									if (studentDetail.size() > 0) {
										attendanceDataBean.setStudentName(studentDetail.get(0).getFirstName()+studentDetail.get(0).getLastName());
										attendanceDataBean.setStudentID(person.get(0).getPersonRoleNumber());
									}
								}
								attendanceDataBean.setStudentList(attendancedata);
							}
						}
					} else {
						v = entitymanager.createQuery("from Student where rollNumber=? and school_ID=? and status=?");
						v.setParameter(1, person.get(0).getPersonRoleNumber());
						v.setParameter(2, schoolid);
						v.setParameter(3, "Active");
						List<Student> student = (List<Student>) v.getResultList();
						if (student.size() > 0) {
							v = entitymanager.createQuery("from StudentDetail where student_ID=?");
							v.setParameter(1, student.get(0).getStudent_ID());
							List<StudentDetail> studentDetail = (List<StudentDetail>) v.getResultList();
							if (studentDetail.size() > 0) {
								attendanceDataBean.setStudentName(studentDetail.get(0).getFirstName()+studentDetail.get(0).getLastName());
								attendanceDataBean.setStudentID(person.get(0).getPersonRoleNumber());
							}
						}
						attendanceDataBean.setStudentList(attendancedata);
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<String> parentAttlist(String personID, AttendanceDataBean attendanceDataBean) {
		logger.info("-----------Inside parentAttlist method()----------------");
		List<String> studentrollno = new ArrayList<String>();
		List<Person> person = null;
		Query q = null;
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					int schoolid = person.get(0).getSchool().getSchool_ID();
					q = entitymanager.createQuery("from Parent where school_ID=? and rollNumber=? and status=?");
					q.setParameter(1, schoolid);
					q.setParameter(2, person.get(0).getPersonRoleNumber());
					q.setParameter(3, "Active");
					List<Parent> parent = (List<Parent>) q.getResultList();
					if (parent.size() > 0) {
						q = entitymanager.createQuery("from StudentParent where parent_ID=? and status=?");
						q.setParameter(1, parent.get(0).getParent_ID());
						q.setParameter(2, "Active");
						List<StudentParent> studentparent = (List<StudentParent>) q.getResultList();
						if (studentparent.size() > 0) {
							for (int i = 0; i < studentparent.size(); i++) {
								q=null;
								q=entitymanager.createQuery("from StudentDetail where student_ID=?");
								q.setParameter(1, studentparent.get(i).getStudent().getStudent_ID());
								List<StudentDetail> studentdetail=(List<StudentDetail>)q.getResultList();
								if(studentdetail.size()>0){
									studentrollno.add(studentdetail.get(0).getFirstName()+studentdetail.get(0).getLastName()+"/"+studentparent.get(i).getStudent().getRollNumber());
								}
							}
						}
						HashSet<String> studentId = new HashSet<String>(studentrollno);
						studentrollno.clear();
						studentrollno.addAll(studentId);
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return studentrollno;
	}
	
	@SuppressWarnings("unchecked")
	public String parentstuAttView(String personID, AttendanceDataBean attendanceDataBean) {
		logger.info("-----------Inside parentstuAttView method()----------------");
		List<AttendanceDataBean> attendancedata = new ArrayList<AttendanceDataBean>();
		List<Person> roll = null;
		Query v = null;
		try {
			logger.debug("personID -------- " +personID);
			if (personID != null) {
				roll = getRollType(personID);
				logger.debug("roll size -------- " +roll.size());
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					logger.debug("schoolid -------- " +schoolid);
					StringTokenizer st= new StringTokenizer(attendanceDataBean.getStudentID());
					String studentName=st.nextToken("/");
					String students=attendanceDataBean.getStudentID();
					String studentid=students.substring(students.lastIndexOf("/")+1);
					logger.debug("--------- studentid -------- " +studentid);
					logger.debug("------ Date -------- " +attendanceDataBean.getDate());
					v = entitymanager.createQuery("from Student where roll_number=? and status='Active'");
					v.setParameter(1, studentid);
					List<Student> studentList = (List<Student>) v.getResultList();
					logger.info("----------- studentList --------------------"+studentList.size());
					logger.info("----------- student ID --------------------"+studentList.get(0).getStudent_ID());
					if(studentList.size() >0) { //1 //2 
						v = null;
						v = entitymanager.createQuery("from StudentClass where student_ID=? and status='Active'");
						v.setParameter(1, studentList.get(0).getStudent_ID());
						List<StudentClass> stuclassList = (List<StudentClass>) v.getResultList();// single dara 						
						logger.info("----------- class ID ------------"+stuclassList.get(0).getClazz().getClass_ID());
						logger.info("----------- stuclassList --------------------"+stuclassList.size());
						if(stuclassList.size() >0) {
							v = null;
							v = entitymanager.createQuery("from Class where class_ID=? and status='Active'");
							v.setParameter(1, stuclassList.get(0).getClazz().getClass_ID());
							List<Class> classList = (List<Class>) v.getResultList();
							logger.info("----------- classList --------------------"+classList.size());
							logger.info("----------- class ID ------------"+classList.get(0).getClass_ID());
							logger.info("----------- class Name ------------"+classList.get(0).getClassName());
							logger.info("----------- class Section ------------"+classList.get(0).getClassSection());
							v = null;
							v = entitymanager.createQuery("from Attendance where school_ID=? and date=? and class_=? and section=?");
							//v = entitymanager.createQuery("from Attendance where school_ID=? and date=?");
							v.setParameter(1, schoolid);
							v.setParameter(2, attendanceDataBean.getDate());
							v.setParameter(3, classList.get(0).getClassName());
							v.setParameter(4, classList.get(0).getClassSection());
							List<Attendance> attendance = (List<Attendance>) v.getResultList();
							logger.debug("attendance size -------- " +attendance.size());
							logger.debug("Attendance ID -------- " +attendance.get(0).getAttendance_ID());// correct id
							if (attendance.size() > 0) {
								v = null;
								v = entitymanager.createQuery("from Attendanceclass where attendance_ID=? and student_ID=?");
								//v = entitymanager.createQuery("from Attendanceclass where student_ID=?");
								v.setParameter(1, attendance.get(0).getAttendance_ID());
								v.setParameter(2, studentid);
								List<Attendanceclass> attendanceclass = (List<Attendanceclass>) v.getResultList();
								logger.debug("attendance class size -- " + attendanceclass.size());
								if (attendanceclass.size() > 0) {
									List<String> period = new ArrayList<String>();
									for (int i = 0; i < attendanceclass.size(); i++) {
										period.add(attendanceclass.get(i).getPeriod());
									}
									Collections.sort(period);
									logger.debug("period -------- " +period);
									int i1 = 1;
									for (int i = 0; i < period.size(); i++) {
										v = null;
										v = entitymanager.createQuery("from Attendanceclass where attendance_ID=? and student_ID=? and period=?");
										//v = entitymanager.createQuery("from Attendanceclass where student_ID=? and period=?");
										v.setParameter(1, attendance.get(0).getAttendance_ID());
										v.setParameter(2, studentid);
										v.setParameter(3, period.get(i));
										List<Attendanceclass> attendanceclass1 = (List<Attendanceclass>) v.getResultList();
										if (attendanceclass1.size() > 0) {
											attendanceDataBean.setStudentName(attendanceclass1.get(0).getStudentName());
											attendanceDataBean.setStudentID(attendanceclass1.get(0).getStudent_ID());
											AttendanceDataBean list = new AttendanceDataBean();
											list.setStatus(attendanceclass1.get(0).getStatus());
											list.setPercentage(attendanceclass1.get(0).getPercentage());
											list.setDate(attendanceclass1.get(0).getAttendance().getDate());
											list.setPeriod("Period   "+ attendanceclass1.get(0).getPeriod());
											list.setTime1(attendanceclass1.get(0).getTime().getHours() + ":"
													+ attendanceclass1.get(0).getTime().getMinutes());
											list.setSno(i1);
											list.setStudentName(attendanceclass1.get(0).getStudentName());
									        list.setStudentID(attendanceclass1.get(0).getStudent_ID());
											attendancedata.add(list);
											attendanceDataBean.setStudentList(attendancedata);
											i1++;
										}
									}
								} else {
									attendanceDataBean.setStudentName(studentName);
									attendanceDataBean.setStudentID(studentid);
									attendanceDataBean.setStudentList(attendancedata);
								}
							}else {
									attendanceDataBean.setStudentName(studentName);
									attendanceDataBean.setStudentID(studentid);
									attendanceDataBean.setStudentList(attendancedata);
							}
						}else {
							attendanceDataBean.setStudentName(studentName);
							attendanceDataBean.setStudentID(studentid);
							attendanceDataBean.setStudentList(attendancedata);
						}						
					} else {
						attendanceDataBean.setStudentName(studentName);
						attendanceDataBean.setStudentID(studentid);
						attendanceDataBean.setStudentList(attendancedata);
					}
					logger.debug("attendance size -- " + attendanceDataBean.getStudentList().size());
				}
			}
		} catch (Exception e) {
			logger.warn("Exception-->"+e.getMessage());
		}
		return null;
	}

	/*@SuppressWarnings("unchecked")
	public String parentstuAttView(String personID, AttendanceDataBean attendanceDataBean) {
		logger.info("-----------Inside parentstuAttView method()----------------");
		List<AttendanceDataBean> attendancedata = new ArrayList<AttendanceDataBean>();
		List<Person> roll = null;
		Query v = null;
		try {
			logger.debug("personID -------- " +personID);
			if (personID != null) {
				roll = getRollType(personID);
				logger.debug("roll size -------- " +roll.size());
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					logger.debug("schoolid -------- " +schoolid);
					StringTokenizer st= new StringTokenizer(attendanceDataBean.getStudentID());
					String studentName=st.nextToken("/");
					String students=attendanceDataBean.getStudentID();
					String studentid=students.substring(students.lastIndexOf("/")+1);
					logger.debug("--------- studentid -------- " +studentid);
					logger.debug("------ Date -------- " +attendanceDataBean.getDate());
					v = entitymanager.createQuery("from StudentClass where student_ID=? and status='Active'");
					v.setParameter(1, studentid);
					List<StudentClass> classList = (List<StudentClass>) v.getResultList();
					logger.info("----------- class Name ------------"+classList.get(0).getClazz().getClassName());
					logger.info("----------- class Section ------------"+classList.get(0).getClazz().getClassSection());
					v = null;
					v = entitymanager.createQuery("from Attendance where school_ID=? and date=? and class=? and section=?");
					//v = entitymanager.createQuery("from Attendance where school_ID=? and date=?");
					v.setParameter(1, schoolid);
					v.setParameter(2, attendanceDataBean.getDate());
					v.setParameter(3, classList.get(0).getClazz().getClassName());
					v.setParameter(4, classList.get(0).getClazz().getClassSection());
					List<Attendance> attendance = (List<Attendance>) v.getResultList();
					logger.debug("attendance size -------- " +attendance.size());
					logger.debug("Attendance ID -------- " +attendance.get(0).getAttendance_ID());
					if (attendance.size() > 0) {
						v = null;
						v = entitymanager.createQuery("from Attendanceclass where attendance_ID=? and student_ID=?");
						//v = entitymanager.createQuery("from Attendanceclass where student_ID=?");
						v.setParameter(1, attendance.get(0).getAttendance_ID());
						v.setParameter(2, studentid);
						List<Attendanceclass> attendanceclass = (List<Attendanceclass>) v.getResultList();
						logger.debug("attendance class size -- " + attendanceclass.size());
						if (attendanceclass.size() > 0) {
							List<String> period = new ArrayList<String>();
							for (int i = 0; i < attendanceclass.size(); i++) {
								period.add(attendanceclass.get(i).getPeriod());
							}
							Collections.sort(period);
							logger.debug("period -------- " +period);
							int i1 = 1;
							for (int i = 0; i < period.size(); i++) {
								v = null;
								v = entitymanager.createQuery("from Attendanceclass where attendance_ID=? and student_ID=? and period=?");
								//v = entitymanager.createQuery("from Attendanceclass where student_ID=? and period=?");
								v.setParameter(1, attendance.get(0).getAttendance_ID());
								v.setParameter(2, studentid);
								v.setParameter(3, period.get(i));
								List<Attendanceclass> attendanceclass1 = (List<Attendanceclass>) v.getResultList();
								if (attendanceclass1.size() > 0) {
									attendanceDataBean.setStudentName(attendanceclass1.get(0).getStudentName());
									attendanceDataBean.setStudentID(attendanceclass1.get(0).getStudent_ID());
									AttendanceDataBean list = new AttendanceDataBean();
									list.setStatus(attendanceclass1.get(0).getStatus());
									list.setPercentage(attendanceclass1.get(0).getPercentage());
									list.setDate(attendanceclass1.get(0).getAttendance().getDate());
									list.setPeriod("---------------------- Period --------------------" + attendanceclass1.get(0).getPeriod());
									list.setTime1(attendanceclass1.get(0).getTime().getHours() + ":"
											+ attendanceclass1.get(0).getTime().getMinutes());
									list.setSno(i1);
									list.setStudentName(attendanceclass1.get(0).getStudentName());
							        list.setStudentID(attendanceclass1.get(0).getStudent_ID());
									attendancedata.add(list);
									attendanceDataBean.setStudentList(attendancedata);
									i1++;
								}
							}
						} else {
							attendanceDataBean.setStudentName(studentName);
							attendanceDataBean.setStudentID(studentid);
							attendanceDataBean.setStudentList(attendancedata);
						}
					} else {
						attendanceDataBean.setStudentName(studentName);
						attendanceDataBean.setStudentID(studentid);
						attendanceDataBean.setStudentList(attendancedata);
					}
					logger.debug("attendance size -- " + attendanceDataBean.getStudentList().size());
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return null;
	}*/

	@SuppressWarnings("unchecked")
	public String attendanceChart(String personID, AttendanceDataBean attendanceDataBean) {
		logger.info("-----------Inside attendanceChart method()----------------");
		//logger.debug("attendance chart -- " + attendanceDataBean.getStdate() + " - " + attendanceDataBean.getEndate());
		List<AttendanceDataBean> attendancechartdata = null;
		List<Attendanceclass> attendanceclass = null;
		List<Person> roll = null;
		int mm = 0;
		Query v = null;
		try {
			Calendar now = Calendar.getInstance();
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					StringTokenizer st = new StringTokenizer(attendanceDataBean.getClassname());
					String className = st.nextToken("/");
					String section = attendanceDataBean.getClassname();
					String sectionName = section.substring(section.lastIndexOf("/") + 1);
					mm = attendanceDataBean.getStdate().getMonth() + 1;
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					if (roll.get(0).getPersonRole().equals("Student")) {
						v = entitymanager.createQuery("from Attendance where school_ID=? and date between ? and ?");
						v.setParameter(1, schoolid);
						v.setParameter(2, attendanceDataBean.getStdate());
						v.setParameter(3, attendanceDataBean.getEndate());
						List<Attendance> attendance = (List<Attendance>) v.getResultList();
						logger.debug("attendance size -- " + attendance.size());
						if (attendance.size() > 0) {
							int ii = 0, cc = 0, cc1 = 0, cc2 = 0;
							for (int i = 0; i < attendance.size(); i++) {
								v = null;
								v = entitymanager.createQuery(
										"from Attendanceclass where attendance_ID=? and student_ID=? and monthYear=?");
								v.setParameter(1, attendance.get(i).getAttendance_ID());
								v.setParameter(2, roll.get(0).getPersonRoleNumber());
								v.setParameter(3, mm + "/" + now.get(Calendar.YEAR));
								attendanceclass = (List<Attendanceclass>) v.getResultList();
								if (attendanceclass.size() > 0) {
									ii = ii + attendanceclass.size();
									if (attendanceclass.get(0).getStatus().equals("Present")) {
										cc++;
									} else if (attendanceclass.get(0).getStatus().equals("Absent")) {
										cc1++;
									} else if (attendanceclass.get(0).getStatus().equals("Leave")) {
										cc2++;
									}
								}
							}
							if (attendanceclass.size() > 0) {
								attendancechartdata = new ArrayList<AttendanceDataBean>();
								AttendanceDataBean chart = new AttendanceDataBean();
								chart.setStatus(attendanceclass.get(0).getStatus());
								if (cc > 0) {
									BigDecimal perc = BigDecimal.valueOf(0);
									float v1 = Float.parseFloat("" + cc) / Float.parseFloat("" + ii);
									perc = BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(100));
									chart.setPercentage("" + perc);
								} else {
									chart.setPercentage("0.0%");
								}
								if (cc1 > 0) {
									BigDecimal perc = BigDecimal.valueOf(0);
									float v1 = Float.parseFloat("" + cc1) / Float.parseFloat("" + ii);
									perc = BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(100));
									chart.setAbsent("" + perc);
								} else {
									chart.setAbsent("0.0%");
								}
								if (cc2 > 0) {
									BigDecimal perc = BigDecimal.valueOf(0);
									float v1 = Float.parseFloat("" + cc2) / Float.parseFloat("" + ii);
									perc = BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(100));
									chart.setLeave("" + perc);
								} else {
									chart.setLeave("0.0%");
								}
								attendancechartdata.add(chart);
								attendanceDataBean.setChart(attendancechartdata);
							} else {
								attendancechartdata = new ArrayList<AttendanceDataBean>();
								attendanceDataBean.setChart(attendancechartdata);
							}
						} else {
							attendancechartdata = new ArrayList<AttendanceDataBean>();
							attendanceDataBean.setChart(attendancechartdata);
						}
					} else if (roll.get(0).getPersonRole().equals("Parent")) {
						v = entitymanager.createQuery("from Attendance where school_ID=? and date between ? and ?");
						v.setParameter(1, schoolid);
						v.setParameter(2, attendanceDataBean.getStdate());
						v.setParameter(3, attendanceDataBean.getEndate());
						List<Attendance> attendance = (List<Attendance>) v.getResultList();
						logger.debug("attendance size -- " + attendance.size());
						if (attendance.size() > 0) {
							int ii = 0, cc = 0, cc1 = 0, cc2 = 0;
							for (int i = 0; i < attendance.size(); i++) {
								v = null;
								v = entitymanager.createQuery(
										"from Attendanceclass where attendance_ID=? and student_ID=? and monthYear=?");
								v.setParameter(1, attendance.get(i).getAttendance_ID());
								v.setParameter(2, attendanceDataBean.getStudentID());
								v.setParameter(3, mm + "/" + now.get(Calendar.YEAR));
								attendanceclass = (List<Attendanceclass>) v.getResultList();
								if (attendanceclass.size() > 0) {
									ii = ii + attendanceclass.size();
									if (attendanceclass.get(0).getStatus().equals("Present")) {
										cc++;
									} else if (attendanceclass.get(0).getStatus().equals("Absent")) {
										cc1++;
									} else if (attendanceclass.get(0).getStatus().equals("Leave")) {
										cc2++;
									}
								}
							}
							if (attendanceclass.size() > 0) {
								attendancechartdata = new ArrayList<AttendanceDataBean>();
								AttendanceDataBean chart = new AttendanceDataBean();
								chart.setStatus(attendanceclass.get(0).getStatus());
								if (cc > 0) {
									BigDecimal perc = BigDecimal.valueOf(0);
									float v1 = Float.parseFloat("" + cc) / Float.parseFloat("" + ii);
									perc = BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(100));
									chart.setPercentage("" + perc);
								} else {
									chart.setPercentage("0.0%");
								}
								if (cc1 > 0) {
									BigDecimal perc = BigDecimal.valueOf(0);
									float v1 = Float.parseFloat("" + cc1) / Float.parseFloat("" + ii);
									perc = BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(100));
									chart.setAbsent("" + perc);
								} else {
									chart.setAbsent("0.0%");
								}
								if (cc2 > 0) {
									BigDecimal perc = BigDecimal.valueOf(0);
									float v1 = Float.parseFloat("" + cc2) / Float.parseFloat("" + ii);
									perc = BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(100));
									chart.setLeave("" + perc);
								} else {
									chart.setLeave("0.0%");
								}
								attendancechartdata.add(chart);
								attendanceDataBean.setChart(attendancechartdata);
							} else {
								attendancechartdata = new ArrayList<AttendanceDataBean>();
								attendanceDataBean.setChart(attendancechartdata);
							}
						} else {
							attendancechartdata = new ArrayList<AttendanceDataBean>();
							attendanceDataBean.setChart(attendancechartdata);
						}
					} else if (roll.get(0).getPersonRole().equals("Teacher")
							||roll.get(0).getPersonRole().equals("Admin")) {
						v = entitymanager.createQuery(
								"from Attendance where school_ID=? and date between ? and ? and class_=? and section=?");
						v.setParameter(1, schoolid);
						v.setParameter(2, attendanceDataBean.getStdate());
						v.setParameter(3, attendanceDataBean.getEndate());
						v.setParameter(4, className);
						v.setParameter(5, sectionName);
						List<Attendance> attendance = (List<Attendance>) v.getResultList();
						logger.debug("attendance size -- " + attendance.size());
						if (attendance.size() > 0) {
							int ii = 0, cc = 0, cc1 = 0, cc2 = 0;
							for (int i = 0; i < attendance.size(); i++) {
								v = null;
								v = entitymanager
										.createQuery("from Attendanceclass where attendance_ID=? and monthYear=?");
								v.setParameter(1, attendance.get(i).getAttendance_ID());
								v.setParameter(2, mm + "/" + now.get(Calendar.YEAR));
								attendanceclass = (List<Attendanceclass>) v.getResultList();
								if (attendanceclass.size() > 0) {
									ii = ii + attendanceclass.size();
									for (int j = 0; j < attendanceclass.size(); j++) {
										if (attendanceclass.get(j).getStatus().equals("Present")) {
											cc++;
										} else if (attendanceclass.get(j).getStatus().equals("Absent")) {
											cc1++;
										} else if (attendanceclass.get(j).getStatus().equals("Leave")) {
											cc2++;
										}
									}
								}
							}
							if (attendanceclass.size() > 0) {
								attendancechartdata = new ArrayList<AttendanceDataBean>();
								AttendanceDataBean chart = new AttendanceDataBean();
								chart.setStatus(attendanceclass.get(0).getStatus());
								if (cc > 0) {
									BigDecimal perc = BigDecimal.valueOf(0);
									float v1 = Float.parseFloat("" + cc) / Float.parseFloat("" + ii);
									perc = BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(100));
									chart.setPercentage("" + perc);
								} else {
									chart.setPercentage("0.0%");
								}
								if (cc1 > 0) {
									BigDecimal perc = BigDecimal.valueOf(0);
									float v1 = Float.parseFloat("" + cc1) / Float.parseFloat("" + ii);
									perc = BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(100));
									chart.setAbsent("" + perc);
								} else {
									chart.setAbsent("0.0%");
								}
								if (cc2 > 0) {
									BigDecimal perc = BigDecimal.valueOf(0);
									float v1 = Float.parseFloat("" + cc2) / Float.parseFloat("" + ii);
									perc = BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(100));
									chart.setLeave("" + perc);
								} else {
									chart.setLeave("0.0%");
								}
								attendancechartdata.add(chart);
								attendanceDataBean.setChart(attendancechartdata);
							} else {
								attendancechartdata = new ArrayList<AttendanceDataBean>();
								attendanceDataBean.setChart(attendancechartdata);
							}
						} else {
							attendancechartdata = new ArrayList<AttendanceDataBean>();
							attendanceDataBean.setChart(attendancechartdata);
						}
					}
				}
				logger.debug("chart size -- " + attendanceDataBean.getChart().size());
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public List<String> getexamViewList(String personID, TimeTableDataBean timeTableDataBean) {
		logger.info("-----------Inside getexamViewList method()----------------");
		List<String> examlist = new ArrayList<String>();
		List<Person> person = null;
		Query v = null;
		try {
			Calendar now = Calendar.getInstance();
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					//int schoolid = person.get(0).getSchool().getSchool_ID();
					v = entitymanager.createQuery("from Student where rollNumber=? and status=?");
					v.setParameter(1, person.get(0).getPersonRoleNumber());
					v.setParameter(2, "Active");
					List<Student> student = (List<Student>) v.getResultList();
					if (student.size() > 0) {
						v = null;
						v = entitymanager.createQuery("from StudentClass where student_ID=? and status=?");
						v.setParameter(1, student.get(0).getStudent_ID());
						v.setParameter(2, "Active");
						List<StudentClass> studentclass = (List<StudentClass>) v.getResultList();
						if (studentclass.size() > 0) {
							v = null;
							v = entitymanager.createQuery("from ExamTable where class_name=? and status=?");
							v.setParameter(1, studentclass.get(0).getClazz().getClassName());
							v.setParameter(2, "Insert");
							List<ExamTable> examtable = (List<ExamTable>) v.getResultList();
							if (examtable.size() > 0) {
								for (int i = 0; i < examtable.size(); i++) {
									examlist.add(examtable.get(i).getExamTitle());
								}
								HashSet<String> list = new HashSet<String>(examlist);
								examlist.clear();
								examlist.addAll(list);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return examlist;
	}

	@SuppressWarnings("unchecked")
	public List<String> getstudentList(String personID, TimeTableDataBean timeTableDataBean) {
		logger.info("-----------Inside getstudentList method()----------------");
		List<String> studentlist = new ArrayList<String>();
		List<Person> person = null;
		Query q = null;
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					int schoolid = person.get(0).getSchool().getSchool_ID();
					q = entitymanager.createQuery("from Parent where school_ID=? and rollNumber=? and status=?");
					q.setParameter(1, schoolid);
					q.setParameter(2,person.get(0).getPersonRoleNumber());
					q.setParameter(3, "Active");
					List<Parent> parent = (List<Parent>) q.getResultList();
					if (parent.size() > 0) {
						q = entitymanager.createQuery("from StudentParent where parent_ID=? and status=?");
						q.setParameter(1, parent.get(0).getParent_ID());
						q.setParameter(2, "Active");
						List<StudentParent> studentparent = (List<StudentParent>) q.getResultList();
						if (studentparent.size() > 0) {
							for (int i = 0; i < studentparent.size(); i++) {
								q=null;
								q=entitymanager.createQuery("from StudentDetail where student_ID=?");
								q.setParameter(1, studentparent.get(i).getStudent().getStudent_ID());
								List<StudentDetail> studentDetail=(List<StudentDetail>)q.getResultList();
								if(studentDetail.size()>0){
									studentlist.add(studentDetail.get(0).getFirstName()+studentDetail.get(0).getLastName()+"/"+studentparent.get(i).getStudent().getRollNumber());
								}
							}
						}
						HashSet<String> students = new HashSet<String>(studentlist);
						studentlist.clear();
						studentlist.addAll(students);
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return studentlist;
	}

	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	public String attendanceStatus(String personID, AttendanceDataBean attendanceDataBean) {
		logger.info("-----------Inside attendanceStatus method()----------------");
		Query v = null;
		List<Person> person = null;
		List<String> maillist = new ArrayList<String>();
		String days = "";List<String> phones = new ArrayList<String>();
		String st = "";
		String et = "";
		String stime = "";
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int attendanceid = 0;
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					/*StringTokenizer stringtoken = new StringTokenizer(attendanceDataBean.getClassname());
					String classname = stringtoken.nextToken("/");
					String section = attendanceDataBean.getClassname();*/
					StringTokenizer stringtoken = new StringTokenizer(attendanceDataBean.getClassname());
					String classname = stringtoken.nextToken("/");
					String section = attendanceDataBean.getClassname();
					String sectionName = section.substring(section.lastIndexOf("/") + 1);
					int schoolid = person.get(0).getSchool().getSchool_ID();
					String day = "";
					if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
						day=String.valueOf(GetTimeZone.getDate("Asia/Makassar").getDay());
					}else{
						day=String.valueOf(date.getDay());
					}
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						if (day.equals("0"))
							days = "Sunday";
					}
					if (day.equals("1"))
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
					if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
						st= GetTimeZone.getTimeHour("Asia/Makassar");
						et=GetTimeZone.getTimeMin("Asia/Makassar");
					}else{
						st = String.valueOf(now.getTime().getHours());
						et = String.valueOf(now.getTime().getMinutes());
					}					
					stime = st + ":" + et;
					v = entitymanager.createQuery(
							"from Class where className=? and classSection=? and school_ID=? and status=?");
					v.setParameter(1, classname);
					v.setParameter(2, sectionName);
					v.setParameter(3, schoolid);
					v.setParameter(4, "Active");
					List<Class> classs = (List<Class>) v.getResultList();
					v = entitymanager.createQuery("from Teacher where school_ID=? and person_ID=? and status=?");
					v.setParameter(1, schoolid);
					v.setParameter(2, personID);
					v.setParameter(3, "Active");
					List<Teacher> teacher = (List<Teacher>) v.getResultList();
					if (teacher.size() > 0) {
						v = null;
						v = entitymanager.createQuery("from ClassTable where class_ID=? and day=? and year=? and school_ID=? and status='Active'");
						v.setParameter(1, classs.get(0).getClass_ID());
						v.setParameter(2, days);
						v.setParameter(3, String.valueOf(year));
						v.setParameter(4, schoolid);
						List<ClassTable> classtable = (List<ClassTable>) v.getResultList();
						if (classtable.size() > 0) {
							v = null;
							v = entitymanager.createQuery(
									"from ClassTimeTable where teacher_ID=? and day=? and class_table_ID=? and ? between startTime and endTime");
							v.setParameter(1, teacher.get(0).getTeacher_ID());
							v.setParameter(2, days);
							v.setParameter(3, classtable.get(0).getClass_table_ID());
							v.setParameter(4, stime);
							List<ClassTimeTable> classTimeTable = (List<ClassTimeTable>) v.getResultList();
							if (classTimeTable.size() > 0) {
								int id = Integer.parseInt(personID);
								attendanceDataBean.setPeriod(classTimeTable.get(0).getPeriod());
								v = null;
								v = entitymanager.createQuery(
										"from Attendance where school_ID=? and date=? and class_=? and section=?");
								v.setParameter(1, schoolid);
								v.setParameter(2, attendanceDataBean.getDate());
								v.setParameter(3, classname);
								v.setParameter(4, sectionName);
								List<Attendance> attendance = (List<Attendance>) v.getResultList();
								if (attendance.size() > 0) {
									v = null;
									v = entitymanager.createQuery("from Attendanceclass where attendance_ID=? and studentName=? and student_ID=? and teacher_ID=? and class_time_table_ID=? and period=?");
									v.setParameter(1, attendance.get(0).getAttendance_ID());
									v.setParameter(2, attendanceDataBean.getStudentName());
									v.setParameter(3, attendanceDataBean.getStudentID());
									v.setParameter(4, teacher.get(0).getTeacher_ID());
									v.setParameter(5, classTimeTable.get(0).getClass_time_table_ID());
									v.setParameter(6, classTimeTable.get(0).getPeriod());
									List<Attendanceclass> attendanceclass=(List<Attendanceclass>)v.getResultList();
									if(attendanceclass.size()>0){
										Attendanceclass insert1 = 
												entitymanager.find(Attendanceclass.class, attendanceclass.get(0).getAtt_class_ID());
										insert1.setStatus(attendanceDataBean.getStatus());
										entitymanager.merge(insert1);
									}
									
								}
								v = entitymanager.createQuery("from Student where rollNumber=? and status=?");
								v.setParameter(1, attendanceDataBean.getStudentID());
								v.setParameter(2, "Active");
								List<Student> student = (List<Student>) v.getResultList();
								if (student.size() > 0) {
									v = entitymanager.createQuery("from StudentParent where student_ID=? and status=?");
									v.setParameter(1, student.get(0).getStudent_ID());
									v.setParameter(2, "Active");
									List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();
									if (studentParent.size() > 0) {
										maillist.add(studentParent.get(0).getParent().getParentDetail().getEmaiId());
										phones.add(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
									}
									attendanceDataBean.setMails(maillist);
									attendanceDataBean.setPhones(phones);
								}
								v = null;
								v = entitymanager.createQuery(
										"from Attendanceclass where attendance_ID=? and month=? and year=? and period=? and status='Leave'");
								v.setParameter(1, attendanceid);
								v.setParameter(2, attendanceDataBean.getMonthyear());
								v.setParameter(3, attendanceDataBean.getYear());
								v.setParameter(4, classTimeTable.get(0).getPeriod());
								List<Attendanceclass> attendanceclass = (List<Attendanceclass>) v.getResultList();
								if (attendanceclass.size() > 0) {
									for (int i = 0; i < attendanceclass.size(); i++) {
										try {
											//String teacherid = "" + attendanceclass.get(i).getTeacher().getTeacher_ID();
										} catch (Exception e) {
											Attendanceclass insert1 = entitymanager.find(Attendanceclass.class,
													attendanceclass.get(i).getAtt_class_ID());
											insert1.setTeacher(
													entitymanager.find(Teacher.class, teacher.get(0).getTeacher_ID()));
											insert1.setClassTimeTable(entitymanager.find(ClassTimeTable.class,
													classTimeTable.get(0).getClass_time_table_ID()));
											entitymanager.merge(insert1);
											entitymanager.flush();
											entitymanager.clear();
										}

									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
			e.printStackTrace();
		}
		return "";
	}
//john 09-26-2017
	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	public void attendanceStatusNew(String personID, AttendanceDataBean attendanceDataBean) {
		logger.info("-----------Inside attendanceStatusNew method()----------------");
		Query v = null;
		List<Person> person = null;
		List<String> maillist = new ArrayList<String>();
		String days = "";List<String> phones = new ArrayList<String>();
		String st = "";
		String et = "";
		String stime = "";
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int attendanceid = 0;
		try {
			System.out.println("--------------->"+attendanceDataBean.getPeriod());
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					/*StringTokenizer stringtoken = new StringTokenizer(attendanceDataBean.getClassname());
					String classname = stringtoken.nextToken("/");
					String section = attendanceDataBean.getClassname();*/
					StringTokenizer stringtoken = new StringTokenizer(attendanceDataBean.getClassname());
					String classname = stringtoken.nextToken("/");
					String section = attendanceDataBean.getClassname();
					String sectionName = section.substring(section.lastIndexOf("/") + 1);
					int schoolid = person.get(0).getSchool().getSchool_ID();
					String day = "";
					if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
						day=String.valueOf(GetTimeZone.getDate("Asia/Makassar").getDay());
					}else{
						day=String.valueOf(date.getDay());
					}
					if (day.equals("0"))
						days = "Sunday";
					if (day.equals("1"))
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
					if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
						st= GetTimeZone.getTimeHour("Asia/Makassar");
						et=GetTimeZone.getTimeMin("Asia/Makassar");
					}else{
						st = String.valueOf(now.getTime().getHours());
						et = String.valueOf(now.getTime().getMinutes());
					}					
					/*stime = st + ":" + et;*/
					v = entitymanager.createQuery(
							"from Class where className=? and classSection=? and school_ID=? and status=?");
					v.setParameter(1, classname);
					v.setParameter(2, sectionName);
					v.setParameter(3, schoolid);
					v.setParameter(4, "Active");
					List<Class> classs = (List<Class>) v.getResultList();
					v = entitymanager.createQuery("from Teacher where school_ID=? and person_ID=? and status=?");
					v.setParameter(1, schoolid);
					v.setParameter(2, personID);
					v.setParameter(3, "Active");
					List<Teacher> teacher = (List<Teacher>) v.getResultList();
					if (teacher.size() > 0) {
						v = null;
						v = entitymanager.createQuery("from ClassTable where class_ID=? and day=? and year=? and school_ID=? and status='Active'");
						v.setParameter(1, classs.get(0).getClass_ID());
						v.setParameter(2, days);
						v.setParameter(3, String.valueOf(year));
						v.setParameter(4, schoolid);
						List<ClassTable> classtable = (List<ClassTable>) v.getResultList();
						if (classtable.size() > 0) {
							v = null;
							v = entitymanager.createQuery(
									"from ClassTimeTable where teacher_ID=? and day=? and class_table_ID=? and period=?");
							v.setParameter(1, teacher.get(0).getTeacher_ID());
							v.setParameter(2, days);
							v.setParameter(3, classtable.get(0).getClass_table_ID());
							v.setParameter(4, attendanceDataBean.getPeriod());
							List<ClassTimeTable> classTimeTable = (List<ClassTimeTable>) v.getResultList();
							if (classTimeTable.size() > 0) {
								int id = Integer.parseInt(personID);
								attendanceDataBean.setPeriod(classTimeTable.get(0).getPeriod());
								v = null;
								v = entitymanager.createQuery(
										"from Attendance where school_ID=? and date=? and class_=? and section=?");
								v.setParameter(1, schoolid);
								v.setParameter(2, attendanceDataBean.getDate());
								v.setParameter(3, classname);
								v.setParameter(4, sectionName);
								List<Attendance> attendance = (List<Attendance>) v.getResultList();
								if (attendance.size() > 0) {
									v = null;
									v = entitymanager.createQuery("from Attendanceclass where attendance_ID=? and studentName=? and student_ID=? and teacher_ID=? and class_time_table_ID=? and period=?");
									v.setParameter(1, attendance.get(0).getAttendance_ID());
									v.setParameter(2, attendanceDataBean.getStudentName());
									v.setParameter(3, attendanceDataBean.getStudentID());
									v.setParameter(4, teacher.get(0).getTeacher_ID());
									v.setParameter(5, classTimeTable.get(0).getClass_time_table_ID());
									v.setParameter(6, classTimeTable.get(0).getPeriod());
									List<Attendanceclass> attendanceclass=(List<Attendanceclass>)v.getResultList();
									if(attendanceclass.size()>0){
										Attendanceclass insert1 = 
												entitymanager.find(Attendanceclass.class, attendanceclass.get(0).getAtt_class_ID());
										insert1.setStatus(attendanceDataBean.getStatus());
										entitymanager.merge(insert1);
									}
								}
								v = entitymanager.createQuery("from Student where rollNumber=? and status=?");
								v.setParameter(1, attendanceDataBean.getStudentID());
								v.setParameter(2, "Active");
								List<Student> student = (List<Student>) v.getResultList();
								if (student.size() > 0) {
									v = entitymanager.createQuery("from StudentParent where student_ID=? and status=?");
									v.setParameter(1, student.get(0).getStudent_ID());
									v.setParameter(2, "Active");
									List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();
									if (studentParent.size() > 0) {
										maillist.add(studentParent.get(0).getParent().getParentDetail().getEmaiId());
										phones.add(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
									}
									attendanceDataBean.setMails(maillist);
									attendanceDataBean.setPhones(phones);
								}
								v = null;
								v = entitymanager.createQuery(
										"from Attendanceclass where attendance_ID=? and month=? and year=? and period=? and status='Leave'");
								v.setParameter(1, attendanceid);
								v.setParameter(2, attendanceDataBean.getMonthyear());
								v.setParameter(3, attendanceDataBean.getYear());
								v.setParameter(4, classTimeTable.get(0).getPeriod());
								List<Attendanceclass> attendanceclass = (List<Attendanceclass>) v.getResultList();
								if (attendanceclass.size() > 0) {
									for (int i = 0; i < attendanceclass.size(); i++) {
										try {
											String teacherid = "" + attendanceclass.get(i).getTeacher().getTeacher_ID();
										} catch (Exception e) {
											Attendanceclass insert1 = entitymanager.find(Attendanceclass.class,
													attendanceclass.get(i).getAtt_class_ID());
											insert1.setTeacher(
													entitymanager.find(Teacher.class, teacher.get(0).getTeacher_ID()));
											insert1.setClassTimeTable(entitymanager.find(ClassTimeTable.class,
													classTimeTable.get(0).getClass_time_table_ID()));
											entitymanager.merge(insert1);
											entitymanager.flush();
											entitymanager.clear();
										}

									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private List<Reportcard> getmarkList(int sub_ID, int tea_ID, int studentID, int class_ID, int school_ID,
			String examMarkTitle, ReportCardDataBean reportCardDataBean) {
		logger.info("-----------Inside getmarkList method()----------------");
		Query q = null;
		List<Reportcard> markList = null;
		try {
			markList = new ArrayList<Reportcard>();
			if (sub_ID > 0 && tea_ID > 0 && studentID > 0 && class_ID > 0 && school_ID > 0) {
				logger.debug("Inside getmarkList");
				q = entitymanager.createQuery(
						"from Reportcard where examTitle=? and student_ID=? and school_ID=? and class_ID=? and teacher_ID=? and subject_ID=? and status='Active'");
				q.setParameter(1, examMarkTitle);
				q.setParameter(2, studentID);
				q.setParameter(3, school_ID);
				q.setParameter(4, class_ID);
				q.setParameter(5, tea_ID);
				q.setParameter(6, sub_ID);
				List<Reportcard> result = (List<Reportcard>) q.getResultList();
				logger.debug("----------->>>" + result.size());
				if (result.size() > 0) {
					markList.addAll(result);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			// logger
		}
		return markList;
	}

	@SuppressWarnings("unchecked")
	public String getmailsID(StudentPerformanceDataBean studentPerformanceDataBean) {
		logger.info("-----------Inside getmailsID method()----------------");
		Query v = null;
		List<String> mails = new ArrayList<String>();List<String> phonenos = new ArrayList<String>();
		try {
			v = entitymanager.createQuery("from Student where rollNumber=? and status=?");
			v.setParameter(1, studentPerformanceDataBean.getPerformStudID());
			v.setParameter(2, "Active");
			List<Student> student = (List<Student>) v.getResultList();
			if (student.size() > 0) {
				int schoolid=student.get(0).getSchool().getSchool_ID();
				v = entitymanager.createQuery("from StudentParent where student_ID=? and status=?");
				v.setParameter(1, student.get(0).getStudent_ID());
				v.setParameter(2, "Active");
				List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();
				v = null;
				v = entitymanager.createQuery("from StudentDetail where student_ID=?");
				v.setParameter(1, student.get(0).getStudent_ID());
				List<StudentDetail> studentDetail = (List<StudentDetail>) v.getResultList();
				if (studentDetail.size() > 0) {
					mails.add(studentParent.get(0).getParent().getParentDetail().getEmaiId());
					phonenos.add(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
					if(schoolid==1 ||schoolid==2 || schoolid==3|| schoolid==4 || schoolid==5 || schoolid==6)
					{
					}else{
						mails.add(studentDetail.get(0).getEmailId());
					}				
					studentPerformanceDataBean.setMailid(mails);
					studentPerformanceDataBean.setPhonenos(phonenos);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			// logger
		} finally {

		}
		return "";
	}

	@Transactional(value = "transactionManager")
	@Override
	//@SuppressWarnings("unchecked")
	public String editReportCard(String personID, ReportCardDataBean reportCardDataBean) {
		logger.info("-----------Inside editReportCard method()----------------");
		String status = "Fail";
		List<Person> roleStatus = null;
		int school_ID = 0;
		String rollType = "NotValid";
		int sub_ID = 0;
		int tea_ID = 0;
		int class_ID = 0;
		int student_ID = 0;
		List<Class> classList = null;
		List<Reportcard> markCard = null;
		int reportcardID = 0;
		try {
			logger.debug("---------Inside editReportCard Dao method calling---------");
			roleStatus = new ArrayList<Person>();
			classList = new ArrayList<Class>();
			markCard = new ArrayList<Reportcard>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					logger.debug("---Type---" + rollType);
					if (school_ID > 0) {
						if (rollType.equalsIgnoreCase("Admin")) {
							classList = getClassID(personID, school_ID, reportCardDataBean.getStudMarkClass());
							student_ID = getStudentID(reportCardDataBean.getViewMarkStuName().split("/")[1]);
							logger.debug("student_ID------------" + student_ID);
							sub_ID = getSubjectID(reportCardDataBean.getMarkSubTitle(), personID, school_ID);
							logger.debug("sub_ID---------" + sub_ID);
							logger.debug("classList---------" + classList.size());
							if (classList.size() > 0 && student_ID > 0 && sub_ID > 0) {
								class_ID = classList.get(0).getClass_ID();
								logger.debug("class_ID" + class_ID);
								markCard = getmarkList(sub_ID, student_ID, class_ID, school_ID,
										reportCardDataBean.getExamMarkTitle(), reportCardDataBean);
								logger.debug("markCard" + markCard.size());
								if (markCard.size() > 0) {
									reportcardID = markCard.get(0).getReportcard_ID();
									logger.debug("reportcardID" + reportcardID);
									Reportcard report = entitymanager.find(Reportcard.class, reportcardID);
									if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
										report.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
										report.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
									}else{
										report.setCreatedDate(date);
										report.setCreatedTime(timestamp);
									}
									report.setGrade(reportCardDataBean.getGrade());
									report.setMark(reportCardDataBean.getMark());
									report.setResultStatus(reportCardDataBean.getResultStatus());
									entitymanager.merge(report);
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

	@SuppressWarnings("unchecked")
	private List<Reportcard> getmarkList(int sub_ID, int student_ID, int class_ID, int school_ID, String examMarkTitle,
			ReportCardDataBean reportCardDataBean) {
		logger.info("-----------Inside getmarkList method()----------------");
		Query q = null;
		List<Reportcard> markList = null;
		try {
			markList = new ArrayList<Reportcard>();
			if (sub_ID > 0 && student_ID > 0 && class_ID > 0 && school_ID > 0) {
				//logger.debug("Inside getmarkList");
				q = entitymanager.createQuery(
						"from Reportcard where examTitle=? and student_ID=? and school_ID=? and class_ID=?  and subject_ID=? and status='Active'");
				q.setParameter(1, examMarkTitle);
				q.setParameter(2, student_ID);
				q.setParameter(3, school_ID);
				q.setParameter(4, class_ID);
				q.setParameter(5, sub_ID);
				List<Reportcard> result = (List<Reportcard>) q.getResultList();
				logger.debug("----------->>>" + result.size());
				if (result.size() > 0) {
					markList.addAll(result);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			//logger.warn("Exception -->"+e.getMessage());
			// logger
		}
		return markList;
	}

	
	
	@Override
	public String getReportBarChart(String personID, ReportCardDataBean reportCardDataBean) {
		logger.info("-----------Inside getReportBarChart method()----------------");
		String status = "Fail";
		List<Person> roleStatus = null;
		int school_ID = 0;
		String rollType = "NotValid";
		int sub_ID = 0;
		int tea_ID = 0;
		int class_ID = 0;
		int student_ID = 0;
		int parent_ID = 0;
		List<Class> classList = null;
		List<Reportcard> markCard = null;
		int reportcardID = 0;
		reportCardDataBean.setReportList(null);
		List<String> subjectNameList = null;
		List<Parent> parentList = null;
		List<StudentParent> studentParentList = null;

		//Query q = null;
		try {
			logger.info("---------Inside getReportBarChart  method try block---------");
			parentList = new ArrayList<Parent>();
			subjectNameList = new ArrayList<String>();
			studentParentList = new ArrayList<StudentParent>();
			roleStatus = new ArrayList<Person>();
			classList = new ArrayList<Class>();
			markCard = new ArrayList<Reportcard>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					logger.debug("---Type---" + rollType);
					if (school_ID > 0) {
						if (rollType.equalsIgnoreCase("Admin") || rollType.equalsIgnoreCase("Teacher")) {
							if (reportCardDataBean.getBarstudentid() != null) {
								student_ID = getStudentID(reportCardDataBean.getBarstudentid().split("/")[1]);
								if (student_ID > 0) {
									class_ID = getStudentClassID(student_ID);
									if (class_ID > 0) {
										markCard = getReportCard(student_ID, school_ID, personID, class_ID);
										if (markCard.size() > 0) {
											reportCardDataBean.setReportList(markCard);
											subjectNameList = getSubjectNameList(class_ID, school_ID, personID);
											if (subjectNameList.size() > 0) {
												reportCardDataBean.setSubjectList(subjectNameList);
												status = "Success";
											}
										}
									}
								}
							}
						} else if (rollType.equalsIgnoreCase("Student")) {
							student_ID = getStudentID(roleStatus.get(0).getPersonRoleNumber());
							if (student_ID > 0) {
								class_ID = getStudentClassID(student_ID);
								if (class_ID > 0) {
									markCard = getReportCard(student_ID, school_ID, personID, class_ID);
									if (markCard.size() > 0) {
										reportCardDataBean.setReportList(markCard);
										subjectNameList = getSubjectNameList(class_ID, school_ID, personID);
										if (subjectNameList.size() > 0) {
											reportCardDataBean.setSubjectList(subjectNameList);
											reportCardDataBean.setBarstudentid(roleStatus.get(0).getPersonRoleNumber());
											status = "Success";
										}
									}
								}
							}
						} else if (rollType.equalsIgnoreCase("Parent")) {
							parentList = getParentList(personID, roleStatus.get(0).getPersonRoleNumber());
							if (parentList.size() > 0) {
								parent_ID = parentList.get(0).getParent_ID();
							}
							/*studentParentList = getParStudentID(parent_ID);
							if (studentParentList.size() > 0) {*/
							String students=reportCardDataBean.getBarstudentid();
							String studentid=students.substring(students.lastIndexOf("/")+1);
							student_ID = getStudentID(studentid);
								if (student_ID > 0) {
									class_ID = getStudentClassID(student_ID);
									if (class_ID > 0) {
										markCard = getReportCard(student_ID, school_ID, personID, class_ID);
										if (markCard.size() > 0) {
											reportCardDataBean.setReportList(markCard);
											subjectNameList = getSubjectNameList(class_ID, school_ID, personID);
											if (subjectNameList.size() > 0) {
												reportCardDataBean.setSubjectList(subjectNameList);
												reportCardDataBean.setBarstudentid(
														studentParentList.get(0).getStudent().getRollNumber());
												status = "Success";
											}
										}
									}
								/*}*/
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

	@SuppressWarnings("unchecked")
	private int getStudentClassID(int student_ID) {
		logger.info("-----------Inside getStudentClassID method()----------------");
		int classID = 0;
		Query q = null;
		try {
			q = entitymanager.createQuery("from StudentClass where student_ID=? and status='Active'");
			q.setParameter(1, student_ID);
			List<StudentClass> result = (List<StudentClass>) q.getResultList();
			if (result.size() > 0) {
				classID = result.get(0).getClazz().getClass_ID();
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return classID;
	}

	@SuppressWarnings("unchecked")
	private List<Reportcard> getReportCard(int student_ID, int school_ID, String personID, int class_ID) {
		logger.info("-----------Inside getReportCard method()----------------");
		List<Reportcard> markCard = null;
		Query q = null;
		try {
			markCard = new ArrayList<Reportcard>();
			if (personID != null) {
				q = entitymanager.createQuery(
						"from Reportcard where class_ID=? and school_ID=? and student_ID=? and status='Active'");
				q.setParameter(1, class_ID);
				q.setParameter(2, school_ID);
				q.setParameter(3, student_ID);
				List<Reportcard> res = (List<Reportcard>) q.getResultList();
				if (res.size() > 0) {
					markCard.addAll(res);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return markCard;
	}

	@SuppressWarnings("unchecked")
	private List<String> getSubjectNameList(int class_ID, int school_ID, String personID) {
		logger.info("-----------Inside getSubjectNameList method()----------------");
		Query q1 = null;
		List<String> subList = null;
		try {
			subList = new ArrayList<String>();
			q1 = entitymanager.createQuery("from ClassSubject where class_ID=? and status='Active'");
			q1.setParameter(1, class_ID);
			List<ClassSubject> res = (List<ClassSubject>) q1.getResultList();
			if (res.size() > 0) {
				for (int i = 0; i < res.size(); i++) {
					subList.add(res.get(i).getSubject().getSujectName());
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return subList;
	}

	@SuppressWarnings("unchecked")
	public String classTimeTable(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		logger.info("-----------Inside classTimeTable method()----------------");
		//logger.info("subject list ");
		Query v = null;
		List<Person> roll = null;
		List<String> subjects = null;
		List<ClassTimeTableDataBean> classlist = null;
		int year = 0;
		try {
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					StringTokenizer stringtoken = new StringTokenizer(classTimeTableDataBean.getClassname());
					String classname = stringtoken.nextToken("/");
					String section = classTimeTableDataBean.getClassname();
					String sectionName = section.substring(section.lastIndexOf("/") + 1);
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					classTimeTableDataBean.setSchoolID(schoolid);
					logger.debug("school id -- > " + schoolid);
					classTimeTableDataBean.setStatus("");
					v = entitymanager.createQuery(
							"from Class where className=? and classSection=? and school_ID=? and status=?");
					v.setParameter(1, classname);
					v.setParameter(2, sectionName);
					v.setParameter(3, schoolid);
					v.setParameter(4, "Active");
					List<Class> classs = (List<Class>) v.getResultList();
					if (classs.size() > 0) {
						Calendar now = Calendar.getInstance();
						year = now.get(Calendar.YEAR);
						v = null;
						v = entitymanager
								.createQuery("from ClassTable where day=? and year=? and class_ID=? and school_ID=? and status='Active'");
						v.setParameter(1, classTimeTableDataBean.getDay());
						v.setParameter(2, String.valueOf(year));
						v.setParameter(3, classs.get(0).getClass_ID());
						v.setParameter(4, schoolid);
						List<ClassTable> classtable = (List<ClassTable>) v.getResultList();
						logger.debug("class table size -- " + classtable.size());
						if (classtable.size() > 0) {
							classTimeTableDataBean.setStatus("Exist");
						} else {
							classTimeTableDataBean.setStatus("Not");
							v = null;
							v = entitymanager.createQuery("from ClassSubject where class_ID=? and status=?");
							v.setParameter(1, classs.get(0).getClass_ID());
							v.setParameter(2, "Active");
							List<ClassSubject> classSubject = (List<ClassSubject>) v.getResultList();
							logger.debug("subject size -- " + classSubject.size());
							if (classSubject.size() > 0) {
								subjects = new ArrayList<String>();
								for (ClassSubject classsubject : classSubject) {
									subjects.add(classsubject.getSubject().getSujectName());
								}
								classTimeTableDataBean.setSubjectlist(subjects);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		} finally {

		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public List<String> subjectChange(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		logger.info("-----------Inside subjectChange method()----------------");
		//logger.info("subject change dao ");
		Query v = null;Query q1 = null;Query q2 = null;
		List<Person> roll = new ArrayList<Person>();
		List<ClassTimeTableDataBean> classlist = null;
		Set<String> dublicate=null;
		List<String> idList=null; List<String> codelist=null;
		try {
			dublicate=new HashSet<String>();
			idList=new ArrayList<String>();
			codelist=new ArrayList<String>();
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					StringTokenizer stringtoken = new StringTokenizer(classTimeTableDataBean.getClassname());
					String classname = stringtoken.nextToken("/");
					String section = classTimeTableDataBean.getClassname();
					String sectionName = section.substring(section.lastIndexOf("/") + 1);
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					logger.debug("school id -- > " + schoolid);
					v = entitymanager.createQuery(
							"from Class where className=? and classSection=? and school_ID=? and status=?");
					v.setParameter(1, classname);
					v.setParameter(2, sectionName);
					v.setParameter(3, schoolid);
					v.setParameter(4, "Active");
					List<Class> classs = (List<Class>) v.getResultList();
					if (classs.size() > 0) {
						v = null;
						v = entitymanager.createQuery("from Subject where sujectName=? and school_ID=? and status=?");
						v.setParameter(1, classTimeTableDataBean.getSubject());
						v.setParameter(2, schoolid);
						v.setParameter(3, "Active");
						List<Subject> subject = (List<Subject>) v.getResultList();
						System.out.println("subject size--->"+subject.size());
						if (subject.size() > 0) {
							for (int i = 0; i < subject.size(); i++) {
								v = null;
								v = entitymanager.createQuery(
										"from ClassSubject where class_ID=? and subject_ID=? and status=?");
								v.setParameter(1, classs.get(0).getClass_ID());
								v.setParameter(2, subject.get(i).getSubject_ID());
								v.setParameter(3, "Active");
								List<ClassSubject> classSubject = (List<ClassSubject>) v.getResultList();
								System.out.println("subject size -- " + classSubject.size());
								if (classSubject.size() > 0) {
									codelist.add(classSubject.get(0).getSubject().getSubjectCode());									
									q1=entitymanager.createQuery("from TeacherSubject where school_ID=? and subject_ID=? and status=?");
									q1.setParameter(1, schoolid);
									q1.setParameter(2, classSubject.get(0).getSubject().getSubject_ID());
									q1.setParameter(3, "Active");
									List<TeacherSubject> teachersubList =(List<TeacherSubject>)q1.getResultList();
									System.out.println("teachersubList size-->"+teachersubList.size());
									
									if(teachersubList.size() > 0){
										
										for(TeacherSubject sub : teachersubList)
										{
											q1=entitymanager.createQuery("from TeacherClass where teacher_ID=? and class_ID=? and status=?");
											q1.setParameter(1, sub.getTeacher().getTeacher_ID());
											q1.setParameter(2, classs.get(0).getClass_ID());
											q1.setParameter(3, "Active");
											List<TeacherClass> teacherclass =(List<TeacherClass>)q1.getResultList();
											if (teacherclass.size()>0) {
												String statusTeacher=sub.getTeacher().getStatus();
												if(statusTeacher.equalsIgnoreCase("Active")){
													dublicate.add(sub.getTeacher().getRollNumber());
												}
											}
										
										}
										idList.addAll(dublicate);
									}
								}
							}
							classTimeTableDataBean.setSubjectcodelist(codelist);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		} finally {

		}
		return idList;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> subjectCodeChange(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		logger.info("-----------Inside subjectCodeChange method()----------------");
		Query v = null;Query q1 = null;Query q2 = null;
		List<Person> roll = new ArrayList<Person>();
		List<ClassTimeTableDataBean> classlist = null;
		Set<String> dublicate=null;
		List<String> idList=null; List<String> codelist=null;
		try {
			dublicate=new HashSet<String>();
			idList=new ArrayList<String>();
			codelist=new ArrayList<String>();
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					StringTokenizer stringtoken = new StringTokenizer(classTimeTableDataBean.getClassname());
					String classname = stringtoken.nextToken("/");
					String section = classTimeTableDataBean.getClassname();
					String sectionName = section.substring(section.lastIndexOf("/") + 1);
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					logger.debug("school id -- > " + schoolid);
					v = entitymanager.createQuery(
							"from Class where className=? and classSection=? and school_ID=? and status=?");
					v.setParameter(1, classname);
					v.setParameter(2, sectionName);
					v.setParameter(3, schoolid);
					v.setParameter(4, "Active");
					List<Class> classs = (List<Class>) v.getResultList();
					if (classs.size() > 0) {
						v = null;
						v = entitymanager.createQuery("from Subject where sujectName=? and subjectCode=? and school_ID=? and status=?");
						v.setParameter(1, classTimeTableDataBean.getSubject());
						v.setParameter(2, classTimeTableDataBean.getSubjectCode());
						v.setParameter(3, schoolid);
						v.setParameter(4, "Active");
						List<Subject> subject = (List<Subject>) v.getResultList();
						if (subject.size() > 0) {
							for (int i = 0; i < subject.size(); i++) {
								v = null;
								v = entitymanager.createQuery(
										"from ClassSubject where class_ID=? and subject_ID=? and status=?");
								v.setParameter(1, classs.get(0).getClass_ID());
								v.setParameter(2, subject.get(i).getSubject_ID());
								v.setParameter(3, "Active");
								List<ClassSubject> classSubject = (List<ClassSubject>) v.getResultList();
								logger.debug("subject size -- " + classSubject.size());
								if (classSubject.size() > 0) {
									codelist.add(classSubject.get(0).getSubject().getSubjectCode());									
									q1=entitymanager.createQuery("from TeacherSubject where school_ID=? and subject_ID=? and status=?");
									q1.setParameter(1, schoolid);
									q1.setParameter(2, classSubject.get(0).getSubject().getSubject_ID());
									q1.setParameter(3, "Active");
									List<TeacherSubject> teachersubList =(List<TeacherSubject>)q1.getResultList();
									if(teachersubList.size() > 0){
										
										for(TeacherSubject sub : teachersubList)
										{
										String statusTeacher=sub.getTeacher().getStatus();
										if(statusTeacher.equalsIgnoreCase("Active")){
											dublicate.add(sub.getTeacher().getRollNumber());
										}
										}
										idList.addAll(dublicate);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		} finally {

		}
		return idList;
	}

	@SuppressWarnings("unchecked")
	public String attendanceChartStudent(String personID, AttendanceDataBean attendanceDataBean) {
		logger.info("-----------Inside attendanceChartStudent method()----------------");
		//logger.debug("chart values from -- " + attendanceDataBean.getMonthyear());
		List<AttendanceDataBean> attendancechartdata = null;
		List<Attendanceclass> attendanceclass = null;
		List<Person> roll = null;
		int mm = 0;
		Query v = null;
		try {
			Calendar now = Calendar.getInstance();
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					if (attendanceDataBean.getMonthyear().equals("Month")) {
						mm = attendanceDataBean.getStdate().getMonth() + 1;
						v = entitymanager.createQuery("from Attendance where school_ID=? and date between ? and ?");
						v.setParameter(1, schoolid);
						v.setParameter(2, attendanceDataBean.getStdate());
						v.setParameter(3, attendanceDataBean.getEndate());
						List<Attendance> attendance = (List<Attendance>) v.getResultList();
						if (attendance.size() > 0) {
							int ii = 0, cc = 0, cc1 = 0, cc2 = 0, cc3 = 0;
							for (int i = 0; i < attendance.size(); i++) {
								v = null;
								if (attendanceDataBean.getClassStudent().equals("Class")) {
									v = entitymanager.createQuery(
											"from Attendanceclass where attendance_ID=? and month=? and year=?");
									v.setParameter(1, attendance.get(i).getAttendance_ID());
									v.setParameter(2, "" + mm);
									v.setParameter(3, "" + now.get(Calendar.YEAR));
									attendanceclass = (List<Attendanceclass>) v.getResultList();
									if (attendanceclass.size() > 0) {
										ii = ii + attendanceclass.size();
										/*String classn = attendanceDataBean.getClassname();
										String className = classn.substring(0, 8);
										String sectionName = classn.substring(9, 10);*/
										StringTokenizer stringtoken = new StringTokenizer(attendanceDataBean.getClassname());
										String className = stringtoken.nextToken("/");
										String section = attendanceDataBean.getClassname();
										String sectionName = section.substring(section.lastIndexOf("/") + 1);
										if (attendanceclass.get(0).getAttendance().getClass_().equals(className)
												&& attendanceclass.get(0).getAttendance().getSection()
														.equals(sectionName)) {
											for (int j = 0; j < attendanceclass.size(); j++) {
												if (attendanceclass.get(j).getStatus().equals("Present")) {
													cc++;
												} else if (attendanceclass.get(j).getStatus().equals("Absent")) {
													cc1++;
												} else if (attendanceclass.get(j).getStatus().equals("Leave")) {
													cc2++;
												} else if (attendanceclass.get(j).getStatus().equals("Late")) {
													cc3++;
												}
											}
										}
									}
								} else {
									if (roll.get(0).getPersonRole().equals("Parent")
											|| roll.get(0).getPersonRole().equals("Admin")
											|| roll.get(0).getPersonRole().equals("Teacher")) {
										String student = attendanceDataBean.getStudentID();
										String studentID = student.substring(student.lastIndexOf("/") + 1);
										v = entitymanager.createQuery(
												"from Attendanceclass where attendance_ID=? and student_ID=? and month=? and year=?");
										v.setParameter(1, attendance.get(i).getAttendance_ID());
										v.setParameter(2, studentID);
										v.setParameter(3, "" + mm);
										v.setParameter(4, "" + now.get(Calendar.YEAR));
									} else if (roll.get(0).getPersonRole().equals("Student")) {
										v = entitymanager.createQuery(
												"from Attendanceclass where attendance_ID=? and student_ID=? and month=? and year=?");
										v.setParameter(1, attendance.get(i).getAttendance_ID());
										v.setParameter(2, roll.get(0).getPersonRoleNumber());
										v.setParameter(3, "" + mm);
										v.setParameter(4, "" + now.get(Calendar.YEAR));
									}
									attendanceclass = (List<Attendanceclass>) v.getResultList();
									if (attendanceclass.size() > 0) {
										ii = ii + attendanceclass.size();
										for (int j = 0; j < attendanceclass.size(); j++) {
											if (attendanceclass.get(j).getStatus().equals("Present")) {
												cc++;
											} else if (attendanceclass.get(j).getStatus().equals("Absent")) {
												cc1++;
											} else if (attendanceclass.get(j).getStatus().equals("Leave")) {
												cc2++;
											} else if (attendanceclass.get(j).getStatus().equals("Late")) {
												cc3++;
											}
										}
									}
								}
							}
							if (cc > 0 || cc1 > 0 || cc2 > 0 || cc3 > 0) {
								attendancechartdata = new ArrayList<AttendanceDataBean>();
								AttendanceDataBean chart = new AttendanceDataBean();
								if (cc > 0) {
									BigDecimal perc = BigDecimal.valueOf(0);
									float v1 = Float.parseFloat("" + cc) / Float.parseFloat("" + ii);
									perc = BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(100));
									chart.setPercentage("" + perc);
								} else {
									chart.setPercentage("0.0%");
								}
								if (cc1 > 0) {
									BigDecimal perc = BigDecimal.valueOf(0);
									float v1 = Float.parseFloat("" + cc1) / Float.parseFloat("" + ii);
									perc = BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(100));
									chart.setAbsent("" + perc);
								} else {
									chart.setAbsent("0.0%");
								}
								if (cc2 > 0) {
									BigDecimal perc = BigDecimal.valueOf(0);
									float v1 = Float.parseFloat("" + cc2) / Float.parseFloat("" + ii);
									perc = BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(100));
									chart.setLeave("" + perc);
								} else {
									chart.setLeave("0.0%");
								}
								if (cc3 > 0) {
									BigDecimal perc = BigDecimal.valueOf(0);
									float v1 = Float.parseFloat("" + cc3) / Float.parseFloat("" + ii);
									perc = BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(100));
									chart.setLate("" + perc);
								} else {
									chart.setLate("0.0%");
								}
								attendancechartdata.add(chart);
								attendanceDataBean.setChart(attendancechartdata);
							} else {
								attendancechartdata = new ArrayList<AttendanceDataBean>();
								attendanceDataBean.setChart(attendancechartdata);
							}
						} else {
							attendancechartdata = new ArrayList<AttendanceDataBean>();
							attendanceDataBean.setChart(attendancechartdata);
						}
					} else if (attendanceDataBean.getMonthyear().equals("Year")) {
						v = entitymanager.createQuery("from Attendance where school_ID=? ");
						v.setParameter(1, schoolid);
						List<Attendance> attendance1 = (List<Attendance>) v.getResultList();
						if (attendance1.size() > 0) {
							int ii = 0, cc = 0, cc1 = 0, cc2 = 0, cc3 = 0;
							for (int i = 0; i < attendance1.size(); i++) {
								if (attendanceDataBean.getClassStudent().equals("Class")) {
									logger.info("inside class -- ");
									v = entitymanager
											.createQuery("from Attendanceclass where attendance_ID=? and year=?");
									v.setParameter(1, attendance1.get(i).getAttendance_ID());
									v.setParameter(2, attendanceDataBean.getYear());
									attendanceclass = (List<Attendanceclass>) v.getResultList();
									if (attendanceclass.size() > 0) {
										ii = ii + attendanceclass.size();
										/*String classn = attendanceDataBean.getClassname();
										String className = classn.substring(0, 8);
										String sectionName = classn.substring(9, 10);*/
										StringTokenizer stringtoken = new StringTokenizer(attendanceDataBean.getClassname());
										String className = stringtoken.nextToken("/");
										String section = attendanceDataBean.getClassname();
										String sectionName = section.substring(section.lastIndexOf("/") + 1);
										if (attendanceclass.get(0).getAttendance().getClass_().equals(className)
												&& attendanceclass.get(0).getAttendance().getSection()
														.equals(sectionName)) {
											for (int j = 0; j < attendanceclass.size(); j++) {
												if (attendanceclass.get(j).getStatus().equals("Present")) {
													cc++;
												} else if (attendanceclass.get(j).getStatus().equals("Absent")) {
													cc1++;
												} else if (attendanceclass.get(j).getStatus().equals("Leave")) {
													cc2++;
												} else if (attendanceclass.get(j).getStatus().equals("Late")) {
													cc3++;
												}
											}
										}
									}
								} else {
									logger.info("inside student -- ");
									if (roll.get(0).getPersonRole().equals("Parent")
											|| roll.get(0).getPersonRole().equals("Admin")
											|| roll.get(0).getPersonRole().equals("Teacher")) {
										String student = attendanceDataBean.getStudentID();
										String studentID = student.substring(student.lastIndexOf("/") + 1);
										v = entitymanager.createQuery(
												"from Attendanceclass where attendance_ID=? and student_ID=? and year=?");
										v.setParameter(1, attendance1.get(i).getAttendance_ID());
										v.setParameter(2, studentID);
										v.setParameter(3, attendanceDataBean.getYear());
									} else if (roll.get(0).getPersonRole().equals("Student")) {
										v = entitymanager.createQuery(
												"from Attendanceclass where attendance_ID=? and student_ID=? and year=?");
										v.setParameter(1, attendance1.get(i).getAttendance_ID());
										v.setParameter(2, roll.get(0).getPersonRoleNumber());
										v.setParameter(3, attendanceDataBean.getYear());
									}
									attendanceclass = (List<Attendanceclass>) v.getResultList();
									if (attendanceclass.size() > 0) {
										ii = ii + attendanceclass.size();
										for (int j = 0; j < attendanceclass.size(); j++) {
											if (attendanceclass.get(j).getStatus().equals("Present")) {
												cc++;
											} else if (attendanceclass.get(j).getStatus().equals("Absent")) {
												cc1++;
											} else if (attendanceclass.get(j).getStatus().equals("Leave")) {
												cc2++;
											} else if (attendanceclass.get(j).getStatus().equals("Late")) {
												cc3++;
											}
										}
									}
								}
							}
							if (cc > 0 || cc1 > 0 || cc2 > 0 || cc3 > 0) {
								attendancechartdata = new ArrayList<AttendanceDataBean>();
								AttendanceDataBean chart = new AttendanceDataBean();
								if (cc > 0) {
									BigDecimal perc = BigDecimal.valueOf(0);
									float v1 = Float.parseFloat("" + cc) / Float.parseFloat("" + ii);
									perc = BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(100));
									chart.setPercentage("" + perc);
								} else {
									chart.setPercentage("0.0%");
								}
								if (cc1 > 0) {
									BigDecimal perc = BigDecimal.valueOf(0);
									float v1 = Float.parseFloat("" + cc1) / Float.parseFloat("" + ii);
									perc = BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(100));
									chart.setAbsent("" + perc);
								} else {
									chart.setAbsent("0.0%");
								}
								if (cc2 > 0) {
									BigDecimal perc = BigDecimal.valueOf(0);
									float v1 = Float.parseFloat("" + cc2) / Float.parseFloat("" + ii);
									perc = BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(100));
									chart.setLeave("" + perc);
								} else {
									chart.setLeave("0.0%");
								}
								if (cc3 > 0) {
									BigDecimal perc = BigDecimal.valueOf(0);
									float v1 = Float.parseFloat("" + cc3) / Float.parseFloat("" + ii);
									perc = BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(100));
									chart.setLate("" + perc);
								} else {
									chart.setLate("0.0%");
								}
								attendancechartdata.add(chart);
								attendanceDataBean.setChart(attendancechartdata);
							} else {
								attendancechartdata = new ArrayList<AttendanceDataBean>();
								attendanceDataBean.setChart(attendancechartdata);
							}
						} else {
							attendancechartdata = new ArrayList<AttendanceDataBean>();
							attendanceDataBean.setChart(attendancechartdata);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public String attendanceChartStudentYear(String personID, AttendanceDataBean attendanceDataBean) {
		//logger.debug("values from -yaer- " + attendanceDataBean.getMonthyear());
		logger.info("-----------Inside attendanceChartStudentYear method()----------------");
		List<Attendanceclass> attendanceclass = null;
		List<Person> roll = null;
		List<String> years = new ArrayList<String>();
		Query v = null;
		try {
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					v = entitymanager.createQuery("from Attendance where school_ID=? ");
					v.setParameter(1, schoolid);
					List<Attendance> attendance = (List<Attendance>) v.getResultList();
					if (attendance.size() > 0) {
						for (int i = 0; i < attendance.size(); i++) {
							v = null;
							if (attendanceDataBean.getClassStudent().equals("Class")) {
								v = entitymanager.createQuery("from Attendanceclass where attendance_ID=? ");
								v.setParameter(1, attendance.get(i).getAttendance_ID());
								attendanceclass = (List<Attendanceclass>) v.getResultList();
								if (attendanceclass.size() > 0) {
									
									StringTokenizer stringtoken = new StringTokenizer(attendanceDataBean.getClassname());
									String className = stringtoken.nextToken("/");
									String section = attendanceDataBean.getClassname();
									String sectionName = section.substring(section.lastIndexOf("/") + 1);
									if (attendanceclass.get(0).getAttendance().getClass_().equals(className)
											&& attendanceclass.get(0).getAttendance().getSection()
													.equals(sectionName)) {
										years.add(attendanceclass.get(0).getYear());
									}
								}
							} else {
								if (roll.get(0).getPersonRole().equals("Parent")
										|| roll.get(0).getPersonRole().equals("Admin")
										||roll.get(0).getPersonRole().equals("Teacher")) {
									logger.debug("student id "+attendanceDataBean.getStudentID());
									String student = attendanceDataBean.getStudentID();
									String studentID = student.substring(student.lastIndexOf("/") + 1);
									v = entitymanager
											.createQuery("from Attendanceclass where attendance_ID=? and student_ID=?");
									v.setParameter(1, attendance.get(i).getAttendance_ID());
									v.setParameter(2, studentID);
								} else if (roll.get(0).getPersonRole().equals("Student")) {
									v = entitymanager
											.createQuery("from Attendanceclass where attendance_ID=? and student_ID=?");
									v.setParameter(1, attendance.get(i).getAttendance_ID());
									v.setParameter(2, roll.get(0).getPersonRoleNumber());
								}
								attendanceclass = (List<Attendanceclass>) v.getResultList();
								if (attendanceclass.size() > 0) {
									years.add(attendanceclass.get(0).getYear());
								}
							}
						}
						HashSet<String> year = new HashSet<String>(years);
						years.clear();
						years.addAll(year);
						Collections.sort(years);
						attendanceDataBean.setYears(years);
					} else {
						years.add("");
						attendanceDataBean.setYears(years);
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
			logger.warn("Inside Exception",e);
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public String classStudent(AttendanceDataBean attendanceDataBean, String personID) {
		logger.info("-----------Inside classStudent method()----------------");
		//logger.debug("class - " + attendanceDataBean.getClassname());
		List<Person> roll = null;
		List<String> studentID = null;
		Query v = null;
		try {
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();
				
					StringTokenizer stringtoken = new StringTokenizer(attendanceDataBean.getClassname());
					String className = stringtoken.nextToken("/");
					String section = attendanceDataBean.getClassname();
					String sectionName = section.substring(section.lastIndexOf("/") + 1);
					v = entitymanager.createQuery("from Class where class_name=? and class_section=? and school_ID=? and status='Active'");
					v.setParameter(1, className);
					v.setParameter(2, sectionName);
					v.setParameter(3, schoolid);
					List<Class> classs = (List<Class>) v.getResultList();
					if (classs.size() > 0) {
						v = null;
						v = entitymanager.createQuery("from StudentClass where class_ID=? and status='Active'");
						v.setParameter(1, classs.get(0).getClass_ID());
						List<StudentClass> studentClass = (List<StudentClass>) v.getResultList();
						if (studentClass.size() > 0) {
							studentID = new ArrayList<String>();
							for (int i = 0; i < studentClass.size(); i++) {
								v = null;
								v = entitymanager.createQuery("from StudentDetail where student_ID=?");
								v.setParameter(1, studentClass.get(i).getStudent().getStudent_ID());
								List<StudentDetail> student = (List<StudentDetail>) v.getResultList();
								if (student.size() > 0) {
									studentID.add(student.get(0).getFirstName()+student.get(0).getLastName()+""+"/"+""+studentClass.get(i).getStudent().getRollNumber());
								}
							}							
						}
						attendanceDataBean.setStudents(studentID);
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return "";
	}

	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	public String leaveRequest(AttendanceDataBean attendanceDataBean, String personID) {
		logger.info("-----------Inside leaveRequest method()----------------");
		Query v = null;
		List<Person> roll = null;
		String status = "not exist";
		try {
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					v = entitymanager.createQuery("from Parent where rollNumber=? and status=?");
					v.setParameter(1, roll.get(0).getPersonRoleNumber());
					v.setParameter(2, "Active");
					List<Parent> parent = (List<Parent>) v.getResultList();
					if (parent.size() > 0) {
						String students=attendanceDataBean.getStudentID();
						String studentid=students.substring(students.lastIndexOf("/")+1);
						v = entitymanager.createQuery("from Student where rollNumber=? and status='Active'");
						v.setParameter(1, studentid);
						List<Student> student = (List<Student>) v.getResultList();
						if (student.size() > 0) {
							v = null;
							v = entitymanager.createQuery("from StudentClass where student_ID=? and status=?");
							v.setParameter(1, student.get(0).getStudent_ID());
							v.setParameter(2, "Active");
							List<StudentClass> studentclass = (List<StudentClass>) v.getResultList();
							if (studentclass.size() > 0) {
								v = entitymanager.createQuery(
										"from Leaverequest where leaveDate=? and student_ID=? and status=?");
								v.setParameter(1, attendanceDataBean.getDate());
								v.setParameter(2, studentid);
								v.setParameter(3, "Active");
								List<Leaverequest> leaverequest = (List<Leaverequest>) v.getResultList();
								if (leaverequest.size() > 0) {
									status = "exist";
								} else {
									status = "not exist";
									Leaverequest leavereq = new Leaverequest();
									leavereq.setSchool(entitymanager.find(School.class, schoolid));
									leavereq.setStudent_ID(studentid);
									leavereq.setStatus("Active");
									leavereq.setApprovalStatus("Waiting");
									leavereq.setLeaveDate(attendanceDataBean.getDate());
									leavereq.setLeaveReason(attendanceDataBean.getLeavereason());
									leavereq.setParent(entitymanager.find(Parent.class, parent.get(0).getParent_ID()));
									leavereq.setClassName(studentclass.get(0).getClazz().getClassName() + "/"
											+ studentclass.get(0).getClazz().getClassSection());
									entitymanager.persist(leavereq);
								}
							}
						}
						else {
							logger.info("Student Size is Below Zero");

						}
					}
					else {
						logger.info("Parents Size is Below Zero");

					}
				}
			}
			else {
				logger.info("Person ID is Null");
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	private List<Noticeboard> getNoticeByPerson(String rollType, String follow, int school_ID) {
		logger.info("-----------Inside getNoticeByPerson method()----------------");
		Query q = null;
		List<Noticeboard> noticeBoardList = null;
		try {
			noticeBoardList = new ArrayList<Noticeboard>();
			if (!rollType.equalsIgnoreCase("NotValid")) {
				if (rollType.equalsIgnoreCase("Admin") || rollType.equalsIgnoreCase("Teacher")
						|| rollType.equalsIgnoreCase("Staff") || rollType.equalsIgnoreCase("Librarian")  || rollType.equalsIgnoreCase("BookShop")) {
					q = entitymanager.createQuery("from Noticeboard where school_ID=? and status='Active' order by createdDate desc");
					q.setParameter(1, school_ID);
					noticeBoardList = (List<Noticeboard>) q.getResultList();
				} else {
					q = entitymanager
							.createQuery("from Noticeboard where school_ID=? and (noticeFollower=? or noticeFollower='All') and status='Active' order by createdDate desc");
					q.setParameter(1, school_ID);
					q.setParameter(2, follow);
					noticeBoardList = (List<Noticeboard>) q.getResultList();
				}
				logger.debug("Notice Board Type" + noticeBoardList.size());
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			//e.printStackTrace();
		}
		return noticeBoardList;
	}
	/* Clinton OCT 25   getRoll*/
	@Override
	public String getRoll(String personID) {
		logger.info("-----------Inside getRoll method()----------------");
		String status = "Failed";
		List<Person> roll = null;
		String rollType = "Not Valid";
		//Query q = null;
		try {
			roll = new ArrayList<Person>();
			roll = getRollType(personID);
			if (personID != null) {
				rollType = roll.get(0).getPersonRole();
				logger.debug("-------roll size----" + roll.size());
				if (roll.size() > 0) {
					rollType = roll.get(0).getPersonRole();
					logger.debug("roll -- " + rollType);
					if (rollType.equalsIgnoreCase("Admin")) {
						status = "Admin";
					} else if (rollType.equalsIgnoreCase("Teacher")) {
						status = "Teacher";
					} else if (rollType.equalsIgnoreCase("Student")) {
						status = "Student";
					} else if (rollType.equalsIgnoreCase("Parent")) {
						status = "Parent";
					} else if (rollType.equalsIgnoreCase("Staff")) {
						status = "Staff";
					} else if (rollType.equalsIgnoreCase("Librarian")) {
						status = "Librarian";
					}else if (rollType.equalsIgnoreCase("BookShop")) {
						status = "BookShop";
					}
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.debug("---------outside getroll-----");
		return status;
	}

	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	public String attendancePercentage(String personID, AttendanceDataBean attendanceDataBean) {
		logger.info("-----------Inside attendancePercentage method()----------------");
		Query v = null;
		List<Person> person = null;
		//String status = "";
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					
					StringTokenizer stringtoken = new StringTokenizer(attendanceDataBean.getClassname());
					String classname = stringtoken.nextToken("/");
					String section = attendanceDataBean.getClassname();
					String sectionName = section.substring(section.lastIndexOf("/") + 1);
					int schoolid = person.get(0).getSchool().getSchool_ID();
					v = entitymanager.createQuery("from Attendance where school_ID=?");
					v.setParameter(1, schoolid);
					List<Attendance> attendance = (List<Attendance>) v.getResultList();
					if (attendance.size() > 0) {
						int c = 0, c1 = 0, c2 = 0, c3 = 0;
						int size = 0;
						v = null;
						v = entitymanager.createQuery(
								"from Attendanceclass where studentName=? and student_ID=? and month=? and year=?");
						v.setParameter(1, attendanceDataBean.getStudentName());
						v.setParameter(2, attendanceDataBean.getStudentID());
						v.setParameter(3, attendanceDataBean.getMonthyear());
						v.setParameter(4, attendanceDataBean.getYear());
						List<Attendanceclass> attendanceclass = (List<Attendanceclass>) v.getResultList();
						logger.debug("attendance class size -- " + attendanceclass.size());
						size = attendanceclass.size();
						if (attendanceclass.size() > 0) {
							for (int j = 0; j < attendanceclass.size(); j++) {
								if (attendanceclass.get(j).getStatus().equals("Absent")) {
									c++;
								}/* else if (attendanceclass.get(j).getStatus().equals("Leave")) {
									c1++;
								} */else if (attendanceclass.get(j).getStatus().equals("Present")) {
									c2++;
								} else if (attendanceclass.get(j).getStatus().equals("Late")) {
									c3++;
								}
							}
						}
						logger.debug("c "+c+" - "+c2+" - "+c3+" - "+size);
						BigDecimal perc = BigDecimal.valueOf(0);
						if (size == c2) {
							logger.debug("100 % ");
							perc = BigDecimal.valueOf(100);
						}else if(size == (c2+c3)) {
							perc = BigDecimal.valueOf(100);
						} else {
							float v1 = Float.parseFloat("" + c2) / Float.parseFloat("" + size);
							perc = BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(100));
							perc = perc.setScale(1, BigDecimal.ROUND_HALF_UP);
						}
						attendanceDataBean.setPercentage("" + perc + "%");
						v = entitymanager
								.createQuery("from Attendance where school_ID=? and date=? and class_=? and section=?");
						v.setParameter(1, schoolid);
						v.setParameter(2, attendanceDataBean.getDate());
						v.setParameter(3, classname);
						v.setParameter(4, sectionName);
						List<Attendance> attendances = (List<Attendance>) v.getResultList();
						if (attendances.size() > 0) {
							v = null;
							v = entitymanager.createQuery(
									"from Attendanceclass where attendance_ID=? and studentName=? and student_ID=?");
							v.setParameter(1, attendances.get(0).getAttendance_ID());
							v.setParameter(2, attendanceDataBean.getStudentName());
							v.setParameter(3, attendanceDataBean.getStudentID());
							List<Attendanceclass> attendanceclasses = (List<Attendanceclass>) v.getResultList();
							if (attendanceclasses.size() > 0) {
								for (int i = 0; i < attendanceclasses.size(); i++) {
									Attendanceclass percen = entitymanager.find(Attendanceclass.class,
											attendanceclasses.get(i).getAtt_class_ID());
									percen.setPercentage("" + perc + "%");
									entitymanager.merge(percen);
									entitymanager.flush();
									entitymanager.clear();
								}
							}
						}

					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public String studentList(String personID, AttendanceDataBean attendanceDataBean) {
		logger.info("-----------Inside studentList method()----------------");
		Query v = null;
		List<Person> person = null;
		String days = "";
		String st = "";
		String et = "";
		String stime = "";
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		List<AttendanceDataBean> attendancedata = new ArrayList<AttendanceDataBean>();
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					StringTokenizer stringtoken = new StringTokenizer(attendanceDataBean.getClassname());
					String classname = stringtoken.nextToken("/");
					String section = attendanceDataBean.getClassname();
					String sectionName = section.substring(section.lastIndexOf("/") + 1);
					int schoolid = person.get(0).getSchool().getSchool_ID();			
					String day = "";
					if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
						day=String.valueOf(GetTimeZone.getDate("Asia/Makassar").getDay());
					}else{
						day=String.valueOf(date.getDay());
					}
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						if (day.equals("0"))
							days = "Sunday";
					}
					if (day.equals("1"))
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
					if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
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

					v = entitymanager.createQuery(
							"from Class where school_ID=? and className=? and classSection=? and status=?");
					v.setParameter(1, schoolid);
					v.setParameter(2, classname);
					v.setParameter(3, sectionName);
					v.setParameter(4, "Active");
					List<Class> classes = (List<Class>) v.getResultList();
					if (classes.size() > 0) {
						int classid = classes.get(0).getClass_ID();
						v = null;
						v = entitymanager.createQuery("from StudentClass where class_ID=? and status=?");
						v.setParameter(1, classid);
						v.setParameter(2, "Active");
						List<StudentClass> studentclass = (List<StudentClass>) v.getResultList();
						if (studentclass.size() > 0) {
							int i1 = 1;
							for (int i = 0; i < studentclass.size(); i++) {
								v = null;
								v = entitymanager.createQuery("from StudentDetail where student_ID=?");
								v.setParameter(1, studentclass.get(i).getStudent().getStudent_ID());
								List<StudentDetail> studentdetail = (List<StudentDetail>) v.getResultList();
								if (studentdetail.size() > 0) {
									if(studentclass.get(i).getStudent().getSchool().getSchool_ID()==schoolid){
										AttendanceDataBean attendancelist = new AttendanceDataBean();
										attendancelist.setStudentName(
												studentdetail.get(0).getFirstName() + studentdetail.get(0).getLastName());
										attendancelist.setStudentID(studentclass.get(i).getStudent().getRollNumber());
										if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
											attendancelist.setDate(GetTimeZone.getDate("Asia/Makassar"));
										}else{
											attendancelist.setDate(date);
										}	
										v = entitymanager
												.createQuery("from Teacher where school_ID=? and person_ID=? and status=?");
										v.setParameter(1, schoolid);
										v.setParameter(2, personID);
										v.setParameter(3, "Active");
										List<Teacher> teacher = (List<Teacher>) v.getResultList();
										if (teacher.size() > 0) {
											v = null;
											v = entitymanager
													.createQuery("from ClassTable where class_ID=? and day=? and year=? and status='Active'");
											v.setParameter(1, classes.get(0).getClass_ID());
											v.setParameter(2, days);
											v.setParameter(3, String.valueOf(year));
											List<ClassTable> classtable = (List<ClassTable>) v.getResultList();
											if (classtable.size() > 0) {
												v = null;
												v = entitymanager.createQuery(
														"from ClassTimeTable where teacher_ID=? and day=? and class_table_ID=? and ? between startTime and endTime");
												v.setParameter(1, teacher.get(0).getTeacher_ID());
												v.setParameter(2, days);
												v.setParameter(3, classtable.get(0).getClass_table_ID());
												v.setParameter(4, stime);
												List<ClassTimeTable> classTimeTable = (List<ClassTimeTable>) v
														.getResultList();
												if (classTimeTable.size() > 0) {
													v = null;
													v = entitymanager.createQuery(
															"from Attendance where school_ID=? and date=? and class_=? and section=?");
													v.setParameter(1, schoolid);
													if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
														v.setParameter(2, GetTimeZone.getDate("Asia/Makassar"));
													}else{
														v.setParameter(2, date);
													}												
													v.setParameter(3, classname);
													v.setParameter(4, sectionName);
													List<Attendance> attendance = (List<Attendance>) v.getResultList();
													if (attendance.size() > 0) {
														v = null;
														v = entitymanager.createQuery(
																"from Attendanceclass where attendance_ID=? and studentName=? and student_ID=? and teacher_ID=? and class_time_table_ID=? and period=?");
														v.setParameter(1, attendance.get(0).getAttendance_ID());
														v.setParameter(2, studentdetail.get(0).getFirstName()
																+ studentdetail.get(0).getLastName());
														v.setParameter(3, studentclass.get(i).getStudent().getRollNumber());
														v.setParameter(4, teacher.get(0).getTeacher_ID());
														v.setParameter(5, classTimeTable.get(0).getClass_time_table_ID());
														v.setParameter(6, classTimeTable.get(0).getPeriod());
														List<Attendanceclass> attendanceclass = (List<Attendanceclass>) v
																.getResultList();
														logger.debug("size - " + attendanceclass.size());
														if (attendanceclass.size() > 0) {
															attendancelist.setStatus(attendanceclass.get(0).getStatus());
															attendancelist
																	.setPercentage(attendanceclass.get(0).getPercentage());
															attendancelist.setFlag(false);
															attendancelist.setFlag1(true);
														} else {
															v = entitymanager.createQuery(
																	"from Attendanceclass where attendance_ID=? and student_ID=? and period=?");
															v.setParameter(1, attendance.get(0).getAttendance_ID());
															v.setParameter(2,
																	studentclass.get(i).getStudent().getRollNumber());
															v.setParameter(3, classTimeTable.get(0).getPeriod());
															List<Attendanceclass> attendanceclass1 = (List<Attendanceclass>) v
																	.getResultList();
															if (attendanceclass1.size() > 0) {
																attendancelist
																		.setStatus(attendanceclass1.get(0).getStatus());
																attendancelist.setFlag(false);
																attendancelist.setPercentage(
																		attendanceclass1.get(0).getPercentage());
																attendancelist.setFlag1(true);
															} else {
																attendancelist.setStatus("");
																attendancelist.setPercentage("");
																attendancelist.setFlag1(false);
																attendancelist.setFlag(true);
															}
														}
													} else {
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
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return null;
	}

	public String getStaffRollNumber(String personID, LoginAccess loginaccess) {
		logger.info("-----------Inside getStaffRollNumber method()----------------");
		List<Person> roll = null;
		String rollType = "Not Valid";
		String staffRollNumber = "";
		Query q = null;
		try {
			roll = new ArrayList<Person>();
			roll = getRollType(personID);
			if (personID != null) {
				rollType = roll.get(0).getPersonRole();
				logger.debug("-------roll size----" + roll.size());
				if (roll.size() > 0) {
					rollType = roll.get(0).getPersonRole();
					staffRollNumber = roll.get(0).getPersonRoleNumber();
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			//logger.warn("Exception -->"+e.getMessage());
		}
		return staffRollNumber;

	}

	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	@Override
	public String insertStudentPerform(String personID, StudentPerformanceDataBean studentPerformanceDataBean) {
		logger.info("-----------Inside insertStudentPerform method()----------------");
		String status = "Failure";
		List<Person> roleStatus = null;
		int school_ID = 0;
		int studid = 0;String days=""; 
		int schlid = 0;Calendar now = Calendar.getInstance();
		Query q = null;List<String> phonenos = new ArrayList<String>();
		List<String> mails = new ArrayList<String>(); String st = "";
		String et = "";String subname="";String subcode="";
		String stime = "";
		String schoolName="";
		try {
		
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
		if (roleStatus.size() > 0) {
			logger.info("Role status is  > 0");
			school_ID = roleStatus.get(0).getSchool().getSchool_ID();
			schoolName = roleStatus.get(0).getSchool().getName();
			studentPerformanceDataBean.setSchoolLogo(roleStatus.get(0).getSchool().getLogopath());
			studentPerformanceDataBean.setSchoolName(roleStatus.get(0).getSchool().getName());
			studentPerformanceDataBean.setSchoolID(roleStatus.get(0).getSchool().getSchool_ID());
			String day = "";
		if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
			day=String.valueOf(studentPerformanceDataBean.getTeaDob().getDay());
		}else{
			day=String.valueOf(studentPerformanceDataBean.getTeaDob().getDay());
		}
		logger.debug("day "+day+" role "+roleStatus.get(0).getPersonRole());
		if (roleStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {						
			String studentid=studentPerformanceDataBean.getPerformStudID();
			String stuid = studentid.substring(studentid.lastIndexOf("/") + 1);
			logger.debug("id "+studid);
			q = entitymanager.createQuery("from Student where roll_number=? and school_ID=? and status='Active'");
			q.setParameter(1,stuid );
			q.setParameter(2, roleStatus.get(0).getSchool().getSchool_ID());
			List<Student> studlist = (List<Student>) q.getResultList();
			if (studlist.size() > 0) {
				Query v = null;
				v = entitymanager.createQuery("from StudentParent where student_ID=? and status=?");
				v.setParameter(1, studlist.get(0).getStudent_ID());
				v.setParameter(2, "Active");
				List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();
				if (studentParent.size() > 0) {
					mails.add(studentParent.get(0).getParent().getParentDetail().getEmaiId());
					phonenos.add(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
					studentPerformanceDataBean.setParName(studentParent.get(0).getParent().getParentDetail().getFirstName());
				}if(school_ID==1 || school_ID==2 || school_ID==3|| school_ID==4 || school_ID==5 || school_ID==6)
				{
				}else{
					v = null;
					v = entitymanager.createQuery("from StudentDetail where student_ID=?");
					v.setParameter(1, studlist.get(0).getStudent_ID());
					List<StudentDetail> studentDetail = (List<StudentDetail>) v.getResultList();
					if (studentDetail.size() > 0) {
						mails.add(studentDetail.get(0).getEmailId());
					}
				}
				
				studentPerformanceDataBean.setMailid(mails);
				studentPerformanceDataBean.setPhonenos(phonenos);
				studid = studlist.get(0).getStudent_ID();
				q = null;
				q = entitymanager.createQuery("from StudentPerformance where student_ID=? and createDate=?  and status='Active'");
				q.setParameter(1, studid);
				q.setParameter(2, studentPerformanceDataBean.getTeaDob());
				List<StudentPerformance> plist = (List<StudentPerformance>) q.getResultList();
				
				if (plist.size() > 0) {
					status = "failure";
				} else {
					StudentPerformance perobj = new StudentPerformance();
					perobj.setSchool(entitymanager.find(School.class, school_ID));
					perobj.setStudent(entitymanager.find(Student.class, studid));
					perobj.setAppearanceOtherStatus(studentPerformanceDataBean.isAppearancecheck());
					perobj.setAttitudeOtherStatus(studentPerformanceDataBean.isAttitudecheck());
					perobj.setAppearanceOther(studentPerformanceDataBean.getAppOthers());
					perobj.setAttitudeOther(studentPerformanceDataBean.getAttOthers());
					perobj.setCreateDate(studentPerformanceDataBean.getTeaDob());
					
					if (Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
							.replace("[", "").replace("]", "").trim().equals("")
							&& studentPerformanceDataBean.getAppOthers().equals("")) {
						perobj.setAppearanceStatus("");
					} else if (!Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
							.replace("[", "").replace("]", "").trim().equals("")
							&& studentPerformanceDataBean.getAppOthers().equals("")) {
						perobj.setAppearanceStatus(
								Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
										.replace("[", "").replace("]", "").trim());
					} else if (Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
							.replace("[", "").replace("]", "").trim().equals("")
							&& !studentPerformanceDataBean.getAppOthers().equals("")) {
						perobj.setAppearanceStatus(studentPerformanceDataBean.getAppOthers());
					} else if (!Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
							.replace("[", "").replace("]", "").trim().equals("")
							&& !studentPerformanceDataBean.getAppOthers().equals("")) {
						perobj.setAppearanceStatus(
								Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
										.replace("[", "").replace("]", "").trim() + ","
										+ studentPerformanceDataBean.getAppOthers());
					}
					if (Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
							.replace("[", "").replace("]", "").trim().equals("")
							&& studentPerformanceDataBean.getAttOthers().equals("")) {
						perobj.setAttitudeStatus("");
					} else if (!Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
							.replace("[", "").replace("]", "").trim().equals("")
							&& studentPerformanceDataBean.getAttOthers().equals("")) {
						perobj.setAttitudeStatus(
								Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
										.replace("[", "").replace("]", "").trim());
					} else if (Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
							.replace("[", "").replace("]", "").trim().equals("")
							&& !studentPerformanceDataBean.getAttOthers().equals("")) {
						perobj.setAttitudeStatus(studentPerformanceDataBean.getAttOthers());
					} else if (!Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
							.replace("[", "").replace("]", "").trim().equals("")
							&& !studentPerformanceDataBean.getAttOthers().equals("")) {
						perobj.setAttitudeStatus(
								Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
										.replace("[", "").replace("]", "").trim() + ","
										+ studentPerformanceDataBean.getAttOthers());
					}
					String[] arr1 = perobj.getAttitudeStatus().split(" ");
					String[] arr = perobj.getAppearanceStatus().split(" ");
					studentPerformanceDataBean.setStudentAppearance(arr);
					studentPerformanceDataBean.setStudentAttitude(arr1);
					studentPerformanceDataBean.setStuApp(perobj.getAttitudeStatus());
					studentPerformanceDataBean.setStuAtt(perobj.getAppearanceStatus());
					perobj.setStatus("Active");
					entitymanager.persist(perobj);
					status = "Success";
				}
			}
		} else if (roleStatus.get(0).getPersonRole().equalsIgnoreCase("Teacher")) {
			logger.info("------------------------ Inside Teacher ------------------------");
			String studentid=studentPerformanceDataBean.getPerformStudID();
			String stuid = studentid.substring(studentid.lastIndexOf("/") + 1);
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
			if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
				st= GetTimeZone.getTimeHour("Asia/Makassar");
				et=GetTimeZone.getTimeMin("Asia/Makassar");
				logger.info("start time-----"+st);
				logger.info("--- End Time -----"+et);
			}else if(school_ID==35 || school_ID==36 || school_ID==37 || school_ID==38 || school_ID==39 || school_ID==40 || school_ID==41 || school_ID==42 || school_ID==43){
				st = String.valueOf(now.getTime().getHours());
				et = String.valueOf(now.getTime().getMinutes());
			}else{
				st= GetTimeZone.getTimeHour("Asia/Makassar");
				et=GetTimeZone.getTimeMin("Asia/Makassar");
				logger.info("-- Lombok start time-----"+st);
				logger.info("--- Lombok End Time -----"+et);
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
			q=entitymanager.createQuery("from Teacher where roll_number=? and school_ID=? and status='Active'");
			q.setParameter(1, roleStatus.get(0).getPersonRoleNumber());
			q.setParameter(2, roleStatus.get(0).getSchool().getSchool_ID());
			List<Teacher> teacherList=(List<Teacher>)q.getResultList();
			logger.info("------------------------ Teacher List ------------------------"+teacherList.size());
			if(teacherList.size()>0){
				logger.info("------------------------ School ID ------------------------"+roleStatus.get(0).getSchool().getSchool_ID());
				logger.info("------------------------ Teacher ID ------------------------"+teacherList.get(0).getTeacher_ID());
				logger.info("------------------------ Days ------------------------"+days);
				logger.info("------------------------ Start Time ------------------------"+stime);
		
				/*q=null;
				q=entitymanager.createQuery("from ClassTimeTable where school_ID=? and teacher_ID=? and day=? and ? between startTime and endTime");
				q.setParameter(1, roleStatus.get(0).getSchool().getSchool_ID());
				q.setParameter(2, teacherList.get(0).getTeacher_ID());
				q.setParameter(3, days);
				q.setParameter(4, stime);
				List<ClassTimeTable> classtimetablelist=(List<ClassTimeTable>)q.getResultList();
				logger.info("------------------------ ClassTimeTable List ------------------------"+classtimetablelist.size());
				logger.info("------------------------ subname ------------------------"+subname);
				logger.info("------------------------ subcode ------------------------"+subcode);
				
				if(classtimetablelist.size()>0){
					subname=classtimetablelist.get(0).getSubject(); 
					subcode=classtimetablelist.get(0).getSubjectCode();*/
				logger.info("------------------------ Roll Number for Student ------------------------"+stuid);
					q=null;
					q = entitymanager.createQuery("from Student where roll_number=? and school_ID=? and status='Active'");
					q.setParameter(1, stuid);
					q.setParameter(2, roleStatus.get(0).getSchool().getSchool_ID());
					List<Student> studlist = (List<Student>) q.getResultList();
					logger.info("------------------------ Student List ------------------------"+studlist.size());
					logger.info("------------------------ Student ID ------------------------"+studlist.get(0).getStudent_ID());
					if (studlist.size() > 0) {
						Query v = null;
						v = entitymanager.createQuery("from StudentParent where student_ID=? and status=?");
						v.setParameter(1, studlist.get(0).getStudent_ID());
						v.setParameter(2, "Active");
						List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();
						logger.info("------------------------ StudentParent List ------------------------"+studentParent.size());
		
						if (studentParent.size() > 0) {
							mails.add(studentParent.get(0).getParent().getParentDetail().getEmaiId());
							phonenos.add(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
							studentPerformanceDataBean.setParName(studentParent.get(0).getParent().getParentDetail().getFirstName());
						}
						if(school_ID==1 || school_ID==2 || school_ID==3|| school_ID==4 || school_ID==5 || school_ID==6)
						{
						}else{
							v = null;
							v = entitymanager.createQuery("from StudentDetail where student_ID=?");
							v.setParameter(1, studlist.get(0).getStudent_ID());
							List<StudentDetail> studentDetail = (List<StudentDetail>) v.getResultList();
							logger.info("------------------------ StudentDetail List ------------------------"+studentDetail.size());
		
							if (studentDetail.size() > 0) {
								mails.add(studentDetail.get(0).getEmailId());
							}
							else {
								System.out.println("No teacher details found !!!");
							}
						}
						studentPerformanceDataBean.setMailid(mails);
						studentPerformanceDataBean.setPhonenos(phonenos);
						studid = studlist.get(0).getStudent_ID();
						schlid = studlist.get(0).getSchool().getSchool_ID();
						q = null;
						q = entitymanager.createQuery("from StudentPerformance where student_ID=? and school_ID=? and createDate=? and subjectName=? and subjectCode=? and status='Active'");
						q.setParameter(1, studid);
						q.setParameter(2, schlid);
						q.setParameter(3, studentPerformanceDataBean.getTeaDob());
						q.setParameter(4, subname);
						q.setParameter(5, subcode);
						List<StudentPerformance> plist = (List<StudentPerformance>) q.getResultList();
						logger.info("------------------------ StudentPerformance List ------------------------"+plist.size());
		
						if (plist.size() > 0) {
							status = "failure";
						} else {
							logger.info("------------------------ No StudentPerformance else condition ------------------------");
		
							StudentPerformance perobj = new StudentPerformance();
							perobj.setSchool(entitymanager.find(School.class, schlid));
							perobj.setStudent(entitymanager.find(Student.class, studid));
							perobj.setAppearanceOtherStatus(studentPerformanceDataBean.isAppearancecheck());
							perobj.setAttitudeOtherStatus(studentPerformanceDataBean.isAttitudecheck());
							perobj.setAppearanceOther(studentPerformanceDataBean.getAppOthers());
							perobj.setAttitudeOther(studentPerformanceDataBean.getAttOthers());
							perobj.setCreateDate(studentPerformanceDataBean.getTeaDob());					
							perobj.setSubjectName(subname);
							perobj.setSubjectCode(subcode);
							if (Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
									.replace("[", "").replace("]", "").trim().equals("")
									&& studentPerformanceDataBean.getAppOthers().equals("")) {
								perobj.setAppearanceStatus("");
							} else if (!Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
									.replace("[", "").replace("]", "").trim().equals("")
									&& studentPerformanceDataBean.getAppOthers().equals("")) {
								perobj.setAppearanceStatus(
										Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
										.replace("[", "").replace("]", "").trim());
							} else if (Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
									.replace("[", "").replace("]", "").trim().equals("")
									&& !studentPerformanceDataBean.getAppOthers().equals("")) {
								perobj.setAppearanceStatus(studentPerformanceDataBean.getAppOthers());
							} else if (!Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
									.replace("[", "").replace("]", "").trim().equals("")
									&& !studentPerformanceDataBean.getAppOthers().equals("")) {
								perobj.setAppearanceStatus(
										Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
											.replace("[", "").replace("]", "").trim() + ","
											+ studentPerformanceDataBean.getAppOthers());
							}
							if (Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
									.replace("[", "").replace("]", "").trim().equals("")
									&& studentPerformanceDataBean.getAttOthers().equals("")) {
								perobj.setAttitudeStatus("");
							} else if (!Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
									.replace("[", "").replace("]", "").trim().equals("")
									&& studentPerformanceDataBean.getAttOthers().equals("")) {
								perobj.setAttitudeStatus(
										Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
											.replace("[", "").replace("]", "").trim());
							} else if (Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
									.replace("[", "").replace("]", "").trim().equals("")
									&& !studentPerformanceDataBean.getAttOthers().equals("")) {
								perobj.setAttitudeStatus(studentPerformanceDataBean.getAttOthers());
							} else if (!Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
									.replace("[", "").replace("]", "").trim().equals("")
									&& !studentPerformanceDataBean.getAttOthers().equals("")) {
								perobj.setAttitudeStatus(
										Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
											.replace("[", "").replace("]", "").trim() + ","
											+ studentPerformanceDataBean.getAttOthers());
							}
							String[] arr1 = perobj.getAttitudeStatus().split(" ");
							String[] arr = perobj.getAppearanceStatus().split(" ");
							studentPerformanceDataBean.setStudentAppearance(arr);
							studentPerformanceDataBean.setStudentAttitude(arr1);
							studentPerformanceDataBean.setStuApp(perobj.getAttitudeStatus());
							studentPerformanceDataBean.setStuAtt(perobj.getAppearanceStatus());
							perobj.setStatus("Active");
							entitymanager.persist(perobj);
							logger.info("------------------------ Successfully saved ------------------------");
		
							status = "Success";
						}
					}
				/*}else{
					logger.info("------------------------ Status failure  ------------------------");
		
					status = "Fail";
				}*/
			}
			}
		if(studentPerformanceDataBean.getPhonenos().get(0) != null){
			logger.info("------------------------ Send Mail  ------------------------");
		
			MailSendJDBC.behaviourphonesms(studentPerformanceDataBean, schoolName, school_ID);	
		
		}
		/*if (studentPerformanceDataBean.getMailid().get(0) != null) {
			MailSendJDBC.behaviouremail(studentPerformanceDataBean, schoolName, school_ID);	
		}*/
		/*if (studentPerformanceDataBean.getPhonenos().size()>0) {
			MailSendJDBC.behaviourphonesms(studentPerformanceDataBean, schoolName, school_ID);	
		}*/
		else {
			logger.info("NO Phone number found !!!");
					}
					
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		
		}
		return status;
	}
	
	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	@Override
	public String getPerformEdit(String personID, StudentPerformanceDataBean studentPerformanceDataBean) {
		logger.info("-----------Inside getPerformEdit method()----------------");
		String status = "Failed";
		Query q = null;
		int studid = 0;
		int perid = 0;
		List<Person> roleStatus = null;
		String rollType = "NotValid";
		int school_ID = 0;List<String> phonenos = new ArrayList<String>();
		List<String> mails = new ArrayList<String>();
		try {
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("-------Roll Size-----" + roleStatus.size());
				if (roleStatus.size() > 0) {
					studentPerformanceDataBean.setSchoolLogo(roleStatus.get(0).getSchool().getLogopath());
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					studentPerformanceDataBean.setSchoolName(roleStatus.get(0).getSchool().getName());
					logger.debug("school id "+school_ID);
					if (school_ID > 0) {
						logger.debug("roll "+rollType);
						if (rollType.equalsIgnoreCase("Admin")) {
							String student=studentPerformanceDataBean.getStuName();
							String studentname=student.substring(student.lastIndexOf("/")+1);
							q = entitymanager.createQuery("from Student where roll_number=? and school_ID=? and status='Active'");
							q.setParameter(1, studentname);
							q.setParameter(2, roleStatus.get(0).getSchool().getSchool_ID());
							List<Student> studlist = (List<Student>) q.getResultList();
							if (studlist.size() > 0) {
								studid = studlist.get(0).getStudent_ID();
								Query v = null;
								v = entitymanager.createQuery("from StudentParent where student_ID=? and status=?");
								v.setParameter(1, studlist.get(0).getStudent_ID());
								v.setParameter(2, "Active");
								List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();
								if (studentParent.size() > 0) {
									mails.add(studentParent.get(0).getParent().getParentDetail().getEmaiId());
									phonenos.add(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
								}
								if(school_ID==1 || school_ID==2 || school_ID==3|| school_ID==4 || school_ID==5 || school_ID==6)
								{
								}else{
									v = null;
									v = entitymanager.createQuery("from StudentDetail where student_ID=?");
									v.setParameter(1, studlist.get(0).getStudent_ID());
									List<StudentDetail> studentDetail = (List<StudentDetail>) v.getResultList();
									if (studentDetail.size() > 0) {
										mails.add(studentDetail.get(0).getEmailId());
									}
								}
								
								studentPerformanceDataBean.setMailid(mails);
								studentPerformanceDataBean.setPhonenos(phonenos);
							}
							q = null;
							q = entitymanager.createQuery("from StudentPerformance where student_ID=? and createDate=?");
							q.setParameter(1, studid);
							q.setParameter(2, studentPerformanceDataBean.getBdate());
							logger.debug("----stud id------" + studid);
							List<StudentPerformance> studlist1 = (List<StudentPerformance>) q.getResultList();
							logger.debug("-----update list size------" + studlist1.size());
							if (studlist1.size() > 0) {
								perid = studlist1.get(0).getPerformance_ID();
								StudentPerformance studobj = entitymanager.find(StudentPerformance.class, perid);
								studobj.setAppearanceOtherStatus(studentPerformanceDataBean.isAppearancecheck());
								studobj.setAttitudeOtherStatus(studentPerformanceDataBean.isAttitudecheck());
								studobj.setAppearanceOther(studentPerformanceDataBean.getAppOthers());
								studobj.setAttitudeOther(studentPerformanceDataBean.getAttOthers());
								logger.debug("apperannce - "
										+ Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance()) + " - "
										+ studentPerformanceDataBean.getAppOthers());
								try {
									if (studentPerformanceDataBean.getAppOthers().equals("")) {

									}
								} catch (Exception e) {
									studentPerformanceDataBean.setAppOthers("");
								}
								try {
									if (studentPerformanceDataBean.getAttOthers().equals("")) {

									}
								} catch (Exception e) {
									studentPerformanceDataBean.setAttOthers("");
								}
								if (Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
										.replace("[", "").replace("]", "").trim().equals("")
										&& studentPerformanceDataBean.getAppOthers().equals("")) {
									studobj.setAppearanceStatus("");
								} else if (!Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
										.replace("[", "").replace("]", "").trim().equals("")
										&& studentPerformanceDataBean.getAppOthers().equals("")) {
									studobj.setAppearanceStatus(
											Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
													.replace("[", "").replace("]", "").trim());
								} else if (Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
										.replace("[", "").replace("]", "").trim().equals("")
										&& !studentPerformanceDataBean.getAppOthers().equals("")) {
									studobj.setAppearanceStatus(studentPerformanceDataBean.getAppOthers());
								} else if (!Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
										.replace("[", "").replace("]", "").trim().equals("")
										&& !studentPerformanceDataBean.getAppOthers().equals("")) {
									studobj.setAppearanceStatus(
											Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
													.replace("[", "").replace("]", "").trim() + ","
													+ studentPerformanceDataBean.getAppOthers());
								}
								if (Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
										.replace("[", "").replace("]", "").trim().equals("")
										&& studentPerformanceDataBean.getAttOthers().equals("")) {
									studobj.setAttitudeStatus("");
								} else if (!Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
										.replace("[", "").replace("]", "").trim().equals("")
										&& studentPerformanceDataBean.getAttOthers().equals("")) {
									studobj.setAttitudeStatus(
											Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
													.replace("[", "").replace("]", "").trim());
								} else if (Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
										.replace("[", "").replace("]", "").trim().equals("")
										&& !studentPerformanceDataBean.getAttOthers().equals("")) {
									studobj.setAttitudeStatus(studentPerformanceDataBean.getAttOthers());
								} else if (!Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
										.replace("[", "").replace("]", "").trim().equals("")
										&& !studentPerformanceDataBean.getAttOthers().equals("")) {
									studobj.setAttitudeStatus(
											Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
													.replace("[", "").replace("]", "").trim() + ","
													+ studentPerformanceDataBean.getAttOthers());
								}
								entitymanager.merge(studobj);
								status = "Success";
							}
						} else if (rollType.equalsIgnoreCase("Teacher")) {
							String student=studentPerformanceDataBean.getStuName();
							String studentname=student.substring(student.lastIndexOf("/")+1);
							q = entitymanager.createQuery("from Student where roll_number=? and school_ID=? and status='Active'");
							q.setParameter(1, studentname);
							q.setParameter(2, roleStatus.get(0).getSchool().getSchool_ID());
							List<Student> studlist = (List<Student>) q.getResultList();
							if (studlist.size() > 0) {
								studid = studlist.get(0).getStudent_ID();
								Query v = null;
								v = entitymanager.createQuery("from StudentParent where student_ID=? and status=?");
								v.setParameter(1, studlist.get(0).getStudent_ID());
								v.setParameter(2, "Active");
								List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();
								if (studentParent.size() > 0) {
									mails.add(studentParent.get(0).getParent().getParentDetail().getEmaiId());
									phonenos.add(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
								}
								if(school_ID==1 || school_ID==2 || school_ID==3|| school_ID==4 || school_ID==5 || school_ID==6)
								{
								}else{
									v = null;
									v = entitymanager.createQuery("from StudentDetail where student_ID=?");
									v.setParameter(1, studlist.get(0).getStudent_ID());
									List<StudentDetail> studentDetail = (List<StudentDetail>) v.getResultList();
									if (studentDetail.size() > 0) {
										mails.add(studentDetail.get(0).getEmailId());
									}
								}
							
								studentPerformanceDataBean.setMailid(mails);
								studentPerformanceDataBean.setPhonenos(phonenos);
							}
							q = null;
							q = entitymanager.createQuery("from StudentPerformance where student_ID=? and createDate=?");
							q.setParameter(1, studid);
							q.setParameter(2, studentPerformanceDataBean.getBdate());
							logger.debug("----stud id------" + studid);
							List<StudentPerformance> studlist1 = (List<StudentPerformance>) q.getResultList();
							logger.debug("-----update list size------" + studlist1.size());
							if (studlist1.size() > 0) {
								perid = studlist1.get(0).getPerformance_ID();
								StudentPerformance studobj = entitymanager.find(StudentPerformance.class, perid);
								studobj.setAppearanceOtherStatus(studentPerformanceDataBean.isAppearancecheck());
								studobj.setAttitudeOtherStatus(studentPerformanceDataBean.isAttitudecheck());
								studobj.setAppearanceOther(studentPerformanceDataBean.getAppOthers());
								studobj.setAttitudeOther(studentPerformanceDataBean.getAttOthers());
								try {
									if (studentPerformanceDataBean.getAppOthers().equals("")) {

									}
								} catch (Exception e) {
									studentPerformanceDataBean.setAppOthers("");
								}
								try {
									if (studentPerformanceDataBean.getAttOthers().equals("")) {

									}
								} catch (Exception e) {
									studentPerformanceDataBean.setAttOthers("");
								}
								if (Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
										.replace("[", "").replace("]", "").trim().equals("")
										&& studentPerformanceDataBean.getAppOthers().equals("")) {
									studobj.setAppearanceStatus("");
								} else if (!Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
										.replace("[", "").replace("]", "").trim().equals("")
										&& studentPerformanceDataBean.getAppOthers().equals("")) {
									studobj.setAppearanceStatus(
											Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
													.replace("[", "").replace("]", "").trim());
								} else if (Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
										.replace("[", "").replace("]", "").trim().equals("")
										&& !studentPerformanceDataBean.getAppOthers().equals("")) {
									studobj.setAppearanceStatus(studentPerformanceDataBean.getAppOthers());
								} else if (!Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
										.replace("[", "").replace("]", "").trim().equals("")
										&& !studentPerformanceDataBean.getAppOthers().equals("")) {
									studobj.setAppearanceStatus(
											Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
													.replace("[", "").replace("]", "").trim() + ","
													+ studentPerformanceDataBean.getAppOthers());
								}
								if (Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
										.replace("[", "").replace("]", "").trim().equals("")
										&& studentPerformanceDataBean.getAttOthers().equals("")) {
									studobj.setAttitudeStatus("");
								} else if (!Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
										.replace("[", "").replace("]", "").trim().equals("")
										&& studentPerformanceDataBean.getAttOthers().equals("")) {
									studobj.setAttitudeStatus(
											Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
													.replace("[", "").replace("]", "").trim());
								} else if (Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
										.replace("[", "").replace("]", "").trim().equals("")
										&& !studentPerformanceDataBean.getAttOthers().equals("")) {
									studobj.setAttitudeStatus(studentPerformanceDataBean.getAttOthers());
								} else if (!Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
										.replace("[", "").replace("]", "").trim().equals("")
										&& !studentPerformanceDataBean.getAttOthers().equals("")) {
									studobj.setAttitudeStatus(
											Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())
													.replace("[", "").replace("]", "").trim() + ","
													+ studentPerformanceDataBean.getAttOthers());
								}
								entitymanager.merge(studobj);
								status = "Success";
							}
						} else {
							status = "Not Valid";
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			//e.printStackTrace();
			logger.warn("Inside Exception",e);
		}
		logger.debug("---------outside performEdit------");
		return status;
	}

	@SuppressWarnings("unchecked")
	public String tecaherCheck(ClassTimeTableDataBean classTimeTableDataBean, int schoolid, String starttime, String endtime, int year){
		logger.info("-----------Inside tecaherCheck method()----------------");
		Query q=null;String status="";
		try{
			q=null;
			q=entitymanager.createQuery("from Teacher where rollNumber=? and school_ID=? and status='Active'");
			q.setParameter(1, classTimeTableDataBean.getTeaID());
			q.setParameter(2, schoolid);
			List<Teacher> teacherlist=(List<Teacher>)q.getResultList();
			logger.debug("teacher  "+teacherlist.size());
			if(teacherlist.size()>0){
				q=null;
				q=entitymanager.createQuery("from ClassTable where day=? and year=? and month=? and school_ID=? and status=?");
				q.setParameter(1, classTimeTableDataBean.getDay());
				q.setParameter(2, String.valueOf(year));
				q.setParameter(3, classTimeTableDataBean.getMonth());
				q.setParameter(4, schoolid);
				q.setParameter(5, "Active");
				List<ClassTable> classtablelist=(List<ClassTable>)q.getResultList();
				logger.debug("class table  "+classtablelist.size());
				if(classtablelist.size()>0){
				//starttime = st.format(classTimeTableDataBean.getStartTime());					
				int count=0;
					for (int i = 0; i < classtablelist.size(); i++) {
						q=null;
						q=entitymanager.createQuery("from ClassTimeTable where class_table_ID=? and teacher_ID=? and school_ID=? and ? between startTime and endTime");
						q.setParameter(1, classtablelist.get(i).getClass_table_ID());
						q.setParameter(2, teacherlist.get(0).getTeacher_ID());
						q.setParameter(3, schoolid);
						q.setParameter(4, starttime);
						List<ClassTimeTable> classtimetables=(List<ClassTimeTable>)q.getResultList();
						if(classtimetables.size()>0){
							for (int j = 0; j < classtimetables.size(); j++) {
								if(!classtimetables.get(j).getEndTime().equals(starttime)){
									if(classtimetables.get(j).getClass_time_table_ID()!=classTimeTableDataBean.getClasstableid()){
										count++;
									}
								}
							}												
						}
					}
					if(count>0){
						status="Fail";
					}else{
						status="Success";
					}
				}
			}
		}
		catch(Exception e){
			//e.printStackTrace();
		}
		return status;
	}
	
	@SuppressWarnings("unchecked")
	public String checkupdateClassTimeTable(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		logger.info("-----------Inside checkupdateClassTimeTable method()----------------");
		String status="";Query q=null;List<Person> roll = null;int year = 0;
		DateFormat st=new SimpleDateFormat("hh:mm");String starttime="";String endtime="";
		String starthour = "";
		String endhour = "";
		String endmin = "";
		String startmin = "";
		try{
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					StringTokenizer stringtoken = new StringTokenizer(classTimeTableDataBean.getClassname());
					String classname = stringtoken.nextToken("/");
					String section = classTimeTableDataBean.getClassname();
					String sectionName = section.substring(section.lastIndexOf("/") + 1);
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					q=entitymanager.createQuery("from Class where className=? and classSection=? and school_ID=? and status=?");
					q.setParameter(1, classname);
					q.setParameter(2, sectionName);
					q.setParameter(3, schoolid);
					q.setParameter(4, "Active");
					List<Class> classs = (List<Class>) q.getResultList();
					if(classs.size()>0){
						Calendar now = Calendar.getInstance();
						year = now.get(Calendar.YEAR);
						q=null;
						q=entitymanager.createQuery("from ClassTable where class_ID=? and day=? and school_ID=? and month=? and year=? and status=?");
						q.setParameter(1, classs.get(0).getClass_ID());
						q.setParameter(2, classTimeTableDataBean.getDay());
						q.setParameter(3, schoolid);
						q.setParameter(4, classTimeTableDataBean.getMonth());
						q.setParameter(5, String.valueOf(year));
						q.setParameter(6, "Active");
						List<ClassTable> classtable=(List<ClassTable>)q.getResultList();
						
						logger.debug("class table size ----->"+classtable.size());
						if(classtable.size()>0){
							starthour = "" + classTimeTableDataBean.getStartTime().getHours();
							startmin = "" + classTimeTableDataBean.getStartTime().getMinutes();
							if (starthour.length() == 1 && startmin.length() == 1) {
								starttime = "0" + starthour + ":" + "0" + startmin;
							} else if (starthour.length() == 1 && startmin.length() == 2) {
								starttime = "0" + starthour + ":" + startmin;
							} else if (starthour.length() == 2 && startmin.length() == 2) {
								starttime = starthour + ":" + startmin;
							} else if (starthour.length() == 2 && startmin.length() == 1) {
								starttime = starthour + ":" + "0" + startmin;
							}
							endhour = "" + classTimeTableDataBean.getEndTime().getHours();
							endmin = "" + classTimeTableDataBean.getEndTime().getMinutes();
							if (endhour.length() == 1 && endmin.length() == 1) {
								endtime = "0" + endhour + ":" + "0" + endmin;
							} else if (endhour.length() == 1 && endmin.length() == 2) {
								endtime = "0" + endhour + ":" + endmin;
							} else if (endhour.length() == 2 && endmin.length() == 2) {
								endtime = endhour + ":" + endmin;
							} else if (endhour.length() == 2 && endmin.length() == 1) {
								endtime = endhour + ":" + "0" + endmin;
							}
							logger.debug("start time "+starttime+" endtime "+endtime);
							q=null;
							q=entitymanager.createQuery("from ClassTimeTable where class_table_ID=? and ? between startTime and endTime and school_ID=?");
							q.setParameter(1, classtable.get(0).getClass_table_ID());
							q.setParameter(2, starttime);
							q.setParameter(3, schoolid);
							List<ClassTimeTable> classtimetable=(List<ClassTimeTable>)q.getResultList();
							logger.debug("class time table size "+classtimetable.size());
							if(classtimetable.size()>0){
								int coun=0;
								for (int i = 0; i < classtimetable.size(); i++) {
									logger.debug("end time and start time check----->"+classtimetable.get(i).getEndTime()+"-------->"+starttime);
									if(!classtimetable.get(i).getEndTime().equals(starttime)){									
										if(classtimetable.get(i).getClass_time_table_ID()!=classTimeTableDataBean.getClasstableid()){
											coun++;
										}
									}
								}
								logger.debug("=====count in time validtaion=====>"+coun);
								if(coun>0){
									status="Exist";
								}else{
									status=tecaherCheck(classTimeTableDataBean,schoolid,starttime,endtime,year);
								}								
								logger.debug("id "+classtimetable.get(0).getClass_time_table_ID()+" - "+classTimeTableDataBean.getClasstableid());
							}else{
								status=tecaherCheck(classTimeTableDataBean,schoolid,starttime,endtime,year);
							}
						}
					}
				}
			}
		}catch(Exception e){
			//e.printStackTrace();
		}
		return status;
	}

	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	public String updateClassTimeTable(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		logger.info("-----------Inside updateClassTimeTable method()----------------");
		Query v = null;String starttime="";String endtime="";
		List<Person> roll = null;
		List<String> subjects = null;DateFormat st=new SimpleDateFormat("hh:mm");
		List<String> maillist = new ArrayList<String>();List<String> phoenos = new ArrayList<String>();
		List<ClassTimeTableDataBean> classTimelist = new ArrayList<ClassTimeTableDataBean>();
		int teacherID=0;String starthour = "";
		String startmin = "";
		String endhour = "";
		String endmin = "";
		int year = 0;
		String status="Fail";
		try {
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					logger.debug("1");
					StringTokenizer stringtoken = new StringTokenizer(classTimeTableDataBean.getClassname());
					String classname = stringtoken.nextToken("/");
					String section = classTimeTableDataBean.getClassname();
					String sectionName = section.substring(section.lastIndexOf("/") + 1);
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					classTimeTableDataBean.setSchoolID(schoolid);
					classTimeTableDataBean.setSchoolName(roll.get(0).getSchool().getName());
					logger.debug("school id -- > " + schoolid);
					v = entitymanager.createQuery(
							"from Class where className=? and classSection=? and school_ID=? and status=?");
					v.setParameter(1, classname);
					v.setParameter(2, sectionName);
					v.setParameter(3, schoolid);
					v.setParameter(4, "Active");
					List<Class> classs = (List<Class>) v.getResultList();
					if (classs.size() > 0) {
						logger.debug("2");
						Calendar now = Calendar.getInstance();
						year = now.get(Calendar.YEAR);
						v = null;
						v = entitymanager
								.createQuery("from ClassTable where day=? and year=? and class_ID=? and school_ID=? and status='Active'");
						v.setParameter(1, classTimeTableDataBean.getDay());
						v.setParameter(2, String.valueOf(year));
						v.setParameter(3, classs.get(0).getClass_ID());
						v.setParameter(4, schoolid);
						List<ClassTable> classtable = (List<ClassTable>) v.getResultList();
						if (classtable.size() > 0) {
							starttime=st.format( classTimeTableDataBean.getStartTime());
							endtime=st.format( classTimeTableDataBean.getEndTime());
							logger.debug("3");
							starthour = "" + classTimeTableDataBean.getStartTime().getHours();
							startmin = "" + classTimeTableDataBean.getStartTime().getMinutes();
							if (starthour.length() == 1 && startmin.length() == 1) {
								starttime = "0" + starthour + ":" + "0" + startmin;
							} else if (starthour.length() == 1 && startmin.length() == 2) {
								starttime = "0" + starthour + ":" + startmin;
							} else if (starthour.length() == 2 && startmin.length() == 2) {
								starttime = starthour + ":" + startmin;
							} else if (starthour.length() == 2 && startmin.length() == 1) {
								starttime = starthour + ":" + "0" + startmin;
							}
							endhour = "" + classTimeTableDataBean.getEndTime().getHours();
							endmin = "" + classTimeTableDataBean.getEndTime().getMinutes();
							if (endhour.length() == 1 && endmin.length() == 1) {
								endtime = "0" + endhour + ":" + "0" + endmin;
							} else if (endhour.length() == 1 && endmin.length() == 2) {
								endtime = "0" + endhour + ":" + endmin;
							} else if (endhour.length() == 2 && endmin.length() == 2) {
								endtime = endhour + ":" + endmin;
							} else if (endhour.length() == 2 && endmin.length() == 1) {
								endtime = endhour + ":" + "0" + endmin;
							}
							/*v = null;
							v = entitymanager.createQuery(
									"from ClassTimeTable where class_table_ID=? and subject=? and startTime=? and endTime=?");
							v.setParameter(1, classtable.get(0).getClass_table_ID());
							v.setParameter(2, classTimeTableDataBean.getSubjects());
							v.setParameter(3, starttime);
							v.setParameter(4, endtime);
							List<ClassTimeTable> classtimetable = (List<ClassTimeTable>) v.getResultList();
							if (classtimetable.size() > 0) {*/
								logger.debug("4");
								v = null;
								v = entitymanager.createQuery("from Subject where sujectName=? and subjectCode=? and school_ID=? and status='Active' ");
								v.setParameter(1, classTimeTableDataBean.getSubject());
								v.setParameter(2, classTimeTableDataBean.getSubjectCode());					
								v.setParameter(3, schoolid);
								List<Subject> subject = (List<Subject>) v.getResultList();
								if(subject.size() > 0){
									logger.debug("5---"+classTimeTableDataBean.getTeaID());
									teacherID=getTeacherID(classTimeTableDataBean.getTeaID());
									logger.debug("teacherID"+teacherID+"subject_ID"+subject.get(0).getSubject_ID());
									/*v = null;
									v = entitymanager.createQuery("from TeacherSubject where subject_ID=? and teacher_ID=? and status=?");
									v.setParameter(1, subject.get(0).getSubject_ID());
									v.setParameter(2, teacherID);
									v.setParameter(3, "Active");
									List<TeacherSubject> teacherSubject = (List<TeacherSubject>) v.getResultList();
									logger.debug("teacher subject size - " + teacherSubject.size());
									if (teacherSubject.size() > 0) {*/
										logger.debug("id "+classTimeTableDataBean.getClasstableid());
										logger.debug("time "+starttime+" - "+endtime);
										ClassTimeTable updatePeriod = entitymanager.find(ClassTimeTable.class,classTimeTableDataBean.getClasstableid());
										updatePeriod.setSubject(classTimeTableDataBean.getSubject());
										updatePeriod.setSubjectCode(subject.get(0).getSubjectCode());
										updatePeriod.setTeacher(entitymanager.find(Teacher.class, teacherID));
										updatePeriod.setStartTime(starttime);
										updatePeriod.setEndTime(endtime);
										logger.debug("start time-----"+starttime+"--"+endtime);
										entitymanager.merge(updatePeriod);
										status="Success";
									/*}*/
								}
								logger.debug("7");
								v = null;
								v = entitymanager.createQuery("from StudentClass where class_ID=? and status=?");
								v.setParameter(1, classs.get(0).getClass_ID());
								v.setParameter(2, "Active");
								List<StudentClass> studentclass = (List<StudentClass>) v.getResultList();
								if (studentclass.size() > 0) {
									logger.debug("8");
									for (int j1 = 0; j1 < studentclass.size(); j1++) {
										v = null;
										v = entitymanager.createQuery("from StudentDetail where student_ID=?");
										v.setParameter(1, studentclass.get(j1).getStudent().getStudent_ID());
										List<StudentDetail> studentdetail = (List<StudentDetail>) v.getResultList();
										if (studentdetail.size() > 0) {
											logger.debug("9");
											if(schoolid==1 ||schoolid==2 || schoolid==3|| schoolid==4 || schoolid==5 || schoolid==6)
											{
											}else{											
												maillist.add(studentdetail.get(0).getEmailId());	
											}
											
											v = entitymanager.createQuery("from StudentParent where student_ID=?");
											v.setParameter(1, studentclass.get(j1).getStudent().getStudent_ID());
											List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();
											if (studentParent.size() > 0) {
												logger.debug("10");
												maillist.add(
														studentParent.get(0).getParent().getParentDetail().getEmaiId());
												phoenos.add(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
											}
										}
									}
									v = entitymanager.createQuery("from TeacherClass where class_ID=? and status=?");
									v.setParameter(1, classs.get(0).getClass_ID());
									v.setParameter(2, "Active");
									List<TeacherClass> teacherClass = (List<TeacherClass>) v.getResultList();
									if (teacherClass.size() > 0) {
										logger.debug("11");
										for (TeacherClass teacher : teacherClass) {
											v = entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
											v.setParameter(1, teacher.getTeacher().getTeacher_ID());
											List<TeacherDetail> teacherDetail = (List<TeacherDetail>) v.getResultList();
											if (teacherClass.size() > 0) {
												maillist.add(teacherDetail.get(0).getEmailId());
												phoenos.add(teacherDetail.get(0).getPhoneNumber());
											}
										}
									}
									classTimeTableDataBean.setMails(maillist);
									classTimeTableDataBean.setPhonenos(phoenos);
								}
							/*}*/
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
			//e.printStackTrace();
			logger.warn("Inside Exception",e);
		} finally {

		}
		return status;
	}

// New method to delete a specific period   By Jakap John
	@Transactional(value = "transactionManager")
	@SuppressWarnings("unchecked")
	public String deleteClassTimeTable(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		logger.info("-----------Inside updateClassTimeTable method()----------------");
		System.out.println("--------Inside updateClassTimeTable method() on AdminDaoImpl----------------");
		Query v = null;String starttime="";String endtime="";
		List<Person> roll = null;
		List<String> subjects = null;DateFormat st=new SimpleDateFormat("hh:mm");
		List<String> maillist = new ArrayList<String>();List<String> phoenos = new ArrayList<String>();
		List<ClassTimeTableDataBean> classTimelist = new ArrayList<ClassTimeTableDataBean>();
		int teacherID=0;String starthour = "";
		String startmin = "";
		String endhour = "";
		String endmin = "";
		int year = 0;
		String status="Fail";
		try {
			
			if (personID != null) {
				roll = getRollType(personID);
				System.out.println("Roll"+roll+"personId :"+getRollType(personID));
				if (roll.size() > 0) {
					logger.debug("Entered deleteClassTimeTable method");
					StringTokenizer stringtoken = new StringTokenizer(classTimeTableDataBean.getClassname());
					String classname = stringtoken.nextToken("/");
					String section = classTimeTableDataBean.getClassname();
					String sectionName = section.substring(section.lastIndexOf("/") + 1);
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					classTimeTableDataBean.setSchoolID(schoolid);
					classTimeTableDataBean.setSchoolName(roll.get(0).getSchool().getName());
					logger.debug("school id -- > " + schoolid);
					v = entitymanager.createQuery(
							"from Class where className=? and classSection=? and school_ID=? and status=?");
					v.setParameter(1, classname);
					v.setParameter(2, sectionName);
					v.setParameter(3, schoolid);
					v.setParameter(4, "Active");
					List<Class> classs = (List<Class>) v.getResultList();
					if (classs.size() > 0) {
						logger.debug("2");
						Calendar now = Calendar.getInstance();
						year = now.get(Calendar.YEAR);
						v = null;
						v = entitymanager.createQuery("from ClassTable where day=? and year=? and class_ID=? and school_ID=? and status='Active'");
						v.setParameter(1, classTimeTableDataBean.getDay());
						v.setParameter(2, String.valueOf(year));
						v.setParameter(3, classs.get(0).getClass_ID());
						v.setParameter(4, schoolid);
						List<ClassTable> classtable = (List<ClassTable>) v.getResultList();
						if (classtable.size() > 0) {
								teacherID=getTeacherID(classTimeTableDataBean.getTeaID());
								ClassTimeTable deletePeriod = entitymanager.find(ClassTimeTable.class,classTimeTableDataBean.getClasstableid());
								logger.info("*************Period ::"+deletePeriod.getClass_time_table_ID());
								entitymanager.remove(deletePeriod);
								status="Success";
								v = null;
								v = entitymanager.createQuery("from StudentClass where class_ID=? and status=?");
								v.setParameter(1, classs.get(0).getClass_ID());
								v.setParameter(2, "Active");
								List<StudentClass> studentclass = (List<StudentClass>) v.getResultList();
								if (studentclass.size() > 0) {
									for (int j1 = 0; j1 < studentclass.size(); j1++) {
										v = null;
										v = entitymanager.createQuery("from StudentDetail where student_ID=?");
										v.setParameter(1, studentclass.get(j1).getStudent().getStudent_ID());
										List<StudentDetail> studentdetail = (List<StudentDetail>) v.getResultList();
										if (studentdetail.size() > 0) {
											if(schoolid==1 ||schoolid==2 || schoolid==3|| schoolid==4 || schoolid==5 || schoolid==6)
											{
											}else{											
												maillist.add(studentdetail.get(0).getEmailId());	
											}
											
											v = entitymanager.createQuery("from StudentParent where student_ID=?");
											v.setParameter(1, studentclass.get(j1).getStudent().getStudent_ID());
											List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();
											if (studentParent.size() > 0) {
												maillist.add(
														studentParent.get(0).getParent().getParentDetail().getEmaiId());
												phoenos.add(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
											}
										}
									}
									v = entitymanager.createQuery("from TeacherClass where class_ID=? and status=?");
									v.setParameter(1, classs.get(0).getClass_ID());
									v.setParameter(2, "Active");
									List<TeacherClass> teacherClass = (List<TeacherClass>) v.getResultList();
									if (teacherClass.size() > 0) {
										logger.debug("1");
										for (TeacherClass teacher : teacherClass) {
											v = entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
											v.setParameter(1, teacher.getTeacher().getTeacher_ID());
											List<TeacherDetail> teacherDetail = (List<TeacherDetail>) v.getResultList();
											if (teacherClass.size() > 0) {
												maillist.add(teacherDetail.get(0).getEmailId());
												phoenos.add(teacherDetail.get(0).getPhoneNumber());
											}
										}
									}
									classTimeTableDataBean.setMails(maillist);
									classTimeTableDataBean.setPhonenos(phoenos);
								}
							/*}*/
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
			//e.printStackTrace();
			logger.warn("Inside Exception",e);
		} finally {

		}
		return status;
	}  // delete class time table method ends here


	@Transactional(value = "transactionManager")
	public String saveClassTimeTable(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		logger.info("-----------Inside saveClassTimeTable method()----------------");
		Query v = null;
		List<Person> roll = null;
		int year = 0;
		List<Integer> teacherID = new ArrayList<Integer>();
		List<String> maillist = new ArrayList<String>();List<String> phonenos = new ArrayList<String>();
		DateFormat df = new SimpleDateFormat("HH:mm");
		int teacherIDs=0;
		try {
			logger.debug("classTimeTableDataBean.getClasstimeList().size()"+classTimeTableDataBean.getClasstimeList().size());
			Calendar now = Calendar.getInstance();
			year = now.get(Calendar.YEAR);
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					classTimeTableDataBean.setSchoolLogo(roll.get(0).getSchool().getLogopath());
					StringTokenizer stringtoken = new StringTokenizer(classTimeTableDataBean.getClassname());
					String classname = stringtoken.nextToken("/");
					String section = classTimeTableDataBean.getClassname();
					String sectionName = section.substring(section.lastIndexOf("/") + 1);
					classTimeTableDataBean.setClassSection(classname+"-"+sectionName);
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					classTimeTableDataBean.setSchoolID(schoolid);
					classTimeTableDataBean.setSchoolName(roll.get(0).getSchool().getName());
					v = entitymanager.createQuery(
							"from Class where className=? and classSection=? and school_ID=? and status=?");
					v.setParameter(1, classname);
					v.setParameter(2, sectionName);
					v.setParameter(3, schoolid);
					v.setParameter(4, "Active");
					List<Class> classs = (List<Class>) v.getResultList();
					if (classs.size() > 0) {
						/*if (classTimeTableDataBean.getMonth().equalsIgnoreCase("All")) {*/
							for (int i = 0; i < classTimeTableDataBean.getMonthlist().size(); i++) {
								v = null;
								v = entitymanager
										.createQuery("from ClassTable where day=? and year=? and class_ID=? and school_ID=? and status=? and month=?");
								v.setParameter(1, classTimeTableDataBean.getDay());
								v.setParameter(2, String.valueOf(year));
								v.setParameter(3, classs.get(0).getClass_ID());
								v.setParameter(4, schoolid);
								v.setParameter(5, "Active");
								v.setParameter(6, classTimeTableDataBean.getMonthlist().get(i));
								List<ClassTable> classtable = (List<ClassTable>) v.getResultList();
								 if (classtable.size()>0) {
								}
								else{
								ClassTable classtime = new ClassTable();
								classtime.setSchool(entitymanager.find(School.class, schoolid));
								classtime.setClassz(entitymanager.find(Class.class, classs.get(0).getClass_ID()));
								classtime.setDay(classTimeTableDataBean.getDay());
								classtime.setStatus("Active");
								classtime.setYear("" + year);
								classtime.setMonth(classTimeTableDataBean.getMonthlist().get(i));
								entitymanager.persist(classtime);
								entitymanager.flush();
								entitymanager.clear();
							}
							}
						/*}*/
						/*else{
						ClassTable classtime = new ClassTable();
						classtime.setSchool(entitymanager.find(School.class, schoolid));
						classtime.setClassz(entitymanager.find(Class.class, classs.get(0).getClass_ID()));
						classtime.setDay(classTimeTableDataBean.getDay());
						classtime.setStatus("Active");
						classtime.setYear("" + year);
						classtime.setMonth(classTimeTableDataBean.getMonth());
						entitymanager.persist(classtime);
						entitymanager.flush();
						}*/
						for (int i = 0; i < classTimeTableDataBean.getClasstimeList().size(); i++) {
						logger.debug("inside for loop ClasstimeList");
						try {
							if (!classTimeTableDataBean.getClasstimeList().get(i).getSubject().equalsIgnoreCase("") && 
									classTimeTableDataBean.getClasstimeList().get(i).getSubject()!=null) {
								v = null;
								v = entitymanager.createQuery("from Subject where sujectName=? and subjectCode=? and status=? and school_ID=?");
								v.setParameter(1, classTimeTableDataBean.getClasstimeList().get(i).getSubject());
								v.setParameter(2, classTimeTableDataBean.getClasstimeList().get(i).getSubjectCode());
								v.setParameter(3, "Active");
								v.setParameter(4, schoolid);
								List<Subject> subject = (List<Subject>) v.getResultList();
								logger.debug("subject size "+subject.size()+" id "+subject.get(0).getSubject_ID());
								if(subject.size()>0){
									logger.debug("class id "+classs.get(0).getClass_ID());
									for (int j = 0; j < subject.size(); j++) {									
										v = null;
										v = entitymanager
												.createQuery("from ClassSubject where class_ID=? and subject_ID=? and status=?");
										v.setParameter(1, classs.get(0).getClass_ID());
										v.setParameter(2, subject.get(j).getSubject_ID());
										v.setParameter(3, "Active");
										List<ClassSubject> classSubject = (List<ClassSubject>) v.getResultList();
										logger.debug("class subject size "+classSubject.size());
										if (classSubject.size() > 0) {
											teacherID.add(classSubject.get(0).getSubject().getSubject_ID());
										}
									}
								}
							}
							else{
							
								teacherID.add(0);
							}
						} catch (NullPointerException e) {
							teacherID.add(0);
						}
							
						}
						logger.debug("subject id s - "+teacherID.size());
						/*if (classTimeTableDataBean.getMonth().equalsIgnoreCase("All")) {*/
							for (int j = 0; j < classTimeTableDataBean.getMonthlist().size(); j++) {
								v = null;
								v = entitymanager
										.createQuery("from ClassTable where day=? and year=? and class_ID=? and school_ID=? and status=? and month=?");
								v.setParameter(1, classTimeTableDataBean.getDay());
								v.setParameter(2, String.valueOf(year));
								v.setParameter(3, classs.get(0).getClass_ID());
								v.setParameter(4, schoolid);
								v.setParameter(5, "Active");
								v.setParameter(6, classTimeTableDataBean.getMonthlist().get(j));
								List<ClassTable> classtable = (List<ClassTable>) v.getResultList();
								logger.debug("class table size===>"+classtable.size());
								if (classtable.size() > 0) {
								for (int i = 0; i < classTimeTableDataBean.getClasstimeList().size(); i++) {
									logger.debug("subject -- " + classTimeTableDataBean.getClasstimeList().get(i).getSubject());
									teacherIDs=getTeacherID(classTimeTableDataBean.getClasstimeList().get(i).getTeaID());
									logger.debug(classTimeTableDataBean.getClasstimeList().get(i).getSubject()+"teacherIDs"+teacherIDs);
										v = null;
										v = entitymanager.createQuery("from TeacherSubject where subject_ID=? and teacher_ID=? and status=?");
										v.setParameter(1, teacherID.get(i));
										v.setParameter(2, teacherIDs);
										v.setParameter(3, "Active");
									List<TeacherSubject> teacherSubject = (List<TeacherSubject>) v.getResultList();
									logger.debug("teacher subject size - " + teacherSubject.size());
									if (teacherSubject.size() > 0) {
											ClassTimeTable classtimetable = new ClassTimeTable();
										classtimetable.setSubject(classTimeTableDataBean.getClasstimeList().get(i).getSubject());
										classtimetable.setSubjectCode(classTimeTableDataBean.getClasstimeList().get(i).getSubjectCode());
										/*logger.debug("start time "+classTimeTableDataBean.getClasstimeList().get(i).getStartTime()+" -- " +df.format(classTimeTableDataBean.getClasstimeList().get(i).getStartTime()));
										logger.debug("end time "+classTimeTableDataBean.getClasstimeList().get(i).getEndTime()+" -- " +df.format(classTimeTableDataBean.getClasstimeList().get(i).getEndTime()));*/
										classtimetable.setStartTime(df.format(classTimeTableDataBean.getClasstimeList().get(i).getStartTime()));
										classtimetable.setEndTime(df.format(classTimeTableDataBean.getClasstimeList().get(i).getEndTime()));
										classtimetable.setClassTable(entitymanager.find(ClassTable.class,
												classtable.get(0).getClass_table_ID()));
										classtimetable.setTeacher(entitymanager.find(Teacher.class,teacherIDs));
										classtimetable.setPeriod("" + classTimeTableDataBean.getClasstimeList().get(i).getPeriod());
										classtimetable.setSchool(entitymanager.find(School.class, schoolid));
										classtimetable.setDay(classTimeTableDataBean.getDay());
										entitymanager.persist(classtimetable);
										entitymanager.flush();
										entitymanager.clear();
									}
									else{
										ClassTimeTable classtimetable = new ClassTimeTable();
										classtimetable.setClassTable(entitymanager.find(ClassTable.class,classtable.get(0).getClass_table_ID()));
										classtimetable.setPeriod("" + classTimeTableDataBean.getClasstimeList().get(i).getPeriod());
										classtimetable.setSchool(entitymanager.find(School.class, schoolid));
										classtimetable.setDay(classTimeTableDataBean.getDay());
										entitymanager.persist(classtimetable);
										entitymanager.flush();
										entitymanager.clear();
									}
							}
							}
							}
							v = null;
							v = entitymanager.createQuery("from StudentClass where class_ID=? and status=?");
							v.setParameter(1, classs.get(0).getClass_ID());
							v.setParameter(2, "Active");
							List<StudentClass> studentclass = (List<StudentClass>) v.getResultList();
							if (studentclass.size() > 0) {
								for (int j1 = 0; j1 < studentclass.size(); j1++) {
									v = null;
									v = entitymanager.createQuery("from StudentDetail where student_ID=?");
									v.setParameter(1, studentclass.get(j1).getStudent().getStudent_ID());
									List<StudentDetail> studentdetail = (List<StudentDetail>) v.getResultList();
									if (studentdetail.size() > 0) {
										if(schoolid==1 ||schoolid==2 || schoolid==3|| schoolid==4 || schoolid==5 || schoolid==6)
										{
										}else{
											maillist.add(studentdetail.get(0).getEmailId());	
										}
									
										v = entitymanager.createQuery("from StudentParent where student_ID=?");
										v.setParameter(1, studentclass.get(j1).getStudent().getStudent_ID());
										List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();
										if (studentParent.size() > 0) {
											maillist.add(
													studentParent.get(0).getParent().getParentDetail().getEmaiId());
											phonenos.add(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
										}
									}
								}
							}
							v = entitymanager.createQuery("from TeacherClass where class_ID=? and status=?");
							v.setParameter(1, classs.get(0).getClass_ID());
							v.setParameter(2, "Active");
							List<TeacherClass> teacherClass = (List<TeacherClass>) v.getResultList();
							if (teacherClass.size() > 0) {
								for (TeacherClass teacher : teacherClass) {
									v = entitymanager.createQuery("from TeacherDetail where teacher_ID=?");
									v.setParameter(1, teacher.getTeacher().getTeacher_ID());
									List<TeacherDetail> teacherDetail = (List<TeacherDetail>) v.getResultList();
									if (teacherDetail.size() > 0) {
										maillist.add(teacherDetail.get(0).getEmailId());
										phonenos.add(teacherDetail.get(0).getPhoneNumber());
									}
								}
							}
							classTimeTableDataBean.setMails(maillist);
							classTimeTableDataBean.setPhonenos(phonenos);
							classTimeTableDataBean.setStatus("Success");
						
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
			logger.warn("Inside Exception",e);
			
		} finally {

		}
		return "";
	}

	public List<String> classSubjectList(HomeworkDatabean homeworkDatabean, String personID) {
		logger.info("-----------Inside classSubjectList method()----------------");
		Query v = null;
		Query q1 = null;
		List<Person> roll = null;
		int school_ID=0;
		int class_ID =0;
		int teaId=0;
		String personRollNo = "Not";
		List<String> subList = null;
		LinkedHashSet<String> tempList = null;
		List<String> matchSubjectList = null;
		try {
			subList=new ArrayList<String>();
			tempList = new LinkedHashSet<String>();
			matchSubjectList=new ArrayList<String>();
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					school_ID = roll.get(0).getSchool().getSchool_ID();
					personRollNo=roll.get(0).getPersonRoleNumber();
					teaId = getTeacherID(personRollNo);
					StringTokenizer stringtoken = new StringTokenizer(homeworkDatabean.getClassname());
					String classname = stringtoken.nextToken("/");
					String section = homeworkDatabean.getClassname();
					String sectionName = section.substring(section.lastIndexOf("/") + 1);
					v = entitymanager.createQuery(
							"from Class where className=? and classSection=? and school_ID=? and status=?");
					v.setParameter(1, classname);
					v.setParameter(2, sectionName);
					v.setParameter(3, roll.get(0).getSchool().getSchool_ID());
					v.setParameter(4, "Active");
					List<Class> clasz = (List<Class>) v.getResultList();
					if (clasz.size() > 0) {
				
						class_ID = clasz.get(0).getClass_ID();
						q1 = entitymanager.createQuery(
								"from TeacherSubject where school_ID=? and teacher_ID=? and status='Active'");
						q1.setParameter(1, school_ID);
						q1.setParameter(2, teaId);
						List<TeacherSubject> res = (List<TeacherSubject>) q1.getResultList();
						if (res.size() > 0) {
							for (int i = 0; i < res.size(); i++) {
								subList.add(res.get(i).getSubject().getSujectName() + "/"
										+ res.get(i).getSubject().getSubjectCode());
							}
							logger.debug("---" + subList.size());
							tempList.addAll(subList);
							logger.debug("---" + tempList.size());
							subList.clear();
							subList.addAll(tempList);
							matchSubjectList = getSubjectList(class_ID, school_ID, personID);
							if (matchSubjectList.size() > 0) {
								subList.retainAll(matchSubjectList);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
			//logger.warn("Inside Exception",e);
		}
		return subList;
	}


	public List<String> classNamesList(String personID, String status) {
		logger.info("-----------Inside classNamesList method()----------------");
		List<String> classes = new ArrayList<String>();
		List<Person> roll = null;
		String classname="";String section="";String sectionName="";
		  String days = "";String st = "";String et = "";String stime = "";
		  Calendar now = Calendar.getInstance();
		Query q = null;
		try {
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					logger.debug("school id -- > " + schoolid);
					if (roll.get(0).getPersonRole().equals("Teacher")) {
						String day = "";
						if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
							day=String.valueOf(GetTimeZone.getDate("Asia/Makassar").getDay());
						}else if(schoolid==Integer.parseInt(paths.getString("MALAYSIA.SCHOOLID"))){
							day=String.valueOf(GetTimeZone.getDate("Asia/Kuala_Lumpur").getDay());
						}
						else{
							day=String.valueOf(date.getDay());
						}
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
				    if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
			    		st= GetTimeZone.getTimeHour("Asia/Makassar");
			    		et=GetTimeZone.getTimeMin("Asia/Makassar");
			    	}else if(schoolid==Integer.parseInt(paths.getString("MALAYSIA.SCHOOLID"))){
			    		st= GetTimeZone.getTimeHour("Asia/Kuala_Lumpur");
			    		et=GetTimeZone.getTimeMin("Asia/Kuala_Lumpur");
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
				    	 logger.debug("inside else");
					       q = null;
					       q = entitymanager.createQuery("from Teacher where school_ID=? and person_ID=? and status=?");
					       q.setParameter(1, schoolid);
					       q.setParameter(2, personID);
					       q.setParameter(3, "Active");
					       List<Teacher> teacher = (List<Teacher>) q.getResultList();
					       logger.debug("teacher ---"+teacher.size());
					       if (teacher.size() > 0) {
					         q = null;
					         q = entitymanager.createQuery("from ClassTimeTable where teacher_ID=? and day=? and ? between startTime and endTime ");
					         q.setParameter(1, teacher.get(0).getTeacher_ID());
					         q.setParameter(2, days);
					         q.setParameter(3, stime);
					         List<ClassTimeTable> classTimeTable = (List<ClassTimeTable>) q.getResultList();
					         logger.debug("classtimetable size"+classTimeTable.size()+teacher.get(0).getTeacher_ID()+days+stime);
					         if (classTimeTable.size() > 0){
					        	 classname=classTimeTable.get(0).getClassTable().getClassz().getClassName();
						          sectionName=classTimeTable.get(0).getClassTable().getClassz().getClassSection();
					        	 if(status.equals("homework")){
					        		 classes.add(classname+"/"+sectionName);
							         classes.add(classTimeTable.get(0).getSubject()+"/"+classTimeTable.get(0).getSubjectCode());
					        	 }else if(status.equals("homeworkview")){
					        		 classes.add(classname+"/"+sectionName);
					        	 }					          
					         }
					       }
						
					} else if (roll.get(0).getPersonRole().equals("Parent")) {
						q = entitymanager.createQuery("from Parent where person_ID=? and status=?");
						q.setParameter(1, personID);
						q.setParameter(2, "Active");
						List<Parent> parent = (List<Parent>) q.getResultList();
						if (parent.size() > 0) {
							q = null;
							q = entitymanager.createQuery("from StudentParent where parent_ID=?  and status='Active'");
							q.setParameter(1, parent.get(0).getParent_ID());
							List<StudentParent> studentParent = (List<StudentParent>) q.getResultList();
							if (studentParent.size() > 0) {
								for (StudentParent students : studentParent) {
									q = null;
									q = entitymanager.createQuery("from StudentClass where student_ID=?");
									q.setParameter(1, students.getStudent().getStudent_ID());
									List<StudentClass> studentClass = (List<StudentClass>) q.getResultList();
									if (studentClass.size() > 0) {
										classes.add(studentClass.get(0).getClazz().getClassName() + "/"
												+ studentClass.get(0).getClazz().getClassSection());
									}
								}
								HashSet<String> hashclass = new HashSet<String>(classes);
								classes.clear();
								classes.addAll(hashclass);
							}
						}
					} else if (roll.get(0).getPersonRole().equals("Admin")) {
						q = entitymanager.createQuery("from Class where school_ID=? and status=?");
						q.setParameter(1, schoolid);
						q.setParameter(2, "Active");
						List<Class> classs = (List<Class>) q.getResultList();
						if (classs.size() > 0) {
							for (int i = 0; i < classs.size(); i++) {
								String classnames = classs.get(i).getClassName() + "/" + classs.get(i).getClassSection();
								classes.add(classnames);
							}
							HashSet<String> hashclass = new HashSet<String>(classes);
							classes.clear();
							classes.addAll(hashclass);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
			logger.warn("Inside Exception",e);
		}
		return classes;
	}

	public String checkHomeWork(HomeworkDatabean homeworkDatabean, String personID) {
		logger.info("-----------Inside checkHomeWork method()----------------");
		Query v = null;
		String status = "fail";
		try {
			v = entitymanager
					.createQuery("from Homework where className=? and date=? and subject=? and status='Active'");
			v.setParameter(1, homeworkDatabean.getClassname());
			v.setParameter(2,  date);
			v.setParameter(3, homeworkDatabean.getSubject());
			List<Homework> homeWork = (List<Homework>) v.getResultList();
			logger.debug("home work size -- " + homeWork.size());
			if (homeWork.size() > 0) {
				status = "fail";
			} else {
				status = "success";
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return status;
	}

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    
	/* Prema OCT 25   homeWorkInsert*/
	@Transactional(value = "transactionManager")
	 public String homeWorkInsert(HomeworkDatabean homeworkDatabean, String personID) {
	logger.info("-----------Inside homeWorkInsert method()----------------");
	  Query v = null;
	  List<Person> roll = null;
	  List<String> maillist = new ArrayList<String>();List<String> phonenos = new ArrayList<String>();
	  String status = "Fail";
	  String classname="";
	  String section="";
	  String sectionName="";
	  String days = "";
	  String st = "";
	  String et = "";
	  String stime = "";
	  Calendar now = Calendar.getInstance();
	  int year = now.get(Calendar.YEAR);
	  


	  try {
	   if (personID != null) {
	    roll = getRollType(personID);
	    if (roll.size() > 0) {     
	    	
	    	Date date = new Date();
	        System.out.println(sdf.format(date));
	        
	     int schoolid = roll.get(0).getSchool().getSchool_ID();
	     homeworkDatabean.setSchoolID(schoolid);
	   
	     logger.info("school id -- > " + schoolid);
	     Homework work = new Homework();
	     logger.info("Current Date 1 ------->"+date);
	     //work.setDate(date);
	     
	     
	     date = sdf.parse(sdf.format(date));
         System.out.println(date);
         logger.info("Converted Date -->"+sdf.format(date));	     
	     work.setDate(date);
	     logger.info("Current Date 2 ------->"+date);
	     work.setClassName(homeworkDatabean.getClassname());
	     work.setSubject(homeworkDatabean.getSubject());
	     work.setHomeWork(homeworkDatabean.getHomework());
	     work.setStatus("Active");
	     work.setSchool(entitymanager.find(School.class, schoolid));
	     entitymanager.persist(work);
	     v = entitymanager.createQuery(
	       "from Class where className=? and classSection=? and school_ID=? and status=?");
	     v.setParameter(1, homeworkDatabean.getClassname().split("/")[0]);
	     v.setParameter(2,  homeworkDatabean.getClassname().split("/")[1]);
	     v.setParameter(3, schoolid);
	     v.setParameter(4, "Active");
	     List<Class> classs = (List<Class>) v.getResultList();
	     if (classs.size() > 0) {
	      v = null;
	      v = entitymanager.createQuery("from StudentClass where class_ID=? and status=?");
	      v.setParameter(1, classs.get(0).getClass_ID());
	      v.setParameter(2, "Active");
	      List<StudentClass> studentclass = (List<StudentClass>) v.getResultList();
	      if (studentclass.size() > 0) {
	       for (int j = 0; j < studentclass.size(); j++) {
	        v = null;       
	        v = entitymanager.createQuery("from StudentParent where student_ID=?");
	        v.setParameter(1, studentclass.get(j).getStudent().getStudent_ID());
	        List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();
	        if (studentParent.size() > 0) {
	         maillist.add(studentParent.get(0).getParent().getParentDetail().getEmaiId());
	         phonenos.add(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
	        }
	       }
	       homeworkDatabean.setMails(maillist);
	       homeworkDatabean.setPhones(phonenos);
	      }
	     }
	     status = "Success";
	    }
	   }

	  } catch (Exception e) {
	   logger.warn("Inside Exception"+e.getMessage());
	  }
	  return status;
	 }

	public String classChange(HomeworkDatabean homeworkDatabean, String personID) {
		logger.info("-----------Inside classChange method()----------------");
		Query v = null;
		List<HomeworkDatabean> workList = new ArrayList<HomeworkDatabean>();
		List<Person> roll = null;
		List<Homework> homeWork=null;
		SimpleDateFormat sd=new SimpleDateFormat("MM");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
		String month="",currentMonth="",year="",currentYear="";
		try {
			if (personID != null) {
				homeWork=new ArrayList<Homework>();
				homeworkDatabean.setWorklist(new ArrayList<HomeworkDatabean>());
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					logger.debug("school id -- > " + schoolid);
					if (roll.get(0).getPersonRole().equalsIgnoreCase("Student")) {
						v = entitymanager
								.createQuery("from Student where rollNumber=? and school_ID=? and status='Active'");
						v.setParameter(1, roll.get(0).getPersonRoleNumber());
						v.setParameter(2, schoolid);
						List<Student> student = (List<Student>) v.getResultList();
						if (student.size() > 0) {
							v = entitymanager.createQuery("from StudentClass where student_ID=?");
							v.setParameter(1, student.get(0).getStudent_ID());
							List<StudentClass> studentClass = (List<StudentClass>) v.getResultList();
							if (studentClass.size() > 0) {
								v = entitymanager
										.createQuery("from Homework where className=? and date=? and status='Active'");
								v.setParameter(1, studentClass.get(0).getClazz().getClassName() + "/"
										+ studentClass.get(0).getClazz().getClassSection());
								v.setParameter(2, homeworkDatabean.getDate());
								homeWork = (List<Homework>) v.getResultList();
								logger.debug("home work size -- " + homeWork.size());
								if (homeWork.size() > 0) {
									int no = 1;
									for (Homework work : homeWork) {
										HomeworkDatabean list = new HomeworkDatabean();
										list.setSno(no);
										list.setSubject(work.getSubject());
										list.setHomework(work.getHomeWork());
										list.setClassname(work.getClassName());
										list.setDate(work.getDate());
										workList.add(list);
										homeworkDatabean.setWorklist(workList);
										no++;
									}
								} else {
									homeworkDatabean.setWorklist(workList);
								}
							}
						}
					} else {
						if("".equalsIgnoreCase(homeworkDatabean.getClassname()) && homeworkDatabean.getDate()==null){
							for (int i = 0; i < homeworkDatabean.getClassnameList().size(); i++) {
								v = entitymanager.createQuery("from Homework where className=? and school_ID=? and status='Active' order by date desc");
								v.setParameter(1, homeworkDatabean.getClassnameList().get(i));
								v.setParameter(2, schoolid);
								List<Homework> homeWorkList = (ArrayList<Homework>) v.getResultList();
								logger.debug("homeWorkList  size -- " + homeWorkList.size());
								if (homeWorkList.size() > 0) {
									int no = 1;
									for (Homework work : homeWorkList) {
										 month=sd.format(work.getDate());
										 currentMonth=sd.format(date);
										 year=sdf.format(work.getDate());
										 currentYear=sdf.format(date);
										if(month.equalsIgnoreCase(currentMonth) && year.equalsIgnoreCase(currentYear)){
											HomeworkDatabean list = new HomeworkDatabean();
											list.setSno(no);
											list.setSubject(work.getSubject());
											list.setHomework(work.getHomeWork());
											list.setClassname(work.getClassName());
											list.setDate(work.getDate());
											workList.add(list);
											homeworkDatabean.setWorklist(workList);
											no++;
										}
									}
								}
							}
						}else{
							if("All".equalsIgnoreCase(homeworkDatabean.getClassname())){
								for (int i = 0; i < homeworkDatabean.getClassnameList().size(); i++) {
									v = entitymanager.createQuery("from Homework where className=? and school_ID=? and date=? and status='Active' order by date desc");
									v.setParameter(1, homeworkDatabean.getClassnameList().get(i));
									v.setParameter(2, schoolid);
									v.setParameter(3, homeworkDatabean.getDate());
									List<Homework> homeWorkList = (ArrayList<Homework>) v.getResultList();
									homeWork.addAll(homeWorkList);
								}
							}else{
								v = entitymanager.createQuery("from Homework where className=? and date=? and school_ID=? and status='Active' order by date desc ");
								v.setParameter(1, homeworkDatabean.getClassname());
								v.setParameter(2, homeworkDatabean.getDate());
								v.setParameter(3, schoolid);
								homeWork = (List<Homework>) v.getResultList();
							}
						}
						logger.debug("home work size -->" + homeWork.size());
						if (homeWork.size() > 0) {
							int no = 1;
							for (Homework work : homeWork) {
								HomeworkDatabean list = new HomeworkDatabean();
								list.setSno(no);
								list.setSubject(work.getSubject());
								list.setHomework(work.getHomeWork());
								list.setClassname(work.getClassName());
								list.setDate(work.getDate());
								workList.add(list);
								homeworkDatabean.setWorklist(workList);
								no++;
							}
						} else {
							homeworkDatabean.setWorklist(workList);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return "";
	}

	@Transactional(value = "transactionManager")
	public String updateHomeWork(HomeworkDatabean homeworkDatabean, String personID) {
		logger.info("-----------Inside updateHomeWork method()----------------");
		Query v = null;
		List<Person> roll = null;
		List<String> maillist = new ArrayList<String>();List<String> phones = new ArrayList<String>();
		try {
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					homeworkDatabean.setSchoolName(roll.get(0).getSchool().getName());
					homeworkDatabean.setSchoolID(schoolid);
					logger.debug("school id -- > " + schoolid);
					v = entitymanager.createQuery(
							"from Homework where className=? and date=? and subject=? and status='Active'");
					v.setParameter(1, homeworkDatabean.getClassname());
					v.setParameter(2, homeworkDatabean.getDate());
					v.setParameter(3, homeworkDatabean.getSubject());
					List<Homework> homeWork = (List<Homework>) v.getResultList();
					if (homeWork.size() > 0) {
						StringTokenizer stringtoken = new StringTokenizer(homeworkDatabean.getClassname());
						String classname = stringtoken.nextToken("/");
						String section = homeworkDatabean.getClassname();
						String sectionName = section.substring(section.lastIndexOf("/") + 1);
						Homework work = entitymanager.find(Homework.class, homeWork.get(0).getHome_work_ID());
						work.setHomeWork(homeworkDatabean.getHomework());
						entitymanager.merge(work);
						v = null;
						v = entitymanager.createQuery(
								"from Class where className=? and classSection=? and school_ID=? and status=?");
						v.setParameter(1, classname);
						v.setParameter(2, sectionName);
						v.setParameter(3, schoolid);
						v.setParameter(4, "Active");
						List<Class> classs = (List<Class>) v.getResultList();
						if (classs.size() > 0) {
							v = null;
							v = entitymanager.createQuery("from StudentClass where class_ID=? and status=?");
							v.setParameter(1, classs.get(0).getClass_ID());
							v.setParameter(2, "Active");
							List<StudentClass> studentclass = (List<StudentClass>) v.getResultList();
							if (studentclass.size() > 0) {
								for (int j = 0; j < studentclass.size(); j++) {
									v = null;									
									v = entitymanager.createQuery("from StudentParent where student_ID=?");
									v.setParameter(1, studentclass.get(j).getStudent().getStudent_ID());
									List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();
									if (studentParent.size() > 0) {
										maillist.add(studentParent.get(0).getParent().getParentDetail().getEmaiId());
										phones.add(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
									}
								}
								homeworkDatabean.setMails(maillist);
								homeworkDatabean.setPhones(phones);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return "";
	}

	@Transactional(value = "transactionManager")
	public String deleteHomeWork(HomeworkDatabean homeworkDatabean, String personID) {
		logger.info("-----------Inside deleteHomeWork method()----------------");
		Query v = null;
		List<Person> roll = null;
		List<String> maillist = new ArrayList<String>();
		try {
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					logger.debug("school id -- > " + schoolid);
					v = entitymanager.createQuery(
							"from Homework where className=? and date=? and subject=? and status='Active'");
					v.setParameter(1, homeworkDatabean.getClassname());
					v.setParameter(2, homeworkDatabean.getDate());
					v.setParameter(3, homeworkDatabean.getSubject());
					List<Homework> homeWork = (List<Homework>) v.getResultList();
					if (homeWork.size() > 0) {
						Homework work = entitymanager.find(Homework.class, homeWork.get(0).getHome_work_ID());
						work.setHomeWork(homeworkDatabean.getHomework());
						work.setStatus("DeActive");
						entitymanager.merge(work);
						//entitymanager.remove(work);
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}

	@Transactional(value = "transactionManager")
	public String updateAttendance(AttendanceDataBean attendanceDataBean, String personID) {
		logger.info("-----------Inside updateAttendance method()----------------");
		Query v = null;
		List<Person> person = null;List<String> phones = new ArrayList<String>();
		String status = "fail";
		List<String> mails = new ArrayList<String>();
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					int schoolid = person.get(0).getSchool().getSchool_ID();
					attendanceDataBean.setSchoolName(person.get(0).getSchool().getName());
					v = entitymanager.createQuery("from Teacher where school_ID=? and person_ID=? and status=?");
					v.setParameter(1, schoolid);
					v.setParameter(2, personID);
					v.setParameter(3, "Active");
					List<Teacher> teacher = (List<Teacher>) v.getResultList();
					if (teacher.size() > 0) {
						StringTokenizer stringtoken = new StringTokenizer(attendanceDataBean.getClassname());
						String classname = stringtoken.nextToken("/");
						String section = attendanceDataBean.getClassname();
						String sectionName = section.substring(section.lastIndexOf("/") + 1);
						v = entitymanager
								.createQuery("from Attendance where school_ID=? and date=? and class_=? and section=?");
						v.setParameter(1, schoolid);
						v.setParameter(2, attendanceDataBean.getDate());
						v.setParameter(3, classname);
						v.setParameter(4, sectionName);
						List<Attendance> attendance = (List<Attendance>) v.getResultList();
						if (attendance.size() > 0) {
							v = null;
							v = entitymanager.createQuery(
									"from Attendanceclass where attendance_ID=? and Teacher_ID=? and period=? and studentName=? and student_ID=?");
							v.setParameter(1, attendance.get(0).getAttendance_ID());
							v.setParameter(2, teacher.get(0).getTeacher_ID());
							v.setParameter(3, attendanceDataBean.getPeriod().substring(7, 8));
							v.setParameter(4, attendanceDataBean.getStudentName());
							v.setParameter(5, attendanceDataBean.getStudentID());
							List<Attendanceclass> attendanceclass = (List<Attendanceclass>) v.getResultList();
							if (attendanceclass.size() > 0) {
								Attendanceclass attendanceUpdate = entitymanager.find(Attendanceclass.class,
										attendanceclass.get(0).getAtt_class_ID());
								attendanceUpdate.setStatus(attendanceDataBean.getStatus());
								
								if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
									logger.info("Indonesian time--->"+GetTimeZone.getDate("Asia/Makassar").getTime() );
									attendanceUpdate.setTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
									System.out.println("Lombak Time1 -->"+attendanceUpdate.getTime());

								}else if(schoolid==35 || schoolid==36 || schoolid==37 || schoolid==38|| schoolid==39 || schoolid==40 || schoolid==41 || schoolid==42 || schoolid==43){	
									attendanceUpdate.setTime(timestamp);
									logger.info("Nagercoil time--->"+timestamp);
									//System.out.println("Chennai Time2 -->"+attendanceUpdate.getTime());

								}else{	
									logger.info("Lombak Time3 before -->"+GetTimeZone.getLombakTime("Asia/Makassar"));
									attendanceUpdate.setTime(GetTimeZone.getLombakTime("Asia/Makassar"));									
								}
								entitymanager.merge(attendanceUpdate);
								attendancePercentage(personID, attendanceDataBean);
								AttendanceDataBean updatelist = new AttendanceDataBean();
								updatelist.setSno(attendanceDataBean.getSno());
								updatelist.setStudentName(attendanceDataBean.getStudentName());
								updatelist.setStudentID(attendanceDataBean.getStudentID());
								if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
									updatelist.setTime1(attendanceUpdate.getTime().getHours()+":"+attendanceUpdate.getTime().getMinutes());
									attendanceDataBean.setTime1(attendanceUpdate.getTime().getHours()+":"+attendanceUpdate.getTime().getMinutes());
								}else if(schoolid==35 || schoolid==36 || schoolid==37 || schoolid==38|| schoolid==39 || schoolid==40 || schoolid==41 || schoolid==42 || schoolid==43){
									updatelist.setTime1(timestamp.getHours() + ":" + timestamp.getMinutes());
									attendanceDataBean.setTime1(timestamp.getHours() + ":" + timestamp.getMinutes());
								}else{
									updatelist.setTime1(attendanceUpdate.getTime().getHours()+":"+attendanceUpdate.getTime().getMinutes());
									attendanceDataBean.setTime1(attendanceUpdate.getTime().getHours()+":"+attendanceUpdate.getTime().getMinutes());
									logger.info("Jakarta updaelist Time ---------------->"+updatelist.getTime1()); 
									logger.info("Jakarta Time ---------------->"+attendanceDataBean.getTime1()); 
								}
								logger.debug("time ------>"+attendanceDataBean.getTime1());
								logger.debug("percentage --> " + attendanceDataBean.getPercentage());
								updatelist.setPercentage(attendanceDataBean.getPercentage());
								updatelist.setDate(attendanceDataBean.getDate());
								updatelist.setStatus(attendanceDataBean.getStatus());
								attendanceDataBean.getStudentList().set(attendanceDataBean.getSno() - 1, updatelist);
								status = "success";
								v = entitymanager.createQuery("from Student where rollNumber=? and status=?");
								v.setParameter(1, attendanceDataBean.getStudentID());
								v.setParameter(2, "Active");
								List<Student> student = (List<Student>) v.getResultList();
								if (student.size() > 0) {
									v = entitymanager.createQuery("from StudentParent where student_ID=? and status=?");
									v.setParameter(1, student.get(0).getStudent_ID());
									v.setParameter(2, "Active");
									List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();									
									if (studentParent.size() > 0) {
										mails.add(studentParent.get(0).getParent().getParentDetail().getEmaiId());
										phones.add(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
										attendanceDataBean.setMails(mails);
										if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
											attendanceDataBean.setTime1(attendanceUpdate.getTime().getHours()+":"+attendanceUpdate.getTime().getMinutes());
										}else if(schoolid==35 || schoolid==36 || schoolid==37 || schoolid==38|| schoolid==39 || schoolid==40 || schoolid==41 || schoolid==42 || schoolid==43){
											attendanceDataBean.setTime1(timestamp.getHours() + ":" + timestamp.getMinutes());
										}
										else{
											attendanceDataBean.setTime1(attendanceUpdate.getTime().getHours()+":"+attendanceUpdate.getTime().getMinutes());
											logger.info("Jakarta Time For student.size() > 0 ---------------->"+attendanceDataBean.getTime1()); 
										}
										attendanceDataBean.setPhones(phones);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception-->" +e.getMessage());
		}
		return status;
	}

	public String classPeriod(AttendanceDataBean attendanceDataBean, String personID) {
		logger.info("-----------Inside classPeriod method()----------------");
		List<String> periods = new ArrayList<String>();
		List<String> periodss = null;
		List<Person> person = null;
		Query v = null;
		String periodd = "";
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					int schoolid = person.get(0).getSchool().getSchool_ID();
					if (person.get(0).getPersonRole().equals("Teacher")) {
						v = entitymanager.createQuery("from Teacher where school_ID=? and person_ID=? and status=?");
						v.setParameter(1, schoolid);
						v.setParameter(2, personID);
						v.setParameter(3, "Active");
						List<Teacher> teacher = (List<Teacher>) v.getResultList();
						if (teacher.size() > 0) {
							StringTokenizer stringtoken = new StringTokenizer(attendanceDataBean.getClassname());
							String classname = stringtoken.nextToken("/");
							String section = attendanceDataBean.getClassname();
							String sectionName = section.substring(section.lastIndexOf("/") + 1);
							v = entitymanager.createQuery(
									"from Attendance where school_ID=? and date=? and class_=? and section=?");
							v.setParameter(1, schoolid);
							v.setParameter(2, attendanceDataBean.getDate());
							v.setParameter(3, classname);
							v.setParameter(4, sectionName);
							List<Attendance> attendance = (List<Attendance>) v.getResultList();
							if (attendance.size() > 0) {
								v = null;
								v = entitymanager
										.createQuery("from Attendanceclass where attendance_ID=? and Teacher_ID=?");
								v.setParameter(1, attendance.get(0).getAttendance_ID());
								v.setParameter(2, teacher.get(0).getTeacher_ID());
								List<Attendanceclass> attendanceclass = (List<Attendanceclass>) v.getResultList();
								if (attendanceclass.size() > 0) {
									for (int i = 0; i < attendanceclass.size(); i++) {
										periods.add(attendanceclass.get(i).getPeriod());
									}
									HashSet<String> period = new HashSet<String>(periods);
									periods.clear();
									periods.addAll(period);
									periodss = new ArrayList<String>();
									for (int i = 0; i < periods.size(); i++) {
										if (periods.get(i).equals("1"))
											periodd = "Period " + "1";
										else if (periods.get(i).equals("2"))
											periodd = "Period " + "2";
										else if (periods.get(i).equals("3"))
											periodd = "Period " + "3";
										else if (periods.get(i).equals("4"))
											periodd = "Period " + "4";
										else if (periods.get(i).equals("5"))
											periodd = "Period " + "5";
										else if (periods.get(i).equals("6"))
											periodd = "Period " + "6";
										else if (periods.get(i).equals("7"))
											periodd = "Period " + "7";
										else if (periods.get(i).equals("8"))
											periodd = "Period " + "8";
										else if (periods.get(i).equals("9"))
											periodd = "Period " + "9";
										else if (periods.get(i).equals("10"))
											periodd = "Period " + "10";
										periodss.add(periodd);
									}									
								}								
							}
							attendanceDataBean.setPeriods(periodss);
						}
					} else {
						StringTokenizer stringtoken = new StringTokenizer(attendanceDataBean.getClassname());
						String classname = stringtoken.nextToken("/");
						String section = attendanceDataBean.getClassname();
						String sectionName = section.substring(section.lastIndexOf("/") + 1);
						v = entitymanager.createQuery("from Attendance where school_ID=? and date=? and class_=? and section=?");
						v.setParameter(1, schoolid);
						v.setParameter(2, attendanceDataBean.getDate());
						v.setParameter(3, classname);
						v.setParameter(4, sectionName);
						List<Attendance> attendance = (List<Attendance>) v.getResultList();
						if (attendance.size() > 0) {
							v = null;
							v = entitymanager.createQuery("from Attendanceclass where attendance_ID=?");
							v.setParameter(1, attendance.get(0).getAttendance_ID());
							List<Attendanceclass> attendanceclass = (List<Attendanceclass>) v.getResultList();
							if (attendanceclass.size() > 0) {
								for (int i = 0; i < attendanceclass.size(); i++) {
									periods.add(attendanceclass.get(i).getPeriod());
								}
								HashSet<String> period = new HashSet<String>(periods);
								periods.clear();
								periods.addAll(period);
								periodss = new ArrayList<String>();
								for (int i = 0; i < periods.size(); i++) {
									if (periods.get(i).equals("1"))
										periodd = "Period " + "1";
									else if (periods.get(i).equals("2"))
										periodd = "Period " + "2";
									else if (periods.get(i).equals("3"))
										periodd = "Period " + "3";
									else if (periods.get(i).equals("4"))
										periodd = "Period " + "4";
									else if (periods.get(i).equals("5"))
										periodd = "Period " + "5";
									else if (periods.get(i).equals("6"))
										periodd = "Period " + "6";
									else if (periods.get(i).equals("7"))
										periodd = "Period " + "7";
									else if (periods.get(i).equals("8"))
										periodd = "Period " + "8";
									else if (periods.get(i).equals("9"))
										periodd = "Period " + "9";
									else if (periods.get(i).equals("10"))
										periodd = "Period " + "10";
									periodss.add(periodd);
								}								
							}							
						}
						attendanceDataBean.setPeriods(periodss);
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception-->" +e.getMessage());
		}
		return null;
	}

	public String classChange(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		logger.info("-----------Inside classChange method()----------------");
		Query q = null;
		Query v = null;
		List<Person> roll = null;
		List<String> subjectList = new ArrayList<String>();
		try {
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					StringTokenizer st = new StringTokenizer(classTimeTableDataBean.getClassname());
					String className = st.nextToken("/");
					String sectionName = classTimeTableDataBean.getClassname();
					String section = sectionName.substring(sectionName.lastIndexOf("/") + 1);
					q = entitymanager.createQuery("from Class where className=? and classSection=? and school_ID=? and status='Active'");
					q.setParameter(1, className);
					q.setParameter(2, section);
					q.setParameter(3, schoolid);
					List<Class> classz = (List<Class>) q.getResultList();
					if (classz.size() > 0) {
						v = entitymanager.createQuery("from ClassSubject where class_ID=? and status='Active'");
						v.setParameter(1, classz.get(0).getClass_ID());
						List<ClassSubject> classSubject = (List<ClassSubject>) v.getResultList();
						if (classSubject.size() > 0) {
							for (int i = 0; i < classSubject.size(); i++) {
								subjectList.add(classSubject.get(i).getSubject().getSujectName());
							}
							HashSet<String> sublsit=new HashSet<String>(subjectList);
							subjectList.clear();
							subjectList.addAll(sublsit);
							classTimeTableDataBean.setSubjectlist(subjectList);
							logger.debug("subjects - " + classTimeTableDataBean.getSubjectlist());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}
	public String checkExtraClass(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		logger.info("-----------Inside checkExtraClass method()----------------");
		Query v = null;
		String days = "";
		List<Person> roll = null;
		int year = 0;
		String status = "fail";
		try {
			Calendar now = Calendar.getInstance();
			year = now.get(Calendar.YEAR);		
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					String day = "";
					if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
						day=String.valueOf(GetTimeZone.getDate("Asia/Makassar").getDay());
					}else{
						day=String.valueOf(date.getDay());
					}
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						if (day.equals("0"))
							days = "Sunday";
					}					
					if (day.equals("1"))
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
					classTimeTableDataBean.setDay(days);
					StringTokenizer stringTokenizer = new StringTokenizer(classTimeTableDataBean.getClassname());
					String className = stringTokenizer.nextToken("/");
					String sectionName = classTimeTableDataBean.getClassname();
					String section = sectionName.substring(sectionName.lastIndexOf("/") + 1);					
					v = entitymanager.createQuery(
							"from Class where className=? and classSection=? and school_ID=? and status=?");
					v.setParameter(1, className);
					v.setParameter(2, section);
					v.setParameter(3, schoolid);
					v.setParameter(4, "Active");
					List<Class> classs = (List<Class>) v.getResultList();
					if (classs.size() > 0) {
						v = null;
						v = entitymanager.createQuery("from Subject where subjectCode=? and status=? and school_ID=?");
						v.setParameter(1, classTimeTableDataBean.getSubjectCode());
						v.setParameter(2, "Active");
						v.setParameter(3, schoolid);
						List<Subject> subject = (List<Subject>) v.getResultList();
						v = null;
						v = entitymanager
								.createQuery("from ClassSubject where class_ID=? and subject_ID=? and status=?");
						v.setParameter(1, classs.get(0).getClass_ID());
						v.setParameter(2, subject.get(0).getSubject_ID());
						v.setParameter(3, "Active");
						List<ClassSubject> classSubject = (List<ClassSubject>) v.getResultList();
						v = entitymanager
								.createQuery("from TeacherSubject where school_ID=? and subject_ID=? and status=?");
						v.setParameter(1, schoolid);
						v.setParameter(2, classSubject.get(0).getSubject().getSubject_ID());
						v.setParameter(3, "Active");
						List<TeacherSubject> teacher = (List<TeacherSubject>) v.getResultList();
						v = entitymanager.createQuery("from ClassTable where class_ID=? and day=? and year=? and status='Active'");
						v.setParameter(1, classs.get(0).getClass_ID());
						v.setParameter(2, days);
						v.setParameter(3, String.valueOf(year));
						List<ClassTable> classtable = (List<ClassTable>) v.getResultList();
						if (classtable.size() > 0) {
							String st = "";
							String et = "";
							String ss = "" + classTimeTableDataBean.getStartTime().getHours();
							String ss1 = "" + classTimeTableDataBean.getStartTime().getMinutes();
							if (ss.length() == 1 && ss1.length() == 1) {
								st = "0" + ss + ":" + "0" + ss1;
							} else if (ss.length() == 1 && ss1.length() == 2) {
								st = "0" + ss + ":" + ss1;
							} else if (ss.length() == 2 && ss1.length() == 1) {
								st = ss + ":" + "0" + ss1;
							} else if (ss.length() == 2 && ss1.length() == 2) {
								st = ss + ":" + ss1;
							}
							String ss2 = "" + classTimeTableDataBean.getEndTime().getHours();
							String ss3 = "" + classTimeTableDataBean.getEndTime().getMinutes();
							if (ss2.length() == 1 && ss3.length() == 1) {
								et = "0" + ss2 + ":" + "0" + ss3;
							} else if (ss2.length() == 1 && ss3.length() == 2) {
								et = "0" + ss2 + ":" + ss3;
							} else if (ss2.length() == 2 && ss3.length() == 1) {
								et = ss2 + ":" + "0" + ss3;
							} else if (ss2.length() == 2 && ss3.length() == 2) {
								et = ss2 + ":" + ss3;
							}
							classTimeTableDataBean.setTimeStart(st);
							classTimeTableDataBean.setTimeEnd(et);
							v = null;
							v = entitymanager.createQuery(
									"from ClassTimeTable where day=? and class_table_ID=? and ? between startTime and endTime");
							v.setParameter(1, days);
							v.setParameter(2, classtable.get(0).getClass_table_ID());
							v.setParameter(3, st);
							logger.debug("st "+st);
							logger.debug("id - " + teacher.get(0).getTeacher().getTeacher_ID());
							List<ClassTimeTable> classTimeTable = (List<ClassTimeTable>) v.getResultList();
							if (classTimeTable.size() > 0) {
								status = "fail";
							} else {
								status = "success";
							}
						} else {
							status = "no";
						}
						logger.debug("status "+status);
					}
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//logger.warn("Exception", e);
			logger.warn("Inside Exception",e);
		} finally {

		}
		return status;
	}

	@Transactional(value = "transactionManager")
	   public String insertExtraClass(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		logger.info("-----------Inside insertExtraClass method()----------------");
	    Query v = null;
	    List<Person> roll = null;
	    int year = 0;
	    int teaid=0; List<String> phonenos = new ArrayList<String>();
	    List<String> maillist = new ArrayList<String>();
	    String status = "fail";
	    try {
	     Calendar now = Calendar.getInstance();
	     year = now.get(Calendar.YEAR);
	     if (personID != null) {
	      roll = getRollType(personID);
	      if (roll.size() > 0) {
	       StringTokenizer st = new StringTokenizer(classTimeTableDataBean.getClassname());
	       String className = st.nextToken("/");
	       String sectionName = classTimeTableDataBean.getClassname();
	       String section = sectionName.substring(sectionName.lastIndexOf("/") + 1);
	       int schoolid = roll.get(0).getSchool().getSchool_ID();
	       classTimeTableDataBean.setSchoolName(roll.get(0).getSchool().getName());
	       v = entitymanager.createQuery(
	         "from Class where className=? and classSection=? and school_ID=? and status=?");
	       v.setParameter(1, className);
	       v.setParameter(2, section);
	       v.setParameter(3, schoolid);
	       v.setParameter(4, "Active");
	       List<Class> classs = (List<Class>) v.getResultList();
	       if (classs.size() > 0) {
	        v = null;
	        v = entitymanager.createQuery("from Subject where subjectCode=? and status=? and school_ID=?");
	        v.setParameter(1, classTimeTableDataBean.getSubjectCode());
	        v.setParameter(2, "Active");
	        v.setParameter(3, schoolid);
	        List<Subject> subject = (List<Subject>) v.getResultList();
	        v = null;
	        v = entitymanager
	          .createQuery("from ClassSubject where class_ID=? and subject_ID=? and status=?");
	        v.setParameter(1, classs.get(0).getClass_ID());
	        v.setParameter(2, subject.get(0).getSubject_ID());
	        v.setParameter(3, "Active");
	        List<ClassSubject> classSubject = (List<ClassSubject>) v.getResultList();
	        v = null;
	        v = entitymanager.createQuery("from Teacher where rollNumber=? and status='Active'");
	        v.setParameter(1, classTimeTableDataBean.getTeaID());
	        List<Teacher> teacherlist=(List<Teacher>)v.getResultList();
	        if(teacherlist.size()>0){
	          teaid = teacherlist.get(0).getTeacher_ID();
	        }
	        v = entitymanager
	             .createQuery("from TeacherSubject where school_ID=? and subject_ID=? and teacher_ID=? and status=?");
	           v.setParameter(1, schoolid);
	           v.setParameter(2, classSubject.get(0).getSubject().getSubject_ID());
	           v.setParameter(3, teaid);
	           v.setParameter(4, "Active");
	           List<TeacherSubject> teacher = (List<TeacherSubject>) v.getResultList();
	           logger.debug("teachersubject list size"+teacher.size());
	        maillist.add(teacher.get(0).getTeacher().getTeacherDetails().get(0).getEmailId());
	        phonenos.add(teacher.get(0).getTeacher().getTeacherDetails().get(0).getPhoneNumber());
	        v = entitymanager.createQuery("from ClassTable where class_ID=? and day=? and year=? and status='Active'");
	        v.setParameter(1, classs.get(0).getClass_ID());
	        v.setParameter(2, classTimeTableDataBean.getDay());
	        v.setParameter(3, String.valueOf(year));
	        List<ClassTable> classtable = (List<ClassTable>) v.getResultList();
	        if (classtable.size() > 0) {
	         v = null;
	         v = entitymanager.createQuery("from ClassTimeTable where day=? and class_table_ID=?");
	         v.setParameter(1, classTimeTableDataBean.getDay());
	         v.setParameter(2, classtable.get(0).getClass_table_ID());
	         List<ClassTimeTable> classTimeTable = (List<ClassTimeTable>) v.getResultList();
	         ClassTimeTable extraclass = new ClassTimeTable();
	         extraclass.setTeacher(
	           entitymanager.find(Teacher.class, teacher.get(0).getTeacher().getTeacher_ID()));
	         extraclass.setDay(classTimeTableDataBean.getDay());
	         extraclass.setDate(classTimeTableDataBean.getDate());
	         extraclass.setSubject(classTimeTableDataBean.getSubject());
	         extraclass.setSubjectCode(classTimeTableDataBean.getSubjectCode());
	         extraclass.setTeacher(entitymanager.find(Teacher.class,teaid));
	         logger.debug("teacher id========>"+extraclass.getTeacher());
	         extraclass.setStartTime(classTimeTableDataBean.getTimeStart());
	         extraclass.setEndTime(classTimeTableDataBean.getTimeEnd());
	         extraclass.setClassTable(
	           entitymanager.find(ClassTable.class, classtable.get(0).getClass_table_ID()));
	         extraclass.setSchool(entitymanager.find(School.class, schoolid));
	         extraclass.setPeriod("" + (classTimeTable.size() + 1));
	         entitymanager.persist(extraclass);
	         v = null;
	         v = entitymanager.createQuery("from StudentClass where class_ID=? and status=?");
	         v.setParameter(1, classs.get(0).getClass_ID());
	         v.setParameter(2, "Active");
	         List<StudentClass> studentclass = (List<StudentClass>) v.getResultList();
	         if (studentclass.size() > 0) {
	          for (int j = 0; j < studentclass.size(); j++) {	           
	           v = entitymanager.createQuery("from StudentParent where student_ID=?");
	           v.setParameter(1, studentclass.get(j).getStudent().getStudent_ID());
	           List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();
	           if (studentParent.size() > 0) {
	            maillist.add(studentParent.get(0).getParent().getParentDetail().getEmaiId());
	            phonenos.add(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
	           }
	          }
	          classTimeTableDataBean.setMails(maillist);
	          classTimeTableDataBean.setPhonenos(phonenos);
	          status = "success";
	         }
	        }
	       }
	      }
	     }
	    } catch (Exception e) {
	     logger.warn("Exception", e);
	     //logger.warn("Inside Exception",e);
	    } finally {

	    }
	    return status;
	}
	   

	public String teacherTimeTable(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		logger.info("-----------Inside teacherTimeTable method()----------------");
		Query v = null;
		Query query2 = null;
		List<Person> roll = null;
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String dates = "";
		String exdate = "";
		List<String> periods = new ArrayList<String>();
		List<ClassTimeTableDataBean> teachertablelist = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			logger.info("-----------Inside teacherTimeTable method()----------------");
			/*Calendar c = Calendar.getInstance();
		    System.out.println(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH ) );*/
		    
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					logger.info("school id -- > " + schoolid);
					v = entitymanager.createQuery("from Teacher where school_ID=? and person_ID=? and status=?");
					v.setParameter(1, schoolid);
					v.setParameter(2, personID);
					v.setParameter(3, "Active");
					List<Teacher> teacher = (List<Teacher>) v.getResultList();
					logger.info("Teacher Size -- > " + teacher.size());
					logger.info("----------- Teacher Roll Number ------------"+teacher.get(0).getRollNumber());
					logger.info("----------- Teacher ID ------------"+teacher.get(0).getTeacher_ID());
					logger.info("Month---->"+classTimeTableDataBean.getMonth());
					if (teacher.size() > 0) {
											
						logger.info("---- Teacher size is more than zero----");
						//v = entitymanager.createQuery("from ClassTimeTable where day=? and year=? and school_ID=? and status=?"); // Add month
	v=entitymanager.createNativeQuery("select ctt.class_time_table_ID,ctt.day,ctt.end_time,ctt.start_time,ctt.subject,ctt.subject_code,ctt.period,ctt.class_table_ID "
	+ "from class_time_table as ctt where ctt.teacher_ID=? and ctt.class_table_ID in (select ct.class_table_ID "
		+ "from class_table as ct where ct.school_ID=? and ct.status=? and ct.year=? and ct.day=? and ct.month=?)");

						
						v.setParameter(1, teacher.get(0).getTeacher_ID());
						v.setParameter(2, schoolid);
						v.setParameter(3, "Active");
						v.setParameter(4, String.valueOf(year)); // Year
						v.setParameter(5, classTimeTableDataBean.getDay()); // Day
						v.setParameter(6, now.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH )); // Month
						
						//List<ClassTimeTable> classtimetables = v.getResultList();
						List<Object[]> classtimetables = v.getResultList();
						teachertablelist = new ArrayList<ClassTimeTableDataBean>();
						// int i=1;
						if(classtimetables.size()>0) {
							for (Object[] a : classtimetables) {
								ClassTimeTableDataBean tablelist = new ClassTimeTableDataBean();

								/*logger.info("Time table ID ---------------> "
							            + a[0]
							            + " Day --------------->"
							            + a[1]
						            	+ " End Time --------------->"
									            + a[2]
						            		 + " Start TIme --------------->"
									            + a[3]
						            		 + " Subject --------------->"
									            + a[4]
						            		 + " Subject Code --------------->"
									            + a[5] + " Period --------------->"
									            + a[6]
																					            		
						            		 + " Class time table ID --------------->"
									            + a[7]
							            		);*/
								tablelist.setSerialno(Integer.parseInt(String.valueOf(a[6])));   
								tablelist.setSubject(String.valueOf(a[4]));
								tablelist.setTimeEnd(String.valueOf(a[2]));
								tablelist.setTimeStart(String.valueOf(a[3]));
								tablelist.setSubjectCode(String.valueOf(a[5]));
								
								query2=entitymanager.createNativeQuery("select class_name,class_section from class where class_ID in "
										+ "(select class_ID from class_table where class_table_ID in "
										+ "(select distinct (ctt.class_table_ID) from class_time_table as ctt "
										+ "where ctt.teacher_ID=? and ctt.class_table_ID =?))");
								query2.setParameter(1, teacher.get(0).getTeacher_ID());
								query2.setParameter(2, a[7]);	
								List<Object[]> classList = query2.getResultList();
								
								for (Object[] b : classList) {
									logger.info("Class Name  ---------------> "+ b[0]);
									logger.info("Class Section  ---------------> "+ b[1]);
									tablelist.setClassname(b[0]+"/"+b[1]);
								}

						teachertablelist.add(tablelist);
							}
						}else {
							ClassTimeTableDataBean tablelist = new ClassTimeTableDataBean();
							for(int i=1;i<=8;i++) {
							tablelist.setSerialno(i);
							tablelist.setSubject("-");
							tablelist.setTimeEnd("-");
							tablelist.setTimeStart("-");
							tablelist.setSubjectCode("-");
							tablelist.setClassname("-");
							teachertablelist.add(tablelist);
							}
						}
						
						classTimeTableDataBean.setClasstimeList(teachertablelist);	
					
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception" +e.getMessage());
			//logger.info("Exception 2 -->"+e.getMessage());
			
		} finally {

		}
		return "";
	}

	
	/* Prema OCT 25   classTimeTableView*/
	public String classTimeTableView(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		logger.info("-----------Inside classTimeTableView method()----------------");
		Query v = null;		
		List<Person> roll = null;
		List<String> subjects = null;
		List<ClassTimeTableDataBean> classTimelist = new ArrayList<ClassTimeTableDataBean>();
		int year = 0;
		try {
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					logger.debug("school id -- > " + schoolid);
					logger.debug("role"+roll.get(0).getPersonRole());
					if (!roll.get(0).getPersonRole().equals("Student")) {
						StringTokenizer st = new StringTokenizer(classTimeTableDataBean.getClassname());
						String className = st.nextToken("/");
						String sectionName = classTimeTableDataBean.getClassname();
						String section = sectionName.substring(sectionName.lastIndexOf("/") + 1);
						logger.debug("className"+className+""+section);
						v = entitymanager.createQuery(
								"from Class where className=? and classSection=? and school_ID=? and status=?");
						v.setParameter(1, className);
						v.setParameter(2, section);
						v.setParameter(3, schoolid);
						v.setParameter(4, "Active");
						List<Class> classs = (List<Class>) v.getResultList();
						if (classs.size() > 0) {
							Calendar now = Calendar.getInstance();
							year = now.get(Calendar.YEAR);
							v = null;
							v = entitymanager.createQuery(
									"from ClassTable where day=? and year=? and class_ID=? and school_ID=? and status='Active' and month=?");
							v.setParameter(1, classTimeTableDataBean.getDay());
							v.setParameter(2, String.valueOf(year));
							v.setParameter(3, classs.get(0).getClass_ID());
							v.setParameter(4, schoolid);
							v.setParameter(5, classTimeTableDataBean.getMonth());
							List<ClassTable> classtable = (List<ClassTable>) v.getResultList();
							logger.debug("class table size -- " + classtable.size());
							if (classtable.size() > 0) {
								v = null;
								v = entitymanager.createQuery("from ClassTimeTable where class_table_ID=?");
								v.setParameter(1, classtable.get(0).getClass_table_ID());
								List<ClassTimeTable> classtimetable = (List<ClassTimeTable>) v.getResultList();
								logger.debug("class time table value size ---->"+classtimetable.size());
								if (classtimetable.size() > 0) {	
									classTimeTableDataBean.setClasstimeList(new ArrayList<ClassTimeTableDataBean>());
									classTimeTableDataBean.setYear(classtable.get(0).getYear());
									classTimeTableViewPeriods(classtimetable,schoolid,classTimeTableDataBean);									
								}
								v = null;
								v = entitymanager.createQuery("from ClassSubject where class_ID=? and status=?");
								v.setParameter(1, classs.get(0).getClass_ID());
								v.setParameter(2, "Active");
								List<ClassSubject> classSubject = (List<ClassSubject>) v.getResultList();
								logger.debug("subject size -- " + classSubject.size());
								if (classSubject.size() > 0) {
									subjects = new ArrayList<String>();
									for (ClassSubject classsubject : classSubject) {
										subjects.add(classsubject.getSubject().getSujectName());
									}
									classTimeTableDataBean.setSubjectlist(subjects);
								}
							} else {
								classTimeTableDataBean.setClasstimeList(classTimelist);
							}
							logger.debug(
									"class time table list size - " + classTimeTableDataBean.getClasstimeList().size());
						}

					} else if (roll.get(0).getPersonRole().equals("Student")) {
						logger.debug("inside else");
						v = entitymanager.createQuery("from Student where rollNumber=? and school_ID=? and status=?");
						v.setParameter(1, roll.get(0).getPersonRoleNumber());
						v.setParameter(2, schoolid);
						v.setParameter(3, "Active");
						List<Student> student = (List<Student>) v.getResultList();
						logger.debug("student size"+student.size());
						if (student.size() > 0) {
							Calendar now = Calendar.getInstance();
							year = now.get(Calendar.YEAR);
							v = null;
							v = entitymanager.createQuery("from StudentClass where student_ID=? and status=?");
							v.setParameter(1, student.get(0).getStudent_ID());
							v.setParameter(2, "Active");
							List<StudentClass> studentClass = (List<StudentClass>) v.getResultList();
							logger.debug("studentClass size"+studentClass.size());
							if (studentClass.size() > 0) {
								v = entitymanager.createQuery(
										"from ClassTable where day=? and year=? and class_ID=? and school_ID=? and status='Active' and month=?");
								v.setParameter(1, classTimeTableDataBean.getDay());
								v.setParameter(2, String.valueOf(year));
								v.setParameter(3, studentClass.get(0).getClazz().getClass_ID());
								v.setParameter(4, schoolid);
								v.setParameter(5, classTimeTableDataBean.getMonth());
								List<ClassTable> classtable = (List<ClassTable>) v.getResultList();
								logger.debug("classtable size"+classtable.size());
								if (classtable.size() > 0) {
									v = null;
									v = entitymanager.createQuery("from ClassTimeTable where class_table_ID=?");
									v.setParameter(1, classtable.get(0).getClass_table_ID());
									List<ClassTimeTable> classtimetable = (List<ClassTimeTable>) v.getResultList();
									logger.debug("classtimetable size"+classtimetable.size());
									if (classtimetable.size() > 0) {
										int no = 1;
										classTimeTableDataBean.setYear(classtable.get(0).getYear());
										classTimeTableViewPeriods(classtimetable,schoolid,classTimeTableDataBean);
									} else {
										classTimeTableDataBean.setClasstimeList(classTimelist);
									}
									logger.debug("class time table size"+classTimeTableDataBean.getClasstimeList());
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
			
			logger.warn(" exception - "+e);
		} finally {

		}
		return "";
	}
		
	public String classTimeTableViewPeriods(List<ClassTimeTable> classtimetable, int schoolid, ClassTimeTableDataBean classTimeTableDataBean){
		logger.info("-----------Inside classTimeTableViewPeriods method()----------------");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
		String dates = "";
		String exdate = "";
		List<ClassTimeTableDataBean> classTimelist = new ArrayList<ClassTimeTableDataBean>();
		try{
			int no = 1;
			for (ClassTimeTable classtimetble : classtimetable) {
				if(schoolid==1 || schoolid==2 || schoolid==3|| schoolid==4|| schoolid==5|| schoolid==6){
					if(classtimetble.getDate()!=null){
						try {
							if(schoolid==2 || schoolid==3|| schoolid==4|| schoolid==5|| schoolid==6){
								dates = format.format(GetTimeZone.getDate("Asia/Makassar"));
							}else{
								dates = format.format(date);
							}							
							exdate = "" + classtimetble.getDate();
							if (exdate.equals(dates)) {
								ClassTimeTableDataBean classtime = new ClassTimeTableDataBean();
								classtime.setSerialno(no);
								try {
									if (!"".equalsIgnoreCase(classtimetble.getTeacher().getRollNumber()) && !classtimetble.getTeacher().getRollNumber().equals(null)) {
										classtime.setSubject(classtimetble.getSubject());
										classtime.setSubjects(classtimetble.getSubject());
										classtime.setEndTime(format1.parse(classtimetble.getEndTime()));
										classtime.setStartTime(format1.parse(classtimetble.getStartTime()));
										classtime.setStartTimeMobile(classtimetble.getStartTime());
										classtime.setEndTimeMobile(classtimetble.getEndTime()); 
										classtime.setPeriod(/*"Extra Period " +*/ classtimetble.getPeriod());
										classtime.setTeaID(classtimetble.getTeacher().getRollNumber());
										classtime.setSubjectCode(classtimetble.getSubjectCode());
										classtime.setClasstableid(classtimetble.getClass_time_table_ID());
									}
									else{
										classtime.setPeriod(/*"Extra Period " +*/ classtimetble.getPeriod());
										classtime.setTeaID("");
										classtime.setClasstableid(classtimetble.getClass_time_table_ID());
									}
								} catch (NullPointerException e) {
									classtime.setPeriod(/*"Extra Period " +*/ classtimetble.getPeriod());
									classtime.setTeaID("");
									classtime.setClasstableid(classtimetble.getClass_time_table_ID());
								}
								classTimelist.add(classtime);
								classTimeTableDataBean.setClasstimeList(classTimelist);
								no++;
							}
						} catch (Exception e) {

						}
					}else {
						ClassTimeTableDataBean classtime = new ClassTimeTableDataBean();
						classtime.setSerialno(no);
						try {
							if (!"".equalsIgnoreCase(classtimetble.getTeacher().getRollNumber()) && !classtimetble.getTeacher().getRollNumber().equals(null)) {
							classtime.setSubject(classtimetble.getSubject());
							classtime.setSubjects(classtimetble.getSubject());
							classtime.setEndTime(format1.parse(classtimetble.getEndTime()));
							classtime.setStartTime(format1.parse(classtimetble.getStartTime()));
							classtime.setStartTimeMobile(classtimetble.getStartTime());
							classtime.setEndTimeMobile(classtimetble.getEndTime());
							classtime.setPeriod(/*"Period " +*/ classtimetble.getPeriod());
							classtime.setSubjectCode(classtimetble.getSubjectCode());
							classtime.setTeaID(classtimetble.getTeacher().getRollNumber());
							classtime.setClasstableid(classtimetble.getClass_time_table_ID());
						}
							else{
								classtime.setPeriod(/*"Period " +*/ classtimetble.getPeriod());
								classtime.setTeaID("");
								classtime.setClasstableid(classtimetble.getClass_time_table_ID());
							}
						} catch (NullPointerException e) {
							classtime.setPeriod(/*"Period " +*/ classtimetble.getPeriod());
							classtime.setTeaID("");
							classtime.setClasstableid(classtimetble.getClass_time_table_ID());
						}
						classTimelist.add(classtime);
						classTimeTableDataBean.setClasstimeList(classTimelist);
						no++;
					}
				}else{
					/*if (classtimetble.getPeriod().equalsIgnoreCase("9")
							|| classtimetble.getPeriod().equalsIgnoreCase("10")) {
			
						try {
							if(schoolid==2 || schoolid==3|| schoolid==4|| schoolid==5|| schoolid==6){
								dates = format.format(GetTimeZone.getDate("Asia/Makassar"));
							}else{
								dates = format.format(date);
								}
							exdate = "" + classtimetble.getDate();
							if (exdate.equals(dates)) {
								ClassTimeTableDataBean classtime = new ClassTimeTableDataBean();
								classtime.setSerialno(no);
								try {
									if (!"".equalsIgnoreCase(classtimetble.getTeacher().getRollNumber()) && !classtimetble.getTeacher().getRollNumber().equals(null)) {
										classtime.setSubject(classtimetble.getSubject());
										classtime.setSubjects(classtimetble.getSubject());
										classtime.setEndTime(format1.parse(classtimetble.getEndTime()));
										classtime.setStartTime(format1.parse(classtimetble.getStartTime()));
										classtime.setPeriod("Extra Period " + classtimetble.getPeriod());
										classtime.setTeaID(classtimetble.getTeacher().getRollNumber());
										classtime.setSubjectCode(classtimetble.getSubjectCode());
										classtime.setClasstableid(classtimetble.getClass_time_table_ID());
									}
									else{
										classtime.setPeriod("Extra Period " + classtimetble.getPeriod());
										classtime.setTeaID("");
										classtime.setClasstableid(classtimetble.getClass_time_table_ID());
									}
								} catch (NullPointerException e) {
									classtime.setPeriod("Extra Period " + classtimetble.getPeriod());
									classtime.setTeaID("");
									classtime.setClasstableid(classtimetble.getClass_time_table_ID());
								}
								classTimelist.add(classtime);
								classTimeTableDataBean.setClasstimeList(classTimelist);
								no++;
						} catch (Exception e) {
								e.printStackTrace();
						}
					} else {*/
						ClassTimeTableDataBean classtime = new ClassTimeTableDataBean();
						classtime.setSerialno(no);
						logger.debug("====Subject===============>"+classtimetble.getSubject());
						try {
							if (!"".equalsIgnoreCase(classtimetble.getTeacher().getRollNumber()) && !classtimetble.getTeacher().getRollNumber().equals(null)) {
								classtime.setSubject(classtimetble.getSubject());
								classtime.setSubjects(classtimetble.getSubject());
								classtime.setEndTime(format1.parse(classtimetble.getEndTime()));
								classtime.setStartTime(format1.parse(classtimetble.getStartTime()));
								classtime.setStartTimeMobile(classtimetble.getStartTime());
								classtime.setEndTimeMobile(classtimetble.getEndTime());
								classtime.setPeriod(/*"Period " +*/ classtimetble.getPeriod());
								classtime.setSubjectCode(classtimetble.getSubjectCode());
								classtime.setTeaID(classtimetble.getTeacher().getRollNumber());
								classtime.setClasstableid(classtimetble.getClass_time_table_ID());
							}
							else{
								classtime.setPeriod(/*"Period " +*/ classtimetble.getPeriod());
								classtime.setTeaID("");
								classtime.setClasstableid(classtimetble.getClass_time_table_ID());
							}
						} catch (NullPointerException e) {
							classtime.setPeriod(/*"Period " +*/ classtimetble.getPeriod());
							classtime.setTeaID("");
							classtime.setClasstableid(classtimetble.getClass_time_table_ID());
						}
						classTimelist.add(classtime);
						classTimeTableDataBean.setClasstimeList(classTimelist);
						no++;
					}
				}										
			/*}*/
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception -->"+e.getMessage());
		}
		return "";
	}


	@Override
	public List<ClassTimeTableDataBean> getclassTimeTable(String personID) {
		logger.info("-----------Inside getclassTimeTable method()----------------");
		List<ClassTimeTableDataBean> classtimelist = null;
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
		try {
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					logger.debug("school id -- > " + schoolid);
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						days.add("Sunday");
					}
					days.add("Monday");
					days.add("Tuesday");
					days.add("Wednesday");
					days.add("Thursday");
					days.add("Friday");
					days.add("Saturday");
					int count = 0;
					int count1 = 0;
					if (roll.get(0).getPersonRole().equals("Teacher")) {
						v = entitymanager.createQuery("from Teacher where school_ID=? and person_ID=? and status=?");
						v.setParameter(1, schoolid);
						v.setParameter(2, personID);
						v.setParameter(3, "Active");
						List<Teacher> teacher = (List<Teacher>) v.getResultList();
						if (teacher.size() > 0) {
							v = entitymanager.createQuery("from ClassTable where year=? and status=? and  month=?");
							v.setParameter(1, String.valueOf(year));
							v.setParameter(2, "Active");
							v.setParameter(3, new SimpleDateFormat("MMMM").format(date));
							/*v.setParameter(3, );*/
							List<ClassTable> classTable = (List<ClassTable>) v.getResultList();
							if (classTable.size() > 0) {
								int cc = 1;
								for (int c = 0; c < 10; c++) {
									periods.add("" + cc);
									cc++;
								}
								if (periods.size() > 0) {
									int no = 1;
									classtimelist = new ArrayList<ClassTimeTableDataBean>();
									timetabledashboadTeacher(periods,days,teacher,schoolid,count,count1,classtimelist);								
								}
							}
						}
					} else if (roll.get(0).getPersonRole().equals("Parent")
							|| roll.get(0).getPersonRole().equals("Student")) {
						logger.debug("student or parent");
						int size = 0;
						v = entitymanager.createQuery("from Parent where school_ID=? and person_ID=? and status=?");
						v.setParameter(1, schoolid);
						v.setParameter(2, personID);
						v.setParameter(3, "Active");
						List<Parent> parent = (List<Parent>) v.getResultList();
						if (parent.size() > 0) {
							v = entitymanager.createQuery("from StudentParent where parent_ID=? and status=?");
							v.setParameter(1, parent.get(0).getParent_ID());
							logger.debug("parent id -- " + parent.get(0).getParent_ID());
							v.setParameter(2, "Active");
							List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();
							if (studentParent.size() > 0) {
								v = entitymanager.createQuery("from StudentClass where student_ID=? and status=?");
								v.setParameter(1, studentParent.get(0).getStudent().getStudent_ID());
								v.setParameter(2, "Active");
								studentClass = (List<StudentClass>) v.getResultList();
								size++;
							}
						} else {
							v = entitymanager
									.createQuery("from Student where school_ID=? and person_ID=? and status=?");
							v.setParameter(1, schoolid);
							v.setParameter(2, personID);
							v.setParameter(3, "Active");
							List<Student> student = (List<Student>) v.getResultList();
							if (student.size() > 0) {
								v = entitymanager.createQuery("from StudentClass where student_ID=? and status=?");
								v.setParameter(1, student.get(0).getStudent_ID());
								v.setParameter(2, "Active");
								studentClass = (List<StudentClass>) v.getResultList();
								size++;
							}
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
								timetableparstudent(periods,days,schoolid,count,count1,classtimelist,studentClass);										
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
			//e.printStackTrace();
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
		return classtimelist;
	}

	private void timetabledashboadTeacher(List<String> periods, List<String> days, List<Teacher> teacher, int schoolid,
			int count, int count1, List<ClassTimeTableDataBean> classtimelist) {
		logger.info("-----------Inside timetabledashboadTeacher method()----------------");
		String dates = "";
		String exdate = "";
		List<String> subjects = null;
		List<String> classes = null;List<String> times = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Query v=null;
		try{
			for (int j = 0; j < days.size(); j++) {
				subjects = new ArrayList<String>();
				classes = new ArrayList<String>();
				times = new ArrayList<String>();
				for (int i = 0; i < periods.size(); i++) {
					String subject = "";
					String classname = "";String time = "";
					v = null;
					v = entitymanager.createQuery(
							"from ClassTimeTable where teacher_ID=? and period=? and school_ID=? and day=?");
					v.setParameter(1, teacher.get(0).getTeacher_ID());
					v.setParameter(2, periods.get(i));
					v.setParameter(3, schoolid);
					v.setParameter(4, days.get(j));
					List<ClassTimeTable> classtime = (List<ClassTimeTable>) v.getResultList();	
					if (classtime.size() > 0) {
						for (int k = 0; k < classtime.size(); k++) {
							v = null;
							v = entitymanager.createQuery("from ClassTable where class_table_ID=? and status='Active'");
							v.setParameter(1, classtime.get(k).getClassTable().getClass_table_ID());
							List<ClassTable> classtable = (List<ClassTable>) v.getResultList();
							if(classtable.size() > 0){
								if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
									subject = classtime.get(k).getSubject();
									classname = classtime.get(k).getClassTable().getClassz()
											.getClassName() + "-"
											+ classtime.get(k).getClassTable().getClassz()
													.getClassSection();
									time = classtime.get(k).getStartTime()+" - "+classtime.get(k).getEndTime();
								}else{
									if (classtime.get(k).getPeriod().equalsIgnoreCase("9")
											|| classtime.get(k).getPeriod().equalsIgnoreCase("10")) {
										if (classtime.get(k).getPeriod().equalsIgnoreCase("9")) {
											try {
												if(schoolid==2 || schoolid==3|| schoolid==4|| schoolid==5|| schoolid==6){
													dates = format.format(GetTimeZone.getDate("Asia/Makassar"));
												}else{
													dates = format.format(date);
												}
												exdate = "" + classtime.get(k).getDate();
												if (exdate.equals(dates)) {
													subject = classtime.get(k).getSubject();
													classname = classtime.get(k).getClassTable().getClassz()
															.getClassName() + "-"
															+ classtime.get(k).getClassTable().getClassz()
																	.getClassSection();
													count++;
												}
											} catch (Exception e) {
												subject = "-";
												classname = "";
												time="";
											}
										}
										if (classtime.get(0).getPeriod().equalsIgnoreCase("10")) {
											try {
												if(schoolid==2 || schoolid==3|| schoolid==4|| schoolid==5|| schoolid==6){
													dates = format.format(GetTimeZone.getDate("Asia/Makassar"));
												}else{
													dates = format.format(date);
												}
												exdate = "" + classtime.get(k).getDate();
												if (exdate.equals(dates)) {
													subject = classtime.get(k).getSubject();
													classname = classtime.get(k).getClassTable().getClassz()
															.getClassName() + "-"
															+ classtime.get(k).getClassTable().getClassz()
																	.getClassSection();
													count1++;
												}
											} catch (Exception e) {
												subject = "-";
												classname = "";
												time="";
											}
										}
									} else {
										subject = classtime.get(k).getSubject();
										classname = classtime.get(k).getClassTable().getClassz()
												.getClassName() + "-"
												+ classtime.get(k).getClassTable().getClassz()
														.getClassSection();
									}
								}
							}
						}
												
					} else {
						subject = "-";
						classname = "";
						time="";
					}
					subjects.add(subject);
					classes.add(classname);
					times.add(time);
				}
				ClassTimeTableDataBean tablelist = new ClassTimeTableDataBean();
				if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
					if (days.get(j).equals("Sunday"))
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
				tablelist.setSubjectCode(classes.get(0));
				tablelist.setSubjectCode1(classes.get(1));
				tablelist.setSubjectCode2(classes.get(2));
				tablelist.setSubjectCode3(classes.get(3));
				tablelist.setSubjectCode4(classes.get(4));
				tablelist.setSubjectCode5(classes.get(5));
				tablelist.setSubjectCode6(classes.get(6));
				tablelist.setSubjectCode7(classes.get(7));
				tablelist.setTeaID(times.get(0));tablelist.setTeaID1(times.get(1));tablelist.setTeaID2(times.get(2));tablelist.setTeaID3(times.get(3));
				tablelist.setTeaID4(times.get(4));tablelist.setTeaID5(times.get(5));tablelist.setTeaID6(times.get(6));tablelist.setTeaID7(times.get(7));
				if (count > 0) {
					tablelist.setExSubject(subjects.get(8));
					tablelist.setExSubjectcode(classes.get(8));
					tablelist.setTeaID8(times.get(8));
				}
				if (count1 > 0) {
					tablelist.setExSubject1(subjects.get(9));
					tablelist.setExSubjectcode1(classes.get(9));
					tablelist.setTeaID9(times.get(9));
				}
				classtimelist.add(tablelist);
			}
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception -->"+e.getMessage());
		}
	}
	
	private void timetableparstudent(List<String> periods, List<String> days, int schoolid,
			int count, int count1, List<ClassTimeTableDataBean> classtimelist, List<StudentClass> studentClass) {
		logger.info("-----------Inside timetableparstudent method()----------------");
		String dates = "";
		String exdate = "";
		List<String> subjects = null;
		List<String> classes = null;List<String> times = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Query v=null;
		try{
			for (int j = 0; j < days.size(); j++) {
				subjects = new ArrayList<String>();times = new ArrayList<String>();
				for (int i = 0; i < periods.size(); i++) {
					String subject = "-";
					String time="";
					v = null;
					v = entitymanager.createQuery(
							"from ClassTimeTable where period=? and school_ID=? and day=?");
					v.setParameter(1, periods.get(i));
					v.setParameter(2, schoolid);
					v.setParameter(3, days.get(j));
					List<ClassTimeTable> classtime = (List<ClassTimeTable>) v.getResultList();
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
										if(schoolid==2 || schoolid==3|| schoolid==4|| schoolid==5|| schoolid==6){
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
										if(schoolid==2 || schoolid==3|| schoolid==4|| schoolid==5|| schoolid==6){
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
					} else {
						subject = "-";
						time="";
					}
					subjects.add(subject);
					times.add(time);
				}
				ClassTimeTableDataBean tablelist = new ClassTimeTableDataBean();
				if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
					if (days.get(j).equals("Sunday"))
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
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public List<String> classeslist(String personID) {
		logger.info("-----------Inside classeslist method()----------------");
		List<String> classes = new ArrayList<String>();
		List<Person> roll = null;
		Query q = null;
		try {
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					logger.debug("school id -- > " + schoolid);
					if (roll.get(0).getPersonRole().equals("Admin")) {
						q = entitymanager.createQuery("from Class where school_ID=? and status=?");
						q.setParameter(1, schoolid);
						q.setParameter(2, "Active");
						List<Class> classs = (List<Class>) q.getResultList();
						if (classs.size() > 0) {
							for (int i = 0; i < classs.size(); i++) {
								String classname = classs.get(i).getClassName() + "/" + classs.get(i).getClassSection();
								classes.add(classname);
							}
							HashSet<String> hashclass = new HashSet<String>(classes);
							classes.clear();
							classes.addAll(hashclass);
						}
					} else if (roll.get(0).getPersonRole().equals("Parent")) {
						q = entitymanager.createQuery("from Parent where person_ID=? and status=?");
						q.setParameter(1, personID);
						q.setParameter(2, "Active");
						List<Parent> parent = (List<Parent>) q.getResultList();
						if (parent.size() > 0) {
							q = null;
							q = entitymanager.createQuery("from StudentParent where parent_ID=? and status='Active'");
							q.setParameter(1, parent.get(0).getParent_ID());
							List<StudentParent> studentParent = (List<StudentParent>) q.getResultList();
							if (studentParent.size() > 0) {
								for (StudentParent students : studentParent) {
									q = null;
									q = entitymanager
											.createQuery("from StudentClass where student_ID=? and status='Active'");
									q.setParameter(1, students.getStudent().getStudent_ID());
									List<StudentClass> studentClass = (List<StudentClass>) q.getResultList();
									if (studentClass.size() > 0) {
										classes.add(studentClass.get(0).getClazz().getClassName() + "/"
												+ studentClass.get(0).getClazz().getClassSection());
									}
								}
								HashSet<String> hashclass = new HashSet<String>(classes);
								classes.clear();
								classes.addAll(hashclass);
							}
						}
					}

				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return classes;
	}

	public List<String> ClassListAttView(String personID, AttendanceDataBean attendanceDataBean) {
		logger.info("-----------Inside ClassListAttView method()----------------");
		List<String> classname = new ArrayList<String>();
		List<Person> person = null;
		Query q = null;
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					int schoolid = person.get(0).getSchool().getSchool_ID();
					logger.debug("school id -- > " + schoolid);
					if (person.get(0).getPersonRole().equals("Teacher")) {
						logger.info("inside teacher -- ");
						q = entitymanager.createQuery("from Teacher where school_ID=? and person_ID=? and status=?");
						q.setParameter(1, schoolid);
						q.setParameter(2, personID);
						q.setParameter(3, "Active");
						List<Teacher> teacher = (List<Teacher>) q.getResultList();
						if (teacher.size() > 0) {
							q = entitymanager.createQuery("from TeacherClass where teacher_ID=? and status=?");
							q.setParameter(1, teacher.get(0).getTeacher_ID());
							q.setParameter(2, "Active");
							List<TeacherClass> teacherclass = (List<TeacherClass>) q.getResultList();
							logger.debug("tecaher class size "+teacherclass.size());
							if (teacherclass.size() > 0) {
								for (int i = 0; i < teacherclass.size(); i++) {
									String classs = teacherclass.get(i).getClazz().getClassName() + "/"
											+ teacherclass.get(i).getClazz().getClassSection();
									classname.add(classs);
								}
							}

							HashSet<String> classlist = new HashSet<String>(classname);
							classname.clear();
							classname.addAll(classlist);
						}
					}else if (person.get(0).getPersonRole().equals("Parent")) {
						logger.info("inside parent -- ");
						q = entitymanager.createQuery("from Parent where school_ID=? and person_ID=? and status=?");
						q.setParameter(1, schoolid);
						q.setParameter(2, personID);
						q.setParameter(3, "Active");
						List<Parent> parent = (List<Parent>) q.getResultList();
						if (parent.size() > 0) {
							q = entitymanager.createQuery("from StudentParent where parent_ID=? and status=?");
							q.setParameter(1, parent.get(0).getParent_ID());
							q.setParameter(2, "Active");
							List<StudentParent> studentParent = (List<StudentParent>) q.getResultList();
							if (studentParent.size() > 0) {
								for (int i = 0; i < studentParent.size(); i++) {
									q = entitymanager.createQuery("from StudentClass where student_ID=? and status=?");
									q.setParameter(1, studentParent.get(i).getStudent().getStudent_ID());
									q.setParameter(2, "Active");
									List<StudentClass> studentClass = (List<StudentClass>) q.getResultList();
									if (studentClass.size() > 0) {
										String classs = studentClass.get(0).getClazz().getClassName() + "/"
												+ studentClass.get(0).getClazz().getClassSection();
										classname.add(classs);
									}
								}
							}
							HashSet<String> classlist = new HashSet<String>(classname);
							classname.clear();
							classname.addAll(classlist);
						}
					} else if (person.get(0).getPersonRole().equals("Student")) {
						logger.info("inside student -- ");
						q = entitymanager.createQuery("from Student where school_ID=? and person_ID=? and status=?");
						q.setParameter(1, schoolid);
						q.setParameter(2, personID);
						q.setParameter(3, "Active");
						List<Student> student = (List<Student>) q.getResultList();
						if (student.size() > 0) {							
							q = entitymanager.createQuery("from StudentClass where student_ID=? and status=?");
							q.setParameter(1, student.get(0).getStudent_ID());
							q.setParameter(2, "Active");
							List<StudentClass> studentClass = (List<StudentClass>) q.getResultList();
							if (studentClass.size() > 0) {
								String classs = studentClass.get(0).getClazz().getClassName() + "/"
										+ studentClass.get(0).getClazz().getClassSection();
								classname.add(classs);
							}
							HashSet<String> classlist = new HashSet<String>(classname);
							classname.clear();
							classname.addAll(classlist);
						}
					} else {
						q = entitymanager.createQuery("from Class where school_ID=? and status='Active'");
						q.setParameter(1, schoolid);
						List<com.stumosys.shared.Class> classes = (List<com.stumosys.shared.Class>) q.getResultList();
						if (classes.size() > 0) {
							for (int i = 0; i < classes.size(); i++) {
								String classs = classes.get(i).getClassName() + "/" + classes.get(i).getClassSection();
								classname.add(classs);
							}
							HashSet<String> classlist = new HashSet<String>(classname);
							classname.clear();
							classname.addAll(classlist);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return classname;
	}

public String leaveRequsetView(AttendanceDataBean attendanceDataBean, String personID) {
	logger.info("-----------Inside leaveRequsetView method()----------------");
		Query v = null;
		List<Person> roll = null;List<Leaverequest> leaverequest=null;
		List<AttendanceDataBean> leavelist = new ArrayList<AttendanceDataBean>();
		try {
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					if (roll.get(0).getPersonRole().equals("Parent")) {
						logger.info("inside parent -- ");
						v = entitymanager.createQuery("from Parent where school_ID=? and person_ID=? and status=?");
						v.setParameter(1, schoolid);
						v.setParameter(2, personID);
						v.setParameter(3, "Active");
						List<Parent> parent = (List<Parent>) v.getResultList();
						if (parent.size() > 0) {
							v=null;
							v = entitymanager.createQuery("from Leaverequest where leaveDate=? and className=? and status=? and parent_ID=?");
							v.setParameter(1, attendanceDataBean.getDate());
							v.setParameter(2, attendanceDataBean.getClassname());
							v.setParameter(3, "Active");
							v.setParameter(4, parent.get(0).getParent_ID());
							leaverequest = (List<Leaverequest>) v.getResultList();
						}
					} else if (roll.get(0).getPersonRole().equals("Student")) {
						logger.info("inside student -- ");
						v = entitymanager.createQuery("from Student where school_ID=? and person_ID=?");
						v.setParameter(1, schoolid);
						v.setParameter(2, personID);
						List<Student> student = (List<Student>) v.getResultList();
						if (student.size() > 0) {
							v=null;
							v = entitymanager.createQuery("from Leaverequest where leaveDate=? and className=? and status=? and student_ID=?");
							v.setParameter(1, attendanceDataBean.getDate());
							v.setParameter(2, attendanceDataBean.getClassname());
							v.setParameter(3, "Active");
							v.setParameter(4, student.get(0).getRollNumber());
							leaverequest = (List<Leaverequest>) v.getResultList();
						}
					} else {
						v = entitymanager.createQuery(
								"from Leaverequest where leaveDate=? and className=? and status=?");
						v.setParameter(1, attendanceDataBean.getDate());
						v.setParameter(2, attendanceDataBean.getClassname());
						v.setParameter(3, "Active");
						leaverequest = (List<Leaverequest>) v.getResultList();
					}	
					if (leaverequest.size() > 0) {
						int no = 1;
						for (int i = 0; i < leaverequest.size(); i++) {
							v=null;
							v=entitymanager.createQuery("from Student where rollNumber=? and status='Active'");
							v.setParameter(1, leaverequest.get(i).getStudent_ID());
							List<Student> studentlist=(List<Student>)v.getResultList();
							if(studentlist.size()>0){
								v=null;
								v=entitymanager.createQuery("from StudentDetail where student_ID=?");
								v.setParameter(1, studentlist.get(0).getStudent_ID());
								List<StudentDetail> studentdetail=(List<StudentDetail>)v.getResultList();
								if(studentdetail.size()>0){
									attendanceDataBean.setStudentName(studentdetail.get(0).getFirstName()+studentdetail.get(0).getLastName());
								}
							}
							AttendanceDataBean leave = new AttendanceDataBean();
							leave.setStudentID(leaverequest.get(i).getStudent_ID());
							leave.setLeavereason(leaverequest.get(i).getLeaveReason());
							leave.setStudentName(attendanceDataBean.getStudentName());
							leave.setSno(no);
							leave.setDate(leaverequest.get(i).getLeaveDate());
							leave.setStatus(leaverequest.get(i).getApprovalStatus());
							if(leaverequest.get(i).getApprovalStatus().equalsIgnoreCase("Waiting")) {
								leave.setFlag(true);leave.setFlag1(false);
							}
							else {
								leave.setFlag(false);leave.setFlag1(true);
							}
							leavelist.add(leave);
							attendanceDataBean.setStudentList(leavelist);
							no++;
						}
					} else {
						attendanceDataBean.setStudentList(leavelist);
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}
	
	@Transactional(value = "transactionManager")
	public String leaveRequsetApproval(AttendanceDataBean attendanceDataBean, String personID) {
		logger.info("-----------Inside leaveRequsetApproval method()----------------");
		Query v = null;
		List<Person> roll = null;
		Calendar now = Calendar.getInstance();
		try {
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					v = entitymanager.createQuery(
							"from Leaverequest where leaveDate=? and className=? and status=? and student_ID=?");
					v.setParameter(1, attendanceDataBean.getDate());
					v.setParameter(2, attendanceDataBean.getClassname());
					v.setParameter(3, "Active");
					v.setParameter(4, attendanceDataBean.getStudentID());
					List<Leaverequest> leaverequest = (List<Leaverequest>) v.getResultList();
					if (leaverequest.size() > 0) {
						Leaverequest approved = entitymanager.find(Leaverequest.class,
								leaverequest.get(0).getLeave_requset_ID());
						approved.setApprovalStatus("Approved");
						entitymanager.merge(approved);
						v = null;
						v = entitymanager.createQuery("from Student where rollNumber=? and status=?");
						v.setParameter(1, attendanceDataBean.getStudentID());
						v.setParameter(2, "Active");
						List<Student> student = (List<Student>) v.getResultList();
						if (student.size() > 0) {
							v = null;
							v = entitymanager.createQuery("from StudentDetail where student_ID=?");
							v.setParameter(1, student.get(0).getStudent_ID());
							List<StudentDetail> studentDetail = (List<StudentDetail>) v.getResultList();
							if (studentDetail.size() > 0) {
								StringTokenizer stringtoken = new StringTokenizer(attendanceDataBean.getClassname());
								String className = stringtoken.nextToken("/");
								String section = attendanceDataBean.getClassname();
								String sectionName = section.substring(section.lastIndexOf("/") + 1);
								v = null;
								v = entitymanager.createQuery(
										"from Attendance where class_=? and section=? and date=? and school_ID=?");
								v.setParameter(1, className);
								v.setParameter(2, sectionName);
								v.setParameter(3, attendanceDataBean.getDate());
								v.setParameter(4, schoolid);
								List<Attendance> attendance = (List<Attendance>) v.getResultList();
								String year = "" + attendanceDataBean.getDate().getYear();
								year = year.substring(1, 3);
								if (attendance.size() > 0) {
									v = null;
									v = entitymanager
											.createQuery("from Attendanceclass where student_ID=? and attendance_ID=?");
									v.setParameter(1, attendanceDataBean.getStudentID());
									v.setParameter(2, attendance.get(0).getAttendance_ID());
									List<Attendanceclass> attendanceClass = (List<Attendanceclass>) v.getResultList();
									if (attendanceClass.size() > 0) {
										for (int i = 0; i < attendanceClass.size(); i++) {
											Attendanceclass leaveapproved = entitymanager.find(Attendanceclass.class,
													attendanceClass.get(i).getAtt_class_ID());
											leaveapproved.setStatus("Leave");
											entitymanager.merge(leaveapproved);
											entitymanager.flush();
											entitymanager.clear();
										}
									}
								} else {
									Attendance attendancerequset = new Attendance();
									attendancerequset.setSchool(entitymanager.find(School.class, schoolid));
									attendancerequset.setClass_(className);
									attendancerequset.setSection(sectionName);
									attendancerequset.setDate(attendanceDataBean.getDate());
									entitymanager.persist(attendancerequset);
									v = null;
									v = entitymanager.createQuery("from Attendance");
									List<Attendance> attendance1 = (List<Attendance>) v.getResultList();
									if (attendance1.size() > 0) {
										for (int i = 0; i < 8; i++) {
											Attendanceclass leaveapproved = new Attendanceclass();
											leaveapproved.setAttendance(entitymanager.find(Attendance.class,
													attendance1.get(attendance1.size() - 1).getAttendance_ID()));
											leaveapproved.setStudentName(attendanceDataBean.getStudentName());
											leaveapproved.setStudent_ID(attendanceDataBean.getStudentID());
											leaveapproved.setStatus("Leave");
											leaveapproved.setPeriod("" + (i + 1));
											if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5|| schoolid==6){
												leaveapproved.setTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
											}else{
												leaveapproved.setTime(timestamp);
											}	
											leaveapproved.setStudentName(studentDetail.get(0).getFirstName()
													+ studentDetail.get(0).getLastName());
											leaveapproved.setMonth("" + (attendanceDataBean.getDate().getMonth() + 1));
											leaveapproved.setYear("20" + year);
											entitymanager.persist(leaveapproved);
											entitymanager.flush();
											entitymanager.clear();
										}
									}
								}
								int c = 0, c1 = 0, c2 = 0, c3 = 0;
								int size = 0;String years=""+now.get(Calendar.YEAR);
								v = null;String month=""+(now.get(Calendar.MONTH) + 1);
								v = entitymanager.createQuery(
										"from Attendanceclass where student_ID=? and month=? and year=?");
								v.setParameter(1, attendanceDataBean.getStudentID());
								v.setParameter(2, month);
								v.setParameter(3, years);
								List<Attendanceclass> attendanceclass = (List<Attendanceclass>) v.getResultList();
								logger.debug("attendance class size -- " + attendanceclass.size());
								size = attendanceclass.size();
								if (attendanceclass.size() > 0) {
									for (int j = 0; j < attendanceclass.size(); j++) {
										if (attendanceclass.get(j).getStatus().equals("Absent")) {
											c++;
										} else if (attendanceclass.get(j).getStatus().equals("Leave")) {
											c1++;
										} else if (attendanceclass.get(j).getStatus().equals("Present")) {
											c2++;
										} else if (attendanceclass.get(j).getStatus().equals("Late")) {
											c3++;
										}
									}
								}
								BigDecimal perc = BigDecimal.valueOf(0);
								if (size == c2) {
									logger.debug("100 % ");
									perc = BigDecimal.valueOf(100);
								}else if(size == (c2+c3)) {
									perc = BigDecimal.valueOf(100);
								} else {
									float v1 = Float.parseFloat("" + c2) / Float.parseFloat("" + size);
									perc = BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(100));
									perc = perc.setScale(1, BigDecimal.ROUND_HALF_UP);
								}
								attendanceDataBean.setPercentage("" + perc + "%");
								v = entitymanager
										.createQuery("from Attendance where school_ID=? and date=? and class_=? and section=?");
								v.setParameter(1, schoolid);
								v.setParameter(2, attendanceDataBean.getDate());
								v.setParameter(3, className);
								v.setParameter(4, sectionName);
								List<Attendance> attendances = (List<Attendance>) v.getResultList();
								if (attendances.size() > 0) {
									v = null;
									v = entitymanager.createQuery(
											"from Attendanceclass where attendance_ID=? and student_ID=?");
									v.setParameter(1, attendances.get(0).getAttendance_ID());
									v.setParameter(2, attendanceDataBean.getStudentID());
									List<Attendanceclass> attendanceclasses = (List<Attendanceclass>) v.getResultList();
									if (attendanceclasses.size() > 0) {
										for (int i = 0; i < attendanceclasses.size(); i++) {
											Attendanceclass percen = entitymanager.find(Attendanceclass.class,
													attendanceclasses.get(i).getAtt_class_ID());
											percen.setPercentage("" + perc + "%");
											entitymanager.merge(percen);
											entitymanager.flush();
											entitymanager.clear();
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
		return "";
	}
	
	@Transactional(value="transactionManager")
	public String leaveRequsetReject(AttendanceDataBean attendanceDataBean,String personID){
		logger.info("-----------Inside leaveRequsetReject method()----------------");
		Query v = null;
		List<Person> roll = null;
		Calendar now = Calendar.getInstance();
		try {
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					v = entitymanager.createQuery(
							"from Leaverequest where leaveDate=? and className=? and status=? and student_ID=?");
					v.setParameter(1, attendanceDataBean.getDate());
					v.setParameter(2, attendanceDataBean.getClassname());
					v.setParameter(3, "Active");
					v.setParameter(4, attendanceDataBean.getStudentID());
					List<Leaverequest> leaverequest = (List<Leaverequest>) v.getResultList();
					if (leaverequest.size() > 0) {
						Leaverequest approved = entitymanager.find(Leaverequest.class,
								leaverequest.get(0).getLeave_requset_ID());
						approved.setApprovalStatus("Rejected");
						entitymanager.merge(approved);						
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}
	
	@Override
	public List<HomeworkDatabean> getHomework(String personID) 
	{
		logger.info("-----------Inside getHomework method()----------------");

		Query q = null;
		int school_ID = 0;
		List<Person> roleStatus = null;
		List<Homework> homewrkList = null;
		List<HomeworkDatabean> homeworkViewList = null;
		try {
			homewrkList = new ArrayList<Homework>();
			homeworkViewList = new ArrayList<HomeworkDatabean>();
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					String role=roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					logger.debug("schoolid "+school_ID);
					logger.debug("role"+role);
					if (school_ID > 0) {
						if(role.equalsIgnoreCase("Librarian") || role.equalsIgnoreCase("BookShop")){
							logger.debug("Not Valid");
						}else if(role.equalsIgnoreCase("Teacher")){
							q=entitymanager.createQuery("from Teacher where rollNumber=? and status='Active'");
							q.setParameter(1, roleStatus.get(0).getPersonRoleNumber());
							List<Teacher> teacher=(List<Teacher>) q.getResultList();
							if(teacher.size()>0){
								q=null;
								q=entitymanager.createQuery("from TeacherClass where teacher_ID=? and status='Active'");
								q.setParameter(1, teacher.get(0).getTeacher_ID());
								List<TeacherClass> teacherClass=(List<TeacherClass>)q.getResultList();
								if(teacherClass.size()>0){
									for (int i = 0; i < teacherClass.size(); i++) {
										q=null;
										q=entitymanager.createQuery("from TeacherSubject where teacher_ID=? and status='Active'");
										q.setParameter(1, teacher.get(0).getTeacher_ID());
										List<TeacherSubject> teacherSubject=(List<TeacherSubject>)q.getResultList();
										if(teacherSubject.size()>0){
											for (int j = 0; j < teacherSubject.size(); j++) {
												q=null;
												q=entitymanager.createQuery("from ClassSubject where subject_ID=? and status='Active'");
												q.setParameter(1, teacherSubject.get(j).getSubject().getSubject_ID());
												List<ClassSubject> classSubject=(List<ClassSubject>)q.getResultList();
												if(classSubject.size()>0){
													for (int k = 0; k < classSubject.size(); k++) {
														if(classSubject.get(k).getClazz().getClass_ID()==teacherClass.get(i).getClazz().getClass_ID()){
															q=entitymanager.createQuery("from Homework where className=? and (subject=? or subject=?) and school_ID=? and status=?");
															q.setParameter(1, teacherClass.get(i).getClazz().getClassName()+"/"+teacherClass.get(i).getClazz().getClassSection());
															q.setParameter(2, classSubject.get(k).getSubject().getSujectName());
															q.setParameter(3, classSubject.get(k).getSubject().getSujectName()+"/"+classSubject.get(k).getSubject().getSubjectCode());
															q.setParameter(4, school_ID);
															q.setParameter(5, "Active");
															homewrkList=q.getResultList();
															if(homewrkList.size() > 0)
															{
																for (int c = 0; c < homewrkList.size(); c++)
																{
																	HomeworkDatabean homework= new HomeworkDatabean();
																	homework.setClassname(homewrkList.get(c).getClassName());
																	homework.setDate(homewrkList.get(c).getDate());
																	homework.setHomework(homewrkList.get(c).getHomeWork());
																	homework.setSubject(homewrkList.get(c).getSubject());
																	homeworkViewList.add(homework);
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
						}else{
						q=entitymanager.createQuery("from Homework where school_ID=? and status=?");
						q.setParameter(1, school_ID);
						q.setParameter(2, "Active");
						homewrkList=q.getResultList();
						logger.debug("homewrkList ==== "+homewrkList.size());
						if(homewrkList.size() > 0)
						{
							for (int i = 0; i < homewrkList.size(); i++)
							{
								HomeworkDatabean homework= new HomeworkDatabean();
								homework.setClassname(homewrkList.get(i).getClassName());
								homework.setDate(homewrkList.get(i).getDate());
								homework.setHomework(homewrkList.get(i).getHomeWork());
								homework.setSubject(homewrkList.get(i).getSubject());
								homeworkViewList.add(homework);
							}
						}
					}
					}
				}
			}
		}
		catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return homeworkViewList;
	}
	
	
	@Override
	public List<String> ClassListbooksale(String personID,
			BookSaleDataBean bookSaleDataBean) {
		logger.info("-----------Inside ClassListbooksale method()----------------");

		List<String> classname = new ArrayList<String>();
		List<Person> person = null;
		Query q = null;
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					int schoolid = person.get(0).getSchool().getSchool_ID();
					logger.debug("school id -- > " + schoolid);
					if (person.get(0).getPersonRole().equals("Teacher")) {
						logger.debug("inside teacher -- ");
						q = entitymanager.createQuery("from Teacher where school_ID=? and person_ID=? and status=?");
						q.setParameter(1, schoolid);
						q.setParameter(2, personID);
						q.setParameter(3, "Active");
						List<Teacher> teacher = (List<Teacher>) q.getResultList();
						if (teacher.size() > 0) {
							q = entitymanager.createQuery("from TeacherClass where teacher_ID=? and status=?");
							q.setParameter(1, teacher.get(0).getTeacher_ID());
							q.setParameter(2, "Active");
							List<TeacherClass> teacherclass = (List<TeacherClass>) q.getResultList();
							if (teacherclass.size() > 0) {
								for (int i = 0; i < teacherclass.size(); i++) {
									String classs = teacherclass.get(i)
											.getClazz().getClassName()
											+ "/"
											+ teacherclass.get(i).getClazz()
													.getClassSection();
									classname.add(classs);
								}
							}

							HashSet<String> classlist = new HashSet<String>(classname);
							classname.clear();
							classname.addAll(classlist);
						}
					} else if (person.get(0).getPersonRole().equals("Parent")) {
						logger.debug("inside parent -- "+schoolid+personID);
						q = entitymanager.createQuery("from Parent where school_ID=? and person_ID=? and status=?");
						q.setParameter(1, schoolid);
						q.setParameter(2, personID);
						q.setParameter(3, "Active");
						List<Parent> parent = (List<Parent>) q.getResultList();
						if (parent.size() > 0) {
							logger.debug("parent -- "+parent.size());
							q = entitymanager.createQuery("from StudentParent where parent_ID=? and status=?");
							q.setParameter(1, parent.get(0).getParent_ID());
							q.setParameter(2, "Active");
							List<StudentParent> studentParent = (List<StudentParent>) q.getResultList();
							if (studentParent.size() > 0) {
								logger.debug("studentParent--"+studentParent.size());
								for (int i = 0; i < studentParent.size(); i++) {
									q = entitymanager.createQuery("from StudentClass where student_ID=? and status=?");
									q.setParameter(1, studentParent.get(i).getStudent().getStudent_ID());
									q.setParameter(2, "Active");
									List<StudentClass> studentClass = (List<StudentClass>) q.getResultList();
									if (studentClass.size() > 0) {
										String classs = studentClass.get(0)
												.getClazz().getClassName()
												+ "/"
												+ studentClass.get(0)
														.getClazz()
														.getClassSection();
										classname.add(classs);
									}
								}
							}
							HashSet<String> classlist = new HashSet<String>(
									classname);
							classname.clear();
							classname.addAll(classlist);
						}
					} else if (person.get(0).getPersonRole().equals("Student")) {
						logger.debug("inside student -- ");
						q = entitymanager.createQuery("from Student where school_ID=? and person_ID=? and status=?");
						q.setParameter(1, schoolid);
						q.setParameter(2, personID);
						q.setParameter(3, "Active");
						List<Student> student = (List<Student>) q.getResultList();
						if (student.size() > 0) {
							q = entitymanager.createQuery("from StudentClass where student_ID=? and status=?");
							q.setParameter(1, student.get(0).getStudent_ID());
							q.setParameter(2, "Active");
							List<StudentClass> studentClass = (List<StudentClass>) q.getResultList();
							if (studentClass.size() > 0) {
								String classs = studentClass.get(0).getClazz()
										.getClassName()
										+ "/"
										+ studentClass.get(0).getClazz()
												.getClassSection();
								classname.add(classs);
							}
							HashSet<String> classlist = new HashSet<String>(classname);
							classname.clear();
							classname.addAll(classlist);
						}
					} else {
						logger.debug("admin --  ");
						q = entitymanager.createQuery("from Class where school_ID=? and status='Active'");
						q.setParameter(1, schoolid);
						List<com.stumosys.shared.Class> classes = (List<com.stumosys.shared.Class>) q.getResultList();
						if (classes.size() > 0) {
							for (int i = 0; i < classes.size(); i++) {
								String classs = classes.get(i).getClassName()
										+ "/"
										+ classes.get(i).getClassSection();
								classname.add(classs);
							}
							HashSet<String> classlist = new HashSet<String>(classname);
							classname.clear();
							classname.addAll(classlist);
						}
					}
				}
			}logger.debug("classname size"+classname.size());
		} catch (Exception e) {
			logger.warn("Exception", e);
			logger.warn("Inside Exception",e);
		}
		return classname;
	}

	@Override
	public List<String> getstudentRollNumber(String personID, String classname) 
	{
		logger.info("-----------Inside getstudentRollNumber method()----------------");
		String status = "Failed";
		List<Person> roll = null;
		String rollType = "Not Valid";
		List<String> rollnumber=new ArrayList<String>();
		Query q = null;
		Query v = null;
		int perid = 0;
		int parentid = 0;
		int studid = 0;int stuids=0;
		try {
			roll = new ArrayList<Person>();
			roll = getRollType(personID);
			if (personID != null) {
				rollType = roll.get(0).getPersonRole();
				int schoolid = roll.get(0).getSchool().getSchool_ID();
				logger.debug("-------roll size----" + roll.size());
				if (roll.size() > 0) {
					rollType = roll.get(0).getPersonRole();
					logger.debug("roll -- " + rollType);
					StringTokenizer st = new StringTokenizer(classname);
					String className = st.nextToken("/");
					String section = classname.substring(classname.lastIndexOf("/") + 1);
					if (rollType.equalsIgnoreCase("Parent")) {
						v = entitymanager.createQuery("from Class where school_ID=? and className=? and classSection=? and status=?");
						v.setParameter(1, schoolid);
						v.setParameter(2, className);
						v.setParameter(3, section);
						v.setParameter(4, "Active");
						List<Class> classes = (List<Class>) v.getResultList();
						if (classes.size() > 0) {
							int classID=classes.get(0).getClass_ID();
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
								for (int i = 0; i < splist.size(); i++)
								{
									studid = splist.get(i).getStudent().getStudent_ID();
									q=null;
									q=entitymanager.createQuery("from StudentClass where class_ID=? and student_ID=?");
									q.setParameter(1, classID);
									q.setParameter(2, studid);
									List<StudentClass> studlist = (List<StudentClass>) q.getResultList();
									if(studlist.size() > 0)
									{
										for (int j = 0; j < studlist.size(); j++) {
											stuids = studlist.get(j).getStudent().getStudent_ID();
											q=null;
											q=entitymanager.createQuery("from StudentDetail where student_ID = ?");
											q.setParameter(1, stuids);
											List<StudentDetail> studentdetailList=(List<StudentDetail>)q.getResultList();
											if(studentdetailList.size()>0){
												rollnumber.add(studentdetailList.get(0).getFirstName()+studentdetailList.get(0).getLastName()+"/"+studlist.get(j).getStudent().getRollNumber());
											}
										}
									}
								}
							}
						}
						}
					
					} else if (rollType.equalsIgnoreCase("Student")) {
						String rollnumberr = roll.get(0).getPersonRoleNumber();
						rollnumber.add(rollnumberr);
					} else if (rollType.equalsIgnoreCase("Teacher")) {
						String rollnumberr = roll.get(0).getPersonRoleNumber();
						rollnumber.add(rollnumberr);
					} else if (rollType.equalsIgnoreCase("Staff") || rollType.equalsIgnoreCase("Librarian")) {
						String rollnumberr = roll.get(0).getPersonRoleNumber();
						rollnumber.add(rollnumberr);
					}else if(rollType.equalsIgnoreCase("Admin") || rollType.equalsIgnoreCase("BookShop")){
						v = entitymanager.createQuery("from Class where school_ID=? and className=? and classSection=? and status=?");
						v.setParameter(1, schoolid);
						v.setParameter(2, className);
						v.setParameter(3, section);
						v.setParameter(4, "Active");
						List<Class> classlist = (List<Class>) v.getResultList();
						if (classlist.size() > 0) {
							int classid=classlist.get(0).getClass_ID();
							q=null;
							q=entitymanager.createQuery("from StudentClass where class_ID=? and status=?");
							q.setParameter(1, classid);
							q.setParameter(2, "Active");
							List<StudentClass> studentlist = (List<StudentClass>) q.getResultList();
							if(studentlist.size()>0){
								for(int i=0;i<studentlist.size();i++){
									stuids = studentlist.get(i).getStudent().getStudent_ID();
									q = null;
									q = entitymanager.createQuery("from StudentDetail where student_ID = ?");
									q.setParameter(1, stuids);
									List<StudentDetail> studentdetailList = (List<StudentDetail>) q.getResultList();
									if (studentdetailList.size() > 0) {
										rollnumber.add(studentdetailList.get(0).getFirstName()+ studentdetailList.get(0).getLastName()+ "/"+ studentlist.get(i).getStudent().getRollNumber());
									}
								}
							}
						}
				}
			}
			}
		} catch (Exception e) {
			logger.warn(e);
		}
		logger.debug("---------outside getroll-----");
		return rollnumber;

	}

	@Override
	public List<LibraryDataBean> getBookListView1(String personID, String categoryname) {
		logger.info("-----------Inside getBookListView1 method()----------------");

		List<LibraryDataBean> resultList = null;
		Query q = null;
		List<Person> roleStatus = null;
		String rollType = "NotValid";
		int school_ID = 0;
		try {
			resultList = new ArrayList<LibraryDataBean>();
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						q = entitymanager
								.createQuery("from Library where school_ID=? and category=? and status='Active'");
						q.setParameter(1, school_ID);
						q.setParameter(2, categoryname);
						List<Library> res = (List<Library>) q.getResultList();
						if (res.size() > 0) {
							for (int i = 0; i < res.size(); i++) {
								String qty=res.get(i).getQuantity();
								if(res.get(i).getQuantity() == null || Integer.parseInt(res.get(i).getQuantity())<=0){
									qty="0";
								}else{
								LibraryDataBean lib = new LibraryDataBean();
								lib.setsNo((String.valueOf(i + 1)));
								lib.setAuthorName(res.get(i).getAuthorName());
								lib.setBookCategory(res.get(i).getCategory());
								lib.setBookDescription(res.get(i).getDescription());
								lib.setBookeditions(res.get(i).getBookEdition());
								lib.setBookPages(res.get(i).getPages());
								lib.setBookPrice(res.get(i).getPrice());
								lib.setBookName(res.get(i).getBookName());
								lib.setBookDueType(res.get(i).getDueType());
								lib.setBookFee(res.get(i).getPenaltyFee());
								lib.setBookQty(res.get(i).getQuantity());
								resultList.add(lib);
							}
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

	public List<String> getSubjectListTeacher(String personID,String classname) {
		logger.info("-----------Inside getSubjectListTeacher method()----------------");

		List<Person> roll = null;
		Query v=null;
		List<String> classsubjet = new ArrayList<String>();
		try {
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					v = entitymanager.createQuery("from Class where className=? and classSection=? and school_ID=? and status=?");
					v.setParameter(1, classname.split("/")[0]);
					v.setParameter(2, classname.split("/")[1]);
					v.setParameter(3, roll.get(0).getSchool().getSchool_ID());
					v.setParameter(4, "Active");
					List<Class> clasz = (List<Class>) v.getResultList();
					if (clasz.size() > 0) {
						v = entitymanager.createQuery("from ClassSubject where class_ID=? and status='Active'");
						v.setParameter(1, clasz.get(0).getClass_ID());
						List<ClassSubject> res = (List<ClassSubject>) v.getResultList();
						if (res.size() > 0) {
							for (int i = 0; i < res.size(); i++) {
								classsubjet.add(
										res.get(i).getSubject().getSujectName() + "/" + res.get(i).getSubject().getSubjectCode());
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return classsubjet;
	}

	@Override
	 public List<String> getRollNumber1(String personID) {
		logger.info("-----------Inside getRollNumber1 method()----------------");
	  String status = "Failed";
	  List<Person> roll = null;
	  String rollType = "Not Valid";
	  String rollnumber = "";
	  List<String> rollnumberlist=new ArrayList<String>();
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
	       logger.debug("splist -- "+splist.size());
	       if (splist.size() > 0) {
	        for (int i = 0; i < splist.size(); i++) 
	        {
	         studid = splist.get(i).getStudent().getStudent_ID();
	         q = null;
	         q = entitymanager.createQuery("from Student where student_ID=? and status='Active'");
	         q.setParameter(1, studid);
	         List<Student> studlist = (List<Student>) q.getResultList();
	         if (studlist.size() > 0) {
	          rollnumber = studlist.get(0).getRollNumber();
	         }
	        rollnumberlist.add(rollnumber); 
	        }
	        
	       }
	      }
	     }
	    }
	   }
	  } catch (Exception e) {
	   logger.warn("inside exception",e);
	  }
	  return rollnumberlist;
	 }
	@Override
	 public List<StudentDataBean> getStudentDetails(String stuRollNo,
	   StudentDataBean studentDataBean) {
		logger.info("-----------Inside getStudentDetails method()----------------");

	  List<StudentDataBean> studentlist=null;
	  Query q=null;
	  int studid=0;
	  StudentDataBean student=new StudentDataBean();
	  if(stuRollNo!=null){
	  try{
	  studentlist=new ArrayList<StudentDataBean>();
	  q = entitymanager.createQuery("from Student where roll_number=?");
	  q.setParameter(1, stuRollNo);
	  List<Student> studlist = (List<Student>) q.getResultList();
	  if (studlist.size() > 0) {
	   studid = studlist.get(0).getStudent_ID();
	  q = null;
	  q = entitymanager.createQuery("from StudentDetail where student_ID=?");
	  q.setParameter(1, studid);
	  List<StudentDetail> studlist1 = (List<StudentDetail>) q.getResultList(); 
	  if(studlist1.size()>0){
	   student.setStudentlist(studlist1);
	   studentlist.add(student); 
	  }
	  q = null;
	  q = entitymanager.createQuery("from ImagePath where student_ID=? and roll_status='Student'");
	  q.setParameter(1, studid);
	  List<ImagePath> imagelist = (List<ImagePath>) q.getResultList();
	  logger.debug("---imagelist size----" + imagelist.size());
	  if(imagelist.size()>0){
	  student.setImageList(imagelist);
	  studentlist.add(student);
	  }
	  }
	  }catch(Exception e){
	   logger.warn("Inside Exception",e);
	  }
	  }
	  return studentlist;
	 }

	@Override
	public String getPerClassList1(String personID,
			PaymentDatabean paymentDatabean) {
		logger.info("-----------Inside getPerClassList1 method()----------------");

		List<String> stt = null;
		List<Person> roll=null;
		int schoolid=0;
		ArrayList<PaymentDatabean> picklist2 = new ArrayList<PaymentDatabean>();
		Query q = null;
		try {
			roll=new ArrayList<Person>();
			if (personID != null) {
				roll = getRollType(personID);
				if(roll.size()>0){
					schoolid=roll.get(0).getSchool().getSchool_ID();
			q = entitymanager.createQuery("from Class where school_ID=? and status='Active'");
			q.setParameter(1, schoolid);
			List<com.stumosys.shared.Class> picklist1 = (List<com.stumosys.shared.Class>) q.getResultList();
			logger.debug("--------list size-----" + picklist1.size());
			if (picklist1.size() > 0) {
				for (int i = 0; i < picklist1.size(); i++) {
					PaymentDatabean pickobj = new PaymentDatabean();
					pickobj.setStuCls1(picklist1.get(i).getClassName() + "/" + picklist1.get(i).getClassSection());
					picklist2.add(pickobj);
					paymentDatabean.setStuClassList(picklist2);
				}
				logger.debug("------domain list size------" + paymentDatabean.getStuClassList().size());
			}
			}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.debug("-------outside  picklist------");
		return "";

	}
	//prema 16/08/2016 getstudentlist for fees registration on payment module
	@Override
	public String getPerClass(PaymentDatabean paymentDatabean) {
		logger.info("Inside getPerClass method() - single");
		Query q = null;
		int studid = 0;
		int clsid = 0;
		ArrayList<PaymentDatabean> rollnolist1 = null;
		try {
			rollnolist1=new ArrayList<PaymentDatabean>();
			StringTokenizer st = new StringTokenizer(paymentDatabean.getPayClassSection());
			String className = st.nextToken("/");
			String section = paymentDatabean.getPayClassSection();
			String sectionName = section.substring(section.lastIndexOf("/") + 1);
			q = entitymanager.createQuery("from Class where class_name=? and class_section=? and school_ID=? and status='Active' ");
			q.setParameter(1, className);
			q.setParameter(2, sectionName);
			q.setParameter(3, Integer.parseInt(paymentDatabean.getParentID()));
			List<Class> clslist = (List<Class>) q.getResultList();
			if (clslist.size() > 0) {
				clsid = clslist.get(0).getClass_ID();
				q = null;
				q = entitymanager.createQuery("from StudentClass where class_ID=? and status='Active'");
				q.setParameter(1, clsid);
				List<StudentClass> clslist1 = (List<StudentClass>) q.getResultList();
				if (clslist1.size() > 0) {
					for (int i = 0; i < clslist1.size(); i++) {
						studid = clslist1.get(i).getStudent().getStudent_ID();
						q = null;
						q = entitymanager.createQuery("from StudentDetail where student_ID=?");
						q.setParameter(1, studid);
						List<StudentDetail> studentlist = (List<StudentDetail>) q.getResultList();
						logger.debug("--------list size-----" + studentlist.size());
						if (studentlist.size() > 0) {
							for (int s = 0; s < studentlist.size(); s++) {
								PaymentDatabean idobj = new PaymentDatabean();
								idobj.setPaymentStudID(studentlist.get(s).getFirstName()+studentlist.get(s).getLastName()+""+"/"+""+clslist1.get(i).getStudent().getRollNumber());
								rollnolist1.add(idobj);
							}
							
						}
					}
				}
			}
			paymentDatabean.setRollnolist(rollnolist1);
		} catch (Exception e) {
			logger.info("-------exception  getPerClass-----");
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.info("-------outside getPerClass-----");
		return "";
	}
  
@Transactional(value = "transactionManager")
	public String insertfees(String personID, PaymentDatabean paymentDatabean) {
	logger.info("-----------Inside insertfees method()----------------");
		List<Person> roleStatus = null;
		String status="Fail";
		int school_ID = 0;
		String invoicenumber="";
		Query q=null;
		try {
			roleStatus = new ArrayList<Person>();
			//FacesContext context = FacesContext.getCurrentInstance();
			//ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
			if (personID != null) {
					roleStatus = getRollType(personID);
					logger.debug("Roll Size" + roleStatus.size());
					if (roleStatus.size() > 0) {
						if (roleStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")) {
							school_ID = roleStatus.get(0).getSchool().getSchool_ID();
							System.out.println("--------school_ID--------------------"+school_ID);
							if (school_ID > 0) {
								String students=paymentDatabean.getPaymentStudID();
								String studentid=students.substring(students.lastIndexOf("/")+1);
								
								if(school_ID==Integer.parseInt(paths.getString("MAHARISHI.SCHOOLID"))){
										q=entitymanager.createQuery("from FeesDetail where student_ID=? and className=? and year=? and month=? and status='Active'");
										q.setParameter(1, studentid);
										q.setParameter(2, paymentDatabean.getPayClassSection());
										q.setParameter(3, paymentDatabean.getYear());
										q.setParameter(4, paymentDatabean.getMonth());
										List<FeesDetail> reslist=(List<FeesDetail>)q.getResultList();
										logger.debug("reslist--------------->"+reslist.size());
										if(reslist.size()>0){
											status="Exist";
										}
										else{
											q=entitymanager.createQuery("from FeesDetail where school_id=?");
											q.setParameter(1, school_ID);
											List<FeesDetail> reslistsize=(List<FeesDetail>)q.getResultList();
											 if(reslistsize.size()>0){
												    int clientSize=reslistsize.size()+1;
												    if(clientSize<=9) invoicenumber="0000"+clientSize;
												    else if(clientSize>9 && clientSize<=99) invoicenumber="000"+clientSize;
												    else if(clientSize>99 && clientSize<=999) invoicenumber="00"+clientSize;
												    else if(clientSize>999 && clientSize<=9999) invoicenumber="0"+clientSize;
												    else invoicenumber=""+clientSize;
												   }else{
													   invoicenumber="00001";
												   }
											FeesDetail feesdeatil = new FeesDetail();
											feesdeatil.setSchoolId(entitymanager.find(School.class, school_ID));
											feesdeatil.setClassName(paymentDatabean.getPayClassSection());
											feesdeatil.setStudent_ID(studentid);
											feesdeatil.setCreatedDate(date);
											feesdeatil.setUploadedDate(date);
											feesdeatil.setInvoiceNumber(invoicenumber);
											feesdeatil.setTuitionFees(paymentDatabean.getTuitionFees());
											feesdeatil.setTransportFees(paymentDatabean.getTransportFees());
											feesdeatil.setExtraFees1(paymentDatabean.getExtraFee1());
											feesdeatil.setExtraFees2(paymentDatabean.getExtraFee2());
											feesdeatil.setApprovalStatus("insert");									
											feesdeatil.setTotalFees(""+new BigDecimal(paymentDatabean.getExtraFee1()).add(new BigDecimal(paymentDatabean.getTuitionFees()).add(new BigDecimal(paymentDatabean.getTransportFees()).add(new BigDecimal(paymentDatabean.getExtraFee2())))));
											feesdeatil.setMonth(paymentDatabean.getMonth());
											feesdeatil.setYear(paymentDatabean.getYear());
											feesdeatil.setPaidAmount("0");
											feesdeatil.setBalanceAmount("0");
											feesdeatil.setPaymentStatus("Not Paid");
											feesdeatil.setStatus("Active");
											entitymanager.persist(feesdeatil);
											paymentDatabean.setTotalFees(""+new BigDecimal(paymentDatabean.getExtraFee1()).add(new BigDecimal(paymentDatabean.getTuitionFees()).add(new BigDecimal(paymentDatabean.getTransportFees()).add(new BigDecimal(paymentDatabean.getExtraFee2())))));
											status = "Success";
											q = null;
											q = entitymanager.createQuery("from Student where rollNumber=? and school_ID=?");
											q.setParameter(1, studentid);
											q.setParameter(2, school_ID);
											List<Student> student = (List<Student>) q.getResultList();
											if (student.size() > 0) {
												q = entitymanager.createQuery("from StudentParent where student_ID=?");
												q.setParameter(1, student.get(0).getStudent_ID());
												List<StudentParent> studentParent = (List<StudentParent>) q.getResultList();
												if (studentParent.size() > 0) {
													paymentDatabean.setMailId(studentParent.get(0).getParent().getParentDetail().getEmaiId());
													paymentDatabean.setParentID(roleStatus.get(0).getPersonRoleNumber());
													paymentDatabean.setPhoneno(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
												}
											}
										
										}
								}else if(school_ID==Integer.parseInt(paths.getString("SCMS.SCHOOLID"))){
									q=entitymanager.createQuery("from FeesDetail where student_ID=? and className=? and year=? and month=? and status='Active'");
									q.setParameter(1, studentid);
									q.setParameter(2, paymentDatabean.getPayClassSection());
									q.setParameter(3, paymentDatabean.getYear());
									q.setParameter(4, paymentDatabean.getMonth());
									List<FeesDetail> reslist=(List<FeesDetail>)q.getResultList();
									logger.debug("reslist--------------->"+reslist.size());
									if(reslist.size()>0){
										status="Exist";
									}
									else{
										FeesDetail feesdeatil = new FeesDetail();
										feesdeatil.setSchoolId(entitymanager.find(School.class, school_ID));
										feesdeatil.setClassName(paymentDatabean.getPayClassSection());
										feesdeatil.setStudent_ID(studentid);
										feesdeatil.setCreatedDate(date);
										feesdeatil.setTuitionFees(paymentDatabean.getTuitionFees());
										feesdeatil.setTransportFees(paymentDatabean.getTransportFees());
										feesdeatil.setSpecialFees(paymentDatabean.getSpecialFees());
										feesdeatil.setEcaFees(paymentDatabean.getEcaFees());
										feesdeatil.setLabFees(paymentDatabean.getLabFees());
										feesdeatil.setAddmissionFees(paymentDatabean.getAdmissionFees());
										feesdeatil.setApprovalStatus("insert");									
										feesdeatil.setTotalFees(paymentDatabean.getTotalFees());
										feesdeatil.setMonth(paymentDatabean.getMonth());
										feesdeatil.setYear(paymentDatabean.getYear());
										feesdeatil.setPaidAmount("0");
										feesdeatil.setBalanceAmount("0");
										feesdeatil.setPaymentStatus("Not Paid");
										feesdeatil.setStatus("Active");
										entitymanager.persist(feesdeatil);
										status = "Success";
										q = null;
										q = entitymanager.createQuery("from Student where rollNumber=? and school_ID=?");
										q.setParameter(1, studentid);
										q.setParameter(2, school_ID);
										List<Student> student = (List<Student>) q.getResultList();
										if (student.size() > 0) {
											q = entitymanager.createQuery("from StudentParent where student_ID=?");
											q.setParameter(1, student.get(0).getStudent_ID());
											List<StudentParent> studentParent = (List<StudentParent>) q.getResultList();
											if (studentParent.size() > 0) {
												paymentDatabean.setMailId(studentParent.get(0).getParent().getParentDetail().getEmaiId());
												paymentDatabean.setParentID(roleStatus.get(0).getPersonRoleNumber());
												paymentDatabean.setPhoneno(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
											}
										}
									
									}
								}
								else{
									FeesDetail feesdeatil = new FeesDetail();

									q=entitymanager.createQuery("from FeesDetail where student_ID=? and className=? and year=? and status=?");
									q.setParameter(1, studentid);
									q.setParameter(2, paymentDatabean .getPayClassSection());
									q.setParameter(3, String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
									q.setParameter(4, "Active");
									List<FeesDetail> reslist=(List<FeesDetail>)q.getResultList();
									
									if(reslist.size()>0){
										status="Exist";
									}
									
									else{
										if(school_ID==Integer.parseInt(paths.getString("SMRV.SCHOOLID"))){
											q=entitymanager.createQuery("from FeesDetail where school_id=?");
											q.setParameter(1, school_ID);
											List<FeesDetail> reslistsize=(List<FeesDetail>)q.getResultList();
											 if(reslistsize.size()>0){
												    int clientSize=reslistsize.size()+1;
												    if(clientSize<=9) invoicenumber="0000"+clientSize;
												    else if(clientSize>9 && clientSize<=99) invoicenumber="000"+clientSize;
												    else if(clientSize>99 && clientSize<=999) invoicenumber="00"+clientSize;
												    else if(clientSize>999 && clientSize<=9999) invoicenumber="0"+clientSize;
												    else invoicenumber=""+clientSize;
												   }else{
													   invoicenumber="00001";
												   }
											 
											 // added by Alex
											 
									}
									//FeesDetail feesdeatil = new FeesDetail();
									feesdeatil.setSchoolId(entitymanager.find(School.class, school_ID));
									feesdeatil.setClassName(paymentDatabean.getPayClassSection());
									feesdeatil.setStudent_ID(studentid);
									if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6||school_ID==44){
										feesdeatil.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
										feesdeatil.setUploadedDate(GetTimeZone.getDate("Asia/Makassar"));
									}else{
										feesdeatil.setCreatedDate(date);
										feesdeatil.setUploadedDate(date);
									}	
									feesdeatil.setTuitionFees(paymentDatabean.getTuitionFees());
									
									if(school_ID==Integer.parseInt(paths.getString("SMRV.SCHOOLID"))){
										feesdeatil.setTransportFees("");
										feesdeatil.setExamFees("");
										feesdeatil.setInvoiceNumber(invoicenumber);
										paymentDatabean.setTransportFees("0");
										paymentDatabean.setExamFees("0");
										}
									else{
										feesdeatil.setTransportFees(paymentDatabean.getTransportFees());
										feesdeatil.setExamFees(paymentDatabean.getExamFees());
									}
									
									feesdeatil.setApprovalStatus("insert");									
									feesdeatil.setTotalFees(""+new BigDecimal(paymentDatabean.getExamFees()).add(new BigDecimal(paymentDatabean.getTuitionFees()).add(new BigDecimal(paymentDatabean.getTransportFees()))));
									feesdeatil.setPaidAmount("0");
									feesdeatil.setBalanceAmount("0");
									feesdeatil.setPaymentStatus("Not Paid");
									feesdeatil.setStatus("Active");
									feesdeatil.setYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
									entitymanager.persist(feesdeatil);
									
									paymentDatabean.setTotalFees(""+new BigDecimal(paymentDatabean.getExamFees()).add(new BigDecimal(paymentDatabean.getTuitionFees()).add(new BigDecimal(paymentDatabean.getTransportFees()))));
									status = "Success";
									q = null;
									q = entitymanager.createQuery("from Student where rollNumber=? and school_ID=?");
									q.setParameter(1, studentid);
									q.setParameter(2, school_ID);
									List<Student> student = (List<Student>) q.getResultList();
									
									if (student.size() > 0) {
										q = entitymanager.createQuery("from StudentParent where student_ID=?");
										q.setParameter(1, student.get(0).getStudent_ID());
										List<StudentParent> studentParent = (List<StudentParent>) q.getResultList();
										if (studentParent.size() > 0) {
											paymentDatabean.setMailId(studentParent.get(0).getParent().getParentDetail().getEmaiId());
											paymentDatabean.setParentID(roleStatus.get(0).getPersonRoleNumber());
											paymentDatabean.setPhoneno(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
										}
									}
								}
								}
								
									
							}
						}
					} else {
						status = "Invalid";
					}
			}

		} catch (Exception e) {
			//logger.warn(" exception - "+e);
			logger.warn("Exception -->"+e.getMessage());
			//e.printStackTrace();
		}
		return status;
	}

/* Prema OCT 25   feesinfomation*/
@Override
public List<PaymentDatabean> feesinfomation(String personID,PaymentDatabean paymentDatabean) {
	logger.info("-----------Inside feesinfomation method()----------------");

	Query q = null;
	List<PaymentDatabean> paymentlist = null;
	List<Person> roleStatus = null;
	int school_ID = 0;
	int studentID=0;
	int i=0;
	try {
		roleStatus = new ArrayList<Person>();
		paymentlist = new ArrayList<PaymentDatabean>();
		if (personID != null) {
			roleStatus = getRollType(personID);
			logger.debug("Roll Size" + roleStatus.size());
			if (roleStatus.size() > 0) {
				school_ID = roleStatus.get(0).getSchool().getSchool_ID();
				if (school_ID > 0) {
					if(roleStatus.get(0).getPersonRole().equalsIgnoreCase("Student")){
						q = entitymanager.createQuery("from FeesDetail where school_id=? and student_ID=? and status='Active'");
						q.setParameter(1, school_ID);
						q.setParameter(2, roleStatus.get(0).getPersonRoleNumber());
					}else{
						if(paths.getString("SCMS.SCHOOLID").equalsIgnoreCase(String.valueOf(school_ID))){
							if(!StringUtils.isEmpty(paymentDatabean.getMonth())){
								q = entitymanager.createQuery("from FeesDetail where school_id=? and className=? and status='Active' and month=?");
								q.setParameter(1, school_ID);
								q.setParameter(2, paymentDatabean.getPayClassSection());
								q.setParameter(3, paymentDatabean.getMonth());
							}else{
								q = entitymanager.createQuery("from FeesDetail where school_id=? and className=? and status='Active'");
								q.setParameter(1, school_ID);
								q.setParameter(2, paymentDatabean.getPayClassSection());
							}
						}else{
							q = entitymanager.createQuery("from FeesDetail where school_id=? and className=? and status='Active'");
							q.setParameter(1, school_ID);
							q.setParameter(2, paymentDatabean.getPayClassSection());
						}
					}
					List<FeesDetail> feesDetail = (List<FeesDetail>) q.getResultList();
					logger.debug("Inside------dao--list" + feesDetail.size());
					logger.debug("Inside------dao--list" + feesDetail.size());
					if (feesDetail.size() > 0) {
						for (FeesDetail fees : feesDetail) {
							PaymentDatabean pay = new PaymentDatabean();
							q=null;
							q=entitymanager.createQuery("from Student where rollNumber=? and status='Active' and school_ID=?");
							q.setParameter(1, fees.getStudent_ID());
							q.setParameter(2, school_ID);
							List<Student> studentlist=(List<Student>)q.getResultList();
							if(studentlist.size()>0){
								q=null;
								q=entitymanager.createQuery("from StudentDetail where student_ID=?");
								q.setParameter(1, studentlist.get(0).getStudent_ID());
								List<StudentDetail> studentDetail=(List<StudentDetail>)q.getResultList();
								if(studentDetail.size()>0){
									pay.setPaymentStuName(studentDetail.get(0).getFirstName()+" "+studentDetail.get(0).getLastName());
								}
							}
							pay.setsNo(String.valueOf(i + 1));
							pay.setPayClassSection(fees.getClassName());
							pay.setPaymentStudID(fees.getStudent_ID());
							pay.setExamFees(fees.getExamFees());
							pay.setTuitionFees(fees.getTuitionFees());
							pay.setTransportFees(fees.getTransportFees());
							pay.setTotalFees(fees.getTotalFees());
							if(roleStatus.get(0).getPersonRole().equalsIgnoreCase("Admin")){
								if(fees.getApprovalStatus().equalsIgnoreCase("insert")){
									pay.setApprovalStatus("Not Upload");
									pay.setUpflag(true);
								}else{
									pay.setApprovalStatus(fees.getApprovalStatus());
									if(fees.getApprovalStatus().equalsIgnoreCase("Rejected")) pay.setUpflag(true);
									else pay.setUpflag(false);
								}								
								if(fees.getApprovalStatus().equalsIgnoreCase("Waiting")) pay.setFlag(true);
								else pay.setFlag(false);
							}else if(roleStatus.get(0).getPersonRole().equalsIgnoreCase("Teacher")){
								pay.setUpflag(false);
								pay.setFlag(false);
								if(fees.getApprovalStatus().equalsIgnoreCase("insert")) pay.setApprovalStatus("Not Upload");
								else pay.setApprovalStatus(fees.getApprovalStatus());
							}
							else if(roleStatus.get(0).getPersonRole().equalsIgnoreCase("Student")){
								pay.setApprovalStatus(fees.getApprovalStatus());
							}
							if(paths.getString("CNPS.SCHOOLID").equalsIgnoreCase(String.valueOf(school_ID))||paths.getString("SCMS.SCHOOLID").equalsIgnoreCase(String.valueOf(school_ID))){
								pay.setStatus(fees.getPaymentStatus());
								pay.setAmount(fees.getPaidAmount());
								pay.setExamFees(fees.getBalanceAmount());
								pay.setDescription(fees.getTermstatus());
								DateFormatSymbols symbols = new DateFormatSymbols();
								pay.setMonth(symbols.getMonths()[Integer.parseInt(fees.getMonth())-1]);
								pay.setSpecialFees(fees.getSpecialFees());
								pay.setEcaFees(fees.getEcaFees());
								pay.setLabFees(fees.getLabFees());
								pay.setAdmissionFees(fees.getAddmissionFees());
							}
							paymentlist.add(pay);
							i++;
						}
					}
				}
				logger.debug("payment list size"+paymentlist.size());
				logger.debug("payment list size"+paymentlist.size());
			}
		}
	} catch (Exception e) {
		logger.warn("Exception -->"+e.getMessage());
	}
	return paymentlist;
}


	@Override
	public String paymentview(String personID,
			PaymentDatabean paymentDatabean) {
		logger.info("-----------Inside paymentview method()----------------");

		Query q = null;
		Query q1 = null;
		List<PaymentDatabean> resultList = null;
		List<Person> roleStatus = null;
		int school_ID = 0;
		try {
			roleStatus = new ArrayList<Person>();
			resultList = new ArrayList<PaymentDatabean>();
			if (paymentDatabean.getPaymentStudID() != null) {
				if (personID != null) {
					roleStatus = getRollType(personID);
					logger.debug("Roll Size" + roleStatus.size());
					if (roleStatus.size() > 0) {
						school_ID = roleStatus.get(0).getSchool().getSchool_ID();
						if (school_ID > 0) {
							q = entitymanager.createQuery("from FeesDetail where student_ID=? and className=?  and school_id=? and status='Active'");
							q.setParameter(1, paymentDatabean.getPaymentStudID());
							q.setParameter(2, paymentDatabean.getPayClassSection());
							q.setParameter(3, school_ID);
							List<FeesDetail> result = (List<FeesDetail>) q.getResultList();
							if (result.size() > 0) {
								paymentDatabean.setPayClassSection(result.get(0).getClassName());
								paymentDatabean.setPaymentStudID(paymentDatabean.getPaymentStuName()+"/"+result.get(0).getStudent_ID());
								paymentDatabean.setExamFees(result.get(0).getExamFees());
								paymentDatabean.setTuitionFees(result.get(0).getTuitionFees());
								paymentDatabean.setTransportFees(result.get(0).getTransportFees());
								paymentDatabean.setTotalFees(result.get(0).getTotalFees());
								paymentDatabean.setFilePath(result.get(0).getFileUpload());
								DateFormatSymbols symbols = new DateFormatSymbols();
								paymentDatabean.setMonth(symbols.getMonths()[Integer.parseInt(result.get(0).getMonth())-1]);
								paymentDatabean.setSpecialFees(result.get(0).getSpecialFees());
								paymentDatabean.setEcaFees(result.get(0).getEcaFees());
								paymentDatabean.setLabFees(result.get(0).getLabFees());
								paymentDatabean.setAdmissionFees(result.get(0).getAddmissionFees());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}

	@Override
	@Transactional(value="transactionManager")
	public String updatePayment(String personID, PaymentDatabean paymentDatabean) {
		logger.info("-----------Inside updatePayment method()----------------");

		List<Person> roleStatus = null;
		int school_ID = 0;
		Query q = null;
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
							if (paymentDatabean.getPaymentStudID() != null) {
								String students=paymentDatabean.getPaymentStudID();
								String studentid=students.substring(students.lastIndexOf("/")+1);
								if(school_ID==Integer.parseInt(paths.getString("SCMS.SCHOOLID"))){
									q=entitymanager.createQuery("from FeesDetail where student_ID=? and className=? and year=? and month=? and status='Active'");
									q.setParameter(1, studentid);
									q.setParameter(2, paymentDatabean.getPayClassSection());
									q.setParameter(3, paymentDatabean.getYear());
									q.setParameter(4, paymentDatabean.getMonth());
								}else{
									q = entitymanager.createQuery("from FeesDetail where student_ID=? and className=?  and school_id=? and status='Active'");
									q.setParameter(1, studentid);
									q.setParameter(2, paymentDatabean.getPayClassSection());
									q.setParameter(3, school_ID);
								}
									List<FeesDetail> result = (List<FeesDetail>) q.getResultList();
									if (result.size() > 0) {
										FeesDetail feesDetail = entitymanager.find(FeesDetail.class, result.get(0).getPaymentId());
										if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
											feesDetail.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
										}else{
											feesDetail.setCreatedDate(date);
										}
										feesDetail.setStatus("Active");
										feesDetail.setExamFees(paymentDatabean.getExamFees());
										feesDetail.setTuitionFees(paymentDatabean.getTuitionFees());
										feesDetail.setTransportFees(paymentDatabean.getTransportFees());
										if(school_ID==Integer.parseInt(paths.getString("SCMS.SCHOOLID"))){
											feesDetail.setTotalFees(paymentDatabean.getTotalFees());
											if("6".equalsIgnoreCase(paymentDatabean.getMonth())){
												feesDetail.setAddmissionFees(paymentDatabean.getAdmissionFees());
												feesDetail.setLabFees(paymentDatabean.getLabFees());
												feesDetail.setSpecialFees(paymentDatabean.getSpecialFees());
												feesDetail.setEcaFees(paymentDatabean.getEcaFees());
											}
										}else{
											feesDetail.setTotalFees(""+new BigDecimal(paymentDatabean.getExamFees()).add(new BigDecimal(paymentDatabean.getTuitionFees()).add(new BigDecimal(paymentDatabean.getTransportFees()))));
											paymentDatabean.setTotalFees(""+new BigDecimal(paymentDatabean.getExamFees()).add(new BigDecimal(paymentDatabean.getTuitionFees())
											.add(new BigDecimal(paymentDatabean.getTransportFees()))));
										}
										entitymanager.merge(feesDetail);
										status = "Success";
									q = null;
									q = entitymanager.createQuery("from Student where rollNumber=? and school_ID=?");
									q.setParameter(1, studentid);
									q.setParameter(2, school_ID);
									List<Student> student = (List<Student>) q.getResultList();
									if (student.size() > 0) {
										q = entitymanager.createQuery("from StudentParent where student_ID=?");
										q.setParameter(1, student.get(0).getStudent_ID());
										List<StudentParent> studentParent = (List<StudentParent>) q.getResultList();
										if (studentParent.size() > 0) {
											paymentDatabean.setMailId(studentParent.get(0).getParent().getParentDetail().getEmaiId());
											paymentDatabean.setParentID(roleStatus.get(0).getPersonRoleNumber());
											paymentDatabean.setPhoneno(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
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
	
	@Override
	@Transactional(value="transactionManager")
	public String deletePayment(String personID, PaymentDatabean paymentDatabean) {
		logger.info("-----------Inside deletePayment method()----------------");

		List<Person> roleStatus = null;
		int school_ID = 0;
		Query q = null;
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
							if (paymentDatabean.getPaymentStudID() != null) {
								String students=paymentDatabean.getPaymentStudID();
								String studentid=students.substring(students.lastIndexOf("/")+1);
								if(school_ID==Integer.parseInt(paths.getString("SCMS.SCHOOLID"))){
									q=entitymanager.createQuery("from FeesDetail where student_ID=? and className=? and year=? and month=? and status='Active'");
									q.setParameter(1, studentid);
									q.setParameter(2, paymentDatabean.getPayClassSection());
									q.setParameter(3, paymentDatabean.getYear());
									q.setParameter(4, paymentDatabean.getMonth());
								}else{
									q = entitymanager.createQuery("from FeesDetail where student_ID=? and className=?  and school_id=? and status='Active'");
									q.setParameter(1,studentid );
									q.setParameter(2, paymentDatabean.getPayClassSection());
									q.setParameter(3, school_ID);
								}
								List<FeesDetail> result = (List<FeesDetail>) q.getResultList();
								if (result.size() > 0) {
									FeesDetail feesDetail = entitymanager.find(FeesDetail.class, result.get(0).getPaymentId());
									feesDetail.setStatus("DeActive");
									entitymanager.merge(feesDetail);
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


	public List<String> classStudent(String personID, String string){
		logger.info("-----------Inside classStudent method()----------------");

		Query v=null;
		List<String> students=null;
		List<Person> roll=null;
		try{
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					StringTokenizer st = new StringTokenizer(string);
					String className = st.nextToken("/");
					String sectionName = string;
					String section = sectionName.substring(sectionName.lastIndexOf("/") + 1);
					v=entitymanager.createQuery("from Class where className=? and classSection=? and "
							+ "school_ID=? and status='Active'");
					v.setParameter(1, className);
					v.setParameter(2, section);
					v.setParameter(3, roll.get(0).getSchool().getSchool_ID());
					List<Class> classes=(List<Class>)v.getResultList();
					if(classes.size()>0){
						v=null;
						v=null;
						v=entitymanager.createQuery("from Parent where rollNumber=? and status='Active'");
						v.setParameter(1, roll.get(0).getPersonRoleNumber());
						List<Parent> parent=(List<Parent>)v.getResultList();
						if(parent.size()>0){
							v=null;
							v=entitymanager.createQuery("from StudentClass where class_ID=? and status='Active'");
							v.setParameter(1, classes.get(0).getClass_ID());
							List<StudentClass> studentClass=(List<StudentClass>)v.getResultList();
							if(studentClass.size()>0){
								students=new ArrayList<String>();
								for (int i = 0; i < studentClass.size(); i++) {
									v=entitymanager.createQuery("from StudentParent where student_ID=? and parent_ID=? and status='Active'");
									v.setParameter(1, studentClass.get(i).getStudent().getStudent_ID());
									v.setParameter(2, parent.get(0).getParent_ID());
									List<StudentParent> studentParent=(List<StudentParent>)v.getResultList();
									if(studentParent.size()>0){
										for (int j = 0; j < studentParent.size(); j++) {
											v=null;
											v=entitymanager.createQuery("from StudentDetail where student_ID=?");
											v.setParameter(1, studentParent.get(j).getStudent().getStudent_ID());
											List<StudentDetail> studentdetail=(List<StudentDetail>)v.getResultList();
											if(studentdetail.size()>0){
												students.add(studentdetail.get(0).getFirstName()+studentdetail.get(0).getLastName()+"/"+studentClass.get(i).getStudent().getRollNumber());
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
		catch(Exception e){
			logger.warn("inside exception "+e);
		}
		return students;
	}
	
	public String classStudentFeesView(String personID,PaymentDatabean paymentDatabean){
		logger.info("-----------Inside classStudentFeesView method()----------------");

		//logger.debug("fees view dao ");
		Query v=null;
		List<Person> roll=null;
		try{
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					String students= paymentDatabean.getPaymentStudID();
					String studentid=students.substring(students.lastIndexOf("/")+1);
					v=entitymanager.createQuery("from FeesDetail where className=? and student_ID=? and school_id=? and status='Active'");
					v.setParameter(1, paymentDatabean.getPayClassSection());
					v.setParameter(2, studentid);
					v.setParameter(3, roll.get(0).getSchool().getSchool_ID());
					List<FeesDetail> feesDetail=(List<FeesDetail>)v.getResultList();
					if(feesDetail.size()>0){
						paymentDatabean.setExamFees(feesDetail.get(0).getExamFees());
						paymentDatabean.setTuitionFees(feesDetail.get(0).getTuitionFees());
						paymentDatabean.setTransportFees(feesDetail.get(0).getTransportFees());	
						paymentDatabean.setTotalFees(feesDetail.get(0).getTotalFees());
						if(feesDetail.get(0).getApprovalStatus().equalsIgnoreCase("insert")){
							paymentDatabean.setApprovalStatus("Not Upload");
						}else{
							paymentDatabean.setApprovalStatus(feesDetail.get(0).getApprovalStatus());
						}
						if(feesDetail.get(0).getApprovalStatus().equalsIgnoreCase("insert") ||
								feesDetail.get(0).getApprovalStatus().equalsIgnoreCase("Rejected")){							
							paymentDatabean.setPayflag(true);
							paymentDatabean.setFlag(true);
							paymentDatabean.setFlag1(false);
						}else if(feesDetail.get(0).getApprovalStatus().equalsIgnoreCase("Waiting") ||
								feesDetail.get(0).getApprovalStatus().equalsIgnoreCase("Approved")){
							paymentDatabean.setPayflag(false);
							paymentDatabean.setFlag(false);
							paymentDatabean.setFlag1(true);
						}	
					}else{
						paymentDatabean.setExamFees("");
						paymentDatabean.setTuitionFees("");
						paymentDatabean.setTransportFees("");
						paymentDatabean.setTotalFees("");
						paymentDatabean.setFlag(false);
						paymentDatabean.setFlag1(false);
					}
				}
			}
		}
		catch(Exception e){
			logger.warn("inside exception "+e);
		}
		return "";
	}
	
	@Transactional(value="transactionManager")
	public String feespay(String personID, PaymentDatabean paymentDatabean){
		logger.info("-----------Inside feespay method()----------------");

		Query v=null;
		List<Person> roll=null;
		try{
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int school_ID=roll.get(0).getSchool().getSchool_ID();
					paymentDatabean.setSchool_Name(roll.get(0).getSchool().getName());
					String students=paymentDatabean.getPaymentStudID();
					String studentid=students.substring(students.lastIndexOf("/")+1);
					v=entitymanager.createQuery("from FeesDetail where className=? and student_ID=?"
							+ " and school_id=? and status='Active'");
					v.setParameter(1, paymentDatabean.getPayClassSection());
					v.setParameter(2, studentid);
					v.setParameter(3, roll.get(0).getSchool().getSchool_ID());
					List<FeesDetail> feesDetail=(List<FeesDetail>)v.getResultList();
					if(feesDetail.size()>0){
						FeesDetail feesPay=entitymanager.find(FeesDetail.class, feesDetail.get(0).getPaymentId());
						feesPay.setApprovalStatus("Waiting");
						if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
							feesPay.setUploadedDate(GetTimeZone.getDate("Asia/Makassar"));
						}else{
							feesPay.setUploadedDate(date);
						}	
						feesPay.setFileUpload(paymentDatabean.getFilePath());
						entitymanager.merge(feesPay);
						paymentDatabean.setMailId(roll.get(0).getSchool().getEmailId());
						paymentDatabean.setParentID(roll.get(0).getPersonRoleNumber());
						paymentDatabean.setPhoneno(roll.get(0).getSchool().getPhoneNumber());
					}
				}
			}
		}
		catch(Exception e){
			logger.warn("inside exception "+e);
		}
		return "";
	}

	@Transactional(value="transactionManager")
	public String approveRejectFees(String personID,PaymentDatabean paymentDatabean){
		logger.info("-----------Inside approveRejectFees method()----------------");

		Query v=null;
		List<Person> roll=null;
		try{
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int school_ID=roll.get(0).getSchool().getSchool_ID();
					v=entitymanager.createQuery("from FeesDetail where className=? and student_ID=?"
							+ " and school_id=? and status='Active'");
					v.setParameter(1, paymentDatabean.getPayClassSection());
					v.setParameter(2, paymentDatabean.getPaymentStudID());
					v.setParameter(3, roll.get(0).getSchool().getSchool_ID());
					List<FeesDetail> feesDetail=(List<FeesDetail>)v.getResultList();
					if(feesDetail.size()>0){
						FeesDetail feesPay=entitymanager.find(FeesDetail.class, feesDetail.get(0).getPaymentId());						
						if(paymentDatabean.getStatus().equalsIgnoreCase("Approved")){
							feesPay.setApprovalStatus("Approved");
						}else if(paymentDatabean.getStatus().equalsIgnoreCase("Rejected")){
							feesPay.setApprovalStatus("Rejected");
						}						
						if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
							feesPay.setUploadedDate(GetTimeZone.getDate("Asia/Makassar"));
						}else{
							feesPay.setUploadedDate(date);
						}
						entitymanager.merge(feesPay);
						v = null;
						v = entitymanager.createQuery("from Student where rollNumber=? and school_ID=?");
						v.setParameter(1, paymentDatabean.getPaymentStudID());
						v.setParameter(2, roll.get(0).getSchool().getSchool_ID());
						List<Student> student = (List<Student>) v.getResultList();
						if (student.size() > 0) {
							v = entitymanager.createQuery("from StudentParent where student_ID=?");
							v.setParameter(1, student.get(0).getStudent_ID());
							List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();
							if (studentParent.size() > 0) {
								paymentDatabean.setMailId(studentParent.get(0).getParent().getParentDetail().getEmaiId());
								paymentDatabean.setParentID(roll.get(0).getPersonRoleNumber());
								paymentDatabean.setPhoneno(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			logger.warn("inside exception "+e);
		}
		return "";
	}

	/* Prema OCT 25   getBoorowerBookView1*/
	@Override
	public List<LibraryDataBean> getBoorowerBookView1(String personID) {
		logger.info("-----------Inside getBoorowerBookView1 method()----------------");

		List<String> studentrollno = new ArrayList<String>();
		List<Person> roleStatus = null;
		String rollType = "NotValid";
		int school_ID = 0;
		int studentID = 0;
		List<LibraryDataBean> borrowList = null;
		Query q = null;
		int libID = 0;
		try {
			roleStatus = new ArrayList<Person>();
			borrowList = new ArrayList<LibraryDataBean>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						if (rollType.equalsIgnoreCase("Parent")) {
							studentrollno = getparentstudent(personID);
							if (studentrollno.size() > 0) {
								for (int j = 0; j < studentrollno.size(); j++) {
									studentID = getStudentID(studentrollno.get(j));
									if (studentID > 0) {
										q = entitymanager.createQuery("from Browerbook where school_ID=? and student_ID=? and status='Borrowed'");
										q.setParameter(1, school_ID);
										q.setParameter(2, studentID);
										List<Browerbook> res = (List<Browerbook>) q.getResultList();
										logger.debug("res--" + res.size());
										if (res.size() > 0) {
											q = null;
											q = entitymanager.createQuery("from StudentDetail where student_ID=?");
											q.setParameter(1, studentID);
											List<StudentDetail> studentdetail = (List<StudentDetail>) q.getResultList();
											for (int i = 0; i < res.size(); i++) {
												BigDecimal fineAmount = BigDecimal.ZERO;
												logger.debug("Fine Amount"+ fineAmount);
												fineAmount = getFineAmountByDate(
														res.get(i).getCreatedDate(),
														res.get(i).getLibrary().getDueType(),
														res.get(i).getLibrary().getPenaltyFee());
												LibraryDataBean lib = new LibraryDataBean();
												lib.setBorroweDate(res.get(i).getCreatedDate());
												lib.setsNo(String.valueOf(i + 1));
												lib.setAuthorName(res.get(i).getLibrary().getAuthorName());
												lib.setBookeditions(res.get(i).getLibrary().getBookEdition());
												lib.setBookName(res.get(i).getLibrary().getBookName());
												lib.setBookCategory(res.get(i).getLibrary().getCategory());
												lib.setBookDueType(res.get(i).getLibrary().getDueType());
												lib.setDuedate(res.get(i).getDueDate());
												lib.setBookFee(res.get(i).getLibrary().getPenaltyFee());
												lib.setBookfine(fineAmount.toString());
												lib.setLibStudID(studentrollno.get(j));
												lib.setStudentid(studentdetail.get(0).getFirstName()+ studentdetail.get(0).getLastName());
												borrowList.add(lib);
												logger.debug(res.get(i).getCreatedDate());
											}
										}
									}
								}
							}
						} else if (rollType.equalsIgnoreCase("Student")) {
							studentID = getStudentID(roleStatus.get(0).getPersonRoleNumber());
							q = entitymanager.createQuery("from Browerbook where school_ID=? and student_ID=? and status='Borrowed'");
							q.setParameter(1, school_ID);
							q.setParameter(2, studentID);
							List<Browerbook> res = (List<Browerbook>) q.getResultList();
							if (res.size() > 0) {
								q = null;
								q = entitymanager.createQuery("from StudentDetail where student_ID=?");
								q.setParameter(1, studentID);
								List<StudentDetail> studentdetail = (List<StudentDetail>) q.getResultList();
								for (int i = 0; i < res.size(); i++) {
									BigDecimal fineAmount = BigDecimal.ZERO;
									logger.debug("Fine Amount" + fineAmount);
									fineAmount = getFineAmountByDate(res.get(i).getCreatedDate(), 
											res.get(i).getLibrary().getDueType(), 
											res.get(i).getLibrary().getPenaltyFee());
									LibraryDataBean lib = new LibraryDataBean();
									lib.setBorroweDate(res.get(i).getCreatedDate());
									lib.setsNo(String.valueOf(i + 1));
									lib.setAuthorName(res.get(i).getLibrary().getAuthorName());
									lib.setBookeditions(res.get(i).getLibrary().getBookEdition());
									lib.setBookName(res.get(i).getLibrary().getBookName());
									lib.setBookCategory(res.get(i).getLibrary().getCategory());
									lib.setDuedate(res.get(i).getDueDate());
									lib.setBookFee(res.get(i).getLibrary().getPenaltyFee());
									lib.setBookfine(fineAmount.toString());
									lib.setLibStudID(res.get(i).getStudent().getRollNumber());
									lib.setStudentid(studentdetail.get(0).getFirstName()+ studentdetail.get(0).getLastName());
									borrowList.add(lib);
									logger.debug(res.get(i).getCreatedDate());

								}
							}
						} else {
							q = entitymanager.createQuery("from Browerbook where school_ID=? and  status='Borrowed'");
							q.setParameter(1, school_ID);
							List<Browerbook> res = (List<Browerbook>) q.getResultList();
							logger.debug("res--" + res.size());
							if (res.size() > 0) {
								for (int i = 0; i < res.size(); i++) {
									q = null;
									q = entitymanager.createQuery("from StudentDetail where student_ID=?");
									q.setParameter(1, res.get(i).getStudent().getStudent_ID());
									List<StudentDetail> studentdetail = (List<StudentDetail>) q.getResultList();
									BigDecimal fineAmount = BigDecimal.ZERO;
									logger.debug("Fine Amount" + fineAmount);
									fineAmount = getFineAmountByDate(res.get(i).getCreatedDate(), 
											res.get(i).getLibrary().getDueType(),
											res.get(i).getLibrary().getPenaltyFee());
									LibraryDataBean lib = new LibraryDataBean();
									lib.setBorroweDate(res.get(i).getCreatedDate());
									lib.setsNo(String.valueOf(i + 1));
									lib.setAuthorName(res.get(i).getLibrary().getAuthorName());
									lib.setBookeditions(res.get(i).getLibrary().getBookEdition());
									lib.setBookName(res.get(i).getLibrary().getBookName());
									lib.setBookCategory(res.get(i).getLibrary().getCategory());
									lib.setDuedate(res.get(i).getDueDate());
									lib.setBookFee(res.get(i).getLibrary().getPenaltyFee());
									lib.setBookfine(fineAmount.toString());
									lib.setLibStudID(res.get(i).getStudent().getRollNumber());
									lib.setStudentid(studentdetail.get(0).getFirstName()+ studentdetail.get(0).getLastName());
									borrowList.add(lib);
									logger.debug(res.get(i).getCreatedDate());

								}
							}

						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return borrowList;
	}
	  
	
	public List<String> getparentstudent(String personID) {
		logger.info("-----------Inside getparentstudent method()----------------");

		List<String> studentrollno = new ArrayList<String>();
		List<Person> person = null;
		Query q = null;
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					int schoolid = person.get(0).getSchool().getSchool_ID();
					q = entitymanager.createQuery("from Parent where school_ID=? and rollNumber=? and status=?");
					q.setParameter(1, schoolid);
					q.setParameter(2, person.get(0).getPersonRoleNumber());
					q.setParameter(3, "Active");
					List<Parent> parent = (List<Parent>) q.getResultList();
					if (parent.size() > 0) {
						q = entitymanager.createQuery("from StudentParent where parent_ID=? and status=?");
						q.setParameter(1, parent.get(0).getParent_ID());
						q.setParameter(2, "Active");
						List<StudentParent> studentparent = (List<StudentParent>) q.getResultList();
						if (studentparent.size() > 0) {
							for (int i = 0; i < studentparent.size(); i++) {
								String rollno = studentparent.get(i).getStudent().getRollNumber();
								studentrollno.add(rollno);
							}
						}
						HashSet<String> studentId = new HashSet<String>(studentrollno);
						studentrollno.clear();
						studentrollno.addAll(studentId);
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception", e);
		}
		return studentrollno;
	}
	
	/* Prema OCT 25   getreturnBookView1*/
		
	@Override
	  public List<LibraryDataBean> getreturnBookView1(String personID)
	  {
		logger.info("-----------Inside getreturnBookView1 method()----------------");

	   List<String> studentrollno = new ArrayList<String>();
	   List<Person> roleStatus = null;
	   String rollType = "NotValid";
	   int school_ID = 0;
	   int studentID = 0;
	   List<LibraryDataBean> borrowList = null;
	   Query q = null;
	   int libID = 0;
	   try
	   {
	    roleStatus = new ArrayList<Person>();
	    borrowList = new ArrayList<LibraryDataBean>();
	    if (personID != null) {
	     roleStatus = getRollType(personID);
	     logger.debug("Roll Size" + roleStatus.size());
	     if (roleStatus.size() > 0) {
	      rollType = roleStatus.get(0).getPersonRole();
	      school_ID = roleStatus.get(0).getSchool().getSchool_ID();
	      if (school_ID > 0)
	      {
	       if(rollType.equalsIgnoreCase("Parent"))
	       {

	        studentrollno=getparentstudent(personID);
	        if(studentrollno.size()>0)
	        {
	         for (int j = 0; j < studentrollno.size(); j++) 
	         {
	          studentID = getStudentID(studentrollno.get(j));
	          if (studentID > 0) {
	           q = entitymanager.createQuery(
	             "from Browerbook where school_ID=? and student_ID=? and status='Returned'");
	           q.setParameter(1, school_ID);
	           q.setParameter(2, studentID);
	           List<Browerbook> res = (List<Browerbook>) q.getResultList();
	           logger.debug("res--"+res.size());
	           if (res.size() > 0) {
	        	    q = null;
					q = entitymanager.createQuery("from StudentDetail where student_ID=?");
					q.setParameter(1, studentID);
					List<StudentDetail> studentdetail = (List<StudentDetail>) q.getResultList();
	            for (int i = 0; i < res.size(); i++) {

	             BigDecimal fineAmount = BigDecimal.ZERO;
	             logger.debug("Fine Amount" + fineAmount);
	             fineAmount = getFineAmountByDate(res.get(i).getCreatedDate(),
	               res.get(i).getLibrary().getDueType(),
	               res.get(i).getLibrary().getPenaltyFee());
	             LibraryDataBean lib = new LibraryDataBean();
	             lib.setBorroweDate(res.get(i).getReturnDate());
	             lib.setsNo(String.valueOf(i + 1));
	             lib.setAuthorName(res.get(i).getLibrary().getAuthorName());
	             lib.setBookName(res.get(i).getLibrary().getBookName());
	             lib.setDuedate(res.get(i).getDueDate());
	             lib.setBookFee(res.get(i).getLibrary().getPenaltyFee());
	             lib.setBookfine(fineAmount.toString());
	             lib.setLibStudID(studentrollno.get(j));
	             lib.setStudentid(studentdetail.get(0).getFirstName()+ studentdetail.get(0).getLastName());
	             borrowList.add(lib);
	             logger.debug(res.get(i).getCreatedDate());
	            }
	           }
	          }
	         }
	         }
	        }else if(rollType.equalsIgnoreCase("Student"))
		      {
		          studentID = getStudentID(roleStatus.get(0).getPersonRoleNumber());
		          if (studentID > 0) {
		           q = entitymanager.createQuery(
		             "from Browerbook where school_ID=? and student_ID=? and status='Returned'");
		           q.setParameter(1, school_ID);
		           q.setParameter(2, studentID);
		           List<Browerbook> res = (List<Browerbook>) q.getResultList();
		           logger.debug("res--"+res.size());
		           if (res.size() > 0) {
		        	    q = null;
						q = entitymanager.createQuery("from StudentDetail where student_ID=?");
						q.setParameter(1, studentID);
						List<StudentDetail> studentdetail = (List<StudentDetail>) q.getResultList();
		            for (int i = 0; i < res.size(); i++) {

		             BigDecimal fineAmount = BigDecimal.ZERO;
		             logger.debug("Fine Amount" + fineAmount);
		             fineAmount = getFineAmountByDate(res.get(i).getCreatedDate(),
		               res.get(i).getLibrary().getDueType(),
		               res.get(i).getLibrary().getPenaltyFee());
		             LibraryDataBean lib = new LibraryDataBean();
		             lib.setBorroweDate(res.get(i).getReturnDate());
		             lib.setsNo(String.valueOf(i + 1));
		             lib.setAuthorName(res.get(i).getLibrary().getAuthorName());
		             lib.setBookName(res.get(i).getLibrary().getBookName());
		             lib.setDuedate(res.get(i).getDueDate());		   
		             lib.setBookDueType(res.get(i).getLibrary().getDueType());
		             lib.setBookFee(res.get(i).getLibrary().getPenaltyFee());
		             lib.setBookfine(fineAmount.toString());
		             lib.setLibStudID(roleStatus.get(0).getPersonRoleNumber());
		             lib.setStudentid(studentdetail.get(0).getFirstName()+ studentdetail.get(0).getLastName());
		             borrowList.add(lib);
		             logger.debug(res.get(i).getCreatedDate());
		            }
		           }
		          }
		         }
	        else {
	         q = entitymanager.createQuery(
	           "from Browerbook where school_ID=? and  status='Returned'");
	         q.setParameter(1, school_ID);
	         List<Browerbook> res = (List<Browerbook>) q.getResultList();
	         logger.debug("res--"+res.size());
	         if (res.size() > 0) {
	          for (int i = 0; i < res.size(); i++) {
	        	    q = null;
					q = entitymanager.createQuery("from StudentDetail where student_ID=?");
					q.setParameter(1, res.get(i).getStudent().getStudent_ID());
					List<StudentDetail> studentdetail = (List<StudentDetail>) q.getResultList();
	           BigDecimal fineAmount = BigDecimal.ZERO;
	           logger.debug("Fine Amount" + fineAmount);
	           fineAmount = getFineAmountByDate(res.get(i).getCreatedDate(),
	             res.get(i).getLibrary().getDueType(),
	             res.get(i).getLibrary().getPenaltyFee());
	           LibraryDataBean lib = new LibraryDataBean();
	           lib.setBorroweDate(res.get(i).getReturnDate());
	           lib.setsNo(String.valueOf(i + 1));
	           lib.setAuthorName(res.get(i).getLibrary().getAuthorName());
	           lib.setBookName(res.get(i).getLibrary().getBookName());
	           lib.setDuedate(res.get(i).getDueDate());
	           lib.setBookFee(res.get(i).getLibrary().getPenaltyFee());
	           lib.setBookfine(fineAmount.toString());
	           lib.setLibStudID(res.get(i).getStudent().getRollNumber());
	           lib.setStudentid(studentdetail.get(0).getFirstName()+ studentdetail.get(0).getLastName());
	           borrowList.add(lib);
	           logger.debug(res.get(i).getCreatedDate());
	          }
	         }
	         
	        }
	       }
	      }
	     }
	   

	   }
	   catch(Exception e)
	   {
	    logger.warn(" exception - "+e);
	   }
	   return borrowList;
	  }
	
	public List<LibraryDataBean> getborrowerDetails(String personID,LibraryDataBean libraryDataBean){
		logger.info("-----------Inside getborrowerDetails method()----------------");

		List<Person> roleStatus = new ArrayList<Person>();
		String rollType = "NotValid";
		int school_ID = 0;
		List<LibraryDataBean> borrowList = new ArrayList<LibraryDataBean>();
		Query q = null;
		try {
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
					school_ID = roleStatus.get(0).getSchool().getSchool_ID();
					if (school_ID > 0) {
						q = entitymanager.createQuery("from Browerbook where school_ID=? and status='Borrowed'");
						q.setParameter(1, school_ID);
						List<Browerbook> res = (List<Browerbook>) q.getResultList();
						if (res.size() > 0) {
							for (int i = 0; i < res.size(); i++) {
								q=null;
								q=entitymanager.createQuery("from StudentDetail where student_ID=?");
								q.setParameter(1, res.get(i).getStudent().getStudent_ID());
								List<StudentDetail> studentdetail=(List<StudentDetail>)q.getResultList();
								LibraryDataBean lib = new LibraryDataBean();
								lib.setBorroweDate(res.get(i).getCreatedDate());
								lib.setsNo(String.valueOf(i + 1));
								lib.setAuthorName(res.get(i).getLibrary().getAuthorName());
								lib.setBookName(res.get(i).getLibrary().getBookName());
								lib.setBookFee(res.get(i).getLibrary().getPrice());
								lib.setStudentid(studentdetail.get(0).getFirstName()+studentdetail.get(0).getLastName());
								lib.setLibStudID(res.get(i).getStudent().getRollNumber());
								borrowList.add(lib);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn(" exception - "+e);
		}
		return borrowList;
	}
	
	@Transactional(value="transactionManager")
	public String toReturnBook(String personID, LibraryDataBean libraryDataBean){
		logger.info("-----------Inside toReturnBook method()----------------");

		Query v=null;
		String status="Fail";
		List<Person> role=new ArrayList<Person>();
		try{
			if (personID != null) {
				role = getRollType(personID);
				if (role.size() > 0) {
					int school_ID=role.get(0).getSchool().getSchool_ID();
					v = entitymanager.createQuery("from Student where roll_number=? and school_ID=? and status='Active'");
					v.setParameter(1, libraryDataBean.getLibStudID());
					v.setParameter(2, role.get(0).getSchool().getSchool_ID());
					List<Student> student = (List<Student>) v.getResultList();
					if(student.size()>0){
						v=null;
						v=entitymanager.createQuery("from Library where authorName=? and bookName=? and school_ID=? and status='Active'");
						v.setParameter(1, libraryDataBean.getAuthorName());
						v.setParameter(2, libraryDataBean.getBookName());
						v.setParameter(3, role.get(0).getSchool().getSchool_ID());
						List<Library> library=(List<Library>)v.getResultList();
						if(library.size()>0){
							int libraryID=library.get(0).getLibrary_ID();
							BigDecimal stockin=BigDecimal.valueOf(0),stockout=BigDecimal.valueOf(0);
							stockin=new BigDecimal(library.get(0).getQuantity()).add(BigDecimal.valueOf(1));
							stockout=new BigDecimal(library.get(0).getQuantity()).subtract(BigDecimal.valueOf(1));
							v=null;
							v=entitymanager.createQuery("from Browerbook where school_ID=? and student_ID=? and library_ID=? and status='Borrowed'");	
							v.setParameter(1, role.get(0).getSchool().getSchool_ID());
							v.setParameter(2, student.get(0).getStudent_ID());
							v.setParameter(3, library.get(0).getLibrary_ID());
							List<Browerbook> borrowerbook=(List<Browerbook>)v.getResultList();
							if(borrowerbook.size()>0){
								int borrowbookId=borrowerbook.get(0).getBorrowerID();
								Browerbook bookUpdate=entitymanager.find(Browerbook.class, borrowbookId);
								bookUpdate.setStatus("Returned");
								if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
									bookUpdate.setReturnDate(GetTimeZone.getDate("Asia/Makassar"));
								}else{
									bookUpdate.setReturnDate(date);
								}	
								entitymanager.merge(bookUpdate);
								Library libraryUpdate=entitymanager.find(Library.class, libraryID);
								libraryUpdate.setQuantity(stockin.toString());
								entitymanager.merge(libraryUpdate);
								status="Success";
							}
						}						
					}
				}
			}			
		}
		catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return status;
	}

	/*prema 28/10/2016*/
	public List<TimeTableDataBean> getexamMarksList(String personID,TimeTableDataBean timeTableDataBean) {
		logger.info("-----------Inside getexamMarksList method()----------------");

		List<Person> person = null;List<TimeTableDataBean> domainList=new ArrayList<TimeTableDataBean>();
		Query v = null;
		try {
			if (personID != null) {
				person = getRollType(personID);
				if (person.size() > 0) {
					v = entitymanager.createQuery(
							"from ExamTable where class_name=? and school_ID=? and examTitle=? and status=?");
					v.setParameter(1, timeTableDataBean.getExamClass());
					v.setParameter(2, person.get(0).getSchool().getSchool_ID());
					v.setParameter(3, timeTableDataBean.getExamTitle());
					v.setParameter(4, "Insert");
					List<ExamTable> examtable = (List<ExamTable>) v.getResultList();
					logger.debug("exam table size - " + examtable.size());
					logger.debug("exam table id - " + examtable.get(0).getExam_table_ID());
					if (examtable.size() > 0) {
						for (ExamTable exTable : examtable) {
							v = entitymanager.createQuery("from GradeDetail where exam_table_ID=?");
							v.setParameter(1, exTable.getExam_table_ID());
							List<GradeDetail> gradeDetail = (List<GradeDetail>) v.getResultList();
							logger.debug("exam table id - " +  exTable.getExam_table_ID());
							logger.debug("gradeDetail size - " + gradeDetail.size());
							if (gradeDetail.size() > 0) {
								for (int i = 0; i < gradeDetail.size(); i++) {
									TimeTableDataBean timeTableDataBeans=new TimeTableDataBean();
									timeTableDataBeans.setExamFromMark(gradeDetail.get(i).getFromMark());
									if(gradeDetail.get(i).getToMark().equalsIgnoreCase("99")){
										timeTableDataBeans.setExamToMark("100");
									}else{
										timeTableDataBeans.setExamToMark(gradeDetail.get(i).getToMark());
									}
									timeTableDataBeans.setExamGrade(gradeDetail.get(i).getGrade());
									timeTableDataBeans.setGradedetailId(String.valueOf(gradeDetail.get(i).getGrade_details_ID()));
									timeTableDataBeans.setStatus(gradeDetail.get(i).getYear());
									timeTableDataBeans.setExamResult(gradeDetail.get(i).getExamResult());
									domainList.add(timeTableDataBeans);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return domainList;
	}
	
	@Transactional(value="transactionManager")
	public String getexammarksupdate(String personID,TimeTableDataBean timeTableDataBean) {
		logger.info("-----------Inside getexammarksupdate method()----------------");

		String status = "Fail";List<Person> roleStatus = null;
		String rollType = "NotValid";Calendar now = Calendar.getInstance(); int year = now.get(Calendar.YEAR);
		int school_ID = 0; int grade_id = 0;
		try {
			roleStatus = new ArrayList<Person>();
			if (personID != null) {
				roleStatus = getRollType(personID);
				logger.debug("Roll Size" + roleStatus.size());
				if (roleStatus.size() > 0) {
					rollType = roleStatus.get(0).getPersonRole();
						if (rollType.equalsIgnoreCase("Admin")) {
							grade_id= Integer.parseInt(timeTableDataBean.getGradedetailId());
							if (grade_id > 0) {
								GradeDetail grade = entitymanager.find(GradeDetail.class, grade_id);
								grade.setYear(String.valueOf(year));
								grade.setFromMark(timeTableDataBean.getExamFromMark());
								if(timeTableDataBean.getExamToMark().equalsIgnoreCase("100")){
									grade.setToMark("99");
								}else{
									grade.setToMark(timeTableDataBean.getExamToMark());
								}
								grade.setGrade(timeTableDataBean.getExamGrade());
								grade.setExamResult(timeTableDataBean.getExamResult());
								entitymanager.merge(grade);
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
	
	public String getexammarkgrade(ReportCardDataBean reportCardDataBean) {
		logger.info("-----------Inside getexammarkgrade method()----------------");
		List<GradeDetail> gradetail=null;
		Query q=null;
		try {
			StringTokenizer st=new StringTokenizer(reportCardDataBean.getStudMarkClass());
			String classname=st.nextToken("/");
			q=entitymanager.createQuery("from ExamTable where examTitle=? and (class_name=? or class_name=?) and status='Insert' and school_ID=?");
			q.setParameter(1, reportCardDataBean.getExamMarkTitle());
			q.setParameter(2, classname);
			q.setParameter(3, reportCardDataBean.getStudMarkClass());
			q.setParameter(4, reportCardDataBean.getSchoolID());
			List<ExamTable> examtable=(List<ExamTable>)q.getResultList();
			if(examtable.size()>0){
				String mark="";
				if(reportCardDataBean.getMark().equalsIgnoreCase("100")){
					mark="99";
				}else{
					mark=reportCardDataBean.getMark();
				}
				q=null;
				if(reportCardDataBean.getSchoolID()==Integer.parseInt(paths.getString("SMRV.SCHOOLID"))){
					q=entitymanager.createQuery("from GradeDetail where schoolID=? and ? between fromMark and toMark");
					q.setParameter(1, String.valueOf(reportCardDataBean.getSchoolID()));
					q.setParameter(2,mark);
					gradetail=(List<GradeDetail>)q.getResultList();
				}
				else{
					q=entitymanager.createQuery("from GradeDetail where exam_table_ID=? and ? between fromMark and toMark");
					q.setParameter(1,examtable.get(0).getExam_table_ID());
					q.setParameter(2,mark);
					gradetail=(List<GradeDetail>)q.getResultList();
				}
				if(gradetail.size()>0){
					reportCardDataBean.setGrade(gradetail.get(0).getGrade());
					reportCardDataBean.setMark(reportCardDataBean.getMark());
					reportCardDataBean.setResultStatus(gradetail.get(0).getExamResult());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	@Transactional(value="transactionManager")
	public String classTimeTableDelete(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		logger.info("-----------Inside classTimeTableDelete method()----------------");

		String status="Fail";Query q=null;List<Person> roll = null;Calendar now = Calendar.getInstance();
		int year=0;
		try{
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					StringTokenizer stringtoken = new StringTokenizer(classTimeTableDataBean.getClassname());
					String classname = stringtoken.nextToken("/");
					String section = classTimeTableDataBean.getClassname();
					String sectionName = section.substring(section.lastIndexOf("/") + 1);
					int schoolid = roll.get(0).getSchool().getSchool_ID();
					q=entitymanager.createQuery("from Class where className=? and classSection=? and school_ID=? and status=?");
					q.setParameter(1, classname);
					q.setParameter(2, sectionName);
					q.setParameter(3, schoolid);
					q.setParameter(4, "Active");
					List<Class> classs = (List<Class>) q.getResultList();
					if(classs.size()>0){
						year = now.get(Calendar.YEAR);
						q=null;
						q=entitymanager.createQuery("from ClassTable where class_ID=? and day=? and school_ID=? and year=? and status='Active'");
						q.setParameter(1, classs.get(0).getClass_ID());
						q.setParameter(2, classTimeTableDataBean.getDay());
						q.setParameter(3, schoolid);
						q.setParameter(4, String.valueOf(year));
						List<ClassTable> classtable=(List<ClassTable>)q.getResultList();
						if(classtable.size()>0){
							ClassTable classtables=entitymanager.find(ClassTable.class, classtable.get(0).getClass_table_ID());
							classtables.setStatus("DeActive");
							entitymanager.merge(classtables);
							status="Success";
						}
					}
				}
			}
		}catch(Exception e){
			//e.printStackTrace();
		}
		return status;
	}

	public String checkExtraPeriod(String personID,
			ClassTimeTableDataBean classTimeTableDataBean){
		logger.info("-----------Inside checkExtraPeriod method()----------------");
		Query v = null;
		List<Person> roll = null;
		int year = 0;
		String status = "fail";
		try {
			Calendar now = Calendar.getInstance();
			year = now.get(Calendar.YEAR);
			if (personID != null) {
				roll = getRollType(personID);
				if (roll.size() > 0) {
					int schoolid = roll.get(0).getSchool().getSchool_ID();					
					StringTokenizer stringTokenizer = new StringTokenizer(classTimeTableDataBean.getClassname());
					String className = stringTokenizer.nextToken("/");
					String sectionName = classTimeTableDataBean.getClassname();
					String section = sectionName.substring(sectionName.lastIndexOf("/") + 1);					
					v = entitymanager.createQuery(
							"from Class where className=? and classSection=? and school_ID=? and status=?");
					v.setParameter(1, className);
					v.setParameter(2, section);
					v.setParameter(3, schoolid);
					v.setParameter(4, "Active");
					List<Class> classs = (List<Class>) v.getResultList();
					if (classs.size() > 0) {
						v = null;
						v = entitymanager.createQuery("from Subject where subjectCode=? and status=? and school_ID=?");
						v.setParameter(1, classTimeTableDataBean.getSubjectCode());
						v.setParameter(2, "Active");
						v.setParameter(3, schoolid);
						List<Subject> subject = (List<Subject>) v.getResultList();
						v = null;
						v = entitymanager
								.createQuery("from ClassSubject where class_ID=? and subject_ID=? and status=?");
						v.setParameter(1, classs.get(0).getClass_ID());
						v.setParameter(2, subject.get(0).getSubject_ID());
						v.setParameter(3, "Active");
						List<ClassSubject> classSubject = (List<ClassSubject>) v.getResultList();
						v = entitymanager
								.createQuery("from TeacherSubject where school_ID=? and subject_ID=? and status=?");
						v.setParameter(1, schoolid);
						v.setParameter(2, classSubject.get(0).getSubject().getSubject_ID());
						v.setParameter(3, "Active");
						List<TeacherSubject> teacher = (List<TeacherSubject>) v.getResultList();
						v = entitymanager.createQuery("from ClassTable where class_ID=? and day=? and year=? and status='Active'");
						v.setParameter(1, classs.get(0).getClass_ID());
						v.setParameter(2, classTimeTableDataBean.getDay());
						v.setParameter(3, String.valueOf(year));
						List<ClassTable> classtable = (List<ClassTable>) v.getResultList();
						if (classtable.size() > 0) {
							String st = "";
							String et = "";
							String ss = "" + classTimeTableDataBean.getStartTime().getHours();
							String ss1 = "" + classTimeTableDataBean.getStartTime().getMinutes();
							if (ss.length() == 1 && ss1.length() == 1) {
								st = "0" + ss + ":" + "0" + ss1;
							} else if (ss.length() == 1 && ss1.length() == 2) {
								st = "0" + ss + ":" + ss1;
							} else if (ss.length() == 2 && ss1.length() == 1) {
								st = ss + ":" + "0" + ss1;
							} else if (ss.length() == 2 && ss1.length() == 2) {
								st = ss + ":" + ss1;
							}
							String ss2 = "" + classTimeTableDataBean.getEndTime().getHours();
							String ss3 = "" + classTimeTableDataBean.getEndTime().getMinutes();
							if (ss2.length() == 1 && ss3.length() == 1) {
								et = "0" + ss2 + ":" + "0" + ss3;
							} else if (ss2.length() == 1 && ss3.length() == 2) {
								et = "0" + ss2 + ":" + ss3;
							} else if (ss2.length() == 2 && ss3.length() == 1) {
								et = ss2 + ":" + "0" + ss3;
							} else if (ss2.length() == 2 && ss3.length() == 2) {
								et = ss2 + ":" + ss3;
							}
							int coun=0;
							classTimeTableDataBean.setTimeStart(st);
							classTimeTableDataBean.setTimeEnd(et);
							v = null;
							v = entitymanager.createQuery(
									"from ClassTimeTable where day=? and class_table_ID=? and ? between startTime and endTime");
							v.setParameter(1, classTimeTableDataBean.getDay());
							v.setParameter(2, classtable.get(0).getClass_table_ID());
							v.setParameter(3, st);
							logger.debug("st "+st);
							logger.debug("id - " + teacher.get(0).getTeacher().getTeacher_ID());
							List<ClassTimeTable> classTimeTable = (List<ClassTimeTable>) v.getResultList();
							if (classTimeTable.size() > 0) {
								if(classTimeTable.get(0).getEndTime().equals(st)) status = "success";
								else {
									try{
										if(Integer.parseInt(classTimeTable.get(0).getEndTime())<Integer.parseInt(st)) status = "fail";
										else status = "success";
									}catch(Exception e){
										status = "fail";
									}									
								}
							}else status = "success";
						} else{
							status = "no";
						}
						logger.debug("status "+status);
					}
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("Exception", e);
			logger.warn("Inside Exception",e);
		} finally {

		}
		return status;
	}

	@Transactional(value="transactionManager")
	public String insertExtraPeriod(String personID,
			ClassTimeTableDataBean classTimeTableDataBean){
		logger.info("-----------Inside insertExtraPeriod method()----------------");
	   // logger.debug("insert period class time table ");
	    Query v = null;
	    List<Person> roll = null;
	    int year = 0;
	    int teaid=0; List<String> phonenos = new ArrayList<String>();
	    List<String> maillist = new ArrayList<String>();
	    String status = "fail";
	    try {
	     Calendar now = Calendar.getInstance();
	     year = now.get(Calendar.YEAR);
	     if (personID != null) {
	      roll = getRollType(personID);
	      if (roll.size() > 0) {
	       StringTokenizer st = new StringTokenizer(classTimeTableDataBean.getClassname());
	       String className = st.nextToken("/");
	       String sectionName = classTimeTableDataBean.getClassname();
	       String section = sectionName.substring(sectionName.lastIndexOf("/") + 1);
	       int schoolid = roll.get(0).getSchool().getSchool_ID();
	       classTimeTableDataBean.setSchoolName(roll.get(0).getSchool().getName());
	       v = entitymanager.createQuery(
	         "from Class where className=? and classSection=? and school_ID=? and status=?");
	       v.setParameter(1, className);
	       v.setParameter(2, section);
	       v.setParameter(3, schoolid);
	       v.setParameter(4, "Active");
	       List<Class> classs = (List<Class>) v.getResultList();
	       if (classs.size() > 0) {
	        v = null;
	        v = entitymanager.createQuery("from Subject where sujectName=? and subjectCode=? and status=? and school_ID=?");
	        v.setParameter(1, classTimeTableDataBean.getSubject());
	        v.setParameter(2, classTimeTableDataBean.getSubjectCode());
	        v.setParameter(3, "Active");
	        v.setParameter(4, schoolid);
	        List<Subject> subject = (List<Subject>) v.getResultList();
	        v = null;
	        v = entitymanager
	          .createQuery("from ClassSubject where class_ID=? and subject_ID=? and status=?");
	        v.setParameter(1, classs.get(0).getClass_ID());
	        v.setParameter(2, subject.get(0).getSubject_ID());
	        v.setParameter(3, "Active");
	        List<ClassSubject> classSubject = (List<ClassSubject>) v.getResultList();
	        v = null;
	        v = entitymanager.createQuery("from Teacher where rollNumber=? and status='Active'");
	        v.setParameter(1, classTimeTableDataBean.getTeaID());
	        List<Teacher> teacherlist=(List<Teacher>)v.getResultList();
	        if(teacherlist.size()>0){
	          teaid = teacherlist.get(0).getTeacher_ID();
	        }
	        v = entitymanager
	             .createQuery("from TeacherSubject where school_ID=? and subject_ID=? and teacher_ID=? and status=?");
	           v.setParameter(1, schoolid);
	           v.setParameter(2, classSubject.get(0).getSubject().getSubject_ID());
	           v.setParameter(3, teaid);
	           v.setParameter(4, "Active");
	           List<TeacherSubject> teacher = (List<TeacherSubject>) v.getResultList();
	           logger.debug("teachersubject list size"+teacher.size());
	        maillist.add(teacher.get(0).getTeacher().getTeacherDetails().get(0).getEmailId());
	        phonenos.add(teacher.get(0).getTeacher().getTeacherDetails().get(0).getPhoneNumber());
	        v = entitymanager.createQuery("from ClassTable where class_ID=? and day=? and month=? and year=? and status='Active'");
	        v.setParameter(1, classs.get(0).getClass_ID());
	        v.setParameter(2, classTimeTableDataBean.getDay());
	        v.setParameter(3, classTimeTableDataBean.getMonth());
	        v.setParameter(4, String.valueOf(year));
	        List<ClassTable> classtable = (List<ClassTable>) v.getResultList();
	        if (classtable.size() > 0) {
	         v = null;
	         v = entitymanager.createQuery("from ClassTimeTable where day=? and class_table_ID=?");
	         v.setParameter(1, classTimeTableDataBean.getDay());
	         v.setParameter(2, classtable.get(0).getClass_table_ID());
	         List<ClassTimeTable> classTimeTable = (List<ClassTimeTable>) v.getResultList();
	         ClassTimeTable extraclass = new ClassTimeTable();
	         extraclass.setTeacher(
	           entitymanager.find(Teacher.class, teacher.get(0).getTeacher().getTeacher_ID()));
	         extraclass.setDay(classTimeTableDataBean.getDay());
	         extraclass.setSubject(classTimeTableDataBean.getSubject());
	         extraclass.setSubjectCode(classTimeTableDataBean.getSubjectCode());
	         extraclass.setTeacher(entitymanager.find(Teacher.class,teaid));
	         logger.debug("teacher id========>"+extraclass.getTeacher());
	         logger.debug("start time "+classTimeTableDataBean.getTimeStart());
	         extraclass.setStartTime(classTimeTableDataBean.getTimeStart());
	         logger.debug("end time "+classTimeTableDataBean.getTimeEnd());
	         extraclass.setEndTime(classTimeTableDataBean.getTimeEnd());
	         extraclass.setClassTable(
	           entitymanager.find(ClassTable.class, classtable.get(0).getClass_table_ID()));
	         extraclass.setSchool(entitymanager.find(School.class, schoolid));
	         extraclass.setPeriod("" + (classTimeTable.size() + 1));
	         entitymanager.persist(extraclass);
	         v = null;
	         v = entitymanager.createQuery("from StudentClass where class_ID=? and status=?");
	         v.setParameter(1, classs.get(0).getClass_ID());
	         v.setParameter(2, "Active");
	         List<StudentClass> studentclass = (List<StudentClass>) v.getResultList();
	         if (studentclass.size() > 0) {
	          for (int j = 0; j < studentclass.size(); j++) {	           
	           v = entitymanager.createQuery("from StudentParent where student_ID=?");
	           v.setParameter(1, studentclass.get(j).getStudent().getStudent_ID());
	           List<StudentParent> studentParent = (List<StudentParent>) v.getResultList();
	           if (studentParent.size() > 0) {
	            maillist.add(studentParent.get(0).getParent().getParentDetail().getEmaiId());
	            phonenos.add(studentParent.get(0).getParent().getParentDetail().getPhoneNumber());
	           }
	          }
	          classTimeTableDataBean.setMails(maillist);
	          classTimeTableDataBean.setPhonenos(phonenos);
	          status = "success";
	         }
	        }
	       }
	      }
	     }
	    } catch (Exception e) {
	     logger.warn("Exception" +e.getMessage());
	     //logger.warn("Inside Exception",e);
	    // e.printStackTrace();
	    } finally {

	    }
	    return status;
	}

	@Override
	public List<AttendanceDataBean> attendanceReport(String personID,
			AttendanceDataBean attendanceDataBean) {
		logger.info("-----------Inside attendanceReport method()----------------");

		List<AttendanceDataBean> attendancelist=null;
		List<Person> roll = null;
		List<Attendanceclass> attendance=null;
		if(personID!=null){
		try{
			attendancelist=new ArrayList<AttendanceDataBean>();
			attendance=new ArrayList<Attendanceclass>();
			roll = getRollType(personID);
			if (roll.size() > 0) {
				int schoolid = roll.get(0).getSchool().getSchool_ID();
				if(!attendanceDataBean.getStudentName().equalsIgnoreCase("")){
				attendance=getAttendancedetails(attendanceDataBean,schoolid);
				}else{
					attendance=getDetailsclass(attendanceDataBean,schoolid);
				}
				if(attendance.size()>0){
					for (int i = 0; i < attendance.size(); i++) {
						attendanceDataBean=new AttendanceDataBean();
						attendanceDataBean.setStudentID(attendance.get(i).getStudent_ID());
						attendanceDataBean.setStudentName(attendance.get(i).getStudentName());
						attendanceDataBean.setDate(attendance.get(i).getTime());
						attendanceDataBean.setPercentage(attendance.get(i).getPercentage());
						attendanceDataBean.setStatus(attendance.get(i).getStatus());
						attendanceDataBean.setPeriod(attendance.get(i).getPeriod());
						attendancelist.add(attendanceDataBean);
					}
				}
			}
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception -->"+e.getMessage());
		}
		}
		return attendancelist;
	}

	private List<Attendanceclass> getDetailsclass(
			AttendanceDataBean attendanceDataBean, int schoolid) {
		logger.info("-----------Inside getDetailsclass method()----------------");

		Query q=null;String currentMonth="";
		List<Attendanceclass> attendancelist=null;
		List<Attendance> list=null;
		int attendanceid=0;
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		Format formatter = new SimpleDateFormat("MM"); 
	    String month = formatter.format((date));
		try{
			if(month.startsWith("0")){
				currentMonth=month.substring(1);
			}else{
				currentMonth=month;
			}
				q=entitymanager.createQuery("from Attendance where school_ID=? and class_=? and section=? ");
				q.setParameter(1, schoolid);
				q.setParameter(2, attendanceDataBean.getClassname().split("/")[0]);
				q.setParameter(3, attendanceDataBean.getClassname().split("/")[1]);
				list=(ArrayList<Attendance>)q.getResultList();
				if(list.size()>0){
					if (attendanceDataBean.getCategory().equals("Monthly")) {
					for(int i=0;i<list.size();i++){
						attendanceid=list.get(i).getAttendance_ID();
						q=entitymanager.createQuery("from Attendanceclass where attendance_ID=? and month=?");
						q.setParameter(1, attendanceid);
						q.setParameter(2, currentMonth);
						attendancelist=(List<Attendanceclass>)q.getResultList();
					}
					}else if(attendanceDataBean.getCategory().equals("Half Yearly")){
						if(Integer.parseInt(currentMonth)>6){
							q=entitymanager.createQuery("from Attendanceclass where where attendance_ID=? and month>6");
							q.setParameter(1, attendanceid);
							attendancelist=(List<Attendanceclass>)q.getResultList();
						}else if(Integer.parseInt(currentMonth)<6){
							q=entitymanager.createQuery("from Attendanceclass where where attendance_ID=? and month<6");
							q.setParameter(1, attendanceid);
							attendancelist=(List<Attendanceclass>)q.getResultList();
						}
					}else if(attendanceDataBean.getCategory().equals("Yearly")){
						q=entitymanager.createQuery("from Attendanceclass where where attendance_ID=? and year=?");
						q.setParameter(1, attendanceid);
						q.setParameter(2, String.valueOf(year));
						attendancelist=(List<Attendanceclass>)q.getResultList();
					}
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		return attendancelist;
	}

	private List<Attendanceclass> getAttendancedetails(AttendanceDataBean attendanceDataBean, int schoolid) {
		logger.info("-----------Inside getAttendancedetails method()----------------");
		Query q=null;String currentMonth="";
		List<Attendanceclass> attendancelist=null;
	    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		Format formatter = new SimpleDateFormat("MM"); 
	    String month = formatter.format((date)); 
		try{
			if(month.startsWith("0")){
				currentMonth=month.substring(1);
			}else{
				currentMonth=month;
			}
			
			System.out.println("studid --- "+attendanceDataBean.getStudentName().split("/")[1]);
			System.out.println("student name"+attendanceDataBean.getStudentName().split("/")[0]);
			System.out.println("month----"+currentMonth);
			if (attendanceDataBean.getCategory().equals("Monthly")) {
					q=entitymanager.createQuery("from Attendanceclass where student_ID=? and studentName=? and month=?");
					q.setParameter(1, attendanceDataBean.getStudentName().split("/")[1]);
					q.setParameter(2, attendanceDataBean.getStudentName().split("/")[0]);
					q.setParameter(3, currentMonth);
					attendancelist=(List<Attendanceclass>)q.getResultList();
				}else if(attendanceDataBean.getCategory().equals("Half Yearly")){
					if(Integer.parseInt(currentMonth)>6){
					q=entitymanager.createQuery("from Attendanceclass where student_ID=? and studentName=? and month>6");
					q.setParameter(1, attendanceDataBean.getStudentName().split("/")[1]);
					q.setParameter(2, attendanceDataBean.getStudentName().split("/")[0]);
					attendancelist=(List<Attendanceclass>)q.getResultList();
					}else if(Integer.parseInt(currentMonth)<6){
					q=entitymanager.createQuery("from Attendanceclass where student_ID=? and studentName=? and month<6");
					q.setParameter(1, attendanceDataBean.getStudentName().split("/")[1]);
					q.setParameter(2, attendanceDataBean.getStudentName().split("/")[0]);
					attendancelist=(List<Attendanceclass>)q.getResultList();
					}
				}else if(attendanceDataBean.getCategory().equals("Yearly")){
					q=entitymanager.createQuery("from Attendanceclass where student_ID=? and studentName=? and year=?");
					q.setParameter(1, attendanceDataBean.getStudentName().split("/")[1]);
					q.setParameter(2, attendanceDataBean.getStudentName().split("/")[0]);
					q.setParameter(3, String.valueOf(year));
					attendancelist=(List<Attendanceclass>)q.getResultList();
				}
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception -->"+e.getMessage());
		}
		return attendancelist;
	}

	@Override
	public List<ReportCardDataBean> getmarks(String personID,
			ReportCardDataBean reportCardDataBean) {
		logger.info("-----------Inside getmarks method()----------------");
		Query q=null;
		List<ReportCardDataBean> reportlist=null;
		int schoolid=0;
		if(personID!=null){
		try{
			reportlist=new ArrayList<ReportCardDataBean>();
			q=entitymanager.createQuery("from School where name=?");
			q.setParameter(1, reportCardDataBean.getSchoolName());
			List<School> schoollist=(List<School>)q.getResultList();
			if(schoollist.size()>0){
				schoolid = schoollist.get(0).getSchool_ID();
				if(!reportCardDataBean.getStudMarkClass().equalsIgnoreCase("All")){
					q = entitymanager.createQuery("from Class where className=? and classSection=? and school_ID=?");
					q.setParameter(1, reportCardDataBean.getStudMarkClass().split("/")[0]);
					q.setParameter(2, reportCardDataBean.getStudMarkClass().split("/")[1]);
					q.setParameter(3, schoolid);
					List<Class> classlist=(List<Class>)q.getResultList();
					int classid=classlist.get(0).getClass_ID();
				if(!reportCardDataBean.getMarkSubTitle().equalsIgnoreCase("")){
					q = entitymanager.createQuery("from Subject where sujectName=? and subjectCode=? and school_ID=?");	
					q.setParameter(1, reportCardDataBean.getMarkSubTitle().split("/")[0]);
					q.setParameter(2, reportCardDataBean.getMarkSubTitle().split("/")[1]);
					q.setParameter(3, schoolid);
					List<Subject> subjectlist=(List<Subject>)q.getResultList();
					if(subjectlist.size()>0){
						int subjectid=subjectlist.get(0).getSubject_ID();
						q=entitymanager.createQuery("from Reportcard where school_ID=? and class_ID=? and examTitle=? and subject_ID=?");
						q.setParameter(1, schoolid);
						q.setParameter(2, classid);
						q.setParameter(3, reportCardDataBean.getExamMarkTitle());
						q.setParameter(4, subjectid);
						List<Reportcard> reporcardlist=(List<Reportcard>)q.getResultList();
						if(reporcardlist.size()>0){
							reportlist=getreportdetails(reporcardlist,reportCardDataBean);
						 }
					}
				}else{
				q=entitymanager.createQuery("from Reportcard where school_ID=? and class_ID=? and examTitle=?");
				q.setParameter(1, schoolid);
				q.setParameter(2, classid);
				q.setParameter(3, reportCardDataBean.getExamMarkTitle());
				List<Reportcard> reporcardlist=(List<Reportcard>)q.getResultList();
				if(reporcardlist.size()>0){
					reportlist=getreportdetails(reporcardlist,reportCardDataBean);
				 }
				}
				}else{
					q=entitymanager.createQuery("from Reportcard where school_ID=? and  examTitle=?");
					q.setParameter(1, schoolid);
					q.setParameter(2, reportCardDataBean.getExamMarkTitle());
					List<Reportcard> reporcardlist=(List<Reportcard>)q.getResultList();
					if(reporcardlist.size()>0){
						reportlist=getreportdetails(reporcardlist,reportCardDataBean);
					}
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		return reportlist;
	}

	private List<ReportCardDataBean> getreportdetails(
			List<Reportcard> reporcardlist, ReportCardDataBean reportCardDataBean) {
		logger.info("-----------Inside getreportdetails method()----------------");
		Query q=null;
		List<ReportCardDataBean> reportlist=null;
		try{
			reportlist=new ArrayList<ReportCardDataBean>();
			for(int i=0;i<reporcardlist.size();i++){
				int studentid=reporcardlist.get(i).getStudent().getStudent_ID();
				q = entitymanager.createQuery("from StudentDetail where student_ID=?");
				q.setParameter(1, studentid);
				List<StudentDetail> studentlist = (List<StudentDetail>) q.getResultList();
				Format formatter = new SimpleDateFormat("yyyy"); 
			    String date = formatter.format(reporcardlist.get(i).getCreatedDate());
			    if(date.equalsIgnoreCase(reportCardDataBean.getGrade())){
			    ReportCardDataBean	report=new ReportCardDataBean();
			    report.setExamMarkTitle(reporcardlist.get(i).getExamTitle());
			    report.setGrade(reporcardlist.get(i).getGrade());
			    report.setMark(reporcardlist.get(i).getMark());
			    report.setResultStatus(reporcardlist.get(i).getResultStatus());
			    report.setName(reporcardlist.get(i).getSubject().getSujectName()+"/"+reporcardlist.get(i).getSubject().getSubjectCode());
			    String studentname=reporcardlist.get(i).getStudent().getRollNumber();
			    report.setSearchname(studentname+"/"+studentlist.get(0).getFirstName()+" "+studentlist.get(0).getLastName());
			    reportlist.add(report);
			    }
			}
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception -->"+e.getMessage());
		}
		return reportlist;
	}

	@Override
	public List<StudentPerformanceDataBean> getStudentperformance(String personID,
			StudentPerformanceDataBean studentPerformanceDataBean) {
		logger.info("-----------Inside getStudentperformance method()----------------");
		Query q=null;
		List<StudentPerformanceDataBean> studentperformacelist=null;
		int schoolid=0;
		if(personID!=null){
		try{
			studentperformacelist=new ArrayList<StudentPerformanceDataBean>();
			q=entitymanager.createQuery("from School where name=?");
			q.setParameter(1, studentPerformanceDataBean.getSchoolName());
			List<School> schoollist=(List<School>)q.getResultList();
			if(schoollist.size()>0){
				schoolid = schoollist.get(0).getSchool_ID();
			if(!studentPerformanceDataBean.getClassname().equalsIgnoreCase("All")){
				if(!studentPerformanceDataBean.getStuName().equalsIgnoreCase("")){
				q=entitymanager.createQuery("from Student where rollNumber=?");
				q.setParameter(1, studentPerformanceDataBean.getStuName().split("/")[1]);
				List<Student> studentlist=(List<Student>)q.getResultList();
				int studentid=studentlist.get(0).getStudent_ID();
				if(!studentPerformanceDataBean.getStuApp().equalsIgnoreCase("")){
					if(studentPerformanceDataBean.getStuApp().equalsIgnoreCase("Monthly")){
						q=entitymanager.createQuery("from StudentPerformance where school_ID=?  and student_ID=? and status='Active'");
						q.setParameter(1, schoolid);
						q.setParameter(2, studentid);
						List<StudentPerformance> performancelist=(ArrayList<StudentPerformance>)q.getResultList();
						for(int i=0;i<performancelist.size();i++){
						Format formatter = new SimpleDateFormat("MM"); 
						String month = formatter.format(performancelist.get(i).getCreateDate());
						String currentmonth=formatter.format(date);
							if(currentmonth.equals(month)){
								StudentPerformanceDataBean perform=new StudentPerformanceDataBean();
								perform.setStuAtt(performancelist.get(i).getAttitudeStatus());
								perform.setStuApp(performancelist.get(i).getAppearanceStatus());
								perform.setFromdate(performancelist.get(i).getCreateDate());
								perform.setStuName(performancelist.get(i).getStudent().getRollNumber());
								studentperformacelist.add(perform);
							}
						}
					}else if(studentPerformanceDataBean.getStuApp().equalsIgnoreCase("Half Yearly")){
						q=entitymanager.createQuery("from StudentPerformance where school_ID=?  and student_ID=? and status='Active'");
						q.setParameter(1, schoolid);
						q.setParameter(2, studentid);
						List<StudentPerformance> performancelist=(ArrayList<StudentPerformance>)q.getResultList();
						for(int i=0;i<performancelist.size();i++){
						Format formatter = new SimpleDateFormat("MM"); 
						String month = formatter.format((performancelist.get(i).getCreateDate()));
						if(Integer.parseInt(month)>6){
							StudentPerformanceDataBean perform=new StudentPerformanceDataBean();
							perform.setStuAtt(performancelist.get(i).getAttitudeStatus());
							perform.setStuApp(performancelist.get(i).getAppearanceStatus());
							perform.setFromdate(performancelist.get(i).getCreateDate());
							perform.setStuName(performancelist.get(i).getStudent().getRollNumber());
							studentperformacelist.add(perform);
						}else if(Integer.parseInt(month)<6){
							StudentPerformanceDataBean perform=new StudentPerformanceDataBean();
							perform.setStuAtt(performancelist.get(i).getAttitudeStatus());
							perform.setStuApp(performancelist.get(i).getAppearanceStatus());
							perform.setFromdate(performancelist.get(i).getCreateDate());
							perform.setStuName(performancelist.get(i).getStudent().getRollNumber());
							studentperformacelist.add(perform);
						}
						}
					}else if(studentPerformanceDataBean.getStuApp().equalsIgnoreCase("Yearly")){
						q=entitymanager.createQuery("from StudentPerformance where school_ID=?  and student_ID=? and status='Active'");
						q.setParameter(1, schoolid);
						q.setParameter(2, studentid);
						List<StudentPerformance> performancelist=(ArrayList<StudentPerformance>)q.getResultList();
						for(int i=0;i<performancelist.size();i++){
						Format formatter = new SimpleDateFormat("yyyy"); 
						String year = formatter.format(performancelist.get(i).getCreateDate());
						String currentyear=formatter.format(date);
							if(currentyear.equals(year)){
								StudentPerformanceDataBean perform=new StudentPerformanceDataBean();
								perform.setStuAtt(performancelist.get(i).getAttitudeStatus());
								perform.setStuApp(performancelist.get(i).getAppearanceStatus());
								perform.setFromdate(performancelist.get(i).getCreateDate());
								perform.setStuName(performancelist.get(i).getStudent().getRollNumber());
								studentperformacelist.add(perform);
							}
						}
					}
			}else{
				if(studentPerformanceDataBean.getFromdate()!=null && studentPerformanceDataBean.getTodate()!=null){
					q=entitymanager.createQuery("from StudentPerformance where school_ID=? and student_ID=? and createDate between ? and ? and status='Active'");
					q.setParameter(1, schoolid);
					q.setParameter(2, studentid);
					q.setParameter(3, studentPerformanceDataBean.getFromdate());
					q.setParameter(4, studentPerformanceDataBean.getTodate());
					List<StudentPerformance> list=(ArrayList<StudentPerformance>)q.getResultList();
					if(list.size()>0){
						for(int i=0;i<list.size();i++){
							StudentPerformanceDataBean perform=new StudentPerformanceDataBean();
							perform.setStuAtt(list.get(i).getAttitudeStatus());
							perform.setStuApp(list.get(i).getAppearanceStatus());
							perform.setFromdate(list.get(i).getCreateDate());
							perform.setStuName(list.get(i).getStudent().getRollNumber());
							studentperformacelist.add(perform);
						}
					}
				}
			}
			}
			}else if(studentPerformanceDataBean.getClassname().equalsIgnoreCase("All")){
				if(!studentPerformanceDataBean.getStuApp().equalsIgnoreCase("")){
					if(studentPerformanceDataBean.getStuApp().equalsIgnoreCase("Monthly")){
						q=entitymanager.createQuery("from StudentPerformance where school_ID=?   and status='Active'");
						q.setParameter(1, schoolid);
						List<StudentPerformance> performancelist=(ArrayList<StudentPerformance>)q.getResultList();
						for(int i=0;i<performancelist.size();i++){
						Format formatter = new SimpleDateFormat("MM"); 
						String month = formatter.format(performancelist.get(i).getCreateDate());
						String currentmonth=formatter.format(date);
							if(currentmonth.equals(month)){
								StudentPerformanceDataBean perform=new StudentPerformanceDataBean();
								perform.setStuAtt(performancelist.get(i).getAttitudeStatus());
								perform.setStuApp(performancelist.get(i).getAppearanceStatus());
								perform.setFromdate(performancelist.get(i).getCreateDate());
								perform.setStuName(performancelist.get(i).getStudent().getRollNumber());
								studentperformacelist.add(perform);
							}
						}
					}else if(studentPerformanceDataBean.getStuApp().equalsIgnoreCase("Half Yearly")){
						q=entitymanager.createQuery("from StudentPerformance where school_ID=?   and status='Active'");
						q.setParameter(1, schoolid);
						List<StudentPerformance> performancelist=(ArrayList<StudentPerformance>)q.getResultList();
						for(int i=0;i<performancelist.size();i++){
							String name=performancelist.get(i).getStudent().getRollNumber();
						Format formatter = new SimpleDateFormat("MM"); 
						String month = formatter.format((performancelist.get(i).getCreateDate()));
						if(Integer.parseInt(month)>6){
							StudentPerformanceDataBean perform=new StudentPerformanceDataBean();
							perform.setStuAtt(performancelist.get(i).getAttitudeStatus());
							perform.setStuApp(performancelist.get(i).getAppearanceStatus());
							perform.setFromdate(performancelist.get(i).getCreateDate());
							perform.setStuName(performancelist.get(i).getStudent().getRollNumber());
							studentperformacelist.add(perform);
						}else if(Integer.parseInt(month)<6){
							StudentPerformanceDataBean perform=new StudentPerformanceDataBean();
							perform.setStuAtt(performancelist.get(i).getAttitudeStatus());
							perform.setStuApp(performancelist.get(i).getAppearanceStatus());
							perform.setFromdate(performancelist.get(i).getCreateDate());
							perform.setStuName(performancelist.get(i).getStudent().getRollNumber());
							studentperformacelist.add(perform);
						}
						}
					}else if(studentPerformanceDataBean.getStuApp().equalsIgnoreCase("Yearly")){
						q=entitymanager.createQuery("from StudentPerformance where school_ID=?  and student_ID=? and status='Active'");
						q.setParameter(1, schoolid);
						List<StudentPerformance> performancelist=(ArrayList<StudentPerformance>)q.getResultList();
						for(int i=0;i<performancelist.size();i++){
							String name=performancelist.get(i).getStudent().getRollNumber();
						Format formatter = new SimpleDateFormat("yyyy"); 
						String year = formatter.format(performancelist.get(i).getCreateDate());
						String currentyear=formatter.format(date);
							if(currentyear.equals(year)){
								StudentPerformanceDataBean perform=new StudentPerformanceDataBean();
								perform.setStuAtt(performancelist.get(i).getAttitudeStatus());
								perform.setStuApp(performancelist.get(i).getAppearanceStatus());
								perform.setFromdate(performancelist.get(i).getCreateDate());
								perform.setStuName(performancelist.get(i).getStudent().getRollNumber());
								studentperformacelist.add(perform);
							}
						}
					}
			}else{
				if(studentPerformanceDataBean.getFromdate()!=null && studentPerformanceDataBean.getTodate()!=null){
					q=entitymanager.createQuery("from StudentPerformance where school_ID=?  and createDate between ? and ? and status='Active'");
					q.setParameter(1, schoolid);
					q.setParameter(2, studentPerformanceDataBean.getFromdate());
					q.setParameter(3, studentPerformanceDataBean.getTodate());
					List<StudentPerformance> list=(ArrayList<StudentPerformance>)q.getResultList();
					if(list.size()>0){
						for(int i=0;i<list.size();i++){
							String name=list.get(i).getStudent().getRollNumber();
							StudentPerformanceDataBean perform=new StudentPerformanceDataBean();
							perform.setStuAtt(list.get(i).getAttitudeStatus());
							perform.setStuApp(list.get(i).getAppearanceStatus());
							perform.setFromdate(list.get(i).getCreateDate());
							perform.setStuName(list.get(i).getStudent().getRollNumber());
							studentperformacelist.add(perform);
						}
					}
				}
			}
			}
			}
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception -->"+e.getMessage());
		}
		}
		return studentperformacelist;
	}
	
	@Override
	public List<String> getcountryList(String valuechange) {
		logger.info("-----------Inside getcountryList method()----------------");
		Query q=null;
		List<String> listTeacher=new ArrayList<String>();
		try{
			q=entitymanager.createQuery("select stateName from State where country=?");
			q.setParameter(1, valuechange);			
			listTeacher=q.getResultList();
			logger.debug("=========================size=============>"+listTeacher); 
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception -->"+e.getMessage());
		}
		return listTeacher;
	}

	public String attendanceTableView(String personID, AttendanceDataBean attendanceDataBean) {
		logger.info("-----------Inside attendanceTableView method()----------------");
		List<AttendanceDataBean> attendancedata = new ArrayList<AttendanceDataBean>();
		List<Person> person = null;
		Query v = null;
		try {
			logger.info("attendanceTableView method 1");
			if (personID != null) {
				logger.info("attendanceTableView method 2");
				person = getRollType(personID);
				if (person.size() > 0) {
					logger.info("attendanceTableView method 3");
					int schoolid = person.get(0).getSchool().getSchool_ID();
					StringTokenizer st = new StringTokenizer(attendanceDataBean.getClassname());
					String className = st.nextToken("/");
					String section = attendanceDataBean.getClassname();
					String sectionName = section.substring(section.lastIndexOf("/") + 1);
					logger.info("attendanceTableView method 4");
						v = entitymanager.createQuery("from Attendance where school_ID=? and date=? and class_=? and section=?");
						v.setParameter(1, schoolid);
						v.setParameter(2, attendanceDataBean.getDate());
						v.setParameter(3, className);
						v.setParameter(4, sectionName);
						List<Attendance> attendance = (List<Attendance>) v.getResultList();
						if (attendance.size() > 0) {
							logger.info("attendanceTableView method 5");
							v = null;
							v = entitymanager.createQuery("from Attendanceclass where attendance_ID=? and period=?");
							v.setParameter(1, attendance.get(0).getAttendance_ID());
							v.setParameter(2, attendanceDataBean.getPeriod().substring(7, 8));
							List<Attendanceclass> attendanceclass = (List<Attendanceclass>) v.getResultList();
							if (attendanceclass.size() > 0) {
								logger.info("attendanceTableView method 6");
								int i1 = 1;
								for (int i = 0; i < attendanceclass.size(); i++) {
									AttendanceDataBean list = new AttendanceDataBean();
									list.setStudentName(attendanceclass.get(i).getStudentName());
									list.setStudentID(attendanceclass.get(i).getStudent_ID());
									list.setStatus(attendanceclass.get(i).getStatus());
									list.setPercentage(attendanceclass.get(i).getPercentage());
									list.setDate(attendance.get(0).getDate());
									list.setTime1(attendanceclass.get(i).getTime().getHours() + ":"
											+ attendanceclass.get(i).getTime().getMinutes());
									list.setSno(i1);
									attendancedata.add(list);
									attendanceDataBean.setStudentList(attendancedata);
									i1++;
								}
								logger.info("attendanceTableView method 7");
								attendanceDataBean.setFlag(false);
							}
						} else {
							logger.info("attendanceTableView method 8");
							attendanceDataBean.setStudentList(attendancedata);
						}
						logger.debug("attendance class size -- " + attendanceDataBean.getStudentList().size());
				}
				}
		} catch (Exception e) {
			//logger.warn("Exception", e);
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.info("attendanceTableView method 9");
		return null;
	}

	@Override
	public List<AttendanceDataBean> attendanceSearch(String personID,AttendanceDataBean attendanceDataBean) {
		logger.info("-----------Inside attendanceSearch method()----------------");
		//logger.debug("----chk value -date-adao-->"+attendanceDataBean.getDate());
		List<AttendanceDataBean> attendancedata = new ArrayList<AttendanceDataBean>();
		List<Person> person = null;
		attendanceDataBean.setStudentList(new ArrayList<AttendanceDataBean>());
		Query v = null;
		try {
			
			if (personID != null) {
				person = getRollType(personID);
				
				if (person.size() > 0) {
					int schoolid = person.get(0).getSchool().getSchool_ID();
					v = entitymanager.createQuery("from Attendance where school_ID=? and date=?");
					v.setParameter(1, schoolid);
					v.setParameter(2, attendanceDataBean.getDate());
					List<Attendance> attendance = (List<Attendance>) v.getResultList();
					
					if (attendance.size() > 0) {
						for (int j = 0; j < attendance.size(); j++) {
							v = null;
							v = entitymanager.createQuery("from Attendanceclass where attendance_ID=? and student_ID=?");
							v.setParameter(1, attendance.get(j).getAttendance_ID());
							v.setParameter(2, attendanceDataBean.getStudentID());
							List<Attendanceclass> attendanceclass = (List<Attendanceclass>) v.getResultList();
							if (attendanceclass.size() > 0) {
								List<String> period = new ArrayList<String>();
								for (int i = 0; i < attendanceclass.size(); i++) {
									AttendanceDataBean list = new AttendanceDataBean();
									list.setPeriod("Period " + attendanceclass.get(i).getPeriod());
									list.setStatus(attendanceclass.get(i).getStatus());
									list.setSno(attendanceclass.get(i).getAtt_class_ID());
									list.setTime1(attendanceclass.get(i).getTime().getHours() + ":" + attendanceclass.get(i).getTime().getMinutes());
									list.setStudentName(attendanceclass.get(i).getStudentName());
									
									list.setPercentage(attendanceclass.get(0).getPercentage());
									attendancedata.add(list);
									attendanceDataBean.setStudentList(attendancedata);
								}
							} 
						}
					}
				}
			}
			logger.debug("-----student list size----->"+attendanceDataBean.getStudentList().size());
		} catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
			//logger.warn("Exception", e);
		//	e.printStackTrace();
		}
		return attendanceDataBean.getStudentList();
	}
	@Transactional(value="transactionManager")
	@Override
	public String updateAttendanceGlobal(AttendanceDataBean attendanceDataBean,String personID) {
		logger.info("-----------Inside updateAttendanceGlobal method()----------------");
		String status="Fail";
		try {
			logger.debug(attendanceDataBean.getStatus());
			logger.debug(attendanceDataBean.getSno());
			Attendanceclass attupdate=entitymanager.find(Attendanceclass.class, attendanceDataBean.getSno());
			attupdate.setStatus(attendanceDataBean.getStatus());
			entitymanager.merge(attupdate);
			status="Success";
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
//08-09-2017,08-10-2017	
	private List<ExamTable> getExamList(int school_ID, String personID, String classname) {
		logger.info("-----------Inside getExamList method()----------------");
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
			logger.error("Exception -->"+e.getMessage());
			//logger.warn(e);
		}
		return timeList;
	}

	public List<TimeTable> getExamTableList(String personID, int examID) {
		logger.info("-----------Inside getExamTableList method()----------------");
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
			logger.error("Exception -->"+e.getMessage());
			//logger.warn(e);
		}
		return result;
	}
	
	@Override
	public List<ReportCardDataBean> getglobalsearchMarkList(String schoolID,String personID, ReportCardDataBean reportCardDataBean) {
		logger.info("-----------Inside getglobalsearchMarkList method()----------------");
		List<ReportCardDataBean> markList = null;
		List<TimeTable> result1 = null;
		Date date = new Date();
		Query q=null;
		int sub_ID= 0;
		int student_ID=0;
		int class_ID=0;
		List<Class> classList=null;
		List<Reportcard> markCard =null;
		try {
			markList = new ArrayList<ReportCardDataBean>();
			result1 = new ArrayList<TimeTable>();
			classList = new ArrayList<Class>();
			student_ID = getStudentID(reportCardDataBean.getRollNo());
			classList = getClassID(personID, Integer.parseInt(schoolID), reportCardDataBean.getStudMarkClass());
			if (classList.size() > 0 && student_ID > 0) {
				class_ID=classList.get(0).getClass_ID();
				result1=getExamTableList(personID,reportCardDataBean.getExamID());
				logger.debug("---test result1 size---"+result1.size());
				if (result1.size()>0) {
					for (int i = 0; i < result1.size(); i++) {
						ReportCardDataBean dataBean = new ReportCardDataBean();
						markCard = new ArrayList<Reportcard>();
						
						dataBean.setMarkSubTitle(result1.get(i).getSubject()+"/"+result1.get(i).getSubjectCode());
						dataBean.setExamMarkTitle(result1.get(i).getExamTable().getExamTitle());
						dataBean.setExamDate(result1.get(i).getExamDate());
						sub_ID = getSubjectID(dataBean.getMarkSubTitle(), personID, Integer.parseInt(schoolID));
						markCard = getmarkListGlobal(sub_ID, student_ID, class_ID, Integer.parseInt(schoolID),reportCardDataBean.getExamID(), reportCardDataBean);
						if (markCard.size()>0) {
							dataBean.setMark(markCard.get(0).getMark());
							dataBean.setGrade(markCard.get(0).getGrade());
							dataBean.setResultStatus(markCard.get(0).getResultStatus());
						}
						else{
							dataBean.setMark("");
							dataBean.setGrade("");
							dataBean.setResultStatus("");
						}
						markList.add(dataBean);
					}
				}
			}
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}
		return markList;
	}
	
	@Override
	public String performanceCheck(StudentPerformanceDataBean studentPerformanceDataBean) {
		logger.info("-----------Inside performanceCheck method()----------------");
		String status="";Query q=null;
		try{
			q = entitymanager.createQuery("from Student where roll_number=? and school_ID=? and status='Active'");
			q.setParameter(1,studentPerformanceDataBean.getPerformStudID() );
			q.setParameter(2, studentPerformanceDataBean.getSchoolID());
			List<Student> studlist = (List<Student>) q.getResultList();
			if(studlist.size()>0){
				logger.info("id -----------"+studentPerformanceDataBean.getPerformStudID());
				logger.info("date -----------"+studentPerformanceDataBean.getTeaDob());
				q = entitymanager.createQuery("from StudentPerformance where student_ID=? and createDate=?  and status='Active'");
				q.setParameter(1, studlist.get(0).getStudent_ID());
				q.setParameter(2, studentPerformanceDataBean.getTeaDob());
				List<StudentPerformance> plist = (List<StudentPerformance>) q.getResultList();
				logger.info("plist---"+plist.size());
				if(plist.size()>0){
					status="Exist";
				}
			}
			
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return status;
	}
	//08-10-2017 chng update
	@Override
	public String getexammarkgradeGlobal(ReportCardDataBean reportCardDataBean) {
		logger.info("-----------Inside getexammarkgradeGlobal method()----------------");
			Query q=null;
			String mark="";
			try {
				if(reportCardDataBean.getExamID()!=0){
					if(reportCardDataBean.getMark().equalsIgnoreCase("100")){
						mark="99";
					}else{
						mark=reportCardDataBean.getMark();
					}
					q=entitymanager.createQuery("from GradeDetail where exam_table_ID=? and ? between fromMark and toMark");
					q.setParameter(1,reportCardDataBean.getExamID());
					q.setParameter(2,mark);
					List<GradeDetail> gradetail=(List<GradeDetail>)q.getResultList();
					if(gradetail.size()>0){
						reportCardDataBean.setGrade(gradetail.get(0).getGrade());
						reportCardDataBean.setMark(reportCardDataBean.getMark());
						reportCardDataBean.setResultStatus(gradetail.get(0).getExamResult());
					}
				}
			}catch(Exception e){
				//e.printStackTrace();
				logger.error("Exception -->"+e.getMessage());
			}
			return "";
		}
		
		@Override
		@Transactional(value = "transactionManager")
		public String addReportCardGlobal(String personID, ReportCardDataBean reportCardDataBean) {
			logger.info("-----------Inside addReportCardGlobal method()----------------");
			String status = "Fail";
			List<Person> roleStatus = null;
			int school_ID = 0;
			String rollType = "NotValid";
			int sub_ID = 0;
			int tea_ID = 0;
			int class_ID = 0;
			int student_ID = 0;
			List<Class> classList = null;
			List<Reportcard> markCard = null;
			int reportcardID = 0;
			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
			try {
				logger.info("addReportCardGlobal method 1");
				//System.out.println("---------Inside addReportCard Dao method calling---------");
				roleStatus = new ArrayList<Person>();
				classList = new ArrayList<Class>();
				markCard = new ArrayList<Reportcard>();
				if (personID != null) {
					logger.info("addReportCardGlobal method 2");
					roleStatus = getRollType(personID);
					//System.out.println("Roll Size" + roleStatus.size());
					if (roleStatus.size() > 0) {
						logger.info("addReportCardGlobal method 3");
						rollType = roleStatus.get(0).getPersonRole();
						school_ID = roleStatus.get(0).getSchool().getSchool_ID();
						reportCardDataBean.setSchoolName(roleStatus.get(0).getSchool().getName());
						reportCardDataBean.setSchoolID(school_ID);
						//System.out.println("---Type---" + rollType);
						logger.info("addReportCardGlobal method 4");
						if (school_ID > 0) {
							logger.info("addReportCardGlobal method 5");
							if (rollType.equalsIgnoreCase(text.getString("stumosys.teacher"))) {
								//System.out.println("inside teacher if codition");
								logger.info("addReportCardGlobal method 6");
							}
							else if (rollType.equalsIgnoreCase(text.getString("stumosys.Admin"))) {
								logger.info("addReportCardGlobal method 7");
								//System.out.println("inside Admin if codition");
								classList = getClassID(personID, school_ID, reportCardDataBean.getStudMarkClass());
								student_ID = getStudentID(reportCardDataBean.getRollNo());
								sub_ID = getSubjectID(reportCardDataBean.getMarkSubTitle(), personID, school_ID);
								if (classList.size() > 0 && student_ID > 0 && sub_ID > 0) {
									class_ID = classList.get(0).getClass_ID();
									markCard = getmarkListGlobal(sub_ID, student_ID, class_ID, school_ID,
											reportCardDataBean.getExamID(), reportCardDataBean);
									if (markCard.size() > 0) {
										reportcardID = markCard.get(0).getReportcard_ID();
										Reportcard report = entitymanager.find(Reportcard.class, reportcardID);
										if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
											report.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
											report.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
										}else{
											report.setCreatedDate(date);
											report.setCreatedTime(timestamp);
										}	
										report.setGrade(reportCardDataBean.getGrade());
										report.setMark(reportCardDataBean.getMark());
										report.setExamTitle(reportCardDataBean.getExamMarkTitle());
										report.setResultStatus(reportCardDataBean.getResultStatus());
										entitymanager.merge(report);
										status = "Edit";
									} else {
										Reportcard reportcard = new Reportcard();
										reportcard.setClazz(entitymanager.find(Class.class, class_ID));
										if(school_ID==2 || school_ID==3 || school_ID==4 || school_ID==5|| school_ID==6){
											reportcard.setCreatedDate(GetTimeZone.getDate("Asia/Makassar"));
											reportcard.setCreatedTime(new Timestamp(GetTimeZone.getDate("Asia/Makassar").getTime()));
										}else{
											reportcard.setCreatedDate(date);
											reportcard.setCreatedTime(timestamp);
										}	
										reportcard.setGrade(reportCardDataBean.getGrade());
										reportcard.setMark(reportCardDataBean.getMark());
										reportcard.setExamTitle(reportCardDataBean.getExamMarkTitle());
										reportcard.setStudent(entitymanager.find(Student.class, student_ID));
										reportcard.setResultStatus(reportCardDataBean.getResultStatus());
										reportcard.setSchool(entitymanager.find(School.class, school_ID));
										reportcard.setStatus("Active");
										reportcard.setSubject(entitymanager.find(Subject.class, sub_ID));
										reportcard.setExamTable(entitymanager.find(ExamTable.class, reportCardDataBean.getExamID()));
										entitymanager.persist(reportcard);
										status = "Success";
									}
									Query q=null;
							        q=entitymanager.createQuery("from StudentParent where student_ID=? and status='Active'");
							        q.setParameter(1, student_ID);
							        List<StudentParent> studpar=(List<StudentParent>)q.getResultList();
							        if(studpar.size()>0){						         
							          reportCardDataBean.setMailid(studpar.get(0).getParent().getParentDetail().getEmaiId());
							          logger.debug("mail -- "+reportCardDataBean.getMailid());
							          reportCardDataBean.setPhoneno(studpar.get(0).getParent().getParentDetail().getPhoneNumber());
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
		private List<Reportcard> getmarkListGlobal(int sub_ID, int studentID, int class_ID, int school_ID,
				int examID, ReportCardDataBean reportCardDataBean) {
			logger.info("-----------Inside getmarkListGlobal method()----------------");
			Query q = null;
			List<Reportcard> markList = null;
			try {
				logger.info("getmarkListGlobal method 1");
				markList = new ArrayList<Reportcard>();
				if (sub_ID > 0 && studentID > 0 && class_ID > 0 && school_ID > 0) {
					logger.info("getmarkListGlobal method 2");
					logger.debug("Inside getmarkList");
					q = entitymanager.createQuery(
							"from Reportcard where exam_table_ID=? and student_ID=? and school_ID=? and class_ID=? and subject_ID=? and status='Active'");
					q.setParameter(1, examID);
					q.setParameter(2, studentID);
					q.setParameter(3, school_ID);
					q.setParameter(4, class_ID);
					q.setParameter(5, sub_ID);
					List<Reportcard> result = (List<Reportcard>) q.getResultList();
				logger.debug("----------->>>" + result.size());
				logger.info("getmarkListGlobal method 3");

					if (result.size() > 0) {
						logger.info("getmarkListGlobal method 4");
						markList.addAll(result);
					}
				}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
				// logger
			}
			logger.info("getmarkListGlobal method 5");
			return markList;
		}
		@Transactional(value ="transactionManager")
		@Override
		public String examDelete(TimeTableDataBean timeTableDataBean) {
			String status="";
			try{
				logger.info("examDelete method 1");
				TimeTable timeTable=entitymanager.find(TimeTable.class,timeTableDataBean.getExamtableId());
				timeTable.setStatus("Deactive");
				entitymanager.merge(timeTable);
				logger.info("examDelete method 2");
				status="Success";
			}catch(Exception e){
				logger.warn("Exception -->"+e.getMessage());
			}
			logger.info("examDelete method 3");
			return status;
		}

		@Transactional(value ="transactionManager")
		@Override
		public String gradeDelete(TimeTableDataBean timeTable) {
			String status="";
			try{
				logger.info("gradeDelete method 1");
				GradeDetail gradeDetail=entitymanager.find(GradeDetail.class, Integer.parseInt(timeTable.getGradedetailId()));
				gradeDetail.setStatus("Deactive");
				entitymanager.merge(gradeDetail);
				logger.info("gradeDelete method 2");
				status="Success";
			}catch(Exception e){
				logger.warn("Exception -->"+e.getMessage());
			}
			logger.info("gradeDelete method 3");
			return status;
		}
}
