package com.company.learningplatform.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import com.company.learningplatform.constant.RoleEnum;
import com.company.learningplatform.exception.RoleNotFoundException;
import com.company.learningplatform.exception.UserAlreadyExistsException;
import com.company.learningplatform.shared.dto.UserDto;
import com.company.learningplatform.ui.model.request.CreateUserRequestModel;

public interface UserService extends UserDetailsService
{
	@Transactional
	void createUser(UserDto userDto) throws UserAlreadyExistsException;

	@Transactional
	UserDto createUser(CreateUserRequestModel createUserReqModel, RoleEnum role)
			throws UserAlreadyExistsException, RoleNotFoundException;

	UserDto getUser(String email);

	void updateUser(UserDto userDto);

	boolean deleteUserByUsername(String email);

	List<UserDto> getUsers(int page, int limit);

	boolean changePassword(String email, String oldPassword, String newPassword);

	void firstLogin(String email, String newPassword);

}
