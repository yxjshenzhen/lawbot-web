package com.lawbot.contract.service;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.lawbot.contract.entity.ContractDocResponse;

/**
 * 
 * @author Cloud Lau
 *
 */
public interface ContractService {


	/**
	 * 
	 * @param file
	 * @param fundType
	 * @param reasonColumn
	 * @return
	 * @throws Exception
	 */
	ContractDocResponse compare(MultipartFile file , String fundType, String reasonColumn) throws Exception;
	
	InputStream readContractDoc(String name) throws FileNotFoundException;
}
