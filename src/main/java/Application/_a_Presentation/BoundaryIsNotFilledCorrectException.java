package Application._a_Presentation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)

public class BoundaryIsNotFilledCorrectException  extends RuntimeException{
	private static final long serialVersionUID = -209132244199139633L;
	public BoundaryIsNotFilledCorrectException() {
		super();
	}
	public BoundaryIsNotFilledCorrectException(String msg) {
		super(msg);
	}
	public BoundaryIsNotFilledCorrectException(Throwable cause) {
		super(cause);
	}
	public BoundaryIsNotFilledCorrectException(String message, Throwable cause) {
		super(message  , cause);
	}
}
