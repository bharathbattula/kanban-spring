package com.kanban.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5261600905049703426L;

	public ResourceNotFoundException(final String msg) {
		super(msg);
	}

	public ResourceNotFoundException(final String msg, final Throwable cause) {
		super(msg, cause);
	}
}
