package org.yzkx.secin.model.core;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Article implements Serializable {

	private Long id;
	private Long categoryId;
	private Long category2ndId;
	private String articleTitle;
	private Double popularValue;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getCategory2ndId() {
		return category2ndId;
	}
	public void setCategory2ndId(Long category2ndId) {
		this.category2ndId = category2ndId;
	}
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public Double getPopularValue() {
		return popularValue;
	}
	public void setPopularValue(Double popularValue) {
		this.popularValue = popularValue;
	}

}
