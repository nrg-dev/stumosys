package com.stumosys.util;

import java.sql.Connection;
//import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.io.IOException;


//import javax.faces.context.FacesContext;

//import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;


import com.stumosys.domain.AttendanceDataBean;
import com.stumosys.domain.ClassTimeTableDataBean;
import com.stumosys.domain.HomeworkDatabean;
import com.stumosys.domain.LibrarianDataBean;
import com.stumosys.domain.LoginAccess;
import com.stumosys.domain.NoticeBoardDataBean;
import com.stumosys.domain.ParentsDataBean;
import com.stumosys.domain.PaymentDatabean;
import com.stumosys.domain.RegisteredLogin;
import com.stumosys.domain.ReportCardDataBean;
import com.stumosys.domain.StaffDataBean;
import com.stumosys.domain.StudentDataBean;
import com.stumosys.domain.StudentPerformanceDataBean;
import com.stumosys.domain.TeacherDataBean;
import com.stumosys.domain.TimeTableDataBean;


public class MailSendJDBC {
	static ResourceBundle database=ResourceBundle.getBundle("com.sms.database");
	private static Logger logger = Logger.getLogger(MailSendJDBC.class);
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL_EMAIL = "jdbc:mysql://35.166.255.46:3306/PollerDB";//local Linux , It is for only email not sms	
	static final String DB_URL_SMS = "jdbc:mysql://52.24.234.95:3306/PollerDB"; // This is fore SMS 	
	static final String DBURL = "jdbc:mysql://35.166.255.46:3306/StumosysDB";
	static final String USER =  database.getString("USER");
	static final String PASS = database.getString("PASSWORD");
	public static Connection con = null; 
	public static Statement stmt=null;
	public static PreparedStatement preparedStatement=null; 
	static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	public static List<String> mails=null;
	public static List<String> phones=null;
	static final String Query ="insert into email_temp (toaddress,subject,message,status,date,school_ID) values(?,?,?,?,?,?)";
	static final String smsQuery ="insert into sms_temp (sender,receiver,message,status,date,school_ID) values(?,?,?,?,?,?)";
	static Locale locale = new Locale("en", "US");
	static ResourceBundle paths = ResourceBundle.getBundle("email",locale);
	static final Date date = new Date();
	static final java.sql.Date currentDate = new java.sql.Date(date.getTime());	
	private static String messagesID() throws SQLException, ClassNotFoundException{
		return "";
	}
	
	// Teacher Registration for Email Sending 
	public static String teacherInsert(TeacherDataBean teacherDataBean, String schoolName,String schoolID) throws SQLException, IOException{
		logger.info("------------------- teacherInsert start ----------------------");
		logger.debug("teacher school "+schoolName);
		String status="fail";
		String messege="";
		try{
			
			messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")
			+paths.getString("EMAIL.TEMPLATES.USERNAMEPASS")
			+ paths.getString("EMAIL.TEMPLATES.MIDDLE")+paths.getString("EMAIL.TEMPLATES.USERNAMEPASSTITLE")+"<td>"+
			teacherDataBean.getTeaUsername()+
			"</td><td>"+teacherDataBean.getTeaSecurePasword()+"</td><td>"+"Teacher"+"</td>"+paths.getString("EMAIL.TEMPLATES.END");
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(teacherDataBean.getTeaEmail(),"Stumosys - Teacher Registration Confirmed",messege);
			logger.info("Successfully  Email Called Service------------");	
			
			/*
			int schoolid=Integer.parseInt(schoolID);
			if (!"".equalsIgnoreCase(teacherDataBean.getTeaEmail()) && teacherDataBean.getTeaEmail() !=null) {
			Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			stmt=con.createStatement();
			preparedStatement=con.prepareStatement(Query);
			messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")
					+paths.getString("EMAIL.TEMPLATES.USERNAMEPASS")
					+ paths.getString("EMAIL.TEMPLATES.MIDDLE")+paths.getString("EMAIL.TEMPLATES.USERNAMEPASSTITLE")+"<td>"+
					teacherDataBean.getTeaUsername()+
					"</td><td>"+teacherDataBean.getTeaSecurePasword()+"</td><td>"+"Teacher"+"</td>"+paths.getString("EMAIL.TEMPLATES.END");
			preparedStatement.setString(1, teacherDataBean.getTeaEmail());
			preparedStatement.setString(2, "STUMOSYS REGISTRATION CONFIRMED-" + teacherDataBean.getTeaUsername()); 
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."
						+ " Log-in ID: "+teacherDataBean.getTeaUsername()+" Password: "+teacherDataBean.getTeaSecurePasword()+" Ini bersifat personal. Selamat Menggunakan.");
			}else{
			preparedStatement.setString(3,messege );
			}
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, schoolID);
			preparedStatement.executeUpdate();
			logger.info("TeacherInsert Email Was successfully inserted into emailtemp table");
			}
			else{
				logger.info("No Emails are Available");
			}
		if (teacherDataBean.getCode()!="" && teacherDataBean.getTeaPhoneNo()!="") {
			
				if(teacherDataBean.getTeaPhoneNo()!=null) {
			preparedStatement=getSMSDB(con);
			preparedStatement.setString(1, "Stumosys");
			preparedStatement.setString(2,teacherDataBean.getCode()+teacherDataBean.getTeaPhoneNo()); 
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6 ){
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."
						+ " Log-in ID: "+teacherDataBean.getTeaUsername()+" Password: "+teacherDataBean.getTeaSecurePasword()+" Ini bersifat personal. Selamat Menggunakan."
								+paths.getString("SMS.TEMPLATES.LINK"));
				}
			else{
				preparedStatement.setString(3, "Welcome to STUMOSYS Login Here http://www.stumosys.neotural.com With Your "
						+ "User Name " + teacherDataBean.getTeaUsername() + " and Password "+ teacherDataBean.getTeaSecurePasword()
						+paths.getString("SMS.TEMPLATES.LINK"));
			}
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, schoolID);
			preparedStatement.executeUpdate();
			
			System.out.println("------------------- teacherInsert end ----------------------");	
				}else 
				{
					logger.info("Phone number is Null");
				}
	
		}
			status="Success";
		*/
		
		status="success";	
			
			
		
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
						//e.printStackTrace();
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
		return status;
	}
	
	
	public static void mailPhone() throws SQLException{
		if(mails.size() > 0){
			mails.removeAll(Collections.singleton(null));  //remove null values from the list
			mails.removeAll(Collections.singleton(""));  //remove "" values from the list
			Set<String> dublicate=new HashSet<String>();
			dublicate.addAll(mails);
			mails.clear();
			mails.addAll(dublicate);
		}
		if(phones.size() > 0){
			phones.removeAll(Collections.singleton(null));  //remove null values from the list
			phones.removeAll(Collections.singleton(""));  //remove "" values from the list
			Set<String> dublicates=new HashSet<String>();
			dublicates.addAll(phones);
			phones.clear();
			phones.addAll(dublicates);
		}
		logger.debug("mail id  "+mails+" phone nos - "+phones);		
	}
	
	
	// Parent registration for Email
	public static String parentInsert(ParentsDataBean parentsDataBean, String schoolName, String schoolID) throws SQLException, IOException{
		System.out.println("parent school "+schoolName);
		logger.info("------------------- parentInsert start ----------------------");
		String messege="";
		try{
			
			
			messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")
			+paths.getString("EMAIL.TEMPLATES.USERNAMEPASS")
			+ paths.getString("EMAIL.TEMPLATES.MIDDLE")+paths.getString("EMAIL.TEMPLATES.USERNAMEPASSTITLE")+"<td>"+
			parentsDataBean.getParUsername()+
			"</td><td>"+parentsDataBean.getParSecurePasword()+"</td><td>"+"Parent"+"</td>"+paths.getString("EMAIL.TEMPLATES.END");
	
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(parentsDataBean.getParEmail(),"Stumosys - Parent Registration Confirmed",messege);
			logger.info("Successfully  Email Called Service------------");	
			
			/*
			int schoolid=Integer.parseInt(schoolID);
			if (!"".equalsIgnoreCase(parentsDataBean.getParEmail()) && parentsDataBean.getParEmail() !=null){
			messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")
					+paths.getString("EMAIL.TEMPLATES.USERNAMEPASS")
					+ paths.getString("EMAIL.TEMPLATES.MIDDLE")+paths.getString("EMAIL.TEMPLATES.USERNAMEPASSTITLE")+"<td>"+
					parentsDataBean.getParUsername()+
					"</td><td>"+parentsDataBean.getParSecurePasword()+"</td><td>"+"Parent"+"</td>"+paths.getString("EMAIL.TEMPLATES.END");
			Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			stmt=con.createStatement();
			preparedStatement=con.prepareStatement(Query);
			preparedStatement.setString(1, parentsDataBean.getParEmail());
			preparedStatement.setString(2, "STUMOSYS REGISTRATION CONFIRMED-" + parentsDataBean.getParUsername()); 
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."
						+ " Log-in ID: "+parentsDataBean.getParUsername()+" Password: "+parentsDataBean.getParSecurePasword()+" Ini bersifat personal. Selamat Menggunakan.");
			}else{
			preparedStatement.setString(3, messege);
			}
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, schoolID);
			preparedStatement.executeUpdate();			
			}
			else{
				logger.info("No Emails are Available");
			}
			System.out.println("===================>"+parentsDataBean.getParPhoneNo().substring(0,1)+"--------->"+parentsDataBean.getParPhoneNo());
			
			if (parentsDataBean.getParPhoneNo()!="" && parentsDataBean.getParPhoneNo().substring(0,1).equalsIgnoreCase("+")){
			
			preparedStatement=getSMSDB(con);

			preparedStatement.setString(1, "Stumosys");
			preparedStatement.setString(2, parentsDataBean.getParPhoneNo());
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6 ){
			preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."
						+ " Log-in ID: "+parentsDataBean.getParUsername()+" Password: "+parentsDataBean.getParSecurePasword()+
						" Ini bersifat personal. Selamat Menggunakan."+paths.getString("SMS.TEMPLATES.LINK"));
			}
			else{
				preparedStatement.setString(3, "Welcome to STUMOSYS Login Here http://www.stumosys.neotural.com With Your "
						+ "User Name "+parentsDataBean.getParUsername()+" and Password "+parentsDataBean.getParSecurePasword()
						+paths.getString("SMS.TEMPLATES.LINK"));
			}
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, schoolID);
			preparedStatement.executeUpdate();
			logger.info("------------------- parentInsert end ----------------------");			
		}
	*/}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
		return "";
	}
	
	public static String staffInsert(StaffDataBean staffDataBean, String schoolName, String schoolID) throws SQLException, IOException{
		logger.debug("staff school "+schoolName);
		String messege="";
		try{
			
			messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")
			+paths.getString("EMAIL.TEMPLATES.USERNAMEPASS")
			+ paths.getString("EMAIL.TEMPLATES.MIDDLE")+paths.getString("EMAIL.TEMPLATES.USERNAMEPASSTITLE")+"<td>"+
			staffDataBean.getStaUsername()+
			"</td><td>"+staffDataBean.getStaSecurePasword()+"</td><td>"+"Staff"+"</td>"+paths.getString("EMAIL.TEMPLATES.END");
	
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(staffDataBean.getStaEmail(),"Stumosys - Staff Registration Confirmed",messege);
			logger.info("Successfully  Email Called Service------------");	
			
			/*int schoolid=Integer.parseInt(schoolID);
			if (!"".equalsIgnoreCase(staffDataBean.getStaEmail()) && staffDataBean.getStaEmail() !=null) {
			messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")
					+paths.getString("EMAIL.TEMPLATES.USERNAMEPASS")
					+ paths.getString("EMAIL.TEMPLATES.MIDDLE")+paths.getString("EMAIL.TEMPLATES.USERNAMEPASSTITLE")+"<td>"+
					staffDataBean.getStaUsername()+
					"</td><td>"+staffDataBean.getStaSecurePasword()+"</td><td>"+"Staff"+"</td>"+paths.getString("EMAIL.TEMPLATES.END");
			Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			stmt=con.createStatement();
			preparedStatement=con.prepareStatement(Query);
			preparedStatement.setString(1, staffDataBean.getStaEmail());
			preparedStatement.setString(2, "STUMOSYS REGISTRATION CONFIRMED-" + staffDataBean.getStaUsername()); 
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."
						+ " Log-in ID: "+staffDataBean.getStaUsername()+" Password: "+staffDataBean.getStaSecurePasword()+" Ini bersifat personal. Selamat Menggunakan.");
			}else{
			preparedStatement.setString(3, messege);
			}
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, schoolID);
			preparedStatement.executeUpdate();	
			}
			else{
				logger.info("No Emails are Available");
			}
			if (staffDataBean.getStaPhoneNo()!="" && staffDataBean.getStaPhoneNo().substring(0,1).equalsIgnoreCase("+")){
			
			preparedStatement=getSMSDB(con);
			preparedStatement.setString(1, "Stumosys");
			preparedStatement.setString(2, staffDataBean.getStaPhoneNo()); 
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
			preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."
						+ " Log-in ID: "+staffDataBean.getStaUsername()+" Password: "+staffDataBean.getStaSecurePasword()+" Ini bersifat personal. Selamat Menggunakan."
						+paths.getString("SMS.TEMPLATES.LINK"));
			}
			else{
				preparedStatement.setString(3, "Welcome to STUMOSYS Login Here http://www.stumosys.neotural.com With Your "
						+ "User Name " + staffDataBean.getStaUsername() +" and Password "+staffDataBean.getStaSecurePasword()
						+paths.getString("SMS.TEMPLATES.LINK"));
			}
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, schoolID);
			preparedStatement.executeUpdate();
			}*/
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
		return "";
	}
	
	
	
	
	public static String librarianInsert(LibrarianDataBean librarianDataBean, String schoolName, String schoolID) throws SQLException, IOException{
		logger.debug("librarian school "+schoolName);
		String messege="";
		try{
			
			messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")
			+paths.getString("EMAIL.TEMPLATES.USERNAMEPASS")
			+ paths.getString("EMAIL.TEMPLATES.MIDDLE")+paths.getString("EMAIL.TEMPLATES.USERNAMEPASSTITLE")+"<td>"+
			librarianDataBean.getLibUsername()+
			"</td><td>"+librarianDataBean.getLibSecurePassword()+"</td><td>"+"Librarian"+"</td>"+paths.getString("EMAIL.TEMPLATES.END");
	
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(librarianDataBean.getLibEmail(),"Stumosys - Librarian Registration Confirmed",messege);
			logger.info("Successfully  Email Called Service------------");	
			
			
		/*	int schoolid=Integer.parseInt(schoolID);
			if ( !"".equalsIgnoreCase(librarianDataBean.getLibEmail()) && librarianDataBean.getLibEmail() !=null ) {
			messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")
					+paths.getString("EMAIL.TEMPLATES.USERNAMEPASS")
					+ paths.getString("EMAIL.TEMPLATES.MIDDLE")+paths.getString("EMAIL.TEMPLATES.USERNAMEPASSTITLE")+"<td>"+
					librarianDataBean.getLibUsername()+
					"</td><td>"+librarianDataBean.getLibSecurePassword()+"</td><td>"+"Librarian"+"</td>"+paths.getString("EMAIL.TEMPLATES.END");
			Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			stmt=con.createStatement();
			preparedStatement=con.prepareStatement(Query);
			preparedStatement.setString(1, librarianDataBean.getLibEmail());
			preparedStatement.setString(2, "STUMOSYS REGISTRATION CONFIRMED-" + librarianDataBean.getLibUsername()); 
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."
						+ " Log-in ID: "+librarianDataBean.getLibUsername() +" Password: "+librarianDataBean.getLibSecurePassword()+" Ini bersifat personal. Selamat Menggunakan.");
			}else{
			preparedStatement.setString(3, messege);
			}
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, schoolID);
			preparedStatement.executeUpdate();		
			}
			else{
				logger.info("No Emails are Available");
			}
			if (librarianDataBean.getLibPhoneNo()!="" && librarianDataBean.getLibPhoneNo().substring(0,1).equalsIgnoreCase("+")){
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
			preparedStatement=getSMSDB(con);
			preparedStatement.setString(1, "Stumosys");
			preparedStatement.setString(2, librarianDataBean.getLibPhoneNo()); 
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."
						+ " Log-in ID: "+librarianDataBean.getLibUsername()+" Password: "+librarianDataBean.getLibSecurePassword()+" Ini bersifat personal. Selamat Menggunakan."
						+paths.getString("SMS.TEMPLATES.LINK"));
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, schoolID);
			preparedStatement.executeUpdate();
			}
			}*/
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
		return "";
	}
	
	public static String bookShopInsert(StaffDataBean staffDataBean, String schoolName, String schoolID) throws SQLException, IOException{
		logger.debug("bookshop school "+schoolName);
		String messege="";
		try{
			messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")
			+paths.getString("EMAIL.TEMPLATES.USERNAMEPASS")
			+ paths.getString("EMAIL.TEMPLATES.MIDDLE")+paths.getString("EMAIL.TEMPLATES.USERNAMEPASSTITLE")+"<td>"+
			staffDataBean.getStaUsername()+
			"</td><td>"+staffDataBean.getStaSecurePasword()+"</td><td>"+"BookShop"+"</td>"+paths.getString("EMAIL.TEMPLATES.END");
	
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(staffDataBean.getStaEmail(),"Stumosys - BookShop Registration",messege);
			logger.info("Successfully  Email Called Service------------");	
			
			
	/*		
			int schoolid=Integer.parseInt(schoolID);
			if ( !"".equalsIgnoreCase(staffDataBean.getStaEmail()) && staffDataBean.getStaEmail() !=null) {
			messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")
					+paths.getString("EMAIL.TEMPLATES.USERNAMEPASS")
					+ paths.getString("EMAIL.TEMPLATES.MIDDLE")+paths.getString("EMAIL.TEMPLATES.USERNAMEPASSTITLE")+"<td>"+
					staffDataBean.getStaUsername()+
					"</td><td>"+staffDataBean.getStaSecurePasword()+"</td><td>"+"BookShop"+"</td>"+paths.getString("EMAIL.TEMPLATES.END");
			Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			stmt=con.createStatement();
			preparedStatement=con.prepareStatement(Query);
			preparedStatement.setString(1, staffDataBean.getStaEmail());
			preparedStatement.setString(2, "STUMOSYS REGISTRATION CONFIRMED-" + staffDataBean.getStaUsername()); 
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."
						+ " Log-in ID: "+staffDataBean.getStaUsername()+" Password: "+staffDataBean.getStaSecurePasword()+" Ini bersifat personal. Selamat Menggunakan.");
			}else{
			preparedStatement.setString(3, messege);
			}
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, schoolID);
			preparedStatement.executeUpdate();	
			logger.info("bookShopInsert Email was Successfully stored in email_temp table");
			}
			else{
				logger.info("No Emails are Available");
			}
			if (staffDataBean.getStaPhoneNo()!="" && staffDataBean.getStaPhoneNo().substring(0,1).equalsIgnoreCase("+")){
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
			preparedStatement=getSMSDB(con);

			preparedStatement.setString(1, "Stumosys");
			preparedStatement.setString(2, staffDataBean.getStaPhoneNo()); 
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."
						+ " Log-in ID: "+staffDataBean.getStaUsername()+" Password: "+staffDataBean.getStaSecurePasword()+" Ini bersifat personal. Selamat Menggunakan."
						+paths.getString("SMS.TEMPLATES.LINK"));
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, schoolID);
			preparedStatement.executeUpdate();
			}	
			}*/
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
		return "";
	}
	
	// Exam Time table for Email and SMS 
	public static String examTimeTableInsert(TimeTableDataBean timeTableDataBean, String schoolName, int schoolid) throws SQLException, IOException{
		logger.debug("exam time table insert school "+schoolName);
		try{
			mails=new ArrayList<String>();
			phones=new ArrayList<String>();
			mails.addAll(timeTableDataBean.getMails());
			phones.addAll(timeTableDataBean.getPhonenos());
			mailPhone();
			// Email 
			for(int i=0;i<mails.size();i++){
				String mail=mails.get(i);
				insertexamdetails(timeTableDataBean,schoolName,mail,schoolid);
			}	
			// SMS 
			for(int i=0;i<phones.size();i++){
				if (phones.get(i)!="" && phones.get(i).substring(0,1).equalsIgnoreCase("+")){
				String phone=phones.get(i);
				insertphonedetails(timeTableDataBean,schoolName,phone,schoolid);
				}
				else{
					logger.info("Phone Number not properly given");
				}
			}			
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}
		return "Success";
	}
	// SMS 
	private static void insertphonedetails(TimeTableDataBean timeTableDataBean,
			String schoolName,  String phone, int schoolid) throws SQLException {
		try{
			
			
			
			
			Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			preparedStatement=getSMSDB(con);
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
			preparedStatement.setString(1, "Stumosys");
			preparedStatement.setString(2, phone); 
			
				preparedStatement.setString(3, "Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."
						+ " Ujian akan dimulai pada "+dateFormat.format(timeTableDataBean.getExamStartDate())
						+paths.getString("SMS.TEMPLATES.LINK"));			
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, String.valueOf(schoolid));
			preparedStatement.execute();
			con.close();
			}
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if(con!=null) {
				con.close();
				}
		}
		
	}

	// Email
	private static void insertexamdetails(
			TimeTableDataBean timeTableDataBean, String schoolName,String mail, int schoolid) throws SQLException  {
		String messege="";
		String tablecontent="";
		int j=0;
		try{
			logger.info("-------------- insertexamdetails start ------------------");
			logger.info("size of the class list---->"+timeTableDataBean.getClassList().size());
			if (!"".equalsIgnoreCase(mail) && mail !=null) {
			for (int i = 0; i < timeTableDataBean.getClassList().size(); i++) {
				tablecontent=tablecontent+"<tr><td>"+
						dateFormat.format(timeTableDataBean.getClassList().get(i).getExamStartDate())+
					"</td><td>"+timeTableDataBean.getClassList().get(i).getExamSubject()+"</td><td>"+
					timeTableDataBean.getClassList().get(i).getExamRoomNo()+"</td><td>"+
					timeTableDataBean.getClassList().get(i).getExamDay()+"</td></tr>";
			}
		messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")
				+paths.getString("EMAIL.TEMPLATES.EXAMTIMETABLE")
				+ paths.getString("EMAIL.TEMPLATES.EXAMTIMETABLEMIDDLE")+
				timeTableDataBean.getClassList().get(j).getExamTitle()+"-"+timeTableDataBean.getClassList().get(j).getExamClass()
				+paths.getString("EMAIL.TEMPLATES.EXAMTIMETABLETITLE")+tablecontent+paths.getString("EMAIL.TEMPLATES.END");
		
		
	
		logger.info("Calling Email Service -------------");
		PushEmail.sendMail(mail,"Stumosys - EXAM TIME TABLE",messege);
		logger.info("Successfully  Email Called Service------------");	
		
		
		/*Class.forName(JDBC_DRIVER);
		con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
		preparedStatement=con.prepareStatement(Query);
		preparedStatement.setString(1, mail);
		preparedStatement.setString(2, "EXAM TIME TABLE "); 
		if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
			preparedStatement.setString(3,messege);
		}else{
		preparedStatement.setString(3, messege);
		}
		preparedStatement.setString(4, "I");
		preparedStatement.setDate(5, currentDate);
		preparedStatement.setString(6, String.valueOf(schoolid));
		
		preparedStatement.execute();
		con.close();
		logger.info("-------------- insertexamdetails end ------------------");
			}*/
			/*else{
				logger.info("No Emails are Available");
			}*/

		} }catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if(con!=null) {
				con.close();
				}
		}
	}

	// Exam time table update Email and SMS 
	public static String examTimeTableUpdate(TimeTableDataBean timeTableDataBean, String schoolName, int schoolid) throws SQLException, IOException{
		logger.info("-------------- examTimeTableUpdate start ------------------");

		logger.debug("exam time table update school "+schoolName);
		try{
			mails.addAll(timeTableDataBean.getMails());
			phones.addAll(timeTableDataBean.getPhonenos());
			mailPhone();	
			for(int i=0;i<mails.size();i++){
				String mail=mails.get(i);
				//Email
				updateexamdetails(timeTableDataBean,schoolName,mail,schoolid);
			}			
			preparedStatement=getSMSDB(con);
			for(int i=0;i<phones.size();i++){
				if (phones.get(i)!="" && phones.get(i).substring(0,1).equalsIgnoreCase("+")){
				String phno=phones.get(i);
				//SMS 
				updateexamdetailsphone(timeTableDataBean,schoolName,phno,schoolid);
				}
			}
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
		return "";
	}
	
	// SMS 
	private static void updateexamdetailsphone(
			TimeTableDataBean timeTableDataBean, String schoolName,
			String phno, int schoolid) throws SQLException {
		try{
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
			preparedStatement=getSMSDB(con);
			preparedStatement.setString(1, "Stumosys");
			preparedStatement.setString(2, phno); 
			
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."
						+ dateFormat.format(timeTableDataBean.getExamStartDate())+" Ujian Berubah ke "+timeTableDataBean.getExamSubject()
						+paths.getString("SMS.TEMPLATES.LINK"));
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, String.valueOf(schoolid));
			preparedStatement.executeUpdate();
			con.close();
			}
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		}finally{
			con.close();
		}
	}
	// Email
	private static void updateexamdetails(TimeTableDataBean timeTableDataBean,
			String schoolName, String mail, int schoolid) throws SQLException {
		String messege="";
		try{
			if (!"".equalsIgnoreCase(mail) && mail !=null ) {
			Class.forName(JDBC_DRIVER);
			messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")+"</div></div><div align='center'>"+
					dateFormat.format(timeTableDataBean.getExamStartDate())+paths.getString("EMAIL.TEMPLATES.EXAMTIMETABLEUPDATE")+timeTableDataBean.getExamSubject()
					+ paths.getString("EMAIL.TEMPLATES.MIDDLE")+"</tr></table>"+paths.getString("EMAIL.TEMPLATES.END");
			
			
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(mail,"Stumosys - EXAM TIME TABLE UPDATE",messege);
			logger.info("Successfully  Email Called Service------------");	
			
			/*con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			preparedStatement=con.prepareStatement(Query);
			preparedStatement.setString(1, mail);
			preparedStatement.setString(2, "EXAM TIME TABLE UPDATE");
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."
						+ dateFormat.format(timeTableDataBean.getExamStartDate())+" Ujian Berubah ke "+timeTableDataBean.getExamSubject());
			}else{
			preparedStatement.setString(3, messege);
			}
			preparedStatement.setString(4, "I");
			
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, String.valueOf(schoolid));
			preparedStatement.executeUpdate();
			
			con.close();
			}
			else{
				logger.info("No Emails are Available");
			}*/
		} }catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			con.close();
		}
	}
	// Class time table register Email and SMS 
	public static String classTimeTableInsert(ClassTimeTableDataBean classTimeTableDataBean, String schoolName, String schoolID) throws SQLException, IOException{
		logger.debug("class time table insert school "+schoolName);
		try{
			int schoolid=Integer.parseInt(schoolID);
			mails=new ArrayList<String>();phones=new ArrayList<String>();
			mails.addAll(classTimeTableDataBean.getMails());
			phones.addAll(classTimeTableDataBean.getPhonenos());
			mailPhone();			
			for(int i=0;i<mails.size();i++){
				String mail=mails.get(i);
				// Email
				classTimeTableInsertmail(classTimeTableDataBean,schoolName,mail,schoolid);
			}			
			for(int i=0;i<phones.size();i++){
				if (phones.get(i)!="" && phones.get(i).substring(0,1).equalsIgnoreCase("+")){
				String messageId=messagesID();
				String phone=phones.get(i);
				// SMS 
				classTimeTableInsertphone(classTimeTableDataBean,messageId,phone,schoolName,schoolid);
				}
				
			}			
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
		return "";
	}
	// SMS 
	private static void classTimeTableInsertphone(
			ClassTimeTableDataBean classTimeTableDataBean, String messageId,
			String phone, String schoolName, int schoolid) throws SQLException {
		logger.info("------------------------- Inside classTimeTableInsertphone method -----------------");
		try{
		Class.forName(JDBC_DRIVER);
		con=DriverManager.getConnection(DB_URL_SMS, USER, PASS);
		if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
		preparedStatement=getSMSDB(con);
		preparedStatement.setString(1, "Stumosys");
		preparedStatement.setString(2, phone); 
			preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Ananda siswa kelas "+classTimeTableDataBean.getDay()
					+"kelas jadwal dibuat"+paths.getString("SMS.TEMPLATES.LINK"));
		preparedStatement.setString(4, "I");
		preparedStatement.setDate(5, currentDate);
		preparedStatement.setString(6, String.valueOf(schoolid));
		preparedStatement.executeUpdate();
		con.close();
		}
		}catch(Exception e){
			logger.warn("Exception classTimeTableInsertphone --------------------->> "+e.getMessage());
		}finally{
			con.close();
		}
	}
	// Email
	private static void classTimeTableInsertmail(ClassTimeTableDataBean classTimeTableDataBean, String schoolName,
			String mail, int schoolid) throws SQLException {
		String tablecontent="";
		String messege="";
		try{
			if (!"".equalsIgnoreCase(mail) && mail !=null) {
			for (int i = 0; i < classTimeTableDataBean.getClasstimeList().size(); i++) {
				tablecontent=tablecontent+"<tr><td>"+
						(i+1)+
					"</td><td>"+classTimeTableDataBean.getClasstimeList().get(i).getSubject()+"</td></tr>";
					
			}
		messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")
				+paths.getString("EMAIL.TEMPLATES.CLASSTIMETABLE")
				+ paths.getString("EMAIL.TEMPLATES.EXAMTIMETABLEMIDDLE")+
				classTimeTableDataBean.getClassSection()+"-"+classTimeTableDataBean.getDay()
				+paths.getString("EMAIL.TEMPLATES.CLASSTIMETABLETITLE")+tablecontent+paths.getString("EMAIL.TEMPLATES.END");
		
		logger.info("Calling Email Service -------------");
		PushEmail.sendMail(mail,"Stumosys - CLASS TIME TABLE",messege);
		logger.info("Successfully  Email Called Service------------");	
		
			/*Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			preparedStatement=con.prepareStatement(Query);
			preparedStatement.setString(1, mail);
			preparedStatement.setString(2, "CLASS TIME TABLE "); 
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(3,messege );	
			}else{
			preparedStatement.setString(3, messege);
			}
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, String.valueOf(schoolid));
			preparedStatement.executeUpdate();
			con.close();
			}
			else{
				logger.info("No Emails are Available");*/
			}
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if(con!=null) {
				con.close();
				}
			
			//con.close();
		}
		
	}
	
	// Class time table update Email and SMS 
	public static String classTimeTableUpdate(ClassTimeTableDataBean classTimeTableDataBean, String schoolName, int schoolid) throws SQLException{
		logger.debug("class time table update school "+schoolName);
		try{
			mails=new ArrayList<String>();phones=new ArrayList<String>();
			mails.addAll(classTimeTableDataBean.getMails());
			phones.addAll(classTimeTableDataBean.getPhonenos());
			mailPhone();
			for(int i=0;i<mails.size();i++){
				String mail=mails.get(i);
				// Email
				classTimeTableUpdatemail(classTimeTableDataBean,mail,schoolName,schoolid);
			}			
			
			for(int i=0;i<phones.size();i++){
				if (phones.get(i)!="" && phones.get(i).substring(0,1).equalsIgnoreCase("+")){
				String phone=phones.get(i);
				// SMS 
				classTimeTableUpdatephone(classTimeTableDataBean,phone,schoolName,schoolid);
				}
			}			
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
		return "";
	}
	// SMS 
	private static void classTimeTableUpdatephone(
			ClassTimeTableDataBean classTimeTableDataBean, String phone,
			String schoolName,  int schoolid) throws SQLException {
		try{
			Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_SMS, USER, PASS);
			preparedStatement=getSMSDB(con);
				preparedStatement.setString(1, "Stumosys");
				preparedStatement.setString(2, phone); 
				if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
					preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."
							+" The "+classTimeTableDataBean.getPeriod() +" on "+classTimeTableDataBean.getDay()+ " Berubah untuk "+classTimeTableDataBean.getSubject()
							+paths.getString("SMS.TEMPLATES.LINK"));
				}
				else{
					preparedStatement.setString(3,"Welcome to STUMOSYS,"
							+" The Period "+classTimeTableDataBean.getPeriod() +" on "+classTimeTableDataBean.getDay()+ " Changed to "+classTimeTableDataBean.getSubject()
							+paths.getString("SMS.TEMPLATES.LINK"));
				}
				preparedStatement.setString(4, "I");
				preparedStatement.setDate(5, currentDate);
				preparedStatement.setString(6, String.valueOf(schoolid));
				preparedStatement.executeUpdate();
				con.close();

		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			con.close();
		}
	}
	// Email
	private static void classTimeTableUpdatemail(
			ClassTimeTableDataBean classTimeTableDataBean, String mail,
			String schoolName, int schoolid) throws SQLException {		
		String messege="";
		try{
			if (!"".equalsIgnoreCase(mail) && mail !=null ) {
			messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")+"</div></div><div align='center'>"+classTimeTableDataBean.getPeriod()
					+paths.getString("EMAIL.TEMPLATES.CLASSTIMETABLEUPDATE")+classTimeTableDataBean.getDay()+" is Changed to "+classTimeTableDataBean.getSubject()
					+"<center><br/><table border='1'><tr>"+paths.getString("EMAIL.TEMPLATES.END");
		
			
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(mail,"Stumosys - CLASS TIME TABLE UPDATE",messege);
			logger.info("Successfully  Email Called Service------------");	
			
			/*Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			preparedStatement=con.prepareStatement(Query);
			preparedStatement.setString(1, mail);
			preparedStatement.setString(2, "CLASS TIME TABLE UPDATE"); 
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."
						+" The "+classTimeTableDataBean.getPeriod() +" on "+classTimeTableDataBean.getDay()+ " Berubah untuk "+classTimeTableDataBean.getSubject());
			}else{
			preparedStatement.setString(3, messege);
			}
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, String.valueOf(schoolid));
			preparedStatement.executeUpdate();
			con.close();
			}
			else{
				logger.info("No Emails are Available");*/
			}
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			con.close();
		}
		
	}
// Class time table add Email and SMS
	public static String classTimeTableAdd(ClassTimeTableDataBean classTimeTableDataBean, String schoolName, int schoolid) throws SQLException{
		logger.debug("Add class time table school "+schoolName);
		try{
			mails=new ArrayList<String>();phones=new ArrayList<String>();
			mails.addAll(classTimeTableDataBean.getMails());
			phones.addAll(classTimeTableDataBean.getPhonenos());
			mailPhone();
		
			for(int i=0;i<mails.size();i++){
				String mail=mails.get(i);
				classTimeTableaAddemail(classTimeTableDataBean,mail,schoolName,schoolid);
			}			
			
			
			for(int i=0;i<phones.size();i++){
				if (phones.get(i)!="" && phones.get(i).substring(0,1).equalsIgnoreCase("+")){
				String phone=phones.get(i);
				classTimeTableaAddphone(classTimeTableDataBean,phone,schoolName,schoolid);
				}
			}			
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
		return "";
	}
	// SMS 
	private static void classTimeTableaAddphone(
			ClassTimeTableDataBean classTimeTableDataBean, String phone,
			String schoolName, int schoolid) throws SQLException {
		try{
			Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_SMS, USER, PASS);
			preparedStatement=getSMSDB(con);
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(1, "Stumosys");
				preparedStatement.setString(2, phone); 
					preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."
							+ "Ananda siswa kelas"+classTimeTableDataBean.getClassname()+" memiliki kelas baru "+classTimeTableDataBean.getSubject() + " Senin " +"("+dateFormat.format(classTimeTableDataBean.getDate()+")"+ classTimeTableDataBean.getTimeStart() + " - "
							+ classTimeTableDataBean.getTimeEnd() + "WITA di Ruang Auditorium. ")
							+paths.getString("SMS.TEMPLATES.LINK"));
				preparedStatement.setString(4, "I");
				preparedStatement.setDate(5, currentDate);
				preparedStatement.setString(6, String.valueOf(schoolid));
				preparedStatement.executeUpdate();
				con.close();

			}
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			con.close();
		}
	}
	// Email 
	private static void classTimeTableaAddemail(
			ClassTimeTableDataBean classTimeTableDataBean, String mail,
			String schoolName, int schoolid) throws SQLException {
	try{
		if (!"".equalsIgnoreCase(mail) && mail !=null ) {
		
			String message = "The extra class for "
					+ classTimeTableDataBean.getSubject() + " at " + classTimeTableDataBean.getTimeStart() + " to "
					+ classTimeTableDataBean.getTimeEnd() + " on " + dateFormat.format(classTimeTableDataBean.getDate());
			
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(mail,"Stumosys - CLASS TIME TABLE UPDATE",message);
			logger.info("Successfully  Email Called Service------------");	
			
	   /*Class.forName(JDBC_DRIVER);
		con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
		preparedStatement=con.prepareStatement(Query);
		preparedStatement.setString(1, mail);
		preparedStatement.setString(2, "CLASS TIME TABLE UPDATE"); 
		if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
			preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."
					+ "Ananda siswa kelas"+classTimeTableDataBean.getClassname()+" memiliki kelas baru "+classTimeTableDataBean.getSubject() + " Senin " +"("+dateFormat.format(classTimeTableDataBean.getDate()+")"+ classTimeTableDataBean.getTimeStart() + " - "
					+ classTimeTableDataBean.getTimeEnd() + "WITA di Ruang Auditorium. "));
		}else{
		preparedStatement.setString(3, "The extra class for "
				+ classTimeTableDataBean.getSubject() + " at " + classTimeTableDataBean.getTimeStart() + " to "
				+ classTimeTableDataBean.getTimeEnd() + " on " + dateFormat.format(classTimeTableDataBean.getDate()));
		}
		preparedStatement.setString(4, "I");
		preparedStatement.setDate(5, currentDate);
		preparedStatement.setString(6, String.valueOf(schoolid));
		preparedStatement.executeUpdate();
		con.close();
		}
		else{
			logger.info("No Emails are Available");*/
		}
	}catch(Exception e){
					logger.warn("Exception -->"+e.getMessage());
	}finally{
		con.close();
	}
		
	}

	
	// Notice board register  for Email and SMS 
	public static void noticeBoardInsert(NoticeBoardDataBean noticeBoardDataBean, String schoolName, int schoolid) throws SQLException {
		logger.info("--------- Inside Notice board method ------------");
		logger.info("School Name ------->"+schoolName);
		try{
			mails=new ArrayList<String>();
			phones=new ArrayList<String>();
			mails.addAll(noticeBoardDataBean.getEmailList());
			phones.addAll(noticeBoardDataBean.getPhonenos());
			mailPhone();
			noticeBoardInsertmail(noticeBoardDataBean,mails,schoolName,schoolid);
			noticeBoardInsertphone(noticeBoardDataBean,phones,schoolName,schoolid);
			
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
	}
		
	//SMS  
	private static void noticeBoardInsertphone(NoticeBoardDataBean noticeBoardDataBean, 
			List<String> phones, String schoolName, int schoolid) throws SQLException {
		String message="";
		Connection connection=null;
		try{
			
			logger.info("Try to Push the SMS into Windows server Poller datatbase");
			//Class.forName(JDBC_DRIVER);
			if(connection==null) {
			preparedStatement=getSMSDB(connection);			
			}
			
			logger.info("Notice board Phone number Count --->"+phones.size());
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6) {
			for(int i=0;i<phones.size();i++){
				if (phones.get(i)!="" && phones.get(i).substring(0,1).equalsIgnoreCase("+")){
				String phone=phones.get(i);				
				String text=noticeBoardDataBean.getNoticeID().replaceAll("\\<.*?\\>", "");
				message=" Notice Board Information "+"   Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."+"pada"+dateFormat.format(noticeBoardDataBean.getFromdate())+" untuk "+dateFormat.format(noticeBoardDataBean.getTodate())
				+text+paths.getString("SMS.TEMPLATES.LINK");				
				logger.info("Message content ------>"+message);
				logger.info("Message Phone number ------>"+phone);
				logger.info("Message Date ------>"+currentDate);
				logger.info("Message School ID ------>"+schoolid);
				preparedStatement.setString(1, "Stumosys");
				preparedStatement.setString(2, phone); 
				preparedStatement.setString(3,message);
				preparedStatement.setString(4, "I");
				preparedStatement.setDate(5, currentDate);
				preparedStatement.setString(6, String.valueOf(schoolid));
				preparedStatement.executeUpdate();
				con.close();
				logger.info("Indonesai School data is successfully inserted into polloer database sms_temp");				
				
				}

			}
			
			}	
			
			else 
			{	// List of phone number 
				for(int i=0;i<phones.size();i++){
				if (phones.get(i)!="" && phones.get(i).substring(0,1).equalsIgnoreCase("+")){
				String phone=phones.get(i);
				String text=noticeBoardDataBean.getNoticeID().replaceAll("\\<.*?\\>", "");
				logger.info("SMS for NagerCoil School");
				logger.info("0 -->"+noticeBoardDataBean.getNoticeID());
				logger.info("1 -->"+noticeBoardDataBean.getNoticeboardID());
				logger.info("2 -->"+noticeBoardDataBean.getNoticeClass());
				logger.info("3 -->"+noticeBoardDataBean.getNoticeFollower());
				logger.info("4 -->"+noticeBoardDataBean.getNoticeFollowers());
				logger.info("5 -->"+noticeBoardDataBean.getNoticeID());
				logger.info("6 -->"+noticeBoardDataBean.getNoticeSubject());
				logger.info("7 -->"+noticeBoardDataBean.getNoticeSubjects());
				logger.info("8 -->"+noticeBoardDataBean.getNoticeDate());
				logger.info("9 -->"+noticeBoardDataBean.getSchoolID());
				logger.info("10 -->"+noticeBoardDataBean.getSchoolName());
				
				message=schoolName+"-"+text
						+" from "+dateFormat.format(noticeBoardDataBean.getFromdate())+" to "+dateFormat.format(noticeBoardDataBean.getTodate())+paths.getString("SMS.TEMPLATES.LINK");
				logger.info("Message content ------>"+message); // This message is coming as HTML format 
				logger.info("Message Date ------>"+currentDate);
				logger.info("Message School ID ------>"+schoolid);
				preparedStatement.setString(1, "Stumosys");
				preparedStatement.setString(2, phone); 
				preparedStatement.setString(3, message);
				preparedStatement.setString(4, "I");
				preparedStatement.setDate(5, currentDate);
				preparedStatement.setString(6, String.valueOf(schoolid));
				preparedStatement.addBatch();
				//preparedStatement.executeUpdate();
				logger.info("India data is successfully inserted into polloer database sms_temp");
					}
				}
				
				preparedStatement.executeBatch();
				
			}
			
			
		}catch(Exception e){
						//logger.warn("Exception -->"+e.getMessage());
						e.printStackTrace();
		}finally{
			if(connection!=null) {
			connection.close();
			}
		}
	}
	
	
	// Email
		private static void noticeBoardInsertmail(
				NoticeBoardDataBean noticeBoardDataBean, List<String> mails,
				String schoolName, int schoolid) throws SQLException {
			String messege="";
			try{
				//if (!"".equalsIgnoreCase(mail) && mail!=null ) {
				messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")
				+ paths.getString("EMAIL.TEMPLATES.NOTICEBOARD")+noticeBoardDataBean.getNoticeID().replace("<br>", "")+
				" from "+dateFormat.format(noticeBoardDataBean.getFromdate())+" to "+
				dateFormat.format(noticeBoardDataBean.getTodate())
				+paths.getString("EMAIL.TEMPLATES.END");
				for(int i=0;i<mails.size();i++){
					String mail=mails.get(i);
					logger.info("Calling Email Service -------------");
					PushEmail.sendMail(mail,"Stumosys - Notice Board Information",messege);
					logger.info("Successfully  Email Called Service------------");	
					}
				
				
				/*Class.forName(JDBC_DRIVER);
				messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")
						+ paths.getString("EMAIL.TEMPLATES.NOTICEBOARD")+noticeBoardDataBean.getNoticeID().replace("<br>", "")+
						" from "+dateFormat.format(noticeBoardDataBean.getFromdate())+" to "+
						dateFormat.format(noticeBoardDataBean.getTodate())
						+paths.getString("EMAIL.TEMPLATES.END");
				
				con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
				preparedStatement=con.prepareStatement(Query);
				if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
					
					for(int i=0;i<mails.size();i++){
					String mail=mails.get(i);
					if (!"".equalsIgnoreCase(mail) && mail!=null ) {
					preparedStatement.setString(1, mail);
					}
					preparedStatement.setString(2, noticeBoardDataBean.getNoticeSubject()); 
					preparedStatement.setString(3, " Notice Board Information "+"   Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS.Silahkan Log in untuk melihat informasi");
					preparedStatement.setString(4, "I");
					preparedStatement.setDate(5, currentDate);
					preparedStatement.setString(6, String.valueOf(schoolid));
					preparedStatement.executeUpdate(); 
				}
				
				}
				else{
					for(int i=0;i<mails.size();i++){
						String mail=mails.get(i);
						if (!"".equalsIgnoreCase(mail) && mail!=null ) {
					preparedStatement.setString(1, mail);
						}
					preparedStatement.setString(2, noticeBoardDataBean.getNoticeSubject()); 
					preparedStatement.setString(3, messege);
					preparedStatement.setString(4, "I");
					preparedStatement.setDate(5, currentDate);
					preparedStatement.setString(6, String.valueOf(schoolid));
					preparedStatement.executeUpdate(); 
				
					}
				
				
				}
				con.close(); */
			}catch(Exception e){
							logger.warn("Exception -->"+e.getMessage());
			}finally{
				con.close();
			}
		}

	// Notice board for Email and SMS 
	public static void noticeBoardUpdate(NoticeBoardDataBean noticeBoardDataBean, String schoolName, int schoolid) throws SQLException {
		logger.debug("notice board update "+schoolName);
		try{
			mails=new ArrayList<String>();phones=new ArrayList<String>();
			mails.addAll(noticeBoardDataBean.getEmailList());
			phones.addAll(noticeBoardDataBean.getPhonenos());
			mailPhone();
			for(int i=0;i<mails.size();i++){
				String mail=mails.get(i);
				//Email
				noticeBoardUpdatemail(noticeBoardDataBean,schoolName,mail,schoolid);
			}			
			for(int i=0;i<phones.size();i++){
				if (phones.get(i)!="" && phones.get(i).substring(0,1).equalsIgnoreCase("+")){
				String phone=phones.get(i);
				//SMS
				noticeBoardUpdatephone(noticeBoardDataBean,schoolName,phone,schoolid);
				}
			}			
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
	}
	// SMS
	private static void noticeBoardUpdatephone(
			NoticeBoardDataBean noticeBoardDataBean, String schoolName,
			String phone, int schoolid) throws SQLException {
		String message="";
		try{
			Class.forName(JDBC_DRIVER);
			preparedStatement=getSMSDB(con);
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(1, "Stumosys");
				preparedStatement.setString(2, phone); 
				preparedStatement.setString(3," Notice Board Information "+"   Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS.Silahkan Log in untuk melihat informasi"
						+paths.getString("SMS.TEMPLATES.LINK"));
				preparedStatement.setString(4, "I");
				preparedStatement.setDate(5, currentDate);
				preparedStatement.setString(6, String.valueOf(schoolid));
				preparedStatement.executeUpdate();
				con.close();

			}else{
				logger.info("No School Has SMS");
				

				message=schoolName+"-"+noticeBoardDataBean.getNoticeID().replace("<br>", "")
						+" from "+dateFormat.format(noticeBoardDataBean.getFromdate())+" to "+dateFormat.format(noticeBoardDataBean.getTodate())+paths.getString("SMS.TEMPLATES.LINK");
				logger.info("MAHARISHI / SMRV Notification Sms ------>"+message);
				preparedStatement.setString(1, "Stumosys");
				preparedStatement.setString(2, phone); 
				preparedStatement.setString(3, message);
				preparedStatement.setString(4, "I");
				preparedStatement.setDate(5, currentDate);
				preparedStatement.setString(6, String.valueOf(schoolid));
				preparedStatement.executeUpdate();
				con.close();
				logger.info("MAHARISHI / SMRV Successfully inserted into polloer database sms_temp");
			
			}
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			con.close();
		}
	}

	// Email
	private static void noticeBoardUpdatemail(
			NoticeBoardDataBean noticeBoardDataBean, String schoolName,
			String mail, int schoolid) throws SQLException {
		String messege="";
		try{
			if (!"".equalsIgnoreCase(mail) && mail !=null) {
			Class.forName(JDBC_DRIVER);
			messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")
					+ paths.getString("EMAIL.TEMPLATES.NOTICEBOARD")+noticeBoardDataBean.getNoticeID().replace("<br>", "")+
					" from "+dateFormat.format(noticeBoardDataBean.getFromdate())+" to "+
					dateFormat.format(noticeBoardDataBean.getTodate())
					+paths.getString("EMAIL.TEMPLATES.END");
			
			
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(mail,"Stumosys - Notice Board Information",messege);
			logger.info("Successfully  Email Called Service------------");	
			
			/*con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			preparedStatement=con.prepareStatement(Query);
			preparedStatement.setString(1, mail);
			preparedStatement.setString(2, "UPDATE "+noticeBoardDataBean.getNoticeSubject()); 
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(3," Notice Board Information "+"   Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS.Silahkan Log in untuk melihat informasi");
			}else{
			preparedStatement.setString(3, messege);
			}
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, String.valueOf(schoolid));
			preparedStatement.executeUpdate();
			con.close();
			}
			else{
				logger.info("No Emails are Available");
			}*/
		} } catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			con.close();
		}
	}
	
	// Student performance for Email and SMS
	public static void studentPerformInsert(StudentPerformanceDataBean studentPerformanceDataBean,String schoolName, int schoolid) throws SQLException {
		logger.debug("student perform insert  "+schoolName);
		try{
			mails=new ArrayList<String>();phones=new ArrayList<String>();
			mails.addAll(studentPerformanceDataBean.getMailid());
			phones.addAll(studentPerformanceDataBean.getPhonenos());
			mailPhone();
			for(int i=0;i<mails.size();i++){
				String mail=mails.get(i);
				//Email
				studentPerformInsertmail(studentPerformanceDataBean,schoolName,mail,schoolid);
			}			
			for(int i=0;i<phones.size();i++){
				if (phones.get(i)!="" && phones.get(i).substring(0,1).equalsIgnoreCase("+")){
				String phone=phones.get(i);
				//SMS
				studentPerformInsertphone(studentPerformanceDataBean,schoolName,phone,schoolid);
				}
			}			
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
	}
	// SMS
	private static void studentPerformInsertphone(
			StudentPerformanceDataBean studentPerformanceDataBean,
			String schoolName, String phone,  int schoolid) throws SQLException {
		try{
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				//Class.forName(JDBC_DRIVER);
				//con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
				preparedStatement=getSMSDB(con);
				preparedStatement.setString(1, "Stumosys");
				preparedStatement.setString(2, phone); 
					preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."
					+studentPerformanceDataBean.getPerformStudID().split("/")[0]+Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
					+ Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())+"Harap diperhatikan." 
					+paths.getString("SMS.TEMPLATES.LINK"));
				preparedStatement.setString(4, "I");
				preparedStatement.setDate(5, currentDate);
				preparedStatement.setString(6, String.valueOf(schoolid));
				preparedStatement.executeUpdate();
				con.close();
			}
			else {
				//Class.forName(JDBC_DRIVER);
				//con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
				preparedStatement=getSMSDB(con);
				preparedStatement.setString(1, "Stumosys");
				preparedStatement.setString(2, phone); 
					preparedStatement.setString(3,"Welcome to STUMOSYS."
					+studentPerformanceDataBean.getPerformStudID().split("/")[0]+Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
					+ Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())+" Please note." 
					+paths.getString("SMS.TEMPLATES.LINK"));
				preparedStatement.setString(4, "I");
				preparedStatement.setDate(5, currentDate);
				preparedStatement.setString(6, String.valueOf(schoolid));
				preparedStatement.executeUpdate();
				con.close();
			}
		}catch (Exception e) {
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			con.close();
		}
	}
	// Email
	private static void studentPerformInsertmail(
			StudentPerformanceDataBean studentPerformanceDataBean,
			String schoolName, String mail, int schoolid) throws SQLException {
		String messege="";
		try{
			String email_header="BEHAVIOUR REPORT |"+schoolName;
			logger.info("Name -->"+studentPerformanceDataBean.getParName());
			if (!"".equalsIgnoreCase(mail) && mail !=null ) {
			Class.forName(JDBC_DRIVER);
			
		messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")+
				studentPerformanceDataBean.getParName()+paths.getString("EMAIL.TEMPLATES.BEHAVIOUR")+dateFormat.format(studentPerformanceDataBean.getTeaDob())
				+ paths.getString("EMAIL.TEMPLATES.MIDDLE")+paths.getString("EMAIL.TEMPLATES.BEHAVIOURTITLE")+"<tr><td>"+
				dateFormat.format(studentPerformanceDataBean.getTeaDob())+
				"</td><td>"+Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())+"</td><td>"
				+Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())+"</td>"+paths.getString("EMAIL.TEMPLATES.END");
			
		
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(mail,email_header,messege);
			logger.info("Successfully  Email Called Service------------");	
		
		
			/*con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			preparedStatement=con.prepareStatement(Query);
			preparedStatement.setString(1, mail);
			//preparedStatement.setString(2, "APPEARANCE AND ATTITUDE STATUS"); 
			preparedStatement.setString(2, email_header); 

			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."
				+studentPerformanceDataBean.getPerformStudID().split("/")[0]+Arrays.deepToString(studentPerformanceDataBean.getStudentAppearance())
				+ Arrays.deepToString(studentPerformanceDataBean.getStudentAttitude())+"Harap diperhatikan." );
			}else{
			preparedStatement.setString(3, messege);
			}
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, String.valueOf(schoolid));
			preparedStatement.executeUpdate();
			con.close();
			}
			else{
				logger.info("No Emails are Available");*/
			}
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			con.close();
		}
	}
	// Student performance for Email and SMS
	public static void studentPerformUrgent(StudentPerformanceDataBean studentPerformanceDataBean,String schoolName, int schoolid) throws SQLException {
		logger.debug("student perform urgent  "+schoolName);
		try{
			mails=new ArrayList<String>();phones=new ArrayList<String>();
			mails.addAll(studentPerformanceDataBean.getMailid());
			phones.addAll(studentPerformanceDataBean.getPhonenos());
			mailPhone();
			for(int i=0;i<mails.size();i++){
				String mail=mails.get(i);
				//Email
				studentPerformUrgentmail(studentPerformanceDataBean,mail,schoolName,schoolid);
			}			
			for(int i=0;i<phones.size();i++){
				if (phones.get(i)!="" && phones.get(i).substring(0,1).equalsIgnoreCase("+")){
				String phone=phones.get(i);
				//SMS
				studentPerformUrgentphone(studentPerformanceDataBean,phone,schoolName,schoolid);
				}
			}			
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
	}
	// SMS
	private static void studentPerformUrgentphone(
			StudentPerformanceDataBean studentPerformanceDataBean,
			String phone, String schoolName,  int schoolid) throws SQLException {
		try{
			//Class.forName(JDBC_DRIVER);
			//con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			preparedStatement=getSMSDB(con);
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(1, "Stumosys");
				preparedStatement.setString(2, phone); 
					preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS. perhatian segera diperlukan.Silahkan kunjungi sekolah"
							+paths.getString("SMS.TEMPLATES.LINK"));	
			
				preparedStatement.setString(4, "I");
				preparedStatement.setDate(5, currentDate);
				preparedStatement.setString(6, String.valueOf(schoolid));
				preparedStatement.executeUpdate();
				con.close();
			}
			else {
				preparedStatement.setString(1, "Stumosys");
				preparedStatement.setString(2, phone); 
					preparedStatement.setString(3,"Welcome to STUMOSYS. Immediate Attention is Needed. Please visit the School"
							+paths.getString("SMS.TEMPLATES.LINK"));	
			
				preparedStatement.setString(4, "I");
				preparedStatement.setDate(5, currentDate);
				preparedStatement.setString(6, String.valueOf(schoolid));
				preparedStatement.executeUpdate();
				con.close();
			}
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			con.close();
		}
	}
	// Email
	private static void studentPerformUrgentmail(
			StudentPerformanceDataBean studentPerformanceDataBean, String mail,
			String schoolName, int schoolid) throws SQLException {
		String messege="";
		
		try{
			String email_header="URGENT NOTICE |"+schoolName;
			if (!"".equalsIgnoreCase(mail) && mail !=null ) {
			Class.forName(JDBC_DRIVER);
			messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")
					+paths.getString("EMAIL.TEMPLATES.URGENTMAIL")+paths.getString("EMAIL.TEMPLATES.NOTICEBOARDEND");
			
			
			
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(mail,email_header,messege);
			logger.info("Successfully  Email Called Service------------");	
			
			/*con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			preparedStatement=con.prepareStatement(Query);
			preparedStatement.setString(1, mail);
			preparedStatement.setString(2, email_header); 
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS. perhatian segera diperlukan.Silahkan kunjungi sekolah");	
			}else{
			preparedStatement.setString(3, messege);
			}
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, String.valueOf(schoolid));
			preparedStatement.executeUpdate();
			con.close();
			}
			else{
				logger.info("No Emails are Available");*/
			}
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			con.close();
		}
	}
	// Student performance update for Email and SMS
	public static void studentPerformUpdate(StudentPerformanceDataBean studentPerformanceDataBean,String schoolName, int schoolid) throws SQLException {
		logger.debug("student perform insert  "+schoolName);
		try{
			mails=new ArrayList<String>();phones=new ArrayList<String>();
			mails.addAll(studentPerformanceDataBean.getMailid());
			phones.addAll(studentPerformanceDataBean.getPhonenos());
			mailPhone();
			for(int i=0;i<mails.size();i++){
				String mail=mails.get(i);
				//Email
				studentPerformInsertmail(studentPerformanceDataBean,schoolName,mail,schoolid);
			}			
			for(int i=0;i<phones.size();i++){
				if (phones.get(i)!="" && phones.get(i).substring(0,1).equalsIgnoreCase("+")){
				String phone=phones.get(i);
				//SMS
				studentPerformInsertphone(studentPerformanceDataBean,schoolName,phone,schoolid);
				}
			}			
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
	}

	// Register attendance for Email and SMS
	public static void attendanceTake(AttendanceDataBean attendanceDataBean,String schoolName, int schoolid) throws SQLException {
		logger.debug("take attendance   "+schoolName);
		String period="";
		try{
			if(schoolid==Integer.parseInt(paths.getString("MAHARISHI.SCHOOLID")) || schoolid==Integer.parseInt(paths.getString("SMRV.SCHOOLID"))){
				if (!attendanceDataBean.getPhonesNo().equalsIgnoreCase("") && attendanceDataBean.getPhonesNo().substring(0,1).equalsIgnoreCase("+")) {
					//SMS
					attendanceTakephone(attendanceDataBean,attendanceDataBean.getPhonesNo(),schoolName,period,schoolid);
				}
				else{
					logger.info("No Phone Number Available for sending messege");
				}
				if (!attendanceDataBean.getMailid().equalsIgnoreCase("")) {
					//Email
					attendanceTakemail(attendanceDataBean,attendanceDataBean.getMailid(),schoolName,period,schoolid);
				}
				else {
					logger.info("No Mail Id Available for Sending Mail");
				}
			}
			else {
			mails=new ArrayList<String>();phones=new ArrayList<String>();
			mails.addAll(attendanceDataBean.getMails());
			phones.addAll(attendanceDataBean.getPhones());
			mailPhone();
			if (attendanceDataBean.getPeriod().equals("1")) period = "st Period";
			else if (attendanceDataBean.getPeriod().equals("2")) period = "nd Period";
			else if (attendanceDataBean.getPeriod().equals("3")) period = "rd Period";
			else period = "th Period";
			for(int i=0;i<mails.size();i++){
				String mail=mails.get(i);
				//Email
				attendanceTakemail(attendanceDataBean,mail,schoolName,period,schoolid);
			}				
			for(int i=0;i<phones.size();i++){
				if (phones.get(i)!="" && phones.get(i).substring(0,1).equalsIgnoreCase("+")){
				String phone=phones.get(i);
				//SMS
				attendanceTakephone(attendanceDataBean,phone,schoolName,period,schoolid);
				}
			}			
		}
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
	}
	// SMS
	private static void attendanceTakephone(AttendanceDataBean attendanceDataBean, String phone,
			String schoolName, String period, int schoolid) throws SQLException {
		logger.debug("inside attendanceTakephone");
		try{
			//Class.forName(JDBC_DRIVER);
			//con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			preparedStatement=getSMSDB(con);
			if (phone!="" && phone.substring(0,1).equalsIgnoreCase("+")){
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(1, "Stumosys");
				preparedStatement.setString(2, phone); 
					preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."+attendanceDataBean.getStudentName()+ "Tidak Hadir dikelas"
				+attendanceDataBean.getPeriod() + period +"."+paths.getString("SMS.TEMPLATES.LINK"));
				preparedStatement.setString(4, "I");
				preparedStatement.setDate(5, currentDate);
				preparedStatement.setString(6, String.valueOf(schoolid));
				preparedStatement.executeUpdate();
				con.close();
			}
			
			else if(schoolid==Integer.parseInt(paths.getString("MAHARISHI.SCHOOLID")) || schoolid==Integer.parseInt(paths.getString("SMRV.SCHOOLID"))) {
				logger.info("inside email");
				preparedStatement.setString(1, "Stumosys");
				preparedStatement.setString(2, phone); 
				preparedStatement.setString(3, ""+ schoolName+ "-" +attendanceDataBean.getClassname()+"-"+attendanceDataBean.getStudentID()+"-"
						+ "Absent on " + dateFormat.format(attendanceDataBean.getMonthDates())+"."+paths.getString("SMS.TEMPLATES.LINK"));
				preparedStatement.setString(4, "I");
				preparedStatement.setDate(5, currentDate);
				preparedStatement.setString(6, String.valueOf(schoolid));
				preparedStatement.executeUpdate();
				con.close();
			}
			
			}
			else{
				logger.info("Phone Number Not valid");
			}
			
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if(con!=null) {
			con.close();
			}
		}
	}

	// Email
	private static void attendanceTakemail(
			AttendanceDataBean attendanceDataBean, String mail,
			String schoolName, String period, int schoolid) throws SQLException {
		String messege="";
		
		try{
			String email_header="ATTENDANCE STATUS |"+schoolName;
			if (!"".equalsIgnoreCase(mail) && mail !=null ) {
			messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")
					+paths.getString("EMAIL.TEMPLATES.ATTENDANCEMAIL")+attendanceDataBean.getStudentName()+"is Absent Today.</div>"
					+paths.getString("EMAIL.TEMPLATES.NOTICEBOARDEND");
					
			
			
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(mail,email_header,messege);
			logger.info("Successfully  Email Called Service------------");	
			
			
			/*Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			preparedStatement=con.prepareStatement(Query);
			preparedStatement.setString(1, mail);
			preparedStatement.setString(2, email_header); 
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."+"attendanceDataBean.getStudentName()"+ "Tidak Hadir dikelas"
			+attendanceDataBean.getPeriod() + period+"."+paths.getString("SMS.TEMPLATES.LINK"));
			}else{
			preparedStatement.setString(3, messege);	
			}
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, String.valueOf(schoolid));
			preparedStatement.executeUpdate();
			}
			else{
				logger.info("No Emails are Available");*/
			}
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			con.close();
		}
	}

	// Attendance update for Email and SMS
	public static void attendanceUpdate(AttendanceDataBean attendanceDataBean,String schoolName, int schoolid) throws SQLException {
		logger.debug("attendance update  "+schoolName);
		String period="";
		try{
			mails=new ArrayList<String>();phones=new ArrayList<String>();
			mails.addAll(attendanceDataBean.getMails());
			phones.addAll(attendanceDataBean.getPhones());
			mailPhone();
			if (attendanceDataBean.getPeriod().substring(7, 8).equals("1")) period = "st Period";
			else if (attendanceDataBean.getPeriod().substring(7, 8).equals("2")) period = "nd Period";
			else if (attendanceDataBean.getPeriod().substring(7, 8).equals("3")) period = "rd Period";
			else period = "th Period";
			
			for(int i=0;i<mails.size();i++){
				String mail=mails.get(i);
				// Email
				attendanceUpdatemail(attendanceDataBean,period,mail,schoolName,schoolid);
			}			
			for(int i=0;i<phones.size();i++){
				if (phones.get(i)!="" && phones.get(i).substring(0,1).equalsIgnoreCase("+")){
				String messageId=messagesID();
				String phone=phones.get(i);
				// SMS
				attendanceUpdatephone(attendanceDataBean,period,phone,schoolName,messageId,schoolid);
				}
			}			
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
	}

	// SMS
	private static void attendanceUpdatephone(
			AttendanceDataBean attendanceDataBean, String period, String phone,
			String schoolName, String messageId, int schoolid) throws SQLException {
		//FacesContext context = FacesContext.getCurrentInstance();
		//ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
		try{
			//Class.forName(JDBC_DRIVER);
			//con=DriverManager.getConnection(DB_URL_SMS, USER, PASS);
			preparedStatement=getSMSDB(con);
			if (phone!="" && phone.substring(0,1).equalsIgnoreCase("+")){
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(1, "Stumosys");
				preparedStatement.setString(2, phone); 
				if (attendanceDataBean.getStatus().equals("Absent")) {
						preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."+"attendanceDataBean.getStudentName()"+ "Tidak Hadir dikelas"
					+attendanceDataBean.getPeriod().substring(7,8) + period+"."+paths.getString("SMS.TEMPLATES.LINK"));
				
				} else if (attendanceDataBean.getStatus().equals("Late")) {
						preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."+attendanceDataBean.getStudentName()
								+"Terlambat hadir dikelas"+ "at " + attendanceDataBean.getTime1() +"for"+ attendanceDataBean.getPeriod().substring(7, 8) + period
								+"."+paths.getString("SMS.TEMPLATES.LINK"));
					
				}
			
				preparedStatement.setString(4, "I");
				preparedStatement.executeUpdate();
				con.close();
			}
			}
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			con.close();
		}
	}

	// Email
	private static void attendanceUpdatemail(
			AttendanceDataBean attendanceDataBean, String period, String mail,
			String schoolName, int schoolid) throws SQLException {
		try{
			String email_header="ATTENDANCE UPDATE |"+schoolName;
			String message="";
			if (!"".equalsIgnoreCase(mail) && mail !=null ) {
			
				if (attendanceDataBean.getStatus().equals("Absent")) {
				
					message= "Welcome on-board into "+ schoolName+ " family.The Student " + attendanceDataBean.getStudentName()
							+ " is Absent Today " + attendanceDataBean.getPeriod().substring(7,8) + period+"."+paths.getString("SMS.TEMPLATES.LINK");	
					
				} else if (attendanceDataBean.getStatus().equals("Late")) {
					
					message = "The Student " + attendanceDataBean.getStudentName()
							+ " is Coming Late Today " + "at " + attendanceDataBean.getTime1() + " for "
							+ attendanceDataBean.getPeriod().substring(7, 8) + period
							+"."+paths.getString("SMS.TEMPLATES.LINK");
					}
				}	
				
				logger.info("Calling Email Service -------------");
				PushEmail.sendMail(mail,email_header,message);
				logger.info("Successfully  Email Called Service------------");	
				
				
				/*Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			preparedStatement=con.prepareStatement(Query);
			preparedStatement.setString(1, mail);
			preparedStatement.setString(2, email_header);
			if (attendanceDataBean.getStatus().equals("Absent")) {
				if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
					preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."+"attendanceDataBean.getStudentName()"+ "Tidak Hadir dikelas"
				+attendanceDataBean.getPeriod() + period+"."+paths.getString("SMS.TEMPLATES.LINK"));
				}else{
				preparedStatement.setString(3, "Welcome on-board into "+ schoolName+ " family.The Student " + attendanceDataBean.getStudentName()
						+ " is Absent Today " + attendanceDataBean.getPeriod().substring(7,8) + period+"."+paths.getString("SMS.TEMPLATES.LINK"));	
				}
			} else if (attendanceDataBean.getStatus().equals("Late")) {
				if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
					preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."+attendanceDataBean.getStudentName()
							+"Terlambat hadir dikelas"+ "at " + attendanceDataBean.getTime1() +"for"+ attendanceDataBean.getPeriod().substring(7, 8) + period
							+"."+paths.getString("SMS.TEMPLATES.LINK"));
				}else{
				preparedStatement.setString(3, "The Student " + attendanceDataBean.getStudentName()
						+ " is Coming Late Today " + "at " + attendanceDataBean.getTime1() + " for "
						+ attendanceDataBean.getPeriod().substring(7, 8) + period
						+"."+paths.getString("SMS.TEMPLATES.LINK"));
				}
			}				
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, String.valueOf(schoolid));
			//preparedStatement.setString(5, "");
			//preparedStatement.setBytes(6, null);
			preparedStatement.executeUpdate();
			con.close();
			}
			else{*/
				logger.info("No Emails are Available");
			//}
		}catch(Exception w){
			w.printStackTrace();
		}finally{
			con.close();
		}
	}
	// Home work register for Email and SMS
	public static void homeworkInsert(HomeworkDatabean homeworkDatabean,String schoolName, int schoolid) throws SQLException {
		logger.debug("add home work  "+schoolName);
		try{
			mails=new ArrayList<String>();phones=new ArrayList<String>();
			mails.addAll(homeworkDatabean.getMails());
			phones.addAll(homeworkDatabean.getPhones());
			mailPhone();
			for(int i=0;i<mails.size();i++){
				String mail=mails.get(i);
				// Email
				homeworkInsertmail(homeworkDatabean,mail,schoolName,schoolid);
			}			
			for(int i=0;i<phones.size();i++){
				if (phones.get(i)!="" && phones.get(i).substring(0,1).equalsIgnoreCase("+")){
				String phone=phones.get(i);
				// SMS 
				homeworkInsertphone(homeworkDatabean,phone,schoolName,schoolid);
				}
			}			
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
	}
	// SMS
	private static void homeworkInsertphone(HomeworkDatabean homeworkDatabean,
			String phone, String schoolName,  int schoolid) throws SQLException {
		try{
			preparedStatement=getSMSDB(con);
			if (phone!="" && phone.substring(0,1).equalsIgnoreCase("+")){
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(1, "Stumosys");
				preparedStatement.setString(2, phone); 
					preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS. Rumah Kerja untuk"
							+homeworkDatabean.getSubject() + " is " + homeworkDatabean.getHomework()+paths.getString("SMS.TEMPLATES.LINK"));
				preparedStatement.setString(4, "I");
				preparedStatement.setDate(5, currentDate);
				preparedStatement.setString(6, String.valueOf(schoolid));
				preparedStatement.executeUpdate();
				con.close();
			}
			}
			
			else {
				logger.info("No messege For school");
			}
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			con.close();
		}
	}
	// Email
	private static void homeworkInsertmail(HomeworkDatabean homeworkDatabean,
			String mail, String schoolName, int schoolid) throws SQLException {
		String messege="";
		try{
			String email_header="HOME WORK |"+schoolName;
			if (!"".equalsIgnoreCase(mail) && mail !=null ) {
			Class.forName(JDBC_DRIVER);
			messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")
					+paths.getString("EMAIL.TEMPLATES.HOMEWORKINSERTMAIL")+homeworkDatabean.getSubject()+" is "+
					homeworkDatabean.getHomework()
					+paths.getString("EMAIL.TEMPLATES.NOTICEBOARDEND");
					
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(mail,email_header,messege);
			logger.info("Successfully  Email Called Service------------");	
			
		/*	
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			preparedStatement=con.prepareStatement(Query);
			preparedStatement.setString(1, mail);
			preparedStatement.setString(2, email_header);
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS. Rumah Kerja untuk"
						+homeworkDatabean.getSubject() + " is " + homeworkDatabean.getHomework()+paths.getString("SMS.TEMPLATES.LINK"));
			}else{
			preparedStatement.setString(3, messege);	
			}
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, String.valueOf(schoolid));
			preparedStatement.executeUpdate();
			con.close();
			}
			else{*/
				logger.info("No Emails are Available");
			}
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			con.close();
		}
	}
	// Home work update for Email and SMS 
	public static void homeworkUpdate(HomeworkDatabean homeworkDatabean,String schoolName, int schoolid) throws SQLException {
		logger.debug("update home work  "+schoolName);
		try{
			mails=new ArrayList<String>();phones=new ArrayList<String>();
			mails.addAll(homeworkDatabean.getMails());
			phones.addAll(homeworkDatabean.getPhones());
			mailPhone();
			for(int i=0;i<mails.size();i++){
				String mail=mails.get(i);
				// Email
				homeworkUpdatemail(homeworkDatabean,mail,schoolName,schoolid);
			}			
			for(int i=0;i<phones.size();i++){
				if (phones.get(i)!="" && phones.get(i).substring(0,1).equalsIgnoreCase("+")){
				String phone=phones.get(i);
				// SMS 
				homeworkUpdatephone(homeworkDatabean,phone,schoolName,schoolid);
				}
			}			
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
	}
	// SMS 
	private static void homeworkUpdatephone(HomeworkDatabean homeworkDatabean,
			String phone, String schoolName,  int schoolid) throws SQLException {
		try{
			preparedStatement = getSMSDB(con);
			if (phone!="" && phone.substring(0,1).equalsIgnoreCase("+")){
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(1, "Stumosys");
				preparedStatement.setString(2, phone); 
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS. Rumah Kerja untuk"
							+homeworkDatabean.getSubject() + " diperbarui dan pekerjaan adalah " + homeworkDatabean.getHomework()
							+paths.getString("SMS.TEMPLATES.LINK"));
				preparedStatement.setString(4, "I");
				preparedStatement.setDate(5, currentDate);
				preparedStatement.setString(6, String.valueOf(schoolid));
				preparedStatement.executeUpdate();
				con.close();
			}
			}
			
			else {
				logger.info("No Messeges for this school");
			}
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			con.close();
		}
	}

	// Email
	private static void homeworkUpdatemail(HomeworkDatabean homeworkDatabean,
			String mail, String schoolName, int schoolid) throws SQLException {
			String messege="";
			try{
				String email_header="HOME WORK UPDATE |"+schoolName;
				if (!"".equalsIgnoreCase(mail) && mail !=null ) {
				Class.forName(JDBC_DRIVER);
				messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")
						+paths.getString("EMAIL.TEMPLATES.HOMEWORKINSERTMAIL")+homeworkDatabean.getSubject()+" is "+
						homeworkDatabean.getHomework()
						+paths.getString("EMAIL.TEMPLATES.NOTICEBOARDEND");
			
				
				logger.info("Calling Email Service -------------");
				PushEmail.sendMail(mail,email_header,messege);
				logger.info("Successfully  Email Called Service------------");
				
				/*con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			preparedStatement=con.prepareStatement(Query);
			preparedStatement.setString(1, mail);
			preparedStatement.setString(2, email_header);
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS. Rumah Kerja untuk"
						+homeworkDatabean.getSubject() + " diperbarui dan pekerjaan adalah " + homeworkDatabean.getHomework()
						+paths.getString("SMS.TEMPLATES.LINK"));
			}else{
			preparedStatement.setString(3, messege);	
			}	
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, String.valueOf(schoolid));
			preparedStatement.executeUpdate();
			con.close();
				}
				else{*/
					logger.info("No Emails are Available");
				}
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			con.close();
		}
	}
	// This is method is used to insert into PollerDB in Linux local server for Email notification
	public static void addReportCard(ReportCardDataBean reportCardDataBean,String schoolName, int schoolid) throws SQLException {
		logger.debug("add report card  "+schoolName);
		String messege="";
		
		try{
			if (reportCardDataBean.getMailid() !=null && reportCardDataBean.getMailid() != "") {
		//	Class.forName(JDBC_DRIVER);
			messege=paths.getString("EMAIL.TEMPLATES.TOP1")+schoolName+paths.getString("EMAIL.TEMPLATES.TOP2")
					+paths.getString("EMAIL.TEMPLATES.ADDREPORTCARDMAIL")+" Details of "+
					reportCardDataBean.getExamMarkTitle() +" Marks for " + reportCardDataBean.getMarkSubTitle()+" is "+reportCardDataBean.getMark()+
					" , Grade - "+reportCardDataBean.getGrade()+" and the status is "+reportCardDataBean.getResultStatus()
					+paths.getString("EMAIL.TEMPLATES.NOTICEBOARDEND");
			
			
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(reportCardDataBean.getMailid(),reportCardDataBean.getExamMarkTitle() + " Marks for " + reportCardDataBean.getMarkSubTitle(),messege);
			logger.info("Successfully  Email Called Service------------");
			
			/*con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			
			preparedStatement=con.prepareStatement(Query);
			preparedStatement.setString(1, reportCardDataBean.getMailid());
			preparedStatement.setString(2, reportCardDataBean.getExamMarkTitle() + " Marks for " + reportCardDataBean.getMarkSubTitle());
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."+reportCardDataBean.getExamMarkTitle()+"is"+"Tingkatkan lagi prestasinya"
				+reportCardDataBean.getMark()+" , Grade - "+reportCardDataBean.getGrade()+"dan statusnya"+reportCardDataBean.getResultStatus()+paths.getString("SMS.TEMPLATES.LINK"));
			}else{	
			preparedStatement.setString(3, messege);
			}
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, String.valueOf(schoolid));
			preparedStatement.executeUpdate();
			logger.info("Mail Successfully inserted into email_temp table");
			}
			else{
				logger.info("No Emails are Available");
			}
			if (reportCardDataBean.getPhoneno()!="" && reportCardDataBean.getPhoneno().substring(0,1).equalsIgnoreCase("+")){
			
			preparedStatement = getSMSDB(con);
			preparedStatement.setString(1, "Stumosys");
			preparedStatement.setString(2,  reportCardDataBean.getPhoneno()); 
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."
						+reportCardDataBean.getExamMarkTitle() +"Marks untuk"+reportCardDataBean.getMarkSubTitle()
						+paths.getString("SMS.TEMPLATES.LINK"));
			}else{
			preparedStatement.setString(3, "Dear Parents, We have listed the"
					+ reportCardDataBean.getExamMarkTitle() + " Marks for " + reportCardDataBean.getMarkSubTitle()+"."
					+paths.getString("SMS.TEMPLATES.LINK"));
			}
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, String.valueOf(schoolid));
			preparedStatement.executeUpdate();
			}
		
			else {*/
				logger.info("no Number to send");
			}
		
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
	}

	// This is method is used to insert into PollerDB in Linux local server for Email notification
	public static void teacherUpdate(TeacherDataBean teacherDataBean,String schoolName, int schoolid) throws SQLException, IOException {}
	


	public static void parentUpdate(ParentsDataBean parentsDataBean,String schoolName, int schoolid) throws SQLException, IOException {}

	public static void staffUpdate(StaffDataBean staffDataBean,String schoolName, int schoolid) throws SQLException, IOException {}

	public static void librarianUpdate(LibrarianDataBean librarianDataBean,String schoolName, int schoolid) throws SQLException, IOException {}

	public static void bookShopUpdate(StaffDataBean staffDataBean,String schoolName, int schoolid) throws SQLException, IOException {}

	public static void feesRegister(PaymentDatabean paymentDatabean,String schoolName, int schoolid) throws SQLException {
		logger.debug("fees register school "+schoolName);
		try{
			String messege="";
			String email_header="FEES STRUCTURE |"+schoolName;
			if (!"".equalsIgnoreCase( paymentDatabean.getMailId()) && paymentDatabean.getMailId() !=null) {
			
				messege =  " You can check "+paymentDatabean.getPaymentStudID()+"'s fees structure. "
						+ "Total Fees  :  Rs.  "+paymentDatabean.getTotalFees()
						+paths.getString("SMS.TEMPLATES.LINK");
				
				logger.info("Calling Email Service -------------");
				PushEmail.sendMail(paymentDatabean.getMailId(),email_header,messege);
				logger.info("Successfully  Email Called Service------------");
						
				
				
				/*Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			stmt=con.createStatement();
			preparedStatement=con.prepareStatement(Query);
			preparedStatement.setString(1, paymentDatabean.getMailId());
			preparedStatement.setString(2, email_header); 
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."+paymentDatabean.getPaymentStudID()
						+"sebesar Rp." +paymentDatabean.getTotalFees()+"- sudah jatuh tempo. Segera lakukan pembayaran."
						+paths.getString("SMS.TEMPLATES.LINK"));
			}else{
			preparedStatement.setString(3, " You can check "+paymentDatabean.getPaymentStudID()+"'s fees structure. "
					+ "Total Fees  :  Rs.  "+paymentDatabean.getTotalFees()
					+paths.getString("SMS.TEMPLATES.LINK"));
			}
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, String.valueOf(schoolid));
			//preparedStatement.setString(5, "");
			//preparedStatement.setBytes(6, null);
			preparedStatement.executeUpdate();	
			}
			else{
				logger.info("No Emails are Available");
			}
			if (!"".equalsIgnoreCase( paymentDatabean.getPhoneno()) && paymentDatabean.getPhoneno().substring(0,1).equalsIgnoreCase("+")){
			preparedStatement = getSMSDB(con);			
			preparedStatement.setString(1, "Stumosys");
			preparedStatement.setString(2, paymentDatabean.getPhoneno()); 
			if(schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
				preparedStatement.setString(3,"Assalamua’alaikum wr wb. Yth Bapak/Ibu, Selamat datang di STUMOSYS."+paymentDatabean.getPaymentStudID()
						+"sebesar Rp." +paymentDatabean.getTotalFees()+"- sudah jatuh tempo. Segera lakukan pembayaran."
						+paths.getString("SMS.TEMPLATES.LINK"));
			}else{
			preparedStatement.setString(3, " You can check "+paymentDatabean.getPaymentStudID()+"'s fees structure. "
					+ "Total Fees  :  Rs.  "+paymentDatabean.getTotalFees());
			}
			
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, String.valueOf(schoolid));
			preparedStatement.executeUpdate();*/
			
			}
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
	}

	public static void paymentApprove(PaymentDatabean paymentDatabean,String schoolName) throws SQLException {}

	public static void paymentUpdate(PaymentDatabean paymentDatabean,String schoolName, int schoolid) throws SQLException {}

	public static void feespay(PaymentDatabean paymentDatabean,String schoolName) throws SQLException {}

			
	
	public static void insertschool(LoginAccess loginaccess) throws SQLException {
		//String schoolID="";
		try{
			String message = "";
			//schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			String email_header="STUMOSYS REGISTRATION CONFIRMED |"+loginaccess.getUsername();
			if (!"".equalsIgnoreCase(loginaccess.getSchoolEmail()) && loginaccess.getSchoolEmail() !=null ) {
			
				message = "Welcome on-board Your "
						+ "User Name  - " + loginaccess.getUsername() + " and Password  - "+ loginaccess.getUserpassword()+paths.getString("SMS.TEMPLATES.LINK");
				
				logger.info("Calling Email Service -------------");
				PushEmail.sendMail(loginaccess.getSchoolEmail(),email_header,message);
				logger.info("Successfully  Email Called Service------------");
						
				
				
				/*Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			stmt=con.createStatement();
			preparedStatement=con.prepareStatement(Query);
			preparedStatement.setString(1, loginaccess.getSchoolEmail());
			preparedStatement.setString(2, email_header); 
			preparedStatement.setString(3, "Welcome on-board Your "
					+ "User Name  - " + loginaccess.getUsername() + " and Password  - "+ loginaccess.getUserpassword()+paths.getString("SMS.TEMPLATES.LINK"));
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, schoolID);
			preparedStatement.executeUpdate();
			}
			else{
				logger.info("No Emails are Available");
			}
			
			if (!"".equalsIgnoreCase(loginaccess.getSchoolPhone()) &&  loginaccess.getSchoolPhone().substring(0,1).equalsIgnoreCase("+")){
			preparedStatement=getSMSDB(con);
			preparedStatement.setString(1, "Stumosys");
			preparedStatement.setString(2, loginaccess.getSchoolPhone()); 
			preparedStatement.setString(3, "Welcome to STUMOSYS Login Here http://stumosys.neotural.com With Your "
					+ "User Name  (" + loginaccess.getUsername() + ") and Password  ( "+ loginaccess.getUserpassword()+")");
		
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, schoolID);
			preparedStatement.executeUpdate();*/
			}
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
	}
	// OTP for Email
	public static String insertotp(LoginAccess loginaccess, String mailid) throws SQLException {
		String status="fail";
	//	String schoolID="";
		logger.info("insertotp 1");
		try{
		//	schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			String email_header="STUMOSYS OTP";
			if (!"".equalsIgnoreCase(mailid) && mailid !=null ) {
			
				
				logger.info("Calling Email Service -------------");
				PushEmail.sendMail(mailid,email_header,"Your OTP is"+" "+(loginaccess.getTemOTP()));
				logger.info("Successfully  Email Called Service------------");
				
				
			/*	Class.forName(JDBC_DRIVER);
			logger.info("insertotp 2");
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			stmt=con.createStatement();
			logger.info("insertotp 3");
			preparedStatement=con.prepareStatement(Query);
			preparedStatement.setString(1, mailid);
			logger.info("insertotp 4");
			preparedStatement.setString(2, email_header); 
			preparedStatement.setString(3,  "Your OTP is"+" "+(loginaccess.getTemOTP())
					+"."+"Enter Your OTP and reset your password");
			preparedStatement.setString(4, "I");
			logger.info("insertotp 5");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, schoolID);
			logger.info("insertotp 6");
			preparedStatement.executeUpdate();
			logger.info("insertotp 7");*/
			status="success";
			}
			else{
				logger.info("No Emails are Available");
			}
		}catch(Exception e){
			status="success";
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
		return status;
	}
	
		
	// OTP Send 
	public static String OTPSend(RegisteredLogin registeredLogin) throws SQLException, IOException{
		//String schoolID="";
		   try{
			  // schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			   String email_header="STUMOSYS OTP CONFIRMED |"+registeredLogin.getUsername();
			   if (!"".equalsIgnoreCase(registeredLogin.getEmailid()) && registeredLogin.getEmailid() !=null) {
		   
				   
				   
		   logger.info("Calling Email Service -------------");
			PushEmail.sendMail(registeredLogin.getEmailid(),email_header,registeredLogin.getOtpnumber());
			logger.info("Successfully  Email Called Service------------");
					
				   
			/*	   Class.forName(JDBC_DRIVER);
		    con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
		    stmt=con.createStatement();
		    preparedStatement=con.prepareStatement(Query);
		    preparedStatement.setString(1, registeredLogin.getEmailid());
		    preparedStatement.setString(2, email_header); 
		    preparedStatement.setString(3, registeredLogin.getOtpnumber());
		    preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, schoolID);
		    preparedStatement.executeUpdate();  
			   } else{
					logger.info("No Emails are Available");*/
				}
		   }catch(Exception e){
		    			logger.warn("Exception -->"+e.getMessage());
		   }finally{
		    if (preparedStatement != null) preparedStatement.close();
		    if (con != null) con.close();
		   }
		   return "";
		 }
		
	// OTP Mail
		public static String OTPMail(RegisteredLogin registeredLogin) throws SQLException, IOException{
			//String schoolID="";
		   try{
			  // schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			   if (!"".equalsIgnoreCase(registeredLogin.getEmailid()) && registeredLogin.getEmailid() !=null) {
		  
				   
				   logger.info("Calling Email Service -------------");
					PushEmail.sendMail(registeredLogin.getEmailid(),"STUMOSYS REGISTRATION CONFIRMED-" + registeredLogin.getUsername(),"Welcome on-board Your "
							+ "User Name  - " + registeredLogin.getUsername() + " and Password  - "+ registeredLogin.getSecurePassword()
							+paths.getString("SMS.TEMPLATES.LINK"));
					logger.info("Successfully  Email Called Service------------");
				   
				 /*  
				   Class.forName(JDBC_DRIVER);
		    con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
		    stmt=con.createStatement();
		    preparedStatement=con.prepareStatement(Query);
		    preparedStatement.setString(1, registeredLogin.getEmailid());
		    preparedStatement.setString(2, "STUMOSYS REGISTRATION CONFIRMED-" + registeredLogin.getUsername()); 
		    preparedStatement.setString(3, "Welcome on-board Your "
					+ "User Name  - " + registeredLogin.getUsername() + " and Password  - "+ registeredLogin.getSecurePassword()
					+paths.getString("SMS.TEMPLATES.LINK"));
		    preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, schoolID);
		    preparedStatement.executeUpdate();  
			   }
			   else{*/
					logger.info("No Emails are Available");
				}
			   
		   }catch(Exception e){
		    			logger.warn("Exception -->"+e.getMessage());
		   }finally{
		    if (preparedStatement != null) preparedStatement.close();
		    if (con != null) con.close();
		   }
		   return "";
		 }
	
		
	
		public static void studentinsert(StudentDataBean studentDataBean) throws SQLException, IOException {
			String messege="";
		///	String schoolID="";
			try{
				//schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
				if (!"".equalsIgnoreCase(studentDataBean.getStuEmail()) && studentDataBean.getStuEmail() !=null) {
				Class.forName(JDBC_DRIVER);
				messege=paths.getString("EMAIL.TEMPLATES.TOP1")+studentDataBean.getSchoolName()+paths.getString("EMAIL.TEMPLATES.TOP2")
						+paths.getString("EMAIL.TEMPLATES.USERNAMEPASS")
						+ paths.getString("EMAIL.TEMPLATES.MIDDLE")+paths.getString("EMAIL.TEMPLATES.USERNAMEPASSTITLE")+"<td>"+
						studentDataBean.getStuUsername()+
						"</td><td>"+studentDataBean.getStuSecurePasword()+"</td><td>"+"Student"+"</td>"+paths.getString("EMAIL.TEMPLATES.END");
				
				
				
				
				logger.info("Calling Email Service -------------");
				PushEmail.sendMail(studentDataBean.getStuEmail(),"STUMOSYS REGISTRATION CONFIRMED-" + studentDataBean.getStuUsername(),messege);
				logger.info("Successfully  Email Called Service------------");
							
				
				
				/*con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
				stmt=con.createStatement();
				preparedStatement=con.prepareStatement(Query);
				preparedStatement.setString(1, studentDataBean.getStuEmail());
				preparedStatement.setString(2, "STUMOSYS REGISTRATION CONFIRMED-" + studentDataBean.getStuUsername()); 
				preparedStatement.setString(3, messege);
				preparedStatement.setString(4, "I");
				preparedStatement.setDate(5, currentDate);
				preparedStatement.setString(6, schoolID);
				preparedStatement.executeUpdate();
				}
				else{
					logger.info("No Emails are Available");
				}
				if (!"".equalsIgnoreCase(studentDataBean.getStuPhoneNo()) && studentDataBean.getStuPhoneNo().substring(0,1).equalsIgnoreCase("+")){
				preparedStatement=getSMSDB(con);

				preparedStatement.setString(1, "Stumosys");
				preparedStatement.setString(2, studentDataBean.getStuPhoneNo()); 
				preparedStatement.setString(3, "Welcome to STUMOSYS Login Here http://www.stumosys.neotural.com With Your "
						+ "User Name " + studentDataBean.getStuUsername() + " and Password "+ studentDataBean.getStuSecurePasword());
				preparedStatement.setString(4, "I");
				preparedStatement.setDate(5, currentDate);
				preparedStatement.setString(6, schoolID);
				preparedStatement.executeUpdate();
			*/}
			}catch(Exception e){
							logger.warn("Exception -->"+e.getMessage());
			}finally{
				if (preparedStatement != null) preparedStatement.close();
				if (con != null) con.close();
			}
		}
		
		public static void studentupdate(StudentDataBean studentDataBean) throws SQLException, IOException {
			logger.info("NO sms going | function is disabled");

		}

		static String schoolLogo="select * from School where school_ID like ? and status like ?";
		public static String pdfProcess(int schoolID){
			 String JDBC_DRIVERs ="com.mysql.jdbc.Driver";  
			 String DB_URL =database.getString("DB_URL");
			 String USER = database.getString("USER");
			 String PASS = database.getString("PASS");
			 Connection con = null;  
			 String logo="";
			 try{
				 logger.debug("jdbc driver "+JDBC_DRIVER);
				 Class.forName(JDBC_DRIVERs);
				 con=DriverManager.getConnection(DB_URL, USER, PASS);
				 stmt = con.createStatement();
				 preparedStatement = con.prepareStatement(schoolLogo);
				 preparedStatement.setInt(1, schoolID);
				 preparedStatement.setString(2, "Active");
				 ResultSet rs = preparedStatement.executeQuery();
		   		 while (rs.next()) {
					  logo = rs.getString("logopath");
				 }
			 }
			 catch(Exception e){
				 			logger.warn("Exception -->"+e.getMessage());
			 }
			 return logo;
		}

	
	
		// Class period add for Email and SMS 
	public static String classPeriodAdd(ClassTimeTableDataBean classTimeTableDataBean, String schoolName) throws SQLException{
		logger.info("------------------- classPeriodAdd start ----------------------");
		logger.debug("Add class period time table school "+schoolName);
		try{
			mails=new ArrayList<String>();phones=new ArrayList<String>();
			mails.addAll(classTimeTableDataBean.getMails());
			phones.addAll(classTimeTableDataBean.getPhonenos());
			mailPhone();
		
			for(int i=0;i<mails.size();i++){
				String mail=mails.get(i);
				// Email
				classPeriodAddemail(classTimeTableDataBean,mail,schoolName);
			}			
			for(int i=0;i<phones.size();i++){
				if (!"".equalsIgnoreCase(phones.get(i)) && phones.get(i).substring(0,1).equalsIgnoreCase("+")){
				/*String messageId=messagesID();*/
				String phone=phones.get(i);
				// SMS 
				classPeriodAddphone(classTimeTableDataBean,phone,schoolName);
				}
			}	
			logger.info("------------------- classPeriodAdd end ----------------------");
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		}finally{
			if (preparedStatement != null) preparedStatement.close();
			if (con != null) con.close();
		}
		return "";
	}

	// SMS 
	private static void classPeriodAddphone(ClassTimeTableDataBean classTimeTableDataBean, String phone,
			String schoolName) throws SQLException {
		logger.info("------------------- classPeriodAddphone start ----------------------");

		try{
			if (!"".equalsIgnoreCase(phone) && phone.substring(0,1).equalsIgnoreCase("+")){
				preparedStatement=getSMSDB(con);
				preparedStatement.setString(1, "Stumosys");
				preparedStatement.setString(2, phone); 
				preparedStatement.setString(3, "New Period for "+classTimeTableDataBean.getDay()+" is "
						+ classTimeTableDataBean.getSubject() + " at " + classTimeTableDataBean.getTimeStart() + " to "
						+ classTimeTableDataBean.getTimeEnd()+"."
						+paths.getString("SMS.TEMPLATES.LINK"));
				preparedStatement.setString(6, "I");
				preparedStatement.executeUpdate();
				con.close();
			logger.info("Successfully inserted classPeriodAddphone in pollerdb");
			}
		}catch(Exception e){
						logger.warn("Exception -->"+e.getMessage());
		}finally{
			con.close();
		}
	}

	// Email
	private static void classPeriodAddemail(
			ClassTimeTableDataBean classTimeTableDataBean, String mail,
			String schoolName) throws SQLException {
		logger.info("------------------- classPeriodAddemail start ----------------------");
	//	String schoolID="";
	try{
	//	schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		if (!"".equalsIgnoreCase(mail) && mail !=null) {
		
			
			
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(mail,"NEW PERIOD" ,"New Period for "+classTimeTableDataBean.getDay()+" is "
					+ classTimeTableDataBean.getSubject() + " at " + classTimeTableDataBean.getTimeStart() + " to "
					+ classTimeTableDataBean.getTimeEnd()+"."+paths.getString("SMS.TEMPLATES.LINK"));
			logger.info("Successfully  Email Called Service------------");
					
			
			
			/*
			Class.forName(JDBC_DRIVER);
		con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
		preparedStatement=con.prepareStatement(Query);
		preparedStatement.setString(1, mail);
		preparedStatement.setString(2, "NEW PERIOD"); 
		preparedStatement.setString(3, "New Period for "+classTimeTableDataBean.getDay()+" is "
				+ classTimeTableDataBean.getSubject() + " at " + classTimeTableDataBean.getTimeStart() + " to "
				+ classTimeTableDataBean.getTimeEnd()+"."+paths.getString("SMS.TEMPLATES.LINK"));
		preparedStatement.setString(4, "I");
		preparedStatement.setDate(5, currentDate);
		preparedStatement.setString(6, schoolID);
		preparedStatement.executeUpdate();
		con.close();
		logger.info("------------------- classPeriodAddemail end ----------------------");
		}
		else{*/
			logger.info("No Emails are Available");
		}
	}catch(Exception e){
					logger.warn("Exception -->"+e.getMessage());
	}finally{
		con.close();
	}
		
	}
	
	
	public static PreparedStatement getSMSDB(Connection con){
		//Connection con = null;
		PreparedStatement preparedStatement=null;
		try {
			logger.info("Before SMS DB Connection start ---------------------------------");
			Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_SMS, USER, PASS);
			preparedStatement=con.prepareStatement(smsQuery);	
			logger.info("Before SMS DB Connection end ---------------------------------");

		}catch(Exception e)
		{
			logger.warn("Exception -->"+e.getMessage());
		}
		finally{

			
			
		}
		return preparedStatement;
	}

	
	public static List<LoginAccess> searchvalues(String schoolID) {
		Connection con = null;
		PreparedStatement preparedStatement=null;
		List<LoginAccess> indexvalue1=null;List<LoginAccess> indexList=null;
		try{
			indexList=new ArrayList<LoginAccess>();
			indexvalue1=new ArrayList<LoginAccess>();
			con=DriverManager.getConnection(DBURL, USER, PASS);
			preparedStatement=con.prepareStatement("select * from Indexs_v where school_ID=?");
			preparedStatement.setString(1, schoolID);
			ResultSet rs = preparedStatement.executeQuery();
			
			  while(rs.next()){
				  
				  LoginAccess loginaccess1=new LoginAccess();
				  loginaccess1.setValue1(rs.getString("value"));
				  loginaccess1.setValue2(rs.getString("value"));
				  loginaccess1.setModuleName(rs.getString("module"));
				  loginaccess1.setP_id(rs.getString("p_id"));
				  loginaccess1.setPersonID(rs.getString("person_ID"));
				  loginaccess1.setLableValue(rs.getString("value")+"-"+rs.getString("module"));
				  indexvalue1.add(loginaccess1);
				  
				  LoginAccess loginaccess2=new LoginAccess();
				  loginaccess2.setValue1(rs.getString("value1"));
				  loginaccess2.setValue2(rs.getString("value"));
				  loginaccess2.setModuleName(rs.getString("module"));
				  loginaccess2.setP_id(rs.getString("p_id"));
				  loginaccess2.setPersonID(rs.getString("person_ID"));
				  loginaccess2.setLableValue(rs.getString("value1")+"-"+rs.getString("module"));
				  indexvalue1.add(loginaccess2);
				  
				  LoginAccess loginaccess3=new LoginAccess();
				  loginaccess3.setValue1(rs.getString("value2"));
				  loginaccess3.setValue2(rs.getString("value"));
				  loginaccess3.setModuleName(rs.getString("module"));
				  loginaccess3.setP_id(rs.getString("p_id"));
				  loginaccess3.setPersonID(rs.getString("person_ID"));
				  loginaccess3.setLableValue(rs.getString("value2")+"-"+rs.getString("module"));
				  indexvalue1.add(loginaccess3);
				  
				  LoginAccess loginaccess4=new LoginAccess();
				  loginaccess4.setValue1(rs.getString("value3"));
				  loginaccess4.setValue2(rs.getString("value"));
				  loginaccess4.setModuleName(rs.getString("module"));
				  loginaccess4.setP_id(rs.getString("p_id"));
				  loginaccess4.setPersonID(rs.getString("person_ID"));
				  loginaccess4.setLableValue(rs.getString("value3")+"-"+rs.getString("module"));
				  indexvalue1.add(loginaccess4);
				  
				  LoginAccess loginaccess5=new LoginAccess();
				  loginaccess5.setValue1(rs.getString("value4"));
				  loginaccess5.setValue2(rs.getString("value"));
				  loginaccess5.setModuleName(rs.getString("module"));
				  loginaccess5.setP_id(rs.getString("p_id"));
				  loginaccess5.setPersonID(rs.getString("person_ID"));
				  loginaccess5.setLableValue(rs.getString("value4")+"-"+rs.getString("module"));
				  indexvalue1.add(loginaccess5);
				  
			  }
			  indexList.addAll(indexvalue1);
		}catch (Exception e) {
			logger.error("Exception -->"+e.getMessage());
			//e.printStackTrace();
		}
	finally {
		 con = null;
		 preparedStatement=null;
	}
		return indexList;
		
	}
	
	public static String userDetailmail(LoginAccess loginAccess) throws SQLException, IOException {
		  logger.info("------------------- teacherInsert start ----------------------");
		  String status="";
		//String schoolID="";
		  try{
			 // schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			  if (!"".equalsIgnoreCase(loginAccess.getSchoolEmail()) && loginAccess.getSchoolEmail() !=null) {
		 
				  
				  
				  
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(loginAccess.getSchoolEmail(),"STUMOSYS REGISTRATION CONFIRMED-" + loginAccess.getSchoolName() ,"Username and Password for "+loginAccess.getUser()+"in"+loginAccess.getSchoolName()+"."
					   +paths.getString("SMS.TEMPLATES.LINK"));
			logger.info("Successfully  Email Called Service------------");
					
						  
				  
				  
				/*  Class.forName(JDBC_DRIVER);
		   con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
		   stmt=con.createStatement();
		   preparedStatement=con.prepareStatement(Query);
		   preparedStatement.setString(1, loginAccess.getSchoolEmail());
		   preparedStatement.setString(2, "STUMOSYS REGISTRATION CONFIRMED-" + loginAccess.getSchoolName()); 
		   preparedStatement.setString(3, "Username and Password for "+loginAccess.getUser()+"in"+loginAccess.getSchoolName()+"."
				   +paths.getString("SMS.TEMPLATES.LINK"));
		   preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, schoolID);
		   preparedStatement.executeUpdate();
		   status="success";
			  }
			  else{*/
					logger.info("No Emails are Available");
					   status="success";

			  }
		  }catch(Exception e){
		   logger.warn("Exception -->"+e.getMessage());
		   e.printStackTrace();
		  }finally{
		   if (preparedStatement != null) preparedStatement.close();
		   if (con != null) con.close();
		  }
		  return status;
		 }

	// SMS 
	public static void behaviourphonesms(StudentPerformanceDataBean studentPerformanceDataBean,String schoolName, int schoolid) throws SQLException	{
	logger.debug("inside behaviourphonesms");
	int i=0;
	try{
		if (!"".equalsIgnoreCase(studentPerformanceDataBean.getPhonenos().get(i)) && studentPerformanceDataBean.getPhonenos().get(i).substring(0,1).equalsIgnoreCase("+")){
		preparedStatement=getSMSDB(con);
		
		logger.debug("StuApp-->"+studentPerformanceDataBean.getStuApp()+"---StuAtt---->"+studentPerformanceDataBean.getStuAtt());		
		if(schoolid==Integer.parseInt(paths.getString("MAHARISHI.SCHOOLID")) || schoolid==Integer.parseInt(paths.getString("SMRV.SCHOOLID"))) 
		{
			logger.debug("inside sms behaviours Phonenos-->"+studentPerformanceDataBean.getPhonenos().get(i));
			preparedStatement.setString(1, "Stumosys");
			preparedStatement.setString(2, studentPerformanceDataBean.getPhonenos().get(i)); 
			preparedStatement.setString(3, ""+ schoolName+ "-" +studentPerformanceDataBean.getPerClassSection()+"-"+
			studentPerformanceDataBean.getPerformStudID().substring(studentPerformanceDataBean.getPerformStudID().indexOf("/", 1))+
					"-"+studentPerformanceDataBean.getStuApp()+"//"+studentPerformanceDataBean.getStuAtt()+" On " + dateFormat.format(studentPerformanceDataBean.getTeaDob())
					+paths.getString("SMS.TEMPLATES.LINK"));
			preparedStatement.setString(4, "I");
			preparedStatement.setDate(5, currentDate);
			preparedStatement.setString(6, String.valueOf(schoolid));
			preparedStatement.executeUpdate();
			con.close();
		
		}
		else {
			logger.info("No School Name find in Paths Property file ");
		}
		}
	}catch(Exception e){
					logger.warn("Exception -->"+e.getMessage());
	}finally{
		preparedStatement=null;
		con.close();
	}
}
	
	public static void getoverallpass(LoginAccess loginaccess, String schoolID) {
		Connection con = null;
		PreparedStatement preparedStatement=null;
		List<LoginAccess> overallmarkList=null;
		List<String> subjectList=null;
		HashSet<String> subj=null;
		try{
			overallmarkList=new ArrayList<LoginAccess>();
			subjectList=new ArrayList<String>();
			con=DriverManager.getConnection(DBURL, USER, PASS);
			preparedStatement=con.prepareStatement("select * from overallpass_piechart_v where school_ID=? and status=? and YEAR(created_date)= ?");
			preparedStatement.setString(1, schoolID);
			preparedStatement.setString(2, "Active");
			preparedStatement.setInt(3, loginaccess.getYear());
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				  LoginAccess login=new LoginAccess();
				  login.setS_id(rs.getInt("school_ID"));
				  login.setSubject_id(rs.getInt("subject_ID"));
				  login.setExamTitle(rs.getString("exam_title"));
				  login.setMark(rs.getString("mark"));
				  login.setResult_status(rs.getString("result_status"));
				  login.setStatus(rs.getString("status"));
				  login.setCreated_date(rs.getDate("created_date"));
				  login.setSubject_name(rs.getString("suject_name"));
				  subjectList.add(rs.getString("suject_name"));
				  overallmarkList.add(login);
			  }
			 subj=new HashSet<String>(subjectList);
			subjectList.clear();
			subjectList.addAll(subj);
			
			loginaccess.setSubjectList(subjectList);
			loginaccess.setOverallmarkList(overallmarkList);
			System.out.println("SubjectList-------"+loginaccess.getSubjectList().size());
			System.out.println("OverallmarkList-------"+loginaccess.getOverallmarkList().size());
		}catch (Exception e) {
		logger.error("Error -->"+e.getMessage());
			//e.printStackTrace();
		}
		finally {
			 con = null;
			 preparedStatement=null;
			 overallmarkList=null;
			 subjectList=null;
			 subj=null;
		}
		
	}
	
}