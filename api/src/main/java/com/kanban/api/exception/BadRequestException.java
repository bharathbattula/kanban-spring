package com.kanban.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 735727378268472880L;

	public BadRequestException(final String msg) {
		super(msg);
	}

	public BadRequestException(final String msg, final Throwable cause) {
		super(msg, cause);
	}
}
