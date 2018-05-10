/**
 * Project Name:GisqWebMagic
 * File Name:MagicEntrySetService.java
 * Package Name:com.gisquest.webmagic.mapper
 * Date:2017�?9�?5日下�?1:51:38
 * Copyright (c) 2017, Yangs All Rights Reserved.
 *
*/
package org.pine.webmagic.service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.pine.common.criteria.Filter;
import org.pine.common.criteria.GeneratedCriteria;
import org.pine.common.util.SpringContextUtil;
import org.pine.webmagic.entity.MagicEntrySet;
import org.pine.webmagic.mapper.MagicEntrySetMapper;
import org.springframework.stereotype.Service;


/**
 * 
 * @author yangs
 *
 */
@Service
public class MagicEntrySetService {
	@Resource
	private MagicEntrySetMapper mapper;
	private Filter filter;
	private GeneratedCriteria criteria;

	public List<MagicEntrySet> getList(Filter filter) throws Exception {
		if (mapper == null) {
			mapper = SpringContextUtil.getBean(MagicEntrySetMapper.class);
		}
		// 无子对象
		return mapper.selectByFilter(filter);
	}

	public List<MagicEntrySet> getListByPGuidAndType(String guid, String type) throws Exception {
		filter = new Filter();
		criteria = new GeneratedCriteria();
		criteria.addCriterion("PGUID =", guid);
		criteria.addCriterion("TYPE =", type);
		filter.createCriteria(criteria);
		return getList(filter);
	}

	public MagicEntrySet get(String key, String guid, String type) throws Exception {
		filter = new Filter();
		criteria = new GeneratedCriteria();
		criteria.addCriterion("KEY =", key);
		criteria.addCriterion("PGUID =", guid);
		criteria.addCriterion("TYPE =", type);
		filter.createCriteria(criteria);
		return getList(filter).get(0);
	}

	public String insert(MagicEntrySet record) throws Exception {
		if (record == null) {
			throw new Exception("参数对象不能为空");
		}
		String pguid = record.getPguid();
		String key = record.getKey();
		if (StringUtils.isEmpty(pguid) || StringUtils.isEmpty(key)) {
			throw new Exception("参数必需属性缺失");
		}
		if (mapper.insert(record) > 0) {
			return pguid;
		}
		else {
			return null;
		}
	}

	public int insertList(List<MagicEntrySet> recordList) throws Exception {
		if (recordList == null || recordList.size() <= 0) {
			throw new Exception("参数对象不能为空");
		}
		else {
			for (int i = 0; i < recordList.size(); i++) {
				String pguid = recordList.get(i).getPguid();
				String key = recordList.get(i).getKey();
				if (StringUtils.isEmpty(pguid) || StringUtils.isEmpty(key)) {
					throw new Exception("参数必需属属性缺失[" + i + "]");
				}
			}
			return mapper.insertList(recordList);
		}
	}

	public int delete(String key, String guid, String type) throws Exception {
		if (StringUtils.isEmpty(key) || StringUtils.isEmpty(key) || StringUtils.isEmpty(key)) {
			throw new Exception("参数值不能为空");
		}
		filter = new Filter();
		criteria = new GeneratedCriteria();
		criteria.addCriterion("KEY =", key);
		criteria.addCriterion("PGUID =", guid);
		criteria.addCriterion("TYPE =", type);
		filter.createCriteria(criteria);
		return mapper.deleteByFilter(filter);
	}

	public int deleteListByPGuidsAndType(String ids, String type) throws Exception {
		if (StringUtils.isEmpty(ids) || StringUtils.isEmpty(type)) {
			throw new Exception("参数值不能为空");
		}
		String[] idArry = ids.split(",");
		List<String> idList = Arrays.asList(idArry);

		filter = new Filter();
		criteria = new GeneratedCriteria();
		criteria.addCriterion("PGUID in", idList);
		criteria.addCriterion("TYPE =", type);
		filter.createCriteria(criteria);
		return mapper.deleteByFilter(filter);
	}

	public int updatePortion(MagicEntrySet record) throws Exception {
		if (record == null) {
			throw new Exception("参数对象不能为空");
		}
		filter = new Filter();
		criteria = new GeneratedCriteria();
		criteria.addCriterion("KEY =", record.getKey());
		criteria.addCriterion("PGUID =", record.getPguid());
		criteria.addCriterion("TYPE =", record.getType());
		filter.createCriteria(criteria);
		return mapper.updateByFilterSelective(record, filter);
	}

	public int updateComplete(MagicEntrySet record) throws Exception {
		if (record == null) {
			throw new Exception("参数对象不能为空");
		}
		filter = new Filter();
		criteria = new GeneratedCriteria();
		criteria.addCriterion("KEY =", record.getKey());
		criteria.addCriterion("PGUID =", record.getPguid());
		criteria.addCriterion("TYPE =", record.getType());
		filter.createCriteria(criteria);
		return mapper.updateByFilter(record, filter);
	}
}
