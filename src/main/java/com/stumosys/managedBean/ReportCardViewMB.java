package com.stumosys.managedBean;

//import java.awt.event.ActionEvent;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
//import java.io.InputStream;
//import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

//import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.imageio.ImageIO;
import javax.mail.BodyPart;
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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
//import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
//import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
//import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.stumosys.controler.SmsController;
import com.stumosys.domain.AttendanceDataBean;
import com.stumosys.domain.ReportCardDataBean;
//import com.stumosys.shared.Reportcard;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name = "reportCardViewMB")
@RequestScoped
public class ReportCardViewMB {
	ReportCardDataBean reportCardDataBean = new ReportCardDataBean();
	private List<String> classSectionList = null;
	private List<String> studentIDList = null;
	private List<ReportCardDataBean> studentMarkList = null;
	private List<ReportCardDataBean> filterList;
	private List<String> examtitle = null;
	private static Logger logger = Logger.getLogger(ReportCardViewMB.class);
	static Paragraph cell = new Paragraph();
	static PdfPCell cell1 = new PdfPCell();
	private boolean editFlag = false;
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	private BarChartModel barModel;
	private boolean barflag = false;
	Date now = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	private String base64Str;
	private StreamedContent file;
	private boolean renderflag = false;
	private boolean renderflag1 = false;
	private boolean renderflag2 = false;
	private boolean renderflag3 = false;
	private List<String> schoollist=null;
	private boolean tableflag=false;
	private BarChartModel barchart;
	private boolean chartFlag=false;
	private boolean subjectflag=false;
	private List<String> subjectlist=null;
	
	public boolean isSubjectflag() {
		return subjectflag;
	}

	public void setSubjectflag(boolean subjectflag) {
		this.subjectflag = subjectflag;
	}

	public List<String> getSubjectlist() {
		return subjectlist;
	}

	public void setSubjectlist(List<String> subjectlist) {
		this.subjectlist = subjectlist;
	}

	public BarChartModel getBarchart() {
		return barchart;
	}

	public void setBarchart(BarChartModel barchart) {
		this.barchart = barchart;
	}

	public boolean isChartFlag() {
		return chartFlag;
	}

	public void setChartFlag(boolean chartFlag) {
		this.chartFlag = chartFlag;
	}

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

	public boolean isRenderflag3() {
			return renderflag3;
		}

		public void setRenderflag3(boolean renderflag3) {
			this.renderflag3 = renderflag3;
		}

		public boolean isRenderflag2() {
			return renderflag2;
		}

		public void setRenderflag2(boolean renderflag2) {
			this.renderflag2 = renderflag2;
		}

		public boolean isRenderflag1() {
			return renderflag1;
		}

		public void setRenderflag1(boolean renderflag1) {
			this.renderflag1 = renderflag1;
		}
		
	/**
	 * @return the examtitle
	 */
	public List<String> getExamtitle() {
		return examtitle;
	}

	/**
	 * @param examtitle
	 *            the examtitle to set
	 */
	public void setExamtitle(List<String> examtitle) {
		this.examtitle = examtitle;
	}

	/**
	 * @return the classSectionList
	 */
	public List<String> getClassSectionList() {
		return classSectionList;
	}

	/**
	 * @param classSectionList
	 *            the classSectionList to set
	 */
	public void setClassSectionList(List<String> classSectionList) {
		this.classSectionList = classSectionList;
	}

	/**
	 * @return the studentIDList
	 */
	public List<String> getStudentIDList() {
		return studentIDList;
	}

	/**
	 * @param studentIDList
	 *            the studentIDList to set
	 */
	public void setStudentIDList(List<String> studentIDList) {
		this.studentIDList = studentIDList;
	}

	/**
	 * @return the reportCardDataBean
	 */
	public ReportCardDataBean getReportCardDataBean() {
		return reportCardDataBean;
	}

	/**
	 * @param reportCardDataBean
	 *            the reportCardDataBean to set
	 */
	public void setReportCardDataBean(ReportCardDataBean reportCardDataBean) {
		this.reportCardDataBean = reportCardDataBean;
	}

	/*Neela Oct 25 submit*/
	public String submit() {
		logger.debug("----submit method calling-----");
		SmsController controller = null;
		//int total = 0;
		reportCardDataBean.setTotalMark("");
		try {
			studentMarkList = new ArrayList<ReportCardDataBean>();
			if (validate(true)) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				logger.debug("personID" + personID);
				if (personID != null) {
					studentMarkList = controller.getRepoartCard(personID, reportCardDataBean);
					logger.debug(reportCardDataBean.getTotalMark());
					for (int i = 0; i < studentMarkList.size(); i++) {
						logger.debug(studentMarkList.get(i).getTotalMark());
						reportCardDataBean.setTotalMark(studentMarkList.get(i).getTotalMark());
					}
					logger.debug(reportCardDataBean.getTotalMark());

					return "getmarksheet";
				}
			}
		} catch (Exception e) {
			logger.debug("------submit method Exception Calling------");
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}

	public String reset() {
		reportCardDataBean.setStudMarkClass("");
		reportCardDataBean.setExamMarkTitle("");
		reportCardDataBean.setViewMarkStuName("");
		return "";
	}

	public void classChange(ValueChangeEvent event) {
		SmsController controller = null;
		examtitle = null;
		studentIDList = null;
		try {
			studentIDList = new ArrayList<String>();
			examtitle = new ArrayList<String>();
			if (event.getNewValue().toString() != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				if (personID != null) {
					reportCardDataBean.setStudMarkClass(event.getNewValue().toString());
					examtitle = controller.getExamTitleName(personID, event.getNewValue().toString());
					studentIDList = controller.getStudentIDList(reportCardDataBean, personID);
					logger.debug("Size---" + studentIDList);
					reportCardDataBean.setTotalMark("");
				}
			}
		} catch (NullPointerException e1) {

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}

	/**
	 * @return the studentMarkList
	 */
	public List<ReportCardDataBean> getStudentMarkList() {
		return studentMarkList;
	}

	/**
	 * @param studentMarkList
	 *            the studentMarkList to set
	 */
	public void setStudentMarkList(List<ReportCardDataBean> studentMarkList) {
		this.studentMarkList = studentMarkList;
	}

	/**
	 * @return the filterList
	 */
	public List<ReportCardDataBean> getFilterList() {
		return filterList;
	}

	/**
	 * @param filterList
	 *            the filterList to set
	 */
	public void setFilterList(List<ReportCardDataBean> filterList) {
		this.filterList = filterList;
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
	
		if (reportCardDataBean.getExamMarkTitle() != null && reportCardDataBean.getStudMarkClass() != null
				&& reportCardDataBean.getViewMarkStuName() != null) {
			PdfPTable table = new PdfPTable(2);
			cell = new Paragraph("Exam Title :");
			cell.add(reportCardDataBean.getExamMarkTitle());
			cell1 = new PdfPCell(cell);
			cell1.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell1);
			cell = new Paragraph("Class & Section :");
			cell.add(reportCardDataBean.getStudMarkClass());
			cell1 = new PdfPCell(cell);
			cell1.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell1);
			cell = new Paragraph("Student ID :");
			cell.add(reportCardDataBean.getViewMarkStuName());
			cell1 = new PdfPCell(cell);
			cell1.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell1);
			cell = new Paragraph("");
			cell.add("");
			cell1 = new PdfPCell(cell);
			cell1.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell1);
			pdf.add(table);
			pdf.add(new Paragraph(
					"----------------------------------------------------------------------------------------------------------------------------------"
							+ "\n"));
		}
	}

	public void postProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		PdfPTable table = new PdfPTable(2);
		cell = new Paragraph("");
		cell.add("");
		cell1 = new PdfPCell(cell);
		cell1.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell1);
		cell = new Paragraph("Total :");
		cell.add(reportCardDataBean.getTotalMark());
		cell1 = new PdfPCell(cell);
		cell1.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell1);
		pdf.add(table);
	}

	/**
	 * @return the editFlag
	 */
	public boolean isEditFlag() {
		return editFlag;
	}

	/**
	 * @param editFlag
	 *            the editFlag to set
	 */
	public void setEditFlag(boolean editFlag) {
		this.editFlag = editFlag;
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed",
					"Old: " + oldValue + ", New:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void onRowEdit(RowEditEvent event) {
		SmsController controller = null;
		String mark = "";
		int submark = 0;
		String status = "Fail";
		String serial = "";
		String subject = "";
		int schoolid=0;String personID ="",schoolID="";
		ApplicationContext ctx=null;
		logger.debug("Calling");
		try {
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			if (personID != null) {
			ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			schoolid=Integer.parseInt(schoolID);
			reportCardDataBean.setSchoolID(schoolid);
			reportCardDataBean.setsNo("");
			reportCardDataBean.setMarkSubTitle("");
			reportCardDataBean.setMark("");
			reportCardDataBean.setResultStatus("");
			reportCardDataBean.setGrade("");
			mark = ((ReportCardDataBean) event.getObject()).getMark().toString();
			serial = ((ReportCardDataBean) event.getObject()).getsNo().toString();
			subject = ((ReportCardDataBean) event.getObject()).getMarkSubTitle().toString();
			reportCardDataBean.setsNo(serial);
			reportCardDataBean.setMarkSubTitle(subject);
			reportCardDataBean.setMark(mark);
			if (mark != null) {
				if (mark.equalsIgnoreCase("A")) {
					reportCardDataBean.setMark(mark);
					reportCardDataBean.setResultStatus("Absent");
					reportCardDataBean.setGrade("F");
					status = setTableValues(reportCardDataBean);
				} else {
					try {
						controller.getexammarkgrade(reportCardDataBean);
						status = setTableValues(reportCardDataBean);
						/*submark = Integer.parseInt(mark);
						if (submark <= 100 && submark >= 80 || submark >= 80 && submark <= 100) {
							reportCardDataBean.setMark(mark);
							reportCardDataBean.setResultStatus("Pass");
							reportCardDataBean.setGrade("A");
							status = setTableValues(reportCardDataBean);
						} else if (submark <= 79 && submark >= 70 || submark >= 70 && submark <= 79) {
							reportCardDataBean.setMark(mark);
							reportCardDataBean.setResultStatus("Pass");
							reportCardDataBean.setGrade("A");
							status = setTableValues(reportCardDataBean);
						} else if (submark <= 69 && submark >= 60 || submark >= 60 && submark <= 69) {
							reportCardDataBean.setMark(mark);
							reportCardDataBean.setResultStatus("Pass");
							reportCardDataBean.setGrade("B");
							status = setTableValues(reportCardDataBean);
						} else if (submark <= 59 && submark >= 55 || submark >= 55 && submark <= 59) {
							reportCardDataBean.setMark(mark);
							reportCardDataBean.setResultStatus("Pass");
							reportCardDataBean.setGrade("B");
							status = setTableValues(reportCardDataBean);
						} else if (submark <= 54 && submark >= 50 || submark >= 50 && submark <= 54) {
							reportCardDataBean.setMark(mark);
							reportCardDataBean.setResultStatus("Pass");
							reportCardDataBean.setGrade("B-");
							status = setTableValues(reportCardDataBean);
						} else if (submark <= 49 && submark >= 45 || submark >= 45 && submark <= 49) {
							reportCardDataBean.setMark(mark);
							reportCardDataBean.setResultStatus("Pass");
							reportCardDataBean.setGrade("C");
							setTableValues(reportCardDataBean);
						} else if (submark <= 44 && submark >= 40 || submark >= 40 && submark <= 44) {
							reportCardDataBean.setMark(mark);
							reportCardDataBean.setResultStatus("Pass");
							reportCardDataBean.setGrade("C");
							status = setTableValues(reportCardDataBean);
						} else if (submark <= 39 && submark >= 35 || submark >= 35 && submark <= 39) {
							reportCardDataBean.setMark(mark);
							reportCardDataBean.setResultStatus("Partially Pass");
							reportCardDataBean.setGrade("C-");
							status = setTableValues(reportCardDataBean);
						} else if (submark <= 34 && submark >= 30 || submark >= 30 && submark <= 34) {
							reportCardDataBean.setMark(mark);
							reportCardDataBean.setResultStatus("Partially Pass");
							reportCardDataBean.setGrade("D");
							status = setTableValues(reportCardDataBean);
						} else if (submark <= 29 && submark >= 25 || submark >= 25 && submark <= 29) {
							reportCardDataBean.setMark(mark);
							reportCardDataBean.setResultStatus("Partially Pass");
							reportCardDataBean.setGrade("D");
							status = setTableValues(reportCardDataBean);
						} else if (submark <= 24 && submark >= 0 || submark >= 0 && submark <= 24) {
							reportCardDataBean.setMark(mark);
							reportCardDataBean.setResultStatus("Fail");
							reportCardDataBean.setGrade("F");
							status = setTableValues(reportCardDataBean);
						} else {
							logger.debug("Not valid Number");
							reportCardDataBean.setMark(mark);
							reportCardDataBean.setResultStatus("NA");
							reportCardDataBean.setGrade("NA");
							status = setTableValues(reportCardDataBean);
						}*/
					} catch (NumberFormatException nex) {
						/*
						 * reportCardDataBean.setMarkSubTitle(mark);
						 * reportCardDataBean.setResultStatus("NA");
						 * reportCardDataBean.setGrade("NA");
						 * setTableValues(reportCardDataBean);
						 */
						ReportCardDataBean rp = new ReportCardDataBean();
						rp.setGrade("");
						rp.setsNo(reportCardDataBean.getsNo());
						rp.setResultStatus("");
						rp.setMarkSubTitle(reportCardDataBean.getMarkSubTitle());
						rp.setMark("");
						studentMarkList.set((Integer.parseInt(reportCardDataBean.getsNo()) - 1), rp);

						FacesMessage msg = new FacesMessage("Please Enter Valid Mark", reportCardDataBean.getRollNo());
						FacesContext.getCurrentInstance().addMessage(null, msg);

					}
				}
			}
			if (status.equalsIgnoreCase("Success")) {
					String reportStatus = controller.editReportCard(personID, reportCardDataBean);
					if (reportStatus.equalsIgnoreCase("Success")) {
						FacesMessage msg = new FacesMessage("Sucessfully Saved mark",
								reportCardDataBean.getMarkSubTitle());
						FacesContext.getCurrentInstance().addMessage(null, msg);
						/*String email = sendEmail(reportCardDataBean);
						if (email.equalsIgnoreCase("Success")) {
							FacesMessage msg = new FacesMessage("Sucessfully Saved mark",
									reportCardDataBean.getMarkSubTitle());
							FacesContext.getCurrentInstance().addMessage(null, msg);
						}*/
					} else {
						FacesMessage msg = new FacesMessage("Network Problem.Please try again..",
								reportCardDataBean.getMarkSubTitle());
						FacesContext.getCurrentInstance().addMessage(null, msg);
					}
			}
			}
		} catch (NullPointerException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}

	private String setTableValues(ReportCardDataBean reportCardDataBean) {
		String status = "Fail";
		if (reportCardDataBean.getResultStatus().equalsIgnoreCase("NA")) {
			ReportCardDataBean rp = new ReportCardDataBean();
			rp.setGrade("");
			rp.setsNo(reportCardDataBean.getsNo());
			rp.setResultStatus("");
			rp.setMarkSubTitle(reportCardDataBean.getMarkSubTitle());
			rp.setMark("");
			rp.setUpflag(false);
			rp.setDownflag(false);
			studentMarkList.set((Integer.parseInt(reportCardDataBean.getsNo()) - 1), rp);

			FacesMessage msg = new FacesMessage("Please Enter Valid Mark", reportCardDataBean.getsNo());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			ReportCardDataBean rp = new ReportCardDataBean();
			rp.setGrade(reportCardDataBean.getGrade());
			rp.setsNo(reportCardDataBean.getsNo());
			rp.setResultStatus(reportCardDataBean.getResultStatus());
			rp.setMarkSubTitle(reportCardDataBean.getMarkSubTitle());
			rp.setMark(reportCardDataBean.getMark());
			studentMarkList.set((Integer.parseInt(reportCardDataBean.getsNo()) - 1), rp);
			/*
			 * FacesMessage msg = new FacesMessage("Sucessfully Saved mark"
			 * ,reportCardDataBean.getsNo() );
			 * FacesContext.getCurrentInstance().addMessage(null, msg);
			 */
			status = "Success";
		}
		return status;
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled", ((ReportCardDataBean) event.getObject()).getRollNo());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	private String sendEmail(ReportCardDataBean reportCardDataBean) {
		String status = "Fail";

		try {
			String to = reportCardDataBean.getMailid();
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
					+ "<h3>Dear Parents/Students,</h3>" + "<p>We have listed the"
					+ reportCardDataBean.getExamMarkTitle() + " Marks for " + reportCardDataBean.getMarkSubTitle()
					+ " Subject."
					+ "<table bgcolor='orange' border='1' cellpadding='10' style='width:100%;border: 1px solid black;border-collapse: collapse;'>"
					+ "<tr>" + "<td> Student ID" + "</td>" + "<td> Name" + "</td>" + "<td> Mark" + "</td>"
					+ "<td> Grade " + "</td>" + "<td> Result " + "</td>" + "</tr>" + "<tr>" + "<td>"
					+ reportCardDataBean.getRollNo() + "</td>" + "<td>" + reportCardDataBean.getName() + "</td>"
					+ "<td>" + reportCardDataBean.getMark() + "</td>" + "<td>" + reportCardDataBean.getGrade() + "</td>"
					+ "<td>" + reportCardDataBean.getResultStatus() + "</td>" + "</tr>" + "</table>" + "<footer>"
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
				message.setSubject(
						reportCardDataBean.getExamMarkTitle() + " Marks for " + reportCardDataBean.getMarkSubTitle());
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);

				Transport.send(message);

				logger.debug("message sent successfully");
				status = "Success";

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}

	
	public void searchChange(ValueChangeEvent value) {
		classSectionList = null;
		SmsController controller = null;
		reportCardDataBean.setBarstudentid("");
		reportCardDataBean.setBarclassid("");
		setBarflag(false);
		try {
			classSectionList = new ArrayList<String>();
			String eventvalue = value.getNewValue().toString();
			if (eventvalue != null) {
				if (eventvalue.equalsIgnoreCase("StudentID")) {

				} else {

					ApplicationContext ctx = FacesContextUtils
							.getWebApplicationContext(FacesContext.getCurrentInstance());
					controller = (SmsController) ctx.getBean("controller");
					String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
							.get("LogID");
					//String roll = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role");

					if (personID != null) {
						classSectionList = controller.getClassNameList(personID);
					}
				}
			}
		} catch (NullPointerException e) {
			logger.warn("Inside Exception",e);
		}
	}

	public String barchartSubmit() {
		SmsController controller = null;
		//boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			logger.debug("Inside Barchart" + reportCardDataBean.getBarclassid());
			setBarflag(false);
			if (validatebar(true)) {
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				if (personID != null) {
					String status = controller.getReportBarChart(personID, reportCardDataBean);
					if (reportCardDataBean.getReportList() == null) {
						logger.debug("Null");
						fieldName = CommonValidate.findComponentInRoot("barstudent").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("No Records Found."));
					} else {
						logger.debug(reportCardDataBean.getReportList().size());
						createLineModels();
						setBarflag(true);
					}
					logger.debug("Inside MB" + status);
				}

			}

		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return "";

	}	
	
	/**
	 * @return the barModel
	 */
	public BarChartModel getBarModel() {
		return barModel;
	}

	/**
	 * @param barModel
	 *            the barModel to set
	 */
	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	/**
	 * @return the barflag
	 */
	public boolean isBarflag() {
		return barflag;
	}

	/**
	 * @param barflag
	 *            the barflag to set
	 */
	public void setBarflag(boolean barflag) {
		this.barflag = barflag;
	}

	/*
	 * Use to create chart file as a image
	 */
	public String submittedBase64Str() {
		// You probably want to have a more comprehensive check here.
		// In this example I only use a simple check
		logger.debug("----------------Inside Submit");
		// Create Directory
		File files = new File(paths.getString("sms.reportcard.pdf") + ft.format(now));
		if (!files.exists()) {
			files.mkdirs();
		} else {
			logger.debug("Alreday Found");
		}

		if (base64Str.split(",").length > 1) {
			String encoded = base64Str.split(",")[1];
			byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(encoded);
			// Write to a .png file
			try {
				RenderedImage renderedImage = ImageIO.read(new ByteArrayInputStream(decoded));
				ImageIO.write(renderedImage, "png",
						new File(files + paths.getString("path_context").toString() + reportCardDataBean.getBarstudentid() + ".png")); // use
																									// a
																									// proper
																									// path
																									// &
																									// file
																									// name
																									// here.
			} catch (IOException e) {
				logger.warn("Inside Exception",e);
			}
		}
		return "";
	}

	/**
	 * @return the base64Str
	 */
	public String getBase64Str() {
		return base64Str;
	}

	/**
	 * @param base64Str
	 *            the base64Str to set
	 */
	public void setBase64Str(String base64Str) {
		this.base64Str = base64Str;
	}

	/**
	 * @return the file
	 */
	public StreamedContent getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(StreamedContent file) {
		this.file = file;
	}

	/**
	 * @return the renderflag
	 */
	public boolean isRenderflag() {
		return renderflag;
	}

	/**
	 * @param renderflag
	 *            the renderflag to set
	 */
	public void setRenderflag(boolean renderflag) {
		this.renderflag = renderflag;
	}

	/*
	 * public ReportCardViewMB() { String
	 * s="C://Chart/ReportCards/"+ft.format(now)+"/"+reportCardDataBean.
	 * getBarstudentid()+".png"; InputStream stream = new
	 * ByteArrayInputStream(s.getBytes()); file = new
	 * DefaultStreamedContent(stream, "image/jpg", "downloaded_optimus.png"); }
	 * 
	 */
	
	
    private LineChartModel lineModel2;
     
    public LineChartModel getLineModel2() {
        return lineModel2;
    }
     
    private void createLineModels() {
        lineModel2 = initCategoryModel();
        lineModel2.setTitle("Report Card Chart");
        lineModel2.setLegendPosition("ne");
        lineModel2.setShowPointLabels(true);
        lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Subjects"));
        Axis yAxis = lineModel2.getAxis(AxisType.Y);
        yAxis.setLabel("Marks");
        yAxis.setMin(0);
        yAxis.setMax(120);
    }
     
    private LineChartModel initCategoryModel() {
        LineChartModel model = new LineChartModel();
      //  ChartSeries exams = new ChartSeries();
        List<String> examList=new ArrayList<String>();
        List<ChartSeries> series = new ArrayList<ChartSeries>();
        int marks=0;
        try{
        	if (reportCardDataBean.getReportList() == null) {
				logger.debug("Inside Null Pointer");
			} else {
				if (reportCardDataBean.getReportList().size()>0){
					for (int i = 0; i < reportCardDataBean.getReportList().size(); i++) {
						examList.add(reportCardDataBean.getReportList().get(i).getExamTitle());
					}
					HashSet<String> hashexams=new HashSet<String>(examList); examList.clear(); examList.addAll(hashexams);
					for (int i = 0; i < examList.size(); i++) {
						series.add(new ChartSeries(examList.get(i)));
					}
					for (int i = 0; i < series.size(); i++) {
						for (int j = 0; j < reportCardDataBean.getReportList().size(); j++) {
							if(reportCardDataBean.getReportList().get(j).getExamTitle().equalsIgnoreCase(series.get(i).getLabel())){
								marks=Integer.parseInt(reportCardDataBean.getReportList().get(j).getMark());
								series.get(i).set(reportCardDataBean.getReportList().get(j).getSubject().getSujectName(),marks);
							}
						}
						model.addSeries(series.get(i));
					}
				}
			}
        }catch(Exception e){
        	logger.warn(" exception - "+e);
        }
        return model;
    }
    
    public boolean validate(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (StringUtils.isEmpty(reportCardDataBean.getStudMarkClass())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("studMarkViewClass").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Class."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(reportCardDataBean.getExamMarkTitle())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("markViewExamTitle").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Exam Title."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(reportCardDataBean.getViewMarkStuName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("markViewStudName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Student Name."));
			}
			valid = false;
		} /*
			 * else
			 * if(!StringUtils.isEmpty(reportCardDataBean.getViewMarkStuName()))
			 * { if(!CommonValidate.validateName(reportCardDataBean.
			 * getViewMarkStuName())){ if(flag){ fieldName =
			 * CommonValidate.findComponentInRoot("markViewStudName").
			 * getClientId(fc); fc.addMessage(fieldName, new FacesMessage(
			 * "Please Enter the valid Stduent Name.")); } valid = false; } }
			 */
		return valid;
	}
	
	private boolean validatebar(boolean valid) {
		String roll = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role");
		valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if(roll.equalsIgnoreCase("Admin") || roll.equalsIgnoreCase("Teacher")){
			if(StringUtils.isEmpty(reportCardDataBean.getStudMarkClass())){
				fieldName = CommonValidate.findComponentInRoot("barclass").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.select")));
				valid = false;
			}
		}
		if(StringUtils.isEmpty(reportCardDataBean.getBarstudentid())) {
			fieldName = CommonValidate.findComponentInRoot("barstudent").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage(text.getString("sms.parent.studentID")));
			valid = false;
		}

		return valid;
	}
	
	public String barchartpageLoad() {
		reportCardDataBean.setBarclassid("");
		reportCardDataBean.setBarstudentid("");
		reportCardDataBean.setSearchname("");
		reportCardDataBean.setName("");
		reportCardDataBean.setStudMarkClass("");
		SmsController controller = null;
		AttendanceDataBean attendanceDataBean=new AttendanceDataBean();
		setBarflag(false);
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String roll = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role");
			if (personID != null) {
				ApplicationContext ctx = FacesContextUtils
						.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				studentIDList=new ArrayList<String>();
				classSectionList=new ArrayList<String>();
				if (roll.equalsIgnoreCase("Student")) {
					renderflag = false;
					String status = controller.getReportBarChart(personID, reportCardDataBean);
					if (reportCardDataBean.getReportList() == null) {
						logger.debug("Null");
						reportCardDataBean.setName("No Records Found.");
					} else {
						logger.debug(reportCardDataBean.getReportList().size());
						reportCardDataBean.setName("");
						createLineModels();
						setBarflag(true);
					}
					logger.debug("Inside MB" + status);
				} else if(roll.equalsIgnoreCase("Parent")){
					renderflag=false;
					studentIDList=controller.parentAttlist(personID, attendanceDataBean);
					Collections.sort(studentIDList);
				}else if (roll.equalsIgnoreCase("Admin") || roll.equalsIgnoreCase("Teacher")) {
					classSectionList = controller.getClassNameList(personID);
					HashSet<String> hashclass = new HashSet<String>(classSectionList);
					classSectionList.clear();
					classSectionList.addAll(hashclass);
					Collections.sort(classSectionList);
					renderflag=true;
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return "repoartbar";

	}
	
	private boolean classstuflag = false;
	
	public boolean isClassstuflag() {
		return classstuflag;
	}

	public void setClassstuflag(boolean classstuflag) {
		this.classstuflag = classstuflag;
	}

	public String marksheetviewpage() {
			classSectionList = null;
			studentIDList = null;
			examtitle = null;
			SmsController controller = null;
			try {
				logger.debug("------marksheetviewPage calling------");
				reportCardDataBean.setStudMarkClass("");
				reportCardDataBean.setExamMarkTitle("");
				reportCardDataBean.setViewMarkStuName("");
				classSectionList = new ArrayList<String>();
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				String roll = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role");

				if (personID != null) {
					classSectionList = controller.getClassNameList(personID);
					HashSet<String> hashclass = new HashSet<String>(classSectionList);
					classSectionList.clear();
					classSectionList.addAll(hashclass);
					Collections.sort(classSectionList);
					if (roll.equalsIgnoreCase("Admin")) {
						setEditFlag(true);
					} else {
						setEditFlag(false);
					}
					return "marksheetview";
				}
			} catch (Exception e) {
				logger.debug("-------marksheetviewPage Exception Calling------");
				logger.warn("Exception -->"+e.getMessage());
			}

			return "";
		}
	
	//Ragulan nov 25
	public String markreportpage(){
		SmsController controller=null;
		try{
			setTableflag(false);
			setChartFlag(false);
			setSubjectflag(false);
			reportCardDataBean.setStudMarkClass("");
			reportCardDataBean.setSchoolName("");
			reportCardDataBean.setExamMarkTitle("");
			reportCardDataBean.setGrade("");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
			if(personID!=null){
			schoollist=new ArrayList<String>();
			classSectionList=new ArrayList<String>();
			schoollist.add(schoolName);
			classSectionList=controller.getClassNameList(personID);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "studentMarkreport";
	}
	
	public void classsectionchange(ValueChangeEvent event) {
		SmsController controller = null;
		setSubjectflag(false);
		try {
			examtitle = new ArrayList<String>();
			subjectlist=new ArrayList<String>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String classname=event.getNewValue().toString();
			if (classname != null) {
				if(classname.equalsIgnoreCase("All")){
				examtitle = controller.getExamTitleName(personID, classname);
				setSubjectflag(false);
				}else{
					setSubjectflag(true);
					examtitle = controller.getExamTitleName(personID, classname);
					subjectlist=controller.getSubjectListTeacher(personID,classname.split("/")[0]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getmarkreport(){
		SmsController controller=null;
		try{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if(personID!=null){
				if(validation(true)){
					studentMarkList=new ArrayList<ReportCardDataBean>();
					studentMarkList=controller.getmarks(personID, reportCardDataBean);
					setTableflag(true);
					setChartFlag(false);
				}
			}
		}catch(Exception w){
			w.printStackTrace();
		}
		return "";
	}

	private boolean validation(boolean valid) {
		valid=true;
		String name;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(StringUtils.isEmpty(reportCardDataBean.getSchoolName())){
			name=CommonValidate.findComponentInRoot("schoolname").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Choose The SchoolName"));
			valid=false;
		}
		if(StringUtils.isEmpty(reportCardDataBean.getStudMarkClass())){
			name=CommonValidate.findComponentInRoot("classname").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Choose The Class"));
			valid=false;
		}
		if(StringUtils.isEmpty(reportCardDataBean.getExamMarkTitle())){
			name=CommonValidate.findComponentInRoot("examname").getClientId();
			fc.addMessage(name, new FacesMessage("Please Select The ExamTitle"));
			valid=false;
		}
		if(StringUtils.isEmpty(reportCardDataBean.getGrade())){
			name=CommonValidate.findComponentInRoot("year").getClientId();
			fc.addMessage(name, new FacesMessage("Please Select The Year"));
			valid=false;
		}
		return valid;
		
	}
	
	public void createBarModel(){
		setChartFlag(true);
		barchart = initBarModel();
		barchart.setTitle("Bar Chart");
		barchart.setLegendPosition("ne");
		Axis xAxis = barchart.getAxis(AxisType.X);
    	xAxis.setLabel("Exam Title");
    	Axis yAxis = barchart.getAxis(AxisType.Y);
    	yAxis.setLabel("Mark");
    	yAxis.setMin(0);
    	yAxis.setMax(100);
	}

	private BarChartModel initBarModel() {
		BarChartModel model=null;
		try{
		model = new BarChartModel();
        if(studentMarkList.size()>0){
        	for (int i = 0; i < studentMarkList.size(); i++) {
        		ChartSeries attendance = new ChartSeries();
        		 attendance.setLabel(studentMarkList.get(i).getSearchname());
				attendance.set(studentMarkList.get(i).getExamMarkTitle(), Integer.parseInt(studentMarkList.get(i).getMark()));
				model.addSeries(attendance);
				}
        }
		}catch(Exception q){
			q.printStackTrace();
		}
		return model;
	}
}