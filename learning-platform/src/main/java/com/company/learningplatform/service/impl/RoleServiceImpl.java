package com.company.learningplatform.service.impl;

import java.util.Set;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.company.learningplatform.constant.MessageEnum;
import com.company.learningplatform.event.AppEventPublisher;
import com.company.learningplatform.event.OnChangeAuthorities;
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
	private final RoleRepository roleRepository;
	private AppEventPublisher appPublisher;
	private ModelMapper modelMapper;

	@Transactional
	@Override
	public boolean deleteRole(String roleName) throws RoleNotFoundException
	{
		RoleEntity role = roleRepository.findByNameWihtAuthoritiesNRoles(roleName).orElseThrow(() -> {
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
		roleRepository.delete(role);
		return true;
	}

	@Override
	public void createRoles(Set<RoleEntity> roles)
	{
		for (RoleEntity role : roles) {
			roleRepository.save(role);
		}
		appPublisher.publishEvent(new OnChangeAuthorities(this));
	}

	@Override
	public <T> T findByName(String roleName, Class<T> clazz) throws RoleNotFoundException
	{
		RoleEntity roleEntity = roleRepository.findByName(roleName)
				.orElseThrow(() -> {
					throw new RoleNotFoundException(MessageEnum.ROLE_NOT_FOUND_EXCEPTION.getMessage());
				});

		return modelMapper.map(roleEntity, clazz);
	}

	@Override
	public <T> T findByNameWihtAuthorities(String roleName, Class<T> clazz) throws RoleNotFoundException
	{
		RoleEntity roleEntity = roleRepository.findByNameWihtAuthorities(roleName)
				.orElseThrow(() -> {
					throw new RoleNotFoundException(MessageEnum.ROLE_NOT_FOUND_EXCEPTION.getMessage());
				});

		return modelMapper.map(roleEntity, clazz);
	}
}
