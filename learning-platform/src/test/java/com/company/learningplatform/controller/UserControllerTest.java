package com.company.learningplatform.controller;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.company.learningplatform.service.impl.UserServiceImpl;
import com.company.learningplatform.shared.dto.UserDto;
import com.company.learningplatform.ui.api.UserController;

class UserControllerTest
{
	@InjectMocks
	UserController userController;

	@Mock
	UserServiceImpl userService;

	UserDto userDto;

	@BeforeEach
	void setUp() throws Exception
	{
		MockitoAnnotations.openMocks(this);

//		userDto=UserDto.builder()
//				.email("email@email.com")
//				.password("1234")
//				.
//				.build();
	}

	@Test
	void test()
	{
		fail("Not yet implemented");
	}

}
