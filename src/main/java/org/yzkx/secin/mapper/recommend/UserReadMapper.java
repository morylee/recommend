package org.yzkx.secin.mapper.recommend;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.yzkx.secin.model.recommend.UserRead;

@Mapper
public interface UserReadMapper {

	public void add(UserRead userRead);
	public void update(UserRead userRead);
	public UserRead find(Map<String, Object> params);

}
