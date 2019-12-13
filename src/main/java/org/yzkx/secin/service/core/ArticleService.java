package org.yzkx.secin.service.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzkx.secin.mapper.core.ArticleMapper;
import org.yzkx.secin.model.core.Article;

@Service
public class ArticleService {

	@Autowired
	private ArticleMapper mapper;
	
	public Article findById(Long id) {
		return mapper.findById(id);
	}
	
	public List<Long> selectIds(Map<String, Object> params) {
		return mapper.selectIds(params);
	}
	
	public List<Long> selectIdsByCategory(Long categoryId) {
		if (categoryId == null) return new ArrayList<>();
		
		Map<String, Object> params = new HashMap<>();
		params.put("categoryId", categoryId);
		
		return this.selectIds(params);
	}
	
	public List<Long> selectIdsByTag(Long tagId) {
		if (tagId == null) return new ArrayList<>();
		
		Map<String, Object> params = new HashMap<>();
		params.put("tagId", tagId);
		
		return this.selectIds(params);
	}

}
