package com.company.learningplatform.io.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.company.learningplatform.io.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>
{
	Optional<UserEntity> findByEmail(String email);

	Optional<UserEntity> findByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	@Modifying
	@Query(value = "DELETE FROM User WHERE username = :username", nativeQuery = true)
	void deleteByUsername(@Param("username") String username);

//	@Modifying
//	@Query("delete from User u where u.firstName = ?1")
//	void deleteUsersByFirstName(String firstName);
}
