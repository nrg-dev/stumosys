package sms;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.stumosys.util.ZipValidationMB;
 
public class checkEmail {
	final static Logger logger = Logger.getLogger(checkEmail.class);
public static void main(String[] args) throws AddressException {
	
	
		String to="johnclinton5193@gmail.com";
		InternetAddress[] myCcList =InternetAddress.parse("robertarjun46@gmail.com,janivesh@gmail.com");
		logger.debug("To"+to);
		Properties prop=new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.auth", "true");  
		prop.put("mail.smtp.port", "587"); 
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		String body1="<htm><head></heade><body>"
				+ " <header style='background-color:orange;color:white;height:50px;'>"
				+ "<br></br>"
				+ "<center>"
				+"<span style='font-weight: bold;'>Hi <span style='font-style: italic'>Robert,</span></span><div><span style='font-weight: bold;'><span style='font-style: italic;'><br></span></span></div><div><span style='font-weight: bold;'><span style='font-style: italic;'>How are You ?&nbsp;</span></span></div><div><span style='font-weight: bold;'><span style='font-style: italic;'><br></span></span></div><div><span style='font-weight: bold;'><span style='font-style: italic;'>I am fine</span></span></div>"
				+ "<footer>"
				+ "<center>"
				+ "<h4>&copy; 2016 PT. Neotural Era Graha</h4>"
				+ "</center>"
				+ "</footer>" 
				+ "</body></html>";
		
		Session session = Session.getInstance(prop,  
				   new javax.mail.Authenticator() {  
				   protected PasswordAuthentication getPasswordAuthentication() {  
				   return new PasswordAuthentication("nrgsolutions.india@gmail.com","Nrg@1234");  
				   }  
				  });  
		 //compose message  
		  try {  
		   MimeMessage message = new MimeMessage(session);  
		   message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));//change accordingly  
		   message.addRecipient(Message.RecipientType.TO,new InternetAddress(to)); 
		   message.addRecipients(Message.RecipientType.CC, myCcList);
		   message.setSubject("Check");  
		  
		
		    BodyPart messageBodyPart1 = new MimeBodyPart();  
		    messageBodyPart1.setContent(body1, "text/html");
		    
	/*	    BodyPart messageBodyPart3 = new MimeBodyPart();  
		    messageBodyPart3.setContent("<h1>HI David And Nivesh", "text/html"); 
		      
		    //4) create new MimeBodyPart object and set DataHandler object to this object      
		    MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
		  
		    String filename = "C://Testing/Test1/30-01-2016/PDF_Java4s.pdf";//change accordingly  
		    DataSource source = new FileDataSource(filename);  
		    messageBodyPart2.setDataHandler(new DataHandler(source));  
		    messageBodyPart2.setFileName(filename);  
		     
		     
		    //5) create Multipart object and add MimeBodyPart objects to this object      
		    Multipart multipart = new MimeMultipart();  
		    multipart.addBodyPart(messageBodyPart1);  
		    multipart.addBodyPart(messageBodyPart2);  
		    multipart.addBodyPart(messageBodyPart3);  
		    //6) set the multiplart object to the message object  
		     *   Multipart multipart = new MimeMultipart();  
		    multipart.addBodyPart(messageBodyPart1);  
*/		  Multipart multipart = new MimeMultipart();  
		   multipart.addBodyPart(messageBodyPart1);  
		    message.setContent(multipart );  
		     
		   Transport.send(message);  
		  
		   logger.debug("message sent successfully");  
		   
		  } catch (MessagingException e) {throw new RuntimeException(e);}  
		   
		 }  
}
