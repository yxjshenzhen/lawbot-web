package com.lawbot.reco.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawbot.core.entity.result.Result;
import com.lawbot.core.entity.result.ResultCode;
import com.lawbot.core.entity.result.Result.ResultData;
import com.lawbot.reco.dao.LawDao;
import com.lawbot.reco.remote.AIService;
import com.lawbot.reco.service.RuleService;

/**
 * 
 * @author Cloud Lau
 *
 */
@RestController
@RequestMapping("/")
public class MainController {
	
	@Autowired
	private AIService aiService;
	
	@Autowired
	private RuleService ruleService;

	/**
	 * 
	 * @param params
	 * @return
	 */
	@PostMapping("case-keys")
	public Result calcFactor(@RequestBody(required = true) Map<String, Object> params){
		List<String> factors = aiService.getCaseKeys(params);
		return Result.success(new Result.ResultData("caseKeys", factors));
	}
	
	@GetMapping("case-laws/{caseId}")
	public Result getCaseLaws(@PathVariable(required = true) String caseId){
		List rs = aiService.getCaseLaws(caseId);
		return Result.success(new Result.ResultData("caseLaws", rs));
	}
	
	@PostMapping("case-rules")
	public Result getCaseRules(@RequestBody(required = true) Map<String,Object> params){
		if(params.containsKey("keys") && params.get("keys") instanceof List){
			return Result.success(new Result.ResultData("caseRules",ruleService.findWithLawByKeys((List)params.get("keys"))));
		}
		return Result.error(ResultCode.REQUEST_PARAMS_ERROR);
	}
	
	@PostMapping("case-same")
	public Object getSameCases(@RequestBody(required = true) Map<String,Object> params){
		return Result.success(new Result.ResultData("sameCases",aiService.getSameCases(params)));
	}
	
	@GetMapping("case-detail/{caseId}")
	public Object getCaseDetail(@PathVariable(required = true) String caseId){
		return Result.success(new Result.ResultData("caseDetail",aiService.getCaseDetail(caseId)));
	}
	
	
}
