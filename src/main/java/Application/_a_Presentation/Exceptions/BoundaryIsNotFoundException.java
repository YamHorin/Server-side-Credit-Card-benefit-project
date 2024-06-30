package Application._a_Presentation.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)

public class BoundaryIsNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BoundaryIsNotFoundException() {
		super();
	}
	public BoundaryIsNotFoundException(String msg) {
		super(msg);
	}
	public BoundaryIsNotFoundException(Throwable cause) {
		super(cause);
	}
	public BoundaryIsNotFoundException(String message, Throwable cause) {
		super(message  , cause);
	}
	
	

}
