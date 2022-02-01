package com.company.learningplatform.exception;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExistsException extends AuthenticationException
{

	private static final long serialVersionUID = 2743627355582195971L;

	public UserAlreadyExistsException(String msg)
	{
		super(msg);
	}

}
