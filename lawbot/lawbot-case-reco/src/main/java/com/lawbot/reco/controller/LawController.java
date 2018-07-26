package com.lawbot.reco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawbot.core.entity.result.Result;
import com.lawbot.core.entity.result.Result.ResultData;
import com.lawbot.reco.service.LawService;

/**
 * 
 * @author Cloud Lau
 *
 */
@RequestMapping("law")
@RestController
public class LawController {
	
	@Autowired
	private LawService lawService;

	@GetMapping("list")
	public Result getLaws(@RequestParam("lawArea") String area){
		
		return Result.success(new ResultData("laws" , lawService.getLaws(area)));
	}
	
}
