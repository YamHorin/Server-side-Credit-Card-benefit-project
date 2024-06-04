package Application._a_Presentation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)

public class Boundary_is_not_filled_correct  extends RuntimeException{
	private static final long serialVersionUID = -209132244199139633L;
	public Boundary_is_not_filled_correct() {
		super();
	}
	public Boundary_is_not_filled_correct(String msg) {
		super(msg);
	}
	public Boundary_is_not_filled_correct(Throwable cause) {
		super(cause);
	}
	public Boundary_is_not_filled_correct(String message, Throwable cause) {
		super(message  , cause);
	}
}
