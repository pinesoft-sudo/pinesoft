/**
 * Project Name:GisqDCServerRest
 * File Name:SolrFTR.java
 * Package Name: com.gisquest.solr.basis
 * Date:2016年10月20日
 * Copyright (c) 2016, Gisquest All Rights Reserved.
 * Current Author:  yangs.
*/
package org.pine.solr.impl;

/**
 * @author:    yangs
 * @Class:      SolrFTR
 * @Date:       2016年11月1日
 * @version:   v1.0
 * @Function: TODO ADD FUNCTION.
 * @Reason:   TODO ADD REASON.
 * @since       JDK 1.8
 */
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.pine.solr.basis.SolrParser;
import org.pine.solr.contract.AbstractSolrFTR;
import org.pine.solr.entity.SolrFtrParam;
import org.pine.solr.entity.SolrRequest;
import org.pine.solr.entity.SolrResponse;

import com.alibaba.fastjson.JSON;


public class SolrFTR extends AbstractSolrFTR {

	public SolrFTR(SolrClient solrclient) {
		super(solrclient);
	}

	public SolrFTR(String solrurl, String solrhome) {
		super(solrurl, solrhome);
	}

	/**
	 *
	 * 创建索引文件（全文检索）
	 *
	 * @see com.gisquest.solr.contract.AbstractSolrFTR#createIndex(java.util.List)
	 */
	@Override
	public long createIndex(List<SolrFtrParam> dataMap) throws Exception {
		try {
			super.createSolrInputDocList(dataMap);
			return createIndex();
		}
		catch (SolrServerException e) {
			throw e;
		}
	}


	/**
	 *
	 * 全文检索查询（对象）
	 *
	 * com.gisquest.solr.contract.AbstractSolrFTR#searchIndex(com.gisquest.entity.solr.SolrRequest)
	 */
	@Override
	public String searchIndex(SolrRequest srq) throws Exception {
		try {
			SolrQuery query = SolrParser.qureyFTR(srq);
			SolrResponse solrRep = searchBase(query);
			return JSON.toJSONString(solrRep);
		}
		catch (SolrServerException e) {
			throw e;
		}
	}

	/**
	 *
	 * 全文检索查询（参数）
	 *
	 * @see com.gisquest.solr.contract.AbstractSolrFTR#searchIndex(java.lang.String, java.lang.String, int, int,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String searchIndex(String searchValue, String bizCode, int size, int current, String sortField, String sortType, String hlFields)
			throws Exception {
		try {
			SolrQuery query = SolrParser.qureyFTR(searchValue, bizCode, size, current, sortField, sortType, hlFields);
			SolrResponse solrRep = searchBase(query);
			return JSON.toJSONString(solrRep);
		}
		catch (SolrServerException e) {
			throw e;
		}
	}

	/**
	 * 全文检索查询（对象+模版）
	 *
	 * @see com.gisquest.solr.contract.AbstractSolrFTR#searchIndex(com.gisquest.entity.solr.SolrRequest,
	 *      java.lang.String)
	 */
	@Override
	public String searchIndex(SolrRequest srq, String executeContent) throws Exception {
		try {
			SolrQuery query = SolrParser.qureyFTR(srq);
			SolrResponse solrRep = searchBase(query);
			solrRep.setContent(SolrParser.contentParse(solrRep, executeContent));
			return JSON.toJSONString(solrRep);
		}
		catch (SolrServerException e) {
			throw e;
		}
	}

	/**
	 * 全文检索查询（参数+模版）
	 *
	 * @see com.gisquest.solr.contract.AbstractSolrFTR#searchIndex(java.lang.String, java.lang.String, int, int,
	 *      java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String searchIndex(String searchValue, String bizCode, int size, int current, String sortField, String sortType, String hlFields,
			String executeContent) throws Exception {
		try {
			SolrQuery query = SolrParser.qureyFTR(searchValue, bizCode, size, current, sortField, sortType, hlFields);
			SolrResponse solrRep = searchBase(query);
			solrRep.setContent(SolrParser.contentParse(solrRep, executeContent));
			return JSON.toJSONString(solrRep);
		}
		catch (SolrServerException e) {
			throw e;
		}
	}

}
