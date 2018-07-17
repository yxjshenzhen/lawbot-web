package com.lawbot.reco.domain;

import java.io.Serializable;

/**
 * 
 * @author Cloud Lau
 *
 */
public class Rule implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int ruleId;
	
	private String ruleContent;
	
	private byte ruleStatus;
	
	private String remark;
	

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleContent() {
		return ruleContent;
	}

	public void setRuleContent(String ruleContent) {
		this.ruleContent = ruleContent;
	}

	public byte getRuleStatus() {
		return ruleStatus;
	}

	public void setRuleStatus(byte ruleStatus) {
		this.ruleStatus = ruleStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
