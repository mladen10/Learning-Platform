package com.company.learningplatform.event;

import org.springframework.context.ApplicationEvent;

public class OnChangeAuthorities extends ApplicationEvent
{
	private static final long serialVersionUID = -3093520078891368386L;

	public OnChangeAuthorities(Object source)
	{
		super(source);
	}
}
