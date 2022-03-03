package com.company.learningplatform.io.model;

import java.io.Serializable;
import java.util.HashSet;
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
import javax.persistence.OneToOne;
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

	@OneToOne(	mappedBy = "user",
				cascade = { CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST },
				fetch = FetchType.EAGER)
	private UserInformationEntity userInformation;

	@ManyToMany(cascade = { CascadeType.MERGE },
				fetch = FetchType.EAGER)
	@JoinTable(	name = "user_role",
				joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
				inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") })
	private Set<RoleEntity> roles = new HashSet<>();

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
