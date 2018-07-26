package com.lawbot.sys.service;

import com.lawbot.sys.domain.User;

/**
 * 
 * @author Cloud Lau
 *
 */
public interface UserService {
	
	User findById(String uid);
	
	String findPasswordByUname(String uname);
	
	User login(String user,String password);

}
