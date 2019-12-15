package org.yzkx.secin.service.recommend;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzkx.secin.mapper.recommend.UserReadMapper;
import org.yzkx.secin.model.recommend.UserRead;

@Service
public class UserReadService {

	@Autowired
	private UserReadMapper mapper;

	public void add(UserRead userRead) {
		mapper.add(userRead);
	}
	
	public void update(UserRead userRead) {
		mapper.update(userRead);
	}
	
	public Double view(Long userId, Long articleId) {
		UserRead userRead = this.find(userId, articleId);
		if (userRead == null) {
			userRead = new UserRead();
			userRead.setUserId(userId);
			userRead.setArticleId(articleId);
			userRead.setViewTimes(1);
			userRead.setReadTimes(0);
			userRead.setReadTotalTime(0l);
			this.add(userRead);
			return 1.0;
		} else {
			Integer viewTimes = userRead.getViewTimes() + 1;
			userRead.setViewTimes(viewTimes);
			this.update(userRead);
			return viewTimes > UserRead.MAX_EFFECTIVE_READ ? 0.0 : 1.0 / UserRead.REPEAT_READ_INTERVAL;
		}
	}
	
	public void startRead(Long userId, Long articleId) {
		UserRead userRead = this.find(userId, articleId);
		if (userRead == null) return;
		
		userRead.setReadTimes(userRead.getReadTimes() + 1);
		if (userRead.getReadingAt() == null) userRead.setReadingAt(new Date());
		this.update(userRead);
	}
	
	public Double endRead(Long userId, Long articleId) {
		Double ratio = 0d;
		UserRead userRead = this.find(userId, articleId);
		if (userRead != null && userRead.getReadingAt() != null) {
			Long readTotalTime = userRead.getReadTotalTime();
			Date startAt = userRead.getReadingAt();
			Date endAt = new Date();
			
			if (userRead.getReadTimes() <= UserRead.MAX_EFFECTIVE_READ)
				ratio = readTotalTime == 0 ? 1.0 : 1.0 / UserRead.REPEAT_READ_INTERVAL;
			
			userRead.setReadingAt(null);
			userRead.setReadStartAt(startAt);
			userRead.setReadEndAt(endAt);
			Long readTime = endAt.getTime() - startAt.getTime();
			userRead.setReadTotalTime(readTotalTime + readTime);
			this.update(userRead);
		}
		
		return ratio;
	}
	
	public UserRead find(Map<String, Object> params) {
		return mapper.find(params);
	}
	
	public UserRead find(Long userId, Long articleId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("articleId", articleId);
		
		return this.find(params);
	}

}
