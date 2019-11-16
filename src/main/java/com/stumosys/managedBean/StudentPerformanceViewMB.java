package com.stumosys.managedBean;

/*import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
*///import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Date;
import java.util.List;
//import java.util.Properties;
import java.util.ResourceBundle;

/*import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;*/
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
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
*/
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/*import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
*/import com.stumosys.controler.SmsController;
//import com.stumosys.domain.StudentDataBean;
import com.stumosys.domain.StudentPerformanceDataBean;
//import com.stumosys.shared.StudentDetail;
import com.stumosys.shared.StudentPerformance;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name = "studentPerformanceViewMB")
@RequestScoped
public class StudentPerformanceViewMB {
	StudentPerformanceDataBean studentPerformanceDataBean = new StudentPerformanceDataBean();
	private List<String> spclass = null;
	List<String> classlist = null;
	List<StudentPerformanceDataBean> studviewlist = null;
	List<StudentPerformanceDataBean> filteredStudent;
	List<StudentPerformance> perviewlist = null;
	private boolean flag = false;
	private String status;
	private boolean boxflag = false;
	private boolean boxflag1 = false;
	private boolean DelBoxflag = false;
	private boolean flag3 = false;
	List<StudentPerformanceDataBean> studentList = null;
	private boolean valid = false;
	List<StudentPerformance> editList = null;
	private boolean check;
	private boolean flag1 = true;
	private boolean flag2 = true;
	private boolean sflag = true;
	private boolean pflag = true;
	private boolean lflag = false;
	private static Logger logger = Logger.getLogger(StudentPerformanceViewMB.class);
	private boolean lflag1 = false;
	private List<String> schoollist=null;
	private boolean tableflag=false;
	
	public boolean isTableflag() {
		return tableflag;
	}

	public void setTableflag(boolean tableflag) {
		this.tableflag = tableflag;
	}

	public List<String> getSchoollist() {
		return schoollist;
	}

	public void setSchoollist(List<String> schoollist) {
		this.schoollist = schoollist;
	}

	public boolean isLflag1() {
		return lflag1;
	}

	public void setLflag1(boolean lflag1) {
		this.lflag1 = lflag1;
	}

	public boolean isLflag() {
		return lflag;
	}

	public void setLflag(boolean lflag) {
		this.lflag = lflag;
	}

	public boolean isSflag() {
		return sflag;
	}

	public void setSflag(boolean sflag) {
		this.sflag = sflag;
	}

	public boolean isPflag() {
		return pflag;
	}

	public void setPflag(boolean pflag) {
		this.pflag = pflag;
	}

	private boolean check1;
	private String clssection;
	private List<String> sRoll = null;
	List<String> idlist = null;

	public boolean isBoxflag1() {
		return boxflag1;
	}

	public void setBoxflag1(boolean boxflag1) {
		this.boxflag1 = boxflag1;
	}

	public boolean isFlag3() {
		return flag3;
	}

	public void setFlag3(boolean flag3) {
		this.flag3 = flag3;
	}

	public boolean isFlag2() {
		return flag2;
	}

	public void setFlag2(boolean flag2) {
		this.flag2 = flag2;
	}

	public String getClssection() {
		return clssection;
	}

	public void setClssection(String clssection) {
		this.clssection = clssection;
	}

	
	public boolean isFlag1() {
		return flag1;
	}

	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
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

	public List<StudentPerformance> getEditList() {
		return editList;
	}

	public void setEditList(List<StudentPerformance> editList) {
		this.editList = editList;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public List<String> getsRoll() {
		return sRoll;
	}

	public void setsRoll(List<String> sRoll) {
		this.sRoll = sRoll;
	}

	public List<StudentPerformanceDataBean> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<StudentPerformanceDataBean> studentList) {
		this.studentList = studentList;
	}

	public List<String> getIdlist() {
		return idlist;
	}

	public void setIdlist(List<String> idlist) {
		this.idlist = idlist;
	}

	public boolean isDelBoxflag() {
		return DelBoxflag;
	}

	public void setDelBoxflag(boolean delBoxflag) {
		DelBoxflag = delBoxflag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isBoxflag() {
		return boxflag;
	}

	public void setBoxflag(boolean boxflag) {
		this.boxflag = boxflag;
	}

	public List<StudentPerformance> getPerviewlist() {
		return perviewlist;
	}

	public void setPerviewlist(List<StudentPerformance> perviewlist) {
		this.perviewlist = perviewlist;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public List<String> getClasslist() {
		return classlist;
	}

	public void setClasslist(List<String> classlist) {
		this.classlist = classlist;
	}

	public List<StudentPerformanceDataBean> getFilteredStudent() {
		return filteredStudent;
	}

	public void setFilteredStudent(List<StudentPerformanceDataBean> filteredStudent) {
		this.filteredStudent = filteredStudent;
	}

	public List<StudentPerformanceDataBean> getStudviewlist() {
		return studviewlist;
	}

	public void setStudviewlist(List<StudentPerformanceDataBean> studviewlist) {
		this.studviewlist = studviewlist;
	}

	public List<String> getSpclass() {
		return spclass;
	}

	public void setSpclass(List<String> spclass) {
		this.spclass = spclass;
	}

	public StudentPerformanceDataBean getStudentPerformanceDataBean() {
		return studentPerformanceDataBean;
	}

	public void setStudentPerformanceDataBean(StudentPerformanceDataBean studentPerformanceDataBean) {
		this.studentPerformanceDataBean = studentPerformanceDataBean;
	}

	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	Date now = new Date();
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	
	public String AttitudePageLoad() {
		  logger.debug("--------inside performance view------");
		  logger.debug("Calling-------------");
		  studentPerformanceDataBean.setPerformViewClass(null);
		  studentPerformanceDataBean.setTtdate(null);
		  String rollnumber = "";
		  try {
			  studentList=new ArrayList<StudentPerformanceDataBean>();
		   setBoxflag(false);
		   setBoxflag1(false);
		   flag3 = false;
		   spclass = new ArrayList<String>();
		   SmsController controller = null;
		   ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		   String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
		     .get("LogID");
		   controller = (SmsController) ctx.getBean("controller");
		   if (personID != null) {
		    status = controller.getRoll(personID);
		    logger.debug("-----status----" + status);
		    if (status.equalsIgnoreCase("Admin") || status.equalsIgnoreCase("Teacher")) {
		     setLflag(true); flag3 = false;
		     setLflag1(false);
		    }
		    if (status.equalsIgnoreCase("Student") ) {
		     setLflag(false);
		     setLflag1(true);
		     rollnumber = controller.getRollNumber(personID);
		     studentPerformanceDataBean.setStudID(rollnumber);
		     studentList = controller.getStudentPerInfo(personID, studentPerformanceDataBean);
		     setFlag3(false);
		     setSflag(true);
		     if(studentList.size()==0){
					FacesContext.getCurrentInstance().addMessage(CommonValidate.findComponentInRoot("panelid").getClientId(), new FacesMessage("No information found"));
				}
		    }
		    if (status.equalsIgnoreCase("Parent")) {
				logger.debug("iiiinnnnnn");
				setLflag(false);
				setLflag1(false);
				studentList = controller.getStudentPerInfo(personID, studentPerformanceDataBean);
				setFlag3(false);
				setSflag(true);
				if(studentList.size()==0){
					FacesContext.getCurrentInstance().addMessage(CommonValidate.findComponentInRoot("panelid").getClientId(), new FacesMessage("No information found"));
				}
			}
		    classlist = controller.getClassList1(personID, studentPerformanceDataBean);
		    for (int i = 0; i < studentPerformanceDataBean.getStudentclassList().size(); i++) {
		     spclass.add(studentPerformanceDataBean.getStudentclassList().get(i).getStuCls());
		    }

		    logger.debug("-----sclass---" + spclass);
		   }
		  } catch (Exception e) {
		   logger.warn("Exception -->"+e.getMessage());
		   logger.warn("Inside Exception",e);
		  }
		  return "studentPerfomInfo";

		 }

	public boolean validate(boolean flag) {
		boolean valid = true;
		String fieldName;
		String sapp = null;
		String satt = null;
		FacesContext fc = FacesContext.getCurrentInstance();
		System.out.println("---validate---------"+studentPerformanceDataBean.getStudentAppearance());
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
		/*
		 * else if (StringUtils.isEmpty(satt) &&
		 * StringUtils.isEmpty(studentPerformanceDataBean.getAttOthers())) {
		 * if(flag){ fieldName =
		 * CommonValidate.findComponentInRoot("perAttitude").getClientId(fc);
		 * fc.addMessage(fieldName, new FacesMessage(
		 * "Please Select the Student Attitude")); } valid = false; }
		 */
		return valid;
	}

	public void date(ValueChangeEvent e) {
		studentPerformanceDataBean.setPerformViewClass(null);
		setFlag3(false);
	}
	
	public void submit1() {
		logger.debug("---- Inside Student Information ----");
		SmsController controller = null;
		String status = null;
		setFlag3(true);
		try {
			studentList=new ArrayList<StudentPerformanceDataBean>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			logger.debug("-------person id-------" + personID);
			if (personID != null) {
				status = controller.getRoll(personID);
				logger.debug("-----status----" + status);
				if (status.equalsIgnoreCase("Admin") || status.equalsIgnoreCase("Teacher")) {
					setSflag(false);
				}
				if (status.equalsIgnoreCase("Student") || status.equalsIgnoreCase("Parent")) {
					setSflag(true);
				}
				//studentPerformanceDataBean.setPerClassSection(event.getNewValue().toString());
				studentPerformanceDataBean.setPerClassSection(studentPerformanceDataBean.getPerformViewClass()); 
				logger.debug("--class name------" + studentPerformanceDataBean.getPerClassSection());
				studentList = controller.getStudentPerInfo(personID, studentPerformanceDataBean);
				if(studentList.size()==0){
					FacesContext.getCurrentInstance().addMessage(CommonValidate.findComponentInRoot("panelid").getClientId(), new FacesMessage("No information found with given criteria"));
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}

	}

	public void performView() {
		logger.debug("-----inside perform view--------");
		//String stud = null;
		SmsController controller = null;
		if (!studentPerformanceDataBean.getPerformStudID().equalsIgnoreCase("")) {
			try {
				setFlag(false);
				setBoxflag(false);
				setBoxflag1(false);
				setDelBoxflag(false);
				studentPerformanceDataBean.setStuPar1("");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				perviewlist = controller.getPerformView(studentPerformanceDataBean);
				logger.debug("-----perviewlist size-----" + perviewlist.size() + "name - "
						+ studentPerformanceDataBean.getStuPar1());
				if (perviewlist.size() > 0) {
					studentPerformanceDataBean.setPerformStudID(studentPerformanceDataBean.getPerformStudID());
					studentPerformanceDataBean.setStuApp(perviewlist.get(0).getAppearanceStatus());
					studentPerformanceDataBean.setStuAtt(perviewlist.get(0).getAttitudeStatus());
					studentPerformanceDataBean.setStuName(studentPerformanceDataBean.getStuName1());
					studentPerformanceDataBean.setParName(studentPerformanceDataBean.getStuPar1());
				}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
		}
	}

	public void performDelete() {
		logger.debug("-----inside perform Delete--------");
		setDelBoxflag(false);
		SmsController controller = null;
		logger.debug("--delete roll------" + studentPerformanceDataBean.getPerformStudID());
		if (studentPerformanceDataBean.getPerformStudID() != null) {
			try {
				studentPerformanceDataBean.setBdate(studentPerformanceDataBean.getTtdate());
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				status = controller.getperformDelete(personID, studentPerformanceDataBean);
				if (status.equalsIgnoreCase("Success")) {
					RequestContext.getCurrentInstance().execute("PF('dlg').show();");
				}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
		}
	}

	public String returnToHome1() {

		SmsController controller = null;
		logger.debug("---------inside return to home-------");
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {

				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				studentList = controller.getStudentPerInfo(personID, studentPerformanceDataBean);
				logger.debug("---------size---------" + studentList.size());
				if(studentList.size()==0){
					FacesContext.getCurrentInstance().addMessage(CommonValidate.findComponentInRoot("panelid").getClientId(), new FacesMessage("No information found with given criteria"));
				}
				setFlag3(false);
				setDelBoxflag(false);
				setBoxflag(false);
				return "StudentviewPage";

			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());

		}
		logger.debug("--------outside return to home-------");
		return "";
	}

	

	public String loadingupdate() {
		if (validate(true)) {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				RequestContext reqcontextt = RequestContext.getCurrentInstance();
				reqcontextt.execute("studentPerformupdateBlk();");
			}
		}
		return "";
	}

	public String performUpdate() {
		logger.debug("-----inside student Update-------");
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
				studentPerformanceDataBean.setBdate(studentPerformanceDataBean.getTtdate());
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
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('studentperformUpdtUI').hide();");
						reqcontext.execute("PF('dlg').show();");
						setBoxflag(true);
//					/*}*/

					valid = false;
				} else if (status.equalsIgnoreCase("Not Valid")) {
					RequestContext reqcontext = RequestContext.getCurrentInstance();
					reqcontext.execute("PF('studentperformUpdtUI').hide();");
					reqcontext.execute("PF('dlg').show();");
					setBoxflag(true);
					valid = false;
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	public String reset() {
		try {
			studentPerformanceDataBean.setPerformStudID("");
			studentPerformanceDataBean.setPerClassSection("");
			studentPerformanceDataBean.setStudentAppearance(null);
			studentPerformanceDataBean.setStudentAttitude(null);
			studentPerformanceDataBean.setAppOthers(null);
			studentPerformanceDataBean.setAttOthers(null);
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
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

	public String classSection() {
		logger.debug("-------inside test method------");
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
		SmsController controller = null;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			studentPerformanceDataBean.setPerformStudID((studentPerformanceDataBean.getPerformStudID()));
			logger.debug("-------roll------" + studentPerformanceDataBean.getPerformStudID());
			clssection = controller.getPerClass(studentPerformanceDataBean,personID);
			logger.debug("-----class section-----" + clssection);
		} catch (Exception v) {
			v.printStackTrace();
		}
		return clssection;
	}

	/* neela(3-5-2016) 
	private String generatePdf(StudentPerformanceDataBean studentPerformanceDataBean2) {
		String status = "fail";
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			if (personID != null) {
				// Create Directory
				File files = new File(paths.getString("sms.studentperformance.pdf") + ft.format(now));
				if (!files.exists()) {
					files.mkdirs();
				} else {
					logger.debug("Alreday Found");
				}
				// Write PDF File
				OutputStream file = new FileOutputStream(
						new File(files + paths.getString("path_context").toString() + studentPerformanceDataBean2.getPerformStudID() + ".pdf"));

				Document document = new Document(PageSize.A4, 50, 50, 10, 50);
				PdfWriter.getInstance(document, file);
				document.open();
				String logo ="";
				if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
					logo = paths.getString(schoolID+"_LOGO");
					document.add(Image.getInstance(getClass().getClassLoader().getResource(logo)));
				}else{
					logo = studentPerformanceDataBean.getSchoolLogo();
					document.add(Image.getInstance(logo));
				}

				float[] columnWidths = { 20f, 1f };
				Font font3 = new Font(Font.TIMES_ROMAN, 27);
				PdfPTable table = new PdfPTable(2); // .
				table.setWidthPercentage(100f);
				table.setWidths(columnWidths);

				PdfPCell cell = new PdfPCell();

				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(2);
				cell.setBorder(PdfPCell.NO_BORDER);
				PdfPCell cell1 = new PdfPCell(new Paragraph());
				cell1.setBorder(PdfPCell.NO_BORDER);
				PdfPCell cell2 = new PdfPCell(new Paragraph());
				cell2.setBorder(PdfPCell.NO_BORDER);
				table.setSpacingBefore(25f);
				table.setSpacingAfter(25f);

				PdfPTable nestedTable = new PdfPTable(4);
				nestedTable.setWidthPercentage(100f);
				PdfPCell nesCell1 = new PdfPCell(new Paragraph("Class : "));
				PdfPCell nesCell2 = new PdfPCell(new Paragraph(studentPerformanceDataBean2.getPerClassSection()));
				PdfPCell nesCell3 = new PdfPCell(new Paragraph("Student ID : "));
				PdfPCell nesCell4 = new PdfPCell(new Paragraph(studentPerformanceDataBean2.getPerformStudID()));
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
				PdfPCell nesCell5 = new PdfPCell(new Paragraph("Appearance : "));
				PdfPCell nesCell6 = new PdfPCell(
						new Paragraph(Arrays.deepToString(studentPerformanceDataBean2.getStudentAppearance())));
				PdfPCell nesCell7 = new PdfPCell(new Paragraph("Attitude : "));
				PdfPCell nesCell8 = new PdfPCell(
						new Paragraph(Arrays.deepToString(studentPerformanceDataBean2.getStudentAttitude())));
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

				table.addCell(cell);
				table.addCell(cell1);
				table.addCell(cell2);
				document.add(table);
				document.close();
				file.close();

				
				 * //PDF open Windows and MAC File pdfFile = new
				 * File(files+paths.getString("path_context").toString()+teacherDataBean2.getTeaUsername()+".pdf");
				 * if(pdfFile.exists()){ if (Desktop.isDesktopSupported()) {
				 * Desktop.getDesktop().open(pdfFile); } else { logger.debug(
				 * "Awt Desktop is not supported!"); }
				 * 
				 * } else { logger.debug("File is not exists!"); }
				 

				logger.debug("Done");
				status = "Success";
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return status;
	}*/
/*
	 neela(3-5-2016) 
	private String sendEmail(StudentPerformanceDataBean studentPerformanceDataBean2) {
		String status = "Fail";
		logger.debug("---mail id --- " + studentPerformanceDataBean2.getMailid());
		String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
		try {

			InternetAddress[] toAddress = new InternetAddress[studentPerformanceDataBean2.getMailid().size()];
			for (int i = 0; i < studentPerformanceDataBean2.getMailid().size(); i++) {
				toAddress[i] = new InternetAddress(studentPerformanceDataBean2.getMailid().get(i));
			}
			Properties prop = new Properties();
			prop.put("mail.smtp.host", text.getString("sms.mail.host"));
			prop.put("mail.smtp.socketFactory.port", text.getString("sms.mail.port"));
			prop.put("mail.smtp.socketFactory.class", text.getString("sms.mail.class"));
			prop.put("mail.smtp.starttls.enable", text.getString("sms.mail.smtp.startles"));
			prop.put("mail.smtp.auth", text.getString("sms.mail.auth"));
			prop.put("mail.smtp.port", text.getString("sms.mail.smtp.port"));
			prop.put("mail.smtp.ssl.trust", text.getString("sms.mail.smtp.trust"));

			String body1 = "<htm><head></heade><body>"
					+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
					+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
					+ "<h3>Dear " + "Parents"
					+"<br></br>"
					+"Your son/daughter "
					+studentPerformanceDataBean2.getPerformStudID() + ",</h3>"
					+ "<p>Welcome on-board into"
					+  schoolName + "family. "
					+ "Please find the Appearance and Attitude Status enclosed with is mail."
					+ "</p>"
					+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
					+ "<tr>" + "<td>Appearance" + "</td>" + "<td>"
					+ Arrays.deepToString(studentPerformanceDataBean2.getStudentAppearance()) + "</td>" + "</tr>"
					+ "<tr>" + "<td>Attitude" + "</td>" + "<td>"
					+ Arrays.deepToString(studentPerformanceDataBean2.getStudentAttitude()) + "</td>" + "</tr>"
					+ "</table>" + "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>"
					+ "</footer>" + "</body></html>";
			

			Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
				}
			});
			// compose message
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
				message.addRecipients(Message.RecipientType.TO, toAddress);
				message.setSubject("Updated Student Performance and Behaviour");
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");

				

				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
			
				message.setContent(multipart);

				Transport.send(message);

				logger.debug("message sent successfully");
				status = "Success";

			} catch (MessagingException e) {
				logger.warn("Inside Exception",e);
				throw new RuntimeException(e);
			}

		} catch (Exception e) {
			logger.warn("Inside Exception",e);
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}
*/
	public String performEdit() {
		logger.debug("-----inside perform Edit--------");
		setBoxflag(false);
		SmsController controller = null;
		ArrayList<String> app = new ArrayList<String>();
		ArrayList<String> att = new ArrayList<String>();
		String[] arr;
		String[] arr2;

		try {
			valid = true;
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			sRoll = new ArrayList<String>();
			/*idlist = controller.getIdList(studentPerformanceDataBean);
			for (int i = 0; i < studentPerformanceDataBean.getRollnolist().size(); i++) {
				sRoll.add(studentPerformanceDataBean.getRollnolist().get(i).getPerformStudID());
			}

			clssection = controller.getPerClass(studentPerformanceDataBean);
			logger.debug("-----sclass---" + sRoll);*/
			editList = controller.getPerformDetails(studentPerformanceDataBean);
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
				studentPerformanceDataBean.setStuName(studentPerformanceDataBean.getStuName());
				studentPerformanceDataBean.setPerformStudID(studentPerformanceDataBean.getPerformStudID());
				setCheck(editList.get(0).isAppearanceOtherStatus());
				setCheck1(editList.get(0).isAttitudeOtherStatus());
				studentPerformanceDataBean.setAppOthers(editList.get(0).getAppearanceOther());
				studentPerformanceDataBean.setAttOthers(editList.get(0).getAttitudeOther());
			}

		} catch (Exception e)

		{
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.debug("----status---------" + status);
		return "studentPerformEdit";
	}
	
	//Ragulan Nov 28
	
	public String performancereportpage(){
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		SmsController	controller = (SmsController) ctx.getBean("controller");
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
		String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
		try{
			setTableflag(false);
			studentPerformanceDataBean.setSchoolName("");
			studentPerformanceDataBean.setStuApp("");
			studentPerformanceDataBean.setStuAtt("");
			studentPerformanceDataBean.setStuName("");
			studentPerformanceDataBean.setFromdate(null);
			studentPerformanceDataBean.setTodate(null);
			studentPerformanceDataBean.setClassname("");
			schoollist=new ArrayList<String>();
			classlist=new ArrayList<String>();
			schoollist.add(schoolName);
			classlist=controller.getClassNameList(personID);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "studentPerformancereport";
	}
	
	public void classchange(ValueChangeEvent event){
		try{
		sRoll=new ArrayList<String>();
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		SmsController	controller = (SmsController) ctx.getBean("controller");
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
		String classname=event.getNewValue().toString();
		if(classname!=null){
			if(!classname.equalsIgnoreCase("All")){
		studentPerformanceDataBean.setPerClassSection(classname);
		controller.getPerClass(studentPerformanceDataBean,personID);
		if(studentPerformanceDataBean.getRollnolist().size() > 0){
			for (int i = 0; i < studentPerformanceDataBean.getRollnolist().size(); i++) {
			sRoll.add(studentPerformanceDataBean.getRollnolist().get(i).getPerformStudID());
			}
		   }
		  }
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String performancereport(){
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController	controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if(personID!=null){
				try{
					if(validation(true)){
						System.out.println("size"+true);
						studviewlist=new ArrayList<StudentPerformanceDataBean>();
						studviewlist=controller.getStudentperformance(personID,studentPerformanceDataBean);
						System.out.println("list.size------>"+studviewlist.size());
						setTableflag(true);
					}
				}catch(Exception q){
					q.printStackTrace();
				}
			}
		return "";
	}

	private boolean validation(boolean valid) {
		valid=true;
		String name;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(StringUtils.isEmpty(studentPerformanceDataBean.getSchoolName())){
			name=CommonValidate.findComponentInRoot("schoolname").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Plaese Select the School Name"));
			valid=false;
		}
		/*if(StringUtils.isEmpty(studentPerformanceDataBean.getStuAtt())){
			name=CommonValidate.findComponentInRoot("behaviour").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Plaese Select the School Behaviour"));
			valid=false;
		}*/
		if(StringUtils.isEmpty(studentPerformanceDataBean.getClassname())){
			name=CommonValidate.findComponentInRoot("classname").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Plaese Select the Class Name"));
			valid=false;
		}
		if(!studentPerformanceDataBean.getClassname().equalsIgnoreCase("")){
		if(!studentPerformanceDataBean.getClassname().equalsIgnoreCase("All")){
			if(StringUtils.isEmpty(studentPerformanceDataBean.getStuName())){
				name=CommonValidate.findComponentInRoot("studentname").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Plaese Select the Student Name"));
				valid=false;
			}
		}
		}
		/*if(StringUtils.isEmpty(studentPerformanceDataBean.getStuApp())){
			name=CommonValidate.findComponentInRoot("repfrom").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Plaese Select the Type"));
			valid=false;
		}*/
		return valid;
	}

	public String globalstudPerformance() {
		logger.debug("-----inside perform Edit--------");
		setBoxflag(false);
		SmsController controller = null;
		ArrayList<String> app = new ArrayList<String>();
		ArrayList<String> att = new ArrayList<String>();
		String[] arr;
		String[] arr2;

		try {
			valid = true;
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			sRoll = new ArrayList<String>();
			//studentPerformanceDataBean.setPerformStudID(studentPerformanceDataBean.getPerformStudID());
			System.out.println("----roll no-----"+studentPerformanceDataBean.getPerformStudID());
			System.out.println("----date-----"+studentPerformanceDataBean.getBdate());
			editList = controller.getPerformDetails(studentPerformanceDataBean);
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
				studentPerformanceDataBean.setStuName(studentPerformanceDataBean.getStuName());
				studentPerformanceDataBean.setPerformStudID(studentPerformanceDataBean.getPerformStudID());
				setCheck(editList.get(0).isAppearanceOtherStatus());
				setCheck1(editList.get(0).isAttitudeOtherStatus());
				studentPerformanceDataBean.setAppOthers(editList.get(0).getAppearanceOther());
				studentPerformanceDataBean.setAttOthers(editList.get(0).getAttitudeOther());
				System.out.println("------------"+array.toString());
				System.out.println("------------"+array1.toString());
				RequestContext reqcontext = RequestContext.getCurrentInstance();
				reqcontext.execute("PF('performanceEditdlg').show();");
			}

		} catch (Exception e)
		{
			logger.warn("Exception -->"+e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	
	public String globalloadingupdate() {
		if (validateperformance(true)) {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				System.out.println("js calling -----------------------");
				RequestContext reqcontextt = RequestContext.getCurrentInstance();
				reqcontextt.execute("studentPerformupdateBlk1();");
				reqcontextt.execute("PF('performanceEditdlg').hide()");
			}
		}
		return "";
	}
	
	public boolean validateperformance(boolean flag) {
		boolean valid = true;
		String fieldName;
		String sapp = null;
		String satt = null;
		FacesContext fc = FacesContext.getCurrentInstance();
		System.out.println("---validate---------"+studentPerformanceDataBean.getStudentAppearance());
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
	
	public String performUpdate1() {
		System.out.println("-----inside student Update-------"+studentPerformanceDataBean.getStuName());
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
				System.out.println("Status1" + status);
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
			e.printStackTrace();
		}
		return "";
	}
	
	public void performDelete1() {
		logger.debug("-----inside perform Delete--------");
		setDelBoxflag(false);
		SmsController controller = null;
		logger.debug("--delete roll------" + studentPerformanceDataBean.getPerformStudID());
		if (studentPerformanceDataBean.getPerformStudID() != null) {
			try {
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				status = controller.getperformDelete(personID, studentPerformanceDataBean);
				System.out.println("status--------------"+status);
				if (status.equalsIgnoreCase("Success")) {
					RequestContext reqcontext = RequestContext.getCurrentInstance();
					reqcontext.execute("PF('performancedlgdlt').show();");
				}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
		}
	}
	
	
}
