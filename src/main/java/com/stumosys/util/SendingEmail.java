package com.stumosys.util;

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

import com.stumosys.domain.NoticeBoardDataBean;
import com.stumosys.managedBean.NoticeBoardMB;

public class SendingEmail {
	
	private static Logger logger = Logger.getLogger(SendingEmail.class);
	private static final String MAIL_HOST="smtp.gmail.com";
	private static final String MAIL_PORT="465";
	private static final String MAIL_CLASS="javax.net.ssl.SSLSocketFactory";
	private static final String MAIL_AUTH="true";
	private static final String MAIL_SMTP_PORT="587";
	private static final String MAIL_STARTLES="true";
	private static final String MAIL_TRUST="smtp.gmail.com";

	public static String sendNoticeEmail(NoticeBoardDataBean noticeBoardDataBean) {
		StringBuilder status = new StringBuilder("Fail");

		try {
			InternetAddress[] toAddress = new InternetAddress[noticeBoardDataBean.getEmailList().size()];
			for (int i = 0; i < noticeBoardDataBean.getEmailList().size(); i++) {
				toAddress[i] = new InternetAddress(noticeBoardDataBean.getEmailList().get(i));
				logger.debug("mail id -- " + noticeBoardDataBean.getEmailList().get(i));
			}
			Properties prop = new Properties();
			prop.put("mail.smtp.host", MAIL_HOST);
			prop.put("mail.smtp.socketFactory.port", MAIL_PORT);
			prop.put("mail.smtp.socketFactory.class", MAIL_CLASS);
			prop.put("mail.smtp.starttls.enable", MAIL_STARTLES);
			prop.put("mail.smtp.auth", MAIL_AUTH);
			prop.put("mail.smtp.port", MAIL_SMTP_PORT);
			prop.put("mail.smtp.ssl.trust", MAIL_TRUST);

			String body1 = "<htm><head></heade><body>"
					+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
					+ "<h1> Student Monitoring System Notice Board</h1><br></br></center>" + "</center>" + "</header>"
					+ "<h3>Dear " + noticeBoardDataBean.getNoticeFollower() + ",</h3>" 
					+noticeBoardDataBean.getNoticeID()
					+"<footer>" + "<center>"
					+ "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>" + "</body></html>";

			Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
				}
			});
			// compose message
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
				message.setRecipients(Message.RecipientType.TO, toAddress);
				/*
				 * message.addRecipients(Message.RecipientType.CC, myCcList);
				 */ message.setSubject(noticeBoardDataBean.getNoticeSubject());
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);

				Transport.send(message);

				logger.debug("message sent successfully");
				status = new StringBuilder("Success");

			} catch (MessagingException e) {
				logger.warn("Inside Exception",e);
				throw new RuntimeException(e);
			}

		} catch (AddressException e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
		return status.toString();
	}
}
