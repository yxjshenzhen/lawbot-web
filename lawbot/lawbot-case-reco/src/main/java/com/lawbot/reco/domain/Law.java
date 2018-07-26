package com.lawbot.reco.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Cloud Lau
 *
 */
public class Law implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int lawId;
	
	private String lawFullName;
	
	private String lawSimpName;
	
	private List<LawDetail> lawDetails;
	
	/**
	 * 
	 */
	private int lawType;

	public int getLawId() {
		return lawId;
	}

	public void setLawId(int lawId) {
		this.lawId = lawId;
	}

	public String getLawFullName() {
		return lawFullName;
	}

	public void setLawFullName(String lawFullName) {
		this.lawFullName = lawFullName;
	}

	public String getLawSimpName() {
		return lawSimpName;
	}

	public void setLawSimpName(String lawSimpName) {
		this.lawSimpName = lawSimpName;
	}

	public List<LawDetail> getLawDetails() {
		return lawDetails;
	}

	public void setLawDetails(List<LawDetail> lawDetails) {
		this.lawDetails = lawDetails;
	}

	public int getLawType() {
		return lawType;
	}

	public void setLawType(int lawType) {
		this.lawType = lawType;
	}
	
}
