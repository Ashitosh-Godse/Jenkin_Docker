package com.epam.advice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionResponse invalidArgumentException(MethodArgumentNotValidException e,WebRequest r){
		log.info("MethodArgumentNotValidException exception occured:{}",e.getMessage());
		log.error(ExceptionUtils.getStackTrace(e));
		List<String>errorList=new ArrayList<>();
		e.getBindingResult().getAllErrors().forEach(err->errorList.add(err.getDefaultMessage()));
		return new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.toString(), errorList.toString(), r.getDescription(false));
		
	}
    
	@ExceptionHandler(MailException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionResponse bookExceptionHandler(MailException e,WebRequest r) {
		log.error(ExceptionUtils.getStackTrace(e));
		log.info("BookException exception occured:{}",e.getMessage());
		return new ExceptionResponse(new Date().toString(),HttpStatus.NOT_FOUND.toString(),e.getMessage(),r.getDescription(false));
	}
	
	
}
