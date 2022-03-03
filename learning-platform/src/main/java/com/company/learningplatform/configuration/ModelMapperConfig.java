package com.company.learningplatform.configuration;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.company.learningplatform.io.model.AdminInformationEntity;
import com.company.learningplatform.io.model.ProfessorInformationEntity;
import com.company.learningplatform.io.model.RoleEntity;
import com.company.learningplatform.io.model.StudentInformationEntity;
import com.company.learningplatform.io.model.UserEntity;
import com.company.learningplatform.io.model.UserInformationEntity;
import com.company.learningplatform.shared.dto.AdminInformationDto;
import com.company.learningplatform.shared.dto.ProfessorInformationDto;
import com.company.learningplatform.shared.dto.StudentInformationDto;
import com.company.learningplatform.shared.dto.UserDto;

@Configuration
public class ModelMapperConfig
{
	@Bean
	public ModelMapper modelMapper()
	{
		ModelMapper modelMapper = new ModelMapper();
		modelMapper = userEntityToDtoTypeMap(modelMapper);
		modelMapper = userDtoToEntityTypeMap(modelMapper);

//		modelMapper.typeMap(StudentInformationDto.class, UserInformationEntity.class)
//				.setProvider(new Provider<UserInformationEntity>()
//				{
//					public UserInformationEntity get(ProvisionRequest<UserInformationEntity> request)
//					{
//						return new StudentInformationEntity();
//					}
//				});
//
//		modelMapper.typeMap(ProfessorInformationDto.class, UserInformationEntity.class)
//				.setProvider(new Provider<UserInformationEntity>()
//				{
//					public UserInformationEntity get(ProvisionRequest<UserInformationEntity> request)
//					{
//						return new ProfessorInformationEntity();
//					}
//				});
//
//		modelMapper.typeMap(AdminInformationDto.class, UserInformationEntity.class)
//				.setProvider(new Provider<UserInformationEntity>()
//				{
//					public UserInformationEntity get(ProvisionRequest<UserInformationEntity> request)
//					{
//						return new AdminInformationEntity();
//					}
//				});

		return modelMapper;
	}

	private ModelMapper userDtoToEntityTypeMap(ModelMapper modelMapper)
	{
		modelMapper.typeMap(UserDto.class, UserEntity.class)
				.addMappings(mapper -> {
					mapper.skip(UserEntity::setId);
					mapper.skip(UserEntity::setAccountNonExpired);
					mapper.skip(UserEntity::setAccountNonLocked);
					mapper.skip(UserEntity::setCredentialsNonExpired);
					mapper.skip(UserEntity::setEnabled);
					mapper.skip(UserEntity::setUsername);
				});

		return modelMapper;
	}

	private ModelMapper userEntityToDtoTypeMap(ModelMapper modelMapper)
	{

		Converter<Set<RoleEntity>, Set<String>> roleEntityToDtoCnv = new Converter<>()
		{
			@Override
			public Set<String> convert(MappingContext<Set<RoleEntity>, Set<String>> context)
			{
				return context.getSource().stream()
						.map(RoleEntity::getName)
						.collect(Collectors.toCollection(HashSet::new));
			}
		};

		modelMapper
				.typeMap(UserEntity.class, UserDto.class).addMappings(mapper -> {
					mapper.using(roleEntityToDtoCnv).map(UserEntity::getRoles, UserDto::setRoles);
					mapper.skip(UserDto::setUserInformation);
				});

		return modelMapper;
	}
}
