package org.yzkx.secin.mapper.core;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.yzkx.secin.model.core.Category;

@Mapper
public interface CategoryMapper {

	public List<Category> select(Map<String, Object> params);

}
