package org.yzkx.secin.core.util;

import java.util.List;

import org.yzkx.secin.core.exception.IllegalParameterException;

public class AssertUtil {

	public static void isNull(Object object, String key) {
		if (object == null) {
			throw new IllegalParameterException(error("param: %s is null", key));
		}
	}
	
	public static void isBlank(String str, String key) {
		if (str == null || "".equals(str.trim())) {
			throw new IllegalParameterException(error("param: %s is blank", key));
		}
	}
	
	public static void length(String str, Integer min, Integer max, String key) {
		if (min != null && str.length() < min) {
			throw new IllegalParameterException(error("param: %s's length less than %d", key, min));
		}
		if (max != null && str.length() > max) {
			throw new IllegalParameterException(error("param: %s's length more than %d", key, max));
		}
	}
	
	public static void length(List<Long> list, Integer min, Integer max, String key) {
		if (min != null && list.size() < min) {
			throw new IllegalParameterException(error("param: %s's length less than %d", key, min));
		}
		if (max != null && list.size() > max) {
			throw new IllegalParameterException(error("param: %s's length more than %d", key, max));
		}
	}
	
	public static void contains(Integer item, List<Integer> list, String key) {
		if (!list.contains(item)) {
			throw new IllegalParameterException(error("param: %s not in range", key));
		}
	}
	
	public static String error(String format, Object... args) {
		return String.format(format, args);
	}

}
