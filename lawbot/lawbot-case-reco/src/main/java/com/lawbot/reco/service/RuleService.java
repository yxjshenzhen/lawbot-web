package com.lawbot.reco.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Cloud Lau
 *
 */
public interface RuleService {

	List<Map> findWithLawByKeys(List<String> keys);
}
