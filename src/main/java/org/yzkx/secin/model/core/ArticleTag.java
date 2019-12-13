package org.yzkx.secin.model.core;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ArticleTag implements Serializable {

	private Long id;
	private Long articleId;
	private Long tagId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getArticleId() {
		return articleId;
	}
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
	public Long getTagId() {
		return tagId;
	}
	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

}
