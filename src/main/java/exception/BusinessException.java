package exception;

public class BusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5555012302852826283L;

	public BusinessException() {
		super();
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

}
