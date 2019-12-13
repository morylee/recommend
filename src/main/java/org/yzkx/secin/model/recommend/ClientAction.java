package org.yzkx.secin.model.recommend;

import java.util.ArrayList;
import java.util.List;

public enum ClientAction {

	PublishArticle(0, 0, "发布文章", 0d, 3d, 0d), PassArticle(1, 0, "审核通过文章", 3d, 0d, 0d),
	ViewArticle(2, 0, "浏览文章", 2d, 2d, 2d), LikeArticle(3, 0, "点赞文章", 3d, 0d, 2d),
	CollectArticle(4, 0, "收藏文章", 4d, 0d, 2d), CommentArticle(5, 0, "评论文章", 3d, 0d, 2d),
	StartReadArticle(6, 0, "开始沉浸式阅读文章", 2d, 0d, 2d), EndReadArticle(7, 0, "结束沉浸式阅读文章", 3d, 0d, 2d),
	LikeCategory(100, 10, "添加兴趣分类", 3d, 0d, 0d);
	
	private final Integer value;
	private final Integer type;
	private final String name;
	private final Double favoriteCategory;
	private final Double skilledCategory;
	private final Double favoriteTag;
	
	private ClientAction(Integer value, Integer type, String name, Double favoriteCategory, Double skilledCategory, Double favoriteTag) {
		this.value = value;
		this.type = type;
		this.name = name;
		this.favoriteCategory = favoriteCategory;
		this.skilledCategory = skilledCategory;
		this.favoriteTag = favoriteTag;
	}
	
	public Integer getValue() {
		return this.value;
	}
	
	public Integer getType() {
		return this.type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Double getFavoriteCategory() {
		return this.favoriteCategory;
	}
	
	public Double getSkilledCategory() {
		return this.skilledCategory;
	}
	
	public Double getFavoriteTag() {
		return this.favoriteTag;
	}
	
	public static ClientAction valueOf(Integer value) {
		switch (value) {
		case 0:
			return PublishArticle;
		case 1:
			return PassArticle;
		case 2:
			return ViewArticle;
		case 3:
			return LikeArticle;
		case 4:
			return CollectArticle;
		case 5:
			return CommentArticle;
		case 6:
			return StartReadArticle;
		case 7:
			return EndReadArticle;
		default:
			return null;
		}
	}
	
	public static final List<Integer> articleActionValue = new ArrayList<>();
	
	static {
		for (ClientAction action: ClientAction.values()) {
			switch (action.getType()) {
			case 0:
				articleActionValue.add(action.getValue());
				break;
			default:
				break;
			}
		}
	}

}
