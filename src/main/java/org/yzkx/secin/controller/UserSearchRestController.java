package org.yzkx.secin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yzkx.secin.core.util.AssertUtil;
import org.yzkx.secin.core.util.PaginationUtil;
import org.yzkx.secin.core.util.TypeParseUtil;
import org.yzkx.secin.model.Pagination;
import org.yzkx.secin.model.recommend.UserSearch;
import org.yzkx.secin.service.recommend.UserSearchService;

@RestController
@RequestMapping("/user/search")
public class UserSearchRestController extends BaseRestController {

	@Autowired
	private UserSearchService userSearchService;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, headers = "Accept=application/json")
	public Object create(ModelMap modelMap, @RequestBody Map<String, Object> params, HttpServletRequest request) {
		Long userId = TypeParseUtil.convertToLong(params.get("userId"));
		String keyword = TypeParseUtil.convertToString(params.get("keyword"));
		AssertUtil.isNull(userId, "userId");
		AssertUtil.isBlank(keyword, "keyword");
		AssertUtil.length(keyword, 0, 100, "keyword");
		
		userSearchService.add(userId, keyword);
		
		return setSuccessModelMap(modelMap, true);
	}
	
	@RequestMapping(value = "/history", method = RequestMethod.POST, headers = "Accept=application/json")
	public Object history(ModelMap modelMap, @RequestBody Map<String, Object> params, HttpServletRequest request) {
		Long userId = TypeParseUtil.convertToLong(params.get("userId"));
		
		Integer total = userSearchService.countHistory(userId);
		Pagination pagination = PaginationUtil.controllerPaging(params, total, UserSearch.MAX_HISTORY);
		List<String> history = userSearchService.selectHistory(userId, pagination);
		Map<String, Object> result = new HashMap<>();
		result.put("history", history);
		pagination.mergeTo(result);
		
		return setSuccessModelMap(modelMap, result);
	}

}
