package com.lawbot.reco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lawbot.reco.dao.LawDao;
import com.lawbot.reco.domain.Law;

/**
 * 
 * @author Cloud Lau
 *
 */
@Service
public class LawServiceImpl implements LawService {
	
	@Autowired
	private LawDao lawDao;

	@Override
	public JSONObject getLaws(String area) {
		List<Law> law0 = lawDao.findByAreaAndType(area, 0);
		List<Law> law1 = lawDao.findByAreaAndType(area, 1);
		List<Law> law2 = lawDao.findByAreaAndType(area, 2);
		JSONObject rs = new JSONObject();
		rs.put("law0", law0);
		rs.put("law1", law1);
		rs.put("law2", law2);
		return rs;
	}

}
