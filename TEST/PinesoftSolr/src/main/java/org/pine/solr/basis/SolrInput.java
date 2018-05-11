/**
 * Project Name:GisqDCServerRest
 * File Name:SolrInput.java
 * Package Name: com.gisquest.solr.basis
 * Date:2016年10月20日
 * Copyright (c) 2016, Gisquest All Rights Reserved.
 * Current Author:  yangs.
*/
package org.pine.solr.basis;

/**
 * @author:    yangs
 * @Class:      SolrInput
 * @Date:       2016年10月20日
 * @version:   v1.0
 * @Function:  ADD FUNCTION.
 * @Reason:    ADD REASON.
 * @since       JDK 1.8
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.common.SolrInputDocument;
import org.pine.solr.entity.SolrFtrParam;


public class SolrInput {
	/* 动态字段_字符串标识 */
	final static String DYNAMICSTRING = "_s";
	/* 动态字段_时间标识 */
	final static String DYNAMICDATE = "_dt";
	/* 动态字段_分词标识 */
	final static String DYNAMICFTR = "_st";
	/* 默认时间最小值 */
	final static String LOWDATE = "1900-01-01";
	/* solr执行文件对象列 */
	public static List<SolrInputDocument> DOCLIST;

	/**
	 * 构造函数（条件查询）.
	 *
	 * @param dataSet
	 * @param hlFields
	 * @throws Exception
	 */
	public SolrInput(List<Map<String, Object>> dataSet, String hlFields) throws Exception {
		if (dataSet != null && dataSet.size() > 0) {
			SolrInput.DOCLIST = getSolrInputDocList(dataSet, hlFields);
		}
	}

	/**
	 * 构造函数（全文检索）
	 *
	 * @param param
	 * @throws Exception
	 */
	public SolrInput(List<SolrFtrParam> param) throws Exception {
		if (param != null && param.size() > 0) {
			SolrInput.DOCLIST = getSolrInputDocList(param);
		}
	}

	/**
	 * 通过业务数据集获取solr执行文件对象列 提示：如果为时间类型或者时间格式字段，请在配置字段时在其后加上_date来标识
	 *
	 * @Title: getSolrInputDocList
	 * @Description: 静态方法
	 * @param dataSet
	 *            数据集
	 * @param hlFields
	 *            高亮字段
	 * @return List<SolrInputDocument> 执行文件对象列
	 * @author yangs
	 */
	public static List<SolrInputDocument> getSolrInputDocList(List<Map<String, Object>> dataSet, String hlFields) throws Exception {
		List<SolrInputDocument> docList = new ArrayList<SolrInputDocument>();
		if (dataSet != null && dataSet.size() > 0) {
			/* 循环向索引文件中添加数据 */
			for (Map<String, Object> map : dataSet) {
				boolean idExist = false;
				boolean hasHl = hlFields != null && hlFields.length() > 0;
				SolrInputDocument doc = new SolrInputDocument();
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					String field = entry.getKey();
					Object value = entry.getValue() == null ? "" : entry.getValue();

					/* 空值判定 */
					if (StringUtils.isEmpty(field)) {
						continue;
					}

					/* 默认字段（主键id，检索字段searchtext，检索域bizcode）处理 */
					if (field.equals("id") || field.equals("searchText") || field.equals("bizCode") || field.equals("name")
							|| field.equals("creadeDate")) {
						if (field.equals("id")) {
							idExist = true;
						}
						doc.addField(field, value.toString());
						continue;
					}

					/* 高亮字段处理 */
					String[] hlFieldArry = hlFields.split(",");
					if (hasHl && Arrays.asList(hlFieldArry).contains(field)) {
						doc.addField(field + DYNAMICFTR, value.toString());
						/* continue; */
					}

					/* 时间类型字段处理 */
					if (field.toLowerCase().contains("_date")) {
						Date date = null;
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						if (StringUtils.isEmpty(value.toString())) {
							date = sdf.parse(LOWDATE);
						}
						else {
							date = sdf.parse(value.toString());
						}
						doc.addField(field + DYNAMICDATE, date);
						continue;
					}

					/* 缺省字段处理 */
					doc.addField(field + DYNAMICSTRING, value.toString());
				}

				/* 索引创建时间 */
				Date time = new Date();
				doc.addField("creadeDate", time);

				/* 判断是否存在唯一id（主键） */
				if (idExist) {
					docList.add(doc);
				}
				else {
					break;
				}
			}
		}
		return docList;
	}

	/**
	 * 通过全文检索参数对象列获取solr执行文件对象列
	 *
	 * @Title: getSolrInputDocList
	 * @Description: 静态方法
	 * @param param
	 *            全文检索参数对象
	 * @return List<SolrInputDocument> 执行文件对象列
	 * @author yangs
	 */
	public static List<SolrInputDocument> getSolrInputDocList(List<SolrFtrParam> param) throws Exception {
		List<SolrInputDocument> docList = new ArrayList<SolrInputDocument>();
		if (param != null && param.size() > 0) {
			for (SolrFtrParam value : param) {
				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", value.getId());
				doc.addField("creadeDate", value.getCreadeDate());
				doc.addField("name", value.getName());
				doc.addField("searchText", value.getSearchText());
				doc.addField("bizCode", value.getBizCode());

				if (value.getHlfields() != null && value.getHlfields().size() > 0) {
					for (Map.Entry<String, Object> entry : value.getHlfields().entrySet()) {
						String field = entry.getKey();
						Object val = entry.getValue();
						doc.addField(field + DYNAMICFTR, val.toString());
					}
				}

				docList.add(doc);
			}
		}
		return docList;
	}
}
