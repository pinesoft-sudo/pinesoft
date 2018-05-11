package org.pine.solr.entity;
/**
 * @author yangs:
 * @date 创建时间：2017年4月10日 下午3:43:58
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public enum EOperator {
	/**
	 * 不等于
	 */
	ne("!=%s"),
	/**
	 * 等于
	 */
	is("=%s"),
	/**
	 * 大于
	 */
	gt(">%s"),
	/**
	 * 大于等于
	 */
	gte(">=%s"),
	/**
	 * 小于
	 */
	lt("<%s"),
	/**
	 * 小于等于
	 */
	lte("<=%s"),
	/**
	 * 包含
	 */
	in(" in (%s)"),
	/**
	 * 不包含
	 */
	nin(" not in(%s)"),
	/**
	 * 模糊匹配
	 */
	like(" like '%%%s%%'"),
	/**
	 * 左模糊
	 */
	llike(" like '%%%s'"),
	/**
	 * 右模糊
	 */
	rlike(" like '%s%%'");
	
	private String operator;
	
	private EOperator(String operator) {
		this.operator = operator;
	}
	
	public String getOperator() {
		return operator;
	}
}
