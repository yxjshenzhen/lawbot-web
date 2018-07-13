package com.lawbot.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.lawbot.sys.domain.User;

/**
 * 
 * @author Cloud Lau
 *
 */
@Mapper
public interface UserDao {

	/**
	 * 
	 * @param uid
	 * @return
	 */
	User findById(String uid);
	
	/**
	 * 
	 * @param uid
	 * @return
	 */
	String findPasswordByUname(@Param("uname") String uname);
	
	/**
	 * 
	 * @param uname
	 * @return
	 */
	User findByUname(@Param("uname") String uname);
	
}
