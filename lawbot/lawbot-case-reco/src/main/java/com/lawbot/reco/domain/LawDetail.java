package com.lawbot.reco.domain;

import java.io.Serializable;

/**
 * 
 * @author Cloud Lau
 *
 */
public class LawDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int detailId;

	private int lawIndex;
	
	private String indexName;
	
	private String lawDetail;

	public int getLawIndex() {
		return lawIndex;
	}

	public void setLawIndex(int lawIndex) {
		this.lawIndex = lawIndex;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getLawDetail() {
		return lawDetail;
	}

	public void setLawDetail(String lawDetail) {
		this.lawDetail = lawDetail;
	}

	public int getDetailId() {
		return detailId;
	}

	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}
	
	
}
