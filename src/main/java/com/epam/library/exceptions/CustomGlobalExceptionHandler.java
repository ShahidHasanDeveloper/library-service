package com.epam.library.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.netflix.client.ClientException;

@ControllerAdvice
public class CustomGlobalExceptionHandler  extends ResponseEntityExceptionHandler{




	@ExceptionHandler(ClientException.class)
	public final ResponseEntity<Object>handleExceptions(ClientException ex, WebRequest req){ 
		CustomErrorDetails customErrorDetails = new
			CustomErrorDetails(new Date(), "underline service might be down. Please check once", ex.getMessage());
		return new ResponseEntity<Object>(customErrorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
			
	}


}
