package com.lawbot.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.lawbot.sys.domain.Priv;

/**
 * 
 * @author Cloud Lau
 *
 */
@Mapper
public interface PrivDao {

	Priv findByUid(String uid);
}
