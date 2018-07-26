package com.lawbot.reco.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.lawbot.reco.domain.Law;

@Mapper
public interface LawDao {
	
	List<Law> findByAreaAndType(@Param("lawArea") String area , @Param("lawType") int type);
}
