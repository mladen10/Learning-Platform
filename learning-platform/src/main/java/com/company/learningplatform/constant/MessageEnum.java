package com.company.learningplatform.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageEnum
{
	SUCCESFFUL_CREATE("User was created successfully"),
	MISSING_REQUIRED_FIELD("Missing requred field"),
	TOKEN_CANNOT_BE_VERIFIED("Token cannot be verified"),
	USERNAME_NOT_FOUND("USERNAME_NOT_FOUND_MSG"),
	USER_ALREADY_EXISTS_EXCEPTION("USER_ALDREADY_EXIST_MSG"),
	ROLE_NOT_FOUND_EXCEPTION("ROLE_NOT_FOUND_EXCEPTION_MSG"),
	SUCCESFFUL_DELETE(" "),
	GRESKADELETE(" ");

//RoleNotFoundException
	private final String message;

}
