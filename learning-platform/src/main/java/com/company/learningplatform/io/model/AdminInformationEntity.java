package com.company.learningplatform.io.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name = "admin_information")
public class AdminInformationEntity extends UserInformationEntity
{
	private static final long serialVersionUID = -6842970760870475082L;
	private String adminProperties;
}
