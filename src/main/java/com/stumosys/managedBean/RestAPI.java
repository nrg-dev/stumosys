package com.stumosys.managedBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.stumosys.controler.SmsController;
import com.stumosys.domain.AttendanceDataBean;
import com.stumosys.domain.ClassTimeTableDataBean;
import com.stumosys.domain.LoginAccess;
import com.stumosys.domain.StudentDataBean;
import com.stumosys.service.SmsService;
import com.stumosys.shared.SupProduct;
import com.stumosys.shared.UserProduct;

@Path("/RestAPI")
public class RestAPI{

	final static Logger logger = Logger.getLogger(RestAPI.class);
	SmsService service=(SmsService) SpringApplicationContext.getBean("smsservice");
	Gson gson=new Gson();
	ResourceBundle database =ResourceBundle.getBundle("com.sms.database");
	AttendanceDataBean attendanceDataBean=new AttendanceDataBean();
	List<ClassTimeTableDataBean> classTimetableList=null;
	StudentDataBean studentDataBean=new StudentDataBean();
	
	@POST
	@Path("/userLogin")
	@Consumes(MediaType.APPLICATION_JSON)
	public LoginAccess userLogin(LoginAccess loginAccess){
		logger.info("- RestAPI Inside LoginAccess ----");
		String status="";
		 List<UserProduct> mainMenuList=null;
    	 List<SupProduct> subMenuList=null;
		try{
			logger.info("- RestAPI Inside try LoginAccess ----");
			status=superadminlogin(loginAccess.getUsername(), loginAccess.getUserpassword());
			 if("Success".equalsIgnoreCase(status)){
				 loginAccess.setAuthentoicationStatus(status);
			 }else{
				 service.loginService(loginAccess);
				 logger.info("- RestAPI Inside LoginAccess else ----");
				 if("Authorized".equalsIgnoreCase(loginAccess.getAuthentoicationStatus())){
					 mainMenuList=new ArrayList<UserProduct>();
		    		 subMenuList=new ArrayList<SupProduct>();
		    		 loginAccess.setMainMeuList(new ArrayList<String>());
		    		 loginAccess.setSupMenuList(new ArrayList<String>());
		    		 mainMenuList=service.LoadMenu(loginAccess);
		    		 if(mainMenuList.size()>0){
		    			for (int i = 0; i < mainMenuList.size(); i++) {
							loginAccess.getMainMeuList().add(mainMenuList.get(i).getProduct().getProductName()+"/"+mainMenuList.get(i).getProduct().getProduct_ID());
							subMenuList=service.loadSubMenu(mainMenuList.get(i).getProduct().getProduct_ID(),mainMenuList.get(i).getProduct().getProductCode());
							if(subMenuList.size()>0){
							for (int j = 0; j < subMenuList.size(); j++) {
								loginAccess.getSupMenuList().add(subMenuList.get(j).getProductName()+"/"+subMenuList.get(j).getProductCode()+"/"+subMenuList.get(j).getProduct().getProduct_ID());
							}
							}
						}
					}
		    		if("Parent".equalsIgnoreCase(loginAccess.getUser_rolles())){
			    		 loginAccess.setStudentList(new ArrayList<String>());
			    		 loginAccess.setStudentList(service.parentAttlist(loginAccess.getPersonID(), attendanceDataBean));
		    		}
				 }
			 }
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		}
		return loginAccess;
	}
	
	 private String superadminlogin(String username, String userpassword) {
		 String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		 String DB_URL =database.getString("DB_URL");
		 String USER = database.getString("USER");
		 String PASS = database.getString("PASSWORD");
		 Connection con = null;  
		 Statement stmt=null;
		 String status="Fail";
		 try{
			 Class.forName(JDBC_DRIVER);
			 con=DriverManager.getConnection(DB_URL, USER, PASS);
			 stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT username, password FROM superadmin_login;");
			 while(rs.next()){
	                String userName = rs.getString("username");
	                String password = rs.getString("password");
	                if(userName.equalsIgnoreCase(username) && password.equalsIgnoreCase(userpassword)){
	                   status="Success";
	                }
			 }
		 }catch(Exception e){
				logger.warn("Exception -->"+e.getMessage());
		 }
		return status;
	}

	 @POST
	 @Path("/getstudentclassTimeTable")
	 @Consumes(MediaType.APPLICATION_JSON)
	 public String getstudentclassTimeTable(JSONObject jsonObject) throws JSONException{
		 String personID="";
		 try{
			 personID=jsonObject.getString("personID");
			 studentDataBean.setStuStudentId(jsonObject.getString("studentID").split("/")[1]);
			 classTimetableList=new ArrayList<ClassTimeTableDataBean>();
			 classTimetableList=service.getstudentclassTimeTable(personID, studentDataBean);
		 }catch(Exception e){
			 logger.warn("Exception -->"+e.getMessage());
		 }
		 return gson.toJson(classTimetableList);
	 }
	 
	 @GET
	 @Path("/getclassTimeTable/{id}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public String getclassTimeTable(@PathParam("id") String personID){
		 try{
			if(personID!=null){
				classTimetableList=new ArrayList<ClassTimeTableDataBean>();
				classTimetableList=service.getclassTimeTable(personID);
			}
		 }catch(Exception e){
			 logger.warn("Exception -->"+e.getMessage());
		 }
		return gson.toJson(classTimetableList); 
	 }
}
