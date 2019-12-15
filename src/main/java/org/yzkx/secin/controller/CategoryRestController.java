package org.yzkx.secin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yzkx.secin.core.Constants;
import org.yzkx.secin.core.exception.IllegalParameterException;
import org.yzkx.secin.core.quartz.JobInfo;
import org.yzkx.secin.core.quartz.JobManager;
import org.yzkx.secin.core.util.AssertUtil;
import org.yzkx.secin.core.util.DateUtil;
import org.yzkx.secin.core.util.TypeParseUtil;

@RestController
@RequestMapping("/category")
public class CategoryRestController extends BaseRestController {

	@Autowired
	private JobManager jobManager;
	
	@RequestMapping(value = "/favorite", method = RequestMethod.POST, headers = "Accept=application/json")
	public Object param(ModelMap modelMap, @RequestBody Map<String, Object> params, HttpServletRequest request) {
		Long userId;
		List<Long> categoryIds;
		try {
			userId = TypeParseUtil.convertToLong(params.get("userId"));
			AssertUtil.isNull(userId, "userId");
			
			JSONObject json = new JSONObject(params);
			categoryIds = new ArrayList<>();
			if (json.has("categoryIds")) {
				JSONArray array = json.getJSONArray("categoryIds");
				for (int i = 0; i < array.length(); i++) {
					categoryIds.add(TypeParseUtil.convertToLong(array.get(i)));
				}
			}
			AssertUtil.length(categoryIds, 1, null, "categoryIds");
		} catch (JSONException e) {
			throw new IllegalParameterException("params error");
		}
		
		String uniqueCode = jobManager.uniqueCode(userId, categoryIds.hashCode());
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("userId", userId);
		dataMap.put("categoryIds", categoryIds);
		
		jobManager.addJob(JobInfo.FavoriteCategory, uniqueCode,
			DateUtil.getSecondAfter(new Date(), Constants.TASK_EXECUTE_DELAY_SECONDS), dataMap);
		
		return setSuccessModelMap(modelMap, true);
	}

}
