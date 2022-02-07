package com.company.learningplatform.io.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.company.learningplatform.io.model.UserInformationEntity;

@NoRepositoryBean
public interface UserInformationRepository<T extends UserInformationEntity> extends Repository<T, Long>
{

}
