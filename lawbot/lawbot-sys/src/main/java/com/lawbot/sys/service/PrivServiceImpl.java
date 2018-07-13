package com.lawbot.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.lawbot.sys.dao.PrivDao;
import com.lawbot.sys.domain.Priv;

/**
 * 
 * @author Cloud Lau
 *
 */
@Service
public class PrivServiceImpl implements PrivService{

	@Autowired
	private PrivDao privDao;

	@Override
	public Priv findByUid(String uid) {
		
		Assert.notNull(uid);
		
		return privDao.findByUid(uid);
	}
	
	

	
}
