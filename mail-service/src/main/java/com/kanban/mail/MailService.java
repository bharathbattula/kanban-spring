package com.kanban.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	JavaMailSender mailSender;

	public void sendEmail() {
		final SimpleMailMessage message = new SimpleMailMessage();

		message.setTo("bha8421@gmail.com");
		message.setSubject("Dummy mail");
		message.setText("Hello Bharath, \n This test email. Please ignore");

		this.mailSender.send(message);
	}

}
