package org.yzkx.secin.service.recommend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzkx.secin.core.util.PaginationUtil;
import org.yzkx.secin.mapper.recommend.UserSearchMapper;
import org.yzkx.secin.model.Pagination;
import org.yzkx.secin.model.recommend.UserSearch;

@Service
public class UserSearchService {

	@Autowired
	private UserSearchMapper mapper;
	
	public void add(UserSearch userSearch) {
		mapper.add(userSearch);
	}
	
	public void update(UserSearch userSearch) {
		mapper.update(userSearch);
	}
	
	public UserSearch find(Map<String, Object> params) {
		return mapper.find(params);
	}
	
	public void add(Long userId, String keyword) {
		if (userId == null || StringUtils.isBlank(keyword)) return;
		
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("keyword", keyword);
		UserSearch userSearch = this.find(params);
		if (userSearch == null) {
			userSearch = new UserSearch();
			userSearch.setUserId(userId);
			userSearch.setKeyword(keyword);
			this.add(userSearch);
		} else {
			this.update(userSearch);
		}
	}
	
	public List<String> selectHistory(Map<String, Object> params) {
		return mapper.selectHistory(params);
	}
	
	public Integer countHistory(Map<String, Object> params) {
		return mapper.countHistory(params);
	}
	
	public List<String> selectHistory(Long userId, Pagination pagination) {
		if (userId == null) return new ArrayList<String>();
		
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		PaginationUtil.servicePaging(params, pagination);
		
		return this.selectHistory(params);
	}
	
	public Integer countHistory(Long userId) {
		if (userId == null) return 0;
		
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		
		return this.countHistory(params);
	}

}
