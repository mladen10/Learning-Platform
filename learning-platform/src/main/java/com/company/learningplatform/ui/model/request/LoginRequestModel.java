package com.company.learningplatform.ui.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.company.learningplatform.annotation.ValidPassword;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginRequestModel
{
	@Email
	@NotNull
	private String email;

	@ValidPassword
	@NotNull
	private String password;
}
