/**
 * Project Name:GisqWebMagic
 * File Name:MagicSelectService.java
 * Package Name:com.gisquest.webmagic.mapper
 * Date:2017�?9�?5日下�?1:51:38
 * Copyright (c) 2017, Yangs All Rights Reserved.
 *
*/
package org.pine.webmagic.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.pine.common.criteria.Filter;
import org.pine.common.criteria.GeneratedCriteria;
import org.pine.common.util.SpringContextUtil;
import org.pine.webmagic.entity.MagicEntrySet;
import org.pine.webmagic.entity.MagicSelectItem;
import org.pine.webmagic.mapper.MagicSelectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * @author yangs
 *
 */
@Service
public class MagicSelectService {
	@Resource
	private MagicSelectMapper mapper;
	@Resource
	private MagicEntrySetService magicEntrySetService;

	private Filter filter;
	private GeneratedCriteria criteria;
	private static final String TYPE = "select";

	public List<MagicSelectItem> getList(Filter filter) throws Exception {
		if (mapper == null) {
			mapper = SpringContextUtil.getBean(MagicSelectMapper.class);
		}
		// 无子对象
		return mapper.selectByFilter(filter);
	}

	public MagicSelectItem get(String guid) throws Exception {
		filter = new Filter("GUID =", guid);
		MagicSelectItem msi = getList(filter).get(0);
		List<MagicEntrySet> meList = magicEntrySetService.getListByPGuidAndType(guid, TYPE);
		msi.setSelects(meList);
		return msi;
	}

	public List<MagicSelectItem> getListByPGuidAndType(String pguid, String type) throws Exception {
		if (StringUtils.isEmpty(pguid) || StringUtils.isEmpty(type)) {
			throw new Exception("参数值不能为�?");
		}
		filter = new Filter();
		criteria = new GeneratedCriteria();
		criteria.addCriterion("PGUID =", pguid);
		criteria.addCriterion("TYPE =", type);
		filter.createCriteria(criteria);
		List<MagicSelectItem> msiList = getList(filter);
		if (msiList != null && msiList.size() > 0) {
			for (int i = 0; i < msiList.size(); i++) {
				List<MagicEntrySet> meList = magicEntrySetService.getListByPGuidAndType(msiList.get(i).getGuid(), TYPE);
				msiList.get(i).setSelects(meList);
			}
		}
		return msiList;
	}

	@Transactional(rollbackFor = Exception.class)
	public String insert(MagicSelectItem record) throws Exception {
		if (record == null) {
			throw new Exception("参数对象不能为空");
		}
		if (StringUtils.isEmpty(record.getPguid())) {
			throw new Exception("参数必需属�?�[PGUID]缺失");
		}
		if (StringUtils.isEmpty(record.getType())) {
			throw new Exception("参数必需属�?�[TYPE]缺失");
		}

		String newguid = record.getGuid();
		if (StringUtils.isEmpty(newguid)) {
			newguid = UUID.randomUUID().toString();
			record.setGuid(newguid);
		}

		List<MagicEntrySet> meList = record.getSelects();
		for (int i = 0; i < meList.size(); i++) {
			meList.get(i).setPguid(newguid);
			meList.get(i).setType(TYPE);
		}
		magicEntrySetService.insertList(meList);

		if (mapper.insert(record) > 0) {
			return newguid;
		}
		else {
			return null;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public int insertList(List<MagicSelectItem> recordList) throws Exception {
		if (recordList == null || recordList.size() <= 0) {
			throw new Exception("参数对象不能为空");
		}
		else {
			for (int i = 0; i < recordList.size(); i++) {
				String pguid = recordList.get(i).getPguid();
				if (StringUtils.isEmpty(pguid)) {
					throw new Exception("参数必需属�?�[PGUID]缺失");
				}
				String newguid = recordList.get(i).getGuid();
				if (StringUtils.isEmpty(pguid)) {
					newguid = UUID.randomUUID().toString();
					recordList.get(i).setGuid(newguid);
				}
				List<MagicEntrySet> meList = recordList.get(i).getSelects();
				for (int j = 0; j < meList.size(); j++) {
					meList.get(j).setPguid(newguid);
					meList.get(i).setType(TYPE);
				}
				magicEntrySetService.insertList(meList);
			}
			return mapper.insertList(recordList);
		}
	}

	public int deleteByGuids(String ids) throws Exception {
		if (StringUtils.isEmpty(ids)) {
			throw new Exception("参数值不能为�?");
		}
		String[] idArry = ids.split(",");
		List<String> idList = Arrays.asList(idArry);
		magicEntrySetService.deleteListByPGuidsAndType(ids, TYPE);

		filter = new Filter("GUID in", idList);
		return mapper.deleteByFilter(filter);
	}

	public int delete(Filter filter) throws Exception {
		return mapper.deleteByFilter(filter);
	}

	public int deleteByPGuidAndType(String pguid, String type) throws Exception {
		if (StringUtils.isEmpty(pguid)) {
			throw new Exception("参数值不能为�?");
		}
		filter = new Filter();
		criteria = new GeneratedCriteria();
		criteria.addCriterion("PGUID =", pguid);
		criteria.addCriterion("TYPE =", type);
		filter.createCriteria(criteria);
		List<MagicSelectItem> msiList = getList(filter);
		for (MagicSelectItem magicSelectItem : msiList) {
			magicEntrySetService.deleteListByPGuidsAndType(magicSelectItem.getGuid(), TYPE);
		}
		return delete(filter);
	}

	public int deleteByPGuids(String ids) throws Exception {
		if (StringUtils.isEmpty(ids)) {
			throw new Exception("参数值不能为�?");
		}
		filter = new Filter();
		criteria = new GeneratedCriteria();
		criteria.addCriterion("PGUID in", ids);
		filter.createCriteria(criteria);
		List<MagicSelectItem> msiList = getList(filter);
		for (MagicSelectItem magicSelectItem : msiList) {
			magicEntrySetService.deleteListByPGuidsAndType(magicSelectItem.getGuid(), TYPE);
		}
		return delete(filter);
	}

	public int updatePortion(MagicSelectItem record) throws Exception {
		if (record == null) {
			throw new Exception("参数对象不能为空");
		}
		filter = new Filter("GUID =", record.getGuid());
		return mapper.updateByFilterSelective(record, filter);
	}

	public int updateComplete(MagicSelectItem record) throws Exception {
		if (record == null) {
			throw new Exception("参数对象不能为空");
		}
		filter = new Filter("GUID =", record.getGuid());
		return mapper.updateByFilterSelective(record, filter);
	}
}
