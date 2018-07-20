package com.lawbot.contract.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lawbot.contract.entity.ContractDocResponse;
import com.lawbot.contract.service.ContractService;
import com.lawbot.core.entity.result.Result;
import com.lawbot.core.entity.result.Result.ResultData;
import com.lawbot.core.entity.result.ResultCode;

@RestController
@RequestMapping("/")
public class ContractController {
	
	Logger logger = LoggerFactory.getLogger(ContractController.class);

	@Autowired
	private ContractService contractService;
	
	@PostMapping("compare")
	public Result compare(@RequestParam("file") MultipartFile file , @RequestParam String fundType , @RequestParam String reasonColumn) throws Exception{
		
		ContractDocResponse docRes = contractService.compare(file, fundType, reasonColumn);
		
		int code = docRes.getCode();
		String error = "";
		
		switch(code){
		case 0:
		case 1:
		case 2:
		case 3:
			return Result.success(new ResultData("fileName" , docRes.getOutputDoc()));
		case -1:
			error = "Unknow error";
			break;
		case -11:
			error = "Error file: " + file.getOriginalFilename();
			break;
		case -30:
			error = "Template not found"; break;
		case -31:
			error = "Read template error"; break;
		case -21:
			error = "Generate contract error"; break;
		default:
			error = "Unknow error";
		}
		return Result.error(ResultCode.OTHER_ERROR , new ResultData("error" , error));
	}
	
	@GetMapping("file/{fileName}.{ext}")
	public void downloadAward(@PathVariable String fileName , @PathVariable String ext , @RequestParam(name = "downloadName" , required = false) String downloadName, 
			HttpServletResponse res){
		res.setContentType("application/docx" + ext);  
		if(StringUtils.isEmpty(downloadName)){
			downloadName = fileName;
		}
		res.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(downloadName + "." + ext) + "\""); 
		
		InputStream is;
		try {
			is = contractService.readContractDoc(fileName + "." + ext);
			IOUtils.copy(is, res.getOutputStream());	
			res.flushBuffer();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
	}
}
