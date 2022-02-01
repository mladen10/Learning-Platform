package com.company.learningplatform.ui.model.request;

import javax.validation.constraints.NotNull;

import com.company.learningplatform.annotation.ValidPassword;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequestModel
{
	@NotNull
	private String oldPassword;

	@NotNull
	@ValidPassword
	private String newPassword;

}
