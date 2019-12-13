package org.yzkx.secin.service.recommend;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzkx.secin.mapper.recommend.SkilledCategoryMapper;
import org.yzkx.secin.model.recommend.SkilledCategory;

@Service
public class SkilledCategoryService {

	@Autowired
	private SkilledCategoryMapper mapper;
	
	public void addIgnoreIndex(SkilledCategory skilledCategory) {
		mapper.addIgnoreIndex(skilledCategory);
	}
	
	public List<SkilledCategory> select(Map<String, Object> params) {
		return mapper.select(params);
	}
	
	public void increase(Long userId, Long categoryId, Double value) {
		if (value == null || value <= 0) return;
		
		this.change(userId, categoryId, value);
	}
	
	public void change(Long userId, Long categoryId, Double value) {
		SkilledCategory skilledCategory = new SkilledCategory();
		skilledCategory.setUserId(userId);
		skilledCategory.setCategoryId(categoryId);
		skilledCategory.setValue(value);
		skilledCategory.setIsDelete(false);
		
		this.addIgnoreIndex(skilledCategory);
	}

}
