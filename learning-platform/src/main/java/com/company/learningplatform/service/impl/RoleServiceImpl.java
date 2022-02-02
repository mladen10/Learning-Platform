package com.company.learningplatform.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.company.learningplatform.constant.MessageEnum;
import com.company.learningplatform.exception.RoleNotFoundException;
import com.company.learningplatform.io.model.RoleEntity;
import com.company.learningplatform.io.repository.AuthorityRepository;
import com.company.learningplatform.io.repository.RoleRepository;
import com.company.learningplatform.service.RoleService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService
{
	private final AuthorityRepository authorityRepo;
	private final RoleRepository roleRepo;

	@Transactional
	@Override
	public boolean deleteRole(String roleName) throws RoleNotFoundException
	{
		RoleEntity role = roleRepo.findByNamesWihtAuthoritiesNRoles(roleName).orElseThrow(() -> {
			throw new RoleNotFoundException(MessageEnum.ROLE_NOT_FOUND_EXCEPTION.getMessage());
		});

		role.getAuthorities().stream()
				.forEach(authority -> {
					authority.getRoles().remove(role);
					if (authority.getRoles().isEmpty()) {
						authorityRepo.delete(authority);
					} else {
						authorityRepo.save(authority);
					}

				});
		roleRepo.delete(role);
		return true;
	}
}
