package com.stumosys.managedBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
//import java.util.Properties;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.stumosys.controler.SmsController;
import com.stumosys.domain.PaymentDatabean;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name="feesPaymentViewMB")
public class FeesPaymentViewMB {
	
	PaymentDatabean paymentDatabean=new PaymentDatabean();
	private static Logger logger = Logger.getLogger(FeesPaymentViewMB.class);
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text =  context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	public List<String> classNames=null;
	public List<String> students=null;
	public String personID="";	
	UploadedFile file;
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	Date now = new Date();
	private boolean editFlag=false;
	private List<PaymentDatabean> paymentList = null;
	private boolean delboxflag=false;
	private StreamedContent streamedContent;
	private boolean flag=false;
	private StreamedContent stream;
	public  String classflag="1";
	private boolean studentlistFlag=false;
		private boolean schoolFlag=false;
	 
	 public boolean isSchoolFlag() {
		return schoolFlag;
	}
	public void setSchoolFlag(boolean schoolFlag) {
		this.schoolFlag = schoolFlag;
	}
	 
	 public boolean isStudentlistFlag() {
	 	return studentlistFlag;
	 }
	 public void setStudentlistFlag(boolean studentlistFlag) {
	 	this.studentlistFlag = studentlistFlag;
	 }
	 	
	public String getClassflag() {
		return classflag;
	}

	public void setClassflag(String classflag) {
		this.classflag = classflag;
	}
	public StreamedContent getStream() {
		return stream;
	}

	public void setStream(StreamedContent stream) {
		this.stream = stream;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public StreamedContent getStreamedContent() {
		return streamedContent;
	}

	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}

	public boolean isDelboxflag() {
		return delboxflag;
	}

	public void setDelboxflag(boolean delboxflag) {
		this.delboxflag = delboxflag;
	}

	public List<PaymentDatabean> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<PaymentDatabean> paymentList) {
		this.paymentList = paymentList;
	}

	public boolean isEditFlag() {
		return editFlag;
	}

	public void setEditFlag(boolean editFlag) {
		this.editFlag = editFlag;
	}

	public SimpleDateFormat getFt() {
		return ft;
	}

	public void setFt(SimpleDateFormat ft) {
		this.ft = ft;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}

	public List<String> getClassNames() {
		return classNames;
	}

	public void setClassNames(List<String> classNames) {
		this.classNames = classNames;
	}

	public List<String> getStudents() {
		return students;
	}

	public void setStudents(List<String> students) {
		this.students = students;
	}

	public PaymentDatabean getPaymentDatabean() {
		return paymentDatabean;
	}

	public void setPaymentDatabean(PaymentDatabean paymentDatabean) {
		this.paymentDatabean = paymentDatabean;
	}	
	
	public void classChange(ValueChangeEvent v){
		SmsController controller=null;
		setStudents(null);paymentDatabean.setPaymentStudID("");
		try{
			personID=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID").toString();
			ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(SmsController) ctx.getBean("controller");
			if(personID!=null){
				students=controller.classStudent(personID,v.getNewValue().toString());
				Collections.sort(students);
				logger.debug("students "+students);
			}
		}
		catch(Exception e){
			logger.warn("inside exception "+e);
		}
	}
	
	public void studentChange(ValueChangeEvent v){
		SmsController controller=null;
		paymentDatabean.setFlag(false);paymentDatabean.setFlag1(false);
		String fieldName;file=null;paymentDatabean.setUpFile(null);
		FacesContext fc = FacesContext.getCurrentInstance();
		try{
			personID=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID").toString();
			ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(SmsController) ctx.getBean("controller");
			paymentDatabean.setPaymentStudID(v.getNewValue().toString());
			if(personID!=null){
				controller.classStudentFeesView(personID,paymentDatabean);		
				paymentDatabean.setUpflag(false);
				if(paymentDatabean.getTotalFees().equalsIgnoreCase("")){
					fieldName = CommonValidate.findComponentInRoot("studentid").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Did not register the Fees"));
				}
			}
		}
		catch(Exception e){
			logger.warn("inside exception "+e);
		}
	}
	
	public String uploadFile(){
		logger.debug("inside uppload");
		paymentDatabean.setPayflag(false);
		paymentDatabean.setUpflag(true);
	    return "";
	}
	
	public String dummyAction(FileUploadEvent event) throws IOException {
		this.file = event.getFile();
		paymentDatabean.setUpFile(event.getFile());
		FacesMessage message = new FacesMessage("Succesfully "+event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);
		return "";
	}
	
	public String loadImage(){
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		logger.debug("inside image load");
		try{
			personID=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID").toString();
			if(personID!=null){
				if(paymentDatabean.getUpFile()==null){
					fieldName = CommonValidate.findComponentInRoot("file").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please upload the file"));
				}
				else{
					RequestContext reqcontext = RequestContext.getCurrentInstance();
					reqcontext.execute("feesBlk()");
				}
			}
		}
		catch(Exception e){
			logger.warn("Inside Exception",e);
		}
		return"";
	}	
	
	private void uploadFile(String fileName, InputStream inputstream, String n) {
		try {
			File files = new File(paths.getString("sms.feespay.photo") + ft.format(now));
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
	
	/*private String sendEmail(String personID, PaymentDatabean paymentDatabean) {
		String status = "Fail";
		try {
			String to = paymentDatabean.getMailId();
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
					+ "<h3>Dear Sir/Madam ,</h3>"
					+ "<p>File is uploaded from "+paymentDatabean.getParentID() +" please check </p>"
					+ "<footer>" + "<center>"
					+ "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>" + "</body></html>";

			Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
				}
			});
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("File Uploaded  ");
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
			logger.warn("Inside Exception",e);
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}*/
	
public void classPaymentDetails(){
		SmsController controller=null;
		String schoolID="";
		try{
			schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			personID=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID").toString();
			ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(SmsController) ctx.getBean("controller");
			if(personID!=null){
				paymentList = new ArrayList<PaymentDatabean>();
				paymentList = controller.feesinfomation(personID, paymentDatabean);
				if(schoolID.equalsIgnoreCase(paths.getString("CNPS.SCHOOLID")) ||
						schoolID.equalsIgnoreCase(paths.getString("SCMS.SCHOOLID")) ){
					setFlag(false);
					setSchoolFlag(true);
				}else{
					setFlag(true);
					setSchoolFlag(false);
				}
			}
		}
		catch(Exception e){
			logger.warn("inside exception "+e);
		}
	}

	public void paymentinfo(PaymentDatabean paymentDatabean){
		SmsController controller = null;
		try {
			logger.debug("inside payment info method calling");
			if (paymentDatabean.getPaymentStudID() != null) {
				paymentDatabean.setPaymentStudID(paymentDatabean.getPaymentStudID());
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				if (personID != null) {
					controller.paymentview(personID, paymentDatabean);
					getPaymentDatabean().setPayClassSection(paymentDatabean.getPayClassSection());
					getPaymentDatabean().setPaymentStudID(paymentDatabean.getPaymentStudID());
					getPaymentDatabean().setExamFees(paymentDatabean.getExamFees());
					getPaymentDatabean().setTuitionFees(paymentDatabean.getTuitionFees());
					getPaymentDatabean().setTransportFees(paymentDatabean.getTransportFees());
					getPaymentDatabean().setTotalFees(paymentDatabean.getTotalFees());
					getPaymentDatabean().setFilePath(paymentDatabean.getFilePath());
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
	}
	public String paymentEdit() {
		SmsController controller = null;
		try {
				if (paymentDatabean.getPaymentStudID() != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				if (personID != null) {
					controller.paymentview(personID, paymentDatabean);
				}

			}
		} catch (Exception e) {
			logger.warn("Inside Exception"+e.getMessage());
		}
		return "paymentEdit";
	}
	
	
	public boolean Notvalidate(boolean flag){
		flag=true;
		return flag;
	}
	
	public boolean validate(boolean flag) {
		//boolean flag=false;
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		
		if (StringUtils.isEmpty(paymentDatabean.getExamFees())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("payexamFees").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.examfees")));
			}
			valid = false;
		} 
		
		else if (!StringUtils.isEmpty(paymentDatabean.getExamFees())) {
			if (!CommonValidate.validateNumberOnly(paymentDatabean.getExamFees())) {
				logger.debug("character");
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("payexamFees").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vexamfees")));
				}
				valid = false;
			}
			else if(new BigDecimal(paymentDatabean.getExamFees()).compareTo(BigDecimal.ZERO)==0){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("payexamFees").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vexamfees")));
				}
				valid = false;
			}
		}
		
		// empty
		if (StringUtils.isEmpty(paymentDatabean.getTuitionFees())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("paytuiFees").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.tuitionfees")));
			}
			valid = false;
		} 
		
		else if (!StringUtils.isEmpty(paymentDatabean.getTuitionFees())) {
			if (!CommonValidate.validateNumberOnly(paymentDatabean.getTuitionFees())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("paytuiFees").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vtuitionfees")));
				}
				valid = false;
			}
			
			else if(new BigDecimal(paymentDatabean.getTuitionFees()).compareTo(BigDecimal.ZERO)==0){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("paytuiFees").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vtuitionfees")));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(paymentDatabean.getTransportFees())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("paytransFees").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.transportfees")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(paymentDatabean.getTransportFees())) {
			if (!CommonValidate.validateNumberOnly(paymentDatabean.getTransportFees())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("paytransFees").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vtransportfees")));
				}
				valid = false;
			}
			else if(new BigDecimal(paymentDatabean.getTransportFees()).compareTo(BigDecimal.ZERO)==0){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("paytransFees").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vtransportfees")));
				}
				valid = false;
			}
		}
		return valid;
	
	}
	
	
	
	public void deletePayment() {
		SmsController controller = null;
		try {
			if (paymentDatabean.getPaymentStudID() != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
			    String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
				if (personID != null) {
					if(paths.getString("SCMS.SCHOOLID").equalsIgnoreCase(schoolID)){
			    		now = new SimpleDateFormat("MMMM").parse(paymentDatabean.getMonth());
			    	    Calendar cal = Calendar.getInstance();
			    	    cal.setTime(now);
			    	    int month = cal.get(Calendar.MONTH);
			    	    paymentDatabean.setMonth(String.valueOf(month+1));
			    		paymentDatabean.setYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
					}
					String status = controller.deletePayment(personID, paymentDatabean);
					if(status.equalsIgnoreCase("Success")){
						setDelboxflag(true);
					}else{
						setDelboxflag(false);
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
	}
	
	public void file(PaymentDatabean paymentDatabean) throws FileNotFoundException {
		SmsController controller = null;
	    try{
	    	InputStream input=null;
	    	ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {				
				controller.paymentview(personID, paymentDatabean);
				logger.debug("path "+paymentDatabean.getFilePath());
				String path = paymentDatabean.getFilePath();
				File file=new File(paths.getString("sms.feespay.photo").toString()+path);
				input = new FileInputStream(file);
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				this.setStream(new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()), file.getName()));
				
				paymentDatabean.setPaymentStudID("");
				paymentDatabean.setPayClassSection("");paymentDatabean.setFilePath("");
			}
	    }
	    catch(Exception e){
	    	logger.warn(" exception - "+e);
	    	logger.warn("Inside Exception",e);
	    }
	   }

	public String loadApprove(){
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("LogID");
		if (personID != null) {
			RequestContext reqcontext = RequestContext.getCurrentInstance();
			reqcontext.execute("feesApproBlk()");
		}		
		return "";
	}
	
	public void approvePayment() {
		SmsController controller = null;
		try {
			if (paymentDatabean.getPaymentStudID() != null) {
				String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				if (personID != null) {
					paymentDatabean.setStatus("Approved");
					controller.approveRejectFees(personID, paymentDatabean);
					MailSendJDBC.paymentApprove(paymentDatabean,schoolName);
					/*sendEmails(personID, paymentDatabean);*/
					RequestContext reqcontext = RequestContext.getCurrentInstance();
					reqcontext.execute("PF('feesApprblockUI').hide();");
					reqcontext.execute("PF('approvalDialog').show();");
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
	}
	
	public String loadreject(){
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("LogID");
		if (personID != null) {
			RequestContext reqcontext = RequestContext.getCurrentInstance();
			reqcontext.execute("feesRejecBlk()");
		}		
		return "";
	}
	public void rejectPayment() {
		SmsController controller = null;
		try {
			if (paymentDatabean.getPaymentStudID() != null) {
				String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				if (personID != null) {
					paymentDatabean.setStatus("Rejected");
					controller.approveRejectFees(personID, paymentDatabean);
					MailSendJDBC.paymentApprove(paymentDatabean,schoolName);
					/*sendEmails(personID, paymentDatabean);*/
					RequestContext reqcontext = RequestContext.getCurrentInstance();
					reqcontext.execute("PF('feesApprblockUI').hide();");
					reqcontext.execute("PF('rejectDialog').show();");
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
	}
	
	//While update - Empty or Null validation check 
	
	
	public String feesupdatepage() {
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		boolean flag=false;
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		
		
		  try {
		   logger.info("------feesupdatepage method calling--------");
		   
		   if(paths.getString("SMAN.SCHOOL_ID").equalsIgnoreCase(schoolID)) {
			   if(Notvalidate(true)){

				    String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				      .get("LogID");
				    if (personID != null) {
				      RequestContext reqcontextt = RequestContext.getCurrentInstance();
				      reqcontextt.execute("feesUpdateBlk();");// calling JavaScript
				    }
				   
			   }
			   
		   }
		   
		   else  {
			
			   if (validate(true)) {
				    String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				      .get("LogID");
				    if (personID != null) {
				      RequestContext reqcontextt = RequestContext.getCurrentInstance();
				      reqcontextt.execute("feesUpdateBlk();");// calling JavaScript
				    }
				   }
		   }
		} catch (Exception e) {
		   logger.warn("Exception -->"+e.getMessage());
		   return "failure";
		  }
		  return "";
		 }
		 public void updatePayment() {
		  SmsController controller = null;
		  String totalFees="";
		  try {
		   if (paymentDatabean.getPaymentStudID() != null) {
			   String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
		    ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		    controller = (SmsController) ctx.getBean("controller");
		    String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
		      .get("LogID");
		    String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		    int schoolid=Integer.parseInt(schoolID);
		    RequestContext reqcontextt = RequestContext.getCurrentInstance();
		    if (personID != null) {
		    	if(paths.getString("SCMS.SCHOOLID").equalsIgnoreCase(schoolID)){
		    		now = new SimpleDateFormat("MMMM").parse(paymentDatabean.getMonth());
		    	    Calendar cal = Calendar.getInstance();
		    	    cal.setTime(now);
		    	    int month = cal.get(Calendar.MONTH);
		    	    paymentDatabean.setMonth(String.valueOf(month+1));
		    		paymentDatabean.setYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
					if("June".equalsIgnoreCase(paymentDatabean.getMonth())){
						BigDecimal temp =new BigDecimal(paymentDatabean.getTransportFees()).add(new BigDecimal(paymentDatabean.getTuitionFees()));
						BigDecimal temp1=new BigDecimal(paymentDatabean.getSpecialFees()).add(new BigDecimal(paymentDatabean.getEcaFees()));
						BigDecimal temp2=new BigDecimal(paymentDatabean.getLabFees()).add(new BigDecimal(paymentDatabean.getAdmissionFees()));
						totalFees=temp.add(temp1).add(temp2).toString();
						paymentDatabean.setTotalFees(totalFees);
					}else{
						totalFees =new BigDecimal(paymentDatabean.getTransportFees()).add(new BigDecimal(paymentDatabean.getTuitionFees())).toString();
						paymentDatabean.setTotalFees(totalFees);
					}
				}
		      String status = controller.updatePayment(personID, paymentDatabean);
		      if(status.equalsIgnoreCase("Success")){
		    	  MailSendJDBC.paymentUpdate(paymentDatabean,schoolName,schoolid);
		      /* String emailstatus=sendUpdateEmail(personID,paymentDatabean);
		       if(emailstatus.equalsIgnoreCase("Success")){*/
		        reqcontextt.execute("PF('feesUpdateblockUI').hide();");
		        reqcontextt.execute("PF('paymentEditDialog').show();");
		        setEditFlag(true);
		       /*}
		       else{
		        reqcontextt.execute("PF('feesUpdateblockUI').show();");
		        reqcontextt.execute("PF('paymentEditDialog').hide();");
		        setEditFlag(true);
		       }*/
		      }else{
		    	  reqcontextt.execute("PF('feesUpdateblockUI').show();");
			       reqcontextt.execute("PF('paymentEditDialog').hide();");
		       setEditFlag(false);
		      }
		    }
		   }
		  }catch(NumberFormatException n){
			  FacesMessage message=new FacesMessage("Please enter the amounts should be number");
			  FacesContext.getCurrentInstance().addMessage(CommonValidate.findComponentInRoot("feeseditBlk").getClientId(), message);
			  logger.warn("Inside Exception"+n.getMessage());
		  } catch (Exception e) {
		   logger.warn("Inside Exception",e);
		  }
		 }
		 
	/* private String sendUpdateEmail(String personID, PaymentDatabean paymentDatabean) {
		String status = "Fail";
		try {
			String to = paymentDatabean.getMailId();
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
					+ "<h3>Dear " + paymentDatabean.getParentID()+ ",</h3>"
					+ "<p>Welcome to join  our Student Monitoring System. "
					+ "please check your updated child fees structure ."
					+ "Total Fees  :  Rs.  "+paymentDatabean.getTotalFees()+"</p>"
					+ "<footer>" + "<center>"
					+ "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>" + "</body></html>";


			Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
				}
			});
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("Updated Fees Structure");
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
			logger.warn("Inside Exception",e);
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}*/
	 
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
	 public String feesViewCall(){
	 		logger.debug("fees payment view call ");
	 		SmsController controller=null;		
	 		setFlag(false);
			setSchoolFlag(false);
	 		try{
	 			personID=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID").toString();
	 			ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	 			controller=(SmsController) ctx.getBean("controller");
	 			if(personID!=null){
	 				classNames=controller.getClassNameList(personID);
	 				HashSet<String> classes=new HashSet<String>(classNames);
	 				classNames.clear();classNames.addAll(classes);
	 				Collections.sort(classNames);
	 				logger.debug("classes "+classNames);
	 				if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role").equals("Admin") || 
	 						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role").equals("Teacher")){	
	 					setFlag(false);
	 					return "paymentInfo";
	 				}
	 				else if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role").equals("Parent")){	
	 					setFlag(false);
	 					return "feesPaymentPar";
	 				}
	 				else if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role").equals("Student")){
	 					paymentList = new ArrayList<PaymentDatabean>();
	 					classflag="none";
	 					paymentList = controller.feesinfomation(personID, paymentDatabean);
	 					setStudentlistFlag(true);
	 					return "paymentInfo";
	 				}
	 			}			
	 		}
	 		catch(Exception e){
	 			logger.warn("inside exception "+e);
	 		}
	 		finally{
	 			paymentDatabean.setPayClassSection("");
	 			paymentDatabean.setPaymentStudID("");
	 			paymentDatabean.setFlag(false);paymentDatabean.setFlag1(false);
	 			setEditFlag(false);setDelboxflag(false);
	 			paymentDatabean.setMonth("");
	 		}
	 		return "";		
	 	}
	 	
	 	
	 	public String payNow(){
	 		logger.debug("inside upload");
	 		SmsController controller=null;	
	 		try{
	 			String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
	 			personID=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID").toString();
	 			ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	 			controller=(SmsController) ctx.getBean("controller");
	 			if(personID!=null){				
	 				String s = paymentDatabean.getUpFile().getContentType();
	 				String type = s.substring(s.lastIndexOf("/") + 1);
	 				String students=paymentDatabean.getPaymentStudID();
	 				String studentid=students.substring(students.lastIndexOf("/")+1);
	 				uploadFile(studentid, paymentDatabean.getUpFile().getInputstream(), type);
	 				String path = ft.format(now) + "/" + studentid + "." + type;
	 				paymentDatabean.setFilePath(path);
	 				controller.feespay(personID,paymentDatabean);
	 				MailSendJDBC.feespay(paymentDatabean,schoolName);
	 				/*sendEmail(personID, paymentDatabean);*/
	 				RequestContext context=RequestContext.getCurrentInstance();
	 				context.execute("PF('feesPayblockUI').hide();");
	 				context.execute("PF('confirmDialog').show();");
	 			}
	 		}
	 		catch(Exception e){
	 			logger.warn("inside exception "+e);
	 			logger.warn("Inside Exception",e);
	 		}
	 		return "";
	 	}
}
