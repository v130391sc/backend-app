package es.upm.fi.catering.service.backendapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ResourceUnauthorized extends RuntimeException {

	public ResourceUnauthorized(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
