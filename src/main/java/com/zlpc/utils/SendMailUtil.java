package com.zlpc.utils;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * 发送信息到指定邮箱
 * 
 * @author shy
 */
public class SendMailUtil {

	// 1 发件人的 邮箱 和 密码(客户端的授权码)
	private static final String MY_EMAIL_ACCOUNT = "shenghoulei@163.com";
	private static final String MY_EMAIL_PASSWORD = "*****";

	// 2 发件人邮箱的 SMTP 服务器地址
	private static final String MY_EMAIL_SMTP_HOST = "smtp.163.com";

	/**
	 * 3 发送邮件
	 * 
	 * @param receiveAccount
	 *            接受人的邮箱
	 * @param subject
	 *            邮件的主题
	 * @param msg
	 *            发送的消息
	 * @throws Exception
	 *             抛出异常
	 */
	public static void send(String receiveAccount, String subject, String msg) throws Exception {
		// 1. 创建参数配置, 用于连接邮件服务器的参数配置
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", MY_EMAIL_SMTP_HOST);
		props.setProperty("mail.smtp.auth", "true");

		// 2. 根据配置创建会话对象, 用于和邮件服务器交互
		Session session = Session.getInstance(props);
		session.setDebug(true);

		// 3. 创建一封邮件
		MimeMessage message = createMimeMessage(session, MY_EMAIL_ACCOUNT, receiveAccount, subject, msg);

		// 4. 根据 Session 获取邮件传输对象
		Transport transport = session.getTransport();
		transport.connect(MY_EMAIL_ACCOUNT, MY_EMAIL_PASSWORD);

		// 5. 发送邮件
		transport.sendMessage(message, message.getAllRecipients());

		// 6. 关闭连接
		transport.close();
	}

	/**
	 * 4 创建一封只包含文本的简单邮件
	 * 
	 * @param session
	 *            和服务器交互的会话
	 * @param sendMail
	 *            发件人邮箱
	 * @param receiveMail
	 *            收件人邮箱
	 * @return 返回邮件对象
	 * @throws Exception
	 *             向上抛出异常信息
	 */
	private static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, String subject,
			String msg) throws Exception {
		// 1. 创建一封邮件
		MimeMessage message = new MimeMessage(session);

		// 2. From: 发件人
		message.setFrom(new InternetAddress(sendMail, "shy", "UTF-8"));

		// 3. To: 收件人（可以增加多个收件人、抄送、密送）
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "erro", "UTF-8"));

		// 4. Subject: 邮件主题
		message.setSubject(subject, "UTF-8");

		// 5. Content: 邮件正文
		message.setContent(msg, "text/html;charset=UTF-8");

		// 6. 设置发件时间
		message.setSentDate(new Date());

		// 7. 保存设置
		message.saveChanges();

		return message;
	}

}