package com.tyc.bos.utils;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import java.util.Properties;

public class MailUtils {
	private static String smtp_host = "smtp.qq.com";
	private static String username = "tangyucong716@qq.com";
	private static String password = "qdxngbgvekhubeia";

	private static String from = "tangyucong716@qq.com"; // 使用当前账户
	public static String activeUrl = "http://localhost:8890/bos_fore/customerAction_activeMail.action";

	public static void sendMail(String subject, String content, String to) {
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", smtp_host);
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.auth", "true");
		Session session = Session.getInstance(props);
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));
			message.setRecipient(RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setContent(content, "text/html;charset=utf-8");
			Transport transport = session.getTransport();
			transport.connect(smtp_host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("邮件发送失败...");
		}
	}

	public static void main(String[] args) {
		sendMail("测试邮件", "你好，传智播客", "lm012252@163.com");
	}
}
