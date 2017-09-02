package utils;

import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class MailUtils extends Thread {

	private static String from = "woyumen4597@163.com";
	private static String username = "woyumen4597";
	private static String password = "woyumen4597";
	private static String host = "smtp.163.com";
	private static String email; //要发送的email地址
	private static String info;
	private static String subject;
	@Override
	public void run() {
		try {
			Properties props = new Properties();
			props.setProperty("mail.host", host);
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.smtp.auth", "true");
			Session session = Session.getInstance(props);
			Transport ts = session.getTransport();
			ts.connect(host, username, password);
			Message message = makeMessage(session, email,info,subject);
			ts.sendMessage(message, message.getAllRecipients());
			ts.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Message makeMessage(Session session,String email,String info,String subject) throws Exception{
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
		message.setSubject(subject);
		message.setContent(info,"text/html;charset=UTF-8");
		message.saveChanges();
		return message;
	}
	//库存缺货通知
	public static void StockOutInform(String book_name){
		email = "woyumen4597@163.com";
		info = "商品"+book_name+"已缺货,请及时通知管理员补充!";
		subject = "缺货通知";
		new MailUtils().start();
	}
	//注册验证
	public static String RegisterConfirm(String UserEmail){
		email = UserEmail;
		String code = UUID.randomUUID().toString().substring(0, 8);
		info = "您的注册验证码为:"+code;
		subject = "注册校验";
		new MailUtils().start();
		return code;
	}

	public static void main(String[] args) {
		StockOutInform("数据结构");
	}
}
