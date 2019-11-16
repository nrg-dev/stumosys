package com.stumosys.managedBean;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
//import java.util.Map;
//import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/*import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;*/
//import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.imageio.ImageIO;
//import javax.mail.BodyPart;
/*import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;*/

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
//import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.stumosys.controler.SmsController;
import com.stumosys.domain.AttendanceDataBean;
import com.stumosys.domain.ReportCardDataBean;
import com.stumosys.domain.StudentDataBean;
import com.stumosys.domain.StudentPerformanceDataBean;
import com.stumosys.shared.ExamTable;
import com.stumosys.shared.StudentDetail;
import com.stumosys.shared.StudentPerformance;
import com.stumosys.shared.TimeTable;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;
//import com.stumosys.util.Validation;
import java.util.Calendar;


@ManagedBean(name = "studentViewMB")
@RequestScoped
public class StudentViewMB {
	StudentPerformanceViewMB studentPerformanceViewMB=new StudentPerformanceViewMB();
	StudentPerformanceDataBean studentPerformanceDataBean=new StudentPerformanceDataBean();
	StudentDataBean studentDataBean = new StudentDataBean();
	AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
	Date d=new Date();
	List<StudentDataBean> studviewlist = null;
	List<StudentDataBean> filteredStudent;
	List<StudentDetail> viewList = null;
	List<StudentDetail> editList = null;
	private List<StudentDataBean> ImageListPath = null;
	String deleteList = null;
	private boolean valid = false;
	private boolean flag = false;
	private String status;
	List<StudentDataBean> studentList = null;
	private boolean boxflag = false;
	private boolean delBoxflag = false;
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	private List<String> stateLists = null;
	public String zipList = null;
	public String zipList1 = null;
	private List<String> sclass1 = null;
	List<String> classlist = null;
	private String sclass2 = null;
	UploadedFile file;
	public String myName1;
	public String reason;
	public String myName2;
	private boolean sflag = false;
	private boolean classflag = false;
	private boolean flag1 = false;
	private boolean flag2 = false;
	List<String> classSectionList = null;
	private List<String> teaClass = null;
	public String code1;
	public List<String> statelist=null;
	public List<String> statelist1=null;
	private boolean chkBox;
	private boolean permenantAddFlag=true;
	private boolean permenantAddFlag1=false; 
	private static Logger logger = Logger.getLogger(StudentViewMB.class);
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	List<StudentPerformance> editList1 = null;
	private List<String> sRoll = null;
	private boolean check;
	private boolean check1;
	private boolean boxflag1 = false;
	private List<AttendanceDataBean> filteredstudent;
	List<StudentDataBean> selectedstudentlist = null;
	private boolean oldFlag=false;
	private boolean newFlag=false;
	private List<String> numberList=null;
	private List<String> ifscnoList=null;
	private List<String> branchcodeList=null;
	private List<String> aadharnoList=null;
	private List<String> emisnoList=null;

	public List<String> getNumberList() {
		return numberList;
	}

	public void setNumberList(List<String> numberList) {
		this.numberList = numberList;
	}

	public List<String> getIfscnoList() {
		return ifscnoList;
	}

	public void setIfscnoList(List<String> ifscnoList) {
		this.ifscnoList = ifscnoList;
	}

	public List<String> getBranchcodeList() {
		return branchcodeList;
	}

	public void setBranchcodeList(List<String> branchcodeList) {
		this.branchcodeList = branchcodeList;
	}

	public List<String> getAadharnoList() {
		return aadharnoList;
	}

	public void setAadharnoList(List<String> aadharnoList) {
		this.aadharnoList = aadharnoList;
	}

	public List<String> getEmisnoList() {
		return emisnoList;
	}

	public void setEmisnoList(List<String> emisnoList) {
		this.emisnoList = emisnoList;
	}

	public boolean isOldFlag() {
		return oldFlag;
	}

	public void setOldFlag(boolean oldFlag) {
		this.oldFlag = oldFlag;
	}

	public boolean isNewFlag() {
		return newFlag;
	}

	public void setNewFlag(boolean newFlag) {
		this.newFlag = newFlag;
	}

	public List<StudentDataBean> getSelectedstudentlist() {
		return selectedstudentlist;
	}

	public void setSelectedstudentlist(List<StudentDataBean> selectedstudentlist) {
		this.selectedstudentlist = selectedstudentlist;
	}
	
	public List<AttendanceDataBean> getFilteredstudent() {
		return filteredstudent;
	}

	public void setFilteredstudent(List<AttendanceDataBean> filteredstudent) {
		this.filteredstudent = filteredstudent;
	}

	public AttendanceDataBean getAttendanceDataBean() {
		return attendanceDataBean;
	}

	public void setAttendanceDataBean(AttendanceDataBean attendanceDataBean) {
		this.attendanceDataBean = attendanceDataBean;
	}

	public boolean isBoxflag1() {
		return boxflag1;
	}

	public void setBoxflag1(boolean boxflag1) {
		this.boxflag1 = boxflag1;
	}

	public List<StudentPerformance> getEditList1() {
		return editList1;
	}

	public void setEditList1(List<StudentPerformance> editList1) {
		this.editList1 = editList1;
	}

	public List<String> getsRoll() {
		return sRoll;
	}

	public void setsRoll(List<String> sRoll) {
		this.sRoll = sRoll;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public boolean isCheck1() {
		return check1;
	}

	public void setCheck1(boolean check1) {
		this.check1 = check1;
	}

	public StudentPerformanceDataBean getStudentPerformanceDataBean() {
		return studentPerformanceDataBean;
	}

	public void setStudentPerformanceDataBean(StudentPerformanceDataBean studentPerformanceDataBean) {
		this.studentPerformanceDataBean = studentPerformanceDataBean;
	}

	public StudentPerformanceViewMB getStudentPerformanceViewMB() {
		return studentPerformanceViewMB;
	}

	public void setStudentPerformanceViewMB(StudentPerformanceViewMB studentPerformanceViewMB) {
		this.studentPerformanceViewMB = studentPerformanceViewMB;
	}

	public boolean isChkBox() {
		return chkBox;
	}

	public void setChkBox(boolean chkBox) {
		this.chkBox = chkBox;
	}

	public List<String> getStatelist() {
		return statelist;
	}

	public void setStatelist(List<String> statelist) {
		this.statelist = statelist;
	}

	public List<String> getStatelist1() {
		return statelist1;
	}

	public void setStatelist1(List<String> statelist1) {
		this.statelist1 = statelist1;
	}

	public boolean isPermenantAddFlag() {
		return permenantAddFlag;
	}

	public void setPermenantAddFlag(boolean permenantAddFlag) {
		this.permenantAddFlag = permenantAddFlag;
	}

	public boolean isPermenantAddFlag1() {
		return permenantAddFlag1;
	}

	public void setPermenantAddFlag1(boolean permenantAddFlag1) {
		this.permenantAddFlag1 = permenantAddFlag1;
	}

	public String getCode1() {
		return code1;
	}

	public void setCode1(String code1) {
		this.code1 = code1;
	}

	public boolean isClassflag() {
		return classflag;
	}

	public void setClassflag(boolean classflag) {
		this.classflag = classflag;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<StudentDataBean> getImageListPath() {
		return ImageListPath;
	}

	public void setImageListPath(List<StudentDataBean> imageListPath) {
		ImageListPath = imageListPath;
	}

	public List<StudentDataBean> getFilteredStudent() {
		return filteredStudent;
	}

	public void setFilteredStudent(List<StudentDataBean> filteredStudent) {
		this.filteredStudent = filteredStudent;
	}

	public boolean isFlag2() {
		return flag2;
	}

	public void setFlag2(boolean flag2) {
		this.flag2 = flag2;
	}

	public List<String> getTeaClass() {
		return teaClass;
	}

	public void setTeaClass(List<String> teaClass) {
		this.teaClass = teaClass;
	}

	public List<String> getClassSectionList() {
		return classSectionList;
	}

	public void setClassSectionList(List<String> classSectionList) {
		this.classSectionList = classSectionList;
	}

	public boolean isFlag1() {
		return flag1;
	}

	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}

	public String getMyName1() {
		return myName1;
	}

	public void setMyName1(String myName1) {
		this.myName1 = myName1;
	}

	public String getMyName2() {
		return myName2;
	}

	public void setMyName2(String myName2) {
		this.myName2 = myName2;
	}

	public boolean isSflag() {
		return sflag;
	}

	public void setSflag(boolean sflag) {
		this.sflag = sflag;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String code;
	Date now = new Date();

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public SimpleDateFormat getFt() {
		return ft;
	}

	public void setFt(SimpleDateFormat ft) {
		this.ft = ft;
	}

	public List<String> getStateLists() {
		return stateLists;
	}

	public void setStateLists(List<String> stateLists) {
		this.stateLists = stateLists;
	}

	public String getZipList() {
		return zipList;
	}

	public void setZipList(String zipList) {
		this.zipList = zipList;
	}

	public String getZipList1() {
		return zipList1;
	}

	public void setZipList1(String zipList1) {
		this.zipList1 = zipList1;
	}

	public List<String> getSclass1() {
		return sclass1;
	}

	public void setSclass1(List<String> sclass1) {
		this.sclass1 = sclass1;
	}

	public List<String> getClasslist() {
		return classlist;
	}

	public void setClasslist(List<String> classlist) {
		this.classlist = classlist;
	}

	public String getSclass2() {
		return sclass2;
	}

	public void setSclass2(String sclass2) {
		this.sclass2 = sclass2;
	}

	public boolean isBoxflag() {
		return boxflag;
	}

	public void setBoxflag(boolean boxflag) {
		this.boxflag = boxflag;
	}

	public boolean isDelBoxflag() {
		return delBoxflag;
	}

	public void setDelBoxflag(boolean delBoxflag) {
		this.delBoxflag = delBoxflag;
	}

	public List<StudentDataBean> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<StudentDataBean> studentList) {
		this.studentList = studentList;
	}

	public List<StudentDetail> getViewList() {
		return viewList;
	}

	public void setViewList(List<StudentDetail> viewList) {
		this.viewList = viewList;
	}

	public List<StudentDetail> getEditList() {
		return editList;
	}

	public void setEditList(List<StudentDetail> editList) {
		this.editList = editList;
	}

	public String getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(String deleteList) {
		this.deleteList = deleteList;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	/**
	 * @return the studentDataBean
	 */
	public StudentDataBean getStudentDataBean() {
		return studentDataBean;
	}

	public List<StudentDataBean> getStudviewlist() {
		return studviewlist;
	}

	public void setStudviewlist(List<StudentDataBean> studviewlist) {
		this.studviewlist = studviewlist;
	}

	/**
	 * @param studentDataBean
	 *            the studentDataBean to set
	 */
	public void setStudentDataBean(StudentDataBean studentDataBean) {
		this.studentDataBean = studentDataBean;
	}

	public String studentInfo() {
		logger.debug("------studentinfo method calling--------");
		SmsController controller = null;
		setDelBoxflag(false);
		setBoxflag(false);
		setFlag2(false);
		String rollnumber = "";
		studentDataBean.setTeaclssection(null);
		studentDataBean.setTeaClassList(null);
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				status = controller.getRoll(personID);
				logger.debug("-----status----" + status);
				if (status.equalsIgnoreCase("Admin")) {
					setFlag1(true);
					setSflag(false);
					setFlag2(false);
					teaClass = controller.getTeaClass(personID, studentDataBean);
					Collections.sort(teaClass);
					logger.debug("class -- " + teaClass);
				} else if (status.equalsIgnoreCase("Teacher")) {

					setSflag(false);
					setFlag1(true);
					teaClass = new ArrayList<String>();
					//rollnumber = controller.getRollNumber(personID);
					classSectionList = controller.getTeaClass(personID, studentDataBean);
					for (int i = 0; i < studentDataBean.getTeaClassList().size(); i++) {
						teaClass.add(studentDataBean.getTeaClassList().get(i).getTeaclssection());
					}

				} else if (status.equalsIgnoreCase("Student") || status.equalsIgnoreCase("Parent")) {
					setFlag1(false);
					setSflag(false);
					setFlag2(true);
					rollnumber = controller.getRollNumber(personID);
					studentDataBean.setTestroll(rollnumber);
					studentList = controller.getStudentInfo(personID, studentDataBean);

				}
				studentDataBean.setReturnStatus(status);
			}
		} catch (Exception e) {
			logger.warn(e);
		}
		return "studentview";
	}

	public String returnToHome() {
		SmsController controller = null;
		setBoxflag(false);
		setDelBoxflag(false);
		logger.debug("--------returnToHome-----");
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				studentList = new ArrayList<StudentDataBean>();
				studentList = controller.getStudentInfo(personID, studentDataBean);
				setSflag(true);
				setBoxflag(false);
				setDelBoxflag(false);
				// return "studentPerformInfo";
				return "StudentviewPageLoad";
			}
		} catch (Exception e) {
			logger.warn(e);
		}
		return "";

	}

	/*
	 * @PostConstruct public void init(){ logger.debug(
	 * "-------------- Inside init method calling ---------------------");
	 * SmsController controller = null; try{ String personID=(String)
	 * FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
	 * get("LogID"); if(personID !=null){ ApplicationContext ctx =
	 * FacesContextUtils.getWebApplicationContext(FacesContext.
	 * getCurrentInstance()); controller = (SmsController)
	 * ctx.getBean("controller"); studentList=new ArrayList<StudentDataBean>();
	 * studentList=controller.getAllStudentInfo(personID);
	 * 
	 * } }catch(Exception e){ logger.warn("Exception -->"+e.getMessage()); } }
	 */
	public void imageview(OutputStream out, Object data) throws IOException {
		logger.debug("-----image view--------");
		setBoxflag(false);
		setDelBoxflag(false);
		String s = paths.getString("sms.student.photo");
		logger.debug("path mb -- " + studentDataBean.getImagePath1());
		BufferedImage img = ImageIO
				.read(new File(s + ft.format(studentDataBean.getImageDate()) + "/" + studentDataBean.getImagePath1()));
		ImageIO.write(img, "png", out);
	}

	public String studentView() {
		logger.debug("-----inside student view--------");
		OutputStream out = null;
		Object data = null;
		SmsController controller = null;
		studentDataBean.setImagePath1("");
		setBoxflag(false);
		setDelBoxflag(false);
		String classname, rollnumber;
		if (!studentDataBean.getStuRollNo().equalsIgnoreCase("")) {
			try {
				setFlag(false);
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
				int schoolid=Integer.parseInt(schoolID);
				if (personID != null) {
					viewList = controller.getStudentView(studentDataBean,personID);
					classname = controller.getStudentClass(studentDataBean.getStuRollNo(),personID);
					rollnumber = studentDataBean.getStuRollNo();
					logger.debug("-----viewlist size-----" + viewList.size());
					if (viewList.size() > 0) {
						studentDataBean.setNames(viewList.get(0).getFirstName() + " " + viewList.get(0).getLastName());
						if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6)
						{
							flags=false;
						}else{
							studentDataBean.setStuEmail(viewList.get(0).getEmailId());
							studentDataBean.setStuPhoneNo(viewList.get(0).getPhoneNumber());
							flags=true;
						}
						
						studentDataBean.setStuCls(classname);
						studentDataBean.setStuRollNo(rollnumber);
						studentDataBean.setStuLastName(viewList.get(0).getLastName());
						studentDataBean.setStuFatherName(viewList.get(0).getFatherName());
						studentDataBean.setStuMotherName(viewList.get(0).getMotherName());
						studentDataBean.setStuFatherIncome(viewList.get(0).getFatherIncome());
						studentDataBean.setStuFatherOccu(viewList.get(0).getFatherOccupation());
						studentDataBean.setStuMotherOccu(viewList.get(0).getMotherOccupation());
						studentDataBean.setStuDob(viewList.get(0).getDob());
						studentDataBean.setStuGender(viewList.get(0).getGender());
						studentDataBean.setStuPresentAddress(viewList.get(0).getAddress1_Street());
						studentDataBean.setStuPermanentAddress(viewList.get(0).getAddress2_Street());
						//studentDataBean.setStuPresentZip(viewList.get(0).getPostcode1().getPostcode());
						//studentDataBean.setStuPresentState(viewList.get(0).getPostcode1().getState().getStateName());
						studentDataBean.setPresentAdd(viewList.get(0).getAddress1_Street());
						studentDataBean.setStufilePath(studentDataBean.getImagePath1());
						studentDataBean.setPath(studentDataBean.getImagePath1());
						studentDataBean.setPathDate(studentDataBean.getImageDate());
						imageview(out, data);
					}
				}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
		}
		return "";
	}
	
	public String studentViewForm() {
		logger.debug("-----inside student view--------");
		OutputStream out = null;
		Object data = null;
		SmsController controller = null;
		studentDataBean.setImagePath1("");
		setBoxflag(false);
		setDelBoxflag(false);
		String classname, rollnumber;
		if (!studentDataBean.getStuRollNo().equalsIgnoreCase("")) {
			try {
				setFlag(false);
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
				int schoolid=Integer.parseInt(schoolID);
				if (personID != null) {
					viewList = controller.getStudentView(studentDataBean,personID);
					classname = controller.getStudentClass(studentDataBean.getStuRollNo(),personID);
					rollnumber = studentDataBean.getStuRollNo();
					logger.debug("-----viewlist size-----" + viewList.size());
					if (viewList.size() > 0) {
						studentDataBean.setNames(viewList.get(0).getFirstName() + " " + viewList.get(0).getLastName());
						if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6)
						{
							flags=false;
						}else{
							studentDataBean.setStuEmail(viewList.get(0).getEmailId());
							studentDataBean.setStuPhoneNo(viewList.get(0).getPhoneNumber());
							flags=true;
						}
						
						studentDataBean.setStuCls(classname);
						studentDataBean.setStuRollNo(rollnumber);
						studentDataBean.setStuFirstName(viewList.get(0).getFirstName());
						studentDataBean.setStuLastName(viewList.get(0).getLastName());
						studentDataBean.setStuFatherName(viewList.get(0).getFatherName());
						studentDataBean.setStuMotherName(viewList.get(0).getMotherName());
						studentDataBean.setStuFatherIncome(viewList.get(0).getFatherIncome());
						studentDataBean.setStuFatherOccu(viewList.get(0).getFatherOccupation());
						studentDataBean.setStuMotherOccu(viewList.get(0).getMotherOccupation());
						studentDataBean.setStuDob(viewList.get(0).getDob());
						studentDataBean.setStuGender(viewList.get(0).getGender());
						studentDataBean.setStuPresentAddress(viewList.get(0).getAddress1_Street());
						studentDataBean.setStuPermanentAddress(viewList.get(0).getAddress2_Street());
						//studentDataBean.setStuPresentZip(viewList.get(0).getPostcode1().getPostcode());
						//studentDataBean.setStuPresentState(viewList.get(0).getPostcode1().getState().getStateName());
						studentDataBean.setPresentAdd(viewList.get(0).getAddress1_Street());
						studentDataBean.setStufilePath(studentDataBean.getImagePath1());
						studentDataBean.setPath(studentDataBean.getImagePath1());
						studentDataBean.setPathDate(studentDataBean.getImageDate());
						studentDataBean.setStuPermanentCountry(viewList.get(0).getPermanentCountry());
						studentDataBean.setStuPresentCountry(viewList.get(0).getPresentCountry());
						studentDataBean.setStuPermanentState(viewList.get(0).getPermanentState());
						studentDataBean.setStuPresentState(viewList.get(0).getPresentState());
						studentDataBean.setStuPresentCity(viewList.get(0).getPresentCity());
						studentDataBean.setStuPermanentCity(viewList.get(0).getPermanentCity());
						studentDataBean.setStuPresentpostal(viewList.get(0).getPresentPostal());
						studentDataBean.setStuPermanentpostal(viewList.get(0).getPermanentPostal());
						studentDataBean.setStuPhoneNo1(viewList.get(0).getPhoneNumber1());
						studentDataBean.setStuPhoneNo(viewList.get(0).getPhoneNumber());
						studentDataBean.setCode(viewList.get(0).getPresentcode());
						studentDataBean.setCode1(viewList.get(0).getPermanentcode());
						imageview(out, data);
					}
				}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
		}
		return "redirectstudentview";
	}
	
	private boolean flags=false;
	public boolean isFlags() {
		return flags;
	}

	public void setFlags(boolean flags) {
		this.flags = flags;
	}
	
	@SuppressWarnings("null")
	public String studentEdit() {
		logger.debug("-----inside student Edit--------");
		SmsController controller = null;
		String personID = "";
		try {
			stateLists=new ArrayList<String>();
			valid = true;
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			logger.debug(personID);
			if ((personID != null) || (!personID.equalsIgnoreCase(""))) {
				stateLists = controller.getStateLists(personID);
			}
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			sclass1 = new ArrayList<String>();
			classlist = controller.getClassList(personID, studentDataBean);
			for (int i = 0; i < studentDataBean.getStuPickList().size(); i++) {
				sclass1.add(studentDataBean.getStuPickList().get(i).getStuCls());
			}
			editList = controller.getStudentDetails(studentDataBean,personID);
			logger.debug("---------------editList--------------- "+editList.size());
			if (editList.size() > 0) {
				for (StudentDetail st : editList) {
					studentDataBean.setStuFirstName(st.getFirstName());
					studentDataBean.setStuLastName(st.getLastName());
					studentDataBean.setStuFatherName(st.getFatherName());
					studentDataBean.setStuMotherName(st.getMotherName());
					studentDataBean.setStuDob(st.getDob());
					studentDataBean.setStuGender(st.getGender());
					studentDataBean.setStuPresentAddress(st.getAddress1_Street());
					studentDataBean.setStuPermanentAddress(st.getAddress2_Street());
					/*studentDataBean.setStuPresentState(st.getPostcode1().getState().getStateName());
					studentDataBean.setStuPresentZip(st.getPostcode1().getPostcode());
					studentDataBean.setStuPermanentState(st.getPostcode2().getState().getStateName());
					studentDataBean.setStuPermanentZip(st.getPostcode2().getPostcode());*/
					studentDataBean.setStuFatherOccu(st.getFatherOccupation());
					studentDataBean.setStuFatherIncome(st.getFatherIncome());
					studentDataBean.setStuMotherOccu(st.getMotherOccupation());
					studentDataBean.setStuCls(studentDataBean.getClassName());
					studentDataBean.setStuCls1(studentDataBean.getClassName());
					studentDataBean.setStuPermanentCountry(st.getPermanentCountry());
					studentDataBean.setStuPresentCountry(st.getPresentCountry());
					studentDataBean.setStuPermanentState(st.getPermanentState());
					studentDataBean.setStuPresentState(st.getPresentState());
					studentDataBean.setStuPresentCity(st.getPresentCity());
					studentDataBean.setStuPermanentCity(st.getPermanentCity());
					studentDataBean.setStuPresentpostal(st.getPresentPostal());
					studentDataBean.setStuPermanentpostal(st.getPermanentPostal());
					studentDataBean.setStuPhoneNo1(st.getPhoneNumber1());
					studentDataBean.setStuPhoneNo(st.getPhoneNumber());
					studentDataBean.setCode(st.getPresentcode());
					studentDataBean.setCode1(st.getPermanentcode());
					if(schoolID.equals(paths.getString("SMRV.SCHOOLID"))){
						studentDataBean.setReligion(st.getReligion());
						studentDataBean.setCaste(st.getCaste());
						studentDataBean.setClassification(st.getClassification());
						studentDataBean.setCommunityCertificateNo(st.getCommunityCertificateNo());
						studentDataBean.setIssuedAuthority(st.getIssuedAuthority());
						studentDataBean.setIssuedDate(st.getIssueddate());
						studentDataBean.setBankAccNo(st.getBankAccNo());
						studentDataBean.setBankName(st.getBankName());
						studentDataBean.setBranchName(st.getBranchName());
						studentDataBean.setIfscCode(st.getIfscCode());
						studentDataBean.setMicrNo(st.getMicrNo());
						studentDataBean.setBranchCode(st.getBranchCode());
						studentDataBean.setAadharCardNo(st.getAadharCardNo());
						studentDataBean.setRationCardNo(st.getRationCardNo());
						studentDataBean.setEmploymentCardNo(st.getEmploymentCardNo());
						studentDataBean.setEmisNo(st.getEmisNo());
						studentDataBean.setBusPassNo(st.getBusPassNo());
						numberList=new ArrayList<String>();
						ifscnoList=new ArrayList<String>();
						branchcodeList=new ArrayList<String>();
						aadharnoList=new ArrayList<String>();
						emisnoList=new ArrayList<String>();
						if(studentDataBean.getBankAccNo().equals("")|| studentDataBean.getBankAccNo()==null){
							 for (int i = 0; i < 15; i++) {
							        numberList.add(new String());
							 }
						 }else{
							 for (int i = 0; i < studentDataBean.getBankAccNo().length(); i++) {
							        numberList.add(String.valueOf(studentDataBean.getBankAccNo().charAt(i)));
							 }
						 }
						 if(studentDataBean.getIfscCode().equals("") ||  studentDataBean.getIfscCode()==null){
							 for (int i = 0; i < 11; i++) {
							        ifscnoList.add(new String());
							  }
						  }else{
							  for (int i = 0; i < studentDataBean.getIfscCode().length(); i++) {
							        ifscnoList.add(String.valueOf(studentDataBean.getIfscCode().charAt(i)));
							  }
						  }
						  if(studentDataBean.getBranchCode().equals("") || studentDataBean.getBranchCode()==null){
							  for (int i = 0; i < 9; i++) {
							        branchcodeList.add(new String());
							  }
						  }else{
							  for (int i = 0; i <studentDataBean.getBranchCode().length(); i++) {
							        branchcodeList.add(String.valueOf(studentDataBean.getBranchCode().charAt(i)));
							   }
						   }
						   if(studentDataBean.getAadharCardNo().equals("") || studentDataBean.getAadharCardNo()==null){
							   for (int i = 0; i < 14; i++) {
							        aadharnoList.add(new String());
							    }
						    }else{
						    	 for (int i = 0; i < studentDataBean.getAadharCardNo().length(); i++) {
								        aadharnoList.add(String.valueOf(studentDataBean.getAadharCardNo().charAt(i)));
								  }
						    }
						    if(studentDataBean.getEmisNo().equals("") || studentDataBean.getEmisNo()==null){
						    	 for (int i = 0; i < 16; i++) {
								        emisnoList.add(new String());
								  }
						    }else{
						    	 for (int i = 0; i < studentDataBean.getEmisNo().length(); i++) {
								        emisnoList.add(String.valueOf(studentDataBean.getEmisNo().charAt(i)));
								  }
						    }
					}
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){						
						flags=false;
					}else{
						studentDataBean.setStuEmail(st.getEmailId());
						studentDataBean.setStuPhoneNo(st.getPhoneNumber());
						flags=true;
					}					
					studentDataBean.setStufilePath(studentDataBean.getImagePath1());
					status = "studentInfopage";
					if(studentDataBean.getStufilePath()==null || studentDataBean.getStufilePath()==""){
						setOldFlag(false);
						setNewFlag(true);
					}else{
						setOldFlag(true);
						setNewFlag(false);
					}
				}
			}
			
		} catch (Exception e)

		{
			logger.warn("Exception -->"+e.getMessage());
			e.printStackTrace();
		}
		studentDataBean.setViewregFlag(true);
		return "studentInfopage";

	}
	public boolean validate(boolean flag) {
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();

		if (StringUtils.isEmpty(studentDataBean.getStuFirstName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sFName").getClientId(fc);
				//fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.ftname")));
				fc.addMessage(fieldName, new FacesMessage(text.getString("stumosys.student.ftname")));				
			}
			valid = false;
		} else if (!StringUtils.isEmpty(studentDataBean.getStuFirstName())) {
			if (studentDataBean.getStuFirstName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vftname")));
				}
				valid = false;
			} else if (!CommonValidate.validateName(studentDataBean.getStuFirstName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vftname")));
				}
				valid = false;
			}
		}
		
		if(StringUtils.isEmpty(studentDataBean.getStuPresentCountry())){
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("SPcountry").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.teacherCountry")));
			}
			valid = false;
		}
		if(!StringUtils.isEmpty(studentDataBean.getStuPresentCountry())){
			if(StringUtils.isEmpty(studentDataBean.getStuPhoneNo())) {
				/*if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPhone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumber")));
				}
				valid = false;*/
			}else if(!CommonValidate.validateNumberOnly(studentDataBean.getStuPhoneNo())){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPhone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumberinvalid")));
				}
				valid = false;
			}
			else if(!CommonValidate.countryValidateNumber(studentDataBean.getStuPhoneNo(),studentDataBean.getStuPresentCountry())){
				fieldName = CommonValidate.findComponentInRoot("TPhone").getClientId(fc);
				if (studentDataBean.getStuPresentCountry().equalsIgnoreCase("INDIA")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.india.phonevalidation")));
				}
				else if (studentDataBean.getStuPresentCountry().equalsIgnoreCase("UAE")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.uae.phonevalidation")));
				}
				else if (studentDataBean.getStuPresentCountry().equalsIgnoreCase("INDONESIA")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.indonesia.phonevalidation")));
				}
				else if (studentDataBean.getStuPresentCountry().equalsIgnoreCase("MALAYSIA")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.malaysia.phonevalidation")));
				}
				
				valid = false;
			}
		}
		
		
		/*if (StringUtils.isEmpty(studentDataBean.getStuLastName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sLName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.lname")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(studentDataBean.getStuLastName())) {
			if (studentDataBean.getStuLastName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sLName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vlname")));
				}
				valid = false;
			} else if (!CommonValidate.validateName(studentDataBean.getStuLastName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sLName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vlname")));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(studentDataBean.getStuFatherName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sFaName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.fname")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(studentDataBean.getStuFatherName())) {
			if (studentDataBean.getStuFatherName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sFaName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vfname")));
				}
				valid = false;
			} else if (!CommonValidate.validateName(studentDataBean.getStuFatherName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sFaName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vfname")));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(studentDataBean.getStuMotherName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sMoName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.mname")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(studentDataBean.getStuMotherName())) {
			if (studentDataBean.getStuMotherName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sMoName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vmname")));
				}
				valid = false;
			} else if (!CommonValidate.validateName(studentDataBean.getStuMotherName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sMoName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vmname")));
				}
				valid = false;
			}
		}
		if (studentDataBean.getStuDob() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sDob").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.dob")));
			}
			valid = false;
		}

		if (StringUtils.isEmpty(studentDataBean.getStuGender())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sGender").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.gender")));
			}
			valid = false;
		}

		if (StringUtils.isEmpty(studentDataBean.getStuPresentAddress())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("SPAddress").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.address")));
			}
			valid = false;

		} else if (studentDataBean.getStuPresentAddress().length() < 3) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("SPAddress").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vaddress")));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(studentDataBean.getStuPresentState())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("myTextBox1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.state")));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(studentDataBean.getStuPermanentAddress())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("SpermantAddress").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.peraddress")));
			}
			valid = false;
		} else if (studentDataBean.getStuPermanentAddress().length() < 3) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("SpermantAddress").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vperaddress")));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(studentDataBean.getStuPermanentState())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("myTextBox4").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.perstate")));
			}
			valid = false;
		}

		if (StringUtils.isEmpty(studentDataBean.getStuPresentZip())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("myTextBox").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.zip")));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(studentDataBean.getStuPermanentZip())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("myTextBox3").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.perzip")));
			}
			valid = false;
		}
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		int schoolid=Integer.parseInt(schoolID);
		if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
		}else{
			if (StringUtils.isEmpty(studentDataBean.getStuPhoneNo())) {
				if (flag) {
	
					fieldName = CommonValidate.findComponentInRoot("sPhoneNo").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.phno")));
				}
				valid = false;
			} else if(studentDataBean.getStuPhoneNo().length()<11){
				if (flag) {
	
					fieldName = CommonValidate.findComponentInRoot("sPhoneNo").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vphno")));
				}
				valid = false;
			}
	
			if (StringUtils.isEmpty(studentDataBean.getStuEmail())) {
				{
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("sEmail").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.email")));
					}
					valid = false;
				}
			} else if (!StringUtils.isEmpty(studentDataBean.getStuEmail())) {
				if (!CommonValidate.validateEmail(studentDataBean.getStuEmail())) {
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("sEmail").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.email")));
					}
					valid = false;
				}
			}
		}
		if (StringUtils.isEmpty(studentDataBean.getStuFatherOccu())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sFatherOccu").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.foccupation")));
			}
			valid = false;
		}
		if (studentDataBean.getStuFatherIncome() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sFatherInRange").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.income")));
			}
			valid = false;
		} else if (studentDataBean.getStuFatherIncome() != null) {
			if (!CommonValidate.validatePrice(studentDataBean.getStuFatherIncome())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sFatherInRange").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vincome")));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(studentDataBean.getStuMotherOccu())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sMotherOccu").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.moccupation")));
			}
			valid = false;
		}*/

		if (StringUtils.isEmpty(studentDataBean.getStuCls())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sClass").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.class")));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(studentDataBean.getStuRollNo())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sRollNo").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.rollno")));
			}
			valid = false;
		} 
		if(schoolID.equals(paths.getString("SMRV.SCHOOLID"))){
			if(StringUtils.isEmpty(studentDataBean.getReligion())){
				fieldName = CommonValidate.findComponentInRoot("religion").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.religion")));
				valid=false;
			}if(StringUtils.isEmpty(studentDataBean.getClassification())){
				fieldName = CommonValidate.findComponentInRoot("classification").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.classification")));
				valid=false;
			}
			/*for (int i = 0; i <emisnoList.size(); i++) {
				if(StringUtils.isEmpty(emisnoList.get(i))){
					fieldName = CommonValidate.findComponentInRoot("panelid").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.emisNo")));
					valid=false;
				}
			}*/
		}
		return valid;
	}

	public void studentDelete() {
		logger.debug("-----inside student Delete--------");
		setBoxflag(false);
		setDelBoxflag(false);
		SmsController controller = null;
		if (studentDataBean.getStuRollNo() != null) {
			try {
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				status = controller.getStudentDelete(personID, studentDataBean);
				if (status.equalsIgnoreCase("Success")) {
					setDelBoxflag(true);
				}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
		}

	}

	public String convertToString(List<String> list) {
		String joinValue="";
		try{
			if(list!=null){
				for(int i=0; i<list.size(); i++){
					joinValue += list.get(i)+" ";
				}
				joinValue=joinValue.replace(" ", "");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return joinValue;
	}
	
	public String studentUpdates() {
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		RequestContext reqcontextt = RequestContext.getCurrentInstance();
		reqcontextt.execute("PF('studentUpdatesUI').show();");
		if (validate(true)) {
			logger.debug("validate true ");	
			if(schoolID.equals(paths.getString("SMRV.SCHOOLID"))){
				studentDataBean.setBankAccNo(convertToString(numberList));
				studentDataBean.setIfscCode(convertToString(ifscnoList));
				studentDataBean.setAadharCardNo(convertToString(aadharnoList));
				studentDataBean.setBranchCode(convertToString(branchcodeList));
				studentDataBean.setEmisNo(convertToString(emisnoList));
			}
			reqcontextt.execute("studendupdateBlk();");
		}else{
			reqcontextt.execute("PF('studentUpdatesUI').hide();");
		}
		return "";
	}

	public String studentUpdate() {
		logger.info("-----inside student Update-------");
		SmsController controller = null;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			if (studentDataBean.getStuFile() != null) {
				String s = studentDataBean.getStuFile().getContentType();
				logger.debug("file Type " + s);
				String type = s.substring(s.lastIndexOf("/") + 1);
				logger.debug(type);

				copyFile(studentDataBean.getStuRollNo(), studentDataBean.getStuFile().getInputstream(), type);

				logger.debug(studentDataBean.getStuRollNo() + "." + type);
				String path = ft.format(now) + "/" + studentDataBean.getStuRollNo() + "." + type;
				studentDataBean.setStufilePath(path);
				logger.debug(studentDataBean.getStufilePath());
			} else {
				studentDataBean.setStufilePath(null);
			}
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			/*if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6)
			{
			}else{
				if(studentDataBean.getStuPhoneNo().startsWith(text.getString("sms.phno"))){
					studentDataBean.setStuPhoneNo(studentDataBean.getStuPhoneNo());
				}else if(studentDataBean.getStuPhoneNo().startsWith("0")){
					String phoneno=studentDataBean.getStuPhoneNo().substring(1);
					studentDataBean.setStuPhoneNo(text.getString("sms.phno")+phoneno);
				}else{
					studentDataBean.setStuPhoneNo(text.getString("sms.phno")+studentDataBean.getStuPhoneNo());
				}
			}*/
			logger.info("--------------controller calling -----------");
			status = controller.getStudentEdit(studentDataBean,personID);
			logger.debug("Status1" + status);
			logger.info("status - " + status);
			if (status.equalsIgnoreCase("Success")) {
				if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6)
				{
				}else{
					String pdfStatus = generatePdf(studentDataBean);
					logger.debug("status "+pdfStatus);
					if (pdfStatus.equalsIgnoreCase("Success")) 
					{
						MailSendJDBC.studentupdate(studentDataBean);
					}
				}
				
				/*String pdfStatus = generatePdf(studentDataBean);
				if (pdfStatus.equalsIgnoreCase("Success")) {
					String emailStatus = sendEmail(studentDataBean, ImageListPath);
					if (emailStatus.equalsIgnoreCase("Success")) {
						FacesMessage message = new FacesMessage("Succesful",
								studentDataBean.getStuRollNo() + " is Inserted .");
						FacesContext.getCurrentInstance().addMessage(null, message);
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
						studentDataBean.setStuCls("");*/
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('studentUpdatesUI').hide();");
						reqcontext.execute("PF('studentUpdateDialog').show();");
						setBoxflag(true);
						valid = false;
					}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";

	}

	public String dummyAction(FileUploadEvent event) throws IOException {
		this.file = event.getFile();
		studentDataBean.setStuFile(event.getFile());
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);
		return "";
	}

	private void copyFile(String fileName, InputStream inputstream, String n) {
		try {

			// Create Directory
			File files = new File(paths.getString("sms.student.photo") + ft.format(now));
			if (!files.exists()) {
				files.mkdirs();
			} else {
				logger.debug("Alreday Found");
			}

			// write the inputStream to a FileOutputStream
			FileOutputStream out = new FileOutputStream(new File(files + paths.getString("path_context").toString() + fileName + "." + n));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputstream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			inputstream.close();
			out.flush();
			out.close();

			logger.debug("New file created!");
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}

	}

	private String generatePdf(StudentDataBean studentDataBean2) {
		String status = "fail";
		SmsController controller = null;
		String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		int schoolid=Integer.parseInt(schoolID);
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				ImageListPath = new ArrayList<StudentDataBean>();
				ImageListPath = controller.getImagePath1(personID, studentDataBean2.getStuRollNo());
				if (ImageListPath.size() > 0) {
					// Create Directory
					File files = new File(paths.getString("sms.student.pdf") + ft.format(now));
					if (!files.exists()) {
						files.mkdirs();
					} else {
						logger.debug("Alreday Found");
					}
					// Write PDF File
					OutputStream file = new FileOutputStream(
							new File(files + paths.getString("path_context").toString() + studentDataBean2.getStuRollNo() + ".pdf"));
					studentDataBean.setStufilePath(files + paths.getString("path_context").toString() + studentDataBean2.getStuRollNo() + ".pdf");
					Document document = new Document(PageSize.A4, 50, 50, 10, 50);
					PdfWriter.getInstance(document, file);
					document.open();
					
					String logo ="";
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						logo = paths.getString(schoolID+"_LOGO");
						document.add(Image.getInstance(getClass().getClassLoader().getResource(logo)));
					}else{
						logo = studentDataBean.getSchoolLogo();
						document.add(Image.getInstance(logo));
					}
					float[] columnWidths = { 20f, 1f };
					//Font font3 = new Font(Font.TIMES_ROMAN, 27);
					PdfPTable table = new PdfPTable(2); // .
					table.setWidthPercentage(100f);
					table.setWidths(columnWidths);

					PdfPCell cell1 = new PdfPCell(new Paragraph());
					cell1.setBorder(PdfPCell.NO_BORDER);
					PdfPCell cell2 = new PdfPCell(new Paragraph());
					cell2.setBorder(PdfPCell.NO_BORDER);
					table.setSpacingBefore(20f);
					table.setSpacingAfter(25f);

					PdfPTable nestedTable = new PdfPTable(4);
					nestedTable.setWidthPercentage(100f);
					PdfPCell nesCell1 = new PdfPCell(new Paragraph("First Name:"));
					PdfPCell nesCell2 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuFirstName()));
					PdfPCell nesCell3 = new PdfPCell(new Paragraph("Last Name:"));
					PdfPCell nesCell4 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuLastName()));
					nesCell1.setBorder(PdfPCell.NO_BORDER);
					nesCell2.setBorder(PdfPCell.NO_BORDER);
					nesCell3.setBorder(PdfPCell.NO_BORDER);
					nesCell4.setBorder(PdfPCell.NO_BORDER);
					nestedTable.addCell(nesCell1);
					nestedTable.addCell(nesCell2);
					nestedTable.addCell(nesCell3);
					nestedTable.addCell(nesCell4);
					nestedTable.setSpacingBefore(3f);
					nestedTable.setSpacingAfter(3f);
					cell1.addElement(nestedTable);

					PdfPTable nestedTable1 = new PdfPTable(4);
					nestedTable1.setWidthPercentage(100f);
					PdfPCell nesCell5 = new PdfPCell(new Paragraph("Father Name:"));
					PdfPCell nesCell6 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuFatherName()));
					PdfPCell nesCell7 = new PdfPCell(new Paragraph("Mother Name:"));
					PdfPCell nesCell8 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuMotherName()));
					nesCell5.setBorder(PdfPCell.NO_BORDER);
					nesCell6.setBorder(PdfPCell.NO_BORDER);
					nesCell7.setBorder(PdfPCell.NO_BORDER);
					nesCell8.setBorder(PdfPCell.NO_BORDER);
					nestedTable1.addCell(nesCell5);
					nestedTable1.addCell(nesCell6);
					nestedTable1.addCell(nesCell7);
					nestedTable1.addCell(nesCell8);
					nestedTable1.setSpacingBefore(3f);
					nestedTable1.setSpacingAfter(3f);
					cell1.addElement(nestedTable1);

					PdfPTable nestedTable2 = new PdfPTable(4);
					nestedTable2.setWidthPercentage(100f);
					PdfPCell nesCell9 = new PdfPCell(new Paragraph(" Date of Birth:"));
					PdfPCell nesCell10 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuDob().toString()));
					PdfPCell nesCell11 = new PdfPCell(new Paragraph("Gender:"));
					PdfPCell nesCell12 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuGender()));
					nesCell9.setBorder(PdfPCell.NO_BORDER);
					nesCell10.setBorder(PdfPCell.NO_BORDER);
					nesCell11.setBorder(PdfPCell.NO_BORDER);
					nesCell12.setBorder(PdfPCell.NO_BORDER);
					nestedTable2.addCell(nesCell9);
					nestedTable2.addCell(nesCell10);
					nestedTable2.addCell(nesCell11);
					nestedTable2.addCell(nesCell12);
					nestedTable2.setSpacingBefore(3f);
					nestedTable2.setSpacingAfter(3f);
					cell1.addElement(nestedTable2);

					PdfPTable nestedTable3 = new PdfPTable(4);
					nestedTable3.setWidthPercentage(100f);
					PdfPCell nesCell13 = new PdfPCell(new Paragraph("Present Address:"));
					PdfPCell nesCell14 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuPermanentAddress()));
					PdfPCell nesCell15 = new PdfPCell(new Paragraph("Permanent Address:"));
					PdfPCell nesCell16 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuPermanentAddress()));
					nesCell13.setBorder(PdfPCell.NO_BORDER);
					nesCell14.setBorder(PdfPCell.NO_BORDER);
					nesCell15.setBorder(PdfPCell.NO_BORDER);
					nesCell16.setBorder(PdfPCell.NO_BORDER);
					nestedTable3.addCell(nesCell13);
					nestedTable3.addCell(nesCell14);
					nestedTable3.addCell(nesCell15);
					nestedTable3.addCell(nesCell16);
					nestedTable3.setSpacingBefore(3f);
					nestedTable3.setSpacingAfter(3f);
					cell1.addElement(nestedTable3);
					
					/*PdfPTable nestedTable3 = new PdfPTable(4);
					nestedTable3.setWidthPercentage(100f);
					PdfPCell nesCell13 = new PdfPCell(new Paragraph("Present Address:"));
					PdfPCell nesCell14 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuPermanentAddress()));
					PdfPCell nesCell15 = new PdfPCell(new Paragraph("Present State:"));
					PdfPCell nesCell16 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuPresentState()));
					nesCell13.setBorder(PdfPCell.NO_BORDER);
					nesCell14.setBorder(PdfPCell.NO_BORDER);
					nesCell15.setBorder(PdfPCell.NO_BORDER);
					nesCell16.setBorder(PdfPCell.NO_BORDER);
					nestedTable3.addCell(nesCell13);
					nestedTable3.addCell(nesCell14);
					nestedTable3.addCell(nesCell15);
					nestedTable3.addCell(nesCell16);
					nestedTable3.setSpacingBefore(3f);
					nestedTable3.setSpacingAfter(3f);
					cell1.addElement(nestedTable3);

					PdfPTable nestedTable4 = new PdfPTable(4);
					nestedTable4.setWidthPercentage(100f);
					PdfPCell nesCell17 = new PdfPCell(new Paragraph("Permanent Address:"));
					PdfPCell nesCell18 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuPermanentAddress()));
					PdfPCell nesCell19 = new PdfPCell(new Paragraph("Permanent State:"));
					PdfPCell nesCell20 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuPermanentState()));
					nesCell17.setBorder(PdfPCell.NO_BORDER);
					nesCell18.setBorder(PdfPCell.NO_BORDER);
					nesCell19.setBorder(PdfPCell.NO_BORDER);
					nesCell20.setBorder(PdfPCell.NO_BORDER);
					nestedTable4.addCell(nesCell17);
					nestedTable4.addCell(nesCell18);
					nestedTable4.addCell(nesCell19);
					nestedTable4.addCell(nesCell20);
					nestedTable4.setSpacingBefore(3f);
					nestedTable4.setSpacingAfter(3f);
					cell1.addElement(nestedTable4);

					PdfPTable nestedTable5 = new PdfPTable(4);
					nestedTable5.setWidthPercentage(100f);
					PdfPCell nesCell37 = new PdfPCell(new Paragraph("Present Zip:"));
					PdfPCell nesCell38 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuPresentZip()));
					PdfPCell nesCell39 = new PdfPCell(new Paragraph("Permanent Zip:"));
					PdfPCell nesCell40 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuPermanentZip()));
					nesCell37.setBorder(PdfPCell.NO_BORDER);
					nesCell38.setBorder(PdfPCell.NO_BORDER);
					nesCell39.setBorder(PdfPCell.NO_BORDER);
					nesCell40.setBorder(PdfPCell.NO_BORDER);
					nestedTable5.addCell(nesCell37);
					nestedTable5.addCell(nesCell38);
					nestedTable5.addCell(nesCell39);
					nestedTable5.addCell(nesCell40);
					nestedTable5.setSpacingBefore(3f);
					nestedTable5.setSpacingAfter(3f);
					cell1.addElement(nestedTable5);*/
					
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6)
					{
					}else{
						PdfPTable nestedTable6 = new PdfPTable(4);
						nestedTable6.setWidthPercentage(100f);
						PdfPCell nesCell21 = new PdfPCell(new Paragraph("Phone Number:"));
						PdfPCell nesCell22 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuPhoneNo()));
						PdfPCell nesCell23 = new PdfPCell(new Paragraph("Email ID:"));
						PdfPCell nesCell24 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuEmail()));
						nesCell21.setBorder(PdfPCell.NO_BORDER);
						nesCell22.setBorder(PdfPCell.NO_BORDER);
						nesCell23.setBorder(PdfPCell.NO_BORDER);
						nesCell24.setBorder(PdfPCell.NO_BORDER);
						nestedTable6.addCell(nesCell21);
						nestedTable6.addCell(nesCell22);
						nestedTable6.addCell(nesCell23);
						nestedTable6.addCell(nesCell24);
						nestedTable6.setSpacingBefore(3f);
						nestedTable6.setSpacingAfter(3f);
						cell1.addElement(nestedTable6);
					}					

					PdfPTable nestedTable7 = new PdfPTable(4);
					nestedTable7.setWidthPercentage(100f);
					PdfPCell nesCell25 = new PdfPCell(new Paragraph("Father Occupation:"));
					PdfPCell nesCell26 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuFatherOccu()));
					PdfPCell nesCell27 = new PdfPCell(new Paragraph("Father Income:"));
					PdfPCell nesCell28 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuFatherIncome()));
					nesCell25.setBorder(PdfPCell.NO_BORDER);
					nesCell26.setBorder(PdfPCell.NO_BORDER);
					nesCell27.setBorder(PdfPCell.NO_BORDER);
					nesCell28.setBorder(PdfPCell.NO_BORDER);
					nestedTable7.addCell(nesCell25);
					nestedTable7.addCell(nesCell26);
					nestedTable7.addCell(nesCell27);
					nestedTable7.addCell(nesCell28);
					nestedTable7.setSpacingBefore(3f);
					nestedTable7.setSpacingAfter(3f);
					cell1.addElement(nestedTable7);

					PdfPTable nestedTable8 = new PdfPTable(4);
					nestedTable8.setWidthPercentage(100f);
					PdfPCell nesCell29 = new PdfPCell(new Paragraph("Mother Occupation:"));
					PdfPCell nesCell30 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuMotherOccu()));
					PdfPCell nesCell31 = new PdfPCell(new Paragraph("Class&Section:"));
					PdfPCell nesCell32 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuCls()));
					nesCell29.setBorder(PdfPCell.NO_BORDER);
					nesCell30.setBorder(PdfPCell.NO_BORDER);
					nesCell31.setBorder(PdfPCell.NO_BORDER);
					nesCell32.setBorder(PdfPCell.NO_BORDER);
					nestedTable8.addCell(nesCell29);
					nestedTable8.addCell(nesCell30);
					nestedTable8.addCell(nesCell31);
					nestedTable8.addCell(nesCell32);
					nestedTable8.setSpacingBefore(3f);
					nestedTable8.setSpacingAfter(3f);
					cell1.addElement(nestedTable8);

					PdfPTable nestedTable9 = new PdfPTable(4);
					nestedTable9.setWidthPercentage(100f);
					PdfPCell nesCell33 = new PdfPCell(new Paragraph("Roll Number:"));
					PdfPCell nesCell34 = new PdfPCell(new Paragraph(ImageListPath.get(0).getStuRollNo()));
					nesCell33.setBorder(PdfPCell.NO_BORDER);
					nesCell34.setBorder(PdfPCell.NO_BORDER);
					nestedTable9.addCell(nesCell33);
					nestedTable9.addCell(nesCell34);
					nestedTable9.setSpacingBefore(3f);
					nestedTable9.setSpacingAfter(3f);
					cell1.addElement(nestedTable9);

					if(ImageListPath.get(0).getPathDate()!=null){
						PdfPTable nestedTable10 = new PdfPTable(2);
						nestedTable10.setWidthPercentage(100f);						
						PdfPCell nesCell45 = new PdfPCell(new Paragraph("Photo"));
						String tempdate = ft.format(ImageListPath.get(0).getPathDate());
						String logos = paths.getString("sms.student.photo") + tempdate + "/" + ImageListPath.get(0).getPath();
						Image image = Image.getInstance(logos);
						image.scaleAbsolute(100, 100);
						PdfPCell nesCell46 = new PdfPCell(image, false);
						nesCell45.setBorder(PdfPCell.NO_BORDER);
						nesCell46.setBorder(PdfPCell.NO_BORDER);
						nestedTable10.addCell(nesCell45);
						nestedTable10.addCell(nesCell46);
						nestedTable10.setSpacingBefore(3f);
						nestedTable10.setSpacingAfter(3f);
						cell1.addElement(nestedTable10);
					}
					table.addCell(cell1);
					table.addCell(cell2);
					document.add(table);
					document.close();
					file.close();

					/*
					 * //PDF open Windows and MAC File pdfFile = new
					 * File(files+paths.getString("path_context").toString()+studentDataBean2.getStuUsername()+".pdf")
					 * ; if(pdfFile.exists()){ if (Desktop.isDesktopSupported())
					 * { Desktop.getDesktop().open(pdfFile); } else {
					 * logger.debug("Awt Desktop is not supported!"); }
					 * 
					 * } else { logger.debug("File is not exists!"); }
					 */

					logger.debug("Done");
					status = "Success";
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return status;
	}

	/*private String sendEmail(StudentDataBean studentDataBean2, List<StudentDataBean> imageListPath2) {
		String status = "Fail";
		String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
		String mail = studentDataBean2.getStuEmail();
		String to = "" + mail;
		Properties prop = new Properties();
		prop.put("mail.smtp.host", text.getString("sms.mail.host"));
		prop.put("mail.smtp.socketFactory.port", text.getString("sms.mail.port"));
		prop.put("mail.smtp.socketFactory.class", text.getString("sms.mail.class"));
		prop.put("mail.smtp.auth", text.getString("sms.mail.auth"));
		prop.put("mail.smtp.port", text.getString("sms.mail.smtp.port"));

		String body1 = "<htm><head></heade><body>"
				+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
				+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
				+ "<h3>Dear " + studentDataBean2.getStuRollNo() + ",</h3>" + "<p>Welcome on-board into"
						+ schoolName
						+ " family."
				+ "Your Profile is succesfully updated and the details are enclosed with this mail.</p>" + "<footer>"
				+ "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>"
				+ "</body></html>";

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
			}
		});
		// compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			 message.addRecipients(Message.RecipientType.CC, myCcList); 
			message.setSubject("Profile Updated-" + studentDataBean2.getStuRollNo());
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setContent(body1, "text/html");

			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			String filename = paths.getString("sms.student.pdf") + ft.format(now) + "/" + studentDataBean2.getStuRollNo()
					+ ".pdf";// change accordingly
			DataSource source = new FileDataSource(filename);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(studentDataBean2.getStuRollNo() + ".pdf");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);
			message.setContent(multipart);

			Transport.send(message);

			logger.debug("message sent successfully");
			status = "Success";

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return status;
	}*/

	public void classChange1(ValueChangeEvent v) {
		logger.debug("-----inside value changed1 method--------");
	//SmsController controller = null;
		setSflag(false);
		try {
			String stuclassChange = v.getNewValue().toString();
			String stuclass = studentDataBean.getStuCls1();

			logger.debug("class -- " + stuclass);
			logger.debug("stuclassChange -- " + stuclassChange);

			if (stuclass.equalsIgnoreCase(stuclassChange)) {
				setClassflag(false);
			} else {
				setClassflag(true);
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}

	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		int schoolid=Integer.parseInt(schoolID);
		String logo ="";		
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);		
		if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
			logo = paths.getString(schoolID+"_LOGO");
			pdf.add(Image.getInstance(getClass().getClassLoader().getResource(logo)));
		}else{
			logo=MailSendJDBC.pdfProcess(schoolid);
			logger.debug("logo "+logo);
			pdf.add(Image.getInstance(logo));
		}		
		pdf.add(new Paragraph(
				"------------------------------------------------------------------------------------------------------------------------"));
	}

	public void classChange(ValueChangeEvent v) {
		logger.debug("-----inside value changed method--------");
		SmsController controller = null;
		setSflag(false);
		try {

			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String schoolID=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID").toString();
			String roll=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role").toString();
			if (personID != null) {
				studentDataBean.setTeaclssection(v.getNewValue().toString());
				studentList = controller.getStudentInfo(personID, studentDataBean);
				if (roll.equalsIgnoreCase("Admin")) {
					setSflag(true);
					setFlag2(true);
				} else {
					setSflag(false);
					setFlag2(true);
				}
				if(schoolID.equalsIgnoreCase(paths.getString("SMRV.SCHOOLID"))){
					if(roll.equalsIgnoreCase("Teacher")){
						setSflag(true);
					}
				}
			}
		} catch (Exception e) {
			logger.warn(e);
		}
	}
	
	
	public void state(ValueChangeEvent v){
		SmsController controller=null;
		String valuechange="";
		setCode("");
		statelist=new ArrayList<String>();
		try{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			logger.debug("=========================================1");
			controller = (SmsController) ctx.getBean("controller");	
			valuechange=v.getNewValue().toString();				
			statelist=controller.getcountryList(valuechange); 
			logger.debug("================>"+statelist.size());  
			if(valuechange.equalsIgnoreCase("INDIA")) studentDataBean.setCode("+91"); 
		     else if(valuechange.equalsIgnoreCase("MALAYSIA")) studentDataBean.setCode("+60"); 
		     else if(valuechange.equalsIgnoreCase("UAE")) studentDataBean.setCode("+971"); 
		     else if(valuechange.equalsIgnoreCase("INDONESIA")) studentDataBean.setCode("+62");
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}
		}

	public void state1(ValueChangeEvent v){
		SmsController controller=null;
		String valuechange="";
		setCode("");statelist1=new ArrayList<String>();
		try{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			logger.debug("=========================================1");
			controller = (SmsController) ctx.getBean("controller");	
			valuechange=v.getNewValue().toString();				
			statelist1=controller.getcountryList(valuechange); 
			logger.debug("================>"+statelist1.size());  
			if(valuechange.equalsIgnoreCase("INDIA")) studentDataBean.setCode1("+91"); 
		     else if(valuechange.equalsIgnoreCase("MALAYSIA")) studentDataBean.setCode1("+60"); 
		     else if(valuechange.equalsIgnoreCase("UAE")) studentDataBean.setCode1("+971"); 
		     else if(valuechange.equalsIgnoreCase("INDONESIA")) studentDataBean.setCode1("+62");
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}
		}
	
	public void checkbox(ValueChangeEvent v){	
		 String chkBox=null;
		try{
			logger.debug("========================>insidecheckbox================================>");
			chkBox=v.getNewValue().toString();
			if(chkBox.equals("true")){
				setPermenantAddFlag(false);
				setPermenantAddFlag1(true); 
				studentDataBean.setStuPermanentAddress(studentDataBean.getStuPresentAddress());
				studentDataBean.setStuPermanentCountry(studentDataBean.getStuPresentCountry());
				studentDataBean.setStuPermanentState(studentDataBean.getStuPresentState());
				studentDataBean.setStuPermanentCity(studentDataBean.getStuPresentCity());
				studentDataBean.setStuPermanentpostal(studentDataBean.getStuPresentpostal());
				studentDataBean.setStuPhoneNo1(studentDataBean.getStuPhoneNo());
				studentDataBean.setCode1(studentDataBean.getCode());
			}else{
				setPermenantAddFlag(true);
				setPermenantAddFlag1(false);  
				studentDataBean.setStuPermanentAddress("");
				studentDataBean.setStuPermanentCountry("");
				studentDataBean.setStuPermanentState("");
				studentDataBean.setStuPermanentCity("");
				studentDataBean.setStuPermanentpostal("");
				studentDataBean.setStuPhoneNo1("");
				studentDataBean.setCode1("");
			}
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}
	}
	
	@SuppressWarnings("null")
	public String globalstudentpage(StudentDataBean studentDataBean2) {
		logger.debug("-----inside student Edit--------");
		SmsController controller = null;
		String personID = "";
		try {
			studentDataBean.setTerm("");
			stateLists=new ArrayList<String>();
			valid = true;
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			logger.info(personID);
			if ((personID != null) || (!personID.equalsIgnoreCase(""))) {
				stateLists = controller.getStateLists(personID);
			}
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			sclass1 = new ArrayList<String>();
			classlist = controller.getClassList(personID, studentDataBean);
			for (int i = 0; i < studentDataBean.getStuPickList().size(); i++) {
				sclass1.add(studentDataBean.getStuPickList().get(i).getStuCls());
			}
			logger.debug("classlist size ====== "+sclass1.size());
			studentDataBean.setStuRollNo(studentDataBean2.getStuRollNo());
			studentDataBean.setActList(controller.getActivitieslist(studentDataBean));
			editList = controller.getStudentDetails(studentDataBean,personID);
			logger.debug("editList ====== "+editList.size());
			if (editList.size() > 0) {
				for (StudentDetail st : editList) {
					studentDataBean.setStuFirstName(st.getFirstName());
					studentDataBean.setStuLastName(st.getLastName());
					studentDataBean.setStuFatherName(st.getFatherName());
					studentDataBean.setStuMotherName(st.getMotherName());
					studentDataBean.setStuDob(st.getDob());
					studentDataBean.setStuGender(st.getGender());
					studentDataBean.setStuPresentAddress(st.getAddress1_Street());
					studentDataBean.setStuPermanentAddress(st.getAddress2_Street());
					/*studentDataBean.setStuPresentState(st.getPostcode1().getState().getStateName());
					studentDataBean.setStuPresentZip(st.getPostcode1().getPostcode());
					studentDataBean.setStuPermanentState(st.getPostcode2().getState().getStateName());
					studentDataBean.setStuPermanentZip(st.getPostcode2().getPostcode());*/
					studentDataBean.setStuFatherOccu(st.getFatherOccupation());
					studentDataBean.setStuFatherIncome(st.getFatherIncome());
					studentDataBean.setStuMotherOccu(st.getMotherOccupation());
					studentDataBean.setStuCls(studentDataBean.getClassName());
					studentDataBean.setStuCls1(studentDataBean.getClassName());
					studentDataBean.setStuPermanentCountry(st.getPermanentCountry());
					studentDataBean.setStuPresentCountry(st.getPresentCountry());
					studentDataBean.setStuPermanentState(st.getPermanentState());
					studentDataBean.setStuPresentState(st.getPresentState());
					studentDataBean.setStuPresentCity(st.getPresentCity());
					studentDataBean.setStuPermanentCity(st.getPermanentCity());
					studentDataBean.setStuPresentpostal(st.getPresentPostal());
					studentDataBean.setStuPermanentpostal(st.getPermanentPostal());
					studentDataBean.setStuPhoneNo1(st.getPhoneNumber1());
					studentDataBean.setStuPhoneNo(st.getPhoneNumber());
					studentDataBean.setCode(st.getPresentcode());
					studentDataBean.setCode1(st.getPermanentcode());
					if(schoolID.equals(paths.getString("SMRV.SCHOOLID"))){
						if (studentDataBean.getClassName().split("/")[1].equalsIgnoreCase(paths.getString("SMRV.CLASS.SECTION"))) {
							feesdetailFlag=true;
						}
						else{
							feesdetailFlag=false;
						}
						studentDataBean.setReligion(st.getReligion());
						studentDataBean.setCaste(st.getCaste());
						studentDataBean.setClassification(st.getClassification());
						studentDataBean.setCommunityCertificateNo(st.getCommunityCertificateNo());
						studentDataBean.setIssuedAuthority(st.getIssuedAuthority());
						studentDataBean.setIssuedDate(st.getIssueddate());
						studentDataBean.setBankAccNo(st.getBankAccNo());
						studentDataBean.setBankName(st.getBankName());
						studentDataBean.setBranchName(st.getBranchName());
						studentDataBean.setIfscCode(st.getIfscCode());
						studentDataBean.setMicrNo(st.getMicrNo());
						studentDataBean.setBranchCode(st.getBranchCode());
						studentDataBean.setAadharCardNo(st.getAadharCardNo());
						studentDataBean.setRationCardNo(st.getRationCardNo());
						studentDataBean.setEmploymentCardNo(st.getEmploymentCardNo());
						studentDataBean.setEmisNo(st.getEmisNo());
						studentDataBean.setBusPassNo(st.getBusPassNo());
						numberList=new ArrayList<String>();
						ifscnoList=new ArrayList<String>();
						branchcodeList=new ArrayList<String>();
						aadharnoList=new ArrayList<String>();
						emisnoList=new ArrayList<String>();
						if(studentDataBean.getBankAccNo().equals("")|| studentDataBean.getBankAccNo()==null){
							 for (int i = 0; i < 15; i++) {
							        numberList.add(new String());
							 }
						 }else{
							 for (int i = 0; i < studentDataBean.getBankAccNo().length(); i++) {
							        numberList.add(String.valueOf(studentDataBean.getBankAccNo().charAt(i)));
							 }
						 }
						 if(studentDataBean.getIfscCode().equals("") ||  studentDataBean.getIfscCode()==null){
							 for (int i = 0; i < 11; i++) {
							        ifscnoList.add(new String());
							  }
						  }else{
							  for (int i = 0; i < studentDataBean.getIfscCode().length(); i++) {
							        ifscnoList.add(String.valueOf(studentDataBean.getIfscCode().charAt(i)));
							  }
						  }
						  if(studentDataBean.getBranchCode().equals("") || studentDataBean.getBranchCode()==null){
							  for (int i = 0; i < 9; i++) {
							        branchcodeList.add(new String());
							  }
						  }else{
							  for (int i = 0; i <studentDataBean.getBranchCode().length(); i++) {
							        branchcodeList.add(String.valueOf(studentDataBean.getBranchCode().charAt(i)));
							   }
						   }
						   if(studentDataBean.getAadharCardNo().equals("") || studentDataBean.getAadharCardNo()==null){
							   for (int i = 0; i < 14; i++) {
							        aadharnoList.add(new String());
							    }
						    }else{
						    	 for (int i = 0; i < studentDataBean.getAadharCardNo().length(); i++) {
								        aadharnoList.add(String.valueOf(studentDataBean.getAadharCardNo().charAt(i)));
								  }
						    }
						    if(studentDataBean.getEmisNo().equals("") || studentDataBean.getEmisNo()==null){
						    	 for (int i = 0; i < 16; i++) {
								        emisnoList.add(new String());
								  }
						    }else{
						    	 for (int i = 0; i < studentDataBean.getEmisNo().length(); i++) {
								        emisnoList.add(String.valueOf(studentDataBean.getEmisNo().charAt(i)));
								  }
						    }
					}
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){						
						flags=false;
					}else{
						studentDataBean.setStuEmail(st.getEmailId());
						studentDataBean.setStuPhoneNo(st.getPhoneNumber());
						flags=true;
					}					
					studentDataBean.setStufilePath(studentDataBean.getImagePath1());
					
				}
			}
			gettimelist();
			FacesContext.getCurrentInstance().getExternalContext().redirect(paths.getString("stumosys.studentglobalpage"));
			
		} catch (Exception e)

		{
			logger.error("Exception -->"+e.getMessage());
			logger.warn("Exception -->"+e.getMessage());
			e.printStackTrace();
		}
		return "";

	}
	
	List<ReportCardDataBean> studentMarkList=null;
	List<TimeTable> timeTableList=null;
	List<String> examList=null;
	List<ExamTable> examTableList=null;
	ReportCardDataBean reportCardDataBean=new ReportCardDataBean();
	
	
	public List<ExamTable> getExamTableList() {
		return examTableList;
	}

	public void setExamTableList(List<ExamTable> examTableList) {
		this.examTableList = examTableList;
	}

	public ReportCardDataBean getReportCardDataBean() {
		return reportCardDataBean;
	}

	public void setReportCardDataBean(ReportCardDataBean reportCardDataBean) {
		this.reportCardDataBean = reportCardDataBean;
	}

	public List<String> getExamList() {
		return examList;
	}

	public void setExamList(List<String> examList) {
		this.examList = examList;
	}

	public List<TimeTable> getTimeTableList() {
		return timeTableList;
	}

	public void setTimeTableList(List<TimeTable> timeTableList) {
		this.timeTableList = timeTableList;
	}

	public List<ReportCardDataBean> getStudentMarkList() {
		return studentMarkList;
	}

	public void setStudentMarkList(List<ReportCardDataBean> studentMarkList) {
		this.studentMarkList = studentMarkList;
	}
	
	public void onTabChange(TabChangeEvent t)
	{
		String tabname="";SmsController controller = null;String personID = "";
		studentDataBean.setStuActivity(null);studentDataBean.setActFlag(false);
		try{
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			tabname=t.getTab().getTitle();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			studentDataBean.setSchool_id(Integer.parseInt(schoolID));
			studentDataBean.setActList(new ArrayList<String>());
			if(tabname.equalsIgnoreCase("Behaviour")){
				behaviourtab();
			}
			else if(tabname.equalsIgnoreCase("Extra Curricular Activities")){
				studentDataBean.setActivityList(controller.getActivity(studentDataBean));
				studentDataBean.setActList(controller.getActivitieslist(studentDataBean));
			}
			//This need to come from Property file
			else if(tabname.equalsIgnoreCase("Attendance")){
				attendanceDataBean.setSelectedmonth("");
				attendanceDataBean.setAttendanceFlag(false);
				attendanceTab();	
			}
			else if(tabname.equalsIgnoreCase("Fees Details")){
					int year=Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR));
					years=new ArrayList<String>();
					years.add(0,(String.valueOf((year-1))));
					years.add(1,(String.valueOf((year))));
					years.add(2,(String.valueOf((year+1))));
					studentDataBean.setYear(String.valueOf(year));
					if(studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("MAHARISHI.SCHOOLID")) || studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("SCMS.SCHOOLID"))) {
					studentDataBean.setMonth(String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1));
					}
				studentDataBean.setTerm("");
				studentDataBean.setClassName(studentDataBean.getStuCls().split("/")[0]);
				feesdetailstab();
			}else if(tabname.equalsIgnoreCase("Mark Details")){
				logger.debug("inside Mark Details");
				Date date=new Date();
				reportCardDataBean.setExamID(0);
				reportCardDataBean.setMonths(String.valueOf((date.getMonth()+1)));
				examTableList=new ArrayList<ExamTable>();
				String classname=controller.getStudentClass(studentDataBean.getStuRollNo(),personID);
				studentMarkList = new ArrayList<ReportCardDataBean>();
					if (personID != null) {	
					controller = (SmsController) ctx.getBean("controller");
					examTableList=controller.getglobalSearchTimeTableList(schoolID,personID,classname,String.valueOf((date.getMonth()+1)));
					logger.debug("examTableList.size----"+examTableList.size());}
					
			}
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}
		finally{
			 tabname=""; controller = null; personID = "";
		}
	}
	
	public String globalloadingupdate() {
		if (validateperformance(true)) {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				RequestContext reqcontextt = RequestContext.getCurrentInstance();
				reqcontextt.execute("studentPerformupdateBlk1();");
			}
		}
		return "";
	}
	
	public String performUpdate() {
		logger.debug("-----inside student Update-------"+studentPerformanceDataBean.getStuName());
		SmsController controller = null;
		setBoxflag(false);
		setBoxflag1(false);
		try {
			String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			if (personID != null) {
				
				studentPerformanceDataBean.setAppearancecheck(check);
				studentPerformanceDataBean.setAttitudecheck(check1);
				if(studentPerformanceDataBean.isAppearancecheck() == false){
					studentPerformanceDataBean.setAppOthers("");
				}
				if(studentPerformanceDataBean.isAttitudecheck() == false){
					studentPerformanceDataBean.setAttOthers("");
				}
				
				status = controller.getPerformEdit(personID, studentPerformanceDataBean);
				logger.debug("Status1" + status);
				if (status.equalsIgnoreCase("Success")) {
					MailSendJDBC.studentPerformUpdate(studentPerformanceDataBean, schoolName,schoolid);
				/*	String pdfStatus = generatePdf(studentPerformanceDataBean);
					logger.debug("pdf status -- " + pdfStatus);*/
					logger.debug("email idd -- " + studentPerformanceDataBean.getMailid());
					/*String emailStatus = sendEmail(studentPerformanceDataBean);
					logger.debug("email status -- " + emailStatus);
					if (emailStatus.equalsIgnoreCase("Success")) {*/
						FacesMessage message = new FacesMessage("Succesful",
								studentPerformanceDataBean.getPerformStudID() + " Performanse is inserted .");
						FacesContext.getCurrentInstance().addMessage(null, message);
						studentPerformanceDataBean.setPerformStudID("");
						studentPerformanceDataBean.setPerClassSection("");
						studentPerformanceDataBean.setStudentAppearance(null);
						studentPerformanceDataBean.setStudentAttitude(null);
						studentPerformanceDataBean.setAppOthers("");
						studentPerformanceDataBean.setAttOthers("");
						studentPerformanceDataBean.setAttitudeOther("");
						setCheck1(false);
						setCheck(false);
						logger.debug("------confirmation------------");
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('studentUpdatesUI').hide();");
						reqcontext.execute("PF('dlg').show();");
						setBoxflag(true);
//					/*}*/

					valid = false;
				} else if (status.equalsIgnoreCase("Not Valid")) {
					RequestContext reqcontext = RequestContext.getCurrentInstance();
					reqcontext.execute("PF('studentUpdatesUI').hide();");
					reqcontext.execute("PF('dlg').show();");
					setBoxflag(true);
					valid = false;
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.error("Exception -->"+e.getMessage());
		}
		return "";
	}
	

	public void valueChangeHandler1(ValueChangeEvent event2) {
		logger.debug("-----inside valueChangeHandler1------");
		String s = event2.getNewValue().toString();
		logger.debug("-------value------" + s);
		if (event2.getNewValue().toString().trim().equalsIgnoreCase("true")) {
			setFlag2(false);
		} else {
			setFlag2(true);
		}
	}

	public void valueChangeHandler2(ValueChangeEvent event3) {
		logger.debug("-----inside valueChangeHandler2------");
		String s = event3.getNewValue().toString();
		logger.debug("-------value------" + s);
		if (event3.getNewValue().toString().trim().equalsIgnoreCase("true")) {
			setFlag1(false);
		} else {
			setFlag1(true);
		}
	}
	
	public boolean validateperformance(boolean flag) {
		boolean valid = true;
		String fieldName;
		String sapp = null;
		String satt = null;
		FacesContext fc = FacesContext.getCurrentInstance();
		logger.debug("---validate---------"+studentPerformanceDataBean.getStudentAppearance());
		Object[] obj1 = studentPerformanceDataBean.getStudentAppearance();
		for (int x = 0; x < obj1.length; x++) {
			Object obj = obj1[x];
			sapp = obj.toString();
			logger.debug(sapp);
			logger.debug("-------"+sapp);
			
		}
		Object[] objj1 = studentPerformanceDataBean.getStudentAttitude();
		for (int x = 0; x < objj1.length; x++) {
			Object objj = objj1[x];
			satt = objj.toString();
			logger.debug(satt);
		}

		
		if (StringUtils.isEmpty(sapp) && StringUtils.isEmpty(satt) && check == false && check1==false) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("perAppearance").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Student Appearance or Attitude"));
			}
			valid = false;
		}
		
		if(check == true){
			if(StringUtils.isEmpty(studentPerformanceDataBean.getAppOthers())){
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("checkOther").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Reason"));
			}
			valid = false;
			}else if(!StringUtils.isEmpty(studentPerformanceDataBean.getAppOthers())){
				if(!CommonValidate.validateName(studentPerformanceDataBean.getAppOthers()))
				{
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("checkOther").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Reason"));
				}
				valid = false;
			}
			}
		}
		
		if(check1 == true){
			if(StringUtils.isEmpty(studentPerformanceDataBean.getAttOthers()))
			{
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("checkAttOther").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Reason"));
			}
			valid = false;
			}else if(!StringUtils.isEmpty(studentPerformanceDataBean.getAttOthers())){
				if(!CommonValidate.validateName(studentPerformanceDataBean.getAttOthers())){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("checkAttOther").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Reason"));
				}
				valid = false;
				}
			}
		}
		
		return valid;
	}

	
	/*2 nd part*/
	
	public void AttendanceSearch(ValueChangeEvent v) {
			logger.debug("---------- search Method Calling-----");
		//	SimpleDateFormat fc=new SimpleDateFormat("dd/MM/yyyy");
			logger.debug("attendance search valuew----->"+v.getNewValue());
			SmsController controller = null;
			String personID = "";
			try {
				logger.debug("----chk value -student ID--->"+attendanceDataBean.getStudentID());
				attendanceDataBean.setDate((Date)v.getNewValue());
				logger.debug("----chk value -date--->"+attendanceDataBean.getDate());
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
					if (personID != null) {
						attendanceDataBean.setStudentList(controller.attendanceSearch(personID, attendanceDataBean));
					}
			logger.debug("---chk size of student list-->"+attendanceDataBean.getStudentList().size());
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
	}
	public void onRowCancelAttendance(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void onRowEditAttendance(RowEditEvent event) {
		logger.debug("update student attendance  ");
		String personID = "";
		try {
			//Calendar now = Calendar.getInstance();
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			if (personID != null) {
				logger.debug("----s no--->"+((AttendanceDataBean) event.getObject()).getSno());
				logger.debug("----getStatus--->"+((AttendanceDataBean) event.getObject()).getStatus().toString());
				logger.debug("----getDate--->"+((AttendanceDataBean) event.getObject()).getDate());
				
				attendanceDataBean.setSno(((AttendanceDataBean) event.getObject()).getSno());
				attendanceDataBean.setStudentName(((AttendanceDataBean) event.getObject()).getStudentName().toString());
				attendanceDataBean.setStatus(((AttendanceDataBean) event.getObject()).getStatus().toString());
				
				logger.debug("----student ID--->"+attendanceDataBean.getStudentID());
				
			
				String status = controller.updateAttendanceGlobal(attendanceDataBean, personID);
				setStatus(status);
				logger.debug("--status--"+status);
				RequestContext reqcontext = RequestContext.getCurrentInstance();
				if (status.equalsIgnoreCase("Success")) {
					reqcontext.execute("PF('studentUpdateDialog').show();");
				}
				else{
					reqcontext.execute("PF('studentUpdateDialog').hide();");
				}
				
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}
	
	/*neela 2*/
	public String behaviourtab(){
		SmsController controller = null;
		List<StudentDataBean> templist=null;
		try{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			studentDataBean.setStudentbehaveList(new ArrayList<StudentDataBean>());
			studentDataBean.setStudentbehaveList(controller.getstudentbehaviourList(studentDataBean));
			templist=new ArrayList<StudentDataBean>();
			for (int i = 0; i < studentDataBean.getStudentbehaveList().size(); i++) {
				try{
					String currentMonth=String.valueOf(d.getMonth()+1);
					String listMonth=String.valueOf(((studentDataBean.getStudentbehaveList().get(i).getBdate().getMonth())+1));
					logger.info("currentMonth------"+currentMonth+"-----listMonth-----"+listMonth);
				if (currentMonth.equals(listMonth)){
				      templist.add(studentDataBean.getStudentbehaveList().get(i));
				     }
				}catch(NullPointerException e){
					logger.error("Exception -->"+e.getMessage());
				}
			}
			studentDataBean.getStudentbehaveList().clear();
			studentDataBean.getStudentbehaveList().addAll(templist);
			studentDataBean.setCurrentmonths(String.valueOf(d.getMonth()+1));
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}
		return "";
	}
	private List<String> years;
	
	public List<String> getYears() {
		return years;
	}

	public void setYears(List<String> years) {
		this.years = years;
	}
	
	private boolean renderflageca = false;
	private boolean renderflagabacus = false;
 
	public boolean isRenderflageca() {
		return renderflageca;
	}

	public void setRenderflageca(boolean renderflageca) {
		this.renderflageca = renderflageca;
	}

	public boolean isRenderflagabacus() {
		return renderflagabacus;
	}

	public void setRenderflagabacus(boolean renderflagabacus) {
		this.renderflagabacus = renderflagabacus;
	}

	public String feesdetailstab(){
		SmsController controller = null;
		 setRenderflageca(false);
		 setRenderflagabacus(false);
		logger.debug("----------feesdetailstab---------------");
		studentDataBean.setPayeditFlag(false);
		try{
			 if(studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("MAHARISHI.SCHOOLID"))){
			 if(studentDataBean.getClassName().equalsIgnoreCase(paths.getString("MAHARISHI.CLASS.STDI")) || 
					 studentDataBean.getClassName().equalsIgnoreCase(paths.getString("MAHARISHI.CLASS.STDII"))){
				 setRenderflageca(true);
			 }
			 else if(studentDataBean.getClassName().equalsIgnoreCase(paths.getString("MAHARISHI.CLASS.STDIII")) || 
					 studentDataBean.getClassName().equalsIgnoreCase(paths.getString("MAHARISHI.CLASS.STDIV")) ||
						studentDataBean.getClassName().equalsIgnoreCase(paths.getString("MAHARISHI.CLASS.STDV"))){
				 setRenderflageca(true);
				 setRenderflagabacus(true);
			 }
			 }
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			
			studentDataBean.setFeesList(controller.getFeesList(studentDataBean));
			if(studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("CNPS.SCHOOLID"))){
				 studentDataBean.setPayeditFlag(false);
			}else{
			 if(studentDataBean.getFeesList().size()== 0 || studentDataBean.getFeesList().get(0).getPaymentStatus().equalsIgnoreCase("Paid")){
			     studentDataBean.setPayFlag(false);
			    }else{
			     studentDataBean.setPayFlag(true);
			    }
			 if(studentDataBean.getFeesList().get(0).getPaymentStatus().equalsIgnoreCase("Not Paid")){
				 studentDataBean.setPayeditFlag(true);
			 }else{
				 studentDataBean.setPayeditFlag(false);
			 }
			}
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}
		return "";
	}
	public void termfeeChange(ValueChangeEvent v) {
		ApplicationContext ctx=null;
		SmsController controller = null;
		try{
			if(studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("CNPS.SCHOOLID"))){
				if(v.getNewValue()!=null || !v.getNewValue().equals(""))
					studentDataBean.setTerm(v.getNewValue().toString());
					ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
					controller = (SmsController) ctx.getBean("controller");
					if("First Term".equalsIgnoreCase(studentDataBean.getTerm())){
					if(paths.getString("CNPS-LKG").equalsIgnoreCase(studentDataBean.getClassName())){
						if(studentDataBean.getStuGender().equalsIgnoreCase("Male")){
							studentDataBean.setTotalFees(paths.getString("LKG.FIRSTTERM.BOYS"));
						}else if(studentDataBean.getStuGender().equalsIgnoreCase("Female")){
							studentDataBean.setTotalFees(paths.getString("LKG.FIRSTTERM.GIRLS"));
						}
					}else if(paths.getString("CNPS-UKG").equalsIgnoreCase(studentDataBean.getClassName())){
						if(studentDataBean.getStuGender().equalsIgnoreCase("Male")){
							studentDataBean.setTotalFees(paths.getString("UKG.FIRSTTERM.BOYS"));
						}else if(studentDataBean.getStuGender().equalsIgnoreCase("Female")){
							studentDataBean.setTotalFees(paths.getString("UKG.FIRSTTERM.GIRLS"));
						}
					}else if(paths.getString("CNPS-STD1").equalsIgnoreCase(studentDataBean.getClassName())){
						if(studentDataBean.getStuGender().equalsIgnoreCase("Male")){
							studentDataBean.setTotalFees(paths.getString("STD1.FIRSTTERM.BOYS"));
						}else if(studentDataBean.getStuGender().equalsIgnoreCase("Female")){
							studentDataBean.setTotalFees(paths.getString("STD1.FIRSTTERM.GIRLS"));
						}
					}else if(paths.getString("CNPS-STD2").equalsIgnoreCase(studentDataBean.getClassName())){
						if(studentDataBean.getStuGender().equalsIgnoreCase("Male")){
							studentDataBean.setTotalFees(paths.getString("STD2.FIRSTTERM.BOYS"));
						}else if(studentDataBean.getStuGender().equalsIgnoreCase("Female")){
							studentDataBean.setTotalFees(paths.getString("STD2.FIRSTTERM.GIRLS"));
						}
					}else if(paths.getString("CNPS-STD3").equalsIgnoreCase(studentDataBean.getClassName())){
						if(studentDataBean.getStuGender().equalsIgnoreCase("Male")){
							studentDataBean.setTotalFees(paths.getString("STD3.FIRSTTERM.BOYS"));
						}else if(studentDataBean.getStuGender().equalsIgnoreCase("Female")){
							studentDataBean.setTotalFees(paths.getString("STD3.FIRSTTERM.GIRLS"));
						}
					}else if(paths.getString("CNPS-STD4").equalsIgnoreCase(studentDataBean.getClassName())){
						if(studentDataBean.getStuGender().equalsIgnoreCase("Male")){
							studentDataBean.setTotalFees(paths.getString("STD4.FIRSTTERM.BOYS"));
						}else if(studentDataBean.getStuGender().equalsIgnoreCase("Female")){
							studentDataBean.setTotalFees(paths.getString("STD4.FIRSTTERM.GIRLS"));
						}
					}else if(paths.getString("CNPS-STD5").equalsIgnoreCase(studentDataBean.getClassName())){
						if(studentDataBean.getStuGender().equalsIgnoreCase("Male")){
							studentDataBean.setTotalFees(paths.getString("STD5.FIRSTTERM.BOYS"));
						}else if(studentDataBean.getStuGender().equalsIgnoreCase("Female")){
							studentDataBean.setTotalFees(paths.getString("STD5.FIRSTTERM.GIRLS"));
						}
					}else if(paths.getString("CNPS-STD6").equalsIgnoreCase(studentDataBean.getClassName())){
						if(studentDataBean.getStuGender().equalsIgnoreCase("Male")){
							studentDataBean.setTotalFees(paths.getString("STD6.FIRSTTERM.BOYS"));
						}else if(studentDataBean.getStuGender().equalsIgnoreCase("Female")){
							studentDataBean.setTotalFees(paths.getString("STD6.FIRSTTERM.GIRLS"));
						}
					}
						controller.insertTermfees(studentDataBean);
						if(studentDataBean.getFeesList().size()==0){
							feesdetailstab();
						}
					}else if("Second Term".equalsIgnoreCase(studentDataBean.getTerm())){
						if(paths.getString("CNPS-LKG").equalsIgnoreCase(studentDataBean.getClassName())){
							if(studentDataBean.getStuGender().equalsIgnoreCase("Male")){
								studentDataBean.setTotalFees(paths.getString("LKG.SECONDTERM.BOYS"));
							}else if(studentDataBean.getStuGender().equalsIgnoreCase("Female")){
								studentDataBean.setTotalFees(paths.getString("LKG.SECONDTERM.GIRLS"));
							}
						}else if(paths.getString("CNPS-UKG").equalsIgnoreCase(studentDataBean.getClassName())){
							if(studentDataBean.getStuGender().equalsIgnoreCase("Male")){
								studentDataBean.setTotalFees(paths.getString("UKG.SECONDTERM.BOYS"));
							}else if(studentDataBean.getStuGender().equalsIgnoreCase("Female")){
								studentDataBean.setTotalFees(paths.getString("UKG.SECONDTERM.GIRLS"));
							}
						}else if(paths.getString("CNPS-STD1").equalsIgnoreCase(studentDataBean.getClassName())){
							if(studentDataBean.getStuGender().equalsIgnoreCase("Male")){
								studentDataBean.setTotalFees(paths.getString("STD1.SECONDTERM.BOYS"));
							}else if(studentDataBean.getStuGender().equalsIgnoreCase("Female")){
								studentDataBean.setTotalFees(paths.getString("STD1.SECONDTERM.GIRLS"));
							}
						}else if(paths.getString("CNPS-STD2").equalsIgnoreCase(studentDataBean.getClassName())){
							if(studentDataBean.getStuGender().equalsIgnoreCase("Male")){
								studentDataBean.setTotalFees(paths.getString("STD2.SECONDTERM.BOYS"));
							}else if(studentDataBean.getStuGender().equalsIgnoreCase("Female")){
								studentDataBean.setTotalFees(paths.getString("STD2.SECONDTERM.GIRLS"));
							}
						}else if(paths.getString("CNPS-STD3").equalsIgnoreCase(studentDataBean.getClassName())){
							if(studentDataBean.getStuGender().equalsIgnoreCase("Male")){
								studentDataBean.setTotalFees(paths.getString("STD3.SECONDTERM.BOYS"));
							}else if(studentDataBean.getStuGender().equalsIgnoreCase("Female")){
								studentDataBean.setTotalFees(paths.getString("STD3.SECONDTERM.GIRLS"));
							}
						}else if(paths.getString("CNPS-STD4").equalsIgnoreCase(studentDataBean.getClassName())){
							if(studentDataBean.getStuGender().equalsIgnoreCase("Male")){
								studentDataBean.setTotalFees(paths.getString("STD4.SECONDTERM.BOYS"));
							}else if(studentDataBean.getStuGender().equalsIgnoreCase("Female")){
								studentDataBean.setTotalFees(paths.getString("STD4.SECONDTERM.GIRLS"));
							}
						}else if(paths.getString("CNPS-STD5").equalsIgnoreCase(studentDataBean.getClassName())){
							if(studentDataBean.getStuGender().equalsIgnoreCase("Male")){
								studentDataBean.setTotalFees(paths.getString("STD5.SECONDTERM.BOYS"));
							}else if(studentDataBean.getStuGender().equalsIgnoreCase("Female")){
								studentDataBean.setTotalFees(paths.getString("STD5.SECONDTERM.GIRLS"));
							}
						}else if(paths.getString("CNPS-STD6").equalsIgnoreCase(studentDataBean.getClassName())){
							if(studentDataBean.getStuGender().equalsIgnoreCase("Male")){
								studentDataBean.setTotalFees(paths.getString("STD6.SECONDTERM.BOYS"));
							}else if(studentDataBean.getStuGender().equalsIgnoreCase("Female")){
								studentDataBean.setTotalFees(paths.getString("STD6.SECONDTERM.GIRLS"));
							}
						}
						controller.insertTermfees(studentDataBean);
						if(studentDataBean.getFeesList().size()==0){
							feesdetailstab();
						}
					}else if("Third Term".equalsIgnoreCase(studentDataBean.getTerm())){
						FacesMessage msg=new FacesMessage("Please Registrer the Third Term Fees");
						FacesContext.getCurrentInstance().addMessage("XX", msg);
						studentDataBean.setFeesList(new ArrayList<StudentDataBean>());
					}
				}
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}

	}
	
	public void payTermfees(StudentDataBean student){
		studentDataBean.setTotalAmount(student.getTotalAmount());
		studentDataBean.setPaidAmount(student.getPaidAmount());
		studentDataBean.setPaidAmount1(student.getPaidAmount());
		studentDataBean.setBalanceAmount(student.getBalanceAmount());
		studentDataBean.setPaymentStatus(student.getPaymentStatus());
		studentDataBean.setPaynow("");
		studentDataBean.setTerm(student.getTerm());
		RequestContext reqcontext = RequestContext.getCurrentInstance();
		reqcontext.execute("PF('paymentdlg').show();");
	}
	
	public void monthlyFeesTable(ValueChangeEvent v) {
		try{
			/*if(studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("MAHARISHI.SCHOOLID"))) {*/
				if (2000<Integer.parseInt(v.getNewValue().toString())) {
					studentDataBean.setYear(v.getNewValue().toString());
				}
				else {
					studentDataBean.setMonth(v.getNewValue().toString());
				}
				logger.debug("-------year and months-----------"+studentDataBean.getMonth()+"---->"+studentDataBean.getYear());
				feesdetailstab();
			
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
			e.printStackTrace();
		}

	}
	
	public void monthlyBehaviour(ValueChangeEvent v) {
		List<StudentDataBean> templist=null;SmsController controller = null;
		try{
			templist=new ArrayList<StudentDataBean>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			studentDataBean.setStudentbehaveList(new ArrayList<StudentDataBean>());
			studentDataBean.setStudentbehaveList(controller.getstudentbehaviourList(studentDataBean));
			for (int i = 0; i < studentDataBean.getStudentbehaveList().size(); i++) {
				try{
					String a=v.getNewValue().toString();
					String b=String.valueOf(((studentDataBean.getStudentbehaveList().get(i).getBdate().getMonth())+1));
				if (a.equals(b)){
				      templist.add(studentDataBean.getStudentbehaveList().get(i));
				     }
				}catch(NullPointerException e){
					logger.error("Exception -->"+e.getMessage());
				}
				
			}
			studentDataBean.getStudentbehaveList().clear();
			studentDataBean.getStudentbehaveList().addAll(templist);
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}

	}
	
	

	
	public String saveActivity(){
		logger.debug("---------------saveActivity-------------------");
		SmsController controller = null;
		String status="";
		try{
			String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			studentDataBean.setSchool_id(Integer.parseInt(schoolID));
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			status=controller.saveActivity(studentDataBean);
			if(status.equalsIgnoreCase("Success")){
				RequestContext reqcontext = RequestContext.getCurrentInstance();
				reqcontext.execute("PF('studentUpdatesUI').hide();");
				reqcontext.execute("PF('actdlg').show();");
			}
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		finally{
			studentDataBean.setStuActivity("");
			studentDataBean.setEtime(null);
			studentDataBean.setStime(null);
		}
		return "";
	}
	
	public String globalsaveActivity() {
		logger.debug("---------------globalsaveActivity-------------------");
		if (validateactivity(true)) {
				RequestContext reqcontextt = RequestContext.getCurrentInstance();
				reqcontextt.execute("studentactivityBlk();");
		}
		return "";
	}
	
	
	public boolean validateactivity(boolean flag) {
		logger.debug("---------------validateactivity-------------------");
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (StringUtils.isEmpty(studentDataBean.getStuActivity())) {
			if (flag) {
				logger.debug("---------------------");
				fieldName = CommonValidate.findComponentInRoot("activity").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.activity")));
			}
			valid = false;
		} 
		if (studentDataBean.getStime()==null) {
			if (flag) {
				logger.debug("---------------------");
				fieldName = CommonValidate.findComponentInRoot("etime").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.stime")));
			}
			valid = false;
		} 
		if (studentDataBean.getEtime()==null) {
			if (flag) {
				logger.debug("---------------------");
				fieldName = CommonValidate.findComponentInRoot("stime").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.etime")));
			}
			valid = false;
		} 
		return valid;
	}
	public void onCellEdit(CellEditEvent event) {
	//	Object oldValue = event.getOldValue();
		//Object newValue = event.getNewValue();
		
	}
	public void onRowEdit(RowEditEvent event) {
		SmsController controller = null;
		String act = "";
		String etime = null;
		String stime = null;
		int pid=0;
		//SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy HH:mm");
		logger.debug("Calling");
		try {
			studentDataBean.setStuActivity("");studentDataBean.setEtime(null);studentDataBean.setStime(null);
			act = ((StudentDataBean) event.getObject()).getStuActivity().toString();
			etime = ((StudentDataBean) event.getObject()).getEtime();
			stime = ((StudentDataBean) event.getObject()).getStime();
			pid=((StudentDataBean) event.getObject()).getAct_pid();
			logger.debug("pid --- "+pid);
			logger.debug("act ----"+act);
			studentDataBean.setStuActivity(act);
			studentDataBean.setEtime(etime);
			studentDataBean.setStime(stime);
			studentDataBean.setAct_pid(pid);
			logger.info("stime ---"+studentDataBean.getStime());
			String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			studentDataBean.setSchool_id(Integer.parseInt(schoolID));
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			status=controller.updateActivity(studentDataBean);
			logger.debug("status ---- "+status);
			if(status.equalsIgnoreCase("Success")){
				RequestContext reqcontext = RequestContext.getCurrentInstance();
				reqcontext.execute("PF('actdlg1').show();");
			}
		}
		catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
	}
	/**
	 * @param event
	 */
	public void onRowEditfees(RowEditEvent event) {
		SmsController controller = null;
		String examfees="";
		String transfees ="";
		String tuitionfees ="";
		String extrafee1="";
		String extrafee2="";
		String specialFees="",ecaFees = "",labFees="",addmissionFees="",totalFees="";
		try{
			String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			studentDataBean.setSchool_id(Integer.parseInt(schoolID));
			if(studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("MAHARISHI.SCHOOLID"))) {
				 transfees = ((StudentDataBean) event.getObject()).getTransfees();
				 tuitionfees = ((StudentDataBean) event.getObject()).getTuitionfees();
				 extrafee1=((StudentDataBean) event.getObject()).getExtraFee1();
				 extrafee2=((StudentDataBean) event.getObject()).getExtraFee2();
				 totalFees=new BigDecimal(transfees).add(new BigDecimal(tuitionfees)).add(new BigDecimal(extrafee1)).add(new BigDecimal(extrafee2)).toString();
			}
			if(studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("SMRV.SCHOOLID"))) {
				 tuitionfees = ((StudentDataBean) event.getObject()).getTuitionfees();
				 totalFees=tuitionfees;
			}else if(studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("SCMS.SCHOOLID"))){
				 transfees = ((StudentDataBean) event.getObject()).getTransfees();
				 tuitionfees = ((StudentDataBean) event.getObject()).getTuitionfees();
				 if("6".equalsIgnoreCase(studentDataBean.getMonth())){
					 ecaFees=((StudentDataBean) event.getObject()).getMicrNo();
					 specialFees=((StudentDataBean) event.getObject()).getAadharCardNo();
					 labFees=((StudentDataBean) event.getObject()).getRationCardNo();
					 addmissionFees=((StudentDataBean) event.getObject()).getBusPassNo();
					 BigDecimal temp =new BigDecimal(transfees).add(new BigDecimal(tuitionfees));
					 BigDecimal temp1=new BigDecimal(specialFees).add(new BigDecimal(ecaFees));
					 BigDecimal temp2=new BigDecimal(labFees).add(new BigDecimal(addmissionFees));
					 totalFees=temp.add(temp1).add(temp2).toString();
				 }else{
					totalFees =new BigDecimal(transfees).add(new BigDecimal(tuitionfees)).toString();
				 }
				 studentDataBean.setMicrNo(ecaFees);
				 studentDataBean.setAadharCardNo(specialFees);
				 studentDataBean.setRationCardNo(labFees);
				 studentDataBean.setBusPassNo(addmissionFees);
			}
			else {
				 examfees = ((StudentDataBean) event.getObject()).getExamfees();
				 transfees = ((StudentDataBean) event.getObject()).getTransfees();
				 tuitionfees = ((StudentDataBean) event.getObject()).getTuitionfees();
				 totalFees=new BigDecimal(examfees).add(new BigDecimal(transfees)).add(new BigDecimal(tuitionfees)).toString();
			}
			studentDataBean.setExamfees(examfees);
			studentDataBean.setTuitionfees(tuitionfees);
			studentDataBean.setTransfees(transfees);
			studentDataBean.setExtraFee1(extrafee1);
			studentDataBean.setExtraFee2(extrafee2);
			studentDataBean.setTotalAmount(totalFees);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			status=controller.updatefees(studentDataBean);
			logger.debug("status =========== "+status);
			if(status.equalsIgnoreCase("Success")){
				RequestContext reqcontext = RequestContext.getCurrentInstance();
				reqcontext.execute("PF('feesdlgupdate').show();");
			}
		}catch(NumberFormatException n){
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Please enter the amounts should be number"));
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Exception -->"+e.getMessage());
		}
	}
	public void onRowCancel(RowEditEvent event) {
		/*FacesMessage msg = new FacesMessage("Edit Cancelled", ((ReportCardDataBean) event.getObject()).getRollNo());
		FacesContext.getCurrentInstance().addMessage(null, msg);*/
	}
	
	public void actChange(ValueChangeEvent ve){
		try{
			String value=ve.getNewValue().toString();
			if(value.equals("+ ADD NEW")){
				studentDataBean.setAddActivity("");
				RequestContext reqcontext = RequestContext.getCurrentInstance();
				reqcontext.execute("PF('addActivitydlg').show();");
			}
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
	}

	public String saveAddActivity(){
		String status="";
		SmsController controller = null;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		try{
			if(studentDataBean.getAddActivity().equals("")||(studentDataBean.getAddActivity()==null)){
				fieldName = CommonValidate.findComponentInRoot("panelact").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.addactivity")));
			}else{
				logger.debug("-------------"+studentDataBean.getAddActivity());
				String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
				studentDataBean.setSchool_id(Integer.parseInt(schoolID));
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				status=controller.saveAddActivity(studentDataBean);
				if(status.equalsIgnoreCase("Success")){
					RequestContext reqcontext = RequestContext.getCurrentInstance();
					reqcontext.execute("PF('addActivitydlg').hide();");
					reqcontext.update("XX");
					reloadActivity();
					studentDataBean.setActFlag(true);
					studentDataBean.setStuActivity(studentDataBean.getAddActivity());
				}
			}
			
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return "";
	}
	public String reloadActivity(){
		SmsController controller = null;
		try{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			studentDataBean.setActivityList(controller.getActivity(studentDataBean));
			studentDataBean.setActList(controller.getActivitieslist(studentDataBean));
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}finally {
			studentDataBean.setActFlag(false);
			studentDataBean.setStuActivity(null);
			studentDataBean.setEtime(null);
			studentDataBean.setStime(null);
		}
		return "";
	}
	
	public String addactivitypanelload(){
		try{
			studentDataBean.setActFlag(true);
			studentDataBean.setStuActivity(null);
			studentDataBean.setEtime(null);
			studentDataBean.setStime(null);
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return "";
	}
	
	public String deleteActivity(StudentDataBean stud){
		SmsController controller = null;
		String status="";
		try{
			logger.debug("--------inside deleteActivity --------------");
			String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			studentDataBean.setSchool_id(Integer.parseInt(schoolID));
			studentDataBean.setAct_pid(stud.getAct_pid());
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			status=controller.deleteActivity(studentDataBean);
			if(status.equalsIgnoreCase("Success")){
				RequestContext reqcontext = RequestContext.getCurrentInstance();
				reqcontext.execute("PF('actdlgdlt').show();");
			}
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}
		return "";
	}
	
	public String deleteFeesDetail(){
		SmsController controller = null;
		String status="";
		try{
			String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			studentDataBean.setSchool_id(Integer.parseInt(schoolID));
			logger.debug("-----------------"+studentDataBean.getStuRollNo());
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			status=controller.deletefeesDetails(studentDataBean);
			if(status.equals("Success")){
				RequestContext reqcontext = RequestContext.getCurrentInstance();
				reqcontext.execute("PF('feesdlgdelete').show();");
			}
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}
		return "";
	}
	
	public String paymentDetail(){
		try{
			feesdetailstab();
			//studentDataBean.setFeesList(controller.getFeesList(studentDataBean));
			if(studentDataBean.getFeesList().size()>0){
				studentDataBean.setTotalAmount(studentDataBean.getFeesList().get(0).getTotalAmount());
				studentDataBean.setPaidAmount(studentDataBean.getFeesList().get(0).getPaidAmount());
				studentDataBean.setPaidAmount1(studentDataBean.getFeesList().get(0).getPaidAmount());
				studentDataBean.setBalanceAmount(studentDataBean.getFeesList().get(0).getBalanceAmount());
				studentDataBean.setPaymentStatus(studentDataBean.getFeesList().get(0).getPaymentStatus());
				studentDataBean.setPaynow("");
				RequestContext reqcontext = RequestContext.getCurrentInstance();
				reqcontext.execute("PF('paymentdlg').show();");
			}
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return "";
	}
	
	public String payfees()
	{
		SmsController controller = null;
		String status="";
		String fieldName;
		StudentPerformanceDataBean stud1=null;
		FacesContext fc = FacesContext.getCurrentInstance();
		try{
			if(new BigDecimal(studentDataBean.getPaidAmount()).compareTo(new BigDecimal(studentDataBean.getTotalAmount()))==1){
				logger.debug("--------------exception-----------------");
				fieldName = CommonValidate.findComponentInRoot("aact").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.exceedamount")));
			}else{
				studentDataBean.setFeesregList(new ArrayList<StudentDataBean>());
				String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
				studentDataBean.setSchool_id(Integer.parseInt(schoolID));
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				status=controller.payfees(studentDataBean);
				logger.debug("status =========== "+status);
				stud1=new StudentPerformanceDataBean();
				if(status.equalsIgnoreCase("Success")){
					if(studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("MAHARISHI.SCHOOLID"))) {
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('feesInvoicedlg').show();");
						reqcontext.execute("PF('paymentdlg').hide();");
						reqcontext.update("XX:feesInvoicedlg");
						feesdetailstab();
						studentDataBean.setTuitionfees(studentDataBean.getFeesList().get(0).getTuitionfees());
						studentDataBean.setTransfees(studentDataBean.getFeesList().get(0).getTransfees());
						studentDataBean.setExtraFee1(studentDataBean.getFeesList().get(0).getExtraFee1());
						studentDataBean.setExtraFee2(studentDataBean.getFeesList().get(0).getExtraFee2());
						
						StudentDataBean stud=new StudentDataBean();
						
						stud.setFees("School Fee");
						stud.setAmount(studentDataBean.getFeesList().get(0).getTuitionfees());
						studentDataBean.getFeesregList().add(stud);
						
						StudentDataBean studd=new StudentDataBean();
						studd.setFees("Van Fee");
						studd.setAmount(studentDataBean.getFeesList().get(0).getTransfees());
						studentDataBean.getFeesregList().add(studd);
						StudentDataBean stud2=new StudentDataBean();
						if(studentDataBean.getClassName().equalsIgnoreCase(paths.getString("MAHARISHI.CLASS.STDI")) || studentDataBean.getClassName().equalsIgnoreCase(paths.getString("MAHARISHI.CLASS.STDII"))){
							stud2.setFees("ECA Fee");
							stud2.setAmount(studentDataBean.getFeesList().get(0).getExtraFee1());
							studentDataBean.getFeesregList().add(stud2);
						} 
						else if(studentDataBean.getClassName().equalsIgnoreCase(paths.getString("MAHARISHI.CLASS.STDIII")) || studentDataBean.getClassName().equalsIgnoreCase(paths.getString("MAHARISHI.CLASS.STDIV")) ||
								studentDataBean.getClassName().equalsIgnoreCase(paths.getString("MAHARISHI.CLASS.STDV"))){
						stud2.setFees("ECA Fee");
						stud2.setAmount(studentDataBean.getFeesList().get(0).getExtraFee1());
						studentDataBean.getFeesregList().add(stud2);
						
						StudentDataBean stud3=new StudentDataBean();
						stud3.setFees("Abacus Fees");
						stud3.setAmount(studentDataBean.getFeesList().get(0).getExtraFee2());
						studentDataBean.getFeesregList().add(stud3);
						}
						studentDataBean.setInvoiceNumber(studentDataBean.getFeesList().get(0).getInvoiceNumber());
						studentDataBean.setTodayDate(d);
					}
					else if (studentDataBean.getSchool_id()==Integer.parseInt(paths.getString("SMRV.SCHOOLID"))){
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('feesInvoicedlg').show();");
						reqcontext.execute("PF('paymentdlg').hide();");
						reqcontext.update("XX:feesInvoicedlg");
						feesdetailstab();
						studentDataBean.setTuitionfees(studentDataBean.getFeesList().get(0).getTuitionfees());
						
						StudentDataBean stud=new StudentDataBean();
						
						stud.setFees("Tuition Fee");
						stud.setAmount(studentDataBean.getFeesList().get(0).getTuitionfees());
						studentDataBean.getFeesregList().add(stud);
					
						studentDataBean.setInvoiceNumber(studentDataBean.getFeesList().get(0).getInvoiceNumber());
						studentDataBean.setTodayDate(d);
					}
					else{
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('feesInvoicedlg').show();");
						reqcontext.execute("PF('paymentdlg').hide();");
						reqcontext.update("XX:feesInvoicedlg");
						feesdetailstab();
						logger.debug("pay now----------------------"+studentDataBean.getPaynow());
						studentDataBean.setTuitionfees(studentDataBean.getFeesList().get(0).getTuitionfees());
						studentDataBean.setExamfees(studentDataBean.getFeesList().get(0).getExamfees());
						studentDataBean.setTransfees(studentDataBean.getFeesList().get(0).getTransfees());
						StudentDataBean stud=new StudentDataBean();
						stud.setSno("1");
						stud.setFees("Tuition Fees");
						stud.setAmount(studentDataBean.getFeesList().get(0).getTuitionfees());
						studentDataBean.getFeesregList().add(stud);
						StudentDataBean studd=new StudentDataBean();
						studd.setSno("2");
						studd.setFees("Exam Fees");
						studd.setAmount(studentDataBean.getFeesList().get(0).getExamfees());
						studentDataBean.getFeesregList().add(studd);
						StudentDataBean stud2=new StudentDataBean();
						stud2.setSno("3");
						stud2.setFees("Transport Fees");
						stud2.setAmount(studentDataBean.getFeesList().get(0).getTransfees());
						studentDataBean.getFeesregList().add(stud2);
						studentDataBean.setTodayDate(d);
					}
				}
			
			}
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}finally{
			studentDataBean.setTerm("");
		}
		return "";
	}
	
	public void feesChange(ValueChangeEvent v){
		BigDecimal paid=BigDecimal.valueOf(0);
		BigDecimal balance=BigDecimal.valueOf(0);
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		try{
			logger.debug("-----feesChange-----"+studentDataBean.getPaidAmount1());
			String paynow=v.getNewValue().toString();
			paid=new BigDecimal(studentDataBean.getPaidAmount1()).add(new BigDecimal(paynow));
			balance=new BigDecimal(studentDataBean.getTotalAmount()).subtract(paid);
			if(balance.compareTo(BigDecimal.ZERO)==0){
				studentDataBean.setPaymentStatus("Paid");
			}else if(balance.compareTo(BigDecimal.ZERO)>0){
				studentDataBean.setPaymentStatus("Partially Paid");
			}
			else if(balance.compareTo(new BigDecimal(paynow))== 1){
				fieldName = CommonValidate.findComponentInRoot("aact").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.exceedamount")));
			}
			studentDataBean.setPaidAmount(String.valueOf(paid));
			studentDataBean.setBalanceAmount(String.valueOf(balance));
			studentDataBean.setPaynow(paynow);
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
	}
	
	public String attendanceTab(){

		int monthCount=0;SmsController controller = null;
		int year = 0;//Calendar.getInstance().get(Calendar.YEAR);
		int loopcount=0;
		AttendanceDataBean attendance=null;
		try{
			year = Calendar.getInstance().get(Calendar.YEAR);
			attendanceDataBean.setAttendanceList(new ArrayList<AttendanceDataBean>());
			String selectMonth="0"+((d.getMonth()+1));// What is this ? it's for getting current month with 0 (first letter)
			logger.info("selectMonth ------- "+selectMonth);
			attendanceDataBean.setSelectedmonth(selectMonth);
			if(selectMonth.equals("01") || selectMonth.equals("03") || selectMonth.equals("05") || selectMonth.equals("07")||selectMonth.equals("08")||selectMonth.equals("10")||selectMonth.equals("12")){
				monthCount=31;
			}else if(selectMonth.equals("02")){
				monthCount=29;
			}
			else if(selectMonth.equals("04")||selectMonth.equals("06")||selectMonth.equals("09")||selectMonth.equals("11")){
				monthCount=30;
			}
			attendanceDataBean.setDyear(year);
			attendanceDataBean.setDmonth(Integer.parseInt(selectMonth));
			String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			attendanceDataBean.setSchoolID(Integer.parseInt(schoolID));
			attendanceDataBean.setStudentID(studentDataBean.getStuRollNo());
			attendanceDataBean.setClassname(studentDataBean.getStuCls());
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			status=controller.getAttendanceList(attendanceDataBean);
			logger.debug("----------Student Attendance List ---------------------"+attendanceDataBean.getStudentAttendanceList().size());
			if(attendanceDataBean.getStudentAttendanceList().size()>0){
				for (int i = 0; i < monthCount; i++) {
					loopcount=0;
					for (int j = 0; j < attendanceDataBean.getStudentAttendanceList().size(); j++) {
						attendance=new AttendanceDataBean();
						try{
							Date date1=ft.parse((i+1)+"-"+selectMonth+"-"+year);
							Date date2=attendanceDataBean.getStudentAttendanceList().get(j).getAttendance_date();
							if(date1.getDate()==(date2.getDate())){
								logger.info("check if----------- "+i);
								attendance.setMonthDates(ft.parse((i+1)+"-"+selectMonth+"-"+year));
								attendance.setSno(i+1);
								attendance.setInTime(attendanceDataBean.getStudentAttendanceList().get(j).getInTime());
								attendance.setOutTime(attendanceDataBean.getStudentAttendanceList().get(j).getOutTime());
								attendance.setPresentStatus(attendanceDataBean.getStudentAttendanceList().get(j).getPresent_status());
								attendance.setPresentDay(attendanceDataBean.getStudentAttendanceList().get(j).getPresent_day());
								attendance.setInTime1(attendanceDataBean.getStudentAttendanceList().get(j).getInTime());
								attendance.setOutTime1(attendanceDataBean.getStudentAttendanceList().get(j).getOutTime());
								attendance.setPresentStatus1(attendanceDataBean.getStudentAttendanceList().get(j).getPresent_status());
								attendance.setPresentDay1(attendanceDataBean.getStudentAttendanceList().get(j).getPresent_day());
								if(d.getDate()>i)
								{
									attendance.setAttendanceeditFlag(true);
								}else{
									attendance.setAttendanceeditFlag(false);
								}
								attendance.setPresentFlag1(true);
								attendance.setAbsentFlag1(true);
								attendanceDataBean.getAttendanceList().add(attendance);
							}else{
								loopcount=loopcount+1;
								logger.debug(loopcount+"------loop------"+j+"----"+attendanceDataBean.getStudentAttendanceList().size());
								if(loopcount==attendanceDataBean.getStudentAttendanceList().size()){
									logger.info("leave -------------------");
									attendance.setMonthDates(ft.parse((i+1)+"-"+selectMonth+"-"+year));
									attendance.setSno(i+1);
									attendance.setInTime(null);
									attendance.setOutTime(null);
									attendance.setPresentStatus("");
									attendance.setPresentDay("");
									attendance.setInTime1(null);
									attendance.setOutTime1(null);
									attendance.setPresentStatus1("");
									attendance.setPresentDay("");
									if(d.getDate()>i)
									{
										attendance.setAttendanceeditFlag(true);
									}else{
										attendance.setAttendanceeditFlag(false);
									}
									attendance.setPresentFlag1(true);
									attendance.setAbsentFlag1(true);
									attendanceDataBean.getAttendanceList().add(attendance);
									j--;
								}
							}
						}catch (Exception e) {
							attendance.setMonthDates(ft.parse((i+1)+"-"+selectMonth+"-"+year));
							attendance.setSno(i+1);
							attendance.setInTime(null);
							attendance.setOutTime(null);
							attendance.setPresentStatus("");
							attendance.setPresentDay("");
							attendance.setInTime1(null);
							attendance.setOutTime1(null);
							attendance.setPresentStatus1("");
							attendance.setPresentDay("");
							if(d.getDate()>i)
							{
								attendance.setAttendanceeditFlag(true);
							}else{
								attendance.setAttendanceeditFlag(false);
							}
							attendanceDataBean.getAttendanceList().add(attendance);
							logger.error("Exception -->"+e.getMessage());
						}
						
					}
				}
			}else{
				for (int i = 0; i < monthCount; i++) {
					attendance=new AttendanceDataBean();
					attendance.setMonthDates(ft.parse((i+1)+"-"+selectMonth+"-"+year));
					attendance.setSno(i+1);
					attendance.setInTime(null);
					attendance.setOutTime(null);
					attendance.setPresentStatus("");
					attendance.setPresentDay("");
					attendance.setInTime1(null);
					attendance.setOutTime1(null);
					attendance.setPresentStatus1("");
					attendance.setPresentDay("");
					if(d.getDate()>i)
					{
						attendance.setAttendanceeditFlag(true);
					}else{
						attendance.setAttendanceeditFlag(false);
					}
					attendance.setPresentFlag1(true);
					attendance.setAbsentFlag1(true);
					attendanceDataBean.getAttendanceList().add(attendance);
				}
			}
			attendanceDataBean.setAttendanceFlag(true);
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		finally
		{
			if(attendance!=null){
				attendance=null;
			}
			if(controller!=null){
				controller=null;
			}
		}
		return "";
	
	}
	
	public void monthlyAttendance(ValueChangeEvent v) {
		int monthCount=0;SmsController controller = null;
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int loopcount=0;
		try{
			attendanceDataBean.setAttendanceList(new ArrayList<AttendanceDataBean>());
			String selectMonth=v.getNewValue().toString();
			if(selectMonth.equals("01") || selectMonth.equals("03") || selectMonth.equals("05") || selectMonth.equals("07")||selectMonth.equals("08")||selectMonth.equals("10")||selectMonth.equals("12")){
				monthCount=31;
			}else if(selectMonth.equals("02")){
				monthCount=29;
			}
			else if(selectMonth.equals("04")||selectMonth.equals("06")||selectMonth.equals("09")||selectMonth.equals("11")){
				monthCount=30;
			}
			attendanceDataBean.setDyear(year);
			attendanceDataBean.setDmonth(Integer.parseInt(selectMonth));
			String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			attendanceDataBean.setSchoolID(Integer.parseInt(schoolID));
			attendanceDataBean.setStudentID(studentDataBean.getStuRollNo());
			attendanceDataBean.setClassname(studentDataBean.getStuCls());
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			status=controller.getAttendanceList(attendanceDataBean);
			logger.info("----------test---------------------"+attendanceDataBean.getStudentAttendanceList().size());
			if(attendanceDataBean.getStudentAttendanceList().size()>0){
				for (int i = 0; i < monthCount; i++) {
					loopcount=0;
					for (int j = 0; j < attendanceDataBean.getStudentAttendanceList().size(); j++) {
						AttendanceDataBean attendance=new AttendanceDataBean();
						try{
							Date date1=ft.parse((i+1)+"-"+selectMonth+"-"+year);
							Date date2=attendanceDataBean.getStudentAttendanceList().get(j).getAttendance_date();
							if(date1.getDate()==(date2.getDate())){
								logger.info("check if----------- "+i);
								attendance.setMonthDates(ft.parse((i+1)+"-"+selectMonth+"-"+year));
								attendance.setInTime(attendanceDataBean.getStudentAttendanceList().get(j).getInTime());
								attendance.setOutTime(attendanceDataBean.getStudentAttendanceList().get(j).getOutTime());
								attendance.setPresentStatus(attendanceDataBean.getStudentAttendanceList().get(j).getPresent_status());
								attendance.setPresentDay(attendanceDataBean.getStudentAttendanceList().get(j).getPresent_day());
								attendance.setInTime1(attendanceDataBean.getStudentAttendanceList().get(j).getInTime());
								attendance.setOutTime1(attendanceDataBean.getStudentAttendanceList().get(j).getOutTime());
								attendance.setPresentStatus1(attendanceDataBean.getStudentAttendanceList().get(j).getPresent_status());
								attendance.setPresentDay1(attendanceDataBean.getStudentAttendanceList().get(j).getPresent_day());
								logger.info("---1---"+Integer.parseInt(selectMonth)+"----2----"+d.getMonth());
								if(Integer.parseInt(selectMonth)==(d.getMonth()+1)){
									if(d.getDate()>i)
									{
										attendance.setAttendanceeditFlag(true);
									}else{
										attendance.setAttendanceeditFlag(false);
									}
								}else if(Integer.parseInt(selectMonth)<(d.getMonth()+1)){
									attendance.setAttendanceeditFlag(true);
								}else if(Integer.parseInt(selectMonth)>(d.getMonth()+1)){
									attendance.setAttendanceeditFlag(false);
								}
								attendance.setSno(i+1);
								attendance.setPresentFlag1(true);
								attendance.setAbsentFlag1(true);
								attendanceDataBean.getAttendanceList().add(attendance);
							}else{
								loopcount=loopcount+1;
								logger.info(loopcount+"------loop------"+j+"----"+attendanceDataBean.getStudentAttendanceList().size());
								if(loopcount==attendanceDataBean.getStudentAttendanceList().size()){
									logger.info("leave -------------------");
									attendance.setMonthDates(ft.parse((i+1)+"-"+selectMonth+"-"+year));
									attendance.setSno(i+1);
									attendance.setInTime(null);
									attendance.setOutTime(null);
									attendance.setPresentStatus("");
									attendance.setPresentDay1("");
									attendance.setInTime1(null);
									attendance.setOutTime1(null);
									attendance.setPresentStatus1("");
									attendance.setPresentDay("");
									if(Integer.parseInt(selectMonth)==(d.getMonth()+1)){
										if(d.getDate()>i)
										{
											attendance.setAttendanceeditFlag(true);
										}else{
											attendance.setAttendanceeditFlag(false);
										}
									}else if(Integer.parseInt(selectMonth)<(d.getMonth()+1)){
										attendance.setAttendanceeditFlag(true);
									}else if(Integer.parseInt(selectMonth)>(d.getMonth()+1)){
										attendance.setAttendanceeditFlag(false);
									}
									attendance.setPresentFlag1(true);
									attendance.setAbsentFlag1(true);
									attendanceDataBean.getAttendanceList().add(attendance);
									j--;
								}
							}
						}catch (Exception e) {
							attendance.setMonthDates(ft.parse((i+1)+"-"+selectMonth+"-"+year));
							attendance.setSno(i+1);
							attendance.setInTime(null);
							attendance.setOutTime(null);
							attendance.setPresentStatus("");
							attendance.setPresentDay("");
							attendance.setInTime1(null);
							attendance.setOutTime1(null);
							attendance.setPresentStatus1("");
							attendance.setPresentDay("");
							if(Integer.parseInt(selectMonth)==(d.getMonth()+1)){
								if(d.getDate()>i)
								{
									attendance.setAttendanceeditFlag(true);
								}else{
									attendance.setAttendanceeditFlag(false);
								}
							}else if(Integer.parseInt(selectMonth)<(d.getMonth()+1)){
								attendance.setAttendanceeditFlag(true);
							}else if(Integer.parseInt(selectMonth)>(d.getMonth()+1)){
								attendance.setAttendanceeditFlag(false);
							}
							attendanceDataBean.getAttendanceList().add(attendance);
						}
						
					}
				}
			}else{
				for (int i = 0; i < monthCount; i++) {
					AttendanceDataBean attendance=new AttendanceDataBean();
					attendance.setMonthDates(ft.parse((i+1)+"-"+selectMonth+"-"+year));
					attendance.setSno(i+1);
					attendance.setInTime(null);
					attendance.setOutTime(null);
					attendance.setPresentStatus("");
					attendance.setPresentDay("");
					attendance.setInTime1(null);
					attendance.setOutTime1(null);
					attendance.setPresentStatus1("");
					attendance.setPresentDay("");
					if(Integer.parseInt(selectMonth)==(d.getMonth()+1)){
						if(d.getDate()>i)
						{
							attendance.setAttendanceeditFlag(true);
						}else{
							attendance.setAttendanceeditFlag(false);
						}
					}else if(Integer.parseInt(selectMonth)<(d.getMonth()+1)){
						attendance.setAttendanceeditFlag(true);
					}else if(Integer.parseInt(selectMonth)>(d.getMonth()+1)){
						attendance.setAttendanceeditFlag(false);
					}
					attendance.setPresentFlag1(true);
					attendance.setAbsentFlag1(true);
					attendanceDataBean.getAttendanceList().add(attendance);
				}
			}
			attendanceDataBean.setAttendanceFlag(true);
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
	}
	
	
	
	
	public void presentChange(ValueChangeEvent ve){
		logger.info("inside presentChange -------");
		try{
			String status=ve.getNewValue().toString();
			String no = ve.getComponent().getAttributes().get("sno").toString();
			Date adate =(Date) ve.getComponent().getAttributes().get("adate");
			String intime =ve.getComponent().getAttributes().get("intime").toString();
			String outtime =ve.getComponent().getAttributes().get("outtime").toString();
			String presentDay = ve.getComponent().getAttributes().get("presentDay").toString();
			logger.info("sno  -----------"+no);
			AttendanceDataBean atdnc=new AttendanceDataBean();
			
			if(status.equalsIgnoreCase("Present")){
				logger.info("if------------");
				atdnc.setPresentFlag(true);
				atdnc.setAbsentFlag(true);
				atdnc.setPresentDay(presentDay);
				atdnc.setInTime(intime);
				atdnc.setOutTime(outtime);
			}else{
				logger.info("else------------");
				atdnc.setPresentFlag(false);
				atdnc.setAbsentFlag(true);
				atdnc.setPresentDay("");
				atdnc.setInTime(null);
				atdnc.setOutTime(null);
			}
			atdnc.setPresentStatus(status);
			atdnc.setMonthDates(adate);
			atdnc.setAttendanceeditFlag(true);
			atdnc.setSno(Integer.parseInt(no));
			attendanceDataBean.getAttendanceList().set(Integer.parseInt(no) - 1,atdnc);
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
	}
	
	public String attendanceEdit(AttendanceDataBean attendance){
		try{
			if(attendance.getPresentStatus().equalsIgnoreCase("Absent")){
				attendance.setPresentFlag(false);
				attendance.setAbsentFlag(true);
			}else{
				attendance.setPresentFlag(true);
				attendance.setAbsentFlag(true);
			}
			attendance.setPresentFlag1(false);
			attendance.setAbsentFlag1(false);
			attendanceDataBean.getAttendanceList().set(attendance.getSno() - 1,attendance);
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return "";
	}
	
	public String updateAttendance(AttendanceDataBean attendance){
		String status="";SmsController controller = null;
		try{
			attendanceDataBean.setMonthDates(attendance.getMonthDates());
			attendanceDataBean.setPresentStatus(attendance.getPresentStatus());
			attendanceDataBean.setOutTime(attendance.getOutTime());
			attendanceDataBean.setInTime(attendance.getInTime());
			attendanceDataBean.setPresentDay(attendance.getPresentDay());
			attendanceDataBean.setStudentID(studentDataBean.getStuRollNo());
			attendanceDataBean.setClassname(studentDataBean.getStuCls());
			String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			attendanceDataBean.setSchoolID(Integer.parseInt(schoolID));
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			status=controller.updateAttendance(attendanceDataBean);
			logger.info("status ----------"+status);
			if(status.equalsIgnoreCase("update")){
				RequestContext reqcontext = RequestContext.getCurrentInstance();
				reqcontext.execute("PF('attendanceUpdatedlg').show();");
			}else if(status.equalsIgnoreCase("save")){
				RequestContext reqcontext = RequestContext.getCurrentInstance();
				reqcontext.execute("PF('attendanceSavedlg').show();");
			}
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return "";
	}
	
	public String cancelAttendance(AttendanceDataBean attendance){
		logger.info("----------cancelAttendance---------------");
		attendance.setPresentFlag1(true);
		attendance.setAbsentFlag1(true);
		attendance.setPresentFlag(false);
		attendance.setAbsentFlag(false);
		attendance.setPresentDay(attendance.getPresentDay1());
		attendance.setPresentStatus(attendance.getPresentStatus1());
		attendance.setInTime(attendance.getInTime1());
		attendance.setOutTime(attendance.getOutTime1());
		attendanceDataBean.getAttendanceList().set(attendance.getSno() - 1,attendance);
		return "";
	}
	
	//08-11-2017
	
private List<ReportCardDataBean> studentClassList = null;
	
	public List<ReportCardDataBean> getStudentClassList() {
		return studentClassList;
	}

	public void setStudentClassList(List<ReportCardDataBean> studentClassList) {
		this.studentClassList = studentClassList;
	}
	private String setTableValues(ReportCardDataBean reportCardDataBean) {
		String status = "Fail";
		try {
		if (reportCardDataBean.getResultStatus().equalsIgnoreCase("NA")) {
			ReportCardDataBean rp = new ReportCardDataBean();
			rp.setGrade("");
			rp.setResultStatus("");
			rp.setMarkSubTitle(reportCardDataBean.getMarkSubTitle());
			rp.setExamMarkTitle(reportCardDataBean.getExamMarkTitle());
			rp.setMark("");
			rp.setUpflag(false);
			rp.setDownflag(false);
			studentMarkList.set(Integer.parseInt(reportCardDataBean.getsNo()), rp);

			FacesMessage msg = new FacesMessage("Please Enter Valid Mark", reportCardDataBean.getsNo());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			ReportCardDataBean rp = new ReportCardDataBean();
			rp.setGrade(reportCardDataBean.getGrade());
			
			rp.setResultStatus(reportCardDataBean.getResultStatus());
			rp.setMarkSubTitle(reportCardDataBean.getMarkSubTitle());
			rp.setExamMarkTitle(reportCardDataBean.getExamMarkTitle());
			rp.setMark(reportCardDataBean.getMark());
			studentMarkList.set(Integer.parseInt(reportCardDataBean.getsNo()), rp);
			
			status = "Success";
		}
		} catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
		return status;
	}	
	public void onRowEditMarkTable(RowEditEvent event) {
		SmsController controller = null;
		String mark = "";
		String status = "Fail";
		String serial = "";
		String subject= "";
		String examname = "";
		logger.debug("----------- onRowEditMarkTable -------------");
		try {
			DataTable index=(DataTable) event.getSource();
			serial=String.valueOf(index.getRowIndex());
			ApplicationContext ctxs = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctxs.getBean("controller");
			String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			reportCardDataBean.setsNo("");
			reportCardDataBean.setRollNo("");
			reportCardDataBean.setMark("");
			reportCardDataBean.setResultStatus("");
			reportCardDataBean.setGrade("");
			reportCardDataBean.setMarkSubTitle("");
			
			mark = ((ReportCardDataBean) event.getObject()).getMark().toString();
			subject =((ReportCardDataBean) event.getObject()).getMarkSubTitle().toString();
			examname = ((ReportCardDataBean) event.getObject()).getExamMarkTitle().toString();
			
			reportCardDataBean.setMark(mark);
			reportCardDataBean.setRollNo(studentDataBean.getStuRollNo());
			reportCardDataBean.setMarkSubTitle(subject);
			reportCardDataBean.setExamMarkTitle(examname);
			reportCardDataBean.setsNo(serial);
			
			logger.debug("mark ----->"+mark);
			if (mark == null || mark.equals(null) || mark.equals("")) {
				FacesMessage msg = new FacesMessage("Please Add Mark");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}else{
				if (mark.equalsIgnoreCase(text.getString("stumosys.absent"))) {
					reportCardDataBean.setMark(mark);
					reportCardDataBean.setResultStatus("Absent");
					reportCardDataBean.setGrade(text.getString("stumosys.absentGrade"));
					status = setTableValues(reportCardDataBean);
				} else {
					try {
						logger.debug("Exam Id--->"+reportCardDataBean.getExamID());
						controller.getexammarkgradeGlobal(reportCardDataBean);
						logger.debug("----grad---->"+reportCardDataBean.getGrade());
						if(reportCardDataBean.getGrade().equalsIgnoreCase(text.getString("stumosys.failGrade1"))||
								reportCardDataBean.getGrade().equalsIgnoreCase(text.getString("stumosys.failGrade2"))||
										reportCardDataBean.getGrade().equalsIgnoreCase(text.getString("stumosys.failGrade3"))||
										reportCardDataBean.getGrade().equalsIgnoreCase(text.getString("stumosys.failGrade4"))){
							//reportCardDataBean.setResultStatus("Fail");
							status = setTableValues(reportCardDataBean);
							logger.debug("----ResultStatus----->"+reportCardDataBean.getResultStatus());
						}else{
							//reportCardDataBean.setResultStatus("Pass");
							status = setTableValues(reportCardDataBean);
							logger.debug("----ResultStatus------>"+reportCardDataBean.getResultStatus());
						}
					}catch (NumberFormatException nex) {
						nex.printStackTrace();
						ReportCardDataBean rp = new ReportCardDataBean();
						rp.setGrade("");
						rp.setsNo("");
						rp.setResultStatus("");
						rp.setMark("");
						studentClassList.set((Integer.parseInt(reportCardDataBean.getsNo())), rp);

						FacesMessage msg = new FacesMessage("Please Enter Valid Mark", reportCardDataBean.getRollNo());
						FacesContext.getCurrentInstance().addMessage(null, msg);
							
					}
				}
			}
			if (status.equalsIgnoreCase("Success")) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
				if (personID != null) {
					String reportStatus = controller.addReportCardGlobal(personID, reportCardDataBean);
					if (reportStatus.equalsIgnoreCase("Success")) {
						FacesMessage msg1 = new FacesMessage("Please wait..", reportCardDataBean.getRollNo());
						FacesContext.getCurrentInstance().addMessage(null, msg1);
						MailSendJDBC.addReportCard(reportCardDataBean,schoolName,schoolid);
							FacesMessage msg = new FacesMessage("Sucessfully Saved mark",
									reportCardDataBean.getRollNo());
							FacesContext.getCurrentInstance().addMessage(null, msg);
					} else if (reportStatus.equalsIgnoreCase("Edit")) {
						FacesMessage msg1 = new FacesMessage("Please wait..", reportCardDataBean.getRollNo());
						FacesContext.getCurrentInstance().addMessage(null, msg1);
						MailSendJDBC.addReportCard(reportCardDataBean,schoolName,schoolid);
							FacesMessage msg = new FacesMessage("Sucessfully updated mark",
									reportCardDataBean.getRollNo());
							FacesContext.getCurrentInstance().addMessage(null, msg);
					} else {
						FacesMessage msg = new FacesMessage("Network Problem.Please try again..",
								reportCardDataBean.getRollNo());
						FacesContext.getCurrentInstance().addMessage(null, msg);
					}
				}
			}
		} catch (NullPointerException e1) {
			e1.printStackTrace();
			logger.error("Exception -->"+e1.getMessage());
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			e.printStackTrace();
		}
	}
	public String getmarkdetails(){
		logger.debug("inside getmark details");
		SmsController controller = null;
		try{
			String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String classname=controller.getStudentClass(studentDataBean.getStuRollNo(),personID);
			studentMarkList = new ArrayList<ReportCardDataBean>();
			if (personID != null) {
					reportCardDataBean.setStudMarkClass(classname);
					reportCardDataBean.setRollNo(studentDataBean.getStuRollNo());
					studentMarkList = controller.getglobalsearchMarkList(schoolID,personID,reportCardDataBean);
					logger.debug("studentmarklist size-------->"+studentMarkList.size());
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Exception -->"+e.getMessage());
		}
		return "";
	}
	public void monthlyMarks(ValueChangeEvent vc){
		logger.debug("inside monthlymarks");
		SmsController controller = null;
		try{
			String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			String personID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			examTableList=new ArrayList<ExamTable>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String classname=controller.getStudentClass(studentDataBean.getStuRollNo(),personID);
			examTableList=controller.getglobalSearchTimeTableList(schoolID,personID,classname,vc.getNewValue().toString());
			logger.debug("inside if----"+examTableList.size());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Exception -->"+e.getMessage());
		}
	}
	
	public void studentmultiDelete() {
		logger.debug("-----inside student Delete--------");
		setBoxflag(false);
		setDelBoxflag(false);
		SmsController controller = null;
		if (selectedstudentlist.size()>0) {
			try {
				System.out.println("selectedstudent size --------------"+selectedstudentlist.size());
				for (int i = 0; i < selectedstudentlist.size(); i++) {
					studentDataBean.setStuRollNo(selectedstudentlist.get(i).getStuRollNo());
					String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
							.get("LogID");
					ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
					controller = (SmsController) ctx.getBean("controller");
					status = controller.getStudentDelete(personID, studentDataBean);
					if (status.equalsIgnoreCase("Success")) {
						setDelBoxflag(true);
					}
				}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
		}

	}
	
	private void gettimelist() {
		try{
			studentDataBean.setTimeList(new ArrayList<String>());
			studentDataBean.getTimeList().addAll(Arrays.asList("12:00 AM","12:15 AM","12:30 AM","12:45 AM","01:00 AM","01:15 AM","01:30 AM","01:45 AM","02:00 AM","02:15 AM","02:30 AM","02:45 AM",
					"03:00 AM","03:15 AM","03:30 AM","03:45 AM","04:00 AM","04:15 AM","04:30 AM","04:45 AM","05:00 AM","05:15 AM","05:30 AM","05:45 AM","06:00 AM","06:15 AM","06:30 AM",
					"06:45 AM","07:00 AM","07:15 AM","07:30 AM","07:45 AM","08:00 AM","08:15 AM","08:30 AM","08:45 AM","09:00 AM","09:15 AM","09:30 AM","09:45 AM","10:00 AM","10:15 AM","10:30 AM",
					"10:45 AM","11:00 AM","11:15 AM","11:30 AM","11:45 AM","12:00 PM","12:15 PM","12:30 PM","12:45 PM","01:00 PM","01:15 PM","01:30 PM","01:45 PM","02:00 PM","02:15 PM","02:30 PM",
					"02:45 PM","03:00 PM","03:15 PM","03:30 PM","03:45 PM","04:00 PM","04:15 PM","04:30 PM","04:45 PM","05:00 PM","05:15 PM","05:30 PM","05:45 PM","06:00 PM","06:15 PM",
					"06:30 PM","06:45 PM","07:00 PM","07:15 PM","07:30 PM","07:45 PM","08:00 PM","08:15 PM","08:30 PM","08:45 PM","09:00 PM","09:15 PM","09:30 PM","09:45 PM","10:00 PM","10:15 PM",
					"10:30 PM","10:45 PM","11:00 PM","11:15 PM","11:30 PM","11:45 PM"));
			
		}catch (Exception e) {
			//e.printStackTrace();
logger.warn("Exception -->"+e.getMessage());
		}
		
	}
	//john 08-23-2017 Community Information
	private String communitySearchType;
	
	public String getCommunitySearchType() {
		return communitySearchType;
	}

	public void setCommunitySearchType(String communitySearchType) {
		this.communitySearchType = communitySearchType;
	}

	public String studentCommunitypage() {
		logger.debug("-----inside student Community page--------");
		try {
			setCommunitySearchType("");
			studentDataBean.setClassName("");
		} catch (Exception e){
			logger.error("Exception -->"+e.getMessage());
			logger.warn("Exception -->"+e.getMessage());
			e.printStackTrace();
		}
		return "communityInformation";

	}
	
	private List<StudentViewMB> communitydetail=null;
	private Boolean feesdetailFlag=true;
	
	public Boolean getFeesdetailFlag() {
		return feesdetailFlag;
	}

	public void setFeesdetailFlag(Boolean feesdetailFlag) {
		this.feesdetailFlag = feesdetailFlag;
	}

	public List<StudentViewMB> getCommunitydetail() {
		return communitydetail;
	}

	public void setCommunitydetail(List<StudentViewMB> communitydetail) {
		this.communitydetail = communitydetail;
	}
	
	private String religion;
	
	private int OCB;
	private int OCG;
	private int OCT;
	
	private int BCB;
	private int BCG;
	private int BCT;
	
	private int BCMB;
	private int BCMG;
	private int BCMT;
	
	private int MBCB;
	private int MBCG;
	private int MBCT;
	
	private int SCB;
	private int SCG;
	private int SCT;
	
	private int SSB;
	private int SSG;
	private int SST;
	
	private int STB;
	private int STG;
	private int STT;
	
	private int TTB;
	private int TTG;
	private int TTT;
	
/*	private HashMap<Integer, StudentViewMB> communityHmap = new HashMap<Integer, StudentViewMB>();
	 
	
	
	public HashMap<Integer, StudentViewMB> getCommunityHmap() {
		return communityHmap;
	}

	public void setCommunityHmap(HashMap<Integer, StudentViewMB> communityHmap) {
		this.communityHmap = communityHmap;
	}*/

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public int getOCB() {
		return OCB;
	}

	public void setOCB(int oCB) {
		OCB = oCB;
	}

	public int getOCG() {
		return OCG;
	}

	public void setOCG(int oCG) {
		OCG = oCG;
	}

	public int getOCT() {
		return OCT;
	}

	public void setOCT(int oCT) {
		OCT = oCT;
	}

	public int getBCB() {
		return BCB;
	}

	public void setBCB(int bCB) {
		BCB = bCB;
	}

	public int getBCG() {
		return BCG;
	}

	public void setBCG(int bCG) {
		BCG = bCG;
	}

	public int getBCT() {
		return BCT;
	}

	public void setBCT(int bCT) {
		BCT = bCT;
	}

	public int getBCMB() {
		return BCMB;
	}

	public void setBCMB(int bCMB) {
		BCMB = bCMB;
	}

	public int getBCMG() {
		return BCMG;
	}

	public void setBCMG(int bCMG) {
		BCMG = bCMG;
	}

	public int getBCMT() {
		return BCMT;
	}

	public void setBCMT(int bCMT) {
		BCMT = bCMT;
	}

	public int getMBCB() {
		return MBCB;
	}

	public void setMBCB(int mBCB) {
		MBCB = mBCB;
	}

	public int getMBCG() {
		return MBCG;
	}

	public void setMBCG(int mBCG) {
		MBCG = mBCG;
	}

	public int getMBCT() {
		return MBCT;
	}

	public void setMBCT(int mBCT) {
		MBCT = mBCT;
	}

	public int getSCB() {
		return SCB;
	}

	public void setSCB(int sCB) {
		SCB = sCB;
	}

	public int getSCG() {
		return SCG;
	}

	public void setSCG(int sCG) {
		SCG = sCG;
	}

	public int getSCT() {
		return SCT;
	}

	public void setSCT(int sCT) {
		SCT = sCT;
	}

	public int getSSB() {
		return SSB;
	}

	public void setSSB(int sSB) {
		SSB = sSB;
	}

	public int getSSG() {
		return SSG;
	}

	public void setSSG(int sSG) {
		SSG = sSG;
	}

	public int getSST() {
		return SST;
	}

	public void setSST(int sST) {
		SST = sST;
	}

	public int getSTB() {
		return STB;
	}

	public void setSTB(int sTB) {
		STB = sTB;
	}

	public int getSTG() {
		return STG;
	}

	public void setSTG(int sTG) {
		STG = sTG;
	}

	public int getSTT() {
		return STT;
	}

	public void setSTT(int sTT) {
		STT = sTT;
	}

	public int getTTB() {
		return TTB;
	}

	public void setTTB(int tTB) {
		TTB = tTB;
	}

	public int getTTG() {
		return TTG;
	}

	public void setTTG(int tTG) {
		TTG = tTG;
	}

	public int getTTT() {
		return TTT;
	}

	public void setTTT(int tTT) {
		TTT = tTT;
	}
	/*public boolean communityValidate(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();

		if (StringUtils.isEmpty(studentDataBean.getStuFirstName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("sFName").getClientId(fc);
				//fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.ftname")));
				fc.addMessage(fieldName, new FacesMessage(text.getString("stumosys.student.ftname")));				
			}
			valid = false;
		} else if (!StringUtils.isEmpty(studentDataBean.getStuFirstName())) {
			if (studentDataBean.getStuFirstName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vftname")));
				}
				valid = false;
			} else if (!CommonValidate.validateName(studentDataBean.getStuFirstName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("sFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.student.vftname")));
				}
				valid = false;
			}
		}
		return valid;
	}*/
	public String communitySearch() {
		logger.debug("-----inside student communitySearch --------");
		System.out.println("------inside student communitySearch-----------------");
		SmsController controller = null;
		String personID = "";
		String schoolID = "";
		List<StudentDataBean> studentCommunityDetails=null;
		try {
			studentCommunityDetails=new ArrayList<StudentDataBean>();
			schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			personID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			studentDataBean.setSchool_id(Integer.parseInt(schoolID));
			studentDataBean.setPersonID(personID);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			if (studentDataBean.getClassName().equalsIgnoreCase("Middle School Class")) {
				studentDataBean.setClassName(paths.getString("SMRV.CLASS.STDVI"));
				studentCommunityDetails.addAll(controller.getStudentCommunitryDetail(communitySearchType, studentDataBean));
				studentDataBean.setClassName(paths.getString("SMRV.CLASS.STDVII"));
				studentCommunityDetails.addAll(controller.getStudentCommunitryDetail(communitySearchType, studentDataBean));
				studentDataBean.setClassName(paths.getString("SMRV.CLASS.STDVIII"));
				studentCommunityDetails.addAll(controller.getStudentCommunitryDetail(communitySearchType, studentDataBean));
			}
			else if (studentDataBean.getClassName().equalsIgnoreCase("High School Class")) {
				studentDataBean.setClassName(paths.getString("SMRV.CLASS.STDIX"));
				studentCommunityDetails.addAll(controller.getStudentCommunitryDetail(communitySearchType, studentDataBean));
				studentDataBean.setClassName(paths.getString("SMRV.CLASS.STDX"));
				studentCommunityDetails.addAll(controller.getStudentCommunitryDetail(communitySearchType, studentDataBean));
			}
			else if (studentDataBean.getClassName().equalsIgnoreCase("Higher Secondary School Class")) {
				studentDataBean.setClassName(paths.getString("SMRV.CLASS.STDXI"));
				studentCommunityDetails.addAll(controller.getStudentCommunitryDetail(communitySearchType, studentDataBean));
				studentDataBean.setClassName(paths.getString("SMRV.CLASS.STDXII"));
				studentCommunityDetails.addAll(controller.getStudentCommunitryDetail(communitySearchType, studentDataBean));
			}
			else{
			studentCommunityDetails=controller.getStudentCommunitryDetail(communitySearchType, studentDataBean);
			}
			communitydetail=new ArrayList<StudentViewMB>();
			logger.debug("size of list community detail -------->"+studentCommunityDetails.size());
			for (int j = 0; j < 5; j++) {
			StudentViewMB communitytempobj= new StudentViewMB();
			communitytempobj.setReligion(paths.getString("SMRV.RELIGION."+(j+1)));
			for (int i = 0; i < studentCommunityDetails.size(); i++) {
				if (studentCommunityDetails.get(i).getReligion().equalsIgnoreCase(paths.getString("SMRV.RELIGION."+(j+1))) || 
						paths.getString("SMRV.RELIGION."+(j+1)).equalsIgnoreCase("Total")) {
					if (studentCommunityDetails.get(i).getClassification().equalsIgnoreCase("OC")) {
						if (studentCommunityDetails.get(i).getStuGender().equalsIgnoreCase("Male")) {
							communitytempobj.setOCB(communitytempobj.getOCB()+1);
							}
						else if (studentCommunityDetails.get(i).getStuGender().equalsIgnoreCase("Female")) {
							communitytempobj.setOCG(communitytempobj.getOCG()+1);
							}
						communitytempobj.setOCT(communitytempobj.getOCT()+1);
						}
					
					
					if (studentCommunityDetails.get(i).getClassification().equalsIgnoreCase("BC")) {
						if (studentCommunityDetails.get(i).getStuGender().equalsIgnoreCase("Male")) {
							communitytempobj.setBCB(communitytempobj.getBCB()+1);
							}
						else if (studentCommunityDetails.get(i).getStuGender().equalsIgnoreCase("Female")) {
							communitytempobj.setBCG(communitytempobj.getBCG()+1);
							}
						communitytempobj.setBCT(communitytempobj.getBCT()+1);
						}
					
					if (studentCommunityDetails.get(i).getClassification().equalsIgnoreCase("BCM")) {
						if (studentCommunityDetails.get(i).getStuGender().equalsIgnoreCase("Male")) {
							communitytempobj.setBCMB(communitytempobj.getBCMB()+1);
							}
						else if (studentCommunityDetails.get(i).getStuGender().equalsIgnoreCase("Female")) {
							communitytempobj.setBCMG(communitytempobj.getBCMG()+1);
							}
						communitytempobj.setBCMT(communitytempobj.getBCMT()+1);
						}
					
					if (studentCommunityDetails.get(i).getClassification().equalsIgnoreCase("MBC")) {
						if (studentCommunityDetails.get(i).getStuGender().equalsIgnoreCase("Male")) {
							communitytempobj.setMBCB(communitytempobj.getMBCB()+1);
							}
						else if (studentCommunityDetails.get(i).getStuGender().equalsIgnoreCase("Female")) {
							communitytempobj.setMBCG(communitytempobj.getMBCG()+1);
							}
						communitytempobj.setMBCT(communitytempobj.getMBCT()+1);
						}
					
					if (studentCommunityDetails.get(i).getClassification().equalsIgnoreCase("SC")) {
						if (studentCommunityDetails.get(i).getStuGender().equalsIgnoreCase("Male")) {
							communitytempobj.setSCB(communitytempobj.getSCB()+1);
							}
						else if (studentCommunityDetails.get(i).getStuGender().equalsIgnoreCase("Female")) {
							communitytempobj.setSCG(communitytempobj.getSCG()+1);
							}
						communitytempobj.setSCT(communitytempobj.getSCT()+1);
						}
					
					if (studentCommunityDetails.get(i).getClassification().equalsIgnoreCase("SS")) {
						if (studentCommunityDetails.get(i).getStuGender().equalsIgnoreCase("Male")) {
							communitytempobj.setSSB(communitytempobj.getSSB()+1);
							}
						else if (studentCommunityDetails.get(i).getStuGender().equalsIgnoreCase("Female")) {
							communitytempobj.setSSG(communitytempobj.getSSG()+1);
							}
						communitytempobj.setSST(communitytempobj.getSST()+1);
						}
					
					if (studentCommunityDetails.get(i).getClassification().equalsIgnoreCase("ST/DNC")) {
						if (studentCommunityDetails.get(i).getStuGender().equalsIgnoreCase("Male")) {
							communitytempobj.setSTB(communitytempobj.getSTB()+1);
							}
						else if (studentCommunityDetails.get(i).getStuGender().equalsIgnoreCase("Female")) {
							communitytempobj.setSTG(communitytempobj.getSTG()+1);
							}
						communitytempobj.setSTT(communitytempobj.getSTT()+1);
						}
			if (studentCommunityDetails.get(i).getStuGender().equalsIgnoreCase("Male")) {
				communitytempobj.setTTB(communitytempobj.getTTB()+1);
				}
			else if (studentCommunityDetails.get(i).getStuGender().equalsIgnoreCase("Female")) {
				communitytempobj.setTTG(communitytempobj.getTTG()+1);
				}
				}
			}
			communitytempobj.setTTT(communitytempobj.getTTB()+communitytempobj.getTTG());
			communitydetail.add(communitytempobj);
			}
		} catch (Exception e){
			logger.error("Exception -->"+e.getMessage());
			logger.warn("Exception -->"+e.getMessage());
			e.printStackTrace();
		}
		return "communityInformation";

	}
	
	public void communitySearchTypes(ValueChangeEvent vc){
		logger.debug("inside communitySearchTypes");
		SmsController controller = null;
		List<String>temp =new ArrayList<String>();
		try{
			studentDataBean.setClassName("");
			String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			String personID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			teaClass=new ArrayList<String>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			if (vc.getNewValue().toString().equalsIgnoreCase("Class Wise")) {
				teaClass = controller.getTeaClass(personID, studentDataBean);
				for (int i = 0; i < teaClass.size(); i++) {
					temp.add(i,teaClass.get(i).split("/")[0]);
				}
				teaClass= new ArrayList<String>();
				temp = new ArrayList<String>(new LinkedHashSet<String>(temp));
				teaClass.addAll(temp);
				Collections.sort(teaClass);
			}
			else if(vc.getNewValue().toString().equalsIgnoreCase("Section Wise")){
				teaClass = controller.getTeaClass(personID, studentDataBean);
				Collections.sort(teaClass);
			}
			else if(vc.getNewValue().toString().equalsIgnoreCase("Year Wise")){
				teaClass.add("Middle School Class");
				teaClass.add("High School Class");
				teaClass.add("Higher Secondary School Class");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Exception -->"+e.getMessage());
		}
	}
	public String classchangepage() {
		logger.debug("------studentinfo method calling--------");
		SmsController controller = null;
		setBoxflag(false);
		setFlag2(false);
		studentDataBean.setTeaclssection(null);
		studentDataBean.setTeaClassList(null);
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				status = controller.getRoll(personID);
				logger.debug("-----status----" + status);
					setFlag1(true);
					setSflag(false);
					setFlag2(false);
					teaClass = controller.getTeaClass(personID, studentDataBean);
					Collections.sort(teaClass);
					logger.debug("class -- " + teaClass);
				studentDataBean.setReturnStatus(status);
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "classchange";
	}
	// --------------- Class section empty validation check for UI ------------
	private boolean validates(boolean valid) {
		logger.info("-----------Inside validates method()----------------");
		valid=true;
		if (StringUtils.isEmpty(studentDataBean.getTeaclssection())) {
			FacesMessage msg = new FacesMessage("Please Choose the Class Name");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			valid = false;
		} 
		return valid;
	}
public void updateclass(RowEditEvent event){
	logger.info("-----------Inside updateclass method()----------------");
	SmsController controller = null;
	String status = "Fail";
	try {
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
		if (personID != null) {
			String className = ((StudentDataBean) event.getObject()).getTeaclssection().toString();
			String stuRollNo = ((StudentDataBean) event.getObject()).getStuRollNo().toString();
			String stuStudentId = ((StudentDataBean) event.getObject()).getStuStudentId().toString();
			studentDataBean.setTeaclssection(className);
			studentDataBean.setStuRollNo(stuRollNo);
			studentDataBean.setStuStudentId(stuStudentId);
			logger.debug("--- update class  Name ----"+className);
			logger.debug("---- student Roll number ------"+stuRollNo); 
			if (validates(true)) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				status = controller.getClassEdit(studentDataBean,personID);
				if (status.equalsIgnoreCase("Success")) {
					RequestContext.getCurrentInstance().execute("PF('updateblock').show();");
				}else if(status.equalsIgnoreCase("Fail")){
					logger.info("---- Failed ------");
				}
			}
		}
	}
	catch(Exception e)
	{
		logger.warn("Exception--> "+e.getMessage());
	}
}

	public void onRowClassCancel(RowEditEvent event){
		logger.info("-----------Inside onRowClassCancel method()----------------");
		FacesMessage msg = new FacesMessage("Edit Cancelled");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
