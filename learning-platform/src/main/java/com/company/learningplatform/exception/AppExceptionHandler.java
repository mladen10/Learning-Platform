package com.company.learningplatform.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.company.learningplatform.shared.Utility;
import com.company.learningplatform.ui.model.response.GenericResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class AppExceptionHandler implements ErrorController
{
	@AllArgsConstructor
	@Getter
	private enum ErrorMessageEnum
	{
		USER_ALREADY_EXIST("USER_ALREADY_EXIST_MSG"),
		INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR_MSG"),
		CONSTRAINT_VIOLATION("CONSTRAINT_VIOLATION_EXCEPTION_MSG");

		private final String message;
	}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	public ResponseEntity<GenericResponse> handleConstraintViolationException(ConstraintViolationException e)
	{
		log.info(e.getLocalizedMessage());
		return Utility.response(HttpStatus.BAD_REQUEST,
				ErrorMessageEnum.CONSTRAINT_VIOLATION.getMessage());
	}

	@ExceptionHandler(value = { UserAlreadyExistsException.class })
	public ResponseEntity<GenericResponse> handleUserAlreadyExistException(UserAlreadyExistsException e)
	{
		log.info(e.getLocalizedMessage());
		return Utility.response(HttpStatus.BAD_REQUEST,
				ErrorMessageEnum.USER_ALREADY_EXIST.getMessage());
	}

	@ExceptionHandler(value = { RoleNotFoundException.class })
	public ResponseEntity<GenericResponse> handleUserAlreadyExistException(RoleNotFoundException e)
	{
		log.info(e.getLocalizedMessage());
		return Utility.response(HttpStatus.BAD_REQUEST,
				ErrorMessageEnum.USER_ALREADY_EXIST.getMessage());
	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<GenericResponse> handleOtherExceptions(Exception e)
	{
		log.info(e.getLocalizedMessage());
		return Utility.response(HttpStatus.INTERNAL_SERVER_ERROR,
				ErrorMessageEnum.INTERNAL_SERVER_ERROR.getMessage());
	}

}
