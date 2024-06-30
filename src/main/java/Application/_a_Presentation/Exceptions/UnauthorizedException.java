package Application._a_Presentation.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)

public class UnauthorizedException extends RuntimeException {
	private static final long serialVersionUID = -6794785427574107043L;

		public UnauthorizedException() {
			super();
		}
		public UnauthorizedException(String msg) {
			super(msg);
		}
		public UnauthorizedException(Throwable cause) {
			super(cause);
		}
		public UnauthorizedException(String message, Throwable cause) {
			super(message  , cause);
		}

}
