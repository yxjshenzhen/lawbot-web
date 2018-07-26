package com.lawbot.core.entity;

import java.beans.Transient;
import java.io.Serializable;

/**
 * 
 * @author Cloud Lau
 *
 */
public class LawbotUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LawbotUser(String uid ,String username){
		this.username = username;
		this.uid = uid;
	}
	
	public LawbotUser(String uid , String username, int priv){
		this(uid, username);
		this.priv = priv;
	}

	private String username;
	
	
	private int priv;
	
	private String uid;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Transient
	public int getPriv() {
		return priv;
	}

	public void setPriv(int priv) {
		this.priv = priv;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
}
