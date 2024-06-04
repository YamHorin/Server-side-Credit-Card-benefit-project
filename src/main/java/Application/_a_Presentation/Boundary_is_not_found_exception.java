package Application._a_Presentation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)

public class Boundary_is_not_found_exception extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public Boundary_is_not_found_exception() {
		super();
	}
	public Boundary_is_not_found_exception(String msg) {
		super(msg);
	}
	public Boundary_is_not_found_exception(Throwable cause) {
		super(cause);
	}
	public Boundary_is_not_found_exception(String message, Throwable cause) {
		super(message  , cause);
	}
	
	

}
