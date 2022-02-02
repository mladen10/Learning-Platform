package com.company.learningplatform.bootstrap;

import java.util.Arrays;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.company.learningplatform.constant.RoleEnum;
import com.company.learningplatform.io.model.AuthorityEntity;
import com.company.learningplatform.io.model.RoleEntity;
import com.company.learningplatform.io.repository.AuthorityRepository;
import com.company.learningplatform.io.repository.RoleRepository;
import com.company.learningplatform.service.impl.RoleServiceImpl;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class DefaultAppData implements CommandLineRunner
{
	private final RoleRepository roleRepository;
	private final AuthorityRepository authorityRepository;

	@Override
	public void run(String... args) throws Exception
	{
		System.out.println("\n---------------------BOOTSTRAP---------------------\n");

		defaultRoles();

		System.out.println("\n---------------------BOOTSTRAP END---------------------\n");
	}

	// @Transactional
	private void defaultRoles()
	{
		System.out.println("\n---------------------DEFAULT ROLES---------------------\n");

		Arrays.asList(RoleEnum.values()).stream()
				.map(new Function<RoleEnum, RoleEntity>()
				{
					@Override
					public RoleEntity apply(RoleEnum t)
					{
						RoleEntity role = RoleEntity.builder()
								.name(t.name())
								.build();

						Arrays.asList(t.getAuthorities()).stream()
								.forEach(permision -> {
									authorityRepository.findByPermision(permision).ifPresentOrElse(
											value -> {
												role.getAuthorities().add(value);
											},
											() -> {
												AuthorityEntity authority = AuthorityEntity.builder()
														.permision(permision)
														.build();
												authority = authorityRepository.save(authority);
												role.getAuthorities().add(authority);
											});
								});
						return role;
					}
				})
				.forEach(role -> roleRepository.save(role));

		System.out.println("\n---------------------DEFAULT ROLES END---------------------\n");

	}
}
