package com.lawbot.award.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.lawbot.award.entities.AwardDocResponse;

/**
 * 
 * @author Cloud Lau
 *
 */
public interface AwardSevice {
	
	AwardDocResponse generate(String caseType,MultipartFile routineFile ,MultipartFile appDocFile , MultipartFile[] eAppFiles, MultipartFile resDocFile,MultipartFile[] eResFiles) throws IOException; 
	
	InputStream readAwardDoc(String name) throws FileNotFoundException;
}
