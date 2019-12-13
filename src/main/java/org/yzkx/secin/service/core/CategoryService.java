package org.yzkx.secin.service.core;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzkx.secin.mapper.core.CategoryMapper;
import org.yzkx.secin.model.core.Category;

@Service
public class CategoryService {

	@Autowired
	private CategoryMapper mapper;
	
	public List<Category> select(Map<String, Object> params) {
		return mapper.select(params);
	}

}
