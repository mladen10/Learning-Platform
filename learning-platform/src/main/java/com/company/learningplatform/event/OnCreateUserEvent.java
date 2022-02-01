package com.company.learningplatform.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class OnCreateUserEvent extends ApplicationEvent
{
	private static final long serialVersionUID = 4624621206286702638L;
	private String email;

	public OnCreateUserEvent(Object source, String email)
	{
		super(source);
		this.email = email;
	}
}
