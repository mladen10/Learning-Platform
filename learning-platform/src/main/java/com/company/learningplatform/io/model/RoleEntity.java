package com.company.learningplatform.io.model;

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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NamedEntityGraph(
					name = "role-entity-graph",
					attributeNodes = { @NamedAttributeNode("users")
					})

@NamedEntityGraph(
					name = "role-authority-graph",
					attributeNodes = { @NamedAttributeNode(value = "authorities", subgraph = "authories-subgraph") },
					subgraphs = { @NamedSubgraph(	name = "authority-subgraph",
													attributeNodes = { @NamedAttributeNode("roles") })
					})

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

	@Column(unique = true)
	private String name;

	@ManyToMany(mappedBy = "roles")
	@Builder.Default
	private Set<UserEntity> users = new HashSet<>();

	@ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(	name = "role_authority",
				joinColumns = {
						@JoinColumn(name = "role_id", referencedColumnName = "id") },
				inverseJoinColumns = {
						@JoinColumn(name = "authority_id", referencedColumnName = "id") })
	@Builder.Default
	private Set<AuthorityEntity> authorities = new HashSet<>();
}
