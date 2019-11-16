package com.stumosys.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.stumosys.bo.SmsBo;
import com.stumosys.bo.SmsBoImpl;
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
//import com.stumosys.shared.BackofficeLogin;
import com.stumosys.shared.BookSalesRecord;
import com.stumosys.shared.ExamTable;
import com.stumosys.shared.Library;
import com.stumosys.shared.Noticeboard;
//import com.stumosys.shared.Product;
import com.stumosys.shared.School;
//import com.stumosys.shared.Staff;
import com.stumosys.shared.StudentDetail;
import com.stumosys.shared.StudentPerformance;
import com.stumosys.shared.SupProduct;
import com.stumosys.shared.TimeTable;
import com.stumosys.shared.UserProduct;

/**
 * This Java Class will communicate with MentariBo.java
 * 
 * @author Alex NRG
 * @date 13-11-2015
 * @copyright NRG
 */
@Service("service")
public class SmsServiceImpl implements SmsService {

	final Logger logger = Logger.getLogger(SmsServiceImpl.class);

	
	@Autowired
	@Qualifier("bo")
	SmsBo bo;

	@Override
	public String loginService(LoginAccess loginaccess) throws StumosysException {
		logger.info("Before Calling to BO");
		return bo.loginBo(loginaccess);
	}

	@Override
	public List<UserProduct> LoadMenu(LoginAccess loginaccess) {
		return bo.LoadMenu(loginaccess);
	}

	@Override
	public List<SupProduct> loadSubMenu(int product_ID, String string) {

		return bo.loadSubMenu(product_ID, string);
	}

	@Override
	public List<String> getStateList(String personID) {

		return bo.getStateList(personID);
	}

	@Override
	public String checkValidateZip(String personID, TeacherDataBean teacherDataBean) {
		return bo.checkValidateZip(personID, teacherDataBean);
	}

	@Override
	public List<String> getClassSection(String personID) {
		return bo.getClassSection(personID);
	}

	@Override
	public List<String> getSubjectList(String personID) {
		return bo.getSubjectList(personID);
	}

	@Override
	public String insertTeacher(String personID, TeacherDataBean teacherDataBean) {

		return bo.insertTeacher(personID, teacherDataBean);
	}

	@Override
	public List<TeacherDataBean> getImagePath(String personID, String teaID) {

		return bo.getImagePath(personID, teaID);
	}

	@Override
	public String checkTeacherID(TeacherDataBean teacherDataBean, String personID) {

		return bo.checkTeacherID(teacherDataBean, personID);
	}

	@Override
	public List<String> getStateLists(String personID) {

		return bo.getStateList(personID);
	}

	@Override
	public String getZipList(StudentDataBean studentdatabean) {
		return bo.getZipList(studentdatabean);
	}

	@Override
	public String getZipList1(StudentDataBean studentdatabean) {

		return bo.getZipList1(studentdatabean);
	}

	@Override
	public List<TeacherDataBean> getAllTeacherInfo(String personID, TeacherDataBean teacherDataBean) {
		return bo.getAllTeacherInfo(personID,teacherDataBean);
	}

	@Override
	public List<TeacherDataBean> getTeacherInfo(String personID, String teaID) {

		return bo.getTeacherInfo(personID, teaID);
	}

	@Override
	public String updateTeacher(String personID, TeacherDataBean teacherDataBean) {

		return bo.updateTeacher(personID, teacherDataBean);
	}

	@Override
	public String deleteTeacher(String personID, TeacherDataBean teacherDataBean) {

		return bo.deleteTeacher(personID, teacherDataBean);
	}

	@Override
	public String insertNoticeBoard(String personID, NoticeBoardDataBean noticeBoardDataBean) {

		return bo.insertNoticeBoard(personID, noticeBoardDataBean);
	}

	@Override
	public String insertStudent(String personID, StudentDataBean studentDataBean) {

		return bo.insertStudent(personID, studentDataBean);
	}

	@Override
	public List<StudentDataBean> getImagePath1(String personID, String stuRollNo) {

		return bo.getImagePath1(personID, stuRollNo);
	}

	@Override
	public List<StudentDataBean> getStudentInfo(String personID, StudentDataBean studentDataBean) {

		return bo.getStudentInfo(personID, studentDataBean);
	}

	@Override
	public List<StudentDetail> getStudentView(StudentDataBean studentDataBean,String personID) {

		return bo.getStudentView(studentDataBean,personID);
	}

	@Override
	public String getStudentEdit(StudentDataBean studentDataBean,String personID) {

		return bo.getStudentEdit(studentDataBean,personID);
	}

	@Override
	public String getStudentDelete(String personID, StudentDataBean studentDataBean) {

		return bo.getStudentDelete(personID, studentDataBean);
	}

	@Override
	public List<StudentDetail> getStudentDetails(StudentDataBean studentDataBean,String personID) {

		return bo.getStudentDetails(studentDataBean,personID);
	}

	@Override
	public String checkStudentRollno(StudentDataBean studentDataBean, String stuRollNo) {

		return bo.checkStudentRollno(studentDataBean, stuRollNo);
	}

	@Override
	public int getStudentId(String stuRollNo) {
		return bo.getStudentId(stuRollNo);
	}

	@Override
	public String getStudentClass(String stuRollNo,String personID) {

		return bo.getStudentClass(stuRollNo,personID);
	}

	@Override
	public List<StudentDataBean> getAllStudentInfo(String personID) {

		return bo.getAllStudentInfo(personID);
	}

	@Override
	public List<NoticeBoardDataBean> getNoticeBoardView(String personID) {

		return bo.getNoticeBoardView(personID);
	}

	@Override
	public List<Noticeboard> getNoticeBoardViewBYSubject(String personID, NoticeBoardDataBean noticeBoardDataBean,
			String role_user) {

		return bo.getNoticeBoardViewBYSubject(personID, noticeBoardDataBean, role_user);
	}

	@Override
	public String updateNotice(String personID, NoticeBoardDataBean noticeBoardDataBean) {

		return bo.updateNotice(personID, noticeBoardDataBean);
	}

	@Override
	public String deleteNotice(String personID, NoticeBoardDataBean noticeBoardDataBean) {

		return bo.deleteNotice(personID, noticeBoardDataBean);
	}

	@Override
	public List<String> getStudentRollNumber(String personID, String name) {

		return bo.getStudentRollNumber(personID, name);
	}

	@Override
	public String insertParents(String personID, ParentsDataBean parentsDataBean) {

		return bo.insertParents(personID, parentsDataBean);
	}

	@Override
	public List<ParentsDataBean> getParentDetilsList(String personID, ParentsDataBean parentsDataBean) {

		return bo.getParentDetilsList(personID, parentsDataBean);
	}

	@Override
	public List<ParentsDataBean> getParentInfo(String personID, ParentsDataBean parentsDataBean) {

		return bo.getParentInfo(personID, parentsDataBean);
	}

	@Override
	public String updateParent(String personID, ParentsDataBean parentsDataBean) {

		return bo.updateParent(personID, parentsDataBean);
	}

	@Override
	public String deleteParent(String personID, ParentsDataBean parentsDataBean) {

		return bo.deleteParent(personID, parentsDataBean);
	}

	@Override
	public List<String> getClassNameList(String personID) {

		return bo.getClassNameList(personID);
	}

	@Override
	public List<String> getSubjectNameList(ReportCardDataBean reportCardDataBean, String personID) {

		return bo.getSubjectNameList(reportCardDataBean, personID);
	}

	@Override
	public List<ReportCardDataBean> getStudent(String personID, ReportCardDataBean reportCardDataBean) {

		return bo.getStudent(personID, reportCardDataBean);
	}

	@Override
	public String addReportCard(String personID, ReportCardDataBean reportCardDataBean) {

		return bo.addReportCard(personID, reportCardDataBean);
	}

	@Override
	public List<String> getStudentIDList(ReportCardDataBean reportCardDataBean, String personID) {

		return bo.getStudentIDList(reportCardDataBean, personID);
	}

	@Override
	public List<String> getIdList(StudentPerformanceDataBean studentPerformanceDataBean) {

		return bo.getIdList(studentPerformanceDataBean);
	}

	@Override
	public String getPerClass(StudentPerformanceDataBean studentPerformanceDataBean,String personID) {
		return bo.getPerClass(studentPerformanceDataBean,personID);
	}

	@Override
	public String insertStudentPerform(String personID, StudentPerformanceDataBean studentPerformanceDataBean) {

		return bo.insertStudentPerform(personID, studentPerformanceDataBean);
	}

	@Override
	public List<StudentPerformanceDataBean> getStudentPerInfo(String personID,
			StudentPerformanceDataBean studentPerformanceDataBean) {

		return bo.getStudentPerInfo(personID, studentPerformanceDataBean);
	}

	@Override
	public List<String> getClassList1(String personID, StudentPerformanceDataBean studentPerformanceDataBean) {

		return bo.getClassList1(personID, studentPerformanceDataBean);
	}

	@Override
	public List<StudentPerformance> getPerformView(StudentPerformanceDataBean studentPerformanceDataBean) {

		return bo.getPerformView(studentPerformanceDataBean);
	}

	@Override
	public String getperformDelete(String personID, StudentPerformanceDataBean studentPerformanceDataBean) {

		return bo.getperformDelete(personID, studentPerformanceDataBean);
	}

	@Override
	public List<StudentPerformance> getPerformDetails(StudentPerformanceDataBean studentPerformanceDataBean) {

		return bo.getPerformDetails(studentPerformanceDataBean);
	}

	@Override
	public String getPerformEdit(String personID, StudentPerformanceDataBean studentPerformanceDataBean) {

		return bo.getPerformEdit(personID, studentPerformanceDataBean);
	}

	@Override
	public List<String> getPerClassList1(String personID,StudentPerformanceDataBean studentPerformanceDataBean) {

		return bo.getPerClassList1(personID,studentPerformanceDataBean);
	}

	@Override
	public List<ReportCardDataBean> getRepoartCard(String personID, ReportCardDataBean reportCardDataBean) {

		return bo.getRepoartCard(personID, reportCardDataBean);
	}

	public List<String> getClassList(String personID) {
		return bo.getClassList(personID);
	}

	public String subjectValues(TimeTableDataBean timeTableDataBean, String personID) {
		return bo.subjectValues(timeTableDataBean, personID);
	}

	public String changeSubjectCode(String personID, TimeTableDataBean timeTableDataBean) {
		return bo.changeSubjectCode(personID, timeTableDataBean);
	}

	public String timeTableInsert(String personID, TimeTableDataBean timeTableDataBean, List<TimeTableDataBean> timesBean) {
		return bo.timeTableInsert(personID, timeTableDataBean,timesBean);
	}

	public String examCheck(TimeTableDataBean timeTableDataBean, String personID) {
		return bo.examCheck(timeTableDataBean, personID);
	}

	public String timeTableView(TimeTableDataBean timeTableDataBean, String personID) {
		return bo.timeTableView(timeTableDataBean, personID);
	}

	public List<String> getexamList(String personID, TimeTableDataBean timeTableDataBean) {
		return bo.getexamList(personID, timeTableDataBean);
	}

	public String examRecords(TimeTableDataBean timeTableDataBean, String personID) {
		return bo.examRecords(timeTableDataBean, personID);
	}

	public String getexamRecordsList(String personID, TimeTableDataBean timeTableDataBean) {
		return bo.getexamRecordsList(personID, timeTableDataBean);
	}

	public String getexamEditList(String personID, TimeTableDataBean timeTableDataBean) {
		return bo.getexamEditList(personID, timeTableDataBean);
	}

	public String getexamupdate(String personID, TimeTableDataBean timeTableDataBean) {
		return bo.getexamupdate(personID, timeTableDataBean);
	}

	@Override
	public String insertLibrary(String personID, LibraryDataBean libraryDataBean) {

		return bo.insertLibrary(personID, libraryDataBean);
	}

	@Override
	public List<LibraryDataBean> getBookListView(String personID, String name) {

		return bo.getBookListView(personID, name);
	}

	@Override
	public List<LibraryDataBean> getBookDetailsView(String personID, LibraryDataBean libraryDataBean) {

		return bo.getBookDetailsView(personID, libraryDataBean);
	}

	@Override
	public String updateLibrary(String personID, LibraryDataBean libraryDataBean) {

		return bo.updateLibrary(personID, libraryDataBean);
	}

	@Override
	public String deleteBook(String personID, LibraryDataBean libraryDataBean) {

		return bo.deleteBook(personID, libraryDataBean);
	}

	@Override
	public String insertBookBorrowed(String personID, LibraryDataBean libraryDataBean) {

		return bo.insertBookBorrowed(personID, libraryDataBean);
	}

	public List<String> getClassListAtt(String personID) {
		return bo.getClassListAtt(personID);
	}

	public String studentList(String personID, AttendanceDataBean attendanceDataBean) {
		return bo.studentList(personID, attendanceDataBean);
	}

	public String attendanceStatus(String personID, AttendanceDataBean attendanceDataBean) {
		return bo.attendanceStatus(personID, attendanceDataBean);
	}

	public List<String> ClassListAttView(String personID, AttendanceDataBean attendanceDataBean) {
		return bo.ClassListAttView(personID, attendanceDataBean);
	}

	public String attendanceView(String personID, AttendanceDataBean attendanceDataBean) {
		return bo.attendanceView(personID, attendanceDataBean);
	}

	public String studentAttView(String personID, AttendanceDataBean attendanceDataBean) {
		return bo.studentAttView(personID, attendanceDataBean);
	}

	public List<String> parentAttlist(String personID, AttendanceDataBean attendanceDataBean) {
		return bo.parentAttlist(personID, attendanceDataBean);
	}

	public String parentstuAttView(String personID, AttendanceDataBean attendanceDataBean) {
		return bo.parentstuAttView(personID, attendanceDataBean);
	}

	@Override
	public List<String> getBorrowedBook(String personID, String categoryname, String libStudID) {

		return bo.getBorrowedBook(personID, categoryname, libStudID);
	}

	@Override
	public List<LibraryDataBean> getBoorowerBookView(String personID, String name) {

		return bo.getBoorowerBookView(personID, name);
	}

	@Override
	public List<String> getExamTitleName(String personID, String classname) {
		return bo.getExamTitleName(personID, classname);
	}

	@Override
	public List<String> getStudentIDList1(String name, String personID) {
		return bo.getStudentIDList1(name, personID);
	}

	@Override
	public String getRoll(String personID) {

		return bo.getRoll(personID);
	}

	@Override
	public String insertAddBook(String personID, BookSaleDataBean bookSaleDataBean) {

		return bo.insertAddBook(personID, bookSaleDataBean);
	}

	@Override
	public List<BookSaleDataBean> getBookInfo(String personID, BookSaleDataBean bookSaleDataBean) {

		return bo.getBookInfo(personID, bookSaleDataBean);
	}

	@Override
	public String addBookOrder(String personID, BookSaleDataBean bookSaleDataBean) {

		return bo.addBookOrder(personID, bookSaleDataBean);
	}

	@Override
	public List<BookSaleDataBean> getBookViewInfo(String personID, BookSaleDataBean bookSaleDataBean) {

		return bo.getBookViewInfo(personID, bookSaleDataBean);
	}

	@Override
	public List<BookSalesRecord> getBookDetailInfo(String personID, BookSaleDataBean bookSaleDataBean) {

		return bo.getBookDetailInfo(personID, bookSaleDataBean);
	}

	@Override
	public String getBookDelete(String personID, BookSaleDataBean bookSaleDataBean) {

		return bo.getBookDelete(personID, bookSaleDataBean);
	}

	@Override
	public String getBookUpdate(String personID, BookSaleDataBean bookSaleDataBean) {

		return bo.getBookUpdate(personID, bookSaleDataBean);
	}

	@Override
	public String getRollNumber(String personID) {

		return bo.getRollNumber(personID);
	}

	@Override
	public List<BookSaleDataBean> getBookInfo1(String personID, BookSaleDataBean bookSaleDataBean) {

		return bo.getBookInfo1(personID, bookSaleDataBean);
	}

	public String attendanceChart(String personID, AttendanceDataBean attendanceDataBean) {
		return bo.attendanceChart(personID, attendanceDataBean);
	}

	public List<String> getexamViewList(String personID, TimeTableDataBean timeTableDataBean) {
		return bo.getexamViewList(personID, timeTableDataBean);
	}

	public List<String> getstudentList(String personID, TimeTableDataBean timeTableDataBean) {
		return bo.getstudentList(personID, timeTableDataBean);
	}

	@Override
	public String getParentRollNumber(String personID, LoginAccess loginAccess) {

		return bo.getParentRollNumber(personID, loginAccess);
	}

	@Override
	public List<ExamTable> getTimeTableList(String personID) {

		return bo.getTimeTableList(personID);
	}

	@Override
	public List<TimeTable> getExamTableList(String personID, int examID) {

		return bo.getExamTableList(personID, examID);
	}

	@Override
	public List<School> getAdminDetails(String personID, LoginAccess loginaccess) {

		return bo.getAdminDetails(personID, loginaccess);
	}

	@Override
	public String getStudClsSection(String rollnumber) {

		return bo.getStudClsSection(rollnumber);
	}

	@Override
	public List<String> getTeaClass(String rollnumber, StudentDataBean studentDataBean) {

		return bo.getTeaClass(rollnumber, studentDataBean);
	}

	@Override
	public List<String> getClassList(String personID, StudentDataBean studentdatabean) {

		return bo.getClassList(personID, studentdatabean);
	}

	public String getmailsID(StudentPerformanceDataBean studentPerformanceDataBean) {
		return bo.getmailsID(studentPerformanceDataBean);
	}

	@Override
	public List<ParentsDataBean> getImagePath2(String personID, String parParentID) {

		return bo.getImagePath2(personID, parParentID);
	}

	@Override
	public List<NoticeBoardDataBean> getAllUserList(String personID, NoticeBoardDataBean noticeBoardDataBean) {
		return bo.getAllUserList(personID, noticeBoardDataBean);
	}

	@Override
	public String editReportCard(String personID, ReportCardDataBean reportCardDataBean) {

		return bo.editReportCard(personID, reportCardDataBean);
	}

	@Override
	public String getReportBarChart(String personID, ReportCardDataBean reportCardDataBean) {
		return bo.getReportBarChart(personID, reportCardDataBean);
	}

	public List<String> classeslist(String personID) {
		return bo.classeslist(personID);
	}

	public String classTimeTable(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		return bo.classTimeTable(personID, classTimeTableDataBean);
	}

	public List<String> subjectChange(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		return bo.subjectChange(personID, classTimeTableDataBean);
	}

	public String saveClassTimeTable(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		return bo.saveClassTimeTable(classTimeTableDataBean, personID);
	}

	public String classTimeTableView(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		return bo.classTimeTableView(classTimeTableDataBean, personID);
	}

	public String updateClassTimeTable(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		return bo.updateClassTimeTable(classTimeTableDataBean, personID);
	}

	public String deleteClassTimeTable(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		return bo.deleteClassTimeTable(classTimeTableDataBean, personID);
	}

	public String teacherTimeTable(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		return bo.teacherTimeTable(personID, classTimeTableDataBean);
	}

	public String classPeriod(AttendanceDataBean attendanceDataBean, String personID) {
		return bo.classPeriod(attendanceDataBean, personID);
	}

	public String updateAttendance(AttendanceDataBean attendanceDataBean, String personID) {
		return bo.updateAttendance(attendanceDataBean, personID);
	}

	public String attendanceChartStudent(String personID, AttendanceDataBean attendanceDataBean) {
		return bo.attendanceChartStudent(personID, attendanceDataBean);
	}

	public String attendanceChartStudentYear(String personID, AttendanceDataBean attendanceDataBean) {
		return bo.attendanceChartStudentYear(personID, attendanceDataBean);
	}

	public String classStudent(AttendanceDataBean attendanceDataBean, String personID) {
		return bo.classStudent(attendanceDataBean, personID);
	}

	public String leaveRequest(AttendanceDataBean attendanceDataBean, String personID) {
		return bo.leaveRequest(attendanceDataBean, personID);
	}

	public String leaveRequsetView(AttendanceDataBean attendanceDataBean, String personID) {
		return bo.leaveRequsetView(attendanceDataBean, personID);
	}

	public String leaveRequsetApproval(AttendanceDataBean attendanceDataBean, String personID) {
		return bo.leaveRequsetApproval(attendanceDataBean, personID);
	}

	@Override
	public List<ClassTimeTableDataBean> getclassTimeTable(String personID) {
		return bo.getclassTimeTable(personID);
	}

	@Override
	public List<StaffDataBean> getImagePath3(String personID, String staStaffID) {
		return bo.getImagePath3(personID, staStaffID);
	}

	@Override
	public String staffidcheck(StaffDataBean staffDataBean, String staStaffID) {

		return bo.staffidcheck(staffDataBean, staStaffID);
	}

	@Override
	public List<StaffDataBean> staffinfomation(String personID, StaffDataBean staffDataBean) {

		return bo.staffinfomation(personID, staffDataBean);
	}

	@Override
	public List<StaffDataBean> tagstaffview(String personID, StaffDataBean staffDataBean) {

		return bo.tagstaffview(personID, staffDataBean);
	}

	@Override
	public String insertStaff(String personID, StaffDataBean staffDataBean) {

		return bo.insertStaff(personID, staffDataBean);
	}

	@Override
	public String updateStaff(String personID, StaffDataBean staffDataBean) {

		return bo.updateStaff(personID, staffDataBean);
	}

	@Override
	public String deleteStaff(String personID, StaffDataBean staffDataBean) {

		return bo.deleteStaff(personID, staffDataBean);
	}

	@Override
	public String forgot(LoginAccess loginAccess) {
		return bo.forgot(loginAccess);
	}

	@Override
	public String otpvarify(String otp, String username) {
		return bo.otpvarify(otp, username);
	}

	@Override
	public String setNewpassword(String newPassword, String username) {
		return bo.setNewpassword(newPassword, username);
	}

	@Override
	public String checkLibrarianID(LibrarianDataBean librarianDataBean, String libID) {

		return bo.checkLibrarianID(librarianDataBean, libID);
	}

	@Override
	public String insertLibrarian(String personID, LibrarianDataBean librarianDataBean) {

		return bo.insertLibrarian(personID, librarianDataBean);
	}

	@Override
	public List<LibrarianDataBean> getImagePath5(String personID, String libID) {
		return bo.getImagePath5(personID, libID);
	}

	@Override
	public List<LibrarianDataBean> getAllLibrarianInfo(String personID) {
		return bo.getAllLibrarianInfo(personID);
	}

	@Override
	public String getLibrarianInfo(String personID, String libID, LibrarianDataBean librarianDataBean) {
		return bo.getLibrarianInfo(personID, libID, librarianDataBean);
	}

	@Override
	public String deleteLibrarian(String personID, LibrarianDataBean librarianDataBean) {

		return bo.deleteLibrarian(personID, librarianDataBean);
	}

	public String getStaffRollNumber(String personID, LoginAccess loginaccess) {
		return bo.getStaffRollNumber(personID, loginaccess);
	}

	public String libUpdate(LibrarianDataBean librarianDataBean, String personID) {
		return bo.libUpdate(librarianDataBean, personID);
	}

	@Override
	public int getTotalTeacher(String personID) {

		return bo.getTotalTeacher(personID);
	}

	@Override
	public int getTotalParent(String personID) {

		return bo.getTotalParent(personID);
	}

	public String totalStudent(LoginAccess loginaccess) {
		return bo.totalStudent(loginaccess);
	}

	public List<String> classSubjectList(HomeworkDatabean homeworkDatabean, String personID) {
		return bo.classSubjectList(homeworkDatabean, personID);
	}

	public List<String> classNamesList(String personID,String status) {
		return bo.classNamesList(personID,status);
	}

	public String checkHomeWork(HomeworkDatabean homeworkDatabean, String personID) {
		return bo.checkHomeWork(homeworkDatabean, personID);
	}

	public String homeWorkInsert(HomeworkDatabean homeworkDatabean, String personID) {
		return bo.homeWorkInsert(homeworkDatabean, personID);
	}

	public String classChange(HomeworkDatabean homeworkDatabean, String personID) {
		return bo.classChange(homeworkDatabean, personID);
	}

	public String updateHomeWork(HomeworkDatabean homeworkDatabean, String personID) {
		return bo.updateHomeWork(homeworkDatabean, personID);
	}

	public String deleteHomeWork(HomeworkDatabean homeworkDatabean, String personID) {
		return bo.deleteHomeWork(homeworkDatabean, personID);
	}

	public String classChange(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		return bo.classChange(personID, classTimeTableDataBean);
	}

	public String checkExtraClass(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		return bo.checkExtraClass(personID, classTimeTableDataBean);
	}

	public String insertExtraClass(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		return bo.insertExtraClass(personID, classTimeTableDataBean);
	}

	public String timeTableICheck(String personID, TimeTableDataBean timeTableDataBean) {
		return bo.timeTableICheck(personID, timeTableDataBean);
	}
	
	public String leaveRequsetReject(AttendanceDataBean attendanceDataBean, String personID){
		return bo.leaveRequsetReject(attendanceDataBean,personID);
	}
	
	@Override
	public List<HomeworkDatabean> getHomework(String personID) {
		return bo.getHomework(personID);
	}
	
	@Override
	public List<String> ClassListbooksale(String personID,
			BookSaleDataBean bookSaleDataBean) {
		return bo.ClassListbooksale(personID,bookSaleDataBean);
	}
	@Override
	public List<String> getstudentRollNumber(String personID, String classname) {
		
		return bo.getstudentRollNumber(personID,classname);
	}

	@Override
	public List<LibraryDataBean> getBookListView1(String personID, String categoryname) {
		
		return bo.getBookListView1(personID,categoryname);
	}
	
	public List<String> getSubjectListTeacher(String personID, String classname){
		return bo.getSubjectListTeacher(personID,classname);
	}

	public List<String> getRollNumber1(String personID){
		return bo.getRollNumber1(personID);
	}

	public List<StudentDataBean> getStudentDetails(String stuRollNo, StudentDataBean studentDataBean){
		return bo.getStudentDetails(stuRollNo,studentDataBean);
	}

	@Override
	public String insertStaff2(String personID, StaffDataBean staffDataBean) {
	
		return bo.insertStaff2(personID,staffDataBean);
	}

	@Override
	public List<StaffDataBean> getImagePathstaff3(String personID, String staStaffID) {
		return bo.getImagePathstaff3(personID,staStaffID);
	}
	
	public String getPerClassList1(String personID, PaymentDatabean paymentDatabean){
		return bo.getPerClassList1(personID,paymentDatabean);
	}

	public String getPerClass(PaymentDatabean paymentDatabean){
		return bo.getPerClass(paymentDatabean);
	}

	public String insertfees(String personID, PaymentDatabean paymentDatabean){
		return bo.insertfees(personID,paymentDatabean);
	}

	public List<PaymentDatabean> feesinfomation(String personID, PaymentDatabean paymentDatabean){
		return bo.feesinfomation(personID,paymentDatabean);
	}

	public List<String> classStudent(String personID, String string){
		return bo.classStudent(personID,string);
	}

	public String classStudentFeesView(String personID, PaymentDatabean paymentDatabean){
		return bo.classStudentFeesView(personID,paymentDatabean);
	}

	public String feespay(String personID, PaymentDatabean paymentDatabean){
		return bo.feespay(personID,paymentDatabean);
	}

	public String paymentview(String personID, PaymentDatabean paymentDatabean){
		return bo.paymentview(personID,paymentDatabean);
	}

	public String updatePayment(String personID, PaymentDatabean paymentDatabean){
		return bo.updatePayment(personID,paymentDatabean);
	}

	public String deletePayment(String personID, PaymentDatabean paymentDatabean){
		return bo.deletePayment(personID,paymentDatabean);
	}

	public String approveRejectFees(String personID, PaymentDatabean paymentDatabean){
		return bo.approveRejectFees(personID,paymentDatabean);
	}
	
	@Override
	public List<BookSaleDataBean> getBookView(String personID,
			BookSaleDataBean bookSaleDataBean) {
		
		return bo.getBookView(personID,bookSaleDataBean);
	}

	@Override
	public String deletebooksale(String personID,
			BookSaleDataBean bookSaleDataBean) {
		return bo.deletebooksale(personID,bookSaleDataBean);
	}

	@Override
	public String bookOrderUpdate(String personID,
			BookSaleDataBean bookSaleDataBean) {
		return bo.bookOrderUpdate(personID,bookSaleDataBean);
	}

	@Override
	public String bookpaymentupload(String personID, BookSaleDataBean bookSaleDataBean) {
		return bo.bookpaymentupload(personID,bookSaleDataBean);
	}

	@Override
	public String bookpayemtreject(String personID, BookSaleDataBean bookSaleDataBean) {
		return bo.bookpayemtreject(personID,bookSaleDataBean);
	}

	@Override
	public String bookpayemtapprove(String personID, BookSaleDataBean bookSaleDataBean) {
		return bo.bookpayemtapprove(personID,bookSaleDataBean);
	}
	
	@Override
	public String insertschool(LoginAccess loginaccess) {
		return bo.inserschool(loginaccess);
	}

	@Override
	public List<LoginAccess> getSchoolsList(LoginAccess loginaccess) {
		return bo.getSchoolsList(loginaccess);
	}

	@Override
	public String newschoolapproval(LoginAccess login) {
		return bo.newschoolapproval(login);
	}

	@Override
	public String newschoolreject(LoginAccess login) {
		return bo.newschoolreject(login);
	}

	@Override
	public List<School> schooledit(LoginAccess login) {
		return bo.schooledit(login);
	}

	@Override
	public String schoolupdate(LoginAccess loginaccess) {
		return bo.schoolupdate(loginaccess);
	}
	@Override
	public String getRegisteredCustomer(RegisteredLogin registeredLogin) {
		return bo.getRegisteredCustomer(registeredLogin);
	}

	@Override
	public String getOtpCustomer(RegisteredLogin registeredLogin) {
		return bo.getOtpCustomer(registeredLogin);
	}

	@Override
	public String getOtpResent(RegisteredLogin registeredLogin) {
		return bo.getOtpResent(registeredLogin);
	}

	@Override
	public List<ClassTimeTableDataBean> getstudentclassTimeTable(
			String personID, StudentDataBean studentDataBean) {
		return bo.getstudentclassTimeTable(personID,studentDataBean);
	}
	
	public List<LibraryDataBean> getBoorowerBookView1(String personID){
		return bo.getBoorowerBookView1(personID);
	}

	public List<LibraryDataBean> getreturnBookView1(String personID){
		return bo.getreturnBookView1(personID);
	}
	
	public List<LibraryDataBean> getborrowerDetails(String personID,LibraryDataBean libraryDataBean){
		return bo.getborrowerDetails(personID,libraryDataBean);
	}

	public String toReturnBook(String personID, LibraryDataBean libraryDataBean){
		return bo.toReturnBook(personID,libraryDataBean);
	}
	
	public String insertclass(String personID, String schoolID,
			LibrarianDataBean librarianDataBean){
		return bo.insertclass(personID,schoolID,librarianDataBean);
	}

	public String insertsubject(String personID, String schoolID,
			LibrarianDataBean librarianDataBean){
		return bo.insertsubject(personID,schoolID,librarianDataBean);
	}

	public List<String> getmenus(){
		return bo.getmenus();
	}
	
	public String takeattendance(String personID,AttendanceDataBean attendanceDataBean){
		return bo.takeattendance(personID,attendanceDataBean);
	}
	
	public String getperstudentList(StudentPerformanceDataBean studentPerformanceDataBean,String personID){
		return bo.getperstudentList(studentPerformanceDataBean,personID);
	}
	
	public String bookstockin(BookSaleDataBean bookSaleDataBean, String personID){
		return bo.bookstockin(bookSaleDataBean,personID);
	}
	
	public List<Library> authernamelist(String authername, String personID){
		return bo.authernamelist(authername,personID);
	}

	public List<Library> booknamelist(String personID){
		return bo.booknamelist(personID);
	}
	
	public String stockinbookQuentity(LibraryDataBean libraryDataBean,String personID) {
		return bo.stockinbookQuentity(libraryDataBean,personID);	
	}
	
	public List<String> studentidlist(String personID){
		return bo.studentidlist(personID);	
	}

	public List<LibraryDataBean> stuinfo(String personID, String sturollno){
		return bo.stuinfo(personID,sturollno);	
	}

	public String borrowrinsertbook(String personID,
			LibraryDataBean libraryDataBean){
		return bo.borrowrinsertbook(personID,libraryDataBean);	
	}
	
	public String getNotReturnedBooks(String personID, LoginAccess loginaccess){
		return bo.getNotReturnedBooks(personID,loginaccess);	
	}
	
	public String validatecalss(LibrarianDataBean librarianDataBean,String schoolID){
		return bo.validatecalss(librarianDataBean,schoolID);	
	}

	public String validatesubject(LibrarianDataBean librarianDataBean,String schoolID){
		return bo.validatesubject(librarianDataBean,schoolID);	
	}
	
	public List<LibrarianDataBean> classlistview(String personID,LibrarianDataBean librarianDataBean) {
		return bo.classlistview(personID,librarianDataBean);	
	}

	public String editclasssec(String personID,LibrarianDataBean librarianDataBean) {
		return bo.editclasssec(personID,librarianDataBean);
	}

	public List<LibrarianDataBean> subjectlistview(String personID,LibrarianDataBean librarianDataBean) {
		return bo.subjectlistview(personID,librarianDataBean);
	}

	public String updatesujectname(String personID,LibrarianDataBean librarianDataBean) {
		return bo.updatesujectname(personID,librarianDataBean);
	}

	public String deleteclasssec(String personID,LibrarianDataBean librarianDataBean) {
		return bo.deleteclasssec(personID,librarianDataBean);
	}

	public String deletesubname(String personID,LibrarianDataBean librarianDataBean) {
		return bo.deletesubname(personID,librarianDataBean);
	}
	
	public List<TimeTableDataBean> getexamMarksList(String personID,TimeTableDataBean timeTableDataBean) {
		return bo.getexamMarksList(personID,timeTableDataBean);
	}

	public String getexammarksupdate(String personID,TimeTableDataBean timeTableDataBean) {
		return bo.getexammarksupdate(personID,timeTableDataBean);
	}

	public String getexammarkgrade(ReportCardDataBean reportCardDataBean) {
		return bo.getexammarkgrade(reportCardDataBean);
	}
	
	@Override
	public String feessave(String personID, PaymentDatabean paymentDatabean) {
		return bo.feessave(personID,paymentDatabean);
	} 
	
	public String checkupdateClassTimeTable(
			ClassTimeTableDataBean classTimeTableDataBean, String personID){
		return bo.checkupdateClassTimeTable(classTimeTableDataBean,personID);
	}

	public String classTimeTableDelete(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		return bo.classTimeTableDelete(classTimeTableDataBean,personID);
	}

	public String checkExtraPeriod(String personID,
			ClassTimeTableDataBean classTimeTableDataBean){
		return bo.checkExtraPeriod(personID,classTimeTableDataBean);
	}

	public String insertExtraPeriod(String personID,
			ClassTimeTableDataBean classTimeTableDataBean){
		return bo.insertExtraPeriod(personID,classTimeTableDataBean);
	}
	
	@Override
	public List<AttendanceDataBean> attendanceReport(String personID,
			AttendanceDataBean attendanceDataBean) {
		return bo.attendanceReport(personID,attendanceDataBean);
	}

	@Override
	public List<ReportCardDataBean> getmarks(String personID,
			ReportCardDataBean reportCardDataBean) {
		return bo.getmarks(personID,reportCardDataBean);
	}

	@Override
	public List<StudentPerformanceDataBean> getStudentperformance(String personID,
			StudentPerformanceDataBean studentPerformanceDataBean) {
		return bo.getStudentperformance(personID,studentPerformanceDataBean);
	}
	
	public List<String> subjectCodeChange(String personID,ClassTimeTableDataBean classTimeTableDataBean){
		return bo.subjectCodeChange(personID,classTimeTableDataBean);
	}

	@Override
	public List<String> getcountryList(String valuechange) {
		return bo.getcountryList(valuechange); 
	}

	@Override
	public List<LoginAccess> searchvalues(String schoolID){
		System.out.println("Service Called ::::::::");

		return bo.searchvalues(schoolID);

	}

	@Override
	public List<TeacherDataBean> getNotesDetail(TeacherDataBean teacherDataBean) {
		return bo.getNotesDetail(teacherDataBean);
	}

	@Override
	public String updateNotes(TeacherDataBean teacherDataBean) {
		return bo.updateNotes(teacherDataBean);
	}

	@Override
	public List<TeacherDataBean> getAttendentsDetail(
			TeacherDataBean teacherDataBean) {
		return bo.getAttendentsDetail(teacherDataBean);
	}

	@Override
	public String saveAttendents(TeacherDataBean teacherDataBean) {
		return bo.saveAttendents(teacherDataBean);
	}

	@Override
	public String saveNotes(TeacherDataBean teacherDataBean) {
		return bo.saveNotes(teacherDataBean);
	}

	@Override
	public String deleteNotes(TeacherDataBean teacherDataBean) {
		return bo.deleteNotes(teacherDataBean);
	}
	@Override
	public List<String> getActivitieslist(StudentDataBean studentDataBean) {
		return bo.getActivitieslist(studentDataBean);
	}

	@Override
	public List<StudentDataBean> getActivity(StudentDataBean studentDataBean) {
		return bo.getActivity(studentDataBean);
	}

	@Override
	public String saveActivity(StudentDataBean studentDataBean) {
		return bo.saveActivity(studentDataBean);
	}

	@Override
	public String updateActivity(StudentDataBean studentDataBean) {
		return bo.updateActivity(studentDataBean);
	}

	@Override
	public String saveAddActivity(StudentDataBean studentDataBean) {
		return bo.saveAddActivity(studentDataBean);
	}

	@Override
	public String deleteActivity(StudentDataBean studentDataBean) {
		return bo.deleteActivity(studentDataBean);
	}
	

	@Override
	public List<AttendanceDataBean> attendanceSearch(String personID,
			AttendanceDataBean attendanceDataBean) {
		return bo.attendanceSearch(personID,attendanceDataBean);
	}

	@Override
	public String updateAttendanceGlobal(AttendanceDataBean attendanceDataBean,String personID) {
		return bo.updateAttendanceGlobal(attendanceDataBean,personID);
	}
	
	@Override
	public List<StudentDataBean> getstudentbehaviourList(StudentDataBean studentDataBean) {
		return bo.getstudentbehaviourList(studentDataBean);
	}

	@Override
	public List<StudentDataBean> getFeesList(StudentDataBean studentDataBean) {
		return bo.getFeesList(studentDataBean);
	}

	@Override
	public String updatefees(StudentDataBean studentDataBean) {
		return bo.updatefees(studentDataBean);
	}

	@Override
	public String deletefeesDetails(StudentDataBean studentDataBean) {
		return bo.deletefeesDetails(studentDataBean);
	}

	@Override
	public String payfees(StudentDataBean studentDataBean) {
		return bo.payfees(studentDataBean);
	}
	@Override
	public List<ReportCardDataBean> getglobalsearchMarkList(String schoolID,String personID, ReportCardDataBean reportCardDataBean) {
		return bo.getglobalsearchMarkList(schoolID,personID,reportCardDataBean);
	}
	@Override
	public String performanceCheck(
			StudentPerformanceDataBean studentPerformanceDataBean) {
		return bo.performanceCheck(studentPerformanceDataBean);
	}
	
	@Override
	public String getAttendanceList(AttendanceDataBean attendanceDataBean){
		return bo.getAttendanceList(attendanceDataBean);
	}
	@Override
	public String updateAttendance(AttendanceDataBean attendanceDataBean){
		return bo.updateAttendance(attendanceDataBean);

	}
	
	@Override
	public List<ExamTable> getglobalSearchTimeTableList(String schoolID,String personID, String className, String month) {
		return bo.getglobalSearchTimeTableList(schoolID,personID,className,month);
	}

	@Override
	public String getexammarkgradeGlobal(ReportCardDataBean reportCardDataBean) {
		return bo.getexammarkgradeGlobal(reportCardDataBean);
	}

	@Override
	public String addReportCardGlobal(String personID,ReportCardDataBean reportCardDataBean) {
		return bo.addReportCardGlobal(personID, reportCardDataBean);
	}
	
	@Override
	public List<LoginAccess> getUserDetails(LoginAccess loginaccess) {
		return bo.getUserDetails(loginaccess);
	}

	@Override
	public String insertformdetails(LoginAccess loginaccess) {
		return bo.insertformdetails(loginaccess);
	}
	@Override
	public List<StudentDataBean> getStudentCommunitryDetail(String communitySearchType,
			StudentDataBean studentDataBean) {
		return bo.getStudentCommunitryDetail(communitySearchType,studentDataBean);
	}
	@Override
	public List<String> getTeachername(String schoolID) {
		return bo.getTeachername(schoolID);
	}

	@Override
	public List<String> getStudentname(String schoolID) {
		return bo.getStudentname(schoolID);
	}

	@Override
	public void getStudentdata(String personID, StaffDataBean staffDataBean) {
		bo.getStudentdata(personID,staffDataBean);
	}

	@Override
	public String emisnoDetails(StaffDataBean staffDataBean) {
		return bo.emisnoDetails(staffDataBean);
	}

	@Override
	public String saveTC(StaffDataBean staffDataBean) {
		return bo.saveTC(staffDataBean);
	}
	@Override
	public String changePassword(LoginAccess loginaccess) {
		return bo.changePassword(loginaccess);
	}

	@Override
	public List<String> getTeaClasslist(String personID) {
		return bo.getTeaClasslist(personID);
	}

	@Override
	public void takeattendanceNew(String personID, AttendanceDataBean attendanceDataBean) {
		bo.takeattendanceNew(personID,attendanceDataBean);
		
	}

	@Override
	public void attendanceStatusNew(String personID, AttendanceDataBean attendanceDataBean) {
		bo.attendanceStatusNew(personID,attendanceDataBean);
		
	}

	@Override
	public String insertTermfees(StudentDataBean studentDataBean) {
		return bo.insertTermfees(studentDataBean);
	}
	@Override
	public String insertAttandance(String personID, String schoolID, AttendanceDataBean attendanceDataBean) {
		return bo.insertAttandance(personID, schoolID, attendanceDataBean);
	}
	@Override
	public String examDelete(TimeTableDataBean timeTable) {
		return bo.examDelete(timeTable);
	}

	@Override
	public String gradeDelete(TimeTableDataBean timeTable) {
		return bo.gradeDelete(timeTable);
	}
	

	@Override
	public String saveReport(ReportDataBean reportDataBean) {
		return bo.saveReport(reportDataBean);
	}

	@Override
	public List<ReportDataBean> getReportmenulist(ReportDataBean reportDataBean) {
		return bo.getReportmenulist(reportDataBean);
	}

	@Override
	public List<ReportDataBean> getFilterlist(ReportDataBean reportDataBean) {
		return bo.getFilterlist(reportDataBean);
	}

	@Override
	public void classNamebasedData(ReportDataBean reportDataBean) {
		bo.classNamebasedData(reportDataBean);
	}

	@Override
	public List<AttendanceDataBean> getAttendanceReport(ReportDataBean reportDataBean) {
		return bo.getAttendanceReport(reportDataBean);
	}

	@Override
	public void secNamebasedData(ReportDataBean reportDataBean) {
		bo.secNamebasedData(reportDataBean);
		
	}

	@Override
	public String deleteMenu(ReportDataBean reportDataBean) {
		return bo.deleteMenu(reportDataBean);
	}

	@Override
	public List<StudentPerformanceDataBean> getBehaviourReport(ReportDataBean reportDataBean) {
		return bo.getBehaviourReport(reportDataBean);
	}

	@Override
	public String updateMenuEdit(ReportDataBean reportDataBean) {
		return  bo.updateMenuEdit(reportDataBean);
	}

	@Override
	public List<String> getExamListforMark(ReportDataBean reportDataBean) {
		return bo.getExamListforMark(reportDataBean);
	}

	@Override
	public List<ReportCardDataBean> getReportCardReport(ReportDataBean reportDataBean) {
		return bo.getReportCardReport(reportDataBean);
	}
	
	
	
		@Override
	public List<String> teacherNameList(ReportDataBean reportDataBean) {
		return bo.teacherNameList(reportDataBean);
	}

	@Override
	public List<ReportDataBean> getTeacherAttendanceReport(
			ReportDataBean reportDataBean) {
		return bo.getTeacherAttendanceReport(reportDataBean);
	}

	@Override
	public List<ReportDataBean> getTeacherTimetableReport(
			ReportDataBean reportDataBean) {
		return bo.getTeacherTimetableReport(reportDataBean);
	}

	@Override
	public List<String> subjectNameList(ReportDataBean reportDataBean) {
		return bo.subjectNameList(reportDataBean);
	}
	
	
	@Override
	public String getClassEdit(StudentDataBean studentDataBean,String personID) {
		return bo.getClassEdit(studentDataBean, personID);
	}
	@Override
	public String insertAttandance1(String personID, String schoolID, ArrayList<AttendanceDataBean> list) {
		return bo.insertAttandance1(personID, schoolID, list);
	}
	
}
