package com.example.demo.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.example.demo.vo.UserVO;

public class EmailSender {
	
	public static void sendEmail(UserVO userVO, String authKey) {
		String host = "smtp.naver.com";
		String fromEmail = "";
		String password = "";
		String mailTitle = "Mail Test";
		String html = "<h1>[회원가입 인증]</h1>"
				+ "<p>아래 링크를 클릭하시면 회원가입 인증이 완료됩니다.</p>"
				+ "<a href='http://localhost:8080/naversw/registerConfirm?email="
				//+ userVO.getUserNo()
				//+ "&email="
				+ userVO.getEmail()
				+ "&authKey="
				+ authKey
				+ "' target='_blenk'>이메일 인증 확인</a>";
		
		//SMTP 서버 정보 설정
		Properties prop = new Properties();
		prop.put("mail.smtp.host", host);
		prop.put("mail.smtp.port", 587);
		prop.put("mail.smtp.auth", "true");
		
		Session session = Session.getInstance(prop, new javax.mail.Authenticator() { 
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		});
		
		
		try { 
			MimeMessage message = new MimeMessage(session); 
			message.setFrom(new InternetAddress(fromEmail));
			
			InternetAddress[] toUserEmail = new InternetAddress[1];
			toUserEmail[0] = new InternetAddress("");
			//toUserEmail[1] = new InternetAddress("");
			
			message.addRecipients(Message.RecipientType.TO, toUserEmail);
			//message.addRecipients(Message.RecipientType.TO, userVO.getEmail());
			
			// 메일 제목 
			message.setSubject(mailTitle);
			
			// 메일 내용 
			message.setContent(html, "text/html; charset=utf-8");
			
			// 메일 보내기 
			Transport.send(message);
			System.out.println("Success sendEmail"); 
			
			} catch (Exception e) {
			  e.printStackTrace();
		}
	}
}
