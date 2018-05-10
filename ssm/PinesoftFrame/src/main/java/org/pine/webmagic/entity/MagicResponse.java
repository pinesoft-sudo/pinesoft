package org.pine.webmagic.entity;

public class MagicResponse {
	private String guid;
	private String requestGuid;
	private String pageUrl;
	private String value;

	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getRequestGuid() {
		return requestGuid;
	}
	public void setRequestGuid(String requestGuid) {
		this.requestGuid = requestGuid;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
