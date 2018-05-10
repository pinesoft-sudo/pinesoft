package org.pine.common.criteria;

import java.util.ArrayList;
import java.util.List;


/** 
* @author  xuj: 
* @date 创建时间：2016年10月28日 下午4:18:32 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public class Filter {
	
 
	protected String orderByClause;
 
	protected boolean distinct;
	protected List<GeneratedCriteria> oredCriteria;
	public Filter() {
		oredCriteria = new ArrayList<GeneratedCriteria>();
	}
	public Filter(String condition, Object value) {
		oredCriteria = new ArrayList<GeneratedCriteria>();
		GeneratedCriteria criteria = new GeneratedCriteria();
		criteria.addCriterion(condition,value); 
		createCriteria(criteria);
	}
	public Filter(String condition) {
		oredCriteria = new ArrayList<GeneratedCriteria>();
		GeneratedCriteria criteria = new GeneratedCriteria();
		criteria.addCriterion(condition); 
		createCriteria(criteria);
	}
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}
	public boolean isDistinct() {
		return distinct;
	}
	public List<GeneratedCriteria> getOredCriteria() {
		return oredCriteria;
	}
	public void or(GeneratedCriteria criteria) {
		oredCriteria.add(criteria);
	}
	
	public void createCriteria(GeneratedCriteria criteria) {
		oredCriteria.add(criteria);
	}
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	
	
	
}
