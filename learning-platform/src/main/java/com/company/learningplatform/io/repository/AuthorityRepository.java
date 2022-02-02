package com.company.learningplatform.io.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.company.learningplatform.io.model.AuthorityEntity;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Integer>
{
	Optional<AuthorityEntity> findByPermision(String permision);

	@EntityGraph(value = "authority-role-graph", type = EntityGraphType.LOAD)
	@Query(value = "select a from AuthorityEntity a where a.permision=?1")
	Optional<AuthorityEntity> findByPermisionWihtRoles(String permision);
}
