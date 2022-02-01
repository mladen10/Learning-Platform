package com.company.learningplatform.constant;

import org.apache.commons.lang3.ArrayUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleEnum
{
	STUDENT(new String[] { "student:1", "student:2" }),

	PROFFESOR(new String[] { "proffesor:1", "proffesor:2", "proffesor:3", "proffesor:4" }),

	ADMIN(new String[] { "admin:1", "admin:2", "admin:3" }),

	ROOT_ADMIN(
			ArrayUtils.addAll(new String[] { "root_admin:1 ", "root_admin:2" }, RoleEnum.ADMIN.getAuthorities()));

	private String[] authorities;
}
