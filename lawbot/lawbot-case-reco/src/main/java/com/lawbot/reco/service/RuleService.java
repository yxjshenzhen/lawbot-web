package com.lawbot.reco.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author Cloud Lau
 *
 */
public interface RuleService {
	
	List<String> getCaseKeys(Map<String,Object> params);
	
	List<String> getCaseKeysOnCached(Map<String, Object> params) throws InterruptedException, ExecutionException;

	List<Map> findWithLawByKeys(List<String> keys);
	
	List<Map> findWithLawByKeysOnCached(List<String> keys) throws InterruptedException, ExecutionException;
	
	List<Map> findCaseLawByCaseId(long caseId);
	
	JSONObject findSameCases(List<String> keys);
	
	JSONObject findSameCasesOnCached(List<String> keys) throws InterruptedException, ExecutionException;
		
	JSONObject fetchStat(JSONObject params);

	//mmht
	
	List<String> getCaseKeysMmht(Map<String,Object> params);
	
	List<String> getCaseKeysOnCachedMmht(Map<String, Object> params) throws InterruptedException, ExecutionException;

	List<Map> findWithLawByKeysMmht(List<String> keys);
	
	List<Map> findWithLawByKeysOnCachedMmht(List<String> keys) throws InterruptedException, ExecutionException;
	
	List<Map> findCaseLawByCaseIdMmht(long caseId);
	
	JSONObject findSameCasesMmht(List<String> keys);
}
