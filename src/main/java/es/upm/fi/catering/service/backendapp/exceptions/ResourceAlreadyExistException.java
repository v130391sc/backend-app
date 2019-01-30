package es.upm.fi.catering.service.backendapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceAlreadyExistException extends RuntimeException {

	public ResourceAlreadyExistException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
