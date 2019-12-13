package org.yzkx.secin.mapper.recommend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.yzkx.secin.model.recommend.FavoriteCategory;

@Mapper
public interface FavoriteCategoryMapper {

	public void addIgnoreIndex(FavoriteCategory favoriteCategory);
	public void updateBatchIgnoreIndex(List<FavoriteCategory> favoriteCategories);
	public List<FavoriteCategory> select(Map<String, Object> params);

}
