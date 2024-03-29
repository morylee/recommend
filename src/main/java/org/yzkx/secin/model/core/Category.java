package org.yzkx.secin.model.core;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Category implements Serializable {

	private Long id;
	private Long parentId;
	private String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
