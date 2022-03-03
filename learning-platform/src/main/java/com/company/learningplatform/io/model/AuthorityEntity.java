package com.company.learningplatform.io.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NamedEntityGraph(
					name = "authority-role-graph",
					attributeNodes = {
							@NamedAttributeNode("roles")
					})

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authority")
@Builder
public class AuthorityEntity implements Serializable
{

	private static final long serialVersionUID = 6747500930517264127L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(unique = true)
	private String permision;

	@ManyToMany(mappedBy = "authorities")
	@Builder.Default
	private Set<RoleEntity> roles = new HashSet<>();
}
