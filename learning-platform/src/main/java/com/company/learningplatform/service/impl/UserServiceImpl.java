package com.company.learningplatform.service.impl;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.learningplatform.constant.MessageEnum;
import com.company.learningplatform.constant.RoleEnum;
import com.company.learningplatform.constant.UserConstant;
import com.company.learningplatform.exception.RoleNotFoundException;
import com.company.learningplatform.exception.UserAlreadyExistsException;
import com.company.learningplatform.io.model.AdminInformationEntity;
import com.company.learningplatform.io.model.ProfessorInformationEntity;
import com.company.learningplatform.io.model.RoleEntity;
import com.company.learningplatform.io.model.StudentInformationEntity;
import com.company.learningplatform.io.model.UserEntity;
import com.company.learningplatform.io.model.UserInformationEntity;
import com.company.learningplatform.io.repository.RoleRepository;
import com.company.learningplatform.io.repository.UserRepository;
import com.company.learningplatform.security.UserPrincipal;
import com.company.learningplatform.service.RoleService;
import com.company.learningplatform.service.UserService;
import com.company.learningplatform.shared.dto.UserDto;
import com.company.learningplatform.ui.model.request.CreateUserRequestModel;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService
{
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private ModelMapper modelMapper;
	private RoleService roleService;

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

		userEntity.setPassword(passwordEncoder.encode(UserConstant.TEMP_PASSWORD));
		userEntity.setRoles(roles);
		userEntity.setUsername(userDto.getEmail());
		userEntity.setUserInformation(mapList(userDto.getUserInformation(), UserInformationEntity.class));
		userEntity.getUserInformation().stream().forEach(e -> e.setUser(userEntity));

		userRepository.save(userEntity);
	}

	@Override
	public UserDto createUser(CreateUserRequestModel createUserReqModel, RoleEnum role)
			throws UserAlreadyExistsException, RoleNotFoundException
	{
		userRepository.findByEmail(createUserReqModel.getEmail()).ifPresent(u -> {
			throw new UserAlreadyExistsException(MessageEnum.USER_ALREADY_EXISTS_EXCEPTION.getMessage());
		});

		RoleEntity roleEntity = roleService.<RoleEntity>findByName(role.name(), RoleEntity.class);

		UserEntity userEntity = modelMapper.map(createUserReqModel, UserEntity.class);
		userEntity.getRoles().add(roleEntity);
		userEntity.setPassword(passwordEncoder.encode(UserConstant.TEMP_PASSWORD));
		userEntity.getUserInformation().add(createUserInformationBasedOnRole(role));
		userEntity.getUserInformation().iterator().next().setUser(userEntity);

		return modelMapper.map(userRepository.save(userEntity), UserDto.class);
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
		if (passwordEncoder.matches(user.getPassword(), passwordEncoder.encode(oldPassword))) {
			user.setPassword(passwordEncoder.encode(newPassword));
			userRepository.save(user);
			return true;
		}
		return false;
	}

	@Override
	public void firstLogin(String email, String newPassword)
	{
		UserEntity user = this.findByEmail(email);
		user.setPassword(passwordEncoder.encode(newPassword));
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

	private UserInformationEntity createUserInformationBasedOnRole(RoleEnum role)
	{
		if (role.equals(RoleEnum.STUDENT)) {
			return new StudentInformationEntity();
		} else if (role.equals(RoleEnum.PROFFESOR)) {
			return new ProfessorInformationEntity();
		} else if (role.equals(RoleEnum.ADMIN) || role.equals(RoleEnum.ROOT_ADMIN)) {
			return new AdminInformationEntity();
		}
		return null;
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
