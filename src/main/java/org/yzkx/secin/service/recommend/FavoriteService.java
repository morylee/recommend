package org.yzkx.secin.service.recommend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	private UserReadService userReadService;
	
	@Autowired
	private FavoriteCategoryService favCateService;
	
	@Autowired
	private SkilledCategoryService skdCateService;
	
	@Autowired
	private FavoriteTagService favTagService;
	
	@Transactional
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
	
	@Transactional
	public void articleFavorite(Integer action, Long userId, Long articleId) {
		ClientAction clientAction = ClientAction.valueOf(action);
		if (clientAction == null) return;
		
		Article article = articleService.findById(articleId);
		if (article == null) return;
		
		Double ratio = this.favoriteRatio(action, userId, articleId);
		if (ratio == null || ratio.doubleValue() == 0) return;
		
		Double fc = clientAction.getFavoriteCategory();
		Double sc = clientAction.getSkilledCategory();
		Double ft = clientAction.getFavoriteTag();
		if (fc != null && fc > 0)
			favCateService.increase(userId, article.getCategory2ndId(), fc * ratio);
		if (sc != null && sc > 0)
			skdCateService.increase(userId, article.getCategory2ndId(), sc * ratio);
		List<Long> tagIds = articleTagService.selectIdsByArticle(articleId);
		if (ft != null && ft > 0) {
			List<FavoriteTag> favoriteTags = new ArrayList<>();
			FavoriteTag favoriteTag;
			for (Long tagId: tagIds) {
				favoriteTag = new FavoriteTag();
				favoriteTag.setUserId(userId);
				favoriteTag.setTagId(tagId);
				favoriteTag.setIsDelete(false);
				favoriteTag.setValue(ft * ratio);
				
				favoriteTags.add(favoriteTag);
			}
			if (favoriteTags.size() > 0) favTagService.updateBatchIgnoreIndex(favoriteTags);
		}
	}
	
	public Double favoriteRatio(Integer action, Long userId, Long articleId) {
		if (action.intValue() == ClientAction.ViewArticle.getValue().intValue()) {
			return userReadService.view(userId, articleId);
		} else if (action.intValue() == ClientAction.StartReadArticle.getValue().intValue()) {
			userReadService.startRead(userId, articleId);
			return 0.0;
		} else if (action.intValue() == ClientAction.EndReadArticle.getValue().intValue()) {
			return userReadService.endRead(userId, articleId);
		} else {
			return 1.0;
		}
	}

}
