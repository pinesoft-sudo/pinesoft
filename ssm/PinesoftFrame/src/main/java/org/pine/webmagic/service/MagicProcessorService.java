/**
 * Project Name:GisqWebMagic
 * File Name:MagicProcessorService.java
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
import org.pine.common.util.SpringContextUtil;
import org.pine.webmagic.entity.MagicEntrySet;
import org.pine.webmagic.entity.MagicProcessor;
import org.pine.webmagic.entity.MagicSelectItem;
import org.pine.webmagic.entity.MagicSelectable;
import org.pine.webmagic.mapper.MagicProcessorMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * @author yangs
 *
 */
@Service
public class MagicProcessorService {
	@Resource
	private MagicProcessorMapper mapper;
	@Resource
	private MagicEntrySetService magicEntrySetService;
	@Resource
	private MagicSelectService magicSelectService;

	private Filter filter;
	private static final String TYPE_H = "header";
	private static final String TYPE_P = "proxy";

	private static final String SELECT_U = "url";
	private static final String SELECT_H = "html";
	private static final String SELECT_T = "target";

	public List<MagicProcessor> getList(Filter filter) throws Exception {
		if (mapper == null) {
			mapper = SpringContextUtil.getBean(MagicProcessorMapper.class);
		}
		return mapper.selectByFilter(filter);
	}

	public MagicProcessor getByGuid(String guid) throws Exception {
		filter = new Filter("GUID =", guid);
		MagicProcessor mp = getList(filter).get(0);
		if (mp != null) {
			List<MagicEntrySet> meList = null;
			meList = magicEntrySetService.getListByPGuidAndType(guid, TYPE_H);
			mp.setAddHeader(meList);
			meList = magicEntrySetService.getListByPGuidAndType(guid, TYPE_P);
			mp.setHttpProxy(meList);

			MagicSelectable msa = new MagicSelectable();
			msa.setGetUrls(magicSelectService.getListByPGuidAndType(guid, SELECT_U));
			msa.setGetHtmls(magicSelectService.getListByPGuidAndType(guid, SELECT_H));
			msa.setTargetRequests(magicSelectService.getListByPGuidAndType(guid, SELECT_T));
			mp.setSelects(msa);
		}
		return mp;
	}

	public MagicProcessor getByPGuid(String pguid) throws Exception {
		filter = new Filter("PGUID =", pguid);
		MagicProcessor mp = getList(filter).get(0);
		if (mp != null) {
			List<MagicEntrySet> meList = null;
			meList = magicEntrySetService.getListByPGuidAndType(mp.getGuid(), TYPE_H);
			mp.setAddHeader(meList);
			meList = magicEntrySetService.getListByPGuidAndType(mp.getGuid(), TYPE_P);
			mp.setHttpProxy(meList);

			MagicSelectable msa = new MagicSelectable();
			msa.setGetUrls(magicSelectService.getListByPGuidAndType(mp.getGuid(), SELECT_U));
			msa.setGetHtmls(magicSelectService.getListByPGuidAndType(mp.getGuid(), SELECT_H));
			msa.setTargetRequests(magicSelectService.getListByPGuidAndType(mp.getGuid(), SELECT_T));
			mp.setSelects(msa);
		}
		return mp;
	}
	@Transactional(rollbackFor = Exception.class)
	public String insert(MagicProcessor record) throws Exception {
		if (record == null) {
			throw new Exception("参数对象不能为空");
		}
		if (StringUtils.isEmpty(record.getPguid())) {
			throw new Exception("参数必需属�?�[PGUID]缺失");
		}
		String newguid = record.getGuid();
		if (StringUtils.isEmpty(newguid)) {
			newguid = UUID.randomUUID().toString();
			record.setGuid(newguid);
		}

		if (record.getAddHeader() != null && record.getAddHeader().size() > 0) {
			List<MagicEntrySet> meList = record.getAddHeader();
			for (int i = 0; i < meList.size(); i++) {
				meList.get(i).setPguid(newguid);
				meList.get(i).setType(TYPE_H);
			}
			magicEntrySetService.insertList(meList);
		}
		if (record.getHttpProxy() != null && record.getHttpProxy().size() > 0) {
			List<MagicEntrySet> meList = record.getHttpProxy();
			for (int i = 0; i < meList.size(); i++) {
				meList.get(i).setPguid(newguid);
				meList.get(i).setType(TYPE_P);
			}
			magicEntrySetService.insertList(meList);
		}
		if (record.getSelects() != null) {
			MagicSelectable selects = record.getSelects();
			List<MagicSelectItem> msiList = null;

			if (selects.getGetUrls() != null && selects.getGetUrls().size() > 0) {
				msiList = selects.getGetUrls();
				for (int i = 0; i < msiList.size(); i++) {
					msiList.get(i).setPguid(newguid);
					msiList.get(i).setType(SELECT_U);
				}
				magicSelectService.insertList(msiList);
			}

			if (selects.getGetHtmls() != null && selects.getGetHtmls().size() > 0) {
				msiList = selects.getGetHtmls();
				for (int i = 0; i < msiList.size(); i++) {
					msiList.get(i).setPguid(newguid);
					msiList.get(i).setType(SELECT_H);
				}
				magicSelectService.insertList(msiList);
			}

			if (selects.getTargetRequests() != null && selects.getTargetRequests().size() > 0) {
				msiList = selects.getTargetRequests();
				for (int i = 0; i < msiList.size(); i++) {
					msiList.get(i).setPguid(newguid);
					msiList.get(i).setType(SELECT_T);
				}
				magicSelectService.insertList(msiList);
			}
		}

		if (mapper.insert(record) > 0) {
			return newguid;
		}
		else {
			return null;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public int insertList(List<MagicProcessor> recordList) throws Exception {
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

				if (recordList.get(i).getAddHeader() != null && recordList.get(i).getAddHeader().size() > 0) {
					List<MagicEntrySet> meList = recordList.get(i).getAddHeader();
					for (int n = 0; n < meList.size(); n++) {
						meList.get(n).setPguid(newguid);
						meList.get(n).setType(TYPE_H);
					}
					magicEntrySetService.insertList(meList);
				}

				if (recordList.get(i).getHttpProxy() != null && recordList.get(i).getHttpProxy().size() > 0) {
					List<MagicEntrySet> meList = recordList.get(i).getHttpProxy();
					for (int n = 0; n < meList.size(); n++) {
						meList.get(n).setPguid(newguid);
						meList.get(n).setType(TYPE_P);
					}
					magicEntrySetService.insertList(meList);
				}

				if (recordList.get(i).getSelects() != null) {
					MagicSelectable selects = recordList.get(i).getSelects();
					List<MagicSelectItem> msiList = null;

					if (selects.getGetUrls() != null && selects.getGetUrls().size() > 0) {
						msiList = selects.getGetUrls();
						for (int n = 0; n < msiList.size(); n++) {
							msiList.get(n).setPguid(newguid);
							msiList.get(n).setType(SELECT_U);
						}
						magicSelectService.insertList(msiList);
					}

					if (selects.getGetHtmls() != null && selects.getGetHtmls().size() > 0) {
						msiList = selects.getGetHtmls();
						for (int n = 0; n < msiList.size(); n++) {
							msiList.get(n).setPguid(newguid);
							msiList.get(n).setType(SELECT_H);
						}
						magicSelectService.insertList(msiList);
					}

					if (selects.getTargetRequests() != null && selects.getTargetRequests().size() > 0) {
						msiList = selects.getTargetRequests();
						for (int n = 0; n < msiList.size(); n++) {
							msiList.get(n).setPguid(newguid);
							msiList.get(n).setType(SELECT_T);
						}
						magicSelectService.insertList(msiList);
					}
				}
			}
			return mapper.insertList(recordList);
		}
	}

	public int deleteListByGuids(String ids) throws Exception {
		if (StringUtils.isEmpty(ids)) {
			throw new Exception("参数值不能为�?");
		}

		magicEntrySetService.deleteListByPGuidsAndType(ids, TYPE_H);
		magicEntrySetService.deleteListByPGuidsAndType(ids, TYPE_P);
		magicSelectService.deleteByPGuids(ids);

		String[] idArry = ids.split(",");
		List<String> idList = Arrays.asList(idArry);
		filter = new Filter("GUID in", idList);
		return delete(filter);
	}

	public int deleteListByPGuids(String ids) throws Exception {
		if (StringUtils.isEmpty(ids)) {
			throw new Exception("参数值不能为�?");
		}
		String[] idArry = ids.split(",");
		List<String> idList = Arrays.asList(idArry);

		String _ids = null;
		for (String string : idList) {
			_ids += getByPGuid(string).getGuid() + ",";
		}
		_ids = _ids.substring(0, _ids.length() - 1);
		magicEntrySetService.deleteListByPGuidsAndType(ids, TYPE_H);
		magicEntrySetService.deleteListByPGuidsAndType(ids, TYPE_P);
		magicSelectService.deleteByPGuids(ids);

		filter = new Filter("PGUID in", idList);
		return delete(filter);
	}

	public int delete(Filter filter) throws Exception {
		return mapper.deleteByFilter(filter);
	}

	public int updatePortion(MagicProcessor record) throws Exception {
		if (record == null) {
			throw new Exception("参数对象不能为空");
		}
		filter = new Filter("GUID =", record.getGuid());
		return mapper.updateByFilterSelective(record, filter);
	}

	public int updateComplete(MagicProcessor record) throws Exception {
		if (record == null) {
			throw new Exception("参数对象不能为空");
		}
		filter = new Filter("GUID =", record.getGuid());
		return mapper.updateByFilterSelective(record, filter);
	}
}
