package com.company.learningplatform.io.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "student_information")
public class StudentInformationEntity extends UserInformationEntity
{
	private static final long serialVersionUID = -1695038092324871034L;
	private String studentProperties;
}
