package com.company.learningplatform.ui.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.company.learningplatform.annotation.ValidPassword;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserLoginRequestModel
{
	@NotNull
	@Email
	private String email;

	@NotNull
	@ValidPassword
	private String password;
}
