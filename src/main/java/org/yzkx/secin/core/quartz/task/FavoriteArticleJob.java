package org.yzkx.secin.core.quartz.task;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yzkx.secin.service.recommend.FavoriteService;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class FavoriteArticleJob implements Job {

	@Autowired
	private FavoriteService favoriteService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		Integer action = dataMap.getInt("action");
		Long userId = dataMap.getLong("userId");
		Long articleId = dataMap.getLong("articleId");
		
		favoriteService.articleFavorite(action, userId, articleId);
	}

}
