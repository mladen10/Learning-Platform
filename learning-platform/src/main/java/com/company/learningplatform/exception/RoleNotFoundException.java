package com.company.learningplatform.exception;

import org.springframework.security.core.AuthenticationException;

public class RoleNotFoundException extends AuthenticationException
{

	private static final long serialVersionUID = 8458228295667692649L;

	public RoleNotFoundException(String msg)
	{
		super(msg);
	}

}
