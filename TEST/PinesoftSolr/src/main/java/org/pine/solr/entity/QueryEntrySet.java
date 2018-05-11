package org.pine.solr.entity;

import java.io.Serializable;

import org.apache.solr.parser.QueryParser;

/**
 * @author yangs
 * @date 2016年10月25日 下午1:19:44
 * @version 1.0
 * @parameter
 */
public class QueryEntrySet implements Serializable {
	private static final long serialVersionUID = 1L;

	final static String DYNAMICSTRING = "_s";

	private String key;
	private String value;
	private QueryRange range;
	private EOperator operator;// 运算符

	public String getNewKey() {
		if (key == null)
			return null;
		if (key.equals("id") || key.equals("searchText") || key.equals("bizCode") || key.equals("name")
				|| key.equals("creadeDate")) {
			return key;
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

	/**
	 * 经过转义处理的值
	 */
	public String getEscapeValue() {
		return QueryParser.escape(value);
	}

	/**
	 * 未经过转义处理的值
	 */
	public String getValue1() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public QueryRange getRange() {
		return range;
	}

	public void setRange(QueryRange range) {
		this.range = range;
	}

	public EOperator getOperator() {
		return operator;
	}

	public void setOperator(EOperator operator) {
		this.operator = operator;
	}
}
