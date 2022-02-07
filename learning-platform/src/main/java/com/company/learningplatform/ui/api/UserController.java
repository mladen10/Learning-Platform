package com.company.learningplatform.ui.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.learningplatform.constant.MessageEnum;
import com.company.learningplatform.constant.RoleEnum;
import com.company.learningplatform.constant.SecurityConstant;
import com.company.learningplatform.event.AppEventPublisher;
import com.company.learningplatform.event.OnCreateUserEvent;
import com.company.learningplatform.security.UserPrincipal;
import com.company.learningplatform.security.token.JWTProvider;
import com.company.learningplatform.service.UserService;
import com.company.learningplatform.shared.Utility;
import com.company.learningplatform.shared.dto.UserDto;
import com.company.learningplatform.shared.dto.UserInformationDto;
import com.company.learningplatform.ui.model.request.ChangePasswordRequestModel;
import com.company.learningplatform.ui.model.request.CreateUserRequestModel;
import com.company.learningplatform.ui.model.request.FirstLoginRequestModel;
import com.company.learningplatform.ui.model.request.LoginRequestModel;
import com.company.learningplatform.ui.model.request.UpdateUserRequestModel;
import com.company.learningplatform.ui.model.response.GenericResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/users",
				produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
				consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class UserController
{
	private AuthenticationManager authManager;
	private JWTProvider jwtProvider;
	private UserService userService;
	private ModelMapper modelMapper;
	private AppEventPublisher appPublisher;

	@PostMapping(value = "/createStudent")
	// @StudentCreatePermission
	ResponseEntity<GenericResponse> createStudent(@Valid @RequestBody CreateUserRequestModel createUserRequestModel)
	{
		return createUser(createUserRequestModel, RoleEnum.STUDENT);
	}

	@PostMapping(value = "/createProfessor")
	public ResponseEntity<GenericResponse> createProfessor(
			@Valid @RequestBody CreateUserRequestModel createUserRequestModel)
	{
		return createUser(createUserRequestModel, RoleEnum.PROFFESOR);
	}

	@PostMapping(value = "/createAdmin")
	public ResponseEntity<GenericResponse> createAdmin(
			@Valid @RequestBody CreateUserRequestModel createUserRequestModel)
	{
		return createUser(createUserRequestModel, RoleEnum.ADMIN);
	}

	@PostMapping("/login")
	public ResponseEntity<UserDto> login(@RequestBody LoginRequestModel loginReq)
	{
		authManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
		UserPrincipal user = (UserPrincipal) userService.loadUserByUsername(loginReq.getEmail());
		HttpHeaders jwtHeader = getJwtHeader(user);
		UserDto userDto = modelMapper.map(user.getUserEntity(), UserDto.class);
		return new ResponseEntity<>(userDto, jwtHeader, HttpStatus.OK);
	}

	@PutMapping("/changePassword")
	public ResponseEntity<GenericResponse> changePassword(HttpServletRequest request,
			@Valid @RequestBody ChangePasswordRequestModel changePasswordReq)
	{
		String email = request.getUserPrincipal().getName();
		boolean isOk = userService.changePassword(email, changePasswordReq.getOldPassword(),
				changePasswordReq.getNewPassword());
		if (isOk)
			return Utility.response(HttpStatus.OK, "neka poruka");
		return Utility.response(HttpStatus.UNAUTHORIZED, "neka poruka");
	}

	@PutMapping("/confirmation")
	public ResponseEntity<GenericResponse> firstLogin(@Valid @RequestBody FirstLoginRequestModel firstLoginReq)
	{
		String email = jwtProvider.getSubject(firstLoginReq.getToken());
		userService.firstLogin(email, firstLoginReq.getNewPassword());

		return Utility.response(HttpStatus.OK, "neka poruka");
	}

	@GetMapping("/find/{email}")
	public ResponseEntity<UserDto> getUser(@PathVariable("email") String email)
	{
		UserDto user = userService.getUser(email);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<UserDto>> getAllUsers()
	{
		List<UserDto> users = userService.getUsers(0, 0);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<GenericResponse> update(@Valid @RequestBody UpdateUserRequestModel requestModel)
	{
		UserDto userDto = modelMapper.map(requestModel, UserDto.class);
		userService.updateUser(userDto);
		return Utility.response(HttpStatus.OK, "neka poruka");
	}

	@DeleteMapping("/delete/{username}")
	public ResponseEntity<GenericResponse> deleteUser(@PathVariable("username") String username)
	{
		String message = MessageEnum.SUCCESFFUL_DELETE.getMessage();
		if (!userService.deleteUserByUsername(username))
			message = MessageEnum.GRESKADELETE.getMessage();

		return Utility.response(HttpStatus.NO_CONTENT, message);
	}

	// ===============================
	// private methods
	// ===============================
	private HttpHeaders getJwtHeader(UserPrincipal userPrincipal)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add(SecurityConstant.JWT_TOKEN_HEADER, jwtProvider.generateToken(userPrincipal));
		return headers;
	}

	private <T extends UserInformationDto> ResponseEntity<GenericResponse> createUser(
			CreateUserRequestModel createUserReqModel, RoleEnum role)
	{
		userService.createUser(createUserReqModel, role);
		appPublisher.publishEvent(new OnCreateUserEvent(this, createUserReqModel.getEmail()));

		// return Utility.response(HttpStatus.CREATED,
		// MessageEnum.SUCCESFFUL_CREATE.getMessage());
		// =============================================================================================
		// TMP CODE
		String t = jwtProvider.tokenForEmailConfirm(createUserReqModel.getEmail());
		return Utility.response(HttpStatus.CREATED, t);
		// =============================================================================================
	}

//	@SuppressWarnings("unused")
//	private <T extends UserInformationDto> ResponseEntity<GenericResponse> createUser(
//			CreateUserRequestModel createUserRequestModel,
//			Set<RoleEnum> rolesEnum,
//			Set<T> userInformationsDto)
//	{
//		UserDto userDto = modelMapper.map(createUserRequestModel, UserDto.class);
//		Set<RoleDto> rolesDto = rolesEnum
//				.stream()
//				.map(new Function<RoleEnum, RoleDto>()
//				{
//					@Override
//					public RoleDto apply(RoleEnum t)
//					{
//						return RoleDto.builder()
//								.name(t.name())
//								.build();
//					}
//				})
//				.collect(Collectors.toSet());
//		userDto.setRoles(rolesDto);
//		userDto.setUserInformation(userInformationsDto);
//
//		return Utility.response(HttpStatus.CREATED, MessageEnum.SUCCESFFUL_CREATE.getMessage());
//	}

}
