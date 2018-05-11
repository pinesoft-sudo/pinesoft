package org.pine.webmagic.controller;

import java.util.List;

import javax.annotation.Resource;

import org.pine.common.util.MessageInfo;
import org.pine.common.util.MessageInfo.ResultEnums;
import org.pine.webmagic.entity.MagicEntrySet;
import org.pine.webmagic.service.MagicEntrySetService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;


@RestController
@RequestMapping(value = "magic/entryset/")
public class MagicEntrySetController {
	@Resource
	private MagicEntrySetService magicEntrySetService;

	@RequestMapping(value = "getListByPGuidAndType")
	public MessageInfo getListByPGuidAndType(@RequestParam("pguid") String pguid, @RequestParam("type") String type) {
		try {
			List<MagicEntrySet> response = magicEntrySetService.getListByPGuidAndType(pguid, type);
			String ret = JSON.toJSONString(response);
			return new MessageInfo(ResultEnums.Success, "获取映射信息对象", ret);
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "get")
	public MessageInfo get(@RequestParam("key") String key, @RequestParam("guid") String guid, @RequestParam("type") String type) {
		try {
			MagicEntrySet response = magicEntrySetService.get(key, guid, type);
			String ret = JSON.toJSONString(response);
			return new MessageInfo(ResultEnums.Success, "获取映射信息对象（KEY-VALUE）", ret);
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "add")
	public MessageInfo add(@RequestParam("record") String record) {
		try {
			MagicEntrySet request = JSON.parseObject(record, MagicEntrySet.class);
			String ret = magicEntrySetService.insert(request);
			return new MessageInfo(ResultEnums.Success, "新增映射信息对象", ret);
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "addList")
	public MessageInfo addList(@RequestParam("record") String record) {
		try {
			List<MagicEntrySet> request = JSON.parseArray(record, MagicEntrySet.class);
			int ret = magicEntrySetService.insertList(request);
			return new MessageInfo(ResultEnums.Success, "新增映射信息对象[" + ret + "]", String.valueOf(ret));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "delete")
	public MessageInfo delete(@RequestParam("key") String key, @RequestParam("guid") String guid, @RequestParam("type") String type) {
		try {
			int ret = magicEntrySetService.delete(key, guid, type);
			return new MessageInfo(ResultEnums.Success, "删除映射信息对象[" + ret + "]", String.valueOf(ret));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "deleteListByPGuidsAndType")
	public MessageInfo deleteListByPGuidsAndType(@RequestParam("ids") String ids, @RequestParam("type") String type) {
		try {
			int ret = magicEntrySetService.deleteListByPGuidsAndType(ids, type);
			return new MessageInfo(ResultEnums.Success, "删除映射信息对象[" + ret + "]", String.valueOf(ret));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "updatePortion")
	public MessageInfo updatePortion(@RequestParam("record") String record) {
		try {
			MagicEntrySet request = JSON.parseObject(record, MagicEntrySet.class);
			int ret = magicEntrySetService.updatePortion(request);
			return new MessageInfo(ResultEnums.Success, "更新（合并）映射信息对象[" + ret + "]", String.valueOf(ret));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "updateComplete")
	public MessageInfo updateComplete(@RequestParam("record") String record) {
		try {
			MagicEntrySet request = JSON.parseObject(record, MagicEntrySet.class);
			int ret = magicEntrySetService.updateComplete(request);
			return new MessageInfo(ResultEnums.Success, "更新（覆盖）映射信息对象[" + ret + "]", String.valueOf(ret));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}
}
