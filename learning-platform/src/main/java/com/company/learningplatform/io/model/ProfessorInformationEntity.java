package com.company.learningplatform.io.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "professor_information")
public class ProfessorInformationEntity extends UserInformationEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8541673465392734448L;
	private String proffesorProperties;
}
