package com.lawbot.sys.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lawbot.core.util.PasswordEncoder;
import com.lawbot.sys.dao.UserDao;
import com.lawbot.sys.domain.User;

/**
 * 
 * @author Cloud Lau
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	

	@Override
	public User findById(String uid) {
		return userDao.findById(uid);
	}

	@Override
	public String findPasswordByUname(String uname) {
		return userDao.findPasswordByUname(uname);
	}
	
	/**
	 * login
	 */
	public User login(String uname,String password){
		Objects.requireNonNull(uname);
		Objects.requireNonNull(password);
		
		User user = userDao.findByUname(uname);
		
		if(Objects.isNull(user)) return null;
		
		String upass = user.getUpass();
		
		if(StringUtils.isEmpty(upass)) return null;
		
		if(!PasswordEncoder.matches(password, upass))
			return null;
		
		return user;
	}
	
}
