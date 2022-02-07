package com.company.learningplatform.io.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.company.learningplatform.io.model.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer>
{
	@Query(value = "select * from Role r where r.name in (:roleNames)", nativeQuery = true)
	Optional<Set<RoleEntity>> findRolesByNames(@Param("roleNames") List<String> roleNames);

//	@EntityGraph(value = "role-user-graph", type = EntityGraphType.LOAD)
//	@Query(value = "select * from Role r where r.name in (:roleNames)", nativeQuery = true)
//	Optional<Set<RoleEntity>> findRolesByNamesWithUsers(@Param("roleNames") List<String> roleNames);

	@EntityGraph(value = "role-authority-graph", type = EntityGraphType.LOAD)
	@Query(value = "select role from RoleEntity role where role.name=?1")
	Optional<RoleEntity> findByNameWihtAuthorities(String roleName);

	@EntityGraph(attributePaths = { "authorities.roles" }, type = EntityGraphType.LOAD)
	@Query(value = "select role from RoleEntity role where role.name=?1")
	Optional<RoleEntity> findByNameWihtAuthoritiesNRoles(String roleName);

	@EntityGraph(value = "role-authority-graph", type = EntityGraphType.LOAD)
	@Query(value = "select role from RoleEntity role where role.name in (:roleNames)")
	Optional<Set<RoleEntity>> findByNamesWihtAuthorities(@Param("roleNames") List<String> roleNames);

	@EntityGraph(value = "role-authority-graph", type = EntityGraphType.LOAD)
	@Query(value = "select role from RoleEntity role")
	Optional<Set<RoleEntity>> findAllWihtAuthorities();

	Optional<RoleEntity> findByName(String name);

	boolean existsByName(String name);

}
