package org.yzkx.secin.service.recommend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzkx.secin.core.Constants;
import org.yzkx.secin.mapper.recommend.SkilledCategoryMapper;
import org.yzkx.secin.model.core.Category;
import org.yzkx.secin.model.recommend.SkilledCategory;
import org.yzkx.secin.service.core.CategoryService;

@Service
public class SkilledCategoryService {

	@Autowired
	private SkilledCategoryMapper mapper;
	
	@Autowired
	private CategoryService categoryService;
	
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
	
	public Map<Long, Double> skillMap(Long userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		List<SkilledCategory> skilledCategories = this.select(params);
		Map<Long, Double> skillMap = new HashMap<>();
		for (SkilledCategory sc: skilledCategories) {
			skillMap.put(sc.getCategoryId(), sc.getValue());
		}
		
		return skillMap;
	}
	
	public List<Map<String, Object>> skillList(Long userId, Integer type) {
		Map<String, Object> params = new HashMap<>();
		params.put("parentId", 0);
		List<Category> parents = categoryService.select(params);
		
		params.clear();
		params.put("notParentId", 0);
		params.put("type", type);
		List<Category> categories = categoryService.select(params);
		
		Map<Long, Double> skillMap = this.skillMap(userId);
		
		Map<Long, List<Map<String, Object>>> childrenMap = new HashMap<>();
		Map<Long, Integer> skilledCountMap = new HashMap<>();
		Long parentId;
		List<Map<String, Object>> children;
		Integer skilledCount;
		Map<String, Object> child;
		Double value;
		for (Category category: categories) {
			parentId = category.getParentId();
			children = childrenMap.get(parentId);
			skilledCount = skilledCountMap.get(parentId);
			if (children == null) children = new ArrayList<Map<String,Object>>();
			if (skilledCount == null) skilledCount = 0;
			
			child = new HashMap<>();
			child.put("id", category.getId());
			child.put("name", category.getName());
			value = skillMap.get(category.getId());
			if (value == null) value = 0.0;
			child.put("value", value);
			if (value > Constants.MIN_SKILLED_CATEGORY_VALUE) skilledCount++;
			
			children.add(child);
			childrenMap.put(parentId, children);
			skilledCountMap.put(parentId, skilledCount);
		}
		
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> parent;
		for (Category category: parents) {
			children = childrenMap.get(category.getId());
			if (children == null) children = new ArrayList<>();
			skilledCount = skilledCountMap.get(category.getId());
			
			parent = new HashMap<>();
			parent.put("id", category.getId());
			parent.put("name", category.getName());
			parent.put("total", children.size());
			parent.put("skilled", skilledCount == null ? 0 : skilledCount);
			parent.put("children", children);
			
			list.add(parent);
		}
		
		return list;
	}

}
