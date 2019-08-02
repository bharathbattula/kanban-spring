package com.kanban.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 3742112337599361080L;

	public ApplicationException(final String msg) {
		super(msg);
	}

	public ApplicationException(final String msg, final Throwable cause) {
		super(msg,cause);
	}
}
