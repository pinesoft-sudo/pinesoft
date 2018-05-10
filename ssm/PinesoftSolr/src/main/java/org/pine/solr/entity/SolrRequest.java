package org.pine.solr.entity;

/**
 * @author yangs
 * @date 2016年11月1日 上午9:41:33
 * @version 1.0
 * @parameter
 */
public class SolrRequest {
	private String searchvalue;
	private String bizcode;
	private String templatetype;
	private int size;
	private int current;
	private String sortfield;
	private String sorttype;
	private String hlfields;


	public String getSearchvalue() {
		return searchvalue;
	}

	public void setSearchvalue(String searchvalue) {
		this.searchvalue = searchvalue;
	}

	public String getBizcode() {
		return bizcode;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		if (size < 0) {
			this.size = 0;
		} else {
			this.size = size;
		}
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		if (current == 0) {
			this.current = 1;
		} else {
			this.current = current;
		}
	}

	public String getSortfield() {
		return sortfield;
	}

	public void setSortfield(String sortfield) {
		this.sortfield = sortfield;
	}

	public String getSorttype() {
		return sorttype;
	}

	public void setSorttype(String sorttype) {
		this.sorttype = sorttype;
	}

	public String getHlfields() {
		return hlfields;
	}

	public void setHlfields(String hlfields) {
		this.hlfields = hlfields;
	}

	public String getTemplatetype() {
		return templatetype;
	}

	public void setTemplatetype(String templatetype) {
		this.templatetype = templatetype;
	}

}
