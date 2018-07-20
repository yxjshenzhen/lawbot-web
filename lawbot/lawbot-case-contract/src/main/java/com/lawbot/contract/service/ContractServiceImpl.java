package com.lawbot.contract.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawbot.contract.entity.ContractDocResponse;
import com.lawbot.contract.textprocessor.DocGenerator;
import com.lawbot.core.util.Random;

/**
 * 
 * @author Cloud Lau
 *
 */
@Service
public class ContractServiceImpl implements ContractService{

	@Value("${lawbot.doc.upload.root}")
	private String uploadRoot;
	
	@Value("${lawbot.doc.output.root}")
	private String outputRoot;
	
	@Value("${lawbot.doc.template.root}")
	private String templateRoot;
	
	@Override
	public ContractDocResponse compare(MultipartFile file, String fundType, String reasonColumn) throws Exception {

		String docPath = saveUploadFiles(file);	
		String outputName = Random.unique() + ".docx";
		String outputPath = outputRoot + File.separator + outputName;	
		
		int code = DocGenerator.generate(docPath, outputPath , templateRoot + File.separator,fundType,reasonColumn);
		
		return new ContractDocResponse(outputName ,  code);
	}
	
	private String saveUploadFiles(MultipartFile file) throws IOException{
		
		File root = new File(uploadRoot + File.separator + Random.unique());
		FileUtils.forceMkdir(root);
		String filePath = root + File.separator + file.getOriginalFilename();
		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath));
		
		return filePath;
	}
	
	@Override
	public InputStream readContractDoc(String name) throws FileNotFoundException {
		File file = new File(outputRoot + File.separator + name);
		if(file.exists()){
			return new FileInputStream(file);
		}
		return null;
	}

	
	
}
