package com.company.learningplatform.ui.model.request;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestModel
{
	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@NotNull
	@Email
	private String email;

	// @Setter(lombok.AccessLevel.NONE)
	private Set<String> roleNames;

	public void setRoleNames(Set<String> roleNames)
	{
		this.roleNames = roleNames.stream().map(e -> e.toUpperCase()).collect(Collectors.toSet());
	}

}
