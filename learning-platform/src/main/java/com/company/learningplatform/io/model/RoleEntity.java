package com.company.learningplatform.io.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
@Builder
public class RoleEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@ManyToMany(mappedBy = "roles")
	@Builder.Default
	private Set<UserEntity> users = new HashSet<>();

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(	name = "role_authority",
				joinColumns = {
						@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") },
				inverseJoinColumns = {
						@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID") })
	@Builder.Default
	private Set<AuthorityEntity> authorities = new HashSet<>();
}
