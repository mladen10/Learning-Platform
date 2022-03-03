package com.company.learningplatform.bootstrap;

import static com.company.learningplatform.constant.AuthorityConstant.ADMIN_CREATE;
import static com.company.learningplatform.constant.AuthorityConstant.PROFESSOR_CREATE;
import static com.company.learningplatform.constant.AuthorityConstant.STUDENT_CREATE;
import static java.util.Map.entry;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.company.learningplatform.constant.RoleEnum;
import com.company.learningplatform.io.model.AuthorityEntity;
import com.company.learningplatform.io.model.RoleEntity;
import com.company.learningplatform.io.model.RootAdminInformationEntity;
import com.company.learningplatform.io.model.UserEntity;
import com.company.learningplatform.io.repository.AuthorityRepository;
import com.company.learningplatform.io.repository.UserRepository;
import com.company.learningplatform.service.RoleService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class DefaultAppData implements CommandLineRunner
{
	private RoleService roleService;
	private AuthorityRepository authorityRepository;
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception
	{
		System.out.println("\n---------------------BOOTSTRAP---------------------\n");

		createDefaultPermisions();

		createRootAdmin();

		System.out.println("\n---------------------BOOTSTRAP END---------------------\n");
	}

	private void createRootAdmin()
	{
		UserEntity rootAdmin = new UserEntity();
		rootAdmin.setFirstName("RA_Name");
		rootAdmin.setLastName("RA_LastName");
		rootAdmin.setPassword(passwordEncoder.encode("1111"));
		rootAdmin.setEmail("rootadmin@email.com");
		rootAdmin.setEnabled(true);
		rootAdmin.setUserInformation(new RootAdminInformationEntity());
		rootAdmin.getUserInformation().setUser(rootAdmin);
		rootAdmin.getRoles().add(roleService.findByName(RoleEnum.ROOT_ADMIN.name(), RoleEntity.class));
		userRepository.save(rootAdmin);
	}

	private void createDefaultPermisions()
	{
		Set<RoleEntity> setOfRoles = mapOfPermisions().entrySet().stream()
				.map(new Function<Map.Entry<String, Set<String>>, RoleEntity>()
				{
					@Override
					public RoleEntity apply(Map.Entry<String, Set<String>> entry)
					{
						RoleEntity role = RoleEntity.builder()
								.name(entry.getKey())
								.build();

						entry.getValue().stream()
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
				}).collect(Collectors.toSet());

		roleService.createRoles(setOfRoles);
	}

	private Map<String, Set<String>> mapOfPermisions()
	{
		// ===============================
		// PERMISIONS SPECIFIC FOR ROLE
		// ===============================
		Set<String> ONLY_STUDENT_AUTHORITIES = Set.of("student:1", "student:2");
		Set<String> ONLY_PROFFESOR_AUTHORITIES = Set.of("proffesor:1", "proffesor:2", "proffesor:3", "proffesor:4");
		Set<String> ONLY_ADMIN_AUTHORITIES = Set.of(STUDENT_CREATE, PROFESSOR_CREATE, "admin:3");
		Set<String> ONLY_ROOT_ADMIN_AUTHORITIES = Set.of(ADMIN_CREATE, "root_admin:2");

		// ===============================
		// COMBINED PERMISIONS FOR ROLE
		// ===============================
		Set<String> STUDENT_AUTHORITIES = ONLY_STUDENT_AUTHORITIES;

		Set<String> PROFFESOR_AUTHORITIES = Stream.of(
				STUDENT_AUTHORITIES, ONLY_PROFFESOR_AUTHORITIES)
				.flatMap(Set::stream)
				.collect(Collectors.toSet());

		Set<String> ADMIN_AUTHORITIES = Stream.of(
				PROFFESOR_AUTHORITIES, ONLY_ADMIN_AUTHORITIES)
				.flatMap(Set::stream)
				.collect(Collectors.toSet());

		Set<String> ROOT_ADMIN_AUTHORITIES = Stream.of(
				ADMIN_AUTHORITIES, ONLY_ROOT_ADMIN_AUTHORITIES)
				.flatMap(Set::stream)
				.collect(Collectors.toSet());

		// ===============================
		// MAP ROLE ---> AUTHORITY
		// ===============================
		return Map.ofEntries(
				entry(RoleEnum.STUDENT.name(), STUDENT_AUTHORITIES),
				entry(RoleEnum.PROFFESOR.name(), PROFFESOR_AUTHORITIES),
				entry(RoleEnum.ADMIN.name(), ADMIN_AUTHORITIES),
				entry(RoleEnum.ROOT_ADMIN.name(), ROOT_ADMIN_AUTHORITIES));
	}
}
