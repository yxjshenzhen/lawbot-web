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
	
	
	List<Map> findCaseLawByCaseId(@Param("caseId") long caseId);
	
	List<Map> findSameCaseByKeys(@Param("caseKeys") List<String> keys , @Param("courtLevel") int courtLevel);
}
