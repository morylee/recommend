package org.yzkx.secin.mapper.core;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.yzkx.secin.model.core.Article;

@Mapper
public interface ArticleMapper {

	public Article findById(Long id);
	public List<Long> selectIds(Map<String, Object> params);

}
