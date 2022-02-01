package com.company.learningplatform.ui.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateUserRequestModel
{
	@NotNull
	@Email
	private String email;

	@NotNull

	private String firstName;
	@NotNull
	private String lastName;
}
