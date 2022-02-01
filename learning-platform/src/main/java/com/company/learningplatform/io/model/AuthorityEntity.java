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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authority")
@Builder
public class AuthorityEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String permision;

//	@ManyToMany(mappedBy = "authorities")
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(	name = "role_authority",
				inverseJoinColumns = {
						@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") },
				joinColumns = {
						@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID") })
	@Builder.Default
	private Set<RoleEntity> roles = new HashSet<>();
}
