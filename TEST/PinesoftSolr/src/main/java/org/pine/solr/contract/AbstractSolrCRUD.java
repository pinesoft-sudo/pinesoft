/**
 * Project Name:GisqDCServerRest
 * File NameAbstractSolrCRUD.java
 * Package Name: com.gisquest.solr.basis
 * Date:2016年10月20日
 * Copyright (c) 2016, Gisquest All Rights Reserved.
 * Current Author:  yangs.
*/
package org.pine.solr.contract;

/**
 * @author:    yangs
 * @Class:      AbstractSolrCRUD
 * @Date:       2016年10月31日
 * @version:   v1.0
 * @Function: TODO ADD FUNCTION.
 * @Reason:   TODO ADD REASON.
 * @since       JDK 1.8
 */
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;

import org.pine.solr.entity.SolrBuilder;

public abstract class AbstractSolrCRUD extends SolrCase {
	/**
	 * 构造函数（继承）
	 */
	public AbstractSolrCRUD(SolrClient solrclient) {
		super(solrclient);
	}

	/**
	 * 构造函数（继承）
	 */
	public AbstractSolrCRUD(String solrurl, String solrhome) {
		super(solrurl, solrhome);
	}

	/**
	 * 创建单个索引文件（条件查询）
	 *
	 * @Title: createIndex
	 * @Description: 公开方法
	 * @param dataMap
	 *            单条业务数据
	 * @param hlFields
	 *            高亮字段
	 * @return 是否创建成功
	 * @throws Exception
	 * @returnType boolean
	 * @author yangs
	 */
	public abstract boolean createIndex(Map<String, Object> dataMap, String hlFields) throws Exception;

	/**
	 * 创建索引文件 （条件查询）
	 *
	 * @Title: createIndex
	 * @Description: 公开方法
	 * @param dataSet
	 *            业务数据集
	 * @param hlFields
	 *            高亮字段
	 * @return 创建索引数目
	 * @throws Exception
	 * @returnType long
	 * @author yangs
	 */
	public abstract long createIndex(List<Map<String, Object>> dataSet, String hlFields) throws Exception;

	/**
	 * 条件查询检索
	 *
	 * @Title: searchIndex
	 * @Description: 公开方法
	 * @param sqb
	 *            查询条件对象
	 * @return 查询返回值
	 * @throws Exception
	 * @returnType String
	 * @author yangs
	 */
	public abstract String searchIndex(SolrBuilder sqb) throws Exception;

	/**
	 * 条件查询检索
	 *
	 * @Title: searchIndex
	 * @Description: TODO
	 * @param sqb
	 *            查询条件对象
	 * @param executeContent
	 *            显示模版
	 * @return 显示内容
	 * @throws Exception
	 * @returnType String
	 * @author yangs
	 */
	public abstract String searchIndex(SolrBuilder sqb, String executeContent) throws Exception;
}
