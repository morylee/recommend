package org.yzkx.secin.core.quartz;

import org.quartz.Job;
import org.yzkx.secin.core.quartz.task.FavoriteArticleJob;
import org.yzkx.secin.core.quartz.task.FavoriteCategoryJob;

public enum JobInfo {

	FavoriteArticle("ARTICLE_JOB", "ARTICLE_GROUP", "FAVORITE_TRIGGER", FavoriteArticleJob.class),
	FavoriteCategory("CATEGORY_JOB", "CATEGORY_GROUP", "FAVORITE_TRIGGER", FavoriteCategoryJob.class);
	
	private final String name;
	private final String groupName;
	private final String triggerName;
	private final Class<? extends Job> clazz;
	
	private JobInfo(String name, String groupName, String triggerName, Class<? extends Job> clazz) {
		this.name = name;
		this.groupName = groupName;
		this.triggerName = triggerName;
		this.clazz = clazz;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getGroupName() {
		return this.groupName;
	}
	
	public String getTriggerName() {
		return this.triggerName;
	}
	
	public Class<? extends Job> getClazz() {
		return this.clazz;
	}

}
