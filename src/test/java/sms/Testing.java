package sms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.neo4j.cypher.internal.compiler.v2_1.perty.docbuilders.catchErrors;

import com.sun.jersey.multipart.BodyPart;

public class Testing {
	final static Logger logger = Logger.getLogger(Testing.class);
	/*static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/demo";
	static final String USER = "root";
	static final String PASS = "root";
	static final String PURCHASE_QUERY = "select * from purchase_v where tem_order_number like ?";
	public static String status;
	public static Connection con = null; 
	public static PreparedStatement preparedStatement=null; 
   
	public static void main(String[] args) {
		try{
			String name="po";
			logger.debug("1");
			Class.forName(JDBC_DRIVER);  
			logger.debug("12");
			con=DriverManager.getConnection(DB_URL,USER,PASS);  
			logger.debug("13");
			preparedStatement = con.prepareStatement(PURCHASE_QUERY);
			preparedStatement.setString(1, "%"+ name +"%");
			logger.debug("14");
			ResultSet rs = preparedStatement.executeQuery();
			logger.debug("15");
			while(rs.next())  {
			 logger.debug(rs.getString("tem_order_number"));
			 logger.debug("16");
			}
		  
			con.close();
			
	}catch(Exception e){
	}
	}*/
	
	public static final String SMTP_OUT_SERVER = "smtpout.secureserver.net";
    public static final String USER = "ragulan@neotural.com";
    public static final String PASSWORD = "Welcome01";
 
    public static void main(String[] args) {
        try {
            sendMail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public static void sendMail() throws Exception {
        Properties props = System.getProperties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", SMTP_OUT_SERVER);
 
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "25");
        props.setProperty("mail.user", USER);
        props.setProperty("mail.password", PASSWORD);
 
        Session mailSession = Session.getInstance(props);
        mailSession.setDebug(true);
        Transport transport = mailSession.getTransport("smtp");
        MimeMessage message = new MimeMessage(mailSession);
        message.setSentDate(new java.util.Date());
        message.setSubject("Hi Test");
        message.setFrom(new InternetAddress(USER));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("ragulanaz08@gmail.com"));
        MimeMultipart multipart = new MimeMultipart("related");
 
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Hi Ragulan", "text/html");
 
        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);
 
        transport.connect(SMTP_OUT_SERVER, USER, PASSWORD);
        transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }

}
