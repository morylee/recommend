package org.yzkx.secin.core.exception;

import org.yzkx.secin.core.HttpCode;

/**
 * @author LiChenhui
 */
@SuppressWarnings("serial")
public class IllegalParameterException extends BaseException {

	public IllegalParameterException() {
	}

	public IllegalParameterException(Throwable ex) {
		super(ex);
	}

	public IllegalParameterException(String message) {
		super(message);
	}

	public IllegalParameterException(String message, Throwable ex) {
		super(message, ex);
	}

	protected HttpCode getHttpCode() {
		return HttpCode.BAD_REQUEST;
	}

}
