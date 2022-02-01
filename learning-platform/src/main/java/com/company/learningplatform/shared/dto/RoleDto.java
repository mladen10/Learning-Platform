package com.company.learningplatform.shared.dto;

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
public class RoleDto
{
	private String name;
	private Set<UserDto> users;
	private Set<AuthorityDto> authorities;
}
