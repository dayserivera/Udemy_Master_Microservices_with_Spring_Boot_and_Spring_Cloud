package com.in28minutes.rest.webservices.restfulwebservices.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4838682929001345967L;

	public PostNotFoundException(String string) {
		super(string);
	}
}
