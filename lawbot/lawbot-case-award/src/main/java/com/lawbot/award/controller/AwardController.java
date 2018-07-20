package com.lawbot.award.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lawbot.award.entities.AwardDocResponse;
import com.lawbot.award.service.AwardSevice;
import com.lawbot.core.entity.result.Result;
import com.lawbot.core.entity.result.Result.ResultData;
import com.lawbot.core.entity.result.ResultCode;

@RestController
@RequestMapping("/")
public class AwardController {
	
	Logger logger = LoggerFactory.getLogger(AwardController.class);
	
	@Autowired
	private AwardSevice awardService;


	@PostMapping("generate")
	public Result generate(@RequestParam("caseType") String caseType ,@RequestParam("routineFile") MultipartFile routineFile , @RequestParam("appDocFile") MultipartFile appDocFile , 
			@RequestParam(name = "eAppFiles" , required = false) MultipartFile[] eAppFiles,@RequestParam("resDocFile") MultipartFile resDocFile,
			@RequestParam(name = "eResFiles" , required = false) MultipartFile[] eResFiles){
		
		
		String fileName ;
		try {
			AwardDocResponse docRes = awardService.generate(caseType , routineFile , appDocFile , eAppFiles , resDocFile , eResFiles);
			
			if(!docRes.getErrors().isEmpty() || docRes.getFileName() == null){
				return Result.error(ResultCode.OTHER_ERROR , new ResultData("doc", docRes));
			}
			
			return Result.success(new ResultData("fileName" , docRes.getFileName()));
		} catch (IOException e) {
			logger.error(e.getMessage());
			return Result.error(ResultCode.OTHER_ERROR);
		}

	}

	@GetMapping("file/{fileName}.{ext}")
	public void downloadAward(@PathVariable String fileName , @PathVariable String ext , HttpServletResponse res){
		res.setContentType("application/docx" + ext);  
		res.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode("裁决书" + fileName + "." + ext) + "\""); 
		
		InputStream is;
		try {
			is = awardService.readAwardDoc(fileName + "." + ext);
			IOUtils.copy(is, res.getOutputStream());	
			res.flushBuffer();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
	}
}
