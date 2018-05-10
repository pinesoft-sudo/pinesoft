package org.pine.webmagic.controller;

import java.util.List;

import javax.annotation.Resource;

import org.pine.common.util.MessageInfo;
import org.pine.common.util.MessageInfo.ResultEnums;
import org.pine.webmagic.entity.MagicSelectItem;
import org.pine.webmagic.service.MagicSelectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

@RestController
@RequestMapping(value = "magic/select/")
public class MagicSelectController {
	@Resource
	private MagicSelectService magicSelectService;

	@RequestMapping(value = "getListByPGuidAndType")
	public MessageInfo getListByPGuidAndType(@RequestParam("pguid") String pguid, @RequestParam("type") String type) {
		try {
			List<MagicSelectItem> response = magicSelectService.getListByPGuidAndType(pguid, type);
			String ret = JSON.toJSONString(response);
			return new MessageInfo(ResultEnums.Success, "获取爬虫筛选器对象列", ret);
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "get")
	public MessageInfo get(@RequestParam("guid") String guid) {
		try {
			MagicSelectItem response = magicSelectService.get(guid);
			String ret = JSON.toJSONString(response);
			return new MessageInfo(ResultEnums.Success, "获取爬虫筛选器对象", ret);
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "add")
	public MessageInfo add(@RequestParam("record") String record) {
		try {
			MagicSelectItem request = JSON.parseObject(record, MagicSelectItem.class);
			String ret = magicSelectService.insert(request);
			return new MessageInfo(ResultEnums.Success, "新增爬虫筛选器对象", ret);
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "addList")
	public MessageInfo addList(@RequestParam("record") String record) {
		try {
			List<MagicSelectItem> request = JSON.parseArray(record, MagicSelectItem.class);
			int ret = magicSelectService.insertList(request);
			return new MessageInfo(ResultEnums.Success, "新增爬虫筛选器对象[" + ret + "]项", String.valueOf(ret));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "deleteByGuids")
	public MessageInfo deleteByGuids(@RequestParam("ids") String ids) {
		try {
			int ret = magicSelectService.deleteByGuids(ids);
			return new MessageInfo(ResultEnums.Success, "删除爬虫筛选器对象[" + ret + "]项", String.valueOf(ret));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "deleteByPGuidAndType")
	public MessageInfo deleteByPGuidAndType(@RequestParam("pguid") String pguid, @RequestParam("type") String type) {
		try {
			int ret = magicSelectService.deleteByPGuidAndType(pguid, type);
			return new MessageInfo(ResultEnums.Success, "删除爬虫筛选器对象[" + ret + "]项", String.valueOf(ret));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "deleteByPGuids")
	public MessageInfo deleteByPGuids(@RequestParam("ids") String ids) {
		try {
			int ret = magicSelectService.deleteByPGuids(ids);
			return new MessageInfo(ResultEnums.Success, "删除爬虫筛选器对象[" + ret + "]项", String.valueOf(ret));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "updatePortion")
	public MessageInfo updatePortion(@RequestParam("record") String record) {
		try {
			MagicSelectItem request = JSON.parseObject(record, MagicSelectItem.class);
			int ret = magicSelectService.updatePortion(request);
			return new MessageInfo(ResultEnums.Success, "更新（合并）爬虫筛选器对象[" + ret + "]项", String.valueOf(ret));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "updateComplete")
	public MessageInfo updateComplete(@RequestParam("record") String record) {
		try {
			MagicSelectItem request = JSON.parseObject(record, MagicSelectItem.class);
			int ret = magicSelectService.updateComplete(request);
			return new MessageInfo(ResultEnums.Success, "更新（覆盖）爬虫筛选器对象[" + ret + "]项", String.valueOf(ret));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

}
