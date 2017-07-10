package tools;

public class InterruptedFileTransferException extends Exception {

	public InterruptedFileTransferException() {
		super("An interruption occured while the files were being transfered");
	}

	public InterruptedFileTransferException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InterruptedFileTransferException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InterruptedFileTransferException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InterruptedFileTransferException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
