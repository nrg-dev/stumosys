package com.stumosys.managedBean;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
//import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
/*import javax.mail.BodyPart;
import javax.mail.Message;
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
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.stumosys.controler.SmsController;
import com.stumosys.domain.AttendanceDataBean;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name = "attendancePreviewMB")
@RequestScoped
public class AttendancePreviewMB {

	AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
	private List<String> classList;
	private List<String> studentList;
	private List<AttendanceDataBean> filteredstudent;
	private boolean flag2 = false;
	private boolean flag3 = false;
	private String flagg = "none";
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	private String status;
	private List<AttendanceDataBean> attendancelist=null;
	private BarChartModel barchart;
	private boolean chartFlag=false;
	private List<String> schoollist=null;
	
	public List<String> getSchoollist() {
		return schoollist;
	}

	public void setSchoollist(List<String> schoollist) {
		this.schoollist = schoollist;
	}

	public boolean isChartFlag() {
		return chartFlag;
	}

	public void setChartFlag(boolean chartFlag) {
		this.chartFlag = chartFlag;
	}

	public BarChartModel getBarchart() {
		return barchart;
	}

	public void setBarchart(BarChartModel barchart) {
		this.barchart = barchart;
	}

	public List<AttendanceDataBean> getAttendancelist() {
		return attendancelist;
	}

	public void setAttendancelist(List<AttendanceDataBean> attendancelist) {
		this.attendancelist = attendancelist;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFlagg() {
		return flagg;
	}

	public void setFlagg(String flagg) {
		this.flagg = flagg;
	}

	public boolean isFlag3() {
		return flag3;
	}

	public void setFlag3(boolean flag3) {
		this.flag3 = flag3;
	}

	public List<String> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<String> studentList) {
		this.studentList = studentList;
	}

	public boolean isFlag2() {
		return flag2;
	}

	public void setFlag2(boolean flag2) {
		this.flag2 = flag2;
	}

	public List<String> getClassList() {
		return classList;
	}

	public void setClassList(List<String> classList) {
		this.classList = classList;
	}

	public List<AttendanceDataBean> getFilteredstudent() {
		return filteredstudent;
	}

	public void setFilteredstudent(List<AttendanceDataBean> filteredstudent) {
		this.filteredstudent = filteredstudent;
	}

	/**
	 * @return the attendanceDataBean
	 */
	public AttendanceDataBean getAttendanceDataBean() {
		return attendanceDataBean;
	}

	/**
	 * @param attendanceDataBean
	 *            the attendanceDataBean to set
	 */
	public void setAttendanceDataBean(AttendanceDataBean attendanceDataBean) {
		this.attendanceDataBean = attendanceDataBean;
	}

	private static Logger logger = Logger.getLogger(AttendanceMB.class);
	SmsController controller = null;

	public String previewAttendace() {
		logger.info("attendance view -- ");
		String personID = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
						.equals("Student")) {
					flag2 = false;
					flag3 = false;
					flagg = "none";
					return "attendanceViewStudent";
				} else if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
						.equals("Parent")) {
					studentList = controller.parentAttlist(personID, attendanceDataBean);
					Collections.sort(studentList);
					flag2 = true;
					flag3 = false;
					flagg = "1";
					return "attendanceViewStudent";
				} else {
					classList = controller.ClassListAttView(personID, attendanceDataBean);
					Collections.sort(classList);
					flag2 = false;
					return "Attendanceview";
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {
			attendanceDataBean.setClassname("");
			attendanceDataBean.setSection("");
			attendanceDataBean.setDate(null);
			attendanceDataBean.setStudentID(null);
			attendanceDataBean.setPeriod("");
		}
		return "";
	}

	public void classPeriod(ValueChangeEvent v) {
		logger.info("class period change -- ");
		flag2 = false;
		String personID = "";
		attendanceDataBean.setPeriod("");
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (validate1(true)) {
				if (personID != null) {
					attendanceDataBean.setClassname(v.getNewValue().toString());
					controller.classPeriod(attendanceDataBean, personID);
				}
			}
		} catch (Exception e) {
			logger.info("---------- search Method Exception Calling-----");
			logger.warn("Exception -->"+e.getMessage());
		}
	}

	private boolean validate1(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();

		if ((attendanceDataBean.getDate()) == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("attPreDate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Date."));
			}
			valid = false;

		}
		return valid;
	}

	private boolean validate(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();

		if ((attendanceDataBean.getDate()) == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("attPreDate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Date."));
			}
			valid = false;

		}
		if (attendanceDataBean.getClassname().equals("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("attClass").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Class."));
			}
			valid = false;

		}
		return valid;
	}

	public void search(ValueChangeEvent v) {
		logger.info("---------- search Method Calling-----");
		flag2 = false;
		String personID = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (validate(true)) {
				if (personID != null) {
					attendanceDataBean.setPeriod(v.getNewValue().toString());
					controller.attendanceView(personID, attendanceDataBean);
					flag2 = true;
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}

	public void loadimage(RowEditEvent event) {
		logger.info("update student attendance---------------");
		String personID = "";
		try {
			Calendar now = Calendar.getInstance();
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			if (personID != null) {
				attendanceDataBean.setSno(((AttendanceDataBean) event.getObject()).getSno());
				attendanceDataBean.setStudentID(((AttendanceDataBean) event.getObject()).getStudentID().toString());
				attendanceDataBean.setStudentName(((AttendanceDataBean) event.getObject()).getStudentName().toString());
				attendanceDataBean.setStatus(((AttendanceDataBean) event.getObject()).getStatus().toString());
				
				attendanceDataBean.setMonthyear("" + (now.get(Calendar.MONTH) + 1));
				attendanceDataBean.setYear("" + now.get(Calendar.YEAR));
				
				String status = controller.updateAttendance(attendanceDataBean, personID);
				setStatus(status);
				RequestContext reqcontextt = RequestContext.getCurrentInstance();
				reqcontextt.execute("attendupdBlk();");
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}

	public String onRowEdit() {
		logger.info("update student attendance  ");
		String personID = "";
		try {
			String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			if (personID != null) {
				if (status.equals("success")) {
					if (attendanceDataBean.getStatus().equals("Absent")) {
						/*sendEmail(attendanceDataBean);*/
						MailSendJDBC.attendanceUpdate(attendanceDataBean,schoolName,schoolid);
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('attendupdblocksUI').hide();");
					
					
					} else if (attendanceDataBean.getStatus().equals("Late")) {
						/*sendEmail1(attendanceDataBean);*/
						MailSendJDBC.attendanceUpdate(attendanceDataBean,schoolName,schoolid);
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('attendupdblocksUI').hide();");
					} else {
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('attendupdblocksUI').hide();");
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	// Below code is not used

	/*private String sendEmail(AttendanceDataBean attendanceDataBean) {
		String status = "Fail";
		String period = "";
		String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
		try {
			InternetAddress[] toAddress = new InternetAddress[attendanceDataBean.getMails().size()];
			for (int i = 0; i < attendanceDataBean.getMails().size(); i++) {
				toAddress[i] = new InternetAddress(attendanceDataBean.getMails().get(i));
			}

			Properties prop = new Properties();
			prop.put("mail.smtp.host", text.getString("sms.mail.host"));
			prop.put("mail.smtp.socketFactory.port", text.getString("sms.mail.port"));
			prop.put("mail.smtp.socketFactory.class", text.getString("sms.mail.class"));
			prop.put("mail.smtp.starttls.enable", text.getString("sms.mail.smtp.startles"));
			prop.put("mail.smtp.auth", text.getString("sms.mail.auth"));
			prop.put("mail.smtp.port", text.getString("sms.mail.smtp.port"));
			prop.put("mail.smtp.ssl.trust", text.getString("sms.mail.smtp.trust"));
			if (attendanceDataBean.getPeriod().substring(7, 8).equals("1"))
				period = "st Period";
			else if (attendanceDataBean.getPeriod().substring(7, 8).equals("2"))
				period = "nd Period";
			else if (attendanceDataBean.getPeriod().substring(7, 8).equals("3"))
				period = "rd Period";
			else
				period = "th Period";
			String body1 = "<htm><head></heade><body>"
					+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
					+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
					+ "<h3>Dear " + attendanceDataBean.getStudentName() + ",</h3>"
					+ "<p>Welcome on-board into "
					+ schoolName
					+ "family.The Student " + attendanceDataBean.getStudentName()
					+ " is Absent Today " + attendanceDataBean.getPeriod().substring(7, 8) + period + ".</p>"
					+ "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>"
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
				message.setSubject("Change the Attendance ");
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);
				Transport.send(message);

				logger.info("message sent successfully");
				status = "Success";

			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}*/

	/*private String sendEmail1(AttendanceDataBean attendanceDataBean) {
		String status = "Fail";
		String period = "";
		String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
		try {
			InternetAddress[] toAddress = new InternetAddress[attendanceDataBean.getMails().size()];
			for (int i = 0; i < attendanceDataBean.getMails().size(); i++) {
				toAddress[i] = new InternetAddress(attendanceDataBean.getMails().get(i));
			}

			Properties prop = new Properties();
			prop.put("mail.smtp.host", text.getString("sms.mail.host"));
			prop.put("mail.smtp.socketFactory.port", text.getString("sms.mail.port"));
			prop.put("mail.smtp.socketFactory.class", text.getString("sms.mail.class"));
			prop.put("mail.smtp.starttls.enable", text.getString("sms.mail.smtp.startles"));
			prop.put("mail.smtp.auth", text.getString("sms.mail.auth"));
			prop.put("mail.smtp.port", text.getString("sms.mail.smtp.port"));
			prop.put("mail.smtp.ssl.trust", text.getString("sms.mail.smtp.trust"));
			if (attendanceDataBean.getPeriod().substring(7, 8).equals("1"))
				period = "st Period";
			else if (attendanceDataBean.getPeriod().substring(7, 8).equals("2"))
				period = "nd Period";
			else if (attendanceDataBean.getPeriod().substring(7, 8).equals("3"))
				period = "rd Period";
			else
				period = "th Period";
			String body1 = "<htm><head></heade><body>"
					+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
					+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
					+ "<h3>Dear " + attendanceDataBean.getStudentName() + ",</h3>"
					+ "<p>Welcome on-board into "
					+ schoolName
					+ "family.The Student " + attendanceDataBean.getStudentName()
					+ " is Coming Late Today " + "at " + attendanceDataBean.getTime1() + " for "
					+ attendanceDataBean.getPeriod().substring(7, 8) + period + ".</p>" + "<footer>" + "<center>"
					+ "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>" + "</body></html>";

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
				message.setSubject("Change the Attendance ");
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);
				Transport.send(message);

				logger.info("message sent successfully");
				status = "Success";

			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}*/

	public void studentView(ValueChangeEvent v) {
		logger.info("---------- search Method Calling-----");
		attendanceDataBean.setStudentList(null);
		flag3 = false;
		String personID = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				attendanceDataBean.setStudentID(v.getNewValue().toString());
				logger.info("---------------- student ID ------------------"+attendanceDataBean.getStudentID()); 
				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
						.equals("Parent")) {
					if (validate1(true)) {
						controller.parentstuAttView(personID, attendanceDataBean);
						flag3 = true;
					}
				} else {
					controller.parentstuAttView(personID, attendanceDataBean);
					flag3 = true;
				}
			}
		} catch (Exception e) {
			logger.info("---------- search Method Exception Calling-----");
			logger.warn("Exception -->"+e.getMessage());
		} finally {

		}
	}

	public void attendanceStudent(ValueChangeEvent v) {
		try {
			String personID = "";
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				attendanceDataBean.setDate((Date) v.getNewValue());
				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
						.equals("Student")) {
					controller.studentAttView(personID, attendanceDataBean);
					flag3 = true;
				} else if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
						.equals("Parent")) {
					attendanceDataBean.setStudentID("");
					flag3 = false;
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {

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
	
	public void dateSelect(ValueChangeEvent v) {
		attendanceDataBean.setClassname("");
		attendanceDataBean.setPeriod("");
		flag2=false;
	}
	
	//Ragulan Nov-23 Attendance Report
	
	public String attendanceReport(){
		System.out.println("inside the method----");
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (SmsController) ctx.getBean("controller");
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
		String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
		if(personID!=null){
		try{
			setFlag2(false);
			setChartFlag(false);
			attendanceDataBean.setSchoolName("");
			attendanceDataBean.setClassname("");
			attendanceDataBean.setStudentName("");
			attendanceDataBean.setCategory("");
			classList=new ArrayList<String>();
			schoollist=new ArrayList<String>();
			classList=controller.ClassListAttView(personID, attendanceDataBean);
			Collections.sort(classList);
			schoollist.add(schoolName);
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		return"studentAttendancereport";
	}
	
	public void classChange(ValueChangeEvent v) {
		String personID = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
					studentList=new ArrayList<String>();
					attendanceDataBean.setClassname(v.getNewValue().toString());
					controller.classStudent(attendanceDataBean, personID);
					studentList.addAll(attendanceDataBean.getStudents());
					Collections.sort(studentList);
				} 
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public String attendanceSearch(){
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (SmsController) ctx.getBean("controller");
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
		if(personID!=null){
			try{
				if(validation(true)){
					attendancelist=new ArrayList<AttendanceDataBean>();
					attendancelist=controller.attendanceReport(personID,attendanceDataBean);
					setFlag2(true);	
					setChartFlag(false);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return "";
	}

	private boolean validation(boolean valid) {
		valid=true;
		String name;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(StringUtils.isEmpty(attendanceDataBean.getSchoolName())){
			name=CommonValidate.findComponentInRoot("schoolname").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Choose The SchoolName"));
			valid=false;	
		}
		if(StringUtils.isEmpty(attendanceDataBean.getClassname())){
			name=CommonValidate.findComponentInRoot("classname").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Choose The ClassName"));
			valid=false;	
		}
		/*if(StringUtils.isEmpty(attendanceDataBean.getStudentName())){
			name=CommonValidate.findComponentInRoot("studentname").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Choose The StudentName"));
			valid=false;	
		}*/
		if(StringUtils.isEmpty(attendanceDataBean.getCategory())){
			name=CommonValidate.findComponentInRoot("repfrom").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Choose The Report Type"));
			valid=false;	
		}
		return valid;
	}
	
	public void createBarModel() {
		setChartFlag(true);
		barchart = initBarModel();
		barchart.setTitle("Bar Chart");
		barchart.setLegendPosition("ne");
        if(attendanceDataBean.getCategory().equalsIgnoreCase("Monthly")){
        	Axis xAxis = barchart.getAxis(AxisType.X);
        	xAxis.setLabel("Month");
        	Axis yAxis = barchart.getAxis(AxisType.Y);
        	yAxis.setLabel("Percentage");
        	yAxis.setMin(0);
        	yAxis.setMax(100);
         }else if(attendanceDataBean.getCategory().equalsIgnoreCase("Yearly")){
        	 Axis xAxis = barchart.getAxis(AxisType.X);
             xAxis.setLabel("Year");
             Axis yAxis = barchart.getAxis(AxisType.Y);
             yAxis.setLabel("Percentage");
             yAxis.setMin(0);
             yAxis.setMax(100); 
         }else if(attendanceDataBean.getCategory().equalsIgnoreCase("Half Yearly")){
        	 Axis xAxis = barchart.getAxis(AxisType.X);
             xAxis.setLabel("Month");
             Axis yAxis = barchart.getAxis(AxisType.Y);
             yAxis.setLabel("Percentage");
             yAxis.setMin(0);
             yAxis.setMax(100); 
         }
    }

	private BarChartModel initBarModel() {
		BarChartModel model=null;
		try{
		model = new BarChartModel();
        ChartSeries attendance=null;
			if(attendancelist.size()>0){
				if(attendanceDataBean.getCategory().equalsIgnoreCase("Monthly")){
				for (int i = 0; i < attendancelist.size(); i++) {
					attendance=new ChartSeries();
					 attendance.setLabel("Percentage");
					Format formatter = new SimpleDateFormat("MMMM"); 
				    String month= formatter.format(attendancelist.get(i).getDate());
				    String percentage="";
				    BigDecimal presents = BigDecimal.valueOf(0);
				    StringTokenizer stringtoken = new StringTokenizer(attendancelist.get(i).getPercentage());
				    percentage=stringtoken.nextToken("%");
				    presents=new BigDecimal(percentage);
					attendance.set(month, presents);
					}
				}else if(attendanceDataBean.getCategory().equalsIgnoreCase("Yearly")){
					for (int i = 0; i < attendancelist.size(); i++) {
						attendance=new ChartSeries();
						 attendance.setLabel("Percentage");
						Format formatter = new SimpleDateFormat("yyyy"); 
					    String year= formatter.format(attendancelist.get(i).getDate());
					    String percentage="";
					    BigDecimal presents = BigDecimal.valueOf(0);
					    StringTokenizer stringtoken = new StringTokenizer(attendancelist.get(i).getPercentage());
					    percentage=stringtoken.nextToken("%");
					    presents=new BigDecimal(percentage);
						attendance.set(year, presents);
					}	
				}else if(attendanceDataBean.getCategory().equalsIgnoreCase("Half Yearly")){
					for (int i = 0; i < attendancelist.size(); i++) {
						attendance=new ChartSeries();
						 attendance.setLabel("Percentage");
						Format formatter = new SimpleDateFormat("MMMM"); 
					    String month= formatter.format(attendancelist.get(i).getDate());
					    String percentage="";
					    BigDecimal presents = BigDecimal.valueOf(0);
					    StringTokenizer stringtoken = new StringTokenizer(attendancelist.get(i).getPercentage());
					    percentage=stringtoken.nextToken("%");
					    presents=new BigDecimal(percentage);
						attendance.set(month, presents);
					}
				}
				}
		model.addSeries(attendance);
		}catch(Exception e){
			e.printStackTrace();
		}
        return model;
	}
}
