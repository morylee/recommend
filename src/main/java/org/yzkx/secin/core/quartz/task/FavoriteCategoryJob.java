package org.yzkx.secin.core.quartz.task;

import java.util.List;

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
public class FavoriteCategoryJob implements Job {

	@Autowired
	private FavoriteService favoriteService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		Long userId = dataMap.getLong("userId");
		@SuppressWarnings("unchecked")
		List<Long> categoryIds = (List<Long>) dataMap.get("categoryIds");
		
		favoriteService.categoryFavorite(userId, categoryIds);
	}

}
