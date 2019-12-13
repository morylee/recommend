package org.yzkx.secin.mapper.recommend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.yzkx.secin.model.recommend.FavoriteTag;

@Mapper
public interface FavoriteTagMapper {

	public void addIgnoreIndex(FavoriteTag favoriteTag);
	public void updateBatchIgnoreIndex(List<FavoriteTag> favoriteTags);
	public List<FavoriteTag> select(Map<String, Object> params);

}
