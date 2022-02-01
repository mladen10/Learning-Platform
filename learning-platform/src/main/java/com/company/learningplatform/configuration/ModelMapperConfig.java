package com.company.learningplatform.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.Provider.ProvisionRequest;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.company.learningplatform.io.model.AdminInformationEntity;
import com.company.learningplatform.io.model.ProfessorInformationEntity;
import com.company.learningplatform.io.model.StudentInformationEntity;
import com.company.learningplatform.io.model.UserEntity;
import com.company.learningplatform.io.model.UserInformationEntity;
import com.company.learningplatform.shared.dto.AdminInformationDto;
import com.company.learningplatform.shared.dto.ProfessorInformationDto;
import com.company.learningplatform.shared.dto.StudentInformationDto;
import com.company.learningplatform.shared.dto.UserDto;
import com.company.learningplatform.shared.dto.UserInformationDto;

@Configuration
public class ModelMapperConfig
{
	@Bean
	public ModelMapper modelMapper()
	{
		ModelMapper modelMapper = new ModelMapper();

		TypeMap<UserDto, UserEntity> propertyMapper = modelMapper
				.createTypeMap(UserDto.class, UserEntity.class);
		propertyMapper
				.addMappings(mapper -> {
					mapper.skip(UserEntity::setId);
					mapper.skip(UserEntity::setAccountNonExpired);
					mapper.skip(UserEntity::setAccountNonLocked);
					mapper.skip(UserEntity::setCredentialsNonExpired);
					mapper.skip(UserEntity::setEnabled);
					mapper.skip(UserEntity::setUsername);
				});

//		modelMapper.createTypeMap(UserInformationDto.class, UserInformationEntity.class)
//				.include(StudentInformationDto.class, StudentInformationEntity.class)
//				.include(AdminInformationDto.class, AdminInformationEntity.class)
//				.include(ProfessorInformationDto.class, ProfessorInformationEntity.class);

//		modelMapper.typeMap(BaseSrcA.class, BaseDest.class).setProvider(new Provider<BaseDest>()
//		{
//			public BaseDest get(ProvisionRequest<BaseDest> request)
//			{
//				return new BaseDestA();
//			}
//		});

		modelMapper.typeMap(StudentInformationDto.class, UserInformationEntity.class)
				.setProvider(new Provider<UserInformationEntity>()
				{
					public UserInformationEntity get(ProvisionRequest<UserInformationEntity> request)
					{
						return new StudentInformationEntity();
					}
				});

		modelMapper.typeMap(ProfessorInformationDto.class, UserInformationEntity.class)
				.setProvider(new Provider<UserInformationEntity>()
				{
					public UserInformationEntity get(ProvisionRequest<UserInformationEntity> request)
					{
						return new ProfessorInformationEntity();
					}
				});

		modelMapper.typeMap(AdminInformationDto.class, UserInformationEntity.class)
				.setProvider(new Provider<UserInformationEntity>()
				{
					public UserInformationEntity get(ProvisionRequest<UserInformationEntity> request)
					{
						return new AdminInformationEntity();
					}
				});

		return modelMapper;
	}
}
