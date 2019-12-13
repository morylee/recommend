package org.yzkx.secin.core.exception;

import org.yzkx.secin.core.HttpCode;

@SuppressWarnings("serial")
public class NotFoundException extends BaseException {

	public NotFoundException() {
	}

	public NotFoundException(Throwable ex) {
		super(ex);
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(String message, Throwable ex) {
		super(message, ex);
	}
	
	@Override
	protected HttpCode getHttpCode() {
		return HttpCode.NOT_FOUND;
	}

}
