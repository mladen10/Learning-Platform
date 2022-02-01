package com.company.learningplatform.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.company.learningplatform.io.model.RoleEntity;
import com.company.learningplatform.io.model.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserPrincipal implements UserDetails, CredentialsContainer
{

	private static final long serialVersionUID = -831300089880427699L;

	private UserEntity userEntity;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return this.userEntity.getRoles().stream()
				.map(RoleEntity::getAuthorities)
				.flatMap(Set::stream)
				.map(authority -> new SimpleGrantedAuthority(authority.getPermision()))
				.collect(Collectors.toSet());
	}

	@Override
	public String getPassword()
	{
		// TODO Auto-generated method stub
		return this.userEntity.getPassword();
	}

	@Override
	public String getUsername()
	{
		// TODO Auto-generated method stub
		return this.userEntity.getEmail();
	}

	@Override
	public boolean isAccountNonExpired()
	{
		// TODO Auto-generated method stub
		return this.userEntity.getAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return this.userEntity.getAccountNonLocked();

	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return this.userEntity.getCredentialsNonExpired();

	}

	@Override
	public boolean isEnabled()
	{
		return this.userEntity.getEnabled();

	}

	@Override
	public void eraseCredentials()
	{
		// this.password = null; ???
	}

}
