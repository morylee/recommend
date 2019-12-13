package org.yzkx.secin.service.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzkx.secin.mapper.core.ArticleTagMapper;

@Service
public class ArticleTagService {

	@Autowired
	private ArticleTagMapper mapper;
	
	public List<Long> selectIds(Map<String, Object> params) {
		return mapper.selectIds(params);
	}
	
	public List<Long> selectIdsByArticle(Long articleId) {
		if (articleId == null) return new ArrayList<>();
		
		Map<String, Object> params = new HashMap<>();
		params.put("articleId", articleId);
		return this.selectIds(params);
	}

}
