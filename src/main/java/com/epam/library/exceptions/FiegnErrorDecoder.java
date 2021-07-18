package com.epam.library.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FiegnErrorDecoder implements ErrorDecoder {

	private Logger logger= LoggerFactory.getLogger(FiegnErrorDecoder.class);
	
	@Override
	public Exception decode(String methodKey, Response response) {
		logger.error("decoder |  methodkey :" +methodKey+ " response :"+response.body() + " class : "+response.body().getClass());
		return new  ResponseStatusException(HttpStatus.valueOf(response.status()), response.reason());
		
	}
}
