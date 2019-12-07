package com.utils;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @Description: 发送邮件工具类,关于type 1是验证码类型,2是邀请码类型
 * @Author 唐世杰
 * @Date： 2019/11/28
 */
public class EmailUtils {
	public static boolean sendMail(String emailTo,int type,String content) {
		 Properties p = new Properties();  
		 //smtp服务器信息
	     p.put("mail.smtp.host", "smtp.qq.com");
	     p.put("mail.smtp.auth", "true");  
	     p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  //使用JSSE的SSL socketfactory来取代默认的socketfactory
	     p.put("mail.smtp.socketFactory.fallback", "false");  // 只处理SSL的连接,对于非SSL的连接不做处理
	     p.put("mail.smtp.port", "465");  
	     p.put("mail.smtp.socketFactory.port", "465");
	     //设置发送邮件的账号和密码
	     Session session = Session.getDefaultInstance(p, new Authenticator() {
	         @Override
	         protected PasswordAuthentication getPasswordAuthentication() {
	         //两个参数分别是发送邮件的账户和密码
	         return new PasswordAuthentication("1399314573@qq.com","wpuciinimxjbjjbd");//密码是授权码
	                 }
	      });  
	     //将传入的验证码
//	     String identifyCode = IdentifyCodeUtils.getCode();
//	     MailboxVerification mailboxVerification = new MailboxVerification(emailTo, type, identifyCode);
	     //创建邮件对象
	     Message mailMessage = new MimeMessage(session);  
	     try {  
//	         System.out.println("I'm sending...");
	         
	         Address from = new InternetAddress("1399314573@qq.com");
	         //设置发出方  
	         mailMessage.setFrom(from);  
	         Address to = new InternetAddress(emailTo);
	         //设置接收人员  
	         mailMessage.setRecipient(Message.RecipientType.TO, to); 
//	         System.out.println(emailTo);
	         mailMessage.setSubject("生源分析团队");//设置邮件标题

	         if(type==1)//如果是验证码
//		        mailMessage.setContent("您的验证码是"+content+"，请确认是本人操作","text/html;charset=utf-8"); //设置邮件内容
		         mailMessage.setContent("<!DOCTYPE HTML>\n" +
				         "<html lang=\"en\">" +
				         "<head>\n" +
				         "<title>生源分析团队</title>\n" +
				         "<meta charset=\"utf-8\">\n" +
				         "<link href=\"//fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&amp;subset=devanagari,latin-ext\" rel=\"stylesheet\">\n" +
				         "</head>\n" +
				         "<body>\n"+
				         "您的验证码是"+content+"，请确认是本人操作" +
				         "</body>\n" +
				         "</html>","text/html;charset=utf-8");
	         if(type==2)//邀请码类型
//	         	mailMessage.setContent("您好,您的邀请码是"+content+"，欢迎使用生源系统","text/html;charset=utf-8"); //设置邮件内容
		         mailMessage.setContent("<!DOCTYPE HTML>" +
				         "<html lang=en>" +
				         "<head>" +
				         "<title>生源分析团队</title>" +
				         "<meta charset=utf-8>" +
				         "<link href=//fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&amp;subset=devanagari,latin-ext rel=stylesheet>" +
				         "</head>" +
				         "<body>"+
				         "<h2>您的验证码是"+content+"，请确认是本人操作</h2>" +
				         "</body>" +
				         "</html>","text/html;charset=utf-8");
	         // 发送邮件
	         Transport.send(mailMessage);  
	         return true;  
	     } catch (Exception e) {  
	         e.printStackTrace();  
	     }  
	     	return false;  
	 }  
	public static void main(String[] args) {
//		sendMail("oliveryx@163.com","yuxiytx912","5374664@qq.com",1,"abcd");
		sendMail("1083979040@qq.com",1,"abcd222");
	}
}
