package com.company.learningplatform.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.learningplatform.io.model.ProfessorInformationEntity;

@Repository
public interface ProfessorInformationRepository extends JpaRepository<ProfessorInformationEntity, Long>
{

}
