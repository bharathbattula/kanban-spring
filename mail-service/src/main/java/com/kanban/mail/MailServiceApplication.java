package com.kanban.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MailServiceApplication implements CommandLineRunner {

	@Autowired
	private MailService mailService;

	public static void main(final String[] args) {
		SpringApplication.run(MailServiceApplication.class, args);
	}

	@Override
	public void run(final String... args) throws Exception {
		this.mailService.sendEmail();
	}
}
