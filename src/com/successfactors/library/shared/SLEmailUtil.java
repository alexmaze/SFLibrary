package com.successfactors.library.shared;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SLEmailUtil {

	private String SMS_EMAIL_ADDRESS = "sflibrary@yeah.net";
	private String SMS_EMAIL_HOST = "smtp.yeah.net";
	private String SMS_EMAIL_PASSWORD = "Welcomeay!";

	/**
	 *  发送邮件
	 */
	public boolean sendEmail(String toEmail, String emailSubject,
			String emailContent) {

		boolean result = false;

		Properties props = System.getProperties();
		props.put("mail.smtp.host", SMS_EMAIL_HOST);
		props.put("mail.smtp.auth", "true");
		MyAuthenticator myauth = new MyAuthenticator(SMS_EMAIL_ADDRESS,
				SMS_EMAIL_PASSWORD);
		Session session = Session.getDefaultInstance(props, myauth);
		MimeMessage message = new MimeMessage(session);

		try {
			
			message.setFrom(new InternetAddress(SMS_EMAIL_ADDRESS));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject(emailSubject);
			message.setText(emailContent);
			message.saveChanges();
			Transport.send(message);
			result = true;
			
		} catch (AddressException e1) {
			e1.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return result;
	}

	public class MyAuthenticator extends javax.mail.Authenticator {
		private String strUser;
		private String strPwd;

		public MyAuthenticator(String user, String password) {
			this.strUser = user;
			this.strPwd = password;
		}

		/**
		 * 用于实现邮件发送用户验证
		 * 
		 * @see javax.mail.Authenticator#getPasswordAuthentication
		 */
		protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
			return new javax.mail.PasswordAuthentication(strUser, strPwd);
		}
	}

}
