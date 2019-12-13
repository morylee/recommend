package org.yzkx.secin.mapper.recommend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.yzkx.secin.model.recommend.SkilledCategory;

@Mapper
public interface SkilledCategoryMapper {

	public void addIgnoreIndex(SkilledCategory skilledCategory);
	public List<SkilledCategory> select(Map<String, Object> params);

}
