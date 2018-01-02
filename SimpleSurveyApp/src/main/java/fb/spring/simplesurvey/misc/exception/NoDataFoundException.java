/**
 * 
 */
package fb.spring.simplesurvey.misc.exception;

/**
 * @author fbecke12
 *
 */
public class NoDataFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoDataFoundException() {

	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NoDataFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoDataFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public NoDataFoundException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NoDataFoundException(Throwable cause) {
		super(cause);
	}
}
