package org.pine.solr.entity;

import java.util.List;
import java.util.Map;

/**
 * @author yangs
 * @date 2016年10月24日 上午10:44:03
 * @version 1.0
 * @parameter
 */
public class SolrResponse {
	private long totalnum;
	private List<Map<String, Object>> data;
	private String content;

	public long getTotalnum() {
		return totalnum;
	}

	public void setTotalnum(long totalnum) {
		this.totalnum = totalnum;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
