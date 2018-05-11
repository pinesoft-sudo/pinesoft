package org.pine.webmagic.controller;

import java.util.List;

import javax.annotation.Resource;

import org.pine.common.util.MessageInfo;
import org.pine.common.util.MessageInfo.ResultEnums;
import org.pine.webmagic.entity.MagicProcessor;
import org.pine.webmagic.service.MagicProcessorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;


@RestController
@RequestMapping(value = "magic/processor/")
public class MagicProcessorController {
	@Resource
	private MagicProcessorService magicProcessorService;

	@RequestMapping(value = "getByGuid")
	public MessageInfo getByGuid(@RequestParam("guid") String guid) {
		try {
			MagicProcessor response = magicProcessorService.getByGuid(guid);
			String ret = JSON.toJSONString(response);
			return new MessageInfo(ResultEnums.Success, "获取爬虫解析器", ret);
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "getByPGuid")
	public MessageInfo getByPGuid(@RequestParam("pguid") String pguid) {
		try {
			MagicProcessor response = magicProcessorService.getByGuid(pguid);
			String ret = JSON.toJSONString(response);
			return new MessageInfo(ResultEnums.Success, "获取爬虫解析器", ret);
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "add")
	public MessageInfo add(@RequestParam("record") String record) {
		try {
			MagicProcessor request = JSON.parseObject(record, MagicProcessor.class);
			String ret = magicProcessorService.insert(request);
			return new MessageInfo(ResultEnums.Success, "新增爬虫解析器", ret);
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "addList")
	public MessageInfo addList(@RequestParam("record") String record) {
		try {
			List<MagicProcessor> request = JSON.parseArray(record, MagicProcessor.class);
			int ret = magicProcessorService.insertList(request);
			return new MessageInfo(ResultEnums.Success, "新增爬虫解析器-[" + ret + "]", String.valueOf(ret));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "deleteListByGuids")
	public MessageInfo deleteListByGuids(@RequestParam("ids") String ids) {
		try {
			int ret = magicProcessorService.deleteListByGuids(ids);
			return new MessageInfo(ResultEnums.Success, "删除爬虫解析器-[" + ret + "]", String.valueOf(ret));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "deleteListByPGuids")
	public MessageInfo deleteListByPGuids(@RequestParam("ids") String ids) {
		try {
			int ret = magicProcessorService.deleteListByPGuids(ids);
			return new MessageInfo(ResultEnums.Success, "删除爬虫解析器-[" + ret + "]", String.valueOf(ret));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "updatePortion")
	public MessageInfo updatePortion(@RequestParam("record") String record) {
		try {
			MagicProcessor request = JSON.parseObject(record, MagicProcessor.class);
			int ret = magicProcessorService.updatePortion(request);
			return new MessageInfo(ResultEnums.Success, "更新（合并）爬虫解析器-[" + ret + "]", String.valueOf(ret));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "updateComplete")
	public MessageInfo updateComplete(@RequestParam("record") String record) {
		try {
			MagicProcessor request = JSON.parseObject(record, MagicProcessor.class);
			int ret = magicProcessorService.updateComplete(request);
			return new MessageInfo(ResultEnums.Success, "更新（覆盖）爬虫解析器-[" + ret + "]", String.valueOf(ret));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}
}
