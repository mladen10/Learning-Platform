package com.company.learningplatform.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.learningplatform.constant.MessageEnum;
import com.company.learningplatform.constant.UserConstant;
import com.company.learningplatform.exception.RoleNotFoundException;
import com.company.learningplatform.exception.UserAlreadyExistsException;
import com.company.learningplatform.io.model.RoleEntity;
import com.company.learningplatform.io.model.UserEntity;
import com.company.learningplatform.io.model.UserInformationEntity;
import com.company.learningplatform.io.repository.RoleRepository;
import com.company.learningplatform.io.repository.UserRepository;
import com.company.learningplatform.security.UserPrincipal;
import com.company.learningplatform.service.UserService;
import com.company.learningplatform.shared.dto.RoleDto;
import com.company.learningplatform.shared.dto.UserDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService
{
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private ModelMapper modelMapper;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
	{
		return new UserPrincipal(userRepository
				.findByEmail(email)
				.orElseThrow(() -> {
					throw new UsernameNotFoundException(MessageEnum.USERNAME_NOT_FOUND.getMessage());
				}));

	}

	@Override
	@Transactional
	public void createUser(UserDto userDto) throws UserAlreadyExistsException
	{
		userRepository.findByEmail(userDto.getEmail()).ifPresent(u -> {
			throw new UserAlreadyExistsException(MessageEnum.USER_ALREADY_EXISTS_EXCEPTION.getMessage());
		});

		Set<RoleEntity> roles = roleRepository
				.findRolesByNames(userDto.getRoles()
						.stream()
						.map(role -> role.getName())
						.collect(Collectors.toList()))
				.get();

		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

		userEntity.setPassword(bCryptPasswordEncoder.encode(UserConstant.TEMP_PASSWORD));
		userEntity.setRoles(roles);
		userEntity.setUsername(userDto.getEmail());
		userEntity.setUserInformation(mapList(userDto.getUserInformation(), UserInformationEntity.class));
		userEntity.getUserInformation().stream().forEach(e -> e.setUser(userEntity));

		userRepository.save(userEntity);

	}

	@Override
	public <T> void createUser(UserDto userDto, RoleDto roleDto, T userInformationDto)
			throws UserAlreadyExistsException, RoleNotFoundException
	{
		userRepository.findByEmail(userDto.getEmail()).ifPresent(u -> {
			throw new UserAlreadyExistsException(MessageEnum.USER_ALREADY_EXISTS_EXCEPTION.getMessage());
		});

		RoleEntity roleEntity = roleRepository.findByName(roleDto.getName())
				.orElseThrow(() -> {
					throw new RoleNotFoundException(MessageEnum.ROLE_NOT_FOUND_EXCEPTION.getMessage());
				});
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
		userEntity.getRoles().add(roleEntity);
		userEntity.getUserInformation()
				.add(modelMapper.map(userInformationDto, UserInformationEntity.class));
		userEntity.getUserInformation().stream().forEach(e -> e.setUser(userEntity));
		userEntity.setPassword(bCryptPasswordEncoder.encode(UserConstant.TEMP_PASSWORD));

		userRepository.save(userEntity);

	}

	@Override
	public UserDto getUser(String email) throws UsernameNotFoundException
	{
		UserEntity user = findByEmail(email);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public void updateUser(UserDto userDto) throws UsernameNotFoundException
	{
		UserEntity user = findByEmail(userDto.getEmail());

		modelMapper.map(user, userDto);
		userRepository.save(user);
	}

	@Override
	@Transactional
	public boolean deleteUserByUsername(String username) throws UsernameNotFoundException
	{
		UserEntity user = userRepository.findByEmail(username).orElseThrow(() -> {
			throw new UsernameNotFoundException(MessageEnum.USERNAME_NOT_FOUND.getMessage());
		});
		user.getRoles().stream()
				.forEach(role -> {
					role.getUsers().remove(user);
					roleRepository.save(role);
				});
		userRepository.delete(user);

		return true;
	}

	@Override
	public List<UserDto> getUsers(int page, int limit)
	{
		List<UserEntity> users = userRepository.findAll();
		return mapList(users, UserDto.class);
	}

	@Override
	public boolean changePassword(String email, String oldPassword, String newPassword)
	{
		UserEntity user = this.findByEmail(email);
		if (bCryptPasswordEncoder.matches(user.getPassword(), bCryptPasswordEncoder.encode(oldPassword))) {
			user.setPassword(bCryptPasswordEncoder.encode(newPassword));
			userRepository.save(user);
			return true;
		}
		return false;
	}

	@Override
	public void firstLogin(String email, String newPassword)
	{
		UserEntity user = this.findByEmail(email);
		user.setPassword(bCryptPasswordEncoder.encode(newPassword));
		userRepository.save(user);
	}
	// ===============================
	// private methods
	// ===============================

	private UserEntity findByEmail(String email) throws UsernameNotFoundException
	{
		return userRepository.findByEmail(email)
				.orElseThrow(() -> {
					throw new UsernameNotFoundException(MessageEnum.USERNAME_NOT_FOUND.getMessage());
				});
	}

	private <S, T> List<T> mapList(List<S> source, Class<T> targetClass)
	{
		return source
				.stream()
				.map(element -> modelMapper.map(element, targetClass))
				.collect(Collectors.toList());
	}

	private <S, T> Set<T> mapList(Set<S> source, Class<T> targetClass)
	{
		return source
				.stream()
				.map(element -> modelMapper.map(element, targetClass))
				.collect(Collectors.toSet());
	}

}
