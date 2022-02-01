package com.company.learningplatform.io.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity implements Serializable
{
	private static final long serialVersionUID = -9059813763498590767L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 120, unique = true)
	// @Setter(AccessLevel.NONE)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<UserInformationEntity> userInformation;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST },
				fetch = FetchType.EAGER)
	@JoinTable(	name = "user_role",
				joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") },
				inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") })
	private Set<RoleEntity> roles;

	private Boolean accountNonExpired = true;

	private Boolean accountNonLocked = true;

	private Boolean credentialsNonExpired = true;

	private Boolean enabled = false;

	public void setEmail(String email)
	{
		this.email = email;
		String[] emailParts = email.split("@");
		this.username = emailParts[0];
	}

}
