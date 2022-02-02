package com.company.learningplatform.constant;

import org.springframework.stereotype.Component;

@Component("auth")
public class AuthorityConstant
{
	public static final String STUDENT_CREATE = "student-create";
	public static final String PROFESSOR_CREATE = "professor-create";
	public static final String ADMIN_CREATE = "admin-create";
	public static final String USER_UPDATE = "user-update";
	public static final String USER_DELETE = "user-delete";

}
