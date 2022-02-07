package com.company.learningplatform.service;

import java.util.Set;

import com.company.learningplatform.exception.RoleNotFoundException;
import com.company.learningplatform.io.model.RoleEntity;

public interface RoleService
{
	public boolean deleteRole(String roleName) throws RoleNotFoundException;

	<T> T findByName(String roleName, Class<T> clazz) throws RoleNotFoundException;

	void createRoles(Set<RoleEntity> roles);

	<T> T findByNameWihtAuthorities(String roleName, Class<T> clazz) throws RoleNotFoundException;
}
