package org.yzkx.secin.core.util;

import java.util.Map;

import org.yzkx.secin.core.Constants;
import org.yzkx.secin.model.Pagination;

public class PaginationUtil {

	public static void servicePaging(Map<String, Object> params, Pagination pagination) {
		params.put("from", pagination.getFrom());
		params.put("pageSize", pagination.getPageSize());
	}
	
	public static Pagination controllerPaging(Map<String, Object> params, Integer total, Integer max) {
		if (total != null && total > max) total = max;
		return controllerPaging(params, total);
	}
	
	public static Pagination controllerPaging(Map<String, Object> params, Integer total) {
		if (total == null) total = 0;
		Integer page = TypeParseUtil.convertToInteger(params.get("page"));
		if (page == null) page = 1;
		Integer pageSize = TypeParseUtil.convertToInteger(params.get("pageSize"));
		if (pageSize == null) pageSize = Constants.DEFAULT_PAGE_SIZE;
		Integer totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
		if (page > totalPage) page = totalPage;
		
		return new Pagination(page, pageSize, total);
	}

}
