package org.yzkx.secin.service.recommend;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzkx.secin.core.Constants;
import org.yzkx.secin.core.util.DateUtil;
import org.yzkx.secin.mapper.recommend.FavoriteTagMapper;
import org.yzkx.secin.model.recommend.FavoriteDowngrade;
import org.yzkx.secin.model.recommend.FavoriteTag;

@Service
public class FavoriteTagService {

	@Autowired
	private FavoriteTagMapper mapper;
	
	public void addIgnoreIndex(FavoriteTag favoriteTag) {
		mapper.addIgnoreIndex(favoriteTag);
	}
	
	public void updateBatchIgnoreIndex(List<FavoriteTag> favoriteTags) {
		mapper.updateBatchIgnoreIndex(favoriteTags);
	}
	
	public List<FavoriteTag> select(Map<String, Object> params) {
		return mapper.select(params);
	}
	
	public void increase(Long userId, Long categoryId, Double value) {
		if (value == null || value <= 0) return;
		
		this.change(userId, categoryId, value);
	}
	
	public void change(Long userId, Long categoryId, Double value) {
		FavoriteTag favoriteTag = new FavoriteTag();
		favoriteTag.setUserId(userId);
		favoriteTag.setTagId(categoryId);
		favoriteTag.setValue(value);
		favoriteTag.setIsDelete(false);
		
		this.addIgnoreIndex(favoriteTag);
	}
	
	public void batchChange(Map<String, Object> params, Double value) {
		List<FavoriteTag> favoriteTags = this.select(params);
		for (FavoriteTag ft: favoriteTags) {
			ft.setValue(value);
		}
		
		this.updateBatchIgnoreIndex(favoriteTags);
	}
	
	public void autoBatchDecrease(Integer dayFrom, Integer dayTo, Double value) {
		if (dayFrom == null || dayFrom.intValue() < FavoriteDowngrade.Keep30Day.getValue().intValue()) return;
		if (value == null || value >= 0) return;
		
		Map<String, Object> params = new HashMap<>();
		params.put("valueFrom", Constants.MIN_FAVORITE_TAG_VALUE - value);
		params.put("isDelete", false);
		Date curr = new Date();
		if (dayFrom != null) params.put("updateFrom", DateUtil.getDayBefore(curr, dayFrom));
		if (dayTo != null) params.put("updateTo", DateUtil.getDayBefore(curr, dayTo));
		
		this.batchChange(params, value);
	}
	
	public void autoBatchDecrease(FavoriteDowngrade downgrade) {
		if (downgrade == null) return;
		FavoriteDowngrade nextDowngrade = FavoriteDowngrade.nextGrade(downgrade.getValue());
		Integer dayFrom = nextDowngrade == null ? null : nextDowngrade.getValue();
		
		this.autoBatchDecrease(dayFrom, downgrade.getValue(), downgrade.getTag());
	}

}
