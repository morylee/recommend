package org.yzkx.secin.mapper.recommend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.yzkx.secin.model.recommend.UserSearch;

@Mapper
public interface UserSearchMapper {

	public void add(UserSearch userSearch);
	public void update(UserSearch userSearch);
	public UserSearch find(Map<String, Object> params);
	public List<String> selectHistory(Map<String, Object> params);
	public Integer countHistory(Map<String, Object> params);

}
