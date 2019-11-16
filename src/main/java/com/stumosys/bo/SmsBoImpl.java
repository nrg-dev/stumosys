package com.stumosys.bo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stumosys.dao.AdministrationDao;
import com.stumosys.dao.SmsDao;
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
import com.stumosys.domain.ReportDataBean;
import com.stumosys.domain.StaffDataBean;
import com.stumosys.domain.StudentDataBean;
import com.stumosys.domain.StudentPerformanceDataBean;
import com.stumosys.domain.TeacherDataBean;
import com.stumosys.domain.TimeTableDataBean;
import com.stumosys.exception.StumosysException;
import com.stumosys.shared.BookSalesRecord;
import com.stumosys.shared.ExamTable;
import com.stumosys.shared.Library;
import com.stumosys.shared.Noticeboard;
import com.stumosys.shared.Person;
import com.stumosys.shared.School;
import com.stumosys.shared.StudentDetail;
import com.stumosys.shared.StudentPerformance;
import com.stumosys.shared.SupProduct;
import com.stumosys.shared.TimeTable;
import com.stumosys.shared.UserProduct;
import com.stumosys.util.MailSendJDBC;
import org.apache.log4j.Logger;


@Service("bo")
public class SmsBoImpl implements SmsBo {
	
	final Logger logger = Logger.getLogger(SmsBoImpl.class);

	@Autowired
	SmsDao dao;

	@Autowired
	AdministrationDao adao;

	@Override
	public String loginBo(LoginAccess loginaccess) throws StumosysException
	{

		return dao.loginDao(loginaccess);
	}

	@Override
	public List<UserProduct> LoadMenu(LoginAccess loginaccess) {

		return dao.LoadMenu(loginaccess);
	}

	@Override
	public List<SupProduct> loadSubMenu(int product_ID, String string) {

		return dao.loadSubMenu(product_ID, string);
	}

	@Override
	public List<String> getStateList(String personID) {
		return dao.getStateList(personID);
	}

	@Override
	public String checkValidateZip(String personID, TeacherDataBean teacherDataBean) {

		return dao.checkValidateZip(personID, teacherDataBean);
	}

	@Override
	public List<String> getClassSection(String personID) {

		return dao.getClassSection(personID);
	}

	@Override
	public List<String> getSubjectList(String personID) {

		return dao.getSubjectList(personID);
	}

	@Override
	public String insertTeacher(String personID, TeacherDataBean teacherDataBean) {

		return dao.insertTeacher(personID, teacherDataBean);
	}

	@Override
	public List<TeacherDataBean> getImagePath(String personID, String teaID) {

		return dao.getImagePath(personID, teaID);
	}

	@Override
	public String checkTeacherID(TeacherDataBean teacherDataBean, String personID) {

		return dao.checkTeacherID(teacherDataBean, personID);
	}

	@Override
	public List<String> getStateLists(String personID) {
		return dao.getStateList(personID);
	}

	@Override
	public String getZipList(StudentDataBean studentdatabean) {

		return dao.getZipList(studentdatabean);
	}

	@Override
	public String getZipList1(StudentDataBean studentdatabean) {

		return dao.getZipList1(studentdatabean);
	}

	@Override
	public List<TeacherDataBean> getAllTeacherInfo(String personID, TeacherDataBean teacherDataBean) {

		return dao.getAllTeacherInfo(personID,teacherDataBean);
	}

	@Override
	public List<TeacherDataBean> getTeacherInfo(String personID, String teaID) {

		return dao.getTeacherInfo(personID, teaID);
	}

	@Override
	public String updateTeacher(String personID, TeacherDataBean teacherDataBean) {

		return dao.updateTeacher(personID, teacherDataBean);
	}

	@Override
	public String deleteTeacher(String personID, TeacherDataBean teacherDataBean) {

		return dao.deleteTeacher(personID, teacherDataBean);
	}

	@Override
	public String insertNoticeBoard(String personID, NoticeBoardDataBean noticeBoardDataBean) {

		return adao.insertNoticeBoard(personID, noticeBoardDataBean);
	}

	@Override
	public List<String> getClassList(String personID, StudentDataBean studentdatabean) {

		return dao.getClassList(personID, studentdatabean);
	}

	@Override
	public String insertStudent(String personID, StudentDataBean studentDataBean) {

		return dao.insertStudent(personID, studentDataBean);
	}

	@Override
	public List<StudentDataBean> getImagePath1(String personID, String stuRollNo) {

		return dao.getImagePath1(personID, stuRollNo);
	}

	@Override
	public List<StudentDataBean> getStudentInfo(String personID, StudentDataBean studentDataBean) {

		return dao.getStudentInfo(personID, studentDataBean);
	}

	@Override
	public List<StudentDetail> getStudentView(StudentDataBean studentDataBean,String personID) {

		return dao.getStudentView(studentDataBean,personID);
	}

	@Override
	public String getStudentEdit(StudentDataBean studentDataBean,String personID) {
		return dao.getStudentEdit(studentDataBean,personID);
	}

	@Override
	public String getStudentDelete(String personID, StudentDataBean studentDataBean) {

		return dao.getStudentDelete(personID, studentDataBean);
	}

	@Override
	public List<StudentDetail> getStudentDetails(StudentDataBean studentDataBean,String personID) {

		return dao.getStudentDetails(studentDataBean,personID);
	}

	@Override
	public String checkStudentRollno(StudentDataBean studentDataBean, String stuRollNo) {

		return dao.checkStudentRollno(studentDataBean, stuRollNo);
	}

	@Override
	public int getStudentId(String stuRollNo) {

		return dao.getStudentId(stuRollNo);
	}

	@Override
	public String getStudentClass(String stuRollNo,String personID) {

		return dao.getStudentClass(stuRollNo,personID);
	}

	@Override
	public List<StudentDataBean> getAllStudentInfo(String personID) {

		return dao.getAllStudentInfo(personID);
	}

	@Override
	public List<NoticeBoardDataBean> getNoticeBoardView(String personID) {

		return adao.getNoticeBoardView(personID);
	}

	@Override
	public List<Noticeboard> getNoticeBoardViewBYSubject(String personID, NoticeBoardDataBean noticeBoardDataBean,
			String role_user) {

		return adao.getNoticeBoardViewBYSubject(personID, noticeBoardDataBean, role_user);
	}

	@Override
	public String updateNotice(String personID, NoticeBoardDataBean noticeBoardDataBean) {

		return adao.updateNotice(personID, noticeBoardDataBean);
	}

	@Override
	public String deleteNotice(String personID, NoticeBoardDataBean noticeBoardDataBean) {
		return adao.deleteNotice(personID, noticeBoardDataBean);
	}

	@Override
	public List<String> getStudentRollNumber(String personID, String name) {
		return dao.getStudentRollNumber(personID, name);
	}

	@Override
	public String insertParents(String personID, ParentsDataBean parentsDataBean) {

		return dao.insertParents(personID, parentsDataBean);
	}

	@Override
	public List<ParentsDataBean> getParentDetilsList(String personID, ParentsDataBean parentsDataBean) {

		return dao.getParentDetilsList(personID, parentsDataBean);
	}

	@Override
	public List<ParentsDataBean> getParentInfo(String personID, ParentsDataBean parentsDataBean) {

		return dao.getParentInfo(personID, parentsDataBean);
	}

	@Override
	public String updateParent(String personID, ParentsDataBean parentsDataBean) {

		return dao.updateParent(personID, parentsDataBean);
	}

	@Override
	public String deleteParent(String personID, ParentsDataBean parentsDataBean) {

		return dao.deleteParent(personID, parentsDataBean);
	}

	@Override
	public List<String> getClassNameList(String personID) {

		return adao.getClassNameList(personID);
	}

	@Override
	public List<String> getSubjectNameList(ReportCardDataBean reportCardDataBean, String personID) {

		return adao.getSubjectNameList(reportCardDataBean, personID);
	}

	@Override
	public List<ReportCardDataBean> getStudent(String personID, ReportCardDataBean reportCardDataBean) {

		return adao.getStudent(personID, reportCardDataBean);
	}

	@Override
	public String addReportCard(String personID, ReportCardDataBean reportCardDataBean) {

		return adao.addReportCard(personID, reportCardDataBean);
	}

	@Override
	public List<String> getStudentIDList(ReportCardDataBean reportCardDataBean, String personID) {

		return adao.getStudentIDList(reportCardDataBean, personID);
	}

	@Override
	public List<String> getIdList(StudentPerformanceDataBean studentPerformanceDataBean) {

		return adao.getIdList(studentPerformanceDataBean);
	}

	@Override
	public String getPerClass(StudentPerformanceDataBean studentPerformanceDataBean,String personID) {

		return adao.getPerClass(studentPerformanceDataBean,personID);
	}

	@Override
	public String insertStudentPerform(String PersonID, StudentPerformanceDataBean studentPerformanceDataBean) {

		return adao.insertStudentPerform(PersonID, studentPerformanceDataBean);
	}

	@Override
	public List<String> getClassList1(String PersonID, StudentPerformanceDataBean studentPerformanceDataBean) {

		return adao.getClassList1(PersonID, studentPerformanceDataBean);
	}

	@Override
	public List<StudentPerformanceDataBean> getStudentPerInfo(String personID,
			StudentPerformanceDataBean studentPerformanceDataBean) {

		return adao.getStudentPerInfo(personID, studentPerformanceDataBean);
	}

	@Override
	public List<StudentPerformance> getPerformView(StudentPerformanceDataBean studentPerformanceDataBean) {

		return adao.getPerformView(studentPerformanceDataBean);
	}

	@Override
	public String getperformDelete(String personID, StudentPerformanceDataBean studentPerformanceDataBean) {

		return adao.getperformDelete(personID, studentPerformanceDataBean);
	}

	@Override
	public List<StudentPerformance> getPerformDetails(StudentPerformanceDataBean studentPerformanceDataBean) {

		return adao.getPerformDetails(studentPerformanceDataBean);
	}

	@Override
	public String getPerformEdit(String personID, StudentPerformanceDataBean studentPerformanceDataBean) {

		return adao.getPerformEdit(personID, studentPerformanceDataBean);
	}

	@Override
	public List<String> getPerClassList1(String personID,StudentPerformanceDataBean studentPerformanceDataBean) {

		return adao.getPerClassList1(personID,studentPerformanceDataBean);

	}

	@Override
	public List<ReportCardDataBean> getRepoartCard(String personID, ReportCardDataBean reportCardDataBean) {

		return adao.getRepoartCard(personID, reportCardDataBean);
	}

	public List<String> getClassList(String personID) {
		return adao.getClassList(personID);
	}

	public String subjectValues(TimeTableDataBean timeTableDataBean, String personID) {
		return adao.subjectValues(timeTableDataBean, personID);
	}

	public String changeSubjectCode(String personID, TimeTableDataBean timeTableDataBean) {
		return adao.changeSubjectCode(personID, timeTableDataBean);
	}

	public String timeTableInsert(String personID, TimeTableDataBean timeTableDataBean, List<TimeTableDataBean> timesBean) {
		List<Person> per = null;
		List<ExamTable> exam = null;
		int schoolId = 0;
		//String status = "";
		String status = "Fail";
		if (personID != null) {
			per = adao.getperson(personID);
			if (per.size() > 0) {
				schoolId = per.get(0).getSchool().getSchool_ID();
				timeTableDataBean.setSchoolLogo(per.get(0).getSchool().getLogopath());
				timeTableDataBean.setSchoolID(schoolId);
				if (schoolId > 0) {
					exam = adao.examTableInsert(schoolId, timeTableDataBean,timesBean);
					if (exam.size() > 0) {
						int examId = exam.get(exam.size() - 1).getExam_table_ID();
						timeTableDataBean.setExamtableId(examId);
						for (int i = 0; i < timeTableDataBean.getClassList().size(); i++) {
							status = adao.timeTableInsert(examId, timeTableDataBean, i,schoolId);
						}
					}
				}
			}
		}
		return status;
	}

	public String examCheck(TimeTableDataBean timeTableDataBean, String personID) {
		return adao.examCheck(timeTableDataBean, personID);
	}

	public String timeTableView(TimeTableDataBean timeTableDataBean, String personID) {
		return adao.timeTableView(timeTableDataBean, personID);
	}

	public List<String> getexamList(String personID, TimeTableDataBean timeTableDataBean) {
		return adao.getexamList(personID, timeTableDataBean);
	}

	public String examRecords(TimeTableDataBean timeTableDataBean, String personID) {
		return adao.examRecords(timeTableDataBean, personID);
	}

	public String getexamRecordsList(String personID, TimeTableDataBean timeTableDataBean) {
		return adao.getexamRecordsList(personID, timeTableDataBean);
	}

	public String getexamEditList(String personID, TimeTableDataBean timeTableDataBean) {
		return adao.getexamEditList(personID, timeTableDataBean);
	}

	public String getexamupdate(String personID, TimeTableDataBean timeTableDataBean) {
		return adao.getexamupdate(personID, timeTableDataBean);
	}

	@Override
	public String insertLibrary(String personID, LibraryDataBean libraryDataBean) {

		return adao.insertLibrary(personID, libraryDataBean);
	}

	@Override
	public List<LibraryDataBean> getBookListView(String personID, String name) {
		return adao.getBookListView(personID, name);
	}

	@Override
	public List<LibraryDataBean> getBookDetailsView(String personID, LibraryDataBean libraryDataBean) {

		return adao.getBookDetailsView(personID, libraryDataBean);
	}

	@Override
	public String updateLibrary(String personID, LibraryDataBean libraryDataBean) {

		return adao.updateLibrary(personID, libraryDataBean);
	}

	@Override
	public String deleteBook(String personID, LibraryDataBean libraryDataBean) {

		return adao.deleteBook(personID, libraryDataBean);
	}

	@Override
	public String insertBookBorrowed(String personID, LibraryDataBean libraryDataBean) {

		return adao.insertBookBorrowed(personID, libraryDataBean);
	}

	public List<String> getClassListAtt(String personID) {
		return adao.getClassListAtt(personID);
	}

	public String studentList(String personID, AttendanceDataBean attendanceDataBean) {
		return adao.studentList(personID, attendanceDataBean);
	}

	public String attendanceStatus(String personID, AttendanceDataBean attendanceDataBean) {
		adao.attendanceStatus(personID, attendanceDataBean);
		adao.attendancePercentage(personID, attendanceDataBean);
		return "";
	}

	public List<String> ClassListAttView(String personID, AttendanceDataBean attendanceDataBean) {
		return adao.ClassListAttView(personID, attendanceDataBean);
	}

	public String attendanceView(String personID, AttendanceDataBean attendanceDataBean) {
		return adao.attendanceView(personID, attendanceDataBean);
	}

	public String studentAttView(String personID, AttendanceDataBean attendanceDataBean) {
		return adao.studentAttView(personID, attendanceDataBean);
	}

	public List<String> parentAttlist(String personID, AttendanceDataBean attendanceDataBean) {
		return adao.parentAttlist(personID, attendanceDataBean);
	}

	public String parentstuAttView(String personID, AttendanceDataBean attendanceDataBean) {
		return adao.parentstuAttView(personID, attendanceDataBean);
	}

	@Override
	public List<String> getBorrowedBook(String personID, String categoryname, String libStudID) {

		return adao.getBorrowedBook(personID, categoryname, libStudID);
	}

	@Override
	public List<LibraryDataBean> getBoorowerBookView(String personID, String name) {

		return adao.getBoorowerBookView(personID, name);
	}

	@Override
	public List<String> getExamTitleName(String personID, String className) {
		return adao.getExamTitleName(personID, className);
	}

	@Override
	public List<String> getStudentIDList1(String name, String personID) {

		return adao.getStudentIDList1(name, personID);
	}

	@Override
	public String getRoll(String personID) {

		return adao.getRoll(personID);
	}

	@Override
	public String insertAddBook(String personID, BookSaleDataBean bookSaleDataBean) {

		return dao.insertAddBook(personID, bookSaleDataBean);
	}

	@Override
	public List<BookSaleDataBean> getBookInfo(String personID, BookSaleDataBean bookSaleDataBean) {

		return dao.getBookInfo(personID, bookSaleDataBean);
	}

	@Override
	public String addBookOrder(String personID, BookSaleDataBean bookSaleDataBean) {

		return dao.addBookOrder(personID, bookSaleDataBean);
	}

	@Override
	public List<BookSaleDataBean> getBookViewInfo(String personID, BookSaleDataBean bookSaleDataBean) {

		return dao.getBookViewInfo(personID, bookSaleDataBean);
	}

	@Override
	public List<BookSalesRecord> getBookDetailInfo(String personID, BookSaleDataBean bookSaleDataBean) {

		return dao.getBookDetailInfo(personID, bookSaleDataBean);
	}

	@Override
	public String getBookDelete(String personID, BookSaleDataBean bookSaleDataBean) {

		return dao.getBookDelete(personID, bookSaleDataBean);
	}

	@Override
	public String getBookUpdate(String personID, BookSaleDataBean bookSaleDataBean) {

		return dao.getBookUpdate(personID, bookSaleDataBean);
	}

	@Override
	public String getRollNumber(String personID) {

		return dao.getRollNumber(personID);
	}

	@Override
	public List<BookSaleDataBean> getBookInfo1(String personID, BookSaleDataBean bookSaleDataBean) {

		return dao.getBookInfo1(personID, bookSaleDataBean);
	}

	public String attendanceChart(String personID, AttendanceDataBean attendanceDataBean) {
		return adao.attendanceChart(personID, attendanceDataBean);
	}

	public List<String> getexamViewList(String personID, TimeTableDataBean timeTableDataBean) {
		return adao.getexamViewList(personID, timeTableDataBean);
	}

	public List<String> getstudentList(String personID, TimeTableDataBean timeTableDataBean) {
		return adao.getstudentList(personID, timeTableDataBean);
	}

	@Override
	public String getParentRollNumber(String personID, LoginAccess loginAccess) {

		return dao.getParentRollNumber(personID, loginAccess);
	}

	@Override
	public List<ExamTable> getTimeTableList(String personID) {

		return dao.getTimeTableList(personID);
	}

	@Override
	public List<TimeTable> getExamTableList(String personID, int examID) {

		return dao.getExamTableList(personID, examID);
	}

	@Override
	public List<School> getAdminDetails(String personID, LoginAccess loginaccess) {

		return dao.getAdminDetails(personID, loginaccess);
	}

	@Override
	public String getStudClsSection(String rollnumber) {

		return dao.getStudClsSection(rollnumber);
	}

	@Override
	public List<String> getTeaClass(String rollnumber, StudentDataBean studentDataBean) {

		return dao.getTeaClass(rollnumber, studentDataBean);
	}

	public String getmailsID(StudentPerformanceDataBean studentPerformanceDataBean) {
		return adao.getmailsID(studentPerformanceDataBean);
	}

	@Override
	public List<ParentsDataBean> getImagePath2(String personID, String parParentID) {

		return dao.getImagePath2(personID, parParentID);
	}

	@Override
	public List<NoticeBoardDataBean> getAllUserList(String personID, NoticeBoardDataBean noticeBoardDataBean) {

		return dao.getAllUserList(personID, noticeBoardDataBean);
	}

	@Override
	public String editReportCard(String personID, ReportCardDataBean reportCardDataBean) {
		return adao.editReportCard(personID, reportCardDataBean);
	}

	@Override
	public String getReportBarChart(String personID, ReportCardDataBean reportCardDataBean) {

		return adao.getReportBarChart(personID, reportCardDataBean);
	}

	public List<String> classeslist(String personID) {
		return adao.classeslist(personID);
	}

	public String classTimeTable(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		return adao.classTimeTable(personID, classTimeTableDataBean);
	}

	public List<String> subjectChange(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		return adao.subjectChange(personID, classTimeTableDataBean);
	}

	public String saveClassTimeTable(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		return adao.saveClassTimeTable(classTimeTableDataBean, personID);
	}

	public String classTimeTableView(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		return adao.classTimeTableView(classTimeTableDataBean, personID);
	}

	public String updateClassTimeTable(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		return adao.updateClassTimeTable(classTimeTableDataBean, personID);
	}

	public String deleteClassTimeTable(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		return adao.deleteClassTimeTable(classTimeTableDataBean, personID);
	}
	
	public String teacherTimeTable(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		return adao.teacherTimeTable(personID, classTimeTableDataBean);
	}

	public String classPeriod(AttendanceDataBean attendanceDataBean, String personID) {
		return adao.classPeriod(attendanceDataBean, personID);
	}

	public String updateAttendance(AttendanceDataBean attendanceDataBean, String personID) {
		return adao.updateAttendance(attendanceDataBean, personID);
	}

	public String attendanceChartStudent(String personID, AttendanceDataBean attendanceDataBean) {
		return adao.attendanceChartStudent(personID, attendanceDataBean);
	}

	public String attendanceChartStudentYear(String personID, AttendanceDataBean attendanceDataBean) {
		return adao.attendanceChartStudentYear(personID, attendanceDataBean);
	}

	public String classStudent(AttendanceDataBean attendanceDataBean, String personID) {
		return adao.classStudent(attendanceDataBean, personID);
	}

	public String leaveRequest(AttendanceDataBean attendanceDataBean, String personID) {
		return adao.leaveRequest(attendanceDataBean, personID);
	}

	public String leaveRequsetView(AttendanceDataBean attendanceDataBean, String personID) {
		return adao.leaveRequsetView(attendanceDataBean, personID);
	}

	public String leaveRequsetApproval(AttendanceDataBean attendanceDataBean, String personID) {
		return adao.leaveRequsetApproval(attendanceDataBean, personID);
	}

	@Override
	public List<ClassTimeTableDataBean> getclassTimeTable(String personID) {
		return adao.getclassTimeTable(personID);
	}

	@Override
	public String staffidcheck(StaffDataBean staffDataBean, String staStaffID) {

		return dao.staffidcheck(staffDataBean, staStaffID);
	}

	@Override
	public List<StaffDataBean> staffinfomation(String personID, StaffDataBean staffDataBean) {

		return dao.staffinfomation(personID, staffDataBean);
	}

	@Override
	public List<StaffDataBean> tagstaffview(String personID, StaffDataBean staffDataBean) {

		return dao.tagstaffview(personID, staffDataBean);
	}

	@Override
	public String insertStaff(String personID, StaffDataBean staffDataBean) {

		return dao.insertStaff(personID, staffDataBean);
	}

	@Override
	public List<StaffDataBean> getImagePath3(String personID, String staStaffID) {
		return dao.getImagePath3(personID, staStaffID);
	}

	@Override
	public String updateStaff(String personID, StaffDataBean staffDataBean) {

		return dao.updateStaff(personID, staffDataBean);
	}

	@Override
	public String deleteStaff(String personID, StaffDataBean staffDataBean) {
		return dao.deleteStaff(personID, staffDataBean);
	}

	@Override
	public String forgot(LoginAccess loginAccess) {
		return dao.forgot(loginAccess);
	}

	@Override
	public String otpvarify(String otp, String username) {

		return dao.otpvarify(otp, username);
	}

	@Override
	public String setNewpassword(String newPassword, String username) {

		return dao.setNewpassword(newPassword, username);
	}

	@Override
	public String checkLibrarianID(LibrarianDataBean librarianDataBean, String libID) {

		return dao.checkLibrarianID(librarianDataBean, libID);
	}

	@Override
	public String insertLibrarian(String personID, LibrarianDataBean librarianDataBean) {

		return dao.insertLibrarian(personID, librarianDataBean);
	}

	@Override
	public List<LibrarianDataBean> getImagePath5(String personID, String libID) {
		return dao.getImagePath5(personID, libID);
	}

	@Override
	public List<LibrarianDataBean> getAllLibrarianInfo(String personID) {
		return dao.getAllLibrarianInfo(personID);
	}

	@Override
	public String getLibrarianInfo(String personID, String libID, LibrarianDataBean librarianDataBean) {
		return dao.getLibrarianInfo(personID, libID, librarianDataBean);
	}

	@Override
	public String deleteLibrarian(String personID, LibrarianDataBean librarianDataBean) {

		return dao.deleteLibrarian(personID, librarianDataBean);
	}

	public String getStaffRollNumber(String personID, LoginAccess loginaccess) {
		return adao.getStaffRollNumber(personID, loginaccess);
	}

	public String libUpdate(LibrarianDataBean librarianDataBean, String personID) {
		return dao.libUpdate(librarianDataBean, personID);
	}

	@Override
	public int getTotalTeacher(String personID) {

		return dao.getTotalTeacher(personID);
	}

	@Override
	public int getTotalParent(String personID) {

		return dao.getTotalParent(personID);
	}

	public String totalStudent(LoginAccess loginaccess) {
		return dao.totalStudent(loginaccess);
	}

	public List<String> classSubjectList(HomeworkDatabean homeworkDatabean, String personID) {
		return adao.classSubjectList(homeworkDatabean, personID);
	}

	public List<String> classNamesList(String personID, String status) {
		return adao.classNamesList(personID,status);
	}

	public String checkHomeWork(HomeworkDatabean homeworkDatabean, String personID) {
		return adao.checkHomeWork(homeworkDatabean, personID);
	}

	public String homeWorkInsert(HomeworkDatabean homeworkDatabean, String personID) {
		return adao.homeWorkInsert(homeworkDatabean, personID);
	}

	public String classChange(HomeworkDatabean homeworkDatabean, String personID) {
		return adao.classChange(homeworkDatabean, personID);
	}

	public String updateHomeWork(HomeworkDatabean homeworkDatabean, String personID) {
		return adao.updateHomeWork(homeworkDatabean, personID);
	}

	public String deleteHomeWork(HomeworkDatabean homeworkDatabean, String personID) {
		return adao.deleteHomeWork(homeworkDatabean, personID);
	}

	public String classChange(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		return adao.classChange(personID, classTimeTableDataBean);
	}

	public String checkExtraClass(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		return adao.checkExtraClass(personID, classTimeTableDataBean);
	}

	public String insertExtraClass(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		return adao.insertExtraClass(personID, classTimeTableDataBean);
	}

	public String timeTableICheck(String personID, TimeTableDataBean timeTableDataBean) {
		return adao.timeTableICheck(personID, timeTableDataBean);
	}

	public String leaveRequsetReject(AttendanceDataBean attendanceDataBean, String personID){
		return adao.leaveRequsetReject(attendanceDataBean,personID);
	}
	
	@Override
	public List<HomeworkDatabean> getHomework(String personID) {
		return adao.getHomework(personID);
	}
	
	@Override
	public List<String> ClassListbooksale(String personID,
			BookSaleDataBean bookSaleDataBean) {
		return adao.ClassListbooksale(personID,bookSaleDataBean);
	}
	@Override
	public List<String> getstudentRollNumber(String personID, String classname) {
		
		return adao.getstudentRollNumber(personID,classname);
	}

	@Override
	public List<LibraryDataBean> getBookListView1(String personID, String categoryname) {
		
		return adao.getBookListView1(personID,categoryname);
	}

	public List<String> getSubjectListTeacher(String personID, String classname){
		return adao.getSubjectListTeacher(personID,classname);
	}

	public List<String> getRollNumber1(String personID){
		return adao.getRollNumber1(personID);
	}

	public List<StudentDataBean> getStudentDetails(String stuRollNo, StudentDataBean studentDataBean){
		return adao.getStudentDetails(stuRollNo,studentDataBean);
	}

	@Override
	public String insertStaff2(String personID, StaffDataBean staffDataBean) {
		

		  return dao.insertStaff2(personID, staffDataBean);
	}

	@Override
	public List<StaffDataBean> getImagePathstaff3(String personID, String staStaffID) {
	
		return dao.getImagePathstaff3(personID,staStaffID);
	}

	public String getPerClassList1(String personID, PaymentDatabean paymentDatabean){
		return adao.getPerClassList1(personID,paymentDatabean);
	}

	public String getPerClass(PaymentDatabean paymentDatabean){
		return adao.getPerClass(paymentDatabean);
	}

	public String insertfees(String personID, PaymentDatabean paymentDatabean){
		return adao.insertfees(personID,paymentDatabean);
	}

	public List<PaymentDatabean> feesinfomation(String personID, PaymentDatabean paymentDatabean){
		return adao.feesinfomation(personID,paymentDatabean);
	}

	public List<String> classStudent(String personID, String string){
		return adao.classStudent(personID,string);
	}

	public String classStudentFeesView(String personID, PaymentDatabean paymentDatabean){
		return adao.classStudentFeesView(personID,paymentDatabean);
	}

	public String feespay(String personID, PaymentDatabean paymentDatabean){
		return adao.feespay(personID,paymentDatabean);
	}

	public String paymentview(String personID, PaymentDatabean paymentDatabean){
		return adao.paymentview(personID,paymentDatabean);
	}

	public String updatePayment(String personID, PaymentDatabean paymentDatabean){
		return adao.updatePayment(personID,paymentDatabean);
	}

	public String deletePayment(String personID, PaymentDatabean paymentDatabean){
		return adao.deletePayment(personID,paymentDatabean);
	}

	public String approveRejectFees(String personID, PaymentDatabean paymentDatabean){
		return adao.approveRejectFees(personID,paymentDatabean);
	}
	
	@Override
	public List<BookSaleDataBean> getBookView(String personID,
			BookSaleDataBean bookSaleDataBean) {
		
		return dao.getBookView(personID,bookSaleDataBean);
	}

	@Override
	public String deletebooksale(String personID,
			BookSaleDataBean bookSaleDataBean) {
		return dao.deletebooksale(personID,bookSaleDataBean);
	}

	@Override
	public String bookOrderUpdate(String personID,
			BookSaleDataBean bookSaleDataBean) {
		return dao.bookOrderUpdate(personID,bookSaleDataBean);
	}

	@Override
	public String bookpaymentupload(String personID, BookSaleDataBean bookSaleDataBean) {
		return dao.bookpaymentupload(personID,bookSaleDataBean);
	}

	@Override
	public String bookpayemtreject(String personID, BookSaleDataBean bookSaleDataBean) {
		return dao.bookpayemtreject(personID,bookSaleDataBean);
	}

	@Override
	public String bookpayemtapprove(String personID, BookSaleDataBean bookSaleDataBean) {
		return dao.bookpayemtapprove(personID,bookSaleDataBean);
	}
	
	@Override
	public String inserschool(LoginAccess loginaccess) {
		return dao.inserschool(loginaccess);
	}

	@Override
	public List<LoginAccess> getSchoolsList(LoginAccess loginaccess) {
		return dao.getSchoolsList(loginaccess);
	}

	@Override
	public String newschoolapproval(LoginAccess login) {
		return dao.newschoolapproval(login);
	}

	@Override
	public String newschoolreject(LoginAccess login) {
		return dao.newschoolreject(login);
	}

	@Override
	public List<School> schooledit(LoginAccess login) {
		return dao.schooledit(login);
	}

	@Override
	public String schoolupdate(LoginAccess loginaccess) {
		return dao.schoolupdate(loginaccess);
	}
	
	@Override
	public String getRegisteredCustomer(RegisteredLogin registeredLogin) {
		return dao.getRegisteredCustomer(registeredLogin);
	}

	@Override
	public String getOtpCustomer(RegisteredLogin registeredLogin) {
		return dao.getOtpCustomer(registeredLogin);
	}

	@Override
	public String getOtpResent(RegisteredLogin registeredLogin) {
	return dao.getOtpResent(registeredLogin);
	}

	@Override
	public List<ClassTimeTableDataBean> getstudentclassTimeTable(
			String personID, StudentDataBean studentDataBean) {
		return dao.getstudentclassTimeTable(personID,studentDataBean);
	}
	
	public List<LibraryDataBean> getBoorowerBookView1(String personID){
		return adao.getBoorowerBookView1(personID);
	}

	public List<LibraryDataBean> getreturnBookView1(String personID){
		return adao.getreturnBookView1(personID);
	}
	
	public List<LibraryDataBean> getborrowerDetails(String personID,LibraryDataBean libraryDataBean){
		return adao.getborrowerDetails(personID,libraryDataBean);
	}
	
	public String toReturnBook(String personID, LibraryDataBean libraryDataBean){
		return adao.toReturnBook(personID,libraryDataBean);
	}
	
	public String insertclass(String personID, String schoolID,
			LibrarianDataBean librarianDataBean){
		return dao.insertclass(personID,schoolID,librarianDataBean);
	}

	public String insertsubject(String personID, String schoolID,
			LibrarianDataBean librarianDataBean){
		return dao.insertsubject(personID,schoolID,librarianDataBean);
	}

	public List<String> getmenus(){
		return dao.getmenus();
	}
	
	public String takeattendance(String personID,AttendanceDataBean attendanceDataBean){
		return dao.takeattendance(personID,attendanceDataBean);
	}
	
	public String getperstudentList(StudentPerformanceDataBean studentPerformanceDataBean,String personID){
		return dao.getperstudentList(studentPerformanceDataBean,personID);
	}
	
	public String bookstockin(BookSaleDataBean bookSaleDataBean, String personID){
		return dao.bookstockin(bookSaleDataBean,personID);
	}
	
	public List<Library> authernamelist(String authername, String personID){
		return dao.authernamelist(authername,personID);
	}

	public List<Library> booknamelist(String personID){
		return dao.booknamelist(personID);
	}

	public String stockinbookQuentity(LibraryDataBean libraryDataBean,String personID) {
		return dao.stockinbookQuentity(libraryDataBean,personID);	
	}
	
	public List<String> studentidlist(String personID){
		return dao.studentidlist(personID);	
	}

	public List<LibraryDataBean> stuinfo(String personID, String sturollno){
		return dao.stuinfo(personID,sturollno);	
	}

	public String borrowrinsertbook(String personID,
			LibraryDataBean libraryDataBean){
		return dao.borrowrinsertbook(personID,libraryDataBean);	
	}
	
	public String getNotReturnedBooks(String personID, LoginAccess loginaccess){
		return dao.getNotReturnedBooks(personID,loginaccess);	
	}
	
	public String validatecalss(LibrarianDataBean librarianDataBean,String schoolID){
		return dao.validatecalss(librarianDataBean,schoolID);	
	}

	public String validatesubject(LibrarianDataBean librarianDataBean,String schoolID){
		return dao.validatesubject(librarianDataBean,schoolID);	
	}
	
	public List<LibrarianDataBean> classlistview(String personID,LibrarianDataBean librarianDataBean) {
		return dao.classlistview(personID,librarianDataBean);	
	}

	public String editclasssec(String personID,LibrarianDataBean librarianDataBean) {
		return dao.editclasssec(personID,librarianDataBean);
	}

	public List<LibrarianDataBean> subjectlistview(String personID,LibrarianDataBean librarianDataBean) {
		return dao.subjectlistview(personID,librarianDataBean);
	}

	public String updatesujectname(String personID,LibrarianDataBean librarianDataBean) {
		return dao.updatesujectname(personID,librarianDataBean);
	}

	public String deleteclasssec(String personID,LibrarianDataBean librarianDataBean) {
		return dao.deleteclasssec(personID,librarianDataBean);
	}

	public String deletesubname(String personID,LibrarianDataBean librarianDataBean) {
		return dao.deletesubname(personID,librarianDataBean);
	}
	
	public List<TimeTableDataBean> getexamMarksList(String personID,TimeTableDataBean timeTableDataBean) {
		return adao.getexamMarksList(personID,timeTableDataBean);
	}

	public String getexammarksupdate(String personID,TimeTableDataBean timeTableDataBean) {
		return adao.getexammarksupdate(personID,timeTableDataBean);
	}

	public String getexammarkgrade(ReportCardDataBean reportCardDataBean) {
		return adao.getexammarkgrade(reportCardDataBean);
	}
	
	@Override
	public String feessave(String personID, PaymentDatabean paymentDatabean) {
		return dao.feessave(personID,paymentDatabean);
	}
	
	public String checkupdateClassTimeTable(
			ClassTimeTableDataBean classTimeTableDataBean, String personID){
		return adao.checkupdateClassTimeTable(classTimeTableDataBean,personID);
	}

	public String classTimeTableDelete(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		return adao.classTimeTableDelete(classTimeTableDataBean,personID);
	}

	public String checkExtraPeriod(String personID,
			ClassTimeTableDataBean classTimeTableDataBean){
		return adao.checkExtraPeriod(personID,classTimeTableDataBean);
	}

	public String insertExtraPeriod(String personID,
			ClassTimeTableDataBean classTimeTableDataBean){
		return adao.insertExtraPeriod(personID,classTimeTableDataBean);
	}
	
	@Override
	public List<AttendanceDataBean> attendanceReport(String personID,
			AttendanceDataBean attendanceDataBean) {
		return adao.attendanceReport(personID,attendanceDataBean);
	}

	@Override
	public List<ReportCardDataBean> getmarks(String personID,
			ReportCardDataBean reportCardDataBean) {
		return adao.getmarks(personID,reportCardDataBean);
	}

	@Override
	public List<StudentPerformanceDataBean> getStudentperformance(String personID,
			StudentPerformanceDataBean studentPerformanceDataBean) {
		return adao.getStudentperformance(personID,studentPerformanceDataBean);
	}
	
	public List<String> subjectCodeChange(String personID,ClassTimeTableDataBean classTimeTableDataBean){
		return adao.subjectCodeChange(personID,classTimeTableDataBean);

	}

	@Override
	public List<String> getcountryList(String valuechange) {
		return adao.getcountryList(valuechange);
	}

	@Override
	 public List<LoginAccess> searchvalues(String schoolID){
		logger.info("BO Called ::::::::");
		return MailSendJDBC.searchvalues(schoolID);

	 }

	@Override
	public List<TeacherDataBean> getNotesDetail(TeacherDataBean teacherDataBean) {
		return dao.getNotesDetail(teacherDataBean);
	}

	@Override
	public String updateNotes(TeacherDataBean teacherDataBean) {
		return dao.updateNotes(teacherDataBean);
	}

	@Override
	public List<TeacherDataBean> getAttendentsDetail(
			TeacherDataBean teacherDataBean) {
		return dao.getAttendentsDetail(teacherDataBean);
	}

	@Override
	public String saveAttendents(TeacherDataBean teacherDataBean) {
		return dao.saveAttendents(teacherDataBean);
	}

	@Override
	public String saveNotes(TeacherDataBean teacherDataBean) {
		return dao.saveNotes(teacherDataBean);
	}

	@Override
	public String deleteNotes(TeacherDataBean teacherDataBean) {
		return dao.deleteNotes(teacherDataBean);
	}
	@Override
	public List<String> getActivitieslist(StudentDataBean studentDataBean) {
		return dao.getActivitieslist(studentDataBean);
	}

	@Override
	public List<StudentDataBean> getActivity(StudentDataBean studentDataBean) {
		return dao.getActivity(studentDataBean);
	}

	@Override
	public String saveActivity(StudentDataBean studentDataBean) {
		return dao.saveActivity(studentDataBean);
	}

	@Override
	public String updateActivity(StudentDataBean studentDataBean) {
		return dao.updateActivity(studentDataBean);
	}

	@Override
	public String saveAddActivity(StudentDataBean studentDataBean) {
		return dao.saveAddActivity(studentDataBean);
	}

	@Override
	public String deleteActivity(StudentDataBean studentDataBean) {
		return dao.deleteActivity(studentDataBean);
	}
	
	@Override
	public List<AttendanceDataBean> attendanceSearch(String personID,
			AttendanceDataBean attendanceDataBean) {
		return adao.attendanceSearch(personID,attendanceDataBean);
	}

	@Override
	public String updateAttendanceGlobal(AttendanceDataBean attendanceDataBean,
			String personID) {
		return adao.updateAttendanceGlobal(attendanceDataBean,personID);
	}
	@Override
	public List<StudentDataBean> getstudentbehaviourList(StudentDataBean studentDataBean) {
		return dao.getstudentbehaviourList(studentDataBean);
	}

	@Override
	public List<StudentDataBean> getFeesList(StudentDataBean studentDataBean) {
		return dao.getFeesList(studentDataBean);
	}

	@Override
	public String updatefees(StudentDataBean studentDataBean) {
		return dao.updatefees(studentDataBean);
	}

	@Override
	public String deletefeesDetails(StudentDataBean studentDataBean) {
		return dao.deletefeesDetails(studentDataBean);
	}

	@Override
	public String payfees(StudentDataBean studentDataBean) {
		return dao.payfees(studentDataBean);
	}
	
	@Override
	public List<ReportCardDataBean> getglobalsearchMarkList(String schoolID,String personID, ReportCardDataBean reportCardDataBean) {
		return adao.getglobalsearchMarkList(schoolID,personID,reportCardDataBean);
	}
	
	@Override
	public String performanceCheck(
			StudentPerformanceDataBean studentPerformanceDataBean) {
		return adao.performanceCheck(studentPerformanceDataBean);
	}
	
	public String getAttendanceList(AttendanceDataBean attendanceDataBean){
		return dao.getAttendanceList(attendanceDataBean);
	}
	public String updateAttendance(AttendanceDataBean attendanceDataBean){
		return dao.updateAttendance(attendanceDataBean);

	}
	
	@Override
	public List<ExamTable> getglobalSearchTimeTableList(String schoolID,String personID, String className, String month) {
		return dao.getglobalSearchTimeTableList(schoolID,personID,className,month);
	}

	@Override
	public String getexammarkgradeGlobal(ReportCardDataBean reportCardDataBean) {
		return adao.getexammarkgradeGlobal(reportCardDataBean);
	}

	@Override
	public String addReportCardGlobal(String personID,ReportCardDataBean reportCardDataBean) {
		return adao.addReportCardGlobal(personID, reportCardDataBean);
	}
	
	@Override
	public List<LoginAccess> getUserDetails(LoginAccess loginaccess) {
		return dao.getUserDetails(loginaccess);
	}

	@Override
	public String insertformdetails(LoginAccess loginaccess) {
		return dao.insertformdetails(loginaccess);
	}
	@Override
	public List<StudentDataBean> getStudentCommunitryDetail(String communitySearchType,
			StudentDataBean studentDataBean) {
		return dao.getStudentCommunitryDetail(communitySearchType,studentDataBean);
	}
	
	@Override
	public List<String> getTeachername(String schoolID) {
		return dao.getTeachername(schoolID);
	}

	@Override
	public List<String> getStudentname(String schoolID) {
		return dao.getStudentname(schoolID);
	}

	@Override
	public void getStudentdata(String personID, StaffDataBean staffDataBean) {
		dao.getStudentdata(personID,staffDataBean);
	}

	@Override
	public String emisnoDetails(StaffDataBean staffDataBean) {
		return dao.emisnoDetails(staffDataBean);
	}

	@Override
	public String saveTC(StaffDataBean staffDataBean) {
		return dao.saveTC(staffDataBean);
	}
	@Override
	public String changePassword(LoginAccess loginaccess) {
		return dao.changePassword(loginaccess);
	}

	@Override
	public List<String> getTeaClasslist(String personID) {
		return dao.getTeaClasslist(personID);
	}

	@Override
	public void takeattendanceNew(String personID, AttendanceDataBean attendanceDataBean) {
		dao.takeattendanceNew(personID,attendanceDataBean);
		
	}

	@Override
	public void attendanceStatusNew(String personID, AttendanceDataBean attendanceDataBean) {
		adao.attendanceStatusNew(personID,attendanceDataBean);
		
	}

	@Override
	public String insertTermfees(StudentDataBean studentDataBean) {
		return dao.insertTermfees(studentDataBean);
	}
	@Override
	public String insertAttandance(String personID, String schoolID, AttendanceDataBean attendanceDataBean) {
		return dao.insertAttandance(personID, schoolID, attendanceDataBean);
	}
	
	@Override
	public String examDelete(TimeTableDataBean timeTable) {
		return adao.examDelete(timeTable);
	}

	@Override
	public String gradeDelete(TimeTableDataBean timeTable) {
		return adao.gradeDelete(timeTable);
	}
	@Override
	public String saveReport(ReportDataBean reportDataBean) {
		return dao.saveReport(reportDataBean);
	}

	@Override
	public List<ReportDataBean> getReportmenulist(ReportDataBean reportDataBean) {
		return dao.getReportmenulist(reportDataBean);
	}

	@Override
	public List<ReportDataBean> getFilterlist(ReportDataBean reportDataBean) {
		return dao.getFilterlist(reportDataBean);
	}

	@Override
	public void classNamebasedData(ReportDataBean reportDataBean) {
		dao.classNamebasedData(reportDataBean);
		
	}

	@Override
	public List<AttendanceDataBean> getAttendanceReport(
			ReportDataBean reportDataBean) {
		return dao.getAttendanceReport(reportDataBean);
	}

	@Override
	public void secNamebasedData(ReportDataBean reportDataBean) {
		dao.secNamebasedData(reportDataBean);
		
	}

	@Override
	public String deleteMenu(ReportDataBean reportDataBean) {
		return dao.deleteMenu(reportDataBean);
	}

	@Override
	public List<StudentPerformanceDataBean> getBehaviourReport(
			ReportDataBean reportDataBean) {
		return dao.getBehaviourReport(reportDataBean);
	}

	@Override
	public String updateMenuEdit(ReportDataBean reportDataBean) {
		return dao.updateMenuEdit(reportDataBean);
	}

	@Override
	public List<String> getExamListforMark(ReportDataBean reportDataBean) {
		return dao.getExamListforMark(reportDataBean);
	}

	@Override
	public List<ReportCardDataBean> getReportCardReport(ReportDataBean reportDataBean) {
		return dao.getReportCardReport(reportDataBean);
	}
	
	
	//
	
	@Override
	public List<String> teacherNameList(ReportDataBean reportDataBean) {
		
		return dao.teacherNameList(reportDataBean);
	}

	@Override
	public List<ReportDataBean> getTeacherAttendanceReport(
			ReportDataBean reportDataBean) {
		return dao.getTeacherAttendanceReport(reportDataBean);
	}

	@Override
	public List<ReportDataBean> getTeacherTimetableReport(
			ReportDataBean reportDataBean) {
		return dao.getTeacherTimetableReport(reportDataBean);
	}

	@Override
	public List<String> subjectNameList(ReportDataBean reportDataBean) {
		return dao.subjectNameList(reportDataBean);
	}
	
	
	@Override
	public String getClassEdit(StudentDataBean studentDataBean,String personID) {
		return dao.getClassEdit(studentDataBean, personID);
	}
	
	@Override
	public String insertAttandance1(String personID, String schoolID, ArrayList<AttendanceDataBean> list) {
		logger.info("----------Before Attendanceinsert Bo Calling-----");
		
		/*list.forEach((AttendanceDataBean value) -> 
		
		logger.info("ID -->"+value.getStudentID() + " Name -->" +value.getStudentID()+ 
				"Status -->"+value.getStatus() + "Class name -->"+value.getClassname()+
				"Class Section"+value.getSection()+"Date -->"+value.getDate())

		
				);*/
		 
		for(AttendanceDataBean value:list){
			System.out.print("ID -->"+value.getStudentID() + " Name -->" +value.getStudentID()+ 
					"Status -->"+value.getStatus() + "Class name -->"+value.getClassname()+
					"Class Section"+value.getSection()+"Date -->"+value.getDate());

		}
		
		return dao.insertAttendance1(personID, schoolID, list);
	}
	
}