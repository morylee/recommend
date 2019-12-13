package org.yzkx.secin.model.recommend;

public enum FavoriteDowngrade {

	Keep30Day(30, -0.2d, -0.1d), Keep40Day(40, -0.3d, -0.2d), Keep50Day(50, -0.4d, -0.3d), Keep60Day(60, -0.5d, -0.4d);
	
	private final Integer value;
	private final Double category;
	private final Double tag;
	
	private FavoriteDowngrade(Integer value, Double category, Double tag) {
		this.value = value;
		this.category = category;
		this.tag = tag;
	}
	
	public Integer getValue() {
		return this.value;
	}
	
	public Double getCategory() {
		return this.category;
	}
	
	public Double getTag() {
		return this.tag;
	}
	
	public static FavoriteDowngrade valueOf(Integer value) {
		switch (value) {
		case 40:
			return Keep40Day;
		case 50:
			return Keep50Day;
		case 60:
			return Keep60Day;
		default:
			return Keep30Day;
		}
	}
	
	public static FavoriteDowngrade nextGrade(Integer value) {
		switch (value) {
		case 40:
			return Keep50Day;
		case 50:
			return Keep60Day;
		case 60:
			return null;
		default:
			return Keep40Day;
		}
	}

}
