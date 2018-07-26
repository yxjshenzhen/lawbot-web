package com.lawbot.reco.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lawbot.reco.dao.RuleDao;
import com.lawbot.reco.remote.AIService;
import com.lawbot.reco.speed.FutureCachedLoader;
import com.lawbot.reco.speed.FutureCachedRunnable;

@Service
public class RuleServiceImpl implements RuleService {
	
	@Autowired
	private RuleDao ruleDao;
	
	@Autowired
	private AIService aiService;
	
	private FutureCachedLoader<List<Map>> futureLoader = new FutureCachedLoader<List<Map>>();
	
	private FutureCachedLoader<List<String>> caseKeysLoader = new FutureCachedLoader<List<String>>();
	
	public List<String> getCaseKeys(Map<String, Object> params){
		
		if(!params.containsKey("case_content")) return null;
		
		return aiService.getCaseKeys(params);
	}
	
	@Override
	public List<String> getCaseKeysOnCached(Map<String, Object> params) throws InterruptedException, ExecutionException {
		if(!params.containsKey("case_content")) return null;
		
		String caseContent = params.get("case_content").toString();
		
		return caseKeysLoader.load("CASEKYES_" + caseContent, (k) -> getCaseKeys(params)).get();
	}

	@Override
	public List<Map> findWithLawByKeys(List<String> keys) {
		return ruleDao.findWithLawByKeys(keys);
	}
	
	public List<Map> findWithLawByKeysOnCached(List<String> keys) throws InterruptedException, ExecutionException{
		String key = getCacheKey("CASELAW_", keys);
		
		return futureLoader.load(key, new FutureCachedRunnable<List<Map>>() {

			@Override
			public List<Map> run(String key) {
				return findWithLawByKeys(keys);
			}
		}).get();
	}
	

	@Override
	public List<Map> findCaseLawByCaseId(long caseId) {
		return ruleDao.findCaseLawByCaseId(caseId);
	}
	
	
	public JSONObject findSameCases(List<String> keys){
		JSONObject sameCases = new JSONObject();
		sameCases.put("cases_leve1", findSameCases(keys, 1));
		sameCases.put("cases_leve2", findSameCases(keys, 2));
		sameCases.put("cases_leve3", findSameCases(keys, 3));
		sameCases.put("cases_leve4", findSameCases(keys, 4));
		return sameCases;
	}
	
	private List<Map> findSameCases(List<String> keys , int courtLevel){
		return ruleDao.findSameCaseByKeys(keys, courtLevel);
	}

	@Override
	public JSONObject findSameCasesOnCached(List<String> keys) throws InterruptedException, ExecutionException {
	
		String key = getCacheKey("SAMECASE_" , keys);
		
		JSONObject sameCases = new JSONObject();
		
		Future<List<Map>> case1Future = futureLoader.load("1" + key , (k) -> findSameCases(keys, 1));	
		Future<List<Map>> case2Future = futureLoader.load("2" + key , (k) -> findSameCases(keys, 2));
		Future<List<Map>> case3Future = futureLoader.load("3" + key , (k) -> findSameCases(keys, 3));
		Future<List<Map>> case4Future = futureLoader.load("4" + key , (k) -> findSameCases(keys, 4));
		
		sameCases.put("cases_leve1", case1Future.get());
		sameCases.put("cases_leve2", case2Future.get());
		sameCases.put("cases_leve3", case3Future.get());
		sameCases.put("cases_leve4", case4Future.get());
		
		return sameCases;
	}
	
	
	private String getCacheKey(String prefix , List<String> keys){
		keys.sort((a , b) -> a.compareTo(b));
		return prefix + String.join(",", keys);
	}

	@Override
	public JSONObject fetchStat(JSONObject params) {
		
		
		String str1 = "[{\"name\": \"西藏\", \"value\": 5},{\"name\": \"青海\", \"value\": 1},{\"name\": \"宁夏\", \"value\": 44},{\"name\": \"海南\", \"value\": 1},{\"name\": \"甘肃\", \"value\": 36},{\"name\": \"贵州\", \"value\": 7}," +
				"{\"name\": \"新疆\", \"value\": 34},{\"name\": \"云南\", \"value\": 68},{\"name\": \"重庆\", \"value\": 42},{\"name\": \"吉林\", \"value\": 25},{\"name\": \"山西\", \"value\": 20},{\"name\": \"天津\", \"value\": 7}," + 
				"{\"name\": \"江西\", \"value\": 96},{\"name\": \"广西\", \"value\": 9},{\"name\": \"陕西\", \"value\": 98},{\"name\": \"黑龙江\", \"value\": 44},{\"name\": \"内蒙古\", \"value\": 18},{\"name\": \"安徽\", \"value\": 189}," +
				"{\"name\": \"北京\", \"value\": 0},{\"name\": \"福建\", \"value\": 357},{\"name\": \"上海\", \"value\": 1},{\"name\": \"湖北\", \"value\": 91},{\"name\": \"湖南\", \"value\": 141},{\"name\": \"四川\", \"value\": 108},{\"name\": \"辽宁\", \"value\": 68}," +
				"{\"name\": \"河北\", \"value\": 112},{\"name\": \"河南\", \"value\": 200},{\"name\": \"浙江\", \"value\": 92},{\"name\": \"山东\", \"value\": 249},{\"name\": \"江苏\", \"value\": 195},{\"name\": \"广东\", \"value\": 23}]";
		String str2 = "[{\"name\": \"最高法院\", \"value\": 30},{\"name\": \"高级法院\", \"value\": 30}, {\"name\": \"中级法院\", \"value\": 2381},{\"name\": \"基层法院\", \"value\": 0}]";
		
		JSONObject rs = new JSONObject();
		rs.put("cities" , JSON.parse(str1));
		rs.put("levels", JSON.parse(str2));
		//please check type cast

		/*List<String> caseKeys = (List<String>) params.get("caseKeys");
		List<Integer> courtLevels = (List<Integer>) params.get("courtLevels");
		rs.put("cities", ruleDao.findCaseCitiesStats(caseKeys, courtLevels, 0.2f));
		rs.put("levels", ruleDao.findCaseCourtLevelStats(caseKeys, courtLevels, 0.2f));*/
		
		return rs;
	}

}
