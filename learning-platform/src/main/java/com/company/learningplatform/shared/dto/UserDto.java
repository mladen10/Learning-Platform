package com.company.learningplatform.shared.dto;

import java.util.HashSet;
import java.util.Set;

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
public class UserDto
{
	private String email;
	private String username;
	private String firstName;
	private String lastName;

	@Builder.Default
	private Set<RoleDto> roles = new HashSet<>();
	@Builder.Default
	private Set<? extends UserInformationDto> userInformation = new HashSet<>();

	private Boolean accountNonExpired;

	private Boolean accountNonLocked;

	private Boolean credentialsNonExpired;

	private Boolean enabled;
}
