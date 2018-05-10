/**
 * Project Name:GisqWebMagic
 * File Name:MagicRequestService.java
 * Package Name:com.gisquest.webmagic.mapper
 * Date:2017�?9�?5日下�?1:51:38
 * Copyright (c) 2017, Yangs All Rights Reserved.
 *
*/
package org.pine.webmagic.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.pine.common.criteria.Filter;
import org.pine.common.util.SpringContextUtil;
import org.pine.webmagic.entity.MagicEntrySet;
import org.pine.webmagic.entity.MagicProcessor;
import org.pine.webmagic.entity.MagicRequest;
import org.pine.webmagic.mapper.MagicRequestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * @author yangs
 *
 */
@Service
public class MagicRequestService {

	@Resource
	private MagicRequestMapper mapper;
	@Resource
	private MagicEntrySetService magicEntrySetService;
	@Resource
	private MagicProcessorService magicProcessorService;
	private Filter filter;
	private static final String TYPE = "pipeline";

	public List<MagicRequest> getList(Filter filter) throws Exception {
		if (mapper == null) {
			mapper = SpringContextUtil.getBean(MagicRequestMapper.class);
		}
		// 添加子对象Processor
		List<MagicRequest> mrList = mapper.selectByFilter(filter);
		for (int i = 0; i < mrList.size(); i++) {
			MagicProcessor processor = magicProcessorService.getByPGuid(mrList.get(i).getGuid());
			mrList.get(i).setProcessor(processor);
		}
		return mrList;
	}

	public MagicRequest get(String guid) throws Exception {
		filter = new Filter("GUID =", guid);
		MagicRequest mr = getList(filter).get(0);
		if (mr != null) {
			List<MagicEntrySet> meList = magicEntrySetService.getListByPGuidAndType(guid, TYPE);
			mr.setPipeline(meList);

			MagicProcessor processor = magicProcessorService.getByPGuid(guid);
			mr.setProcessor(processor);
		}
		return mr;
	}
	@Transactional(rollbackFor = Exception.class)
	public String insert(MagicRequest record) throws Exception {
		if (record == null) {
			throw new Exception("参数对象不能为空");
		}

		String newguid = record.getGuid();
		if (StringUtils.isEmpty(newguid)) {
			newguid = UUID.randomUUID().toString();
			record.setGuid(newguid);
		}

		if (record.getProcessor() != null) {
			MagicProcessor mp = record.getProcessor();
			mp.setPguid(newguid);
			magicProcessorService.insert(mp);
		}
		if (record.getPipeline() != null && record.getPipeline().size() > 0) {
			List<MagicEntrySet> meList = record.getPipeline();
			for (int i = 0; i < meList.size(); i++) {
				meList.get(i).setPguid(newguid);
				meList.get(i).setType(TYPE);
			}
			magicEntrySetService.insertList(meList);
		}
		if (mapper.insert(record) > 0) {
			return newguid;
		}
		else {
			return null;
		}
	}
	@Transactional(rollbackFor = Exception.class)
	public int insertList(List<MagicRequest> recordList) throws Exception {
		if (recordList == null || recordList.size() <= 0) {
			throw new Exception("参数对象不能为空");
		}
		else {
			for (int i = 0; i < recordList.size(); i++) {
				String newguid = recordList.get(i).getGuid();
				if (StringUtils.isEmpty(newguid)) {
					newguid = UUID.randomUUID().toString();
					recordList.get(i).setGuid(newguid);
				}
				List<MagicProcessor> mcList = new ArrayList<MagicProcessor>();
				List<MagicEntrySet> msList = new ArrayList<MagicEntrySet>();
				for (MagicRequest magicRequest : recordList) {
					MagicProcessor mp = magicRequest.getProcessor();
					mp.setPguid(newguid);
					mcList.add(mp);

					List<MagicEntrySet> meList = magicRequest.getPipeline();
					for (int j = 0; j < meList.size(); j++) {
						meList.get(j).setPguid(newguid);
						meList.get(i).setType(TYPE);
					}
					msList.addAll(meList);
				}
			}
			return mapper.insertList(recordList);
		}
	}

	public int deleteList(String ids) throws Exception {
		if (StringUtils.isEmpty(ids)) {
			throw new Exception("参数值不能为�?");
		}
		String[] idArry = ids.split(",");
		List<String> idList = Arrays.asList(idArry);

		magicProcessorService.deleteListByPGuids(ids);
		magicEntrySetService.deleteListByPGuidsAndType(ids, TYPE);

		filter = new Filter("GUID in", idList);
		return mapper.deleteByFilter(filter);
	}

	public int delete(Filter filter) throws Exception {
		return mapper.deleteByFilter(filter);
	}

	public int updatePortion(MagicRequest record) throws Exception {
		if (record == null) {
			throw new Exception("参数对象不能为空");
		}
		filter = new Filter("GUID =", record.getGuid());
		return mapper.updateByFilterSelective(record, filter);
	}

	public int updateComplete(MagicRequest record) throws Exception {
		if (record == null) {
			throw new Exception("参数对象不能为空");
		}
		filter = new Filter("GUID =", record.getGuid());
		return mapper.updateByFilterSelective(record, filter);
	}
}
