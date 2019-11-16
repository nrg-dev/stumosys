package com.stumosys.controler;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.stumosys.domain.ReportCardDataBean;
import com.stumosys.domain.ReportDataBean;
import com.stumosys.domain.StaffDataBean;
import com.stumosys.domain.StudentDataBean;
import com.stumosys.domain.StudentPerformanceDataBean;
import com.stumosys.domain.TeacherDataBean;
import com.stumosys.domain.TimeTableDataBean;
import com.stumosys.exception.StumosysException;
import com.stumosys.service.SmsService;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("controller")
public class SmsControllerImpl implements SmsController {

	final Logger logger = LoggerFactory.getLogger(SmsControllerImpl.class);

	@Autowired
	SmsService service;

	public SmsService getService() {
		return service;
	}

	public void setService(SmsService service) {
		this.service = service;
	}

	@Override
	public String userLogin(LoginAccess loginaccess) throws StumosysException {
		return service.loginService(loginaccess);
	}

	@Override
	public List<UserProduct> LoadMenu(LoginAccess loginaccess) {

		return service.LoadMenu(loginaccess);
	}

	@Override
	public List<SupProduct> loadSubMenu(int product_ID, String string) {

		return service.loadSubMenu(product_ID, string);
	}

	@Override
	public List<String> getStateList(String personID) {

		return service.getStateList(personID);
	}

	@Override
	public String checkValidateZip(String personID, TeacherDataBean teacherDataBean) {

		return service.checkValidateZip(personID, teacherDataBean);
	}

	@Override
	public List<String> getClassSection(String personID) {

		return service.getClassSection(personID);
	}

	@Override
	public List<String> getSubjectList(String personID) {
		return service.getSubjectList(personID);
	}

	@Override
	public String insertTeacher(String personID, TeacherDataBean teacherDataBean) {

		return service.insertTeacher(personID, teacherDataBean);
	}

	@Override
	public List<TeacherDataBean> getImagePath(String personID, String teaID) {

		return service.getImagePath(personID, teaID);
	}

	@Override
	public String checkTeacherID(TeacherDataBean teacherDataBean, String personID) {

		return service.checkTeacherID(teacherDataBean, personID);
	}

	@Override
	public List<String> getStateLists(String personID) {

		return service.getStateList(personID);
	}

	@Override
	public String getZipList(StudentDataBean studentdatabean) {

		return service.getZipList(studentdatabean);
	}

	@Override
	public String getZipList1(StudentDataBean studentdatabean) {

		return service.getZipList1(studentdatabean);
	}

	@Override
	public String insertStudent(String personID, StudentDataBean studentDataBean) {

		return service.insertStudent(personID, studentDataBean);
	}

	@Override
	public List<TeacherDataBean> getAllTeacherInfo(String personID, TeacherDataBean teacherDataBean) {

		return service.getAllTeacherInfo(personID,teacherDataBean);
	}

	@Override
	public List<TeacherDataBean> getTeacherInfo(String personID, String teaID) {

		return service.getTeacherInfo(personID, teaID);
	}

	@Override
	public String updateTeacher(String personID, TeacherDataBean teacherDataBean) {

		return service.updateTeacher(personID, teacherDataBean);
	}

	@Override
	public String deleteTeacher(String personID, TeacherDataBean teacherDataBean) {

		return service.deleteTeacher(personID, teacherDataBean);
	}

	@Override
	public String insertNoticeBoard(String personID, NoticeBoardDataBean noticeBoardDataBean) {

		return service.insertNoticeBoard(personID, noticeBoardDataBean);
	}

	@Override
	public List<String> getClassList(String personID, StudentDataBean studentdatabean) {

		return service.getClassList(personID, studentdatabean);
	}

	@Override
	public List<StudentDataBean> getImagePath1(String personID, String stuRollNo) {

		return service.getImagePath1(personID, stuRollNo);
	}

	@Override
	public List<StudentDataBean> getStudentInfo(String personID, StudentDataBean studentDataBean) {

		return service.getStudentInfo(personID, studentDataBean);
	}

	@Override
	public List<StudentDetail> getStudentView(StudentDataBean studentDataBean,String personID) {

		return service.getStudentView(studentDataBean,personID);
	}

	@Override
	public String getStudentEdit(StudentDataBean studentDataBean,String personID) {

		return service.getStudentEdit(studentDataBean,personID);
	}

	@Override
	public String getStudentDelete(String personID, StudentDataBean studentDataBean) {

		return service.getStudentDelete(personID, studentDataBean);
	}

	@Override
	public List<StudentDetail> getStudentDetails(StudentDataBean studentDataBean,String personID) {

		return service.getStudentDetails(studentDataBean,personID);
	}

	@Override
	public String checkStudentRollno(StudentDataBean studentDataBean, String stuRollNo) {

		return service.checkStudentRollno(studentDataBean, stuRollNo);
	}

	@Override
	public int getStudentId(String stuRollNo) {

		return service.getStudentId(stuRollNo);
	}

	@Override
	public String getStudentClass(String stuRollNo,String personID) {

		return service.getStudentClass(stuRollNo,personID);
	}

	public List<StudentDataBean> getAllStudentInfo(String personID) {

		return service.getAllStudentInfo(personID);
	}

	@Override
	public List<NoticeBoardDataBean> getNoticeBoardView(String personID) {

		return service.getNoticeBoardView(personID);
	}

	@Override
	public List<Noticeboard> getNoticeBoardViewBYSubject(String personID, NoticeBoardDataBean noticeBoardDataBean,
			String role_user) {

		return service.getNoticeBoardViewBYSubject(personID, noticeBoardDataBean, role_user);
	}

	@Override
	public String updateNotice(String personID, NoticeBoardDataBean noticeBoardDataBean) {

		return service.updateNotice(personID, noticeBoardDataBean);
	}

	@Override
	public String deleteNotice(String personID, NoticeBoardDataBean noticeBoardDataBean) {

		return service.deleteNotice(personID, noticeBoardDataBean);
	}

	@Override
	public List<String> getStudentRollNumber(String personID, String name) {

		return service.getStudentRollNumber(personID, name);
	}

	@Override
	public String insertParents(String personID, ParentsDataBean parentsDataBean) {

		return service.insertParents(personID, parentsDataBean);
	}

	@Override
	public List<ParentsDataBean> getParentDetilsList(String personID, ParentsDataBean parentsDataBean) {

		return service.getParentDetilsList(personID, parentsDataBean);
	}

	@Override
	public List<ParentsDataBean> getParentInfo(String personID, ParentsDataBean parentsDataBean) {

		return service.getParentInfo(personID, parentsDataBean);
	}

	@Override
	public String updateParent(String personID, ParentsDataBean parentsDataBean) {

		return service.updateParent(personID, parentsDataBean);
	}

	@Override
	public String deleteParent(String personID, ParentsDataBean parentsDataBean) {

		return service.deleteParent(personID, parentsDataBean);
	}

	@Override
	public List<String> getClassNameList(String personID) {

		return service.getClassNameList(personID);
	}

	@Override
	public List<String> getSubjectNameList(ReportCardDataBean reportCardDataBean, String personID) {

		return service.getSubjectNameList(reportCardDataBean, personID);
	}

	@Override
	public List<ReportCardDataBean> getStudent(String personID, ReportCardDataBean reportCardDataBean) {

		return service.getStudent(personID, reportCardDataBean);
	}

	@Override
	public String addReportCard(String personID, ReportCardDataBean reportCardDataBean) {

		return service.addReportCard(personID, reportCardDataBean);
	}

	@Override
	public List<String> getStudentIDList(ReportCardDataBean reportCardDataBean, String personID) {

		return service.getStudentIDList(reportCardDataBean, personID);
	}

	@Override
	public List<String> getIdList(StudentPerformanceDataBean studentPerformanceDataBean) {

		return service.getIdList(studentPerformanceDataBean);
	}

	@Override
	public String getPerClass(StudentPerformanceDataBean studentPerformanceDataBean,String personID) {
		return service.getPerClass(studentPerformanceDataBean,personID);
	}

	@Override
	public String insertStudentPerform(String personID, StudentPerformanceDataBean studentPerformanceDataBean) {

		return service.insertStudentPerform(personID, studentPerformanceDataBean);
	}

	@Override
	public List<String> getClassList1(String personID, StudentPerformanceDataBean studentPerformanceDataBean) {

		return service.getClassList1(personID, studentPerformanceDataBean);
	}

	@Override
	public List<StudentPerformanceDataBean> getStudentPerInfo(String personID,
			StudentPerformanceDataBean studentPerformanceDataBean) {

		return service.getStudentPerInfo(personID, studentPerformanceDataBean);
	}

	@Override
	public List<StudentPerformance> getPerformView(StudentPerformanceDataBean studentPerformanceDataBean) {

		return service.getPerformView(studentPerformanceDataBean);
	}

	@Override
	public String getperformDelete(String personID, StudentPerformanceDataBean studentPerformanceDataBean) {

		return service.getperformDelete(personID, studentPerformanceDataBean);
	}

	@Override
	public List<StudentPerformance> getPerformDetails(StudentPerformanceDataBean studentPerformanceDataBean) {

		return service.getPerformDetails(studentPerformanceDataBean);
	}

	@Override
	public String getPerformEdit(String personID, StudentPerformanceDataBean studentPerformanceDataBean) {

		return service.getPerformEdit(personID, studentPerformanceDataBean);
	}

	@Override
	public List<String> getPerClassList1(String personID,StudentPerformanceDataBean studentPerformanceDataBean) {

		return service.getPerClassList1(personID,studentPerformanceDataBean);
	}

	@Override
	public List<ReportCardDataBean> getRepoartCard(String personID, ReportCardDataBean reportCardDataBean) {

		return service.getRepoartCard(personID, reportCardDataBean);
	}

	public List<String> getClassList(String personID) {
		return service.getClassList(personID);
	}

	public String subjectValues(TimeTableDataBean timeTableDataBean, String personID) {
		return service.subjectValues(timeTableDataBean, personID);
	}

	public String changeSubjectCode(String personID, TimeTableDataBean timeTableDataBean) {
		return service.changeSubjectCode(personID, timeTableDataBean);
	}

	public String timeTableInsert(String personID, TimeTableDataBean timeTableDataBean, List<TimeTableDataBean> timesBean) {
		return service.timeTableInsert(personID, timeTableDataBean,timesBean);
	}

	public String examCheck(TimeTableDataBean timeTableDataBean, String personID) {
		return service.examCheck(timeTableDataBean, personID);
	}

	public String timeTableView(TimeTableDataBean timeTableDataBean, String personID) {
		return service.timeTableView(timeTableDataBean, personID);
	}

	public List<String> getexamList(String personID, TimeTableDataBean timeTableDataBean) {
		return service.getexamList(personID, timeTableDataBean);
	}

	public String examRecords(TimeTableDataBean timeTableDataBean, String personID) {
		return service.examRecords(timeTableDataBean, personID);
	}

	public String getexamRecordsList(String personID, TimeTableDataBean timeTableDataBean) {
		return service.getexamRecordsList(personID, timeTableDataBean);
	}

	public String getexamEditList(String personID, TimeTableDataBean timeTableDataBean) {
		return service.getexamEditList(personID, timeTableDataBean);
	}

	public String getexamupdate(String personID, TimeTableDataBean timeTableDataBean) {
		return service.getexamupdate(personID, timeTableDataBean);
	}

	@Override
	public String insertLibrary(String personID, LibraryDataBean libraryDataBean) {

		return service.insertLibrary(personID, libraryDataBean);
	}

	@Override
	public List<LibraryDataBean> getBookListView(String personID, String name) {
		return service.getBookListView(personID, name);
	}

	@Override
	public List<LibraryDataBean> getBookDetailsView(String personID, LibraryDataBean libraryDataBean) {

		return service.getBookDetailsView(personID, libraryDataBean);
	}

	@Override
	public String updateLibrary(String personID, LibraryDataBean libraryDataBean) {

		return service.updateLibrary(personID, libraryDataBean);
	}

	@Override
	public String deleteBook(String personID, LibraryDataBean libraryDataBean) {

		return service.deleteBook(personID, libraryDataBean);
	}

	@Override
	public String insertBookBorrowed(String personID, LibraryDataBean libraryDataBean) {
		return service.insertBookBorrowed(personID, libraryDataBean);
	}

	public List<String> getClassListAtt(String personID) {
		return service.getClassListAtt(personID);
	}

	public String studentList(String personID, AttendanceDataBean attendanceDataBean) {
		return service.studentList(personID, attendanceDataBean);
	}

	public String attendanceStatus(String personID, AttendanceDataBean attendanceDataBean) {
		return service.attendanceStatus(personID, attendanceDataBean);
	}

	public List<String> ClassListAttView(String personID, AttendanceDataBean attendanceDataBean) {
		return service.ClassListAttView(personID, attendanceDataBean);
	}

	public String attendanceView(String personID, AttendanceDataBean attendanceDataBean) {
		return service.attendanceView(personID, attendanceDataBean);
	}

	public String studentAttView(String personID, AttendanceDataBean attendanceDataBean) {
		return service.studentAttView(personID, attendanceDataBean);
	}

	public List<String> parentAttlist(String personID, AttendanceDataBean attendanceDataBean) {
		return service.parentAttlist(personID, attendanceDataBean);
	}

	public String parentstuAttView(String personID, AttendanceDataBean attendanceDataBean) {
		return service.parentstuAttView(personID, attendanceDataBean);
	}

	@Override
	public List<String> getBorrowedBook(String personID, String categoryname, String libStudID) {

		return service.getBorrowedBook(personID, categoryname, libStudID);
	}

	@Override
	public List<LibraryDataBean> getBoorowerBookView(String personID, String name) {

		return service.getBoorowerBookView(personID, name);
	}

	@Override
	public List<String> getExamTitleName(String personID, String classname) {

		return service.getExamTitleName(personID, classname);
	}

	@Override
	public List<String> getStudentIDList1(String name, String personID) {

		return service.getStudentIDList1(name, personID);
	}

	@Override
	public String getRoll(String personID) {

		return service.getRoll(personID);
	}

	@Override
	public String insertAddBook(String personID, BookSaleDataBean bookSaleDataBean) {

		return service.insertAddBook(personID, bookSaleDataBean);
	}

	@Override
	public List<BookSaleDataBean> getBookInfo(String personID, BookSaleDataBean bookSaleDataBean) {

		return service.getBookInfo(personID, bookSaleDataBean);
	}

	@Override
	public String addBookOrder(String personID, BookSaleDataBean bookSaleDataBean) {

		return service.addBookOrder(personID, bookSaleDataBean);
	}

	@Override
	public List<BookSaleDataBean> getBookViewInfo(String personID, BookSaleDataBean bookSaleDataBean) {

		return service.getBookViewInfo(personID, bookSaleDataBean);
	}

	@Override
	public List<BookSalesRecord> getBookDetailInfo(String personID, BookSaleDataBean bookSaleDataBean) {

		return service.getBookDetailInfo(personID, bookSaleDataBean);
	}

	@Override
	public String getBookDelete(String personID, BookSaleDataBean bookSaleDataBean) {

		return service.getBookDelete(personID, bookSaleDataBean);
	}

	@Override
	public String getBookUpdate(String personID, BookSaleDataBean bookSaleDataBean) {

		return service.getBookUpdate(personID, bookSaleDataBean);
	}

	@Override
	public String getRollNumber(String personID) {

		return service.getRollNumber(personID);
	}

	@Override
	public List<BookSaleDataBean> getBookInfo1(String personID, BookSaleDataBean bookSaleDataBean) {

		return service.getBookInfo1(personID, bookSaleDataBean);
	}

	public String attendanceChart(String personID, AttendanceDataBean attendanceDataBean) {
		return service.attendanceChart(personID, attendanceDataBean);
	}

	public List<String> getexamViewList(String personID, TimeTableDataBean timeTableDataBean) {
		return service.getexamViewList(personID, timeTableDataBean);
	}

	public List<String> getstudentList(String personID, TimeTableDataBean timeTableDataBean) {
		return service.getstudentList(personID, timeTableDataBean);
	}

	@Override
	public String getParentRollNumber(String personID, LoginAccess loginAccess) {

		return service.getParentRollNumber(personID, loginAccess);
	}

	@Override
	public List<ExamTable> getTimeTableList(String personID) {

		return service.getTimeTableList(personID);
	}

	@Override
	public List<TimeTable> getExamTableList(String personID, int examID) {

		return service.getExamTableList(personID, examID);
	}

	@Override
	public List<School> getAdminDetails(String personID, LoginAccess loginaccess) {
		return service.getAdminDetails(personID, loginaccess);
	}

	@Override
	public String getStudClsSection(String rollnumber) {

		return service.getStudClsSection(rollnumber);
	}

	@Override
	public List<String> getTeaClass(String rollnumber, StudentDataBean studentDataBean) {

		return service.getTeaClass(rollnumber, studentDataBean);
	}

	public String getmailsID(StudentPerformanceDataBean studentPerformanceDataBean) {
		return service.getmailsID(studentPerformanceDataBean);
	}

	@Override
	public List<ParentsDataBean> getImagePath2(String personID, String parParentID) {
		return service.getImagePath2(personID, parParentID);
	}

	@Override
	public List<NoticeBoardDataBean> getAllUserList(String personID, NoticeBoardDataBean noticeBoardDataBean) {

		return service.getAllUserList(personID, noticeBoardDataBean);
	}

	@Override
	public String editReportCard(String personID, ReportCardDataBean reportCardDataBean) {

		return service.editReportCard(personID, reportCardDataBean);
	}

	@Override
	public String getReportBarChart(String personID, ReportCardDataBean reportCardDataBean) {

		return service.getReportBarChart(personID, reportCardDataBean);
	}

	public List<String> classeslist(String personID) {
		return service.classeslist(personID);
	}

	public String classTimeTable(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		return service.classTimeTable(personID, classTimeTableDataBean);
	}

	public List<String> subjectChange(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		return service.subjectChange(personID, classTimeTableDataBean);
	}

	public String saveClassTimeTable(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		return service.saveClassTimeTable(classTimeTableDataBean, personID);
	}

	public String classTimeTableView(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		return service.classTimeTableView(classTimeTableDataBean, personID);
	}

	public String updateClassTimeTable(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		return service.updateClassTimeTable(classTimeTableDataBean, personID);
	}
	public String deleteClassTimeTable(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		return service.deleteClassTimeTable(classTimeTableDataBean, personID);
	}
	public String teacherTimeTable(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		return service.teacherTimeTable(personID, classTimeTableDataBean);
	}

	public String classPeriod(AttendanceDataBean attendanceDataBean, String personID) {
		return service.classPeriod(attendanceDataBean, personID);
	}

	public String updateAttendance(AttendanceDataBean attendanceDataBean, String personID) {
		return service.updateAttendance(attendanceDataBean, personID);
	}

	public String attendanceChartStudent(String personID, AttendanceDataBean attendanceDataBean) {
		return service.attendanceChartStudent(personID, attendanceDataBean);
	}

	public String attendanceChartStudentYear(String personID, AttendanceDataBean attendanceDataBean) {
		return service.attendanceChartStudentYear(personID, attendanceDataBean);
	}

	public String classStudent(AttendanceDataBean attendanceDataBean, String personID) {
		return service.classStudent(attendanceDataBean, personID);
	}

	public String leaveRequest(AttendanceDataBean attendanceDataBean, String personID) {
		return service.leaveRequest(attendanceDataBean, personID);
	}

	public String leaveRequsetView(AttendanceDataBean attendanceDataBean, String personID) {
		return service.leaveRequsetView(attendanceDataBean, personID);
	}

	public String leaveRequsetApproval(AttendanceDataBean attendanceDataBean, String personID) {
		return service.leaveRequsetApproval(attendanceDataBean, personID);
	}

	@Override
	public List<ClassTimeTableDataBean> getclassTimeTable(String personID) {
		return service.getclassTimeTable(personID);
	}

	@Override
	public String insertStaff(String personID, StaffDataBean staffDataBean) {

		return service.insertStaff(personID, staffDataBean);
	}

	@Override
	public List<StaffDataBean> getImagePath3(String personID, String staStaffID) {
		return service.getImagePath3(personID, staStaffID);
	}

	@Override
	public String staffidcheck(StaffDataBean staffDataBean, String staStaffID) {

		return service.staffidcheck(staffDataBean, staStaffID);
	}

	@Override
	public List<StaffDataBean> staffinfomation(String personID, StaffDataBean staffDataBean) {

		return service.staffinfomation(personID, staffDataBean);
	}

	@Override
	public List<StaffDataBean> tagstaffview(String personID, StaffDataBean staffDataBean) {

		return service.tagstaffview(personID, staffDataBean);
	}

	@Override
	public String updateStaff(String personID, StaffDataBean staffDataBean) {

		return service.updateStaff(personID, staffDataBean);
	}

	@Override
	public String deleteStaff(String personID, StaffDataBean staffDataBean) {

		return service.deleteStaff(personID, staffDataBean);
	}

	@Override
	public String forgot(LoginAccess loginAccess) {
		return service.forgot(loginAccess);
	}

	@Override
	public String otpvarify(String otp, String username) {

		return service.otpvarify(otp, username);
	}

	@Override
	public String setNewpassword(String newPassword, String username) {

		return service.setNewpassword(newPassword, username);
	}

	@Override
	public String checkLibrarianID(LibrarianDataBean librarianDataBean, String libID) {

		return service.checkLibrarianID(librarianDataBean, libID);
	}

	@Override
	public String insertLibrarian(String personID, LibrarianDataBean librarianDataBean) {

		return service.insertLibrarian(personID, librarianDataBean);
	}

	@Override
	public List<LibrarianDataBean> getImagePath5(String personID, String libID) {
		return service.getImagePath5(personID, libID);
	}

	@Override
	public List<LibrarianDataBean> getAllLibrarianInfo(String personID) {
		return service.getAllLibrarianInfo(personID);
	}

	@Override
	public String getLibrarianInfo(String personID, String libID, LibrarianDataBean librarianDataBean) {
		return service.getLibrarianInfo(personID, libID, librarianDataBean);
	}

	@Override
	public String deleteLibrarian(String personID, LibrarianDataBean librarianDataBean) {

		return service.deleteLibrarian(personID, librarianDataBean);
	}

	public String getStaffRollNumber(String personID, LoginAccess loginaccess) {
		return service.getStaffRollNumber(personID, loginaccess);
	}

	public String libUpdate(LibrarianDataBean librarianDataBean, String personID) {
		return service.libUpdate(librarianDataBean, personID);
	}

	@Override
	public int getTotalTeacher(String personID) {

		return service.getTotalTeacher(personID);
	}

	@Override
	public int getTotalParent(String personID) {
		return service.getTotalParent(personID);
	}

	public String totalStudent(LoginAccess loginaccess) {
		return service.totalStudent(loginaccess);
	}

	public List<String> classSubjectList(HomeworkDatabean homeworkDatabean, String personID) {
		return service.classSubjectList(homeworkDatabean, personID);
	}

	public List<String> classNamesList(String personID, String status) {
		return service.classNamesList(personID,status);
	}

	public String checkHomeWork(HomeworkDatabean homeworkDatabean, String personID) {
		return service.checkHomeWork(homeworkDatabean, personID);
	}

	public String homeWorkInsert(HomeworkDatabean homeworkDatabean, String personID) {
		return service.homeWorkInsert(homeworkDatabean, personID);
	}

	public String classChange(HomeworkDatabean homeworkDatabean, String personID) {
		return service.classChange(homeworkDatabean, personID);
	}

	public String updateHomeWork(HomeworkDatabean homeworkDatabean, String personID) {
		return service.updateHomeWork(homeworkDatabean, personID);
	}

	public String deleteHomeWork(HomeworkDatabean homeworkDatabean, String personID) {
		return service.deleteHomeWork(homeworkDatabean, personID);
	}

	public String classChange(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		return service.classChange(personID, classTimeTableDataBean);
	}

	public String checkExtraClass(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		return service.checkExtraClass(personID, classTimeTableDataBean);
	}

	public String insertExtraClass(String personID, ClassTimeTableDataBean classTimeTableDataBean) {
		return service.insertExtraClass(personID, classTimeTableDataBean);
	}

	public String timeTableICheck(String personID, TimeTableDataBean timeTableDataBean) {
		return service.timeTableICheck(personID, timeTableDataBean);
	}
	
	public String leaveRequsetReject(AttendanceDataBean attendanceDataBean, String personID){
		return service.leaveRequsetReject(attendanceDataBean,personID);
	}
	
	@Override
	public List<HomeworkDatabean> getHomework(String personID) {
		return service.getHomework(personID);
	}
	
	@Override
	public List<String> ClassListbooksale(String personID,
			BookSaleDataBean bookSaleDataBean) {
		return service.ClassListbooksale(personID,bookSaleDataBean);
	}
	@Override
	public List<String> getstudentRollNumber(String personID, String classname) {
		
		return service.getstudentRollNumber(personID,classname);
	}

	@Override
	public List<LibraryDataBean> getBookListView1(String personID, String categoryname) {
	
		return service.getBookListView1(personID,categoryname);
	}

	@Override
	public String insertStaff2(String personID, StaffDataBean staffDataBean) {
		
		return service.insertStaff2(personID,staffDataBean);	
	}

	@Override
	public List<StaffDataBean> getImagePathstaff3(String personID, String staStaffID) {
		
		return service.getImagePathstaff3(personID,staStaffID);
	}
	
	public String getPerClassList1(String personID, PaymentDatabean paymentDatabean){
		return service.getPerClassList1(personID,paymentDatabean);
	}

	public String getPerClass(PaymentDatabean paymentDatabean){
		return service.getPerClass(paymentDatabean);
	}

	public String insertfees(String personID, PaymentDatabean paymentDatabean){
		return service.insertfees(personID,paymentDatabean);
	}

	public List<PaymentDatabean> feesinfomation(String personID, PaymentDatabean paymentDatabean){
		return service.feesinfomation(personID,paymentDatabean);
	}

	public List<String> classStudent(String personID, String string){
		return service.classStudent(personID,string);
	}

	public String classStudentFeesView(String personID, PaymentDatabean paymentDatabean){
		return service.classStudentFeesView(personID,paymentDatabean);
	}

	public String feespay(String personID, PaymentDatabean paymentDatabean){
		return service.feespay(personID,paymentDatabean);
	}

	public String paymentview(String personID, PaymentDatabean paymentDatabean){
		return service.paymentview(personID,paymentDatabean);
	}

	public String updatePayment(String personID, PaymentDatabean paymentDatabean){
		return service.updatePayment(personID,paymentDatabean);
	}

	public String deletePayment(String personID, PaymentDatabean paymentDatabean){
		return service.deletePayment(personID,paymentDatabean);
	}

	public String approveRejectFees(String personID, PaymentDatabean paymentDatabean){
		return service.approveRejectFees(personID,paymentDatabean);
	}
	
	@Override
	public List<BookSaleDataBean> getBookView(String personID,
			BookSaleDataBean bookSaleDataBean) {
		
		return service.getBookView(personID,bookSaleDataBean);
	}

	@Override
	public String deletebooksale(String personID,
			BookSaleDataBean bookSaleDataBean) {
		return service.deletebooksale(personID,bookSaleDataBean);
	}

	@Override
	public String bookOrderUpdate(String personID,
			BookSaleDataBean bookSaleDataBean) {
		return service.bookOrderUpdate(personID,bookSaleDataBean);
	}

	@Override
	public String bookpaymentupload(String personID, BookSaleDataBean bookSaleDataBean) {
		return service.bookpaymentupload(personID,bookSaleDataBean);
	}

	@Override
	public String bookpayemtreject(String personID, BookSaleDataBean bookSaleDataBean) {
		return service.bookpayemtreject(personID,bookSaleDataBean);
	}

	@Override
	public String bookpayemtapprove(String personID, BookSaleDataBean bookSaleDataBean) {
		return service.bookpayemtapprove(personID,bookSaleDataBean);
	}
	@Override
	public String insertschool(LoginAccess loginaccess) {
		return service.insertschool(loginaccess);
	}

	@Override
	public List<LoginAccess> getSchoolsList(LoginAccess loginaccess) {
		return service.getSchoolsList(loginaccess);
	}

	@Override
	public String newschoolapproval(LoginAccess login) {
		return service.newschoolapproval(login);
	}

	@Override
	public String newschoolreject(LoginAccess login) {
		return service.newschoolreject(login);
	}

	@Override
	public List<School> schooledit(LoginAccess login) {
		return service.schooledit(login);
	}

	@Override
	public String schoolupdate(LoginAccess loginaccess) {
		return service.schoolupdate(loginaccess);
	}
	
	@Override
	public List<ClassTimeTableDataBean> getstudentclassTimeTable(
			String personID, StudentDataBean studentDataBean) {
		return service.getstudentclassTimeTable(personID,studentDataBean);
	}
	
	public List<LibraryDataBean> getBoorowerBookView1(String personID){
		return service.getBoorowerBookView1(personID);
	}

	public List<LibraryDataBean> getreturnBookView1(String personID){
		return service.getreturnBookView1(personID);
	}
	
	public List<LibraryDataBean> getborrowerDetails(String personID,LibraryDataBean libraryDataBean){
		return service.getborrowerDetails(personID,libraryDataBean);
	}

	public String toReturnBook(String personID, LibraryDataBean libraryDataBean){
		return service.toReturnBook(personID,libraryDataBean);
	}
	
	public String insertclass(String personID, String schoolID,
			LibrarianDataBean librarianDataBean){
		return service.insertclass(personID,schoolID,librarianDataBean);
	}

	public String insertsubject(String personID, String schoolID,
			LibrarianDataBean librarianDataBean){
		return service.insertsubject(personID,schoolID,librarianDataBean);
	}

	public List<String> getmenus(){
		return service.getmenus();
	}
	
	public String takeattendance(String personID,AttendanceDataBean attendanceDataBean){
		return service.takeattendance(personID,attendanceDataBean);
	}
	
	public String getperstudentList(StudentPerformanceDataBean studentPerformanceDataBean,String personID){
		return service.getperstudentList(studentPerformanceDataBean,personID);
	}
	
	public String bookstockin(BookSaleDataBean bookSaleDataBean, String personID){
		return service.bookstockin(bookSaleDataBean,personID);
	}
	
	public List<Library> authernamelist(String authername, String personID){
		return service.authernamelist(authername,personID);
	}

	public List<Library> booknamelist(String personID){
		return service.booknamelist(personID);
	}

	public String stockinbookQuentity(LibraryDataBean libraryDataBean,String personID) {
		return service.stockinbookQuentity(libraryDataBean,personID);	
	}
	
	public List<String> studentidlist(String personID){
		return service.studentidlist(personID);	
	}

	public List<LibraryDataBean> stuinfo(String personID, String sturollno){
		return service.stuinfo(personID,sturollno);	
	}

	public String borrowrinsertbook(String personID,
			LibraryDataBean libraryDataBean){
		return service.borrowrinsertbook(personID,libraryDataBean);	
	}
	
	public String getNotReturnedBooks(String personID, LoginAccess loginaccess){
		return service.getNotReturnedBooks(personID,loginaccess);	
	}
	
	public String validatecalss(LibrarianDataBean librarianDataBean,String schoolID){
		return service.validatecalss(librarianDataBean,schoolID);	
	}

	public String validatesubject(LibrarianDataBean librarianDataBean,String schoolID){
		return service.validatesubject(librarianDataBean,schoolID);	
	}
	
	public List<LibrarianDataBean> classlistview(String personID,LibrarianDataBean librarianDataBean) {
		return service.classlistview(personID,librarianDataBean);	
	}

	public String editclasssec(String personID,LibrarianDataBean librarianDataBean) {
		return service.editclasssec(personID,librarianDataBean);
	}

	public List<LibrarianDataBean> subjectlistview(String personID,LibrarianDataBean librarianDataBean) {
		return service.subjectlistview(personID,librarianDataBean);
	}

	public String updatesujectname(String personID,LibrarianDataBean librarianDataBean) {
		return service.updatesujectname(personID,librarianDataBean);
	}

	public String deleteclasssec(String personID,LibrarianDataBean librarianDataBean) {
		return service.deleteclasssec(personID,librarianDataBean);
	}

	public String deletesubname(String personID,LibrarianDataBean librarianDataBean) {
		return service.deletesubname(personID,librarianDataBean);
	}
	
	public List<TimeTableDataBean> getexamMarksList(String personID,TimeTableDataBean timeTableDataBean) {
		return service.getexamMarksList(personID,timeTableDataBean);
	}

	public String getexammarksupdate(String personID,TimeTableDataBean timeTableDataBean) {
		return service.getexammarksupdate(personID,timeTableDataBean);
	}

	public String getexammarkgrade(ReportCardDataBean reportCardDataBean) {
		return service.getexammarkgrade(reportCardDataBean);
	}
	
	@Override
	public String feessave(String personID, PaymentDatabean paymentDatabean) {
		return service.feessave(personID,paymentDatabean);
	} 
	
	public String checkupdateClassTimeTable(
			ClassTimeTableDataBean classTimeTableDataBean, String personID){
		return service.checkupdateClassTimeTable(classTimeTableDataBean,personID);
	}

	@Override
	public String classTimeTableDelete(ClassTimeTableDataBean classTimeTableDataBean, String personID) {
		return service.classTimeTableDelete(classTimeTableDataBean,personID);
	}

	public String checkExtraPeriod(String personID,
			ClassTimeTableDataBean classTimeTableDataBean){
		return service.checkExtraPeriod(personID,classTimeTableDataBean);
	}

	public String insertExtraPeriod(String personID,
			ClassTimeTableDataBean classTimeTableDataBean){
		return service.insertExtraPeriod(personID,classTimeTableDataBean);
	}
	
	@Override
	public List<AttendanceDataBean> attendanceReport(String personID,
			AttendanceDataBean attendanceDataBean) {
		return service.attendanceReport(personID,attendanceDataBean);
	}

	@Override
	public List<ReportCardDataBean> getmarks(String personID,
			ReportCardDataBean reportCardDataBean) {
		return service.getmarks(personID,reportCardDataBean);
	}

	@Override
	public List<StudentPerformanceDataBean> getStudentperformance(String personID,
			StudentPerformanceDataBean studentPerformanceDataBean) {
		return service.getStudentperformance(personID,studentPerformanceDataBean);
	}
	
	public List<String> getSubjectListTeacher(String personID, String classname){
		return service.getSubjectListTeacher(personID,classname);
	}
	
	public List<String> subjectCodeChange(String personID,ClassTimeTableDataBean classTimeTableDataBean){
		return service.subjectCodeChange(personID,classTimeTableDataBean);
	}

	@Override
	public List<String> getcountryList(String valuechange) {
		return service.getcountryList(valuechange); 
	}

	@Override
	public List<LoginAccess> searchvalues(String schoolID){
		System.out.println("------ Inside searchvalues() menthod ------");
		return service.searchvalues(schoolID);

	}

	@Override
	public List<TeacherDataBean> getAttendentsDetail(TeacherDataBean teacherDataBean) {
		return service.getAttendentsDetail(teacherDataBean);
	}

	@Override
	public String saveAttendents(TeacherDataBean teacherDataBean) {
		return service.saveAttendents(teacherDataBean);
	}

	@Override
	public String saveNotes(TeacherDataBean teacherDataBean) {
		return service.saveNotes(teacherDataBean);
	}

	@Override
	public List<TeacherDataBean> getNotesDetail(TeacherDataBean teacherDataBean) {
		return service.getNotesDetail(teacherDataBean);
	}

	@Override
	public String updateNotes(TeacherDataBean teacherDataBean) {
		return service.updateNotes(teacherDataBean);
	}

	@Override
	public String deleteNotes(TeacherDataBean teacherDataBean) {
		return service.deleteNotes(teacherDataBean);
	}

	@Override
	public List<String> getActivitieslist(StudentDataBean studentDataBean) {
		return service.getActivitieslist(studentDataBean);
	}

	@Override
	public List<StudentDataBean> getActivity(StudentDataBean studentDataBean) {
		return service.getActivity(studentDataBean);
	}

	@Override
	public String saveActivity(StudentDataBean studentDataBean) {
		return service.saveActivity(studentDataBean);
	}

	@Override
	public String updateActivity(StudentDataBean studentDataBean) {
		return service.updateActivity(studentDataBean);
	}

	@Override
	public String saveAddActivity(StudentDataBean studentDataBean) {
		return service.saveAddActivity(studentDataBean);
	}

	@Override
	public String deleteActivity(StudentDataBean studentDataBean) {
		return service.deleteActivity(studentDataBean);
	}

	@Override
	public List<AttendanceDataBean> attendanceSearch(String personID,
			AttendanceDataBean attendanceDataBean) {
		return service.attendanceSearch(personID,attendanceDataBean);
	}

	@Override
	public String updateAttendanceGlobal(AttendanceDataBean attendanceDataBean,
			String personID) {
		return service.updateAttendanceGlobal(attendanceDataBean,personID);
	}
	@Override
	public List<StudentDataBean> getstudentbehaviourList(StudentDataBean studentDataBean) {
		return service.getstudentbehaviourList(studentDataBean);
	}

	@Override
	public List<StudentDataBean> getFeesList(StudentDataBean studentDataBean) {
		return service.getFeesList(studentDataBean);
	}

	@Override
	public String updatefees(StudentDataBean studentDataBean) {
		return service.updatefees(studentDataBean);
	}

	@Override
	public String deletefeesDetails(StudentDataBean studentDataBean) {
		return service.deletefeesDetails(studentDataBean);
	}

	@Override
	public String payfees(StudentDataBean studentDataBean) {
		return service.payfees(studentDataBean);
	}
	
	@Override
	public List<ReportCardDataBean> getglobalsearchMarkList(String schoolID,String personID, ReportCardDataBean reportCardDataBean) {
		return service.getglobalsearchMarkList(schoolID,personID,reportCardDataBean);
	}

	@Override
	public String performanceCheck(
			StudentPerformanceDataBean studentPerformanceDataBean) {
		return service.performanceCheck(studentPerformanceDataBean);
	}
	
	@Override
	public String getAttendanceList(AttendanceDataBean attendanceDataBean){
		return service.getAttendanceList(attendanceDataBean);
	}
	@Override
	public String updateAttendance(AttendanceDataBean attendanceDataBean){
		return service.updateAttendance(attendanceDataBean);

	}
	
	@Override
	public List<ExamTable> getglobalSearchTimeTableList(String schoolID,String personID, String className, String month) {
		return service.getglobalSearchTimeTableList(schoolID,personID,className,month);
	}

	@Override
	public String getexammarkgradeGlobal(ReportCardDataBean reportCardDataBean) {
		return service.getexammarkgradeGlobal(reportCardDataBean);
	}

	@Override
	public String addReportCardGlobal(String personID,ReportCardDataBean reportCardDataBean) {
		return service.addReportCardGlobal(personID, reportCardDataBean);
	}
	
	@Override
	public List<LoginAccess> getUserDetails(LoginAccess loginaccess) {
		return service.getUserDetails(loginaccess);
	}

	@Override
	public String insertformdetails(LoginAccess loginaccess) {
		return service.insertformdetails(loginaccess);
	}

	@Override
	public List<StudentDataBean> getStudentCommunitryDetail(String communitySearchType,
			StudentDataBean studentDataBean) {
		return service.getStudentCommunitryDetail(communitySearchType,studentDataBean);
	}
	
	@Override
	public List<String> getTeachername(String schoolID) {
		return service.getTeachername(schoolID);
	}

	@Override
	public List<String> getStudentname(String schoolID) {
		return service.getStudentname(schoolID);
	}

	@Override
	public void getStudentdate(String personID, StaffDataBean staffDataBean) {
		service.getStudentdata(personID,staffDataBean);
	}

	@Override
	public String emisnoDetails(StaffDataBean staffDataBean) {
		return service.emisnoDetails(staffDataBean);
	}

	@Override
	public String saveTC(StaffDataBean staffDataBean) {
		return service.saveTC(staffDataBean);
	}

	@Override
	public String changePassword(LoginAccess loginaccess) {
		return service.changePassword(loginaccess);
	}

	@Override
	public List<String> getTeaClasslist(String personID) {
		return service.getTeaClasslist(personID);
	}

	@Override
	public void takeattendanceNew(String personID, AttendanceDataBean attendanceDataBean) {
		service.takeattendanceNew(personID,attendanceDataBean);
		
	}

	@Override
	public void attendanceStatusNew(String personID, AttendanceDataBean attendanceDataBean) {
		service.attendanceStatusNew(personID,attendanceDataBean);
		
	}

	@Override
	public String insertTermfees(StudentDataBean studentDataBean) {
		return service.insertTermfees(studentDataBean);
	}

	@Override
	public String insertAttandance(String personID, String schoolID, AttendanceDataBean attendanceDataBean) {
		return service.insertAttandance(personID, schoolID, attendanceDataBean);
	}
	
	@Override
	public String examDelete(TimeTableDataBean timeTable) {
		return service.examDelete(timeTable);
	}

	@Override
	public String gradeDelete(TimeTableDataBean timeTable) {
		return service.gradeDelete(timeTable);
	}
	
	@Override
	public String saveReport(ReportDataBean reportDataBean) {
		return service.saveReport(reportDataBean);
	}

	@Override
	public List<ReportDataBean> getReportmenulist(ReportDataBean reportDataBean) {
		return service.getReportmenulist(reportDataBean);
	}

	@Override
	public List<ReportDataBean> getFilterlist(ReportDataBean reportDataBean) {
		return service.getFilterlist(reportDataBean);
	}

	@Override
	public void classNamebasedData(ReportDataBean reportDataBean) {
		service.classNamebasedData(reportDataBean);
	}

	@Override
	public List<AttendanceDataBean> getAttendanceReport(ReportDataBean reportDataBean) {
		return service.getAttendanceReport(reportDataBean);
	}

	@Override
	public void secNamebasedData(ReportDataBean reportDataBean) {
		service.secNamebasedData(reportDataBean);
	}

	@Override
	public String deleteMenu(ReportDataBean reportDataBean) {
		return service.deleteMenu(reportDataBean);
	}

	@Override
	public List<StudentPerformanceDataBean> getBehaviourReport(ReportDataBean reportDataBean) {
		return service.getBehaviourReport(reportDataBean);
	}

	@Override
	public String updateMenuEdit(ReportDataBean reportDataBean) {
		return service.updateMenuEdit(reportDataBean);
	}

	@Override
	public List<String> getExamListforMark(ReportDataBean reportDataBean) {
		return service.getExamListforMark(reportDataBean);
	}

	@Override
	public List<ReportCardDataBean> getReportCardReport(ReportDataBean reportDataBean) {
		return service.getReportCardReport(reportDataBean);
	}
	
	@Override
	public List<String> teacherNameList(ReportDataBean reportDataBean) {
		return service.teacherNameList(reportDataBean);
	}

	@Override
	public List<ReportDataBean> getTeacherAttendanceReport(
			ReportDataBean reportDataBean) {
		return service.getTeacherAttendanceReport(reportDataBean);
	}

	@Override
	public List<ReportDataBean> getTeacherTimetableReport(
			ReportDataBean reportDataBean) {
		return service.getTeacherTimetableReport(reportDataBean);
	}

	@Override
	public List<String> subjectNameList(ReportDataBean reportDataBean) {
		return service.subjectNameList(reportDataBean);
	}
	
	@Override
	public String getClassEdit(StudentDataBean studentDataBean,String personID) {
		return service.getClassEdit(studentDataBean, personID);
	}

	
	
	
}