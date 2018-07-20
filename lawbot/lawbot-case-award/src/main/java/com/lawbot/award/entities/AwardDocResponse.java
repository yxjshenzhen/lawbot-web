package com.lawbot.award.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Cloud Lau
 *
 */
public class AwardDocResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String fileName;
	
	private List<String> errors = new ArrayList<String>();
	
	private List<String> warns = new ArrayList<String>();
	
	public void addError(String ...errors){
		addError(Arrays.asList(errors));
	}
	
	public void addError(List<String> errors){
		this.errors.addAll(errors);
	}
	
	public void addWarn(String ...warns){
		addWarn(Arrays.asList(warns));
	}
	
	public void addWarn(List<String> warns){
		this.warns.addAll(warns);
	}

	
	public List<String> getErrors() {
		return errors;
	}

	public List<String> getWarns() {
		return warns;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

}
