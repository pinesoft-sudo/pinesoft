package org.pine.solr.entity;

import java.io.Serializable;

/**
 * @author yangs
 * @date 2016年10月25日 下午1:24:41
 * @version 1.0
 * @parameter
 */
public class QueryRange implements Serializable{
	private static final long serialVersionUID = 1L;
	final static String DYNAMICSTRING = "_s";
    final static String DYNAMICDATE = "_dt";

    private String key;
    private String begin;
    private String end;

    public String getNewKey() {
	if (key == null)
	    return null;
	if (key.equals("id") || key.equals("searchText") || key.equals("bizCode") || key.equals("name")
		|| key.equals("creadeDate")) {
	    return key;
	} else if (key.toLowerCase().contains("_date")) {
	    return key + DYNAMICDATE;
	} else {
	    return key + DYNAMICSTRING;
	}
    }

    public String getKey() {
	return key;
    }

    public void setKey(String key) {
	this.key = key;
    }

    public String getBegin() {
	return begin;
    }

    public void setBegin(String begin) {
	this.begin = begin;
    }

    public String getEnd() {
	return end;
    }

    public void setEnd(String end) {
	this.end = end;
    }

}
