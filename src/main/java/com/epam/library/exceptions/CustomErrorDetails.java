package com.epam.library.exceptions;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CustomErrorDetails {

	private Date timestamp;
	private String message;
	private String errorDetails;
	private String traceId;
	
}
