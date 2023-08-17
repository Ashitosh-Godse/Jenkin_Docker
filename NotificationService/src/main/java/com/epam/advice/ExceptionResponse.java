package com.epam.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {
	private String timeStamp;
	private String status;
	private String path;
	private String error;

}
