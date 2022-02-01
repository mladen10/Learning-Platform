package com.company.learningplatform.ui.model.request;

import javax.validation.constraints.NotNull;

import com.company.learningplatform.annotation.ValidPassword;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FirstLoginRequestModel
{
	@ValidPassword
	@NotNull
	private String newPassword;

	@NotNull
	private String token;
}
