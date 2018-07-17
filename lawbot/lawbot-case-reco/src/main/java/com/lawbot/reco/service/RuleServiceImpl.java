package com.lawbot.reco.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawbot.reco.dao.RuleDao;

@Service
public class RuleServiceImpl implements RuleService {
	
	@Autowired
	private RuleDao ruleDao;

	@Override
	public List<Map> findWithLawByKeys(List<String> keys) {
		return ruleDao.findWithLawByKeys(keys);
	}

}
