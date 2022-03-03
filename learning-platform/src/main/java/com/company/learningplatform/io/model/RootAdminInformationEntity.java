package com.company.learningplatform.io.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "root_admin_information")
public class RootAdminInformationEntity extends AdminInformationEntity
{
	private static final long serialVersionUID = -6811588887197122152L;
	private String rootAdminProperties;
}
