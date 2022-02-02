package com.company.learningplatform.service;

import com.company.learningplatform.exception.RoleNotFoundException;

public interface RoleService
{
	public boolean deleteRole(String roleName) throws RoleNotFoundException;
}
