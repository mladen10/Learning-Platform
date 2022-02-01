package com.company.learningplatform.io.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.learningplatform.io.model.AuthorityEntity;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Integer>
{
	Optional<AuthorityEntity> findByPermision(String permision);
}
