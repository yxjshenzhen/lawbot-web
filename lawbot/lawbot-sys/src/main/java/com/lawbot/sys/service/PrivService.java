package com.lawbot.sys.service;

import com.lawbot.sys.domain.Priv;

/**
 * 
 * @author Cloud Lau
 *
 */
public interface PrivService {

	
	Priv findByUid(String uid);
	
}
