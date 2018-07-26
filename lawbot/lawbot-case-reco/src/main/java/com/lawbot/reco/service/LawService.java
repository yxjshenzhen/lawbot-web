package com.lawbot.reco.service;
/**
 * 
 * @author Cloud Lau
 *
 */

import com.alibaba.fastjson.JSONObject;

public interface LawService {
	
	JSONObject getLaws(String area); 
}
