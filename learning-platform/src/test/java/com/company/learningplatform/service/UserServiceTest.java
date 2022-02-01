package com.company.learningplatform.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.company.learningplatform.io.model.UserEntity;
import com.company.learningplatform.io.repository.UserRepository;
import com.company.learningplatform.service.impl.UserServiceImpl;

class UserServiceTest
{
	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepository;

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	String encryptedPassword = "sfsdfsfsdf";

	UserEntity userEntity;

	@BeforeEach
	void setUp() throws Exception
	{
		MockitoAnnotations.openMocks(this);

		userEntity = new UserEntity();

	}

	@Test
	void testCreateUser()
	{
//		System.out.println("bla bla");
//		when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(null));
//		// Mockito.doNothing().when(userRepository.findByEmail(anyString()));
//		System.out.println("bla bla");
//		when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
//		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
//
//		UserDto userDto = UserDto.builder().build();
//		userDto.setEmail("email@email.com");
//		userDto.setPassword("1234");
//		userDto.setRoles(new HashSet<>(Arrays.asList(RoleDto.builder().name("ROLE").build())));
//		userDto.setUserInformation(UserInformationDto.builder().name("ime").build());
////		UserDto userDto = UserDto.builder()
////				.email("email@email.com")
////				.password("1234")
////				.roles(new HashSet<>(Arrays.asList(RoleDto.builder().name("ROLE").build())))
////				.userInformation(UserInformationDto.builder().name("ime").build())
////				.build();
//
//		UserDto storedUserDetails = UserDto.builder().build();
//		userService.createUser(userDto);
//		System.out.println("asdfsf");
//		assertNotNull(storedUserDetails);
	}

}
