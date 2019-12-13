package org.yzkx.secin.mapper.core;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleTagMapper {

	public List<Long> selectIds(Map<String, Object> params);

}
