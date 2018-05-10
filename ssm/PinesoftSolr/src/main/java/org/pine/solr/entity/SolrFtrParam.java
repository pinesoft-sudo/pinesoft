package org.pine.solr.entity;

import java.util.Date;
import java.util.Map;

/** 
* @author  yangs
* @date 2016年10月31日 下午3:42:23 
* @version 1.0 
* @parameter  
*/
public class SolrFtrParam {
	private String id;
	private Date creadedate;
	private String name;
	private String searchtext;
	private String bizcode;
	private Map<String, Object> hlfields;
	
	public Map<String, Object> getHlfields() {
		return hlfields;
	}
	public void setHlfields(Map<String, Object> hlfields) {
		this.hlfields = hlfields;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreadeDate() {
		return creadedate;
	}
	public void setCreadeDate(Date creadeDate) {
		this.creadedate = creadeDate;
	}
	public String getSearchText() {
		return searchtext;
	}
	public void setSearchText(String searchText) {
		this.searchtext = searchText;
	}
	public String getBizCode() {
		return bizcode;
	}
	public void setBizCode(String bizCode) {
		this.bizcode = bizCode;
	}
}
