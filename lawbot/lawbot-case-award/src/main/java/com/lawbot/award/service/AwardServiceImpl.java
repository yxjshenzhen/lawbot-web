package com.lawbot.award.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.lawbot.award.entities.ArbitrationApplication;
import com.lawbot.award.entities.ArbitrationRoutine;
import com.lawbot.award.entities.AwardDocResponse;
import com.lawbot.award.fileprocessor.ApplicationDocReader;
import com.lawbot.award.fileprocessor.AwardDocGenerator;
import com.lawbot.award.fileprocessor.EvidenceDocReader;
import com.lawbot.award.fileprocessor.RespondDocReader;
import com.lawbot.award.fileprocessor.RoutineDocReader;
import com.lawbot.core.util.Random;

/**
 * 
 * @author Cloud Lau
 *
 */
@Service
public class AwardServiceImpl implements AwardSevice{
	
	private static final Logger logger = LoggerFactory.getLogger(AwardServiceImpl.class);
	
	@Value("${lawbot.doc.upload.root}")
	private String uploadRoot;
	
	@Value("${lawbot.doc.output.root}")
	private String outputRoot;

	@Override
	public AwardDocResponse generate(String caseType, MultipartFile routineFile, MultipartFile appDocFile,
			MultipartFile[] eAppFiles, MultipartFile resDocFile, MultipartFile[] eResFiles) throws IOException {

		JSONObject paths = saveUploadFiles(routineFile, appDocFile, eAppFiles, resDocFile, eResFiles);
		
		return generate(paths);
		
	}
	
	/**
	 * 如果太慢可以改成多线程的方式
	 * @param routineFile
	 * @param appDocFile
	 * @param eAppFiles
	 * @param resDocFile
	 * @param eResFiles
	 * @return
	 * @throws IOException
	 */
	private JSONObject saveUploadFiles(MultipartFile routineFile, MultipartFile appDocFile,
			MultipartFile[] eAppFiles, MultipartFile resDocFile, MultipartFile[] eResFiles) throws IOException{
		
		JSONObject paths = new JSONObject();
		
		File root = new File(uploadRoot + File.separator + Random.unique());
		FileUtils.forceMkdir(root);
		
		//程序文件	
		String routineFilePath = root + File.separator + routineFile.getOriginalFilename();
		FileUtils.copyInputStreamToFile(routineFile.getInputStream(), new File(routineFilePath));
		paths.put("routineFilePath", routineFilePath);
		
		//仲裁申请书
		String appDocFilePath = root + File.separator + appDocFile.getOriginalFilename();
		FileUtils.copyInputStreamToFile(appDocFile.getInputStream(), new File(appDocFilePath));
		paths.put("appDocFilePath", appDocFilePath);
		
		//申请人证据
		List<String> eAppFilePaths = new ArrayList<String>(eAppFiles.length);
		for(MultipartFile f : eAppFiles){
			String eAppFilePath = root + File.separator + f.getOriginalFilename();
			FileUtils.copyInputStreamToFile(f.getInputStream(), new File(eAppFilePath));
			eAppFilePaths.add(eAppFilePath);
		}
		paths.put("eAppFilePaths", eAppFilePaths);
		
		//答辩状
		String resDocFilePath = root + File.separator + resDocFile.getOriginalFilename();
		FileUtils.copyInputStreamToFile(resDocFile.getInputStream(), new File(resDocFilePath));
		paths.put("resDocFilePath", resDocFilePath);
		
		//被申请人证据
		List<String> eResFilePaths = new ArrayList<String>(eResFiles.length);
		for(MultipartFile f : eResFiles){
			String eResFilePath = root + File.separator + f.getOriginalFilename();
			FileUtils.copyInputStreamToFile(f.getInputStream(), new File(eResFilePath));
			eResFilePaths.add(eResFilePath);
		}
		paths.put("eResFilePaths", eResFilePaths);
		
		return paths;
	}

	private AwardDocResponse generate(JSONObject paths) throws IOException {
		
		String routineDoc = paths.getString("routineFilePath");
		String applicationDoc = paths.getString("appDocFilePath");
		List<String> aeAppList = (List<String>) paths.get("eAppFilePaths");
		String responseDoc = paths.getString("resDocFilePath");
		List<String> eResList = (List<String>) paths.get("eResFilePaths");
		ArbitrationRoutine routine = null;
	    ArbitrationApplication aApplication = null;
	    
	    String respondContent = null;
	    List reAppList = null;
	    AwardDocResponse response = new AwardDocResponse();

        try {
            RoutineDocReader rdr = new RoutineDocReader();
            routine = rdr.processRoutine(routineDoc);
            response.addError(rdr.getErrorToUser());
            response.addWarn(rdr.getWarningToUser());
            for (int i = 0; i < rdr.getErrorToUser().size(); ++i) {
            	logger.error(rdr.getErrorToUser().get(i).toString());
            }
            for (int i = 0; i < rdr.getWarningToUser().size(); ++i) {
                logger.warn(rdr.getWarningToUser().get(i).toString());
            }
        } catch (IOException e) {
        	response.addError(e.getMessage());
            logger.error(e.getMessage());
        }
        
        try {
            ApplicationDocReader adr = new ApplicationDocReader();
            aApplication = adr.processApplication(applicationDoc);
            response.addError(adr.getErrorToUser());
            response.addWarn(adr.getWarningToUser());
            for (int i = 0; i < adr.getErrorToUser().size(); ++i) {
            	logger.error(adr.getErrorToUser().get(i).toString());
            }
            for (int i = 0; i < adr.getWarningToUser().size(); ++i) {
            	logger.warn(adr.getWarningToUser().get(i).toString());
            }
        } catch (IOException e) {
        	response.addError(e.getMessage());
        	logger.error(e.getMessage());
        }
        try {
            EvidenceDocReader aedr = new EvidenceDocReader();
            aeAppList = aedr.getEvidenceList(aeAppList);
            response.addError(aedr.getErrorToUser());
            response.addWarn(aedr.getWarningToUser());
            for (int i = 0; i < aedr.getErrorToUser().size(); ++i) {
            	logger.error(aedr.getErrorToUser().get(i).toString());
            }
            for (int i = 0; i < aedr.getWarningToUser().size(); ++i) {
            	logger.warn(aedr.getWarningToUser().get(i).toString());
            }
        } catch (IOException e) {
        	response.addError(e.getMessage());
        	logger.error(e.getMessage());
        }
        try {
            RespondDocReader resdr = new RespondDocReader();
            respondContent = resdr.processRespond(responseDoc);
            response.addError(resdr.getErrorToUser());
            response.addWarn(resdr.getWarningToUser());
            for (int i = 0; i < resdr.getErrorToUser().size(); ++i) {
            	logger.error(resdr.getErrorToUser().get(i).toString());
            }
            for (int i = 0; i < resdr.getWarningToUser().size(); ++i) {
            	logger.warn(resdr.getWarningToUser().get(i).toString());
            }
        } catch (IOException e) {
        	response.addError(e.getMessage());
        	logger.error(e.getMessage());
        }
        try {
            EvidenceDocReader redr = new EvidenceDocReader();
            reAppList = redr.getEvidenceList(eResList);
            response.addError(redr.getErrorToUser());
            response.addWarn(redr.getWarningToUser());
            for (int i = 0; i < redr.getErrorToUser().size(); ++i) {
            	logger.error(redr.getErrorToUser().get(i).toString());
            }
            for (int i = 0; i < redr.getWarningToUser().size(); ++i) {
            	logger.warn(redr.getWarningToUser().get(i).toString());
            }
        } catch (IOException e) {
        	response.addError(e.getMessage());
        	logger.error(e.getMessage());
        }
        
        if(!response.getErrors().isEmpty()){//如果错误提前返回
        	return response;
        }
        
        File root = new File(outputRoot);
		FileUtils.forceMkdir(root);
		
		String fileName =  Random.unique() + ".docx";
		
		String destPath = outputRoot + File.separator + fileName;
        
        AwardDocGenerator awardGen = new AwardDocGenerator(destPath);
        
        XWPFDocument generatedAwardDoc = awardGen.generateAwardDoc(routine, aApplication, aeAppList, respondContent, reAppList);
        
        if(generatedAwardDoc != null){
        	response.setFileName(fileName);
        }
        return response; 
	}

	@Override
	public InputStream readAwardDoc(String name) throws FileNotFoundException {
		File file = new File(outputRoot + File.separator + name);
		if(file.exists()){
			return new FileInputStream(file);
		}
		return null;
	}

}
