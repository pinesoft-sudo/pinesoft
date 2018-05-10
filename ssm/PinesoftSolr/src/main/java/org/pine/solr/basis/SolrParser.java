/**
 * Project Name:GisqDCServerRest
 * File Name:SolrParser.java
 * Package Name: com.gisquest.solr.basis
 * Date:2016年10月20日
 * Copyright (c) 2016, Gisquest All Rights Reserved.
 * Current Author:  yangs.
*/
package org.pine.solr.basis;

/**
 * @author:    yangs
 * @Class:      SolrParser
 * @Date:       2016年10月24日
 * @version:   v1.0
 * @Function:  ADD FUNCTION.
 * @Reason:    ADD REASON.
 * @since       JDK 1.8
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.parser.QueryParser;
import org.pine.solr.entity.EOperator;
import org.pine.solr.entity.QueryEntrySet;
import org.pine.solr.entity.SolrBuilder;
import org.pine.solr.entity.QueryRange;
import org.pine.solr.entity.SolrRequest;
import org.pine.solr.entity.SolrResponse;


public class SolrParser {
	/* 动态字段_时间标识 */
	final static String DYNAMICDATE = "_dt";
	/* 动态字段_分词标识 */
	final static String DYNAMICFTR = "_st";
	/* 默认时间最小值 */
	final static String LOWDATE = "1900-01-01";
	/* 动态字段_缺省标识 */
	final static String DYNAMICSTRING = "_s";

	/**
	 * 通过检索数据和显示模版获取显示内容
	 *
	 * @Title: contentParse
	 * @Description: 公开方法
	 * @param solrRep
	 *            检索数据
	 * @param executeContent
	 *            显示模版
	 * @return String 显示内容字符串
	 * @author yangs
	 */
	public static String contentParse(SolrResponse solrRep, String executeContent) {
		if (solrRep == null) return null;
		String retStr = "";
		List<Map<String, Object>> dataList = solrRep.getData();
		if (dataList != null && dataList.size() > 0) {
			for (Map<String, Object> map : dataList) {
				String temp = executeContent;
				for (String key : map.keySet()) {
					String replaceKey = "@" + key + "@";
					String replaceValue = map.get(key) == null ? "" : map.get(key).toString();
					temp = temp.replace(replaceKey, replaceValue);
				}
				temp = temp.replace("@TOTAL@", String.valueOf(solrRep.getTotalnum()));
				retStr += temp;
			}
		}
		return retStr;
	}

	/**
	 * 解析条件查询对象SolrQueryBuilder
	 *
	 * @Title: queryParse
	 * @Description:公开方法
	 * @param sqb
	 *            条件检索请求对象
	 * @return SolrQuery 查询条件对象
	 * @author yangs
	 */
	public static SolrQuery queryParse(SolrBuilder sqb) throws Exception {
		SolrQuery query = null;
		StringBuilder sb = null;
		if (sqb != null) {
			query = new SolrQuery();
			List<QueryEntrySet> and_org = sqb.getParam_and();
			List<List<QueryEntrySet>> or_arg = sqb.getParam_or();
			boolean hasor = true;

			/* 查询条件（OR） */
			if (or_arg != null && or_arg.size() > 0) {
				sb = new StringBuilder();
				for (int i = 0; i < or_arg.size(); i++) {
					if (i == 0) {
						sb.append("(");
					}
					else {
						sb.append(" OR ");
					}
					for (int j = 0; j < or_arg.get(i).size(); j++) {
						QueryEntrySet sqa = or_arg.get(i).get(j);
						if (j == 0) {
							sb.append("(");
						}
						else {
							sb.append(" AND ");
						}
						/* AND对象解析 */
						sb.append(solrQueryFormat(sqa));

						if (j == or_arg.get(i).size() - 1) {
							sb.append(")");
						}
					}
					if (i == or_arg.size() - 1) {
						sb.append(")");
					}
				}
				query.setQuery(sb.toString());
			}
			else {
				hasor = false;
			}

			/* 查询条件（AND） */
			if (and_org != null && and_org.size() > 0) {
				sb = new StringBuilder();
				for (int i = 0; i < and_org.size(); i++) {
					QueryEntrySet sqa = and_org.get(i);

					/* AND对象解析 */
					sb.append(solrQueryFormat(sqa));

					if (hasor) {
						query.addFilterQuery(sb.toString());
						sb.delete(0, sb.length());
					}
					else {
						if (i != and_org.size() - 1) {
							sb.append(" AND ");
						}
					}
				}
				if (!hasor) {
					query.setQuery(sb.toString());
				}
			}

			// 当查询条件都为null时，默认查询全部
			if (and_org == null && or_arg == null) {
				query.setQuery("*:*");
			}

			/* 分页（size为0则不开启分页） */
			if (sqb.getSize() > 0) {
				query.setStart(sqb.getStart());
				query.setRows(sqb.getSize());
			}

			/* 排序（排序字段为空则不开启排序） */
			if (!StringUtils.isEmpty(sqb.getSortfield())) {
				List<SortClause> scs = solrQuerySort(sqb.getSortfield(), sqb.getSorttype());
				if (scs != null) {
					query.setSorts(scs);
				}
			}
		}
		return query;
	}

	/**
	 * 解析全文检索对象SolrRequest
	 *
	 * @Title: qureyFTR
	 * @Description: 公开方法
	 * @param srq
	 *            全文检索请求对象
	 * @return SolrQuery 查询条件对象
	 * @author yangs
	 */
	public static SolrQuery qureyFTR(SolrRequest srq) throws Exception {
		SolrQuery query = null;
		if (srq != null) {
			query = qureyFTR(srq.getSearchvalue(), srq.getBizcode(), srq.getSize(), srq.getCurrent(), srq.getSortfield(), srq.getSorttype(),
					srq.getHlfields());
		}
		return query;
	}

	/**
	 * 解析全文检索参数
	 *
	 * @Title: qureyFTR
	 * @Description: 公开方法
	 * @param searchValue
	 *            检索内容
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
	 * @return SolrQuery 查询条件对象
	 * @author yangs
	 */
	public static SolrQuery qureyFTR(String searchValue, String bizCode, int size, int current, String sortField, String sortType, String hlFields)
			throws Exception {
		SolrQuery query = new SolrQuery();
		StringBuilder sb = new StringBuilder();

		/* 查询条件 */
		if (StringUtils.isEmpty(searchValue)) {
			sb.append("searchText:*");
		}
		else {
			// 特殊字符转译问题（solr query的查询关键字）
			searchValue = QueryParser.escape(searchValue);
			searchValue = searchValue.trim().replaceAll(" +", ",");
			sb.append("searchText:" + searchValue);
		}

		/* 数据域（bizCode） */
		if (StringUtils.isEmpty(bizCode)) {
			query.setQuery(sb.toString());
		}
		else {
			bizCode = bizCode.replace(",", " OR ");
			query.setQuery(sb.toString() + " AND bizCode:(" + bizCode + ")");
		}

		/* 分页（size为0则不开启分页） */
		if (size > 0) {
			if (current <= 0) current = 1;
			int start = (current - 1) * size;
			query.setStart(start);
			query.setRows(size);
		}

		/* 排序（排序字段为空则不开启排序） */
		if (!StringUtils.isEmpty(sortField)) {
			List<SortClause> scs = solrQuerySort(sortField, sortType);
			if (scs != null && scs.size() > 0) {
				query.setSorts(scs);
			}
		}

		/* 高亮（hlFields开启高亮） */
		String[] hlFieldArry = hlFields == null ? null : hlFields.split(",");
		if (hlFieldArry != null && hlFieldArry.length > 0) {
			query.setHighlight(true);
			query.addHighlightField("searchText");
			query.addHighlightField("name");
			for (String str : hlFieldArry) {
				if (str.equals("searchText") || str.equals("name")) {
					continue;
				}
				else {
					query.addHighlightField(str + DYNAMICFTR);
				}
			}
			query.setHighlightSimplePre("<font color=\"red\">");
			query.setHighlightSimplePost("</font>");
		}
		return query;
	}

	
	
	/**
	 * 解析排序对象列
	 *
	 * @Title: solrQuerySort
	 * @Description: 内部方法
	 * @param sortfieldStr
	 *            排序字段
	 * @param sorttypeStr
	 *            排序方式
	 * @return List<SortClause>排序对象
	 * @author yangs
	 */
	private static List<SortClause> solrQuerySort(String sortfieldStr, String sorttypeStr) throws Exception {
		String[] sortfieldArry = sortfieldStr.split(",|，");
		String[] sorttypeArry = sorttypeStr.split(",|，", sortfieldArry.length);

		List<SortClause> scs = new ArrayList<SortClause>();
		for (int i = 0; i < sortfieldArry.length; i++) {
			if (StringUtils.isEmpty(sortfieldArry[i])) {
				continue;
			}
			String field = sortfieldArry[i];
			if (field != null) {
				if (field.equals("id") || field.equals("searchText") || field.equals("bizCode") || field.equals("name")
						|| field.equals("creadeDate")) {
				}
				else if (field.toLowerCase().contains("_date")) {
					field = field + DYNAMICDATE;
				}
				else {
					field = field + DYNAMICSTRING;
				}
			}
			String type = (i >= sorttypeArry.length) ? "" : sorttypeArry[i];
			ORDER order;
			switch (type) {
				case "desc" :
					order = ORDER.desc;
					break;
				case "asc" :
					order = ORDER.asc;
					break;
				default :
					order = ORDER.desc;
					break;
			}
			scs.add(new SortClause(field, order));
		}
		return scs;
	}

	/**
	 * 解析条件拼接对象SolrQueryAnd
	 *
	 * @Title: solrQueryFormat
	 * @Description: 内部方法
	 * @param sqa
	 *            条件拼接对象
	 * @return String 查询拼接字符串
	 * @author yangs
	 */
	private static String solrQueryFormat(QueryEntrySet sqa) throws Exception {
		String str = null;
		if (sqa.getRange() == null && (sqa.getOperator() == null || sqa.getOperator() == EOperator.like)) {
			if (sqa.getKey().equals("*")) {
				str = String.format("*:%s", sqa.getEscapeValue().equals("*") ? "*" : sqa.getEscapeValue());
			}
			else {
				str = String.format("%s:*%s*", sqa.getNewKey(), sqa.getEscapeValue());
			}
		}
		else if (sqa.getOperator() == EOperator.llike) {
			str = String.format("%s:*%s", sqa.getNewKey(), sqa.getEscapeValue());
		}
		else if (sqa.getOperator() == EOperator.rlike) {
			str = String.format("%s:%s*", sqa.getNewKey(), sqa.getEscapeValue());
		}
		else if (sqa.getOperator() == EOperator.is) {
			str = String.format("%s:%s", sqa.getNewKey(), sqa.getEscapeValue());
		}
		else if (sqa.getOperator() == EOperator.ne) {
			str = String.format("!(%s:%s)", sqa.getNewKey(), sqa.getEscapeValue());
		}
		else {
			QueryRange sqr = sqa.getRange();
			String beginStr = sqr.getBegin();
			String endStr = sqr.getEnd();
			if (sqr.getNewKey().contains(DYNAMICDATE)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date lowDate = sdf.parse(LOWDATE);

				SimpleDateFormat tmp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				if (StringUtils.isEmpty(beginStr)) {
					beginStr = tmp.format(lowDate);
				}
				else {
					Date beginDate = sdf.parse(beginStr);
					beginStr = tmp.format(beginDate);
				}

				if (StringUtils.isEmpty(endStr)) {
					endStr = "*";
				}
				else {
					Date endDate = sdf.parse(endStr);
					endStr = tmp.format(endDate);
				}
				str = String.format("%s:{%s TO %s]", sqr.getNewKey(), beginStr, endStr);

			}
			else {
				str = String.format("%s:[%s TO %s]", sqr.getNewKey(), StringUtils.isEmpty(beginStr) ? "*" : beginStr,
						StringUtils.isEmpty(endStr) ? "*" : endStr);
			}
		}
		return str;
	}

}
