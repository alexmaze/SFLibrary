package com.successfactors.library.shared;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.successfactors.library.shared.model.SLBorrow;
import com.successfactors.library.shared.model.SLOrder;
import com.successfactors.library.shared.model.SLRecommendedBook;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class SLEmailUtil {

	private String SMS_EMAIL_GROUP_ADDRESS = "MinervaBookLib@successfactors.com";
	private String SMS_EMAIL_HOST = "10.50.0.11";
	
	private String SMS_ADMIN_EMAIL_ADDRESS_1 = "meganwang@successfactors.com";
	private String SMS_ADMIN_EMAIL_ADDRESS_2 = "amei@successfactors.com";
	private String SMS_ADMIN_EMAIL_ADDRESS_3 = "dwei@successfactors.com";

	/**
	 *  发送邮件
	 */
	public boolean sendEmail(String toEmail, String emailSubject,
			String emailContent) {

		boolean result = false;

		Properties props = System.getProperties();
		props.put("mail.smtp.host", SMS_EMAIL_HOST);
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage message = new MimeMessage(session);

		try {
			
			message.setFrom(new InternetAddress(SMS_EMAIL_GROUP_ADDRESS));
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

	/**
	 * 发送超期邮件
	 * */
	public void sendOverdueEmail(SLBorrow slBorrow) {
		// TODO 美化超期邮件
		
		// 发送给用户
		String toEmail = slBorrow.getUserEmail();
		String strTitle = "[Minerva's Book Lib]借书超期提醒";
		String strContent = "亲爱的"+slBorrow.getTheUser().getUserName()+"，\n\n"
				+ "您于 " + formatDate(slBorrow.getBorrowDate())+ " 借阅的" + " 《" + slBorrow.getTheBook().getBookName() + "》\n"
				+ "已超过借阅期限，请尽快归还！\n\n"
				+ "感谢您的支持！\n"
				+ "Minerva's Book Lib\n";
		
		sendEmail(toEmail, strTitle, strContent);
	}
	
	/**
	 * 发送借阅成功邮件
	 * */
	public void sendBorrowSuccessEmail(SLBorrow slBorrow) {
		// TODO 美化借阅成功邮件

		// 发送给用户
		String toEmail = slBorrow.getUserEmail();
		String strTitle = "[Minerva's Book Lib]借阅《"+ slBorrow.getTheBook().getBookName() +"》成功";
		String strContent = "亲爱的"+slBorrow.getTheUser().getUserName()+"，\n\n"
				+ "您已成功借阅" +" 《"+ slBorrow.getTheBook().getBookName() +"》\n"
				+ "请到图书管理员处领取\n"
				+ "借阅到期时间为："+formatDate(slBorrow.getShouldReturnDate())+", 请按期归还。\n\n"
				+ "感谢您的支持！\n"
				+ "Minerva's Book Lib\n";
		
		sendEmail(toEmail, strTitle, strContent);
		
		// 发送给管理员
		String strTitleToAdmin = "[Minerva's Book Lib]"+slBorrow.getTheUser().getUserName()+"借阅《"+ slBorrow.getTheBook().getBookName() +"》成功";
		String strContentToAdmin = "亲爱的管理员同志，\n\n"
				+ slBorrow.getTheUser().getUserName() + "已成功借阅" +" 《"+ slBorrow.getTheBook().getBookName() +"》\n"
				+ "请准备好此书给他，并登记出库信息\n"
				+ "借阅到期时间为："+formatDate(slBorrow.getShouldReturnDate())+"\n\n"
				+ "感谢您的付出！\n"
				+ "Minerva's Book Lib\n";
		
		sendEmail(SMS_ADMIN_EMAIL_ADDRESS_1, strTitleToAdmin, strContentToAdmin);
		sendEmail(SMS_ADMIN_EMAIL_ADDRESS_2, strTitleToAdmin, strContentToAdmin);
		sendEmail(SMS_ADMIN_EMAIL_ADDRESS_3, strTitleToAdmin, strContentToAdmin);
		
		
	}

	/**
	 * 发送预订成功邮件
	 * */
	public void sendOrderSuccessEmail(SLOrder slOrder) {
		// TODO 美化预订成功邮件

		// 发送给用户
		String toEmail = slOrder.getUserEmail();
		String strTitle = "[Minerva's Book Lib]预订成功提醒";
		String strContent = "亲爱的"+slOrder.getTheUser().getUserName()+"，\n\n"
				+ "您已成功预订" +" 《"+ slOrder.getTheBook().getBookName() +"》\n"
				+ "我们会自动按预订顺序，为您安排借阅，请耐心等待。\n\n"
				+ "感谢您的支持！\n"
				+ "Minerva's Book Lib\n";
		
		sendEmail(toEmail, strTitle, strContent);
	}
	
	/**
	 * 发送购买书单邮件
	 * */
	public void sendBuyListEmail(ArrayList<SLRecommendedBook> buyList) {
		// TODO 美化预订成功邮件

		String buyListString = "";
		double totalPrice = 0.0;
		for (SLRecommendedBook book : buyList) {
			buyListString += " \t "+ book.getBookISBN()+" \t "+ book.getBookName()+" \t "+book.getCountPrice() + "\n";
			totalPrice += book.getCountPrice();
		}
		buyListString += " \t " + "总价："+totalPrice+" 元";
		
		// 发送给管理员
		String strTitleToAdmin = "[Minerva's Book Lib]"+"最新购买书单";
		String strContentToAdmin = "亲爱的管理员同志，\n\n"
				+ "本次选购书单如下，请尽快购进：\n\n"
				+ buyListString +"\n\n"
				+ "感谢您的付出！\n"
				+ "Minerva's Book Lib\n";

		sendEmail(SMS_ADMIN_EMAIL_ADDRESS_1, strTitleToAdmin, strContentToAdmin);
		sendEmail(SMS_ADMIN_EMAIL_ADDRESS_2, strTitleToAdmin, strContentToAdmin);
		sendEmail(SMS_ADMIN_EMAIL_ADDRESS_3, strTitleToAdmin, strContentToAdmin);
	}
	
	/**
	 * 格式化日期，返回 YYYY-MM-DD
	 * */
	@SuppressWarnings("deprecation")
	public String formatDate(Date dt)
	{
		if (dt == null) {
			return "";
		}
		String ret = new String();
		
		ret = String.valueOf(dt.getYear()+1900) + "-"
		+ String.valueOf(dt.getMonth()+1) + "-"
		+ String.valueOf(dt.getDate());
		
		return ret;
	}
}
