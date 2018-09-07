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
	
	List<Map> findCaseCitiesStats(@Param("caseKeys") List<String> keys , @Param("courtLevels") List<Integer> courtLevels , float x);
	
	List<Map> findCaseCourtLevelStats(@Param("caseKeys") List<String> keys , @Param("courtLevels") List<Integer> courtLevels , float x);
	
	/*
	 * mmht
	*/
	List<Map> findWithLawByKeysMmht(@Param("caseKeys") List<String> keys);
	
	List<Map> findCaseLawByCaseIdMmht(@Param("caseId") long caseId);
	
	List<Map> findSameCaseByKeysMmht(@Param("caseKeys") List<String> keys , @Param("courtLevel") int courtLevel);
}
