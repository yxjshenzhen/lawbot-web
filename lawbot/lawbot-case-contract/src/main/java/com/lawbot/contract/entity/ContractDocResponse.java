package com.lawbot.contract.entity;

import java.io.Serializable;

/**
 * 
 * @author Cloud Lau
 *
 */
public class ContractDocResponse implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String outputDoc;
	
	private int code;
	
	
	public ContractDocResponse(String outputDoc , int code){
		this.outputDoc = outputDoc;
		this.code = code;
	}


	public String getOutputDoc() {
		return outputDoc;
	}


	public int getCode() {
		return code;
	}
	
	
}
