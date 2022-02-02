package com.company.learningplatform.constant;

import static com.company.learningplatform.constant.AuthorityConstant.*;

import org.apache.commons.lang3.ArrayUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleEnum
{
	STUDENT(new String[] { "student:1", "student:2" }),

	PROFFESOR(new String[] { "proffesor:1", "proffesor:2", "proffesor:3", "proffesor:4" }),

	ADMIN(new String[] { STUDENT_CREATE, PROFESSOR_CREATE, "admin:3" }),

	ROOT_ADMIN(
			ArrayUtils.addAll(new String[] { ADMIN_CREATE, "root_admin:2" },
					RoleEnum.ADMIN.getAuthorities()));

	private String[] authorities;
}
