package org.yzkx.secin.core.exception;

import org.apache.commons.lang3.StringUtils;
import org.yzkx.secin.core.HttpCode;
import org.springframework.ui.ModelMap;

/**
 * 
 * @author LiChenhui
 */
@SuppressWarnings("serial")
public abstract class BaseException extends RuntimeException {

	public BaseException() {
	}
	
	public BaseException(Throwable ex) {
		super(ex);
	}
	
	public BaseException(String message) {
		super(message);
	}
	
	public BaseException(String message, Throwable ex) {
		super(message, ex);
	}
	
	public void handler(ModelMap modelMap) {
		modelMap.put("httpCode", getHttpCode().value());
		if (StringUtils.isNotBlank(getMessage())) {
			modelMap.put("msg", getMessage());
		} else {
			modelMap.put("msg", getHttpCode().msg());
		}
		modelMap.put("timestamp", System.currentTimeMillis());
	}
	
	protected abstract HttpCode getHttpCode();

}
