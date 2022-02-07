package com.company.learningplatform.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.learningplatform.io.model.StudentInformationEntity;

@Repository
public interface StudentInformationRepository
		extends JpaRepository<StudentInformationEntity, Long>, UserInformationRepository<StudentInformationEntity>
{

}
