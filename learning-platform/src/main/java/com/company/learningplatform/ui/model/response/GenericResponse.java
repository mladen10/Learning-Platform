package com.company.learningplatform.ui.model.response;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse
{
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Europe/Belgrade")
	private Date timeStamp;
	private HttpStatus httpStatus;
	private int httpStatusCode;
	private String httpReasonPhrase;
	private String message;

	public Date getTimeStamp()
	{
		return new Date();
	}
}
