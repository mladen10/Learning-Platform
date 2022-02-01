package com.company.learningplatform.bootstrap;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.company.learningplatform.constant.RoleEnum;
import com.company.learningplatform.io.model.AuthorityEntity;
import com.company.learningplatform.io.model.RoleEntity;
import com.company.learningplatform.io.repository.AuthorityRepository;
import com.company.learningplatform.io.repository.RoleRepository;
import com.company.learningplatform.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class DefaultAppData implements CommandLineRunner
{

	private final UserService userService;
	private final RoleRepository roleRepository;
	private final AuthorityRepository authorityRepository;

	@Override
	public void run(String... args) throws Exception
	{
		System.out.println("\n---------------------BOOTSTRAP---------------------\n");

		defaultRoles2();

		System.out.println("\n---------------------BOOTSTRAP END---------------------\n");
	}

//	private void defaultRoles()
//	{
//		System.out.println("\n---------------------DEFAULT ROLES---------------------\n");
//
//		Arrays.asList(RoleEnum.values()).stream()
//				.map(new Function<RoleEnum, RoleEntity>()
//				{
//					@Override
//					public RoleEntity apply(RoleEnum t)
//					{
//						return RoleEntity.builder()
//								.name(t.name())
//								.authorities(Arrays.asList(t.getAuthorities()).stream()
//										.map(a -> AuthorityEntity.builder()
//												.permision(a)
//												.build())
//										.collect(Collectors.toSet()))
//								.build();
//					}
//				})
//				.forEach(role -> roleRepository.save(role));
//
//		System.out.println("\n---------------------DEFAULT ROLES END---------------------\n");
//
//	}

	private void defaultRoles2()
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
								.authorities(new HashSet<>())
								.build();

						Arrays.asList(t.getAuthorities()).stream()
								.forEach(permision -> {
									authorityRepository.findByPermision(permision).ifPresentOrElse(
											value -> {
												value.getRoles().add(role);
												// role.getAuthorities().add(value);
												if (!roleRepository.existsByName(role.getName()))
													authorityRepository.save(value);
											},
											() -> role.getAuthorities()
													.add(AuthorityEntity.builder().permision(permision).build()));
								});

						return role;
					}

				})
				.forEach(role -> {
					if (!roleRepository.existsByName(role.getName()))
						roleRepository.save(role);
				});

		System.out.println("\n---------------------DEFAULT ROLES END---------------------\n");

	}

}
