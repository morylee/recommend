package org.yzkx.secin.service.recommend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzkx.secin.model.core.Article;
import org.yzkx.secin.model.core.Category;
import org.yzkx.secin.model.recommend.ClientAction;
import org.yzkx.secin.model.recommend.FavoriteCategory;
import org.yzkx.secin.model.recommend.FavoriteTag;
import org.yzkx.secin.service.core.ArticleService;
import org.yzkx.secin.service.core.ArticleTagService;
import org.yzkx.secin.service.core.CategoryService;

@Service
public class FavoriteService {

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ArticleTagService articleTagService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private FavoriteCategoryService favCateService;
	
	@Autowired
	private SkilledCategoryService skdCateService;
	
	@Autowired
	private FavoriteTagService favTagService;
	
	public void categoryFavorite(Long userId, List<Long> categoryIds) {
		if (categoryIds == null || categoryIds.size() == 0) return;
		Double value = ClientAction.LikeCategory.getFavoriteCategory();
		if (value == null || value <= 0) return;
		
		Map<String, Object> params = new HashMap<>();
		params.put("parentIds", categoryIds);
		List<Category> categories = categoryService.select(params);
		
		List<FavoriteCategory> favoriteCategories = new ArrayList<>();
		FavoriteCategory favoriteCategory;
		for (Category category: categories) {
			favoriteCategory = new FavoriteCategory();
			favoriteCategory.setUserId(userId);
			favoriteCategory.setCategoryId(category.getId());
			favoriteCategory.setIsDelete(false);
			favoriteCategory.setValue(value);
			
			favoriteCategories.add(favoriteCategory);
		}
		if (favoriteCategories.size() > 0) favCateService.updateBatchIgnoreIndex(favoriteCategories);
	}
	
	public void articleFavorite(Integer action, Long userId, Long articleId) {
		ClientAction clientAction = ClientAction.valueOf(action);
		if (clientAction == null) return;
		
		Article article = articleService.findById(articleId);
		if (article == null) return;
		
		if (clientAction.getFavoriteCategory() != null && clientAction.getFavoriteCategory() > 0)
			favCateService.increase(userId, article.getCategory2ndId(), clientAction.getFavoriteCategory());
		if (clientAction.getSkilledCategory() != null && clientAction.getSkilledCategory() > 0)
			skdCateService.increase(userId, article.getCategory2ndId(), clientAction.getSkilledCategory());
		List<Long> tagIds = articleTagService.selectIdsByArticle(articleId);
		if (clientAction.getFavoriteTag() != null && clientAction.getFavoriteTag() > 0) {
			List<FavoriteTag> favoriteTags = new ArrayList<>();
			FavoriteTag favoriteTag;
			for (Long tagId: tagIds) {
				favoriteTag = new FavoriteTag();
				favoriteTag.setUserId(userId);
				favoriteTag.setTagId(tagId);
				favoriteTag.setIsDelete(false);
				favoriteTag.setValue(clientAction.getFavoriteTag());
				
				favoriteTags.add(favoriteTag);
			}
			if (favoriteTags.size() > 0) favTagService.updateBatchIgnoreIndex(favoriteTags);
		}
	}

}
