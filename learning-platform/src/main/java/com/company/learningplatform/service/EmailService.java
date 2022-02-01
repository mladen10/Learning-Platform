package com.company.learningplatform.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService
{
	private static final String FROM = "from@email.com";

	private final JavaMailSender jms;

	public void sendEmail(String to, String subject, String content)
	{
		jms.send(createEmail(to, subject, content));
		log.info("send email to {}", to);
	}

	private SimpleMailMessage createEmail(String to, String subject, String content)
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(FROM);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);

		return message;
	}
}
