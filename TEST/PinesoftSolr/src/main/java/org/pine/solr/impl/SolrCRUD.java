/**
 * Project Name:GisqDCServerRest
 * File Name:SolrCRUD.java
 * Package Name: com.gisquest.solr.basis
 * Date:2016年10月20日
 * Copyright (c) 2016, Gisquest All Rights Reserved.
 * Current Author:  yangs.
*/
package org.pine.solr.impl;

/**
 * @author:    yangs
 * @Class:      SolrCRUD
 * @Date:       2016年11月1日
 * @version:   v1.0
 * @Function: TODO ADD FUNCTION.
 * @Reason:   TODO ADD REASON.
 * @since       JDK 1.8
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.pine.solr.basis.SolrParser;
import org.pine.solr.contract.AbstractSolrCRUD;
import org.pine.solr.entity.SolrBuilder;
import org.pine.solr.entity.SolrResponse;

import com.alibaba.fastjson.JSON;


public class SolrCRUD extends AbstractSolrCRUD {

	public SolrCRUD(SolrClient solrclient) {
		super(solrclient);
	}

	public SolrCRUD(String solrurl, String solrhomet) {
		super(solrurl, solrhomet);
	}

	/**
	 *
	 * 创建单个索引文件（条件查询）
	 *
	 * @see com.gisquest.solr.contract.AbstractSolrCRUD#createIndex(java.util.Map, java.lang.String)
	 */
	@Override
	public boolean createIndex(Map<String, Object> dataMap, String hlFields) throws Exception {
		try {
			List<Map<String, Object>> dataSet = new ArrayList<Map<String, Object>>();
			dataSet.add(dataMap);
			super.createSolrInputDocList(dataSet, hlFields);
			return createIndex() > 0;
		}
		catch (SolrServerException e) {
			throw e;
		}
	}

	/**
	 *
	 * 创建索引文件 （条件查询）
	 *
	 * @see com.gisquest.solr.contract.AbstractSolrCRUD#createIndex(java.util.List, java.lang.String)
	 */
	@Override
	public long createIndex(List<Map<String, Object>> dataSet, String hlFields) throws Exception {
		try {
			super.createSolrInputDocList(dataSet, hlFields);
			return createIndex();
		}
		catch (SolrServerException e) {
			throw e;
		}
	}

	/**
	 *
	 * 条件查询检索
	 *
	 * @see com.gisquest.solr.contract.AbstractSolrCRUD#searchIndex(com.SolrBuilder.entity.solr.QueryBuilder)
	 */
	@Override
	public String searchIndex(SolrBuilder sqb) throws Exception {
		try {
			SolrQuery query = SolrParser.queryParse(sqb);
			SolrResponse solrRep = searchBase(query);
			return JSON.toJSONString(solrRep);
		}
		catch (SolrServerException e) {
			throw e;
		}
	}

	/**
	 *
	 * 条件查询检索
	 *
	 * @see com.gisquest.solr.contract.AbstractSolrCRUD#searchIndex(com.SolrBuilder.entity.solr.QueryBuilder,
	 *      java.lang.String)
	 */
	@Override
	public String searchIndex(SolrBuilder sqb, String executeContent) throws Exception {
		try {
			SolrQuery query = SolrParser.queryParse(sqb);
			SolrResponse solrRep = searchBase(query);
			solrRep.setContent(SolrParser.contentParse(solrRep, executeContent));
			return JSON.toJSONString(solrRep);
		}
		catch (SolrServerException e) {
			throw e;
		}
	}
}
