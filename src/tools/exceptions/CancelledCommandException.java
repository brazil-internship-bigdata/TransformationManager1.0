package tools.exceptions;

public class CancelledCommandException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1591377902575469556L;

	public CancelledCommandException() {
		super();
	}

	public CancelledCommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CancelledCommandException(String message, Throwable cause) {
		super(message, cause);
	}

	public CancelledCommandException(String message) {
		super(message);
	}

	public CancelledCommandException(Throwable cause) {
		super(cause);
	}

}
