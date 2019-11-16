package com.stumosys.bo;

import java.util.ArrayList;
import java.util.List;
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
import com.stumosys.shared.School;
import com.stumosys.shared.StudentDetail;
import com.stumosys.shared.StudentPerformance;
import com.stumosys.shared.SupProduct;
import com.stumosys.shared.TimeTable;
import com.stumosys.shared.UserProduct;

/**
 * This Java Class will communicate with SMSDaoImpl.java
 * 
 * @author Alex
 * @date 13-11-2015
 * @copyright NRG
 * 
 */
public interface SmsBo {
	
	public String loginBo(LoginAccess loginAccess) throws StumosysException;
	public List<UserProduct> LoadMenu(LoginAccess loginaccess);
	public List<SupProduct> loadSubMenu(int product_ID, String string);
	public List<String> getStateList(String personID);
	public String checkValidateZip(String personID, TeacherDataBean teacherDataBean);
	public List<String> getClassSection(String personID);
	public List<String> getSubjectList(String personID);
	public String insertTeacher(String personID, TeacherDataBean teacherDataBean);
	public List<TeacherDataBean> getImagePath(String personID, String teaID);
	public String checkTeacherID(TeacherDataBean teacherDataBean, String personID);
	public List<String> getStateLists(String personID);
	public String getZipList(StudentDataBean studentdatabean);
	public String getZipList1(StudentDataBean studentdatabean);
	public List<TeacherDataBean> getAllTeacherInfo(String personID, TeacherDataBean teacherDataBean);
	public List<TeacherDataBean> getTeacherInfo(String personID, String teaID);
	public String updateTeacher(String personID, TeacherDataBean teacherDataBean);
	public String deleteTeacher(String personID, TeacherDataBean teacherDataBean);
	public String insertNoticeBoard(String personID, NoticeBoardDataBean noticeBoardDataBean);
	public List<String> getClassList(String personID, StudentDataBean studentdatabean);
	public String insertStudent(String personID, StudentDataBean studentDataBean);
	public List<StudentDataBean> getImagePath1(String personID, String stuRollNo);
	public List<StudentDataBean> getStudentInfo(String personID, StudentDataBean studentDataBean);
	public List<StudentDetail> getStudentView(StudentDataBean studentDataBean,String personID);
	public String getStudentEdit(StudentDataBean studentDataBean,String personID);
	public String getStudentDelete(String personID, StudentDataBean studentDataBean);
	public List<StudentDetail> getStudentDetails(StudentDataBean studentDataBean,String PersonID);
	public String checkStudentRollno(StudentDataBean studentDataBean, String stuRollNo);
	public int getStudentId(String stuRollNo);
	public String getStudentClass(String stuRollNo,String personID);
	public List<StudentDataBean> getAllStudentInfo(String personID);
	public List<NoticeBoardDataBean> getNoticeBoardView(String personID);
	public List<Noticeboard> getNoticeBoardViewBYSubject(String personID, NoticeBoardDataBean noticeBoardDataBean,String role_user);
	public String updateNotice(String personID, NoticeBoardDataBean noticeBoardDataBean);
	public String deleteNotice(String personID, NoticeBoardDataBean noticeBoardDataBean);
	public List<String> getStudentRollNumber(String personID, String name);
	public String insertParents(String personID, ParentsDataBean parentsDataBean);
	public List<ParentsDataBean> getParentDetilsList(String personID, ParentsDataBean parentsDataBean);
	public List<ParentsDataBean> getParentInfo(String personID, ParentsDataBean parentsDataBean);
	public String updateParent(String personID, ParentsDataBean parentsDataBean);
	public String deleteParent(String personID, ParentsDataBean parentsDataBean);
	public List<String> getClassNameList(String personID);
	public List<String> getSubjectNameList(ReportCardDataBean reportCardDataBean, String personID);
	public List<ReportCardDataBean> getStudent(String personID, ReportCardDataBean reportCardDataBean);
	public String addReportCard(String personID, ReportCardDataBean reportCardDataBean);
	public List<String> getStudentIDList(ReportCardDataBean reportCardDataBean, String personID);
	public List<String> getIdList(StudentPerformanceDataBean studentPerformanceDataBean);
	public String getPerClass(StudentPerformanceDataBean studentPerformanceDataBean, String personID);
	public String insertStudentPerform(String personID, StudentPerformanceDataBean studentPerformanceDataBean);
	public List<String> getClassList1(String personID, StudentPerformanceDataBean studentPerformanceDataBean);
	public List<StudentPerformanceDataBean> getStudentPerInfo(String personID,StudentPerformanceDataBean studentPerformanceDataBean);
	public List<StudentPerformance> getPerformView(StudentPerformanceDataBean studentPerformanceDataBean);
	public String getperformDelete(String personID, StudentPerformanceDataBean studentPerformanceDataBean);
	public List<StudentPerformance> getPerformDetails(StudentPerformanceDataBean studentPerformanceDataBean);
	public String getPerformEdit(String personID, StudentPerformanceDataBean studentPerformanceDataBean);
	public List<String> getPerClassList1(String personID, StudentPerformanceDataBean studentPerformanceDataBean);
	public List<ReportCardDataBean> getRepoartCard(String personID, ReportCardDataBean reportCardDataBean);
	public List<String> getClassList(String personID);
	public String subjectValues(TimeTableDataBean timeTableDataBean, String personID);
	public String changeSubjectCode(String personID, TimeTableDataBean timeTableDataBean);
	public String timeTableInsert(String personID, TimeTableDataBean timeTableDataBean, List<TimeTableDataBean> timesBean);
	public String examCheck(TimeTableDataBean timeTableDataBean, String personID);
	public String timeTableView(TimeTableDataBean timeTableDataBean, String personID);
	public List<String> getexamList(String personID, TimeTableDataBean timeTableDataBean);
	public String examRecords(TimeTableDataBean timeTableDataBean, String personID);
	public String getexamRecordsList(String personID, TimeTableDataBean timeTableDataBean);
	public String getexamEditList(String personID, TimeTableDataBean timeTableDataBean);
	public String getexamupdate(String personID, TimeTableDataBean timeTableDataBean);
	public String insertLibrary(String personID, LibraryDataBean libraryDataBean);
	public List<LibraryDataBean> getBookListView(String personID, String name);
	public List<LibraryDataBean> getBookDetailsView(String personID, LibraryDataBean libraryDataBean);
	public String updateLibrary(String personID, LibraryDataBean libraryDataBean);
	public String deleteBook(String personID, LibraryDataBean libraryDataBean);
	public String insertBookBorrowed(String personID, LibraryDataBean libraryDataBean);
	public List<String> getClassListAtt(String personID);
	public String studentList(String personID, AttendanceDataBean attendanceDataBean);
	public String attendanceStatus(String personID, AttendanceDataBean attendanceDataBean);
	public List<String> ClassListAttView(String personID, AttendanceDataBean attendanceDataBean);
	public String attendanceView(String personID, AttendanceDataBean attendanceDataBean);
	public String studentAttView(String personID, AttendanceDataBean attendanceDataBean);
	public List<String> parentAttlist(String personID, AttendanceDataBean attendanceDataBean);
	public String parentstuAttView(String personID, AttendanceDataBean attendanceDataBean);
	public List<String> getBorrowedBook(String personID, String categoryname, String libStudID);
	public List<LibraryDataBean> getBoorowerBookView(String personID, String name);
	public List<String> getExamTitleName(String personID, String classname);
	public List<String> getStudentIDList1(String name, String personID);
	public String getRoll(String personID);
	public String insertAddBook(String personID, BookSaleDataBean bookSaleDataBean);
	public List<BookSaleDataBean> getBookInfo(String personID, BookSaleDataBean bookSaleDataBean);
	public String addBookOrder(String personID, BookSaleDataBean bookSaleDataBean);
	public List<BookSaleDataBean> getBookViewInfo(String personID, BookSaleDataBean bookSaleDataBean);
	public List<BookSalesRecord> getBookDetailInfo(String personID, BookSaleDataBean bookSaleDataBean);
	public String getBookDelete(String personID, BookSaleDataBean bookSaleDataBean);
	public String getBookUpdate(String personID, BookSaleDataBean bookSaleDataBean);
	public String getRollNumber(String personID);
	public List<BookSaleDataBean> getBookInfo1(String personID, BookSaleDataBean bookSaleDataBean);
	public String attendanceChart(String personID, AttendanceDataBean attendanceDataBean);
	public List<String> getexamViewList(String personID, TimeTableDataBean timeTableDataBean);
	public List<String> getstudentList(String personID, TimeTableDataBean timeTableDataBean);
	public String getParentRollNumber(String personID, LoginAccess loginAccess);
	public List<ExamTable> getTimeTableList(String personID);
	public List<TimeTable> getExamTableList(String personID, int examID);
	public List<School> getAdminDetails(String personID, LoginAccess loginaccess);
	public String getStudClsSection(String rollnumber);
	public List<String> getTeaClass(String rollnumber, StudentDataBean studentDataBean);
	public String getmailsID(StudentPerformanceDataBean studentPerformanceDataBean);
	public List<ParentsDataBean> getImagePath2(String personID, String parParentID);
	public List<NoticeBoardDataBean> getAllUserList(String personID, NoticeBoardDataBean noticeBoardDataBean);
	public String editReportCard(String personID, ReportCardDataBean reportCardDataBean);
	public String getReportBarChart(String personID, ReportCardDataBean reportCardDataBean);
	public List<String> classeslist(String personID);
	public String classTimeTable(String personID, ClassTimeTableDataBean classTimeTableDataBean);
	public List<String> subjectChange(String personID, ClassTimeTableDataBean classTimeTableDataBean);
	public String saveClassTimeTable(ClassTimeTableDataBean classTimeTableDataBean, String personID);
	public String classTimeTableView(ClassTimeTableDataBean classTimeTableDataBean, String personID);
	public String updateClassTimeTable(ClassTimeTableDataBean classTimeTableDataBean, String personID);
	public String deleteClassTimeTable(ClassTimeTableDataBean classTimeTableDataBean, String personID);
	public String teacherTimeTable(String personID, ClassTimeTableDataBean classTimeTableDataBean);
	public String classPeriod(AttendanceDataBean attendanceDataBean, String personID);
	public String updateAttendance(AttendanceDataBean attendanceDataBean, String personID);
	public String attendanceChartStudent(String personID, AttendanceDataBean attendanceDataBean);
	public String attendanceChartStudentYear(String personID, AttendanceDataBean attendanceDataBean);
	public String classStudent(AttendanceDataBean attendanceDataBean, String personID);
	public String leaveRequest(AttendanceDataBean attendanceDataBean, String personID);
	public String leaveRequsetView(AttendanceDataBean attendanceDataBean, String personID);
	public String leaveRequsetApproval(AttendanceDataBean attendanceDataBean, String personID);
	public List<ClassTimeTableDataBean> getclassTimeTable(String personID);
	public List<StaffDataBean> getImagePath3(String personID, String staStaffID);
	public String staffidcheck(StaffDataBean staffDataBean, String staStaffID);
	public List<StaffDataBean> staffinfomation(String personID, StaffDataBean staffDataBean);
	public List<StaffDataBean> tagstaffview(String personID, StaffDataBean staffDataBean);
	public String insertStaff(String personID, StaffDataBean staffDataBean);
	public String updateStaff(String personID, StaffDataBean staffDataBean);
	public String deleteStaff(String personID, StaffDataBean staffDataBean);
	public String forgot(LoginAccess loginAccess);
	public String otpvarify(String username, String username2);
	public String setNewpassword(String newPassword, String username);
	public String checkLibrarianID(LibrarianDataBean librarianDataBean, String libID);
	public String insertLibrarian(String personID, LibrarianDataBean librarianDataBean);
	public List<LibrarianDataBean> getImagePath5(String personID, String libID);
	public List<LibrarianDataBean> getAllLibrarianInfo(String personID);
	public String getLibrarianInfo(String personID, String libID, LibrarianDataBean librarianDataBean);
	public String deleteLibrarian(String personID, LibrarianDataBean librarianDataBean);
	public String getStaffRollNumber(String personID, LoginAccess loginaccess);
	public String libUpdate(LibrarianDataBean librarianDataBean, String personID);
	public int getTotalTeacher(String personID);
	public int getTotalParent(String personID);
	public String totalStudent(LoginAccess loginaccess);
	public List<String> classSubjectList(HomeworkDatabean homeworkDatabean, String personID);
	public List<String> classNamesList(String personID, String status);
	public String checkHomeWork(HomeworkDatabean homeworkDatabean, String personID);
	public String homeWorkInsert(HomeworkDatabean homeworkDatabean, String personID);
	public String classChange(HomeworkDatabean homeworkDatabean, String personID);
	public String updateHomeWork(HomeworkDatabean homeworkDatabean, String personID);
	public String deleteHomeWork(HomeworkDatabean homeworkDatabean, String personID);
	public String classChange(String personID, ClassTimeTableDataBean classTimeTableDataBean);
	public String checkExtraClass(String personID, ClassTimeTableDataBean classTimeTableDataBean);
	public String insertExtraClass(String personID, ClassTimeTableDataBean classTimeTableDataBean);
	public String timeTableICheck(String personID, TimeTableDataBean timeTableDataBean);	
	public String leaveRequsetReject(AttendanceDataBean attendanceDataBean, String personID);	
	public List<HomeworkDatabean> getHomework(String personID);
	public List<String> ClassListbooksale(String personID,BookSaleDataBean bookSaleDataBean);
	public List<String> getstudentRollNumber(String personID, String classname);
	public List<LibraryDataBean> getBookListView1(String personID, String categoryname);
	public List<String> getSubjectListTeacher(String personID, String classname);
	public List<String> getRollNumber1(String personID);
	public List<StudentDataBean> getStudentDetails(String stuRollNo, StudentDataBean studentDataBean);
	public String insertStaff2(String personID, StaffDataBean staffDataBean);
	public List<StaffDataBean> getImagePathstaff3(String personID,String staStaffID);
	public String getPerClassList1(String personID, PaymentDatabean paymentDatabean);
	public String getPerClass(PaymentDatabean paymentDatabean);
	public String insertfees(String personID, PaymentDatabean paymentDatabean);
	public List<PaymentDatabean> feesinfomation(String personID, PaymentDatabean paymentDatabean);
	public List<String> classStudent(String personID, String string);
	public String classStudentFeesView(String personID, PaymentDatabean paymentDatabean);
	public String feespay(String personID, PaymentDatabean paymentDatabean);
	public String paymentview(String personID, PaymentDatabean paymentDatabean);
	public String updatePayment(String personID, PaymentDatabean paymentDatabean);
	public String deletePayment(String personID, PaymentDatabean paymentDatabean);
	public String approveRejectFees(String personID, PaymentDatabean paymentDatabean);
	public List<BookSaleDataBean> getBookView(String personID,BookSaleDataBean bookSaleDataBean);
	public String deletebooksale(String personID,BookSaleDataBean bookSaleDataBean);
	public String bookOrderUpdate(String personID,BookSaleDataBean bookSaleDataBean);
	public String bookpaymentupload(String personID, BookSaleDataBean bookSaleDataBean);
	public String bookpayemtreject(String personID, BookSaleDataBean bookSaleDataBean);
	public String bookpayemtapprove(String personID, BookSaleDataBean bookSaleDataBean);
	public String inserschool(LoginAccess loginaccess);
	public List<LoginAccess> getSchoolsList(LoginAccess loginaccess);
	public String newschoolapproval(LoginAccess login);
	public String newschoolreject(LoginAccess login);
	public List<School> schooledit(LoginAccess login);
	public String schoolupdate(LoginAccess loginaccess);
	public String getRegisteredCustomer(RegisteredLogin registeredLogin);
	public String getOtpCustomer(RegisteredLogin registeredLogin);
	public String getOtpResent(RegisteredLogin registeredLogin);
	public List<ClassTimeTableDataBean> getstudentclassTimeTable(String personID, StudentDataBean studentDataBean);
	public List<LibraryDataBean> getBoorowerBookView1(String personID);
	public List<LibraryDataBean> getreturnBookView1(String personID);
	public List<LibraryDataBean> getborrowerDetails(String personID, LibraryDataBean libraryDataBean);
	public String toReturnBook(String personID, LibraryDataBean libraryDataBean);
	public String insertclass(String personID, String schoolID,LibrarianDataBean librarianDataBean);
	public String insertsubject(String personID, String schoolID,LibrarianDataBean librarianDataBean);
	public List<String> getmenus();	
	public String takeattendance(String personID,AttendanceDataBean attendanceDataBean);	
	public String getperstudentList(StudentPerformanceDataBean studentPerformanceDataBean,String personID);
	public String bookstockin(BookSaleDataBean bookSaleDataBean, String personID);	
	public List<Library> authernamelist(String authername, String personID);
	public List<Library> booknamelist(String personID);	
	public String stockinbookQuentity(LibraryDataBean libraryDataBean,String personID);	
	public List<String> studentidlist(String personID);
	public List<LibraryDataBean> stuinfo(String personID, String sturollno);
	public String borrowrinsertbook(String personID,LibraryDataBean libraryDataBean);	
	public String getNotReturnedBooks(String personID, LoginAccess loginaccess);	
	public String validatecalss(LibrarianDataBean librarianDataBean, String schoolID);
	public String validatesubject(LibrarianDataBean librarianDataBean, String schoolID);	
	public List<LibrarianDataBean> classlistview(String personID,LibrarianDataBean librarianDataBean);
	public String editclasssec(String personID,LibrarianDataBean librarianDataBean);
	public List<LibrarianDataBean> subjectlistview(String personID,LibrarianDataBean librarianDataBean);
	public String updatesujectname(String personID,LibrarianDataBean librarianDataBean);
	public String deleteclasssec(String personID,LibrarianDataBean librarianDataBean);	
	public String deletesubname(String personID,LibrarianDataBean librarianDataBean);	
	public List<TimeTableDataBean> getexamMarksList(String personID,TimeTableDataBean timeTableDataBean);
	public String getexammarksupdate(String personID,TimeTableDataBean timeTableDataBean);	
	public String getexammarkgrade(ReportCardDataBean reportCardDataBean);	
	public String feessave(String personID, PaymentDatabean paymentDatabean);	
	public String checkupdateClassTimeTable(ClassTimeTableDataBean classTimeTableDataBean, String personID);
	public String classTimeTableDelete(ClassTimeTableDataBean classTimeTableDataBean, String personID);	
	public String checkExtraPeriod(String personID,ClassTimeTableDataBean classTimeTableDataBean);
	public String insertExtraPeriod(String personID,ClassTimeTableDataBean classTimeTableDataBean);	
	public List<AttendanceDataBean> attendanceReport(String personID,AttendanceDataBean attendanceDataBean);	
	public List<ReportCardDataBean> getmarks(String personID,ReportCardDataBean reportCardDataBean);	
	public List<StudentPerformanceDataBean> getStudentperformance(String personID,StudentPerformanceDataBean studentPerformanceDataBean);	
	public List<String> subjectCodeChange(String personID,ClassTimeTableDataBean classTimeTableDataBean);
	public List<String> getcountryList(String valuechange);
	public List<LoginAccess> searchvalues(String schoolID);
	public List<TeacherDataBean> getNotesDetail(TeacherDataBean teacherDataBean);
	public String updateNotes(TeacherDataBean teacherDataBean);
	public List<TeacherDataBean> getAttendentsDetail(TeacherDataBean teacherDataBean);
	public String saveAttendents(TeacherDataBean teacherDataBean);
	public String saveNotes(TeacherDataBean teacherDataBean);
	public String deleteNotes(TeacherDataBean teacherDataBean);
	public List<String> getActivitieslist(StudentDataBean studentDataBean);
	public List<StudentDataBean> getActivity(StudentDataBean studentDataBean);
	public String saveActivity(StudentDataBean studentDataBean);
	public String updateActivity(StudentDataBean studentDataBean);
	public String saveAddActivity(StudentDataBean studentDataBean);
	public String deleteActivity(StudentDataBean studentDataBean);
	public List<AttendanceDataBean> attendanceSearch(String personID,AttendanceDataBean attendanceDataBean);
	public String updateAttendanceGlobal(AttendanceDataBean attendanceDataBean,String personID);
	public List<StudentDataBean> getstudentbehaviourList(StudentDataBean studentDataBean);
	public List<StudentDataBean> getFeesList(StudentDataBean studentDataBean);
	public String updatefees(StudentDataBean studentDataBean);
	public String deletefeesDetails(StudentDataBean studentDataBean);
	public String payfees(StudentDataBean studentDataBean);	
	public List<ReportCardDataBean> getglobalsearchMarkList(String schoolID,String personID, ReportCardDataBean reportCardDataBean);
	public String performanceCheck(StudentPerformanceDataBean studentPerformanceDataBean);
	public String getAttendanceList(AttendanceDataBean attendanceDataBean);
	public String updateAttendance(AttendanceDataBean attendanceDataBean);
	public List<ExamTable> getglobalSearchTimeTableList(String schoolID,String personID, String className, String month);
	public String getexammarkgradeGlobal(ReportCardDataBean reportCardDataBean);
	public String addReportCardGlobal(String personID, ReportCardDataBean reportCardDataBean);
	public List<LoginAccess> getUserDetails(LoginAccess loginaccess);
	public String insertformdetails(LoginAccess loginaccess);
	public List<StudentDataBean> getStudentCommunitryDetail(String communitySearchType,StudentDataBean studentDataBean); 
	public List<String> getTeachername(String schoolID);
	public List<String> getStudentname(String schoolID);
	public void getStudentdata(String personID, StaffDataBean staffDataBean);
	public String emisnoDetails(StaffDataBean staffDataBean);
	public String saveTC(StaffDataBean staffDataBean);
	public String changePassword(LoginAccess loginaccess);
	public List<String> getTeaClasslist(String personID);
	public void takeattendanceNew(String personID, AttendanceDataBean attendanceDataBean);
	public void attendanceStatusNew(String personID, AttendanceDataBean attendanceDataBean);
	public String insertTermfees(StudentDataBean studentDataBean);
	public String insertAttandance(String personID, String schoolID, AttendanceDataBean attendanceDataBean);
	public String examDelete(TimeTableDataBean timeTable);
	public String gradeDelete(TimeTableDataBean timeTable); 

	public String saveReport(ReportDataBean reportDataBean);
	public List<ReportDataBean> getReportmenulist(ReportDataBean reportDataBean);
	public List<ReportDataBean> getFilterlist(ReportDataBean reportDataBean);
	public void classNamebasedData(ReportDataBean reportDataBean);
	public List<AttendanceDataBean> getAttendanceReport(ReportDataBean reportDataBean);
	public void secNamebasedData(ReportDataBean reportDataBean);
	public String deleteMenu(ReportDataBean reportDataBean);
	public List<StudentPerformanceDataBean> getBehaviourReport(ReportDataBean reportDataBean);
	public String updateMenuEdit(ReportDataBean reportDataBean);
	public List<String> getExamListforMark(ReportDataBean reportDataBean);
	public List<ReportCardDataBean> getReportCardReport(ReportDataBean reportDataBean);

	public List<String> teacherNameList(ReportDataBean reportDataBean);
	public List<ReportDataBean> getTeacherAttendanceReport(ReportDataBean reportDataBean);
	public List<ReportDataBean> getTeacherTimetableReport(ReportDataBean reportDataBean);
	public List<String> subjectNameList(ReportDataBean reportDataBean); 
	public String getClassEdit(StudentDataBean studentDataBean,String personID);
	public String insertAttandance1(String personID, String schoolID, ArrayList<AttendanceDataBean> list);




}
