package com.company.learningplatform.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.learningplatform.io.model.AuthorityEntity;
import com.company.learningplatform.io.model.RoleEntity;
import com.company.learningplatform.io.repository.RoleRepository;

import lombok.Getter;

@Component
@Getter
public class PermisionHolder
{
	@Autowired
	public RoleRepository roleRepository;
	private Map<String, Set<String>> permisionMap = new HashMap<>();

	public void getRoleAuthoritiesMap()
	{
		Set<RoleEntity> roleEntities = roleRepository.findAllWihtAuthorities().get();

		roleEntities
				.stream()
				.forEach(r -> {
					permisionMap.put(r.getName(), r.getAuthorities()
							.stream()
							.map(AuthorityEntity::getPermision)
							.collect(Collectors.toSet()));
				});
		permisionMap.entrySet().stream().forEach(System.out::println);
	}
}
