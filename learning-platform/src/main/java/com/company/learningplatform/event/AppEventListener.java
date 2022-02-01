package com.company.learningplatform.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.company.learningplatform.security.token.JWTProvider;
import com.company.learningplatform.service.EmailService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AppEventListener
{
	EmailService emailService;
	JWTProvider tokenProvider;

	@EventListener({ OnCreateUserEvent.class })
	public void handleOnCreateUserEvent(OnCreateUserEvent e)
	{

		String token = tokenProvider.tokenForEmailConfirm(e.getEmail());
		System.out.println("Sending email to" + e.getEmail() + "/n with token: " + token);
		// emailService.sendEmail(e.getEmail(), "Subject", token);
	}

}
