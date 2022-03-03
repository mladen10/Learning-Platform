package com.company.learningplatform.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.company.learningplatform.constant.RoleEnum;
import com.company.learningplatform.exception.UserAlreadyExistsException;
import com.company.learningplatform.io.model.RoleEntity;
import com.company.learningplatform.io.model.UserEntity;
import com.company.learningplatform.io.model.UserInformationEntity;
import com.company.learningplatform.io.repository.RoleRepository;
import com.company.learningplatform.io.repository.UserRepository;
import com.company.learningplatform.service.RoleService;
import com.company.learningplatform.shared.dto.UserDto;
import com.company.learningplatform.ui.model.request.CreateUserRequestModel;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest
{

	@Mock
	UserRepository userRepo;

	@Mock
	RoleRepository roleRepo;

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	ModelMapper modelMapper;

	@Mock
	RoleService roleService;

	@InjectMocks
	UserServiceImpl userService;

	UserEntity user;

	@BeforeEach
	void setUp() throws Exception
	{
		user = new UserEntity();
		user.setFirstName("asdfsdfs");
	}

	@Test
	@Disabled
	void testLoadUserByUsername()
	{

	}

	@DisplayName("Create User Test")
	@ParameterizedTest(name = " {displayName} --> [{index}] {arguments}")
	@EnumSource(RoleEnum.class)
	void testCreateUser(RoleEnum roleEnum)
	{
		CreateUserRequestModel createUser = new CreateUserRequestModel();
		UserDto userDto = new UserDto();
		UserEntity userEntity = new UserEntity();
		RoleEntity roleEntity = RoleEntity.builder().name(roleEnum.name()).build();

		given(userRepo.findByEmail(anyString())).willReturn(Optional.empty());
		given(roleService.findByName(roleEnum.name(), RoleEntity.class)).willReturn(roleEntity);
		given(modelMapper.map(createUser, UserEntity.class)).willReturn(userEntity);
		given(bCryptPasswordEncoder.encode(anyString())).willReturn(RandomStringUtils.randomAlphanumeric(10));
		given(userRepo.save(userEntity)).willReturn(userEntity);
		given(modelMapper.map(userEntity, UserDto.class)).willReturn(userDto);

		UserDto returnValue = userService.createUser(createUser, roleEnum);

		assertAll("properties",
				() -> {
					assertNotNull(userEntity.getUserInformation());
					UserInformationEntity userInformation = userEntity.getUserInformation();

					// Executed only if the previous assertion is valid.
					assertAll("user information",
							() -> assertTrue(userInformation.getUser() == userEntity));
				},
				() -> {
					// Grouped assertion, so processed independently
					// of results of first name assertions.
					assertEquals(userEntity.getRoles().size(), 2, "The object you enter return null");
					RoleEntity userRole = userEntity.getRoles().iterator().next();

					// Executed only if the previous assertion is valid.
					assertAll("user role",
							() -> assertEquals(userRole.getName(),
									roleEntity.getName(), "The object you enter return null"));
				},
				() -> assertNotNull("The object you enter return null", returnValue));

	}

//	@Test(expected = NullPointerException.class)
//	public void whenConfigNonVoidRetunMethodToThrowEx_thenExIsThrown() {
//	    MyDictionary dictMock = mock(MyDictionary.class);
//	    when(dictMock.getMeaning(anyString()))
//	      .thenThrow(NullPointerException.class);
//
//	    dictMock.getMeaning("word");
//	}

	@Test()
	@Disabled
	void testCreateUser()
	{
		given(userRepo.findByEmail(anyString())).willThrow(UserAlreadyExistsException.class);
		userService.createUser(new CreateUserRequestModel(), RoleEnum.ADMIN);
		assertThrows(UserAlreadyExistsException.class,
				() -> userService.createUser(new CreateUserRequestModel(), RoleEnum.ADMIN));

	}

	@Test
	@Disabled
	void testGetUser()
	{
		fail("Not yet implemented");
	}
//
//	@Test
//	void testUpdateUser()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDeleteUserByUsername()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetUsers()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testChangePassword()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFirstLogin()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testUserServiceImpl()
//	{
//		fail("Not yet implemented");
//	}

}
