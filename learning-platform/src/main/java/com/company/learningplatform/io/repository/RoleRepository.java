package com.company.learningplatform.io.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

	Optional<RoleEntity> findByName(String name);

	boolean existsByName(String name);
}
