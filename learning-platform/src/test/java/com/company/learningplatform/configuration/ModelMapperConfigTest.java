package com.company.learningplatform.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import com.company.learningplatform.io.model.RoleEntity;
import com.company.learningplatform.io.model.UserEntity;
import com.company.learningplatform.shared.dto.UserDto;

class ModelMapperConfigTest
{
	ModelMapper modelMapper = new ModelMapperConfig().modelMapper();

	@Test
	void test()
	{

		UserDto userDto = UserDto.builder()
				.email("email@email.com")
				.username("username")
				.firstName("firstName")
				.lastName("lastName")
				.accountNonExpired(false)
				.accountNonLocked(false)
				.credentialsNonExpired(false)
				.enabled(true)
				.build();

		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

		assertEquals(userDto.getEmail(), userEntity.getEmail());
		assertEquals(userDto.getFirstName(), userEntity.getFirstName());
		assertEquals(userDto.getLastName(), userEntity.getLastName());

		String[] emailParts = userDto.getEmail().split("@");
		String username = emailParts[0];

		assertEquals(username, userEntity.getUsername());
		assertTrue(userEntity.getAccountNonExpired() && userEntity.getAccountNonLocked()
				&& userEntity.getCredentialsNonExpired() && !userEntity.getEnabled());

	}

	@Test
	void test2()
	{

		Set<RoleEntity> setOfRoleEntities = new HashSet<>();
		Set<RoleEntity> setOfRoleStrings = new HashSet<>();

	}

}
