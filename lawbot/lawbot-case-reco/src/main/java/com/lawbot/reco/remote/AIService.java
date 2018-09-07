package com.lawbot.reco.remote;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
 * api for connect ai server
 * @author Cloud Lau
 *
 */
@FeignClient(value = "aiservice")
public interface AIService {

	/**
	 * 获取关键因子
	 * @param params {case_content: ""}
	 * @return ["",""]
	 */
	@PostMapping(path = "get_case_keys" , consumes = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	List<String> getCaseKeys(@RequestBody Map<String,Object> params);
	
	/**
	 * 获取关键因子
	 * @param params {case_content: ""}
	 * @return ["",""]
	 */
	@PostMapping(path = "get_case_keys_contract" , consumes = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	List<String> getCaseKeysMmht(@RequestBody Map<String,Object> params);
	
	/**
	 * 获取法条
	 * @param caseId
	 * @return [{},{}]
	 */
	@GetMapping(value = "get_case_laws")
	List<JSONObject> getCaseLaws(@RequestParam("case_id") String caseId);
	
	
	/**
	 * 获取引用规则
	 * @param params {keys: ["", "",""]}
	 * @return { "rules": ["",""]}
	 */
	@PostMapping(value = "get_case_rules" , consumes = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	JSONObject getCaseRules(@RequestBody Map<String,Object> params);
	
	/**
	 * 
	 * @param params {keys: ["" , ""]}
	 * @return {"xxx": [],"yyy": []}
	 */
	@PostMapping(value = "get_same_cases" , consumes = {MediaType.APPLICATION_JSON_VALUE})
	JSONObject getSameCases(@RequestBody Map<String,Object> params);
	
	
	/**
	 * 
	 * @param caseId
	 * @return {}
	 */
	@GetMapping(value = "get_case_detail")
	JSONObject getCaseDetail(@RequestParam("case_id") String caseId); 
	
	
	
}
