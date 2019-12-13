package org.yzkx.secin.service.recommend;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzkx.secin.core.Constants;
import org.yzkx.secin.core.util.DateUtil;
import org.yzkx.secin.mapper.recommend.FavoriteCategoryMapper;
import org.yzkx.secin.model.recommend.FavoriteCategory;
import org.yzkx.secin.model.recommend.FavoriteDowngrade;

@Service
public class FavoriteCategoryService {

	@Autowired
	private FavoriteCategoryMapper mapper;
	
	public void addIgnoreIndex(FavoriteCategory favoriteCategory) {
		mapper.addIgnoreIndex(favoriteCategory);
	}
	
	public void updateBatchIgnoreIndex(List<FavoriteCategory> favoriteCategories) {
		mapper.updateBatchIgnoreIndex(favoriteCategories);
	}
	
	public List<FavoriteCategory> select(Map<String, Object> params) {
		return mapper.select(params);
	}
	
	public void increase(Long userId, Long categoryId, Double value) {
		if (value == null || value <= 0) return;
		
		this.change(userId, categoryId, value);
	}
	
	public void change(Long userId, Long categoryId, Double value) {
		FavoriteCategory favoriteCategory = new FavoriteCategory();
		favoriteCategory.setUserId(userId);
		favoriteCategory.setCategoryId(categoryId);
		favoriteCategory.setValue(value);
		favoriteCategory.setIsDelete(false);
		
		this.addIgnoreIndex(favoriteCategory);
	}
	
	public void batchChange(Map<String, Object> params, Double value) {
		List<FavoriteCategory> favoriteCategories = this.select(params);
		for (FavoriteCategory fc: favoriteCategories) {
			fc.setValue(value);
		}
		
		this.updateBatchIgnoreIndex(favoriteCategories);
	}
	
	public void autoBatchDecrease(Integer dayFrom, Integer dayTo, Double value) {
		if (dayFrom == null || dayFrom.intValue() < FavoriteDowngrade.Keep30Day.getValue().intValue()) return;
		if (value == null || value >= 0) return;
		
		Map<String, Object> params = new HashMap<>();
		params.put("valueFrom", Constants.MIN_FAVORITE_CATEGORY_VALUE - value);
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
		
		this.autoBatchDecrease(dayFrom, downgrade.getValue(), downgrade.getCategory());
	}

}
