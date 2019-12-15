package org.yzkx.secin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.yzkx.secin.model.recommend.ClientAction;

@RestController
@RequestMapping("/article")
public class ArticleRestController extends BaseRestController {

	@Autowired
	private JobManager jobManager;
	
	@RequestMapping(value = "/favorite", method = RequestMethod.POST, headers = "Accept=application/json")
	public Object param(ModelMap modelMap, @RequestBody Map<String, Object> params, HttpServletRequest request) {
		Integer action;
		Long userId, articleId;
		try {
			action = TypeParseUtil.convertToInteger(params.get("action"));
			AssertUtil.isNull(action, "action");
			AssertUtil.contains(action, ClientAction.articleActionValue, "action");
			
			userId = TypeParseUtil.convertToLong(params.get("userId"));
			AssertUtil.isNull(userId, "userId");
			
			articleId = TypeParseUtil.convertToLong(params.get("articleId"));
			AssertUtil.isNull(articleId, "articleId");
		} catch (Exception e) {
			throw new IllegalParameterException("params error");
		}
		
		String uniqueCode = jobManager.uniqueCode(action, userId, articleId);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("action", action);
		dataMap.put("userId", userId);
		dataMap.put("articleId", articleId);
		
		jobManager.addJob(JobInfo.FavoriteArticle, uniqueCode,
			DateUtil.getSecondAfter(new Date(), Constants.TASK_EXECUTE_DELAY_SECONDS), dataMap);
		
		return setSuccessModelMap(modelMap, true);
	}

}
