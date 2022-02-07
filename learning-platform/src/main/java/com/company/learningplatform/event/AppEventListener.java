package com.company.learningplatform.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.company.learningplatform.security.PermisionHolder;
import com.company.learningplatform.security.token.JWTProvider;
import com.company.learningplatform.service.EmailService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class AppEventListener
{
	EmailService emailService;
	JWTProvider tokenProvider;
	PermisionHolder roleAuthorityHolder;

	@EventListener({ OnCreateUserEvent.class })
	public void handleOnCreateUserEvent(OnCreateUserEvent e)
	{
		log.info("HANDLE ON CREATE USER EVENT");
		String token = tokenProvider.tokenForEmailConfirm(e.getEmail());
		log.info("Sending email to {} /n with token: {}", e.getEmail(), token);
		// emailService.sendEmail(e.getEmail(), "Subject", token);
	}

	@EventListener({ OnChangeAuthorities.class })
	public void handleOnChangeAuthorities()
	{
		log.info("HANDLE ON CHANGE AUTHORITIES");
		roleAuthorityHolder.getRoleAuthoritiesMap();
	}
}
