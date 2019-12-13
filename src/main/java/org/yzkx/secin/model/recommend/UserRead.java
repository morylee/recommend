package org.yzkx.secin.model.recommend;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class UserRead implements Serializable {

	public static final Integer RECOMMEND_INTERVAL_TIMES = 10;
	public static final Integer SKILLED_INTERVAL_TIMES = 10;
	
	private Long id;
	private Long userId;
	private Long articleId;
	private Integer viewTimes;
	private Integer readTimes;
	private Date readingAt;
	private Date readStartAt;
	private Date readEndAt;
	private Long readTotalTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getArticleId() {
		return articleId;
	}
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
	public Integer getViewTimes() {
		return viewTimes;
	}
	public void setViewTimes(Integer viewTimes) {
		this.viewTimes = viewTimes;
	}
	public Integer getReadTimes() {
		return readTimes;
	}
	public void setReadTimes(Integer readTimes) {
		this.readTimes = readTimes;
	}
	public Date getReadingAt() {
		return readingAt;
	}
	public void setReadingAt(Date readingAt) {
		this.readingAt = readingAt;
	}
	public Date getReadStartAt() {
		return readStartAt;
	}
	public void setReadStartAt(Date readStartAt) {
		this.readStartAt = readStartAt;
	}
	public Date getReadEndAt() {
		return readEndAt;
	}
	public void setReadEndAt(Date readEndAt) {
		this.readEndAt = readEndAt;
	}
	public Long getReadTotalTime() {
		return readTotalTime;
	}
	public void setReadTotalTime(Long readTotalTime) {
		this.readTotalTime = readTotalTime;
	}

}
