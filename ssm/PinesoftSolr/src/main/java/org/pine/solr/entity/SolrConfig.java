package org.pine.solr.entity;

public class SolrConfig {

	private String recordGuid;
	private String name;
	private String code;
	private String enable;
	private String searchFields;
	private String cache;
	private String orderFields;
	private String orderType;
	private String hlFields;
	private String sqlText;
	private String titleField;

	public String getRecordGuid() {
		return recordGuid;
	}

	public void setRecordGuid(String recordGuid) {
		this.recordGuid = recordGuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getSearchFields() {
		return searchFields;
	}

	public void setSearchFields(String searchFields) {
		this.searchFields = searchFields;
	}

	public String getCache() {
		return cache;
	}

	public void setCache(String cache) {
		this.cache = cache;
	}

	public String getOrderFields() {
		return orderFields;
	}

	public void setOrderFields(String orderFields) {
		this.orderFields = orderFields;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getHlFields() {
		return hlFields;
	}

	public void setHlFields(String hlFields) {
		this.hlFields = hlFields;
	}

	public String getSqlText() {
		return sqlText;
	}

	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}

	public String getTitleField() {
		return titleField;
	}

	public void setTitleField(String titleField) {
		this.titleField = titleField;
	}
}