package com.company.learningplatform.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import com.company.learningplatform.exception.UserAlreadyExistsException;
import com.company.learningplatform.shared.dto.RoleDto;
import com.company.learningplatform.shared.dto.UserDto;

public interface UserService extends UserDetailsService
{
	@Transactional
	void createUser(UserDto userDto) throws UserAlreadyExistsException;

	@Transactional
	<T> void createUser(UserDto userDto, RoleDto roleDto, T userInformationDto) throws UserAlreadyExistsException;

	UserDto getUser(String email);

	void updateUser(UserDto userDto);

	boolean deleteUserByUsername(String email);

	List<UserDto> getUsers(int page, int limit);

	boolean changePassword(String email, String oldPassword, String newPassword);

	void firstLogin(String email, String newPassword);

}
