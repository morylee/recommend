package org.yzkx.secin.model;

import java.util.Map;

import org.yzkx.secin.core.Constants;

public class Pagination {

	private Integer page;
	private Integer pageSize;
	private Integer total;
	
	public Pagination() {
		
	}
	
	public Pagination(Integer page, Integer pageSize, Integer total) {
		this.page = page;
		this.pageSize = pageSize;
		this.total = total;
	}
	
	public Integer getPage() {
		return page == null ? 1 : page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageSize() {
		return pageSize == null ? Constants.DEFAULT_PAGE_SIZE : pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotal() {
		return total == null ? 0 : total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getFrom() {
		return this.getPageSize() * (this.getPage() - 1);
	}
	public void mergeTo(Map<String, Object> params) {
		params.put("page", this.getPage());
		params.put("pageSize", this.getPageSize());
		params.put("total", this.getTotal());
	}

}
