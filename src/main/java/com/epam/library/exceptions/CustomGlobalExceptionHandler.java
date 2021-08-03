package com.epam.library.exceptions;

import static com.epam.library.common.LibraryConstants.TRACE_ID;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.netflix.client.ClientException;
import com.netflix.hystrix.exception.HystrixRuntimeException;

@ControllerAdvice
public class CustomGlobalExceptionHandler  extends ResponseEntityExceptionHandler{

private final Logger  logger= LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);


	@ExceptionHandler(ClientException.class)
	public final ResponseEntity<Object>handleExceptions(ClientException ex, WebRequest req){ 
		logger.error("CustomGlobalExceptionHandler |  handleExceptions | underline service might be down. Please check once  {} : "+ex.getCause());
		CustomErrorDetails customErrorDetails = new
			CustomErrorDetails(new Date(), "underline service might be down. Please check once", ex.getMessage(), MDC.get(TRACE_ID));
		return new ResponseEntity<Object>(customErrorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
			
	}
	
	
	@ExceptionHandler(HystrixRuntimeException.class)
	public final ResponseEntity<Object>handleHystrixExceptions(HystrixRuntimeException ex, WebRequest req){ 
		logger.error("CustomGlobalExceptionHandler |  handleExceptions | underline service might be down or responding slow. Please check once  {} : "+ex.getCause());
		CustomErrorDetails customErrorDetails = new
			CustomErrorDetails(new Date(), "underline service might be down. Please check once or responding slow", ex.getMessage(), MDC.get(TRACE_ID));
		return new ResponseEntity<Object>(customErrorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
			
	}


}
