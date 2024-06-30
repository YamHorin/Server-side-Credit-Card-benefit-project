package Application._a_Presentation.Exceptions;

public class DeprecationException extends RuntimeException {
	private static final long serialVersionUID = 5817983602753987345L;

	public DeprecationException() {
		super();
	}

	public DeprecationException(String message) {
		super(message);
	}

	public DeprecationException(Throwable cause) {
		super(cause);
	}

	public DeprecationException(String message, Throwable cause) {
		super(message, cause);
	}
}
