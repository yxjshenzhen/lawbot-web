package com.lawbot.sys.domain;

import java.io.Serializable;

/**
 * 
 * @author Cloud Lau
 *
 */
public class Priv implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pid;
	
	private String uid;
	
	private Integer priv;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getPriv() {
		return priv;
	}

	public void setPriv(Integer priv) {
		this.priv = priv;
	}
	
	
}
