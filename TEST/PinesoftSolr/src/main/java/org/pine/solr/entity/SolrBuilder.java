package org.pine.solr.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author yangs
 * @date 2016年10月24日 下午1:44:36
 * @version 1.0
 * @parameter
 */
public class SolrBuilder implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<QueryEntrySet> param_and;
	private List<List<QueryEntrySet>> param_or;	//or条件分组作用，所有分组以or作为条件关联，and作为分组间关联 
	private String sortfield;
	private String sorttype;
	private int current;
	private int size;
	private String bizcode;
	private String templatetype;
	

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
	
    public int getStart() {
        return (current-1) * size;
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

	public List<QueryEntrySet> getParam_and() {
		return param_and;
	}

	public void setParam_and(List<QueryEntrySet> param_and) {
		this.param_and = param_and;
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

	public String getTemplatetype() {
		return templatetype;
	}

	public void setTemplatetype(String templatetype) {
		this.templatetype = templatetype;
	}

	public String getBizcode() {
		return bizcode;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}

	public List<List<QueryEntrySet>> getParam_or() {
		return param_or;
	}

	public void setParam_or(List<List<QueryEntrySet>> param_or) {
		this.param_or = param_or;
	}

}
