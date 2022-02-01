package com.company.learningplatform.shared;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.company.learningplatform.ui.model.response.GenericResponse;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utility
{
	public ResponseEntity<GenericResponse> response(HttpStatus httpStatus, String message)
	{
		GenericResponse httpResponse = GenericResponse.builder()
				.httpStatus(httpStatus)
				.httpStatusCode(httpStatus.value())
				.httpReasonPhrase(httpStatus.getReasonPhrase())
				.message(message)
				.build();
		return new ResponseEntity<>(httpResponse, httpStatus);
	}
}
