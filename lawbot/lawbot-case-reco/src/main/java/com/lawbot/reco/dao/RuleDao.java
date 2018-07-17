package com.lawbot.reco.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author Cloud Lau
 *
 */
@Mapper
public interface RuleDao {

	List<Map> findWithLawByKeys(@Param("caseKeys") List<String> keys);
}
