package one.digitalinnovation.personapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 28495874871067123L;

	public PersonNotFoundException(Long id) {
		super("Person not found with id " + id);
	}
}
