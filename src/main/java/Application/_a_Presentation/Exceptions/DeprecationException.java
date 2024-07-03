package Application._a_Presentation.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)

public class DeprecationException extends RuntimeException {
	private static final long serialVersionUID = 5817983602753987345L;

	public DeprecationException() {
		super();
	}

	public DeprecationException(String message) {
		super("Deprecation exception"+message);
	}

	public DeprecationException(Throwable cause) {
		super(cause);
	}

	public DeprecationException(String message, Throwable cause) {
		super("Deprecation exception"+message, cause);
	}
}
