package exception;

public class CertificadoException extends Exception {
	private static final long serialVersionUID = -1618914697541285600L;

	public CertificadoException() {
		super();
	}

	public CertificadoException(String message, Throwable cause) {
		super(message, cause);
	}

	public CertificadoException(String message) {
		super(message);
	}

	public CertificadoException(Throwable cause) {
		super(cause);
	}
}
