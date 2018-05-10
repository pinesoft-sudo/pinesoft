/**
 * Project Name:GisqDCServerRest
 * File Name:SolrCase.java
 * Package Name: com.gisquest.solr.contract
 * Date:2016年10月20日
 * Copyright (c) 2016, Gisquest All Rights Reserved.
 * Current Author:  yangs.
*/
package org.pine.solr.contract;

/**
 * @author:    yangs
 * @Class:      SolrCase
 * @Date:       2016年11月1日
 * @version:   v1.0
 * @Function:  ADD FUNCTION.
 * @Reason:    ADD REASON.
 * @since       JDK 1.8
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import org.pine.solr.basis.SolrClientInstance;
import org.pine.solr.basis.SolrInput;
import org.pine.solr.entity.SolrFtrParam;
import org.pine.solr.entity.SolrResponse;

public class SolrCase {
	/* 动态字段_字符串标识 */
	final static String DYNAMICSTRING = "_s";
	/* 动态字段_时间标识 */
	final static String DYNAMICDATE = "_dt";
	/* 动态字段_分词标识 */
	final static String DYNAMICFTR = "_st";
	/* solr服务客户端对象 */
	public SolrClient client;
	/* solr执行文件对象列 */
	public List<SolrInputDocument> docList;

	/**
	 * 构造函数
	 *
	 * @param solrclient
	 *            solr服务客户端
	 */
	public SolrCase(SolrClient solrclient) {
		this.client = solrclient;
	}

	/**
	 * 构造函数
	 *
	 * @param solrurl
	 *            服务地址
	 * @param solrhome
	 *            服务空间名
	 */
	public SolrCase(String solrurl, String solrhome) {
		this.client = SolrClientInstance.getSolrClientInstance(solrurl, solrhome);
	}

	/**
	 * 创建solr执行文件对象列（条件检索）
	 *
	 * @Title: createSolrInputDocList
	 * @Description: 公开方法
	 * @param dataSet
	 *            业务数据集
	 * @param hlFields
	 *            高亮字段
	 * @return void
	 * @author yangs
	 */
	public void createSolrInputDocList(List<Map<String, Object>> dataSet, String hlFields) throws Exception {
		this.docList = SolrInput.getSolrInputDocList(dataSet, hlFields);
	}

	/**
	 * 创建solr执行文件对象列（全文检索）
	 *
	 * @Title: createSolrInputDocList
	 * @Description: 公开方法
	 * @param param
	 *            索引参数对象
	 * @return void
	 * @author yangs
	 */
	public void createSolrInputDocList(List<SolrFtrParam> param) throws Exception {
		this.docList = SolrInput.getSolrInputDocList(param);
	}

	/**
	 * 创建索引文件基方法
	 *
	 * @Title: createIndex
	 * @Description: 公开方法
	 * @return long
	 * @author yangs
	 */
	public long createIndex() throws Exception {
		try {
			if (docList != null && docList.size() > 0) {
				client.add(docList);
				client.commit();
				return docList.size();
			}
			else {
				return 0;
			}
		}
		catch (SolrServerException e) {
			throw e;
		}
	}

	/**
	 * 清空索引文件基方法
	 *
	 * @Title: clearIndex
	 * @Description: 公开方法
	 * @return long
	 * @author yangs
	 */
	public long clearIndex() throws Exception {
		try {
			SolrQuery query = new SolrQuery();
			query.setQuery("*:*");
			QueryResponse response = client.query(query);
			SolrDocumentList docs = response.getResults();

			if (docs.getNumFound() > 0) {
				client.deleteByQuery("*:*");
				client.commit();
			}
			return docs.getNumFound();
		}
		catch (SolrServerException e) {
			throw e;
		}
	}

	/**
	 * 通过域（分类）码删除索引文件
	 *
	 * @Title: deleteIndexbybizCode
	 * @Description: 公开方法
	 * @param bizCode
	 *            域（分类）码
	 * @return long 删除索引文件数目
	 * @author yangs
	 */
	public long deleteIndexbybizCode(String bizCode) throws Exception {
		try {
			SolrQuery query = new SolrQuery();
			query.setQuery("bizCode:" + bizCode);
			QueryResponse response = client.query(query);
			SolrDocumentList docs = response.getResults();

			if (docs.getNumFound() > 0) {
				client.deleteByQuery("bizCode:" + bizCode);
				client.commit();
			}
			return docs.getNumFound();
		}
		catch (SolrServerException e) {
			throw e;
		}
	}

	/**
	 * 通过索引主键删除索引文件
	 *
	 * @Title: deleteIndexbyId
	 * @Description: 公开方法
	 * @param id
	 *            索引主键
	 * @return boolean 是否执行成功
	 * @author yangs
	 */
	public boolean deleteIndexbyId(String id) throws Exception {
		try {
			client.deleteByQuery("id:" + id);
			client.commit();
			return true;
		}
		catch (SolrServerException e) {
			throw e;
		}
	}

	/**
	 * 通过查询条件对象获取检索返回对象
	 *
	 * @Title: searchBase
	 * @Description: 内部方法
	 * @param query
	 *            查询条件对象
	 * @return SolrResponse 检索返回对象
	 * @author yangs
	 */
	public SolrResponse searchBase(SolrQuery query) throws Exception {
		try {
			QueryResponse response = client.query(query);
			SolrDocumentList docs = response.getResults();

			/* 去除动态字段的匹配后缀字符 */
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			for (SolrDocument doc : docs) {
				Map<String, Object> map = doc.getFieldValueMap();
				Map<String, Object> newmap = new HashMap<String, Object>();
				for (String field : map.keySet()) {
					Object val = map.get(field);
					if (field.endsWith(DYNAMICFTR)) {
						field = field.substring(0, field.length() - DYNAMICFTR.length());
					}
					if (field.endsWith(DYNAMICDATE)) {
						field = field.substring(0, field.length() - DYNAMICDATE.length());
					}
					if (field.endsWith(DYNAMICSTRING)) {
						field = field.substring(0, field.length() - DYNAMICSTRING.length());
					}
					newmap.put(field, val);
				}
				data.add(newmap);
			}

			/* 数据高亮 */
			if (query.getHighlight() && query.getHighlightFields().length > 0) {
				Map<String, Map<String, List<String>>> map = response.getHighlighting();
				for (int i = 0; i < data.size(); i++) {
					for (String str : query.getHighlightFields()) {
						List<String> val = map.get(data.get(i).get("id")).get(str);
						if (val != null) {
							if (str.endsWith(DYNAMICFTR)) {
								str = str.substring(0, str.length() - DYNAMICFTR.length());
							}
							data.get(i).put(str, val);
						}
					}
				}
			}

			SolrResponse solrRep = new SolrResponse();
			solrRep.setTotalnum(docs.getNumFound());
			solrRep.setData(data);
			return solrRep;
		}
		catch (SolrServerException e) {
			throw e;
		}
	}
}
