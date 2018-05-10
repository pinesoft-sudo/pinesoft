/**
 * Project Name:GisqDCServerRest
 * File Name:AbstractSolrFTR.java
 * Package Name: com.gisquest.solr.basis
 * Date:2016年10月20日
 * Copyright (c) 2016, Gisquest All Rights Reserved.
 * Current Author:  yangs.
*/
package org.pine.solr.contract;

/**
 * @author:    yangs
 * @Class:      AbstractSolrFTR
 * @Date:       2016年10月31日
 * @version:   v1.0
 * @Function: TODO ADD FUNCTION.
 * @Reason:   TODO ADD REASON.
 * @since       JDK 1.8
 */
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;

import org.pine.solr.entity.SolrFtrParam;
import org.pine.solr.entity.SolrRequest;

public abstract class AbstractSolrFTR extends SolrCase {
	/**
	 * 构造函数（继承）
	 */
	public AbstractSolrFTR(SolrClient solrclient) {
		super(solrclient);
	}

	/**
	 * 构造函数（继承）
	 */
	public AbstractSolrFTR(String solrurl, String solrhome) {
		super(solrurl, solrhome);
	}

	/**
	 * 创建索引文件（全文检索）
	 *
	 * @Title: createIndex
	 * @Description: 公开方法
	 * @param dataMap
	 *            全文检索参数对象
	 * @return long 创建索引文件数目
	 * @author yangs
	 */
	public abstract long createIndex(List<SolrFtrParam> dataMap) throws Exception;

	/**
	 * 全文检索查询（对象）
	 *
	 * @Title: searchIndex
	 * @Description: 公开方法
	 * @param srq
	 *            检索请求对象
	 * @return String 检索返回值
	 * @author yangs
	 */
	public abstract String searchIndex(SolrRequest srq) throws Exception;

	/**
	 * 全文检索查询（对象+模版）
	 *
	 * @Title: searchIndex
	 * @Description: 公开方法
	 * @param srq
	 *            检索请求对象
	 * @param executeContent
	 *            显示模版
	 * @return 检索返回值
	 * @throws Exception
	 * @returnType String
	 * @author yangs
	 */
	public abstract String searchIndex(SolrRequest srq, String executeContent) throws Exception;

	/**
	 * 全文检索查询（参数）
	 *
	 * @Title: searchIndex
	 * @Description: 公开方法
	 * @param searchValue
	 *            检索值
	 * @param bizCode
	 *            域（分类）码
	 * @param size
	 *            获取数据长度
	 * @param current
	 *            当前页码
	 * @param sortField
	 *            排序字段
	 * @param sortType
	 *            排序方式
	 * @param hlFields
	 *            高亮字段
	 * @return String 检索返回值
	 * @author yangs
	 */
	public abstract String searchIndex(String searchValue, String bizCode, int size, int current, String sortField, String sortType, String hlFields)
			throws Exception;

	/**
	 * 全文检索查询（参数+模版）
	 *
	 * @Title: searchIndex
	 * @Description: 公开方法
	 * @param searchValue
	 *            检索值
	 * @param bizCode
	 *            域（分类）码
	 * @param size
	 *            获取数据长度
	 * @param current
	 *            当前页码
	 * @param sortField
	 *            排序字段
	 * @param sortType
	 *            排序方式
	 * @param hlFields
	 *            高亮字段
	 * @param executeContent
	 *            显示模版
	 * @return 检索返回值
	 * @throws Exception
	 * @returnType String
	 * @author yangs
	 */
	public abstract String searchIndex(String searchValue, String bizCode, int size, int current, String sortField, String sortType, String hlFields,
			String executeContent) throws Exception;

}
