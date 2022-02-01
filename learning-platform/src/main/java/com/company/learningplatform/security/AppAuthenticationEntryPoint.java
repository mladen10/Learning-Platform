package com.company.learningplatform.security;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import com.company.learningplatform.ui.model.response.GenericResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AppAuthenticationEntryPoint extends Http403ForbiddenEntryPoint
{
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
			throws IOException
	{
		GenericResponse httpResponse = GenericResponse.builder()
				.httpStatus(HttpStatus.FORBIDDEN)
				.message(HttpStatus.FORBIDDEN.getReasonPhrase())
				.build();

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.FORBIDDEN.value());
		OutputStream outputStream = response.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(outputStream, httpResponse);
		outputStream.flush();
	}
}
